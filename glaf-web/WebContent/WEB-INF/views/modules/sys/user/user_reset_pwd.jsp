<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.modules.BaseDataManager"%>
<%@ page import="com.alibaba.fastjson.JSONObject"%>
<%
String context = request.getContextPath();
SysUser bean=(SysUser)request.getAttribute("bean");
BaseDataManager bm=BaseDataManager.getInstance();
Iterator<BaseDataInfo> passwordRules = bm.getList("passwordrules");
String passwordRuleStr="[]";
if(passwordRules!=null){
    passwordRuleStr=JSONObject.toJSONString(passwordRules);
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="<%=context%>/scripts/password/passwordRule.css">
<script language="javascript" src='<%=context%>/scripts/jquery.min.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script></head>
<script language="javascript" src='<%=context%>/scripts/password/passwordRule.js'></script></head>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/bootstrap/js/bootstrap.min.js"></script>
<script language="JavaScript">
function checkForm(form){
	var currStrength=jQuery("#level").attr("strength");
  if(verifyAll(form)){
     if(form.newPwd.value!=form.password2.value){
	     alert("新密码与确认密码不匹配！");
	 }else if(strength>currStrength){
		 alert("密码太简单，请设置"+strength+"级强度的密码！");
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

<body style="overflow: hidden">
<div class="nav-title"><span class="Title"  style="font-size: 13px;font-weight: bold">重置密码</span></div>
<html:form action="${contextPath}/mx/sys/user/resetPwd" method="post"  onsubmit="return checkForm(this);"> 
<input type="hidden" name="id" value="<%=bean.getActorId()%>"> 
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
       <label class="col-xs-3 " for="name" style="text-align: right;">用户名:</label>
          <div class="col-xs-9">
          <%=bean.getActorId()%>
          </div>
      </div>
      
      <div class="form-group row">
       <label class="col-xs-3 " for="name" style="text-align: right;">姓　　名:</label>
          <div class="col-xs-9">
         <%=bean.getName()%>
          </div>
      </div>
      
      <div class="form-group row">
       <label class="col-xs-3 " for="newPwd" style="text-align: right;">新密码*:</label>
          <div class="col-xs-9">
         <input name="newPwd" type="password" size="30" class="form-control" value="" datatype="string" nullable="no" minsize="6" maxsize="20" chname="密码" style="width:250px;display: inline-block;">
		  <span id="strengthDesc" style="color:red;"></span>
          </div>
      </div>
      
      <div class="form-group row">
       <label class="col-xs-3 " for="newPwd" style="text-align: right;">确认密码*:</label>
          <div class="col-xs-9">
          <input name="password2" type="password" size="30" class="form-control" value=""  datatype="string" nullable="no" minsize="6" maxsize="20" chname="确认密码" style="width:250px;display: inline-block;">
          </div>
      </div>
      
      <div class="form-group row">
       <label class="col-xs-3 " for="newPwd" style="text-align: right;"></label>
          <div  id="level" class="col-xs-9" >
          <div class="pw-bar"></div>
			<div class="pw-bar-on"></div>
			<div class="pw-txt">
			<span>弱</span>
			<span>中</span>
			<span>强</span>
			</div>
          </div>
      </div>
	 
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
           <input name="btn_save2" type="submit" value="修改密码" class="button btn-success">
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
<script language="JavaScript">
  var  passwordRuleJSON=<%=passwordRuleStr%>;
  var  options={};
  var  rules={};
  var  ruleName,rule;
  //要求密码强度
  var  strength=0;
  if(passwordRuleJSON&&passwordRuleJSON.length>0){
  jQuery.each(passwordRuleJSON,function(i,passwordRule){
	    ruleName=passwordRule.code;
		if(ruleName=='strength'){
			strength=passwordRule.value;
			if(strength&&strength>0){
			   jQuery("#strengthDesc").text("要求密码强度等级为"+strength+"级");
			}
		}else{
			rule={};
			rule.len=passwordRule.value;
			rule.name=passwordRule.name;
			rules[ruleName]=rule;
		}
  });
  }
  options.rules=rules;
  jQuery(function(){
	jQuery("[name='newPwd']").passwordRule(options);
});
</script>
</body>
</html>
