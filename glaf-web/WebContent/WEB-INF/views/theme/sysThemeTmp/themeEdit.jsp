<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>主题编辑器</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-summernote/summernote.css" rel="stylesheet" type="text/css" />
<!-- BEGIN THEME GLOBAL STYLES -->
<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/css/components.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/apps/css/inbox.min.css" rel="stylesheet" type="text/css"/>
<!-- dialog -->
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/plugins/bootstrap/dialog/css/bootstrap-dialog.min.css">
<!--ztree-->
<link rel="stylesheet" href="${contextPath}/scripts/ztree/css/metroStyle/metroStyle.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<!--grid-->
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/plugins/bootstrap/pagination/css/pagination.css"/>
<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/grid/css/grid.css'>
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/plugins/bootstrap/treelist/css/treelist.css">
<!--开始颜色选择器-->
<link href="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-colorpicker/css/colorpicker.css" rel="stylesheet" type="text/css" />
<link href="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-minicolors/jquery.minicolors.css" rel="stylesheet" type="text/css" />
<!--开始数值增量输入框-->
<link href="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-touchspin/bootstrap.touchspin.css" rel="stylesheet" type="text/css" />
<!-- jplayer -->
<link rel="stylesheet" type="text/css" media="screen" href="/glaf/scripts/plugins/bootstrap/jPlayer/css/jplayer.blue.monday.min.css">
<link rel="stylesheet" type="text/css" media="screen" href="/glaf/scripts/theme/sysThemeTmp/theme.css">
</head>
<body style="height:100%;">
<style id="gobalStyle"></style>
<div class="portlet box blue strruleportlet" style="border:1px solid #ccc; margin: 0px 3px; height:100%;">
    <div class="portlet-title" style="min-height: 0px;">
        <!-- <div class="" style="line-height:30px;float:left;">
            主题信息
        </div> -->
        <ul class="nav nav-tabs" style="float:left">
            <li class="active">
                <a href="#themeInfoPanel" data-toggle="tab"> 主题信息 </a>
            </li>
            <li>
                <a href="#portlet_tab_2" data-toggle="tab"> 主题编辑 </a>
            </li>
        </ul>
        <div class="" style="padding-top: 5px; line-height: 30px; float:right;">
            <button type="button" id="saveLayoutStyleBtn" class="btn btn-danger btn-sm " style="padding-top: 1px;
            padding-bottom: 1px;">
            <i class="fa fa-save"></i>保存主题样式
            </button>
            <button type="button" id="buildThemeStyleBtn" class="btn green-haze btn-sm " style="padding-top: 1px;background-color: #14a354;padding-bottom: 1px;">
            <i class="fa fa-save"></i>生成主题样式
            </button>
            <button type="button" id="prevAllControlBtn" class="btn default btn-sm " style="padding-top: 1px;padding-bottom: 1px;">
            <i class="fa fa-save"></i>全部控件预览
            </button>
        </div>
    </div>
    <div class="portlet-body" style="">
        <div class="tab-content" style="height:100%;">
            <div class="tab-pane themeInfoPanel active" id="themeInfoPanel" style="overflow:hidden; height:100%;">
                
            </div>
            <div class="tab-pane themeMainPanel" id="portlet_tab_2" style="overflow:hidden; height:100%;">
                <div class="theme_panel themeTreePanel">
                    <div class="themeTreeSlimScroll_div">
                        <div id="themeTree" class="ztree">
                        </div>
                    </div>
                </div>
                <div class="theme_panel theme_right_panel" style="">
                    <div class="dspPanel compDspPanel" style="display:none;">
                        <div id="compDsp" >
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="stylePanel" style="display:none;">
</div>
<div class="hidemy-modal">
    <div id="freelayoutModal" class="modal fade in" role="dialog" aria-hidden="true" style="min-height: 250px; display: none; padding-right: 17px;    z-index: 999999;">
        <div class="modal-dialog" style="min-width:200px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button class="close" aria-label="Close" type="button" data-dismiss="modal"><span aria-hidden="true">×</span></button>
                </div>
                <div class="modal-body" style="padding:3px;">
                    <span>布局设置</span>
                    <table class="bannerL">
                        <tbody>
                            <tr>
                                <td style="width:150px;">
                                    <div class="text-center laycell" layno="1" style="height:25px;width:150px;">1</div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <table class="bodyL">
                        <tbody>
                            <tr>
                                <td class="left" style="width:30px;">
                                    <div class="text-center laycell" layno="2_1" style="height: 120px;width:30px;">2</div>
                                </td>
                                <td class="main" style="width:90px;">
                                    <div class="text-center laycell" layno="2_2_1" style="height: 25px;width: 86px;display:block;margin-left:2px;">5</div>
                                    <div class="text-center laycell" layno="2_2_2_1" style="height: 66px;width: 40px;display: inline-block;margin-left: 2px;margin-top: 2px;margin-bottom: 2px;">6</div>
                                    <div class="text-center laycell" layno="2_2_2_2" style="height: 66px;width: 40px;display:inline-block;margin-left: 2px;margin-top: 2px;margin-bottom: 2px;">7</div>
                                    <div class="text-center laycell" layno="2_2_3" style="height: 25px;width: 86px;display:block;margin-left:2px;">8</div>
                                </td>
                                <td class="right" style="width:30px;">
                                    <div class="text-center laycell" layno="2_3" style="height: 120px;width:30px;">3</div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <table class="footerL">
                        <tbody>
                            <tr>
                                <td style="width:150px;">
                                    <div class="text-center laycell" layno="3" style="height:25px;width:150px;">4</div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<template id="layout_attr_item_template">
</template>
<template id="left_menu">
    <ul class="dropdown-menu myMenu" role="menu" style="font-size:12px;">
        <li class="dropdown-submenu add_layout_menu">
            新建布局
        </li>
        <li class="dropdown-submenu add_control_menu">
            新建控件/模板
        </li>
        <li class="dropdown-submenu add_layout_area_menu">
            新建布局模板
        </li>
        <li class="dropdown-submenu add_layout_control_menu">
            新建控件/容器
        </li>
        <li class="dropdown-submenu set_layout_menu">
            设置布局格式
        </li>
        <li class="dropdown-submenu edit_menu">
            修改
        </li>
    </ul>
</template>
<template id="edit_layout_template">
    <form style="padding:10px 10px;overflow:hidden;" class="form-horizontal" enctype="multipart/form-data">
        {{if themeTmpId}}
        <input type="hidden" name="themeTmpId" value="{{themeTmpId}}">
        {{/if}}
        {{if layoutId}}
        <input type="hidden" name="layoutId" value="{{layoutId}}">
        {{/if}}
        <div class="col-xs-4">
            <div class="theme_thumbnail_panel">
                <a class="theme_thumbnail ">
                    <input class="theme_thumbnail theme_thumbnail_upload" type="file" name="file" id="thumbnail_upload">
                    <span class="btn blue">设置缩略图</span>
                </a>
                {{if layoutId}}
                <img class="theme_thumbnail" id="theme_thumbnail_show" src="${contextPath}/mx/theme/sysThemeTmpLayout/downloadThumbnail?layoutId={{layoutId}}&_{{nowdate}}">
                {{else}}
                <img class="theme_thumbnail" id="theme_thumbnail_show" src="${contextPath}/scripts/designer/images/none.jpg">
                {{/if}}
                
            </div>
            <div class="theme_thumbnail_title">缩略图</div>
        </div>
        <div class="col-xs-8">
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">名称：</label>
                    <div class="col-xs-8">
                        <input type="text" name="layoutName" class="form-control" maxlength="50" required value="{{layoutName}}">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">代码：</label>
                    <div class="col-xs-8">
                        {{if layoutId}}
                        <input type="text" disabled="true" readonly="true" class="form-control" maxlength="50" required value="{{layoutCode}}">
                        {{else}}
                        <input type="text" name="layoutCode" class="form-control" maxlength="50" required>
                        {{/if}}
                    </div>
                </div>
            </div>
            {{if layoutId}}
            
            {{else}}
            <div class="form-group layoutShow">
                <div class="row">
                    <label class="control-label col-xs-3">布局：</label>
                    <div class="col-xs-8">
                        <table class="bannerL">
                            <tbody>
                                <tr>
                                    <td style="width:150px;">
                                        <div class="text-center laycell" layno="1" style="height:25px;width:150px;" code="lay_1">1</div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <table class="bodyL">
                            <tbody>
                                <tr>
                                    <td class="left" style="width:30px;">
                                        <div class="text-center laycell" layno="2_1" style="height: 120px;width:30px;" code="lay_2_1">2</div>
                                    </td>
                                    <td class="main" style="width:90px;">
                                        <div class="text-center laycell" layno="2_2_1" style="height: 25px;width: 86px;display:block;margin-left:2px;" code="lay_2_2_1">5</div>
                                        <div class="text-center laycell" layno="2_2_2_1" style="height: 66px;width: 40px;display: inline-block;margin-left: 2px;margin-top: 2px;margin-bottom: 2px;" code="lay_2_2_2_1">6</div>
                                        <div class="text-center laycell" layno="2_2_2_2" style="height: 66px;width: 40px;display:inline-block;margin-left: 2px;margin-top: 2px;margin-bottom: 2px;" code="lay_2_2_2_2">7</div>
                                        <div class="text-center laycell" layno="2_2_3" style="height: 25px;width: 86px;display:block;margin-left:2px;" code="lay_2_2_3">8</div>
                                    </td>
                                    <td class="right" style="width:30px;">
                                        <div class="text-center laycell" layno="2_3" style="height: 120px;width:30px;" code="lay_2_3">3</div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <table class="footerL">
                            <tbody>
                                <tr>
                                    <td style="width:150px;">
                                        <div class="text-center laycell" layno="3" style="height:25px;width:150px;" code="lay_3">4</div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            {{/if}}
        </div>
        <div class="form-group">
            <div class="row" style="text-align:center">
                <button type="button" id="saveBtn" class="btn blue btn-sm " >
                <i class="glyphicon glyphicon-ok"></i>创建
                </button>
            </div>
        </div>
    </form>
</template>
<template id="edit_control_template">
    <form style="padding:10px 10px;overflow:hidden;" class="form-horizontal" enctype="multipart/form-data">
        {{if themeTmpId}}
        <input type="hidden" name="themeTmpId" value="{{themeTmpId}}">
        {{/if}}
        {{if controlId}}
        <input type="hidden" name="controlId" value="{{controlId}}">
        {{/if}}
        <div class="col-xs-4">
            <div class="theme_thumbnail_panel">
                <a class="theme_thumbnail ">
                    <input class="theme_thumbnail theme_thumbnail_upload" type="file" name="file" id="thumbnail_upload">
                    <span class="btn blue">设置缩略图</span>
                </a>
                {{if controlId}}
                <img class="theme_thumbnail" id="theme_thumbnail_show" src="${contextPath}/mx/theme/sysThemeTmpControl/downloadThumbnail?controlId={{controlId}}&_{{nowdate}}">
                {{else}}
                <img class="theme_thumbnail" id="theme_thumbnail_show" src="${contextPath}/scripts/designer/images/none.jpg">
                {{/if}}
                
            </div>
            <div class="theme_thumbnail_title">缩略图</div>
        </div>
        <div class="col-xs-8">
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">名称：</label>
                    <div class="col-xs-8">
                        <input type="text" name="controlName" class="form-control" maxlength="50" required value="{{controlName}}">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">代码：</label>
                    <div class="col-xs-8">
                        {{if controlId}}
                        <input type="text" disabled="true" readonly="true" class="form-control" maxlength="50" required value="{{controlCode}}">
                        {{else}}
                        <input type="text" name="controlCode" class="form-control" maxlength="50" required>
                        {{/if}}
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">类型：</label>
                    <div class="col-xs-8">
                        {{if compType}}
                        <select name="compType" class="form-control" disabled="true" >
                            {{each componentTypeData as data i}}
                            {{if data.dataRole==compType}}
                            <option value="{{data.dataRole}}" selected>{{data.name}}</option>
                            {{/if}}
                            {{/each}}
                        </select>
                        {{else}}
                        <select name="compType" class="form-control" required >
                            {{each componentTypeData as data i}}
                            <option value="{{data.dataRole}}">{{data.name}}</option>
                            {{/each}}
                        </select>
                        {{/if}}
                    </div>
                </div>
            </div>
            <!-- <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">符合模板：</label>
                    <div class="col-xs-8">
                        <label>是:<input type="radio" name="compositionFlag" class="icheck" value="12" checked="checked" style="margin-top: 10px"></label>
                        <label>否:<input type="radio" name="compositionFlag" class="icheck" value="24" style="margin-top: 10px" checked="checked"></label>
                    </div>
                </div>
            </div> -->
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">模板：</label>
                    <div class="col-xs-8">
                        <textarea rows="8" style="width:100%" name="htmlText">{{HTML}}</textarea>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">全局控件：</label>
                    <div class="col-xs-8">
                        {{if commonFlag}}
                        <label>是:<input type="radio" name="commonFlag" class="icheck" value="1" checked="checked" style="margin-top: 10px"></label>
                        <label>否:<input type="radio" name="commonFlag" class="icheck" value="0" style="margin-top: 10px"></label>
                        {{else}}
                        <label>是:<input type="radio" name="commonFlag" class="icheck" value="1" style="margin-top: 10px"></label>
                        <label>否:<input type="radio" name="commonFlag" class="icheck" value="0" style="margin-top: 10px" checked="checked"></label>
                        {{/if}}
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">容器标识：</label>
                    <div class="col-xs-8">
                        {{if containerFlag}}
                        <label>是:<input type="radio" name="containerFlag" class="icheck" value="1" checked="checked" style="margin-top: 10px"></label>
                        <label>否:<input type="radio" name="containerFlag" class="icheck" value="0" style="margin-top: 10px"></label>
                        {{else}}
                        <label>是:<input type="radio" name="containerFlag" class="icheck" value="1" style="margin-top: 10px"></label>
                        <label>否:<input type="radio" name="containerFlag" class="icheck" value="0" style="margin-top: 10px" checked="checked"></label>
                        {{/if}}
                    </div>
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
    </form>
</template>
<template id="edit_layout_area_control_template">
    <form style="padding:10px 10px;overflow:hidden;" class="form-horizontal" enctype="multipart/form-data">
        {{if themeTmpId}}
        <input type="hidden" name="themeTmpId" value="{{themeTmpId}}">
        {{/if}}
        {{if pcontrolId}}
        <input type="hidden" name="pcontrolId" value="{{pcontrolId}}">
        {{/if}}
        <!--区域ID-->
        <input type="hidden" name="areaId" value="{{areaId}}">
        <!--布局ID-->
        <input type="hidden" name="layoutId" value="{{layoutId}}">
        {{if controlId}}
        <input type="hidden" name="controlId" value="{{controlId}}">
        {{/if}}
        <div class="col-xs-4">
            <div class="theme_thumbnail_panel">
                <a class="theme_thumbnail ">
                    <input class="theme_thumbnail theme_thumbnail_upload" type="file" name="file" id="thumbnail_upload">
                    <span class="btn blue">设置缩略图</span>
                </a>
                {{if controlId}}
                <img class="theme_thumbnail" id="theme_thumbnail_show" src="${contextPath}/mx/theme/sysThemeTmpLayoutAreaControl/downloadThumbnail?controlId={{controlId}}&_{{nowdate}}">
                {{else}}
                <img class="theme_thumbnail" id="theme_thumbnail_show" src="${contextPath}/scripts/designer/images/none.jpg">
                {{/if}}
            </div>
            <div class="theme_thumbnail_title">缩略图</div>
        </div>
        <div class="col-xs-8">
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">名称：</label>
                    <div class="col-xs-8">
                        <input type="text" name="controlName" class="form-control" maxlength="50" required value="{{controlName}}">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">代码：</label>
                    <div class="col-xs-8">
                        {{if controlId}}
                        <input type="text" disabled="true" readonly="true" class="form-control" maxlength="50" required value="{{controlId}}">
                        {{else}}
                        <input type="text" name="controlCode" class="form-control" maxlength="50" required>
                        {{/if}}
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">类型：</label>
                    <div class="col-xs-8">
                        {{if compType}}
                        <select name="compType" class="form-control" disabled="true" >
                            {{each componentTypeData as data i}}
                            {{if data.dataRole==compType}}
                            <option value="{{data.dataRole}}" selected>{{data.name}}</option>
                            {{/if}}
                            {{/each}}
                        </select>
                        {{else}}
                        <select name="compType" class="form-control" required >
                            {{each componentTypeData as data i}}
                            <option value="{{data.dataRole}}">{{data.name}}</option>
                            {{/each}}
                        </select>
                        {{/if}}
                    </div>
                </div>
            </div>
            <!-- <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">符合模板：</label>
                    <div class="col-xs-8">
                        <label>是:<input type="radio" name="compositionFlag" class="icheck" value="12" checked="checked" style="margin-top: 10px"></label>
                        <label>否:<input type="radio" name="compositionFlag" class="icheck" value="24" style="margin-top: 10px" checked="checked"></label>
                    </div>
                </div>
            </div> -->
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">容器标识：</label>
                    <div class="col-xs-8">
                        {{if containerFlag}}
                        <label>是:<input type="radio" name="containerFlag" class="icheck" value="1" checked="checked" style="margin-top: 10px"></label>
                        <label>否:<input type="radio" name="containerFlag" class="icheck" value="0" style="margin-top: 10px"></label>
                        {{else}}
                        <label>是:<input type="radio" name="containerFlag" class="icheck" value="1" style="margin-top: 10px"></label>
                        <label>否:<input type="radio" name="containerFlag" class="icheck" value="0" style="margin-top: 10px" checked="checked"></label>
                        {{/if}}
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row" style="text-align:center">
                <button type="button" id="saveBtn" class="btn blue btn-sm " >
                <i class="glyphicon glyphicon-ok"></i>创建
                </button>
            </div>
        </div>
    </form>
</template>
<template id="settingItem_template">
    <div class="setting_item" style="width:100%">
        <div class="" style="background-color: #44a6f5;color: white;font-size: 22px;">
            <span class="setting_item_name"> </span>
        </div>
        <div class="setting_item_panel" style="width:100%;">
            <div class=" setting_left_panel display_panel page" style="width:65%;display: inline-block;padding:0px 15px;">
            </div>
            <div class="setting_right_panel style_setting_panel" style="display: inline-block;vertical-align: top;">
            </div>
        </div>
    </div>
</template>
<template id="gobalControls_template">
    <div style="height:100%">
        <div class="leftPanel ">
            <div class="gobalControlsSlimScroll" style="height:100%">
                <div class="gobalControls ">
                </div>
            </div>
        </div>
        <div class="rightPanel">
            <div class="attrContentSlimScroll" style="height:100%">
                <div class="gobalAttrContentPanel" style="height:100%"></div>
            </div>
        </div>
    </div>
</template>
<template id="allGobalControls_template">
    <div style="height:100%">
        <div class="gobalControlsSlimScroll" style="height:100%">
            <div class="gobalControls ">
            </div>
        </div>
    </div>
</template>
<template id="gobalAttrContent_template">
    <div style="height:100%;">
        <fieldset class="myFieldset" style="height:100%;">
            <legend>{{name}}({{cmpName}})</legend>
            <div class="form-inline attrContent"></div>
        </fieldset>
    </div>
</template>
<template id="gobalControlType_template">
    <div class="gobalControlType">
    </div>
</template>
<template id="gobalControlItem_template">
    <div class="gobalControlItem">
        <div class="content">
        </div>
    </div>
</template>
<template id="mainStylePanel_template">
    <div class="mainStylePanel" style="height:100%">
        <div class="leftPanel">
            <div class="settingPanel baseAttrContent active">
                <div class="baseAttrContentTop top" style="">
                    <i class="fa fa-cog"></i><span>基础配置 </span>
                    <a href="javascript:;" class="collapse" style=""> </a>
                </div>
                <div class="form-inline content baseAttrContentContent"></div>
            </div>
            <div class="settingPanel highAttrContent">
                <div class="highAttrContentTop top" style="">
                    <i class="fa fa-cog"></i><span>高级配置 </span>
                    <a href="javascript:;" class="collapse" style=""> </a>
                </div>
                <div class="form-inline content highAttrContentContent"></div>
            </div>
        </div>
        <div class="rightPanel">
            <div class="exampleDisplay" style="height:100%;width:100%;">
                ABCDEFGHIJKLMNOPQRSTUVWXYZ<br>
                FUJIANHUAMINTONGDA<br>
                福建华闽通达<br>
            </div>
        </div>
    </div>
</template>
<template id="controlPanel_template">
    <div class="controlPanel" style="height:100%;width:100%;">
        <div style="vertical-align: top;width:245px;float: left;height:100%;border-right:1px solid #8d8d8d;">
            <div class="slimScrollDivPanel controlTypePanel" style="height:100%;">
            </div>
        </div>
        <div style="width: 206px;padding:3px 2px;vertical-align: top;float: left;height:100%;border-right:1px solid #8d8d8d;">
            <div class="slimScrollDivPanel controlThemePanel" style="height:100%;">
            </div>
        </div>
        <div class="resizeWidthPanel" style="width:30%;vertical-align: top;float: left;height:100%;border-right:1px solid #8d8d8d;">
            <div class="slimScrollDivPanel controlDislayPanel" style="height:100%;">
            </div>
        </div>
        <div style="width:310px;vertical-align: top;float: left;height:100%;">
            <div class="slimScrollDivPanel controlAttrContent form-inline" style="height:100%;">
            </div>
        </div>
    </div>
</template>
<template id="controlItem_template">
    <div class="theme_item">
        <span class="mask">
            <div class="operatorGroup">
                <button class="btn changeControlItemBtn">修改</button>
                <button class="btn editControlItemBtn">编辑</button>
                <br>
                <button class="btn setDefaultBtn" style="margin-top:10px;">设置为默认控件</button>
            </div>
        </span>
        <img class="theme_thumbnail" src="${contextPath}/mx/theme/sysThemeTmpControl/downloadThumbnail?controlId={{controlId}}&_{{nowdate}}" onerror="javascript:this.src='${contextPath}/scripts/designer/images/none.jpg'">
        <div class="item_name_div">
            <span class="item_name">{{name}}
                {{if commonFlag == 1}}
                <span style="color:red">(全局)</span>
                {{/if}}
                {{if defaultFlag == 1}}
                <span class="defaultFlag" style="color:red">(默认)</span>
                {{/if}}
            </span>
        </div>
    </div>
</template>
<template id="controlThemeAdd_template">
    <div class="theme_item theme_add">
        <span class="mask">
            <div class="operatorGroup">
                <button class="btn add_theme_btn">新增</button>
            </div>
        </span>
        <div>
            <a class="btn addBtn">
                <i class="fa fa-plus-circle"></i>
            </a>
        </div>
    </div>
</template>
<!--   <template id="gobalControlSettingItem_template">
    <div class="setting_item" style="width:100%">
        <div class="" style="background-color: #44a6f5;color: white;font-size: 22px;">
            <span class="setting_item_name"> </span>
        </div>
        <div class="setting_item_panel" style="width:100%;">
            <div class=" setting_left_panel display_panel page" style="width:65%;display: inline-block;padding:0px 15px;">
            </div>
            <div class="setting_right_panel style_setting_panel" style="display: inline-block;vertical-align: top;">
            </div>
        </div>
    </div>
</template> -->
<template id="attrItem_template">
    <div class="attrItem" style="padding:4px 5px;">
        <fieldset class="myFieldset">
            <legend>XXX区域/布局</legend>
            <div class="form-inline attrContent"></div>
        </fieldset>
    </div>
</template>
<template id="themeEditTemplate">
    <form style="padding:10px 10px;overflow:hidden;" id="themeEditForm" class="form-horizontal" enctype="multipart/form-data">
        {{if themeTmpId}}
        <input type="hidden" name="themeTmpId" value="{{themeTmpId}}">
        {{/if}}
        <div class="col-xs-4">
            <div style="float: right;">
                <div class="theme_thumbnail_panel">
                    <a class="theme_thumbnail ">
                        <input class="theme_thumbnail theme_thumbnail_upload" type="file" name="file" id="thumbnail_upload">
                        <span class="btn blue">设置缩略图</span>
                    </a>
                    {{if themeTmpId}}
                    <img class="theme_thumbnail" id="theme_thumbnail_show" src="${contextPath}/mx/theme/sysThemeTmp/downloadThumbnail?themeTmpId={{themeTmpId}}&_{{nowdate}}" onerror="javascript:this.src='${contextPath}/scripts/designer/images/none.jpg'">
                    {{else}}
                    <img class="theme_thumbnail" id="theme_thumbnail_show" src="${contextPath}/scripts/designer/images/none.jpg">
                    {{/if}}
                    
                    
                </div>
                <div class="theme_thumbnail_title">缩略图</div>
            </div>
        </div>
        <div class="col-xs-8">
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">名称：</label>
                    <div class="col-xs-8">
                        <input type="text" name="themeTmpName" class="form-control" value="{{themeTmpName}}" maxlength="50" required>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">代码：</label>
                    <div class="col-xs-8">
                        {{if themeTmpId}}
                        <input type="text" disabled="true" readonly="true" value="{{themeTmpId}}" class="form-control" maxlength="50" required>
                        {{else}}
                        <input type="text" name="themeTmpCode" class="form-control" maxlength="50" required>
                        {{/if}}
                        
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">适用UI：</label>
                    <div class="col-xs-8">
                        <select name="ui" class="form-control" required>
                            {{each uiTypeData as uiData i}}
                            {{if !ui || ui != uiData.code}}
                            <option value="{{uiData.code}}">{{uiData.name}}</option>
                            {{else}}
                            <option value="{{uiData.code}}" selected="true">{{uiData.name}}</option>
                            {{/if}}
                            {{/each}}
                        </select>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row" style="text-align:center">
                <button type="button" id="saveThemeBtn" class="btn blue btn-sm " >
                <i class="glyphicon glyphicon-ok"></i>保存修改
                </button>
            </div>
        </div>
    </form>
</template>
<!-- 引入控件的template -->
<%@ include file="../../designer/metro_template.jsp"%>
</body>
</html>
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
<script type="text/javascript" src="${contextPath}/scripts/plugins/bootstrap/pagination/js/jquery.pagination.extends.js"></script>
<script src='${contextPath}/scripts/plugins/bootstrap/grid/js/jquery.grid.extends.js' type='text/javascript'></script>
<script src="${contextPath}/scripts/plugins/bootstrap/treelist/js/jquery.treelist.extends.js" type="text/javascript"></script>
<!-- 表单验证 -->
<script src="${contextPath}/scripts/jquery.validate.min.js" type="text/javascript"></script>
<!-- altTemplate -->
<script src="/glaf/scripts/artTemplate/dist/template.js"></script>
<script src="/glaf/scripts/artTemplate/dataUtils.js"></script>
<script src="/glaf/scripts/plugins/bootstrap/touchspin/ext/jquery.touchspin.js"></script>
<!-- 开始颜色选择器 -->
<script src="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.js" type="text/javascript"></script>
<script src="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-minicolors/jquery.minicolors.min.js" type="text/javascript"></script>
<%@ include file="bootstrapTheme.jsp"%>
<script type ="text/javascript">
var initContainer = function(){};
var disableSortable = function(){};
var enableSortable = function(){};
</script>
<script src="/glaf/scripts/designer/comp_render_template.js" type="text/javascript"></script>
<!-- 属性面板 -->
<script src="/glaf/scripts/theme/sysThemeTmp/layout_attr.js"></script>
<script src="/glaf/scripts/theme/sysThemeTmp/attr_toolbox.js"></script>
<script src="/glaf/scripts/theme/sysThemeTmp/attrfunc.js"></script>
<script type ="text/javascript">
var contextPath = '${contextPath}';
var _themeId = '${themeId}';
</script>
<script src="/glaf/scripts/theme/sysThemeTmp/theme.js"></script>