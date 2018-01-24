<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.business.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.ui.model.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%
	String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(
			context);
	pageContext.setAttribute("contextPath", context);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>选择角色</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript" src="${contextPath}/scripts/map.js"></script>
<style type="text/css">
html {
	height: 100%;
	width: 100%;
	margin: 0;
}

body {
	height: 100%;
	width: 100%;
	margin: 0;
	font: 12px/1 Tahoma, Helvetica, Arial, "宋体", sans-serif;
	overflow: hidden;
}

.titleTd {
	text-align: left;
	width: 100px;
}
</style>
</head>
<body>
	<div id="vertical" style="width:100%;height:100%; margin: 0 auto;">
		<div id="head" class="k-header k-footer footerCss">
			<table style="width:100%;border:0px;">
				<tr>
					<td style="width:20px;text-align: left;vertical-align: middle;"><img
						src="${contextPath}/images/users.png"
						style="vertical-align: middle;width:16px;padding-left: 5px;" /></td>
					<td
						style="width:120px;text-align:left;vertical-align: middle;padding-left:5px;">
						流程定义》选择角色</td>
					<td style="vertical-align: middle;text-align:left;">
						<div id="toolbar" style="border:0px;text-align:left;">
							<button id="confirmBt" type="button">确认选择</button>
							<button id="resetBt" type="button">重新选择</button>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div id="content">
			<div id="filterCondition"
				style="width:95%;height:60px;float: inherit;">
				<table style="width:95%;height:100%;margin:auto">
					<tr>
						<td class="titleTd"><img
							src="${contextPath}/images/bullet_blue.png"
							style="vertical-align: middle;" /><span
							style="word-spacing:8px; letter-spacing: 1px;">编号：</span></td>
						<td><input type="text" name="id" class="k-textbox" /></td>
						<td class="titleTd"><img
							src="${contextPath}/images/bullet_blue.png"
							style="vertical-align: middle;" /><span
							style="word-spacing:8px; letter-spacing: 1px;">名称：</span></td>
						<td><input type="text" name="name" class="k-textbox" /></td>
						<td class="titleTd"><img
							src="${contextPath}/images/bullet_blue.png"
							style="vertical-align: middle;" /><span
							style="word-spacing:8px; letter-spacing: 1px;">编码：</span></td>
						<td><input type="text" name="code" class="k-textbox" /></td>
						<td style="vertical-align:middle;width:120px;"><button
								id="searchBt" type="button">查询</button></td>
					</tr>
				</table>
			</div>
			<div id="grid"></div>
		</div>
	</div>
	<script type="text/javascript">
	var contextPath = '${contextPath}';
	var webPath = '${webPath}';
	//初始化布局
	window.MSPointerEvent = null;
	window.PointerEvent = null;
	var mainHeight = $(window).height();
	var mainWidth = $(window).width();
	$('#vertical').height(mainHeight);
	$("#vertical").kendoSplitter({
		orientation : "vertical",
		panes : [ {
			collapsed : false,
			resizable : false,
			scrollable : false,
			collapsible : false,
			size : "30px"
		}, {
			collapsed : false,
			scrollable : false
		} ]
	});
  $(document).ready(function(){
	  jQuery("#grid").kendoGrid({
        "columnMenu": true,
        "dataSource": {
            "schema": {
                "total": "total",
                "model": {
                    "fields": {
                        "roleId": {
                            "type": "string"
                        },
                        "name": {
                            "type": "string"
                        },
                        "code": {
                            "type": "string"
                        },
                        "content": {
                            "type": "string"
                        },
                        "createTime": {
                            "type": "date",
							"format": "{0: yyyy-MM-dd}"
                        },
                        "lastLoginTime": {
                            "type": "date",
							"format": "{0: yyyy-MM-dd}"
                        }
                    }
                },
                "data": "rows"
            },
            "transport": {
                "parameterMap": function(options) {
                    return JSON.stringify(options);
                },
                "read": {
				    "contentType": "application/json",
                    "type": "POST",
                    "url": "${contextPath}/rs/sys/role/data"
                }
            },
			            "serverFiltering" : true,
						"serverSorting" : true,
						"pageSize" : 15,
						"serverPaging" : true,
						"serverGrouping" : false,
						},
						"dataBound": function(e) {
						   gridInit();
						 },
						"height" : 505,
						"reorderable" : true,
						"filterable" : false,
						"sortable" : true,
						"pageable" : {
							"refresh" : true,
							"pageSizes" : [ 10, 15, 30,
									50, 100 ],
							"buttonCount" : 15
						},
                     "columns": [{
									"field" : "roleId",
									"headerTemplate" : "<input type=\"checkbox\" name=\"checkall\" onclick=\"selectAll(this)\"/><label for=\"check-all\">全选</label>",
									"width" : "60px",
									"lockable" : false,
									"locked" : false,
									"filterable" : false,
									"sortable" : false,
									"template" : "<input type=\"checkbox\" name=\"rid\" value=\"#:roleId#\" onclick=\"selectRow(this)\"/>"
												},
						   {
					           "field": "roleId",
					           "title": "角色编号",
					           "width": "100px",
					           "lockable": false,
					           "locked": false
					       },
					       {
					           "field": "name",
					           "title": "角色名称",
					           "width": "150px",
							"lockable": false,
					           "locked": false
					       },
					       {
					           "field": "code",
					           "title": "编码",
					           "width": "120px",
					           "locked": false
					       },
					       {
					           "field": "content",
					           "title": "描述",
					           "width": "250px",
							"sortable": false,
							"filterable": {
					                   "cell": {
										"showOperators": false,
										 "suggestionOperator": "contains",
										"operator": "contains"
					                   }
					               }
					       },
						{
							"command": [
								{
					               "text": "用户设置",
					               "name": "roleUsers",
					               "click": function showDetails(e) {
									 dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
									 var link = "${contextPath}/mx/sys/role/roleUsers?roleId="+ dataItem.roleId;
					                    roleUsers(link);
																				}
																			} ],
																			"width" : "100px",
																		} ],
																"scrollable" : {},
																"resizable" : true,
																"groupable" : false
															});
					
											//根据获取的参数设置页面选中
					function initSelect(){
						var selectedVal="${selectedVal}";
						var selectedNameVal="${selectedNameVal}";
						var selectedArr,selectedNameArr;
						if(selectedVal){
						   selectedArr=selectedVal.split(",");
						   selectedNameArr=selectedNameVal.split(",");
						   $.each(selectedArr,function(i,item){
						       selectedIds.put(item,item);
						       selectedNames.put(selectedNameArr[i],selectedNameArr[i]);
						   });
						  gridInit();
						}
					}
					initSelect();
});
        var selectedIds=new Map();
        var selectedNames=new Map();
        //checkbox选中、取消选中行操作
 		function selectRow(obj) {
			var grid = $("#grid").data("kendoGrid");
			var checkstatus = obj.checked;
			var selRow = $(obj).closest("tr"), dataItem = grid.dataItem(selRow);
			if (checkstatus) {
				//grid.select(selRow);
				selRow.addClass("k-state-selected");
				selectedIds.put($(obj).val(),$(obj).val());
				selectedNames.put(dataItem.name,dataItem.name);
			} else {
				//grid.clearSelection(selRow);
				selRow.removeClass("k-state-selected");
				$("input[name='checkall']").prop("checked",false);
				selectedIds.remove($(obj).val());
				selectedNames.remove(dataItem.name);
			}
		}
		//全选
		function selectAll(obj){
		    var grid = $("#grid").data("kendoGrid");
		    var checkstatus=obj.checked;
		    var checkboxs=$("input[name='rid']");
		    var selRows = checkboxs.closest("tr");
		    if(checkstatus){
		      checkboxs.prop("checked",true);
		      selRows.addClass("k-state-selected");
		      $.each(checkboxs, function(i, checkbox) {
		            selectedIds.put($(checkbox).val(),$(checkbox).val());
		            var selRow = $(checkbox).closest("tr"), dataItem = grid.dataItem(selRow);
		            selectedNames.put(dataItem.name,dataItem.name);
		      });
		    }
		    else{
		     checkboxs.prop("checked",false);
		     selRows.removeClass("k-state-selected");
		     selectedIds.clear();
		     selectedNames.clear();
		    }
		}
		//grid数据加载完成后执行事件 checkbox选中状态初始化
		function gridInit(){
		    $.each(selectedIds.elements,function(i,selectedId){
		       //默认选中打钩
		       var checkbox=$("input[type='checkbox'][value='"+selectedId.key+"']");
		       if(checkbox){
		       checkbox.prop("checked",true);
		       var selRow = checkbox.closest("tr");
		       selRow.addClass("k-state-selected");
		       }
		    });
		}
		$("#confirmBt").kendoButton({
			imageUrl : "${contextPath}/images/save_as.png"
		});
		var confirmBt = $("#confirmBt").data("kendoButton");
		//确认按钮点击事件绑定
		confirmBt.bind("click",function(e) {
		        if(selectedIds&&selectedIds.size()>0){
				    /*  console.log(selectedIds.values().join(","));
				     parent.setPropertyVal(selectedIds.values().join(","),selectedNames.values().join(","));
				     parent.closeAssignChooseDialog(); */
		        	 setValue(selectedIds.values().join(","), selectedNames.values().join(","));
				}
				else{
				   if(confirm("未选择角色，窗口将直接关闭，确认继续？")){
				      /* parent.setPropertyVal("","");
				      parent.closeAssignChooseDialog(); */
					   setValue('','');
				   }
				}
		});
		
		function setValue(values, names){
			var targetId = "${param.targetId}", fn = "${param.fn}" , $parent = window.opener || window.parent;
		     if(fn && $parent[fn]){
		    	 var $target = $($parent.document.getElementById(targetId));
		    	 $parent[fn].call($target, {data : {
		    		 role_name : names,
		    		 role_id : values
		    	 }});
		    	 $target.attr("select-type", "role");
		    	 window.close();
		     } else {
			     $parent.setPropertyVal(values, names);
			     $parent.closeAssignChooseDialog();
		     }
		}
		
		$("#resetBt").kendoButton({
			imageUrl : "${contextPath}/images/gem_cancel_1.png"
		});
		var resetBt = $("#resetBt").data("kendoButton");
		//重置按钮点击事件绑定
		resetBt.bind("click",function(e) {
				$.each(selectedIds.elements,function(i,selectedId){
		           //默认选中打钩
			       var checkbox=$("input[type='checkbox'][value='"+selectedId.key+"']");
			       if(checkbox){
			       checkbox.prop("checked",false);
			       var selRow = checkbox.closest("tr");
			       selRow.removeClass("k-state-selected");
		          }
		        });
		        $("input[name='checkall']").prop("checked",false);
				selectedIds=new Map();
				selectedNames=new Map();
		});
		//查询按钮
		$("#searchBt").kendoButton({
			imageUrl : "${contextPath}/images/search.png"
		});
		var searchBt = $("#searchBt").data("kendoButton");
		//查询按钮事件绑定
		searchBt.bind("click", function(e) {
			var paramVal = {};
			var filterObjs = $("#filterCondition input");
			var filters = new Array();
			$.each(filterObjs, function(i, filterObj) {
				if ($(filterObj).val() != '') {
					filter = new Object();
					filter.field = $(filterObj).attr("name");
					filter.operator = "contains";
					filter.value = $(filterObj).val();
					filters.push(filter);
				}
			});
			paramVal = JSON.stringify(filters);
			var grid = $("#grid").data("kendoGrid");
			var filter = JSON.parse(paramVal);
			grid.dataSource.filter(filter);
			grid.dataSource.read();

		});
		function roleUsers(link) {
			jQuery.layer({
				type : 2,
				maxmin : true,
				shadeClose : true,
				title : "角色用户",
				closeBtn : [ 0, true ],
				shade : [ 0.8, '#000' ],
				border : [ 10, 0.3, '#000' ],
				offset : [ '20px', '' ],
				fadeIn : 100,
				area : [ '520px', (jQuery(window).height() - 40) + 'px' ],
				iframe : {
					src : link
				}
			});
		}
	</script>
</body>
</html>