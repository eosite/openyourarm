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
<title>页面列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.base64.js"></script>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <span class="x_content_title"></span>&nbsp;&nbsp;
	  <input id="keywordsLike" name="keywordsLike" type="text" class="k-textbox"  
	         style="width:185px;" />
	  <button type="button" id="searchButton" class="k-button" style="width: 90px" 
	          onclick="javascript:searchData();">查找</button>
   </div>
</script>
<script type="text/javascript">
    var dataItem;

    var setting = {
			async: {
				enable: true,
				url:"<%=request.getContextPath()%>/mx/form/page/treeJson",
				dataFilter: filter
			},
			callback: {
				onClick: zTreeOnClick
			}
		};
  
  	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			childNodes[i].icon="<%=request.getContextPath()%>/images/basic.gif";
		}
		return childNodes;
	}


    function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		jQuery("#nodeId").val(treeNode.id);
		var link="<%=request.getContextPath()%>/rs/form/page/read?nodeParentId="+treeNode.id;
	    var grid = jQuery("#datagrid").data("kendoGrid");
		//alert(grid);
		grid.setDataSource(createGridDataSource(link));
 	}


    jQuery(document).ready(function(){
		jQuery.fn.zTree.init(jQuery("#ztree"), setting);
	});

    function reloadGrid(){
        var link="<%=request.getContextPath()%>/rs/form/page/read";
	    var grid = jQuery("#datagrid").data("kendoGrid");
		grid.setDataSource(createGridDataSource(link));
	}

	function searchData(){
		var keywordsLike=document.getElementById("keywordsLike").value;
		keywordsLike = jQuery.base64.encode(keywordsLike);
		var link="<%=request.getContextPath()%>/rs/form/page/read?keywordsLike_base64="+keywordsLike;
	    var grid = jQuery("#datagrid").data("kendoGrid");
		grid.setDataSource(createGridDataSource(link));
	}

	//创建grid表格数据源
	function createGridDataSource(link){
		var dataSource = new kendo.data.DataSource({
			type: "json",
            transport: {
            	parameterMap: function(options) {
                    return JSON.stringify(options);
                },
                read: {
		            "contentType": "application/json",
					"dataType": "json",
                    "type": "POST",
                    "url": link
                }
            },
			serverFiltering: true,
            serverSorting: true,
            pageSize: 20,
            serverPaging: true,
            schema:{
            	 model: {
                           id: "id"
                        }
            }
		});
		return dataSource;
	}


    jQuery(function() {
	    var dataSource = new kendo.data.DataSource({
                            transport: {
                                read: {
                                    url: "<%=request.getContextPath()%>/rs/form/page/read",
                                    dataType: "json",
									contentType: "application/json",
                                    type: "POST"
                                },
								create: {
                                    url: "<%=request.getContextPath()%>/mx/form/page/create",
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
                                    id: "id"
                                }
                            },
                            "pageSize": "20"
                        });

    jQuery("#datagrid").kendoGrid({
        "dataSource": dataSource,
        "height": x_height,
        "filterable": true,
        "sortable": true,
        "pageable": {
            "refresh": true,
            "pageSizes": [ 10, 15, 20, 25, 50, 100, 200, 500],
            "buttonCount": 20
         },
		"toolbar": kendo.template(jQuery("#template").html()),
        "columns": [
        {
            "field": "name",
            "title": "名称",
            "width": "320px",
			"expandable": true,
			"lockable": false,
            "locked": false
        },
		{
            "field": "title",
            "title": "标题",
            "width": "280px",
			"expandable": true,
			"lockable": false,
            "locked": false
        },
		{
            "field": "createBy",
            "title": "创建人",
            "width": "90px",
			"lockable": false,
            "locked": false
        },
        {
            "field": "createDate_datetime",
            "title": "创建日期",
            "width": "130px",
            "locked": false
        },
		{
			"command": [{
				    "text": "编辑",
					"name": "edit",
					"click": function(e) {
						 		dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								//window.open("<%=request.getContextPath()%>/mx/form/page/edit?id="+dataItem.id);
								editRow("<%=request.getContextPath()%>/mx/form/page/edit?id="+dataItem.id);
							}
					},
					{
					"text": "预览",
					"name": "show",
					"click": function(e) {
						 		dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								window.open("<%=request.getContextPath()%>/mx/form/page/viewPage?id="+dataItem.id);
							}
					},
					{
					"text": "发布",
					"name": "publish",
					"click": function(e) {
							 dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
							// console.log(dataItem);
							  var link = "<%=request.getContextPath()%>/mx/form/page/publish";
							 // console.log(link);
							 // alert(link);
							 $.ajax({
									type: "GET",
								 	url: link,
								 	data:"id="+dataItem.id ,
								 	dataType:'json',
								 	success: function(html){
								 		//console.log(html);
								 	   // alert(html);
								 	}
								});
							}
					 },
					{
					"text": "下载",
					"name": "download",
					"click": function(e) {
						 		dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								window.open("<%=request.getContextPath()%>/mx/form/page/download?id="+dataItem.id);
							}
					}
			   ]
          }
		]
      });
    });


    function editRow(link){
		//alert(link);
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['880px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

 </script>
</head>
<body>
<input type="hidden" id="nodeId" name="nodeId" value="${nodeId}" > 
<input type="hidden" id="nodeParentId" name="nodeParentId" value="${nodeId}" >
<div id="container" style="height: 100%; width: 100%;" class="k-content">
    <div id="vertical" style="height: 100%; width: 100%;">
        <div id="horizontal" style="height: 100%; width: 100%;">
            <!-- <div id="left-pane">
                <div class="pane-content"  style="height: 100%; width: 100%;">
                    <ul id="ztree" class="ztree"></ul>  
                </div>
            </div> -->
            <div id="center-pane">
                <div class="pane-content"  style="height: 100%; width: 100%;">
                    <div id="datagrid"></div>
                </div>
            </div>
        </div>
    </div>    
</div>
<script>
                $(document).ready(function() {
                    $("#vertical").kendoSplitter({
                        orientation: "vertical",
                        panes: [
                            { collapsible: false },
                            { collapsible: false, size: "100px" },
                            { collapsible: false, resizable: false, size: "100px" }
                        ]
                    });

                    $("#horizontal").kendoSplitter({
                        panes: [
                            { collapsible: true, resizable: true, size: "280px"  },
                            { collapsible: false },
                            { collapsible: true}
                        ]
                    });
                });
            </script>
</body>
</html>