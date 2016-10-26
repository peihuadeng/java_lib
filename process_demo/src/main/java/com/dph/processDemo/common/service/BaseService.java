/**
 * @author peihuadeng
 *
 */
package com.dph.processDemo.common.service;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import com.dph.processDemo.common.application.BaseApplication;

/**
 * @author peihuadeng
 *
 */
public class BaseService {

	// private final static Logger logger = LoggerFactory.getLogger(BaseService.class);

	public static boolean flag = true;

	public static void main(String[] args) {
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		String name = runtime.getName();
		int index = name.indexOf("@");
		int pid = -1;
		if (index != -1) {
			pid = Integer.parseInt(name.substring(0, index));
		}

		String successFlag = BaseApplication.START_SUCCESS_FLAG.replaceFirst(BaseApplication.PID_KEY, String.valueOf(pid));
		String failFlag = BaseApplication.START_FAIL_FLAG;
		System.out.println(successFlag);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				BaseService.flag = false;
				System.out.println("shutdown");
				File file = new File("d:/a.txt");
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		while (BaseService.flag) {
			System.out.println("a");
		}
	}

}
