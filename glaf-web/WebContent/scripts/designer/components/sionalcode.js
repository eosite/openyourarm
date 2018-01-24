/**
 * sionalcode
 */
var sionalcode_designer = {
    /**
     * 设置控件名称
     */
    setName: function(component, val) {
//        var $this = sionalcode_designer.getMainObject(component);
    	var $component = $(component);
    	if (val !== '') {
            var prop = "name";
            sionalcode_designer.setDataRule(component, prop, val);
            $component.attr("cname", val);
        }
    },


    /**
     * 设置data-rule
     */
	setDataRule: function(component, prop, val,unit) {
//	    var lastVal = sionalcode_designer.getDataRule(component, prop)
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
//    	var $this = sionalcode_designer.getMainObject(component);
    	var prop = "borderWidth";
    	sionalcode_designer.setDataRule(component, prop, val, unit);
        if("" == val){
        	component.css('border-width',0);
        }else {
        	component.css('border-width',val);
        }
    },
    getBorderWidth: function(component, val) {
    	var $this = sionalcode_designer.getMainObject(component);
    	return component.css("border-width");
    },
    
    /**
     * 设置宽度
     */
    setWidth: function(component, val) {
//    	var $this = sionalcode_designer.getMainObject(component);
    	var prop = "width";
    	sionalcode_designer.setDataRule(component, prop, val);
        if("" == val){
        	component.width("auto");
        }else {
        	component.width(val);
        }
    },
    
    /**
	 * 设置高度
	 */
	setHeight : function(component, val) {
//		var $this = sionalcode_designer.getMainObject(component);
		var prop = "height";
		sionalcode_designer.setDataRule(component,prop,val);
		if (val == '') {
			component.height("auto");
		} else {
			component.height(val);
		}
	},
	/**
	 * 设置匹配度
	 */
//	setMatch : function(component, val) {
//		var $this = sionalcode_designer.getMainObject(component);
//		var prop = "match";
//		sionalcode_designer.setDataRule(component,prop,val);
////		if (val == '') {
////			$this.match("auto");
////		} else {
////			$this.match(val);
////		}
//	},
//    
    getBorderWidth: function(component, val) {
    	//var $this = sionalcode_designer.getMainObject(component);
    	return component.css("border-width");
    },
    
    
    
   
};