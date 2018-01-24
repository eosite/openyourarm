var dialog_ = null;

var _validateSetting = {
    
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
}

function converToJson(serializeArray) {
    var datas = {};
    $.each(serializeArray, function(i, item) {
        datas[item.name] = item.value;
    })
    return datas;
}

function resizePage($body) {
    var $body = $body || $("body");
    $body.find(".portlet").outerHeight($body.find(".portlet").parent().height());
    $body.find(".portlet-body").outerHeight($body.find(".portlet").height() - $body.find(".portlet-title").height());

    $body.find(".parentPanel").each(function(i, item) {
        var $item = $(item);
        var $dynamicPanelWidth = $(item).find(">.dynamicPanelWidth");
        if ($dynamicPanelWidth[0]) {
            $.each($dynamicPanelWidth, function(k, ktem) {
                var $ktem = $(ktem);

                var bortherWidth = 0;
                var $borther = $ktem.siblings();
                $borther.each(function(b, btem) {
                    if (!$(btem).is(":hidden")) {
                        bortherWidth += $(btem).outerWidth();
                    }
                })

                $ktem.outerWidth($item.width() - bortherWidth -5);
            })
        }

        var $dynamicPanelHeight = $(item).find(">.dynamicPanelHeight");

        if ($dynamicPanelHeight[0]) {
            $.each($dynamicPanelHeight, function(k, ktem) {
                var $ktem = $(ktem);

                var bortherHeight = 0;
                var $borther = $ktem.siblings();
                $borther.each(function(b, btem) {
                    if (!$(btem).is(":hidden")) {
                        bortherHeight += $(btem).outerHeight();
                    }
                })

                $ktem.outerHeight($item.height() - bortherHeight);
            })
        }
    })

    $(".slimScrollDivPanel").slimscroll({
        destroy: "destroy"
    });
    $(".slimScrollDivPanel").slimscroll({
        height: $(".slimScrollDivPanel").height(),
        alwaysVisible: false,
        railVisible: true,
        railColor: '#333',
    });
}

function initValidateSetting(defaultSetting,$form){
    var validateSetting = $.extend(true,{},defaultSetting);

    validateSetting.messages = {};
    validateSetting.rules = {};
    var $form = $form;
    $.each($form.find("[name]"),function(i,item){
        var $item = $(item);
        var name = $item.attr("name");
        validateSetting.rules[name] = validateSetting.rules[name] || {};
        validateSetting.messages[name] = validateSetting.messages[name] || {};
        if($item.attr("required")){
            validateSetting.messages[name].required = "必填项";
            validateSetting.rules[name].required = true;
        }
        if($item.attr("maxlength")){
            validateSetting.messages[name].maxlength = "长度必须小于"+$item.attr("maxlength");
            validateSetting.rules[name].maxlength = $item.attr("maxlength");
        }
    })
    return validateSetting;
}

$(function() {
    resizePage();
    window.onresize = function() {
        resizePage();
    }
    if(!_terminalId){
        alert("无法获取终端信息！");
    }

    var pageManageFunc = new PageManageFunc(_terminalId);
});

function PageManageFunc() {
    this.pageManage = {
        $grid: null,
        $ztree: null,
        $editZtreeTemplate: null,
        $editGridTemplate: null,
        $changeGridTypeTemplate: null,
        $editWarningItemTemplate:null,
        $editPeopleTemplate:null,
        $gridInfoTemplate:null,
        treeObj: null,
        prodTypeDatas : null,   //终端类型
        levelDatas : null,  //级别
        warningCodeDatas : null,    //预警项代码数据
        warningUnitDatas : null,    //预警项单位数据
        warningRefTypeDatas : null,    //预警项参照类型
        warningWarningTypeDatas : null, //提醒方式
        _gobalParam: {
            treeQueryUrl: contextPath + '/mx/monitor/monitorProcCategory/treeData',
            saveTreeUrl : contextPath + '/mx/monitor/monitorProcCategory/saveMonitorProcCategory',  //保存分类url
            deleteTreeUrl : contextPath + '/mx/monitor/monitorProcCategory/delete', //删除分类url 

            gridQueryUrl: contextPath + '/mx/monitor/monitorProc/read',
            saveGridUrl : contextPath + '/mx/monitor/monitorProc/saveMonitorProc',
            deleteGridUrl : contextPath + '/mx/monitor/monitorProc/delete', //删除分类url

            getRelationUrl : contextPath + '/mx/monitor/monitorProcBusCategory/list',
            addRelationUrl : contextPath + '/mx/monitor/monitorProcBusCategory/saveMonitorProcBusCategory',

            peopleQueryUrl : contextPath + '/mx/monitor/monitorProcUser/read',
            savePeopleUrl : contextPath + '/mx/monitor/monitorProcUser/saveMonitorProcUser',
            deletePeopleUrl : contextPath + '/mx/monitor/monitorProcUser/delete',   //删除分类url  

            warningItemQueryUrl : contextPath + '/mx/monitor/monitorProcItem/read',
            saveWarningItemUrl : contextPath + '/mx/monitor/monitorProcItem/saveMonitorProcItem',
            deleteWarningItemUrl : contextPath + '/mx/monitor/monitorProcItem/delete', //删除分类url  

            startProcUrl : contextPath + '/mx/momitor/server/startProc',   //启动操作
            stopProcUrl : contextPath + '/mx/momitor/server/stopProc',   //启动操作
            terminateProcUrl : contextPath + '/mx/momitor/server/terminateProc'   //启动操作
        },
        _init: function() {
            var that = this;
            that.$grid = $("#grid");
            that.$ztree = $("#categoryTree");
            that.$editZtreeTemplate = $("#editZtreeTemplate");
            that.$editGridTemplate = $("#editGridTemplate");
            that.$changeGridTypeTemplate = $("#changeGridTypeTemplate");
            that.$setWarningItemTemplate = $("#setWarningItemTemplate");
            that.$setPeopleTemplate = $("#setPeopleTemplate");
            that.$editWarningItemTemplate = $("#editWarningItemTemplate");
            that.$editPeopleTemplate = $("#editPeopleTemplate");
            that.$gridInfoTemplate = $("#gridInfoTemplate");
            that.prodTypeDatas = [
                {
                    text : '数据库',
                    value : 'database'
                },
                {
                    text : '应用服务器中间件',
                    value : 'middleware'
                }
            ];
            that.levelDatas = [
                {
                    text : '低',
                    value : '1'
                },
                {
                    text : '中',
                    value : '2'
                },
                {
                    text : '高',
                    value : '3'
                },
                {
                    text : '极高',
                    value : '4'
                }
            ];
            that.warningCodeDatas = [
                {
                    text : 'dir',
                    value : 'dir'
                },
                {
                    text : '内存',
                    value : 'mem'
                },
                {
                    text : '磁盘',
                    value : 'disk'
                },
                {
                    text : 'JVM内存',
                    value : 'jvmmem'
                }
            ];
            that.warningUnitDatas = [
                {
                    text : '百分比',
                    value : 'percent'
                },
                {
                    text : '数值',
                    value : 'numerial'
                }
            ];
            that.warningRefTypeDatas = [
                {
                    text : '可用不低于',
                    value : '1'
                },
                {
                    text : '使用不高于',
                    value : '2'
                }
            ];
            that.warningWarningTypeDatas = [
                {
                    text : '邮件',
                    value : 'email'
                },
                {
                    text : '短信',
                    value : 'sms'
                },
                {
                    text : '平台',
                    value : 'workspace'
                }
            ];
            that.roleDatas = [];

            that._loadGrid();
            that._loadTree();
            that._bindEvent();
        },
        gridSetting : {
            ajax: {
                read: {
                    // url: that._gobalParam.gridQueryUrl,
                    async: true,
                    // data: {"params" : params},
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
                        fields: {}
                    },
                    data: "rows",
                    total: "total"
                }
            },
            selectable: "sinagle",
            scrollable: false,
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
        },
        //加载grid
        _loadGrid: function() {
            var that = this;
            // var params = "{}";
            //查询出终端下面的数据
            var params = JSON.stringify({"terminalId" : _terminalId});
            var gridSetting = $.extend(true,{},that.gridSetting);
            gridSetting.ajax.read.url = that._gobalParam.gridQueryUrl;
            gridSetting.ajax.read.data = {"params" : params};
            gridSetting.columns = [{
                title: '程序名',
                field: 'processName',
                sortable: false,
                'isEditor': false,
                'attributes': {
                    'style': 'text-align:center;width:15%;font-weight:bold;'
                },
            },{
                title: '中文名称',
                field: 'name',
                sortable: false,
                'isEditor': false,
                'attributes': {
                    'style': 'text-align:center;width:15%;font-weight:bold;'
                },
            }, {
                title: '类型',
                field: 'type',
                sortable: false,
                'isEditor': false,
                'attributes': {
                    'style': 'text-align:center;width:10%;font-weight:bold;'
                },
            }, {
                title: '级别',
                field: 'level',
                sortable: false,
                'isEditor': false,
                'attributes': {
                    'style': 'text-align:center;width:10%;font-weight:bold;'
                },
                template : function(data){
                    var value = "";
                    if(that.levelDatas){
                        $.each(that.levelDatas,function(i,item){
                            if(item.level = item.value){
                                value = item.text;
                            }
                        });
                    }
                    return value;
                }
            }, {
                title: '状态',
                field: 'status',
                sortable: false,
                'isEditor': false,
                'attributes': {
                    'style': 'text-align:center;width:10%;font-weight:bold;'
                },
                template: function(data) {
                    if(data.status == null){
                        return '未检测';
                    }
                    if(data.status == '-1'){
                        return '挂起';
                    }else if(data.status == '0'){
                        return '未开启';
                    }else if(data.status == '1'){
                        return '已启动';
                    }
                }
            }, {
                title: '操作',
                field: 'subject',
                sortable: true,
                'isEditor': true,
                'attributes': {
                    'style': 'text-align:center;width:40%;'
                },
                'template': function(data) {
                    var buttonHtml = '<button type="button" class="showInfoBtn btn blue-hoki btn-sm " style="margin-top:5px;">' +
                        '<i class="fa fa-info-circle"></i>详细信息</button>';
                    buttonHtml += '<button type="button" class="showWarningItemBtn btn blue-hoki btn-sm " style="margin-top:5px;">' +
                        '<i class="fa fa-bell"></i>预警指标</button>';
                    buttonHtml += '<button type="button" class="showBaseItemBtn btn blue-hoki btn-sm " style="margin-top:5px;">' +
                        '<i class="fa fa-bell"></i>基础属性</button>';
                    // buttonHtml += '<button type="button" class="showProcBtn btn blue-hoki btn-sm " style="margin-top:5px;">' +
                    //     '<i class="fa fa-cubes"></i>服务器</button>';
                    buttonHtml += '<button type="button" class="showPerssonBtn btn blue-hoki btn-sm " style="margin-top:5px;">' +
                        '<i class="fa fa-users"></i>人员列表</button>';

                    buttonHtml += '<button type="button" class="showLog btn blue-hoki btn-sm " style="margin-top:5px;">' +
                        '<i class="fa fa-users"></i>查看日志</button>';

                    return buttonHtml;
                }
            }];
            gridSetting.events = {
                onClickRow: function(index, row) {
                    $("#editGridBtn").removeAttr("disabled");
                    $("#deleteGridBtn").removeAttr("disabled");
                    $("#changeTypeBtn").removeAttr("disabled");

                    if(row.status == 0 || row.status == -1){
                        $("#startBtn").removeAttr("disabled");    
                    }else if(row.status == 1){
                        $("#stopBtn").removeAttr("disabled");    
                        $("#terminateBtn").removeAttr("disabled");    
                    }
                },
                onLoadSuccess: function(data) {
                    $("#editGridBtn").attr("disabled","disabled");
                    $("#deleteGridBtn").attr("disabled","disabled");
                    $("#changeTypeBtn").attr("disabled","disabled");

                    $("#startBtn").attr("disabled","disabled");
                    $("#stopBtn").attr("disabled","disabled");
                    $("#terminateBtn").attr("disabled","disabled");

                    // $.each(that.$grid.find("tbody tr"),function(i,item){
                    //     var $tr = $(item);
                    //     var itemData = that.$grid.grid("getRow",item);
                    //     that._initGridInfoByEcharts($tr,itemData);    
                    // })
                }
            }
            that.$grid.grid(gridSetting);
        },
        _reloadGrid: function(param) {
            var that = this;
            that.$grid.grid("query",param);
        },
        ztreeSetting: {
            data:{
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pid",
                    rootPId: -1
                }
            },
            async: {
                enable: true,
                autoParam: ["id=pid"],
                dataFilter: function(treeId, parentNode, responseData) {
                    $.each(responseData.rows,function(i,item){
                        item.isParent = true;
                        item.icon = contextPath + '/images/server.png';
                    })
                    return responseData.rows;
                }
            },
            callback: {
                onClick: function(event, treeId, treeNode) {},
                onRightClick: function(event, treeId, treeNode) {},
                onAsyncSuccess: function(event, treeId, treeNode, msg) {}
            }
        },
        //加载树
        _loadTree: function() {
            var that = this;
            var ztreeSetting = $.extend(true,{},that.ztreeSetting);
            ztreeSetting.async.url = that._gobalParam.treeQueryUrl;
            ztreeSetting.callback.onClick = function(event, treeId, treeNode){
                //禁用按钮操作
                $("#addZtreeChildrenBtn").removeAttr("disabled");
                $("#deleteZtreeBtn").removeAttr("disabled");
                $("#editZtreeBtn").removeAttr("disabled");
                $("#addGridBtn").removeAttr("disabled");
                //重载grid
                var param ={
                    categoryId : treeNode.id
                }
                $(".terminalPanel .titleName").text('"' + treeNode.name + '"的终端信息');
                that._reloadGrid(param);
            }
            that.firstLoadZtree = true;
            ztreeSetting.callback.onAsyncSuccess = function(event, treeId, treeNode, msg) {
                //取消禁用按钮操作
                $("#editZtreeBtn").attr("disabled","disabled");
                $("#addZtreeChildrenBtn").attr("disabled","disabled");
                $("#deleteZtreeBtn").attr("disabled","disabled");
                $("#addGridBtn").attr("disabled","disabled");

                // 默认全部展开
                // if(that.firstLoadZtree){
                //     //第一个时展开全部节点
                //     var expandNodes = [];
                //     if(treeNode){
                //         expandNodes = treeNode.children || [];
                //     }else{
                //         expandNodes = that.treeObj.getNodes() || [];
                //     }

                //     $.each(expandNodes,function(i,item){
                //         that.treeObj.expandNode(item, true);  
                //     })
                //     that.firstLoadZtree = false;
                // }

                if($.isFunction(that.onTreeLoadedFunc)){
                    that.onTreeLoadedFunc(event, treeId, treeNode, msg);
                    that.onTreeLoadedFunc = null;
                }
            }

            that.treeObj = $.fn.zTree.init(that.$ztree, ztreeSetting);
        },
        /**
         * 重新加载树结构，如果有data，则联动完默认选中data的数据
         * @param  {[type]} data [description]
         * @param  {[type]} parentNode [父节点]
         * @return {[type]}      [description]
         */
        _reloadTree: function(data,parentNode) {
            var that = this;
            if(data != null){
                that.onTreeLoadedFunc = function(event, treeId, treeNode, msg){
                    var nodes = that.treeObj.getNodesByParam("id", data.id, null);
                    that.treeObj.expandNode(parentNode,true)
                    that.treeObj.selectNode(nodes[0]);
                }
            }
            that.treeObj.reAsyncChildNodes(parentNode, "refresh");
        },
        /**
         * 请求
         * @param  {[type]} $btn      [按钮对象，用于禁用]
         * @param  {[type]} url       [url]
         * @param  {[type]} param     [参数]
         * @param  {[type]} scallback [成功回调方法]
         * @return {[type]}           [description]
         */
        _doAjax : function($btn,url,param,scallback){
            var that = this;
            var $btn = $btn;
            if($btn.attr("disabled")){
                return;
            }
            var option = {
                url: url,
                async: false,
                type: 'post',
                data : param,
                // contentType : 'appliaction/json',
                // dataType: 'json',
                success: function(ret) {
                    if(typeof ret == 'string'){
                        ret = JSON.parse(ret);
                    }
                    if(ret.statusCode == '200'){
                        alert(ret.message || "操作成功");
                        if(scallback && $.isFunction(scallback)){
                            scallback(ret);
                        }
                    }else {
                        alert(ret.message || "操作失败");
                    }
                },
                complete: function(){
                    $btn.removeAttr("disabled");
                }
            }
            $btn.attr("disabled","disabled");
            $.ajax(option);
        },
        _openResultMessage : function(title,status,message){
            var height = window.document.body.clientHeight - 250 ;
            var width = window.document.body.clientWidth - 250 ;
            var title = title || '返回结果';
            if(status == null){
            }else if(status){
                title += "(成功)";
            }else{
                title += "(错误)";
            }

            var dialog = BootstrapDialog.show({
                title: title,
                draggable: false,
                message: "",
                closeByBackdrop: true,
                css: {
                    width: width,
                    height: '680px'
                },
                onhide: function(dialog) {
                },
                onshow: function(dialog){

                    var $dialogBody = dialog.getModalBody().find(".bootstrap-dialog-body");
                    var $textContent = $('<pre style="word-wrap: break-word; white-space: pre-wrap; white-space: -moz-pre-wrap" >');
                    $dialogBody.append($textContent);

                    if(status != null && !status){
                        $textContent.addClass("error");
                    }
                    
                    $textContent.height(height);
                    $textContent.text(message);
                }
            });
            dialog.getModalHeader().css({
                padding: '4px 4px',
            });
        },
        /**
         * 启动/停止/终止操作
         * @param  {[type]} $btn      [按钮对象，用于禁用]
         * @param  {[type]} url       [url]
         * @param  {[type]} param     [参数]
         * @param  {[type]} scallback [成功回调方法]
         * @return {[type]}           [description]
         */
        _doClientAjax : function($btn,url,param,scallback){
            var that = this;
            var $btn = $btn;
            if($btn.attr("disabled")){
                return;
            }
            var option = {
                url: url,
                async: false,
                type: 'post',
                data : param,
                // contentType : 'appliaction/json',
                // dataType: 'json',
                success: function(ret) {
                    if(typeof ret == 'string'){
                        ret = JSON.parse(ret);
                    }
                    var flag = true;
                    if(ret.statusCode == '200'){
                        if(scallback && $.isFunction(scallback)){
                            scallback(ret);
                        }
                    }else {
                        flag = false;
                    }
                    that._openResultMessage("",flag,ret.message);
                },
                complete: function(){
                    $btn.removeAttr("disabled");
                }
            }
            $btn.attr("disabled","disabled");
            $.ajax(option);
        },
        _bindEvent: function() {
            var that = this;
            //同级增加树节点
            $("#addZtreeBortherBtn").click(function(event) {
                //选取选中节点
                var data = null;
                var selectedNodes = that.treeObj.getSelectedNodes();
                if (selectedNodes && selectedNodes.length > 0) {
                    data = selectedNodes[0];
                }
                that._openEditZtreeDialog("新增同级分类", data, "addBorther");
            })
            $("#addZtreeChildrenBtn").click(function(event) {
                //选取选中节点
                var data = null;
                var selectedNodes = that.treeObj.getSelectedNodes();
                if (selectedNodes && selectedNodes.length > 0) {
                    data = selectedNodes[0];
                }else{
                    alert("未选中节点信息");
                    return;
                }
                that._openEditZtreeDialog("新增子级分类", data, "addChildren");
            })
            $("#editZtreeBtn").click(function(event) {
                //选取选中节点
                var data = null;
                var selectedNodes = that.treeObj.getSelectedNodes();
                if (selectedNodes && selectedNodes.length > 0) {
                    data = selectedNodes[0];
                }else{
                    alert("未选中节点信息");
                    return;
                }
                that._openEditZtreeDialog("新增子级分类", data, "edit");
            })
            $("#deleteZtreeBtn").click(function(event) {
                //选取选中节点
                var data = null;
                var selectedNodes = that.treeObj.getSelectedNodes();
                if (selectedNodes && selectedNodes.length > 0) {
                    data = selectedNodes[0];
                }else{
                    alert("未选中节点信息");
                    return;
                }

                var scallback = function(){
                    that._reloadTree();
                }
                var param = {
                    id : data.id
                }
                that._doAjax($(this),that._gobalParam.deleteTreeUrl,param,scallback);
            })

            $("#addGridBtn").click(function(event){
                var selectedNodes = that.treeObj.getSelectedNodes();
                var data = null;
                if (selectedNodes && selectedNodes.length > 0) {
                    data = selectedNodes[0];
                }else{
                    alert("未选中节点信息");
                    return;
                }
                var param = {
                    "categoryId" : data.id   
                }
                that._openEditGridDialog("新增终端",param);
            })
            $("#editGridBtn").click(function(event){
                var selectedNodes = that.$grid.grid("getSelectedRows");
                var data = null;
                if (selectedNodes && selectedNodes.length > 0) {
                    data = selectedNodes[0];
                }else{
                    alert("未选中节点信息");
                    return;
                }
                that._openEditGridDialog("编辑终端",data);    
            })
            $("#deleteGridBtn").click(function(event) {
                var selectedNodes = that.$grid.grid("getSelectedRows");
                var data = null;
                if (selectedNodes && selectedNodes.length > 0) {
                    data = selectedNodes[0];
                }else{
                    alert("未选中节点信息");
                    return;
                }
                var scallback = function(){
                    var selectedNodes = that.treeObj.getSelectedNodes();
                    var data = null;
                    if (selectedNodes && selectedNodes.length > 0) {
                        data = selectedNodes[0];
                    }
                    var param = {};
                    if(data && data.id){
                        param = {
                            "categoryId" : data.id   
                        }
                    }

                    that._reloadGrid(param);
                }
                var param = {
                    id : data.id
                }
                that._doAjax($(this),that._gobalParam.deleteGridUrl,param,scallback);
            })
            //设置分类
            $("#changeTypeBtn").click(function(event){
                var selectedNodes = that.$grid.grid("getSelectedRows");
                var data = null;
                if (selectedNodes && selectedNodes.length > 0) {
                    data = selectedNodes[0];
                }else{
                    alert("未选中节点信息");
                    return;
                }
                that._openChangeGridTypeDialog("设置分类",data);    
            })
            $(".terminalPanel").on("click",".showWarningItemBtn,.showBaseItemBtn",function(event){
                var selectedNodes = that.$grid.grid("getSelectedRows");
                var data = null;
                if (selectedNodes && selectedNodes.length > 0) {
                    data = selectedNodes[0];
                }else{
                    alert("未选中节点信息");
                    return;
                }
                if($(this).hasClass("showBaseItemBtn")){
                    that._openSetWarningItemDialog("设置基础属性",data,2);        
                }else{
                    that._openSetWarningItemDialog("设置预警项",data,1);    
                } 
            });

            $(".terminalPanel").on("click",".showInfoBtn",function(event){
                var selectedNodes = that.$grid.grid("getSelectedRows");
                var data = null;
                if (selectedNodes && selectedNodes.length > 0) {
                    data = selectedNodes[0];
                }else{
                    alert("未选中节点信息");
                    return;
                }
                that._openGridInfoDialog("监控终端信息",data);    
            });

            $(".terminalPanel").on("click",".showPerssonBtn",function(event){
                var selectedNodes = that.$grid.grid("getSelectedRows");
                var data = null;
                if (selectedNodes && selectedNodes.length > 0) {
                    data = selectedNodes[0];
                }else{
                    alert("未选中节点信息");
                    return;
                }
                that._openPeopleDialog("监控终端人员信息",data);    
            });

            $(".terminalPanel").on("click",".showLog",function(event){
                var selectedNodes = that.$grid.grid("getSelectedRows");
                var data = null;
                if (selectedNodes && selectedNodes.length > 0) {
                    data = selectedNodes[0];
                }else{
                    alert("未选中节点信息");
                    return;
                }
                var $btn = $(this);
                if($btn.attr("disabled")){
                    return;
                }
                var url = contextPath + "/mx/monitor/monitorLog/download";
                var param = {
                    id : data.id,
                    type : "PROC"
                }
                var option = {
                    url: url,
                    async: false,
                    type: 'post',
                    data : param,
                    // contentType : 'appliaction/json',
                    // dataType: 'json',
                    success: function(ret) {
                        that._openResultMessage("日志",null,ret); 
                    },
                    complete: function(){
                        $btn.removeAttr("disabled");
                    }
                }
                $btn.attr("disabled","disabled");
                $.ajax(option);
            });
            

            //启动
            $("#startBtn").click(function(event){
                var selectedNodes = that.$grid.grid("getSelectedRows");
                var data = null;
                if (selectedNodes && selectedNodes.length > 0) {
                    data = selectedNodes[0];
                }else{
                    alert("未选中节点信息");
                    return;
                }

                var scallback = function(){
                    if(data && data.id){
                        param = {
                            "categoryId" : data.id   
                        }
                    }
                    that._reloadGrid();
                }
                var param = {
                    id : data.id
                }
                that._doClientAjax($(this),that._gobalParam.startProcUrl,param,scallback);  
            })
            //停止
            $("#stopBtn").click(function(event){
                var selectedNodes = that.$grid.grid("getSelectedRows");
                var data = null;
                if (selectedNodes && selectedNodes.length > 0) {
                    data = selectedNodes[0];
                }else{
                    alert("未选中节点信息");
                    return;
                }

                var scallback = function(){
                    if(data && data.id){
                        param = {
                            "categoryId" : data.id   
                        }
                    }
                    that._reloadGrid();
                }
                var param = {
                    id : data.id
                }
                that._doClientAjax($(this),that._gobalParam.stopProcUrl,param,scallback);  
            })
            //终止
            $("#terminateBtn").click(function(event){
                var selectedNodes = that.$grid.grid("getSelectedRows");
                var data = null;
                if (selectedNodes && selectedNodes.length > 0) {
                    data = selectedNodes[0];
                }else{
                    alert("未选中节点信息");
                    return;
                }

                var scallback = function(){
                    if(data && data.id){
                        param = {
                            "categoryId" : data.id   
                        }
                    }
                    that._reloadGrid();
                }
                var param = {
                    id : data.id
                }
                that._doClientAjax($(this),that._gobalParam.terminateProcUrl,param,scallback);  
            })
        },
        /**
         * 获取类别与终端的关系数据
         * @param  {[type]} param [description]
         * @return {[type]}       [description]
         */
        _getRelation : function(param){
            var that = this;
            var datas;
            var option = {
                url: that._gobalParam.getRelationUrl,
                async: false,
                type: 'post',
                data : param,
                // contentType : 'appliaction/json',
                // dataType: 'json',
                success: function(ret) {
                    if(typeof ret == 'string'){
                        ret = JSON.parse(ret);
                    }
                    datas = ret.rows;
                }
            }
            $.ajax(option);
            return datas;
        },
        /**
         * 设置人员信息
         * @param  {[type]} title [弹出框标题]
         * @param  {[type]} data  [渲染数据]
         * @return {[type]}       [description]
         */
        _openPeopleDialog : function(title,data){
            var that = this;
            var data = data || {};
            //新增主题
            var height = window.outerHeight - 250 ;
            var width = window.outerWidth * 0.8;
            var dialog = BootstrapDialog.show({
                title: title || "设置预警项",
                draggable: false,
                css: {
                    width: width + 'px',
                    height: '500px'
                },
                onshown: function(dialog) {
                    var $modalBody = dialog.getModalBody();
                    if (!data) {
                        data = data || {};
                    }

                    var $templateHtml = $(ArtTemplateDataUtils.convertOne(data, that.$setPeopleTemplate.html()));
                    $modalBody.append($templateHtml);
                    $templateHtml.height(height);

                    // var params = "{}";
                    var params = JSON.stringify({"procId" : data.id});
                    var gridSetting = $.extend(true,{},that.gridSetting);
                    gridSetting.ajax.read.url = that._gobalParam.peopleQueryUrl;
                    gridSetting.ajax.read.data = {"params" : params};

                    gridSetting.columns = [{
                        title: '姓名',
                        field: 'username',
                        sortable: false,
                        'isEditor': false,
                        'attributes': {
                            'style': 'text-align:center;width:30%;'
                        },
                    }, {
                        title: '角色',
                        field: 'role',
                        sortable: false,
                        'isEditor': false,
                        'attributes': {
                            'style': 'text-align:center;width:20%;'
                        },
                    }, {
                        title: '手机号码',
                        field: 'phone',
                        sortable: false,
                        'isEditor': false,
                        'attributes': {
                            'style': 'text-align:center;width:20%;'
                        }
                    }, {
                        title: '邮箱地址',
                        field: 'email',
                        sortable: false,
                        'isEditor': false,
                        'attributes': {
                            'style': 'text-align:center;width:30%;'
                        }
                    }];
                    gridSetting.events = {
                        onClickRow: function(index, row) {
                        },
                        onLoadSuccess: function(data) {
                        }
                    }
                    var $grid = $modalBody.find("#peopleGrid");
                    $grid.grid(gridSetting);
                    
                    var scallback = function(data){
                        $grid.grid("query");
                    }
                    $modalBody.find(".addBtn").click(function(event){
                        //新增预警项
                        var selectedNode = {
                            procId : data.id
                        }
                        that._openEditPeopleDialog("新增人员信息",selectedNode,scallback);
                    })
                    $modalBody.find(".editBtn").click(function(event){
                        //修改预警项
                        var selectedNodes = $grid.grid("getSelectedRows");
                        var selectedNode = null;
                        if (selectedNodes && selectedNodes.length > 0) {
                            selectedNode = selectedNodes[0];
                        }else{
                            alert("未选中节点信息");
                            return;
                        }
                        selectedNode.procId = data.id;
                        that._openEditPeopleDialog("修改人员信息",selectedNode,scallback);
                    })
                    $modalBody.find(".deleteBtn").click(function(event){
                        //删除预警项
                        
                        //选取选中节点
                        var data = null;
                        var selectedNodes = $grid.grid("getSelectedRows");
                        if (selectedNodes && selectedNodes.length > 0) {
                            data = selectedNodes[0];
                        }else{
                            alert("未选中节点信息");
                            return;
                        }

                        var scallback = function(){
                            $grid.grid("query");
                        }
                        var param = {
                            id : data.id
                        }
                        that._doAjax($(this),that._gobalParam.deletePeopleUrl,param,scallback);

                    })

                    resizePage($modalBody);
                }
            });

            dialog.getModalHeader().css({
                padding: '4px 4px',
            });
            that.dialog = dialog;
        },
        /**
         * 编辑预警项
         * @param  {[type]} title     [弹出框标题]
         * @param  {[type]} data      [渲染数据]
         * @param  {[type]} scallback [编辑成功回调，用于重载表格]
         * @return {[type]}           [description]
         */
        _openEditPeopleDialog : function(title,data,scallback){
            var that = this;
            var data = data || {};
            var scallback = scallback;
            //编辑预警项
            var dialog = null;
            dialog = BootstrapDialog.show({
                title: title || "编辑人员信息",
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
                    data.roleDatas = that.roleDatas;

                    var $templateHtml = $(ArtTemplateDataUtils.convertOne(data, that.$editPeopleTemplate.html()));
                    $modalBody.append($templateHtml);

                    var $form = $modalBody.find("form");
                    var validateSetting = initValidateSetting(_validateSetting,$form);
                    var saveValidator = $form.validate(validateSetting);

                    $form.find("select").select2({
                        minimumResultsForSearch: -1
                    });

                    var saveUrl = that._gobalParam.savePeopleUrl;
                    $modalBody.find("#saveBtn").click(function(e) {
                        //修改预警项
                        //按钮
                        var $this = $(this);
                        //保存主题信息
                        if (!saveValidator.form() || saveUrl == null) {
                            return;
                        }
                        var paramData = $form.serializeArray();
                        paramData = converToJson(paramData);
                        
                        var option = {
                            url: saveUrl,
                            async: true,
                            type: 'post',
                            data : paramData,
                            // contentType : 'appliaction/json',
                            // dataType: 'json',
                            success: function(ret) {
                                var datas = "";
                                if(typeof ret == 'string'){
                                    ret = JSON.parse(ret);
                                }
                                if(ret.statusCode == '200'){
                                    //成功
                                    alert(ret.message || "操作成功");
                                    if($.isFunction(scallback)){
                                        scallback(ret);
                                    }
                                    dialog.close();
                                }else{
                                    //失败
                                    alert(ret.message || "操作失败");
                                }
                            }
                        }

                        $.ajax(option);
                    });
                    resizePage($modalBody);
                }
            });

            dialog.getModalHeader().css({
                padding: '4px 4px',
            });
        },
        /**
         * 设置预警项
         * @param  {[type]} title [弹出框标题]
         * @param  {[type]} data  [渲染数据]
         * @param  {[type]} type  [预警项/基础属性,1为预警项,2为基础属性]
         * @return {[type]}       [description]
         */
        _openSetWarningItemDialog : function(title,data,type){
            var that = this;
            var data = data || {};
            //新增主题
            var height = window.document.body.clientHeight - 250 ;
            var width = window.document.body.clientWidth * 0.8;
            var dialog = BootstrapDialog.show({
                title: title || "设置预警项",
                draggable: false,
                css: {
                    width: width + 'px',
                    height: '500px'
                },
                onshown: function(dialog) {
                    var $modalBody = dialog.getModalBody();
                    if (!data) {
                        data = data || {};
                    }

                    var $templateHtml = $(ArtTemplateDataUtils.convertOne(data, that.$setWarningItemTemplate.html()));
                    $modalBody.append($templateHtml);
                    $templateHtml.height(height);

                    // var params = "{}";
                    var params = JSON.stringify({"procId" : data.id,"type":type});
                    var gridSetting = $.extend(true,{},that.gridSetting);
                    gridSetting.ajax.read.url = that._gobalParam.warningItemQueryUrl;
                    gridSetting.ajax.read.data = {"params" : params};

                    data.warningRefTypeDatas = that.warningRefTypeDatas;
                    data.warningWarningTypeDatas = that.warningWarningTypeDatas;

                    var columns = {
                        1 : [{
                            title: '名称',
                            field: 'name',
                            sortable: false,
                            'isEditor': false,
                            'attributes': {
                                'style': 'text-align:center;width:25%;'
                            },
                        }, {
                            title: '代码',
                            field: 'code',
                            sortable: false,
                            'isEditor': false,
                            'attributes': {
                                'style': 'text-align:center;width:15%;'
                            },
                            template : function(nodeData){
                                var value = "";
                                if(!nodeData.code){
                                    return;
                                }
                                $.each(that.warningCodeDatas,function(i,item){
                                    if(item.value = nodeData.code){
                                        value = item.text;
                                        return false;
                                    }
                                })
                                return value;
                            }
                        }, {
                            title: '单位',
                            field: 'unit',
                            sortable: false,
                            'isEditor': false,
                            'attributes': {
                                'style': 'text-align:center;width:15%;'
                            },
                            template : function(nodeData){
                                var value = "";
                                if(!nodeData.unit){
                                    return;
                                }
                                $.each(that.warningUnitDatas,function(i,item){
                                    if(item.value = nodeData.unit){
                                        value = item.text;
                                        return false;
                                    }
                                })
                                return value;
                            }
                        }, {
                            title: '预警值',
                            field: 'alarmVal',
                            sortable: false,
                            'isEditor': false,
                            'attributes': {
                                'style': 'text-align:center;width:15%;'
                            },
                        }, {
                            title: '提醒方式',
                            field: 'warningType',
                            sortable: false,
                            'isEditor': false,
                            'attributes': {
                                'style': 'text-align:center;width:30%;'
                            },
                            template : function(nodeData){
                                var value = "";
                                if(nodeData.warningType){
                                    var warningTypes = nodeData.warningType.split(",");
                                    $.each(that.warningWarningTypeDatas,function(i,item){
                                        if(warningTypes.includes(item.value)){
                                            if(value){
                                                value += ",";
                                            }
                                            value += item.text;
                                        }
                                    })
                                }
                                
                                return value;
                            }
                        }],
                        2:[{
                            title: '名称',
                            field: 'name',
                            sortable: false,
                            'isEditor': false,
                            'attributes': {
                                'style': 'text-align:center;width:25%;'
                            },
                        }, {
                            title: '代码',
                            field: 'code',
                            sortable: false,
                            'isEditor': false,
                            'attributes': {
                                'style': 'text-align:center;width:15%;'
                            },
                            template : function(nodeData){
                                var value = "";
                                if(!nodeData.code){
                                    return;
                                }
                                $.each(that.warningCodeDatas,function(i,item){
                                    if(item.value = nodeData.code){
                                        value = item.text;
                                        return false;
                                    }
                                })
                                return value;
                            }
                        },{
                            title: '值',
                            field: 'value',
                            sortable: false,
                            'isEditor': false,
                            'attributes': {
                                'style': 'text-align:center;width:25%;'
                            },
                        }]
                    }

                    gridSetting.columns = columns[type];

                    gridSetting.events = {
                        onClickRow: function(index, row) {
                        },
                        onLoadSuccess: function(data) {
                        }
                    }
                    var $grid = $modalBody.find("#warningItemGrid");
                    $grid.grid(gridSetting);
                    
                    var scallback = function(data){
                        $grid.grid("query");
                    }
                    $modalBody.find(".addBtn").click(function(event){
                        //新增预警项
                        var selectedNode = {
                            procId : data.id,
                            type : type
                        }
                        var title = type == 2?"基础属性":"新增预警项";
                        that._openEditWarningItemDialog(title,selectedNode,scallback);
                    })
                    $modalBody.find(".editBtn").click(function(event){
                        //修改预警项
                        var selectedNodes = $grid.grid("getSelectedRows");
                        var selectedNode = null;
                        if (selectedNodes && selectedNodes.length > 0) {
                            selectedNode = selectedNodes[0];
                        }else{
                            alert("未选中节点信息");
                            return;
                        }
                        selectedNode.procId = data.id;
                         var title = type == 2?"基础属性":"新增预警项";
                        that._openEditWarningItemDialog(title,selectedNode,scallback);
                    })
                    $modalBody.find(".deleteBtn").click(function(event){
                        //删除预警项
                        
                        //选取选中节点
                        var data = null;
                        var selectedNodes = $grid.grid("getSelectedRows");
                        if (selectedNodes && selectedNodes.length > 0) {
                            data = selectedNodes[0];
                        }else{
                            alert("未选中节点信息");
                            return;
                        }

                        var scallback = function(){
                            $grid.grid("query");
                        }
                        var param = {
                            id : data.id
                        }
                        that._doAjax($(this),that._gobalParam.deleteWarningItemUrl,param,scallback);

                    })

                    resizePage($modalBody);
                }
            });

            dialog.getModalHeader().css({
                padding: '4px 4px',
            });
            that.dialog = dialog;
        },
        /**
         * 编辑预警项
         * @param  {[type]} title     [弹出框标题]
         * @param  {[type]} data      [渲染数据]
         * @param  {[type]} scallback [编辑成功回调，用于重载表格]
         * @return {[type]}           [description]
         */
        _openEditWarningItemDialog : function(title,data,scallback){
            var that = this;
            var data = data || {};
            var scallback = scallback;
            //编辑预警项
            var dialog = null;
            dialog = BootstrapDialog.show({
                title: title || "编辑预警项",
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
                    data.warningCodeDatas = that.warningCodeDatas;
                    data.warningUnitDatas = that.warningUnitDatas;
                    data.warningRefTypeDatas = that.warningRefTypeDatas;
                    data.warningWarningTypeDatas = that.warningWarningTypeDatas;

                    var $templateHtml = $(ArtTemplateDataUtils.convertOne(data, that.$editWarningItemTemplate.html()));
                    $modalBody.append($templateHtml);

                    var $form = $modalBody.find("form");
                    var validateSetting = initValidateSetting(_validateSetting,$form);
                    var saveValidator = $form.validate(validateSetting);

                    $form.find("select").select2({
                        minimumResultsForSearch: -1
                    });
                    if(data.warningType){
                        $form.find('[name="warningType"]').val(data.warningType.split(",")).trigger('change');    
                    }

                    var saveUrl = that._gobalParam.saveWarningItemUrl;
                    $modalBody.find("#saveBtn").click(function(e) {
                        //修改预警项
                        //按钮
                        var $this = $(this);
                        //保存主题信息
                        if (!saveValidator.form() || saveUrl == null) {
                            return;
                        }
                        var paramData = $form.serializeArray();
                        paramData = converToJson(paramData);
                        var k = $form.find('[name="warningType"]').val();
                        if(k){
                            paramData.warningType = k.join(",")    
                        }
                        
                        var option = {
                            url: saveUrl,
                            async: true,
                            type: 'post',
                            data : paramData,
                            // contentType : 'appliaction/json',
                            // dataType: 'json',
                            success: function(ret) {
                                var datas = "";
                                if(typeof ret == 'string'){
                                    ret = JSON.parse(ret);
                                }
                                if(ret.statusCode == '200'){
                                    //成功
                                    alert(ret.message || "操作成功");
                                    if($.isFunction(scallback)){
                                        scallback(ret);
                                    }
                                    dialog.close();
                                }else{
                                    //失败
                                    alert(ret.message || "操作失败");
                                }
                            }
                        }

                        $.ajax(option);
                    });
                    resizePage($modalBody);
                }
            });

            dialog.getModalHeader().css({
                padding: '4px 4px',
            });
        },
        /**
         * 重新设置终端类型
         * @param  {[type]} title [弹出框标题]
         * @param  {[type]} data  [渲染数据]
         * @return {[type]}       [description]
         */
        _openChangeGridTypeDialog: function(title, data) {
            var that = this;
            var data = data || {};
            //新增主题
            var height = window.outerHeight * 0.8;
            var dialog = BootstrapDialog.show({
                title: title || "设置分类",
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

                    data.prodTypeDatas = that.prodTypeDatas;
                    data.levelDatas = that.levelDatas;

                    //获取类别与终端的关系数据，用于回显
                    var relationDatas = that._getRelation({
                        procId : data.id
                    }) || [];

                    var $templateHtml = $(ArtTemplateDataUtils.convertOne(data, that.$changeGridTypeTemplate.html()));
                    $templateHtml.height(height);
                    $modalBody.append($templateHtml);

                    var ztreeSetting = $.extend(true,{},that.ztreeSetting);
                    ztreeSetting.async.url = that._gobalParam.treeQueryUrl;
                    ztreeSetting.check = {
                        enable: true,
                        chkStyle: "checkbox",
                        chkboxType: { "Y": "", "N": "" }
                    }
                    ztreeSetting.async.dataFilter = function(treeId, parentNode, responseData){
                        $.each(responseData.rows,function(i,item){
                            item.isParent = true;
                            item.icon = contextPath + '/images/server.png';
                            //回显，类别
                            $.each(relationDatas,function(k,ktem){
                                if(ktem.categoryId == item.id){
                                    item.checked = true;
                                }
                            });
                        })
                        return responseData.rows;
                    }
                    ztreeSetting.callback.onAsyncSuccess = function(event, treeId, treeNode, msg) {
                        that.dialog.treeObj.expandAll(true);
                    }
                    that.dialog.treeObj = $.fn.zTree.init($modalBody.find("#selectCategoryTree"), ztreeSetting);


                    $modalBody.find("#saveBtn").click(function(event){
                        //设置终端分类
                        var $thisBtn = $(this);
                        if($thisBtn.attr("disabled")){
                            return;
                        };
                        //获取勾选的分类节点信息
                        var checkedNodes = that.dialog.treeObj.getCheckedNodes();
                        var categoryIds = "";
                        if (checkedNodes && checkedNodes.length > 0) {
                            $.each(checkedNodes,function(i,item){
                                if(categoryIds){
                                    categoryIds += ",";
                                }
                                categoryIds += item.id;
                            })
                        }else{
                            alert("未选中节点信息");
                            return;
                        }
                        var params = {
                            procId : data.id,
                            categoryIds : categoryIds
                        }
                        $thisBtn.attr("disabled",true);    
                        var option = {
                            url: that._gobalParam.addRelationUrl,
                            async: true,
                            type: 'post',
                            data : params,
                            // contentType : 'appliaction/json',
                            // dataType: 'json',
                            success: function(ret) {
                                if(typeof ret == 'string'){
                                    ret = JSON.parse(ret);
                                }
                                if(ret.statusCode == '200'){
                                    alert(ret.message || "操作成功");
                                    that.dialog.close();
                                }else{
                                    alert(ret.message || "操作失败");
                                }
                            },
                            complete: function(ret){
                                $thisBtn.removeAttr("disabled");
                            }
                        }
                        $.ajax(option);
                    })

                    resizePage($modalBody);
                }
            });

            dialog.getModalHeader().css({
                padding: '4px 4px',
            });
            that.dialog = dialog;
        },

        /**
         * 编辑终端信息
         * @param  {[type]} title [弹出框标题]
         * @param  {[type]} data  [渲染数据]
         * @return {[type]}       [description]
         */
        _openEditGridDialog: function(title, data) {
            var that = this;
            //新增主题
            var dialog = BootstrapDialog.show({
                title: title || "监控终端编辑",
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

                    data.prodTypeDatas = that.prodTypeDatas;
                    data.levelDatas = that.levelDatas;

                    var templateHtml = ArtTemplateDataUtils.convertOne(data, that.$editGridTemplate.html());
                    $modalBody.append(templateHtml);

                    var scallback = function(data){
                        var selectedNodes = that.treeObj.getSelectedNodes();
                        var data = null;
                        if (selectedNodes && selectedNodes.length > 0) {
                            data = selectedNodes[0];
                        }
                        var param = {};
                        if(data && data.id){
                            param = {
                                "categoryId" : data.id   
                            }
                        }

                        that._reloadGrid(param);
                    }

                    that._bindDailogEvent($modalBody, that._gobalParam.saveGridUrl,scallback);
                }
            });

            dialog.getModalHeader().css({
                padding: '4px 4px',
            });
            that.dialog = dialog;
        },
        /**
         * 编辑分类信息
         * @param  {[type]} title [弹出框标题]
         * @param  {[type]} data  [渲染数据]
         * @param  {[type]} type  [同级或子级]
         * @return {[type]}       [description]
         */
        _openEditZtreeDialog: function(title, data, type) {
            var that = this;
            //新增主题
            var dialog = BootstrapDialog.show({
                title: title || "分类编辑",
                draggable: false,
                css: {
                    width: '800px',
                    height: '500px'
                },
                onshown: function(dialog) {
                    var $modalBody = dialog.getModalBody();

                    //获取父节点信息
                    var parentNode;
                    if(data != null){
                        parentNode = data.getParentNode();
                    }

                    if(type == 'addBorther'){
                        //加同级节点，则indexId和treeId取父节点
                        if(parentNode != null){
                            data = {
                                parentIndexId : parentNode.id,
                                parentTreeId : parentNode.treeid
                            }
                        }else{
                            data = {};
                        }
                    }else if(type == 'addChildren'){
                        parentNode = data;
                        //加子级节点，则indexId和treeId取该节点
                        data = {
                            parentIndexId : data.id,
                            parentTreeId : data.treeid
                        }
                    }else if(type = 'edit'){
                        if(!data){
                            return;
                        }
                    }else{
                        return;
                    }

                    if (!data) {
                        data = data || {};
                    }

                    var templateHtml = ArtTemplateDataUtils.convertOne(data, that.$editZtreeTemplate.html());
                    $modalBody.append(templateHtml);

                    var scallback = function(data){
                        that._reloadTree(data,parentNode);
                    }

                    that._bindDailogEvent($modalBody, that._gobalParam.saveTreeUrl, scallback);
                }
            });

            dialog.getModalHeader().css({
                padding: '4px 4px',
            });
            that.dialog = dialog;
        },
        /**
         * 注册模态框中的基础事件
         * @param  {[type]} $modalBody [description]
         * @param  {[type]} saveUrl    [description]
         * @param  {[type]} scallback  [保存成功时回调方法]
         * @return {[type]}            [description]
         */
        _bindDailogEvent: function($modalBody, saveUrl, scallback) {
            var that = this;
            // var saveValidator = $modalBody.find("form").validate(_validateSetting);
            var $form = $modalBody.find("form");
            //初始化下拉框
            $form.find("select").select2({
                minimumResultsForSearch: -1
            });

            var validateSetting = initValidateSetting(_validateSetting,$form);
            var saveValidator = $form.validate(validateSetting);
            $modalBody.find("#saveBtn").click(function(e) {
                //按钮
                var $this = $(this);
                //保存主题信息
                if (!saveValidator.form() || saveUrl == null) {
                    return;
                }
                var paramData = $modalBody.find("form").serializeArray();
                paramData = converToJson(paramData);
                //加上终端信息
                paramData.terminalId = _terminalId;
                var option = {
                    url: saveUrl,
                    async: true,
                    type: 'post',
                    data : paramData,
                    // contentType : 'appliaction/json',
                    // dataType: 'json',
                    success: function(ret) {
                        var datas = "";
                        if(typeof ret == 'string'){
                            ret = JSON.parse(ret);
                        }
                        if(ret.statusCode == '200'){
                            //成功
                            alert(ret.message || "操作成功");
                            //重新加载树
                            if($.isFunction(scallback)){
                                scallback(ret.data);
                            }
                            that.dialog.close();
                        }else{
                            //失败
                            alert(ret.message || "操作失败");
                        }
                    }
                }

                $.ajax(option);
            });
        },
        /**
         * 查看终端详细信息
         * @param  {[type]} title [弹出框标题]
         * @param  {[type]} data  [渲染数据]
         * @return {[type]}       [description]
         */
        _openGridInfoDialog: function(title, data) {
            var that = this;
            //新增主题
            var height = window.outerHeight - 250 ;
            var dialog = BootstrapDialog.show({
                title: title || "监控终端信息",
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
                    if(data.memSize){
                        data.memSizeKb = data.memSize / 1024.0;
                        data.memSizeMb = data.memSizeKb / 1024.0;
                        data.memSizeGb = data.memSizeMb / 1024.0;

                        data.memSizeKb = data.memSizeKb.toFixed(2);
                        data.memSizeMb = data.memSizeMb.toFixed(2);
                        data.memSizeGb = data.memSizeGb.toFixed(2);
                    }
                    if(data.diskSize){
                        data.diskSizeMb = data.diskSize / 1024.0;
                        data.diskSizeGb = data.diskSizeMb / 1024.0;

                        data.diskSizeMb = data.diskSizeMb.toFixed(2);
                        data.diskSizeGb = data.diskSizeGb.toFixed(2);
                    }
                    var $templateHtml = $(ArtTemplateDataUtils.convertOne(data, that.$gridInfoTemplate.html()));
                    $templateHtml.height(height);
                    $modalBody.append($templateHtml);

                    var myChart = that._initGridInfoByEcharts($modalBody,data,["28%","72%"]);

                    $modalBody.on('shown.bs.tab','a[data-toggle="tab"]', function(e) {
                        myChart.resize();
                    })

                    resizePage($modalBody);
                }
            });

            dialog.getModalHeader().css({
                padding: '4px 4px',
            });
            that.dialog = dialog;
        },
        highchartSetting : {
            "credits": {
                "enabled": false    //去掉水印
            },
            "exporting": {
                "enabled": false    //不允许导出图片
            },
            chart: {
                type: 'gauge',
                plotBackgroundColor: null,
                plotBackgroundImage: null,
                plotBorderWidth: 0,
                plotShadow: false
            },
            title: {
                text: ''
            },
            pane: {
                startAngle: -130,
                endAngle: 130,
                background: [{
                    backgroundColor: {
                        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
                        stops: [
                            [0, '#FFF'],
                            [1, '#333']
                        ]
                    },
                    borderWidth: 0,
                    outerRadius: '109%'
                }, {
                    backgroundColor: {
                        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
                        stops: [
                            [0, '#333'],
                            [1, '#FFF']
                        ]
                    },
                    borderWidth: 1,
                    outerRadius: '107%'
                }, {
                    // default background
                }, {
                    backgroundColor: '#DDD',
                    borderWidth: 0,
                    outerRadius: '105%',
                    innerRadius: '103%'
                }]
            },
            // the value axis
            yAxis: {
                min: 0,
                max: 100,
                minorTickInterval: 'auto',
                minorTickWidth: 1,
                minorTickLength: 10,
                minorTickPosition: 'inside',
                minorTickColor: '#666',
                tickPixelInterval: 30,
                tickWidth: 2,
                tickPosition: 'inside',
                tickLength: 10,
                tickColor: '#666',
                labels: {
                    step: 2,
                    rotation: 'auto'
                },
                title: {
                    // text: 'km/h'
                },
                plotBands: [{
                    from: 0,
                    to: 50,
                    color: '#55BF3B' // green
                }, {
                    from: 50,
                    to: 80,
                    color: '#DDDF0D' // yellow
                }, {
                    from: 80,
                    to: 100,
                    color: '#DF5353' // red
                }]
            },
            series: [{
                name: 'CPU',
                data: [80],
                tooltip: {
                    valueSuffix: ' km/h'
                }
            }]
        },
        _initGridInfoByHighcharts : function($modalBody,terminalData,height){
            var that = this;
            var $cpuGauge = $modalBody.find('#cpuGauge');
            var $memGauge = $modalBody.find('#memGauge');
            var $diskGauge = $modalBody.find('#diskGauge');

            var data = JSON.parse(terminalData.otherItems);

            //获取CUP信息
            var usedValue = data.cup?data.cup.used || 0 : 0;
            var totalValue = data.cup?data.cup.total || 1 : 1;
            //计算百分比
            var value = usedValue / totalValue * 100;
            if(value){
                value = value.toFixed(2);
            }
            //生成仪表盘
            var highchartSetting = $.extend(true,{},that.highchartSetting);
            highchartSetting.title.text = "CUP使用情况"; //设置名称
            if(height){
                highchartSetting.chart.height = height;
            }
            // highchartSetting.yAxis.title.text = "CUP使用情况"; //设置名称
            highchartSetting.series = [{
                name: 'CPU',
                data: [value],
                tooltip: {
                    valueSuffix: '%'
                }
            }]
            $cpuGauge.highcharts(highchartSetting);

            //获取内存信息
            usedValue = data.mem?data.mem.used || 0 : 0;
            totalValue = data.mem?data.mem.total || 1 : 1;
            //计算百分比
            value = usedValue / totalValue * 100;
            if(value){
                value = value.toFixed(2);
            }
            //生成仪表盘
            highchartSetting = $.extend(true,{},that.highchartSetting);
            if(height){
                highchartSetting.chart.height = height;
            }
            highchartSetting.title.text = "内存使用情况"; //设置名称
            // highchartSetting.yAxis.title.text = "内存使用情况"; //设置名称
            highchartSetting.series = [{
                name: '内存',
                data: [value],
                tooltip: {
                    valueSuffix: '%'
                }
            }]
            $memGauge.highcharts(highchartSetting);

            //获取磁盘信息
            usedValue = data.disk?data.disk.used || 0 : 0;
            totalValue = data.disk?data.disk.total || 1 : 1;
            //计算百分比
            value = usedValue / totalValue * 100;
            if(value){
                value = value.toFixed(2);
            }
            //生成仪表盘
            highchartSetting = $.extend(true,{},that.highchartSetting);
            if(height){
                highchartSetting.chart.height = height;
            }
            highchartSetting.title.text = "磁盘使用情况"; //设置名称
            // highchartSetting.yAxis.title.text = "磁盘使用情况"; //设置名称
            highchartSetting.series = [{
                name: '磁盘',
                data: [value],
                tooltip: {
                    valueSuffix: '%'
                }
            }]
            $diskGauge.highcharts(highchartSetting);
        },
        echartSetting : {
            tooltip : {
                formatter: "{a} <br/>{c} {b}"
            },
            toolbox: {
                show: false,
                feature: {
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            series:[],
            leftSerie : {
                name: '转速',
                type: 'gauge',
                center: ['25%', '50%'],    // 默认全局居中
                radius: '60%',
                min:0,
                max:100,
                startAngle: 340,
                endAngle: 20,
                splitNumber:10,
                axisLine: {            // 坐标轴线
                    lineStyle: {       // 属性lineStyle控制线条样式
                        width: 5
                    }
                },
                axisTick: {            // 坐标轴小标记
                    length:12,        // 属性length控制线长
                    lineStyle: {       // 属性lineStyle控制线条样式
                        color: 'auto'
                    }
                },
                splitLine: {           // 分隔线
                    length:20,         // 属性length控制线长
                    lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                        color: 'auto'
                    }
                },
                pointer: {
                    width:5
                },
                title: {
                    show:true,
                    //offsetCenter: [0, '-30%'],       // x, y，单位px
                },
                detail: {
                    // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    fontSize:'10',
                    show:false,
                    fontWeight: 'bolder'
                },
                data:[{value: 1.5, name: 'x1000 r/min'}]
            },
            centerSerie : {
                name: '速度',
                type: 'gauge',
                z: 3,
                min: 0,
                max: 100,
                splitNumber: 10,
                radius: '80%',
                axisLine: {            // 坐标轴线
                    lineStyle: {       // 属性lineStyle控制线条样式
                        color: [[0.02, '#44b6ae'],[0.82, '#3598dc'],[1, '#ff4500']],
                        width: 5
                    }
                },
                axisTick: {            // 坐标轴小标记
                    length: 15,        // 属性length控制线长
                    lineStyle: {       // 属性lineStyle控制线条样式
                        color: 'auto'
                    }
                },
                splitLine: {           // 分隔线
                    length: 20,         // 属性length控制线长
                    lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                        color: 'auto'
                    }
                },
                axisLabel: {
                    backgroundColor: 'auto',
                    borderRadius: 2,
                    color: '#eee',
                    padding: 3,
                    textShadowBlur: 2,
                    textShadowOffsetX: 1,
                    textShadowOffsetY: 1,
                    textShadowColor: '#222'
                },
                title : {
                    // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    fontWeight: 'bolder',
                    fontSize: 20,
                    fontStyle: 'italic'
                },
                detail : {
                    // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    formatter: function (value) {
                        value = (value + '').split('.');
                        value.length < 2 && (value.push('00'));
                        return ('00' + value[0]).slice(-2)
                            + '.' + (value[1] + '00').slice(0, 2);
                    },
                    fontWeight: 'bolder',
                    borderRadius: 3,
                    backgroundColor: '#444',
                    borderColor: '#aaa',
                    shadowBlur: 5,
                    shadowColor: '#333',
                    shadowOffsetX: 0,
                    shadowOffsetY: 3,
                    borderWidth: 2,
                    textBorderColor: '#000',
                    textBorderWidth: 2,
                    textShadowBlur: 2,
                    textShadowColor: '#fff',
                    textShadowOffsetX: 0,
                    textShadowOffsetY: 0,
                    fontFamily: 'Arial',
                    width: 50,
                    color: '#eee',
                    fontSize:'10',
                    show:false,
                    rich: {}
                },
                data:[{value: 40, name: 'km/h'}]
            },
            rightSerie : {
                name: '转速',
                type: 'gauge',
                center: ['75%', '50%'],    // 默认全局居中
                radius: '60%',
                min:0,
                max:100,
                startAngle: 150,
                endAngle: -150,
                splitNumber:10,
                axisLine: {            // 坐标轴线
                    lineStyle: {       // 属性lineStyle控制线条样式
                        width: 5
                    }
                },
                axisTick: {            // 坐标轴小标记
                    length:12,        // 属性length控制线长
                    lineStyle: {       // 属性lineStyle控制线条样式
                        color: 'auto'
                    }
                },
                splitLine: {           // 分隔线
                    length:20,         // 属性length控制线长
                    lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                        color: 'auto'
                    }
                },
                pointer: {
                    width:5
                },
                title: {
                    show:true,
                    //offsetCenter: [0, '-30%'],       // x, y，单位px
                },
                detail: {
                    // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    fontSize:10,
                    show:false,
                    fontWeight: 'bolder'
                },
                data:[{value: 1.5, name: 'x1000 r/min'}]
            },
        },
        _initGridInfoByEcharts : function($modalBody,terminalData,position){
            var that = this;
            var $gauge = $modalBody.find('#gauge');
            // var $memGauge = $modalBody.find('#memGauge');
            // var $diskGauge = $modalBody.find('#diskGauge');
            if(!terminalData.otherItems){
                return;
            }
            var data = JSON.parse(terminalData.otherItems);

            //获取echart的默认配置信息
            var echartSetting = $.extend(true,{},that.echartSetting);
            //生成echart对象
            var myChart = echarts.init($gauge[0]);

            //获取CUP信息
            var usedValue = data.cup?data.cup.used || 0 : 0;
            var totalValue = data.cup?data.cup.total || 1 : 1;
            //计算百分比
            var value = usedValue / totalValue * 100;
            if(value){
                value = value.toFixed(2);
            }
            //生成仪表盘,生成在中间
            var serieData = $.extend(true,{},that.echartSetting.centerSerie);
            serieData.name = "CUP使用情况";
            serieData.data = [{
                value : value,
                name : "CUP"
            }]
            echartSetting.series.push(serieData);
            
            //获取内存信息
            usedValue = data.mem?data.mem.used || 0 : 0;
            totalValue = data.mem?data.mem.total || 1 : 1;
            //计算百分比
            value = usedValue / totalValue * 100;
            if(value){
                value = value.toFixed(2);
            }
            //生成仪表盘,生成在左边
            serieData = $.extend(true,{},that.echartSetting.leftSerie);
            serieData.name = "内存使用情况";

            serieData.data = [{
                value : value,
                name : "内存"
            }]
            if(position && position[0]){
                //设置左边盘的位置
                serieData.center[0] = position[0];
            }
            echartSetting.series.push(serieData);

            //获取磁盘信息
            usedValue = data.disk?data.disk.used || 0 : 0;
            totalValue = data.disk?data.disk.total || 1 : 1;
            //计算百分比
            value = usedValue / totalValue * 100;
            if(value){
                value = value.toFixed(2);
            }
            //生成仪表盘,生成在右边
            serieData = $.extend(true,{},that.echartSetting.rightSerie);
            serieData.name = "磁盘使用情况";
            serieData.data = [{
                value : value,
                name : "磁盘"
            }]
            if(position && position[1]){
                //设置右边盘的位置
                serieData.center[0] = position[1];
            }
            echartSetting.series.push(serieData);

            myChart.setOption(echartSetting, true);

            return myChart;
        }
    }
    this.pageManage._init();
}