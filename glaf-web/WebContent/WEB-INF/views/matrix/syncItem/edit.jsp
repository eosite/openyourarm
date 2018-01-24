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
<title>同步项</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";

	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/syncItem/save',
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
		document.getElementById("id").value="";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/syncItem/save',
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
	<span class="x_content_title">编辑同步项</span>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" 
	   onclick="javascript:saveData();" >保存</a> 
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="id" name="id" value="${syncItem.id}"/>
  <input type="hidden" id="syncId" name="syncId" value="${syncId}"/>
  <table class="easyui-form" style="width:788px;" align="center">
    <tbody>
	<tr>
		<td width="90" align="left">目标表</td>
		<td align="left">
            <input id="targetTableName" name="targetTableName" type="text" 
			       class="easyui-validatebox x-text" style="width:525px;"  
				   value="${syncItem.targetTableName}"/>
		</td>
	</tr>
	<tr>
		<td width="90" align="left">主键</td>
		<td align="left">
            <input id="primaryKey" name="primaryKey" type="text" 
			       class="easyui-validatebox x-text" style="width:525px;"  
				   value="${syncItem.primaryKey}"/>
			<br>（提示：可以是唯一列或物理表的主键列。）
		</td>
	</tr>
	<tr>
		<td width="90" align="left">表达式</td>
		<td align="left">
            <input id="expression" name="expression" type="text" 
			       class="easyui-validatebox x-text" style="width:525px;"  
				   value="${syncItem.expression}"/>
			<br>（提示：表达式不为空时结果为true才处理记录。）
		</td>
	</tr>
	<tr>
		<td width="90" align="left">是否需要建表</td>
		<td align="left">
			<select id="createTableFlag" name="createTableFlag">
				<option value="">----请选择----</option>
				<option value="N">否</option>
				<option value="Y">是</option>
		    </select>&nbsp;（提示：表不存在时自动创建）
		    <script type="text/javascript">
			    document.getElementById("createTableFlag").value="${syncItem.createTableFlag}";
		    </script>
		</td>
	</tr>
	<tr>
		<td width="90" align="left">SQL语句</td>
		<td align="left">
		    <textarea id="sql" name="sql" rows="6" cols="46" class="x-textarea" style="width:525px;height:320px;" >${syncItem.sql}</textarea>
		   <div style="margin-top:5px;">
		     （提示：可以使用union语句组合结果。）
			<br>
			<span>
			 （可以使用动态参数,例如: column1 = <%="#"%>{param1}。）
			</span>
			<br>
			<span>
			 （today_start内置变量代表当天的开始时间, today_end内置变量代表当天的结束时间。）
			</span>
			<br>
			<span>
			 （yesterday_start内置变量代表昨天的开始时间, yesterday_end内置变量代表昨天的结束时间。）
			</span>
			<br>
			<span>
			 （today_curr_hour_start内置变量代表当前小时的开始时间, today_curr_hour_end内置变量代表当前小时的结束时间。）
			</span>
			<br>
			<span>
			 （today_previous_hour_start内置变量代表前一小时的开始时间, today_previous_hour_end内置变量代表前一小时的结束时间。）
			</span>
	    </div>
		</td>
	</tr>
	<!-- <tr>
		<td width="90" align="left">清空SQL语句</td>
		<td align="left">
		    <textarea id="removeSql" name="removeSql" rows="6" cols="46" class="x-textarea" style="width:525px;height:120px;" >${syncItem.removeSql}</textarea>
		   <br>（提示：在目标库上执行同步前需要清空表数据时执行的删除SQL。）
		</td>
	</tr> -->
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
                 document.getElementById("sortNo").value="${syncItem.sortNo}";
             </script>
			 &nbsp;（提示：顺序小的先执行。）
		</td>
	</tr>
	<tr>
	  <td><br><br><br><br><td>
	</tr>
    </tbody>
  </table>
  </form>
</div>
</div>
</body>
</html>