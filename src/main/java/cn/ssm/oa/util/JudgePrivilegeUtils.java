package cn.ssm.oa.util;

import cn.ssm.oa.po.Privilege;
import cn.ssm.oa.po.Role;
import cn.ssm.oa.po.User;

public class JudgePrivilegeUtils {
	
	/**
	 * 根据名称判断该用户是不是有此权限
	 * @param user
	 * @param name
	 * @return
	 */
	public static boolean hasPrivilegeByName(User user, String name) {
		if ("admin".equals(user.getLoginName())) {
			return true;
		}
		for (Role role : user.getRoles()) {
			for (Privilege privilege : role.getPrivileges()) {
				if (name.equals(privilege.getName())) {
					return true;
				}
			}
		}
		return false;
	}

}
