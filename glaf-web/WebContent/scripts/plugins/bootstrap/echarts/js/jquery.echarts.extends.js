! function($) {
	var plugin = 'echarts';


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
	
	//中国城市地图经纬度,可写在json文件下
	$.fn[plugin].geoCoordMap = {
		    "海门":[121.15,31.89],
		    "鄂尔多斯":[109.781327,39.608266],
		    "招远":[120.38,37.35],
		    "舟山":[122.207216,29.985295],
		    "齐齐哈尔":[123.97,47.33],
		    "盐城":[120.13,33.38],
		    "赤峰":[118.87,42.28],
		    "青岛":[120.33,36.07],
		    "乳山":[121.52,36.89],
		    "金昌":[102.188043,38.520089],
		    "泉州":[118.58,24.93],
		    "莱西":[120.53,36.86],
		    "日照":[119.46,35.42],
		    "胶南":[119.97,35.88],
		    "南通":[121.05,32.08],
		    "拉萨":[91.11,29.97],
		    "云浮":[112.02,22.93],
		    "梅州":[116.1,24.55],
		    "文登":[122.05,37.2],
		    "上海":[121.48,31.22],
		    "攀枝花":[101.718637,26.582347],
		    "威海":[122.1,37.5],
		    "承德":[117.93,40.97],
		    "厦门":[118.1,24.46],
		    "汕尾":[115.375279,22.786211],
		    "潮州":[116.63,23.68],
		    "丹东":[124.37,40.13],
		    "太仓":[121.1,31.45],
		    "曲靖":[103.79,25.51],
		    "烟台":[121.39,37.52],
		    "福州":[119.3,26.08],
		    "瓦房店":[121.979603,39.627114],
		    "即墨":[120.45,36.38],
		    "抚顺":[123.97,41.97],
		    "玉溪":[102.52,24.35],
		    "张家口":[114.87,40.82],
		    "阳泉":[113.57,37.85],
		    "莱州":[119.942327,37.177017],
		    "湖州":[120.1,30.86],
		    "汕头":[116.69,23.39],
		    "昆山":[120.95,31.39],
		    "宁波":[121.56,29.86],
		    "湛江":[110.359377,21.270708],
		    "揭阳":[116.35,23.55],
		    "荣成":[122.41,37.16],
		    "连云港":[119.16,34.59],
		    "葫芦岛":[120.836932,40.711052],
		    "常熟":[120.74,31.64],
		    "东莞":[113.75,23.04],
		    "河源":[114.68,23.73],
		    "淮安":[119.15,33.5],
		    "泰州":[119.9,32.49],
		    "南宁":[108.33,22.84],
		    "营口":[122.18,40.65],
		    "惠州":[114.4,23.09],
		    "江阴":[120.26,31.91],
		    "蓬莱":[120.75,37.8],
		    "韶关":[113.62,24.84],
		    "嘉峪关":[98.289152,39.77313],
		    "广州":[113.23,23.16],
		    "延安":[109.47,36.6],
		    "太原":[112.53,37.87],
		    "清远":[113.01,23.7],
		    "中山":[113.38,22.52],
		    "昆明":[102.73,25.04],
		    "寿光":[118.73,36.86],
		    "盘锦":[122.070714,41.119997],
		    "长治":[113.08,36.18],
		    "深圳":[114.07,22.62],
		    "珠海":[113.52,22.3],
		    "宿迁":[118.3,33.96],
		    "咸阳":[108.72,34.36],
		    "铜川":[109.11,35.09],
		    "平度":[119.97,36.77],
		    "佛山":[113.11,23.05],
		    "海口":[110.35,20.02],
		    "江门":[113.06,22.61],
		    "章丘":[117.53,36.72],
		    "肇庆":[112.44,23.05],
		    "大连":[121.62,38.92],
		    "临汾":[111.5,36.08],
		    "吴江":[120.63,31.16],
		    "石嘴山":[106.39,39.04],
		    "沈阳":[123.38,41.8],
		    "苏州":[120.62,31.32],
		    "茂名":[110.88,21.68],
		    "嘉兴":[120.76,30.77],
		    "长春":[125.35,43.88],
		    "胶州":[120.03336,36.264622],
		    "银川":[106.27,38.47],
		    "张家港":[120.555821,31.875428],
		    "三门峡":[111.19,34.76],
		    "锦州":[121.15,41.13],
		    "南昌":[115.89,28.68],
		    "柳州":[109.4,24.33],
		    "三亚":[109.511909,18.252847],
		    "自贡":[104.778442,29.33903],
		    "吉林":[126.57,43.87],
		    "阳江":[111.95,21.85],
		    "泸州":[105.39,28.91],
		    "西宁":[101.74,36.56],
		    "宜宾":[104.56,29.77],
		    "呼和浩特":[111.65,40.82],
		    "成都":[104.06,30.67],
		    "大同":[113.3,40.12],
		    "镇江":[119.44,32.2],
		    "桂林":[110.28,25.29],
		    "张家界":[110.479191,29.117096],
		    "宜兴":[119.82,31.36],
		    "北海":[109.12,21.49],
		    "西安":[108.95,34.27],
		    "金坛":[119.56,31.74],
		    "东营":[118.49,37.46],
		    "牡丹江":[129.58,44.6],
		    "遵义":[106.9,27.7],
		    "绍兴":[120.58,30.01],
		    "扬州":[119.42,32.39],
		    "常州":[119.95,31.79],
		    "潍坊":[119.1,36.62],
		    "重庆":[106.54,29.59],
		    "台州":[121.420757,28.656386],
		    "南京":[118.78,32.04],
		    "滨州":[118.03,37.36],
		    "贵阳":[106.71,26.57],
		    "无锡":[120.29,31.59],
		    "本溪":[123.73,41.3],
		    "克拉玛依":[84.77,45.59],
		    "渭南":[109.5,34.52],
		    "马鞍山":[118.48,31.56],
		    "宝鸡":[107.15,34.38],
		    "焦作":[113.21,35.24],
		    "句容":[119.16,31.95],
		    "北京":[116.46,39.92],
		    "徐州":[117.2,34.26],
		    "衡水":[115.72,37.72],
		    "包头":[110,40.58],
		    "绵阳":[104.73,31.48],
		    "乌鲁木齐":[87.68,43.77],
		    "枣庄":[117.57,34.86],
		    "杭州":[120.19,30.26],
		    "淄博":[118.05,36.78],
		    "鞍山":[122.85,41.12],
		    "溧阳":[119.48,31.43],
		    "库尔勒":[86.06,41.68],
		    "安阳":[114.35,36.1],
		    "开封":[114.35,34.79],
		    "济南":[117,36.65],
		    "德阳":[104.37,31.13],
		    "温州":[120.65,28.01],
		    "九江":[115.97,29.71],
		    "邯郸":[114.47,36.6],
		    "临安":[119.72,30.23],
		    "兰州":[103.73,36.03],
		    "沧州":[116.83,38.33],
		    "临沂":[118.35,35.05],
		    "南充":[106.110698,30.837793],
		    "天津":[117.2,39.13],
		    "富阳":[119.95,30.07],
		    "泰安":[117.13,36.18],
		    "诸暨":[120.23,29.71],
		    "郑州":[113.65,34.76],
		    "哈尔滨":[126.63,45.75],
		    "聊城":[115.97,36.45],
		    "芜湖":[118.38,31.33],
		    "唐山":[118.02,39.63],
		    "平顶山":[113.29,33.75],
		    "邢台":[114.48,37.05],
		    "德州":[116.29,37.45],
		    "济宁":[116.59,35.38],
		    "荆州":[112.239741,30.335165],
		    "宜昌":[111.3,30.7],
		    "义乌":[120.06,29.32],
		    "丽水":[119.92,28.45],
		    "洛阳":[112.44,34.7],
		    "秦皇岛":[119.57,39.95],
		    "株洲":[113.16,27.83],
		    "石家庄":[114.48,38.03],
		    "莱芜":[117.67,36.19],
		    "常德":[111.69,29.05],
		    "保定":[115.48,38.85],
		    "湘潭":[112.91,27.87],
		    "金华":[119.64,29.12],
		    "岳阳":[113.09,29.37],
		    "长沙":[113,28.21],
		    "衢州":[118.88,28.97],
		    "廊坊":[116.7,39.53],
		    "菏泽":[115.480656,35.23375],
		    "合肥":[117.27,31.86],
		    "武汉":[114.31,30.52],
		    "大庆":[125.03,46.58],
		    "南明区":[106.72,26.57],
		    "云岩区":[106.72,26.62],
		    "乌当区":[106.75,26.63],
		    "白云区":[106.65,26.68],
		    "小河区":[106.7,26.53],
		    "开阳县":[106.97,27.07],
		    "息烽县":[106.73,27.1],
		    "修文县":[106.58,26.83],
		    "清镇市":[106.47,26.55],
		    "六盘水市":[104.83,26.6],
		    "钟山区":[104.83,26.6],
		    "六枝特区":[105.48,26.22],
		    "水城县":[104.95,26.55],
		    "盘县":[104.47,25.72],
		    "遵义市":[106.92,27.73],
		    "红花岗区":[106.92,27.65],
		    "汇川区":[106.92,27.73],
		    "遵义县":[106.82,28.13],
		    "桐梓县":[104.47,25.72],
		    "绥阳县":[107.18,27.95],
		    "正安县":[107.43,28.55],
		    "道真仡佬族苗族自治县":[107.6,28.88],
		    "务川仡佬族苗族自治县":[107.88,28.53],
		    "凤冈县":[107.72,27.97],
		    "湄潭县":[1107.48,27.77],
		    "余庆县":[107.88,27.22],
		    "习水县":[106.22,28.32],
		    "赤水市":[105.7,28.58],
		    "仁怀市":[106.42,27.82],
		    "安顺市":[105.95,26.25],
		    "西秀区":[105.92,26.25],
		    "平坝县":[106.25,26.42],
		    "普定县":[105.75,26.32],
		    "镇宁布依族苗族自治县":[105.77,26.07],
		    "关岭布依族苗族自治县":[105.62,25.95],
		    "紫云苗族布依族自治县":[106.08,25.75],
		    "铜仁地区":[109.18,27.72],
		    "铜仁市":[109.18,27.72],
		    "江口县":[108.85,27.7],
		    "石阡县":[108.23,27.52],
		    "思南县":[108.25,27.93],
		    "德江县":[108.12,28.27],
		    "玉屏侗族自治县":[108.92,27.23],
		    "印江土家族苗族自治县":[108.41,28.02],
		    "沿河土家族自治县":[108.48,28.57],
		    "松桃苗族自治县":[109.2,28.17],
		    "万山特区":[109.2,27.52],
		    "毕节市":[105.28,27.3],
		    "大方县":[105.6,27.15],
		    "黔西县":[106.3,27.03],
		    "金沙县":[106.22,27.47],
		    "织金县":[105.77,26.67],
		    "纳雍县":[105.38,26.78],
		    "赫章县":[109.2,27.52],
		    "兴义市":[104.9,25.08],
		    "兴仁县":[105.18,25.43],
		    "普安县":[104.95,25.78],
		    "晴隆县":[105.22,25.83],
            "平坝" : [106.26,26.42],
		    "贞丰县":[105.65,25.38],
		    "望谟县":[106.1,25.17],
		    "册亨县":[104.9,25.08],
		    "安龙县":[105.47,25.12],
		    "黔东南苗族侗族自治州":[107.97,26.58],
		    "凯里市":[107.97,26.58],
		    "黄平县":[107.9,26.9],
		    "施秉县":[108.12,27.03],
		    "三穗县":[108.68,26.97],
		    "镇远县":[108.42,27.05],
		    "岑巩县":[108.82,27.18],
		    "天柱县":[109.2,26.92],
		    "锦屏县":[109.2,26.68],
		    "剑河县":[108.45,26.73],
		    "台江县":[108.32,26.67],
		    "黎平县":[109.13,26.23],
		    "榕江县":[108.52,25.93],
		    "从江县":[108.9,25.75],
		    "雷山县":[108.7,26.38],
		    "麻江县":[107.58,26.5],
		    "丹寨县":[107.8,26.2],
		    "黔南布依族苗族自治州":[107.52,26.27],
		    "齐伯镇" : [106.1842,26.5607],
		    "乐平镇" : [106.2555,26.4055],
		    "十字回族苗族乡" : [106.262565,26.466373],
		    "鼓楼街道办事处" : [106.14330100000006,26.651772],
		    "夏云镇" : [106.31980699999997,26.447968],
		    "安平街道办事处" : [106.25555699999995,26.405502],
		    "天龙镇" : [106.16430300000002,26.348261],
		    "白云镇" : [106.24177199999997,26.351453],
		    "羊昌布依族苗族乡" : [106.32855999999992,26.339514],
		    "都匀市":[107.52,26.27],
		    "福泉市":[107.5,26.7],
		    "荔波县":[107.88,25.42],
		    "贵定县":[107.23,26.58],
		    "瓮安县":[107.47,27.07],
		    "独山县":[107.53,25.83],
		    "平塘县":[107.32,25.83],
		    "罗甸县":[106.75,25.43],
		    "长顺县":[106.45,26.3],
		    "龙里县":[106.97,26.45],
		    "惠水县":[106.65,26.13]
		    
		};
	$.fn[plugin].defaults = {
	    calculable : true,
	    label : {	//图形上的文本标签，可用于说明图形的一些数据信息  		
    		emphasis : {	//高亮时
    			show : true,	//显示值
    			textStyle:{
    				fontSize: 30,	//值字体大小
        			color: '#333',	//值字体颜色
    			}
    		},  		
    	},    
	    serie:{	//地图数据
	    	zoom: 1,	//放大比例
	    	roam: false,//是否开启鼠标缩放和平移漫游。默认不开启。如果只想要开启缩放或者平移，可以设置成 'scale' 或者 'move'。设置成 true 为都开启
	    	selectedMode: true,	//选中模式，表示是否支持多个选中，默认关闭，支持布尔值和字符串，字符串取值可选'single'表示单选，或者'multiple'表示多选。
	    	map: 'mapext', 	
	    },	
	    events : {
	    	EchartClick : function(item){}
	    }
	};
	var sional;
	$.fn[plugin].methods = {
		_init: function() {
			var that = this;	
//			$(that.target).width(that.options.width);
//			$(that.target).height(that.options.height);
			$(that.target).css("width",that.options.width);
			$(that.target).css("height",that.options.height);
			var option = that.options;
			
			

			function randomData() {
				return Math.round(Math.random() * 1000);
			}

			var target = this.target, that = this;
			//echarts文件与GIS文件冲突，将调用echarts.js文件放在在ArcGIS文件前
			var myChart = echarts.init(that.target);
			$(that.target).resize(function(e){
				myChart.resize();
			})
			that._querydata = that.options._querydata;
			//标题无内容默认NULL
			option.title.text = option.title.text != undefined && option.title.text != "" && option.title.text != "null" ? option.title.text : " ";
			if(option.visible != undefined ){
				if(option.visible != "none"){
					$(that.target).css("display","block");
				}
				else{
					 $(that.target).css("display","none");
				}
			}
			$.ajax({
				type : "POST",
				url : contextPath + "/mx/form/charts/datas",
				contentType : "application/json",
				dataType : "json",
				data : JSON.stringify({
					'rid' : option.rid,
					'params' : that._querydata
				}),
				success : function(msg) {
					//图表类型
					if(that.options.chart.type == 'heatmap' ){
						    //热力图和迁徙图共享同一组件
						    //热力图
							if(that.options.heatmap.type != 'migration'){
						    	var geoCoordMap = $.fn['echarts'].geoCoordMap;
								var data = that.convertData(geoCoordMap,msg[0].data);
								msg[0].data = data;
								var series = that.transform(msg);
								series[0].data = data;
								option.series = series;
								option = that._setHeatMapElement(option);
								//获取地图组件
								that.getMapMessage(option,myChart);				
							
						    }
							//迁徙图
						    else{
						    	var geoCoordMap = $.fn['echarts'].geoCoordMap;
						    	var series = [],serie = {};
						    	
						    	var heatData = that.setMigraData(msg[0].data);
						    	[['平坝', heatData]].forEach(function (item, i) {
						    		//判断数据源有无数据
						    		if(series = msg[1] != undefined && msg[1] != null){
                                        //数据源规则
									    var rules = option.chartsSource,
										    elementsName,
										    longitute,
										    latitude;
									    $.each(rules,function(i,rule){
									    	if(rule.elementsName != undefined && rule.elementsName != null){
									    		elementsName = rule.elementsName.en;
									    	}
									    	if(rule.longitute != undefined && rule.longitute != null){
									    		longitute = rule.longitute.en;
									    	}
									    	if(rule.latitude != undefined && rule.latitude != null){
									    		latitude = rule.latitude.en;
									    	}
									    });
						    			var config = {};
						    			//数据源数据与地区经纬度结合
						    		    $.each(msg[1].data,function(i,data){
						    		    	config[data[elementsName]] = [parseFloat(data[longitute]),parseFloat(data[latitude])];
						    		    });
						    			series = that.setMigSeries(config,item,i);
						    		}
						    		//无数据
						    		else{
						    			series = that.setMigSeries(geoCoordMap,item,i);
						    		}
						    	});
						    	serie.coordinateSystem = 'geo';
						    	series.serie = serie;
						    	//热力图基本属性设置
						    	option = that._setHeatMapElement(option);
						    	option.series = series;
						    	//获取地图组件
						    	that.getMapMessage(option,myChart);				
						    }
					
					}
					//散点地图
					else if(that.options.chart.type == 'scatter'){
						var geoCoordMap = $.fn['echarts'].geoCoordMap;
						var data = that.convertData(geoCoordMap,msg[0].data);
						msg[0].data = data;
						var series = that.transform(msg);
						series[0].data = data;
						option.series = series;
						option = that._setHeatMapElement(option);
						option.visualMap = {
					        min: 0,
					        max: 200,
					        calculable: true,
					        inRange: {
					            color: ['#50a3ba', '#eac736', '#d94e5d']
					        },
					        textStyle: {
					            color: '#fff'
					        }
					    };
						//获取地图组件
						that.getMapMessage(option,myChart);								
					}
					//折线图和堆叠图
					else if(that.options.chart.type == 'cross' || that.options.chart.type == "line"){
						var series = [];
						option.datas = msg[0].data;
						option.categories = that.staticPublicCategoData(option);
						
						option.names = that.staticPublicNameData(option);
						option.datas = that.staticPublicData(option);
						series = option.datas;
						option.series = series;
						option = that._setPublicElement(option);
						myChart.setOption(option, true);
					}
					//其他图表（丁格尔玫瑰、仪表盘）
 					else {
						var series = that.transform(msg);
						option.series = series;
						myChart.setOption(option, true);
					}
				}
			});	
		},
		getMapMessage : function(option,myChart){
			//获取地图组件
	    	if(option.mapid){
				$.ajax({
					url: contextPath + "/mx/form/formfile?method=download",
					type:'post',
					data: {
						id: option.mapid
					},
					success: function(ret) {
						echarts.registerMap('china', ret);		
						myChart.setOption(option,true);
						myChart.on("click",function(e){
							option.events.EchartClick(e);
					    });   
					}
		    	});
	         }	
		},
		//如类似于推叠图格式，则可以采用以下方法
		staticPublicCategoData : function(options){
			var categories = [];
			$.each(options.datas,function(t,data){
				if(options.chartsSource != undefined){
					if(options.chartsSource[0].xAxisName != undefined){
						var f = false;
						$.each(categories,function(i,categorie){
							if(data[options.chartsSource[0].xAxisName.en] == categorie){
								f = true;
							}
						});
						if(f == false){
							categories.push(data[options.chartsSource[0].xAxisName.en]);
						}		
					}
				}	
			});
			return categories;
		},
		
		staticPublicNameData : function(options){
			var names = [];
			$.each(options.datas,function(t,data){
				if(options.chartsSource != undefined){
					if(options.chartsSource[0].yAxisName != undefined){
						var f = false;
						$.each(names,function(i,name){
							if(data[options.chartsSource[0].yAxisName[0].en] == name){
								f = true;
							}
						});
						if(f == false){
							names.push(data[options.chartsSource[0].yAxisName[0].en]);
						}	
					}
				}						
			});
			return names;
		},
		staticPublicData : function(options){
			var temp = [];
			var datas = [];
			
			options.lineSet = options.lineSet != undefined && options.lineSet != "" ? eval("("+options.lineSet+")") : options.lineSet;
			$.each(options.names,function(i,name){
				var ele = {};
				$.each(options.datas,function(t,data){
					if(options.chartsSource != undefined){
						if(options.chartsSource[0].yAxisName != undefined){
							if(data[options.chartsSource[0].yAxisName[0].en] == name){
								if(options.chartsSource[0].axisName != undefined){
									if(data[options.chartsSource[0].axisName.en] != ""){
										var param = {
												name : data[options.chartsSource[0].xAxisName.en],
										        value : parseInt(data[options.chartsSource[0].axisName.en])
										}
										datas.push(param);
									}
									
								}
								
							}
						}
						
					}
				});
				ele['name'] = name;
				var params = [];
				
				$.each(options.categories,function(i,cate){
					var fs = undefined;
					$.each(datas,function(j,item){
						if(item.name == cate){
							fs = item.value;
						}
					});
					if(fs != undefined){
						params.push(fs);
					}
					else{
						params.push("");
					}
					
					
				});
				var isflag = false;
				$.each(params,function(i,v){
					if(v != ""){
						isflag = true;
					}
					
				});
				if(isflag == false){
					ele["tooltip"] =  {
			                "show": false
			        };
				}
				
				
				ele['data'] = params;
				
				ele['type'] = 'line';
				if(options.chart.type == "cross"){
					ele['stack'] =  '总量';
					ele['areaStyle'] = {
							normal: {}
					};
				}
				
				options.label.emphasis.textStyle.fontSize = options.itemTextSize;
				
				datas = [];
				temp.push(ele);
			});
			
			if(options.chart.type == "line"){
				var newEl = {
				          name:"自定义",
				          data: temp[0].data,
				          type:"line",
				          lineStyle:{
				              normal:{
				                  width:0
				              }
				          },
				          showSymbol:false,
				          markLine :{
				        	  data :[]
				          },
				          tooltip:{
				                show : false
				          }
				};
				
				options.defined = options.defined != undefined && options.defined != "" ? eval("("+options.defined+")") : undefined;					
					if(options.defined != undefined ){
						$.each(options.defined,function(k,defined){

							if(defined != undefined){
								
								var newConfig = {
									    yAxis: parseInt(defined.yLineVal),
									    label: {
				                            normal: {
				                                formatter: defined.title
				                            }
				                        },
				                        lineStyle :{
				                            normal : {
				                              color:defined.lineColor
				                            }
				                        }
								}
								
								newEl.markLine.data.push(newConfig);
							}			
						});	
					
				}
					
				temp.push(newEl);
			}
			
			return temp;
		},
		setMigSeries : function(geoCoordMap,item,i){
			 var color = ['#a6c84c', '#ffa022', '#46bee9'],series = [],that = this;

			 series.push({
	    	        name: item[0] + ' Top10',
	    	        type: 'lines',
	    	        zlevel: 1,
	    	        effect: {
	    	            show: true,
	    	            period: 6,
	    	            trailLength: 0.7,
	    	            color: '#fff',
	    	            symbolSize: 3
	    	        },
	    	        
	    	        lineStyle: {
	    	            normal: {
	    	                color: color[i],
	    	                width: 0,
	    	                curveness: 0.2
	    	            }
	    	        },
	    	        data: that.convertDataByMigration(geoCoordMap,item[1])
	    	    },
	    	    {
	    	        name: item[0] + ' Top10',
	    	        type: 'lines',
	    	        zlevel: 2,
	    	        symbol: ['none', 'circle'],
	    	        symbolSize: 10,
	    	        effect: {
	    	            show: true,
	    	            period: 6,
	    	            trailLength: 0,
	    	            symbolSize: 15
	    	        },
	    	       
	    	        lineStyle: {
	    	            normal: {
	    	                color: color[i],
	    	                width: 1,
	    	                opacity: 0.6,
	    	                curveness: 0.2
	    	            }
	    	        },
	    	        data: that.convertDataByMigration(geoCoordMap,item[1])
	    	    },
	    	    {
	    	        name: item[0] + ' Top10',
	    	        
	    	        type: 'effectScatter',
	    	        coordinateSystem: 'geo',
	    	        zlevel: 2,
	    	        rippleEffect: {
	    	            brushType: 'stroke'
	    	        },
	    	        label: {
	    	            normal: {
	    	                show: true,
	    	                position: 'right',
	    	                formatter: '{b}'
	    	            }
	    	        },
	    	        symbolSize: function(val){
	    	        	var x = that.options.leaveNumber != undefined && that.options.leaveNumber != null && that.options.leaveNumber != 0 ? val[2] / that.options.leaveNumber : 10;
	    	            return x;
	    	        	
	    	        },
	    	        itemStyle: {
	    	            normal: {
	    	                color: color[i]
	    	            }
	    	        },
	    	        
	    	        data: item[1].map(function (dataItem) {
	    	            return {
	    	                name: dataItem[1].name,
	    	                value: geoCoordMap[dataItem[1].name].concat([dataItem[1].value])
	    	            };
	    	        })
	    	    });
			 return series;
		},
		
		setMigraData : function(ret){
			var target = this.target,
		        that = this,
		        options = that.options,
			    rules = options.chartsSource,
			    xAxisName,
			    yAxisName,
			    axisName,
			    element = [],
			    config = [];
			$.each(rules,function(y,rule){
				if(rule.xAxisName != undefined && rule.xAxisName != null){
					xAxisName = rule.xAxisName.en;
				}
				if(rule.yAxisName != undefined && rule.yAxisName != null){
					yAxisName = rule.yAxisName[0].en;
				}
				if(rule.axisName != undefined && rule.axisName != null){
					axisName = rule.axisName.en;
				}
			});
			$.each(ret,function(i,data){
				element = [{
					name : '平坝'
				},{
					name : data[yAxisName],
					value : data[axisName]
				}];
				config.push(element);
			});
			return config;
		},
		
		_setPublicElement : function(option){
			option.tooltip = {
		        trigger: 'axis',
		        axisPointer: {
		            type: option.chart.type,
		            label: {
		                backgroundColor: '#6a7985'
		            }
		        }
		    };
			option.grid = {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        height: '90%',
			        width: '85%',
			        containLabel: true,
			       
			};
	
			option.legend['enabled'] = option.legend.enabled;
			option.legend['itemGap'] = 15;
			option.legend['data'] = option.names;
			
			if(option.legend.itemLeft != undefined && option.legend.itemLeft != 0 && option.legend.itemLeft != ''){
				option.legend['left'] = option.legend.itemLeft;
			}
            if(option.legend.itemHeight != undefined && option.legend.itemHeight != 0 && option.legend.itemHeight != ''){
            	option.legend['itemHeight'] = option.legend.itemHeight;
            }
            if(option.legend.itemWidth != undefined && option.legend.itemWidth != 0 && option.legend.itemWidth != ''){
            	option.legend['itemWidth'] = option.legend.itemWidth;
            }
            
            if(option.legend.itemStyle != undefined){
            	option.legend.textStyle = {};
	            if(option.legend.itemStyle.fontSize != undefined && option.legend.itemStyle.fontSize != 0 ){
	            	option.legend.textStyle.fontSize = option.legend.itemStyle.fontSize;
	            }
            }
            if(option.legend.verticalAlign == 'middle'){
            	option.legend['top'] = "50%";
            }
            if(option.legend.verticalAlign == 'bottom'){
            	option.legend['top'] = "90%";
            	option.grid.bottom = '10%';
            	option.grid.height = '85%';
            }
            if(option.legend.align == 'center'){
            	option.legend['x'] = "center";
            }
            if(option.legend.align == 'left'){
            	option.legend['x'] = "left";
            }
            if(option.legend.align == 'right'){
            	option.legend['x'] = "right";
            }
            if(option.legend.layout!= undefined && option.legend.layout != 0 && option.legend.itemStyle.layout != ''){
            	option.legend['orient'] = option.legend.layout;
            }
            option.xAxis = [ {
				type : 'category',
				boundaryGap : false,
				data : option.categories,
				axisLine : {
					lineStyle : {

					}
				},
				axisLabel : {
					show : true,
					textStyle : {

					}
				},
				
			} ];
  		    if(option.xAxisSet != undefined && option.xAxisSet != ""){
  		    	$.each(eval("("+option.xAxisSet+")"),function(i,xAxisSet){
  		    		 option.xAxis[0].axisLine.lineStyle.color = xAxisSet.xlineColor;
  		    		 option.xAxis[0].axisLabel.textStyle.color = xAxisSet.xtextColor;
  		    		 option.xAxis[0].axisLabel.textStyle.fontSize = xAxisSet.xfontSize;
  		    		
  		    	});
  		    }
  			option.yAxis = [ {
  				type : 'value',
  				axisLine : {
  					lineStyle : {}
  				},
  				axisLabel : {
  					show : true,
  					textStyle : {}
  				},
  				min : function(value){
  					var i = parseInt(value.min / 10) - 1;
					return i * 10;
				},
				max : function(value){
					var i = parseInt(value.max / 10) + 1;
					return i * 10;
				}
  			} ];
  			
  			if(option.yAxisSet != undefined && option.yAxisSet != ""){
  		    	$.each(eval("("+option.yAxisSet+")"),function(i,yAxisSet){
  		    		 option.yAxis[0].axisLine.lineStyle.color = yAxisSet.yline;
  		    		 option.yAxis[0].axisLabel.textStyle.color = yAxisSet.ytextColor;
  		    		 option.yAxis[0].axisLabel.textStyle.fontSize = yAxisSet.yfontSize;
  		    		 
  		    		if(yAxisSet.ymin != undefined){
  		    			option.yAxis[0].min = yAxisSet.ymin;
  		    		}  
  		    		if(yAxisSet.ymax != undefined){
  		    			option.yAxis[0].max = yAxisSet.ymax;
  		    		} 
  		    		 
  		    	});
  		    }
			var color = [];
			$.each(option.lineSet,function(i,line){
				color.push(line.lineColor);
			});
			if(option.lineSet != undefined && option.lineSet != ""){
				option.color = color;
			}
			option.legend.align = null;
			option.lineSet != undefined && option.lineSet != "" ? (option.lineSet = JSON.stringify(option.lineSet)) : undefined;
			option.defined != undefined && option.defined != "" ? (option.defined = JSON.stringify(option.defined)) : undefined;
			return option;
		},
		_setHeatMapElement : function(option){
			var areaColor = option.areaColor != undefined && option.areaColor != ""? option.areaColor : '#323c48';
			option.name = 'AOI';
		    option.backgroundColor = option.heatbackgroundColor != undefined && option.heatbackgroundColor != "" && option.heatbackgroundColor != null ? option.heatbackgroundColor : '#404a59';
		    option.visualMap = {
		        splitNumber: 5,
		        
		        textStyle: {
		            color: '#fff'
		        }
		    };
		    
		    
		    option.geo = {
		        map: 'china',
		        center: [106.1650,26.45672],
		        zoom: 20,
		        label: {
		        	normal: {
                        show: true
                    },
                    emphasis: {
                        show: false
                    }
		        },
		        roam: true,
		        itemStyle: {
		            normal: {
		                'areaColor': areaColor,
		                borderWidth: 1.5,
		                borderColor : "#cccccc"
		            },
		            emphasis: {
		            	'areaColor': areaColor,
		                borderWidth: 1.5,
		                borderColor : "#cccccc"
		            },
		        }
		    };
		    option.chart.backgroundColor == "transparent" ? option.geo.itemStyle.emphasis.opacity = 10 : null;
		    return option;
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
		convertData : function (geoCoordMap,data) {
			var target = this.target,
			    that = this,
			    options = that.options,
			    rules = options.chartsSource,res = [];
		    for (var i = 0; i < data.length; i++) {
		    	var element = data[i];
		        var geoCoord = geoCoordMap[element[rules[0].xAxisName.en]];
		        if (geoCoord) {
		        	var yAxis = rules[0].yAxisName;
		        	var xAxis = rules[0].xAxisName;
		        	res.push(geoCoord.concat(parseInt(element[yAxis[0].en])));
		        }
		    }
		    return res;
		},
		convertDataByMigration : function (geoCoordMap,data) {
		    var res = [];
		    for (var i = 0; i < data.length; i++) {
		        var dataItem = data[i];
		        var fromCoord = geoCoordMap[dataItem[0].name];
		        var toCoord = geoCoordMap[dataItem[1].name];
		        if (fromCoord && toCoord) {
		            res.push({
		                fromName: dataItem[0].name,
		                toName: dataItem[1].name,
		                coords: [fromCoord, toCoord]
		            });
		        }
		    }
		    return res;
		},

		transform : function(datas) {
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
						if(rule['yAxisNameSpecial1'] != undefined){
							var serie = that._groupBySeriesSpecial(data.data, rule, options);
						}
						else{
							var serie = that._groupBySeries(data.data, rule, i, options);
						}
						
						$.merge(series, serie);
					}
				}
				return series;
			}
			return [];
		},
		_groupYAxisSpecial : function(rule){
			var arr = []
			$.each(rule,function(i,item){
				if(i.indexOf("yAxisNameSpecial") != -1){
					arr.push(item);
				}
			});
			return arr;
		},
		_groupBySeriesSpecial : function(datas,rule,opts){
			var that = this;
			var series = [],
				serie, subseries, subserie, data, datasSize = datas.length,
//				yans = rule.yAxisNameSpecial,
//				yansSize = yans.length,
				xan = rule.xAxisName,
				yAxisLength = false;
				// yAxisLength = opts.yAxis.length > 1;
			rule._chartType = opts.type;
			rule.pieInnerSize = opts.pieInnerSize;
			var arr = that._groupYAxisSpecial(rule);
			for (var i = 0; i < datasSize; i++) {
				data = datas[i];
				for (var j = 0; j < arr.length; j++) {
					var yan = arr[j];
					
					var hasSerie = that._hasYAxisName(series, xan.cn);
					if (hasSerie) {
						hasSerie.data.push(that._buildSubserieSpecial(data, rule, yan));
					} else {
					serie = {};
					
					serie.type = opts.chart.type || 'map';
					// serie.coordinateSystem= 'geo';
					if(serie.type == 'heatmap' ||  serie.type == 'scatter'){
						serie.coordinateSystem = 'geo';
					}
					
					if(serie.type != 'gauge'){
						serie.name = xan.cn;
						
					}
					
					serie = serie.type == 'gauge' ? that._setGeauElement(serie,j,data[yan.en]) : serie;	
					serie = serie.type == 'pie' ? that._setRoseElement(serie) : serie;
					that._setSeriesLabel(serie);
					that._setShowValueAndPoint(serie, rule);
					if(serie.type == 'scatter'){
						serie.symbol = "image://"+ contextPath +"/scripts/plugins/bootstrap/echarts/img/blue.png";
						serie.symbolSize = 40;
						serie.label.emphasis.show = false;
					}
					if (rule.stack) {
						serie.stack = data[rule.stack];
					}
					subseries = [];
					subserie = that._buildSubserieSpecial(data, rule, yan);
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
			return series;
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

						//serie.map= 'mapext';
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
			} 
			 else { // 动态系列
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
							
							
							//增加图例
							//that.options.legend.data.push(yan.cn);

							//serie.map= 'mapext';
							serie.type = opts.chart.type || 'map';
							// serie.coordinateSystem= 'geo';
							if(serie.type == 'heatmap' ||  serie.type == 'scatter'){
								serie.coordinateSystem = 'geo';
							}
							
							if(serie.type != 'gauge'){
								serie.name = yan.cn;
								
							}
							
							serie = serie.type == 'gauge' ? that._setGeauElement(serie,i,data[xan.en]) : serie;	
							serie = serie.type == 'pie' ? that._setRoseElement(serie) : serie;
							that._setSeriesLabel(serie);
							that._setShowValueAndPoint(serie, rule);
							if(serie.type == 'scatter'){
								serie.symbol = "image://"+ contextPath +"/scripts/plugins/bootstrap/echarts/img/blue.png";
								serie.symbolSize = 40;
								serie.label.emphasis.show = false;
							}
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
		_setRoseElement : function(serie){
			var that = this;
			serie.type = 'pie';
			serie.radius = that.options.innerSize != undefined ? [that.options.innerSize, 110] :  [30, 110];
			serie.roseType = 'radius';
			serie.label = {
                normal: {
                    show: false
                },
                emphasis: {
                    show: true
                }
            };
            serie.lableLine = {
                normal: {
                    show: false
                },
                emphasis: {
                    show: true
                }
            };
			return serie;
		},
		_setGeauElement : function(serie,x,name){
			var that = this;
			var xzhou = that.options.xzhou;
			var x = x;
			var serie = serie;
			$.each(eval(xzhou), function(i, v) {
				if (i == x) {
					serie.z = 3;
					serie.min = 0;
					serie.title = {}
					serie.title.textStyle = {};
					if(v.max != undefined && v.max != ''){
						serie.max = v.max;
					}
					if(v.spiltNumber != undefined && v.spiltNumber != ''){
						serie.splitNumber = v.spiltNumber;
					}
					
					if(v.xLie != undefined && v.xLie != ''){
						if(v.yLie != undefined && v.yLie != ''){
							serie.center = [ v.xLie + '%', v.yLie +'%' ];
						}
						else{
							serie.center = [ v.xLie +'%','50%' ];
						}
					}
					if(v.yLie != undefined && v.yLie != ''){
						if(v.xLie != undefined && v.xLie != ''){
							serie.center = [ v.xLie + '%', v.yLie +'%' ];
						}
						else{
							serie.center = [ '50%', v.yLie +'%' ];
						}
					}
					if(v.fontSize != undefined && v.fontSize != ''){
						serie.title.textStyle.fontSize = v.fontSize; 
					}
					if(v.fontWeight != undefined && v.fontWeight != ''){
						if(v.fontWeight){
							serie.title.textStyle.fontWeight = 'bolder';
						}
					}
					if(v.fontStyle != undefined && v.fontStyle != ''){
						if(v.fontStyle){
							serie.title.textStyle.fontStyle = 'italic';
						}
					}
					if(v.textColor != undefined && v.textColor != ''){				
					    serie.title.textStyle.color = v.textColor;						
					}
					if(v.radius != undefined && v.radius != ''){
						serie.radius = v.radius + '%';
					}
					if(v.floor != undefined && v.floor != ''){
						serie.startAngle = v.floor;
						
					}
					if(v.ceiling != undefined && v.ceiling != ''){
						serie.endAngle = v.ceiling;
						
					}
					
					serie.axisLine = { // 坐标轴线
						lineStyle : { // 属性lineStyle控制线条样式
							width : 10
						}
					};
					serie.axisTick = { // 坐标轴小标记
						length : 15, // 属性length控制线长
						lineStyle : { // 属性lineStyle控制线条样式
							color : 'auto'
						}
					};
					serie.splitLine = { // 分隔线
						length : 20, // 属性length控制线长
						lineStyle : { // 属性lineStyle（详见lineStyle）控制线条样式
							color : 'auto'
						}
					};
					serie.detail = {
						show : false
					};
				}
			});
		
			return serie;
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
					/*
					 * var y = parseFloat(data[p]); subserie.y =
					 * parseFloat(y.toFixed(2));
					 */
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
		//用于列传行的数据源
		_buildSubserieSpecial: function(data, rule, yan) { // 动态列
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
		    subserie.name = yan.cn;
			
			return subserie;
		},
	}
	
	
	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor : Build
	});
	

}(jQuery);





