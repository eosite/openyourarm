(function($) {
	var plugin = 'treelist',
		Build = function(target, state) {
			this.target = target;
			this.state = state;
			this.options = state.options;
			this.plugin = 'treelist';
			this._init();
		};
	$.parser.plugins.push(plugin);
	$.fnInit(plugin, function(target) {

		var state = $.data(target, plugin + '.options');

		var columns = $(target).data("columns");
		if (columns && typeof columns == 'string') {
			state.options.columns = eval('(' + columns + ')');
		}
		var p = new Build(target, state);

		$.data(target, plugin, p);
	});

	$.fn[plugin].defaults = $.extend(true, {}, $.fn.grid.defaults, {
		expandChilds: "",
		keys: {
			idKey: 'id',
			parentKey: 'parentId',
			childrenKey: '_items_'
		},
		selectedCls: 'treelist-selected',
		loadingCls: 'treelist-expander-loading',
		events: {
			onExpand: function(row, read) {}
		}
	});


	$.fn[plugin].methods = $.extend(true, {}, $.fn.grid.methods, {

		addToolBar: function() {
			var that = this,
				$target = $(that.target);
			if (this.options.toolbar) {
				var toolbar = eval(this.options.toolbar);
				var toolbarTemplate = new StringBuffer('<div class="grid-toolbar" id="' + $target.attr("id") + '_t' + '">');
				$.each(toolbar, function(i, o) {
					if (o.name == "addBrother") {
						toolbarTemplate.appendFormat(that._handleTemplate, 'red', o.name, 'fa-plus', o.text);
					}
					if (o.name == "addChildren") {
						toolbarTemplate.appendFormat(that._handleTemplate, 'blue', o.name, 'fa-plus', o.text);
					} else if (o.name == "save") {
						toolbarTemplate.appendFormat(that._handleTemplate, 'green', o.name, 'fa-check', o.text);
					} else if (o.name == "cancel") {
						toolbarTemplate.appendFormat(that._handleTemplate, 'default', o.name, 'fa-ban', o.text);
					}
				});
				toolbarTemplate.append('</div>');
				$target.prepend(toolbarTemplate.toString());
			}
		},
		_initEvents: function() {
			var that = this,
				$table = that.table();

			that._initClientEvents();

			$("#" + $(this.target).attr("id") + '_t').on("click", "a.btn", function() { //新增
				if ($(this).attr("atype") == "addBrother") {
					that._newRow();
				}
				if ($(this).attr("atype") == "addChildren") {
					that._newChildrenRow();
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

			$table.on("click.span.treelist-expander", 'span.treelist-expander', function(e) {
				var parentId = that.getRow(this).id;
				if(that.requestQueue){
					for(var i = 0 ; i < that.requestQueue.length; i ++){
						if(parentId == that.requestQueue[i]){
							return;	//正在查询中，不可展开
						}
					}
				}
				// if(that.requestQueue && that.requestQueue.length > 0 ){
				// 	return;
				// }
				var $this = $(this);
				that._toggle($this);
				!(that.options.isSync==false) && that._requestGetChildren($this); //需要异步加载节点
				return false;
			});

			//绑定options事件
			$.each(that.options.events, function(event, fn) {
				$table.bind(event, function(e) {
					return fn.apply(that, Array.prototype.slice.call(arguments, 1));
				});
			});

			var key = '_cls',
				cls, types = {
					expand: 'expanded',
					collapse: 'collapsed'
				};
			$.each(types, function(t, v) {
				$table.on(t, 'tbody>tr', function(e) {
					var $this = $(this),
						$span = $this.find("span.treelist-expander");
					cls = 'treelist-' + v;
					$this.removeClass($this.data(key)).addClass(cls).data(key, cls);
					if ($span[0] && !$span.data(that._isAjaxLeaf)) {
						if (!$span.data(key)) {
							$.each(types, function(i, o) {
								$span.removeClass('treelist-expander-' + o);
							});
						} else {
							$span.removeClass($span.data(key));
						}
						cls = 'treelist-expander-' + v;
						$span.addClass(cls).data(key, cls);
					}
				});
			});
		},
		_newRow: function() {
			var that = this,
				i = 0,
				opts = that.options;
			var c = new StringBuffer();

			var d = that._attr({});
			if(opts.datas){
				//静态数据时，自动生成id
				d[opts.ajax.schema.model.id] = d[this._rowIndex]+1;
			}
			that._append(d, c);
			that.tbody().prepend(c.toString());
			$tr = that.tbody().find('tr[' + this._rowIndex + "=" + d[this._rowIndex] + ']');
			$tr.addClass('new');
			// $btn = $tr.find('a[atype="edit"]');

			var selectRow = that.getSelectedRows()[0];
			var row = that.getRow($tr);
			
			if (selectRow && selectRow[opts.ajax.schema.model.id]) {
				row[opts.keys.parentKey] = selectRow[opts.keys.parentKey] || "";
			}
			that._toUpdate($tr);
		},
		_newChildrenRow: function() {
			var that = this,
				i = 0,
				opts = that.options;
			var c = new StringBuffer();

			var selectRow = that.getSelectedRows()[0];

			var d = that._attr({});
			if(opts.datas){
				//静态数据时，自动生成id
				d[opts.ajax.schema.model.id] = d[this._rowIndex]+1;
				if (selectRow && selectRow[opts.ajax.schema.model.id]) {
					d[opts.keys.parentKey] = selectRow[opts.ajax.schema.model.id] || "";
				}
			}
			that._append(d, c);
			that.tbody().prepend(c.toString());
			$tr = that.tbody().find('tr[' + this._rowIndex + "=" + d[this._rowIndex] + ']');
			$tr.addClass('new');
			// $btn = $tr.find('a[atype="edit"]');
			
			var row = that.getRow($tr);
			// if(selectRow&&selectRow[opts.ajax.schema.model.id]){
			// 	opts.dataSource.options.onRequest = function(data){
			// 		data.parentId = selectRow[opts.ajax.schema.model.id];
			// 	};
			if (selectRow && selectRow[opts.ajax.schema.model.id]) {
				row[opts.keys.parentKey] = selectRow[opts.ajax.schema.model.id] || "";
			} else {
				alert("请先选择一个父节点！");
				that.tbody().find("tr").eq(0).remove();
			}
			that._toUpdate($tr);
			
		},
		_trigger: function(event, params) {
			return this.table().trigger(event, params);
		},
		_isAjaxLeaf: 'isAjaxLeaf', //叶子节点(有可能是请求超时)
		_isRequested: "isRequested",
		_requestGetChildren: function($span) {
			var that = this,
				$tr = that.getDomRow($span),
				opts = that.options,
				params,
				loadingCls = opts.loadingCls;
			if (!$tr.data(that._isRequested) && !opts.datas) {
				var row = that.getRow($span);
				if (row) {

					var r = $.extend(true, {}, opts.ajax.read);

					if ($.isFunction(opts.events.onExpand)) {
						that._trigger("onExpand", [row, r]);
						// r = opts.events.onExpand(row,r);
					}

					$.extend(opts.ajax.read, r, {
						beforeSend: function() {
							$span.addClass(loadingCls);
						},
						complete: function(ret) {
							var span = $tr.find('span.' + loadingCls).removeClass(loadingCls);
							if (!that.getChildNodes(that.getNodeId($tr)).length) {
								span.data(that._isAjaxLeaf, true).removeClass('treelist-expander-expanded');
							}
						}
					});
					params = {
						id: row[opts.keys.idKey],
						parentId: row[opts.keys.parentKey]
					};
					// params[opts.keys.idKey] = params.id;
					// params[opts.keys.parentKey] = params.parentId;

					that.query(params, $tr);

				}
				$tr.data(that._isRequested, true);
			}
		},
		iteratorQueryChildren: function(data) {
			var opts = this.options;
			if (opts.expandChilds && $.isNumeric(opts.expandChilds)) { //如果联动操作传递了展开层级参数
				for (var i = 0; i < data.length; i++) {
					if ((data[i]._level_ + 1) >= opts.expandChilds) {} else {
						var $tr = this.tbody().find('tr[' + this._rowIndex + "=" + data[i][this._rowIndex] + ']');
						// 注意：只能展开关闭状态下的节点 否则会导致问题
						$span = $tr.find("span.treelist-expander-collapsed");
						$span.click();
					}
				}
			} else { //如果没有传递展开层级参数，默认展开20个节点左右
				if (this.tbody().find("tr").length < 20) {
					for (var i = 0; i < data.length; i++) {
						var $tr = this.tbody().find('tr[' + this._rowIndex + "=" + data[i][this._rowIndex] + ']');
						// 注意：只能展开关闭状态下的节点 否则会导致问题
						$span = $tr.find("span.treelist-expander-collapsed");
						$span.click();
					}
				}
			}
		},
		/**
		 * 记录参数
		 */
		getParams: function(params) {
			var data = this.options.ajax.read.d_data__; //取动态参数
			var ret = $.extend({}, data || this.options.ajax.read.data || {});
			if (params && data) { //如果不是第一次查询
				var keys = {
						id: '',
						parentId: ''
					},
					opts = this.options;
				keys[opts.keys.idKey] = '';
				keys[opts.keys.parentKey] = '';

				if (!data.params) {
					data.params = {};
				} else {
					data.params = JSON.parse(data.params);
				}

				for (var key in params) {
					if (!(key in keys)) {
						data.params[key] = params[key]; //保存动态参数
					} else {
						ret[key] = params[key]; //装载变化参数
					}
				}

				data.params = JSON.stringify(data.params);
				ret.params = data.params; //更新要保留参数
			}
			return ret;
		},
		_toggle: function(e) {
			var isCollapsed = this.isCollapsed(e);
			isCollapsed ? this.expand(e) : this.collapse(e);
			//	this.resize();
			return isCollapsed;
		},
		_children: '_items_',
		_sortDatas: function(datas, n) {
			var that = this,
				keys = that.options.keys,
				children = keys.childrenKey || that._children;
			var _currentDatas = new Array();
			
			/**
			 * 客户端排序
			 */
			that.SortDatas && that.SortDatas(datas, n);

			function rebuild(data, level) {
				$.each(data, function(i, v) {
					v._level_ = level;
					_currentDatas.push(v);
					if (v[children]) {
						rebuild(v[children], (level + 1));
					}
				});
			}
			rebuild($.transformToTreeFormat(datas, keys.idKey, keys.parentKey, children), n);
			return _currentDatas;
		},
		_buildClass: function(n, c) {
			var that = this,
				keys = that.options.keys,
				parentId = n[keys.parentKey];
			c.appendFormat(" class='treelist-{0}", n[keys.idKey]);
			if (parentId != null && parentId != undefined) {
				c.appendFormat(" treelist-parent-{0}", parentId);
			}
			c.appendFormat("' row-index='{0}'", n['row-index']);
		},
		_buildCell: function(n, v, c, index, isExpanded) {
			var that = this,
				i = 0,
				span = '',
				style = (that.options.showInLine?"white-space: nowrap;overflow: hidden; text-overflow:ellipsis;word-wrap:keep-all;":"word-wrap:break-word;");
			if (index == 0) {
				span = new StringBuffer();
				for (; i < (n._level_ || 0); i++) {
					span.append('<span class="treelist-indent"></span>');
				}
				if (that._statistics.lineDone) {

				} else {
					if(isExpanded != null){
						span.appendFormat('<span class="treelist-expander treelist-expander-{0}"></span>', isExpanded ? "expanded" : "collapsed");
					}else{
						if(!(that.options.isSync==false)){
							span.appendFormat('<span class="treelist-expander treelist-expander-{0}"></span>', n[that._children] ? "expanded" : "collapsed");
						}else{
							span.appendFormat('<span class="{0}"></span>', n[that._children] ? "treelist-expander  treelist-expander-expanded" : "treelist-indent");
						}
					}
				}
				span = span.toString('');
				//span = '';
			}
			c.appendFormat("<td style='{3}' field='{0}' hid='{2}' >{1}</td>",
				v.field || '',
				span + (this._getTemplate(n, v) || this._componentHandle(n, v) || ''),
				v.uuid,
				style + ((v.attributes && v.attributes.style) ? v.attributes.style : ""),
				n);
		},
		getNodeId: function(e) {
			var template = /treelist-([A-Za-z0-9_-]+)/;
			if (template.test($(e).attr('class'))) {
				return template.exec($(e).attr('class'))[1];
			}
			return null;
		},
		getParentNodeId: function(e) {
			var template = /treelist-parent-([A-Za-z0-9_-]+)/;
			if (template.test($(e).attr('class'))) {
				return template.exec($(e).attr('class'))[1];
			}
			return null;
		},
		getNodeById: function(id, treegridContainer) {
			var templateClass = "treelist-" + id,
				treegridContainer = treegridContainer || this.table();
			return treegridContainer.find('tr.' + templateClass);
		},
		getChildNodes: function(id, treegridContainer) {
			var templateClass = "treelist-parent-" + id,
				treegridContainer = treegridContainer || this.table();
			return treegridContainer.find('tr.' + templateClass);
		},
		isCollapsed: function(target) {
			var $this = this.getDomRow(target);
			return $this.hasClass('treelist-collapsed') || ($this.find("span.treelist-expander").hasClass('treelist-expander-collapsed'));
		},
		isExpanded: function(target) {
			var $this = this.getDomRow(target);
			return $this.hasClass('treelist-expanded') || ($this.find("span.treelist-expander").hasClass('treelist-expander-expanded'));
		},
		expand: function(target) {
			var that = this,
				$this = this.getDomRow(target);
			that.getChildNodes(that.getNodeId($this)).each(function(i, v) {
				!!that.isExpanded($(v).toggle()) && that.expand(v);
			});
			$this.trigger('expand');
		},
		collapse: function(target, expand) {
			var that = this,
				$this = this.getDomRow(target);
			that.getChildNodes(that.getNodeId($this)).each(function(i, v) {
				!!that.isExpanded($(v).toggle()) && that.collapse(v, true);
			});
			!expand && $this.trigger('collapse');
		},
		expandByNodeId: function(nodeId){
			var $this = $(this);
			that._toggle($this);
			that._requestGetChildren($this); //需要异步加载节点
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
				//寻找子节点并勾选
				if($(this).hasClass("header_checkbox")){
					//全选/全不选
					if($(this).prop("checked")){
						$tbale.find(".inner_checkbox").prop("checked",true).trigger("change");
					}else{
						$tbale.find(".inner_checkbox").prop("checked",false).trigger("change");
					}
				}else{
					//选中/取消选中下级节点
					var rowData = that.getRow(this);
					if($(this).prop("checked")){
						$tbale.find(".treelist-parent-"+rowData.id+" .inner_checkbox").prop("checked",true).trigger("change");
					}else{
						$tbale.find(".treelist-parent-"+rowData.id+" .inner_checkbox").prop("checked",false).trigger("change");
					}

					var onCheckRowFunc = $(that.target).data("__onCheckRow__") || that.options.events.onCheckRow;
					//触发选中事件
					if($.isFunction(onCheckRowFunc)){
						onCheckRowFunc();
						// that.options.events.onCheckRow(rowData[that._rowIndex], rowData);
					}
				}
			})
		},
		_getChildrenHtmlNodeByParent : function(data){
			var that = this;
			var $tbale = that.table();
			return $tbale.find(".treelist-parent-"+data.id);
		},
		
		/**
		 * 展开并刷新子节点
		 * @param  {[type]} data [description]
		 * @return {[type]}      [description]
		 */
		refreshAndExpandNode : function(data){
			var that = this;
			data._isRequested = false;
			var $item = $(that.target).find("tr[row-index='"+data[that._rowIndex]+"']");
			that.collapse($item);
			$item.data(that._isRequested,false);

			that.deleteDataByParent(data);
			$item.find(".treelist-expander").click();
		},
		
		//刷新当前节点
		nodeRefresh :function(data,rename,text){
			var that = this;
			var $item = $(that.target).find("tr[row-index='"+data[that._rowIndex]+"']");
			
		var dataItems =	$item.find('td')[0]
		var node =dataItems.childNodes;
		
		for(i = 0;i< node.length;i++){
			var nodetext = dataItems.childNodes[i];
			if(nodetext.nodeName == '#text'){
				nodetext.textContent = rename;
			}
		}
			
		},
		
		
		deleteDataByParent : function(data,removeAry){
			var that = this;
			var $childrenNodes = that._getChildrenHtmlNodeByParent(data);
			var __dataArray = that._getDataArray(),
				canRemove = !removeAry;
			if(!removeAry){
				removeAry = [];
			} 
			$.each($childrenNodes,function(i,item){
				// var row = that.getRow(item);
				var rowIndex = $(item).attr(that._rowIndex);
				for (var i = __dataArray.length - 1; i >= 0; i--) {
					var valData = __dataArray[i];
					if(valData[that._rowIndex] == rowIndex){
						that.deleteDataByParent(valData,removeAry);
					 	removeAry.push(i);
					}
				}
				
			})
			if(canRemove && removeAry.length){
				//删除 __dataArray
				var max =Math.max.apply(null, removeAry);
				__dataArray.splice(max,removeAry.length);
			}
			//删除 nodes
			$childrenNodes.remove();
		},
		
		
		/**
		 * 将输入插入到选中节点下
		 * @param  {[type]} datas []
		 * @param  {[type]} rows  []
		 * @return {[type]}       []
		 */
		insertStaticRow : function(datas){
			var that = this;
			var $selected_trs = that.getSelections();
			var pre_id = "static";
			var data_array = that._getDataArray();
			var max_id = data_array.length;
			var opts = that.options;
			var idKey = opts.keys.idKey;
			var parentKey = opts.keys.parentKey;
			if($selected_trs && $selected_trs.length > 0){
				$.each($selected_trs,function(i,tr){
					var $tr = $(tr);
					//先展开当前节点信息
					$tr.find(".treelist-expander.treelist-expander-collapsed").click();

					var row = that.getRow(tr);//获取当前节点的数据
					//插入到根节点
					$.each(datas,function(i,item){
						item[idKey] = pre_id + max_id;
						max_id ++;
						item[parentKey] = row[idKey];
					})

					that._loadDatas(datas,$(tr));	
				})
			}else{
				
				//插入到根节点
				$.each(datas,function(i,item){
					item[idKey] = pre_id + max_id;
					max_id ++;
					if(item[parentKey]){
						delete item[parentKey];
					}
				})
				$.merge(data_array,datas);
				// datas_.push(datas);
				that._loadDatas(data_array);
			}
		}
	});

	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor: Build
	});

})(jQuery);