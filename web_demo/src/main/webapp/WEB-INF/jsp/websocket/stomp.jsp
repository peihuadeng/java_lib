<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/system/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<script src="js/stompClient.js"></script><script type="text/javascript">
var topic = "/topic/notice";
var topicId = null;
function receiveTopicMessage(data) {
	var response = document.getElementById('response');
    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.appendChild(document.createTextNode("message topic: " + decodeURIComponent(data)));
    response.appendChild(p);
}

$(document).ready(function() {
	$("#subcribe").click(function() {
		checkStompClient("${pageContext.request.contextPath}/socket");
		topicId = top.stompClient.subscribe(topic, receiveTopicMessage);
		console.log(topicId);
		$("#subcribe").attr("disabled", "disabled");
		$("#subcribe").addClass("btn-default disabled");
		$("#subcribe").removeClass("btn-primary");
		$("#unsubcribe").removeAttr("disabled");
		$("#unsubcribe").removeClass("btn-default disabled");
		$("#unsubcribe").addClass("btn-primary");
	});

	$("#unsubcribe").click(function() {
		checkStompClient("${pageContext.request.contextPath}/socket");
		top.stompClient.unsubscribe(topicId);
		$("#subcribe").removeAttr("disabled");
		$("#subcribe").removeClass("btn-default disabled");
		$("#subcribe").addClass("btn-primary");
		$("#unsubcribe").attr("disabled", "disabled");
		$("#unsubcribe").addClass("btn-default disabled");
		$("#unsubcribe").removeClass("btn-primary");
	});
});
//离开页面，断开链接
$(window).bind("beforeunload", function() {
	if (top == this && top.stompClient != null) {
		top.stompClient.disconnect();
	}
});

</script>
<title>test stomp</title>
</head>
<body>
<div>
	<button id="subcribe" class="btn btn-primary">订阅</button>
	<button id="unsubcribe" disabled="disabled" class="btn btn-default disabled">取消订阅</button>
    <div id="conversationDiv">
        <p id="response"></p>
    </div>
</div>
</body>
</html>