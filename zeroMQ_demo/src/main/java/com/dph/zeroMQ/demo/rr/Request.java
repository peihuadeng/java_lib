package com.dph.zeroMQ.demo.rr;

import org.zeromq.ZMQ;

public class Request {
	public static void main(String args[]) {
		ZMQ.Context context = ZMQ.context(1); // 创建一个I/O线程的上下文
		ZMQ.Socket socket = context.socket(ZMQ.REQ); // 创建一个request类型的socket，这里可以将其简单的理解为客户端，用于向response端发送数据

		socket.connect("tcp://localhost:5555"); // 与response端建立连接
		long now = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			String request = "request from client - hello";
			socket.send(request); // 向reponse端发送数据
			byte[] response = socket.recv(); // 接收response发送回来的数据, 正在request/response模型中，send之后必须要recv之后才能继续send，这可能是为了保证整个request/response的流程走完
			System.out.println("receive : " + new String(response));
		}
		long after = System.currentTimeMillis();

		System.out.println((after - now) / 1000);
	}
}