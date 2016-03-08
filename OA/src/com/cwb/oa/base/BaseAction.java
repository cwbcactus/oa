package com.cwb.oa.base;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.cwb.oa.domain.User;
import com.cwb.oa.service.DepartmentService;
import com.cwb.oa.service.ForumService;
import com.cwb.oa.service.PrivilegeService;
import com.cwb.oa.service.ReplyService;
import com.cwb.oa.service.RoleService;
import com.cwb.oa.service.TopicService;
import com.cwb.oa.service.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	// ===================== 声明Service ====================
	@Resource
	protected RoleService roleService;

	@Resource
	protected DepartmentService departmentService;
	
	@Resource
	protected UserService userService;
	
	@Resource
	protected PrivilegeService privilegeService;
	
	@Resource
	protected ForumService forumService;
	
	@Resource
	protected TopicService topicService;
	
	@Resource
	protected ReplyService replyService;

	// ===================== 对ModelDriven的支持 ====================
	protected T model;

	public BaseAction() {
		try {
			ParameterizedType pt = (ParameterizedType) this.getClass()
					.getGenericSuperclass();
			Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];

			this.model = clazz.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public T getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	
	/**
	 * 获取登录的用户
	 * @return
	 */
	public User getCurrentUser(){
		return (User)ActionContext.getContext().getSession().get("user");
	}
	
	public String getRequestIP(){
		return ServletActionContext.getRequest().getRemoteAddr();
	}
}
