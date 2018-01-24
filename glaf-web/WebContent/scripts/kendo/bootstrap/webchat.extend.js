/**
 * 基于websocket的聊天室
 */
var webchatFunc = {
	linkage:function(rule, params,args){
		var $this = pubsub.getJQObj(rule),
			key = "roomId";
		if(key in params){
			$this.webchat("reload",params[key]);
		}
	},
	sendMsg:function(rule, params,args){
		var $this = pubsub.getJQObj(rule);
		for(var key in params){
			params[key] && $this.webchat("sendMsg",params[key]);
		}
	},
	
	getUserName:function(rule, params,args){
		var $this = pubsub.getJQObj(rule.inid),
		    $opt = $this.data("webchat");
		return $opt.message.username;
		
	},
	getOnlineCount:function(rule, params,args){
		var $this = pubsub.getJQObj(rule.inid),
	    $opt = $this.data("webchat");
	    return $opt.message.onlineCount;
	},
	getChatUser:function(rule, params,args){
		var $this = pubsub.getJQObj(rule.inid),
	    $opt = $this.data("webchat");
	    return $opt.message.chatuser;
	},
	getChatUserMessage:function(rule, params,args){
		var $this = pubsub.getJQObj(rule.inid),
	    $opt = $this.data("webchat");
	    return $opt.message.chatUserMessage;
	},
	getOnlineUser:function(rule, params,args){
		var $this = pubsub.getJQObj(rule.inid),
	    $opt = $this.data("webchat"),str = "";
		$.each($opt.message.onlineUser,function(i,item){
			if(i == 0){
				str = str + item;
			}
			else if(i == $opt.message.onlineUser.length - 1){
				str = str + "," + item;
			}
			else{
				str = str + "," + item + ",";
			}
		});
	    return str;
	}
};
pubsub.sub("webchat", webchatFunc);