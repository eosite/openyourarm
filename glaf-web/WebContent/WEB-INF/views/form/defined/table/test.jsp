<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/layer/skin/layer.ext.css" >
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/layer/extend/layer.ext.js"></script>
<%@ include file="/WEB-INF/views/form/defined/table/select_js.jsp"%>
<script type="text/javascript">
	var height=document.documentElement.clientHeight;
	$(function(){
		
		$("#selectBtn").on("click",function(e){
			//参数1：返回的结果文本ID
			//参数2：中文表名文本框ID
			//参数3：中文字段名文本框ID
			selectTableAndFields("results","tableName","fieldName");
		});
		
		$("#tableHander").on("click",function(e){
			//参数1：返回的表头中文文本框ID
			//参数2：返回的表头JSON字符串对象文本框ID
			//参数3：选表后返回的结果文本框ID
			modifyTableHander("tableHander","headerObjElementId","results");
		});
		
	});
</script>
</head> 
<body>
	<div id="main_content" style="height:500px">
		返回结果<textarea id="results" name="results" rows="10" cols="230" ></textarea>
		<br/>
		表头返回结果<textarea id="headerObjElementId" rows="5" cols="230"></textarea>
		<table width="30%">
			<tr>
				<td colspan="2"><button id="selectBtn">选择表</button></td>
			</tr>
			<tr>
				<td>数据表名：</td>
				<td><input type="text" id="tableName" name="tableName" class="k-textbox" /></td>
			</tr>
			<tr>
				<td>数据字段名：</td>
				<td><input type="text" id="fieldName" name="fieldName" class="k-textbox" /></td>
			</tr>
			<tr>
				<td>表头设置：</td>
				<td><input type="text" id="tableHander" name="tableHander" class="k-textbox" /></td>
			</tr>
		</table>
	</div>
</body>
</html>