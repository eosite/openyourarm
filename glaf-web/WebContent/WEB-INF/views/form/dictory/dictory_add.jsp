<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
    String context = request.getContextPath();
    int parent=ParamUtil.getIntParameter(request, "parent", 0);
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基础平台系统</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src='<%=context%>/scripts/main.js'></script>
<script type="text/javascript" src='<%=context%>/scripts/verify.js'></script> 
</head>
<body style="margin:10px;">
<html:form  id="editForm"  action="${contextPath}/mx/form/formDictory/saveAdd" method="post" 
      onsubmit="return verifyAll(this);">
<input type="hidden" name="nodeId" value="<%=parent%>">
<div class="easyui-panel" title="新增数据字典" style="width:550px;padding:10px"> 
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
      <tr>
        <td width="21%" class="input-box">名称&nbsp;<font color="red">*</font></td>
        <td width="79%">
		<input type="text" name="name"  datatype="string" nullable="no" maxsize="50" chname="名称"
		       class="easyui-validatebox" data-options="required:true" size="50">
		</td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">代码</td>
        <td>
		<input type="text" name="code" datatype="string" nullable="yes" maxsize="50" chname="代码"
		       class="easyui-validatebox" size="50">
		</td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">属性值</td>
        <td>
		<input type="text" name="value" datatype="string" nullable="yes" maxsize="200" chname="属性值"
		       class="easyui-validatebox" size="50">
		</td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">描述</td>
        <td>
		<input type="text" name="desc" size="50" class="easyui-validatebox" 
		       datatype="string" nullable="yes" maxsize="200" chname="描述">
		</td>
      </tr>

	  <c:forEach items="${list}" var="a">
	  <tr>
        <td class="input-box2" valign="top">
		${a.title}&nbsp;<c:if test="${a.required==1}"><font color="red">*</font></c:if>
		</td>
        <td>
		   <c:choose>
		     <c:when test="${a.type=='String'}">
			    <input type="text" name="${a.name}" datatype="string" nullable="${a.nullable}" maxsize="${a.length}" 
				       chname="${a.title}" value="${a.value}" size="50"
				       class="easyui-validatebox"
				<c:if test="${a.required==1}">data-options="required:true"</c:if>>
             </c:when>
			 <c:when test="${a.type=='Date'}">
			    <input type="text" name="${a.name}" class="easyui-datebox"  datatype="datetime" nullable="${a.nullable}" 
				       maxsize="${a.length}" chname="${a.title}" size="50"
				       value="<fmt:formatDate value="${a.value}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				<c:if test="${a.required==1}">data-options="required:true"</c:if>
				>&nbsp;
				 
             </c:when>
			 <c:when test="${a.type=='Long'}">
			    <input type="text" name="${a.name}" datatype="integer" nullable="${a.nullable}" maxsize="12" chname="${a.title}" 
				       value="${a.value}" size="50"
				       class="easyui-validatebox"
				<c:if test="${a.required==1}">data-options="required:true"</c:if>>
             </c:when>
			 <c:when test="${a.type=='Double'}">
			    <input type="text" name="${a.name}" datatype="double" nullable="${a.nullable}" maxsize="20" chname="${a.title}" 
				       value="${a.value}" size="50"
				       class="easyui-validatebox"
				<c:if test="${a.required==1}">data-options="required:true"</c:if>
				>
             </c:when>
		   </c:choose>
		
		</td>
      </tr>
	  </c:forEach>
	   
      <tr>
        <td class="input-box2" valign="top">是否有效</td>
        <td>
          <input type="radio" name="blocked" value="1">否
          <input type="radio" name="blocked" value="0" checked>是
        </td>
      </tr>
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
              <input name="btn_save3" type="submit" value="保存" class="button"></td>
      </tr>
  </table> 
 </div>
</html:form> 
</body>
</html>
