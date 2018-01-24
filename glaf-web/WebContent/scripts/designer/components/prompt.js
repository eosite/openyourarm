/**
 * prompt
 */
var prompt_designer = {
    /**
     * 设置控件名称
     */
    setName: function(component, val) {
//        var $this = prompt_designer.getMainObject(component);
    	var $component = $(component);
    	if (val !== '') {
            var prop = "name";
            prompt_designer.setDataRule(component, prop, val);
            $component.attr("cname", val);
        }
    },


    /**
     * 设置data-rule
     */
	setDataRule: function(component, prop, val,unit) {
//	    var lastVal = prompt_designer.getDataRule(component, prop)
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
    
   
};