package cn.ssm.oa.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ssm.oa.po.Forum;
import cn.ssm.oa.po.Topic;
import cn.ssm.oa.po.User;
import cn.ssm.oa.service.ForumService;
import cn.ssm.oa.service.TopicService;

@Controller
@RequestMapping("/topic")
public class TopicController {

	@Autowired
	private ForumService forumService;
	@Autowired
	private TopicService topicService;
	
	@RequestMapping("/addUI")
	public String addUI(Model model, Long forumId) {
		Forum forum = forumService.getById(forumId);
		model.addAttribute("forum", forum);
		return "topic/addUI";
	}
	
	@RequestMapping("/add")
	public String add(Model model,HttpServletRequest request, Topic topic) {
		// 当前直接能获取的信息
		User author = (User) request.getSession().getAttribute("user");
		topic.setAuthor(author); // 设值发表新主题作者为当前的登录用户
		topic.setIpAddr(request.getRemoteAddr()); // 设值当前的IP地址
		topicService.save(topic);
		return "topic/show";
	}
}
