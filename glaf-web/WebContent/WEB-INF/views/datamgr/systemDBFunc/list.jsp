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
<title>数据库公共函数表列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:addRow();">新增</button>      
	<button type="button" class="k-button" style="width: 90px" 
	  onclick="javascript:reload_();">重载</button>          
   </div>
</script>
<script type="text/javascript">

function reload_(){
	$.ajax({
		dataType : 'json',
		url : '${contextPath}/mx/datamgr/systemDBFunc/reload',
		success : function(ret){
			alert(ret.message);
		}
	});
}
	
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
                        "code": {
                                                        "type": "string"
                        },
                        "name": {
                                                        "type": "string"
                        },
                        "publicParams": {
                                                        "type": "string"
                        },
                        "desc": {
                                                        "type": "string"
                        },
                        "createBy": {
                                                        "type": "string"
                        },
                        "createTime": {
							"type": "date",
							"format": "{0: yyyy-MM-dd}"
                        },
                        "updateBy": {
                                                        "type": "string"
                        },
                        "updateTime": {
							"type": "date",
							"format": "{0: yyyy-MM-dd}"
                        },
                        "deleteFlag": {
							"type": "number"
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
                    "url": "${contextPath}/rs/datamgr/systemDBFunc/data"
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
				"field": "code",
				"title": "代码",
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
				"field": "publicParams",
				"title": "公共参数说明",
                                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "desc",
				"title": "描述",
                                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "createBy",
				"title": "CREATEBY_",
                                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "createTime",
				"title": "CREATETIME_",
				"width": "120px",
				"format": "{0: yyyy-MM-dd}",
				"filterable": {
					"ui": "datetimepicker"  
				    },
				"lockable": false,
				"locked": false
                }/* ,
                {
				"field": "updateBy",
				"title": "UPDATEBY_",
                                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "updateTime",
				"title": "UPDATETIME_",
				"width": "120px",
				"format": "{0: yyyy-MM-dd}",
				"filterable": {
					"ui": "datetimepicker"  
				    },
				"lockable": false,
				"locked": false
                },
                {
				"field": "deleteFlag",
				"title": "DELETE_FLAG_",
				"width": "90px",
				"lockable": false,
				"locked": false
                } */,
		    {
			"command": [{
                "text": "修改",
                "name": "edit",
                "click": function showDetails(e) {
					var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
		                        //alert(JSON.stringify(dataItem));
				        //alert(dataItem.id);
					var link = "${contextPath}/mx/datamgr/systemDBFunc/edit?id="+dataItem.id;
					 editRow(link);
				    }
                }, {
                    "text": "映射",
                    "name": "mapping",
                    "click": function showDetails(e) {
			    			var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
			    			var link = "${contextPath}/mx/datamgr/systemDBFuncMapping?funcId="+dataItem.id;
			    			window.open(link);
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
         editRow('${contextPath}/mx/datamgr/systemDBFunc/edit');
    }

    function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑数据库公共函数表信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['620px', (jQuery(window).height() - 350) +'px'],
                        iframe: {src: link}
		});
	}

 </script>
</head>
<body>
<div id="main_content" class="k-content">
<br>
 <div class="x_content_title">
  <img src="${contextPath}/images/window.png" alt="数据库公共函数表列表">&nbsp;
    数据库公共函数表列表
 </div>
<br>
<div id="grid"></div>
</div>     
<br/>
<br/>
<br/>
<br/>
</body>
</html>