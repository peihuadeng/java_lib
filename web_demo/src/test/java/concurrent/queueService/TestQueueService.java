package concurrent.queueService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

public class TestQueueService {

	private AtomicInteger totalCount = new AtomicInteger(0);
	private AtomicInteger totalError = new AtomicInteger(0);

	public TestQueueService() {
	}

	public void mainTest(int workers, long seconds) throws InterruptedException {
		MyQueueService.getInstance().start(5);// 启动队列服务

		long startTime = System.currentTimeMillis();
		ExecutorService service = Executors.newFixedThreadPool(workers);
		List<TestRunnable> workerList = new ArrayList<TestRunnable>();

		for (int i = 0; i < workers; i++) {
			TestRunnable runnable = new TestRunnable();
			service.submit(runnable);
			workerList.add(runnable);
		}

		Thread.sleep(seconds * 1000);

		for (TestRunnable runnable : workerList) {
			runnable.destory();
		}

		service.shutdownNow();

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(String.format("requests: %d\ntimeout: %d\nconsume time: %d\nRPS: %.3f",
				totalCount.get(), totalError.get(), totalTime, (double) totalCount.get() * 1000 / totalTime));

		MyQueueService.getInstance().stop();// 停止队列服务
	}

	/**
	 * 统计数据量
	 * 
	 * @param count
	 */
	private void statistics(int count) {
		totalCount.addAndGet(count);
	}

	/**
	 * 统计错误量
	 * 
	 * @param count
	 */
	private void statisticsError(int count) {
		totalError.addAndGet(count);
	}

	/**
	 * 测试线程
	 * 
	 * @author root
	 *
	 */
	private class TestRunnable implements Runnable {

		private final Logger logger = Logger.getLogger(TestRunnable.class);

		/**
		 * 线程状态
		 */
		public final static byte STATUS_RUNNING = 0;
		public final static byte STATUS_STOPPED = 1;

		private byte status = STATUS_STOPPED;

		public TestRunnable() {
			status = STATUS_RUNNING;// 初始化：正在运行
		}

		/**
		 * 停止线程
		 */
		public void destory() {
			status = STATUS_STOPPED;
		}

		@Override
		public void run() {
			while (status == STATUS_RUNNING) {
				try {
					MyQueueService.getInstance().push("test");
					System.err.println("input test");
					statistics(1);
				} catch (Exception e) {
					statisticsError(1);
					logger.warn("error occurs when testing", e);
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		TestQueueService serviceTest = new TestQueueService();
		serviceTest.mainTest(3, 5);
	}

}
