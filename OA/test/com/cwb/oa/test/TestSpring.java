package com.cwb.oa.test;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {
	
	private static ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	@Test
	public void testSessionFactory(){
		SessionFactory sessionFactory = (SessionFactory)ac.getBean("sessionFactory");
		System.out.println(sessionFactory.openSession());
	}
	
	@Test
	public void testTx(){
		TestService testService = (TestService)ac.getBean("testService");
		testService.saveTwoUser();
	}
}
