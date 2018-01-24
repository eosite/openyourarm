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
<title>树表修改器</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";

	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/treeTableModifier/saveTreeTableModifier',
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
				   url: '<%=request.getContextPath()%>/mx/sys/treeTableModifier/saveTreeTableModifier',
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

	function openDx(){
        var selected = jQuery("#databaseIds").val();
        var link = '<%=request.getContextPath()%>/mx/sys/treeTableModifier/chooseDatabases?elementId=databaseIds&elementName=selectedDB&selected='+selected+"&treeTableAggregateId=${treeTableModifier.id}";
        var x=100;
        var y=100;
        if(is_ie) {
        	x=document.body.scrollLeft+event.clientX-event.offsetX-200;
        	y=document.body.scrollTop+event.clientY-event.offsetY-200;
        }
        openWindow(link,self,x, y, 695, 480);
    }

</script>
</head>

<body>
<div style="margin:0;"></div>  

<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"> 
	<span class="x_content_title">编辑树表修改器</span>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" onclick="javascript:saveData();" >保存</a> 
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="id" name="id" value="${treeTableModifier.id}"/>
  <table class="easyui-form" style="width:880px;" align="center">
    <tbody>
	<tr>
		<td width="12%" align="left">标题</td>
		<td align="left">
            <input id="title" name="title" type="text" 
			       class="easyui-validatebox  x-text"  style="width:380px;"
				   value="${treeTableModifier.title}"/>
		</td>
	</tr>
	<tr>
		<td width="12%" align="left">数据库表名</td>
		<td align="left">
            <input id="tableName" name="tableName" type="text" 
			       class="easyui-validatebox  x-text"  style="width:380px;"
				   value="${treeTableModifier.tableName}"/>
		</td>
	</tr>
	<tr>
		<td width="12%" align="left">转换数据库</td>
		<td align="left">
             <input type="hidden" id="databaseIds" name="databaseIds" value="${treeTableModifier.databaseIds}">
			 <textarea id="selectedDB" name="selectedDB" rows="12" cols="68" class="easyui-validatebox x-textarea"
					  style="width:380px;height:80px;" onclick="javascript:openDx();"  
					  readonly="true" >${selectedDB}</textarea>&nbsp;
			<a href="#" onclick="javascript:openDx();">
				<img src="<%=request.getContextPath()%>/images/search_results.gif" border="0">
			</a>  
		</td>
	</tr>
	<tr>
		<td width="12%" align="left">主键列</td>
		<td align="left">
            <input id="primaryKey" name="primaryKey" type="text" 
			       class="easyui-validatebox  x-text"  style="width:380px;" 			
				   value="${treeTableModifier.primaryKey}"/>
		</td>
	</tr>
	<tr>
		<td width="12%" align="left">Id列</td>
		<td align="left">
            <input id="idColumn" name="idColumn" type="text" 
			       class="easyui-validatebox  x-text"  style="width:380px;"  
				   value="${treeTableModifier.idColumn}"/>（提示：该字段值必须是数值类型）
		</td>
	</tr>
	<tr>
		<td width="12%" align="left">父ParentId列</td>
		<td align="left">
            <input id="parentIdColumn" name="parentIdColumn" type="text" 
			       class="easyui-validatebox  x-text"  style="width:380px;"  
				   value="${treeTableModifier.parentIdColumn}"/>（提示：该字段值必须是数值类型）
		</td>
	</tr>
	<tr>
		<td width="12%" align="left">TreeId列</td>
		<td align="left">
            <input id="treeIdColumn" name="treeIdColumn" type="text" 
			       class="easyui-validatebox  x-text" style="width:380px;" 			
				   value="${treeTableModifier.treeIdColumn}"/>（提示：该字段不存在自行创建）
		</td>
	</tr>
	<tr>
		<td width="12%" align="left">层级列</td>
		<td align="left">
            <input id="levelColumn" name="levelColumn" type="text" 
			       class="easyui-validatebox  x-text"  style="width:380px;" 
				   value="${treeTableModifier.levelColumn}"/>
		</td>
	</tr>
	<tr>
		<td width="12%" align="left">是否有效</td>
		<td align="left">
			<select id="locked" name="locked">
				<option value="0">是</option>
				<option value="1">否</option>
			</select>
			<script type="text/javascript">
				 document.getElementById("locked").value="${treeTableModifier.locked}";
			</script>
		</td>
	</tr>
	<tr>
		<td width="12%" align="left"></td>
		<td align="left">
            <br><br><br><br>
		</td>
	</tr>
    </tbody>
  </table>
  </form>
</div>
</div>
</body>
</html>