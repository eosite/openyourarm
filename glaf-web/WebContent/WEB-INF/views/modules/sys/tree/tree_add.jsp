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
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="<%=context%>/css/core.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
</head>
<body style="margin:10px;">
<html:form action="${contextPath}/mx/sys/tree/saveAdd" method="post" onsubmit="return verifyAll(this);" > 
<input type="hidden" name="parent" value="<%=parent%>">
 <table width="95%" align="center" border="0" cellspacing="1" cellpadding="5">
      <tr>
        <td class="input-box">名　　称*</td>
        <td><input name="name" type="text" size="40" class="easyui-validatebox" datatype="string" nullable="no" maxsize="30" chname="名称" data-options="required:true"></td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">编　　码*</td>
        <td><input name="code" type="text" size="40" class="easyui-validatebox" value=""  datatype="string" nullable="no" maxsize="20" chname="编码" data-options="required:true"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="middle">描　　述</td>
        <td><textarea name="desc" cols="30" rows="6" class="easyui-validatebox" datatype="string" nullable="yes" maxsize="100" chname="描述"></textarea></td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">允许单个文件大小</td>
        <td>
		  <select id="allowedFizeSize" name="allowedFizeSize">
			<option value="0">----请选择------</option>
			<%for(int i=50; i>0; i--){%>
			<option value="<%=i%>"><%=i%>MB</option>
			<%}%>
		  </select>&nbsp;（不设置使用系统默认值。）
        </td>
      </tr>
	  <tr>
        <td class="input-box2" valign="middle">允许文件列表</td>
        <td>
		<textarea id="allowedFileExts" name="allowedFileExts" cols="30" rows="6" class="easyui-validatebox" datatype="string" nullable="yes" maxsize="200" chname="允许文件列表"></textarea>
		<br>（允许多个文件扩展名，用半角的逗号“,”分隔。）
		</td>
      </tr>
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
            <input name="btn_save" type="submit" value="保存" class="btn btnGray">
		</td>
      </tr>
    </table> 
</html:form>
</body>
</html>
