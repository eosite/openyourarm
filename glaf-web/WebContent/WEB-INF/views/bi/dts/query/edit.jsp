<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.jdbc.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.query.*"%>
<%@ page import="com.glaf.core.service.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.dts.domain.*"%>
<%@ page import="com.glaf.dts.transform.*"%>
<%@ page import="com.glaf.dts.query.*"%>
<%@ page import="com.glaf.dts.service.*"%>
<%@ page import="com.glaf.dts.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_config.jsp"%>
<%
   	String actionType = request.getParameter("actionType");     
	QueryDefinition query = (QueryDefinition)request.getAttribute("query");
    List<QueryDefinition> queries = (List<QueryDefinition>)request.getAttribute("queries");
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	     function saveForm(actionType){
             if(document.getElementById("title").value==""){
				   alert("标题是必须的！");
				   document.getElementById("title").focus();
                   return;
			 }
			 if(document.getElementById("sql").value==""){
				   alert("查询语句是必须的！");
				   document.getElementById("sql").focus();
                   return;
			 }
			 document.getElementById("actionType").value=actionType;
			 var params = jQuery("#iForm").formSerialize();
			  jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/dts/query/saveQuery',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data.message != null){
						   alert(data.message);
					   } else {
						 alert('操作成功完成！');
					   }
						//location.href="queryMain.jsp";
				   }
			 });
	    }

		 function saveAs(actionType){
             if(document.getElementById("title").value==""){
				   alert("标题是必须的！");
				   document.getElementById("title").focus();
                   return;
			 }
			  if(document.getElementById("sql").value==""){
				   alert("查询语句是必须的！");
				   document.getElementById("sql").focus();
                   return;
			 }

			 document.getElementById("queryId").value="";
			 document.getElementById("actionType").value=actionType;
			 var params = jQuery("#iForm").formSerialize();
			  jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/dts/query/saveQuery',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data.message != null){
						   alert(data.message);
					   } else {
						 alert('操作成功完成！');
					   }
					   //location.href="<%=request.getContextPath()%>/bi/etl.do?method=queryMain";
				   }
			 });
	    }


		function transformToTable(){
			var databaseId = jQuery("#databaseId").val();
			var tableName = jQuery("#targetTableName").val();
			if(tableName.length > 0){
			  if(confirm("确定取数到目标表吗？")){
				 var queryId = jQuery("#queryId").val();
				 jQuery.ajax({
					   type: "POST",
					   url: '<%=request.getContextPath()%>/rs/dts/table/transformQueryToTable?tableName='+tableName+'&queryId='+queryId+'&databaseId='+databaseId,
					   dataType:  'json',
					   error: function(data){
						   alert('服务器处理错误！');
					   },
					   success: function(data){
							if(data.message != null){
							   alert(data.message);
							} else {
							   alert('操作成功完成！');
							}
					   }
				 });
		       }
			} else {
				 alert('请指定目标表！');
                 document.getElementById("targetTableName").focus();
			}
		}


		function queryForm(){
			document.getElementById("actionType").value="query";
			document.getElementById("iForm").submit();
	    }

		function openEditor(){
			window.open('<%=request.getContextPath()%>/mx/dts/query/sqleditor?queryId=${query.id}', '', 'height=510, width=820, top='+Math.round((window.screen.height-500)/2)+', left='+Math.round((window.screen.width-300)/2)+', toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no')
		}

		function switchDB(){

		}

		function openDataSet(){
             window.open('<%=request.getContextPath()%>/mx/dataset');
		}
</script>
</head>
<body style="padding-left:30px;padding-right:30px">
<br/>
<div class="x_content_title" style="width: 98%;">
    <img src="<%=request.getContextPath()%>/images/window.png" alt="编辑查询信息">&nbsp;编辑查询信息
</div>
<form id="iForm"  name="iForm" method="post" action="" class="x-form">
<input type="hidden" id="actionType" name="actionType" />
<input type="hidden" id="nodeId" name="nodeId" value="${nodeId}"/>
<input type="hidden" id="queryId" name="queryId" value="${query.id}"/>
<div class="content-block" style="width: 98%;"><br>
 
<table align="center"  class="easyui-form"  cellspacing="1" cellpadding="1" width="98%">
	<tr>
	    <td width="12%" height="28" align="left">标题&nbsp;<span class="required">*</span>&nbsp;</td>
		<td width="88%" height="28" align="left" colspan="3">
		    <input type="text" id="title" name="title" size="115" class="x-text input-xlarge easyui-validatebox" value="${query.title}"/> 
		</td>
	</tr>
	<tr>
	    <td width="12%" height="28" align="left">名称</td>
		<td width="38%" height="28" align="left">
		    <input type="text" id="name" name="name" size="30" class="x-text input-xlarge" value="${query.name}"/>
		</td>
		<td width="12%" height="28" align="left">结果映射变量</td>
		<td width="38%" height="28" align="left">
		    <input type="text" id="mapping" name="mapping" size="30" class="x-text input-xlarge" value="${query.mapping}"/>
		</td>
	</tr>
	<tr>
		<td width="12%" height="28" align="left">目标表</td>
		<td width="38%" height="28" align="left">
		  <input type="text" id="targetTableName" name="targetTableName" size="30" class="x-text input-xlarge" value="${query.targetTableName}"/>
		</td>
		<td width="12%" align="left" height="28">描述</td>
		<td width="38%" align="left" height="28">
		  <input type="text" id="description" name="description" size="30" class="x-text input-xlarge" value="${query.description}"/>
		</td>
	</tr>
	<tr>
		<td width="12%" align="left" height="28">父查询</td>
		<td width="38%" align="left" height="28">
		  
		   <%
		     StringBuffer sb = new StringBuffer();
	         for(QueryDefinition qd: queries){
                    if(query != null && !(qd.getId().equals(query.getId()))){
						sb.append("<option value=\"").append(qd.getId()).append("\"");
						if(qd.getId().equals(query.getParentId())){
							sb.append(" selected ");
						}
						sb.append(">");
						sb.append(qd.getTitle()).append("</option>");
					}
	         }
	       %>

		    <select id="parentId"  name="parentId" style="height:20px">
			    <option value="">----请选择----</option>
				<%=sb.toString()%>
		   </select>

		</td>
		<td width="12%" align="left" height="28">数据源</td>
		<td width="38%" align="left" height="28">
		   <select id="databaseId" name="databaseId" >
			<option value="0" selected>系统默认</option>
			<c:forEach items="${databases}" var="db">
			<option value="${db.id}">${db.title}</option>
			</c:forEach> 
		   </select>
           <script type="text/javascript">
			    document.getElementById("databaseId").value="${query.databaseId}";
			</script>
		</td>
	</tr>
    <tr>
	    <td width="12%" align="left" height="28"></td>
		<td width="38%" align="left" height="28">
		   <select id="sortNo" name="sortNo" class="span2" style="height:20px">
			    <option value="0">0</option>
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
				<option value="8">8</option>
				<option value="9">9</option>
		    </select>&nbsp;&nbsp;（提示：同一分组数值小的先执行）
			<script type="text/javascript">
			    document.getElementById("sortNo").value="${query.sortNo}";
			</script>
		</td>
		<td width="12%" align="left" height="28">是否有效</td>
		<td width="38%" align="left" height="28">
          <select id="locked" name="locked" >
			<option value="0">有效</option>
			<option value="1">无效</option>
		   </select>
           <script type="text/javascript">
			    document.getElementById("locked").value="${query.locked}";
			</script>
		</td>
	</tr>
	<tr>
	    <td width="12%" align="left" height="28">行列变换</td>
		<td width="38%" align="left" height="28">
		    <select id="rotatingFlag" name="rotatingFlag" >
			  <option value="">无需变换</option>
			  <option value="R2C">行转列</option>
		    </select>&nbsp;（提示：可以将查询结果唯一的行记录转成列）
            <script type="text/javascript">
			    document.getElementById("rotatingFlag").value="${query.rotatingFlag}";
		    </script>
		</td>
        <td width="12%" align="left" height="28">目标列</td>
		<td width="38%" align="left" height="28">
            <select id="rotatingColumn" name="rotatingColumn" >
			    <option value="">----请选择----</option>
			    <c:forEach items="${columns}" var="col">
				  <c:if test="${!empty col.columnName }">
			      <option value="${col.columnName}">${col.columnName}[${col.title}]</option>
				  </c:if>
			    </c:forEach>
		    </select>
            <script type="text/javascript">
			    document.getElementById("rotatingColumn").value="${query.rotatingColumn}";
		    </script>
		</td>
	</tr>
	<tr>
		<td width="12%" align="left">SQL语句&nbsp;<span class="required">*</span>&nbsp;</td>
		<td colspan="3"> 
		    <textarea id="sql" name="sql" rows="15" cols="86" style="width:650px;height:300px;"
			          class="span7 x-textarea input-xlarge easyui-validatebox">${query.sql}</textarea>
			 <!-- &nbsp;<input type="button"  name="myDataSet" value="数据集" class="btn" 
		                  onclick="javascript:openDataSet();"/> -->
			<br>
				 提示：如果查询条件使用变量，请用 ( 某表字段 = <%out.println("#{变量}");%> ) ，如果变量不存在则替换为 ( 1=1 )
                 <br><%out.println("#{curr_yyyymmdd}");%>代表当天的日期，如<%=com.glaf.core.config.SystemConfig.getCurrentYYYYMMDD()%>
                 <br><%out.println("#{curr_yyyymm}");%>代表当天的月份，如<%=com.glaf.core.config.SystemConfig.getCurrentYYYYMM()%>
				 <br><%out.println("#{input_yyyymmdd}");%>代表报表输入的日期，如<%=com.glaf.core.config.SystemConfig.getInputYYYYMMDD()%>
                 <br><%out.println("#{input_yyyymm}");%>代表报表输入的月份，如<%=com.glaf.core.config.SystemConfig.getInputYYYYMM()%>
		</td>
	</tr>

	<tr>
	   <td width="12%" align="left">&nbsp;</td>
	   <td colspan="3" align="left"><br/>
	    <input type="button"  name="save" value="保存" class="btn btn-primary" 
		       onclick="javascript:saveForm('');"/>&nbsp;
		<input type="button"  name="save" value="另存" class="btn " 
		       onclick="javascript:saveAs('');"/>&nbsp;
		<input type="button"  name="query" value="查询" class="btn"
			   onclick="javascript:queryForm('');"/>&nbsp;
		<input type="button"  name="transform" value="取数到目标表" class="btn"
			   onclick="javascript:transformToTable();"/>&nbsp;
 	    <!-- <input type="button"  name="qtyEditor" value="设计器" class="btn" 
		       onclick="javascript:openEditor();"/> -->
	   </td>
	</tr>
</table>
	
</form>

<br />
 <%
  if("query".equals(actionType)){
	String dbName = (String)request.getAttribute("dbName");
	String systemName = com.glaf.core.config.Environment.getCurrentSystemName();
    com.glaf.core.config.Environment.setCurrentSystemName(dbName);
    TableDefinition tableDefinition = null;
	if(query.getSql() != null && query.getSql().trim().length() >0 && DBUtils.isAllowedSql(query.getSql())){
		MxTransformManager manager = new MxTransformManager();
		try{
			 tableDefinition = manager.toTableDefinition(query);
		} catch(Exception ex){
			ex.printStackTrace();
			out.println("<div style='color:red;font-size:24px;'>");
			out.println("<br />查询语句有错误：<br />");
			out.println(ex.getMessage());
			out.println("</div>");
			return;
		 }
	}
   if(tableDefinition != null && tableDefinition.getColumns() != null){
%>
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" 
       class="table table-striped table-bordered table-condensed">
	<tr >
	   <% for(ColumnDefinition column:tableDefinition.getColumns()){%>
		<td >
		     <%=column.getColumnName()%>
		</td>
		<%}%>
	</tr>

	 <%if(tableDefinition != null && tableDefinition.getColumns() != null  && DBUtils.isAllowedSql(query.getSql())){
		try {
			ITablePageService tablePageService = ContextFactory.getBean("tablePageService");
 
            Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put(SystemConfig.CURR_YYYYMMDD, SystemConfig.getCurrentYYYYMMDD());
			parameters.put(SystemConfig.CURR_YYYYMM, SystemConfig.getCurrentYYYYMM());
			parameters.put(SystemConfig.INPUT_YYYYMMDD, SystemConfig.getInputYYYYMMDD());
			parameters.put(SystemConfig.INPUT_YYYYMM, SystemConfig.getInputYYYYMM());
			String querySQL = query.getSql();
            querySQL = QueryUtils.replaceSQLVars(querySQL);
			querySQL = QueryUtils.replaceSQLParas(querySQL, parameters);
		    List rows = tablePageService.getListData(querySQL, parameters);
			if(rows != null && !rows.isEmpty()){
 				Iterator iterator = rows.iterator();
				while(iterator.hasNext()){
					 Map rowMap = (Map)iterator.next();
					 out.println("<tr class='x-content'>");
					 for(ColumnDefinition column: tableDefinition.getColumns()){
						String value = "";
						String javaType = column.getJavaType();
						Object object =  ParamUtils.getObject(rowMap, column.getColumnLabel());
						//rowMap.get(column.getColumnName());
                        if(object != null){
							  if(object instanceof Date){
								  Date date = (Date)object;
								  value = DateUtils.getDate(date);
							  } else{
								  value = object.toString();
							  }
						}
%>
        <td >
		      <%=value%>
		</td>
<%   
				  }
				  out.println("</tr>");
			   }
			 }
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
}
%>
</table>
<%}
   com.glaf.core.config.Environment.setCurrentSystemName(systemName);
}
%>

 </div>
 </form>
 <br>
 <br>
</body>
</html>