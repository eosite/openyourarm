var treelistbt_designer = {
	getMainObject:function (component){
		return component;
	},
	/**
	 * 设置控件名称
	 */
	setName: function (component, val){
		if(val!==''){
			var prop = "name"; 
			treelistbt_designer.setDataRule(component,prop,val);
			component.attr("cname", val);
		}
	},
	/**
	 * 设置外间距
	 */
	setMarginTop : function(component, val) {
		var prop = "marginTop";
		treelistbt_designer.setDataRule(component,prop,val);
		treelistbt_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		treelistbt_designer.setDataRule(component,prop,val);
		treelistbt_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		treelistbt_designer.setDataRule(component,prop,val);
		treelistbt_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		treelistbt_designer.setDataRule(component,prop,val);
		treelistbt_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
//		var obj = treelistbt_designer.getBodyObject(component);
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
		treelistbt_designer.setDataRule(component,prop,val);
		treelistbt_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		treelistbt_designer.setDataRule(component,prop,val);
		treelistbt_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		treelistbt_designer.setDataRule(component,prop,val);
		treelistbt_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		treelistbt_designer.setDataRule(component,prop,val);
		treelistbt_designer.setPositon(component, 'right', val);
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
		treelistbt_designer.setDataRule(component,prop,val);
		
		component.css("position", val);
		
	},
	/**
	*设置尺寸
	*/
	setWidth:function (component,val,unit){ 

		var prop = "width";

		treelistbt_designer.setDataRule(component,prop,val,unit);

		treelistbt_designer.setSize(component,"width",val,unit);

	},

	setHeight:function (component,val,unit){

		var prop = "height";

		treelistbt_designer.setDataRule(component,prop,val,unit); 

		treelistbt_designer.setSize(component,"height",val,unit);

	},

	setMinWidth:function (component,val,unit){

		var prop = "minWidth";

		treelistbt_designer.setDataRule(component,prop,val,unit); 

		treelistbt_designer.setSize(component,"min-width",val,unit);

	},

	setMinHeight:function (component,val,unit){

		var prop = "minHeight";  

		treelistbt_designer.setDataRule(component,prop,val,unit); 

		treelistbt_designer.setSize(component,"min-height",val,unit);

	},

	setMaxWidth:function (component,val,unit){

		var prop = "maxWidth";

		treelistbt_designer.setDataRule(component,prop,val,unit); 

		treelistbt_designer.setSize(component,"max-width",val,unit);

	},

	setMaxHeight:function (component,val,unit){

		var prop = "maxHeight";

		treelistbt_designer.setDataRule(component,prop,val,unit); 

		treelistbt_designer.setSize(component,"max-height",val,unit);

	},
	setSize:function(component,type,val,unit){
		var portlet=treelistbt_designer.getMainObject(component);
		if(val==''){
		  portlet.css(type,'');	
		}else{
			if(unit==undefined){
				unit="px";
			}else{
				portlet.css(type,val+unit);
			}
		}
	},
	/**
	 * 常规尺寸
	 */
	setSizeVal: function (component, val){
		var prop = "sizeVal"; 
		treelistbt_designer.setDataRule(component,prop,val);
//		debugger;
		if ($(this).hasClass('active')) {
			component.removeClass("hmtd-xs");
			component.addClass(val);
		} else {
			component.removeClass("hmtd-xs");
		}
	},
	
	
	
	getStyler: function (component){
		var sid = treelistbt_proto.getStylerId(component);
		var styler = component.find('#'+sid);
		if(!styler.length){
			component.append("<input id=\""+sid+"\" type=\"hidden\" value=\"{}\" />");
			return new Object();
		}else{
			return $.parseJSON(styler.val());
		}
	},
	getStyleBox: function (component){
		var sid = treelistbt_proto.getStyleBoxId(component);
		var box = component.find('#'+sid);
		if(!box.length){
			component.append("<style id=\""+sid+"\"></style>");
		}
		return component.find('#'+sid);
	},
	getStylerId: function (component){
		var id = component.attr('id');
		return id+'_styler_hidden';
	},
	getStyleBoxId: function (component){
		var id = component.attr('id');
		return id+'_styler_init';
	},
	updateStyler: function (component,styler){
		var sid = treelistbt_proto.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	updateStyleBox: function (component,styler){
		var id = component.attr('id');
		var _id = '#'+id;
		/**
		 * default
		 */
		//偶数行
		var _default0 = _id+" tbody>tr:nth-of-type(even){";
		if(styler['bgcolor0']){
			_default0 += "background-color:"+styler['bgcolor0']+";";
		}
		if(styler['bocolor0']){
			_default0 += "border-color:"+styler['bocolor0']+";";
		}
		if(styler['focolor0']){
			_default0 += "color:"+styler['focolor0']+";";
		}
		_default0 += "}";
		//奇数行
		var _default1 = _id+" tbody>tr:nth-of-type(odd){";
		if(styler['bgcolor1']){
			_default1 += "background-color:"+styler['bgcolor1']+";";
		}
		if(styler['bocolor1']){
			_default1 += "border-color:"+styler['bocolor1']+";";
		}
		if(styler['focolor1']){
			_default1 += "color:"+styler['focolor1']+";";
		}
		_default1 += "}";

		var _default_header = ""
						+_id+" .grid-header .table-bordered{";
		if(styler['bocolorHeader'] && component.attr('table-style')!=='condensed'){
			_default_header += "border:1px "+(styler['bostyleHeader']||"solid")+" "+styler['bocolorHeader']+";";
		}

		if(styler['borderSizeHT']){
			_default_header += "border-top-width:"+styler['borderSizeHT']+";";
		}
		if(styler['borderSizeHB']){
			_default_header += "border-bottom-width:"+styler['borderSizeHB']+";";
		}
		if(styler['borderSizeHL']){
			_default_header += "border-left-width:"+styler['borderSizeHL']+";";
		}
		if(styler['borderSizeHR']){
			_default_header += "border-right-width:"+styler['borderSizeHR']+";";
		}
		_default_header += "}";
		
 
		var _default_header_td = ""
						+_id+" thead>tr>th,"
						+ _id+" thead>tr>td{";
		if(styler['bocolorHeaderTd'] && component.attr('table-style')!=='condensed'){
			_default_header_td += "border:1px "+(styler['bostyleHeaderTd']||"solid")+" "+styler['bocolorHeaderTd']+";";
		}
		if(styler['borderSizeHTdT']){
			_default_header_td += "border-top-width:"+styler['borderSizeHTdT']+";";
		}
		if(styler['borderSizeHTdB']){
			_default_header_td += "border-bottom-width:"+styler['borderSizeHTdB']+";";
		}
		if(styler['borderSizeHTdL']){
			_default_header_td += "border-left-width:"+styler['borderSizeHTdL']+";";
		}
		if(styler['borderSizeHTdR']){
			_default_header_td += "border-right-width:"+styler['borderSizeHTdR']+";";
		}
		_default_header_td += "}";

		var _default_body = ""
						+_id+" tbody>tr>th,"
						+ _id+" tbody>tr>td{";
		if(styler['bocolorBody'] && component.attr('table-style')!=='condensed'){
			_default_body += "border:1px "+(styler['bostyleBody']||"solid")+" "+styler['bocolorBody']+";";
		}
		if(styler['borderSizeBT']){
			_default_body += "border-top-width:"+styler['borderSizeBT']+";";
		}
		if(styler['borderSizeBB']){
			_default_body += "border-bottom-width:"+styler['borderSizeBB']+";";
		}
		if(styler['borderSizeBL']){
			_default_body += "border-left-width:"+styler['borderSizeBL']+";";
		}
		if(styler['borderSizeBR']){
			_default_body += "border-right-width:"+styler['borderSizeBR']+";";
		}
		_default_body += "}";

		//无论奇偶
		var _default = ""
					+ _id+" tbody>tr>td,"
					+ _id+" tbody>tr>th,"
					+ _id+" thead>tr>td,"
					+ _id+" thead>tr>th,"
					+ _id+" tfoot>tr>td,"
					+ _id+" tfoot>tr>th{";
		if(styler['bocolor'] && component.attr('table-style')!=='condensed'){
			_default += "border:1px "+(styler['bostyle']||"solid")+" "+styler['bocolor']+";";
		}
		_default += "}";

		var _default_tbody_focolor = _id+" tbody>tr," + _id+" tbody>tr{"
		if(styler['focolor']){
			_default_tbody_focolor += "color:"+styler['focolor']+";";
		}
		_default_tbody_focolor += "}";

		var _default_thead_focolor = _id+" thead>tr," + _id+" thead>tr{"
		if(styler['focolor_th']){
			_default_thead_focolor += "color:"+styler['focolor_th']+";";
		}
		if(styler['titleBgColor']){
			_default_thead_focolor += "background-color:"+styler['titleBgColor']+";";
		}
		_default_thead_focolor += "}";

		var _default_tbody_td = _id+" tbody td{"
		if(styler['tbodyfontsize']){
			_default_tbody_td += 'font-size:'+styler['tbodyfontsize']+";";
		}
		_default_tbody_td += "}";

		var _default_th_td = _id+" thead th{"
		if(styler['thfontsize']){
			_default_th_td += 'font-size:'+styler['thfontsize']+";";
		}
		_default_th_td += "}";

		_default += _default_tbody_focolor+_default_thead_focolor + _default_tbody_td + _default_th_td +_default_header + _default_header_td + _default_body;

		/**
		 * hover
		 */
		var _hover = _id+" tbody>tr:hover{"
		if(styler['bgcolor_h']){
			_hover += "background-color:"+styler['bgcolor_h']+";";
		}
		if(styler['bocolor_h']){
			_hover += "border:2px "+(styler['bostyle_h']||"solid")+" "+styler['bocolor_h']+";";
		}
		if(styler['focolor_h']){
			_hover += "color:"+styler['focolor_h']+";";
		}
		_hover += "}";

		var _selected = _id+" .treelist-selected>td{";
		if(styler['bgcolor_s']){
			_selected += "background-color: "+styler['bgcolor_s']+";";
		}
		if(styler['font_color_selected']){
			_selected += "color: "+styler['font_color_selected']+";";
		}
		_selected += "}";

		font_color_selected

		var stylebox = treelistbt_proto.getStyleBox(component);
		stylebox.html(_default0+_default1+_default+_hover+_selected);
	},
	updateStyleByKey: function (component,key,val){
		var styler = treelistbt_proto.getStyler(component);
		styler[key] = val;
		treelistbt_proto.updateStyler(component,styler);
		treelistbt_proto.updateStyleBox(component,styler);
	},
	/**
	*设置表格风
	*/
	setTableStyle: function (component,val){
		component.attr('table-style', val);
		var tables = component.find('table');
		var prop = "tableStyle"; 
		treelistbt_designer.setDataRule(component,prop,val);
		if(val==='condensed'){
			tables.removeClass('table-bordered');
			tables.addClass('table-condensed');
		}else{
			tables.removeClass('table-condensed');
			tables.addClass('table-bordered');
		}
		var styler = treelistbt_proto.getStyler(component);
		treelistbt_proto.updateStyleBox(component,styler);
	},
	/**
	*设置背景色
	*/
	setBackgroundColor: function (component,val){
			var prop = "backgroundColor"; 
			treelistbt_designer.setDataRule(component,prop,val);
			treelistbt_proto.updateStyleByKey(component,'bgcolor',val);
	},
	/**
	*设置背景色(odd)
	*/
	setBackgroundColorOdd: function (component,val){
			var prop = "backgroundColorOdd"; 
			treelistbt_designer.setDataRule(component,prop,val);
			treelistbt_proto.updateStyleByKey(component,'bgcolor1',val);
	},
	/**
	*设置背景色(even)
	*/
	setBackgroundColorEven: function (component,val){
			var prop = "backgroundColorEven"; 
			treelistbt_designer.setDataRule(component,prop,val);
			treelistbt_proto.updateStyleByKey(component,'bgcolor0',val);
	},
	/**
	*设置背景色(selected)
	*/
	setBackgroundColorSelected: function (component,val){
			var prop = "backgroundColorSelected"; 
			treelistbt_designer.setDataRule(component,prop,val);
			treelistbt_proto.updateStyleByKey(component,'bgcolor_s',val);
	},
	/**
	*设置边框颜色
	*/
	setBorderColor: function (component,val){
			var prop = "borderColor"; 
			treelistbt_designer.setDataRule(component,prop,val);
			treelistbt_proto.updateStyleByKey(component,'bocolor',val);
	},
	/**
	*设置边框样式
	*/
	setBorderStyle: function (component,val){
			var prop = "borderStyle"; 
			treelistbt_designer.setDataRule(component,prop,val);
			treelistbt_proto.updateStyleByKey(component,'bostyle',val);
	},
	/**
	*设置边框颜色（表头）
	*/
	setBorderColorHeader: function (component,val){
		var prop = "borderColorHeader"; 
		treelistbt_designer.setDataRule(component,prop,val);
		treelistbt_proto.updateStyleByKey(component,'bocolorHeader',val);
	},
	/**
	*设置边框样式（表头）
	*/  
	setBorderStyleHeader: function (component,val){
		var prop = "borderStyleHeader"; 
		treelistbt_designer.setDataRule(component,prop,val);
		treelistbt_proto.updateStyleByKey(component,'bostyleHeader',val);
		// component.css("border-style",val);
	},
	/**
	 * 上边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeHeaderT: function (component,val){
		var prop = "borderSizeHT"; 
		treelistbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		treelistbt_proto.updateStyleByKey(component,'borderSizeHT',val);
		// component.css("border-style",val);
	},
	/**
	 * 下边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeHeaderB: function (component,val){
		var prop = "borderSizeHB"; 
		treelistbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		treelistbt_proto.updateStyleByKey(component,'borderSizeHB',val);
		// component.css("border-style",val);
	},  
	/**
	 * 左边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeHeaderL: function (component,val){
		var prop = "borderSizeHL"; 
		treelistbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		treelistbt_proto.updateStyleByKey(component,'borderSizeHL',val);
		// component.css("border-style",val);
	},
	/**
	 * 右边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeHeaderR: function (component,val){
		var prop = "borderSizeHR"; 
		treelistbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		treelistbt_proto.updateStyleByKey(component,'borderSizeHR',val);
		// component.css("border-style",val);
	},
	/**
	*设置边框颜色（表头）
	*/
	setBorderColorHeaderTd: function (component,val){
		var prop = "borderColorHeaderTd"; 
		treelistbt_designer.setDataRule(component,prop,val);
		treelistbt_proto.updateStyleByKey(component,'bocolorHeaderTd',val);
	},
	/**
	*设置边框样式（表头）
	*/  
	setBorderStyleHeaderTd: function (component,val){
		var prop = "borderStyleHeaderTd"; 
		treelistbt_designer.setDataRule(component,prop,val);
		treelistbt_proto.updateStyleByKey(component,'bostyleHeaderTd',val);
		// component.css("border-style",val);
	},
	/**
	 * 上边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeHeaderTdT: function (component,val){
		var prop = "borderSizeHTdT"; 
		treelistbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		treelistbt_proto.updateStyleByKey(component,'borderSizeHTdT',val);
		// component.css("border-style",val);
	},
	/**
	 * 下边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeHeaderTdB: function (component,val){
		var prop = "borderSizeHTdB"; 
		treelistbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		treelistbt_proto.updateStyleByKey(component,'borderSizeHTdB',val);
		// component.css("border-style",val);
	},  
	/**
	 * 左边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeHeaderTdL: function (component,val){
		var prop = "borderSizeHTdL"; 
		treelistbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		treelistbt_proto.updateStyleByKey(component,'borderSizeHTdL',val);
		// component.css("border-style",val);
	},
	/**
	 * 右边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeHeaderTdR: function (component,val){
		var prop = "borderSizeHTdR"; 
		treelistbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		treelistbt_proto.updateStyleByKey(component,'borderSizeHTdR',val);
		// component.css("border-style",val);
	},
	/**
	*设置边框颜色（内容）
	*/
	setBorderColorBody: function (component,val){
		var prop = "borderColorBody"; 
		treelistbt_designer.setDataRule(component,prop,val);
		treelistbt_proto.updateStyleByKey(component,'bocolorBody',val);
	},
	/**
	*设置边框样式（内容）
	*/
	setBorderStyleBody: function (component,val){
		var prop = "borderStyleBody"; 
		treelistbt_designer.setDataRule(component,prop,val);
		treelistbt_proto.updateStyleByKey(component,'bostyleBody',val);
		// component.css("border-style",val);
	},
		/**
	 * 上边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeBodyT: function (component,val){
		var prop = "borderSizeBT"; 
		treelistbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		treelistbt_proto.updateStyleByKey(component,'borderSizeBT',val);
		// component.css("border-style",val);
	},
	/**
	 * 下边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeBodyB: function (component,val){
		var prop = "borderSizeBB"; 
		treelistbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		treelistbt_proto.updateStyleByKey(component,'borderSizeBB',val);
		// component.css("border-style",val);
	},  
	/**
	 * 左边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeBodyL: function (component,val){
		var prop = "borderSizeBL"; 
		treelistbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		treelistbt_proto.updateStyleByKey(component,'borderSizeBL',val);
		// component.css("border-style",val);
	},
	/**
	 * 右边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeBodyR: function (component,val){
		var prop = "borderSizeBR"; 
		treelistbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		treelistbt_proto.updateStyleByKey(component,'borderSizeBR',val);
		// component.css("border-style",val);
	},
	/**
	*设置字体
	*/
	setFontFamily: function (component,val){
			var tg = component.find('span');
			tg.css("font-family",val);
	},
	setFontColor: function (component,val){
		if(val!=''){
			var prop = "fontColor"; 
			treelistbt_designer.setDataRule(component,prop,val);
			treelistbt_proto.updateStyleByKey(component,'focolor',val);
		}
	},
	setFontColorTh: function (component,val){
		if(val!=''){
			var prop = "fontColor_th"; 
			treelistbt_designer.setDataRule(component,prop,val);
			treelistbt_proto.updateStyleByKey(component,'focolor_th',val);
		}
	},
	/**
	 * 选中行背景颜色
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setFontColor_selected: function(component,val){
		var prop = "font_color_selected"; 
		treelistbt_designer.setDataRule(component,prop,val);
		treelistbt_proto.updateStyleByKey(component,'font_color_selected',val);
	},
	setFontStyle: function (component,val){
		var prop = "fontStyle"; 
		treelistbt_designer.setDataRule(component,prop,val);
		var tg = component.find('span');
		var tgcss = $(tg).attr('style') || '';
		if(val=='italic'){
			if(tgcss.indexOf('font-style: italic')==-1){
				tg.css("font-style",'italic');
			}else{
				tg.css("font-style",'');
			}
		}else if(val=='bold'){
			if(tgcss.indexOf('font-weight: bold')==-1){
				tg.css("font-weight",'bold');
			}else{
				tg.css("font-weight",'');
			}
		}
	},
	/**
	*设置字体大小
	*/
	setFontSize: function (component,val){
		if(val!=''){
			var prop = "fontSize"; 
			treelistbt_designer.setDataRule(component,prop,val);
			var tg = component.find('span');
			tg.css("font-size",val+"px");
		}
	},
	/**
	*鼠标悬停效果
	*/
	setBackgroundColor_hover: function (component,val){
		if(val!=''){
			var prop = "backgroundColor_h"; 
			treelistbt_designer.setDataRule(component,prop,val);
			treelistbt_proto.updateStyleByKey(component,'bgcolor_h',val);
		}
	},
	setBorderColor_hover: function (component,val){
		if(val!=''){
			var prop = "borderColor_h"; 
			treelistbt_designer.setDataRule(component,prop,val);
			treelistbt_proto.updateStyleByKey(component,'bocolor_h',val);
		}
	},
	setFontColor_hover: function (component,val){
		if(val!=''){
			var prop = "fontColor_h"; 
			treelistbt_designer.setDataRule(component,prop,val);
			treelistbt_proto.updateStyleByKey(component,'focolor_h',val);
		}
	},

	setDataRule : function(component,prop,val,unit){
        var lastVal = treelistbt_designer.getDataRule(component,prop)
        
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
    setTbodyFontSize : function(component,val,unit){
    	var prop = "tbodyfontsize";
		treelistbt_designer.setDataRule(component,prop,val,unit); 
		var val_str = val + unit;
		treelistbt_proto.updateStyleByKey(component,'tbodyfontsize',val_str);
    },

    setThFontSize  : function(component,val,unit){
    	var prop = "thfontsize";
		treelistbt_designer.setDataRule(component,prop,val,unit); 
		var val_str = val + unit;
		treelistbt_proto.updateStyleByKey(component,'thfontsize',val_str);
    },

    setTitleBackgroundColor : function(component,val){
    	var prop = "backgroundTitle"; 
		treelistbt_designer.setDataRule(component,prop,val);
	
		treelistbt_proto.updateStyleByKey(component,'titleBgColor',val);
    },

    setting :{
		datas:[],
		// tableCls: 'table myTableStyle',
	    resizable: false,
	    scrollable: false,
	    clickUpdate: false,
	    occupy: false,
	    sortable: {},
	    selectable: '',
	    showInLine: true,
	    sortDesc: false,
	    combineAble: false,
	    toolbar: "[{'name':'addBrother','text':'同级新增'},{'name':'addChildren','text':'子级新增'}]",
	    columns:[],
    },
    showStaticDataPanle : function(){
    	setStaticData(null,'ztree');
    },
	setStaticData : function(component,dataStr){
		var prop = 'staticData';
		if(dataStr){
			var datas = JSON.parse(dataStr);
			// treelistbt_designer.setDataRule(component,prop,dataStr);

			component.empty();
			component.removeData();
			treelistbt_designer.setting = $.extend(false,treelistbt_designer.setting,datas.dataGrid);
			

			var datasourceSet_columns = [];
			var dataSourceSet = [{
				"databaseId": "",
				"datasetId": "",
				"name": "staticSource",
				"title": "静态数据数据集",
				'columns': datas.fieldGrid.datas,
			}]

			var ruleData = {
				rule : {
					dataSourceSet : dataSourceSet,
				},
				setting : treelistbt_designer.setting,
				staticDataRule : datas
			}
			var pageId = id;
			var param = {
				pageId : pageId,
				componentId : component.attr("id"),
				ruleData : JSON.stringify(ruleData),
			}

			$.ajax({
				url: contextPath + '/mx/form/staticFile/save',
				type: "post",
				data: param,
				async: false,
				success: function(msg) {
					if(msg){
						
					}
				},
				error: function() {
					alert("异常错误,请稍后再试.");
				}
			})

			treelistbt_designer.setting.columns = treelistbt_designer.setting.columns.slice(0,treelistbt_designer.setting.columns.length-1);
			treelistbt_designer.setting.toolbar = "";
			component.treelist(treelistbt_designer.setting);
		}
	},
};
var treelistbt_proto = Object.create(treelistbt_designer);