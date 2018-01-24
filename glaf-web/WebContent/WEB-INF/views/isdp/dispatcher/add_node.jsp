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
<title>文件分类信息</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript" src="/yz/scripts/layer/layer.min.js"></script>
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

   function save(){
       var form = document.getElementById("iForm");
       var validator = jQuery("#iForm").data("kendoValidator");
       if (validator.validate()) {
		   var link = "${contextPath}/mx/isdp/cell/file/saveNode";
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
							   window.parent.dynamicAddNode(data.nodeType,JSON.parse(data.treeFolder));
							   var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引 
							   parent.layer.close(index); //执行关闭 
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
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="文件分类信息">&nbsp;文件分类信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">

  	<input class="k-textbox"  type="hidden" name="indexId" data-bind="value:indexId" ></input>
  	<input class="k-textbox"  type="hidden" name="parentId" data-bind="value:parentId" ></input>
  	<input class="k-textbox"  type="hidden" name="type" value="${nodeType}" ></input>
  	
 	   <div id="tt" class="easyui-tabs" tools="#tab-tools" style="width:695px;height: 100%">
		<div title="文件分类信息" style="padding:10px;"  closable="false">
			<table id="info" class="content-block" cellspacing="4" cellpadding="1" border="0" >
				<tbody>
				<tr>
					<td width="20%" class="table-title">文件分类名</td>
					<td width="30%" class="table-content">
					    <input class="k-textbox"  type="text" name="sindexName" data-bind="value:sindexName" size="50"></input>
					</td>
				</tr>
				<tr>
					<td width="20%" class="table-title">文件分类号</td>
					<td width="30%" class="table-content">
					    <input class="k-textbox"  type="text" name="num" data-bind="value:num" size="50"></input>
					</td>
				</tr>
				<tr>
					<td class="table-title">文件分类码</td>
					<td class="table-content">
					    <input class="k-textbox"  type="text" name="filenum" data-bind="value:filenum" size="50"></input>
					</td>
				</tr>
				<tr>
					<td class="table-title">著录项类别</td>
					<td class="table-content">
					     <select name="ztype" id="ztype" class="span3">
					      	<option value=""></option>
						     <option value="0">文书</option>
							 <option value="1">声像</option>
							 <option value="2">照片</option>
							 <option value="3">图纸</option>
							 <option value="4">其他</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="table-title">密级</td>
					<td class="table-content">
					     <select name="slevel" id="slevel" class="span3">
					      	<option value=""></option>
							 <option value="秘密">秘密</option>
							 <option value="机密">机密</option>
							 <option value="绝密">绝密</option>
							 <option value="公开">公开</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="table-title">保管期限</td>
					<td class="table-content">
					    <select name="savetime" id="savetime" class="span3">
							 <option value=""></option>
							 <option value="永久">永久</option>
							 <option value="长期">长期</option>
							 <option value="短期">短期</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="table-title">分类描述</td>
					<td class="table-content">
					    <textarea class="k-textbox"  type="text" name="content"  rows="5" >${treeFolder.content}</textarea>
					</td>
				</tr>
				<tr>
			        	<td colspan="4" align="center" valign="bottom" height="30">&nbsp;
					        <div>
					          <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>
							</div>
						</td>
			      </tr>
				</tbody>
			</table>
		</div>
	</div>
</form>
</div>     
<script>
    jQuery(document).ready(function() {
    	jQuery("#iconButton").kendoButton({
            spriteCssClass: "k-icon"
      	});         
    	$('#ztype').val('${treeFolder.ztype}');
    	$('#slevel').val('${treeFolder.slevel}');
    	$('#savetime').val('${treeFolder.savetime}');
    	
    	var viewModel = kendo.observable({
    		'parentId' : '${treeFolder.parentId}',
    		'indexId' : '${treeFolder.indexId}',
     		'sindexName' : '${treeFolder.sindexName}',
     		'num' : '${treeFolder.num}',
     		'filenum' : '${treeFolder.filenum}'
   	 	});
    	kendo.bind(jQuery("#iForm"), viewModel);
    });
</script>
</body>
</html>