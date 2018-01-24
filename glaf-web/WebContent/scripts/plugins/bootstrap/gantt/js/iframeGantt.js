(function($) {
	var plugin = 'iframegantt';

	var _init = function(target, params) {
		clear(target);
		var opts = getOptions(target);
		opts.frameId = $(target).attr('id') + '_iframe'
		if (opts.asDemo) {
			IframeGantt.init($(target));
			return;
		}

		opts.dataSource = new mt.DataSource(opts.ajax);

		var ar = opts.ajax.read;
		ar.success = function(ret) {
			if (ret && ret instanceof Object) {
				/*ret.data = IframeGantt.initEventObject(ret.data, opts.columns);
				var treeDatas = $.transformToTreeFormat(ret.data, opts.keys.idKey, opts.keys.parentKey, "children");
				var retDatas = [];

				function ganttIterator(retDatas, treeDatas, level) {
					$.each(treeDatas, function(i, d) {
						d.level = level;
						retDatas.push(d);
						if (d.children) {
							ganttIterator(retDatas, d.children, level + 1);
						}
					})
				}
				ganttIterator(retDatas, treeDatas, 1);
				ret.data = retDatas;*/
				opts.datas = IframeGantt.initDatas(ret, opts, 0);
				IframeGantt.init($(target), ar, opts);
			}
		}
		ar.complete = function(XHR, TS) {
			ar.data = $.parseJSON(ar.data);
		}
		if (params) {
			$.extend(true, ar.data, {
				params: JSON.stringify(params)
			});
		}
		ar.data = JSON.stringify(ar.data);
		$.ajax(ar);
	}
	var _staticinit = function(target, params) {
		clear(target);
		var opts = getOptions(target);
		opts.frameId = $(target).attr('id') + '_iframe'
		if (opts.asDemo) {
			IframeGantt.init($(target));
			return;
		}
		opts.data = opts.datas;
        IframeGantt.init($(target), null, opts);
		
	}
	function getOptions(target) {
		return $(target).data(plugin) ? $(target).data(plugin).options : {};
	}

	function close(target) {

	}

	function open(target) {

	}

	function destroy(target) {

	}

	function restore(target) {

	}

	function clear(target) {
		$(target).empty();
	}

	function getParameter(target, param) {
		var opts = getOptions(target);
		return opts[param];
	}

	function initEvents(target, events) {
		var opts = getOptions(target);
		$.extend(true, opts, events);
	}

	//初始化
	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string') {
			return $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i, o) {
			var state = $.data(o, plugin);
			if (state) {
				state.tabs = [];
				$.extend(true, state.options, options);
			} else {
				$.data(o, plugin, {
					options: $.extend(true, {}, $.fn[plugin].defaults, options),
					target: o,
					columns: [],
					datas: param,
				});
			}
			options.datas !=null && options.datas != undefined ? _staticinit(o,null) : _init(o);

		});
	};

	//外部调用方法
	$.fn[plugin].methods = {
		close: function(jq) {
			close(jq[0]);
		},
		open: function(jq) {
			open(jq[0]);
		},
		destroy: function(jq) {
			destroy(jq[0]);
		},
		restore: function(jq) {
			restore(jq[0]);
		},
		getParameter: function(jq, param) {
			return getParameter(jq[0], param);
		},
		initEvents: function(jq, events) {
			return initEvents(jq[0], events);
		},
		save: function(jq, param) {
			IframeGantt.saveGantt(jq[0], param);
		},
		load: function(jq, params) {
			_init(jq, params);
		}
	};

	$.fn[plugin].defaults = {
		asDemo: false,
		datas: null,
		mapping: [],
		columns: [],
		ajax: {
			read: {
				url: '',
				type: 'POST',
				dataType: 'JSONp',
				data: {},
				contentType: "application/json",
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
			parameterMap: function(params) {
				return params;
			},
			schema: {
				data: 'data',
				total: 'total'
			}
		},
		events: {
			onClickRow: function(index, row) {},
			onDblClickRow: function(index, row) {},
			onClickCell: function(index, field, value) {},
			onDblClickCell: function(index, field, value) {},
			onLoadSuccess: function(data) {},
			onLoadData: function(data) {}
		},
		_clientEvents: {
			Row: {
				target: "tr[__template='TASKROW']",
				fn: function(e, fn, iframeId) {
					var task = IframeGantt.getCurrentTask(iframeId);
					if (task) {
						var index = task.getRow();
						return fn(index, task);
					}
				}
			},
			Cell: {
				target: "tr[__template='TASKROW']>td",
				fn: function(e, fn, iframeId) {
					var task = IframeGantt.getCurrentTask(iframeId);
					if ($(e.currentTarget).find('button')[0]) {
						return;
					}
					/*var row = that.getRow(e.target), field;
					return fn(row[that._rowIndex], (field = $(e.target)
							.closest("td").attr("field")), row[field]);*/
				}
			}
		}
	};
})(jQuery);

var IframeGantt = {
	initDatas: function(ret, opts, startLevel) {
		ret.data = IframeGantt.initEventObject(ret.data, opts.columns);
		var treeDatas = $.transformToTreeFormat(ret.data, opts.keys.idKey, opts.keys.parentKey, "children");
		var retDatas = [];

		function ganttIterator(retDatas, treeDatas, level) {
			$.each(treeDatas, function(i, d) {
				d.level = level;
				d['index'] = d[opts.keys.idKey];
				d['parent'] = d[opts.keys.parentKey];
				retDatas.push(d);
				if (d.children) {
					ganttIterator(retDatas, d.children, level + 1);
				}
			})
		}
		ganttIterator(retDatas, treeDatas, startLevel + 1);
		ret.data = retDatas;
		ret.cols = opts.colsTemplate;
		return ret;
	},
	convertDatas: function(ret, opts) {
		ret.cols = opts.colsTemplate;
		ret.canwrite = opts.canWrite;
		var tasks = ArtTemplateDataUtils.convertOne(ret, opts.template);
		return eval("(" + tasks + ")");
	},
	init: function($target, read, opts) {
		var $iframe = $('<iframe>');
		var frameId = IframeGantt.getFrameId($target);
		var params = {};
		if (read && read.data) {
			params = JSON.parse(read.data);
		}
		$iframe.attr({
			'id': frameId,
			'name': frameId,
			'width': '100%',
			'frameborder': 'no',
			'scrolling': 'no',
			'src': contextPath + "/mx/form/gantt/view?frameId=" + frameId + "&rid=" + (params.rid || '') + "&dataRole=" + $target.attr('data-role') + "&showSysMenu=" + (opts ? (opts.showSysMenu || "false") : "false")
		});
		$target.append($iframe);
	},
	saveGantt: function(target, param) {
		var frameId = IframeGantt.getFrameId($(target));
		window[frameId].saveGanttOnServer();
	},
	getFrameId: function($target) {
		return $target.attr('id') + '_iframe';
	},
	deleteTasks: function(dataSource, models, fn) {
		confirm("你确定删除吗?", function(ok) {
			if (ok) {
				dataSource.destroy(models, fn).async();
			}
		});
	},
	getDataSource: function(id) {
		return $('#' + id).iframegantt('getParameter', 'dataSource');
	},
	updateData: function(id, tasks) {
		var dataSource = IframeGantt.getDataSource(id);
		tasks = JSON.stringify(tasks);
		tasks = JSON.parse(tasks);
		dataSource.save(tasks).async(function(ret) {
			if (ret.message) {
				alert(ret.message);
			}
		});
	},

	deleteCurrentTask: function(id, task) {
		var list = [];
		list.push(task);
		IframeGantt.addDeletes(IframeGantt.getDataSource(id), task);
	},
	addDeletes: function(dataSource, task) {
		var idField = task.tableName + '_0_id';
		var obj = {};
		obj[idField] = task.id;
		dataSource.destroy(obj);
	},
	deleteAll: function(id, tasks) {
		var dataSource = IframeGantt.getDataSource(id);
		$.each(tasks, function(i, task) {
			IframeGantt.addDeletes(dataSource, task);
		});
	},
	resize: function(id, width, height, padding) {
		var $target = $('#' + id);
		if (width) {
			$target.attr('width', width);
		}
		if (height) {
			$target.attr('height', height);
		}
		$target.parent().css('padding', padding ? padding : '0px');
	},
	getColumns: function(id) {
		var datas = IframeGantt.parameters(id, 'datas');
		var columns = IframeGantt.parameters(id, 'columns');
		//var columns = IframeGantt.parameters(id, 'mapping');
		return columns;
		if (datas && columns[0]) {
			return IframeGantt.combineColumns(datas.cols, columns);
		}
	},
	combineColumns: function(cols, columns) {
		var list = [];
		var map = IframeGantt.colsMap(cols);
		$.each(columns, function(i, c) {
			var col = map[c.id];
			if (col) {
				$.extend(true, c, col);
				list.push(c);
			}
		});
		return list;
	},
	colsMap: function(cols) {
		var map = {};
		$.each(cols, function(i, c) {
			map[c.protoCode] = c;
		});
		return map;
	},
	parameters: function(id, param) {
		var $target = $('#' + id);
		return $target.iframegantt('getParameter', param);
	},
	initEventObject: function(datas, columns) {
		var list = [];
		for (var i = 0; i < datas.length; i++) {
			var data = datas[i];
			for (var j = 0; j < columns.length; j++) {
				var template = columns[j].template;
				if ($.isFunction(template)) {
					var eobj = IframeGantt.convertEventObject(template(data));
					$.extend(true, eobj, {
						'field-data': data[columns[j].field]
					});
					data[columns[j].field + '_eventObject'] = JSON.stringify(eobj);
				}
			}
			list.push(data);
		}
		//$.transformToTreeFormat();

		return list;
	},
	convertEventObject: function(template) {
		var $target = $(template);
		if ($target[0]) {
			var alinks = $target.closest('a');
			if (!alinks[0]) {
				alinks = $target.find('a');
			}
			for (var i = 0; i < alinks.length; i++) {
				return IframeGantt.getAttributes(alinks[i]);
			};
		}
	},
	initEvent: function(params) {
		if (params) {
			return "parent.IframeGantt.trigger(event, '" + params + "')";
		}
		return "";
	},
	initButton: function(params, val) {
		if (params) {
			var $button = $('<button>').css({
				position: 'relative',
				right: '28px',
				cursor: 'pointer'
			}).attr(
				IframeGantt.getEventObject(params)
			).text('...');
			return $button.prop('outerHTML');
		}
		return "";
	},
	getEventObject: function(params) {
		var $a = $('<a>');
		$a.html(params);
		return JSON.parse($a.html());
	},
	trigger: function(e, params) {
		var onclick = "parent.IframeGantt.trigger(event, '" + params + "')";
		params = JSON.parse(params);
		if (params['onclick']) {
			if ($(e.target).is('button') && !params.etype) {
				$(e.target).attr(params);
				$(e.target).removeAttr('onclick');
				$(e.target).attr("onclick", onclick);
				eval(params['onclick'].replace("(event)", "(e, 'button')"));
			} else if ($(e.target).is('input') && params.etype) {
				$(e.target).attr(params);
				$(e.target).removeAttr('onclick');
				$(e.target).attr("on" + params.etype, onclick);
				eval(params['onclick'].replace("(event)", "(e, 'input')"));
			}
		}

	},
	getAttributes: function(el) {
		var obj = {};
		var map = el.attributes;
		for (var i = 0; i < map.length; i++) {
			obj[map[i].name] = map[i].value;
		};
		return obj;
	},
	getCurrentTask: function(iframeId) {
		var ge = window[iframeId].getGanttMasterInst();
		return ge.currentTask;
	},
	initEvents: function(id, iframeId) {
		var target = $('#' + id);
		var iframe = target.find('#' + iframeId)[0];
		var $gantt = $(iframe.contentDocument);
		var $rows = $gantt.find("[__template='TASKROW']");
		var $tbody = $rows.parent();
		var events = IframeGantt.parameters(id, 'events');
		var _clientEvents = IframeGantt.parameters(id, '_clientEvents');
		$.each(['Click', 'DblClick'], function(i, t) {
			$.each(_clientEvents, function(k, event) {
				$tbody.on(t.toLowerCase() + "." + event.target, event.target, function(e) {
					e.preventDefault();
					var fn = events["on" + t + k];
					if (fn && $.isFunction(fn))
						return event.fn(e, fn, iframeId);
				});
			});
		});
	}
};