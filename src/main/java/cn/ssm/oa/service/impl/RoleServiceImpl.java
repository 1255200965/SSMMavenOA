package cn.ssm.oa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.ssm.oa.mapper.RoleMapper;
import cn.ssm.oa.po.Privilege;
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

	/**
	 * 获得包括关联的权限列表的岗位
	 */
	@Override
	public Role findById(Long id) {
		Role role = roleMapper.findById(id); // 自定义方法，包含关联关系
		/*
		 * 给页面要使用的privilegeIds设置值，用于表单标签回显数据
		 */
		List<Privilege> privileges = role.getPrivileges();
		Long[] privilegeIds = null;
		if (privileges != null && !privileges.isEmpty()) {
			privilegeIds = new Long[privileges.size()];
			for (int i = 0; i < privileges.size(); i++) {
				privilegeIds[i] = privileges.get(i).getId();
			}
		}
		role.setPrivilegeIds(privilegeIds);
		return role;
	}

	@Override
	public void update(Role role) {
		roleMapper.updateByPrimaryKey(role);
	}

	@Override
	public void delete(Long id) {
		roleMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 修改岗位的权限
	 * 	策略：首先删除关联表有关该岗位的所有记录
	 * 		然后插入该岗位设置的新权限纪录
	 */
	@Override
	public void updateRolePrivilege(Role role) {
		roleMapper.deleteRolePrivilege(role.getId());
		roleMapper.insertRolePrivilege(role);
	}

}
