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
				//autoParam:["id", "name=n", "level=lv"],
				//otherParam:{"otherParam":"zTreeAsyncTest"},
				dataFilter: filter
			},
			callback: {
				beforeClick: zTreeBeforeClick,
				//onExpand: zTreeOnExpand,
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
		}

		function updateNode(treeId, treeNode){
			var zTree = $.fn.zTree.getZTreeObj(treeId);
			zTree.setting.view.fontCss["color"] = "#0000ff";
			//treeNode.iconSkin = "icon03" ;
			zTree.updateNode(treeNode);
		}

		function zTreeBeforeClick(treeId, treeNode, clickFlag) {
           
		}
		//加载文件列表信息
		function zTreeOnClick(event, treeId, treeNode, clickFlag) {
             //alert(treeNode.id);
			 //updateNode(treeId, treeNode);
			 var t = new Date().getTime();
			 var ic = document.getElementById('includesChildren').checked ;
			 loadData('${contextPath}/rs/isdp/cellUpicInfo/list',treeNode.id) ;
			getParams();
		}

		function loadData(url,nodeId){
			if(!nodeId){
				nodeId = "" ;
			}
			var searchWord = $('#searchWord').val();
			var pageOptions = $('#myGridData').datagrid("getPager").pagination('options') ;
			pageOptions.pageNumber = 1;
			var rows = pageOptions.pageSize ;
			var t = new Date().getTime();
			var ic = document.getElementById('includesChildren').checked ;
			var param = {"ic":ic,"t":t,"page":1,"rows":rows,"searchWord":searchWord,"treeFolderIndexId":nodeId} ;
			//searchWord
		  $.post(url,param,function(data){
			  $('#myGridData').datagrid('loadData', data);
		  },'json');
	  }


		$(document).ready(function(){
			ztreeObj = $.fn.zTree.init($("#myTree"), setting);
		});
		//下载文件
		function onMyDbClickRow(rowIndex, row){
			var link = "${contextPath}/rs/isdp/cellUpicInfo/download?id="+row.id;
			window.open(link);
		}
		//新增
        function addFile(){
        	var selectedNode = ztreeObj.getSelectedNodes() ;
        	if(selectedNode.length<=0){
        		alert("请选择分类");
        	};
        	var link = '${contextPath}/mx/isdp/cell/file/edit?treeNodeId=' +selectedNode[0].id ;
        		jQuery.layer({
        			type: 2,
        			maxmin: true,
        			shadeClose: true,
        			title: "新增/修改",
        			closeBtn: [0, true],
        			shade: [0.8, '#000'],
        			border: [10, 0.3, '#000'],
        			offset: ['20px',''],
        			fadeIn: 100,
        			area: ['780px', (jQuery(window).height() - 50) +'px'],
                    iframe: {src: link}
        		});
		}
		//修改
		function editFile(){
			var rows = jQuery('#myGridData').datagrid('getSelections');
			if(rows == null || rows.length !=1){
			     alert("请选择其中一条记录。");
				 return;
			}
			var row = jQuery('#myGridData').datagrid('getSelected');
			if (row ){
			    var link = '${contextPath}/mx/isdp/cell/file/edit?fileId='+row.id ;
		        		jQuery.layer({
		        			type: 2,
		        			maxmin: true,
		        			shadeClose: true,
		        			title: "新增/修改",
		        			closeBtn: [0, true],
		        			shade: [0.8, '#000'],
		        			border: [10, 0.3, '#000'],
		        			offset: ['20px',''],
		        			fadeIn: 100,
		        			area: ['780px', (jQuery(window).height() - 50) +'px'],
		                    iframe: {src: link}
		        		});
			} 
		}
		//删除
		function delFile(){
			var rows = jQuery('#myGridData').datagrid('getSelections');
			if(rows == null || rows.length !=1){
			     alert("请选择其中一条记录。");
				 return;
			}
			 if(confirm("数据删除后不能恢复，确定删除吗？")){
					var row = jQuery('#myGridData').datagrid('getSelected');
					 var link = '${contextPath}/mx/isdp/cell/file/delete?id='+row.id ;
					jQuery.ajax({
						   type: "get",
						   url: link,
						   dataType:  'json',
						   error: function(data){
							   alert('服务器处理错误！');
						   },
						   success: function(data){
							   if(data != null && data.message != null){
								   alert(data.message);
							   } else {
								   alert('操作成功完成！');
							   }
							   jQuery('#myGridData').datagrid('reload');
						   }
					});
			 }
    	}
		
		
	    function searchData(){
	    	var selectedNode = ztreeObj.getSelectedNodes() ;
	    	var nodeId = "" ;
        	if(selectedNode.length > 0){
        		nodeId = selectedNode[0].id ;
        	};
			var searchWord = $('#searchWord').serialize();
			var t = new Date().getTime();
			var ic = document.getElementById('includesChildren').checked ;
			var url = '${contextPath}/rs/isdp/cellUpicInfo/list?treeFolderIndexId='+nodeId+"&ic="+ic+'&t='+ t ;
	        jQuery.ajax({
                  type: "post",
                  url: url,
                  dataType:  'json',
                  data: searchWord,
                  error: function(data){
                          alert('服务器处理错误！');
                  },
                  success: function(data){
                       jQuery('#myGridData').datagrid('loadData', data);
                   //    $('#searchWord').val('') ;
                  }
             });
	        
	        getParams();
		}
			
	   function includsChildrenChange(ic){
		   var selectedNodes = ztreeObj.getSelectedNodes();
		   if(selectedNodes.length <= 0){
			   return;
		   }
		   var treeNode = selectedNodes[0] ;
		   loadData('${contextPath}/rs/isdp/cellUpicInfo/list',treeNode.id) ;
		   getParams();
	   }
	 
	</script>
</head>
<body>  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  

    <div data-options="region:'west',split:true" style="width:250px;">
	  <div class="easyui-layout" data-options="fit:true">  
           
			 <div data-options="region:'center',border:false">
			    <ul id="myTree" class="ztree"></ul>  
			 </div> 
			 
            <!--<div data-options="region:'south',split:true,border:true" style="height:30px"></div>--> 
        </div>  
	</div> 
	
    <div data-options="region:'center'">  
        <div class="easyui-layout" data-options="fit:true">  

           <div data-options="region:'center',split:true,border:true, fit:true">
		   
             <div style="background:#fafafa;padding:2px;border:1px solid #ddd;font-size:12px"> 
			   <b>文件列表</b>
			   <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-view'">查看</a>
               <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'"
			      onclick="javascript:addFile();">增加</a>  
               <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
			      onclick="javascript:editFile();">修改</a>  
			   <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'" onclick="javascript:delFile();">删除</a> 
			 <input id="searchWord" name="searchWord" type="text" class="x-searchtext" size="30" maxlength="200"  />
			   <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-search'" onclick="javascript:searchData();">查找</a>
			   <input type="checkbox" name="includesChildren" id="includesChildren" value="true" checked="checked" onchange="javascript:includsChildrenChange(this.checked)">显示下级表格
             </div>  

			 <table id="myGridData" class="easyui-datagrid" >
				<thead>
					<tr>
						<th data-options="field:'sortNo',width:80">序号</th>
						<c:forEach items="${fields}" var="f">
						<th	data-options="field:'${f.dname}',width:${f.listweigth}">${f.fname}</th>
						</c:forEach>
					</tr>
				</thead>
			</table>  
			<script type="text/javascript">
			//data-options="url:'${contextPath}/rs/isdp/cellUpicInfo/list',fitColumns:true,
			//nowrap:false,rownumbers:false,showFooter:true,pagination:true,pageNumber:1,pageSize:15,
			//pageList:[10,15,20,30,40,50],singleSelect:true,onDblClickRow:onMyDbClickRow"
			
			$('#myGridData').datagrid({   
				method:'POST',
				height:480,
			    url:'${contextPath}/rs/isdp/cellUpicInfo/list',   
			    fitColumns:true,
				nowrap:false,
				rownumbers:false,
				showFooter:true,
				pagination:true,
				pageNumber:1,
				pageSize:15,
				pageList:[10,15,20,30,40,50],
				singleSelect:true,
				onDblClickRow:onMyDbClickRow,
			});  
			
			//点击树、查询、勾选下级表格时.动态设置参数
			function getParams(){
				var treeFolderIndexId = "" ;
				if(ztreeObj){
					var selectedNode = ztreeObj.getSelectedNodes() ;
		        	if(selectedNode.length>0){
		        		treeFolderIndexId = selectedNode[0].id ;
		        	};
				}
				var searchWord = $('#searchWord').val();
				//serialize();
				var t = new Date().getTime();
				var ic = document.getElementById('includesChildren').checked ;
				$('#myGridData').datagrid('options').queryParams =  {"treeFolderIndexId":treeFolderIndexId,"ic":ic,"searchWord":searchWord,"t":t};
			}
			
			getParams();
			
			</script>
	   			
			</div>  

            <!-- <div data-options="region:'south',split:true,border:true,align:'right'" style="height:30px">
			    <div style="text-align:right;valign:middle;padding-top:5px;">
				共有10条记录,共1页,第1页
				</div>
			</div>  --> 
        </div>  
    </div>  
    </div>  

 <div id="mm1" style="width:150px;"> 
	<div>新增同级</div>
	<div>新增下级</div>
 </div>

  <div id="mm3" style="width:150px;"> 
	<div>删除本节点</div>
	<div>删除本节点及子孙</div>
 </div>

 <iframe id="newFrame" name="newFrame" width="0" height="0"></iframe>

</body>  
</html>  
<!--end of isdp/dispatcher/cell_setr_folder.jsp-->