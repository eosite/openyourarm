/**
 * 
 */
var bmapddextFunc = {
	setLocationByGps  : function(rule, params) {
		var $this = pubsub.getJQObj(rule)  ;
		$this.bmapddext("setLocationByGps");
	},
	setLocationMarker : function(rule, params) {
		var $this = pubsub.getJQObj(rule)  ;
		$this.bmapddext("setLocationMarker");
	},
	getLocationMarkerPosition : function(rule,params){
		var $this = pubsub.getJQObj(rule,true) ;	//获取输入控件
		return 	$this.bmapddext("getLocationMarkerPosition");
	},
	clearOverlays : function(rule,params){
		var $this = pubsub.getJQObj(rule) ;
		return 	$this.bmapddext("clearOverlays");	
	},
	setZoom : function(rule,params){
		var $this = pubsub.getJQObj(rule) ;
		var zoom = null;
		$.each(params,function(i,item){
			zoom = item;
		})
		zoom && $this.bmapddext("setZoom",zoom);		
	},
	setMarkerByGps : function(rule,params){
		var $this = pubsub.getJQObj(rule) ;
		var point = null;
		$.each(params,function(i,item){
			point = item;
		})
		if(point){
			if(typeof point == 'string'){
				try{
					point = JSON.parse(point);
				}catch(e){
					point = eval(point);
				}
			}
			$this.bmapddext("setMarkerByGps",point);		
		}
	},
	setMarkerAndPanto : function(rule,params){
		var $this = pubsub.getJQObj(rule) ;
		var point = null;
		$.each(params,function(i,item){
			point = item;
		})
		if(point){
			if(typeof point == 'string'){
				try{
					point = JSON.parse(point);
				}catch(e){
					point = eval(point);
				}
			}
			$this.bmapddext("setMarkerAndPanto",point);		
		}
		
	},
	setDragAble : function(rule,params){
		var $this = pubsub.getJQObj(rule) ;
		var value = null;
		$.each(params,function(i,item){
			value = item;
		})
		if(value){
			value = eavl(value);
			value && $this.bmapddext("setDragAble",value);	
		}
	},
	setDragRange : function(rule,params){
		var $this = pubsub.getJQObj(rule) ;
		var value = null;
		$.each(params,function(i,item){
			value = item;
		})
		if(value){
			value = eavl(value);
			value && $this.bmapddext("setDragRange",value);	
		}	
	},
	setKeyWrods : function(rule,params){
		var that = this;
		var keyWords = [];
		$.each(params,function(i,item){
			item && $.merge(keyWords,item.split(","));
		})

		var $this = pubsub.getJQObj(rule) ;
		$this.bmapddext("setKeyWrods",keyWords);
	},
	setSearchRange : function(rule,params){
		var that = this;
		var value;
		$.each(params,function(i,item){
			value = item;
		})

		var $this = pubsub.getJQObj(rule) ;
		$this.bmapddext("setSearchRange",value);
	},
	showSearchPanel2 : function(rule,params){
		var $this = pubsub.getJQObj(rule) ;
		$this.bmapddext("setSearchPanelStatus",true);
	},
	hideSearchPanel : function(rule,params){
		var $this = pubsub.getJQObj(rule) ;
		$this.bmapddext("setSearchPanelStatus",false);
	},
	//获取地图信息
	getMarkerAddress : function(rule,params){
		var $this = pubsub.getJQObj(rule,true) ;
		return $this.bmapddext("getMarkerAddress");
	},
	//获取地点描述
	getAddressTitle : function(rule,params){
		var $this = pubsub.getJQObj(rule,true) ;
		return $this.bmapddext("getAddressTitle");
	},
    getAddress : function(rule,params){
    	var $this = pubsub.getJQObj(rule,true) ;
		return $this.bmapddext("getAddress")[rule.columnName];
    },
    setValue : function(rule,params){
    	var that = this;
		var value;

		var $this = pubsub.getJQObj(rule) ;
		return $this.bmapddext("setValue",params);
    },
    setStartMarker : function(rule,params){
    	var that = this;
		var value;
		$.each(params,function(i,item){
			value = item;
		})

		var $this = pubsub.getJQObj(rule) ;
		return $this.bmapddext("setStartMarker",value);
    },
    setEndMarker : function(rule,params){
    	var that = this;
		var value;
		$.each(params,function(i,item){
			value = item;
		})

		var $this = pubsub.getJQObj(rule) ;
		return $this.bmapddext("setEndMarker",value);
    },
    startDriving : function(rule,params){
    	//开始导航
    	var $this = pubsub.getJQObj(rule) ;
		return $this.bmapddext("startDriving");
    },
    endDriving : function(rule,params){
    	//结束导航
    	var $this = pubsub.getJQObj(rule) ;
		return $this.bmapddext("endDriving");
    },
    //设置起点经度
    setStartLng : function(rule,params){
    	var that = this;
		var value;
		$.each(params,function(i,item){
			value = item;
		})

		var $this = pubsub.getJQObj(rule) ;
		return $this.bmapddext("setStartLng",value);
    },
    //设置起点纬度
    setStartLat : function(rule,params){
    	var that = this;
		var value;
		$.each(params,function(i,item){
			value = item;
		})

		var $this = pubsub.getJQObj(rule) ;
		return $this.bmapddext("setStartLat",value);
    },
    //设置终点经度
    setEndLng : function(rule,params){
    	var that = this;
		var value;
		$.each(params,function(i,item){
			value = item;
		})

		var $this = pubsub.getJQObj(rule) ;
		return $this.bmapddext("setEndLng",value);
    },
    //设置终点纬度
    setEndLat : function(rule,params){
    	var that = this;
		var value;
		$.each(params,function(i,item){
			value = item;
		})

		var $this = pubsub.getJQObj(rule) ;
		return $this.bmapddext("setEndLat",value);
    }
};
pubsub.sub("bmapddext", bmapddextFunc);