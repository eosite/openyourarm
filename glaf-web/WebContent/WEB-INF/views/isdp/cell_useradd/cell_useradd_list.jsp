<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript">
	
	var fields = {},columns = JSON.parse('${results.columns}');
	$(function(){
		if("${results.sysListDefine.showTooltip}"=="1"){
			var tooltip = $("#tooltip").kendoTooltip({
	            position: "bottom",
	            content: kendo.template($("#template").html())
	        }).data("kendoTooltip");
		}
		
		for(var i=0;i<columns.length;i++){
			columns[i].width="150";
			
			var type;
			if(columns[i].dtype=="string"){
				type = "string";
			}else if(columns[i].dtype=="i4"){
				type = "number";
			}else if(columns[i].dtype=="datetime"){
				type = "date";
			}
			fields[columns[i].field]={type:type};
		}
		
		var dataSource = new kendo.data.DataSource({
			type: "json",
            transport: {
            	read:{
            		contentType: "application/json",
                    type: "POST",
                    dataType:"JSON",
                    url: "${pageContext.request.contextPath}/rs/isdp/cellUserAddRest/getListData?databaseCode=${param.databaseCode}&tableName=${results.table.tableName}",
            	},
                  parameterMap : function(options, operation) {
                      if (operation == "read") {
                          var parameter = {
                              page : options.page,    //当前页
                              pageSize : options.pageSize//每页显示个数
                          };
                          return kendo.stringify(parameter);
                      }
                  }
            },
            schema:{
            	total:"total",
            	data:"rows",
            	model:{
            		fields:fields
            	},
            },
            pageSize: "${results.sysListDefine.pageSize}",
           	serverPaging: true,
           	serverSorting: true
		});
		
		
		$("#cell_user_add_grid").kendoGrid({
			dataSource:dataSource,
            height: document.documentElement.clientHeight-70,
            groupable: false,
            sortable: true,
            resizable:true,
            reorderable:true,
            selectable:"row",
            columns: columns,
            change:function(e){
            	var row = this.select();
            	if("${results.sysListDefine.showTooltip}"=="1"){
            		tooltip.show($(row));
            	}
            	
            	var data = this.dataItem(row);
            	for(var i=0;i<columns.length;i++){
            		$("#"+columns[i].field).val(data[columns[i].field]);
            	}
            }
        });
		
		if("${results.sysListDefine.pageable}"=="1"){
			var grid = $("#cell_user_add_grid").data("kendoGrid");
			grid.setOptions({
				pageable:{
		        	refresh:true,
		        	pageSizes:[20,50,100]
				}
			});
		}else{
			var grid = $("#cell_user_add_grid").data("kendoGrid");
			grid.setOptions({
				pageable:false
			});
		}
		
	});
</script>
</head>
<body>
	<div id="main_content">
	 	 <div class="x_content_title">
	 	 <br/>
		 <img src="${pageContext.request.contextPath}/images/window.png">&nbsp;
		 ${results.table.name }
		 </div>
		 <br/>
		 <div id="cell_user_add_grid"></div>
	</div>
	<div id="tooltip"></div>
     <script type="text/x-kendo-template" id="template">
		<table id="tooltipTable" cellspacing="5">
         <c:forEach items="${results.columns}" var="column" varStatus="sta">
         	<c:if test="${sta.index%2==1 }">
         		<tr>
         	</c:if>
         	<td align="right"><label>${column.title }</label></td><td><input id="${column.field }" class="k-textbox" style="width:250px" readonly/></td>
         	<c:if test="${sta.index%2==0 }">
         	</tr>
         	</c:if>
         </c:forEach>
       	</table>
	</script>
</body>
</html>