<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.base.project.domain.*"%>
<%@ page import="com.glaf.datamgr.domain.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
	List list = (List)request.getAttribute("projects");
	Project project = (Project)request.getAttribute("project");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目设置</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";

	function saveData(){
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/datamgr/project/saveProject',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
					   window.parent.location.reload();
					   window.close();
				   }
			 });
	}

	function saveAsData(){
		document.getElementById("id").value="";
		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/datamgr/project/saveProject',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
					   window.parent.location.reload();
					   window.close();
				   }
			 });
	}

</script>
</head>

<body>
<div style="margin:0;"></div>  

<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"> 
		<span class="x_content_title">编辑记录</span>
		<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" 
		   onclick="javascript:saveData();" >保存</a>
    </div> 
  </div>

  <div data-options="region:'center',border:false,cache:true">
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="id" name="id" value="${project.id}"/>
  <table class="easyui-form" style="width:600px;" align="left">
    <tbody>
      <tr>
        <td width="20%" class="input-box">上级节点</td>
        <td>
		  <select id="parentId" name="parentId">
           <option value="0">/</option>
          <%
			if(list!=null){
			  Iterator iter = list.iterator();   
			  while(iter.hasNext()){
				Project bean2=(Project)iter.next();
				if(project != null){
					if(project.getId() == bean2.getId() || project.getId() == bean2.getParentId()){
						continue;
					}
				}
			%>
          <option value="<%=bean2.getId()%>">
          <%
			for(int i=1;i<bean2.getLevel();i++){
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
		    document.all.parentId.value="${project.parentId}";	
	    </script>
		</td>
      </tr>

	  <tr>
		<td width="20%" align="left">名称</td>
		<td align="left">
            <input id="name" name="name" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${project.name}" size="50"/>
		   <br>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">代码</td>
		<td align="left">
            <input id="code" name="code" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${project.code}" size="50"/>
		   <br>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">主题</td>
		<td align="left">
            <input id="title" name="title" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${project.title}" size="50"/>
		</td>
	</tr>
    <tr>
		<td width="20%" align="left">类别</td>
		<td align="left">
            <select id="discriminator" name="discriminator">
				<option value="----请选择----"></option>
				<option value="P">项目</option>
				<option value="C">业主</option>
				<option value="J">监理</option>
				<option value="A">施工</option>
				<option value="O">其他</option>
            </select>
			<script language="javascript">
		      document.all.discriminator.value="${project.discriminator}";	
	        </script>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">是否启用</td>
		<td align="left">
		  <input type="radio" name="active" value="1" <c:if test="${project.active == '1'}">checked</c:if>>启用&nbsp;&nbsp;
	      <input type="radio" name="active" value="0" <c:if test="${project.active == '0'}">checked</c:if>>禁用
		</td>
	</tr>
 
    </tbody>
  </table>
 </form>
</div>
</div>
</body>
</html>