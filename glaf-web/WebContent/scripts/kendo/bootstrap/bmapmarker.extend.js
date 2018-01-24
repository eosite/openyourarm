/**
 * 
 */
var bmapmarkerFunc = {
	//获取选中坐标的位置信息
    getSelectedAddress : function(rule,params){
    	var $this = pubsub.getJQObj(rule,true) ;
		return $this.bmapmarker("getSelectedAddress")[rule.columnName];
    },
    getClickMarkerData : function(rule,params){
    	var $this = pubsub.getJQObj(rule,true) ;
		return $this.bmapmarker("getClickMarkerData")[rule.columnName];
    }
};
pubsub.sub("bmapmarker", bmapmarkerFunc);