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
List list = pager.getResults();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/layer/skin/layer.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/layer/layer.min.js"></script> 
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>

<script language="javascript">
var num=0;
function checkOperation(form){
  num = getCheckedBoxNum(form,"id");
  if(num>0){
    document.all.btn_del.disabled=false;
	if(num==1){
	  document.all.btn_modify.disabled=false;
	  document.all.btn_ru.disabled=false;
	  document.all.btn_rm.disabled=false;
	}else{
	  document.all.btn_modify.disabled=true;
	  document.all.btn_ru.disabled=true;
	  document.all.btn_rm.disabled=true;
	}
  }else{
    document.all.btn_del.disabled=true;
	document.all.btn_modify.disabled=true;
	document.all.btn_ru.disabled=true;
	document.all.btn_rm.disabled=true;
  }
}
function add(){
  var url="post/prepareAdd";
  var link = "<%=request.getContextPath()%>/mx/sys/"+url;
  var width=520;
  var height=320;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "添加岗位", lock: false, scrollbars:"no" }, false);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "添加岗位",
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
  var url="post/prepareModify?id="+id;
  var link = "<%=request.getContextPath()%>/mx/sys/"+url;
  var width=520;
  var height=320;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "修改岗位", scrollbars:"no" , lock: false });
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "修改岗位",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function editRow(id){
  var url="post/prepareModify?id="+id;
  var link = "<%=request.getContextPath()%>/mx/sys/"+url;
  var width=520;
  var height=320;
  var scroll="no";
  //openWindow(url, width, height, scroll);
 // art.dialog.open(link, { height: height, width: width, title: "修改岗位", scrollbars:"no" , lock: false });
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "修改岗位",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function postUsers(form){
  var id =0;
  for (var i=0;i<form.id.length;i++) {
    var e = form.id[i];
    if (e.checked){
	  id=e.value;
	}     
  }
  var link = "<%=request.getContextPath()%>/mx/sys/post/postUsers?id="+id;
  var width=520;
  var height=425;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "岗位用户", scrollbars:"no" , lock: false });
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "岗位用户",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function editPostUsers(id){
  var link = "<%=request.getContextPath()%>/mx/sys/post/postUsers?id="+id;
  var width=520;
  var height=425;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "岗位用户", scrollbars:"no" , lock: false });
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "岗位用户",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}


function deleteRecordX(){
  if(confirm("数据删除后不能恢复，确定删除吗？")){
	//var form = document.getElementById("iForm");
    //form.target="hiddenFrame";
    //form.action="<%=request.getContextPath()%>/mx/sys/post/batchDelete";
	//form.submit();
	var link = "<%=request.getContextPath()%>/rs/sys/post/batchDelete";
	var params = jQuery("#iForm").formSerialize();
    jQuery.ajax({
				   type: "POST",
				   url: link,
				   dataType:  'json',
				   data: params,
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

function sort(id, operate){  
  //SysRoleAjaxService.sort(id, operate, {callback:function (){reloadPage();}});
    	jQuery.ajax({
			type: "POST",
			url: '<%=request.getContextPath()%>/mx/sys/post/sort?id='+id+'&operate='+operate,
			dataType:  'json',
				error: function(data){
					alert('服务器处理错误！');
				},
				success: function(data){
					   location.reload();
				 }
		});
}

function queryForm(){
    var iForm = document.getElementById("iForm");
	//document.getElementById("nameLike_encode").value="";
	//document.getElementById("codeLike_encode").value="";
	iForm.method="post";
    iForm.action="<%=request.getContextPath()%>/mx/sys/post/showList?_rq_=1";
	iForm.submit();
}

</script>
</head>

<body style="padding-top:10px;padding-left:2px;padding-right:20px;">
<html:form id="iForm" name="iForm" method="post"> 
<input name="page_no" type="hidden" value="<%=pager.getCurrentPageNo()%>">
<input type="hidden" name="id" value="0">
<div class="nav-title"><span class="Title">岗位管理</span>&gt;&gt;岗位列表</div>
&nbsp;&nbsp;&nbsp;&nbsp;岗位名称：<input type="text" name="nameLike" id="nameLike" size="20" value="${nameLike}" class="input"><input type="hidden" id="nameLike_encode" name="nameLike_encode" value="${nameLike_encode}">
&nbsp;&nbsp;岗位代码：<input type="text" name="codeLike" id="codeLike" size="20" value="${codeLike}" class="input">
<input type="hidden" id="codeLike_encode" name="codeLike_encode" value="${codeLike_encode}">
<input name="btn_query" type="button" value="查询" class="button" onClick="javascript:queryForm();"> 
<table width="100%" border="0" cellspacing="1" cellpadding="0" class="list-box">
  <tr class="list-title"> 
    <td width="5%" align="center"> <input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this);checkOperation(this.form)">    </td>
    <td width="5%" align="center">序号</td>
    <td width="20%" align="center">岗位名称</td>
    <td width="20%" align="center">代码</td>
    <td width="25%" align="center">描述</td>
    <!-- <td width="10%" align="center">排序</td> -->
	<td width="15%" align="center">功能键</td>
  </tr>
  <%
int i=0;
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    Post bean=(Post)iter.next();
%>
  <tr <%=i%2==0?"":"class='list-back'"%>> 
    <td class="td-cb"> <input type="checkbox" name="id" value="<%=bean.getId()%>" onClick="checkOperation(this.form)">    </td>
    <td class="td-no"><%=((pager.getCurrentPageNo()-1)*pageSize + i+1)%></td>
    <td class="td-text"><%=bean.getName()%>&nbsp; </td>
    <td class="td-text"><%=bean.getCode() != null ? bean.getCode() : "" %></td>
    <td class="td-text"><%=bean.getContent() != null ? bean.getContent() : ""%>&nbsp;</td>
    <!-- <td class="td-no"><a href="javascript:sort(<%=bean.getId()%>, 0);" title="上移"><img src="<%=context%>/images/up.gif" border="0" height="13" width="13"></a> <a href="javascript:sort(<%=bean.getId()%>, 1);" title="下移"><img src="<%=context%>/images/down.gif" border="0" height="13" width="13"></a></td> -->
	<td align="center">&nbsp;
	<a href="#" onclick="javascript:editRow(<%=bean.getId()%>);">修改</a>&nbsp;
	<a href="#" onclick="javascript:editPostUsers(<%=bean.getId()%>);">用户</a>&nbsp;
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
	<!-- <td>&nbsp;</td> -->
  </tr>
<%
}
%>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td width="50%"> 
	  <input name="btn_add" type="button" value="增加" class="button" onClick="javascript:add();"> 
      <input name="btn_del" type="button" value="删除" class="button" onClick="javascript:deleteRecordX();" disabled>
      <input name="btn_modify" type="button" value="修改" class="button" onClick="javascript:modify(this.form);" disabled>
	  <input name="btn_ru" type="button" value="岗位用户" class="button" onClick="javascript:postUsers(this.form);" disabled>
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
//attachFrame();
</script> 
</body>
</html>
