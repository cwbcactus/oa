package com.cwb.oa.service;

import java.util.List;

import com.cwb.oa.base.DaoSupport;
import com.cwb.oa.domain.Department;

public interface DepartmentService extends DaoSupport<Department>{
	/**
	 * 查询所有的子部门
	 * @param parentId
	 * @return
	 */
	List<Department> getChilidrenById(Long parentId);
	
	/**
	 * 查询顶级部门
	 * @return
	 */
	List<Department> findTopDepartment();
}
