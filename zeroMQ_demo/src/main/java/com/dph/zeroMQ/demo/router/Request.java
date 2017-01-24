package com.dph.zeroMQ.demo.router;

import org.zeromq.ZMQ;

public class Request {
	public static void main(String args[]) {
		for (int j = 0; j < 5; j++) {
			new Thread(new Runnable() {
				public void run() {
					ZMQ.Context context = ZMQ.context(1); // 创建一个I/O线程的上下文
					ZMQ.Socket socket = context.socket(ZMQ.REQ); // 创建一个request类型的socket，这里可以将其简单的理解为客户端，用于向response端发送数据

					socket.connect("ipc://localhost:5555"); // 与response端建立连接
					long now = System.currentTimeMillis();
					for (int i = 0; i < 1000; i++) {
						String request = "hello";
						socket.send(request); // 向reponse端发送数据
						byte[] response = socket.recv(); // 接收response发送回来的数据
															// 正在request/response模型中，send之后必须要recv之后才能继续send，这可能是为了保证整个request/response的流程走完
						System.out.println("receive : " + new String(response));
					}
					long after = System.currentTimeMillis();

					System.out.println((after - now) / 1000);
				}

			}).start();
			;
		}

	}
}