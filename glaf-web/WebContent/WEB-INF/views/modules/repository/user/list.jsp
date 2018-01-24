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
				url: '<%=request.getContextPath()%>/mx/user/repository/data/json?date1=${date1}&date2=${date2}&databaseId=${databaseId}&xtype=${xtype}',
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
		var str = "<a href='javascript:download(\""+row.receiveBy+"\",\""+row.id+"\");'>下载</a>";
	    return str;
  }

  function download(userId, id){
      window.open("<%=request.getContextPath()%>/mx/user/repository/data/download?id="+id+"&userId="+userId);
  }

  function searchData(){
	  var date1 = jQuery("#date1").val();
	  var date2 = jQuery("#date2").val();
	  var xtype = jQuery("#xtype").val();
	  var databaseId = jQuery("#databaseId").val();
	  location.href="<%=request.getContextPath()%>/mx/user/repository/data?date1="+date1+"&date2="+date2+"&databaseId="+databaseId+"&xtype="+xtype;
  }

  function switchSystem(){
	  var date1 = jQuery("#date1").val();
	  var date2 = jQuery("#date2").val();
	  var xtype = jQuery("#xtype").val();
	  var databaseId = jQuery("#databaseId").val();
	  location.href="<%=request.getContextPath()%>/mx/user/repository/data?date1="+date1+"&date2="+date2+"&databaseId="+databaseId+"&xtype="+xtype;
  }

function myformatter(date){
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        var d = date.getDate();
        return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
    }

 function onSelect1(date){
    jQuery('#date1').value=myformatter(date);
	document.getElementById("date1").value=myformatter(date);
 }

 function onSelect2(date){
    jQuery('#date2').value=myformatter(date);
	document.getElementById("date2").value=myformatter(date);
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
   <td align="left" >
	<img src="<%=request.getContextPath()%>/images/window.png">
	&nbsp;<span class="x_content_title">数据列表</span></td>
   <td align="right" width="20%">
       存储库：
        <select id="databaseId" name="databaseId" onchange="javascript:switchSystem();">
			<option value="">----请选择----</option>
			<%if(databases != null && !databases.isEmpty()){
			   for(Database repo: databases){
				   if(!"1".equals(repo.getActive())){
					   continue;
				   }
			%>
			<option value="<%=repo.getId()%>"><%=repo.getTitle()%></option>   
			<% }
	         }
			%>
         </select>
		 <script type="text/javascript">
		     document.getElementById("databaseId").value="${databaseId}";
		 </script>
   </td>
   <td align="right" width="10%">
       类型：
       <select id="xtype" name="xtype">
		<option value="send">发送</option>   
		<option value="receive">接收</option>   
       </select>
	   <script type="text/javascript">
		     document.getElementById("xtype").value="${xtype}";
	   </script>
   </td>
   <td align="right" width="40%">
    <input type="hidden" id="date1" name="date1">
	<input type="hidden" id="date2" name="date2">
	日期：
    <input id="date21" name="date21" type="text" data-options=" onSelect:onSelect1"
	       class="x-searchtext easyui-datebox" size="15" maxlength="200" 
		   value="${date21}">
	~
	<input id="date22" name="date22" type="text" data-options=" onSelect:onSelect2"
	       class="x-searchtext easyui-datebox" size="15" maxlength="200" 
		   value="${date22}">
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
<!--/modules/repository/user/list.jsp-->