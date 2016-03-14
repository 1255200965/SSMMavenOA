package cn.ssm.oa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.ssm.oa.mapper.DepartmentMapper;
import cn.ssm.oa.mapper.UserMapper;
import cn.ssm.oa.po.Department;
import cn.ssm.oa.po.User;
import cn.ssm.oa.service.UserService;

public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
//	@Autowired
//	private DepartmentMapper departmentMapper;
	
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
	public void save(User user) {
		userMapper.insertSelective(user);
	}

	@Override
	public User findById(Long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(User user) {
		userMapper.updateByPrimaryKey(user);
	}

	@Override
	public void delete(Long id) {
		userMapper.deleteByPrimaryKey(id);
	}

}
