package com.dph.common.concurrent.service;

import java.util.concurrent.locks.Lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dph.common.cache.constEnum.RunningStatus;
import com.dph.common.concurrent.lock.WaitLock;

/**
 * 工作线程
 * 
 * @author peihuadeng
 *
 */
public abstract class QueueServiceRunnable<T> implements Runnable {

	private final Logger logger = LoggerFactory.getLogger(QueueServiceRunnable.class);

	private volatile RunningStatus status;// 工作线程内置状态:RUNNING, STOPPING, WAITING, STOPPED
	private final Lock lock;// 内置锁对象
	private final WaitLock waitLock;// 内置锁对象
	private final QueueService<T, ? extends QueueServiceRunnable<T>> queueService;
	protected final long waitTime;// 等待时间，单位：毫秒。默认：5000

	/**
	 * 初始化，传入内置锁对象
	 * 
	 * @param lock
	 */
	public QueueServiceRunnable(WaitLock waitLock, QueueService<T, ? extends QueueServiceRunnable<T>> queueService) {
		this.waitLock = waitLock;
		this.lock = waitLock.getLock();
		this.queueService = queueService;
		this.waitTime = 5000;
	}

	/**
	 * 初始化，传入内置锁对象
	 * 
	 * @param lock
	 */
	public QueueServiceRunnable(WaitLock waitLock, QueueService<T, ? extends QueueServiceRunnable<T>> queueService, long waitTime) {
		this.waitLock = waitLock;
		this.lock = waitLock.getLock();
		this.queueService = queueService;
		this.waitTime = waitTime;
	}

	/**
	 * 获取工作线程内置状态
	 * 
	 * @return
	 */
	protected RunningStatus getStatus() {
		return status;
	}

	/**
	 * 停止工作线程，不保证立即停止
	 */
	protected void destory() {
		logger.info("queue service runnable stopping...");
		lock.lock();
		try {
			if (this.status != RunningStatus.RUNNING && this.status != RunningStatus.WAITING) {
				logger.warn("queue service runnable has stopped.");
				return;
			}
			this.status = RunningStatus.STOPPING;
			this.waitLock.wakeAll();
		} finally {
			lock.unlock();
		}
	}

	protected T getObject() {
		T t = this.queueService.poll();
		return t;
	}

	/**
	 * 逻辑执行
	 */
	@Override
	public void run() {
		logger.info("queue service runnable started.");
		// 状态修改为正在运行
		lock.lock();
		try {
			this.status = RunningStatus.RUNNING;
		} finally {
			lock.unlock();
		}
		// 执行逻辑
		executeLoop();
		// 状态修改为已停止
		lock.lock();
		try {
			this.status = RunningStatus.STOPPED;
		} finally {
			lock.unlock();
		}
		logger.info("queue service runnable stopped.");
	}

	protected void executeLoop() {
		while (status == RunningStatus.RUNNING) {
			try {
				// 从队列中取出数据
				T t = getObject();
				if (t == null) {
					// 队列为空，则等待5s或被唤醒后继续执行
					lock.lock();
					try {
						if (status == RunningStatus.RUNNING) {
							status = RunningStatus.WAITING;
							waitLock.waitFor(this.waitTime);
						}
						if (status == RunningStatus.WAITING) {
							status = RunningStatus.RUNNING;
						}
					} finally {
						lock.unlock();
					}
				} else {
					execute(t);
				}
			} catch (Exception e) {
				logger.warn("error occurs when waitting", e);
				lock.lock();
				try {
					if (status == RunningStatus.WAITING) {
						status = RunningStatus.RUNNING;
					}
				} finally {
					lock.unlock();
				}
			}
		}
	}

	protected void execute(T t) {
		throw new RuntimeException("the execute method does not implement");
	}

}