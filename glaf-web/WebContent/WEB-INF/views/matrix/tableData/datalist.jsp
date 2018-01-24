<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
	request.setAttribute("contextPath", request.getContextPath());
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.base64.js"></script>
<script type="text/javascript">
   var contextPath="<%=request.getContextPath()%>";
   <c:if test="${table.treeFlag eq 'Y'}">
   var setting = {
			async: {
				enable: true,
				url:"${contextPath}/mx/tableData/treeJson?tableId=${tableId}",
				dataFilter: filter
			},
			callback: {
				onClick: zTreeOnClick
			}
		};
  
  	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			childNodes[i].icon="${contextPath}/images/basic.gif";
			if(childNodes[i].level == 2){
               //childNodes[i].icon="${contextPath}/images/bricks.png";
			}
		}
		return childNodes;
	}


    function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		jQuery("#nodeId").val(treeNode.id);
		//var p = jQuery('#mydatagrid').datagrid('getPager');
		//alert(p.pageSize);
		//var link = "${contextPath}/mx/tableData/data?tableId=${tableId}&parentId="+treeNode.id;
		//loadGridData(link);
		jQuery('#mydatagrid').datagrid({
			queryParams: {
				tableId: '${tableId}',
				parentId: treeNode.id
			}
		});

 	}

    jQuery(document).ready(function(){
		jQuery.fn.zTree.init(jQuery("#myTree"), setting);
	});

	</c:if>

   jQuery(function(){
		jQuery('#mydatagrid').datagrid({
				width:1000,
				height:450,
				fit:true,
				fitColumns: true,
				nowrap: false,
				striped: true,
				collapsible: true,
				url: "${contextPath}/mx/tableData/json?tableId=${tableId}&topId=${topId}",
				remoteSort: false,
				singleSelect: true,
				idField: 'id',
				columns:[[
				           {title:'序号', field:'startIndex', width:60, sortable:false},
                        <c:forEach  var="field" items="${columns}" >
                         <c:if test="${!empty field.columnName}">
						   {title:'${field.title}', field:'${field.lowerColumnName}', width:150, sortable:true},
					     </c:if>
                        </c:forEach>
						<c:if test="${topId == 0}">
						   {title:'审核状态', field:'business_status_', width:60, sortable:false, formatter: formatterStatus},
						</c:if>
					       {field:'functionKey', title:'功能键', width:210, formatter:formatterKeys }
				]],
				rownumbers: false,
				pagination: true,
				pageSize: 10,
				pageList: [10,15,20,25,30,40,50,100,500,1000],
				pagePosition: 'both'
			});

			var p = jQuery('#mydatagrid').datagrid('getPager');
			jQuery(p).pagination({
				onBeforeRefresh:function(){
                    alert('before refresh');
 				}
		    });

			$('#mydatagrid').datagrid({
				onDblClickRow: function(index, row){
					 editRow(row.uuid);
				}
			});

	});

		 
	function addNew(){
		link="${contextPath}/mx/tableData/edit?tableId=${tableId}&topId=${topId}";
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "新增记录",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['880px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}


	function formatterStatus(val, row){
		if(val == 9){
			return "<font color='green'>通过</font>";
		} else if(val == -1){
			return "<font color='red'>不通过</font>";
		} else {
            return "<font color='blue'>待审核</font>";
		}
	}


	function formatterKeys(val, row){
		var str = "";
		<c:if test="${canEdit == true}">
		if(row.business_status_ != 9){
		    str = "<a href='javascript:editRow(\""+row.uuid+"\");'><img src='${contextPath}/images/edit.gif' border='0'>修改</a>&nbsp;<a href='javascript:deleteRow(\""+row.uuid+"\");'><img src='${contextPath}/images/remove.png' border='0'>删除</a>";
		}
		</c:if>
        <c:if test="${table.auditFlag == 'Y'}">
		  if(row.business_status_ == -1 || row.business_status_ == 9){
		     str = str + "&nbsp;<a href='javascript:openAuditWin(\""+row.id+"\");'><img src='${contextPath}/images/comments.png' border='0'>审核意见</a>";
		  }
        </c:if>
		<c:forEach var="table" items="${correlations}" >
		<c:if test="${table.tableCorrelation.relationshipType == 'OneToMany'}">
           str = str + "&nbsp;<a href='javascript:openMxWin${table.tableId}(\""+row.id+"\");'><img src='${contextPath}/images/list.gif' border='0'>${table.title}</a>";
		</c:if>
		</c:forEach >
	    return str;
	}

    function openAuditWin(id){
        var link="${contextPath}/mx/tableData/comments?tableId=${table.tableId}&topId="+id;
		jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "审核意见",
				closeBtn: [0, true],
				shade: [0.8, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['20px',''],
				fadeIn: 100,
				area: ['680px', (jQuery(window).height() - 50) +'px'],
				iframe: {src: link}
		});
	}

  <c:forEach var="table" items="${correlations}" >
    <c:if test="${table.tableCorrelation.relationshipType == 'OneToMany'}">
    function openMxWin${table.tableId}(topId){
			var link="${contextPath}/mx/tableData/datalist?tableId=${table.tableId}&topId="+topId;
			jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "${table.title}",
				closeBtn: [0, true],
				shade: [0.8, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['20px',''],
				fadeIn: 100,
				area: ['1080px', (jQuery(window).height() - 50) +'px'],
				iframe: {src: link}
			});
	}

   </c:if>
 </c:forEach >

	function editRow(uuid){
		link="${contextPath}/mx/tableData/edit?tableId=${tableId}&topId=${topId}&uuid="+uuid;
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑记录",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['880px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}


	function reloadGrid(){
		jQuery('#mydatagrid').datagrid({
			queryParams: {
				tableId: '${tableId}',
				topId: '${topId}',
				parentId: jQuery("#nodeId").val()
			}
		});
	}


	function resize(){
		jQuery('#mydatagrid').datagrid('resize', {
			width:800,
			height:400
		});
	}

	function removeSelected(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    if(rows == null || rows.length !=1){
		  alert("请选择其中一条记录。");
		  return;
	    }
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected ){
		   deleteRow(selected.uuid);
	    }
	}

	function deleteRow(uuid){
		if(confirm("删除数据不能恢复，确定删除吗？")){
			jQuery.ajax({
				   type: "POST",
				   url: '${contextPath}/mx/tableDataDelete/deleteById?tableId=${tableId}&topId=${topId}&uuid='+uuid,
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
					   if(data.statusCode == 200){
						   //window.location.reload();
						   jQuery('#mydatagrid').datagrid('reload');
					   }
				   }
			 });
		}
	}

	function editSelected(){
	    var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    if(rows == null || rows.length !=1){
		  alert("请选择其中一条记录。");
		  return;
	    }
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected ){
		   editRow(selected.uuid);
	    }
	}


<c:forEach var="table" items="${correlations}" >
  <c:if test="${table.tableCorrelation.relationshipType == 'OneToMany'}">
    function openWin${table.tableId}(){
		var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    if(rows == null || rows.length !=1){
		  alert("请选择其中一条记录。");
		  return;
	    }
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected){
			var link="${contextPath}/mx/tableData/datalist?tableId=${table.tableId}&topId="+selected.id;
			jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "${table.title}",
				closeBtn: [0, true],
				shade: [0.8, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['20px',''],
				fadeIn: 100,
				area: ['1080px', (jQuery(window).height() - 50) +'px'],
				iframe: {src: link}
			});
		}
	}
  </c:if>
</c:forEach >

    function importXls(){
        var link="${contextPath}/mx/tableData/showImport?tableId=${table.tableId}";
		jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "${table.title}",
				closeBtn: [0, true],
				shade: [0.8, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['80px',''],
				fadeIn: 100,
				area: ['680px', (jQuery(window).height() - 150) +'px'],
				iframe: {src: link}
			});
	}

    function exportXls(){
        window.open("${contextPath}/mx/tableData/exportXls?tableId=${tableId}&topId=${topId}");
    }

</script>
</head>
<body>
<input type="hidden" id="tableId" name="tableId" value="${tableId}" >
<input type="hidden" id="parentId" name="parentId" value="${parentId}" >
<input type="hidden" id="nodeId" name="nodeId" value="${nodeId}" > 
<input type="hidden" id="topId" name="topId" value="${topId}" >
<div class="easyui-layout" data-options="fit:true"> 
   <c:if test="${table.treeFlag == 'Y'}">
    <div data-options="region:'west',split:true" style="width:240px;">
	  <div class="easyui-layout" data-options="fit:true">  
			 <div data-options="region:'center',border:false">
			    <ul id="myTree" class="ztree"></ul>  
			 </div> 
        </div>  
	</div> 
   </c:if>
    <div data-options="region:'center'"> 
	 <div class="easyui-layout" data-options="fit:true">  
	  <div data-options="region:'center',border:false">
	        <div class="toolbar-backgroud"  style="margin-top:2px;height:30px"> 
			&nbsp;<img src="${contextPath}/images/window.png">
			&nbsp;<span class="x_content_title">数据列表</span>
			    <c:if test="${canEdit == true}">
				<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-add'" 
				   onclick="javascript:addNew();">新增</a>  
				<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
				   onclick="javascript:editSelected();">修改</a>
				<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-remove'"
				   onclick="javascript:removeSelected();">删除</a>
			    </c:if>
				<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon_export_xls'"
				   onclick="javascript:importXls();">导入Excel</a>
			    <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon_export_xls'"
				   onclick="javascript:exportXls();">导出Excel</a>
			    <c:forEach var="table" items="${correlations}" >
			    <c:if test="${table.tableCorrelation.relationshipType == 'OneToMany'}">
                <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-list'"
				   onclick="javascript:openWin${table.tableId}();">${table.title}</a>
			    </c:if>
			    </c:forEach >
		    </div> 
		    <table id="mydatagrid"></table>
		    <br>
	  </div>  
	</div>
  </div>
</div>
</body>
</html>
