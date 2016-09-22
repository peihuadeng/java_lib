package concurrent.queueService;

import com.dph.common.concurrent.lock.WaitLock;
import com.dph.common.concurrent.service.QueueService;
import com.dph.common.concurrent.service.QueueServiceRunnable;

public class MyQueueServiceRunnable extends QueueServiceRunnable<String> {
	
//	private final static Logger logger = LoggerFactory.getLogger(MyQueueServiceRunnable.class);

	public MyQueueServiceRunnable(WaitLock lock, QueueService<String, ? extends QueueServiceRunnable<String>> queueService) {
//		super(lock, queueService);
		super(lock, queueService, 60000);
	}

	@Override
	protected void execute(String str) {
		System.out.println("print: " + str);
	}

}