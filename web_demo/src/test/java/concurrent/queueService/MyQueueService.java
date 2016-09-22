package concurrent.queueService;

import com.dph.common.concurrent.lock.WaitLock;
import com.dph.common.concurrent.service.QueueService;

public class MyQueueService extends QueueService<String, MyQueueServiceRunnable> {

	private static MyQueueService instance = null;// 单例引用

	private MyQueueService() {
	}

	/**
	 * 获取单例，线程安全操作
	 * 
	 * @return
	 */
	public static MyQueueService getInstance() {
		lock.lock();
		try {
			if (instance == null) {
				instance = new MyQueueService();
			}

			return instance;
		} finally {
			lock.unlock();
		}
	}

	@Override
	protected MyQueueServiceRunnable newRunnable(WaitLock waitLock) {
		return new MyQueueServiceRunnable(waitLock, this);
	}
}
