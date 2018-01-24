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
	  onclick="javascript:selectNode();">确定</button>               
   </div>
</script>
<script type="text/javascript">
  var dataItem;
  function selectNode(){
		var g = $("#grid").data("kendoGrid"),
			selects = g.select(),
			item,retAry=[],retNames="";
		$.each(selects,function(i,v){
			item = g.dataItem(v);
			retAry.push(item.code);
			retNames +=  item.name + ",";
		});
		parent.mtxx.select.val(retNames);
		parent.mtxx.select.data("idata",retAry);
		parent.layer.close(parent.layer.getFrameIndex());
  }
  jQuery(function() {
    jQuery("#grid").kendoGrid({
        "columnMenu": true,
        "dataSource": {
            "schema": {
                "total": "total",
                "model": {
                    "fields": {
                        "roleId": {
                            "type": "string"
                        },
                        "name": {
                            "type": "string"
                        },
                        "code": {
                            "type": "string"
                        },
                        "content": {
                            "type": "string"
                        },
                        "createTime": {
                            "type": "date",
							"format": "{0: yyyy-MM-dd}"
                        },
                        "lastLoginTime": {
                            "type": "date",
							"format": "{0: yyyy-MM-dd}"
                        }
                    }
                },
                "data": "rows"
            },
            "transport": {
                "parameterMap": function(options) {
					//alert(JSON.stringify(options));
                    return JSON.stringify(options);
                },
                "read": {
                    //"dataType": "json",
				    "contentType": "application/json",
                    "type": "POST",
                    "url": "${pageContext.request.contextPath}/rs/sys/role/data"
                }
            },
			"serverFiltering": true,
            "serverSorting": true,
            "pageSize": 10,
            "serverPaging": true,
            "serverGrouping": false,
        },
        "height": x_height,
        "reorderable": true,
        "filterable": true,
        "sortable": true,
		"pageable": {
                       "refresh": true,
                       "pageSizes": [5, 10, 15, 20, 25, 50, 100, 200],
                       "buttonCount": 10
                     },
		"selectable": "multiple,row",
        "toolbar": kendo.template(jQuery("#template").html()),
        "columns": [
        {
            "field": "name",
            "title": "角色名称",
            "width": "150px",
			"lockable": false,
            "locked": false
        },
        {
            "field": "code",
            "title": "编码",
            "width": "120px",
            "locked": false
        },
        {
            "field": "content",
            "title": "描述",
            "width": "220px",
			"sortable": false,
			"filterable": {
                    "cell": {
						"showOperators": false,
						 "suggestionOperator": "contains",
						"operator": "contains"
                    }
                }
        },
        {
            "field": "createTime",
            "title": "创建日期",
            "width": "120px",
            "lockable": false,
			"align": "center",
			"format": "{0: yyyy-MM-dd}",
			"filterable": {
              "ui": "datetimepicker"  
           }
        }],
        "scrollable": {},
        "resizable": true,
        "groupable": false
    });
  });

 </script>
</head>
<body>
<div id="grid"></div>
</body>
</html>