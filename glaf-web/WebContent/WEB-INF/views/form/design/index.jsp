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
<title>UI设计</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%> 
<script type="text/javascript">

</script>
    
</head>
<body>
    <div id="layoutMain" style="height:100%;">
    	<ul>
			<li class="k-state-active">
				选择布局 
			</li>
			<li>
				选择表单
			</li>
		</ul>
		<!-- 第一选卡项-->
		<div style="overflow: auto;height:100%">
		        <div id="listView"></div>
		        <div id="pager" class="k-pager-wrap"></div>
	    </div>
		<!-- 第二选卡项-->
	    <div>
	    	选择表单
	    </div>
	    
    </div>

    <script type="text/x-kendo-template" id="template">
        <div class="product">
            <img src="<%=request.getContextPath()%>/#=imagePath#" alt="#:name# " />
            <h3>#:name#</h3>
            <p onclick="window.open('design?layoutId=#:id#');">#:name#</p>
        </div>
    </script>

    <script>
        $(function() {
        	
        	$("#layoutMain").kendoTabStrip({
    			animation:  {
    				open: {
    					effects: "fadeIn"
    				}
    			}
          });
       	 
            var dataSource = new kendo.data.DataSource({
                transport: {
                    read: {
                        url: "<%=request.getContextPath()%>/rs/form/layout/json",
                        dataType: "json"
                    }
                },
                pageSize: 6
            });
            $("#pager").kendoPager({
                dataSource: dataSource
            });
            $("#listView").kendoListView({
                dataSource: dataSource,
                template: kendo.template($("#template").html())
            });
        });
    </script>

    <style scoped>
     	 #listView {
            padding: 10px 5px;
            margin-bottom: -1px;
            min-height: 500px;
        }
        .product {
            float: left;
            position: relative;
            width: 300px;
            height: 250px;
            margin: 0 8px;
            padding: 0;
        }
        .product img {
            width: 300px;
            height: 200px;
        }
        .product h3 {
            margin: 0;
            padding: 3px 5px 0 0;
            max-width: 96px;
            overflow: hidden;
            line-height: 1.1em;
            font-size: .9em;
            font-weight: normal;
            text-transform: uppercase;
            color: #999;
        }
        .product p {
            visibility: hidden;
        }
        .product:hover p {
            visibility: visible;
            position: absolute;
            width: 300px;
            height: 200px;
            top: 0;
            margin: 0;
            padding: 0;
            line-height: 100px;
            vertical-align: middle;
            text-align: center;
            color: #fff;
            background-color: rgba(0,0,0,0.75);
            transition: background .2s linear, color .2s linear;
            -moz-transition: background .2s linear, color .2s linear;
            -webkit-transition: background .2s linear, color .2s linear;
            -o-transition: background .2s linear, color .2s linear;
        }
    </style>


    
    
</body>
</html>
