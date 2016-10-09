package concurrent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpConcurrentTest {

	private AtomicInteger totalCount = new AtomicInteger(0);
	private AtomicInteger totalError = new AtomicInteger(0);

	public HttpConcurrentTest() {
	}

	public void mainTest(int workers, long seconds) throws InterruptedException {
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

		System.out.println(String.format("requests: %d\ntimeout: %d\nconsume time: %dms\nRPS: %.3f req/s", totalCount.get(), totalError.get(),
				totalTime, (double) totalCount.get() * 1000 / totalTime));
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

		private final Logger logger = LoggerFactory.getLogger(TestRunnable.class);
		private final static String URL = "http://localhost:8080/est/updateIntegral.action";

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
				int i = 1;
				try {
					CloseableHttpClient httpClient = HttpClients.createDefault();
					HttpPost post = new HttpPost(URL);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					String jsonParame = "0rgYYBTMEzcQ/r+qXvnInzGqQGMU9QI0ME8yylG9jCaxCsD+5YShxDS4HICK 1d8w9XKZDBiMksPGDREZkXOg4fmbZwA4NtcEdWcEmI3klpJPkdQcsVrxkAAE yGiNII3c8XMMCC7rvqxYyCAtH9LMX7N3cojynm5j76By8yGcZ5FgUSCMQ6vn P0q6jEn2edwxi3Q2VXYPdkrnCxk7x3BzcFGlPzUxcxtfULTwWvGm1XQPBcco YicU+MtWM0Rp4QL8bKbJi837lzA=";
					params.add(new BasicNameValuePair("jsonParame", jsonParame));
					UrlEncodedFormEntity entity = null;
					try {
						entity = new UrlEncodedFormEntity(params, "UTF-8");
						post.setEntity(entity);
						CloseableHttpResponse response = httpClient.execute(post);
						try {
							if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//								HttpEntity responseEntity = response.getEntity();
								statistics(i);
								i--;
							} else {
								statisticsError(i);
								i--;
							}
						} finally {
							response.close();
						}
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						// 关闭连接,释放资源
						try {
							httpClient.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					statisticsError(i);
					logger.warn("error occurs when testing", e);
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		HttpConcurrentTest serviceTest = new HttpConcurrentTest();
		serviceTest.mainTest(40, 60);
	}

}
