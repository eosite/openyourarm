<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%
	String context = request.getContextPath();
	SysUser bean=(SysUser)request.getAttribute("bean");
	List  list = (List)request.getAttribute("trees");
	int nodeId=ParamUtil.getIntParameter(request, "nodeId", 0);
	int deptId=ParamUtil.getIntParameter(request, "deptId", 0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/bootstrap/css/bootstrap.min.css" />

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/plugins/bootstrap/datetimepicker/css/bootstrap-datetimepicker.min.css" />

<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script></head>
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
     //if(form.password.value!=form.password2.value){
	 //  alert("密码与确认密码不匹配");
	 //}else{
	 //  return true;
	 //}
	 return true;
  }
   return false;
}
function setValue(obj){
  obj.value=obj[obj.selectedIndex].value;
}
</script>
</head>

<body>
<div class="nav-title"><span class="Title" style="font-size: 13px;font-weight: bold">用户管理</span><span class="Title" style="font-size: 13px;font-weight: bold">&gt;&gt;修改用户</span></div>
<html:form id="iForm" name="iForm" action="${contextPath}/mx/branch/user/saveModify" 
      method="post"  onsubmit="return checkForm(this);"> 
<input type="hidden" name="id" value="<%=RequestUtils.encodeString(bean.getActorId())%>">
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
    <td><table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
    
    <div class="form-group row">
       <label class="col-xs-3 " for="account" style="text-align: right;">用户名</label>
          <div class="col-xs-9"  class="form-control">
          <%=bean.getActorId()%>
          </div>
     </div>
      <div class="form-group row">
       <label class="col-xs-3 " for="name" style="text-align: right;">部　门</label>
          <div class="col-xs-9">
          <select id="deptId" name="deptId" onchange="javascript:setValue(this);" class="form-control" style="width:250px;display: inline-block;">
          <%
			if(list!=null){
			  Iterator iter=list.iterator();   
			  while(iter.hasNext()){
				SysTree bean2=(SysTree)iter.next();	
				SysDepartment dept = bean2.getDepartment();
			%>
			<option value="<%=dept!=null?dept.getId():""%>">
			<%
			for(int i=1;i<bean2.getDeep();i++){
			  out.print("&nbsp;&nbsp;");
			}
			out.print(bean2.getName());
			%>
			</option>
			<%    
			  }
			}
			%>
        </select>
		<%if(bean.getDepartment() != null){%>
		<script language="javascript">								
          document.all.deptId.value="<%=bean.getDepartment().getId()%>";	
	    </script>	
		<%}%>
          </div>
     </div>
      <div class="form-group row">
       <label class="col-xs-3 " for="name" style="text-align: right;">姓　　名*</label>
          <div class="col-xs-9">
          <input name="name" type="text" size="30" class="form-control" value="<%=bean.getName()%>" datatype="string" nullable="no" maxsize="20" chname="姓名" style="width:300px;display: inline-block;">
          </div>
     </div>
      
       <div class="form-group row">
       <label class="col-xs-3 " for="code" style="text-align: right;">编　　码</label>
          <div class="col-xs-9">
          <input name="code" type="text" value="<%=bean.getCode() != null ? bean.getCode() : ""%>"  size="30" class="form-control" datatype="string" nullable="no" maxsize="20" chname="编码" style="width:300px;display: inline-block;">
          </div>
     </div>
	  
	   <div class="form-group row">
       <label class="col-xs-3 " for="code" style="text-align: right;">性　别</label>
          <div class="col-xs-9">
          <label class="radio-inline">
           <input type="radio" name="gender" value="0" <%=bean.getGender()==0?"checked":""%>>男
           </label>
           <label class="radio-inline">
          <input type="radio" name="gender" value="1" <%=bean.getGender()==1?"checked":""%>>女
          </label>
          </div>
     </div>
	  
	  <div class="form-group row">
       <label class="col-xs-3 " for="mobile" style="text-align: right;">手　　机</label>
          <div class="col-xs-9">
          <input name="mobile" type="text" size="30" class="form-control"  value="<%=bean.getMobile() != null ? bean.getMobile() :""%>" datatype="string" nullable="yes" maxsize="12" chname="手机" style="width:300px;display: inline-block;">
          </div>
     </div>
      
      <div class="form-group row">
       <label class="col-xs-3 " for="mobile" style="text-align: right;">邮　　件</label>
          <div class="col-xs-9">
           <input name="email" type="text"  value="<%=bean.getEmail() != null ? bean.getEmail() : ""%>"  size="30" class="form-control" datatype="email" nullable="yes" maxsize="50" chname="邮件" style="width:300px;display: inline-block;">
          </div>
     </div>
      
       <div class="form-group row">
       <label class="col-xs-3 " for="mobile" style="text-align: right;">办公电话</label>
          <div class="col-xs-9">
           <input name="telephone" type="text" size="30" class="form-control"  value="<%=bean.getTelephone() != null ? bean.getTelephone() : ""%>"  datatype="string" nullable="yes" maxsize="20" chname="办公电话" style="width:300px;display: inline-block;">
          </div>
     </div>
      
      <div class="form-group row">
       <label class="col-xs-3 " for="headship" style="text-align: right;">职位</label>
          <div class="col-xs-9">
          <select id="headship" name="headship">
			  <option value="">----请选择----</option>
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
       <label class="col-xs-3 " for="x_users_name" style="text-align: right;">直接上级</label>
          <div class="col-xs-9">
          <input type="hidden" id="superiorIds" name="superiorIds" value="">
          <textarea cols="40" id="x_users_name" name="x_users_name" rows="8"  wrap="yes" readonly  
             onclick="javascript:selectUser('iForm', 'superiorIds','x_users_name');" class="form-control"
		     style="height:25px;width:300px;display: inline-block;color: #000066; background: #ffffff;">${x_users_name}</textarea>
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
          $("#deadlineTime").datetimepickerExt("setValue","${bean.deadlineTime}");
          
        </script>
      </div>
    </div>
	  
	  <div class="form-group row">
       <label class="col-xs-3 " for="x_users_name" style="text-align: right;">是否有效</label>
          <div class="col-xs-9">
            <label class="radio-inline">
           <input type="radio" name="status" value="0" <%="0".equals(bean.getStatus())?"checked":""%>>是
           </label>
             <label class="radio-inline">
          <input type="radio" name="status" value="1" <%="1".equals(bean.getStatus())?"checked":""%>>否
          </label>
          </div>
     </div>
	  
     
 
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
              <input name="btn_save2" type="submit" value=" 确定 " class="btnGray btn-success">
	    </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	 <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="box">
        <td class="box-lb">&nbsp;</td>
        <td class="box-mb">&nbsp;</td>
        <td class="box-rb">&nbsp;</td>
      </tr>
    </table>
	</td>
  </tr>
</table>
</html:form>
</body>
</html>
