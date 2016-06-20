<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/system/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<!-- sockjs & stomp -->
<script src="lib/sockjs/sockjs-0.3.min.js"></script>
<script src="lib/sockjs/stomp.min.js"></script>
<script src="js/StompClient.js"></script><script type="text/javascript">
var topic = "/topic/notice";
var messageMappingTemplate = "/app/notice/template";
var messageMappingAnnotation = "/app/notice/annotation";
var messageMappingInit = "/app/init";
var topicId = null;
var initTopicId = null;

function receiveTopicMessage(data) {
	var response = document.getElementById("response");
    var p = document.createElement("p");
    p.style.wordWrap = "break-word";
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
		$("#init").removeAttr("disabled");
		$("#init").removeClass("btn-default disabled");
		$("#init").addClass("btn-primary");
		$("#sendMessage").removeAttr("disabled");
		$("#sendMessage").removeClass("btn-default disabled");
		$("#sendMessage").addClass("btn-primary");
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
		$("#init").attr("disabled", "disabled");
		$("#init").addClass("btn-default disabled");
		$("#init").removeClass("btn-primary");
		$("#sendMessage").attr("disabled", "disabled");
		$("#sendMessage").addClass("btn-default disabled");
		$("#sendMessage").removeClass("btn-primary");
	});
	
	$("#init").click(function() {
		checkStompClient("${pageContext.request.contextPath}/socket");
		initTopicId = top.stompClient.subscribe(messageMappingInit, receiveTopicMessage);
	});
	
	$("#sendMessage").click(function() {
		var destination = "";
		$(":radio[name='destination']:checked").each(function() {
			destination = $(this).val();
		});
		var message = $("#message").val();
		console.log("destination: " + destination + ", message: " + message);
		
		checkStompClient("${pageContext.request.contextPath}/socket");
		if (stompClient.isConnected() == false) {
			alert("正在重连，请稍后发送。");
		}
		stompClient.sendMessage(destination, {}, message);
	});
	
});

var stompClient = null;
//离开页面，取消订阅及断开链接
$(window).bind("beforeunload", function() {
	//取消订阅
	if (topicId != undefined && topicId != null && top.stompClient != null) {
		top.stompClient.unsubscribe(topicId);
	}

	if (initTopicId != undefined && initTopicId != null && top.stompClient != null) {
		top.stompClient.unsubscribe(initTopicId);
	}
	
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
    <button id="init" disabled="disabled" class="btn btn-default disabled">init</button>
    <div id="conversationDiv">
    	<p>            
              <textarea id="message" rows="5"></textarea>        
        </p>
        <label><input type="radio" name="destination" value="/app/notice/template" checked="checked">/app/notice/template</label>
        <label><input type="radio" name="destination" value="/app/notice/annotation">/app/notice/annotation</label>
        <br/>
        <button id="sendMessage" disabled="disabled" class="btn btn-default disabled">发送</button>
        <p id="response"></p>
    </div>
</div>
</body>
</html>