package cn.ssm.oa.service;

import java.util.Collection;
import java.util.List;

import cn.ssm.oa.po.Privilege;

public interface PrivilegeService {

	List<Privilege> findTopList();

	Collection<String> getAllPrivilegeUrls();
}
