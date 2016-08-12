package com.dph.ws.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dph.ws.server.HelloWorld;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-context.xml")
public class TestWebService {
	
	@Autowired
	private HelloWorld helloWorld;
	
	@Test
	public void mainTest() {
		String result = helloWorld.hello("hua");
		
		System.out.println(result);
	}

}
