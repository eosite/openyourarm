<%@page import="com.glaf.chinaiss.model.Treepinfo"%>
<%@page import="com.glaf.core.util.RequestUtils"%>
<%@page import="com.glaf.chinaiss.query.InterfaceQuery"%>
<%@page import="com.glaf.chinaiss.service.InterfaceService"%>
<%@page import="com.glaf.chinaiss.model.Interface"%>
<%@page import="com.glaf.chinaiss.model.TreepName"%>
<%@page import="com.glaf.chinaiss.query.TreepNameQuery"%>
<%@page import="com.glaf.chinaiss.service.TreepNameService"%>
<%@page import="com.glaf.chinaiss.model.ProjMuiProjList"%>
<%@page import="java.util.List"%>
<%@page import="com.glaf.core.context.ContextFactory"%>
<%@page import="com.glaf.chinaiss.service.ProjMuiProjListService"%>
<%@page import="com.glaf.core.config.Environment"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
	String useDatabase = "default";
	if(request.getParameter("useDatabase")!=null){
		useDatabase = request.getParameter("useDatabase");
	}else if(request.getAttribute("useDatabase")!=null){
		useDatabase = request.getAttribute("useDatabase").toString();
	}
	Treepinfo treepinfo = request.getAttribute("treepinfo")==null?null:(Treepinfo)request.getAttribute("treepinfo");
	int parentId = treepinfo==null?-1:treepinfo.getParentId();
	int indexId = treepinfo==null?-1:treepinfo.getIndexId();
	String treepInfoId = treepinfo==null?"":treepinfo.getId();
	
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
    
    InterfaceService interfaceService = (InterfaceService)ContextFactory.getBean("com.glaf.chinaiss.service.interfaceService");
    InterfaceQuery interfaceQuery = new InterfaceQuery();
    interfaceQuery.setFrmType("pfile");
    interfaceQuery.setIsSystem("1");
    interfaceQuery.setIsListShow("1");
    interfaceQuery.setOrderBy("listno");
    List<Interface> interfaceList = interfaceService.list(interfaceQuery);
    String ztreeMaxCount=com.glaf.core.config.SystemConfig.getString("treeNodeLoadQty", "500");
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看文件</title>
<%@include file="../inc/script.jsp" %>
<script type="text/javascript">
	
	var setting = {
		async: {
			enable: true,
			url: "<%=request.getContextPath()%>/rs/treepInfoRest/getTreepinfoByParentId?systemName=<%=useDatabase%>",
			autoParam:["indexId"],
			otherParam:["pageName" , "file"],
			type:"post"
		},
		callback:{
			onExpand:zTreeOnExpand,
			onClick:zTreeOnClick,
			onAsyncSuccess: onAsyncSuccess
		},
		view: {
			showIcon: true
		}
	};
	function zTreeOnExpand(event, treeId, treeNode){
		if(treeNode.children.length==0){
			treeNode.isParent = false;
			var treeObj = jQuery.fn.zTree.getZTreeObj("myTree");
			treeObj.updateNode(treeNode);
		}
	}
	
			function onAsyncSuccess(event, treeId, treeNode) {
			if(treeNode.count<<%=ztreeMaxCount%> ){
			if(treeNode.count>1){
			
			expandNodes(treeNode.children);
		      }else{
		      treeNode.isParent=false;
		      
		      }
		    
		  
		}
			
			
			
		}
		
		function expandNodes(nodes) {
		if (!nodes) return;
		 var treeObj = $.fn.zTree.getZTreeObj("myTree");
			for (var i=0, l=nodes.length; i<l; i++) {
			    
				treeObj.expandNode(nodes[i], true, false, false);
				if (nodes[i].isParent && nodes[i].zAsync) {
					expandNodes(nodes[i].children);
				} else {
					goAsync = true;
				}
			}
		}
	
	
	function zTreeOnClick(event, treeId, treeNode){
		jQuery("#myTable").datagrid({
			queryParams:{"useDatabase":<%=useDatabase%>,"treepInfoId":treeNode.id,"indexId":treeNode.indexId}
		});
	}
	
	
	jQuery(document).ready(function(){
		jQuery.fn.zTree.init(jQuery("#myTree"), setting);
	});

	jQuery(function(){
		
		//加载表格
		jQuery("#myTable").datagrid({
			url:"<%=request.getContextPath()%>/rs/viewFileRest/fileTable?useDatabase=<%=useDatabase%>&treepInfoId=<%=treepInfoId%>",
			onSelect:function(rowIndex,rowData){
				if(rowData){
					setValue(rowData.tname,rowData.duty,rowData.slevel,rowData.ctime,rowData.savetime,rowData.tagnum,rowData.totalNum,rowData.page,rowData.thematic,rowData.carrierType,rowData.summary,rowData.annotations,rowData.pageType,"",rowData.listNum,rowData.vnum);
				}else{
					setValue("","","","","","","","","","","","","","","","");
				}
			},
			onDblClickRow:function(rowIndex,rowData){
				if(rowData){
					setValue(rowData.tname,rowData.duty,rowData.slevel,rowData.ctime,rowData.savetime,rowData.tagnum,rowData.totalNum,rowData.page,rowData.thematic,rowData.carrierType,rowData.summary,rowData.annotations,rowData.pageType,"",rowData.listNum,rowData.vnum);
					showFile(false,rowData.id_enc);
				}else{
					setValue("","","","","","","","","","","","","","","","");
				}
			},
			onLoadSuccess:function(data){
				if(data.rows!=0){
					jQuery("#myTable").datagrid("selectRow",0);
					jQuery('#pagination').pagination({total:data.total});
					if(data.total>20){
						jQuery('#pagination').show();
					}
				}else{
					setValue("","","","","","","","","","","","","","","","");
					jQuery('#pagination').hide();
				}
			}
		});
		
		jQuery('#pagination').pagination({
			pageSize:20,
			showRefresh:false,
			showPageList:false,
			beforePageText:'第',
			afterPageText:'页，共{pages}页',
			displayMsg:'显示第 {from} 至 {to} 条记录，共 {total} 条记录',
			onSelectPage:function(pageNumber, pageSize){
				jQuery(this).pagination('loading');
				gotoPage(pageNumber,pageSize);
				jQuery(this).pagination('loaded');
			}
		});
	});
	
	/*
	 *选择页数
	 */
	function gotoPage(pageNumber,pageSize){
		jQuery("#myTable").datagrid({
			queryParams:{useDatabase:<%=useDatabase%>,"treepInfoId":"<%=treepInfoId%>","pageNumber":pageNumber,"pageSize":pageSize}
		});
	}
	
	//设置表单值
	function setValue(tname,duty,slevel,ctime,savetime,tagnum,totalNum,page,thematic,carrierType,summary,annotations,pageType,unit,listNum,vnum){
		jQuery("#tname").val(tname);//文件题名
		jQuery("#duty").val(duty);//责任者
		jQuery("#slevel").val(slevel);//密级
		jQuery("#ctime").val(ctime);//文件时间
		jQuery("#savetime").val(savetime);//	保管期限
		jQuery("#tagnum").val(tagnum);//文件编号
		jQuery("#totalNum").val(totalNum);//总登记号
		jQuery("#page").val(page);//页数
		jQuery("#thematic").val(thematic);//主题词
		jQuery("#carrierType").val(carrierType);//载体类型
		jQuery("#summary").val(summary);//搞要
		jQuery("#annotations").val(annotations);//附注
		jQuery("#pageType").val(pageType);//纸张类型
		//jQuery("#pageType").val(unit);//编制单位
		jQuery("#listNum").val(listNum);//目录号
		jQuery("#vnum").val(vnum);//档号
	}
	
	
	function showFile(selected,id_enc){
		var url="<%=request.getContextPath()%>/mx/docManage/yz/fileview/image/show?useDatabase=<%=useDatabase%>&method=fileAtt&topId=";
		if(selected){
			var row = jQuery("#myTable").datagrid("getSelected");
			url += row.id_enc;
		}else{
			url += id_enc;
		}
		//var sFeatures = "width="+window.screen.width+",height="+window.screen.height+",top=0,left=0,toolbar=no,menubar=no,status=no,location=no,help=no,center=yes,resizable=no,scroll=no,depended=yes,alwaysRaised=yes";
		//window.open(url,"viewFile",sFeatures);
		var height = screen.availHeight-100;
    	var width = screen.availWidth-10;
	    var title = "查看表格";
    	art.dialog.open(url, { height: height, width: width, title: title, scrollbars:"no" , lock: true });
	}
	
	function searchFrame(){
		var url = "<%=request.getContextPath()%>/mx/docManage/yz/fileview/viewFile/viewFileSearch?useDatabase=<%=useDatabase%>";
		art.dialog.open(url, { height: 550, width: 900, title: "检索", lock: false, scrollbars:"no" }, false);
	 }
</script>
</head>
<body  class="easyui-layout" >
	<div title="工程划分WBS"  data-options="region:'west'" style="width:230px">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north'" style="width:215px;height:25px;border: 0px">
				<label>项目</label><input type="text" class="easyui-validatebox"  style="width: 185px;background-color: #EEE" readonly="readonly" value="<%=treepName==null?"":treepName.getIndexName()%>"/>
			</div>
			<div data-options="region:'center'" style="width:215px;border: 0px;">
				<ul id="myTree" class="ztree"></ul>
			</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north'" style="height:300px;border: 0px;">
				<div class="toolbar-backgroud" style="height: 30px">
					<table border="0" style="width: 100%;height: 30px" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<img src="<%=request.getContextPath() %>/images/window.png">&nbsp;<span class="x_content_title">文件列表</span>
								<a href="javascript:searchFrame();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">检索</a>
								<a href="javascript:showFile(true,'');" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-view'">查看</a>
							</td>
							<td  align="right"><div id="pagination" style="width: 550px;display: none"></div></td>
						</tr>
					</table>
				</div>
				<table id="myTable" class="easyui-datagrid"  style="height: 260px" border="0" data-options="idField:'id_enc',singleSelect:true,fitColumns:false,rownumbers:true">
							<thead>
								<tr>
									<th data-options="field:'id_enc',hidden:true">id</th>
									<th data-options="field:'indexId',hidden:true">indexId</th>
									<th data-options="field:'efileNum',align:'center'">原文</th>
									<%
										for(Interface interfaceModel:interfaceList){
											String width = "100";
											String align = "center";
											if(interfaceModel.getDname().equals("tname")){//文件题名
												width = "800";
												align = "left";
											}else if(interfaceModel.getDname().equals("page")){//页数
												width = "50";
											}
									%>
										<th data-options="field:'<%=interfaceModel.getDname() %>',width:<%=width%>,align:'<%=align%>'"><%=interfaceModel.getFname() %></th>
									<%} %>
								</tr>
							</thead>
						</table>
			</div>
			<div title="文件信息" data-options="region:'center'" style="border: 0px">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'center'" style="border: 0px;">
						<div id="allTabs" class="easyui-tabs" data-options="fit:true" style="border: 0px">
						    <div title="文件著录" data-options="fit:true" style="overflow:auto;">  
						        <table cellpadding="0" cellspacing="6" border="0" >
						        	<tr>
						        		<td align="right" ><label>文件题名</label></td>
						        		<td colspan="3" ><textarea id="tname"   style="width: 100%;height:45px;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"></textarea></td>
						        	</tr>
						        	<tr>
						        		<td align="right" style="width: 5%"><label>责任者</label></td>
						        		<td style="width: 45%"><input id="duty"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
						        		<td align="right" style="width: 5%"><label>密级</label></td>
						        		<td style="width: 45%"><input id="slevel"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
						        	</tr>
						        	<tr>
						        		<td align="right" ><label>文件时间</label></td>
						        		<td><input id="ctime"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
						        		<td align="right" ><label>保管期限</label></td>
						        		<td><input id="savetime"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
						        	</tr>
						        	<tr>
						        		<td align="right" ><label>文件编号</label></td>
						        		<td><input id="tagnum"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
						        		<td align="right" ><label>总登记号</label></td>
						        		<td><input id="totalNum"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
						        	</tr>
						        	<tr>
						        		<td align="right" ><label>页数</label></td>
						        		<td><input id="page"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
						        		<td align="right" ><label>主题词</label></td>
						        		<td><input id="thematic"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
						        	</tr>
						        	<tr>
						        		<td align="right" ><label>载体类型</label></td>
						        		<td><input id="carrierType"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
						        		<td align="right" ><label>摘要</label></td>
						        		<td><input id="summary"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
						        	</tr>
						        	<tr>
						        		<td align="right" ><label>附注</label></td>
						        		<td><input id="annotations"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
						        		<td align="right" ><label>纸张类型</label></td>
						        		<td><input id="pageType"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
						        	</tr>
						        	<tr>
						        		<td align="right" ><label>编制单位</label></td>
						        		<td><input id="duty1"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
						        		<td align="right" ><label>目录号</label></td>
						        		<td><input id="listNum"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
						        	</tr>
						        	<tr>
						        		<td align="right" ><label>档号</label></td>
						        		<td><input id="vnum"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
						        		<td align="right" >&nbsp;</td>
						        		<td>&nbsp;</td>
						        	</tr>
						        </table>
						    </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>