<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%@ taglib uri="http://github.com/jior/glaf/tags" prefix="glaf"%> 
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
<title>编辑SQL语句</title>
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
        "parentId": "${sqlDefinition.parentId}",
        "name": "${sqlDefinition.name}",
        "title": "${sqlDefinition.title}",
        "rowKey": "${sqlDefinition.rowKey}",
        "scheduleFlag": "${sqlDefinition.scheduleFlag}",
		"shareFlag": "${sqlDefinition.shareFlag}",
		"locked": "${sqlDefinition.locked}"
    });

   // kendo.bind(jQuery("#iForm"), viewModel);

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
	      var link = "<%=request.getContextPath()%>/mx/datamgr/sql/definition/saveSqlDefinition";
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

   function saveDataAs(){
       var form = document.getElementById("iForm");
       var validator = jQuery("#iForm").data("kendoValidator");
       if (validator.validate()) {
	      var link = "<%=request.getContextPath()%>/mx/datamgr/sql/definition/saveAsSqlDefinition";
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
 </script>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content ">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑SQL语句">&nbsp;
编辑SQL语句</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${sqlDefinition.id}"/>
<input type="hidden" id="sqlDefId_enc" name="sqlDefId_enc" value="${sqlDefId_enc}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  
  <c:if test="${!empty sqlDefinition}">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="name" class="required">编号&nbsp;</label>
        ${sqlDefinition.id} 
    </td>
  </tr>
  </c:if>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="parentId" class="required">父查询&nbsp;</label>
        <select id="parentId" name="parentId">
            <option value="">----请选择----</option>    
		<c:forEach items="${parentList}" var="item">
			<option value="${item.id}">${item.name}</option>     
		</c:forEach>
        </select>
		<script type="text/javascript">
	       document.getElementById("parentId").value="${sqlDefinition.parentId}";
	    </script>
		&nbsp;提示：父查询的主键列可以做子查询参数rowKey使用。
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="name" class="required">名称&nbsp;</label>
        <input id="name" name="name" type="text" class="k-textbox"  
	           data-bind="value: name" style="width:680px;"
	           value="${sqlDefinition.name}" validationMessage="请输入名称"/>
	    <span class="k-invalid-msg" data-for="name"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="code" class="required">编码&nbsp;</label>
        <input id="code" name="code" type="text" class="k-textbox"  
	           data-bind="value: code" style="width:680px;"
	           value="${sqlDefinition.code}" validationMessage="请输入编码"/>
	    <span class="k-invalid-msg" data-for="code"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="title" class="required">标题&nbsp;</label>
        <input id="title" name="title" type="text" class="k-textbox"  
	           data-bind="value: title" style="width:680px;"
	           value="${sqlDefinition.title}" validationMessage="请输入标题"/>
	    <span class="k-invalid-msg" data-for="title"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="sql" class="required">SQL语句&nbsp;</label>
	    <textarea id="sql" name="sql" rows="6" cols="46" data-bind="value: sql" class="k-textbox" style="height:320px; width:680px;" validationMessage="请输入SQL语句">${sqlDefinition.sql}</textarea> 
	    <span class="k-invalid-msg" data-for="sql"></span>
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="center">
		提示：如需执行数据库更新操作，请联系开发人员！
    </td>
  </tr>
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="countSql" class="required">汇总SQL语句&nbsp;</label>
	    <textarea id="countSql" name="countSql" rows="6" cols="46" data-bind="value: countSql" class="k-textbox" style="height:320px; width:680px;" validationMessage="请输入汇总SQL语句">${sqlDefinition.countSql}</textarea> 
	    <span class="k-invalid-msg" data-for="countSql"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	    <label for="rowKey" class="required">主键列&nbsp;</label>
        <input id="rowKey" name="rowKey" type="text" class="k-textbox"  
	           data-bind="value: rowKey" style="width:180px;"
	           value="${sqlDefinition.rowKey}" validationMessage="请输入主键列"/>
	    <span class="k-invalid-msg" data-for="rowKey"></span>
    </td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="fetchFlag" class="required">抓取数据&nbsp;</label>
	  <select id="fetchFlag" name="fetchFlag">
	    <option value="">----请选择----</option>
		<option value="Y">是</option>
		<option value="N">否</option>
	  </select>&nbsp;（是否自动抓取数据到统计表）
	  <script type="text/javascript">
	       document.getElementById("fetchFlag").value="${sqlDefinition.fetchFlag}";
	  </script>
	</td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="deleteFetch" class="required">每次抓取前删除&nbsp;</label>
	  <select id="deleteFetch" name="deleteFetch">
	    <option value="">----请选择----</option>
		<option value="Y">是</option>
		<option value="N">否</option>
	  </select>&nbsp;（提示：如果选择每次抓取前删除，将删除当天抓取的记录）
	  <script type="text/javascript">
	       document.getElementById("deleteFetch").value="${sqlDefinition.deleteFetch}";
	  </script>
	</td>
  </tr>

    <tr>
		<td width="2%" align="left">&nbsp;</td>
		<td class="x-content" colspan="3" height="28">
		    <label for="ordinal" class="required">执行次序&nbsp;</label>
		    <select id="ordinal" name="ordinal" class="span2" style="height:20px">
			    <option value="0">0</option>
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
				<option value="8">8</option>
				<option value="9">9</option>
		    </select>&nbsp;&nbsp;（提示：数值小的先执行，多个查询抓取到一个目标表时须设置。）
			<script type="text/javascript">
			    document.getElementById("ordinal").value="${sqlDefinition.ordinal}";
			</script>
		</td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="exportFlag" class="required">是否导出&nbsp;</label>
	  <select id="exportFlag" name="exportFlag">
	    <option value="">----请选择----</option>
	    <option value="N">否</option>
		<option value="Y">是</option>
	  </select>&nbsp;
	  <script type="text/javascript">
	       document.getElementById("exportFlag").value="${sqlDefinition.exportFlag}";
	  </script>
	</td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="exportTableName" class="required">导出表名&nbsp;</label>
	  <input id="exportTableName" name="exportTableName" type="text" class="k-textbox"  
	           data-bind="value: exportTableName" style="width:180px;"
	           value="${sqlDefinition.exportTableName}" validationMessage="导出表名"/>
	    <span class="k-invalid-msg" data-for="exportTableName"></span>
	</td>
  </tr>
  
  <c:if test="${!empty sqlDefinition}">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="targetTableName" class="required">抓取目标表&nbsp;</label>
	  <input id="targetTableName" name="targetTableName" type="text" class="k-textbox"  
	           data-bind="value: targetTableName" style="width:180px;"
	           value="${sqlDefinition.targetTableName}" validationMessage="请输入抓取目标表"/>
	    <span class="k-invalid-msg" data-for="targetTableName"></span>
		&nbsp;注意：&nbsp;抓取目标表必须以SQL_RESULT为前缀，后面为数字（正整数）。
	</td>
  </tr>
  </c:if>
  
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="scheduleFlag" class="required">是否定时调度&nbsp;</label>
	  <select id="scheduleFlag" name="scheduleFlag">
	    <option value="">----请选择----</option>
		<option value="Y">是</option>
		<option value="N">否</option>
	  </select>&nbsp;（是否由后台自动定时执行）
	  <script type="text/javascript">
	       document.getElementById("scheduleFlag").value="${sqlDefinition.scheduleFlag}";
	  </script>
	</td>
  </tr>

  <glaf:permission key="SystemAdministrator">
  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="shareFlag" class="required">是否共享&nbsp;</label>
	  <select id="shareFlag" name="shareFlag">
	    <option value="">----请选择----</option>
		<option value="Y">是</option>
		<option value="N">否</option>
	  </select>&nbsp;（其他用户能否看到并执行）
	  <script type="text/javascript">
	       document.getElementById("shareFlag").value="${sqlDefinition.shareFlag}";
	  </script>
	</td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="publicFlag" class="required">匿名访问&nbsp;</label>
	  <select id="publicFlag" name="publicFlag">
	    <option value="">----请选择----</option>
	    <option value="N">否</option>
		<option value="Y">是</option>
	  </select>&nbsp;（未登录用户能否访问结果集）
	  <script type="text/javascript">
	       document.getElementById("publicFlag").value="${sqlDefinition.publicFlag}";
	  </script>
	</td>
  </tr>
  </glaf:permission>


  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="aggregationFlag" class="required">聚合结果&nbsp;</label>
	  <select id="aggregationFlag" name="aggregationFlag">
	    <option value="">----请选择----</option>
		<option value="A">求平均值</option>
		<option value="S">求和</option>
		<option value="C">汇总个数</option>
	  </select>&nbsp;（执行聚合结果）
	  <script type="text/javascript">
	       document.getElementById("aggregationFlag").value="${sqlDefinition.aggregationFlag}";
	  </script>
	</td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="saveFlag" class="required">是否保存0记录&nbsp;</label>
	  <select id="saveFlag" name="saveFlag">
	    <option value="">----请选择----</option>
		<option value="Y">是</option>
		<option value="N">否</option>
	  </select>&nbsp;（是否保存统计结果为0的记录）
	  <script type="text/javascript">
	       document.getElementById("saveFlag").value="${sqlDefinition.saveFlag}";
	  </script>
	</td>
  </tr>

  <tr>
    <td width="2%" align="left">&nbsp;</td>
    <td align="left">
	  <label for="locked" class="required">是否有效&nbsp;</label>
	  <select id="locked" name="locked">
		<option value="0">是</option>
		<option value="1">否</option>
	  </select>
	  <script type="text/javascript">
	       document.getElementById("locked").value="${sqlDefinition.locked}";
	  </script>
	</td>
  </tr>

  <c:if test="${submitFlag eq 'true'}">
  <tr>
    <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
     <div>
      <button type="button" id="button"  class="k-button k-primary" style="width: 90px" 
	          onclick="javascript:saveData();">保存</button>
	  <button type="button" id="button2"  class="k-button k-primary" style="width: 90px" 
	          onclick="javascript:saveDataAs();">另存</button>
	 </div>
	</td>
  </tr>
  </c:if>
</table>   
</form>
<br/>
<br/>
</div>     
</body>
</html>