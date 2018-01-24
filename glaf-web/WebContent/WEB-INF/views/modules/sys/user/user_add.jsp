<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
    String context = request.getContextPath();
    pageContext.setAttribute("contextPath", context);
    List  list = (List)request.getAttribute("parent");
    int parent=ParamUtil.getIntParameter(request, "parent", 0);
	int nodeId=ParamUtil.getIntParameter(request, "nodeId", 0);
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加用户</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/bootstrap/css/bootstrap.min.css" />

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/plugins/bootstrap/datetimepicker/css/bootstrap-datetimepicker.min.css" />

<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/bootstrap/js/bootstrap.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/kendo/utils.js"></script> 

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/bootstrap/datetimepicker/js/bootstrap-datetimepicker.min.js"></script> 

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/bootstrap/datetimepicker/ext/jquery.datetimepicker.extends.js"></script> 

<script language="javascript">

  var contextPath = "<%=request.getContextPath()%>";

  function checkForm(form){
    if(verifyAll(form)){
     if(form.password.value!=form.password2.value){
	   alert("密码与确认密码不匹配!");
	 }else{
	   return true;
	 }
    }
     return false;
  }

</script>
</head>

<body>
<div class="nav-title"><span class="Title" style="font-size: 13px;font-weight: bold">用户管理</span>&gt;&gt;<span class="Title" style="font-size: 13px;font-weight: bold">增加用户</span></div>
<html:form id="iForm" name="iForm" action="${contextPath}/mx/sys/user/saveAdd"
           method="post" onsubmit="return checkForm(this);"> 
<input type="hidden" name="parent" value="<%=parent%>">
<input type="hidden" name="nodeId" value="<%=nodeId%>">

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
    <td><table width="80%" align="center" border="0" cellspacing="0" cellpadding="5">
    <div class="form-group row">
       <label class="col-xs-3 " for="name" style="text-align: right;">用户名*</label>
          <div class="col-xs-9">
          <input name="id" type="text" size="30"  datatype="string" nullable="no" maxsize="20" chname="员工编码" class="form-control" style="width:300px;display: inline-block;">
          </div>
    </div>
    
     <div class="form-group row">
       <label class="col-xs-3 " for="password" style="text-align: right;">密　码*</label>
          <div class="col-xs-9">
          <input name="password" type="password" size="30" class="form-control" datatype="string" nullable="no" minsize="6" maxsize="20" chname="密码" style="width:300px;display: inline-block;">
          </div>
     </div>
    
     
       <div class="form-group row">
       <label class="col-xs-3 " for="password" style="text-align: right;">确认密码*</label>
          <div class="col-xs-9">
            <input name="password2" type="password" size="30" class="form-control"  datatype="string" nullable="no" minsize="6" maxsize="20" chname="确认密码" style="width:300px;display: inline-block;">
          </div>
       </div>
     
        <div class="form-group row">
        <label class="col-xs-3 " for="name" style="text-align: right;">姓　名*</label>
          <div class="col-xs-9">
            <input name="name" type="text" size="30" class="form-control" datatype="string" nullable="no" maxsize="20" chname="姓名" style="width:300px;display: inline-block;">
          </div>
       </div>
     	
      <div class="form-group row">
         <label class="col-xs-3 " for="name" style="text-align: right;">编　码</label>
          <div class="col-xs-9">
          	<input name="code" type="text" size="30" class="form-control" datatype="string" nullable="no" maxsize="20" chname="编码" style="width:300px;display: inline-block;">
          </div>
       </div>
      
	   <div class="form-group row">
         <label class="col-xs-3 " for="radio" style="text-align: right;">性　别</label>
          <div class="col-xs-9">
          <label class="radio-inline">
            <input type="radio" name="gender" value="0">男
          </label>
          <label class="radio-inline">
            <input type="radio" name="gender" value="1" checked>女
          </label>
          </div>
       </div>
       
      <div class="form-group row">
         <label class="col-xs-3 " for="code" style="text-align: right;">手　机</label>
          <div class="col-xs-9">
          <input name="mobile" type="text" size="30" class="form-control" datatype="string" nullable="yes" maxsize="12" chname="手机" style="width:300px;display: inline-block;">
          </div>
       </div> 
      
      <div class="form-group row">
         <label class="col-xs-3 " for="mobile" style="text-align: right;">邮　件</label>
          <div class="col-xs-9">
          <input name="email" type="text" size="30" class="form-control" datatype="email" nullable="yes" maxsize="50" chname="邮件" style="width:300px;display: inline-block;">
          </div>
       </div> 
       
      <div class="form-group row">
         <label class="col-xs-3 " for="telephone" style="text-align: right;">办公电话</label>
          <div class="col-xs-9">
           <input name="telephone" type="text" size="30" class="form-control" datatype="string" nullable="yes" maxsize="20" chname="办公电话" style="width:300px;display: inline-block;"> 
          </div>
       </div> 
      <div class="form-group row">
         <label class="col-xs-3 " for="headship" style="text-align: right;">职　位</label>
          <div class="col-xs-9">
          <select id="headship" name="headship"  class="form-control" style="width:300px;">
			  <option value="" >----请选择----</option>
			  <c:forEach items="${dictories}" var="a">
				<option value="${a.code}">${a.name} [${a.code}]</option>
			  </c:forEach>
		   </select>
		   <script type="text/javascript">
		        document.getElementById("headship").value="${bean.headship}";
		   </script>
          </div>
       </div> 
      
      <div class="form-group row">
         <label class="col-xs-3 " for="mobile" style="text-align: right;">账户类型</label>
          <div class="col-xs-9">
          <select id="accountType" name="accountType" class="form-control" style="width:300px;">
			  <option value="0">----请选择----</option>
			  <c:forEach items="${accountTypeDictories}" var="a">
				<option value="${a.code}">${a.name} [${a.code}]</option>
			  </c:forEach>
		   </select>
		   <script type="text/javascript">
		        document.getElementById("accountType").value="${bean.accountType}";
		   </script>
          </div>
       </div> 
      
       <div class="form-group row">
         <label class="col-xs-3 " for="superiorIds" style="text-align: right;">直接上级</label>
          <div class="col-xs-9">
           <input type="hidden" id="superiorIds" name="superiorIds" value="">
          <textarea cols="40" id="x_users_name" name="x_users_name" rows="8"  wrap="yes" readonly  
             onclick="javascript:selectUser('iForm', 'superiorIds','x_users_name');" class="form-control"
		     style="height:25px;width:300px;color: #000066; background: #ffffff;display: inline-block;"></textarea>
          </div>
       </div> 
	
	 <div class="form-group row">
         <label class="col-xs-3 " for="telephone" style="text-align: right;">允许通过MQ登录</label>
          <div class="col-xs-9">
          <select id="mqLoginFlag" name="mqLoginFlag" class="form-control" style="width:300px;">
			  <option value="">----请选择----</option>
				<option value="Y">允许</option>
				<option value="N">不允许</option>
		   </select>
		   <script type="text/javascript">
		        document.getElementById("mqLoginFlag").value="${bean.mqLoginFlag}";
		   </script>
          </div>
       </div> 
	 
	  <div class="form-group row">
         <label class="col-xs-3 " for="superiorIds" style="text-align: right;">允许第三方通过密锁登录</label>
          <div class="col-xs-9">
          <select id="secretLoginFlag" name="secretLoginFlag" class="form-control" style="width:300px;">
			  <option value="">----请选择----</option>
				<option value="Y">允许</option>
				<option value="N">不允许</option>
		   </select>
		   <script type="text/javascript">
		        document.getElementById("secretLoginFlag").value="${bean.secretLoginFlag}";
		   </script>
       </div> 
       </div>

    <div class="form-group row">
      <label class="col-xs-3 " for="deadlineTime" style="text-align: right;">截止日期</label>
      <div class="col-xs-9">
        <input id="deadlineTime" name="deadlineTime" class="form-control" style="width:300px;">
        <script type="text/javascript">
          $("#deadlineTime").datetimepickerExt({
            format: "yyyy-mm-dd hh:ii:ss",
            defaultsystemdate: "null",
            position: "right top",
            visible: true,
            enabled: true,
            readable: true,
            clearBtn: false,
          });
          // $("#deadlineTime").datetimepickerExt("setValue","${bean.deadlineTime}");
          
        </script>
      </div>
    </div>
	 
	  <div class="form-group row">
         <label class="col-xs-3 " for="radio" style="text-align: right;">是否有效</label>
          <div class="col-xs-9">
            <label class="radio-inline">
             <input type="radio" name="status" value="0" checked>是
           </label>
          <label class="radio-inline">
            <input type="radio" name="status" value="1">否
            </label>
       </div> 
	  
       
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
         <input name="btn_save" type="submit" value="保存" class="button btn-success">
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
