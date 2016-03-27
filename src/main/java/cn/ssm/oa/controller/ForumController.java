package cn.ssm.oa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import cn.ssm.oa.po.Forum;
import cn.ssm.oa.po.Topic;
import cn.ssm.oa.service.ForumService;
import cn.ssm.oa.service.TopicService;

@Controller
@RequestMapping("/forum")
public class ForumController {

	@Autowired
	private ForumService forumService;
	@Autowired
	private TopicService topicService;
	
	@RequestMapping("/list")
	public String list(Model model) {
		List<Forum> list = forumService.findAll();
		model.addAttribute("forumList", list);
		return "forum/list";
	}
	
	/**
	 * 分页查询主题列表记录
	 * @param model
	 * @param id
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/show")
	public String show(Model model, Long id, Integer pageNum, Integer pageSize, Integer viewType, Integer orderBy, Boolean asc) {
		Forum forum = forumService.getById(id);
		model.addAttribute("forum", forum);
		PageInfo<Topic> page = topicService.findByForumIdPage(id, pageNum, pageSize, viewType, orderBy, asc);
		model.addAttribute("page", page);
		
		/*
		 * 回显过滤与排序条件参数
		 */
		model.addAttribute("viewType", viewType);
		model.addAttribute("orderBy", orderBy);
		model.addAttribute("asc", asc);
		return "forum/show";
	}
}
