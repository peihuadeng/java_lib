package ehcache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dph.ehcache.LocalCache;
import com.dph.ehcache.LocalCacheManager;
import com.dph.modules.student.entity.Student;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-context*.xml")
public class TestEhcache {
	
	private Logger logger = LoggerFactory.getLogger(TestEhcache.class);
	
	@Autowired
	private LocalCacheManager localCacheManager;
	
	@Test
	public void mainTest() throws InterruptedException, ExecutionException {
		//测试本地缓存
		int workers = 5;
		ExecutorService service = Executors.newFixedThreadPool(workers);
		List<Future<?>> futureList = new ArrayList<Future<?>>();
		for (int i = 0; i < workers; i ++) {
			service.execute(new GetCacheRunnable());
			Future<?> future = service.submit(new GetCacheRunnable());
			futureList.add(future);
		}
		
		for (Future<?> future : futureList) {
			logger.info("result: " + future.get());
		}
	}
	
	private class GetCacheRunnable implements Runnable {

		public final static byte STATUS_RUNNING = 0;
		public final static byte STATUS_STOPPED = 1;

		private byte status = STATUS_RUNNING;
		private final static int threshold = 10000;
		private int times;

		public void run() {
			while (status == STATUS_RUNNING) {
				LocalCache<Student> cache = localCacheManager.getAndCreatePersistentCache("test", Student.class);
				logger.info(cache.toString());
				times ++;
				if (times >= threshold) {
					status = STATUS_STOPPED;
				}
			}
		}
	}

}
