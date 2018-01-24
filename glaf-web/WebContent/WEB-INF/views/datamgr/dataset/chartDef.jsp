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
<title>设置数据集图表信息</title>
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
        "name": "${dataSet.name}",
        "title": "${dataSet.title}",
        "id": "${dataSet.id}"
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


   function saveData(){
        var form = document.getElementById("iForm");
        var validator = jQuery("#iForm").data("kendoValidator");
        if (validator.validate()) {
	    var link = "<%=request.getContextPath()%>/mx/dataset/saveChartDef";
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
					   //window.parent.location.reload();
				   }
			});
        }
   }


</script>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content ">
<br>
<div class="x_content_title">
<img src="<%=request.getContextPath()%>/images/window.png" alt="设置数据集图表信息">&nbsp;
设置数据集图表信息
</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${dataSet.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">

  <tr>
    <td width="12%" align="left"><label for="name" class="required">名称</label></td>
    <td width="88%" align="left"  colspan="3">
       ${dataSet.name}
    </td>
  </tr>
  <tr>
    <td width="12%" align="left"><label for="title" class="required">标题</label></td>
    <td width="88%" align="left" colspan="3">
        ${dataSet.title}
    </td>
  </tr>

  <tr>
    <td width="12%" align="left"><label for="category" class="required">分类</label></td>
    <td width="88%" align="left" colspan="3">
         <select id="category" name="category">
		   <c:forEach items="${dataSet.selectSegments}" var="seg">
			<option value="${seg.id}">${seg.title}[${seg.tableName}]</option>
		   </c:forEach>
         </select>&nbsp;(X轴)
		 <script type="text/javascript">
		     document.getElementById("category").value="${category}";
		 </script>
    </td>
  </tr>

  <tr>
    <td width="12%" align="left"><label for="series" class="required">系列</label></td>
    <td width="88%" align="left" colspan="3">
         <select id="series" name="series">
		   <c:forEach items="${dataSet.selectSegments}" var="seg">
			<option value="${seg.id}">${seg.title}[${seg.tableName}]</option>
		   </c:forEach>
         </select>&nbsp;(Y轴)
		 <script type="text/javascript">
		     document.getElementById("series").value="${series}";
		 </script>
    </td>
  </tr>

  <tr>
    <td width="12%" align="left"><label for="value" class="required">数值</label></td>
    <td width="88%" align="left" colspan="3">
         <select id="value" name="value">
		   <c:forEach items="${dataSet.selectSegments}" var="seg">
			<option value="${seg.id}">${seg.title}[${seg.tableName}]</option>
		   </c:forEach>
         </select>
		 &nbsp;（必须是计算结果为数值的列。）
		 <script type="text/javascript">
		     document.getElementById("value").value="${value}";
		 </script>
    </td>
  </tr>
 
   <tr>
        <td colspan="4" align="center" valign="bottom" height="30">&nbsp;
        <div>
          <button type="button" id="iconButton1"  class="k-button k-primary" style="width: 90px" 
		          onclick="javascript:saveData();">保存</button>
	</div>
	</td>
   </tr>
</table>   
</form>
<br>
<br>
</div>     
</body>
</html>