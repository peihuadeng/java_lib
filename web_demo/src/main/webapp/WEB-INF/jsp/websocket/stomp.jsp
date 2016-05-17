<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/system/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
var stompClient = null;
var messageMapping = "/app/notice/annotation";
var subscribe_topic = "/app/init";
var message_topic = "/topic/notice";
var subscribe_topic_subscription = null;
var message_topic_subscription = null;

function setConnected(connected) {
	document.getElementById("connect").disabled = connected;
	document.getElementById("disconnect").disabled = !connected;
	document.getElementById("conversationDiv").style.visibility = connected ? "visible" : "hidden";
	document.getElementById("response").innerHTML = "";
}

function connectedCallback(frame) {
	console.log(frame);
	
	setConnected(true);
	
	//message topic
	message_topic_subscription = stompClient.subscribe(message_topic, function(data) {
		console.log(data);
		console.log(decodeURIComponent(JSON.stringify(data)));
		
		var response = document.getElementById('response');
        var p = document.createElement('p');
        p.style.wordWrap = 'break-word';
        p.appendChild(document.createTextNode("message topic: " + decodeURIComponent(data.body)));
        response.appendChild(p);
	});
	
	//subscribe topic 仅返回一次消息
	subscribe_topic_subscription = stompClient.subscribe(subscribe_topic, function(data) {
		console.log(data);
		console.log(decodeURIComponent(JSON.stringify(data)));
		
		var response = document.getElementById('response');
        var p = document.createElement('p');
        p.style.wordWrap = 'break-word';
        p.appendChild(document.createTextNode("subscribe topic: " + decodeURIComponent(data.body)));
        response.appendChild(p);
	});	
}

function errorCallback(error) {
	alert(JSON.stringify(error));
}

function connect() {
	var socket = new SockJS("${pageContext.request.contextPath}/socket");
	stompClient = Stomp.over(socket);
	var headers = {};
	
	stompClient.connect(headers, connectedCallback, errorCallback);
}

function sendName() {
	var value = document.getElementById("name").value;
	stompClient.send(messageMapping, {}, encodeURIComponent(value));
}

function disconnect() {
	if (subscribe_topic_subscription != null) {
		subscribe_topic_subscription.unsubscribe();
		subscribe_topic_subscription = null;
	}

	if (message_topic_subscription != null) {
		message_topic_subscription.unsubscribe();
		message_topic_subscription = null;
	}
	
	if (stompClient != null) {
		stompClient.disconnect(function() {
			alert("disconnected");
		});
		stompClient = null;
	}
	
	setConnected(false);
	console.log("disconnected");
}


</script>
<title>test stomp</title>
</head>
<body>
<div>    
    <div>        
        <button id="connect" onclick="connect();">Connect</button> 
       <button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>    
    </div>    
    <div id="conversationDiv" style="visibility:hidden;">
        <p>            
            <label>notice content?</label>        
        </p>        
        <p>            
              <textarea id="name" rows="5"></textarea>        
        </p>        
        <button id="sendName" onclick="sendName();">Send</button>        
        <p id="response"></p>
    </div>
</div>
</body>
</html>