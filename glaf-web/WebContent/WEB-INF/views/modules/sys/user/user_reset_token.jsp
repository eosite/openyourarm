<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
SysUser bean=(SysUser)request.getAttribute("bean");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/bootstrap/css/bootstrap.min.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/bootstrap/js/bootstrap.min.js"></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script language="javascript">
function resetUserToken(){
    if(confirm("确定要重置“${bean.name}”的令牌吗？")){
	    jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/user/resetUserToken?actorId=${bean.encodeActorId}',
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
}
 
</script>
</head>

<body style="overflow: hidden">
<div class="nav-title"><span class="Title" style="font-size: 13px;font-weight: bold">重置令牌</span></div>
<html:form id="iForm" name="iForm" method="post" > 
<input type="hidden" name="actorId" value="<%=bean.getActorId()%>"> 
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
	  
      <div class="form-group row">
       <label class="col-xs-3 " for="name" style="text-align: right;">用户名:</label>
          <div class="col-xs-9">
         ${bean.account}
          </div>
      </div>
      
      <div class="form-group row">
       <label class="col-xs-3 " for="name" style="text-align: right;">姓 名:</label>
          <div class="col-xs-9">
          ${bean.name}
          </div>
      </div>
      
      <div class="form-group row">
       <label class="col-xs-3 " for="name" style="text-align: right;">Email:</label>
          <div class="col-xs-9">
         ${bean.mail}
          </div>
      </div>
      <div class="form-group row">
       <label class="col-xs-3 " for="name" style="text-align: right;">当前令牌:</label>
          <div class="col-xs-9">
         ${bean.token}
          </div>
      </div>
      
      
	  
	  
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
           <input name="btn_save2" type="button" value="重置令牌" class="button btn-success" onclick="javascript:resetUserToken();">
		</td>
      </tr>
</table>
</html:form>
</body>
</html>
