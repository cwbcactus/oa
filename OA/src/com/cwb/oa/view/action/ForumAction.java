package com.cwb.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cwb.oa.base.BaseAction;
import com.cwb.oa.domain.Forum;
import com.cwb.oa.domain.Topic;
import com.cwb.oa.util.QueryHelper;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ForumAction extends BaseAction<Forum> {
	
	/**
	 * 0 表示全部主题<br>
	 * 1 表示全部精华贴
	 */
	private int viewType;
	
	/**
	 * 0 表示默认排序(所有置顶帖在前面，并按最后更新时间降序排列)<br>
	 * 1 表示只按最后更新时间排序<br>
	 * 2 表示只按主题发表时间排序<br>
	 * 3 表示只按回复数量排序
	 */
	private int orderBy;
	
	/**
	 * true表示升序<br>
	 * false表示降序
	 */
	private boolean asc;
	
	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

	private int pageNum = 1;
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * 列表
	 * @return
	 */
	public String list(){
		List<Forum> forums = forumService.findAll();
		ActionContext.getContext().put("forums", forums);
		return "list";
	}
	
	/**
	 * 显示单个模块
	 * @return
	 */
	public String show(){
		//未分页 version1
		//Forum forum = forumService.getById(model.getId());
		//List<Topic> topics = topicService.findTopicListByForum(forum);
		//Set<Topic> topics = forum.getTopics(); 直接这样取之后分页不好封装
		
		//分页 version2
//		 Forum forum = forumService.getById(model.getId());
//		 System.out.println(model.getId());
//		 PageBean pageBean = topicService.getPageBeanByForum(pageNum, forum);
//		 ActionContext.getContext().getValueStack().push(pageBean);
		
		//分页待条件 version3
//		Forum forum = forumService.getById(model.getId());
//		StringBuffer hql = new StringBuffer();
//		List<Object> args = new ArrayList<Object>();
//		args.add(forum);
//		
//		hql.append("FROM Topic t WHERE t.forum=? ");
//		if(viewType == 1){
//			hql.append(" and t.type=?");
//			args.add(Topic.TYPE_BEST);
//		}
//		
//		if(orderBy == 1){
//			hql.append(" ORDER BY t.lastUpdateTime " + (asc ? "ASC" : "DESC"));
//		}else if(orderBy == 2){
//			hql.append(" ORDER BY t.postTime " + (asc ? "ASC" : "DESC"));
//		}else if(orderBy == 3){
//			hql.append(" ORDER BY t.replyCount " + (asc ? "ASC" : "DESC"));
//		}else {
//			hql.append("ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC, t.lastUpdateTime DESC");
//		}
//		
//		PageBean pageBean = topicService.getPageBean(pageNum, hql.toString(), args);
//		ActionContext.getContext().getValueStack().push(pageBean);
		
		//分页 version4 构建hql语句
		Forum forum = forumService.getById(model.getId());
		new QueryHelper(Topic.class, "t")//
			.addWhereCondition("t.forum", forum)
			.addWhereCondition((viewType == 1), "t.type=?", Topic.TYPE_BEST)
			.addOrderByProperty((orderBy == 1), "t.lastUpdateTime", true);
			
		
		return "show";
	}

}
