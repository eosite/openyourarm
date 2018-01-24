/**
 * 进度条
 */
var progressbarFunc = {
	getValue : function(rule, args) {
		var $id = pubsub.getJQObj(rule,true);
		return $id.progressBarExt("getValue");
	},
	setValue : function(rule, args) {
		var $id = pubsub.getJQObj(rule), r, v;
		var params = {};
		if(args.value){
			//设置固定值
			params.value = args.value;
			params.length = 1;
		}
		if(args.value_uint){
			//设置固定值后缀
			params.value_uint = args.value_uint;
			params.length = 1;
		}
		if(args.remain){
			//设置剩余值
			params.remain = args.remain;
			params.length = 1;
		}
		if(args.remain_uint){
			//设置剩余值后缀
			params.remain_uint = args.remain_uint;
			params.length = 1;
		}
		if(params.length > 0){
			$id.progressBarExt("setValue",params);
		}else{
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			if (v) {
				$id.progressBarExt("setValue",v);
			}
		}
	},
	tdisabled: function(rule, args) { // 禁用
		var $id = pubsub.getJQObj(rule);
		$id.progressBarExt("disabled",false);
	},
	tenabled: function(rule, args) { // 启用
		var $id = pubsub.getJQObj(rule);
		$id.progressBarExt("disabled",true);
	}
};
pubsub.sub("progressbarbt", progressbarFunc);