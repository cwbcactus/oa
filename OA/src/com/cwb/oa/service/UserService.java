package com.cwb.oa.service;

import com.cwb.oa.base.DaoSupport;
import com.cwb.oa.domain.User;

public interface UserService extends DaoSupport<User> {

	User findByLoginNameAndPassword(String name, String password);

}
