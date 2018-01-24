<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<div id="grid-01" class="mt-grid"></div>
<script type="text/javascript">
var TabsFunc = (function(){
	
	var $tabs = $("#tt"), //
	setId = new Date().getTime(), opt = {
		title : "映射设置"
	};
	
	var func = new Function();
	
	func.addTab = function(id, dsmId, opts){
		opts = opts || opt;
		func.closeTab();
		var $iframe = $("<iframe>", {
			frameborder : "0",
			width : '100%',
			height : '95%',
			src : "${contextPath}/mx/dataset/dataSetMapping/mapping?parentId="+ //
					id +"&view=/datamgr/dataset/dsm&dsmId=" + dsmId + "&fn=closeTab"
		});
		$tabs.tabs("add", $.extend(opts, {
			content : $iframe.get(0).outerHTML
		}));
	};
	
	func.closeTab = function(id){
		$tabs.tabs("close", id || opt.title);
	};
	
	return func;
	
})();

function closeTab(){
	TabsFunc.closeTab();
}

var _reload = function(data){
	
	save([data], true);
};

var GridOptions = (function(){
	
	var cm = $.extend(true, [], retFn ? seacrhCommand : command)
	
	var options = {
			"columnMenu" : false,
			"dataSource" : {
				"schema" : {
					"total" : "total",
					"model" : {
						"fields" : {
							"id" : {
								"type" : "string"
							},
							"startIndex" : {
								"type" : "number"
							}
						}
					},
					"data" : "rows"
				},
				"transport" : {
					"parameterMap" : function(options) {
						options.rows = options.pageSize;
						options.pageId = $("#pageId").val()
						return options;
					},
					"read" : {
						"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
						"type" : "POST",
						dataType : 'JSON',
						"url" : "${contextPath}/mx/dataset/json?nodeId=${nodeId}"
					}
				},
				"serverFiltering" : true,
				"serverSorting" : true,
				"pageSize" : 10,
				"serverPaging" : true
			},
			"height" : retFn ? "445px" : x_height,
			"reorderable" : true,
			"filterable" : true,
			"sortable" : true,
			"pageable" : {
				"refresh" : true,
				"pageSizes" : [ 5, 10, 15, 20, 25, 50, 100, 200, 500 ],
				"buttonCount" : 10
			},
		///	"selectable" : "single",
			"toolbar" : kendo.template(jQuery("#template").html()),
			"columns" : [ {
				"field" : "name",
				"title" : "名称",
				"width" : "220px",
				"expandable" : true,
				"lockable" : false,
				"locked" : false
			}, {
				"field" : "title",
				"title" : "标题",
				"width" : "280px",
				"expandable" : true,
				"lockable" : false,
				"locked" : false
			}, {
				"field" : "createBy",
				"title" : "创建人",
				"width" : "100px",
				"expandable" : true,
				"lockable" : false,
				"locked" : false
			}, {
				"field" : "createTime",
				"title" : "创建日期",
				"width" : "100px",
				"lockable" : false,
				"align" : "center",
				"format" : "{0: yyyy-MM-dd}",
				"filterable" : {
					"ui" : "datetimepicker"
				}
			}, {
				"command" : cm
			} ],
			"scrollable" : {},
			"resizable" : true,
			"groupable" : false
		}, func = new Function();
	
	
	cm.push({
		text : '新增数据集',
		name : 'add_',
		click : function(e){
			var $tr = jQuery(e.currentTarget).closest("tr");
			var dataItem = this.dataItem($tr);
			func.dataItem = dataItem;
			var grid = $tr.//
			closest("div[data-role=grid]").data("kendoGrid");
			grid.expandRow(".k-master-row:eq(" +  $tr.index() + ")");
			
			editRow('${contextPath}/mx/dataset/edit?fn=_reload&nodeId=' + nodeId);
		}
	},{
		text : '添加已有数据集',
		name : 'add__',
		click : function(e){
			var $tr = jQuery(e.currentTarget).closest("tr");
			var dataItem = this.dataItem($tr);
			func.dataItem = dataItem;
			var grid = $tr.//
			closest("div[data-role=grid]").data("kendoGrid");
			grid.expandRow(".k-master-row:eq(" +  $tr.index() + ")");
			
			var link = '${contextPath}/mx/dataset?view=/datamgr/dataset/list2&fn=save';
			jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "添加",
				closeBtn: [0, true],
				shade: [0.8, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['20px',''],
				fadeIn: 100,
				area: [jQuery(window).width(), (jQuery(window).height() - 40) +'px'], 
				iframe: {src: link}
			});
		}
	});
	
	func.options = options;
	
	func.commands = [
					{
						"text" : "修改",
						"name" : "edit_",
						"click" : function(e) {
							var dataItem = this.dataItem(jQuery(
									e.currentTarget).closest("tr"));
							var link = "${contextPath}/mx/dataset/edit?id="
								+ dataItem.id;
						editRow(link);
					}
				},
				{
					"text" : "删除",
					"name" : "remove_",
					"click" : function(e) {
						var dataItem = this.dataItem(jQuery(e.currentTarget)
								.closest("tr"));
						var $grid = jQuery(e.currentTarget).//
						closest("div[data-role=grid]");

						if (confirm("删除映射配置吗?")) {
							$
									.ajax({
										url : "${contextPath}/mx/dataset/dataSetMapping/deleteByParentIdAndDsId",
										data : {
											dsmId : dataItem.id,
											parentId : $grid.attr("id")
										},
										dataType : 'JSON',
										type : "POST",
										success : function(ret) {
											ret && ret.message
													&& (alert(ret.message));

											reloadGrid_($grid.data("kendoGrid"));
										}
									});
						}
					}
				},
				{
	                "text": "SQL构建器",
	                "name": "designer_",
	                "click": function(e){
	                	var dataItem = this.dataItem(jQuery(//
	                			e.currentTarget).closest("tr"));
	                	showSqlbuilder(dataItem);
	                }
	            },
				{
					"text" : "映射设置",
					"name" : "mappingSet",
					"click" : function(e) {
						var dataItem = this.dataItem(jQuery(//
								e.currentTarget).closest("tr"));

						var $grid = jQuery(e.currentTarget).//
						closest("div[data-role=grid]");

						TabsFunc.addTab($grid.attr("id"), dataItem.id);
					}
				}]

		return func;

	})();
	
function showSqlbuilder(dataItem) {
    var link = "${contextPath}/mx/dataset/sqlbuilder?id="+dataItem.id;
    if(dataItem.dynamicDataSet && dataItem.dynamicDataSet == 'Y'){
    	link = '${contextPath}/mx/form/dataset/dynamicDatabase?id='+dataItem.id;
    }
    window.open(link);
}

	function save(data, add) {
		var current = GridOptions.dataItem;
		var mappings = [];
		if (data && current && data.length) {
			var singledata = {};
			singledata[current.id] = current;
			var grid = $("#" + current.id).data("kendoGrid");
			var d = grid.dataSource.data();
			$.each(d, function(i, v) {
				singledata[v.id] = v;
			});
			$.each(data, function(i, v) {
				!singledata[v.id] && (mappings.push({
					dsName : current.name,
					dsmName : v.name,
					status : 1,
					dsmId : v.id
				}));
			});
			$
					.ajax({
						url : "${contextPath}/mx/dataset/dataSetMapping/saveDataSetMappings",
						data : {
							parentId : current.id,
							mappings : JSON.stringify(mappings)
						},
						dataType : 'JSON',
						type : "POST",
						success : function(ret) {
							//ret && ret.message && (alert(ret.message));
							closeLayer();
							reloadGrid_(grid);

							!add && TabsFunc.addTab(current.id, mappings[0].dsmId);
						}
					});

		}
	}

	function reloadGrid_(grid) {
		window.setTimeout(function() {
			grid && grid.dataSource && grid.dataSource.read();
		}, 0);
	}

	function closeLayer() {
		layer.close(layer.getFrameIndex());
	}

	function maxLayer() {
		layer.full(layer.getFrameIndex());
	}

	$(document).ready(function() {
		var opts = $.extend(true, {}, GridOptions.options, {
			detailInit : detailInit
		});
		var element = $("#grid-01").kendoGrid(opts);
	});

	/**
	 * 明细
	 */
	function detailInit(e) {
		var data = e.data;
		var opts = $.extend(true, {}, GridOptions.options, {
			"dataSource" : {
				"transport" : {
					"parameterMap" : function(options) {
						options.dsId = data.id;
						return options;
					}
				}
			},
			toolbar : ' ',
			pageable : false,
			height : 150,
			"reorderable" : false,
			"filterable" : false,
			"sortable" : false
		});

		opts.columns[4] && (opts.columns[4] = {
			command : GridOptions.commands
		});
		
		opts.columns.unshift({
			template: function(o){
				return "<input class='mapping-checkbox' type='checkbox' mappingid ='"+ o.mappingId +"' "+ (o.status == 1 ? "checked='true'" : "") +" />";
			},
			field: "status",
			title: "启用",
			width: 40
		});
		
		$("<div>", {
			id : data.id
		}).appendTo(e.detailCell).kendoGrid(opts);
	}
	
	
	$(function(){
		$(document).on("click.input.mapping-checkbox",//
				"input.mapping-checkbox", function(){
			var mappingId = $(this).attr("mappingid");
			if(!mappingId){
				return ;
			}
			
			$.ajax({
				url : '${contextPath}/mx/dataset/dataSetMapping/saveDataSetMapping',
				type : 'POST',
				data : {
					id : mappingId,
					status : this.checked ? 1 : 0
				}
			});
			
		});
	});
	
</script>
