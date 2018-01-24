<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.core.config.SystemConfig"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<%=request.getContextPath()%>/scripts/kendoui/styles/kendo.common-material.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/scripts/kendoui/styles/kendo.material.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/scripts/kendoui/styles/kendo.common.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/scripts/kendoui/styles/kendo.rtl.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/scripts/kendoui/styles/blueopal.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/scripts/kendoui/styles/kendo.dataviz.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/scripts/kendoui/styles/kendo.dataviz.blueopal.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/scripts/kendoui/styles/kendo.mobile.all.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/scripts/layer/skin/layer.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/themes/default/styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/css/icons.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/css/kendoui.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/defineKendoui.css">
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/kendoui/js/angular.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/kendoui/js/kendo.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/kendoui/js/jszip.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/kendoui/js/cultures/kendo.culture.zh-CN.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/kendoui/js/messages/kendo.messages.zh-CN.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/highcharts/highcharts.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/highcharts/highcharts-3d.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/highcharts/highcharts-more.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/highcharts/modules/funnel.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/highcharts/modules/exporting.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/highcharts/themes/default.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/layer/layer.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/inc/globaljs.jsp"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/Silverlight.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ueditor/ueditor.all.js"></script>
<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/defineKendoui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/webfile/js/jquery.editor.extends.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/arcgis/js/esri/css/esri.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/arcgis/init.js"></script>
<script>
	var murl = '<%=SystemConfig.getString("gisServiceUrl")%>',isDynamic = "${isDynamic}" ;
	var myGridDataSource = [] ,pageParameters= "",targetId = "${param.targetId}" ,fn = "${param.fn}";

	var jsgisLayers = buildSource("jsgisLayer");
	jsgisLayers.read();
	var _layers = {} ;
	jsgisLayers.fetch(function() {
		var datas = jsgisLayers.data();
		$.each(datas,function(i,data){
			_layers[data.code] = data.value.split(",") ;
		})
	});


	
	function buildSource(type,urlType) {
		if(urlType){
			urlType = "getDictByCodeOld" ;
		}else{
			urlType = "getDictByCode" ;
		}
		return new kendo.data.DataSource({//编辑器数据源
			type : "json",
			transport : {
				read : {
					//contentType : "application/json",
					url : "${pageContext.request.contextPath}/mx/form/defined/"+urlType+"?code=" + type,
					type : "POST",
					dataType : "JSON"
				},
				parameterMap : function(options, operation) {
					if (operation == "read") {
						return options;
					}
				}
			}
		});
	}
	
	$(document).ready(function() {
		$('#splitter_1').kendoSplitter({
			orientation : 'horizontal',
			panes : [ {
				scrollable : false,
				resizable : false,
			}, {
				collapsible : false,
				scrollable : false,
				min : '300px',
				max : '520px',
				size : '28%',
			}, ]
		});
		$('#splitter').kendoSplitter({
			orientation : 'vertical',
			panes : [ {
				scrollable : false,
				resizable : false,
				size : '26px',
			}, {
				scrollable : false,
				resizable : false,
				size : '',
			}, ]
		});
		
		$("#mutliSelect").on("click",function(){
			if(targetId){

				var widget = $("#mygrid").data("kendoGrid"),dataAry = [],valAry=[];
				var rows = widget.select();
				for (var i = 0; i < rows.length; i++) {
					var data = widget.dataItem(rows[i]);
					dataAry.push(data);
					valAry.push(data.layerId+"-"+data.FID);
				}
				var p = window.opener || window.parent ;
				var tagObj = p.$('#'+targetId) ;
				var dbrule = tagObj.attr('dbrule');
				if(dbrule && dbrule!=''){
					var datas = {};
					datas.data = dataAry ;
					datas.rule = dbrule ;
					p[fn](datas);
				}else{
					tagObj.val(valAry.join(","));
				}
				window.close();
			}else{
				alert('错误');
			}
		})
		//dropdownlist
		$("#layer").kendoDropDownList({
             dataTextField: "name",
             dataValueField: "code",
            /* optionLabel: {
                 name: "全部",
                 code: ""
             },*/
             change: function(e) {
             	/*
			    var value = this.value();
			    var queryTask = new esri.tasks.QueryTask(murl+"/"+value),query = new esri.tasks.Query() ;
					query.outFields = ["road"];
					query.returnGeometry = false;
					query.returnDistinctValues = true;
					
					queryTask.layerId = layer ;
					queryTask.on("complete", function(r){
						var results = r.featureSet.features;
						console.log(results);
					});
					query.where = " 1=1 "
					queryTask.execute(query);
				*/
			 },
             dataSource: buildSource('jsgisLayer'),
         });

		//项目
		$("#road").kendoDropDownList({
             dataTextField: "name",
             dataValueField: "code",
             /*optionLabel: {
                 name: "全部",
                 code: ""
             },*/
             dataSource: buildSource('gis_road',true),
         });

		//标段
		$("#biaoduan").kendoDropDownList({
             dataTextField: "name",
             dataValueField: "code",
             optionLabel: {
                 name: "全部",
                 code: ""
             },
             dataSource: buildSource('gis_biaoduan',true),
         });

		
		//初始化图片链接grid
		$("#mygrid").kendoGrid({
			dataSource : myGridDataSource,
			sortable : true,
			selectable : "rows",
			scrollable : true,
			selectable :"multiple, row",
			columns : [{
				field : 'biaoduan',
				title : '标段',
				width: "80px" ,
			}, {
				field : 'name',
				title : '名称',
				minScreenWidth: 1024,
				width: "100px" ,
				template : function(dataItem) {
				      return dataItem.name || dataItem.NAME || "";
			    }
			}, {
				field : 'road',
				title : '道路',
				minScreenWidth: 1024,
				width: "80px" 
			}, {
				field : 'place',
				title : '位置',
				width: "80px" 
			}, {
				width: "150px" ,
				command : [{
					name : "查看",
					click : function(e) {
						var tr = $(e.target).closest("tr");
						var data = this.dataItem(tr);
						var myid = data.FID;
						if (selectedTaxLot != null){// 判断是否有要定位Graphic
							selectedTaxLot.hide();
//							selectedTaxLot.setSymbol(symbol);
						} 
						map.graphics.clear();
						// 如果有将其symbol设为统一风格
						for (var i = 0, il = map.storeGraphics.length; i < il; i++) {// 获取定位的Graphic
							var currentGraphic = map.storeGraphics[i];
							if ((currentGraphic.attributes) && currentGraphic.attributes.FID == myid) {
								selectedTaxLot = currentGraphic;
								break;
							}
						}
						if(!selectedTaxLot.visible){
							selectedTaxLot.show();
						}
						//填充
						var point = selectedTaxLot.geometry ;
						if(point.type === "line" || point.type === "polyline"){
							point.x = point.paths[0][0][0] ;
							point.y = point.paths[0][0][1] ;
						}
						map.graphics.add(selectedTaxLot);
						map.setExtent(selectedTaxLot._extent);
						//map.setZoom(16);
						//map.centerAt(point);
					/* 	var highlightSymbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID, 
								new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,new dojo.Color([ 255, 0, 0 ]), 3),
								new dojo.Color([ 125, 125, 125, 0.35 ]));
						selectedTaxLot.setSymbol(highlightSymbol); */// 设置定位图元的symbol
						/* var taxLotExtent = selectedTaxLot.geometry.getExtent();
						map.setExtent(taxLotExtent); */// 定位到选定Graphic
					}
				},{
					name : "选择",
					click : function(e) {
						var tr = $(e.target).closest("tr");
						var data = this.dataItem(tr);
						if(targetId){
							var p = window.opener || window.parent ;
							var tagObj = p.$('#'+targetId) ;
							var dbrule = tagObj.attr('dbrule');
							if(dbrule && dbrule!=''){
								var datas = {};
								datas.data = data ;
								datas.rule = dbrule ;
								p[fn](datas);
							}else{
								tagObj.val(data.layerId+"-"+data.FID);
							}
							window.close();
						}else{
							alert('错误');
						}
					}
				}]
			} ]
		});
		
	});
	dojo.require("esri.map");// 注册引用map控件
	dojo.require("esri.tasks.find");
	var findTask, findParams;
	var map, symbol, grid, store ,selectedTaxLot;
	var dynamicMapServiceLayer, visible = [];
	function init() {
		map = new esri.Map("map", {
			slider : true
		});// 实例化map控件 
		map.storeGraphics = [];
		if(isDynamic=="dynamic"){
			dynamicMapServiceLayer = new esri.layers.ArcGISDynamicMapServiceLayer(murl);
		}else{
			dynamicMapServiceLayer = new esri.layers.ArcGISTiledMapServiceLayer(murl);	
		}
		// 定义查询
		findTask = new esri.tasks.FindTask(murl);
		// 定义查询参数
		findParams = new esri.tasks.FindParameters();
		findParams.returnGeometry = true;
		findParams.searchFields = [  "FID","name", "biaoduan","road","licheng", "place" ];

		dojo.connect(dynamicMapServiceLayer, "onLoad", buildLayerList);
	}
	dojo.addOnLoad(init);// 加载是调用init初始化地图
	function buildLayerList(dynamicMapServiceLayer) {
		/*var infos = dynamicMapServiceLayer.layerInfos, info;
		for (var i = 0, il = infos.length; i < il; i++) {
			info = infos[i];
			if (info.defaultVisibility) {
				visible.push(info.id);
			}
		}*/
		//dynamicMapServiceLayer.setVisibleLayers(visible);
		map.addLayer(dynamicMapServiceLayer);
		//jsgisMap.addLayer(new esri.layers.ArcGISTiledMapServiceLayer(jsgisMap.jsgisUrl));
	}
	// //按钮单击开始查询
	var queryTask = null,query = new esri.tasks.Query() ;
	query.outFields = ["*"];
	query.returnGeometry = true;
	function startquery() {
		var layerIds =  Object.keys(_layers) || [ 2,3,4,8,32 ],layer = $("#layer").val(),searchText = document.getElementById("searth_text").value,
			biaoduan = $("#biaoduan").val(),road = $("#road").val();
		if(layer){
			//layerIds = [ parseInt(layer)] ;
			queryTask = new esri.tasks.QueryTask(murl+"/"+layer);
			queryTask.layerId = layer ;
			queryTask.on("complete", function(r){
				var results = r.featureSet.features;
				map.storeGraphics.length = 0 ;
				var items = []; // 存储查询结果属性值的变量
				for (var i = 0, il = results.length; i < il; i++) {
					var result = results[i], attributes = result.attributes,symbol;
					attributes.layerId = r.target.layerId ;
					items.push(attributes); // 向items中压入数据
					var graphic = results[i],ptype = graphic.geometry.type;// 以下为高亮显示查询结果
					if(ptype == "point" ){
						symbol = new esri.symbol.PictureMarkerSymbol(contextPath+'/images/gismap.png', 20, 20);
						symbol.setOffset(0, 10);
					}else if(ptype == "polyline" ){
						symbol = new esri.symbol.SimpleLineSymbol( esri.symbol.SimpleLineSymbol.STYLE_SOLID,new esri.Color([ 255, 0, 0 ]),2);
					}else if(ptype == "polygon"){
						var simpleLine = new esri.symbol.SimpleLineSymbol( esri.symbol.SimpleLineSymbol.STYLE_SOLID,new esri.Color([ 255, 0, 0 ]),2);
						symbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID,simpleLine,new esri.Color([ 255, 0, 0 ]));
					}
					graphic.setSymbol(symbol);
					//var infoTemplate = new esri.InfoTemplate();
					//infoTemplate.setTitle(graphic.attributes.FID);
					//graphic.setInfoTemplate(infoTemplate);
					graphic.visible = false ;
					map.storeGraphics.push(graphic);
					//map.graphics.add(graphic);
				}
				myGridDataSource.length = 0;
				$.merge(myGridDataSource, items);
				var grid = $("#mygrid").data("kendoGrid");
				grid.dataSource.read();
			});
			var whereStr = "" , count = 0 ;
			if(biaoduan){
				whereStr += " biaoduan = '"+biaoduan+"' " ;
				count ++ ;
			}
			if(road){
				whereStr += (count >0 ? " and " : "" )+" road = '"+road+"' " ;
			}
			if(searchText){
				whereStr += (count >0 ? " and ( " : "" ) ;
				$.each(_layers[layer],function(i,v){
					whereStr += (i>0?" or " : "")+" "+ v +" like '%" +searchText+"%' "
				})
				if(whereStr){
					whereStr += (count >0 ? " ) " : "" ) ;
					query.where = whereStr ;
				}else{
					query.where = " name like '%"+searchText+"%' " ;
				}

				/*if(layer == "32"){
					query.where = " name like '%"+searchText+"%' " ;
				}else{
					query.where = " name like '%"+searchText+"%' or biaoduan like '%"+searchText+"%' or road like '%"+searchText+"%' or place like '%"+searchText+"%' ";
				}*/
			}else if(whereStr){
				query.where = whereStr ;
			}else{
				query.where = '1=1';
			}
			query.orderByFields = ["id"] ;
			queryTask.execute(query);
		}else{
			findParams.layerIds = layerIds;
			findParams.searchText = searchText;// 获取查询条件
			findTask.execute(findParams, showResults);	
		}
	}
	// ///早地图上高亮显示查询结果
	function showResults(results) {
		map.graphics.clear();
	/* 	symbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_NULL, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,
				new dojo.Color([ 255, 255, 0 ]), 2), new dojo.Color([ 0, 0, 0, 0 ])); */
		map.storeGraphics.length = 0 ;
		var items = []; // 存储查询结果属性值的变量
		for (var i = 0, il = results.length; i < il; i++) {
			var result = results[i], attributes = result.feature.attributes;
			attributes.layerId = result.layerId ;
			items.push(attributes); // 向items中压入数据
			var graphic = results[i].feature,ptype = graphic.geometry.type;// 以下为高亮显示查询结果
			if(ptype == "point" ){
				symbol = new esri.symbol.PictureMarkerSymbol(contextPath+'/images/gismap.png', 20, 20);
			}else if(ptype == "polyline" ){
				symbol = new esri.symbol.SimpleLineSymbol( esri.symbol.SimpleLineSymbol.STYLE_SOLID,new esri.Color([ 255, 0, 0 ]),2);
			}
			graphic.setSymbol(symbol);
			var infoTemplate = new esri.InfoTemplate();
			infoTemplate.setTitle(graphic.attributes.FID);
			graphic.setInfoTemplate(infoTemplate);
			graphic.visible = false ;
			map.storeGraphics.push(graphic);
			//map.graphics.add(graphic);
		}
		myGridDataSource.length = 0;
		$.merge(myGridDataSource, items);
		var grid = $("#mygrid").data("kendoGrid");
		grid.dataSource.read();
		
	}
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/defineKendouiInit.js"></script>
</head>
<body>
	<div id="splitter" style="width: 99%; height: 98%;margin: 0;padding: 0;">
		<div id="splitter_0" style="width: 100%; height: 100%">
			图层<input id="layer"/>
			项目<input id="road" type="text" name="road">
			标段<input id="biaoduan" type="text" name="biaoduan">
			名称<input id="searth_text" class="k-textbox" type="text" /> <input id="Button1" type="button" class="k-button" value="查询" onclick="startquery();" />
			<input id="mutliSelect" type="button" value="多选" class="k-button" style="position: absolute;right: 13px;" />
		</div>
		<div id="splitter_1" style="width: 100%; height: 100%">
			<div id="splitter_1_0" style="width: 100%; height: 100%">
				<div id="map" style="width: 100%; height: 100%"></div>
			</div>
			<div id="splitter_1_1" style="width: 100%; height: 100%">
				<div id="mygrid"  style="width: 100%; height: 100%"></div>
			</div>
		</div>
	</div>
</body>
</html>