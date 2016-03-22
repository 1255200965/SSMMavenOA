package cn.ssm.oa.util;

import java.util.Collection;

import javax.servlet.ServletContext;

import org.springframework.web.context.support.WebApplicationContextUtils;

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
	
	/**
	 * 判断当前用户是否有指定URL的权限
	 * @param user 当前登录用户
	 * @param privUrl 当前访问的URL
	 * @param allPrivilegeUrls 所有的权限列表集合
	 * @return
	 */
	public static boolean hasPrivilegeByUrl(User user, String privUrl, Collection<String> allPrivilegeUrls) {
		if ("admin".equals(user.getLoginName())) {
			return true;
		}
		
		/*
		 * 去掉UI的后缀(因为数据库中只存了形如:
		 * /edit.action的url连接)而没有/editUI.action
		 * loginUI.action也是这个原理
		 */
		if (privUrl.endsWith("UI.action")) {
			privUrl = privUrl.substring(0, privUrl.length() - 9) + ".action";
		}
		
		/*
		 * 如果本URL不需要控制(作为基本权限)，则登录用户就可以使用
		 */
		if (!allPrivilegeUrls.contains(privUrl)) {
			return true;
		} 
		// 如果当前访问url属于控制范围内，则判断是否是分配权限
		else {
			for (Role role : user.getRoles()) {
				for (Privilege privilege : role.getPrivileges()) {
					if (privUrl.equals(privilege.getUrl())) {
						return true;
					}
				}
			}
			return false;
		}
	}

}
