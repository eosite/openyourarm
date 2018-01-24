/**
 * 简易聊天室功能 
 * $("id").webchat(options); // 初始化 
 * $("id").webchat('sendMsg',message); //发送消息
 * $("id").webchat('reload',roomId); //重新进入房间
 */
(function($) {
	var pluginName = "webchat", pluginObj = "webSocket";

	$.fn[pluginName] = function(command, options) {
		if (!this.length) {
			return this;
		}
		if (typeof command === "object") {
			options = command;
			var opts = $.extend({}, $.fn[pluginName].defaults, options);
			var defined=options.htmldefined;
			var reg1 = /~F[^#]+}/g;
			if(typeof(defined)!="undefined"){
				var templateStr=options.htmldefined[0].htmldata.htmlVal;
				if(options.islist){
					if(templateStr !=""){
						var columns = templateStr.match(reg1);
						$.each(columns,function(i,item){
							templateStr = templateStr.replace(item,"{{item}}");
							
						});
						opts.userListTemp = templateStr;
					}
				}else{
					opts.userListTemp = "";
				}
			}
			var htmltitle = options.htmltitle;
			if(typeof(htmltitle)!="undefined"){
				var title = options.htmltitle[0].htmldata.htmlVal;
					title = title.replace("~F{userName}","{{userName}}");
					title = title.replace("~F{type}","{{type}}");
					title = title.replace("~F{onlineCount}","{{onlineCount}}");
					opts.noticeTemp =title;
			}
			var messagehtml =options.htmlmessage;
			if(typeof(messagehtml)!="undefined"){
				var htmlmessage=options.htmlmessage[0].htmldata.htmlVal;
				htmlmessage = title.replace("~F{user}","{{user}}");
				htmlmessage = title.replace("~F{message}","{{message}}");
				opts.messageTemp =htmlmessage;
			}
			
			this.each(function() {
				var $this = $(this);
				if (!$this.data(pluginName)) {
					$this.data(pluginName, opts);
					methods.__init__.call($this, opts);
				}
			});
		} else if (typeof command === "string" && methods[command]) {
			this.each(function() {
				var $this = $(this);
				methods[command].call($this, options);
			});
		} else {
			$.error('Method ' + command + ' does not exist on jQuery.' + pluginName + "!");
		}

		return this;
	};

	// 消息接口
	function analysisMessage(message) {
		message = JSON.parse(message);
		if (message.type == "message") { // 会话消息
			showChat.call(this, message.message);
		}
		if (message.type == "notice") { // 提示消息
			showNotice.call(this, message.message);
		}
		if (message.list != null && message.list != undefined) { // 在线列表
			showOnline.call(this, message.list);
		}
		var $this = this, options = $this.data(pluginName);
		options.events.connecteOnline();
	}

	/**
	 * 展示提示信息
	 */
	function showNotice(notice) {
		var $this = this, options = $this.data(pluginName), chat = $this.find(".mt_webchat_chat");
		//当前做简单处理，后期需要给用户自定义消息发送模板
		var str = options.noticeTemp.replace("{{userName}}", notice.userName).replace("{{onlineCount}}", notice.onlineCount).replace("{{type}}", notice.type=="enter"?"进入":"离开");
		chat.append(str);
		chat.scrollTop(chat[0].scrollHeight); // 让聊天区始终滚动到最下面
		options.message.username = notice.userName;
		options.message.onlineCount = notice.onlineCount
	}

	/**
	 * 展示会话信息
	 */
	function showChat(message) {
		var $this = this, options = $this.data(pluginName), chat = $this.find(".mt_webchat_chat");
		$this.find(".mt_webchat_input textarea").val(""); // 清空输入区
		var str = options.messageTemp.replace("{{user}}", message.from).replace("{{message}}", message.content);
		chat.append(str);
		chat.scrollTop(chat[0].scrollHeight); // 让聊天区始终滚动到最下面
		options.message.chatuser = message.from;
		options.message.chatUserMessage = message.content;
	}

	/**
	 * 展示在线列表
	 */
	function showOnline(list) {
		var $this = this, options = $this.data(pluginName), $userlist = $this.find(".mt_webchat_right"), str = "";
		$this.find(".mt_webchat_user").remove();// 清空在线列表
		$.each(list, function(index, item) {
			str += options.userListTemp.replace("{{item}}", item);
		});
		
		$userlist.append(str);
		options.message.onlineUser = list;
	}

	var methods = $.fn[pluginName].methods = {
			/**
			 * 发送消息 
			 */
		sendMsg : function(params) {
			var $this = $(this), opts = $this.data(pluginName), ws = $this.data(pluginObj);
			ws.send(JSON.stringify({
				message : {
					content : params, // 发送文本内容
					from : '', // 当前登录用户
					to : "", // 接收人,如果没有则置空,如果有多个接收人则用,分隔
					time : "" // 发送时间
				},
				type : "message"
			}));
		},
		/**
		 * 重新装载房间
		 */
		reload : function(roomId){
			var $this = this,opts = $this.data(pluginName);
			if(roomId){
				opts.roomId = roomId;
				methods.__init__.call($this,opts);
			}
		},
		__init__ : function(opts) {
			var $this = this,
			    $opts = $this.data(pluginName);
			$opts.visible ? $this.css("display","block") : $this.css("display","none"); 
			if (window.WebSocket) {
				//如果没有房间号 则不初始化
				if(!opts.roomId){
					return;
				}
				var ws = new WebSocket(opts.uri + "?roomId=" + opts.roomId);
				ws.onopen = function(evt) {
					console.log("连接成功");
				};
				ws.onmessage = function(evt) {
					analysisMessage.call($this, evt.data); // 解析后台传回的消息,并予以展示
				};
				ws.onerror = function(evt) {
					console.error("异常错误");
				};
				ws.onclose = function(evt) {
					console.log("连接关闭");
				};
				$this.data(pluginObj, ws);
				//绑定发送按钮
				var $txtarea = $this.find(".mt_webchat_input textarea"),
					$btn = $this.find(".mt_webchat_button input");
				$btn.on("click",function(){
					var val= $txtarea.val();
					if(val){
						methods.sendMsg.call($this,val);
					}
				});
				//绑定回车事件
				$txtarea.on("keydown",function(e){
					if(event.keyCode==13){ 
						$btn.trigger("click");
					} 
				});
			} else {
				alert("当前浏览器不支持！");
			}
		}
	};

	// default options
	var defaults = $.fn[pluginName].defaults = {
		uri : "", // 地址,一般为 ws://xxx/xxx
		roomId : "", // 房间号
		noticeTemp : '<div class="mt_webchat_notice"><i class="fa fa-bell"></i>&nbsp;[{{userName}}]{{type}}聊天室，当前在线人数为{{onlineCount}}位</div>',
		userListTemp : '<div class="mt_webchat_user" >{{item}}</div>',
		messageTemp : '<div class="mt_webchat_message"><font color="green">{{user}}:</font> {{message}}</div>',
	};

})(jQuery);