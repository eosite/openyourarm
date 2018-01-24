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
<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/arcgis/claro.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/arcgis/js/esri/css/esri.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/arcgis/init.js"></script>
<script>
	var objelementId = "${param.objelementId}" ,nameElementId = "${param.nameElementId}",pageId = "${param.pageId}",gisUrlId="${param.gisUrlId}",
	murl = parent.$("#"+gisUrlId).val() || "<%=SystemConfig.getString("gisServiceUrl")%>",isDynamic;
	if(murl){
		$.ajax({
			method:"post",
			url : "<%=request.getContextPath()%>/mx/form/defined/checkDynamicMap" ,
			dataType : "json",
			success:function(data){
				isDynamic = data.isDynamic ;
			},
			async:false,
			data:{
				gisUrl : murl
			}
		});
	}

	var  k = parent.$("#"+objelementId).val();
	dojo.require("esri.map");// 注册引用map控件
	dojo.require("esri.geometry.Extent");
	var findTask, findParams;
	var map, symbol, grid, store ,selectedTaxLot;
	var dynamicMapServiceLayer, visible = [];

	 require(["dojo/dom", "dojo/dom-construct", "esri/map", "myModules/InfoWindow", "esri/layers/FeatureLayer",
            "esri/InfoTemplate", "dojo/string", "esri/dijit/BasemapGallery", "esri/dijit/BasemapLayer",
            "esri/dijit/Basemap", "dijit/TitlePane", "dojo/domReady!"], function (dom, domConstruct, Map, InfoWindow,
        FeatureLayer, InfoTemplate, string, BasemapGallery, BasemapLayer, Basemap) {
           	function init() {
					var obj = {
						slider : true,
						isZoomSlider:true,
						isClickRecenter:true,
						showAttribution:true,
					};	
					map = new esri.Map("map", obj);// 实例化map控件 	
				
					map.on("extent-change",function(a){
						//console.log(a);
						console.log(a.target.getZoom());
						$("#d").html("zoom->"+a.target.getZoom());
					});
					if(isDynamic == "dynamic"){
						dynamicMapServiceLayer = new esri.layers.ArcGISDynamicMapServiceLayer(murl);	
					}else{
						dynamicMapServiceLayer = new esri.layers.ArcGISTiledMapServiceLayer(murl);	
					}
					

					dojo.connect(dynamicMapServiceLayer, "onLoad", buildLayerList);

					var basemapGallery = new BasemapGallery({
						showArcGISBasemaps: false,
						map: map,
					}, "basemapGallery");
					/*var dataJsons = JSON.parse(datas);
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
					})*/
					basemapGallery.startup(); // 开启  
					dojo.connect(basemapGallery, "onError", function(msg) {
						console.log(msg);
					});
			}
			dojo.addOnLoad(init);// 加载是调用init初始化地图
			function buildLayerList(dynamicMapServiceLayer) {
				var infos = dynamicMapServiceLayer.layerInfos, info;
				for (var i = 0, il = infos.length; i < il; i++) {
					info = infos[i];
					if (info.defaultVisibility) {
						visible.push(info.id);
					}
				}
			//	dynamicMapServiceLayer.setVisibleLayers(visible);
				map.addLayer(dynamicMapServiceLayer);
			}
     });
	
	function convertNum(str){
		return (str+"").replace("px","")-0;
	}
	function retVal(){
		//a.extent
		var $k = $("#draggablekk"),obj = {};
		
		if(convertNum($k.css("left"))<convertNum($k.css("right"))){
			obj["left"] = $k.css("left");
		}else if(convertNum($k.css("left"))>=convertNum($k.css("right"))){
			obj["right"] = $k.css("right");
		}
		if(convertNum($k.css("top"))<convertNum($k.css("bottom"))){
			obj["top"] = $k.css("top");
		}else if(convertNum($k.css("top"))>=convertNum($k.css("bottom"))){
			obj["bottom"] = $k.css("bottom");
		}
		/*obj = {
			top:$k.css("top"),
			bottom:$k.css("bottom"),
			right:$k.css("right"),
			left:$k.css("left"),
		} ;*/ 
		var name = "已设置位置" ;
		parent.$("#"+nameElementId).val(name);
		parent.$("#"+objelementId).val(JSON.stringify([{name:name,position:obj}]));
		parent.layer.close(parent.layer.getFrameIndex());
	}
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/defineKendouiInit.js"></script>
</head>
<body>
	<div id="map" style="width: 100%; height: 100%">
		<div id="draggablekk" style="position:absolute; right:80px; top:10px; z-Index:99;">
			<div data-dojo-type="dijit.TitlePane" data-dojo-props="title:'切换地图',closable:false,open:false">
				<div data-dojo-type="dijit.layout.ContentPane" style="overflow:auto;">
					<div id="basemapGallery"></div>
				</div>
			</div>
		</div>
	</div>
	<div  style="position: absolute;top: 5px;right: 5px;">
		<input class="k-button" type="button" onclick="retVal()" value="确定"></input>
	</div>
	<script type="text/javascript">
		 $("#draggablekk").kendoDraggable({
			    drag: function(e){
			    	var width = $(window).width(),height = $(window).height(),css = {};
			    	if(width/2 <e.clientX){
			    		css.right = width - e.clientX ;
			    		css.left = '' ;
			    	}else{
			    		css.left = e.clientX ;
			    		css.right = '' ;
			    	}
			    	if(height/2 <e.clientY){
			    		css.bottom = height - e.clientY ;
			    		css.top = '' ;
			    	}else{
			    		css.top = e.clientY ;
			    		css.bottom = '' ;
			    	}
			    	$("#draggablekk").css(css);
				}
		  });
		 	if(k){
				var position = JSON.parse(k)[0].position ;
				 $("#draggablekk").css(position);
			}
	</script>
</body>
</html>