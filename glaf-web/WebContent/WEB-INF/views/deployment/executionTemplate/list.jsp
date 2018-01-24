<%@ page contentType="text/html;charset=UTF-8" %>
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
<title>执行模板列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.base64.js"></script>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	          onclick="javascript:addRow();">新增</button>  
	  <button type="button" id="chooseButton"  class="k-button" style="width: 90px" 
	          onclick="javascript:chooseServers();">创建执行任务</button>  
   </div>
</script>
<script type="text/javascript">

   var contextPath="<%=request.getContextPath()%>";

   var setting = {
			async: {
				enable: true,
				url:"<%=request.getContextPath()%>/rs/datamgr/project/treeJson?category=deployment",
				dataFilter: filter
			},
			callback: {
				onClick: zTreeOnClick
			}
		};
  
  	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			childNodes[i].icon="<%=request.getContextPath()%>/images/basic.gif";
		}
		return childNodes;
	}


    function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		jQuery("#nodeId").val(treeNode.id);
		//var link = "<%=request.getContextPath()%>/mx/my/project/json?category=${category}&parentId="+treeNode.id;
		//var grid = jQuery("#grid").data("kendoGrid");
		searchData();
 	}

    jQuery(document).ready(function(){
		jQuery.fn.zTree.init(jQuery("#myTree"), setting);
	});

	
    jQuery(function() {
      jQuery("#grid").kendoGrid({
        "columnMenu": true,
        "dataSource": {
            "schema": {
                "total": "total",
                "model": {
                    "fields": {
                        "id": {
                            "type": "string"
                        },
						 "nodeId": {
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
                    return JSON.stringify(options);
                },
                "read": {
		            "contentType": "application/json",
                    "type": "POST",
                    "url": "<%=request.getContextPath()%>/rs/deployment/executionTemplate/data"
                }
            },
	        "serverFiltering": true,
            "serverSorting": true,
            "pageSize": 10,
            "serverPaging": true
        },
        "height": x_height,
        "reorderable": true,
        "filterable": true,
        "sortable": true,
		"pageable": {
                       "refresh": true,
                       "pageSizes": [5, 10, 15, 20, 25, 50, 100, 200, 500],
                       "buttonCount": 10
                     },
		"selectable": "single",
		"toolbar": kendo.template(jQuery("#template").html()),
        "columns": [
                {
				"field": "title",
				"title": "标题",
                "width": "350px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "retryCount",
				"title": "重试次数",
				"width": "90px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "sortNo",
				"title": "顺序",
				"width": "90px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "active",
				"title": "是否有效",
                "width": "90px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "createBy",
				"title": "创建人",
                "width": "100px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "createTime",
				"title": "创建时间",
				"width": "120px",
				"format": "{0: yyyy-MM-dd}",
				"filterable": {
					"ui": "datetimepicker"  
				    },
				"lockable": false,
				"locked": false
                },
				{
				"command": [{
					"text": "修改",
					"name": "edit",
					"click": function showDetails(e) {
								 var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								 var link = "<%=request.getContextPath()%>/mx/deployment/executionTemplate/edit?id="+dataItem.id;
								 editRow(link);
								}
					},{
					"text": "删除",
					"name": "delete",
					"click": function deleteDetails(e) {
						          if(confirm("数据删除后不能恢复，确定删除吗？")){
								    var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								    var link = "<%=request.getContextPath()%>/mx/deployment/executionTemplate/delete?id="+dataItem.id;
									jQuery.ajax({
										   type: "POST",
										   url: link,
										   dataType: 'json',
										   error: function(data){
											   alert('服务器处理错误！');
										   },
										   success: function(data){
											   if(data != null && data.message != null){
												   alert(data.message);
											   } else {
												   alert('操作成功完成！');
											   }
											   if(data.statusCode == 200){
												   window.location.reload();
											   }
										   }
									 });
								  }
								}
					}]
			  }
	    ],
        "scrollable": {},
        "resizable": true,
        "groupable": false
    });
  });


    function addRow(){
		if(jQuery("#nodeId").val()!=""){
           editRow('<%=request.getContextPath()%>/mx/deployment/executionTemplate/edit?nodeId='+jQuery("#nodeId").val());
		} else {
           alert("请选择左边分类。");
		}
    }

    function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑执行模板信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['820px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function chooseServers(){
	  if(jQuery("#nodeId").val()!=""){
		  var link="<%=request.getContextPath()%>/mx/deployment/executionTemplate/chooseServers?nodeId="+jQuery("#nodeId").val();
		  jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "选择执行",
				closeBtn: [0, true],
				shade: [0.8, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['20px',''],
				fadeIn: 100,
				area: ['980px', (jQuery(window).height() - 50) +'px'],
				iframe: {src: link}
			});
		} else {
           alert("请选择左边分类。");
		}
	}

    function searchData(){
 		var link="<%=request.getContextPath()%>/rs/deployment/executionTemplate/data?category=deployment";
		
		$("#grid").data("kendoGrid").dataSource.read({
			nodeId : jQuery("#nodeId").val()
		});
	}

 </script>
</head>
<body>
<input type="hidden" id="nodeId" name="nodeId" value="${nodeId}" > 
<div class="easyui-layout" data-options="fit:true">  
    <div data-options="region:'west',split:true" style="width:180px;">
	  <div class="easyui-layout" data-options="fit:true">  
           
			 <div data-options="region:'center',border:false">
			    <ul id="myTree" class="ztree"></ul>  
			 </div> 
			 
        </div>  
	</div> 
   <div data-options="region:'center'"> 
	<div class="easyui-layout" data-options="fit:true">  
	  <div data-options="region:'center',border:false">
		 <div id="grid"></div>
	  </div>  
	</div>
  </div>
</div>
</body>
</html>