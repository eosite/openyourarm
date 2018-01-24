/**
 * 
 */
var bmapextFunc = {
	setLocationByGps  : function(rule, params) {
		var $this = pubsub.getJQObj(rule)  ;
		$this.bmapext("setLocationByGps");
	},
	setLocationMarker : function(rule, params) {
		var $this = pubsub.getJQObj(rule)  ;
		$this.bmapext("setLocationMarker");
	},
	getLocationMarkerPosition : function(rule,params){
		var $this = pubsub.getJQObj(rule,true) ;	//获取输入控件
		return 	$this.bmapext("getLocationMarkerPosition");
	},
	clearOverlays : function(rule,params){
		var $this = pubsub.getJQObj(rule) ;
		return 	$this.bmapext("clearOverlays");	
	},
	setZoom : function(rule,params){
		var $this = pubsub.getJQObj(rule) ;
		var zoom = null;
		$.each(params,function(i,item){
			zoom = item;
		})
		zoom && $this.bmapext("setZoom",zoom);		
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
			$this.bmapext("setMarkerByGps",point);		
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
			$this.bmapext("setMarkerAndPanto",point);		
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
			value && $this.bmapext("setDragAble",value);	
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
			value && $this.bmapext("setDragRange",value);	
		}	
	},
	setKeyWrods : function(rule,params){
		var that = this;
		var keyWords = [];
		$.each(params,function(i,item){
			item && $.merge(keyWords,item.split(","));
		})

		var $this = pubsub.getJQObj(rule) ;
		$this.bmapext("setKeyWrods",keyWords);
	},
	setSearchRange : function(rule,params){
		var that = this;
		var value;
		$.each(params,function(i,item){
			value = item;
		})

		var $this = pubsub.getJQObj(rule) ;
		$this.bmapext("setSearchRange",value);
	},
	showSearchPanel2 : function(rule,params){
		var $this = pubsub.getJQObj(rule) ;
		$this.bmapext("setSearchPanelStatus",true);
	},
	hideSearchPanel : function(rule,params){
		var $this = pubsub.getJQObj(rule) ;
		$this.bmapext("setSearchPanelStatus",false);
	},
	//获取地图信息
	getMarkerAddress : function(rule,params){
		var $this = pubsub.getJQObj(rule,true) ;
		return $this.bmapext("getMarkerAddress");
	},
	//获取地点描述
	getAddressTitle : function(rule,params){
		var $this = pubsub.getJQObj(rule,true) ;
		return $this.bmapext("getAddressTitle");
	},
    getAddress : function(rule,params){
    	var $this = pubsub.getJQObj(rule,true) ;
		return $this.bmapext("getAddress")[rule.columnName];
    }
};
pubsub.sub("bmapext", bmapextFunc);