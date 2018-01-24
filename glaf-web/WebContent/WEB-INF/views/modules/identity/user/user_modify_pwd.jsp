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
pageContext.setAttribute("contextPath", context); 
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
<link rel="stylesheet" type="text/css" href="<%=context%>/scripts/password/passwordRule.css">
<script language="javascript" src='<%=context%>/scripts/jquery.min.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script></head>
<script language="javascript" src='<%=context%>/scripts/password/passwordRule.js'></script></head>
<script language="JavaScript">
var contextPath = '${contextPath}';

function checkForm(form){
  var currStrength=jQuery("#level").attr("strength");
  if(verifyAll(form)){
    jQuery(".errorSpan").text("");
    if(!form.oldPwd.value){
      jQuery(".oldErrorSpan").text("请输入原密码");
      // document.getElementsByClassName("oldErrorSpan")[0].innerHTML = "请输入原密码";
      // $("#oldErrorSpan").text("请输入原密码");
    }else if(!form.newPwd.value){
      jQuery(".newErrorSpan").text("请输入新密码");
      // document.getElementsByClassName("newErrorSpan")[0].innerHTML = "请输入新密码";
      // $("#newErrorSpan").text("请输入新密码")
    }else if(form.newPwd.value!=form.password2.value){
      jQuery(".newErrorSpan").text("新密码与确认密码不匹配");
      // document.getElementsByClassName("newErrorSpan")[0].innerHTML = "新密码与确认密码不匹配";
      // $("#newErrorSpan").text("新密码与确认密码不匹配！");
	   // alert("新密码与确认密码不匹配！");
	 }else if(strength>currStrength){
		 alert("密码太简单，请设置"+strength+"级强度的密码！");
	 }else{
      var params = {
        oldPwd : form.oldPwd.value,
        newPwd : form.newPwd.value,
        password2 : form.password2.value
      }
      jQuery.ajax({
        url: contextPath + "/mx/identity/user/savePwd2",
        type: "post",
        dataType: "json",
        async: true,
        data: params,
        success: function(ret) {
          if(ret && ret.statusCode == '200'){
            //成功
            alert("操作成功");
          }else{
            alert("原密码错误，请重新输入！");
          }
        },
        error: function(e) {
          console.log("服务器处理错误！,请修改后再试。");
        }
      })

	   return false;
	 }
  }
   return false;
}
function vaildOld(){
  if(!jQuery("#oldPwd").val()){
    jQuery(".oldErrorSpan").text("请输入原密码");
  }
}
function hiddenOldWarn(){
  jQuery(".oldErrorSpan").text("");
}
function vaildNew(){
  if(!jQuery("#newPwd").val()){
    jQuery(".newErrorSpan").text("请输入新密码");
  }
}
function hiddenNewWarn(){
  jQuery(".newErrorSpan").text("");
}
function setValue(obj){
  obj.value=obj[obj.selectedIndex].value;
}
</script>
<style type="text/css">
.page-title {
	color:#153544;
	font-family: 微软雅黑;
	border-left:4px solid #588fc8;
	padding-left:5px;
}
input{
	height:25px;
}
.icon{
	margin-right:5px;
}
.errorSpan{
  color:red;
}
</style>
</head>

<body>
<h2 class="page-title"> 
   修改密码
</h2>
<html:form action="${contextPath}/mx/identity/user/savePwd" method="post"  onsubmit="return checkForm(this);"> 
<table width="500" border="0" align="center" cellpadding="0" cellspacing="0" class="box">
  <tr>
    <td>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="box">
        <td class="box-lt"></td>
        <td class="box-mt">&nbsp;</td>
        <td class="box-rt">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="box-mm"><table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
	  <tr>
        <td width="107px" valign="middle"><img class="icon" src="${contextPath}/images/s_09.jpg"/>原密码*</td>
        <td width="228px"><input name="oldPwd" id="oldPwd" type="password" size="30" class="input" onclick="hiddenOldWarn()" onblur="vaildOld()" value="" datatype="string" nullable="no" style="width:228px" minsize="6" maxsize="20" chname="密码"></td>
        <td class="errorSpan oldErrorSpan"></td>
      </tr>
      <tr>
        <td valign="middle"><img class="icon" src="${contextPath}/images/s_09.jpg"/>新密码*</td>
        <td><input name="newPwd" type="password" id="newPwd" size="30" style="width:228px" onclick="hiddenNewWarn()" onblur="vaildNew()" class="input" value="" datatype="string" nullable="no" minsize="6" maxsize="20" chname="密码"></td>
        <td class="errorSpan newErrorSpan"></td>
      </tr>
      <tr>
        <td valign="middle"><img class="icon" src="${contextPath}/images/s_09.jpg"/>确认密码*</td>
        <td><input name="password2" type="password" size="30" style="width:228px" class="input" value=""  datatype="string" nullable="no" minsize="6" maxsize="20" chname="确认密码"></td>
      </tr>
	  <tr>    
		<td></td>       
		<td id="level" class="pw-strength">           	
			<div class="pw-bar"></div>
			<div class="pw-bar-on"></div>
			<div class="pw-txt">
			<span>弱</span>
			<span>中</span>
			<span>强</span>
			</div>
		</td>	
	  </tr>
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
           <input name="btn_save2" type="submit" value="确定" class="button">
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
