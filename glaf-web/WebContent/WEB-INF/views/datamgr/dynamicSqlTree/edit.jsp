<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.datamgr.domain.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
        String theme = com.glaf.core.util.RequestUtils.getTheme(request);
        request.setAttribute("theme", theme);
	    List list = (List)request.getAttribute("dynamicSqlTrees");
	    DynamicSqlTree dynamicSqlTree = (DynamicSqlTree)request.getAttribute("dynamicSqlTree");
 
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>动态SQL条件配置</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css"/>
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";
 
	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/datamgr/dynamicSqlTree/saveDynamicSqlTree',
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
					       window.close();
					   }
				   }
			 });
	}

	function saveAsData(){
		document.getElementById("id").value="";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/datamgr/dynamicSqlTree/saveDynamicSqlTree',
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
					       window.close();
					   }  
				   }
			 });
	}

	function dispLayer(flag){
		if(flag == "Y"){
			jQuery("#layer_separator").show();
			jQuery("#layer_operator").hide();
			jQuery("#layer_sql").hide();
		} else {
            jQuery("#layer_separator").hide();
			jQuery("#layer_operator").show();
			jQuery("#layer_sql").show();
		}
	}

</script>
</head>

<body>
<div style="margin:0;"></div>  

<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"> 
		<span class="x_content_title">编辑记录</span>
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" 
		   onclick="javascript:saveData();" >保存</a>
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="id" name="id" value="${dynamicSqlTree.id}"/>
  <input type="hidden" id="moduleId" name="moduleId" value="${moduleId}" > 
  <input type="hidden" id="businessKey" name="businessKey" value="${businessKey}">

  <table class="easyui-form" style="width:600px;" align="left">
  <tbody>
    <tr>
        <td width="20%" class="input-box">上级节点</td>
        <td>
		  <select id="parentId" name="parentId">
		   <option value="0">/根节点</option>
          <%
			if(list!=null){
			  Iterator iter = list.iterator();   
			  while(iter.hasNext()){
				DynamicSqlTree bean2=(DynamicSqlTree)iter.next();
				if(dynamicSqlTree != null){
					if(dynamicSqlTree.getId() == bean2.getId() || dynamicSqlTree.getId() == bean2.getParentId()){
						continue;
					}
				}
			%>
          <option value="<%=bean2.getId()%>">
          <%
			for(int i=1;i<bean2.getLevel();i++){
			  out.print("&nbsp;&nbsp;");
			}
			out.print(bean2.getName());
			%>
          </option>
          <%    
           }
         }
        %>
        </select>
		<script language="javascript">
		    document.all.parentId.value="${parentId}";	
	    </script>
		</td>
    </tr>
	<tr>
		<td width="20%" align="left">标题</td>
		<td align="left">
            <input id="name" name="name" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${dynamicSqlTree.name}" size="50"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">表名称</td>
		<td align="left">
            <input id="tableName" name="tableName" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${tableName}" size="50"/>
		   <br>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">表别名</td>
		<td align="left">
            <input id="tableAlias" name="tableAlias" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${dynamicSqlTree.tableAlias}" size="50"/>
		   <br>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">列名称</td>
		<td align="left">
            <input id="columnName" name="columnName" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${dynamicSqlTree.columnName}" size="50"/>
		   <br>
		</td>
	</tr>
    <tr>
		<td width="20%" align="left">列类型</td>
		<td align="left">
            <select id="columnType" name="columnType">
				<option value="">----请选择----</option>
				<option value="Date">日期型</option>
				<option value="Integer">整数型</option>
				<option value="Long">长整数型</option>
				<option value="Double">数值型</option>
				<option value="String">文本型</option>
            </select>
			<script language="javascript">
		      document.all.columnType.value="${dynamicSqlTree.columnType}";	
	        </script>
		</td>
	</tr>

    <tr>
		<td width="20%" align="left">参数标题</td>
		<td align="left">
            <input id="paramTitle" name="paramTitle" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${dynamicSqlTree.paramTitle}" size="50"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">参数名称</td>
		<td align="left">
            <input id="paramName" name="paramName" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${dynamicSqlTree.paramName}" size="50"/>
		   <br>
		</td>
	</tr>

	<tr>
		<td width="20%" align="left">连接条件</td>
		<td align="left">
            <select id="condition" name="condition">
				<option value="">----请选择----</option>
				<option value="AND">AND</option>
				<option value="OR">OR</option>
            </select>
			<script language="javascript">
		      document.all.condition.value="${dynamicSqlTree.condition}";	
	        </script>
		   <br>
		</td>
	</tr>



	<tr>
		<td width="20%" align="left">是否必须</td>
		<td align="left">
		  <input type="radio" name="requiredFlag" value="Y" 
		         <c:if test="${dynamicSqlTree.requiredFlag == 'Y'}">checked</c:if>>是&nbsp;&nbsp;
	      <input type="radio" name="requiredFlag" value="N" 
		         <c:if test="${dynamicSqlTree.requiredFlag == 'N'}">checked</c:if>>否
		    &nbsp;（提示：如果必须但没有值会抛出参数错误异常）
		</td>
	</tr>

	<tr>
		<td width="20%" align="left">是否集合参数</td>
		<td align="left">
		  <input type="radio" name="collectionFlag" value="Y" onclick="javascript:dispLayer('Y');";
		         <c:if test="${dynamicSqlTree.collectionFlag == 'Y'}">checked</c:if>>是&nbsp;&nbsp;
	      <input type="radio" name="collectionFlag" value="N" onclick="javascript:dispLayer('N');"
		         <c:if test="${dynamicSqlTree.collectionFlag == 'N'}">checked</c:if>>否
		    &nbsp;（提示：是否多个参数值，默认用半角的逗号,隔开）
		</td>
	</tr>

	<tr id="layer_operator">
		<td width="20%" align="left">运算符</td>
		<td align="left">
            <select id="operator" name="operator">
				<option value="">无需运算符</option>
				<option value="=">等于=</option>
				<option value="!=">不等于!=</option>
				<option value=">">大于></option>
				<option value=">=">大于等于>=</option>
				<option value="<">小于&lt;</option>
				<option value="<=">小于等于&lt;=</option>
				<option value="LIKE">LIKE</option>
				<option value="NOT LIKE">NOT LIKE</option>
            </select>
			<script language="javascript">
		      document.all.operator.value="${dynamicSqlTree.operator}";	
	        </script>
		   <br>
		</td>
	</tr>

	<tr id="layer_separator" style="display:none;">
		<td width="20%" align="left">参数分隔符</td>
		<td align="left">
            <input id="separator" name="separator" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${dynamicSqlTree.separator}" size="50"/>
		   <br>
		</td>
	</tr>

  <tr id="layer_sql" style="display:none;">
    <td width="20%" align="left">SQL条件</td>
    <td align="left">
        <textarea id="sql" name="sql" rows="12" cols="60" class="x-textarea"
				  style="width:340px;height:120px;" >${dynamicSqlTree.sql}</textarea>&nbsp;
	    <span class="k-invalid-msg" data-for="sql"></span>
		<br>&nbsp;（提示：如果默认的列组装条件不能满足可以自行写SQL，添加and条件即可）
		<br>&nbsp;（如果是in或exists子查询，运算符这里就不要选）
    </td>
  </tr>

	<tr>
		<td width="20%" align="left">是否启用</td>
		<td align="left">
		  <input type="radio" name="locked" value="0" <c:if test="${dynamicSqlTree.locked == 0}">checked</c:if>>启用&nbsp;&nbsp;
	      <input type="radio" name="locked" value="1" <c:if test="${dynamicSqlTree.locked == 1}">checked</c:if>>禁用
		</td>
	</tr>

	<tr>
	 <td colspan="2"><br><br><br><br></td>
	</tr>

    </tbody>
  </table>
 </form>
</div>
</div>
<script type="text/javascript">
 <c:if test="${!empty dynamicSqlTree.sql}">
    jQuery("#layer_sql").show();
 </c:if>
</script>
</body>
</html>