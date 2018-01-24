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
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(context);
	pageContext.setAttribute("contextPath", context);
	
	Object object = com.glaf.isdp.util.TableActionUtils.getDefaultExpValue();
	
	
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>增删改定义工具</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<%@ include file="/WEB-INF/views/inc/globaljs.jsp"%>
<link rel="stylesheet"
	href="${contextPath}/scripts/treegrid/css/jquery.treegrid.css">
<script type="text/javascript">
	var contextPath = "${contextPath}", wdataSet = {
		id : "${id}"
	}, bind = "${param.bind}", //
	pFn = "${param.fn}", //
	targetId = "${param.targetId}", //
	$parent = window.opener || window.parent, DefaultExpValue = <%= com.alibaba.fastjson.JSON.toJSONString(object)%>;

	var isBind = "${param.isBind}" || pFn;

	var tableMsg_test = {
		"linkageControl" : "[{\"id\":\"3-3\",\"text\":\"审批意见\"},{\"id\":\"3-4\",\"text\":\"监理单位\"},{\"id\":\"3-5\",\"text\":\"监理合同段\"},{\"id\":\"3-6\",\"text\":\"施工单位\"},{\"id\":\"3-7\",\"text\":\"施工合同段\"},{\"id\":\"3-8\",\"text\":\"短隧道累计长度\"},{\"id\":\"3-9\",\"text\":\"短隧道\"},{\"id\":\"3-10\",\"text\":\"中隧道累计长度\"},{\"id\":\"3-11\",\"text\":\"中隧道\"},{\"id\":\"3-12\",\"text\":\"项目名称\"},{\"id\":\"3-13\",\"text\":\"字段01\"}]",
	//	"wdataSet" : {
	//		"id" : 1921,
	//		"dataSetName" : "业务表分类测试表 系统自动生成"
	//	},
	//		"tableMsg" : JSON.stringify([{"name":"","dataSetId":"","table":{"addType":1,"id":"20150831/admin-0000003","isSubTable":"0","tableName":"cell_useradd8105","topId":""},"columns":[{"dType":"string","fieldId":"20150831/admin-0000099","fieldName":"cell_useradd8105_user46","id":"3-3","strlen":30,"text":"审批意见"},{"dType":"string","fieldId":"20150831/admin-0000098","fieldName":"cell_useradd8105_user45","id":"3-4","strlen":30,"text":"监理单位"},{"dType":"string","fieldId":"20150831/admin-0000097","fieldName":"cell_useradd8105_user44","id":"3-5","strlen":30,"text":"监理合同段"},{"dType":"string","fieldId":"20150831/admin-0000096","fieldName":"cell_useradd8105_user43","id":"3-6","strlen":30,"text":"施工单位"},{"dType":"string","fieldId":"20150831/admin-0000095","fieldName":"cell_useradd8105_user42","id":"3-7","strlen":30,"text":"施工合同段"},{"dType":"string","fieldId":"20150831/admin-0000094","fieldName":"cell_useradd8105_user41","id":"3-8","strlen":30,"text":"短隧道累计长度"},{"dType":"string","fieldId":"20150831/admin-0000093","fieldName":"cell_useradd8105_user40","id":"3-9","strlen":30,"text":"短隧道"},{"dType":"string","fieldId":"20150831/admin-0000092","fieldName":"cell_useradd8105_user39","id":"3-10","strlen":30,"text":"中隧道累计长度"},{"dType":"string","fieldId":"20150831/admin-0000091","fieldName":"cell_useradd8105_user38","id":"3-11","strlen":"30","text":"中隧道"},{"dType":"string","fieldId":"20150831/admin-0000090","fieldName":"cell_useradd8105_user37","id":"3-12","strlen":30,"text":"项目名称"},{"dType":"string","fieldId":"20150831/admin-0000071","fieldName":"cell_useradd8105_user18","id":"3-13","strlen":30,"text":"字段01"}]}])
	};

	tableMsg_test = {
		"linkageControl" : "[{\"id\":\"3-3\",\"text\":\"审批意见\"},{\"id\":\"3-4\",\"text\":\"监理单位\"},{\"id\":\"3-5\",\"text\":\"监理合同段\"},{\"id\":\"3-6\",\"text\":\"施工单位\"},{\"id\":\"3-7\",\"text\":\"施工合同段\"},{\"id\":\"3-8\",\"text\":\"短隧道累计长度\"},{\"id\":\"3-9\",\"text\":\"短隧道\"},{\"id\":\"3-10\",\"text\":\"中隧道累计长度\"},{\"id\":\"3-11\",\"text\":\"中隧道\"},{\"id\":\"3-12\",\"text\":\"项目名称\"},{\"id\":\"3-13\",\"text\":\"字段01\"}]",
		"wdataSet" : {
			"id" : 2001,
			"dataSetName" : "业务表分类测试表 系统自动生成"
		},
		"tableMsg" : "[{\"dataSetId\":\"4b6418f5cf7444d09c522aed7194ec9e\",\"columns\":[{\"strlen\":10,\"fieldName\":\"rev_\",\"dType\":\"integer\",\"id\":\"3-3\",\"text\":\"审批意见\"},{\"strlen\":64,\"fieldName\":\"execution_id_\",\"dType\":\"string\",\"id\":\"3-4\",\"text\":\"监理单位\"},{\"strlen\":64,\"fieldName\":\"proc_inst_id_\",\"dType\":\"string\",\"id\":\"3-5\",\"text\":\"监理合同段\"},{\"strlen\":64,\"fieldName\":\"proc_def_id_\",\"dType\":\"string\",\"id\":\"3-6\",\"text\":\"施工单位\"},{\"strlen\":255,\"fieldName\":\"name_\",\"dType\":\"string\",\"id\":\"3-7\",\"text\":\"施工合同段\"},{\"strlen\":64,\"fieldName\":\"parent_task_id_\",\"dType\":\"string\",\"id\":\"3-8\",\"text\":\"短隧道累计长度\"},{\"strlen\":4000,\"fieldName\":\"description_\",\"dType\":\"string\",\"id\":\"3-9\",\"text\":\"短隧道\"},{\"strlen\":255,\"fieldName\":\"task_def_key_\",\"dType\":\"string\",\"id\":\"3-10\",\"text\":\"中隧道累计长度\"},{\"strlen\":255,\"fieldName\":\"owner_\",\"dType\":\"string\",\"id\":\"3-11\",\"text\":\"中隧道\"},{\"strlen\":64,\"fieldName\":\"delegation_\",\"dType\":\"string\",\"id\":\"3-12\",\"text\":\"项目名称\"},{\"strlen\":255,\"fieldName\":\"category_\",\"dType\":\"string\",\"id\":\"3-13\",\"text\":\"字段01\"}],\"table\":{\"ctime_datetime\":\"2016-07-05 11:37:56\",\"parentTId\":\"tree_1\",\"_id_\":\"1467032203\",\"type\":99,\"tableName\":\"ACT_RU_TASK\",\"ctime_date\":\"2016-07-05\",\"editNameFlag\":false,\"ctime\":\"2016-07-05\",\"_oid_\":\"1467032203\",\"id\":\"1467032203\",\"zAsync\":true,\"isFirstNode\":false,\"isParent\":false,\"isHover\":true,\"level\":1,\"indexName\":\"ACT_RU_TASK\",\"isAjaxing\":false,\"parentId\":\"1|\",\"tId\":\"tree_27\",\"name\":\"ACT_RU_TASK\",\"tableId\":\"1467032203\",\"isLastNode\":false,\"tableNameCN\":\"ACT_RU_TASK\",\"user\":\"admin\",\"open\":false}}]"
	}

	tableMsg_test = null;

	window.tableMsg__ = null;
	window.$tableMsg__ = null;
	function _getTableMsg() {
		if (!window.tableMsg__) {
			//var msg = window.initTableMsg();
			var msg = window.$tableMsg__;
			if (msg && msg.tableMsg) {
				window.tableMsg__ = JSON.parse(msg.tableMsg)[0];
			}
		}
		return window.tableMsg__;
	}

	/**
	 * 获取配置信息/返回配置信息
	 */
	function initTableMsg(wdataSet) {
		var target = $parent.document.getElementById(targetId);
		var tableMsg = null;
		if (wdataSet && wdataSet.ruleJson) {//返回
			var ruleJson = JSON.parse(wdataSet.ruleJson);
			if (ruleJson.columns) {
				var linkageControl = [], msg = window._getTableMsg() || {};
				tableMsg = {
					name : msg.name,
					dataSetId : msg.dataSetId,
					table : ruleJson.node,
					columns : []
				};
				//	window.a2b([ "addType", "databaseId", "id", "isSubTable",//
				//	"systemName", "tableName", "topId" ], ruleJson.node,
				//			tableMsg.table);
				var keys = [];
				$.each(ruleJson.columns, function(i, c) {
					!!this.isKey && (keys.push(this.dataColumnName));
					if (c.id)
						linkageControl.push({
							id : c.id,
							text : c.text
						});
					if (!c.id || !c.dname) {
						return true;
					}
					//debugger;
					var o = {
						dType : c.dtype,
						fieldId : c.FieldId,
						fieldName : c.dname,
						id : c.id,
						strlen : c.strlen,
						text : c.text
					}
					tableMsg.columns.push(o);
				});
				tableMsg.table.primaryKeys = keys.length ? keys.join(',')
						: "id";
			}

			var retTableMsg = {
				linkageControl : JSON.stringify(linkageControl),
				wdataSet : {
					id : wdataSet.id,
					dataSetName : wdataSet.dataSetName
				},
				tableMsg : JSON.stringify([ tableMsg ])
			};

			if ("${param.isBind}") {

				returnValue(retTableMsg);

			} else {
				window.buildDataSet([ tableMsg ], function(ret) {
					retTableMsg.tableMsg = JSON.stringify(ret);
					returnValue(retTableMsg);
				});
			}

		} else { //获取

			if (window.$tableMsg__) {
				return window.$tableMsg__;
			} else if (pFn && $parent[pFn]) {
				return (window.$tableMsg__ = $parent[pFn].call(target));
			} else if (target && target.value) {
				return (window.$tableMsg__ = JSON.parse(target.value));
			} else {
				return tableMsg_test;
			}
		}
	}

	function returnValue(retTableMsg) {
		var target = $parent.document.getElementById(targetId);
		if (pFn && $parent[pFn]) {
			$parent[pFn].call(target, retTableMsg);
		} else if (target) {
			target.value = JSON.stringify([ retTableMsg ]);
		}
		window.close();
	}

	/**
	 * 生成更新集
	 */
	function buildDataSet(tableMsg, func) {
		$.ajax({
			url : '${contextPath}/mx/form/defined/createUpdateSource',
			type : "post",
			data : {
				pageId : "${param.pageId}",
				tableMsgArr : JSON.stringify(tableMsg)
			},
			dataType : "json",
			success : function(data) {
				func && (func(data));
			}
		});
	}
</script>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<link
	href="${contextPath}/scripts/plugins/bootstrap/base/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/scripts/plugins/bootstrap/grid/css/grid.css">
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
	font: 14px/1 Tahoma, Helvetica, Arial, "宋体", sans-serif;
	overflow: hidden;
}

.expspan span:visited {
	line-height: 18px;
	color: #000066;
	letter-spacing: 0.1em;
	text-decoration: none;
}

.expspan  span:hover {
	line-height: 18px;
	color: #FF0000;
	letter-spacing: 0.1em;
	text-decoration: underline;
}

.expspan  span:active {
	font-size: 14px;
	line-height: 18px;
	color: #000066;
	letter-spacing: 0.1em;
	text-decoration: none;
}

.tree td {
	border-top: 1px solid #ddd;
	padding: 5px;
}

td {
	border-top: 1px solid #ddd;
	padding: 5px;
}

ul li {
	list-style-type: none;
}

.active {
	background-color: #93C3CF
}
</style>
<body>
	<div id="vertical" style="width: 98%; height: 98%; margin: 10px auto;">
		<div id="head" class="k-header k-footer footerCss">
			<table style="width: 100%;">
				<tr>
					<td style="width: 679px; text-align: left; vertical-align: middle;"><img
						src="${contextPath}/images/database.png"
						style="vertical-align: middle;" /> <span
						style="font-family: Lucida Calligraphy; font-size: 20px; font-weight: bolder;">增删改</span>
						<span style="font-size: 16px; font-weight: bolder;">定义工具</span> <span
						style="margin-left: 90px;">当前表名 : </span><input class="k-textbox"
						id="create_table_name" />
						<span id="table_name"></span>
						</td>


					<td style="text-align: right;">
						<div style="padding: 5px;">
						    <button t="add" class="k-button" type="button">增加</button>
							<button t="sure" class="k-button" type="button">确定</button>
							<button t="test" class="k-button" type="button">测试</button>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div>
			<div id="horizontal" style="height: 100%; width: 100%;">
				<div style="height: 451px; padding-top: 10px;">
					<%@include file="/WEB-INF/views/form/defined/ex/commonTree.jsp"%>
				</div>
				<div>
					<div id="tabstrip" style="border: 0px;">
						<ul>
							<li>已选字段</li>
							<li>未选字段</li>
							<li>更新条件</li>
							<li>删除条件</li>
							<li>参数定义</li>
						</ul>

						<div class='tabstrips'
							style="width: 96%; overflow-y: auto; overflow-x: hidden;">
							<div id="grid-2" style="width: 100%; height: 650px;"></div>
						</div>

						<div class='tabstrips'
							style="width: 96%; overflow-y: auto; overflow-x: hidden;">
							<div id="grid-3" style="width: 100%; height: 650px;"></div>
						</div>
						<div class='tabstrips'
							style="width: 96%; overflow-y: auto; overflow-x: hidden;">
							<table id="updateClaus" class="table-tree tree"
								style="border-spacing: 0px;">
							</table>
						</div>
						<div class='tabstrips'
							style="width: 96%; overflow-y: auto; overflow-x: hidden;">
							<table id="deleteClaus" class="table-tree tree"
								style="border-spacing: 0px;">
							</table>
						</div>
						<div class='tabstrips'
							style="width: 96%; overflow-y: auto; overflow-x: hidden;">
							<%@include file="/WEB-INF/views/datamgr/dataset/params.jsp" %>
						</div>
					</div>
					<div id="message"
						style="width: 100%; height: 200px; overflow: auto;"></div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="${contextPath}/scripts/treegrid/js/jquery.treegrid.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/plugins/bootstrap/grid/js/jquery.grid.extends.js"></script>
<script type="text/javascript"
	src="${contextPath}/webfile/js/sqlcrud.js"></script>
<script type="text/javascript"
	src="${contextPath}/webfile/js/whereClaus.js"></script>
</html>