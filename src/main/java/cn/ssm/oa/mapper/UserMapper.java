package cn.ssm.oa.mapper;

import java.util.List;

import cn.ssm.oa.po.Role;
import cn.ssm.oa.po.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
	
	/**
	 * 自定义关联查询方法(连表查询)
	 * @return
	 */
	List<User> findAll();
	
	/**
	 * 插入用户表的基本数据(没有维护与岗位的关联关系)
	 * @param user
	 */
	void save(User user);
	
	/**
	 * 插入新增用户相关联的用户角色数据
	 * @param user
	 */
	void inserUserRole(User user);

	/**
	 * 根据用户的id删除和岗位相关的记录
	 * @param id
	 */
	void deleteUserRole(Long id);
	
	/**
	 * 根据用户的id获取拥有岗位关联关系和部门关联关系(部门关联可以省略)的用户
	 * @param id
	 * @return
	 */
	User getUserById(Long id);

	/**
	 * 根据用户id查询该用户的岗位列表
	 * @return
	 */
	List<Role> findUserRoleListByUserId(Long id);

	/**
	 * 根据登录名和密码查询登录用户包括关联的岗位数据及岗位所拥有的权限数据
	 * @param user
	 * @return
	 */
	User findByLoginNameAndPassword(User user);
}