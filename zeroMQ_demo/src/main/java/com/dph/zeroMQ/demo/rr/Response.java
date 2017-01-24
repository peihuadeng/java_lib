package com.dph.zeroMQ.demo.rr;

import org.zeromq.ZMQ;

/**
 * 这是1:1模型
 * @author peihuadeng
 *
 */
public class Response {
	public static void main(String[] args) {
		ZMQ.Context context = ZMQ.context(1); // 这个表示创建用于一个I/O线程的context
		ZMQ.Socket socket = context.socket(ZMQ.REP); // 创建一个response类型的socket，它可以接收request发送过来的请求，其实可以将其简单的理解为服务端
		socket.bind("tcp://*:5555"); // 绑定端口

		int index = 0;
		while (!Thread.currentThread().isInterrupted()) {
			byte[] request = socket.recv(); // 获取request发送过来的数据
			index++;
			System.out.println(String.format("%s : %s", index, new String(request)));
			String response = "response from server : test";
			socket.send(response); // 向request端发送数据 ，必须要request端返回数据，没有返回就又recv，将会出错，这里可以理解为强制要求走完整个request/response流程
		}
		socket.close(); // 先关闭socket
		context.term(); // 关闭当前的上下文
	}
}