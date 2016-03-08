package com.cwb.oa.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cwb.oa.base.BaseDaoImpl;
import com.cwb.oa.dao.DepartmentDao;
import com.cwb.oa.domain.Department;

@Repository
@SuppressWarnings("unchecked")
public class DepartmentDaoImpl extends BaseDaoImpl<Department> implements DepartmentDao {
	
	public List<Department> getChildrenById(Long parentId) {
		// TODO Auto-generated method stub
		return getSession().createQuery(//
				"FROM "+Department.class.getSimpleName()+" WHERE parentId=:parentId")
				.setParameter("parentId", parentId).list();
	}
	
	public List<Department> findTopDepartment() {
		// TODO Auto-generated method stub
		return getSession().createQuery(//
				"FROM "+Department.class.getSimpleName()+" WHERE parent is NULL").list();
	}
}
