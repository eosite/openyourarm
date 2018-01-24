<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
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
<title>Java定义平台</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${contextPath }/webfile/js/jquery.ztree.extends.js" ></script>
<script type="text/javascript" src="${contextPath }/webfile/js/jquery.cookie.js" ></script>
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
.tdCls{
	align:left;
	height:16px;
	border-top:1px solid #ddd;
}
.selectCls{
	width : 90%;
}
.textCls{
	width : 65%;
}
</style>
<script type="text/javascript">

/**
 * 全局参数
 */
var pageGobalParam_ = {
	contextPath : '${contextPath}',
	url : 	"${contextPath}/mx/form/defined",
	selectedNode : null,
	copyNode : null,	//准备复制的对象信息
	pageId : "${pageId}",
	componentName : "${componentName}",
	ruleId : "${ruleId}",
	dataRole : "${dataRole}"
}, contextPath = '${contextPath}';

</script>
</head>
<body >
	<div id="container">
		<div id="vertical" style=''>
			
			<div id="north-pane" class="k-header k-footer footerCss">
				<table style="width: 100%;">
					<tr>
						<td style="width:500px;text-align: left;vertical-align: middle; " ><img
							src="${contextPath}/images/setting_tools.png"
							style="vertical-align: middle;" /> 
							<span style="font-family:Lucida Calligraphy;font-size: 20px;font-weight: bolder;">华闽通达自定义平台</span>
							<span style="font-size: 16px;font-weight: bolder;">(JAVA)</span>
						</td>
						<td style="text-align:right;">
							<button id="sureButton" class='k-button' type="button">确定</button>
						</td>
					</tr>
				</table>
			</div>
			
			<div id="center-pane" style="border:0px;">
				<div style="border:0px;">
					<div style='margin: 2px;'>
						模块 : <div id="pageCategory"></div>
					</div>
					<div style='margin: 2px;'>
						禁用分类:<input type='checkbox' title="显示禁用分类" id='showLockedBox'  />
						<input type='text' class='k-textbox' id='searchTextBox' style='width:60px;' />
						<button id="button-4" class='k-button' type="button">搜索</button>
					</div>
					<div class="ztree" id="tree" style='height:auto;border:0px;' ></div>
				</div>
				<div style="border:0px;">
					<div id="tabstrip-1" class='tabstrip' style="border:0px;"></div>
				</div>
				<div style="border:0px;">
					<div id="tabstrip-2" class='tabstrip' style="border:0px;">
						<div class="ztree" id="rightTree" style='height:auto;border:0px;' ></div>
					</div>
				</div>
			</div>
			
			<%--<div id="south-pane" >
				<table style="width: 100%;">
					<tr>
						<td style="text-align:center;">
							<span style="" >华闽通达 版权所有</span>
						</td>
					</tr>
				</table>
			</div>
		--%></div>
	</div>
	<div id="selectField" style="display:none;width:300px;height:200px;" >
		<div id='selectFieldComb' style='margin : 10px;' ></div>
	</div>
</body>
</html>
<script type="text/javascript">
	/**
	 * 初始化布局
	 * @return {[type]} [description]
	 */
	function initLayout(){
		/**
		 * 布局 start
		 */
		var $tabstrip = $(".tabstrip"),
			$vertical = $("#vertical"),
			contextPath = pageGobalParam_.contextPath;

		function onResize(e) {
			$vertical.css({
				height: $(window).height()
			});
			$tabstrip.css({
				height: $tabstrip.closest('div[role=group]').height()
			});
		}

		$vertical.css({
			height: $(window).height()
		}).kendoSplitter({
			orientation: "vertical",
			panes: [{
				collapsible: false,
				resizable: false,
				scrollable: false,
				size: '32px'
			}, {
				collapsible: true,
				resizable: true,
				scrollable: false
			}, {
				collapsible: true,
				resizable: true,
				scrollable: false,
				size: '4%'
			}],
			layoutChange: onResize,
		});

		$("#center-pane").kendoSplitter({
			orientation: "horizontal",
			panes: [{
				collapsible: true,
				resizable: true,
				scrollable: true,
				size: '252px'
			}, {
				collapsible: true,
				resizable: true,
				scrollable: true
			}, {
				collapsible: true,
				resizable: true,
				scrollable: false,
				size: '340px'
			}]
		});
	}
	




	/**
	 * ztree funcs
	 */
	var ztreeFuncs = {
		ztreeBeforeClick: function(treeId, treeNode, clickFlag) {
			pageGobalParam_.selectedNode = treeNode;
			if (!treeNode.isParent) {
				var id = treeNode.id;
				var text = treeNode.name;

				$("#tabstrip-1").empty();
				$frame = jQuery('<iframe>', {
					frameborder: 0,
					scrolling: 'auto',
					id: id,
					style: 'width:100%;height:99%'
				}).attr({
					src: pageGobalParam_.url + '/getFormPageHtmlById?id=' + id,
					onload: "frameLoaded(this);",
					title: text
				});
				$("#tabstrip-1").append($frame);
				pageGobalParam_.$frame = $frame;
				pageGobalParam_.copyPageId = id;
			}
		},
	}

	function frameLoaded(o) {
		populateJs($(o));
	}
	function ajaxDataFilter(treeId, parentNode, responseData) {
		if (responseData) {
			for (var i = 0; i < responseData.length; i++) {
				var data = responseData[i];
				if (data.locked == 1) {
					data.icon = contextPath + "/images/lock.png";
				}
			}
		}
		return responseData;
	};

	//左边结构树的面板初始化
	function leftPanelInit(){
		//begin 模块下拉框初始化
		pageGobalParam_.pageCategory = $.cookie('pageCategory');
		$("#pageCategory").kendoDropDownList({
			dataSource: new kendo.data.DataSource({
				transport: {
					read: {
						url: pageGobalParam_.contextPath + '/rs/form/formPageCategory/all',
						data: {},
						type: 'post',
						dataType: 'json',
					}
				}
			}),
			dataTextField: "name",
			dataValueField: "id",
			animation: false,
			change: function(e) {
				var value = this.value();
				$.cookie('pageCategory', value, {
					expires: 30
				});
				pageGobalParam_.zTree.setting.async.otherParam = {
					locked: $("#showLockedBox").prop("checked"),
					pageCategory: value
				};
				pageGobalParam_.zTree.reAsyncChildNodes(null, 'refresh');
			}
		});

		if (pageGobalParam_.pageCategory) {
			$("#pageCategory").data("kendoDropDownList").value(pageGobalParam_.pageCategory);
		}
		//end 模块下拉框初始化

		/**
		 * 是否显示禁用项
		 */
		$('#showLockedBox').on('change', function(e) {
			var isChecked = e.target.checked;
			pageGobalParam_.zTree.setting.async.otherParam = {
				locked: isChecked,
				pageCategory: $("#pageCategory").data("kendoDropDownList").value()
			};
			pageGobalParam_.zTree.reAsyncChildNodes(null, 'refresh');
		});

		//begin 结构树初始化
		pageGobalParam_.zTree = $("#tree").ztree({
			async: {
				url: pageGobalParam_.url + '/getFormPageTree',
				autoParam: ["parentId=pId", "id"],
				dataFilter: ajaxDataFilter
			},
			callback: {
				beforeClick: ztreeFuncs.ztreeBeforeClick,
				onAsyncSuccess: function() {
					if (pageGobalParam_.selectedNode) {
						var nodes = pageGobalParam_.zTree.getNodesByParamFuzzy("id", pageGobalParam_.selectedNode.id, null);
						pageGobalParam_.zTree.expandNode(nodes[0], true, true, false);
					}
				},
			},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "parentId",
				}
			},
			edit: {
				enable: false,
			}
		}).ztree('getZtree');
		//end 结构树初始化
		//搜索功能
		/**
		 * 搜索
		 */
		$('#button-4').kendoButton({
			imageUrl: pageGobalParam_.contextPath + "/images/search.png",
			click: function() {
				var searchTextBox = $("#searchTextBox"),
					val = searchTextBox.val();
				var f = pageGobalParam_.zTree.expandAll(false);

				function updateHighlight(nodes, flag) {
					$.each(nodes, function(i, node) {
						node.highlight = flag || false;
						pageGobalParam_.zTree.updateNode(node);
					});
				}
				if (!f) {
					setTimeout(function() {
						var ns = searchTextBox.data('nodes');
						if (ns && ns[0]) {
							updateHighlight(ns, false);
						}
						if (val) {
							var nodes = pageGobalParam_.zTree.getNodesByParamFuzzy("name", val, null);
							if (nodes && nodes[0]) {
								$.each(nodes, function(i, node) {
									if (!node.isParent) {
										node = node.getParentNode();
									}
									pageGobalParam_.zTree.expandNode(node, true, true, false);
								});
								updateHighlight(nodes, true);
								searchTextBox.data('nodes', nodes);
							}
						}
					}, 500);
				}
			}
		});
	}

	/**
	 * 初始化右边面板
	 * @return {[type]} [description]
	 */
	function initRightPanel($dataRoleElements){
		var dataRoleRows = [];
		$.each($dataRoleElements,function(i,item){
			var cname = item.getAttribute("cname");
			var dataRole = item.getAttribute("data-role");
			if(cname && dataRole == pageGobalParam_.dataRole){
				var row = {
					"name":cname,
					"dataRole":item.getAttribute('data-role'),
					"id":item.id
				}
				dataRoleRows.push(row);
			}
		})
		//begin 结构树初始化
		if(dataRoleRows)
			pageGobalParam_.rightZtree = $.fn.zTree.init($("#rightTree"),{
				edit: {
					enable: false,
				},
				check: {
					enable: true,
					chkStyle: "radio"
				},
				callback: {
					onCheck: function(event, treeId, treeNode){
						var $frame = pageGobalParam_.$frame;
						$frame.contents().find("#"+treeNode.id).trigger("click");
						pageGobalParam_.copyNode = treeNode;	//赋值
					}
				}
			},dataRoleRows);
	}

	$(function(){
		initLayout();	//初始化布局
		leftPanelInit();	//初始化左边面板
		
		//确认按钮，进行复制。
		$("#sureButton").click(function(event){
			var params = {
				fromPageId : pageGobalParam_.copyPageId,
				fromComponentName : pageGobalParam_.copyNode.id,
				toComponentName : pageGobalParam_.componentName,
				toRuleId : pageGobalParam_.ruleId,
			}
			$.ajax({
				url: pageGobalParam_.url + '/copyPageComponentRule',
				type: 'post',
				data: params,
				dataType: 'json',
				async: true,
				success: function(msg) {
					if(msg && msg.statusCode == '500'){
						alert(msg.message);
					}else{
						alert('操作成功!');
					}
				},
				error: function(e,errorText,errorThrown) {
					alert("异常错误,请稍后再试.");
				}
			})
		})
	})

	function selectRightTree(id){
		var node = pageGobalParam_.rightZtree.getNodeByParam("id", id, null);
		if(node){
			pageGobalParam_.rightZtree.checkNode(node, true, true);
			pageGobalParam_.copyNode = node;	//赋值
		}
	}

	/**
	 * 注册定义页面事件
	 * 注册页面的点击选中事件
	 * @param jQueryFrame
	 */
	function populateJs(jQueryFrame) {
		var doc = jQueryFrame.contents(),
			divs_ = new Array();
		var crtltypes = doc.find('[crtltype]'),
			pageId = jQueryFrame.attr("id");
		if (crtltypes && crtltypes.length) {
			var $this, $body = doc.find("body");

			initRightPanel($body.find("[data-role]"));

			$body.bind('click', function(index, item) {
				pageGobalParam_.saveData = {
					name: pageId,
					pageId: pageId,
					dataRole: 'page'
				};
				popuCss(pageId);
				return false;
			});

			$.each(crtltypes, function(index, item) {
				$this = $(item).unbind('click').unbind('dblclick');
				$.each(['click','dblclick'], function(index, val) {
					$this.bind(val, function(e) {
						$this = jQuery(this);
						var id = $this.attr('id');
						var dataRole = $this.attr('data-role');

						if (dataRole) {
							dataRole = dataRole.toLowerCase();
						}
						pageGobalParam_.saveData = {
							name: id,
							pageId: pageId,
							dataRole: dataRole
						};
						selectRightTree(id)
						popuCss(id);

						return false;
					});
				});
			});

			function popuCss(id) {
				// 清楚其他div_颜色
				jQuery.each(divs_, function() {
					$(this).css({
						'border-color': ''
					});
				});

				jQuery.each(crtltypes, function() {
					if ($(this).attr('id') === id) {
						$this.css({
							border: '5px #FFB90F solid'
						});
					} else {
						$(this).css({
							border: ''
						});
					}
				});
			}
		}
	}
</script>
