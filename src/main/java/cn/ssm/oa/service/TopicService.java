package cn.ssm.oa.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.ssm.oa.po.Topic;

public interface TopicService {

	List<Topic> findByForumId(Long id);

	PageInfo<Topic> findByForumIdPage(Long id, Integer pageNum, Integer pageSize);

	void save(Topic topic);
}
