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
<title>应用模块</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script type="text/javascript">

	 var setting = {
			async: {
				enable: true,
				url: "<%=request.getContextPath()%>/mx/identity/menu/menuJson",
				dataFilter: filter
			},
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "all"
			},
			callback: {
				onClick: zTreeOnClick
			}
		};
  
  	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
            //if(childNodes[i].iconCls=='icon-user'){
			   // childNodes[i].icon="<%=request.getContextPath()%>/images/user.gif";
		    //}
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

	function chooseMenu(){
		var zTree = $.fn.zTree.getZTreeObj("myTree");
        var selectedNodes  = zTree.getCheckedNodes(true);
        var parent_window = getOpener();
		if(parent_window != null){
			var elementId = parent_window.document.getElementById("${elementId}");
			if(elementId != null && elementId != "" && elementId != "undefined"){
				if(selectedNodes[0].url != null && selectedNodes[0].url !="" && selectedNodes[0].url != "undefined"){
				    elementId.value = selectedNodes[0].url;
				} else {
                    alert("请选择有链接地址的菜单！");
				}
			} 
		} else {
			if(selectedNodes[0].url != null && selectedNodes[0].url !="" && selectedNodes[0].url != "undefined"){
				//alert(selectedNodes[0].url);
				var obj = document.getElementById("linkDiv");
				obj.style.display = 'block';
				document.getElementById("link").value=selectedNodes[0].url;
			} else {
				alert("请选择有链接地址的菜单！");
			}
		}
	}


	function openWin(){
       window.open("<%=request.getContextPath()%>"+document.getElementById("link").value);
	}

</script>
</head>

<body style="margin:0px;"> 
<form id="iForm" name="iForm" method="post">
<input type="hidden" id="nodeIds" name="nodeIds">
<div class="toolbar-backgroud"> 
<span class="x_content_title">应用模块</span>
&nbsp;
<input type="button" name="save" value=" 确 定 " class="btnGray" onclick="javascript:chooseMenu();"> 
</div> 
<div>
	<ul id="myTree" class="ztree"></ul> 
</div>
<div id="linkDiv" style="margin-top:10px; margin-left:10px; display:none;valign:center" >
   <textarea id="link" name="link" rows="6" cols="48" class="x-textarea"></textarea>
   <br><br>
   <input id="test" type="button" value="测试" onclick="javascript:openWin();" class="btnGray">
</div>
<br><br>
</form>
</body>
</html>