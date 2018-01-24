//注册事件定义器中输入输出参数
window.mt = window.mt || {};
window.mt.control = {
	'default': function(id) {
		return [{
			id: id,
			text: 'value',
			type: 'getValue'
		}];
	},
	combobox: function(id) {
		return [{
			id: id,
			text: 'text',
			type: 'getText'
		}, {
			id: id,
			text: 'value',
			type: 'getValue'
		}];
	},
	dropdownlist: function(id) {
		return mt.control.combobox(id);
	},
	metroselect: function(id, pageId) {
		var datas = [{
			id: id,
			text: 'text',
			type: 'getText'
		}, {
			id: id,
			text: 'value',
			type: 'getValue'
		}, {
			text: '数据源',
			items: []
		}];
		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds) {
			$.each(ds, function(i, d) {
				var selectColumns = d.columns;
				if (selectColumns) {
					$.each(selectColumns, function(i, c) {
						c.text = c.title;
						c.type = 'getData';
						c.id = id;
						datas[2].items.push(c);
					})
				}
			})
		}
		return datas;
	},
	metroselectm: function(id, pageId) {
		var datas = [{
			id: id,
			text: 'text',
			type: 'getText',
			columnName: 'text'
		}, {
			id: id,
			text: 'value',
			type: 'getValue',
			columnName: 'value'
		}, {
			text: '数据源',
			items: []
		}];
		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds) {
			$.each(ds, function(i, d) {
				var selectColumns = d.columns;
				if (selectColumns) {
					$.each(selectColumns, function(i, c) {
						c.text = c.title;
						c.type = 'getData';
						c.id = id;
						datas[2].items.push(c);
					})
				}
			})
		}
		return datas;
	},
	selectpicker: function(id, pageId) {
		var datas = [{
			text: '数据源',
			items: [],
		},{
			id: id,
			text: 'text',
			type: 'getText'
		}, {
			id: id,
			text: 'value',
			type: 'getValue'
		},{
			id: id,
			text: '文本框值',
			type: 'getTextBoxValue'
		} ];
		// 获取列
		var ds = getDataSourceSet(id, pageId);
		
		if (ds && ds[0]) {
			datas[0].datasetId = ds[0].datasetId;
			alert
			var columns = ds[0].selectColumns;
			if (columns) {
				$.each(columns, function(i, c) {
					var k = jQuery.extend(true, {}, c);
					k.type = 'getRow';
					k.text = k.title;
					datas[0].items.push(k);		
				});
			}
		}
		return datas;
		return datas;
	},
	phoneListView : function(id,pageId){
		var datas = [{
			text: '数据源',
			items: [],
		}];
		var ds = getDataSourceSet(id, pageId);
				
		if (ds && ds[0]) {
			datas[0].datasetId = ds[0].datasetId;
			alert
			var columns = ds[0].selectColumns;
			if (columns) {
				$.each(columns, function(i, c) {
					var k = jQuery.extend(true, {}, c);
					k.type = 'getAll';
					k.text = k.title;
					datas[0].items.push(k);		
				});
			}
		}
		return datas;
	},
	__grid__: function(id, pageId) {
		var datas = [{
			text: '选中行',
			items: []
		},{
			text: '未选中行',
			items: []
		}, {
			text: '数据源',
			items: []
		}, {
			text: '选中单元格',
			items: [{
				text: '单元格值',
				type: 'getText',
				id: id
			},{
				text: '列名值',
				type: 'getCurColumn',
				id: id
			}]
		}, {
			text: '勾选节点',
			items: []
		}];
		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds && ds[0]) {
			datas[0].datasetId = ds[0].datasetId;
			var columns = ds[0].columns;
			if (columns) {
				$.each(columns, function(i, c) {
					c.text = c.title;
					c.type = 'getRow';
					c.id = id;
					datas[0].items.push(c);
					var b = $.extend(true,{},c);
					b.type = 'getUnselectRow';
					datas[1].items.push(b);
					var k = jQuery.extend(true, {}, c);
					k.type = 'getAll';
					datas[2].items.push(k);
					var f = jQuery.extend(true, {}, c);
					f.type = 'getCheckedRow';
					datas[4].items.push(f);
				});
			}
		}
		return datas;
	},
	
	grid: function(id, pageId) {
		var datas = window.mt.control.__grid__(id, pageId),
			dlen  = datas.length;
		/*
		 * datas.push({ text : '数据源', items : [] });
		 */
		datas.push({
			text: '总记录数',
			id : id,
			type : 'getTotal',
		},{
			text: '点击的当前行',
			items: []
		}, {
			text: '表字段名',
			items:[]
		},{
			text: '是否选中',
			type: 'isChecked',
			id: id
		},{
			text: '是否勾中',
			type: 'isCheckedInput',
			id: id
		});
		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds && ds[0]) {
			var columns = ds[0].columns;
			if (columns) {
				$.each(columns, function(i, c) {
					c.text = c.title;
					c.type = 'getCurRow';
					c.id = id;
					datas[dlen+1].items.push(c);
					var m = jQuery.extend(true, {}, c);
					m.text = m.title;
					m.type = 'getKeyName';
					m.id = id;
					datas[dlen+2].items.push(m);
				});
			}
		}
		
		return datas;
	},
	excelupload : function(id,pageId){
		var datas = [{
			text: '文件信息',
			type: 'getExcelVal',
			id: id
		}];

		return datas;
	},
	treelist: function(id, pageId) {
		var datas = [{
			text: '选中行',
			items: [{
				text: '层级',
				type: 'getGrade',
				id: id
			}]
		},  {
			text: '数据源',
			items: []
		},{
			text: '点击的当前行',
			items: []
		}, {
			text: '选中单元格',
			items: [{
				text: '单元格值',
				type: 'getText',
				id: id
			}]
		}, {
			text: '表字段名',
			items:[]
		}, {
			text: '勾选节点',
			items: []
		}];

		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds && ds[0]) {
			datas[0].datasetId = ds[0].datasetId;
			var columns = ds[0].columns;
			if (columns) {
				$.each(columns, function(i, c) {
					c.text = c.title;
					c.type = 'getRow';
					c.id = id;
					datas[0].items.push(c);
					var k = jQuery.extend(true, {}, c);
					k.type = 'getAll';
					datas[1].items.push(k);
					var p = jQuery.extend(true, {}, c);
					p.text = p.title;
					p.type = 'getCurRow';
					p.id = id;
					datas[2].items.push(p);
					var m = jQuery.extend(true, {}, c);
					m.text = m.title;
					m.type = 'getKeyName';
					m.id = id;
					datas[4].items.push(m);
					var f = jQuery.extend(true, {}, c);
					f.text = f.title;
					f.type = 'getCheckedRow';
					f.id = id;
					datas[5].items.push(f);
				});
			}
		}
		return datas;
	},
	ztree: function(id, pageId) {
		var datas = [{
			text: '选中节点',
			items: []
		}, {
			text: '勾选节点',
			items: []
		}, {
			text: '点击/勾选的当前节点',
			items: []
		}, {
			text: '数据源',
			items: []
		}, {
			text: '获取字段名称',
			items: []
		}, {
			text: '获取层级节点',
			items: [{
					text: '上级条件满足',
					value: "getTreeUpNode(args,'field','criteria','outField',true)",
					code: "getTreeUpNode(args,'field','criteria','outField',true)"
				}, {
					text: '下级条件满足',
					value: "getTreeDownNode(args,'field','exp','outField',true)",
					code: "getTreeDownNode(args,'field','exp','outField',true)"
				}]
		},{
			text: '是否勾选',
			type: 'isChecked',
			id: id
		}];
		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds && ds[0]) {
			var columns = ds[0].columns;
			if (columns) {
				$.each(columns, function(i, c) {
					c.text = c.title;
					c.type = 'getRow';
					c.id = id;
					datas[0].items.push(c);

					var k = jQuery.extend(true, {}, c);
					k.type = 'getChkRow';
					datas[1].items.push(k);

					var j = jQuery.extend(true, {}, c);
					j.type = 'getCurRow';
					datas[2].items.push(j);

					var d = jQuery.extend(true, {}, c);
					d.type = 'getAll';
					datas[3].items.push(d);

					var q = jQuery.extend(true, {}, c);
					q.code = q.columnLabel;
					q.value = q.columnLabel;
					q.type = "getKeyName";
					q.text = c.title;
					datas[4].items.push(q);
				});
			}
		}
		return datas;
	},
	mapext: function(id, pageId) {
		var datas = [{
			text: '点击节点',
			items: []
		}];

		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds) {
			$.each(ds, function(i, d) {
				var selectColumns = d.selectColumns;
				if (selectColumns) {
					$.each(selectColumns, function(i, c) {
						c.text = c.seq + "." + c.datasetName + "." + c.title;
						c.type = 'getClickNode';
						c.id = id;
						datas[0].items.push(c);
					});
					datas[0].items.push({
						columnName: 'seriesName',
						title: '图例名称',
						text: '图例名称',
						type: 'getClickNode',
						id: id
					});
				}
			});
		}
		return datas;
	},
	charts: function(id, pageId) {
		var datas = [{
			text: '选中节点',
			items: []
		}];

		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds) {
			$.each(ds, function(i, d) {
				var selectColumns = d.selectColumns;
				if (selectColumns) {
					$.each(selectColumns, function(i, c) {
						c.text = c.seq + "." + c.datasetName + "." + c.title;
						c.type = 'getRow';
						c.id = id;
						datas[0].items.push(c);
					});
					datas[0].items.push({
						FieldLength: 200,
						FieldType: 'string',
						code: '~F{categories.categories.categories}',
						columnName: 'categories',
						columnLabel: 'categories',
						title: '系列名称',
						value: '~F{系列名称}',
						seq: '',
						text: '系列名称',
						type: 'getRow',
						id: id
					});
				}
			});
		}
		return datas;
	},
	page: function(id, pageId) {
		var datas = [{
			text: '页面数据集',
			items: []
		}];
		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds && ds[0]) {
			datas[0].datasetId = ds[0].datasetId;
			var columns = ds[0].columns;
			if (columns) {
				$.each(columns, function(i, c) {
					c.text = c.title;
					c.type = 'getRow';
					c.id = id;
					datas[0].items.push(c);
				});
			}
		}
		return datas;
	},
	diagram: function(id, pageId) {
		var datas = [{
			text: '选中节点',
			items: []
		}];

		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds) {
			$.each(ds, function(i, d) {
				var selectColumns = d.columns;
				if (selectColumns) {
					$.each(selectColumns, function(i, c) {
						console.log(JSON.stringify(c));

						var columnName = c.columnName;
						c.text = c.title;
						c.type = 'getNode';
						c.id = id;
						c[columnName] = columnName;
						datas[0].items.push(c);
					});
				}
			});
		}
		return datas;
	},
	jsgis: function(id, pageId) {
		var datas = [{
			text: '画图触发',
			items: [{
				text: '坐标信息',
				type: 'getCoord',
				id: id
			}]
		}, {
			text: '选中图形',
			items: []
		}, {
			text: '表字段名',
			items:[]
		}];

		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds) {
			$.each(ds, function(i, d) {
				var selectColumns = d.selectColumns;
				if (selectColumns) {
					$.each(selectColumns, function(i, c) {
						c.text = c.datasetName + "." + c.title;
						c.type = 'getNode';
						c.id = id;
						datas[1].items.push(c);

						var m = jQuery.extend(true, {}, c);
						m.text = m.title;
						m.type = 'getKeyName';
						m.id = id;
						datas[2].items.push(m);
					});
				}
			});
		}
		return datas;
	},
	calendarbt: function(id, pageId) {
		var datas = [{
			text: '选中日程',
			items: []
		}, {
			text: '选中日历',
			items: [{
				id: id,
				text: '获取日期',
				type: 'getDate'
			}]
		}];

		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds) {
			$.each(ds, function(i, d) {
				var selectColumns = d.selectColumns;
				if (selectColumns) {
					$.each(selectColumns, function(i, c) {
						// console.log(JSON.stringify(c));

						c.text = c.title;
						c.type = 'getSource';
						c.id = id;
						datas[0].items.push(c);

					})
				}
			})
		}
		return datas;
	},
	megamenu: function(id, pageId) {
		var datas = [{
			text: '数据源',
			items: []
		}, {
			text: '固定数据源',
			items: [{
				id: id,
				text: '菜单名称',
				type: 'getMenu',
				columnName: 'name'
			}, {
				id: id,
				text: '菜单图标',
				type: 'getMenu',
				columnName: 'icon'
			}, {
				id: id,
				text: '菜单链接',
				type: 'getMenu',
				columnName: 'url'
			}]
		}, {
			text: '系统菜单',
			items: [{
				id: id,
				text: '菜单ID',
				type: 'getMenu',
				columnName: 'id'
			}, {
				id: id,
				text: '菜单父ID',
				type: 'getMenu',
				columnName: 'parentId'
			}, {
				id: id,
				text: '菜单名称',
				type: 'getMenu',
				columnName: 'name'
			}, {
				id: id,
				text: '菜单图标',
				type: 'getMenu',
				columnName: 'icon'
			}, {
				id: id,
				text: '菜单链接',
				type: 'getMenu',
				columnName: 'url'
			}]
		}];
		/*
		 * ,{ text : '固定数据源', items : [{ id : id, text : '菜单名称', type :
		 * 'getMenu', columnName : 'name' }, { id : id, text : '菜单图标', type :
		 * 'getMenu', columnName : 'icon' },{ id : id, text : '菜单链接', type :
		 * 'getMenu', columnName : 'url' }] }
		 */
		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds) {
			$.each(ds, function(i, d) {
				var selectColumns = d.columns;
				if (selectColumns) {
					$.each(selectColumns, function(i, c) {
						c.text = c.title;
						c.type = 'getMenu';
						c.id = id;
						datas[0].items.push(c);

					})
				}
			})
		}
		return datas;
	},
	step: function(id, pageId) {
		var datas = [{
			text: '数据源',
			items: []
		}, {
			text: '固定数据源',
			items: [{
				id: id,
				text: 'id',
				type: 'getData',
				columnName: 'id'
			}, {
				id: id,
				text: '编号',
				type: 'getData',
				columnName: 'index'
			}, {
				id: id,
				text: '序号值',
				type: 'getData',
				columnName: 'number'
			}, {
				id: id,
				text: '内容区',
				type: 'getData',
				columnName: 'content'
			}]
		}];
		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds) {
			$.each(ds, function(i, d) {
				var selectColumns = d.columns;
				if (selectColumns) {
					$.each(selectColumns, function(i, c) {
						c.text = c.title;
						c.type = 'getData';
						c.id = id;
						datas[0].items.push(c);
					})
				}
			})
		}
		return datas;
	},
	definedTable: function(id, pageId) {
		var datas = window.mt.control.__grid__(id, pageId),
			dlen  = datas.length;
		/*
		 * datas.push({ text : '数据源', items : [] });
		 */
		datas.push({
			text: '第一行',
			items: []
		}, {
			text: '所有数据',
			items: []
		},{
			id: id,
			text: '获取当前页数',
			type: 'getPage'
		});
		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds && ds[0]) {
			var columns = ds[0].columns;
			if (columns) {
				$.each(columns, function(i, c) {
					c.text = c.title;
					c.type = 'getFirstRow';
					c.id = id;
					datas[dlen].items.push(c);
					var k = jQuery.extend(true, {}, c);
					k.type = 'getAllRows';
					datas[dlen+1].items.push(k);
				});
			}
		}
		return datas;
	},
	radio: function(id) {
		return [{
			id: id,
			text: '是否被选中',
			type: 'isChecked'
		}, {
			id: id,
			text: '获取当前值',
			type: 'getValue'
		}, {
			id: id,
			text: '获取选中值',
			type: 'getSelect'
		}];
	},
	a: function(id) {
		return [{
			id: id,
			text: '获取真实值',
			type: 'getActVal'
		}, {
			id: id,
			text: '获取文本',
			type: 'getValue'
		}];
	},
	icheckradio: function(id, pageId) {
		var datas = [{
			id: id,
			text: '获取文本',
			type: 'getText'
		}, {
			id: id,
			text: '获取真实值',
			type: 'getValue'
		}, {
			text: '数据源',
			items: []
		}];
		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds) {
			$.each(ds, function(i, d) {
				var selectColumns = d.columns;
				if (selectColumns) {
					$.each(selectColumns, function(i, c) {
						c.text = c.title;
						c.type = 'getData';
						c.id = id;
						datas[2].items.push(c);
					})
				}
			})
		}
		return datas;
	},
	icheckbox: function(id, pageId) {
		var datas = [{
			id: id,
			text: '获取文本',
			type: 'getText'
		}, {
			id: id,
			text: '获取真实值',
			type: 'getValue'
		}, {
			text: '数据源',
			items: []
		}, {
			text: '表字段名',
			items:[]
		}];
		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds) {
			$.each(ds, function(i, d) {
				var selectColumns = d.columns;
				if (selectColumns) {
					$.each(selectColumns, function(i, c) {
						c.text = c.title;
						c.type = 'getData';
						c.id = id;
						datas[2].items.push(c);
						
						var m = jQuery.extend(true, {}, c);
						m.text = m.title;
						m.type = 'getKeyName';
						m.id = id;
						datas[3].items.push(m);
					})
				}
			})
		}
		return datas;
	},
	listview: function(id, pageId) {
		var datas = [{
			text: '选中格',
			items: []
		}];
		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds && ds[0]) {
			var columns = ds[0].columns;
			if (columns) {
				$.each(columns, function(i, c) {
					c.text = c.title;
					c.type = 'getRow';
					c.id = id;
					datas[0].items.push(c);
				});
			}
		}
		return datas;
	},
	treelistbt: function(id, pageId) {
		var datas = window.mt.control.treelist(id, pageId);
		return datas;
	},
	gridbt: function(id, pageId) {
		var datas = window.mt.control.grid(id, pageId);
		return datas;
	},
	gantt: function(id, pageId) {
		var datas = window.mt.control.__grid__(id, pageId);
		return datas;
	},
	'touchspin': function(id) {
		return [{
			id: id,
			text: 'value',
			type: 'getValue'
		}, {
			id: id,
			text: '变长列统计值',
			type: 'getSumValue'
		}];
	},
	phoneTimePicker : function(id){
		return [{
			id: id,
			text: 'value',
			type: 'getValue'
		}];
	},
	gridList: function(id, pageId) {
		var datas = window.mt.control.gridlist(id, pageId);
		return datas;
	},
	gridlist: function(id, pageId) {
		var datas = [{
			text: '选中格',
			items: [{
				text : '系统内置属性（唯一序号）',
				type : 'getClickCell',
				id : id,
				columnName : 'cell-index'
			}]
		}, {
			text: '数据源',
			items: [{
				text : '系统内置属性（唯一序号）',
				type : 'getAll',
				id : id,
				columnName : 'cell-index'
			}]
		},{
			text: '拖拽格',
			items:[{
				text : '系统内置属性（唯一序号）',
				type : 'getDrapRow',
				id : id,
				columnName : 'cell-index'
			}]
		},{
			text: '拖入格',
			items:[{
				text : '系统内置属性（唯一序号）',
				type : 'getDropRow',
				id : id,
				columnName : 'cell-index'
			}]
		},{
			text: '移入格',
			items:[{
				text : '系统内置属性（唯一序号）',
				type : 'getOverRow',
				id : id,
				columnName : 'cell-index'
			}]
		},{
			text: '表字段名',
			items:[{
				text : '系统内置属性（唯一序号）',
				type : 'getKeyName',
				id : id,
				columnName : 'cell-index'
			}]
		},{
			text: '单击格',
			items: [{
				text : '系统内置属性（唯一序号）',
				type : 'getNowClickCell',
				id : id,
				columnName : 'cell-index'
			}]
		},{
			text: '双击格',
			items: [{
				text : '系统内置属性（唯一序号）',
				type : 'getNowDblClickCell',
				id : id,
				columnName : 'cell-index'
			}]
		},{
			text: '是否选中',
			type: 'isChecked',
			id: id
		}];

		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds && ds[0]) {
			datas[0].datasetId = ds[0].datasetId;
			var columns = ds[0].columns;
			if (columns) {
				$.each(columns, function(i, c) {
					c.text = c.title;
					c.type = 'getClickCell';
					c.id = id;
					datas[0].items.push(c);
					var k = jQuery.extend(true, {}, c);
					k.type = 'getAll';
					datas[1].items.push(k);
					var w = jQuery.extend(true, {}, c);
					w.type = 'getDrapRow';
					datas[2].items.push(w);
					var h = jQuery.extend(true, {}, c);
					h.type = 'getDropRow';
					datas[3].items.push(h);
					var l = jQuery.extend(true, {}, c);
					l.type = 'getOverRow';
					datas[4].items.push(l);
					var p = jQuery.extend(true, {}, c);
					p.type = 'getKeyName';
					datas[5].items.push(p);
					var m = jQuery.extend(true, {}, c);
					m.type = 'getNowClickCell';
					datas[6].items.push(m);
					var b = jQuery.extend(true, {}, c);
					b.type = 'getNowDblClickCell';
					datas[7].items.push(b);
				});
			}
		}
		return datas;
	},
	imageupload: function(id,pageId){
		var datas = [{
			id: id,
			text: '图片ID',
			type: 'getValue'
		},{
			id: id,
			text: '附件信息',
			type: 'getRandomparent'
		}, {
			text: '图片基础数据',
			items: [{id:id,columnName:'ExifVersion'	,	text:'Exif 版本',	type:'getBasePicData'},
				{id:id,columnName:'FlashPixVersion'	,	text:'FlashPix 版本',	type:'getBasePicData'},
				{id:id,columnName:'ColorSpace'	,	text:'色域、色彩空间',	type:'getBasePicData'},
				{id:id,columnName:'PixelXDimension'	,	text:'图像的有效宽度',	type:'getBasePicData'},
				{id:id,columnName:'PixelYDimension'	,	text:'图像的有效高度',	type:'getBasePicData'},
				{id:id,columnName:'ComponentsConfiguration'	,	text:'图像构造',	type:'getBasePicData'},
				{id:id,columnName:'CompressedBitsPerPixel'	,	text:'压缩时每像素色彩位',	type:'getBasePicData'},
				{id:id,columnName:'MakerNote'	,	text:'制造商设置的信息',	type:'getBasePicData'},
				{id:id,columnName:'UserComment'	,	text:'用户评论',	type:'getBasePicData'},
				{id:id,columnName:'RelatedSoundFile'	,	text:'关联的声音文件',	type:'getBasePicData'},
				{id:id,columnName:'DateTimeOriginal'	,	text:'创建时间',	type:'getBasePicData'},
				{id:id,columnName:'DateTimeDigitized'	,	text:'数字化创建时间',	type:'getBasePicData'},
				{id:id,columnName:'SubsecTime'	,	text:'日期时间（秒）',	type:'getBasePicData'},
				{id:id,columnName:'SubsecTimeOriginal'	,	text:'原始日期时间（秒）',	type:'getBasePicData'},
				{id:id,columnName:'SubsecTimeDigitized'	,	text:'原始日期时间数字化（秒）',	type:'getBasePicData'},
				{id:id,columnName:'ExposureTime'	,	text:'曝光时间',	type:'getBasePicData'},
				{id:id,columnName:'FNumber'	,	text:'光圈值',	type:'getBasePicData'},
				{id:id,columnName:'ExposureProgram'	,	text:'曝光程序',	type:'getBasePicData'},
				{id:id,columnName:'SpectralSensitivity'	,	text:'光谱灵敏度',	type:'getBasePicData'},
				{id:id,columnName:'ISOSpeedRatings'	,	text:'感光度',	type:'getBasePicData'},
				{id:id,columnName:'OECF'	,	text:'光电转换功能',	type:'getBasePicData'},
				{id:id,columnName:'ShutterSpeedValue'	,	text:'快门速度',	type:'getBasePicData'},
				{id:id,columnName:'ApertureValue'	,	text:'镜头光圈',	type:'getBasePicData'},
				{id:id,columnName:'BrightnessValue'	,	text:'亮度',	type:'getBasePicData'},
				{id:id,columnName:'ExposureBiasValue'	,	text:'曝光补偿',	type:'getBasePicData'},
				{id:id,columnName:'MaxApertureValue'	,	text:'最大光圈',	type:'getBasePicData'},
				{id:id,columnName:'SubjectDistance'	,	text:'物距',	type:'getBasePicData'},
				{id:id,columnName:'MeteringMode'	,	text:'测光方式',	type:'getBasePicData'},
				{id:id,columnName:'Lightsource'	,	text:'光源',	type:'getBasePicData'},
				{id:id,columnName:'Flash'	,	text:'闪光灯',	type:'getBasePicData'},
				{id:id,columnName:'SubjectArea'	,	text:'主体区域',	type:'getBasePicData'},
				{id:id,columnName:'FocalLength'	,	text:'焦距',	type:'getBasePicData'},
				{id:id,columnName:'FlashEnergy'	,	text:'闪光灯强度',	type:'getBasePicData'},
				{id:id,columnName:'SpatialFrequencyResponse'	,	text:'空间频率反应',	type:'getBasePicData'},
				{id:id,columnName:'FocalPlaneXResolution'	,	text:'焦距平面X轴解析度',	type:'getBasePicData'},
				{id:id,columnName:'FocalPlaneYResolution'	,	text:'焦距平面Y轴解析度',	type:'getBasePicData'},
				{id:id,columnName:'FocalPlaneResolutionUnit'	,	text:'焦距平面解析度单位',	type:'getBasePicData'},
				{id:id,columnName:'SubjectLocation'	,	text:'主体位置',	type:'getBasePicData'},
				{id:id,columnName:'ExposureIndex'	,	text:'曝光指数',	type:'getBasePicData'},
				{id:id,columnName:'SensingMethod'	,	text:'图像传感器类型',	type:'getBasePicData'},
				{id:id,columnName:'FileSource'	,	text:'源文件',	type:'getBasePicData'},
				{id:id,columnName:'SceneType'	,	text:'场景类型（1 == 直接拍摄）',	type:'getBasePicData'},
				{id:id,columnName:'CFAPattern'	,	text:'CFA 模式',	type:'getBasePicData'},
				{id:id,columnName:'CustomRendered'	,	text:'自定义图像处理',	type:'getBasePicData'},
				{id:id,columnName:'ExposureMode'	,	text:'曝光模式',	type:'getBasePicData'},
				{id:id,columnName:'WhiteBalance'	,	text:'白平衡（1 == 自动，2 == 手动）',	type:'getBasePicData'},
				{id:id,columnName:'DigitalZoomRation'	,	text:'数字变焦',	type:'getBasePicData'},
				{id:id,columnName:'FocalLengthIn35mmFilm'	,	text:'35毫米胶片焦距',	type:'getBasePicData'},
				{id:id,columnName:'SceneCaptureType'	,	text:'场景拍摄类型',	type:'getBasePicData'},
				{id:id,columnName:'GainControl'	,	text:'场景控制',	type:'getBasePicData'},
				{id:id,columnName:'Contrast'	,	text:'对比度',	type:'getBasePicData'},
				{id:id,columnName:'Saturation'	,	text:'饱和度',	type:'getBasePicData'},
				{id:id,columnName:'Sharpness'	,	text:'锐度',	type:'getBasePicData'},
				{id:id,columnName:'DeviceSettingDescription'	,	text:'设备设定描述',	type:'getBasePicData'},
				{id:id,columnName:'SubjectDistanceRange'	,	text:'主体距离范围',	type:'getBasePicData'},
				{id:id,columnName:'InteroperabilityIFDPointer'	,	text:'',	type:'getBasePicData'},
				{id:id,columnName:'ImageUniqueID'	,	text:'图像唯一ID',	type:'getBasePicData'},
			]
		}];
		
		return datas;
	},
	bim : function(id, pageId){
		return [{
			text: '选中节点',
			items: [{
				text: '节点名称',
				type: 'getData',
				id: id,
				columnName : 'name'
			},{
				text: '节点ID',
				type: 'getData',
				id: id,
				columnName : 'id'
			},{
				text: '节点类型',
				type: 'getData',
				id: id,
				columnName : 'nodeType'
			},{
				text: '父节点ID',
				type: 'getData',
				id: id,
				columnName : 'parentId'
			},{
				text: '模型唯一码',
				type: 'getData',
				id: id,
				columnName : '_documentId'
			}]
		}]
	},
	bootstraptabs : function(id,pageId){
		return [{
			id: id,
			text: '激活的选卡名称',
			type: 'getActivedValue'
		},{
			id: id,
			text: '激活的选卡序号',
			type: 'getActivedIndex'
		}]
	},
	bootstrapdialog : function(id,pageId){
		return [{
			id: id,
			text: '窗口标题',
			type: '_getCurName'
		},{
			id: id,
			text: '高度',
			type: '_getcWidth'
		},{
			id: id,
			text: '宽度',
			type: '_getcHeight'
		}]
	},
	definedcard : function(id,pageId){
		var datas = [{
			text: '选中格',
			items: []
		}, {
			text: '数据源',
			items: []
		}];

		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds && ds[0]) {
			datas[0].datasetId = ds[0].datasetId;
			var columns = ds[0].columns;
			if (columns) {
				$.each(columns, function(i, c) {
					c.text = c.title;
					c.type = 'getSelectRow';
					c.id = id;
					datas[0].items.push(c);
					var k = jQuery.extend(true, {}, c);
					k.type = 'getAll';
					datas[1].items.push(k);
				});
			}
		}
		return datas;
	},
	ratylimaster : function(id,pageId){
		var datas = [{
			id: id,
			text: '获取评分值',
			type: 'getRatyValue'
		}];
		return datas;
	},
	metrolist : function(id,pageId){
		var datas = [{
			text: '拖拽行',
			items:[]
		}];
		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds) {
			$.each(ds, function(i, d) {
				var selectColumns = d.columns;
				if (selectColumns) {
					$.each(selectColumns, function(i, c) {
						c.text = c.title;
						c.type = 'getDrapRow';
						c.id = id;
						datas[0].items.push(c);
					})
				}
			})
		}
		return datas;
	},
	nestable: function(id, pageId) {
		var datas = [{
			text: '拖拽格',
			items: []
		}];

		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds && ds[0]) {
			datas[0].datasetId = ds[0].datasetId;
			var columns = ds[0].columns;
			if (columns) {
				$.each(columns, function(i, c) {
					c.text = c.title;
					c.type = 'getNowNest';
					c.id = id;
					datas[0].items.push(c);
					
				});
			}
		}
		return datas;
	},
	diagrambt : function(id,pageId){
		var datas = [{
			text: '选中节点',
			items:[]
		},{
			text: '表字段名',
			items:[{
				text : '系统内置属性（唯一序号）',
				type : 'getKeyName',
				id : id,
				columnName : 'cell-index'
			}]
		}];
		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds) {
			$.each(ds, function(i, d) {
				var selectColumns = d.selectColumns;
				if (selectColumns) {
					$.each(selectColumns, function(i, c) {
						c.text = c.title;
						c.type = 'getFocusNodeData';
						c.id = id;
						datas[0].items.push(c);
						var p = jQuery.extend(true, {}, c);
						p.type = 'getKeyName';
						datas[1].items.push(p);
					})
				}
			})
		}
		return datas;
	},
	rangeSlider : function(id,pageId){
		var datas = [{
			id: id,
			text: '获取进度条的最小值',
			type: 'getLeftValue'
		},{
			id: id,
			text: '获取进度条的最大值',
			type: 'getRightValue'
		}];
		return datas;
	},
	definedpanel : function(id,pageId){
		var datas = [{
			text: '数据源',
			items: []
		}, {
			text: '系统菜单',
			items: [{
				id: id,
				text: '菜单ID',
				type: 'getSelectedData',
				columnName: 'id'
			}, {
				id: id,
				text: '菜单父ID',
				type: 'getSelectedData',
				columnName: 'parentId'
			}, {
				id: id,
				text: '菜单名称',
				type: 'getSelectedData',
				columnName: 'name'
			}, {
				id: id,
				text: '菜单图标',
				type: 'getSelectedData',
				columnName: 'icon'
			}, {
				id: id,
				text: '菜单链接',
				type: 'getSelectedData',
				columnName: 'url'
			}]
		}];
		/*
		 * ,{ text : '固定数据源', items : [{ id : id, text : '菜单名称', type :
		 * 'getMenu', columnName : 'name' }, { id : id, text : '菜单图标', type :
		 * 'getMenu', columnName : 'icon' },{ id : id, text : '菜单链接', type :
		 * 'getMenu', columnName : 'url' }] }
		 */
		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds) {
			$.each(ds, function(i, d) {
				var selectColumns = d.columns;
				if (selectColumns) {
					$.each(selectColumns, function(i, c) {
						c.text = c.title;
						c.type = 'getSelectedData';
						c.id = id;
						datas[0].items.push(c);

					})
				}
			})
		}
		return datas;
	},
	bmapmarker : function(id,pageId){
		var datas = [{
			text: '数据源',
			items: []
		}, {
			text: '基础信息',
			items: [{
				id: id,
				text: '省',
				type: 'getSelectedAddress',
				columnName: 'province'
			},{
				id: id,
				text: '市',
				type: 'getSelectedAddress',
				columnName: 'city'
			},{
				id: id,
				text: '区',
				type: 'getSelectedAddress',
				columnName: 'district'
			},{
				id: id,
				text: '街',
				type: 'getSelectedAddress',
				columnName: 'street'
			},{
				id: id,
				text: '街号',
				type: 'getSelectedAddress',
				columnName: 'streetNumber'
			},{
				id: id,
				text: '地址',
				type: 'getSelectedAddress',
				columnName: 'address'
			},{
				id: id,
				text: '电话号码',
				type: 'getSelectedAddress',
				columnName: 'phoneNumber'
			},{
				id: id,
				text: '邮政编码',
				type: 'getSelectedAddress',
				columnName: 'postcode'
			},{
				id: id,
				text: '经度',
				type: 'getSelectedAddress',
				columnName: 'lng'
			},{
				id: id,
				text: '纬度',
				type: 'getSelectedAddress',
				columnName: 'lat'
			}]
		}];
		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds && ds[0]) {
			datas[0].datasetId = ds[0].datasetId;
			var columns = ds[0].selectColumns;
			if (columns) {
				$.each(columns, function(i, c) {
					c.text = c.title;
					c.type = 'getClickMarkerData';
					c.id = id;
					datas[0].items.push(c);
				});
			}
		}

		return datas;
	},
	bmapext : function(id,pageId){
		var datas = [{
			text: '数据源',
			items: []
		}, {
			text: '基础信息',
			items: [{
				id: id,
				text: '定位信息',
				type: 'getLocationMarkerPosition',
				columnName: 'id'
			},
			{
				id: id,
				text: '详细信息',
				type: 'getMarkerAddress',
				columnName: 'id'
			},
			{
				id: id,
				text: '地点描述',
				type: 'getAddressTitle',
				columnName: 'id'
			},{
				id: id,
				text: '省',
				type: 'getAddress',
				columnName: 'province'
			},{
				id: id,
				text: '市',
				type: 'getAddress',
				columnName: 'city'
			},{
				id: id,
				text: '区',
				type: 'getAddress',
				columnName: 'district'
			},{
				id: id,
				text: '街',
				type: 'getAddress',
				columnName: 'street'
			},{
				id: id,
				text: '街号',
				type: 'getAddress',
				columnName: 'streetNumber'
			},{
				id: id,
				text: '地址',
				type: 'getAddress',
				columnName: 'address'
			},{
				id: id,
				text: '电话号码',
				type: 'getAddress',
				columnName: 'phoneNumber'
			},{
				id: id,
				text: '邮政编码',
				type: 'getAddress',
				columnName: 'postcode'
			},{
				id: id,
				text: '经度',
				type: 'getAddress',
				columnName: 'lng'
			},{
				id: id,
				text: '纬度',
				type: 'getAddress',
				columnName: 'lat'
			}]
		}];
		return datas;
	},
	bmapddext : function(id,pageId){
		var datas = [{
			text: '数据源',
			items: []
		}, {
			text: '基础信息',
			items: [{
				id: id,
				text: '定位信息',
				type: 'getLocationMarkerPosition',
				columnName: 'id'
			},
			{
				id: id,
				text: '详细信息',
				type: 'getMarkerAddress',
				columnName: 'id'
			},
			{
				id: id,
				text: '地点描述',
				type: 'getAddressTitle',
				columnName: 'id'
			},{
				id: id,
				text: '省',
				type: 'getAddress',
				columnName: 'province'
			},{
				id: id,
				text: '市',
				type: 'getAddress',
				columnName: 'city'
			},{
				id: id,
				text: '区',
				type: 'getAddress',
				columnName: 'district'
			},{
				id: id,
				text: '街',
				type: 'getAddress',
				columnName: 'street'
			},{
				id: id,
				text: '街号',
				type: 'getAddress',
				columnName: 'streetNumber'
			},{
				id: id,
				text: '地址',
				type: 'getAddress',
				columnName: 'address'
			},{
				id: id,
				text: '电话号码',
				type: 'getAddress',
				columnName: 'phoneNumber'
			},{
				id: id,
				text: '邮政编码',
				type: 'getAddress',
				columnName: 'postcode'
			},{
				id: id,
				text: '经度',
				type: 'getAddress',
				columnName: 'lng'
			},{
				id: id,
				text: '纬度',
				type: 'getAddress',
				columnName: 'lat'
			}]
		}];
		return datas;
	},
	reviewArea : function(id,pageId){
		var datas = [{
			text: '点击行',
			items: []
		}, {
			text: '数据源',
			items: [{
				text : '系统内置属性（唯一序号）',
				type : 'getAll',
				id : id,
				columnName : 'cell-index'
			}]
		},{
			text: '表字段名',
			items:[]
		}];

		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds && ds[0]) {
			datas[0].datasetId = ds[0].datasetId;
			var columns = ds[0].columns;
			if (columns) {
				$.each(columns, function(i, c) {
					c.text = c.title;
					c.type = 'getClickRowData';
					c.id = id;
					datas[0].items.push(c);
					var k = jQuery.extend(true, {}, c);
					k.type = 'getAllData';
					datas[1].items.push(k);
					
					var m = jQuery.extend(true, {}, c);
					m.text = m.title;
					m.type = 'getKeyName';
					m.id = id;
					datas[2].items.push(m);
				});
			}
		}
		return datas;
	},
	scan: function(id, pageId) {
		var datas = [{
			id: id,
			text: '获取ID',
			type: 'getValue'
		}, {
			id: id,
			text: '获取父ID',
			type: 'getValue'
		}];
		return datas;
	},
	modelbim: function(id, pageId) {
		var datas = [{
			text: '选中节点',
			items: [{
				text: '模型ID',
				type: 'getNodeId',
				id: id
			}]
		}];

		return datas;
	},
	echarts: function(id, pageId) {
		var datas = [{
			text: '参数名',
			type : 'getParamName',
			id : id
		},{
			text : "参数值",
			type : 'getParamVal',
			id : id
		}];

		return datas;
	},
	editor: function(id, pageId) {
		var datas = [{
			text: 'value',
			type : 'getValue',
			id : id
		},{
			text : "获取纯文本",
			type : 'getValueTxt',
			id : id
		}];

		return datas;
	},
	webchat: function(id, pageId) {
		var datas = [{
			text: '提示信息',
			items : [{
				text : '当前用户名称',
				id : id,
				type : 'getUserName'
			},{
				text : '在线聊天人数',
				id : id,
				type : 'getOnlineCount'
			}]
		},{
			text : "会话信息",
			items : [{
				text : '会话用户',
				id : id,
				type : 'getChatUser'
			},{
				text : '会话用户内容',
				id : id,
				type : 'getChatUserMessage'
			}]
		},{
			text : "在线用户",
			items : [{
				text : '在线用户信息',
				id : id,
				type : 'getOnlineUser'
			}]
		}];

		return datas;
	},
	dhxgantt : function(id,pageId){
		var datas = [{
			text: '选中行',
			items: []
		}, {
			text: '数据源',
			items: []
		},{
			text: '表字段名',
			items:[]
		}];

		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds && ds[0]) {
			datas[0].datasetId = ds[0].datasetId;
			var columns = ds[0].columns;
			if (columns) {
				$.each(columns, function(i, c) {
					c.text = c.title;
					c.type = 'getSelectedData';
					c.id = id;
					datas[0].items.push(c);
					var k = jQuery.extend(true, {}, c);
					k.type = 'getAllData';
					datas[1].items.push(k);
					
					var m = jQuery.extend(true, {}, c);
					m.text = m.title;
					m.type = 'getKeyName';
					m.id = id;
					datas[2].items.push(m);
				});
			}
		}
		return datas;
	},
	levellist : function(id,pageId){
		var datas = [{
			text: '选中行',
			items: [{
				text : '系统内置属性(开关值)',
				type : 'getSelectedData',
				id : id,
				columnName : 'switchValue'
			}]
		}, {
			text: '数据源',
			items: [{
				text : '系统内置属性(开关值)',
				type : 'getAllData',
				id : id,
				columnName : 'switchValue'
			}]
		},{
			text: '表字段名',
			items:[]
		}];

		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds && ds[0]) {
			datas[0].datasetId = ds[0].datasetId;
			var columns = ds[0].columns;
			if (columns) {
				$.each(columns, function(i, c) {
					c.text = c.title;
					c.type = 'getSelectedData';
					c.id = id;
					datas[0].items.push(c);
					var k = jQuery.extend(true, {}, c);
					k.type = 'getAllData';
					datas[1].items.push(k);
					
					var m = jQuery.extend(true, {}, c);
					m.text = m.title;
					m.type = 'getKeyName';
					m.id = id;
					datas[2].items.push(m);
				});
			}
		}
		return datas;
	},
	foldlist : function(id,pageId){
		var datas = [{
			text: '选中行',
			items: [{
				text : '系统内置属性(开关值)',
				type : 'getSelectedData',
				id : id,
				columnName : 'switchValue'
			}]
		}, {
			text: '数据源',
			items: [{
				text : '系统内置属性(开关值)',
				type : 'getAllData',
				id : id,
				columnName : 'switchValue'
			}]
		},{
			text: '表字段名',
			items:[]
		}];

		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds && ds[0]) {
			datas[0].datasetId = ds[0].datasetId;
			var columns = ds[0].columns;
			if (columns) {
				$.each(columns, function(i, c) {
					c.text = c.title;
					c.type = 'getSelectedData';
					c.id = id;
					datas[0].items.push(c);
					var k = jQuery.extend(true, {}, c);
					k.type = 'getAllData';
					datas[1].items.push(k);
					
					var m = jQuery.extend(true, {}, c);
					m.text = m.title;
					m.type = 'getKeyName';
					m.id = id;
					datas[2].items.push(m);
				});
			}
		}
		return datas;
	},
	vectorDraw: function(id, pageId) {
		
		var datas = [{
			text: '选中数据',
			items: []
		},{
			text: '获取x坐标',
			type : 'getX',
			id : id
		},{
			text : "获取Y坐标",
			type : 'getY',
			id : id
		}];
		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds && ds[0]) {
			datas[0].datasetId = ds[0].datasetId;
			var columns = ds[0].columns;
			if (columns) {
				$.each(columns, function(i, c) {
					c.text = c.title;
					c.type = 'getSelectItem';
					c.id = id;
					datas[0].items.push(c);
					
				});
			}
		}
		return datas;
	},
    medialist: function(id, pageId) {
		
		var datas = [{
			text: '当前选择数据',
			items: []
		}];
		// 获取列
		var ds = getDataSourceSet(id, pageId);
		if (ds && ds[0]) {
			datas[0].datasetId = ds[0].datasetId;
			var columns = ds[0].columns;
			if (columns) {
				$.each(columns, function(i, c) {
					c.text = c.title;
					c.type = 'getSelectItem';
					c.id = id;
					datas[0].items.push(c);
					
				});
			}
		}
		return datas;
	},
	imagesext: function(id,pageId){
		var datas = [{
			id: id,
			text: '附件信息',
			type: 'getRandomparent'
		}]
	}
};

$.extend(mt.control, {
	textbox: mt.control["default"],
	textboxbt: mt.control["default"]
});

function getControlParams(type, id, pageId) {
	if (!(type in mt.control))
		type = 'default';
	var f = mt.control[type];
	if ($.isFunction(f)) {
		return f(id, pageId);
	} else {
		return f;
	}
}
(function(fn) {
	if (!fn) {
		window.getDataSourceSet = function(id, pageId) {
			var datas = null;
			$.ajax({
				url: contextPath + '/mx/form/defined/getDataSourceSet',
				data: {
					pageId: pageId,
					name: id
				},
				dataType: 'JSON',
				type: 'post',
				async: false,
				success: function(data) {
					datas = data;
				}
			});
			return datas;
		}
	}
})(window.getDataSourceSet);