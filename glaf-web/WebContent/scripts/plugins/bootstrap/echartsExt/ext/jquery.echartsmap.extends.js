! function($) {
	var plugin = 'echartsmapExt';


	var Build = function(target, state) {
		this.target = target;
		this.state = state;
		this.options = state.options;
		this._init();
	};
	$.parser.plugins.push(plugin);
	$.fnInit(plugin, function(target) {
		var state = $.data(target, plugin + '.options');
		var columns = $(target).data("columns");
		if(columns&&columns.length>0){
			state.options.columns = eval('('+columns+')');
		}
		var p = new Build(target, state);

		$.data(target, plugin, p);
	});

	$.fn[plugin].defaults = {
		width: '500px',
		height: '500px',
		title: {	//标题
	    	text: '',	//标题名称
	        subtext: '',	//父标题名称
	        left: 'center',	//标题水平位置
	        top: 'top',	//标题垂直位置
	        textStyle:{	//标题样式
			    fontSize: 18,
			    fontWeight: 'bolder',
			    color: '#333'
			},
			subtextStyle:{
				fontSize: 12,
			    fontWeight: '',
			    color: '#aaa'
			}
	    },
	    color:['#48cda6','#fd87ab','#11abff','#ffdf33','#968ade'],  //手动设置每个图例的颜色
	    legend : {	//图例
	    	orient: 'vertical',	//布局方式，图例的排序，可选为：'horizontal' | 'vertical'
	        left: 'left',	//水平位置
	        top: 'top',	//垂直位置
	        padding: 5,	//内边距
	        textStyle:{
	        	fontSize: 18,	//图例字体大小
	        	color: '#333',	//图例字体颜色
	        },
	        data:[],	//图片名称,以series为主
	    },
	    toolbox: {
	        show: true,
	        orient: 'vertical',
	        left: 'right',
	        top: 'center',
	        feature: {
	            dataView: {readOnly: false},
	            restore: {},
	            saveAsImage: {}
	        }
	    },
	    // dataRange: {	//值域
	    // 	orient: 'vertical',	//布局方式，图例的排序，可选为：'horizontal' | 'vertical'
	    //     left: 'left',	//水平位置
	    //     top: 'top',	//垂直位置
	    // },
	    visualMap:{
	    	min: 0,	//最小值
	        max: 2500,	//最大值
	        left: 'left',	//水平位置
	        top: 'bottom',	//垂直位置
	        text: ['高','低'],           // 文本，默认为数值文本
	        calculable: true
	    },
	    tooltip : {	//提示
	    	show: true,	//显示提示
	    	backgroundColor : 'rgba(0,0,0,0.3)',	//提示的背景颜色
	    	borderRadius : '4',	//圆角
	    	borderWidth : '0',	//边框宽度
	    	padding : 5,	//内边距
	    	trigger: 'item'
	    },
	    label : {	//图形上的文本标签，可用于说明图形的一些数据信息
    		normal : {	//普通时
    			show : false,	//显示值
    			textStyle:{
    				fontSize: 18,	//值字体大小
        			color: '#333',	//值字体颜色
    			}
    		},
    		emphasis : {	//高亮时
    			show : false,	//显示值
    			textStyle:{
    				fontSize: 30,	//值字体大小
        			color: '#333',	//值字体颜色
    			}
    		}
    	},
	    serie:{	//地图数据
	    	zoom: 1,	//放大比例
	    	roam: false,//是否开启鼠标缩放和平移漫游。默认不开启。如果只想要开启缩放或者平移，可以设置成 'scale' 或者 'move'。设置成 true 为都开启
	    	selectedMode: false,	//选中模式，表示是否支持多个选中，默认关闭，支持布尔值和字符串，字符串取值可选'single'表示单选，或者'multiple'表示多选。
	    	map: 'mapext',
	    	scaleLimit:{	//缩放限制，最大或最小
	    		min:1,
	    		max:10,
	    	},
	    	label : {	//图形上的文本标签，可用于说明图形的一些数据信息
	    		normal : {	//普通时
	    			show : false,	//显示值
	    			textStyle:{
	    				fontSize: 18,	//值字体大小
	        			color: '#333',	//值字体颜色
	    			}
	    		},
	    		emphasis : {	//高亮时
	    			show : false,	//显示值
	    			textStyle:{
	    				fontSize: 30,	//值字体大小
	        			color: '#333',	//值字体颜色
	    			}
	    		}
	    	}
	    },
		events:{
			clickNode: function(data){},
		}
	};
	var sional;
	$.fn[plugin].methods = {
		_init: function() {
			var that = this;
			$(that.target).width("500px");
			$(that.target).height("500px");

			var option = that.options;

			function randomData() {
			    return Math.round(Math.random()*1000);
			}

			if(option.mapid){
				$.ajax({
					url: contextPath + "/mx/form/formfile?method=download",
					async: true,
					type:'post',
					data: {
						id: option.mapid
					},
					success: function(ret) {
						echarts.registerMap('mapext',ret);
						var myChart = echarts.init(that.target);
					    that._querydata = that.options._querydata;

					    $.ajax({
							type: "POST",
							url: contextPath + "/mx/form/charts/datas",
							contentType: "application/json",
							dataType: "json",
							data: JSON.stringify({
								'rid': option.rid,
								'params': that._querydata
							}),
							success: function(msg) {
								console.log(msg);
								var series = that.transform(msg);
								option.series = series;
								myChart.setOption(option, true);
								myChart.on('click', function (params) {
								    if($.isFunction(option.events.clickNode)){
								    	option.events.clickNode(params);
								    }
								});
							}
						});
					},
					error: function(e){
						console.log(e);
						// echarts.registerMap('mapext',mapdata);
					}
				})
			}
		},
		_getRule: function(rules, id) { // 根据id找到对应的规则
			var rulesSize = rules.length;
			if (rulesSize) {
				var rule;
				for (var i = 0; i < rulesSize; i++) {
					rule = rules[i];
					if (rule.id == id) {
						return rule;
					}
				}
			}
			return null;
		},
		transform: function(datas) {
			var that = this;
			var options = that.options;
			var rules = options.chartsSource;
			var datasSize = datas.length;
			var data = null;
			var series = [];

			if(datasSize){
				for (var i = 0; i < datasSize; i++) {
					data = datas[i];
					rule = that._getRule(rules, data.id);
					if (rule) {
						var serie = that._groupBySeries(data.data, rule, i, options);

						

						$.merge(series, serie);
					}
				}
				return series;
			}
			return [];
		},
		_groupBySeries: function(datas, rule, ind, opts) { // 根据系列分组
			var that = this;
			var series = [],
				serie, subseries, subserie, data, datasSize = datas.length,
				yans = rule.yAxisName,
				yansSize = yans.length,
				an = rule.axisName,
				yan, xan = rule.xAxisName,
				yAxisLength = false;
				// yAxisLength = opts.yAxis.length > 1;
			rule._chartType = opts.type;
			rule.pieInnerSize = opts.pieInnerSize;
			// 静态系列
			if (yans.length == 1 && an != undefined && an.en != "") {
				for (var i = 0; i < datasSize; i++) {
					data = datas[i];
					var yAxisName = data[yans[0].en];
					if (yans[0].type == "datetime") {
						yAxisName = that._formatTime(yAxisName, rule.tf);
					}
					// var xAxisName = data[xan.en]; 系列名
					var hasSerie = that._hasYAxisName(series, yAxisName);
					if (hasSerie) { // 如果包含
						hasSerie.data.push(that._staticBuildSubserie(data, rule));
					} else {
						serie = {};
						serie.name = yAxisName;
						//增加图例
						that.options.legend.data.push(yAxisName);

						serie.map= 'mapext';
						that._setSeriesLabel(serie);
						// serie.coordinateSystem= 'geo';
						if (yAxisLength) {
							serie.yAxis = ind;
						}
						// serie.xAxis = ind ;
						serie.type = rule.chartType || map;
						if (rule.stack) {
							serie.stack = data[rule.stack];
						}
						that._setShowValueAndPoint(serie, rule);

						/*
						 * if(serie.name == "0"){ serie.stack =ind+ "我的" ; }else{
						 * serie.stack =ind+ "你的" ; }
						 */
						subseries = [];
						subserie = that._staticBuildSubserie(data, rule);
						subseries.push(subserie);
						serie.data = subseries;

						if (opts.pieInnerSize) {
							serie.innerSize = opts.pieInnerSize;
						}
						if ((serie.type == "pie" || (!serie.type && opts.chart.type == "pie")) && opts.pieInnerSize) {
							serie.colorByPoint = true; //根据点自动分配颜色
						}
						series.push(serie);
					}
				}
			} else { // 动态系列
				for (var i = 0; i < datasSize; i++) {
					data = datas[i];
					for (var j = 0; j < yansSize; j++) {
						yan = yans[j];
						var hasSerie = that._hasYAxisName(series, yan.cn);
						if (hasSerie) {
							hasSerie.data.push(that._buildSubserie(data, rule, yan));
						} else {
							serie = {};
							if (yAxisLength) {
								serie.yAxis = ind;
							}
							serie.name = yan.cn;
							//增加图例
							that.options.legend.data.push(yan.cn);

							serie.map= 'mapext';
							serie.type = rule.chartType || 'map';
							// serie.coordinateSystem= 'geo';
							that._setSeriesLabel(serie);
							that._setShowValueAndPoint(serie, rule);
							if (rule.stack) {
								serie.stack = data[rule.stack];
							}
							subseries = [];
							subserie = that._buildSubserie(data, rule, yan);
							subseries.push(subserie);
							serie.data = subseries;
							if (opts.colorByPoint) {
								serie.colorByPoint = true; //根据点自动分配颜色
							}
							if ((serie.type == "pie" || (!serie.type && opts.type == "pie")) && opts.pieInnerSize) {
								serie.innerSize = opts.pieInnerSize;
							}
							series.push(serie);
						}
					}
				}
			}
			return series;
		},
		_setSeriesLabel: function(serie){
			var that = this;
			var options = that.options;
			serie.label = options.label;
		},
		_setShowValueAndPoint: function(serie, rule) {
			if (rule.markerEnable) {
				serie.marker = serie.marker || {};
				serie.marker.enabled = rule.markerEnable && rule.markerEnable == 'true' ? true : false;
			}
			if (rule.dataLabelsEnable) {
				serie.dataLabels = serie.dataLabels || {};
				serie.dataLabels.enabled = rule.dataLabelsEnable && rule.dataLabelsEnable == 'true' ? true : false;
			}
		},
		_staticBuildSubserie: function(data, rule) { // 静态列
			var subserie = {},
				chartType = rule.chartType || rule._chartType,
				isScatter = chartType == "scatter",
				isBubble = chartType == "bubble";
			for (var p in data) {
				if (rule.xAxisName && p == rule.xAxisName.en) {
					subserie.name = data[p];
					if (rule.xAxisName.type == "datetime") {
						subserie.name = chartsFunc._formatTime(data[p], rule.tf);
					}
					if (isScatter || isBubble) {
						var x = parseFloat(+data[p]);
						subserie.x = parseFloat(x.toFixed(2));
					}
				} else if (p == rule.axisName.en) {
					/*var y = parseFloat(data[p]);
					subserie.y = parseFloat(y.toFixed(2));*/
					if (data[p]) {
						var y = parseFloat(+data[p]);
						subserie.y = parseFloat(y.toFixed(2));
					} else {
						subserie.y = null;
					}
				} else if (isBubble && rule.zAxisName && p == rule.zAxisName.en) {
					subserie.zoneAxis = "z";
					if (data[p]) {
						var z = parseFloat(+data[p]);
						subserie.z = parseFloat(z.toFixed(2));
					} else {
						subserie.z = null;
					}
				}
				subserie[p] = data[p];
			}
			return subserie;
		},
		_hasYAxisName: function(series, yAxisName) {
			var seriesSize = series.length,
				serie;
			if (seriesSize) {
				for (var i = 0; i < seriesSize; i++) {
					serie = series[i];
					if (serie.name == yAxisName) {
						return serie;
					}
				}
			}
			return null;
		},
		_formatTime: function(str, format) { // 时间转换
			//return kendo.format('{0:' + (format || 'yyyy-MM-dd') + '}', kendo.parseDate(str)) || str;
			return hmtdUtils.parseDate(str).format(format || 'yyyy-MM-dd') || str;
			//return new Date(str).format(format || 'yyyy-MM-dd') || str;
		},
		_buildSubserie: function(data, rule, yan) { // 动态列
			var subserie = {},
				chartType = rule.chartType || rule._chartType,
				isScatter = chartType == "scatter",
				isBubble = chartType == "bubble";
			for (var p in data) {
				if (rule.xAxisName && p == rule.xAxisName.en) {
					subserie.name = data[p];
					if (rule.xAxisName.type == "datetime") {
						subserie.name = chartsFunc._formatTime(data[p], rule.tf);
					}
					if (isScatter || isBubble) {
						var x = parseFloat(+data[p]);
						subserie.x = parseFloat(x.toFixed(2));
					}
				} else if (p == yan.en) {
					if (data[p]) {
						var y = parseFloat(+data[p]);
						subserie.value = parseFloat(y.toFixed(2));
					} else {
						subserie.value = null;
					}
				} else if (isBubble && rule.zAxisName && p == rule.zAxisName.en) {
					subserie.zoneAxis = "z";
					if (data[p]) {
						var z = parseFloat(+data[p]);
						subserie.z = parseFloat(z.toFixed(2));
					} else {
						subserie.z = null;
					}
				}
				subserie[p] = data[p];
			}
			if (!subserie.name) { // 如果系列名x轴未配置 则默认使用y轴的列名
				subserie.name = yan.cn;
			}
			return subserie;
		},
	}
	
	
	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor : Build
	});
	

}(jQuery);