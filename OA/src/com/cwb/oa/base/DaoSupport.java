package com.cwb.oa.base;

import java.util.List;

import com.cwb.oa.domain.PageBean;
import com.cwb.oa.util.QueryHelper;

/**
 * Service层和Dao层整合
 * @author chenwb
 *
 * @param <T>
 */
public interface DaoSupport<T> {
	/**
	 * 保存实体
	 * @param entity
	 */
	void save(T entity);
	
	/**
	 * 删除实体
	 * @param entity
	 */
	void delete(Long id);
	
	/**
	 * 更新实体
	 * @param entity
	 */
	void update(T entity);
	
	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	T getById(Long id);
	
	/**
	 * 根据ID数据查询多个
	 * @param ids
	 * @return
	 */
	List<T> getByIds(Long[] ids);
	
	/**
	 * 查询所有的实体
	 * @return
	 */
	List<T> findAll();

	/**
	 * 分页查询
	 * @param pageNum
	 * @param queryHelper
	 * @return
	 */
	PageBean getPageBean(int pageNum, QueryHelper queryHelper);
}
