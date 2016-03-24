package cn.ssm.oa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ssm.oa.mapper.ReplyMapper;
import cn.ssm.oa.mapper.TopicMapper;
import cn.ssm.oa.mapper.UserMapper;
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

}
