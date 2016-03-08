package com.cwb.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cwb.oa.base.BaseAction;
import com.cwb.oa.domain.Forum;
import com.cwb.oa.domain.PageBean;
import com.cwb.oa.domain.Reply;
import com.cwb.oa.domain.Topic;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class TopicAction extends BaseAction<Topic>{
	
	private int pageNum = 1;
	
	private Long forumId;
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public Long getForumId() {
		return forumId;
	}

	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}

	/**
	 * 单个topic展示
	 * @return
	 */
	public String show(){
//		Topic topic = topicService.getById(model.getId());
//		List<Reply> replies = replyService.findReplyByTopic(topic);
//		
//		ActionContext.getContext().put("topic", topic);
//		ActionContext.getContext().put("replies", replies);
		
		//分页
		Topic topic = topicService.getById(model.getId());
		System.out.println("id:"+model.getId()+", topic:"+topic.getContent());
		PageBean pageBean = replyService.findPageBeanByTopic(pageNum, topic);

		ActionContext.getContext().put("topic", topic);
		ActionContext.getContext().getValueStack().push(pageBean);
		
		return "show";
	}
	
	/**
	 * topic添加页面
	 * @return
	 */
	public String addUI(){
		Forum forum = forumService.getById(forumId);
		ActionContext.getContext().put("forum", forum);
		return "saveUI";
	}
	
	
	/**
	 * topic添加
	 * @return
	 */
	public String add(){
		Topic topic = new Topic();
		topic.setTitle(model.getTitle());
		topic.setContent(model.getContent());
		topic.setAuthor(getCurrentUser());
		topic.setIpAddr(getRequestIP());
		topic.setForum(forumService.getById(forumId));
		
		topicService.save(topic);
		
		ActionContext.getContext().put("topicId", topic.getId());
		
		return "toShow";
	}
}
