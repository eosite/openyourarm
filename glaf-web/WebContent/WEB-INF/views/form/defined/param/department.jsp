<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色列表</title>

<link href="${pageContext.request.contextPath}/scripts/kendoui/styles/kendo.common.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/scripts/kendoui/styles/kendo.rtl.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/scripts/kendoui/styles/kendo.default.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/scripts/kendoui/styles/kendo.dataviz.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/scripts/kendoui/styles/kendo.dataviz.default.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/scripts/kendoui/styles/kendo.mobile.all.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/scripts/layer/skin/layer.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/themes/default/styles.css" rel="stylesheet" type="text/css" >
<link href="${pageContext.request.contextPath}/css/icons.css" rel="stylesheet" type="text/css" >
<link href="${pageContext.request.contextPath}/css/kendoui.css" rel="stylesheet" type="text/css" >
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.form.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/json2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/kendoui/js/angular.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/kendoui/js/kendo.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/kendoui/js/jszip.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/kendoui/js/cultures/kendo.culture.zh-CN.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/kendoui/js/messages/kendo.messages.zh-CN.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/layer/layer.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/inc/globaljs.jsp"></script>  
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:callbackfunc();">确定</button>               
   </div>
</script>
<script type="text/javascript">
  var dataItem;
  
  function callbackfunc(){
	  
	 var targetId = "${param.targetId}", fn = "${param.fn}" , //
	 $parent = window.opener || window.parent;
     if(fn && $parent[fn]){
    	 var $target = $($parent.document.getElementById(targetId));
    	 var $treelist = jQuery("#treelist").data("kendoTreeList");
    	 var items = $treelist.select();
    	 if(items && items.length){
	   		var names = [], codes = [];
    		 $.each(items, function(i, v){
    			 var item = $treelist.dataItem(v);
    			 names.push(item.name);
    			 codes.push(item.code);
    		 });
	    	 $parent[fn].call($target, {data : {
	    		 dept_name : names.join(","),
	    		 dept_code : codes.join(",")
	    	 }});
	    	 window.close();
    	 } else {
    		 alert("请选择!");
    	 }
     } else {
		  selectNode();
	 }
  }
  
  jQuery(function() {
    var dataSource = new kendo.data.TreeListDataSource({
                            transport: {
                                read: {
                                    url: "/glaf/rs/sys/department/read",
                                    dataType: "json",
									contentType: "application/json",
                                    type: "POST"
                                }
                            },
                            schema: {
                                model: {
                                    id: "nodeId",
                                    fields: {
                                        nodeId: { type: "number", nullable: false },
                                        parentId: { field: "nodeParentId", type: "number", nullable: true },
										deptId: { type: "number", nullable: false }
                                    },
								    expanded: true
                                }
                            }
                        });

    jQuery("#treelist").kendoTreeList({
        "dataSource": dataSource,
        "height": x_height,
        "filterable": true,
        "sortable": true,
		"selectable":true,
        "toolbar": kendo.template(jQuery("#template").html()),
        "columns": [
        {
            "field": "name",
            "title": "部门名称",
            "width": "180px",
			"expandable": true,
			"lockable": false,
            "locked": false
        },
        {
            "field": "code",
            "title": "代码",
            "width": "150px",
            "locked": false
        },
        {
            "field": "code2",
            "title": "编码",
            "width": "150px",
            "locked": false
        },
        {
            "field": "no",
            "title": "部门区分",
            "width": "150px",
            "locked": false
        },
        {
            "field": "desc",
            "title": "部门描述",
            "width": "220px",
            "locked": false
        }
		]
    });
  });


 </script>
</head>
<body>
<div id="treelist"></div>
</body>
</html>