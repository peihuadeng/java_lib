package com.dph.system.user.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dph.common.persistence.Page;
import com.dph.common.web.BaseController;
import com.dph.system.user.entity.User;
import com.dph.system.user.service.UserService;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {

	private final static Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@RequestMapping({"", "list"})
	public String list(User user, Model model) {
		String destPage = "system/user/userList";
		Page<User> page = userService.findPage(user);
		
		model.addAttribute("page", page);

		return destPage;
	}
	
	@RequestMapping("view")
	public String view(@RequestParam(defaultValue = "") String id, Model model) {
		String destPage = "system/user/userView";
		User user = userService.get(id);
		model.addAttribute("user", user);

		return destPage;
	}

	@RequestMapping("save")
	public String save(User user, Model model) {
		String destPage = "redirect:/user/list";
		
		int result = userService.save(user);
		logger.info("save user result: " + result);

		return destPage;
	}

}
