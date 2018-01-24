(function($, window, document, undefined) {
	var plugin = "cellWebCab", optionKey = plugin + ".options",
	
    Build = function(o) {
		this.target = $(o);
		this.w = $(document);
		this.option = this.target.data(optionKey).options;
		this._init($(this));
		
	};
	
	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string' && (options in $.fn[plugin].methods)) {
			var p = this.data(plugin);
			if (!plugin) {
				this.data("cellWebCab", new Plugin(this, params));
            } 
			return p ? p[options].apply(p, Array.prototype.slice.call(
					arguments, 1)) : $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i, o) {
			var state = $.data(o, optionKey);
			if (state) {
				$.extend(true, state.options, options);
			} else {
				$.data(o, optionKey, {
					options : $
							.extend(true, {}, $.fn[plugin].defaults, options),
					datas : param
				});
			}
			$.data(o, plugin, new Build(o));
		});
	};

	$.fn[plugin].defaults = {
			/*width:'100%',
			height:'{{height}}',
			method:'post',
			url:'${contextPath}/rs/isdp/cellFillForm/tableList',
			fitColumns:false,
			striped:true,
			rownumbers:true,
			singleSelect:true,
			nowrap:false,
			queryParams:{
				indexId:'${param.indexId}',
				data_id:'${param.data_id}',
				fileDotFileId:'${param.fileID}',
				systemName:'${param.systemName}'
			},
			columns:[[
				{field:'id',title:'id',hidden:true},
				{field:'fileDotFileId',title:'fileDotFileId',hidden:true},
				{field:'name',title:'名称',align:'left'}
			]],
			onLoadSuccess:function(data){
				if(data.rows.length>0){
					$("#cellGrid").datagrid('selectRow',0);
				}
			},
			onSelect:function(rowIndex,rowData){
				openCell(rowData.id);
			}	*/
	};

	$.fn[plugin].methods = {
		_init : function(e){
			var that = this,
			    $target = that.target,
			    $opt = that.option;
			/*that.openCell({
				systemName : 'default',
				fileId : ''
			});*/
			$target.find("img").css("display","none");
			$target.css("width",$opt.width);
			$target.css("height",$opt.height);
		},
		
		 DetectActiveX : function(){
			try {
				new ActiveXObject('CELLWEB5.CellWebCtrl.1');
			} catch (e) {
				alert("未安装华表插件，请先下载安装！");
				return false;
			}
			return true;
		},
		__linkageControl : function(param){
			var that = this;
			var $opt = that.option;
			
			$opt.rid = $opt.rid;
			$opt.params = JSON.stringify(param);
			that.openCell($opt);
		},
		openCell : function(param){
			var params = encodeURIComponent(param.params);
			var url = contextPath + "/mx/form/cellset/downLoadFile?rid="+param.rid+"&params="+params;
			if(this.DetectActiveX()==true){
				CellWeb.Login('炎晟软件','','11100101387','1120-1235-0064-6005');
				CellWeb.LocalizeControl(2052);// (&H804);
				CellWeb.style.width = param.width;
				CellWeb.style.height = param.height;
//				var flag = CellWeb.OpenFile("<%=request.getAttribute("filePath")%>",""); 

				var flag = CellWeb.OpenFile(url,"");
				switch(flag){
					case -1:
						alert("文件不存在");
						break;
					case -2:
						alert("文件操作错误");
						break;
					case -3:
						alert("文件格式错误");
						break;
					case -4:
						alert("密码错误");
						break;
					case -5:
						alert("不能打开高版本文件");
						break;
					case -6:
						alert("不能打开特定版本文件");
						break;
					case -99:
						alert("不能下载文件");
						break;
					default:
						break;
				}
				CellWeb.ShowHScroll(1,0);
				CellWeb.ShowVScroll(1,0);
				CellWeb.WorkbookReadonly = true;
			}
		}

	};
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery, window, document);

