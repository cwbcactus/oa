package com.cwb.oa.view.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cwb.oa.base.BaseAction;
import com.cwb.oa.domain.Privilege;
import com.cwb.oa.domain.Role;
import com.opensymphony.xwork2.ActionContext;

@Scope("prototype")
@Controller
public class RoleAction extends BaseAction<Role>{
	
	private Long[] privilegeIds;
	
	public Long[] getPrivilegeIds() {
		return privilegeIds;
	}

	public void setPrivilegeIds(Long[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}

	/**
	 * 列表
	 * @return
	 */
	public String list(){
		List<Role> roles = roleService.findAll();
		ActionContext.getContext().put("roles", roles);
		return "list";
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		roleService.delete(model.getId());
		return "toList";
	}
	
	/**
	 * 添加页面
	 * @return
	 */
	public String addUI(){
		return "saveUI";
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String add(){
		roleService.save(model);
		return "toList";
	}
	
	/**
	 * 修改页面
	 * @return
	 */
	public String editUI(){
		Role role = roleService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(role);
		return "saveUI";
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String edit(){
		Role role = roleService.getById(model.getId());
		
		role.setName(model.getName());
		role.setDescription(model.getDescription());
		
		roleService.update(role);
		return "toList";
	}
	
	/**
	 * 设置权限页面
	 * @return
	 */
	public String setPrivilegeUI(){
		List<Privilege> topPrivileges = privilegeService.getTopPrivileges();
		
		Role role = roleService.getById(model.getId());
		Set<Privilege> privileges = role.getPrivileges();
		
		if(privileges.size() > 0){
			privilegeIds = new Long[privileges.size()];
			
			int index = 0;
			for(Privilege privilege : privileges){
				privilegeIds[index++] = privilege.getId();
			}
		}
		
		ActionContext.getContext().put("topPrivileges", topPrivileges);
		
		return "setPrivilegeUI";
	}
	
	public String setPrivilege(){
		List<Privilege> privileges = privilegeService.getByIds(privilegeIds);
		
		Role role = roleService.getById(model.getId());
		
		if(privileges == null || privileges.size() == 0){
			role.setPrivileges(null);
		}else{
			role.setPrivileges(new HashSet<Privilege>(privileges));
		}
		roleService.update(role);
		
		return "toList";
	}

}
