<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/system/taglib.jsp" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!-- （如jsp等）异常没有被SimpleMappingExceptionResolver 则不会经过sitemesh过滤 -->
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- jquery -->
<script src="lib/jquery/1.12.3/jquery-1.12.3.min.js"></script>
<script src="lib/jquery/1.12.3/jquery-migrate-1.3.0.min.js"></script>
<!-- bootstrap -->
<link rel="stylesheet" href="lib/bootstrap/3.3.5/dist/css/bootstrap.min.css">
<script src="lib/bootstrap/3.3.5/dist/js/bootstrap.min.js"></script>
<!-- common -->
<link rel="stylesheet" href="css/common.css">
<script src="js/common.js"></script>
<title>web_demo:服务器内部错误</title>
</head>
<body>
	<h1>500 - 服务器内部错误！</h1>
	<div>
		<button class="btn btn-default" onclick="history.go(-1)">返回</button>
	</div>
</body>
</html>