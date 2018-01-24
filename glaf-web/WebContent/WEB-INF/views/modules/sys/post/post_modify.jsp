<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
Post bean=(Post)request.getAttribute("bean");
List  list = (List)request.getAttribute("parent");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script></head>

<body>
<html:form action="${contextPath}/mx/sys/post/saveModify" method="post"  onsubmit="return verifyAll(this);">
<input type="hidden" name="id" value="<%=bean.getId()%>">
<div class="nav-title"><span class="Title">岗位管理</span>&gt;&gt;修改岗位</div>
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
    <td class="box-mm"><table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
      <tr>
        <td class="input-box">岗位名称*</td>
        <td><input name="name" type="text" size="35" class="input" datatype="string" nullable="no" maxsize="20" chname="岗位名称" value="<%=bean.getName()%>"></td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">代码*</td>
        <td><input name="code" type="text" size="35" value="<%=bean.getCode() != null ? bean.getCode() : ""%>" class="input" datatype="string" nullable="no" maxsize="20" chname="代码"></td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">顺序</td>
        <td>
		    <select id="sort" name="sort">
			    <option value="0" selected>----请选择----</option>
			    <%
				 for(int i=1;i<100;i++){
				%>
				<option value="<%=i%>"><%=i%></option>
				<%}%>
		    </select>
			<script type="text/javascript">
			   document.getElementById("sort").value="<%=bean.getSort()%>";
			</script>
			&nbsp;（顺序小的排在前面）
        </td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">描　　述</td>
        <td>
		<textarea name="content" cols="40" rows="3" class="input-multi" datatype="string" nullable="yes" maxsize="100" chname="描述"><%=bean.getContent() != null ? bean.getContent() : ""%></textarea>        
		</td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">是否开放分级管理</td>
        <td>
		    <select id="isUseBranch" name="isUseBranch">
			    <option value="">----请选择----</option>
			    <option value="Y">开放</option>
				<option value="N">不开放</option>
		    </select>
			<script type="text/javascript">
			     document.getElementById("isUseBranch").value="<%=bean.getIsUseBranch() != null ? bean.getIsUseBranch() : ""%>";
			</script>
        </td>
      </tr>
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
              <input name="btn_save" type="submit" value="保存" class="button"></td>
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
