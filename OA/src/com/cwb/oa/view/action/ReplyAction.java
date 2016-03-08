package com.cwb.oa.view.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cwb.oa.base.BaseAction;
import com.cwb.oa.domain.Reply;
import com.cwb.oa.domain.Topic;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ReplyAction extends BaseAction<Reply>{
	
	private Long topicId;

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	/**
	 * 添加回复页面
	 * @return
	 */
	public String addUI(){
		Topic topic = topicService.getById(topicId);
		ActionContext.getContext().put("topic", topic);
		return "saveUI";
	}
	
	/**
	 * 添加回复 
	 * @return
	 */
	public String add(){
		Topic topic = topicService.getById(topicId);
		Reply reply = new Reply();
		
		reply.setTopic(topic);
		reply.setContent(model.getContent());
		reply.setAuthor(getCurrentUser());
		reply.setIpAddr(getRequestIP());
		
		replyService.save(reply);
		
		return "toTopicShow";
	}
}
