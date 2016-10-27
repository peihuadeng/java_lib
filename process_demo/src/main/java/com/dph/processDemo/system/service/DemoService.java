/**
 * @author peihuadeng
 *
 */
package com.dph.processDemo.system.service;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dph.processDemo.common.service.BaseService;

/**
 * 测试demo
 * 
 * @author peihuadeng
 *
 */
public class DemoService extends BaseService {

	private final static Logger logger = LoggerFactory.getLogger(DemoService.class);
	public static boolean flag = true;
	private final Thread thread;

	public DemoService() {
		thread = new Thread() {
			@Override
			public void run() {
				long i = 0;
				while (DemoService.flag) {
					i++;
					logger.info(String.valueOf(i));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}

	@Override
	protected void onStart() {
		thread.start();
	}

	@Override
	protected void onStop() {
		flag = false;
	}

}
