<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*" %> 
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.identity.*"%>
<%@ page import="com.glaf.datamgr.domain.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据集列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
<script type="text/javascript">

    function searchDataSets(){
		document.iForm.method="POST";
		document.iForm.submit();
	}

	function checkDataSets(){
        var obj = document.getElementsByName("id");
		var ids = "";
		var names = "";
		for ( var i = 0; i < obj.length; i++) {
			var e = obj.item(i);
			if (e.checked) {
				ids = ids + e.getAttribute("value") + ",";
				names = names  + e.getAttribute("text") + ",";
			}
		}
		//alert(ids);
		//alert(names);
		var closeWin = true;
		var parent_window = getOpener();
		if(parent_window == null){
			parent_window = window;
			closeWin = false;
			var index = parent.layer.getFrameIndex(window.name);
            var x_id = parent.document.getElementById("${elementId}");
			var x_name = parent.document.getElementById("${elementName}");
			x_id.value = ids;
			x_name.value = names;
			parent.layer.close(index);
		} else {
			//alert(parent_window);
			var x_id = parent_window.document.getElementById("${elementId}");
			var x_name = parent_window.document.getElementById("${elementName}");
			x_id.value = ids;
			x_name.value = names;
			if(closeWin){
			  window.close();
			}
		}
	}
  
</script>
</head>
<body leftmargin="0" topmargin="0">  
<center>
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="数据集列表">&nbsp;数据集列表</div>
<form id="iForm" name="iForm" method="post" action="<%=request.getContextPath()%>/mx/datamgr/dataSetExport/datasetList?elementId=${elementId}&elementName=${elementName}&id=${dataSetExport.id}&xtype=${xtype}">
<table width="95%" height="30" border="0" cellspacing="1" cellpadding="0" class="mainTable">
  <tr>
    <td width="100%" align="right">
		<select id="nodeId" name="nodeId">
			<option value="">----所有分类----</option>
			<c:forEach items="${treeModels}" var="treeModel">
			   <option value="${treeModel.id}">${treeModel.name}</option>
				  <c:forEach items="${treeModel.children}" var="child">
				  <option value="${child.id}">&nbsp;&nbsp;---->${child.name}</option>
					  <c:forEach items="${child.children}" var="c">
					  <option value="${c.id}">&nbsp;&nbsp;&nbsp;&nbsp;-------->${c.name}</option>
					  </c:forEach>
				  </c:forEach>
			</c:forEach>
		</select>
		<script type="text/javascript">
			document.getElementById("nodeId").value="${nodeId}";
		</script>
        &nbsp;
		<input type="text" id="keywordsLike" name="keywordsLike" value="${keywordsLike}" class=" x-searchtext">&nbsp;
		<input type="button" value="查找" class="btnGray" onclick="javascript:searchDataSets();">
		&nbsp;
	    <input type="button" value="确定" class="btnGray" onclick="javascript:checkDataSets();">
	</td>
  </tr>
</table>
</form>
<% 
    List<DataSet> datasetList = (List<DataSet>)request.getAttribute("list"); 
	if(datasetList != null && !datasetList.isEmpty() ){   
%> 
		<table width="95%" height="80" border="0" cellspacing="1" cellpadding="0" class="mainTable">
		  <%
		  int size = datasetList.size() / 2 ;
		  for(int j=0; j<=size; j++) { 
		  %>
		  <tr>
			<%
			for(int i=j*2; i<(j+1)*2; i++) { 
			  if(i<datasetList.size()){
                 DataSet dataset = datasetList.get(i);
				 String checked = "";
				 if(dataset.isChecked()){
					 checked = "checked";
				 }
			%>
			<td class="weekend" align="left" height="25" width="245">			  
			  <input type="checkbox" id="id_<%=dataset.getId()%>" name="id" <%=checked%> text="<%=dataset.getTitle()%>" value="<%=dataset.getId()%>">
			    <a href="<%=request.getContextPath()%>/mx/dataset/sqlbuilder?id=<%=dataset.getId()%>" target="_blank">
				<%				 				      
					out.println(dataset.getTitle()+"&nbsp;["+dataset.getName()+"]");	    
				%>		
				</a>
			</td>
			<%
			   } else{
			%>
			<td class="weekend" align="left" height="25" width="180">&nbsp;</td>
			<%  }
		    }%>
		  </tr>
		  <%}%>
		</table>
<%}%>
<br>
<br>
<br>
<br>
</center>
</body>  
</html>