/**
 * @author peihuadeng
 *
 */
package com.dph.rocketmqDemo.rocketmq;

import java.nio.charset.Charset;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

/**
 * rocketmq 生产者 单例
 * 
 * @author peihuadeng
 *
 */
public class RocketMQClient {

	private final static Logger logger = LoggerFactory.getLogger(RocketMQClient.class);

	private static RocketMQClient instance;
	private final static Lock staticLock = new ReentrantLock();
	private final String rocketmqAddr;// 地址
	private final String producerGroup;// 生产者组名
	private final DefaultMQProducer producer;// 生产者

	/**
	 * 初始化
	 */
	private RocketMQClient() {
		logger.debug("rocketmq client init...");
		// 可改成配置文件配置
		rocketmqAddr = "172.31.0.3:9876";
		producerGroup = "producer";
		producer = new DefaultMQProducer(producerGroup);
		producer.setNamesrvAddr(rocketmqAddr);
		producer.setRetryAnotherBrokerWhenNotStoreOK(true);
		logger.info(String.format("rocketmq client initialized. address[%s], producer group[%s]", rocketmqAddr, producerGroup));
	}

	/**
	 * 单例
	 * 
	 * @return
	 */
	public static RocketMQClient getIntance() {
		staticLock.lock();
		try {
			if (instance == null) {
				instance = new RocketMQClient();
			}
			return instance;
		} finally {
			staticLock.unlock();
		}
	}

	/**
	 * 启动
	 */
	public void start() {
		try {
			logger.info("rocketmq client starting...");
			producer.start();
			logger.info("rocketmq client started.");
		} catch (MQClientException e) {
			throw new RuntimeException("fail to start rocket client.", e);
		}
	}

	/**
	 * 停止
	 */
	public void stop() {
		logger.info("rocketmq client stopping...");
		producer.shutdown();
		logger.info("rocketmq client stopped.");
		staticLock.lock();
		try {
			instance = null;
		} finally {
			staticLock.unlock();
		}
	}

	/**
	 * 发送消息到rocketmq
	 * 
	 * @param message
	 * @return
	 */
	public boolean send(Message message) {
		try {
			logger.debug(String.format("send message to rocketmq. topic[%s], tags[%s], content[%s]", //
					message.getTopic(), message.getTags(), new String(message.getBody(), Charset.forName("UTF-8"))));
			SendResult result = producer.send(message);
			logger.debug(String.format("finish to send message to rocketmq. message id[%s], result[%s]", //
					result.getMsgId(), result.getSendStatus()));
			switch (result.getSendStatus()) {
			case SEND_OK:
				return true;
			case FLUSH_DISK_TIMEOUT:
			case SLAVE_NOT_AVAILABLE:
			default:
				return false;
			}
		} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
			throw new RuntimeException("fail to send message to rocketmq.", e);
		}
	}

}
