var modelbimFunc = {
	tshow : function(rule,args){
		var $id = pubsub.getJQObj(rule.outid, true),
	    $tar = $id.data("modelbim").target;
		$tar.css("display","block");
	},
	thidden : function(rule,args){
		var $id = pubsub.getJQObj(rule[0].outid, true),
	    $tar = $id.data("modelbim").target;
		$tar.css("display","none");
	},
	setModelValue : function(rule,args){
		var $id = pubsub.getJQObj(rule[0].outid, true),
		    $opt = $id.data("modelbim").option;
		$opt.renderType = args.RENDERTYPE;
		$opt.model = args.MODEL;
	},
	select2View : function(rule,args){
		var $id = pubsub.getJQObj(rule[0].outid, true),
		    $opt = $id.data("modelbim").option,
		    arr = [];
		
		$.each(args,function(i,arg){
			arr.push(arg);
			
		});
		$opt.token.setSelection(arr,0);
		
		
	},
	getNodeId : function(rule,args){
		var val = "";
		if(args[0][0] != undefined ){
			var param = args[0][0],
			    arr = param.split("-");
			for(var i = 0 ; i < arr.length ; i++){
				if(i != 2){
					arr[i] = arr[i].toUpperCase();
				}
				
				if(i == 0){
					val = val + arr[i] + "-";
				}
				else if(i == arr.length - 1){
					val = val + arr[i];
				}
				else{
					val =  val  + arr[i] + "-";
				}
				
			}
			
		}
		return val;
		
	},
	initModelBim : function(rule,args){
		var $id = pubsub.getJQObj(rule.outid, true),
	    $opt = $id.data("modelbim").option;
		$opt.token.unSetAllModel();
	}
};
pubsub.sub("modelbim", modelbimFunc);
