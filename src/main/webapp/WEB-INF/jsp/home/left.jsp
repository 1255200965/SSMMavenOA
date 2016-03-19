<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="hp" uri="/WEB-INF/hp.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html><head>
<title>导航菜单</title>
<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
<script language="JavaScript" src="script/menu.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/style/blue/menu.css">
<script type="text/javascript">
	function menuClick(menu) {
		/* $(menu).next().toggle(); */
 		$(menu).next().slideToggle();
	}
</script>
</head>
<body style="margin: 0">
<div id="Menu">
    <ul id="MenuUl">
    	<c:forEach items="${applicationScope.topPrivilegeList }" var="topPrivilege">
    	<c:if test="${hp:hasPrivilegeByName(user, topPrivilege.name) }">
        <li class="level1">
            <div onclick="menuClick(this)" class="level1Style"><img src="${pageContext.request.contextPath }/style/images/MenuIcon/${topPrivilege.id }.gif" class="Icon">${topPrivilege.name }</div>
            <ul style="display: none;" class="MenuLevel2">
            	<c:forEach items="${topPrivilege.children }" var="child">
            	<c:if test="${hp:hasPrivilegeByName(user, child.name) }">
                <li class="level2">
                    <div class="level2Style"><img src="${pageContext.request.contextPath }/style/images/MenuIcon/menu_arrow_single.gif"> 
                    	<a target="right" href="${pageContext.request.contextPath }${child.url }">${child.name }</a>
                    </div>
                </li>
                </c:if>
                </c:forEach>
            </ul>
        </li>
        </c:if>
        </c:forEach>
    </ul>
</div>


</body></html>
