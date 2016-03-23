package cn.ssm.oa.service;

import java.util.List;

import cn.ssm.oa.po.Topic;

public interface TopicService {

	List<Topic> findByForumId(Long id);
}
