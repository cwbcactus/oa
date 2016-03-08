<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<title>导航菜单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/WEB-INF/jsp/public/header.jsp"%>
<script language="JavaScript" src="script/menu.js"></script>
<link type="text/css" rel="stylesheet" href="style/blue/menu.css" />
</head>
<body style="margin: 0">
<div id="Menu">
    <ul id="MenuUl">
    	<s:iterator value="#application.topPrivileges">
    		<s:if test="#session.user.hasPrivilegeByName(name)">
		        <li class="level1">
		            <div onClick=" $(this).next().toggle() " class="level1Style"><img src="style/images/MenuIcon/${id}.gif" class="Icon" />
		            	<!-- 可以用EL表达式, ognl表达式包含了EL表达式的功能 ${name} -->
		            	<s:property value="name"/> 
		            </div>
		            <ul style="display: none;" class="MenuLevel2">
				    	<s:iterator value="children">
				    		<s:if test="#session.user.hasPrivilegeByName(name)">
				                <li class="level2">
				                    <div class="level2Style">
				                    	<img src="style/images/MenuIcon/menu_arrow_single.gif" />
				                    	<%-- 这里不要使用<s:a>标签 struts2的<s:a>标签之后将会修改源码 --%>
				                    	<a href="${pageContext.request.contextPath }${url }.do" target="right"><s:property value="name"/></a>
				                    </div>
				                </li>
			                </s:if>
		                </s:iterator>
		            </ul>
		        </li>
	        </s:if>
        </s:iterator>
    </ul>
</div>
</body>
</html>
