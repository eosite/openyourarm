/**
 * sidebar
 */
var sidebar_designer = {
    /**
     * 设置控件名称
     */
    setName: function(component, val) {
        var $this = sidebar_designer.getMainObject(component);
    	if (val !== '') {
            var prop = "name";
            sidebar_designer.setDataRule(component, prop, val);
            component.attr("cname", val);
        }
    },
    /**
     * 获取操作控件对象
     */
    getMainObject: function(component) {

        return component.find("div:first");

    },
    /**
     * 获取操作控件对象
     */
    getObject: function(component) {

        //return component.find("div:nth-child(2)");
        return component.find($(".imgres"));
    },
    getObjectEdit: function(component) {
    	
    	//return component.find("div:nth-child(2)");
    	return component.find($(".editHandle"));
    },
    getObjectCom: function(component) {
    	
    	return component.find($(".imsive"));
    },

    /**
     * 设置data-rule
     */
	setDataRule: function(component, prop, val,unit) {
	    var lastVal = sidebar_designer.getDataRule(component, prop)
	    if (component.attr("data-rule")) {
	        var data_rule = JSON.parse(component.attr("data-rule"));
	        data_rule[prop] = val;
	    } else {
	        var data_rule = {};
	        data_rule[prop] = val;
	    }
	    if(unit!=undefined){
	        data_rule[prop+"_unit"]=unit;
	    }
	    component.attr("data-rule", JSON.stringify(data_rule));
	    return lastVal;
	},
	getDataRule: function(component, prop) {
	    var value = "";
	    if (component.attr("data-rule")) {
	        value = JSON.parse(component.attr("data-rule"))[prop];
	    }
	    return value;
	},
    
    /**
     * 设置边框宽度
     */
    setBorderWidth: function(component, val, unit) {
    	var $this = sidebar_designer.getMainObject(component);
    	var prop = "borderWidth";
        sidebar_designer.setDataRule(component, prop, val, unit);
        if("" == val){
        	$this.css('border-width',0);
        }else {
        	$this.css('border-width',val);
        }
    },
    getBorderWidth: function(component, val) {
    	var $this = sidebar_designer.getMainObject(component);
    	return $this.css("border-width");
    },
    
    /**
     * 设置宽度
     */
    setWidth: function(component, val,unit) {
    	var $this = sidebar_designer.getMainObject(component);
    	var prop = "width";
        sidebar_designer.setDataRule(component, prop, val);
        if("" == val){
        	$this.width("20%");
        }else {
        	$this.width(val+unit);
        }
    },
    
    /**
	 * 设置高度
	 */
	setHeight : function(component, val,unit) {
		var $this = sidebar_designer.getMainObject(component);
		var prop = "height";
		sidebar_designer.setDataRule(component,prop,val);
		if (val == '') {
			$this.height("20%");
		} else {
			$this.height(val+unit);
		}
	},
    
	/**
	 * 设置堆叠
	 */
	setZindex:function (component,val){
		var $this = sidebar_designer.getMainObject(component);
		var prop = "zindex";
		sidebar_designer.setDataRule(component,prop,val);
		$this.css("z-index",val);
	},
	
	
	/**
     * 设置图标宽度
     */
    setWidthin: function(component, val) {
    	var $this = sidebar_designer.getObject(component);
    	var prop = "widthin";
        sidebar_designer.setDataRule(component, prop, val);
        if("" == val){
        	$this.css("border-width","20px");
        }else {
        	$this.css("border-width",val);
        }
    },
    
    /**
	 * 设置图标高度
	 */
	setHeightin : function(component, val) {
		var $this = sidebar_designer.getObject(component);
		var prop = "heightin";
		sidebar_designer.setDataRule(component,prop,val);
		if (val == '') {
			$this.outerHeight("auto");
		} else {
			$this.outerHeight(val);
		}
	},
	/**
	 * 设置图标上下左右边框
	 */
	 setBordetop: function(component, val){
	    	var $this = sidebar_designer.getObject(component);
	    	var prop = "bordetop";
	    	sidebar_designer.setDataRule(component, prop, val);
	    	sidebar_designer.setBorderWidth(component, 'top', val);
	    	
	    },
	    
	    setBordeleft: function(component, val){
	    	var $this = sidebar_designer.getObject(component);
	    	var prop = "bordeleft";
	    	sidebar_designer.setDataRule(component, prop, val);
	    	sidebar_designer.setBorderWidth(component, 'left', val);
	    	
	    	
	    },
	    setBordebottom: function(component, val){
	    	var $this = sidebar_designer.getObject(component);
	    	var prop = "bordebottom";
	    	sidebar_designer.setDataRule(component, prop, val);
	    	sidebar_designer.setBorderWidth(component, 'bottom', val);
	    	
	    	
	    },
	    
	    setBorderWidth:function(component,direct,val){
	    	var obj=sidebar_designer.getObject(component);
	    	   obj.css("border-"+direct+"-width",val+"px");
	    	
	    },
	    
	    /**
		 * 设计图标左边颜色
		 */
	    setBackedColor: function(component,val){
		    	var $this = sidebar_designer.getObject(component);
		    	var prop = "backedColor";
		    	sidebar_designer.setDataRule(component, prop, val);
		    	if(val==''){
		    		$this.css('border-left-color',"red");

		    	}else{
		    		
		    		$this.css('border-left-color',val);
		    	}
		    	
		    },
	/**
     * 设置小图标宽度
     */
    setWidthinod: function(component, val) {
    	var $this = sidebar_designer.getObjectCom(component);
    	var prop = "widthinod";
        sidebar_designer.setDataRule(component, prop, val);
        if("" == val){
        	$this.css("border-width","5px");
        }else {
        	$this.css("border-width",val);
        }
    },
    
    /**
	 * 设置小图标高度
	 */
	setHeightinod : function(component, val) {
		var $this = sidebar_designer.getObjectCom(component);
		var prop = "heightinod";
		sidebar_designer.setDataRule(component,prop,val);
		if (val == '') {
			$this.outerHeight("auto");
		} else {
			$this.outerHeight(val);
		}
	},
	/**
	 * 设计小图标颜色
	 */
	 setBackColor: function(component, val){
	    	var $this = sidebar_designer.getObjectCom(component);
	    	var prop = "backColor";
	    	sidebar_designer.setDataRule(component, prop, val);
	    	if(val == ''){
	    		$this.css('border-left-color',"red");
	    	}else{
	    		
	    		$this.css('border-left-color',val);
	    	}
	    	
	    },
	    setBordertop: function(component, val){
	    	debugger
	    	var $this = sidebar_designer.getObjectCom(component);
	    	var prop = "bordertop";
	    	sidebar_designer.setDataRule(component, prop, val);
	    	sidebar_designer.setBordWidth(component, 'top', val);
	    },
	    setBorderleft: function(component, val){
	    	var $this = sidebar_designer.getObjectCom(component);
	    	var prop = "borderleft";
	    	sidebar_designer.setDataRule(component, prop, val);
	    	sidebar_designer.setBordWidth(component, 'left', val);
	    },
	    setBorderbottom: function(component, val){
	    	var $this = sidebar_designer.getObjectCom(component);
	    	var prop = "borderbottom";
	    	sidebar_designer.setDataRule(component, prop, val);
	    	sidebar_designer.setBordWidth(component, 'bottom', val);
	    	
	    },
	
	    setBordWidth:function(component,direct,val){
	    	var obj=sidebar_designer.getObjectCom(component);
	    	if(val==''){
	    	   obj.css("border-"+direct+"-width",'5px');
	    	}else{
	    	   obj.css("border-"+direct+"-width",val+"px");
	    	}
	    },
	    
	
    getBorderWidth: function(component, val) {
    	var $this = sidebar_designer.getMainObject(component);
    	return $this.css("border-width");
    },
    
   
	 /**
     * 设置透明度
     */
    setOpacity : function(component, val) {
    	var $this = sidebar_designer.getMainObject(component);
    	var prop = "opacity";
    	if(val){
			val = val/100;
		}
    	sidebar_designer.setDataRule(component,prop,val);
		if(val==0){
			$this.css('opacity',0.8);
		}else{
			$this.css('opacity',val);
		}
    },
   
   
    
    /**
     * 设置边框颜色
     */
    setBorderColor: function(component, val){
    	var $this = sidebar_designer.getMainObject(component);
    	var prop = "borderColor";
    	sidebar_designer.setDataRule(component, prop, val);
    	$this.css('border-color',val);
    },
    setBackgroundColor: function(component, val){
    	var $this = sidebar_designer.getMainObject(component);
    	var prop = "backgroundColor";
    	sidebar_designer.setDataRule(component, prop, val);
    	$this.css('background-color',val);
    },
    
    /**
     *设置内间距
     */
    setPaddingTop: function(component, val) {
        var prop = "paddingTop";
        sidebar_designer.setDataRule(component, prop, val);
        sidebar_designer.setPadding(component, "top", val);
    },
    setPaddingBottom: function(component, val) {
        var prop = "paddingBottom";
        sidebar_designer.setDataRule(component, prop, val);
        sidebar_designer.setPadding(component, "bottom", val);
    },
    setPaddingLeft: function(component, val) {
        var prop = "paddingLeft";
        sidebar_designer.setDataRule(component, prop, val);
        sidebar_designer.setPadding(component, "left", val);
    },
    setPadding: function(component, direct, val) {
    	var portletBody = sidebar_designer.getMainObject(component);
        if (val == '') {
            portletBody.css("padding-" + direct, "");
        } else {
            portletBody.css("padding-" + direct, val + "px");
        }
    },
};