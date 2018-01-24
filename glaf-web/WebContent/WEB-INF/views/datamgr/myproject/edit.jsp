<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.datamgr.domain.*"%>
<%@ page import="com.glaf.base.project.domain.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
        String theme = com.glaf.core.util.RequestUtils.getTheme(request);
        request.setAttribute("theme", theme);
	    List list = (List)request.getAttribute("projects");
	    Project project = (Project)request.getAttribute("project");

 		StringBuffer bufferx = new StringBuffer();
		StringBuffer buffery = new StringBuffer();
		StringBuffer bufferz = new StringBuffer();
	    List projects = (List)request.getAttribute("projects");
		//List subordinateIds = (List)request.getAttribute("subordinateIds");
        if(projects != null && projects.size() > 0){
			StringBuilder bu = new StringBuilder();
			for(int j=0; j<projects.size(); j++){
				Project d = (Project) projects.get(j);
				bu.delete(0, bu.length());
				for(int k=0; k<d.getLevel(); k++){
				       bu.append("&nbsp;&nbsp;");
				}
				if(project != null && project.getId() != d.getId()){					
					Collection<Long> subordinateIds = project.getSubordinateIds();
					if(subordinateIds != null && subordinateIds.contains(d.getId())){
					   buffery.append("\n<option value=\"").append(d.getId()).append("\">")
						   .append(bu.toString()).append(d.getName()).append(" [")
						   .append(d.getId()).append("]</option>");
					   bufferz.append(bu.toString()).append(d.getName()).append(" [")
						   .append(d.getId()).append("]  \n");
					} else {
                      bufferx.append("\n<option value=\"").append(d.getId()).append("\">")
						   .append(bu.toString()).append(d.getName()).append(" [")
						   .append(d.getId()).append("]</option>");
					}
				} else{
					bufferx.append("\n<option value=\"").append(d.getId()).append("\">")
						   .append(bu.toString()).append(d.getName()).append(" [")
						   .append(d.getId()).append("]</option>");
				}
			}
		}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分类设置</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css"/>
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script type="text/javascript">
  var contextPath="<%=request.getContextPath()%>";

  function addElement() {
        var list = document.iForm.noselected;
        for (i = 0; i < list.length; i++) {
            if (list.options[i].selected) {
                var value = list.options[i].value;
                var text = list.options[i].text;
                addToList(value, text);
				list.remove(i);
				i=i-1;
            }
        }
    }


  function addToList(value, text) {
        var list = document.iForm.selected;
        if (list.length > 0) {
            for (k = 0; k < list.length; k++) {
                if (list.options[k].value == value) {
                    return;
                }
            }
        }

        var len = list.options.length;
        list.length = len + 1;
        list.options[len].value = value;
        list.options[len].text = text;
    }


  function removeElement() {
        var list = document.iForm.selected;
		var slist = document.iForm.noselected;
        if (list.length == 0 || list.selectedIndex < 0 || list.selectedIndex >= list.options.length)
            return;

        for (i = 0; i < list.length; i++) {
            if (list.options[i].selected) {
			    var value = list.options[i].value;
                var text = list.options[i].text;
                list.options[i] = null;
                i--;
				var len = slist.options.length;
				slist.length = len+1;
                slist.options[len].value = value;
                slist.options[len].text = text;				
            }
        }
    }

	var setting = {
			async: {
				enable: true,
				url: "<%=request.getContextPath()%>/mx/my/project/projectUsersJson?projectId=${project.id}&xtype=owners",
				dataFilter: filter
			},
			check: {
				enable: true
			},
			callback: {
				onClick: zTreeOnClick
			}
		};
  
  	var setting2 = {
			async: {
				enable: true,
				url: "<%=request.getContextPath()%>/mx/my/project/projectUsersJson?projectId=${project.id}&xtype=accessors",
				dataFilter: filter
			},
			check: {
				enable: true
			},
			callback: {
				onClick: zTreeOnClick
			}
		};

  	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
            //if(childNodes[i].iconCls=='icon-user'){
			   // childNodes[i].icon="<%=request.getContextPath()%>/images/user.gif";
		    //}
		}
		return childNodes;
	}

	function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		//jQuery("#nodeId").val(treeNode.id);
		//loadData('<%=request.getContextPath()%>/mx/branch/application/json&parentId='+treeNode.id);
	}


    jQuery(document).ready(function(){
		//jQuery.fn.zTree.init(jQuery("#myTree"), setting);
		jQuery.fn.zTree.init(jQuery("#myTree2"), setting2);
	});


	function saveData(){
		/*
		var zTree = $.fn.zTree.getZTreeObj("myTree");
        var selectedNodes  = zTree.getCheckedNodes(true);

        var sx = '';  
		var code='';
        for(var i=0; i<selectedNodes.length; i++){  
            if (sx != ''){ 
				sx += ','; 
			}
			code = selectedNodes[i].name;
			code = code.substring(0, code.indexOf(" "));
            sx += code;  
        }  
        $("#owners").val(sx);
		*/

		var zTree = $.fn.zTree.getZTreeObj("myTree2");
        var selectedNodes  = zTree.getCheckedNodes(true);

        var sx = '';  
		var code='';
        for(var i=0; i<selectedNodes.length; i++){  
            if (sx != ''){ 
				sx += ','; 
			}
			code = selectedNodes[i].name;
			code = code.substring(0, code.indexOf(" "));
            sx += code;  
        }  
        $("#accessors").val(sx);

        /*
		var len= document.iForm.selected.length;
		var result = "";
		for (var i=0;i<len;i++) {
		  result = result + document.iForm.selected.options[i].value;
		  if(i < (len - 1)){
			  result = result + ",";
		   }
		}
		$("#subordinateIds").val(result);
		*/

		var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/my/project/saveProject',
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
				   url: '<%=request.getContextPath()%>/mx/my/project/saveProject',
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
					   if(data.statusCode == 200){
						   window.parent.location.reload();
					       window.close();
					   }  
				   }
			 });
	}

	function openSub(){
		var selected = jQuery("#subordinateIds").val();
        var link = '<%=request.getContextPath()%>/mx/my/project/chooseSubs?elementId=subordinateIds&elementName=subName&projectId=${project.id}&category=${category}&selected='+selected;
		var x=180;
		var y=50;
		if(is_ie) {
			x=document.body.scrollLeft+event.clientX-event.offsetX-200;
			y=document.body.scrollTop+event.clientY-event.offsetY-200;
		}
		openWindow(link,self,x, y, 725, 580);
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
  <input type="hidden" id="category" name="category" value="${category}" > 
  <input type="hidden" id="owners" name="owners">
  <input type="hidden" id="accessors" name="accessors">
  <input type="hidden" id="subordinateIds" name="subordinateIds">
  <table class="easyui-form" style="width:800px;" align="left">
    <tbody>
      <tr>
        <td width="20%" class="input-box">上级节点</td>
        <td>
		  <select id="parentId" name="parentId">
		  <%
		  //只有系统管理员可以更改根节点
		  //com.glaf.core.security.LoginContext loginContext = RequestUtils.getLoginContext(request);
		  //if (loginContext.isSystemAdministrator()) {%>
		   <!-- <option value="">----请选择----</option> -->
		   <option value="0">/根节点</option>
          <%//}
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
		    document.all.parentId.value="${parentId}";	
	    </script>
		</td>
      </tr>
	<tr>
		<td width="20%" align="left">名称</td>
		<td align="left">
            <input id="name" name="name" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${project.name}" size="80"/>
		   <br>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">代码</td>
		<td align="left">
            <input id="code" name="code" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${project.code}" size="80"/>
		   <br>
		</td>
	</tr>
	<tr>
		<td width="20%" align="left">主题</td>
		<td align="left">
            <input id="title" name="title" type="text" 
			       class="easyui-validatebox  x-text"  
				   value="${project.title}" size="80"/>
		</td>
	</tr>
    <tr>
		<td width="20%" align="left">类别</td>
		<td align="left">
            <select id="discriminator" name="discriminator">
				<option value="">----请选择----</option>
				<option value="P">分类</option>
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
		<td width="20%" align="left">区域</td>
		<td align="left">
            <select id="area" name="area">
				<option value="">----请选择----</option>
				<c:forEach items="${dicts}" var="dict">
				    <option value="${dict.value}">${dict.name}</option>
                </c:forEach>
            </select>
			<script language="javascript">
		      document.all.area.value="${project.area}";	
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

	<tr>
		<td width="20%" valign="middle" >从属节点</td>
		<td width="80%" align="left"  >
            <textarea id="subName" name="subName" rows="8" cols="46" style="width:580px" 
			          onclick="javascript:openSub();" readonly><%=bufferz.toString()%></textarea> 
			&nbsp; 
			<a href="#" onclick="javascript:openSub();">
			  <img src="<%=request.getContextPath()%>/images/search_results.gif" border="0">
		    </a>&nbsp;&nbsp;  
		</td>
	</tr>

	<tr>
		<td width="20%" align="left" valign="top">授权</td>
		<td align="left" valign="top">
		  <ul id="myTree2" class="ztree"></ul>
		</td>
	</tr>
	<tr>
	 <td colspan="2"><br><br><br><br><br><br><br><br></td>
	</tr>
	<!-- <tr>
		<td width="100%" align="center" colspan="2">
			<table>
				<tr>
					<td width="50%">管理者（该节点拥有管理权限的用户）</td>
					<td width="50%">授权（授权用户具有该节点数据权限）</td>
				</tr>
				<tr>
					<td width="50%" valign="top">
					    <ul id="myTree" class="ztree"></ul>
					</td>
					<td width="50%" valign="top">
					    <ul id="myTree2" class="ztree"></ul>
					</td>
				</tr>
			</table>
        </td>
	</tr> -->
    </tbody>
  </table>
 </form>
</div>
</div>

</body>
</html>