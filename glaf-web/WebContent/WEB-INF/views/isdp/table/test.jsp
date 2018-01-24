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
<%@ include file="/WEB-INF/views/isdp/table/select_js.jsp"%>
<script type="text/javascript">
	var height=document.documentElement.clientHeight;
	$(function(){
		
		$("#databaseDropDownList").kendoDropDownList({
            dataTextField: "text",
            dataValueField: "code",
            optionLabel: "请选择项目标段",
            dataSource: {
            	transport:{
            		read:{
            			contentType: "application/json",
                        type: "POST",
                        url:"${pageContext.request.contextPath}/rs/isdp/global/databaseJson"
            		}
            	}
            },
            value:"${param.databaseCode}"
        });
		
		$("#tableId").on("click",function(e){
			var databaseDropDownList = $("#databaseDropDownList").data("kendoDropDownList");
			var databaseCode = databaseDropDownList.value();
			var tableObj = $("#tableObjElementId").val();
			selectTable(databaseCode,"tableId","tableName","tableObjElementId",tableObj);
		});
		
		$("#fieldId").on("click",function(e){
			var databaseDropDownList = $("#databaseDropDownList").data("kendoDropDownList");
			var databaseCode = databaseDropDownList.value();
			
			var tableObj = $("#tableObjElementId").val();
			var fieldObj = $("#fieldObjElementId").val();
			selectField(databaseCode,"fieldId","fieldName","fieldObjElementId",tableObj,fieldObj);
		});
		
		$("#modifyFieldBtn").on("click",function(e){
			var databaseDropDownList = $("#databaseDropDownList").data("kendoDropDownList");
			var databaseCode = databaseDropDownList.value();
			
			var fieldObj = $("#fieldObjElementId").val();
			modifyField(databaseCode,"fieldObjElementId",fieldObj)
		});
		
		$("#tableHander").on("click",function(e){
			modifyTableHander("tableHander","headerObjElementId",null,"fieldObjElementId");
		});
		
		$("#statusPic").on("click",function(e){
			
		});
	});
</script>
</head> 
<body>
	<div id="main_content" style="height:500px">
		隐藏表ID:<textarea id="tableObjElementId" name="tableObjElementId" rows="10" cols="50" ></textarea>
		隐藏字段ID：<textarea id="fieldObjElementId" name="fieldObjElementId" rows="10" cols="100" ></textarea><br/>
		隐藏表头数据：<textarea id="headerObjElementId" name="headerObjElementId" rows="10" cols="50" ></textarea><br/>
		<table width="30%">
			<tr>
				<td>数据源：</td>
				<td><input type="text" id="databaseDropDownList" name="databaseId" /></td>
			</tr>
			<tr>
				<td>数据表：</td>
				<td><input type="text" id="tableId" name="tableId" class="k-textbox" /></td>
			</tr>
			<tr>
				<td>数据表名：</td>
				<td><input type="text" id="tableName" name="tableName" class="k-textbox" /></td>
			</tr>
			<tr>
				<td>数据字段：</td>
				<td><input type="text" id="fieldId" name="fieldId" class="k-textbox" /></td>
			</tr>
			<tr>
				<td>数据字段名：</td>
				<td><input type="text" id="fieldName" name="fieldName" class="k-textbox" /></td>
			</tr>
			<tr>
				<td>编辑字段：</td>
				<td><button id="modifyFieldBtn" class="k-button">编辑字段</button></td>
			</tr>
			<tr>
				<td>表头设置：</td>
				<td><input type="text" id="tableHander" name="tableHander" class="k-textbox" /></td>
			</tr>
			<tr>
				<td>状态图片设置：</td>
				<td><input type="text" id="statusPic" name="statusPic" class="k-textbox" /></td>
			</tr>
		</table>
	</div>
</body>
</html>