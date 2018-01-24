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
    List  list = (List)request.getAttribute("parent");
    int parent=ParamUtil.getIntParameter(request, "parent", 0);
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
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script type="text/javascript" src='<%=context%>/scripts/main.js'></script>
<script type="text/javascript" src='<%=context%>/scripts/verify.js'></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
<script type="text/javascript">

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

    function verifyApplicationForm(form){
		if(verifyAll(form)){
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
		return true;
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
    var url="<%=request.getContextPath()%>/mx/wpf/form/config?formName="+formName+"&elementId="+elementId+"&elementName="+elementName;
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
    var url="<%=request.getContextPath()%>/mx/form/treedot?formName="+formName+"&elementId="+elementId+"&elementName="+elementName;
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
    var url="<%=request.getContextPath()%>/mx/form/def?formName="+formName+"&elementId="+elementId+"&elementName="+elementName;
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
$(function (){
	$("#iForm").bootstrapValidator({
       fields: {
           username: {
               message: '用户名验证失败',
               validators: {
                   notEmpty: {
                       message: '用户名不能为空'
                   }
               }
           },
           
       }
   });
});
</script>
</head>
<body style="margin:10px;">
<html:form id="iForm" name="iForm"
           action="${contextPath}/mx/sys/application/saveAdd" method="post" enctype="multipart/form-data"
           onsubmit="return verifyApplicationForm(this);"  > 
<div class="panel anel-primary" style="width:550px;padding:0px;background-color:#CCCCFF">
 <div class="panel-heading">
        <h3 class="panel-title">增加模块</h3>
    </div>
<div class="panel-body">
<input type="hidden" name="parent" value="<%=parent%>">
<input type="hidden" id="databaseId" name="databaseId" value="">
<input type="hidden" id="systemFlag" name="systemFlag" value="">
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
   <div class="form-group row">
    <label class="col-xs-3 " for="name">名　　称*</label>
     <div class="col-xs-9">
        <input id="name" name="name" type="text" size="35" class="form-control" datatype="string" nullable="no" maxsize="20" chname="名称" data-options="required:true"  style="width:300px;display: inline-block;"/>
     </div>
   </div>
   <div class="form-group row">
    <label class="col-xs-3" for="code">编　　码</label>
    <div class="col-xs-9">
    	<input id="code" name="code" type="text" size="35" class="form-control" datatype="string" nullable="yes" maxsize="20" chname="代码"  style="width:300px;display: inline-block;"/>
    </div>
   </div>
      
	<div class="form-group row">
    	<label class="col-xs-3" for="desc">描　　述</label>
    	<div class="col-xs-9">
     	  <textarea name="desc" cols="42" rows="4" class="form-control" datatype="string" nullable="yes" maxsize="100" chname="描述" style="width:300px;height:90px;"></textarea>
    	</div>
   	</div>

     <div class="form-group row">
    	<label class="col-xs-3" for="type">链接类型</label>
    	<div class="col-xs-9">
    	<input id="type" name="type"  type="radio" value="L" onclick="javascript:changeXDiv('L');" >链接地址
			<!-- <input id="type" name="type" type="radio" value="T" onclick="javascript:changeXDiv('T');" >链接文件 -->
			<br>
			<div id="urlDir" style="display:block;">
		      <textarea id="url" name="url" cols="42" rows="4" class="form-control" datatype="string" nullable="yes" maxsize="100" chname="链接" style="width:300px;height:90px;"></textarea>
			</div>
    	</div>
   	</div>
	
	
	<div class="form-group row">
    	<label class="col-xs-3" for="printFileName">打印模板</label>
    	<div class="col-xs-9">
    	<div id="divDir2" style="display:block;">
		        <input type="file" id="printFileName" name="printFileName"  class="form-control"  size="35" style="width:300px;display: inline-block;">
				<br>模板链接参数<br>
				<textarea id="printParam" name="printParam" cols="42" rows="4" class="form-control"  datatype="string" nullable="yes" maxsize="100" chname="链接参数" style="width:300px;height:90px;"></textarea>
			</div>
    	</div>
   	</div>
	 
 	
 	<div class="form-group row">
    	<label class="col-xs-3" for="code">关联菜单</label>
    	<div class="col-xs-9">
    	<input type="hidden" id="refId1" name="refId1" value="">
           <input type="text" id="refName1" name="refName1" size="35" value="" class="form-control"  style="width:300px;display: inline-block;"
				  onclick="javascript:chooseCellTreedot('iForm', 'refId1', 'refName1');">
    	</div>
   	</div>
 
	
	  <!-- <tr>
        <td class="input-box2" valign="top">关联工程信息</td>
        <td>
           <input type="hidden" id="refId2" name="refId2" value="">
           <input type="text" id="refName2" name="refName2" size="35" value="" class="input "
				  onclick="javascript:chooseTreeProjectInfo('iForm', 'refId2', 'refName2');">
		</td>
      </tr> -->
	
	<div class="form-group row">
    	<label class="col-xs-3" for="uid">关联WPF界面</label>
    	<div class="col-xs-9">
    	<input type="hidden" id="uid" name="uid" value="">
           <input type="text" id="refName2" name="refName2" size="35" value="" class="form-control"  style="width:300px;display: inline-block;"
				  onclick="javascript:chooseWPFMenu('iForm', 'uid', 'refName2');">
    	</div>
   	</div>
 	<div class="form-group row">
    	<label class="col-xs-3" for="uid">关联ISDP界面</label>
    	<div class="col-xs-9">
    	<input type="hidden" id="cellTreedotIndex" name="cellTreedotIndex" value="">
           <input type="hidden" id="cellTreedotIndex" name="cellTreedotIndex" value="">
           <input type="text" id="refName5" name="refName5" size="35" value="" class="form-control"  style="width:300px;display: inline-block;"
				  onclick="javascript:chooseIsdpWPFMenu('iForm', 'cellTreedotIndex', 'refName5');">
    	</div>
   	</div>
      
	  <div class="form-group row">
    	<label class="col-xs-3" for="position">位置</label>
    	<div class="col-xs-9">
    	<input id="position" name="position" type="text" size="35" class="form-control" datatype="string" nullable="yes" maxsize="20" chname="位置" value="131,22,1 789,999"   style="width:300px;display: inline-block;">
    	</div>
   	 </div>
	 <div class="form-group row">
    	<label class="col-xs-3" for="pageId">关联定义平台界面</label>
    	<div class="col-xs-9">
    	<input type="hidden" id="pageId" name="pageId" value="">
           <input type="text" id="refName3" name="refName3" size="35" value="" class="form-control"  style="width:300px;display: inline-block;"
				  onclick="javascript:chooseMenu('iForm', 'pageId', 'refName3');">
    	</div>
   	 </div>
	
	  <div class="form-group row">
    	<label class="col-xs-3" for="showType">显示类型</label>
    	<div class="col-xs-9">
    	<select id="showType" name="showType" class="form-control" style="width:300px;display: inline-block;">
			<option value="A" selected>前后端均显示</option>
			<option value="B">仅后端显示</option>
			<option value="F">仅前端显示</option>
           </select>
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
    	</div>
   	 </div>
   	 
	<div class="form-group row" style="text-align: center">
		<input name="btn_query" type="submit" value="保存" class="button btn-success" /> 
	</div>
  </table> 
      </div>
</div>
</html:form>
</body>
</html>
