package com.dph.zeroMQ.demo.pipeLine;

import org.zeromq.ZMQ;

public class MainPusher {
	public static void main(String[] args) throws InterruptedException {
		// 参数代表使用多少线程，大多数情况下，1个线程已经足够。
		ZMQ.Context context = ZMQ.context(1);
		// 指定模式为Pusher
		ZMQ.Socket socket = context.socket(ZMQ.PUSH);
		socket.bind("tcp://127.0.0.1:5557"); // 绑定服务地址及端口
		for (;;) {
			long time = System.nanoTime();
			socket.send(String.valueOf(time));
			System.out.println("发布了新消息，时间：" + time);
			Thread.sleep(10000);
		}
	}
}