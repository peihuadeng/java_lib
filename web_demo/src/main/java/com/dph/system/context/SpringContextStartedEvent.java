package com.dph.system.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
public class SpringContextStartedEvent implements ApplicationListener<ContextStartedEvent> {

	private final static Logger logger = LoggerFactory.getLogger(SpringContextStartedEvent.class);

	@Override
	public void onApplicationEvent(ContextStartedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			logger.debug("spring root context started event");
		} else {
			logger.debug("spring context started event");
		}

	}

}
