<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
	String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(
			context);
	pageContext.setAttribute("contextPath", context);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>转换配置</title>

<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript" src="${contextPath}/scripts/glaf-base.js"></script>

<script type="text/javascript">
var contextPath="${contextPath}";
</script>


<script type="text/javascript">

 	

 	jQuery(function() {

    	$("#grid").kendoGrid({

    		"height": x_height,
	        "selectable": "single",
        	"pageable": {
                   "refresh": true,
                   "pageSizes": [5, 10, 15, 20, 25, 50, 100, 200, 500],
                   "buttonCount": 10
                 },
    		dataSource:{},
            
    		"columns": [
				{
	            "field": "transName",
	            "title": "配置名称",
	            "width": "220px",
				"expandable": true,
				"lockable": false,
	            "locked": false
	            },			
				{
	            "field": "createBy",
	            "title": "创建人",
	            "width": "100px",
				"expandable": true,
				"lockable": false,
	            "locked": false
	            },
	            {
	            "field": "createTime",
	            "title": "创建日期",
	            "width": "100px",
	            "lockable": false,
				"align": "center",
				"format": "{0: yyyy-MM-dd}",
				"filterable": {
	              "ui": "datetimepicker"  
	              }
	            },
	            {"command": [{
	                "text": "选中",
	                "name": "selected",
	                "click": function showDetails(e) {
				            	var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
		                        //alert(JSON.stringify(dataItem));
				                //alert(dataItem.id);
				           	 	selectRow(dataItem);
			                }			               
	                }]
          		}]
    	});
		reloadGrid();
     });

	function reloadGrid(){
		var link="${contextPath}/mx/datatransfer/listTransferConfigs";
	    var grid = $("#grid").data("kendoGrid");
		var dataSource = new kendo.data.DataSource({
            transport: {
			    read: {
			      type: "POST",
			      dataType: "json",
			      contentType: "application/json",
			      url: link,
			    },
			    parameterMap: function(options) {
                	return JSON.stringify(options);
            	},
			},
			"serverFiltering": true,
        	"serverSorting": true,
        	"pageSize": 10,
        	"serverPaging": true,
           	schema:{
					"total": "total",
                	"data": "rows"
    		}
		});
		grid.setDataSource(dataSource);
 	}

 	function selectRow(dataItem){

 		var parent_window = getOpener();

		var transId = parent_window.document.getElementById("transId_");
   		var transName = parent_window.document.getElementById("transName");
   		var srcId = parent_window.document.getElementById("srcId_");
   		transId.value = dataItem.id;
   		transName.value = dataItem.transName;
   		srcId.value = dataItem.dataSrcId;
	    window.close();
 	}


// 	function getParameter(name){
//     var params = {};
//     var search = window.location.search;
//     if(search && search.length > 1){
//       search = search.substring(1);
//       search = search.split("&");
//       var s;
//       $.each(search, function(i, v){
//         s = v.split("=");
//         params[s[0]] = s[1];
//       });
//     }
//     return params[name];
// }

//    function callBack2(iconClass){
// 	var fn = window.getParameter("fn"), tragetId = window.getParameter("tragetId");
// 	var $parent = window.opener || window.parent;
// 	if(fn && $parent[fn]){
// 		$parent[fn].call($parent.document.getElementById("tragetId"), iconClass);
// 	}
// }

 </script>
</head>
<body>
	<input type="hidden" id="nodeId" name="nodeId" value="" > 
	<div class="easyui-layout" data-options="fit:true">  

	   <div data-options="region:'center'"> 
		<div class="easyui-layout" data-options="fit:true">  
		  <div data-options="region:'center',border:false">
			 <div id="grid"></div>
		  </div>  
		</div>
	  </div>

	</div>
</body>
</html>