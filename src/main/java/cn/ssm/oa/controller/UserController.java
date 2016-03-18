package cn.ssm.oa.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

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
	
	/**
	 * 分页查询用户记录
	 * @param model
	 * @param pageNum 当前页参数，每次都将封装到page对象中(源代码体现)(页面可以回显)
	 * @param pageSize 每页显示的记录条数，每次都将封装到page对象中(页面可以回显)
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model, Integer pageNum, Integer pageSize) {
		PageInfo<User> page = userService.findAllByPage(pageNum, pageSize);
		model.addAttribute("page", page);
		return "user/list";
	}
	
//	@RequestMapping("/list")
//	public String list(Model model) {
//		List<User> list = userService.findAll();
//		model.addAttribute("userList", list);
//		return "user/list";
//	}
	
	@RequestMapping("/addUI")
	public String addUI(Model model) throws Exception {
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		model.addAttribute("departmentList", departmentList);
		List<Role> roleList = roleService.findAll();
		model.addAttribute("roleList", roleList);
		// 为了让saveUI.jsp的form表单commandName="user"有值，使其editUI能用SpringMVC表单标签回显
		model.addAttribute("user", new User());
		return "user/saveUI";
	}
	
	@RequestMapping("/add")
	public String add(User user, Long[] roleIdList) throws Exception {
		userService.save(user, roleIdList);
		return "forward:list.action";
	}
	
	@RequestMapping("/delete")
	public String delete(Long id) {
		userService.delete(id);
		return "forward:list.action";
	}
	
	@RequestMapping("/initPassword")
	public String initPassword(Long id) {
		User user = userService.findById(id);
		user.setPassword(DigestUtils.md5Hex("1234"));
		userService.update(user);
		return "forward:list.action";
	}

	@RequestMapping("/editUI")
	public String editUI(Model model, Long id) throws Exception {
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		model.addAttribute("departmentList", departmentList);
		List<Role> roleList = roleService.findAll();
		model.addAttribute("roleList", roleList);
		// 返回用户完整关联关系(角色,部门可以不用，由departmentId代替)的用户
		User user = userService.getUserById(id); 
		model.addAttribute("user", user);
		return "user/saveUI";
	}

	@RequestMapping("/edit")
	public String edit(User user) throws Exception {
		userService.update(user);
		return "forward:list.action";
	}
	
	@RequestMapping("/loginUI")
	public String loginUI() {
		return "user/loginUI";
	}
	
	@RequestMapping("/login")
	public String login(Model model, HttpSession session, String loginName, String password) {
		User user = userService.findByLoginNameAndPassword(loginName, DigestUtils.md5Hex(password));
		if (user == null) {
			model.addAttribute("msg", "登录名或者密码错误！");
			model.addAttribute("loginName", loginName);
			model.addAttribute("password", password);
			return "user/loginUI";
		} else {
			session.setAttribute("user", user);
			return "redirect:/index.jsp";
		}
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); // 使session失效，并清空session占用的空间
//		session.removeAttribute("user"); // 删除session的user属性，不会清空session所占用的空间
		return "user/logout";
	}
}
