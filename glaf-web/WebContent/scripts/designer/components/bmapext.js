var bmapext_designer = {
	getMainObject:function (component){

		return component;

	},

	/**
	 * 设置控件名称
	 */
	setName: function (component, val){
		if(val!==''){
			var prop = "name"; 
			bmapext_designer.setDataRule(component,prop,val);
			component.attr("cname", val);
		}else{
			component.attr("cname", "");
		}
	},

	setDataRule : function(component,prop,val,unit){
        var lastVal = bmapext_designer.getDataRule(component,prop)

		if(component.attr("data-rule")){

			var data_rule = JSON.parse(component.attr("data-rule"));

			data_rule[prop]=val;

		}else{

			var data_rule={};

			data_rule[prop]=val;

		}

		if(unit!=undefined){

			data_rule[prop+"_unit"]=unit;

		}

		component.attr("data-rule",JSON.stringify(data_rule));

		

		return lastVal;
    },
    getDataRule : function(component,prop){
        var value ="";
        if(component.attr("data-rule")){
            value = JSON.parse(component.attr("data-rule"))[prop];
        }
        return value;
    },

};
// var dhxgantt_proto = Object.create(bmapext_designer);