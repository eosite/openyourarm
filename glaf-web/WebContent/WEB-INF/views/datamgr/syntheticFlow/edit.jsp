<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="com.alibaba.fastjson.*"%>
<%@ page import="com.glaf.datamgr.domain.*"%>
<%
	StringBuffer bufferx = new StringBuffer();
	StringBuffer bufferx2 = new StringBuffer();
	StringBuffer buffery = new StringBuffer();
	StringBuffer buffery2 = new StringBuffer();
	String currentType = (String)request.getAttribute("currentType");
	JSONArray array = (JSONArray) request.getAttribute("array");
	List rows = (List) request.getAttribute("rows");
	if (array != null && array.size() > 0) {
		boolean includePrev = false;
		boolean inculdeNext = false;
		String key = null;
		for (int i = 0; i < array.size(); i++) {
			JSONObject json = (JSONObject) array.getJSONObject(i);
			key = json.getString("key");
			includePrev = false;
			inculdeNext = false;
			if (rows != null && rows.size() > 0) {
				for (int j = 0; j < rows.size(); j++) {
					SyntheticFlow flow = (SyntheticFlow) rows.get(j);
					if (StringUtils.equals(key, flow.getPreviousStep()) && 
						StringUtils.equals(currentType, flow.getPreviousType())) {
						bufferx2.append("\n<option value=\"").append(json.getString("key")).append("\">")
								.append(json.getString("value")).append(" [").append(json.getString("key"))
								.append("]</option>");
						includePrev = true;
					}
					if (StringUtils.equals(key, flow.getNextStep()) && 
						StringUtils.equals(currentType, flow.getNextType())) {
						buffery2.append("\n<option value=\"").append(json.getString("key")).append("\">")
								.append(json.getString("value")).append(" [").append(json.getString("key"))
								.append("]</option>");
						inculdeNext = true;
					}
				}
			}
			if (!includePrev) {
				bufferx.append("\n<option value=\"").append(json.getString("key")).append("\">")
						.append(json.getString("value")).append(" [").append(json.getString("key"))
						.append("]</option>");
			}
			if (!inculdeNext) {
				buffery.append("\n<option value=\"").append(json.getString("key")).append("\">")
						.append(json.getString("value")).append(" [").append(json.getString("key"))
						.append("]</option>");
			}
		}
	}
%>
<!DOCTYPE html>
<html>
<title>执行处理器</title>
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
 

  function doSelection() {

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

	 
	if(confirm("确定选择这些处理器吗？")){
		  document.getElementById("previousObjectIds").value=result;
		  document.getElementById("nextObjectIds").value=result2;
		  var link = "<%=request.getContextPath()%>/mx/sys/syntheticFlow/save?previousType=${item.type}&nextType=${item.type}";
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
					       //window.parent.location.reload();
					   }
				   }
			 });
		}

  }

  function switchXY(){
	   var code = document.getElementById("code").value;
       location.href="<%=request.getContextPath()%>/mx/sys/syntheticFlow/edit?currentStep=${currentStep}&code="+code;
  }
</script>
</head>
<body>
	<center>
		<form id="iForm" name="iForm" class="x-form" method="post" 
		      action="<%=request.getContextPath()%>/mx/sys/syntheticFlow/edit?currentStep=${currentStep}">
			<input type="hidden" id="currentStep" name="currentStep" value="${currentStep}">
			<input type="hidden" id="previousObjectIds" name="previousObjectIds" >
			<input type="hidden" id="nextObjectIds" name="nextObjectIds">
			<div class="content-block" style="width: 100%;">
				<br>
				<div class="x_content_title">
					<img src="<%=request.getContextPath()%>/images/window.png"
						alt="执行处理器">&nbsp;执行处理器
				</div>
				<fieldset class="x-fieldset" style="width: 98%;">
				    <br>
					<select id="code" name="code" onchange="javascript:switchXY();">
						<option value="">----请选择----</option>
						<c:forEach items="${definitions}" var="item">
							<option value="${item.code}">${item.title}</option>
						</c:forEach>
					</select>
					<script type="text/javascript">
						document.getElementById("code").value="${code}";
					</script>
					<table class="table-border" align="center" cellpadding="4"
						cellspacing="1" width="90%">
						<tbody>
							<tr>
								<td class="beta" colspan="2">
									<div align="center">可选前置处理器</div>
								</td>
								<td class="beta"></td>
								<td class="beta" colspan="3">
									<div align="center">已选前置处理器</div>
								</td>
							</tr>
							<tr>
								<td class="beta" width="18">&nbsp;</td>
								<td class="table-content" height="26" valign="top" width="690">
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
								<td class="table-content" height="26" valign="top" width="359">
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
						cellspacing="1" width="90%">
						<tbody>
							<tr>
								<td class="beta" colspan="2">
									<div align="center">可选后置处理器</div>
								</td>
								<td class="beta"></td>
								<td class="beta" colspan="3">
									<div align="center">已选后置处理器</div>
								</td>
							</tr>
							<tr>
								<td class="beta" width="18">&nbsp;</td>
								<td class="table-content" height="26" valign="top" width="690">
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
								<td class="table-content" height="26" valign="top" width="359">
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
					<input value="确 定" class="btn btnGray" name="btn" type="button"
						onclick="javacsript:doSelection();"> 
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