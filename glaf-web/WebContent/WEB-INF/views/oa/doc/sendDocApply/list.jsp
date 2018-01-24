<%@page import="com.glaf.base.modules.BaseDataManager"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
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
<title>SendDocApply列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<style type="text/css">
.datagrid {
	height: 440px;
}
</style>
<script type="text/javascript">
	var tabstrip,columns,contextPath = "<%=request.getContextPath()%>";
	
	$(function(){
		
		x_height = ($('.datagrid').css('height').replace('px','') * 1 - 5);
		
		tabstrip = $("#tabstrip").kendoTabStrip({
			animation:  {
				open: {
					effects: "fadeIn"
				}
			},
			select : function(e){
				
				var index = ($(e.item).attr('aria-controls').substring(9) * 1 - 1);
				
				init_grid({id : 'grid' + index,status : index > 2 ? null : index});

			}
		}).data('kendoTabStrip').select(0);
		
	});
	
	function getSysDataTable(){
		if(!columns){
			var baseData = new Object();
			function $getData(dataItemId){
				if(!baseData[dataItemId]){
					$.ajax({
						url : contextPath + '/mx/oa/doc/sendDocApply/getDictorysBySysDataItemId',
						type:'post',
						async : false,
						dataType:'json',
						data:{dataItemId : dataItemId},
						success:function(data){
							if(data){
								$.each(data,function(index,item){
									item.text = item.name;
								});
							}
							baseData[dataItemId] = data;
						}
					});
				}
				return baseData[dataItemId];
			}
			var obj;
			columns = new Array();
			<c:forEach items="${sysDataTable.fields}" var="field">
			    <c:if test="${field.displayType == 2 || field.displayType == 4}">
					obj = {
			            "field": "${field.name}",
			            "title": "${field.title}",
			            "width": "${field.listWeigth}"
		             };
					<c:if test="${field.dataType == 'Date'}">
						obj.format = "{0: yyyy-MM-dd}";
					</c:if> 
					<c:if test="${not empty field.dataItemId}">
						obj.values = $getData('${field.dataItemId}');
					</c:if> 
						columns.push(obj);
				</c:if>
	        </c:forEach>
			///增加自己的业务
			 columns.push({
				field : 'status',
				title : '操作',
				width : '180px',
				template : formatterStatusField
			});
		}
		return columns;
	}
	
	function init_grid(opt){
		
		var columns = getSysDataTable();
		
		var dataSource = getDataSource(opt.status);
		
		var kendoGrid = $('#' + opt.id).kendoGrid({
			columnMenu : true,
			dataSource : dataSource,
			height : x_height,
			reorderable : true,
			sortable : true,
			pageable : {
				refresh : true,
			    pageSizes : [5, 10, 15, 20, 25, 50, 100, 200, 500],
			    buttonCount : 10
			},				
			selectable : 'single',
			//toolbar : kendo.template(jQuery("#template").html()),
			columns : columns,
			filterable: true,
			"scrollable": {},
			"resizable": true,
			"groupable": false	

		}).data('kendoGrid');
		
		return kendoGrid;
	}
	
	function getDataSource(status){
	
		var dataSource =  new kendo.data.DataSource({
			schema : {
				total : 'total',
				model : {
					fields : {
						id : {
							type : 'string'
						},
						startIndex : {
							type : 'number'
						}
					}
				},
				data : 'rows'
			},
			transport : {
				//parameterMap : function(options){
				//	return JSON.stringify(options);
				//},
				read : {
					dataType: "json",
					type : 'post',
					url : function(o){
						return contextPath + '/mx/oa/doc/sendDocApply/json';
					} ,
					data : function(o){
						return {
							sortName : 'status,urgencyLevel desc',
							status : status,
							rows : o.pageSize
						};
					}
				}
			},
			serverFiltering : true,
			serverSorting : true,
			pageSize : 10,
			serverPaging : true
		});
		
		return dataSource;
	}
	
	function formatterStatusField(dataItem){
		
		var button = "<button type=\"button\" class=\"k-button\" style=\"width:70px\" onclick=\"{0}\">{1}</button>";
		
		var retStr = '';
		
		if(dataItem.status == 0 || dataItem.status == 1){
			
			if(dataItem.status == 0){
				retStr += kendo.format(button,kendo.format("deleteFunc({0});",dataItem.id),'删除');
			}
			retStr += kendo.format(button,kendo.format("dealFunc({0},'',{1});",dataItem.id,dataItem.status),'处理');
			
		}
		return retStr;
	}
	
	var editUrl = contextPath + "/mx/oa/doc/sendDocApply/edit";
	
	function addRow(){
         dealFunc();
    }

    function editRow(link){
    	window.open(link);
    	//window.location.href = link;
	}
	
	//删除
	function deleteFunc(id){
		if(confirm('您确定删除此草稿吗?')){
			$.post(contextPath + '/mx/oa/doc/sendDocApply/delete',{id:id},function(data){
				if(data){
					init_grid({id : 'grid0',status : 0});
					alert('删除成功!');
				}
			});
		}
	}
	
	function dealFunc(id,dealType,status){
		var url = contextPath + '/mx/flow/property/flowPropertySetPage?';
		url += $.param({
			docId : id,
			status : status,
			dealType : dealType,
			processDefId : '20120813/sys-0000028',
	        activityDefId : '20120813/sys-0000076'
		});
		
		editRow(url);
	}
</script>

</head>
<body>
	<div id="template">
		<div class="toolbar" style="padding: 5px;">
			<button type="button" id="newButton" class="k-button"
				style="width: 80px" onclick="javascript:dealFunc('','add')">新增</button>
			<button type="button" id="dealButton" class="k-button"
				style="width: 80px" onclick="javascript:dealFunc('','dealAll')">处理全部</button>
		</div>
	</div>

	<div id="tabstrip">
		<ul>
			<li>草稿</li>
			<li>待办</li>
			<li>已办</li>
			<li>全部</li>
		</ul>
		<div class="datagrid">
			<div id="grid0"></div>
		</div>
		<div class="datagrid">
			<div id="grid1"></div>
		</div>
		<div class="datagrid">
			<div id="grid2"></div>
		</div>
		<div class="datagrid">
			<div id="grid3"></div>
		</div>
	</div>
</body>
</html>