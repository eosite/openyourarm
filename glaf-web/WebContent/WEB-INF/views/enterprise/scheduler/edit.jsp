<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.job.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%
 String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title>任务业务调度管理</title>
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/scripts/calendar/skins/aqua/theme.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/main.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/verify.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/calendar/calendar.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/calendar/lang/calendar-en.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/calendar/calendar-setup.js"></script>
<script type="text/javascript">

  String.prototype.trim = function() {
     return this.replace(/(^\s*)|(\s*$)/g, "");
  }

 function check_integer(xx){
	 if (!(event.keyCode==45 || (event.keyCode>=48 && event.keyCode<=57))) {
		alert("该字段只能输入数字！");
		return false;
	 }
	var x = xx.value*1;
	if(x >2147483647 || x <-2147483648){
		 return false;
	 }
	 return true;
 }

 function submitRequest() {
    var taskName = document.schedulerForm.taskName.value.trim();
	var title = document.schedulerForm.title.value.trim();

    if(taskName == "")	 {
		alert("任务名称不能为空！");
		document.schedulerForm.taskName.focus();
		return;
	 }

	 if(title == "")	 {
		alert("任务主题不能为空！");
		document.schedulerForm.title.focus();
		return;
	 }

     document.schedulerForm.submit();
 }

 function openQtz(){
	var url= '<%=request.getContextPath()%>/quartz.html';
    var x=180;
    var y=120;
    if(is_ie) {
	    x=document.body.scrollLeft+event.clientX-event.offsetX-100;
	    y=document.body.scrollTop+event.clientY-event.offsetY-50;
     }
	 var f = "height=580,width=780,status=0,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+y+",left="+x+",resizable=no,dialog=yes,minimizable=no";
	 if(is_ie){
         window.open(url, "Quartz参考", f);
	 } else {
		 window.open(url, parent, f, true);
	 }
 }

 function openExpr2(){
	var expression = document.getElementById("expression").value;
    var url= '<%=request.getContextPath()%>/mx/enterprise/scheduler/choose?elementId=expression&expression='+expression;
    var x=180;
    var y=120;
    if(is_ie) {
	    x=document.body.scrollLeft+event.clientX-event.offsetX-100;
	    y=document.body.scrollTop+event.clientY-event.offsetY-50;
     }
	 var f = "height=490,width=860,status=0,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+y+",left="+x+",resizable=no,dialog=yes,minimizable=no";
	 //alert(is_ie);
	 if(is_ie){
         window.open(url, "Quartz表达式", f);
		 //openWindow(url,parent,0,0,400,400);
	 } else {
		 window.open(url, parent, f, true);
		 //openWindow(url,parent,0,0,400,400);
	 }
 }

    function changeXDiv(state){
		if(state=='J'){
			jQuery("#jobDiv").show();
			jQuery("#springDiv").hide();
		}
		if(state=='S'){
			jQuery("#jobDiv").hide();
			jQuery("#springDiv").show();
		}
	}

</script>
<body id="document" style="padding-left:120px;padding-right:120px">
 
<br> 
<html:form action="${contextPath}/mx/enterprise/scheduler/save" method="post"  onsubmit="return verifyAll(this);">
<input type="hidden" name="status" value="0">
<c:if test="${not empty scheduler.id}">
<input type="hidden" name="id" value="${scheduler.id}">
</c:if>
<input type="hidden" name="taskType" value="${taskType}">

<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png"
	alt="业务调度管理"> &nbsp;业务调度管理
</div>
<br>
 
<table border="0" cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-condensed">
	<tr>
		<td width="25%" height="24">名称&nbsp;*</td>
		<td height="24">
		<input type="text" name="taskName" size="50" maxlength="50"
		       class="input span3 x-text" value="${scheduler.taskName}" datatype="string" nullable="no" maxsize="50" 
			   chname="业务调度名称">
		</td>
	</tr>

	<tr>
		<td width="25%" height="24">主题&nbsp;*</td>
		<td height="24">
		<input type="text" name="title" size="50"  class="input span3 x-text"
			maxlength="255" value="${scheduler.title}" datatype="string" nullable="no" maxsize="50" chname="业务调度主题">
		</td>
	</tr>

	<!-- <tr>
		<td width="25%" height="24">内容&nbsp;&nbsp;</td>
		<td height="24"><textarea name="content" rows="8" cols="39"
			 class="input span3 x-text"  style="width:295px;height:120px;"><c:out value="${scheduler.content}" /></textarea>
		</td>
	</tr> -->

	<tr>
		<td width="25%" height="30">任务类名&nbsp;</td>
		<td height="30" >
		    <input name="type" type="radio" value="J" onclick="javascript:changeXDiv('J');" <c:if test="${!empty scheduler.jobClass}">checked</c:if>>Job类
			<!--
			<input name="type" type="radio" value="S" onclick="javascript:changeXDiv('S');" >Spring Bean
			<div id="jobDiv" <c:if test="${empty scheduler.jobClass}">style="display:none;"</c:if>>
			-->
			&nbsp;
			<select id="jobClass" name="jobClass" nullable="no" chname="任务类名">
              <option value="">----请选择----</option>
			<%
			  JobProperties.reload();
			  Properties props =  JobProperties.getProperties();
			  Enumeration<?> e = props.keys();
			  while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = props.getProperty(key);
			%>
				<option value="<%=key%>"><%=value%></option>
			<%}%>
			</select>
			<script type="text/javascript">
			    document.getElementById("jobClass").value="${scheduler.jobClass}";
			</script>
			</div>
			<!--
			<div id="springDiv" style="display:none;">
			  <table width="99%">
			  <tr>
				<td width="20%" height="30">Spring组件类名</td>
				<td><input type="text" id="springClass" name="springClass" size="50"
			   class="input span3 x-text" maxlength="255"
			   value="${scheduler.springClass}"></td>
			  </tr>
			  <tr>
				<td  width="20%" height="30">Spring组件BeanId</td>
				<td><input type="text" id="springBeanId" name="springBeanId" size="50"
			   class="input span3 x-text" maxlength="255"
			   value="${scheduler.springBeanId}"></td>
			  </tr>
			  <tr>
				<td  width="20%" height="30">方法名</td>
				<td><input type="text" id="methodName" name="methodName" size="50"
			   class="input span3 x-text" maxlength="255"
			   value="${scheduler.methodName}"></td>
			  </tr>
			  </table>
			  
			</div>
			-->
		</td>
	</tr>

   <tr>
		<td width="25%" height="60">时间表达式&nbsp;</td>
		<td height="50" >
		<input type="text" id="expression" name="expression" size="50"
			   class="input span3 x-text" maxlength="255"
			   value="${scheduler.expression}">&nbsp;
			<img src="<%=request.getContextPath()%>/images/expr.png" onclick="javascript:openExpr2();" style="cursor:pointer">
            &nbsp;<span style="cursor:pointer" onclick="javascript:openExpr2();">表达式</span>
			<br />&nbsp;(可不填,可以参考<a href="#" onclick="javascript:openQtz();">quartz</a>文件)
			<br>示例：每周一到周五凌晨5点执行一次（ 0 0 5 ? * MON-FRI  ）
			<br>  每天早上6点、中午1点和下午5点各执行一次（ 0 0 6,13,17 * * ?  ）
		    </td>
	</tr>

	<tr>
		<td width="25%" height="24">开始日期&nbsp;&nbsp;</td>
		<td height="24"><input id="startDate" name="startDate" size="18"
			type="text"  class="input span3 x-text" required="true"
			value="<fmt:formatDate value="${scheduler.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" />&nbsp;
		<img src="<%=request.getContextPath()%>/images/calendar.png"
			id="f_trigger_1" style="cursor: pointer; border: 1px solid red;" />
		</td>
	</tr>

	<tr>
		<td width="25%" height="24">结束日期&nbsp;&nbsp;</td>
		<td height="24"><input id="endDate" name="endDate" size="18"
			type="text"  class="input span3 x-text" required="true"
			value="<fmt:formatDate value="${scheduler.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" />&nbsp;
		<img src="<%=request.getContextPath()%>/images/calendar.png"
			id="f_trigger_2" style="cursor: pointer; border: 1px solid red;" />
		</td>
	</tr>

	<tr>
		<td width="25%" height="24">运行类型&nbsp;</td>
		<td height="24">
		  <select id="runType" name="runType">
			<option value="2">可重复多次</option>
			<option value="1">重复单实例</option>
			<option value="0">一天一次</option>
		  </select>
		  <script type="text/javascript">
			document.getElementById("runType").value="${scheduler.runType}";
		  </script>
		</td>
	</tr>

	<tr>
		<td width="25%" height="24">是否自动启动&nbsp;</td>
		<td height="24">
		<c:choose>
			<c:when test="${scheduler.autoStartup == 1}">
				<input type="radio" name="autoStartup" value="1" checked>是
				<input type="radio" name="autoStartup" value="0">否
			</c:when>
			<c:otherwise>
				<input type="radio" name="autoStartup" value="1">是
				<input type="radio" name="autoStartup" value="0" checked>否
			</c:otherwise>
		</c:choose>
		</td>
	</tr>

	<tr>
		<td width="25%" height="24">是否启用&nbsp;</td>
		<td height="24">
		<c:choose>
			<c:when test="${scheduler.locked == 0}">
				<input type="radio" name="locked" value="0" checked>是
				<input type="radio" name="locked" value="1">否
			</c:when>
			<c:otherwise>
				<input type="radio" name="locked" value="0">是
				<input type="radio" name="locked" value="1" checked>否
			</c:otherwise>
		</c:choose>
		</td>
	</tr>
 
    </table> 

	<div align="center">
	    <input name="btn_save" type="submit" value="保存" class="btn btn-primary">
	</div>

</html:form>

<script type="text/javascript">

    Calendar.setup({
			inputField     :    "startDate",     // id of the input field
			ifFormat       :    "%Y-%m-%d %H:%M:%S",      // format of the input field
			button         :    "f_trigger_1",  // trigger for the calendar (button ID)
			align          :    "Bl",           // alignment (defaults to "Bl")
			singleClick    :    true,
			showsTime      :    true
	});


    Calendar.setup({
			inputField     :    "endDate",     // id of the input field
			ifFormat       :    "%Y-%m-%d %H:%M:%S",      // format of the input field
			button         :    "f_trigger_2",  // trigger for the calendar (button ID)
			align          :    "Bl",           // alignment (defaults to "Bl")
			singleClick    :    true,
			showsTime      :    true
	});


</script>
<br/>
</body> 
</html>