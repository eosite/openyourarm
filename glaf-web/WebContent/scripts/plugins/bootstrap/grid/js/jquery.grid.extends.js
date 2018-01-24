(function($) {
	var plugin = 'grid',
		Build = function(target, state) {
			this.target = target;
			this.state = state;
			this.w = $(document);
			this.options = state.options;
			this._init();
			
			
		},
		hasTouch = 'ontouchstart' in document,hasPointerEvents = (function()
    {
        var el    = document.createElement('div'),
            docEl = document.documentElement;
        if (!('pointerEvents' in el.style)) {
            return false;
        }
        el.style.pointerEvents = 'auto';
        el.style.pointerEvents = 'x';
        docEl.appendChild(el);
        var supports = window.getComputedStyle && window.getComputedStyle(el, '').pointerEvents === 'auto';
        docEl.removeChild(el);
        return !!supports;
    })();
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
		datas: null,
		columns: [],
		__columns: [],
		childrenColumn: 'columns',
		editable: true,
		scrollable: true,
		clickUpdate: false,
		occupy: false,
		selectable: "multiple",
		able: {
			mode: "single"
		},
		autoHeader: true, // 自动合并表头
		sortBy : '',
		ajax: {
			read: {
				url: '',
				type: 'POST',
				dataType: 'JSON',
				data: {},
				contentType: "application/json",
				success: function(r) {
					return {
						total: r.total,
						rows: r.rows
					};
				},
				async: true
			},
			save: {
				url: '',
				type: 'POST',
				data: {},
				success: function(e) {}
			},
			destroy: {
				url: '',
				type: 'POST',
				data: {},
				success: function(e) {}
			},
			parameterMap: function(options) {
				return options;
			},
			schema: {
				total: 'total',
				data: 'data',
				errors: 'error',
				model: {
					id: "id",
					fields: {}
				}
			}
		},
		events: {
			onClickRow: function(index, row) {},
			onDblClickRow: function(index, row) {},
			onClickCell: function(index, field, value) {},
			onDblClickCell: function(index, field, value) {},
			onLoadSuccess: function(data) {},
			onLoadData: function(data) {},
			onCheckRow: function(index, row) {},
			onBeforeEndEdit: function(data){},
			onUpdateButtonClick : function(){},
			onDeletedEvent : function(){},
		},
		tableCls: 'table table-striped table-bordered table-hover table-plus',
		selectedCls: 'table-selected',
		pagination: {
			paging: false,
			page: 1,
			pageSize: 10,
			pageSizes: [5, 10, 15, 20, 25, 50, 100, 200, 500], //分页条数选择
			buttonCount: 10,
			previousNext: "true", //是否使能分页翻页按钮
			numeric: "true", //是否使能数字页码按钮
			input: "false", //是否使能输入框翻页
			first: '首页', //首页按钮提示文本
			last: '末页', //末页按钮提示文本
			previous: '上一页', //上一页显示文本
			next: '下一页', //下一页显示文本
			refresh: 'false'
		}
	};

	$.fn[plugin].methods = {

		_init: function() {
			var that = this,
				$target = $(that.target).addClass("grid").empty();
            
			if(that.options.height){
				if(typeof that.options.height == 'string' && (that.options.height.indexOf("px") != -1 || that.options.height.indexOf("%"))){
					$target.height(that.options.height);
				}
			}

			var tableCls = that.options.tableCls;
			if ($target.attr('table-style') === 'condensed') {
				tableCls = tableCls.replace('table-bordered', 'table-condensed');
			}
			var sb = new StringBuffer();
			$.each(["grid-header", "grid-content"], function(i, cls) {
				!$target.find("div." + cls).get(0) && (sb.appendFormat("<div class='{0}'><table class='{1}'><colgroup></colgroup></table></div>", cls, tableCls));
			});
			
			if (sb.collection.length > 0)
				$target.append(sb.toString(" "));
			sb = null;

			var header = $target.find("div.grid-header");
			var headerContent = $('<div class="grid-header-wrap"></div>').append(header.html());
			header.html(headerContent);
			that.addToolBar();

			that._initHeader();
			that._resizabled();
			that._initGroups();
			that._initEvents();
			that.load();
			
			if(this.options.scrollable){
				//拥有滚动条时
				$target.find("div.grid-content").css("overflow-y","scroll");
				$target.find("div.grid-header").addClass("grid_header_scroll");
			}
			
			if(that.options.dragEnable){
				var list = that;
				list.reset();
				$(list.target).data('grid-group', this.options.group);
	            list.placeEl = $('<tr class="' + list.options.placeClass + '"/>');
	            var onStartEvent = function(e)
	            {
	                var handle = $(e.target);
	                if (!handle.hasClass(list.options.handleClass)) {
	                    if (handle.closest('.' + list.options.noDragClass).length) {
	                        return;
	                    }
	                    handle = handle.closest('.' + list.options.handleClass);
	                }

	                if (!handle.length || list.dragEl) {
	                    return;
	                }

	                list.isTouch = /^touch/.test(e.type);
	                if (list.isTouch && e.touches.length !== 1) {
	                    return;
	                }

	                e.preventDefault();
	                list.dragStart(e.touches ? e.touches[0] : e);
	            };

	            var onMoveEvent = function(e)
	            {
	                if (list.dragEl) {
	                    e.preventDefault();
	                    list.dragMove(e.touches ? e.touches[0] : e);
	                }
	            };

	            var onEndEvent = function(e)
	            {
	                if (list.dragEl) {
	                    e.preventDefault();
	                   
	                    list.dragStop(e.touches ? e.touches[0] : e);
	                    
	                }
	            };
	            var mouseDownAndUpTimer = null;
	            var onMouseDownFlag = false;
	            $(list.target).mousedown(function(){
	                onMouseDownFlag = false;
	                mouseDownAndUpTimer = setTimeout(function(){
	                    // OnMouseDown Code in here
	                	$(list.target).on('mousedown', onStartEvent);
	                    onMouseDownFlag = true;
	                },400);
	            }).mouseup(function(){
	                if(onMouseDownFlag){
	                	list.w.on('mouseup', onEndEvent);     
	                    // OnMouseUp Code in here
	                }else{               	
	                    clearTimeout(mouseDownAndUpTimer); // 清除延迟时间
	                }
	            });        
	            
	            if (hasTouch) {
	                window.addEventListener('touchstart', onStartEvent, false);
	                window.addEventListener('touchmove', onMoveEvent, false);
	                window.addEventListener('touchend', onEndEvent, false);
	                window.addEventListener('touchcancel', onEndEvent, false);
	            }
	            list.w.on('mousemove', onMoveEvent);
			}
			
		},
		_unselectstart:function(){
			$(this.target).attr("onselectstart","return false;").css({
				"user-select": "none"
			});
		},
		_onselectstart:function(){
			$(this.target).removeAttr('onselectstart').css({
				"user-select": ""
			});
		},
		_resizabled: function() {
			if (this.options.resizable) {
				var that = this,
					$header = that.header(),
					$table = that.table(),
					$ele = $header.find('th[data-index]'),
					$col,
					index = -1,
					$document = $(document);
					noselect = "selectstart._startmove",
					noselectFn = new Function('event.returnValue=false;');
				    that.resizabled = new mtResizable($ele, {
					hint: function(handle) {
						return $('<div class="mt-resize-indicator" />').css({
							height: $(that.target).height() + 20,
							top : $(that.target).offset().top-10,
						});
					},
					start: function(e) {
						$(that.target).attr("onselectstart","return false;").css({
							"user-select": "none"
						});
						//$document.on(noselect,noselectFn);
						index = /*$ele.index(this)*/ this.getAttribute("data-index");
						var colst = 'col:eq(' + index + ')';
						$col = $header.find(colst).add($table.find(colst));
					},
					move: function(e) {

					},
					end: function(e) {
						var width = this.location.oldWidth + (e.pageX - this.location.oldX);
						if (width < 10) {
							$col.width(10);
						} else {
							$col.width(width);
						}!that.options.scrollable && that._resizeGroup && that._resizeGroup();
						//$document.off(noselect);
						$(that.target).removeAttr('onselectstart').css({
							"user-select": ""
						});
					}
				});
			}
		},
		addToolBar: function() {
			var that = this,
				$target = $(that.target),
				param = [];
			
			if (this.options.toolbar) {
				var toolbar = eval(this.options.toolbar);
				var toolbarTemplate = new StringBuffer('<div class="grid-toolbar" id="' + $target.attr("id") + '_t' + '">');
				$.each(toolbar, function(i, o) {
					if (o.name == "create") {
						toolbarTemplate.appendFormat(that._handleTemplate, 'red', o.name, 'fa-plus', o.text);
					} else if (o.name == "save") {
						toolbarTemplate.appendFormat(that._handleTemplate, 'green', o.name, 'fa-check', o.text);
					} else if (o.name == "cancel") {
						toolbarTemplate.appendFormat(that._handleTemplate, 'default', o.name, 'fa-ban', o.text);
					}
					else if (o.name == "multiSelect") {
						var sel = "<select id='done' class='editor-multiselect selectpicker' multiple data-done-button='true'>";
						$.each(eval($target.attr("data-columns")),function(i,t){
							if(t.field != undefined){
								sel = sel + "<option value='"+ t.field + "' selected='selected'>"+ t.title +"</option>";
							} 
						});
						sel = sel + "</select>";
						toolbarTemplate.append(sel);
							/*toolbarTemplate.appendFormat(that._handleTemplate, 'default', o.name, 'fa-ban', o.text);*/
					}
				});
				toolbarTemplate.append('</div>');
				$target.prepend(toolbarTemplate.toString());
				if($target.find(".editor-multiselect")[0] != undefined){
					$target.find(".editor-multiselect").on("change",function(e){
						var _li = $target.find(".editor-multiselect").find("li");
						var _option = $target.find(".editor-multiselect").find("option");
						$.each(_li,function(i,li){
							if($(li).attr("class") == ""){
								
								var _td = $target.find("td");
								$.each(_td,function(k,td){
									$.each(_option,function(n,option){
										if($(td).attr("field") == _option[$(li).attr("data-original-index")].value){
											$($target.find(".grid-header-wrap col")[parseInt($(li).attr("data-original-index"))]).css("display","none");
											$($target.find(".grid-content col")[parseInt($(li).attr("data-original-index"))]).css("display","none");
											$($target.find("th")[parseInt($(li).attr("data-original-index"))]).css("display","none");
											$(td).css("display","none");
										}
									})
								});
							}
							else{						
								var _td = $target.find("td[field]");
								$.each(_td,function(k,td){
									$.each(_option,function(n,option){
										if($(td).attr("field") == _option[$(li).attr("data-original-index")].value){
											$($target.find("th[data-index]")[parseInt($(li).attr("data-original-index"))]).css("display","table-cell");
											$($target.find(".grid-header-wrap col")[parseInt($(li).attr("data-original-index"))]).css("display","table-column");
											$($target.find(".grid-content col")[parseInt($(li).attr("data-original-index"))]).css("display","table-column");
											$(td).css("display","table-cell");
										}
									});
								});
							}	
						});
						
					})
				}
			}
		},
		header: function() {
			return $(this.target).find("div.grid-header table");
		},
		table: function() {
			var $table = $(this.target);
			!$table.is("table") && ($table = $table.find("table"));
			// var $content = this.resize();
			var $target = $(this.target)
			var $content = $target.find("div.grid-content")
			return $content.find("table");
		},
		resize: function() {
			var $target = $(this.target),
				$content = $target.find("div.grid-content");

			this._resizeGroup &&
				this._resizeGroup();
			var h = this.target.style.height;

			if (h /*$target.height()*/  && $target.is(":visible")) {

				// var marginTop = eval($target.css('margin-top').replace('px',''));
				// var marginBottom = eval($target.css('margin-bottom').replace('px',''));
				// var marginLeft = eval($target.css('margin-left').replace('px',''));
				// var marginRight = eval($target.css('margin-right').replace('px',''));

				// $target.height($target.height() - marginTop - marginBottom);
				// $target.width($target.width() - marginLeft - marginRight);

				var targetHeight = $target.outerHeight();
				var headerHeight = $target.find('div.grid-header').outerHeight();
				var pageHeight = $target.find("div.page").length > 0 ? $target.find("div.page").outerHeight() : 0;

				/*$content.is(":visible") && $content.css({
					'height' : (targetHeight-headerHeight-pageHeight)+'px'
				});*/

				$content.is(":visible") && $content.outerHeight((targetHeight - headerHeight - pageHeight));

			}

			if ($target.is(":visible")) {

			} else {

			}

			return $content;
		},
		_resizeGroup: function() {
			var that = this,
				$target = $(that.target), //	
				width =  $target.css("width"),
				$rows = $target.find("div.grid-content table colgroup>col"); //
				//当获取不到实际宽度(表明可能在隐藏中)有百分比时不重新计算，当宽度为0时也不重新计算
			if ($rows[0] && width.indexOf("%") == -1 && $target.width() && !$target.is(":hidden")) {
				$hdRows = $target.find("div.grid-header table colgroup>col"); //
				var $cdRows  = $target.find("div.grid-content table colgroup>col");
				var $header = $target.find("div.grid-header table thead");
				$.each(that.options.__columns, function(i, c) {
					var $th = $header.find("th[tid='" + c.uuid + "']"),
						w = $th.outerWidth(true);
					if($th.is(":hidden")){
						that.options.sortable && $hdRows.eq(i).css({
							display : "none"
						});
						that.options.sortable && $cdRows.eq(i).css({
							display : "none"
						})
					}
					if (w) {
						$rows.eq(i).css({
							width: w
						});
						//如果是滚动条的 再重新计算表头的col 
						that.options.sortable && $hdRows.eq(i).css({
							width: w
						});
					}
				});
			}
		},
		_initHeader: function() {
			var that = this,
				options = that.options,
				columns = options.columns;
			if (!columns) {
				return;
			}
			that._initColumnsLevel(columns, 0);
			var header = that._createHeader(columns);
			that.header().append(header);
		},
		_initGroups: function() {
			var rows = [],
				that = this;
			var scrollable = this.options.scrollable;
			$.each(this.options.__columns, function(i,item) {
				var str = "<col "
				if(item.command && item.command.length > 0){
					str += "class='commandCell' ";
				}
				if (scrollable) {
					$(that.target).find("div.grid-content").css("overflow-x", "auto");
					str+= "style='width:" + this.width +";'></col>";
					// rows.push("<col style='width:" + this.width +";'></col>");
					//rows.push("<col style='width:" + (this.width + "").replace("px", "") + "px;'></col>");
				} else {
					str+= "style='width:auto;'></col>";
					// rows.push("<col style='width:auto;'></col>");
				}
				 rows.push(str);
			});

			//如果是滚动条 那么需要增加一列来填充空白
			scrollable && rows.push("<col style='width:auto;'></col>");

			var cols = rows.join('');
			this.table().find("colgroup").append(cols);
			this.header().find("colgroup").append(cols);
		},
		//	_seq : {},
		_seq: function() {
			!this.state._seq && (this.state._seq = {});
			return this.state._seq;
		},
		_getSequence: function(region, start) {
			region = region || 'nextId';
			var that = this,
				seq = that._seq();
			return seq[region] || (seq[region] = new Sequence(region, start));
		},
		//	_colArray : [],//
		_colArray: function() {
			!this.state._colArray && (this.state._colArray = []);
			return this.state._colArray;
		},
		//	_colObj : {},//
		_colObj: function() {
			!this.state._colObj && (this.state._colObj = {});
			return this.state._colObj;
		},
		_getDataArray: function() {
			!this.options.__dataArray && (this.options.__dataArray = []);
			return this.options.__dataArray;
		},
		_createHeader: function(columns) {
			var $table = $("<table style='table-layout:fixed;'></table>");
			var $tr = $("<tr>");
			$table[0].appendChild($tr[0]);
			this._createTd($table[0], $tr[0],columns);
			return "<thead>" + $table.html() + "</thead>";
		},
		_getTh: function(node) {
			var $th = $("<th>", {
				tid: node.uuid,
				style: node.style || (node.attributes ? node.attributes.style : '')
			});
			if(node.hidden){
				$th.css("display","none");
			}
			$.each({
				'text-align': 'center',
				'vertical-align': 'middle',
				'word-break': 'break-all',
				'word-wrap': 'break-word'
			}, function(k, v) {
				if (!$th.css(k)) $th.css(k, v);
			});
			return $th;
		},
		_createTd: function(table, row, nodes,isChild) {
			var that = this,
				childrenColumn = that.options.childrenColumn, //
				node;
			if (nodes && nodes.length > 0) {
				var maxDeep = isChild || that._getLastLevelNum(nodes);
				var level = 0 ;
				for (var i = 0; i < nodes.length; i++) {
					node = nodes[i];
					var children = node[childrenColumn];
					var col = that._getTh(node)[0];
					if (children && children.length > 0) {
						if (!Boolean(node.isHidden)) {
							col.colSpan = that._getLastChildrenNum(children);
							that._appendTh(row, col, node);
							level = (children[0].level + 1) - 1 ;
							var trow = table.rows[(children[0].level + 1) - 1];
							if (!trow) {
								trow = $("<tr>", {})[0];
								table.appendChild(trow);
							}
							that._createTd(table, trow, children,maxDeep);
						}
					} else {
						if (!Boolean(node.isHidden)) {
							col.setAttribute("data-index",(that.colIndexNum==undefined ? (that.colIndexNum=0):++that.colIndexNum));
							col.rowSpan = (maxDeep + 1 - (node.level + 1));
							that._appendTh(row, col, node);
						}
					}
				}
				//如果有滚动条 则补上空白行
				((!isChild && level==0) || level == 1) && this.options.scrollable && that._appendTh(row, $("<th></th>",{rowSpan:$(table).find("tr").length})[0], {});
			}
		},
		_appendTh: function(row, col, node) {
			//如果有链接，添加链接
			var $content;
			if (node.link && node.link !== "") {
				var a = document.createElement("a");
				a.href = "#";
				a.innerText = node.title || '';
				col.appendChild(a);
			} else if (node.headerTemplate && ($content = node.headerTemplate(node)) //
				&& ($content = $($content)) && $content.get(0)) {
				//col.appendChild($(node.headerTemplate())[0]);
				$content.each(function() {
					col.appendChild(this);
				});
			} else if(node.type == 'inner_checkbox'){
				col.appendChild($(this._getCheckBox(node,'checkbox',null,'header_checkbox'))[0]);
			}else {
				col.innerText = node.title || '';
			}
			if(node.command && node.command.length > 0){
				$(col).addClass("commandCell");
			}
			row.appendChild(col);
		},
		_getLastChildrenNum: function(nodes) {
			var that = this,
				childrenNum = 0,
				childrenColumn = that.options.childrenColumn;
			if (nodes && nodes.length > 0) {
				for (var i = 0; i < nodes.length; i++) {
					var children = nodes[i][childrenColumn];
					if (children && children.length > 0) {
						childrenNum += that._getLastChildrenNum(nodes[i][childrenColumn]);
					} else {
						if (!Boolean(nodes[i].isHidden)) {
							childrenNum++;
						}
					}
				}
			}
			return childrenNum;
		},
		_getLastLevelNum: function(nodes) {
			var that = this,
				maxLevel = 0,
				childrenColumn = that.options.childrenColumn;
			if (nodes && nodes.length > 0) {
				for (var i = 0; i < nodes.length; i++) {
					var tlevel = 0;
					var children = nodes[i][childrenColumn];
					if (children && children.length > 0) {
						tlevel = that._getLastLevelNum(nodes[i][childrenColumn]);
					} else {
						if (!Boolean(nodes[i].isHidden)) {
							tlevel = nodes[i].level + 1;
						}
					}
					if (tlevel > maxLevel) maxLevel = tlevel;
				}
			}
			return maxLevel;
		},
		_initColumnsLevel: function(columns, level) {
			var i = 0,
				c, that = this,
				seq = that._getSequence("hid-", 199999), //
				rowspan, opts = that.options,
				autoHeader = opts.autoHeader;

			function getUUID() {
				//return seq.nextRegVal();
				return "hid-" + window.uuid();
			};
			for (; i < columns.length; i++) {
				c = columns[i], rowspan = autoHeader ? 1 : c.rowspan;
				$.extend(c, {
					uuid: getUUID(),
					level: level,
					rowspan: rowspan
				});
				// 'editor':that.options.ajax.schema.model.fields[c.field]
				that._colArray().push(c);
				if (c[opts.childrenColumn]) {
					that._initColumnsLevel(c[opts.childrenColumn], level + 1);
				} else {
					opts.__columns.push(c);
				}
				that._colObj()[c.uuid] = c;
			}
		},
		_initEvents: function() {
			var that = this;
			that._initClientEvents();

			$("#" + $(this.target).attr("id") + '_t').on("click", "a.btn", function() { //新增
				if ($(this).attr("atype") == "create") {
					that._newRow();
				} else if ($(this).attr("atype") == "save") { //保存
					that.saveHandle();
				} else if ($(this).attr("atype") == "cancel") {
					that.tbody().find('tr.' + that._editing + '').each(function(i, o) { //取消
						if ($(o).hasClass('new')) {
							$tr.remove();
							return;
						}
						$btn = $(o).find('a[atype="cancel"]');
						that._cancelEdit($btn);
					});
				}
			});

			//	$(that.target).resize(that.resize);

			//	$(window).resize(function(){
			//		that.resize();
			//	});
		},
		//		_initGridButton : function() {
		//			//删除 编辑
		//			var that = this;
		//			
		//		},
		_clientEvents: {
			Row: {
				target: 'tbody>tr',
				fn: function(e, fn, that) {
					var row = that.getRow(e.target);
					if(row)
						return fn(row[that._rowIndex], row);
				}
			},
			Cell: {
				target: 'tbody>tr>td',
				fn: function(e, fn, that) {
					var row = that.getRow(e.target),
						field;
					var id = that.target.getAttribute("id");
					if(that.options.clickcell){
						$("#"+id+" .grid-content td").css("background-color","");
						e.target.style.backgroundColor = that.options.cellcolor;
					}
					
					return fn.call(that,row[that._rowIndex], (field = $(e.target)
						.closest("td").attr("field")), row[field]);
				}
			}
		},
		_trigger: function(event, params) {
			return this.table().trigger(event, params);
		},
		__setTargetColor : function(target,opts){
				$(target).find(".red").css("background-color",opts.editColor);
				$(target).find(".red").css("border-color",opts.editColor)		
				$(target).find(".purple").css("background-color",opts.purpleColor);
				$(target).find(".purple").css("border-color",opts.purpleColor);	
				$(target).find(".green").css("background-color",opts.endColor);
				$(target).find(".green").css("border-color",opts.endColor);		
				$(target).find(".default").css("background-color",opts.cancelColor);
				$(target).find(".default").css("border-color",opts.cancelColor);
				$(target).find("a[atype=upward]").css("background-color",opts.ascColor);
				$(target).find("a[atype=upward]").css("border-color",opts.ascColor);			
				$(target).find("a[atype=downward]").css("background-color",opts.descColor);
				$(target).find("a[atype=downward]").css("border-color",opts.descColor);
		},
		_initClientEvents: function() {

			var that = this;

			$(this.target).resize(function() {
				that.resize.call(that);
			});

			var that = this,
				$table = that.table(),
				opts = that.options,
				events = opts.events;


			$.each(opts.__columns, function(i, o) {
				if ((o.sortable == undefined || o.sortable) && o.field) {
					var $ths = $(that.target).find('div.grid-header thead th[tid="' + o.uuid + '"]').on("click", function(e) {
						var state;
						if (opts.sortable.mode == "single") {
							$(that.target).find('div.grid-header thead th[tid!="' + o.uuid + '"]').find("i").remove();
							that.__sortArray = [];
						}
						if ($(this).find("i").length > 0 && $(this).find("i").hasClass('fa-sort-asc')) { //降序
							$(this).find("i").removeClass('fa-sort-asc').addClass('fa-sort-desc');
							state = 'desc';
						} else if ($(this).find("i").length > 0 && $(this).find("i").hasClass('fa-sort-desc')) { //不排序
							$(this).find("i").remove();
							state = '';
						} else { //升序
							$(this).html($(this).html() + '<i class="fa fa-sort-asc"></i>');
							state = 'asc';
						}

						if (that.__sortArray.length == 0) {
							that.__sortArray.push({
								field: o.field,
								dir: state
							});
						} else {
							var flag = false,
								k;
							$.each(that.__sortArray, function(j, p) {
								if (p.field == o.field) {
									flag = true;
									k = j;
									return;
								}
							});
							if (flag) {
								if (state != '') {
									that.__sortArray[k] = {
										field: o.field,
										dir: state
									};
								} else {
									that.__sortArray.splice(k, 1);
								}
							} else {
								if (state != '') {
									that.__sortArray.push({
										field: o.field,
										dir: state
									});
								}
							}
						}

						that.query();
					});
				} else {}
			});

			//滚动条联动
			$(that.target).find("div.grid-content").bind("scroll", function(e) {
				$(that.target).find("div.grid-header-wrap")[0].scrollLeft = this.scrollLeft;
			});

			//绑定options事件
			for (var p in that.options.events) {
				$table.bind(p, function(e) {
					return that.options.events[p].apply(that, Array.prototype.slice.call(arguments, 1));
				});
			};
			// $.each(that.options.events, function(event, fn){
			// 	$table.bind(event, function(e){
			// 		return f.apply(that, Array.prototype.slice.call(arguments, 1));
			// 	});	
			// });

			//$table.on("click","a.btn",function(e){// 用户可能加上a.btn 样式
			$table.on("click", "a.btn[atype]", function(e) {
				e.stopPropagation();
				var $btn = $(this);
				if ($btn.attr('atype') === 'destroy' || $btn.attr('atype') === 'cancel') {
					
					
					var $tr = that.getDomRow(this);
					if ($(this).attr("atype") == "destroy") {
						var rowData = that.getRow(this);
						var data = that.getRow(this);
						if(!opts.datas && !opts.nopersistent){
							that.removeRowFromDB(this);	
							if($.isFunction(that.options.events.onDeletedEvent)){
								that.options.events.onDeletedEvent(data);
							}
						}else{
							that.removeRow(this);
							if($.isFunction(that.options.events.onDeletedEvent)){
								that.options.events.onDeletedEvent(data);
							}
						}
						that.__setTargetColor(that.target,opts);
						

						
						
					} else if ($(this).attr("atype") == "cancel") {
						that._cancelEdit(this);
						that.__setTargetColor(that.target,opts);
						
					}
				} else if ($btn.attr('atype') === 'edit' || $btn.attr('atype') === 'update') {
					var $tr = that.getDomRow(this);
					if ($tr.hasClass(that._editing)) { //切换到编辑
                        if(!$(this).closest("tr").find("em.error:visible").length > 0){
						var newBtn = new StringBuffer();
						that._endEdit(this);
						var row = that.getRow(this);

						if(!that.options.datas){
							//非静态数据时，发送请求
							that.ajaxSave([row], function() {
								that.query({
									id: ""
								});
							});
						}else{
							if(opts.datas){
								that._loadDatas(that.getData());
							}
						}
						//样式
						newBtn.appendFormat('<i class="fa {0}"></i>{1}', 'fa-edit', '编辑');
						$(this).html(newBtn.toString());
						$(this).removeClass('green').addClass('red');
						$(this).attr("atype", "edit");
						//样式
						newBtn = new StringBuffer();
						newBtn.appendFormat('<i class="fa {0}"></i>{1}', 'fa-times', '删除');
						$tr.find('a[atype="cancel"]').html(newBtn.toString());
						$tr.find('a[atype="cancel"]').removeClass('default').addClass('purple');
						$tr.find('a[atype="cancel"]').attr("atype", "destroy");
						that.options.events.onUpdateButtonClick();
						that.__setTargetColor(that.target,opts);
						
                        }

						
					} else { //切换到更新
						that._toUpdate(this);
						that.__setTargetColor(that,opts);
//						that.options.events.onUpdateButtonClick();
					}
				} else if($btn.attr('atype') === "upward" || $btn.attr('atype') === "downward"){
					if(opts.datas){
						that._move(that.getRow(this),$btn.attr('atype'));
					}else{
						that.ward({
							paramType : (that.options.sortDesc?($btn.attr('atype')=="upward"?true:false):($btn.attr('atype')=="upward"?false:true)),
							models : [that.getRow(this)]
						});
					}
				}else{
					$(this).closest("tr").trigger("click");
					$(this).trigger("commandclick");
				}
			});


			// $(document).ready(function(){
			// 	var key=0;  //记录ctrl/shift键
			// $(window).keydown(function(e){
			// 	if(e.ctrlKey){
			// 		key=1;
			// 	}else if(e.shiftKey){
			// 		key=2;
			// 	}
			// }).keyup(function(){
			// 		key=0;
			// });

			$.each(['Click', 'DblClick'], function(i, t) {
				$.each(that._clientEvents, function(k, event) {
					$table.on(t.toLowerCase() + "." + event.target, event.target, function(e) {
						var $this = $(this);
						if ($this.is('tr')) {
							if(e.shiftKey && opts.selectable == "multiple"){
								if(that._prevClickTr  && !$(this).hasClass("editing")){
									var pi = that._prevClickTr.index(),
										ti = $this.index(),
										isGt = pi < ti;

									$table.find('tr').removeClass(opts.selectedCls);
									$table.find('tr').slice(isGt?pi:ti,(isGt?ti:pi)+1).addClass(opts.selectedCls);
								}else{
									that._clickRowToChangeCheckBox($(this),true);
									$this.toggleClass(opts.selectedCls);
								}
							}else{
								that._prevClickTr = $this;
								if (opts.selectable != "multiple" && !$(this).hasClass("editing")) {
									$table.find('tr').removeClass(opts.selectedCls);
								}
								if(!(that.options.clickUpdate && opts.editable) && opts.selectable != "multiple" && $(this).hasClass("editing")){
									$table.find('tr').removeClass(opts.selectedCls);	
								}
								
								$this.toggleClass(opts.selectedCls);

								that._clickRowToChangeCheckBox($(this));
							}
							
							if (that.options.clickUpdate && opts.editable) {
								var $parent = $(e.target).closest('div.cell-editor');
								if (!$parent[0]) {
									//如果为单选，且在编辑状态中选中时，只取消编辑，不取消选中
									if(opts.selectable != 'multiple' && $this && $this.hasClass("editing") && !$this.hasClass(opts.selectedCls)){
										$this.toggleClass(opts.selectedCls);
									}
									if ($this.hasClass(that._editing)) {
										that._endEdit($this);
									} else {
										$table.find("tr.editing").each(function() {
											that._endEdit($(this));
										});
										if($this.attr("disabledEdit")){

										}else{
											that.beginEdit($this);
										}
									}
								} else {
									$this.toggleClass(opts.selectedCls);
								}
							}
						}
						if(!(opts.editable && $(e.target).closest('div.cell-editor')[0])){
							var fn = $(that.target).data("__" + "on"+t+k + "__") || events["on" + t + k];
							if (fn && $.isFunction(fn))
								return event.fn(e, fn, that);
						}
					});
				});
			});

			that._bindInnerCheckBox();
		},
		_move: function(item,type){
			var that = this;

			var datas = that.getData();
			var itemNum = 0;
			$.each(datas,function(i,data){
				if(data[that._rowIndex] == item[that._rowIndex]){
					itemNum = i;
					return false;
				}
			})
			if(type == 'upward'){
				if(itemNum > 0){
					datas.splice(itemNum,1);
					datas.splice(itemNum-1,0,item);
					that._loadDatas(datas);
				}
			}else if(type == 'downward'){
				if(itemNum < datas.length){
					datas.splice(itemNum,1);
					datas.splice(itemNum+1,0,item);
					that._loadDatas(datas);
				}
			}
		},
		/**
		 * 点击行时改变选择框
		 * @param  {[type]} $tr [点击的行]
		 * @return {[type]}     [description]
		 */
		_clickRowToChangeCheckBox : function($tr,isMultiple){
			var that = this;
			var $table = that.table();
			return;
			if(isMultiple){
				that._changeInnerCheckBox($tr.find(".inner_checkbox"),2);
			}else{
				that._changeInnerCheckBox($table.find('tr .inner_checkbox'),0);
				that._changeInnerCheckBox($tr.find(".inner_checkbox"),1);
			}
		},
		/**
		 * 更改内置选择框的状态
		 * @param  {[type]} $innerCheckbox [选择框]
		 * @param  {[type]} type           [状态，0为不选中，1为选中，2为更改变化]
		 * @return {[type]}                [description]
		 */
		_changeInnerCheckBox : function($innerCheckbox,type){
			if(type == 0){
				$innerCheckbox.prop('checked', false);
			}else if(type == 1){
				$innerCheckbox.prop('checked', true);
			}else if(type == 2){
				$innerCheckbox.prop('checked', !$innerCheckbox.prop('checked'));
			}
		},
		/**
		 * [_bindInnerCheckBox description]
		 * @param  {[type]} $innerCheckbox [description]
		 * @return {[type]}                [description]
		 */
		_bindInnerCheckBox : function(){
			var that = this;
			var $tbale = that.table();

			$(that.target).on("change",".inner_checkbox",function(event){
				if($(this).hasClass("header_checkbox")){
					//全选/全不选
					if($(this).prop("checked")){
						$tbale.find(".inner_checkbox").prop("checked",true).trigger("change");
					}else{
						$tbale.find(".inner_checkbox").prop("checked",false).trigger("change");
					}
				}else{
					var rowData = that.getRow(this);
					// //触发选中事件
					// if($.isFunction(that.options.events.onCheckRow)){
					// 	that.options.events.onCheckRow(rowData[that._rowIndex], rowData);
					// }

					var onCheckRowFunc = $(that.target).data("__onCheckRow__") || that.options.events.onCheckRow;
					//触发选中事件
					if($.isFunction(onCheckRowFunc)){
						onCheckRowFunc();
						// that.options.events.onCheckRow(rowData[that._rowIndex], rowData);
					}
				}
			})
		},
		saveHandle: function() { //保存操作
			var data = [],
				that = this;
			that.tbody().find('tr.' + that._editing + '').each(function(i, o) { //保存
				that._endEdit(this);
				var row = that.getRow(this);
				data.push(row);
			});
			that.ajaxSave(data, function() {
				that.query({
					id: ""
				});
			});
		},
		_toUpdate: function(obj) { //编辑按钮
			$table = this.table();
			var that = this;

			//that.options.data
			if(!that.options.datas)
				//非静态数据时，取消其他编辑状态
				$table.find("tr." + this._editing).each(function(i, o) {
					that._cancelEdit($(o).find('a[atype="cancel"]'));
				});
			var $tr = this.getDomRow(obj);
			this.beginEdit(obj);
			if ($(obj).is("a") || $(obj).find('a[atype="edit"]').length) {
				var newBtn = new StringBuffer(),
					$btn;
				if ($(obj).is("a")) {
					$btn = $(obj);
				} else if ($(obj).find('a[atype="edit"]').length) {
					$btn = $(obj).find('a[atype="edit"]');
				}
				//样式
				newBtn.appendFormat('<i class="fa {0}"></i>{1}', 'fa-check', '更新');
				$btn.html(newBtn.toString());
				$btn.removeClass('red').addClass('green');
				$btn.attr("atype", "update");
				//样式
				newBtn = new StringBuffer();
				newBtn.appendFormat('<i class="fa {0}"></i>{1}', 'fa-ban', '取消');
				$tr.find('a[atype="destroy"]').html(newBtn.toString());
				$tr.find('a[atype="destroy"]').removeClass('purple').addClass('default');
				$tr.find('a[atype="destroy"]').attr("atype", "cancel");
			}
			that.options.textAlign != undefined && that.options.textAlign != null && that.options.textAlign != "" ? $(that.target).find(".red").closest("td").css("text-align",that.options.textAlign) : null;
			that.options.textAlign != undefined && that.options.textAlign != null && that.options.textAlign != "" ? $(that.target).find(".green").closest("td").css("text-align",that.options.textAlign) : null;
			
		},
		_cancelEdit: function(obj) {
			var $tr = this.getDomRow(obj);
			if ($tr.hasClass('new')) {
				$tr.remove();
				return;
			}
			var newBtn = new StringBuffer();
			this._giveUpEdit(obj);
			if ($(obj).is("a")) {
				//样式
				newBtn.appendFormat('<i class="fa {0}"></i>{1}', 'fa-edit', '编辑');
				$tr.find('a[atype="update"]').html(newBtn.toString());
				$tr.find('a[atype="update"]').removeClass('green').addClass('red');
				$tr.find('a[atype="update"]').attr("atype", "edit");
				//样式
				newBtn = new StringBuffer();
				newBtn.appendFormat('<i class="fa {0}"></i>{1}', 'fa-times', '删除');
				$(obj).html(newBtn.toString());
				$(obj).removeClass('default').addClass('purple');
				$(obj).attr("atype", "destroy");
			}
		},
		_editors: {
			textbox: function(container, options, row) {
				var $input = $("<input>", {
					field: options.field,
					name: options.field,
					class: 'form-control'
				}).appendTo(container);
				if (row) {
					$input.val(pubsub.htmlUnescape(row[options.field]) || '');
					//$input.val(row[options.field] || '');
				}
			},
			select: function(container, options, row) {
				var $select = $('<select>', {
					field: options.field,
					name: options.field,
					class: 'form-control'
				}).appendTo(container);
				if (options.editor.dataSource) {
					$select.select2({
						data: options.editor.dataSource,
						editor: options.editor,
					});
					if (row && row[options.field]) {
						$select.val(row[options.field] || '').trigger('change');
					}else{
						var defaultIndex = options.editor.defaultIndex;
						if(defaultIndex && defaultIndex >0 && defaultIndex <= options.editor.dataSource.length){
							var defaultValue = options.editor.dataSource[options.editor.defaultIndex-1];
							$select.val(defaultValue.id).trigger('change');
						}
					}
				} else if (options.editor && options.editor.params) {
					// $select.select2({
					// 	  ajax: {
					// 	    url: contextPath + options.editor.url.replace("contextPath",""),
					// 	    dataType: 'json',
					// 	    contentType:"application/x-www-form-urlencoded",
					// 	    delay: 250,
					// 	    data: function (params) {
					// 	      return $.extend({
					// 	        q: params.term, // search term
					// 	        page: params.page
					// 	      },options.editor.params);
					// 	    },
					// 	    processResults: function (data, params) {
					// 	      params.page = params.page || 1;
					// 	      for(var i=0;i<data.length;i++){
					// 			data[i].id=i;
					// 			data[i].text = data[i][options.editor.params.text];
					// 			data[i].value = data[i][options.editor.params.value]; 
					// 		  }
					// 	      return {
					// 	        results: data,
					// 	        pagination: {
					// 	          more: (params.page * 30) < data.total_count
					// 	        }
					// 	      };
					// 	    },
					// 	    cache: true
					// 	  }
					//  });
					$.ajax({
						url: contextPath + options.editor.url.replace("contextPath", ""),
						type: 'POST',
						dataType: 'JSON',
						contentType: "application/x-www-form-urlencoded",
						data: options.editor.params,
						success: function(data) {
							if (data) {
								for (var i = 0; i < data.length; i++) {
									data[i].id = data[i][options.editor.params.value];
									data[i].text = data[i][options.editor.params.text];
									data[i].value = data[i][options.editor.params.value];
								}
								$select.select2({
									data: data,
									editor: options.editor,
								});
								
//								if (row) {
////									$select.val(row[options.field] || '').trigger('change');
//									if(row[options.field] != ""){
//										$select.val(row[options.field] || '').trigger('change');
//									}
//									else{
//										$select.val(data[options.editor.defaultIndex-1].id || '').trigger('change');
//									}
//									
//								}
							}
						},
						async: true
					});
				}
			},
			select2: function(container, options, row) {
				var $select = $('<select>', {
					field: options.field,
					name: options.field,
					class: 'form-control',
					multiple: ""
				}).appendTo(container);
				if (options.editor.dataSource) {
					$select.select2({
						data: options.editor.dataSource,
						editor: options.editor,
					});
					if (row) {
						var val = row[options.field] || ",";
						var array = val.split(",");
						$select.val(array).trigger('change');
					}
				} else if (options.editor && options.editor.params) {
					$.ajax({
						url: contextPath + options.editor.url.replace("contextPath", ""),
						type: 'POST',
						dataType: 'JSON',
						contentType: "application/x-www-form-urlencoded",
						data: options.editor.params,
						success: function(data) {
							if (data) {
								for (var i = 0; i < data.length; i++) {
									data[i].id = data[i][options.editor.params.value];
									data[i].text = data[i][options.editor.params.text];
									data[i].value = data[i][options.editor.params.value];
								}
								$select.select2({
									data: data,
									editor: options.editor,
								});
								if (row) {
									var val = row[options.field] || ",";
									val = val.indexOf(",") != -1 ? val.replace(new RegExp(",","g"),",") : val;
									var array = val.split(",");
									$select.val(array).trigger('change');
								}
							}
						},
						async: true
					});
				}
			},
			datepicker: function(container, options, row) {
				var $span = $('<span class="input-group-btn"><button class="btn default form-control" type="button"><i class="fa fa-calendar"></i></button></span>');
				var $input = $("<input>", {
					class: 'form-control'
				});
				var $datepicker = $("<div>", {
					class: 'input-group date'
				}).append($input).append($span).appendTo(container);
				var $hidden = $("<input>", {
					field: options.field,
					name: options.field,
					type: 'hidden'
				});
				container.append($hidden);
				$datepicker.datepicker({
					autoclose: true,
					clearBtn: true,
					format: options.format.replace(/MM/g, "mm") || 'yyyy-mm-dd',
					language: 'zh-CN',
					todayBtn: 'linked',
					orientation: "right top"
				});
				$datepicker.datepicker().on("changeDate", function(e) {
					//debugger;
					$hidden.val(e.date ? e.date.toJSON() : "");
				});
				if (row[options.field]) {
					$datepicker.datepicker("setDate", new Date(row[options.field]) == "Invalid Date" ? '' : new Date(row[options.field]));
				}
			},
			datetimepicker: function(container, options, row) {
				var $span = $('<span class="input-group-btn"><button class="btn default date-set" type="button"><i class="fa fa-clock-o"></i></button></span>');
				var $input = $("<input>", {
					type: "text",
					class: 'form-control'
				});
				var $datetimepicker = $("<div>", {
					class: 'input-group date'
				}).append($input).append($span).appendTo(container);
				var $hidden = $("<input>", {
					field: options.field,
					name: options.field,
					type: 'hidden'
				});
				
				container.append($hidden);
				
				$datetimepicker.datetimepicker({
					autoclose: true,
					showMeridian: true,
					format: options.format.replace(/mm/g, "ii").replace(/MM/g, "mm").replace(/HH/g, "hh") || "yyyy-mm-dd hh:ii:ss",
					pickerPosition: "bottom-left",
					language: 'zh-CN',
					todayBtn: true
				});
			    if(options.defaultTimePicker){
			    	var defaultDate = new Date();
			    	$datetimepicker.datetimepicker("setDate",defaultDate);
			    	$hidden.val(defaultDate != "Invalid Date" ? defaultDate.toJSON() : "");
			    }
				$datetimepicker.datetimepicker().on('changeDate', function(ev) {
					//debugger;
					var date = new Date(ev.date.valueOf() - 8 * 60 * 60 * 1000);
					$hidden.val(date != "Invalid Date" ? date.toJSON() : "");
				});
				if (row[options.field]) {
					$datetimepicker.datetimepicker('setDate', new Date(row[options.field]) == "Invalid Date" ? '' : new Date(row[options.field]));
					// $datetimepicker.datetimepicker('update', new Date(row[options.field])|| '');
					//$datetimepicker.find('input').val(new Date(row[options.field]).format(options.format) || '');
				}
			},
			touchspin: function(container, options, row) {
				var $touchspin = $('<div class="input-group bootstrap-touchspin"><input type="text" class="form-control"><span class="input-group-btn-vertical"><button class="btn btn-default bootstrap-touchspin-up" type="button"><i class="glyphicon glyphicon-plus "></i></button><button class="btn btn-default bootstrap-touchspin-down" type="button"><i class="glyphicon glyphicon-minus"></i></button></span></div>');
				$touchspin.appendTo(container);
				$touchspin.find("input").attr('field', options.field);
				$touchspin.find("input").attr('name', options.field);
				var dec = 0,
					st = 1;
				if (options.format == "#.#") {
					dec = 1;
					st = 0.1;
				} else if (options.format == "#.##") {
					dec = 2;
					st = 0.01;
				} else if(options.format == '#.###'){
					dec = 3;
					st = 0.001;
				} else if(options.format == '#.####'){
					dec = 4;
					st = 0.0001;
				}else {
					dec = 0;
					st = 1;
				}
				var touchSpinOption = {
					min: -1000000000,
					max: 1000000000,
					step: st,
					decimals: dec
				}; 
				$touchspin.find("input").TouchSpin(touchSpinOption);
				$touchspin.find("input").data("touchspin",{"options":touchSpinOption});
				if (row) {
					$touchspin.find('input').val(row[options.field] || '');
				}
			},
			checkbox: function(container, options, row) {
				var $input = $("<input>", {
					type: 'checkbox'
				}).appendTo(container);
				var $hidden = $("<input>", {
					field: options.field,
					name: options.field,
					type: 'hidden'
				});
				container.append($hidden);
				$input.iCheck({
					checkboxClass: "icheckbox_minimal-grey"
				});
				$input.on('ifChecked', function(event) {
					if (options.editor.dataSource) {
						$hidden.val(options.editor.dataSource[0].value);
					}
				});
				$input.on('ifUnchecked', function(event) {
					if (options.editor.dataSource) {
						$hidden.val(options.editor.dataSource[1].value);
					}
				});
				if (options.editor.dataSource && row[options.field] == options.editor.dataSource[0].value) {
					$input.iCheck('check');
				} else if (options.editor.dataSource && row[options.field] == options.editor.dataSource[1].value) {
					$input.iCheck('uncheck');
				}
			},
			radio: function(container, options, row) {
				
				var $hidden = $("<input>", {
					field: options.field,			
					name: options.field,
					type: 'hidden'
				});
				
				
				if(options.editor.dataSource == undefined){
					$.ajax({
						url: contextPath + options.editor.url.replace("contextPath", ""),
						type: 'POST',
						dataType: 'JSON',
						contentType: "application/x-www-form-urlencoded",
						data: options.editor.params,
						success: function(data) {
							if (data) {
								$.each(data,function(i,ret){
									var $input = $("<input>", {
										type: 'radio',
										name : options.field + row.row_number,
										value : ret[options.editor.params.value],
										style : "vertical-align:middle; margin-top:0;"
									}).appendTo(container);
							    	container.append("<label>"+ret[options.editor.params.value]+"</label><br />");
									
									container.append($hidden);
									/*$input.iCheck({
										radioClass: "iradio_square-blue"
									});*/
									$input.on('click', function(event) {
											$hidden.val(this.value);
									});	
									if (options.editor.dataSource && row[options.field] == ret[options.editor.params.value]) {
										$($input).attr('checked',"checked");
									} 
								});
										
							}
						},
						async: true
					});
				}
				else{
					$.each(options.editor.dataSource,function(i,data){
				    	var $input = $("<input>", {
							type: 'radio',
							name : options.field + row.row_number,
							value : data.value,
						}).appendTo(container);
				    	container.append("<label>"+data.text+"</label><br />");
						
						container.append($hidden);
						/*$input.iCheck({
							radioClass: "iradio_square-blue"
						});*/
						$input.on('click', function(event) {
								$hidden.val(event.target.value);
							
						});				
						if (options.editor.dataSource && row[options.field] == data.value) {
							$($input).attr('checked',"checked");
						} 
				    });
				}
			    
				
			},
			fileupload: function(container, options, row) {
				var $div = $('<div>', {
					class: 'mt-sp-jqfileupload'
				});
				var $btn = '<span class="btn btn-success fileinput-button"><i class="glyphicon glyphicon-plus"></i><span>选择文件</span>' +
					'<input class="mt-sp-fileupload" type="file" name="file" multiple/></span>';
				var $progress = '<div class="progress" style="margin-bottom: 0px;display: inline-block;width: 50%;"><div class="progress-bar progress-bar-success"></div></div>';
				var $files = '<div class="mt-sp-files"></div>';
				$div.css("text-align", "left").append($btn).append($progress).append($files).appendTo(container);
				var $fileuploadHidden = $("<input>", {
					field: options.field,
					name: options.field,
					type: 'hidden'
				});
				container.append($fileuploadHidden);
				//对隐藏域进行赋值
				$fileuploadHidden.val(row[options.field]);
				$div.spFileUpload({
					getUrl: contextPath + "/mx/form/attachment?method=getFilesByRandomParent&randomParent=",
					url: contextPath + "/mx/form/attachment?method=upload&to=to_db&randomParent=",
					dowmloadUrl: contextPath + "/mx/form/attachment?method=download&from=to_db",
					removeUrl: contextPath + "/mx/form/attachment?method=remove&from=to_db",
					randomParent: new UUID().createUUID(),
					maxFileSize: 6291456,
					autoUpload: true,
					uploadSuccessCallback: function(e, data) {
							var files = data.result,
								names = "",
								ids = "";
							for (var i = 0; i < files.length; i++) {
								names += files[i].fileName + ",";
								ids += files[i].id + ",";
							}
							$fileuploadHidden.val(data.randomParent); //存值
							// row[options.field+'componentData']={type:'fileupload'};
							// row[options.field+'componentData'].names = (names.substr(0,names.length - 1));
							// row[options.field+'componentData'].ids = ids.substr(0,ids.length - 1);
						}
						// ,deleteCallback:function(data){
						// 			var names = "", ids = "";
						// 			for (var i = 0; i < data.length; i++) {
						// 	names += data[i].fileName + ",";
						// 	ids += data[i].id + ",";
						// }
						// var str = (row[options.field+'componentData'].names + ",").replace(names, "");
						// row[options.field+'componentData'].names = str.substr(0, str.length - 1);
						// str = (row[options.field+'componentData'].ids + ",").replace(ids, "");
						// row[options.field+'componentData'].ids = str.substr(0, str.length - 1);
						// 		}
				});
				$div.spFileUpload("setValue", row[options.field]);
			}
		},
		//上下合并
		_combineUpDown : function($table,startColumnField){
			//遍历列进行行合并
			var $firstTd = null;	//合并的第一个单元格
			var spanNum = 1;
			$.each($table.find("td[field='"+startColumnField+"']"),function(tdNum,tdItem){
				var $tdItem = $(tdItem);
				if($tdItem.is(":hidden")){
					//若该单元格已经进行列合并，则不再进行行合并
					if($firstTd && !$firstTd.attr("rowspan"))
						$firstTd.attr("rowspan", spanNum);
					$firstTd = null;
					spanNum = 1;
					return true;	//跳过这次循环，进入下一次
				}
				var colspanNum = $tdItem.attr("colspan") || 1;	//该单元格所占用的列数
				var firstTdColspanNum = $firstTd?$firstTd.attr("colspan") || 1 : 1;	//合并单元格所占用的列数
				if($firstTd && $firstTd.text() == $tdItem.text() && firstTdColspanNum == colspanNum){
					//值相等和列相等时才能合并
					$tdItem.hide();
					spanNum++;
				}else{
					//设置合并的属性
					if($firstTd){
						$firstTd.attr("rowspan", spanNum);
						$firstTd.css("line-height",$firstTd.height() + "px");
					}
					//设置新的第一个单元格
					$firstTd = $tdItem;	//赋值合并的第一个单元格
					spanNum = 1;
				}
			})
			if($firstTd){
				$firstTd.attr("rowspan", spanNum);
				$firstTd.css("line-height",$firstTd.height() + "px");
			}
		},
		//左右合并
		_combineLeftRight : function($table,startColumnField,endColumnField){
			$.each($table.find("tr"),function(trNum,trItem){
				var startFlag = false;	//开始标识
				var $firstTd = null;	//合并的第一个单元格
				var spanNum = 1;	//合并的个数
				//遍历一行中的单元格
				$.each($(trItem).find("td"),function(tdNum,tdItem){
					var $tdItem = $(tdItem);
					var tdField = $tdItem.attr("field");	//该单元格的字段信息
					if(!startFlag && tdField == startColumnField){
						startFlag = true;
					}
					if(startFlag){
						if($tdItem.is(":hidden")){
							//若该单元格已经进行行合并，则不再进行列合并
							if($firstTd && !$firstTd.attr("c +" +
									-"olspan"))
								$firstTd.attr("colspan", spanNum);
							$firstTd = null;
							spanNum = 1;
							return true;	//跳过这次循环，进入下一次
						}
						var rowspanNum = $tdItem.attr("rowspan") || 1;	//该单元格所占用的列数
						var firstTdRowspanNum = $firstTd?$firstTd.attr("rowspan") || 1 : 1;	//合并单元格所占用的列数
						if($firstTd && $firstTd.text() == $tdItem.text() && firstTdRowspanNum == rowspanNum){
							//若值相等，合并值，并隐藏当前的单元格
							$tdItem.hide();
							spanNum++;
						}else{
							//设置合并的属性
							if($firstTd)
								$firstTd.attr("colspan", spanNum);
							//设置新的第一个单元格
							$firstTd = $tdItem;	//赋值合并的第一个单元格
							spanNum = 1;
						}
					}
					if(tdField == endColumnField){
						if($firstTd)
							$firstTd.attr("colspan", spanNum);
						return false;//结束时推出循环
					}
				})
			})
		},
		_combine : function($table){
			var that = this;
			if(!that.options.combineAble){
				return;
			}

			if(!that.options.combinedField || !that.options.combinedField[0]){
				return;
			}

			var combinedRuleDatas = that.options.combinedField[0].combinedRule;
			var columns = that.options.columns;	//获取表头信息

			var columnsJSON = {};
			$.each(columns,function(columnNum,column){
				columnsJSON[column.field] = column;
			});

			$.each(combinedRuleDatas,function(i,item){
				var combinedType = item.combinedType.id;//合并类型
				var startColumnField = item.startField.columnName;//开始字段
				var endColumnField = item.endField.columnName;//结束字段

				if(combinedType == 'upDown'){
					//上下合并
					columnsJSON[startColumnField].isEditor = false;	//取消编辑
					that._combineUpDown($table,startColumnField);
				}
				if(combinedType == 'leftRight'){
					//左右合并
					var startFlag = false;	//开始标识
					$.each(columns,function(columnNum,column){
						var columnField = column.field;
						if(!startFlag && columnField == startColumnField){
							startFlag = true;
						}
						if(startFlag){
							columnsJSON[columnField].isEditor = false;	//取消编辑
						}
						if(endColumnField && columnField == endColumnField){
							return false;
						}
					});
					that._combineLeftRight($table,startColumnField,endColumnField);
				}
				if(combinedType == 'all'){
					//全合并
					var startFlag = false;	//开始标识
					$.each(columns,function(columnNum,column){
						var columnField = column.field;
						if(!startFlag && columnField == startColumnField){
							startFlag = true;
						}
						if(startFlag){
							//进行行合并
							columnsJSON[columnField].isEditor = false;	//取消编辑
							that._combineUpDown($table,columnField);		
						}
						if(endColumnField && columnField == endColumnField){
							return false;
						}
					});
					that._combineLeftRight($table,startColumnField,endColumnField);
				}
			})
		},
		_loadDatas: function(datas, $target) {
			var that = this,
				opts = that.options;
			datas = datas || opts.datas;
			var dataSource = opts.dataSource || (opts.dataSource = new mt.DataSource(opts.ajax));
			if (datas) {
				// opts.__dataArray = [];
				
				if(that.options.keys){
					var children = that.options.keys.childrenKey || that._children;
					if(children){
						$.each(datas,function(i,item){
							item[children] = null;
						})	
					}
				}
				that._trigger("onLoadData", [datas]);
				that._appends(datas, $target);
				opts.expandChilds && that.iteratorQueryChildren && that.iteratorQueryChildren(datas);

				// that.options.events.onLoadSuccess([datas]);

				var onLoadSuccessFunc = $(that.target).data("__onLoadSuccess__") || that.options.events.onLoadSuccess;
				//触发加载完事件
				if($.isFunction(onLoadSuccessFunc)){
					onLoadSuccessFunc();
					// that.options.events.onCheckRow(rowData[that._rowIndex], rowData);
				}

				that._combine($(that.target).find(".grid-content table tbody"));

				if(typeof(reValue) != "undefined" && initProgressBarFunc && $.isFunction(initProgressBarFunc))
					initProgressBarFunc();
				that.resize();
				//that._initGridButton();
			} else if (dataSource) {
				if (dataSource instanceof Array) {
					that._ajaxSuccess(dataSource, $target);
				} else if (dataSource.datas) {
					that._ajaxSuccess(dataSource.datas, $target);
				} else if (dataSource.options.read.url) {
					that.query(dataSource.options.read.data, $target);
				}
			} else if (opts.ajax.read) {

			}
			
			that.__setTargetColor(that.target,opts);
			$(that.target).find(".pagination").css("float",opts.pagePosition);
			$(that.target).find(".page-size").css("float",opts.pagePosition);
			$(that.target).find(".total").css("float",opts.pagePosition);
			if(opts.pagePosition == 'right'){
				$(that.target).find(".total").after($(that.target).find(".page-size"));
			}
			else if(opts.pagePosition == 'left'){
				$(that.target).find(".page-size").after($(that.target).find(".total"));
				$(that.target).find(".total").after($(that.target).find(".pagination"));
			}
			$(that.target).find(".red").closest("td").css("text-align",opts.textAlign);
			$(that.target).find(".green").closest("td").css("text-align",opts.textAlign);
			//获取所有的行
			that.options.events.onUpdateButtonClick();
			var trs=$(that.target).find('tr');
			//获取所有的数据
			var data=this.getData();
			var colordefine=this.options.colorDefin;
			if(data.length>0){
				for(var i=0;i<data.length;i++){
					for(var pro in data[i]){
						for(var j in colordefine){
							var exp=colordefine[j].exp;
								//fieldname
								var exps=exp.substring(exp.lastIndexOf(".")+1,exp.indexOf("}"));
								var rowdata=pro;
								var expdata=exps;
								if(rowdata.indexOf(expdata)!=-1){
									//表达式
									var realexp=exp.replace(/~F{[\w|\.]+}/g,data[i][rowdata]);
									var rel="";
									try{
										rel=eval(realexp);
									}catch(e){
										rel=eval("'"+realexp+"'") 
									}
									if(rel){
										//当前行
										$(trs[i+1]).css('background-color',colordefine[j].color);
									}
								}
						} 
					}
				}
				
			}
		},
		_getParameterMap: function(data) {
			return this.options.ajax.parameterMap(data);
		},
		_request: function(ar, params) {
			var that = this;
			
			
			if (!ar.__success__) { //第一次ajax 调用
				ar.success = function(ret) {
					that._ajaxSuccess(ret, ar.__target);

				};

				ar.__success__ = true;
				ar.__data__ = $.extend(ar.__data__ || {}, ar.data); // 原始参数保存
				ar.d_data__ = $.extend(ar.d_data__ || {}, ar.data); // 动态参数保存
				//	$.extend(this.getParams(), ar.data);
			}
			that._initParams(ar, params);
			
			$.ajax(ar);
		},
		__sortArray: [],
		_initParams: function(ar, params) {
			var data = $.extend(true, {}, ar.d_data__, ar.__data__, params);
			if (this.options.pagination.paging) {
				$.extend(data, {
					page: this.options.pagination.page,
					pageSize: this.options.pagination.pageSize
				});
			}
			//将查询参数信息放入保存中,ajax.save
			if(params && this.options.ajax.save){
				this.options.ajax.save.data.params = JSON.parse(params.params || "{}");
			}
			$.extend(data, {
				sort: this.__sortArray
			});
			ar.data = this._getParameterMap(data);
		},
		_ajaxSuccess: function(ret, $target,parentId) {
			var that = this,
				a = that.options.ajax,
				datas = null,
				total = 0;
			if(parentId){
				for(var i = 0 ; i < that.requestQueue.length ; i++){
					if(that.requestQueue[i] == parentId){
						that.requestQueue.splice(i,1);	
					}
				}	
			}
			if (ret) {
				if (ret instanceof Array) {
					datas = ret;
					total = datas.length;
				} else if (ret instanceof Object) { //TODO 分页
					datas = ret[a.schema.data];
					total = ret[a.schema.total];
					for (i = 0; i < datas.length; i++) {
						datas[i].id = datas[i][a.schema.model.id];
					}
				}
			}
			if (this.options.occupy) {
				datas = that._offset(datas);
			}
			that._total = total;
			that._paging(total);
			that._loadDatas(datas || [], $target);
			//取消复选框选中状态
			// that._cancelCheSelect(that.target); 
			id = that.selectedDatAafterRefreash;
			that.select(id);
		},
		_cancelCheSelect : function($target){
			$($target).find("input[type=checkbox]").attr("checked",false);
		},
		_offset: function(datas) {
			var page = this.options.pagination;
			if (datas && datas[0] && page && page.paging) {
				var offset = page.pageSize - datas.length;
				for (var i = 0; i < offset; i++) {
					datas.push({
						offsetRow: true
					});
				}
			}
			return datas;
		},
		_paging: function(total) {
			var page = this.options.pagination;
			var pageable = page.pageable;
			if (pageable) {
				$.extend(page, pageable, pageable.messages);
			}
			if (page && page.paging) {
				var that = this;
				var $target = $(that.target),
					$id = $target.attr("paging");
				!$id && ($target.attr("paging", ($id = 'pageId-' + new Date().getTime())));
				var $page = $("#" + $id),
					pageFn = $page.data("pagination");
				if (!$page[0]) {
					$page = $("<div>", {
						id: $id,
						class: 'page',
						'style': 'text-align:right;'
					}).pagination($.extend(true, {}, page, {
						total: total,
						events: {
							onSelectPage: function(pageNumber, pageSize) {
								var options = {
									page: pageNumber,
									pageSize: pageSize
								};
								$.extend(page, options);
								that.isPageParams = true ;
								that.query();
								that.isPageParams = false ;
								//  that._request(that.options.ajax.read.data, options);
							},
							onChangePageSize: function(pageNumber, pageSize) {
								$.extend(page, {
									page: pageNumber,
									'pageSize': pageSize
								});
								that.isPageParams = true ;
								that.query();
								that.isPageParams = false ;
							},
							onRefresh: function(pageNumber, pageSize) {
								that.isPageParams = true ;
								that.query();
								// that.load();
								that.isPageParams = false ;
							}
						}
					}));
					$target.append($page);
				} else {
					pageFn.refresh({
						total: total,
						pageNumber: page.page
					});
				}
			}
		},
		
		/**
		 * 客户端排序
		 */
		SortDatas : function(datas, n){
			var that = this, sortBy = that.options.sortBy,
				desc = that.options.sortDesc;
			if(sortBy && datas && datas.length){
				try {
					//eg : sortBy = index_id asc, id desc
					function sort(key, dc){
						datas.sort(function(a, b){
							var rst = (a[key] || 0) - (b[key] || 0);
							return dc ? -rst : rst;
						});
					};
					var sortBys = sortBy.split(",")	;
					$.each(sortBys, function(i, v){
						var sbs = v.split(" "), key = sbs[0].trim(), dc = sbs[1];
						if(dc && (dc = dc.trim())){
							dc = dc == "desc";
						} else {
							dc = desc;
						}
						sort(key, dc);
					});
				} catch(e) {
					console.log(e);
				}
			}
		},
		
		_sortDatas: function(datas, n) {
			this.options.__dataArray = [];
			
			this.SortDatas(datas, n);
			
			return datas;
		},
		_buildClass: function(n, c) {
			c.appendFormat(" row-index='{0}'", n[this._rowIndex]);
		},
		//	_getEditor : function(n, v) {
		//		return this._getTemp(n, v, 'editor');
		//	},
		_handleTemplate: '<a href="javascript:;" class="btn {0}" atype={1}><i class="fa {2}"></i>{3}</a>',
		_getCommand: function(v, k, j) {
			var handleTemplate = new StringBuffer(),
				that = this;
			if (v[k] && v[k] instanceof Array) {
				$.each(v[k], function(i, o) {
					if (o.name == "edit") {
						handleTemplate.appendFormat(that._handleTemplate, 'red', o.name, 'fa-edit', o.text);
					} else if (o.name == "destroy") {
						handleTemplate.appendFormat(that._handleTemplate, 'purple', o.name, 'fa-times', o.text);
					} else if (o.name == "upward"){
						handleTemplate.appendFormat(that._handleTemplate, 'green', o.name, '', o.text);
					} else if (o.name == "downward"){
						handleTemplate.appendFormat(that._handleTemplate, 'yellow', o.name, '', o.text);
					}else{
						handleTemplate.appendFormat(that._handleTemplate, o.color||'yellow', o.name, o.icon, o.text);
					}
				});
			}
			return handleTemplate.toString();
		},
		_getCheckBox: function(v, k, j, otherClass) {
			if(v.type == "inner_checkbox"){
				var handleTemplate = new StringBuffer(),
					that = this;
				// var str = "<input class='grid_inner_checkbox' type='checkbox'>";
				return "<input class='"+v.type+" "+(otherClass || "")+"' type='checkbox'>";
			}
			return "";
		},
		_getTemp: function(n, v, k) {
			if(n.isstatistics){
				return '';
			}
			if ((k = v[k]) && $.isFunction(k))
				k = k(n, v);
			return k || '';
		},
		_getStatisticsColumn: function(v, k) {
			if (this._statistics.lineDone && (k = v[k]) && $.isFunction(k)) {
				k = k(this._statistics);
				return k || "";
			} else {
				return "";
			}
		},
		_getTemplate: function(n, v) {
			return this._getTemp(n, v, 'template') || this._getStatisticsColumn(v, 'footerTemplate') || this._getCommand(v, 'command') || this._getCheckBox(v,'checkbox');
		},
		_editDoneFlag: false, //结束编辑标志位
		_componentCache: {
			"select": {},
			"select2": {}
		},
		_componentHandle: function(n, v) {
			var content = "",
				that = this,
				val = n[v.field] ? n[v.field] : "";
			// if(this._editDoneFlag){
			if (v.editor && v.editor.component == "checkbox" && v.editor.dataSource) {
				$.each(v.editor.dataSource, function(i, o) {
					if (val == o.value) {
						content = o.text;
					}
				});
				return content;
			} else if (v.editor && v.editor.component == "fileupload") {
				// if(n[v.field+'componentData']!=undefined){
				// 	content =n[v.field+'componentData'].names||"";
				// }
				$.ajax({
					url: contextPath + "/mx/form/attachment?method=getFilesByRandomParent&randomParent=" + val
				}).always(function() {}).done(function(result) {
					var $tr = $(that.target).find("div.grid-content").find("tr[" + that._rowIndex + "=" + n[that._rowIndex] + "]");
					var $td = $tr.find("td[hid=" + v.uuid + "]");
					if ($td.length && result) {
						$td.html("");
						$.each(JSON.parse(result), function(index, file) {
							var $tg = $('<span/>').text(file.fileName),
								$a = $('<a>').attr('target', '_blank').prop('href', contextPath + "/mx/form/attachment?method=download&from=to_db&id=" + file.id).append($tg);
							var $div = $('<div/>').append($a);
							$td.append($div.outer());
						});
					}
				});
				return "";
			} else if (v.editor && v.editor.component == "dropdownlist") {
				if (v.editor.dataSource) {
					for (var i = 0; i < v.editor.dataSource.length; i++) {
						if (val == v.editor.dataSource[i].value) {
							return v.editor.dataSource[i].text;
						}
					}
					// if(v.editor.defaultIndex && v.editor.dataSource[v.editor.defaultIndex-1]){
					// 	var defaultIndexValue = v.editor.dataSource[v.editor.defaultIndex-1].text;
					// 	return defaultIndexValue || "";
					// }

					return "";
				} else if (v.editor && v.editor.params) {
					if (!that._componentCache.select[v.id] || (!that._componentCache.select[v.id].wait && !that._componentCache.select[v.id].cache)) {
						if(that._componentCache.select[v.id] == null){
							that._componentCache.select[v.id] = {};	
						}
						that._componentCache.select[v.id].wait = true;
						that._componentCache.select[v.id].list = [];
						$.ajax({
							url: contextPath + v.editor.url.replace("contextPath", ""),
							type: 'POST',
							dataType: 'JSON',
							contentType: "application/x-www-form-urlencoded",
							data: v.editor.params,
							success: function(data) {
								that._componentCache.select[v.id].cache = [];
								that._componentCache.select[v.id].cache = data;
								that._componentCache.select[v.id].wait = false;
								// that._componentCache.select.cache = [];
								// that._componentCache.select.cache = data;
								var list = that._componentCache.select[v.id].list;
								if (data) {
									for (var i = 0; i < data.length; i++) {
										var value = data[i][v.editor.params.value];
										if (n[v.field] == value) {
											content = data[i][v.editor.params.text];
										}
										$.each(list,function(j,item){
											if(item.n[item.v.field] == value){
												item.content = data[i][v.editor.params.text];
											}
										})
										data[i].id = data[i][v.editor.params.value];
										data[i].text = data[i][v.editor.params.text];
										data[i].value = data[i][v.editor.params.value];
									}
								}
								var $tr = $(that.target).find("div.grid-content").find("tr[" + that._rowIndex + "=" + n[that._rowIndex] + "]");
								var $td = $tr.find("td[hid=" + v.uuid + "]");
								if(!$td.find("div.cell-editor")[0]){
									$td.html(content ? content : "");	
								}

								$.each(list,function(i,item){
									var $tr = item.$target.find("div.grid-content").find("tr[" + item.rowIndex + "=" + item.n[item.rowIndex] + "]");
									var $td = $tr.find("td[hid=" + item.v.uuid + "]");
									// if(!item.content && item.v.editor.defaultIndex && data[item.v.editor.defaultIndex-1]){
									// 	var defaultIndexValue = data[item.v.editor.defaultIndex-1][item.v.editor.params.text];
									// 	$td.html(defaultIndexValue? defaultIndexValue : "");
									// }else{
									// 	$td.html(item.content ? item.content : "");	
									// }

									if(!$td.find("div.cell-editor")[0]){
										$td.html(item.content ? item.content : "");	
									}
								})
							},
							async: true
						});
						return "";
					} else {
						if(that._componentCache.select[v.id].wait){
							that._componentCache.select[v.id].list.push({
								$target : $(that.target),
								rowIndex : that._rowIndex,
								n : n,
								v : v,
							})
						}else{
							for (var i = 0; i < that._componentCache.select[v.id].cache.length; i++) {
								var data = that._componentCache.select[v.id].cache;
								var value = data[i][v.editor.params.value];
								if (val == value) {
									content = data[i][v.editor.params.text];
								}
								// if(!content && v.editor.defaultIndex && data[v.editor.defaultIndex-1]){
								// 	var defaultIndexValue = data[v.editor.defaultIndex-1][v.editor.params.text];
								// 	return defaultIndexValue || "";
								// }
							}
						}
						return content ? content : "";
					}
				} else {
					return "";
				}
			} else if (v.editor && v.editor.component == "multiselect") {
				var arr = val.split(","),
					arrOut = [];
				if (v.editor.dataSource) {
					for (var i = 0; i < v.editor.dataSource.length; i++) {
						var value = v.editor.dataSource[i].value;
						for (var j = arr.length - 1; j >= 0; j--) {
							if (arr[j] == value) {
								arrOut.push(v.editor.dataSource[i].text);
							}
						};
					}
					return arrOut.join(",");
				} else if (v.editor && v.editor.params) {
					if (!that._componentCache.select.cache) {
						$.ajax({
							url: contextPath + v.editor.url.replace("contextPath", ""),
							type: 'POST',
							dataType: 'JSON',
							contentType: "application/x-www-form-urlencoded",
							data: v.editor.params,
							success: function(data) {
								that._componentCache.select2.cache = [];
								that._componentCache.select2.cache = data;
								if (data) {
									for (var i = 0; i < data.length; i++) {
										var value = data[i][v.editor.params.value];
										for (var j = arr.length - 1; j >= 0; j--) {
											if (arr[j] == value) {
												arrOut.push(data[i][v.editor.params.text]);
											}
										};
									}
								}
								var $tr = $(that.target).find("div.grid-content").find("tr[" + that._rowIndex + "=" + n[that._rowIndex] + "]");
								var $td = $tr.find("td[hid=" + v.uuid + "]");
								
//								$td.html(arrOut.join(",")); 
								//2017-10-31  赖振锋 grid多选下拉框CRUD操作异常
								$td.html(arrOut.join(","));   
							},
							async: true
						});
						return "";
					} else {
						for (var i = 0; i < that._componentCache.select2.cache.length; i++) {
							var data = that._componentCache.select.cache;
							var value = data[i][v.editor.params.value];
							if (n[v.field] == value) {
								content = data[i][v.editor.params.text];
							}
						}
						return content ? content : "";
					}
				} else {
					return "";
				}
			} else if (v.editor && v.editor.component == "numerictextbox") {
				var out;
				if (v.format && !isNaN(parseFloat(val || 0))) {
					if(val!=""){
						out = hmtdUtils.format("{0:" + v.format + "}", parseFloat(val));
					}
				} else {
					out = ""
				}
				return out;
			} else if (v.editor && v.editor.component == "datepicker") {
				if(window.mtDate){
					return mtDate.formatDatetime(val, v.format);
				} else if (v.format) {
					var date = new Date(val);
					if (date != "Invalid Date") {
						return date.format(v.format);
					} else {
						try{
							if(val){
								var a = val.split(" ");
								var b = a[0].split("-"); 
								var c = a[1].split(":"); 
								date = new Date(b[0], b[1]-1, b[2], c[0], c[1], c[2]);
								if (date != "Invalid Date") {
									return date.format(v.format);
								}else{
									return "";
								}
							}
						}catch(e){
							return "";
						}
						return "";
					}
				} else {
					return "";
				}
			} else if (v.editor && v.editor.component == "datetimepicker") {
				if(window.mtDate){
					return mtDate.formatDatetime(val, v.format);
				} else if (v.format) {
					var date = new Date(val);
					if (date != "Invalid Date") {
						return date.format(v.format);
					} else {
						try{
							if(val){
								var a = val.split(" ");
								var b = a[0].split("-"); 
								var c = a[1].split(":"); 
								date = new Date(b[0], b[1]-1, b[2], c[0], c[1], c[2]);
								if (date != "Invalid Date") {
									return date.format(v.format);
								}else{
									return "";
								}
							}
						}catch(e){
							return "";
						}
						return "";
					}
				} else {
					return "";
				}
			} else {
				return val;
			}
			// }	
		},
		_buildCell: function(n, v, c, l) {
			var style = v.attributes ? v.attributes.style : '',
				val = "";
			val = (this._getTemplate(n, v) || this._componentHandle(n, v) || '');


			var strTemplate = "<td class='";
			var hidecommandCell = $(this.target).data("hidecommandCell");

			if(v.command && v.command.length > 0){
				//如果是操作列，则加上class
				strTemplate += "commandCell ";
			}
			if(this.options.dragEnable != undefined && this.options.dragEnable != false){
				if(typeof val == 'number' ||(typeof val == 'string' && val.indexOf("<") == -1)){
					strTemplate += "dd-td' field='{0}' hid='{2}' style='{3}' title='{4}'>{1}</td>";
				}else{
					strTemplate += "dd-td' field='{0}' hid='{2}' style='{3}'>{1}</td>";
				}
			}
			else{
				
				if(typeof val == 'number' ||(typeof val == 'string' && val.indexOf("<") == -1) ){
					strTemplate += "'  field='{0}' hid='{2}' style='{3}' title='{4}'>{1}</td>";
				}else{
					strTemplate += "'  field='{0}' hid='{2}' style='{3}'>{1}</td>";	
				}
			}
			var titleVal = n[v.field];
			if(typeof val == 'string' && val.match("[,]")){
				titleVal = val.replace(/,/g,'&sbquo;');
			}
			c.appendFormat(strTemplate,
				v.field || '',
				val = (val || ''),
				v.uuid,
				(this.options.showInLine ? "white-space: nowrap;overflow: hidden; text-overflow:ellipsis;word-wrap:keep-all;" : "word-wrap:break-word;")+((v.hidden || (v.command && v.command.length > 0 && hidecommandCell))?"display:none;":"") + (style || ""),
				//this.options.showInLine?val:"",
				titleVal,
				n);
		},
		_offsetCell: function(v, c) {
			var style = v.attributes ? v.attributes.style : '';
			c.appendFormat("<td style='{0}'>&nbsp;</td>", 'word-wrap:break-word;' + (style));
		},
		_append: function(n, c) {
			if (n) {
				var i = 0,
					v, that = this,
					index = 0,
					opts = that.options;
				if(opts.dragEnable != undefined && opts.dragEnable != '' && opts.dragEnable != null &&  opts.dragEnable != false){
					c.append("<tr class='dd-tr'");
				}
				else{
					c.append("<tr ");
				}
				that._buildClass(n, c);
				c.append(">");
				
				for (; i < opts.__columns.length; i++) {

					v = opts.__columns[i];
					v.field && (n[v.field] === undefined) && (n[v.field] = n[v.field] || "");
					if (n.offsetRow) {
						that._offsetCell(v, c);
					} else {
						that._buildCell(n, v, c, index++);
					}

					if (that._statistics[v.field]) {
						that._statistics[v.field].sum = that._statisticsFunc.sum(that._statistics[v.field].sum, parseFloat(n[v.field]));
						that._statistics[v.field].max = that._statisticsFunc.max(that._statistics[v.field].max, parseFloat(n[v.field]));
						var min = parseFloat(n[v.field]);
						if(that._statistics[v.field].min){
							that._statistics[v.field].min = that._statisticsFunc.min(that._statistics[v.field].min, min);	
						}else if(!isNaN(min)){
							that._statistics[v.field].min = min;
						}
						
						that._statistics[v.field].count = that._statistics.lineNo;
						that._statistics[v.field].average = that._statisticsFunc.average(that._statistics[v.field].sum, that._statistics[v.field].count);
					} else {
						that._statistics[v.field] = {};
						that._statistics[v.field].sum = parseFloat(n[v.field]);
						that._statistics[v.field].max = 0;
						var min = parseFloat(n[v.field]);
						if(!isNaN(min)){
							that._statistics[v.field].min = min;
						}else{
							that._statistics[v.field].min = '';	
						}
						that._statistics[v.field].count = that._statistics.lineNo;
						that._statistics[v.field].average = 0;
					}
					if (v.footerTemplate) {
						that._statistics.flag = true;
					}
				}
				this.options.scrollable && c.append('<td style="border:0px;"></td>');
				c.append("</tr>");
			}
		},
		_statistics: {
			flag: false,
			lineDone: false
		}, //列统计缓存
		_statisticsFunc: {
			sum: function(accumulator, value) {
				if (isNaN(accumulator)) {
					accumulator = value;
				} else if (!isNaN(value)) {
					accumulator += value;
				}
				return accumulator;
			},
			max: function(accumulator, value) {

				if (accumulator < value) {
					accumulator = value;
				}
				return accumulator;
			},
			min: function(accumulator, value) {

				if (accumulator > value) {
					accumulator = value;
				}
				return accumulator;
			},
			average: function(sum, count) {
				return sum / count;
			}
		},
		_newRow: function() {
			var that = this,
				i = 0,
				opts = that.options;
			that.endEdit();
			var c = new StringBuffer();

			var d = that._attr({});
			that._append(d, c);
			that.tbody().prepend(c.toString());
			$tr = that.tbody().find('tr[' + this._rowIndex + "=" + d[this._rowIndex] + ']');
			$tr.addClass('new');
			// $btn = $tr.find('a[atype="edit"]');
			that._toUpdate($tr);
		},
		refreshRow: function(o, n) { //刷新$o 当前行
			var $o = $(o),
				that = this;
			!$o.is("tr") && ($o = $o.closest("tr"));
			n[this._rowIndex] = ($o.attr(this._rowIndex) || -1) * 1;
			var c = new StringBuffer();
			var arr = that._getDataArray();
			if (arr && arr.length) {
				$.each(arr, function(i, v) {
					if (v[that._rowIndex] == n[that._rowIndex]) {
						try {
							for (var k in v) {
								delete v[k];
							}
							$.extend(v, n);
						} catch (e) {
							console.log(e);
						}
					}
				});
			}
			this._append(n, c);
			if (c.size()) {
				var $tr = $(c.toString());
				$o.replaceWith($tr);
			}
		},
		//清空数据及页面的数据
		_clearAllDatas : function(){
			this.options.__dataArray = [];
			return this.tbody().empty();
		},
		_appends: function(datas, $target) {
			var t = this,
				S = new StringBuffer(),
				n = 0,
				str, isTr, d;
			if(!$target){
				//清空数据及页面的数据
				$target = t._clearAllDatas();
				// $target = t.tbody().empty();
			}
			// $target = $target || t.tbody().empty();
			!!(isTr = $target.is("tr")) && (n = ((t.getRow($target)||{})._level_ || 0 ) + 1);
			//(((n = (t.getRow($target) || {})._level_) || -1) + 1);
			datas = t._sortDatas(datas, n);

			t._statistics = {
				flag: false,
				lineDone: false
			}; //初始化列统计缓存
			for (var i = 0, len = datas.length; i < len; i++) {
				d = t._attr(datas[i]);
				t._statistics.lineNo = i + 1;
				t._append(d, S);
			}

			if (t._statistics.flag) {
				var n = t._attr({});
				n.isstatistics = true;	//加上判断，证明为列统计，用于过滤列的样式
				t._statistics.lineDone = true;
				t._append(n, S);
			}

			str = S.toString();
			return isTr ? $target.after(str) : $target.append(str);
		},
		_rowIndex: 'row-index',
		_attr: function(n) {
			if (n) {
				n[this._rowIndex] = this._getDataArray().length;
				this._getDataArray().push(n);
			}
			return n;
		},
		tbody: function() {
			var $table = this.table();

			!($table.find("tbody")[0]) && $table.append("<tbody class='dd-tbody'></tbody>");

			return $table.find("tbody");
		},
		load: function(datas, $target) {
			this._loadDatas(datas, $target);
		},
		query: function(data, $target) {
			var that = this,
				ar = that.options.ajax.read;

			that.requestQueue = that.requestQueue || [];
			var data_parentId = null;
			if(data && data.parentId){
				that.requestQueue.push(data.parentId);
				data_parentId = data.parentId;
			}
			ar.success = function(ret) {
				that._ajaxSuccess(ret, $target, data_parentId);	
			};
			var params = that.getParams(data);
			that._request(ar, $.extend(true, {}, params)); //把data中rid等参数放入params
		},
		
		ward: function(data, $target){
			var that = this,
				ar = that.options.ajax.ward;
			ar.success = function(ret) {
				that.isPageParams = true ;
				that.load();
				that.isPageParams = false ;
			};
			//var params = that.getParams(data);
			that._request(ar, $.extend(true, {}, data)); //把data中rid等参数放入params
		},
		wardBydrag: function(data, $target){
			var that = this,
				ar = that.options.ajax.wardBydrag;
			ar.success = function(ret) {
				that.isPageParams = true ;
				that.load();
				that.isPageParams = false ;
			};
			//var params = that.getParams(data);
			that._request(ar, $.extend(true, {}, data)); //把data中rid等参数放入params
		},
		getParams: function(params) {
			var that = this,
				data = that.options.ajax.read.d_data__ = (that.options.ajax.read.__data__ ? $.extend({}, that.options.ajax.read.__data__) : undefined); //取动态参数
			var ret = $.extend({}, data || this.options.ajax.read.data);
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
			}else if(!params && that.isPageParams){
				ret = $.extend({}, JSON.parse(this.options.ajax.read.data));
			}
			return ret;
		},
		getData: function() {
			var datas = [],
				v, i = 0,
				arr = (this._getDataArray() || []),
				len = arr.length;
			for (; i < len; i++) {
				v = arr[i];
				if (v) datas.push(v);
			}
			return datas;
		},
		getChangeRows: function() {
			var datas = [],
				v, i = 0,
				arr = (this._getDataArray() || []),
				len = arr.length;
			for (; i < len; i++) {
				v = arr[i];
				v && v[this.dirtyKey] && datas.push(v);
			}
			return datas;
		},
		_getFactRow: function(o) {
			var that = this,
				rowIndex = this.getRowIndex(o);
			var row = null,
				arr = this._getDataArray();
			$.each(arr, function(i, v) {
				if (v && v[that._rowIndex] == rowIndex) {
					row = v;
					return false;
				}
			});
			return row;
		},
		getRow: function(o) {
			//	var index = this.getRowIndex(o), row = this._getDataArray()[index];
			var row = this._getFactRow(o);
			if (row && row[this._children])
				delete row[this._children];
			return row;
		},
		getDomRow: function(o) {
			var that = this;
			var $retDom = $(o).closest("tr");
			// if(!$retDom[0] || !$retDom.attr(this._rowIndex)){
			// 	$retDom = $(o).closest("tr.gridTr");
			// }
			if(!$retDom[0] || !$retDom.attr(this._rowIndex)){
				$retDom = $(o).closest("tr["+that._rowIndex+"]");
			}
			return $retDom;	
		},
		getRowIndex: function(o) {
			var rowIndex = this.getDomRow(o).attr(this._rowIndex);
			return parseInt(rowIndex || '-1');
		},
		getSelections: function() {
			return this.tbody().find("tr." + this.options.selectedCls);
		},
		getSelectedRows: function() {
			var selectedRows = new Array(),
				that = this;
			$.each(that.getSelections() || [], function() {
				selectedRows.push(that.getRow(this));
			});
			return selectedRows;
		},
		/**
		 * 获取勾选节点的JQUERY对象
		 * @return {[type]} [description]
		 */
		getCheckedRows: function() {
			return this.tbody().find("tr .inner_checkbox:checked").closest("tr");
		},
		/**
		 * 获取勾选节点的数据
		 * @return {[type]} [description]
		 */
		getCheckedRowsData: function() {
			var checkedRowsData = new Array(),
				that = this;
			$.each(that.getCheckedRows() || [], function() {
				checkedRowsData.push(that.getRow(this));
			});
			return checkedRowsData;
		},
		bulkCheckRows : function(idVals,items){
			// if(idVals){
			// 	var ids=idVals.split(",");
			// 	for(var i=0;i<item.length;i++){
			//     	 var ite=item[i];
		 //    		  if(idVal.indexOf(ite.id)!=-1){
		 //    			var rowIndex=ite['row-index']+1;
			//     		var trs=$(this.target).find("tr");
			//     		var tr=$(trs[rowIndex]);
			//     		$(tr[0]).find(".inner_checkbox").prop('checked',true).trigger("change");
		 //    		  }
			//      }
			// }
			
			// if(isNaN(id[0])){
			// 	if(idVal&&item){
			// 		var values=[];
				     
			// 	}
			// }else{

			// }
		},
		checkRow : function(idVal,selector,item){
			//如果是
			if(idVal){
				var datas = that.getData();
			    for(var i=0;i<datas.length;i++){
			    	 var ite=datas[i];
		    		  if(idVal.indexOf(ite.id)!=-1){
		    			var rowIndex=ite['row-index']+1;
			    		var trs=$(this.target).find("tr");
			    		var tr=$(trs[rowIndex]);
			    		$(tr[0]).find(".inner_checkbox").prop('checked',true).trigger("change");
		    		  }
			     }
			}else if(selector){
				for(var i=0;i<id.length;i++){
					var trs=$(this.target).find("tr");
		    		var tr=$(trs[id[i]]);
		    		$(tr[0]).find(".inner_checkbox").prop('checked',true).trigger("change");
				}
			}else if (item){
				var $tr = this.tbody().find('tr[' + this._rowIndex + "=" + item[this._rowIndex] + ']');
				$tr.find(".inner_checkbox").prop('checked',true).trigger("change");
			}
		},
		getUnSelections: function() {
			return this.tbody().find("tr:not(." + this.options.selectedCls +")");
		},
		getUnselectedRows: function(){
			var unselectedRows = new Array(),
				that = this;
			$.each(that.getUnSelections() || [], function() {
				unselectedRows.push(that.getRow(this));
			});
			return unselectedRows;
		},
		select: function(idVal, selector, selectData) {
			var that = this;
			var datas = this.getData();
			var flag = false;
			// this.getSelections().removeClass(this.options.selectedCls);
			if (idVal) {
				for (var j = 0; j < datas.length; j++) {
					if (datas[j]['id'] == idVal) {
						var $tr = this.tbody().find('tr[' + this._rowIndex + "=" + datas[j][this._rowIndex] + ']');
						$tr.addClass(this.options.selectedCls);
						// $tr.find(".inner_checkbox").attr('checked',true);
						// this.options.events.onClickRow(datas[j][this._rowIndex], datas[j]);

						var onClickRowFunc = $(that.target).data("__onClickRow__") || that.options.events.onClickRow;
						//触发点击事件
						if($.isFunction(onClickRowFunc)){
							onClickRowFunc();
							// that.options.events.onCheckRow(rowData[that._rowIndex], rowData);
						}
						flag = true;
						// this._trigger("onClickRow",[datas[j][this._rowIndex],datas[j]]);
					}
				}
			} else if(selector){
				// this.tbody().find(selector).addClass(this.options.selectedCls);
				for (var j = 0; j < datas.length; j++) {
					if (this.tbody().find(selector).length > 0) {
						var $trs = this.tbody().find(selector);
						for (var i = 0; i < $trs.length; i++) {
							if ($trs.eq(i).attr(this._rowIndex) == datas[j][this._rowIndex]) {
								// this._trigger("onClickRow",[datas[j][this._rowIndex],datas[j]]);
								var $tr = this.tbody().find('tr[' + this._rowIndex + "=" + datas[j][this._rowIndex] + ']');
								$tr.addClass(this.options.selectedCls);
								// $tr.find(".inner_checkbox").attr('checked',true);
								// this.options.events.onClickRow(datas[j][this._rowIndex], datas[j]);

								var onClickRowFunc = $(that.target).data("__onClickRow__") || that.options.events.onClickRow;
								//触发点击事件
								if($.isFunction(onClickRowFunc)){
									onClickRowFunc();
									// that.options.events.onCheckRow(rowData[that._rowIndex], rowData);
								}
								flag = true;
							}
						}
					}
				}
			}else if(selectData){
				var $tr = this.tbody().find('tr[' + this._rowIndex + "=" + selectData[this._rowIndex] + ']');
				$tr.addClass(this.options.selectedCls);
				// $tr.find(".inner_checkbox").attr('checked',true);
				// this.options.events.onClickRow(selectData[this._rowIndex], selectData);
				var onClickRowFunc = $(that.target).data("__onClickRow__") || that.options.events.onClickRow;
				//触发点击事件
				if($.isFunction(onClickRowFunc)){
					onClickRowFunc();
					// that.options.events.onCheckRow(rowData[that._rowIndex], rowData);
				}
				flag = true;
			}
			return flag;
		},
		cancelSelect: function() {
			this.getSelections().removeClass(this.options.selectedCls);
		},
		removeRowFromDB: function(o) { //服务端删除

			var $tr = this.getDomRow(o),
				that = this;
			if ($tr.get(0)) {
				this.ajaxDestroy([this.getRow(o)], function() {
					$tr.remove();
					that.query({
						id: ""
					});
				});
			}
		},
		removeRow: function(o) { //客户端删除

			//			var $tr = this.getDomRow(o),that = this;
			//			if($tr.get(0)){
			//				this.ajaxDestroy([this.getRow(o)], function(){
			//					$tr.remove();
			//					that.load();
			//				});
			//			}
			var rowIndex = null,
				that = this;
			if (o instanceof Number) {
				rowIndex = o;
			} else {
				rowIndex = this.getDomRow(o).attr(this._rowIndex);
				try {
					rowIndex = parseInt(rowIndex);
				} catch (e) {
					console.log(e);
				}
			}

			try {
				if (rowIndex != null) {
					var $tr = this.tbody().find('tr[' + this._rowIndex + "=" + rowIndex + ']');
					$tr.remove();
					var arr = that._getDataArray();
					$.each(arr, function(i, v) {
						if (v && (v[that._rowIndex] == rowIndex)) {
							arr[rowIndex] = null;
						}

						/*if(v[that._rowIndex] == rowIndex){
							arr.splice(i, 1);
						}*/
					});
					//that._getDataArray().splice(rowIndex*1, 1);
				}
			} catch (e) {
				console.log(e);
			}

		},
		_getEditor: function(o) {
			var that = this,
				$td = $(o).closest("td"),
				hid = $td.attr('hid'),
				editorFn = null;
			//return this._getTemp(n, v, 'editor');

			if (hid) {
				var c = that._colObj()[hid];
				editor = c.editor;
			}
			if ($.isFunction(editor)) {
				editorFn = editor;
			} else if (c && c.isEditor && editor && editor.component) {
				if (editor.component == 'dropdownlist') {
					editorFn = that._editors.select;
				} else if (editor.component == 'multiselect') {
					editorFn = that._editors.select2;
				} else if (editor.component == 'numerictextbox') {
					editorFn = that._editors.touchspin;
				} else if (editor.component == 'maskedtextbox') {
					editorFn = that._editors.textbox;
				} else if (editor.component == 'datepicker') {
					editorFn = that._editors.datepicker;
				} else if (editor.component == 'datetimepicker') {
					editorFn = that._editors.datetimepicker;
				} else if (editor.component == 'checkbox') {
					editorFn = that._editors.checkbox;
				} else if (editor.component == 'fileupload') {
					editorFn = that._editors.fileupload;
				}else if (editor.component == 'radio') {
					editorFn = that._editors.radio;
				}
			}
			return editorFn;
		},
		_editing: 'editing',
		beginEdit: function(o) {
			var that = this,
				$tr = that.getDomRow(o);
			this._editDoneFlag = false;
			if (!$tr.hasClass(that._editing)) {
				var row = that.getRow($tr);
				$tr.addClass(that._editing).find("td").each(function() {
					var editorFn = that._getEditor(this);
					var $this = $(this);
					if (editorFn) {
						var edit = $("<div>", {
							class: 'cell-editor'
						}).css({
							width: '96%',
							'text-align': "center"
						});
						$this.empty();
						$this.append(edit);
						var options = that._colObj()[$this.attr('hid')];
						if (options.editor && options.editor.component == "dropdownlist" && !options.editor.dataSource){
							options.editor.dataSource = that._componentCache.select[options.id]?that._componentCache.select[options.id].cache:"";
						}
						options.defaultTimePicker = that.options.defaultTimePicker;
						editorFn.call($this, edit, options, row);
					}
				});
			}
			if($.isFunction(that.options.events.beginEditEvent)){
				that.options.events.beginEditEvent(o);
			}
		},
		dirtyKey: "__dirties",
		/**
		 * 取消编辑状态
		 * @param  {[type]} o                   [对应的行]
		 * @param  {[type]} keeySelectedState [是否保留选中状态，默认清除]
		 * @return {[type]}                     [description]
		 */
		_endEdit: function(o,keeySelectedState) {
			var that = this,
				$tr = that.getDomRow(o);
			this._editDoneFlag = true;

			if ($tr.hasClass(that._editing)) {
				if($.isFunction(that.options.events.onBeforeEndEdit)){
					that.options.events.onBeforeEndEdit();
				}

				var row = that.getRow($tr),
					dirtyKey = that.dirtyKey;
				var fields = that.options.ajax.schema.model.fields;
				if (fields) {
					$.each(fields, function(key, v) {
						!row[key] && row[key] !== 0 && (row[key] = null);
					});
				}
				if (row) {
					!row.__Old && (row.__Old = $.extend({}, row));
					$tr.removeClass(that._editing)
					if(!keeySelectedState && !that.options.selectable == "multiple"){
						$tr.removeClass(that.options.selectedCls)
					}
					//
					$tr.find("td").each(function(i, td) {
						var $this = $(this);
						var $cellEditor = $this.find("div.cell-editor");
						if ($cellEditor.get(0)) {
							var $field = $cellEditor.find("[field]");
							if ($field.get(0)) {
								var field = $field.attr("field");
								if (row[field] !== $field.val()) {
									!row[dirtyKey] && (row[dirtyKey] = {});
									row[dirtyKey][field] = row[field] = [$field.val()].join(",");
									var fieldToLowerCase = field.toLowerCase();
									if(field != fieldToLowerCase){
										//若该字段不是小写时，则存在另一个小写的字段
										row[dirtyKey][fieldToLowerCase] = row[fieldToLowerCase] = [$field.val()].join(",");
									}
								}
							}
							var c = new StringBuffer();
							v = that.options.__columns[i];
							if(that.plugin && that.plugin == 'treelist'){
								that._buildCell(row, v, c, i, that.isExpanded($this));
							}else{
								that._buildCell(row, v, c, i, null);
							}
							$this.replaceWith(c.toString());
						}
					});
				}
				//				else{
				//					var row= {};
				//					$tr.removeClass(that._editing).removeClass(that.options.selectedCls)
				//					.find("td").each(function(i, td) {
				//						var $this = $(this);
				//						var $cellEditor = $this.find("div.cell-editor");
				//						if ($cellEditor.get(0)) {
				//							var $field = $cellEditor.find("[field]");
				//							if($field.get(0)){
				//								var field = $field.attr("field");
				//								if($field.val()){
				//									!row[dirtyKey] && (row[dirtyKey] = {});
				//									row[dirtyKey][field] = $field.val();
				//								}
				//							}
				//							var c = new StringBuffer();
				//							v = that.options.__columns[i];
				//							
				//							that._buildCell(row, v, c, null);
				//							$this.replaceWith(c.toString());
				//						}
				//					});
				//				}
			}
		},
		_giveUpEdit: function(o) {
			var that = this,
				$tr = that.getDomRow(o);
			if ($tr.hasClass(that._editing)) {
				var row = that.getRow($tr);
				$tr.removeClass(that._editing).removeClass(that.options.selectedCls) //
					.find("td").each(function(i, td) {
						var $this = $(this);
						var $cellEditor = $this.find("div.cell-editor");
						if ($cellEditor.get(0)) {
							var c = new StringBuffer();
							v = that.options.__columns[i];
							if(that.plugin && that.plugin == 'treelist'){
								that._buildCell(row, v, c, i, that.isExpanded($this));
							}else{
								that._buildCell(row, v, c, i, null);
							}
							$this.replaceWith(c.toString());
						}
					});
			}
		},
		acceptChanges: function() {
			var changes = this.getChangeRows();
			if (changes && changes.length) {
				var dirtyKey = this.dirtyKey;
				$.each(changes, function(i, c) {
					c[dirtyKey] && ($.each(c[dirtyKey], function(k, v) {
						c[k] = v;
					}));
				});
			}
		},
		rejectChanges: function() {
			var changes = this.getChangeRows();
			if (changes && changes.length) {
				$.each(changes, function(i, c) {
					var o = c.__Old;
					if (o) {
						c.__Old = undefined;
						$.extend(c, o);
					}
				});
			}
		},
		ajaxSave: function(models, fn) {
			var that = this,
				options = that.options;
			var dataSource = options.dataSource;
			dataSource.save(models, fn).async();

		},
		ajaxDestroy: function(models, fn) {
			var that = this,
				options = that.options;
			var dataSource = options.dataSource;
			show({
				title: '信息',
				message: '你确定删除吗?',
				buttons: [{
					label: '确认',
					cssClass: 'btn-primary',
					action: function(d) {
						dataSource.destroy(models, fn).async();
						d.close();
					}
				}, {
					label: '取消',
					cssClass: '',
					action: function(d) {
						d.close();
					}
				}]
			});
			/*confirm2("你确定删除吗?", function(ok){
				if(ok){
					dataSource.destroy(models, fn).async();
				}
			});*/
		},
		setRowBgColor: function(rowIndex, color) {
			$("[" + this._rowIndex + "=" + rowIndex + "]").css('background-color', color);
		},
		renderRow: function(o) {

		},
		getTotal : function(){
			var that = this;
			return that._total;
		},
		//取消编辑
		endEdit : function(){
			var that = this;
			if (that.options.editable) {
				var $table = that.table();
				$table.find("tr.editing").each(function() {
					var $tr = $(this);
					that._endEdit($(this),true);

					var $updateBtn = $tr.find(".btn[atype='update']");
					if($updateBtn[0]){
						var newBtn = new StringBuffer();
						var row = that.getRow($updateBtn[0]);
						newBtn.appendFormat('<i class="fa {0}"></i>{1}', 'fa-edit', '编辑');
						$updateBtn.html(newBtn.toString());
						$updateBtn.removeClass('green').addClass('red');
						$updateBtn.attr("atype", "edit");
						//样式
						newBtn = new StringBuffer();
						newBtn.appendFormat('<i class="fa {0}"></i>{1}', 'fa-times', '删除');
						$tr.find('a[atype="cancel"]').html(newBtn.toString());
						$tr.find('a[atype="cancel"]').removeClass('default').addClass('purple');
						$tr.find('a[atype="cancel"]').attr("atype", "destroy")
					}

					
				});
			}
		},
		editRow : function(idVal, selector, selectData){
			var that = this;
			var $table = that.table();
			var datas = this.getData();
			if (idVal) {
				for (var j = 0; j < datas.length; j++) {
					if (datas[j]['id'] == idVal) {
						$table.find("tr.editing").each(function(i,item) {
							that._endEdit($(item));
						});
						that.beginEdit($table.find('tr[' + this._rowIndex + "=" + datas[j][this._rowIndex] + ']'));
					}
				}
			} else if(selector){
				// this.tbody().find(selector).addClass(this.options.selectedCls);
				for (var j = 0; j < datas.length; j++) {
					if (this.tbody().find(selector).length > 0) {
						var $trs = this.tbody().find(selector);
						for (var i = 0; i < $trs.length; i++) {
							if ($trs.eq(i).attr(this._rowIndex) == datas[j][this._rowIndex]) {
								// this._trigger("onClickRow",[datas[j][this._rowIndex],datas[j]]);
								var $tr = this.tbody().find('tr[' + this._rowIndex + "=" + datas[j][this._rowIndex] + ']');
								
								$table.find("tr.editing").each(function(i,item) {
									that._endEdit($(item));
								});
								that.beginEdit($tr);
							}
						}
					}
				}
			}else if(selectData){
				var $tr = this.tbody().find('tr[' + this._rowIndex + "=" + selectData[this._rowIndex] + ']');
				
				$table.find("tr.editing").each(function(i,item) {
					that._endEdit($(item));
					// that._endEdit($table.find('tr[' + that._rowIndex + "=" + datas[j][that._rowIndex] + ']'));
				});
				that.beginEdit($tr);
			}
		},
		insertStaticRow : function(datas){
			var that = this;
			var datas_ = that.getData();
			$.merge(datas_,datas);
			// datas_.push(datas);
			that._loadDatas(datas_);
		},
		setEditDisabled : function(idVal, selector, selectData){
			var that = this;
			var datas = this.getData();
			// this.getSelections().removeClass(this.options.selectedCls);
			if (idVal) {
				for (var j = 0; j < datas.length; j++) {
					if (datas[j]['id'] == idVal) {
						var $tr = this.tbody().find('tr[' + this._rowIndex + "=" + datas[j][this._rowIndex] + ']');
						$tr.attr("disabledEdit","disabledEdit");
					}
				}
			} else if(selector){
				// this.tbody().find(selector).addClass(this.options.selectedCls);
				for (var j = 0; j < datas.length; j++) {
					if (this.tbody().find(selector).length > 0) {
						var $trs = this.tbody().find(selector);
						for (var i = 0; i < $trs.length; i++) {
							if ($trs.eq(i).attr(this._rowIndex) == datas[j][this._rowIndex]) {
								// this._trigger("onClickRow",[datas[j][this._rowIndex],datas[j]]);
								var $tr = this.tbody().find('tr[' + this._rowIndex + "=" + datas[j][this._rowIndex] + ']');
								$tr.addClass(this.options.selectedCls);
								

								$tr.attr("disabledEdit","disabledEdit");
							}
						}
					}
				}
			}else if(selectData){
				var $tr = this.tbody().find('tr[' + this._rowIndex + "=" + selectData[this._rowIndex] + ']');
				$tr.addClass(this.options.selectedCls);
				
				$tr.attr("disabledEdit","disabledEdit");
			}
		},
		setEditEnabled : function(idVal, selector, selectData){
			var that = this;
			var datas = this.getData();
			// this.getSelections().removeClass(this.options.selectedCls);
			if (idVal) {
				for (var j = 0; j < datas.length; j++) {
					if (datas[j]['id'] == idVal) {
						var $tr = this.tbody().find('tr[' + this._rowIndex + "=" + datas[j][this._rowIndex] + ']');
						$tr.removeAttr("disabledEdit");
					}
				}
			} else if(selector){
				// this.tbody().find(selector).addClass(this.options.selectedCls);
				for (var j = 0; j < datas.length; j++) {
					if (this.tbody().find(selector).length > 0) {
						var $trs = this.tbody().find(selector);
						for (var i = 0; i < $trs.length; i++) {
							if ($trs.eq(i).attr(this._rowIndex) == datas[j][this._rowIndex]) {
								// this._trigger("onClickRow",[datas[j][this._rowIndex],datas[j]]);
								var $tr = this.tbody().find('tr[' + this._rowIndex + "=" + datas[j][this._rowIndex] + ']');
								$tr.addClass(this.options.selectedCls);
								

								$tr.removeAttr("disabledEdit");
							}
						}
					}
				}
			}else if(selectData){
				var $tr = this.tbody().find('tr[' + this._rowIndex + "=" + selectData[this._rowIndex] + ']');
				$tr.addClass(this.options.selectedCls);
				
				$tr.removeAttr("disabledEdit");
			}
		},
		
		hasPointerEvents : function()
		    {
		        var el    = document.createElement('div'),
		            docEl = document.documentElement;
		        if (!('pointerEvents' in el.style)) {
		            return false;
		        }
		        el.style.pointerEvents = 'auto';
		        el.style.pointerEvents = 'x';
		        docEl.appendChild(el);
		        var supports = window.getComputedStyle && window.getComputedStyle(el, '').pointerEvents === 'auto';
		        docEl.removeChild(el);
		        return !!supports;
		  }(),
		  
		  serialize: function()
	        {
	            var data,
	                depth = 0,
	                list  = this;
	                step  = function(level, depth)
	                {
	                    var array = [ ],
	                        items = level.children(list.options.itemNodeName);
	                    items.each(function()
	                    {
	                        var li   = $(this),
	                            item = $.extend({}, li.data()),
	                            sub  = li.children(list.options.listNodeName);
	                        if (sub.length) {
	                            item.children = step(sub, depth + 1);
	                        }
	                        array.push(item);
	                    });
	                    return array;
	                };
	            data = step($(list.target).find(list.options.listNodeName).first(), depth);
	            return data;
	        },

	        serialise: function()
	        {
	            return this.serialize();
	        },

	        reset: function()
	        {
	            this.mouse = {
	                offsetX   : 0,
	                offsetY   : 0,
	                startX    : 0,
	                startY    : 0,
	                lastX     : 0,
	                lastY     : 0,
	                nowX      : 0,
	                nowY      : 0,
	                distX     : 0,
	                distY     : 0,
	                dirAx     : 0,
	                dirX      : 0,
	                dirY      : 0,
	                lastDirX  : 0,
	                lastDirY  : 0,
	                distAxX   : 0,
	                distAxY   : 0
	            };
	            this.isTouch    = false;
	            this.moving     = false;
	            this.dragEl     = null;
	            this.dragRootEl = null;
	            this.dragDepth  = 0;
	            this.hasNewRoot = false;
	            this.pointEl    = null;
	        },

	        expandItem: function(li)
	        {
	            li.removeClass(this.options.collapsedClass);
	            li.children('[data-action="expand"]').hide();
	            li.children('[data-action="collapse"]').show();
	            li.children(this.options.listNodeName).show();
	        },

	        collapseItem: function(li)
	        {
	            var lists = li.children(this.options.listNodeName);
	            if (lists.length) {
	                li.addClass(this.options.collapsedClass);
	                li.children('[data-action="collapse"]').hide();
	                li.children('[data-action="expand"]').show();
	                li.children(this.options.listNodeName).hide();
	            }
	        },

	        expandAll: function()
	        {
	            var list = this;
	            $(list.target).find(list.options.itemNodeName).each(function() {
	                list.expandItem($(this));
	            });
	        },

	        collapseAll: function()
	        {
	            var list = this;
	            $(list.target).find(list.options.itemNodeName).each(function() {
	                list.collapseItem($(this));
	            });
	        },

	        setParent: function(li)
	        {
	            /*if (li.children(this.options.listNodeName).length) {
	                li.prepend($(this.options.expandBtnHTML));
	                li.prepend($(this.options.collapseBtnHTML));
	            }*/
	        	li.children('[data-action="collapse"]').show();
	            li.children('[data-action="expand"]').hide();
	        },

	        unsetParent: function(li)
	        {
	            /*li.removeClass(this.options.collapsedClass);*/
	            /*li.children('[data-action]').remove();*/
	            /*li.children(this.options.listNodeName).remove();*/
	        },

	        dragStart: function(e)
	        {
	        	
	            var mouse    = this.mouse,
	                target   = $(e.target),
	                dragItem = target.closest(this.options.itemNodeName),
	                that = this;

	            this.placeEl.css('height', dragItem.height());

	            mouse.offsetX = e.offsetX !== undefined ? e.offsetX : e.pageX - target.offset().left;
	            mouse.offsetY = e.offsetY !== undefined ? e.offsetY : e.pageY - target.offset().top;
	            mouse.startX = mouse.lastX = e.pageX;
	            mouse.startY = mouse.lastY = e.pageY;

	            this.dragRootEl = this.target;

	            this.dragEl = $(document.createElement(this.options.listNodeName)).addClass(this.options.listClass + ' ' + this.options.dragClass);
	            this.dragEl.css('width', dragItem.width());

	            dragItem.after(this.placeEl);
	            dragItem[0].parentNode.removeChild(dragItem[0]);
	            dragItem.appendTo(this.dragEl);

	            $(document.body).append(this.dragEl);
	            this.dragEl.css({
	                'left' : e.pageX - mouse.offsetX,
	                'top'  : e.pageY - mouse.offsetY
	            });
	            // total depth of dragging item
	            var i, depth,
	                items = this.dragEl.find(this.options.itemNodeName);
	            for (i = 0; i < items.length; i++) {
	                depth = $(items[i]).parents(this.options.listNodeName).length;
	                if (depth > this.dragDepth) {
	                    this.dragDepth = depth;
	                }
	            }
	        },

	        dragStop: function(e)
	        {
	        	var that = this,
	        	    listno,
	        	    nextlistno,
	        	    preElement,
	        	    elementByTD,
	        	    nextElement;
	            var el = this.dragEl.children(this.options.itemNodeName).first(); //当前结点
	            var ele;
	            var parentEl = this.placeEl.parent();
                that.placeEl.replaceWith(el);      
            	that.dragEl.remove();
            	$(that.target).trigger('change');   	           
	            that.hasNewRoo ? that.dragRootEl.trigger('change') : null;   	            
	            that.reset();          
	            nextElement = el.next();	            
	            nextElement = nextElement.find("td");
	            if(nextElement[0] != undefined){
	            	$.each(nextElement,function(i,t){
	            		var arr = $(t).attr("field").split("_");
	            		if(arr[arr.length-1] == 'listno'){
	            			nextlistno = $(t).attr('title');
	            		}
	            	});
	            }
	            //拖拽到当前首行的时候，则
	            else{
	            	preElement = el.prev();	            
            		preElement = preElement.find("td");
                    if(preElement[0] != undefined){
                	$.each(preElement,function(i,t){
                		var arr = $(t).attr("field").split("_");
                		if(arr[arr.length-1] == 'listno'){
                			nextlistno = $(t).attr('title');
                			nextlistno = parseInt(nextlistno) + 1;
                		}
                	});
                    }
	            }
            	
            	elementByTD = el.find("td");
            	$.each(elementByTD,function(i,t){
            		var arr = $(t).attr("field").split("_");
            		if(arr[arr.length-1] == 'listno'){
            			listno = $(t).attr('title');
            		}
            	})
            	if(parseInt(nextlistno) < parseInt(listno)){  
            		preElement = el.prev();	            
            		preElement = preElement.find("td");
                    if(preElement[0] != undefined){
                	$.each(preElement,function(i,t){
                		var arr = $(t).attr("field").split("_");
                		if(arr[arr.length-1] == 'listno'){
                			nextlistno = $(t).attr('title');
                		}
                	});
                    }
                    else{
                    	nextElement = el.next();	            
        	            nextElement = nextElement.find("td");
                    	$.each(nextElement,function(i,t){
                    		var arr = $(t).attr("field").split("_");
                    		if(arr[arr.length-1] == 'listno'){
                    			nextlistno = $(t).attr('title');
                    			nextlistno = parseInt(nextlistno) - 1; 
                    		}
                    	});
                    }
            	}
            	that.wardBydrag({
            		paramType : that.paramType,
            		params : JSON.stringify({
            			 'nextlistno' : ""+nextlistno,
            			 'listno' : ""+listno
            		}),
					models : [that.getRow(el)]
				});
	        },
	       
	        dragMove: function(e)
	        {
	            var list, parent, prev, next, depth,
	                opt   = this.options,
	                mouse = this.mouse;

	            this.dragEl.css({
	                'left' : e.pageX - mouse.offsetX,
	                'top'  : e.pageY - mouse.offsetY
	            });

	            // mouse position last events
	            mouse.lastX = mouse.nowX;
	            mouse.lastY = mouse.nowY;
	            // mouse position this events
	            mouse.nowX  = e.pageX;
	            mouse.nowY  = e.pageY;
	            // distance mouse moved between events
	            mouse.distX = mouse.nowX - mouse.lastX;
	            mouse.distY = mouse.nowY - mouse.lastY;
	            // direction mouse was moving
	            mouse.lastDirX = mouse.dirX;
	            mouse.lastDirY = mouse.dirY;
	            // direction mouse is now moving (on both axis)
	            mouse.dirX = mouse.distX === 0 ? 0 : mouse.distX > 0 ? 1 : -1;
	            mouse.dirY = mouse.distY === 0 ? 0 : mouse.distY > 0 ? 1 : -1;
	            // axis mouse is now moving on
	            var newAx   = Math.abs(mouse.distX) > Math.abs(mouse.distY) ? 1 : 0;

	            // do nothing on first move
	            if (!mouse.moving) {
	                mouse.dirAx  = newAx;
	                mouse.moving = true;
	                return;
	            }

	            // calc distance moved on this axis (and direction)
	            if (mouse.dirAx !== newAx) {
	                mouse.distAxX = 0;
	                mouse.distAxY = 0;
	            } else {
	                mouse.distAxX += Math.abs(mouse.distX);
	                if (mouse.dirX !== 0 && mouse.dirX !== mouse.lastDirX) {
	                    mouse.distAxX = 0;
	                }
	                mouse.distAxY += Math.abs(mouse.distY);
	                if (mouse.dirY !== 0 && mouse.dirY !== mouse.lastDirY) {
	                    mouse.distAxY = 0;
	                }
	            }
	            mouse.dirAx = newAx;

	            /**
	             * move horizontal
	             */
	            if (mouse.dirAx && mouse.distAxX >= opt.threshold) {
	                // reset move distance on x-axis for new phase
	                mouse.distAxX = 0;
	                prev = this.placeEl.prev(opt.itemNodeName);
	                // increase horizontal level if previous sibling exists and is not collapsed
	                if (mouse.distX > 0 && prev.length && !prev.hasClass(opt.collapsedClass)) {
	                    // cannot increase level when item above is collapsed
	                    list = prev.find(opt.listNodeName).last();
	                    // check if depth limit has reached
	                    depth = this.placeEl.parents(opt.listNodeName).length;
	                    if (depth + this.dragDepth <= opt.maxDepth) {
	                        // create new sub-level if one doesn't exist
	                        if (!list.length) {
	                            list = $('<' + opt.listNodeName + '/>').addClass(opt.listClass);
	                            list.append(this.placeEl);
	                            prev.append(list);
	                            this.setParent(prev);
	                        } else {
	                            // else append to next level up
	                            list = prev.children(opt.listNodeName).last();
	                            list.append(this.placeEl);
	                        }
	                    }
	                }
	                // decrease horizontal level
	                if (mouse.distX < 0) {
	                    // we can't decrease a level if an item preceeds the current one
	                    next = this.placeEl.next(opt.itemNodeName);
	                    if (!next.length) {
	                        parent = this.placeEl.parent();
	                        this.placeEl.closest(opt.itemNodeName).after(this.placeEl);
	                        if (!parent.children().length) {
	                            this.unsetParent(parent.parent());
	                        }
	                    }
	                }
	            }

	            var isEmpty = false;

	            // find list item under cursor
	            if (!hasPointerEvents) {
	                this.dragEl[0].style.visibility = 'hidden';
	            }
	            this.pointEl = $(document.elementFromPoint(e.pageX - document.body.scrollLeft, e.pageY - (window.pageYOffset || document.documentElement.scrollTop)));
	            if (!hasPointerEvents) {
	                this.dragEl[0].style.visibility = 'visible';
	            }
	            if (this.pointEl.hasClass(opt.handleClass)) {
	                this.pointEl = this.pointEl.parent(opt.itemNodeName);
	            }
	            if (this.pointEl.hasClass(opt.emptyClass)) {
	                isEmpty = true;
	            }
	            else if (!this.pointEl.length || !this.pointEl.parent().hasClass(opt.itemClass)) {
	                return;
	            }

	            // find parent list of item under cursor
	            var pointElRoot = $(this.pointEl).parent().closest('.' + opt.rootClass),
	                isNewRoot   = $(this.dragRootEl).data('grid-id') !== pointElRoot.data('grid-id');

	            /**
	             * move vertical
	             */
	            if (!mouse.dirAx || isNewRoot || isEmpty) {
	                // check if groups match if dragging over new root
	                if (isNewRoot && opt.group !== pointElRoot.data('grid-group')) {
	                    return;
	                }
	                // check depth limit
	                depth = this.dragDepth - 1 + this.pointEl.parents(opt.listNodeName).length;
	                if (depth > opt.maxDepth) {
	                    return;
	                }
	                var before = e.pageY < ($(this.pointEl).parent().offset().top + $(this.pointEl).parent().height() / 2);
	                    parent = $(this.pointEl).parent().parent();
	                
	                if (isEmpty) {
	                    list = $(document.createElement(opt.listNodeName)).addClass(opt.listClass);
	                    list.append(this.placeEl);
	                    $(this.pointEl).parent().replaceWith(list);
	                }
	                else if (before) {
	                	$(this.pointEl).parent().before(this.placeEl);
	                	this.paramType = false;
	                	
	                }
	                else {
	                	$(this.pointEl).parent().after(this.placeEl);
	                	this.paramType = true;
	                	
	                }
	                if (!parent.children().length) {
	                    this.unsetParent(parent.parent());
	                }
	                if (!$(this.dragRootEl).parent().find(opt.itemNodeName).length) {
	                	      $(this.dragRootEl).parent().empty();
	                		  $(this.dragRootEl).parent().append('<tr class="' + opt.emptyClass + '"/>');  
	                  }
	                // parent root list has changed
	                if (isNewRoot) {
	                	var drag = $(this.dragRootEl).parent();
	                    drag[0] = pointElRoot;
	                    this.hasNewRoot = this.target[0] !== drag[0];
	                }
	            }
	        },
	        getRadioVal : function($target){
	        	var __dataArray = this.options.__dataArray;
	        	var __curArraytd = $target.closest("td");
	        	var __curArraytr = $target.closest("tr");
	        	
	        	$.each(__dataArray,function(i,dataArray){
	        		if(i == __curArraytr.rowIndex){
	        		$.each(dataArray,function(j,_array){
	        			if(j == $(__curArraytd).attr("field")){
	        				(__dataArray[i])[j] = $target.value;
	        			}
	        		});
	        		}
	        	});
	        }
			
	};

	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor: Build
	});

})(jQuery);

window.rate = function(jq) {
	var height = $(window).height();
	var width = $(window).width();
	var x_height = Math.floor(height * 0.68);
	var x_width = Math.floor(width * 0.82);
	x_height = 0.9;

	if (height <= 830) {
		//x_height = Math.floor(height * 0.60);
		x_height = 0.60;
	}
	if (width < 1200) {
		//x_width = Math.floor(width * 0.82);
		x_width = 0.82;
	} else if (width > 1280) {
		//x_width = Math.floor(width * 0.72);
		x_width = 0.72;
	}
	return {
		hr: x_height,
		wr: x_width
	};
};

window.uuid = (function() {
	var time = new Date().getTime();
	var func = function() {
		return time++;
	};
	return func;
})();

function Sequence(region, start) {
	this.region = region;
	this.start = start || 0;
	this.current = this.start;
	this.nextVal = function() {
		return this.current++;
	};
	this.nextRegVal = function() {
		return this.region + (this.current++);
	};
	this.reset = function() {
		this.current = this.start;
	};
}