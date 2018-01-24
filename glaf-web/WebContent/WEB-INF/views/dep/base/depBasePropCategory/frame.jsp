<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.glaf.base.modules.sys.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<title>基础平台系统</title>
<script type="text/javascript">
    
    $(function(){
        if(${not empty param.rootCategoryId}){
            document.getElementById("allFrame").cols = "220,*";
        }else{
            document.getElementById("allFrame").cols = "380,*";
        }
    });

    var parentFrameIndex = "${param.frameIndex}";
</script>
</head>
    <frameset id="allFrame" marginwidth="0" marginheight="0" leftmargin="0" topmargin="0"
        framespacing="3" border="1" frameborder="yes" cols="350,*" style="border-color: #000000;">
        
        <frame style="border-right: #99ccff 1px solid; border-top: #003366 1px solid;" leftmargin="0"
            topmargin="0" border="0" frameborder="no" scrolling="auto" id="leftFrame" name="leftFrame"
            src="${pageContext.request.contextPath}/mx/dep/base/depBaseCategory/categoryTree?pId=-1&rootCategoryId=${param.rootCategoryId}">
        
        <frame style="border-top: #003366 1px solid; border-left: #99ccff 2px groove;" border="0"
            frameborder="no" scrolling="auto" id="mainFrame" name="mainFrame" bordercolor="#DEE3E7"
            src="${pageContext.request.contextPath}/mx/dep/base/depBaseProp/getPropByCategoryId/${param.list}?categoryId=-1&rootCategoryId=${param.rootCategoryId}">
    </frameset>
<noframes>
</noframes>
</html>
