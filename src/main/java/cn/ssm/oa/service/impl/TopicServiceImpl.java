package cn.ssm.oa.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;

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
import cn.ssm.oa.service.TopicService;
import tk.mybatis.mapper.entity.Example;

public class TopicServiceImpl implements TopicService {

	@Autowired
	private TopicMapper topicMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired 
	private ReplyMapper replyMapper;
	@Autowired
	private ForumMapper forumMapper;
	
	@Override
	public List<Topic> findByForumId(Long id) {
		Example example = new Example(Topic.class);
		example.or().andEqualTo("forumId", id);
		return topicMapper.selectByExample(example);
	}

	/**
	 * 通过forumId分页查询主题记录,注意service层方法名需要满足applicationContext-transaction.xml
	 * 中事务控制配置的通知的方法命名规则，才能执行一级缓存，即方法内userMapper.selectByPrimaryKey(topic.getAuthorId());
	 * 如果authorId一样只查询一次，同样的再一级缓存中读取，如果方法命名为pageFindByForumId，并不是<tx:method>中事物控制的方法
	 * 不能使用一级缓存，即：同样的sql每次都发出，降低了数据库性能(一级缓存默认不用配置，默认开启)
	 */
	@Override
	public PageInfo<Topic> findByForumIdPage(Long id, Integer pageNum, Integer pageSize) {
		if (pageNum == null || pageNum == 0) {
			pageNum = 1; // 默认显示第一页数据
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 10; // 默认页面显示10条数据
		}
		Example example = new Example(Topic.class);
		example.or().andEqualTo("forumId", id);
		/* 
		 * 设置默认的排序条件:
		 * 以类型降序排列，置顶帖在上面，精华帖和普通帖一起混合排列
		 * 类型相同的情况下，以最新更新时间降序排列
		 */
		example.setOrderByClause("(CASE type WHEN 2 THEN 2 ELSE 0 END) DESC, lastUpdateTime DESC");
		PageHelper.startPage(pageNum, pageSize); // 可以使用指定排序条件的startPage方法
		List<Topic> list = topicMapper.selectByExample(example);
		for (Topic topic : list) {
			User author = userMapper.selectByPrimaryKey(topic.getAuthorId());
			topic.setAuthor(author); // 为主题设置作者
			Reply lastReply = replyMapper.selectByPrimaryKey(topic.getLastReplyId());
			if (lastReply != null) {
				lastReply.setAuthor(userMapper.selectByPrimaryKey(lastReply.getAuthorId())); // 为最后回复设置作者
			}
			topic.setLastReply(lastReply); // 为主题设置最后回复
		}
		PageInfo<Topic> page = new PageInfo<Topic>(list, 10); // 指定导航页显示10个页码
		return page;
	}

	@Override
	public void save(Topic topic) {
		// 1，设置属性并保存
		topic.setAuthorId(topic.getAuthor().getId());
		topic.setPostTime(new Date()); // 发表时间为当前时间
		topic.setLastReplyId(null); // 新帖无最新回复
		topic.setLastUpdateTime(topic.getPostTime());
		topic.setReplyCount(0); // 回复数为0
		topic.setType(Topic.TYPE_NORMAL); // 新帖默认为普通帖
		topicMapper.insertSelective(topic); // 插入单表记录
		
		// 2，维护相关的特殊属性
		Forum forum = forumMapper.selectByPrimaryKey(topic.getForumId());
		forum.setArticleCount(forum.getArticleCount() + 1); // 该板块文章数增加1
		forum.setLastTopicId(topic.getId()); // 改板块最新主题设置为当前的帖，必须使用主键返回注解@GeneratedValue
		forum.setTopicCount(forum.getTopicCount() + 1);
		forumMapper.updateByPrimaryKeySelective(forum); // 更新该论坛板块特殊属性的值
	}

}
