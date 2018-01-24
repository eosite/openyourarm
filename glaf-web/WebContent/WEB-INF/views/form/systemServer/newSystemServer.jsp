<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.business.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.ui.model.*"%>
<%@ page import="com.glaf.core.security.LoginContext"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%
    String context = request.getContextPath();
    com.glaf.base.utils.ContextUtil.getInstance().setContextPath(context);
    pageContext.setAttribute("contextPath", context);
    if (request.getAttribute("userTheme") != null) {
        UserTheme userTheme = (UserTheme) request.getAttribute("userTheme");
        request.setAttribute("theme", userTheme.getThemeStyle());
        request.setAttribute("homeTheme", userTheme.getHomeThemeStyle());
    } else {
        String theme = com.glaf.core.util.RequestUtils.getTheme(request);
        request.setAttribute("theme", theme);
        String homeTheme = com.glaf.core.util.RequestUtils.getHomeTheme(request);
        request.setAttribute("homeTheme", homeTheme);
    }
    LoginContext loginContext = RequestUtils.getLoginContext(request);
    request.setAttribute("loginContext", loginContext);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><%=SystemConfig.getString("res_system_name")%></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-summernote/summernote.css" rel="stylesheet" type="text/css" />
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/css/components.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/apps/css/inbox.min.css" rel="stylesheet" type="text/css"/>
        
        <!-- dialog --> 
        <link rel="stylesheet" type="text/css" href="/glaf/scripts/plugins/bootstrap/dialog/css/bootstrap-dialog.min.css">
        <!--ztree-->
        <link rel="stylesheet" href="${contextPath}/scripts/ztree/css/metroStyle/metroStyle.css" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
        <!--图片弹窗-->
        <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/fancybox/source/jquery.fancybox.css?v=2.1.5" media="screen" />

        <!--grid-->
        <link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/grid/css/grid.css'>
        <link rel="stylesheet" type="text/css" href="/glaf/scripts/plugins/bootstrap/treelist/css/treelist.css">

        <!-- iCheck -->
        <link href="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/skins/all.css" rel="stylesheet" type="text/css" />
        <!-- datepicker3 -->
        <link rel="stylesheet" type="text/css" href="/glaf/scripts/plugins/bootstrap/datepicker/css/bootstrap-datepicker3.min.css"/>
        <link rel="stylesheet" type="text/css" href="/glaf/scripts/plugins/bootstrap/datetimepicker/css/bootstrap-datetimepicker.min.css"/>
        <!-- select2 -->
        <link href="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
        <link href="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />

        
        
        <style type="text/css">
            .portlet.box>.portlet-title{
                padding: 0 10px;
            }
            .portlet.box>.portlet-title>.nav-tabs>li.active>a,.portlet.box>.portlet-title>.nav-tabs>li:hover>a {
                color: #fff;
                font-weight: bold;
            }
            .portlet.box{
                min-height: 0px;    
            }
            .portlet.box>.portlet-title{
                min-height: 0px;    
            }
            .portlet.box>.portlet-title>.caption,.portlet.box>.portlet-title>.nav-tabs>li>a{
                padding: 6px 6px;
            }
            .portlet.box>.portlet-title>.nav-tabs>li>a {
                color: #fff;
            }
            .tabbable-line>.nav-tabs>li.active{
                border-bottom-color: #ab1e27;
            }
            line>.nav-tabs>li.open, .tabbable-line>.nav-tabs>li:hover {
                border-bottom-color: #f36a5a;
            }
            .form-group label{
                text-align: right;
            }

            .border_div{
                border:1px solid #ccc;
            }

            html,body{
                height: 100%;
            }
            #systemServerTab,.tab-pane,.full-height{
                height: 100%;
            }
        </style>
</head>
<body >
    <div style="padding:5px 5px;height:100%;">

	<div id="systemServerTab" class="tabbable-line">
		<ul class="nav nav-tabs">
        </ul>
        <div class="tab-content" style="padding:10px 0px;">
        </div>
       
	</div>
    </div>
	<template id="添加部门"> 

	</template>    
</body>
</html>
<script type ="text/javascript">
    var contextPath = '${contextPath}';
</script>

<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-summernote/summernote.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-summernote/lang/summernote-zh-CN.js" type="text/javascript"></script>
<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.exedit.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/html2canvas/html2canvas.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/html2canvas/html2canvas.svg.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/fancybox/source/jquery.fancybox.pack.js?v=2.1.5"></script>
<script type="text/javascript" src="${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/layer/layer.min.js"></script>
<!-- ztree-->
<script type="text/javascript" src="${contextPath}/scripts/kendo/bootstrap/ztree.extend.js"></script>

<!-- dialog --> 
<script src="/glaf/scripts/plugins/bootstrap/dialog/js/bootstrap-dialog.min.js"></script>
<script src="/glaf/scripts/plugins/bootstrap/dialog/js/jquery.dialog.extends.js"></script>
<!--grid-->
<script src='${contextPath}/scripts/plugins/bootstrap/grid/js/jquery.grid.extends.js' type='text/javascript'></script>
<script src="/glaf/scripts/plugins/bootstrap/treelist/js/jquery.treelist.extends.js" type="text/javascript"></script>

<!-- iCheck -->
<script src="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/icheck.js" type="text/javascript"></script>
<!-- datepicker -->
<script type="text/javascript" src="/glaf/scripts/plugins/bootstrap/datepicker/ext/jquery.datepicker.extends.js"></script>
<script type="text/javascript" src="/glaf/scripts/plugins/bootstrap/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="/glaf/scripts/plugins/bootstrap/datetimepicker/ext/jquery.datetimepicker.extends.js"></script>

<!-- slimscroll -->
<script src="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>

<!-- select2 -->
<script src="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>

<!--自身的js -->
<script type="text/javascript" src="/glaf/scripts/systemServer/newSystemServer.js"></script>

<script type ="text/javascript">
    //事件定义器中的回调方法
    var retFn = "${param.retFn}";
    function callback(config){
        var retObj = {
            name:config.dataItem.name,
            value:config.tabid+"_"+config.dataItem.key,
            otherParam : config.dataItem,
            defaultParam : config.defaultParam,
            url : config.dataItem.url
        };
        if(config.itemParam != undefined){
        	retObj = {
                    name:config.dataItem.operation,
                    value:config.dataItem.operationCode,
                    otherParam : config.dataItem,
                    itemParam : config.itemParam,
                    tableName : config.itemParam.tableName,
                    databaseId : config.itemParam.databaseId
            };
         }
        parent[retFn](retObj);
        closeWin();
    }
    function callbackCopy(config){
        var retObj = {
            name:config.dataItem.operation,
            value:config.dataItem.operationCode,
            otherParam : config.dataItem
        };
        if(config.itemParam != undefined){
        	retObj = {
                    name:config.dataItem.operation,
                    value:config.dataItem.operationCode+"-"+config.itemParam.tableName+"-"+config.itemParam.databaseId+"-"+config.itemParam.item.index_id+"-"+config.itemParam.item.parent_id+"-"+config.itemParam.item.treeid,
                    otherParam : config.dataItem,
                    itemParam : config.itemParam,
                    
            };
         }
        parent[retFn](retObj);
        closeWin();
    }
    function closeWin(){
        parent.layer.close(parent.layer.getFrameIndex());
    }
    function OutExtItem(param){
    	window.itemParam = param;
    }
</script>