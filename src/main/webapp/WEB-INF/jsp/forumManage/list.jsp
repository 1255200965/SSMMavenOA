<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html><head>
    <title>版块列表</title>
    <%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
    <style type="text/css">
    	.disabled {
    		color: grey;
    		cursor: pointer;
    	}
    </style>
</head>
<body>

<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img src="${pageContext.request.contextPath }/style/images/title_arrow.gif" border="0" height="13" width="13"> 版块管理
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="MainArea">
    <table class="TableStyle" cellpadding="0" cellspacing="0">
       
        <!-- 表头-->
        <thead>
            <tr id="TableTitle" align="CENTER" valign="MIDDLE">
            	<td width="250px">版块名称</td>
                <td width="300px">版块说明</td>
                <td>相关操作</td>
            </tr>
        </thead>

		<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="forumList">
		
		<c:forEach items="${forumList }" var="forum" varStatus="status">	
        <tr class="TableDetail1 demodata_record">
				<td>${forum.name }&nbsp;</td>
				<td>${forum.description }&nbsp;</td>
				<td><a onclick="return delConfirm()" href="${pageContext.request.contextPath }/forumManage/delete.action?id=${forum.id }">删除</a>
					<a href="${pageContext.request.contextPath }/forumManage/editUI.action?id=${forum.id }">修改</a>
					<c:choose>
						<c:when test="${status.first }">
							<span class="disabled">上移</span>
						</c:when>
						<c:otherwise>
							<a href="${pageContext.request.contextPath }/forumManage/moveUp.action?id=${forum.id }">上移</a>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${status.last }">
							<span class="disabled">下移</span>
						</c:when>
						<c:otherwise>
							<a href="${pageContext.request.contextPath }/forumManage/moveDown.action?id=${forum.id }">下移</a>
						</c:otherwise>
					</c:choose>
				</td>
		</tr>
		</c:forEach>
		</tbody>
    </table>
    
    <!-- 其他功能超链接 -->
    <div id="TableTail">
        <div id="TableTail_inside">
            <a href="${pageContext.request.contextPath }/forumManage/addUI.action"><img src="${pageContext.request.contextPath }/style/images/createNew.png"></a>
        </div>
    </div>
</div>

<div class="Description">
	说明：<br>
	1，显示的列表按其sortOrder值升序排列。<br>
	2，可以通过上移与下移功能调整顺序。最上面的不能上移，最下面的不能下移。<br>
</div>



</body></html>
