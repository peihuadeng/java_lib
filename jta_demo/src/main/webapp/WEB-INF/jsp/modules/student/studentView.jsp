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
<title>student view</title>
</head>
<body>
	<form action="student/save" method="post">
		<div>
			<span>id:</span> <input type="text" readonly="readonly" name="id"
				value="${student.id }" />
		</div>
		<div>
			<span>name:</span> <input type="text" name="name"
				value="${student.name}" />
		</div>
		<div>
			<span>age:</span> <input type="number" name="age"
				value="${student.age }" />
		</div>
		<div>
			<div>
				<input type="submit" value="submit" />
				<button onclick="history.go(-1)">返回</button>
			</div>
		</div>
	</form>

</body>
</html>