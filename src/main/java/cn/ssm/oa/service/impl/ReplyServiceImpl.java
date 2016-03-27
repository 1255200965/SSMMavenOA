package cn.ssm.oa.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ssm.oa.mapper.ForumMapper;
import cn.ssm.oa.mapper.ReplyMapper;
import cn.ssm.oa.mapper.TopicMapper;
import cn.ssm.oa.mapper.UserMapper;
import cn.ssm.oa.po.Forum;
import cn.ssm.oa.po.Reply;
import cn.ssm.oa.po.Topic;
import cn.ssm.oa.po.User;
import cn.ssm.oa.service.ReplyService;
import tk.mybatis.mapper.entity.Example;

public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyMapper replyMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private TopicMapper topicMapper;
	@Autowired
	private ForumMapper forumMapper;
	
	/**
	 * 根据topicId查询该主题的所有回复列表(包括作者的关联信息)
	 */
	@Override
	public List<Reply> findByTopicId(Long id) {
		Example example = new Example(Reply.class);
		example.or().andEqualTo("topicId", id);
		List<Reply> list = replyMapper.selectByExample(example);
		for (Reply reply : list) {
			User author = userMapper.selectByPrimaryKey(reply.getAuthorId()); // 为每个回复设置作者，自动使用一级缓存
			reply.setAuthor(author);
		}
		return list;
	}

	/**
	 * 根据topicId分页查询该主题的所有回复列表(包括作者的关联信息)
	 */
	@Override
	public PageInfo<Reply> findByTopicIdPage(Long id, Integer pageNum, Integer pageSize) {
		if (pageNum == null || pageNum == 0) {
			pageNum = 1; // 默认显示第一页数据
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 10; // 默认页面显示10条数据
		}
		Example example = new Example(Reply.class);
		example.or().andEqualTo("topicId", id);
		PageHelper.startPage(pageNum, pageSize, "postTime ASC"); // 已发表时间升序排列
		List<Reply> list = replyMapper.selectByExample(example);
		for (Reply reply : list) {
			User author = userMapper.selectByPrimaryKey(reply.getAuthorId()); // 为每个回复设置作者，自动使用一级缓存
			reply.setAuthor(author);
		}
		PageInfo<Reply> page = new PageInfo<Reply>(list, 10);
		return page;
	}

	@Override
	public void save(Reply reply) {
		reply.setPostTime(new Date());
		replyMapper.insertSelective(reply); // 新增单表的记录
		
		/*
		 * 维护关联关系的特殊属性
		 */
		Topic topic = topicMapper.selectByPrimaryKey(reply.getTopicId()); // 获取该回复所属主题
		Forum forum = forumMapper.selectByPrimaryKey(topic.getForumId()); // 获取该主题所属的板块
		topic.setReplyCount(topic.getReplyCount() + 1); // 该主题的回复数加1
		topic.setLastReplyId(reply.getId()); // 最新回复更新成当前的回复(需要Reply.java设置主键返回)
		topic.setLastUpdateTime(reply.getPostTime()); // 最后更新时间修改成当前回复发表时间 
		forum.setArticleCount(forum.getArticleCount() + 1); // 该论坛板块的文章数加1
		
		topicMapper.updateByPrimaryKeySelective(topic); // 更新所属主题非空数据
		forumMapper.updateByPrimaryKeySelective(forum); // 更新所属板块非空数据
	}

}
