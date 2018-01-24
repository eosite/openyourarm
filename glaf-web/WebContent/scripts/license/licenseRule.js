$(function() {
    if(!licenseId){
        alert("无licenseId");
        return;
    }

    var systemZtreeObj_ = null; //系统树
    var modelZtreeObj_ = null; //模块树
    var tableZtreeObj_ = null; //表格树
    var systemZtreeObj_checked_datas = []; //系统树的已勾选数据
    var tableZtreeObj_checked_datas = []; //表格树的已勾选数据
    var tableZtreeObj_ClickNode = null; //点击格
    var utableTreeUrl = contextPath + "/rs/isdp/cellUTableTree/getUtableTreeByTableTypeInit?1=1"; //表格树
    var cellUrl = contextPath + "/rs/isdp/treeDot/getTreeDotInit?1=1"; // getTreeDotInit
    // var $checkedSystemGrid = $("#checkedSystemGrid");   //勾选的系统信息
    var $selectedSystemGrid = $("#selectedSystemGrid"); //已选择的系统信息
    // var $checkedTableGrid = $("#checkedTableGrid"); //勾选的表格信息
    var $selectedTableGrid = $("#selectedTableGrid"); //已选的表格信息


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

    //查询出对应的规则信息
    $.ajax({
        url: contextPath + '/mx/form/license/getById',
        type: "post",
        dataType: "json",
        async: false,
        data:{id:licenseId},
        success: function(ret) {
            if(typeof ret == 'string'){
                ret = JSON.parse(ret);
            }

            if (ret && ret.statusCode == '200') {
                if(ret.data && ret.data.ruleJson){
                    var licenseObj = ret.data.ruleJson;
                    if(typeof licenseObj == 'string'){
                        licenseObj = JSON.parse(licenseObj);
                    }

                    systemZtreeObj_checked_datas = licenseObj.systems;
                    tableZtreeObj_checked_datas = licenseObj.tables;

                    $("#macCode").val(licenseObj.macCode);
                    
                    $("#maxUserNum").val(licenseObj.maxUserNum); //最大用户数量
                   

                    $("#startTime").datetimepickerExt("setValue",licenseObj.startTime);
                    $("#endTime").datetimepickerExt("setValue",licenseObj.endTime);
                }

            }
        },
        error: function() {
            alert("异常错误,请稍后再试.");
        }
    })


    function resizePanel() {
        $(".licensePortlet").innerHeight($(".mainBody").height() - $(".baseLicensePortlet").outerHeight() - 6);

        $(".resizePortletBody").each(function(i, item) {
            var $item = $(item);
            var $prev = $item.prevAll();
            var prevHeight = 0;
            $prev.each(function(k, ktem) {
                if (!$(ktem).is(":hidden")) {
                    prevHeight += $(ktem).outerHeight();
                }
            })
            $item.outerHeight($item.parent().height() - prevHeight);
        })
        $(".downPanel").each(function(i, item) {
            var $item = $(item);
            var $prev = $item.prevAll();
            var prevHeight = 0;
            $prev.each(function(k, ktem) {
                if (!$(ktem).is(":hidden")) {
                    prevHeight += $(ktem).outerHeight();
                }
            })
            $item.outerHeight($item.parent().height() - prevHeight);
        })

        $(".slimScroll_parent").each(function(i, item) {
            var $item = $(item);
            var $prev = $item.prevAll();
            var prevHeight = 0;
            $prev.each(function(k, ktem) {
                if (!$(ktem).is(":hidden")) {
                    prevHeight += $(ktem).outerHeight();
                }
            })
            $item.outerHeight($item.parent().height() - prevHeight);
        })

        $(".slimScroll_div").slimscroll({
            destroy: "destroy"
        });

        $(".slimScroll_div").each(function(i, item) {
            var $item = $(item);
            $item.slimscroll({
                height: $item.closest(".slimScroll_parent").height(),
                alwaysVisible: true,
                railVisible: true,
                railColor: '#333',
            })
        })

        $(".grid").grid("_resizeGroup");
    }


    window.onresize = function() {
        resizePanel();
    }
    $('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
        resizePanel();
    })



    var selectedTableGrid_setting = {
        datas: tableZtreeObj_checked_datas,
        scrollable: false,
        selectable: "sinagle",
        columns: [{
            title: '表中文名',
            field: 'name',
            width: 80,
            sortable: false,
            'isEditor': false,
            'attributes': {
                'style': 'text-align:center;'
            },
        }, {
            title: '表名',
            field: 'tableName',
            width: 80,
            sortable: false,
            'isEditor': false,
            'attributes': {
                'style': 'text-align:center;'
            },
        }, {
            'command': [{
                'name': 'destroy',
                'text': '删除'
            }]
        }, ],
        events: {
            onClickRow: function(index, row) {
                var getSelectedRows = $selectedTableGrid.grid("getSelectedRows");
                if (getSelectedRows && getSelectedRows.length > 0) {
                    var getSelectedRowData = getSelectedRows[0];

                    var templateHtml = ArtTemplateDataUtils.convertOne(getSelectedRowData.competence, $("#tableRuleFormEditTemplate").html());
                    $(".selectedRuleTable").empty();
                    var $templateHtml = $(templateHtml);
                    $(".selectedRuleTable").append($templateHtml);

                    $templateHtml.find('input[type="radio"]').iCheck({
                        radioClass: 'iradio_minimal-blue',
                    });
                    $templateHtml.find('input[type="checkbox"]').iCheck({
                        checkboxClass: 'icheckbox_square-blue',
                    });
                }
            },
            onDeletedEvent: function(data){
                var nodeData = tableZtreeObj_.getNodeByParam("tableName", data.tableName);

                if(nodeData){
                    nodeData.chkDisabled = false;
                    systemZtreeObj_.refresh();    
                    $.each(tableZtreeObj_checked_datas,function(i,item){
                        if(item.tableName == data.tableName){
                            tableZtreeObj_checked_datas.splice(i,1);
                            return false;
                        }
                    })
                }
            }
        }
    }

    function traverseZtree(datas, callback) {
        if (!datas) {
            return;
        }
        $.each(datas, function(i, item) {
            if ($.isFunction(callback)) {
                callback(item);
            }
            traverseZtree(item.children, callback);
        })
    }

    var tableZtreeSetting = {
        view: {
            fontCss: function(treeId, node) {
                return node.font ? node.font : {};
            }
        },
        async: {
            enable: true,
            url: utableTreeUrl,
            autoParam: ["indexId", "nlevel=level"],
            dataFilter: function(treeId, parentNode, responseData) {

                var datas = [];


                // if (datas && datas.length) {
                //     $.each(datas, function(i, node) {
                //         if (node.ds == "F") { //数据集目录
                //             node.icon = contextPath + '/images/folder-horizontal.png';
                //             node.iconOpen = contextPath + '/images/folder-horizontal-open.png';
                //             node.iconClose = contextPath + '/images/folder-horizontal.png';
                //         } else if (node.ds == 'T') { //数据集
                //             node.icon = contextPath + '/images/blue-documents-stack.png';
                //             node.tableName = node.indexId;
                //         }
                //         node.name = node.indexName + "(" + node.tableName + ")";
                //     });

                //     // //遍历已勾选数据，判断是否已经配置过的信息，配置过的不可勾选。
                //     // if (tableZtreeObj_checked_datas && datas) {
                //     //     traverseZtree(datas, function(ktem) {
                //     //         $.each(tableZtreeObj_checked_datas, function(b, btem) {
                //     //             if (btem.tableName == ktem.tableName) {
                //     //                 ktem.chkDisabled = true;
                //     //             }
                //     //         });
                //     //     })
                //     // }
                // }

                if (responseData && responseData.length) {
                    var tableName;
                    $.each(responseData, function(i, v) {
                        if ((tableName = v.tableName) && tableName.toLowerCase().indexOf("log_") == 0) {
                            return false;
                        }
                        if (!tableName) {
                            v.icon = contextPath + '/images/blue-folder-horizontal.png';
                            v.iconOpen = contextPath + '/images/blue-folder-horizontal-open.png';
                            v.iconClose = contextPath + '/images/blue-folder-horizontal.png';
                        }
                        datas.push(v);

                    });

                    //遍历已勾选数据，判断是否已经配置过的信息，配置过的不可勾选。
                    if (responseData) {
                        traverseZtree(responseData, function(ktem) {
                            if (!ktem.tableName) {
                                return;
                            }
                            //修改名称为中文名称(英文名称)
                            ktem.indexName = ktem.name + "(" + ktem.tableName + ")";
                            if (tableZtreeObj_checked_datas) {
                                $.each(tableZtreeObj_checked_datas, function(b, btem) {
                                    if (btem.tableName == ktem.tableName) {
                                        ktem.chkDisabled = true;
                                    }
                                });
                            }
                        })
                    }
                }
                return datas;
            }
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "indexId",
                pIdKey: "parentId"
            },
            key: {
                name: "indexName",
                title: 'tableName'
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
        callback: {
            onClick: function(event, treeId, treeNode) {

            },

            onCheck: function(event, treeId, treeNode) {
                var nodes = tableZtreeObj_.getCheckedNodes(true);
                for (var i = 0; i < nodes.length;) {
                    var item = nodes[i];
                    if (item.isParent) {
                        nodes.splice(i, 1);
                    } else {
                        i++;
                    }
                }
                // tableZtreeObj_checked_datas = nodes;
                // $checkedTableGrid.grid("_loadDatas",nodes);
            }
        }
    }

    function setIcon(treeNodes) {
        $.each(treeNodes, function(i, item) {
            if (item.children) {
                item.isParent = true;
                item.icon = contextPath + '/images/brick.png';
            }
            if (item.children) {
                setIcon(item.children);
            }
        })
    }

    function initSystemPanel() {

        //模块树
        systemZtreeObj_ = $.fn.zTree.init($("#systemZtree"), {
            async: {
                enable: true,
                url: "/glaf/mx/form/license/getApplicationTreeData",
                dataFilter: function(treeId, parentNode, responseData) {
                    if (!responseData) return null;

                    setIcon(responseData);
                    for (var i = 0, l = responseData.length; i < l; i++) {
                        responseData[i].name = responseData[i].name.replace(/\.n/g, '.');
                        // responseData[i].icon = contextPath + '/images/brick.png';
                        //if(responseData[i].iconCls=='icon-user'){
                        // responseData[i].icon="/glaf/images/user.gif";
                        //}
                    }

                    //遍历已勾选数据，判断是否已经配置过的信息，配置过的不可勾选。
                    if (responseData) {
                        traverseZtree(responseData, function(ktem) {
                            $.each(systemZtreeObj_checked_datas, function(b, btem) {
                                if (btem.id == ktem.id) {
                                    ktem.chkDisabled = true;
                                }
                            });
                        })
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
            callback: {
                onClick: function(event, treeId, treeNode) {

                },

                onCheck: function(event, treeId, treeNode) {
                    var nodes = systemZtreeObj_.getCheckedNodes(true);
                    for (var i = 0; i < nodes.length;) {
                        var item = nodes[i];
                        if (item.isParent) {
                            nodes.splice(i, 1);
                        } else {
                            i++;
                        }
                    }
                    // systemZtreeObj_checked_datas = nodes;
                    // $checkedSystemGrid.grid("_loadDatas",nodes);
                }
            }
        });

        $selectedSystemGrid.grid({
            datas: systemZtreeObj_checked_datas,
            scrollable: false,
            selectable: "sinagle",
            columns: [{
                title: '系统名',
                field: 'name',
                width: 80,
                sortable: false,
                'isEditor': false,
                'attributes': {
                    'style': 'text-align:center;'
                },
            }, {
                title: '编码',
                field: 'id',
                width: 80,
                sortable: false,
                'isEditor': false,
                'attributes': {
                    'style': 'text-align:center;'
                },
            }, {
                'command': [{
                        'name': 'destroy',
                        'text': '删除'
                    },
                    // {
                    //     'name': 'showRuleBtn',
                    //     'text': '规则信息'
                    // }
                ]
            }],
            events: {
                onClickRow: function(index, row) {
                    var getSelectedRows = $selectedSystemGrid.grid("getSelectedRows");
                    if (getSelectedRows && getSelectedRows.length > 0) {
                        var getSelectedRowData = getSelectedRows[0];

                        var templateHtml = ArtTemplateDataUtils.convertOne(getSelectedRowData.competence, $("#systemRuleFormEditTemplate").html());
                        $(".selectedRuleSystem").empty();
                        $(".selectedRuleSystem").append(templateHtml);
                    }
                },
                onDeletedEvent: function(data){
                    var nodeData = systemZtreeObj_.getNodeByParam("id", data.id);

                    if(nodeData){
                        nodeData.chkDisabled = false;
                        systemZtreeObj_.refresh();    
                        $.each(systemZtreeObj_checked_datas,function(i,item){
                            if(item.id == data.id){
                                systemZtreeObj_checked_datas.splice(i,1);
                                return false;
                            }
                        })
                    }
                }
            }
        });

        $("body").on("click", ".changeSystemRuleBtn", function(i, item) {
            var getSelectedRows = $selectedSystemGrid.grid("getSelectedRows");
            if (getSelectedRows && getSelectedRows.length > 0) {
                getSelectedRows[0].competence = getSelectedRows[0].competence || {};
                getSelectedRows[0].competence.access = $(".selectedRuleSystem").find("input[name='access']:checked").val();
                alert("修改成功");
            }
        })



        $selectedSystemGrid.on("commandclick", "a.btn[atype='showRuleBtn']", function(event) {
            var $this = $(this);
            var getSelectedRows = $selectedSystemGrid.grid("getSelectedRows");
            if (getSelectedRows && getSelectedRows.length > 0) {
                var getSelectedRow = getSelectedRows[0];

                BootstrapDialog.show({
                    title: '系统"' + getSelectedRow.name + '"规则信息',
                    draggable: true,
                    message: "",
                    closeByBackdrop: true,
                    css: {
                        width: '600px',
                        height: '400px'
                    },
                    onhide: function(dialog) {},
                    buttons: [{
                        id: 'btn-ok',
                        icon: 'glyphicon glyphicon-remove',
                        label: '取消',
                        cssClass: 'yellow',
                        autospin: false,
                        action: function(dialogRef) {
                            dialogRef.close();
                        }
                    }, {
                        id: 'btn-ok',
                        icon: 'glyphicon glyphicon-check',
                        label: '修改',
                        cssClass: 'btn-primary',
                        autospin: false,
                        action: function(dialogRef) {
                            var $body = dialogRef.getModalBody().find(">div");

                            // $body.find("input[name='access']").attr("name","access1");

                            getSelectedRow.access = $body.find("input[name='access']:checked").val();

                            // $selectedTableGrid.grid("refreshRow",$this,getSelectedRow);

                            dialogRef.close();
                        }
                    }],
                    onshow: function(dialog) {
                        var $body = dialog.getModalBody().find(">div");
                        $body.append($("#systemRuleForm_template").html());

                        $body.find('input[type="radio"]').iCheck({
                            radioClass: 'iradio_minimal-blue',
                        });
                        $body.find('input[type="checkbox"]').iCheck({
                            checkboxClass: 'icheckbox_square-blue',
                        });

                        $body.find("ins").css("position", "");

                        $body.find("input[name='access']").iCheck('uncheck');
                        if (getSelectedRow.access == '1') {
                            $body.find("input[name='access'][value='1']").iCheck('check');
                        } else {
                            $body.find("input[name='access'][value='0']").iCheck('check');
                        }
                    },


                });
            }
        });

        $("#selectSystemBtn").click(function(event) {
            //获取当前勾选的节点信息
            var checkDatas = systemZtreeObj_.getCheckedNodes(true);
            if (!checkDatas) {
                return;
            }

            systemZtreeObj_checked_datas = systemZtreeObj_checked_datas || [];
            var access = $("#systemRuleForm").find("input[name='access']:checked").val();

            $.each(checkDatas, function(i, item) {
                item.competence = item.competence || {};
                item.competence.access = access;
                item.chkDisabled = true;
                systemZtreeObj_checked_datas.push(item);
            })

            systemZtreeObj_.refresh();
            // $checkedSystemGrid.grid("_loadDatas",[]);
            $selectedSystemGrid.grid("_loadDatas", systemZtreeObj_checked_datas);
        })
    }

    function initModelPanel() {
        modelZtreeObj_ = $.fn.zTree.init($("#modelZtree"), {
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
        }, [{
            'id': 1,
            'parentId': 0,
            'name': "父节点 1",
            'open': true
        }, {
            'id': 11,
            'parentId': 1,
            'name': "叶子节点 1-1"
        }, {
            'id': 12,
            'parentId': 1,
            'name': "叶子节点 1-2"
        }, {
            'id': 13,
            'parentId': 1,
            'name': "叶子节点 1-3"
        }, {
            'id': 2,
            'parentId': 0,
            'name': "父节点 2",
            'open': true
        }, {
            'id': 21,
            'parentId': 2,
            'name': "叶子节点 2-1"
        }, {
            'id': 22,
            'parentId': 2,
            'name': "叶子节点 2-2"
        }, {
            'id': 23,
            'parentId': 2,
            'name': "叶子节点 2-3"
        }, {
            'id': 3,
            'parentId': 0,
            'name': "父节点 3",
            'open': true
        }, {
            'id': 31,
            'parentId': 3,
            'name': "叶子节点 3-1"
        }, {
            'id': 32,
            'parentId': 3,
            'name': "叶子节点 3-2"
        }, {
            'id': 33,
            'parentId': 3,
            'name': "叶子节点 3-3"
        }]);
    }

    /**
     * [重新初始化表格树]
     * @param  {[type]} tableType [表格类型]
     * @param  {[type]} ds        [标段]
     * @return {[type]}           [description]
     */
    function initTableZtree(tableType, ds) {
        var $zTree = $("#tableZtree");
        tableZtreeObj_.destroy();

        if (tableType == 1) {
            tableZtreeSetting.async.url = utableTreeUrl + "&type=1&tableType=2&systemName=" + ds;
            $.fn.zTree.init($zTree, tableZtreeSetting);
        } else if (tableType == 2) {
            tableZtreeSetting.async.url = cellUrl + '&systemName=' + ds;
            $.fn.zTree.init($zTree, tableZtreeSetting);
        } else if (tableType == 3) {
            tableZtreeSetting.async.url = utableTreeUrl + "&type=1&tableType=12&systemName=" + ds;
            $.fn.zTree.init($zTree, tableZtreeSetting);
        } else if (tableType == 4) {
            tableZtreeSetting.async.url = utableTreeUrl + "&type=4&tableType=12&systemName=" + ds;
            $.fn.zTree.init($zTree, tableZtreeSetting, [{
                'indexName': '系统内置表',
                'parentId': -1,
                'indexId': 1,
                id: '1|',
                isParent: true
            }]);
        } else if (tableType == 5) {
            $.fn.zTree.init($zTree, tableZtreeSetting, [{
                'indexName': '自动生成表',
                'parentId': -1,
                'indexId': -1,
                id: '1|'
            }]);
        }
    }

    var getDataSets = (function() {
        return function(parentNode) {
            if (!parentNode) {
                return window.dataSetTrees;
            } else {
                return [];
            }
        };
    })();

    function initTablePanel() {
        tableZtreeObj_ = $.fn.zTree.init($("#tableZtree"), tableZtreeSetting);

        //初始化标段信息
        var $select_biaoduan = $("#select-biaoduan");
        $.ajax({
            url: contextPath + '/rs/isdp/global/databaseJson',
            type: "post",
            dataType: "json",
            async: true,
            success: function(ret) {
                if (ret) {
                    $.each(ret, function(i, item) {
                        item.value = item.code;
                        item.id = item.code;
                    })
                    $select_biaoduan.select2({
                        minimumResultsForSearch: Infinity,
                        data: ret
                    });
                }
            },
            error: function() {
                alert("异常错误,请稍后再试.");
            }
        })

        //初始化表格类型信息
        var $select_type = $("#select-type");
        $.ajax({
            url: contextPath + '/mx/form/defined/getDictByCode/?code=tableType',
            type: "post",
            dataType: "json",
            async: true,
            success: function(ret) {
                if (ret) {
                    $.each(ret, function(i, item) {
                        item.value = item.code;
                        item.id = item.code;
                        item.text = item.name;
                    })
                    $select_type.select2({
                        minimumResultsForSearch: Infinity,
                        data: ret
                    });
                }
            },
            error: function() {
                alert("异常错误,请稍后再试.");
            }
        })

        $select_biaoduan.change(function(event) {
            var tableType = $select_type.select2('data')[0].value;
            initTableZtree(tableType, $select_biaoduan.select2('data')[0].value);
        })
        $select_type.change(function(event) {
            var biaoduan = $select_biaoduan.select2('data')[0].value;
            initTableZtree($select_type.select2('data')[0].value, biaoduan);
        })

        $("#research_name_Btn").click(function(event) {
            searchProject();
        })

        // $checkedTableGrid.grid(checkedTableGrid_setting);

        $("#selectTableBtn").click(function(event) {
            var checkDatas = tableZtreeObj_.getCheckedNodes(true);
            if (!checkDatas) {
                return;
            }

            tableZtreeObj_checked_datas = tableZtreeObj_checked_datas || [];

            var createAble = $("#createAble").prop("checked");
            var retrieveAble = $("#retrieveAble").prop("checked");
            var updateAble = $("#updateAble").prop("checked");
            var deleteAble = $("#deleteAble").prop("checked");
            var maxRowNum = $("#maxRowNum").val();
            $.each(checkDatas, function(i, item) {
                if (item.tableName) {
                    item.competence = item.competence || {};
                    item.competence.createAble = createAble; //创表权限
                    item.competence.retrieveAble = retrieveAble; //查询表权限
                    item.competence.updateAble = updateAble; //修改表权限
                    item.competence.deleteAble = deleteAble; //删除表权限
                    item.competence.maxRowNum = maxRowNum; //可允许插入最大数据量
                    item.chkDisabled = true;
                    tableZtreeObj_checked_datas.push(item);
                }
            })

            tableZtreeObj_.refresh();
            // $checkedSystemGrid.grid("_loadDatas",[]);
            $selectedTableGrid.grid("_loadDatas", tableZtreeObj_checked_datas);
        })
        $selectedTableGrid.grid(selectedTableGrid_setting);

        $("body").on("click", ".changeTableRuleBtn", function(i, item) {
            var getSelectedRows = $selectedTableGrid.grid("getSelectedRows");
            if (getSelectedRows && getSelectedRows.length > 0) {
                var getSelectedRow = getSelectedRows[0];
                var $selectedRuleTable = $(".selectedRuleTable");
                getSelectedRow.createAble = $selectedRuleTable.find("input[name='createAble']").prop("checked");
                getSelectedRow.retrieveAble = $selectedRuleTable.find("input[name='retrieveAble']").prop("checked");
                getSelectedRow.updateAble = $selectedRuleTable.find("input[name='updateAble']").prop("checked");
                getSelectedRow.deleteAble = $selectedRuleTable.find("input[name='deleteAble']").prop("checked");
                getSelectedRow.maxRowNum = $selectedRuleTable.find("input[name='maxRowNum']").val();
                alert("修改成功");
            }
        })

        $selectedTableGrid.on("commandclick", "a.btn[atype='showRuleBtn']", function(event) {
            var $this = $(this);
            var getSelectedRows = $selectedTableGrid.grid("getSelectedRows");
            if (getSelectedRows && getSelectedRows.length > 0) {
                var getSelectedRow = getSelectedRows[0];
                BootstrapDialog.show({
                    title: '表"' + getSelectedRow.name + '"规则信息',
                    draggable: true,
                    message: "",
                    closeByBackdrop: true,
                    css: {
                        width: '600px',
                        height: '400px'
                    },
                    onhide: function(dialog) {},
                    buttons: [{
                        id: 'btn-ok',
                        icon: 'glyphicon glyphicon-remove',
                        label: '取消',
                        cssClass: 'yellow',
                        autospin: false,
                        action: function(dialogRef) {
                            dialogRef.close();
                        }
                    }, {
                        id: 'btn-ok',
                        icon: 'glyphicon glyphicon-check',
                        label: '修改',
                        cssClass: 'btn-primary',
                        autospin: false,
                        action: function(dialogRef) {
                            var $body = dialogRef.getModalBody().find(">div");

                            getSelectedRow.createAble = $body.find("input[name='createAble']").prop("checked");
                            getSelectedRow.retrieveAble = $body.find("input[name='retrieveAble']").prop("checked");
                            getSelectedRow.updateAble = $body.find("input[name='updateAble']").prop("checked");
                            getSelectedRow.deleteAble = $body.find("input[name='deleteAble']").prop("checked");
                            getSelectedRow.maxRowNum = $body.find("input[name='maxRowNum']").val();

                            // $selectedTableGrid.grid("refreshRow",$this,getSelectedRow);

                            dialogRef.close();
                        }
                    }],
                    onshow: function(dialog) {
                        var $body = dialog.getModalBody().find(">div");
                        $body.append($("#tableRuleForm_template").html());

                        $body.find('input[type="radio"]').iCheck({
                            radioClass: 'iradio_minimal-blue',
                        });
                        $body.find('input[type="checkbox"]').iCheck({
                            checkboxClass: 'icheckbox_square-blue',
                        });

                        if (getSelectedRow.createAble) {
                            $body.find("input[name='createAble']").iCheck('check');
                        }
                        if (getSelectedRow.updateAble) {
                            $body.find("input[name='updateAble']").iCheck('check');
                        }
                        if (getSelectedRow.deleteAble) {
                            $body.find("input[name='deleteAble']").iCheck('check');
                        }
                        if (getSelectedRow.retrieveAble) {
                            $body.find("input[name='retrieveAble']").iCheck('check');
                        }
                        $body.find("input[name='maxRowNum']").val(getSelectedRow.maxRowNum);

                    },


                });
            }
        })
    }

    $("#tableRuleForm").append($("#tableRuleForm_template").html());
    $("#systemRuleForm").append($("#systemRuleForm_template").html());

    initSystemPanel(); //初始化系统面板
    initModelPanel(); //初始化模块面板
    initTablePanel(); //初始化表格面板

    $('input[type="radio"]').iCheck({
        radioClass: 'iradio_minimal-blue',
    });
    $('input[type="checkbox"]').iCheck({
        checkboxClass: 'icheckbox_square-blue',
    });

    

    $("#editLicenseRuleBtn").click(function() {

        var $thisBtn = $(this);

        if($thisBtn.attr("disabled")){
            return;
        }

        var selectedSystemGrid_datas = $selectedSystemGrid.grid("getData");
        var selectedTableGrid_datas = $selectedTableGrid.grid("getData");

        var licenseObj = {};

        licenseObj.macCode = $("#macCode").val();
        if (!licenseObj.macCode) {
            alert("请输入MAC码！");
            return;
        }
        licenseObj.maxUserNum = $("#maxUserNum").val(); //最大用户数量
        if (licenseObj.maxUserNum == null || licenseObj.maxUserNum == "") {
            alert("请输入最大用户数量！");
            return;
        }

        licenseObj.startTime = mtDate.formatDatetime($("#startTime").datetimepickerExt("getValue"));
        licenseObj.endTime = mtDate.formatDatetime($("#endTime").datetimepickerExt("getValue"));
        if (!licenseObj.startTime || !licenseObj.endTime) {
            alert("请选择开始和结束时间！");
            return;
        }
        if (licenseObj.startTime > licenseObj.endTime) {
            alert("结束时间必须大于开始时间！");
            return;
        }



        licenseObj.systems = [];
        licenseObj.tables = [];
        $.each(selectedSystemGrid_datas, function(i, item) {
            licenseObj.systems.push({
                name: item.name,
                code: item.code,
                id: item.id,
                attributeObj : {
                    url : item.url,    
                    appId: item.appId,
                    pageId: item.pageId
                },
                competence: item.competence
            })
        });
        $.each(selectedTableGrid_datas, function(i, item) {
            licenseObj.tables.push({
                tableName: item.tableName,
                name: item.name,
                competence: item.competence
            })
        })
        console.log(licenseObj);

        var params = {
            id : licenseId,
            ruleJson : JSON.stringify(licenseObj)
        }

        $thisBtn.attr("disabled");
        $.ajax({
            url: contextPath + '/mx/form/license/saveLicense',
            async: true,
            data: params,
            type : "POST",
            success: function(ret) {
                if(typeof ret == 'string'){
                    ret = JSON.parse(ret);
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


        // var $iframe = $('<iframe id="down-file-iframe" />');
        // var $form = $('<form target="down-file-iframe" method="' + 'post' + '" />');
        // $form.attr('action', contextPath + '/mx/form/license/downloadXml');
        // var $input = $('<input type="hidden" name="licenseObj">');
        // $input.val(JSON.stringify(licenseObj));
        // $form.append($input);
        // $iframe.append($form);
        // $(document.body).append($iframe);
        // $form[0].submit();
        // $iframe.remove();


        //  $.ajax({
        //     url: contextPath + '/mx/form/license/createXml',
        //     type: "post",
        //     contentType: 'application/json',
        //     data: JSON.stringify(licenseObj),
        //     async: false,
        //     success: function(msg) {
        //         if(msg){

        //         }
        //     },
        //     error: function() {
        //         alert("异常错误,请稍后再试.");
        //     }
        // })

        console.log('aaa');
    })

    resizePanel();
});

function searchProject() {
    var searchTextBox = $("#research_name_input"),
        val = searchTextBox.val();
    var f = tableZtreeObj_.expandAll(false),
        tmp = {};

    function updateHighlight(ns, flag) {
        $.each(ns, function(i, node) {
            node.highlight = flag || false;
            if (node.highlight) {
                node.font = {
                    'color': 'red'
                };
            } else {
                // delete node.font;
            }

            tableZtreeObj_.updateNode(node);
        });
    }

    function getNodesLike(value, collection) {
        if (value && (value = $.trim(value))) {
            $.each(["name", "tableName"], function() {
                var ns = tableZtreeObj_.getNodesByParamFuzzy(this, value, null);
                if (ns && ns.length) {
                    $.each(ns, function() {
                        if (!this.isParent && !tmp[this.id]) {
                            if (this.indexName && this.indexName.indexOf(this.tableName) < 0) {
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

    if (!f) {
        setTimeout(function() {
            var ns = searchTextBox.data('nodes');
            if (ns && ns[0]) {
                updateHighlight(ns, false);
            }
            if (val) {
                nodes = [];
                if (val.indexOf(",") > 0) {
                    $.each(val.split(","), function() {
                        getNodesLike(this, nodes);
                    });
                } else {
                    getNodesLike(val, nodes);
                }
                if (nodes && nodes[0]) {
                    $.each(nodes, function(i, node) {
                        if (!node.isParent) {
                            node = node.getParentNode();
                        }
                        tableZtreeObj_.expandNode(node, true, true, false);
                    });
                    updateHighlight(nodes, true);
                    searchTextBox.data('nodes', nodes);
                    searchTextBox.data("n-index", 0);
                    window.setTimeout(function() {
                        window.selectNode();
                    }, 500);
                }
            }

        }, 500);
    }
}

function selectNode() {
    var $o = $("#research_name_input");
    var nodes = $o.data("nodes"),
        index = ($o.data("n-index") || 0) * 1;
    if (nodes && nodes.length) {
        if (index >= nodes.length) {
            index = nodes.length - 1;
            $o.data("n-index", -1);
        }
        var node = nodes[index * 1];
        tableZtreeObj_.selectNode(node);

    }
}