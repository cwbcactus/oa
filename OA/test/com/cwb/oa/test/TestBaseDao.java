package com.cwb.oa.test;

import org.junit.Test;

import com.cwb.oa.dao.impl.RoleDaoImpl;
import com.cwb.oa.dao.impl.UserDaoImpl;
import com.cwb.oa.domain.Role;
import com.cwb.oa.domain.User;

public class TestBaseDao {
	
	@Test
	public void testBaseDao(){
		UserDaoImpl userDao = new UserDaoImpl();
		RoleDaoImpl roleDao = new RoleDaoImpl();
		
		User user = userDao.getById(1L);
		Role role = roleDao.getById(1L);
	}
	
}
