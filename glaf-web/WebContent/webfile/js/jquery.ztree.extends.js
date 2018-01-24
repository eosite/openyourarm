/**
 * jquery ztree plugin
 * 
 */
(function($) {
	var plugin = 'ztree', optionKey = plugin + ".options";

	var ZtreeFunc = (function() {
		var func = new Function();
		var zt = $.fn.zTree, consts = zt.consts;
		var view = zt._z.view, data = zt._z.data,
		tools = zt._z.tools;
		view.makeDOMNodeFont = function(setting, node) {
			return "<i class='{0}' aria-hidden='true'></i>"
					.format(node.fontSkin || "");
		};
		
		func.makeDOMNodeIcon_ = view.makeDOMNodeIcon;
		func.makeDOMNodeIcon = view.makeDOMNodeIcon = //
		function(html, setting, node) {
			var nameIsHTML = setting.view.nameIsHTML, awesome = setting.view.awesome;
			var nameStr = data.getNodeName(setting, node), name = nameIsHTML ? nameStr
					: nameStr.replace(/&/g, '&amp;').replace(/</g, '&lt;')
							.replace(/>/g, '&gt;');
			var cls = "", $i = "";
			if (awesome && node.fontSkin) {
				$i = view.makeDOMNodeFont(setting, node);
			} else {
				cls = view.makeNodeIcoClass(setting, node);
			}

			html.push("<span id='", node.tId, consts.id.ICON,
					"' title='' treeNode", consts.id.ICON, " class='", cls,
					"' style='", view.makeNodeIcoStyle(setting, node), "'>",
					$i, "</span><span id='", node.tId, consts.id.SPAN, "'>",
					name, "</span>");
		};
		return func;
	})();

	function Build(o) {
		this.$target = $(o);
		this.highlights = null;
		this.state = $.data(o, optionKey);
		this.init(this.state.options, this.state.datas);
	}

	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string' && (options in $.fn[plugin].methods)) {
			var p = this.data(plugin);
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

	$.fn[plugin].methods = {
		init : function(setting, datas) {
			this.state.ztree = $.fn.zTree.init(this.$target.addClass(plugin),
					setting, datas);
			return this;
		},
		getZtree : function() {
			return this.state.ztree;
		},
		options : function() {
			return this.state.options;
		},
		updateHighlight : function(nodes, highlight) {
			var ztree = this.state.ztree;
			$.each(nodes, function(i, node) {
				node.highlight = highlight || false;
				ztree.updateNode(node);
			});
			return this;
		},
		highlight : function(keys, value, append) {
			if (!keys || !value)
				return this;
			var ztree = this.state.ztree;
			var nodes = this.highlights;
			if (nodes && nodes.length && !append) {
				this.updateHighlight(nodes, !!append);
			}
			if (!(keys instanceof Array)) {
				keys = [ keys ];
			}
			nodes = [];
			$.each(keys, function(i, key) {
				var ns = ztree.getNodesByParamFuzzy(key, value, null);
				if (ns != null && ns.length) {
					$.each(ns, function() {
						nodes.push(this);
					});
				}
			});
			this.highlights = nodes;
			if (nodes && nodes[0]) {
				$.each(nodes, function(i, node) {
					!node.isParent && (node = node.getParentNode());
					ztree.expandNode(node, true, true, false);
				});
				this.updateHighlight(nodes, true);
			}
			return this;
		}
	};

	Build.prototype = $.extend(true, $.fn[plugin].methods, {
		constructor : Build
	});

	$.fn[plugin].defaults = {
		async : {
			enable : true,
			url : ''
		},
		view : {
			awesome : false,
			dblClickExpand : true,
			showLine : true,
			selectedMulti : false,
			expandSpeed : 'fast', // 'slow'
			fontCss : function(treeId, treeNode) {
				return (!!treeNode.highlight) ? {
					color : "#A60000",
					"font-weight" : "bold"
				} : {
					color : "#333",
					"font-weight" : "normal"
				};
			}
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "parentId",
				rootPId : null
			}
		},
		check : {
			chkStle : 'checkbox',
			enable : false
		},
		callback : {
			beforeClick : function() {
			}
		}
	};

})(jQuery);