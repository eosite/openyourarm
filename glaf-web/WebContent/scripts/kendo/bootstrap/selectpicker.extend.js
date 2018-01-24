
var selectPickerFunc = {
		setValues : function(rule,args){
			var $id = pubsub.getJQObj(rule);
	    	$.each(args,function(i,arg){
	    		
	    		if(arg.indexOf(",") != -1){
	    			var arr = arg.split(",");
	    			$id.data("selectpicker").setValue(arr);
	    		}
	    		else{
	    			var arr = [];
	    			arr.push(arg);
	    			$id.data("selectpicker").setValue(arr);
	    		}
	    	});
		},
		
		reload : function(rule,args){
			var $id = pubsub.getJQObj(rule);
	        $id.data("selectpicker").reload(args);
	    	
		},
		getValue : function(rule,args){
			var $id = pubsub.getJQObj(rule.inid),arr = [];
			for(var i = 0 ; i < args.length ; i++){
				var items = args[i];
				$.each(items,function(j,item){
					item != undefined ? arr.push(item.value) : undefined;
				})
			}
			return arr.join(",");
		},
		getText : function(rule,args){
			var $id = pubsub.getJQObj(rule.inid),arr = [];
			for(var i = 0 ; i < args.length ; i++){
				var items = args[i];
				$.each(items,function(j,item){
					item != undefined ? arr.push(item.text) : undefined;
				})
			}
			return arr.join(",");
		},
		clear : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			$id.data("selectpicker").clear();
		},
		
		thidden : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			$id.data("selectpicker").hidden();
		},
		tshow : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			$id.data("selectpicker").show();
		},
		tenabled : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			$id.data("selectpicker").enable();
		},
		tdisabled : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			$id.data("selectpicker").disable();
		},
		getTextBoxValue : function(rule,args){
			var $id = pubsub.getJQObj(rule.inid);
			return $id.data("selectpicker").target.val();
		}
};
pubsub.sub("selectpicker", selectPickerFunc);