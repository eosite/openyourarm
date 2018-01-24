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
<title>规则属性列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <input id="categoryDropdownList" style="width:150px" />
      <button type="button" id="newButton"  class="k-button" style="width: 60px" 
      onclick="javascript:copyPropConfirm();"><span class="k-icon k-i-tick"></span>确定</button>
      <button type="button" id="copyPropButton" class="k-button" style="width:60px"
      onclick="javascript:parent.layer.close(parent.layer.getFrameIndex(window.name));"><span class="k-icon k-i-close"></span>关闭</button>
   </div>
</script>
<script type="text/javascript">
jQuery(function() {

    jQuery("#grid").kendoGrid({
        "columnMenu": true,
        "height": "530",
        "reorderable": true,
        "filterable": true,
        "sortable": true,
        "pageable": {
            "refresh": true,
            "pageSize": 20,
            "pageSizes": [20, 50, 100,],
            "buttonCount": 10
        },
        "scrollable": {},
        "resizable": true,
        "groupable": false,
        "selectable": "multiple",
        "toolbar": kendo.template(jQuery("#template").html()),
        "columns": [{
            "field": "ruleName",
            "title": "规则名称",
            "width": "150px",
            "lockable": false,
            "locked": false
        }, {
            "field": "ruleCode",
            "title": "规则代码",
            "width": "120px",
            "lockable": false,
            "locked": false
        }, {
            "field": "ruleDesc",
            "title": "规则描述",
            "width": "150px",
            "lockable": false,
            "locked": false
        }, {
            "field": "defaultVal",
            "title": "默认值",
            "width": "80px",
            "lockable": false,
            "locked": false
        }, {
            "field": "orderNo",
            "title": "排序号",
            "width": "80px",
            "lockable": false,
            "locked": false
        }],
        "dataBound": function(e) {
            $("#categoryDropdownList").kendoDropDownList({
                dataSource: JSON.parse('${categorys}'),
                dataTextField: "name",
                dataValueField: "treeId",
                optionLabel: "请选择系统分类",
                change: function(e) {
                    var grid =  jQuery("#grid").data("kendoGrid");
                    var ds = createGridDataSource(this.value());
                    grid.setDataSource(ds);
                }
            });
        }
    });
});

function createGridDataSource(treeId){
    var ds = new kendo.data.DataSource({
        "schema": {
            "total": "total",
            "model": {
                "fields": {
                    "id": {
                        "type": "string"
                    },
                    "ruleCode": {
                        "type": "string"
                    },
                    "ruleName": {
                        "type": "string"
                    },
                    "ruleDesc": {
                        "type": "string"
                    },
                    "sysCategory": {
                        "type": "string"
                    },
                    "useCategory": {
                        "type": "string"
                    },
                    "openFlag": {
                        "type": "string"
                    },
                    "orderNo": {
                        "type": "number"
                    },
                    "readOnly": {
                        "type": "string"
                    },
                    "repeatFlag": {
                        "type": "string"
                    },
                    "notNull": {
                        "type": "string"
                    },
                    "inputType": {
                        "type": "string"
                    },
                    "defaultVal": {
                        "type": "string"
                    },
                    "extJson": {
                        "type": "string"
                    },
                    "creator": {
                        "type": "string"
                    },
                    "createDateTime_datetime": {
                        "type": "date",
                        "format": "{0: yyyy-MM-dd HH:mm:ss}"
                    },
                    "modifier": {
                        "type": "string"
                    },
                    "modifyDateTime_datetime": {
                        "type": "date",
                        "format": "{0: yyyy-MM-dd HH:mm:ss}"
                    },
                    "delFlag": {
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
                "url": "${pageContext.request.contextPath}/rs/dep/base/depBaseProp/allData?delFlag=0&categoryTreeId="+treeId
            }
        },
        "serverFiltering": true,
        "serverSorting": true,
        "pageSize": 20,
        "serverPaging": true
    });
    return ds;
}


function copyPropConfirm() {
    var grid = $("#grid").data("kendoGrid");
    var rows = grid.select();
    var ruleIds = [];
    $.each(rows, function(index, row) {
        var data = grid.dataItem(row);
        ruleIds.push(data.ruleId);
    });

    $.post('${pageContext.request.contextPath}/rs/dep/base/depBasePropCategory/copyProp', 
        {
            ruleIds: ruleIds.join(','),
            categoryId:${categoryId}
        }, function(data, textStatus, xhr) {
            if(data.statusCode == 200){
                alert("引入属性操作完成！");
                var parentgrid = parent.$("#grid").data("kendoGrid");
                parentgrid.dataSource.read();
            }else{
                alert("引入属性操作失败！");
            }
        },
    'json');

}
</script>

</head>
<body>
    <div id="main_content" class="k-content">
        <br>
        <div class="x_content_title">
            <img src="${pageContext.request.contextPath}/images/window.png" alt="规则属性列表">&nbsp; 规则属性列表
        </div>
        <br>
        <div id="grid"></div>
    </div>
    <br />
</body>

</html>
