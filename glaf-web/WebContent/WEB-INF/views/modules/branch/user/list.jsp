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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/layer/skin/layer.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/bootstrap/css/bootstrap.min.css" />
<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/grid/css/grid.css'>
<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/bim/css/style.bin.css'>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/layer/layer.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/bootstrap/jquery.core.extends.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/bootstrap/pagination/js/jquery.pagination.extends.js"></script>
<script src='<%=request.getContextPath()%>/scripts/plugins/bootstrap/grid/js/jquery.grid.extends.js' type='text/javascript'></script>
<script src='<%=request.getContextPath()%>/scripts/jquery.mtResizable.js' type='text/javascript'></script>
<style type="text/css">
.page-size {
    float: left;
    margin-top: 10px;
    }
 .total {
    float: left;
    margin-top: 15px;
    }
    
</style>
<script type="text/javascript">

   jQuery(function(){
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
	                    url:'<%=request.getContextPath()%>/mx/branch/user/json?parent=${parent}&deptId=${deptId}',
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
	                    'style': 'text-align:center;width:10%;' 
	                },}, 
	                {title:'用户名', field:'actorId', sortable:false,'isEditor':false, 'attributes': { 
	                    'style': 'text-align:center;width:10%;' 
	                },}, 
	                {title:'姓名', field:'name', sortable:false,'isEditor':false, 'attributes': { 
	                    'style': 'text-align:center;width:10%;' 
	                },}, 
	                {title:'部门', field:'deptName', sortable:false,'isEditor':false, 'attributes': { 
	                    'style': 'text-align:center;width:10%;' 
	                },}, 
	                {title:'最近登录日期', field:'lastLoginTime', sortable:false,'isEditor':false, 'attributes': { 
	                    'style': 'text-align:center;width:10%;' 
	                },}, 
	                {title:'是否有效', field:'status', sortable:false,'isEditor':false, 'attributes': { 
	                    'style': 'text-align:center;width:10%;' 
	                },
	                'template':function(data){
	                   return data.locked?'禁用':"启用";
	                }
	                
	                }, 
	                
	            ],
	            pagination: {
	                paging: true,
	                page: 1,
	                pageable: {
	                    refresh: true,
	                    buttonCount: 10,
	                    
	                },
	                pageSize: 10
	            }
	           
	});
	   
	   
	});


	

	function addNew(){
	    //location.href="<%=request.getContextPath()%>/branch/user.do?method=edit";
	    var link="<%=request.getContextPath()%>/mx/branch/user/prepareAdd?parent=${parent}&deptId=${deptId}";
	    //art.dialog.open(link, { height: 430, width: 620, title: "添加用户", lock: true, scrollbars:"no" }, false);
		jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "添加用户",
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
            //window.open('<%=request.getContextPath()%>/branch/user.do?method=edit&id='+row.id);
	    var link = '<%=request.getContextPath()%>/mx/branch/user/prepareModify?parent=${parent}&deptId=${deptId}&id='+row.actorId_enc;
	    //art.dialog.open(link, { height: 430, width: 620, title: "修改用户", lock: true, scrollbars:"no" }, false);
		jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "修改用户",
				closeBtn: [0, true],
				shade: [0.8, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['20px',''],
				fadeIn: 100,
				area: ['680px', (jQuery(window).height() - 50) +'px'],
				iframe: {src: link}
			});
	}

	function searchWin(){
	    $('#dlg').dialog('open').dialog('setTitle','用户查询');
	    //jQuery('#searchForm').form('clear');
	}

	function resize(){
		$('#gridId').grid('resize', {
			width:800,
			height:400
		});
	}

	function editSelected(){
	    var rows = $('#gridId').grid('getSelections');
	    if(rows == null || rows.length !=1){
		  alert("请选择其中一条记录。");
		  return;
	    }
	    var selected = $('#gridId').grid('getSelectedRows');
	    if (selected ){
		  //location.href="<%=request.getContextPath()%>/branch/user.do?method=edit&id="+selected[0].id;
		  var link = "<%=request.getContextPath()%>/mx/branch/user/prepareModify?parent=${parent}&deptId=${deptId}&id="+selected[0].actorId_enc;
		  //art.dialog.open(link, { height: 430, width: 620, title: "修改用户", lock: true, scrollbars:"no" }, false);
		  jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "修改用户",
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


	function resetPwd(){
		var rows = jQuery('#gridId').grid('getSelections');
	    if(rows == null || rows.length !=1){
		  alert("请选择其中一条记录。");
		  return;
	    }
	    var selected = $('#gridId').grid('getSelectedRows');
	    if (selected ){
		  //location.href="<%=request.getContextPath()%>/branch/user.do?method=edit&id="+selected[0].id;
		  var link = "<%=request.getContextPath()%>/mx/branch/user/prepareResetPwd?parent=${parent}&deptId=${deptId}&id="+selected[0].actorId_enc;
		  //alert(link);
		  //art.dialog.open(link, { height: 300, width: 465, title: "重置用户密码", lock: true, scrollbars:"no" }, false);
		  jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "重置用户密码",
				closeBtn: [0, true],
				shade: [0.8, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['20px',''],
				fadeIn: 100,
				area: ['580px', (jQuery(window).height() - 150) +'px'],
				iframe: {src: link}
			});
	    }
	}

	function userRoles(){
		var rows = $('#gridId').grid('getSelections');
	    if(rows == null || rows.length !=1){
		  alert("请选择其中一条记录。");
		  return;
	    }
	    var selected = $('#gridId').grid('getSelectedRows');
	    if (selected ){
		  //location.href="<%=request.getContextPath()%>/branch/user.do?method=edit&id="+selected[0].id;
		  var link = "<%=request.getContextPath()%>/mx/branch/user/showRole?parent=${parent}&deptId=${deptId}&actorId="+selected[0].actorId_enc;
		  //art.dialog.open(link, { height: 420, width: 620, title: "用户角色设置", lock: true, scrollbars:"no" }, false);
		  jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "用户角色设置",
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

	function viewSelected(){
		var rows = $('#gridId').grid('getSelections');
		if(rows == null || rows.length !=1){
			alert("请选择其中一条记录。");
			return;
		}
		var selected = $('#gridId').grid('getSelectedRows');
		if (selected ){
		    var link ="<%=request.getContextPath()%>/mx/branch/user/prepareModify?parent=${parent}&deptId=${deptId}&id="+selected[0].actorId_enc;
			jQuery.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "修改用户",
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

	function deleteSelections(){
		var ids = [];
		var rows = $('#gridId').grid('getSelectedRows');
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].actorId);
		}
		if(ids.length > 0 && confirm("数据删除后不能恢复，确定删除吗？")){
		    var x_ids = ids.join(',');
			//alert(x_ids);
			jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/branch/user/delete?userIds='+x_ids,
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

	function reloadGrid(){
	    jQuery('#gridId').grid('query');
	}

	function getSelectedRows(){
	    var selected = $('#gridId').grid('getSelectedRows');
	    if (selected){
		alert(selected.code+":"+selected.name+":"+selected.addr+":"+selected.col4);
	    }
	}

	function getSelections(){
	    var ids = [];
	    var rows = $('#gridId').grid('getSelections');
	    for(var i=0;i<rows.length;i++){
		ids.push(rows[i].code);
	    }
	    alert(ids.join(':'));
	}

	function clearSelections(){
	    $('#gridId').grid('clearSelections');
	}

	function searchData(){
	    var params = $("#searchForm").formSerialize();
	    var queryParams = $('#gridId').grid('options').queryParams;
	    $('#gridId').grid('query');	
	    $('#dlg').dialog('close');
	}
		 
</script>
</head>
<body style="margin:1px;">  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"  > 
	<img src="<%=request.getContextPath()%>/images/window.png">
	&nbsp;<span class="x_content_title">用户列表</span>
    <a href="#" class="btn btn-success"
	   onclick="javascript:addNew();">新增</a>  
    <a href="#" class="btn btn-primary" 
	   onclick="javascript:editSelected();">修改</a>  
	<a href="#" class="btn btn-danger"
	   onclick="javascript:deleteSelections();">删除</a>
	<a href="#" class="btn btn-warning"
	   onclick="javascript:resetPwd();">重置密码</a>  
	<a href="#" class="btn btn-info" 
	   onclick="javascript:userRoles();">用户角色</a>
	<!-- <a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-search'"
	   onclick="javascript:searchWin();">查找</a> -->
   </div> 
  </div>
  <table id="gridId"></table> 
  <!-- <div data-options="region:'center',border:true">
	 <table id="gridId"></table>
  </div>   -->
</div>
</body>
</html>
