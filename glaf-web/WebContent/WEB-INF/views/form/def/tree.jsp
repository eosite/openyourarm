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
<title>定义平台菜单</title>
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
				url: "<%=request.getContextPath()%>/mx/form/def/treeJson?databaseId=${databaseId}&searchWord_enc=${searchWord_enc}",
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

	function chooseMenus(){
		var zTree = $.fn.zTree.getZTreeObj("myTree");
        var selectedNodes  = zTree.getCheckedNodes(true);

        var sx = '';  
		var id='';
        for(var i=0; i<selectedNodes.length; i++){  
			id = selectedNodes[i].id;
            sx = selectedNodes[i].text;  
        }  
        
		var databaseId = document.getElementById("databaseId").value;
		if(databaseId =="" || databaseId == "0"){
			alert("请选择一个数据库");
			return;
		}

		var parent_window = getOpener();
		parent_window.document.getElementById("${elementId}").value = id;
        parent_window.document.getElementById("${elementName}").value = sx;
		parent_window.document.getElementById("databaseId").value="${databaseId}";
		parent_window.document.getElementById("systemFlag").value="JAVA";
		if(parent_window.document.getElementById("name").value==""){
			parent_window.document.getElementById("name").value=sx;
		}
		if(parent_window.document.getElementById("code").value==""){
			parent_window.document.getElementById("code").value=id;
		}
        //alert(id + "   "+ sx);
		window.close();
	}

	function switchDB(){
		var databaseId=document.getElementById("databaseId").value;
		document.iForm.action="<%=request.getContextPath()%>/mx/form/def?databaseId="+databaseId+"&elementId=${elementId}&elementName=${elementName}";
		document.iForm.submit();
	}

	function search(){
		var databaseId=document.getElementById("databaseId").value;
		var searchWord=document.getElementById("searchWord").value;
		document.iForm.action="<%=request.getContextPath()%>/mx/form/def?databaseId="+databaseId+"&elementId=${elementId}&elementName=${elementName}";
		document.iForm.submit();
	}

</script>
</head>

<body style="margin:0px;"> 
<form id="iForm" name="iForm" method="post">
<div class="toolbar-backgroud"> 
<span class="x_content_title">
选择定义平台菜单&nbsp;
    <select id="databaseId" name="databaseId" onchange="javascript:switchDB();">
	   <option value="">----请选择----<option>
	   <c:forEach var="d" items="${databases}">
		<option value="${d.id}">${d.title}</option>
	   </c:forEach>
    </select>&nbsp;&nbsp;
	<script type="text/javascript">
	    document.getElementById("databaseId").value="${database.id}";
	</script>
</span>
&nbsp;
<input type="text" id="searchWord" name="searchWord" class="x-searchtext" value="${searchWord}"  >
&nbsp;
<input type="button" name="save" value=" 查找 " class="button" onclick="javascript:search();"> 
&nbsp;
<input type="button" name="save" value=" 确定 " class="button" onclick="javascript:chooseMenus();"> 
</div> 
<div>
	<script type="text/javascript">
	    document.getElementById("databaseId").value="${databaseId}";
	</script>
	<ul id="myTree" class="ztree"></ul> 
</div>
</form>
</body>
</html>