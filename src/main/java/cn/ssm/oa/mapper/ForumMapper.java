package cn.ssm.oa.mapper;

import java.util.List;

import cn.ssm.oa.po.Forum;
import tk.mybatis.mapper.common.Mapper;

public interface ForumMapper extends Mapper<Forum> {

	List<Forum> findAll();
}