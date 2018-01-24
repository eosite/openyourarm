<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.glaf.form.core.domain.FormLayout" %>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    FormLayout formLayout = (FormLayout)request.getAttribute("formLayout");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UI设计</title>
<style type="text/css">
        html
        {
			 width:98%;
			 height:100%;
			 margin:0;
        }
        body
        {	
			width:100%;
            height:99%;
            margin:0; 
        }
		
</style>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/map.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/list.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/design.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/autocomplete.js"></script>
	<script type="text/javascript">
	//全局元素
	var contextPath = "<%=request.getContextPath()%>" ;
	
$(document).ready(function() {
	//初始化页面
	initLayout();
	
	//初始化布局
	var jsons = <%=formLayout.getJson()%> ;
	parseExcelJson(jsons);
	//var returnList = parseExcelJson(jsons);
	//divs = returnList.get(0);
	//jsArrays = returnList.get(1);
		
	//jsArraysJson =  JSON.stringify(jsArrays) ;
	//divsJson =  divs[0].outerHTML ;
	
});
	</script>

</head>

<body >
	<input id="hiddenID" type="hidden" value="" >
	<input id="id" type="hidden" value="">
	<div style="width: 99%;">
            <ul id="menu" style="width: 100%;">
                <li>
                  	<span onclick="saveDesign();" > 保存</span> 
                </li>
                <li>
                  		 预留	
                </li>
                <li>
                   	<span onclick="openWindow(this);" >打开属性</span>
                </li>
                <li disabled="disabled">
                  		 预留3
                </li>
            </ul>
	</div >
	<div id="mainDiv" style="width:99%;height:93%;" >
		<div id="panel" class="box-col" >
            <ul data-role="panelbar"
                data-bind="visible: isVisible"
                style="width: 184px;">
                <li class="k-state-active">
                    	表单
                    <ul>
                        <li>
                        	<img src="<%=request.getContextPath()%>/images/design/star.png" />
                        	<span draggable="true" ondragstart="drag(event)" template="autocomplete" id="drag1">
                        		文本输入框
                        	</span>
                        </li>
                        <li>
                        	<img src="<%=request.getContextPath()%>/images/design/star.png" />
                        	<span draggable="true" ondragstart="drag(event)" template="autocomplete" id="drag2">
                        		文本输入框2
                        	</span>
                        </li>
                    </ul>
                </li>
                <li>
                    	表格
                    <ul>
                        <li>
                        	<img src="<%=request.getContextPath()%>/images/design/star.png" />
                        	<span >
                        		测试
                        	</span>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
		<div  >
			<div id="tabstrip" style="width:96%;height:99%;">
					<ul>
						<li class="k-state-active">
							设计
						</li>
						<li>
							<span onclick="preview()">预览</span>
						</li>
					</ul>
					<!-- 第一选卡项-->
					<div  style="height:560px;">
						<div id="htmlDiv" style="width:100%;height:100%;"   onmousedown="show_element(event)"   ondrop="drop(event)" ondragover="allowDrop(event)">
						</div>
					</div>
					<!-- 第二选卡项-->
					<div  style="height:560px;">
						<div   style="height:100%;">
							<div id="xx">
												
							</div>
						</div>
					</div>
			</div>
		</div>
	</div>
	<div id="window" >
              <div id="tabstrip2" style="width:99%;height:99%;">
					<ul>
						<li class="k-state-active">
							属性值
						</li>
						<li>
							数据源
						</li>
					</ul>
					<!-- 第一选卡项-->
					<div id="aDiv"  style="width:88%">
						
					</div>
					<!-- 第二选卡项-->
					<div id="datasourceDiv" style="width:88%">
						
					</div>
			</div>
     </div>
</body>
</html>


