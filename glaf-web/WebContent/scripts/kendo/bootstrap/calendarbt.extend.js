/**
 * calendar事件
 */
var calendarbtFunc = {

	grefresh: function(rule, args) {//刷新操作
		var $id = pubsub.getJQObj(rule);
		$id.calendarExt("refresh");
	},
	getSource:function(rule, args){//选中日程
		return args[0][rule.columnName];
	},
	getDate:function(rule, args){//选中日历
		var $id = pubsub.getJQObj(rule);
		return $id.calendarExt("dateFormat",args[0],"yyyy-MM-dd HH:mm:dd");
	},
	linkage:function(rule, args){//选中日历
		var $id = pubsub.getJQObj(rule);

		return $id.calendarExt("query",args);
	},
	
};
pubsub.sub("calendarbt", calendarbtFunc);