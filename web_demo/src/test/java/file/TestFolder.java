package file;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class TestFolder {
	
	//@Test
	public void testDelNullFolder() {
		String filePath = "/";
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			file.delete();
		}
		
		File dir = file.getParentFile();
		while (dir != null && dir.isDirectory()) {
			if (dir.delete()) {
				dir = dir.getParentFile();
			} else {
				dir = null;
			}
		}
	}
	
//	@Test
	public void testNaN() {
		System.out.println(Double.NaN);
	}
	
	@Test
	public void sort() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		Collections.sort(list);
		for (int i : list) {
			System.out.println(i);
		}
	}

}
