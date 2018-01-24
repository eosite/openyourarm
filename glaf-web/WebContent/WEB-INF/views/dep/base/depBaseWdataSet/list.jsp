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
    String code =  com.glaf.core.config.CustomProperties.getString("dataSet.code");
    code = RequestUtils.getString(request, "code", code == null ? "report_category" : code);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更新数据集列表</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/jquery.base64.js"></script>

<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
   <c:if  test="${param.retFn==null}">
      <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:addRow();">新增</button> 
		 <button type="button" class="k-button" style="width: 90px" title="刷新更新集内部字段"
	  onclick="javascript:refreshWDataSets();">刷新</button>     
   </c:if>
    <c:if  test="${param.retFn!=null}">
	   <button type="button" id="clearAll"  class="k-button" style="width: 90px" 
	          onclick="javascript:clearAll();">清除</button> 

       <button type="button" id="clearAll"  class="k-button" style="width: 90px" 
	          onclick="javascript:closeWin();">关闭</button>          
      </c:if>
   </div>
</script>
<script type="text/javascript">
	//事件定义器中的回调方法
	var retFn = "${param.retFn}";
	var getFn = "${param.getFn}";
	var _selectDatasource = [];
	function callback(dataItem){
		var data = {
			id : dataItem.id,
			depBaseWdataSetId : dataItem.depBaseWdataSetId,
			dataSetName: dataItem.dataSetName,
			dataSetDesc : dataItem.dataSetDesc,
			creator : dataItem.creator,
			createDatetime : dataItem.createDatetime,
			dynamicDataSet : dataItem.dynamicDataSet
		};
		var retObj = {
			name:dataItem.dataSetName,
			value:dataItem.id,
			data : data
		};
		parent[retFn](retObj);
		closeWin();
	}
	function closeWin(){
		parent.layer.close(parent.layer.getFrameIndex());
	}
	function clearAll(){
		callback({});
		closeWin();
	}

	var idata = [];
	if(getFn){
  		idata =  parent[getFn]();
  		if(idata && idata.data){
  			_selectDatasource.push(idata.data);
  		}	
    }

	 var setting = {
			async: {
				enable: true,
				url:"<%=request.getContextPath()%>/rs/tree/treeJson?nodeCode=<%=code%>",
			    dataFilter : filter
		},
		    callback : {
			    onClick : zTreeOnClick
		}
	};

	function filter(treeId, parentNode, childNodes) {
		if (!childNodes)
			return null;
		for (var i = 0, l = childNodes.length; i < l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			childNodes[i].icon = "<%=request.getContextPath()%>/images/basic.gif";
			}
			return childNodes;
		}


	    function zTreeOnClick(event, treeId, treeNode, clickFlag) {
	    	//jQuery("#nodeId").val(treeNode.id);
			<%-- var link="<%=request.getContextPath()%>/rs/dep/base/depBaseWdataSet/data?nodeId="+treeNode.id; --%>
			//alert(grid);
		//	grid.setDataSource(createGridDataSource(link));
			var zTree = $.fn.zTree.getZTreeObj("myTree"); 
		    var $grid = jQuery("#grid").data("kendoGrid"); 
		 
	        $grid.dataSource.query({
			    filter : {
				     filters : [ {
					 field : 'nodeId',
					 operator : 'eq',
					 value : treeNode.id
				 } ],
				logic : 'and'
			}
		    });
			//window.searchData();
	 	}

		function loadMxData(url){
			  location.href=url;
		}
		
		function refreshWDataSets(){
			
			if(!confirm("刷新更新集内部字段?")){
				return;
			}
			
			$.post("${contextPath}/mx/dep/base/depBaseWdataSet/refreshWDataSets", {
				
			}, function(data){
				alert(data.message);
			}, "JSON");
		}

	    jQuery(document).ready(function(){
				jQuery.fn.zTree.init(jQuery("#myTree"), setting);
		});

	jQuery(function() {
		if(getFn || retFn){
    		jQuery("#selectedGrid").kendoGrid({
		        "columnMenu": true,
		        "dataSource": _selectDatasource,
		        "height": 87,
		        // "reorderable": true,
		        // "filterable": true,
		        "sortable": true,
				// "pageable": {
		  //                      "refresh": true,
		  //                      "pageSizes": [5, 10, 15, 20, 25, 50, 100, 200, 500],
		  //                      "buttonCount": 10
		  //                    },
				"selectable": "single",
				"toolbar": '<div class="toolbar"><span class="x_content_title">已选更新集</span>(旧的没有需要重新选择)</div>',
		        "columns": [
					{
						"field" : "dataSetName",
						"title" : "更新集名称",
						"width" : 150,
						"lockable" : false,
						"locked" : false
					},
					{
						"field" : "tableName",
						"title" : "数据表名",
						"width" : 150,
						"lockable" : false,
						"locked" : false
					},
					{
						"field" : "dataTableName",
						"title" : "数据表物理表名",
						"width" : 150,
						"lockable" : false,
						"locked" : false
					},	
					{
						"field" : "creator",
						"title" : "创建人",
						"width" : 150,
						"lockable" : false,
						"locked" : false
					}, {
						"field" : "createDatetime",
						"title" : "创建时间",
						"width" : 150,
						"format" : "{0: yyyy-MM-dd}",
						"filterable" : {
							"ui" : "datetimepicker"
						},
						"lockable" : false,
						"locked" : false
					},
				    {
					"command": [{
				        "text": "更新定义",
				        "name": "def",
				        "click": function showSqlbuilder(e) {
				        	btns.def.call(this,e);
			                // var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
		                 //    //alert(JSON.stringify(dataItem));
		                 //    //alert(dataItem.id);

			                // var link = "<%=request.getContextPath()%>/mx/dataset/sqlbuilder?id="+dataItem.id;
			                // if(dataItem.dynamicDataSet && dataItem.dynamicDataSet == 'Y'){
			                // 	link = '<%=request.getContextPath()%>/mx/form/dataset/dynamicDatabase?id='+dataItem.id;
			                // }
			                // window.open(link);
		                }
			        }]
		          }
				],
		        // "scrollable": {},
		        // "resizable": true,
		        // "groupable": false
		    });
    	}else{
    		jQuery("#selectedGrid").remove();
    	}
		
		var $grid = jQuery("#grid")
				.kendoGrid(
						{
							"columnMenu" : true,
							"dataSource" : {
								"schema" : {
									"total" : "total",
									"model" : {
										"fields" : {
											"id" : {
												"type" : "string"
											},
											"dataSetCode" : {
												"type" : "string"
											},
											"dataSetName" : {
												"type" : "string"
											},
											"dataSetDesc" : {
												"type" : "string"
											},
											"ruleJson" : {
												"type" : "string"
											},
											"tableName" : {
												"type" : "string"
											},
											"dataTableName" : {
												"type" : "string"
											},
											"wtype" : {
												"type" : "string"
											},
											"ver" : {
												"type" : "number"
											},
											"creator" : {
												"type" : "string"
											},
											"createDatetime" : {
												"type" : "date",
												"format" : "{0: yyyy-MM-dd}"
											},
											"modifier" : {
												"type" : "string"
											},
											"modifyDatetime" : {
												"type" : "date",
												"format" : "{0: yyyy-MM-dd}"
											},
											"delFlag" : {
												"type" : "string"
											},
											"startIndex" : {
												"type" : "number"
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
										"contentType": "application/x-www-form-urlencoded; charset=UTF-8",
					                    "type": "POST",
					                    dataType : 'JSON',
										"url" : "<%=request.getContextPath()%>/rs/dep/base/depBaseWdataSet/data?node=${nodeId}"
									}
								},
								"serverFiltering" : true,
								"serverSorting" : true,
								"pageSize" : 10,
								"serverPaging" : true
							},
							"height" : retFn?"445px":x_height,
							"reorderable" : true,
							"filterable" : true,
							"sortable" : true,
							"pageable" : {
								"refresh" : true,
								"pageSizes" : [ 5, 10, 15, 20, 25, 50, 100,
										200, 500 ],
								"buttonCount" : 10
							},
							"selectable" : "single",
							"toolbar" : kendo.template(jQuery("#template")
									.html()),
							"columns" : [
							/* {
							"field": "dataSetCode",
							"title": "数据集代码",
							                "width": "150px",
							"lockable": false,
							"locked": false
							}, */
							{
								"field" : "dataSetName",
								"title" : "数据集名称",
								"width" : 150,
								"lockable" : false,
								"locked" : false
							}, {
								"field" : "dataSetDesc",
								"title" : "数据集描述",
								"width" : 150,
								"minScreenWidth" : 982,
								"lockable" : false,
								"locked" : false
							},
							/* {
							"field": "ruleJson",
							"title": "数据集规则JSON",
							                "width": "150px",
							"lockable": false,
							"locked": false
							}, */
							{
								"field" : "tableName",
								"title" : "数据表名",
								"width" : 150,
								"lockable" : false,
								"locked" : false
							}, {
								"field" : "dataTableName",
								"title" : "数据表物理表名",
								"width" : 150,
								"lockable" : false,
								"locked" : false
							}/* , {
								"field" : "wtype",
								"title" : "新增/更新",
								"minScreenWidth" : 982,
								"width" : 150,
								"lockable" : false,
								"locked" : false
							} , {
								"field" : "ver",
								"title" : "版本号",
								"minScreenWidth" : 982,
								"width" : 150,
								"lockable" : false,
								"locked" : false
							}*/, {
								"field" : "creator",
								"title" : "创建人",
								"width" : 150,
								"lockable" : false,
								"locked" : false
							}, {
								"field" : "createDatetime",
								"title" : "创建时间",
								"width" : 150,
								"format" : "{0: yyyy-MM-dd}",
								"filterable" : {
									"ui" : "datetimepicker"
								},
								"lockable" : false,
								"locked" : false
							}, {
								"field" : "modifier",
								"title" : "修改人",
								"minScreenWidth" : 982,
								"width" : 150,
								"lockable" : false,
								"locked" : false
							}, {
								"field" : "modifyDatetime",
								"title" : "修改时间",
								"minScreenWidth" : 982,
								"width" : 150,
								"format" : "{0: yyyy-MM-dd}",
								"filterable" : {
									"ui" : "datetimepicker"
								},
								"lockable" : false,
								"locked" : false
							},
							/* {
							"field": "delFlag",
							"title": "删除标识",
							                "width": "150px",
							"lockable": false,
							"locked": false
							}, */
							{
								title : '操作',
								"width" : 320,
								template : window.getTemplate
							} ],
							scrollable : true,
							"resizable" : true,
							"groupable" : false
						}).on("click.button.t", "button[t]", function(e) {
					var t = $(this).attr("t");
					t && (t = btns[t]) && (t.call($grid.data("kendoGrid"), e));
				});

	});

	var btns = {
		u : function(e) {
			var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
			var link = "${contextPath}/mx/dep/base/depBaseWdataSet/edit?id="
					+ dataItem.id;
			editRow(link);
		},
		d : function(e) {
			if (confirm("确定删除吗?")) {
				var dataItem = this.dataItem(jQuery(e.currentTarget).closest(
						"tr"));
				$.ajax({
					url : '${contextPath}/mx/dep/base/depBaseWdataSet/delete',
					data : {
						id : dataItem.id
					},
					type : 'post',
					dataType : 'JSON',
					success : function(ret) {
						if (ret && ret.message) {
							location.reload();
							alert(ret.message);
						}
					}
				});
			}
		},
		def : function(e) {
			var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
		//	var link = "${contextPath}/mx/dep/base/depBaseWdataSet?view=/dep/base/depBaseWdataSet/sqlcrud&id="
		//			+ dataItem.id
			var link = "${contextPath}/mx/form/defined/ex/sqlcrud?id=" + dataItem.id;
			link = "${contextPath}/mx/dep/base/depBaseWdataSet?view=/dep/base/depBaseWdataSet/sqlcrud&id=" + dataItem.id;
			window.open(link);
		},
		sure : function(e){
			var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
			callback(dataItem);
		}
	};

	function getTemplate(dataItem) {

		var buttons = [];

		if(retFn){
			buttons.push("<button class='k-button' t='sure' >选择</button>");
			buttons.push("<button class='k-button' t='def' >更新定义</button>");
		}else{
			buttons.push("<button class='k-button' t='u' >修改</button>");

			buttons.push("<button class='k-button' t='d' >删除</button>");

			buttons.push("<button class='k-button' t='def' >更新定义</button>");
		}

		return buttons.join(' ');
	}

	function addRow() {
		var treeObj = $.fn.zTree.getZTreeObj("myTree");
		var selectedNodes = treeObj.getSelectedNodes();
		if(!selectedNodes || selectedNodes.length == 0){
			alert("需要选择分类");
		}else{
			editRow('${contextPath}/mx/dep/base/depBaseWdataSet/edit?typeId='+selectedNodes[0].id);	
		}
	}
	function searchData(o){
		//var keywordsLike=document.getElementById("keywordsLike").value;
	//	keywordsLike = jQuery.base64.encode(keywordsLike);
		
	  //var grid = jQuery("#grid").data("kendoGrid");
		//grid.setDataSource(createGridDataSource(link));
		
		//params = $.extend((params || {}), {
		//	keywordsLike : keywordsLike
	//	});
		var zTree = $.fn.zTree.getZTreeObj("myTree"); 
		var $grid = jQuery("#grid").data("kendoGrid"); 
		 
	    $grid.dataSource.query({
			filter : {
				filters : [ {
					field : 'nodeId',
					operator : 'eq',
					value : treeNode.id
				} ],
				logic : 'and'
			}
		});

	}
	function editRow(link) {
		jQuery.layer({
			type : 2,
			maxmin : true,
			shadeClose : true,
			title : "编辑更新数据集信息",
			closeBtn : [ 0, true ],
			shade : [ 0.8, '#000' ],
			border : [ 10, 0.3, '#000' ],
			//	offset : [ '20px', '' ],
			fadeIn : 100,
			area : [ '620px', (260) + 'px' ],
			iframe : {
				src : link
			}
		});
	}
</script>
</head>
<body>
    <input type="hidden" id="nodeId" name="nodeId" value="${nodeId}">
	<div class="easyui-layout" data-options="fit:true">
	
		<div data-options="region:'west',split:true" style="width: 180px;">
			<div class="easyui-layout" data-options="fit:true">

				<div data-options="region:'center',border:false">
					<ul id="myTree" class="ztree"></ul>
				</div>

			</div>
		</div>
		<div data-options="region:'center'" style="overflow-y: scroll;">
			
			<div id="selectedGrid" class="mt-grid"></div>
			<div id="main_content" class="k-content">
				<c:if test="${param.retFn==null}">
					<br>
					<div class="x_content_title">
						<img src="${contextPath}/images/window.png" alt="更新数据集列表">&nbsp;
						更新数据集列表
					</div>
					<br>
				</c:if>
				<div id="grid" class="mt-grid"></div>
			</div>

		</div>
	</div>

</body>
</html>