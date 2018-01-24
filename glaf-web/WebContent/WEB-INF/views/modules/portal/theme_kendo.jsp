 <%@ page contentType="text/html;charset=UTF-8"%>
    <!--修改主题窗口-->
   <div id="themeDialog">
		主题： <select id="theme" name="theme" onchange="setMyTheme()">
			<option value="default" selected>蓝色</option>
			<option value="gray">灰色</option>
			<option value="sunny">橙色</option>
			<option value="bootstrap">bootstrap</option>
			<option value="metro">metro</option>
			<!-- <option value="metro-blue">metro-blue</option> -->
		</select> 布局： <select id="layoutModel" name="layoutModel"
			onchange="setMyTheme()">
			<option value="home" selected>LOGO菜单</option>
			<option value="home2">左边折叠菜单</option>
			<option value="home3">横向菜单</option>
			<option value="home_kendo">kendo横向菜单</option>
			<option value="kendo">kendo_outlook菜单</option>
			<option value="kendo2">kendo_outlook图片菜单</option>
			<option value="main">左边树形菜单</option>
			<option value="default_home">默认首页</option>
		</select> 
		
		<input id="background" type="hidden" name="background"
			value="${userTheme.background}" />
	    <input id="backgroundType"
			name="backgroundType" type="hidden"
			value="${userTheme.backgroundType}" /> 
		<input id="userThemeId"
			name="userThemeId" type="hidden" value="${userTheme.id}" />
		<form name="mainForm" id="mainForm" method="post">
		<input id="treeURL"
			name="treeURL" type="hidden" value="${param.treeURL}" />
		<input id="contentURL"
			name="contentURL" type="hidden" value="${param.contentURL}" />
		<input id="themeFlag"
			name="themeFlag" type="hidden" value="0" />
		</form>
	</div>
	<script type="text/javascript">
    //初始化选择样式
    document.getElementById("layoutModel").value="${userTheme.layoutModel}";
    document.getElementById("theme").value="${userTheme.themeStyle}";
$("#themeDialog").kendoWindow({
	    title:"<font size='2'>请选择主题</font>",
	    visible: false,
		draggable:false,
		pinned: true,
		modal: true
}
);
var dialog = $("#themeDialog").data("kendoWindow");
dialog.bind("close", closethemeDialog);
function openthemeDialog()
{
	dialog.open();
	dialog.toFront();
	dialog.center();
}
//控制设置完主题后是否继续显示主题窗口
function closethemeDialog()
{
	$("#themeFlag").val(0);
}
if($("#themeFlag").val()==1)
{
	openthemeDialog();
}
 function setMyTheme(){
 	   var theme = $('#theme').val();
 	   var layoutModel = $('#layoutModel').val();
 	   var background = $('#background').val();
 	   var userThemeId = $('#userThemeId').val();
 	   var backgroundType = $('#backgroundType').val();
 	   var param = {'themeStyle':theme,'layoutModel':layoutModel , 'background':background 
 			   ,'userThemeId':userThemeId,'backgroundType':backgroundType} ;
 	   jQuery.ajax({
 				   type: "POST",
 				   url: '<%=request.getContextPath()%>/mx/my/home/setTheme',
 				   dataType:  'json',
 				   data:param,
 				   error: function(data){
 					   alert('服务器处理错误！');
 				   },
 				   success: function(data){
                        var contentURL=$("#content").find("iframe").attr("src");
                        var treeURL=$("#tree").find("iframe").attr("src");
                        $("#contentURL").val(contentURL);
                        $("#treeURL").val(treeURL);
                        $("#themeFlag").val(1);
                        $("#mainForm").action=location.href;
                        $("#mainForm").submit();
 					    //location.reload();
                         //document.getElementById("themeui").href=contextPath+"/scripts/easyui/themes/"+theme+"/easyui.css";
 				   }
 			 });
    }
    // create DropDownList from input HTML element
    $("#theme").kendoDropDownList();
    // create DropDownList from input HTML element
    $("#layoutModel").kendoDropDownList();
</script>