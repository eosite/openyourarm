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
<title>访问日志</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<link href="<%=request.getContextPath()%>/scripts/layer/skin/layer.css" rel="stylesheet" />
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/layer/layer.min.js"></script>
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
				url: '<%=request.getContextPath()%>/mx/accessLog/json?startTime=${startTime}&endTime=${endTime}&searchWords_enc=${searchWords_enc}',
				remoteSort: false,
				singleSelect: true,
				idField: 'id',
				columns:[[
				        {title:'序号', field:'startIndex', width:60, sortable:false},
						{title:'用户名', field:'userId', width:120, formatter:formatterUser},
						{title:'用户姓名', field:'userName', width:120},
						{title:'操作时间', field:'accessTime_datetime', width:120},
					    {title:'用时（毫秒）', field:'timeMillis', width:130},
						{title:'IP', field:'ip', width:150},
					    {title:'模块', field:'title', width:180},
					    {title:'链接', field:'uri', align:'left', width:320, formatter:formatterUri}
				]],
				rownumbers: false,
				pagination: true,
				pageSize: <%=com.glaf.core.util.Paging.DEFAULT_PAGE_SIZE%>,
				pageList: [10,15,20,25,30,40,50,100,200,500,1000],
				pagePosition: 'both'
			});

	});

 
    function formatterUser(val, row){
		var str = "<a href='javascript:viewUser(\""+row.userId_enc+"\");'>"+val+"</a>";
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

	function formatterUri(val, row){
          return "<label title='"+row.content+"'>"+val+"</label>";
	}

	function viewUser(actorId){
		var link = '<%=request.getContextPath()%>/mx/identity/user/view?actorId='+actorId;
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "用户信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['580px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
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


    function searchData2(){
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
                    url: '<%=request.getContextPath()%>/mx/accessLog/json',
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

	function deleteYesterday(){
		if(confirm("数据删除不能恢复，确定删除一天以前的访问日志吗？")){
          jQuery.ajax({
                    type: "POST",
                    url: '<%=request.getContextPath()%>/rs/accessLog/deleteYesterday',
                    dataType:  'json',
                    error: function(data){
                            alert('服务器处理错误！');
                    },
                    success: function(data){
						    alert(data.message);
                            location.reload();
                    }
               });
		}
	}

	function deleteLastWeek(){
		if(confirm("数据删除不能恢复，确定删除一周以前的访问日志吗？")){
          jQuery.ajax({
                    type: "POST",
                    url: '<%=request.getContextPath()%>/rs/accessLog/deleteLastWeek',
                    dataType:  'json',
                    error: function(data){
                            alert('服务器处理错误！');
                    },
                    success: function(data){
						    alert(data.message);
                            location.reload();
                    }
               });
		}
	}

	function searchData(){
		var startTime = jQuery("#startTime").val();
        var endTime = jQuery("#endTime").val();
		if(startTime != ""){
			//startTime = startTime + " 00:00:00";
		}
		if(endTime != ""){
			//endTime = endTime + " 23:59:59";
		}
		if(startTime != "" && endTime != "" && startTime > endTime){
			alert("开始时间不能大于结束时间。");
			return;
		}
		//alert(endTime);
		document.getElementById("startTime").value=startTime;
		document.getElementById("endTime").value=endTime;
		/*
        var params = jQuery("#iForm").formSerialize();
        jQuery.ajax({
                    type: "POST",
                    url: '<%=request.getContextPath()%>/mx/accessLog/json',
                    dataType:  'json',
                    data: params,
                    error: function(data){
                              alert('服务器处理错误！');
                    },
                    success: function(data){
                              jQuery('#mydatagrid').datagrid('loadData', data);
                    }
                  });
		*/
		document.iForm.submit();
	}
				 
</script>
</head>
<body style="margin:1px;"> 

<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
   <div data-options="region:'north',split:true,border:true" style="height:44px"> 
    <div class="toolbar-backgroud"  > 
	<form id="iForm" name="iForm" method="post">
	<table valign="middle">
	 <tr>
	    <td style="width:150px">
		   <img src="<%=request.getContextPath()%>/images/window.png">&nbsp;<span class="x_content_title">访问日志</span>
		</td>
		<td>
		   &nbsp;&nbsp;开始时间：<input type="text" id="startTime" name="startTime" class="easyui-datetimebox x-text" style="width:150px;" value="${startTime}">  
		</td>
		<td>
		   &nbsp;&nbsp;结束时间：<input type="text" id="endTime" name="endTime" class="easyui-datetimebox x-text" style="width:150px;" value="${endTime}"> 
		</td>
		<td>
		   &nbsp;&nbsp;关键字：<input type="text" id="searchWords" name="searchWords" class="x-searchtext" style="width:120px;" value="${searchWords}"> 
		</td>
		<td>
		   &nbsp;&nbsp;<button type="button" id="searchButton" class="btn btnGrayS" style="width: 60px" 
	               onclick="javascript:searchData();">查找</button>
		</td>
	 </tr>
	</table>
	</form> 
   </div> 
  </div> 
  <div data-options="region:'center',border:true">
	 <table id="mydatagrid"></table>
  </div>  
</div>
</body>
</html>
