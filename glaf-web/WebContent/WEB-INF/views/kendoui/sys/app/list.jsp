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
<title>模块列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%> 
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:addRow();">新增</button>
	  <button type="button" id="impButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:showImport(3);">导入</button>
	  <!-- <button type="button" id="sortButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:sortMenu();">同级菜单排序</button> -->
   </div>
</script>
<script type="text/javascript">
    var dataItem;

    jQuery(function() {
	 var dataSource = new kendo.data.TreeListDataSource({
                            transport: {
                                read: {
                                    url: "<%=request.getContextPath()%>/rs/sys/application/read",
                                    dataType: "json",
									contentType: "application/json",
                                    type: "POST"
                                },
								create: {
                                    url: "<%=request.getContextPath()%>/mx/system/application/create",
                                    dataType: "json",
									contentType: "application/json",
                                    type: "POST"
                                },
								update: {
                                    url: "<%=request.getContextPath()%>/mx/system/application/update",
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
                                    id: "nodeId",
                                    fields: {
                                        nodeId: { type: "number", nullable: false },
                                        parentId: { field: "nodeParentId", type: "number", nullable: true },
										appId: { type: "number", nullable: false }
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
        "toolbar": kendo.template(jQuery("#template").html()),
        "columns": [
        {
            "field": "name",
            "title": "模块名称",
            "width": "180px",
			"expandable": true,
			"lockable": false,
            "locked": false
        },
        {
            "field": "code",
            "title": "代码",
            "width": "120px",
            "locked": false
        },
        {
            "field": "desc",
            "title": "模块描述",
            "width": "220px",
            "locked": false
        },
        {
            "field": "url",
            "title": "模块链接",
            "width": "320px",
            "locked": false
        },
		{
			"command": [{
						"text": "修改",
						"name": "edit2",
						"click": function showDetails(e) {
								  dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								  var link = "<%=request.getContextPath()%>/mx/system/application/edit?appId="+dataItem.appId;
								  editRow(link);
								}
						},{
						"text": "排序",
						"name": "sort2",
						"click": function showDetails2(e) {
								  dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								  sortMenu(dataItem.nodeId);
								}
						},{
						"text": "导出",
						"name": "exp2",
						"click": function showDetails2(e) {
								  dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								  var link='<%=request.getContextPath()%>/mx/sys/application/exportJson?parentId='+dataItem.nodeId;
								  window.open(link);
								}
						},{
						"text": "导入",
						"name": "imp2",
						"click": function showDetails2(e) {
								  dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								  showImport(dataItem.nodeId);
								}
						}]
          }
		]
      });
    });


	function showImport(id){
      var link='<%=request.getContextPath()%>/mx/sys/application/showImport?parentId='+id;
      jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "导入模块信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['650px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
    }


	function addRow(){
        editRow('<%=request.getContextPath()%>/mx/system/application/edit');
	}

    function editRow(link){
		//alert(link);
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑模块信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['620px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function sortMenu(id){
		var link="${contextPath}/mx/system/application/showSort?nodeId="+id;
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "模块排序",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['620px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}


 </script>
</head>
<body>
<div id="main_content" class="k-content">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="模块列表">&nbsp;
模块列表</div>
<br>
<div id="treelist"></div>
</div>     
<br/>
<br/>
<br/>
<br/>
</body>
</html>