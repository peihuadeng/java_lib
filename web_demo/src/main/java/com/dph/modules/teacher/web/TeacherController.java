package com.dph.modules.teacher.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dph.common.entity.Page;
import com.dph.common.web.BaseController;
import com.dph.modules.teacher.entity.Teacher;
import com.dph.modules.teacher.service.TeacherService;

@Controller
@RequestMapping("teacher")
public class TeacherController extends BaseController {
	
	private final static Logger logger = Logger.getLogger(TeacherController.class);

	@Autowired
	private TeacherService teacherService;

	@RequestMapping({"", "list"})
	public String list(Teacher teacher, Model model) {
		String destPage = "modules/teacher/teacherList";
		Page<Teacher> page = teacherService.findPage(teacher);
		
		model.addAttribute("page", page);

		return destPage;
	}
	
	@RequestMapping("view")
	public String view(@RequestParam(defaultValue = "") String id, Model model) {
		String destPage = "modules/teacher/teacherView";
		Teacher teacher = teacherService.get(id);
		model.addAttribute("teacher", teacher);

		return destPage;
	}

	@RequestMapping("save")
	public String save(Teacher teacher, Model model) {
		String destPage = "redirect:/teacher/list";
		
		int result = teacherService.save(teacher);
		logger.info("save teacher result: " + result);

		return destPage;
	}
	
	@RequestMapping("delete")
	public String delete(@RequestParam("id") String id) {
		String destPage = "redirect:/teacher/list";
		
		int result = teacherService.remove(id);
		logger.info("delete teacher result: " + result);
		
		return destPage;
	}
}
