<%--
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String actorId = com.glaf.core.util.RequestUtils.getActorId(request);
	com.glaf.core.identity.User user = com.glaf.core.security.IdentityFactory.getUser(actorId);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微会员</title>
<%@ include file="/WEB-INF/views/wx/inc/wx_styles.jsp"%>
<%@ include file="/WEB-INF/views/wx/inc/wx_scripts.jsp"%>
<script type="text/javascript">
   var contextPath="<%=request.getContextPath()%>";

   jQuery(function(){
		jQuery('#mydatagrid').datagrid({
				width:1000,
				height:480,
				fit:true,
				fitColumns: true,
				nowrap: false,
				striped: true,
				collapsible: true,
				url: '<%=request.getContextPath()%>/mx/wx/wxMember/json/${accountId}',
				//sortName: 'id',
				//sortOrder: 'desc',
				remoteSort: false,
				singleSelect: true,
				idField: 'id',
				columns:[[
				        {title:'序号', field:'startIndex', width:80, sortable:false},
					{title:'卡号',field:'cardNo', width:120},
					{title:'姓名',field:'name', width:120},
					{title:'手机',field:'mobile', width:120},
					{title:'邮件',field:'mail', width:120},
					{title:'QQ',field:'qq', width:120},
					{title:'地址',field:'address', width:120},
					{title:'创建日期',field:'createDate', width:120},
					{field:'functionKey',title:'功能键',width:120, formatter:formatterKeys}
				]],
				rownumbers: false,
				pagination: true,
				pageSize: <%=com.glaf.core.util.Paging.DEFAULT_PAGE_SIZE%>,
				pageList: [10,15,20,25,30,40,50,100],
				pagePosition: 'both',
				onDblClickRow: onRowClick 
			});

			var p = jQuery('#mydatagrid').datagrid('getPager');
			jQuery(p).pagination({
				onBeforeRefresh:function(){
					//alert('before refresh');
				}
		    });
	});


	function formatterKeys(val, row){
		return "<a href='#' onclick='javascript:editRow("+row.id+");'>修改</a>&nbsp;<a href='#' onclick='javascript:deleteRow("+row.id+");'>删除</a>";
	}


	function deleteRow(id){
		if(confirm("数据删除后不能恢复，确定删除吗？")){
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/wx/wxMember/delete?id='+id,
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
					   jQuery('#mydatagrid').datagrid('reload');
				   }
			 });
		} 
	}

		 
	function addNew(){
	    //location.href="<%=request.getContextPath()%>/wx/wxMember/edit";
	    var link="<%=request.getContextPath()%>/mx/wx/wxMember/edit?accountId=${accountId}&fromUrl=${fromUrl}";
	    //art.dialog.open(link, { height: 420, width: 680, title: "添加记录", lock: true, scrollbars:"no" }, false);
		location.href=link;
	}


	function editRow(rowId){
        //window.open('<%=request.getContextPath()%>/wx/wxMember/edit?id='+row.id);
	    var link = '<%=request.getContextPath()%>/mx/wx/wxMember/edit?accountId=${accountId}&fromUrl=${fromUrl}&id='+rowId;
	    //art.dialog.open(link, { height: 420, width: 680, title: "修改记录", lock: true, scrollbars:"no" }, false);
		location.href=link;
	}

	function onRowClick(rowIndex, row){
            //window.open('<%=request.getContextPath()%>/wx/wxMember/edit?id='+row.id);
	    var link = '<%=request.getContextPath()%>/mx/wx/wxMember/edit?accountId=${accountId}&fromUrl=${fromUrl}&id='+row.id;
	    //art.dialog.open(link, { height: 420, width: 680, title: "修改记录", lock: true, scrollbars:"no" }, false);
		location.href=link;
	}

	function searchWin(){
	    jQuery('#dlg').dialog('open').dialog('setTitle','微会员查询');
	    //jQuery('#searchForm').form('clear');
	}

	function resize(){
		jQuery('#mydatagrid').datagrid('resize', {
			width:800,
			height:400
		});
	}

	function editSelected(){
	    var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    if(rows == null || rows.length !=1){
		  alert("请选择其中一条记录。");
		  return;
	    }
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected ){
		  //location.href="<%=request.getContextPath()%>/wx/wxMember/edit?id="+selected.id;
		  var link = "<%=request.getContextPath()%>/mx/wx/wxMember/edit?accountId=${accountId}&fromUrl=${fromUrl}&id="+selected.id;
		  //art.dialog.open(link, { height: 420, width: 680, title: "修改记录", lock: true, scrollbars:"no" }, false);
		  location.href=link;
	    }
	}

	function viewSelected(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		if(rows == null || rows.length !=1){
			alert("请选择其中一条记录。");
			return;
		}
		var selected = jQuery('#mydatagrid').datagrid('getSelected');
		if (selected ){
		  location.href="<%=request.getContextPath()%>/mx/wx/wxMember/edit?accountId=${accountId}&fromUrl=${fromUrl}&id="+selected.id;
		}
	}

	function deleteSelections(){
		var ids = [];
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].id);
		}
		if(ids.length > 0 ){
		  if(confirm("数据删除后不能恢复，确定删除吗？")){
		    var str = ids.join(',');
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/wx/wxMember/delete?ids='+str,
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
					   jQuery('#mydatagrid').datagrid('reload');
				   }
			 });
		  }
		} else {
			alert("请选择至少一条记录。");
		}
	}

	function reloadGrid(){
	    jQuery('#mydatagrid').datagrid('reload');
	}

	function getSelected(){
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected){
		alert(selected.code+":"+selected.name+":"+selected.addr+":"+selected.col4);
	    }
	}

	function getSelections(){
	    var ids = [];
	    var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    for(var i=0;i<rows.length;i++){
		ids.push(rows[i].code);
	    }
	    alert(ids.join(':'));
	}

	function clearSelections(){
	    jQuery('#mydatagrid').datagrid('clearSelections');
	}

	function loadGridData(url){
            jQuery.post(url,{qq:'xx'},function(data){
            //var text = JSON.stringify(data); 
            //alert(text);
            jQuery('#mydatagrid').datagrid('loadData', data);
            },'json');
	  }

	function searchData(){
            var params = jQuery("#searchForm").formSerialize();
            jQuery.ajax({
                        type: "POST",
                        url: '<%=request.getContextPath()%>/mx/wx/wxMember/json/${accountId}',
                        dataType:  'json',
                        data: params,
                        error: function(data){
                                  alert('服务器处理错误！');
                        },
                        success: function(data){
                                  jQuery('#mydatagrid').datagrid('loadData', data);
                        }
                        });

	    jQuery('#dlg').dialog('close');
	}


	function viewSite(){
			var link = '<%=request.getContextPath()%>/website/wx/member/mobile/${accountId}';
			//art.dialog.open(link, { height: 720, width: 400, title: "预览效果", lock: true, scrollbars:"no" }, false);
			var x=200;
			var y=150;
			var fx = "height=520,width=400,status=0,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+y+",left="+x+",resizable=no,modal=yes,dependent=yes,dialog=yes,minimizable=no";
			if(jQuery.browser.msie){
				window.open(link,  "预览效果", fx);
			} else {
				window.open(link, self, fx, true);
			}
	}
		 
</script>
</head>
<body style="margin:1px;">  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"  > 
	<img src="<%=request.getContextPath()%>/images/window.png">
	&nbsp;<span class="x_content_title">微会员列表</span>
    <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'" 
	   onclick="javascript:addNew();">新增</a>  
    <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
	   onclick="javascript:editSelected();">修改</a>  
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'"
	   onclick="javascript:deleteSelections();">删除</a> 
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-search'"
	   onclick="javascript:searchWin();">查找</a>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-view'"
	   onclick="javascript:viewSite();">预览</a>
   </div> 
  </div> 
  <div data-options="region:'center',border:true">
	 <table id="mydatagrid"></table>
  </div>  
</div>
<div id="edit_dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
	closed="true" buttons="#dlg-buttons">
    <form id="editForm" name="editForm" method="post">
         
    </form>
</div>
<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
	closed="true" buttons="#dlg-buttons">
  <form id="searchForm" name="searchForm" method="post">
  <table class="easyui-form" >
    <tbody>
    <tr>
	<td>卡号</td>
	<td>
        <input id="cardNoLike" name="cardNoLike" class="easyui-validatebox" type="text"></input>
       </td>
     </tr>
    <tr>
	<td>姓名</td>
	<td>
        <input id="nameLike" name="nameLike" class="easyui-validatebox" type="text"></input>
       </td>
     </tr>
    <tr>
	<td>手机</td>
	<td>
        <input id="mobileLike" name="mobileLike" class="easyui-validatebox" type="text"></input>
       </td>
     </tr>
    <tr>
	<td>邮件</td>
	<td>
        <input id="mailLike" name="mailLike" class="easyui-validatebox" type="text"></input>
       </td>
     </tr>
    <tr>
	<td>QQ</td>
	<td>
        <input id="qqLike" name="qqLike" class="easyui-validatebox" type="text"></input>
       </td>
     </tr>
    <tr>
	<td>地址</td>
	<td>
        <input id="addressLike" name="addressLike" class="easyui-validatebox" type="text"></input>
       </td>
     </tr>
    
      </tbody>
    </table>
  </form>
</div>
<div id="dlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:searchData()">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:jQuery('#dlg').dialog('close')">取消</a>
</div>
</body>
</html>
