package cn.ssm.oa.util;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.ssm.oa.po.Privilege;
import cn.ssm.oa.service.PrivilegeService;

/**
 * 初始化监听器，服务器启动时执行一次
 * 保存经常使用的ServletContext对象中，页面中通过application作用域访问
 * @author RocHuang
 *
 */
public class InitListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce)  { 
    	WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
    	/*
    	 * 将顶级权限列表topPrivilegeList保存到application对象中
    	 */
    	PrivilegeService privilegeService = (PrivilegeService) context.getBean("privilegeService");
    	List<Privilege> topPrivilegeList = privilegeService.findTopList();
    	sce.getServletContext().setAttribute("topPrivilegeList", topPrivilegeList);
    	System.out.println("======== InitListener.contextInitialized().topPrivilegeList ========");
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
    }
	
}
