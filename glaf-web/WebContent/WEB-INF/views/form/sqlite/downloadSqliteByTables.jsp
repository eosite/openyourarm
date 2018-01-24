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
        </style>
</head>
<body>
    <template id="tables_panel_template">
        <div class="portlet box light blue strruleportlet" style="margin:3px 3px;">
            <div class="portlet-title tabbable-line">
                
                <ul class="nav nav-tabs">  
                    <li class="active">
                        <a href="#tab_rule_0" data-toggle="tab" aria-expanded="false"> 子系统权限配置</a>
                    </li>
                    <!-- <li class="">
                        <a href="#tab_1" data-toggle="tab" aria-expanded="false"> 模块权限设置</a>
                    </li> -->
                    <li class="">
                        <a href="#tab_rule_2" data-toggle="tab" aria-expanded="false"> 表权限设置</a>
                    </li>
                </ul>
            </div>
            <div class="portlet-body" style="background:#FAFAFA;">
                <div class="tab-content" >
                    <div class="tab-pane active" id="tab_rule_0">
                        <div class="row">
                            <div class="col-xs-3" style="padding-right:0px;">
                                <div class="border_div">
                                    <!--标段信息-->
                                    <!-- <div class="row form-horizontal"> -->
                                        <!-- <div class="col-xs-12"> -->
                                        <div style="text-align: left;padding-left:10px;padding-top:10px">
                                            <label class="control-label">标段：
                                                <select id="select-biaoduan" style="width : 150px;"> </select>
                                            </label>
                                        </div>
                                        <!-- </div> -->
                                    <!-- </div> -->
                                    <!-- 类型 -->
                                    <!-- <div class="form-horizontal"> -->
                                        <!-- <div class="col-xs-12"> -->
                                        <div style="text-align: left;padding-left:10px;">
                                            <label class="control-label">类型：
                                                <select id="select-type" style="width : 150px;"></select>
                                            </label>
                                        </div>
                                        <!-- </div> -->
                                    <!-- </div> -->
                                        <!-- 查询 -->
                                        <div style="text-align: left;padding-left:10px;">
                                            <label class="control-label">表名：
                                                <input id="research_name_input" type="text" style="width : 150px;">
                                            </label>
                                            <button type="button" id="research_name_Btn" class="btn blue btn-sm" style="padding:3px 10px;border-style:none;">
                                                <i class="fa fa-search"></i>查询
                                            </button>
                                            <button type="button" id="search_s_name_Btn" class="btn blue btn-sm" style="padding:3px 10px;border-style:none;">
                                                <i class="fa fa-search"></i>查询并选中
                                            </button>
                                        </div>
                                        <div class="button_panel" id="button_panel" style="margin:3px 0px;">
                                            
                                        </div>
                                    <!--表信息树-->
                                    <div class="slimScroll_div" style="height:500px;">
                                        <div id="tableZtree" class="ztree"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-9">
                                <fieldset class="myFieldset">
                                        <legend>规则生成</legend>
                                <div style="padding:10px 10px;overflow:hidden;" id="downloadSqlite_form" class="form-horizontal">
                                    <div class="form-group">
                                        <div class="col-md-5">
                                            <label class="control-label col-md-3">名称：</label>
                                            <div class="col-md-8">
                                                <input type="text" name="name" class="form-control">
                                            </div>
                                        </div>
                                        <div class="col-md-5">
                                            <label class="control-label col-md-3">描述：</label>
                                            <div class="col-md-8">
                                                <input type="text" name="name" class="form-control">
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                                <button type="button" id="createLicenseBtn" class="btn blue btn-sm " >
                                            <i class="glyphicon glyphicon-ok"></i>根据表格生成sqlite
                                        </button>
                                        </div>
                                        
                                    </div>
                                </div>
                            </fieldset>
                                
                                <div>
                                    <fieldset class="myFieldset">
                                        <legend>规则信息</legend>
                                        <label class="control-label">数据导入规则：
                                            <select id="importDataRule" class="importDataRule">
                                            </select>
                                        </label>
                                    </fieldset>
                                </div>
                                <fieldset class="myFieldset">
                                <legend>已设置规则的表<span style="color:red;">(总数:<span id="gridDataCount"></span>)</span></legend>
                                    <div style="background-color:white;">
                                        <div id="checkedTableGrid" style="height:500px;">
                                        </div>
                                    </div>
                                </fieldset>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane" id="tab_rule_2">
                        <div class="row">
                            <div class="col-xs-3 ">
                                <!--系统树-->
                                <div class="border_div"> 
                                    <div class="slimScroll_div" style="height:630px;">
                                        <div id="systemZtree" class="ztree"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-9">
                                <div style="padding:10px 10px;overflow:hidden;" >
                                    <span>已勾选的模块信息</span>
                                    <button type="button" id="createLicenseBtnByModel" class="btn blue btn-sm pull-right" style="">
                                        <i class="glyphicon glyphicon-ok"></i>根据模块生成sqlite文件
                                    </button>
                                </div>
                                <div style="background-color:white;">
                                    <div id="checkedSystemGrid" style="height:585px;">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
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

<script type="text/javascript" src="/glaf/scripts/plugins/bootstrap/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="/glaf/scripts/plugins/bootstrap/datetimepicker/ext/jquery.datetimepicker.extends.js"></script>

<!-- 一些整合好的方法，如查询表格字段信息 -->
<script type="text/javascript" src="/glaf/webfile/js/builder.js"></script>
<script type="text/javascript" src="/glaf/webfile/js/builder.jsplumb.js"></script>

<style type="text/css">
    .select2-dropdown.select2-dropdown--below{
        z-index: 99999;
    }
    .grid_operator_div{
        display: inline-block;
        margin-left: 6px;
    }
    .sqliteDefaultBtn{
        margin-left: 3px;
        margin-top:1px;
    }
</style>


<script type ="text/javascript">
    
    var sqliteCode_ = '${sqliteCode}';
    var utableTreeUrl = contextPath + "/rs/isdp/cellUTableTree/getUtableTreeByTableTypeInit?1=1";   //表格树
    var cellUrl = contextPath + "/rs/isdp/treeDot/getTreeDotInit?1=1";// getTreeDotInit
    var $checkedTableGrid = null;
    var tableZtreeObj_ = null;
    var tableZtreeObj_checked_datas = []; //表格树的已设置规则的数据
    var $select_biaoduan = null;    //标段信息
    var $select_type = null;    //表格类型信息
    var $systemZtree = null;    //模块树
    var $checkedSystemGrid = null;  //模块选择。
     
    var dialog_ = null;

    var global_param_ = {
        userSqliteUrl : '/mx/form/dataset/userSqlite',
        userSqliteUploadUrl : '/mx/form/dataset/userSqliteUpload',
        importDataRule : [{
            "id":"1",
            "text":"无数据时插入"
        },{
            "id":"2",
            "text":"去重插入"
        },{
            "id":"4",
            "text":"覆盖插入"
        }],

        // ,{
        //     "id":"3",
        //     "text":"更新表结构并插入"
        // }
        
        slectedData : ["sys_tree","sys_department","net_role"]
    }

    var default_button_func = {
        //按钮所在的面板
        button_panel_ID : 'button_panel',
        defaultBtnClass : 'sqliteDefaultBtn',
        //生成默认的按钮信息
        initDefaultButton : function($button_panel){
            var $button_panel = $("#"+default_button_func.button_panel_ID);
            if($button_panel){
                $button_panel = $button_panel;
            }
            $.ajax({
                url: contextPath + global_param_.userSqliteUrl + "/getDefaultData",
                type: 'POST',
                dataType: 'json',
                // contentType: 'application/json',
            }).done(function(result) {
                default_button_func.createDefaultButton($button_panel,result);
                default_button_func.bindButtonEvent($button_panel);
            }).fail(function(e) {
                console.log(e);
            })
        },
        //创建默认按钮
        createDefaultButton : function($button_panel,datas){
            $.each(datas,function(i,item){
                var $button = $('<button type="button" class="btn yellow btn-sm hmtd-xs"></button>');
                $button.text(item.name);
                $button.data("sqliteData",item.data);
                $button.addClass(default_button_func.defaultBtnClass);
                $button_panel.append($button);
            })
        },
        //
        bindButtonEvent : function($button_panel){
            $button_panel.on("click","."+default_button_func.defaultBtnClass,function(event){
                var datas = $(this).data("sqliteData");
                $.each(datas,function(i,item){
                    item.biaoduan = $select_biaoduan.select2('data')[0].value;
                    item.biaoduanCn = $select_biaoduan.select2('data')[0].text;
                })
                $.merge(tableZtreeObj_checked_datas,datas);
                $checkedTableGrid.grid("_loadDatas",tableZtreeObj_checked_datas);
            })
        }

    }

    var downloadDataRuleSelect_Func = {
        /**
         * [创建数据导入规则的下拉框]
         * 有data时，复制出对应的信息
         * @param  {[type]} $body [下拉框对象所属的JQUERY对象]
         * @param  {[type]} data [description]
         * @return {[type]}      [description]
         */
        initOrCloneSelect : function($body,data){
            var $importDataRule = null;
            if($body){
                $importDataRule = $body.find("#importDataRule");
            }else{
                $importDataRule = $("#importDataRule");
            }
            if($importDataRule[0]){
                if(data){
                    $importDataRule = $importDataRule.clone();
                    $importDataRule.removeAttr("id");
                    $importDataRule.empty();    
                }
                $.each(global_param_.importDataRule,function(i,item){
                    var $option = $("<option>");
                    $option.text(item.text);
                    $option.val(item.id);
                    $importDataRule.append($option);
                    if(data && data.importDataRule == item.id){
                        $option.attr("selected",true);
                    }
                })
            }
            return $importDataRule;
        },
        /**
         * 获取选中的信息,返回字符串信息
         * @return {[string]} [select选中的值]
         */
        getSelectedVal : function($this){
            if($this){
                return $this.val();
            }
            return $("#importDataRule").val();
        }
    }

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

    function traverseZtree(datas,callback){
        if(!datas){
            return;
        }
        $.each(datas,function(i,item){
            if($.isFunction(callback)){
                callback(item);
            }
            traverseZtree(item.children,callback);
        })
    }
    var tableZtree_func = {
        setting : {
            view: {
                fontCss : function(treeId, node) {
                    return node.font ? node.font : {};
                }
            },
            async:{
                enable:true,
                url:utableTreeUrl,
                autoParam:["indexId","nlevel=level"],
                dataFilter : function(treeId, parentNode, responseData){
                    var datas = [];
                    if(datas && datas.length){
                        $.each(datas, function(i, node){
                            if(node.ds == "F"){ //数据集目录
                                node.icon = contextPath + '/images/folder-horizontal.png'; 
                                node.iconOpen = contextPath + '/images/folder-horizontal-open.png'; 
                                node.iconClose = contextPath + '/images/folder-horizontal.png';
                            } else  if(node.ds == 'T'){//数据集
                                node.icon = contextPath + '/images/blue-documents-stack.png'; 
                                node.tableName = node.indexId;
                            }
                            node.name = node.indexName;
                        });
                    }
                    if(responseData && responseData.length ){
                        var tableName;
                        $.each(responseData, function(i, v){
                            if((tableName = v.tableName )&& tableName.toLowerCase().indexOf("log_") == 0){
                                return false;
                            }
                            if(!tableName){
                                v.icon = contextPath + '/images/blue-folder-horizontal.png'; 
                                v.iconOpen = contextPath + '/images/blue-folder-horizontal-open.png'; 
                                v.iconClose = contextPath + '/images/blue-folder-horizontal.png';
                            }
                            datas.push(v);
                        });
                    }
                    var biaoduan = $select_biaoduan.select2('data')[0].value;
                    traverseZtree(datas,function(data){
                        var flag = false;
                        $.each(tableZtreeObj_checked_datas,function(i,item){
                            if(data.tableName == item.tableName && item.biaoduan == biaoduan){
                                data.checked = true;
                                data.chkDisabled = true;
                            }
                        });
                    })
                    return datas;
                }
            },
            data: {
                simpleData:{
                    enable:true,
                    idKey:"indexId",
                    pIdKey:"parentId"
                },
                key:{
                    name:"indexName",
                    title : 'tableName'
                }
            },
            'check': {
                'enable': true,
                'chkStyle': 'checkbox',
                'chkboxType': {
                    "Y": "s",
                    "N": "s"
                },
            },
            callback:{
                onClick : function(event, treeId, treeNode) {
                   
                },
                
                onCheck : function(event, treeId, treeNode) {
                    tableZtree_func.loadCheckedTable([treeNode]);
                }
            }
        },
        //初始化表格信息
        initTableZtree : function(tableType,ds){
            var $zTree = $("#tableZtree");
            if(tableZtreeObj_)
                tableZtreeObj_.destroy();

            if(tableType==1){
                tableZtree_func.setting.async.url = utableTreeUrl+"&type=1&tableType=2&systemName="+ds;
                tableZtreeObj_ = $.fn.zTree.init($zTree, tableZtree_func.setting); 
            }else if(tableType==2)  {
                tableZtree_func.setting.async.url = cellUrl+'&systemName='+ds;
                tableZtreeObj_ = $.fn.zTree.init($zTree, tableZtree_func.setting); 
            }else if(tableType==3){
                tableZtree_func.setting.async.url = utableTreeUrl+"&type=1&tableType=12&systemName="+ds;
                tableZtreeObj_ = $.fn.zTree.init($zTree, tableZtree_func.setting); 
            }else if(tableType==4){
                tableZtree_func.setting.async.url = utableTreeUrl+"&type=4&tableType=12&systemName="+ds;
                tableZtreeObj_ = $.fn.zTree.init($zTree, tableZtree_func.setting,[{'indexName':'系统内置表','parentId':-1,'indexId':1,id:'1|',isParent:true}]); 
            }else if(tableType==5){
                tableZtreeObj_ = $.fn.zTree.init($zTree, tableZtree_func.setting,[{'indexName':'自动生成表','parentId':-1,'indexId':-1,id:'1|'}]); 
            }
        },
        //刷新表格信息
        refreshZtree : function(){
            var biaoduan = $select_biaoduan.select2('data')[0].value;
            var tableType = $select_type.select2('data')[0].value;
            tableZtree_func.initTableZtree(tableType,biaoduan);
        },
        //选中树节点
        loadCheckedTable : function(treeNodes){
            var importDataRule = downloadDataRuleSelect_Func.getSelectedVal();
            var biaoduan = $select_biaoduan.select2('data')[0].value;
            var biaoduanCn = $select_biaoduan.select2('data')[0].text;
            var tableType = $select_type.select2('data')[0].value;
            traverseZtree(treeNodes,function(item){
                var flag = false;
                if(!item.isParent){
                    item.biaoduan = biaoduan;
                    item.biaoduanCn = biaoduanCn;
                    item.tableType = tableType;
                    item.importDataRule = importDataRule;
                    if(!item.chkDisabled){
                        tableZtreeObj_checked_datas.push(item);   
                    }
                    item.checked = true;
                    item.chkDisabled = true;
                }
            })
            $checkedTableGrid.grid("_loadDatas",tableZtreeObj_checked_datas);
        }
    }

    var getDataSets = (function(){
        return function(parentNode){
            if(!parentNode){
                return window.dataSetTrees;
            } else {
                return [];
            }
        };
    })();

    /**
     * [setSelectedDataRow description]
     * @param {[type]} idlist [为ID列表,如[1,23,4]]
     */
    function setSelectedDataRow(idlist){
        setSelectedDataRow.selectedRow.idlist = idlist.datalist;
        setSelectedDataRow.selectedRow.idColumnName = idlist.idColumnName;
        window.XXXdialog.close();
    }

    /**
     * 在$body下中生成表格选择面板
     * @param  {[type]} $body [生成]
     * @param  {[type]} data [初始化数据]
     * @param  {[type]} usersqliteId []
     * @return {[type]}       [description]
     */
    function initTablePanel($body,sqliteRuleData,usersqliteId){
        var checkedDatas = null;
        if(sqliteRuleData && typeof sqliteRuleData == 'string'){
            sqliteRuleData = JSON.parse(sqliteRuleData);
        }
        if(sqliteRuleData){
            var sqliteRuleData_ruleJson = sqliteRuleData.ruleJson;
            if(sqliteRuleData_ruleJson && typeof sqliteRuleData_ruleJson == 'string'){
                sqliteRuleData_ruleJson = JSON.parse(sqliteRuleData_ruleJson);
            }
            checkedDatas = sqliteRuleData_ruleJson.data;
            tableZtreeObj_checked_datas = checkedDatas;

            var $downloadSqlite_form = $("#downloadSqlite_form");
            $downloadSqlite_form.find("input[name='name']").val(sqliteRuleData.name);
            $downloadSqlite_form.find("input[name='desc']").val(sqliteRuleData.value);
        }
        

        $checkedTableGrid = $body.find("#checkedTableGrid"); //勾选的表格信息
        
        //初始化规则
        downloadDataRuleSelect_Func.initOrCloneSelect($body);

        tableZtreeObj_ = tableZtree_func.initTableZtree(1,'default');

        //初始化标段信息
        $select_biaoduan =  $body.find("#select-biaoduan");
        $.ajax({
            url: contextPath + '/rs/isdp/global/databaseJson',
            type: "post",
            dataType: "json",
            async: true,
            success: function(ret) {
                if(ret){
                    $.each(ret,function(i,item){
                        item.value = item.code;
                        item.id = item.code;
                    })
                    $select_biaoduan.select2({
                        minimumResultsForSearch: Infinity,
                        data : ret
                    });
                }
            },
            error: function() {
                alert("异常错误,请稍后再试.");
            }
        })
        
        //初始化表格类型信息
        $select_type = $body.find("#select-type");
        $.ajax({
            url: contextPath + '/mx/form/defined/getDictByCode/?code=tableType',
            type: "post",
            dataType: "json",
            async: true,
            success: function(ret) {
                if(ret){
                    $.each(ret,function(i,item){
                        item.value = item.code;
                        item.id = item.code;
                        item.text = item.name;
                    })
                    $select_type.select2({
                        minimumResultsForSearch: Infinity,
                        data : ret
                    });
                }
            },
            error: function() {
                alert("异常错误,请稍后再试.");
            }
        })

        $select_biaoduan.change(function(event){
            tableZtree_func.refreshZtree();
        })
        $select_type.change(function(event){
            tableZtree_func.refreshZtree();
        })

        $body.find("#research_name_Btn").click(function(event){
            searchProject();
        })
        $body.find("#search_s_name_Btn").click(function(event){
            searchProject(true);  
        })

        $checkedTableGrid.grid({
            datas:checkedDatas || [],
            scrollable: false,
            selectable:"sinagle",
            columns:[
                {title:'表中文名', field:'tableNameCN', sortable:false,'isEditor':false, 'attributes': {
                    'style': 'text-align:center;width:20%;'
                },},
                {title:'表名', field:'tableName',sortable:false,'isEditor':false, 'attributes': {
                    'style': 'text-align:center;width:25%;'
                },},
                {title:'标段', field:'biaoduanCn',sortable:false,'isEditor':false, 'attributes': {
                    'style': 'text-align:center;width:15%;'
                },},
                {title:'', field:'',sortable:false,'isEditor':false, 'attributes': {
                    'style': 'text-align:center;width:40%;'
                    },template : function(data){
                        var buttons = "<div class='grid_operator_div'>";
                        buttons += downloadDataRuleSelect_Func.initOrCloneSelect(null,data)[0].outerHTML;
                        buttons +="</div>";

                        if(global_param_.slectedData.indexOf(data.tableName)>-1){
                            buttons += "<div class='grid_operator_div'>";
                            buttons += '<button type="button" class="selectData btn blue btn-sm hmtd-xs" style=""><i class="fa fa-edit"></i>设置插入的数据</button>';
                            buttons +="</div>";  
                        }
                        buttons += "<div class='grid_operator_div'>";
                        buttons += '<button type="button" class="deleteBtn btn red btn-sm hmtd-xs" style=""><i class="fa fa-remove"></i>删除</button>';
                        buttons +="</div>"; 
                        return buttons;
                    },headerTemplate : function(data){
                        var buttons = "<div class='grid_operator_div'>";
                        buttons += '<button type="button" class="deleteAllBtn btn red btn-sm hmtd-xs" style=""><i class="fa fa-remove"></i>全部删除</button>';
                        buttons +="</div>";
                        return buttons;
                    }
                },
                
            ],
            events:{
                onLoadSuccess : function(data){
                    if(data){
                        $("#gridDataCount").text(data[0].length);    
                    }
                }
            }
        });
        //刪除功能
        $checkedTableGrid.on("click",".btn.deleteBtn",function(event){
            $checkedTableGrid.grid('removeRow',this);
            tableZtreeObj_checked_datas = $checkedTableGrid.grid("getData");
            tableZtree_func.refreshZtree();
            $("#gridDataCount").text($checkedTableGrid.grid("getData").length);    
        })
        $checkedTableGrid.on("click",".btn.deleteAllBtn",function(event){
            tableZtreeObj_checked_datas = [];
            $checkedTableGrid.grid("_loadDatas",[]);
             tableZtree_func.refreshZtree();
            $("#gridDataCount").text($checkedTableGrid.grid("getData").length);    
        })
        
        //设置上传规则信息
        $checkedTableGrid.on("change",".importDataRule",function(event){
            var selectedRows = $checkedTableGrid.grid("getSelectedRows");
            if(selectedRows && selectedRows[0]){
                var selectedRow = selectedRows[0];
                selectedRow.importDataRule = downloadDataRuleSelect_Func.getSelectedVal($(this));
            }
        })
        //设置导出的数据信息
        $checkedTableGrid.on("click",".btn.selectData",function(event){
            var selectedRows = $checkedTableGrid.grid("getSelectedRows");
            if(!selectedRows || !selectedRows[0]){
                return;
            }
            var selectedRow = selectedRows[0];
            setSelectedDataRow.selectedRow = selectedRow;
            window.XXXdialog = BootstrapDialog.show({
                title: "表("+selectedRow.tableName+")的数据选择",
                // draggable: true,
                message: "",
                // closeByBackdrop: true,
                closable: true,
                css: {
                    width: '885px',
                    height: '428px'
                },
                onhide: function(dialog) {
                },
                onshow: function(dialog){
                    var $body = dialog.getModalBody().find(".bootstrap-dialog-body");

                    var $iframe = $('<iframe style="width: 878px; height: 428px;border-width:0px;"></iframe>');
                    // $iframe.attr("src","/glaf/mx/sys/table/resultList?q=1&tableName_enc=3A1E3AEE34ACDD1CAF06FD17F06A8FECEBE5403AF1AE4B5238FDE1746545DFD4D78D732D290A6CD6D80833F9EF58586B47473E3B08B4F274B3CD5799A30800AB176263937089D0257940FD08CC940EA7&_=1496995358949");
                    
                    $iframe.attr("src", contextPath + "/mx/sys/table/resultList?retFn=setSelectedDataRow&type=sqlite&q=1&tableName="+selectedRow.tableName+"&_=1496995358949");

                    // var $div = $("<div>");
                    
                    // var tableObj = {
                    //     tableId : selectedRow.tableId,
                    //     tableName : selectedRow.tableName,
                    //     systemName : selectedRow.biaoduan,
                    // }
                    // var tcs = getFieldByTableId(tableObj).result;
                    // var columns = [];
                    // $.each(tcs.rows,function(i,item){
                    //     columns.push({
                    //         title:item.fname || item.dname || item.columnName,
                    //         field:item.columnName || item.dname,
                    //             'attributes': {
                    //             'style': 'text-align:center;'
                    //         },
                    //     })
                    // })
                    // $div.grid()
                    $body.append($iframe);
                    // $div.grid()
                }
            });
        })

        $body.find("#createLicenseBtn").click(function(event){
            var $thisButtom = $(this);
            if($thisButtom.attr("disabled")){
                return;
            }
            var $downloadSqlite_form = $("#downloadSqlite_form");
            if(!$downloadSqlite_form.find("input[name='name']").val()){
                alert("需要填写名称");
                return;
            }
            // var interfaceTime = $("#interfaceTime").datetimepickerExt("getValue");
            // if(!interfaceTime){
            //     alert("需要设置业务表表结构更新时间");
            //     return;
            // }
            $thisButtom.attr("disabled");
            //生成sqllite文件
            var ruleJson = [];
            var datas = $checkedTableGrid.grid("getData");
            $.each(datas,function(i,item){
                var param = {
                    tableName : item.tableName,
                    tableNameCN : item.tableNameCN,
                    biaoduan : item.biaoduan,
                    tableType : item.tableType,
                    biaoduanCn : item.biaoduanCn,
                    importDataRule : item.importDataRule,
                    idlist : item.idlist,
                    idColumnName : item.idColumnName,
                    id : item.id,
                }
                // if(tableName == 'r_interface'){
                //     param.interfaceTime = interfaceTime;
                // }
                ruleJson.push(param);
            })
            var param = {
                'ruleJson':JSON.stringify({
                    data : ruleJson,
                    type : 'table',
                    // interfaceTime : $("#interfaceTime").datetimepickerExt("getValue")
                }),
                name : $downloadSqlite_form.find("input[name='name']").val(),
                desc : $downloadSqlite_form.find("input[name='desc']").val(),
            }
            if(usersqliteId){
                param.id = usersqliteId;
            }
            $.ajax({
                url: contextPath + global_param_.userSqliteUrl+'/saveUserSqlite',
                type: "post",
                data: param,
                async: false,
                success: function(msg) {
                    alert("已放入正在生成列表中，请等待");
                    $("#userSqliteGrid").grid("query");
                    dialog_.close();
                },
                error: function() {
                    alert("异常错误,请稍后再试.");
                    $thisButtom.removeAttr("disabled");
                },
            })
        })

        $body.find("#createLicenseBtnByModel").click(function(event){
            var $thisButtom = $(this);
            if($thisButtom.attr("disabled")){
                return;
            }
            $thisButtom.attr("disabled");
            //生成sqllite文件
            var ruleJson = [];
            var datas = $checkedSystemGrid.grid("getData");
            $.each(datas,function(i,item){
                ruleJson.push({
                    code : item.code,
                    id : item.id,
                    name : item.name
                });
            })
            var param = {
                'ruleJson':JSON.stringify({
                    data : ruleJson,
                    type : 'model'
                })
            }
            if(usersqliteId){
                param.id = usersqliteId;
            }
            return;
            $.ajax({
                url: contextPath + global_param_.userSqliteUrl+'/saveUserSqlite',
                type: "post",
                data: param,
                async: false,
                success: function(msg) {
                    alert("已放入正在生成列表中，请等待");
                    $("#userSqliteGrid").grid("query");
                    dialog_.close();
                },
                error: function() {
                    alert("异常错误,请稍后再试.");
                    $thisButtom.removeAttr("disabled");
                },
            })
        })

        var systemZtreeObj_ = null;
        systemZtreeObj_ = $.fn.zTree.init($body.find("#systemZtree"),{
            async: {
                enable: true,
                url: contextPath + "/rs/sys/user/userMenusJson",
                dataFilter: function(treeId,parentNode,responseData){
                    if (!responseData) return null;

                    // setIcon(responseData);
                    for (var i=0, l=responseData.length; i<l; i++) {
                        responseData[i].name = responseData[i].name.replace(/\.n/g, '.');
                        // responseData[i].icon = contextPath + '/images/brick.png';
                        //if(responseData[i].iconCls=='icon-user'){
                           // responseData[i].icon="/glaf/images/user.gif";
                        //}
                    }
                    return responseData;
                }
            },
            'data': {
                'simpleData': {
                    'enable': true,
                    'idKey': 'id',
                    'pIdKey': 'parentId',
                }
            },
            'check': {
                'enable': true,
                'chkStyle': 'checkbox',
                'chkboxType': {
                    "Y": "s",
                    "N": "s"
                },
            },
            callback:{
                onClick : function(event, treeId, treeNode) {
                   
                },
                
                onCheck : function(event, treeId, treeNode) {
                    var nodes = systemZtreeObj_.getCheckedNodes(true);
                    for(var i = 0 ; i < nodes.length ; ){
                        var item = nodes[i];
                        if(item.isParent){
                            nodes.splice(i,1);
                        }else{
                            i++;
                        }
                    }

                    // systemZtreeObj_checked_datas = nodes;
                    $checkedSystemGrid.grid("_loadDatas",nodes);
                }
            }
        });

        $checkedSystemGrid  = $body.find("#checkedSystemGrid");
        $checkedSystemGrid.grid({
            datas:[],
            scrollable: false,
            columns:[
                {title:'系统名', field:'name', width:80, sortable:false,'isEditor':false, 'attributes': {
                    'style': 'text-align:center;'
                },},
                {title:'代码', field:'code', width:80, sortable:false,'isEditor':false, 'attributes': {
                    'style': 'text-align:center;'
                },},

            ],
        })
    }

    $(function(){
        var sqliteRuleData = null;
        if(sqliteCode_){
            $.ajax({
                url: contextPath + global_param_.userSqliteUrl + "/getBySqliteCode",
                type: 'POST',
                dataType: 'json',
                // contentType: 'application/json',
                async : false,
                "data": {
                    'sqliteCode' : sqliteCode_,
                },
            }).done(function(result) {
                sqliteRuleData = result;

            }).fail(function() {
                console.log("error");
            })
        }

        $("body").append($("#tables_panel_template").html());
        initTablePanel($("body"),sqliteRuleData,sqliteRuleData?sqliteRuleData.id:"");
        $("body").find(".slimScroll_div").slimScroll({
            // height: 500,
            alwaysVisible: true,
            railVisible: true,
            railColor: '#333',
        });
        $(".form_datetime").datetimepickerExt({
            format: "yyyy-mm-dd hh:ii:ss",
            defaultsystemdate: "null",
            position: "right top",
            visible: true,
            enabled: true,
            readable: true,
            clearBtn: false,
            time: Date(),
        });
        default_button_func.initDefaultButton();
    });

    function searchProject(isselect){
        var searchTextBox = $("#research_name_input"),val = searchTextBox.val();
        var f = tableZtreeObj_.expandAll(false), tmp = {};
        function updateHighlight(ns,flag){
            $.each(ns, function(i,node){
                node.highlight = flag || false;
                if(node.highlight){
                    node.font={'color':'red'};
                    if(isselect){
                        node.checked = true;
                        node.chkDisabled = true;
                    }
                }else{
                    delete node.font;
                }
                
                tableZtreeObj_.updateNode(node);
            });
        }
        
        function getNodesLike(value, collection){
            if(value && (value = $.trim(value))){
                $.each(["name", "tableName"], function(){
                    var ns = tableZtreeObj_.getNodesByParamFuzzy(this, value, null);
                    if(ns && ns.length){
                        $.each(ns, function(){
                            if(!this.isParent && !tmp[this.id]){                            
                                if(this.indexName && this.indexName.indexOf(this.tableName) < 0){
                                    this.indexName += " [" + this.tableName + "]";
                                }
                                delete this.font;
                                collection.push(this);
                                tmp[this.id] = this;
                            }
                        });
                    }
                });
            }
        }
        
        if(!f){
            setTimeout(function(){
                var ns = searchTextBox.data('nodes');
                if(ns && ns[0]){
                    updateHighlight(ns, false);
                    tableZtreeObj_.refresh();
                }
                if(val){
                    nodes = [];
                    if(val.indexOf(",") > 0){
                        $.each(val.split(","), function(){
                            getNodesLike(this, nodes);
                        });
                    } else {
                        getNodesLike(val, nodes);
                    }
                    if(nodes && nodes[0]){
                        $.each(nodes,function(i,node){
                            if(!node.isParent){
                                node = node.getParentNode();
                            }
                            tableZtreeObj_.expandNode(node, true, true, false);
                        });
                        if(isselect){
                            tableZtree_func.loadCheckedTable(nodes);
                        }
                        updateHighlight(nodes, true);
                        searchTextBox.data('nodes', nodes);
                        searchTextBox.data("n-index", 0);
                        window.setTimeout(function(){
                            window.selectNode();
                        }, 500);                    
                    }
                }
                
            }, 500);
        }
    }

    function selectNode(){
        var $o = $("#research_name_input");
        var nodes = $o.data("nodes"), index = ($o.data("n-index") || 0)*1;
        if(nodes && nodes.length){
            if(index >= nodes.length){
                index = nodes.length - 1;
                $o.data("n-index", -1);
            }
            var node = nodes[index*1];
            tableZtreeObj_.selectNode(node);
            
        }
    }

</script>