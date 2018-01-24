<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    
    String useDatabase = request.getParameter("useDatabase")==null?"default":request.getParameter("useDatabase");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工程定位</title>
<%@include file="../inc/script.jsp" %>
<script type="text/javascript">
	jQuery(function(){
		jQuery("#researchBtn").hide();//页面加载时隐藏再检索按钮
	});
	
	//检索
	function search(searchFlag){
		if(searchFlag==0){
			//点击检索时执行
			//再检索按钮显示出来并隐藏检索按钮
			jQuery("#researchBtn").show();
			jQuery("#searchBtn").hide();
			
			//执行检索
			searchKeyWord(1);
		}else if(searchFlag==1){
			//点击再检索时执行
			
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
		if(jQuery("#searchKeyWord").val()==""){
			jQuery.messager.alert("提示","请填写查询关键字","info");
			return;
		}
		
		if(jQuery("#searchType").combobox("getValue")=="projName"){
			var searchKeyWord = jQuery("#searchKeyWord").val();
			
			jQuery("#dataTable").datagrid({
				url:"<%=request.getContextPath()%>/rs/treepInfoRest/search?useDatabase=<%=useDatabase%>&searchType="+jQuery("#searchType").combobox("getValue"),
				queryParams:{"searchKeyWord":searchKeyWord,"pageNumber":pageNumber,"pageSize":pageSize},
				onLoadSuccess:function(data){
					var total = data.total;
					var pageTotal = parseInt((total-1)/pageSize+1);
					jQuery("#pageNumber").val(pageNumber);
					jQuery("#pageTotal").val(pageTotal);
					jQuery("#pageNumberSpan").html(pageNumber);
					jQuery("#pageTotalSpan").html(pageTotal);
				}
			});
		}
	}
	
	//定位至目标
	function gotoTarget(){
		if(parent){
			var row = jQuery("#dataTable").datagrid("getSelected");
			if(!row){
				alert("请选择一条记录！");
				return;
			}
			
			parent.searchGoto(row);
		}
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
			<td colspan="2" style="background-color: #FFFBF0">工程划分检索</td>
		</tr>
		<tr height="35px">
			<td colspan="2" style="background-color: #F0F0F0">
				<table cellpadding="5" cellspacing="0" >
					<tr>
						<td><font size="2" style="margin-left: 8px">选择检索项</font></td>
						<td>
							<select id="searchType" class="easyui-combobox" name="searchType" style="width:100px">
								<option value="projName">工程名称</option>
							</select>
						</td>
						<td><input type="text" id="searchKeyWord" name="searchKeyWord" class="easyui-validatebox" style="width:450px;margin-left:8px"/></td>
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
			<td colspan="2"  style="background-color:#FFFBF0">
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
			<td width="800px">
				<table id="dataTable" class="easyui-datagrid" style="width:auto;height:430px" border="0" data-options="idField:'indexId',singleSelect:true,fitColumns:true,rownumbers:true">
						<thead>
							<tr>
								<th data-options="field:'id',width:20,hidden:true">ID</th>
								<th data-options="field:'indexId',width:20,hidden:true">indexId</th>
								<th data-options="field:'intcellfinish',width:60,align:'center'">状态</th>
								<th data-options="field:'num',width:100,align:'center'">编号</th>
								<th data-options="field:'indexName',width:400">名称</th>
								<th data-options="field:'bdate',width:120,align:'center'">开始时间</th>
								<th data-options="field:'edate',width:120,align:'center'">结束时间</th>
							</tr>
						</thead>
					</table>
			</td>
			<td style="background-color: #F0F0F0;padding-top: 50px;text-align:center;vertical-align: top;">
				<a id="gotoBtn" href="javascript:gotoTarget();" class="easyui-linkbutton"  data-options="plain:true,iconCls:'icon-redo'">定位</a>
			</td>
		</tr>
	</table>
</body>
</html>