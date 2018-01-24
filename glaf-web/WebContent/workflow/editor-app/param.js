/**
 * 
 */
///获取url中指定参数值
// <param name="url">url</param>
// <param name="paras">参数名称</param>   
function fetchRequestParmValue(url, paras) {
	var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
	var paraObj = {}
	for (i = 0; j = paraString[i]; i++) {
		paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j
				.indexOf("=") + 1, j.length);
	}
	var returnValue = paraObj[paras.toLowerCase()];
	if (typeof (returnValue) == "undefined") {
		return "";
	} else {
		return returnValue;
	}
}
var url = window.location.href;
var definedType = fetchRequestParmValue(url, "definedType");
var modelName=fetchRequestParmValue(url, "modelName");
var modelKey=fetchRequestParmValue(url, "modelKey");
if (definedType == '') {
	definedType = 'pageflow';
}
if(definedType=='pageflow')
{
	$(".bannerTitle").text("Page流程建模工具");	
}
else if(definedType=='systemplan')
{
	$(".bannerTitle").text("系统规划流程建模工具");	
}
else
{
	$(".bannerTitle").text("工作流程建模工具");	
}
