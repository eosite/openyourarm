<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
	pageContext.setAttribute("contextPath", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>机构列表</title>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/layer/skin/layer.css"/>
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/icons.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/bootstrap/css/bootstrap.min.css" />
<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/grid/css/grid.css'>
<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/bim/css/style.bin.css'>
<script type="text/javascript" src="${contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/layer/layer.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/plugins/bootstrap/pagination/js/jquery.pagination.extends.js"></script>
<script src='${contextPath}/scripts/plugins/bootstrap/grid/js/jquery.grid.extends.js' type='text/javascript'></script>
<script src='${contextPath}/scripts/jquery.mtResizable.js' type='text/javascript'></script>


<style type="text/css">
.table-striped>tbody>tr:nth-of-type(odd) {
    background-color: #e6f2f8;
}
</style>

<script type="text/javascript">
var contextPath="<%=request.getContextPath()%>";
 
jQuery(function(){
	$("body").on("click",".createuser",function(event){
		deptUsers();
    });
	$("body").on("click",".changeBtn",function(event){
		editSelected();
    });
	$("body").on("click",".createrole",function(event){
		deptRoles();
    });
	   $("#gridId").grid({
	       // toolbar: "[{'name':'create','text':'新增'},{'name':'save','text':'保存'},{'name':'cancel','text':'取消'}]",
	        resizable: false,
			scrollable: false,
	        clickUpdate: false,
	        occupy: false,
	        sortable: {},
	        selectable: '',
	        showInLine: true,
	        sortDesc: false,
	        combineAble: false,
	         ajax: { 
	                read: { 
	                    url:'<%=request.getContextPath()%>/mx/branch/department/read',
	                    async: true, 
	                    data: {}, 
	                    success: function(ret) { 
	                        if (ret) { 
	                            return { 
	                                total: ret.total, 
	                                rows: ret.data 
	                            } 
	                        } else { 
	                            return ret; 
	                        } 
	                    } 
	                }, 
	                parameterMap: function(data) { 
	                    return JSON.stringify(data || {}); 
	                }, 
	                schema: { 
	                    model: { 
	                        id: "id", 
	                        fields: { 
	                            } 
	                    }, 
	                    data: "rows", 
	                    total: "total" 
	                } 
	            }, 
	            columns:[ 
	                {title:'序号', field:'startIndex', sortable:false,'isEditor':false, 'attributes': { 
	                    'style': 'text-align:center;width:15%;' 
	                },}, 
	                {title:'名称', field:'name', sortable:false,'isEditor':false, 'attributes': { 
	                    'style': 'text-align:center;width:15%;' 
	                },}, 
	                {title:'代码', field:'code', sortable:false,'isEditor':false, 'attributes': { 
	                    'style': 'text-align:center;width:15%;' 
	                },}, 
	                {title:'编码', field:'no', sortable:false,'isEditor':false, 'attributes': { 
	                    'style': 'text-align:center;width:15%;' 
	                },}, 
	                {title:'是否启用', field:'status', sortable:false,'isEditor':false, 'attributes': { 
	                    'style': 'text-align:center;width:15%;' 
	                },
	                'template':function(data){
	                   return data.locked?'禁用':"启用";
	                }
	                
	                }, 
	                 {title:'功能键',field:'functionKey',sortable:true,'isEditor':true,  
	                'attributes': { 
	                    'style': 'text-align:center;width:25%;' 
	                },
	                'template':function(data){
	                    var buttonHtml = '<button type="button" class="changeBtn">'+
	                    '<a href="#" onclick="javascript:editSelected2();">修改</a>  </button>';
	                    buttonHtml += '<button type="button"class="createuser">'+
	                    '<a href="#" onclick="javascript:deptUsers2();">机构用户</a>  </button>';
	                    buttonHtml += '<button type="button" class="createrole">'+
	                    '<a href="#" onclick="javascript:deptRoles2();">机构角色</a>  </button>';
	                    return buttonHtml;
	                }
	                 
	                 }
	           
	            ],
	           
	});
		
});
 
	var setting = {
		async: {
				enable: true,
				url:"<%=request.getContextPath()%>/mx/branch/department/treeJson",
				dataFilter: filter
			},
			callback: {
				beforeClick: zTreeBeforeClick,
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

	function zTreeOnExpand(treeId, treeNode){
        treeNode.icon="${contextPath}/scripts/ztree/css/zTreeStyle/img/diy/2.png";
	}

	function updateNode(treeId, treeNode){
		var zTree = jQuery.fn.zTree.getZTreeObj(treeId);
		zTree.setting.view.fontCss["color"] = "#0000ff";
		zTree.updateNode(treeNode);
	}

	function zTreeBeforeClick(treeId, treeNode, clickFlag) {
           
	}

	function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		$("#nodeId").val(treeNode.id);
		loadData('<%=request.getContextPath()%>/mx/branch/department/read?parentId='+treeNode.id);
	}

	function loadData(url){
		/* $.post(url,{qq:'xx'},function(data){
		      //var text = JSON.stringify(data); 
              //alert(text);
			  $('#gridId').grid('', data);
		},'json') */
		var treeObj = $.fn.zTree.getZTreeObj("myTree");
		var nodes =   treeObj.getSelectedNodes()
		var name = nodes[0].name;
		var id = nodes[0].id;
		$("#gridId").grid("query",{name:name,id:id});
	}


	$(document).ready(function(){
		$.fn.zTree.init($("#myTree"), setting);
	});

	function onMyDbClickRow(rowIndex, row){
		var link = '<%=request.getContextPath()%>/mx/branch/department/prepareModify?id='+row.id;
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "修改机构",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

    
	function deptRef(){
        var link = '<%=request.getContextPath()%>/mx/branch/departmentCorrelation?deptId='+deptId;
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "关联信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function editDept(deptId){
	    var link = '<%=request.getContextPath()%>/mx/branch/department/prepareModify?id='+deptId;
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "修改机构",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

    function addNew(){
		var nodeId = $("#nodeId").val();
		var link = "<%=request.getContextPath()%>/mx/branch/department/prepareAdd?parent="+nodeId;
	    //art.dialog.open(link, { height: 420, width: 680, title: "添加记录", lock: true, scrollbars:"yes" }, false);
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "修改机构",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function onRowClick(rowIndex, row){
	    var link = '<%=request.getContextPath()%>/mx/branch/department/prepareModify?id='+row.id;
	    //art.dialog.open(link, { height: 450, width: 680, title: "修改记录", lock: true, scrollbars:"yes" }, false);
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "修改机构",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function doImport(){
	  var link = "<%=request.getContextPath()%>/mx/sys/department/showImport";
	  var width=880;
	  var height=380;
	  var scroll="no";
	  //openWindow(url, width, height, scroll);
	  //art.dialog.open(link, { height: height, width: width, title: "部门用户", scrollbars:"no" , lock: false });
	  jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "部门导入",
				closeBtn: [0, true],
				shade: [0.8, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['20px',''],
				fadeIn: 100,
				area: ['980px', (jQuery(window).height() - 50) +'px'],
				iframe: {src: link}
			});
	}
	function reloadGrid(){
	    $('#gridId').grid('query');
	}

	function searchWin(){
	    $('#dlg').dialog('open').dialog('setTitle','机构列表查询');
	}

	function resize(){
		$('#gridId').grid('resize', {
			width:800,
			height:400
		});
	}

	function deleteSelected(){
	    var rows = $('#gridId').grid('getSelections');
	    if(rows == null || rows.length !=1){
		  //alert("请选择其中一条记录。");
		    var nodeId = $("#nodeId").val();
			if(nodeId != null && nodeId != ""){
                 if(confirm("机构删除后不能恢复，确定删除吗？")){
			        jQuery.ajax({
							   type: "POST",
							   url: '<%=request.getContextPath()%>/mx/branch/department/deleteById?deptId='+nodeId,
							   dataType:  'json',
							   error: function(data){
								   alert('服务器处理错误！');
							   },
							   success: function(data){
								   if(data != null && data.message != null){
									   alert(data.message);
								   } else {
									   alert('操作成功完成！');
								   }
								   if(data.statusCode == 200){
								       window.location.reload();
								   }
							   }
						 });
				 }
				 return;
			} else {
				 alert("请选择其中一条记录。");
		         return;
			}
		}

		var selected = $('#gridId').grid('getSelectedRows');
	    if ( selected ){
             if(confirm("机构删除后不能恢复，确定删除吗？")){
			        jQuery.ajax({
							   type: "POST",
							   url: '<%=request.getContextPath()%>/mx/branch/department/deleteById?deptId='+selected[0].id,
							   dataType:  'json',
							   error: function(data){
								   alert('服务器处理错误！');
							   },
							   success: function(data){
								   if(data != null && data.message != null){
									   alert(data.message);
								   } else {
									   alert('操作成功完成！');
								   }
								   if(data.statusCode == 200){
								       window.location.reload();
								   }
							   }
						 });
				 }
		} else {
		      alert("请选择其中一条记录。");
		      return;
		}
	}

	function editSelected(){
	    var rows = $('#gridId').grid('getSelections');
	    if(rows == null || rows.length !=1){
		  //alert("请选择其中一条记录。");
		    var nodeId = $("#nodeId").val();
			if(nodeId != null && nodeId != ""){
				var link = "<%=request.getContextPath()%>/mx/branch/department/prepareModify?id="+nodeId;
				jQuery.layer({
					type: 2,
					maxmin: true,
					shadeClose: true,
					title: "修改机构",
					closeBtn: [0, true],
					shade: [0.8, '#000'],
					border: [10, 0.3, '#000'],
					offset: ['20px',''],
					fadeIn: 100,
					area: ['680px', (jQuery(window).height() - 50) +'px'],
					iframe: {src: link}
				});
			} else {
				 alert("请选择其中一条记录。");
		         return;
			}
	    }
	    var selected = $('#gridId').grid('getSelectedRows');
	    if ( selected ){
		    var link = "<%=request.getContextPath()%>/mx/branch/department/prepareModify?id="+selected[0].id;
		    //art.dialog.open(link, { height: 450, width: 680, title: "修改记录", lock: true, scrollbars:"yes" }, false);
			jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "修改机构",
				closeBtn: [0, true],
				shade: [0.8, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['20px',''],
				fadeIn: 100,
				area: ['680px', (jQuery(window).height() - 50) +'px'],
				iframe: {src: link}
			});
	    }
	}
	
	
	function deptRoles(){
		var rows = $('#gridId').grid('getSelections');
	    if(rows == null || rows.length !=1){
		  alert("请选择其中一条记录。");
		  return;
	    }
	    var selected = $('#gridId').grid('getSelectedRows');
	    if ( selected){
		    var link = "<%=request.getContextPath()%>/mx/branch/department/deptRole?deptId="+selected[0].id;
		    //art.dialog.open(link, { height: 420, width: 680, title: "机构角色", lock: true, scrollbars:"yes" }, false);
			jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "机构角色",
				closeBtn: [0, true],
				shade: [0.8, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['20px',''],
				fadeIn: 100,
				area: ['680px', (jQuery(window).height() - 50) +'px'],
				iframe: {src: link}
			});
	    }
	}

	

	function deptUsers(){
		var rows = $('#gridId').grid('getSelections');
	    if(rows == null || rows.length !=1){
		    //alert("请选择其中一条记录。");
            var nodeId = $("#nodeId").val();
			if(nodeId != null && nodeId != ""){
				var link = "<%=request.getContextPath()%>/mx/branch/user?deptId="+nodeId;
				jQuery.layer({
					type: 2,
					maxmin: true,
					shadeClose: true,
					title: "机构用户",
					closeBtn: [0, true],
					shade: [0.8, '#000'],
					border: [10, 0.3, '#000'],
					offset: ['20px',''],
					fadeIn: 100,
					area: ['880px', (jQuery(window).height() - 50) +'px'],
					iframe: {src: link}
				});
			 } else {
				 alert("请选择其中一条记录。");
		         return;
			}
	    }
	    var selected = $('#gridId').grid('getSelectedRows');
	    if ( selected){
		    var link = "<%=request.getContextPath()%>/mx/branch/user?deptId="+selected[0].id;
		    //art.dialog.open(link, { height: 430, width: 880, title: "机构用户", lock: true, scrollbars:"yes" }, false);
			jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "机构用户",
				closeBtn: [0, true],
				shade: [0.8, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['20px',''],
				fadeIn: 100,
				area: ['880px', (jQuery(window).height() - 50) +'px'],
				iframe: {src: link}
			});
	    }
	}

	function deptRoles(){
		var rows = $('#gridId').grid('getSelections');
	    if(rows == null || rows.length !=1){
		    //alert("请选择其中一条记录。");
            var nodeId = jQuery("#nodeId").val();
			if(nodeId != null && nodeId != ""){
				var link = "<%=request.getContextPath()%>/mx/branch/department/showRole?deptId="+nodeId;
				jQuery.layer({
					type: 2,
					maxmin: true,
					shadeClose: true,
					title: "机构角色",
					closeBtn: [0, true],
					shade: [0.8, '#000'],
					border: [10, 0.3, '#000'],
					offset: ['20px',''],
					fadeIn: 100,
					area: ['880px', (jQuery(window).height() - 50) +'px'],
					iframe: {src: link}
				});
			 } else {
				 alert("请选择其中一条记录。");
		         return;
			}
	    }
	    var selected = $('#gridId').grid('getSelectedRows');
	    if ( selected){
		    var link = "<%=request.getContextPath()%>/mx/branch/department/showRole?deptId="+selected[0].id;
		    //art.dialog.open(link, { height: 430, width: 880, title: "机构用户", lock: true, scrollbars:"yes" }, false);
			jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "机构角色",
				closeBtn: [0, true],
				shade: [0.8, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['20px',''],
				fadeIn: 100,
				area: ['880px', (jQuery(window).height() - 50) +'px'],
				iframe: {src: link}
			});
	    }
	}

	
	function viewSelected(){
		var rows = $('#gridId').grid('getSelections');
		if(rows == null || rows.length !=1){
			alert("请选择其中一条记录。");
			return;
		}
		var selected = $('#gridId').grid('getSelectedRows');
		if (selected ){
		    location.href="<%=request.getContextPath()%>/mx/branch/department/prepareModify?id="+selected.id;
		}
	}

	function deleteSelections(){
		var ids = [];
		var rows = $('#gridId').grid('getSelectedRows');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].id);
		}
		if(ids.length > 0 && confirm("数据删除后不能恢复，确定删除吗？")){
		    var rowIds = ids.join(',');
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/branch/department/delete?rowIds='+rowIds,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						 alert('操作成功完成！');
					   }
					   $('#gridId').grid('query');
				   }
			 });
		} else {
			alert("请选择至少一条记录。");
		}
	}


	function getSelected(){
	    var selected = $('#gridId').grid('getSelected');
	    if (selected){
		  alert(selected.code+":"+selected.name+":"+selected.addr+":"+selected.col4);
	    }
	}

	function getSelectedRows(){
	    var ids = [];
	    var rows = jQuery('#gridId').grid('getSelectedRows');
	    for(var i=0;i<rows.length;i++){
		ids.push(rows[i].code);
	    }
	    alert(ids.join(':'));
	}

	function clearSelections(){
	    jQuery('#gridId').grid('clearSelections');
	}

	function searchData(){
	    var params = jQuery("#searchForm").formSerialize();
	    var queryParams = jQuery('#gridId').grid('options').queryParams;
	    jQuery('#gridId').grid('reload');	
	    jQuery('#dlg').dialog('close');
	}

	function sortOrg(){
	    var nodeId = jQuery("#nodeId").val();
		var link = "<%=request.getContextPath()%>/mx/branch/department/showSort?nodeId="+nodeId;
		var width=680;
		var height=430;
		var scroll="yes";
		jQuery.layer({
					type: 2,
					maxmin: true,
					shadeClose: true,
					title: "节点排序",
					closeBtn: [0, true],
					shade: [0.8, '#000'],
					border: [10, 0.3, '#000'],
					offset: ['20px',''],
					fadeIn: 100,
					area: ['650px', (jQuery(window).height() - 50) +'px'],
					iframe: {src: link}
				});
   }

   function deptRoles3(){
	    var nodeId = jQuery("#nodeId").val();
		var link = "<%=request.getContextPath()%>/mx/branch/department/showRole?nodeId="+nodeId;
		var width=680;
		var height=430;
		var scroll="yes";
		jQuery.layer({
					type: 2,
					maxmin: true,
					shadeClose: true,
					title: "机构角色",
					closeBtn: [0, true],
					shade: [0.8, '#000'],
					border: [10, 0.3, '#000'],
					offset: ['20px',''],
					fadeIn: 100,
					area: ['850px', (jQuery(window).height() - 50) +'px'],
					iframe: {src: link}
				});
   }


</script>
</head>
<body>  
<input type="hidden" id="nodeId" name="nodeId" value="" >
<div style="margin:0;"></div>  
<div class="container" data-options="fit:true" style="margin:0;">  

    <div class="left" data-options="region:'west',split:true" style="width:20%;">
	  <div class="easyui-layout" data-options="fit:true">  
           
			 <div data-options="region:'center',border:false,animate:true,dnd:true">
			    <ul id="myTree" class="ztree"></ul>  
			 </div> 
			 
        </div>  
	</div> 
	
    <div class="right" style="width:80%;">  

             <div style="background:#fafafa;padding:2px;border:1px solid #ddd;font-size:12px"> 
			   <div class="toolbar-backgroud"  > 
				<img src="<%=request.getContextPath()%>/images/window.png">
				&nbsp;
				<span class="x_content_title">
				  <a href="<%=request.getContextPath()%>/mx/branch/department" target="_blank">机构列表</a>
				</span>
				<button type="submit" class="btn btn-success" 
				   onclick="javascript:addNew();">新增</button>  
				<button  class="btn btn-primary" 
				   onclick="javascript:editSelected();">修改</button>  
				<button   class="btn btn-danger"
				   onclick="javascript:deleteSelected();">删除</button>  
				<button  class="btn"style="background:#BCD2EE;color:white"
				   onclick="javascript:deptUsers();">机构用户</button> 
				<button  class="btn btn-info"
				   onclick="javascript:deptRoles();">机构角色</button>
				<button   class="btn" style="background:#2894FF;color:white"
				   onclick="javascript:sortOrg();">同级排序</button>
				<button  class="btn" style="background:#5CADAD;color:white"
				   onclick="javascript:doImport();">导入</button>
				<button  class="btn" style="background:#93FF93;color:white"
				   onclick="javascript:reloadGrid();">全部</button> 
				<a href="<%=request.getContextPath()%>/mx/branch/department" target="_blank" >
				<button  class="btn" style="background:#00CC33;color:white"
				   onclick="javascript:reloadGrid();">新窗口</button> 
				</a> 
			   </div> 
             </div>  
			
			<table id="gridId"></table>
	    
  </div>  
</div>
</body>  
</html>