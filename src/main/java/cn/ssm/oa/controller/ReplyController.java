package cn.ssm.oa.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ssm.oa.po.Forum;
import cn.ssm.oa.po.Reply;
import cn.ssm.oa.po.Topic;
import cn.ssm.oa.po.User;
import cn.ssm.oa.service.ForumService;
import cn.ssm.oa.service.ReplyService;
import cn.ssm.oa.service.TopicService;

@Controller
@RequestMapping("/reply")
public class ReplyController {

	@Autowired
	private TopicService topicService;
	@Autowired
	private ForumService forumService;
	@Autowired
	private ReplyService replyService;
	
	@RequestMapping("/addUI")
	public String addUI(Model model, Long topicId) {
		Topic topic = topicService.getById(topicId); // 获取回复所属的主题
		model.addAttribute("topic", topic);
		Forum forum = forumService.getById(topic.getForumId()); // 获取该主题所属的
		model.addAttribute("forum", forum);
		return "reply/addUI";
	}
	
	@RequestMapping("/add")
	public String add(Reply reply, HttpServletRequest request) {
		// 直接能获取的信息(形参自动封装title,content,topicId)
		User author = (User) request.getSession().getAttribute("user");
		reply.setAuthorId(author.getId()); // 获取当前的作者
		reply.setIpAddr(request.getRemoteAddr()); // 获取当前的IP地址
		replyService.save(reply);
		/*
		 * 转发到其他Controller作用域上的写法(类上的RequestMapping+方法上的RequestMapping(包括所有的'/'))
		 */
		return "forward:/topic/show.action?id=" + reply.getTopicId();
	}
}
