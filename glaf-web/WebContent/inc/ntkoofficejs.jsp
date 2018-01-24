<%@ page contentType="text/javascript;charset=UTF-8" %>

function _getContextPath(){
	
	var script;
	var scripts = document.getElementsByTagName('script');
	for ( var i = 0; i < scripts.length; i++) {
		script = scripts[i];
		var src = script.src;
		if (!src)
			continue;
		var m = src.match(/ntkoofficecontrol\.js(\W|$)/i);
		
		if (m) {
			script.parentNode.removeChild(script);
			return src.substring(0, m.index).replace("/scripts/officecontrol/",
					"");
		}
	}
	return "";
}

(function() {
	
	var contextPath = _getContextPath();
	
	document.write('<!-- 用来产生编辑状态的ActiveX控件的JS脚本-->   ');
	document.write('<!-- 因为微软的ActiveX新机制，需要一个外部引入的js-->   ');
	document
			.write('<object id="TANGER_OCX" classid="clsid:C9BC4DFF-4248-4a3c-8A49-63A7D317F404"');
	document
			.write('codebase="<%=request.getContextPath() %>/scripts/officecontrol/OfficeControl.cab#version=5,0,2,8" width="100%" height="100%">   ');
	document.write('<param name="IsUseUTF8URL" value="-1">   ');
	document.write('<param name="IsUseUTF8Data" value="-1">   ');
	document.write('<param name="BorderStyle" value="1">   ');
	document.write('<param name="BorderColor" value="14402205">   ');
	document.write('<param name="TitlebarColor" value="15658734">   ');
	document.write('<param name="TitlebarTextColor" value="0">   ');
	document.write('<param name="MenubarColor" value="14402205">   ');
	document.write('<param name="MenuButtonColor" VALUE="16180947">   ');
	document.write('<param name="MenuBarStyle" value="3">   ');
	document.write('<param name="MenuButtonStyle" value="7">   ');
	document.write('<param name="WebUserName" value="福建华闽通达信息技术有限公司">   ');
	document.write('<param name="Caption" value="华闽通达 OFFICE文档控件">   ');
	document.write('<param name="ProductCaption" value="福建华闽通达信息技术有限公司"> ');
    document.write('<param name="ProductKey" value="1DCECF33F44BB1EF7B86D53EDC07392B218BAA1D"> ');
	document
			.write('<SPAN STYLE="color:red">该网页需要控件浏览.浏览器无法装载所需要的文档控件.请检查浏览器选项中的安全设置.</SPAN>   ');
	document.write('</object>');
})();

