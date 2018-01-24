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
<title>编辑Docker容器编排信息</title>
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
</style>
<script type="text/javascript">
                
  jQuery(function() {
    var viewModel = kendo.observable({
        "deploymentId": "${dockerComposeSegment.deploymentId}",
        "title": "${dockerComposeSegment.title}",
        "name": "${dockerComposeSegment.name}",
        "image": "${dockerComposeSegment.image}",
        "containerName": "${dockerComposeSegment.containerName}",
		"databaseId": "${dockerComposeSegment.databaseId}",
		"serverId": "${dockerComposeSegment.serverId}",
		"port": "${dockerComposeSegment.port}",
        "sortNo": "${dockerComposeSegment.sortNo}",
        "active": "${dockerComposeSegment.active}",
        "id": "${dockerComposeSegment.id}"
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
	    //form.method="post";
	    //form.action = "<%=request.getContextPath()%>/mx/deployment/dockerComposeSegment/saveDockerComposeSegment";
	    //form.submit();
		var exposePort = "";
		for(var i=0; i<10; i++){
             if(document.getElementById("exposePort_"+i).value != ""){
				 if(document.getElementById("exposePort_"+i).value * 1.0 <65535){
				     exposePort = exposePort + document.getElementById("exposePort_"+i).value+",";
				 }
			 }
		}
		document.getElementById("exposePort").value=exposePort;
	    var link = "<%=request.getContextPath()%>/mx/deployment/dockerComposeSegment/saveDockerComposeSegment";
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
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑Docker容器编排信息">&nbsp;
编辑Docker容器编排信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${dockerComposeSegment.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="title" class="required">标题&nbsp;</label>
    <input id="title" name="title" type="text" class="k-textbox"  
	       data-bind="value: title" style="width:480px;"
	       value="${dockerComposeSegment.title}" validationMessage="请输入标题"/>
	<span class="k-invalid-msg" data-for="title"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="name" class="required">名称&nbsp;</label>
    <input id="name" name="name" type="text" class="k-textbox"  
	       data-bind="value: name" style="width:480px;"
	       value="${dockerComposeSegment.name}" validationMessage="请输入名称"/>
	<span class="k-invalid-msg" data-for="name"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	 <label for="imageId" class="required">镜像名称&nbsp;</label>
     <select id="imageId" name="imageId" >
		<option value="">----请选择----</option>    
		<c:forEach items="${images}" var="item">
		<option value="${item.id}">${item.title}</option>     
		</c:forEach>
	 </select>
	 <script type="text/javascript">
	       document.getElementById("imageId").value="${dockerComposeSegment.imageId}";
	    </script>
	 <span class="k-invalid-msg" data-for="imageId"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="containerName" class="required">容器名称&nbsp;</label>
    <input id="containerName" name="containerName" type="text" class="k-textbox"  
	       data-bind="value: containerName" style="width:480px;"
	       value="${dockerComposeSegment.containerName}" validationMessage="请输入容器名称"/>
	<span class="k-invalid-msg" data-for="containerName"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="databaseId" class="required">数据库&nbsp;</label>
	  <select id="databaseId" name="databaseId">
		<option value="">----请选择----</option>
		<c:forEach items="${databases}" var="item">
		<option value="${item.id}">${item.title}</option>     
		</c:forEach>
	  </select>
	  <script type="text/javascript">
	       document.getElementById("databaseId").value="${dockerComposeSegment.databaseId}";
	  </script>
	  <span class="k-invalid-msg" data-for="databaseId"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="serverId" class="required">部署服务器&nbsp;</label>
	  <select id="serverId" name="serverId">
		<option value="">----请选择----</option>
		<c:forEach items="${servers}" var="item">
		<option value="${item.id}">${item.title}</option>     
		</c:forEach>
	  </select>
	  <script type="text/javascript">
	       document.getElementById("serverId").value="${dockerComposeSegment.serverId}";
	  </script>    
	  <span class="k-invalid-msg" data-for="serverId"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="port" class="required">对外端口&nbsp;</label>
	<input id="port" name="port" type="text" class="k-textbox" 
	       data-bind="value: port" style="width:60px;" maxlength="5"
	       value="${dockerComposeSegment.port}" validationMessage="请输入对外端口"/>
	<span class="k-invalid-msg" data-for="port"></span>
    </td>
  </tr>
  <!-- <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	<label for="content" class="required">内容&nbsp;</label>
    <textarea id="content" name="content" rows="12" cols="68" class="k-textbox"
				  style="width:480px;height:120px;"  
				  readonly="true" >${dockerComposeSegment.content}</textarea> 
	<span class="k-invalid-msg" data-for="content"></span>
    </td>
  </tr> -->
<c:if test="${!empty dockerComposeSegment}">
<tr>
    <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
     <table id="environment_table">
     <tr>
		<td colspan="3" align="left"><label for="content" class="required">环境变量</label></td>
     </tr>
     <td colspan="2" align="left" valign="bottom" height="30">&nbsp;
        <div id="environment_grid"></div>
		<script type="text/javascript">
            $(document).ready(function () {
				 var dataSource = new kendo.data.DataSource({
                            transport: {
                                read:  {
									 "contentType": "application/json",
									 "type": "POST",
                                     "url": "<%=request.getContextPath()%>/rs/deployment/templateVariableInstance/data?category=environment&segmentId=${dockerComposeSegment.id}"
                                },
                                update: {
                                    "contentType": "application/json",
                                    "type": "POST",
                                    "url": "<%=request.getContextPath()%>/rs/deployment/templateVariableInstance/update"
                                },
                                destroy: {
                                    "contentType": "application/json",
                                    "type": "POST",
                                    "url": "<%=request.getContextPath()%>/rs/deployment/templateVariableInstance/delete"
                                },
                                create: {
                                    "contentType": "application/json",
                                    "type": "POST",
                                    "url": "<%=request.getContextPath()%>/rs/deployment/templateVariableInstance/create?category=environment&segmentId=${dockerComposeSegment.id}"
                                },
                                parameterMap: function(options, operation) {
                                    if (operation !== "read" && options.models) {
										//alert(JSON.stringify(options.models));
                                        return  JSON.stringify(options.models);
                                    }
                                }
                            },
                            batch: true,
                            pageSize: 200,
                            schema: {
                                model: {
                                    id: "id",
                                    fields: {
                                        id: { editable: false, nullable: true },
                                        name: { validation: { required: true } },
                                        value: { validation: { required: true } }
                                    }
                                }
                            }
                        });

                $("#environment_grid").kendoGrid({
					dataSource: dataSource,
                    pageable: false,
                    height: 250,
                    toolbar: ["create"],
                    columns: [
                            { field: "name", title: "变量名称",  width: "180px" },
                            { field: "value", title:"变量值", width: "180px" },
                            { command: ["edit", "destroy"], title: "&nbsp;", width: "150px" }],
                    editable: "inline"
                });
            });
		</script>
	 </td>
     </table>
	</td>
  </tr>

  <tr>
    <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
     <table id="volumes_table">
     <tr>
		<td colspan="3" align="left"><label for="content" class="required">数据卷</label></td>
     </tr>
     <td colspan="2" align="left" valign="bottom" height="30">&nbsp;
        <div id="volumes_grid"></div>
		<script type="text/javascript">
            $(document).ready(function () {
				 var dataSource = new kendo.data.DataSource({
                            transport: {
                                read:  {
									 "contentType": "application/json",
									 "type": "POST",
                                     "url": "<%=request.getContextPath()%>/rs/deployment/templateVariableInstance/data?category=volumes&segmentId=${dockerComposeSegment.id}"
                                },
                                update: {
                                    "contentType": "application/json",
                                    "type": "POST",
                                    "url": "<%=request.getContextPath()%>/rs/deployment/templateVariableInstance/update"
                                },
                                destroy: {
                                    "contentType": "application/json",
                                    "type": "POST",
                                    "url": "<%=request.getContextPath()%>/rs/deployment/templateVariableInstance/delete"
                                },
                                create: {
                                    "contentType": "application/json",
                                    "type": "POST",
                                    "url": "<%=request.getContextPath()%>/rs/deployment/templateVariableInstance/create?category=volumes&segmentId=${dockerComposeSegment.id}"
                                },
                                parameterMap: function(options, operation) {
                                    if (operation !== "read" && options.models) {
										//alert(JSON.stringify(options.models));
                                        return  JSON.stringify(options.models);
                                    }
                                }
                            },
                            batch: true,
                            pageSize: 200,
                            schema: {
                                model: {
                                    id: "id",
                                    fields: {
                                        id: { editable: false, nullable: true },
                                        name: { validation: { required: true } },
                                        value: { validation: { required: true } }
                                    }
                                }
                            }
                        });

                $("#volumes_grid").kendoGrid({
					dataSource: dataSource,
                    pageable: false,
                    height: 250,
                    toolbar: ["create"],
                    columns: [
                            { field: "name", title: "宿主机目录",  width: "180px" },
                            { field: "value", title:"容器目录", width: "180px" },
                            { command: ["edit", "destroy"], title: "&nbsp;", width: "150px" }],
                    editable: "inline"
                });
            });
		</script>
	 </td>
     </table>
	</td>
  </tr>

  <tr>
    <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
     <table id="ports_table">
     <tr>
		<td colspan="3" align="left"><label for="content" class="required">端口映射</label></td>
     </tr>
     <td colspan="2" align="left" valign="bottom" height="30">&nbsp;
        <div id="ports_grid"></div>
		<script type="text/javascript">
            $(document).ready(function () {
				var dataSource = new kendo.data.DataSource({
                            transport: {
                                read:  {
									 "contentType": "application/json",
									 "type": "POST",
                                     "url": "<%=request.getContextPath()%>/rs/deployment/templateVariableInstance/data?category=ports&segmentId=${dockerComposeSegment.id}"
                                },
                                update: {
                                    "contentType": "application/json",
                                    "type": "POST",
                                    "url": "<%=request.getContextPath()%>/rs/deployment/templateVariableInstance/update"
                                },
                                destroy: {
                                    "contentType": "application/json",
                                    "type": "POST",
                                    "url": "<%=request.getContextPath()%>/rs/deployment/templateVariableInstance/delete"
                                },
                                create: {
                                    "contentType": "application/json",
                                    "type": "POST",
                                    "url": "<%=request.getContextPath()%>/rs/deployment/templateVariableInstance/create?category=ports&segmentId=${dockerComposeSegment.id}"
                                },
                                parameterMap: function(options, operation) {
                                    if (operation !== "read" && options.models) {
										//alert(JSON.stringify(options.models));
                                        return  JSON.stringify(options.models);
                                    }
                                }
                            },
                            batch: true,
                            pageSize: 200,
                            schema: {
                                model: {
                                    id: "id",
                                    fields: {
                                        id: { editable: false, nullable: true },
                                        name: { validation: { required: true } },
                                        value: { validation: { required: true } }
                                    }
                                }
                            }
                        });

                $("#ports_grid").kendoGrid({
					    dataSource: dataSource,
                        pageable: false,
                        height: 150,
                        toolbar: ["create"],
                        columns: [
                            { field: "name", title: "宿主机端口",  width: "90px" },
                            { field: "value", title:"容器端口", width: "90px" },
                            { command: ["edit", "destroy"], title: "&nbsp;", width: "150px" }],
                        editable: "inline"
                });
            });
		</script>
	 </td>
     </table>
	</td>
  </tr>
 </c:if>

  <tr>
    <td colspan="2" align="left" valign="bottom" height="30">&nbsp;
	  <label for="content" class="required">导出端口</label>&nbsp;
	  <input type="hidden" id="exposePort" name="exposePort" value="${dockerComposeSegment.exposePort}">
      <%
	     String exposePort="";
	     for(int i=0; i<10; i++){
             exposePort = (String)request.getAttribute("exposePort_" + i);
             if(exposePort==null){
				 exposePort="";
			 }
	  %>
         <input id="exposePort_<%=i%>" name="exposePort_<%=i%>" type="text" class="k-textbox"  
	            style="width:60px;" maxlength="5"
	            value="<%=exposePort%>" />
	  <%
	     }
	  %>
	</td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	 <label for="active" class="required">是否有效&nbsp;</label>
      <select id="active" name="active">
		<option value="Y">是</option>
		<option value="N">否</option>
	  </select>
	  <script type="text/javascript">
	       document.getElementById("active").value="${dockerComposeSegment.active}";
	  </script>    
	 <span class="k-invalid-msg" data-for="active"></span>
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
</body>
</html>