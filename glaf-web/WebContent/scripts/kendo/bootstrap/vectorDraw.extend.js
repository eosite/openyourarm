var vectorDrawFunc = {		
    
    
    open : function(rule,args){
    	var $id = pubsub.getJQObj(rule),
	    that = $id.data("vectorDrawOpt");
    	$.each(args,function(i,arg){
    		that.Open(arg);
    	});
	    
    },
    getX : function(rule,args){
    	var $id = pubsub.getJQObj(rule),text = "";
    	$.each(args,function(i,arg){
    		text = arg.x.toFixed(2);
    	})
    	return text;
    },
    getY : function(rule,args){
    	var $id = pubsub.getJQObj(rule),text = "";
    	$.each(args,function(i,arg){
    		text = arg.y.toFixed(2);
    	})
    	return text;
    },
    getSelectItem : function(rule,args){
    	var $id = pubsub.getJQObj(rule),value = [];
    	$.each(args,function(i,arg){
    		value.push(arg[rule.columnName]);
    	})
    	return value.join(",");
    },
};
pubsub.sub("vectorDraw", vectorDrawFunc);
