<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>部门用户</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript">
var deleteObj = {};
	 var setting = {
			async: {
				enable: true,
				url: "<%=request.getContextPath()%>/mx/sys/user/deptUsersJson?deptId=${dept.id}",
				dataFilter: filter
			},
			check: {
				enable: true
			},
			callback: {
				onClick: zTreeOnClick,
				onCheck : function(e, treeId, node){
					if(!node.checked){
						var code = node.name;
						code = code.substring(0, code.indexOf(" "));
						deleteObj[code] = node;
					}
				}
			}
		};
  
  	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
            //if(childNodes[i].iconCls=='icon-user'){
			   // childNodes[i].icon="<%=request.getContextPath()%>/images/user.gif";
		    //}
            !(childNodes[i].iconCls=="icon-user") && (childNodes[i].nocheck = true);
		}
		return childNodes;
	}

	function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		//jQuery("#nodeId").val(treeNode.id);
		//loadData('<%=request.getContextPath()%>/mx/sys/application/json&parentId='+treeNode.id);
	}


    jQuery(document).ready(function(){
			jQuery.fn.zTree.init(jQuery("#myTree"), setting);
	});

	function saveDeptUsers(){
		var zTree = $.fn.zTree.getZTreeObj("myTree");
      
		var selectedNodes  = zTree.getCheckedNodes(true);

        var sx = '';  
		var code='';
        for(var i=0; i<selectedNodes.length; i++){  
            if (sx != ''){ 
				sx += ','; 
			}
			code = selectedNodes[i].name;
			code = code.substring(0, code.indexOf(" "));
            sx += code;
			if((code in deleteObj)){
				delete deleteObj[code];
			}
        }
        var dels = [];
        for(var k in deleteObj){
        	dels.push(k);
        }
        if(dels.length){
        	window.saveUsers(dels.join(','), null, function(){
        		window.saveUsers(sx, '${dept.id}');
        	});
        } else {
			window.saveUsers(sx, '${dept.id}');
        }
	}
	
	function saveUsers(sx, deptId, fn){
		jQuery.ajax({
			   type: "POST",
			   url: '<%=request.getContextPath()%>/mx/sys/user/saveDeptUsers',
			   dataType:  'json',
			   data: {
				   deptId : deptId,
				   userIds : sx
			   },
			   error: function(data){
				   alert('服务器处理错误！');
			   },
			   success: function(data){
				   if(fn){
					   fn();
				   } else {
					   if(data != null && data.message != null){
						   alert(data.message);
						   (window.opener || parent).location.reload();
					   } else {
						 alert('操作成功完成！');
						 (window.opener || parent).location.reload();
					   }
				   }
			   }
		 });
	}

</script>
</head>

<body style="margin:0;"> 
<form id="iForm" name="iForm" method="post">
<input type="hidden" id="userIds" name="userIds">
<div class="toolbar-backgroud"> 
<span class="x_content_title">查看部门【${dept.name}】的用户</span>
&nbsp;
<input type="button" name="save" value=" 保 存 " class="button" onclick="javascript:saveDeptUsers();"> 
</div> 
<div>
	<ul id="myTree" class="ztree"></ul> 
</div>
</form>
</body>
</html>