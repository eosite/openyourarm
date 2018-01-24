<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DmsPfile</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/layer/layer.min.js"></script>
</head>
<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";
	var zTreeObj,
		setting = {
			view: {
				selectedMulti: false
			}
		};
	//检查上传文件
	function onUpload(e) {
	    var files = e.files;
	    $.each(files, function () {
	        if (this.extension.toLowerCase() != ".txt") {
	            alert("请上传TXT文件")
	            e.preventDefault();
	        }
	    });
	}
	function onError(e){
		alert('上传解析异常');
		e.preventDefault();
	}
	function onSuccess(e) {
		var resultJson = JSON.parse(e.XMLHttpRequest.responseText);
		if(resultJson.statusCode == 500){
			alert('上传异常错误');
			return ;
		}
		var zTreeNodes = resultJson;
		zTreeObj = $.fn.zTree.init($("#tree"), setting, zTreeNodes);
	}
	jQuery(document).ready(function() {
		    $("#files").kendoUpload({
		    	async: {
		            saveUrl: "${contextPath}/mx/isdp/cell/file/saveUploadNode?nodeId=${nodeId}"
		        },
		    	multiple: false,
		    	success: onSuccess,
		    	showFileList: false,
		    	upload: onUpload,
		    	error: onError
	        });
	});
	function colseWindow(){
		 var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引 
		 parent.layer.close(index); //执行关闭 
	}
	//引入
	function save(){
		if(!zTreeObj){
			alert('请上传资料目录');
			return;
		}
		if(save.time ==  1){
			return ;
		}
		save.time = 1 ;
		var nodes = zTreeObj.getNodes();
		var isNew = document.getElementById('isNew').checked;
		var nodeId = document.getElementById('nodeId').value ;
		var params = {'nodeId':nodeId,'isNew':isNew,'nodeAarray':JSON.stringify(nodes)} ;
		jQuery.ajax({
			   type: "POST",
			   url: '${contextPath}/mx/isdp/cell/file/saveImportNodes',
			   data: params,
			   dataType:  'json',
			   error: function(data){
				   alert('服务器处理错误！');
				   save.time = 0 ;
			   },
			   success: function(data){
				   if(data != null && data.statusCode == 500){
					   alert('服务器处理错误！');
					   save.time = 0 ;
				   } else {
					   alert('操作成功完成！');
					   window.parent.ztreeObj.reAsyncChildNodes(null, "refresh");
					   var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引 
					   parent.layer.close(index); //执行关闭 
				   }
			   }
		 });
		
	}
	
</script>
<body>
<input type=hidden id="nodeId" value="${nodeId}" >

<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:false,border:false" style="height: 58px;" > 
	  <div class="easyui-layout" data-options="fit:true">  
			  <div data-options="region:'west',split:false,border:false" style="width: 55%;"> 
				    <div class="toolbar-backgroud" style="height:48px;line-height: 48px;"> 
				    	<input type="checkbox" id="isNew" >新建所有文件分类 &nbsp;&nbsp;&nbsp;
						<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" id="save" onclick="javascript:save();" >引入</a> 
						<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-back'" onclick="javascript:colseWindow();">关闭</a>
				    </div> 
			  </div>
			  <div data-options="region:'center',border:false,cache:true"  >
			    	<div class="toolbar-backgroud" style="height:48px;line-height: 48px;"> 
						<input type="file" name="files" id="files"  accept="text/plain"/>
					</div>
	  		  </div>
	  </div>
  </div>

  <div data-options="region:'center',border:false,cache:true" >
 	 	<div>
  			<ul id="tree" class="ztree" style="width:230px; overflow:auto;"></ul>
  		</div>
  </div>
</div>
</body>
</html>