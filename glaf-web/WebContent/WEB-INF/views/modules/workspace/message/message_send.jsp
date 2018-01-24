<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.List" %>
<%@ page import="java.net.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.workspace.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%!private String htmlspecialchars(String str) {
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot;");
		return str;
	}%>
<%
	//String htmlData = "";
	//ReportTask reportTask = (ReportTask) request.getAttribute("reportTask");
	//if (reportTask != null && reportTask.getSendContent() != null) {
	//	htmlData = htmlspecialchars(reportTask.getSendContent());
	//}
%>
<%
    Message bean = (Message) request.getAttribute("bean");
    String title = "";
	String recverIds = "";
	String recverName = "";
	String recverEmail = "";
	if (bean != null) {
		title = "Re:" + bean.getTitle();
		SysUser recver = bean.getSender();
		recverIds = recver == null ? "" : recver.getId() + "";
		recverName = recver == null ? "" : recver.getName()+"";
		recverEmail =recver == null ? "" : recver.getEmail();
  }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/scripts/kindeditor/themes/default/default.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/scripts/kindeditor/plugins/code/prettify.css" />
<script type='text/javascript' src='<%= request.getContextPath() %>/scripts/main.js'></script>
<script type='text/javascript' src="<%= request.getContextPath() %>/scripts/verify.js"></script>
<script charset="UTF-8"
	src="<%=request.getContextPath()%>/scripts/kindeditor/kindeditor-all-min.js"></script>
<script charset="UTF-8"
	src="<%=request.getContextPath()%>/scripts/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8"
	src="<%=request.getContextPath()%>/scripts/kindeditor/plugins/code/prettify.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/glaf-base.js"></script>
<script type="text/javascript">
    var context = "<%= request.getContextPath() %>";
    var contextPath = "<%= request.getContextPath() %>";

    var editor1 = null;

    KindEditor.ready(function(K) {
			    editor1 = K.create('textarea[id="content"]', {
				uploadJson : '<%=request.getContextPath()%>/mx/uploadJson',
				fileManagerJson : '<%=request.getContextPath()%>/mx/fileManagerJson',
				allowFileManager : true
			});
		});

  function changeSelect() {
    var recverType = getRadioValue('recverType');
	  $('recverName').value = '';
	  $('recverIds').value = '';
	  //$('toEmail').value='';
	if (recverType == 0) {
	  $('add_user').disabled = false;
	  //$('add_supplier').disabled = true;
	  $('add_dept').disabled = true;
	} else if(recverType ==1){
	  $('add_user').disabled = true;
	  $('add_dept').disabled = false;
	  //$('add_supplier').disabled = true;
	} else if(recverType == 2){
	  $('add_user').disabled = true;
	  $('add_dept').disabled = true;
	  //$('add_supplier').disabled = false;
	}
}

function saveAndSend(type){
    if(document.getElementById('recverName').value==''){
        alert("请选择收件人");
        return false;
    }
    if(document.getElementById('title').value==''){
        alert("主题不能为空");
        return false;
    }
	document.getElementById("content").value=editor1.html();
    document.getElementById("iForm").action= "<%=request.getContextPath()%>/mx/workspace/message/saveAndSend?messageType="+type;
    document.getElementById("iForm").submit();
}

</script>
<title>发送消息</title>
</head>

<body>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td class="nav-title"><span class="Title">工作台</span>&gt;&gt; 发送消息</td>
  </tr>
</table>
<html:form id="iForm" name="iForm" 
      action="/workspace/message.do?method=saveSend" method="post" onsubmit="return verifyAll(this);" >
<input type="hidden" name="sysType" value="1">
<table width="95%" border="0" align="center" cellpadding="5" cellspacing="0">
      <tr>
        <td width="50" class="input-box">收件人 <font color="#FF0000">*</font></td>
        <td>
		    <input id="recverName" name="recverName" type="text" class="input" size="45" value="<%= recverName %>" readonly="readonly" datatype="string" nullable="no" chname="接收人">
            <input id="recverIds" name="recverIds" type="hidden" value="<%= recverIds %>">
            
            <input name="recverType" type="radio" value="0" checked onClick="javascript:changeSelect()">
            <input name="add_user" type="button" class="button" value="选择用户"
			       onclick="javascript:selectUser('iForm', 'recverIds','recverName');">
            <input type="radio" name="recverType" value="1" onClick="javascript:changeSelect()">
            <input name="add_dept" type="button" class="button" value="选择部门"  
			       onclick="javascript:selectDept('iForm', 'recverIds','recverName');">
     
         </td>
      </tr>
      <!-- <tr><td width="50" class="input-box">邮&nbsp;&nbsp;箱</td>
          <td><input name="toEmail" type="text" datatype="string" nullable="no" searchflag="1" size="25"  readonly="readonly" class="input" value="<%=recverEmail %>" >
            &nbsp;&nbsp;注意： *选择供应商必需是系统用户才能发送系统信息* 
          </td>
      </tr> -->
      <tr>
        <td class="input-box">主&nbsp;&nbsp;题 <font color="#FF0000">*</font></td>
        <td><input id="title" name="title" type="text" class="input" size="85" maxlength="250" value="<%= title %>" datatype="string" nullable="no" chname="标题" maxsize="50"></td>
      </tr>
      <tr>
        <td valign="top" class="input-box2">内&nbsp;&nbsp;容 <font color="#FF0000">*</font></td>
        <td><textarea id="content" name="content" cols="58" rows="10" class="input" datatype="string" nullable="no" chname="内容" maxsize="2000" style="width:765px;height:350px;"></textarea></td>
      </tr>
      <tr>
        <td valign="top">&nbsp;</td>
        <td>
		    <input name="btn_save" type="button" class="button" value="发送系统消息"  
			       onClick="javascript:saveAndSend('msg')">
            <input name="btn_email" type="button" class="button" value="发送Email"  
			       onClick="javascript:saveAndSend('email')">
            <input name="btn_both" type="button" class="button" value="双发送"  
			       onClick="javascript:saveAndSend('both')">
        </td>
      </tr>
    </table>
</html:form>
</body>
</html>
