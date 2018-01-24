var gridbt_designer = {
	getMainObject:function (component){

		return component;

	},

	/**
	 * 格式刷复制方法
	 * @param  {[type]} $newComp [格式刷后的控件信息,Jquery对象]
	 * @param  {[type]} $oldComp  [格式刷前的控件信息,Jquery对象]
	 * @return {[type]}         [description]
	 */
	copyFormat: function($newComp,$oldComp){
		
		var newCompId = $newComp.attr("id");
		var $newStylerInput = $newComp.find("#"+newCompId+'_styler_hidden');	//样式隐藏域信息
		// var $newStylerInit = $newComp.find("#"+newCompId+'_styler_init');	//样式域信息

		var oldCompId = $oldComp.attr("id");
		var oldStyleInputId = oldCompId+'_styler_hidden';
		var $oldStylerInput = $oldComp.find("#"+oldStyleInputId);	//样式隐藏域信息
		// var oldStylerInitId = oldCompId+'_styler_init';
		// var $oldStylerInit = $oldComp.find("#"+oldStylerInitId);	//样式域信息

		if(!$oldStylerInput[0]){
			$oldStylerInput = $("<input id='"+ oldStyleInputId +"' type='hidden'>");
			$oldComp.append($oldStylerInput);

			// $oldStylerInit = $("<style id='"+ oldStylerInitId +"'></style>");
			// $oldComp.append($oldStylerInit);
		}

		if($newStylerInput[0]){
			$oldStylerInput.val($newStylerInput.val());
			// $oldStylerInit.html($newStylerInit.html());
			gridbt_designer.updateStyleByKey($oldComp);
		}else{
			$oldStylerInput.val("");
			$oldStylerInit.html("");
		}

		$newComp.html($oldComp.html());
	},

	/**
	 * 设置控件名称
	 */
	setName: function (component, val){
		if(val!==''){
			var prop = "name"; 
			gridbt_designer.setDataRule(component,prop,val);
			component.attr("cname", val);
		}else{
			component.attr("cname", "");
		}
	},
	/**
	 * 设置内间距
	 */
	setPaddingTop : function(component, val) {
		var prop = "paddingTop";
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_designer.setPadding(component, "top", val);
	},
	setPaddingBottom : function(component, val) {
		var prop = "paddingBottom";
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_designer.setPadding(component, "bottom", val);
	},
	setPaddingLeft : function(component, val) {
		var prop = "paddingLeft";
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_designer.setPadding(component, "left", val);
	},
	setPaddingRight : function(component, val) {
		var prop = "paddingRight";
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_designer.setPadding(component, "right", val);
	},
	setPadding : function(component, direct, val) {
//		var obj = gridbt_designer.getBodyObject(component);

		if (val == '') {
			component.css("padding-" + direct, "");
		} else {
			component.css("padding-" + direct, val + "px");
		}
	},
	/**
	 * 初始化静态数据。
	 */
	// initStaticData:function(template,value){
	// 	// var $template = $(template);
	// 	// $template.find("#staticData1").val(value);
	// 	// return $template.html();
	// },
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
	    toolbar: "[{'name':'create','text':'新增'}]",
	    columns:[],
    },
    showStaticDataPanle : function(){
    	setStaticData(null,'grid');
    },
	setStaticData : function(component,dataStr){
		var prop = 'staticData';
		if(dataStr){
			var datas = JSON.parse(dataStr);
			// gridbt_designer.setDataRule(component,prop,dataStr);

			component.empty();
			component.removeData();
			gridbt_designer.setting = $.extend(false,gridbt_designer.setting,datas.dataGrid);
			
			var datasourceSet_columns = [];
			var dataSourceSet = [{
				"databaseId": "",
				"datasetId": "",
				"name": "staticSource",
				"title": "静态数据数据集",
				'columns': datas.fieldGrid.datas.slice(0,datas.fieldGrid.datas.length),
			}]

			var ruleData = {
				rule : {
					dataSourceSet : dataSourceSet,
				},
				setting : gridbt_designer.setting,
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
				async: true,
				success: function(msg) {
					if(msg){
						
					}
				},
				error: function() {
					alert("异常错误,请稍后再试.");
				}
			})
			gridbt_designer.setting.columns = gridbt_designer.setting.columns.slice(0,gridbt_designer.setting.columns.length-1);
			gridbt_designer.setting.toolbar = "";
			component.grid(gridbt_designer.setting);
			//获取页面id
			//获取控件id

		}
	},
	/**
	 * 设置外间距
	 */
	setMarginTop : function(component, val) {
		var prop = "marginTop";
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_designer.setMargin(component, "top", val);
	},
	setMarginBottom : function(component, val) {
		var prop = "marginBottom";
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_designer.setMargin(component, "bottom", val);
	},
	setMarginLeft : function(component, val) {
		var prop = "marginLeft";
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_designer.setMargin(component, "left", val);
	},
	setMarginRight : function(component, val) {
		var prop = "marginRight";
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_designer.setMargin(component, "right", val);
	},
	setMargin : function(component, direct, val) {
//		var obj = gridbt_designer.getBodyObject(component);
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
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_designer.setPositon(component, 'right', val);
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
		gridbt_designer.setDataRule(component,prop,val);
		
		component.css("position", val);
		
	},
	/**
	*设置尺寸
	*/
	setWidth:function (component,val,unit){ 

		var prop = "width";

		gridbt_designer.setDataRule(component,prop,val,unit);

		gridbt_designer.setSize(component,"width",val,unit);

	},

	setHeight:function (component,val,unit){

		var prop = "height";

		gridbt_designer.setDataRule(component,prop,val,unit); 

		gridbt_designer.setSize(component,"height",val,unit);

	},

	setMinWidth:function (component,val,unit){

		var prop = "minWidth";

		gridbt_designer.setDataRule(component,prop,val,unit); 

		gridbt_designer.setSize(component,"min-width",val,unit);

	},

	setMinHeight:function (component,val,unit){

		var prop = "minHeight";  

		gridbt_designer.setDataRule(component,prop,val,unit); 

		gridbt_designer.setSize(component,"min-height",val,unit);

	},

	setMaxWidth:function (component,val,unit){

		var prop = "maxWidth";

		gridbt_designer.setDataRule(component,prop,val,unit); 

		gridbt_designer.setSize(component,"max-width",val,unit);

	},

	setMaxHeight:function (component,val,unit){

		var prop = "maxHeight";

		gridbt_designer.setDataRule(component,prop,val,unit); 

		gridbt_designer.setSize(component,"max-height",val,unit);

	},
	setSize:function(component,type,val,unit){
		var portlet=gridbt_designer.getMainObject(component);
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
		gridbt_designer.setDataRule(component,prop,val);
//		debugger;

		if ($(this).hasClass('active')) {
			component.removeClass("hmtd-xs");
			component.addClass(val);
		} else {
			component.removeClass("hmtd-xs");
		}
	},
	
	setTitleBackgroundColor:function(component,val){
		var prop = "backgroundTitle"; 
		gridbt_designer.setDataRule(component,prop,val);
	
		gridbt_proto.updateStyleByKey(component,'titleBgColor',val);
		
	},
	/**
	 * 设置表头是否显示
	 */
	setHeaderShow:function(component,val){
		var prop = "headerShow"; 
		gridbt_designer.setDataRule(component,prop,val);
		if(val && val == 'no'){
			val = "none";
		}else{
			val = "";
		}
		gridbt_proto.updateStyleByKey(component,'headerShow',val);
		
	},
	/**
	*设置背景色(odd)
	*/
	setBackgroundColorOdd: function (component,val){
		if(val!=''){
			var prop = "backgroundColorOdd"; 
			gridbt_designer.setDataRule(component,prop,val);
			gridbt_proto.updateStyleByKey(component,'bgcolor1',val);
		}
	},
	/**
	*设置背景色(even)
	*/
	setBackgroundColorEven: function (component,val){
		if(val!=''){
			var prop = "backgroundColorEven"; 
			gridbt_designer.setDataRule(component,prop,val);
			gridbt_proto.updateStyleByKey(component,'bgcolor0',val);
		}
	},
	
	
	
	getStyler: function (component){
		var sid = gridbt_proto.getStylerId(component);
		var styler = component.find('#'+sid);
		if(!styler.length){
			component.append("<input id=\""+sid+"\" type=\"hidden\" value=\"\" />");
			return new Object();
		}else{
			return styler.val()?$.parseJSON(styler.val()) : new Object();
		}
	},
	getStyleBox: function (component){
		var sid = gridbt_proto.getStyleBoxId(component);
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
		var sid = gridbt_proto.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	updateStyleBox: function (component,styler){
		var id = component.attr('id');
		var _id = '#'+id;


		
		var title = _id+" thead>tr{";  //表头
		if(styler['titleBgColor']){
			title += "background-color:"+styler['titleBgColor']+";";
		}
		if(styler['headerShow']){
			title += 'display:'+styler['headerShow']+";";
		}
		title += "}";
//		var body = _id+ " tbody>tr{";
//		if(styler[""]){
//			
//		}
//		body+="}";
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
		

		_default += _default_tbody_focolor+_default_thead_focolor + _default_tbody_td + _default_th_td + _default_header + _default_header_td + _default_body;

		/**
		 * hover
		 */
		var _hover = _id+" tbody>tr:hover{" ;
		if(styler['bgcolor_h']){
			_hover += "background-color:"+styler['bgcolor_h']+";";
		}
		if(styler['bocolor_h']){
			_hover += "border:1px "+(styler['bostyle_h']||"solid")+" "+styler['bocolor_h']+";";
		}
		if(styler['focolor_h']){
			_hover += "color:"+styler['focolor_h']+";";
		}
		_hover += "}";

		var _selected = _id+" .table-selected>td{";
		if(styler['bgcolor_s']){
			_selected += "background-color: "+styler['bgcolor_s']+";";
		}
		if(styler['font_color_selected']){
			_selected += "color: "+styler['font_color_selected']+";";
		}
		_selected += "}";

		var stylebox = gridbt_proto.getStyleBox(component);
		stylebox.html(title+_default0+_default1+_default+_hover+_selected);
	},
	updateStyleByKey: function (component,key,val){
		var styler = gridbt_proto.getStyler(component);
		styler[key] = val;
		gridbt_proto.updateStyler(component,styler);
		gridbt_proto.updateStyleBox(component,styler);
	},
	/**
	*设置表格风
	*/
	setTableStyle: function (component,val){
		component.attr('table-style', val);
		var tables = component.find('table');
		var prop = "tableStyle"; 
		gridbt_designer.setDataRule(component,prop,val);
		if(val==='condensed'){
			tables.removeClass('table-bordered');
			tables.addClass('table-condensed');
		}else{
			tables.removeClass('table-condensed');
			tables.addClass('table-bordered');
		}
		var styler = gridbt_proto.getStyler(component);
		gridbt_proto.updateStyleBox(component,styler);
	},
	/**
	*设置背景色
	*/
	setBackgroundColor: function (component,val){
			var prop = "backgroundColor"; 
			gridbt_designer.setDataRule(component,prop,val);
			gridbt_proto.updateStyleByKey(component,'bgcolor',val);
	},
	/**
	*设置背景色(selected)
	*/
	setBackgroundColorSelected: function (component,val){
			var prop = "backgroundColorSelected"; 
			gridbt_designer.setDataRule(component,prop,val);
			gridbt_proto.updateStyleByKey(component,'bgcolor_s',val);
	},
		/**
	*设置边框颜色（全局）
	*/
	setBorderColor: function (component,val){
			var prop = "borderColor"; 
			gridbt_designer.setDataRule(component,prop,val);
			gridbt_proto.updateStyleByKey(component,'bocolor',val);
	},
	/**
	 * 边框悬停颜色（全局）
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderColor_hover: function (component,val){
		var prop = "borderColor_h"; 
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_proto.updateStyleByKey(component,'bocolor_h',val);
	},
	/**
	*设置边框样式（全局）
	*/
	setBorderStyle: function (component,val){
		var prop = "borderStyle"; 
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_proto.updateStyleByKey(component,'bostyle',val);
		// component.css("border-style",val);
	},
	/**
	*设置边框颜色（表头）
	*/
	setBorderColorHeader: function (component,val){
		var prop = "borderColorHeader"; 
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_proto.updateStyleByKey(component,'bocolorHeader',val);
	},
	/**
	*设置边框样式（表头）
	*/  
	setBorderStyleHeader: function (component,val){
		var prop = "borderStyleHeader"; 
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_proto.updateStyleByKey(component,'bostyleHeader',val);
		// component.css("border-style",val);
	},
	/**
	 * 上边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeHeaderT: function (component,val){
		var prop = "borderSizeHT"; 
		gridbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		gridbt_proto.updateStyleByKey(component,'borderSizeHT',val);
		// component.css("border-style",val);
	},
	/**
	 * 下边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeHeaderB: function (component,val){
		var prop = "borderSizeHB"; 
		gridbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		gridbt_proto.updateStyleByKey(component,'borderSizeHB',val);
		// component.css("border-style",val);
	},  
	/**
	 * 左边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeHeaderL: function (component,val){
		var prop = "borderSizeHL"; 
		gridbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		gridbt_proto.updateStyleByKey(component,'borderSizeHL',val);
		// component.css("border-style",val);
	},
	/**
	 * 右边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeHeaderR: function (component,val){
		var prop = "borderSizeHR"; 
		gridbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		gridbt_proto.updateStyleByKey(component,'borderSizeHR',val);
		// component.css("border-style",val);
	},
	/**
	*设置边框颜色（表头）
	*/
	setBorderColorHeaderTd: function (component,val){
		var prop = "borderColorHeaderTd"; 
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_proto.updateStyleByKey(component,'bocolorHeaderTd',val);
	},
	/**
	*设置边框样式（表头）
	*/  
	setBorderStyleHeaderTd: function (component,val){
		var prop = "borderStyleHeaderTd"; 
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_proto.updateStyleByKey(component,'bostyleHeaderTd',val);
		// component.css("border-style",val);
	},
	/**
	 * 上边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeHeaderTdT: function (component,val){
		var prop = "borderSizeHTdT"; 
		gridbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		gridbt_proto.updateStyleByKey(component,'borderSizeHTdT',val);
		// component.css("border-style",val);
	},
	/**
	 * 下边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeHeaderTdB: function (component,val){
		var prop = "borderSizeHTdB"; 
		gridbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		gridbt_proto.updateStyleByKey(component,'borderSizeHTdB',val);
		// component.css("border-style",val);
	},  
	/**
	 * 左边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeHeaderTdL: function (component,val){
		var prop = "borderSizeHTdL"; 
		gridbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		gridbt_proto.updateStyleByKey(component,'borderSizeHTdL',val);
		// component.css("border-style",val);
	},
	/**
	 * 右边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeHeaderTdR: function (component,val){
		var prop = "borderSizeHTdR"; 
		gridbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		gridbt_proto.updateStyleByKey(component,'borderSizeHTdR',val);
		// component.css("border-style",val);
	},
	/**
	*设置边框颜色（内容）
	*/
	setBorderColorBody: function (component,val){
		var prop = "borderColorBody"; 
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_proto.updateStyleByKey(component,'bocolorBody',val);
	},
	/**
	*设置边框样式（内容）
	*/
	setBorderStyleBody: function (component,val){
		var prop = "borderStyleBody"; 
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_proto.updateStyleByKey(component,'bostyleBody',val);
		// component.css("border-style",val);
	},
		/**
	 * 上边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeBodyT: function (component,val){
		var prop = "borderSizeBT"; 
		gridbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		gridbt_proto.updateStyleByKey(component,'borderSizeBT',val);
		// component.css("border-style",val);
	},
	/**
	 * 下边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeBodyB: function (component,val){
		var prop = "borderSizeBB"; 
		gridbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		gridbt_proto.updateStyleByKey(component,'borderSizeBB',val);
		// component.css("border-style",val);
	},  
	/**
	 * 左边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeBodyL: function (component,val){
		var prop = "borderSizeBL"; 
		gridbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		gridbt_proto.updateStyleByKey(component,'borderSizeBL',val);
		// component.css("border-style",val);
	},
	/**
	 * 右边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeBodyR: function (component,val){
		var prop = "borderSizeBR"; 
		gridbt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		gridbt_proto.updateStyleByKey(component,'borderSizeBR',val);
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
			var prop = "fontColor"; 
			gridbt_designer.setDataRule(component,prop,val);
			gridbt_proto.updateStyleByKey(component,'focolor',val);
	},
	setFontColorTh: function (component,val){
			var prop = "fontColor_th"; 
			gridbt_designer.setDataRule(component,prop,val);
			gridbt_proto.updateStyleByKey(component,'focolor_th',val);
	},
	setFontStyle: function (component,val){
		var prop = "fontStyle"; 
		gridbt_designer.setDataRule(component,prop,val);
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
			gridbt_designer.setDataRule(component,prop,val);
			var tg = component.find('span');
			tg.css("font-size",val+"px");
		}
	},
	/**
	*鼠标悬停效果
	*/
	setBackgroundColor_hover: function (component,val){
		var prop = "backgroundColor_h"; 
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_proto.updateStyleByKey(component,'bgcolor_h',val);
	},
	setBorderColor_hover: function (component,val){
		var prop = "borderColor_h"; 
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_proto.updateStyleByKey(component,'bocolor_h',val);
	},
	setFontColor_hover: function (component,val){
		var prop = "fontColor_h"; 
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_proto.updateStyleByKey(component,'focolor_h',val);
	},
	/**
	 * 选中行背景颜色
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setFontColor_selected: function(component,val){
		var prop = "font_color_selected"; 
		gridbt_designer.setDataRule(component,prop,val);
		gridbt_proto.updateStyleByKey(component,'font_color_selected',val);
	},

	setDataRule : function(component,prop,val,unit){
        var lastVal = gridbt_designer.getDataRule(component,prop)

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
		gridbt_designer.setDataRule(component,prop,val,unit); 
		var val_str = val + unit;
		gridbt_proto.updateStyleByKey(component,'tbodyfontsize',val_str);
    },

    setThFontSize  : function(component,val,unit){
    	var prop = "thfontsize";
		gridbt_designer.setDataRule(component,prop,val,unit); 
		var val_str = val + unit;
		gridbt_proto.updateStyleByKey(component,'thfontsize',val_str);
    }


};
var gridbt_proto = Object.create(gridbt_designer);