<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html class="k-blueopal">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%> 
<script type="text/javascript">
                
    $(document).ready(function() {
        $("#grid").kendoGrid({
            dataSource: {
                type: "json",
                transport: {
                    read: {   dataType: "json", url:"<%=request.getContextPath()%>/mx/system/user/json"},
					parameterMap: function(options, operation) {
						if (operation !== "read" && options.models) {
							return {models: kendo.stringify(options.models)};
						}
					}
                },
				total: "total",
				data: "data",
                groups: "data",
                schema: {
                    model: {
                        fields: {
							actorId: { type: "string" },
							name: { type: "string" },
							deptName: { type: "string" },
							lastLoginDate: { type: "string" },
							telephone: { type: "string" }
                        }
                    }
                },
                pageSize: 10,
                serverPaging: true,
                serverFiltering: true,
                serverSorting: true
            },
            height: 420,
            filterable: true,
            sortable: true,
            pageable: true,
            columns: [{
                    field:"actorId",
					title: "账号",
                    filterable: true
                },
				{
                    field:"name",
					title: "姓名",
                    filterable: true
                },{
                    field:"deptName",
					title: "部门",
                    filterable: false
                },
                {
                    field: "lastLoginDate",
                    title: "最后登录日期"
                }, {
                    field: "telephone",
                    title: "电话"
                } 
            ]
        });
    });

 </script>
</head>
<body>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="用户列表">&nbsp;
用户列表</div> 
<div id="grid"></div>
     
</body>
</html>

 
