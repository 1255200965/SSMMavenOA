package cn.ssm.oa.service;

import java.util.List;

import cn.ssm.oa.po.Forum;

public interface ForumService {

	List<Forum> findAll();

	void save(Forum forum);

	void delete(Long id);

	Forum getById(Long id);

	void update(Forum forum);

	void moveUp(Long id);

	void moveDown(Long id);

}
