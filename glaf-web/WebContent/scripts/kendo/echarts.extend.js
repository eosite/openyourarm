var echartsFunc = {
		
    //获取地图信息
	getMapJsonData : function(id,args){
		var $id = pubsub.getJQObj(id);
		var that = $id.data('echarts');
		var myChart = echarts.init(that.target);
		
		 $.getJSON(contextPath+'/scripts/plugins/bootstrap/echarts/json/beijing.json', function (data) {
		        echarts.registerMap('beijing', data);
		        myChart.setOption({
		            series: [{
		                type: 'map',
		                map: 'beijing'
		            }]
		        });
		    });
		
	},
	refresh : function(id,arg){
		var $id = pubsub.getJQObj(id);
		var that = $id.data("echarts");
		that._init();
	},
	linkageControl: function(id, params, args) {
		var $id = pubsub.getJQObj(id),
		    that = $id.data("echarts");
		that.options._querydata = JSON.stringify(params);
		that._init();
	},
	getParamName : function(rule,args){
		var $id = pubsub.getJQObj(rule),
	    val = null;
		$.each(args,function(i,arg){
			val = arg.name;
		});
		return val;
	},
	getParamVal : function(rule,args){
		var $id = pubsub.getJQObj(rule),
		    val = null;
		$.each(args,function(i,arg){
			val = arg.value;
		});
		return val;
	}
};
pubsub.sub("echarts", echartsFunc);

