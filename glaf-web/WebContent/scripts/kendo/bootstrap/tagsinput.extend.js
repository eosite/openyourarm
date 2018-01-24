var tagsinputObj = {
	
	defaults:{
		visible:true,
		enabled:true,
		defaultVal:''
	},
	init : function(rule, args) {
		var options = args[0],$id = $("#" + rule.inid);
		var opts = $.extend(true,{},tagsinputObj.defaults, options);
		
		tagsinputObj.setValue($id,opts.defaultVal);
		tagsinputObj.display($id,opts.visible);
		$id.data('tagsinput',{"element":$id[0],"defaults":tagsinputObj.defaults,"options":opts});
	},
	getValue : function(rule, args) {
		var $id = (rule instanceof $)?rule:pubsub.getJQObj(rule, true);
		var jq = tagsinputObj.getOject.call($id);
		return jq.val();
	},
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		tagsinputObj.display(rule,false);
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		tagsinputObj.display(rule,true);
	},
	tclear: function(rule, args) { //清空
		var $id = (rule instanceof $)?rule:pubsub.getJQObj(rule);
		var jq =  tagsinputObj.getOject.call($id);
		jq.attr("tclear", "tclear");
		jq.tagsinput('removeAll');
	},
	setValue : function(rule,args){
		var jq,r,v="";
		jq = tagsinputObj.getOject.call((rule instanceof $)?rule:pubsub.getJQObj(rule));
		jq.attr("setValue", "setValue");
		jq.tagsinput('removeAll');
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			jq.tagsinput('add', v);
		} else if(rule instanceof $){
			jq.tagsinput('add', args);
		}
	},
	appendValue : function(rule,args){
		var $id = (rule instanceof $)?rule:pubsub.getJQObj(rule),jq,r,v;
		jq = tagsinputObj.getOject.call($id);
		jq.attr("setValue", "setValue");
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			jq.tagsinput('add', v);
		} else if(rule instanceof $){
			jq.tagsinput('add', args);
		}
	},
	display : function(rule,args){    //是否显示
		var $id = (rule instanceof $)?rule:pubsub.getJQObj(rule);
		if (!args){    
			$id.css("display", "none");
		}else{
			$id.css("display", "");
		}
	},
//	disabled : function(bl){    //是否启用   
//		if(!bl){
////			jq.attr("disabled","disabled");		
//			this.css("cursor","not-allowed");
//		}else{
////			jq.removeAttr("disabled");
//			this.css("cursor","");
//		}
//	},
	getOject : function(){
		$this = $(this);
		var obj;
		if ($this.find('input[role="tagsinput"]').length>0) {
			obj = $this.find('input[role="tagsinput"]');
		} else {
			obj = $this;
		}
		return obj;
	}
};
pubsub.sub("tagsinput", tagsinputObj);