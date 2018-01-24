<%@page import="com.glaf.chinaiss.query.InterfaceQuery"%>
<%@page import="com.glaf.chinaiss.service.InterfaceService"%>
<%@page import="com.glaf.core.context.ContextFactory"%>
<%@page import="com.glaf.chinaiss.model.Interface"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    
    String useDatabase = request.getParameter("useDatabase")==null?"default":request.getParameter("useDatabase");
    
    InterfaceService interfaceService = (InterfaceService)ContextFactory.getBean("com.glaf.chinaiss.service.interfaceService");
    InterfaceQuery interfaceQuery = new InterfaceQuery();
    interfaceQuery.setFrmType("pfile");
    interfaceQuery.setIsSystem("1");
    interfaceQuery.setIsListShow("1");
    interfaceQuery.setOrderBy("listno");
    List<Interface> interfaceList = interfaceService.list(interfaceQuery);
	request.setAttribute("interfaceList", interfaceList);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工程定位</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<link href="<%=request.getContextPath()%>/scripts/artDialog/skins/default.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/icons/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/layout/css/styles.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/artDialog.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/plugins/iframeTools.js"></script>
<script type="text/javascript">
	var searchFlag = 1;
	jQuery(function(){
		jQuery("#researchBtn").hide();//页面加载时隐藏再检索按钮
		
		jQuery("#searchType").combobox({
			onSelect:function(rec){
				if(rec.value=="ctime"){
					jQuery("#searchKeyWord").hide();
					jQuery("#saveTimeSearchDiv").show();
				}else{
					jQuery("#searchKeyWord").show();
					jQuery("#saveTimeSearchDiv").hide();
				}
			}
		});
	});
	
	//检索
	function search(flag){
		searchFlag = flag;
		if(searchFlag==0){
			//点击检索时执行
			//再检索按钮显示出来并隐藏检索按钮
			jQuery("#researchBtn").show();
			jQuery("#searchBtn").hide();
			
			//执行检索
			searchKeyWord(1);
		}else if(searchFlag==1){
			//点击再检索时执行
			searchKeyWord(1);
		}else if(searchFlag==2){
			//点击新检索时执行
			searchKeyWord(1);
		}
	}
	
	/*
	 *查询，pageNumber为页数
	 */
	function searchKeyWord(pageNumber){
		var pageSize = 20;
		if(jQuery("#searchType").combobox("getValue")=="ctime"){
			if(jQuery("#timeInput").datebox("getValue")==""){
				jQuery.messager.alert("提示","请选择日期","info");
				return;
			}
		}else {
			if(jQuery("#searchKeyWord").val()==""){
				jQuery.messager.alert("提示","请填写查询关键字","info");
				if(searchFlag==0){
					jQuery("#researchBtn").hide();
					jQuery("#searchBtn").show();
				}else{
					jQuery("#researchBtn").show();
					jQuery("#searchBtn").hide();
				}
				return;
			}
		}
		var searchKeyWord = jQuery("#searchKeyWord").val();
		
		jQuery("#dataTable").datagrid({
			url:"<%=request.getContextPath()%>/rs/viewFileRest/searchKeyWord",
			queryParams:{"searchFlag":searchFlag,"searchKeyWord":searchKeyWord,"pageNumber":pageNumber,"pageSize":pageSize,"searchType":jQuery("#searchType").combobox("getValue"),"timeInput":jQuery("#timeInput").datebox("getValue"),"expression":jQuery("#expression").combobox("getValue")},
			onLoadSuccess:function(data){
				var total = data.total;
				var pageTotal = parseInt((total-1)/pageSize+1);
				jQuery("#pageNumber").val(pageNumber);
				jQuery("#pageTotal").val(pageTotal);
				jQuery("#pageNumberSpan").html(pageNumber);
				jQuery("#pageTotalSpan").html(pageTotal);
			},
			onDblClickRow:function(rowIndex, row){
				var sFeatures = "width="+window.screen.width+",height="+window.screen.height+",top=0,left=0,toolbar=no,menubar=no,status=no,location=no,help=no,center=yes,resizable=no,scroll=no,depended=yes,alwaysRaised=yes";
				window.open("<%=request.getContextPath()%>/mx/docManage/yz/fileview/image/show?method=fileAtt&topId="+row.id_enc,"viewFile",sFeatures);
			}
		});
	}
	
	function gotoPage(flag){
		var pageNumber = jQuery("#pageNumber").val()==0?1:parseInt(jQuery("#pageNumber").val());
		var pageTotal = jQuery("#pageTotal").val();
		if(flag==1){//首页
			searchKeyWord(1);
		}else if(flag==2){//上页
			if(pageNumber==1){
				jQuery.messager.alert("消息","已经是第一页了","info");
				return;
			}
			searchKeyWord(pageNumber-1);
		}else if(flag==3){//下页
			if(pageNumber==pageTotal){
				jQuery.messager.alert("消息","已经是最后一页了","info");
				return;
			}
			searchKeyWord(pageNumber+1);
		}else if(flag==4){//尾页
			searchKeyWord(pageTotal);
		}
	}
</script>
</head>
<body>
	<table style="width: 100%;height: 100%;vertical-align: center" cellpadding="0" cellspacing="0">
		<tr align="center" height="30px">
			<td style="background-color: #FFFBF0">文件检索</td>
		</tr>
		<tr height="35px">
			<td style="background-color: #F0F0F0">
				<table cellpadding="5" cellspacing="0" >
					<tr>
						<td><font size="2" style="margin-left: 8px">选择检索项</font></td>
						<td>
							<select id="searchType" class="easyui-combobox" name="searchType" style="width:100px">
								<option value="tname">文件题名</option>
								<option value="duty">责任者</option>
								<option value="savetime">保管期限</option>
								<option value="ctime">文件时间</option>
								<option value="tagnum">文件编号</option>
								<option value="page">页数</option>
								<option value="thematic">主题词</option>
								<option value="vnum">档号</option>
								<option value="slevel">密级</option>
							</select>
						</td>
						<td>
							<input type="text" id="searchKeyWord" name="searchKeyWord" class="easyui-validatebox" style="width:450px;margin-left:8px"/>
							<div id="saveTimeSearchDiv" style="display: none">
							<select id="expression" name="expression"  style="width:100px;"class="easyui-combobox">
								<option value="equal">等于</option>
								<option value="greaterThan">大于</option>
								<option value="greaterThanEqual">大于等于</option>
							</select>
							<input type="text" id="timeInput" name="timeInput"  class="easyui-datebox"/>
							</div>
						</td>
						<td>
							<a id="searchBtn" href="javascript:search(0);" class="easyui-linkbutton"  data-options="plain:true,iconCls:'icon-search'">检&nbsp;&nbsp;&nbsp;&nbsp;索</a>
							<a id="researchBtn" href="javascript:search(1);" class="easyui-linkbutton"  data-options="plain:true,iconCls:'icon-search'">再检索</a>
						</td>
						<td><a id="newsearchBtn" href="javascript:search(2);" class="easyui-linkbutton"  data-options="plain:true,iconCls:'icon-reload'">新检索</a></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr height="20px">
			<td style="background-color:#FFFBF0">
				<input id="pageNumber"  value="0" type="hidden"/> 
				<input id="pageTotal" value="0" type="hidden"/>
				<font size="2" style="margin-left:12px">
					查找结果&nbsp;&nbsp;第<span id="pageNumberSpan">0</span>页，共有<span id="pageTotalSpan">0</span>页
					<a href="javascript:gotoPage(1);">首页</a>
					<a href="javascript:gotoPage(2);">上页</a>
					<a href="javascript:gotoPage(3);">下页</a>
					<a href="javascript:gotoPage(4);">尾页</a>
					</font>
			</td>
		</tr>
		<tr height="380px">
			<td>
				<table id="dataTable" class="easyui-datagrid" style="width:1600;height:430px" border="0" data-options="idField:'indexId',singleSelect:true,fitColumns:true,rownumbers:true">
						<thead>
							<tr>
									<th data-options="field:'id_enc',hidden:true">id</th>
									<th data-options="field:'indexId',hidden:true">indexId</th>
									<th data-options="field:'efileNum',align:'center',width:50">原文</th>
									<%
										for(Interface interfaceModel:interfaceList){
											String width = "100";
											String align = "center";
											if(interfaceModel.getDname().equals("tname")){//文件题名
												width = "600";
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
			</td>
		</tr>
	</table>
</body>
</html>