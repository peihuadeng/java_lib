package com.dph.rocketmqDemo;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;

public class Consumer {

	public final static String ROCKETMQ_ADDR = "172.31.0.3:9876";

	public static void main(String[] args) {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("Consumer");// 消费者组名
		consumer.setNamesrvAddr(ROCKETMQ_ADDR);
		try {
			consumer.subscribe("testTopic", "push");
			consumer.setMessageModel(MessageModel.BROADCASTING);// 广播模式：组内所有消费者都能收到
			/**
			 * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
			 * 如果非第一次启动，那么按照上次消费的位置继续消费
			 */
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			consumer.registerMessageListener(new MessageListenerConcurrently() {
				public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
					Message msg = list.get(0);
					System.out.println(msg.toString());
					String recString = null;
					try {
						recString = new String(msg.getBody(), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					System.out.println(recString);
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
			});
			consumer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}