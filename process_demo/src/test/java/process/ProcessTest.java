package process;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessTest {
	public static void main(String[] args) {
		try {
			List<String> list = new ArrayList<String>();
			ProcessBuilder pb = null;
			Process p = null;
			String line = null;
			BufferedReader stdout = null;

			// list the files and directorys under C:\
			list.add("CMD.EXE");
			list.add("/C");
			list.add("dir");
			pb = new ProcessBuilder(list);
			pb.directory(new File("C:\\"));
			p = pb.start();

			stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = stdout.readLine()) != null) {
				System.out.println(line);
			}
			stdout.close();

			// echo the value of NAME
			pb = new ProcessBuilder();
			pb.command(new String[] { "CMD.exe", "/C", "echo %NAME%" });
			pb.environment().put("NAME", "TEST");
			p = pb.start();

			stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = stdout.readLine()) != null) {
				System.out.println(line);
			}
			stdout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}