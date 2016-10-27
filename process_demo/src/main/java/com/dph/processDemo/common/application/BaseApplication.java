/**
 * @author peihuadeng
 *
 */
package com.dph.processDemo.common.application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dph.processDemo.common.cache.constEnum.OSType;
import com.dph.processDemo.common.service.BaseService;
import com.dph.processDemo.common.util.OSUtils;

/**
 * 基础应用程序：启动或停止服务进程
 * 
 * @author peihuadeng
 *
 */
public abstract class BaseApplication {

	public final static String PID_KEY = "\\\\d\\+";// pid填充标识，用于填充启动成功返回标识
	public final static String START_SUCCESS_FLAG = "abcdefgh_\\d+_abcdefgh";// 启动成功返回标识，必须与启动失败返回标识，互不为子序列
	public final static String START_FAIL_FLAG = "hgfedcba";// 启动失败返回标识
	private final static String binPath = "bin";
	private final static String pidFileName = "pid.conf";
	private final File pidFile;// pid文件对象
	private final byte osType;// 操作系统类型
	private final Class<? extends BaseService> serviceClass;// 服务类类型

	/**
	 * 构造函数，必须传入服务类类型
	 * 
	 * @param serviceClass
	 */
	public BaseApplication(Class<? extends BaseService> serviceClass) {
		this.serviceClass = serviceClass;
		// 判断bin目录是否存在，不存在则创建
		File binDir = new File(binPath);
		if (binDir.exists() == false) {
			binDir.mkdirs();
		}
		pidFile = new File(binPath + File.separator + pidFileName);
		// 获取操作系统类型
		osType = OSUtils.getOSType();
	}

	/**
	 * 新建并保存pid文件
	 */
	private final void savePid(int pid) {
		BufferedWriter writer = null;
		try {
			pidFile.createNewFile();
			writer = new BufferedWriter(new FileWriter(pidFile));
			writer.write(String.valueOf(pid));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 移除pid文件
	 */
	private final void removePid() {
		if (pidFile != null && pidFile.exists() == true) {
			pidFile.delete();
		}
	}

	/**
	 * 从pid文件上获取pid
	 * 
	 * @return
	 */
	private final int getPid() {
		int pid = -1;
		// 判断pid文件是否存在
		if (pidFile.exists() == false) {
			return pid;
		}

		// 读取pid文件
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(pidFile));
			String line = reader.readLine();
			pid = Integer.valueOf(line);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return pid;
	}

	/**
	 * 判断指定pid进程是否正在运行
	 * 
	 * @param pid
	 *            进程号
	 * @return true：正在运行，false：已停止
	 */
	private final boolean isRunning(int pid) {
		boolean status = false;
		// 构造命令: jps -l
		List<String> commandList = new ArrayList<String>();
		commandList.add("jps");
		commandList.add("-l");
		ProcessBuilder builder = new ProcessBuilder(commandList);
		builder.redirectErrorStream(true);// 错误输出流重定向到标准输出流
		BufferedReader reader = null;
		Process process;
		try {
			process = builder.start();// 启动进程，运行命令
			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			// 逐行读取运行命令的输出内容
			while ((line = reader.readLine()) != null) {
				// 判断该行是否以${pid}开始；如果是，则表示程序正在启动
				if (line.startsWith(String.valueOf(pid)) == true) {
					status = true;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭进程的标准输出流
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return status;
	}

	/**
	 * 启动服务进程，并保存pid文件
	 */
	public final void start() {
		System.out.println("Application starting...");
		// 首先检查pid.conf文件，检测进程是否已启动
		int pid = getPid();
		if (pid >= 0 && isRunning(pid) == true) {
			System.out.println(String.format("Warning! Application has started! Pid: %d", pid));
			return;
		}
		// ${JAVA_HOME}/bin/java
		String java = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		// ${classPath}:包括所有引入的jar包、类路径
		String classPath = System.getProperty("java.class.path");

		// 构造命令: ${JAVA_HOME}/bin/java -classpath ${classPath} ${className} ${PARAM}...
		List<String> commandList = new ArrayList<String>();
		commandList.add(java);
		commandList.add("-classpath");
		commandList.add(classPath);
		commandList.add(BaseService.class.getName());
		commandList.add(serviceClass.getName());

		ProcessBuilder builder = new ProcessBuilder(commandList);
		builder.redirectErrorStream(true);// 错误输出流重定向到标准输出流
		BufferedReader reader = null;
		try {
			Process process = builder.start();// 启动进程
			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			boolean success = false;
			pid = -1;
			Pattern successPattern = Pattern.compile(BaseApplication.START_SUCCESS_FLAG);
			// 读取进程标准输出流
			while ((line = reader.readLine()) != null) {
				Matcher successMatcher = successPattern.matcher(line);
				if (successMatcher.find() == true) {// 启动成功
					// 获得成功返回的字符串
					String successContent = successMatcher.group(0);
					// 获得pid
					Pattern pidPattern = Pattern.compile("\\d+");
					Matcher pidMatcher = pidPattern.matcher(successContent);
					if (pidMatcher.find()) {
						// 成功获取pid
						pid = Integer.valueOf(pidMatcher.group(0));
					}

					success = true;
					System.out.println(String.format("Application started. Pid: %d", pid));
					break;
				} else if (line.contains(BaseApplication.START_FAIL_FLAG) == true) {// 启动失败
					success = false;
					System.out.println("Warning! Application fail to start!");
					break;
				}
			}
			// 启动成功，则保存pid文件
			if (success == true) {
				savePid(pid);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			// 关闭进程的标准输出流
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * linux:停止指定进程。执行 kill -15 ${pid}命令
	 * 
	 * @param pid
	 */
	private final void stopInLinux(int pid) {
		// 执行 kill -15 ${pid}命令
		List<String> stopCommand = new ArrayList<String>();
		stopCommand.add("kill");
		stopCommand.add("-15");
		stopCommand.add(String.valueOf(pid));
		ProcessBuilder stopBuilder = new ProcessBuilder(stopCommand);
		try {
			stopBuilder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * linux:强制停止指定进程。执行 kill -9 ${pid}命令
	 * 
	 * @param pid
	 */
	private final void killInLinux(int pid) {
		// 杀进程:执行 kill -9 ${pid}命令
		List<String> killCommand = new ArrayList<String>();
		killCommand.add("kill");
		killCommand.add("-9");
		killCommand.add(String.valueOf(pid));
		ProcessBuilder killBuilder = new ProcessBuilder(killCommand);
		try {
			killBuilder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * windows:强制停止指定进程。执行 taskkill /f /pid ${pid}命令
	 * 
	 * @param pid
	 */
	private final void killInWin(int pid) {
		// 杀进程:执行 taskkill /f /pid ${pid}命令
		List<String> killCommand = new ArrayList<String>();
		killCommand.add("taskkill");
		killCommand.add("/f");
		killCommand.add("/pid");
		killCommand.add(String.valueOf(pid));
		ProcessBuilder killBuilder = new ProcessBuilder(killCommand);
		try {
			killBuilder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 一段时间内持续检测指定进程是否正在运行
	 * 
	 * @param pid
	 * @return
	 */
	private final boolean continuityCheck(int pid) {
		// 循环检测进程是否停止
		long maxTime = 10l * 1000;// 持续时间10s
		long startTime = System.currentTimeMillis();
		boolean isRunning = true;
		// 循环
		while ((System.currentTimeMillis() - startTime) < maxTime) {
			try {
				Thread.sleep(200);// 200ms
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 判断进程是否停止
			if (isRunning(pid) == false) {
				isRunning = false;
				break;
			}
		}

		return isRunning;
	}

	/**
	 * 利用pid文件，实现停止程服务进程
	 */
	public final void stop() {
		System.out.println("Application stopping...");
		// 获取pid，并判断进程是否已停止
		int pid = getPid();
		if (pid < 0 || isRunning(pid) == false) {
			System.out.println("Warning! Application has stopped!");
			removePid();// 移除pid文件
			return;
		}

		// 根据操作系统不同，执行不同的停止进程命令
		if (osType == OSType.WINDOWS) {
			System.out.println("Warning! Killing program in windows.");
			killInWin(pid);
		} else {
			stopInLinux(pid); // linux：执行停止进程命令
		}
		boolean isRunning = continuityCheck(pid);
		// 成功停止
		if (isRunning == false) {
			System.out.println(String.format("Application stopped. Pid: %d", pid));
			removePid();// 移除pid文件
			return;
		}
		// 超时未停止，则杀进程。根据操作系统不同，执行不同的杀进程命令
		if (osType == OSType.WINDOWS) {
			killInWin(pid);
		} else {
			killInLinux(pid); // linux：执行停止进程命令
		}
		System.out.println(String.format("Warning! Application was stopped by killing! Pid: %d", pid));
		removePid();// 移除pid文件
	}
}
