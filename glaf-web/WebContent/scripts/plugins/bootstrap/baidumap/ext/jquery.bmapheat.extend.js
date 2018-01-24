! function($) {
	var plugin = 'bmapheat';

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
		if (columns && columns.length > 0) {
			state.options.columns = eval('(' + columns + ')');
		}
		var p = new Build(target, state);

		$.data(target, plugin, p);
	});

	$.fn[plugin].defaults = {
		events :{
			initEnd : function(){

			}
		}
	};

	$.fn[plugin].methods = {
		localMarker : null,	//当前位置信息
		localSearch : null,	//查询对象
		getJQMapObj : function(){
			var that = this;
			return that.$map;
		},
		getJQSearchPanelObj : function(){
			var that = this;
			return that.$searchPanel;
		},
		getLocationPoint : function(){
			var that = this;
			return that.locationPoint;
		},
		setSearchPanelStatus : function(flag){
			var that = this;
			if(flag){
				that.$map.css("height","50%");
				that.$searchPanel.show();
			}else{
				that.$map.css("height","100%");
				that.$searchPanel.hide();
			}
		},
		//判断浏览区是否支持canvas，热力图必须要支持canvas
	    isSupportCanvas : function(){
	        var elem = document.createElement('canvas');
	        return !!(elem.getContext && elem.getContext('2d'));
	    },
	    getMap : function(){
	    	return this.map;
	    },
		_init: function() {
			var that = this;
			var options = that.options;


			//设置是否允许微调
			that.dragAble = that.options.dragAble;
			//设置微调范围
			that.options.dragRang && (that.dragRang = that.options.dragRang);
			//查询关键字
			if(that.options.searchWords){
				that.searchWords  = that.options.searchWords.split(",");
			}
			//设置搜索范围
			that.searchRange = that.options.searchRange;

			var $target = $(that.target);
			//增加地图面板
			var $map = $("<div style='width:100%;height:50%;'></div>");
			$map.attr("id",$target.attr("id") + "_map");
			$target.append($map);
			that.$map = $map;
			//增加地图搜索面板
			var $searchPanel = $("<div class='searchPanel' style='width:100%;height:50%;'></div>");
			$searchPanel.attr("id",$target.attr("id") + "_searchPanel");
			$target.append($searchPanel);
			if(!options.showSearchPanel){
				$map.css("height","100%");
				$searchPanel.hide();
			}
			that.$searchPanel = $searchPanel;

			//加载地图引擎
			var map = new BMap.Map($map[0]);
			that.map = map; //百度地图对象
			if (options.enableScrollWheelZoom) {
				//是否开放树表滚轮控制缩放
				map.enableScrollWheelZoom();
			}

			options.navigation == true && map.addControl(new BMap.NavigationControl()); //平移缩放控件
			options.scaleControl == true &&	map.addControl(new BMap.ScaleControl()); //比例尺控件
			options.overviewMap == true && map.addControl(new BMap.OverviewMapControl()); //缩略图控件
			if(options.overviewMap == true){
				var overView = new BMap.OverviewMapControl(); //缩略图
				//打开或收起缩略图
				map.addControl(new BMap.OverviewMapControl({
					isOpen: true,
					anchor: BMAP_ANCHOR_BOTTOM_RIGHT
				}))
			}
			options.mapTypeControl == true && map.addControl(new BMap.MapTypeControl());

			if(options.geolocation){
				// // 添加定位控件
				// var geolocationControl = new BMap.GeolocationControl();
				// geolocationControl.addEventListener("locationSuccess", function(e) {
				// 	var point = e.point;
				// });
				// geolocationControl.addEventListener("locationError", function(e) {
				// });
				// map.addControl(geolocationControl);
			}


			// 定义一个控件类,即function
			function AutoPositionControl(){
			  // 默认停靠位置和偏移量
			  this.defaultAnchor = BMAP_ANCHOR_BOTTOM_LEFT;
			  this.defaultOffset = new BMap.Size(10, 10);
			}

			// 通过JavaScript的prototype属性继承于BMap.Control
			AutoPositionControl.prototype = new BMap.Control();

			// 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
			// 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
			AutoPositionControl.prototype.initialize = function(map){
				var $div = $('<div class="BMap_AutoPosition"></div>');
				$div.css({
					width:"32px",
					height: "32px",
					cursor: "pointer",
					"background-image": "url('http://api0.map.bdimg.com/images/geolocation-control/mobile/default-40x40.png')",
					"background-size": "20px 20px", 
					"background-repeat": "no-repeat",
					"background-position": "center center",
					"background-color":"rgba(255, 255, 255,0.6)"})
				$div.click(function(e){
					that.setLocationByGps2();
				})
				// 添加DOM元素到地图中
				map.getContainer().appendChild($div[0]);
				// 将DOM元素返回
				return $div[0];
			}
			if(options.geolocation){
				// 创建控件
				var autoPositionCtrl = new AutoPositionControl();
				// 添加到地图当中
				map.addControl(autoPositionCtrl);
			}

			map.centerAndZoom(new BMap.Point(116.404, 39.915), options.zoom || 15);
			map.enableScrollWheelZoom(); // 允许滚轮缩放
			if(that.options.initname){
				map.setCenter(that.options.initname);
			}
			// //定位当前位置
			// that.getlocationPosition(function(r){
			// 	// that.map.panTo(r.point);
			// 	that.locationPoint = r.point;
			// 	map.setCenter(r.point);

			// 	if($.isFunction(that.options.events.initEnd)){
			// 		that.options.events.initEnd();
			// 	}
			// 	//创建搜索面板
			// 	// that._createSearchPanel(r.point);
			// },function(){
			// 	// that.map.setCenter(new BMap.Point(116.404, 39.915));
			// });
			//定位
			// map.centerAndZoom(new BMap.Point(116.404, 39.915),15);
			
			
			
			that.query();

			that._bindEvent();
			// 
			// $(".anchorBL").remove();
		},
		query : function(){
			var that = this;
			var options = that.options;
			//查询热力图的点
			if(!that.isSupportCanvas()){
		    	alert('热力图目前只支持有canvas支持的浏览器,您所使用的浏览器不能使用热力图功能~')
		    }else{
		    	$.ajax({
					type: "POST",
					url: contextPath + "/mx/form/charts/datas",
					contentType: "application/json",
					dataType: "json",
					data: JSON.stringify({
						'rid': options.rid,
						'params': options._querydata
					}),
					success: function(msg) {
						if(msg && msg[0]){
							var datas = [];
							var mapping = JSON.parse(that.options.mapping);
							//遍历数据，转换格式
							$.each(msg[0].data,function(i,item){
								datas.push({
									lng : item[mapping.lng.en],	//经纬
									lat : item[mapping.lat.en],	//纬度
									count : item[mapping.count.en],	//权重
								})
							})
							var from = 5,to = 5;
							if(that.options.translatetype == 'wgs84'){
								from = 1;
							}else if(that.options.translatetype == 'sogou'){
								from = 2;
							}else if(that.options.translatetype == 'gcj02'){
								from = 3;
							}else if(that.options.translatetype == 'mitype'){
								from = 4;
							}
							that.convertPointsByGps(datas,function(data){
								if(data.status === 0) {
									var heatmapOverlay = new BMapLib.HeatmapOverlay({"visible":true,"radius":20});
									that.heatmapOverlay = heatmapOverlay;
									that.map.addOverlay(heatmapOverlay);
									heatmapOverlay.setDataSet({data:data.points,max:100});
								}
							},from,to);
							
						}
					}
				});
		    }
		},
		setDragAble : function(flag){
			var that = this;
			if(flag){
				//允许微调
				if(that.localMarker){
					localMarker.enableDragging();
				}
				that.dragAble = true;
			}else{
				//禁用微调
				if(that.localMarker){
					localMarker.disabledDragging();
				}
				that.dragAble = false;
			}
		},
		setDragRange : function(value){
			var that = this;
			value && (that.dragRang =value);
		},
		setKeyWrods : function(keyWords){
			var that = this;
			that.searchWords = keyWords;
			if(that.locationPoint){
				//范围搜索
				that.localSearch.searchNearby(that.searchWords,that.locationPoint,that.searchRange || 1000);
			}else{
				local.searchInBounds(keyWords, that.map.getBounds());
			}
			
		},
		_createSearchPanel : function(locationPoint){
			var that = this;
			if(!that.options.showSearchPanel){
				return;
			}
			var $searchPanel = that.getJQSearchPanelObj();
			
			var options = {
				onSearchComplete: function(results){
					$searchPanel.empty();
					var $ul = $("<ul></ul>");
					$searchPanel.append($ul);
					// 判断状态是否正确
					if (local.getStatus() == BMAP_STATUS_SUCCESS){
						var s = [];
						for(var i = 0 ; i < results.length; i ++ ){            
							var kbb = results[i];
							for (var j = 0; j < kbb.getCurrentNumPois(); j ++){
								var $li = $("<li></li>");
								var $div = $("<div class='positionPanel'></div>");
								var $title = $("<div class='titleName'></div>");
								var $position = $("<div class='positionName'></div>");
								var $checked = $("<div class='checkedIcon'><i class='fa fa-check'></i></div>");


								$title.text(kbb.getPoi(j).title);
								$position.text(kbb.getPoi(j).address);
								$div.append($checked);
								$div.append($title);
								$div.append($position);

								$div.data("data",kbb.getPoi(j));

								$li.append($div);
								$ul.append($li);
							}
						}
					}
				}
			};
			var local = new BMap.LocalSearch(that.map, options);
			var myKeys = ["酒店", "加油站"];
			that.localSearch = local;	//设置查询面板状态
		    local.searchNearby(that.searchWords,locationPoint,that.searchRange || 1000);
		},
		setMarker : function(){
			// 		var that = this;
			// 		var point = new BMap.Point(116.399, 40.005);
			// //移动到指定位置
			// that.map.panTo(point);  
			// //创建标志
			// 		var marker = new BMap.Marker(point);  // 创建标注    
			// that.map.addOverlay(marker); 	// 将标注添加到地图中
			// debugger;
			// // marker.addEventListener("click", function(){    
			// // 	alert("您点击了标注");    
			// // });
			// marker.enableDragging();    
			// that.marker = marker;
			// marker.addEventListener("dragend", function(e){    
			// 	alert("当前位置：" + e.point.lng + ", " + e.point.lat);    
			// })


			//清除标志
			// map.removeOverlay(marker);    
			// marker.dispose(); // 1.1 版本不需要这样调用
		},
		openInfoWindow : function(){
			// var opts = {    
			// 	width : 250,     // 信息窗口宽度    
			// 	height: 100,     // 信息窗口高度    
			// 	title : "Hello"  // 信息窗口标题   
			// }    
			// var infoWindow = new BMap.InfoWindow("World", opts);  // 创建信息窗口对象    
			// map.openInfoWindow(infoWindow, map.getCenter());      // 打开信息窗口

			// 		var navigopts = {
			// 			offset: new BMap.Size(250, 5),	//可以在控件使用设置坐标，BMap.Size(x坐标, y坐标)
			// 			//设置控件样式，BMAP_NAVIGATION_CONTROL_LARGE 表示显示完整的平移缩放控件。
			// 			//BMAP_NAVIGATION_CONTROL_SMALL 表示显示小型的平移缩放控件。
			// 			//BMAP_NAVIGATION_CONTROL_PAN 表示只显示控件的平移部分功能。
			// 			//BMAP_NAVIGATION_CONTROL_ZOOM 表示只显示控件的缩放部分功能。
			// 			type: BMAP_NAVIGATION_CONTROL_SMALL		
			// 		};
		},

		getOject: function() {
			return $(this.target).find("video");
		},
		_bindEvent: function() {
			var that = this;
			var $target = $(that.target);

			$target.on("click",".positionPanel",function(e){
				var $this = $(this);
				$(".positionPanel.active").removeClass("active");
				$this.addClass("active");
				var poi = $this.data("data");
				var point = poi.point;

				that.setLocationMarker2(point,true);
				that.setLocationInfo(poi);
			})
		},
		setPointByGeolocation: function(pointValue) {
			var that = this;
			var longitude = pointValue.coords.longitude;
			var latitude = pointValue.coords.latitude;
			that.map.panTo(new BMap.Point(longitude, latitude));
		},
		setLocationInfo : function(point){
			var that = this;
			that.localTitle  = "";
			if(point.address){
				that.localinfo = point.province + point.address;
				that.localTitle = point.title;
			}else{
				var gc = new BMap.Geocoder();    
				gc.getLocation(that.localMarker.point, function(rs){
					that.localinfo = rs.address;
				});
			}
		},
		getAddressTitle : function(){
			return this.localTitle;
		},
		setLocationMarker2 : function(point,flag){
			var that = this;

			that.locationPoint = point;
			if (!that.localMarker) {
				var localMarker = new BMap.Marker(point); // 创建标注    
				that.map.addOverlay(localMarker); // 将标注添加到地图中
				if(that.dragAble && (typeof that.dragAble == 'boolean' || (typeof that.dragAble == 'string' && that.dragAble == 'true'))){
					that.dragAble  && localMarker.enableDragging();		
				}
				that.localMarker = localMarker;
			} else {
				that.localMarker.setPosition(point);
			}
			if(!flag){
				that.setLocationInfo(point);
			}
			
			
			//设置拖拽距离不可以超过其原点2000米；
			var renderPoint = null;
			that.localMarker.removeEventListener("dragging");
			that.localMarker.removeEventListener("dragend");
			that.localMarker.addEventListener("dragging", function(e) {
				if (that.map.getDistance(e.currentTarget.point, point) < that.dragRang) {
					renderPoint = e.currentTarget.point;	//设置范围内的点
				}
			});
			that.localMarker.addEventListener("dragend", function(e) {
				if (that.map.getDistance(e.currentTarget.point, point) > that.dragRang && renderPoint) {
					that.localMarker.setPosition(renderPoint);	//超过范围还原
				}
				that.setLocationInfo(that.localMarker.point)
			});
		},
		setLocationMarker : function(){
			var that = this;
			var scallback = function(r){
				var mk = new BMap.Marker(r.point);
				currentLat = r.point.lat;
				currentLon = r.point.lng;
				var pt = new BMap.Point(currentLon, currentLat);
				//移动到指定位置
				that.map.panTo(pt);
				that.map.setZoom(15);
				//增加标志
				//创建标志
				var point = pt;
				
				that._createSearchPanel(point);
				that.setLocationMarker2(point);
				
				//获取地址位置信息
				var geoc = new BMap.Geocoder();
				geoc.getLocation(pt, function(rs) {
					var addComp = rs.addressComponents;
					var city = addComp.city;
					var addComp = rs.addressComponents;
					var texts = addComp.district + "-" + addComp.street + "-" + addComp.streetNumber;
					//获取地理位置成功，跳转  

				});
			}
			that.getlocationPosition(scallback);
		},
		getlocationPosition: function(scallback,escallback) {
			var that = this;
			var geolocation = new BMap.Geolocation();
			geolocation.getCurrentPosition(function(r) {
				if (this.getStatus() == BMAP_STATUS_SUCCESS) {
					if($.isFunction(scallback)){
						scallback(r);
					}
				}else{
					if($.isFunction(escallback)){
						escallback(r);
					}
					
				}
			});
		},
		getLocationMarkerPosition : function(){
			var that = this;
			return JSON.stringify(that.localMarker.point);
		},
		getMarkerAddress : function(){
			var that = this;
			return that.localinfo;
		},
		clearOverlays : function(){
			var that = this;
			that.map.clearOverlays();
		},
		setZoom : function(zoom){
			var that = this;
			zoom && that.setZoom(zoom);
		},
		convertPointsByGps : function(pointArr,translateCallback,from,to){
			var convertor = new BMap.Convertor();
			if(from && to && from == to){
				translateCallback({
					status : 0,
					points : pointArr
				});
			}else{
				convertor.translate(pointArr, from || 1, to || 5, translateCallback)	
			}
		},
		setLocationByGps2 : function(){
			var that = this;
			if (window.WebViewJavascriptBridge) {
				var thatWin = /*pubsub.getThat(rule, true)*/ window;
				window.WebViewJavascriptBridge.callHandler('callGps', {}, function(responseData) {
					if(typeof responseData == 'string'){
						responseData = JSON.parse(responseData);
					}
					var pt = new BMap.Point(responseData.longitude, responseData.latitude);

					var marker = new BMap.Marker(pt);
					that.map.setCenter(pt);
					var point = pt;
					that._createSearchPanel(point);
					that.setLocationMarker2(point);
				});
			} else {
				console.error("请在手机端使用获取GPS坐标功能");
			}
		},
		setLocationByGps : function(){
			var that = this;
			setTimeout(function(){
				if (window.WebViewJavascriptBridge) {
					var thatWin = /*pubsub.getThat(rule, true)*/ window;
					window.WebViewJavascriptBridge.callHandler('callGps', {}, function(responseData) {
						if(typeof responseData == 'string'){
							responseData = JSON.parse(responseData);
						}
						var pt = new BMap.Point(responseData.longitude, responseData.latitude);

						var marker = new BMap.Marker(pt);
						that.map.setCenter(pt);
						var point = pt;
						that._createSearchPanel(point);
						that.setLocationMarker2(point);
					});
				} else {
					console.error("请在手机端使用获取GPS坐标功能");
				}
			},1500)
		},
		setMarkerByGps : function(point){
			var that = this;
			if(point){
				var pt = new BMap.Point(point.longitude, point.latitude,point.altitude);

				var marker = new BMap.Marker(pt);
				that.map.setCenter(pt);
				var point = pt;
				that.setLocationMarker2(point);

				// that.convertPointsByGps([pt],function(data){
				// 	if(data.status === 0) {
				// 		var marker = new BMap.Marker(data.points[0]);
					
				// 		that.map.setCenter(data.points[0]);
				// 		var point = data.points[0];
				// 		that.setLocationMarker2(point);
				// 	}
				// })
			}
		},
		setMarkerAndPanto : function(point){
			var that = this;
			if(point){
				var pt = new BMap.Point(point.lng, point.lat);

				that.map.panTo(pt);
				that.setLocationMarker2(pt);
			}
		},
		setSearchRange : function(value){
			var that = this;
			that.searchRange = value;
		}
	}

	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor: Build
	});

}(jQuery);