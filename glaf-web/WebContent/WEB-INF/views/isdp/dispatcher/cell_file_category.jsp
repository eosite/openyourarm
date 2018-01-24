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
		var setting = {
			async: {
				enable: true,
				url:"${contextPath}/rs/isdp/treeFolder/dataLibTreeJson",
				autoParam:["id","id=treetopIndexId"],
				//otherParam:{"otherParam":"zTreeAsyncTest"},
				dataFilter: filter
			},
			callback: {
				//beforeClick: zTreeBeforeClick,
				onExpand: zTreeOnExpand,
				onClick: zTreeOnClick
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
			//jQuery('#iForm').form('load', treeNode);
		}

		function updateNode(treeId, treeNode){
			var zTree = $.fn.zTree.getZTreeObj(treeId);
			zTree.setting.view.fontCss["color"] = "#0000ff";
			//treeNode.iconSkin = "icon03" ;
			zTree.updateNode(treeNode);
		}

		function zTreeBeforeClick(treeId, treeNode, clickFlag) {
           
		}

		function zTreeOnClick(event, treeId, treeNode, clickFlag) {
             //alert(treeNode.id);
			 //updateNode(treeId, treeNode);
			 //loadData('data/datagrid_data32.json');
			 jQuery('#iForm').form('load', treeNode);
		}

		function loadData(url){
		  $.get(url,{name:'mike'},function(data){
		      //var text = JSON.stringify(data); 
              //alert(text);
			  $('#myGridData').datagrid('loadData', data);
		  },'json');
	  }


		$(document).ready(function(){
			ztreeObj = $.fn.zTree.init($("#myTree"), setting);
		});
		
		//同级增加   1代表同级   2代表下级  3代表修改
		function addNode(type){
			//获取选中的节点 ztreeObj.getSelectedNodes()
			var nodeId  = "-1" ;
			var selectedNode = ztreeObj.getSelectedNodes() ;
        	if(selectedNode.length>0){
        		switch (type) {
					case 1:
						nodeId = selectedNode[0].parentId ;
						break;
					default:
						nodeId = selectedNode[0].indexId ;
						break;
				}
        	};
			var link = '${contextPath}/mx/isdp/cell/file/addNode?nodeType='+type+'&nodeId=' + nodeId;
    		jQuery.layer({
    			type: 2,
    			maxmin: true,
    			shadeClose: true,
    			title: "文件分类信息",
    			closeBtn: [0, true],
    			shade: [0.8, '#000'],
    			border: [10, 0.3, '#000'],
    			offset: ['20px',''],
    			fadeIn: 100,
    			area: ['780px', (jQuery(window).height() - 50) +'px'],
                iframe: {src: link}
    		});
		}
		
		function deleteNode(){
			var selectedNode = ztreeObj.getSelectedNodes() ;
			if(selectedNode.length <= 0){
			     alert("请选择其中一条记录。");
				 return;
			}
			var indexId = selectedNode[0].indexId ;
			var id = selectedNode[0]._oid_ ;
			 if(confirm("数据删除后不能恢复，确定删除吗？")){
					var link = '${contextPath}/mx/isdp/cell/file/deleteNode?id='+id+'&indexId='+indexId ;
					jQuery.ajax({
						   type: "get",
						   url: link,
						   dataType:  'json',
						   error: function(data){
							   alert('服务器处理错误！');
						   },
						   success: function(data){
							   console.log(data);
							   if(data != null && data.statusCode == 500){
								   alert('此资料分类或它的子节点已挂文件，不能删除');
							   } else {
								   alert('操作成功完成！');
								   //动态删除节点
								   ztreeObj.removeNode(selectedNode[0]);
							   }
						   }
					});
			 }
		}
		
		function dynamicAddNode(type,newNodes){
			var node = ztreeObj.getSelectedNodes()[0] ;
			if(type=="2"){
				newNodes.name = newNodes.indexName ;
				ztreeObj.addNodes(node, newNodes);
			}else if(type == "1"){
				newNodes.name = newNodes.indexName ;
				ztreeObj.addNodes(node.getParentNode(), newNodes);
			}else if(type == "3"){
				for(var p in newNodes){
					node[p] = newNodes[p];
				}
				jQuery('#iForm').form('load', node);
			}
		}
		function download(){
			var link = "${contextPath}/rs/isdp/treeFolder/download";
			window.open(link);
		}
		
		function moveNode(){
			var link = '${contextPath}/mx/isdp/cell/file/moveNode';
    		jQuery.layer({
    			type: 2,
    			maxmin: true,
    			shadeClose: true,
    			title: "移动树节点",
    			closeBtn: [0, true],
    			shade: [0.8, '#000'],
    			border: [10, 0.3, '#000'],
    			offset: ['20px',''],
    			fadeIn: 100,
    			area: ['780px', (jQuery(window).height() - 50) +'px'],
                iframe: {src: link}
    		});
		}
		function uploadNode(){
			//如果ztreeObj未有选中节点  默认选中第一个节点
			var selectedNodes = ztreeObj.getSelectedNodes();
			var node ;
			if(selectedNodes.length <= 0){
				var nodes = ztreeObj.getNodes();
				if (nodes.length>0) {
					node = nodes[0] ;
					ztreeObj.selectNode(node);
				}
			}else{
				node = selectedNodes[0];
			}
			var nodeId = 0 ;
			if(node){
				nodeId = node.id ;
			}
			var link = '${contextPath}/mx/isdp/cell/file/uploadNode?nodeId='+nodeId;
    		jQuery.layer({
    			type: 2,
    			maxmin: true,
    			shadeClose: true,
    			title: "引入文件分类",
    			closeBtn: [0, true],
    			shade: [0.8, '#000'],
    			border: [10, 0.3, '#000'],
    			offset: ['20px',''],
    			fadeIn: 100,
    			area: ['780px', (jQuery(window).height() - 50) +'px'],
                iframe: {src: link}
    		});
		}
	</script>
</head>
<body>  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  

    <div data-options="region:'west',split:true" style="width:280px;">
	  <div class="easyui-layout" data-options="fit:true">  
          <div data-options="region:'north',split:true,border:true" style="height:72px">

			<div style="background:#fafafa;padding:5px;border:1px solid #ddd">  
             <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addNode(1)">同级增加</a>
			 <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addNode(2)">增加下级</a>
			 <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="addNode(3)">修改</a>
			 <br>
               
			 <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteNode()">删除</a> 
             <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-move',plain:true" onclick="moveNode()">移动</a> 
			 <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-imp',plain:true" onclick="uploadNode()">导入</a> 
			 <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-exp',plain:true" onclick="download()">导出</a> 
             </div>  
			</div>

			 <div data-options="region:'center',border:false">
			    <ul id="myTree" class="ztree"></ul>  
			 </div> 
			 
            <!--<div data-options="region:'south',split:true,border:true" style="height:30px"></div>--> 
        </div>  
	</div> 
	
    <div data-options="region:'center'">  
        <div class="easyui-layout" data-options="fit:true">  

           <div data-options="region:'center',split:true,border:true, fit:true">
		   
             <!-- <div style="background:#fafafa;padding:2px;border:1px solid #ddd;font-size:12px;height:30px"> 
			   <b>资料分类信息</b>
                
             </div> -->  

			 <form id="iForm" name="iForm" method="post">
 	   <div id="tt" class="easyui-tabs" tools="#tab-tools" style="width:695px;height:542px;">
		
		<div title="文件分类信息" style="padding:10px;"  closable="false">
			<table id="info" class="content-block" cellspacing="4" cellpadding="1" border="0" >
				<tbody>
				<tr>
					<td width="20%" class="table-title">文件分类名</td>
					<td width="30%" class="table-content">
					    <input class="x-text" type="text" name="indexName" size="50"></input>
					</td>
				</tr>
				<tr>
					<td width="20%" class="table-title">文件分类号</td>
					<td width="30%" class="table-content">
					    <input class="x-text" type="text" name="num" size="50"></input>
					</td>
				</tr>
				<tr>
					<td class="table-title">文件分类码</td>
					<td class="table-content">
					    <input class="x-text" type="text" name="filenum" size="50"></input>
					</td>
				</tr>
				<tr>
					<td class="table-title">著录项类别</td>
					<td class="table-content">
					     <select name="ztype" class="span3">
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
					     <select name="slevel" class="span3">
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
					    <select name="savetime" class="span3">
							 <option value=""></option>
							 <option value="永久">永久</option>
							 <option value="长期">长期</option>
							 <option value="短期">短期</option>
						</select>
					</td>
				</tr>
				 
				</tbody>
			</table>
		</div>
 
	</div>
	</form>
	   
			</div>  

            <div data-options="region:'south',split:true,border:true,align:'right'" style="height:30px">
			    <div style="text-align:right;valign:middle;padding-top:5px;">
				 
				</div>
			</div>  
        </div>  
    </div>  
    </div>  

 <div id="mm1" style="width:150px;"> 
	<div>插入分类</div>
	<div>新增下级</div>
 </div>

  <div id="mm3" style="width:150px;"> 
	<div>删除本节点</div>
	<div>删除本节点及子孙</div>
 </div>

</body>  
</html>  
<!--end of isdp/dispatcher/cell_setf_class.jsp-->