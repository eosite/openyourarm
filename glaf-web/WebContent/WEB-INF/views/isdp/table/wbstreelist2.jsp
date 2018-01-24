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

	//http://127.0.0.1:8080/glaf/mx/isdp/table/data/treelist?databaseId=223
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/webfile/js/jquery.ztree.extends.js" ></script>
<style type="text/css"> 
html {
	height: 100%;
	width: 100%;
	margin: 0;
}

body {
	height: 100%;
	width: 100%;
	margin: 0;
	font: 12px/1 Tahoma, Helvetica, Arial, "宋体", sans-serif;
	overflow: hidden;
}
.tdCls{
	align:left;
	height:16px;
	border-top:1px solid #ddd;
}
.selectCls{
	width : 90%;
}
.textCls{
	width : 65%;
}
</style>
<script type="text/javascript">

    var setting = {
			async: {
				enable: true,
				url: getAsyncUrl(),
				autoParam:["id"],
				dataFilter: filter
			},
			/**
			data: {
				simpleData: {
					enable:true,
					idKey: "id",
					pIdKey: "pId",
					rootPId: "-1"
				}
			},**/
			callback: {
				onClick: zTreeOnClick
			}
		};

	function getAsyncUrl(treeNode){
		 var parentId = "-1";
		 if(treeNode != null){
             alert(treeNode);
		 }
         return "<%=request.getContextPath()%>/mx/isdp/table/data/wbsTreeJson?databaseId=${databaseId}";
	}

    function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			if(childNodes[i].isParent){
			    //childNodes[i].icon="<%=request.getContextPath()%>/images/orm_root.gif";
			}
		}
		return childNodes;
	}

    function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		//alert(treeNode.id);
		document.getElementById("index_id").value=treeNode.id;
		<c:choose>
		  <c:when test="${!empty fillform }">
		var link="<%=request.getContextPath()%>/rs/isdp/table/data/fillform?databaseId=${databaseId}&index_id="+treeNode.id;
		  </c:when>
		  <c:otherwise>
		var link="<%=request.getContextPath()%>/rs/isdp/table/data/wbsTables?databaseId=${databaseId}&index_id="+treeNode.id;
	      </c:otherwise>
		</c:choose>
		var grid = jQuery("#grid").data("kendoGrid");
		grid.setDataSource(createGridDataSource(link));
	}

	jQuery(document).ready(function(){
		jQuery.fn.zTree.init(jQuery("#tree"), setting);
	});


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
                    "type": "POST",
                    "url": link
                }
            },
			serverFiltering: true,
            serverSorting: true,
            pageSize: 10,
            serverPaging: true,
            schema:{
            	total: "total",
            	data: "rows",
            	model:{
            		fields:{
                        "id": {
                            "type": "string"
                        },
                        "startIndex": {
                            "type": "number"
                        }
                    }
            	}
            }
		});
		return dataSource;
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
                    "type": "POST"
                }
             },
	        "serverFiltering": true,
            "serverSorting": true,
            "pageSize": 100,
            "serverPaging": true
        },
        "height": "500",
        "reorderable": true,
        "filterable": true,
        "sortable": true,
		"selectable": "single",
		//"toolbar": kendo.template(jQuery("#template").html()),
        "columns": [
			{
            "field": "name",
            "title": "名称",
            "width": "480px",
			"expandable": true,
			"lockable": false,
            "locked": false
            },
		    {
			"command": [{
						"text": "查看",
						"name": "view",
						"click": function showDetails(e) {
								      dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
								      var index_id = document.getElementById("index_id").value;
									  <c:choose>
		                                <c:when test="${!empty fillform }">
								     
									    </c:when>
		                                <c:otherwise>
											 var link = "<%=request.getContextPath()%>/mx/isdp/table/data/detail?databaseId=${databaseId}&tablename="+dataItem.id+"&index_id="+index_id;
								             show(link);
										</c:otherwise>
		                              </c:choose>
								}
						}
			           ]}
	     ],
        "scrollable": {},
        "resizable": true,
        "groupable": false
    });
  });


    function show(link){
		//alert(link);
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "详细信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['920px', (jQuery(window).height() - 48) +'px'],
            iframe: {src: link}
		});
	}

</script>
</head>
<body>

	<div id="container">
		<div id="vertical" style=''>
			
			<div id="north-pane" class="k-header k-footer footerCss">
				<table style="width: 100%;">
					<tr>
						<td style="width:500px;text-align: left;vertical-align: middle; " >
						<img src="<%=request.getContextPath()%>/images/setting_tools.png" style="vertical-align: middle;" /> 
							<span style="font-family:Lucida Calligraphy;font-size: 20px;font-weight: bolder;">数据列表</span>
							<span style="font-size: 16px;font-weight: bolder;"></span>
						</td>
						<td style="text-align:right;">
							 
						</td>
					</tr>
				</table>
			</div>
			
			<div id="center-pane" style="border:0px;">
				<div style="border:0px;">
					<div style='margin: 2px;'>
					    <input type="hidden" id="index_id" name="index_id" value="">
						<input type='text' class='k-textbox' id='searchTextBox' style='width:120px;' />
						<button id="button-4" class='k-button' type="button">搜索</button>
					</div>
					<div class="ztree" id="tree" style='height:auto;border:0px;'></div>
				</div>
				<div style="border:0px;">
					<div id="tabstrip-1" class='tabstrip' style="border:0px;">
 		              <div id="grid"></div>
 					</div>
				</div>
				<div style="border:0px;">
					<div id="tabstrip-2" class='tabstrip' style="border:0px;"></div>
				</div>
			</div>

		</div>
	</div>
 
</body>
</html>
<script type="text/javascript">
 
var $tabstrip = $(".tabstrip"),$vertical = $("#vertical"), contextPath = "<%=request.getContextPath()%>";

$vertical.css({
	height : $(window).height()
}).kendoSplitter({
	orientation: "vertical",
	panes: [
		{ collapsible: false , resizable : false, scrollable: false ,size : '32px'},
		{ collapsible: true, resizable : true, scrollable: false} 
	],
	layoutChange : onResize,
});

$("#center-pane").kendoSplitter({
	orientation: "horizontal",
	panes: [
		{ collapsible: true, resizable : true, scrollable: true, size : '420px'},
		{ collapsible: true, resizable : true, scrollable: true},
		{ collapsible: true, resizable : true, scrollable: false, size : '0px'}
	]
}); 

function onResize(e){
	
	$vertical.css({
		height : $(window).height()
	});
	
	$tabstrip.css({
		height : $tabstrip.closest('div[role=group]').height()
	});
	
}

$tabstrip.each(function(i,item){
	var $this = $(this);
	mtxx[$this.attr('id')] = $this.kendoTabStrip({
		animation:  {
			open: {
				effects: "fadeIn"
			}
		},
		select : function(e){
			var dd = $(this.wrapper);
			$('#' + e.contentElement.id).height(dd.innerHeight() - dd.children(".k-tabstrip-items").outerHeight() - 16);
		}
	});
});

</script>
