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
}