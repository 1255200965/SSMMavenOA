<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
    <title>用户列表</title>
    <%@include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body>

<div id="Title_bar">
    <div id="Title_bar_Head"> 
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img src="${pageContext.request.contextPath }/style/images/title_arrow.gif" height="13" border="0" width="13"> 用户管理
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="MainArea">
    <table class="TableStyle" cellpadding="0" cellspacing="0">
       
        <!-- 表头-->
        <thead>
            <tr id="TableTitle" align="center" valign="middle">
                <td width="100">登录名</td>
                <td width="100">姓名</td>
                <td width="100">所属部门</td>
                <td width="200">岗位</td>
                <td>备注</td>
                <td>相关操作</td>
            </tr>
        </thead>
        
        <!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="userList">
        <c:forEach items="${userList }" var="user">    
        <tr class="TableDetail1 demodata_record">
                <td>${user.loginName }&nbsp;</td>
                <td>${user.name }&nbsp;</td>
                <td>${user.department.name }&nbsp;</td>
                <td>
               	<c:forEach items="${user.roles }" var="role" varStatus="status">
               		<c:choose>
               			<c:when test="${status.last }">
               				${role.name }
               			</c:when>
               			<c:otherwise>
               				${role.name },
               			</c:otherwise>
               		</c:choose>
               	</c:forEach>
                </td>
                <td>${user.description }&nbsp;</td>
                <td><a onclick="return delConfirm()" href="list.html">删除</a>
                    <a href="saveUI.html">修改</a>
					<a href="#" onclick="return window.confirm('您确定要初始化密码为1234吗？')">初始化密码</a>
                </td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <!-- 其他功能超链接 -->
    <div id="TableTail">
        <div id="TableTail_inside">
            <a href="${pageContext.request.contextPath }/user/addUI.action"><img src="${pageContext.request.contextPath }/style/images/createNew.png"></a>
        </div>
    </div>
</div>



</body></html>