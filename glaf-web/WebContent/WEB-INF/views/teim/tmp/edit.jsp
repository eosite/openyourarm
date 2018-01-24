<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑服务模板信息</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<style scoped>

   .k-textbox {
        width: 18.8em;
    }

    .main-section {
        width: 800px;
        padding: 0;
     }

    label {
        display: inline-block;
        width: 100px;
        text-align: right;
        padding-right: 10px;
    }

    .required {
        font-weight: bold;
    }

    .accept, .status {
        padding-left: 90px;
    }
    .confirm {
        text-align: right;
    }

    .valid {
        color: green;
    }

    .invalid {
        color: red;
    }
    span.k-tooltip {
        margin-left: 6px;
    }
	
	.datagrid-header td,th {
	border-right: 1px dotted #ccc;
	font-size: 12px;
	font-weight: normal;
	background: #fafafa url('${contextPath}/images/datagrid_header_bg.gif')
		repeat-x left bottom;
	border-bottom: 1px dotted #ccc;
	border-top: 1px dotted #ccc;
	height: 28px;
	}
	.datagrid-header td:first-child,th:first-child {
		border-left: 1px dotted #ccc;
	}
.datagrid-tbody td {
	border-right: 1px solid #ccc;
	font-size: 12px;
	font-weight: normal;
	border-bottom: 1px solid #ccc;
	border-top: 1px solid #fff;
	height: 28px;
}
.datagrid-tbody td:first-child {
	border-left: 1px solid #ccc;
}
.datagrid-tbody tr {
	text-align: center;
}
.datagrid-tbody tr td input,.datagrid-tbody tr th input{
	width:100%;height:100%;border:none;
}
.datagrid-title td {
	font-size: 12px;
	font-weight: bolder;
	background: #E0ECFF repeat-x left bottom;
	height: 28px;
	vertical-align: middle;
}

.titleTd {
	background: #E0ECFF repeat-x left bottom;
}

td,th {
	display: table-cell;
	vertical-align: inherit;
}
</style>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content ">
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="tmpId" name="tmpId" value="${eimServerTmp.tmpId}"/>
<input type="hidden" id="categoryId" name="categoryId" value="${param.categoryId}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="150" align="left"><label for="name" class="required">模板名称&nbsp;</label></td>
    <td align="left">
	
        <input id="name" name="name" type="text" class="k-textbox"  
	   data-bind="value: name"
	   value="${eimServerTmp.name}" validationMessage="请输入模板名称"/>
	<span class="k-invalid-msg" data-for="name"></span>
    </td>
  </tr>
  <tr>
    <td width="150" align="left"><label for="path_" class="required">服务路径&nbsp;</label></td>
    <td align="left">
        <input id="path_" name="path_" type="text" class="k-textbox"  
	   data-bind="value: path_"
	   value="${eimServerTmp.path_}" validationMessage="请输入服务路径"/>
	<span class="k-invalid-msg" data-for="path_"></span>
    </td>
  </tr>
  <tr>
    <td width="150" align="left"><label for="reqUrlParam" class="required">URL参数&nbsp;</label></td>
    <td align="left">
     <input type="hidden" id="reqUrlParam" name="reqUrlParam"  class="k-textbox"  
	   data-bind="value: reqUrlParam" value="${eimServerTmp.reqUrlParam}"
	   validationMessage="请输入请求参数"/>
	
    </td>
  </tr>
  <tr>
    <td width="150" align="left"><label for="reqType" class="required">请求方式&nbsp;</label></td>
    <td align="left">
			 <input type="radio"  name="reqType" value="POST" ${( eimServerTmp.reqType ==''||eimServerTmp.reqType=='POST')?"checked":""}/> POST
			 <span></span>
			 <input type="radio"  name="reqType"  value="GET" ${eimServerTmp.reqType=='GET'?"checked":""}/> GET
			 <span></span>
			 <input type="radio"  name="reqType"  value="DELETE" ${eimServerTmp.reqType=='DELETE'?"checked":""}/> DELETE
			 <span></span>
			  <input type="radio"  name="reqType"  value="PUT" ${eimServerTmp.reqType=='PUT'?"checked":""}/> PUT
			 <span></span>
    </td>
  </tr>
  <tr>
    <td width="150" align="left"><label for="reqHeader" class="required">请求头模板&nbsp;</label></td>
    <td align="left">
       <input type="hidden" id="reqHeader" name="reqHeader" type="text" class="k-textbox"  
	   data-bind="value: reqHeader" value="${eimServerTmp.reqHeader}"
	   validationMessage="请输入请求头模板"/>
    </td>
  </tr>
  <tr>
    <td width="150" align="left"><label for="reqContentType" class="required">请求媒体格式&nbsp;</label></td>
    <td align="left">
        <input id="reqContentType" name="reqContentType" type="text" class="k-textbox"  
	   data-bind="value: reqContentType"
	   value="${eimServerTmp.reqContentType==null?"application/json":eimServerTmp.reqContentType}" validationMessage="请输入请求媒体格式"/>
	<span class="k-invalid-msg" data-for="reqContentType"></span>
    </td>
  </tr>
  <tr>
    <td width="150" align="left"><label for="resContentType" class="required">响应媒体格式&nbsp;</label></td>
    <td align="left">
        <input id="resContentType" name="resContentType" type="text" class="k-textbox"  
	   data-bind="value: resContentType"
	   value="${eimServerTmp.resContentType ==null?"application/json":eimServerTmp.resContentType}" validationMessage="请输入响应媒体格式"/>
	<span class="k-invalid-msg" data-for="resContentType"></span>
    </td>
  </tr>
  <tr>
    <td width="150" align="left"><label for="reqBody" class="required">请求体模板&nbsp;</label></td>
    <td align="left">
      <input id="reqBody" name="reqBody" type="hidden" class="k-textbox"  
	   data-bind="value: reqBody" value="${eimServerTmp.reqBody}"/>
    </td>
  </tr>
  <tr>
    <td width="150" align="left"><label for="reqBodyCustom" class="required">自定义请求体&nbsp;</label></td>
    <td align="left">
      <textarea id="reqBodyCustom" name="reqBodyCustom" type="text"
	   rows="6" cols="100"
	   validationMessage="自定义请求体">${eimServerTmp.reqBodyCustom}</textarea>
	  <span class="k-invalid-msg" data-for="reqBodyCustom"></span>
    </td>
  </tr>
  <tr>
    <td width="150" align="left"><label for="response_" class="required">响应体模板&nbsp;</label></td>
    <td align="left">
      <textarea id="response_" name="response_" type="text" class="k-textbox"  
	   rows="6"
	   validationMessage="请输入响应体模板">${eimServerTmp.response_}</textarea>
	  <span class="k-invalid-msg" data-for="response_"></span>
    </td>
  </tr>
  <tr>
    <td width="150" align="left"><label for="pagingContent" class="required">分页定义&nbsp;</label></td>
    <td align="left">
      <input id="pagingContent" name="pagingContent" type="hidden" class="k-textbox"  
	   data-bind="value: pagingContent" value="${eimServerTmp.pagingContent}"/>
	  <table style="width: 500px; margin: 0px; padding: 0px; border-spacing: 0px;">
		 <tbody class="datagrid-tbody">
		  <tr>
		   <th>当前页码参数</th>
		   <th><input type="text" class="currentPage"/></th>
		  </tr>
		   <tr>
		   <th>总记录数字段</th>
		   <th><input type="text" class="totalCount"/></th>
		  </tr>
		   <tr>
		   <th>单页条数字段</th>
		   <th><input type="text" class="pageSize"/></th>
		  </tr>
		 </tbody>
	</table>
	
    </td>
  </tr>
  <tr>
    <td width="150" align="left"><label for="recursiveContent" class="required">递归定义&nbsp;</label></td>
    <td align="left">
      <input id="recursiveContent" name="recursiveContent" type="hidden" class="k-textbox"  
	   data-bind="value: recursiveContent" value="${eimServerTmp.recursiveContent}"/>
    </td>
  </tr>
  <tr>
    <td width="150" align="left"> <label for="deleteFlag" class="required">有效性&nbsp;</label></td>
    <td align="left"> 
			 <input type="radio"  name="deleteFlag" value="0" ${eimBaseInfo.deleteFlag!=1?"checked":""}/> 有效
			 <span></span>
			 <input type="radio"  name="deleteFlag"  value="1" ${eimBaseInfo.deleteFlag==1?"checked":""}/> 无效
			 <span></span>
    </td>
  </tr>
	   
    <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
        <div>
          <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>
	    </div>
	</td>
      </tr>
</table>   
</form>
</div>     
<script type="text/javascript">
                
  jQuery(function() {
    var viewModel = kendo.observable({
		"name": "${eimServerTmp.name}",
        "categoryId": "${eimServerTmp.categoryId}",
        "path_": "${eimServerTmp.path_}",
        "reqUrlParam": '${eimServerTmp.reqUrlParam}',
        "reqType": "${eimServerTmp.reqType}",
        "reqHeader": '${eimServerTmp.reqHeader}',
        "reqContentType": "${(eimServerTmp==null||eimServerTmp.reqContentType==null)?"application/json":eimServerTmp.reqContentType}",
        "resContentType": "${(eimServerTmp==null||eimServerTmp.resContentType==null)?"text/json;charset=utf-8":eimServerTmp.resContentType}",
        "reqBody": '${eimServerTmp.reqBody}',
		"pagingContent": '${eimServerTmp.pagingContent}',
		"recursiveContent": '${eimServerTmp.recursiveContent}',
        "createBy": "${eimServerTmp.createBy}",
        "createTime": "<fmt:formatDate value='${eimServerTmp.createTime}' pattern='MM/dd/yyyy'/>",
        "updateBy": "${eimServerTmp.updateBy}",
        "updateTime": "<fmt:formatDate value='${eimServerTmp.updateTime}' pattern='MM/dd/yyyy'/>",
        "deleteFlag": "${eimServerTmp.deleteFlag}",
        "tmpId": "${eimServerTmp.tmpId}"
    });

    kendo.bind(jQuery("#iForm"), viewModel);

   });

    jQuery(document).ready(function() {
          jQuery("#iconButton").kendoButton({
                spriteCssClass: "k-icon"
          });
		  //初始化grid
		  initGrid($("#reqUrlParam"),"reqUrlParamGrid",'${eimServerTmp.reqUrlParam}');
		  initGrid($("#reqHeader"),"reqHeaderGrid",'${eimServerTmp.reqHeader}');
		  initGrid($("#reqBody"),"reqBodyGrid",'${eimServerTmp.reqBody}');
		  initRecurGrid($("#recursiveContent"),"recursiveContentGrid",'${eimServerTmp.recursiveContent}');
		  initPagingContent('${eimServerTmp.pagingContent}');
    });
	/**
	*初始化grid
	*/
    function initGrid(preObj,id,content){
		var tableInst=$($("#tableTmp").html());
		tableInst.attr("id",id);
		//产生行记录
		if(content!='')
		{
			tableInst.find("tbody").empty();
			var data=JSON.parse(content);
			var name,defaultval,type;
			var row;
			$.each(data,function(code,item){
				name=item.name;
				defaultval=item.defaultval;
				type=item.type;
				row=$($("#rowTmp").html());
				row.find(".code").val(code);
				row.find(".name").val(name);
				row.find(".defaultval").val(defaultval);
				row.find(".type").val(type);
				tableInst.append(row);
			});
		}
		bindBt(tableInst);
		tableInst.find(".topAdd").click(function(){
			var row=$($("#rowTmp").html());
			tableInst.find("tbody").append(row);
		});
		preObj.after(tableInst);
	}
	function initRecurGrid(preObj,id,content){
		var tableInst=$($("#recurTableTmp").html());
		tableInst.attr("id",id);
		//产生行记录
		if(content!='')
		{
			tableInst.find("tbody").empty();
			var data=JSON.parse(content);
			var row;
			$.each(data,function(code,item){
				row=$($("#recurRowTmp").html());
				row.find(".paramName").val(code);
				row.find(".mappingField").val(item);
				tableInst.append(row);
			});
		}
		bindRecurBt(tableInst);
		tableInst.find(".recurTopAdd").click(function(){
			var row=$($("#recurRowTmp").html());
			tableInst.find("tbody").append(row);
		});
		preObj.after(tableInst);
	}
	function initPagingContent(content){
		if(content&&content!=""){
			var data=JSON.parse(content);
			if(data){
			$(".currentPage").val(data.currentPage);
			if(data.totalCount&&data.totalCount instanceof Object){
			    $(".totalCount").val(JSON.stringify(data.totalCount));
			}else{
				$(".totalCount").val(data.totalCount);
			}
			$(".pageSize").val(data.pageSize);
			}
		}
	}
	function bindBt(tableObj){
		tableObj.on("click",".add",function(){
			var row=$($("#rowTmp").html());
			$(this).closest("tr").after(row);
		});
		tableObj.on("click",".delete",function(){
			$(this).closest("tr").remove();
		});
	}
	function bindRecurBt(tableObj){
		tableObj.on("click",".addRecurMapping",function(){
			var row=$($("#recurRowTmp").html());
			$(this).closest("tr").after(row);
		});
		tableObj.on("click",".deleteRecurMapping",function(){
			$(this).closest("tr").remove();
		});
	}
    jQuery(function () {
        var container = jQuery("#iForm");
        kendo.init(container);
        container.kendoValidator({
                rules: {
                      greaterdate: function (input) {
                            if (input.is("[data-greaterdate-msg]") && input.val() != "") {                                    
                               var date = kendo.parseDate(input.val()),
                               otherDate = kendo.parseDate(jQuery("[name='" + input.data("greaterdateField") + "']").val());
                               return otherDate == null || otherDate.getTime() < date.getTime();
                             }

                             return true;
                          }
                      }
        });
    });

   function save(){
       var form = document.getElementById("iForm");
       var validator = jQuery("#iForm").data("kendoValidator");
       if (validator.validate()) {
	   //form.method="post";
	   //form.action = "<%=request.getContextPath()%>/mx/teim/eimServerTmp/saveEimServerTmp";
	   //form.submit();
	   var link = "<%=request.getContextPath()%>/mx/teim/tmp/saveEimServerTmp";
	   //重组Gird内容到input
	   var urlParamJson={};
	   //获取url参数
	   var tableInst=$("#reqUrlParamGrid");
	   setReqInputVal($("#reqUrlParam"),tableInst);
	   //设置请求头参数
	   tableInst=$("#reqHeaderGrid");
	   setReqInputVal($("#reqHeader"),tableInst);
	   //设置请求体参数
	   tableInst=$("#reqBodyGrid");
	   setReqInputVal($("#reqBody"),tableInst);
	   //设置分页定义
	   setPagingContentVal();
	   tableInst=$("#recursiveContentGrid");
	   setRecurInputVal($("#recursiveContent"),tableInst);
	   var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: link,
				   dataType:  'json',
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
					   //刷新grid
					  parent.refreshGrid();
					  parent.closeDialog("addDialog");
				   }
			 });
       }
   }
   function setReqInputVal(inputObj,tableInst){
	   var returnJson={};
	   var rows=tableInst.find("tbody").find("tr");
	   var code,name,defaultval,type;
	   var itemJson;
	   $.each(rows,function(i,row){
		   code=$(row).find(".code").val();
		   if(code!=""){
		   name=$(row).find(".name").val();
		   defaultval=$(row).find(".defaultval").val();
		   type=$(row).find(".type").val();
		   itemJson={};
		   itemJson.name=name;
		   itemJson.type=type;
		   if(defaultval!=""){
		   if(type=="int")
		      itemJson.defaultval=parseInt(defaultval);
	       else
			  itemJson.defaultval=defaultval;
		   }
		   returnJson[code]=itemJson;
		   }
	   });
	   inputObj.val(JSON.stringify(returnJson));
   }
   function setPagingContentVal(){
	        var currentPage=$(".currentPage").val();
			var totalCount=$(".totalCount").val();
			var pageSize=$(".pageSize").val();
			var dataJson={};
			if(currentPage!=null){
				dataJson.currentPage=currentPage;
				if(totalCount.indexOf("{")>-1){
				    dataJson.totalCount=JSON.parse(totalCount);
				}else{
					dataJson.totalCount=totalCount;
				}
				dataJson.pageSize=pageSize;
				$("#pagingContent").val(JSON.stringify(dataJson));
			}
   }
   function setRecurInputVal(inputObj,tableInst){
	   var returnJson={};
	   var rows=tableInst.find("tbody").find("tr");
	   var paramName,mappingField;
	   var itemJson;
	   $.each(rows,function(i,row){
		   paramName=$(row).find(".paramName").val();
		   mappingField=$(row).find(".mappingField").val();
		   if(paramName!=""&&mappingField!=""){
		      returnJson[paramName]=mappingField;
		   }
	   });
	   inputObj.val(JSON.stringify(returnJson));
   }
 </script>
  <script type="text/html" id="tableTmp">
     <table style="width: 700px; margin: 0px; padding: 0px; border-spacing: 0px;">
	     <thead class="datagrid-header">
		 <tr>
		   <th style="width:150px;">代码</th>
		   <th style="width:300px;">描述</th>
		   <th style="width:100px;">数据类型</th>
		   <th style="width:200px;">默认值</th>
		   <th style="width:150px;"><button type="button" class="topAdd">新增</button></th>
		 </tr>
		 </thead>
		 <tbody class="datagrid-tbody">
		  <tr>
		   <td><input type="text" class="code"/></td>
		   <td><input type="text" class="name"/></td>
		   <td>
		      <select class="type">
			   <option value="string">字符串</option>
			   <option value="int">数值</option>
			 </select>
		   </td>
		   <td><input type="text" class="defaultval"/></td>
		   <td><button type="button" class="add">新增</button>&nbsp;<button type="button" class="delete">删除</button></td>
		  </tr>
		 </tbody>
	</table>
   </script>
   <script type="text/html" id="recurTableTmp">
     <table style="width: 600px; margin: 0px; padding: 0px; border-spacing: 0px;">
	     <thead class="datagrid-header">
		 <tr>
		   <th style="width:150px;">参数名</th>
		   <th style="width:300px;">映射结果字段</th>
		   <th style="width:150px;"><button type="button" class="recurTopAdd">新增</button></th>
		 </tr>
		 </thead>
		 <tbody class="datagrid-tbody">
		  <tr>
		   <td><input type="text" class="paramName"/></td>
		   <td><input type="text" class="mappingField"/></td>
		   <td><button type="button" class="addRecurMapping">新增</button>&nbsp;<button type="button" class="deleteRecurMapping">删除</button></td>
		  </tr>
		 </tbody>
	</table>
   </script>
    <script type="text/html" id="rowTmp">
      <tr>
		   <td><input type="text" class="code"/></td>
		   <td><input type="text" class="name"/></td>
		   <td>
			 <select class="type">
			   <option value="string">字符串</option>
			   <option value="int">数值</option>
			 </select>
		   </td>
		   <td><input type="text" class="defaultval"/></td>
		   <td><button type="button" class="add">新增</button>&nbsp;<button type="button" class="delete">删除</button></td>
	  </tr>
   </script>
    <script type="text/html" id="recurRowTmp">
      <tr>
		   <td><input type="text" class="paramName"/></td>
		   <td><input type="text" class="mappingField"/></td>
		   <td><button type="button" class="addRecurMapping">新增</button>&nbsp;<button type="button" class="deleteRecurMapping">删除</button></td>
	  </tr>
   </script>
</body>
</html>