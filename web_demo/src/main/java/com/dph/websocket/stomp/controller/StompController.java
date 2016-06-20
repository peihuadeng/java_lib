package com.dph.websocket.stomp.controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.dph.websocket.stomp.service.PublishService;

@Controller
public class StompController {
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@PostConstruct
	public void init() {
		PublishService.getInstance().start(simpMessagingTemplate);
	}

	@PreDestroy
	public void destory() {
		PublishService.getInstance().stop();
	}
}
