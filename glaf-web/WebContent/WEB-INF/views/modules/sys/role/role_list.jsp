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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/bootstrap/css/bootstrap.min.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/layer/layer.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/bootstrap/js/bootstrap.min.js"></script>
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
  var url="role/prepareAdd";
  var link = "<%=request.getContextPath()%>/mx/sys/"+url;
  var width=520;
  var height=320;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "添加角色", lock: false, scrollbars:"no" }, false);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "添加角色",
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
  var url="role/prepareModify?id="+id;
  var link = "<%=request.getContextPath()%>/mx/sys/"+url;
  var width=520;
  var height=320;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "修改角色", scrollbars:"no" , lock: false });
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "修改角色",
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
  var url="role/prepareModify?id="+id;
  var link = "<%=request.getContextPath()%>/mx/sys/"+url;
  var width=520;
  var height=320;
  var scroll="no";
  //openWindow(url, width, height, scroll);
 // art.dialog.open(link, { height: height, width: width, title: "修改角色", scrollbars:"no" , lock: false });
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "修改角色",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function roleUsers(form){
  var id =0;
  for (var i=0;i<form.id.length;i++) {
    var e = form.id[i];
    if (e.checked){
	  id=e.value;
	}     
  }
  var link = "<%=request.getContextPath()%>/mx/sys/role/roleUsers?id="+id;
  var width=520;
  var height=425;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "角色用户", scrollbars:"no" , lock: false });
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "角色用户",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function editRoleUsers(id){
  var link = "<%=request.getContextPath()%>/mx/sys/role/roleUsers?id="+id;
  var width=520;
  var height=425;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "角色用户", scrollbars:"no" , lock: false });
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "角色用户",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}


function editAuthorityUsers(id){
  var link = "<%=request.getContextPath()%>/mx/sys/role/authorityUsers?id="+id;
  var width=520;
  var height=425;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "角色用户", scrollbars:"no" , lock: false });
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "授权用户",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function roleMenus(form){
  var id =0;
  for (var i=0;i<form.id.length;i++) {
    var e = form.id[i];
    if (e.checked){
	  id=e.value;
	}     
  }
  var link = "<%=request.getContextPath()%>/mx/sys/role/roleMenus?roleId="+id;
  var width=520;
  var height=425;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "角色菜单", scrollbars:"no" , lock: false });
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "角色菜单",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function editRoleMenus(id){
  var link = "<%=request.getContextPath()%>/mx/sys/role/roleMenus?roleId="+id;
  var width=520;
  var height=425;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "角色菜单", scrollbars:"no" , lock: false });
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "角色菜单",
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
    //form.action="<%=request.getContextPath()%>/mx/sys/role/batchDelete";
	//form.submit();
	var link = "<%=request.getContextPath()%>/rs/sys/role/batchDelete";
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
			url: '<%=request.getContextPath()%>/mx/sys/role/sort?id='+id+'&operate='+operate,
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
    iForm.action="<%=request.getContextPath()%>/mx/sys/role/showList?_rq_=1";
	iForm.submit();
}

</script>
</head>

<body style="padding-top:10px;padding-left:2px;padding-right:20px;">
<html:form id="iForm" name="iForm" method="post"> 
<input name="page_no" type="hidden" value="<%=pager.getCurrentPageNo()%>">
<input type="hidden" name="id" value="0">
<div class="nav-title"><span class="Title" style="font-size: 13px;font-weight: bold">角色管理</span></span><span class="Title" style="font-size: 13px;font-weight: bold">&gt;&gt;角色列表</span></div>
<div class="form-group">
<label for="nameLike">角色名称:</label>
<input type="text" name="nameLike" id="nameLike" size="20" value="${nameLike}"  class="form-control" style="width: 10%;display: inline-block;"><input type="hidden" id="nameLike_encode" name="nameLike_encode" value="${nameLike_encode}">
<label for="nameLike">角色代码:</label>
<input type="text" name="codeLike" id="codeLike" size="20" value="${codeLike}" class="form-control" style="width: 10%;display: inline-block;">
<input type="hidden" id="codeLike_encode" name="codeLike_encode" value="${codeLike_encode}">
<input name="btn_query" type="button" value="查询" class="button btn-success" onClick="javascript:queryForm();"/> 
</div>

<table width="100%" border="0" cellspacing="1" cellpadding="0" class="table table-bordered">
  <tr  style='background-color:#e6f2f8;font-size:15px;'> 
    <th style="text-align:center;width: 5%"> <input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this);checkOperation(this.form)">    </td>
    <th style="text-align:center;width: 5%">序号</th>
    <th style="text-align:center;width: 20%">角色名称</th>
    <th style="text-align:center;width: 15%">代码</th>
    <th style="text-align:center;width: 25%">描述</th>
    <!-- <td width="10%" align="center">排序</td> -->
	<th style="text-align:center;width: 20%">功能键</th>
  </tr>
  <%
int i=0;
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    SysRole bean=(SysRole)iter.next();
%>
  <tr <%=i%2==0?"":"style='background-color:#e6f2f8;font-size:15px;'"%>> 
    <td class="td-cb"> <input type="checkbox" name="id" value="<%=bean.getId()%>" onClick="checkOperation(this.form)">    </td>
    <td class="td-no"><%=((pager.getCurrentPageNo()-1)*pageSize + i+1)%></td>
    <td class="td-text"><%=bean.getName()%>&nbsp; </td>
    <td class="td-text"><%=bean.getCode() != null ? bean.getCode() : "" %></td>
    <td class="td-text"><%=bean.getContent() != null ? bean.getContent() : ""%>&nbsp;</td>
    <!-- <td class="td-no"><a href="javascript:sort(<%=bean.getId()%>, 0);" title="上移"><img src="<%=context%>/images/up.gif" border="0" height="13" width="13"></a> <a href="javascript:sort(<%=bean.getId()%>, 1);" title="下移"><img src="<%=context%>/images/down.gif" border="0" height="13" width="13"></a></td> -->
	<td align="center">&nbsp;
	<a href="#" onclick="javascript:editRow(<%=bean.getId()%>);">修改</a>&nbsp;
	<a href="#" onclick="javascript:editRoleUsers(<%=bean.getId()%>);" title="设置角色用户">用户</a>&nbsp;
	<a href="#" onclick="javascript:editAuthorityUsers(<%=bean.getId()%>);" title="将角色授权用户">下放授权</a>&nbsp;
	<a href="#" onclick="javascript:editRoleMenus(<%=bean.getId()%>);">菜单</a>&nbsp;
	</td>
  </tr>
  <%
    i++;
  }
}
for(; i<pageSize; i++){
%>
  <tr <%=i%2==0?"":"style='background-color:#e6f2f8;font-size:15px;'"%>> 
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
<br>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td width="50%"> 
	  <input name="btn_add" type="button" value="增加" class="button btn-primary" onClick="javascript:add();"> 
      <input name="btn_del" type="button" value="删除" class="button btn-danger" onClick="javascript:deleteRecordX();" disabled>
      <input name="btn_modify" type="button" value="修改" class="button btn-info" onClick="javascript:modify(this.form);" disabled>
	  <input name="btn_ru" type="button" value="角色用户" style="background:#BCD2EE;color:white" class="button" onClick="javascript:roleUsers(this.form);" disabled>
	  <input name="btn_rm" type="button" value="角色菜单" style="background:#8E8E38;color:white" class="button" onClick="javascript:roleMenus(this.form);" disabled>
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
