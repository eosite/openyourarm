<%@page import="com.glaf.form.core.domain.FormDictTree"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%
	String context = request.getContextPath();
	FormDictTree bean=(FormDictTree)request.getAttribute("bean");
	List list = (List)request.getAttribute("parent");
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
<script language="javascript">
function checkForm(form){
  if(verifyAll(form)){
     if(form.parent.value=='<%=bean.getId()%>'){
	   alert("当前节点不能选择为所属节点");
	 }else{
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

<body style="margin:10px;">
<html:form action="${contextPath}/mx/form/formDictTree/saveModify" method="post"  onsubmit="return checkForm(this);"> 
<input type="hidden" name="id" value="<%=bean.getId()%>">
 <table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
      <tr>
        <td class="input-box">上级节点</td>
        <td><select name="parent" onChange="javascript:setValue(this);">
          <%
			if(list!=null){
			  Iterator iter=list.iterator();   
			  while(iter.hasNext()){
				  FormDictTree bean2=(FormDictTree)iter.next();
			%>
          <option value="<%=bean2.getId()%>" <%=bean2.getId()==bean.getParentId()?"selected":"" %>>
          <%
			for(int i=1;i<bean2.getDeep();i++){
			  out.print("&nbsp;&nbsp;　");
			}
			out.print(bean2.getName());
			%>
          </option>
          <%    
           }
         }
        %>
        </select>
		</td>
      </tr>
      <tr>
        <td class="input-box">名　　称*</td>
        <td><input name="name" type="text" size="40" class="easyui-validatebox" value="<%=bean.getName()%>" datatype="string" nullable="no" maxsize="30" chname="名称" data-options="required:true"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">编　　码*</td>
        <td><input name="code" type="text" size="40" class="easyui-validatebox" value="<%=bean.getCode() != null ? bean.getCode() : ""%>"  datatype="string" nullable="no" maxsize="20" chname="编码" data-options="required:true"></td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">排序号*</td>
        <td>
		<select id="sort" name="sort">
			<option value="100">----请选择----</option>
			<%for(int i=0;i<99;i++){%>
			<option value="<%=i%>"><%=i%></option>
			<%}%>
		</select>
		<script type="text/javascript">
		   document.getElementById("sort").value="<%=bean.getSort()%>";
		</script>
		&nbsp;(提示：顺序小的排在前面)
		</td>
      </tr>
	  <tr>
        <td class="input-box2" valign="middle">描　　述</td>
        <td><textarea name="desc" cols="30" rows="6" class="easyui-validatebox" datatype="string" nullable="yes" maxsize="100" chname="描述"><%=bean.getDesc() != null ? bean.getDesc(): ""%></textarea></td>
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
