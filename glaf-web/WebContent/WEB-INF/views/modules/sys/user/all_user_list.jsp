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
<script type="text/javascript" src="<%=context%>/scripts/verify.js"></script>
<script type="text/javascript" src="<%=context%>/scripts/main.js"></script>
<script language="javascript">
var num=0;
function checkOperation(form){
  num = getCheckedBoxNum(form,"id");
  if(num>0){
    //document.all.btn_del.disabled=false;
	if(num==1){
	  document.all.btn_modify.disabled=false;
	  document.all.btn_role.disabled=false;
	  document.all.btn_reset_pwd.disabled=false;
	  document.all.btn_agent.disabled=false;
	  document.all.btn_enable.disabled=false;
	  document.all.btn_disable.disabled=false;
	}else{
	  document.all.btn_modify.disabled=true;
	  document.all.btn_reset_pwd.disabled=true;
	  document.all.btn_role.disabled=true;
	  document.all.btn_agent.disabled=true;
	  document.all.btn_enable.disabled=false;
	  document.all.btn_disable.disabled=false;
	}
  }else{
    //document.all.btn_del.disabled=true;
	document.all.btn_modify.disabled=true;
	document.all.btn_reset_pwd.disabled=true;
	document.all.btn_role.disabled=true;
	document.all.btn_agent.disabled=true;
	document.all.btn_enable.disabled=true;
	document.all.btn_disable.disabled=true;
  }
}

function add(){
  var link = "<%=request.getContextPath()%>/mx/sys/user/prepareAdd?parent=0";
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

function editRow(id){
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
  var height=300;
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

function changePwd(id){
  var link = "<%=request.getContextPath()%>/mx/sys/user/prepareResetPwd?id="+id;
  var width=450;
  var height=300;
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
			area: ['580px', (jQuery(window).height() - 250) +'px'],
            iframe: {src: link}
		});
}


function changeToken(id){
	debugger
  var link = "<%=request.getContextPath()%>/mx/sys/user/prepareResetToken?id="+id;
  var width=880;
  var height=280;
  var scroll="no";
  //openWindow(link, width, height, scroll);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "重置用户令牌",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 350) +'px'],
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
  var link = "<%=request.getContextPath()%>/mx/sys/user/showRole?actorId="+id;
  var width=550;
  var height=420;
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

function showDownloadMobile(form){
	var ids = "";
	for (var i=0;i<form.id.length;i++) {
		var e = form.id[i];
		if (e.checked){
			if(ids)
				ids += ",";
			ids += e.value;
		}     
	}
	if(!ids){
		alert("必须要选择对应用户才可导出!");
		return;
	}
	var k = jQuery.layer({
		type: 1,
		// maxmin: true,
		// shadeClose: true,
		title: "批量用户电话导出",
		// closeBtn: [0, true],
		// shade: [0.8, '#000'],
		// border: [10, 0.3, '#000'],
		// offset: ['20px',''],
		// fadeIn: 100,
		page:{
			html: '<div style="padding:10px;text-align: center;">每天可接受短信条数<input class="input limitPhone" type="number"><br><br><button class="downloadMobile">导出</button></div>',	
		},
		callback:function(){
			debugger;
		}
		// area: ['680px', (jQuery(window).height() - 50) +'px'],
	});
	jQuery(".downloadMobile").click(function(){

		var link = "<%=request.getContextPath()%>/mx/custom/user/downloadMobile";
		link += "?limit="+jQuery(".limitPhone").val() || 10;
		link += '&ids=' + ids;
		window.open(link);
		layer.close(k);
	})
}
function showDownloadAllMobile(){
  
  var width=580;
  var height=350;
  var scroll="yes";
  // debugger;
  // window.open(link);
  var k = jQuery.layer({
		type: 1,
		// maxmin: true,
		// shadeClose: true,
		title: "全部用户电话导出",
		// closeBtn: [0, true],
		// shade: [0.8, '#000'],
		// border: [10, 0.3, '#000'],
		// offset: ['20px',''],
		// fadeIn: 100,
		page:{
			html: '<div style="padding:10px;text-align: center;">每天可接受短信条数<input class="input limitPhone" type="number"><br><br><button class="downloadMobile">导出</button></div>',	
		},
		
		// area: ['680px', (jQuery(window).height() - 50) +'px'],
	});
	jQuery(".downloadMobile").click(function(){

		var link = "<%=request.getContextPath()%>/mx/custom/user/downloadAllMobile";
		link += "?limit="+jQuery(".limitPhone").val() || 10;
		window.open(link);
		layer.close(k);
	})
}


function editUserRole(id){
  var link = "<%=request.getContextPath()%>/mx/sys/user/showRole?actorId="+id;
  var width=550;
  var height=420;
  var scroll="yes";
  //openWindow(link, width, height, scroll);
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
			area: ['880px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function viewUserMenus(id){
  var link = "<%=request.getContextPath()%>/mx/sys/user/showUserMenus?actorId="+id;
  var width=550;
  var height=420;
  var scroll="yes";
  //openWindow(link, width, height, scroll);
  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "用户菜单",
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
	//document.getElementById("nameLike_encode").value="";
	//document.getElementById("actorIdLike_encode").value="";
	iForm.method="post";
    iForm.action="<%=request.getContextPath()%>/mx/sys/user/showAllList?_rq_=1";
	iForm.submit();
}

function resetStatus(form, status){
   var msg = "确定启用选中的用户，确认吗？";
   if(status == 1){
	   msg = "用户禁用后将无法登录系统，确定禁用选中用户吗？";
   }
   if(confirm(msg)){
	    var id =0;
	    for (var i=0;i<form.id.length;i++) {
		  var e = form.id[i];
		  if (e.checked){
		    id=e.value;
		  }     
	     }
         var params = jQuery("#iForm").formSerialize();
         jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/rs/sys/user/resetStatus?status='+status,
				   dataType: 'json',
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

</script>
</head>

<body style="margin-left:5px;">
<div class="nav-title"><span class="Title" style="font-size: 13px;font-weight: bold">用户管理</span>  </div>
<html:form id="iForm" name="iForm" method="post" target="_self">
<div class="form-group">
<label for="nameLike">姓名:</label>
<input type="text" name="nameLike" id="nameLike" size="20" value="${nameLike}" class="form-control" style="width: 10%;display: inline-block;"><input type="hidden" id="nameLike_encode" name="nameLike_encode" value="${nameLike_encode}">
<label for="nameLike">帐号：</label><input type="text" name="actorIdLike" id="actorIdLike" size="20" value="${actorIdLike}" class="form-control" style="width: 10%;display: inline-block;"/>

<input type="hidden" id="actorIdLike_encode" name="actorIdLike_encode" value="${actorIdLike_encode}">
<input name="btn_query" type="button" value="查询" class="button btn-success" onClick="javascript:queryForm();"/> 
<input name="page_no" type="hidden" value="<%=pager.getCurrentPageNo()%>">
<input type="hidden" name="id" value="0">
</div>
<table width="100%" border="0" cellspacing="1" cellpadding="0" class="table table-bordered">
<thead>
  <tr style='background-color:#e6f2f8;font-size:15px;'> 
    <th style="text-align:center;width: 5%"> 
	<input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this);checkOperation(this.form)">    
	</th>
    <th style="text-align:center;width: 5%">序号</th>
    <th style="text-align:center;width: 10%">帐号</th>
    <th style="text-align:center;width: 10%">姓名</th>
    <th style="text-align:center;width: 10%">是否有效</th>
    <th style="text-align:center;width: 10%">创建日期</th>
    <th style="text-align:center;width: 10%">上次登陆时间</th>
	<th style="text-align:center;width: 40%">功能键</th>
  </tr>
  </thead>
  <%
int i=0;
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    SysUser bean=(SysUser)iter.next();
%>
<tbody>
  <tr <%=i%2==0?"":"style='background-color:#e6f2f8;font-size:15px;'"%>> 
    <td width="5%" align="left" class="td-cb"> 
	<input type="checkbox" id="id" name="id" value="<%= RequestUtils.encodeString(bean.getActorId())%>" onClick="checkOperation(this.form)"> 
	</td>
    <td width="5%"  align="left" class="td-no" style="color:#003f59;font-size:13px;"><%=((pager.getCurrentPageNo()-1)*pageSize + i+1)%></td>
    <td width="10%" align="left"  class="td-text" style="color:#003f59;font-size:13px;"><%=bean.getAccount()%>&nbsp;</td>
    <td width="10%" align="left" class="td-text" style="color:#003f59;font-size:13px;"><%=bean.getName()%>&nbsp;</td>
    <td width="10%" align="center" class="td-no" style="color:#003f59;font-size:13px;"><%=bean.getLocked()==0?"<span style='color:blue'>是</span>":"<span style='color:red'>否</span>"%>&nbsp;</td>
    <td width="10%" align="center" class="td-time" style="color:#003f59;font-size:13px;">
	    <%=DateUtils.getDate(bean.getCreateTime())%>&nbsp;&nbsp;
	</td>
    <td width="10%" align="center" class="td-time" align="center" >
	   <%=DateUtils.getDate(bean.getLastLoginTime())%>&nbsp;&nbsp;
	</td>
	<td align="center">&nbsp;
	   <a href="#" style="color:#003f59;font-size:13px;" onclick="javascript:editRow('<%=RequestUtils.encodeString(bean.getActorId())%>');">修改</a>&nbsp;
	   <a href="#" style="color:#003f59;font-size:13px;" onclick="javascript:changePwd('<%=RequestUtils.encodeString(bean.getActorId())%>');">重置密码</a>&nbsp;
	   <a href="#" style="color:#003f59;font-size:13px;" onclick="javascript:changeToken('<%=RequestUtils.encodeString(bean.getActorId())%>');">重置令牌</a>&nbsp;
	   <a href="#" style="color:#003f59;font-size:13px;" onclick="javascript:editUserRole('<%=RequestUtils.encodeString(bean.getActorId())%>');">角色设置</a>&nbsp;
	   <a href="#" style="color:#003f59;font-size:13px;" onclick="javascript:viewUserMenus('<%=RequestUtils.encodeString(bean.getActorId())%>');">菜单</a>&nbsp;
    </td>
    </tr>
  <%
    i++;
  }
}
for(; i<pageSize; i++){
%>
  <tr <%=i%2==0?"":"style='background-color:#e6f2f8;'"%>> 
    <td height="20">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
  </tr>
  <tbody>
<%
}
%>
</table>
<br>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td  width="60%" align="left"> 
	  <input name="btn_add" type="button" value="增加" class="button btn-primary" onClick="javascript:add();"> 
      <!-- <input name="btn_del" type="button" value="删除" class="button" onClick="javascript:del();" disabled> -->
      <input name="btn_modify" type="button" value="修改" class="button btn-info" onClick="javascript:modify(this.form);" disabled>
	  <input name="btn_reset_pwd" type="button" value="重置密码" style="background:#CD4F39;color:white" class="button" onClick="javascript:resetPwd(this.form);" disabled>
      <input name="btn_role" type="button" value="角色设置" style="background:#BCD2EE;color:white" class="button" onClick="javascript:roles(this.form);" disabled> 
	  <input name="btn_agent" type="button" value="代理设置" style="background:#9ACD32;color:white" class="button" onClick="javascript:agent(this.form);" disabled>
	  <input name="btn_enable" type="button" value="启用" style="background:#8E8E38;color:white" class="button" onClick="javascript:resetStatus(this.form,0);" disabled>
	  <input name="btn_disable" type="button" value="禁用" style="background:#8968CD;color:white" class="button" onClick="javascript:resetStatus(this.form,1);" disabled>
	  <input name="btn_imp" type="button" value="导入" style="background:	#5D478B;color:white" class="button" onClick="javascript:showImport(this.form);" >
	  <input name="btn_imp" type="button" value="导出用户手机信息"  style="background:#53868B;color:white" class="button" onClick="javascript:showDownloadMobile(this.form);" >
	  <input name="btn_imp" type="button" value="导出全部用户手机信息" style="background:#40E0D0;color:white" class="button" onClick="javascript:showDownloadAllMobile();" >
	  <c: test="${!empty serverEntity}">
	<!--   <input name="btn_imp2" type="button" value="同步到云众联" class="button" onClick="javascript:doImport(this.form);" > -->
      </c:if>
	</td>
    <td  width="40%" align="center"> 
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
