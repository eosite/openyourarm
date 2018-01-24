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
<title>编辑合成表信息</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
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
</style>
<script type="text/javascript">
                
  jQuery(function() {
    var viewModel = kendo.observable({
        "syntheticId": "${syntheticId}",
        "name": "${treeTableSyntheticRule.name}",
        "title": "${treeTableSyntheticRule.title}",
        "mappingToSourceIdColumn": "${treeTableSyntheticRule.mappingToSourceIdColumn}",
        "mappingToTargetColumn": "${treeTableSyntheticRule.mappingToTargetColumn}",
	    "mappingToTargetAlias": "${treeTableSyntheticRule.mappingToTargetAlias}",
		"columnTitle": "${treeTableSyntheticRule.columnTitle}",
        "datasetId": "${treeTableSyntheticRule.datasetId}",
        "sqlDefId": "${treeTableSyntheticRule.sqlDefId}",
        "id": "${treeTableSyntheticRule.id}"
    });

    kendo.bind(jQuery("#iForm"), viewModel);

   });

    jQuery(document).ready(function() {
          jQuery("#iconButton").kendoButton({
                spriteCssClass: "k-icon"
          });           
    });

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
	      var link = "<%=request.getContextPath()%>/mx/sys/treeTableSyntheticRule/saveRule";
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
					   if(data.statusCode == 200){
					       window.parent.location.reload();
					   }
				   }
			 });
       }
   }

   function saveAs(){
	   document.getElementById("id").value="";
       var form = document.getElementById("iForm");
       var validator = jQuery("#iForm").data("kendoValidator");
       if (validator.validate()) {
	      var link = "<%=request.getContextPath()%>/mx/sys/treeTableSyntheticRule/saveRule";
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
					   if(data.statusCode == 200){
					       //window.parent.location.reload();
					   }
				   }
			 });
       }
   }

  function openDataset(){
        var selected = jQuery("#datasetId").val();
        var link = '<%=request.getContextPath()%>/mx/dataset/showTree?elementId=datasetId&elementName=datasetName&nodeCode=report_category&selected='+selected;
		var x=100;
		var y=50;
		if(is_ie) {
			x=document.body.scrollLeft+event.clientX-event.offsetX-200;
			y=document.body.scrollTop+event.clientY-event.offsetY-200;
		}
		openWindow(link,self,x, y, 595, 480);
	}

	function switchDataset(){
         var datasetId = jQuery("#datasetId").val();
		 //alert("datasetId:"+datasetId);
		 jQuery.getJSON("<%=request.getContextPath()%>/rs/dataset/columns?datasetId="+datasetId, function(data){
		  var mappingToSourceIdColumn = document.getElementById("mappingToSourceIdColumn");
		  var mappingToTargetColumn = document.getElementById("mappingToTargetColumn");
		  var columnName = document.getElementById("columnName");
		  mappingToSourceIdColumn.options.length=0;
		  mappingToTargetColumn.options.length=0;
		  //columnName.options.length=0;
		  jQuery.each(data, function(i, item){
			  //alert("column:"+item.columnName);
			  mappingToSourceIdColumn.options.add(new Option(item.title+'['+item.column_lowercase+']', item.column_lowercase));
			  mappingToTargetColumn.options.add(new Option(item.title+'['+item.column_lowercase+']', item.column_lowercase));
			  columnName.options.add(new Option(item.title+'['+item.column_lowercase+']', item.column_lowercase));
		  });
		});
	}

	function showMyDataset() {
		var link = "<%=request.getContextPath()%>/mx/dataset/sqlbuilder?id=${treeTableSyntheticRule.datasetId}";
		window.open(link);
	}

 </script>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content ">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑合成表规则">&nbsp;
    编辑合成表规则</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="syntheticId" name="syntheticId" value="${syntheticId}"/>
<input type="hidden" id="id" name="id" value="${treeTableSyntheticRule.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="title" class="required">标题&nbsp;</label>
        <input id="title" name="title" type="text" class="k-textbox"  
	           data-bind="value: title" style="width:380px"
	           value="${treeTableSyntheticRule.title}" validationMessage="请输入标题"/>
	    <span class="k-invalid-msg" data-for="title"></span>
    </td>
  </tr>

  <!-- <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="name" class="required">名称&nbsp;</label>
        <input id="name" name="name" type="text" class="k-textbox"  
	           data-bind="value: name" style="width:380px"
	           value="${treeTableSyntheticRule.name}" validationMessage="请输入名称"/>
	    <span class="k-invalid-msg" data-for="name"></span>
    </td>
  </tr> -->

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	     <label for="selectedDs" class="required">数据集&nbsp;</label>
		 <input type="hidden" id="datasetId" name="datasetId" value="${treeTableSyntheticRule.datasetId}">
         <input id="datasetName" name="datasetName" rows="12" cols="68" class="k-textbox"
				style="width:380px;height:30px;" onclick="javascript:openDataset();"  
				value="${datasetName}" readonly="true" >&nbsp;
	    <a href="#" onclick="javascript:openDataset();">
			<img src="<%=request.getContextPath()%>/images/search_results.gif" border="0">
		</a>&nbsp;&nbsp;  
        <input type="button" id="showDataset" name="showDataset" class="k-button" value="查看" 
		       style="width: 50px" onclick="javascript:showMyDataset();">
	    <span class="k-invalid-msg" data-for="selectedDs"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="mappingToSourceIdColumn" class="required">关联主键字段&nbsp;</label>
        <select id="mappingToSourceIdColumn" name="mappingToSourceIdColumn" >
			<option value="">----请选择----</option> 
			<c:forEach items="${columns}" var="item">
			<option value="${item.columnName}">${item.title}[${item.columnName}]</option>     
			</c:forEach>
		</select> 
	    <span class="k-invalid-msg" data-for="mappingToSourceIdColumn"></span>&nbsp;
		<!-- <a href="#" onclick="javascript:switchDataset();">
			<img src="<%=request.getContextPath()%>/images/search_results.gif" border="0">
		</a> -->  
		<script type="text/javascript">
	       document.getElementById("mappingToSourceIdColumn").value="${treeTableSyntheticRule.mappingToSourceIdColumn}";
	    </script>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="mappingToTargetColumn" class="required">抽取字段&nbsp;</label>
		<select id="mappingToTargetColumn" name="mappingToTargetColumn" >
			<option value="">----请选择----</option>
			<c:forEach items="${columns}" var="item">
			<option value="${item.columnName}">${item.title}[${item.columnName}]</option>     
			</c:forEach>
		</select> 
	    <span class="k-invalid-msg" data-for="mappingToTargetColumn"></span>
		<script type="text/javascript">
	       document.getElementById("mappingToTargetColumn").value="${treeTableSyntheticRule.mappingToTargetColumn}";
	    </script>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
		<label for="columnName" class="required">目标字段&nbsp;</label>
		<select id="columnName" name="columnName" >
			<option value="">----请选择----</option>    
			<c:forEach items="${rules}" var="item">
			<option value="${item.columnName}">${item.columnTitle}[${item.columnName}]</option>     
			</c:forEach>
		</select>
        <script type="text/javascript">
	       document.getElementById("columnName").value="${treeTableSyntheticRule.columnName}";
	    </script>
		<div style="margin-top:5px;">
			<span style="color:blue; margin-left:110px;">
			 （提示：可以选择已经存在的字段做目标字段，如不选择，将生成新字段做目标字段。）
			</span>
	   </div>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="mappingToTargetAlias" class="required">目标表字段别名&nbsp;</label>
        <input id="mappingToTargetAlias" name="mappingToTargetAlias" type="text" class="k-textbox"  
	           data-bind="value: mappingToTargetAlias" style="width:380px"
	           value="${treeTableSyntheticRule.mappingToTargetAlias}" validationMessage="请输入目标表字段别名"/>
	    <span class="k-invalid-msg" data-for="mappingToTargetAlias"></span>
		<div style="margin-top:5px;">
			<span style="color:blue; margin-left:110px;">
			 （提示：只有当来源相同字段提取到不同目标列才要录入该信息。）
			</span>
	   </div>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="columnTitle" class="required">列中文名称&nbsp;</label>
		<input type="hidden" id="columnLabel" name="columnLabel">
        <input id="columnTitle" name="columnTitle" type="text" class="k-textbox"  
	           data-bind="value: columnTitle" style="width:380px"
	           value="${treeTableSyntheticRule.columnTitle}" validationMessage="请输入列中文名称"/>
	    <span class="k-invalid-msg" data-for="columnTitle"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="type" class="required">字段类型&nbsp;</label>
		<select id="type" name="type" >
			<option value="">----请选择----</option>    
			<option value="String">字符串型</option>    
			<option value="Date">日期时间型</option> 
			<option value="Integer">整数型</option>    
			<option value="Long">长整数型</option> 
			<option value="Double">数值型</option>    
		</select>
	    <span class="k-invalid-msg" data-for="type"></span>
	    <script type="text/javascript">
	       document.getElementById("type").value="${treeTableSyntheticRule.type}";
	    </script>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="columnSize" class="required">字段长度&nbsp;</label>
		<select id="columnSize" name="columnSize" >
			<option value="500">默认500</option>    
			<option value="50">50</option>    
			<option value="100">100</option> 
			<option value="200">200</option>    
			<option value="250">250</option> 
			<option value="800">800</option> 
			<option value="1000">1000</option>    
			<option value="2000">2000</option>    
			<option value="4000">4000</option>    
		</select>
	    <span class="k-invalid-msg" data-for="columnSize"></span>
	    <script type="text/javascript">
	       document.getElementById("columnSize").value="${treeTableSyntheticRule.columnSize}";
	    </script>
    </td>
  </tr>

    <tr>
    <td width="2%" align="left" height="25">&nbsp;</td>
    <td align="left">
	  <label for="locked" class="required">是否有效&nbsp;</label>
	  <select id="locked" name="locked">
		<option value="0">是</option>
		<option value="1">否</option>
	  </select>
	  <script type="text/javascript">
	       document.getElementById("locked").value="${treeTableSyntheticRule.locked}";
	  </script>
    </td>
  </tr>

  <tr>
    <td colspan="2" align="center" valign="bottom" height="25">&nbsp;
    <div>
      <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>
	  &nbsp;
	  <button type="button" id="iconButton"  class="k-button " style="width: 90px" onclick="javascript:saveAs();">另存</button>
	</div>
	</td>
  </tr>
</table>   
</form>
</div>     
 
</body>
</html>