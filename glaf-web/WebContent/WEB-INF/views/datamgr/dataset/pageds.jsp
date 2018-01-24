<%@page import="com.glaf.core.config.CustomProperties"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    String code =  CustomProperties.getString("dataSet.code");
    code = RequestUtils.getString(request, "code", code == null ? "report_category" : code);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据集列表</title>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript"
	src="${contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/jquery.base64.js"></script>
<script type="text/javascript"
	src="${contextPath}/webfile/js/jquery.ztree.extends.js"></script>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      	
   </div>
</script>
<script type="text/javascript">

	var retFn = "${param.retFn}";
	function callback(dataItem){
		//debugger;
		var retObj = {
			name:dataItem.name,
			value:dataItem.id
		};
		parent[retFn](retObj);
		closeWin();
	}
	function closeWin(){
		parent.layer.close(parent.layer.getFrameIndex());
	}
	function clearAll(){
		callback({});
		closeWin();
	}


    var setting = {
			async: {
				enable: true,
				//url:"${contextPath}/rs/tree/treeJson?nodeCode=<%=code%>",
				url: '${contextPath}/mx/form/defined/getFormPageTree'
			},
			callback: {
				onClick: zTreeOnClick,
				onAsyncSuccess: function(event, treeId, treeNode, msg){
					$("#" + treeId).ztree("highlight", "id", "${param.pageId}");
				}
			}
		};
  

    function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		jQuery("#pageId").val(treeNode.id);
		//var link="${contextPath}/rs/dataset/data?nodeId="+treeNode.id;
	    jQuery("#grid-01").data("kendoGrid").dataSource.read();
		//window.searchData();
 	}

    jQuery(document).ready(function(){
		$("#myTree").ztree(setting);
	});

	var command = [{
                "text": "修改",
                "name": "edit",
                "click": function showDetails(e) {
			            var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
			            var link = "${contextPath}/mx/dataset/edit?id="+dataItem.id;
			            editRow(link);
		                }
                },
				{
                "text": "删除",
                "name": "delete",
                "click": function deleteItem(e) {
			                var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
                            if(confirm("数据删除后不能恢复，确定删除吗？")){
                                var link = "${contextPath}/mx/dataset/delete?id="+dataItem.id;
								var params = jQuery("#iForm").formSerialize();
								jQuery.ajax({
									   type: "POST",
									   url: link,
									   dataType:  'json',
									   error: function(data){
										   alert('服务器处理错误！');
									   },
									   success: function(data){
										   if(data != null && data.message != null){
											   alert(data.message);
										   } else {
											   alert('操作成功完成！');
										   }
										   window.location.reload();
									   }
								});
							}
		                }
                },
				{
                "text": "SQL构建器",
                "name": "designer",
                "click": function showSqlbuilder(e) {
			                var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
			                var link = "${contextPath}/mx/dataset/sqlbuilder?id="+dataItem.id;
			                if(dataItem.dynamicDataSet && dataItem.dynamicDataSet == 'Y'){
			                	link = '${contextPath}/mx/form/dataset/dynamicDatabase?id='+dataItem.id;
			                }
			                window.open(link);
		                }
                }],
        seacrhCommand = [{
                "text": "选择",
                "name": "select",
                "click": function showSqlbuilder(e) {
			                var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							callback(dataItem);
                }
        }];

    jQuery(function() {
     
  	});


    function addRow(){
        editRow('${contextPath}/mx/dataset/edit?nodeId=' + jQuery("#nodeId").val());
    }

    function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑数据集信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['820px', (jQuery(window).height() - 40) +'px'], iframe: {src: link}
		});
	}

 </script>
</head>
<body>
	<input type="hidden" id="nodeId" name="nodeId" value="${nodeId}">
	<input type="hidden" id="pageId" name="pageId" value="${param.pageId}">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',split:true" style="width: 240px;">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'center',border:false">
					<ul id="myTree" class="ztree"></ul>
				</div>
			</div>
		</div>
		<div data-options="region:'center'">
			<div id="tt" class="easyui-tabs" style="" data-options="fit:true">
				<div title="视图1" style="padding: 10px;">
					<%@ include file="/WEB-INF/views/datamgr/dataset/treelist.jsp"%>
				</div>
			</div>
		</div>
	</div>
</body>

</html>