! function($) {
	var plugin = 'bmapddext';

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
			onLocationChange : function(){

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
		resize : function(){
			var that =this;
			var otherHeight =0;
			if(that.$tool && !that.$tool.is(":hidden")){
				otherHeight += that.$tool.outerHeight(true)
			}
			if(that.$selectSurePanel && !that.$selectSurePanel.is(":hidden")){
				otherHeight += that.$selectSurePanel.outerHeight(true)
			}
			that.$map.height(that.$target.height() -  otherHeight);

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
		},
		setValue : function(value){
			var that = this;
			if(typeof value == 'string'){
				value = JSON.parse(value);
			}
			if(value && (value.startlng && value.startlat && value.endlng && value.endlat)){
				var startPoint = new BMap.Point(value.startlng, value.startlat);
				var endPoint = new BMap.Point(value.endlng, value.endlat);

				setTimeout(function(){
					that.setStartMarker(startPoint);
					that.setEndMarker(endPoint);		
				},1000)
			}
		},
		//设置开始坐标，用于外部调用
		setStartMarker : function(point){
			var that = this;
			//地图选点
			if(!that.startMarker){
				var myIcon = new BMap.Icon(contextPath + "/images/bmapStart.png", new BMap.Size(25,34));	
				that.startMarker = new BMap.Marker(null,{icon:myIcon});
			}
			that.startMarker.setPosition(point);
			if(that.isInitEnd && !that.$map.is(":hidden")){
				// that.map.addOverlay(that.startMarker);    //添加标注
				that.startDriving();
			}else{
				setTimeout(function(){
					// that.map.addOverlay(that.startMarker);    //添加标注
					that.startDriving();
				},1000);
			}
		},
		//设置结束坐标，用于外部调用
		setEndMarker : function(point){
			var that = this;
			//地图选点
			if(!that.endMarker){
				var myIcon = new BMap.Icon(contextPath + "/images/bmapEnd.png", new BMap.Size(25,34));	
				that.endMarker = new BMap.Marker(null,{icon:myIcon});
			}
			that.endMarker.setPosition(point);
			if(that.isInitEnd && !that.$map.is(":hidden")){
				// that.map.addOverlay(that.endMarker);    //添加标注
				that.startDriving();
			}else{
				setTimeout(function(){
					// that.map.addOverlay(that.endMarker);    //添加标注
					that.startDriving();
				},1000);
			}
			
		},
		//设置起点经度
		setStartLng : function(value){
			var that = this;
			that.startLng = value;
		},
		//设置起点纬度
		setStartLat : function(value){
			var that = this;
			that.startLat = value;
		},
		//设置终点经度
		setEndLng : function(value){
			var that = this;
			that.endLng = value;
		},
		//设置终点纬度
		setEndLat : function(value){
			var that = this;
			that.endLat = value;
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
		//设置当前坐标位置
		setLocationMarker2 : function(point,flag,onrenderFlag){
			var that = this;

			that.locationPoint = point;
			that.localMarker.setPosition(point);
			if(!flag){
				that.setLocationInfo(point);
			}
			if(!onrenderFlag){
				that.map.setCenter(point);	
			}
			that.map.addOverlay(that.localMarker);    //添加标注
			
			//设置拖拽距离不可以超过其原点2000米；
			var renderPoint = null;
		},
		//清空坐标信息
		clearOverlays : function(){
			var that = this;
			that.map.clearOverlays();
			that.startMarker && (that.startMarker.point = null);
			that.endMarker && (that.endMarker.point = null);
		},
		//开始导航
		startDriving :function(){
			var that = this;
			if(that.startLng && that.startLat && that.endLng && that.endLat){
				var startPoint = new BMap.Point(that.startLng, that.startLat);
				var endPoint = new BMap.Point(that.endLng, that.endLat);
				that.startMarker && (that.startMarker.point = null);
				that.endMarker && (that.endMarker.point = null);
				that.setStartMarker(startPoint);
				that.setEndMarker(endPoint);
				that.startLng = null;
				that.startLat = null; 
				that.endLng = null; 
				that.endLat = null;
				return;
			}
			if(!that.startMarker || !that.endMarker){
				return;
			}
			var startPoint = that.startMarker.getPosition();
			var endPoint = that.endMarker.getPosition();
			if(!startPoint || !endPoint){
				return;
			}
			that._setDriving();

			if(!that.drivingCircle){
				that.drivingCircle = new BMap.Circle(null,100,{fillColor:"#3fa5f5", strokeWeight: 0.1,fillOpacity: 0.3, strokeOpacity: 0.3});
				// that.map.addOverlay(that.drivingCircle);
			}

			that.getLocationByGps(function(point){
				var point = point;
				that.setLocationMarker2(point);
				// that.map.removeOverlay(that.drivingCircle); 
				that.drivingCircle.setCenter(point);
				
				that.map.setCenter(point);
				if($.isFunction(that.options.events.onLocationChange)){
					setTimeout(function(){
						that.options.events.onLocationChange();
					},100);
				}
			})
			that.options.refreshms = that.options.refreshms || 500;
			if(that.options.refreshms && !that.intervalNum){
				that.intervalNum = setInterval(function(){
					that.getLocationByGps(function(point){
						var point = point;
						that.setLocationMarker2(point,null,true);
						// that.map.removeOverlay(that.drivingCircle); 
						// that.drivingCircle.setCenter(point);
						// that.map.addOverlay(that.drivingCircle);
						if($.isFunction(that.options.events.onLocationChange)){
							setTimeout(function(){
								that.options.events.onLocationChange();
							},100);
						}
					})
				},that.options.refreshms);	
			}
		},
		//结束导航
		endDriving : function(){
			var that = this;
			if(that.intervalNum){
				//清除循环
				clearInterval(that.intervalNum);
				that.intervalNum = null;
			}
		},
		_init: function() {
			var that = this;
			var options = that.options;
			var $target = $(that.target);
			that.$target = $target;


			$target.resize(function(){
				that.resize();
			})

			that.resizeAry = that.resizeAry || [];

			//谷歌位置信息获取
			var gc = new BMap.Geocoder();
			that.gc = gc;

			$target.addClass("bmapdd");

			// var $style = $('<script type="text/javascript" src="http://api.map.baidu.com/library/TrafficControl/1.4/src/TrafficControl_min.js"></script>');
			// 	$("body").append($style);
			
			var $tool = $("<div class='topPanel'>");
			that.$target.append('<div class="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>');
			var $left = $("<div class='form-horizontal' style='float:left'>" + '<div class="form-group" style="margin-bottom:0px;">'
			+'<div class="row" style="border-bottom:1px solid darkgray;">'
				+'<label class="control-label col-xs-3">起点：</label>'
				+'<div class="col-xs-6">'
				+'<input type="text" id="'+that.target.id+'_startInp'+'" class="form-control input-sm positionInput" value="">'
				+'</div>'
				+'<div class="col-xs-2">'
				+'<button type="button" id="saveBtn" bmapdd_atype="start" class="btn btn-default btn-sm selectPositionBtn myBtn" >'
	            	+'<i class="fa fa-map-pin"></i>选点'
	            +'</button>'
				+'</div>'
			+'</div>'
			+'<div class="row">'
				+'<label class="control-label col-xs-3">终点：</label>'
				+'<div class="col-xs-6">'
				+'<input type="text" id="'+that.target.id+'_endInp'+'" class="form-control input-sm positionInput" value="">'
				+'</div>'
				+'<div class="col-xs-2">'
				+'<button type="button" id="saveBtn" bmapdd_atype="stop" class="btn btn-default btn-sm selectPositionBtn myBtn" >'
	            	+'<i class="fa fa-map-pin"></i>选点'
	            +'</button>'
				+'</div>'
			+'</div>'
			+'</div>'
			+"</div></div>");
			var $right = $('<div style="float:left;""><button type="button" style="font-size:20px" id="startDrivingBtn" class="btn btn-default btn-sm myBtn" >开始导航</button></div>');
			$tool.append($left);
			$tool.append($right);
			$target.append($tool);

			that.resizeAry.push({
				$parent : $tool,
				$resizePanel : $left,
				type : '1'	//1为左右结构
			})


			//开始下拉框
			that._startInpAc = new BMap.Autocomplete(    //建立一个自动完成的对象
				{"input" : that.target.id+'_startInp'
				,"location" : that.map
			});
			//结束下拉框
			that._endInpAc = new BMap.Autocomplete(    //建立一个自动完成的对象
				{"input" : that.target.id+'_endInp'
				,"location" : that.map
			});


			that.$tool = $tool;	//选择起点和终点栏
			if(!that.options.showSelectPanel){
				$tool.hide();	
			}

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

			
			//增加地图面板
			var $map = $("<div style='width:100%;height:100%;'></div>");
			$map.attr("id",$target.attr("id") + "_map");
			$target.append($map);
			that.$map = $map;

			//增加地址确认栏
			var $selectSurePanel = $("<div class='selectSurePanel' style='overflow:hidden;'></div>");
			var $leftPanel = $("<div style='width:80%;float:left;'></div>");
			var $titlePanel = $("<div class='selectSureTitle'>aaa</div>");
			// var $contentPanel = $("<div class='selectSureContent'>bbb</div>");
			$leftPanel.append($titlePanel);
			// $leftPanel.append($contentPanel);
			var $rightPanel = $("<div style='float:left;'></div>");
			$rightPanel.append('<button type="button" style="font-size:20px" id="sureSelectBtn" class="btn btn-default btn-sm myBtn" >确认</button>');
			$selectSurePanel.append($leftPanel);
			$selectSurePanel.append($rightPanel);
			$selectSurePanel.hide();
			$target.append($selectSurePanel);

			
			that.resizeAry.push({
				$parent : $selectSurePanel,
				$resizePanel : $leftPanel,
				type : '1'	//1为左右结构
			})

			that.$selectSurePanel = $selectSurePanel;


			that.resize();

			that.isFirstInit = true;
			//加载地图引擎
			var map = new BMap.Map($map[0]);
			that.map = map; //百度地图对象
			//是否开放树表滚轮控制缩放
				map.enableScrollWheelZoom();
			// if (options.enableScrollWheelZoom) {
			// 	//是否开放树表滚轮控制缩放
			// 	map.enableScrollWheelZoom();
			// }

			//设置当前位置
			if(!that.localMarker){
				var myIcon = new BMap.Icon(contextPath + "/images/bmapGPS.png", new BMap.Size(32,32),{imageSize:"16px"});	
				that.localMarker = new BMap.Marker(null,{icon:myIcon});
			}


			that.getLocationByGps(function(point){
				that.setInitPoint(point);
			},function(){
				that.setInitPoint(null);
			})

			//定位
			that._bindEvent();
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
		},
		openInfoWindow : function(){
		},

		getOject: function() {
			return $(this.target).find("video");
		},
		_setDriving : function(){
			var that = this;
			if(that.startMarker && that.endMarker){
				var startPoint = that.startMarker.getPosition();
				var endPoint = that.endMarker.getPosition();
				if(startPoint && endPoint){
					// that.map.removeOverlay(that.startMarker);    //添加标注
					// that.map.removeOverlay(that.endMarker);    //添加标注

					if(!that.driving){
						//BMAP_DRIVING_POLICY_LEAST_TIME为最少时间
						//BMAP_DRIVING_POLICY_LEAST_DISTANCE为最短距离
						//BMAP_DRIVING_POLICY_AVOID_HIGHWAYS为避开高速
						var routePolicy = [BMAP_DRIVING_POLICY_LEAST_TIME,BMAP_DRIVING_POLICY_LEAST_DISTANCE,BMAP_DRIVING_POLICY_AVOID_HIGHWAYS];
						var driving = new BMap.DrivingRoute(that.map, {renderOptions:{map: that.map, autoViewport: true},policy: BMAP_DRIVING_POLICY_LEAST_TIME});
						that.driving = driving;
						
					}
					// that.driving.disableAutoViewport();
					that.driving.search(startPoint,endPoint);
				}
			}
		},
		_setStartMarker : function(point){
			var that = this;
			//地图选点
			if(!that.startMarker){
				var myIcon = new BMap.Icon(contextPath + "/images/bmapStart.png", new BMap.Size(25,34));	
				that.startMarker = new BMap.Marker(null,{icon:myIcon});
			}
			that.startMarker.setPosition(point);
			// that.map.addOverlay(that.startMarker);    //添加标注
			that._setDriving();
		},
		_setEndMarker : function(point){
			var that = this;
			//地图选点
			if(!that.endMarker){
				var myIcon = new BMap.Icon(contextPath + "/images/bmapEnd.png", new BMap.Size(25,34));	
				that.endMarker = new BMap.Marker(null,{icon:myIcon});
			}
			that.endMarker.setPosition(point);
			// that.map.addOverlay(that.endMarker);    //添加标注
			that._setDriving();
		},
		_bindEvent: function() {
			var that = this;
			var $target = $(that.target);


			that._startInpAc.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
				var _value = e.item.value;
				var myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
				
				var local = new BMap.LocalSearch(that.map, { //智能搜索
					onSearchComplete: function(){
						var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
						that.map.setCenter(pp);
						that._setStartMarker(pp);	//设置起点
					}
				});
				local.search(myValue);
			});
			that._endInpAc.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
				var _value = e.item.value;
				var myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
				
				var local = new BMap.LocalSearch(that.map, { //智能搜索
					onSearchComplete: function(){
						var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
						that.map.setCenter(pp);
						that._setEndMarker(pp);	//设置终点
					}
				});
				local.search(myValue);
			});

			$target.on("click",".startDrivingBtn",function(e){

			})
			// that._endInpAc 

			//选点事件
			$target.on("click",".selectPositionBtn",function(e){
				that.$selectSurePanel.show();
				that.resize();
				//地图选点
				if(!that.selectMarker){
					var myIcon = new BMap.Icon(contextPath + "/images/bmaplocation.png", new BMap.Size(25,34));	
					that.selectMarker = new BMap.Marker(null,{icon:myIcon});
				}

				that.selectMarkerInfo = that.selectMarkerInfo || new BMap.InfoWindow();  // 创建信息窗口对象

				var $this = $(this);
				var nowpoint = null;
				if($this.attr("bmapdd_atype") == 'start'){
					that.selectType = 1;	//1为选择开始
					if(that.startMarker)
						nowpoint = that.startMarker.getPosition();
				}else{
					that.selectType = 2;	//1为选择终止节点
					if(that.endMarker)
						nowpoint = that.endMarker.getPosition();
				}

				that.$selectSurePanel.find(".selectSureTitle").text('定位中');
				if(nowpoint){
					that.selectMarker.setPosition(nowpoint);
					that.map.setCenter(nowpoint);
					// that.map.addOverlay(that.selectMarker);
					that.startSelect = true;
					//获取位置的详细信息
					that.gc.getLocation(nowpoint,function(pointInfo){
						that.$selectSurePanel.find(".selectSureTitle").text(pointInfo.address)
					})
				}else{
					//获取我的位置信息
					that.getLocationByGps(function(point){
						that.selectMarker.setPosition(point);
						that.map.setCenter(point);
						// that.map.addOverlay(that.selectMarker);
						that.startSelect = true;

						//获取位置的详细信息
						that.gc.getLocation(point,function(pointInfo){
							that.$selectSurePanel.find(".selectSureTitle").text(pointInfo.address)
						})
					})	
				}
			})

			//确认选点事件
			$target.on("click","#sureSelectBtn",function(e){
				that.startSelect = false;
				var nowPoint = that.selectMarker.getPosition();
				// that.map.removeOverlay(that.selectMarker);
				that.$selectSurePanel.hide();
				that.resize();
				if(that.selectType == 1){
					//确定起点位置
					//获取位置的详细信息
					that.gc.getLocation(nowPoint,function(pointInfo){
						$("#"+that.target.id+'_startInp').val(pointInfo.address);

						that._setStartMarker(nowPoint);	//设置起点
					})
				}else if(that.selectType == 2){
					//确定终点位置
					//获取位置的详细信息
					that.gc.getLocation(nowPoint,function(pointInfo){
						$("#"+that.target.id+'_endInp').val(pointInfo.address);

						that._setEndMarker(nowPoint);	//设置终点
					})
				}
			})

			that.map.addEventListener("dragging", function(position){
				if(that.selectMarker && that.startSelect){
					that.selectMarker.setPosition(that.map.getCenter()); 	
				}
			});

			that.map.addEventListener("dragend",function(position){
				if(that.selectMarker && that.startSelect){
					var point = that.map.getCenter();
					that.selectMarker.setPosition(point); 	
					that.$selectSurePanel.find(".selectSureTitle").text("定位中");
					//获取位置的详细信息
					that.gc.getLocation(point,function(pointInfo){
						that.$selectSurePanel.find(".selectSureTitle").text(pointInfo.address);
					})
				}
			})

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
		//根据手机端的GPS获取定位信息
		getLocationByGps : function(callback,ecallback){
			var that = this;
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
		getAddress : function(){
			return this.localAdressInfo;
		},
		//获取地点说明
		getAddressTitle : function(){
			return this.localTitle;
		},
		//获取定位信息
		getMarkerAddress : function(){
			var that = this;
			return that.localinfo;
		}
	}

	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor: Build
	});

}(jQuery);