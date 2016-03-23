package cn.ssm.oa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.ssm.oa.mapper.TopicMapper;
import cn.ssm.oa.po.Topic;
import cn.ssm.oa.service.TopicService;
import tk.mybatis.mapper.entity.Example;

public class TopicServiceImpl implements TopicService {

	@Autowired
	private TopicMapper topicMapper;
	
	@Override
	public List<Topic> findByForumId(Long id) {
		Example example = new Example(Topic.class);
		example.or().andEqualTo("forumId", id);
		return topicMapper.selectByExample(example);
	}

}
