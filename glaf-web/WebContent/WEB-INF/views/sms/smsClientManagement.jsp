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
        <link rel="stylesheet" type="text/css" href="/glaf/scripts/plugins/bootstrap/pagination/css/pagination.css"/>
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
            .portlet.box>.portlet-title>.nav-tabs{
                float: none;
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

            .fileUpload {
                position: relative;
                overflow: hidden;
                /*margin: 10px;*/
                margin-left: 0px;
            }

            .fileUpload input.upload {  position: absolute;
                top: 0;
                right: 0;
                margin: 0;
                padding: 0;
                font-size: 20px;
                cursor: pointer;
                opacity: 0;
                filter: alpha(opacity=0);
            }
            .modal-body{
                padding:3px 3px;
            }

            .btn.hmtd-xs{
                padding:3px 10px !important;
                border-style:none !important;
            }
            .myFieldset{
                border:2px groove #fdfdfd;
                padding:5px 5px;
            }
            .myFieldset legend{
                width: inherit;
                margin-bottom: 0px;
                border-bottom: 0;
                font-size: 14px;

            }
            html,body{
                height:100%;
            }

            .fileUpload {
                position: relative;
                overflow: hidden;
                /*margin: 10px;*/
                margin-left: 0px;
                display: inline-block;
                
            }

            .fileUpload input.upload {  position: absolute;
                display: inline-block;
                top: 0;
                right: 0;
                margin: 0;
                padding: 0;
                font-size: 20px;
                cursor: pointer;
                opacity: 0;
                filter: alpha(opacity=0);
            }

        </style>
</head>
<body style="padding:3px 3px !important;">
    <div class="portlet box light blue strruleportlet" style="margin:0px 0px;">
        <div class="portlet-title tabbable-line">
            <div class="caption">
                <i class="fa fa-cog"></i>短信服务设置
            </div>
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="#tab_0" data-toggle="tab" aria-expanded="false" value="download">短信服务器设置</a>
                </li>
                <li class="">
                    <a href="#tab_1" data-toggle="tab" aria-expanded="false" value="upload">客户端设置</a>
                </li>
                <li class="">
                    <a href="#tab_2" data-toggle="tab" aria-expanded="false" value="upload">短信管理</a>
                </li>
                <li class="">
                    <a href="#tab_3" data-toggle="tab" aria-expanded="false" value="upload">短信历史记录管理</a>
                </li>
            </ul>
        </div>
        <div class="portlet-body" style="background:#FAFAFA;">
            <div class="tab-content" >
                <div class="tab-pane active" id="tab_0">
                    <div>
                        <button type="button" id="showAddSmsServerBtn" class="btn green btn-sm" style="margin-bottom:20px;"><i class="glyphicon glyphicon-plus"></i>新增</button>
                        

                        <!-- <button type="button" id="importSqlliteBtn" class="btn green btn-sm" style=""><i class="glyphicon glyphicon-plus"></i>导入sqllite文件</button> -->
                        <div id="smsServerGrid"></div>
                    </div>
                </div>
                <div class="tab-pane" id="tab_1">
                    <div>
                        <button type="button" id="showAddSmsClientBtn" class="btn green btn-sm" style="margin-bottom:20px;"><i class="glyphicon glyphicon-plus"></i>新增</button>
                        

                        <!-- <button type="button" id="importSqlliteBtn" class="btn green btn-sm" style=""><i class="glyphicon glyphicon-plus"></i>导入sqllite文件</button> -->
                        <div id="smsClientGrid"></div>
                    </div>
                </div>
                <div class="tab-pane row" id="tab_2">
                    <div>
                        <!-- <button type="button" id="importSqlliteBtn" class="btn green btn-sm" style=""><i class="glyphicon glyphicon-plus"></i>导入sqllite文件</button> -->
                        <div id="smsMessageGrid"></div>
                    </div>
                </div>
                <div class="tab-pane row" id="tab_3">
                    <div>
                        <!-- <button type="button" id="importSqlliteBtn" class="btn green btn-sm" style=""><i class="glyphicon glyphicon-plus"></i>导入sqllite文件</button> -->
                        <div id="smsHistoryMessageGrid"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
<template id="editSmsServerTemplate">
    <form style="padding:10px 10px;overflow:hidden;" class="form-horizontal">
        
        {{if id }}
            <input type="hidden" value="{{id}}" name="id">
        {{/if}}
        <div class="row">
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">主题：</label>
                    <div class="col-xs-8">
                        <input type="text" name="subject" class="form-control" maxlength="50" value="{{subject}}" required>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">SMS短信服务器IP地址：</label>
                    <div class="col-xs-8">
                        <input type="text" name="serverIP" class="form-control" maxlength="50" value="{{serverIP}}" required>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">端口：</label>
                    <div class="col-xs-8">
                        <input type="text" name="port" class="form-control" maxlength="50" value="{{port}}" required>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">路径：</label>
                    <div class="col-xs-8">
                        <input type="text" name="path" class="form-control" maxlength="50" value="{{path}}" required>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">类型：</label>
                    <div class="col-xs-8">
                        <input type="text" name="type" class="form-control" maxlength="50" value="{{type}}" required>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">是否锁定：</label>
                    <div class="col-xs-8">
                        {{if locked}}
                            <input type="checkbox" name="locked" class="form-control" maxlength="50" checked required>
                        {{else}}
                            <input type="checkbox" name="locked" class="form-control" maxlength="50" required>
                        {{/if}}
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row" style="text-align:center">
                    <button type="button" id="saveBtn" class="btn blue btn-sm " >
                    <i class="glyphicon glyphicon-ok"></i>保存
                    </button>
                </div>
            </div>
        </div>
    </form>
</template>

<template id="editSmsPersonTemplate">
    <form style="padding:10px 10px;overflow:hidden;" class="form-horizontal" enctype="multipart/form-data">
        
        {{if id }}
            <input type="hidden" value="{{id}}" name="id">
        {{/if}}
        <div class="row form-group">
            <label class="control-label col-xs-3"></label>
            <div class="col-xs-8">
                <label class="control-label">导入：

                    <input id="uploadFile" class="form-control" placeholder="选择Excel文件" disabled="disabled" style="width:250px;display:inline-block;">
                    <div class="fileUpload btn blue">
                        <span><i class="fa fa-search"></i>选择文件</span>
                        <input id="uploadBtn" type="file" name="file" class="upload" accept=".xlsx">
                    </div>
                    <button class="btn green" type="button" id="uploadExcel"><i class="glyphicon glyphicon-import"></i>确认上传</button>
                    <script type="text/javascript">
                        document.getElementById("uploadBtn").onchange = function () {
                            document.getElementById("uploadFile").value = this.value;
                        };

                    </script>
                </label>
            </div>
        </div>
    </form>
    <div style="padding:5px 5px;">
        <div id="smsPersonGrid"></div>
    </div>
</template>

<template id="editSmsClientTemplate">
    <form style="padding:10px 10px;overflow:hidden;" class="form-horizontal">
        
        {{if id }}
            <input type="hidden" value="{{id}}" name="id">
        {{/if}}
        <div class="row">
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">主题：</label>
                    <div class="col-xs-8">
                        <input type="text" name="subject" class="form-control" maxlength="50" value="{{subject}}" required>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">发送端IP地址：</label>
                    <div class="col-xs-8">
                        <input type="text" name="remoteIP" class="form-control" maxlength="50" value="{{remoteIP}}" required>
                    </div>
                </div>
            </div>
            
            {{if !nowType || nowType != "edit"}}
                <div class="form-group">
                    <div class="row">
                        <label class="control-label col-xs-3">系统代码：</label>
                        <div class="col-xs-8">
                            <input type="text" name="sysCode" class="form-control" maxlength="50" value="{{sysCode}}" required>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <label class="control-label col-xs-3">系统密码：</label>
                        <div class="col-xs-8">
                            <input type="text" name="sysPwd" class="form-control" maxlength="50" value="{{sysPwd}}" required>
                        </div>
                    </div>
                </div>
            {{else}}
                <div class="form-group">
                    <div class="row">
                        <label class="control-label col-xs-3">系统代码：</label>
                        <div class="col-xs-8">
                            <input type="text" class="form-control" maxlength="50" value="{{sysCode}}" required readonly="true" disabled="true">
                        </div>
                    </div>
                </div>
            {{/if}}
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">加密标识：</label>
                    <div class="col-xs-8">
                        <select name="encryptionFlag" class="form-control" required>
                            {{if !encryptionFlag || encryptionFlag=="NONE"}}
                                <option value="NONE" selected>不加密</option>
                                <option value="ALL">全部</option>
                                <option value="DATA">数据</option>
                            {{else if encryptionFlag=="ALL"}}
                                <option value="NONE">不加密</option>
                                <option value="ALL" selected>全部</option>
                                <option value="DATA">数据</option>
                            {{else}}
                                <option value="NONE">不加密</option>
                                <option value="ALL">全部</option>
                                <option value="DATA" selected>数据</option>
                            {{/if}}
                            
                        </select>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">公钥：</label>
                    <div class="col-xs-6">
                        <textarea name="publicKey" rows="3" required style="width:100%;">{{publicKey}}</textarea>
                    </div>
                    <div class="col-xs-2">
                        <button type="button" id="createRsaKey" class="btn green btn-sm " >
                            <i class="glyphicon glyphicon-ok"></i>自动生成
                        </button>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">私钥：</label>
                    <div class="col-xs-6">
                        <textarea name="privateKey" rows="3" required style="width:100%;">{{privateKey}}</textarea>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">对方公钥：</label>
                    <div class="col-xs-8">
                        <textarea name="peerPublicKey" rows="3" required style="width:100%;">{{peerPublicKey}}</textarea>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">令牌：</label>
                    <div class="col-xs-8">
                        <textarea name="token" rows="3" required style="width:100%;">{{token}}</textarea>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">类型：</label>
                    <div class="col-xs-8">
                        <input type="text" name="type" class="form-control" maxlength="50" value="{{type}}" required>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">发送频率：</label>
                    <div class="col-xs-8">
                        <input type="number" name="frequence" class="form-control" maxlength="50" value="{{frequence}}" required>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">限制条数：</label>
                    <div class="col-xs-8">
                        <input type="number" name="limit" class="form-control" maxlength="50" value="{{limit}}" required>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">是否锁定：</label>
                    <div class="col-xs-8">
                        {{if locked}}
                            <input type="checkbox" name="locked" class="form-control" maxlength="50" checked required>
                        {{else}}
                            <input type="checkbox" name="locked" class="form-control" maxlength="50" required>
                        {{/if}}
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row" style="text-align:center">
                    <button type="button" id="saveBtn" class="btn blue btn-sm " >
                    <i class="glyphicon glyphicon-ok"></i>保存
                    </button>
                </div>
            </div>
        </div>
    </form>
</template>
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

<!-- ztree-->
<script type="text/javascript" src="${contextPath}/scripts/kendo/bootstrap/ztree.extend.js"></script>

<!-- dialog --> 
<script src="/glaf/scripts/plugins/bootstrap/dialog/js/bootstrap-dialog.min.js"></script>
<script src="/glaf/scripts/plugins/bootstrap/dialog/js/jquery.dialog.extends.js"></script>
<!--grid-->
<script type="text/javascript" src="${contextPath}/scripts/plugins/bootstrap/pagination/js/jquery.pagination.extends.js"></script>
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

<!-- 一些整合好的方法，如查询表格字段信息 -->
<script type="text/javascript" src="/glaf/webfile/js/builder.js"></script>
<script type="text/javascript" src="/glaf/webfile/js/builder.jsplumb.js"></script>

<!-- 表单验证 -->
<script src="${contextPath}/scripts/jquery.validate.min.js" type="text/javascript"></script>
<!-- altTemplate -->
<script src="/glaf/scripts/artTemplate/dist/template.js"></script>
<script src="/glaf/scripts/artTemplate/dataUtils.js"></script>

<style type="text/css">
    .select2-dropdown.select2-dropdown--below{
        z-index: 99999;
    }
    .grid_operator_div{
        display: inline-block;
        margin-left: 6px;
    }
</style>


<script type ="text/javascript">
    var dialog_ = null;

    /**
     * 日期格式化
     * @param  
     * @returns 
     */
    Date.prototype.format = function(format) {
        var args = {
            "M+": this.getMonth() + 1,
            "d+": this.getDate(),
            "H+": this.getHours(),
            "m+": this.getMinutes(),
            "s+": this.getSeconds(),
            "q+": Math.floor((this.getMonth() + 3) / 3), //quarter 
            "S": this.getMilliseconds()
        };
        if (/(y+)/.test(format))
            format = format.replace(RegExp.$1, (this.getFullYear() + "")
                .substr(4 - RegExp.$1.length));
        for (var i in args) {
            var n = args[i];
            if (new RegExp("(" + i + ")").test(format))
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));
        }
        return format;
    };

    function converToJson(serializeArray){
        var datas = {};
        $.each(serializeArray,function(i,item){
            datas[item.name] = item.value;
        })
        return datas;
    }

    function resizeSmsPage(){
        $(".portlet").outerHeight($(".portlet").parent().height());
        $(".portlet-body").outerHeight($(".portlet").height() - $(".portlet-title").height());
    }

    $(function(){
        resizeSmsPage();
        window.onresize = function() {
            resizeSmsPage();
        }

        var smsServerManagement = {
            validate_setting : {
                messages: {
                    subject: {
                        "required": "需要输入名称",
                    },
                    serverIP: {
                        "required": "需要输入名称",
                    },
                    port: {
                        "required": "需要输入名称",
                    },
                    path: {
                        "required": "需要输入名称",
                        "maxlength": "输入长度最多是{0}的字符串(汉字算一个字符)"
                    },
                },
                rules: {
                    subject: {
                        "required": true,
                    },
                    serverIP: {
                        "required": true,
                    },
                    port: {
                        "required": true,
                    },
                    path: {
                        "required": true,
                        "maxlength": 50
                    },
                },
                errorElement: 'span', //default input error message container
                errorClass: 'help-block help-block-error', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "", // validate all fields including form hidden input
                invalidHandler: function(event, validator) { //display error alert on form submit              
                },
                errorPlacement: function(error, element) {
                    element.parent().find(".help-block-error").remove();
                    if (element.is(':checkbox')) {
                        error.insertAfter(element.closest(".md-checkbox-list, .md-checkbox-inline, .checkbox-list, .checkbox-inline"));
                    } else if (element.is(':radio')) {
                        error.insertAfter(element.closest(".md-radio-list, .md-radio-inline, .radio-list,.radio-inline"));
                    } else {
                        error.insertAfter(element); // for other inputs, just perform default behavior
                    }
                },
                highlight: function(element) { // hightlight error inputs
                    $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
                },

                unhighlight: function(element) { // revert the change done by hightlight
                    $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
                },

                success: function(label) {
                    label.closest('.form-group').removeClass('has-error'); // set success class to the control group
                    label.remove();
                },
            },
            _gabalParam : {
                queryUrl : contextPath + "/mx/sys/smsServer/read",
                saveurl : contextPath + "/mx/sys/smsServer/saveSmsServer",
            },
            _init : function(){
                var that = this;
                that.$smsServerGrid = $("#smsServerGrid");
                that._loadSmsServerGrid();
                that._bindEvent();
            },
            _loadSmsServerGrid : function(){
                var that = this;
                that.$smsServerGrid.grid({
                    ajax: {
                        read: {
                            url: that._gabalParam.queryUrl,
                            async: true,
                            data: {},
                            success: function(ret) {
                                if (ret) {
                                    return {
                                        total: ret.total,
                                        rows: ret.data
                                    }
                                } else {
                                    return ret;
                                }
                            }
                        },
                        parameterMap: function(data) {
                            return JSON.stringify(data || {});
                        },
                        schema: {
                            model: {
                                id: "id",
                                fields: {
                                    }
                            },
                            data: "rows",
                            total: "total"
                        }
                    },
                    selectable: "sinagle",
                    scrollable: false,
                    columns:[
                        {title:'主题', field:'subject', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:15%;'
                        },},
                        {title:'SMS服务IP', field:'serverIP', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:15%;'
                        },},
                        // {title:'文件代码', field:'sqliteCode', sortable:false,'isEditor':false, 'attributes': {
                        //     'style': 'text-align:center;width:10%;'
                        // },},
                        {title:'端口', field:'port', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },},
                        {title:'路径', field:'path', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:20%;'
                        },},
                        {title:'启用/禁用', field:'locked', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },
                        template : function(data){
                            return data.locked?'禁用':"启用";
                        }
                        },
                        {title:'创建者', field:'createBy', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },},
                        {title:'创建时间', field:'createTime_datetime', sortable:true,'isEditor':true, 
                        'editor':{'component':'datetimepicker',},'format':'yyyy-MM-dd HH:mm:ss',
                        'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },},
                        {title:'操作',field:'subject',sortable:true,'isEditor':true, 
                        'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },
                        'template':function(data){
                            var buttonHtml = '<button type="button" class="changeBtn btn blue btn-sm " >'+
                                '<i class="glyphicon glyphicon-edit"></i>修改</button>';

                            return buttonHtml;
                        }
                        }
                    ],
                    pagination: {
                        paging: true,
                        page: 1,
                        pageSize: 10,
                        pageSizes: [5, 10, 15, 20, 25, 50, 100, 200, 500], //分页条数选择
                        buttonCount: 10,
                        previousNext: "true", //是否使能分页翻页按钮
                        numeric: "true", //是否使能数字页码按钮
                        input: "false", //是否使能输入框翻页
                        first: '首页', //首页按钮提示文本
                        last: '末页', //末页按钮提示文本
                        previous: '上一页', //上一页显示文本
                        next: '下一页', //下一页显示文本
                        refresh: 'false'
                    }
                })
            },
            _reloadSmsServerGrid : function(){
                var that = this;
                that.$smsServerGrid.grid("query");
            },
            _bindEvent : function(){
                var that = this;
                //新增短信服务器
                $("#showAddSmsServerBtn").click(function(){
                    that._openEditSmsServerDialog("新增SMS短信服务器");
                })
                //修改操作
                that.$smsServerGrid.on("click",".changeBtn",function(event){
                    var datas = that.$smsServerGrid.grid("getSelectedRows");
                    if(datas && datas.length > 0){
                        that._openEditSmsServerDialog("新增SMS短信服务器",datas[0]);    
                    }
                    
                })
            },
            _openEditSmsServerDialog : function(title,data){
                var that = this;
                //新增主题
                var dialog = BootstrapDialog.show({
                    title: title || "SMS短信服务器",
                    draggable: false,
                    css: {
                        width: '800px',
                        height: '500px'
                    },
                    onshown: function(dialog) {
                        var $modalBody = dialog.getModalBody();
                        if (!data) {
                            data = data || {};
                        }

                        var templateHtml = ArtTemplateDataUtils.convertOne(data, $("#editSmsServerTemplate").html());
                        $modalBody.append(templateHtml);

                        that._bindDailogEvent($modalBody, that._gabalParam.editLayoutUrl, 'layout');
                    }
                });

                dialog.getModalHeader().css({
                    padding: '4px 4px',
                });
                that.dialog = dialog;
            },
            _bindDailogEvent : function($dialog,saveUrl){
                var that = this;

                var saveFormValidator = $dialog.find("form").validate(that.validate_setting);
                $dialog.find("#saveBtn").click(function(event){
                    //保存操作
                    var $thisBtn = $(this);
                    if($thisBtn.attr("disabled")){
                        return;
                    }
                    //保存主题信息
                    if (!saveFormValidator.form()) {
                        return;
                    }

                    var params = converToJson($dialog.find("form").serializeArray());
                    if(params.locked && (params.locked == 'on' || params.locked == 'yes')){
                        params.locked = 1;
                    }else{
                        params.locked = 0;
                    }

                    $.ajax({
                        url: that._gabalParam.saveurl,
                        async: true,
                        data: params,
                        type : "POST",
                        success: function(ret) {
                            if(typeof ret == 'string'){
                                ret = JSON.parse(ret);
                                that.dialog.close();
                                that._reloadSmsServerGrid();
                            }
                            if (ret && ret.statusCode == '200') {
                                alert("保存成功");
                            } else {
                                alert("保存失败");
                            }
                        },
                        complete : function(){
                            $thisBtn.removeAttr("disabled");
                        }
                    })
                })
            }
        }
        smsServerManagement._init();

        var smsClientManagement = {
            validate_setting : {
                messages: {
                    subject: {
                        "required": "必填",
                    },
                    remoteIP: {
                        "required": "必填",
                    },
                    sysCode: {
                        "required": "必填",
                    },
                    sysPwd: {
                        "required": "必填",
                    },
                    publicKey: {
                        "required": "必填",
                    },
                    privateKey: {
                        "required": "必填",
                    },
                    token: {
                        "required": "必填",
                    },
                },
                rules: {
                    subject: {
                        "required": true,
                    },
                    remoteIP: {
                        "required": true,
                    },
                    sysCode: {
                        "required": true,
                    },
                    sysPwd: {
                        "required": true,
                    },
                    publicKey: {
                        "required": true,
                    },
                    privateKey: {
                        "required": true,
                    },
                    token: {
                        "required": true,
                    },
                },
                errorElement: 'span', //default input error message container
                errorClass: 'help-block help-block-error', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "", // validate all fields including form hidden input
                invalidHandler: function(event, validator) { //display error alert on form submit              
                },
                errorPlacement: function(error, element) {
                    element.parent().find(".help-block-error").remove();
                    if (element.is(':checkbox')) {
                        error.insertAfter(element.closest(".md-checkbox-list, .md-checkbox-inline, .checkbox-list, .checkbox-inline"));
                    } else if (element.is(':radio')) {
                        error.insertAfter(element.closest(".md-radio-list, .md-radio-inline, .radio-list,.radio-inline"));
                    } else {
                        error.insertAfter(element); // for other inputs, just perform default behavior
                    }
                },
                highlight: function(element) { // hightlight error inputs
                    $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
                },

                unhighlight: function(element) { // revert the change done by hightlight
                    $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
                },

                success: function(label) {
                    label.closest('.form-group').removeClass('has-error'); // set success class to the control group
                    label.remove();
                },
            },
            _gabalParam : {
                queryUrl : contextPath + "/mx/sys/smsClient/read",
                saveurl : contextPath + "/mx/sys/smsClient/saveSmsClient",
                checkCode : contextPath + "/mx/sys/smsClient/checkCode",
                queryPersonUrl : contextPath + '/mx/sys/smsPerson/read',
                ablePersonUrl : contextPath + '/mx/sys/smsPerson/able',
                uploadPersonUrl : contextPath + '/mx/sys/smsPerson/upload',
                getRsaKeyUrl : contextPath + '/mx/sys/smsClient/getRsaKey',
            },
            _init : function(){
                var that = this;
                that.$smsClientGrid = $("#smsClientGrid");
                that._loadsmsClientGrid();
                that._bindEvent();
            },
            _loadsmsClientGrid : function(){
                var that = this;
                that.$smsClientGrid.grid({
                    ajax: {
                        read: {
                            url: that._gabalParam.queryUrl,
                            async: true,
                            data: {},
                            success: function(ret) {
                                if (ret) {
                                    return {
                                        total: ret.total,
                                        rows: ret.data
                                    }
                                } else {
                                    return ret;
                                }
                            }
                        },
                        parameterMap: function(data) {
                            return JSON.stringify(data || {});
                        },
                        schema: {
                            model: {
                                id: "id",
                                fields: {
                                    }
                            },
                            data: "rows",
                            total: "total"
                        }
                    },
                    selectable: "sinagle",
                    scrollable: false,
                    columns:[
                        {title:'主题', field:'subject', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },},
                        {title:'发送端IP地址', field:'remoteIP', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },},
                        // {title:'文件代码', field:'sqliteCode', sortable:false,'isEditor':false, 'attributes': {
                        //     'style': 'text-align:center;width:10%;'
                        // },},
                        {title:'系统代码', field:'sysCode', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },},
                        {title:'启用/禁用', field:'locked', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },
                        template : function(data){
                            return data.locked?'禁用':"启用";
                        }
                        },
                        {title:'发送频率', field:'frequence', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },},
                        {title:'限制条数', field:'limit', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },},
                        {title:'创建者', field:'createBy', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },},
                        {title:'创建时间', field:'createTime_datetime', sortable:true,'isEditor':true, 
                        'editor':{'component':'datetimepicker',},'format':'yyyy-MM-dd HH:mm:ss',
                        'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },},
                        {title:'操作',field:'subject',sortable:true,'isEditor':true, 
                        'attributes': {
                            'style': 'text-align:center;width:20%;'
                        },
                        'template':function(data){
                            var buttonHtml = '<button type="button" class="changeBtn btn blue btn-sm " >'+
                                '<i class="glyphicon glyphicon-edit"></i>修改</button>';
                            buttonHtml += '<button type="button" class="showPersonBtn btn blue btn-sm " >'+
                                '<i class="glyphicon glyphicon-import"></i>用户信息</button>';

                            return buttonHtml;
                        }
                        }
                    ],
                    pagination: {
                        paging: true,
                        page: 1,
                        pageSize: 10,
                        pageSizes: [5, 10, 15, 20, 25, 50, 100, 200, 500], //分页条数选择
                        buttonCount: 10,
                        previousNext: "true", //是否使能分页翻页按钮
                        numeric: "true", //是否使能数字页码按钮
                        input: "false", //是否使能输入框翻页
                        first: '首页', //首页按钮提示文本
                        last: '末页', //末页按钮提示文本
                        previous: '上一页', //上一页显示文本
                        next: '下一页', //下一页显示文本
                        refresh: 'false'
                    }
                })
            },
            _reloadsmsClientGrid : function(){
                var that = this;
                that.$smsClientGrid.grid("query");
            },
            _bindEvent : function(){
                var that = this;
                //新增短信服务器
                $("#showAddSmsClientBtn").click(function(){
                    that._openEditSmsClientDialog("新增SMS短信服务器",null,"editSmsClientTemplate");
                })
                //修改操作
                that.$smsClientGrid.on("click",".changeBtn",function(event){
                    var datas = that.$smsClientGrid.grid("getSelectedRows");
                    if(datas && datas.length > 0){
                        datas[0].nowType = 'edit';
                        that._openEditSmsClientDialog("修改SMS短信服务器",datas[0],"editSmsClientTemplate");    
                    }
                    
                })
                //修改操作
                that.$smsClientGrid.on("click",".showPersonBtn",function(event){
                    var datas = that.$smsClientGrid.grid("getSelectedRows");
                    if(datas && datas.length > 0){
                        that._openEditSmsClientDialog("用户信息修改("+datas[0].subject+")",datas[0],"editSmsPersonTemplate",'person');    
                    }
                })
            },
            _openEditSmsClientDialog : function(title,data,templateId,type){
                var that = this;
                //新增主题
                var dialog = BootstrapDialog.show({
                    title: title || "SMS短信服务器",
                    draggable: false,
                    css: {
                        width: '860px',
                        height: '500px'
                    },
                    onshown: function(dialog) {
                        var $modalBody = dialog.getModalBody();
                        if (!data) {
                            data = data || {};
                        }

                        var templateHtml = ArtTemplateDataUtils.convertOne(data, $("#"+templateId).html());
                        $modalBody.append(templateHtml);

                        that._bindDailogEvent($modalBody, that._gabalParam.editLayoutUrl, type,data);
                    }
                });
                dialog.getModalHeader().css({
                    padding: '4px 4px',
                });
                that.dialog = dialog;
            },
            _initPersonGrid : function(params){
                var that = this;
                $("#smsPersonGrid").grid({
                    ajax: {
                        read: {
                            url: that._gabalParam.queryPersonUrl+"?clientId="+params.clientId,
                            async: true,
                            data: {
                                params:JSON.stringify(params) || ""
                            },
                            success: function(ret) {
                                if (ret) {
                                    return {
                                        total: ret.total,
                                        rows: ret.data
                                    }
                                } else {
                                    return ret;
                                }
                            }
                        },
                        parameterMap: function(data) {
                            return JSON.stringify(data || {});
                        },
                        schema: {
                            model: {
                                id: "id",
                                fields: {
                                    }
                            },
                            data: "rows",
                            total: "total"
                        }
                    },
                    selectable: "sinagle",
                    scrollable: false,
                    columns:[
                        {title:'名称', field:'name', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:30%;'
                        },},
                        {title:'手机号码', field:'mobile', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:20%;'
                        },},
                        {title:'状态', field:'locked', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },
                        template : function(data){
                            return data.locked?'禁用':'启动'
                        }
                        },
                        {title:'限制条数', field:'limit', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },},
                        {title:'操作类型', field:'locked', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:30%;'
                        },
                        template : function(data){
                            var buttonHtml = '<button type="button" class="changeBtn btn blue btn-sm " >'+
                                '<i class="glyphicon glyphicon-edit"></i>修改</button>';
                            buttonHtml += '<button type="button" class="ableBtn btn red btn-sm " >'+
                                '<i class="glyphicon glyphicon-import"></i>启用/禁用</button>';

                            return buttonHtml;
                        }
                        },
                    ],
                    pagination: {
                        paging: true,
                        page: 1,
                        pageSize: 10,
                        pageSizes: [5, 10, 15, 20, 25, 50, 100, 200, 500], //分页条数选择
                        buttonCount: 10,
                        previousNext: "true", //是否使能分页翻页按钮
                        numeric: "true", //是否使能数字页码按钮
                        input: "false", //是否使能输入框翻页
                        first: '首页', //首页按钮提示文本
                        last: '末页', //末页按钮提示文本
                        previous: '上一页', //上一页显示文本
                        next: '下一页', //下一页显示文本
                        refresh: 'false'
                    }
                })
            },
            _reloadPersonGrid : function(){
                $("#smsPersonGrid").grid("query");
            },
            _bindDailogEvent : function($dialog,saveUrl,type,data){
                var that = this;

                if(type && type == 'person'){
                    that._initPersonGrid({
                        clientId:data.smsClientId
                    });

                    $dialog.on("click","#uploadExcel",function(){
                        var $this = $(this);
                        if($this.attr("disabled")){
                            return;
                        }
                        if(!$("#uploadFile").val()){
                            alert("未选择导入的文件！");
                            return;
                        }

                        var FileController = that._gabalParam.uploadPersonUrl;                    // 接收上传文件的后台地址 
                        var xhr = new XMLHttpRequest();
                        xhr.open("post", FileController, true);
                        xhr.onload = function (oEvent) {
                            if (xhr.status == 200) {
                                alert("导入用户信息成功");
                            }else{
                                alert("导入用户信息失败");
                            }
                            // $this.attr("disabled");
                            $this.removeAttr("disabled");;
                        };
                        xhr.oncomponent = function(oEvent){

                        }
                        var formdata = new FormData($dialog.find("form")[0]);
                        $this.attr("disabled",true);
                        formdata.append("clientId",data.smsClientId);
                        xhr.send(formdata); //发送请求
                    })

                    $dialog.on("click",".ableBtn",function(){
                        //修改启用或禁用状态
                        var $thisBtn = $(this);
                        if($thisBtn.attr("disabled")){
                            return;
                        }
                        var datas = $("#smsPersonGrid").grid("getSelectedRows");
                        if(datas && datas.length > 0){
                            var params = {
                                ids : datas[0].id,
                                able : datas[0].locked?0:1,
                            }
                            $thisBtn.attr("disabled","disabled");
                            $.ajax({
                                url: that._gabalParam.ablePersonUrl,
                                async: true,
                                data: params,
                                type : "POST",
                                success: function(ret) {
                                    if(typeof ret == 'string'){
                                        ret = JSON.parse(ret);
                                        // that.dialog.close();
                                        that._reloadPersonGrid();
                                    }
                                    if (ret && ret.statusCode == '200') {
                                        alert("保存成功");
                                    } else {
                                        alert("操作失败");
                                    }
                                },
                                complete : function(){
                                    $thisBtn.removeAttr("disabled");
                                }
                            })
                        }
                    })
                    return;
                }

                
                $dialog.on("click","#createRsaKey",function(){
                    var $thisBtn = $(this);
                    if($thisBtn.attr("disabled")){
                        return;
                    }
                    $thisBtn.attr("disabled","disabled");
                    $.ajax({
                        url: that._gabalParam.getRsaKeyUrl,
                        async: true,
                        data: {},
                        type : "POST",
                        success: function(ret) {
                            if(typeof ret == 'string'){
                                ret = JSON.parse(ret);
                            }
                            if(ret && ret.statusCode == '200'){
                                //赋值RSAKEY值
                                $dialog.find('textarea[name="publicKey"]').text(ret.publicKey);
                                $dialog.find('textarea[name="privateKey"]').text(ret.privateKey);    
                            }
                        },
                        complete : function(){
                            $thisBtn.removeAttr("disabled");
                        }
                    })
                })

                var saveFormValidator = $dialog.find("form").validate(that.validate_setting);
                $dialog.find("#saveBtn").click(function(event){
                    //保存操作
                    
                    var $thisBtn = $(this);
                    if($thisBtn.attr("disabled")){
                        return;
                    }
                    //保存主题信息
                    if (!saveFormValidator.form()) {
                        return;
                    }

                    

                    var params = converToJson($dialog.find("form").serializeArray());
                    if(params.locked && (params.locked == 'on' || params.locked == 'yes')){
                        params.locked = 1;
                    }else{
                        params.locked = 0;
                    }

                    // if(params.encryptionFlag && (params.encryptionFlag == 'on' || params.encryptionFlag == 'yes')){
                    //     params.encryptionFlag = 1;
                    // }else{
                    //     params.encryptionFlag = 0;
                    // }

                    var flag = true;
                    if(params.sysCode){
                        $.ajax({
                            url: that._gabalParam.checkCode,
                            async: false,
                            data: {sysCode:params.sysCode},
                            type : "POST",
                            success: function(ret) {
                                if(typeof ret == 'string'){
                                    ret = JSON.parse(ret);
                                }
                                if (ret && ret.statusCode == '200') {
                                    flag = true;
                                } else {
                                    flag = false;
                                }
                            }
                        })
                    }

                    if(!flag){
                        alert("系统代码重复!");
                        return;
                    }

                    $thisBtn.attr("disabled","disabled");
                    $.ajax({
                        url: that._gabalParam.saveurl,
                        async: true,
                        data: params,
                        type : "POST",
                        success: function(ret) {
                            if(typeof ret == 'string'){
                                ret = JSON.parse(ret);
                                that.dialog.close();
                                that._reloadsmsClientGrid();
                            }
                            if (ret && ret.statusCode == '200') {
                                alert("保存成功");
                            } else {
                                alert("保存异常或系统代码重复");
                            }
                        },
                        complete : function(){
                            $thisBtn.removeAttr("disabled");
                        }
                    })
                })
            }
        }
        smsClientManagement._init();


        var smsMessageManagement = {
            _gabalParam : {
                queryUrl : contextPath + "/mx/sys/smsMessage/read",
            },
            _init : function(){
                var that = this;
                that.$grid = $("#smsMessageGrid");
                that._loadGrid();
                that._bindEvent();
            },
            _loadGrid : function(){
                var that = this;
                that.$grid.grid({
                    ajax: {
                        read: {
                            url: that._gabalParam.queryUrl,
                            async: true,
                            data: {},
                            success: function(ret) {
                                if (ret) {
                                    return {
                                        total: ret.total,
                                        rows: ret.data
                                    }
                                } else {
                                    return ret;
                                }
                            }
                        },
                        parameterMap: function(data) {
                            return JSON.stringify(data || {});
                        },
                        schema: {
                            model: {
                                id: "id",
                                fields: {
                                    }
                            },
                            data: "rows",
                            total: "total"
                        }
                    },
                    selectable: "sinagle",
                    scrollable: false,
                    columns:[
                        {title:'客户端ID', field:'clientId', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },},
                        {title:'服务器ID', field:'serverId', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },},
                        // {title:'文件代码', field:'sqliteCode', sortable:false,'isEditor':false, 'attributes': {
                        //     'style': 'text-align:center;width:10%;'
                        // },},
                        {title:'接收者', field:'name', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },},
                        {title:'手机号', field:'mobile', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },},
                        {title:'消息内容', field:'message', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:15%;'
                        },},
                        {title:'重试次数', field:'retryTimes', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:7%;'
                        },},
                        {title:'发送状态', field:'status', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:8%;'
                        },
                        template : function(data){
                            if(!data.status){
                                return "待发送";
                            }
                            if(data.status == 1){
                                return "发送失败";
                            }
                        }
                        },
                        {title:'待发送日期', field:'sendLaterTime_datetime', sortable:true,'isEditor':true, 
                        'editor':{'component':'datetimepicker',},'format':'yyyy-MM-dd HH:mm:ss',
                        'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },},
                        {title:'返回结果', field:'result', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:20%;'
                        },},
                    ],
                    pagination: {
                        paging: true,
                        page: 1,
                        pageSize: 10,
                        pageSizes: [5, 10, 15, 20, 25, 50, 100, 200, 500], //分页条数选择
                        buttonCount: 10,
                        previousNext: "true", //是否使能分页翻页按钮
                        numeric: "true", //是否使能数字页码按钮
                        input: "false", //是否使能输入框翻页
                        first: '首页', //首页按钮提示文本
                        last: '末页', //末页按钮提示文本
                        previous: '上一页', //上一页显示文本
                        next: '下一页', //下一页显示文本
                        refresh: 'false'
                    }
                })
            },
            _reloadGrid : function(){
                var that = this;
                that.$grid.grid("query");
            },
            _bindEvent : function(){
                var that = this;
                
            },
        };
        smsMessageManagement._init();

        var smsHistoryMessageManagement = {
            _gabalParam : {
                queryUrl : contextPath + "/mx/sys/smsHistoryMessage/read",
            },
            _init : function(){
                var that = this;
                that.$grid = $("#smsHistoryMessageGrid");
                that._loadGrid();
                that._bindEvent();
            },
            _loadGrid : function(){
                var that = this;
                that.$grid.grid({
                    ajax: {
                        read: {
                            url: that._gabalParam.queryUrl,
                            async: true,
                            data: {},
                            success: function(ret) {
                                if (ret) {
                                    return {
                                        total: ret.total,
                                        rows: ret.data
                                    }
                                } else {
                                    return ret;
                                }
                            }
                        },
                        parameterMap: function(data) {
                            return JSON.stringify(data || {});
                        },
                        schema: {
                            model: {
                                id: "id",
                                fields: {
                                    }
                            },
                            data: "rows",
                            total: "total"
                        }
                    },
                    selectable: "sinagle",
                    scrollable: false,
                    columns:[
                        {title:'客户端ID', field:'clientId', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },},
                        {title:'服务器ID', field:'serverId', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },},
                        // {title:'文件代码', field:'sqliteCode', sortable:false,'isEditor':false, 'attributes': {
                        //     'style': 'text-align:center;width:10%;'
                        // },},
                        {title:'接收者', field:'name', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },},
                        {title:'手机号', field:'mobile', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },},
                        {title:'消息内容', field:'message', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:15%;'
                        },},
                        {title:'重试次数', field:'retryTimes', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:7%;'
                        },},
                        {title:'发送状态', field:'status', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:8%;'
                        },
                        template : function(data){
                            if(!data.status){
                                return "待发送";
                            }
                            if(data.status == 1){
                                return "发送失败";
                            }
                            if(data.status == 2){
                                return "成功发送";   
                            }
                            return "发送失败"
                        }
                        },
                        {title:'待发送日期', field:'sendLaterTime_datetime', sortable:true,'isEditor':true, 
                        'editor':{'component':'datetimepicker',},'format':'yyyy-MM-dd HH:mm:ss',
                        'attributes': {
                            'style': 'text-align:center;width:10%;'
                        },},
                        {title:'返回结果', field:'result', sortable:false,'isEditor':false, 'attributes': {
                            'style': 'text-align:center;width:20%;'
                        },},
                    ],
                    pagination: {
                        paging: true,
                        page: 1,
                        pageSize: 10,
                        pageSizes: [5, 10, 15, 20, 25, 50, 100, 200, 500], //分页条数选择
                        buttonCount: 10,
                        previousNext: "true", //是否使能分页翻页按钮
                        numeric: "true", //是否使能数字页码按钮
                        input: "false", //是否使能输入框翻页
                        first: '首页', //首页按钮提示文本
                        last: '末页', //末页按钮提示文本
                        previous: '上一页', //上一页显示文本
                        next: '下一页', //下一页显示文本
                        refresh: 'false'
                    }
                })
            },
            _reloadGrid : function(){
                var that = this;
                that.$grid.grid("query");
            },
            _bindEvent : function(){
                var that = this;
                
            },
        };
        smsHistoryMessageManagement._init();
    });



</script>