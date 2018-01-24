/**
 * 文件上传事件定义器
 */
var fileUploadFunc = {
	getValue: function(rule, args) { //取值
		var $id = pubsub.getJQObj(rule, true);
		return $id.data("saveId");
	},
	setValue: function(rule, args) { //赋值
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.attr("setValue", "setValue");
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			$id.spFileUpload("setValue",v);
		} else {
			$id.spFileUpload("setValue",args);
		}
	},
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.hide();
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.show();
	},
	tdisabled: function(rule, args) { // 禁用
		var $id = pubsub.getJQObj(rule);
		$id.spFileUpload("disabled", false);
	},
	tenabled: function(rule, args) { // 启用
		var $id = pubsub.getJQObj(rule);
		$id.spFileUpload("disabled", true);
	},
	setRamdomparent : function(rule, args){
		var $id = pubsub.getJQObj(rule);

		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
		} else {
			v = args;
		}

		$id.spFileUpload("setValue", v);
	},
	clear : function(rule,args){
		var $id = pubsub.getJQObj(rule);

		$id.spFileUpload("setValue", "");
	},
	//设置只读状态，不能上传
	setReadOnly:function(rule){
		var $id = pubsub.getJQObj(rule);
		$id.spFileUpload("setReadOnly");
	},
	//取消只读状态。
	removeReadOnly:function(rule){
		var $id = pubsub.getJQObj(rule);
		$id.spFileUpload("removeReadOnly");
	},

	disabledDelete:function(rule){
		var $id = pubsub.getJQObj(rule);
		$id.spFileUpload("disabledDelete");
	},
	removeDisabledDelete:function(rule){
		var $id = pubsub.getJQObj(rule);
		$id.spFileUpload("removeDisabledDelete");
	}
};
pubsub.sub("jqfileupload", fileUploadFunc);




function uploadSuccessDo(e,data){
	
	var files = data.result, names = "", ids = "", 
	  	outputNames = $(this).data("spFileUpload").opts.outputNames,
		outputIds = $(this).data("spFileUpload").opts.outputIds;
		for (var i = 0; i < files.length; i++) {
			names += files[i].fileName + ",";
			ids += files[i].id + ",";
		}
		if (outputNames && names) {
			var opns = outputNames.split(",");
			for (var k = 0; k < opns.length; k++) {
				outputName = opns[k];
				var $op = $("#" + outputName);
				var opval = $op.val();
				$op.val((opval ? (opval + ",") : "")+ names.substr(0,names.length - 1));
			}
		}
		if (outputIds && ids) {
			var opids = outputIds.split(",");
			for (var k = 0; k < opids.length; k++) {
				var outputId = opids[k], $op = $("#"
						+ outputId), opval = $op.val();
				$op.val((opval ? (opval + ","): "")+ ids.substr(0,ids.length - 1));
			}
		}
}

function deleteDo(data){
	
	var files = data, names = "", ids = "", 
	  	outputNames = $(this).data("spFileUpload").opts.outputNames,
		outputIds = $(this).data("spFileUpload").opts.outputIds;
	 	for (var i = 0; i < files.length; i++) {
			names += files[i].fileName + ",";
			ids += files[i].id + ",";
		}
		if (outputNames && names) {
			var opns = outputNames.split(",");
			for (var k = 0; k < opns.length; k++) {
				outputName = opns[k];
				var $op = $("#" + outputName);
				var str = ($op.val() + ",").replace(
						names, "");
				$op.val(str.substr(0, str.length - 1));
			}
		}
		if (outputIds && ids) {
			var opns = outputIds.split(",");
			for (var k = 0; k < opns.length; k++) {
				var outputId = opns[k], $op = $("#"
						+ outputId), str = ($op.val() + ",")
						.replace(ids, "");
				$op.val(str.substr(0, str.length - 1));
			}
		}
}