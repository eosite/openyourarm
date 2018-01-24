/**
 * 流程实例函数 撤销、终止...
 * 
 * @author klaus.wang
 */
var FlowProcess = {
	SUBMIT : 0,
	STOP : 1,
	CANCEL : 2,
	ACTIVE : 3,
	REJECT : 4
};

FlowProcess.callWindow = function(args, sfn, efn, fn) {
	$.extend(FlowProcess, {
		args : args,
		sfn : sfn,
		efn : efn
	});
	var url = contextPath + //
	"/mx/form/defined/ex/flowProcessFeedback?taskId=" + (args.taskId || '')
			+ "&" + $.param({
				processId : args.processId,
				type : args.type,
				fn : fn || "FlowProcessFunc"
			});

	var $frame = $("<iframe>", {
		frameborder : 'no',
		width : '100%',
		height : 400
	});

	FlowProcess.dialog = window.show({
		css : {
			width : 650,
			height : 450
		},
		message : $frame,
		onshown : function(dialog) {
			$frame.attr("src", url);
		}
	});

};

function FlowProcessFunc(args) {
	args = $.extend(true, (FlowProcess.args || {}), args || {});
	var url = contextPath + //
	"/mx/form/workflow/defined/flowProcess";
	$.ajax({
		url : url,
		data : args,
		type : 'post',
		dataType : 'json',
		success : FlowProcess.sfn || function(ret) {
			if (!ret)
				return;
			if (ret.errMsg) {
				alert(ret.errMsg);
			} else {
				alert("操作成功!");
				FlowProcessFunc.close();
				FlowProcess.callback && FlowProcess.callback(ret);
				FlowProcess.callback = null;
			}
		},
		error : FlowProcess.efn || function(ret) {
			alert("服务器处理出错!");
		}
	});
}

FlowProcessFunc.close = function() {
	FlowProcess.dialog && (FlowProcess.dialog.close());
};

/**
 * 流程终止(作废)
 */
FlowProcess.stop = function(args) {
	if (!confirm("你确定终止流程吗?")) {
		return false;
	}
	FlowProcess.callWindow({
		type : FlowProcess.STOP,
		processId : args.processId
	});
};

/**
 * 流程终止
 */
FlowProcess.stop0 = function(rule, args) {
	return FlowProcessFunc0(rule, $.extend(true, {}, args, {
		type : FlowProcess.STOP
	}), "终止");
};

/**
 * 流程挂起(作废)
 */
FlowProcess.cancel = function(args) {
	if (!confirm("你确定挂起流程吗?")) {
		return false;
	}
	FlowProcessFunc({
		type : FlowProcess.CANCEL,
		processId : args.processId
	});
};

/**
 * 流程挂起
 */
FlowProcess.cancel0 = function(rule, args) {
	return FlowProcessFunc0(rule, $.extend(true, {}, args, {
		type : FlowProcess.CANCEL
	}), "挂起");
};

/**
 * 流程激活(作废)
 */
FlowProcess.active = function(args) {
	if (!confirm("你确定激活流程吗?")) {
		return false;
	}
	FlowProcessFunc({
		type : FlowProcess.ACTIVE,
		processId : args.processId
	});
};
/**
 * 流程激活
 */
FlowProcess.active0 = function(rule, args) {
	return FlowProcessFunc0(rule, $.extend(true, {}, args, {
		type : FlowProcess.ACTIVE
	}), "激活");
};

/**
 * 流程撤回(作废)
 */
FlowProcess.reject = function(args) {
	// if (!confirm("你确定撤回/退回流程吗?")) {
	// return false;
	// }
	FlowProcess.callWindow({
		type : FlowProcess.REJECT,
		processId : args.processId,
		taskId : args.taskId
	});
}
/**
 * 流程撤回
 */
FlowProcess.reject0 = function(rule, args) {

	// if(window.$ParentFlow){
	// return window.$ParentFlow.back();
	// }

	if (!args.taskId) {
		alert("流程任务id不能为空");
		return false;
	}
	return FlowProcessFunc0(rule, $.extend(true, {}, args, {
		type : FlowProcess.REJECT
	}), "撤回/退回");
}

/**
 * 流程提交(作废)
 */
FlowProcess.submit = function(args) {
	FlowProcess.callWindow({
		type : FlowProcess.SUBMIT,
		processId : args.processId,
		taskId : args.taskId
	});
};

/**
 * 签收
 */
FlowProcess.mtAssign0 = function(rule, args) {
	if (!args.processId) {
		alert("流程实例id不能为空");
		return false;
	}

	var url = contextPath + "/mx/form/workflow/defined/assign";
	$.ajax({
		url : url,
		data : args,
		type : 'post',
		dataType : 'json',
		success : function(ret) {
			if (!ret)
				return;
			if (ret.errMsg) {
				alert(ret.errMsg);
			} else {
				alert("操作成功!");
			}
			pubsub.execChilds(rule);
		},
		error : function(ret) {
			alert("服务器处理出错!");
		}
	});

}

/**
 * 流程提交
 */
FlowProcess.mtSubmit0 = function(rule, args) {
	if (window.$ParentFlow) {
		function callback(msg) {
			var ruleOne;
			var thatWin = /* pubsub.getThat(rule, true) */window;
			if ($.isPlainObject(rule)) {
				ruleOne = rule;
			}
			if ($.isArray(rule)) {
				ruleOne = rule[0];
			}
			thatWin.callbackParam = thatWin.callbackParam || {};
			$.each(ruleOne.callback, function(i, k) {
				thatWin.callbackParam[k.param] = msg[k.param];
			});
			pubsub.execChilds(rule);
		}
		if (args.approve == false) {
			if (args.notOpen != true)
				return window.$ParentFlow.back(FlowProcess.callback = callback);
		} else
			return window.$ParentFlow.submit(callback);
	}

	if (!args.processId) {
		alert("流程实例id不能为空");
		return false;
	}

	/**
	 * 退回直接走撤回道路
	 */
	if (args.approve == false && args.notOpen == true) {
		args.approve = null;
		args.notOpen = null;
		return FlowProcessFunc0(rule, $.extend(true, {}, args, {
			type : FlowProcess.REJECT
		}), "撤回/退回");
	}

	if (!confirm((args.approve == false ? "退回" : "提交") + "流程?")) {
		return false;
	}

	var url = contextPath + //
	"/mx/form/workflow/defined/submit";
	$.ajax({
		url : url,
		data : args,
		type : 'post',
		dataType : 'json',
		success : function(ret) {
			if (!ret)
				return;
			if (ret.errMsg) {
				alert(ret.errMsg);
			} else {
				alert("操作成功!");
			}
			pubsub.execChilds(rule);
		},
		error : function(ret) {
			alert("服务器处理出错!");
		}
	});

};

function FlowProcessFunc0(rule, args, msg) {

	if (!args.processId) {
		alert("流程实例id不能为空");
		return false;
	}

	if (!confirm(msg + "流程?")) {
		return false;
	}

	var url = contextPath + //
	"/mx/form/workflow/defined/flowProcess";
	$.ajax({
		url : url,
		data : args,
		type : 'post',
		dataType : 'json',
		success : function(ret) {
			if (!ret)
				return;
			if (ret.errMsg) {
				alert(ret.errMsg);
			} else {
				alert("操作成功!");
			}
			pubsub.execChilds(rule);
		},
		error : function(ret) {
			alert("服务器处理出错!");
		}
	});
}