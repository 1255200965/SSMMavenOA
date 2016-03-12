package cn.ssm.oa.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

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
	
	@RequestMapping("/addUI")
	public String addUI() {
		return "role/saveUI";
	}
	
	@RequestMapping("/add")
	public String add(Role role) {
		roleService.save(role);
		return "forward:list.action";
	}
	
	@RequestMapping("/editUI")
	public String editUI(Long id, Model model) {
		Role role = roleService.findById(id);
		model.addAttribute(role);
		return "role/saveUI";
	}
	
	@RequestMapping("/edit")
	public String edit(Role role) {
		roleService.update(role);
		return "forward:list.action";
	}
	
	@RequestMapping("delete")
	public String delete(Long id) {
//		roleService.findById(id);
		roleService.delete(id);
		return "forward:list.action";
	}
}
