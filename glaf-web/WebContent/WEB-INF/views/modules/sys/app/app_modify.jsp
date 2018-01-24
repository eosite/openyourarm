<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
    String context = request.getContextPath();
    SysApplication bean=(SysApplication)request.getAttribute("bean");
    List  list = (List)request.getAttribute("parent");
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
	request.setAttribute("contextPath", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/bootstrap/css/bootstrap.min.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script type="text/javascript" src='<%=context%>/scripts/main.js'></script>
<script type="text/javascript" src='<%=context%>/scripts/verify.js'></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script language="javascript">

    function changeXDiv(state){
		if(state=='L'){
			jQuery("#urlDir").show();
			jQuery("#divDir").hide();
			document.getElementById("type").value = state;
		}
		if(state=='T'){
			jQuery("#divDir").show();
			jQuery("#urlDir").hide();
			document.getElementById("type").value = state;
		}
	}

	function checkForm(form){
	  if(verifyAll(form)){
		 if(form.parent.value=='<%=bean.getId()%>'){
		   alert("当前模块不能选择为所属模块");
		 } else{
		   return true;
		 }
	  }
	   return false;
	}

	function setValue(obj){
	  obj.value=obj[obj.selectedIndex].value;
	}

    function verifyApplicationForm(form){
	   if(checkForm(form)){
		  var type = document.getElementById("type").value;
		  if("L" == type){
			  //var link = document.getElementById("url").value;
			  //if(link == ""){
			  //	alert("链接地址是必须的！");
			  //	document.getElementById("url").focus();
			  //	return false;
			 // }
		  }

		  if("T" == type){
			  var content = document.getElementById("linkFileName").value;
			  if(content == ""){
				//alert("文件是必须的！");
				//document.getElementById("linkFileName").focus();
				//return false;
			  }
		  }
	    }
    }

 function chooseCellTreedot(formName, elementId, elementName){
    var x_selected =  document.getElementById(elementId);
    var url="<%=request.getContextPath()%>/mx/cell/treedot/choose?formName="+formName+"&elementId="+elementId+"&elementName="+elementName;
    if(x_selected != null && x_selected.value != ""){
	    url = url + "&selecteds="+x_selected.value;
    }
    var x=150;
    var y=50;
    if(is_ie) {
	    x=document.body.scrollLeft+event.clientX-event.offsetX-200;
	    y=document.body.scrollTop+event.clientY-event.offsetY-200;
     }
     openWindow(url,self,x, y, 565, 600);
}


   function chooseTreeProjectInfo(formName, elementId, elementName){
    var x_selected =  document.getElementById("refId1");
    var url="<%=request.getContextPath()%>/mx/treeProjectInfo/choose?formName="+formName+"&elementId="+elementId+"&elementName="+elementName;
    if(x_selected != null && x_selected.value != ""){
	    url = url + "&strfuntion="+x_selected.value;
    }
    var x=150;
    var y=50;
    if(is_ie) {
	    x=document.body.scrollLeft+event.clientX-event.offsetX-200;
	    y=document.body.scrollTop+event.clientY-event.offsetY-200;
     }
     openWindow(url,self,x, y, 565, 600);
  }

  function chooseWPFMenu(formName, elementId, elementName){
    var x_selected =  document.getElementById("databaseId");
    var url="<%=request.getContextPath()%>/mx/wpf/form/config?formName="+formName+"&elementId="+elementId+"&elementName="+elementName+"&databaseId=<%=bean.getDatabaseId() != null ? bean.getDatabaseId() : ""%>";
    if(x_selected != null && x_selected.value != ""){
	    url = url + "&strfuntion="+x_selected.value;
    }
    var x=150;
    var y=50;
    if(is_ie) {
	    x=document.body.scrollLeft+event.clientX-event.offsetX-200;
	    y=document.body.scrollTop+event.clientY-event.offsetY-200;
     }
     openWindow(url,self,x, y, 565, 600);
  }

  function chooseIsdpWPFMenu(formName, elementId, elementName){
    var x_selected =  document.getElementById("databaseId");
    var url="<%=request.getContextPath()%>/mx/form/treedot?formName="+formName+"&elementId="+elementId+"&elementName="+elementName+"&databaseId=<%=bean.getDatabaseId() != null ? bean.getDatabaseId() : ""%>";
    if(x_selected != null && x_selected.value != ""){
	    url = url + "&strfuntion="+x_selected.value;
    }
    var x=150;
    var y=50;
    if(is_ie) {
	    x=document.body.scrollLeft+event.clientX-event.offsetX-200;
	    y=document.body.scrollTop+event.clientY-event.offsetY-200;
     }
     openWindow(url,self,x, y, 565, 600);
  }

  function chooseMenu(formName, elementId, elementName){
    var x_selected =  document.getElementById("databaseId");
    var url="<%=request.getContextPath()%>/mx/form/def?formName="+formName+"&elementId="+elementId+"&elementName="+elementName+"&databaseId=<%=bean.getDatabaseId() != null ? bean.getDatabaseId() : ""%>";
    if(x_selected != null && x_selected.value != ""){
	    url = url + "&strfuntion="+x_selected.value;
    }
    var x=150;
    var y=50;
    if(is_ie) {
	    x=document.body.scrollLeft+event.clientX-event.offsetX-200;
	    y=document.body.scrollTop+event.clientY-event.offsetY-200;
     }
     openWindow(url,self,x, y, 565, 600);
  }

  
  function chooseImg(){ 
	   var link="<%=request.getContextPath()%>/mx/image/choose?elementId=imagePath"; 
	   openWindow(link, self, 50, 50, 1098, 520); 
  } 
</script>
</head>

<body style="margin:10px;">
<html:form action="${contextPath}/mx/sys/application/saveModify" method="post" enctype="multipart/form-data" 
           onsubmit="return verifyApplicationForm(this);"> 
<div class="panel anel-primary" style="width:550px;padding:0px;background-color:#CCCCFF">
 <div class="panel-heading">
        <h3 class="panel-title">修改模块</h3>
    </div>
<div class="panel-body">
<input type="hidden" id="id" name="id" value="<%=bean.getId()%>">
<input type="hidden" id="databaseId" name="databaseId" value="<%=bean.getDatabaseId() !=null ? bean.getDatabaseId() : ""%>">
<input type="hidden" id="systemFlag" name="systemFlag" value="<%=bean.getSystemFlag() !=null ? bean.getSystemFlag() : ""%>">
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">


<div class="form-group row">
    <label class="col-xs-3 " for="name">上级模块</label>
     <div class="col-xs-9">
     <select name="parent" onChange="javascript:setValue(this);" class="form-control" style="width:300px;display: inline-block;">
          <%
			if(list!=null){
			  Iterator iter=list.iterator();   
			  while(iter.hasNext()){
				SysTree bean2=(SysTree)iter.next();
			%>
					  <option value="<%=bean2.getId()%>">
			<%
			for(int i=0;i<bean2.getDeep();i++){
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
    <label class="col-xs-3 " for="name">名　　称*</label>
     <div class="col-xs-9">
     <input id="name" name="name" type="text" class="input easyui-validatebox" value="<%=bean.getName()%>" size="35" datatype="string" nullable="no" maxsize="20" chname="名称" data-options="required:true" style="width:300px;display: inline-block;">
     </div>
   </div>
     
    <div class="form-group row">
    <label class="col-xs-3" for="code">编　　码</label>
     <div class="col-xs-9">
     <input id="code" name="code" type="text" value="<%=bean.getCode()!=null?bean.getCode():""%>" size="35" class="form-control" datatype="string" nullable="yes" maxsize="50" chname="代码"  style="width:300px;display: inline-block;">
     </div>
    </div>
    
	  <div class="form-group row">
    	<label class="col-xs-3" for="desc">描　　述</label>
    	<div class="col-xs-9">
    	<textarea name="desc" cols="42" rows="4" class="form-control" datatype="string" nullable="yes" maxsize="100" chname="描述" style="width:300px;height:90px;"
		><%=bean.getDesc() != null ? bean.getDesc() : ""%></textarea>
    	</div>
   	</div>
	  
     <div class="form-group row">
    	<label class="col-xs-3" for="type">链接类型</label>
    	<div class="col-xs-9">
    	<c:if test="${bean.linkType == 'L'}">checked</c:if>链接地址
			<!-- <input id="type" name="type" type="radio" value="T" onclick="javascript:changeXDiv('T');" 
			<c:if test="${bean.linkType == 'T'}">checked</c:if> />链接文件 -->
			<br>
			<div id="urlDir" style="display:block;">
		      <textarea  id="url" name="url" cols="42" rows="4" class="input-multi" datatype="string" nullable="yes"
			        style="width:300px;height:90px;"
			        maxsize="100" chname="链接"><%=bean.getUrl() != null ? bean.getUrl() :""%></textarea>
			</div>
			<div id="divDir" style="display:none;">
		        <input type="file" id="linkFileName" name="linkFileName"  class="input " size="35">
				<c:if test="${bean.linkType == 'T'}">
				<br><br>如需更换链接文件<a href="<%=request.getContextPath()%>/mx/lob/lob/download?fileId=${bean.linkFileId}" target="newFrame">${bean.linkFileName}</a>，请重新上传。
                </c:if>
				<iframe id="newFrame" name="newFrame" width="0" height="0"></iframe>
				<br>链接参数<br>
				<textarea id="linkParam" name="linkParam" cols="42" rows="4" class="form-control " datatype="string" nullable="yes" maxsize="100" chname="链接参数"><%=bean.getLinkParam() != null ? bean.getLinkParam() :""%></textarea>
			</div>
    	</div>
   	</div>
   	
	 <div class="form-group row">
    	<label class="col-xs-3" for="printFileName">打印模板</label>
    	<div class="col-xs-9">
    	<div id="divDir2" style="display:block;">
		        <div id="divDir2" style="display:block;">
		        <input type="file" id="printFileName" name="printFileName" class="form-control" size="35" style="width:300px;display: inline-block;">
				<c:if test="${bean.printType == 'T'}">
				<br><br>如需更换链接文件<a href="<%=request.getContextPath()%>/mx/lob/lob/download?fileId=${bean.printFileId}" target="newFrame">${bean.printFileName}</a>，请重新上传。
                </c:if>
				<iframe id="newFrame" name="newFrame" width="0" height="0"></iframe>
				<br>链接参数<br>
				<textarea id="printParam" name="printParam" cols="42" rows="4" class="form-control" datatype="string" 
				      style="width:300px;height:90px;"
				      nullable="yes" maxsize="100" chname="链接参数"><%=bean.getPrintParam() != null ? bean.getPrintParam() :""%></textarea>
			   </div>
			</div>
    	</div>
   	</div>
   	
   	
   	<div class="form-group row">
    	<label class="col-xs-3" for="refId1">关联菜单</label>
    	<div class="col-xs-9">
    	<input type="hidden" id="refId1" name="refId1" value="<%=bean.getRefId1()!=null?bean.getRefId1():""%>">
           <input type="text" id="refName1" name="refName1" size="35" class="form-control "  style="width:300px;display: inline-block;"
		          value="<%=bean.getRefName1()!=null?bean.getRefName1():""%>"
				  onclick="javascript:chooseCellTreedot('iForm', 'refId1', 'refName1');">
    	</div>
   	</div>
	 
      
	  <!-- <tr>
        <td class="input-box2" valign="top">关联工程信息</td>
        <td>
           <input type="hidden" id="refId2" name="refId2" value="<%=bean.getRefId2()!=null?bean.getRefId2():""%>">
           <input type="text" id="refName2" name="refName2" size="35" class="input "
		          value="<%=bean.getRefName2()!=null?bean.getRefName2():""%>"
				  onclick="javascript:chooseTreeProjectInfo('iForm', 'refId2', 'refName2');">
		</td>
      </tr> -->
	
	
	<div class="form-group row">
    	<label class="col-xs-3" for="uid">关联WPF界面</label>
    	<div class="col-xs-9">
    	<input type="hidden" id="uid" name="uid" value="<%=bean.getUid()!=null?bean.getUid():""%>">
           <input type="text" id="refName2" name="refName2" size="35"   style="width:300px;display: inline-block;"
		          value="<%=bean.getRefName2()!=null?bean.getRefName2():""%>" class="form-control "
				  onclick="javascript:chooseWPFMenu('iForm', 'uid', 'refName2');">
    	</div>
   	</div>
	
	  <div class="form-group row">
    	<label class="col-xs-3" for="uid">关联ISDP界面</label>
    	<div class="col-xs-9">
    	<input type="hidden" id="cellTreedotIndex" name="cellTreedotIndex" 
		          value="<%=bean.getCellTreedotIndex()!=null ? bean.getCellTreedotIndex():""%>">
           <input type="text" id="refName5" name="refName5" size="35"  style="width:300px;display: inline-block;"
		          value="<%=bean.getRefName5()!=null ? bean.getRefName5():""%>" class="form-control "
				  onclick="javascript:chooseIsdpWPFMenu('iForm', 'cellTreedotIndex', 'refName5');">
    	</div>
   	</div>

	   <div class="form-group row">
    	<label class="col-xs-3" for="position">位置</label>
    	<div class="col-xs-9">
    	<input id="position" name="position" type="text" size="35" class="form-control" datatype="string" nullable="yes" maxsize="20" chname="位置" value="<%=bean.getPosition()!=null ? bean.getPosition():""%>"  style="width:300px;display: inline-block;"" >
    	</div>
   	   </div>

	  <div class="form-group row">
    	<label class="col-xs-3" for="pageId">关联定义平台界面</label>
    	<div class="col-xs-9">
    	<input type="hidden" id="pageId" name="pageId" 
		          value="<%=bean.getPageId() != null? bean.getPageId() : ""%>">
           <input type="text" id="refName3" name="refName3" size="35"   style="width:300px;display: inline-block;"
		          value="<%=bean.getRefName3()!=null?bean.getRefName3():""%>" class="form-control"
				  onclick="javascript:chooseMenu('iForm', 'pageId', 'refName3');">
    	</div>
   	 </div>

	   <div class="form-group row">
    	<label class="col-xs-3" for="showType">显示类型</label>
    	<div class="col-xs-9">
    	<select id="showType" name="showType" class="form-control" style="width:300px;display: inline-block;">
			<option value="A">前后端均显示</option>
			<option value="B">仅后端显示</option>
			<option value="F">仅前端显示</option>
           </select>
           <script type="text/javascript">
                document.getElementById("showType").value="${bean.showType}";
           </script>
    	</div>
   	 </div>

      <div class="form-group row">
    	<label class="col-xs-3" for="radio">菜单图标</label>
    	<div class="col-xs-9">
    	<input type="text" id="imagePath" name="imagePath"  class="form-control" size="35"  style="width:300px;display: inline-block;"
		         value="<%=bean.getImagePath() != null ? bean.getImagePath() :""%>" 
				 onclick="javascript:chooseImg();">
    	</div>
   	 </div>
      
      
      <div class="form-group row">
    	<label class="col-xs-3" for="radio">是否弹出窗</label>
    	<div class="col-xs-9">
        <select id="showMenu" name="showMenu" class="form-control" style="width:300px;display: inline-block;">
        <option value="1">选项卡</option>
        <option value="2">新窗口</option>
        <option value="3">子窗口</option>
           </select>
           <script type="text/javascript">
                document.getElementById("showMenu").value="${bean.showMenu}";
           </script>
    	 <!-- <label class="radio-inline">
          <input type="radio" name="showMenu" value="2" <%=bean.getShowMenu()==2?"checked":""%>>是
          </label>
          <label class="radio-inline">
            <input type="radio" name="showMenu" value="1" <%=bean.getShowMenu()!=2?"checked":""%>>否
          </label> -->
    	</div>
   	 </div>
      <div class="form-group row">
    	<label class="col-xs-3" for="radio">是否有效</label>
    	<div class="col-xs-9">
    	 <label class="radio-inline">
           <input type="radio" name="locked" value="0" <%=bean.getLocked()==0?"checked":""%>>是
          </label>
          <label class="radio-inline">
            <input type="radio" name="locked" value="1" <%=bean.getLocked()==1?"checked":""%>>否
          </label>
    	</div>
   	 </div>
      
     <div class="form-group row" style="text-align: center">
		<input name="btn_query" type="submit" value="保存" class="button btn-success" /> 
	</div>
	  
	 
      
    </table>
    </div>
</div>
</html:form>
<script type="text/javascript">
    <c:if test="${bean.linkType == 'T'}">
	    changeXDiv("${bean.linkType}");
    </c:if>
</script>
</body>
</html>
