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
<title>页面选择</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${contextPath}/webfile/js/jquery.ztree.extends.js" ></script>
<script type="text/javascript" src="${contextPath}/webfile/js/jquery.cookie.js" ></script>
<style type="text/css">
.ztree li span.button.tree_folder_ico_open{margin-right:2px; background: url(${contextPath}/images/folder-open.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_folder_ico_close{margin-right:2px; background: url(${contextPath}/images/folder.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_folder_ico_docu{margin-right:2px; background: url(${contextPath}/images/folder.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}

.ztree li span.button.tree_leaf_ico_open{margin-right:2px; background: url(${contextPath}/images/orm.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_leaf_ico_close{margin-right:2px; background: url(${contextPath}/images/orm.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_leaf_ico_docu{margin-right:2px; background: url(${contextPath}/images/orm.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
</style>
<script type="text/javascript">

var mtxx = {
	url : 	"${contextPath}/mx/form/defined/getFormPageTree",
	fn : '${fn}',
	targetId : '${targetId}',
	selectPageTree : null,
	pageCategory : $.cookie('pageCategory'),
	retFn : '${param.retFn}'
};


$(function() {
	
	$('.datagrid').css({
		height : $(document).height() - 120
	});
	
	$("#tabstrip").kendoTabStrip({
		animation:  {
			open: {
				effects: "fadeIn"
			}
		},
		select : function(e){
			mtxx.targetTree =  $(e.contentElement).find('.ztree');
		}
	}).data('kendoTabStrip').select(0);

	$("#page-tree").ztree({
		async : {
			url : mtxx.url
		}
	});
	
	var $rows = 8899988;
	var templateUrl = "${contextPath}/mx/dep/report/depReportTemplate/json?onlyName=true&rows=" + $rows;
	
	var reportUrl = "${contextPath}/mx/dep/report/depReportCategory/json?rows=" + $rows;

	$("#cell-page").ztree({
		async : {
			url : reportUrl,
			dataFilter : function(treeId, parentNode, responseData){
				var rows = responseData.rows || [];
				rows.unshift({
					id : 0,
					name : "模板分类",
					icon : '${contextPath}/images/home.png'
				});
				
				$.each(rows, function(i, row){
					row.id += $rows;
					row.pId += $rows;
					row.isCata = true;
				});
			
				$.ajax({
					url : "${contextPath}/mx/dep/report/depReportTemplate/templateJson?rows=" + $rows,
					type : 'post',
					async : false,
					dataType : 'json',
					success : function(ret){
						if(ret && ret.rows){
							$.each(ret.rows, function(i, v){
								rows.push({
									id : v.ID_,
									name : v.NAME_,
									pId : v.PID + $rows,
									icon : '${contextPath}/images/reports.png'
								});
							});
						}
					}
				});
				
				return rows;

				
				//return data;
			}
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
			}
		}
	});
	
	reportUrl = "${contextPath}/mx/report/reportTmpMapping/json"; 
	$("#report-page").ztree({
		async : {
			url : reportUrl,
			contentType: "application/json",
			type : 'POST',
			data : JSON.stringify({
				deleteFlag : 0
			}),
			dataType :'json',
			dataFilter : function(treeId, parentNode, responseData){
				var rows = responseData.rows || [];
				rows.unshift({
					id : 0,
					name : "报表分类",
					desc : "报表分类",
					icon : '${contextPath}/images/home.png'
				});
				$.each(rows, function(i, row){
				//	row.id += $rows;
					row.pId = 0;
				//	row.isCata = true;
					row.name = row.desc;
					row.pageType  = 'report';
				});
				return rows;
			}
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
			}
		}
	});
	
	/* $("#chart").ztree({
		async : {
			url : function(treeId, treeNode){
					if(treeNode != null){ 
					    var param = "nodeId="+treeNode.id; 
					    return "${contextPath}/rs/bi/chart/treeJson?"+param; 
					}
				return "${contextPath}/rs/bi/chart/treeJson?nodeCode=report_category"; 
			}
	    }
	}); */
	
	/**
	* 获取字典信息
	*/
	mtxx.getDictByCode = function(code,fn){
		var k ;
		$.ajax({
			url : "${contextPath}/mx/form/defined/getDictByCode",
			data : {code : code},
			type : 'post',
			dataType : 'json',
			async : false,
			success:function(data){ 
				if(fn)
					fn(data);
				k = data ;
			}
		});
		return k ;
	};
	
	var c = new AddChildren();
	var other = [
		{
			isFix : true , //用来表示固定连接
			name : '输入输出参数关系定义',
			id : '/mx/form/defined/param/outInParam'
		},{
			isFix : true,
			name : '输入表达式定义',
			id : '/mx/form/defined/param/inparam'
		},{
			isFix : true,
			name : '输入形参定义',
			id : '/mx/form/defined/param/paramDefined'
		}
	];
	$.each(other, function(index, val) {
		c.addOther(val);
	});
	
	
	//系统页面
	var sys = [
		{
			isFix : true , //用来表示固定连接
			code : 'jsgisParam',
			name : 'GIS',
			//params : [{name:'参数一',code:'param1'},{name:'参数二',code:'param2'}] ,
			id : '/mx/form/defined/jsgis'
		},
		{
			isFix : true , //用来表示固定连接
			code : 'multipleform',
			name : '工作流配置页面',
			//params : [{name:'参数一',code:'param1'},{name:'参数二',code:'param2'}] ,
			id : '/mx/form/defined/ex/multipleform'
		},
		{
			isFix : true ,
			//code : 'multipleform',
			name : '工作流启动页面',
			params : [{name :'配置信息ID', code:'defId'},{ name:"流程实例ID", code:"processId"},{ name:"ReadOnly", code:"readOnly"}],
			id : '/mx/form/defined/ex/multiFormStart'
		},
		{
			isFix : true , //用来表示固定连接
		//	code : 'multipleform',
			name : '追溯数据源',
			params : mtxx.getDictByCode('fix_wbstreelist') ,
			id : '/mx/isdp/table/data/wbstreelist'
		},
		{
			isFix : true , //用来表示固定连接
			name : '工作流程图',
			params : [{name : '配置信息ID', code : 'msgId'}],
			id : '/mx/form/defined/ex/showProcessView'
		},
		{
			isFix : true,
			name : '流程查看',
			params : [{name : '流程实例ID', code : 'processInstanceId'}],
			id : '/mx/activiti/task'
		},
		{
			name : '用户选择',
			code : 'user_variable',
			id : '/mx/form/workflow/assign/view?assignType=user'
		},{
			name : '角色选择',
			code : 'role_variable',
			id : '/mx/form/workflow/assign/view?assignType=role'
		},{
			name : '部门选择',
			code : 'depart_variable',
			id : '/mx/form/defined/param/department'
		},{
			name : 'SpreadSheet报表查看',
			isFix : true , //用来表示固定连接content
			code : 'spreadSheet_report',
			params : [{name : '报表模板id', code : 'templateId'},{name : '报表文件id', code : 'reportId'},{name : '页索引', code : 'pageIndex'},{name : '数据库', code : 'content'},{name : '密钥', code : 'key'},{name : '标段', code : 'database'}],
			id : '/mx/spread/report/view?1=1'
		},{
			name : 'cell表格查看',
			isFix : true , //用来表示固定连接
			code : 'view_cell',
			params : [{name : '文件ID', code : 'fileID'},{name:'结构树index_id',code:'indexId'},{name:'桥接关联data_id',code:'data_id'},{name:'数据源',code:'systemName'}],
			id : '/mx/isdp/cell/data/show'
		},{
			isFix : true , //用来表示固定连接
			name : '流程树结构',
			params : [{name :'配置信息ID', code:'defId'}, {name :'流程配置数据集ID', code:'dsId'}],
			id : '/mx/form/defined/ex/flowTree'
		},
	];
	
	////用户、角色
	$.each(sys,function(i, v){
		c.addSys(v);
	});
	
	var datas = c.datas();
	
	initRelatePageTree(datas);
	
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
			mtxx.zTree = $.fn.zTree.getZTreeObj("page-tree");
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

function AddChildren(){
	this.other = {
		name : '配置页面',
		id : '1',
		parentId : null,
		children : []
	};
	
	this.system = {
		name : '系统内置页面',
		id : '2',
		parentId : null,
		children : []
	};
	
	this.addOther = function(e){
		this.other.children.push(e);
	};
	this.addSys = function(e){
		this.system.children.push(e);
	};
	
	this.datas = function(){
		return [this.other, this.system];
	};
	
}

function getTreeNode(){
	
	var $targetTree = mtxx.targetTree;
	
	if($targetTree){
		var selectedNode, nodes = $targetTree.ztree('getZtree').getSelectedNodes();
		if(nodes && (selectedNode = nodes[0])){
			if(selectedNode.isCata){
				alert("请选择页面模版!");
				return false;
			}
			var id = $targetTree.attr('id');
			var retUrl, retVal ={
					pageType : id,
					node : selectedNode,
					id:selectedNode.id,
					name:selectedNode.name
				};
			switch(id){
			case  'page-tree':
				retUrl = "/mx/form/page/viewPage?id=";
				break;
			case  'relate-page':
				retUrl = "";
				break;
			case  'report-page':
				retUrl = "/mx/stml/report/view/js?mappingId=";
				break;
			default : 
				retUrl = "/mx/bi/chart/viewChart?chartId=";
			}
			retVal.url = retUrl + selectedNode.id;
			retCall(retVal);
			parent.layer.close(parent.layer.getFrameIndex());
		} else {
			confirmfunc();
		}
	} else {
		confirmfunc();
	}
	
	function confirmfunc(){
		if(confirm('您没有选择任何页面或图表,将清空此项?')){
			retCall('');
		}
	}
	
	function retCall(retVal){
		var p = window.opener || window.parent;
		if(mtxx.fn){
			var target = p.document.getElementById(mtxx.targetId);
			var func = p[mtxx.fn];
			if(func){
				func.call(target,retVal);
			}
		}else if(mtxx.retFn){
			var func = p[mtxx.retFn];
			if(func){
				func.call(null,retVal);
			}
		}
	}
}

function initRelatePageTree(datas){
	$("#relate-page").ztree({
		data: {
			simpleData: {
				enable:true,
				idKey: "id",
				pIdKey: "parentId",
				rootPId: null
			}
		}
	}, datas);
}
</script>
</head>
<body>
	<div id="tabstrip">
		<ul>
			<li>关联页面</li>
			<!-- <li>图表</li> -->
			<li>系统页面</li>
			<li>CELL页面</li>
			<li>报表</li>
		</ul>
		<div class="datagrid">
			<div>模块：<div id="pageCategory"></div></div>
			<div class="ztree" id="page-tree" data-options="title:'关联页面'"  ></div>
		</div>
		<div class="datagrid">
			<!-- <div class="ztree" id="chart" data-options="title:'图表'"></div> -->
			<div class="ztree" id="relate-page" data-options="title:'系统页面'"></div>
		</div>
		<div class="datagrid">
			<!-- <div class="ztree" id="chart" data-options="title:'图表'"></div> -->
			<div class="ztree" id="cell-page" data-options="title:'CELL页面'"></div>
		</div>
		<div class="datagrid">
			<div class="ztree" id="report-page" data-options="title:'报表'"></div>
		</div>
	</div>
	<br>
	<div style="" align="center" >
		<button class='k-button' onclick="getTreeNode();" > 确  定 </button>
	</div>
</body>
</html>