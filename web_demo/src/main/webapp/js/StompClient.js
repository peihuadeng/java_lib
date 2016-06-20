/**
 * 订阅/发布服务客户端
 * 支持自动重连，重连后自动重新订阅原有主题及回调原有处理函数
 * @param url
 * @returns
 */

//订阅/发布服务客户端
function StompClient(url) {
	//订阅服务对象定义
	function SubscriptionService(id, topic, callback) {
		this.id = id;
		this.topic = topic;
		this.callback = callback;
		this.subscription = null;
	}
	
	var self = this;
	self._url = url;
	self._stomp = null;
	self._isConnected = false;
	self._reconnectTimer = null;
	self._subscriptionCount = 0;
	self._subscriptionMap = new Map();
	//连接成功回调函数
	self._connectedCallback = function(frame) {
		console.log("stomp client connected!");
		if (self._reconnectTimer != null) {
			clearTimeout(self._reconnectTimer);
			self._reconnectTimer = null;
		}
		self._isConnected = true;
		//重新订阅原有主题
		if (self._subscriptionMap == null) {
			self._subscriptionMap = new Map();			
		} else {
			self._subscriptionMap.forEach(function(service) {
				self.subscribe(service.topic, service.callback, service.id);
			});
		}
	};
	//连接出错回调函数
	self._errrCallback = function(error) {
		console.log("stomp client connect fail! error: " + JSON.stringify(error));
		self._isConnected = false;
		//3000ms后重连，直到连接成功为止
		self._reconnectTimer = setTimeout(self.connect, 3000);
	};
	//建立连接，能自动重连
	self.connect = function() {
		if (self._isConnected == true) {
			//已建立连接，直接返回
			return;
		}
		
		var socket = new SockJS(self._url, undefined, {debug:false});
		self._stomp = Stomp.over(socket);
		self._stomp.debug = function(logMessage) {
			//stomp日志
			console.log(logMessage);
		};
		var headers = {};
		
		console.log("stomp client connecting...");
		self._stomp.connect(headers, self._connectedCallback, self._errrCallback);
	};
	//断开连接
	self.disconnect = function() {
		//取消所有订阅的主题
		if (self._subscriptionMap != null) {
			self._subscriptionMap.forEach(function(service) {
				if (service.subscription != null) {
					service.subscription.unsubscribe();					
				}
			});
			self._subscriptionMap = new Map();
		}
		//真正断开连接
		if (self._stomp != null) {
			self._stomp.disconnect(function() {
				//连接断开回调函数
				console.log("stomp client disconnected success!");
				self._isConnected = false;
				self._stomp = null;
			});
		}
	};
	//返回连接状态
	self.isConnected = function() {
		return self._isConnected;
	}
	//订阅，返回订阅id
	self.subscribe = function(topic, callback, id) {
		//创建service
		if (id == undefined) {
			id = self._subscriptionCount;
			self._subscriptionCount ++;
		}
		var service = new SubscriptionService(id, topic, callback);

		if (self._isConnected) {
			//订阅
			var subscription = self._stomp.subscribe(topic, function(message) {
				callback(message.body);
			});
			service.subscription = subscription;			
		}
		//放置到map中
		self._subscriptionMap.set(id, service);
		
		return id;
	};
	//取消订阅，传入订阅id
	self.unsubscribe = function(id) {
		if (id != null) {
			var service = self._subscriptionMap.get(id);
			if (service != null && service.subscription != null) {
				service.subscription.unsubscribe();				
			}
			self._subscriptionMap.delete(id);
		}
	};
	//发送消息
	self.sendMessage = function(destination, headers, message) {
		if (self._isConnected == false) {
			return false;
		}
		
		self._stomp.send(destination, headers, encodeURIComponent(message));
	};
}