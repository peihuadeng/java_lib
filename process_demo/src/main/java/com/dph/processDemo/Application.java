package com.dph.processDemo;

import com.dph.processDemo.common.application.BaseApplication;
import com.dph.processDemo.system.service.DemoService;

/**
 * 程序入口
 *
 */
public class Application extends BaseApplication {

	public Application() {
		super(DemoService.class);
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Incorrect command. please input start|stop|restart.");
			return;
		}

		Application application = new Application();
		switch (args[0].toLowerCase()) {
		case "start":
			application.start();
			break;
		case "stop":
			application.stop();
			break;
		case "restart":
			application.stop();
			application.start();
			break;
		default:
			System.err.println(String.format("Incorrect command[%s]. please input start|stop|restart.", args[0]));
			break;
		}
	}
}