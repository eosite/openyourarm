<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%
	String context = request.getContextPath();
	List list = (List)request.getAttribute("list");
	SysUser user = (SysUser)request.getAttribute("user");
	int deptId=ParamUtil.getIntParameter(request, "deptId", 0);
	Set roleIds=new HashSet();
	Iterator roles = user.getRoles().iterator();
	while(roles.hasNext()){  
	  SysRole role=(SysRole)roles.next();
	  roleIds.add(role.getId());
	}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<link rel="stylesheet" type="text/css" href="<%=context%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/bootstrap/css/bootstrap.min.css"/>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript" src='<%=context%>/scripts/jquery.min.js'></script>
<script language="javascript" src='<%=context%>/scripts/jquery.form.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/bootstrap/js/bootstrap.min.js"></script>
<script language="javascript">
function checkForm(form){
  var isChecked = false;
  for(var i = 0; i < form.elements.length; i++){
    var e = form.elements[i];
    if(e.name != "chkall" && e.type == "checkbox" && e.checked == true){
      isChecked = true;
      break;
    }
  }
  if(!isChecked){
    alert("您还没有选择要授权的角色.");
    return false;
  }
}

function submitRequest(closeWin){
	var ids = '';
	var obj = document.getElementsByName("id");
	var num = 0;
	for ( var i = 0; i < obj.length; i++) {
		var e = obj.item(i);
		if (e.checked) {
			num++;
			ids+=e.value+',';
		}
	}
	//alert(ids);
	jQuery.ajax({
			type: "POST",
			url: '<%=request.getContextPath()%>/mx/branch/user/saveUserRoles?actorId=<%=RequestUtils.encodeString(user.getActorId())%>&deptId=<%=deptId%>&objectIds='+ids,
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
					if(closeWin){
						window.parent.location.reload();
						window.close();
					}
			    }
			}
	  });
 }

</script>
</head>
<body>
<div class="nav-title">
  <span class="Title" style="font-size: 13px;font-weight: bold">用户管理</span><span class="Title" style="font-size: 13px;font-weight: bold">&gt;&gt;设置用户 <b><%=user.getName()%></b> 的角色</span>
</div>
<html:form  name="iForm" method="post" > 
<table width="95%" border="0" align="center" cellspacing="1" cellpadding="0" class="table table-bordered">
  <tr style='background-color:#e6f2f8;font-size:15px;'> 
    <td width="9%" align="center">
	  <input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this)">
    </td>
    <td width="10%" align="center">序号</td>
    <td width="37%" align="center">名称</td>
    <td width="44%" align="center">描述</td>
  </tr>
  <%
	int i=0;
	if(list!=null && list.size()>0){
	  Iterator iter2=list.iterator();   
	  while(iter2.hasNext()){
		SysRole role = (SysRole)iter2.next();
		%>
		  <tr <%=i%2==0?"":"style='background-color:#e6f2f8;font-size:15px;'"%>>
			<td class="td-cb"><input type="checkbox" name="id" value="<%=role.getId()%>" <%=roleIds.contains(role.getId())?"checked":""%>>
			</td>
			<td class="td-no"><%= i+1%></td>
			<td class="td-text"><%=role.getName()%>&nbsp;</td>
			<td class="td-text"><%=role.getContent() !=null ? role.getContent() : ""%>&nbsp;</td>
		  </tr>
		  <%
			i++;
		  }
		}
		for(; i<10; i++){
		%>
		  <tr <%=i%2==0?"":"style='background-color:#e6f2f8;font-size:15px;'"%>> 
			<td height="20">&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp; </td>
			<td>&nbsp;</td>
			</tr>
	<%
		}
	%>
</table>
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center" height="30" valign="bottom">
	   <input name="btn_save" type="button" value=" 保存" class="btnGray btn-success" onclick="javascript:submitRequest(false);">
	   <input name="btn_save" type="button" value=" 保存并关闭 " class="btnGray btn-primary" onclick="javascript:submitRequest(true);">
    </td>
  </tr>
</table>
</html:form> 
</body>
</html>