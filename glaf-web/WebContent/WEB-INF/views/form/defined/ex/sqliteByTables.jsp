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
        </style>
</head>
<body>
    <div class="portlet box light blue strruleportlet" style="margin:3px 3px;">
        <div class="portlet-title tabbable-line">
            <div class="caption">
                <i class="fa fa-cog"></i>sqlite文件信息
            </div>
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="#tab_0" data-toggle="tab" aria-expanded="false" value="download"> 导出sqlite文件</a>
                </li>
                <li class="">
                    <a href="#tab_1" data-toggle="tab" aria-expanded="false" value="upload"> 导入sqlite文件</a>
                </li>
            </ul>
        </div>
        <div class="portlet-body" style="background:#FAFAFA;">
            <div class="tab-content" >
                <div class="tab-pane active" id="tab_0">
                    <div>
                        <button type="button" id="showCraeteSqlliteBtn" class="btn green btn-sm" style="margin-bottom:20px;"><i class="glyphicon glyphicon-plus"></i>生成sqllite文件</button>
                        

                        <!-- <button type="button" id="importSqlliteBtn" class="btn green btn-sm" style=""><i class="glyphicon glyphicon-plus"></i>导入sqllite文件</button> -->
                        <div id="userSqliteGrid"></div>
                    </div>
                </div>
                <div class="tab-pane" id="tab_1">
                    <div class="form-inline" style="margin-bottom:20px;">
                        <form method="post" class="" id="uploadSqliteForm" style="text-align: center;" action="/glaf/mx/dataset/userSqliteUpload/upload" enctype="multipart/form-data">
                            <div class="form-group" >
                                <label class="control-label">文件名称：</label>
                                    <input name="fileName" class="form-control" type="text" >
                            </div>
                            <!-- <div class="form-group">
                                <label class="control-label">文件名称：</label>
                                    <input name="macCode" class="form-control" type="text" >
                            </div> -->
                            <div class="form-group" style="margin-left:10px;">
                                <label class="control-label">选择文件：</label>
                                <input id="uploadFile" class="form-control" placeholder="Choose File" disabled="disabled" />
                                <div class="fileUpload btn blue btn-sm ">
                                    <span><i class="fa fa-search"></i>选择文件</span>
                                    <input id="uploadBtn" type="file" name="file" class="upload" accept=".db" />
                                </div>
                            </div>
                            <div class="form-group" style="margin-left:10px;">
                                <button type="button" id="uploadSqlliteBtn" class="btn green btn-sm" ><i class="fa fa-cloud-upload"></i>导入sqllite文件</button>
                            </div>

                            <script type="text/javascript">
                                document.getElementById("uploadBtn").onchange = function () {
                                    document.getElementById("uploadFile").value = this.value;
                                };

                            </script>
                           <!--  <input type="file" name="file" accept=".db" />
                            <input type="submit" value="文件上传" /> -->
                        </form>
                    </div>
                    <div id="uploadSqliteGrid"></div>
                </div>
            </div>
        </div>
    </div>
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
                            <div class="col-xs-3">
                                <div class="border_div">
                                    <!--标段信息-->
                                    <!-- <div class="row form-horizontal"> -->
                                        <!-- <div class="col-xs-12"> -->
                                        <div style="text-align: center;padding-top:10px">
                                            <label class="control-label">标段：
                                                <select id="select-biaoduan" style="width : 150px;"> </select>
                                            </label>
                                        </div>
                                        <!-- </div> -->
                                    <!-- </div> -->
                                    <!-- 类型 -->
                                    <!-- <div class="form-horizontal"> -->
                                        <!-- <div class="col-xs-12"> -->
                                        <div style="text-align: center;">
                                            <label class="control-label">类型：
                                                <select id="select-type" style="width : 150px;"></select>
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
                                                <i class="fa fa-search"></i>查询
                                            </button>
                                        </div>
                                    <!--表信息树-->
                                    <div class="slimScroll_div" style="height:500px;">
                                        <div id="tableZtree" class="ztree"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-9">
                                <div style="padding:10px 10px;overflow:hidden;" >
                                    <span>已勾选的表信息<span>
                                    <button type="button" id="createLicenseBtn" class="btn blue btn-sm pull-right" style="">
                                        <i class="glyphicon glyphicon-ok"></i>根据表格生成sqlite文件
                                    </button>
                                </div>
                                <div style="background-color:white;">
                                    <div id="checkedTableGrid" style="height:585px;">
                                    </div>
                                </div>
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

<style type="text/css">
    .select2-dropdown.select2-dropdown--below{
        z-index: 99999;
    }
</style>


<script type ="text/javascript">
    var utableTreeUrl = contextPath + "/rs/isdp/cellUTableTree/getUtableTreeByTableTypeInit?1=1";   //表格树
    var cellUrl = contextPath + "/rs/isdp/treeDot/getTreeDotInit?1=1";// getTreeDotInit
    var $checkedTableGrid = null;
    var $select_biaoduan = null;    //标段信息
    var $select_type = null;    //表格类型信息
    var $systemZtree = null;    //模块树
    var $checkedSystemGrid = null;  //模块选择。
     
    var dialog_ = null;

    var global_param_ = {
        userSqliteUrl : '/mx/form/dataset/userSqlite',
        userSqliteUploadUrl : '/mx/form/dataset/userSqliteUpload',
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


    var tableZtreeSetting = {
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
                var nodes = tableZtreeObj_.getCheckedNodes(true);
                for(var i = 0 ; i < nodes.length ; ){
                    var item = nodes[i];
                    if(item.isParent){
                        nodes.splice(i,1);
                    }else{
                        item.biaoduan = $select_biaoduan.select2('data')[0].value;
                        item.biaoduanCn = $select_biaoduan.select2('data')[0].text;
                        item.tableType = $select_type.select2('data')[0].value;
                        i++;
                    }
                }
                tableZtreeObj_checked_datas = nodes;
                $checkedTableGrid.grid("_loadDatas",nodes);
            }
        }
    }

    /**
     * [重新初始化表格树]
     * @param  {[type]} tableType [表格类型]
     * @param  {[type]} ds        [标段]
     * @return {[type]}           [description]
     */
    function initTableZtree(tableType,ds){
        var $zTree = $("#tableZtree");
        tableZtreeObj_.destroy();

        if(tableType==1){
            tableZtreeSetting.async.url = utableTreeUrl+"&type=1&tableType=2&systemName="+ds;
            $.fn.zTree.init($zTree, tableZtreeSetting); 
        }else if(tableType==2)  {
            tableZtreeSetting.async.url = cellUrl+'&systemName='+ds;
            $.fn.zTree.init($zTree, tableZtreeSetting); 
        }else if(tableType==3){
            tableZtreeSetting.async.url = utableTreeUrl+"&type=1&tableType=12&systemName="+ds;
            $.fn.zTree.init($zTree, tableZtreeSetting); 
        }else if(tableType==4){
            tableZtreeSetting.async.url = utableTreeUrl+"&type=4&tableType=12&systemName="+ds;
            $.fn.zTree.init($zTree, tableZtreeSetting,[{'indexName':'系统内置表','parentId':-1,'indexId':1,id:'1|',isParent:true}]); 
        }else if(tableType==5){
            $.fn.zTree.init($zTree, tableZtreeSetting,[{'indexName':'自动生成表','parentId':-1,'indexId':-1,id:'1|'}]); 
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
     * 在$body下中生成表格选择面板
     * @param  {[type]} $body [生成]
     * @param  {[type]} data [初始化数据]
     * @return {[type]}       [description]
     */
    function initTablePanel($body,checkedDatas,usersqliteId){
        if(checkedDatas && typeof checkedDatas == 'string'){
            checkedDatas = JSON.parse(checkedDatas);
        }

        $checkedTableGrid = $body.find("#checkedTableGrid"); //勾选的表格信息
        var tableZtreeObj_checked_datas = null; //表格树的已勾选数据

        tableZtreeObj_ = $.fn.zTree.init($body.find("#tableZtree"),tableZtreeSetting);

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
            var tableType = $select_type.select2('data')[0].value;
            initTableZtree(tableType,$select_biaoduan.select2('data')[0].value);
        })
        $select_type.change(function(event){
            var biaoduan = $select_biaoduan.select2('data')[0].value;
            initTableZtree($select_type.select2('data')[0].value,biaoduan);
        })

        $body.find("#research_name_Btn").click(function(event){
            searchProject();
        })

        $checkedTableGrid.grid({
            datas:checkedDatas || [],
            scrollable: false,
            columns:[
                {title:'表中文名', field:'tableNameCN', width:80, sortable:false,'isEditor':false, 'attributes': {
                    'style': 'text-align:center;'
                },},
                {title:'表名', field:'tableName', width:80, sortable:false,'isEditor':false, 'attributes': {
                    'style': 'text-align:center;'
                },},
                {title:'标段', field:'biaoduanCn', width:80, sortable:false,'isEditor':false, 'attributes': {
                    'style': 'text-align:center;'
                },},
            ],
        });

        $body.find("#createLicenseBtn").click(function(event){
            var $thisButtom = $(this);
            if($thisButtom.attr("disabled")){
                return;
            }
            $thisButtom.attr("disabled");
            //生成sqllite文件
            var ruleJson = [];
            var datas = $checkedTableGrid.grid("getData");
            $.each(datas,function(i,item){
                ruleJson.push({
                    tableName : item.tableName,
                    tableNameCN : item.tableNameCN,
                    biaoduan : item.biaoduan,
                    tableType : item.tableType,
                    biaoduanCn : item.biaoduanCn,
                    id : item.id,
                });
            })
            var param = {
                'ruleJson':JSON.stringify({
                    data : ruleJson,
                    type : 'table'
                })
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
        var uploadSqliteGridUrl = contextPath + global_param_.userSqliteUploadUrl+'';

        $("#uploadSqliteGrid").grid({
            ajax: {
                read: {
                    url: uploadSqliteGridUrl + "/read",
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
                {title:'文件名称', field:'fileName', sortable:false,'isEditor':false, 'attributes': {
                    'style': 'text-align:center;width:15%;'
                },},
                {title:'文件位置', field:'filePath', sortable:false,'isEditor':false, 'attributes': {
                    'style': 'text-align:center;width:15%;'
                },},
                {title:'状态', field:'status', sortable:false,'isEditor':false, 'attributes': {
                    'style': 'text-align:center;width:15%;'
                },template : function(data){
                        if(data.status == 0){
                            return '导入失败！';
                        }else if(data.status == 1){
                            return '导入中！';
                        }else if(data.status == 2){
                            return '导入成功！'
                        }
                    }
                },
                {title:'导入时间', field:'uploadDate', sortable:false,'isEditor':false, 
                'editor':{'component':'datetimepicker',},'format':'yyyy-MM-dd HH:mm:ss',
                'attributes': {
                    'style': 'text-align:center;width:15%;'
                },},
                {title:'', field:'sqliteCode',sortable:false,'isEditor':false,
                    template : function(data){
                        var buttons = '<button type="button" class="showLogBtn btn blue btn-sm" style=""><i class="fa fa-search"></i>查看日志信息</button>'
                        buttons += '<button type="button" class="downLogBtn btn blue btn-sm" style=""><i class="fa fa-search"></i>下载日志文件</button>'
                        buttons += '<button type="button" class="downFileBtn btn blue btn-sm" style=""><i class="fa fa-search"></i>下载原文件</button>'
                        return buttons;
                    },
                    'style': 'width:40%;'
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

        var downloadSqliteUrl = contextPath + global_param_.userSqliteUrl+'/download';
        $("#userSqliteGrid").grid({
            ajax: {
                read: {
                    url: contextPath + "/mx/form/dataset/userSqlite/read",
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
                {title:'文件代码', field:'sqliteCode', sortable:false,'isEditor':false, 'attributes': {
                    'style': 'text-align:center;width:10%;'
                },},
                {title:'创建者', field:'createBy', sortable:false,'isEditor':false, 'attributes': {
                    'style': 'text-align:center;width:10%;'
                },},
                {title:'创建时间', field:'createDate', sortable:false,'isEditor':true, 
                'editor':{'component':'datetimepicker',},'format':'yyyy-MM-dd HH:mm:ss',
                'attributes': {
                    'style': 'text-align:center;width:10%;'
                },},
                {title:'修改者', field:'updateBy', sortable:false,'isEditor':false, 'attributes': {
                    'style': 'text-align:center;width:10%;'
                },},
                {title:'修改时间', field:'updateDate', sortable:false,'isEditor':false, 
                'editor':{'component':'datetimepicker',},'format':'yyyy-MM-dd HH:mm:ss',
                'attributes': {
                    'style': 'text-align:center;width:10%;'
                },},
                {title:'生成时间', field:'fileDate', sortable:false,'isEditor':false, 
                'editor':{'component':'datetimepicker',},'format':'yyyy-MM-dd HH:mm:ss',
                'attributes': {
                    'style': 'text-align:center;width:10%;'
                },},
                {title:'下载量', field:'downloadNum', sortable:false,'isEditor':false, 'attributes': {
                    'style': 'text-align:center;width:10%;'
                },},
                {title:'状态', field:'status', sortable:false,'isEditor':false, 'attributes': {
                    'style': 'text-align:center;width:10%;'
                },template : function(data){
                        if(data.status == 0){
                            return '生成失败！';
                        }else if(data.status == 1){
                            return '正在生成中！';
                        }else if(data.status == 2){
                            return '已生成！'
                        }
                    }
                },
                {title:'', field:'sqliteCode',sortable:false,'isEditor':false,
                    template : function(data){
                        var buttons = '<button type="button" class="editBtn btn blue btn-sm" style=""><i class="fa fa-edit"></i>修改</button>'
                        if(data.status == 2){
                            buttons += '<button type="button" class="downSqliteBtn btn yellow btn-sm" style=""><i class="fa fa-cloud-download"></i>下载文件</button>'    
                        }
                        return buttons;
                    },
                    'style': 'width:15%;'
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

        $("body").on('shown.bs.tab','a[data-toggle="tab"]',function (e) {
            // reInitDataGrid($dataGrid,$fieldGrid,initfunc,type);
            if($(this).attr("value") == 'upload'){
                $("#uploadSqliteGrid").grid("query");
            }else{
                $("#userSqliteGrid").grid("query");
            }
        })

        $("#userSqliteGrid").on("click",".btn.downSqliteBtn",function(event){
            var selectRows = $("#userSqliteGrid").grid("getSelectedRows");
            if(selectRows && selectRows.length > 0){
                window.open(downloadSqliteUrl+"?sqliteCode="+selectRows[0].sqliteCode);
            }
        })

        $("#userSqliteGrid").on("click",".btn.editBtn",function(event){
            var selectRows = $("#userSqliteGrid").grid("getSelectedRows");
            if(selectRows && selectRows.length > 0){
                var selectRow = selectRows[0];
                dialog_ = BootstrapDialog.show({
                    title: selectRow.sqliteCode+'的sqlite生成规则',
                    // draggable: true,
                    message: "",
                    // closeByBackdrop: true,
                    closable: true,
                    css: {
                        width: '980px',
                        height: '680px'
                    },
                    onhide: function(dialog) {
                    },
                    onshow: function(dialog){

                        var $body = dialog.getModalBody().find(".bootstrap-dialog-body");
                        $body.append($("#tables_panel_template").html());
                        
                        initTablePanel($body,selectRow.ruleJson,selectRow.id);

                        $body.find(".slimScroll_div").slimScroll({
                            // height: 500,
                            alwaysVisible: true,
                            railVisible: true,
                            railColor: '#333',
                        });
                    }
                });
            }
        })

        $("#showCraeteSqlliteBtn").click(function(event){
            dialog_ = BootstrapDialog.show({
                title: 'sqlite生成规则',
                draggable: true,
                message: "",
                closeByBackdrop: true,
                css: {
                    width: '980px',
                    height: '680px'
                },
                onhide: function(dialog) {
                },
                onshow: function(dialog){

                    var $body = dialog.getModalBody().find(".bootstrap-dialog-body");
                    $body.append($("#tables_panel_template").html());
                    
                    initTablePanel($body,null);

                    $body.find(".slimScroll_div").slimScroll({
                        // height: 500,
                        alwaysVisible: true,
                        railVisible: true,
                        railColor: '#333',
                    });
                }
            });
        })

        var downUploadSqliteGridUrl = contextPath + global_param_.userSqliteUploadUrl+'/download';
        $("#uploadSqliteGrid").on("click",".btn.downLogBtn",function(event){
            var selectRows = $("#uploadSqliteGrid").grid("getSelectedRows");
            if(selectRows && selectRows.length > 0){
                window.open(downUploadSqliteGridUrl+"?id="+selectRows[0].id + "&type=2");
            }
        })
        $("#uploadSqliteGrid").on("click",".btn.downFileBtn",function(event){
            var selectRows = $("#uploadSqliteGrid").grid("getSelectedRows");
            if(selectRows && selectRows.length > 0){
                window.open(downUploadSqliteGridUrl+"?id="+selectRows[0].id + "&type=1");
            }
        })
        $("#uploadSqliteGrid").on("click",".btn.showLogBtn",function(event){
            var selectRows = $("#uploadSqliteGrid").grid("getSelectedRows");
            if(selectRows && selectRows.length > 0){
                dialog_ = BootstrapDialog.show({
                    title: '文件：'+selectRows[0].fileName+'的导入日志',
                    draggable: true,
                    message: "",
                    closeByBackdrop: true,
                    css: {
                        width: '980px',
                        height: '680px'
                    },
                    onhide: function(dialog) {
                    },
                    onshow: function(dialog){

                        var $body = dialog.getModalBody().find(".bootstrap-dialog-body");
                        $body.append($('<pre id="textContent" style="word-wrap: break-word; white-space: pre-wrap; white-space: -moz-pre-wrap" >'));
                        
                        $.ajax({
                            url: downUploadSqliteGridUrl+"?id="+selectRows[0].id + "&type=2",
                            type: 'POST',
                            dataType: 'text',
                            "data": {},
                        }).done(function(result) {
                            $body.find("#textContent").text(result);

                        }).fail(function() {
                            console.log("error");
                        })
                    }
                });
            }

        })
        


        // initTablePanel();   //初始化表格面板

         $("#uploadSqlliteBtn").click(function(){
            if(!$("#uploadFile").val()){
                alert("未选择导入的文件！");
                return;
            }

            var FileController = contextPath+global_param_.userSqliteUploadUrl+'/upload';                    // 接收上传文件的后台地址 
            var xhr = new XMLHttpRequest();
            xhr.open("post", FileController, true);
            xhr.onload = function (oEvent) {
                if (xhr.status == 200) {
                    alert("导入sqlite文件成功");
                }else{
                    alert("导入sqlite文件失败");
                }
            };
            var formdata = new FormData($("#uploadSqliteForm")[0]);
            xhr.send(formdata); //发送请求
        })

        $(".slimScroll_div").slimScroll({
            // height: 600,
            alwaysVisible: true,
            railVisible: true,
            railColor: '#333',
        });
    });

    function searchProject(){
        var searchTextBox = $("#research_name_input"),val = searchTextBox.val();
        var f = tableZtreeObj_.expandAll(false), tmp = {};
        function updateHighlight(ns,flag){
            $.each(ns, function(i,node){
                node.highlight = flag || false;
                if(node.highlight){
                    node.font={'color':'red'};
                }else{
                    // delete node.font;
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