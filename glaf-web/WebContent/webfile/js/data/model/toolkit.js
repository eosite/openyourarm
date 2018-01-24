(function(Rotors) {

	if (Rotors && Rotors.RotorsInstance) {
		var customElements = Rotors.RotorsInstance.prototype.customElements;

		customElements["r-each"].compile_ = customElements["r-each"].compile;

		var nf = customElements["r-each"].compile_.toString().replace("$key",//
		"item.$key=$key");

		customElements["r-each"].compile = new Function(" return " + nf)();
		
		var handlers = jsPlumbToolkit.Dialogs.handlers;
		
		$.each(["NUMBER"], function(i, v){
			for(var k in handlers){
				handlers[k][v] = handlers[k].TEXT;
			}
		});
		//console.log(handlers);
//		var handlers = {
//	    		clearHandlers : _clearHandlers	,
//	    		getHandlers : _getHandlers,
//	    		setHandlers : _setHandlers
//	    };
		

	}

	var ResizeMain = (function() {
		var func = function() {
			$(".jtk-demo-main").css({
				height : $(window).height()
			});
			$(".node-palette").css({
				height : $(window).height()
			});
			return func;
		};
		return func;
	})();
	$(window).resize(ResizeMain());
})(window.Rotors);

var ToolKitFunc = (function() {
	var func = new Function(), tk, left = -200, top = 70;

	func.GetColumns = function(tables, systemName, fn) {
		if (tables && tables.length) {
			$.getJSON(contextPath + "/data/columns", {
				tables : tables.join(","),
				systemName : systemName
			}, fn);
		}
	};

	func.getToolkit = function() {
		!tk && (tk = renderer.getToolkit());
		return tk;
	};

	func.update = function(tableMsg) {
		
		func.getToolkit().updateNode.call(tableMsg);
	};

	func._left = function() {
		return left += 220;
	};

	func._top = function() {
		return top += 10;
	};

	func.add = function(tableMsg) {

		$.extend(true, tableMsg, {
			left : func._left(),
			top : func._top()
		});

		func.getToolkit().addNode(tableMsg);
	};

	func._each = function(i, v) {
		!v.name && (v.name = v.id);
		var tk = func.getToolkit();
		$.each(v.columns, function(i, v) {
			v.id = v.name;
			v.datatype = v.type;
			v.checked = "";
		});
		if (tk.getNode(v.id)) {
			func.update(v);
		} else {
			func.add(v);
		}
	};

	/**
	 * 新增、修改选择的表格
	 */
	func.addOrUpdate = function(tables, systemName) {
		func.GetColumns(tables, systemName, function(ret) {
			if (ret) {
				$.each(ret, func._each);
			}
		});
	};

	/**
	 * 过滤掉已经删除的或者无用的数据
	 */
	var _filter = function(json) {
		var ports = json.ports, //
		nodes = json.nodes, map = {}, msg;
		if (ports && ports.length) {
			var _eachPort = function(i, v) {
				if (v.id) {
					msg = v.id.split(".");
					!map[msg[0]] && (map[msg[0]] = {});
					map[msg[0]][msg[1]] = v;
				}
			}
			$.each(ports, _eachPort);
		}

		if (nodes && nodes.length) {
			var _eachColumn = function(i, v, m, c) {
				m[v.id] && c.push(v);
				v.$key && (delete v.$key);
			}, _eachNode = function(i, v) {
				var m = map[v.id];
				if (m && v.columns.length) {
					var c = [];
					$.each(v.columns, function(i, v) {
						_eachColumn(i, v, m, c);
					});
					v.columns = c;
				}
			}
			$.each(nodes, _eachNode);
		}
		return json;
	};
	/**
	 * 获取数据
	 */
	func.exportData = function() {
		return _filter(func.getToolkit().exportData());
	};

	/**
	 * 加载数据/还原
	 */
	func.loadData = function() {

	}

	func.reloadData = function(data) {
		try {
			func.getToolkit().clear();
		} finally {
			func.loadData(data);
		}
	};
	func.setParamter = function(data) {
		func.setParam(data);
	};

	func.refresh = function() {
		func.reloadData(func.exportData());
	};

	return func;
})();