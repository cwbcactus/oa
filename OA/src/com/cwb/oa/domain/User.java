package com.cwb.oa.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;

public class User implements Serializable {

	private Long id;

	private Set<Role> roles = new HashSet<Role>();
	private Department department;

	private String loginName; // 登录名
	private String password; // 密码
	private String name; // 真实姓名
	private String gender; // 性别
	private String phoneNumber; // 电话号码
	private String email; // 电子邮件
	private String description; // 说明

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 判断当前用户是否有指定名称的权限
	 * 
	 * @param privilegeName
	 *           	权限的名称
	 */
	public boolean hasPrivilegeByName(String privilegeName){
		//如果是超级管理员，就有所有的权限
		if(isAdmin()){
			return true;
		}
		//如果是普通用户则需要判断是否有权限
		else{
			for(Role role : roles){
				Set<Privilege> privileges = role.getPrivileges();
				for(Privilege privilege : privileges){
					if(privilegeName.equals(privilege.getName())){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean hasPrivilegeByUrl(String privilegeUrl){
		//如果是超级管理员，就有所有的权限
		if(isAdmin()){
			return true;
		}
		//如果是普通用户则需要判断是否有权限
		else{
			int pos = privilegeUrl.indexOf("?");
			if(pos > -1){
				privilegeUrl = privilegeUrl.substring(0, pos);
			}
			
			if(privilegeUrl.endsWith("UI")){
				privilegeUrl = privilegeUrl.substring(0, privilegeUrl.length() - 2);
			}
			
			//a、如果这个URL是不需要控制的功能（登录后就能直接使用的，比如登录和登出系统），应直接返回true
			Collection<String> allPrivilegesUrls = (Collection<String>)ActionContext.getContext().getApplication().get("allPrivilegesUrls");
			if(!allPrivilegesUrls.contains(privilegeUrl)){
				return true;
			}else{
				for(Role role : roles){
					Set<Privilege> privileges = role.getPrivileges();
					for(Privilege privilege : privileges){
						if(privilegeUrl.equals(privilege.getUrl())){
							return true;
						}
					}
				}
				return false;
			}
		}
	}
	
	private boolean isAdmin(){
		return "admin".equals(loginName);
	}
}
