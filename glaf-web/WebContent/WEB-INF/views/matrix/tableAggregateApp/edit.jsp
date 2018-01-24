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
<title>组合汇总表</title>
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
				   url: '<%=request.getContextPath()%>/mx/sys/tableAggregateApp/save',
				   data: params,
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
					   if(data.statusCode == 200) { 
					       parent.location.reload(); 
					   }
				   }
			 });
	}

	function saveAsData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/tableAggregateApp/saveAs',
				   data: params,
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
					   if(data.statusCode == 200) { 
					       parent.location.reload(); 
					   }
				   }
			 });
	}

    function openQx(){
        var selected = jQuery("#sourceCalculateColumns").val();
		var srcDatabaseId = jQuery("#srcDatabaseId").val();
		var sourceTableName = jQuery("#sourceTableName").val();
        var link = '<%=request.getContextPath()%>/mx/sys/tableAggregateApp/chooseColumns?elementId=sourceCalculateColumns&elementName=sourceCalculateColumns&selected='+
			selected+"&srcDatabaseId="+srcDatabaseId+"&sourceTableName="+sourceTableName+"&id=${tableAggregateApp.id}";
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
	<span class="x_content_title">编辑组合汇总表</span>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" 
	   onclick="javascript:saveData();" >保存</a> 
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" 
	   onclick="javascript:saveAsData();" >另存</a> 
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="id" name="id" value="${tableAggregateApp.id}"/>
  <input type="hidden" id="syncId" name="syncId" value="${tableAggregateApp.id}"/>
  <table class="easyui-form" style="width:880px;" align="center">
    <tbody>
	<tr>
		<td width="15%" align="left">标题</td>
		<td align="left">
            <input id="title" name="title" type="text" 
			       class="easyui-validatebox x-text" style="width:425px;" 
				   value="${tableAggregateApp.title}"/>
		</td>
	</tr>

   <tr>
    <td width="15%" align="left">来源表</td>
    <td align="left">
        <input id="sourceTableName" name="sourceTableName" type="text" 
               class="easyui-validatebox x-text" style="width:425px;" 
	           value="${tableAggregateApp.sourceTableName}" validationMessage="请输入来源表"/>
    </td>
   </tr>

   <tr>
    <td width="15%" align="left">来源表主键列</td>
    <td align="left">
        <input id="sourceIdColumn" name="sourceIdColumn" type="text" 
               class="easyui-validatebox x-text" style="width:425px;" 
	           value="${tableAggregateApp.sourceIdColumn}" validationMessage="请输入来源表主键列"/>
    </td>
   </tr>

   <tr>
    <td width="15%" align="left">来源表IndexId列</td>
    <td align="left">
        <input id="sourceIndexIdColumn" name="sourceIndexIdColumn" type="text" 
               class="easyui-validatebox x-text" style="width:425px;" 
	           value="${tableAggregateApp.sourceIndexIdColumn}" validationMessage="请输入来源表IndexId列"/>
		&nbsp;（提示：数据类型仅限整数型）
    </td>
   </tr>

   <tr>
    <td width="15%" align="left">来源表ParentId列</td>
    <td align="left">
        <input id="sourceParentIdColumn" name="sourceParentIdColumn" type="text" 
               class="easyui-validatebox x-text" style="width:425px;" 
	           value="${tableAggregateApp.sourceParentIdColumn}" validationMessage="请输入来源表ParentId列"/>
		&nbsp;（提示：数据类型仅限整数型）
    </td>
   </tr>

   <tr>
    <td width="15%" align="left">来源表TreeId列</td>
    <td align="left">
        <input id="sourceTreeIdColumn" name="sourceTreeIdColumn" type="text" 
               class="easyui-validatebox x-text" style="width:425px;" 
	           value="${tableAggregateApp.sourceTreeIdColumn}" validationMessage="请输入来源表TreeId列"/>
		&nbsp;（提示：数据类型仅限字符串型）
    </td>
   </tr>

   <tr>
    <td width="15%" align="left">来源表名称列</td>
    <td align="left">
        <input id="sourceTextColumn" name="sourceTextColumn" type="text" 
               class="easyui-validatebox x-text" style="width:425px;" 
	           value="${tableAggregateApp.sourceTextColumn}" validationMessage="请输入来源表名称列"/>
		&nbsp;（提示：数据类型仅限字符串型）
    </td>
   </tr>

   <tr>
    <td width="15%" align="left">来源表日期切分列</td>
    <td align="left">
        <input id="sourceTableDateSplitColumn" name="sourceTableDateSplitColumn" type="text" 
               class="easyui-validatebox x-text" style="width:425px;" 
	           value="${tableAggregateApp.sourceTableDateSplitColumn}" validationMessage="请输入来源表日期切分列"/>
		&nbsp;（提示：数据类型必须是日期型）
    </td>
   </tr>

   <tr>
    <td width="15%" align="left">切分日期格式</td>
    <td align="left">
        <input id="splitDateFormat" name="splitDateFormat" type="text" 
               class="easyui-validatebox x-text" style="width:425px;" 
	           value="${tableAggregateApp.splitDateFormat}" validationMessage="请输入切分日期格式"/>
		&nbsp;（提示：默认日期切分格式为 yyyyMMdd）
    </td>
   </tr>

  <tr>
    <td width="15%" align="left">统计列</td>
    <td align="left">
        <textarea id="sourceCalculateColumns" name="sourceCalculateColumns" rows="12" cols="68" class="x-textarea"
				  style="width:425px;height:90px;" readonly="true">${tableAggregateApp.sourceCalculateColumns}</textarea>
		&nbsp;
	    <a href="#" onclick="javascript:openQx();">
			<img src="<%=request.getContextPath()%>/images/search_results.gif" border="0">
		</a> 
	    <span class="k-invalid-msg" data-for="sourceCalculateColumns"></span>
		<div style="margin-top:5px;">
			<span style="  margin-left:10px;">
		    &nbsp;（提示：参与统计的列,列必须是数值类型.）
		    </span>
	    </div>
    </td>
  </tr>

   <tr>
    <td width="15%" align="left">维度表</td>
    <td align="left">
        <input id="dimensionTableName" name="dimensionTableName" type="text" 
               class="easyui-validatebox x-text" style="width:425px;" 
	           value="${tableAggregateApp.dimensionTableName}" validationMessage="请输入维度表"/>
    </td>
   </tr>

   <tr>
    <td width="15%" align="left">维度列</td>
    <td align="left">
        <input id="dimensionColumn" name="dimensionColumn" type="text" 
               class="easyui-validatebox x-text" style="width:425px;" 
	           value="${tableAggregateApp.dimensionColumn}" validationMessage="请输入维度列"/>
    </td>
   </tr>

	<tr>
		<td width="15%" align="left">目标表</td>
		<td align="left">
            <input id="targetTableName" name="targetTableName" type="text" 
			       class="easyui-validatebox x-text" style="width:425px;"  
				   value="${tableAggregateApp.targetTableName}"/>
		    <div style="margin-top:5px;">
			  <span style="color:red; margin-left:2px;">
			    （提示：为了保证系统安全，目标表只能以useradd_、etl_、sync_、tree_table_、tmp_开头。）
			  </span>
	       </div>
		</td>
	</tr>
	<tr>
		<td width="15%" align="left">来源数据库编号</td>
		<td align="left">
			<select id="srcDatabaseId" name="srcDatabaseId">
			    <option value="">----请选择----</option>
				<c:forEach items="${databases}" var="database">
				<option value="${database.id}">${database.title}[${database.dbname}]</option>
				</c:forEach>
             </select>
             <script type="text/javascript">
                 document.getElementById("srcDatabaseId").value="${tableAggregateApp.srcDatabaseId}";
             </script>
		</td>
	</tr>
	<tr>
		<td width="15%" align="left">目标数据库</td>
		<td align="left">
		    <select class="easyui-combobox" name="targetDatabaseIds" 
			        multiple="true" multiline="true"  
			        labelPosition="top" style="width:425px;height:30px;">
				<option value=""></option>
                <c:forEach items="${databases}" var="database">
				<option value="${database.id}" ${database.selected}>${database.title}[${database.dbname}]</option>
				</c:forEach>
            </select>
		</td>
	</tr>
	<!-- <tr>
		<td width="15%" align="left">同步标识</td>
		<td align="left">
		    <select id="syncFlag" name="syncFlag">
			    <option value="">----请选择----</option>
				<option value="INSERT_UPDATE">增量同步</option>
				<option value="INSERT_ONLY">数据表为空才能增加</option>
			    <option value="DELETE_INSERT">数据表不空时先删除再增加</option>
             </select>
             <script type="text/javascript">
                 document.getElementById("syncFlag").value="${tableAggregateApp.syncFlag}";
             </script>
		</td>
	</tr> -->
	<tr>
		<td width="15%" align="left">类型</td>
		<td align="left">
             <select id="type" name="type">
			    <option value="">----请选择----</option>
			    <option value="SYS">系统表</option>
				<option value="FORM">表单自定义</option>
				<option value="BPM">工作流</option>
				<option value="USER">业务数据</option>
             </select>
             <script type="text/javascript">
                 document.getElementById("type").value="${tableAggregateApp.type}";
             </script>
		</td>
	</tr>
	<tr>
		<td width="15%" align="left">是否自动同步</td>
		<td align="left">
		    <select id="autoSyncFlag" name="autoSyncFlag">
			    <option value="">----请选择----</option>
				<option value="Y">是</option>
			    <option value="N">否</option>
             </select>
             <script type="text/javascript">
                 document.getElementById("autoSyncFlag").value="${tableAggregateApp.autoSyncFlag}";
             </script>
		</td>
	</tr>
	<tr>
		<td width="15%" align="left">时间间隔</td>
		<td align="left">
		    <select id="interval" name="interval">
			    <option value="">----请选择----</option>
				<option value="1">1分钟</option>
				<option value="2">2分钟</option>
				<option value="5">5分钟</option>
				<option value="10">10分钟</option>
				<option value="15">15分钟</option>
				<option value="20">20分钟</option>
				<option value="30">30分钟</option>
			    <option value="60">60分钟</option>
				<option value="120">2小时</option>
				<option value="240">4小时</option>
				<option value="480">8小时</option>
				<option value="720">12小时</option>
				<option value="1440">24小时</option>
             </select>
             <script type="text/javascript">
                 document.getElementById("interval").value="${tableAggregateApp.interval}";
             </script>
		</td>
	</tr>
	<tr>
		<td width="15%" align="left">执行顺序</td>
		<td align="left">
		    <select id="sortNo" name="sortNo">
			    <option value="0">----请选择----</option>
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
			    <option value="8">8</option>
				<option value="9">9</option>
				<option value="10">10</option>
             </select>
             <script type="text/javascript">
                 document.getElementById("sortNo").value="${tableAggregateApp.sortNo}";
             </script>
			 &nbsp;（提示：顺序小的先执行。）
		</td>
	</tr>
	<tr>
		<td width="15%" align="left">是否有效</td>
		<td align="left">
		    <select id="active" name="active">
				<option value="Y">是</option>
			    <option value="N">否</option>
             </select>
             <script type="text/javascript">
                 document.getElementById("active").value="${tableAggregateApp.active}";
             </script>
		</td>
	</tr>
	<tr><td><br><br><br><br></td></tr>
    </tbody>
  </table>
 </form>
</div>
</div>
</body>
</html>