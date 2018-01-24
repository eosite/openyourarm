/**
 * 九宫格事件定义器接口
 */
var cellWebCabFunc = {
	//联动控件
	linkageControl : function(rule,args){
		var $id = pubsub.getJQObj(rule);
		var paramter = {};
		$.each(args,function(i,arg){
			paramter[i] = arg;
			
		});
		$id.data("cellWebCab").__linkageControl(paramter);
	},

};
pubsub.sub("cellWebCab", cellWebCabFunc);