<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.query.*"%>
<%@ page import="com.glaf.core.service.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.dts.domain.*"%>
<%@ page import="com.glaf.dts.query.*"%>
<%@ page import="com.glaf.dts.service.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
        String encoding = request.getParameter("encoding");
		if (encoding == null) {
			encoding = "UTF-8";
		}
      
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		response.setContentType("text/html;charset=" + encoding);
         
	    TableDefinition table = (TableDefinition)request.getAttribute("table");
		if(table != null){
             pageContext.setAttribute("table", table);
			 pageContext.setAttribute("nodeId", table.getNodeId());
		}

		String theme = com.glaf.core.util.RequestUtils.getTheme(request);
        request.setAttribute("theme", theme);
%>
<!DOCTYPE html >
<html>
<head>
<title>合成表管理</title>
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/layer/skin/layer.css"  />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/layer/layer.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>		
<script type="text/javascript">

		function openQx(){
            var selected = jQuery("#queryIds").val();
            var link = '<%=request.getContextPath()%>/mx/dts/query/queryTree?elementId=queryIds&elementName=queryNames&nodeCode=report_category&selected='+selected;
			var x=100;
			var y=100;
			if(is_ie) {
				x=document.body.scrollLeft+event.clientX-event.offsetX-200;
				y=document.body.scrollTop+event.clientY-event.offsetY-200;
			}
			openWindow(link,self,x, y, 495, 480);
		}

		function openAggrQx(){
            var selected = jQuery("#aggregationQueryIds").val();
            var link = '<%=request.getContextPath()%>/mx/dts/query/queryTree?elementId=aggregationQueryIds&elementName=queryNames2&nodeCode=report_category&selected='+selected;
			var x=100;
			var y=100;
			if(is_ie) {
				x=document.body.scrollLeft+event.clientX-event.offsetX-200;
				y=document.body.scrollTop+event.clientY-event.offsetY-200;
			}
			openWindow(link,self,x, y, 495, 480);
		}

	     function openQx2(){
			var elementValue = $('#queryIds').val();
			var link = '<%=request.getContextPath()%>/mx/dts/query/chooseQuery?elementId=queryIds&elementName=queryNames&elementValue='+elementValue;
			var x=100;
			var y=100;
			if(is_ie) {
				x=document.body.scrollLeft+event.clientX-event.offsetX-200;
				y=document.body.scrollTop+event.clientY-event.offsetY-200;
			}
			openWindow(link,self,x, y, 695, 480);
	    }

		function transformTable(){
			 if(confirm("确定重新获取数据吗？")){
				 var tableName = document.getElementById("tableName").value
				 var params = jQuery("#iForm").formSerialize();
				  jQuery.ajax({
					   type: "POST",
					   url: '<%=request.getContextPath()%>/rs/dts/table/transformTable?tableName='+tableName,
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
					   }
				 });
		  }
	  }

	function saveForm(type){
		document.getElementById("actionType").value=type;
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/dts/table/saveTable',
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
					   window.location.reload();
				   }
		});
	}

	function deleteColumn2(id){
		if(confirm("数据删除不能恢复，确定删除列定义信息吗？")){
          jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/dts/table/deleteColumn?columnId='+id,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   alert('操作成功完成！');
					   window.location.reload();
				   }
		     });
		}
	}

   function editColumn(){
	    var link="<%=request.getContextPath()%>/mx/dts/table/editColumn?tableName_enc=${tableName_enc}";
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑字段信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['980px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

</script>
</head>
<body>
<br>
<div class="x_content_title">
    <img src="<%=request.getContextPath()%>/images/window.png" alt="编辑合成表信息">&nbsp;编辑合成表信息
</div>
<form id="iForm"  name="iForm" method="post" action="">
<input type="hidden" id="nodeId" name="nodeId" value="${nodeId}"/>
<input type="hidden" id="actionType" name="actionType" />

<div class="content-block" style="width: 90%;"><br>
 
<table align="center" class="x-table-border" cellspacing="1"
	cellpadding="1" width="95%" border="0">
	<tr>
		<td height="28" width="12%">表名</td>
		<td class="x-content" colspan="3" height="28" width="88%"> 
		<c:choose>
		  <c:when test="${!empty table }">
		    ${table.tableName}
			<input type="hidden" id="tableName" name="tableName" value="${table.tableName}"/>
		  </c:when>
		  <c:otherwise>
		    <input type="text" id="tableName" name="tableName" class="x-text" size= "50" maxlength="25">
		  </c:otherwise>
		</c:choose>
		</td>
	</tr>
	<tr> 
		<td height="28" width="12%">标题</td>
		<td class="x-content" colspan="3" height="28" width="88%">
		<input type="text" id="title" name="title" class="x-text" size= "50" value="${table.title}"/>
		</td>
	</tr>
	<tr> 
		<td height="28">聚合主键</td>
		<td class="x-content" colspan="3" height="28">
		<input type="text" id="aggregationKeys" name="aggregationKeys" class="x-text" size= "50" value="${table.aggregationKeys}"/> （构成每条记录唯一性的字段列表，多个字段以半角的逗号隔开“,”）
		</td>
	</tr>
	<tr>
		<td height="28">查询数据集</td>
        <td class="x-content" colspan="3" height="28">
			<input type="hidden" id="queryIds" name="queryIds" value="${table.queryIds}">
			<input type="text" id="queryNames" name="queryNames" value="${queryNames}"
				   readonly="true" size="50" onclick="javascript:openQx();" class="x-text">
			&nbsp;
			<a href="#" onclick="javascript:openQx();">
			<img src="<%=request.getContextPath()%>/images/search_results.gif" border="0"
				title="如果表数据由多个查询数据集组成，请先建好查询数据再选择。">
			</a>
            &nbsp;请选择构成该数据表的多个查询数据集(多个查询的列组成行数据)
		</td>
	</tr>
	<tr>
		<td height="28">聚合数据集</td>
        <td class="x-content" colspan="3" height="28">
			<input type="hidden" id="aggregationQueryIds" name="aggregationQueryIds" value="${table.aggregationQueryIds}">
			<input type="text" id="queryNames2" name="queryNames2" value="${queryNames2}"
				   readonly="true" size="50" onclick="javascript:openAggrQx();" class="x-text">
			&nbsp;
			<a href="#" onclick="javascript:openAggrQx();">
			<img src="<%=request.getContextPath()%>/images/search_results.gif" border="0"
				title="如果表数据由多个查询数据集组成，请先建好查询数据再选择。">
			</a>
            &nbsp;请选择构成该数据表的多个聚合查询数据集(每个查询结果独立更新)
		</td>
	</tr>
    <tr>
		<td height="28">执行次序</td>
		<td class="x-content" colspan="3" height="28">
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
		    </select>&nbsp;&nbsp;（提示：数值小的先执行）
			<script type="text/javascript">
			    document.getElementById("sortNo").value="${table.sortNo}";
			</script>
		</td>
	</tr>
	<tr> 
	    <td height="28">主键(PK)</td>
		<td class="x-content" colspan="3" height="28">
            <select id="primaryKey" name="primaryKey" class="span2" style="height:20px">
			 <%
			 if(table != null && table.getColumns() != null){
	          for(ColumnDefinition column : table.getColumns()){
				  if(column.getColumnName() != null){
				     out.println("<option value='"+column.getColumnName()+"'>"+column.getColumnName()+"</option>");
				  }
			  }
			}
	        %>
		    </select>&nbsp;&nbsp;（提示：必须设置物理主键才能进行数据抓取）
			<script type="text/javascript">
			    document.getElementById("primaryKey").value="${table.primaryKey}";
			</script>
		</td>
	</tr>
	<tr> 
	    <td height="28">是否临时表</td>
		<td class="x-content" colspan="3" height="28">
            <select id="temporaryFlag" name="temporaryFlag" class="span2" style="height:20px">
			    <option value="0">否</option>
				<option value="1">是</option>
		    </select>&nbsp;&nbsp;（提示：临时表在每次抓取数据都将清空该表数据，并且表命名必须包含"_tmp_"）
			<script type="text/javascript">
			    document.getElementById("temporaryFlag").value="${table.temporaryFlag}";
			</script>
		</td>
	</tr>
	<tr> 
	    <td height="28">每次抓取前删除</td>
		<td class="x-content" colspan="3" height="28">
            <select id="deleteFetch" name="deleteFetch" class="span2" style="height:20px">
			    <option value="0">否</option>
				<option value="1">是</option>
		    </select>&nbsp;&nbsp;（提示：如果选择每次抓取前删除，将删除当天指定的记录）
			<script type="text/javascript">
			    document.getElementById("deleteFetch").value="${table.deleteFetch}";
			</script>
		</td>
	</tr>
	
	<tr>
		<td >描述</td>
		<td class="x-content" colspan="3" >
		    <textarea id="description" name="description" rows="8" cols="66" class="x-textarea span5" style="align:left;"><c:out value="${table.description}" /></textarea>
		</td>
	</tr>

<%if(table != null){%>
    <tr>
	<td colspan="4" align="left" width="100%">
	  <br>
	  <table align="center"  border="0" cellspacing="1" cellpadding="0" width="100%" class="list-box">
	   <tr class="list-title ">
			<td width="15%">字段名</td>
			<td width="15%">数据类型</td>
			<td width="25%">标题</td>
			<td width="25%">名称</td>
			<td width="10%">长度</td>
			<td width="10%" align="center">功能键</td>
		</tr>
		<%
		   if( table.getColumns() != null){
			for(ColumnDefinition column : table.getColumns()){
				if(column.getLength() <= 0){
					column.setLength(200);
				}
				pageContext.setAttribute("column", column);
		%>
		  <tr >
			<td height="32">${column.columnName}</td>
			<td height="32">
				<c:choose>
					<c:when test="${column.javaType == 'Integer' }">
						 整型
					</c:when>
					<c:when test="${column.javaType == 'Long' }">
						 长整型
					</c:when>
					<c:when test="${column.javaType == 'Double' }">
						 数值型
					</c:when>
					<c:when test="${column.javaType == 'Boolean' }">
						 逻辑型
					</c:when>
					<c:when test="${column.javaType == 'Date' }">
						 日期型
					</c:when>
					<c:otherwise>
						 字符串型
					</c:otherwise>
				</c:choose>
				 
			</td>
			<td height="32">
			<input type="text" name="${column.columnName}_title" class=" x-text" size="25"
			value="${column.title}"/>
			</td>
			<td height="32">
			<input type="text" name="${column.columnName}_name" class=" x-text" size="25"
			value="${column.name}"/>
			</td>
			<td height="32">
			<c:choose>
			  <c:when test="${column.javaType == 'String' }">
			  <input type="text" name="${column.columnName}_length" class=" x-text" 
			       value="${column.length}" size="3" maxLength="4"/>
			  </c:when>
			  <c:otherwise>
					默认值	
			  </c:otherwise>
			</c:choose>
			</td>
			<td align="center">
			  <c:choose>
			  <c:when test="${column.columnName == 'id' || column.columnName == 'ID' }">
			  
			  </c:when>
			  <c:when test="${column.columnName == 'aggregationkey' || column.columnName == 'aggregationKey' || column.columnName == 'AGGREGATIONKEY' }">
			  
			  </c:when>
			  <c:otherwise>
				<a href="#" onclick="javascript:deleteColumn2('<%=RequestUtils.encodeString(column.getId())%>');">删除</a>	 
			  </c:otherwise>
			</c:choose>
			<td>
		</tr>
		<%}
	   }
		%>
	</table>
	</td>
  </tr>
 <%}%>
</table>

  <br/>
	<div align="center">
	    <input type="button"  name="save" value="保存" class="btnGray" onclick="javascript:saveForm('');"/>
		<%if(table != null){%>
		<input type="button"  name="save" value="添加字段" class="btnGray" onclick="javascript:editColumn();"/>
		<%}%>
		<%if(table != null && table.getColumns() != null &&  table.getColumns().size()>0){%>
		<input type="button"  name="saveAndRecreate" value="保存并更新表" class="btnGray" 
		       onclick="javascript:saveForm('alterTable');"/>
		<input type="button"  name="transformTablex" value="重新取数" class="btnGray" 
		       onclick="javascript:transformTable();"/>
		<%}%>
	</div>
</fieldset>
</div>
</form>
<br>
<br>
<br>
</body>
</html>