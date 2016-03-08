package com.cwb.oa.view.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cwb.oa.base.BaseAction;
import com.cwb.oa.domain.Department;
import com.cwb.oa.domain.Role;
import com.cwb.oa.domain.User;
import com.cwb.oa.util.DepartmentUtils;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	private Long[] roleIds;

	private Long departmentId;

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * 列表
	 * 
	 * @return
	 */
	public String list() {
		List<User> users = userService.findAll();
		ActionContext.getContext().put("users", users);
		return "list";
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() {
		userService.delete(model.getId());
		return "toList";
	}

	/**
	 * 添加页面
	 * 
	 * @return
	 */
	public String addUI() {
		List<Department> topList = departmentService.findTopDepartment();
		List<Department> departments = DepartmentUtils.getAllDepartmentList(
				topList, null);
		List<Role> roles = roleService.findAll();

		ActionContext.getContext().put("departments", departments);
		ActionContext.getContext().put("roles", roles);
		return "saveUI";
	}

	/**
	 * 添加
	 * 
	 * @return
	 */
	public String add() {
		Department department = null;
		if (departmentId != null) {
			department = departmentService.getById(departmentId);
		}

		List<Role> roles = null;
		if (roleIds != null && roleIds.length > 0) {
			roles = roleService.getByIds(roleIds);
		}

		model.setDepartment(department);
		model.setRoles(new HashSet<Role>(roles));

		userService.save(model);

		return "toList";
	}

	/**
	 * 修改页面
	 * 
	 * @return
	 */
	public String editUI() {
		User user = userService.getById(model.getId());
		Department department = user.getDepartment();
		Set<Role> roleList = user.getRoles();

		if (department != null) {
			this.departmentId = department.getId();
		}

		roleIds = new Long[roleList.size()];
		int index = 0;
		for (Role role : roleList) {
			roleIds[index++] = role.getId();
		}

		ActionContext.getContext().getValueStack().push(user);

		List<Department> topList = departmentService.findTopDepartment();
		List<Department> departments = DepartmentUtils.getAllDepartmentList(
				topList, null);
		List<Role> roles = roleService.findAll();

		ActionContext.getContext().put("departments", departments);
		ActionContext.getContext().put("roles", roles);
		return "saveUI";
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	public String edit() {
		// 1，从数据库中取出原对象
		User user = userService.getById(model.getId());

		// 2，设置要修改的属性
		user.setLoginName(model.getLoginName());
		user.setName(model.getName());
		user.setGender(model.getGender());
		user.setPhoneNumber(model.getPhoneNumber());
		user.setEmail(model.getEmail());
		user.setDescription(model.getDescription());

		Department department = null;
		if (departmentId != null) {
			department = departmentService.getById(departmentId);
		}

		List<Role> roles = null;
		if (roleIds != null && roleIds.length > 0) {
			roles = roleService.getByIds(roleIds);
		}

		user.setDepartment(department);
		user.setRoles(new HashSet<Role>(roles));

		userService.update(user);
		return "toList";
	}

	/**
	 * 初始化密码
	 * 
	 * @return
	 */
	public String initPassword() {
		User user = userService.getById(model.getId());
		
		String md5 = DigestUtils.md5Hex("1234");// 密码要使用MD5摘要
		user.setPassword(md5);
		
		userService.update(user);
		
		return "toList";
	}
}
