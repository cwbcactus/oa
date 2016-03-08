package com.cwb.oa.util;

import java.util.ArrayList;
import java.util.List;

import com.cwb.oa.base.DaoSupport;
import com.cwb.oa.domain.PageBean;
import com.opensymphony.xwork2.ActionContext;

public class QueryHelper {

	private String fromClause;
	private String whereClause;
	private String orderByClause;
	
	private List<Object> parameters = new ArrayList<Object>();
	
	/**
	 * 生成From子句
	 * 
	 * @param clazz
	 * @param alias
	 *            别名
	 */
	public QueryHelper(Class clazz, String alias){
		fromClause = "FROM "+ clazz.getSimpleName() + " "+alias;
	}
	
	/**
	 * 拼接Where子句
	 * 
	 * @param condition
	 * @param args
	 */
	public QueryHelper addWhereCondition(String condition, Object... args){
		if(whereClause.length() == 0){
			whereClause += " WHERE "+condition;
		}else{
			whereClause += " AND " + condition; 
		}
		
		if(args != null && args.length > 0){
			for(Object arg : args){
				parameters.add(arg);
			}
		}
		return this;
	}
	
	/**
	 * 如果第一个参数的值为true，就拼接Where子句
	 * 
	 * @param append
	 * @param condition
	 * @param args
	 */
	public QueryHelper addWhereCondition(boolean append, String condition, Object... args) {
		if (append) {
			addWhereCondition(condition, args);
		}
		return this;
	}
	
	
	public QueryHelper addOrderByProperty(String propertyName, boolean asc){
		if(orderByClause.length() == 0){
			orderByClause += " ORDER BY " + propertyName + (asc ? " ASC" : " DESC"); 
		}else{
			orderByClause += ", " + propertyName + (asc ? " ASC" : " DESC");
		}
		
		return this;
	}
	
	/**
	 * 如果第一个参数的值为true，就拼接OrderBy子句
	 * 
	 * @param append
	 * @param propertyName
	 * @param asc
	 */
	public QueryHelper addOrderByProperty(boolean append, String propertyName, boolean asc) {
		if (append) {
			addOrderByProperty(propertyName, asc);
		}
		return this;
	}

	/**
	 * 获取查询数据列表的HQL语句
	 * @return
	 */
	public String getQueryListHql(){
		return fromClause + whereClause + orderByClause;
	}
	
	/**
	 * 获取查询总记录数的HQL语句（没有OrderBy子句）
	 * @return
	 */
	public String getQueryCountHql(){
		return "SELECT COUNT(*) "+fromClause + whereClause;
	}
	

	/**
	 * 获取参数列表
	 * 
	 * @return
	 */
	public List<Object> getParameters() {
		return parameters;
	}
	
	/**
	 * 准备PageBean对象到Struts2的栈顶
	 * @param service
	 * @param pageNum
	 */
	public void preparePageBean(DaoSupport<?> service, int pageNum){
		PageBean pageBean = service.getPageBean(pageNum, this);
		ActionContext.getContext().getValueStack().push(pageBean);
	}
}
