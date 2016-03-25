package cn.ssm.oa.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import cn.ssm.oa.mapper.UserMapper;
import cn.ssm.oa.po.Forum;
import cn.ssm.oa.po.Reply;
import cn.ssm.oa.po.Topic;
import cn.ssm.oa.po.User;
import cn.ssm.oa.service.ForumService;
import cn.ssm.oa.service.ReplyService;
import cn.ssm.oa.service.TopicService;

@Controller
@RequestMapping("/topic")
public class TopicController {

	@Autowired
	private ForumService forumService;
	@Autowired
	private TopicService topicService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private UserMapper userMapper;
	
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
//		return "topic/show"; // 直接转到页面加无法加载相应的数据
		/*
		 * 需要指明显示哪个主题，即调用下面的show方法，并且传递必须的参数，也可以调用的形式
		 * return show(model, topic.getId(), 1, 10);
		 */
		return "forward:show.action?id=" + topic.getId(); 
	}
	
	/* 显示单个主题(主贴+回帖列表) */
//	@RequestMapping("/show")
//	public String show(Model model, Long id) {
//		Topic topic = topicService.getById(id);
//		model.addAttribute("topic", topic);
//		Forum forum = forumService.getById(topic.getForumId()); // 查询该主题所属论坛板块(非封装对象属性方式)
//		model.addAttribute("forum", forum);
//		User author = userMapper.selectByPrimaryKey(topic.getAuthorId()); // 查询该主题的作者
//		model.addAttribute("author", author);
//		PageInfo<Reply> page = replyService.findByTopicIdPage(id);
//		model.addAttribute("page", page);
//		return "topic/show";
//	}
	/* 分页显示单个主题(主贴+回帖列表) */
	@RequestMapping("/show")
	public String show(Model model, Long id, Integer pageNum, Integer pageSize) {
		Topic topic = topicService.getById(id);
		model.addAttribute("topic", topic);
		Forum forum = forumService.getById(topic.getForumId()); // 查询该主题所属论坛板块(非封装对象属性方式)
		model.addAttribute("forum", forum);
		User author = userMapper.selectByPrimaryKey(topic.getAuthorId()); // 查询该主题的作者
		model.addAttribute("author", author);
		PageInfo<Reply> page = replyService.findByTopicIdPage(id, pageNum, pageSize);
		model.addAttribute("page", page);
		return "topic/show";
	}
}
