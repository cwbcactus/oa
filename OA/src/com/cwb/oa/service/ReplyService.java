package com.cwb.oa.service;

import java.util.List;

import com.cwb.oa.base.DaoSupport;
import com.cwb.oa.domain.PageBean;
import com.cwb.oa.domain.Reply;
import com.cwb.oa.domain.Topic;

public interface ReplyService extends DaoSupport<Reply>{

	List<Reply> findReplyByTopic(Topic topic);

	PageBean findPageBeanByTopic(int pageNum, Topic topic);

}
