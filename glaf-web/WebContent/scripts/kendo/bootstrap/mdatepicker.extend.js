var mdatepickerFunc = {		
    setValue : function(rule,args){
    	var $id = pubsub.getJQObj(rule),
    	    that = $id.data("mdatepicker");
    	
    	$.each(args,function(i,arg){
    		
    		var dataOption = that.target.attr("data-options");
    		that.setValue(arg);
    		if(dataOption != undefined && dataOption != "{}"){
    			var stat = JSON.parse(dataOption);
    			stat['value'] = arg;
    			that.target.attr("data-options",JSON.stringify(stat));
    			that.opt.picker = new mui.DtPicker(stat);
    		}
    		else{
    			that.target.attr("data-options","{\"value\":\""+arg+"\"}");
    			var stat =  JSON.parse(that.target.attr("data-options"));
    			that.opt.picker = new mui.DtPicker(stat);
    		}
    		
    		
    	});
    	
    },
    tdisabled : function(rule,args){
    	var $id = pubsub.getJQObj(rule);
        $id.data("mdatepicker").tdisabled();
    
    },
    thidden : function(rule,args){
    	var $id = pubsub.getJQObj(rule);
        $id.data("mdatepicker").thidden();
    },
    getValue : function(rule,args){
    	var $id = pubsub.getJQObj(rule.inid);
    	var vlx = $id.data("mdatepicker").target.val();
    	return vlx;
    },
    tshow : function(rule,args){
    	var $id = pubsub.getJQObj(rule),
    	    that = $id.data("mdatepicker");
    	that._dtPicker(that.target,that.option);
        that.tshow();
    }
};
pubsub.sub("mdatepicker", mdatepickerFunc);
