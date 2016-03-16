package cn.ssm.oa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.ssm.oa.mapper.DepartmentMapper;
import cn.ssm.oa.mapper.RoleMapper;
import cn.ssm.oa.mapper.UserMapper;
import cn.ssm.oa.po.Department;
import cn.ssm.oa.po.Role;
import cn.ssm.oa.po.User;
import cn.ssm.oa.service.UserService;

public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public List<User> findAll() {
		List<User> list = userMapper.findAll(); // 调用自定义有关联查询的方法
		return list;
	}
	
	/**
	 * 单表查询自带方法(通过循环查询关联的单表方式(不推荐sql查询次数很多))
	 */
//	@Override
//	public List<User> findAll() {
//		List<User> list = userMapper.selectAll(); // 单表查询自带方法
//		for (User user : list) {
//			Department department = departmentMapper.selectByPrimaryKey(user.getDepartmentId());
//			user.setDepartment(department);
//		}
//		return list;
//	}

	@Override
	public void save(User user, Long[] roleIdList) {
		List<Role> roles = new ArrayList<Role>();
		if (roleIdList != null && roleIdList.length > 0) {
			for (Long id : roleIdList) {
				roles.add(roleMapper.selectByPrimaryKey(id));
			}
			user.setRoles(roles);
		}
		user.setPassword(DigestUtils.md5Hex("1234")); // 新增用户的默认密码为‘1234’的MD5加密
		if (user.getDepartmentId() == 0) { // 没有选择所属部门
			user.setDepartmentId(null);
		}
		userMapper.save(user);
		
		/*
		 * 维护与岗位的关联关系
		 */
		if (user.getRoles() != null) { // 当没有进行岗位设置时，不插入关联表数据
			userMapper.inserUserRole(user);
		}
		
	}

	@Override
	public User findById(Long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(User user) {
		List<Role> roles = new ArrayList<Role>();
		Long[] roleIdList = user.getRoleIdList();
		if (roleIdList != null && roleIdList.length > 0) {
			for (Long id : roleIdList) {
				roles.add(roleMapper.selectByPrimaryKey(id));
			}
			user.setRoles(roles);
		}
		if (user.getDepartmentId() != null) {
			if (0 == user.getDepartmentId()) { // 没有选择所属部门
				user.setDepartmentId(null);
			}
		}
		userMapper.updateByPrimaryKeySelective(user); // 修改user表的非空字段
		
		/*
		 * 维护与岗位的关联关系
		 */
		if (user.getRoleIdList() != null && user.getRoleIdList().length != 0) { // 处理初始化密码用户对象的roleIdList=null而删除本已存在的岗位关联记录
			userMapper.deleteUserRole(user.getId()); // 根据用户id首先删除和该用户有关的记录
		}
		if (user.getRoles() != null) { 
			userMapper.inserUserRole(user); // 如果有新的岗位设置插入新记录到关联岗位的表
		}
		
	
	}

	@Override
	public void delete(Long id) {
		userMapper.deleteUserRole(id); // 根据用户的id删除关联的岗位记录
		userMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 自定义查询方法,根据用户的id获取拥有岗位关联关系和部门关联关系(部门关联可以省略)的用户
	 */
	@Override
	public User getUserById(Long id) {
		User user = userMapper.getUserById(id);
		/*
		 * 将岗位对象转换成岗位Id数组
		 */
		List<Role> roles = user.getRoles();
		Long[] roleIdList = null;
		if (roles != null && roles.size() > 0) {
			roleIdList = new Long[roles.size()];
			for (int i = 0; i < roles.size(); i++) {
				roleIdList[i] = roles.get(i).getId();
			}
		}
		user.setRoleIdList(roleIdList); // 设置前端使用的Long[]
		return user;
	}

}
