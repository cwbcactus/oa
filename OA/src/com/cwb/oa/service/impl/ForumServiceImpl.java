package com.cwb.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwb.oa.base.DaoSupportImpl;
import com.cwb.oa.domain.Forum;
import com.cwb.oa.domain.Topic;
import com.cwb.oa.service.ForumService;

@Service
@SuppressWarnings("unchecked")
@Transactional
public class ForumServiceImpl extends DaoSupportImpl<Forum> implements
		ForumService {
	
	@Override
	public List<Forum> findAll() {
		// TODO Auto-generated method stub
		return getSession().createQuery(//
				"FROM "+Forum.class.getSimpleName()+" ORDER BY position").list();
	}
	
	@Override
	public void save(Forum entity) {
		// TODO Auto-generated method stub
		getSession().save(entity);
		entity.setPosition(entity.getId().intValue());
	}

	public void moveUp(Long id) {
		//找到要上移的forum
		  Forum forum = getById(id);
		//上一个forum
		Forum preForum = (Forum)getSession().createQuery(//
				"FROM "+Forum.class.getSimpleName()+
				" WHERE position < ? ORDER BY position DESC")
				.setParameter(0, forum.getPosition())
				.setFirstResult(0)
				.setMaxResults(1).uniqueResult();
		
		//如果要上移的是最上级，则不移
		if(preForum == null){
			return ;
		}
		
		//交换
		int temp = preForum.getPosition();
		preForum.setPosition(forum.getPosition());
		forum.setPosition(temp);
		
		getSession().update(preForum);
		getSession().update(forum);
	}

	public void moveDown(Long id) {
		//找到要上移的forum
		  Forum forum = getById(id);
		//上一个forum
		Forum nextForum = (Forum)getSession().createQuery(//
				"FROM "+Forum.class.getSimpleName()+
				" WHERE position > ? ORDER BY position ASC")
				.setParameter(0, forum.getPosition())
				.setFirstResult(0)
				.setMaxResults(1).uniqueResult();
		
		//如果要上移的是最上级，则不移
		if(nextForum == null){
			return ;
		}
		
		//交换
		int temp = nextForum.getPosition();
		nextForum.setPosition(forum.getPosition());
		forum.setPosition(temp);
		
		getSession().update(nextForum);
		getSession().update(forum);
	}

}
