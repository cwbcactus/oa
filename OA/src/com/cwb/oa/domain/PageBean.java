package com.cwb.oa.domain;

import java.util.List;

public class PageBean {

	/**
	 * 页面传递过来的参数
	 */
	private int currentPage;
	private int pageSize;

	/**
	 * 查询数据库获取
	 */
	private List<?> recordList;
	private int recordCount;

	/**
	 * 通过上面四个字段计算获取
	 */
	private int pageCount;
	//分页初始页码
	private int beginPageIndex; //前4个 + 当前页 + 后5个
	//页面结束页码
	private int endPageIndex; //前4个 + 当前页 + 后5个
	
	public PageBean(int currentPage, int pageSize, List<?> recordList, int recordCount){
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.recordList = recordList;
		this.recordCount = recordCount;
		
		pageCount = (recordCount + pageSize - 1) / pageSize;
		
		//如果pageCount不足10页，则显示所有
		if(pageCount <= 10){
			beginPageIndex = 1;
			endPageIndex = pageCount;
		}else{
			beginPageIndex = currentPage - 4;
			endPageIndex = currentPage + 5;
			
			//前面不足四个页面时，显示前十页
			if(beginPageIndex < 1){
				beginPageIndex = 1;
				endPageIndex = 10;
			}
			//当后面不足5个页面时，显示后10页
			else if(endPageIndex > pageCount){
				endPageIndex = pageCount;
				beginPageIndex = pageCount - 10 + 1;
			}
		}
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<?> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<?> recordList) {
		this.recordList = recordList;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getBeginPageIndex() {
		return beginPageIndex;
	}

	public void setBeginPageIndex(int beginPageIndex) {
		this.beginPageIndex = beginPageIndex;
	}

	public int getEndPageIndex() {
		return endPageIndex;
	}

	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}
}
