<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="com.alibaba.fastjson.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.deployment.domain.*"%>
<%
	StringBuffer bufferx = new StringBuffer();
	StringBuffer bufferx2 = new StringBuffer();
	StringBuffer buffery = new StringBuffer();
	StringBuffer buffery2 = new StringBuffer();
	List templates = (List) request.getAttribute("templates");
	List servers = (List) request.getAttribute("servers");
	if (templates != null && templates.size() > 0) {
		Iterator iterator =  templates.iterator();
		while(iterator.hasNext()){
			ExecutionTemplate model = (ExecutionTemplate)iterator.next();
			bufferx.append("\n<option value=\"").append(model.getId()).append("\">")
				   .append(model.getTitle()).append(" [").append(model.getSortNo())
				   .append("]</option>");
		}
	}

	if (servers != null && servers.size() > 0) {
		Iterator iterator =  servers.iterator();
		while(iterator.hasNext()){
			ServerEntity model = (ServerEntity)iterator.next();
			buffery.append("\n<option value=\"").append(model.getId()).append("\">")
				   .append(model.getTitle()).append(" [").append(model.getHost())
				   .append("]</option>");
		}
	}

	String jobNo = RequestUtils.getActorId(request) + "_" + DateUtils.getNowYearMonthDayHHmmss();
%>
<!DOCTYPE html>
<html>
<title>执行命令</title>
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>

<script language="javascript">

var contextPath="<%=request.getContextPath()%>";

  function addElement() {
        var list = document.iForm.noselected1;
        for (i = 0; i < list.length; i++) {
            if (list.options[i].selected) {
                var value = list.options[i].value;
                var text = list.options[i].text;
                addToList(value, text);
				list.remove(i);
				i=i-1;
            }
        }
    }

  function addElement2() {
        var list = document.iForm.noselected2;
        for (i = 0; i < list.length; i++) {
            if (list.options[i].selected) {
                var value = list.options[i].value;
                var text = list.options[i].text;
                addToList2(value, text);
				list.remove(i);
				i=i-1;
            }
        }
    }

  function addToList(value, text) {
        var list = document.iForm.selected1;
        if (list.length > 0) {
            for (k = 0; k < list.length; k++) {
                if (list.options[k].value == value) {
                    return;
                }
            }
        }

        var len = list.options.length;
        list.length = len + 1;
        list.options[len].value = value;
        list.options[len].text = text;
    }

  function addToList2(value, text) {
        var list = document.iForm.selected2;
        if (list.length > 0) {
            for (k = 0; k < list.length; k++) {
                if (list.options[k].value == value) {
                    return;
                }
            }
        }

        var len = list.options.length;
        list.length = len + 1;
        list.options[len].value = value;
        list.options[len].text = text;
    }


  function removeElement() {
        var list = document.iForm.selected1;
		var slist = document.iForm.noselected1;
        if (list.length == 0 || list.selectedIndex < 0 || list.selectedIndex >= list.options.length)
            return;

        for (i = 0; i < list.length; i++) {
            if (list.options[i].selected) {
			    var value = list.options[i].value;
                var text = list.options[i].text;
                list.options[i] = null;
                i--;
				var len = slist.options.length;
				slist.length = len+1;
                slist.options[len].value = value;
                slist.options[len].text = text;				
            }
        }
    }

  function removeElement2() {
        var list = document.iForm.selected2;
		var slist = document.iForm.noselected2;
        if (list.length == 0 || list.selectedIndex < 0 || list.selectedIndex >= list.options.length)
            return;

        for (i = 0; i < list.length; i++) {
            if (list.options[i].selected) {
			    var value = list.options[i].value;
                var text = list.options[i].text;
                list.options[i] = null;
                i--;
				var len = slist.options.length;
				slist.length = len+1;
                slist.options[len].value = value;
                slist.options[len].text = text;				
            }
        }
    }
 

  function createExecTask(runIt) {

    var len= document.iForm.selected1.length;
	var result = "";
	for (var i=0;i<len;i++) {
      result = result + document.iForm.selected1.options[i].value;
	  if(i < (len - 1)){
		  result = result + ",";
	   }
    }

	var len2= document.iForm.selected2.length;
	var result2 = "";
	for (var i=0;i<len2;i++) {
      result2 = result2 + document.iForm.selected2.options[i].value;
	  if(i < (len2 - 1)){
		  result2 = result2 + ",";
	   }
    }

	 
	if(confirm("确定在选中的服务器上执行相关操作吗？")){
		  document.getElementById("templateIds").value=result;
		  document.getElementById("serverIds").value=result2;
		  var category = document.getElementById("category").value;
		  var link = "<%=request.getContextPath()%>/mx/deployment/executionTemplate/createExecTask?jobNo=<%=jobNo%>&runIt="+runIt;
	      var params = jQuery("#iForm").formSerialize();
		  jQuery.ajax({
				   type: "POST",
				   url: link,
				   dataType: 'json',
				   data: params,
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
					   if(data.statusCode == 200){
					       window.parent.location.href="<%=request.getContextPath()%>/mx/deployment/executionEntity?category="+category+"&jobNo=<%=jobNo%>";
					   }
				   }
			 });
		}
  }

  function switchXY(){
	 var category = document.getElementById("category").value;
     location.href="<%=request.getContextPath()%>/mx/deployment/executionTemplate/chooseServers?category="+category;
  }

</script>
</head>
<body>
	<center>
		<form id="iForm" name="iForm" class="x-form" method="post" >
			<input type="hidden" id="templateIds" name="templateIds" >
			<input type="hidden" id="serverIds" name="serverIds">
			<div class="content-block" style="width: 100%;">
				<br>
				<div class="x_content_title">
					<img src="<%=request.getContextPath()%>/images/window.png"
						alt="创建执行任务">&nbsp;创建执行任务
				</div>
				<fieldset class="x-fieldset" style="width: 98%;">
				    <!-- <br>
					选择类别&nbsp;
					<select id="category" name="category" onchange="javascript:switchXY();">
						<option value="">----请选择----</option>
						<c:forEach items="${dicts}" var="item">
							<option value="${item.code}">${item.name}</option>
						</c:forEach>
					</select>
					<script type="text/javascript">
						document.getElementById("category").value="${category}";
					</script> -->
					<table class="table-border" align="center" cellpadding="4"
						cellspacing="1" width="98%">
						<tbody>
							<tr>
								<td class="beta" colspan="2">
									<div align="center">可选命令</div>
								</td>
								<td class="beta"></td>
								<td class="beta" colspan="3">
									<div align="center">已选命令</div>
								</td>
							</tr>
							<tr>
								<td class="beta" width="18">&nbsp;</td>
								<td class="table-content" height="26" valign="top" width="350">
									<div align="center">
										<select class="list" style="width: 380px; height: 250px;"
											multiple="multiple" size="12" name="noselected1"
											ondblclick="addElement()"><%=bufferx.toString()%>
										</select>
									</div>
								</td>
								<td class="beta" width="84">
									<div align="center">
										<input name="add" value="添加 ->" onclick="addElement()"
											class="btn" type="button"> <br> <br> <input
											name="remove" value="<- 删除" onclick="removeElement()"
											class="btn" type="button">
									</div>
								</td>
								<td class="table-content" height="26" valign="top" width="350">
									<div align="center">
										<select class="list" style="width: 380px; height: 250px;"
											multiple="multiple" size="12" name="selected1"
											ondblclick="removeElement()"><%=bufferx2.toString()%>
										</select>
									</div>
								</td>
								<td class="beta" width="23">&nbsp;</td>
							</tr>
						</tbody>
					</table>
					<br>
					<table class="table-border" align="center" cellpadding="4"
						cellspacing="1" width="98%">
						<tbody>
							<tr>
								<td class="beta" colspan="2">
									<div align="center">可选服务器</div>
								</td>
								<td class="beta"></td>
								<td class="beta" colspan="3">
									<div align="center">已选服务器</div>
								</td>
							</tr>
							<tr>
								<td class="beta" width="18">&nbsp;</td>
								<td class="table-content" height="26" valign="top" width="350">
									<div align="center">
										<select class="list" style="width: 380px; height: 250px;"
											multiple="multiple" size="12" name="noselected2"
											ondblclick="addElement2()"><%=buffery.toString()%>
										</select>
									</div>
								</td>
								<td class="beta" width="84">
									<div align="center">
										<input name="add" value="添加 ->" onclick="addElement2()"
											class="btn" type="button"> <br> <br> <input
											name="remove" value="<- 删除" onclick="removeElement2()"
											class="btn" type="button">
									</div>
								</td>
								<td class="table-content" height="26" valign="top" width="350">
									<div align="center">
										<select class="list" style="width: 380px; height: 250px;"
											multiple="multiple" size="12" name="selected2"
											ondblclick="removeElement2()"><%=buffery2.toString()%>
										</select>
									</div>
								</td>
								<td class="beta" width="23">&nbsp;</td>
							</tr>
						</tbody>
					</table>
				</fieldset>

				<div align="center">
				    <br />
					<input value="创建执行任务" class="btn btnGray" name="btn" type="button"
						   onclick="javacsript:createExecTask('false');"> 
					&nbsp;&nbsp;
					<input value="创建并执行任务" class="btn btnGray" name="btn" type="button"
						   onclick="javacsript:createExecTask('true');"> 
					<br />
					<br />
					<br />
					<br />
					<br />
					<br />
				</div>
			</div>
		</form>
	</center>
 </body>
</html>