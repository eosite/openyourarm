<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.business.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.ui.model.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.modules.sys.service.*"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%
	String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(
			context);
	pageContext.setAttribute("contextPath", context);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>OFFICE编辑器</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath() %>/scripts/officecontrol/StyleSheet.css">
	<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/bootstrap/css/bootstrap.min.css'>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript">
	var contextPath = "<%=request.getContextPath() %>";
</script>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/scripts/officecontrol/OfficeContorlFunctions.js"></script>
	
	<script language="JScript" for=TANGER_OCX
						event="OnDocumentClosed()">
		setFileOpenedOrClosed(false);
	</script>
	
	<script language="JScript" for="TANGER_OCX"
		event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">
			
			OFFICE_CONTROL_OBJ.activeDocument.saved=true;//saved属性用来判断文档是否被修改过,文档打开的时候设置成ture,当文档被修改,自动被设置为false,该属性由office提供.
			//获取文档控件中打开的文档的文档类型
			switch (OFFICE_CONTROL_OBJ.doctype)
			{
				case 1:
					fileType = "Word.Document";
					fileTypeSimple = "wrod";
					break;
				case 2:
					fileType = "Excel.Sheet";
					fileTypeSimple="excel";
					break;
				case 3:
					fileType = "PowerPoint.Show";
					fileTypeSimple = "ppt";
					break;
				case 4:
					fileType = "Visio.Drawing";
					break;
				case 5:
					fileType = "MSProject.Project";
					break;
				case 6:
					fileType = "WPS Doc";
					fileTypeSimple="wps";
					break;
				case 7:
					fileType = "Kingsoft Sheet";
					fileTypeSimple="et";
					break;
				default :
					fileType = "unkownfiletype";
					fileTypeSimple="unkownfiletype";
			}
			setFileOpenedOrClosed(true);
		</script>
		
		<script language="JScript" for=TANGER_OCX
			event="BeforeOriginalMenuCommand(TANGER_OCX_str,TANGER_OCX_obj)">
			
			alert("BeforeOriginalMenuCommand事件被触发");
		</script>
		
		<script language="JScript" for=TANGER_OCX
			event="OnFileCommand(TANGER_OCX_str,TANGER_OCX_obj)">
			if (TANGER_OCX_str == 3) 
			{
				alert("不能保存！");
				CancelLastCommand = true;
			}
		</script>
		
		<script language="JScript" for=TANGER_OCX
			event="AfterPublishAsPDFToURL(result,code)">
			result=trim(result);
			alert(result);
			document.all("statusBar").innerHTML="服务器返回信息:"+result;
			if(result=="文档保存成功。")
			{window.close();}
		</script>
		
		<script language="JScript" for=TANGER_OCX
			event="OnCustomMenuCmd2(menuPos,submenuPos,subsubmenuPos,menuCaption,menuID)">
			alert("第" + menuPos +","+ submenuPos +","+ subsubmenuPos +"个菜单项,menuID="+menuID+",菜单标题为\""+menuCaption+"\"的命令被执行.");
		</script>
</head>
<body>
	<div class="container">
		<br>
		<div id="officeId" rid="officeRid" key="OFFICE-OBJECT-KEY" class="row"
			style="">
			<div class="col-xs-10" style="height:750px;">
					<form action='${contextPath}/mx/form/attachment' enctype="multipart/form-data">
						<input type='hidden' name='method' value='officeUpload'>
						<input type='hidden' name='to' value='to_db'>
				        文件名 :<input type='text' class='k-textbox' name='filename'>
					</form>
					<script type="text/javascript"
						src="<%=request.getContextPath() %>/scripts/officecontrol/ntkoofficecontrol.js"></script>
					<div id=statusBar
						style="height:20px;width:100%;background-color:#c0c0c0;font-size:12px;"></div>
			</div>
			<ul>
				<li></li>
			</ul>
		</div>
	</div>
</body>
</html>