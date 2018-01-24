<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%
String theme = com.glaf.core.util.RequestUtils.getTheme(request);
request.setAttribute("theme", theme);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基本情况列表</title>
<%@include file="../inc/script.jsp" %>
<link href="<%=request.getContextPath()%>/css/site.css" type="text/css" rel="stylesheet">
<script language="javascript" src='<%=request.getContextPath()%>/scripts/verify.js'></script>
<script language="javascript" src='<%=request.getContextPath()%>/scripts/main.js'></script></head>
<script type="text/javascript">
	function download(id){
		document.getElementById("hiddenFrame").src = "<%=request.getContextPath()%>/mx/docManage/yz/baseinfo/downFile?id="+id;
	}
</script>
</head>
<body style="margin:1px;"> 
	<c:if test="${baseInfoFlag==0 }">
		<script type="text/javascript">
			alert("没有添加【${name}】单位工程基本情况，请先添加【${name}】单位工程基本情况！");
			window.close();
		</script>
	</c:if>
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
	  <tr>
	    <td class="box-mm"><table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
	    <tr>
	        <td class="input-box2" valign="top">类　　型</td>
	        <td>
	        	<c:if test="${baseInfo.type=='1' }">标段基本情况</c:if>
	        	<c:if test="${baseInfo.type=='2' }">单位工程基本情况</c:if>
	        </td>
	      </tr>
	      <tr>
	        <td class="input-box">标段名称</td>
	        <td>${muiProjList.projName }</td>
	      </tr>
	       <tr>
	        <td class="input-box">单位工程</td>
	        <td>${treepinfo.indexName }</td>
	      </tr>
	      <tr>
	        <td class="input-box2" valign="top">内　　容</td>
	        <td><textarea name="fileContent2" cols="70" rows="15" class="input" readonly="readonly">${baseInfo.fileContent2 }</textarea></td>
	      </tr>
	      <tr>
	        <td class="input-box2" valign="top">附　　件</td>
	        <td><a href="javascript:download(${baseInfo.id });">${baseInfo.fileName }</a></td>
	      </tr>
	    </table></td>
	  </tr>
	  <tr>
	    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr class="box">
	        <td class="box-lb">&nbsp;</td>
	        <td class="box-mb">&nbsp;</td>
	        <td class="box-rb">&nbsp;</td>
	      </tr>
	    </table></td>
	  </tr>
	</table>
	<iframe id="hiddenFrame" style="display: none"></iframe>
</body>
</html>