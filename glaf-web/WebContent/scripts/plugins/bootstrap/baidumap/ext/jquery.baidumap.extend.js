! function($) {
	var plugin = 'bmapext';

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

			},
			onSelectSearch : function(){

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
				that.$searchPanel.show();
			}else{
				that.$searchPanel.hide();
			}
			that._resize();
		},
		setTraffic : function(){
			var that = this;
			try{
				debugger;
				var ctrl = new BMapLib.TrafficControl();
				that.map.addControl(ctrl);
				 //ctrla.setAnchor(BMAP_ANCHOR_BOTTOM_RIGHT); 
    			// ctrla.showTraffic();
				ctrl.showTraffic({predictDate:{hour:15, weekday: 5}});
			}catch(e){

			}
		},
		setControl: function(){
			var that = this;
			var map = that.map;
			var options = that.options;
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
		},
		_resizeSearch : function(){
			var $positionParentPanel = $(".positionParentPanel");

			$positionParentPanel.find(".positionPanel").width($positionParentPanel.width() - $positionParentPanel.find(".positionLeftPanel").width());
		},
		_resize :function(){
			var that = this;
			var $target = that.$target;
			if($target.is(":hidden")){
				return;
			}
			if(!that.$searchPanel.is(":hidden")){
				if(!that.$map.is(":hidden")){
					that.$map.height($target.height()-that.$searchBtnPanel.height());
					if(!that.$map.is(":hidden")){
						that.map.reset();
						if(that.localMarker && that.localMarker.getPosition()){
							setTimeout(function(){
								that.map.setCenter(that.localMarker.getPosition());	
							},200);
						}
					}
					var haftheight = ($target.height()-that.$searchBtnPanel.height()) /3;
					that.$map.height(haftheight*2);
					that.$searchPanel.height(haftheight);
				}else{
					var haftheight = ($target.height()-that.$searchBtnPanel.height());
					that.$searchPanel.height(haftheight);
				}
				that._resizeSearch();
			}else{
				that.$map.height($target.height());
			}
			
			$.each(that.resizeAry,function(i,item){
				var $parent = item.$parent;
				var $resizePanel = item.$resizePanel;
				if($resizePanel.is(":hidden")){
					return true;
				}
				var type = item.type;
				if(type == 1){
			        var $borther = $resizePanel.siblings();
			        var bortherWidth = 0;
			        $borther.each(function(k,ktem){
			            if(!$(ktem).is(":hidden")){
			                bortherWidth += $(ktem).outerWidth(true);    
			            }
			        })
			        $resizePanel.width($parent.width() - bortherWidth);
				}
			})
			if(!that.$map.is(":hidden")){
				that.map.reset();
				if(that.localMarker && that.localMarker.getPosition()){
					setTimeout(function(){
						that.map.setCenter(that.localMarker.getPosition());	
					},200);
				}
			}
			// if(that.localMarker){
			// 	var point = that.localMarker.getPosition();
				// that.map.setCenter(point);
			// }
		},
		_init: function() {
			var that = this;
			var options = that.options;

			// var $style = $('<script type="text/javascript" src="http://api.map.baidu.com/library/TrafficControl/1.4/src/TrafficControl_min.js"></script>');
			// 	$("body").append($style);
			
			that.resizeAry = [];
			//设置是否允许微调
			that.dragAble = that.options.dragAble;
			//设置微调范围
			that.options.dragRang && (that.dragRang = that.options.dragRang);
			//查询关键字
			if(that.options.searchWords){
				that.searchWords  = that.options.searchWords.split(",");
				that.originalSearchWords = that.options.searchWords.split(",");
			}
			//设置搜索范围
			that.searchRange = that.options.searchRange;

			var $target = $(that.target);
			that.$target = $target;
			$target.addClass("bmapext")


			var $searchBtnPanel = $("<div class='searchBtnPanel' style='width:100%;'>"
				+"</div>");
			$target.append($searchBtnPanel);
			var $right = $('<div style="float:left;" class="searchBtnPanel_r"><button type="button" style="font-size:20px" id="startDrivingBtn" class="btn btn-default btn-sm myBtn" >取消</button></div>');
			var $left = $('<div style="float:left;" class="input-icon searchBtnPanel_l">'
				+'<i class="glyphicon glyphicon-search"></i> '
				+'<input type="text" class="form-control" frame-variable="fv1" placeholder="搜索" style="height: 25px; position: relative; top: 0px;" readonly> '
				+'</div>')
			$searchBtnPanel.append($left);
			$searchBtnPanel.append($right);
			$right.hide();
			that.resizeAry.push({
				$parent : $searchBtnPanel,
				$resizePanel : $left,
				type : '1'	//1为左右结构
			})
			if(!that.options.showSearchBtn){
				$searchBtnPanel.hide();
			}
			that.$searchBtnPanel = $searchBtnPanel;

			//增加地图面板
			var $map = $("<div style='width:100%;'></div>");
			$map.attr("id",$target.attr("id") + "_map");
			$target.append($map);
			that.$map = $map;

			//增加地图搜索面板
			var $searchPanel = $("<div class='searchPanel' style='width:100%;'></div>");
			$searchPanel.attr("id",$target.attr("id") + "_searchPanel");
			$target.append($searchPanel);
			that.$searchPanel = $searchPanel;
			if(!options.showSearchPanel){
				var haftheight = $target.height() /3;
				that.$map.height(haftheight*2);
				that.$searchPanel.height(haftheight);
				$searchPanel.hide();
			}else{
				that.$map.height($target.height());
			}

			//加载地图引擎
			var map = new BMap.Map($map[0]);
			that.map = map; //百度地图对象
			if (options.scrollWheelZoom) {
				//是否开放树表滚轮控制缩放
				map.enableScrollWheelZoom();
			}

			that.setControl();

			if(!that.localMarker){
				var myIcon = new BMap.Icon(contextPath + "/images/bmap.png", new BMap.Size(25,34));	
				that.localMarker = new BMap.Marker(null,{icon:myIcon});

				var renderPoint = null;
				var last_point = that.localMarker.getPosition();
				that.localMarker.removeEventListener("dragging");
				that.localMarker.removeEventListener("dragend");
				that.localMarker.addEventListener("dragging", function(e) {
					if (that.map.getDistance(e.currentTarget.point, last_point) < that.dragRang) {
						renderPoint = e.currentTarget.point;	//设置范围内的点
					}
				});
				that.localMarker.addEventListener("dragend", function(e) {
					if (that.map.getDistance(e.currentTarget.point, last_point) > that.dragRang && renderPoint) {
						that.localMarker.setPosition(renderPoint);	//超过范围还原
					}
					that.setKeyWrods2();
					that.setLocationInfo(that.localMarker.point)
				});
			}

			if(that.dragAble && (typeof that.dragAble == 'boolean' || (typeof that.dragAble == 'string' && that.dragAble == 'true'))){
				that.dragAble  && that.localMarker.enableDragging();		
			}

			that.isInitEnd = false;
			that.isFirstInit = true;
			that.getLocationByGps(function(point){
				that.setInitPoint(point);
			},function(){
				that.setInitPoint(null);
			})	

			
			// if(that.options.refreshms){
			// 	var intervalNum = setInterval(function(){
			// 		that.setLocationByGps2();
			// 	},that.options.refreshms);	
			// }
			//定位当前位置
			// that.getlocationPosition(function(r){
			// 	that.setInitPoint(r.point);
			// },function(){
			// 	that.setInitPoint(null);
			// });

			that._bindEvent();
			that._resize();
		},
		setInitPoint : function(point){
			var that = this;
			var options = that.options;
			if(!point){
				if(that.isInitEnd){
					return;
				}
				point = new BMap.Point(116.404, 39.915);
			}

			var point = new BMap.Point(point.lng, point.lat);
			if(that.prePoint){
				that.localMarker.setPosition(point);	
				that.map.centerAndZoom(point, parseInt(options.zoom || 0) || 15);
				that.map.addOverlay(that.localMarker);
				return;
			}
			that.localMarker.setPosition(point);
			
			if(that.isInitEnd){
				that.map.setCenter(point);
			}else{
				that.map.centerAndZoom(point, parseInt(options.zoom || 0) || 15);
				that.isInitEnd = true;
			}
			that.map.addOverlay(that.localMarker);
			if(that.options.showSearchPanel){
		 		that._createSearchPanel(point);
			}
			that.setLocationInfo(point);
		},
		//允许微调
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
		//设置拖拽范围
		setDragRange : function(value){
			var that = this;
			value && (that.dragRang =value);
		},
		setKeyWrods2 : function(keyWords){
			var that = this;
			that.searchWords = keyWords || that.originalSearchWords;
			var point = null;
			if(that.localMarker){
				point = that.localMarker.getPosition();
			}
			if(point){
				//范围搜索
				that.localSearch.searchNearby(that.searchWords,point,that.searchRange || 1000);
			}else{
				that.localSearch.searchInBounds(keyWords, that.map.getBounds());
			}
		},
		//设置查询关键字
		setKeyWrods : function(keyWords){
			var that = this;
			that.searchWords = keyWords || that.searchWords;
			var point = null;
			if(that.localMarker){
				point = that.localMarker.getPosition();
			}
			that.localSearch.search(that.searchWords);
			// if(point){
			// 	//范围搜索
			// 	that.localSearch.searchNearby(that.searchWords,point,that.searchRange || 1000);
			// }else{
			// 	that.localSearch.searchInBounds(keyWords, that.map.getBounds());
			// }
			
		},
		//创建查询面板
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
						if(results && results.length == null){
							results = [results];
						}
						for(var i = 0 ; i < results.length; i ++ ){            
							var kbb = results[i];
							for (var j = 0; j < kbb.getCurrentNumPois(); j ++){
								var $li = $("<li class='positionParentPanel' style='overflow:hidden'></li>");
								var $left = $("<div class='positionLeftPanel' style='float:left'><img src='"+contextPath+"/images/bmapsearch.png'></div>");

								var $div = $("<div style='float:left' class='positionPanel'></div>");
								var $title = $("<div class='titleName'></div>");
								var $position = $("<div class='positionName'></div>");
								var $checked = $("<div class='checkedIcon'><i class='fa fa-check'></i></div>");


								$title.text(kbb.getPoi(j).title);
								$position.text(kbb.getPoi(j).address);
								$div.append($checked);
								$div.append($title);
								$div.append($position);

								$div.data("data",kbb.getPoi(j));

								$li.append($left);
								$li.append($div);
								$ul.append($li);
							}
						}
						that._resizeSearch();
					}
				}
			};
			var local = new BMap.LocalSearch(that.map, options);
			var myKeys = ["酒店", "加油站"];
			that.localSearch = local;	//设置查询面板状态
			// local.search(that.searchWords);
		    local.searchNearby(that.searchWords,locationPoint,that.searchRange || 1000);
		},
		//注册事件
		_bindEvent: function() {
			var that = this;
			var $target = $(that.target);

			that.$searchBtnPanel.on("click","input",function(e){
				var $this = $(this);
				that.$searchBtnPanel.find(".searchBtnPanel_r").show();
				that.$map.hide();
				that._resize();
				$this.removeAttr("readonly");
			})
			that.$searchBtnPanel.on("change","input",function(e){
				that.setKeyWrods($(this).val());
			})
			that.$searchBtnPanel.on("click",".searchBtnPanel_r button",function(e){
				var $this = $(this);
				that.$searchBtnPanel.find(".searchBtnPanel_r").hide();
				that.$map.show();
				that._resize();
				that.$searchBtnPanel.find(".searchBtnPanel_l input").attr("readonly","readonly");
			})


			$target.resize(function() {
				that._resize();
			});

			$target.on("click",".positionPanel",function(e){
				var $this = $(this);
				$(".positionPanel.active").removeClass("active");
				$this.addClass("active");
				var poi = $this.data("data");
				var point = poi.point;

				that.setLocationMarker2(point,true);
				that.setLocationInfo(poi);

				if($.isFunction(that.options.events.onSelectSearch)){
					that.options.events.onSelectSearch();	
				}
			})
		},
		//设置地点位置
		setLocationInfo : function(point){
			var that = this;
			that.localTitle  = "";
			if(point.address){
				that.localinfo = point.province + point.address;
				that.localTitle = point.title;

				that.localAdressInfo = {
					address : point.address,
					phoneNumber : point.phoneNumber,
					postcode : point.postcode,
					province : point.province,
					city : point.city,
					district : "",
					street : "",
					streetNumber : ""
				};
				that.localAdressInfo.lng = point.point.lng;
				that.localAdressInfo.lat = point.point.lat;
			}else{
				var gc = new BMap.Geocoder();
				var mark_point = that.localMarker.getPosition();    
				gc.getLocation(mark_point, function(rs){
					that.localinfo = rs.address;
					that.localAdressInfo = rs.addressComponents;
					that.localAdressInfo.lng = mark_point.lng;
					that.localAdressInfo.lat = mark_point.lat;
				});
			}
		},
		getAddress : function(){
			return this.localAdressInfo;
		},
		//获取地点说明
		getAddressTitle : function(){
			return this.localTitle;
		},
		//设置当前坐标位置
		setLocationMarker2 : function(point,flag){
			var that = this;

			that.locationPoint = point;
			that.localMarker.setPosition(point);
			if(!flag){
				that.setLocationInfo(point);
			}
			that.map.setCenter(point);
			
			//设置拖拽距离不可以超过其原点2000米；
			var renderPoint = null;

		},
		getLocationByPhoneGps : function(callback){
			var thatWin = /*pubsub.getThat(rule, true)*/ window;
			window.WebViewJavascriptBridge.callHandler('callGps', {}, function(responseData) {
				if(typeof responseData == 'string'){
					responseData = JSON.parse(responseData);
				}
				var pt = new BMap.Point(responseData.longitude, responseData.latitude);

				callback(true,pt);
			});
		},
		getLocationByBmapGps : function(callback){
			var that = this;
			var geolocation = new BMap.Geolocation();
			geolocation.getCurrentPosition(function(r) {
				if (this.getStatus() == BMAP_STATUS_SUCCESS) {
					if($.isFunction(callback)){
						callback(true,r.point);
					}
				}else{
					if($.isFunction(callback)){
						callback(false,r);
					}
					
				}
			});
		},
		isMobile : function(){
			var userAgentInfo = navigator.userAgent;  
			var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod");  
			var flag = false;  
			for (var v = 0; v < Agents.length; v++) {  
				if (userAgentInfo.indexOf(Agents[v]) > 0) { flag = true; break; }  
			}  
			return flag;  
		},
		//根据手机端的GPS获取定位信息
		getLocationByGps : function(callback,ecallback){
			var that = this;
			if(that.isMobile()){
				if(!that.intervalNum){
					if(window.WebViewJavascriptBridge){
						that.getLocationByPhoneGps(function(flag,point){
							if(flag){
								callback(point);
							}else{
								ecallback();
							}
						})
						clearInterval(that.intervalNum);
						that.intervalNum = null;
					}else{
						var j = 0;
						that.intervalNum = setInterval(function(){
							if (window.WebViewJavascriptBridge) {
								that.getLocationByPhoneGps(function(flag,point){
									if(flag){
										callback(point);
									}else{
										ecallback();
									}
								})
								clearInterval(that.intervalNum);
								that.intervalNum = null;
							}else{
								if(j == 0){
									that.getLocationByBmapGps(function(flag,point){
										if(flag){
											callback(point);
										}else{
											ecallback();
										}
									})
									j++;
								}
							}
						},2000);	
					}
				}
			}else{
				if(that.isFirstInit){
					setTimeout(function(){
						that.isFirstInit = false;
						if (window.WebViewJavascriptBridge) {
							that.getLocationByPhoneGps(function(flag,point){
								if(flag){
									callback(point);
								}else{
									ecallback();
								}
							})
						}else{
							that.getLocationByBmapGps(function(flag,point){
								if(flag){
									callback(point);
								}else{
									ecallback();
								}
							})
						}
					},1800)
				}else{
					if (window.WebViewJavascriptBridge) {
						that.getLocationByPhoneGps(function(flag,point){
							if(flag){
								callback(point);
							}else{
								ecallback();
							}
						})
					}else{
						that.getLocationByBmapGps(function(flag,point){
							if(flag){
								callback(point);
							}else{
								ecallback();
							}
						})
					}
				}
			}
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
		//获取当前坐标
		getLocationMarkerPosition : function(){
			var that = this;
			return JSON.stringify(that.localMarker.point);
		},
		//获取定位信息
		getMarkerAddress : function(){
			var that = this;
			return that.localinfo;
		},
		//清空坐标信息
		clearOverlays : function(){
			var that = this;
			that.map.clearOverlays();
		},
		//设置缩放大小
		setZoom : function(zoom){
			var that = this;
			zoom && that.setZoom(zoom);
		},
		//坐标转换
		convertPointsByGps : function(pointArr,translateCallback){
			var convertor = new BMap.Convertor();
	        convertor.translate(pointArr, 1, 5, translateCallback)
		},
		setLocationByGps : function(){
			var that = this;
			that.getLocationByGps(function(point){
				var point = point;
				that.setLocationMarker2(point);
				that.setKeyWrods2();
			})
		},
		//设置坐标位置
		setMarkerByGps : function(point){
			var that = this;
			if(point){
				if(that.isInitEnd){
					var pt = new BMap.Point(point.longitude, point.latitude,point.altitude);
					that.map.setCenter(pt);
					var point = pt;
					that.setLocationMarker2(point);
				}else{
					that.prePoint = point;	
				}
			}
		},
		//设置坐标位置并转到
		setMarkerAndPanto : function(point){
			var that = this;
			if(point){
				if(that.isInitEnd){
					var pt = new BMap.Point(point.lng, point.lat);
					that.map.panTo(pt);
					that.setLocationMarker2(pt);
				}else{
					that.prePoint = point;				
				}
			}
		},
		//设置查询范围
		setSearchRange : function(value){
			var that = this;
			that.searchRange = value;
		}
	}

	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor: Build
	});

}(jQuery);