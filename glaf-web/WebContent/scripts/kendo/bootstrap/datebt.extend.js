var datepickerFunc = {
	getValue : function(rule, args) {
		var $id = pubsub.getJQObj(rule, true);
		return $id.datepickerExt("getValue");
	},
	setValue : function(rule, args) {
		var $id = pubsub.getJQObj(rule), r, v = "";
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param]||"";
		}
		$id.datepickerExt("setValue",v);
	},
	tclear: function(rule, args) { //清空
		var $id = pubsub.getJQObj(rule);
		var jq = $id.datepickerExt("getOject");
		jq.attr("tclear", "tclear").val("");
	},
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.datepickerExt("display",false);
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.datepickerExt("display",true);
	},
	tdisabled : function(rule, args) {// 禁用
		var $id = pubsub.getJQObj(rule);
		$id.datepickerExt("disabled",false);
	},
	tenabled : function(rule, args) {// 启用
		var $id = pubsub.getJQObj(rule);
		$id.datepickerExt("disabled",true);
	}
};
pubsub.sub("datepickerbt", datepickerFunc);



var datetimepickerFunc = {
	getValue : function(rule, args) {
		var $id = pubsub.getJQObj(rule, true);
		return $id.datetimepickerExt("getValue");
	},
	setValue : function(rule, args) {
		var $id = pubsub.getJQObj(rule), r, v = "";
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param]||"";
		}
		$id.datetimepickerExt("setValue",v);
	},
	tclear: function(rule, args) { //清空
		var $id = pubsub.getJQObj(rule);
		var jq = $id.datetimepickerExt("getOject");
		jq.attr("tclear", "tclear").val("");
	},
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.datetimepickerExt("display",false);
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.datetimepickerExt("display",true);
	},
	tdisabled : function(rule, args) {// 禁用
		var $id = pubsub.getJQObj(rule);
		$id.datetimepickerExt("disabled",false);
	},
	tenabled : function(rule, args) {// 启用
		var $id = pubsub.getJQObj(rule);
		$id.datetimepickerExt("disabled",true);
	}
};
pubsub.sub("datetimepickerbt", datetimepickerFunc);  


var daterangepickerFunc = {
		
    //开始时间赋值
	startSetValue : function(rule,args){
		var $id = pubsub.getJQObj(rule),
		    element = $id.find(".form-control"),
	        obj = $id.data("daterangepicker"),
	        date = obj.methods.formatDate(obj,args),
	        element = $id.find(".form-control");
		$(element[0]).datepicker('setDate',date);
	},
	dateFormat :function(time, format){   //格式化日期工具
	    var t = new Date(time); 
	    var tf = function(i){return (i < 10 ? '0' : '') + i};
	    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
	        switch(a){
	            case 'yyyy':
	                return tf(t.getFullYear());
	                break;
	            case 'MM':
	                return tf(t.getMonth() + 1);
	                break;
	            case 'mm':
	                return tf(t.getMinutes());
	                break;
	            case 'dd':
	                return tf(t.getDate());
	                break;
	            case 'HH':
	                return tf(t.getHours());
	                break;
	            case 'ss':
	                return tf(t.getSeconds());
	                break;
	        }
	    })   
	},
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.daterangepickerExt("display",false);
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.daterangepickerExt("display",true);
	},
	tdisabled : function(rule, args) {// 禁用
		var $id = pubsub.getJQObj(rule);
		$id.daterangepickerExt("disabled",false);
	},
	tenabled : function(rule, args) {// 启用
		var $id = pubsub.getJQObj(rule);
		$id.daterangepickerExt("disabled",true);
	}
};
pubsub.sub("daterangepicker",daterangepickerFunc); 

