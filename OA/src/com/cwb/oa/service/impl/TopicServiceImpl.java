package com.cwb.oa.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwb.oa.base.DaoSupportImpl;
import com.cwb.oa.cfg.Configuration;
import com.cwb.oa.domain.Forum;
import com.cwb.oa.domain.PageBean;
import com.cwb.oa.domain.Topic;
import com.cwb.oa.service.TopicService;

@Service
@SuppressWarnings("unchecked")
@Transactional
public class TopicServiceImpl extends DaoSupportImpl<Topic> implements TopicService{

	public List<Topic> findTopicListByForum(Forum forum) {
		// 排序：最新状态的排到前面，置顶帖在最上面，先安装type(把精华帖的type先置为0，就不会对精华贴和普通贴按照type排序了)
		return getSession().createQuery(//
				"FROM Topic t WHERE t.forum=? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC, t.lastUpdateTime DESC")//
				.setParameter(0, forum).list();
	}
	
	public void save(Topic topic){
		topic.setPostTime(new Date());
		topic.setType(Topic.TYPE_NORMAL); //普通贴
		topic.setLastReply(null);
		topic.setLastUpdateTime(topic.getPostTime());
		topic.setReplyCount(0);
		
		getSession().save(topic);
		
		//更新forum
		Forum forum = topic.getForum();
		forum.setArticleCount(forum.getArticleCount() + 1);// 文章数量（主题+回复）
		forum.setTopicCount(forum.getTopicCount() + 1);// 主题数量
		forum.setLastTopic(topic);// 最后发表的主题
		
		getSession().update(forum);
	}

	public PageBean getPageBeanByForum(int pageNum, Forum forum) {
		int pageSize = Configuration.getPageSize();
		
		List<Topic> topics = getSession().createQuery(//
				"FROM Topic t WHERE t.forum=? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC, t.lastUpdateTime DESC")//
				.setParameter(0, forum)
				.setFirstResult(pageSize * (pageNum - 1))
				.setMaxResults(pageSize).list();
		
		Long topicCount = (Long)getSession().createQuery(//
				"SELECT COUNT(*) FROM Topic t where t.forum = ?")//
				.setParameter(0, forum).uniqueResult();
		
		return new PageBean(pageNum, pageSize, topics, topicCount.intValue());
	}
	
	public PageBean getPageBean(int pageNum, String hql, List<Object> args) {
		// TODO Auto-generated method stub
		int pageSize = Configuration.getPageSize();
		Query query = getSession().createQuery(hql);
		
		for(int i=0; i<args.size(); i++){
			query.setParameter(i, args.get(i));
		}
		
		query.setFirstResult(pageSize * (pageNum - 1)).setMaxResults(pageSize);
		List<Topic> topics = query.list();
		
		hql = "SELECT COUNT(*) "+hql;
		query = getSession().createQuery(hql);
		
		for(int i=0; i<args.size(); i++){
			query.setParameter(i, args.get(i));
		}
		Long topicCount = (Long)query.uniqueResult();
		
		return new PageBean(pageNum, pageSize, topics, topicCount.intValue());
	}
}
