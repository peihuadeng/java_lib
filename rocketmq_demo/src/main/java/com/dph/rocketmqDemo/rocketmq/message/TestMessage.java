/**
 * @author peihuadeng
 *
 */
package com.dph.rocketmqDemo.rocketmq.message;

import java.io.UnsupportedEncodingException;

import com.alibaba.rocketmq.common.message.Message;

/**
 * 测试消息
 * 
 * @author peihuadeng
 *
 */
public class TestMessage extends Message {

	private static final long serialVersionUID = 1L;

	public TestMessage(String content) throws UnsupportedEncodingException {
		super("testTopic", "push", content.getBytes("UTF-8"));
	}

}
