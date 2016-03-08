package com.cwb.oa.service;

import java.util.List;

import com.cwb.oa.base.DaoSupport;
import com.cwb.oa.domain.Forum;
import com.cwb.oa.domain.PageBean;
import com.cwb.oa.domain.Topic;

public interface TopicService extends DaoSupport<Topic>{
	
	/**
	 * 根据模板获取相应的Topic
	 * @param forum
	 * @return
	 */
	List<Topic> findTopicListByForum(Forum forum);

	PageBean getPageBeanByForum(int pageNum, Forum forum);

	PageBean getPageBean(int pageNum, String string, List<Object> args);

}
