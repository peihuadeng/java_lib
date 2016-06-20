<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/system/taglib.jsp" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<base href="<%=basePath%>">
<!-- jquery -->
<script src="lib/jquery/1.12.3/jquery-1.12.3.min.js"></script>
<script src="lib/jquery/1.12.3/jquery-migrate-1.3.0.min.js"></script>
<!-- bootstrap -->
<link rel="stylesheet" href="lib/bootstrap/3.3.5/dist/css/bootstrap.min.css">
<script src="lib/bootstrap/3.3.5/dist/js/bootstrap.min.js"></script>
<!-- jquery-validation -->
<script src="lib/jquery/jquery-validation/1.15.0/dist/jquery.validate.min.js"></script>
<script src="lib/jquery/jquery-validation/1.15.0/dist/additional-methods.min.js"></script>
<script src="lib/jquery/jquery-validation/1.15.0/dist/localization/messages_zh.min.js"></script>
<!-- select2 -->
<link rel="stylesheet" href="lib/jquery/select2/4.0.2/dist/css/select2.min.css">
<script src="lib/jquery/select2/4.0.2/dist/js/select2.full.min.js"></script>
<script src="lib/jquery/select2/4.0.2/dist/js/i18n/zh-CN.js"></script>
<!-- jbox -->
<link rel="stylesheet" href="lib/jquery/jbox/2.3/jBox/Skins/Default/jbox.css">
<script src="lib/jquery/jbox/2.3/jBox/jquery.jBox-2.3.min.js"></script>
<script src="lib/jquery/jbox/2.3/jBox/i18n/jquery.jBox-zh-CN.js"></script>
<!-- 日期组件 -->
<script src="lib/My97DatePicker/WdatePicker.js"></script>
<!-- common -->
<link rel="stylesheet" href="css/common.css">
<script src="js/common.js"></script>