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
<title>编辑数据表信息</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link href="<%=request.getContextPath()%>/scripts/layer/skin/layer.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/layer/layer.min.js"></script>
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
  var contextPath="<%=request.getContextPath()%>";
  
  jQuery(function() {
    var viewModel = kendo.observable({
        "title": "${sysDataTable.title}",
		"createBy": "${sysDataTable.createBy}",
        "createTime": "<fmt:formatDate value='${sysDataTable.createTime}' pattern='yyyy-MM-dd'/>",
        "updateTime": "<fmt:formatDate value='${sysDataTable.updateTime}' pattern='yyyy-MM-dd'/>",
        "updateBy": "${sysDataTable.updateBy}",
        "content": "${sysDataTable.content}",
		"readUrl": "${sysDataTable.readUrl}",
		"createUrl": "${sysDataTable.createUrl}",
		"updateUrl": "${sysDataTable.updateUrl}",
		"destroyUrl": "${sysDataTable.destroyUrl}",
		"accessType": "${sysDataTable.accessType}",
		"sortColumnName": "${sysDataTable.sortColumnName}",
		"sortOrder": "${sysDataTable.sortOrder}",
		"perms": "${sysDataTable.perms}",
		"addressPerms": "${sysDataTable.addressPerms}",
        "isSubTable": "${sysDataTable.isSubTable}",
		"locked": "${sysDataTable.locked}",
        "id": "${sysDataTable.id}"
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
	   //form.action = "<%=request.getContextPath()%>/mx/system/datatable/saveSysDataTable";
	   //form.submit();
	   var link = "<%=request.getContextPath()%>/mx/system/datatable/saveSysDataTable";
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
					   window.close();
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
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑SysDataTable信息">&nbsp;
编辑数据表信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${sysDataTable.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	<label for="tablename" class="required">表名&nbsp;</label>
	 <c:choose>
	   <c:when test="${!empty sysDataTable}">
	    ${sysDataTable.tablename}
		<input type="hidden" name="tablename" value="${sysDataTable.tablename}">
	   </c:when>
	   <c:otherwise>
	    <input id="tablename" name="tablename" type="text" class="k-textbox"  
	           data-bind="value: tablename" style="width:340px;"
	           value="${sysDataTable.tablename}" validationMessage="请输入表名"/>
	    <span class="k-invalid-msg" data-for="tablename"></span>
       </c:otherwise>
	   </c:choose>
	</td>
    </tr>

	<tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	<label for="title" class="required">标题&nbsp;</label>
        <input id="title" name="title" type="text" class="k-textbox"  
	           data-bind="value: title" style="width:340px;"
	           value="${sysDataTable.title}" validationMessage="请输入标题"/>
	<span class="k-invalid-msg" data-for="title"></span>
	</td>
    </tr>

	<tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="type" class="required">类型&nbsp;</label>
        <select id="type" name="type">
		    <option value="">----请选择----</option>
			<option value="0">临时数据表</option>
			<option value="10">用户自定义表</option>
			<option value="20">中间计量表</option>
			<option value="40">流程审批表</option>
			<option value="90">基础数据表</option>
			<option value="99">系统表</option>
        </select>     
	    <span class="k-invalid-msg" data-for="type"></span>
	</td>
    </tr>

	<tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="treeType" class="required">显示树型结构&nbsp;</label>
        <select id="treeType" name="treeType">
		    <option value="">----请选择----</option>
			<option value="Y">是</option>
			<option value="N">否</option>
        </select> 
		&nbsp;（提示：树型结构将自动产生树节点元素）
	    <span class="k-invalid-msg" data-for="treeType"></span>
	</td>
    </tr>
    
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	<label for="content" >描述&nbsp;</label>
	<textarea id="content" name="content" data-bind="value: content" rows="6" cols="46" class="k-textbox" style="height:90px; width:340px;"></textarea>  
	<span class="k-invalid-msg" data-for="content"></span>
	</td>
    </tr>

	<tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="readUrl">数据读取链接&nbsp;</label>
        <input id="readUrl" name="readUrl" type="text" class="k-textbox"  
	           data-bind="value: readUrl" style="width:340px;"
	           value="${sysDataTable.readUrl}" validationMessage="请输入数据读取链接"/>
	    <span class="k-invalid-msg" data-for="readUrl"></span>
	</td>
    </tr>

	<tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	<label for="createUrl">数据新增链接&nbsp;</label>
        <input id="createUrl" name="createUrl" type="text" class="k-textbox"  
	           data-bind="value: createUrl" style="width:340px;"
	           value="${sysDataTable.createUrl}" validationMessage="请输入数据新增链接"/>
	    <span class="k-invalid-msg" data-for="createUrl"></span>
	</td>
    </tr>

	<tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="updateUrl">数据修改链接&nbsp;</label>
        <input id="updateUrl" name="updateUrl" type="text" class="k-textbox"  
	           data-bind="value: updateUrl" style="width:340px;"
	           value="${sysDataTable.updateUrl}" validationMessage="请输入数据修改链接"/>
	    <span class="k-invalid-msg" data-for="updateUrl"></span>
	</td>
    </tr>

	<tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="destroyUrl">数据删除链接&nbsp;</label>
        <input id="destroyUrl" name="destroyUrl" type="text" class="k-textbox"  
	           data-bind="value: destroyUrl" style="width:340px;"
	           value="${sysDataTable.destroyUrl}" validationMessage="请输入数据删除链接"/>
	    <span class="k-invalid-msg" data-for="destroyUrl"></span>
	</td>
    </tr>

	<tr>
		<td width="2%" align="left"></td>
		<td align="left">
			<label for="sortColumnName" >默认排序列&nbsp;</label>
			<select id="sortColumnName" name="sortColumnName">
			  <option value="">----请选择----</option>
			 <c:forEach items="${sysDataTable.fields}" var="field">
		      <c:if test="${!empty field.columnName}">
			     <option value="${field.columnName}">${field.title}[${field.columnName}]</option>
			  </c:if>
             </c:forEach>   
			</select>
			&nbsp;
			<select id="sortOrder" name="sortOrder">
				<option value="">----请选择----</option>
				<option value="asc">升序</option>
				<option value="desc">降序</option>
			</select>
			<span class="k-invalid-msg" data-for="columnName"></span> 
		</td>
	</tr>

	<tr>
		<td width="2%" align="left"></td>
		<td align="left">
			<label for="accessType" >访问权限&nbsp;</label>
			<select id="accessType" name="accessType">
				<option value="">----请选择----</option>
				<option value="PUB">公开</option>
				<option value="PRI">私有</option>
			</select> &nbsp;（提示：访问权限为公开时任何人可读，创建者及管理员可读写）
			<span class="k-invalid-msg" data-for="accessType"></span> 
		</td>
	</tr>

	<tr>
		<td width="2%" align="left"></td>
		<td align="left">
		    <label for="x_roles_name">访问角色&nbsp;</label>
			<input type="hidden" id="perms" name="perms" value="${sysDataTable.perms}">
            <textarea  id="x_roles_name" name="x_roles_name" rows="6" cols="36" style="width:300px;" class="k-textbox" readonly 
		    >${x_role_names}</textarea>
			<input type="button" name="button" value="添加" class="k-button" 
			       onclick="javascript:selectRole('iForm', 'perms','x_roles_name');"> 
			&nbsp;
			<input type="button" name="button" value="清空" class="k-button" 
			       onclick="javascript:clearSelected('perms','x_roles_name');">
		</td>
	</tr>

	<tr>
		<td width="2%" align="left"></td>
		<td align="left">
		    <label for="addressPerms">允许访问IP地址&nbsp;</label>
		    <textarea id="addressPerms" name="addressPerms" rows="6" cols="36" style="width:300px;" class="k-textbox"  >${sysDataTable.addressPerms}</textarea>
			<br>允许使用*为通配符，多个地址之间用半角的逗号“,”隔开。
			<br>例如：192.168.*.*，那么192.168.1.100及192.168.142.100都可访问该服务。
            <br>192.168.142.*，那么192.168.1.100不能访问但192.168.142.100可访问该服务。
			<br>如果配置成192.168.1.*,192.168.142.*，那么192.168.1.100及192.168.142.100均可访问该服务。
		</td>
	</tr>

    
   <tr>
        <td class="input-box2" valign="top"></td>
        <td>
		  <label for="locked" >是否有效&nbsp;</label>
          <select id="locked" name="locked">
		    <option value="">----请选择----</option>
			<option value="0">是</option>
			<option value="1">否</option>
         </select> 
	     <span class="k-invalid-msg" data-for="treeType"></span>
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
<br/>
<br/>
<script type="text/javascript">
    jQuery(function() {
		document.getElementById("type").value="${sysDataTable.type}";
		document.getElementById("treeType").value="${sysDataTable.treeType}";
		document.getElementById("accessType").value="${sysDataTable.accessType}";
		document.getElementById("sortColumnName").value="${sysDataTable.sortColumnName}";
		document.getElementById("sortOrder").value="${sysDataTable.sortOrder}";
		document.getElementById("locked").value="${sysDataTable.locked}";
	});
</script> 
</body>
</html>