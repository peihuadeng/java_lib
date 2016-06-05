<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/system/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>teacher view</title>
</head>
<body>
<div style="padding-top: 50px;width: 500px;">
	<form action="teacher/save" method="post" role="form" class="form-horizontal">
		<c:if test="${not empty teacher.id}">
			<div class="form-group">
				<label for="id" class="col-sm-4 control-label">id:</label>
				<div class="col-sm-8">
					<input type="text" readonly="readonly" name="id" id="id" class="form-control"
					value="${teacher.id }" />
				</div>
			</div>
		</c:if>
		<div class="form-group">
			<label for="name" class="col-sm-4 control-label">老师名称:</label>
			<div class="col-sm-8">
				<input type="text" name="name" id="name" class="form-control" placeholder="请输入名称"
					value="${teacher.name}" maxlength="50" required/>
			</div>
		</div>
		<div class="form-group">
			<label for="age" class="col-sm-4 control-label">年龄:</label>
			<div class="col-sm-8">
				<input type="text" id="age" name="age" class="form-control" placeholder="请输入年龄"
					value="${teacher.age }"/>
			</div>
		</div>
		
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