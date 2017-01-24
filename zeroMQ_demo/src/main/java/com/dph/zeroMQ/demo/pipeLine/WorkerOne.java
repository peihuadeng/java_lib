package com.dph.zeroMQ.demo.pipeLine;

import org.zeromq.ZMQ;

public class WorkerOne {
	public static void main(String[] args) {
		// 指定模式为pull模式
		ZMQ.Socket receiver = ZMQ.context(1).socket(ZMQ.PULL);
		receiver.connect("tcp://127.0.0.1:5557");
		// 指定模式为push模式
		ZMQ.Socket sender = ZMQ.context(1).socket(ZMQ.PUSH);
		sender.connect("tcp://127.0.0.1:5558");
		for (;;) {
			byte[] recs = receiver.recv();
			long receiveTime = System.nanoTime();
			String oriMsg = new String(recs);
			long pubTime = Long.valueOf(oriMsg);
			long costTime = receiveTime - pubTime;
			System.out.println("Receive: " + oriMsg + " Cost time: " + costTime);
			sender.send("1" + oriMsg);
			System.out.println("Send to sinker.");
		}
	}
}