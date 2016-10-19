package concurrent.queueService;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.dph.common.concurrent.lock.WaitLock;
import com.dph.common.concurrent.service.QueueService;

public class MyQueueService extends QueueService<String, MyQueueServiceRunnable> {

	private static MyQueueService instance = null;// 单例引用
	private final static Lock staticLock = new ReentrantLock();// 单例控制锁

	/**
	 * 私有化构造
	 */
	private MyQueueService() {
	}

	/**
	 * 获取单例，线程安全操作
	 *
	 * @return
	 */
	public static MyQueueService getInstance() {
		staticLock.lock();
		try {
			if (instance == null) {
				instance = new MyQueueService();
			}

			return instance;
		} finally {
			staticLock.unlock();
		}
	}

	@Override
	protected MyQueueServiceRunnable newRunnable(WaitLock waitLock) {
		return new MyQueueServiceRunnable(waitLock, this);
	}
}
