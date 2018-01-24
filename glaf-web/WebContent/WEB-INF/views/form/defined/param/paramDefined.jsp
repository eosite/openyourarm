<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/scripts/layer/skin/layer.ext.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/scripts/layer/extend/layer.ext.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/scripts/defineKendouiInit.js"></script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath }/webfile/js/checkRelation.js"></script>
<script type="text/javascript">
	var nameElementId = '${param.nameElementId}';
	var objelementId = '${param.objelementId}',
		retFn = '${param.retFn}',
		saveId = '${param.saveId}',
		contextPath="${pageContext.request.contextPath }";
	//数据集隐藏域信息
	var datasourceElementId_ = '${param.datasourceElementId}';
</script>
<style type="text/css">
	html,body{
		height: 100%;
		margin: 0px;
		padding: 0px;
	}
	.mtitle{
		height: 20px;
	}
</style>
</head>
<c:set var="tools" value="{name:'idValue',text:'主键ID'},{name:'databaseId',text:'标段'},{name:'wbsId',text:'cell表参数(WBS ID)'},{name:'processId',text:'流程实例ID'},{name:'taskId',text:'流程任务ID'},{name:'importDataSource',text:'引入数据集字段'}"></c:set>
<body>
	<div id="main" style="height: 98%">
		<div>
			<div class="k-header k-grid-toolbar mtitle">参数定义</div>
			<div id="grid" data-role="grid"
			data-toolbar="[{name:'createNew',text:'新增'},'save',${tools }]"
			data-editable="true" data-selectable="rows" data-groupable="false"
			data-sortable="false" data-pageable="false" data-reorderable="false"
			data-params="[{text:'获取',items:[{text:'所有行',type:'getAll',id:'grid'}]}]"
			data-columns="[
					{ field:'param', title:'参数' },
					{ field:'name', title:'参数名' },
					{ command: [{name:'delObj',text:'删除',click:delFunc},{name:'add_obj',text:'->对象',click:addToObj}], title:'',width:'180px'}
				]"
			data-bind="
					source: grid_dataSource,
					events: {
						saveChanges: onSaveChanges
					}
				"></div>
		</div>
		<div>
			<div class="k-header k-grid-toolbar mtitle">对象定义</div>
			<div class="param_splitter">
				<div>
					<div id="obj_grid" data-role="grid"></div>
				</div>
				<div>
					<div id="obj_child_grid" data-role="grid"></div>
				</div>
			</div>
		</div>
		<div>
			<div class="k-header k-grid-toolbar mtitle">集合定义</div>
			<div class="param_splitter">
				<div>
					<div id="ary_grid" data-role="grid"></div>
				</div>
				<div>
					<div id="ary_child_grid" data-role="grid"></div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var component_dataSource_ = window.parent.$('#'+datasourceElementId_).val() ; //控件数据源
		var datasourceSetObj_ = null;
		//控件字段信息
		var component_dataSource_Columns_ = [];
		if(component_dataSource_){
			datasourceSetObj_ = JSON.parse(component_dataSource_);
			if(datasourceSetObj_ && datasourceSetObj_.length > 0){
				if("columns" in datasourceSetObj_[0]){
					component_dataSource_Columns_ = datasourceSetObj_[0].columns;
				}else if("selectDatasource" in datasourceSetObj_[0]){
					component_dataSource_Columns_ =datasourceSetObj_[0].columns || datasourceSetObj_[0].selectColumns ;
				}	
			}
		}
		$.each(component_dataSource_Columns_,function(i,item){
			item.text = item.title;
			item.value = item.columnName;
		})


		$("#main").kendoSplitter({
		  	panes: [ {}, {}, {} ]
		});
		$(".param_splitter").height($(".param_splitter").parent().height()-40);
		$(".param_splitter").kendoSplitter({
		  	panes: [ {}, {}],
		  	orientation: "vertical"
		});
		var dataSource = [],
			obj_source = [],
			obj_child_source = [],
			ary_source = [],
			ary_child_source = [];
		var objsource = window.parent.$('#' + objelementId).val(), objJson;
		//objsource = '[{"name":"字段3,字段2,字段1,","type":"mutil","source":[{"name":"字段3","param":"col1484820732625"},{"name":"字段2","param":"col1484820732425"},{"name":"字段1","param":"col1484820721930"}],"objSource":[{"name":"对象","child":"[{\\\"name\\\":\\\"字段3\\\",\\\"param\\\":\\\"col1484820732625\\\"},{\\\"name\\\":\\\"字段2\\\",\\\"param\\\":\\\"col1484820732425\\\"},{\\\"name\\\":\\\"字段1\\\",\\\"param\\\":\\\"col1484820721930\\\"}]","param":"obj1484820739861","id":""}],"arySource":[]}]' ;
		if (objelementId) {
			if (objsource) {
				var ojs = JSON.parse(objsource),objo = ojs[0];
				if(objo["type"]){
					dataSource = objo["source"] ;
					obj_source = objo["objSource"] ;
					ary_source = objo["arySource"] ;
				}else{
					dataSource = ojs;	
				}
			}
		} else {
			var $target = window.parent.$("body").data("target"), objid = $target
					.data("field");
			objsource = window.parent.$("input[data-field=" + objid + "]")
					.data("jsonSource")/*val()*/;
			if (objsource) {
				objJson = JSON.parse(objsource);
				if (objJson && objJson.length && objJson[0].datas) {
					dataSource = objJson[0].datas[$target.data("field")][0]['val'];
				}
			}
		}
		/**
		 * 保留一份 用来匹配  是否删除原有的 参数 然后用来检测判断 是否被引用了
		 * @type {[type]}
		 */
		var storeDataSource = $.extend(true,[],dataSource);
		var stockTr = {};
		var obj_grid = $("#obj_grid").kendoGrid({
			dataSource : {
				autoSync : true,
				data : obj_source,
				schema : {
					model : {
						id: "id" ,
						fields : {
							name : {
								editable : true,
								type : 'string'
							},
							param : {
								editable : false,
								type : 'string'
							},
							child : {
								type : 'string'
							}
						}
					}
				}
			},
			selectable : "rows",
			editable: true,
			columns: [
		        { field: "param",title:"对象值" },
		        { field:  "name",title:"对象名称"},
		        {
		        	command: [
		        		{
		        			name: "destroy2",
		        			text: "删除",
		        			click:function(e){
		        				var srow = this.dataItem($(e.currentTarget).closest("tr") || this.select());
		        				obj_grid.removeRow($(e.currentTarget).closest("tr"));
		        				if(this.dataItems().length){
		        					obj_grid.select("tr:eq(0)");
		        				}
		        			}
		        		},
		        		{
		        			name: "inner",
		        			text: "->集合",
		        			click:function(e){
		        				if (!ary_grid.select().length) {
									alert("请先创建并选择一个集合");
									return;
								}
								var srow = this.dataItem($(e.currentTarget).closest("tr") || this.select()),
									row = ary_grid.dataItem(ary_grid.select()),
									child = JSON.parse(row.child) ;
								
								if(child.length){
									alert("集合中只能有一个对象");
									return ;
								}

								child.push(JSON.parse(JSON.stringify(srow)));
								row.child = JSON.stringify(child);

								ary_child_source.length = 0 ;
								$.merge(ary_child_source, child);
								refreshChild(ary_child_grid,ary_child_source);
							}
		        		}
		        	], 
		        	title:'',
		        	width:'180px'
		        }
		    ],
		    toolbar: [
			    { name: "createObj" ,text: "新增" }
			],
			change: function(e) {
				stockTr["obj_grid"] = this.select().index();
				var srow = this.dataItem(this.select()),
					child = JSON.parse(srow.child) ;
				obj_child_source.length = 0 ;
				$.merge(obj_child_source, child);
				// obj_child_grid.dataSource.read();
				refreshChild(obj_child_grid,obj_child_source);
			},
			edit: function(e) {
				if (this.select().length == 0) {
					this.select($(e.container[0]).parents("tr"));
				}
			},
			dataBound: function(e) {
			    if (this.select().length == 0 && e.sender.dataItems().length) {
					this.select("tr:eq("+(stockTr["obj_grid"]||0)+")");
				}
			}
		}).data('kendoGrid');
		/**
		 * 删除一行 
		 * @param  {[type]} e [description]
		 * @return {[type]}   [description]
		 */
	function delFunc(e){
		//debugger;
		var srow = this.dataItem($(e.currentTarget).closest("tr") || this.select()),
			that = this,
			needCheck = false;
		$.each(storeDataSource,function(i,storeData){
			if(srow.param == storeData.param){
				needCheck = true;
			}
		})

		function delcallback(){
			var grid = $('#grid').data('kendoGrid');
			grid.removeRow($(e.currentTarget).closest("tr"));
			if(that.dataItems().length){
				grid.select("tr:eq(0)");
			}
		}
		if(needCheck){
			checkParamRelation({
					saveId : saveId,
					param : JSON.stringify(srow)
				},delcallback);
		}else{
			delcallback();
		}
	}
		var obj_child_grid = $("#obj_child_grid").kendoGrid({
			dataSource : {
				autoSync : true,
				data : obj_child_source,
				schema : {
					model : {
						id: "id" ,
						fields : {
							name : {
								editable : false,
								type : 'string'
							},
							param : {
								editable : false,
								type : 'string'
							},
							columnFiled : {
								editable : true,
								type : 'string'
							}
						}
					}
				}
			},
			editable: true,
			columns: [
		        { field: "param",title:"参数" },
		        { field:  "name",title:"参数名"},
		        { field:  "columnFiled",title:"关联字段", 
		        	values:component_dataSource_Columns_,
		        	template : function(dataItem){
						var ret = "" ;
						if(!dataItem.columnFiled){
							dataItem.columnFiled = "";
							return ret;
						}
						$.each(component_dataSource_Columns_,function(i,item){
							if(item.value == dataItem.columnFiled){
								ret = item.text;
								return false;
							}
						})
						//监听数据变化
						var row = obj_grid.dataItem(obj_grid.select());
				  		if(row && obj_child_grid){
							var child = JSON.parse(row.child);
							var obj_child_grid_data = obj_child_grid.dataItems();
							row.child = JSON.stringify(obj_child_grid_data);
						}
						return ret ;
					},
					// editor : function(container,options){
					// 	var $tr = $(container).parents('tr') ;
					// 	var _index = $tr.index() ;
					// 	var kkk = $("<input/>").attr('name',options.field).appendTo(container).
					// 	kendoDropDownList({dataValueField:'value',dataTextField:'text',dataSource:component_dataSource_Columns_,
					// 		change : function(data){
					// 			var $kkkinput = $kkkinput;
					// 			var bb = kkk;
					// 			console.log(data);
					// 		}
					// 	});
					// },
		    	},
		        {
		        	command: [{
		        			name: "destroy2",
		        			text: "删除",
		        			click:function(e){
		        				var srow = obj_child_grid.dataItem($(e.currentTarget).closest("tr"));
		        				obj_child_grid.removeRow($(e.currentTarget).closest("tr"));
		        				var row = obj_grid.dataItem(obj_grid.select());
		        				var child = JSON.parse(row.child);
		        				child = child.filter(function(v) {
		        					return !(v.param == srow.param);
		        				});
		        				row.child = JSON.stringify(child);
		        			}
		        		}], 
		        	title:'',
		        	width:'180px'
		        }
		    ],
		}).data('kendoGrid');

		var ary_grid = $("#ary_grid").kendoGrid({
			dataSource : {
				autoSync : true,
				data : ary_source,
				schema : {
					model : {
						id: "id" ,
						fields : {
							name : {
								editable : true,
								type : 'string'
							},
							param : {
								editable : false,
								type : 'string'
							},
							child : {
								type : 'string'
							}
						}
					}
				}
			},
			selectable : "rows",
			editable: true,
			columns: [
		        { field: "param",title:"集合值" },
		        { field: "name",title:"集合名称"},
		        {
		        	command: [
		        		{
		        			name: "destroy2",
		        			text: "删除",
		        			click:function(e){
		        				var srow = this.dataItem($(e.currentTarget).closest("tr") || this.select());
		        				ary_grid.removeRow($(e.currentTarget).closest("tr"));
		        				if(this.dataItems().length){
		        					ary_grid.select("tr:eq(0)");
		        				}
		        			}
		        		}
		        	], 
		        	title:'',
		        	width:'180px'
		        }
		    ],
		    toolbar: [
			    { name: "createAry" ,text: "新增" }
			],
			change: function(e) {
				stockTr["ary_grid"] = this.select().index();
				var srow = this.dataItem(this.select()),
					child = JSON.parse(srow.child) ;
				ary_child_source.length = 0 ;
				$.merge(ary_child_source, child);
				refreshChild(ary_child_grid,ary_child_source);
			},
			edit: function(e) {
				if (this.select().length == 0) {
					this.select($(e.container[0]).parents("tr"));
				}
			},
			dataBound: function(e) {
			    if (this.select().length == 0 && e.sender.dataItems().length) {
					this.select("tr:eq("+(stockTr["ary_grid"]||0)+")");
				}
			}
		}).data('kendoGrid');

		var ary_child_grid = $("#ary_child_grid").kendoGrid({
			dataSource : {
				autoSync : true,
				data : ary_child_source,
				schema : {
					model : {
						id: "id" ,
						fields : {
							name : {
								editable : false,
								type : 'string'
							},
							param : {
								editable : false,
								type : 'string'
							}
						}
					}
				}
			},
			editable: true,
			columns: [
		        { field: "param",title:"对象值" },
		        { field:  "name",title:"对象名称"},
		        {
		        	command: [{
		        			name: "destroy2",
		        			text: "删除",
		        			click:function(e){
		        				var srow = ary_child_grid.dataItem($(e.currentTarget).closest("tr"));
		        				ary_child_grid.removeRow($(e.currentTarget).closest("tr"));
		        				var row = ary_grid.dataItem(ary_grid.select());
		        				var child = JSON.parse(row.child);
		        				child = child.filter(function(v) {
		        					return !(v.param == srow.param);
		        				});
		        				row.child = JSON.stringify(child);
		        			}
		        		}], 
		        	title:'',
		        	width:'180px'
		        }
		    ],
		}).data('kendoGrid');

		$(".k-grid-createObj").click(function(e) {
			var grid = obj_grid, dataItems = grid
					.dataItems(), times = (new Date().getTime()), param = 'obj'
					+ times;
			for (var i = 0; i < dataItems.length; i++) {
				var data = dataItems[i];
				if (data.param == param) {
					return;
				}
			}
			grid.dataSource.insert({
				name : '',
				child : '[]',
				param : param
			});
			grid.refresh();
			grid.select("tr:eq(0)");
		});

		$(".k-grid-createAry").click(function(e) {
			var grid = ary_grid, dataItems = grid
					.dataItems(), times = (new Date().getTime()), param = 'ary'
					+ times;
			for (var i = 0; i < dataItems.length; i++) {
				var data = dataItems[i];
				if (data.param == param) {
					return;
				}
			}
			grid.dataSource.insert({
				name : '',
				child : '[]',
				param : param
			});
			grid.refresh();
			grid.select("tr:eq(0)");
		});
		//添加至对象
		function addToObj(e){
			if (!obj_grid.select().length) {
				alert("请先创建并选择一个对象");
				return;
			}
			var srow = this.dataItem($(e.currentTarget).closest("tr") || this.select()),
				row = obj_grid.dataItem(obj_grid.select()),
				child = JSON.parse(row.child) ;
			
			if(child.filter(function(v){
				return srow.param==v.param;
			}).length){
				alert("不能重复添加");
				return ;
			}

			child.push(JSON.parse(JSON.stringify(srow)));
			row.child = JSON.stringify(child);

			obj_child_source.length = 0 ;
			$.merge(obj_child_source, child);
			// obj_child_grid.dataSource.read();
			refreshChild(obj_child_grid,obj_child_source);
		}

		function refreshChild(grid,source){
			if(!grid){
				return;
			}
			if(obj_child_grid == grid){
				obj_child_grid.dataSource.read();
				return;
			}
			setTimeout(function(){
				grid.setDataSource(new kendo.data.DataSource({
	            	data: source ,
	            	schema : {
						model : {
							id: "id" ,
							fields : {
								name : {
									editable : false,
									type : 'string'
								},
								param : {
									editable : false,
									type : 'string'
								},
								columnFiled : {
									editable : true,
									type : 'string'
								}
							}
						}
					}
	            }));
	            grid.dataSource.sync();
			},0)
		}

		kendo.bind($("#grid"),{
			grid_dataSource : dataSource,
			onSaveChanges : function(e) {
				var values = this.get("grid_dataSource");
				var hasValues = [];
				var names = "";
				var times = (new Date().getTime());
				for (var i = 0; i < values.length; i++) {
					if (values[i].name == undefined /* || values[i].expression == undefined  */
							|| values[i].name.trim() == "" /* || values[i].expression.trim() == "" */) {
						layer.alert("必须填写值", 3);
						return;
					} else {
						if (hasValues.indexOf(values[i].name) >= 0) {
							layer.alert("存在重复参数名，请检查", 3);
							return;
						} else {
							hasValues.push(values[i].name);
							if (!values[i].param) {
								//自动生成唯一的参数
								values[i].param = 'col'
										+ (times + i);
							}
							names += values[i].name + ",";
						}
					}
				}
				var objSource = obj_grid.dataItems().toJSON(),
					arySource = ary_grid.dataItems().toJSON(),
					retValues = [{
						name : names,
						type : "mutil",
						source : values.toJSON(),
						objSource : objSource ,
						arySource : arySource 
					}];



				window.parent.$('#' + nameElementId).val(names);
				window.parent.$('#' + objelementId).val(
						JSON.stringify(/*values*/retValues));

				retFn && parent[retFn].call();

				parent.layer.close(parent.layer.getFrameIndex());
			}
		});

		$(".k-grid-createNew").click(function(e) {
			var grid = $('#grid').data('kendoGrid'), dataItems = grid
					.dataItems(), times = (new Date().getTime()), param = 'col'
					+ times;
			for (var i = 0; i < dataItems.length; i++) {
				var data = dataItems[i];
				if (data.param == param) {
					return;
				}
			}
			grid.dataSource.insert({
				name : '',
				param : param
			});
			grid.refresh();
		});
		//标段、wbsId
		
		
		
		$.each([${tools}], function(i, v){
			$(".k-grid-" + v.name).click(function(e) {
				if(v.name == "importDataSource"){
					//引入数据集字段信息
					//输入形参中增加字段信息
					var grid = $('#grid').data('kendoGrid');
					var dataItems = grid.dataItems();
					var new_obj_child_datas = [];	//对象子参数数组
					for (var i = 0; i < component_dataSource_Columns_.length; i++) {
						var selectFieldRow = component_dataSource_Columns_[i];
						var param_param = 'col' + (new Date().getTime());
						grid.dataSource.insert({
							name : selectFieldRow.title,
							param : param_param
						});
						new_obj_child_datas.push({
							name : selectFieldRow.title,
							param : param_param,
							columnFiled : selectFieldRow.columnName
						})
					}
					grid.refresh();

					//obj_grid
					var obj_param = 'obj' + (new Date().getTime());
					var new_obj_data = {
						name : '数据集对象',
						child : JSON.stringify(new_obj_child_datas),
						param : obj_param
					};
					obj_grid.dataSource.insert(new_obj_data);
					obj_grid.refresh();
					obj_grid.select("tr:eq(0)");

					//集合grid中增加参数信息
					var ary_param = 'ary' + (new Date().getTime());
					
					ary_grid.dataSource.insert({
						name : '数据集集合',
						child : JSON.stringify([new_obj_data]),
						param : ary_param
					});
					ary_grid.refresh();
					ary_grid.select("tr:eq(0)");
				}else{
					var grid = $('#grid').data('kendoGrid');
					var dataItems = grid.dataItems();
					for (var i = 0; i < dataItems.length; i++) {
						var data = dataItems[i];
						if (data.param == v.name) {
							return;
						}
					}
					grid.dataSource.insert({
						name : v.text,
						param : v.name
					});
					grid.refresh();
				}
			});
		}); 
		
		/* $(".k-grid-databaseId").click(function(e) {
			var grid = $('#grid').data('kendoGrid');
			var dataItems = grid.dataItems();
			for (var i = 0; i < dataItems.length; i++) {
				var data = dataItems[i];
				if (data.param == 'databaseId') {
					return;
				}
			}
			grid.dataSource.insert({
				name : '标段',
				param : 'databaseId'
			});
			grid.refresh();
		});*/
	</script>
</body>
</html>