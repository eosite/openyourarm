<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
int pageSize=Constants.PAGE_SIZE;
com.glaf.core.util.PageResult pager=(com.glaf.core.util.PageResult)request.getAttribute("pager");
String nameLike = request.getAttribute("nameLike").toString();
String descLike = request.getAttribute("descLike").toString();
List list = pager.getResults();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link href="<%=request.getContextPath()%>/scripts/layer/skin/layer.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/layer/layer.min.js"></script>
<script type="text/javascript" src='<%=context%>/scripts/verify.js'></script>
<script type="text/javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript">
var num=0;
function checkOperation(form){
  num = getCheckedBoxNum(form,"id");
  if(num !=""){
    document.all.btn_del.disabled=false;
	if(num==1){
	  document.all.btn_modify.disabled=false;
	  document.all.btn_users.disabled=false;
	  document.all.btn_leader_users.disabled=false;
	}else{
	  document.all.btn_modify.disabled=true;
	  document.all.btn_users.disabled=true;
	  document.all.btn_leader_users.disabled=true;
	}
  }else{
    document.all.btn_del.disabled=true;
	document.all.btn_modify.disabled=true;
	document.all.btn_users.disabled=true;
	document.all.btn_leader_users.disabled=true;
  }
}

function add(){
  var link = "<%=request.getContextPath()%>/mx/identity/group/prepareAdd?type=${type}";
  var width=450;
  var height=250;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "添加群组", lock: false, scrollbars:"no" }, false);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "添加群组",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}
function modify(form){
  var id =0;
  for (var i=0;i<form.id.length;i++) {
    var e = form.id[i];
    if (e.checked){
	  id=e.value;
	}     
  }
  var link = "<%=request.getContextPath()%>/mx/identity/group/prepareModify?groupId="+id;
  var width=450;
  var height=250;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "修改群组", lock: false, scrollbars:"no" }, false);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "修改群组",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function users(form){
  var id =0;
  for (var i=0;i<form.id.length;i++) {
    var e = form.id[i];
    if (e.checked){
	  id=e.value;
	}     
  }
  var link = "<%=request.getContextPath()%>/mx/identity/group/groupUsers?groupId="+id;
  var width=450;
  var height=480;
  var scroll="yes";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "群组用户", lock: false, scrollbars:"no" }, false);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "群组用户",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function userLeaders(form){
  var id =0;
  for (var i=0;i<form.id.length;i++) {
    var e = form.id[i];
    if (e.checked){
	  id=e.value;
	}     
  }
  var link = "<%=request.getContextPath()%>/mx/identity/group/groupLeaders?groupId="+id;
  var width=450;
  var height=480;
  var scroll="yes";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "群组领导", lock: false, scrollbars:"no" }, false);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "群组领导",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function del(){
  var form = document.all.command;
  if(confirmDelete(form)){
    form.target="hiddenFrame";
    form.action="<%=request.getContextPath()%>/mx/identity/group/batchDelete";
	form.submit();
  }
}

function sortGroup(id, operate){  
  //GroupAjaxService.sort(id, operate, {callback:function (){reloadPage();}});
  //alert('groupId='+id+'&operate='+operate);
    	jQuery.ajax({
			type: "POST",
			url: '<%=request.getContextPath()%>/mx/identity/group/sort?groupId='+id+'&operate='+operate,
			dataType:  'json',
				error: function(data){
					alert('服务器处理错误！');
				},
				success: function(data){
					   location.reload();
				 }
		});
}
function query(){
  var form = document.all.command;
    //form.target="hiddenFrame";
    form.action="<%=request.getContextPath()%>/mx/identity/group/showList";
	form.submit();
}
</script>
</head>

<body style="padding-top:1px;padding-left:2px;padding-right:20px;">
<html:form method="post" target="_self"> 
<input name="page_no" type="hidden" value="<%=pager.getCurrentPageNo()%>">
<input type="hidden" name="id" value="">
<div class="nav-title"><span class="Title">群组管理</span>&gt;&gt;群组列表</div>
&nbsp;&nbsp;&nbsp;&nbsp;群组名称：<input type="text" name="nameLike" id="nameLike" size="20" >
&nbsp;&nbsp;描述：<input type="text" name="descLike" id="descLike" size="20" >
<input name="btn_query" type="button" value="查询" class="button" onClick="javascript:query();"> 

<table width="100%" border="0" cellspacing="1" cellpadding="0" class="list-box">
  <tr class="list-title"> 
    <td width="3%" align="center"> 
	<input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this);checkOperation(this.form)">    
	</td>
    <td width="12%" align="center">序号</td>
    <td width="24%" align="center">群组名称</td>
	<td width="24%" align="center">群组代码</td>
    <td width="33%" align="center">描述</td>
    <td width="12%" align="center">排序</td>
  </tr>
  <%
int i=0;
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    Group bean=(Group)iter.next();
%>
  <tr <%=i%2==0?"":"class='list-back'"%>> 
    <td class="td-cb"> 
	<input type="checkbox" name="id" value="<%=bean.getGroupId()%>" onClick="checkOperation(this.form)">    
	</td>
    <td class="td-no"><%=((pager.getCurrentPageNo()-1)*pageSize + i+1)%></td>
    <td class="td-text"><%=bean.getName()%>&nbsp; </td>
	<td class="td-text"><%=bean.getCode() != null ? bean.getCode() : ""%>&nbsp; </td>
    <td class="td-text"><%=bean.getDesc() != null ? bean.getDesc() : ""%>&nbsp;</td>
    <td class="td-no">
	<a href="javascript:sortGroup('<%=bean.getGroupId()%>', 0);" title="上移"><img src="<%=context%>/images/up.gif" border="0" height="13" width="13"></a> 
	<a href="javascript:sortGroup('<%=bean.getGroupId()%>', 1);" title="下移"><img src="<%=context%>/images/down.gif" border="0" height="13" width="13"></a>
	</td>
  </tr>
  <%
    i++;
  }
}
for(; i<pageSize; i++){
%>
  <tr <%=i%2==0?"":"class='list-back'"%>> 
    <td height="20">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp; </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
  </tr>
<%
}
%>
</table>
<br>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td width="50%"> 
	  <input name="btn_add" type="button" value="增加" class="button" onClick="javascript:add();"> 
      <input name="btn_del" type="button" value="删除" class="button" onClick="javascript:del();" disabled>
      <input name="btn_modify" type="button" value="修改" class="button" onClick="javascript:modify(this.form);" disabled>
	  <input name="btn_users" type="button" value="用户" class="button" onClick="javascript:users(this.form);" disabled>
	  <input name="btn_leader_users" type="button" value="领导" class="button" onClick="javascript:userLeaders(this.form);" disabled>
	</td>
    <td width="50%"> 
      <%
        String params = WebUtil.getQueryString(request);
		////System.out.println("params:"+java.net.URLEncoder.encode(params));
      %>
      <jsp:include page="/WEB-INF/views/inc/show_page.jsp" flush="true"> 
              <jsp:param name="total" value="<%=pager.getTotalRecordCount()%>"/>
              <jsp:param name="page_count" value="<%=pager.getTotalPageCount()%>"/>
              <jsp:param name="page_size" value="<%=pageSize%>"/>
              <jsp:param name="page_no" value="<%=pager.getCurrentPageNo()%>"/>
              <jsp:param name="url" value=""/>
			  <jsp:param name="params" value="<%=java.net.URLEncoder.encode(params)%>"/> 
	  </jsp:include> 
	</td>
  </tr>
</table>
</html:form>
<script language="javascript">
attachFrame();
</script> 
</body>
</html>
