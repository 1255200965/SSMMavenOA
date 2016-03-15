package cn.ssm.oa.mapper;

import java.util.List;

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
}