var mapextFunc = {
	getClickNode : function (rule, args) {
		if(args){
			if(rule.columnName == 'seriesName'){
				return args[0].seriesName;
			}else{
				return args[0].data[rule.columnName];
			}
		}
	}
};
pubsub.sub("mapext", mapextFunc);
