<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript">
/**
 * 此页面导入有引入kendoUI样式的页面中，否则将会报错
 * 导入此页面后，从其他页面监听树节点事件
 * 数据说明：
 *	 id:节点id为10000~99999之间的随机数
 *	 zTreeType:{p:项目标段节点;y:年份节点;m:月份节点}
 *   treeData:做为节点具体数据使用
 * onClick: zTreeOnClick(event, treeId, treeNode,clickFlag);
 * onCheck: zTreeOnCheck(event, treeId, treeNode);
 * onRightClick: zTreeOnRightClick(event, treeId, treeNode) ;
 **/
var setting = {
		view: {
			showIcon:true,
			showLine:true,
			showTitle:false,
			selectedMulti: false
		},
		async:{
			enable:true
		},
		data:{
			simpleData:{
				enable:true
			}
		},
		callback: {
			onClick: zTreeOnClick,
			onAsyncSuccess:zTreeOnAsyncSuccess
		}
};

function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	var nodes = treeObj.transformToArray(treeObj.getNodes());
	for(var i=0;i<nodes.length;i++){
		if(nodes[i].zTreeType=='y')
			nodes[i].name += '年';
		else if(nodes[i].zTreeType=='m'){
			nodes[i].name += '月';
		}
	}
};
 

$(document).ready(function(){
	
	$.fn.zTree.init($("#zTree"), setting);
	
	var treeProjectDS = new kendo.data.DataSource({
		type: "json",
        transport: {
        	contentType: "application/json",
            type: "POST",
            read: "${pageContext.request.contextPath}/rs/isdp/global/projectQuery"
        }
	});
	$("#treeProject").kendoDropDownList({
        dataTextField: "text",
        dataValueField: "value",
        dataSource:treeProjectDS,
        change:function(e){
        	setting.async.url = "${pageContext.request.contextPath}/rs/isdp/global/proj_tree_yyyymm?projectId="+this.value();
        	$.fn.zTree.init($("#zTree"), setting);
        }
    });
	
	treeProjectDS.fetch(function(){
		var data = treeProjectDS.data();
		setting.async.url = "${pageContext.request.contextPath}/rs/isdp/global/proj_tree_yyyymm?projectId="+data[0].value;
		$.fn.zTree.init($("#zTree"), setting);
	});
});
</script>
<input type="text" id="treeProject" style="width:200px" />
<ul id="zTree" class="ztree"></ul>
