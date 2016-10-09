package json;

import java.util.Map;

import org.junit.Test;

import com.dph.common.utils.JsonUtils;

public class JsonTest {
	
	@Test
	public void mainTest() {
		String json = "{}";
		Map<String, Object> map = JsonUtils.str2map(json, String.class, Object.class);
		System.out.println(map);
	}

}
