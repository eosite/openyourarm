<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
int parent=ParamUtil.getIntParameter(request, "parent", 0);
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
</head>

<body>
<div class="nav-title"><span class="Title" style="font-size: 13px;font-weight: bold">部门管理</span><span class="Title" style="font-size: 13px;font-weight: bold">&gt;&gt;增加部门</span></div>
<html:form action="${contextPath}/mx/sys/department/saveAdd" method="post" onsubmit="return verifyAll(this);" > 
<input type="hidden" name="parent" value="<%=parent%>">
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
       <label class="col-xs-3 " for="name" style="text-align: right;">部门名称*:</label>
          <div class="col-xs-9">
          <input name="name" type="text" size="37" class="form-control" datatype="string" nullable="no" maxsize="20"
		           style="width:300px;display: inline-block;" chname="部门名称">
          </div>
      </div>
      <!--  
      <div class="form-group row">
       <label class="col-xs-3 " for="anotherName" style="text-align: right;">部门别名*:</label>
          <div class="col-xs-9">
          <input name="anotherName" type="text" size="37" class="form-control" datatype="string" nullable="no" maxsize="20"
		           style="width:300px;display: inline-block;" chname="部门别名">
          </div>
      </div>
     
      <div class="form-group row">
       <label class="col-xs-3 " for="shortName" style="text-align: right;">部门简称:</label>
          <div class="col-xs-9">
          <input name="shortName" type="text" size="37" class="form-control" datatype="string" nullable="no" maxsize="20"
		           style="width:300px;display: inline-block;" chname="部门简称">
          </div>
       </div>
      -->
	   <div class="form-group row">
       <label class="col-xs-3 " for="desc" style="text-align: right;">描　　述:</label>
          <div class="col-xs-9">
          <textarea name="desc" cols="35" rows="8" class="form-control" datatype="string" nullable="yes" maxsize="1000"
		     style="width:300px;height:90px;display: inline-block;"
		     chname="描述"></textarea>
        
          </div>
       </div>
	  
	  <div class="form-group row">
       <label class="col-xs-3 " for="code" style="text-align: right;">代　　码*:</label>
          <div class="col-xs-9">
          <input name="code" type="text" size="37" class="form-control"  datatype="string" nullable="no" maxsize="10" 
		           style="width:300px;display: inline-block;"  chname="代码">
          </div>
       </div>
	  
	   <div class="form-group row">
       <label class="col-xs-3 " for="no" style="text-align: right;">编　　码*:</label>
          <div class="col-xs-9">
          <input name="no" type="text" size="37" class="form-control"  datatype="string" nullable="no" maxsize="10" 
		           style="width:300px;display: inline-block;" chname="编码">
          </div>
       </div>
     	<!--  
     	<div class="form-group row">
       <label class="col-xs-3 " for="code2" style="text-align: right;">部门区分*:</label>
          <div class="col-xs-9">
          <input name="code2" type="text" size="37" class="form-control"  datatype="string" nullable="no" maxsize="10" 
		           style="width:300px;display: inline-block;" chname="部门区分">
          </div>
       </div>
       -->
     	
     	<div class="form-group row">
       <label class="col-xs-3 " for="code2" style="text-align: right;">部门层级:</label>
          <div class="col-xs-9">
          <select id="level" name="level" class="form-control" style="width:300px;display: inline-block;">
			  <option value="">----请选择----</option>
			  <c:forEach items="${dictories}" var="a">
				<option value="${a.ext11}">${a.name} [${a.ext11}]</option>
			  </c:forEach>
		   </select>
          </div>
       </div>
      <!--  
     <div class="form-group row">
       <label class="col-xs-3 " for="code2" style="text-align: right;">是否正式:</label>
          <div class="col-xs-9">
          
			<select id="formalFlag" name="formalFlag" class="form-control" style="width:300px;display: inline-block;">
			  <option value="">----请选择----</option>
				<option value="1">是</option>
				<option value="0">否</option>   
		   </select>
          </div>
       </div>
     -->
	  
	 
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
              <input name="btn_save" type="submit" value="保存" class="button btn-success"></td>
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
