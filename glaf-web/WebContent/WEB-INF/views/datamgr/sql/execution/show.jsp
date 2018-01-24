<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据列表</title>
<script type="text/ecmascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/ecmascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/ecmascript" src="<%=request.getContextPath()%>/scripts/jqgrid/jquery.jqGrid.min.js"></script>
<script type="text/ecmascript" src="<%=request.getContextPath()%>/scripts/jqgrid/js/i18n/grid.locale-en.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/jqueryui/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/jqgrid/css/ui.jqgrid.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<script type="text/javascript">
    function changeDB(){
		var databaseId = document.getElementById("databaseId").value;
        location.href="<%=request.getContextPath()%>/mx/datamgr/sql/execution/show?databaseId="+databaseId+"&sqlDefId=${sqlDefinition.id}";
    }
	function saveCountResult(){
		if(document.getElementById("databaseId").value==""){
		   alert("请选择要执行SQL的数据库");
		   document.getElementById("databaseId").focus();
		   return;
	   }
       if (confirm("确定要在该数据库上执行SQL操作并保存统计结果吗？")) {
		  var databaseId = document.getElementById("databaseId").value;
	      var link = "<%=request.getContextPath()%>/mx/datamgr/sql/execution/saveCountResult?databaseId="+databaseId+"&sqlDefId=${sqlDefinition.id}";
	      //var params = jQuery("#iForm").formSerialize();
		  jQuery.ajax({
				   type: "POST",
				   url: link,
				   dataType:  'json',
				   //data: params,
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
				   }
			 });
       }
	}

    function openChat(){
	    window.open("<%=request.getContextPath()%>/mx/bi/chart/showChart?chartId=179551&sqlDefId=${sqlDefinition.id}&databaseId=${databaseId}&x_query=true");
    }

	function exportData(){
		var databaseId = document.getElementById("databaseId").value;
        window.open("<%=request.getContextPath()%>/mx/datamgr/sql/execution/exportData?databaseId="+databaseId+"&sqlDefId=${sqlDefinition.id}");
	}

	function exportJsonData(){
		var databaseId = document.getElementById("databaseId").value;
        window.open("<%=request.getContextPath()%>/mx/datamgr/sql/execution/exportJsonData?databaseId="+databaseId+"&sqlDefId=${sqlDefinition.id}");
	}

	function exportSqlite(){
		var databaseId = document.getElementById("databaseId").value;
        window.open("<%=request.getContextPath()%>/mx/datamgr/sql/execution/exportSqlite?databaseId="+databaseId+"&sqlDefId=${sqlDefinition.id}");
	}
</script>
</head>
<body>
&nbsp;切换数据库&nbsp;
<select id="databaseId" name="databaseId" onchange="javascript:changeDB();">
<option value="">----请选择----</option>    
<c:forEach items="${databases}" var="item">
<option value="${item.id}">${item.title}</option>     
</c:forEach>
</select>
<script type="text/javascript">
     document.getElementById("databaseId").value="${databaseId}";
</script>
<c:if test="${not empty databaseId}">
&nbsp; <input type="button" value="保存统计结果" onclick="javascript:saveCountResult();" class="btnGray">
</c:if>
<c:if test="${not empty databaseId and not empty sqlDefinition}">
&nbsp; <input type="button" value="数据变化曲线图" onclick="javascript:openChat();" class="btnGray">
</c:if>
<c:if test="${not empty databaseId and not empty sqlDefinition}">
&nbsp; <input type="button" value="导出数据" onclick="javascript:exportData();" class="btnGray">
</c:if>
<c:if test="${not empty databaseId and not empty sqlDefinition}">
&nbsp; <input type="button" value="导出JSON数据" onclick="javascript:exportJsonData();" class="btnGray">
</c:if>
<c:if test="${not empty databaseId and not empty sqlDefinition}">
&nbsp; <input type="button" value="导出SQLite数据" onclick="javascript:exportSqlite();" class="btnGray">
</c:if>
<table id="jqGrid"></table>
<div id="jqGridPager"></div>
<c:forEach items="${sqlDefinitions}" var="child">
  <br/>
  <table id="child_${child.id}"></table>
  <div id="child_pager_${child.id}"></div>
</c:forEach>
<br/>
<br/>
<script type="text/javascript"> 
    
        jQuery(document).ready(function () {
			
			// master grid
            jQuery("#jqGrid").jqGrid({
                url: '<%=request.getContextPath()%>/mx/datamgr/sql/execution/json?sqlDefId=${sqlDefinition.id}&databaseId=${databaseId}&gridType=jqgrid',
                datatype: "json",
                colModel: [
                    { label: 'Index', name: 'startIndex', width: 75 }
					<c:forEach items="${sqlDefinition.columns}" var="column">
                    ,{ 
					    label: '${column.columnLabel}', 
					    name: '${column.columnLabel}', 
					    <c:if test="${column.primaryKey}">
					    key: true, 
					    </c:if>
					    width:150
					}
				    </c:forEach>
                ],
                width: ${sqlDefinition.width},
                height: 250,
                rowNum: 10,
                onSelectRow: function(rowid, selected) {
					//var grid = $("#jqGrid");
                    //var rowKey = grid.getGridParam("selrow");
					//alert("rowid:"+rowid);
					//alert("selected:"+selected);
					//alert("rowKey:"+rowKey);
					if(rowid != null) {
					  var rowData=$("#jqGrid").jqGrid('getRowData', rowid);
                      //$.each(rowData, function(key, val) {        
						  //alert(key+"="+rowData[key]);      
					  //});
                      var jsonParam = JSON.stringify(rowData);
					  //alert(jsonParam);
					  //jsonParam = base64encode(jsonParam);
					  jQuery.base64.is_unicode=true;
					  jsonParam = jQuery.base64.encode(jsonParam);
					  //alert(jQuery.base64.decode(jsonParam));
				      <c:forEach items="${sqlDefinitions}" var="child">
                        var link_${child.id} = '<%=request.getContextPath()%>/mx/datamgr/sql/execution/json?sqlDefId=${child.id}&databaseId=${databaseId}&gridType=jqgrid&rowKey='+rowid+"&jsonParam="+jsonParam;
						jQuery("#child_${child.id}").jqGrid('setGridParam',{url: link_${child.id}, method:'POST', datatype: 'json'});
						//jQuery("#child_${child.id}").jqGrid('setCaption', 'Detail Grid::'+rowid);
						jQuery("#child_${child.id}").trigger("reloadGrid");
                      </c:forEach>
					}					
				}, // use the onSelectRow that is triggered on row click to show a details grid
				onSortCol : clearSelection,
				onPaging : clearSelection,
                pager: "#jqGridPager"
            });
        });
		 

	<c:forEach items="${sqlDefinitions}" var="child">
	    $("#child_${child.id}").jqGrid({
			url: '<%=request.getContextPath()%>/empty.json',
            mtype: "GET",
            datatype: "json",
            page: 1,
			colModel: [
                    { label: 'Index', name: 'startIndex', width: 75 }
					<c:forEach items="${child.columns}" var="col">
                    ,{ 
				        label: '${col.columnLabel}', 
						name: '${col.columnLabel}', 
						<c:if test="${col.primaryKey}">
					    key: true, 
					    </c:if>
						width: 150 
					}
				    </c:forEach>
			],
			width: ${child.width},
			rowNum: 10,
			loadonce: true,
			height: 250,
			viewrecords: true,
			caption: '${child.title}',
			pager: "#child_pager_${child.id}"
		});
	</c:forEach>

		function clearSelection() {
			<c:forEach items="${sqlDefinitions}" var="child">
			jQuery("#child_${child.id}").jqGrid('setGridParam',{url: "<%=request.getContextPath()%>/empty.json", datatype: 'json'});
			jQuery("#child_${child.id}").jqGrid('setCaption', '${child.title}');
			jQuery("#child_${child.id}").trigger("reloadGrid");	
			</c:forEach>
		}
</script> 
</body>
</html>