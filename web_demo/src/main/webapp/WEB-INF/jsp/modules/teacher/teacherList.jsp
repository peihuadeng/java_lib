<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/system/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>teacher list</title>
</head>
<body>
<a href="teacher/view" class="btn btn-primary">增加</a>
<c:choose>
	<c:when test="${empty page }">
		<div>老师列表为空！</div>
	</c:when>
	<c:otherwise>
		<table class="table table-striped table-bordered table-hover table-condensed" style="width: 700px;">
			<thead>
				<tr>
					<td>id</td>
					<td>老师名称</td>
					<td>年龄</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.results}" var="teacher">
					<tr>
						<td><a href="teacher/view?id=${teacher.id}">${teacher.id}</a></td>
						<td>${teacher.name}</td>
						<td>${teacher.age}</td>
						<td><a href="teacher/delete?id=${teacher.id}">删除</a></td>
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
						<li><a href="teacher/list?page.pageSize=${page.pageSize}&page.pageNo=${index}">${index}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</ul>
		
	</c:otherwise>
</c:choose>
</body>
</html>