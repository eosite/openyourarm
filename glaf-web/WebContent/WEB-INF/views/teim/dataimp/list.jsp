<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>服务数据导入定义列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript"
	src="${contextPath}/scripts/kendoConfirm.js"></script>
<script type="text/javascript">
  var contextPath = '${contextPath}';
  var webPath = '${webPath}';
  jQuery(function() {
      jQuery("#grid").kendoGrid({
        "columnMenu": true,
        "dataSource": {
            "schema": {
                "total": "total",
                "model": {
                    "fields": {
                        "id": {
                            "type": "string"
                        },
                        "name": {
                                                        "type": "string"
                        },
                        "appId": {
                                                        "type": "string"
                        },
                        "tmpId": {
                                                        "type": "string"
                        },
                        "emptyTable": {
							"type": "number"
                        },
                        "preSql": {
                                                        "type": "string"
                        },
                        "incrementFlag": {
							"type": "string"
                        },
                        "params": {
                                                        "type": "string"
                        },
                        "targetDatabase": {
                                                        "type": "string"
                        },
                        "targetTable": {
                                                        "type": "string"
                        },
                        "createBy": {
                                                        "type": "string"
                        },
                        "createTime": {
							"type": "date",
							"format": "{0: yyyy-MM-dd}"
                        },
                        "updateBy": {
                                                        "type": "string"
                        },
                        "updateTime": {
							"type": "date",
							"format": "{0: yyyy-MM-dd}"
                        },
                        "deleteFlag": {
							"type": "string"
                        },
                        "startIndex": {
                            "type": "number"
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
                    "url": "<%=request.getContextPath()%>/rs/teim/dataimp/data"
                }
            },
	    "serverFiltering": true,
            "serverSorting": true,
            "pageSize": 10,
            "serverPaging": true
        },
        "height": x_height,
        "reorderable": true,
        "filterable": true,
        "sortable": true,
		"pageable": {
                       "refresh": true,
                       "pageSizes": [5, 10, 15, 20, 25, 50, 100, 200, 500],
                       "buttonCount": 10
                     },
		"selectable": "single",
		"change": function(e) {
					var row = this.select();
					//如果是多选"移动到分类"按钮可操作
					if(row.length>1){
						initToolBarStatus();
						var toolbar = $("#toolbar").data("kendoToolBar");
					}
					else{
			          var data = this.dataItem(row);
					  controlToolBarStatus(data);
					}
		},
		"toolbar":  kendo.template(jQuery("#condition-01").html()),
                "columns": [
                {
				"field": "name",
				"title": "名称",
                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "appId",
				"title": "应用ID",
                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "tmpId",
				"title": "服务模板ID",
                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "incrementFlag",
				"title": "增量导入",
				"width": "90px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "targetDatabase",
				"title": "目标库",
                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "targetTable",
				"title": "目标表",
                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "updateBy",
				"title": "更新人",
                "width": "100px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "updateTime",
				"title": "更新时间",
				"width": "120px",
				"format": "{0: yyyy-MM-dd}",
				"filterable": {
					"ui": "datetimepicker"  
				    },
				"lockable": false,
				"locked": false
                },
                {
				"field": "deleteFlag",
				"title": "有效标识",
				"width": "90px",
				"lockable": false,
				"locked": false
                }
	],
        "scrollable": {},
        "resizable": true,
        "groupable": false
    });
				 $("#toolbar").kendoToolBar({
		        items: [
                { type: "button",id: "create",text: "新建",imageUrl:contextPath+"/images/page_add.png",click : createDataImp},
				{ type: "button",id: "edit",text: "编辑",enable : false,imageUrl:contextPath+"/images/page_edit.png",click : editDataImp},
				{ type: "separator" },
				{
					type : "button",
					id : "delete",
					text : "删  除",
					enable : false,
					imageUrl : contextPath + "/images/page_delete.png",
					click : openDeleteDataImpDialog
				}
		        ]
		    });
				window.setTimeout(function(){
				$("#toolbar").append($("#condition-01").html());
			 }, 10);
  });

        //创建服务数据导入实例
		function createDataImp() {
			$("#addDialog")
			.kendoWindow(
					{
						width : 800,
						height : 600,
						title : "<img src=\"${contextPath}/images/document_edit.png\"/><span class=\"modalTitle\">新增服务数据导入实例</span>",
						visible : false,
						modal : true
					});
			        var url = "/mx/teim/dataimp/edit";
			        $("#addDialog").data("kendoWindow").title("<img src=\"${contextPath}/images/document_edit.png\"/><span class=\"modalTitle\">新增服务数据导入实例</span>");
					$("#addDialog").data("kendoWindow").refresh(
							webPath + url);
					$("#addDialog").data("kendoWindow").toggleMaximization();
					$("#addDialog").data("kendoWindow").open();
		}
		//修改服务数据导入实例
		function editDataImp() {
		  //获取选中行
		  var grid=$("#grid").data("kendoGrid"); 
		  var row = grid.select();
          var data = grid.dataItem(row);
    	  	$("#addDialog")
			.kendoWindow(
					{
						width : 800,
						height : 600,
						title : "<img src=\"${contextPath}/images/document_edit.png\"/><span class=\"modalTitle\">编辑服务数据导入实例</span>",
						visible : false,
						modal : true
					});
			        var url = "/mx/teim/dataimp/edit?id="+data.id;
			        $("#addDialog").data("kendoWindow").title("<img src=\"${contextPath}/images/document_edit.png\"/><span class=\"modalTitle\">编辑服务数据导入实例</span>");
					$("#addDialog").data("kendoWindow").refresh(
							webPath + url);
					$("#addDialog").data("kendoWindow").toggleMaximization();
					$("#addDialog").data("kendoWindow").open();
		}
		//弹出删除窗口
		function openDeleteDataImpDialog() {
			var grid=$("#grid").data("kendoGrid"); 
			var row = grid.select();
	        var data = grid.dataItem(row);
			if (data.id) {
				var okTemplate = {
					text : "&nbsp&nbsp确定&nbsp&nbsp",
					callback : function(kendoConfirm) {
						kendoConfirm.close();
						deleteDataImp();
					}
				};
				kendo.confirm("确认删除服务数据导入实例\"" + data.name + "\"？", {
					title : "<font size=\"2\">系统提醒</font>",
					width : "250px",
					height : "150px"
				}, okTemplate);
			}
		}
		//删除模型方法
		function deleteDataImp() {
			var grid=$("#grid").data("kendoGrid"); 
			var row = grid.select();
	        var data = grid.dataItem(row);
	        if (data.id) {
			$.ajax({
				url : contextPath+"/rs/teim/dataimp/delete",
				type : "post",
				async : false,
				dataType : "json",
				data : {
					id : data.id
				},
				success : function(data) {
					if (data) {
						if (data.result) {
							if (data.result == 1) {
								refreshGrid();
								alert("删除成功！");
							} else {
								alert("删除失败！");
							}
						}
					}
				},
				error : function() {
					alert("删除异常！");
				}

			});
	        }
		}
		//控制菜单按钮状态
		function controlToolBarStatus(node) {
			var toolbar = $("#toolbar").data("kendoToolBar");
			if (toolbar) {
				toolbar.enable("#create");
				toolbar.enable("#edit", false);
				toolbar.enable("#delete", false);
					if (!node.id || node.id== '') {
						toolbar.enable("#create");
						toolbar.enable("#edit", false);
						toolbar.enable("#delete", false);
					} else {
						toolbar.enable("#create");
						toolbar.enable("#edit");
						toolbar.enable("#delete");
					}
			}
		}
		function initToolBarStatus(){
			var toolbar = $("#toolbar").data("kendoToolBar");
			if (toolbar) {
				toolbar.enable("#create");
				toolbar.enable("#edit", false);
				toolbar.enable("#delete", false);
			}
		}
		//刷新grid
		function refreshGrid(){
			var grid = $("#grid").data(
			"kendoGrid");
			grid.dataSource.read();
		}
 </script>
</head>
<body>
<div id="main_content" class="k-content">
<br>
<div id="grid"></div>
</div>
<div id="addDialog">
		
	</div>
	<div id="editorDialog">
	
	</div>  
</body>
<script type="text/html" id='condition-01'>
		<div id="toolbar" style="border:0px;text-align: left;"></div>
	</script>
</html>