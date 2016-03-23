package cn.ssm.oa.mapper;

import cn.ssm.oa.po.Topic;
import tk.mybatis.mapper.common.Mapper;

public interface TopicMapper extends Mapper<Topic> {
	
	/**
	 * 自定义根据id查询主题记录，而不使用通用mapper自带接口selectByPrimaryKey
	 * 原因：使用懒加载模式，必须重写根据id查询记录的方法
	 * @param id
	 * @return
	 */
	Topic getById(Long id);
}