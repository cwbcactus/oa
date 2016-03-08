package com.cwb.oa.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cwb.oa.domain.Privilege;
import com.cwb.oa.service.PrivilegeService;

public class OAInitListener implements ServletContextListener {
	
	private Log log  = LogFactory.getLog(OAInitListener.class);

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();
		/**
		 * 如何通过web容器对象获取spring的容器对象
		 * 不能通过new ClassPath..的方式创建，因为服务器中已经
		 * 有一个spring容器对象了
		 */
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(application);
		
		/**
		 * listener对象的创建是由struts2直接new创建的，
		 * 所以不能通过给privilegeService属性注入的方式创建对象
		 */
		// 1，查询所有顶级的权限列表并放到application作用域中
		PrivilegeService privilegeService = (PrivilegeService)ac.getBean("privilegeServiceImpl");
		List<Privilege> topPrivileges = privilegeService.getTopPrivileges();
		application.setAttribute("topPrivileges", topPrivileges);
		
		log.info("====== topPrivilegeList已经放到application作用域中了！ ======");
		
		// 2，查询出所有的权限的URL集合并放到application作用域中
		List<String> allPrivilegesUrls = privilegeService.getAllPrivilegeUrls();
		application.setAttribute("allPrivilegesUrls", allPrivilegesUrls);
		log.info("allPrivilegesUrls:"+allPrivilegesUrls+", allPrivilegesUrls.size:"+allPrivilegesUrls.size());
		log.info("====== allPrivilegeUrls已经放到application作用域中了！ ======");
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
}
