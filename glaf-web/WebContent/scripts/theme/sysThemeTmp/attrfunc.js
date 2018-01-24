var base_attr_func = {
	setDataRule : function($component,$style,prop,val){
		var styleData = null;
		if($style && $style[0]){
			styleData = $style.data("styleData") || {};
			styleData[prop] = val;
			$style.data("styleData",styleData);
		}else{
			styleData = $component.data("styleData") || {};
			styleData[prop] = val;
			$component.data("styleData",styleData);
		}
		

		base_attr_func.setCss($component,$style,styleData);
	},
	setCss : function($component,$style,styleData){
		var selectorExp = $style.data("selectorExp");
		if(!$style || !$style[0]){
			selectorExp = $component.data("selectorExp");
			var nodeData = $component.data("nodeData");
			if(!nodeData){
				return;
			}
			$style = $('#style_'+nodeData.treeType + nodeData.id);
		}
		
		$style.attr("changedStyle",true);
		//遍历样式属性,生成新的CSS
		var defaultCss = selectorExp + "{";
		$.each(styleData,function(key,value){
			defaultCss += key +":"+value + ";";
		})
		defaultCss += "}";

		$style.html(defaultCss);
		// 'style_"+data.treeType+data.controlId+"'
	},
	initDataRule : function($component,$style,styleData,CSS){

	},
	setPaddingTop : function($component,$style,val){
		base_attr_func.setPadding($component,$style,'top',val);
	},
	setPaddingBottom : function($component,$style,val){
		base_attr_func.setPadding($component,$style,'bottom',val);
	},
	setPaddingLeft : function($component,$style,val){
		base_attr_func.setPadding($component,$style,'left',val);
	},
	setPaddingRight : function($component,$style,val){

		base_attr_func.setPadding($component,$style,'right',val);
	},
	setPadding : function($component,$style,direct,val){
		if(val){
			val = val + "px";
		}
		var prop = "padding-"+direct;
		base_attr_func.setDataRule($component,$style,prop,val);
		// base_attr_func.setCss($component,$style,prop,val);
		// $component.css(prop,val);
	},
	setTextColor : function($component,$style,val){
		var prop = "color";
		base_attr_func.setDataRule($component,$style,prop,val);
	},
	setFontSize : function($component,$style,val){
		var prop = "font-size";
		if(val){
			val = val + 'px';
		}
		base_attr_func.setDataRule($component,$style,prop,val);
	},
	setFontFamily : function($component,$style,val){
		var prop = "font-family";
		
		base_attr_func.setDataRule($component,$style,prop,val);
	},
	setFontStyle : function($component,$style,val){
		var prop = "font-style";
		
		base_attr_func.setDataRule($component,$style,prop,val);	
	},
	setFontWeight : function($component,$style,val){
		var prop = "font-weight";
		
		base_attr_func.setDataRule($component,$style,prop,val);	
	},
	setBackgroundColor : function($component,$style,val){
		var prop = "background-color";
		
		base_attr_func.setDataRule($component,$style,prop,val);	
	},
	//设置边框样式
	setBorderTop : function($component,$style,val){
		base_attr_func.setBorder($component,$style,'top',val);
	},
	setBorderBottom : function($component,$style,val){
		base_attr_func.setBorder($component,$style,'bottom',val);
	},
	setBorderLeft : function($component,$style,val){
		base_attr_func.setBorder($component,$style,'left',val);
	},
	setBorderRight : function($component,$style,val){

		base_attr_func.setBorder($component,$style,'right',val);
	},
	setBorder : function($component,$style,direct,val){
		if(val){
			val = val + "px";
		}
		var prop = "border-"+direct+"-width";
		base_attr_func.setDataRule($component,$style,prop,val);
		// base_attr_func.setCss($component,$style,prop,val);
		// $component.css(prop,val);
	},
	setBorderStyle : function($component,$style,val){
		var prop = "border-style";
		
		base_attr_func.setDataRule($component,$style,prop,val);	
	},
	setBorderColor : function($component,$style,val){
		var prop = "border-color";
		
		base_attr_func.setDataRule($component,$style,prop,val);	
	}
}

var mtbutton_attr_func = {
	setDataRule : function($component,$style,prop,val){
		var styleData = null;
		if($style && $style[0]){
			styleData = $style.data("styleData") || {};
			styleData[prop] = val;
			$style.data("styleData",styleData);
		}else{
			styleData = $component.data("styleData") || {};
			styleData[prop] = val;
			$component.data("styleData",styleData);
		}
		

		mtbutton_attr_func.setCss($component,$style,styleData);
	},
	setCss : function($component,$style,styleData){
		var selectorExp = $style.data("selectorExp");
		if(!$style || !$style[0]){
			selectorExp = $component.data("selectorExp");
			var nodeData = $component.data("nodeData");
			if(!nodeData){
				return;
			}
			$style = $('#style_'+nodeData.treeType + nodeData.id);
		}
		
		$style.attr("changedStyle",true);
		//遍历样式属性,生成新的CSS
		var defaultCss = selectorExp + "{";
		//悬浮样式
		var hoverCss = "";
		var selectors = selectorExp.split(",");
		for(var i = 0 ; i < selectors.length ; i ++){
			if(i > 0){
				hoverCss += ","	;
			}
			hoverCss += selectors[i] + ":hover";
		}
		hoverCss += "{";
		$.each(styleData,function(key,value){
			if(key == 'border-radius'){
				if(value == 'zero'){
					value = '';
				}else if(value=='one'){
					value = "4px 4px 4px 4px !important";
				}else if(value=='two'){ 
					value = "8px 8px 8px 8px !important";
				}else if(value=='three'){
					value = "12px 12px 12px 12px !important";
				}else if(value=='four'){ 
					value = "16px 16px 16px 16px !important";
				}else if(value=='five'){ 
					value = "20px 20px 20px 20px !important";
				}
			}
			if(key == 'borderColorHover'){
				hoverCss += 'border-color:' + value + ";";
				return true;
			}
			if(key == 'backgroundColorHover'){
				hoverCss += 'background-color:' + value + ";";
				return true;
			}
			defaultCss += key +":"+value + ";";	
		})
		defaultCss += "}";
		hoverCss += "}";

		$style.html(defaultCss+hoverCss);
		// 'style_"+data.treeType+data.controlId+"'
	},
	initDataRule : function($component,$style,styleData,CSS){

	},
	setPaddingTop : function($component,$style,val){
		mtbutton_attr_func.setPadding($component,$style,'top',val);
	},
	setPaddingBottom : function($component,$style,val){
		mtbutton_attr_func.setPadding($component,$style,'bottom',val);
	},
	setPaddingLeft : function($component,$style,val){
		mtbutton_attr_func.setPadding($component,$style,'left',val);
	},
	setPaddingRight : function($component,$style,val){

		mtbutton_attr_func.setPadding($component,$style,'right',val);
	},
	setPadding : function($component,$style,direct,val){
		if(val){
			val = val + "px";
		}
		var prop = "padding-"+direct;
		mtbutton_attr_func.setDataRule($component,$style,prop,val);
		// mtbutton_attr_func.setCss($component,$style,prop,val);
		// $component.css(prop,val);
	},
	setTextColor : function($component,$style,val){
		var prop = "color";
		mtbutton_attr_func.setDataRule($component,$style,prop,val);
	},
	setFontSize : function($component,$style,val){
		var prop = "font-size";
		if(val){
			val = val + 'px';
		}
		mtbutton_attr_func.setDataRule($component,$style,prop,val);
	},
	setFontFamily : function($component,$style,val){
		var prop = "font-family";
		
		mtbutton_attr_func.setDataRule($component,$style,prop,val);
	},
	setFontStyle : function($component,$style,val){
		var prop = "font-style";
		
		mtbutton_attr_func.setDataRule($component,$style,prop,val);	
	},
	setFontWeight : function($component,$style,val){
		var prop = "font-weight";
		
		mtbutton_attr_func.setDataRule($component,$style,prop,val);	
	},
	setBackgroundColor : function($component,$style,val){
		var prop = "background-color";
		
		mtbutton_attr_func.setDataRule($component,$style,prop,val);	
	},
	//设置边框样式
	setBorderTop : function($component,$style,val){
		mtbutton_attr_func.setBorder($component,$style,'top',val);
	},
	setBorderBottom : function($component,$style,val){
		mtbutton_attr_func.setBorder($component,$style,'bottom',val);
	},
	setBorderLeft : function($component,$style,val){
		mtbutton_attr_func.setBorder($component,$style,'left',val);
	},
	setBorderRight : function($component,$style,val){

		mtbutton_attr_func.setBorder($component,$style,'right',val);
	},
	setBorder : function($component,$style,direct,val){
		if(val){
			val = val + "px";
		}
		var prop = "border-"+direct+"-width";
		mtbutton_attr_func.setDataRule($component,$style,prop,val);
		// mtbutton_attr_func.setCss($component,$style,prop,val);
		// $component.css(prop,val);
	},
	setBorderStyle : function($component,$style,val){
		var prop = "border-style";
		
		mtbutton_attr_func.setDataRule($component,$style,prop,val);	
	},
	setBorderColor : function($component,$style,val){
		var prop = "border-color";
		
		mtbutton_attr_func.setDataRule($component,$style,prop,val);	
	},
	setBorderRadius : function($component,$style,val){
		var prop = "border-radius";
		
		mtbutton_attr_func.setDataRule($component,$style,prop,val);	
	},
	setBorderColorHover : function($component,$style,val){
		var prop = "borderColorHover";
		
		mtbutton_attr_func.setDataRule($component,$style,prop,val);	
	},
	setBackgroundColorHover : function($component,$style,val){
		var prop = "backgroundColorHover";
		
		mtbutton_attr_func.setDataRule($component,$style,prop,val);	
	},
}


var definedpanel_attr_func = {
	setDataRule : function($component,$style,prop,val){
		var styleData = null;
		if($style && $style[0]){
			styleData = $style.data("styleData") || {};
			styleData[prop] = val;
			$style.data("styleData",styleData);
		}else{
			styleData = $component.data("styleData") || {};
			styleData[prop] = val;
			$component.data("styleData",styleData);
		}
		

		base_attr_func.setCss($component,$style,styleData);
	},
	setCss : function($component,$style,styleData){
		var selectorExp = $style.data("selectorExp");
		if(!$style || !$style[0]){
			selectorExp = $component.data("selectorExp");
			var nodeData = $component.data("nodeData");
			if(!nodeData){
				return;
			}
			$style = $('#style_'+nodeData.treeType + nodeData.id);
		}
		
		$style.attr("changedStyle",true);
		//遍历样式属性,生成新的CSS
		var defaultCss = selectorExp + "{";
		$.each(styleData,function(key,value){
			defaultCss += key +":"+value + ";";
		})
		defaultCss += "}";

		$style.html(defaultCss);
		// 'style_"+data.treeType+data.controlId+"'
	},
	initDataRule : function($component,$style,styleData,CSS){

	},
	setPaddingTop : function($component,$style,val){
		base_attr_func.setPadding($component,$style,'top',val);
	},
	setPaddingBottom : function($component,$style,val){
		base_attr_func.setPadding($component,$style,'bottom',val);
	},
	setPaddingLeft : function($component,$style,val){
		base_attr_func.setPadding($component,$style,'left',val);
	},
	setPaddingRight : function($component,$style,val){

		base_attr_func.setPadding($component,$style,'right',val);
	},
	setPadding : function($component,$style,direct,val){
		if(val){
			val = val + "px";
		}
		var prop = "padding-"+direct;
		base_attr_func.setDataRule($component,$style,prop,val);
		// base_attr_func.setCss($component,$style,prop,val);
		// $component.css(prop,val);
	},
	setTextColor : function($component,$style,val){
		var prop = "color";
		base_attr_func.setDataRule($component,$style,prop,val);
	},
	setFontSize : function($component,$style,val){
		var prop = "font-size";
		if(val){
			val = val + 'px';
		}
		base_attr_func.setDataRule($component,$style,prop,val);
	}
}