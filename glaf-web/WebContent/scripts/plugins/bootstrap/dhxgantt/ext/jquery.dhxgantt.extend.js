! function($) {
	var plugin = 'dhxgantt';

	var Build = function(target, state) {
		this.target = target;
		this.state = state;
		this.options = state.options;
		this._init();
	};
	$.parser.plugins.push(plugin);
	$.fnInit(plugin, function(target) {

		var state = $.data(target, plugin + '.options');
		var columns = $(target).data("columns");
		if (columns && columns.length > 0) {
			state.options.columns = eval('(' + columns + ')');
		}
		var p = new Build(target, state);

		$.data(target, plugin, p);
	});

	$.fn[plugin].defaults = {
		// saveFormDataUrl : contextPath + "/mx/form/gantt/saveFormData2",
		// saveFormDataUrl : contextPath + "/mx/form/data/gridData",
		datas : null,
		url:"",
		visible:true,	//显示
		events: {
			onClickRow: function(index, row) {},
			onDblClickRow: function(index, row) {},
			onTaskSelected: function(index, row) {}
		}
	};

	$.fn[plugin].methods = {
		gantt : null,	//dhxgantt的对象
		$target : null,	//对象
		columns : null,	//数据集映射信息
		mapping : null,	//更新集映射信息
		keyMap : null,	//甘特图的特殊字段表示
		modifyIds : [],	//修改过或更新过的id
		insertIds : [],	//新增节点的id
		deleteIds : [],	//删除节点的真实ID
		exportToPDF : function(){
			//导出PDF文件
			this.gantt.exportToPDF()
		},
		exportToPNG : function(){
			//导出图片
			this.gantt.exportToPNG();
		},
		setDataFormat : function(){
			gantt.config.date_grid = "%d/%m/%Y";
		},
		_setConfig : function(){
			var that = this;
			var gantt = that.gantt;

		

			//设置表格内容中的开始时间的格式
			gantt.config.date_grid = "%Y-%m-%d %H:%M:%s";
			// gantt.templates.date_grid = function(date){
			// 	debugger;
			//     return gantt.date.date_to_str(gantt.config.date_grid)(date);
			// };

			//设置增加任务时，是否内置弹窗.
			gantt.config.details_on_create = false;
			//双击弹出修改界面
			gantt.config.details_on_dblclick = true;
			//设置是否允许拖拽任务的进度（即任务的完成情况）
			gantt.config.drag_progress = false;
			//是否允许拖拽任务（修改任务的开始时间）
			gantt.config.drag_move = true;
			//设置是否允许任务拖拽长度（这是任务的长度，即周期）
			gantt.config.drag_resize = true;
			//设置是否允许拖拽交换位置
			gantt.config.order_branch = true;

			//连接线
			//大小
			gantt.config.link_arrow_size = 8;
			//属性值名称
			gantt.config.link_attribute = "link_id";
			//连接线宽度
			gantt.config.link_line_width = 3;
			//移动到上面的宽度
			gantt.config.link_wrapper_width = 30;
			//连接线

			//列宽
			gantt.config.min_column_width = 100;
			//行高
			gantt.config.row_height = 40;
			//只读
			gantt.config.readonly = true;
			//显示进度
			gantt.config.show_progress = true;
			//允许排序
			gantt.config.sort = false;


			gantt.config.initial_scroll = true;

			gantt.config.drag_lightbox = true;

			gantt.config.fit_tasks = true;

			//设置显示面板的开始时间，和结束时间
			// gantt.config.start_date = new Date(2017, 10, 1);
			// gantt.config.end_date = new Date(2017, 10, 20);

			//start grid 的属性设置
			gantt.config.grid_width = that.options.gridWidth || 500;	//grid的宽度
			gantt.config.grid_width = parseInt(gantt.config.grid_width);

			//grid调整宽度
			gantt.config.grid_resize = true;

			// gantt.config.autofit = true;
			//end grid 的属性设置

			//start 右边进度栏的属性设置
			//设置/增加时间栏的样式
			gantt.config.subscales = [
      			{unit:"month", step:1, date:"%Y年",template:function(date){
      				return date.getFullYear() + "年" + (date.getMonth() +1) + '月';
      			},css:""},
          	];  
          	//设置持续时间的步长，是以分钟来计算持续事件，还是以小时等
			//即实际持续时间 = 步长 * 记录的 （单位）
			gantt.config.duration_step = 1; 
			//持续时间的单位,时分秒，月等
			// gantt.config.duration_unit = 'hour';
			// gantt.config.duration_unit = 60*60*1000;//an hour
			gantt.config.duration_unit = "day";
			//--------右边表头设置-----------
			gantt.config.scale_unit = 'day';	//默认的表头精度
			gantt.config.step=1;	//默认的表头精度
			gantt.config.date_scale = "%d日";	//表头的时间间隔样式,%Y,%M,%d
			//默认日期的模板
			gantt.templates.date_scale = function(date){
				if(that.gantt.config.scale_unit == 'year'){
					return date.getFullYear() + "年";
				}else if(that.gantt.config.scale_unit == 'month'){
					return (date.getMonth()+1) + "月";
				}else if(that.gantt.config.scale_unit == 'day'){
					return date.getDate() + "日";
				}
			}
			//默认日期的class样式
			gantt.templates.scale_cell_class = "";
			//格子宽度
			gantt.config.min_column_width = 30;
			//表头的高度
			gantt.config.scale_height = 20*3;
			//修改默认的格式获取方法。
			gantt._scale_helpers.primaryScale = function(){
				var gantt = that.gantt;
				gantt.templates.date_scale || gantt._init_template("date_scale");
				return {
					unit: gantt.config.scale_unit,
					step: gantt.config.step,
					template : gantt.templates.date_scale,
					date : gantt.config.date_scale,
					css: gantt.templates.scale_cell_class
				};
			};
			//end 右边进度栏的属性设置
			// gantt._click = gantt._click;

			//周末时显示灰色
			gantt.templates.scale_cell_class = function(date){
	            if(date.getDay()==0||date.getDay()==6){
	                return "weekend";
	            }
	        };
	        gantt.templates.task_cell_class = function(item,date){
	            if(date.getDay()==0||date.getDay()==6){
	                return "taskweekend"
	            }
	        };

	        //设置日期格式
	        gantt.templates["xml_date"] = function(dateStr){
	        	return hmtdUtils.parseDate(dateStr);
	        }
		},
		_setCh : function(){
			var that = this;

			that.gantt.locale = {
				date:{
					month_full:["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
					month_short:["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
					day_full:["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
					day_short:["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"]
				},
				labels : {
				"new_task": "新任务",
				"icon_save": "保存",
				"icon_cancel": "取消",
				"icon_details": "详情",
				"icon_edit": "编辑",
				"icon_delete": "删除",
				"confirm_closing": "",
				"confirm_deleting": "是否确认删除?",
				"section_description": "描述",
				"section_time": "时间间隔",
				"section_type": "类型",
				"column_text": "任务名称",
				"column_start_date": "开始事件",
				"column_duration": "持续时间",
				"column_add": "",
				"link": "连线",
				"confirm_link_deleting": "即将删除",
				"link_start": " (开始)",
				"link_end": " (结束)",
				"type_task": "任务",
				"type_project": "项目",
				"type_milestone": "里程碑",
				"minutes": "分",
				"hours": "时",
				"days": "天",
				"weeks": "周",
				"months": "月",
				"years": "年",
				"message_ok": "是",
				"message_cancel": "否"
				}
			}
		},
		_initTool : function(){
			var that = this;
			var $tool = $("<div class='dhx-tool'></div>");
			$tool.append("<div class='btn dhx-btn' operation='save'>保存</div>");
			that.$tool = $tool;
			that.$target.append($tool);
		},
		resize : function(){
			var that =this;
			that.$dhxContent.height(that.$target.height() -  (that.$tool?that.$tool.outerHeight(true):0));
		},
		convertToDefaultGantt : function(data){
			var that = this;
			var keyMap = that.options.keyMap;
			$.each(keyMap,function(key,value){
				data[value] = data[key];
			});
			data.startKey && (data.startKey = hmtdUtils.parseDate(data.startKey).getTime());
			data.endKey && (data.endKey = hmtdUtils.parseDate(data.endKey).getTime());
			return data;
		},
		_uploadFile : function(file,callback){
			var that = this;
			that.gantt.importFromMSProject({
				data: file,
				callback: function(project){
					if(project){
						that.gantt.clearAll();

						if(project.config.duration_unit){
							that.gantt.config.duration_unit = project.config.duration_unit;
						}

						that.gantt.parse(project.data);
					}

					if($.isFunction(callback)){
						callback(project);
					}
				}
			});
		},
		_updateDataAjax : function(){
			var that = this;
			var deletedTaskIds = [];
			var tasksDatas = [];

			$.each(that.modifyIds,function(i,id){
				//修改的数据
				var item = $.extend(true,{},that.gantt.getTask(id));
				item.id = item._originId;	//将id修改回来
				tasksDatas.push(that.convertToDefaultGantt(item));
			});
			$.each(that.insertIds,function(i,id){
				//修改的数据
				var item = $.extend(true,{},that.gantt.getTask(id));
				delete item.id;
				// item.id = item._originId;	//将id修改回来
				tasksDatas.push(that.convertToDefaultGantt(item));
			});
			$.each(that.deleteIds,function(i,id){
				var item = $.extend(true,{},that.gantt.getTask(id));
				deletedTaskIds.push(item._originId);
			});

			var modelsData = [];
			var data = null;
			tasksDatas.length > 0 && (data = data || {},data.tasks = tasksDatas);
			deletedTaskIds.length > 0 && (data = data || {},data.deletedTaskIds = deletedTaskIds);
			data && modelsData.push(data);

			var params = {
				rid : that.options.rid || "",
				models : modelsData
			};
			
			var ajaxOption = {
		        url: that.options.saveFormDataUrl,
		        async: true,
		        contentType : 'application/json',
		        data : JSON.stringify(params),
		        type: 'POST',
		        dataType: 'json',
		        success : function(retdata){
		            if(typeof retdata == 'string'){
		                retdata = JSON.parse(retdata);
		            }
		            if(retdata.statusCode == '200'){
		            	alert(retdata.msg || '保存成功');
		            }else{
		                alert(retdata.msg || '保存失败');
		            }
		        }
		    }
		    $.ajax(ajaxOption);
		},
		_initClientEvent : function(){
			var that = this;
			$(this.target).resize(function() {
				that.resize.call(that);
			});

			that.gantt.attachEvent("onBeforeTaskUpdate", function(id,new_item){
				//任务修改前
				return;
			})
			that.gantt.attachEvent("onAfterTaskUpdate", function(id,new_item){
				//任务修改后，用于记录信息，用于更新操作
				that.convertToOriginData({data:[new_item],links:[]});
				that.modifyIds.push(id);
			})
			that.gantt.attachEvent("onAfterTaskDelete", function(id,item){
				//任务删除前，保存删除信息
				that.deleteIds.push(item._originId);
			});
			that.gantt.attachEvent("onAfterTaskAdd", function(id,item){
				//任务新增时，保存新增信息
				if(item.start_date && item.duration){
					item.endDate = new Date(item.start_date.getTime() + item.duration * 24 * 60 * 60 * 1000);	
				}
				that.convertToOriginData({data:[item],links:[]});
				that.insertIds.push(id);
			});
			//任务选择事件
			that.gantt.attachEvent("onTaskSelected", function(id,item){
				if($.isFunction(that.options.events.onTaskSelected)){
					that.options.events.onTaskSelected(id,item);
				}
				return true;
			});
			//任务点击事件
			that.gantt.attachEvent("onTaskClick", function(id,e){
				if($.isFunction(that.options.events.onClickRow)){
					that.options.events.onClickRow(id,e);
				}
				if(that.options.showTip){
					that.gantt.showQuickInfo(id);	
				}
				return true;
			});
			//任务双击事件
			that.gantt.attachEvent("onTaskDblClick", function(id,e){
			    if($.isFunction(that.options.events.onDblClickRow)){
					that.options.events.onDblClickRow(id,e);
				}
				return true;
			});

			that.gantt.addTasks = function(items) {
				var thatgantt = this;
				$.each(items,function(k,item){
					var parent = null;
					var index = null;
					if (!that.gantt.defined(item.id))
						item.id = that.gantt.uid();

				    if (!that.gantt.defined(parent) || parent == null) parent = thatgantt.getParent(item) || 0;
				    if (!thatgantt.isTaskExists(parent)) parent = 0;
				    thatgantt.setParent(item, parent);
				    item = thatgantt._init_task(item);

				    if (thatgantt.callEvent("onBeforeTaskAdd", [item.id, item])===false) return false;

				    thatgantt._pull[item.id] = item;

					thatgantt._add_branch(item, index);
					thatgantt._sync_order(true);
					thatgantt.callEvent("onAfterTaskAdd", [item.id, item]);
				})
			    thatgantt.refreshData();
				thatgantt._adjust_scales();
			};
			// that.gantt.attachEvent("onAfterLinkUpdate", function(id,item){
			// 	//连线修改时，保存新增信息
			// 	that.convertToOriginData({data:[item],links:[]});
			// 	that.insertIds.push(id);
			// });

		},
		changeDurationUnit : function(value){
			var that = this;
			var gantt = that.gantt;
			if(!value || gantt.config.duration_unit == value){
				return;
			}
			//修改甘特图的精度
			gantt.config.duration_unit = value;
			//修改精度时，修改表头的设置
			if(value == 'year'){
				//--------修改右边表头设置-----------
				gantt.config.scale_unit = 'year';	//默认的表头精度
				gantt.config.date_scale = "%Y年";	//表头的时间间隔样式,%Y,%M,%d  
			}if(value == 'month'){
				gantt.config.subscales = [
	      			{unit:"year", step:1, date:"%Y年",template:function(date){
	      				return date.getFullYear() + "年";
	      			},css:""},
	          	];  
	          	//--------修改右边表头设置-----------
				gantt.config.scale_unit = 'month';	//默认的表头精度
				gantt.config.date_scale = "%M月";	//表头的时间间隔样式,%Y,%M,%d
			}else if(value == 'day'){
				gantt.config.subscales = [
	      			{unit:"month", step:1, date:"%M月",template:function(date){
	      				return date.getFullYear() + "年" + (date.getMonth() +1) + '月';
	      			},css:""},
	          	];
	          	//--------修改右边表头设置-----------
				gantt.config.scale_unit = 'day';	//默认的表头精度
				gantt.config.date_scale = "%d日";	//表头的时间间隔样式,%Y,%M,%d  
			}else{
				gantt.config.subscales = [];
			}
			var originalDatas = $.extend(true,{},that.gantt._pull);
			that.$dhxContent.empty();
			that.gantt.init(that.$dhxContent[0].id);
			// that.query({});
			// that.gantt.parse(originalDatas);

			// that.gantt.refreshData();
		},
		//修改甘特图的精度
		upDurationUnit : function(){
			var that = this;
			var gantt = that.gantt;
			that.nowDurationUnitNum > 0 && (that.nowDurationUnitNum--);
			
			that.changeDurationUnit(that.durationUnitAry[that.nowDurationUnitNum]);
		},
		downDurationUnit : function(){
			var that = this;
			var gantt = that.gantt;
			that.nowDurationUnitNum < 2 && (that.nowDurationUnitNum++);
			
			that.changeDurationUnit(that.durationUnitAry[that.nowDurationUnitNum]);
		},
		_init : function(){
			var that = this;
			var options = that.options;
			var $target = $(that.target);
			that.$target = $target;

			that.durationUnitAry = ["year","month","day"];
			that.nowDurationUnitNum = 2;

			that.nowExpandLevel = 1;
			//展开节点层级数
			that.expandChild = that.options.expandChild;

			var gantt = Gantt.getGanttInstance();
			that.gantt = gantt;

			//系统内置按钮，保存
			// that._initTool();
			var $dhxContent = $("<div></div>");
			$dhxContent.attr("id",that.target.id + "_dhx");
			$dhxContent.css({width:"100%",height:"100%"});
			that.$dhxContent = $dhxContent;
			that.$target.append($dhxContent);
			//初始化系统内置的事件
			that._initClientEvent();

			//转义中文
			that._setCh();
			
			gantt.templates.task_time = function(start, end, ev){
				//设置任务时间的提示信息
				return mtDate.formatDatetime(start,"yyyy年MM月dd") + " 至 " + mtDate.formatDatetime(end,"yyyy年MM月dd");
			}
			gantt.templates.task_date = function(date){
				//设置任务时间的提示信息
				return mtDate.formatDatetime(date,"yyyy年MM月dd");
			}
			//信息框的信息值
			gantt.config.lightbox.sections = [
			    {name:"description", height:70, map_to:"text", type:"textarea",focus:true},
			    {name:"time", type:"duration", map_to:"auto",time_format:["%Y", "%m", "%d"]}
			];

			//设置左边的grid的列信息
			gantt.config.columns = options.columns;
			gantt.config.columns[0].tree = true;
			// gantt.config.columns[1].template = function(data){
			// 	// return "<div>sssss</div>";
			// }
			$.each(gantt.config.columns,function(i,item){
				if(that.options.resizeColumn && i < (gantt.config.columns.length -1)){
					item['resize'] = true;
				}
				if(typeof item['width'] == 'string' && item['width'].indexOf("%") == -1){
					item['width'] = parseInt(item['width'].replace('px',''));	
				}
				// delete item['width'];
			})
			// gantt.config.columns.push({name:"add",        label:"" });
			

			that.keyMap = that.convertKeyMap(that.options.keyMap);
			//设置初始化参数
			that._setConfig();
			//记录原始请求参数
			that.options.readSub = $.extend(true,{},that.options.read);
			//甘特图初始化
			gantt.init($dhxContent[0].id);
			//异步事件修改
			if(that.options.isSync){
				//修改其判断子节点的方法，如果是异步，则每个都有子节点，且
				//在展开时，重新查询
				gantt._has_children = function(id){
					return true;
				};

				gantt._click.gantt_open = gantt.bind(function (e, id, trg) {
					$(e.target).addClass("gantt_loading");
					//异步加载子节点信息
					var item = this.getTask(id);
					if(item["_opened"]){
						that.gantt.selectTask(id);
						that.gantt.open(id);
						return false;
					}

					var level = $(e.target).closest(".gantt_row").attr("aria-level");
					//查询其子节点
					var params = {};
					params["parent_id"] = item[that.options.keys.idKey];
					params[that.options.keys.parentKey] = item[that.options.keys.idKey];
					that.query({
						id : item[that.options.keys.idKey],
						parentId : item[that.options.keys.parentkey],
						params : JSON.stringify(params)
					},true,function(data){
						item["_opened"] = true;
						that.gantt.selectTask(id);
						that.gantt.open(id);

						that._autoExpand(parseInt(level)+1);
					})
					return false;
				}, gantt);
			}

			//修改表列的高度调整
			gantt._set_sizes = function(){
				this._do_autosize();

				var boxSizes = this._get_box_styles();
				this._y = boxSizes.innerHeight;

			    if (this._y < 20) return;

				//same height
				this.$grid.style.height = this.$task.style.height = Math.max(this._y - this.$scroll_hor.offsetHeight - 2, 0) +"px";

				var dataHeight = Math.max((this._y - (this.config.scale_height||0) - this.$scroll_hor.offsetHeight - 2 - 17), 0);
			    this.$grid_data.style.height = this.$task_data.style.height =  dataHeight + "px";

				//share width
				var gridWidth = Math.max(this._get_grid_width()-1, 0);
				this.$grid.style.width =  gridWidth +"px";
				this.$grid.style.display = gridWidth === 0 ? 'none' : '';

				boxSizes = this._get_box_styles();
				this._x = boxSizes.innerWidth;

				if (this._x < 20) return;

			    this.$grid_data.style.width = Math.max(this._get_grid_width()-1, 0) +"px";
				this.$task.style.width = Math.max(this._x - this._get_grid_width() - 2, 0) +"px";
			};

			//修改表列宽度调整和gird与进度条宽度调整
			gantt._render_grid_header_resize = function(){
				that.buildSplit();
			}

			if(options.data){
				that._ajaxSuccess(options.data);
			}else{
				that.query();
			}

		},
		//中间的分隔符
		buildSplit : function(obj,config){
			var that = this;

			var columns = that.gantt.getGridColumns(),
				width = 0,
				lineHeigth = that.gantt.config.scale_height;
			var $gridHeadCell = $(that.gantt.$grid_scale).find(".gantt_grid_head_cell");
			for (var i = 0; i < columns.length; i++) {
				var last = i == columns.length - 1;
				var col = columns[i];
				width += col.width;

				if (col.resize) {
					var resize_el = document.createElement("div");
					resize_el.className = "gantt_grid_column_resize_wrap_my";
					resize_el.style.top = "0px";
					resize_el.style.height = lineHeigth + "px";
					resize_el.innerHTML = "<div class='gantt_grid_column_resize_my'></div>";
					resize_el.setAttribute(that.gantt.config.grid_resizer_column_attribute, i);

					// that.gantt._waiAria.gridSeparatorAttr(resize_el);

					$($gridHeadCell[i]).after(resize_el);

					// that.gantt.$grid_scale.appendChild(resize_el);
					// resize_el.style.left = Math.max(0, width) + "px";
				}
			}
			$(that.gantt.$grid_scale).find(".gantt_grid_column_resize_wrap_my").bind("mousedown",function(e){
				e.stopPropagation();
				e.preventDefault();
				
				config.original_target = {target: e.target || e.srcElement};

				var $prevHeadCell = $(this).prev(".gantt_grid_head_cell");
				if (that.gantt.config.touch) {
					that.gantt.clearDragTimer();

					that.gantt._drag_start_timer = setTimeout(that._gridColumnDragStart(this,$prevHeadCell, e), 500);
				}
				else {
					that._gridColumnDragStart(this,$prevHeadCell, e);
				}
			})

			//先清除，后重新创建
			$(".gantt_grid_resize_wrap_my").remove();
			if (that.gantt.config.grid_resize) {
				//创建整体的拖拽框
				var grid_resize_el = document.createElement("div");
				grid_resize_el.className = "gantt_grid_resize_wrap_my";
				grid_resize_el.style.top = "0px";
				grid_resize_el.style.height = that.gantt.$grid.offsetHeight + "px";
				grid_resize_el.innerHTML = "<div class='gantt_grid_resize_my'></div>";
				grid_resize_el.setAttribute(that.gantt.config.grid_resizer_attribute, "true");

				that.gantt.$container.appendChild(grid_resize_el);
				grid_resize_el.style.left = Math.max(0, that.gantt.config.grid_width + 5) + "px";
				that.grid_resize_el = grid_resize_el;
				that.$grid_resize_el = $(grid_resize_el);

				config = config || that.gantt._settings || {};
				if (config) {
					that.gantt._settings = config;
				}
				//中间分隔符的拖拽功能
				$(grid_resize_el).bind("mousedown",function(e){
					config.original_target = {target: e.target || e.srcElement};
					if (that.gantt.config.touch) {
						that.gantt.clearDragTimer();

						that.gantt._drag_start_timer = setTimeout(that._gridDragStart(grid_resize_el, e), 500);
					}
					else {
						that._gridDragStart(grid_resize_el, e);
					}
				})
			}

			// var $wrap = that.$target.find(".gantt_grid_column_resize");
			// that.$target.find(".gantt_grid_scale").unbind("mousedown");
			// $wrap.unbind("mousedown");
			// $wrap.on("mousedown",function(e){
			// 	e.preventDefault();
	  //         	e.stopPropagation();

	  //         	that._gridDragStart($(this),e);
			// })
			//增加表列的拖拽功能
			// that.$target.on("mousedown","gantt_grid_column_resize_wrap",function(e){
			// 	e.preventDefault();
	  //         	e.stopPropagation();

	  //         	that._gridDragStart($(this),e);
			// })
		},
		getPosition: function (e) {
			var x = 0, y = 0;
			e = e || window.event;
			if (e.pageX || e.pageY) {
				x = e.pageX;
				y = e.pageY;
			} else if (e.clientX || e.clientY) {
				x = e.clientX + document.body.scrollLeft + document.documentElement.scrollLeft;
				y = e.clientY + document.body.scrollTop + document.documentElement.scrollTop;
			}
			return {x: x, y: y};
		},
		//grid的列宽拖拽调整
		_gridColumnDragStart: function (obj,$prevHeadCell, e) {
			var that = this;

			//增加罩层
			if(!that.marker){
				var marker = document.createElement("div");
				marker.innerHTML = "";
				marker.className += "gantt_drag_marker gantt_grid_resize_area";

				that.marker = marker;
				$("body")[0].appendChild(marker);
			}

			that.marker.style.height = that.gantt.$grid.offsetHeight + "px";
			that.marker.style.top = $(that.gantt.$grid).offset().top + "px";
			that.marker.style.width = $prevHeadCell.width() + "px";
			that.marker.style.left = $prevHeadCell.offset().left +'px';
			
			//movemove事件必须绑定到$(document)上，鼠标移动是在整个屏幕上的  
            $(document).bind("mousemove",function(event1){
            	if (event1 && event1.preventDefault) //Cancel default action on DND
					event1.preventDefault();
				(event1).cancelBubble = true;
				// if (gantt.defined(this.config.updates_per_second)) {
				// 	if (!gantt._checkTimeout(this, this.config.updates_per_second))
				// 		return true;
				// }
            	that._gridColumnDragMove(obj,$prevHeadCell,event1);
            });  
            //此处的$(document)可以改为obj  
            $(document).bind("mouseup",function(event){
            	//解绑定，这一步很必要，前面有解释  
                $(document).unbind("mousemove");
                $(document).unbind("mouseup");
                that._gridColumnDragEnd(obj,$prevHeadCell,event);
            });
		},
		//grid的列宽拖拽调整
		_gridColumnDragMove: function (obj,$prevHeadCell, e) {
			var that = this;
			var source = obj;
			if (!source) return;

			var pos = that.getPosition(e);
			if(pos.x - that.marker.offsetLeft < 50){
				that.marker.style.width = '50px';	
			}else if(pos.x > Math.max(0, that.gantt.config.grid_width) -50){
				that.marker.style.width = (Math.max(0, that.gantt.config.grid_width) -50 - $prevHeadCell.offset().left) + 'px';
			}else{
				that.marker.style.width = (pos.x - that.marker.offsetLeft) + 'px';
			}
			
			// that.marker.style.left = pos.x + "px";
			// that.marker.style.top = pos.y + "px";
			

			// if (!that.gantt.config.ignore) {
			// 	source.pos = that.getPosition(source);
				
			// 	that.gantt.callEvent("onDragMove", [obj, source]);
			// 	return false;
			// }
		},
		//grid的列宽拖拽调整
		_gridColumnDragEnd: function (obj,$prevHeadCell,e) {
			var that = this;
			//判断是否允许
			var pos = that.getPosition(e);
			var width = pos.x - that.marker.offsetLeft;
			if(width < 50){
				//移动到100
				width = 50;
			}else if(width > (Math.max(0, that.gantt.config.grid_width) -50)){
				width = (Math.max(0, that.gantt.config.grid_width) -50 - $prevHeadCell.offset().left);
			}
			$prevHeadCell.width(width);
			obj.style.left = '0px';

			var columnIndex = $(obj).attr("column_index");
			var columns = that.gantt.getGridColumns();
			columns[columnIndex].width = width;
			var $row = $(that.gantt.$grid).find(".gantt_row");
			$.each($row,function(i,item){
				$($(item).find(".gantt_cell")[columnIndex]).outerWidth(width);
			})

			var cols_width = 0;
			for (var i = 0; i < columns.length; i++) {
				var v = parseInt(columns[i].width, 10);
				width[i] = v;
				cols_width += v;
			}
			that.gantt.config.grid_width = cols_width;
			that.gantt.$grid_data.style.width = Math.max(cols_width-1, 0) +"px";
			that.gantt.$grid_scale.style.width = Math.max(cols_width-1, 0) +"px";
			that.marker.remove();
			that.marker = null;


			// $(that.gantt.$grid).width();

			// var target = that.gantt.config.backup_element;
			// if (target && target.parentNode) {
			// 	target.parentNode.removeChild(target);
			// }
			// gantt._prevent_touch_scroll = false;
			// if (that.gantt.config.marker) {
			// 	that.gantt.config.marker.parentNode.removeChild(that.gantt.config.marker);
			// 	that.gantt.config.marker = null;

			// 	that.gantt.callEvent("onDragEnd", []);
			// }
			// document.body.className = document.body.className.replace(" gantt_noselect", "");
		},
		//grid的整体宽度拖拽调整
		_gridDragStart: function (obj, e) {
			var that = this;

			//增加罩层
			if(!that.marker){
				var marker = document.createElement("div");
				marker.innerHTML = "";
				marker.className += "gantt_drag_marker gantt_grid_resize_area";

				that.marker = marker;
				$("body")[0].appendChild(marker);
			}

			that.marker.style.height = that.gantt.$grid.offsetHeight + "px";
			that.marker.style.top = $(that.gantt.$grid).offset().top + "px";
			that.marker.style.width = $(that.gantt.$grid).width() + "px";
			that.marker.style.left = $(that.gantt.$grid).offset().left +'px';
			
			//movemove事件必须绑定到$(document)上，鼠标移动是在整个屏幕上的  
            $(document).bind("mousemove",function(event1){
            	if (event1 && event1.preventDefault) //Cancel default action on DND
					event1.preventDefault();
				(event1).cancelBubble = true;
				// if (gantt.defined(this.config.updates_per_second)) {
				// 	if (!gantt._checkTimeout(this, this.config.updates_per_second))
				// 		return true;
				// }
            	that._gridDragMove(obj,event1);
            });  
            //此处的$(document)可以改为obj  
            $(document).bind("mouseup",function(event){
            	//解绑定，这一步很必要，前面有解释  
                $(document).unbind("mousemove");
                $(document).unbind("mouseup");
                that._gridDragEnd(obj,event);
            });
		},
		//grid的整体宽度拖拽调整
		_gridDragMove: function (obj, e) {
			var that = this;
			var source = obj;
			if (!source) return;

			var pos = that.getPosition(e);
			// that.marker.style.left = pos.x + "px";
			// that.marker.style.top = pos.y + "px";
			that.marker.style.width = (pos.x - that.marker.offsetLeft) + 'px';

			// if (!that.gantt.config.ignore) {
			// 	source.pos = that.getPosition(source);
				
			// 	that.gantt.callEvent("onDragMove", [obj, source]);
			// 	return false;
			// }
		},
		//grid的整体宽度拖拽调整
		_gridDragEnd: function (obj,e) {
			var that = this;
			//判断是否允许
			var pos = that.getPosition(e);
			var width = pos.x - $(that.gantt.$grid).offset().left;
			if(width < 100){
				//移动到100
				width = 100;
			}else if(width > ($(that.gantt.$container).width() - 100)){
				width = $(that.gantt.$container).width() - 100;
			}
			$(that.gantt.$grid).width(width);
			$(that.gantt.$task).width(($(that.gantt.$container).width() - width - 7));
			obj.style.left = width + 5 + 'px';

			that.marker.remove();
			that.marker = null;


			// $(that.gantt.$grid).width();

			// var target = that.gantt.config.backup_element;
			// if (target && target.parentNode) {
			// 	target.parentNode.removeChild(target);
			// }
			// gantt._prevent_touch_scroll = false;
			// if (that.gantt.config.marker) {
			// 	that.gantt.config.marker.parentNode.removeChild(that.gantt.config.marker);
			// 	that.gantt.config.marker = null;

			// 	that.gantt.callEvent("onDragEnd", []);
			// }
			// document.body.className = document.body.className.replace(" gantt_noselect", "");
		},
		/**
		 * 将原本的mapping转换为符合dhx的mapping
		 * @param  {[type]} data [description]
		 * @return {[type]}      [description]
		 */
		convertKeyMap : function(data){
			var dhxMapping = {
				"endKey" : "end_date",
				"durationKey" : "Duration",
				"startKey" : "start_date",
				"preTaskKey" : "preTaskKey",	//前置任务，用于生成link
				"nameKey" : "text",
				"indexKey" : "id",
				"pIdKey" : "parent"
				// "type" : "type" ,如数据data.type = 'project'
				// 若type 为 project 则 ，其长度会根据下面的动态变长
				// 若type 为 "milestone" 则，为一个四方形的图片
			}
			var data = $.extend(true,{},data);
			$.each(data,function(key,value){
				dhxMapping[value] && (data[key] = dhxMapping[value])
			})
			return data;
		},
		/**
		 * 将数据转换成符合dhx的数据，即{start_date:''}等
		 * @param  {[type]} ret [description]
		 * @return {[type]}     [description]
		 */
		convertToGanttData : function(ret){
			var that = this;
			var keyMap = that.keyMap;

			var linksData = [];

			$.each(ret.data,function(i,item){
				//保存对象的原始id，用与删除或修改。
				item._originId = item.id;	
				$.each(keyMap,function(key,value){
					// if(value == 'start_date'){
					// 	hmtdUtils.parseDate(item[key]) != null ? hmtdUtils.parseDate(item[key]) : item[key];
					// 	item[key] = mtDate.formatDatetime(item[key],"dd-MM-yyyy HH:mm:ss");
					// }
					
					item[value] = item[key];
				})
				if(item["end_date"]){
					item["end_date"] = hmtdUtils.parseDate(item["end_date"]);
					// item["end_date"] = mtDate.formatDatetime(item["end_date"],"yyyy-MM-dd HH:mm:ss");
				}
				// item.duration = parseFloat(item.duration);
				if(item.preTaskKey){
					//生成Link
					linksData.push({
						id:linksData.length,
						source:item.preTaskKey,
						target:item.id,
						type:"0"
					})
				}
			})
			ret.links = linksData;
			return ret;
		},
		/**
		 * 将符合dhx的数据转换成原始数据等
		 * @param  {[type]} ret [description]
		 * @return {[type]}     [description]
		 */
		convertToOriginData : function(ret){
			var that = this;
			var keyMap = that.keyMap;
			var linksData = [];
			// $.each(ret.data,function(i,item){
			// 	var preTaskKeyName = "";
			// 	$.each(keyMap,function(key,keyValue){
			// 		// if(value == 'start_date'){
			// 		// 	hmtdUtils.parseDate(item[key]) != null ? hmtdUtils.parseDate(item[key]) : item[key];
			// 		// 	item[key] = mtDate.formatDatetime(item[key],"dd-MM-yyyy HH:mm:ss");
			// 		// }
			// 		var value = item[keyValue];
			// 		if(keyValue == 'start_date'){
			// 			value = mtDate.formatDatetime(value,"yyyy-MM-dd HH:mm:ss")
			// 		}
			// 		if(keyValue == 'preTaskKey'){
			// 			preTaskKeyName = key;
			// 		}
			// 		item[key] = value;
			// 	})
			// 	$.each(ret.links,function(k,ktem){
			// 		//设置前置任务
			// 		if(ktem.target == item.id){
			// 			item[preTaskKeyName] = ktem.source;
			// 		}
			// 	})
			// })
		},
		/**
		 * [_ajaxSuccess description]
		 * @param  {[type]} ret        [description]
		 * @param  {[type]} $parentDom [父节点信息]
		 * @return {[type]}            [description]
		 */
		_ajaxSuccess:function(ret,isParent){
			var that = this;
			that.convertToGanttData(ret);
			if(isParent){
				//增加下级节点
				that.gantt.addTasks(ret.data);	
				// if(that.gantt.addTasks){
				// 	that.gantt.addTasks(ret.data);	
				// }else{
				// 	$.each(ret.data,function(i,item){
				// 		that.gantt.addTask(item);	
				// 	})	
				// }
				
				//增加连线
				$.each(ret.links,function(i,item){
					that.gantt.addLink(item);
				})

				that.nowExpandLevel++;
			}else{
				//清楚所有旧节点数据
				that.gantt.clearAll();
				//初始化节点数据
				that.gantt.parse(ret);	
				that._autoExpand(0);

				// that.buildSplit();
			}
			
		},
		_autoExpand : function(n){
			var that = this;
			if(n == null)
				return;
			if(that.expandChild && n < that.expandChild){
				//展开当前层级的节点
				$('[aria-level="'+n+'"] .gantt_tree_icon.gantt_open').trigger("click");
			}
		},
		getParams: function(params) {
			// return params || {};
			var that = this,
				data = that.options.read.d_data__ = (that.options.read.__data__ ? $.extend({}, that.options.read.__data__) : undefined); //取动态参数
			var ret = $.extend({}, data || this.options.read.data);

			if (params && data) { //如果不是第一次查询
				if (!data.params) {
					data.params = {};
				} else {
					data.params = JSON.parse(data.params);
				}
				for (var key in params) {
					if (!(key in data)) {
						data.params[key] = params[key]; //保存要保留参数					
					} else {
						ret[key] = params[key]; //装载变化参数
					}
				}
				data.params = JSON.stringify(data.params);
				ret.params = data.params; //更新要保留参数
			}
			return ret;
		},
		query:function(data,isParent,callback,isRefreshRow){
			var that = this;
			var ar = that.options.read;
			var isParent = isParent;
			if(isParent){
				if(!data || !data.id){
					return;
				}
				var ar = that.options.readSub;
			} 
			ar.success = function(ret) {
				that._ajaxSuccess(ret, isParent);
				if($.isFunction(callback)){
					callback(ret);
				}
			};
			if(isRefreshRow){
				if(!data || !data.indexId){
					return;
				}
				ar.success = function(ret) {
					if($.isFunction(callback)){
						callback(ret);
					}
				};
			}
			if(!isParent && !isRefreshRow){
				that._query_params_ = data;
			}
			data = data || {};
			data = $.extend(true,data,that._query_params_);
			
			//保存查询参数
			var params = that.getParams(data);
			that._request(ar,$.extend(true, {}, params),data?data.id:null);
		},
		_initParams: function(ar, params,id) {
			var data = $.extend(true, {}, ar.d_data__, ar.__data__, params);
			$.extend(data, {
				sort: this.__sortArray
			});
			if(id){
				data.id = id;
			}
			ar.data = this._getParameterMap(data);
		},
		_getParameterMap: function(data) {
			return JSON.stringify(data || {});
		},
		_request: function(ar, params,id) {
			var that = this;
			if (!ar.__success__) { //第一次ajax 调用
				// ar.success = function(ret) {
				// 	that._ajaxSuccess(ret);
				// };
				ar.__success__ = true;
				ar.__data__ = $.extend(ar.__data__ || {}, ar.data); // 原始参数保存
				ar.d_data__ = $.extend(ar.d_data__ || {}, ar.data); // 动态参数保存
			}
			that._initParams(ar, params,id);
			$.ajax(ar);
		},
		/**
		 * 获取选中节点的信息
		 * @return {[type]} [description]
		 */
		getSelectedData: function(){
			var that = this;
			var id = that.gantt.getSelectedId();
			if(id){
				var item = $.extend(true,{},that.gantt.getTask(id));
				item.id = item._originId;
				return item;
			}
			return null;
		},
		getAllData : function(){
			var that = this;
			var datas = that.gantt._pull;
			var retDatas = [];
			$.each(datas,function(k,ktem){
				var item = $.extend(true,{},ktem);
				item.id = item._originId;
				retDatas.push(item);
			})
			return retDatas;
		},
		refreshRowByParam : function(key,value){
			var that = this;
			var datas = that.gantt._pull;
			if(key == 'id'){
				key = _originId;
			}
			$.each(datas,function(k,ktem){
				if(ktem[key] == value){
					var taskData = that.gantt.getTask(ktem.id);
					var params = {};
					params["indexId"] = taskData[that.options.keys.idKey];
					that.query({
						indexId : taskData[that.options.keys.idKey],
						params : JSON.stringify(params)
					},null,function(ret){
						// that.gantt.deleteTask(ktem.id);
						if(ret){
							that.convertToGanttData(ret);
							if(ret.data && ret.data.length > 0){
								var item = ret.data[0];
								item['start_date'] = hmtdUtils.parseDate(item['start_date']);
								$.extend(true,taskData,item);
								// hat.gantt.addTask(ret.data[0]);		
							}
							if(ret.links && ret.links.length > 0){
								that.gantt.addLink(ret.links[0]);
							}
						}
						that.gantt.refreshData();
					},true)
				}
			})
		}, 
		reExpandParentByParam : function(key,value){
			var that = this;
			var datas = that.gantt._pull;
			if(key == 'id'){
				key = _originId;
			}
			$.each(datas,function(k,ktem){
				if(ktem[key] == value){
					var taskData = that.gantt.getTask(ktem.id);
					if(taskData.$level == 0){
						//刷新整个节点
						
					}else{
						taskData = that.gantt.getTask(that.gantt.getPrev(ktem.id));
						
						//重新展开该节点
						//删除一个节点
						that.gantt.deleteTask(ktem.id);
						taskData["_opened"] = false;
						that.gantt.close(taskData.id);
						var taskNode = that.$target.find("[task_id='"+taskData.id+"']");
						taskNode.find(".gantt_tree_icon.gantt_open").trigger("click");
					}
					
				}
			})
		},
		expandNodeByParam : function(key,value){
			var that = this;
			var datas = that.gantt._pull;
			if(key == 'id'){
				key = _originId;
			}
			$.each(datas,function(k,ktem){
				if(ktem[key] == value){
					var taskNode = that.$target.find("[task_id='"+ktem.id+"']");
					taskNode.find(".gantt_tree_icon.gantt_open").trigger("click");
				}
			})
		}
	}

	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor: Build
	});

}(jQuery);