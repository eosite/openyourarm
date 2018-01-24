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
<title>企业通服务模板管理</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<!--ztree-->
<link rel="stylesheet" href="${contextPath}/scripts/ztree/css/metroStyle/metroStyle.css" type="text/css"/>
<script type="text/javascript" src="${contextPath}/scripts/map.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/kendoConfirm.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/dateutil.js"></script>
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
.modalTitle{
	font: 13px/1 Tahoma, Helvetica, Arial, "微软雅黑", sans-serif;
}
</style>
</head>
<body>
		<div id="content">
		  <div id="horizontal" style="height:100%; width: 100%;">
			<div id="tree" style="border: 0px;">
					<div class="zTreeDemoBackground left">
						<ul id="tmpCategory" class="ztree"></ul>
					</div>
			</div>
			<div id="list">
			  <div id="grid"></div>
			</div>
		   </div>
	</div>
	<div id="addDialog">
		
	</div>
	<div id="editorDialog">
	
	</div>
	<div id="moveToCategoryDialog">
		<div class="zTreeDemoBackground left" style="height: 320px;">
			<ul id="tmpCategoryChoose" class="ztree"></ul>
		</div>
		<button id="sureBt" type="button">确认</button>
	</div>
	<div id="importDialog">
		<table style="width:90%;height:90%">
			<tr>
				<td style="width:100px;height:30px;">选择文件：</td>
				<td><input type="file" name="uploadfile" id="uploadfile"
					style="width: 250px;" /></td>
			</tr>
		</table>
	</div>
	<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.core.min.js"></script>
	<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.excheck.min.js"></script>
	<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.exedit.min.js"></script>
	<script type="text/javascript">
		var contextPath = '${contextPath}';
		var webPath = '${webPath}';
		//初始化布局
		window.MSPointerEvent = null;
		window.PointerEvent = null;
		var mainHeight = $(window).height();
		var mainWidth = $(window).width();
		$('#horizontal').height(mainHeight);
		var splitter = $("#horizontal").kendoSplitter({
		panes : [ {
			collapsed : false,
			collapsible : false,
			collapsedSize : "0px",
			max : "200px",
			resizable : false,
			size : "200px",
			scrollable : true
		}, {
			size : "500px",
			scrollable : true
		} ]
	   });
		$(document).ready(function() {
			jQuery("#grid").kendoGrid({
		"columnMenu" : true,
		"selectable":"multiple",
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
                        "path_": {
                                                        "type": "string"
                        },
                        "paasId": {
                                                        "type": "string"
                        },
                        "reqType": {
                                                        "type": "string"
                        },
                        "updateTime": {
							"type": "date",
							"format": "{0: yyyy-MM-dd}"
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
                    "url": "<%=request.getContextPath()%>/rs/teim/tmp/data"
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
		"change": function(e) {
					var row = this.select();
					//如果是多选"移动到分类"按钮可操作
					if(row.length>1){
						initToolBarStatus();
						var toolbar = $("#toolbar").data("kendoToolBar");
						toolbar.enable("#moveTo");
					}
					else{
			          var data = this.dataItem(row);
					  controlToolBarStatus(data);
					}
		},
		"toolbar":  kendo.template(jQuery("#condition-01").html()),
                "columns": [
			   {
				"field": "startIndex",
				"title": "序号",
                "width": "80px",
				"lockable": false,
				"locked": false
                },{
					"field" : "tmpId",
					hidden: true
				},{
					"field" : "name",
					"title" : "模板名称",
					"width" : "200px"
				}, {
					"field" : "path_",
					"title" : "路径",
					"width" : "200px"
				}, {
					"field" : "reqType",
					"title" : "请求方式",
					"width" : "100px"
				} ,  {
				"field": "updateTime_date",
				"title": "更新时间",
				"width": "120px",
				"format": "{0: yyyy-MM-dd}",
				"filterable": {
					"ui": "datetimepicker"  
				},
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
                { type: "button",id: "create",text: "新建",imageUrl:contextPath+"/images/page_add.png",click : createTmp},
				{ type: "button",id: "createWs",text: "新建WS",imageUrl:contextPath+"/images/page_add.png",click : createWsTmp},
				{ type: "button",id: "edit",text: "编辑",enable : false,imageUrl:contextPath+"/images/page_edit.png",click : editTmp},
				{ type: "separator" },
				{
					type : "button",
					id : "delete",
					text : "删  除",
					enable : false,
					imageUrl : contextPath + "/images/page_delete.png",
					click : openDeleteTmpDialog
				}, {
					type : "button",
					id : "moveTo",
					text : "移动到",
					enable : false,
					imageUrl : contextPath + "/images/move_to_folder.png",
					click : openMoveToCategoryDialog
				}
		        ]
		    });
				window.setTimeout(function(){
				$("#toolbar").append($("#condition-01").html());
			 }, 10);
		});
		
		var gridDataSource = new kendo.data.DataSource(
				{
					schema : {
						total : "total",
						data : "records",
						parse : function(response) {
							if (response && response.data) {
								var tmp, records = [];
								$.each(response.data, function(i, v) {
									//遍历行数据
									$.extend({},tmp,v);
									records.push(tmp);
								});
								response.records = records;
							}
							return response;
						}
					},
					transport : {
						"parameterMap": function(options) {
							return JSON.stringify(options);
						},
						read : {
							url : "${contextPath}/rs/teim/tmp/data",
							contentType: "application/json",
							dataType : "json",
							type : "POST"
						}
					},		
                    pageSize : 10,
					serverPaging : true,
					"serverFiltering": true,
                    "serverSorting": true
				});
		//刷新grid
		function refreshGrid(){
			var grid = $("#grid").data(
			"kendoGrid");
			grid.dataSource.read();
		}
		var contextPath='${contextPath}';
		//创建模板
		function createTmp() {
			$("#addDialog")
			.kendoWindow(
					{
						width : 800,
						height : 600,
						title : "<img src=\"${contextPath}/images/document_edit.png\"/><span class=\"modalTitle\">新增服务模板信息</span>",
						visible : false,
						modal : true
					});
			        var url = "/mx/teim/tmp/edit?categoryId="+selectedNode.id;
			        $("#addDialog").data("kendoWindow").title("<img src=\"${contextPath}/images/document_edit.png\"/><span class=\"modalTitle\">新增服务模板信息</span>");
					$("#addDialog").data("kendoWindow").refresh(
							webPath + url);
					$("#addDialog").data("kendoWindow").toggleMaximization();
					$("#addDialog").data("kendoWindow").open();
		}
		//创建WS模板
		function createWsTmp() {
			$("#addDialog")
			.kendoWindow(
					{
						width : 800,
						height : 600,
						title : "<img src=\"${contextPath}/images/document_edit.png\"/><span class=\"modalTitle\">新增服务模板信息</span>",
						visible : false,
						modal : true
					});
			        var url = "/mx/teim/tmp/ws/edit?categoryId="+selectedNode.id;
			        $("#addDialog").data("kendoWindow").title("<img src=\"${contextPath}/images/document_edit.png\"/><span class=\"modalTitle\">新增WS服务模板信息</span>");
					$("#addDialog").data("kendoWindow").refresh(
							webPath + url);
					$("#addDialog").data("kendoWindow").toggleMaximization();
					$("#addDialog").data("kendoWindow").open();
		}
		//修改模板方法
		function editTmp() {
		  //获取选中行
		  var grid=$("#grid").data("kendoGrid"); 
		  var row = grid.select();
          var data = grid.dataItem(row);
    	  	$("#addDialog")
			.kendoWindow(
					{
						width : 800,
						height : 600,
						title : "<img src=\"${contextPath}/images/document_edit.png\"/><span class=\"modalTitle\">编辑服务模板信息</span>",
						visible : false,
						modal : true
					});
			        var url = "/mx/teim/tmp/edit?tmpId="+data.id+"&categoryId="+selectedNode.id;
					if(data.reqType=='soap1.1'||data.reqType=='soap1.2'){
				      url="/mx/teim/tmp/ws/edit?tmpId="+data.id+"&categoryId="+selectedNode.id;
			       }
			        $("#addDialog").data("kendoWindow").title("<img src=\"${contextPath}/images/document_edit.png\"/><span class=\"modalTitle\">编辑服务模板信息</span>");
					$("#addDialog").data("kendoWindow").refresh(
							webPath + url);
					$("#addDialog").data("kendoWindow").toggleMaximization();
					$("#addDialog").data("kendoWindow").open();
		}
		//弹出删除窗口
		function openDeleteTmpDialog() {
			var grid=$("#grid").data("kendoGrid"); 
			var row = grid.select();
	        var data = grid.dataItem(row);
			if (data.id) {
				var okTemplate = {
					text : "&nbsp&nbsp确定&nbsp&nbsp",
					callback : function(kendoConfirm) {
						kendoConfirm.close();
						deleteTmp();
					}
				};
				kendo.confirm("确认删除模板\"" + data.name + "\"？", {
					title : "<font size=\"2\">系统提醒</font>",
					width : "250px",
					height : "150px"
				}, okTemplate);
			}
		}
		//删除模型方法
		function deleteTmp() {
			var grid=$("#grid").data("kendoGrid"); 
			var row = grid.select();
	        var data = grid.dataItem(row);
	        if (data.id) {
			$.ajax({
				url : contextPath+"/rs/teim/tmp/delete",
				type : "post",
				async : false,
				dataType : "json",
				data : {
					tmpId : data.id
				},
				success : function(data) {
					if (data) {
						if (data.statusCode) {
							if (data.statusCode == 200) {
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
		//移动到分类
		function openMoveToCategoryDialog(){
			initCategoryChooseTree();
			 $("#moveToCategoryDialog")
				.kendoWindow(
						{
							width : 600,
							height : 400,
							title : "<img src=\"${contextPath}/images/document_edit.png\"/><span class=\"modalTitle\">移动到分类</span>",
							visible : false,
							modal : true
						});
			$("#moveToCategoryDialog").data("kendoWindow").open();
			$("#moveToCategoryDialog").data("kendoWindow").center();
		}
		function closeDialog(id){
			$("#"+id).data("kendoWindow").close();
		}
		function initCategoryChooseTree(){
			var setting = {
            view: {
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true,
					idKey: "id",
					pIdKey: "parentId",
					rootPId: 0
                }
            },
            edit: {
                enable: false
            }
            };
			//获取当前分类节点数据
			var treeObj = $.fn.zTree.getZTreeObj("tmpCategory");
            var nodes = treeObj.getNodeByParam("id", 0, null).children;
			$.fn.zTree.init($("#tmpCategoryChoose"), setting, nodes);
		}
		$(document).ready(function(){	
		    //添加到分类确认按钮
			 $("#sureBt").kendoButton({
				imageUrl : "${contextPath}/images/okay.png"
			 });
			  var sureBt = $("#sureBt").data("kendoButton");
				  //确认按钮事件绑定
				  sureBt.bind("click",function(e) {
				   //获取选中节点
				   var treeObj = $.fn.zTree.getZTreeObj("tmpCategoryChoose");
                   var nodes = treeObj.getSelectedNodes();
				   if(nodes.length==0){
					   alert("请选择分类");
					   return;
				   }
				   var categoryId=nodes[0].id;
				   //获取当前选中的行
				   var grid=$("#grid").data("kendoGrid"); 
				   var rows = grid.select();
				   if(rows.length==0){
					   alert("请选择模板");
					   return;
				   }
				   var data,tmpId;
				   $.each(rows,function(i,row){
					   data = grid.dataItem(row);
					   //获取选中的模板ID
					   if(i==0)
					    tmpId="'"+data.id+"'";
				       else
						tmpId+=",'"+data.id+"'";   
				   });
				   $.ajax({
					url : contextPath+"/rs/teim/tmp/moveToCategory",
					type : "post",
					async : false,
					dataType : "json",
					data : {
						tmpId :tmpId,
						categoryId:categoryId
					},
					success : function(data) {
						if (data) {
							if (data.result) {
								if (data.result == 1) {
									refreshGrid();
									alert("添加到分类成功！");
									$("#moveToCategoryDialog").data("kendoWindow").close();
								} else {
									alert("添加到分类失败！");
								}
							}
						}
					},
					error : function() {
						alert("添加到分类异常！");
					}

				});
				});
		 });

		//控制菜单按钮状态
		function controlToolBarStatus(node) {
			var toolbar = $("#toolbar").data("kendoToolBar");
			if (toolbar) {
				toolbar.enable("#create");
				toolbar.enable("#edit", false);
				toolbar.enable("#delete", false);
				toolbar.enable("#moveTo", false);
					if (!node.id || node.id== '') {
						toolbar.enable("#create");
						toolbar.enable("#edit", false);
						toolbar.enable("#delete", false);
						toolbar.enable("#moveTo", false);
					} else {
						toolbar.enable("#create");
						toolbar.enable("#edit");
						toolbar.enable("#delete");
						toolbar.enable("#moveTo");
					}
			}
		}
		function initToolBarStatus(){
			var toolbar = $("#toolbar").data("kendoToolBar");
			if (toolbar) {
				toolbar.enable("#create");
				toolbar.enable("#edit", false);
				toolbar.enable("#delete", false);
				toolbar.enable("#moveTo", false);
			}
		}
		//刷新grid
		function refreshGrid(){
			var grid = $("#grid").data(
			"kendoGrid");
			grid.dataSource.read();
		}
	</script>
	  	<script type="text/javascript">
        var setting = {
            view: {
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom,
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true,
					idKey: "id",
					pIdKey: "parentId",
					rootPId: -1
                }
            },
            edit: {
                enable: true,
				removeTitle:"删除服务模板分类",
				renameTitle:"修改服务模板名称",
				showRemoveBtn:showRemoveBtn,
				showRenameBtn:showRenameBtn
            },
			callback:{
				onClick: zTreeOnClick,
				onRename:zTreeOnRename,
				beforeRemove:zTreeBeforeRemove,
				beforeDrop:zTreeBeforeDrop,
				onDrop: zTreeOnDrop
			}
        };
		function showRemoveBtn(treeId, treeNode) {
		    //顶层和未分类节点不显示删除按钮
			if(treeNode.id=="-1"||treeNode.id==""){
				return false;
			}
			return true;
		}
		function showRenameBtn(treeId, treeNode) {
			//顶层和未分类节点不显示重命名按钮
		    if(treeNode.id=="-1"||treeNode.id==""){
				return false;
			}
			return true;
		}
        $(document).ready(function(){		
           $.fn.zTree.initZtree($("#tmpCategory"),url,setting);
        });
		var url="${contextPath}/rs/teim/category/json";
        $.fn.extend($.fn.zTree,{
		  initZtree:function (container,url,setting){
			  $.ajax({
				url : url,
				type : "post",
				async : false,
				contentType : "application/json",
				dataType : "json",
				success : function(rdata) {
					if($.isEmptyObject(rdata)){
						rdata=new Array();
					}
					rdata.push({"id":"-1","parentId":"-1","name":"--全部--","open":true});
					rdata.push({"id":"0","parentId":"-1","name":"已分类模板","open":true});
					rdata.push({"id":"-2","parentId":"-1","name":"未分类模板","open":true});
					var treeObj =$.fn.zTree.init(container,setting, rdata);
					  //默认选中第一个节点
		            var node = treeObj.getNodesByParam("id", "-1", null);
		            treeObj.selectNode(node);
					$("#tmpCategory_1_a").click();
				},
				error : function() {
					console.log("获取分类数据失败");
				}

		   }
		  );
		}});
        var newCount = 1;
		//新增节点事件
        function addHoverDom(treeId, treeNode) {
			//顶层和未分类节点禁用新增按钮
			if(treeNode.id=="-1"||treeNode.id==""){
				return;
			}
            var sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
            var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                + "' title='新增流程分类' onfocus='this.blur();'></span>";
            sObj.after(addStr);
            var btn = $("#addBtn_"+treeNode.tId);
            if (btn) btn.bind("click", function(){
                
                //zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"流程分类" + (newCount++)});
				return zTreeOnAdd(treeId,treeNode);
            });
        };
		//新增节点后台处理
		function zTreeOnAdd(treeId,treeNode){
			 var pId=treeNode.id;
			 var pTreeId=treeNode.treeId;
			 var level=treeNode.level+1;
             var url=contextPath+"/rs/teim/category/add";
			 var name="新建分类" + (newCount++);
			 var params={"pId":pId,"pTreeId":pTreeId,"level":level,"name":name};
			 var result=false;
			 $.ajax({
				url : url,
				type : "post",
				async : false,
				data:params,
				contentType : "application/x-www-form-urlencoded",
				dataType : "json",
				success : function(rdata) {
					if(rdata!=null&&rdata.result==1){
						var zTree = $.fn.zTree.getZTreeObj(treeId);
						zTree.addNodes(treeNode, {id:rdata.id, pId:pId,name:name});
						result= true;
					}else{
						result= false;
					}
					
				},
				error : function() {
					console.log("新增服务模板分类失败");
					result= false;
				}
		   });
		   return result;
		}
        function removeHoverDom(treeId, treeNode) {
            $("#addBtn_"+treeNode.tId).unbind().remove();
        };
		//ztree树节点重命名事件
		function zTreeOnRename(event, treeId, treeNode, isCancel){
			 var categoryId=treeNode.id;
			 var name=treeNode.name;
			 var url=contextPath+"/rs/teim/category/rename";
			 var params={"categoryId":categoryId,"name":name};
			 var result=false;
			 $.ajax({
				url : url,
				type : "post",
				async : false,
				data:params,
				contentType : "application/x-www-form-urlencoded",
				dataType : "json",
				success : function(rdata) {
					result= true;
				},
				error : function() {
					console.log("重命名失败");
					result= false;
				}

		   }
		  );
		  return result;
		}
		function zTreeBeforeDrop(treeId, treeNodes, targetNode, moveType) {
          if(targetNode==null||(targetNode.id==0&&moveType=='prev')){
				 return false;
			 }
        };
		function zTreeOnDrop(event, treeId, treeNodes, targetNode, moveType) {
			 
			 var categoryId=treeNodes[0].id;
		     var treeId=(targetNode.treeId_!=undefined&&targetNode.treeId_!=null?targetNode.treeId_:"")+categoryId+"|";
			 var url=contextPath+"/rs/teim/category/move";
			 var params={"categoryId":categoryId,"pId":targetNode.id,"treeId":treeId,"moveType":moveType};
			 var result=false;
			 $.ajax({
				url : url,
				type : "post",
				async : false,
				data:params,
				contentType : "application/x-www-form-urlencoded",
				dataType : "json",
				success : function(rdata) {
					result= true;
				},
				error : function() {
					console.log("移动失败");
					result= false;
				}

		   }
		  );
		  return result;
		}
		//ztree树节点移除事件
		function zTreeBeforeRemove(treeId, treeNode){
			
			if(!confirm("确定删除分类[" + treeNode.name + "]吗?")){
				return false;
			}
			
			 var categoryId=treeNode.categoryId || treeNode.id;
			 var url=contextPath+"/rs/teim/category/delete";
			 var params={"categoryId":categoryId};
			 var result=false;
			 $.ajax({
				url : url,
				type : "post",
				async : false,
				data:params,
				contentType : "application/x-www-form-urlencoded",
				dataType : "json",
				success : function(rdata) {
					result= true;
				},
				error : function() {
					console.log("删除分类失败");
					result= false;
				}
		   }
		  );
		  return result;
		}
		var selectedNode;
		//ztree树节点点击事件
		function zTreeOnClick(event, treeId, treeNode){
			selectedNode=treeNode;
			var grid = $("#grid").data("kendoGrid");
			//if(treeNode.id!="-1"){
			 grid.dataSource.transport.options.read.url="${contextPath}/rs/teim/tmp/data?categoryId="+treeNode.id;
			 grid.dataSource.read();
			//}
			initToolBarStatus();
		}
		</script>
</body>
<script type="text/html" id='condition-01'>
		<div id="toolbar" style="border:0px;text-align: left;"></div>
	</script>
</html>