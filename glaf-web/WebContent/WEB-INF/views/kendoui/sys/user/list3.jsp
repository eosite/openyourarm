<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%> 
<script type="text/javascript">

    $(document).ready(function () {           
        var crudServiceBaseUrl = "<%=request.getContextPath()%>/mx/system/user",
        dataSource = new kendo.data.DataSource({
            transport: {
                read:  {
                    url: crudServiceBaseUrl + "/json",
                    dataType: "json"
                },
                update: {
                    url: crudServiceBaseUrl + "/update",
                    dataType: "json"
                },
                destroy: {
                    url: crudServiceBaseUrl + "/delete",
                    dataType: "json"
                },
                create: {
                    url: crudServiceBaseUrl + "/create",
                    dataType: "json"
                },
                parameterMap: function(options, operation) {
                    if (operation !== "read" && options.models) {
                        return {models: kendo.stringify(options.models)};
                    }
                }
            },
            batch: true,
            pageSize: 10,
			serverPaging: true,
            serverFiltering: true,
            serverSorting: true,
            schema: {
                model: {
                    id: "actorId",
                    fields: {
                        actorId: { editable: false, nullable: false },
                        name: { editable: true, nullable: false, validation: { required: true } },
                        deptName: { editable: false, type: "string"},
                        lastLoginDate: { editable: false, type: "string" },
                        telephone: { editable: true, type: "string", nullable: true}
                    }
                }
            }
        });

		$("#grid").kendoGrid({
			dataSource: dataSource,
			navigatable: true,
			pageable: true,
			editable: true, 
			sortable: true,
            reorderable: true,
            groupable: false,
            resizable: true,
            filterable: true,
            columnMenu: true,
			height: "420",
			toolbar: ["create", "save", "cancel"],
			columns: [{
						field:"actorId",
						title: "账号",
						filterable: true,
						width: 90
					},{
						field:"name",
						title: "姓名",
						filterable: true,
						width: 150
					},{
						field: "telephone",
						title: "电话",
						width: 150
					},{
						field:"deptName",
						title: "部门",
						filterable: false,
						width: 180
					},{
						field: "lastLoginDate",
						title: "最后登录日期",
						width: 120
					} 
				]
			 });
     });

 </script>
</head>
<body>
<br>
<div id="main_content">
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="用户列表">&nbsp;
用户列表</div> 
<br>
<div id="grid"></div>
</div>     
</body>
</html>

 
