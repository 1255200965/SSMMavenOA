package cn.ssm.oa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

	@RequestMapping("/index")
	public String index() {
		return "home/index";
	}
	
	@RequestMapping("/top")
	public String top() {
		return "home/top";
	}
	
	@RequestMapping("/right")
	public String right() {
		return "home/right";
	}
	
	@RequestMapping("/bottom")
	public String bottom() {
		return "home/bottom";
	}
	
	@RequestMapping("/left")
	public String left() {
		return "home/left";
	}
}
