package fileListener;

import java.util.concurrent.TimeUnit;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 * 文件监控测试
 * 
 * 在Apache的Commons-IO中有关于文件的监控功能的代码. 文件监控的原理如下：
 * 
 * 由文件监控类FileAlterationMonitor中的线程不停的扫描文件观察器FileAlterationObserver，
 * 
 * 如果有文件的变化，则根据相关的文件比较器，判断文件时新增，还是删除，还是更改。（默认为1000毫秒执行一次扫描）
 * 
 * @author wy
 * 
 */
public class FileMonitorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// 监控目录
		String rootDir = "D:\\logs";
		// 轮询间隔 5 秒
		long interval = TimeUnit.SECONDS.toMillis(5);
		// 创建一个文件观察器用于处理文件的格式
//		FileAlterationObserver _observer = new FileAlterationObserver(rootDir, FileFilterUtils.and(FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(".txt")), // 过滤文件格式
//				null);
		FileAlterationObserver observer = new FileAlterationObserver(rootDir);

		observer.addListener(new FileListener()); // 设置文件变化监听器
		// 创建文件变化监听器
		FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
		// 开始监控
		monitor.start();
	}

}