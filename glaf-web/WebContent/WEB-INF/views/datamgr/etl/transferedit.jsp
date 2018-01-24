<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
  String context = request.getContextPath();
  com.glaf.base.utils.ContextUtil.getInstance().setContextPath(
      context);
  pageContext.setAttribute("contextPath", context);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑转换配置信息</title>

<script type="text/javascript">
var contextPath="${contextPath}";
</script>

<link href="${contextPath}/scripts/kendoui/styles/kendo.common.min.css" rel="stylesheet" />
<link href="${contextPath}/scripts/kendoui/styles/kendo.rtl.min.css" rel="stylesheet" />
<link href="${contextPath}/scripts/kendoui/styles/kendo.default.min.css" rel="stylesheet" />
<link href="${contextPath}/scripts/kendoui/styles/kendo.dataviz.min.css" rel="stylesheet" />
<link href="${contextPath}/scripts/kendoui/styles/kendo.dataviz.default.min.css" rel="stylesheet" />
<link href="${contextPath}/scripts/kendoui/styles/kendo.mobile.all.min.css" rel="stylesheet" />
<link href="${contextPath}/scripts/layer/skin/layer.css" rel="stylesheet" />
<link href="${contextPath}/themes/default/styles.css" rel="stylesheet" type="text/css" >
<link href="${contextPath}/css/icons.css" rel="stylesheet" type="text/css" >
<link href="${contextPath}/css/kendoui.css" rel="stylesheet" type="text/css" >
<script type="text/javascript" src="${contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/jquery.form.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/json2.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/kendoui/js/angular.min.js"></script> 
<script type="text/javascript" src="${contextPath}/scripts/kendoui/js/kendo.all.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/kendoui/js/jszip.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/kendoui/js/cultures/kendo.culture.zh-CN.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/kendoui/js/messages/kendo.messages.zh-CN.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/layer/layer.min.js"></script>
<script type="text/javascript" src="${contextPath}/inc/globaljs.jsp"></script>
<script type="text/javascript" src="${contextPath}/scripts${contextPath}-core.js"></script>
<script type="text/javascript" src="${contextPath}/scripts${contextPath}-base.js"></script>
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


  $(document).ready(function() {
        $("#iconButton").kendoButton({
              spriteCssClass: "k-icon"
        }); 

        var id = window.getParameter("id");
        $("#id").val(id);
  });

   function getParameter(name){
        var params = {};
        var search = window.location.search;
        if(search && search.length > 1){
          search = search.substring(1);
          search = search.split("&");
          var s;
          $.each(search, function(i, v){
            s = v.split("=");
            params[s[0]] = s[1];
          });
        }
        return params[name];
    }


	function openDB(){
        var selected = jQuery("#executeDatabaseIds").val();
        var link = '${contextPath}/mx/dataset/chooseDatabases?elementId=executeDatabaseIds&elementName=database_names&selected='+selected+"&dataSetId=";
        var x=100;
        var y=100;
        if(is_ie) {
        	x=document.body.scrollLeft+event.clientX-event.offsetX-200;
        	y=document.body.scrollTop+event.clientY-event.offsetY-200;
        }
        openWindow(link,self,x, y, 695, 480);
    }



   function saveData(){
        var form = document.getElementById("iForm");
        var validator = $("#iForm").data("kendoValidator");
        if (validator.validate()) {
      	    var link = "${contextPath}/mx/datatransfer/editTransferConfig";
      	    var params = $("#iForm").formSerialize();
            // params.sourceId = $("#id").val();
      		  $.ajax({
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
      					   window.parent.location.reload();
  
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
    <img src="${contextPath}/images/window.png" alt="编辑转换配置信息">&nbsp;
    编辑转换配置信息
  </div>
  <br>
  <form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
  <input type="hidden" id="id" name="id" value=""/>
  <table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
 
    <tr>
      <td width="12%" align="left"><label for="transName" class="required">配置名称</label></td>
      <td width="88%" align="left"  colspan="3">
          <input id="transName" name="transName" type="text" class="k-textbox"  
               data-bind="value: transName" style="width:485px;"
               value="" validationMessage="请输入名称"/>
        <span class="k-invalid-msg" data-for="transName"></span>
      </td>
    </tr>
  	<!-- <tr>
  		<td width="12%" align="left"><label for="addressPerms" class="required">允许访问IP地址</label></td>
  		<td align="left" colspan="3"> 
  		    <textarea id="addressPerms" name="addressPerms" rows="6" cols="46" class="k-textbox" style="width:485px;" ></textarea>
  			<br><span style="color:red;">（需要独立提供对外数据服务时才设定。）</span>
  			<br>允许使用*为通配符，多个地址之间用半角的逗号“,”隔开。
  			<br>例如：192.168.*.*，那么192.168.1.100及192.168.142.100都可访问该服务。
              <br>192.168.142.*，那么192.168.1.100不能访问但192.168.142.100可访问该服务。
  			<br>如果配置成192.168.1.*,192.168.142.*，那么192.168.1.100及192.168.142.100均可访问该服务。
  		</td>
  	</tr> -->
   
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