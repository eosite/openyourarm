var btmessenger_designer = {
	/**
	 * 设置控件名称
	 */
	setName: function (component, val){
		if(val!==''){
			var prop = "name"; 
			btmessenger_designer.setDataRule(component,prop,val);
			component.attr("cname", val);
		}
	},
	setContent: function (component, val){
		var prop = "message"; 
		btmessenger_designer.setDataRule(component,prop,val);
		component.attr('data-msg', val);
	},
	setTheme: function (component, val){\
		var prop = "dataTheme"; 
		btmessenger_designer.setDataRule(component,prop,val);
		component.attr('data-theme', val);
	},
	setXposition: function (component, val){
		var prop = "xPosition"; 
		btmessenger_designer.setDataRule(component,prop,val);
		component.attr('x-position', val);
	},
	setYposition: function (component, val){
		var prop = "yPosition"; 
		btmessenger_designer.setDataRule(component,prop,val);
		component.attr('y-position', val);
	},
	save: function (component, val){
		component.on('click.button.checker', 'button.checker', function(e){
			Messenger.options = {
				extraClasses: 'messenger-fixed messenger-on-'+component.attr('y-position')+' messenger-on-'+component.attr('x-position'),
				theme: component.attr('data-theme')
			};
			Messenger().post(component.attr('data-msg'));
		});
	},
	setDataRule : function(component,prop,val){
        var lastVal = btmessenger_designer.getDataRule(component,prop)
        
        if(component.attr("data-rule")){
            var data_rule = JSON.parse(component.attr("data-rule"));
            data_rule[prop]=val;
        }else{
            var data_rule={};
            data_rule[prop]=val;
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
    }
};