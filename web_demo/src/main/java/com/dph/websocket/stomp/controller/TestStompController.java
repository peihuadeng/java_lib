package com.dph.websocket.stomp.controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dph.websocket.stomp.entity.Topic;
import com.dph.websocket.stomp.service.PublishService;

@Controller
@RequestMapping("/websocket")
public class TestStompController {
	
	@RequestMapping("/stomp")
	public String stomp() {
		String destPage = "/websocket/stomp";
		
		return destPage;
	}
	
	@RequestMapping("/testStomp")
	public String testStomp() {
		String destPage = "/websocket/testStomp";
		
		return destPage;
	}
	
	@RequestMapping("/testStompIframe")
	public String testStompIframe() {
		String destPage = "/websocket/testStompIframe";
		
		return destPage;
	}
	
	@MessageMapping("/notice/template")
	public void noticeTemplate(String value) {
		System.out.println("message mapping: /notice/template, message:  " + value);
		Topic topic = new Topic();
		topic.setDestination("/topic/notice");
		topic.setMessage("template send : " + value);
		PublishService.getInstance().publishTopic(topic);
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
		publishTopicRunnable = new PublishTopicRunnable();
		publishTopicRunnable.init();
		Thread sendThread = new Thread(publishTopicRunnable);
		sendThread.start();
	}

	@PreDestroy
	public void destory() {
		publishTopicRunnable.destory();
	}
	
	private PublishTopicRunnable publishTopicRunnable;

	private class PublishTopicRunnable implements Runnable {

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
				
				Topic topic = new Topic();
				topic.setDestination("/topic/notice");
				topic.setMessage("heartbeat");
				PublishService.getInstance().publishTopic(topic);
			}
		}
	}
}
