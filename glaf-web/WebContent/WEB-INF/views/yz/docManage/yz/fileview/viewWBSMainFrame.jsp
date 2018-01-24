<%@page import="com.glaf.chinaiss.model.TreepName"%>
<%@page import="com.glaf.chinaiss.query.TreepNameQuery"%>
<%@page import="com.glaf.chinaiss.service.TreepNameService"%>
<%@page import="java.util.List"%>
<%@page import="com.glaf.core.context.ContextFactory"%>
<%@page import="com.glaf.chinaiss.service.ProjMuiProjListService"%>
<%@page import="com.glaf.core.config.Environment"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
	String useDatabase = request.getParameter("useDatabase")==null?"default":request.getParameter("useDatabase");
	
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    
    Environment.setCurrentSystemName(useDatabase);
    TreepNameService treepNameServcie = (TreepNameService)ContextFactory.getBean("com.glaf.chinaiss.service.treepNameService");
    TreepNameQuery treepNameQuery = new TreepNameQuery();
    List<TreepName> treepNameList = treepNameServcie.list(treepNameQuery);
    TreepName treepName = null;
    if(treepNameList.size()>0){
    	treepName = treepNameList.get(0);
    }
   String ztreeMaxCount=com.glaf.core.config.SystemConfig.getString("treeNodeLoadQty", "50");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看WBS</title>
<%@include file="../inc/script.jsp" %>
<script type="text/javascript">
	var initFileTabs = 0;
	jQuery(function(){
		jQuery("#myTable").treegrid({
			url:'<%=request.getContextPath()%>/rs/treepInfoRest/getRootTreeNodeToEasyUIJSON?parentId=-1&useDatabase=<%=useDatabase%>',   
			width:"auto",
			height:"390",
			fitColumns:true,
			nowrap: false,
		    idField:'indexId',
		    treeField:'indexName',   
		    columns:[[
				{title:'工程信息',width:740,colspan:4},  
				{title:'工程表格',width:90,colspan:3},  
				{title:'工程文件',width:90,colspan:3},  
			],[
			   	{field:'indexId',title:'indexId',hidden:true},
				{field:'indexName',title:'名称',width:540},  
				{field:'intcellfinish',align:"center",title:'完成',width:40,formatter:function(value,rowData){
					if(jQuery("#myTable").treegrid('getChildren',rowData.indexId).length==0){
						if(value==1){
							return "○";
						}else if(value==2){
							return "□";
						}else if(value==3){
							return "△";
						}else{
							return "×";
						}
					}
				}},  
				{field:'bdate',title:'开工时间',width:80,align:"center"},  
				{field:'edate',title:'完成时间',width:80,align:"center"},  
				{field:'cell1',title:'应填',width:30,align:"center"},  
				{field:'cell2',title:'已填',width:30,align:"center"},  
				{field:'cell3',title:'缺少',width:30,align:"center"},  
				{field:'intpfile1',title:'应有',width:30,align:"center"},  
				{field:'intpfile2',title:'已有',width:30,align:"center"},  
				{field:'intpfile3',title:'缺少',width:30,align:"center"}
			]],
			onExpand:function(node){
				//展开时load下一层,如果load过不会再load,只是展开
				var pid = node.attributes.parentId;
			
				
				if(pid>=-1){
		       
					var a = jQuery(this).treegrid('getChildren',node.id);
					if(a[0].id==-2){
						getChildData(node, node.indexId, false);
					}
				}
				
				
			},
			onLoadSuccess: function(node){
				
			var nodes = jQuery("#myTable").treegrid('getChildren',node.id);
			if(node.childrenCount < <%=ztreeMaxCount%>){
			for(var i=0; i<nodes.length;i++){
			if(nodes[i].id != -2)
			 getChildData(nodes[i],nodes[i].indexId, false);
			}
			}
			},
			
			onClickRow:function(row){
				initFileTabs = 0;
				jQuery("#checkTabs").tabs("select","表格检查");
				tableCheckFunc(row.indexId);
			},
			onContextMenu:function(e,node){
				e.preventDefault();
				jQuery(this).treegrid('toggle',node.id);
			},
			onSelect:function(row){
				initFileTabs = 0;
				jQuery("#checkTabs").tabs("select","表格检查");
				tableCheckFunc(row.indexId);
			},
			rownumbers:true
		});
		
		jQuery("#checkTabs").tabs({
			onSelect:function(title,index){
				if(initFileTabs==0 && title=="文件检查"){
					var row = jQuery("#myTable").treegrid("getSelected");
					if(row){
						fileCheckFunc(row.indexId);
						initFileTabs = 1;
					}
				}
			}
		});
	});
	
	//定位
	function searchGoto(row){
		var url = "<%=request.getContextPath()%>/rs/treepInfoRest/searchGoTo?useDatabase=<%=useDatabase%>&id="+row.id_enc;
		jQuery.post(url,function(data){
				jQuery('#myTable').treegrid("loadData",data.treeGridData);
				jQuery("#myTable").treegrid("select",data.selectIndexId);
				/*关闭对话框
				var list = art.dialog.list;
				for (var i in list) {
				    list[i].close();
				};
				*/
			},"JSON"
		);
	}
	
	//展开
	function expand(pid){
		//展开时load下一层,如果load过不会再load,只是展开
		if(pid>=-1){
			var node = jQuery("#myTable").treegrid("find",pid);
			var a = jQuery("#myTable").treegrid('getChildren',node.id);
			if(a[0].id==-2){
				getChildData(node, node.indexId, false);
			}
		}
	}
	
	/**
	  * 异步load子树方法
	  */
	function getChildData(node, pid, flag){
		jQuery.post("<%=request.getContextPath()%>/rs/treepInfoRest/getChildDataMethod?useDatabase=<%=useDatabase%>",{parentId:pid},
			function(data){
				var a = jQuery('#myTable').treegrid('getChildren',node.indexId);
				jQuery('#myTable').treegrid('remove', a[0].indexId);
				jQuery('#myTable').treegrid('append',{
					parent: (node?node.indexId:null),
					data:data
				});
			},"JSON"
		);
 	}
	
	/**
	 *异步获取表格检查数据
	 */
	function tableCheckFunc(indexId){
		jQuery("#tableCheck").datagrid({
			url:"<%=request.getContextPath()%>/rs/viewWBSRest/tableCheckFunc?useDatabase=<%=useDatabase%>&indexId="+indexId
		});
	}
	
	/**
	 *异步获取文件检查数据
	 */
	function fileCheckFunc(indexId){
		jQuery("#fileCheck").datagrid({
			url:"<%=request.getContextPath()%>/rs/viewWBSRest/fileCheckFunc?useDatabase=<%=useDatabase%>&indexId="+indexId
		});
	}
	
	//打开工程定位对话框
	function showSearchDialog(){
		var url = "<%=request.getContextPath()%>/mx/docManage/yz/fileview/viewWBS/projSearchGoTo?useDatabase=<%=useDatabase%>";
		art.dialog.open(url, { height: 550, width: 900, title: "工程定位", lock: false, scrollbars:"no" }, false);
	}
	
	//显示/隐藏详细信息
	function showDetail(){
		if(jQuery("#showDetailInputId").attr("checked")){
			jQuery("#layout").layout("expand","south");
			jQuery("#myTable").datagrid("resize",{width:"auto",height:385});
		}else{
			jQuery("#layout").layout("collapse","south");
			jQuery("#myTable").datagrid("resize",{width:"auto",height:555});
		}
	}
	//状态样式
	function staticFormatter(value,rowData){
		if(value==0){
			return "缺少";
		}else if(value==1){
			return "完成";
		}else if(value==2){
			return "不合格";
		}else{
			return "";
		}
	}
	
	//行样式
	function rowStylerFormatter(index,rowData){
		if(rowData.intFinish==0){
			return "background-color:#F00;";
		}else if(rowData.intFinish==1){
			//return "background-color:#FFF;";
		}else if(rowData.intFinish==2){
			return "background-color:#FF0;";
		}else{
			return "";
		}
	}
	//查看表格样式
	function showTableFormatter(value,rowData){
		if(rowData.intFinish==0){
			return "";
		}else{
			return "<a href='javascript:void(0);' onclick='javascript:showData(\""+rowData.useId_enc+"\",\""+rowData.defId_enc+"\",\""+rowData.name_enc+"\","+rowData.indexId+");' >查看表格</a>";
		}
	}
	
	//查看表格数据
	function showData(fileID,fileDotFileId,name,indexId){
		//判断是否有多页的情况，如果有，弹出菜单选择
		var e = event || window.event;
		var x = e.screenX;
		var y = e.screenY;
		jQuery.post("<%=request.getContextPath()%>/rs/viewWBSRest/checkTableNum",
			{"useDatabase":"<%=useDatabase%>","fileDotFileId":fileDotFileId,"name":name,"indexId":indexId},
			function(data){
				var total = data.total;
				
				if(total>1){
					jQuery("#mm").html("");
					var cells = data.cells;
					for(var i = 0;i<cells.length;i++){
						jQuery('#mm').menu("appendItem",{"text":cells[i].name,"id":cells[i].useId});
					}
					
					jQuery('#mm').menu('show', {
	                    left: x-5,
	                    top: y-20
	                });
					
					return;
				}else{
					show(fileID);
				}
			},
			"JSON"
		);
	}
	
	function menuClick(item){
		show(item.id);
	}
	
	function show(fileID){
		var url = "<%=request.getContextPath()%>/mx/docManage/yz/fileview/cellTable/showTable?method=dotUse&useDatabase=<%=useDatabase%>&fileID="+fileID;
		var height = screen.availHeight-100;
    	var width = screen.availWidth-10;
	    var title = "查看表格";
    	art.dialog.open(url, { height: height, width: width, title: title, scrollbars:"no" , lock: true });
	}
</script>
</head>
<body>
	<div id="layout" class="easyui-layout" data-options="fit:true">  
		<div data-options="region:'north',border:true" style="height:36px"> 
			<div class="toolbar-backgroud"> 
				<img src="<%=request.getContextPath() %>/images/window.png">&nbsp;<span class="x_content_title">工程项目</span>
				<input id="proj_select" class="easyui-combobox" value="<%=treepName.getIndexName() %>" disabled="disabled" />
				<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-search'" onclick="javascript:showSearchDialog();">工程定位</a>
				<input type="checkbox" checked="checked" id="showDetailInputId" onclick="javascript:showDetail()" />
				<label for="showDetailInputId">显示详细信息</label>
				<label>○完成&nbsp;&nbsp;□只完成表格&nbsp;&nbsp;△不合格&nbsp;&nbsp;×未完成</label>
			</div> 
		</div> 
		<div data-options="region:'center'">
			<table id="myTable" ></table>
		</div>
		<div id="southLayout" data-options="region:'south',border:true,split:true" style="height:200px">
			<div id="checkTabs" class="easyui-tabs" data-options="fit:true,border:false">
				<div id="tableTab" title="表格检查" style="overflow:hidden"> 
					<table id="tableCheck" class="easyui-datagrid" style="width:auto;height:155px" border="0" data-options="width:880,idField:'id',singleSelect:true,rownumbers:true,fitColumns:false,rowStyler:rowStylerFormatter">
							<thead>
								<tr>
									<th data-options="field:'id',width:30,hidden:true">ID</th>
									<th data-options="field:'intFinish',width:60,align:'center',formatter:staticFormatter">状态</th>
									<th data-options="field:'intPage0',width:60,align:'center'">应填</th>
									<th data-options="field:'intPage1',width:60,align:'center'">已填</th>
									<th data-options="field:'fileDotNum',width:100,align:'center'">模板编号</th>
									<th data-options="field:'name',width:450">名称</th>
									<th data-options="field:'opt',width:100,align:'center',formatter:showTableFormatter">查看表格</th>
								</tr>
							</thead>
						</table>
				</div>
				<div id="fileTab" title="文件检查" data-options="fit:true,border:false" style="overflow:hidden">
					<table id="fileCheck" class="easyui-datagrid" style="width:auto;height:155px" border="0" data-options="width:620,idField:'id',singleSelect:true,rownumbers:true,fitColumns:false,rowStyler:rowStylerFormatter">
							<thead>
								<tr>
									<th data-options="field:'id',width:50,hidden:true">ID</th>
									<th data-options="field:'intFinish',width:60,align:'center',formatter:staticFormatter">状态</th>
									<th data-options="field:'intPage0',width:60,align:'center'">页数</th>
									<th data-options="field:'name',width:450">名称</th>
								</tr>
							</thead>
						</table>
				</div>
			</div>
		</div>  
	</div>
	
	<div id="mm" class="easyui-menu" data-options="onClick:menuClick" style="width:120px;"></div>
</body>
</html>