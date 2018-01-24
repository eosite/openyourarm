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
	jQuery(function(){
		
	});
	
	function validate(myform){
		var i;

		  for (i=0;i<myform.elements.length;i++){
		    /* 非自定义属性的元素不予理睬 */
		    if (myform.elements[i].chname+""=="undefined") continue;
		    /* 非空校验 */
		   if(jQuery(myform.elements[i]).val()=="-1" && jQuery(myform.elements[i]).attr("disabled")!="disabled"){
			  alert(myform.elements[i].chname+"不能为空值");
		      myform.elements[i].focus(); 
		      return false;
		   }
		    
		    if (myform.elements[i].nullable=="no" && isNull(myform.elements[i].value)){
		      alert(myform.elements[i].chname+"不能为空值");
		      myform.elements[i].focus();  
		      return false;
		    }
		  }
		  return true;
	}
	
	function changeType(type){
		if(type==1){
			document.getElementById("unitIndexId").disabled = true;
		}else{
			document.getElementById("unitIndexId").disabled = false;
		}
	}
	
	var unitObj = ${unitObj};
	function changeProjValue(projIndexId){
		var arr = unitObj[projIndexId+""];
		var e = document.getElementById("unitIndexId");
		for (var i=e.options.length; i>0; i--) {
			e.remove(i); 
		}
		
		for(var i=0;i<arr.length;i++){
			e.options.add(new Option(arr[i].indexName,arr[i].indexId));
		}
	}
</script>
</head>
<body style="margin:1px;">
<div style="width: 700px">
	<html:form action="${pageContext.request.contextPath }/mx/docManage/yz/baseinfo/saveBaseInfo" method="post" onsubmit="return validate(this);"  enctype="multipart/form-data"> 
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
		  <tr>
		    <td class="box-mm"><table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
		    	<tr>
		        <td class="input-box2" valign="top">类　　型*</td>
		        <td>
					<select id="type" name="type" style="width: 150px" chname="类型"  onchange="javascript:changeType(this.value);">
					  	<option value="-1">请选择</option>
					  	<option value="1" ${type=="1"?"selected":"" }>标段基本情况</option>
					  	<option value="2" ${type=="2"?"selected":"" } >单位工程基本情况</option>
				   </select>
				</td>
		      </tr>
		      <tr>
		        <td class="input-box">标段名称*</td>
		        <td>
					<select id="indexId" name="indexId" style="width: 150px" chname="项目名称"  onchange="javascript:changeProjValue(this.value);">
					  	<option value="-1">请选择</option>
					  	<c:forEach items="${muiProjLists}" var="muiProjList">
								<option value="${muiProjList.indexId}">${muiProjList.projName}</option>
						  </c:forEach>
				   </select>
				</td>
		      </tr>
		      <tr>
		        <td class="input-box">单位工程*</td>
		        <td>
					<select id="unitIndexId" name="unitIndexId" style="width: 150px" chname="单位工程"  ${type=="1"?"disabled":""}>
					  	<option value="-1">请选择</option>
				   </select>
				</td>
		      </tr>
		      <tr>
		        <td class="input-box2" valign="top">内　　容*</td>
		        <td><textarea name="fileContent2" cols="70" rows="15" class="input" datatype="string" nullable="no" chname="内容"></textarea></td>
		      </tr>
		      <tr>
		        <td class="input-box2" valign="top">附　　件</td>
		        <td><input name="fileContent" type="file" size="75" class="input"  datatype="file" nullable="yes"  chname="附件" ></td>
		      </tr>
		      <tr>
		        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
		              <input name="btn_save" type="submit" value="保存" class="button">
		        </td>
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
	</html:form>
</div>
</body>
</html>