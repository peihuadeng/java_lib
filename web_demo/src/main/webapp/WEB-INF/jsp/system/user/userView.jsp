<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/system/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>user view</title>
</head>
<body>
<div style="padding-top: 50px;width: 500px;">
	<form action="user/save" method="post" role="form" class="form-horizontal">
		<c:if test="${not empty user.id}">
			<div class="form-group">
				<label for="id" class="col-sm-4 control-label">id:</label>
				<div class="col-sm-8">
					<input type="text" readonly="readonly" name="id" id="id" class="form-control"
					value="${user.id }" />
				</div>
			</div>
		</c:if>
		<div class="form-group">
			<label for="username" class="col-sm-4 control-label">用户名:</label>
			<div class="col-sm-8">
				<input type="text" name="username" id="username" class="form-control" placeholder="请输入名称"
					value="${user.username}" maxlength="50" required/>
			</div>
		</div>
		<div class="form-group">
			<label for="password_show" class="col-sm-4 control-label">加密密码:</label>
			<div class="col-sm-8">
				<input type="text" id="password_show" readonly="readonly" class="form-control" placeholder="密码不存在"
					value="${user.password }"/>
			</div>
		</div>
		<div class="form-group">
			<label for="password" class="col-sm-4 control-label">新密码:</label>
			<div class="col-sm-8">
				<input type="password" id="password" name="password" class="form-control" placeholder="请输入新密码"
					maxlength="50" />
			</div>
		</div>
		
		<div class="form-group">
			<label for="salt" class="col-sm-4 control-label">混合码:</label>
			<div class="col-sm-8">
				<input type="text" name="salt" id="salt" readonly="readonly" class="form-control" placeholder="混合码不存在"
					value="${user.salt }" />
			</div>
		</div>
		
		<div class="form-group">
			<label for="locked" class="col-sm-4 control-label">是否锁定:</label>
			<div class="col-sm-8">
				<input type="text" id="locked" name="locked" readonly="readonly" class="form-control"
					value="${user.locked }" />
			</div>
		</div>
		
		<!-- <div class="form-group">
			<label for="age" class="col-sm-4 control-label">测试select2:</label>
			<div class="col-sm-8">
				<select class="form-control" data-placeholder="请选择">
					<option></option>
					<option>1</option>
					<option>2</option>
					<option>3</option>
					<option>4</option>
				</select>
			</div>
		</div>
		
		<div class="form-group">
			<label for="age" class="col-sm-4 control-label">测试日期组件:</label>
			<div class="col-sm-8">
				<input type="text" class="form-control" readonly="readonly" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});"/>
			</div>
		</div> -->
		
		<div class="form-group">
			<div class="col-sm-offset-4 col-sm-8">
				<button type="submit" class="btn btn-primary">提交</button>
				<button type="button" class="btn btn-default" onclick="history.go(-1)">返回</button>
			</div>
		</div>
	</form>
</div>
</body>
</html>