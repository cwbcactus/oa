package com.cwb.oa.service;

import java.util.List;

import com.cwb.oa.base.DaoSupport;
import com.cwb.oa.domain.Forum;
import com.cwb.oa.domain.Topic;

public interface ForumService extends DaoSupport<Forum>{
	
	/**
	 * 板块上移
	 * @param id
	 */
	void moveUp(Long id);
	
	/**
	 * 板块下移
	 * @param id
	 */
	void moveDown(Long id);

}
