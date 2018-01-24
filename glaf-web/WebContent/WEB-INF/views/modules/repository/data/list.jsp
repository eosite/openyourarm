<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.core.domain.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
	List<Database> databases = (List<Database>)request.getAttribute("databases");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据列表</title>
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
				url: '<%=request.getContextPath()%>/mx/repository/data/json?date=${date}&databaseId=${databaseId}',
				remoteSort: false,
				singleSelect: true,
				idField: 'id',
				columns:[[
				        {title:'序号', field:'startIndex', width:70, sortable:false},
					    {title:'主题', field:'title', width:180, sortable:false},
					    {title:'文件名', field:'filename', width:120, sortable:false},
					    {title:'发送者', field:'createBy', width:90, sortable:false},
					    {title:'接收者', field:'receiveBy', width:90, sortable:false},
					    {title:'文件大小', field:'size', width:80, sortable:false},
				        {title:'发送日期', field:'createDate_datetime', width:120, sortable:false},
					    {title:'接收日期', field:'receiveDate_datetime', width:120, sortable:false},
					    {field:'functionKey',title:'功能键',width:120, formatter:formatterKeys }
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


  function formatterKeys(val, row){
		var str = "<a href='javascript:download(\""+row.receiveBy+"\",\""+row.id+"\");'>下载</a>&nbsp;<a href='javascript:verify(\""+row.receiveBy+"\",\""+row.id+"\");'>验证</a>&nbsp;<a href='javascript:parse(\""+row.receiveBy+"\",\""+row.id+"\");'>解析</a>&nbsp;<c:if test="${fs_repository_delete eq 'true'}"><a href='javascript:deleteById(\""+row.receiveBy+"\",\""+row.id+"\");'>删除</a></c:if>";
	    return str;
  }

  function deleteById(userId, id){
      var link ="<%=request.getContextPath()%>/mx/repository/data/delete?id="+id+"&userId="+userId;
	  if(confirm("数据删除后不能恢复，确定删除吗？")){
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

    function parse(userId, id){
      var link ="<%=request.getContextPath()%>/mx/repository/data/parse?id="+id+"&userId="+userId;
	  if(confirm("确定解析数据规则并保存数据到数据库吗？")){
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

  function verify(userId, id){
      var link ="<%=request.getContextPath()%>/mx/repository/data/verify?id="+id+"&userId="+userId;
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
					        //window.location.reload();
					   }
				   }
		});
  }

  function download(userId, id){
      window.open("<%=request.getContextPath()%>/mx/repository/data/download?id="+id+"&userId="+userId);
  }

  function searchData(){
	  var date = jQuery("#date").val();
	  var databaseId = jQuery("#databaseId").val();
	  location.href="<%=request.getContextPath()%>/mx/repository/data?date="+date+"&databaseId="+databaseId;
  }

  function switchSystem(){
	  var date = jQuery("#date").val();
	  var databaseId = jQuery("#databaseId").val();
	  location.href="<%=request.getContextPath()%>/mx/repository/data?date="+date+"&databaseId="+databaseId;
  }

function myformatter(date){
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        var d = date.getDate();
        return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
    }

 function onSelect1(date){
    jQuery('#date').value=myformatter(date);
	document.getElementById("date").value=myformatter(date);
 }

 function showUpload(){
	 location.href="<%=request.getContextPath()%>/mx/repository/data/showUpload";
 }

</script>
</head>
<body style="margin:1px;">  
<div style="margin:0;"></div>  
<div class="easyui-layout" data-options="fit:true">  
<div data-options="region:'north',split:true,border:true" style="height:46px"> 
<div class="toolbar-backgroud"  > 
 <table width="100%">
  <tr>
   <td align="left" width="50%">
	<img src="<%=request.getContextPath()%>/images/window.png">
	&nbsp;<span class="x_content_title">数据列表</span></td>
	<td align="right" width="10%">
     <!-- <input type="button" id="restore" value="恢复数据" class="btnGray" onclick="javascript:restore();"> -->
	 <input type="button" id="upload" value="导入数据" class="btnGray" onclick="javascript:showUpload();">
   </td>
   <td align="right">
        <select id="databaseId" name="databaseId" onchange="javascript:switchSystem();">
			<option value="">----请选择----</option>
			<%if(databases != null && !databases.isEmpty()){
			   for(Database db: databases){
				   if(!"1".equals(db.getActive())){
					   continue;
				   }
			%>
			<option value="<%=db.getId()%>"><%=db.getTitle()%></option>   
			<% }
	         }
			%>
         </select>
		 <script type="text/javascript">
		     document.getElementById("databaseId").value="${databaseId}";
		 </script>
   </td>
   <td align="right">
    <input type="hidden" id="date" name="date">
    <input id="date2" name="date2" type="text"  data-options=" onSelect:onSelect1"
	       class="x-searchtext easyui-datebox" size="15" maxlength="200" 
		   value="${date}">
	<button  class="btn btnGray " onclick="javascript:searchData();">查找</button>
   </td>
  </tr>
  </table> 
 </div> 
 </div> 
  <div data-options="region:'center',border:true">
	 <table id="mydatagrid"></table>
  </div>  
</div>
</body>
</html>
