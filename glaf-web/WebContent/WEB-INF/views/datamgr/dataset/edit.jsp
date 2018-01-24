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
<title>编辑数据集信息</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<link rel="stylesheet" href="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="/glaf/scripts/metronic/4.5.2/theme/assets/global/css/components.min.css" type="text/css" />
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
	  
	  
	  var dataSet = {
			  "name": "${dataSet.name}",
		        "title": "${dataSet.title}",
		        "description": "${dataSet.description}",
		        "statementId": "${dataSet.statementId}",
		        "accessType": "${dataSet.accessType}",
		        "perms": "${dataSet.perms}",
		        "addressPerms": "${dataSet.addressPerms}",
		        "shareFlag": "${dataSet.shareFlag}",
		        "cacheFlag": "${dataSet.cacheFlag}",
				"dynamicFlag": "${dataSet.dynamicFlag}",
				"dynamicDataSet": "${dataSet.dynamicDataSet}",
				"distinctFlag": "${dataSet.distinctFlag}",
		        "locked": "${dataSet.locked}",
		        "id": "${dataSet.id}",
		        "exportTableName": "${dataSet.exportTableName}"
	  };
	  
	  <c:if test="${!empty dataSet.extText}">
	  	$.each(dataSet.extText = ${dataSet.extText}, //
	  			function(k, v){
	  		$("#" + k).val(v);
	  	});
	  </c:if>
	  
    var viewModel = kendo.observable(dataSet);

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

        $('body').on('click', '.portlet > .portlet-title > .tools > .collapse, .portlet .portlet-title > .tools > .expand', function(e) {
            e.preventDefault();
            var el = $(this).closest(".portlet").children(".portlet-body");
            if ($(this).hasClass("collapse")) {
                $(this).removeClass("collapse").addClass("expand");
                el.slideUp(200);
            } else {
                $(this).removeClass("expand").addClass("collapse");
                el.slideDown(200);
            }
        });
    });



	function openDB(){
        var selected = jQuery("#executeDatabaseIds").val();
        var link = '<%=request.getContextPath()%>/mx/dataset/chooseDatabases?elementId=executeDatabaseIds&elementName=database_names&selected='+selected+"&dataSetId=${dataSet.id}";
        var x=100;
        var y=100;
        if(is_ie) {
        	x=document.body.scrollLeft+event.clientX-event.offsetX-200;
        	y=document.body.scrollTop+event.clientY-event.offsetY-200;
        }
        openWindow(link,self,x, y, 695, 480);
    }
	
   function initExt(params){
	   var obj = {}, ret = false;
	   $.each(["extText"], function(i, v){
	    	var ext = {}, $this;
	    	$("." + v).each(function($i, $v){
	    		$this = $(this);
	    		ext[$this.attr("id")] = $this.val();
	    	});
	    	obj[v] = JSON.stringify(ext);
	    	ret = true;
	    });
	   return params +  (ret ? "&" + $.param(obj) : "");
   }

   function saveData(){
        var form = document.getElementById("iForm");
        var validator = jQuery("#iForm").data("kendoValidator");
        if (validator.validate()) {
	    var link = "<%=request.getContextPath()%>/mx/dataset/saveDataSet";
	    var params = initExt(jQuery("#iForm").formSerialize());
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
					   
					    var $parent = window.opener || window.parent;
						var fn = "${param.fn}";
						if($parent && $parent[fn]){
							$parent && $parent[fn] && $parent[fn](data);
						} else {
						   window.parent.location.reload();
					    }
					   
				   }
			});
        }
   }


<c:if test="${!empty dataSet}">
   function saveDataAs(){
        var form = document.getElementById("iForm");
        var validator = jQuery("#iForm").data("kendoValidator");
        if (validator.validate()) {
	        var link = "<%=request.getContextPath()%>/mx/dataset/saveAsDataSet";
	        var params = initExt(jQuery("#iForm").formSerialize());
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
 </c:if>
</script>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content ">
<br>
<div class="x_content_title">
<img src="<%=request.getContextPath()%>/images/window.png" alt="编辑数据集信息">&nbsp;
编辑数据集信息
</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${dataSet.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
 <tr>
    <td width="12%" align="left"><label for="name" class="required">名称</label></td>
    <td width="88%" align="left"  colspan="3">
        <input id="name" name="name" type="text" class="k-textbox"  
	           data-bind="value: name" style="width:485px;"
	           value="${dataSet.name}" validationMessage="请输入名称"/>
	    <span class="k-invalid-msg" data-for="name"></span>
    </td>
  </tr>

  <tr>
    <td width="12%" align="left"><label for="title" class="required">标题</label></td>
    <td width="88%" align="left" colspan="3">
        <input id="title" name="title" type="text" class="k-textbox"  
	           data-bind="value: title" style="width:485px;"
	           value="${dataSet.title}" validationMessage="请输入标题"/>
	    <span class="k-invalid-msg" data-for="title"></span>
    </td>
  </tr>
  
   <tr>
    <td width="12%" align="left"><label for="description" class="required">描述&nbsp;</label></td>
    <td width="88%" align="left" colspan="3">
		<textarea  id="description" name="description" rows="6" cols="46" class="k-textbox" style="height:60px;width:485px;" 
		    >${dataSet.description}</textarea>
	    <span class="k-invalid-msg" data-for="description"></span>
    </td>
   </tr>
 


 <!-- <c:if test="${!empty treeModels}">
  <tr>
    <td width="12%" align="left"><label for="nodeId" class="required">分类</label></td>
    <td width="38%" align="left">
      <select id="nodeId" name="nodeId">
 	       <option value="">----请选择----</option>
 		   <c:forEach items="${treeModels}" var="treeModel">
           <option value="${treeModel.id}">${treeModel.name}</option>
 		      <c:forEach items="${treeModel.children}" var="child">
              <option value="${child.id}">&nbsp;&nbsp;--${child.name}</option>
 			      <c:forEach items="${child.children}" var="cd">
                  <option value="${cd.id}">&nbsp;&nbsp;&nbsp;&nbsp;------${cd.name}</option>
 				    <c:forEach items="${cd.children}" var="c">
                    <option value="${c.id}">&nbsp;&nbsp;&nbsp;&nbsp;----------${c.name}</option>
 					<c:forEach items="${c.children}" var="x">
 					  <option value="${x.id}">&nbsp;&nbsp;&nbsp;&nbsp;--------------${x.name}</option>
 					</c:forEach>
 		            </c:forEach>
 		          </c:forEach>
 		      </c:forEach>
 		   </c:forEach>
 	  </select>
 	  <script type="text/javascript">
 	  	   var nodeId = "${dataSet.nodeId}" || "${param.nodeId}";
 	       document.getElementById("nodeId").value = nodeId;
 	  </script>
    </td>
 	<td width="12%" align="left">&nbsp;</td>
    <td width="38%" align="left">&nbsp;</td>
  </tr>
 </c:if>
 
  <tr>
    <td width="12%" align="left"><label for="shareFlag" class="required">是否共享</label></td>
    <td width="38%" align="left">
      <select id="shareFlag" name="shareFlag">
 	    <option value="">----请选择----</option>
 		<option value="Y">是</option>
 		<option value="N">否</option>
 	  </select>
 	  <script type="text/javascript">
 	       document.getElementById("shareFlag").value="${dataSet.shareFlag}";
 	  </script>
    </td>
 	<td width="12%" align="left"><label for="accessType" class="required">访问类型</label></td>
    <td width="38%" align="left">
      <select id="accessType" name="accessType">
 	    <option value="PRV">私有</option>
 		<option value="PUB">公开</option>
 	  </select>
 	  <script type="text/javascript">
 	       document.getElementById("accessType").value="${dataSet.accessType}";
 	  </script>   
    </td>
  </tr>
 
  <tr>
    <td width="12%" align="left"><label for="distinctFlag" class="required">记录唯一</label></td>
    <td width="38%" align="left">
      <select id="distinctFlag" name="distinctFlag">
 	    <option value="">----请选择----</option>
 		<option value="Y">是</option>
 		<option value="N">否</option>
 	  </select>
 	  <script type="text/javascript">
 	       document.getElementById("distinctFlag").value="${dataSet.distinctFlag}";
 	  </script>
    </td>
 	<td width="12%" align="left"><label for="locked" class="required">是否有效</label></td>
    <td width="38%" align="left">
 	  <select id="locked" name="locked">
 		<option value="0">是</option>
 		<option value="1">否</option>
 	  </select>
 	  <script type="text/javascript">
 	       document.getElementById("locked").value="${dataSet.locked}";
 	  </script>
 	</td>
   </tr>
 
  <tr>
    <td width="12%" align="left"><label for="cacheFlag" class="required">缓存结果集</label></td>
    <td width="38%" align="left">
      <select id="cacheFlag" name="cacheFlag">
 	    <option value="">----请选择----</option>
 		<option value="Y">是</option>
 		<option value="N">否</option>
 	  </select>
 	  <script type="text/javascript">
 	       document.getElementById("cacheFlag").value="${dataSet.cacheFlag}";
 	  </script>
    </td>
 	<td width="12%" align="left"><label for="locked" class="required">是否动态结果集</label></td>
    <td width="38%" align="left">
 	  <select id="dynamicFlag" name="dynamicFlag">
 	    <option value="">----请选择----</option>
 		<option value="Y">是</option>
 		<option value="N">否</option>
 	  </select>
 	  <script type="text/javascript">
 	       document.getElementById("dynamicFlag").value="${dataSet.dynamicFlag}";
 	  </script>
 	</td>
   </tr>
   
  <tr>
 	<td width="12%" align="left"><label for="locked" class="required">动态数据集</label></td>
    <td width="38%" align="left">
 	  <select id="dynamicDataSet" name="dynamicDataSet">
 	    <option value="">----请选择----</option>
 		<option value="Y">是</option>
 		<option value="N">否</option>
 	  </select>
 	  <script type="text/javascript">
 	       document.getElementById("dynamicDataSet").value="${dataSet.dynamicDataSet}";
 	  </script>
 	</td>
   </tr> -->

  <!-- <tr>
    <td width="12%" align="left"><label for="statementId" class="required">MyBatis语句&nbsp;</label></td>
    <td width="88%" align="left" colspan="3">
        <input id="statementId" name="statementId" type="text" class="k-textbox"  
	           data-bind="value: statementId" style="width:485px;"
	           value="${dataSet.statementId}" validationMessage="请输入MyBatis语句statementId"/>
	    <span class="k-invalid-msg" data-for="statementId"></span>
    </td>
  </tr> -->
  
   <!-- <tr>
    <td width="12%" align="left"><label for="description" class="required">可执行库&nbsp;</label></td>
    <td width="88%" align="left" colspan="3">
   	    <input type="hidden" id="executeDatabaseIds" name="executeDatabaseIds" value="${dataSet.executeDatabaseIds}">
   		<textarea  id="database_names" name="database_names" rows="6" cols="46" class="k-textbox" 
   		           style="height:60px;width:485px;" onclick="javascript:openDB();" >${database_names}</textarea>&nbsp;
   		<a href="#" onclick="javascript:openDB();">
   			<img src="<%=request.getContextPath()%>/images/search_results.gif" border="0">
   		</a>  
   	    <span class="k-invalid-msg" data-for="database_names"></span>
    </td>
   </tr>
   
   <tr>
   		<td width="12%" align="left"><label for="perms" class="required">访问角色</label></td>
   		<td align="left" colspan="3"> 
   			<input type="hidden" id="perms" name="perms" value="${dataSet.perms}">
            <textarea  id="x_roles_name" name="x_roles_name" rows="6" cols="46" class="k-textbox" readonly style="width:485px;" 
   		    >${x_role_names}</textarea>
   			<input type="button" name="button" value="添加" class="k-button" 
   			       onclick="javascript:selectRole('iForm', 'perms','x_roles_name');"> 
   			&nbsp;
   			<input type="button" name="button" value="清空" class="k-button" 
   			       onclick="javascript:clearSelected('perms','x_roles_name');">
   			<br><span style="color:red;">（需要独立提供对外数据服务时才设定。）</span>
   		</td>
   	</tr> -->

	<!-- <tr>
		<td width="12%" align="left"><label for="addressPerms" class="required">允许访问IP地址</label></td>
		<td align="left" colspan="3"> 
		    <textarea id="addressPerms" name="addressPerms" rows="6" cols="46" class="k-textbox" style="width:485px;" >${dataSet.addressPerms}</textarea>
			<br><span style="color:red;">（需要独立提供对外数据服务时才设定。）</span>
			<br>允许使用*为通配符，多个地址之间用半角的逗号“,”隔开。
			<br>例如：192.168.*.*，那么192.168.1.100及192.168.142.100都可访问该服务。
            <br>192.168.142.*，那么192.168.1.100不能访问但192.168.142.100可访问该服务。
			<br>如果配置成192.168.1.*,192.168.142.*，那么192.168.1.100及192.168.142.100均可访问该服务。
		</td>
	</tr> -->
 	<tr>
 		<td colspan="4">
		<div class="portlet box blue-hoki" style="margin:0 0">
		    <div class="portlet-title">
		        <div class="caption">
		            <i class="fa fa-plus-circle"></i>高级 </div>
		        <div class="tools">
		            <a href="javascript:;" class="expand" style=""> </a>
		        </div>
		    </div>
		    <div class="portlet-body" style="display:none;padding:3px 3px">
		    	<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
		        <c:if test="${!empty treeModels}">
				  <tr>
				    <td width="12%" align="left"><label for="nodeId" class="required">分类</label></td>
				    <td width="38%" align="left">
				      <select id="nodeId" name="nodeId">
					       <option value="">----请选择----</option>
						   <c:forEach items="${treeModels}" var="treeModel">
				           <option value="${treeModel.id}">${treeModel.name}</option>
						      <c:forEach items="${treeModel.children}" var="child">
				              <option value="${child.id}">&nbsp;&nbsp;---->${child.name}</option>
							      <c:forEach items="${child.children}" var="cd">
				                  <option value="${cd.id}">&nbsp;&nbsp;&nbsp;&nbsp;-------->${cd.name}</option>
								    <c:forEach items="${cd.children}" var="c">
				                    <option value="${c.id}">&nbsp;&nbsp;&nbsp;&nbsp;------------>${c.name}</option>
									<c:forEach items="${c.children}" var="x">
									  <option value="${x.id}">&nbsp;&nbsp;&nbsp;&nbsp;---------------->${x.name}</option>
									</c:forEach>
						            </c:forEach>
						          </c:forEach>
						      </c:forEach>
						   </c:forEach>
					  </select>
					  <script type="text/javascript">
					  	   var nodeId = "${dataSet.nodeId}" || "${param.nodeId}";
					       document.getElementById("nodeId").value = nodeId;
					  </script>
				    </td>
					<td width="12%" align="left">&nbsp;</td>
				    <td width="38%" align="left">&nbsp;</td>
				  </tr>
				 </c:if>

				  <tr>
				    <td width="12%" align="left"><label for="shareFlag" class="required">是否共享</label></td>
				    <td width="38%" align="left">
				      <select id="shareFlag" name="shareFlag">
					    <option value="">----请选择----</option>
						<option value="Y">是</option>
						<option value="N">否</option>
					  </select>
					  <script type="text/javascript">
					       document.getElementById("shareFlag").value="${dataSet.shareFlag}";
					  </script>
				    </td>
					<td width="12%" align="left"><label for="accessType" class="required">访问类型</label></td>
				    <td width="38%" align="left">
				      <select id="accessType" name="accessType">
					    <option value="PRV">私有</option>
						<option value="PUB">公开</option>
					  </select>
					  <script type="text/javascript">
					       document.getElementById("accessType").value="${dataSet.accessType}";
					  </script>   
				    </td>
				  </tr>

				  <tr>
				    <td width="12%" align="left"><label for="distinctFlag" class="required">记录唯一</label></td>
				    <td width="38%" align="left">
				      <select id="distinctFlag" name="distinctFlag">
					    <option value="">----请选择----</option>
						<option value="Y">是</option>
						<option value="N">否</option>
					  </select>
					  <script type="text/javascript">
					       document.getElementById("distinctFlag").value="${dataSet.distinctFlag}";
					  </script>
				    </td>
					<td width="12%" align="left"><label for="locked" class="required">是否有效</label></td>
				    <td width="38%" align="left">
					  <select id="locked" name="locked">
						<option value="0">是</option>
						<option value="1">否</option>
					  </select>
					  <script type="text/javascript">
					       document.getElementById("locked").value="${dataSet.locked}";
					  </script>
					</td>
				   </tr>

				  <tr>
				    <td width="12%" align="left"><label for="cacheFlag" class="required">缓存结果集</label></td>
				    <td width="38%" align="left">
				      <select id="cacheFlag" name="cacheFlag">
					    <option value="">----请选择----</option>
						<option value="Y">是</option>
						<option value="N">否</option>
					  </select>
					  <script type="text/javascript">
					       document.getElementById("cacheFlag").value="${dataSet.cacheFlag}";
					  </script>
				    </td>
					<td width="12%" align="left"><label for="locked" class="required">是否动态结果集</label></td>
				    <td width="38%" align="left">
					  <select id="dynamicFlag" name="dynamicFlag">
					    <option value="">----请选择----</option>
						<option value="Y">是</option>
						<option value="N">否</option>
					  </select>
					  <script type="text/javascript">
					       document.getElementById("dynamicFlag").value="${dataSet.dynamicFlag}";
					  </script>
					</td>
				   </tr>
				   
				  <tr>
					<td width="12%" align="left"><label for="locked" class="required">动态数据集</label></td>
				    <td width="38%" align="left">
					  <select id="dynamicDataSet" name="dynamicDataSet">
					    <option value="">----请选择----</option>
						<option value="Y">是</option>
						<option value="N">否</option>
					  </select>
					  <script type="text/javascript">
					       document.getElementById("dynamicDataSet").value="${dataSet.dynamicDataSet}";
					  </script>
					</td>
				   </tr>
				   <tr>
				    <td width="12%" align="left"><label for="exportTableName" class="required">导出目标表&nbsp;</label></td>
				    <td width="88%" align="left" colspan="3">
					    
					    <input id="exportTableName" name="exportTableName" type="text" class="k-textbox"  
					           data-bind="value: exportTableName" style="width:485px;"
					           value="${dataSet.exportTableName}" />
					    <span class="k-invalid-msg" data-for="exportTableName"></span>
				    </td>
				   </tr>
				    <tr>
				    <td width="12%" align="left"><label for="valiDataSet" class="required">验证数据集&nbsp;</label></td>
				    <td width="88%" align="left" colspan="3">
					    <input id="valiDataSet" type="text" class="k-textbox extText"  
					           style="width:485px;" />
					    <span class="k-invalid-msg" data-for="valiDataSet"></span>
				    </td>
				   </tr>
				    <tr>
				    <td width="12%" align="left"><label for="url" class="required">远程地址&nbsp;</label></td>
				    <td width="88%" align="left" colspan="3">
					    <input id="url" type="text" class="k-textbox extText"  
					           style="width:485px;" />
					    <span class="k-invalid-msg" data-for="url"></span>
				    </td>
				   </tr>
				   <tr>
				    <td width="12%" align="left"><label for="description" class="required">可执行库&nbsp;</label></td>
				    <td width="88%" align="left" colspan="3">
					    <input type="hidden" id="executeDatabaseIds" name="executeDatabaseIds" value="${dataSet.executeDatabaseIds}">
						<textarea  id="database_names" name="database_names" rows="6" cols="46" class="k-textbox" 
						           style="height:60px;width:485px;" onclick="javascript:openDB();" >${database_names}</textarea>&nbsp;
						<a href="#" onclick="javascript:openDB();">
							<img src="<%=request.getContextPath()%>/images/search_results.gif" border="0">
						</a>  
					    <span class="k-invalid-msg" data-for="database_names"></span>
				    </td>
				   </tr>

				   <tr>
						<td width="12%" align="left"><label for="perms" class="required">访问角色</label></td>
						<td align="left" colspan="3"> 
							<input type="hidden" id="perms" name="perms" value="${dataSet.perms}">
				            <textarea  id="x_roles_name" name="x_roles_name" rows="6" cols="46" class="k-textbox" readonly style="width:485px;" 
						    >${x_role_names}</textarea>
							<input type="button" name="button" value="添加" class="k-button" 
							       onclick="javascript:selectRole('iForm', 'perms','x_roles_name');"> 
							&nbsp;
							<input type="button" name="button" value="清空" class="k-button" 
							       onclick="javascript:clearSelected('perms','x_roles_name');">
							<br><span style="color:red;">（需要独立提供对外数据服务时才设定。）</span>
						</td>
					</tr>
				</table>
		    </div>
		</div>
	</td>
 	</tr>
   <tr>
        <td colspan="4" align="center" valign="bottom" height="30">&nbsp;
        <div>
          <button type="button" id="iconButton1"  class="k-button k-primary" style="width: 90px" 
		          onclick="javascript:saveData();">保存</button>
		  <c:if test="${!empty dataSet}">
		  <button type="button" id="iconButton2"  class="k-button k-primary" style="width: 90px" 
		          onclick="javascript:saveDataAs();">另存</button>
		  </c:if>
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