<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setAttribute("theme", "default");
%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/treegrid/css/jquery.treegrid.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/webfile/styles/eventsDefined.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/layer/skin/layer.ext.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/webfile/js/jquery.cookie.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/treegrid/js/jquery.treegrid.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/webfile/js/filterObj.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/webfile/js/jquery.treeGrid.event.extend.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/webfile/js/eventDefined.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/layer/extend/layer.ext.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/webfile/js/similarity.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/webfile/js/outInParams.js"></script>
<script type="text/javascript">
	var mtxx = {
		contextPath : '${contextPath}',
		url : "${contextPath}/mx/form/defined",
		parent : window.opener || window.parent,
		datas : null
	}, pageId = "${param.pageId}" || "0c3e092947d14affaab846f01df89db9", eleType = "${param.eleType}", objelementId = "${param.objelementId}", nameElementId = "${param.nameElementId}";

	$(function() {
		$("#main").kendoSplitter({
			orientation : "vertical",
			panes : [ {
				scrollable : false,
				size : 32,
				resizable : false
			}, {
				scrollable : false
			} ]
		});
		var $warp = $("#warp").kendoSplitter({
			orientation : "horizontal",
			panes : [{
				scrollable : false,
				size : '200px',
				resizable : true,
				max:"400px",
				collapsed: true,
				collapsible: true ,
			}, {
				scrollable : true,
				min:"30%",
				resizable : true
			}, {
				scrollable : true,
				resizable : true,
				collapsible: true ,
			}, {
				scrollable : false,
				size : '70%',
				resizable : true,
				max:"70%",
				collapsed: true,
				collapsible: true ,
				//collapsedSize: "0%"
			} ],
			expand:function(e){
				if($(e.pane).hasClass("tabwarp") /*|| $(e.pane).hasClass("viewwarp")*/){
					$warp.collapse(".k-pane.paramwarp");
				}
				if($(e.pane).hasClass("paramwarp")){
					$warp.collapse(".k-pane.tabwarp");
				}
			},
			collapse:function(e){
				if($(e.pane).hasClass("viewwarp")){
				//	$warp.expand(".k-pane.paramwarp");
				}
			}
		}).data("kendoSplitter");
		$(".treewarp").kendoSplitter({
			orientation : "vertical",
			panes : [ {
				scrollable : true,
				max:"70%",
				resizable : true
			}, {
				scrollable : true,
				resizable : true,
				max:"70%",
				collapsible: true 
			} ]
		});

		mtxx.tabStrip = $("#frameTab").kendoTabStrip({
			//  collapsible : true,
			animation : {
				open : {
					duration : 0,
					effects : "expand:vertical"
				}
			},
			select : initTabHeight
		}).data("kendoTabStrip");

		$("#treegrid").treegrid();
		var datas = parent.$("#" + objelementId).val();
		treeGridExtend.init(datas || "");
		//选中第一行
		$("#treegrid").find("tbody tr:first").trigger("click");

		$("#help").kendoTooltip({
			content : function(e) {
				var msg = "<div style='color:red;'>说明：预览界面中 ，绿色边框为输入控件、红色边框为触发控件、蓝色为输出控件</div>";
				return msg;
			}
		});

		//初始化 窗口
		$('#eventsWin').kendoWindow({
			modal : true,
			pinned : true,
			width : $(document).width() - 200,
			height : $(document).height() - 200,
			title : "输出控件事件定义器",
			actions : [ "Close" ],
			visible : false,
			position : {
				top : 100,
				left : 100
			}
		});

		$("#treegrid").kendoTooltip({
			//position: "right",
			width:200,
			callout: true,
			filter : "input",
			content : function(e) {
				var target = e.target;
				return target.val();
			}
		});
		$("#treegrid2").kendoTooltip({
			width:200,
			filter : "input",
			content : function(e) {
				var target = e.target;
				return target.val();
			}
		});

		var showPage = $.cookie('showPage');
		if(showPage == "true"){
			$("#showPage").attr("checked",true)
			autoShowPage();
		}else{
			//$("#frameTab").html("请开启显示页面");
			mtxx.tabStrip.append({
				text: "警告",
				content: "请开启显示页面"
			});
			mtxx.tabStrip.select(0);
		}
		var showGroup = $.cookie('showGroup');
		if(showGroup == undefined){
			showGroup = true;
			$.cookie('showGroup',true);
			$("#showGroup").attr("checked",true);
		}else if(showGroup == "true"){
			$("#showGroup").attr("checked",true);
		}

		var ztreeSetting = {
			view : {
				showIcon : true,
				showLine : true,
				showTitle : false,
				selectedMulti : false
			},
			check : {
				enable : false,
				chkStyle : "checkbox",
				chkboxType : {
					"Y" : "s",
					"N" : "ps"
				}
			},
			async : {
				enable : true,
				url : "${contextPath}/mx/form/defined/getPageHierarchicalAssembly?pageId="+pageId+"&showEvent=false&isTrigger=false",
				dataFilter : treeDataFilter,
				otherParam : {
					isGroup : showGroup
				}
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pId"
				}
			},
			callback : {
				onClick : zTreeOnClick
			}
		};
		mtxx.zTreeObj = $.fn.zTree.init($("#viewTree"), ztreeSetting);


		$("[name=selCheck]").on("click",function(){
			selCheckClick();
		})

		$("#clrCheck").on("click",function(){
			clrCheckClick();
		})
	});
	function quickAdd(){
		$.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "快速添加",
			closeBtn: [0, true],
			shade: [0.3, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['', ''],
			fadeIn: 100,
			area: [($(document).width() - 20)+"px", ($(document).height() - 20)+'px'],
			iframe: {
				src: "${contextPath}/mx/form/defined/ex/quickAdd?pageId=" + pageId
			}
		});
	}
	/**
	 * 显示页面
	 * @param  {[type]} that [description]
	 * @return {[type]}      [description]
	 */
	function onShowPage(that){
		if($(that).is(':checked')){
			$.cookie('showPage',true);
			mtxx.tabStrip.remove(0);
			autoShowPage();
		}else{
			$.cookie('showPage',false);
			mtxx.tabStrip.remove("li");
			mtxx.tabStrip.append({
				text: "警告",
				content: "请开启显示页面"
			});
			mtxx.tabStrip.select(0);
		}
	}
	
	function autoShowPage(){
		$.ajax({
			url : "${contextPath}/mx/form/defined/getPageHierarchicalAssembly?pageId=" + pageId + "&showEvent=false",
			success : function(data) {
				if (data) {
					initTabsContent(data, true);
				}
			},
			error : function(e) {
				alert('异常错误，稍后再试');
			}
		});
	}

	/**
	 * 点击是否显示分组
	 * @param  {[type]} that [description]
	 * @return {[type]}      [description]
	 */
	function onShowGroup(that){
		var isGroup = false;
		if($(that).is(':checked')){
			isGroup = true;
		}
		$.cookie('showGroup',isGroup);
		refreshTree(isGroup);
	}

	/**
	 * 刷新左侧树
	 * @param  {Boolean} isGroup [description]
	 * @return {[type]}          [description]
	 */
	function refreshTree(isGroup){
		mtxx.zTreeObj.setting.async.otherParam = {isGroup:isGroup};
		mtxx.zTreeObj.reAsyncChildNodes(null, "refresh");
	}
	function toDiagram(){
		window.open("${contextPath}/mx/form/defined/workflow/workflow2?toDiagram=true&pageId="+pageId);
	}
</script>
</head>
<body>
	<input type="hidden" id="hidParam" />
	<div id="main">
		<div id="title" class="k-header" style="">
			<table style="width: 100%; height: 100%" cellspacing="0">
				<tr>
					<td valign="middle"><img src="/glaf/images/setting_tools.png" style=""> <span style="display: inline; font-size: 18px; font-weight: bold;">输入触发控件</span>
						<button class="k-button" onclick="quickAdd()" style="margin-bottom: 4px;">
							<span class="k-icon k-add"></span>快速添加
						</button>
						<button class="k-button" onclick="treeGridExtend.outerAddRow()" style="margin-bottom: 4px;">
							<span class="k-icon k-add"></span>添加
						</button>
					</td>
					<td align="right" valign="middle">
						<button class="k-button" onclick="toDiagram()">
							<span class="k-icon k-update"></span> 转换图形
						</button>
						<label><input type="checkbox" id="showGroup" onclick="onShowGroup(this)">分组显示</label>
						<button class="k-button" onclick="saveEvents()">
							<span class="k-icon k-update"></span> 保存
						</button>
					</td>
				</tr>
			</table>
		</div>
		<div id="warp">
			<!-- 导航视图 -->
			<div class="viewwarp">
				<div>
					<label><input type="checkbox" name="selCheck" value="1" checked="true">输入</label>
					<label><input type="checkbox" name="selCheck" value="2" checked="true">触发</label>
					<label><input type="checkbox" name="selCheck" value="3" checked="true">输出</label>
					<button id="clrCheck" class="k-button">清除</button>
				</div>
				<ul id="viewTree" class="ztree"></ul>
			</div>
			<div class="treewarp">
				<!-- 主事件 -->
				<div>
					<div class="mt-float-right mt-step">①</div>
					<table id="treegrid" class="tree">
						<thead>
							<tr class="thead">
								<td>名称</td>
								<td>输入控件</td>
								<td>事件触发控件</td>
								<td width="140px" class='hidCls'>输出控件定义</td>
								<td width="160px">操作</td>
							</tr>
						</thead>
					</table>
				</div>
				<!-- 输出事件 -->
				<div>
					<div class="mt-float-right mt-step">②</div>
					<div id="title2" class="k-header" style="	">
						<table style="width: 100%; height: 100%">
							<tr>
								<td align="left" valign="middle">
									<span style="display: inline; font-size: 18px; font-weight: bold;">输出事件</span>
									<button class="k-button" onclick="treeGridExtend2.outerAddRow()" style="margin-bottom: 2px;">
										<span class="k-icon k-add"></span> 添加
									</button>
								</td>
								<td align="right" valign="middle">
									
									<button class="k-button hidCls" onclick="saveOutInEditor()">
										<span class="k-icon k-update"></span> 保存
									</button>
								</td>
							</tr>
						</table>
					</div>
					<table id="treegrid2" class="tree">
						<thead>
							<tr class="thead">
								<td >输出控件</td>
								<td class='outExt'>输出控件扩展</td>
								<td>表达式定义</td>
								<td style="min-width: 40px;">参数</td>
								<td width="140px" style="display: none;">华表表达式</td>
								<td style="min-width: 40px;">回调</td>
								<td width="240px">操作</td>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<!-- 输入输出参数定义 -->
			<div class="paramwarp">
				<div class="mt-float-right mt-step">③</div>
				<!--<iframe id="paramFrame" src="" width="99%" height="99%" border="0"></iframe>-->
					<div id="head" class="k-header k-footer footerCss">
						<table style="width: 100%;">
							<tr>
								<td style="text-align: left; vertical-align: middle;"><img
									src="${pageContext.request.contextPath}/images/flow.png" style="vertical-align: middle;" /> <span
									style="font-size: 16px; font-weight: bolder;">输入&输出关系定义</span>
									<button class='k-button' id='button1' >
										<span class="k-icon k-add"></span>
										添加
									</button>
								</td>
								<td style="text-align: right;">
								<label><input type="checkbox" class="mt-cb all">全匹配</label>
								<label title="禁止自动匹配"><input type="checkbox" class="mt-cb dis">禁用</label>
									<button id="button23" type="button" class='k-button' >自动生成</button>
									<button id="button22" type="button" class='k-button' >一键删除</button>
									<span style="width: 20px;display: inline-block;">&nbsp;</span>
								</td>
							</tr>
						</table>
					</div>
					<div style="height: 85%; width: 100%; overflow: auto;">
						<table id="param-table" class=""  width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="BCD2EE" align="center"></table>
						<div style="height: 600px;">
							
						</div>
					</div>
					<div id="dialog" data-role="window" style="padding: 0px;display: none;">
						<div class="k-header k-footer footerCss">
							<table style="width: 100%;">
							<tr>
								<td style="text-align: left; vertical-align: middle;"><img
									src="${pageContext.request.contextPath}/images/flow.png" style="vertical-align: middle;" /> <span
									style="font-size: 16px; font-weight: bolder;">自动匹配选择</span>
								</td>
								<td style="text-align: right;">
									<button id="button666" type="button" class='k-button' >确定</button>
									<button id="button667" type="button" class='k-button' >关闭</button>
								</td>
							</tr>
						</table>
						</div>
						<div id="dictcc">
							
						</div>
					</div>
			</div>
			<div class="tabwarp">
				<div class="mt-float-right">
					<span id="help">帮助</span>
					<label><input type="checkbox" id="showPage" value="t" onclick="onShowPage(this)">显示页面</label>
				</div>
				<div id="frameTab"></div>
			</div>
		</div>
	</div>

	<div id="eventsWin" style="padding: 0;">
		
		
	</div>
</body>
</html>