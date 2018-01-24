var dhxgantt_designer = {
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
			dhxgantt_designer.updateStyleByKey($oldComp);
		}else{
			$oldStylerInput.val("");
			$oldStylerInit.html("");
		}

		$newComp.html($oldComp.html());
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
			// dhxgantt_designer.setDataRule(component,prop,dataStr);

			component.empty();
			component.removeData();
			dhxgantt_designer.setting = $.extend(false,dhxgantt_designer.setting,datas.dataGrid);
			
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
				setting : dhxgantt_designer.setting,
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
			dhxgantt_designer.setting.columns = dhxgantt_designer.setting.columns.slice(0,dhxgantt_designer.setting.columns.length-1);
			dhxgantt_designer.setting.toolbar = "";
			component.grid(dhxgantt_designer.setting);
			//获取页面id
			//获取控件id

		}
	},

	/**
	 * 设置控件名称
	 */
	setName: function (component, val){
		if(val!==''){
			var prop = "name"; 
			dhxgantt_designer.setDataRule(component,prop,val);
			component.attr("cname", val);
		}else{
			component.attr("cname", "");
		}
	},

	/**
	 * 设置位置
	 */
	setPositionTop : function(component, val) {
		var prop = "top";
		dhxgantt_designer.setDataRule(component,prop,val);
		dhxgantt_designer.setPositon(component, 'top', val);
	},
	setPositionBottom : function(component, val) {
		var prop = "bottom";
		dhxgantt_designer.setDataRule(component,prop,val);
		dhxgantt_designer.setPositon(component, 'bottom', val);
	},
	setPositionLeft : function(component, val) {
		var prop = "left";
		dhxgantt_designer.setDataRule(component,prop,val);
		dhxgantt_designer.setPositon(component, 'left', val);
	},
	setPositionRight : function(component, val) {
		var prop = "right";
		dhxgantt_designer.setDataRule(component,prop,val);
		dhxgantt_designer.setPositon(component, 'right', val);
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
		dhxgantt_designer.setDataRule(component,prop,val);
		
		component.css("position", val);
		
	},
	/**
	*设置尺寸
	*/
	setWidth:function (component,val,unit){ 

		var prop = "width";

		dhxgantt_designer.setDataRule(component,prop,val,unit);

		dhxgantt_designer.setSize(component,"width",val,unit);

	},

	setHeight:function (component,val,unit){

		var prop = "height";

		dhxgantt_designer.setDataRule(component,prop,val,unit); 

		dhxgantt_designer.setSize(component,"height",val,unit);

	},
	
	setTitleBackgroundColor:function(component,val){
		var prop = "backgroundTitle"; 
		dhxgantt_designer.setDataRule(component,prop,val);
	
		dhxgantt_designer.updateStyleByKey(component,'titleBgColor',val);
		
	},

	/**
	*设置背景色
	*/
	setBackgroundColor: function (component,val){
			var prop = "backgroundContent"; 
			dhxgantt_designer.setDataRule(component,prop,val);
			dhxgantt_designer.updateStyleByKey(component,'bgcolor',val);
	},
	/**
	*设置背景色(selected)
	*/
	setBackgroundColorSelected: function (component,val){
			var prop = "backgroundColorSelected"; 
			dhxgantt_designer.setDataRule(component,prop,val);
			dhxgantt_designer.updateStyleByKey(component,'bgcolor_s',val);
	},
	/**
	 * 悬停行颜色
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBackgroundColor_hover: function (component,val){
		var prop = "backgroundColor_h"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		dhxgantt_designer.updateStyleByKey(component,'bgcolor_h',val);
	},
	/**
	*设置边框颜色（外层边框样式）
	*/
	setBorderColorAll: function (component,val){
		var prop = "borderColorAll"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		dhxgantt_designer.updateStyleByKey(component,'bocolorAll',val);
	},
	/**
	*设置边框样式（外层边框样式）
	*/  
	setBorderStyleAll: function (component,val){
		var prop = "borderStyleAll"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		dhxgantt_designer.updateStyleByKey(component,'bostyleAll',val);
		// component.css("border-style",val);
	},
	/**
	*设置边框颜色（grid与右边的分割符）
	*/
	setBorderColorSplit: function (component,val){
		var prop = "borderColorSplit"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		dhxgantt_designer.updateStyleByKey(component,'bocolorSplit',val);
	},
	/**
	*设置边框样式（grid与右边的分割符）
	*/  
	setBorderStyleSplit: function (component,val){
		var prop = "borderStyleSplit"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		dhxgantt_designer.updateStyleByKey(component,'bostyleSplit',val);
		// component.css("border-style",val);
	},

	/**
	*设置边框颜色（表头）
	*/
	setBorderColorHeader: function (component,val){
		var prop = "borderColorHeader"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		dhxgantt_designer.updateStyleByKey(component,'bocolorHeader',val);
	},
	/**
	*设置边框样式（表头）
	*/  
	setBorderStyleHeader: function (component,val){
		var prop = "borderStyleHeader"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		dhxgantt_designer.updateStyleByKey(component,'bostyleHeader',val);
		// component.css("border-style",val);
	},
	/**
	 * 上边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeHeaderT: function (component,val){
		var prop = "borderSizeHT"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		dhxgantt_designer.updateStyleByKey(component,'borderSizeHT',val);
		// component.css("border-style",val);
	},
	/**
	 * 下边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeHeaderB: function (component,val){
		var prop = "borderSizeHB"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		dhxgantt_designer.updateStyleByKey(component,'borderSizeHB',val);
		// component.css("border-style",val);
	},  
	/**
	 * 左边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeHeaderL: function (component,val){
		var prop = "borderSizeHL"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		dhxgantt_designer.updateStyleByKey(component,'borderSizeHL',val);
		// component.css("border-style",val);
	},
	/**
	 * 右边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeHeaderR: function (component,val){
		var prop = "borderSizeHR"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		dhxgantt_designer.updateStyleByKey(component,'borderSizeHR',val);
		// component.css("border-style",val);
	},
	/**
	*设置边框颜色（内容）
	*/
	setBorderColorContent: function (component,val){
		var prop = "borderColorContent"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		dhxgantt_designer.updateStyleByKey(component,'bocolorContent',val);
	},
	/**
	*设置边框样式（内容）
	*/  
	setBorderStyleContent: function (component,val){
		var prop = "borderStyleContent"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		dhxgantt_designer.updateStyleByKey(component,'bostyleContent',val);
		// component.css("border-style",val);
	},
	/**
	 * 上边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeContentT: function (component,val){
		var prop = "borderSizeContentT"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		dhxgantt_designer.updateStyleByKey(component,'borderSizeContentT',val);
		// component.css("border-style",val);
	},
	/**
	 * 下边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeContentB: function (component,val){
		var prop = "borderSizeContentB"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		dhxgantt_designer.updateStyleByKey(component,'borderSizeContentB',val);
		// component.css("border-style",val);
	},  
	/**
	 * 左边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeContentL: function (component,val){
		var prop = "borderSizeContentL"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		dhxgantt_designer.updateStyleByKey(component,'borderSizeContentL',val);
		// component.css("border-style",val);
	},
	/**
	 * 右边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeContentR: function (component,val){
		var prop = "borderSizeContentR"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		dhxgantt_designer.updateStyleByKey(component,'borderSizeContentR',val);
		// component.css("border-style",val);
	},
	/**
	*设置边框颜色（内容）
	*/
	setBorderColorBody: function (component,val){
		var prop = "borderColorBody"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		dhxgantt_designer.updateStyleByKey(component,'bocolorBody',val);
	},
	/**
	*设置边框样式（内容）
	*/
	setBorderStyleBody: function (component,val){
		var prop = "borderStyleBody"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		dhxgantt_designer.updateStyleByKey(component,'bostyleBody',val);
		// component.css("border-style",val);
	},
		/**
	 * 上边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeBodyT: function (component,val){
		var prop = "borderSizeBT"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		dhxgantt_designer.updateStyleByKey(component,'borderSizeBT',val);
		// component.css("border-style",val);
	},
	/**
	 * 下边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeBodyB: function (component,val){
		var prop = "borderSizeBB"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		dhxgantt_designer.updateStyleByKey(component,'borderSizeBB',val);
		// component.css("border-style",val);
	},  
	/**
	 * 左边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeBodyL: function (component,val){
		var prop = "borderSizeBL"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		dhxgantt_designer.updateStyleByKey(component,'borderSizeBL',val);
		// component.css("border-style",val);
	},
	/**
	 * 右边框尺寸
	 * @param {[type]} component [description]
	 * @param {[type]} val       [description]
	 */
	setBorderSizeBodyR: function (component,val){
		var prop = "borderSizeBR"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		dhxgantt_designer.updateStyleByKey(component,'borderSizeBR',val);
		// component.css("border-style",val);
	},
	//分隔符颜色
	setSplitColor : function(component,val){
		var prop = "splitColor"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		dhxgantt_designer.updateStyleByKey(component,'splitColor',val);
	},
	//分隔符宽度
	setSplitWidth : function(component,val){
		var prop = "splitWidth"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		if(val){
			val += 'px';
		}
		dhxgantt_designer.updateStyleByKey(component,'splitWidth',val);
	},

	setProgressColor : function(component,val){
		var prop = "progressColor"; 
		dhxgantt_designer.setDataRule(component,prop,val);
		dhxgantt_designer.updateStyleByKey(component,'progressColor',val);
	},
	
	
	getStyler: function (component){
		var sid = dhxgantt_designer.getStylerId(component);
		var styler = component.find('#'+sid);
		if(!styler.length){
			component.append("<input id=\""+sid+"\" type=\"hidden\" value=\"\" />");
			return new Object();
		}else{
			return styler.val()?$.parseJSON(styler.val()) : new Object();
		}
	},
	getStyleBox: function (component){
		var sid = dhxgantt_designer.getStyleBoxId(component);
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
		var sid = dhxgantt_designer.getStylerId(component);
		$('#'+sid).val(JSON.stringify(styler));
	},
	updateStyleBox: function (component,styler){
		var id = component.attr('id');
		var _id = '#'+id;
		
		var _default_container = _id + " .gantt_container{";
		if(styler['bocolorAll']){
			//表头边框样式
			_default_container += 'border-color : '+styler['bocolorAll']+";";
		}
		if(styler['bostyleAll']){
			//表头边框样式
			_default_container += 'border-style : '+styler['bostyleAll']+";";
		}
		_default_container += "}";

		var _default_grid = _id + " .gantt_grid{";
		if(styler['bocolorSplit']){
			//表头边框样式
			_default_grid += 'border-right-color : '+styler['bocolorSplit']+";";
		}
		if(styler['bostyleSplit']){
			//表头边框样式
			_default_grid += 'border-right-style : '+styler['bostyleSplit']+";";
		}
		_default_grid += "}";

		//表头
		var _default_header = _id+" .gantt_grid_scale .gantt_grid_head_cell,"+_id+" .gantt_task_scale .gantt_scale_cell{";  
		if(styler['titleBgColor']){
			//表头颜色
			_default_header += "background-right-color:"+styler['titleBgColor']+";";
		}
		if(styler['bocolorHeader']){
			//表头边框样式
			_default_header += 'border-right-color : '+styler['bocolorHeader']+";";
		}
		if(styler['bostyleHeader']){
			//表头边框样式
			_default_header += 'border-right-style : '+styler['bostyleHeader']+";";
		}
		//表头边框尺寸
		if(styler['borderSizeHR']){
			_default_header += "border-right-width:"+styler['borderSizeHR']+" !important;";
		}
		_default_header += "}";
		//表头
		var _default_header_row = _id + " .gantt_grid_scale,"+_id + " .gantt_task_scale{";
		if(styler['borderSizeHB']){
			_default_header_row += "border-bottom-width:"+styler['borderSizeHB']+" !important;";
		}
		if(styler['bocolorHeader']){
			//表头边框样式
			_default_header_row += 'border-bottom-color : '+styler['bocolorHeader']+" !important;";
		}
		if(styler['bostyleHeader']){
			//表头边框样式
			_default_header_row += 'border-bottom-style : '+styler['bostyleHeader']+" ;";
		}
		_default_header_row += "}";
		
		
		//表格内容演示
		var _default_content =  _id+" .gantt_row .gantt_cell,"+_id+" .gantt_task_row .gantt_task_cell{";
		//表格内容
		if(styler['bocolorContent']){
			//表头边框样式
			_default_content += 'border-right-color : '+styler['bocolorContent']+" !important;;";
		}
		if(styler['bostyleContent']){
			//表头边框样式
			_default_content += 'border-right-style : '+styler['bostyleContent']+" ;";
		}
		//表格内容边框样式
		if(styler['borderSizeContentR']){
			_default_content += "border-right-width:"+styler['borderSizeContentR']+" !important;";
		}
		//表格内容边框样式
		_default_content += "}";


		var _default_content_row = _id + " .gantt_row,"+_id + " .gantt_task_row{";
		//表格内容
		if(styler['bocolorContent']){
			//表头边框样式
			_default_content_row += 'border-bottom-color : '+styler['bocolorContent']+" !important;;";
		}
		if(styler['bostyleContent']){
			//表头边框样式
			_default_content_row += 'border-bottom-style : '+styler['bostyleContent']+" ;";
		}
		if(styler['borderSizeContentB']){
			_default_content_row += "border-bottom-width:"+styler['borderSizeContentB']+" !important;";
		}
		//表格内容边框样式
		_default_content_row += "}";


		var _default_row = _id+" .gantt_task_row,"+_id+" .gantt_row{";
		if(styler['bgcolor']){
			_default_row += "background-color:"+styler['bgcolor']+";";
		}
		//表格内容边框样式
		_default_row += "}";

		//表格内容边框样式
		var _default_selected = _id+" .gantt_selected{";
		if(styler['bgcolor_s']){
			_default_selected += "background-color:"+styler['bgcolor_s']+";";
		}
		_default_selected += "}";

		//表格内容边框样式
		var _default_row_h = _id+" .gantt_task_row:hover,"+_id+" .gantt_row:hover{";
		if(styler['bgcolor_h']){
			_default_row_h += "background-color:"+styler['bgcolor_h']+";";
		}
		_default_row_h += "}";
		
		//表格进度条样式
		var _default_progress = _id+" .gantt_task_progress,"+_id+" .gantt_task_line{";
		if(styler['progressColor']){
			_default_progress += "background-color:"+styler['progressColor']+";";
		}
		_default_progress += "}";

		//分隔符样式
		var _default_split = _id+" .gantt_grid_column_resize_wrap_my .gantt_grid_column_resize_my{";
		if(styler['splitColor']){
			_default_split += "background-color:"+styler['splitColor']+";";
		}
		if(styler['splitWidth']){
			_default_split += "width:"+styler['splitWidth']+";";
		}
		_default_split += "}";
		

		var stylebox = dhxgantt_designer.getStyleBox(component);
		stylebox.html(_default_container + _default_grid+_default_header+_default_header_row+_default_content+_default_content_row+_default_row+_default_selected+_default_row_h+_default_progress+_default_split);
	},
	updateStyleByKey: function (component,key,val){
		var styler = dhxgantt_designer.getStyler(component);
		styler[key] = val;
		dhxgantt_designer.updateStyler(component,styler);
		dhxgantt_designer.updateStyleBox(component,styler);
	},
	setDataRule : function(component,prop,val,unit){
        var lastVal = dhxgantt_designer.getDataRule(component,prop)

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
// var dhxgantt_proto = Object.create(dhxgantt_designer);