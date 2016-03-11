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

}
