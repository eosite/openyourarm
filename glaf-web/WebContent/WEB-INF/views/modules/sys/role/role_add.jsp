<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
List  list = (List)request.getAttribute("parent");
int parent=ParamUtil.getIntParameter(request, "parent", 0);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/bootstrap/css/bootstrap.min.css" />
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script></head>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/bootstrap/js/bootstrap.min.js"></script>

<body>
<html:form action="${contextPath}/mx/sys/role/saveAdd" method="post" onsubmit="return verifyAll(this);" >
<div class="nav-title"><span class="Title" style="font-size: 13px;font-weight: bold">角色管理</span><span class="Title" style="font-size: 13px;font-weight: bold">&gt;&gt;增加角色</span></div>
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
       <label class="col-xs-3 " for="name" style="text-align: right;">角色名称*:</label>
          <div class="col-xs-9">
          <input name="name" type="text" size="35" class="form-control" datatype="string" nullable="no" maxsize="20" chname="角色名称"   style="width:300px;display: inline-block;">
          </div>
      </div>
      
      <div class="form-group row">
       <label class="col-xs-3 " for="code" style="text-align: right;">代码*:</label>
          <div class="col-xs-9">
          <input name="code" type="text" size="35" class="form-control" datatype="string" nullable="no" maxsize="20" chname="代码"   style="width:300px;display: inline-block;">
          </div>
      </div>
      
      <div class="form-group row">
       <label class="col-xs-3 " for="code" style="text-align: right;">顺序:</label>
          <div class="col-xs-9">
          <select id="sort" name="sort"  class="form-control" style="width:300px;">
			    <option value="0" selected>----请选择----</option>
			    <%
				 for(int i=1;i<100;i++){
				%>
				<option value="<%=i%>"><%=i%></option>
				<%}%>
		    </select>
			&nbsp;（顺序小的排在前面）
          </div>
      </div>
      
	  <div class="form-group row">
       <label class="col-xs-3 " for="content" style="text-align: right;">描　　述:</label>
          <div class="col-xs-9">
          <textarea name="content" cols="40" rows="3" class="form-control"  style="width:300px;height:90px;"
		   nullable="yes" maxsize="100" chname="描述"></textarea>
          </div>
      </div>
	  
       <div class="form-group row">
       <label class="col-xs-3 " for="content" style="text-align: right;">首页链接:</label>
          <div class="col-xs-9">
          <textarea name="indexUrl" cols="40" rows="5" class="form-control"  style="width:300px;height:90px;"
		   nullable="yes" maxsize="100" chname="首页链接"></textarea>
          </div>
      </div>
      
      <div class="form-group row">
       <label class="col-xs-3 " for="isUseBranch" style="text-align: right;">是否开放分级管理:</label>
          <div class="col-xs-9">
          <select id="isUseBranch" name="isUseBranch" class="form-control"  style="width:300px;">
			    <option value="" selected>----请选择----</option>
			    <option value="Y">开放</option>
				<option value="N">不开放</option>
		    </select>
          </div>
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
