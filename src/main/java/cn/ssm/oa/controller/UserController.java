package cn.ssm.oa.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

import cn.ssm.oa.po.Department;
import cn.ssm.oa.po.Role;
import cn.ssm.oa.po.User;
import cn.ssm.oa.service.DepartmentService;
import cn.ssm.oa.service.RoleService;
import cn.ssm.oa.service.UserService;
import cn.ssm.oa.util.DepartmentUtils;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/list")
	public String list(Model model) {
		List<User> list = userService.findAll();
		model.addAttribute("userList", list);
		return "user/list";
	}
	
	@RequestMapping("/addUI")
	public String addUI(Model model) throws Exception {
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		model.addAttribute("departmentList", departmentList);
		List<Role> roleList = roleService.findAll();
		model.addAttribute("roleList", roleList);
		return "user/saveUI";
	}
	
	@RequestMapping("/add")
	public String add(User user, Long[] roleIdList) throws Exception {
//		Department department = departmentService.getById(user.getDepartmentId());
//		user.setDepartment(department);
//		System.out.println(Arrays.toString(roleIdList));
		System.out.println(JSON.toJSONString(roleIdList));
		System.out.println(JSON.toJSONString(user));
		userService.save(user, roleIdList);
		return "forward:list.action";
	}
}
