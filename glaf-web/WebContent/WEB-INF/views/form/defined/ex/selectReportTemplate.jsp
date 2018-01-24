<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.base.modules.BaseDataManager"%>
<%@ page import="com.glaf.base.modules.sys.model.BaseDataInfo"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);

    BaseDataManager bdm = BaseDataManager.getInstance();
	BaseDataInfo baseDataInfo= bdm.getBaseData("getReportTemplateParams", "spreadReportServer");
	request.setAttribute("getParametersUrl",baseDataInfo.getExt1());

	baseDataInfo= bdm.getBaseData("generateReport", "spreadReportServer");
	request.setAttribute("generateReportUrl",baseDataInfo.getExt1());

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>页面选择</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/layer/skin/layer.ext.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/treegrid/css/jquery.treegrid.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/layer/extend/layer.ext.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/treegrid/js/jquery.treegrid.js"></script>
<script type="text/javascript">

	$(function(){
		var databaseSource = new kendo.data.DataSource({
			type : "json",
			transport : {
				read : {
					contentType : "application/json",
					url : "${pageContext.request.contextPath}/rs/isdp/global/databaseJson",
					type : "POST",
					dataType : "JSON"
				},
				parameterMap : function(options, operation) {
					if (operation == "read") {
						return JSON.stringify(options);
					}
				}
			}
		});
		databaseSource.fetch().then(function(){
			$("#templateListGrid").data("kendoGrid").dataSource.read();
		});


		$("#databaseSelect").kendoDropDownList({
			dataTextField: "text",
  			dataValueField: "code",
  			value:'default',
			dataSource:databaseSource,
			change:function(e){
				$("#templateListGrid").data("kendoGrid").dataSource.read({
					systemName:this.value
				});
			}
		});
		
		$("#templateListGrid").kendoGrid({
			"autoBind":false,
			"selectable":true,
			"dataSource": {
	            "schema": {
	                "total": "total",
	                "model": {
	                    "fields": {
	                        "id": {
	                            "type": "string"
	                        },
	                        "startIndex": {
	                            "type": "number"
	                        }
	                    }
	                },
	                "data": "rows"
	            },
	            "transport": {
	                "parameterMap": function(options) {
	                	options.systemName=$("#databaseSelect").data("kendoDropDownList").value();
	                    return JSON.stringify(options);
	                },
	                "read": {
			    		"contentType": "application/json",
	                    "type": "POST",
	                    "url": "<%=request.getContextPath()%>/rs/report/sysReportTemplate/data"
	                }
	            },
		    	"serverFiltering": true,
	            "serverSorting": true,
	            "pageSize": 10,
	            "serverPaging": true
	        },
			columns:[
				{field:"reportTemplateName",title:"报表模板名称"},
				{field:"ctime",title:"创建时间"},
				{field:"utime",title:"更新时间"}
			]
		});

		$("#confirmBtn").kendoButton({
			imageUrl : "${pageContext.request.contextPath}/images/okay.png",
			click : function(e) {
				var $parent = window.opener || window.parent;
				var grid = $("#templateListGrid").data("kendoGrid");
				var dataItem = grid.dataItem(grid.select());
				if(!dataItem){
					if($parent && $parent.$("#${param.id}")){
						$parent["${param.fn}"].call($parent.$("#${param.id}"),{});
					}
					$parent.layer.close($parent.layer.getFrameIndex(window.name));
					return;	
				}
				var databaseCode = $("#databaseSelect").data("kendoDropDownList").dataItem()["code"];
				$.ajax({
					url: '${pageContext.request.contextPath}/mx/form/defined/getDatabaseInfo?databaseCode='+databaseCode,
					type: 'post',
					dataType : 'JSON' ,
					success:function(data){
						//获取加密数据库信息
						$.ajax({
							url: '${getParametersUrl}',
							type: 'post',
							dataType: 'jsonp',
							data: {
								data : JSON.stringify({
									templateId: dataItem.id,
									key : data.key,
									content : data.content					
								})
							},
							success:function(data){
								var opts = {};
								opts.id = dataItem.id;
								opts.name = dataItem.reportTemplateName;
								opts.params = data.ResultData;
								opts.generateReportUrl = "${generateReportUrl}";
								opts.dbCode =  databaseCode ;
								
								if($parent && $parent.$("#${param.id}")){
									$parent["${param.fn}"].call($parent.$("#${param.id}"),opts);
									$parent.layer.close($parent.layer.getFrameIndex(window.name));
								}
							}
						});
					}
				});
				
			}
		});
	});
</script>
</head>
<body>
	<div id="head" class="k-header k-footer footerCss">
		<table style="width: 100%;">
			<tr>
				<td style="width: 500px; text-align: left; vertical-align: middle;"><span
					style="font-size: 16px; font-weight: bolder;">选择数据源：</span>
					<input id="databaseSelect" />
				</td>
				<td style="text-align: right;">
					<button class='k-button' id="confirmBtn" type="button">确定</button>
				</td>
			</tr>
		</table>
	</div>
	<div style="" align="center" >
		<div id="templateListGrid"></div>
	</div>
</body>
</html>