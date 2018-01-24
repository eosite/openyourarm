var rangeSlider_designer = {
		
		/**
		 * 设置控件名称
		 */
		setName : function(component, val) {
			var $component = $(component);
			var prop = "name";
			rangeSlider_designer.setDataRule(component,prop,val);
			if (val == '') {
				$component.attr("cname", "");
			} else {
				$component.attr("cname", val);
			}
		},
		/**
		 * 设置外间距
		 */
		setMarginTop : function(component, val) {
			var prop = "marginTop";
			rangeSlider_designer.setDataRule(component,prop,val);
			rangeSlider_designer.setMargin(component, "top", val);
		},
		setMarginBottom : function(component, val) {
			var prop = "marginBottom";
			rangeSlider_designer.setDataRule(component,prop,val);
			rangeSlider_designer.setMargin(component, "bottom", val);
		},
		setMarginLeft : function(component, val) {
			var prop = "marginLeft";
			rangeSlider_designer.setDataRule(component,prop,val);
			rangeSlider_designer.setMargin(component, "left", val);
		},
		setMarginRight : function(component, val) {
			var prop = "marginRight";
			rangeSlider_designer.setDataRule(component,prop,val);
			rangeSlider_designer.setMargin(component, "right", val);
		},
		setMargin : function(component, direct, val) {
			if (val == '') {
				component.css("margin-" + direct, "");
			} else {
				component.css("margin-" + direct, val + "px");
			}
		},

		/**
		 * 设置位置
		 */
		setPositionTop : function(component, val) {
			var prop = "top";
			rangeSlider_designer.setDataRule(component,prop,val);
			rangeSlider_designer.setPositon(component, 'top', val);
		},
		setPositionBottom : function(component, val) {
			var prop = "bottom";
			rangeSlider_designer.setDataRule(component,prop,val);
			rangeSlider_designer.setPositon(component, 'bottom', val);
		},
		setPositionLeft : function(component, val) {
			var prop = "left";
			rangeSlider_designer.setDataRule(component,prop,val);
			rangeSlider_designer.setPositon(component, 'left', val);
		},
		setPositionRight : function(component, val) {
			var prop = "right";
			rangeSlider_designer.setDataRule(component,prop,val);
			rangeSlider_designer.setPositon(component, 'right', val);
		},
		setPositon : function(component, direct, val) {
			component.css("position", "relative");
			if (val == '') {
				component.css(direct, '');
			} else {
				component.css(direct, val + "px");
			}
		},
		/**
		 * 设置定位方式
		 */
		setPositonStyle : function(component, val) {
			var prop = "position";
			rangeSlider_designer.setDataRule(component,prop,val);
			component.css("position", val);
		},

		/**
		 * 设置宽度
		 */
		setWidth : function(component, val) {
			var prop = "width";
			rangeSlider_designer.setDataRule(component,prop,val);
			if (val == '') {
				component.css("width", '');
			} else {
				component.css("width", val + "px");
			}

		},
		/**
		 *设置背景色
		 */
		setBackgroundColor: function(component, val) {
			var prop = "backgroundColor";
			rangeSlider_designer.setDataRule(component,prop,val);
			if (val == '') {
				component.find(".progress-bar").css("background-color", '');
			} else {
				component.find(".progress-bar").css("background-color", val);
			}
		},
		/**
		 * 设置高度
		 */
		setHeight : function(component, val) {
			var prop = "height";
			rangeSlider_designer.setDataRule(component,prop,val);
			if (val == '') {
				component.css("height", '');
				//设置line-height
				component.find('.progress-bar').css("line-height",'');
				component.find('.progress_remain').css("line-height",'20px');
			} else {
				component.css("height", val + "px");
				component.find('.progress-bar').css("line-height",val + "px");
				component.find('.progress_remain').css("line-height",val + "px");
			}
		},

		/**
		 * 设置主题
		 * 
		 * @param {}
		 *            component
		 * @param {}
		 *            val
		 */
		setTheme : function(component, val) {
			var prop = "theme";
			rangeSlider_designer.setDataRule(component,prop,val);
			var obj = component.find(".progress-bar");
			if (val !== "") {
				obj.removeClass("progress-bar-success progress-bar-info progress-bar-warning progress-bar-danger");
				obj.addClass(val);
			}
		},
		/**
		 * 设置透明度
		 */
		setTransparency : function(component, val) {
			var prop = "transparency";
			rangeSlider_designer.setDataRule(component,prop,val);
			if (val == '') {
				component.css("filter", "");
				component.css("-moz-opacity", "");
				component.css("-khtml-opacity", "");
				component.css("opacity", "");
			} else {
				component.css("filter", "alpha(opacity=" + val + ")");
				component.css("-moz-opacity", val / 100);
				component.css("-khtml-opacity", val / 100);
				component.css("opacity", val / 100);
			}
		},
		setDataRule : function(component,prop,val){
			
			var lastVal = rangeSlider_designer.getDataRule(component,prop)
			
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
			
			//debugger;
			var value ="";
			if(component.attr("data-rule")){
				value = JSON.parse(component.attr("data-rule"))[prop];
			}
			
			return value;
		},

		/**
		 * 设置字体
		 */
		setFontFamily : function(component, val) {
			var prop = "fontFamily";
			rangeSlider_designer.setDataRule(component,prop,val);
			var obj = component.find("span");
			if (val == '') {
				obj.css("font-family", '');
			} else {
				obj.css("font-family", val);
			}
		},

		setFontStyle : function(component, val) {
			var prop = "fontStyle";
			rangeSlider_designer.setDataRule(component,prop,val);
			var obj = component.find("span");
			if (val == 'italic') {
				if ($(this).hasClass('active')) {
					obj.css("font-style", 'italic');
				} else {
					obj.css("font-style", '');
				}
			} else if (val == 'bold') {
				if ($(this).hasClass('active')) {
					obj.css("font-weight", 'bold');
				} else {
					obj.css("font-weight", '');
				}
			}
		},

		/**
		 * 设置字体大小
		 */
		setFontSize : function(component, val) {
			var prop = "fontSize";
			rangeSlider_designer.setDataRule(component,prop,val);
			var obj = component.find("span");
			if (val == '') {
				obj.css("font-size", '');
			} else {
				obj.css("font-size", val + "px");
			}
		},

		//字体间距
		setLetterSpacing : function(component, val) {
			var prop = "spacing";
			rangeSlider_designer.setDataRule(component,prop,val);
			var obj = component.find("span");
			if (val == '') {
				obj.css("letter-spacing", '');
			} else {
				obj.css("letter-spacing", val + "px");
			}
		}, 

		//字体颜色
		setLeftFontColor : function(component, val) {
			var prop = "leftFontColor";
			rangeSlider_designer.setDataRule(component,prop,val);
			var obj = component.find(".progress-bar span");
			if (val == '') {
				obj.css("color", '');
			} else {
				obj.css("color", val);
			}
		}, 

		setRightFontColor : function(component, val) {
			var prop = "rightFontColor";
			rangeSlider_designer.setDataRule(component,prop,val);
			var obj = component.find(".progress_remain span");
			if (val == '') {
				obj.css("color", '');
			} else {
				obj.css("color", val);
			}
		}, 

		//字体种类
		setFontFamily : function(component, val) {
			var prop = "fontFamily";
			rangeSlider_designer.setDataRule(component,prop,val);
			var obj = component.find("span");
			if (val == '') {
				obj.css("font-family", '');
			} else {
				obj.css("font-family", val);
			}
		}, 
		
}