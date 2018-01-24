<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.form.core.domain.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
	FormLayout formLayout = (FormLayout) request.getAttribute("formLayout");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看表单布局信息</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content ">
<br>
<div class="x_content_title">
<img src="<%=request.getContextPath()%>/images/window.png" alt="查看表单布局信息">&nbsp;
查看表单布局信息</div>
<br>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
    <!-- <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	<label for="name" class="required">名称&nbsp;</label> ${formLayout.name} 
	</td>
    </tr> -->

    <c:if test="${!empty formLayout.imagePath}">
	<tr>
	  <td colspan="2" align="center">
	      <img id="file" src="<%=request.getContextPath()%>/${formLayout.imagePath}" border="0">
	  </td>
    </tr>
	</c:if>
 
    <!-- <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
        <div>
          <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" 
		          onclick="javascript:history.back();">返回</button>
	    </div>
	    </td>
    </tr> -->
</table>   
 
</div>     
 
</body>
</html>