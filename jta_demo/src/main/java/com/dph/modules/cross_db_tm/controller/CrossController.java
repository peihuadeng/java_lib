package com.dph.modules.cross_db_tm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dph.modules.cross_db_tm.service.CrossService;
import com.dph.modules.student.entity.Student;
import com.dph.modules.user.entity.User;

@Controller
@RequestMapping("cross")
public class CrossController {
	
	@Autowired
	private CrossService crossService;
	
	@RequestMapping
	public String corss() {
		String destPage = "";
		User user = new User();
		user.setId(1);
		user.setName("user_test");
		user.setAge(100);
		
		Student student = new Student();
		student.setId(1);
		student.setName("stu_test");
		student.setAge(100);
		
		int result = crossService.update(user, student);
		System.out.println("cross db update result: " + result);
		
		return destPage;
	}

}
