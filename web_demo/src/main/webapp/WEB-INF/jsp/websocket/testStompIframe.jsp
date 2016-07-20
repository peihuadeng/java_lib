<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/system/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<!-- sockjs & stomp -->
<script src="lib/sockjs/sockjs-0.3.min.js"></script>
<script src="lib/sockjs/stomp.min.js"></script>
<script src="js/StompClient.js"></script><script type="text/javascript">
var stompClient = null;
var hasCounted = false;
var topic = "/topic/notice";
var messageMappingTemplate = "/app/notice/template";
var messageMappingAnnotation = "/app/notice/annotation";
var messageMappingInit = "/app/init";
var topicId = null;
var initTopicId = null;

//检测stomp client是否存在及连接，如不存在则创建并连接
function checkIframeStompClient(url) {
	//创建客户端
	if (stompClient == undefined || stompClient == null) {
		stompClient = new StompClient(url);
	}
	//连接服务器
	if (stompClient.isConnected() == false) {
		stompClient.connect();
	}
}

function receiveTopicMessage(data) {
	if (hasCounted == false) {
		top.connectedCount += 1;
		hasCounted = true;
	}
	var response = document.getElementById("response");
    var p = document.createElement("p");
    p.style.wordWrap = "break-word";
    p.appendChild(document.createTextNode("message topic: " + decodeURIComponent(data)));
    response.appendChild(p);
}

$(document).ready(function() {
	checkIframeStompClient("${pageContext.request.contextPath}/socket");
	topicId = stompClient.subscribe(topic, receiveTopicMessage);
	console.log(topicId);
	$("#init").removeAttr("disabled");
	$("#init").removeClass("btn-default disabled");
	$("#init").addClass("btn-primary");
	$("#sendMessage").removeAttr("disabled");
	$("#sendMessage").removeClass("btn-default disabled");
	$("#sendMessage").addClass("btn-primary");
	
	$("#init").click(function() {
		checkIframeStompClient("${pageContext.request.contextPath}/socket");
		initTopicId = stompClient.subscribe(messageMappingInit, receiveTopicMessage);
	});
	
	$("#sendMessage").click(function() {
		var destination = "";
		$(":radio[name='destination']:checked").each(function() {
			destination = $(this).val();
		});
		var message = $("#message").val();
		console.log("destination: " + destination + ", message: " + message);
		
		checkIframeStompClient("${pageContext.request.contextPath}/socket");
		if (stompClient.isConnected() == false) {
			alert("正在重连，请稍后发送。");
		}
		stompClient.sendMessage(destination, {}, message);
	});
});

//离开页面，取消订阅及断开链接
$(window).unload(function() {
	//取消订阅
	if (topicId != undefined && topicId != null && stompClient != null) {
		stompClient.unsubscribe(topicId);
	}

	if (initTopicId != undefined && initTopicId != null && stompClient != null) {
		stompClient.unsubscribe(initTopicId);
	}
	
	if (stompClient != null) {
		stompClient.disconnect();
	}
});

</script>
<title>test stomp</title>
</head>
<body>
<div>
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