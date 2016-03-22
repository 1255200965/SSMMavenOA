package cn.ssm.oa.mapper;

import java.util.Collection;

import cn.ssm.oa.po.Privilege;
import tk.mybatis.mapper.common.Mapper;

public interface PrivilegeMapper extends Mapper<Privilege> {

	Collection<String> getAllPrivilegeUrls();
}