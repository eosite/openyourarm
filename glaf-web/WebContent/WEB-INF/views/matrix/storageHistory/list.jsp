<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>存储历史</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
   var contextPath="<%=request.getContextPath()%>";

   jQuery(function(){
		jQuery('#mydatagrid').datagrid({
				width:1000,
				height:480,
				fit:true,
				fitColumns: true,
				nowrap: false,
				striped: true,
				collapsible: true,
				url: '<%=request.getContextPath()%>/mx/sys/storageHistory/json?storageId=${storageId}',
				remoteSort: false,
				singleSelect: true,
				idField: 'id',
				columns:[[
				    {title:'序号', field:'startIndex', width:80, sortable:false},
					{title:'备份文件名',field:'path', width:180},
					{title:'创建人',field:'createBy', width:120},
					{title:'创建时间',field:'createTime_datetime', width:120},
					{field:'functionKey',title:'功能键',width:120, formatter:formatterKeys}
				]],
				rownumbers: false,
				pagination: true,
				pageSize: <%=com.glaf.core.util.Paging.DEFAULT_PAGE_SIZE%>,
				pageList: [10,15,20,25,30,40,50,100],
				pagePosition: 'both' 
			});
	});
 
	function formatterKeys(val, row){
		//<a href='javascript:download(\""+row.id+"\");'>下载</a>&nbsp;
		var str = "<a href='javascript:restore(\""+row.id+"\");'>还原</a>";
	    return str;
	}	 

	function restore(id){
	    if (confirm("存在数据可能被覆盖，确定恢复该数据包吗？")){
		   if (confirm("被覆盖数据无法恢复，确定执行操作吗？")){
		     jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/storageHistory/restore?id='+id,
				   dataType: 'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
				   }
			 });
		   }
	    }
	}
</script>
</head>
<body style="margin:1px;">  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',split:true,border:true" > 
    <div class="toolbar-backgroud"  style="height:30px"> 
	<img src="<%=request.getContextPath()%>/images/window.png">
	&nbsp;<span class="x_content_title">存储历史列表</span>
   </div> 
  </div> 
  <div data-options="region:'center',border:true">
	 <table id="mydatagrid"></table>
  </div>  
</div>
</body>
</html>
