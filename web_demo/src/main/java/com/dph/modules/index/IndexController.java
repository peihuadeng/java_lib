package com.dph.modules.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dph.common.web.BaseController;

@Controller
public class IndexController extends BaseController {
	
	@RequestMapping({"", "index"})
	public String index() {
		String destPage = "/modules/index/index";
		
		return destPage;
	}

}
