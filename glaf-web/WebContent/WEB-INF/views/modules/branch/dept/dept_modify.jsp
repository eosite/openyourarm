<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.base.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="org.apache.commons.lang3.*"%>
<%
String context = request.getContextPath();
SysDepartment bean=(SysDepartment)request.getAttribute("bean");
List  list = (List)request.getAttribute("trees");
//Set histortDeparts = bean.getHistoryDeparts();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/bootstrap/css/bootstrap.min.css" />
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script></head>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/bootstrap/js/bootstrap.min.js"></script>
<script language="javascript">
function checkForm(form){
  if(verifyAll(form)){
     if(form.parent.value=='<%=bean.getId()%>'){
	   alert("当前机构不能选择为所属机构");
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
<div class="nav-title"><span class="Title" style="font-size: 13px;font-weight: bold">机构管理</span><span class="Title" style="font-size: 13px;font-weight: bold">&gt;&gt;增加机构</span></div>
<html:form action="${contextPath}/mx/branch/department/saveModify" method="post"  onsubmit="return checkForm(this);"> 
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
       <label class="col-xs-3 " for="name" style="text-align: right;">上级机构</label>
          <div class="col-xs-9">
          
		<select name="parent" onChange="javascript:setValue(this);" class="form-control" style="width:300px;display: inline-block;">
		  <option value="">/根节点</option>
          <%
			if(list!=null){
			  Iterator iter=list.iterator();   
			  while(iter.hasNext()){
				SysTree bean2=(SysTree)iter.next();
				if(bean != null && (bean.getId() == bean2.getId() || bean.getId() == bean2.getParentId() || 
					            StringUtils.contains(bean2.getTreeId(), bean.getNode().getTreeId()))){
					continue;
				}
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
       <label class="col-xs-3 " for="password" style="text-align: right;">机构名称*</label>
          <div class="col-xs-9">
          <input name="name" type="text" size="37" class="form-control" value="${bean.name}" datatype="string" nullable="no" maxsize="200" 
		           chname="机构名称" style="width:300px;display: inline-block;">
          </div>
     </div>
   <!--
      <div class="form-group row">
       <label class="col-xs-3 " for="password" style="text-align: right;">机构别名</label>
          <div class="col-xs-9">
          <input name="anotherName" type="text" size="37" class="form-control" datatype="string" nullable="no" maxsize="20"
		         value="${bean.anotherName}"    style="width:300px;" chname="机构别名" style="width:300px;display: inline-block;">
          </div>
     </div>
      
	   <div class="form-group row">
       <label class="col-xs-3 " for="password" style="text-align: right;">机构简称</label>
          <div class="col-xs-9">
          <input name="shortName" type="text" size="37" class="form-control" datatype="string" nullable="no" maxsize="20"
		      value="${bean.shortName}"   style="width:300px;" chname="机构简称" style="width:300px;display: inline-block;">
          </div>
     </div>
	  -->
	  <div class="form-group row">
       <label class="col-xs-3 " for="desc" style="text-align: right;">描　述</label>
          <div class="col-xs-9">
          <textarea name="desc" cols="38" rows="4" class="form-control" datatype="string" nullable="yes" maxsize="1000" chname="描述" style="width:300px;display: inline-block;">${bean.desc}</textarea>
          
          </div>
     </div>
	 
	  <div class="form-group row">
       <label class="col-xs-3 " for="code" style="text-align: right;">代　码</label>
          <div class="col-xs-9">
          <input name="code" type="text" size="37" class="form-control" value="${bean.code}" datatype="string" nullable="no" maxsize="10" chname="代码" style="width:300px;display: inline-block;">
          </div>
       </div>
      
     <div class="form-group row">
       <label class="col-xs-3 " for="code" style="text-align: right;">编　码</label>
          <div class="col-xs-9">
          <input name="no" type="text" size="37" class="form-control" value="${bean.no}" datatype="string" nullable="no" maxsize="10" chname="编码" style="width:300px;display: inline-block;">
          </div>
       </div>
     
      <div class="form-group row">
       <label class="col-xs-3 " for="address" style="text-align: right;">地　址</label>
          <div class="col-xs-9">
          <input name="address" type="text" size="37" class="form-control"  value="${bean.address}" datatype="string" nullable="no" maxsize="100" chname="地址" style="width:300px;display: inline-block;">
          </div>
       </div>
      
      <div class="form-group row">
       <label class="col-xs-3 " for="telphone" style="text-align: right;">电　话</label>
          <div class="col-xs-9">
          <input name="telphone" type="text" size="37" class="form-control" value="${bean.telphone}" datatype="string" nullable="no" maxsize="100" chname="电话" style="width:300px;display: inline-block;">
          </div>
       </div>
	  
	  <div class="form-group row">
       <label class="col-xs-3 " for="principal" style="text-align: right;">负责人</label>
          <div class="col-xs-9">
          <input name="principal" type="text" size="37" class="form-control" value="${bean.principal}" datatype="string" nullable="no" maxsize="100" chname="负责人" style="width:300px;display: inline-block;">
          </div>
       </div>
	 
	 <div class="form-group row">
       <label class="col-xs-3 " for="code2" style="text-align: right;">机构区分</label>
          <div class="col-xs-9">
          <input name="code2" type="text" size="37" class="form-control" value="${bean.code2}"  datatype="string" nullable="no" maxsize="10" chname="机构区分" style="width:300px;display: inline-block;">
          </div>
       </div>
	  
       <div class="form-group row">
       <label class="col-xs-3 " for="code2" style="text-align: right;">机构层级</label>
          <div class="col-xs-9">
          <select id="level" name="level" class="form-control" style="width:300px;display: inline-block;">
			  <option value="0">----请选择----</option>
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
       <label class="col-xs-3 " for="code2" style="text-align: right;">是否正式</label>
          <div class="col-xs-9">
          <select id="level" name="level" class="form-control" style="width:300px;display: inline-block;">
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
       <label class="col-xs-3 " for="code2" style="text-align: right;">是否有效</label>
          <div class="col-xs-9">
           <label class="radio-inline">
         <input type="radio" name="status" value="0" <%=bean.getStatus()==0?"checked":""%>>
          是</label>
           <label class="radio-inline">
          <input type="radio" name="status" value="1" <%=bean.getStatus()==1?"checked":""%>>
          否</label>
          </div>
       </div>
	  
     

	  <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
              <input name="btn_save" type="submit" value=" 确定 " class="btnGray btn-success">
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
