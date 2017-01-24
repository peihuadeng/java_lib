package com.dph.zeroMQ.demo.sp;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class Publisher {

	public static void main(String[] args) throws InterruptedException {
		Context context = ZMQ.context(1);
		Socket publisher = context.socket(ZMQ.PUB);
		publisher.bind("tcp://*:5557");
		while (true) {
			Thread.sleep(1000);
			publisher.send("A".getBytes(), ZMQ.SNDMORE);
			publisher.send("This is A".getBytes(), 0);
			publisher.send("B".getBytes(), ZMQ.SNDMORE);
			publisher.send("This is B".getBytes(), 0);
		}
	}

}