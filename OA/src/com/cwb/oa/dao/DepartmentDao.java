package com.cwb.oa.dao;

import java.util.List;

import com.cwb.oa.base.BaseDao;
import com.cwb.oa.domain.Department;

public interface DepartmentDao extends BaseDao<Department>{

	List<Department> getChildrenById(Long parentId);

	List<Department> findTopDepartment();

}
