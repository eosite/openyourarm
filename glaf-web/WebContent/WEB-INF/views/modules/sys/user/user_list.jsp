<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.core.util.DateUtils"%>
<%@ page import="com.glaf.core.util.RequestUtils"%>
<%
	String context = request.getContextPath();
	int pageSize=Constants.PAGE_SIZE;
	SysDepartment department = (SysDepartment)request.getAttribute("department");
	com.glaf.core.util.PageResult pager=(com.glaf.core.util.PageResult)request.getAttribute("pager");
	List list = pager.getResults();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/layer/skin/layer.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
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
    //document.all.btn_del.disabled=false;
	if(num==1){
	  document.all.btn_modify.disabled=false;
	  //document.all.btn_role.disabled=false;
	  document.all.btn_reset_pwd.disabled=false;
	  document.all.btn_agent.disabled=false;
	}else{
	  document.all.btn_modify.disabled=true;
	  document.all.btn_reset_pwd.disabled=true;
	  document.all.btn_agent.disabled=true;
	  //document.all.btn_role.disabled=true;
	}
  }else{
    //document.all.btn_del.disabled=true;
	document.all.btn_modify.disabled=true;
	document.all.btn_reset_pwd.disabled=true;
	document.all.btn_agent.disabled=true;
	//document.all.btn_role.disabled=true;
  }
}

function add(){
  var link = "<%=request.getContextPath()%>/mx/sys/user/prepareAdd?parent=<%=department != null ? department.getId() : 0%>";
  var width=480;
  var height=450;
  var scroll="no";
  //openWindow(link, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "添加用户", lock: true, scrollbars:"no" }, false);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "增加用户",
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
  var link = "<%=request.getContextPath()%>/mx/sys/user/prepareModify?id="+id;
  var width=480;
  var height=450;
  var scroll="no";
  //openWindow(link, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "修改用户", lock: true, scrollbars:"no" }, false);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "修改用户",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}


function agent(form){
  var id =0;
  for (var i=0;i<form.id.length;i++) {
    var e = form.id[i];
    if (e.checked){
	  id=e.value;
	}     
  }
  var link = "<%=request.getContextPath()%>/mx/identity/agent?assignFrom="+id;
  var width=480;
  var height=450;
  var scroll="no";
  //openWindow(link, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "修改用户", lock: true, scrollbars:"no" }, false);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "代理设置",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['880px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function resetPwd(form){
  var id =0;
  for (var i=0;i<form.id.length;i++) {
    var e = form.id[i];
    if (e.checked){
	  id=e.value;
	}     
  }
  var link = "<%=request.getContextPath()%>/mx/sys/user/prepareResetPwd?id="+id;
  var width=450;
  var height=50;
  var scroll="no";
  //openWindow(link, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "重置用户密码", lock: true, scrollbars:"no" }, false);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "重置用户密码",
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
    //form.action="<%=request.getContextPath()%>/mx/sys/user/batchDelete";
	//form.submit();
	var link = "<%=request.getContextPath()%>/rs/sys/user/batchDelete";
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
function roles(form){
  var id =0;
  for (var i=0;i<form.id.length;i++) {
    var e = form.id[i];
    if (e.checked){
	  id=e.value;
	}     
  }
  var link = "<%=request.getContextPath()%>/mx/sys/user/showRole?user_id="+id;
  var width=450;
  var height=350;
  var scroll="yes";
  //openWindow(link, width, height, scroll);
  //art.dialog.open(link, { height: height, width: width, title: "用户角色", lock: true, scrollbars:"no" }, false);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "用户角色",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function showImport(){
  var link = "<%=request.getContextPath()%>/mx/sys/user/showImport";
  var width=580;
  var height=350;
  var scroll="yes";
  //openWindow(link, width, height, scroll);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "用户导入",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function queryForm(){
    var iForm = document.getElementById("iForm");
	document.getElementById("parent").value="";
	//document.getElementById("nameLike_encode").value="";
	//document.getElementById("actorIdLike_encode").value="";
	iForm.method="post";
    iForm.action="<%=request.getContextPath()%>/mx/sys/user/showList?_rq_=1";
	iForm.submit();
}

function doImport(form){
    jQuery.ajax({
		   type: "POST",
		   url: '<%=request.getContextPath()%>/mx/sys/user/gnUserImport',
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
		   }
	});
}

function batchAdd(){
	  var link = "<%=request.getContextPath()%>/mx/sys/user/deptUsersTree?deptId=<%=department != null ? department.getId() : 0%>";
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

</script>
</head>

<body style="margin-left:5px;">
<div class="nav-title"><span class="Title" style="font-size: 13px;font-weight: bold">用户管理</span><span class="Title" style="font-size: 13px;font-weight: bold">&gt;&gt;</span>
<%
List nav = (List)request.getAttribute("nav");
if(nav != null){
Iterator navIter = nav.iterator();
while(navIter.hasNext()){
  SysDepartment bean = (SysDepartment)navIter.next();
%>  
  <%=bean.getName()%>
<%
}
}
%>
</span>
</div>
<html:form id="iForm" name="iForm" action="${contextPath}/mx/sys/user/batchDelete" method="post" target="_self">
<div class="form-group">
<label for="nameLike">姓名:</label>
<input type="text" name="nameLike" id="nameLike" size="20" value="${nameLike}" class="form-control" style="width: 20%;display: inline-block;"><input type="hidden" id="nameLike_encode" name="nameLike_encode" value="${nameLike_encode}">
<label for="nameLike">帐号：</label><input type="text" name="actorIdLike" id="actorIdLike" size="20" value="${actorIdLike}" class="form-control" style="width: 20%;display: inline-block;">
<input type="hidden" id="actorIdLike_encode" name="actorIdLike_encode" value="${actorIdLike_encode}">
<input name="btn_query" type="button" value="查询" class="button btn-success" onClick="javascript:queryForm();"> 
<input name="page_no" type="hidden" value="<%=pager.getCurrentPageNo()%>">
<input id="parent" name="parent" type="hidden" value="<%=department != null ? department.getId() : 0%>">
<input type="hidden" name="id" value="0">
</div>
<table width="100%" border="0" cellspacing="1" cellpadding="0" class="table table-bordered">
<thead>
  <tr style='background-color:#e6f2f8;font-size:15px;'> 
    <th style="text-align:center;width: 5%"> 
	<input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this);checkOperation(this.form)">    
	</th>
    <th style="text-align:center;width: 5%">序号</th>
    <th style="text-align:center;width: 8%" >帐号</th>
    <th style="text-align:center;width: 10%" >姓名</th>
    <th style="text-align:center;width: 15%" >部门</th>
    <th style="text-align:center;width: 8%" >是否有效</th>
    <th style="text-align:center;width: 12%" >创建日期</th>
    <th style="text-align:center;width: 12%" >上次登陆时间</th>
  </tr>
  </thead>
  <%
int i=0;
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    SysUser bean=(SysUser)iter.next();
	String roleName = "";
	 
%>
<tbody>
  <tr <%=i%2==0?"":"style='background-color:#e6f2f8;font-size:15px;'"%>> 
    <td width="5%" class="td-cb"> 
	<input type="checkbox" name="id" value="<%= RequestUtils.encodeString(bean.getActorId())%>" onClick="checkOperation(this.form)"> 
	</td>
    <td width="5%" class="td-no"><%=((pager.getCurrentPageNo()-1)*pageSize + i+1)%></td>
    <td width="8%" class="td-text"><%=bean.getAccount()%>&nbsp;</td>
    <td width="10%" class="td-text"><%=bean.getName()%>&nbsp;</td>
    <td width="15%" class="td-c"><%=bean.getDepartment() !=null ? bean.getDepartment().getName() :""%>&nbsp;</td>
    <!-- <td width="25%" class="td-text" title="<%=roleName%>"><%=roleName%></td> -->
    <td width="8%" class="td-no"><%=bean.getLocked()==0?"<span style='color:blue'>是</span>":"<span style='color:red'>否</span>"%>&nbsp;</td>
    <td width="12%" class="td-time">
	    <%=DateUtils.getDate(bean.getCreateTime())%>&nbsp;
	</td>
    <td width="12%" align="center" class="list">
	   <%=DateUtils.getDate(bean.getLastLoginTime())%>&nbsp;
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
    <td>&nbsp; </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp; </td>
    <!-- <td>&nbsp;</td> -->
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
<%
}
%>
<tbody>
</table>
<br>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td  width="50%"> 
	  <input name="btn_add" type="button" value="增加" class="button btn-primary" onClick="javascript:add();"> 
	  <input name="btn_add" type="button" value="批量修改" style="background:#53868B;color:white" class="button" onClick="javascript:batchAdd();"> 
      <!-- <input name="btn_del" type="button" value="删除" class="button" onClick="javascript:del();" disabled> -->
      <input name="btn_modify" type="button" value="修改" class="button btn-info" onClick="javascript:modify(this.form);" disabled>
	  <input name="btn_reset_pwd" type="button" value="重置密码" style="background:#BCD2EE;color:white" class="button" onClick="javascript:resetPwd(this.form);" disabled>
	  <input name="btn_agent" type="button" value="代理设置" style="background:#9ACD32;color:white" class="button" onClick="javascript:agent(this.form);" disabled>
	  <input name="btn_imp" type="button" value="导入" style="background:	#5D478B;color:white" class="button" onClick="javascript:showImport(this.form);" >
	  <c: test="${!empty serverEntity}">
	  <!-- <input name="btn_imp2" type="button" value="同步到云众联" class="button" onClick="javascript:doImport(this.form);" > -->
      </c:if>
	  <!-- <input name="btn_role" type="button" value="角色设置" class="button" onClick="javascript:roles(this.form);" disabled> -->
	</td>
    <td  width="54%"> 
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
