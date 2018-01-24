<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
int pageSize=Constants.PAGE_SIZE;
int parent=ParamUtil.getIntParameter(request, "parent", 0);
com.glaf.core.util.PageResult pager=(com.glaf.core.util.PageResult)request.getAttribute("pager");
List list = pager.getResults();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>模块管理</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/layer/skin/layer.css" /><base>
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
	//alert(num);
    document.all.btn_del.disabled=false;
	if(num==1){
	  document.all.btn_modify.disabled=false;
	  document.all.btn_function.disabled=false;
	  document.all.btn_perm.disabled=false;
	}else{
	  document.all.btn_modify.disabled=true;
	  document.all.btn_function.disabled=true;
	  document.all.btn_perm.disabled=true;
	}
  }else{
    document.all.btn_del.disabled=true;
	document.all.btn_modify.disabled=true;
	document.all.btn_function.disabled=true;
	//document.all.btn_perm.disabled=true;
  }
}

function add(){
  var link = "<%=request.getContextPath()%>/mx/sys/application/prepareAdd?parent="+<%=parent%>;
  var width=580;
  var height=450;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "添加模块", lock: false, scrollbars:"no" }, false);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "添加模块",
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
  var link = "<%=request.getContextPath()%>/mx/sys/application/prepareModify?id="+id;
  var width=580;
  var height=460;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "修改模块", lock: false, scrollbars:"no" }, false);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "修改模块",
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
  var link = "<%=request.getContextPath()%>/mx/sys/application/prepareModify?id="+id;
  var width=580;
  var height=460;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "修改模块", lock: false, scrollbars:"no" }, false);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "修改模块",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function func(form){
  var id =0;
  for (var i=0;i<form.id.length;i++) {
    var e = form.id[i];
    if (e.checked){
	  id=e.value;
	}     
  }
  var link = "<%=request.getContextPath()%>/mx/sys/function/showFuncList?parent="+id;
  var width=680;
  var height=330;
  var scroll="yes";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "模块功能", lock: false, scrollbars:"no" }, false);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "模块功能",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function editFun(id){
  var link = "<%=request.getContextPath()%>/mx/sys/function/showFuncList?parent="+id;
  var width=680;
  var height=330;
  var scroll="yes";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "模块功能", lock: false, scrollbars:"no" }, false);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "模块功能",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function perm(form){
  var id = 0;
  for (var i=0;i<form.id.length;i++) {
    var e = form.id[i];
    if (e.checked){
	  id=e.value;
	}     
  }
  var link = "${contextPath}/mx/sys/application/permission?id="+id;
  var width=680;
  var height=430;
  var scroll="yes";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "模块权限", lock: false, scrollbars:"no" }, false);
    jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "模块权限",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function editPerm(id){
  var link = "${contextPath}/mx/sys/application/permission?id="+id;
  var width=680;
  var height=430;
  var scroll="yes";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "模块权限", lock: false, scrollbars:"no" }, false);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "模块权限",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function sortMenu(){
  var link = "${contextPath}/mx/system/application/showSort?nodeId=<%=parent%>";
  var width=680;
  var height=430;
  var scroll="yes";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "菜单排序", lock: false, scrollbars:"no" }, false);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "菜单排序",
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
  //var form = document.getElementById("iForm");
  if(confirm("数据删除后不能恢复，确定删除吗？")){
    //form.target="hiddenFrame";
    //form.action="${contextPath}/mx/sys/application/batchDelete";
	//form.submit();
	var link = "<%=request.getContextPath()%>/rs/sys/application/batchDelete";
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
  //SysApplicationAjaxService.sort(<%=parent%>, id, operate, {callback:function (){reloadPage();}});
      	jQuery.ajax({
			type: "POST",
			url: '<%=request.getContextPath()%>/mx/sys/application/sort?parent=<%=parent%>&id='+id+'&operate='+operate,
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
	document.getElementById("parent").value="";
	//document.getElementById("nameLike_encode").value="";
	//document.getElementById("actorIdLike_encode").value="";
	iForm.method="post";
    iForm.action="<%=request.getContextPath()%>/mx/sys/application/showList?_rq_=1";
	iForm.submit();
}

function importJson(){
    var link='<%=request.getContextPath()%>/mx/sys/application/showImport?parentId=<%=parent%>';
    jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "导入模块信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['650px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function exportJson(){
	window.open('<%=request.getContextPath()%>/mx/sys/application/exportJson?parentId=<%=parent%>');
}

</script>
</head>
<body style="padding-top:2px;padding-left:2px;padding-right:10px;">
<div class="nav-title"><label class="Title">模块管理</label><label class="Title">&gt;&gt;模块列表</label></div>
<html:form id="iForm" name="iForm" method="post">
 <div class="form-group">
 <label for="nameLike">模块名称：</label>
<input type="text" name="nameLike" id="nameLike" size="20" value="${nameLike}" class="form-control" style="width: 10%;display: inline-block;"/><input type="hidden" id="nameLike_encode" name="nameLike_encode" value="${nameLike_encode}"/>

 
 <label for="codeLike">模块代码：</label>
<input type="text" name="codeLike" id="codeLike" size="20" value="${codeLike}" class="form-control" style="width: 10%;display: inline-block;"/>
<input type="hidden" id="codeLike_encode" name="codeLike_encode" value="${codeLike_encode}"/>
<input name="btn_query" type="button" value="查询" class="button btn-success" onClick="javascript:queryForm();"/> 
<input name="page_no" type="hidden" value="<%=pager.getCurrentPageNo()%>"/>
<input id="parent" name="parent" type="hidden" value="<%=parent%>"/>
<input type="hidden" name="id" value="0"/>
 </div>


<table width="100%" border="0" cellspacing="1" cellpadding="0" class="table table-bordered ">
 <thead>
  <tr style='background-color:#e6f2f8;font-size:15px;'> 
    <th style="text-align:center;width: 5%"> 
	<input type="checkbox" name="chkall" value="checkbox" onclick="checkAll(this.form, this);checkOperation(this.form)">    
	</th>
    <th style="text-align:center;width: 5%">序号</th>
	<th style="text-align:center;width: 8%">编号</th>
    <th style="text-align:center;width: 12%">名称</th>
    <th style="text-align:center;width: 15%">描述</th>
    <th style="text-align:center;width: 20%">链接</th>
    <th style="text-align:center;width: 8%;">是否有效</th>
    <!-- <td width="5%" align="center">排序</td> -->
	<th style="text-align:center;width: 25%">功能键</th>
  </tr>
  </thead>
  <%
	int i=0;
	if(list!=null){
	  Iterator iter=list.iterator();   
	  while(iter.hasNext()){
		SysApplication bean=(SysApplication)iter.next();	
%>
<tbody>
  <tr align="center" class="td-cb"> 
    <td align="center" class="td-cb"> 
	<input type="checkbox" name="id" value="<%=bean.getId()%>" onClick="checkOperation(this.form)">    
	</td>
    <td align="center" class="td-no" style="color:#003f59;font-size:13px;"><%=((pager.getCurrentPageNo()-1)*pageSize + i+1)%></td>
	<td class="td-text"  style="color:#003f59;font-size:13px;"><%=bean.getId()%>&nbsp;</td>
    <td class="td-text" style="color:#003f59;font-size:13px;"><%=bean.getName()%>&nbsp;</td>
    <td class="td-text" style="color:#003f59;font-size:13px;"><%=bean.getDesc() !=null ? bean.getDesc() : ""%>&nbsp;</td>
    <td class="td-text" style="color:#003f59;font-size:13px;"><%=bean.getUrl() != null ? bean.getUrl() : ""%>&nbsp;</td>
    <td class="td-no">
	<%=bean.getLocked()==0?"<span style='color:blue;font-size:13px;'>是</span>":"<span style='color:red;font-size:13px;'>否</span>"%>&nbsp;
	</td>
    <!-- <td class="td-no"><a href="javascript:sort(<%=bean.getId()%>, 1);" title="上移"><img src="<%=context%>/images/up.gif" border="0" height="13" width="13"></a> <a href="javascript:sort(<%=bean.getId()%>, 0);" title="下移"><img src="<%=context%>/images/down.gif" border="0" height="13" width="13"></a>
	</td> -->
	<td>&nbsp;
	   <a href="#" style="color:#003f59;font-size:13px;" onclick="javascript:editRow(<%=bean.getId()%>);">修改</a>&nbsp;
	   <a href="#" style="color:#003f59;font-size:13px;" onclick="javascript:editFun(<%=bean.getId()%>);">功能</a>&nbsp;
	   <a href="#" style="color:#003f59;font-size:13px;" onclick="javascript:editPerm(<%=bean.getId()%>);">权限</a>&nbsp;
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
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
	<!-- <td>&nbsp;</td> -->
	<td>&nbsp;</td>
  </tr>
    </tbody>
<%
}
%>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" background="<%=request.getContextPath()%>/images/conetent_2_bg2.jpg">
  <tr> 
    <td height="5" background="<%=request.getContextPath()%>/images/content_2_bg2.jpg"></td>
  </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td width="65%" align="left"> <input name="btn_add" type="button" value="增加" class="btn btn-primary" onClick="javascript:add();"> 
      <input name="btn_del" type="button" value="删除" class="btn btn-danger" onClick="javascript:del();" disabled>
      <input name="btn_modify" type="button" value="修改" class="btn btn-info" onClick="javascript:modify(this.form);" disabled>
      <!-- <input name="btn_function" type="button" value="功能设置" class="button" onClick="javascript:func(this.form);" disabled> -->
	  <!-- <input name="btn_perm" type="button" value="权限设置" class="button" onClick="javascript:perm(this.form);" disabled> -->
	  <input name="btn_menu" type="button" value="同级菜单排序" style="background:#6495ED;color:white" class="btn " onClick="javascript:sortMenu(this.form);">
	  <input name="btn_export" type="button" value="导出" style="background:green;color:white" class="btn" onClick="javascript:exportJson(this.form);" >
	  <input name="btn_import" type="button" value="导入" style="background:#8A2BE2;color:white" class="btn" onClick="javascript:importJson(this.form);" >
	</td>
    <td width="35%" align="left"> 
      <%
      String params = WebUtil.getQueryString(request);
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
