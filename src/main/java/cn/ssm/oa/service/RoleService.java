package cn.ssm.oa.service;

import java.util.List;

import cn.ssm.oa.po.Role;

public interface RoleService {

	List<Role> findAll();
	
	void save(Role role);

	Role findById(Long id);

	void update(Role role);

	void delete(Long id);

	
}
