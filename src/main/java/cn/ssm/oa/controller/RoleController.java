package cn.ssm.oa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ssm.oa.po.Role;
import cn.ssm.oa.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;

	@RequestMapping("/list")
	public String list(Model model) {
		List<Role> roleList = roleService.findAll();
		model.addAttribute("roleList", roleList);
		return "role/list";
	}
}
