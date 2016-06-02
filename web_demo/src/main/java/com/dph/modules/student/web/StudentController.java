package com.dph.modules.student.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dph.common.entity.Page;
import com.dph.common.web.BaseController;
import com.dph.modules.student.entity.Student;
import com.dph.modules.student.service.StudentService;

@Controller
@RequestMapping("student")
public class StudentController extends BaseController {
	
	private final static Logger logger = Logger.getLogger(StudentController.class);

	@Autowired
	private StudentService studentService;

	@RequestMapping({"", "list"})
	public String list(Student student, Model model) {
		String destPage = "modules/student/studentList";
		Page<Student> page = studentService.findPage(student);
		
		model.addAttribute("page", page);

		return destPage;
	}
	
	@RequestMapping("view")
	public String view(@RequestParam(defaultValue = "") String id, Model model) {
		String destPage = "modules/student/studentView";
		Student student = studentService.get(id);
		model.addAttribute("student", student);

		return destPage;
	}

	@RequestMapping("save")
	public String save(Student student, Model model) {
		String destPage = "redirect:/student/list";
		
		int result = studentService.save(student);
		logger.info("save student result: " + result);

		return destPage;
	}
	
	@RequestMapping("delete")
	public String delete(@RequestParam("id") String id) {
		String destPage = "redirect:/student/list";
		
		int result = studentService.remove(id);
		logger.info("delete student result: " + result);
		
		return destPage;
	}
}
