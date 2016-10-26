package javaProcess;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JavaProcessTest {
	public static void main(String args[]) {
		try {
			List<String> list = new ArrayList<String>();
			ProcessBuilder pb = null;
			Process p = null;

			String java = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
			String classpath = System.getProperty("java.class.path");
			// list the files and directorys under C:\
			list.add(java);
			list.add("-classpath");
			list.add(classpath);
			list.add(JavaProcessDemo.class.getName());
			list.add("hello");
			list.add("world");
			list.add("good better best");

			pb = new ProcessBuilder(list);
			System.out.println(pb.command());

			p = pb.start();

			// process error and output message
			StreamWatch errorWatch = new StreamWatch(p.getErrorStream(), "ERROR");
			StreamWatch outputWatch = new StreamWatch(p.getInputStream(), "OUTPUT");
			// start to watch
			errorWatch.start();
			outputWatch.start();
			// wait for exit
			int exitVal = p.waitFor();
			// print the content from ERROR and OUTPUT
			System.out.println("ERROR: " + errorWatch.getOutput());
			System.out.println("OUTPUT: " + outputWatch.getOutput());
			System.out.println("the return code is " + exitVal);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}