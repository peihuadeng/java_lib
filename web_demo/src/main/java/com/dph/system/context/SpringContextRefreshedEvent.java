package com.dph.system.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
public class SpringContextRefreshedEvent implements ApplicationListener<ContextRefreshedEvent> {

	private final static Logger logger = LoggerFactory.getLogger(SpringContextRefreshedEvent.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			logger.debug("spring root context refresh event");
		} else {
			logger.debug("spring context refresh event");
		}
	}

}
