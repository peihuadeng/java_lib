package com.dph.zeroMQ.demo.pipeLine;

import org.zeromq.ZMQ;

public class Sinker {
	public static void main(String[] args) {
		ZMQ.Context context = ZMQ.context(1);
		// 指定模式为pull模式
		ZMQ.Socket receiver = context.socket(ZMQ.PULL);
		receiver.bind("tcp://127.0.0.1:5558");
		for (;;) {
			byte[] recs = receiver.recv();
			long receiveTime = System.nanoTime();
			String oriMsg = new String(recs);
			String msg = new String(recs, 1, recs.length - 1);
			long pubTime = Long.valueOf(msg);
			long costTime = receiveTime - pubTime;
			System.out.println("Receive: " + oriMsg + " Cost time: " + costTime);
		}
	}
}