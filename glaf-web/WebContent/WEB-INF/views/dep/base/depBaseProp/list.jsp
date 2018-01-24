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
      <button type="button" id="newButton"  class="k-button" style="width: 60px" 
	  onclick="javascript:addRow();"><span class="k-icon k-i-plus"></span>新增</button>
      <button type="button" id="copyPropButton" class="k-button" style="width:80px"
      onclick="javascript:copyProp();"><span class=""></span>引入属性</button>
   </div>
</script>
<script type="text/javascript">

  jQuery(function() {
      var dicts = ${dicts};

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
                    "url": "${pageContext.request.contextPath}/rs/dep/base/depBaseProp/data?delFlag=0&categoryId=${categoryId}"
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
           "pageSize":50,
           "pageSizes": [5, 10, 15, 20, 25, 50, 100, 200, 500],
           "buttonCount": 10
         },
		"selectable": "single",
		"toolbar": kendo.template(jQuery("#template").html()),
            "columns": [
                {
                    "field": "ruleName",
                    "title": "规则名称",
                    "width": "200px",
                    "lockable": false,
                    "locked": false
                },
                {
    				"field": "ruleCode",
    				"title": "规则代码",
                    "width": "120px",
    				"lockable": false,
    				"locked": false
                },
                {
    				"field": "ruleDesc",
    				"title": "规则描述",
                    "width": "200px",
    				"lockable": false,
    				"locked": false
                },
                {
    				"field": "notNull",
    				"title": "是否非空",
                    "width": "100px",
    				"lockable": false,
    				"locked": false,
                    "template":function(dataItem){
                        return dataItem.notNull=="1"?"是":"否";
                    }
                },
                {
    				"field": "inputType",
    				"title": "录入方式",
                    "width": "100px",
    				"lockable": false,
    				"locked": false,
                    "template":function(dataItem){
                        return dicts[dataItem.inputType];
                    }
                },
                {
    				"field": "defaultVal",
    				"title": "默认值",
                    "width": "80px",
    				"lockable": false,
    				"locked": false
                },
                {
                    "field": "orderNo",
                    "title": "排序号",
                    "width": "80px",
                    "lockable": false,
                    "locked": false
                },
                {
                    "width":"250px",
        			"command": [{
                        "text": "修改",
                        "name": "edit",
                        "click": function showDetails(e) {
                			var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
                			var link = "${pageContext.request.contextPath}/mx/dep/base/depBaseProp/edit?categoryId=${categoryId}&ruleId="+dataItem.ruleId;
                			 editRow(link);
                		    }
                    },{
                        "text":"删除",
                        "name":"delete",
                        "click": function deleteDetail(e){
                            var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
                            if(confirm("确定要删除数据["+dataItem.ruleName+"]吗？")){
                                $.post('${pageContext.request.contextPath}/rs/dep/base/depBaseProp/updateDelFlag', {ruleId: dataItem.ruleId}, function(data) {
                                    if(data.statusCode==200){
                                        alert(data.message);
                                        jQuery("#grid").data("kendoGrid").dataSource.read();
                                    }
                                });
                            }
                        }
                    },{
                        "text":"规则关系配置",
                        "name":"ruleRelationSetting",
                        "click":function ruleRelSetting(e){
                            var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
                            openRuleRelSettingWin(dataItem.ruleId);
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
         editRow('${pageContext.request.contextPath}/mx/dep/base/depBaseProp/edit?categoryId=${categoryId}');
    }

    function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑规则属性信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['',''],
			fadeIn: 100,
			area: ['800px', '650px'],
            iframe: {src: link}
		});
	}

    function openRuleRelSettingWin(ruleId){
        var link = "${pageContext.request.contextPath}/mx/dep/base/depBaseProp/addRel?rootCategoryId=${rootCategoryId}&ruleId="+ruleId;
        $.layer({
            type: 2,
            maxmin: true,
            shadeClose: true,
            title: "编辑规则属性信息",
            closeBtn: [0, true],
            shade: [0.8, '#000'],
            border: [10, 0.3, '#000'],
            offset: ['',''],
            fadeIn: 100,
            area: ['950px', '600px'],
            iframe: {src: link}
        });
    }

    // 引入属性
    // 弹出选择属性页面
    function copyProp(){
        var link = '${pageContext.request.contextPath}/mx/dep/base/depBaseProp/copyDepBaseList?categoryId=${categoryId}&filterId=${rootCategoryId}';
        $.layer({
            type: 2,
            maxmin: true,
            shadeClose: true,
            title: "编辑规则属性信息",
            closeBtn: [0, true],
            shade: [0.8, '#000'],
            border: [10, 0.3, '#000'],
            offset: ['',''],
            fadeIn: 100,
            area: ['800px', '650px'],
            iframe: {src: link}
        });
    }
 </script>
</head>
<body>
	<div id="main_content" class="k-content">
		<br>
		<div class="x_content_title">
			<img src="${pageContext.request.contextPath}/images/window.png"
				alt="规则属性列表">&nbsp; 规则属性列表
		</div>
		<br>
		<div id="grid"></div>
	</div>
	<br />
</body>
</html>