<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<title>组件列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%> 
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:addRow();">新增</button> 
      <button type="button" id="createJsButton"  class="k-button" 
	  onclick="javascript:createRenderJs();">模板静态化</button>	  
   </div>
</script>
<script type="text/javascript">
    var dataItem;

   jQuery(function() {
	 var dataSource = new kendo.data.TreeListDataSource({
                            transport: {
                                read: {
                                    url: "<%=request.getContextPath()%>/rs/form/component/read",
                                    dataType: "json",
									contentType: "application/json",
                                    type: "POST"
                                },
								create: {
                                    url: "<%=request.getContextPath()%>/mx/form/component/create",
                                    dataType: "json",
									contentType: "application/json",
                                    type: "POST"
                                },
								update: {
                                    url: "<%=request.getContextPath()%>/mx/form/component/update",
                                    dataType: "json",
									contentType: "application/json",
                                    type: "POST"
                                },
                                parameterMap: function(options, operation) {
                                    if (operation !== "read") {
                                        return JSON.stringify(options);
                                    }
                                }
                            },
							batch: false,
                            schema: {
                                model: {
                                    id: "id",
                                    fields: {
                                        id: { type: "number", nullable: false },
                                        parentId: { field: "parentId", type: "number", nullable: true } 
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
        "selectable": "multiple, row",
        "toolbar": kendo.template(jQuery("#template").html()),
        "columns": [
        {
            "field": "name",
            "title": "组件名称",
            "width": "150px",
			"expandable": true,
			"lockable": false,
            "locked": false
        },
		{
            "field": "desc",
            "title": "组件描述",
            "width": "280px",
			"lockable": false,
            "locked": false
        },
        {
            "field": "type",
            "title": "类型",
            "width": "120px",
            "locked": false
        },
        {
            "field": "kendoComponent",
            "title": "KendoUI",
            "width": "150px",
            "locked": false
        },
        {
			"field": "dataRole",
			"title": "data-role",
            "width": "120px",
			"lockable": false,
			"locked": false
        },
		{
			"command": [{
					"text": "修改",
					"name": "edit2",
					"click": function showDetails(e) {
							  dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							  var link = "<%=request.getContextPath()%>/mx/form/component/edit?id="+dataItem.id;
							  editRow(link);
							}
						},
						{
					"text": "属性",
					"name": "edit4",
					"click": function showProps(e) {
							  dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							  //alert(dataItem.kendoComponent);
							  var link = "<%=request.getContextPath()%>/mx/form/component/property?componentId="+dataItem.id;
							  //editProps(link);
							  location.href=link;
							}
					  },
					  {
						"text": "模板",
						"name": "edit5",
						"click": function showTemplates(e) {
								  dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								  //alert(dataItem.kendoComponent);
								  var link = "<%=request.getContextPath()%>/mx/form/template/list?componentId="+dataItem.id;
								  //editProps(link);
								  location.href=link;
								}
					  }
			   ]
          }
		]
    });
  });


	function addRow(){
        editRow('<%=request.getContextPath()%>/mx/form/component/edit');
	}

    function editRow(link){
		//alert(link);
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑组件信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function editProps(link){
		//alert(link);
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "组件属性列表",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['880px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}
   function createRenderJs(){
	   jQuery.ajax({
		        url:  '<%=request.getContextPath()%>/rs/form/component/createRenderjs',
                type: "post",
                dataType: 'json',
                success: function (data) {
                    if (data.statusCode==200) {
                         alert("生成成功！");
                    }else{
						alert("生成失败！");
					}
                },
                error: function (xhr, ts) {
                    alert('生成控件渲染JS失败！');
                }
	   });
   }
 </script>
</head>
<body>
<div id="main_content" class="k-content">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="组件列表">&nbsp;
组件列表</div>
<br>
<div id="treelist"></div>
</div>  
<br/>
<br/>
<br/>
<br/>
</body>
</html>