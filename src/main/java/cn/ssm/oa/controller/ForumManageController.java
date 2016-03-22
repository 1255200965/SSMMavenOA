package cn.ssm.oa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ssm.oa.po.Forum;
import cn.ssm.oa.service.ForumService;

@Controller
@RequestMapping("/forumManage")
public class ForumManageController {

	@Autowired
	private ForumService forumService;
	
	@RequestMapping("/list")
	public String list(Model model) {
		List<Forum> list = forumService.findAll();
		model.addAttribute("forumList", list);
		return "forumManage/list";
	}
	
	@RequestMapping("/addUI")
	public String addUI(Model model) {
		/*
		 * 保存一个空对象到页面中，为了共用一个saveUI页面，页面中使用springmvc的表单标签，
		 * 必须指定commandName属性，且必须有值，不指定默认对象为command，但也不需保存到model中
		 */
		model.addAttribute("forum", new Forum());
		return "forumManage/saveUI";
	}
	
	@RequestMapping("/add")
	public String add(Forum forum) {
		forumService.save(forum);
		return "forward:list.action";
	}
	
	@RequestMapping("/delete")
	public String delete(Long id) {
		forumService.delete(id);
		return "forward:list.action";
	}
	
	@RequestMapping("/editUI")
	public String editUI(Long id, Model model) {
		Forum forum = forumService.getById(id);
		model.addAttribute("forum", forum);
		return "forumManage/saveUI";
	}

	@RequestMapping("/edit")
	public String edit(Forum forum) {
		forumService.update(forum);
		return "forward:list.action";
	}
	
	@RequestMapping("/moveUp") 
	public String moveUp(Long id) {
		forumService.moveUp(id);
		return "forward:list.action";
	}
	
	@RequestMapping("/moveDown") 
	public String moveDown(Long id) {
		forumService.moveDown(id);
		return "forward:list.action";
	}
}
