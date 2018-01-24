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
<title>执行参数</title>
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
				url: '<%=request.getContextPath()%>/mx/sys/parameterLog/json?businessKey=${businessKey}&dateAfter=${dateAfter}',
				remoteSort: false,
				singleSelect: true,
				idField: 'id',
				columns:[[
				        {title:'序号', field:'startIndex', width:60, sortable:false},
					    {title:'编号',field:'jobNo', width:185},
					    {title:'标题',field:'title', width:185},
					    {title:'内容',field:'content', width:420},
						{title:'用户',field:'createBy', width:80},
						{title:'时间',field:'createTime_datetime', width:120}
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

	function formatterNo(val, row){
		if(row.type=="dataset_slow"){
		    var str = "<a href='javascript:viewDataset(\""+row.businessKey+"\");'>"+val+"</a>";
	        return str;
		}
		return val;
	}

 
	function formatterStatus(val, row){
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

	function removeToday(){
		if(confirm("确定要删除当天的执行参数吗？")){
			var link = "<%=request.getContextPath()%>/mx/sys/parameterLog/delete?businessKey=${businessKey}";
			jQuery.ajax({
					   type: "POST",
					   url: link,
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
	}

	function removeOverdue(){
		if(confirm("确定要删除已经过期的执行参数吗？")){
			var link = "<%=request.getContextPath()%>/mx/sys/parameterLog/deleteOverdue";
			jQuery.ajax({
					   type: "POST",
					   url: link,
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
	}
	
    function searchData(){
        document.iForm.submit();
	}	
	
</script>
</head>
<body style="margin:1px;"> 

<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"  > 
	<form id="iForm" name="iForm" method="post" action="<%=request.getContextPath()%>/mx/sys/parameterLog">
	<img src="<%=request.getContextPath()%>/images/window.png">
	&nbsp;<span class="x_content_title">参数日志</span>
	<input name="btn1" type="button" value="删除当天日志" class="btnGray" onclick="javascript:removeToday();">
	&nbsp;
	<input name="btn2" type="button" value="删除过期日志" class="btnGray" onclick="javascript:removeOverdue();">
    &nbsp;&nbsp;日期：&nbsp;
	<select id="dateAfter" name="dateAfter">
		<option value="" selected>----全部----</option>
		<option value="1D">最近一天</option>
		<option value="2D">最近两天</option>
		<option value="3D">最近三天</option>
		<option value="4D">最近四天</option>
		<option value="5D">最近五天</option>
		<option value="6D">最近六天</option>
		<option value="1W">最近一周</option>
		<option value="2W">最近两周</option>
		<option value="1M">最近一月</option>
		<option value="2M">最近两月</option>
	</select>
	<script type="text/javascript">
	     document.getElementById("dateAfter").value="${dateAfter}";
	</script>
    &nbsp;&nbsp;
	<button type="button" id="searchButton" class="btn btnGray" style="width: 90px" 
	        onclick="javascript:searchData();">查找</button>
	</form>
   </div> 
  </div> 
  <div data-options="region:'center',border:true">
	 <table id="mydatagrid"></table>
  </div>  
</div>

</body>
</html>
