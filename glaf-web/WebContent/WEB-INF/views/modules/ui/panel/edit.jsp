<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.glaf.core.security.*"%>
<%@ page import="com.glaf.ui.model.*"%>
<%!
private String htmlspecialchars(String str) {
	str = str.replaceAll("&", "&amp;");
	str = str.replaceAll("<", "&lt;");
	str = str.replaceAll(">", "&gt;");
	str = str.replaceAll("\"", "&quot;");
	return str;
}
%>
<%
    String context = request.getContextPath();
	String isSystem = request.getParameter("isSystem"); 
	LoginContext loginContext = RequestUtils.getLoginContext(request);
	String htmlData = "";
	Panel panel = (Panel)request.getAttribute("panel");
	if(panel != null && panel.getContent() != null){
		htmlData = htmlspecialchars(panel.getContent());
	}
%>
<!DOCTYPE html>
<html>
<head>
<title>桌面板块</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/default/easyui.css" >
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/css/portal.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/kindeditor/plugins/code/prettify.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/layer/skin/layer.css"/>
<script charset="UTF-8" src="<%=request.getContextPath()%>/scripts/kindeditor/kindeditor-all-min.js"></script>
<script charset="UTF-8" src="<%=request.getContextPath()%>/scripts/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/scripts/kindeditor/plugins/code/prettify.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/layer/layer.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
    var editor1 = null;
	var contextPath = "<%=request.getContextPath()%>";

    KindEditor.ready(function(K) {
			    editor1 = K.create('textarea[id="content"]', {
				uploadJson : '<%=request.getContextPath()%>/mx/uploadJson',
				fileManagerJson : '<%=request.getContextPath()%>/mx/fileManagerJson',
				allowFileManager : true
			});
		});

 	function changeDiv(state){
		if(state=='L'){
			$("#urlDir").show();
			$("#divDir").hide();
			document.getElementById("type").value = state;
		}
		if(state=='T'){
			$("#divDir").show();
			$("#urlDir").hide();
			document.getElementById("type").value = state;
		}
	}

    function submitXY(){
	  var subject = document.getElementById("title").value;
	  if(subject == ""){
		  alert("标题是必须的！");
          document.getElementById("title").focus();
		  return;
	  }
	  var type = document.getElementById("type").value;
	  if("L" == type){
		  var link = document.getElementById("link").value;
	      if(link == ""){
		    alert("链接地址是必须的！");
            document.getElementById("link").focus();
		    return;
	      }
	  }

	  if("T" == type){
		  //alert(editor1.html());
		  document.getElementById("content").value=editor1.html();
		  var content = document.getElementById("content").value;
	      if(content == ""){
		    alert("文本内容是必须的！");
            document.getElementById("content").focus();
		    return;
	      }
	  }
	  document.getElementById("content").value=editor1.html();
	  document.getElementById("iForm").submit();
	}

	function openChart(){
            var selected = jQuery("#chartIds").val();
            var link = '<%=request.getContextPath()%>/mx/bi/chart/chartTree?elementId=link&elementName=link&chooseType=link&nodeCode=report_category';
			var x=100;
			var y=100;
			if(is_ie) {
				x=document.body.scrollLeft+event.clientX-event.offsetX-200;
				y=document.body.scrollTop+event.clientY-event.offsetY-200;
			 }
			openWindow(link,self,x, y, 495, 480);
		}
	
	function isGobalChange(ele){
		if(ele){
			document.getElementById("selectRole").disabled = "disabled" ;
			document.getElementById("prem").value = "" ;
			document.getElementById("premVal").value = "" ;
		}else{
			document.getElementById("selectRole").disabled = "" ;
		}
	}

	function selectRole2(formName, elementId, elementName){
		var x_selected =  document.getElementById(elementId);
		var url=contextPath+"/mx/base/identityChoose/chooseRoles?formName="+formName+"&elementId="+elementId+"&elementName="+elementName;
		if(x_selected != null && x_selected.value != ""){
			url = url + "&selecteds="+x_selected.value;
		}
		var x=200;
		var y=150;
		if(is_ie) {
			x=document.body.scrollLeft+event.clientX-event.offsetX-100;
			y=document.body.scrollTop+event.clientY-event.offsetY+70;
		 }
		 openWindow(url,self,x, y, 498, 420);
 }

 function showRoleUsers(id){
	 var link = contextPath+'/mx/identity/user/viewRoleUsers?id='+id;
	 jQuery.layer({
					type: 2,
					maxmin: true,
					shadeClose: true,
					title: "查看用户角色",
					closeBtn: [0, true],
					shade: [0.8, '#000'],
					border: [10, 0.3, '#000'],
					offset: ['10px',''],
					fadeIn: 100,
					area: ['580px', (jQuery(window).height() - 50) +'px'],
					iframe: {src: link}
				});
 }
 
 
 function changeCustom(state){
		if(state==1){
			$("#customDiv").show();
		}
		if(state==0){
			$("#customDiv").hide();
		}
	}
 function chooseImg(){
	   var link="/glaf/mx/image/choose?elementId=imgUrl";
	   openWindow(link, self, 50, 50, 1098, 520);
 }
</script>
</head>
<body>
<center> 
<form id="iForm" name="iForm" class="x-form" method="post"
	action="<%=request.getContextPath()%>/mx/panel/save"><input
	type="hidden" name="panelId" value="${panel.id}">
<c:if test="${ panel.id > 0 }">
	<input type="hidden" name="id" value="${panel.id}">
</c:if> 
<c:if test="${ isSystem eq 'true'}">
	<input type="hidden" id="isSystem" name="isSystem"
		value="${isSystem}">
</c:if>
<div class="content-block" style="width: 845px;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="首页板块设置">&nbsp;首页板块设置</div>
<br>
<table width="98%" align="center" border="0" cellspacing="0"
	cellpadding="5">

	<tr height="27">
		<td align="left" width="20%" valign="top">版块名称 *</td>
		<td align="left" width="70%"><input id="title" name="title" type="text" size="50"
			maxlength="50" class="input-xlarge x-text"
			value="${panel.title}" datatype="string"></td>
	</tr>

	<%if (loginContext.isSystemAdministrator() && StringUtils.equals(isSystem, "true")) {%>
	<tr height="27">
		<td align="left" width="20%" valign="top">版块编码 *</td>
		<td align="left" width="70%"><input name="name" type="text" size="50"
			maxlength="20" class="input-xlarge x-text" value="${panel.name}"
			datatype="string"></td>
	</tr>

	<tr height="27">
		<td align="left" width="20%" valign="top">模块名称</td>
		<td align="left" width="70%"><input name="moduleName" type="text" size="50"
			maxlength="50" class="input-xlarge x-text"
			value="${panel.moduleName}" datatype="string"></td>
	</tr>
	<tr height="27">
		<td align="left" width="20%" valign="top">模块编码</td>
		<td align="left" width="70%"><input name="moduleId" type="text" size="50"
			maxlength="20" class="input-xlarge x-text"
			value="${panel.moduleId}" datatype="string"></td>
	</tr>

	<%}%>

	<tr height="27">
		<td align="left" width="20%" valign="top">模块列位置</td>
		<td align="left" width="70%">
		    <select id="columnIndex" name="columnIndex" class="span2">
				<option value="0" <c:if test="${panel.columnIndex == '0'}">selected</c:if>>第一列</option>
				<option value="1" <c:if test="${panel.columnIndex == '1'}">selected</c:if>>第二列</option>
				<option value="2" <c:if test="${panel.columnIndex == '2'}">selected</c:if>>第三列</option>
		    </select>
		</td>
	</tr>

	<tr>
		<td width="20%" align="left" valign="top">模块内容类型</td>
		<td width="70%" align="left" valign="top" >  
			<input id="type" name="type" type="radio" value="L" onclick="changeDiv('L');" 
			<c:if test="${panel.type == 'L'}">checked</c:if> />链接地址
			<input id="type" name="type" type="radio" value="T" onclick="changeDiv('T');" 
			<c:if test="${panel.type == 'T'}">checked</c:if> />文本内容
			<br>
			<div id="urlDir" style="display:block;">
		      <textarea id="link" name="link" rows="5" cols="39"
			    class="input-xlarge x-textarea" style="width: 350px; height: 90px; text-align: left;"><c:out
			    value="${panel.link}" escapeXml="false" /></textarea>
				&nbsp;<a href="#" onclick="javascript:openChart();">
					    <img src="<%=request.getContextPath()%>/images/process.gif" border="0">
				      </a>
			</div>
			<div id="divDir" style="display:none;">
		       <textarea id="content" name="content" rows="8" cols="60"  
			    style="width: 680px; height: 350px; visibility:hidden; "><%=htmlData%></textarea>
			</div>
		</td>
	</tr>

	<tr>
		<td align="left" width="20%" height="35" >高度</td>
		<td align="left" align="left"  height="35" >
			<input type="text" name="height" value="${panel.height}" size="3" maxLength="3" class="input-mini x-text" /> (例如: 300, 显示一个高度为300像素的模块)
		</td>
	</tr>

	<!-- <tr>
		<td align="left" width="20%"   valign="top">样式</td>
		<td align="left" align="left" valign="middle" >
			<input type="text" name="style" class="input-xlarge x-text" />
			<br> (例如 height:320px; 显示一个高度为320像素的模块)
		</td>
	</tr> -->

	<tr>
		<td align="left" width="20%" valign="top">是否可收缩</td>
		<td align="left" align="left" valign="middle" >
			<input type="radio" name="collapsible" value="true"
						<c:if test="${panel.collapsible == 1}">checked</c:if>>是&nbsp;&nbsp;
			<input type="radio" name="collapsible" value="false"
						<c:if test="${panel.collapsible == 0}">checked</c:if>>否
		</td>
	</tr>

	<tr>
		<td align="left" width="20%" valign="top">是否可关闭</td>
		<td align="left" align="left" valign="middle" >
			<input type="radio" name="close" value="true"
						<c:if test="${panel.close == 1}">checked</c:if>>是&nbsp;&nbsp;
			<input type="radio" name="close" value="false"
						<c:if test="${panel.close == 0}">checked</c:if>>否
		</td>
	</tr>
	<tr height="27">
		<td align="left" width="20%" valign="top">是否显示边框</td>
		<td align="left"><input type="radio" name="border" value="0"
			<c:if test="${panel.border == 0}">checked</c:if>>是&nbsp;&nbsp;
		<input type="radio" name="border" value="1"
			<c:if test="${panel.border == 1}">checked</c:if>>否</td>
	</tr>
	<tr height="27">
		<td align="left" width="20%" valign="top">是否显示标题栏</td>
		<td align="left"><input type="radio" name="header" value="0"
			<c:if test="${panel.header == 0}">checked</c:if>>是&nbsp;&nbsp;
		<input type="radio" name="header" value="1"
			<c:if test="${panel.header == 1}">checked</c:if>>否</td>
	</tr>
	<tr height="27">
		<td align="left" width="20%" valign="top">是否启用iframe</td>
		<td align="left"><input type="radio" name="ziframe" value="1"
			<c:if test="${panel.ziframe == 1}">checked</c:if>>是&nbsp;&nbsp;
		<input type="radio" name="ziframe" value="0"
			<c:if test="${panel.ziframe == 0}">checked</c:if>>否 <span style="color: red;">(非特殊情况请不要启用)</span></td> 
	</tr>
	<tr height="27">
		<td align="left" width="20%" valign="top">自定义按钮</td>
		<td>
			<input type="radio" name="custom" value="1" onchange="changeCustom(1);" <c:if test="${panel.custom == 1}">checked</c:if>>是&nbsp;&nbsp;
			<input type="radio" name="custom" value="0" onchange="changeCustom(0);"<c:if test="${panel.custom == 0}">checked</c:if>>否&nbsp;&nbsp;
			<div id="customDiv" style="<c:if test="${panel.custom == 0}">display: none;</c:if>">
				<input type="hidden" id="panelButtonId" name="panelButtonId" value="${panelButton.id}">
				<div>
					打开方式：&nbsp;
					<input type="radio" name="openType" value="0"
						<c:if test="${panelButton.openType == 0}">checked</c:if>>当前打开
					<input type="radio" name="openType" value="1"
						<c:if test="${panelButton.openType == 1}">checked</c:if>>弹出layer
					<input type="radio" name="openType" value="2"
						<c:if test="${panelButton.openType == 2}">checked</c:if>>弹出窗口
				</div> 
				<div>
					按钮图片：<input  class="input-xlarge x-text" type="text" id="imgUrl" name="imgUrl" onclick="javascript:chooseImg();"  value="${panelButton.imgUrl}">
				</div>
				<div>
					链接地址：<br>
					 <textarea id="href" name="href" rows="5" cols="39"
				    		class="input-xlarge x-textarea" style="width: 350px; height: 90px; text-align: left;"><c:out
				    		value="${panelButton.href}" escapeXml="false" /></textarea>
				</div>
			</div>
		</td>
	</tr>
	<% if("true".equals(isSystem)){%>
	<tr height="27">
		<td align="left" width="20%" valign="top">是否公开</td>
		<td align="left">
			<input type="checkbox" id="isGobal"  name="isGobal" value="true" onchange="javascript:isGobalChange(this.checked)"/>
		</td>
	</tr>
	
	<tr height="27" >
		<td align="left" width="20%" valign="top">权限</td>
		<td align="left">
			<input type="hidden" id="prem"  name="prem" value="${selected}"/>
			<input type="hidden" id="premVal" name="premVal" value="${selected}"/> 
			<input  class="btn" type="button" id="selectRole"  value="选择查看" 
			        onclick="javascript:selectRole2('iForm','prem','premVal');">
              <c:if test="${not empty roles}">
			      <br>已经授权角色：&nbsp;
			      <c:forEach items="${roles}" var="r">
				      <a href="#" onclick="javascript:showRoleUsers(${r.id});">${r.name} &nbsp;
				  </c:forEach>
			  </c:if>
		</td>
	</tr>
	<%} %>

	<tr height="27">
		<td align="left" width="20%" valign="top">是否启用</td>
		<td align="left"><input type="radio" name="locked" value="0"
			<c:if test="${panel.locked == 0}">checked</c:if>>是&nbsp;&nbsp;
		<input type="radio" name="locked" value="1"
			<c:if test="${panel.locked == 1}">checked</c:if>>否</td>
	</tr>
</table>
 
<div style="width: 645px;" align="center"><br />
<input name="btn_save2" type="button" value="保存" class="btn btn-primary" onclick="javascript:submitXY();">
<input name="btn_back" type="button" value="返回" class="btn"
	   onclick="javascript:history.back();"> <br />
<br />
</div>
</div>

</form>
</center>
<script type="text/javascript">
   <c:if test="${!empty panel.type}">
    changeDiv('${panel.type}');
   </c:if>
   
   if('${panel.actorId}' == 'system') {
	   document.getElementById('isGobal').checked = "checked" ;
	   isGobalChange(true);
   } 
</script>
</body>
</html>
