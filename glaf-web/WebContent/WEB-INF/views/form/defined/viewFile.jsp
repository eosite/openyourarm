<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.core.config.SystemConfig"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看文件</title>
<script type="text/javascript" src="${contextPath}/scripts/jquery.min.js"></script>
<script src="${contextPath}/scripts/officecontrol/OfficeContorlFunctions.js" type="text/javascript"></script>
<style type="text/css">
	html body{
		height: 100%
	}
</style>
</head>
<body>
	<object id='TANGER_OCX' classid='clsid:C9BC4DFF-4248-4a3c-8A49-63A7D317F404' codebase='${contextPath}/scripts/officecontrol/OfficeControl.cab#version=5,0,2,8'
		width='100%' height='98%'>
		<param name='IsUseUTF8URL' value='-1'>
		<param name='IsUseUTF8Data' value='-1'>
		<param name='BorderStyle' value='1'>
		<param name='BorderColor' value='14402205'>
		<param name='TitlebarColor' value='15658734'>
		<param name='TitlebarTextColor' value='0'>
		<param name='Menubar' value='false'>
		<param name='MenubarColor' value='14402205'>
		<param name='MenuButtonColor' VALUE='16180947'>
		<param name='MenuBarStyle' value='3'>
		<param name='MenuButtonStyle' value='7'>
		<param name='WebUserName' value='福建华闽通达信息技术有限公司'>
		<param name='Caption' value='华闽通达 OFFICE文档控件'>
		<param name='ProductCaption' value='福建华闽通达信息技术有限公司'>
		<param name='ProductKey' value='1DCECF33F44BB1EF7B86D53EDC07392B218BAA1D'>
		<span style='color: red'>不能装载文档控件。请确认你可以连接网络或者检查浏览器的选项中安全设置。</span>
	</object>
	<script language="Jscript" for="TANGER_OCX" event="OnDocumentClosed()">
		officeController.OnDocumentClosed();
	</script>
	<script language="Jscript" for="TANGER_OCX" event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">
		officeController.OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj);
	</script>
	<script language="Jscript" for="TANGER_OCX" event="BeforeOriginalMenuCommand(TANGER_OCX_str,TANGER_OCX_obj)">
		officeController.BeforeOriginalMenuCommand(TANGER_OCX_str,TANGER_OCX_obj);
	</script>
	<script language="Jscript" for="TANGER_OCX" event="OnFileCommand(TANGER_OCX_str,TANGER_OCX_obj)">
		officeController.OnFileCommand(TANGER_OCX_str,TANGER_OCX_obj);
	</script>
	<script language="Jscript" for="TANGER_OCX" event="AfterPublishAsPDFToURL(result,code)">
		officeController.AfterPublishAsPDFToURL(result,code);
	</script>
	<script language="Jscript" for="TANGER_OCX" event="OnCustomMenuCmd2(menuPos,submenuPos,subsubmenuPos,menuCaption,menuID)">
		officeController.OnCustomMenuCmd2(menuPos,submenuPos,subsubmenuPos,menuCaption,menuID);
	</script>
	<script type="text/javascript">
		$(function(){
			$(document.body).attr({
				onbeforeunload : ""
			});
			var contextPath = '${contextPath}' , model = '${param.model}', rp = '${param.rp}' , id = '${param.id}' , key = '${param.key}' , url = '${param.url}' ,fid = '${param.fid}' ;
			var path = contextPath /*+ url*/ ;
			if(model == "ATTA" || model == "FTP"){
				path = contextPath+"/mx/form/imageUpload?method=fileView&mode="+model+"&rp="+rp+"&id="+id+"&key="+key+"&url="+url+"&fid="+fid ;
			}else if(model == "SOA"){
				
			}else{
				if(key && url){
					path = contextPath+"/mx/form/imageUpload?method=fileView&mode="+model+"&rp="+rp+"&id="+id+"&key="+key+"&url="+url+"&fid="+fid ;
				}else{
					path = contextPath+"/mx/form/imageUpload?method=downloadById&id="+id ;
				}
			}
			intializePage(path);
			//intializePage("/glaf/word.doc");
			//intializePage("/glaf/pdf.pdf");
		});
	</script>
</body>
</html>