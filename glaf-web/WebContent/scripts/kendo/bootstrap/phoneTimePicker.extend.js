var phoneTimePickerFunc = {		
    setValue : function(rule,args){
    	var $id = pubsub.getJQObj(rule);
    	$.each(args,function(i,arg){
    		$id.data("phoneTimePicker").setValue(arg);
    	});
    	
    },
    tdisabled : function(rule,args){
    	var $id = pubsub.getJQObj(rule);
        $id.data("phoneTimePicker").tdisabled();
    
    },
    thidden : function(rule,args){
    	var $id = pubsub.getJQObj(rule);
        $id.data("phoneTimePicker").thidden();
    },
    getValue : function(rule,args){
    	var str1 = $("#"+rule.inid).find(".weui-input").val();
    	if(str1.indexOf("年")!= -1){
    		str1 = str1.replace("年","-");
    	} if(str1.indexOf("月")!= -1){
    		str1 = str1.replace("月","-");
    	} if(str1.indexOf("日")!= -1){
    		str1 = str1.replace("日","");
    	} if(str1.indexOf("时")!= -1){
    		str1 = str1.replace("时",":");
    	} if(str1.indexOf("分")!= -1){
    		str1 = str1.replace("分","");
    	}
    	console.log(str1);
    	return str1;
    }
};
pubsub.sub("phoneTimePicker", phoneTimePickerFunc);
