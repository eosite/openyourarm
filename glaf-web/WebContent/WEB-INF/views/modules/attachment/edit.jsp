<%--
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
 String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title>文件信息</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/artDialog/skins/default.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css"/>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script language="javascript">
 
 //(用于onKeypress事件)浮点数字框不能输入其他字符
 function numberFilter() {
		var berr=true;
		if (!(event.keyCode==46 || (event.keyCode>=48 && event.keyCode<=57)))
		{
			alert("该字段只能输入数字！");
			berr=false;
		}
		return berr;
	}

  //(用于onKeypress事件)浮点数字框不能输入其他字符
  function integerInputFilter() {
		var berr=true;
		if (!((event.keyCode>=48 && event.keyCode<=57)))
		{
			alert("该字段只能输入正整数！");
			berr=false;
		}
		return berr;
	}

 //在确定提交前的编码里
 function isInteger(obj,name){
	var value1 = obj.value;
	for(i=0;i<obj.value.length;i++){
		var ch=obj.value.charAt(i);
		if((ch<'0' || ch>'9')){
			alert("<"+name+">只能是数字！");
			obj.focus();
		   return false;
		}
		if(value1<=0){
			alert("<"+name+">应该是一个大于0的数字！");
			obj.focus();
		   return false;
		}
	}
	return true;	    
}

 function submitRequest(form){
    var name = document.getElementById("name").value.trim();
	 
    if(name == ""){
		alert("名称不能为空！");
		form.name.focus();
		return ;
	 }

    //alert(document.getElementById("desc").value.trim().length);
	if(document.getElementById("desc").value.trim().length>500){
		alert("描述长度超过500字节！");
		form.desc.focus();
		return ;
	}

	<c:if test="${ empty fileAttachment  }">
	if(document.getElementById("file").value.trim().length==0){
		alert("请选择要上传的文件！");
		form.file.focus();
		return ;
	}
	</c:if>

	<c:if test="${not empty fileAttachment.fileExt }">
	if(document.getElementById("file").value != "" && 
	  !document.getElementById("file").value.endsWith("${fileAttachment.fileExt}")){
		alert("选择的文件扩展名必须是${fileAttachment.fileExt}！");
		form.file.focus();
		return ;
	}
    </c:if>
    document.getElementById("ok_btn").disabled=!document.getElementById("ok_btn").disabled;
	form.submit();
	//window.parent.reload();
	//window.close();
    //location.href='<%=com.glaf.core.util.RequestUtils.decodeURL(request.getParameter("fromUrl"))%>';
 }

</script>
</head>
<body>
<center>
<form name="iForm" method="post" ENCTYPE="multipart/form-data"
	class="x-form"
	action="<%=request.getContextPath()%>/mx/file/attachment/upload?nodeId=${nodeId}&type=${type}"
	onsubmit="return checkForm();">
<input type="hidden" id="locked" name="locked" value="0">
<c:if test="${not empty nodeId}">
<input type="hidden" name="nodeId" value="${nodeId}">
</c:if>

<c:choose>
	<c:when test="${not empty fileAttachment.id }">
	<input type="hidden" id="id" name="id" value="${fileAttachment.id}">
	</c:when>
	<c:otherwise>
				 
	</c:otherwise>
</c:choose>

<div class="content-block" style="width: 80%;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="编辑文件信息">&nbsp;编辑文件信息
 
</div>
<br>
 
<table border=0 cellspacing=0 cellpadding=2>
	<tbody>
 
		<tr class="x-content-hight">
			<td align="left" width="18%" noWrap><span>名称</span></td>
			<td align="left">
			<input id="name" name="name" type="text" size="40" class="input-xlarge x-text" maxlength="200"
				value="${fileAttachment.name}">
			</td>
		</tr>
		<tr>
			<td width="18%" align="left">描述</td>
			<td align="left">
			    <textarea  id="desc" name="desc" class="x-textarea"  rows="5" cols="38"  
				           style="width:264px;height:90px;">${fileAttachment.desc}</textarea> 
			</td>
		</tr>

		<tr class="x-content-hight">
			<td align="left" width="18%" align="left"><span>文件</span></td>
			<td align="left" width="82%">
			<c:choose>
			  <c:when test="${not empty fileAttachment.path and fileAttachment.image }">
			   <br>
			   <br>
			   <img src="<%=request.getContextPath()%>/${fileAttachment.path}" border="0"/>
			   <br>
			   <br>
			  </c:when>
			  <c:when test="${not empty fileAttachment.path }">
			   <br>
			   <br>
			   <a href="<%=request.getContextPath()%>/${fileAttachment.path}">${fileAttachment.filename}</a>
			   <br>
			   <br>
			  </c:when>
            </c:choose>
			<input type="file" id="file" name="file" size="40"
				class="input-file x-text">
			<c:if test="${not empty fileAttachment.path}">
			   <br>请注意：如果需要替换原始文件，文件扩展名必须和以前相同。
            </c:if>
            </td>
		</tr>
	</tbody>
</table>
 

<div align="center">
<br />
<input id="ok_btn" name="ok_btn" type="button" class="btnGray" value="确定"
	onclick="javascript:submitRequest(this.form);" /> 
<input type="button" class="btnGray" value="返回"
	onclick="javascript:window.history.go(-1);" /> 
<br />
<br />
</div>

</div>
</form>
</center>
<br />
<br />

<%@ include file="/WEB-INF/views/inc/footer.jsp"%>