<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
SysUser bean=(SysUser)request.getAttribute("bean");
List  list = (List)request.getAttribute("parent");
int nodeId=ParamUtil.getIntParameter(request, "nodeId", 0);
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
</head>
<body>
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
       <label class="col-xs-3 " for="name" style="text-align: right;">用户名*</label>
          <div class="col-xs-9">
          <%=bean.getActorId()%>
          </div>
      </div>
      
       <div class="form-group row">
       <label class="col-xs-3 " for="name" style="text-align: right;">部门</label>
          <div class="col-xs-9">
          <select name="parent" onchange="javascript:setValue(this);" class="form-control" style="width:200px;display: inline-block;">
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
		<script language="javascript">								
          document.all.parent.value="<%=bean.getDepartment() !=null ? bean.getDepartment().getId():""%>";	
	    </script>		
          </div>
      </div>
      
      <div class="form-group row">
       <label class="col-xs-3 " for="name" style="text-align: right;">姓　名</label>
          <div class="col-xs-9">
          <input name="name" type="text" size="28" class="form-control" value="<%=bean.getName()%>" datatype="string" nullable="no" maxsize="20" chname="姓名" style="width:200px;display: inline-block;">
          </div>
      </div>
      
      <div class="form-group row">
       <label class="col-xs-3 " for="mobile" style="text-align: right;">手　机</label>
          <div class="col-xs-9">
           <input name="mobile" type="text" size="30" class="form-control" datatype="string" 
		         value="<%=bean.getMobile() != null ? bean.getMobile() :""%>" 
			     nullable="yes" maxsize="12" chname="手机" style="width:200px;display: inline-block;">
          </div>
      </div>
      
      <div class="form-group row">
       <label class="col-xs-3 " for="email" style="text-align: right;">邮　件</label>
          <div class="col-xs-9">
          <input name="email" type="text" size="30" class="form-control" datatype="email" 
		         value="<%=bean.getMail() != null ? bean.getMail() : ""%>" 
				 nullable="yes" maxsize="50" chname="邮件" style="width:200px;display: inline-block;"> 
           
          </div>
      </div>
      
      <div class="form-group row">
       <label class="col-xs-3 " for="telephone" style="text-align: right;">办公电话</label>
          <div class="col-xs-9">
		 <input name="telephone" type="text" size="30" class="form-control" datatype="string" 
		         value="<%=bean.getTelephone() != null ? bean.getTelephone() : ""%>" 
				 nullable="yes" maxsize="20" chname="办公电话" style="width:200px;display: inline-block;">   
           
          </div>
      </div>
     
     
      
      
      
	  <!-- <tr>
        <td class="input-box2" valign="top">职　位</td>
        <td>
		    <select id="headship" name="headship">
			  <option value="0">----请选择----</option>
			  <c:forEach items="${dictories}" var="a">
				<option value="${a.code}">${a.name} [${a.code}]</option>
			  </c:forEach>
		   </select>
		   <script type="text/javascript">
		        document.getElementById("headship").value="${bean.headship}";
		   </script>
		</td>
      </tr> -->
      <div class="form-group row">
       <label class="col-xs-3 " for="accountType" style="text-align: right;">帐户类型</label>
          <div class="col-xs-9">
          <select id="accountType" name="accountType" class="form-control" style="width:200px;display: inline-block;" >
			  <option value="0">系统用户</option>
			  <option value="1">供应商</option>
			  <option value="2">微信用户</option>
		   </select>
		   <script type="text/javascript">
		        document.getElementById("accountType").value="${bean.accountType}";
		   </script>
          </div>
      </div>
      
	   <div class="form-group row">
       <label class="col-xs-3 " for="accountType" style="text-align: right;">是否有效</label>
          <div class="col-xs-9">
           <label class="radio-inline">
          <input type="radio" name="blocked" value="0" <%=bean.getLocked()==0?"checked":""%>>是
          </label>
           <label class="radio-inline">
          <input type="radio" name="blocked" value="1" <%=bean.getLocked()==1?"checked":""%>>否
          </label>
          </div>
      </div>
 
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

</body>
</html>
