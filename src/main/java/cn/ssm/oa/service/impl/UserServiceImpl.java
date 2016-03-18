package cn.ssm.oa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ssm.oa.mapper.DepartmentMapper;
import cn.ssm.oa.mapper.RoleMapper;
import cn.ssm.oa.mapper.UserMapper;
import cn.ssm.oa.po.Department;
import cn.ssm.oa.po.Role;
import cn.ssm.oa.po.User;
import cn.ssm.oa.service.UserService;
import tk.mybatis.mapper.entity.Example;

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
	/**
	 * 分页查询所有用户记录，此方式不可以，因为通过外连接的数据存在问题(总记录数多于单表)，
	 * 而且不体现一对多(多对多)关联关系，导致分页数据错误
	 */
//	@Override
//	public PageInfo<User> findAllByPage(Integer pageNum, Integer pageSize) {
//		// 如果没有传递当前页码参数或者传递为0，显示第一页数据
//		if (pageNum == null || pageNum == 0) {
//			pageNum = 1;
//		}
//		PageHelper.startPage(pageNum, pageSize);
//		List<User> list = userMapper.findAll(); // 调用自定义有关联查询的方法
//		int count = userMapper.selectCountByExample(null); // 查询user单表的条数
//		PageInfo<User> page = new PageInfo<User>(list);
//		/*
//		 *  给总记录数重新赋值,因为左外连接查询的记录数多于user表的count(*)数目，
//		 *  因为user表的基本数据重复，关联的岗位数据不重复，所以查询的记录条数方式多于单表，
//		 *  但是resultMap的collection集合自动将关联的一对多数据封装到List集合中
//		 *  注意：只要分页的pageSize!=1，一对多的属性仍然是同一条记录,但是主表user相同，关联表数据
//		 *  不同的记录，也算了一条记录，导致页面显示的记录少(基本表关联的记录充当了数目)
//		 */
//		page.setTotal(count); // 实际证明此方式仍不可行
//		return page;
//	}
	/**
	 * 循环set设置List<Role> roles的值
	 */
	@Override
	public PageInfo<User> findAllByPage(Integer pageNum, Integer pageSize) {
		// 如果没有传递当前页码参数或者传递为0，显示第一页数据
		if (pageNum == null || pageNum == 0) {
			pageNum = 1;
		}
		// 如果没有传递当前每页显示条数或者传递为0，每页显示10条
		if (pageSize == null || pageSize == 0) {
			pageSize = 10;
		}
		PageHelper.startPage(pageNum, pageSize);
		List<User> list = userMapper.selectAll();
		if (list != null && list.size() != 0) {
			for (User user : list) {
				// 给每个用户设置关联的岗位
				List<Role> roles = userMapper.findUserRoleListByUserId(user.getId());
				user.setRoles(roles);
			}
		}
		/*
		 * 使用自定义页码数量的构造函数(分页导航条显示10个页码),默认显示8个页码
		 */
		PageInfo<User> page = new PageInfo<User>(list, 10);
		return page;
	}

	/**
	 * 通过登录名和密码查询用户
	 */
	@Override
	public User findByLoginNameAndPassword(String loginName, String password) {
		Example example = new Example(User.class);
		example.or().andEqualTo("loginName", loginName).andEqualTo("password", password);
		List<User> list = userMapper.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

}
