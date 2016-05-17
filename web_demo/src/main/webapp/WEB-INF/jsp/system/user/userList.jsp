<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/system/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>user list</title>
</head>
<body>
<a href="user/view" class="btn btn-primary">增加</a>
<c:choose>
	<c:when test="${empty page }">
		<div>用户列表为空！</div>
	</c:when>
	<c:otherwise>
		<table class="table table-striped table-bordered table-hover table-condensed" style="width: 700px;">
			<thead>
				<tr>
					<td>id</td>
					<td>用户名</td>
					<td>密码</td>
					<td>混合码</td>
					<td>锁定</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.results}" var="user">
					<tr>
						<td><a href="user/view?id=${user.id}">${user.id}</a></td>
						<td>${user.username}</td>
						<td>${user.password}</td>
						<td>${user.salt}</td>
						<td>${user.locked}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<ul class="pagination">
			<c:forEach begin="1" end="${page.totalPage}" step="1" var="index">
				<c:choose>
					<c:when test="${index == page.pageNo}">
						<li class="active"><a>${index}</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="user/list?page.pageSize=${page.pageSize}&page.pageNo=${index}">${index}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</ul>
		
	</c:otherwise>
</c:choose>
</body>
</html>