<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/system/taglib.jsp" %>
<html>
<head>
    <title>用户登录</title>
	<script type="text/javascript">
	function changeValImage() {
		$("#valImage").attr("src", "sys/valImage?" + new Date().getTime());
	}
	
	$(document).ready(function() {
		$("#valImage").click(changeValImage);
		$("#valImageLink").click(changeValImage);
	});
	</script>
</head>
<body>
<div class="center-block" style="padding-top: 200px;width: 400px;">
	<h2 style="text-align: center; font-weight: bold; padding-bottom: 10px;">用户登录</h2>
	<form action="" method="post" role="form" class="form-horizontal">
		<div id="error_box" class="col-sm-offset-2 col-sm-10 alert alert-danger text-danger ${empty error ? 'hidden' : 'show'}">
			<button type="button" class="close" data-dismiss="alert" 
		      aria-hidden="true">
		      &times;
		   </button>
		   ${error}
		</div>
		<div class="form-group">
			<label for="username" class="col-sm-4 control-label">用户名:</label>
			<div class="col-sm-8">
				<input type="text" name="username" id="username" class="form-control" placeholder="请输入用户名"
					maxlength="50" required/>
			</div>
		</div>
		<div class="form-group">
			<label for="password" class="col-sm-4 control-label">密码:</label>
			<div class="col-sm-8">
				<input type="password" id="password" name="password" class="form-control" placeholder="请输入密码"
					maxlength="50" required/>
			</div>
		</div>
		
		<c:if test="${not empty sessionScope.validation_code_enabled }">
		<div class="form-group">
			<label for="password" class="col-sm-4 control-label">验证码:</label>
			<div class="col-sm-4">
				<input type="text" id="validation_code" name="validation_code" class="form-control" placeholder="请输入验证码"
					maxlength="4" required/>
			</div>
			<div class="col-sm-4 control-label" style="text-align: left;">
				<img id="valImage" title="点击换一张" src="sys/valImage"><a id="valImageLink" href="javascript:;">换一张</a>
			</div>
		</div>
		</c:if>
		
		<div class="form-group">
			<div class="col-sm-offset-4 col-sm-8">
				<label for="rememberMe"><input type="checkbox" id="rememberMe" name="rememberMe" value="true">&nbsp;&nbsp;记住我（一个星期）</label>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-primary btn-block">登录</button>
			</div>
		</div>
	</form>
</div>
</body>
</html>