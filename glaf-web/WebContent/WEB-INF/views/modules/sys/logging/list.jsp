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
<title>操作日志</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
   var contextPath="<%=request.getContextPath()%>";

   jQuery(function(){
		jQuery('#mydatagrid').datagrid({
				width:1000,
				height:480,
				fit:true,
				fitColumns: true,
				nowrap: false,
				striped: true,
				collapsible: true,
				url: '<%=request.getContextPath()%>/mx/logging/json?moduleId=${moduleId}',
				remoteSort: false,
				singleSelect: true,
				idField: 'id',
				columns:[[
				        {title:'序号', field:'startIndex', width:60, sortable:false},
						{title:'用户名',field:'actorId', width:90, formatter:formatterUser},
						{title:'用户姓名',field:'createUserName', width:90},
						{title:'操作时间',field:'createTime_datetime', width:120},
					    {title:'内容',field:'content', width:320},
						{title:'IP',field:'ip', width:130},
						{title:'时间（毫秒）',field:'timeMS', width:90},
					    {title:'处理状态', field:'flag', align:'center', width:90, formatter:formatterFlag}
				]],
				rownumbers: false,
				pagination: true,
				pageSize: <%=com.glaf.core.util.Paging.DEFAULT_PAGE_SIZE%>,
				pageList: [10,15,20,25,30,40,50,100,200,500,1000],
				pagePosition: 'both'
			});

			var p = jQuery('#mydatagrid').datagrid('getPager');
			jQuery(p).pagination({
				onBeforeRefresh:function(){
					//alert('before refresh');
				}
		    });
	});

 
    function formatterUser(val, row){
		var str = "<a href='javascript:viewUser(\""+row.actorId_enc+"\");'>"+val+"</a>";
	    return str;
	}

	function formatterFlag(val, row){
       if(val == 1){
		   return "<span style='color:green;'>成功</span>";
	   } else if(val == -1) {
           return "<span style='color:red;'>失败</span>";
	   } else {
		   return "<span style='color:blue;'>"+val+"</span>";
	   }
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
                    url: '<%=request.getContextPath()%>/mx/logging/json?moduleId=${moduleId}',
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
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',split:true,border:true" style="height:30px"> 
    <div class="toolbar-backgroud"  > 
	<img src="<%=request.getContextPath()%>/images/window.png">
	&nbsp;<span class="x_content_title">操作日志</span>
   </div> 
  </div> 
  <div data-options="region:'center',border:true">
	 <table id="mydatagrid"></table>
  </div>  
</div>
<form id="iForm" name="iForm" method="post">
   <input type="hidden" id="searchWord" name="searchWord">
   <input type="hidden" id="loginDateGreaterThanOrEqual" name="loginDateGreaterThanOrEqual">
   <input type="hidden" id="loginDateLessThanOrEqual" name="loginDateLessThanOrEqual">
</form> 
</body>
</html>
