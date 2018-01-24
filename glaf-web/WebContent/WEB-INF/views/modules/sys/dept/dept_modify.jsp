<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
SysDepartment bean=(SysDepartment)request.getAttribute("bean");
List  list = (List)request.getAttribute("parent");
//Set histortDeparts = bean.getHistoryDeparts();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/bootstrap/css/bootstrap.min.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/bootstrap/js/bootstrap.min.js"></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script></head>
<script language="javascript">
function checkForm(form){
  if(verifyAll(form)){
     if(form.parent.value=='<%=bean.getId()%>'){
	   alert("当前部门不能选择为所属部门");
	 }else{
	   selectCB("historyId");
	   return true;
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
<div class="nav-title"><span class="Title" >部门管理</span>&gt;&gt;修改部门</div>
<html:form action="${contextPath}/mx/sys/department/saveModify" method="post"  onsubmit="return checkForm(this);"> 
<input type="hidden" name="id" value="<%=bean.getId()%>">
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
    <td>
	<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
	
	
		 <div class="form-group row">
       <label class="col-xs-3 " for="name" style="text-align: right;">上级部门:</label>
          <div class="col-xs-9">
          <select name="parent" onChange="javascript:setValue(this);" class="form-control"  style="width:300px;display: inline-block;">
          <%
			if(list!=null){
			  Iterator iter=list.iterator();   
			  while(iter.hasNext()){
				SysTree bean2=(SysTree)iter.next();
			%>
					  <option value="<%=bean2.getId()%>">
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
		  document.all.parent.value="<%=bean.getNode().getParentId()%>";
	    </script>		
          </div>
      </div>
	
		<div class="form-group row">
       <label class="col-xs-3 " for="name" style="text-align: right;">部门名称*:</label>
          <div class="col-xs-9">
          <input name="name" type="text" size="37" class="form-control" value="<%=bean.getName()%>" datatype="string" nullable="no" maxsize="20"
		           style="width:300px;display: inline-block;" chname="部门名称">
          </div>
       </div>
	
      <div class="form-group row">
       <label class="col-xs-3 " for="anotherName" style="text-align: right;">部门别名*:</label>
          <div class="col-xs-9">
          <input name="anotherName" type="text" size="37"  value="<%=bean.getAnotherName() != null ? bean.getAnotherName() : ""%>"  class="form-control" datatype="string" nullable="no" maxsize="20"
		           style="width:300px;display: inline-block;" chname="部门别名">
          </div>
      </div>
      
	  <div class="form-group row">
       <label class="col-xs-3 " for="shortName" style="text-align: right;">部门简称:</label>
          <div class="col-xs-9">
          <input name="shortName" type="text" size="37" value="<%=bean.getShortName() != null ? bean.getShortName() : ""%>"  class="form-control" datatype="string" nullable="no" maxsize="20"
		           style="width:300px;display: inline-block;" chname="部门简称">
          </div>
       </div>
	  
	  <div class="form-group row">
       <label class="col-xs-3 " for="desc" style="text-align: right;">描　　述:</label>
          <div class="col-xs-9">
          <textarea name="desc" cols="35" rows="8" class="form-control" datatype="string" nullable="yes" maxsize="1000"
		     style="width:300px;height:90px;display: inline-block;"
		     chname="描述"><%=bean.getDesc() != null ? bean.getDesc() : ""%></textarea>
        
          </div>
       </div>
      
      <div class="form-group row">
       <label class="col-xs-3 " for="code" style="text-align: right;">代　　码*:</label>
          <div class="col-xs-9">
          <input name="code" type="text" size="37"  value="<%=bean.getCode() != null ? bean.getCode() : ""%>" class="form-control"  datatype="string" nullable="no" maxsize="10" 
		           style="width:300px;display: inline-block;"  chname="代码">
          </div>
       </div>
     
      <div class="form-group row">
       <label class="col-xs-3 " for="no" style="text-align: right;">编　　码*:</label>
          <div class="col-xs-9">
          <input name="no" type="text" size="37" class="form-control" value="<%=bean.getNo() != null ? bean.getNo() : ""%>" datatype="string" nullable="no" maxsize="10" 
		           style="width:300px;display: inline-block;" chname="编码">
          </div>
       </div>
     
     <div class="form-group row">
       <label class="col-xs-3 " for="code2" style="text-align: right;">部门区分*:</label>
          <div class="col-xs-9">
          <input name="code2" type="text" size="37" class="form-control"  value="<%=bean.getCode2() != null ? bean.getCode2() : ""%>" datatype="string" nullable="no" maxsize="10" 
		           style="width:300px;display: inline-block;" chname="部门区分">
          </div>
       </div>
     
     <div class="form-group row">
       <label class="col-xs-3 " for="code2" style="text-align: right;">部门层级:</label>
          <div class="col-xs-9">
          <select id="level" name="level" class="form-control" style="width:300px;display: inline-block;">
			  <option value="">----请选择----</option>
			  <c:forEach items="${dictories}" var="a">
				<option value="${a.ext11}">${a.name} [${a.ext11}]</option>
			  </c:forEach>
		   </select>
		    <script type="text/javascript">
		        document.getElementById("level").value="${bean.level}";
		   </script>
          </div>
       </div>
     
	  <div class="form-group row">
       <label class="col-xs-3 " for="formalFlag" style="text-align: right;">是否正式:</label>
          <div class="col-xs-9">
          
			<select id="formalFlag" name="formalFlag" class="form-control" style="width:300px;display: inline-block;">
			  <option value="">----请选择----</option>
				<option value="1">是</option>
				<option value="0">否</option>   
		   </select>
		    <script type="text/javascript">
		        document.getElementById("formalFlag").value="${bean.formalFlag}";
		   </script>
          </div>
       </div>
	
	
	<div class="form-group row">
       <label class="col-xs-3 " for="code2" style="text-align: right;">是否有效*:</label>
          <div class="col-xs-9">
           <label class="radio-inline">
			<input type="radio" name="status" value="0" <%=bean.getStatus()==0?"checked":""%>>是
			</label>
			 <label class="radio-inline">
          <input type="radio" name="status" value="1" <%=bean.getStatus()==1?"checked":""%>>否
          </label>
          </div>
       </div>
	
	

	  <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
              <input name="btn_save" type="submit" value="保存" class="button btn-success">
		</td>
      </tr>
    </table>
   </td>
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
<script language="javascript">
attachFrame();
</script>
</html:form> 
</body>
</html>
