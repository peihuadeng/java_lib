package com.dph.common.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 锁等待对象 使用条件变量进行等待\唤醒操作
 * 
 * @author peihuadeng
 *
 */
public class WaitLock {

	private final static Logger logger = LoggerFactory.getLogger(WaitLock.class);
	private final Lock lock;// 目标锁对象
	private final Condition condition;// 条件变量

	/**
	 * 初始化 使用内置锁对象及其条件变量
	 */
	public WaitLock() {
		lock = new ReentrantLock();
		condition = lock.newCondition();
	}

	/**
	 * 初始化 使用传入锁对象创建条件变量
	 * 
	 * @param lock
	 */
	public WaitLock(Lock lock) {
		if (lock == null) {
			lock = new ReentrantLock();
		}

		this.lock = lock;
		condition = lock.newCondition();
	}

	/**
	 * 获取生成条件变量的锁
	 * 
	 * @return
	 */
	public Lock getLock() {
		return lock;
	}

	/**
	 * 等待timeout毫秒或被其他线程唤醒
	 * 
	 * @param timeout
	 * @return
	 */
	public boolean waitFor(long timeout) {
		lock.lock();
		try {
			condition.await(timeout, TimeUnit.MILLISECONDS);
			return true;
		} catch (InterruptedException e) {
			logger.warn("fail to go in to wait-timeout status", e);
			return false;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 等待直到被其他线程唤醒
	 * 
	 * @return
	 */
	public boolean waitFor() {
		lock.lock();
		try {
			condition.await();
			return true;
		} catch (InterruptedException e) {
			logger.warn("fail to go in to wait status", e);
			return false;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 唤醒等待条件变量对象的其中一个线程
	 */
	public void wake() {
		lock.lock();
		try {
			condition.signal();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 唤醒等待条件变量对象的所有线程
	 */
	public void wakeAll() {
		lock.lock();
		try {
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}

}
