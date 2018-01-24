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
<title>Layout</title>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/icons.css">
<script type="text/javascript" src="${contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/layer/layer.min.js"></script>
<script type="text/javascript">
 		var ztreeObj ;
 		var ztreeObj2 ;
		var setting = {
			async: {
				enable: true,
				url:"${contextPath}/rs/isdp/treeFolder/dataLibTreeJson",
				//autoParam:["id=pid", "name=n", "level=lv"],
				//otherParam:{"otherParam":"zTreeAsyncTest"},
				dataFilter: filter
			},
			callback: {
				beforeClick: zTreeBeforeClick,
				//onExpand: zTreeOnExpand,
				//onClick: zTreeOnClick
			},
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType:  { "Y" : "", "N" : "" }
			}
		};
		var setting2 = {
				async: {
					enable: true,
					url:"${contextPath}/rs/isdp/treeFolder/dataLibTreeJson",
					autoParam:["id=pid", "name=n", "level=lv"],
					//otherParam:{"otherParam":"zTreeAsyncTest"},
					dataFilter: filter
				},
				callback: {
					beforeClick: zTreeBeforeClick,
					//onExpand: zTreeOnExpand,
					//onClick: zTreeOnClick
				}
			};

		function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			return childNodes;
		}

		function zTreeOnExpand(treeId, treeNode){
			 //updateNode(treeId, treeNode);
            //treeNode.icon="${contextPath}/scripts/ztree/css/zTreeStyle/img/diy/2.png";
			//alert(treeNode.icon);
		}

		function updateNode(treeId, treeNode){
			var zTree = $.fn.zTree.getZTreeObj(treeId);
			zTree.setting.view.fontCss["color"] = "#0000ff";
			//treeNode.iconSkin = "icon03" ;
			zTree.updateNode(treeNode);
		}

		function zTreeBeforeClick(treeId, treeNode, clickFlag) {
           
		}

		$(document).ready(function(){
			ztreeObj = $.fn.zTree.init($("#myTree"), setting);
			ztreeObj2 = $.fn.zTree.init($("#myTree2"), setting2);
		});
			//关闭
			function closeLayer(){
				 var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引 
				   parent.layer.close(index); //执行关闭 
			}
			//保存移动
			function saveMove(){
				var checkedNodes = ztreeObj.getCheckedNodes(true);
				if(checkedNodes.length<=0){
					alert('请勾选要移动的节点');
					return ;
				}
				var selectedNodes = ztreeObj2.getSelectedNodes();
				if(selectedNodes.length <=0){
					alert('请勾选移动到的目标节点');
					return ;
				}
				var selectedNode = selectedNodes[0] ;
				var checkedNodeIds = [] ;
				for(var i=0; i<checkedNodes.length ; i++){
					var cnode = checkedNodes[i] ;
					if(cnode.id == selectedNode.id){
						alert('相同节点，不需要移动');
						return;
					}
					checkedNodeIds.push (cnode.id);
				}
				
				var link = '${contextPath}/mx/isdp/cell/file/saveMove?checkedIds='+checkedNodeIds.join(',')+'&selectedId='+selectedNode.id ;
				jQuery.ajax({
					   type: "get",
					   url: link,
					   dataType:  'json',
					   error: function(data){
						   alert('服务器处理错误！');
					   },
					   success: function(data){
						   if(data != null && data.statusCode == 500 ){
							   alert('服务器处理错误！');
						   } else {
							   alert('操作成功完成！');
							   ztreeObj.reAsyncChildNodes(null, "refresh");
							   for (var i=0, l=checkedNodes.length; i < l; i++) {
								 	var checkedNode = checkedNodes[i]
								    var node = ztreeObj2.getNodeByParam("id", checkedNode.id, null);
								 	ztreeObj2.removeNode(node);
								}
							   console.log(selectedNode);
							   ztreeObj2.reAsyncChildNodes(selectedNode, "refresh");
							   window.parent.ztreeObj.reAsyncChildNodes(null, "refresh");
						   }
					   }
				});
			}
	 
	</script>
</head>
<body>  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  

    <div data-options="region:'west'" style="width:42%;">
	  <div class="easyui-layout" data-options="fit:true">  
	  		 <div data-options="region:'north',border:false">
			   选择移动节点  ：
			 </div> 
			 <div data-options="region:'center',border:false">
			    <ul id="myTree" class="ztree"></ul>  
			 </div> 
        </div>  
	</div> 
	 <div data-options="region:'center'" style="width:8%;"> 
    	<div class="easyui-layout" data-options="fit:true" >  
			 <div data-options="region:'center',border:false" >
			 <div style="line-height:400px;vertical-align: middle;height: 400px;">
			   &nbsp; &nbsp; &nbsp;&nbsp; &nbsp;  ======>>
			 </div>
			 </div> 
        </div>  
    </div>  
    <div data-options="region:'east'" style="width:42%;"> 
    	<div data-options="region:'north',border:false">
			    移动到 ：
			 </div> 
    	<div class="easyui-layout" data-options="fit:true">  
			 <div data-options="region:'center',border:false">
			    <ul id="myTree2" class="ztree"></ul>  
			 </div> 
        </div>  
    </div>  
   <div data-options="region:'south'"  style="height: 40px;">
   		<div class="easyui-layout" data-options="fit:true">  
			 <div data-options="region:'east',border:false" style="text-align: right;">
			    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="saveMove()">确定</a>
			 	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="closeLayer()">关闭</a>
			 </div> 
        </div>  
	</div> 
</body>  
</html>  
