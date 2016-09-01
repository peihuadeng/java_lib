package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class TestConcurrent {
	
	private class MyRunnable implements Runnable {

		@Override
		public void run() {
			int rand = (int) (Math.random() * 10000);
			System.out.println(rand + ": start");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(rand + ": end");
		}
	}
	
	@Test
	public void mainTest() throws InterruptedException {
		ExecutorService service = Executors.newCachedThreadPool();
		MyRunnable runnable = new MyRunnable();
		for (int i = 0; i < 5; i ++) {
			service.execute(runnable);
		}

		Thread.sleep(10000);
		
	}

}
