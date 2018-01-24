<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%
	String context = request.getContextPath();
	SysUser bean=(SysUser)request.getAttribute("bean");
	pageContext.setAttribute("contextPath", request.getContextPath());
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>重置密码</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/bootstrap/css/bootstrap.min.css" />
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script></head>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/bootstrap/js/bootstrap.min.js"></script>
<script language="javascript">

function checkForm(form){
  if(verifyAll(form)){
     if(form.newPwd.value!=form.password2.value){
	   alert("新密码与确认密码不匹配！");
	 }else {
	   if(confirm("确定要重置“<%=bean.getName()%>”的密码吗？")){
	       return true;
	   }
	 }
  }
   return false;
}

function setValue(obj){
  obj.value=obj[obj.selectedIndex].value;
}
</script>
</head>

<body>
<div class="nav-title"><span class="Title" style="font-size: 13px;font-weight: bold">重置密码</span></div>
<html:form action="${contextPath}/mx/branch/user/resetPwd" method="post"  onsubmit="return checkForm(this);"> 
<input type="hidden" name="id" value="<%=RequestUtils.encodeString(bean.getActorId())%>"> 
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="box">
        <td class="box-lt">&nbsp;</td>
        <td class="box-mt">&nbsp;</td>
        <td class="box-rt">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
    
     <div class="form-group row">
       <label class="col-xs-3 " for="account" style="text-align: right;">用户名:</label>
          <div class="col-xs-9">
          <%=bean.getActorId()%>
          </div>
     </div>
    
	  
     <div class="form-group row">
       <label class="col-xs-3 " for="account" style="text-align: right;">姓　名:</label>
          <div class="col-xs-9">
          <%=bean.getName()%>
          </div>
     </div>
      
      <div class="form-group row">
       <label class="col-xs-3 " for="account" style="text-align: right;">新码*:</label>
          <div class="col-xs-9">
         <input name="newPwd" type="password" size="30" class="form-control" value="" datatype="string" nullable="no" minsize="6" maxsize="20" chname="密码" style="width:200px;display: inline-block;">
          </div>
     </div>
     
      <div class="form-group row">
       <label class="col-xs-3 " for="password2" style="text-align: right;">确认密码*:</label>
          <div class="col-xs-9">
          <input name="password2" type="password" size="30" class="form-control" value=""  datatype="string" nullable="no" minsize="6" maxsize="20" chname="确认密码" style="width:200px;display: inline-block;">
          </div>
     </div>
      
     
      
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
           <input name="btn_save2" type="submit" value="修改密码" class="btnGray btn-success">
		</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="box">
        <td class="box-lb">&nbsp;</td>
        <td class="box-mb">&nbsp;</td>
        <td class="box-rb">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>
</html>
