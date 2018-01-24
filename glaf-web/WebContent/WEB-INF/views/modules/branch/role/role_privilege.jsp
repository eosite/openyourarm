<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.utils.*"%>
<%
	List list = (List)request.getAttribute("list");
	SysRole role = (SysRole)request.getAttribute("role");

	Set appIds=new HashSet();
	/*
	Iterator temp = role.getApps().iterator();
	while(temp.hasNext()){
	  SysApplication bean=(SysApplication)temp.next();
	  appIds.add(bean.getId());
	}

	Set funcIds=new HashSet();
	temp = role.getFunctions().iterator();
	while(temp.hasNext()){
	  SysFunction bean=(SysFunction)temp.next();
	  funcIds.add(bean.getId());
	}*/
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link type="text/css" href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet">
<script language="javascript" src='<%=request.getContextPath()%>/scripts/verify.js'></script>
<script language="javascript" src='<%=request.getContextPath()%>/scripts/main.js'></script>
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
    alert("您还没有选择要授权的模块.");
    return false;
  }
}
function selApp(id){
  var temp;
  eval("temp=document.all.oper1_"+ id +".checked");
  if(!temp){
    eval("document.all.oper1_"+ id +".checked=true");
  }
}
function selAllApp(id){
  var temp;
  eval("temp=document.all.operall_"+ id +".checked");
  if(!temp){
    eval("document.all.oper1_"+ id +".checked=false");
    eval("document.all.oper2_"+ id +".checked=false");
    eval("document.all.oper3_"+ id +".checked=false");
    eval("document.all.oper4_"+ id +".checked=false");
    eval("document.all.oper5_"+ id +".checked=false");
  }else{
    eval("document.all.oper1_"+ id +".checked=true");
    eval("document.all.oper2_"+ id +".checked=true");
    eval("document.all.oper3_"+ id +".checked=true");
    eval("document.all.oper4_"+ id +".checked=true");
    eval("document.all.oper5_"+ id +".checked=true");  
  }
}
function unSelApp(id){
  var temp;
  eval("temp=document.all.oper1_"+ id +".checked");
  if(!temp){
    eval("document.all.oper2_"+ id +".checked=false");
    eval("document.all.oper3_"+ id +".checked=false");
    eval("document.all.oper4_"+ id +".checked=false");
    eval("document.all.oper5_"+ id +".checked=false");
	eval("document.all.operall_"+ id +".checked=false");
  }
}
</SCRIPT>
</head>

<body>
<html:form action="${contextPath}/mx/branch/role/setPrivilege" method="post" target="_self" onsubmit="return checkForm(this);"> 
<input type="hidden" name="roleId" value="<%=role.getId()%>">
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <th width="11">&nbsp;</th>
    <th width="995" valign="top"><div align="left"> 
        <table width="100%"  border="0" cellpadding="0" cellspacing="0" background="<%=request.getContextPath()%>/images/content_title_bg.jpg">
          <tr> 
            <td width="200" height="45" valign="bottom"><table width="144%" height="100%"  border="0" align="right" cellpadding="0" cellspacing="0">
                <tr> 
                  <td width="25"><div align="center"><img src="<%=request.getContextPath()%>/images/content_lt.jpg" width="11" height="34"></div></td>
                  <td><span class="style2">角色管理</span></td>
                </tr>
              </table></td>
             
          </tr>
        </table>
		</div></th>
  </tr>
</table>
<table width="100%" align="center"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="font">&nbsp;设置角色 <b><%=role.getName()%></b> 的权限 </td>
  </tr>
</table>
<table width="95%" height="4"  border="0" align="center" cellpadding="0" cellspacing="0" background="<%=request.getContextPath()%>/images/content_2_bg.jpg">
  <tr>
    <td></td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="2">
  <tr>
    <td width="5%" class="listborder"><div align="center" class="fontname_12">序号</div></td>
    <td width="30%" class="listborder"><div align="left" class="fontname_12">模块名称</div></td>
    <td width="65%" class="listborder"><div align="center"  class="fontname_12">操作</div></td>
  </tr>
  <%
int i=0;
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    SysTree bean=(SysTree)iter.next();
    long id = bean.getApp().getId();
%>
  <tr>
    <td align="center" class="list"><%= i+1%></td>
    <td class="list">
	<%
		if(bean.getDeep()>1){  
		  for(int j=1; j<=bean.getDeep()-1; j++){
			out.print("　　");
		  }
		  out.print("--");
		}else{
		  out.print("＋");
		}
		out.print(bean.getName());
		%>
      &nbsp;
      <input type="hidden" name="appId" value="<%=id%>">
    </td>
   
  </tr>
  <%
    i++;
  }
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
    <td align="center"><input name="btn_add" type="submit" value="保存" class="butt-normal"></td>
  </tr>
</table>
</html:form> 
</body>
</html>