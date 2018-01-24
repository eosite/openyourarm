(function($, window, document, undefined) {
	var plugin = "charts", optionKey = plugin + ".options",
	
    Build = function(o) {
		this.el = $(o);
		this.w = $(document);
		this.option = this.el.data(optionKey).options;
		this._init($(this),o.staticOpt);
		
	};
	
	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string' && (options in $.fn[plugin].methods)) {
			var p = this.data(plugin);
			if (!plugin) {
				this.data("bootstrap_tabs", new Plugin(this, params));
            } 
			return p ? p[options].apply(p, Array.prototype.slice.call(
					arguments, 1)) : $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i, o) {
			var state = $.data(o, optionKey);
			if (state) {
				$.extend(true, state.options, options);
			} else {
				$.data(o, optionKey, {
					options : $
							.extend(true, {}, $.fn[plugin].defaults, options),
					datas : param
				});
			}
			o.staticOpt = options;
			$.data(o, plugin, new Build(o));
		});
	};

	$.fn[plugin].defaults = {
			
	};

	$.fn[plugin].methods = {
		_init : function(e,options){
			var that = this;		
			options.datas != null ? that._staticInit(e,options) : null;
		},
		//矩形树图数据
		staticHeatMapData : function(datas,columns,fieldGrid){
			var temp = [];
			$.each(datas,function(t,data){
				var ele = {};
				$.each(columns,function(j,column){
					$.each(fieldGrid,function(i,field){
						if(column.field != ''){
							if(column.field == field.columnName){
								if(field.type == 'xAxisName'){
									ele['name'] = data[field.columnName];
								}
								else if(field.type == 'yAxisName'){
									if(data[field.columnName] != ""){
									    ele['value'] = parseInt(data[field.columnName]);
									}
									else{
										ele['value'] = 0;
									}
								}
								else if(field.type == 'tColor'){
									ele['color'] = data[field.columnName];
								}
								else if(field.type == 'tParent'){
									if(data[field.columnName] != ""){
										ele['parent'] = data[field.columnName];
									}
								}
								else if(field.type == 'index'){
									if(data[field.columnName] != ""){
									    ele['id'] = data[field.columnName];
									}
								}
							}
						}
					});
				});			
				temp.push(ele);
			});
			return temp;
		},
		staticPieData : function(datas,columns,fieldGrid){
			var temp = [];
			$.each(datas,function(t,data){
				var ele = {};
				$.each(columns,function(j,column){
					$.each(fieldGrid,function(i,field){
						if(column.field != ''){
							if(column.field == field.columnName){
								if(field.type == 'xAxisName'){
									ele['name'] = data[field.columnName];
								}
								else if(field.type == 'yAxisName'){
									ele['y'] = parseInt(data[field.columnName]);
								}
							}
						}
					});
				});			
				temp.push(ele);
			});
			return temp;
		},
		//矩形树图数据
		staticDialData : function(datas,columns,fieldGrid){
			var temp = [];
			$.each(datas,function(t,data){
				var ele = {};
				$.each(columns,function(j,column){
					$.each(fieldGrid,function(i,field){
						if(column.field != ''){
							if(column.field == field.columnName){
								if(field.type == 'xAxisName'){
									ele['name'] = data[field.columnName];
								}
								else if(field.type == 'yAxisName'){
									ele['data'] = [parseInt(data[field.columnName])];		
								}		
							}
						}
					});
				});			
				temp.push(ele);
			});
			return temp;
		},
		//推叠图数据
		staticPublicCategoData : function(options){
			var categories = [];
			$.each(options.datas,function(t,data){
				var ele = {};
				$.each(options.columns,function(j,column){
					$.each(options.fieldGrid.datas,function(i,field){
						if(column.field != ''){
							if(column.field == field.columnName){
								if(field.type == 'xAxisName'){
									var f = false;
									$.each(categories,function(i,categorie){
										if(data[field.columnName] == categorie){
											f = true;
										}
									});
									if(f == false){
										categories.push(data[field.columnName]);
									}
									
								}					
							}
						}
					});
				});			
						
			});
			return categories;
		},
		//推叠图数据
		staticPublicNameData : function(options){
			var names = [];
			$.each(options.datas,function(t,data){
				var ele = {};
				$.each(options.columns,function(j,column){
					$.each(options.fieldGrid.datas,function(i,field){
						if(column.field != ''){
							if(column.field == field.columnName){
								if(field.type == 'yAxisName'){
									var f = false;
									$.each(names,function(i,name){
										if(data[field.columnName] == name){
											f = true;
										}
									});
									if(f == false){
										names.push(data[field.columnName]);
									}
									
								}					
							}
						}
					});
				});			
						
			});
			return names;
		},
		staticPublicData : function(options){
			var temp = [];
			var datas = [];
			$.each(options.names,function(i,name){
				var ele = {};
				$.each(options.datas,function(t,data){
					
					$.each(options.columns,function(j,column){
						$.each(options.fieldGrid.datas,function(y,field){
							if(column.field != ''){
								if(column.field == field.columnName){
									var f = false
									if(field.type == 'yAxisName'){
										if(data[field.columnName] == name){
											$.each(options.fieldGrid.datas,function(y,field2){
												if(field2.type == 'axisName'){
													datas.push(parseInt(data[field2.columnName]));
												}
											});									
										}	
									}
									
								}
							}
						});
					});
					
				});
				ele['name'] = name;
				ele['data'] = datas;
				datas = [];
				temp.push(ele);
			});
			return temp;
		},
		staticScatteNameData : function(options){
			var names = [];
			$.each(options.datas,function(t,data){
				var ele = {};
				$.each(options.columns,function(j,column){
					$.each(options.fieldGrid.datas,function(i,field){
						if(column.field != ''){
							if(column.field == field.columnName){
								if(field.type == 'xAxisName'){
									var f = false;
									$.each(names,function(i,name){
										if(data[field.columnName] == name){
											f = true;
										}
									});
									if(f == false){
										names.push(data[field.columnName]);
									}
									
								}					
							}
						}
					});
				});			
						
			});
			return names;
		},
		staticScatteData : function(options){
			var temp = [];
			var datas = [];
			var node = [];
			$.each(options.names,function(i,name){
				var ele = {};
				$.each(options.datas,function(t,data){
					
					$.each(options.columns,function(j,column){
						$.each(options.fieldGrid.datas,function(y,field){
							if(column.field != ''){
								if(column.field == field.columnName){
									var f = false
									if(field.type == 'xAxisName'){
										if(data[field.columnName] == name){
											$.each(options.fieldGrid.datas,function(y,field2){
												if(field2.type == 'yAxisName'){
													datas.push(parseInt(data[field2.columnName]));
												}
												if(field2.type == 'axisName'){
													datas.push(parseInt(data[field2.columnName]));
												}
											});									
										}	
									}
									
								}
							}
						});
						node.push(datas);
						datas = [];
					});
					
				});
				ele['name'] = name;
				ele['data'] = node;
				node = [];
				temp.push(ele);
			});
			return temp;
		},
		
		_staticInit : function(event,options){
			var that = this,
			    type = options.type.substring(options.type.indexOf("_")+1,options.type.length),
			    config;
			if(type == 'treemap'){
				options.datas = that.staticHeatMapData(options.datas,options.columns,options.fieldGrid.datas);
				config = that._charts_treemap(options);
			}
			else if(type == 'pile'){
				options.categories = that.staticPublicCategoData(options);
				options.names = that.staticPublicNameData(options);
				options.datas = that.staticPublicData(options);
				config = that._charts_column(options);
			}
			else if(type == 'area'){
				options.categories = that.staticPublicCategoData(options);
				options.names = that.staticPublicNameData(options);
				options.datas = that.staticPublicData(options);
				config = that._charts_area(options);
			}
			else if(type == 'scatter'){
				options.names = that.staticScatteNameData(options);
				options.datas = that.staticScatteData(options);
				config = that._charts_scatter(options);
			}
			else if(type == 'bar'){
				options.categories = that.staticPublicCategoData(options);
				options.names = that.staticPublicNameData(options);
				options.datas = that.staticPublicData(options);
				config = that._charts_bar(options);
			}
			else if(type == 'pie'){
				options.datas = that.staticPieData(options.datas,options.columns,options.fieldGrid.datas);
				config = that._charts_pie(options);
			}
			else if(type == 'line'){
				options.categories = that.staticPublicCategoData(options);
				options.names = that.staticPublicNameData(options);
				options.datas = that.staticPublicData(options);
				config = that._charts_line(options);
			}
			else if(type == 'histo'){
				options.categories = that.staticPublicCategoData(options);
				options.names = that.staticPublicNameData(options);
				options.datas = that.staticPublicData(options);
				config = that._charts_histo(options);
			}
			if(type == 'dial'){
			   that.el.highcharts({
				   chart: {
			            type: 'gauge',
			            plotBackgroundColor: null,
			            plotBackgroundImage: null,
			            plotBorderWidth: 0,
			            plotShadow: false
			        },
			        title: {
			            text: '仪表盘'
			        },
			        pane: {
			            startAngle: -150,
			            endAngle: 150,
			            background: [{
			                backgroundColor: {
			                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
			                    stops: [
			                        [0, '#FFF'],
			                        [1, '#333']
			                    ]
			                },
			                borderWidth: 0,
			                outerRadius: '109%'
			            }, {
			                backgroundColor: {
			                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
			                    stops: [
			                        [0, '#333'],
			                        [1, '#FFF']
			                    ]
			                },
			                borderWidth: 1,
			                outerRadius: '107%'
			            }, {
			                // default background
			            }, {
			                backgroundColor: '#DDD',
			                borderWidth: 0,
			                outerRadius: '105%',
			                innerRadius: '103%'
			            }]
			        },
			        // the value axis
			        yAxis: {
			            min: 0,
			            max: 200,
			            minorTickInterval: 'auto',
			            minorTickWidth: 1,
			            minorTickLength: 10,
			            minorTickPosition: 'inside',
			            minorTickColor: '#666',
			            tickPixelInterval: 30,
			            tickWidth: 2,
			            tickPosition: 'inside',
			            tickLength: 10,
			            tickColor: '#666',
			            labels: {
			                step: 2,
			                rotation: 'auto'
			            },
			            
			            plotBands: [{
			                from: 0,
			                to: 120,
			                color: '#55BF3B' // green
			            }, {
			                from: 120,
			                to: 160,
			                color: '#DDDF0D' // yellow
			            }, {
			                from: 160,
			                to: 200,
			                color: '#DF5353' // red
			            }]
			        },
			        series: that.staticDialData(options.datas,options.columns,options.fieldGrid.datas)
			    });
			}			
			else{
				that._highcharts(config);
			}
			
		},
		_highcharts : function(elements){
			var that = this;
			that.el.highcharts(elements);
		},
		_charts_line : function(options){
			var config = {
				    title: {
				        text: '不同城市的月平均气温',
				        x: -20
				    },
				    subtitle: {
				        text: '数据来源: WorldClimate.com',
				        x: -20
				    },
				    xAxis: {
				        categories: options.categories
				    },
				    yAxis: {
				        title: {
				            text: '温度 (°C)'
				        },
				        plotLines: [{
				            value: 0,
				            width: 1,
				            color: '#808080'
				        }]
				    },
				    tooltip: {
				        valueSuffix: '°C'
				    },
				    legend: {
				        layout: 'vertical',
				        align: 'right',
				        verticalAlign: 'middle',
				        borderWidth: 0
				    },
				    series: options.datas
				
		    }
			return config;
		},
		_charts_histo : function(options){
			var config = {
					 chart: {
				            type: 'column'
				        },
				        
				        xAxis: {
				            categories: options.categories,
				            crosshair: true
				        },
				        yAxis: {
				            min: 0,
				            
				        },
				        tooltip: {
				            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
				            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
				            '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
				            footerFormat: '</table>',
				            shared: true,
				            useHTML: true
				        },
				        plotOptions: {
				            column: {
				                pointPadding: 0.2,
				                borderWidth: 0
				            }
				        },
				        series: options.datas
			}
			return config;
		},
		_charts_pie : function(options){
            var config = {
            		 chart: {
            	            plotBackgroundColor: null,
            	            plotBorderWidth: null,
            	            plotShadow: false
            	        },
            	        title: {
            	            text: '2014 某网站各浏览器浏览量占比'
            	        },
            	        tooltip: {
            	            headerFormat: '{series.name}<br>',
            	            pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
            	        },
            	        plotOptions: {
            	            pie: {
            	                allowPointSelect: true,
            	                cursor: 'pointer',
            	                dataLabels: {
            	                    enabled: true,
            	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
            	                    style: {
            	                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
            	                    }
            	                }
            	            }
            	        },
            	        series: [{
            	            type: 'pie',            	         
            	            data:options.datas 
            	        }]
            }
            return config;
		},
		_charts_bar : function(options){
			var config = {
					chart: {
			            type: 'bar'
			        },
			        title: {
			            text: '各洲不同时间的人口条形图'
			        },
			        subtitle: {
			            text: '数据来源: Wikipedia.org'
			        },
			        xAxis: {
			            categories: options.categories,
			            title: {
			                text: null
			            }
			        },
			        yAxis: {
			            min: 0,
			            title: {
			                text: '人口总量 (百万)',
			                align: 'high'
			            },
			            labels: {
			                overflow: 'justify'
			            }
			        },
			        tooltip: {
			            valueSuffix: ' 百万'
			        },
			        plotOptions: {
			            bar: {
			                dataLabels: {
			                    enabled: true,
			                    allowOverlap: true
			                }
			            }
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'top',
			            x: -40,
			            y: 100,
			            floating: true,
			            borderWidth: 1,
			            backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
			            shadow: true
			        },
			        credits: {
			            enabled: false
			        },
			        series: options.datas
			}
			return config;
		},
		_charts_scatter : function(options){
			var config = {
					 chart: {
				            type: 'scatter',
				            zoomType: 'xy'
				        },
				        
				        xAxis: {
				            title: {
				                enabled: true,
				                text: '身高 (cm)'
				            },
				            startOnTick: true,
				            endOnTick: true,
				            showLastLabel: true
				        },
				        yAxis: {
				            title: {
				                text: '体重 (kg)'
				            }
				        },
				        legend: {
				            layout: 'vertical',
				            align: 'left',
				            verticalAlign: 'top',
				            x: 100,
				            y: 70,
				            floating: true,
				            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF',
				            borderWidth: 1
				        },
				        plotOptions: {
				            scatter: {
				                marker: {
				                    radius: 5,
				                    states: {
				                        hover: {
				                            enabled: true,
				                            lineColor: 'rgb(100,100,100)'
				                        }
				                    }
				                },
				                states: {
				                    hover: {
				                        marker: {
				                            enabled: false
				                        }
				                    }
				                },
				                
				            }
				        },
				        series: options.datas
			}
			return config;
		},
		_charts_area : function(options){
			var config = {
					chart: {
			            type: 'area'
			        },
			        xAxis: {
			            categories: options.categories,
			            tickmarkPlacement: 'on',
			            title: {
			                enabled: false
			            }
			        },
			       
			        
			        plotOptions: {
			            area: {
			                stacking: 'normal',
			                lineColor: '#666666',
			                lineWidth: 1,
			                marker: {
			                    lineWidth: 1,
			                    lineColor: '#666666'
			                }
			            }
			        },
			        series: options.datas
			}
		    return config;
		},
		_charts_column : function(options){
			var c = options.categories;
			var s = options.datas;
			var config = {
					chart: {
			            type: 'column'
			        },
			        title: {
			            text: '堆叠柱形图'
			        },
			        xAxis: {
			            categories: options.categories
			        },
			        yAxis: {
			            min: 0,
			            
			            stackLabels: {
			                enabled: true,
			                style: {
			                    fontWeight: 'bold',
			                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
			                }
			            }
			        },
			        legend: {
			            align: 'right',
			            x: -30,
			            verticalAlign: 'top',
			            y: 25,
			            floating: true,
			            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
			            borderColor: '#CCC',
			            borderWidth: 1,
			            shadow: false
			        },
			        tooltip: {
			            formatter: function () {
			                return '<b>' + this.x + '</b><br/>' +
			                    this.series.name + ': ' + this.y + '<br/>' +
			                    '总量: ' + this.point.stackTotal;
			            }
			        },
			        plotOptions: {
			            column: {
			                stacking: 'normal',
			                dataLabels: {
			                    enabled: true,
			                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
			                    style: {
			                        textShadow: '0 0 3px black'
			                    }
			                }
			            }
			        },
			        series: options.datas
			};
			return config;
		},
		_charts_treemap : function(options){
			var config = {
					series: [{
			            type: 'treemap',
			            layoutAlgorithm: 'stripes',
			            alternateStartingDirection: true,
			            levels: [{
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
			            }],
			            data: options.datas
			        }],
			        
				};
			return config;
		}
	};
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery || window.Zepto, window, document);




















