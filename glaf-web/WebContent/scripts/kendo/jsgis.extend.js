var dojoConfig = {
	parseOnLoad: true,
	cacheBust: true,
	packages: [{
		"name": "myModules",
		"location": contextPath + "/scripts/arcgis"
	}]
};
var jsGisFunc = {
	getNode: function(rule, args) { //获取单击图形的某参数
		if (rule.param == "point") {
			return "";
		}
		return args[0].graphic.symbol.data[rule.columnName] || "";
	},
	_gallery: function(BasemapGallery, BasemapLayer, Basemap, dojo, jsgisMap, datas, position) { //多底图功能
		jsgistb = new esri.toolbars.Draw(jsgisMap);
		if (datas) {
			//right:80px; top:10px;
			var content = '<div style="position:absolute; z-Index:99;"><div data-dojo-type="dijit.TitlePane" id="dijit_TitlePane_0" data-dojo-props="title:\'切换地图\',open:false"><div data-dojo-type="dijit.layout.ContentPane" style="overflow:auto;"><div id="basemapGallery"></div></div></div></div>',
				$connect = $(content);
			if (position) {
				$connect.css(JSON.parse(position));
			}
			$("#" + jsgisMap.id).append($connect);
			var basemapGallery = new BasemapGallery({
				showArcGISBasemaps: false,
				map: jsgisMap,
			}, "basemapGallery");
			var dataJsons = JSON.parse(datas);
			$.each(dataJsons, function(i, v) {
				var layer = new BasemapLayer({
					url: v.mapUrl
				});
				var basemap = new Basemap({
					layers: [layer],
					title: v.name,
					thumbnailUrl: contextPath + v.thumbnailUrl
				});
				basemapGallery.add(basemap);
			});
			$("body").on("mouseover", "#dijit_TitlePane_0", function bbc(e) {
				$(this).find(".dijitTitlePaneContentOuter").show();
				$(this).find(".dijitReset").show();
			}).on("mouseleave", "#dijit_TitlePane_0", function bbc(e) {
				$(this).find(".dijitTitlePaneContentOuter").hide();
				$(this).find(".dijitReset").hide();
			});
			basemapGallery.startup(); // 开启  
			dojo.connect(basemapGallery, "onError", function(msg) {
				console.log(msg);
			});
		}
	},
	_convert: function(k, v, type) { //转换函数
		var length = v.length,
			o, par, val, reger, tv = (type == 'key' ? "'" : "");
		if (length) {
			for (var i = 0; i < length; i++) {
				o = v[i];
				for (var p in o) {
					var kt = p.split("_0_")[1];
					reger = new RegExp("##" + kt + "##", "gm");
					k = k.replace(reger, tv + o[p] + tv);
				}
			}
		}
		return k.replace(/\\"/gm, "'").replace(/##col\w*##/gm, type == 'key' ? "''" : "").replace(/##\w*##/gm, type == 'key' ? "''" : "");
	},
	_getParamName: function(rule, param, type) { //获取传递的参数名称
		if (type) {
			if (rule.rules) {
				var r = JSON.parse(rule.rules),
					rulek, ruleObj, par, retVal = "";
				for (var p in r) {
					rulek = r[p];
					if (rulek) {
						for (var i = 0; i < rulek.length; i++) {
							ruleObj = rulek[i];
							par = param[ruleObj['columnName']];
							if (par) {
								retVal += ruleObj['param'] + "=" + par + "&";
							}
						}
					}
				}
				return retVal;
			}
			return "";
		} else {
			for (var i = 0; i < rule.length; i++) {
				var r = rule[i];
				if (param.urlId == r.outid) {
					return r.param;
				}
			}
			return "";
		}
	},
	_jsGisDraw: function(map, params, rules) { //手动画图
		var trul = getUrlToSplitter() + contextPath + "/mx/form/page/viewPage?id=";
		//jsgistb = new esri.toolbars.Draw(map);
		dojo.connect(jsgistb, "onDrawEnd", function(geometry) {
			var symbol = "",
				point = geometry,
				type = geometry.type;
			if (type === "point" || type === "multipoint") {
				symbol = jsgistb.markerSymbol;
			} else if (type === "line" || type === "polyline") {
				point.x = geometry.paths[0][0][0];
				point.y = geometry.paths[0][0][1];
				symbol = jsgistb.lineSymbol;
			} else {
				symbol = jsgistb.fillSymbol;
			}
			map.graphics.add(new esri.Graphic(geometry, symbol));
			var gsonStr = JSON.stringify(geometry.toJson()),
				gson = {
					type: type,
					source: gsonStr
				};
			// 事件定义器bind事件
			var drawEndEvents = $("#"+map.id).data("drawEnd");
			if(drawEndEvents && drawEndEvents.length){
				$.each(drawEndEvents,function(i,drawEndEvent){
					drawEndEvent.call(null,gson);
				});
			}

			//内置画图事件弹窗
			var allParams = [];
			for (var i = 0; i < params.length; i++) {
				var param = params[i];
				if (param.type == type && param.type != "all") {
					var p = "&" + jsGisFunc._getParamName(rules, param) + "=" + JSON.stringify(gson).replace(/\\"/gm, "\\\\\"").replace(/"/gm, "\\\"");
					if (param.urlId) {
						jsGisFunc.jsGisShowInfoWindow(map, point, param.title, trul + param.urlId + p, param.width, param.height);
					}
					return;
				} else if (param.type == "all") {
					allParams.push(param);
				}
			}
			if (allParams) {
				for (var i = 0; i < allParams.length; i++) {
					var allParam = allParams[i];
					var p = "&" + jsGisFunc._getParamName(rules, allParam) + "=" + JSON.stringify(gson).replace(/\\"/gm, "\\\\\"").replace(/"/gm, "\\\"");
					if (allParam.urlId) {
						jsGisFunc.jsGisShowInfoWindow(map, point, allParam.title, trul + allParam.urlId + p, allParam.width, allParam.height);
					}
				}
			}
		});
	},
	_initType: function(data, rules) { //初始化规则（解析某条数据中有多少点）
		var ees = [];
		for (var k = 0; k < rules.length; k++) {
			var ee = rules[k];
			var key = jsGisFunc._convert(ee.exp, [data], 'key');
			if (eval(key)) {
				ees.push(ee);
			}
		}
		return ees;
	},
	_init: function(map, data, rules, coordInfo, url) { //初始化图层
		map.rules = rules;
		var hids = {},
			hidIds = {},
			pointGraphics = [];
		map.strokeGraphics || (map.strokeGraphics = []); //存储所有图形
		map.defaultGraphics || (map.defaultGraphics = []); //存储默认显示图形
		map.prevStorage || (map.prevStorage = []); //上次操作存储区
		for (var i = 0; i < data.length; i++) {
			var dat = data[i].data; //单个数据集数据
			for (var j = 0; j < dat.length; j++) {
				var da = dat[j],
					p = da[coordInfo.x]; //p
				var ees = jsGisFunc._initType(da, rules); //获取适用于当前数据的规则
				for (var k = 0; k < ees.length; k++) {
					var ee = ees[k];
					if (ee.column) {
						p = da[ee.column];
					}
					if (p) {
						try {
							var points = JSON.parse(p.replace(/\\&quot;/ig, "\"").replace(/\\\\/ig, "\\")),
								source,
								flag = points.flag ? true : false;
							//百度地图
							var symbol = null,
								geometry = null,
								isPoint = false;
							if (points instanceof Array) {
								isPoint = true;
								var point = [points[0]["Longitude"], points[0]["Latitude"]];
								symbol = new esri.symbol.PictureMarkerSymbol(contextPath + ee.icon, ee.iconWidth || 20, ee.iconHeight || 20);
								geometry = new esri.geometry.Point(point);
							} else if(points instanceof Object && points.Latitude && points.Longitude){
								isPoint = true;
								var point = [points["Longitude"], points["Latitude"]];
								symbol = new esri.symbol.PictureMarkerSymbol(contextPath + ee.icon, ee.iconWidth || 20, ee.iconHeight || 20);
								geometry = new esri.geometry.Point(point);
							} else {
								// arcgis 装载点
								source = JSON.parse(points.source);
								if (points.type == "point") {
									isPoint = true;
									symbol = new esri.symbol.PictureMarkerSymbol(contextPath + ee.icon, ee.iconWidth || 20, ee.iconHeight || 20);
									geometry = new esri.geometry.Point(source);

								} else if (points.type == "polyline") {
									symbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new esri.Color(ee.lineColor || ([255, 0, 0])), ee.lineWidth || 2);
									geometry = new esri.geometry.Polyline(source);
								} else if (points.type == "polygon") {
									var linesymbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new esri.Color(ee.lineColor || ([255, 0, 0])), ee.lineWidth || 2);
									symbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID, linesymbol, new esri.Color(ee.lineColor || ([255, 0, 0])));
									geometry = new esri.geometry.Polygon(source);
								}
								if (flag) {
									geometry = esri.geometry.geographicToWebMercator(geometry);
								}
							}
							symbol.data = da;
							symbol.rule = ee;
							symbol.rules = rules;
							symbol.column = ee.column;
							var dd = new esri.Graphic(geometry, symbol);
							/*if (ee.isShow == "true") {} else {
								dd.visible = false;
							}*/
							if (ee.isShow == "true") {
								if (isPoint) {
									pointGraphics.push(dd);
								} else {
									map.defaultGraphics.push(dd);
								}
							} else {
								dd.visible = false;
							}
							//map.graphics.add(dd);
							map.strokeGraphics.push(dd);
						} catch (ex) {
							if (p) { // 根据规则 解析点数据对象
								var pAry = p.split(",");
								$.each(pAry, function(index, pVal) {
									var oo = {};
									var psplit = pVal.split("-");
									if(psplit.length == 2){
										oo.id = psplit[1];
										hidIds[psplit[0]] || (hidIds[psplit[0]] = []);
										if ($.inArray(oo.id, hidIds[psplit[0]]) == -1) {
											hidIds[psplit[0]].push(oo.id);
										}
										oo.layerId = psplit[0];
										oo.data = da;
										oo.rule = ee;
										//oo.rules = ees;
										oo.column = ee.column;
										(hids[psplit[0]] || (hids[psplit[0]] = {})) && ((hids[psplit[0]][oo.id]) || (hids[psplit[0]][oo.id] = []));
										hids[psplit[0]][oo.id].push(oo);
									}
									
								});
							}
						}
					}
				}
			}
		};

		if (!$.isEmptyObject(hids)) {
			var queryTask, query = new esri.tasks.Query();
			query.outFields = ["*"];
			query.returnGeometry = true;
			//query.multipatchOption = "xyFootprint";
			var layerIdLength = Object.getOwnPropertyNames(hidIds).length,
				j = 0;
			for (var layerId in hidIds) {
				queryTask = new esri.tasks.QueryTask(url + "/" + layerId);
				queryTask.layerId = layerId;
				//query.objectIds = hidIds[layerId]; 
				var fids = hidIds[layerId].join(",");
				if(fids.length){
					query.where = ' fid in (' + hidIds[layerId].join(",") + ') ';
					queryTask.on("complete", function(r) {
						j++;
						var datas = hids[r.target.layerId];
						$.each(r.featureSet.features, function(index, grap) {
							$.each(datas[grap.attributes.FID], function(i, data) {
								var graphic = $.extend({}, grap);
								if (data.id == graphic.attributes.FID) {
									var ptype = graphic.geometry.type,
										ee = data.rule;
									isPoint = false, isPolygon = false;
									if (ptype == "point") {
										isPoint = true;
										symbol = new esri.symbol.PictureMarkerSymbol(contextPath + ee.icon, ee.iconWidth || 20, ee.iconHeight || 20);
									} else if (ptype == "polyline") {
										//fill-opacity
										symbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new esri.Color(ee.lineColor || ([255, 0, 0])), ee.lineWidth || 2);
										//symbol.setStyle({});
									} else if (ptype == "polygon") {
										isPolygon = true;
										var linesymbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new esri.Color(ee.lineColor || ([255, 0, 0])), ee.lineWidth || 2);
										symbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID, linesymbol, new esri.Color(ee.lineColor || ([255, 0, 0])));
									}
									var toffset = ee.toffset;
									if (toffset && isPoint) {
										try {
											var toffset = toffset.split(",");
											symbol.setOffset(parseFloat(toffset[0]), parseFloat(toffset[1]));
										} catch (e) {
											console.log(e);
										}
									}
									symbol.id = data.id;
									symbol.data = data.data;
									symbol.rule = ee;
									symbol.column = data.column;
									symbol.layerId = data.layerId;
									graphic.setSymbol(symbol);
									if (ee.isShow == "true") {
										//graphic.visible = true;
										if (isPoint) {
											pointGraphics.push(graphic);
										} else if (isPolygon) {
											map.graphics.add(graphic);
										} else {
											map.defaultGraphics.push(graphic);
										}
									} else {
										graphic.visible = false;
									}
									/*if (isPoint) {
										graphicsPoints.push(graphic);
									} else {*/
									map.strokeGraphics.push(graphic);
									map.prevStorage.push(graphic);
									//map.graphics.add(graphic);
									/*}*/
								}
							});
						});
						if (j == layerIdLength) {
							$.merge(map.defaultGraphics, pointGraphics);
							$.each(map.defaultGraphics, function(index, grap) {
								map.graphics.add(grap);
								map.workingStorage.push(grap);
								var graphic = $.extend({}, grap);
								var symbol = graphic.symbol,
									ee = symbol.rule;
								if (ee && ee.isShow == "true" && (ee.isSort == "true" || ee.showColumn) && grap.geometry.type == "point") {
									var text = new jsgisMap.esri.symbol.TextSymbol(ee.isSort == "true" ? map.count++ : (symbol.data[ee.showColumn] || ""));
									text.rule = symbol.rule;
									text.data = symbol.data;
									text.column = symbol.column;
									text.url = text.url;
									if (ee.sortSize) {
										var font = new map.esri.symbol.Font();
										font.setSize(ee.sortSize);
										text.setFont(font);
									}
									if (ee.sortColor) {
										var color = new map.esri.Color(ee.sortColor);
										text.setColor(color);
									}
									var sortOffset = ee.sortOffset;
									if (sortOffset) {
										try {
											var offsets = sortOffset.split(",");
											text.setOffset(parseFloat(offsets[0]), parseFloat(offsets[1]));
										} catch (e) {
											console.log(e);
										}
									}
									var graphic = new map.esri.Graphic(graphic.geometry, text);
									graphic.visible = true;
									graphic.isText = true;
									map.graphics.add(graphic);
									map.workingStorage.push(graphic);
									map.prevStorage.push(graphic);
								}
							});
							//加载完毕执行事件
							if (mapCb) {
								mapCb.fire();
							}
						}
					});
					queryTask.execute(query);
				}
				
			}
		} else {
			$.merge(map.defaultGraphics, pointGraphics);
			$.each(map.defaultGraphics, function(index, grap) {
				map.graphics.add(grap);
				map.workingStorage.push(grap);
				var graphic = $.extend({}, grap);
				var symbol = graphic.symbol,
					ee = symbol.rule;
				if (ee && ee.isShow == "true" && (ee.isSort == "true" || ee.showColumn) && grap.geometry.type == "point") {
					var text = new map.esri.symbol.TextSymbol(ee.isSort == "true" ? map.count++ : (symbol.data[ee.showColumn] || ""));
					text.rule = symbol.rule;
					text.data = symbol.data;
					text.column = symbol.column;
					text.url = text.url;
					if (ee.sortSize) {
						var font = new map.esri.symbol.Font();
						font.setSize(ee.sortSize);
						text.setFont(font);
					}
					if (ee.sortColor) {
						var color = new map.esri.Color(ee.sortColor);
						text.setColor(color);
					}
					var sortOffset = ee.sortOffset;
					if (sortOffset) {
						try {
							var offsets = sortOffset.split(",");
							text.setOffset(parseFloat(offsets[0]), parseFloat(offsets[1]));
						} catch (e) {
							console.log(e);
						}
					}
					var graphic = new map.esri.Graphic(graphic.geometry, text);
					graphic.visible = true;
					graphic.isText = true;
					map.graphics.add(graphic);
					map.workingStorage.push(graphic);
					map.prevStorage.push(graphic);
				}
			});
			if (mapCb) {
				mapCb.fire();
			}
		}
	},
	jsGisShowInfoWindow: function(map, geometry, title, url, width, height) {
		map.infoWindow.resize(width.replace(/px||%/gm, ""), parseInt(height.replace(/px||%/gm, "")) + 10);
		map.infoWindow.setTitle(title);
		map.infoWindow.setContent("<iframe frameborder='no' border='0' marginwidth='0' marginheight='0' src='" + url + "' style='width:100%;height:" + height + ";'></iframe>");
		map.infoWindow.show(geometry, map.getInfoWindowAnchor(geometry));
		var obj = {};
		obj.width = width.replace(/px||%/gm, "");
		obj.height = height.replace(/px||%/gm, "");
		return obj;
	},
	getCoord: function(rule, args) { //获取当前坐标信息
		if(args && args.length==1){
			return JSON.stringify(args[0]);
		}else{
			jsGisFunc._jsGisDraw(args[0], args[1], args[2]);
		}
		return "";
	},
	getCoordType: function(rule, args){
		if(args && args.length==1){
			return args[0]["type"];
		}else{
			return "";
		}
	},
	clickEvent: function(map, e, clickEvent) { //注册点击事件
		var symbol = e.symbol || e.graphic.symbol,
			symdata = symbol.data,
			objs = [],
			ee = symbol.rule,
			key = jsGisFunc._convert(ee.exp, [symdata], 'key');
		if (eval(key) && symbol.column == ee.column) {
			if (ee.urlId) {
				var trul = getUrlToSplitter() + contextPath + "/mx/form/page/viewPage?id=" + ee.urlId + "&" + jsGisFunc._getParamName(ee, symdata, 'c');
				var point = clickEvent;
				if (!clickEvent) {
					point = e.screenPoint || e.geometry;
					if (point.type === "line" || point.type === "polyline") {
						point.x = point.paths[0][0][0];
						point.y = point.paths[0][0][1];
					}
				}
				var obj = jsGisFunc.jsGisShowInfoWindow(map, point, ee.windowName, trul, ee.windowWidth, ee.windowHeight);
				objs.push(obj);
			}
		}
		return objs;
	},
	_bindEventType: function($id, fnType, e) {
		var ary = $id.data(fnType);
		if (ary && ary.length) {
			$.each(ary, function(i, v) {
				v(e);
			})
		}
	},
	_bindEvent: function(map) {
		map.graphics.on("click", function(e) {
			jsGisFunc._bindEventType($("#" + map.id), "clickEventList", e);

			/*var win = jsGisFunc.clickEvent(map, e, eee),
				width = win[0] ? win[0].width : 0,
				height = win[0] ? win[0].height : 0,
				screen = map.toScreen(e.mapPoint); //当前点的坐标位置
			var offset = e.graphic.symbol.rule.offset || 0;
			screen.x = screen.x - (map.width / 2 - width - 80) + offset;
			screen.y = screen.y + (map.height / 2 - height - 80);
			map.centerAt(map.toMap(screen));*/

		});
		map.graphics.on("dbl-click", function(e) {
			jsGisFunc._bindEventType($("#" + map.id), "dbclickList", e);
		});
		map.graphics.on("mouse-over", function(e) {
			jsGisFunc._bindEventType($("#" + map.id), "mouseoverList", e);
		});
		map.graphics.on("mouse-up", function(e) {
			jsGisFunc._bindEventType($("#" + map.id), "mouseupList", e);
		});



	},
	initDataAndClick: function(map, eee, rid, coordInfo, url) {
		$.ajax({
			type: "POST",
			url: contextPath + "/mx/form/charts/datas",
			contentType: "application/json",
			dataType: "json",
			data: JSON.stringify({
				rid: rid
			}),
			success: function(data) {
				/*map.graphics.on("click", function(e) {
					var win = jsGisFunc.clickEvent(map, e, eee),
						width = win[0] ? win[0].width : 0,
						height = win[0] ? win[0].height : 0,
						screen = map.toScreen(e.mapPoint); //当前点的坐标位置
					var offset = e.graphic.symbol.rule.offset || 0;
					screen.x = screen.x - (map.width / 2 - width - 80) + offset;
					screen.y = screen.y + (map.height / 2 - height - 80);
					map.centerAt(map.toMap(screen));
				});*/
				jsGisFunc._bindEvent(map);
				jsGisFunc._init(map, data, eee, coordInfo, url);
				/*setTimeout(function() {
					if (mapCb) { //初始化 地图完毕后 执行事件
						mapCb.fire();
					}
				}, 500);*/
			},
			error: function(msg) {
				alert("数据获取错误");
			}
		});
	},
	_clearGis: function(workingStorage, jsgisMap, remit) { //清除上一次存储
		if (remit) {
			return;
		}
		//隐藏弹出窗口
		if (jsgisMap.infoWindow && jsgisMap.infoWindow.isShowing) {
			jsgisMap.infoWindow.hide();
		}
		if (workingStorage.length > 0) {
			clearInterval(jsgisMap.timer); //清楚闪烁定时任务
			for (var k = 0; k < workingStorage.length; k++) {
				var storageGraphics = workingStorage[k];
				var symbol = storageGraphics.symbol,
					rule = symbol.rule;
				if (rule && "true" != rule.isShow) { //初始化隐藏的 则移除
					//storageGraphics.hide();
					//jsgisMap.graphics.remove(storageGraphics);	
				}
				rule || (rule = {});
				jsgisMap.graphics.remove(storageGraphics);
				var ptype = storageGraphics.geometry.type;

				if ("textsymbol" != symbol.type && (ptype == "line" || ptype == "polyline")) { // 如果是线条  还原回原来样式
					storageGraphics.symbol.color = new jsgisMap.BColor(rule.lineColor || ([255, 0, 0]));
					storageGraphics.symbol.width = rule.lineWidth;
					storageGraphics.draw();

					if (storageGraphics.isFlicker) {
						storageGraphics.isFlicker = false;
					}
				} else if ("textsymbol" == symbol.type) {
					//jsgisMap.graphics.remove(storageGraphics);
				}
			}
			workingStorage.length = 0;
		}
	},
	isExtis: function(symbol, r, pms, pmsAry) {
		return (symbol) && r.columnName && (symbol.column == r.columnName) && (symbol.data[r.columnName].split(",").length > 1 ? pms.indexOf(symbol.data[r.columnName]) != -1 : $.inArray(symbol.data[r.columnName], pmsAry) != -1);
	},
	getKeyName : function(rule,args){
		return rule.columnName;
	},
	_eventFunc: function(rule, params, args, callback, isClear) {
		var jsgisMap = jsGisFunc.getMap(rule),
			$id = pubsub.getJQObj(rule),
			workingStorage = jsgisMap.workingStorage,
			prevStorage = jsgisMap.prevStorage = [];
		if (!isClear) {
			jsGisFunc._clearGis(workingStorage, jsgisMap, params.remit);
		}
		var syrule, graphics = jsgisMap.strokeGraphics,
			pointGraphics = [] /*jsgisMap.graphics.graphics*/ ,
			_col = params["_col"],
			_col_p = params["_col_p"],
			colName = params["keyName"];
		for (var i = 0, il = graphics.length; i < il; i++) { // 获取定位的Graphic
			var currentGraphic = graphics[i],
				symbol = currentGraphic.symbol;
			for (var p in params) {
				// zoom放大  point 定位  remit 豁免   keyName 列名 
				if (p != "zoom" && p != "point" && p != "remit" && p != "_col" && p != "_col_p" && p != 'keyName') {
					var r = $.extend(true,{},jsGisFunc.getRule(rule, p)),
						pms = params[p],
						pmsAry = (pms + "").split(",");
					if(!pms){
						continue;
					}
					try{
						//是否为经纬度坐标 [{"Latitude":29.7181756621,"Longitude":115.9133045858}]
						var pmsJSONStr = (pms+"").replace(/\\&quot;/ig, "\"").replace(/\\\\/ig, "\\")
						// var isLat = $.parseJSON(pms);
						try{
							pmsAry = $.parseJSON(pmsJSONStr);
						}catch(e1){
							pmsAry = $.parseJSON("["+pmsJSONStr+"]")
						}
						// pmsAry = [pms];
					}catch(e){

					}
					r.columnName = symbol.column;
					if (jsGisFunc.isExtis(symbol, r, pms, pmsAry) && (!(_col && _col_p) || (_col && _col_p && (symbol["data"][_col] == _col_p)))) {
						//console.log(currentGraphic);
						workingStorage.push(currentGraphic);
						prevStorage.push(currentGraphic);
						if (currentGraphic.geometry.type == "point") {
							pointGraphics.push(currentGraphic);
						} else {
							jsgisMap.graphics.add(currentGraphic);
						}
						callback.call(this, currentGraphic, jsgisMap, symbol, args, pointGraphics);
					}
				}
			}
		}
		if (pointGraphics && pointGraphics.length) {
			$.each(pointGraphics, function(idex, currGrap) {
				jsgisMap.graphics.add(currGrap);
			})
		}
	},
	_flicker: function(jsgisMap) { //闪烁方法
		var workingStorage = jsgisMap.workingStorage;
		if (workingStorage.length > 0) {
			jsgisMap.timer = setInterval(function() {
				for (var k = 0; k < workingStorage.length; k++) {
					if (!workingStorage[k].isText && workingStorage[k].isFlicker) {
						if (workingStorage[k].visible) {
							workingStorage[k].hide();
						} else {
							workingStorage[k].show();
						}
					}
				}
			}, jsgisMap.flicker || 500);
		}
	},
	highlightingGis: function(rule, params, agrs) { //加粗 高亮显示 只适用于线
		jsGisFunc._eventFunc(rule, params, agrs, function(currentGraphic, jsgisMap, symbol) {
			var ptype = currentGraphic.geometry.type;
			if (ptype == "line" || ptype == "polyline") {
				syrule = currentGraphic.symbol.rule;
				currentGraphic.symbol.color = new jsgisMap.BColor(syrule.highlightColor || ([255, 0, 0]));
				currentGraphic.symbol.width = syrule.highlightWidth;
				currentGraphic.draw();
			}
			currentGraphic.show();
			//jsgisMap.workingStorage.push(currentGraphic);
		});
	},
	flickerGis: function(rule, params, agrs) { //闪烁
		var jsgisMap = jsGisFunc.getMap(rule);
		jsGisFunc._eventFunc(rule, params, agrs, function(currentGraphic, jsgisMap, symbol) {
			currentGraphic.show();
			currentGraphic.isFlicker = true;
			//jsgisMap.workingStorage.push(currentGraphic);
		});
		jsGisFunc._flicker(jsgisMap);
	},
	flickerAndHighlightingGis: function(rule, params, agrs) { //闪烁加粗
		var jsgisMap = jsGisFunc.getMap(rule);
		jsGisFunc._eventFunc(rule, params, agrs, function(currentGraphic, jsgisMap, symbol) {
			var ptype = currentGraphic.geometry.type;
			if (ptype == "line" || ptype == "polyline") {
				syrule = currentGraphic.symbol.rule;
				currentGraphic.symbol.color = new jsgisMap.BColor(syrule.highlightColor || ([255, 0, 0]));
				currentGraphic.symbol.width = syrule.highlightWidth;
				currentGraphic.draw();
			}
			currentGraphic.isFlicker = true;
			currentGraphic.show();
			//jsgisMap.workingStorage.push(currentGraphic);
		})
		jsGisFunc._flicker(jsgisMap);
	},
	showAndHideLastGis: function(rule, params, agrs) { //显示隐藏上一个图标
		$.each(jsgisMap.prevStorage, function(i, storageGraphics) {
			var symbol = storageGraphics.symbol,
				rule = symbol.rule;
			jsgisMap.graphics.remove(storageGraphics);
			jsgisMap.strokeGraphics.removeObj(storageGraphics);
			var ptype = storageGraphics.geometry.type;
			if ("textsymbol" != symbol.type && (ptype == "line" || ptype == "polyline")) { // 如果是线条  还原回原来样式
				storageGraphics.symbol.color = new jsgisMap.BColor(rule.lineColor || ([255, 0, 0]));
				storageGraphics.symbol.width = rule.lineWidth;
				storageGraphics.draw();
				if (storageGraphics.isFlicker) {
					storageGraphics.isFlicker = false;
				}
			}
		});
		jsgisMap.prevStorage.length = 0;
		jsGisFunc._eventFunc(rule, params, agrs, function(currentGraphic, jsgisMap, symbol) {
			if (currentGraphic.visible) {
				currentGraphic.hide();
			} else {
				currentGraphic.show();
				//jsgisMap.workingStorage.push(currentGraphic);
			}
		}, true);
	},
	showAndHideGis: function(rule, params, agrs) { //显示隐藏图标
		jsGisFunc._eventFunc(rule, params, agrs, function(currentGraphic, jsgisMap, symbol) {
			if (currentGraphic.visible) {
				currentGraphic.isFlicker = false;
				currentGraphic.hide();
			} else {
				currentGraphic.show();
				//jsgisMap.workingStorage.push(currentGraphic);
				//	var taxLotExtent = selectedTaxLot.geometry.getExtent();
				//	jsgisMap.setExtent(taxLotExtent);// 定位到选定Graphic
			}
		}, true);
	},
	hideGis: function(rule, params, agrs) { //隐藏图标
		jsGisFunc._eventFunc(rule, params, agrs, function(currentGraphic, jsgisMap, symbol) {
			if (currentGraphic.visible) {
				currentGraphic.isFlicker = false;
				currentGraphic.hide();
			}
		}, true);
	},
	locateAndShowWindow: function(rule, params, agrs) { //弹出窗口
		var $id = pubsub.getJQObj(rule),
			time = $id.data("isZoom") ? 1000 : 0;
		setTimeout(function() {
			jsGisFunc._eventFunc(rule, params, agrs, function(currentGraphic, jsgisMap, symbol, args) {
				var clickPoint = agrs && agrs.length > 1 && agrs[1] ? agrs[0].mapPoint : "";
				if (currentGraphic.visible == false) {
					currentGraphic.show();
				}
				//TODO 如果使用了放大则默认使用原来的点信息
				var objs = jsGisFunc.clickEvent(jsgisMap, currentGraphic, $id.data("isZoom") ? currentGraphic.geometry : clickPoint);
				var point = clickPoint;
				if (!clickPoint) {
					point = currentGraphic.geometry;
					if (point.type === "line" || point.type === "polyline") {
						point.x = point.paths[0][0][0];
						point.y = point.paths[0][0][1];
					}
				}
				//jsgisMap.centerAt(point);
				var screen = jsgisMap.toScreen(point),
					width = objs[0] ? objs[0].width : 0,
					height = objs[0] ? objs[0].height : 0,
					offset = symbol.rule.offset || 0;
				screen.x = screen.x - ($id.width() / 2 - width - 80) + offset;
				screen.y = screen.y + ($id.height() / 2 - height - 80);
				jsgisMap.centerAt(jsgisMap.toMap(screen));
			}, true);
			$id.data("isZoom", false);
		}, time);

	},
	showAndHideOtherGis: function(rule, params, agrs) { //显示自己并隐藏其他
		$id = pubsub.getJQObj(rule);
		var jsgisMap = jsGisFunc.getMap(rule),
			graphics = jsgisMap.graphics.graphics;
		var workingStorage = jsgisMap.workingStorage;
		jsGisFunc._clearGis(workingStorage, jsgisMap, params.remit);
		for (var i = 0, il = graphics.length; i < il; i++) { // 获取定位的Graphic
			var currentGraphic = graphics[i];
			currentGraphic.hide();
		}
		jsgisMap.count = 1;
		jsGisFunc._eventFunc(rule, params, agrs, function(currentGraphic, jsgisMap, symbol, args, pointGraphics) {
			var krule = symbol.rule;
			// 显示图标序号
			var ptype = currentGraphic.geometry.type;
			currentGraphic.show();
			if (krule && (krule.isSort == "true" || krule.showColumn) && ptype != "line" && ptype != "polyline") {
				var text = new jsgisMap.esri.symbol.TextSymbol(krule.isSort == "true" ? jsgisMap.count++ : (symbol.data[krule.showColumn] || ""));
				text.rule = symbol.rule;
				text.rules = symbol.rules;
				text.data = symbol.data;
				text.column = symbol.column;
				text.url = text.url;
				if (krule.sortSize) {
					var font = new jsgisMap.esri.symbol.Font();
					font.setSize(krule.sortSize);
					text.setFont(font);
				}
				if (krule.sortColor) {
					var color = new jsgisMap.esri.Color(krule.sortColor);
					text.setColor(color);
				}
				var sortOffset = krule.sortOffset;
				if (sortOffset) {
					try {
						var offsets = sortOffset.split(",");
						text.setOffset(parseFloat(offsets[0]), parseFloat(offsets[1]));
					} catch (e) {
						console.log(e);
					}
				}
				var graphic = new jsgisMap.esri.Graphic(currentGraphic.geometry, text);
				graphic.visible = true;
				graphic.isText = true;
				//jsgisMap.graphics.add(graphic);
				pointGraphics.push(graphic);
				jsgisMap.workingStorage.push(graphic);
				jsgisMap.prevStorage.push(graphic);
			}
		});
	},
	zoomGis: function(rule, params, agrs) { //放大地图
		$id = pubsub.getJQObj(rule);
		$id.data("isZoom", true);
		var jsgisMap = jsGisFunc.getMap(rule),
			zoom = params["zoom"] ? params["zoom"] : 8,
			colName = params["keyName"];
		for (var p in params) {
			if (p != "zoom" && p != "point") {
				var r = $.extend(true,{},jsGisFunc.getRule(rule, p)),
					pms = params[p],
					pmsAry = (pms + "").split(",");
				// colName && (r.columnName = colName);
				for (var i = 0, il = jsgisMap.graphics.graphics.length; i < il; i++) { // 获取定位的Graphic
					var currentGraphic = jsgisMap.graphics.graphics[i],
						symbol = currentGraphic.symbol;
					r.columnName = symbol.column;
					if ((symbol) && (symbol.column == r.columnName) && (symbol.data[r.columnName].split(",").length > 1 ? pms.indexOf(symbol.data[r.columnName]) != -1 : $.inArray(symbol.data[r.columnName], pmsAry) != -1)) {
						var point = currentGraphic.geometry;
						if (point.type === "line" || point.type === "polyline") {
							var extentCenter = point.getExtent().getCenter();
							point.x = extentCenter.x;
							point.y = extentCenter.y;
							//point.x = point.paths[0][0][0];
							//point.y = point.paths[0][0][1];
						}
						jsgisMap.centerAt(point);
						setTimeout(function() {
							jsgisMap.setZoom(zoom);
						}, 300);
					}
				}
			}
		}
	},
	previewGis: function(rule, params, agrs) { //预览
		var map = jsGisFunc.getMap(rule);
		var findTask = new esri.tasks.FindTask(map.jsgisUrl);
		var findParams = new esri.tasks.FindParameters();
		findParams.contains = false;
		findParams.returnGeometry = true;
		map.graphics.clear()
		for (var p in params) {
			var layerSerch = params[p].split("-");
			if (layerSerch.length == 2) {
				findParams.layerIds = [parseInt(layerSerch[0])];
				findParams.searchFields = ["FID"];
				findParams.searchText = parseInt(layerSerch[1]); // 获取查询条件
				findTask.execute(findParams, function(results) {
					var symbol, ptype, res, graphic;
					for (var i = 0, il = results.length; i < il; i++) {
						res = results[i];
						graphic = res.feature; // 以下为高亮显示查询结果
						ptype = graphic.geometry.type;
						if (ptype == "point") {
							symbol = new esri.symbol.SimpleMarkerSymbol();
							symbol.setColor(new esri.Color([255, 0, 0, 0.5]));
						} else if (ptype == "polyline") {
							symbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new esri.Color([255, 0, 0]), 2);
						}
						graphic.setSymbol(symbol);
						graphic.visible = true;
						map.graphics.add(graphic);
					}
				});
			}
		}
	},
	initExtent: function(rule, params) { //还原初始化位置
		var jsgisMap = jsGisFunc.getMap(rule);
		jsgisMap.setExtent(jsgisMap.Extent(jsgisMap.initExtent));
	},
	hideWindow: function(rule, params) { //隐藏弹出窗口
		var jsgisMap = jsGisFunc.getMap(rule);
		jsgisMap.infoWindow.hide();
	},
	getRule: function(rule, param) {
		for (var i = 0; i < rule.length; i++) {
			var r = rule[i];
			if (r.param == param) {
				return r;
			}
		}
	},
	getMap: function(rs, isIn) {
		if (typeof rs == "object") {
			var rules = [];
			if ($.isArray(rs)) {
				rules = rs;
			} else {
				rules.push(rs);
			}
			var that = pubsub.getThat(rules, isIn);
			var rule = rules[0],
				id = isIn ? rule.inid : rule.outid;
			if (that) {
				return that.eval(id + "Map");
			}
		}
		return eval(rs + "Map");
	},
	//显示轨迹
	showTrack: function(rule, params) {
		var jsgisMap = jsGisFunc.getMap(rule),
			workingStorage = jsgisMap.workingStorage,
			symbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new esri.Color([255, 0, 0]), 4),
			path = params[rule[0].param];
		jsGisFunc._clearGis(workingStorage, jsgisMap, params.remit);
		if (path && (path = JSON.parse(path))) {
			var geometry = new esri.geometry.Polyline(path),
				graphic = new esri.Graphic(geometry, symbol),
				extentCenter = graphic.geometry.getExtent().getCenter();
			jsgisMap.graphics.add(graphic);
			graphic.isFlicker = true;
			workingStorage.push(graphic);
			jsgisMap.centerAt(new esri.geometry.Point([extentCenter.x, extentCenter.y]));
		}
		jsGisFunc._flicker(jsgisMap);
	}
};
pubsub.sub("jsgis", jsGisFunc);