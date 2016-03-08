<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!--分页信息-->
<div id=PageSelectorBar>
	<div id=PageSelectorMemo>
		页次：${currentPage }/${pageCount }页 &nbsp;
		每页显示：${pageSize }条 &nbsp;
		总记录数：${recordCount }条
	</div>
	<div id=PageSelectorSelectorArea>
		<!--
		<IMG SRC="${pageContext.request.contextPath }/style/blue/images/pageSelector/firstPage2.png"/>
		-->
		<a href="javascript:goToPage(1)" title="首页" style="cursor: hand;">
			<img src="${pageContext.request.contextPath }/style/blue/images/pageSelector/firstPage.png"/></a>
			
			<s:iterator begin="%{beginPageIndex}" end="%{endPageIndex}" var="num">
				<s:if test="currentPage != #num"><%-- 非当前页，有链接 --%>
					<span class="PageSelectorNum" style="cursor: hand;" onClick="goToPage(${num});">${num}</span>
				</s:if>
				<s:else><%-- 当前页，没有链接 --%>
				<span class="PageSelectorNum PageSelectorSelected">${num}</span>
				</s:else>
			</s:iterator>
			
		<a href="javascript:goToPage(${pageCount})" title="尾页" style="cursor: hand;">
			<img src="${pageContext.request.contextPath }/style/blue/images/pageSelector/lastPage.png"/></a>
		
		转到：
		<select id="pn" onchange="goToPage( this.value )">
			<s:iterator begin="1" end="%{pageCount}" var="num">
				<option value="${num}">${num}</option>
			</s:iterator>
		</select>
		
		<%-- 让select默认选中当前页 --%>
		<script type="text/javascript">
			$("#pn").val( "${currentPage}" );
		</script>
	</div>
</div>
<script type="text/javascript">
	//方式一： 
	// window.location.href = "forum_show.do?id=${id}&pageNum=" + pageNum;
	// alert("请实现gotoPage()方法！");
	
	//方式二
	$("#pageForm").append("<input type='hidden' name='pageNum' value='" + pageNum + "'>"); // 添加pageNum表单字段
	$("#pageForm").submit();// 提交表单
</script>