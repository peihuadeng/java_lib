package com.dph.websocket.stomp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.dph.common.utils.StringUtils;
import com.dph.websocket.stomp.entity.Topic;

/**
 * 发布主题服务：单例
 * @author root
 *
 */
public class PublishService {
	
	private final static Logger logger = LoggerFactory.getLogger(DispatchTopicRunnable.class);
	
	private SimpMessagingTemplate simpMessagingTemplate = null;
	private BlockingQueue<Topic> topicQueue = null;
	private List<DispatchTopicRunnable> workerList = null;
	private int workers = 5;
	private volatile int workerIndex = 0;
	
	private static PublishService instance = null;
	
	/**
	 * 构造函数
	 */
	private PublishService() {
		topicQueue = new LinkedBlockingQueue<Topic>();
		workerList = new ArrayList<DispatchTopicRunnable>();
	}
	
	/**
	 * 获取单例
	 * @return
	 */
	public synchronized static PublishService getInstance() {
		if (instance == null) {
			instance = new PublishService();
		}
		
		return instance;
	}
	/**
	 * 启动，定义消息模板及发布线程数
	 * @param simpMessagingTemplate
	 * @param workers
	 */
	public synchronized void start(SimpMessagingTemplate simpMessagingTemplate, int workers) {
		this.simpMessagingTemplate = simpMessagingTemplate;
		this.workers = workers;
		this.workerIndex = 0;
		for (int i = 0; i < this.workers; i ++) {
			//构造发布线程
			DispatchTopicRunnable dispatchTopicRunnable = new DispatchTopicRunnable();
			workerList.add(dispatchTopicRunnable);
			Thread thread = new Thread(dispatchTopicRunnable, "dispatch_topic_thread_" + i);
			thread.start();
		}
	}
	/**
	 * 启动，定义消息模板
	 * @param simpMessagingTemplate
	 */
	public synchronized void start(SimpMessagingTemplate simpMessagingTemplate) {
		this.start(simpMessagingTemplate, this.workers);
	}
	/**
	 * 停止
	 */
	public synchronized void stop() {
		//停止每个发布线程
		for (DispatchTopicRunnable dispatchTopicRunnable : workerList) {
			dispatchTopicRunnable.destory();
		}
	}
	/**
	 * 发布主题，仅把主题对象放入队列并唤醒对应线程后，立即返回
	 * @param topic
	 * @return
	 */
	public synchronized boolean publishTopic(Topic topic) {
		boolean result = false;
		try {
			//放入队列
			result = topicQueue.offer(topic, 500, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			logger.error("fail to add topic into queue", e);
		}
		
		if (workerList.size() == 0) {
			return result;
		}
		
		DispatchTopicRunnable dispatchTopicRunnable = workerList.get(workerIndex);
		synchronized (dispatchTopicRunnable) {
			//唤醒对应线程
			dispatchTopicRunnable.notifyAll();
		}
		workerIndex = (workerIndex + 1) % workers;
		
		return result;
	}
	/**
	 * 从队列中获取主题
	 * @return
	 */
	private synchronized Topic pollTopic() {
		return topicQueue.poll();
	}

	/**
	 * 发布线程
	 * @author root
	 *
	 */
	private class DispatchTopicRunnable implements Runnable {
		
		private final Logger logger = LoggerFactory.getLogger(DispatchTopicRunnable.class);

		/**
		 * 线程状态
		 */
		public final static byte STATUS_RUNNING = 0;
		public final static byte STATUS_STOPPED = 1;

		private byte status = STATUS_RUNNING;//初始化：正在运行

		/**
		 * 停止线程
		 */
		public void destory() {
			status = STATUS_STOPPED;
			this.notifyAll();
		}

		@Override
		public void run() {
			while (status == STATUS_RUNNING) {
				try {
					//获取主题
					Topic topic = pollTopic();
					if (topic != null) {
						//有主题则发布，发布完成后继续在下一while循环获取主题
						//TODO:将主题消息转换成JSON
						if (StringUtils.isNotBlank(topic.getUserName())) {
							simpMessagingTemplate.convertAndSendToUser(topic.getUserName(), topic.getDestination(), topic.getMessage());
						} else {
							simpMessagingTemplate.convertAndSend(topic.getDestination(), topic.getMessage());
						}
					} else {
						//无主题则进入等待状态
						synchronized (this) {
							//等待5s或被唤醒
							this.wait(5000);							
						}
					}
				} catch (MessagingException e) {
					logger.error("fail to send stomp message", e);
				} catch (InterruptedException e) {
					logger.error("error occur when in waiting", e);
				}
			}
		}
	}

}
