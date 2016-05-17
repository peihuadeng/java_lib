package com.dph.websocket.endpoints;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebsocketEndPoint extends TextWebSocketHandler {

	private Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<String, WebSocketSession>();
	private SendMessageRunnable sendMessageRunnable;

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		TextMessage returnMessage = new TextMessage(message.getPayload() + "received at server");
		session.sendMessage(returnMessage);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		System.out.println(exception.toString());
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessionMap.put(session.getId(), session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessionMap.remove(session.getId());
	}

	@PostConstruct
	public void init() {
		sendMessageRunnable = new SendMessageRunnable();
		sendMessageRunnable.init();
		Thread sendThread = new Thread(sendMessageRunnable);
		sendThread.start();
	}

	@PreDestroy
	public void destory() {
		sendMessageRunnable.destory();
	}

	private class SendMessageRunnable implements Runnable {

		public final static byte STATUS_RUNNING = 0;
		public final static byte STATUS_STOPPED = 1;

		private byte status = STATUS_STOPPED;

		public void init() {
			status = STATUS_RUNNING;
		}

		public void destory() {
			status = STATUS_STOPPED;
		}

		public void run() {
			while (status == STATUS_RUNNING) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				for (WebSocketSession session : sessionMap.values()) {
					try {
						if (session.isOpen()) {
							session.sendMessage(new TextMessage("heartbeat"));
						} else {
							sessionMap.remove(session.getId());
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
