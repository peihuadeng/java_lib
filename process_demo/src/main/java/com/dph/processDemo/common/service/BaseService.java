/**
 * @author peihuadeng
 *
 */
package com.dph.processDemo.common.service;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dph.processDemo.common.application.BaseApplication;
import com.dph.processDemo.common.cache.constEnum.RunningStatus;

/**
 * 基础服务类：启动或停止服务、构造服务
 * 
 * @author peihuadeng
 *
 */
public abstract class BaseService {

	private final static Logger logger = LoggerFactory.getLogger(BaseService.class);
	private final static Lock lock = new ReentrantLock();
	private volatile RunningStatus status;// 转发服务状态:STARTING, STARTED, STOPPING, STOPPED

	/**
	 * 必须有默认构造函数
	 */
	public BaseService() {
		status = RunningStatus.STOPPED;
	}

	/**
	 * 启动服务
	 * 
	 * @return
	 */
	public final boolean start() {
		logger.info(String.format("%s starting...", this.getClass().getSimpleName()));
		lock.lock();
		try {
			if (status != RunningStatus.STOPPED) {
				logger.warn(String.format("%s has started.", this.getClass().getSimpleName()));
				return true;
			}
			status = RunningStatus.STARTING;
			this.onStart();
			status = RunningStatus.STARTED;
		} catch (Exception e) {
			logger.error(String.format("%s fail to start.", this.getClass().getSimpleName()), e);
			// 启动失败，则停止服务
			stop();
			return false;
		} finally {
			lock.unlock();
		}
		logger.info(String.format("%s started.", this.getClass().getSimpleName()));
		return true;
	}

	/**
	 * 停止服务
	 */
	public final void stop() {
		logger.info(String.format("%s stopping...", this.getClass().getSimpleName()));
		lock.lock();
		try {
			if (status != RunningStatus.STARTED && status != RunningStatus.STARTING) {
				logger.warn(String.format("%s has stopped.", this.getClass().getSimpleName()));
				return;
			}
			status = RunningStatus.STOPPING;
			this.onStop();
			status = RunningStatus.STOPPED;
		} finally {
			lock.unlock();
		}
		logger.info(String.format("%s stopped.", this.getClass().getSimpleName()));
	}

	/**
	 * 构造服务
	 * 
	 * @param serviceName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings({ "unchecked" })
	public final static BaseService newService(String serviceName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<? extends BaseService> serviceClass = (Class<? extends BaseService>) Class.forName(serviceName);
		BaseService service = serviceClass.newInstance();
		return service;
	}

	/**
	 * 启动基础服务:从后到前启动
	 */
	protected abstract void onStart();

	/**
	 * 停止相关服务：从前到后停止
	 */
	protected abstract void onStop();

	/**
	 * 主线程入口
	 * 
	 * @param args
	 */
	public final static void main(String[] args) {
		// 获取进程号
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		String name = runtime.getName();
		int index = name.indexOf("@");
		int pid = -1;
		if (index != -1) {
			pid = Integer.parseInt(name.substring(0, index));
		}
		// 成功和失败标识
		String successFlag = BaseApplication.START_SUCCESS_FLAG.replaceFirst(BaseApplication.PID_KEY, String.valueOf(pid));
		String failFlag = BaseApplication.START_FAIL_FLAG;
		// 没有服务类类型名称，则返回失败标识
		if (args.length < 1) {
			System.out.println(failFlag);
			logger.error("fail to init service. no service name.");
			return;
		}

		String serviceName = args[0];
		try {
			// 构造服务并启动
			BaseService service = BaseService.newService(serviceName);
			boolean success = service.start();
			if (success == true) {// 启动成功
				System.out.println(successFlag);
				// 程序钩子
				Runtime.getRuntime().addShutdownHook(new Thread() {
					@Override
					public void run() {
						service.stop();// 程序推出前，优雅停止服务
					}
				});
			} else {// 启动失败
				System.out.println(failFlag);
			}
		} catch (Throwable t) {// 启动异常
			System.out.println(failFlag);
			logger.error("error occurs when running in main function.", t);
		}
	}

}
