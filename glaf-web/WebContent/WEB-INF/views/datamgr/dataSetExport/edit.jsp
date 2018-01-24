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
<title>数据集导出定义</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";

	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/datamgr/dataSetExport/saveDataSetExport',
				   data: params,
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
					  
				   }
			 });
	}

	function saveAsData(){
		document.getElementById("id").value="";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/datamgr/dataSetExport/saveDataSetExport',
				   data: params,
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
					
				   }
			 });
	}

    function openDataSet(){
        var link = '<%=request.getContextPath()%>/mx/datamgr/dataSetExport/datasetList?elementId=datasetIds&elementName=datasetNames&id=${dataSetExport.id}&xtype=1';
        var x=100;
        var y=100;
        if(is_ie) {
        	x=document.body.scrollLeft+event.clientX-event.offsetX-100;
        	y=document.body.scrollTop+event.clientY-event.offsetY-100;
        }
       // openWindow(link,self,x, y, 995, 560);
	   jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "选择数据集",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['920px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
    }

</script>
</head>

<body>
<div style="margin:0;"></div>  

<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"> 
	<span class="x_content_title">编辑数据集导出定义</span>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" onclick="javascript:saveData();" >保存</a> 
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" onclick="javascript:saveAsData();" >另存</a> 
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="id" name="id" value="${dataSetExport.id}"/>
  <table class="easyui-form" style="width:880px;" align="center">
    <tbody>
	<tr>
		<td width="20%" align="left">名称</td>
		<td align="left">
            <input id="name" name="name" type="text" 
			       class="easyui-validatebox  x-text"  style="width:380px;"
				   value="${dataSetExport.name}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">标题</td>
		<td align="left">
            <input id="title" name="title" type="text" 
			       class="easyui-validatebox  x-text"  style="width:380px;"
				   value="${dataSetExport.title}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">导出库名</td>
		<td align="left">
            <input id="exportDBName" name="exportDBName" type="text" 
			       class="easyui-validatebox  x-text"  style="width:380px;"
				   value="${dataSetExport.exportDBName}"/>
		</td>
	</tr>
	
	<tr>
		<td width="20%" align="left">服务标识</td>
		<td align="left">
            <input id="serviceKey" name="serviceKey" type="text" 
			       class="easyui-validatebox  x-text"  style="width:380px;"
				   value="${dataSetExport.serviceKey}"/>
		</td>
	</tr>
	<tr>
        	 <td width="20%" height="98">数据集</td>
              <td colspan="3" height="98">
                <input type="hidden" id="datasetIds" name="datasetIds" value="${dataSetExport.datasetIds}">
                <textarea id="datasetNames" name="datasetNames" rows="12" cols="68" class="x-textarea"
                          style="width:550px;height:120px;" onclick="javascript:openDataSet();"  
                          readonly="true" >${datasetNames}</textarea>&nbsp;
                <a href="#" onclick="javascript:openDataSet();">
                <img src="<%=request.getContextPath()%>/images/search_results.gif" border="0"
                     title="如果图表需要多个数据集组成一个图表，请先建好数据集再选择。">
                </a>
        	 </td>
    </tr>
	

  <tr>
    <td width="20%" align="left" height="25">是否需要建表</td>
    <td align="left">
        <select id="createTableFlag" name="createTableFlag">
			<option value="">----请选择----</option>
			<option value="N">否</option>
			<option value="Y">是</option>
	  </select>&nbsp;（提示：表不存在时自动创建）
	  <script type="text/javascript">
	       document.getElementById("createTableFlag").value="${dataSetExport.createTableFlag}";
	  </script>
    </td>
  </tr>

  <tr>
    <td width="20%" align="left" height="25">同步前删除</td>
    <td align="left">
        <select id="deleteFetch" name="deleteFetch">
			<option value="">----请选择----</option>
			<option value="Y">是</option>
			<option value="N">否</option>
	  </select>&nbsp;（提示：同步前清空目标表数据，只有临时表tmp_或sync_开头的表才生效）
	  <script type="text/javascript">
	       document.getElementById("deleteFetch").value="${dataSetExport.deleteFetch}";
	  </script>
    </td>
  </tr>


  <tr>
    <td width="20%" align="left" height="25">是否后台调度</td>
    <td align="left">
	  <select id="scheduleFlag" name="scheduleFlag">
	    <option value="N">否</option>
		<option value="Y">是</option>
	  </select>
	  <script type="text/javascript">
	       document.getElementById("scheduleFlag").value="${dataSetExport.scheduleFlag}";
	  </script>
    </td>
  </tr>

  <tr>
    <td width="20%" align="left" height="25">是否有效</td>
    <td align="left">
	  <select id="locked" name="locked">
		<option value="0">是</option>
		<option value="1">否</option>
	  </select>
	  <script type="text/javascript">
	       document.getElementById("locked").value="${dataSetExport.locked}";
	  </script>
    </td>
  </tr>
 
    </tbody>
  </table>
  </form>
</div>
</div>
</body>
</html>