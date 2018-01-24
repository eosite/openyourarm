var exceluploadFunc = {
	//设置参数
	setValue : function(rule, args) { //赋值
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.excelFileUpload("setParam",args);
	},
	getExcelVal : function(rule,args){
		var count = 0;
		$.each(args[0].result,function(i,arg){
			if(arg.count != undefined && arg.count != null){
				count = arg.count;
			}
		})
		return count;
	}
};
pubsub.sub("excelupload", exceluploadFunc);