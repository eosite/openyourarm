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
<title>编辑参数信息</title>
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

   function execute(){
       var form = document.getElementById("iForm");
       var validator = jQuery("#iForm").data("kendoValidator");
       if (validator.validate()) {
	      var link = "<%=request.getContextPath()%>/mx/sys/treeTableAggregate/execute?treeTableAggregateId=${treeTableAggregate.id}";
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

   

 </script>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content ">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑参数信息">&nbsp;
    编辑参数信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="aggregateId" name="aggregateId" value="${aggregateId}"/>
<input type="hidden" id="id" name="id" value="${treeTableAggregate.id}"/>
<table width="98%" align="center" border="0" cellspacing="0" cellpadding="5">

 <% for(int i=1; i <= 10; i++){%>
  <tr>
    <td width="10%" align="left">参数<%=i%>&nbsp;</td>
    <td width="30%" align="left">
	    参数名:<input type="text" id="param_name_<%=i%>" name="param_name_<%=i%>" class="k-textbox" style="width:150px">
    </td>
	<td width="20%" align="left">
	    参数类型:
		<select id="param_type_<%=i%>" name="param_type_<%=i%>">
			<option value="String">字符串型</option>
			<option value="Integer">整数型</option>
			<option value="Long">长整数型</option>
			<option value="Double">数值型</option>
			<option value="Date">日期型</option>
		</select>
    </td>
	<td width="30%" align="left">
	    参数值:<input type="text" id="param_value_<%=i%>" name="param_value_<%=i%>" class="k-textbox" style="width:150px">
    </td>
  </tr>
 <%}%>

  <tr>
    <td colspan="4" align="center" valign="bottom" height="25">&nbsp;
      <div>
          <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" 
		          onclick="javascript:execute();">执行</button>
	  </div>
	</td>
  </tr>
</table>   
</form>
</div>     
<br/>
<br/>
</body>
</html>