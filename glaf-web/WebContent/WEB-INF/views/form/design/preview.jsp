<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.glaf.form.core.domain.FormDesignEntity" %>
<%
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1    
	 response.setHeader("Pragma","no-cache"); //HTTP 1.0    
	 response.setDateHeader ("Expires", 0);  //
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UI设计</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/map.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/list.js"></script>
<style type="text/css">
	html {
		width: 100%;
		height: 100%;
		margin: 0;
	}
	
	body {
		width: 100%;
		height: 99%;
		margin: 0;
	}
</style>
<script type="text/javascript">
  <%
 	 FormDesignEntity formDesignEntity = (FormDesignEntity)request.getAttribute("formDesignEntity");		
  %>
//获取的布局html
var divs  = "<%=formDesignEntity.getLayoutHtml()%>" ;
//获取的布局js
var jsArraysJson = "<%=formDesignEntity.getLayoutJS()%>" ;
//元素html
var docHtml = "<%=formDesignEntity.getAddElementHtml()%>" ;
//元素js
var docJs = "<%=formDesignEntity.getAddElementJS()%>" ;

$(document).ready(function() {
		//布局html
	 	document.getElementById("htmlDiv").innerHTML = divs ;
	 	
		//布局js
		var jsArrays = jQuery.parseJSON(jsArraysJson);
		for (var i = 0;i < jsArrays.length ;i++ ){
			var jsArray = jsArrays[i] ;
			var id = "#"+jsArray.id ;
			$(id).height("100%");
			$(id).kendoSplitter({
					orientation: jsArray.orientation,
					panes: jsArray.panes
			});
		}
		
		//元素html
		var docArrays = jQuery.parseJSON(docHtml);
		for (var i = 0; i < docArrays.length; i++) {
			var obj = docArrays[i];
			document.getElementById(obj.id).innerHTML = obj.html ;
		}
		
		var ids = new Array();
		//元素js
		var docJsArrays = jQuery.parseJSON(docJs);
		for (var i = 0; i < docJsArrays.length; i++) {
			var docJsArray = docJsArrays[i];
			var id = "#" + docJsArray.id ;
			ids.push(id);
			var attrObj = docJsArray.attr ; 
			//$(id).attr("data-role",attrObj.type) ;
			var attr = attrObj.attr ;
			for (var p in attr) {
				$(id).attr(p,attr[p]) ;
			}
			//$(id).attr("data-bind","value: selectedProduct,source: products,visible: isVisible,enabled: isEnabled");
			//$(id).attr("data-text-field","ProductName");
		}
		
		//元素datasources
		for (var i = 0; i < ids.length; i++) {
			var viewModel = kendo.observable({  
		        isVisible: true,
		        isEnabled: true,
		        products: new kendo.data.DataSource({
		            transport: {
		                read: {
		                    url: "http://demos.telerik.com/kendo-ui/service/products",
		                    dataType: "jsonp"
		                }
		            }
		        })
		    });
		    kendo.bind($(ids[i]), viewModel);
		}
		
			
});
	</script>

</head>

<body>
	<div id="htmlDiv" style="height:100%;">
		
	</div>
</body>
</html>

