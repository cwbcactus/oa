package com.cwb.oa.view.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cwb.oa.base.BaseAction;
import com.cwb.oa.domain.User;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class LoginoutAction extends BaseAction<User>{
	
	/**
	 * 登录页面
	 * @return
	 */
	public String loginUI(){
		return "loginUI";
	}
	
	/**
	 * 登录
	 * @return
	 */
	public String login(){
		User user = userService.findByLoginNameAndPassword(model.getLoginName(), model.getPassword());
		
		if(user != null){
			ActionContext.getContext().getSession().put("user", user);
			return "toHome";
		}else{
			this.addFieldError("login", "用户名或者密码错误!!");
			return "loginUI";
		}
		
	}
	
	/**
	 * 退出
	 * @return
	 */
	public String logout(){
		ActionContext.getContext().getSession().remove("user");
		return "logout";
	}
}
