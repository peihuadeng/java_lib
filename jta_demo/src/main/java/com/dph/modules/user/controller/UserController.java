package com.dph.modules.user.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dph.modules.user.entity.User;
import com.dph.modules.user.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	private final static Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@RequestMapping("list")
	public String list(Model model) {
		String page = "modules/user/userList";
		List<User> userList = userService.getAll();
		model.addAttribute("userList", userList);

		return page;
	}

	@RequestMapping("view")
	public String view(@RequestParam(defaultValue = "-1") int id, Model model) {
		String page = "modules/user/userView";
		if (id > 0) {
			User user = userService.get(id);
			model.addAttribute("user", user);
		}

		return page;
	}

	@RequestMapping("save")
	public String save(User user, Model model) {
		String page = "redirect:/user/list";
		System.out.println("user id:" + user.getId());
		int result = userService.save(user);
		logger.info("save user result: " + result);

		return page;
	}

}
