
/**
 * mt
 */
(function($, kendo) {

	$.mt = {
		roleList: {
			grid: 'kendoGrid',
			window: 'kendoWindow',
			// multiselect : 'kendoMultiSelect',
			combobox: 'kendoComboBox',
			eidtor: 'editor',
			dtext: 'dtext',
			custom: 'custom',
			video: 'video',
			dropdownlist: 'kendoDropDownList',
			office: 'office'
		},
		get: function(url, data, fn) {
			$.ajax({
				url: url,
				contentType: "application/json",
				type: 'post',
				async: false,
				data: data,
				dataType: 'json',
				success: function(data) {
					if (fn)
						fn.call(this, data);
				}
			});
		}
	};

	$.mt.window = function(options) {
		if (!options || !options.id) {
			throw new Error('window 参数不对!');
		}
		var jq = $('#' + options.id),
			kendoWindow = 'kendoWindow';
		if (!jq[0]) {
			var defaults = {
				title: 'new Window',
				animation: false,
				modal: true,
				height: '400',
				width: '600',
				content: {},
				appendTo: 'body',
				actions: ["Maximize", "Close"]
			};
			// $.extend(defaults,options);
			$.each(defaults, function(k, v) {
				defaults[k] = options[k] == undefined ? v : options[k];
			});
			jq = $('<div>', {
				id: options.id
			}).kendoWindow(defaults);
		} else if (options.refresh) {
			jq.data(kendoWindow).refresh({
				url: options.content.url
			});
		}
		kendoWindow = jq.data(kendoWindow);
		kendoWindow.open();
		return {
			jq: jq,
			kendo: kendoWindow
		};
	};

	$.mt.init = function(opt, fn) {
		if (typeof opt.options == 'string') {
			var method = $.fn[opt.plugin].methods[opt.options];
			if (method) {
				return method(this, opt.param, opt.args);
			} else {
				return this;
			}
		}
		opt.options = opt.options || {};
		return this.each(function() {
			var state = $.data(this, 'kendoData');
			if (state) {
				$.extend(state.options, opt.options);
			} else {
				opt.options.target = this;
				$.data(this, 'kendoData', {
					options: opt.options,
					target: this
				});
			}
			if (fn)
				fn(this);
		});
	};

})(jQuery, kendo);

/**
 * mtkendo get、set、reload。。。 klaus.wang 2015-05-21
 */
(function($, kendo) {

	var plugin = 'mtkendo';

	function kendoBind(ele) {
		var state = $.data(ele, 'kendoData');
		var opts = state.options;
		var vm = kendo.observable(opts);
		kendo.bind($(ele), vm);
		state.viewModel = vm;
	}

	$.fn[plugin] = function(options, param) {
		return $.mt.init.call(this, {
			plugin: plugin,
			options: options,
			param: param,
			args: arguments
		}, kendoBind);
	};

	$.fn[plugin].methods = {
		init: function(jq, method, params, fn, args) {
			var kendoData = jq[plugin]('getKendoData'),
				dataRole = kendoData.dataRole,
				kendo = kendoData.kendo,
				roleFunction = $.fn[plugin].roleFunctions[dataRole];
			if (roleFunction) {
				if (roleFunction[method])
					return roleFunction[method].call(kendo, jq, params, args);
			} else if (fn) {
				return fn(kendo);
			}
		},
		reload: function(jq, params) {

			jq.each(function(index, item) {
				var kendo = $.fn[plugin].methods.init(jq, 'reload', params,
					null);
				$.extend(kendo.dataSource.transport.options.read.data, params);
				kendo.dataSource.read();
			});
		},
		rebind: function(jq) {
			var kendoData = jq[plugin]('getKendoData'),
				K = kendoData.kendo;

			if (K.destroy) {
				// K.destroy();
				var newJq = jq.clone();
				jq.parent().after(newJq).remove();
				newJq.mtkendo(kendoData.viewModel);
			}

		},
		getValue: function(jq, key) {
			return $.fn[plugin].methods.init(jq, 'getValue', key, function(
				kendo) {
				return jq.val();
			});
		},
		getText: function(jq, key) {
			return $.fn[plugin].methods.init(jq, 'getText', key,
				function(kendo) {
					return jq.val();
				});
		},
		getRow: function(jq, key) {
			return $.fn[plugin].methods.init(jq, 'getRow', key,
				function(kendo) {
					return jq.val();
				});
		},
		getAll: function(jq, key) {
			return $.fn[plugin].methods.init(jq, 'getAll', key,
				function(kendo) {
					return jq.val();
				});
		},
		setValue: function(jq, val, args) {
			$.fn[plugin].methods.init(jq, 'setValue', val, function(kendo) {
				return jq.val($('<div>').html(val).text());
				//return jq.val(val);
			}, args);
		},
		options: function(jq) {
			return jq.data('kendoData').options;
		},
		getKendoData: function(jq) {
			var data = jq.data('kendoData') || {},
				d = jq.data();
			var dataRole = jq.data("role"); //jq.attr('data-role');
			if (!$.mt.roleList[dataRole]) {
				for (var key in d) {
					if (key.toLowerCase().indexOf(dataRole.toLowerCase()) > 0) {
						$.mt.roleList[dataRole] = key;
						break;
					}
				}
			}
			return $.extend(data, {
				dataRole: dataRole || 'text',
				kendo: kendo.widgetInstance(jq) //jq.data($.mt.roleList[dataRole])
			});
		},
		getViewModel: function(jq) {
			return jq.data('kendoData').viewModel;
		}
	};

	var roleFunctions = {
		grid: {
			reload: function(jq, params) {
				return this;
			},
			getValue: function(jq, key) {
				var item = this.dataItem(this.select().eq(0));
				if (item) {
					return item[key];
				}
				return item;
			},
			getRow: function(jq) {
				return this.dataItem(this.select().eq(0));
			},
			getAll: function(jq) {
				return this.dataItems().toJSON();
			}
		},
		combobox: {
			reload: function(jq, params) {
				this.value(null); // 先把下拉框清空
				return this;
			},
			getValue: function(jq, key) {
				var dataItem = this.dataItem();
				if (dataItem)
					return dataItem[key] || this.value();
				return null;
			},
			getText: function(jq, key) {
				var dataItem = this.dataItem();
				if (dataItem)
					return dataItem[key] || this.text();
				return null;
			},
			setValue: function(jq, val) {
				this.value(val);
			}
		},
		editor: {
			get: function(jq) {
				var key = jq.attr("id"),
					UE = window.UE || {};
				return UE.getEditor(key);
			},
			getText: function(jq) {
				$.fn[plugin].roleFunctions.editor.get(jq).getContentTxt();
			},
			getValue: function(jq) {
				return $.fn[plugin].roleFunctions.editor.get(jq).getContent();
			},
			setValue: function(jq, val, args) {
				if (val) {
					var key = jq.attr("id"),
						UE = window.UE || {};
						try {
							/**
							 * 简单转义(反转)
							 */
							if(/&lt/.test(val)){
								var $tmpDiv = $("<div>").html(val);
								val = $tmpDiv.get(0).innerText;
							}
						} catch(e){
							console.log(e);
						}
					UE.getEditor(key).ready(function() {
						this.setContent(val);
					});
				}
			}
		},
		dtext: {
			getValue: function(jq, key) {
				return jq.text();
			},
			setValue: function(jq, val) {
				jq.text(val);
			}
		},
		custom: {
			setValue: function(jq, val, args) {
				customFunc.setValue(jq, args[2]);
			}
		},
		upload: {
			getValue: function(jq, key) {
				return jq.attr("randomparent");
			},
			setValue: function(jq, val) {
				if (val) {
					jq.attr({
						randomparent: val
					});
				}
			}
		},
		imageupload: {
			getValue: function(jq, key) {
				return jq.attr("randomparent");
			},
			setValue: function(jq, val) {
				if (val) {
					jq.attr({
						randomparent: val
					});
					initImageUploadFunc();
				}
			}
		},
		video: {
			getValue: function(jq, key) {
				return jq.data("video");
			},
			setValue: function(jq, val) {
				jq.data("video", val);
			}
		},
		office: {
			getValue: function(jq, key) {
				return jq.attr("randomparent");
			},
			setValue: function(jq, val) {
				if (val) {
					jq.attr({
						randomparent: val,
						isSet: true
					});
					jq.office("openDocs");
				}
			}
		},
		datepicker: {
			getValue: function(jq, key) {
				return jq.val() || this.value();
			},
			setValue: function(jq, val) {
				if (val) {
					try {
						this.value(kendo.parseDate(val));
					} catch(e){
						if (typeof val == 'number') { // 后台是日期类型
							this.value(new Date(val));
						} else {
							jq.val(val);
						}
					}
					/*if (typeof val == 'number') { // 后台是日期类型
						this.value(new Date(val));
					} else {
						jq.val(val);
					}*/
				}
			}
		},
		multiselect: { // 用逗号拼接保存数据
			getValue: function(jq, key) {
				var v = jq.val() || this.value();
				if (v)
					return v.join(",");
				return "";
			},
			setValue: function(jq, val) {
				if (val) {
					kendo.widgetInstance(jq).value(val.split(","));
				}
			}
		},
		ztree: {
			getRow: function(jq) {
				var ztreeObj = jq.zTree.getZTreeObj(jq.attr('id'));
				var nodes = ztreeObj.getSelectedNodes();
				if (nodes) {
					return nodes[0];
				}
			}
		}
	};

	$.extend(roleFunctions, {
		dropdownlist: $.extend(true, {}, roleFunctions.combobox),
		datetimepicker : $.extend(true, {}, roleFunctions.datepicker)
	});

	$.fn[plugin].roleFunctions = roleFunctions;

})(jQuery, kendo);

(function($) {

	var plugin = 'dialog',
		key = 'k-dialog';

	function createdialog(target) {
		var $this = $(target),
			state = $this.data(plugin),
			make = function() {
				state.options.height = ($this.height() || state.options.height) * 1 + 20;
				state.options.width = ($this.width() || state.options.width) * 1 + 20;
				$this.wrap(function() {
					return "<div class='k-dialog'></div>";
				}).css({
					width: state.options.width - 20,
					height: state.options.height - 70,
					overflow: 'auto'
				});
				var $dialog = $this.closest('.k-dialog');
				if (state.options.buttons) {
					var $div = $("<div class='k-tools'>").css({
							'text-align': 'right',
							margin: '5px'
						}).appendTo($dialog),
						$button;

					$.each(state.options.buttons, function(i, v) {
						$button = $('<button>', {
							style: 'margin:5px'
						}).text(v.text).kendoButton({
							imageUrl: v.imageUrl,
							click: function(e) {
								return v.click ? v.click.call(e, $this) : '';
							}
						});

						$div.append($button);
					});
				}
				var d = $dialog.kendoWindow(state.options).data('kendoWindow');
				$this.data(key, d);
				return d;
			};

		if (!$this.data(key)) {
			make();
		}

		$this[plugin]('open');
		$this[plugin]('center');

	}

	$.fn[plugin] = function(options, param) {

		if (typeof options == 'string') {
			return $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i, o) {
			var state = $.data(o, plugin);
			if (state) {
				$.extend(true, state.options, options);
			} else {
				$.data(o, plugin, {
					options: $
						.extend(true, {}, $.fn[plugin].defaults, options),
					target: null
				});
			}
			createdialog(o);
		});

	};

	$.fn[plugin].methods = {
		open: function(jq) {
			jq[plugin](plugin).open();
		},
		center: function(jq) {
			jq[plugin](plugin).center();
		},
		close: function(jq) {
			jq[plugin](plugin).close();
		},
		options: function(jq) {
			return $.data(jq[0], plugin).options;
		},
		dialog: function(jq) {
			return jq.data(key);
		}
	};

	$.fn[plugin].defaults = {
		title: 'new Window',
		animation: false,
		modal: true,
		height: '400',
		width: '600',
		content: {},
		appendTo: 'body',
		actions: ["Close"]
	};

})(jQuery);

/**
 * 自定义表格
 * 
 * @param $
 */
(function($) {

	var plugin = 'definedTable',
		optionKey = 'k-definedTable';

	function createtable(target) {
		var $this = $(target),
			state = $this.data(optionKey),
			options = state.options;
		if (options && options.template) {
			options.page = options.template.page ? options.template.page * 1 : 1;
			options.pageSize = options.template.pageSize ? options.template.pageSize * 1 : 10;
			ajaxRequest($this, {
				rid: options.rid,
				page: options.page,
				pageSize: options.pageSize,
				params : JSON.stringify(options.params||{})
			});
		}
	}

	function ajaxRequest(jq, params) {
		$.ajax({
			url: contextPath + '/mx/form/data/gridData',
			type: 'post',
			dataType: 'json',
			async: true,
			data: JSON.stringify(params),
			contentType: "application/json",
			success: function(data) {
				load(jq, data.data, data.total);
			}
		});
	}

	function load(jq, data, total) {
		// if(data && data.length > 0){
		var options = jq[plugin]("options");
		// var t = new BuildTable(jq,options.template,data.length);
		// $.each(data,function(i,row){
		// t.append(row);
		// });
		// setTimeout(function(){
		new BuildTable(jq, options.template, data || [], total);
		// },50);
		// } else {
		// jq.html("");
		// }
		// 执行加载完回调事件只执行一次
		var loadEndExecOne = options.loadEndExecOneFn;
		if (loadEndExecOne) {
			loadEndExecOne.call(jq);
			options.loadEndFn = null;
		}

		var loadEnd = options.loadEndFn;
		if (loadEnd) {
			loadEnd.call(jq);
		}

		//修改图片信息，将没有表达式的图片修改为取rp
		$.each(jq.find("img"),function(i,o){
			var $img = $(o);
			var $img_src = $img.attr("src");
			if($img_src.indexOf("/glaf/mx/form/imageUpload") > -1){
				var rp = $img_src.substring($img_src.indexOf("rp=")+3);
				var matchAry = rp.match(/.jpg|.png|.gif|.bmp/);
				if(matchAry && matchAry.length > 0){
					$img.attr("src",rp).show();
				}
			}
		})

	}

	function BuildColumn(latitudes, dynamicColumn, valueKey) {
		this.single = {};
		this.latitudes = latitudes || [];
		this.dynamicColumn = dynamicColumn;
		this.valueKey = valueKey;
		this.rows = new Object();
		this.rowIndex = 0;
		this.collection = [];
		this.push = function(row) {
			var o = row[this.dynamicColumn];
			if (!this.single[o]) {
				this.single[o] = "_" + (this.rowIndex++);
				this.collection.push(o);
			}
			if (this.latitudes.length > 0) {
				var key = [];
				$.each(this.latitudes, function(i, v) {
					key.push(row[v]);
				});
				key = key.join('_0_');
				var r = this.rows[key];
				if (!r) {
					r = this.rows[key] = {};
					$.extend(r, row);
				}
				r[this.single[o]] = row[this.valueKey] || "";
			}
		};

		this.getRows = function() {
			var rows = new Array(),
				row, index = 1;
			for (var key in this.rows) {
				row = this.rows[key];
				row.startIndex = index;
				index++;
				rows.push(row);
			}
			this.flush();
			return rows;
		};

		this.build = function(tdTemplate, syn) {
			if (this.collection.length > 0) {
				// var tdTemplate = td.html("{0}").outter();
				var SB = new StringBuffer();
				$.each(this.collection,
					function(i, o) {
						SB.appendFormat(tdTemplate, syn ? "~F{row._" + i + "}" : o);
					});
				return SB.toString();
			}
		};

		this.flush = function() {
			this.single = null;
			this.collection = null;
		};
	}

	function BuildTable(target, template, rows, total) {

		this.size = rows.length;
		this.rows = rows;
		this.rowsSize = rows.length;
		this.allRows = rows;
		this.index = 1;
		this.target = target.empty();

		this.options = target[plugin]("options");

		this.template = template;
		this.isList = false;
		this.listTemplate = template.htmldata || "PLEASE EDIT HTML CSS DEFINED TEMPLATE!"; // 循环体
		this.sb = new StringBuffer();
		this.dynamics = [];
		this.callbacks = null;
		this.total = total;

		this.replaceTd = function(jq, tds) {
			jq.replaceWith(tds);
		};

		this.setRows = function(rows) {
			this.rows = rows;
			this.size = rows.length;
		};

		this.init = function() {

			var that = this,
				tdList = '.td-list';
			var list = $(that.template.listTemplate),
				header = that.template.header;

			if (that.template.dynamic) { // 动态列
				var tdT = list.find(tdList);
				var bc = new BuildColumn(that.template.latitudes,
					that.template.dynamicColumn, that.template.valueKey);
				$.each(that.rows, function(i, v) {
					bc.push(v);
				});
				var tds = bc.build(that.template.tdBodyTemplate, true);
				// var tds = bc.build(tdT, true);
				that.replaceTd(tdT, tds);
				if (header) {
					// var headerTd = $(header).find(tdList);
					tds = bc.build(that.template.tdHeaderTemplate);
					// header = header.replace(headerTd.outter(), tds);
					header = new StringBuffer().appendFormat(header, tds)
						.toString();
					// header = header.replace(headerTd.outter(),
					// bc.build(headerTd));
				}
				that.setRows(bc.getRows());
			}

			that.sb.append(header || "<table class='k-definedtable'>");
			if (list && list[0]) {
				that.isList = true;
				that.listTemplate = list.attr({
					role: 'k-row',
					rowindex: '~F{row.index - 1}'
				}).outter();
			}

			function remove(html) {
				if (html[0])
					html.remove();
			}
			if(this.template.pageable == "true" && this.template.serverPaging != "true"){
				this.startPageCount = (this.options.page-1)*this.template.pageSize ;
				this.endPageCount = this.options.page*this.template.pageSize ;
				that.rows = that.rows.slice(this.startPageCount,this.endPageCount);
				this.rowsSize = this.rows.length ;
			}
			$.each(that.rows, function(i, row) {
				that.append(row);
			});
			if (that.size == 0) {
				this.build();
			}
			//修改图片信息，将没有表达式的图片修改为取rp
			$.each(that.target.find("img"),function(i,o){
				var $img = $(o);
				var $img_src = $img.attr("src");
				if($img_src.indexOf("/glaf/mx/form/imageUpload") > -1){
					var rp = $img_src.substring($img_src.indexOf("rp=")+3);
					var matchAry = rp.match(/.jpg|.png|.gif|.bmp/);
					if(matchAry && matchAry.length > 0){
						$img.attr("src",rp).show();
					}
				}
			})
		};

		this.getTemplate = function(row) {
			if (row != null) {
				this.rowIndex(row);
				var t = this.listTemplate;
				for (var key in row) {
					t = this.filter(key, row, t);
				}
				return t;
			}
		};

		this.rowIndex = function(row) {
			row.index = row.startIndex;
			var index = (this.options.page - 1) * (this.options.pageSize) + row.startIndex;
			row.rowIndex = index;
		};

		this.regex = new RegExp("~F\{(.*?)\}", "g");

		this.filter = function(key, row, t) {
			var result;
			if (/_0_/.test(key)) {
				// 模版 table.column 数据 table_0_column 替换
				row[key.split("_0_")[1]] = row[key];
				t = t.replace(new RegExp(key.replace("_0_", "."), "g"),
					row[key]);
			} else if (/field_/i.test(key)) {
				t = t.replace("row." + key, row[key]);
			}
			while ((result = this.regex.exec(t)) != null) { // 表达式运算
				if (result.length > 0) {
					var find = result[0],
						express, val;
					if(find == "~F{databaseId}"){
						t = t.replace(find, row.databaseId || 0);
					}else{
						if (result.length > 1) {
							express = result[1];
							try {
								val = eval(express);
								t = t.replace(find, val == undefined ? "" : val);
							} catch (e) {
								console.log(e);
							}
						}
					}
				}
			}
			/*while ((result = this.regex.exec(t)) != null) { // 表达式运算
				if (result.length > 0) {
					var find = result[0],
						express, val;
					if (result.length > 1) {
						express = result[1];
						try {
							val = eval(express);
							t = t.replace(find, val == undefined ? "" : val);
						} catch (e) {
							console.log(e);
						}
					}
				}
			}*/
			return t;
		};

		this.append = function(row) {
			var t = this.getTemplate(row);
			if (this.size > 0) {
				if (!this.isList) {
					this.sb.append("<tr").append(" rowindex=") //
						.append(this.index - 1).append(" role='k-row'") //
						.append(" ><td>").append(t) //
						.append("</td></tr>");
				} else {
					this.sb.append(t || "");
				}
			}
			if (this.index == this.rowsSize) {
				this.build();
			}
			this.index++;
		};

		this.build = function() {
			var that = this;
			that.sb.append(that.template.footer || "</table>");
			that.target.append(that.sb.toString()).data(plugin, that);

			if (that.template.pageable == 'true') { // 分页
				that
					.addFn(function() {
						// var tfoot = that.target.find('tfoot');
						var $page = $("<div>", {
							'class': 'row'
						}).pagination({
							pageSize: that.options.pageSize,
							page: that.options.page,
							total: that.total,
							fn: function(page) {
								that.options.page = page;
								if(that.template.serverPaging == "true"){
									that.query({});	
								}else{
									load(that.target, that.allRows, that.total);
								}
							}
						});
						var $table = that.target.find("table");
						$table.find("tfoot") && $table
							.append("<tfoot><tr><td></td></tr></tfoot>");
						// that.target.find("table").after($page);
						$table.find("tfoot tr td:eq(0)")
							.attr({
								colspan: that.target.find(
									"table tr:eq(0)").find(
									"td").length,
								style: 'text-align:right'
							}).append($page);
					});
			}

			if (that.template.combine == 'true') { // 合并
				function combineTable_() {
					var combine = {
						id: '#' + that.target.attr('id'),
						spanLevel: that.template.spanLevel,
						colspan: that.template.colspan,
						rowspan: that.template.rowspan
					};
					combineTable(combine);
				}
				that.addFn(combineTable_);
			}

			that.fire();
		};

		this.fire = function() {
			if (this.callbacks) {
				this.callbacks.fire();
			}
		};

		this.addFn = function(fn) {
			if (!this.callbacks) {
				this.callbacks = $.Callbacks("definedTable callbacks");
			}
			this.callbacks.add(fn);
		};

		this.dataItem = function(o) {
			return this.rows[$(o).closest("[role=k-row]").attr("rowindex")];
		};

		this.query = function(params) {
			return ajaxRequest(this.target, $.extend(this.options, {
				params: JSON.stringify(params)
			}));
		};

		this.reload = function(params) {
			return this.query(params);
		};

		this.init();

	}

	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string') {
			return $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i, o) {
			var state = $.data(o, optionKey);
			if (state) {
				$.extend(true, state.options, options);
			} else {
				$.data(o, optionKey, {
					options: $
						.extend(true, {}, $.fn[plugin].defaults, options),
					target: null
				});
			}
			createtable(o);
		});

	};

	$.fn[plugin].methods = {
		options: function(jq) {
			return jq.data(optionKey).options;
		},
		query: function(jq, params) {
			ajaxRequest(jq, $.extend(jq[plugin]("options"), {
				params: JSON.stringify(params)
			}));
		},
		load: function(jq, datas) {
			load(jq, datas);
		},
		dataItem: function(jq, o) {
			var builder = jq.data(plugin);
			if (builder) {
				var $tr = $(o).closest("[role=k-row]");
				return builder.allRows[$tr.attr("rowindex")];
			}
			return null;
		},
		loadEndExecOne: function(jq, fn) { // 加载完执行一次事件
			if (typeof fn == "function") {
				$.extend(jq[plugin]("options"), {
					loadEndExecOneFn: fn
				});
			}
		},
		loadEnd: function(jq, fn) { // 加载完执行事件
			if (typeof fn == "function") {
				$.extend(jq[plugin]("options"), {
					loadEndFn: fn
				});
			}
		}
	};

	$.fn[plugin].defaults = {

	};

})(jQuery);

/**
 * 合并单元格
 * 
 * @param combine
 */
function combineTable(combine) {

	function tdFilter(td0, td1) {
		return (txt0 = td0.text()) && (txt1 = td1.text()) && txt1 == txt0;
	}

	var spanLevel = {
		colspan: function() {
			if (combine.colspan)
				$.colspan(combine.id, combine.colspan || 'n', tdFilter);
			if (combine.rowspan)
				$.rowspan(combine.id, combine.rowspan || 'n', tdFilter);
		},
		rowspan: function() {
			if (combine.rowspan)
				$.rowspan(combine.id, combine.rowspan || 'n', tdFilter);
			if (combine.colspan)
				$.colspan(combine.id, combine.colspan || 'n', tdFilter);
		}
	};
	setTimeout(function() {
		spanLevel[combine.spanLevel || 'rowspan']();
	}, 0);
}

(function($) {

	var plugin = 'pagination',
		optionKey = plugin + ".options";

	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string') {
			return $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i, o) {
			var state = $.data(o, optionKey);
			if (state) {
				$.extend(true, state.options, options);
			} else {
				$.data(o, optionKey, {
					options: $
						.extend(true, {}, $.fn[plugin].defaults, options),
					target: null
				});
			}
			$.data(o, plugin, new create(o));
		});
	};

	$.fn[plugin].defaults = {
		page: 1,
		total: 0,
		pageSize: 20
	};

	function create(target) {
		this.jq = target;

		this.options = $.data(target, optionKey).options;

		this.template = [
			'<div class="col-md-7 col-sm-7">',
			'<ul class="mt-pagination mt-pagination-sm" style="visibility: visible;">',
			'</ul>', '</div>'
		].join('');

		this.href = "<li><a href='javascript:void(0)' page='{0}' title='{2}'>{1}</a></li>";

		(function(that) {
			var $temp = $(that.template);
			var sb = new StringBuffer();

			var page = that.options.page;
			var pageSize = that.options.pageSize;
			var total = that.options.total;
			var totalPage = Math.ceil(total / pageSize);

			sb.appendFormat(that.href, 1, "<<", "首页");

			sb.appendFormat(that.href, page > 1 ? page - 1 : 1, "<", "上一页");

			sb.appendFormat(
				"<li><span title='第{0}页 共{1}页 '>No.{0}</span></li>", page,
				totalPage);

			sb.appendFormat(that.href, totalPage > page ? page + 1 : totalPage,
				"&gt", "下一页");

			sb.appendFormat(that.href, totalPage, "&gt&gt", "末页");

			sb.appendFormat("<li><span title='共 {0} 条记录'>共 {0} 条</span></li>",
				total);

			$temp.find("ul").append(sb.toString());

			$temp.appendTo(that.jq);
			$(that.jq).on(
				"click.mt-pagination.a",
				"ul.mt-pagination li a",
				function(e) {
					that.options.fn && that.options.fn.call(that, $(e.target).attr(
						"page") * 1);
				});

		})(this);
	}

})(jQuery);

function definedTableQuery(o) {

	var table = $("#definedTable1").data("definedTable");

	table.query({});
}

(function($) {

	var plugin = "office",
		optionKey = plugin + ".options";

	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string') {
			return $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i, o) {
			var state = $.data(o, optionKey);
			if (state) {
				$.extend(true, state.options, options);
			} else {
				$.data(o, optionKey, {
					options: $
						.extend(true, {}, $.fn[plugin].defaults, options),
					target: null
				});
			}
			create(o);
		});
	};

	function create(target) {

		var randomUUID = new UUID().createUUID();

		var $this = $(target).attr({
				randomparent: randomUUID
			}),
			state = $this.data(optionKey),
			options = state.options;

		var key = $this.attr("key");
		if (key == 'true') { //
			new buildKey($this).build();
		}
		officeForm.randomparent.value = randomUUID;
	}

	function buildKey(jq) {

		this.jq = jq;

		this.options = this.jq.data(optionKey).options;

		var keyBoard = ["<div class='col-xs-2'>",
			"<br><br><ul style='list-style-type:none'></ul>", "</div>"
		];

		this.build = function() {
			var that = this;
			$
				.ajax({
					url: contextPath + '/mx/form/defined/getFormCompoentProperties',
					data: {
						id: this.jq.attr("id"),
						pageId: this.options.pageId,
						dataRole: "office"
					},
					dataType: 'json',
					async: true,
					success: function(ret) {
						if (ret && ret.rows) {
							var collection = new Object(),
								p = {};
							ret.rows.sort(function(a, b) {
								return b.listNo - a.listNo; // 根据容器属性排序(倒序)
							});
							$
								.each(
									ret.rows,
									function(i, v) {
										if (v.parentId) {
											if (!collection[v.parentId]) {
												collection[v.parentId] = new Array();
											}
											collection[v.parentId]
												.push(v);
										} else {
											p[v.name] = v;
										}
										if (v.id in collection) {
											v.children = collection[v.id];
										} else {
											v.children = collection[v.id] = new Array();
										}
									});
							if (p.officeKey) {
								var lis = new StringBuffer();
								$
									.each(
										p.officeKey.children,
										function(i, v) {
											if (v.children) {
												var parentTitle = false;
												$
													.each(
														v.children,
														function(
															$i,
															$v) {
															if ($v.value == 'true') {
																if (!parentTitle) {
																	parentTitle = true;
																	lis
																		.appendFormat(
																			"<li><span style='font-size: 16px; font-weight: bolder;'>{0}</span></li>",
																			v.title);
																}
																lis
																	.appendFormat(
																		"<li onclick='{0}'>{1}</li>",
																		$v.value_,
																		$v.title);
															}
														});
												lis.append("<br>");
											}
										});
								var $keyBoard = $(keyBoard.join(""));
								$keyBoard.find("ul").append(lis.toString());
								that.jq.find(">div").before($keyBoard);
							}
						}
					}
				});

		};

	}

	$.fn[plugin].methods = {
		openDocs: function(jq) {
			var isSet = jq.attr("isSet"),
				docUrl = null;
			if (isSet == 'true') {
				$.ajax({
					url: contextPath + "/mx/form/attachment?" + $.param({
						method: 'getFormAttachmentByRandomParent',
						randomparent: jq.attr("randomparent")
					}),
					async: true,
					type: 'post',
					dataType: 'JSON',
					success: function(ret) {
						if (ret) {
							var ext = /\.[^\.]+/.exec(ret.fileName);

							officeForm.filename.value = ret.fileName.replace(
								ext, '');
							officeForm.attachmentId.value = ret.id;
							officeForm.randomparent.value = ret.parent;
						}
					}
				});
				docUrl = contextPath + "/mx/form/attachment?" + $.param({
					method: 'getOfficeByRandomParent',
					randomparent: jq.attr("randomparent")
				});
			} else {
				docUrl = contextPath + "/webfile/templates/ntko/blank.docx";
			}
			intializePage(docUrl);
		}
	};

})(jQuery);

/*******************************************************************************
 * 扩展方法...................................................
 */
(function($) {
	$.fn.extend({
		outter: function() {
			return $('<div>').append(jQuery(this).clone()).html();
		}
	});

	$.extend({
		log: function(msg) {
			console.log(msg);
		}
	});

})(jQuery);

function StringBuffer(str) {

	this.collection = new Array();

	this.append = function(str) {
		this.collection[this.collection.length] = str;
		return this;
	};

	this.toString = function(split) {
		return this.collection.join(split ? split : '');
	};

	this.appendFormat = function() {
		if (arguments.length == 0)
			return this;
		var str = arguments[0];
		for (var i = 1; i < arguments.length; i++)
			str = str.replace(new RegExp("\\{" + (i - 1) + "\\}", "g"),
				arguments[i]);
		return this.append(str);
	};

	if (str) {
		this.append(str);
	}
}

/**
 * 数组排序
 */
var SortArray = function(datas, sortBy, desc){
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
};
