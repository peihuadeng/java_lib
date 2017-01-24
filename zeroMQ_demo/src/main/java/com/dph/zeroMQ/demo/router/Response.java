package com.dph.zeroMQ.demo.router;

import org.zeromq.ZMQ;

public class Response {
	public static void main(String args[]) {
		final ZMQ.Context context = ZMQ.context(1);
		ZMQ.Socket router = context.socket(ZMQ.ROUTER);
		ZMQ.Socket dealer = context.socket(ZMQ.DEALER);

		router.bind("ipc://localhost:5555");//增加router和dealer，实现n:m模型
		dealer.bind("ipc://localhost:5556");

		for (int i = 0; i < 20; i++) {
			new Thread(new Runnable() {

				public void run() {
					ZMQ.Socket response = context.socket(ZMQ.REP);
					response.connect("ipc://localhost:5556");
					while (!Thread.currentThread().isInterrupted()) {
						byte[] b = response.recv();
						System.out.println(new String(b, ZMQ.CHARSET));
						response.send("hello".getBytes());
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					response.close();
				}

			}).start();
		}
		ZMQ.proxy(router, dealer, null);
		router.close();
		dealer.close();
		context.term();
	}
}
