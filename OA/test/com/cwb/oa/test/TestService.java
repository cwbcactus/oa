package com.cwb.oa.test;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwb.oa.domain.User;

@Service
public class TestService {
	
	@Resource
	private SessionFactory sessionFactory;
	
	@Transactional()
	public void saveTwoUser(){
		Session session = sessionFactory.getCurrentSession();
		session.save(new User());
//		int a = 1/0;
		session.save(new User());
	}
}
