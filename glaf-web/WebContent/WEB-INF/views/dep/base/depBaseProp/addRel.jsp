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
<title>规则关系配置</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <button type="button" id="newButton"  class="k-button" style="width: 60px" 
	  onclick="javascript:addRow();"><span class="k-icon k-i-plus"></span>新增</button>
	  <button type="button" id="newButton"  class="k-button" style="width: 60px" 
	  onclick="javascript:saveGrid();"><span class="k-icon k-i-tick"></span>保存</button>
	  <button type="button" id="newButton"  class="k-button" style="width: 60px" 
	  onclick="javascript:closeWin();"><span class="k-icon k-i-close"></span>关闭</button>           
   </div>
</script>
<script type="text/javascript">
	var editors = {
        stuff : function(container){
            var $tr = container.closest('tr');
            return {
                tr : $tr,
                index : $tr.index(),
                grid : $tr.closest('[data-role=grid]').data('kendoGrid')
            };
        },
        textbox : function(container,options){
            var s = editors.stuff(container),$input = $("<input/>",{
                name : options.field,
                class : 'k-textbox'
            }).appendTo(container).change(function(e){
                s.grid.dataSource.data()[s.index][options.field] = $(this).val();
            });                                 
            return $input;                                  
        },
        dropdownlist : function(container,options,dropdownlist){
            
            var $input = $("<input/>",{
                name : options.field
            }).appendTo(container).kendoDropDownList(dropdownlist);
            
            return $input;
            
        },
        checkbox : function(container,options){
            var $input = $("<input/>",{
                name : options.field,
                type : 'checkbox'
            }).appendTo(container);                                 
            return $input;
        }
    };
  jQuery(function() {
  	var dicts = ${dicts};

      jQuery("#grid").kendoGrid({
        "columnMenu": true,
        "dataSource": {
            "schema": {
                "total": "total",
                "model": {
                	"id": "",
                    "fields": {
                    	"ruleId":{
                    		"type":"string",
                    		"defaultValue":"${ruleId}",
                    		"validation":{nullable:true}
                    	},
                        "relRuleId": {
                        	"type": "string",
                        	"validation":{nullable:true}
                        },
                        "relRuleName": {
                        	"type": "string"
                        },
                        "relType": {
                         	"type": "string",
                         	"validation":{required:true}
                        },
                        "creator": {
                         	"type": "string"
                        },
                        "createDateTime": {
							"type": "date",
							"format": "{0: yyyy-MM-dd}"
                        }
                    }
                },
                "data": "rows"
            },
            "transport": {
                "parameterMap": function(options,operation) {
                    if (operation !== "read" && options.models) {
                    	if(operation === "destroy"){
                    		var params = options.models;
                    		return params[0];
                    	}
                        return {models: kendo.stringify(options.models)};
                    }else{
                    	return JSON.stringify(options);
                    }
                },
                "read": {
                    "type": "POST",
                    "contentType": "application/json",
                    "url": "${pageContext.request.contextPath}/rs/dep/base/depBasePropRel/dataByRuleId?ruleId=${ruleId}"
                },
                "create":{
					"url": "${pageContext.request.contextPath}/rs/dep/base/depBasePropRel/saveDepBasePropRel?ruleId=${ruleId}",
                	"type": "POST"
                },
                "update": {
                	"url": "${pageContext.request.contextPath}/rs/dep/base/depBasePropRel/saveDepBasePropRel?ruleId=${ruleId}",
                	"type": "POST"
                },
                "destroy": {
                	"url": "${pageContext.request.contextPath}/rs/dep/base/depBasePropRel/deleteByPrimaryKey",
                	"type": "POST"
                }
            },
        	"batch": true,
	    	"serverFiltering": true,
            "serverSorting": true,
            "pageSize": 10,
            "serverPaging": true
        },
        "height": "480px",
        "reorderable": true,
        "filterable": true,
        "sortable": true,
        "editable":true,
		"pageable": {
           "refresh": true,
           "pageSizes": [5, 10, 15, 20, 25, 50, 100, 200, 500],
           "buttonCount": 10
         },
		"selectable": "single",
		"toolbar": kendo.template(jQuery("#template").html()),
            "columns": [
            {
            	"field": "ruleId",
            	"title": "规则1",
            	"hidden": true
            },
            {
				"field": "relRuleId",
				"title": "规则",
	            "width": "450px",
				"lockable": false,
				"locked": false,
				"editor":function(container,options){
                    $("body").data("uuid", options.model.uid);
                    editors.textbox(container,options).dblclick(function(event) {
                        selectRelWin();
                    });
				},
				"template":function(dataItem){
					return dataItem.relRuleName
				}
            },
            {
				"field": "relType",
				"title": "关系类型",
	            "width": "250px",
				"lockable": false,
				"locked": false,
				"editor":function(container,options){
					var input = $("<input/>");
					input.attr("name", options.field);
					input.appendTo(container);
					input.kendoDropDownList({
						dataSource:new kendo.data.DataSource({
			          		transport:{
								read:{
									url:"${pageContext.request.contextPath}/rs/form/formDictory/dataByTreeCode",
									type: "POST",
									data:{treeCode:"ruleRelType"},
									dataType:"json"
								}
							}
			          	}),
			          	dataTextField:"name",
			          	dataValueField:"value"
					});
				},
				"template":function(dataItem){
					return dataItem.relType==""?"":dicts[dataItem.relType];
				}
            },
		    {
			"command": [{
				"text":"删除",
				"click":function(e){
					this.removeRow($(e.currentTarget).closest("tr"));
					this.dataSource.sync();
				}
			}]
	      }
		],
        "resizable": true,
        "groupable": false
    });
  });


    function addRow(){
         var grid = $("#grid").data("kendoGrid");
         grid.addRow();
    }
    function saveGrid(){
    	var grid = $("#grid").data("kendoGrid");
    	$.each(grid.dataSource.data(), function(index, val) {
    		 val.dirty = true;
    	});
    	grid.dataSource.sync();
    }
    function closeWin(){
    	var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    	parent.layer.close(index); //执行关闭
    }

    var relWinLayerIndex = -1;
    function selectRelWin(){
    	var link = "${pageContext.request.contextPath}/mx/dep/base/depBasePropCategory/frame?list=list_select&rootCategoryId=${rootCategoryId}";
        relWinLayerIndex = $.layer({
            type: 2,
            maxmin: true,
            shadeClose: true,
            title: "编辑规则属性信息",
            closeBtn: [0, true],
            shade: [0.8, '#000'],
            border: [10, 0.3, '#000'],
            offset: ['',''],
            fadeIn: 100,
            area: ['800px', '550px'],
            iframe: {src: link}
        });
    }

    function retFunc(data){
        var uuid = $("body").data("uuid");
        var grid = $("#grid").data("kendoGrid");
        var dataItem = grid.dataSource.getByUid(uuid);
        dataItem.relRuleId = data.ruleId;
        dataItem.relRuleName = data.ruleName;
        grid.refresh();
        layer.close(relWinLayerIndex);
    }

 </script>
</head>
<body>
<div id="main_content" class="k-content">
<br>
 <div class="x_content_title">
  <img src="<%=request.getContextPath()%>/images/window.png" alt="规则关系列表">&nbsp;
    规则关系列表
 </div>
<br>
<div id="grid"></div>
</div>     
<br/>
</body>
</html>