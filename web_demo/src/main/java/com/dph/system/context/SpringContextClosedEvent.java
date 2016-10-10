package com.dph.system.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import com.dph.common.utils.HttpClientUtils.HttpClientPool;

@Component
@Lazy(false)
public class SpringContextClosedEvent implements ApplicationListener<ContextClosedEvent> {

	private final static Logger logger = LoggerFactory.getLogger(SpringContextClosedEvent.class);

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			logger.debug("spring root context closed event");
			HttpClientPool.destory();
		} else {
			logger.debug("spring context closed event");
		}
	}

}
