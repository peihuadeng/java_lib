package com.dph.system.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
public class SpringContextStoppedEvent implements ApplicationListener<ContextStoppedEvent> {

	private final static Logger logger = LoggerFactory.getLogger(SpringContextStoppedEvent.class);

	@Override
	public void onApplicationEvent(ContextStoppedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			logger.debug("spring root context stopped event");
		} else {
			logger.debug("spring context stopped event");
		}

	}

}
