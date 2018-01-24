<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>群组编辑</title>
<link href="<%=request.getContextPath()%>/scripts/layer/skin/layer.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/layer/layer.min.js"></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/scripts/main.js'></script>
<script type="text/javascript">
var contextPath="<%=request.getContextPath()%>";

function saveData(){
	var params = jQuery("#iForm").formSerialize();
	var isValid = jQuery("#iForm").form('validate');
	if (!isValid){																											
		return isValid; // 返回false将停止form提交 								
	}
	//var loadi = layer.load('加载中...');
	jQuery.ajax({
			   type: "POST",
			   url: '<%=request.getContextPath()%>/mx/identity/group/saveEdit',
			   data: params,
			   dataType:  'json',
			   error: function(data){
				   //alert('服务器处理错误！');
				   jQuery.messager.alert('Info', '服务器处理错误！', 'info');
			   },
			   success: function(data){
				   if(data != null && data.message != null){
					   //alert(data.message);
					   jQuery.messager.alert('Info', data.message, 'info');
				   } else {
					   //alert('操作成功完成！');
					   jQuery.messager.alert('Info', '操作成功完成！', 'info');
				   }
				   //parent.reloadGrid();
				   //layer.close(loadi);
				   var origin = artDialog.open.origin;
				   origin.reloadGrid();
				   closeDiv();
			   }
		 });
}
function closeDiv(){
	art.dialog.close();
}

function closeLayer(){
	var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
	//$('#closebtn').on('click', function(){
		parent.layer.close(index); //执行关闭
	//});
}
function showTips(){
	layer.tips('填写用户帐号，多个帐号用逗号分隔','#manageUsers', {guide: 2, time: 1});
}

function addUser(){
	  var manageUsersValue = jQuery('#manageUsers').val();
	  var link = "<%=request.getContextPath()%>/mx/base/identityChoose/chooseUsersInsert?type=addReturn&rowId=${bean.groupId}&accountsNotIn="+manageUsersValue;
	  var width=700;
	  var height=400;
	  var scroll="yes";
	  //art.dialog.open(link, { height: height, width: width, title: "添加用户", lock: true, scrollbars:"no" }, false);
	  jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "添加用户",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['680px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}
function setReturnValue(ids,actors,names){
	var manageUsersValue = jQuery('#manageUsers').val();
	if(manageUsersValue!=null && manageUsersValue!=""){
		manageUsersValue += ","+actors;
	}else{
		manageUsersValue = actors;
	}
	jQuery('#manageUsers').val(manageUsersValue);
}

</script>
</head>
<body style="margin:5px;">  
<div class="easyui-layout" data-options="fit:true"> 
  <!-- <div data-options="region:'north',split:true,border:true" style="height:40px"> 
    <div class="toolbar-backgroud"> 
	<span class="x_content_title">编辑角色</span>
	<a href="#" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-save'" onclick="javascript:saveData();" >保存</a> 
    </div> 
  </div> -->
<span></span>
<div data-options="region:'center',border:false,cache:true" >
  <form id="iForm" name="iForm" method="post">
  <input type="hidden" id="groupId" name="groupId" value="${bean.groupId}">
  <input type="hidden" id="type" name="type" value="${type}">
  <fieldset >
	<legend>
		基本信息
	</legend>
  <table class="easyui-form" style="width:500px;" align="center">
    <tbody>
		  <tr>
			<td width="20%" align="left" class="editTitle">群组名称<font color="red">*</font></td>
			<td><input id="name" name="name" type="text" class="easyui-validatebox editInput"  maxlength="50"  data-options="required:true" style="width:250px;" value="${bean.name}" style="width:300px;" ></td>
		  </tr>
		  <tr>
			<td width="20%" align="left" class="editTitle">群组代码<font color="red">*</font></td>
			<td><input id="code" name="code" type="text" class="easyui-validatebox editInput"  maxlength="50"  data-options="required:false" style="width:250px;" value="${bean.code}" style="width:300px;" ></td>
		  </tr>
		  <tr>
		<td width="20%" class="editTitle">管理用户:</td>
		<td align="left">
			<div style="float:left;">
			<textarea name="manageUsers" id="manageUsers" cols="30" rows="4" style="width:250px;"
			class="input-multi" datatype="string" maxsize="100" onclick="showTips();">${bean.manageUsers}</textarea>
			</div>
			<div style="float:left;margin-left:10px;margin-top:20px;">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="javascript:addUser();" >添加用户</a> 
			</div>
		</td>
		</tr>
		  <tr>
			<td width="20%" align="left" class="editTitle" valign="top">描　　述</td>
			<td><textarea id="desc" name="desc" cols="30" rows="5" class="easyui-textbox"  class="input-multi" style="width:300px;height:90px;">${bean.desc}</textarea>        
			</td>
		  </tr>
		<tr>
			<td colspan="2" align="center">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="javascript:saveData();" >保存</a> 
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:closeDiv();" >关闭</a>
			</td>
		</tr>
    </tbody>
  </table>
  </fieldset>
  </form>	
</div>
</div>
</body>
</html>
