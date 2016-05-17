<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/system/taglib.jsp" %>
<!-- 异常发生在contoller/service/dao层 则会经过sitemesh过滤 -->
<!DOCTYPE html>
<html>
<head>
<title>错误页面</title>
</head>
<body>
	<h1>出错了</h1>
	${exception.message}
	<div>
		<button class="btn btn-default" onclick="history.go(-1)">返回</button>
	</div>
</body>
</html>