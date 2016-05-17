package com.dph.modules.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dph.modules.student.entity.Student;
import com.dph.modules.student.service.StudentService;

@Controller
@RequestMapping("student")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@RequestMapping({ "", "list" })
	public String list(Model model) {
		String destPage = "modules/student/studentList";
		List<Student> list = studentService.getAll();
		model.addAttribute("studentList", list);

		return destPage;
	}

	@RequestMapping("view")
	public String view(@RequestParam int id, Model model) {
		String destPage = "modules/student/studentView";
		Student student = studentService.get(id);
		model.addAttribute("student", student);

		return destPage;
	}

	@RequestMapping("save")
	public String save(Student student, Model model) {
		String destPage = "redirect:/student/list";
		System.out.println("student id: " + student.getId());
		int result = studentService.save(student);
		System.out.println("save student result: " + result);

		return destPage;
	}

}
