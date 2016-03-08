package com.cwb.oa.dao;

import com.cwb.oa.base.BaseDao;
import com.cwb.oa.domain.User;

public interface UserDao extends BaseDao<User>{

	User getByNameAndPassword(String username, String password);
	
}
