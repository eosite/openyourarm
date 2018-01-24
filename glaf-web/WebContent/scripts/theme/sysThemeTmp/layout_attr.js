var layout_attr =  [{
	"id": "color",
	"label":"字体颜色",
	"title": "颜色",
	"type": "string",
	"defaultVal": "",
	"inputMode": "colorpicker",
	"require": "false",
	"readonly": "false",
	"ctrImpl": "setTextColor"
},{
	"id": "fontSize",
	"title": "字体大小",
	"inputMode": "touchspin",
	"properties":[{
		"id": "font-size",
		"title": "字体大小",
		"icon":"font",
		"type": "int",
		"width":"100px",
		"maxval":100,
	    "minval":0,
		"defaultVal": "",
		"inputMode": "touchspin",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setFontSize"
	}]
}]


var base_attr = [{
	"id": "padding",
	"label": "内间距",
	"title": "内间距",
	"inputMode": "touchspin",
	"properties": [{
		"id": "padding-top",
		"title": "上",
		"type": "int",
		"width":"100px",
		"maxval":"2000",
	    "minval":"-2000",
		"defaultVal": "",
		"inputMode": "touchspin",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setPaddingTop"
	},
	{
		"id": "padding-bottom",
		"title": "下",
		"type": "int",
		"width":"100px",
	    "maxval":"2000",
	    "minval":"-2000",
		"defaultVal": "",
		"inputMode": "touchspin",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setPaddingBottom"
	},
	{
		"id": "padding-left",
		"title": "左",
		"type": "int",
		"width":"100px",
	    "maxval":"2000",
	    "minval":"-2000",
		"defaultVal": "",
		"inputMode": "touchspin",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setPaddingLeft"
	},
	{
		"id": "padding-right",
		"title": "右",
		"type": "int",
		"width":"100px",
	    "maxval":"2000",
	    "minval":"-2000",
		"defaultVal": "",
		"inputMode": "touchspin",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setPaddingRight"
	}]
},{
	"id": "color",
	"label":"字体颜色",
	"title": "颜色",
	"type": "string",
	"defaultVal": "",
	"inputMode": "colorpicker",
	"require": "false",
	"readonly": "false",
	"ctrImpl": "setTextColor"
},{
	"id": "fontSize",
	"title": "字体大小",
	"inputMode": "touchspin",
	"properties":[{
		"id": "font-size",
		"title": "字体大小",
		"icon":"font",
		"type": "int",
		"width":"100px",
		"maxval":100,
	    "minval":0,
		"defaultVal": "",
		"inputMode": "touchspin",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setFontSize"
	}]
},{
	"id": "font-weight",
	"title": "字体加粗",
	"type": "string",
	"defaultVal": "",
	"inputMode": "buttongroup",
	"require": "false",
	"readonly": "false",
	"ctrImpl": "setFontWeight",
	"valrange": [{
		"title": "字体加粗",
		"value": "bold",
		"icon":"bold"
	}]
},{
	"id": "font-style",
	"title": "字体样式",
	"type": "string",
	"defaultVal": "",
	"inputMode": "buttongroup",
	"require": "false",
	"readonly": "false",
	"ctrImpl": "setFontStyle",
	"valrange": [{
		"title": "斜体",
		"value": "italic",
		"icon":"italic"
	}]
},{
	"id": "background-color",
	"label":"背景颜色",
	"title": "颜色",
	"type": "string",
	"defaultVal": "",
	"inputMode": "colorpicker",
	"require": "false",
	"readonly": "false",
	"ctrImpl": "setBackgroundColor"
},{
	"id": "border",
	"label": "边框",
	"title": "边框",
	"inputMode": "touchspin",
	"properties": [{
		"id": "border-top-width",
		"title": "上",
		"type": "int",
		"width":"100px",
		"maxval":"2000",
	    "minval":"-2000",
		"defaultVal": "",
		"inputMode": "touchspin",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setBorderTop"
	},
	{
		"id": "border-bottom-width",
		"title": "下",
		"type": "int",
		"width":"100px",
	    "maxval":"2000",
	    "minval":"-2000",
		"defaultVal": "",
		"inputMode": "touchspin",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setBorderBottom"
	},
	{
		"id": "border-left-width",
		"title": "左",
		"type": "int",
		"width":"100px",
	    "maxval":"2000",
	    "minval":"-2000",
		"defaultVal": "",
		"inputMode": "touchspin",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setBorderLeft"
	},
	{
		"id": "border-right-width",
		"title": "右",
		"type": "int",
		"width":"100px",
	    "maxval":"2000",
	    "minval":"-2000",
		"defaultVal": "",
		"inputMode": "touchspin",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setBorderRight"
	}]
},{
	"id": "border-style",
	"label":"边框颜色",
	"title": "边框样式",
	"type": "string",
	"maxlen": "50",
	"minlen": "5",
	"defaultVal": "left",
	"inputMode": "buttongroup",
	"require": "false",
	"readonly": "false",
	"ctrImpl": "setBorderStyle",
	"valrange": [{
	    "name":"无",
		"title": "无边框",
		"value": "none"
	},
	{
		"name":"—",
		"title": "实线框",
		"value": "solid"
	},{
	    "name":"--",
		"title": "虚线框",
		"value": "dashed"
	},{
	    "name":"...",
		"title": "点线框",
		"value": "dotted"
	}]
},{
	"id": "border-color",
	"title": "颜色",
	"type": "string",
	"defaultVal": "",
	"inputMode": "colorpicker",
	"require": "false",
	"readonly": "false",
	"ctrImpl": "setBorderColor"
}]

var mtbutton_attr = [{
	"type":"label",
	"value":"内间距",
	"inputMode":"label",
	"isParent":true,
	"children":[{
		"id": "padding",
		"title": "内间距",
		"inputMode": "touchspin",
		"properties": [{
			"id": "padding-top",
			"title": "上",
			"type": "int",
			"width":"100px",
			"maxval":"2000",
		    "minval":"-2000",
			"defaultVal": "",
			"inputMode": "touchspin",
			"require": "false",
			"readonly": "false",
			"ctrImpl": "setPaddingTop"
		},
		{
			"id": "padding-bottom",
			"title": "下",
			"type": "int",
			"width":"100px",
		    "maxval":"2000",
		    "minval":"-2000",
			"defaultVal": "",
			"inputMode": "touchspin",
			"require": "false",
			"readonly": "false",
			"ctrImpl": "setPaddingBottom"
		},
		{
			"id": "padding-left",
			"title": "左",
			"type": "int",
			"width":"100px",
		    "maxval":"2000",
		    "minval":"-2000",
			"defaultVal": "",
			"inputMode": "touchspin",
			"require": "false",
			"readonly": "false",
			"ctrImpl": "setPaddingLeft"
		},
		{
			"id": "padding-right",
			"title": "右",
			"type": "int",
			"width":"100px",
		    "maxval":"2000",
		    "minval":"-2000",
			"defaultVal": "",
			"inputMode": "touchspin",
			"require": "false",
			"readonly": "false",
			"ctrImpl": "setPaddingRight"
		}]
	}]
},{
	"type":"label",
	"isParent":true,
	"value":"字体",
	"children":[{
		"id": "font-family",
		"title": "字体",
		"type": "string",
		"maxlen": "50",
		"minlen": "5",
		"width":"146px",
		"defaultVal": "宋体",
		"inputMode": "select",
		"valrange": [{
			"title": "Microsoft YaHei",
			"value": "Microsoft YaHei"
		},{	
			"title": "Arial",
			"value": "Arial"
		},{
			"title": "Glyphicons Halflings",
			"value": "Glyphicons Halflings"
		},{
			"title": "Consolas",
			"value": "Consolas"
		},{
			"title": "Courier New",
			"value": "Courier New"
		},
		{	
			"title": "宋体",
			"value": "SimSun"
		}],
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setFontFamily"
	},{
		"id": "fontSize",
		"title": "字体大小",
		"inputMode": "touchspin",
		"properties":[{
			"id": "font-size",
			"title": "字体大小",
			"icon":"font",
			"type": "int",
			"width":"80px",
			"maxval":100,
		    "minval":0,
			"defaultVal": "",
			"inputMode": "touchspin",
			"require": "false",
			"readonly": "false",
			"ctrImpl": "setFontSize"
		}]
	},{
		"id": "font-weight",
		"title": "字体加粗",
		"type": "string",
		"defaultVal": "",
		"inputMode": "buttongroup",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setFontWeight",
		"valrange": [{
			"title": "字体加粗",
			"value": "bold",
			"icon":"bold"
		}]
	},{
		"id": "font-style",
		"title": "字体样式",
		"type": "string",
		"defaultVal": "",
		"inputMode": "buttongroup",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setFontStyle",
		"valrange": [{
			"title": "斜体",
			"value": "italic",
			"icon":"italic"
		}]
	},{
		"id": "color",
		"title": "字体颜色",
		"type": "string",
		"defaultVal": "",
		"inputMode": "colorpicker",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setTextColor"
	}]
},{
	"type":"label",
	"value":"背景",
	"isParent":true,
	"children":[{
		"id": "background-color",
		"title": "背景颜色",
		"type": "string",
		"defaultVal": "",
		"inputMode": "colorpicker",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setBackgroundColor"
	}]
},{
	"type":"label",
	"value":"边框",
	"isParent":true,
	"children":[{
		"id": "border",
		"title": "边框框",
		"inputMode": "touchspin",
		"properties": [{
			"id": "border-top-width",
			"title": "上",
			"type": "int",
			"width":"100px",
			"maxval":"2000",
		    "minval":"-2000",
			"defaultVal": "",
			"inputMode": "touchspin",
			"require": "false",
			"readonly": "false",
			"ctrImpl": "setBorderTop"
		},
		{
			"id": "border-bottom-width",
			"title": "下",
			"type": "int",
			"width":"100px",
		    "maxval":"2000",
		    "minval":"-2000",
			"defaultVal": "",
			"inputMode": "touchspin",
			"require": "false",
			"readonly": "false",
			"ctrImpl": "setBorderBottom"
		},
		{
			"id": "border-left-width",
			"title": "左",
			"type": "int",
			"width":"100px",
		    "maxval":"2000",
		    "minval":"-2000",
			"defaultVal": "",
			"inputMode": "touchspin",
			"require": "false",
			"readonly": "false",
			"ctrImpl": "setBorderLeft"
		},
		{
			"id": "border-right-width",
			"title": "右",
			"type": "int",
			"width":"100px",
		    "maxval":"2000",
		    "minval":"-2000",
			"defaultVal": "",
			"inputMode": "touchspin",
			"require": "false",
			"readonly": "false",
			"ctrImpl": "setBorderRight"
		}]
	},{
		"id": "border-style",
		"title": "边框样式",
		"type": "string",
		"maxlen": "50",
		"minlen": "5",
		"defaultVal": "left",
		"inputMode": "buttongroup",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setBorderStyle",
		"valrange": [{
		    "name":"无",
			"title": "无边框",
			"value": "none"
		},
		{
			"name":"—",
			"title": "实线框",
			"value": "solid"
		},{
		    "name":"--",
			"title": "虚线框",
			"value": "dashed"
		},{
		    "name":"...",
			"title": "点线框",
			"value": "dotted"
		}]
	},{
		"id": "border-color",
		"title": "颜色",
		"type": "string",
		"defaultVal": "",
		"inputMode": "colorpicker",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setBorderColor"
	},{
		"id": "border-radius",
		"title": "弧度等级",
		"type": "string",
		"maxlen": "50",
		"minlen": "5",
		"defaultVal": "一级",
		"inputMode": "select",
		"valrange": [{
			"title": "无",
			"value": "zero"
		},{
			"title": "一级",
			"value": "one"
		},{	
			"title": "二级",
			"value": "two"
		},{
			"title": "三级",
			"value": "three"
		},{
			"title": "四级",
			"value": "four"
		},{
			"title": "五级",
			"value": "five"
		}],
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setBorderRadius"
	}]
},{
	"type":"label",
	"value":"悬浮",
	"isParent":true,
	"children":[{
		"id": "borderColorHover",
		"title": "悬浮",
		"addonText":"边框",
		"type": "string",
		"defaultVal": "",
		"inputMode": "colorpicker",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setBorderColorHover"
	},{
		"id": "backgroundColorHover",
		"addonText":"背景",
		"type": "string",
		"defaultVal": "",
		"inputMode": "colorpicker",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setBackgroundColorHover"
	}]
}]

var theme_attr = [{
	"type":"label",
	"isParent":true,
	"value":"字体",
	"children":[{
		"id": "font-family",
		"title": "字体",
		"type": "string",
		"maxlen": "50",
		"minlen": "5",
		"width":"146px",
		"defaultVal": "宋体",
		"inputMode": "select",
		"valrange": [{
			"title": "Microsoft YaHei",
			"value": "Microsoft YaHei"
		},{	
			"title": "Arial",
			"value": "Arial"
		},{
			"title": "Glyphicons Halflings",
			"value": "Glyphicons Halflings"
		},{
			"title": "Consolas",
			"value": "Consolas"
		},{
			"title": "Courier New",
			"value": "Courier New"
		},
		{	
			"title": "宋体",
			"value": "SimSun"
		}],
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setFontFamily"
	},{
		"id": "fontSize",
		"title": "字体大小",
		"inputMode": "touchspin",
		"properties":[{
			"id": "font-size",
			"title": "字体大小",
			"icon":"font",
			"type": "int",
			"width":"80px",
			"maxval":100,
		    "minval":0,
			"defaultVal": "",
			"inputMode": "touchspin",
			"require": "false",
			"readonly": "false",
			"ctrImpl": "setFontSize"
		}]
	},{
		"id": "font-weight",
		"title": "字体加粗",
		"type": "string",
		"defaultVal": "",
		"inputMode": "buttongroup",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setFontWeight",
		"valrange": [{
			"title": "字体加粗",
			"value": "bold",
			"icon":"bold"
		}]
	},{
		"id": "font-style",
		"title": "字体样式",
		"type": "string",
		"defaultVal": "",
		"inputMode": "buttongroup",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setFontStyle",
		"valrange": [{
			"title": "斜体",
			"value": "italic",
			"icon":"italic"
		}]
	},{
		"id": "color",
		"title": "字体颜色",
		"type": "string",
		"defaultVal": "",
		"inputMode": "colorpicker",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setTextColor"
	}]
}]



var definedpanel_attr = [{
	"id": "padding",
	"label": "内间距",
	"title": "内间距",
	"inputMode": "touchspin",
	"properties": [{
		"id": "padding-top",
		"title": "上",
		"type": "int",
		"width":"100px",
		"maxval":"2000",
	    "minval":"-2000",
		"defaultVal": "",
		"inputMode": "touchspin",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setPaddingTop"
	},
	{
		"id": "padding-bottom",
		"title": "下",
		"type": "int",
		"width":"100px",
	    "maxval":"2000",
	    "minval":"-2000",
		"defaultVal": "",
		"inputMode": "touchspin",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setPaddingBottom"
	},
	{
		"id": "padding-left",
		"title": "左",
		"type": "int",
		"width":"100px",
	    "maxval":"2000",
	    "minval":"-2000",
		"defaultVal": "",
		"inputMode": "touchspin",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setPaddingLeft"
	},
	{
		"id": "padding-right",
		"title": "右",
		"type": "int",
		"width":"100px",
	    "maxval":"2000",
	    "minval":"-2000",
		"defaultVal": "",
		"inputMode": "touchspin",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setPaddingRight"
	}]
},{
	"id": "color",
	"label":"字体",
	"title": "颜色",
	"type": "string",
	"defaultVal": "",
	"inputMode": "colorpicker",
	"require": "false",
	"readonly": "false",
	"ctrImpl": "setTextColor"
},{
	"id": "fontSize",
	"title": "字体大小",
	"inputMode": "touchspin",
	"properties":[{
		"id": "font-size",
		"title": "字体大小",
		"icon":"font",
		"type": "int",
		"width":"100px",
		"maxval":100,
	    "minval":0,
		"defaultVal": "",
		"inputMode": "touchspin",
		"require": "false",
		"readonly": "false",
		"ctrImpl": "setFontSize"
	}]
}]