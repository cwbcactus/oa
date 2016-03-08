package com.cwb.oa.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwb.oa.base.DaoSupportImpl;
import com.cwb.oa.domain.User;
import com.cwb.oa.service.UserService;

@Service
@Transactional
public class UserServiceImpl extends DaoSupportImpl<User> implements
		UserService {

	public User findByLoginNameAndPassword(String loginName, String password) {
		// TODO Auto-generated method stub
		String md5 = DigestUtils.md5Hex(password);
		return (User)getSession().createQuery(
				"FROM " + User.class.getSimpleName()
				+ " WHERE loginName=? AND password=?")
				.setParameter(0, loginName)
				.setParameter(1, md5)
				.uniqueResult();
	}

}
