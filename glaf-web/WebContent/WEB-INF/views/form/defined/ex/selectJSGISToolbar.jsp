<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<style type="text/css">
.ztree li span.button.tree_folder_ico_open{margin-right:2px; background: url(${contextPath}/images/folder-open.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_folder_ico_close{margin-right:2px; background: url(${contextPath}/images/folder.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_folder_ico_docu{margin-right:2px; background: url(${contextPath}/images/folder.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}

.ztree li span.button.tree_leaf_ico_open{margin-right:2px; background: url(${contextPath}/images/orm.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_leaf_ico_close{margin-right:2px; background: url(${contextPath}/images/orm.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_leaf_ico_docu{margin-right:2px; background: url(${contextPath}/images/orm.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
</style>
<script type="text/javascript">
	var selectedTabStrip ;
	//选取图片	
	function picUrlTextboxEditor(container, options) {
		var id = "picInput_" + parseInt(Math.random() * 1000);
		var input = $('<input id="'+id+'" type="text" class="k-textbox" data-bind="value:'+options.field+'" />');
		input.appendTo(container);

		var dialogWidth = 1024;
		var dialogHeight = 600;
		var url = "${pageContext.request.contextPath}/mx/isdp/global/image/choose?elementId=" + id;
		$(input).bind({
			"click" : function() {
				if (!!window.ActiveXObject || "ActiveXObject" in window) {
					//IE
					window.showModalDialog(url, self, "edge:raised;scroll:0;status:0;help:0;resizable:0;dialogWidth:" + dialogWidth + "px;dialogHeight:" + dialogHeight + "px;center:true", true);
				} else {
					var f = "height=" + dialogHeight + ",width=" + dialogWidth + ",status=0,toolbar=no,menubar=no,location=no,scrollbars=yes,top=500,left=500,resizable=no,modal=yes,dependent=yes,dialog=yes,minimizable=no";
					window.open(url, self, f, true);
				}
			},
			"blur" : function() {
				//获取当前选中行
				var grid = $("#widget_ds_grid").data("kendoGrid");
				var data = grid.dataItem(grid.select());
				if(input.val()){
					data.imageUrl = "'+contextPath+'"+input.val()+"'+'" ;
					data.imageName = input.val() ; 
				}else{
					data.imageUrl = "" ;
					data.imageName = "" ;
				}
			}
		});
	}
	//选取关联页面
	function contextUrlTextboxEditor(container, options) {
		var id = "contextUrlInput_" + parseInt(Math.random() * 1000);
		var input = $('<input id="'+id+'" type="text" class="k-textbox" data-bind="value:'+options.field+'" />');
		input.appendTo(container);

		$(input).bind({
			"click" : function() {
				//选取关联页面
				$.layer({
					type : 1,
					maxmin : true,
					shadeClose : true,
					title : "请选择页面",
					closeBtn : [ 0, true ],
					shade : [ 0, '#000' ],
					border : [ 2, 0.3, '#000' ],
					fadeIn : 100,
					area : [ "300px", "450px" ],
					btns : 2,
					btn : [ '确定', '取消' ],
					page : {
						dom : "#selectPage"
					},
					yes : function(index) {
						if(selectedTabStrip=="页面"){
							var treeObj = $.fn.zTree.getZTreeObj("selectPageTree");
							var nodes = treeObj.getSelectedNodes();
							if (nodes.length == 0) {
								layer.alert("请先选择链接页面！", 3);
								return;
							} else {
								var node = nodes[0];
								var grid = $("#widget_ds_grid").data("kendoGrid");
								var data = grid.dataItem(grid.select());
								data.set("sid", node.id);
								data.set("urlText", node.name);
								grid.dataSource.sync();
								layer.close(index);
							}
						}else if(selectedTabStrip=="图表"){
							var treeObj = $.fn.zTree.getZTreeObj("selectPageTree2");
							var nodes = treeObj.getSelectedNodes();
							if (nodes.length == 0) {
								layer.alert("请先选择图表！", 3);
								return;
							} else {
								var node = nodes[0];
								var grid = $("#widget_ds_grid").data("kendoGrid");
								var data = grid.dataItem(grid.select());
								data.set("sid", node.id);
								data.set("urlText", node.name);
								grid.dataSource.sync();
								layer.close(index);
							}
						}
					}
				});
			},
			"blur" : function() {
				//获取当前选中行
				var grid = $("#widget_ds_grid").data("kendoGrid");
				var data = grid.dataItem(grid.select());
				//data.contextUrl = input.val();
			}
		});

	}
	function getExpression(){
		
	}
	// html编辑器获取变量方法
	function getVarFn() {
	}
	// html编辑器获取参数方法
	function getParamFn() {
	}
	// html编辑器回调函数
	function retHtmlFn(data) {
		if (data) {
			var grid = $("#widget_ds_grid").data("kendoGrid");
			var dataItem = grid.dataItem(grid.select());
			dataItem.text = data.htmlVal;
			dataItem.name = data.htmlVal;
			dataItem.encoded = false;
			dataItem.htmldata = JSON.stringify(data);
			grid.editCell();
		}
	}
	// html编辑器回显内容 
	function initHTMLFn() {
		var grid = $("#widget_ds_grid").data("kendoGrid");
		var dataItem = grid.dataItem(grid.select());
		return JSON.parse(dataItem.htmldata);
	}
	//HTML样式定义定义
	function htmlExpEditor(container, options) {
		var id = "expInput_" + parseInt(Math.random() * 1000);
		var input = $('<input id="'+id+'" type="text" class="k-textbox" readonly="readonly" data-bind="value:'+options.field+'" />');
		input.appendTo(container);
		var _href = "${pageContext.request.contextPath}/mx/html/editor/view?retFn=retHtmlFn&"
				+ "getFieldFn=getExpression&getVarFn=getVarFn&getParamFn=getParamFn&initHTMLFn=initHTMLFn";
		$(input).bind({
			"click" : function() {
				window.open(_href);
			}
		});
	}
	$(document).ready(function() {
		//获取页面参数
		var gridDataSource =  [] ;
		var parentId = parent.document.getElementById("${objelementId}").value ;
		if(parentId){
			gridDataSource = JSON.parse(parentId);
		}
		
		//初始化树
		var setting = {
			view : {
				showIcon : true,
				showLine : true,
				showTitle : false,
				selectedMulti : false
			},
			async : {
				enable : true,
				url : "${pageContext.request.contextPath}/mx/form/defined/getFormPageTree"
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parentId"
				}
			},
			callback : {
			//onClick : zTreeOnClick
			}
		};
		zTree = jQuery.fn.zTree.init(jQuery("#selectPageTree"), setting);
		
		function getUrl(treeId, treeNode) { 
			if(treeNode != null){ 
			    var param = "nodeId="+treeNode.id; 
			    return "${pageContext.request.contextPath}/rs/bi/chart/treeJson?"+param; 
			} 
			return "${pageContext.request.contextPath}/rs/bi/chart/treeJson?nodeCode=report_category"; 
		}

		//初始化树
		var setting2 = {
			async : {
				enable : true,
				url : getUrl
			}
		};
		zTree2 = jQuery.fn.zTree.init(jQuery("#selectPageTree2"), setting2);

		var ctypes =  [{value:'all',text:'其他'},{value:'point',text:'点'},{value:'polyline',text:'线'}] ;
		
		$("#widget_ds_grid").kendoGrid({
			dataSource : {
				autoSync : true,
				data : gridDataSource,
				schema : {
					model : {
						id: "id" ,
						fields : {
							name : {
								editable : true,
								type : 'string'
							},
							urlText : {
								editable : true,
								type : 'string'
							},
							ctype : {
								editable : true,
								type : 'string'
							},
							windowWidth : {
								editable : true,
								type : 'string'
							},
							windowHeight : {
								editable : true,
								type : 'string'
							}
						}
					}
				}
			},
			editable : true,
			selectable : "rows",
			toolbar : [ 'create', 'save' ],
			columns : [ {
				field : 'name',
				title : '窗口标题',
				editor : htmlExpEditor
			},{
				field : 'ctype',
				title : '图类型',
				template : function(dataItem) {
					var ctype = dataItem.ctype || "" ;
					if (ctypes) {
						var size = ctypes.length;
						for (var i = 0; i < size; i++) {
							var c = ctypes[i] ;
							if (c.value == ctype) {
								return c.text;
							}
						}
					}
					return "";
				},
				editor : function(container, options){
					var $tr = $(container).parents('tr');
					var _index = $tr.index();
					$("<input/>").attr('name', options.field).appendTo(container).kendoDropDownList({
						dataValueField : 'value',
						dataTextField : 'text',
						optionLabel: {
							text: "请选择",
						    value: ""
						},
						dataSource : ctypes
					});
				},
			},{
				field : 'urlText',
				title : '关联页面',
				editor : contextUrlTextboxEditor
			},{
				field : 'windowWidth',
				title : '窗口宽度',
			},{
				field : 'windowheight',
				title : '窗口高度',
			}, {
				command : 'destroy',
				title : '',
				width : '80px'
			} ],
			saveChanges : function(e) {
				var values = this.dataItems();
				var names = ""
				for (var i = 0; i < values.length; i++) {
					if (values[i].name == undefined || values[i].urlText == undefined || values[i].name.trim() == "" || values[i].urlText.trim() == "") {
						layer.alert("名称和关联页面为必填", 3);
						return;
					}
					values[i].id = values[i].sid ;
					names += values[i].name + " "
				}
				//把数据返回到主页面
				parent.document.getElementById("${objelementId}").value = JSON.stringify(values);
				parent.document.getElementById("${nameElementId}").value = names ;

				//关闭当前层
				parent.layer.close(parent.layer.getFrameIndex());
			},edit : function(e) {
				if (this.select().length == 0) {
					this.select($(e.container[0]).parents("tr"));
				}
			}
		});
		
		
		function onselect(e){
			selectedTabStrip = $(e.item).find("> .k-link").text();
		}
		$("#tabstrip").kendoTabStrip({select:onselect}).data("kendoTabStrip").select(0);
	});
</script>
</head>
<body>
	<div id="widget_ds_content" style="height: 440px; width: 100%">
		<div id="widget_ds_grid" ></div>
	</div>

	<div id="selectPage" style="display: none; width: 300px; height: 400px;">
		<div id="tabstrip" style="width: 280;height: 370px">
			<ul>
				<li>页面</li>
				<li>图表</li>
			</ul>
			<div class="ztree" id="selectPageTree" style="width: 270px; height: 330px;overflow: auto;"></div>
			<div class="ztree" id="selectPageTree2" style="width: 270px; height: 330px;overflow: auto;"></div>
		</div>
	</div>
</body>
</html>