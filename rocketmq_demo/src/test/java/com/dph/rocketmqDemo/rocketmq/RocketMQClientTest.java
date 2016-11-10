/**
 * @author peihuadeng
 *
 */
package com.dph.rocketmqDemo.rocketmq;

import java.io.UnsupportedEncodingException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.alibaba.rocketmq.common.message.Message;
import com.dph.rocketmqDemo.rocketmq.message.TestMessage;

/**
 * @author peihuadeng
 *
 */
public class RocketMQClientTest {

	private static RocketMQClient client;

	@BeforeClass
	public static void init() {
		System.err.println("rocketmq init...");
		client = RocketMQClient.getIntance();
		client.start();
		System.err.println("rocketmq initialized.");
	}

	@AfterClass
	public static void destory() {
		client.stop();
		System.err.println("rocketmq stopped.");
	}

	@Test
	public void mainTest() throws UnsupportedEncodingException {
		String content = "abcde";
		Message message = new TestMessage(content);
		boolean success = client.send(message);
		System.err.println("send result: " + success);
	}
}
