<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/system/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
top.connectedCount = 0;
function addIframe() {
	var count = $("#count").val();
	var parent = $("#iframeContainer");
	
	for (var i = 0; i < count; i ++) {
		var dom = $("<iframe src=\"websocket/testStompIframe\" style=\"width: 400px; height: 300px;\"></iframe>");
		parent.append(dom);
	}
}

$(document).ready(function() {
	$("#start").click(addIframe);
	setInterval(function() {
		console.log("connectedCount: " + top.connectedCount);
	}, 1000);
});
</script>
<title>test stomp</title>
</head>
<body>
<div>
	<input id="count" type="text" />
	<button id="start" class="btn btn-primary">start</button>
	<div id="iframeContainer"></div>
</div>
</body>
</html>