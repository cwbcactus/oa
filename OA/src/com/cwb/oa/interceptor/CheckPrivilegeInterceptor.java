package com.cwb.oa.interceptor;

import com.cwb.oa.domain.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CheckPrivilegeInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		User user = (User)ActionContext.getContext().getSession().get("user");
		String namespace = invocation.getProxy().getNamespace();
		String actionName = invocation.getProxy().getActionName();
		
		if(namespace == null || "".equals(namespace)){
			namespace = "/";
		}
		
		if(!namespace.endsWith("/")){
			namespace += "/";
		}
		
		String url = namespace + actionName;
		
		if(user == null){
			//如果当前访问的是登录页面，就放行
			if(url.startsWith("/loginoutAction_login")){
				return invocation.invoke();
			}else{
				return "loginUI";
			}
		}else{
			//如果用户已经登录，就判断权限
			if(user.hasPrivilegeByUrl(url)){
				return invocation.invoke();
			}else{
				return "noPrivilegeUI";
			}
		}
	}

}
