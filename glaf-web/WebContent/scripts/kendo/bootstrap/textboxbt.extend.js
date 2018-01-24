/**
 * textboxbt事件定义器
 */
var textboxbtFunc = {
	getValue: function(rule, args) { //取值
		var $id = pubsub.getJQObj(rule, true);
		return $id.textboxBtExt("getValue");
	},
	setValue: function(rule, args) { //赋值
		var $id = pubsub.getJQObj(rule),r, v = "";
		if (typeof args == "object") {
			if($.isArray(args)){
				$.eahc(args,function(i,item){
					if(v){
						v += ",";
					}
					v += item ||"";
				})
			}else{
				for (var i = 0; i < rule.length; i++) {
					r = rule[i];
					var value = args[r.param];
					if($.isArray(value)){
						$.each(value,function(i,item){
							if(v){
								v += ",";
							}
							v += item[r.columnName] ||"";
						})
					}else{

						v = value;
					}
					
				}
			}
			
			$id.textboxBtExt("setValue",v);
		} else {
			$id.textboxBtExt("setValue",args);
		}
	},
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.textboxBtExt("display",false);
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.textboxBtExt("display",true);
	},
	tclear: function(rule, args) { //清空
		var $id = pubsub.getJQObj(rule);
		var jq = $id.textboxBtExt("getOject");
		jq.attr("tclear", "tclear").val("");
	},
	tdisabled: function(rule, args) { // 禁用
		var $id = pubsub.getJQObj(rule);
		$id.textboxBtExt("disabled",false);
	},
	tenabled: function(rule, args) { // 启用
		var $id = pubsub.getJQObj(rule);
		$id.textboxBtExt("disabled",true);
	},
	appendSetValue: function(rule, args) {//追加赋值
		var $id = pubsub.getJQObj(rule),r, v = "";
		var jq = $id.textboxBtExt("getOject"),
			bval = jq.val() ? jq.val() : "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
				if(v){
					if(bval)
						bval += ",";
					bval = bval + v;
				}
			}
			$id.textboxBtExt("setValue",bval);
		} else {
			$id.textboxBtExt("setValue",bval + args);
		}
	},
	setValueByIndex : function(rule,args){
	    var $id = pubsub.getJQObj(rule),
		    vl = "";
	    var ar = [];
		$.each(args,function(i,arg){
			if(arg.indexOf(",") != -1){
				var arr = arg.split(",");
				vl = arr[i];
			}
			else{
				ar.push(arg);
				
				vl = ar[i];
			}
		});
		$id.textboxBtExt("setValue",pubsub.htmlUnescape(vl));
		
	}
};
pubsub.sub("textboxbt", textboxbtFunc);