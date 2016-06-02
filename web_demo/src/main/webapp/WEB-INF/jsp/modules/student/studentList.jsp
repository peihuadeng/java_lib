<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/system/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>student list</title>
</head>
<body>
<a href="student/view" class="btn btn-primary">增加</a>
<c:choose>
	<c:when test="${empty page }">
		<div>学生列表为空！</div>
	</c:when>
	<c:otherwise>
		<table class="table table-striped table-bordered table-hover table-condensed" style="width: 700px;">
			<thead>
				<tr>
					<td>id</td>
					<td>学生名称</td>
					<td>年龄</td>
					<td>老师ID</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.results}" var="student">
					<tr>
						<td><a href="student/view?id=${student.id}">${student.id}</a></td>
						<td>${student.name}</td>
						<td>${student.age}</td>
						<td>${student.teacherId}</td>
						<td><a href="student/delete?id=${student.id}">删除</a></td>
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
						<li><a href="student/list?page.pageSize=${page.pageSize}&page.pageNo=${index}">${index}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</ul>
		
	</c:otherwise>
</c:choose>
</body>
</html>