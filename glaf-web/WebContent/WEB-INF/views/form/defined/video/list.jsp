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
<title>FORMVIDEO列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript" src="${contextPath}/webfile/js/jquery.kendo.extends.js" ></script>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:addRow();">新增</button>        

		<label>名称：</label><input type="text" class='k-textbox' id="name" />
		<button type="button" id="queryButton" class="k-button" onclick="javascript:query();">查询</button>       
   </div>
</script>
<script type="text/javascript">
	
  jQuery(function() {
      jQuery("#grid").kendoGrid({
    		columnMenu : true,
    		dataSource : {
    			schema : {
    				total : "total",
    				model : {
    					fields : {
    						id : {
    							type : "long"
    						},
    						name : {
    							type : "string"
    						},
    						ip : {
    							type : "string"
    						},
    						port : {
    							type : "string"
    						},
    						status : {
    							type : "number"
    						},
    						valided : {
    							type : "number"
    						},
    						userName : {
    							type : "string"
    						},
    						pwd : {
    							type : "string"
    						},
    						startIndex : {
    							type : "number"
    						}
    					}
    				},
    				data : "rows"
    			},
    			transport : {
    				parameterMap : function(options) {
    					return options;
    				},
    				read : {
    					url : "${contextPath}/mx/form/video/json",
    					dataType : "json",
    					type : "POST",
    					data : {}
    				},
    				destroy : {
    					url : "${contextPath}/mx/form/video/delete",
    					type : "POST"
    				}
    			},
    			serverFiltering : true,
    			serverSorting : true,
    			pageSize : 10,
    			serverPaging : true
    	  
    		},
    		height : x_height,
    		reorderable : true,
    		filterable : true,
    		sortable : true,
    		pageable : {
    			refresh: true,
    			pageSizes : [ 5, 10, 15, 20, 25, 50, 100, 200, 500 ],
    			buttonCount : 10
    		},
    		selectable: "single",
    		toolbar : kendo.template(jQuery("#template").html()),
    		columns : [
    				{
    					field : 'name',
    					title : '名称',
    					width : '180px'
    				},
    				{
    					field : "ip",
    					title : "IP",
    					width : "150px",
    					lockable : false,
    					locked : false
    				},
    				{
    					field : "port",
    					title : "端口号",
    					width : "150px",
    					lockable : false,
    					locked : false
    				},
    				{
    					field : "userName",
    					title : "用户名",
    					width : "150px",
    					lockable : false,
    					locked : false
    				},
    				{
    					field : "status",
    					title : "状态",
    					width : "90px",
    					template : function(e){
    						return e.status == 0 ? '有效' : '无效';
    					}
    				},
    				{
    					field : "valided",
    					title : "验证",
    					width : "90px",
    					lockable : false,
    					locked : false
    				},
    				{
    					command : [
    							{
    								text : "修改",
    								name : "edit",
    								click : function showDetails(e) {
    									var dataItem = this.dataItem(jQuery(
    											e.currentTarget).closest("tr"));
    									var link = "${contextPath}/mx/form/video/edit?id="
    											+ dataItem.id;
    									editRow(link);
    								}
    							},
    							{
    								text : '查看云台控制',
    								name : 'see',
    								click : function(e) {
    									var dataItem = this.dataItem(jQuery(
    											e.currentTarget).closest("tr"));
    									var link = "${contextPath}/mx/form/defined/ex/video?id="
    											+ dataItem.id;
    									window.open(link);
    								}
    							},
    							{
    								text : '删除',
    								name : 'del',
    								click : function(e) {
    									if(confirm("确定删除吗?")){
	    									var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
	    									this.dataSource.remove(dataItem);
	    									this.dataSource.sync();
    									}
    								}
    							}
    					]
    				} ],
    		scrollable : {},
    		resizable : true,
    		groupable : false

      });
  });
  
  function query(){
	  jQuery("#grid").mtkendo('reload',{
		  nameLike : $("#name").val()
	  });
  }


    function addRow(){
         editRow("${contextPath}/mx/form/video/edit");
	}

	function editRow(link) {
		jQuery.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "编辑监控信息",
			closeBtn : [ 0, true ],
			shade : [ 0.8, '#000' ],
			border : [ 10, 0.3, '#000' ],
			fadeIn : 100,
			area : [ '620px', '345px' ],
			iframe : {
				src : link
			}
		});
	}
</script>
</head>
<body>
	<div id="main_content" class="k-content">
		<br>
		<div class="x_content_title">
			<img src="<%=request.getContextPath()%>/images/window.png"
				alt="监控配置列表">&nbsp; 监控配置列表
		</div>
		<br>
		<div id="grid"></div>
	</div>
	<br />
	<br />
	<br />
	<br />
</body>
</html>