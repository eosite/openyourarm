<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="com.alibaba.fastjson.*"%>
<%@ page import="com.glaf.core.identity.*"%>
<%@ page import="com.glaf.datamgr.domain.*"%>
<%
		StringBuffer bufferz = new StringBuffer();
		StringBuffer bufferz2 = new StringBuffer();
		String userId = request.getParameter("userId");
		String currentType = (String)request.getAttribute("currentType");
		JSONArray array = (JSONArray) request.getAttribute("array");
		if (array != null && array.size() > 0) {
			for (int i = 0; i < array.size(); i++) {
				JSONObject json = (JSONObject) array.getJSONObject(i);
				String key = json.getString("key");
			    if(json.containsKey("selected")){
					bufferz2.append("\n<option value=\"").append(key).append("\" title=\""+json.getString("value")+"\">")
						   .append(json.getString("value")).append(" [").append(key).append("]")
						   .append("</option>");		
				} else {
					bufferz.append("\n<option value=\"").append(key).append("\" title=\""+json.getString("value")+"\">")
						    .append(json.getString("value")).append(" [").append(key).append("]")
						    .append("</option>");
				}
			}
	}
%>
<!DOCTYPE html>
<html>
<title>数据权限设置</title>
<%@ include file="/WEB-INF/views/inc/mx_header.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>

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

  function addElement3() {
        var list = document.iForm.noselected3;
        for (i = 0; i < list.length; i++) {
            if (list.options[i].selected) {
                var value = list.options[i].value;
                var text = list.options[i].text;
                addToList3(value, text);
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

  function addToList3(value, text) {
        var list = document.iForm.selected3;
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
 
   function removeElement3() {
        var list = document.iForm.selected3;
		var slist = document.iForm.noselected3;
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
	var len3 = document.iForm.selected3.length;
	var result3 = "";
	for (var i=0;i<len3;i++) {
      result3 = result3 + document.iForm.selected3.options[i].value;
	  if(i < (len3 - 1)){
		  result3 = result3 + ",";
	   }
    }
	 
	if(confirm("确定按选中记录设置数据权限吗？")){
		  document.getElementById("objectIds").value=result3;
		  var link = "<%=request.getContextPath()%>/mx/sys/dataPermission/saveByUser?businessType=${businessType}";
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
	    var userId = document.getElementById("userId").value;
        document.iForm.submit();
    }
  
</script>
</head>
<body>
	<center>
		<form id="iForm" name="iForm" class="x-form" method="post" 
		      action="<%=request.getContextPath()%>/mx/sys/dataPermission/viewByUser?businessType=${businessType}">
			<input type="hidden" id="businessType" name="businessType" value="${businessType}">
			<input type="hidden" id="userIds" name="userIds" >
			<input type="hidden" id="roleIds" name="roleIds">
			<input type="hidden" id="objectIds" name="objectIds">
			<div class="content-block" style="width: 100%;">
				<br>
				<div class="x_content_title">
					<img src="<%=request.getContextPath()%>/images/window.png"
						alt="数据权限设置">&nbsp;数据权限设置
				</div>
				<fieldset class="x-fieldset" style="width: 98%;">
				    <br>
					<table class="table-border" align="center" cellpadding="4"
						cellspacing="1" width="90%">
						<tbody>
							<tr>
								<td class="beta" colspan="4" align="center">
									 <select id="userId" name="userId" onchange="javascript:switchXY();">
										<option value="">----请选择----</option>
										<c:forEach items="${users}" var="user">
										<option value="${user.actorId}">${user.name}[${user.actorId}]</option>
										</c:forEach>
									 </select>
									 <script type="text/javascript">
									     document.getElementById("userId").value="${userId}";
									 </script>
								</td> 
							</tr>	 
						</tbody>
					</table>
					 
					<br>
					<table class="table-border" align="center" cellpadding="4"
						cellspacing="1" width="90%">
						<tbody>
							<tr>
								<td class="beta" colspan="2">
									<div align="center">可选记录</div>
								</td>
								<td class="beta"></td>
								<td class="beta" colspan="3">
									<div align="center">已选记录</div>
								</td>
							</tr>
							<tr>
								<td class="beta" width="18">&nbsp;</td>
								<td class="table-content" height="26" valign="top" width="690">
									<div align="center">
										<select class="list" style="width: 380px; height: 250px;"
											multiple="multiple" size="12" name="noselected3"
											ondblclick="addElement3()"><%=bufferz.toString()%>
										</select>
									</div>
								</td>
								<td class="beta" width="84">
									<div align="center">
										<input name="add" value="添加 ->" onclick="addElement3()"
											class="btn" type="button"> <br> <br> <input
											name="remove" value="<- 删除" onclick="removeElement3()"
											class="btn" type="button">
									</div>
								</td>
								<td class="table-content" height="26" valign="top" width="359">
									<div align="center">
										<select class="list" style="width: 380px; height: 250px;"
											multiple="multiple" size="12" name="selected3"
											ondblclick="removeElement3()"><%=bufferz2.toString()%>
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