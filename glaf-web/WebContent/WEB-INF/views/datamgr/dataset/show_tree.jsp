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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据集列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<style type="text/css">
.x-searchtext {
	background-color:#fff;border:1px solid #d3d3d3;color:#666; padding:2px 2px; line-height:18px ; height:18px;font-size: 13px;
} 
.btnGray {
	padding: 5px 20px; border-radius: 3px; border: 1px solid rgb(170, 170, 170); text-align: center; color: rgb(0, 0, 0); overflow: visible; display: inline-block; cursor: pointer; background-color: rgb(244, 244, 244); -moz-border-radius: 3px; -webkit-border-radius: 3px;
}
.btnGray:hover {
	box-shadow: 0px 1px 1px #ccc; background-color: rgb(248, 248, 248); -moz-box-shadow: 0 1px 1px #ccc; -webkit-box-shadow: 0 1px 1px #ccc;
}
.btnGrayS {
	margin: 0px; padding: 3px 5px; border-radius: 3px; border: 1px solid rgb(170, 170, 170); text-align: center; color: rgb(0, 0, 0); overflow: visible; display: inline-block; cursor: pointer; background-color: rgb(244, 244, 244); -moz-border-radius: 3px; -webkit-border-radius: 3px;
}
.btnGrayS:hover {
	box-shadow: 0px 1px 1px #ccc; background-color: rgb(248, 248, 248); -moz-box-shadow: 0 1px 1px #ccc; -webkit-box-shadow: 0 1px 1px #ccc;
}
.btnGrayS:hover {
	box-shadow: 0px 1px 1px #ccc; background-color: rgb(248, 248, 248); -moz-box-shadow: 0 1px 1px #ccc; -webkit-box-shadow: 0 1px 1px #ccc;
}

.ztree li span.button.tree_folder_ico_open{margin-right:2px; background: url(${contextPath}/images/folder-open.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_folder_ico_close{margin-right:2px; background: url(${contextPath}/images/folder.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_folder_ico_docu{margin-right:2px; background: url(${contextPath}/images/folder.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}

.ztree li span.button.tree_leaf_ico_open{margin-right:2px; background: url(${contextPath}/images/orm.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_leaf_ico_close{margin-right:2px; background: url(${contextPath}/images/orm.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.tree_leaf_ico_docu{margin-right:2px; background: url(${contextPath}/images/orm.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}

</style>
<script type="text/javascript">

    var setting = {
		async: {
			enable: true,
			url: getUrl,
			dataFilter: filter
		},
		check: {
			enable: true,
			chkboxType:{ "Y" : "", "N" : "" }
		}
	};

	function getUrl(treeId, treeNode) {
		if(treeNode != null){
		    var param = "&nodeId="+treeNode.id;
		    return "<%=request.getContextPath()%>/rs/dataset/treeJson?keywordsLike_base64=${keywordsLike_base64}&selected=${selected}"+param;
		}
		return "<%=request.getContextPath()%>/rs/dataset/treeJson?keywordsLike_base64=${keywordsLike_base64}&nodeCode=${nodeCode}&selected=${selected}";
	}

  	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			if(childNodes[i].iconCls == 'folder'){
			  childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			  childNodes[i].icon="<%=request.getContextPath()%>/images/folder.png";
			}
			if(childNodes[i].iconCls == 'leaf'){
			  childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			  childNodes[i].icon="<%=request.getContextPath()%>/images/basic.png";
			}
		}
		return childNodes;
	}

    jQuery(document).ready(function(){
		  jQuery.fn.zTree.init(jQuery("#myTree"), setting);
		  //var zTree = $.fn.zTree.getZTreeObj("myTree");
		  //zTree.expandAll(true);
	});


	function setFormData(){
		var zTree = $.fn.zTree.getZTreeObj("myTree");
        var selectedNodes  = zTree.getCheckedNodes(true);

        var sx = '';  
		var name='';
		var sxv = '';  
		var value='';
        for(var i=0; i<selectedNodes.length; i++){  
            if (sx != ''){ 
				sx += ','; 
			}
			if (sxv != ''){ 
				sxv += ','; 
			}
			name = selectedNodes[i].name;
            sx += name;  
			value = selectedNodes[i].id;
            sxv += value;  
        }  

		var parent_window = getOpener();
		var x_elementId = parent_window.document.getElementById("${elementId}");
        var x_element_name = parent_window.document.getElementById("${elementName}");
		if(confirm("您确定选择'"+sx+"'吗？")){
			x_elementId.value=sxv;
			x_element_name.value=sx;
			try{
				//alert( window.opener );
                window.opener.switchDataset();
			}catch(exe){
			}
			window.close();
		}
	}

	function searchData(){
        document.iForm.submit();
	}

</script>
 
</head>
<body>

<div style="margin:0;"></div>  

<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:true,border:true" style="height:42px"> 
    <div class="toolbar-backgroud"> 
		<form id="iForm" name="iForm" method="post" 
                 action="${contextPath}/mx/dataset/showTree?elementId=${elementId}&elementName=${elementName}&nodeCode=${nodeCode}&selected=${selected}">
		 <span class="x_content_title">数据集列表</span>
	     <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" 
	       onclick="javascript:setFormData();" >确定</a>
         <input id="keywordsLike" name="keywordsLike" type="text" class="x-searchtext"  
	            style="width:185px;" value="${keywordsLike}">
	     <button type="button" id="searchButton" class="btn btnGray" style="width: 90px" 
	             onclick="javascript:searchData();">查找</button>
	    </form>
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
	<ul id="myTree" class="ztree"></ul>
</div>
</div>

</body>
</html>