<%@page import="com.glaf.core.util.RequestUtils"%>
<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="org.json.*"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    //java.util.Map<String, Object> params = RequestUtils.getParameterMap(request);
    //org.json.JSONObject results = (org.json.JSONObject)request.getAttribute("results");
    ////System.out.println(results);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表生成</title>
<%@include file="../inc/script.jsp" %>
<link href="<%=request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
<style type="text/css">
	body{font-size: 12px}
	body table{background-color: #000;}
	body table tr td{background-color: #FFF;}
</style>
</head>
<body style="margin:1px;">
<div style="margin:0;"></div>
	<table id="myTreeGrid" class="easyui-treegrid" style="width:auto;height:<%=Integer.parseInt(request.getAttribute("panelHeight").toString()) %>"
            data-options="
            	url:'<%=request.getContextPath() %>/rs/reportGeneratorRest/getReportTableDW',
            	queryParams:{'useDatabase':'<%=request.getAttribute("useDatabase") %>','startDate':'<%=request.getAttribute("startDate") %>','endDate':'<%=request.getAttribute("endDate") %>'},
                method: 'post',
                lines: true,
                rownumbers: true,
                fitColumns:true,
                idField: 'indexId',
                treeField: 'indexName',
                onLoadSuccess:initTooltip
            ">
      	<thead frozen="true">
           <tr>
               <th field="indexName" width="300" formatter="formatName">单位工程名称</th>
           </tr>
       </thead>
        <thead>
            <tr>
                <th colspan="2">本月启动计划</th>
                <th colspan="3">本月已填写</th>
                <th colspan="3">本月已报验</th>
                <th colspan="3">已形成文件</th>
            </tr>
            <tr>
                <th field="monthCell1Sum" width="60" align="center" formatter="formatNumber">份</th>
                <th field="monthCell1PageSum" width="60" align="center" formatter="formatNumber">页</th>
                <th field="monthCell2Sum" width="60" align="center" formatter="formatNumber">份</th>
                <th field="monthCell2PageSum" width="60" align="center" formatter="formatNumber">页</th>
                <th field="cell2Pencent" width="60" align="center" formatter="formatNumber">%</th>
                <th field="monthCell3Sum" width="60" align="center" formatter="formatNumber">份</th>
                <th field="monthCell3PageSum" width="60" align="center" formatter="formatNumber">页</th>
                <th field="cell3Pencent" width="60" align="center" formatter="formatNumber">%</th>
                <th field="monthIntpFile1Sum" width="60" align="center" formatter="formatNumber">份</th>
                <th field="monthIntpFile1PageSum" width="60" align="center" formatter="formatNumber">页</th>
                <th field="intpFile1Pencent" width="60" align="center" formatter="formatNumber">%</th>
            </tr>
        </thead>
    </table>
	<script type="text/javascript">
	function initTooltip(row){
		$(".easyui-tooltip").tooltip({  
             onShow: function(){  
                 $(this).tooltip('tip').css({   
                     boxShadow: '1px 1px 3px #292929'                          
                 });  
             }  
        });  
	}
	function formatName(value){
		if(value){
			return "<span class='easyui-tooltip' title='"+value+"'>"+value+"</span>";
		}else{
			return "";
		}
	}
	function formatNumber(value){
		if(value){
			return value;
		}else{
			return "";
		}
	}
	</script>
	<%-- <table border="0" cellspacing="1" cellpadding="1" class="list-box" style="width:100%;">
		<tr class="list-title" align="center">
			<td rowspan="2">项次</td>
			<td rowspan="2" width="200px">单位工程名称</td>
			<td colspan="2">本月启动计划</td>
			<td colspan="3">本月已填写</td>
			<td colspan="3">本月已报验</td>
			<td colspan="3">已形成文件</td>
		</tr>
		<tr class="list-title" align="center">
			<td>份</td>
			<td>页</td>
			<td>份</td>
			<td>页</td>
			<td>%</td>
			<td>份</td>
			<td>页</td>
			<td>%</td>
			<td>份</td>
			<td>页</td>
			<td>%</td>
		</tr>
		
		<c:set var="index" value="0"/>
		<c:forEach items="${unitList }" var="unit" varStatus="unitStatus">
			<c:set var="index" value="${index+1 }" />
			<c:set var="treepinfoSum" value="${resultMap[unit.indexId] }" />
  			<tr>
  				<td class="td-no">${index }</td>
  				<td class="td-text" title="${unit.indexName }">${unit.indexName }</td>
			    <td class="td-no">${treepinfoSum.monthCell1Sum }</td>
			    <td class="td-no">${treepinfoSum.monthCell1PageSum }</td>
			    <td class="td-no">${treepinfoSum.monthCell2Sum }</td>
			    <td class="td-no">${treepinfoSum.monthCell2PageSum }</td>
			    <c:set var="percent" value="${treepinfoSum.monthCell1Sum==0?0:treepinfoSum.monthCell2Sum*100/treepinfoSum.monthCell1Sum }" />
			    <td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${percent>100?100:percent }"/></td>
			    <td class="td-no">${treepinfoSum.monthCell3Sum }</td>
			    <td class="td-no">${treepinfoSum.monthCell3PageSum }</td>
			    <c:set var="percent" value="${treepinfoSum.monthCell1Sum==0?0:treepinfoSum.monthCell3Sum*100/treepinfoSum.monthCell1Sum }" />
			    <td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${percent>100?100:percent }"/></td>
			    <td class="td-no">${treepinfoSum.monthIntpFile1Sum }</td>
			    <td class="td-no">${treepinfoSum.monthIntpFile1PageSum }</td>
			    <c:set var="percent" value="${treepinfoSum.monthCell1Sum==0?0:treepinfoSum.monthIntpFile1Sum*100/treepinfoSum.monthCell1Sum }" />
			    <td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${percent>100?100:percent }"/></td>
  			</tr>
		</c:forEach>
	</table> --%>
</body>
</html>