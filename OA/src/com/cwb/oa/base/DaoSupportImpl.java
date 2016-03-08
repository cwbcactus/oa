package com.cwb.oa.base;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.cwb.oa.cfg.Configuration;
import com.cwb.oa.domain.PageBean;
import com.cwb.oa.util.QueryHelper;

@SuppressWarnings("unchecked")
@Transactional
public class DaoSupportImpl<T> implements DaoSupport<T> {
	@Resource
	private SessionFactory sessionFactory;
	
	private Class<T> clazz;

	public DaoSupportImpl() {
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
		if(id == null){
			return null;
		}
		
		return (T) getSession().get(clazz, id);
	}

	public List<T> getByIds(Long[] ids) {
		// TODO Auto-generated method stub
		if(ids == null || ids.length == 0){
			return null;
		}
		
		return getSession().createQuery("FROM "+clazz.getSimpleName()+" WHERE id IN (:ids)")//
				.setParameterList("ids", ids).list();
	}

	public List<T> findAll() {
		// TODO Auto-generated method stub
		return getSession().createQuery("FROM "+clazz.getSimpleName()).list();
	}
	
	public PageBean getPageBean(int pageNum, QueryHelper queryHelper) {
		// TODO Auto-generated method stub
		System.out.println("------------> DaoSupportImpl.getPageBean( int pageNum, QueryHelper queryHelper )");

		// 获取pageSize等信息
		int pageSize = Configuration.getPageSize();
		List<Object> parameters = queryHelper.getParameters();

		// 查询一页的数据列表
		Query query = getSession().createQuery(queryHelper.getQueryListHql());
		if (parameters != null && parameters.size() > 0) { // 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				query.setParameter(i, parameters.get(i));
			}
		}
		query.setFirstResult((pageNum - 1) * pageSize);
		query.setMaxResults(pageSize);
		List<?> list = query.list(); // 查询

		// 查询总记录数
		query = getSession().createQuery(queryHelper.getQueryCountHql()); // 注意空格！
		if (parameters != null && parameters.size() > 0) { // 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				query.setParameter(i, parameters.get(i));
			}
		}
		Long count = (Long) query.uniqueResult(); // 查询

		return new PageBean(pageNum, pageSize, list, count.intValue());
	}
}
