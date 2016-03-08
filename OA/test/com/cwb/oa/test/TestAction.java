package com.cwb.oa.test;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller
public class TestAction extends ActionSupport{

	@Resource
	private TestService testService;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("-------> TestAction.execute()");
		System.out.println("-------> testService = " + testService);
		testService.saveTwoUser();
		return SUCCESS;
	}
}
