package com.cwb.oa.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwb.oa.base.DaoSupportImpl;
import com.cwb.oa.cfg.Configuration;
import com.cwb.oa.domain.Forum;
import com.cwb.oa.domain.PageBean;
import com.cwb.oa.domain.Reply;
import com.cwb.oa.domain.Topic;
import com.cwb.oa.service.ReplyService;

@Service
@SuppressWarnings("unchecked")
@Transactional
public class ReplyServiceImpl extends DaoSupportImpl<Reply> implements ReplyService{

	public List<Reply> findReplyByTopic(Topic topic) {
		// 排序：最新的回复排到最后面
		return getSession().createQuery(//
				"FROM Reply r WHERE r.topic=? ORDER BY r.postTime ASC")//
				.setParameter(0, topic).list();
	}
	
	@Override
	public void save(Reply reply) {
		reply.setPostTime(new Date());
		reply.setDeleted(false);// 默认为未删除
		getSession().save(reply);
		
		Topic topic = reply.getTopic();
		Forum forum = topic.getForum();
		
		topic.setReplyCount(topic.getReplyCount() + 1);// 主题的回复数量
		topic.setLastReply(reply);// 主题的最后发表的回复
		topic.setLastUpdateTime(reply.getPostTime());// 主题的最后更新时间（主题发表的时间或最后回复的时间）
		
		forum.setArticleCount(forum.getArticleCount() + 1);// 版块的文章数量（主题+回复）
		
		getSession().update(topic);
		getSession().update(forum);
	}
	
	public PageBean findPageBeanByTopic(int pageNum, Topic topic) {
		// TODO Auto-generated method stub
		int pageSize = Configuration.getPageSize();
		
		List<Reply> replies = getSession().createQuery(//
				"FROM Reply r WHERE r.topic=? ORDER BY r.postTime ASC")
				.setParameter(0, topic)
				.setFirstResult(pageSize * (pageNum - 1))
				.setMaxResults(pageSize)
				.list();
		
		Long replyCount = (Long)getSession().createQuery(//
				"SELECT COUNT(*) FROM Reply r WHERE r.topic=? ORDER BY r.postTime ASC")
				.setParameter(0, topic)
				.uniqueResult();
		
		return new PageBean(pageNum, pageSize, replies, replyCount.intValue());
	}
}
