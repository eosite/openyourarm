var officebtFunc = {
	/**
	 * 联动
	 */
	linkage : function(rule, args) {
		var $target = pubsub.getJQObj(rule);
		if (args && args.DocId && $target.get(0)) {
			$target.office('openByDocId', args.DocId);		
		}
		pubsub.execChilds(rule);
	},
	/**
	 * 赋值
	 */
	setValue : function(rule, args) {
		var $target = pubsub.getJQObj(rule);
		if (args && $target.get(0)) {
			$.each(args, function(k, v) {
				$target.office("setValue", k, v);
			});
		}
		pubsub.execChilds(rule);
	},
	/**
	 * 赋值 (功能一样)
	 */
	officeSetValue : function(rule, args){
		return officebtFunc.setValue(rule, args);
	},
	
	/**
	 * 控件自身保存
	 */
	OfficeSave : function(rule, args) {
		var $target = pubsub.getJQObj(rule);
		if ($target.get(0)) {
			$target.office("save");
		}
	},
	/**
	 * 打印
	 */
	print : function(rule, args) {
		var $target = pubsub.getJQObj(rule);
		if ($target.get(0)) {
			$target.office("print");
		}
	},
	/**
	 * 联动模板（保存会生成新的文件）
	 */
	linkModel : function(rule, args) {
		var $target = pubsub.getJQObj(rule);
		if (args && args.DocId && $target.get(0)) {
			$target.office('openModelByDocId', args.DocId);
		}
	},
	/**
	 * 修订
	 */
	ShowRevisions : function(rule, args){
		var $target = pubsub.getJQObj(rule);
		if (args && $target.get(0)) {
			$target.office('ShowRevisions', !!args.ShowRevisions);
		}
	},
	/**
	 * 修订加密
	 */
	MtProtect : function(rule, args){
		var $target = pubsub.getJQObj(rule);
		if (args && $target.get(0)) {
			if(args.Protect === undefined){
				alert("加密参数不能为空!");
				return false;
			}
			if(args.PWD === undefined){
				alert("密码不能为空!");
				return false;
			}
			if(args.Protect == true || args.Protect == 'true'){
				$target.office('Protect', args.PWD);
			} else {
				$target.office('Unprotect', args.PWD);
			}
		}
	},
	/**
	 * 另存为
	 */
	MtShowDialog : function(rule, args){
		var $target = pubsub.getJQObj(rule);
		if (args && $target.get(0)) {
			$target.office('ShowDialog', args.TYPE || 3);
		}
	},
	
	saveAs : function(rule, args){
		var $target = pubsub.getJQObj(rule);
		if (args && $target.get(0)) {
			$target.office('saveAs', args.fileName);
		}
	},
	
	AppendRowsByBookMarks : function(rule, args){
		var $target = pubsub.getJQObj(rule);
		if (args && $target.get(0)) {
			!(rule instanceof Array) && (rule = [rule]);
			var bookMarkMapping = {};
			$.each(rule, function(i, v){
				bookMarkMapping[v.param] = v.columnName;
			});
			var data = null;
			for(var k in args){
				!data && (data = args[k]);
			}
			$target.office('AppendRowsByBookMarks', bookMarkMapping//
					, data, false, function(){
				pubsub.execChilds(rule);
			});
		}
	}
};
pubsub.sub("officebt", officebtFunc);