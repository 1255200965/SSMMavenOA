package cn.ssm.oa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ssm.oa.mapper.ReplyMapper;
import cn.ssm.oa.mapper.UserMapper;
import cn.ssm.oa.po.Reply;
import cn.ssm.oa.po.User;
import cn.ssm.oa.service.ReplyService;
import tk.mybatis.mapper.entity.Example;

public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyMapper replyMapper;
	@Autowired
	private UserMapper userMapper;
	
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

}
