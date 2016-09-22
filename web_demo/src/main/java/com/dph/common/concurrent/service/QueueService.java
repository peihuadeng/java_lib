package com.dph.common.concurrent.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dph.common.cache.constEnum.RunningStatus;
import com.dph.common.concurrent.lock.WaitLock;

/**
 * 队列服务。
 * 
 * 操作：启动，停止，入队列，消费队列
 * 
 * @author peihuadeng
 *
 */
public abstract class QueueService<T, R extends QueueServiceRunnable<T>> {

	private final static Logger logger = LoggerFactory.getLogger(QueueService.class);
	protected final static Lock lock = new ReentrantLock();// 队列服务锁对象
	private Queue<T> queue;// 队列
	private volatile RunningStatus status;// 队列服务状态:STARTING, STARTED, STOPPING, STOPPED
	private int workerIndex;// 工作线程当前索引
	private int workers;// 工作线程总数
	private ExecutorService executorService;// 线程池
	private List<R> workerList;// 工作线程数组
	private List<WaitLock> lockList;// 工作线程内置锁对象数据
	private List<Future<?>> futureList;// 工作线程执行结果数组

	// private static QueueService instance = null;// 单例引用
	// /**
	// * 获取单例，线程安全操作
	// *
	// * @return
	// */
	// public static QueueService getInstance() {
	// lock.lock();
	// try {
	// if (instance == null) {
	// instance = new QueueService();
	// }
	//
	// return instance;
	// } finally {
	// lock.unlock();
	// }
	// }

	/**
	 * 初始化
	 */
	public QueueService() {
		queue = new ConcurrentLinkedQueue<T>();// 初始化队列为并发队列对象
		workerList = new ArrayList<R>();// 初始化工作线程数组
		lockList = new ArrayList<WaitLock>();// 初始化工作线程内置锁对象数组
		futureList = new ArrayList<Future<?>>();// 初始化工作线程执行结果数组
		this.status = RunningStatus.STOPPED;// 初始化队列服务状态为停止
	}

	/**
	 * 启动队列服务
	 * 
	 * @param workers
	 */
	public void start(int workers) {
		logger.info("queue service starting...");
		lock.lock();
		try {
			// 状态判断：状态必须为停止才能启动
			if (this.status != RunningStatus.STOPPED) {
				logger.warn("queue service has started");
				return;
			}
			this.status = RunningStatus.STARTING;
			this.workers = workers;
			this.workerIndex = 0;
			// 创建线程池
			this.executorService = Executors.newFixedThreadPool(workers, new ThreadFactory() {
				private final AtomicInteger index = new AtomicInteger(0);

				@Override
				public Thread newThread(Runnable r) {
					// 线程名称为QueueService_${index}
					return new Thread(r, "QueueService_" + index.getAndIncrement());
				}

			});
			// 创建锁对象、工作线程、工作线程执行结果，并把工作线程提交给线程池执行
			for (int i = 0; i < workers; i++) {
				WaitLock waitLock = new WaitLock();
				lockList.add(waitLock);
				R runnable = newRunnable(waitLock);
				workerList.add(runnable);
				Future<?> future = executorService.submit(runnable);
				futureList.add(future);
			}

			this.status = RunningStatus.STARTED;
		} finally {
			lock.unlock();
		}
		logger.info("queue service started.");
	}

	@SuppressWarnings("unchecked")
	protected R newRunnable(WaitLock waitLock) {
		Class<R> runnableClass = (Class<R>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		try {
			R runnable = runnableClass.getConstructor(WaitLock.class, QueueService.class).newInstance(waitLock, this);
			return runnable;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			logger.error("fail to new runnable", e);
			throw new RuntimeException("fail to new runnable");
		}
	}

	/**
	 * 停止队列服务
	 */
	public void stop() {
		logger.info("queue service stopping...");
		lock.lock();
		try {
			// 判断状态：状态必须为已启动才能停止
			if (this.status != RunningStatus.STARTED) {
				logger.warn("queue service has stopped");
				return;
			}
			this.status = RunningStatus.STOPPING;
			// 设置停止标识
			for (int i = 0; i < workers; i++) {
				R runnable = workerList.get(i);
				runnable.destory();
			}
			// 等待停止
			for (int i = 0; i < workers; i++) {
				try {
					Future<?> future = futureList.get(i);
					future.get();
				} catch (Exception e) {
					logger.warn("fail to stop queue service runnable", e);
				}
			}
			// 关闭线程池
			executorService.shutdown();
			// 释放资源
			executorService = null;
			workerList.clear();
			lockList.clear();
			futureList.clear();
			this.status = RunningStatus.STOPPED;
		} finally {
			lock.unlock();
		}
		logger.info("queue service stopped.");
	}

	/**
	 * 出队列
	 * 
	 * @return
	 */
	protected T poll() {
		T t = queue.poll();
		return t;
	}

	/**
	 * 入队列，并唤醒其中一个工作线程，可达到工作线程负载均衡要求
	 * 
	 * @param t
	 * @return
	 */
	public boolean push(T t) {
		boolean result = false;
		// 入队列
		result = queue.offer(t);

		// 工作线程负载均衡
		lock.lock();
		try {
			if (status != RunningStatus.STARTED) {
				return result;
			}
			for (int i = 0; i < workers; i++) {
				WaitLock waitLock = lockList.get(workerIndex);
				R runnable = workerList.get(workerIndex);
				workerIndex = (workerIndex + 1) % workers;
				// 仅唤醒等待状态的工作线程
				if (runnable.getStatus() == RunningStatus.WAITING) {
					waitLock.wakeAll();
					break;
				}
			}
		} finally {
			lock.unlock();
		}
		return result;
	}
}
