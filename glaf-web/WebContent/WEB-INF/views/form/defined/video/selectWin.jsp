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
<title>监控配置列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/webfile/js/jquery.kendo.extends.js" ></script>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
		<label>名称：</label><input type="text" class='k-textbox' id="name" />
		<button type="button" id="queryButton" class="k-button" onclick="javascript:query();">查询</button>       
   </div>
</script>
<script type="text/javascript">
	
  jQuery(function() {
     var $grid = jQuery("#grid").kendoGrid({
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
    					url : "<%=request.getContextPath()%>/mx/form/video/json",
    					dataType : "json",
    					type : "POST",
    					data : {
    						status : 0
    					}
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
    				}],
    		scrollable : {},
    		resizable : true,
    		groupable : false

      }).data("kendoGrid");
      
      
      $("#button-01").kendoButton({
    	 click : function(e){
    		 var rows = $grid.select();
    		 var ret = null;
    		 if(rows.length == 0){
    			 ret = null;
    		 } else {
    			 ret = {
   					 id :'',
   					 name : ''
    			 };
    			 var row = $grid.dataItem(rows[0]);
    			 $.each(ret,function(k,v){
    				 ret[k] = row[k];
    			 });
    		 }
    		 returnFunc(ret);
    	 } 
      });
      
  });
  
  function returnFunc(ret){
	  if(!ret){
		  if(!confirm("你没有选择监控，确定讲清空数据!")){
			  return false;
		  }
	  }
	  var fn = "${param.fn}",targetId = "${param.targetId}";
	  if(fn || targetId){
		  console.log(ret);
		  var $parent = window.opener || window.parent;
		  var target = $parent.document.getElementById(targetId);
		  if(target){
			  target.value = ret.id; 
		  } 
		  if($parent[fn]){
			  $parent[fn](ret);
		  }
	  } else {
		  alert("页面参数不对!");
	  }
  }
  
  function query(){
	  jQuery("#grid").mtkendo('reload',{
		  nameLike : $("#name").val()
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
	<div style="float:right;">
		<button class='k-button' id='button-01'>确定</button>
	</div>
	
	<br />
	<br />
	<br />
</body>
</html>