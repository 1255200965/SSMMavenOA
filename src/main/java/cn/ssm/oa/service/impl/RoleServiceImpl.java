package cn.ssm.oa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.ssm.oa.mapper.RoleMapper;
import cn.ssm.oa.po.Role;
import cn.ssm.oa.service.RoleService;

public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public List<Role> findAll() {
		return roleMapper.selectByExample(null);
	}

	@Override
	public void save(Role role) {
		roleMapper.insertSelective(role);
	}

	@Override
	public Role findById(Long id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(Role role) {
		roleMapper.updateByPrimaryKey(role);
	}

	@Override
	public void delete(Long id) {
		roleMapper.deleteByPrimaryKey(id);
	}

}
