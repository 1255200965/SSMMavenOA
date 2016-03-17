package cn.ssm.oa.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.ssm.oa.po.User;

public interface UserService {

	List<User> findAll();
	
	void save(User user, Long[] roleIdList);

	User findById(Long id);

	void update(User user);

	void delete(Long id);

	User getUserById(Long id);
	
	PageInfo<User> findAllByPage(Integer pageNum, Integer pageSize);
}
