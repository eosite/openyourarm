<%@page contentType="text/html; charset=UTF-8" %>
<%
String theme = com.glaf.core.util.RequestUtils.getTheme(request);
request.setAttribute("theme", theme);
String webPath = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>平面地图</title>
<%@include file="../inc/script.jsp" %>
<script type="text/javascript">
	
	var setting = {
			view: {
				selectedMulti: false
			},
			<%--
			async:{
				url:"<%=request.getContextPath()%>/rs/treepInfoRest/getTreepinfoUnitList",
				enable:true,
				autoParam:["systemName","projId"]
			},--%>
			check:{
				enable:true,
				radioType:"all",
				chkStyle:"radio"
			},
			data:{
				simpleData:{
					enable:true
				}
			}
	};
	
	var nodeData = ${treeData};
	jQuery(document).ready(function(){
		jQuery.fn.zTree.init(jQuery("#myTree"), setting, nodeData);
	});

	jQuery(function(){
		document.definedMapActiveX.Html = this;  
		var definedMapActiveX = document.getElementById("definedMapActiveX");  
		definedMapActiveX.OpenMap();  
	});
	
	function save(){
		var mapId = jQuery("#mapId").val();
		if(mapId){
			var treeObj = jQuery.fn.zTree.getZTreeObj("myTree");
			var nodes = treeObj.getCheckedNodes(true);
			//mapid,projid,indexid
			jQuery.post("<%=request.getContextPath()%>/rs/definedMapRest/save",{"mapId":mapId,"projId":nodes[0].projId,"indexId":nodes[0].indexId},function(data){
				var _data = eval("("+data+")");
				if(_data.resultCode==200){
					alert("保存成功");
				}
			});
		}else{
			alert("请先选择路线");
		}
	}
</script>
</head>
<body class="easyui-layout" style="margin: 1px"> 
	<div style="margin:0;"></div>
	<div data-options="region:'center',title:''">
		<object id="definedMapActiveX" classid="clsid:2440035E-E5BD-4C45-ADEB-3947822BA5B8" height="490" width="1000" border="0" > 
			<param name="MapFilePath" value="C:\Program Files\Microsoft\shp2" />
			<param name="MHeight" value="490" />
			<param name="MWidth" value="1000" />
	    </object>
	</div>

	<div data-options="region:'east',title:'工程结构',split:true" style="width:175px;">
		<div align="center">
			<a href="javascript:save();" class="easyui-linkbutton" >保存</a>
		</div>
		<div align="left">
			<input id="mapId" name="mapId" value=""  type="hidden"/>
			<ul id="myTree" class="ztree"></ul>
		</div>
	</div>
	
	<script type="text/javascript" for="definedMapActiveX" event="ClickEvent(type,indexid)">
	if(type==1){
		if(indexid==-1){
			jQuery("#mapId").val("");
			var treeObj = $.fn.zTree.getZTreeObj("myTree");
			var nodes = treeObj.getCheckedNodes(true);
			for(var i=0;i<nodes.length;i++){
				treeObj.checkNode(nodes[i],false,true);
			}
		}else{
			jQuery("#mapId").val(indexid);
			jQuery.post("<%=request.getContextPath()%>/rs/definedMapRest/getDefinedMap",{"mapId":indexid},function(data){
				if(data.statusCode==200){
					var treeObj = jQuery.fn.zTree.getZTreeObj("myTree");
					var parentTreeObj = treeObj.getNodeByParam("projId", data.projId, null);
					if(treeObj.expandNode(parentTreeObj,true,true)){
						//展开节点
						var obj = treeObj.getNodeByParam("indexId", data.indexId, parentTreeObj);
						treeObj.checkNode(obj,true,false,false);
					}
				}else{
					var treeObj = $.fn.zTree.getZTreeObj("myTree");
					var nodes = treeObj.getCheckedNodes(true);
					for(var i=0;i<nodes.length;i++){
						treeObj.checkNode(nodes[i],false,true);
					}
				}
			},"json");
		}
	}
	</script>
</body>
</html>