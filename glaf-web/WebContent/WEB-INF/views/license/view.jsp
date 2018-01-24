<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/plugins/bootstrap/dialog/css/bootstrap-dialog.min.css">
<!--ztree-->
<link rel="stylesheet" href="${contextPath}/scripts/ztree/css/metroStyle/metroStyle.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<!--图片弹窗-->
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/fancybox/source/jquery.fancybox.css?v=2.1.5" media="screen" />
<!--grid-->
<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/grid/css/grid.css'>
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/plugins/bootstrap/treelist/css/treelist.css">
<!-- iCheck -->
<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/skins/all.css" rel="stylesheet" type="text/css" />
<!-- datepicker3 -->
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/plugins/bootstrap/datepicker/css/bootstrap-datepicker3.min.css"/>
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/plugins/bootstrap/datetimepicker/css/bootstrap-datetimepicker.min.css"/>
<!-- select2 -->
<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/scripts/license/license.css" rel="stylesheet" type="text/css" />
</head>
<body >
<div class="mainBody" style="height:100%;padding:5px 0px;">
    <!-- <div class="col-md-5">
        <div >
            <div id="systemInfoGrid"></div>
        </div>
    </div> -->
    <div class="portlet box blue strruleportlet baseLicensePortlet" style="border:1px solid #ccc;margin:3px 3px;">
        <div class="portlet-title" style="min-height: 0px;">
            <div class="" style="line-height: 30px;">
                License规则信息
            </div>
        </div>
        <div class="portlet-body">
            <div class="form-horizontal">
                <div class="form-group">
                    <div class="col-md-5">
                        <label class="control-label col-md-3">MAC码：</label>
                        <div class="col-md-8">
                            <input name="macCode" id="macCode" class="form-control" type="text" >
                        </div>
                    </div>
                    <div class="col-md-5">
                        <label class="control-label col-md-3">最大用户数量：</label>
                        <div class="col-md-8">
                            <input name="maxUserNum" id="maxUserNum" class="form-control" type="number" value="0">
                        </div>
                    </div>
                    <div class="col-md-2">
                        <button type="button" id="createLicenseBtn" class="btn green btn-sm" style=""><i class="glyphicon glyphicon-plus"></i>生成license文件</button>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-5">
                        <label class="control-label col-md-3">开始时间：</label>
                        <div class="col-md-8">
                            <div class="input-group date form_datetime component" id="startTime">
                                <input type="text" class="form-control id frame-variable" frame-variable="fv1">
                                <span class="input-group-btn">
                                    <button class="btn default form-control date-set" type="button">
                                    <i class="fa fa-clock-o"></i>
                                    </button>
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-5">
                        <label class="control-label col-md-3">结束时间：</label>
                        <div class="col-md-8">
                            <div class="input-group date form_datetime component" id="endTime">
                                <input type="text" class="form-control id frame-variable" frame-variable="fv1">
                                <span class="input-group-btn">
                                    <button class="btn default form-control date-set" type="button">
                                    <i class="fa fa-clock-o"></i>
                                    </button>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="portlet box light blue strruleportlet licensePortlet" style="margin:3px 3px;">
        <div class="portlet-title tabbable-line">
            
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="#tab_0" data-toggle="tab" aria-expanded="false"> 子系统权限配置</a>
                </li>
                <!-- <li class="">
                    <a href="#tab_1" data-toggle="tab" aria-expanded="false"> 模块权限设置</a>
                </li> -->
                <li class="">
                    <a href="#tab_2" data-toggle="tab" aria-expanded="false"> 表权限设置</a>
                </li>
            </ul>
        </div>
        <div class="portlet-body resizePortletBody" style="height:100%;background:#FAFAFA;">
            <div class="tab-content" style="height:100%;">
                <div class="tab-pane active" id="tab_0" style="height:100%;">
                    <div class=" row" style="height:100%;">
                        <div class="col-md-3 " style="padding:0px 5px;height:100%;">
                            <!--系统树-->
                            <div class="border_div slimScroll_parent" style="height:100%;">
                                <div class="slimScroll_div" style="height:100%;">
                                    <div id="systemZtree" class="ztree"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 " style="padding:0px 5px;height:100%;">
                            <div class="border_div upPanel" style="height:116px; padding:3px 3px;">
                                <!-- 已勾选的表格信息 -->
                                <div class="">
                                    <fieldset class="myFieldset">
                                        <legend>批量添加规则设置</legend>
                                        <div class="attrContent">
                                            <!-- <div id="systemRuleFormPanel">
                                            </div> -->
                                            <form id="systemRuleForm" class="form-group row " style="height:50px;margin-bottom:0px;">
                                            </form>
                                        </div>
                                    </fieldset>
                                </div>
                                <div class="" style="text-align:left;">
                                    <button type="button" id="selectSystemBtn" class="btn green btn-sm" style="padding:3px 10px;border-style:none;margin-top:3px;"><i class="glyphicon glyphicon-plus"></i>添加↓</button>
                                </div>
                            </div>
                            <div class="border_div downPanel" style="padding:3px 3px;">
                                <div id="selectedSystemGrid" style="height:100%;">
                                    
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3 " style="padding:0px 5px;height:100%;">
                            <!-- 当前选中行的规则信息 -->
                            <div class="selectedRuleSystem" style="height:100%;">
                            </div>
                        </div>
                    </div>
                </div>
                <!-- <div class="tab-pane" id="tab_1">
                    <div class="" id="content1">
                        <div>
                            模块树
                            <div id="modelZtree" class="ztree"></div>
                        </div>
                    </div>
                </div> -->
                <div class="tab-pane" id="tab_2" style="height:100%;">
                    <div class="row" id="content2" style="height:100%;">
                        <div class="col-md-3" style="height:100%;">
                            <div class="border_div" style="height:100%;">
                                <!--标段信息-->
                                <!-- <div class="row form-horizontal"> -->
                                <!-- <div class="col-md-12"> -->
                                <div style="text-align: center;padding-top:10px">
                                    <label class="control-label">标段：
                                        <select id="select-biaoduan" style="width : 150px;">
                                        </select>
                                    </label>
                                </div>
                                <!-- </div> -->
                                <!-- </div> -->
                                <!-- 类型 -->
                                <!-- <div class="form-horizontal"> -->
                                <!-- <div class="col-md-12"> -->
                                <div style="text-align: center;">
                                    <label class="control-label">类型：
                                        <select id="select-type" style="width : 150px;">
                                            
                                        </select>
                                    </label>
                                </div>
                                <!-- </div> -->
                                <!-- </div> -->
                                <!-- 查询 -->
                                <div style="text-align: center;">
                                    <label class="control-label">表名：
                                        <input id="research_name_input" type="text" style="width : 150px;">
                                    </label>
                                    <button type="button" id="research_name_Btn" class="btn blue btn-sm" style="padding:3px 10px;border-style:none;">
                                    <i class="fa fa-search">
                                    </i>查询
                                    </button>
                                </div>
                                <!--表信息树-->
                                <div class="slimScroll_parent">
                                    <div class="slimScroll_div">
                                        <div id="tableZtree" class="ztree"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 " style="height:100%;">
                            <div class="border_div" style="height:156px; padding:3px 3px;">
                                <!-- 已勾选的表格信息 -->
                                <div class="">
                                    <!-- <div class="col-md-7 border_div">
                                        <div> 已选勾选的表信息 </div>
                                        <div id="checkedTableGrid" style="height:300px;">
                                            
                                        </div>
                                    </div> -->
                                    <div class="col-md-12 border_div form-horizontal">
                                        <div> lincense规则信息 </div>
                                        <form id="tableRuleForm" class="form-horizontal " style="height:100px;margin-bottom:0px;">
                                            
                                            <!-- <label class="control-label col-md-3"></label>
                                            <div class="col-md-8">
                                                <input name="macCode" type="checkbox" >
                                            </div> -->
                                        </form>
                                    </div>
                                </div>
                                <div class="" style="text-align:left;">
                                    <button type="button" id="selectTableBtn" class="btn green btn-sm" style="padding:3px 10px;border-style:none;margin-top:3px;"><i class="glyphicon glyphicon-plus"></i>添加↓</button>
                                </div>
                            </div>
                            <div class="border_div downPanel" style="padding:3px 3px;">
                                <div id="selectedTableGrid" style="height:100%;">
                                    
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3 " style="padding:0px 5px;height:100%;">
                            <!-- 当前选中行的规则信息 -->
                            <div class="selectedRuleTable" style="height:100%;">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<template id="tableRuleForm_template">
<div class="form-group row">
    <div class="col-xs-3">
        <label class="control-label">允许创建表：<input type="checkbox" class="createAble" name="createAble" id="createAble" ></label>
    </div>
    <div class="col-xs-3">
        <label class="control-label">允许查询表：<input type="checkbox" class="retrieveAble" name="retrieveAble" id="retrieveAble" ></label>
    </div>
    <div class="col-xs-3">
        <label class="control-label">允许修改表：<input type="checkbox" class="updateAble" name="updateAble" id="updateAble" ></label>
    </div>
    <div class="col-xs-3">
        <label class="control-label">允许删除表：<input type="checkbox" class="deleteAble" name="deleteAble" id="deleteAble" ></label>
    </div>
    <div class="col-xs-9">
        <label class="control-label">允许最大纪录数：<input type="number" class="maxRowNum" name="maxRowNum" id="maxRowNum" value="0"></label>
    </div>
</div>
</template>
<template id="tableRuleFormEditTemplate">
    <fieldset class="myFieldset" style="height:100%;">
        <legend>
            <button type="button" class="changeTableRuleBtn btn blue btn-sm" style="padding:3px 10px;border-style:none;"><i class="glyphicon glyphicon-edit"></i>修改</button>
            当前选中的规则({{name}})
        </legend>
        <div class="attrContent" style="padding:15px;">
            <div class="form-group row">
                <label class="control-label">允许创建表：
                    {{if createAble}}
                        <input type="checkbox" class="createAble" name="createAble"  checked="true">
                    {{else}}
                        <input type="checkbox" class="createAble" name="createAble">
                    {{/if}}
                </label>
                <label class="control-label">允许查询表：
                    {{if retrieveAble}}
                        <input type="checkbox" class="retrieveAble" name="retrieveAble"  checked="true">
                    {{else}}
                        <input type="checkbox" class="retrieveAble" name="retrieveAble"  >
                    {{/if}}
                </label>    
                <label class="control-label">允许修改表：
                    {{if updateAble}}
                        <input type="checkbox" class="updateAble" name="updateAble"  checked="true">
                    {{else}}
                        <input type="checkbox" class="updateAble" name="updateAble"  >
                    {{/if}}
                </label>
                <label class="control-label">允许删除表：
                    {{if deleteAble}}
                        <input type="checkbox" class="deleteAble" name="deleteAble"  checked="true">
                    {{else}}
                        <input type="checkbox" class="deleteAble" name="deleteAble"  >
                    {{/if}}
                </label> 
                <label class="control-label">允许最大纪录数：
                    <input type="number" class="maxRowNum" name="maxRowNum" id="maxRowNum" value="{{maxRowNum}}">
                </label>
            </div>
        </div>
    </fieldset>
</template>
<template id="systemRuleForm_template">
    <!-- <label class="control-label">允许使用：</label> -->
    <div class="row">
        <label class="col-xs-3 control-label">访问权限：</label>
        <div class="col-xs-6 icheck-inline" style="margin-top:0px;">
            <label class="control-label">允许：
                <input type="radio" class="access" name="access" value="1" checked="true">
            </label>
            <label class="control-label">禁止：
                <input type="radio" class="access" name="access" value="0">
            </label>
            <!-- <label class="control-label">禁止使用：<input type="checkbox" class="ban" name="ban" ></label> -->
        </div>
    </div>
</template>
<template id="systemRuleFormEditTemplate">
    <fieldset class="myFieldset" style="height:100%;">
        <legend>
            <button type="button" class="changeSystemRuleBtn btn blue btn-sm" style="padding:3px 10px;border-style:none;"><i class="glyphicon glyphicon-edit"></i>修改</button>
            当前选中的规则({{name}})
        </legend>
        <div class="attrContent" style="padding:15px;">
            <div class="row">
                <label class="col-xs-3 control-label">访问权限：</label>
                <div class="col-xs-6 icheck-inline" style="margin-top:0px;">
                    {{if access == 0}}
                    <label class="control-label">允许：
                        <input type="radio" class="access" name="access" value="1" >
                    </label>
                    <label class="control-label">禁止：
                        <input type="radio" class="access" name="access" value="0" checked="true">
                    </label>
                    {{else}}
                    <label class="control-label">允许：
                        <input type="radio" class="access" name="access" value="1" checked="true">
                    </label>
                    <label class="control-label">禁止：
                        <input type="radio" class="access" name="access" value="0" >
                    </label>
                    {{/if}}
                    <!-- <label class="control-label">禁止使用：<input type="checkbox" class="ban" name="ban" ></label> -->
                </div>
            </div>
        </div>
    </fieldset>
</template>
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
<!-- ztree-->
<script type="text/javascript" src="${contextPath}/scripts/kendo/bootstrap/ztree.extend.js"></script>
<!-- dialog -->
<script src="${contextPath}/scripts/plugins/bootstrap/dialog/js/bootstrap-dialog.min.js"></script>
<script src="${contextPath}/scripts/plugins/bootstrap/dialog/js/jquery.dialog.extends.js"></script>
<!--grid-->
<script src='${contextPath}/scripts/plugins/bootstrap/grid/js/jquery.grid.extends.js' type='text/javascript'></script>
<script src="${contextPath}/scripts/plugins/bootstrap/treelist/js/jquery.treelist.extends.js" type="text/javascript"></script>
<!-- iCheck -->
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/icheck.js" type="text/javascript"></script>
<!-- datepicker -->
<script type="text/javascript" src="${contextPath}/scripts/plugins/bootstrap/datepicker/ext/jquery.datepicker.extends.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/plugins/bootstrap/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/plugins/bootstrap/datetimepicker/ext/jquery.datetimepicker.extends.js"></script>
<!-- slimscroll -->
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<!-- select2 -->
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
<!-- validate -->
<script src="${contextPath}/scripts/jquery-validation/dist/jquery.validate.min.js"></script>
<!-- altTemplate -->
<script src="${contextPath}/scripts/artTemplate/dist/template.js"></script>
<script src="${contextPath}/scripts/artTemplate/dataUtils.js"></script>
<script src="${contextPath}/scripts/license/license.js"></script>