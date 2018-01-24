<template id="layout_attr_item_template">
</template>
<template id="left_menu">
<ul class="dropdown-menu myMenu" role="menu" style="font-size:12px;">
    <li class="dropdown-submenu add_layout_menu">
        新建布局
    </li>
    <li class="dropdown-submenu edit_menu">
        修改
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
</ul>
</template>
<template id="edit_layout_template">
<form style="padding:10px 10px;overflow:hidden;" class="form-horizontal" enctype="multipart/form-data">
    {{if themeTmpId}}
    <input type="hidden" name="themeTmpId" value="{{themeTmpId}}">
    {{/if}}
    <div class="col-xs-4">
        <div class="theme_thumbnail_panel">
            <a class="theme_thumbnail ">
                <input class="theme_thumbnail theme_thumbnail_upload" type="file" name="file" id="thumbnail_upload">
                <span class="btn blue">设置缩略图</span>
            </a>
            <img class="theme_thumbnail" id="theme_thumbnail_show" src="${contextPath}/scripts/designer/images/none.jpg">
        </div>
        <div class="theme_thumbnail_title">缩略图</div>
    </div>
    <div class="col-xs-8">
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">名称：</label>
                <div class="col-xs-8">
                    <input type="text" name="layoutName" class="form-control" maxlength="50" required>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">代码：</label>
                <div class="col-xs-8">
                    <input type="text" name="layoutCode" class="form-control" maxlength="50" required>
                </div>
            </div>
        </div>
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
    <div class="col-xs-4">
        <div class="theme_thumbnail_panel">
            <a class="theme_thumbnail ">
                <input class="theme_thumbnail theme_thumbnail_upload" type="file" name="file" id="thumbnail_upload">
                <span class="btn blue">设置缩略图</span>
            </a>
            <img class="theme_thumbnail" id="theme_thumbnail_show" src="${contextPath}/scripts/designer/images/none.jpg">
        </div>
        <div class="theme_thumbnail_title">缩略图</div>
    </div>
    <div class="col-xs-8">
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">名称：</label>
                <div class="col-xs-8">
                    <input type="text" name="controlName" class="form-control" maxlength="50" required>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">代码：</label>
                <div class="col-xs-8">
                    <input type="text" name="controlCode" class="form-control" maxlength="50" required>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">类型：</label>
                <div class="col-xs-8">
                    <select name="compType" required >
                        {{each componentTypeData as data i}}
                        {{if data.dataRole==compType}}
                        <option value="{{data.dataRole}}" selected>{{data.name}}</option>
                        {{else}}
                        <option value="{{data.dataRole}}">{{data.name}}</option>
                        {{/if}}
                        {{/each}}
                    </select>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">符合模板：</label>
                <div class="col-xs-8">
                    <label>是:<input type="radio" name="compositionFlag" class="icheck" value="12" checked="checked" style="margin-top: 10px"></label>
                    <label>否:<input type="radio" name="compositionFlag" class="icheck" value="24" style="margin-top: 10px" checked="checked"></label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">容器标识：</label>
                <div class="col-xs-8">
                    <label>是:<input type="radio" name="containerFlag" class="icheck" value="12" checked="checked" style="margin-top: 10px"></label>
                    <label>否:<input type="radio" name="containerFlag" class="icheck" value="24" style="margin-top: 10px" checked="checked"></label>
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

<template id="edit_layout_area_control_template">
<form style="padding:10px 10px;overflow:hidden;" class="form-horizontal" enctype="multipart/form-data">
    {{if themeTmpId}}
    <input type="hidden" name="themeTmpId" value="{{themeTmpId}}">
    {{/if}}
    <!--区域ID-->
    <input type="hidden" name="areaId" value="{{areaId}}">
    <!--布局ID-->
    <input type="hidden" name="layoutId" value="{{layoutId}}">
    <div class="col-xs-4">
        <div class="theme_thumbnail_panel">
            <a class="theme_thumbnail ">
                <input class="theme_thumbnail theme_thumbnail_upload" type="file" name="file" id="thumbnail_upload">
                <span class="btn blue">设置缩略图</span>
            </a>
            <img class="theme_thumbnail" id="theme_thumbnail_show" src="${contextPath}/scripts/designer/images/none.jpg">
        </div>
        <div class="theme_thumbnail_title">缩略图</div>
    </div>
    <div class="col-xs-8">
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">名称：</label>
                <div class="col-xs-8">
                    <input type="text" name="controlName" class="form-control" maxlength="50" required>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">代码：</label>
                <div class="col-xs-8">
                    <input type="text" name="controlCode" class="form-control" maxlength="50" required>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">类型：</label>
                <div class="col-xs-8">
                    <select name="compType" required >
                        {{each componentTypeData as data i}}
                        {{if data.dataRole==compType}}
                        <option value="{{data.dataRole}}" selected>{{data.name}}</option>
                        {{else}}
                        <option value="{{data.dataRole}}">{{data.name}}</option>
                        {{/if}}
                        {{/each}}
                    </select>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">符合模板：</label>
                <div class="col-xs-8">
                    <label>是:<input type="radio" name="compositionFlag" class="icheck" value="12" checked="checked" style="margin-top: 10px"></label>
                    <label>否:<input type="radio" name="compositionFlag" class="icheck" value="24" style="margin-top: 10px" checked="checked"></label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="row">
                <label class="control-label col-xs-3">容器标识：</label>
                <div class="col-xs-8">
                    <label>是:<input type="radio" name="containerFlag" class="icheck" value="12" checked="checked" style="margin-top: 10px"></label>
                    <label>否:<input type="radio" name="containerFlag" class="icheck" value="24" style="margin-top: 10px" checked="checked"></label>
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
        <div class="setting_left_panel style_setting_panel" style="display: inline-table;">
        </div>
        <div class="setting_right_panel display_panel" style="display: inline-table;padding:15px 15px;">
        </div>
    </div>
</div>
</template>
<template id="attrItem_template">
<div class="attrItem" style="padding:4px 5px;">
    <fieldset class="myFieldset">
        <legend>XXX区域/布局</legend>
        <div class="form-inline attrContent"></div>
    </fieldset>
</div>
</template>