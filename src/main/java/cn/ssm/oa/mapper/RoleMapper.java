package cn.ssm.oa.mapper;

import cn.ssm.oa.po.Role;
import tk.mybatis.mapper.common.Mapper;

public interface RoleMapper extends Mapper<Role> {

	Role findById(Long id);

	/**
	 * 删除该岗位之前关联的权限记录
	 * @param id
	 */
	void deleteRolePrivilege(Long id);

	/**
	 * 插入该岗位设置的最新权限记录
	 * @param role
	 */
	void insertRolePrivilege(Role role);
}