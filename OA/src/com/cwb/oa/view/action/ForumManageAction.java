package com.cwb.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cwb.oa.base.BaseAction;
import com.cwb.oa.domain.Forum;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ForumManageAction extends BaseAction<Forum>{
	
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
	 * 添加页面
	 * @return
	 */
	public String addUI(){
		return "saveUI";
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String add(){
		Forum forum = new Forum();
		
		forum.setName(model.getName());
		forum.setDescription(model.getDescription());
		
		forumService.save(forum);
		
		return "toList";
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		forumService.delete(model.getId());
		return "toList";
	}
	
	/**
	 * 更新页面
	 * @return
	 */
	public String editUI(){
		Forum forum = forumService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(forum);
		return "saveUI";
	}
	
	/**
	 * 更新
	 * @return
	 */
	public String edit(){
		Forum forum = forumService.getById(model.getId());
		forum.setName(model.getName());
		forum.setDescription(model.getDescription());
		
		forumService.update(forum);
		
		return "toList";
	}
	
	/**
	 * 板块上移
	 * @return
	 */
	public String moveUp(){
		Long id = model.getId();
		
		forumService.moveUp(id);
		
		return "toList";
	}
	
	/**
	 * 板块下移
	 * @return
	 */
	public String moveDown(){
		Long id = model.getId();
		
		forumService.moveDown(id);
		
		return "toList";
	}
}
