<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>student list</title>
</head>
<body>
<c:choose>
	<c:when test="${empty studentList }">
		<div>用户列表为空！</div>
	</c:when>
	<c:otherwise>
		<table>
			<thead>
				<tr>
					<td>id</td>
					<td>name</td>
					<td>age</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${studentList}" var="student">
					<tr>
						<td><a href="student/view?id=${student.id}">${student.id}</a></td>
						<td>${student.name}</td>
						<td>${student.age}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:otherwise>
</c:choose>
</body>
</html>