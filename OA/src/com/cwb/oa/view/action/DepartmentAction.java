package com.cwb.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cwb.oa.base.BaseAction;
import com.cwb.oa.domain.Department;
import com.cwb.oa.util.DepartmentUtils;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class DepartmentAction extends BaseAction<Department>{

	private Long parentId;
	
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * 列表
	 * @return
	 */
	public String list(){
		List<Department> departments = null;
		if(this.parentId == null){
			//默认显示顶级列表
			departments = departmentService.findAll();
		}else{
			//显示指定部门的子部门
			departments = departmentService.getChilidrenById(parentId);
			
			//不是顶级的话要显示返回上一级，把parent传过去
			Department parent = departmentService.getById(parentId);
			ActionContext.getContext().put("parent",parent);
		}
		
		ActionContext.getContext().put("departments", departments);
		
		return "list";
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		departmentService.delete(model.getId());
		return "toList";
	}
	
	/**
	 * 添加页面
	 * @return
	 */
	public String addUI(){
		
//		List<Department> departments = departmentService.findAll();
//		ActionContext.getContext().put("departments", departments);
		
		//使用树结构递归遍历所有数据
		List<Department> topList = departmentService.findTopDepartment();
		List<Department> departments = DepartmentUtils.getAllDepartmentList(topList, null);
		
		ActionContext.getContext().put("departments", departments);
		return "saveUI";
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String add(){
		Department parent = departmentService.getById(parentId);
		
		Department department = new Department();
		department.setName(model.getName());
		department.setDescription(model.getDescription());
		department.setParent(parent);
		
		departmentService.save(department);
		return "toList";
	}
	
	/**
	 * 修改页面
	 * @return
	 */
	public String editUI(){
		//回显数据
		Department department = departmentService.getById(model.getId());
		if(department.getParent() != null){
			this.parentId = department.getParent().getId();
		}
		
		ActionContext.getContext().getValueStack().push(department);
		
		//树状结构
		List<Department> topList = departmentService.findTopDepartment();
		List<Department> departments = DepartmentUtils.getAllDepartmentList(topList, department);
		ActionContext.getContext().put("departments", departments);
		return "saveUI";
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String edit(){
		Department department = departmentService.getById(model.getId());
		Department parent = departmentService.getById(this.parentId);
		
		department.setName(model.getName());
		department.setDescription(model.getDescription());
		department.setParent(parent);
		
		departmentService.update(department);
		return "toList";
	}
}
