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
<script type="text/javascript" src="${contextPath}/webfile/js/jquery.cookie.js" ></script>
<script type="text/javascript">
	var mtxx = {
		pageCategory : $.cookie('pageCategory')
	};
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
					area : [ "520px", "450px" ],
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
								data.set("content", "<iframe style=\"border: 0;width:100%;height:99%\" prosrc=\"'+contextPath+'/mx/form/page/viewPage?id="+node.id+"'+'\"></iframe>");
								data.set("id", node.id);
								data.set("contentUrlText", node.name);
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
								data.set("content", "<iframe style=\"border: 0;width:100%;height:99%\" prosrc=\"'+contextPath+'/mx/bi/chart/viewChart?chartId="+node.id+"'+'\"></iframe>");
								data.set("id", node.id);
								data.set("contentUrlText", node.name);
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
	function upFun(e){
        var $target = $(e.target),$tr = $target.parents("tr"), $grid = $(e.delegateTarget).data("kendoGrid"),
        	index = $tr.index(),items = $grid.dataItems()  ;
        if(index){
            $grid.dataSource.sync();
        	var temp = items[index -1] ;
            items[index-1] = items[index];
            items[index] = temp ;
            var dataSource = new kendo.data.DataSource({
            	  data: items 
            });
            $grid.setDataSource(dataSource);
            $grid.dataSource.sync();
        }	    
	}
	function downFun(e){
        var $target = $(e.target),$tr = $target.parents("tr"), $grid = $(e.delegateTarget).data("kendoGrid"),
        	index = $tr.index(),items = $grid.dataItems()  ;
        if(items.length -1 != index){
            $grid.dataSource.sync();
        	var temp = items[index + 1] ;
            items[index + 1] = items[index];
            items[index] = temp ;
            var dataSource = new kendo.data.DataSource({
            	  data: items 
            });
            $grid.setDataSource(dataSource);
            $grid.dataSource.sync();
        }	    
	}
	$(document).ready(function() {
		//获取页面参数
		var gridDataSource =  [] ;
		var parentId = parent.document.getElementById("${id}").value ;
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

		
		$("#widget_ds_grid").kendoGrid({
			dataSource : gridDataSource,
			editable : true,
			selectable : "rows",
			toolbar : [ 'create', 'save' ],
			columns : [ {
				field : 'text',
				title : '选卡项名称',
				editor : htmlExpEditor
			},{
				field : 'imageName',
				title : '选卡项图片',
				editor : picUrlTextboxEditor
			},{
				field : 'contentUrlText',
				title : '选卡项内容关联页面',
				editor : contextUrlTextboxEditor
			}, {
				command : [{ 
	        		name: "destroy" 
	        	},{
			         name : "up",
			         text : "↑" ,
			         click: upFun
		        },{
			         name : "down",
			         text : "↓" ,
			         click: downFun
		        }
		      ],
				title : '',
				width : '225px'
			} ],
			saveChanges : function(e) {
				var values = this.dataItems();
				var names = ""
				for (var i = 0; i < values.length; i++) {
					if (values[i].text == undefined || values[i].contentUrlText == undefined || values[i].text.trim() == "" || values[i].contentUrlText.trim() == "") {
						layer.alert("名称和关联页面为必填", 3);
						return;
					}
					names += values[i].name + " "
				}
				//把数据返回到主页面
				parent.document.getElementById("${id}").value = JSON.stringify(values);
				parent.document.getElementById("${name}").value = names ;

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


		$("#pageCategory").kendoDropDownList({
			dataSource: new kendo.data.DataSource({
				transport: {
					read: {
						url: '${contextPath}/rs/form/formPageCategory/all',
						data: {
						},
						type: 'post',
						dataType: 'json',
					}
				}
			}),
			dataTextField: "name",
			dataValueField: "id",
			animation: false,
			change : function(e){
				var value = this.value() ;
				//$.cookie('pageCategory', value , { expires: 30 });
				mtxx.zTree = $.fn.zTree.getZTreeObj("selectPageTree");
				mtxx.zTree.setting.async.otherParam = {
					pageCategory : value 
				};
				mtxx.zTree.reAsyncChildNodes(null, 'refresh');
			}
		});

		if(mtxx.pageCategory ){
			$("#pageCategory").data("kendoDropDownList").value(mtxx.pageCategory);
		}
	});
</script>
</head>
<body>
	<div id="widget_ds_content" style="height: 440px; width: 100%">
		<div id="widget_ds_grid" ></div>
	</div>

	<div id="selectPage" style="display: none; width: 515px; height: 400px;">
		<div id="tabstrip" style="width: 510px;height: 370px">
			<ul>
				<li>页面</li>
				<li>图表</li>
			</ul>
			<div style="width: 480px;height: 330px;">
				<div>模块：<div id="pageCategory"></div></div>
				<div class="ztree" id="selectPageTree" style="width: 100%; overflow: auto;"></div>
			</div>
			<div style="width: 480px;height: 330px;">
				<div class="ztree" id="selectPageTree2" style="width: 100%; overflow: auto;"></div>
			</div>
		</div>
	</div>
</body>
</html>