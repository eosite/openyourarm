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
	  document.all.btn_user.disabled=false;
	  //document.all.btn_role.disabled=false;
	}else{
	  document.all.btn_modify.disabled=true;
	  document.all.btn_user.disabled=true;
	  //document.all.btn_role.disabled=true;
	}
  }else{
	  document.all.btn_modify.disabled=true;
	  document.all.btn_user.disabled=true;
	  document.all.btn_del.disabled=true;
	  //document.all.btn_role.disabled=true;
  }
}

function add(){
  var link = "<%=request.getContextPath()%>/mx/sys/department/prepareAdd?parent="+<%=parent%>;
  var width=530;
  var height=410;
  var scroll="no";  
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "添加部门", scrollbars:"no" , lock: false });
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "添加部门",
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
  var link = "<%=request.getContextPath()%>/mx/sys/department/prepareModify?id="+id;
  var width=530;
  var height=425;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "修改部门", scrollbars:"no" , lock: false });
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "修改部门",
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
  var link = "<%=request.getContextPath()%>/mx/sys/department/prepareModify?id="+id;
  var width=530;
  var height=425;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "修改部门", scrollbars:"no" , lock: false });
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "修改部门",
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
  //var form = document.getElementById("iForm");
  if(confirm("数据删除后不能恢复，确定删除吗？")){
    //form.target="hiddenFrame";
    //form.action="${contextPath}/mx/sys/department/batchDelete";
	//form.submit();
	var link = "<%=request.getContextPath()%>/rs/sys/department/batchDelete";
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
  //SysDepartmentAjaxService.sort(<%=parent%>, id, operate, {callback:function (){reloadPage();}});
       jQuery.ajax({
			type: "POST",
			url: '<%=request.getContextPath()%>/mx/sys/department/sort?parent=<%=parent%>&id='+id+'&operate='+operate,
			dataType:  'json',
				error: function(data){
					alert('服务器处理错误！');
				},
				success: function(data){
					   location.reload();
				 }
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
  var link = "<%=request.getContextPath()%>/mx/sys/user/showList?parent="+id;
  var width=880;
  var height=380;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "部门用户", scrollbars:"no" , lock: false });
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "部门用户",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['980px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function sortMenu(){
  var link = "<%=request.getContextPath()%>/mx/sys/tree/showSort?nodeId=<%=parent%>";
  var width=680;
  var height=430;
  var scroll="yes";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "分类排序", lock: false, scrollbars:"no" }, false);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "部门排序",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['650px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function editDeptUsers(id){
  var link = "<%=request.getContextPath()%>/mx/sys/user/showList?parent="+id;
  var width=880;
  var height=380;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "部门用户", scrollbars:"no" , lock: false });
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "部门用户",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['980px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
} 

function queryForm(){
    var iForm = document.getElementById("iForm");
	document.getElementById("parent").value="";
	//document.getElementById("nameLike_encode").value="";
	//document.getElementById("actorIdLike_encode").value="";
	iForm.method="post";
    iForm.action="<%=request.getContextPath()%>/mx/sys/department/showList?_rq_=1";
	iForm.submit();
}

function doImport(){
  var link = "<%=request.getContextPath()%>/mx/sys/department/showImport";
  var width=880;
  var height=380;
  var scroll="no";
  //openWindow(url, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "部门用户", scrollbars:"no" , lock: false });
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "部门导入",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['980px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

</script>
</head>

<body style="padding-top:10px;padding-left:2px;padding-right:20px;">
<div class="nav-title"><span class="Title" style="font-size: 13px;font-weight: bold">部门管理</span>&gt;&gt;
<%
List nav = (List)request.getAttribute("nav");
if(nav != null){
Iterator navIter = nav.iterator();
while(navIter.hasNext()){
  SysDepartment bean = (SysDepartment)navIter.next();
%>  
  <a href="department/showList?parent=<%=bean.getNode().getId()%>"><%=bean.getName()%></a>&gt;&gt; 
<%
}
}
%>
</div>
<html:form id="iForm" name="iForm" action="${contextPath}/mx/sys/department/batchDelete" method="post" target="_self">
<div class="form-group">
<label for="nameLike">部门名称:</label>
<input type="text" name="nameLike" id="nameLike" size="20" value="${nameLike}" class="form-control" style="width: 10%;display: inline-block;"><input type="hidden" id="nameLike_encode" name="nameLike_encode" value="${nameLike_encode}">
<label for="nameLike">部门代码:</label>
<input type="text" name="codeLike" id="codeLike" size="20" value="${codeLike}" class="form-control" style="width: 10%;display: inline-block;">
<input type="hidden" id="codeLike_encode" name="codeLike_encode" value="${codeLike_encode}">
<input name="btn_query" type="button" value="查询" class="button btn-success" onClick="javascript:queryForm();"/> 
<input name="page_no" type="hidden" value="<%=pager.getCurrentPageNo()%>">
<input id="parent" name="parent" type="hidden" value="<%=parent%>">
<input type="hidden" name="id" value="0">
</div>
<table width="100%" border="0" cellspacing="1" cellpadding="0" class="table table-bordered">
<thead>
  <tr style='background-color:#e6f2f8;font-size:15px;'> 
    <th style="text-align:center;width: 5%"> 
	  <input type="checkbox" name="chkall" value="checkbox" onclick="checkAll(this.form, this);checkOperation(this.form)">    
	</th>
    <th style="text-align:center;width: 5%">序号</th>
    <th style="text-align:center;width: 15%">名称</th>
    <th style="text-align:center;width: 5%">状态</th>
    <th style="text-align:center;width: 10%">代码</th>
    <th style="text-align:center;width: 10%">编码</th>
    <th style="text-align:center;width: 10%">部门区分</th>
    <!-- <td align="center">排序</td> -->
	<th style="text-align:center;width: 20%">功能键</th>
    </tr>
    </thead>
  <%
int i=0;
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    SysDepartment bean=(SysDepartment)iter.next();	
%>
<tbody>
  <tr <%=i%2==0?"":"style='background-color:#e6f2f8;font-size:15px;'"%>> 
    <td class="td-cb"> 
	<input type="checkbox" name="id" value="<%=bean.getId()%>" onClick="checkOperation(this.form)">    
	</td>
    <td class="td-no"><%=((pager.getCurrentPageNo()-1)*pageSize + i+1)%></td>
    <td class="td-text">
	<a href="<%=request.getContextPath()%>/mx/sys/department/showList?id=<%=bean.getId()%>&parent=<%=bean.getNodeId()%>"><%=bean.getName()%></a>
	</td>
    <td class="td-no"><%=bean.getStatus()==0?"有效":"无效"%>&nbsp;</td>
    <td class="td-no"><%=bean.getCode() != null ? bean.getCode() : ""%>&nbsp;</td>
    <td class="td-no"><%=bean.getNo() != null ? bean.getNo() : ""%>&nbsp;</td>
    <td class="td-no"><%=bean.getCode2() != null ? bean.getCode2() : ""%>&nbsp;</td>
    <!-- <td class="td-no">
	<a href="javascript:sort(<%=bean.getId()%>, 0);" title="上移"><img src="<%=context%>/images/up.gif" border="0" height="13" width="13"></a> <a href="javascript:sort(<%=bean.getId()%>, 1);" title="下移"><img src="<%=context%>/images/down.gif" border="0" height="13" width="13"></a>
	</td> -->
	<td>&nbsp;
	   <a href="#" onclick="javascript:editRow(<%=bean.getId()%>);">修改</a>&nbsp;
	   <a href="#" onclick="javascript:editDeptUsers(<%=bean.getId()%>);">部门用户</a>&nbsp;
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
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
	<td>&nbsp;</td>	
	<!-- <td>&nbsp;</td> -->
	<td>&nbsp;</td>	
  </tr>
<%
}
%>
</tbody>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" background="<%=request.getContextPath()%>/images/conetent_2_bg2.jpg">
  <tr> 
    <td height="5" background="<%=request.getContextPath()%>/images/content_2_bg2.jpg"></td>
  </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td width="50%"> 
	  <input name="btn_add" type="button" value="增加" class="button btn-primary" onClick="javascript:add();"> 
      <input name="btn_modify" type="button" value="修改" class="button btn-info" onClick="javascript:modify(this.form);" disabled>
	  <input name="btn_del" type="button" value="删除" class="button btn-danger" onClick="javascript:deleteRecordX();" disabled>
      <input name="btn_user" type="button" value="部门用户" style="background:#BCD2EE;color:white" class="button" onClick="javascript:users(this.form);" disabled>
	  <input name="btn_perm" type="button" value="同级排序" style="background:#9ACD32;color:white" class="button" onClick="javascript:sortMenu(this.form);">
	  <input name="btn_imp" type="button" value="部门导入" style="background:	#5D478B;color:white" class="button" onClick="javascript:doImport();" >
	</td>
    <td width="50%"> 
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
//attachFrame();
</script>
</body>
</html>
