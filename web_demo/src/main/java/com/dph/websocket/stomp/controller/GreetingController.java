package com.dph.websocket.stomp.controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/websocket")
public class GreetingController {
	
	private DispatchTopicRunnable dispatchTopicRunnable;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@RequestMapping("/stomp")
	public String stomp() {
		String destPage = "/websocket/stomp";
		
		return destPage;
	}
	
	@MessageMapping("/notice/template")
	public void noticeTemplate(String value) {
		System.out.println("message mapping: /notice/template, message:  " + value);
		simpMessagingTemplate.convertAndSend("/topic/notice", "template send : " + value);
	}

	@MessageMapping("/notice/annotation")
	@SendTo("/topic/notice")
	public String noticeAnnotation(String value) {
		System.out.println("message mapping: /notice/annotation, message: " + value);
		return "annotation send : " + value;
	}
	
	@SubscribeMapping("/init")
	public String subscribe() {
		System.out.println("subscribe topic: /topic/notice");
		
		return "subscribe topic success: /init";
	}
	
	@PostConstruct
	public void init() {
		dispatchTopicRunnable = new DispatchTopicRunnable();
		dispatchTopicRunnable.init();
		Thread sendThread = new Thread(dispatchTopicRunnable);
		sendThread.start();
	}

	@PreDestroy
	public void destory() {
		dispatchTopicRunnable.destory();
	}

	private class DispatchTopicRunnable implements Runnable {

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
				
//				simpMessagingTemplate.convertAndSend("/topic/notice", "heardbeat");

			}
		}
	}
}
