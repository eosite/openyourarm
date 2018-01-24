var chartsFunc = {
	init: function(rule, args) {
		var options = args[0];
		options._querydata = rule.originParam || (getParams(rule.inid) ? JSON.stringify(getParams(rule.inid)) : null);
		$.ajax({
			type: "POST",
			url: contextPath + "/mx/form/charts/datas",
			contentType: "application/json",
			dataType: "json",
			data: JSON.stringify({
				'rid': options.rid,
				'params': options._querydata,
				'pageSize' : 99999,
				'page' : 1
			}),
			success: function(msg) {
				var series = chartsFunc.transform(msg, options),
					$this = $('#' + rule.inid),
					
				sersLens = [];
				options.series = series;
				if(options.pieInnerSize != undefined && options.pieInnerSize != ""){
					options.series[0].innerSize = options.pieInnerSize;
				} 
				if(options.chart.type == 'pie'){
					options.tooltip.pointFormat = '{series.name}: <b>{point.y}({point.percentage:.1f}%)</b>';
				} 
				$.each(series, function(i, v) {
					v.data.length > 1000 && sersLens.push(v.data.length);
					
				});
				
					
				if (sersLens.length) {
					options.plotOptions.series.turboThreshold = Math.max.apply(Math, sersLens);
				}
				if ($this.data("clickEvent")) {
					options.plotOptions.series.point.events.click = $this.data("clickEvent");
				}
				$this.highcharts(options);
				if (options.rotate3D) {
					chartsFunc.rotate3D(rule.inid);
				}
				if ($this.resize) {
					$this.resize(function(width,height,flag) {
						$this.highcharts().setSize($this.width(), $this.height(), true);
						// if(!width){
						// 	width = $this.width();
						// }
						// if(!height){
						// 	height = $this.height();
						// }
						// if(flag == null){
						// 	flag = true;
						// }
						// if(typeof width == 'object'){
						// 	$this.highcharts().setSize($this.width(), $this.height(), true);	
						// }else{
						// 	$this.highcharts().setSize(width, height, flag);	
						// }
					});
				}
			}
		});
	},
	tshow: function(rule, args) { // 显示
		var mtcharts = pubsub.getJQObj(rule)
		// mtcharts.highcharts().setSize(100, 100, true);
		mtcharts.show();
		// mtcharts.highcharts().setSize(width, height, true);
	},
	getRow: function(rule, args) {
		// $("#" + rule.inid).highcharts();
		var data = args[1];
		if (rule.columnName == "categories") { //系列名称
			var $id = pubsub.getJQObj(rule,true);
			var type = $id.highcharts().options.chart.type;
			if(type == 'pie'){
				return data.name;
			}

			return (data.series && data.series.name) || "";
		}
		return data[rule.columnName];
	},
	fresh: function(id,params,args){
		var $id = pubsub.getJQObj(id);
		var $highcharts = $id.highcharts();
		if ($highcharts) {
			var options = $id.highcharts().options;
			$.ajax({
				type: "POST",
				url: contextPath + "/mx/form/charts/datas",
				contentType: "application/json",
				dataType: "json",
				data:  JSON.stringify({
					'rid': options.rid,
					'params': options._querydata
				}),
				success: function(msg) {
					var series = chartsFunc.transform(msg, options);
					options.series = series;
					$id.highcharts(options);
				}
			});
		}
	},
	refresh: function(id, params, args) {
		chartsFunc.linkageControl(id, params, args);
	},
	linkageControl: function(id, params, args) {
		var $id = pubsub.getJQObj(id);
		var $highcharts = $id.highcharts();
		if ($highcharts) {
			var options = $id.highcharts().options;
			var data = {
				rid: options.rid
			};
			data.params = JSON.stringify(params);
			$.ajax({
				type: "POST",
				url: contextPath + "/mx/form/charts/datas",
				contentType: "application/json",
				dataType: "json",
				data: JSON.stringify(data),
				success: function(msg) {
					var series = chartsFunc.transform(msg, options);
					options.series = series;
					if(options.pieInnerSize != undefined && options.pieInnerSize != ""){
						options.series[0].innerSize = options.pieInnerSize;
					} 
					$id.highcharts(options);
				}
			});
		} else {
			// 循环监测
			var chartsfn = setInterval(function() {
				if ($id.highcharts()) {
					var options = $id.highcharts().options;
					var data = {
						rid: options.rid
					};
					data.params = JSON.stringify(params);
					$.ajax({
						type: "POST",
						url: contextPath + "/mx/form/charts/datas",
						contentType: "application/json",
						dataType: "json",
						data: JSON.stringify(data),
						success: function(msg) {
							var series = chartsFunc.transform(msg, options);
							options.series = series;
							$id.highcharts(options);
						}
					});
					clearInterval(chartsfn);
				}
			}, 200);
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
				}else{
					if (isScatter || isBubble) {
						var x = parseFloat(+data[p]);
						subserie.x = parseFloat(x.toFixed(2));
					}
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
				}else{
					if (isScatter || isBubble) {
						var x = parseFloat(+data[p]);
						subserie.x = parseFloat(x.toFixed(2));
					}
				}
			} else if (p == yan.en) {
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
		if (!subserie.name) { // 如果系列名x轴未配置 则默认使用y轴的列名
			subserie.name = yan.cn;
		}
		return subserie;
	},
	_formatTime: function(str, format) { // 时间转换
		//return kendo.format('{0:' + (format || 'yyyy-MM-dd') + '}', kendo.parseDate(str)) || str;
		return hmtdUtils.parseDate(str).format(format || 'yyyy-MM-dd') || str;
		//return new Date(str).format(format || 'yyyy-MM-dd') || str;
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
	/**
	 * 计算表达式
	 * @param  {[type]} data      [数据来源]
	 * @param  {[type]} format    [表达式]
	 * @param  {[type]} yan 	  [y轴的信息]
	 * @param  {[type]} datasSize [总数]
	 * @return {[type]}           [返回Str]
	 */
	_convertExpToStr : function(exp,data,yan,datasSize){
		var reg = /{[\w|\.]+}/g;
		var columns = exp.match(reg);
		$.each(columns,function(k,column){
			if(column == '{point.name}'){
				//获取系列名称
				exp = exp.replace(column,yan.cn);
			}else if(column == "{y}"){
				//获取值名称
				exp = exp.replace(column,data[yan.en]);
			}else if(column == "{total}"){
				//总数
				exp = exp.replace(column,datasSize);
			}
		});
		return exp;
	},
	_groupBySeries: function(datas, rule, ind, opts) { // 根据系列分组
		var series = [],
			serie, subseries, subserie, data, datasSize = datas.length,
			yans = rule.yAxisName,
			yansSize = yans != undefined ? yans.length : 0,
			an = rule.axisName,
			tsize = rule.tSize,
			tname = rule.tName,
			tid = rule.tid,
			tparent = rule.tParent,
			tcolor = rule.tColor,
			yan, xan = rule.xAxisName,
			yAxisLength = opts.yAxis.length > 1;
		rule._chartType = opts.chart.type;
		rule.pieInnerSize = opts.pieInnerSize;
		// 静态系列
		if(yans != undefined){
		if (yans.length == 1 && an != undefined && an.en != "") {
			for (var i = 0; i < datasSize; i++) {
				data = datas[i];
				var yAxisName = data[yans[0].en];
				if (yans[0].type == "datetime") {
					yAxisName = chartsFunc._formatTime(yAxisName, rule.tf);
				}
				/*if(tsize != undefined)
					data['value'] = parseInt(data[tsize.en]);					
				if(tcolor != undefined)
					data['color'] = data[tcolor.en];
				if(tname != undefined)
					data['name'] = data[tname.en];
				if(tid != undefined)
					data['id'] = data[tid.en];
				if(tparent != undefined){
					if(opts.istree)
						data['parent'] = data[tparent.en];					
				}*/
					// var xAxisName = data[xan.en]; 系列名
				var hasSerie = chartsFunc._hasYAxisName(series, yAxisName);
				if (hasSerie) { // 如果包含
					var subserie = chartsFunc._staticBuildSubserie(data, rule);
					//subserie.color = '#000';
					var formatColor =serie.dataLabels.formatColor;
					if(formatColor){
						var boo = "";
						try{
							boo = JSON.parse(formatColor)
						}catch(e){
							
						}
						if(boo){
							var strExp = "";
							$.each(boo,function(i,item){
								var expression = item.expression;
								// chartsFunc._evalExp(expression,data,serie.name);
								var color = item.color;
								var exp = chartsFunc._convertExpToStr(expression,data,yan,datasSize);
								if(eval(exp)){
									//计算表达式并返回字符串
									// serie.dataLabels.format = chartsFunc._convertExpToStr(htmlExpression,data,yan,datasSize);
									subserie.color = color;
								}
							})
						}
					}
					
					hasSerie.data.push(subserie);
				} else {
					serie = {};
					serie.name = yAxisName;
					if (yAxisLength) {
						serie.yAxis = ind;
					}
					// serie.xAxis = ind ;
					serie.type = rule.chartType;
					if (rule.stack) {
						serie.stack = data[rule.stack];
					}

					serie.dataLabels = serie.dataLabels || $.extend(true,{},opts.plotOptions.series.dataLabels) || {};
					//获取表达式模板中的信息
					var formatExp = serie.dataLabels.formatExp;
					if(formatExp && formatExp.length > 0){
						var strExp = "";
						$.each(formatExp,function(i,item){
							var expression = item.expression;
							// chartsFunc._evalExp(expression,data,serie.name);
							var htmlExpression = item.htmlExpression;
							var exp = chartsFunc._convertExpToStr(expression,data,yan,datasSize);
							if(eval(exp)){
								//计算表达式并返回字符串
								// serie.dataLabels.format = chartsFunc._convertExpToStr(htmlExpression,data,yan,datasSize);
								serie.dataLabels.format = htmlExpression;
							}
						})
					}
					
					var formatColor =serie.dataLabels.formatColor;
					if(formatColor){
						var boo = "";
						try{
							boo = JSON.parse(formatColor)
						}catch(e){
							
						}
						if(boo){
							var strExp = "";
							$.each(boo,function(i,item){
								var expression = item.expression;
								// chartsFunc._evalExp(expression,data,serie.name);
								var color = item.color;
								var exp = chartsFunc._convertExpToStr(expression,data,yan,datasSize);
								if(eval(exp)){
									//计算表达式并返回字符串
									// serie.dataLabels.format = chartsFunc._convertExpToStr(htmlExpression,data,yan,datasSize);
									subserie.color = color;
								}
							})
						}
					}
					
					chartsFunc._setShowValueAndPoint(serie, rule);

					/*
					 * if(serie.name == "0"){ serie.stack =ind+ "我的" ; }else{
					 * serie.stack =ind+ "你的" ; }
					 */
					subseries = [];
					subserie = chartsFunc._staticBuildSubserie(data, rule);
					var formatColor =serie.dataLabels.formatColor;
					if(formatColor){
						var formatc ="";
						try{
							formatc = JSON.parse(formatColor)
						}catch(e){
						}
						if(formatc){
							var strExp = "";
							$.each(formatc,function(i,item){
								var expression = item.expression;
								var color = item.color;
								var exp = chartsFunc._convertExpToStr(expression,data,yan,datasSize);
								if(eval(exp)){
									//计算表达式并返回字符串
									// serie.dataLabels.format = chartsFunc._convertExpToStr(htmlExpression,data,yan,datasSize);
									subserie.color = color;
								}
							})
						}
					}
				
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
					var hasSerie = chartsFunc._hasYAxisName(series, yan.cn);
					/*if(tsize != undefined)
						data['value'] = parseInt(data[tsize.en]);					
					if(tcolor != undefined)
						data['color'] = data[tcolor.en];
					if(tname != undefined)
						data['name'] = data[tname.en];
					if(tid != undefined)
						data['id'] = data[tid.en];
					if(tparent != undefined){
						if(opts.istree)
							data['parent'] = data[tparent.en];					
					}*/
					if (hasSerie) {
//						hasSerie.data.push(chartsFunc._buildSubserie(data, rule, yan));
						var subserie = chartsFunc._buildSubserie(data, rule, yan);
						//subserie.color = '#000';
						var formatColor =serie.dataLabels.formatColor;
						if(formatColor){
							var boo = "";
							try{
								boo = JSON.parse(formatColor)
							}catch(e){
								
							}
							if(boo){
								var strExp = "";
								$.each(boo,function(i,item){
									var expression = item.expression;
									// chartsFunc._evalExp(expression,data,serie.name);
									var color = item.color;
									var exp = chartsFunc._convertExpToStr(expression,data,yan,datasSize);
									if(eval(exp)){
										//计算表达式并返回字符串
										// serie.dataLabels.format = chartsFunc._convertExpToStr(htmlExpression,data,yan,datasSize);
										subserie.color = color;
									}
								})
							}
						}
						hasSerie.data.push(subserie);
					} else {
						serie = {};
						if (yAxisLength) {
							serie.yAxis = ind;
						}
						serie.name = yan.cn;
						serie.type = rule.chartType;

						serie.dataLabels = serie.dataLabels || $.extend(true,{},opts.plotOptions.series.dataLabels) || {};
						//获取表达式模板中的信息
						var formatExp = serie.dataLabels.formatExp;
						if(formatExp && formatExp.length > 0){
							var strExp = "";
							$.each(formatExp,function(i,item){
								var expression = item.expression;
								// chartsFunc._evalExp(expression,data,serie.name);
								var htmlExpression = item.htmlExpression;
								var exp = chartsFunc._convertExpToStr(expression,data,yan,datasSize);
								if(eval(exp)){
									//计算表达式并返回字符串
									// serie.dataLabels.format = chartsFunc._convertExpToStr(htmlExpression,data,yan,datasSize);
									serie.dataLabels.format = htmlExpression;
								}
							})
						}
						

						chartsFunc._setShowValueAndPoint(serie, rule);
						if (rule.stack) {
							serie.stack = data[rule.stack];
						}
						subseries = [];
						subserie = chartsFunc._buildSubserie(data, rule, yan);
						
						var formatColor =serie.dataLabels.formatColor;
						if(formatColor){
							var boo = "";
							try{
								boo = JSON.parse(formatColor)
							}catch(e){
								
							}
							if(boo){
								var strExp = "";
								$.each(boo,function(i,item){
									var expression = item.expression;
									// chartsFunc._evalExp(expression,data,serie.name);
									var color = item.color;
									var exp = chartsFunc._convertExpToStr(expression,data,yan,datasSize);
									if(eval(exp)){
										//计算表达式并返回字符串
										// serie.dataLabels.format = chartsFunc._convertExpToStr(htmlExpression,data,yan,datasSize);
										subserie.color = color;
									}
								})
							}
						}
						
						//subserie.color = "#000";
						subseries.push(subserie);
						serie.data = subseries; //颜色存放地点
						if (opts.colorByPoint) {
							serie.colorByPoint = true; //根据点自动分配颜色
						}
						if ((serie.type == "pie" || (!serie.type && opts.chart.type == "pie")) && opts.pieInnerSize) {
							serie.innerSize = opts.pieInnerSize;
						}
						series.push(serie);
					}
				}
			}
		  }
		}
		return series;
	},
	_buildPie: function(serie) { // 饼图多系列Y轴合并数据
		var ss = [],
			s = {
				data: []
			},
			ser, sdatas;
		for (var j = 0, l = serie.length; j < l; j++) {
			ser = serie[j];
			sdatas = ser.data;
			s.name = sdatas[0].name;
			for (var k = 0, ll = sdatas.length; k < ll; k++) {
				sdatas[k].name = ser.name;
				s.data.push(sdatas[k]);
			}
		}
		ss.push(s);
		return ss;
	},
	_hastreeName: function(series, name) {
		var seriesSize = series.length,
			serie;
		if (seriesSize) {
			for (var i = 0; i < seriesSize; i++) {
				serie = series[i];
				if (serie.name == name) {
					return serie;
				}
			}
		}
		return null;
	},
	_buildtreemap : function(rule,datas,opts){
		var tsize = rule.tSize,
			tname = rule.tName,
			tid = rule.tid,
			tparent = rule.tParent,
			tcolor = rule.tColor,
			datasSize = datas.length,
			subserie,series = [],colorCount = 0,
			color = ['#d94e5d','#eac736','#50a3ba','#404a59','#323c48','#a6c84c', '#ffa022', '#46bee9'];
		for (var i = 0; i < datasSize; i++) {
			    data = datas[i];	
			    var hasSerie = chartsFunc._hastreeName(series,"treemap")
				if(rule.yAxisName != undefined)
					data['value'] = parseInt(data[rule.yAxisName[0].en]);
					
				if(rule.xAxisName != undefined)
					data['name'] = data[rule.xAxisName.en];
				if(tid != undefined)
					data['id'] = data[tid.en];
				if(tparent != undefined){
					if(opts.istree)
						data['parent'] = data[tparent.en];					
				}
				colorCount = colorCount < color.length-1 ? colorCount+1 : 0; 
				data['color'] = color[colorCount];
				if (hasSerie) {
					hasSerie.data.push(data);
				}
				else{
				serie = {};
				serie.type = rule.chartType;
				serie.name = "treemap";
				serie.layoutAlgorithm = 'stripes';
				serie.alternateStartingDirection = true;
				var levels = [{
					level: 1,
	                layoutAlgorithm: 'sliceAndDice',
	                dataLabels: {
	                    enabled: true,
	                    align: 'left',
	                    verticalAlign: 'top',
	                    style: {
	                        fontSize: '15px',
	                        fontWeight: 'bold'
	                    }
	                }
				}]
				if(!opts.istree){
					levels[0].dataLabels.verticalAlign = "middle";
					levels[0].dataLabels.align = "center";
				}
				serie.levels = levels;	
				chartsFunc._setShowValueAndPoint(serie, rule);
				if (rule.stack) {
					serie.stack = data[rule.stack];
				}
				subseries = [];
				subseries.push(data);
				serie.data = subseries;
		    	if (opts.pieInnerSize) {
					serie.innerSize = opts.pieInnerSize;
				}				
				   series.push(serie);
				}
		}
		return series;
	},
	transform: function(datas, opts) {
		var retobj = {},
			rules = opts.chartsSource,
			datasSize = datas.length,
			data, rule, series = [];
		if (datasSize) {
			for (var i = 0; i < datasSize; i++) {
				data = datas[i];
				rule = chartsFunc._getRule(rules, data.id);
				if (rule) {
					var serie = chartsFunc._groupBySeries(data.data, rule, i, opts);
					if (serie && serie.length > 1 && opts.chart.type == "pie") { // 多饼图数据合并处理
						serie = chartsFunc._buildPie(serie);
					}
					if (opts.chart.type == "treemap") { // 多饼图数据合并处理
						serie = chartsFunc._buildtreemap(rule,data.data,opts);
					}
					
					$.merge(series, serie);
				}
			}
			if (opts.sortLegend) {
				var temp = $.extend([], series);
				var sortLegend = opts.sortLegend;
				/*for (var j = 0; j < sortLegend.length; j++) {
					series[j] = temp[(sortLegend[j] - 1)];
				}*/
				var diff = 0,
					tempSerie;
				for (var j = 0; j < sortLegend.length; j++) {
					tempSerie = temp[(sortLegend[j] - 1)];
					if (tempSerie)
						series[j - diff] = tempSerie;
					else
						diff++;
				}
			}
			return series;
		}
		return [];
	},
	rotate3D: function(id) {
		var chart = $('#' + id).highcharts();
		$(chart.container).bind('mousedown.hc touchstart.hc', function(e) {
			e = chart.pointer.normalize(e);
			var posX = e.pageX,
				posY = e.pageY,
				alpha = chart.options.chart.options3d.alpha,
				beta = chart.options.chart.options3d.beta,
				newAlpha, newBeta, sensitivity = 5;
			$(document).bind({
				'mousemove.hc touchdrag.hc': function(e) {
					newBeta = beta + (posX - e.pageX) / sensitivity;
					newBeta = Math.min(100, Math.max(-100, newBeta));
					chart.options.chart.options3d.beta = newBeta;
					newAlpha = alpha + (e.pageY - posY) / sensitivity;
					newAlpha = Math.min(100, Math.max(-100, newAlpha));
					chart.options.chart.options3d.alpha = newAlpha;
					chart.redraw(false);
				},
				'mouseup touchend': function() {
					$(document).unbind('.hc');
				}
			});
		});
	},
	changeSeriesValue: function(rule, args) {
		var mtcharts = pubsub.getJQObj(rule);
		var mtcharts_hightchart = mtcharts.highcharts();
		var series = mtcharts_hightchart.series;
		// var name = 
		$.each(args, function(key, value) {

			$.each(series, function(i, item) {
				if (item.name == key) {
					var valArray = [];
					$.each(item.data, function(k, kitem) {
						valArray.push(parseInt(value));
					})
					item.setData(valArray);
				}
			})
		})
		mtcharts_hightchart.redraw();
	}
};
pubsub.sub("charts", chartsFunc);

/*$(window).resize(function() {
	setTimeout(function() {
		var mtcharts = $("[data-role=charts]");
		if (mtcharts && mtcharts.length) {
			$.each(mtcharts, function(index, val) {
				var $this = $(this);
				$this.highcharts().setSize($this.width(), $this.height(), true);
			});
		}
	}, 100);
});*/

// highcharts 全局设置
try {
	Highcharts.setOptions({
		lang: {
			drillUpText: '<< 返回 {series.name}',
			noData: '暂无数据'
		}
	});
} catch (ex) {}