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
<title>定义平台基础数据</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css"/>
<link href="<%=request.getContextPath()%>/scripts/artDialog/skins/default.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/artDialog.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/plugins/iframeTools.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<style type="text/css">
#rMenu {position:absolute; visibility:hidden; background-color: #c9c9c9;text-align: left;padding: 2px;}
#rMenu ul{padding:0px;height: 100%;margin:2px;}
#rMenu ul li{width:68px;height:18px;margin: 1px 0;padding: 0 2px;cursor: pointer;list-style: none outside none;background-color: #FFF;}
</style>
<script type="text/javascript">

	var treeObj,rMenu;
	var setting = {
		view: {
			showIcon:true,
			showLine:true,
			showTitle:false,
			selectedMulti: false
		},
		async: {
			enable: true,
			url: "<%=request.getContextPath()%>/rs/form/formDictTree/allTreeJson"
		},
		simpleData:{
			enable:true
		},
		callback: {
			onClick: zTreeOnClick,
			onAsyncSuccess: zTreeOnAsyncSuccess,
			onRightClick: OnRightClick
		}
	};
	
	function OnRightClick(event, treeId, treeNode) {
		if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
			treeObj.cancelSelectedNode();
			showRMenu("root", event.clientX, event.clientY);
		} else if (treeNode && !treeNode.noR) {
			treeObj.selectNode(treeNode);
			showRMenu("node", event.clientX, event.clientY);
		}
	}
	
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	   
	};

	function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		if(treeNode.level===0){
			$("#currentAddBtn").attr("disabled",true);
		}else{
			$("#currentAddBtn").attr("disabled",false);
			
	    	var nodes = treeObj.getSelectedNodes();
	    	if(nodes.length>1){
	    		$("#modifyBtn").attr("disabled",true);
	    	}else{
	    		$("#modifyBtn").attr("disabled",false);
	    	}
		}
		
		parent.mainFrame.document.location.href="<%=request.getContextPath()%>/mx/form/formDictory/showList?parent="+treeNode.id;
	}

    $(document).ready(function(){
		$.fn.zTree.init($("#myTree"), setting);
		treeObj = $.fn.zTree.getZTreeObj("myTree");
		rMenu = $("#rMenu");
	});
    
    function showRMenu(type, x, y) {
		$("#rMenu ul").show();
		if (type=="root") {
			$("#m_del").hide();
			$("#m_check").hide();
			$("#m_unCheck").hide();
		} else {
			$("#m_del").show();
			$("#m_check").show();
			$("#m_unCheck").show();
		}
		rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});

		$("body").bind("mousedown", onBodyMouseDown);
	}
	function hideRMenu() {
		if (rMenu) rMenu.css({"visibility": "hidden"});
		$("body").unbind("mousedown", onBodyMouseDown);
	}
	function onBodyMouseDown(event){
		if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
			rMenu.css({"visibility" : "hidden"});
		}
	}

	//增加
    function add(type){
    	hideRMenu();

    	var nodes = treeObj.getSelectedNodes();
    	if(nodes.length===0 || nodes.length>1){
    		alert("请选择一个节点");
    		return;
    	}
    	var node = nodes[0];
    	
    	var parentId = node.parentId;
    	if(type===1){
    		parentId = node.id;
    	}
    	
    	
    	var url="formDictTree/prepareAdd?parent="+parentId;
		var link = "<%=request.getContextPath()%>/mx/form/"+url;
		var width=580;
		var height=420;
		var scroll="yes";
		parent.mainFrame.art.dialog.open(link, { 
			height: height, 
			width: width, 
			title: "添加字典目录", 
			lock: false, 
			scrollbars:"no",
			close:function(){
				window.location.href = window.location.href;
			}
		}, false);
    }
    
    //修改
    function modify(){
    	hideRMenu();

    	var nodes = treeObj.getSelectedNodes();
    	if(nodes.length==0 || nodes.length>1){
    		alert("请选择一个节点");
    		return;
    	}
    	var node = nodes[0];
    	
    	var url="formDictTree/prepareModify?id="+node.id;
		var link = "<%=request.getContextPath()%>/mx/form/"+url;
		var width=580;
		var height=420;
		var scroll="yes";
		parent.mainFrame.art.dialog.open(link, { 
			height: height, 
			width: width, 
			title: "添加字典目录", 
			lock: false, 
			scrollbars:"no",
			close:function(){
				window.location.href = window.location.href;
			}
		}, false);
    }
    
    //删除
    function del(){
    	hideRMenu();
    	
    	if(!confirm("是否删除所选择的数据以及其下层数据？")){
    		return ;
    	}
    	
    	var nodes = treeObj.getSelectedNodes();
    	var node;
    	if(nodes && nodes.length>0){
    		node = nodes[0];
    	}
    	
    	$.post('${pageContext.request.contextPath}/rs/form/formDictTree/batchDelete',{"id":node.id},function(data){
    		if(data.message){
    			alert(data.message);
    			document.location.href = document.location.href;
    		}
    	},"json");
    }
</script>
</head>

<body style="margin:0px;">
	<div>
		<ul id="myTree" class="ztree"></ul> 
	</div>
	<div id="rMenu">
		<ul>
			<li id="m_add0" onclick="add(0);">同级增加</li>
			<li id="m_add1" onclick="add(1);">下级增加</li>
			<li id="m_modify" onclick="modify();">修改</li>
			<li id="m_del" onclick="del();">删除</li>
		</ul>
	</div>
</body>
</html>