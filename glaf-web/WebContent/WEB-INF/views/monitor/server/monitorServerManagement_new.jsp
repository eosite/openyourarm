<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>监控终端管理</title>
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
        <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/plugins/bootstrap/pagination/css/pagination.css"/>
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

        
        <link href="${contextPath}/scripts/monitor/server/monitorServerManagement_new.css" rel="stylesheet" type="text/css" />

        <style type="text/css">
            
        </style>
</head>
<body style="padding:0px 0px !important;">
    <div class="portlet box light dark strruleportlet terminalPanel" style="margin:0px 0px;">
        <div class="portlet-title tabbable-line">
            <div class="caption">
                <i class="fa fa-cog"></i>监控终端管理
            </div>
        </div>
        <div class="portlet-body">
            <div class="parentPanel">
                <div class="contentPanel leftPanel">
                    <div class="parentPanel">
                        <!-- <div class="toolbar">
                            <button type="button" id="addZtreeBortherBtn" class="btn green-haze btn-sm saveBtn" style="padding-top: 1px;padding-bottom: 1px;">
                                <i class="fa fa-save"></i>新增同级
                            </button>
                            <button type="button" id="addZtreeChildrenBtn" class="btn green-haze btn-sm saveBtn" style="padding-top: 1px;padding-bottom: 1px;" disabled>
                                <i class="fa fa-save"></i>新增子级
                            </button>
                            <button type="button" id="editZtreeBtn" class="btn blue btn-sm editBtn" style="padding-top: 1px;padding-bottom: 1px;" disabled>
                                <i class="glyphicon glyphicon-edit"></i>修改
                            </button>
                            <button type="button" id="deleteZtreeBtn" class="btn btn-danger btn-sm deleteBtn" style="padding-top: 1px;padding-bottom: 1px;" disabled>
                                <i class="fa fa-save"></i>删除
                            </button>
                        </div> -->
                        <div style="padding-bottom:20px;padding-top:20px;">
                            <span class="leftPanelTiele">资源监控</span>
                        </div>
                        <div id="categoryTree" class=" dynamicPanelHeight"></div>    
                    </div>
                </div>
                <div class="contentPanel rightPanel dynamicPanelWidth">
                    
                </div>
            </div>
        </div>
    </div>
</body>
</html>

<template id="terminatePanel">
    <div class="parentPanel terminatePanel" style="padding:10px 10px;margin-bottom: 10px;">
        <!-- <div class="title" style="border-bottom: 2px solid dimgray;"> -->
        <div style="padding-bottom:20px;">
            <div class="titleTip">
                <span>
                全部服务器信息
                </span>
                <!-- <span class="titleName" style="font-size: 25px;font-weight: bold;">全部服务器信息</span> -->
            </div>
            <div class="toolbar terminalListPanel" style="margin-bottom:10px;overflow: hidden;">
                <!-- <button type="button" class="btn terminalBtn">
                    服务器1
                </button> -->
            </div>
        </div>
        <div class="dynamicPanelHeight parentPanel" style="padding-right:20px;">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="#terminateMinitorInfo" class="tabbtn" class="btn" data-toggle="tab"> 详细信息 </a>
                </li>
                <li>
                    <a href="#procInfo" class="tabbtn" data-toggle="tab"> 服务/进程 </a>
                </li>
            </ul>
            <div class="tab-content dynamicPanelHeight" style="overflow-y:scroll;">
                <div class="tab-pane fade active in" id="terminateMinitorInfo">
                    
                </div>
                <div class="tab-pane fade" id="procInfo">
                    <div class="toolbar">
                        <button type="button" id="terminateBtn" class="btn green-haze btn-sm deleteBtn" style="margin-right: 5px;float:right;padding-top: 1px;padding-bottom: 1px;" disabled>
                            <i class="fa fa-stop"></i>终止
                        </button>
                        <button type="button" id="stopBtn" class="btn green-haze btn-sm deleteBtn" style="margin-right: 5px;float:right;padding-top: 1px;padding-bottom: 1px;" disabled>
                            <i class="fa fa-pause"></i>停止
                        </button>
                        <button type="button" id="startBtn" class="btn green-haze btn-sm deleteBtn" style="margin-right: 5px;float:right;padding-top: 1px;padding-bottom: 1px;" disabled>
                            <i class="fa fa-play"></i>启动
                        </button>
                    </div>
                    <div id="procGrid">
                        
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<template id="terminateMinitorInfoPanel">
    <form style="padding:10px 10px;overflow:hidden;" class="form-horizontal">
        <div class="col-xs-6">
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">操作系统名称：</label>
                    <label class="control-label col-xs-8" style="font-weight:bold;text-align:left;">{{osName}}</label>
                </div>
            </div>
        </div>
        <div class="col-xs-6">
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">操作系统厂家：</label>
                    <label class="control-label col-xs-8" style="font-weight:bold;text-align:left;">{{osFac}}</label>
                </div>
            </div>
        </div>
        <div class="col-xs-6">
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">操作系统版本：</label>
                    <label class="control-label col-xs-8" style="font-weight:bold;text-align:left;">{{osVer}}</label>
                </div>
            </div>
        </div>
        <div class="col-xs-6">
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">CPU厂家：</label>
                    <label class="control-label col-xs-8" style="font-weight:bold;text-align:left;">{{cpuFac}}</label>
                </div>
            </div>
        </div>
        <div class="col-xs-6">
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">CPU核数：</label>
                    <label class="control-label col-xs-8" style="font-weight:bold;text-align:left;">{{cpuCores}}</label>
                </div>
            </div>
        </div>
        <div class="col-xs-6">
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">单核频率：</label>
                    <label class="control-label col-xs-8" style="font-weight:bold;text-align:left;">{{coreMhz}}</label>
                </div>
            </div>
        </div>
        <div class="col-xs-6">
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">内存类型：</label>
                    <label class="control-label col-xs-8" style="font-weight:bold;text-align:left;">{{memType}}</label>
                </div>
            </div>
        </div>
        <div class="col-xs-6">
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">内存大小(b)：</label>
                    <label class="control-label col-xs-8" style="font-weight:bold;text-align:left;">{{memSizeGb}} GB</label>
                </div>
            </div>
        </div>
        <div class="col-xs-6">
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">磁盘类型：</label>
                    <label class="control-label col-xs-8" style="font-weight:bold;text-align:left;">{{diskType}}</label>
                </div>
            </div>
        </div>
        <div class="col-xs-6">
            <div class="form-group">
                <div class="row">
                    <label class="control-label col-xs-3">硬盘大小(KB)：</label>
                    <label class="control-label col-xs-8" style="font-weight:bold;text-align:left;">{{diskSizeGb}} Gb</label>
                </div>
            </div>
        </div>
    </form>
    
    <div class="col-md-6">
        <div class="form-group">
            <div class="chartTitle">CPU</div>
            <div class="chartContent">
                <div id="cpuChart"></div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="chartTitle">Mem</div>
            <div class="chartContent">
                <div id="memChart"></div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="chartTitle">Disk</div>
            <div class="chartContent">
                <div id="diskChart"></div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            
        </div>
    </div>
    <div class="row">
        
        
        
        
    </div>
    

</template>

<template id="terminateInfoPanel">
    <div class="parentPanel terminatePanel" style="padding:10px 10px;margin-bottom: 10px;">
        <!-- <div class="title" style="border-bottom: 2px solid dimgray;"> -->
        <div class="titleTip">
            <span>
            全部服务器信息
            </span>
            <!-- <span class="titleName" style="font-size: 25px;font-weight: bold;">全部服务器信息</span> -->
        </div>
        <div class="toolbar terminalListPanel" style="margin-bottom:10px;overflow: hidden;">
            <!-- <button type="button" class="btn terminalBtn">
                服务器1
            </button> -->
        </div>
        <div id="grid" ></div>    
    </div>
</template>

<template id="publishGridTemplate">
<form style="padding:10px 10px;overflow:hidden;" class="form-horizontal">
    <div class="form-group">
        <div class="row">
            <label class="control-label col-xs-3">服务器接受信息url：</label>
            <div class="col-xs-8">
                <input type="text" name="serverUrl" class="form-control" maxlength="100" required value="{{serverUrl}}">
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="row" style="text-align:center">
            <button type="button" id="sureBtn" class="btn blue btn-sm sureBtn" >
            <i class="glyphicon glyphicon-ok"></i>确认发布
            </button>
        </div>
    </div>
</form>
</template>

<template id="editZtreeTemplate">
    <form style="padding:10px 10px;overflow:hidden;" class="form-horizontal" enctype="multipart/form-data">
        {{if parentIndexId}}
        <input type="hidden" name="parentIndexId" value="{{parentIndexId}}">
        {{/if}}
        {{if parentTreeId}}
        <input type="hidden" name="parentTreeId" value="{{parentTreeId}}">
        {{/if}}
        {{if id}}
        <input type="hidden" name="id" value="{{id}}">
        {{/if}}
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">名称：</label>
                <div class="col-xs-8">
                    <input type="text" name="name" class="form-control" maxlength="50" required value="{{name}}">
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">代码：</label>
                <div class="col-xs-8">
                    <input type="text" name="code" class="form-control" maxlength="50" required value="{{code}}">
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

<template id="gridInfoTemplate">
    <div class="portlet box blue strruleportlet" style="border:1px solid #ccc; margin: 0px 3px; height:100%;">
        <div class="portlet-title" style="min-height: 0px;">
           
            <ul class="nav nav-tabs" style="float:left">
                <li class="active">
                    <a href="#baseInfoPanel" data-toggle="tab"> 基础信息 </a>
                </li>
                <li>
                    <a href="#otherInfoPanel" data-toggle="tab"> 详细信息 </a>
                </li>
            </ul>
        </div>
        <div class="portlet-body" style="">
            <div class="tab-content" style="height:100%;">
                <div class="tab-pane baseInfoPanel active" id="baseInfoPanel" style="overflow:hidden; height:100%;">
                    <form style="padding:10px 10px;overflow:hidden;" class="form-horizontal">
                        <div class="form-group">
                            <div class="row">
                                <label class="control-label col-xs-3">操作系统名称：</label>
                                <div class="col-xs-8">
                                    <input type="text" class="form-control" disabled="true" readonly="true" value="{{osName}}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <label class="control-label col-xs-3">操作系统厂家：</label>
                                <div class="col-xs-8">
                                    <input type="text" class="form-control" disabled="true" readonly="true" value="{{osFac}}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <label class="control-label col-xs-3">操作系统版本：</label>
                                <div class="col-xs-8">
                                    <input type="text" class="form-control" disabled="true" readonly="true" value="{{osVer}}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <label class="control-label col-xs-3">CPU厂家：</label>
                                <div class="col-xs-8">
                                    <input type="text" class="form-control" disabled="true" readonly="true" value="{{cpuFac}}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <label class="control-label col-xs-3">CPU核数：</label>
                                <div class="col-xs-8">
                                    <input type="text" class="form-control" disabled="true" readonly="true" value="{{cpuCores}}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <label class="control-label col-xs-3">单核频率：</label>
                                <div class="col-xs-8">
                                    <input type="text" class="form-control" disabled="true" readonly="true" value="{{coreMhz}}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <label class="control-label col-xs-3">内存类型：</label>
                                <div class="col-xs-8">
                                    <input type="text" class="form-control" disabled="true" readonly="true" value="{{memType}}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <label class="control-label col-xs-3">内存大小(b)：</label>
                                <div class="col-xs-8">
                                    <input type="text" class="form-control" disabled="true" readonly="true" value="{{memSizeGb}} Gb" style="display:inline-block;width:100%;">
                                   <!--  <input type="text" class="form-control" disabled="true" readonly="true" value="{{memSizeMb}} Mb" style="display:inline-block;width:30%;">
                                    <input type="text" class="form-control" disabled="true" readonly="true" value="{{memSizeKb}} Kb" style="display:inline-block;width:30%;"> -->
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <label class="control-label col-xs-3">磁盘类型：</label>
                                <div class="col-xs-8">
                                    <input type="text" class="form-control" disabled="true" readonly="true" value="{{diskType}}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <label class="control-label col-xs-3">硬盘大小(KB)：</label>
                                <div class="col-xs-8">
                                    <input type="text" class="form-control" disabled="true" readonly="true" value="{{diskSizeGb}} Gb" style="display:inline-block;width:100%;">
                                    <!-- <input type="text" class="form-control" disabled="true" readonly="true" value="{{diskSizeMb}} Mb" style="display:inline-block;width:30%;">
                                    <input type="text" class="form-control" disabled="true" readonly="true" value="{{diskSize}} Kb" style="display:inline-block;width:30%;"> -->
                                </div>
                            </div>
                        </div>
                       
                    </form>
                </div>

                <div class="tab-pane otherInfoPanel" id="otherInfoPanel" style=" height:100%;">
                    <div id="gauge" style="height:300px;width:100%;"></div>
                    <!-- <div class="row">
                        <div class="col-xs-6"><div id="cpuGauge"></div></div>
                        <div class="col-xs-6"><div id="memGauge"></div></div>
                        <div class="col-xs-6"><div id="diskGauge"></div></div>
                    </div> -->
                </div>
            </div>
        </div>
    </div>
    
</template>

<template id="editGridTemplate">
    <form style="padding:10px 10px;overflow:hidden;" class="form-horizontal">
        {{if id}}
        <input type="hidden" name="id" value="{{id}}">
        {{/if}}
        {{if categoryId}}
        <input type="hidden" name="categoryId" value="{{categoryId}}">
        {{/if}}
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">名称：</label>
                <div class="col-xs-8">
                    <input type="text" name="name" class="form-control" maxlength="50" required value="{{name}}">
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">类型：</label>
                <div class="col-xs-8">
                    <select name="type" class="form-control" >
                        {{each terminalTypeDatas as data i}}
                            {{if type == data.value}}
                            <option value="{{data.value}}" selected>{{data.text}}</option>
                            {{else}}
                            <option value="{{data.value}}">{{data.text}}</option>
                            {{/if}}
                        {{/each}}
                    </select>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">级别：</label>
                <div class="col-xs-8">
                    <select name="level" class="form-control" >
                        {{each levelDatas as data i}}
                            {{if level == data.value}}
                            <option value="{{data.value}}" selected>{{data.text}}</option>
                            {{else}}
                            <option value="{{data.value}}">{{data.text}}</option>
                            {{/if}}
                        {{/each}}
                    </select>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">产品品牌：</label>
                <div class="col-xs-8">
                    <input type="text" name="prod" class="form-control" maxlength="50" value="{{prod}}">
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">域名：</label>
                <div class="col-xs-8">
                    <input type="text" name="domain" class="form-control" maxlength="50" value="{{domain}}">
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">地址：</label>
                <div class="col-xs-8">
                    <input type="text" name="address" class="form-control" maxlength="50" value="{{address}}">
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">监控服务地址：</label>
                <div class="col-xs-8">
                    <input type="text" name="monitorServiceAddress" class="form-control" maxlength="150" required value="{{monitorServiceAddress}}">
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">描述：</label>
                <div class="col-xs-8">
                    <textarea rows="8" style="width:100%" name="desc">{{desc}}</textarea>
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

<template id="changeGridTypeTemplate">
    <div class="parentPanel" style="border: 1px solid gray;">
        <div class="toolbar" style="background-color: gainsboro;border-bottom: 1px solid darkgrey;">
            <button type="button" id="saveBtn" class="btn green-haze btn-sm saveBtn" style="padding-top: 1px;padding-bottom: 1px;">
                <i class="fa fa-save"></i>保存
            </button>
        </div>
        <div class="dynamicPanelHeight" style="background-color: whitesmoke;">
            <div class="ztree slimScrollDivPanel" id="selectCategoryTree" style="height:100%">
                
            </div>
        </div>    
    </div>
</template>

<template id="setPeopleTemplate">
    <div class="parentPanel" style="border: 1px solid gray;">
        <div class="toolbar" style="background-color: gainsboro;border-bottom: 1px solid darkgrey;">
            <button type="button" class="addBtn btn green-haze btn-sm" style="padding-top: 1px;padding-bottom: 1px;">
                <i class="fa fa-plus-square"></i>新增
            </button>
            <button type="button" class="editBtn btn blue btn-sm" style="padding-top: 1px;padding-bottom: 1px;">
                <i class="fa fa-save"></i>修改
            </button>
            <button type="button" class="deleteBtn btn-danger btn-sm" style="padding-top: 1px;padding-bottom: 1px;">
                <i class="fa fa-save"></i>删除
            </button>
        </div>
        <div class="dynamicPanelHeight" style="background-color: whitesmoke;">
            <div id="peopleGrid" style="height:100%;background-color:white;">
                
            </div>
        </div>    
    </div>
</template>

<template id="editPeopleTemplate">
    <form style="padding:10px 10px;overflow:hidden;" class="form-horizontal" enctype="multipart/form-data">
        {{if terminalId}}
        <input type="hidden" name="terminalId" value="{{terminalId}}">
        {{/if}}
        {{if id}}
        <input type="hidden" name="id" value="{{id}}">
        {{/if}}
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">姓名：</label>
                <div class="col-xs-8">
                    <input type="text" name="username" class="form-control" maxlength="50" required value="{{username}}">
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">角色：</label>
                <div class="col-xs-8">
                    <select name="role" class="form-control" >
                        {{each roleDatas as data i}}
                            {{if role == data.value}}
                            <option value="{{data.value}}" selected>{{data.text}}</option>
                            {{else}}
                            <option value="{{data.value}}">{{data.text}}</option>
                            {{/if}}
                        {{/each}}
                    </select>
                    <!-- <input type="text" name="code" class="form-control" maxlength="50" required value="{{code}}"> -->
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">工作电话：</label>
                <div class="col-xs-8">
                    <input type="text" name="tel" class="form-control" maxlength="50" required value="{{tel}}">
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">手机号码：</label>
                <div class="col-xs-8">
                    <input type="text" name="phone" class="form-control" maxlength="50" required value="{{phone}}">
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">邮箱地址：</label>
                <div class="col-xs-8">
                    <input type="text" name="email" class="form-control" maxlength="50" required value="{{email}}">
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row" style="text-align:center">
                <button type="button" id="saveBtn" class="btn blue btn-sm saveBtn" >
                <i class="glyphicon glyphicon-ok"></i>保存
                </button>
            </div>
        </div>
    </form>
</template>

<template id="setWarningItemTemplate">
    <div class="parentPanel" style="border: 1px solid gray;">
        <div class="toolbar" style="background-color: gainsboro;border-bottom: 1px solid darkgrey;">
            <button type="button" class="addBtn btn green-haze btn-sm" style="padding-top: 1px;padding-bottom: 1px;">
                <i class="fa fa-plus-square"></i>新增
            </button>
            <button type="button" class="editBtn btn blue btn-sm" style="padding-top: 1px;padding-bottom: 1px;">
                <i class="fa fa-save"></i>修改
            </button>
            <button type="button" class="deleteBtn btn-danger btn-sm" style="padding-top: 1px;padding-bottom: 1px;">
                <i class="fa fa-save"></i>删除
            </button>
        </div>
        <div class="dynamicPanelHeight" style="background-color: whitesmoke;">
            <div id="warningItemGrid" style="height:100%;background-color:white;">
                
            </div>
        </div>    
    </div>
</template>

<template id="editWarningItemTemplate">
    <form style="padding:10px 10px;overflow:hidden;" class="form-horizontal" enctype="multipart/form-data">
        {{if terminalId}}
        <input type="hidden" name="terminalId" value="{{terminalId}}">
        {{/if}}
        {{if id}}
        <input type="hidden" name="id" value="{{id}}">
        {{/if}}
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">名称：</label>
                <div class="col-xs-8">
                    <input type="text" name="name" class="form-control" maxlength="50" required value="{{name}}">
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">代码：</label>
                <div class="col-xs-8">
                    <select name="code" class="form-control" >
                        {{each warningCodeDatas as data i}}
                            {{if code == data.value}}
                            <option value="{{data.value}}" selected>{{data.text}}</option>
                            {{else}}
                            <option value="{{data.value}}">{{data.text}}</option>
                            {{/if}}
                        {{/each}}
                    </select>
                    <!-- <input type="text" name="code" class="form-control" maxlength="50" required value="{{code}}"> -->
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">单位：</label>
                <div class="col-xs-8">
                    <select name="unit" class="form-control" >
                        {{each warningUnitDatas as data i}}
                            {{if unit == data.value}}
                            <option value="{{data.value}}" selected>{{data.text}}</option>
                            {{else}}
                            <option value="{{data.value}}">{{data.text}}</option>
                            {{/if}}
                        {{/each}}
                    </select>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">预警值：</label>
                <div class="col-xs-8">
                    <input type="number" name="alarmVal" class="form-control" maxlength="50" required value="{{alarmVal}}">
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">参照类型：</label>
                <div class="col-xs-8">
                    <select name="refType" class="form-control" data-allow-clear="false">
                        {{each warningRefTypeDatas as data i}}
                            {{if refType == data.value}}
                            <option value="{{data.value}}" selected>{{data.text}}</option>
                            {{else}}
                            <option value="{{data.value}}">{{data.text}}</option>
                            {{/if}}
                        {{/each}}
                    </select>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">提醒方式：</label>
                <div class="col-xs-8">
                    <select name="warningType" class="form-control" multiple="multiple" data-allow-clear="false">
                        {{each warningWarningTypeDatas as data i}}
                            <option value="{{data.value}}">{{data.text}}</option>
                        {{/each}}
                    </select>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">监控服务地址：</label>
                <div class="col-xs-8">
                    <input type="text" name="monitorServiceAddress" class="form-control" maxlength="50" value="{{monitorServiceAddress}}">
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row" style="text-align:center">
                <button type="button" id="saveBtn" class="btn blue btn-sm saveBtn" >
                <i class="glyphicon glyphicon-ok"></i>保存
                </button>
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
<script src="${contextPath}/scripts/plugins/bootstrap/dialog/js/bootstrap-dialog.min.js"></script>
<script src="${contextPath}/scripts/plugins/bootstrap/dialog/js/jquery.dialog.extends.js"></script>
<!--grid-->
<script type="text/javascript" src="${contextPath}/scripts/plugins/bootstrap/pagination/js/jquery.pagination.extends.js"></script>
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

<!-- 一些整合好的方法，如查询表格字段信息 -->
<script type="text/javascript" src="${contextPath}/webfile/js/builder.js"></script>
<script type="text/javascript" src="${contextPath}/webfile/js/builder.jsplumb.js"></script>

<!-- 表单验证 -->
<script src="${contextPath}/scripts/jquery.validate.min.js" type="text/javascript"></script>
<!-- altTemplate -->
<script src="${contextPath}/scripts/artTemplate/dist/template.js"></script>
<script src="${contextPath}/scripts/artTemplate/dataUtils.js"></script>
<!-- highchart -->
<script type='text/javascript' src='${contextPath}/scripts/highstock/highstock.js'></script>
<script type='text/javascript' src='${contextPath}/scripts/highcharts/highcharts-3d.js'></script>
<script type='text/javascript' src='${contextPath}/scripts/highcharts/highcharts-more.js'></script>
<script type='text/javascript' src='${contextPath}/scripts/highcharts/modules/funnel.js'></script>
<script type='text/javascript' src='${contextPath}/scripts/highcharts/modules/heatmap.js'></script>
<script type='text/javascript' src='${contextPath}/scripts/highcharts/modules/treemap.js'></script>
<script type='text/javascript' src='${contextPath}/scripts/highcharts/modules/exporting.js'></script>
<script type='text/javascript' src='${contextPath}/scripts/highcharts/themes/default.js'></script>
<!-- echarts -->
<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/echartsExt/js/echarts.js'></script>
<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/echartsExt/ext/jquery.echartsmap.extends.js'></script>
<style type="text/css">
    .select2-dropdown.select2-dropdown--below{
        z-index: 99999;
    }
    .grid_operator_div{
        display: inline-block;
        margin-left: 6px;
    }
</style>

<script src="${contextPath}/scripts/monitor/server/monitorServerManagement_new.js"></script>

<script type ="text/javascript">

</script>