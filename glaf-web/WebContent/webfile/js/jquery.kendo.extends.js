/*******************************************************************************
 * 扩展方法...................................................
 */
(function($) {
	$.fn.extend({
		outter : function() {
			return this[0] ? this[0].outerHTML : "";
		}
	});

	$.extend({
		fnInit : function(options, params, plugin, fn) {
			if (typeof options == 'string') {
				return $.fn[plugin].methods[options].call($.data(this, plugin),
						this, params);
			}
			options = options || {};
			return this.each(function(i, o) {
				var state = $.data(o, plugin + ".options");
				if (state) {
					$.extend(true, state.options, options);
				} else {
					$.data(o, plugin + ".options", {
						options : $.extend(true, {}, $.fn[plugin].defaults,
								options),
						target : null
					});
				}
				fn(o);
			});
		},
		log : function(msg) {
			console.log(msg);
		}
	});
})(jQuery);

/**
 * mt
 */
(function($, kendo) {

	$.mt = {
		roleList : {
			grid : 'kendoGrid',
			window : 'kendoWindow',
			// multiselect : 'kendoMultiSelect',
			combobox : 'kendoComboBox',
			eidtor : 'editor',
			dtext : 'dtext',
			custom : 'custom',
			video : 'video'
		},
		get : function(url, data, fn) {
			$.ajax({
				url : url,
				contentType : "application/json",
				type : 'post',
				async : false,
				data : data,
				dataType : 'json',
				success : function(data) {
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
		var jq = $('#' + options.id), kendoWindow = 'kendoWindow';
		if (!jq[0]) {
			var defaults = {
				title : 'new Window',
				animation : false,
				modal : true,
				height : '400',
				width : '600',
				content : {},
				appendTo : 'body',
				actions : [ "Maximize", "Close" ]
			};
			// $.extend(defaults,options);
			$.each(defaults, function(k, v) {
				defaults[k] = options[k] == undefined ? v : options[k];
			});
			jq = $('<div>', {
				id : options.id
			}).kendoWindow(defaults);
		} else if (options.refresh) {
			jq.data(kendoWindow).refresh({
				url : options.content.url
			});
		}
		kendoWindow = jq.data(kendoWindow);
		kendoWindow.open();
		return {
			jq : jq,
			kendo : kendoWindow
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
					options : opt.options,
					target : this
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
			plugin : plugin,
			options : options,
			param : param,
			args : arguments
		}, kendoBind);
	};

	$.fn[plugin].methods = {
		init : function(jq, method, params, fn, args) {
			var kendoData = jq[plugin]('getKendoData'), dataRole = kendoData.dataRole, kendo = kendoData.kendo, roleFunction = $.fn[plugin].roleFunctions[dataRole];
			if (roleFunction) {
				if (roleFunction[method])
					return roleFunction[method].call(kendo, jq, params, args);
			} else if (fn) {
				return fn(kendo);
			}
		},
		reload : function(jq, params) {

			jq.each(function(index, item) {
				var kendo = $.fn[plugin].methods.init(jq, 'reload', params,
						null);
				$.extend(kendo.dataSource.transport.options.read.data, params);
				kendo.dataSource.read();
			});
		},
		rebind : function(jq) {
			var kendoData = jq[plugin]('getKendoData'), K = kendoData.kendo;

			if (K.destroy) {
				// K.destroy();
				var newJq = jq.clone();
				jq.parent().after(newJq).remove();
				newJq.mtkendo(kendoData.viewModel);
			}

		},
		getValue : function(jq, key) {

			return $.fn[plugin].methods.init(jq, 'getValue', key, function(
					kendo) {
				return jq.val();
			});

		},
		setValue : function(jq, val, args) {

			$.fn[plugin].methods.init(jq, 'setValue', val, function(kendo) {
				return jq.val(val);
			}, args);
		},
		options : function(jq) {
			return jq.data('kendoData').options;
		},
		getKendoData : function(jq) {
			var data = jq.data('kendoData') || {}, d = jq.data();
			var dataRole = jq.attr('data-role');
			if (!$.mt.roleList[dataRole]) {
				for ( var key in d) {
					if (key.toLowerCase().indexOf(dataRole.toLowerCase()) > 0) {
						$.mt.roleList[dataRole] = key;
						break;
					}
				}
			}
			return $.extend(data, {
				dataRole : dataRole || 'text',
				kendo : jq.data($.mt.roleList[dataRole])
			});
		},
		getViewModel : function(jq) {
			return jq.data('kendoData').viewModel;
		}
	};

	$.fn[plugin].roleFunctions = {
		grid : {
			reload : function(jq, params) {
				return this;
			}
		},
		combobox : {
			reload : function(jq, params) {
				this.value(null);// 先把下拉框清空
				return this;
			},
			getValue : function(jq, key) {
				var dataItem = this.dataItem();
				if (dataItem)
					return dataItem[key];
				return null;
			},
			setValue : function(jq, val) {

			}
		},
		editor : {
			get : function(jq) {
				var key = jq.attr("id"), UE = window.UE || {};
				return UE.getEditor(key);
			},
			getText : function(jq) {
				$.fn[plugin].roleFunctions.editor.get(jq).getContentTxt();
			},
			getValue : function(jq) {
				return $.fn[plugin].roleFunctions.editor.get(jq).getContent();
			},
			setValue : function(jq, val, args) {
				if (val) {
					var key = jq.attr("id"), UE = window.UE || {};
					UE.getEditor(key).ready(function() {
						this.setContent(val);
					});
				}
				// $.fn[plugin].roleFunctions.editor.get(jq).setContent(val);
			}
		},
		dtext : {
			getValue : function(jq, key) {
				return jq.text();
			},
			setValue : function(jq, val) {
				jq.text(val);
			}
		},
		custom : {
			setValue : function(jq, val, args) {
				customFunc.setValue(jq, args[2]);
				// jq.text(val);
			}
		},
		upload : {
			getValue : function(jq, key) {
				return jq.attr("randomparent");
			},
			setValue : function(jq, val) {
				if (val) {
					jq.attr({
						randomparent : val
					});
				}
			}
		},
		imageupload : {
			getValue : function(jq,key){
				return jq.attr("randomparent");
			},
			setValue : function(jq,val){
				if(val){
					jq.attr({
						randomparent : val
					});
				}
			}
		},
		video : {
			getValue : function(jq, key) {
				return jq.data("video");
			},
			setValue : function(jq, val) {
				jq.data("video", val);
			}
		}
	};

})(jQuery, kendo);

(function($) {

	var plugin = 'dialog', key = 'k-dialog';

	function createdialog(target) {
		var $this = $(target), state = $this.data(plugin), make = function() {
			state.options.height = ($this.height() || state.options.height) * 1 + 20;
			state.options.width = ($this.width() || state.options.width) * 1 + 20;
			$this.wrap(function() {
				return "<div class='k-dialog'></div>";
			}).css({
				width : state.options.width - 20,
				height : state.options.height - 70,
				overflow : 'auto'
			});
			var $dialog = $this.closest('.k-dialog');
			if (state.options.buttons) {
				var $div = $("<div class='k-tools'>").css({
					'text-align' : 'right',
					margin : '5px'
				}).appendTo($dialog), $button;

				$.each(state.options.buttons, function(i, v) {
					$button = $('<button>', {
						style : 'margin:5px'
					}).text(v.text).kendoButton({
						imageUrl : v.imageUrl,
						click : function(e) {
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
					options : $
							.extend(true, {}, $.fn[plugin].defaults, options),
					target : null
				});
			}
			createdialog(o);
		});

	};

	$.fn[plugin].methods = {
		open : function(jq) {
			jq[plugin](plugin).open();
		},
		center : function(jq) {
			jq[plugin](plugin).center();
		},
		close : function(jq) {
			jq[plugin](plugin).close();
		},
		options : function(jq) {
			return $.data(jq[0], plugin).options;
		},
		dialog : function(jq) {
			return jq.data(key);
		}
	};

	$.fn[plugin].defaults = {
		title : 'new Window',
		animation : false,
		modal : true,
		height : '400',
		width : '600',
		content : {},
		appendTo : 'body',
		actions : [ "Close" ]
	};

})(jQuery);

/**
 * 自定义表格
 * 
 * @param $
 */
(function($) {

	var plugin = 'definedTable', optionKey = 'k-definedTable';

	function createtable(target) {
		var $this = $(target), state = $this.data(optionKey), options = state.options;
		if (options && options.template) {
			ajaxRequest($this, {
				rid : options.rid
			});
		}
	}

	function ajaxRequest(jq, params) {
		$.ajax({
			url : contextPath + '/mx/form/data/gridData',
			type : 'post',
			dataType : 'json',
			async : false,
			data : JSON.stringify(params),
			contentType : "application/json",
			success : function(data) {
				load(jq, data.data);
			}
		});
	}

	function load(jq, data) {
		// if(data && data.length > 0){
		var options = jq[plugin]("options");
		// var t = new BuildTable(jq,options.template,data.length);
		// $.each(data,function(i,row){
		// t.append(row);
		// });
		new BuildTable(jq, options.template, data || []);
		// } else {
		// jq.html("");
		// }
	}

	function BuildTable(target, template, rows) {

		this.size = rows.length;
		this.rows = rows;
		this.index = 1;
		this.target = target.html("");
		this.template = template;
		this.isList = false;
		this.listTemplate = template.htmldata
				|| "PLEASE EDIT HTML CSS DEFINED TEMPLATE!";// 循环体
		this.sb = new StringBuffer();

		this.init = function() {
			var that = this;
			var list = $(that.template.listTemplate);
			that.sb.append(that.template.header
					|| "<table class='k-definedtable'>");
			if (list && list[0]) {
				that.isList = true;
				that.listTemplate = list.attr({
					role : 'k-row',
					rowindex : '~F{row.index - 1}'
				}).outter();
			}

			function remove(html) {
				if (html[0])
					html.remove();
			}

			$.each(that.rows, function(i, row) {
				that.append(row);
			});
			if (that.size == 0) {
				this.build();
			}
		};

		this.getTemplate = function(row) {
			if (row != null) {
				row.index = row.startIndex;
				var t = this.listTemplate;
				for ( var key in row) {
					t = this.filter(key, row, t);
				}
				return t;
			}
		};

		this.regex = new RegExp("~F\{(.*?)\}", "g");

		this.filter = function(key, row, t) {
			var result;
			if (/_0_/g.test(key)) {
				// 模版 table.column 数据 table_0_column 替换
				row[key.split("_0_")[1]] = row[key];
				t = t.replace(new RegExp(key.replace("_0_", "."), "g"),
						row[key]);
			}
			while ((result = this.regex.exec(t)) != null) {// 表达式运算
				if (result.length > 0) {
					var find = result[0], express, val;
					if (result.length > 1) {
						express = result[1];
						try {
							val = eval(express);
							t = t.replace(find, val);
						} catch (e) {
							console.log(e);
						}
					}
				}
			}
			return t;
		};

		this.append = function(row) {
			var t = this.getTemplate(row);
			if (this.size > 0) {
				if (!this.isList) {
					this.sb.append("<tr").append(" rowindex=")//
					.append(this.index - 1).append(" role='k-row'")//
					.append(" ><td>").append(t)//
					.append("</td></tr>");
				} else {
					this.sb.append(t || "");
				}
			}
			if (this.index == this.size) {
				this.build();
			}
			this.index++;
		};

		this.build = function() {
			this.sb.append(this.template.footer || "</table>");
			this.target.append(this.sb.toString()).data(plugin, this);
		};

		this.dataItem = function(o) {
			return this.rows[$(o).closest("[role=k-row]").attr("rowindex")];
		};

		this.query = function(params) {
			return ajaxRequest(this.target, $.extend(this.target[plugin]
					("options"), {
				params : JSON.stringify(params)
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
					options : $
							.extend(true, {}, $.fn[plugin].defaults, options),
					target : null
				});
			}
			createtable(o);
		});

	};

	$.fn[plugin].methods = {
		options : function(jq) {
			return jq.data(optionKey).options;
		},
		query : function(jq, params) {
			ajaxRequest(jq, $.extend(jq[plugin]("options"), {
				params : JSON.stringify(params)
			}));
		},
		load : function(jq, datas) {
			load(jq, datas);
		},
		dataItem : function(jq, o) {
			var builder = jq.data(plugin);
			if (builder) {
				var $tr = $(o).closest("[role=k-row]");
				return builder.rows[$tr.attr("rowindex")];
			}
			return null;
		}
	};

	$.fn[plugin].defaults = {

	};

})(jQuery);

function definedTableQuery(o) {

	var table = $("#definedTable1").data("definedTable");

	table.query({});
}

(function($) {

	/*var plugin = "table", optionKey = plugin + ".options";

	var create = function(o) {
		var $this = $(target), state = $this.data(optionKey), options = state.options;
		if (options) {
			var fn = new O($this, options);

			$this.data(plugin, fn);
		}
	};

	var O = function(jq) {
		this.target = jq;
		this.options = options;

	};
	
	$.fn[plugin] = function(options, params) {
		return $.fnInit(options, params, plugin, create);
	};
	
	$.fn[plugin].methods = {
			options : function(jq) {
				return jq.data(optionKey).options;
			}
	};

	O.prototype = $.extend(true, {
		constructor : O
	}, $.fn[plugin].methods);


	$.fn[plugin].defaults = {
		request : {
			type : 'post',
			url : '',
			cache : null,
			contentType : "application/json",
			dataType : "json",
			async : false,
			success : function(res) {

			},
			error : function(res) {

			},
			complete : function() {

			}
		}
	};*/

})(jQuery);

String.prototype.trim = function() {
	return this ? this.replace(/(^\s*)|(\s*$)/g, "") : null;
};

String.prototype.lTrim = function() {
	return this ? this.replace(/(^\s*)/g, "") : null;
};

String.prototype.rTrim = function() {
	return this ? this.replace(/(\s*$)/g, "") : null;
};

String.prototype.format = function() {

};

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
		for ( var i = 1; i < arguments.length; i++)
			str = str.replace(new RegExp("\\{" + (i - 1) + "\\}", "g"),
					arguments[i]);
		return this.append(str);
	};

	if (str) {
		this.append(str);
	}
}
