package com.cwb.oa.base;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository
public abstract class BaseDaoImpl<T> implements BaseDao<T> {
	
	@Resource
	private SessionFactory sessionFactory;
	
	private Class<T> clazz;

	public BaseDaoImpl() {
		ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0];
	}

	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public void save(T entity) {
		// TODO Auto-generated method stub
		getSession().save(entity);
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
		if(id == null){
			return ;
		}
		
		T entity = getById(id);
		if(entity != null){
			getSession().delete(entity);
		}
	}

	public void update(T entity) {
		// TODO Auto-generated method stub
		getSession().update(entity);
	}

	public T getById(Long id) {
		// TODO Auto-generated method stub
		return (T) getSession().get(clazz, id);
	}

	public List<T> getByIds(Long[] ids) {
		// TODO Auto-generated method stub
		return getSession().createQuery("FROM "+clazz.getSimpleName()+" WHERE id IN (:ids)")//
				.setParameterList("ids", ids).list();
	}

	public List<T> findAll() {
		// TODO Auto-generated method stub
		return getSession().createQuery("FROM "+clazz.getSimpleName()).list();
	}

}
