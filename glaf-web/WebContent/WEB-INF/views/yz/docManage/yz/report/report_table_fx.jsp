<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="org.json.*"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
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
	<table id="myTreeGrid" class="easyui-treegrid" style="width:auto;height:'<%=Integer.parseInt(request.getAttribute("panelHeight").toString()) %>px'"
            data-options="
            	url:'<%=request.getContextPath() %>/rs/reportGeneratorRest/getReportTableFX',
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
               <th field="indexName" width="300" formatter="formatName">工程名称</th>
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
                <th field="monthCell1Sum" width="63" align="center" formatter="formatNumber">份</th>
                <th field="monthCell1PageSum" width="63" align="center" formatter="formatNumber">页</th>
                <th field="monthCell2Sum" width="63" align="center" formatter="formatNumber">份</th>
                <th field="monthCell2PageSum" width="63" align="center" formatter="formatNumber">页</th>
                <th field="cell2Pencent" width="63" align="center" formatter="formatNumber">%</th>
                <th field="monthCell3Sum" width="63" align="center" formatter="formatNumber">份</th>
                <th field="monthCell3PageSum" width="63" align="center" formatter="formatNumber">页</th>
                <th field="cell3Pencent" width="63" align="center" formatter="formatNumber">%</th>
                <th field="monthIntpFile1Sum" width="63" align="center" formatter="formatNumber">份</th>
                <th field="monthIntpFile1PageSum" width="63" align="center" formatter="formatNumber">页</th>
                <th field="intpFile1Pencent" width="63" align="center" formatter="formatNumber">%</th>
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
	 
	<%-- <table id="dataTable" border="0" cellspacing="1" cellpadding="1" class="list-box" style="width:100%;">
		<tr class="list-title" align="center">
			<td rowspan="2">项次</td>
			<td rowspan="2" width="150px">单位工程名称</td>
			<td rowspan="2" width="200px">分部工程名称</td>
			<td rowspan="2" width="200px">分项工程名称</td>
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
			<c:set var="isFirstUnit" value="1" />
			<c:forEach items="${unitChildMap[unit.indexId] }" var="fenbu" varStatus="fenbuStatus">
				<c:set var="isFirstFenbu" value="1" />
				<c:forEach items="${fenbuChildMap[fenbu.indexId] }" var="fenxiang" varStatus="fenxiangStatus">
					<c:set var="index" value="${index+1 }" />
					<c:set var="treepinfoSum" value="${resultMap[fenxiang.indexId] }" />
		  			<c:set var="monthCell1Count" value="${monthCell1Count+treepinfoSum.monthCell1Sum }" />
		  			<c:set var="monthCell1PageCount" value="${monthCell1PageCount+treepinfoSum.monthCell1PageSum }" />
		  			<c:set var="monthCell2Count" value="${monthCell2Count+treepinfoSum.monthCell2Sum }" />
		  			<c:set var="monthCell2PageCount" value="${monthCell2PageCount+treepinfoSum.monthCell2PageSum }" />
		  			<c:set var="monthCell3Count" value="${monthCell3Count+treepinfoSum.monthCell3Sum }" />
		  			<c:set var="monthCell3PageCount" value="${monthCell3PageCount+treepinfoSum.monthCell3PageSum }" />
		  			<c:set var="monthIntpFile1Count" value="${monthIntpFile1Count+treepinfoSum.monthIntpFile1Sum }" />
		  			<c:set var="monthIntpFile1PageCount" value="${monthIntpFile1PageCount+treepinfoSum.monthIntpFile1PageSum }" />
		  			
		  			<tr>
		  				<td class="td-no">${index }</td>
		  				<c:if test="${isFirstUnit=='1' }">
		  					<c:set var="isFirstUnit" value="0" />
			  				<td class="" title="${unit.indexName }" rowspan="${sizeMap[unit.indexId] }">${unit.indexName }&nbsp;</td>
			  			</c:if>
			  			<c:if test="${isFirstFenbu=='1' }">
			  				<c:set var="isFirstFenbu" value="0" />
			  				<td class="" title="${fenbu.indexName }" rowspan="${sizeMap[fenbu.indexId] }">${fenbu.indexName }</td>
			  			</c:if>
		  				<td class="" title="${fenxiang.indexName }">${fenxiang.indexName }</td>
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
			</c:forEach>
		</c:forEach>
		<tr class="list-title">
		  	<td class="td-no">&nbsp;</td>
		  	<td class="td-no">&nbsp;</td>
		  	<td class="td-no">&nbsp;</td>
		  	<td align="center" >合&nbsp;&nbsp;计</td>
		  	<td class="td-no">${monthCell1Count }</td>
		  	<td class="td-no">${monthCell1PageCount }</td>
		  	<td class="td-no">${monthCell2Count }</td>
		  	<td class="td-no">${monthCell2PageCount }</td>
		  	<td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${monthCell1Count==0?0:monthCell2Count*100/monthCell1Count }"/></td>
		  	<td class="td-no">${monthCell3Count }</td>
		  	<td class="td-no">${monthCell3PageCount }</td>
		  	<td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${monthCell1Count==0?0:monthCell3Count*100/monthCell1Count }"/></td>
		  	<td class="td-no">${monthIntpFile1Count }</td>
		  	<td class="td-no">${monthIntpFile1PageCount }</td>
		  	<td class="td-no"><fmt:formatNumber type="number" pattern="0.0" value="${monthCell1Count==0?0:monthIntpFile1Count*100/monthCell1Count }"/></td>
		  </tr>
	</table> --%>
</body>
</html>