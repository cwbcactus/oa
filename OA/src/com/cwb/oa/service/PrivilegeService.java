package com.cwb.oa.service;

import java.util.List;

import com.cwb.oa.base.DaoSupport;
import com.cwb.oa.domain.Privilege;

public interface PrivilegeService extends DaoSupport<Privilege>{

	List<Privilege> getTopPrivileges();

	List<String> getAllPrivilegeUrls();

}
