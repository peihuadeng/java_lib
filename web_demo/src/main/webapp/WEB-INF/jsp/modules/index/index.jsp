<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/system/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>首页</title>
</head>
<body>
<h2>首页</h2>
登录管理：
<ul>
	<li><a href="sys/login" target="_blank">表单登录</a></li>
	<li><a href="cas" target="_blank">单点登录（需配置）</a></li>
	<li><a href="logout" target="_blank">登出</a></li>
	<li><a href="user" target="_blank">用户管理</a></li>
</ul>
druid统计分析(需管理员登录)：
<ul>
	<li><a href="druid/" target="_blank">druid统计分析</a></li>
</ul>
缓存demo：
<ul>
	<li><a href="student/" target="_blank">学生列表</a></li>
	<li><a href="teacher/" target="_blank">老师列表</a></li>
</ul>
文件上传demo：
<ul>
	<li><a href="webUploader/upload" target="_blank">简单文件上传</a></li>
	<li><a href="webUploader/uploadRich" target="_blank">富客户端文件上传</a></li>
</ul>
websocket demo：
<ul>
	<li><a href="html/websocket/websocket.html" target="_blank">简单websocket应用</a></li>
	<li><a href="websocket/stomp" target="_blank">websocket子协议stomp应用（使用sockjs兼容所有浏览器）</a></li>
	<li><a href="websocket/testStomp" target="_blank">websocket子协议stomp测试</a></li>
</ul>
</body>
</html>
