package com.cwb.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwb.oa.base.DaoSupportImpl;
import com.cwb.oa.dao.DepartmentDao;
import com.cwb.oa.domain.Department;
import com.cwb.oa.service.DepartmentService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class DepartmentServiceImpl extends DaoSupportImpl<Department> implements DepartmentService {
	
	public List<Department> getChilidrenById(Long parentId) {
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
