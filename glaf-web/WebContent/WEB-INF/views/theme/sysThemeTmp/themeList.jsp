<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>主题信息</title>
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

<style type="text/css">
    .theme_list{
        overflow: hidden;
    }
    .portlet .portlet-body{
        background-color: #fafafa;
    }
    
    .theme_list .theme_item{
        /*display: inline-block;*/
        float: left;
        width:200px;
        height:200px;
        border:1px solid #ddd;
        overflow: hidden;
        position: relative;
        padding:3px 3px;
    }
    .theme_thumbnail{
        height: 160px;
        width: 193px;
    }
    .theme_thumbnail_panel{
        height: 162px;
        width: 195px;
        border:1px solid black;
    }
    .theme_thumbnail_title{
        text-align: center;
        width: 195px;
    }
    .theme_thumbnail_panel span{
        margin-top: 54px;
    }
    .theme_thumbnail_panel a{
        position: absolute;
        display: none;
        text-align: center;
    }
    .theme_thumbnail_panel a:hover{
        background-color: rgba(0, 0, 0, 0.3);
    }
    .theme_thumbnail_upload{
        opacity: 0;
        position:absolute;
        cursor:pointer;
    }
    .theme_list .theme_item .item_name_div{
        text-align: center;
        width: 100%;
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
    }
    .theme_list .theme_add{
        text-align: center;
    }
    .theme_list .theme_add .addBtn{
        width: 145px;
        height: 145px;
        
    }
    .theme_list .theme_add .addBtn i{
        font-size: 100px;
        line-height: 145px;
    }
    .theme_list .theme_item .mask{
        height: 194px;
        width: 194px;
        position: absolute;
        top: 3px;
        left: 3px;
        background-color: rgba(0,0,0,0.5);
        display: none;
    }
    .theme_list .theme_item .mask .operatorGroup{
        text-align: center;
        margin-top:82px;
    }

    .theme_list .theme_item.used{
        border-color: red;
        background-color: rgba(242, 121, 121, 0.5);
    }
    
    .theme_item.default {
        border-color: blue;
    }
        
</style>
</head>
<body>
    <template id="theme_item_template">
        <div class="theme_item">
            <span class="mask">
                <div class="operatorGroup">
                    <button class="btn edit_theme_btn">修改</button>
                    <!-- <button class="btn update_theme_btn">修改</button> -->
                    <br >
                    <button class="btn setDefaultBtn" style="margin-top:10px;">设置为默认主题</button>
                </div>
            </span>
            <img class="theme_thumbnail" src="${contextPath}/rs/designer/2024/getCategoryTemplateImage">
            <div class="item_name_div">
                <span class="item_name">主题一</span>
                <span class="defaultFlag" style="color:red"></span>
            </div> 
        </div>
    </template>
    <template id="theme_item_add_template">
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

    <template id="theme_item_add_panel_template">
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
                    {{if themeTmpId}}
                        <img class="theme_thumbnail" id="theme_thumbnail_show" src="${contextPath}/mx/theme/sysThemeTmp/downloadThumbnail?themeTmpId={{themeTmpId}}&_{{nowdate}}" onerror="javascript:this.src='${contextPath}/scripts/designer/images/none.jpg'">
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
                        <button type="button" id="saveTheme" class="btn blue btn-sm " >
                    <i class="glyphicon glyphicon-ok"></i>保存
                </button>
                </div>
            </div>
        </form>
    </template>

    <div class="portlet box blue strruleportlet" style="border:1px solid #ccc;margin:3px 3px;">
            <div class="portlet-title" style="min-height: 0px;">
                <div class="" style="line-height: 30px;">
                    主题信息
                </div>
            </div>
            <div class="portlet-body">
                <div class="theme_panel">
                    <div class="theme_list">
                        <!-- <div class="theme_item">主题一</div>
                        <div class="theme_item used">
                            <span class="mask">
                                <div class="operatorGroup">
                                    <button class="btn">套用</button>
                                    <button class="btn">编辑</button>
                                </div>
                            </span>
                            <img class="theme_thumbnail" src="${contextPath}/rs/designer/2024/getCategoryTemplateImage">
                            <div class="item_name_div">
                                <span class="item_name">主题一</span>
                            </div>
                        </div>
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
                        
                        <div class="theme_item">+</div> -->
                        <!-- <div class="theme_item theme_add">
                            <a class="btn addBtn">
                                <i class="fa fa-plus-circle"></i>
                            </a>
                        </div> -->
                    </div>
                </div>
            </div>
    </div>
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

<script type ="text/javascript">
    var contextPath = '${contextPath}';

    var validate_setting = {
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
    };

    var uiType = {
        read: {
            url: contextPath + "/mx/form/defined/getDictByCode",
            async: false,
            data: {
                code: 'ui_type'
            },
            type: 'post',
            dataType: 'json',
        }
    }

    var _page_param = {
        theme : {
            saveThemeUrl : contextPath + "/mx/theme/sysThemeTmp/saveSysThemeTmp",
            readUrl : contextPath + "/mx/theme/sysThemeTmp/read",
            imgUrl : contextPath + "/mx/theme/sysThemeTmp/downloadThumbnail",
            editThemeUrl : contextPath + "/mx/theme/sysThemeTmp/view/themeEdit",
        }
    }


    var options = {
        read : {
            url: _page_param.theme.readUrl,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify({}),
            contentType: "application/json",
            async: false
        },
        pagination: {
            littlePage : true,  //分页是否为最小
            buttonCount:3,  //按钮数量
            paging: true,   //是否显示分页,显示分页取消滑动效果
            page: 1,    //当前页数
            pageSize: 10,   //每页个数
            // pageSizes: [5, 10, 15, 20, 25, 50, 100, 200, 500], //分页条数选择
            serverPaging: false,    //服务器分页
            previousNext: "true", //是否使能分页翻页按钮
            numeric: "true", //是否使能数字页码按钮
            input: "false", //是否使能输入框翻页
            first: '首页', //首页按钮提示文本
            last: '末页', //末页按钮提示文本
            previous: '上一页', //上一页显示文本
            next: '下一页', //下一页显示文本
            refresh: 'false',
            showPageList:false,
            pagePanelState: 'onehide',  //分页栏占位隐藏
        },
    }

    var _themePanelFunc_ = {
        uiTypeData : null,  //ui类型数据，如bootstrap和kendo
        getUiTypeData : function(){
            var that = this;
            uiType.read.success = function(data){
                that.uiTypeData = data;
            }
            $.ajax(uiType.read);
        },
        _init_ : function(){
            var that = this;
            that.target = $(".theme_panel")[0];
            that.$theme_list_panel = $(".theme_list");
            that.getUiTypeData();
            that.options = options;
            that._load();
            that._bindEvent_();
        },
        //加载主题信息
        _load : function(){
            var that = this;
            that.query();
        },
        query : function(){
            var that = this;
            var options = that.options;
            that.options.read.data = JSON.stringify({
                page: options.pagination.page,
                pageSize: options.pagination.pageSize
            })
            options.read.success = function(data) {
                that._ajaxSuccess(data);
            }
            $.ajax(options.read);
        },
        _createItem : function(item){
            var that = this;
            var $theme_item = $($("#theme_item_template").html());
            var url = _page_param.theme.imgUrl + "?themeTmpId="+item.themeTmpId + "&_"+(new Date().getTime());
            $theme_item.find('.theme_thumbnail').attr("src",url);
            $theme_item.find('.item_name').text(item.themeTmpName);
            if(item.defaultFlag && item.defaultFlag == 1){
                $theme_item.addClass("default");
                $theme_item.find('.defaultFlag').text("(默认)");
            }
            $theme_item.data("data",item);
            that.$theme_list_panel.append($theme_item);
        },
        _createAddItem : function(){
            var that = this;
            that.$theme_list_panel.append($("#theme_item_add_template").html());
        },
        _loadData : function(datas){
            var that = this;
            that.$theme_list_panel.empty();
            $.each(datas,function(i,item){
                that._createItem(item);
            })
            that._createAddItem();
        },
        _ajaxSuccess : function(data){
            var that = this;
            that.total = data.total;
            that.pageMax = Math.ceil(data.total / data.pageSize);
            that._loadData(data.rows);
            that.renderPagination();
        },
        renderPagination : function(){
            var that = this;
            var $target = $(this.target);
            if (that.pageMax > 0) {
                var $id = $target.attr("paging");
                !$id && ($target.attr("paging", ($id = 'pageId-' + new Date().getTime())));
                var $page = $("#" + $id),
                    pageFn = $page.data("pagination");
                if (!$page[0]) {

                    var page = that.options.pagination;
                    var $page = $("<div>", {
                        id: $id,
                        class: 'page',
                        'style': 'text-align:right;'
                    })
                    $page.pagination($.extend(true, {}, page, {
                        total: that.total,
                        totalPage: that.pageMax,
                        showPageList:false,
                        events: {
                            onSelectPage: function(pageNumber, pageSize) {
                                that.options.pagination.page = pageNumber;
                                that.query({});
                            },
                        }
                    }));

                    $(that.target).append($page);
                }else{
                    pageFn.refresh({
                        total: that.total,
                        totalPage: that.pageMax,
                        pageNumber: that.options.pagination.page
                    });
                }
            }
        },
        _reload_ : function(){
            //重载
        },
        //设置主页的事件
        _bindEvent_ : function(){
            var that = this;
            var $target = $(that.target);

            $target.on("mouseover mouseout",".theme_item",function(event){
                var $this = $(this);
                if(event.type == "mouseover"){
                    //鼠标悬浮
                    if($this.hasClass("default")){
                        $this.find(".setDefaultBtn").text("取消默认主题");
                    }else{
                        $this.find(".setDefaultBtn").text("设置为默认主题");
                    }
                    $this.find(".mask").show();
                }else if(event.type == "mouseout"){
                    //鼠标离开
                    $this.find(".mask").hide();
                }
            })

            // $target.find(".theme_item").hover(function(e){
            //     $(this).find(".mask").show();
            // },function(e){
            //     $(this).find(".mask").hide();
            // })

            $target.on("click",".add_theme_btn",function(event){
                that._showEditThemeTmp();
            })

            $target.on("click",".update_theme_btn",function(event){
                //修改主题
                var itemData = $(this).closest(".theme_item").data("data"); 
                itemData.nowdate = new Date().getTime();
                that._showEditThemeTmp(itemData);
            })
            $target.on("click",".edit_theme_btn",function(event){
                //编辑主题
                var itemData = $(this).closest(".theme_item").data("data"); 
                var themeId = itemData.id;
                window.open(_page_param.theme.editThemeUrl + "?themeId="+themeId);
            })

            $target.on("click",".setDefaultBtn",function(event){
                //设置为默认主题
                var itemData = $(this).closest(".theme_item").data("data"); 
                var themeId = itemData.id;

                $.ajax({
                    url: contextPath + "/mx/theme/sysThemeTmp/setDefaultTheme",
                    async: true,
                    type: 'post',
                    data : {'themeTmpId':itemData.themeTmpId,"defaultFlag":itemData.defaultFlag?0:1},
                    // contentType : 'appliaction/json',
                    // dataType: 'json',
                    success: function(ret) {
                        var datas = "";
                        if(typeof ret == 'string'){
                            ret = JSON.parse(ret);
                        }
                        datas = ret.data;
                        that.query({});
                    }
                });
            })
        },
        //打开新增窗口
        _showEditThemeTmp:function(itemData){
            var that = this;
            var itemData = itemData;
            //新增主题
            var dialog = BootstrapDialog.show({
                title: "主题新增",
                draggable: false,
               
                css: {
                    width: '800px',
                    height: '500px'
                },
                onshown:function(dialog){
                    var $modalBody = dialog.getModalBody()
                    itemData = itemData || {};
                    itemData.uiTypeData = that.uiTypeData;
                    var templateHtml = ArtTemplateDataUtils.convertOne(itemData, $("#theme_item_add_panel_template").html());
                    $modalBody.append(templateHtml);
                    
                    that._bindEditThemeEvent_( $modalBody);
                }
            });

            dialog.getModalHeader().css({
                padding: '4px 4px',
            });
            that.dialog = dialog;
        },
        //
        _bindEditThemeEvent_:function( $modalBody){
            var that = this;
            validate_setting.rules = {
                themeTmpName : {
                    "required" : true,
                    "maxlength" : 50
                },
                themeTmpCode : {
                    "required" : true,
                    "maxlength" : 50
                },
                ui : {
                    "required" : true,
                }
            };
            validate_setting.messages = {
                themeTmpName : {
                    "required" : '必填',
                    "maxlength" : '不可大于50个字'
                },
                themeTmpCode : {
                    "required" : '必填',
                    "maxlength" : '不可大于50个字'
                },
                ui : {
                    "required" : '必填',
                    "maxlength" : '不可大于50个字'
                },
            }
            var saveThemeValidator = $modalBody.find("form").validate(validate_setting);
            $modalBody.find(".theme_thumbnail_panel").hover(function(e){
                $(this).find("a").show();
            },function(e){
                $(this).find("a").hide();
            })

            //缩略图修改时，实现图片预览
            $modalBody.find("#thumbnail_upload").change(function(e){
                var file = this.files[0];
                //实现文件预览功能
                var reader = new FileReader();
                var $theme_thumbnail_show = $modalBody.find("#theme_thumbnail_show");
                reader.onloadend = function () {
                    $theme_thumbnail_show.attr("src",reader.result).show();
                }
                if (file) {
                    reader.readAsDataURL(file);
                }
            })

            $modalBody.find("#saveTheme").click(function(e){
                //按钮
                var $this = $(this);
                //保存主题信息
                if(!saveThemeValidator.form()){
                    return;
                }

                if($modalBody.find("#thumbnail_upload")[0].files[0] && $modalBody.find("#thumbnail_upload")[0].files[0].size > 4194304){
                    alert("缩略图过大，不能超过4M!");
                    return;
                }

                var url = _page_param.theme.saveThemeUrl;                    
                // 接收上传文件的后台地址 
                var xhr = new XMLHttpRequest();
                xhr.open("post", url, true);
                xhr.onload = function (oEvent) {
                    debugger;
                    if (xhr.status == 200) {
                        alert("新建主题成功");
                        that.query({});
                        that.dialog.close();
                    }else{
                        alert("异常错误");
                    }
                    // $this.attr("disabled");
                    $this.removeAttr("disabled");;
                };
                
                var formdata = new FormData($modalBody.find("form")[0]);
                $this.attr("disabled",true);    //按钮禁用
                xhr.send(formdata); //发送请求
            })
        }
    }

    _themePanelFunc_._init_();
</script>