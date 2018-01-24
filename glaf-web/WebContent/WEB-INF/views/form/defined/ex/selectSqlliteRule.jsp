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
<title>sqllite文件列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript">
	//事件定义器中的回调方法
    var retFn = "${param.retFn}";
    function callback(dataItem){
        var retObj = {
            name:dataItem.sqlliteRuleName,
            value:dataItem.id
        };
        parent[retFn](retObj);
        closeWin();
    }
    function closeWin(){
        parent.layer.close(parent.layer.getFrameIndex());
    }


  jQuery(function() {
      jQuery("#grid").kendoGrid({
        "columnMenu": true,
        "dataSource": {
            "schema" : {
                "total" : "total",
                "model": {
                    "fields": {
                        "id": {
                            "type": "string"
                        },
                        "sqlliteRuleDesc": {
                                                        "type": "string"
                        },
                        "sqlliteRuleName": {
                                                        "type": "string"
                        },
                        "dataSetsName": {
                                                        "type": "string"
                        },
                        "delflag": {
                                                        "type": "string"
                        },
                        "ruleJson": {
                                                        "type": "string"
                        },
                        "createBy": {
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
                "data" : "rows"
            },
            "transport" : {
                "parameterMap" : function(options) {
                    return JSON.stringify(options);
                },
                "read" : {
                    "contentType" : "application/json",
                    "type" : "POST",
                    "url" : "<%=request.getContextPath()%>/rs/form/wdatasetSqllite/data"
                }
            },
            "serverFiltering" : true,
            "serverSorting" : true,
            "pageSize" : 10,
            "serverPaging" : true
        },
        "height": 455,
        "reorderable": true,
        "filterable": true,
        "sortable": true,
		"pageable": {
           "refresh": true,
           "pageSizes": [5, 10, 15, 20, 25, 50, 100, 200, 500],
           "buttonCount": 10
         },
		"selectable": "single",
                "columns": [
                {
				"field": "sqlliteRuleName",
				"title": "名称",
                                "width": "200px",
				"lockable": false,
				"locked": false
                },
                {
                "field": "sqlliteRuleCode",
                "title": "代码",
                                "width": "200px",
                "lockable": false,
                "locked": false
                },
                {
                "field": "sqlliteRuleDesc",
                "title": "描述",
                                "width": "250px",
                "lockable": false,
                "locked": false
                },
                {
				"field": "createBy",
				"title": "创建人",
                                "width": "150px",
				"lockable": false,
				"locked": false
                },
                {
				"field": "createDate",
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
					name : "选择",
					click : function(e) {
                        var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
                        callback(dataItem);
					}
				},
                {
                    name : "规则定义",
                    click : function(e) {
                        var tr = $(e.target).closest("tr");
                        var data = this.dataItem(tr);
                        window.open("/glaf/mx/form/defined/ex/sqlliteRuleEdit?getData=getSqlliteRuleData&id="+data.id);
                        clickData = data;
                    }
                }]
          }
	],
        "scrollable": {},
        "resizable": true,
        "groupable": false
    });
  });

 </script>
</head>
<body>
<div id="main_content" class="k-content">
<br>
 <div class="x_content_title">
  <img src="<%=request.getContextPath()%>/images/window.png" alt="sqllite文件列表">&nbsp;
    sqllite文件列表
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