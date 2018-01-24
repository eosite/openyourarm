(function() {


	jsPlumbToolkit
			.ready(function() {

				// ------------------------ toolkit setup
				// ------------------------------------

				// This function is what the toolkit will use to get an ID from
				// a node.
				var idFunction = function(n) {
					return n.id;
				};

				// This function is what the toolkit will use to get the
				// associated type from a node.
				var typeFunction = function(n) {
					return n.type;
				};

				// get the various dom elements
				var mainElement = document.querySelector("#jtk-demo-dbase"), canvasElement = mainElement
						.querySelector(".jtk-demo-canvas"), miniviewElement = mainElement
						.querySelector(".miniview"), nodePalette = mainElement
						.querySelector(".node-btn"), controls = mainElement
						.querySelector(".controls");

				// Declare an instance of the Toolkit, and supply the functions
				// we will use to get ids and types from nodes.
				var toolkit = jsPlumbToolkit.newInstance({
					idFunction : idFunction,
					typeFunction : typeFunction,
					nodeFactory : function(type, data, callback) {
						data.columns = [];
						/*
						 * jsPlumbToolkit.Dialogs .show({ id : "dlgName", data :
						 * $.extend(true, {}, data), title : "Enter " + type + "
						 * name:", onOK : function(d) { $.extend(true, data, d);
						 * if (data.name) { if (data.name.length >= 2) {
						 * _columnFilter(data.columns); callback(data); } else
						 * alert(type + " names must be at least 2
						 * characters!"); } } });
						 */
						// _columnAdd();
						
						if(data.noWin){
							callback(data);
						} else {
							showTableDetail(null, callback, type);
						}
						

					},
					edgeFactory : function(params, data, callback) {
						// you must hit the callback if you provide the
						// edgeFactory.
						callback(data);
						// unless you want to return false, to abandon
						// the edge
						// return false;
					},
					portFactory : function(params, data, callback) {
						// add to node. we have to do this manually. the
						// Toolkit does not know our internal
						// data structure.
						columnFunc(null, data);
						params.node.data.columns.push(data)
						callback(data);
					},
					//
					// For a given Node, return the parts of its dataset
					// that we want to configure as Ports.
					// This is called when the data is being loaded. It
					// is an optional argument to the newInstance
					// method.
					//
					portExtractor : function(data, node) {
						return data.columns || [];
					},
					//
					// Prevent connections from a column to itself or to
					// another column on the same table.
					//
					beforeConnect : function(source, target) {
						return source !== target
								&& source.getNode() !== target.getNode();
					}
				});

				//
				// any operation that caused a data update (and would have
				// caused an autosave), fires a dataUpdated event.
				//
				toolkit.bind("dataUpdated", function() {
					_updateDataset();
				});

				// ------------------------ / toolkit setup
				// ------------------------------------

				// ------------------------- dialogs
				// -------------------------------------

				jsPlumbToolkit.Dialogs.initialize({
					selector : ".dlg"
				});

				// ------------------------- / dialogs
				// ----------------------------------

				// ------------------------- behaviour
				// ----------------------------------

				// delete column button
				jsPlumb.on(canvasElement, "tap",
						".table-column-delete, .table-column-delete i",
						function() {
							var info = renderer.getObjectInfo(this);
							jsPlumbToolkit.Dialogs.show({
								id : "dlgConfirm",
								data : {
									msg : "Delete column '" + info.id + "'"
								},
								onOK : function(data) {
									// toolkit.removePort(info.obj.getNode(),
									// info.id);

									_removePort(info.obj.getNode(), info.id,
											true);
								},
								onOpen : function(el) {
									console.dir(el);
								}
							});
						});

				var _ext = function(data) {
					return $.extend(true, {}, {
						id : data.id.replace(" ", "_").toLowerCase(),
						primaryKey : data.primaryKey,
						datatype : data.datatype
					}, data);
				}, _addNewPort = function(info, data) {
					toolkit.addNewPort(info.id, "column", _ext(data));
				}, _updatePort = function(info, data) {
					toolkit.updatePort(info.obj, _ext(data));
				}, _removePort = function(node, id, cb) {
					if (!cb && !confirm("确定删除?")) {
						return false;
					}
					var columns = node.data.columns;
					var _eachColumn = function(i, v) {
						if (v.id === id) {
							columns.splice(i, 1);
							return false;
						}
					};
					$.each(columns, _eachColumn);
					toolkit.removePort(node, id);
				};

				// add new column to table
				jsPlumb.on(canvasElement, "tap", ".new-column, .new-column i",
						function() {
							var info = renderer.getObjectInfo(this), //
							msg = "Column ids must be at least 2 characters!";
							jsPlumbToolkit.Dialogs.show({
								id : "dlgColumnEdit",
								title : "Column Details",
								onOK : function(data) {
									if (data.id) {
										if (data.id.length < 2)
											alert(msg);
										else {
											_addNewPort(info, data);
										}
									}
								}
							});
						});

				// delete a table or view
				jsPlumb.on(canvasElement, "tap", ".delete i, .view-delete i",
						function() {
							var info = renderer.getObjectInfo(this);

							/*
							 * jsPlumbToolkit.Dialogs.show({ id : "dlgConfirm",
							 * data : { msg : "Delete '" + info.id }, onOK :
							 * function(data) { toolkit.removeNode(info.id); }
							 * });
							 */

							BootstrapDialog.confirm2({
								title : '温馨提示',
								cssClass : 'goes-dialog',
								message : '删除[' + info.obj.data.name + "]?",
								callback : function(rst) {
									rst && toolkit.removeNode(info.id);
								}
							});

						});

				// edit a view's query
				jsPlumb.on(canvasElement, "tap", ".view .edit i", function() {
					var info = renderer.getObjectInfo(this);
					jsPlumbToolkit.Dialogs.show({
						id : "dlgViewQuery",
						data : info.obj.data,
						onOK : function(data) {
							data.type = info.obj.data.type;
							return _updateNode(info, data);
						}
					});
				});

				var columnFunc = function(i, v) {
					v.datatype == "varchar" && //
					!v.length && (v.length = 200);
					v.$key = i;
				}, _columnFilter = function(columns) {
					$.each(columns, columnFunc);
				};

				var _filter = function(info, data) {
					var columns, rst = false;
					if (info.obj && info.obj.data //
							&& (columns = info.obj.data.columns)//
							&& data.columns) {
						var m = {}, cm = {}, _eachColumn = function(i, v) {
							m[v.id] = v;
						};
						$.each(columns, _eachColumn);

						var _columns = [];
						$.each(data.columns, function(i, v) {
							if (!v.id) {
								alert("列Code不能为空!");
								return (rst = true);
							}
							if (cm[v.id]) {
								alert("列Code不能重复!");
								return (rst = true);
							} else {
								_columns.push(v);
							}
							cm[v.id] = v;

							if (!m[v.id]) { // add
								_addNewPort(info, v);
							} else {
								// _updatePort(info, v);
							}
						});
						_columnFilter(_columns);
						// data.columns = _columns;
						$.each(info.obj.data.columns, function(i, v) {
							!cm[v.id] && (toolkit.removePort(info, v.id));
						});
						// data.columns = _columns;
					}
					return rst;
				}, _updateNode = function(info, data) {
					var rst = false;

					rst = _filter(info, data);

					!rst && toolkit.updateNode(info.obj, data);

					// console.log(info);

					// toolkit.removeNode(info.id);

					// _addNode(data, {
					// left : info.obj.data.left,
					// top : info.obj.data.top
					// })

					// ToolKitFunc.refresh();
					return !rst;
				}, _addNode = function(n, eventLocation) {
					toolkit.addNode(n, {
						position : eventLocation
					});
				}

				var cdt = $("#column-detail").html(), _columnAdd = function(
						info) {

					var GetField = function(name) {
						return 'input[jtk-att="columns[{0}].{1}"]'.format(
								"{0}", name);
					};

					var $cd = $(".columns-detail"), idSel = GetField("id"), nameSel = GetField("name");
					var len = info ? info.obj.data.columns.length : 0;
					var columnId = null;
					$(".column-btn").off("click").on(
							"click",
							function() {
								var html = cdt.replace(/\$\{\$key\}/g,//
								len++), $html = $(html);
								$html.find(idSel.format(len - 1)).val(
										columnId = MtSequence.GetColumn());
								var name = "";
								$html.find(nameSel.format(len - 1)).val(name);
								$cd.append($html);
								if (info) {
									_addNewPort(info, {
										id : columnId,
										new_ : true,
										datatype : 'varchar',
										name : name
									});
								}
							});
				};

				var GetTableMsg = function() {
					$("div.columns-detail>div.row").each(function(i, row) {
						console.log(row);
					});

				};

				// change a view or table's name

				jsPlumb.on(canvasElement, "tap",
						".view .name span, .table .name span", function() {
							var info = renderer.getObjectInfo(this);
							$(document).data("node", info);
							// _columnFilter(info.obj.data.columns);
							/*
							 * jsPlumbToolkit.Dialogs.show({ id : "dlgName",
							 * data : info.obj.data, title : "Edit " +
							 * info.obj.data.type + " name", onOK :
							 * function(data) { if (data.name) { data.type =
							 * info.obj.data.type; _updateNode(info, data); }
							 * else { // return false; } } }); _columnAdd(info);
							 */

							showTableDetail(info, null);

						});

				var $tableDetails = "<iframe class='table-detail' width='100%' height='360px' scrolling='no'" //
						+ " frameborder='0' onload='window.showDetails(this)' ></iframe>";

				var onshown = function(dialog) {
					dialog.getModalBody()
					//
					.find("iframe.table-detail").attr({
						src : contextPath + //
						"/mx/data/model/index?view=/data/model/tbdetails&type=" //
								+ dialog.getData("type")
					});
				}, successAct = function(dialog) {
					var $win = dialog.getModalBody()//
					.find("iframe").get(0).contentWindow;
					var data = $win.TableDetails.GetData();
					if (data === true) {
						return true;
					}
					if($(this).attr("data-jtk-node-id") == data.id){
						if(IsEmpty(data.color) == '' || data.color == null){
							$("#"+$(this).attr("id")).css("backgroundcolor","#3E7E9C");
						}
						else 
						$("#"+$(this).attr("id")).css("backgroundcolor","#"+data.color);
					}
					
					var type = dialog.getData("type"), callback = dialog
							.getData("callback"), info = dialog.getData("info");
					data.type = type;
					if (callback) {
						callback(data);
					} else {
						_updateNode(info, data);
					}
					dialog.close();
				}

				var bootstrapDialogOpts = {
					onshown : onshown,
					css : {
						width : '750px'
					},
					cssClass : 'goes-dialog',
					buttons : [ {
						label : '确定',
						cssClass : 'btn-primary btn-sm',
						action : successAct
					}, {
						label : '关闭',
						cssClass : 'btn-default btn-sm',
						action : function(dialog) {
							dialog.close();
						}
					} ]
				};
				function IsEmpty(value) {
					return /^\s*$/g.test(value);
				}
				function showTableDetail(info, callback, type) {
					type = type || info.obj.data.type;
					window.showDetails = function(o) {
						if (o.contentWindow.TableDetails) {
							var code = window.nodesParamter.code;
							var data = null;
							if (info) {
								data = info.obj.data;
							} else {
								data = {
									id : "tb_" + (new Date().getTime()),
									name : type,
									code : /*code*/ ""
								};
							}
							o.contentWindow.TableDetails.InitData(data);
						}
					};
					BootstrapDialog.show($.extend(true, bootstrapDialogOpts, {
						title : type,
						message : $tableDetails,
						data : {
							type : type,
							info : info,
							callback : callback
						}
					}));
				}

				// edit an edge's detail
				var _editEdge = function(edge, isNew) {
					jsPlumbToolkit.Dialogs.show({
						id : "dlgRelationshipType",
						data : edge.data,
						onOK : function(data) {
							// update the type in the edge's data model...it
							// will be re-rendered.
							// `type` is set in the radio buttons in the dialog
							// template.
							toolkit.updateEdge(edge, data);
						},
						onCancel : function() {
							// if the user pressed cancel on a new edge, delete
							// the edge.
							if (isNew)
								toolkit.removeEdge(edge);
						}
					});
				};

				// edit a column's details
				jsPlumb.on(canvasElement, "tap", ".table-column-edit i",
						function() {
							var info = renderer.getObjectInfo(this), //
							msg = "Column ids must be at least 2 characters!";
							jsPlumbToolkit.Dialogs.show({
								id : "dlgColumnEdit",
								title : "Column Details",
								data : info.obj.data,
								onOK : function(data) {
									if (data.id) {
										if (data.id.length < 2)
											jsPlumbToolkit.Dialogs.show({
												id : "dlgMessage",
												msg : msg
											});
										else {
											_updatePort(info, data);
										}
									}
								}
							});
						});

				$(document).on("click.table-column-delete-icon", //
				".columns-detail .table-column-delete-icon", function(e) {
					e.preventDefault();
					var info = $(document).data("node");
					if (!info) {
						return false;
					}
					var $row = $(this).closest("div.row");
					var id = $row.find(".column-id").val();
					// toolkit.removePort(info.obj, id);

					_removePort(info.obj, id);
					$row.remove();
				});

				var renderer = window.renderer = toolkit
						.render({
							container : canvasElement,
							view : {
								// Two node types - 'table' and 'view'
								nodes : {
									"table" : {
										template : "tmplTable"
									},
									"view" : {
										template : "tmplView"
									}
								},
								edges : {
									"common" : {
										anchor : [ "Left", "Right" ], // anchors
										// for
										// the
										// endpoints
										connector : "StateMachine", // StateMachine
										// connector
										// type
										cssClass : "common-edge",
										events : {
											"dbltap" : function(params) {
												_editEdge(params.edge);
											}
										},
										overlays : [ [
												"Label",
												{
													cssClass : "delete-relationship",
													label : "<i class='fa fa-times'></i>",
													events : {
														"tap" : function(params) {
															if (confirm("删除连接关系?")) {
																toolkit
																		.removeEdge(params.edge);
															}
														}
													}
												} ] ]
									},
									// each edge type has its own overlays.
									"1:1" : {
										parent : "common",
										overlays : [ [ "Label", {
											label : "1",
											location : 0.1
										} ], [ "Label", {
											label : "1",
											location : 0.9
										} ] ]
									},
									"1:N" : {
										parent : "common",
										overlays : [ [ "Label", {
											label : "1",
											location : 0.1
										} ], [ "Label", {
											label : "N",
											location : 0.9
										} ] ]
									},
									"N:M" : {
										parent : "common",
										overlays : [ [ "Label", {
											label : "N",
											location : 0.1
										} ], [ "Label", {
											label : "M",
											location : 0.9
										} ] ]
									}
								},
								ports : {
									"default" : {
										template : "tmplColumn",
										paintStyle : {
											fill : "#f76258"
										}, // the endpoint's appearance
										hoverPaintStyle : {
											fill : "#434343"
										}, // appearance when mouse hovering on
										// endpoint or connection
										edgeType : "common", // the type of
										maxConnections : -1, // no limit on
										// connections
										dropOptions : { // drop options for the
											hoverClass : "drop-hover"
										},
										allowLoopback : false, // do not allow
										allowNodeLoopback : false, // do not
										events : {
											"dblclick" : function() {
												console.log(arguments);
											}
										}
									}
								}
							},
							layout : {
								type : "Spring",
								parameters : {
									padding : [ 150, 150 ]
								}
							},
							miniview : {
								container : miniviewElement
							},
							events : {
								portAdded : function(params) {
									params.nodeEl.querySelectorAll("ul")[0]
											.appendChild(params.portEl);
								},
								edgeAdded : function(params) {
									if (params.addedByMouse) {
										_editEdge(params.edge, true);
									}
								},
								canvasClick : function(e) {
									toolkit.clearSelection();
								}
							},
							dragOptions : {
								filter : "i, .view .name span, .table .name span, .table-column *"
							},
							consumeRightClick : false,
							zoomToFit : true
						});

				// listener for mode change on renderer.
				renderer.bind("modeChanged", function(mode) {
					jsPlumb.removeClass(controls.querySelectorAll("[mode]"),
							"selected-mode");
					jsPlumb.addClass(controls.querySelectorAll("[mode='" + mode
							+ "']"), "selected-mode");
				});

				// pan mode/select mode
				jsPlumb.on(controls, "tap", "[mode]", function() {
					renderer.setMode(this.getAttribute("mode"));
				});

				// on home button click, zoom content to fit.
				jsPlumb.on(controls, "tap", "[reset]", function() {
					ToolKitFunc.reset();
				});

				jsPlumb.on(controls, "tap", "[jtk-node-type]", function(e) {
					console.log(this);
				});

				// ------------------------ / rendering
				// ------------------------------------

				var _syntax = /("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g;

				var _syntaxHighlight = function(json) {
					json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;')
							.replace(/>/g, '&gt;');
					return "<pre>"
							+ json.replace(_syntax, function(match) {
								var cls = 'number';
								if (/^"/.test(match)) {
									if (/:$/.test(match)) {
										cls = 'key';
									} else {
										cls = 'string';
									}
								} else if (/true|false/.test(match)) {
									cls = 'boolean';
								} else if (/null/.test(match)) {
									cls = 'null';
								}
								return '<span class="' + cls + '">' + match
										+ '</span>';
							}) + "</pre>";
				};

				var datasetContainer = document
						.querySelector(".jtk-demo-dataset");
				var _updateDataset = function() {
					datasetContainer.innerHTML = _syntaxHighlight(JSON
							.stringify(toolkit.exportData(), null, 4));
				};

				ToolKitFunc.Renderer = (function() {
					var o = {
						droppables : controls.querySelectorAll("i.node-tool"),
						dragOptions : {
							zIndex : 50000,
							cursor : "move",
							clone : true
						},
						typeExtractor : function(el, eventInfo, isNativeDrag,
								eventLocation) {
							return el.getAttribute("jtk-node-type");
						},
						dataGenerator : function(type, draggedElement,
								eventInfo, eventLocation) {
							var obj = {
								name : type,
								columns : [],
								type : type,
								id : MtSequence.GetTable()
							};
							return obj;
						}
					};
					return $.extend(true, o, renderer.registerDroppableNodes(o));
				})();

				// ------------------------------------
				ToolKitFunc.loadData = (function() {
					var opts = {
						onload : _updateDataset
					};
					return function(nodes) {
						toolkit.load($.extend(true, {}, opts, {
							data : nodes
						}));
					};
				})();
				//传递参数
				ToolKitFunc.setParam = (function() {	
					return function(nodes) {
						window.nodesParamter = nodes;
					};
				})();
				
				ToolKitFunc.reset = function() {
					toolkit.clearSelection();
					renderer.zoomToFit();
				};

			});

})();

var MtSequence = (function() {
	var tb = new Date().getTime(), //
	col = tb, func = new Function();

	var tb_ = "tb_", col_ = "col_";

	func.GetTable = function() {
		return tb_ + (tb++);
	};

	func.GetColumn = function() {
		return col_ + (col++);
	};
	return func;
})();
