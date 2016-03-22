<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
    <META http-equiv=Content-Type CONTENT="text/html; charset=gbk" />
	<TITLE>Itcast OA</TITLE>
	<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
	<LINK HREF="${pageContext.request.contextPath }/style/blue/login.css" type=text/css rel=stylesheet />
	<script type="text/javascript">
		/* 页面加载完时登录的用户名输入框自动获取光标焦点 */
		$(function () {
			document.forms[0].loginName.focus();
		});
		
		/* 登录页面被嵌套问题:被嵌套是就刷新上级窗口 */
		if (window.parent != window) {
			window.parent.location.reload(true);			
		}
	</script>
</HEAD>

<BODY LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 CLASS=PageBody >
<FORM METHOD="post" NAME="actForm" ACTION="${pageContext.request.contextPath }/user/login.action">
    <DIV ID="CenterAreaBg">
        <DIV ID="CenterArea">
            <DIV ID="LogoImg"><IMG BORDER="0" SRC="${pageContext.request.contextPath }/style/blue/images/logo.png" /></DIV>
            <DIV ID="LoginInfo">
                <TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0 width=100%>
                	<tr>
                		<td colspan="3"><%-- 显示错误信息 --%>
                			<font color="red">${msg }</font>
                		</td>
                	</tr>
                    <TR>
                        <TD width=45 CLASS="Subject"><IMG BORDER="0" SRC="${pageContext.request.contextPath }/style/blue/images/login/userId.gif" /></TD>
                        <TD><INPUT SIZE="20" CLASS="TextField" TYPE="text" NAME="loginName" value="${loginName }" /></TD>
                        <TD ROWSPAN="2" STYLE="padding-left:10px;"><INPUT TYPE="image" SRC="${pageContext.request.contextPath }/style/blue/images/login/userLogin_button.gif"/></TD>
                    </TR>
                    <TR>
                        <TD CLASS="Subject"><IMG BORDER="0" SRC="${pageContext.request.contextPath }/style/blue/images/login/password.gif" /></TD>
                        <TD><INPUT SIZE="20" CLASS="TextField" TYPE="password" NAME="password" value="${password }" /></TD>
                    </TR>
                </TABLE>
            </DIV>
            <DIV ID="CopyRight"><A HREF="javascript:void(0)">&copy; 2016 版权所有 hp_oa</A></DIV>
        </DIV>
    </DIV>
</FORM>
</BODY>

</HTML>


