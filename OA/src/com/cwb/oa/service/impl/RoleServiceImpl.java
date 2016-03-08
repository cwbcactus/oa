package com.cwb.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwb.oa.base.DaoSupportImpl;
import com.cwb.oa.domain.Privilege;
import com.cwb.oa.domain.Role;
import com.cwb.oa.service.RoleService;

@Transactional
@Service
public class RoleServiceImpl extends DaoSupportImpl<Role> implements RoleService{
	
}
