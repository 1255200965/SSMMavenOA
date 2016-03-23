package cn.ssm.oa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@RequestMapping("/show")
	public String show(Model model, Long id) {
		Forum forum = forumService.getById(id);
		model.addAttribute("forum", forum);
		List<Topic> topicList = topicService.findByForumId(id);
		model.addAttribute("topicList", topicList);
		return "forum/show";
	}
}
