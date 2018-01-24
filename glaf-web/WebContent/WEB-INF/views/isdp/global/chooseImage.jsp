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
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图片列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
</head>
<body >
    <div class="demo-section k-header">
        <div id="listView"></div>
        <div id="pager" class="k-pager-wrap"> </div>
    </div>

	<script type="text/x-kendo-tmpl" id="template">
			<div class="product">
				<img src="<%=request.getContextPath()%>#:imagePath#" alt="#:name# image" />
				<h3>#:name#</h3>
			</div>
	</script>

	<script>
        $(document).ready(function() {
            var dataSource = new kendo.data.DataSource({
                    transport: {
                        read: {
                            url: "<%=request.getContextPath()%>/mx/image/data?rows=10000",
                            dataType: "json"
                        }
                    },
                    pageSize: 36
                });

            $("#pager").kendoPager({
                dataSource: dataSource
            });

            $("#listView").kendoListView({
                dataSource: dataSource,
                selectable: "single",
                change: onChange,
                template: kendo.template($("#template").html())
            });
            
            function onChange() {
				var selectedItem = null;
                var data = dataSource.view(),
                    selected = $.map(this.select(), function(item) {
					    selectedItem = data[$(item).index()];
                        return selectedItem.imagePath;
                    });
                	var retFn = "${retFn}";
                	if(retFn != ""){
                		if(window.opener)
                			window.opener[retFn](selected.join(","));
                		else
                			window.parent[retFn](selected.join(","));
                		window.close();
                		return ;
                	}    
                
					if(!!window.ActiveXObject || "ActiveXObject" in window){
						//IE
						parent_window = parent.dialogArguments;
					}else{
						parent_window = window.opener;
					}
					
					if(parent_window != null){
	                    var x_elementId = parent_window.document.getElementById("${elementId}");
						x_elementId.value = selected.join(", ");
						
					} else {
						var x_elementId = parent.window.document.getElementById("${elementId}");
						x_elementId.value = selected.join(", ");
					}
            }
    });

	 
    </script>

    <style>
        .demo-section {
            padding: 10px;
            width: 982px;
        }
        
        .product
        {
            float: left;
            width: 90px;
            height: 75px;
            margin: 0;
            padding: 5px;
            cursor: pointer;
        }
        .product img
        {
            float: left;
			width: 16px;
            height: 16px;
        }
        .product h3
        {
            margin: 0;
            padding: 10px 0 0 10px;
            font-size: .9em;
            overflow: hidden;
            font-weight: normal;
            float: left;
            max-width: 100px;
        }
        .k-pager-wrap
        {
            border-top: 0;
        }
        .demo-section .k-listview:after
        {
            content: ".";
            display: block;
            height: 0;
            clear: both;
            visibility: hidden;
        }
       .demo-section .k-listview
        {
            padding: 0;
            min-width: 290px;
            min-height: 260px;
        }
    </style>
 
</body>
</html>