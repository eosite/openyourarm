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
<title>自动任务生成</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";

function openDx(){
        var selected = jQuery("#databaseIds").val();
        var link = '<%=request.getContextPath()%>/mx/sys/taskTable/chooseDatabases?elementId=databaseIds&elementName=selectedDB&selected='+selected+"&taskTableId=${taskTable.id}";
        var x=100;
        var y=100;
        if(is_ie) {
        	x=document.body.scrollLeft+event.clientX-event.offsetX-200;
        	y=document.body.scrollTop+event.clientY-event.offsetY-200;
        }
        openWindow(link,self,x, y, 695, 480);
    }

    function openQx(){
        var selected = jQuery("#syncColumns").val();
		var databaseIds = jQuery("#databaseIds").val();
		var tableName = jQuery("#tableName").val();
        var link = '<%=request.getContextPath()%>/mx/sys/taskTable/chooseColumns?elementId=syncColumns&elementName=syncColumns&selected='+
			selected+"&taskTableId=${taskTable.id}&databaseIds="+databaseIds+"&tableName="+tableName;
        var x=100;
        var y=100;
        if(is_ie) {
        	x=document.body.scrollLeft+event.clientX-event.offsetX-200;
        	y=document.body.scrollTop+event.clientY-event.offsetY-200;
        }
        openWindow(link,self,x, y, 695, 480);
    }


	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/taskTable/saveTaskTable',
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
					  if(data.statusCode == 200){
					       window.parent.location.reload();
					   }
				   }
			 });
	}

	function saveAsData(){
		document.getElementById("id").value="";
		document.getElementById("id").value="";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/taskTable/saveTaskTable',
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
					   if(data.statusCode == 200){
					       window.parent.location.reload();
					   }
				   }
			 });
	}

</script>
</head>

<body>
<div style="margin:0;"></div>  

<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"> 
	<span class="x_content_title">编辑自动任务生成</span>
	<!-- <input type="button" name="save" value=" 保存 " class="button btn btn-primary" onclick="javascript:saveData();">
	<input type="button" name="saveAs" value=" 另存 " class="button btn" onclick="javascript:saveAsData();">
	<input type="button" name="back" value=" 返回 " class="button btn" onclick="javascript:history.back();"> -->
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" onclick="javascript:saveData();" >保存</a> 

	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" onclick="javascript:saveAsData();" >另存</a> 

	<!--
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-back'" onclick="javascript:history.back();">返回</a>
	-->
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="id" name="id" value="${taskTable.id}"/>
  <table class="easyui-form" style="width:600px;" align="center">
    <tbody>
	<tr>
		<td width="20%" align="left">标题</td>
		<td align="left">
            <input id="title" name="title" type="text" 
			       class="easyui-validatebox  x-text"  style="width:350px;"
				   value="${taskTable.title}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">类型</td>
		<td align="left">
            <input id="type" name="type" type="text" 
			       class="easyui-validatebox  x-text"  style="width:350px;"
				   value="${taskTable.type}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">表名</td>
		<td align="left">
            <input id="tableName" name="tableName" type="text" 
			       class="easyui-validatebox  x-text"  style="width:350px;"
				   value="${taskTable.tableName}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">主键字段</td>
		<td align="left">
            <input id="idColumn" name="idColumn" type="text" 
			       class="easyui-validatebox  x-text"  style="width:350px;"
				   value="${taskTable.idColumn}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">名称字段</td>
		<td align="left">
            <input id="nameColumn" name="nameColumn" type="text" 
			       class="easyui-validatebox  x-text"  style="width:350px;"
				   value="${taskTable.nameColumn}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">名称表达式</td>
		<td align="left">
            <input id="nameExpression" name="nameExpression" type="text" 
			       class="easyui-validatebox  x-text"  style="width:350px;"
				   value="${taskTable.nameExpression}"/>
			<br><%="$"%>{dateString} 代表生成记录的开始日期
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">值字段</td>
		<td align="left">
            <input id="valueColumn" name="valueColumn" type="text" 
			       class="easyui-validatebox  x-text"  style="width:350px;"
				   value="${taskTable.valueColumn}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">值表达式</td>
		<td align="left">
            <input id="valueExpression" name="valueExpression" type="text" 
			       class="easyui-validatebox  x-text"  style="width:350px;"
				   value="${taskTable.valueExpression}"/>
			<br>（如果有值字段，可以通过Java EL表达式取值，如：<%="$"%>{totalMoney / loopCount}）
			<br> (loopCount为内置变量，代表开始时间与结束时间按频率循环的计数。)
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">类型字段</td>
		<td align="left">
            <input id="typeColumn" name="typeColumn" type="text" 
			       class="easyui-validatebox  x-text"  style="width:350px;"
				   value="${taskTable.typeColumn}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">开始日期字段</td>
		<td align="left">
            <input id="startDateColumn" name="startDateColumn" type="text" 
			       class="easyui-validatebox  x-text"  style="width:350px;"
				   value="${taskTable.startDateColumn}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">结束日期字段</td>
		<td align="left">
            <input id="endDateColumn" name="endDateColumn" type="text" 
			       class="easyui-validatebox  x-text"  style="width:350px;"
				   value="${taskTable.endDateColumn}"/>
		</td>
	</tr>

	<tr>
    <td width="20%" align="left">转换数据库</td>
    <td align="left">
		 <input type="hidden" id="databaseIds" name="databaseIds" value="${taskTable.databaseIds}">
         <textarea id="selectedDB" name="selectedDB" rows="8" cols="48" class="x-text"
				  style="width:350px;height:60px;" onclick="javascript:openDx();"  
				  readonly="true" >${selectedDB}</textarea>&nbsp;
	    <a href="#" onclick="javascript:openDx();">
			<img src="<%=request.getContextPath()%>/images/search_results.gif" border="0">
		</a>  
	    <span class="k-invalid-msg" data-for="selectedDB"></span>
    </td>
  </tr>

  <tr>
    <td width="20%" align="left">参与列</td>
    <td align="left">
        <textarea id="syncColumns" name="syncColumns" rows="8" cols="48" class="x-text"
				  style="width:350px;height:120px;" onclick="javascript:openQx();"  
				  readonly="true" >${taskTable.syncColumns}</textarea>&nbsp;
	    <a href="#" onclick="javascript:openQx();">
			<img src="<%=request.getContextPath()%>/images/search_results.gif" border="0">
		</a> 
	    <span class="k-invalid-msg" data-for="syncColumns"></span>
    </td>
  </tr>

	<tr>
		<td width="20%" align="left">开始时间</td>
		<td align="left">
			<input id="startTime" name="startTime" type="text" 
			       class="easyui-datebox x-text" style="width:120px;"
				  value="<fmt:formatDate value="${taskTable.startTime}" pattern="yyyy-MM-dd"/>"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">结束时间</td>
		<td align="left">
			<input id="endTime" name="endTime" type="text" 
			       class="easyui-datebox x-text" style="width:120px;"
				  value="<fmt:formatDate value="${taskTable.endTime}" pattern="yyyy-MM-dd"/>"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">频率</td>
		<td align="left">
			<input id="frequency" name="frequency" type="text" 
			       class="easyui-numberbox x-text"  style="width:60px;"
				   increment="1"  
				   value="${taskTable.frequency}"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">执行天数</td>
		<td align="left">
			<input id="executeDay" name="executeDay" type="text" 
			       class="easyui-numberbox x-text"  style="width:60px;"
				   increment="1"  
				   value="${taskTable.executeDay}"/>
			<br>（执行天数取值应当小于频率，结束时间=开始时间+执行天数）
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">是否有效</td>
		<td align="left">
		  <select id="locked" name="locked">
			<option value="0">是</option>
			<option value="1">否</option>
		  </select>
		  <script type="text/javascript">
			   document.getElementById("locked").value="${taskTable.locked}";
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