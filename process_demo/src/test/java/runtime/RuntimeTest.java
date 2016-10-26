package runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class RuntimeTest {
	public static void main(String[] args) {
		try {
			Process p = null;
			String line = null;
			BufferedReader stdout = null;

			// list the files and directorys under C:\
			p = Runtime.getRuntime().exec("CMD.exe /C dir", null, new File("C:\\"));
			stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = stdout.readLine()) != null) {
				System.out.println(line);
			}
			stdout.close();

			// echo the value of NAME
			p = Runtime.getRuntime().exec("CMD.exe /C echo %NAME%", new String[] { "NAME=TEST" });
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