<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html><head>
	<title>查看主题：${topic.title }</title>
	<%@include file="/WEB-INF/jsp/public/commons.jspf" %>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/style/blue/forum.css">
	
	<script language="javascript" src="${pageContext.request.contextPath }/fckeditor/fckeditor.js" charset="utf-8"></script>
    <script type="text/javascript">
		$(function(){
			var fck = new FCKeditor("content");
			fck.Width = "90%";
			fck.ToolbarSet = "bbs";
			fck.BasePath = "${pageContext.request.contextPath }/fckeditor/";
			fck.ReplaceTextarea();
		});
    </script>
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img src="${pageContext.request.contextPath }/style/images/title_arrow.gif" height="13" border="0" width="13"> 查看主题
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--内容显示-->	
<div id="MainArea">
	<div id="PageHead"></div>
	<center>
		<div class="ItemBlock_Title1" style="width: 98%">
			<font class="MenuPoint"> &gt; </font>
			<a href="${pageContext.request.contextPath }/forum/list.action">论坛</a>
			<font class="MenuPoint"> &gt; </font>
			<a href="${pageContext.request.contextPath }/forum/show.action?id=${topic.forumId }">${forum.name }</a>
			<font class="MenuPoint"> &gt;&gt; </font>
			帖子阅读
			<span style="margin-left:30px;"><a href="${pageContext.request.contextPath }/topic/addUI.action?forumId=${forum.id }">
				<img src="${pageContext.request.contextPath }/style/blue/images/button/publishNewTopic.png" align="absmiddle"></a>
			</span>
		</div>
		
		<div class="ForumPageTableBorder dataContainer" datakey="replyList">
		
			<!--显示主题标题等-->
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tbody><tr valign="bottom">
				<td class="ForumPageTableTitleLeft" width="3">&nbsp;</td>
					<td class="ForumPageTableTitle"><b>本帖主题：${topic.title }</b></td>
					<td class="ForumPageTableTitle" style="padding-right:12px;" align="right">
						<a class="detail" href="${pageContext.request.contextPath }/reply/addUI.action?topicId=${topic.id }"><img src="${pageContext.request.contextPath }/style/images/reply.gif" border="0">回复</a>
						<a href="moveUI.html"><img src="${pageContext.request.contextPath }/style/images/edit.gif" border="0">移动到其他版块</a>
						<a href="#" onclick="return confirm('要把本主题设为精华吗？')"><img src="${pageContext.request.contextPath }/style/images/forum_hot.gif" border="0">精华</a>
						<a href="#" onclick="return confirm('要把本主题设为置顶吗？')"><img src="${pageContext.request.contextPath }/style/images/forum_top.gif" border="0">置顶</a>
						<a href="#" onclick="return confirm('要把本主题设为普通吗？')"><img src="${pageContext.request.contextPath }/style/images/forum_comm.gif" border="0">普通</a>
					</td>
					<td class="ForumPageTableTitleRight" width="3">&nbsp;</td>
				</tr>
				<tr class="ForumPageTableTitleLine" height="1"><td colspan="4"></td></tr>
			</tbody></table>

			<!-- ~~~~~~~~~~~~~~~ 显示主帖 ~~~~~~~~~~~~~~~ -->
			<!-- 只是第一页显示主贴，其他页不显示 -->
			<c:if test="${page.pageNum eq 1 }">
			<div class="ListArea">
				<table border="0" cellpadding="0" cellspacing="1" width="100%">
					<tbody><tr>
						<td rowspan="3" class="PhotoArea" align="center" valign="top" width="130">
							<!--作者头像-->
							<div class="AuthorPhoto">
								<img src="${pageContext.request.contextPath }/style/images/defaultAvatar.gif" onerror="this.onerror=null; this.src='${pageContext.request.contextPath }/style/images/defaultAvatar.gif';" height="110" border="0" width="110">
							</div>
							<!--作者名称-->
							<div class="AuthorName">${author.name}</div>
						</td>
						<td align="center">
							<ul class="TopicFunc">
								<!--操作列表-->
								<li class="TopicFuncLi">
									<a class="detail" href="${pageContext.request.contextPath }/BBS_Topic/saveUI.html"><img src="${pageContext.request.contextPath }/style/images/edit.gif" border="0">编辑</a>
									<a class="detail" href="#" onclick="return confirm('确定要删除本帖吗？')"><img src="${pageContext.request.contextPath }/style/images/delete.gif" border="0">删除</a>
								</li>
								<!-- 文章表情与标题 -->
								<li class="TopicSubject">
									${topic.title }
								</li>
							</ul>
						</td>
					</tr>
					<tr><!-- 文章内容 -->
						<td align="center" valign="top">
							<div class="Content">${topic.content }</div>
						</td>
					</tr>
					<tr><!--显示楼层等信息-->
						<td class="Footer" align="center" height="28" valign="bottom">
							<ul style="margin: 0px; width: 98%;">
								<li style="float: left; line-height:18px;"><font color="#C30000">[楼主]</font>
									<fmt:formatDate value="${topic.postTime}" pattern="yyyy-MM-dd HH:mm:ss" />
								</li>
								<li style="float: right;"><a href="javascript:scroll(0,0)">
									<img src="${pageContext.request.contextPath }/style/images/top.gif" border="0"></a>
								</li>
							</ul>
						</td>
					</tr>
				</tbody></table>
			</div>
			</c:if>
			<!-- ~~~~~~~~~~~~~~~ 显示主帖结束 ~~~~~~~~~~~~~~~ -->


			<!-- ~~~~~~~~~~~~~~~ 显示回复列表 ~~~~~~~~~~~~~~~ -->
			
			<!-- ~~~~~~~~~~~~~~~ 显示回复列表结束 ~~~~~~~~~~~~~~~ -->
		<c:forEach items="${page.list }" var="reply" varStatus="status">
		<div class="ListArea demodata_record">
				<table border="0" cellpadding="0" cellspacing="1" width="100%">
					<tbody><tr>
						<td rowspan="3" class="PhotoArea" align="center" valign="top" width="130">
							<!--作者头像-->
							<div class="AuthorPhoto">
								<img src="${pageContext.request.contextPath }/style/images/defaultAvatar.gif" onerror="this.onerror=null; this.src='${pageContext.request.contextPath }/style/images/defaultAvatar.gif';" height="110" border="0" width="110">
							</div>
							<!--作者名称-->
							<div class="AuthorName">${reply.author.name }</div>
						</td>
						<td align="center">
							<ul class="TopicFunc">
								<!--操作列表-->
								<li class="TopicFuncLi">
									<a class="detail" href="${pageContext.request.contextPath }/BBS_Topic/saveUI.html"><img src="${pageContext.request.contextPath }/style/images/edit.gif" border="0">编辑</a>
									<a class="detail" href="#" onclick="return confirm('确定要删除本帖吗？')"><img src="${pageContext.request.contextPath }/style/images/delete.gif" border="0">删除</a>
								</li>
								<!-- 文章表情与标题 -->
								<li class="TopicSubject">
									${reply.title }
								</li>
							</ul>
						</td>
					</tr>
					<tr><!-- 文章内容 -->
						<td align="center" valign="top">
							<div class="Content">${reply.content }</div>
						</td>
					</tr>
					<tr><!--显示楼层等信息-->
						<td class="Footer" align="center" height="28" valign="bottom">
							<ul style="margin: 0px; width: 98%;">
								<li style="float: left; line-height:18px;"><font color="#C30000">[${(page.pageNum - 1) * page.pageSize + status.count }楼]</font>
									<fmt:formatDate value="${reply.postTime }" pattern="yyyy-MM-dd HH:mm:ss" />
								</li>
								<li style="float: right;"><a href="javascript:scroll(0,0)">
									<img src="${pageContext.request.contextPath }/style/images/top.gif" border="0"></a>
								</li>
							</ul>
						</td>
					</tr>
				</tbody></table>
		</div>
		</c:forEach>
		</div>

		<!--分页信息-->
		<%@ include  file="/WEB-INF/jsp/public/pageView.jspf"%>
		<form action="${pageContext.request.contextPath }/topic/show.action" method="post">
			<!-- 为show方法传递一个隐藏参数id,表单提交之后才能正常分页，不然分页的数据乱掉 -->
			<input type="hidden" name="id" value="${topic.id }">
		</form>

		<div class="ForumPageTableBorder" style="margin-top: 25px;">
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tbody><tr valign="bottom">
					<td class="ForumPageTableTitleLeft" width="3">&nbsp;</td>
					<td class="ForumPageTableTitle"><b>快速回复</b></td>
					<td class="ForumPageTableTitleRight" width="3">&nbsp;</td>
				</tr>
				<tr class="ForumPageTableTitleLine" height="1">
					<td colspan="3"></td>
				</tr>
			</tbody></table>
		</div>
	</center>
			
	<!--快速回复-->
	<div class="QuictReply">
	<form action="${pageContext.request.contextPath }/reply/add.action">
		<input type="hidden" name="topicId" value="${topic.id }">
		<div style="padding-left: 3px;">
			<table class="TableStyle" border="0" cellpadding="5" cellspacing="1" width="98%">
				<tbody><tr class="Tint" height="30">
					<td class="Deep" width="50px"><b>标题</b></td>
					<td class="no_color_bg">
						<input name="title" class="InputStyle" value="回复：${topic.title }" style="width:90%" type="text" readonly="readonly">
					</td>
				</tr>
				<tr class="Tint" height="200">
					<td rowspan="2" class="Deep" valign="top"><b>内容</b></td>
					<td class="no_color_bg" valign="top">
						<textarea name="content" style="width: 95%; height: 300px;"></textarea>
					</td>
				</tr>
				<tr class="Tint" height="30">
					<td class="no_color_bg" colspan="2" align="center">
						<input src="${pageContext.request.contextPath }/style/blue/images/button/submit.PNG" style="margin-right:15px;" type="image">
					</td>
				</tr>
			</tbody></table>
		</div>
	</form>
	</div>
</div>

<div class="Description">
	说明：<br>
	1，主帖只在第一页显示。<br>
	2，只有是管理员才可以进行“移动”、“编辑”、“删除”、“精华”、“置顶”的操作。<br>
	3，删除主帖，就会删除所有的跟帖（回复）。<br>
</div>



<iframe scrolling="no" src="javascript:void(0)" style="margin: 0px; padding: 0px; border: 0px none; background-color: transparent; background-image: none; height: 0px; width: 0px; position: absolute; z-index: 10000;" frameborder="0"></iframe><iframe scrolling="no" src="javascript:void(0)" style="margin: 0px; padding: 0px; border: 0px none; background-color: transparent; background-image: none; height: 0px; width: 0px; position: absolute; z-index: 10000;" frameborder="0"></iframe><iframe scrolling="no" src="javascript:void(0)" style="margin: 0px; padding: 0px; border: 0px none; background-color: transparent; background-image: none; height: 0px; width: 0px; position: absolute; z-index: 10000;" frameborder="0"></iframe><iframe scrolling="no" src="javascript:void(0)" style="margin: 0px; padding: 0px; border: 0px none; background-color: transparent; background-image: none; height: 0px; width: 0px; position: absolute; z-index: 10000;" frameborder="0"></iframe><iframe scrolling="no" src="javascript:void(0)" style="margin: 0px; padding: 0px; border: 0px none; background-color: transparent; background-image: none; height: 0px; width: 0px; position: absolute; z-index: 10000;" frameborder="0"></iframe></body></html>
