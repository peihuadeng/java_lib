package com.dph.rocketmqDemo;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

public class Producer {

	public final static String ROCKETMQ_ADDR = "172.31.0.3:9876";

	public static void main(String[] args) {
		DefaultMQProducer producer = new DefaultMQProducer("Producer");// 生产者组名
		producer.setNamesrvAddr(ROCKETMQ_ADDR);
		try {
			producer.start();

			String pushMsg = "kafka activeMq rocketMq 消息队列使用1";
			Message msg = new Message("testTopic", "push", "1", pushMsg.getBytes("UTF-8"));
			SendResult result = producer.send(msg);
			System.out.println("id:" + result.getMsgId() + " result:" + result.getSendStatus());

			String pushMsg2 = "海量级消息记录单机测试2";
			msg = new Message("testTopic", "push", "2", pushMsg2.getBytes("UTF-8"));
			result = producer.send(msg);
			System.out.println("id:" + result.getMsgId() + " result:" + result.getSendStatus());

			String pushMsg3 = "海量级消息记录单机测试3";
			msg = new Message("testTopic", "push", "1", pushMsg3.getBytes());
			result = producer.send(msg);
			System.out.println("id:" + result.getMsgId() + " result:" + result.getSendStatus());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.shutdown();
		}
	}
}
