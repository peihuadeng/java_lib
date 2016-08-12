package com.dph.hessian.client.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-context.xml")
public class TestHessian {
	
	@Autowired
	private HelloWorld helloWorld;
	
	@Test
	public void mainTest() {
		String result = helloWorld.hello("hua");
		
		System.out.println(result);
	}

}
