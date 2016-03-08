package com.cwb.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwb.oa.base.DaoSupportImpl;
import com.cwb.oa.domain.Privilege;
import com.cwb.oa.service.PrivilegeService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class PrivilegeServiceImpl extends DaoSupportImpl<Privilege> implements
		PrivilegeService {

	public List<Privilege> getTopPrivileges() {
		// TODO Auto-generated method stub
		return getSession().createQuery(//
				"FROM " + Privilege.class.getSimpleName()//
						+ " WHERE parent IS NULL").list();
	}

	public List<String> getAllPrivilegeUrls() {
		// TODO Auto-generated method stub
		return getSession().createQuery(//
				"SELECT DISTINCT url FROM " + Privilege.class.getSimpleName()//
				+" WHERE url IS NOT NULL").list();
	}

}
