<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户在线日志</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
    <link href="<%=request.getContextPath()%>/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/scripts/metronic/4.5.2/theme/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
     <link href="<%=request.getContextPath()%>/scripts/metronic/4.5.2/theme/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <!-- iCheck -->
<link href="<%=request.getContextPath()%>/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/skins/all.css" rel="stylesheet" type="text/css" />
<link rel='stylesheet' type='text/css' href='<%=request.getContextPath()%>/scripts/plugins/bootstrap/grid/css/grid.css'>
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>

<script src="<%=request.getContextPath()%>/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/scripts/plugins/bootstrap/jquery.core.extends.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/bootstrap/login/js/jquery.validate.min.js"></script>
<!-- select2 -->
<script src="<%=request.getContextPath()%>/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>

<!-- iCheck -->
<script src="<%=request.getContextPath()%>/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/icheck.js" type="text/javascript"></script>

<!--validate-->
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/bootstrap/login/js/jquery.validate.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/bootstrap/jquery.core.extends.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/bootstrap/pagination/js/jquery.pagination.extends.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/bootstrap/js/bootstrap.min.js"></script>
<script src='<%=request.getContextPath()%>/scripts/plugins/bootstrap/grid/js/jquery.grid.extends.js' type='text/javascript'></script>
<script src='<%=request.getContextPath()%>/scripts/plugins/bootstrap/My97DatePicker/WdatePicker.js' type='text/javascript'></script>
<script src='<%=request.getContextPath()%>/scripts/jquery.mtResizable.js' type='text/javascript'></script>

<style type="text/css">
.page-size {
    float: left;
    margin-top: 10px;
    }
 .total {
    float: left;
    margin-top: 16px;
    }


</style>
<script type="text/javascript">
   var contextPath="<%=request.getContextPath()%>";

   jQuery(function(){
	   
	   $("#loginDateGreaterThanOrEqual").click(function(){
			WdatePicker();
		});
	   $("#loginDateLessThanOrEqual").click(function(){
			WdatePicker();
		});
	   $("#queryBtn").click(function(){
		   $("#gridId").grid("query",{"namelike":$("#namelike").val(),
			   "loginDateGreaterThanOrEqual":$("#loginDateGreaterThanOrEqual").val(),
			   "loginDateLessThanOrEqual":$("#loginDateLessThanOrEqual").val(),
		   });
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
	                    url:'<%=request.getContextPath()%>/mx/public/onlinelog/read',
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
	                {title:'用户姓名', field:'name', sortable:false,'isEditor':false, 'attributes': { 
	                    'style': 'text-align:center;width:10%;' 
	                },}, 
	                {title:'登录时间', field:'loginDate_datetime', sortable:false,'isEditor':false, 'attributes': { 
	                    'style': 'text-align:center;width:20%;' 
	                },}, 
	                {title:'登录IP', field:'loginIP', sortable:false,'isEditor':false, 'attributes': { 
	                    'style': 'text-align:center;width:20%;' 
	                },}, 
	                {title:'退出时间', field:'checkDate_datetime', sortable:false,'isEditor':false, 'attributes': { 
	                    'style': 'text-align:center;width:10%;' 
	                },}, 
	                {title:'会话编号', field:'sessionId', sortable:false,'isEditor':false, 'attributes': { 
	                    'style': 'text-align:center;width:20%;' 
	                },}
	                
	           
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

 
    function formatterUser(val, row){
		var str = "<a href='javascript:viewUser(\""+row.actorId_enc+"\");'>"+val+"</a>";
	    return str;
	}

	function viewUser(actorId){
		var link = '<%=request.getContextPath()%>/mx/identity/user/view?actorId='+actorId;
		art.dialog.open(link, { height: 420, width: 480, title: "查看用户信息", lock: true, scrollbars:"no" }, false);
	}

 
	function reloadGrid(){
	    jQuery('#mydatagrid').datagrid('reload');
	}

	function getSelected(){
	    var selected = jQuery('#mydatagrid').datagrid('getSelected');
	    if (selected){
		    alert(selected.code+":"+selected.name+":"+selected.addr+":"+selected.col4);
	    }
	}

	function getSelections(){
	    var ids = [];
	    var rows = jQuery('#mydatagrid').datagrid('getSelections');
	    for(var i=0;i<rows.length;i++){
		    ids.push(rows[i].code);
	    }
	    alert(ids.join(':'));
	}

	function clearSelections(){
	    jQuery('#mydatagrid').datagrid('clearSelections');
	}

	function loadGridData(url){
        jQuery.post(url,{qq:'xx'},function(data){
            //var text = JSON.stringify(data); 
            //alert(text);
            jQuery('#mydatagrid').datagrid('loadData', data);
        },'json');
	}


    function myformatter(date){
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        var d = date.getDate();
        return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
    }

    function myparser(s){
        if (!s) return new Date();
        var ss = (s.split('-'));
        var y = parseInt(ss[0],10);
        var m = parseInt(ss[1],10);
        var d = parseInt(ss[2],10);
        if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
            return new Date(y,m-1,d);
        } else {
            return new Date();
        }
    }


	function onSelect1(date){
        jQuery('#loginDateGreaterThanOrEqual').value=myformatter(date);
		document.getElementById("loginDateGreaterThanOrEqual").value=myformatter(date);
		//alert("date="+myformatter(date));
		//alert(document.getElementById("loginDateGreaterThanOrEqual").value);
    }

	function onSelect2(date){
        jQuery('#loginDateLessThanOrEqual').value=myformatter(date);
		document.getElementById("loginDateLessThanOrEqual").value=myformatter(date);
    }


    function searchData(){
		var searchWord = document.getElementById("searchWord2").value.trim();
        document.getElementById("searchWord").value = searchWord;
		//var startDate = document.getElementById("startDate").value;
        //document.getElementById("loginDateGreaterThanOrEqual").value = startDate;
		//var endDate = document.getElementById("endDate").value;
        //document.getElementById("loginDateLessThanOrEqual").value = endDate;
		var params = jQuery("#iForm").formSerialize();
		//alert(params);
		//alert(document.getElementById("startDate").value);
        jQuery.ajax({
                    type: "POST",
                    url: '<%=request.getContextPath()%>/mx/public/onlinelog/json',
                    dataType:  'json',
				    data: params,
                    error: function(data){
                            alert('服务器处理错误！');
                    },
                    success: function(data){
                            jQuery('#mydatagrid').datagrid('loadData', data);
                    }
               });
	}
		 		 
</script>
</head>
<body style="margin:1px;"> 

<div style="margin:0;"></div>  
    <%-- <div class="toolbar-backgroud"  > 
	<img src="<%=request.getContextPath()%>/images/window.png">
	&nbsp;<span class="x_content_title">用户在线日志</span>
	&nbsp;用户&nbsp; <input id="searchWord2" name="searchWord2" type="text" 
	       class="x-searchtext" size="30" maxlength="200"/>
    &nbsp;登录时间&nbsp; 
	<input id="startDate" name="startDate" type="text" 
	       class="x-searchtext easyui-datebox" size="15" maxlength="200" 
		   data-options=" onSelect:onSelect1"/>
	~ <input id="endDate" name="endDate" type="text" 
	       class="x-searchtext easyui-datebox" size="15" maxlength="200"
		   data-options=" onSelect:onSelect2"/>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-search'"
	   onclick="javascript:searchData();">查找</a>
   </div>  --%>
   
       <div class="form-group">
      		<label for="nameLike">用户:</label>
       			<input type="text" name="namelike" id="namelike" size="20"  class="form-control" style="width: 10%;display: inline-block;">
			<label for="nameLike">登录时间：</label>
				<input type="text" name="loginDateGreaterThanOrEqual" id="loginDateGreaterThanOrEqual" size="20" class="form-control" style="width: 10%;display: inline-block;">
				<input type="text" name="loginDateLessThanOrEqual" id="loginDateLessThanOrEqual" size="20"  class="form-control" style="width: 10%;display: inline-block;">
			 <button class="btn btn-primary" id="queryBtn" style=""><i class="fa fa-search"></i>查询</button>
   </div>

   <table id="gridId"></table>
<form id="iForm" name="iForm" method="post">
   <input type="hidden" id="searchWord" name="searchWord">
   <input type="hidden" id="loginDateGreaterThanOrEqual" name="loginDateGreaterThanOrEqual">
   <input type="hidden" id="loginDateLessThanOrEqual" name="loginDateLessThanOrEqual">
</form> 
</body>
</html>
