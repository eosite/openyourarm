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
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
 
   <c:if test="${table.treeFlag == 'Y'}">
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
						{title:'审核状态', field:'business_status_', width:60, sortable:false, formatter: formatterStatus},
					    {field:'functionKey', title:'功能键', width:180, formatter:formatterKeys }
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
					 auditRow(row.uuid);
				}
			});

	});


	function formatterStatus(val, row){
		if(val == 9){
			return "<font color='green'>通过</font>";
		}else if(val == -1){
			return "<font color='red'>不通过</font>";
		} else {
            return "<font color='blue'>待审核</font>";
		}
	}


	function formatterKeys(val, row){
		var str = "<a href='javascript:auditRow(\""+row.uuid+"\");'><img src='${contextPath}/images/audit.png' border='0'>审核</a>";
		<c:if test="${table.auditFlag == 'Y'}">
		  if(row.business_status_ != 0){
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
			var link="${contextPath}/mx/tableData/auditdatalist?tableId=${table.tableId}&topId="+topId;
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

	function auditRow(uuid){
		link="${contextPath}/mx/tableData/audit?tableId=${tableId}&topId=${topId}&uuid="+uuid;
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "审核记录",
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
	    jQuery.ajax({
			type: "POST",
			url:  url,
			dataType:  'json',
			error: function(data){
				alert('服务器处理错误！');
			},
			success: function(data){
				jQuery('#mydatagrid').datagrid('loadData', data);
			}
		});
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
			var link="${contextPath}/mx/tableData/auditdatalist?tableId=${table.tableId}&topId="+selected.id;
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
	    <div class="toolbar-backgroud"  style="height:32px"> 
			<img src="${contextPath}/images/window.png">
			&nbsp;<span class="x_content_title">数据列表</span> 
				<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-audit'"
				   onclick="javascript:auditSelected();">审核</a>
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
