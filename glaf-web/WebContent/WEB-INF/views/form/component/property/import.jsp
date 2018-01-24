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
<title>引入其他控件属性</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript">
var grid;
  jQuery(function() {
      grid = jQuery("#grid").kendoGrid({
        "columnMenu": true,
        "dataSource": {
            "schema": {
                "total": "total",
                "model": {
                    "fields": {
                        "id": {
                            "type": "string"
                        },
                        "parentId": {
							"type": "number"
                        },
                        "deploymentId": {
                                                        "type": "string"
                        },
                        "name": {
                                                        "type": "string"
                        },
                        "dataType": {
                                                        "type": "string"
                        },
                        "type": {
                                                        "type": "string"
                        },
                        "kendoComponent": {
                                                        "type": "string"
                        },
                        "title": {
                                                        "type": "string"
                        },
                        "desc": {
                                                        "type": "string"
                        },
                        "value": {
                                                        "type": "string"
                        },
                        "createDate": {
							"type": "date",
							"format": "{0: yyyy-MM-dd}"
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
                    "url": "<%=request.getContextPath()%>/mx/form/component/property/read?kendoComponent=${kendoComponent}",
                    dataType : 'JSON'
                }
            },
	        "serverFiltering": true,
            "serverSorting": true,
            "pageSize": 10,
            "serverPaging": true
        },
        "height": '300px',
        "reorderable": true,
        "filterable": true,
        "sortable": true,
		"pageable": {
                       "refresh": true,
                       "pageSizes": [5, 10, 15, 20, 25, 50, 100, 200, 500],
                       "buttonCount": 10
                     },
		"selectable": "multiple, row",
                "columns": [
				{
				"field": "title",
				"title": "标题",
                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "name",
				"title": "名称",
                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "dataType",
				"title": "数据类型",
                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "type",
				"title": "类型",
                "width": "150px",
				"lockable": false,
				"locked": false
                },              
                {
				"field": "createDate",
				"title": "创建日期",
				"width": "120px",
				"format": "{0: yyyy-MM-dd}",
				"filterable": {
					"ui": "datetimepicker"  
				   },
				"lockable": false,
				"locked": false
                }
		],
        "scrollable": {},
        "resizable": true,
        "groupable": false
    }).data('kendoGrid');
    
	    kendo.bind(jQuery("#comboboxList"), kendo.observable({
			selectedProduct : null,
			selectedItem : null,
			isPrimitive : false,
			isVisible : true,
			isEnabled : true,
			primitiveChanged : function() {
				this.set("selectedProduct", null);
			},
			displaySelectedProduct : function() {
				var selectedProduct = this.get("selectedProduct");
	           	return kendo.stringify(selectedProduct, null, 4);
			},
			onOpen : function() {},
			onChange : function(e) {
				var kendoThis = e.sender,dataItem = kendoThis.dataItem();
				if(dataItem){
					console.log(dataItem);
					var params = {
						kendoComponent : dataItem.kendoComponent,
                    	componentId : dataItem.id
					};
					var url = "<%=request.getContextPath()%>/mx/form/component/property/read?" + $.param(params);
					grid.dataSource.transport.options.read.url = url;
					grid.dataSource.read();
				}					
			},
			onClose : function() {},
			onSelect : function(e){
				this.selectedItem = e.sender.dataItem();
			},
			products : new kendo.data.DataSource({
				transport : {
					read : {
						url : "<%=request.getContextPath()%>/mx/form/component/queryFormComponent",
						dataType : "json"
					}
				}
			})
		}));
  });

	function importProperty(){
		var selected = grid.select();
		if(selected.length){
			var ids = new Array();
			$.each(selected,function(i,v){
				var row = grid.dataItem(v);
				ids.push(row.id);
			});
			
			var params = {
				ids : ids.join(','),
				kendoComponent : '${kendoComponent}',
				componentId : '${componentId}'
			};
			$.ajax({
				url : '<%=request.getContextPath()%>/mx/form/component/property/saveImportProperties',
				data : params,
				type : 'post',
				success:function(){
					alert('操作成功!');
					parent.location.reload();
				}
			});
		}
	}

 </script>
</head>
<body>
<div id="main_content" class="k-content">
<br>
	<div class="toolbar" style="">
	<div id="comboboxList" style="width: 200px;" data-role="combobox" 
		data-placeholder="please chooes one..." data-value-primitive="true"
		data-text-field="name" data-value-field="id"
		data-bind="value: selectedProduct,
				  source: products,
                     visible: isVisible,
                     enabled: isEnabled,
                     events: {
                       change: onChange,
                       open: onOpen,
                       close: onClose,
                       select : onSelect
                     }">
       </div>
	  <button type="button" id="newButton"  class="k-button" style="width: 90px" onclick="importProperty();">确定</button>               
	</div>
	 <div class="x_content_title">
	  <img src="<%=request.getContextPath()%>/images/window.png" alt="引入属性">&nbsp;引入属性
	 </div>
<br>
<div id="grid"></div>
</div>     
</body>
</html>