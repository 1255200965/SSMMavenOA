package cn.ssm.oa.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.ssm.oa.mapper.PrivilegeMapper;
import cn.ssm.oa.po.Privilege;
import cn.ssm.oa.service.PrivilegeService;
import tk.mybatis.mapper.entity.Example;

public class PrivilegeServiceImpl implements PrivilegeService {

	@Autowired
	private PrivilegeMapper privilegeMapper;
	
	/**
	 * 查询顶级权限列表，其中包含子孙权限(即：树形结构)
	 */
	@Override
	public List<Privilege> findTopList() {
		Example example = new Example(Privilege.class);
		example.or().andIsNull("parentId");
		List<Privilege> topList = privilegeMapper.selectByExample(example);
		if (topList != null && !topList.isEmpty()) {
			for (Privilege top : topList) {
				List<Privilege> children = findChildren(top.getId());
				top.setChildren(children);
				if (children != null && !children.isEmpty()) {
					for (Privilege child : children) {
						List<Privilege> grandsons = findChildren(child.getId());
						child.setChildren(grandsons);
					}
				}
			}
		}
		return topList;
	}
	
	private List<Privilege> findChildren(Long id) {
		Example example = new Example(Privilege.class);
		example.or().andEqualTo("parentId", id);
		return privilegeMapper.selectByExample(example);
	}

	/**
	 * 查询所有权限URL的集合(去重复)
	 */
	@Override
	public Collection<String> getAllPrivilegeUrls() {
		return privilegeMapper.getAllPrivilegeUrls();
	}

}
