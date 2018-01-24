

var _page_param = {
    themeTreeUrl: contextPath + "/mx/theme/sysThemeTmp/getThemeTree", //主题树
    editLayoutUrl: contextPath + "/mx/theme/sysThemeTmpLayout/saveSysThemeTmpLayout", //主题方案
    editControlUrl: contextPath + "/mx/theme/sysThemeTmpControl/saveSysThemeTmpControl", //主题方案
    componentTypeDataUrl: contextPath + '/rs/form/component/read?categoryLike=bootstrap', //获取控件类型数据url
    blukSaveTmpByteArrayUrl: contextPath + '/mx/theme/sysThemeTmpBytearray/blukSave', //保存样式或其他信息
    editLayoutAreaControlUrl: contextPath + "/mx/theme/sysThemeTmpLayoutAreaControl/saveSysThemeTmpLayoutAreaControl", //布局控件/模板
    queryAllControlUrl : contextPath + "/mx/theme/sysThemeTmp/getAllControl",//查询所有控件信息
}


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


//重计算面板高宽
function ResizePanel() {
    var pageWidth = $("body").width();
    var pageHeight = $("body").height();

    //重计算portlet的高度
    $.each($(".portlet"), function(i, item) {
        var $item = $(item);
        var portletHeight = $item.innerHeight();
        $item.find(">.portlet-body").outerHeight(portletHeight - $item.find(">.portlet-title").height());
    });

    //计算
    var themeMainPanelWidth = $(".themeMainPanel").width();
    var themeTreePanelWidth = $(".themeTreePanel").outerWidth();
    $(".theme_right_panel").outerWidth(themeMainPanelWidth - themeTreePanelWidth);

    var preWidth = 0;
    $(".resizeWidthPanel").siblings().each(function(k,ktem){
        if(!$(ktem).is(":hidden")){
            preWidth += $(ktem).outerWidth();    
        }
    })
    $(".resizeWidthPanel").outerWidth($(".resizeWidthPanel").parent().width() - preWidth);

    $(".dspPanel").each(function(i,item){
        var $item = $(item);
        var $prev = $item.prevAll();
        var prevHeight = 0;
        $prev.each(function(k,ktem){
            if(!$(ktem).is(":hidden")){
                prevHeight += $(ktem).height();    
            }
        })
        $item.outerHeight($item.closest(".theme_right_panel").height() - prevHeight - 10);
    })

    // $(".dspPanel").outerHeight($(".theme_right_panel").height() - $(".toolbar").height() - 13);

    $(".parentPanel").each(function(i,item){
        var $item = $(item);
        $item.find(".contentPanel").outerHeight($(item).height() - $item.find(".titlePanel").outerHeight());
    })

    $(".setting_item_panel").each(function(i, item) {
        var $item = $(item);
        $item.find(".setting_right_panel").outerWidth($(item).width() - $item.find(".setting_left_panel").outerWidth() - 4);
    })

    $(".themeTreeSlimScroll_div").height($(".themeTreePanel").height());

     $(".themeTreeSlimScroll_div").slimscroll({
            destroy: "destroy"
        });

     $(".themeTreeSlimScroll_div").slimscroll({
            height: $(".themeTreeSlimScroll_div").height(),
                alwaysVisible: false,
                railVisible: true,
                railColor: '#333',
        });


    $(".gobalControlType").each(function(i,item){
        var $item = $(item);
        $item.find(".gobalControlItem").css("height","auto");

        var maxHeight = 0;
        $item.find(".gobalControlItem").each(function(k,ktem){
            if(maxHeight < $(ktem).height()){
                maxHeight = $(ktem).height();    
            }
        });

        // $item.find(".gobalControlItem").css("min-height",maxHeight)
        $item.find(".gobalControlItem").height(maxHeight);   
    })

    $(".gobalControlsSlimScroll").slimscroll({
        destroy: "destroy"
    });
    $(".gobalControlsSlimScroll").slimscroll({
        height: $(".gobalControlsSlimScroll").height(),
        alwaysVisible: true,
        railVisible: true,
        railColor: '#333',
    });
    $(".attrContentSlimScroll").slimscroll({
        destroy: "destroy"
    });
    $(".attrContentSlimScroll").slimscroll({
        height: $(".attrContentSlimScroll").height(),
        alwaysVisible: true,
        railVisible: true,
        railColor: '#333',
    });

    $(".mainStylePanel .settingPanel").find(".content").height(
        $(".mainStylePanel").find(".leftPanel").innerHeight() 
        - ($(".mainStylePanel .settingPanel").find(".top").outerHeight()*2)
    );

    $(".slimScrollDivPanel").slimscroll({
        destroy: "destroy"
    });
    $(".slimScrollDivPanel").slimscroll({
        height: $(".slimScrollDivPanel").height(),
        alwaysVisible: false,
        railVisible: true,
        railColor: '#333',
        size: '5px',
    });
}



window.onresize = function() {
    ResizePanel();
}

function TraversalTreeData(datas,callback){
    if(!datas){
        return;
    }
    $.each(datas,function(i,item){
        if($.isFunction(callback)){
            callback(item);
        }
        TraversalTreeData(item.children,callback);
    })
}

var _leftPanelFunc = {
    _setIcon : function(data){
        if(data.treeType == 'theme'){
           //主题的图标 
           data.icon = contextPath + '/images/theme.png';
        }else if(data.treeType == 'layoutPanel'){
            //布局
            data.icon = contextPath + '/images/layout_edit.png';
            
        }else if(data.treeType == 'componentPanel'){
            //控件
            data.icon = contextPath + '/images/component_yellow.png';
        }else if(data.treeType == 'modelPanel'){
            //模板
            data.icon = contextPath + '/images/component_red.png';
        }else if(data.treeType == 'layout'){
            //layout（布局）
            data.icon = contextPath + '/images/layout_content.png';
        }else if(data.treeType == 'layoutareapanel'){
            //layoutareapanel（布局面板）
            data.icon = contextPath + '/images/application_tile_vertical.png';
        }else if(data.treeType == 'layoutarea'){
            //layoutarea（区域）
            data.icon = contextPath + '/images/application_xp.png';
        }else if(data.treeType == 'layoutareacontrol'){
            //layoutareacontrol（区域中控件）
            data.icon = contextPath + '/images/asterisk_orange.png';
        }else if(data.treeType == 'control'){
            //control（控件）
            
            data.icon = contextPath + '/images/asterisk_orange.png';
        }else if(data.treeType == 'controlType'){
            //控件类型
            data.icon = contextPath + '/images/application_tile_vertical.png';
        }else if(data.treeType == 'layoutareacontrolpanel'){
            //布局区域控件
            data.icon = contextPath + '/images/component_yellow.png';
        }


    },
    /**
     * 判断是否为容器
     * @param  {[type]}  data [description]
     * @return {Boolean}      [description]
     */
    _isContainer : function(data){
        var that = this;
        if(data.containerFlag){
            return true;
        }else{
            return false;
        }
        //判断是否可以添加子控件,即控件是否是容器.
        if(data.compType.indexOf("col") > -1){
            return true;
        }
        var $comp = $($("#" + data.compType).html());
        if($comp[0] && $comp.find(".containerComp")[0]){
            return true;
        }

        $comp = $("#"+data.treeType + data.id);
        if($comp[0] && $comp.find(".containerComp")[0]){
            return true;
        }

        if(data.cmpTypeParentId){
            var flag = false;
            $.each(that.componentTypeParentData,function(i,item){
                if(item.id == data.cmpTypeParentId){
                    if(item.dataRole == 'container'){
                        flag = true;
                        return false;
                    }
                }
            })
            return flag;
        }

         var parentNodeData = data.getParentNode();
        if(parentNodeData.cmpTypeParentId){
            var flag = false;
            $.each(that.componentTypeParentData,function(i,item){
                if(item.id == parentNodeData.cmpTypeParentId){
                    if(item.dataRole == 'container'){
                        flag = true;
                        return false;
                    }
                }
            })
            return flag;
        }
        
        return false;
    },
    _init_: function(themeId) {
        var that = this;
        that.themeId = themeId;
        that.$target = $(".theme_panel");
        that.target = that.$target[0];
        that.$themeZtree = $("#themeTree"); //主题树
        that.$attrPanel = $("#attrPanel"); //属性面板
        that.attr_toolbox = new $.Attr_toolbox(); //属性工具栏

        that._getComponentTypeData();
        that._loadThemeTree();
        ResizePanel();
    },
    /**
     * 获取控件类型数据
     * @return {[type]} [description]
     */
    _getComponentTypeData: function() {
        var that = this;
        $.ajax({
            url: _page_param.componentTypeDataUrl,
            async: false,
            type: 'post',
            dataType: 'json',
            success: function(datas) {
                var componentTypeData = [];
                var componentTypeParentData = [];
                $.each(datas,function(i,item){
                    if(item.parentId){
                        componentTypeData.push(item);
                    }else{
                        componentTypeParentData.push(item);
                    }
                })
                that.componentTypeData = componentTypeData;
                that.componentTypeParentData = componentTypeParentData;
                that.componentTypeAllData = datas;
            }
        });
    },
    ztreeSetting: {
        async: {
            enable: true,
            url: _page_param.themeTreeUrl + "?themeId=" + _themeId,
            autoParam: ["themeId", "treeType"],
            dataFilter: function(treeId, parentNode, responseData) {
                var componentTypeData = _leftPanelFunc.componentTypeData;
                $.each(responseData.rows, function(i, item) {
                    if (item.treeType == "controlType") {
                        $.each(componentTypeData, function(k, ktem) {
                            if (item.compType == ktem.dataRole) {
                                item.name = ktem.name;
                                item.cmpId = ktem.id;
                                item.cmpTypeParentId = ktem.parentId;
                                return false;
                            }
                        })
                    }
                    _leftPanelFunc._setIcon(item);
                    var callback = function(data){
                        _leftPanelFunc._setIcon(data);
                    }
                    if(item.treeType == "layout"){
                        TraversalTreeData(item.children,function(data){
                            callback(data);
                            if(data.treeType && data.treeType == 'layoutareacontrol'){
                                $.each(componentTypeData, function(k, ktem) {
                                    if (data.compType == ktem.dataRole) {
                                        data.name += "(" +ktem.name +")";
                                        data.cmpId = ktem.id;
                                        data.cmpTypeParentId = ktem.parentId;
                                        return false;
                                    }
                                })
                            }
                        })
                    }else{
                       TraversalTreeData(item.children, callback);
                    }
                })
                return responseData.rows;
            }
        },
        callback: {
            onClick : function(event, treeId, treeNode){
                if(treeNode.treeType == 'theme'){
                    _settingPanelFunc._loadMainStyle(treeNode);
                }
                if(treeNode.treeType == 'componentPanel' || treeNode.parentTreeType == 'componentPanel'){
                    // _settingPanelFunc._loadControl(cmp_panel_data.children);
                }
                if(treeNode.treeType == 'componentPanel'){
                    _settingPanelFunc._loadControlPanelStyle(treeNode);
                }

            },
            onRightClick: function(event, treeId, treeNode) {
            },
            onAsyncSuccess: function(event, treeId, treeNode, msg) {
                if (!treeNode) {
                    var cmp_panel_data = _leftPanelFunc.themeTreeObj.getNodesByParam("treeType", "theme")[0];
                    _settingPanelFunc._loadMainStyle(cmp_panel_data);
                }
                if(_settingPanelFunc.onTreeLoadedFunc && $.isFunction(_settingPanelFunc.onTreeLoadedFunc)){
                    _settingPanelFunc.onTreeLoadedFunc();
                    _settingPanelFunc.onTreeLoadedFunc = null;
                }
                _leftPanelFunc.themeTreeObj.expandAll(true);
            }
        }
    },
    //加载主题树
    _loadThemeTree: function() {
        var that = this;
        // var zNodes =[{"name":"col-md-12_1498467796853","id":"col-md-12_1498467796853","pId":"col-md-12_1498467796853"},{"name":"sss","id":"bootstrap_tabs_1498467803675","pId":"col-md-12_1498467796853"},{"name":"222","id":"mtbutton_1498468771357","pId":"mtbutton_1498468771357"}];
        that.themeTreeObj = $.fn.zTree.init(that.$themeZtree, that.ztreeSetting);
    },
    /**
     * 打开编辑控件的模态框
     * @param  {[type]}   data     [description]
     * @param  {Function} callback [description]
     * @return {[type]}            [description]
     */
    _openEditControlMenu: function(title,data,scallback) {
        var that = this;
        var scallback = scallback;
        //新增主题
        var dialog = BootstrapDialog.show({
            title: title || "控件编辑",
            draggable: false,

            css: {
                width: '800px',
                height: '500px'
            },
            onshown: function(dialog) {
                var $modalBody = dialog.getModalBody();
                if (!data) {
                    data = data || {};
                    data.themeTmpId = that.themeId;
                }
                data.componentTypeData = that.componentTypeData;
                if (that.rightClickNode && that.rightClickNode.treeType == 'controlType') {
                    //如果右击节点为控件类型节点，新增控件时，默认选中
                    data.compType = that.rightClickNode.compType;
                }
                data.nowdate = new Date().getTime();

                var templateHtml = ArtTemplateDataUtils.convertOne(data, $("#edit_control_template").html());
                $modalBody.append(templateHtml);

                that._bindDailogEvent($modalBody, _page_param.editControlUrl, 'control',scallback);
            }
        });

        dialog.getModalHeader().css({
            padding: '4px 4px',
        });
        that.dialog = dialog;
    },
    _bindDailogEvent: function($modalBody, saveUrl, type, scallback) {
        var that = this;
        var scallback = scallback;
        
        //缩略图修改时，实现图片预览
        $modalBody.find("#thumbnail_upload").change(function(e) {
            var file = this.files[0];
            //实现文件预览功能
            var reader = new FileReader();
            var $theme_thumbnail_show = $modalBody.find("#theme_thumbnail_show");
            reader.onloadend = function() {
                $theme_thumbnail_show.attr("src", reader.result).show();
            }
            if (file) {
                reader.readAsDataURL(file);
            }
        });
        $modalBody.find(".theme_thumbnail_panel").hover(function(e) {
            $(this).find("a").show();
        }, function(e) {
            $(this).find("a").hide();
        });

        var saveThemeValidator = $modalBody.find("form").validate(validate_setting);
        $modalBody.find("#saveBtn").click(function(e) {
            //按钮
            var $this = $(this);
            //保存主题信息
            if (!saveThemeValidator.form() || saveUrl == null) {
                return;
            }

            if ($modalBody.find("#thumbnail_upload")[0] && $modalBody.find("#thumbnail_upload")[0].files[0] && $modalBody.find("#thumbnail_upload")[0].files[0].size > 4194304) {
                alert("缩略图过大，不能超过4M!");
                return;
            }

            var url = saveUrl;
            // 接收上传文件的后台地址 
            var xhr = new XMLHttpRequest();
            xhr.open("post", url, true);
            xhr.onload = function(oEvent) {
                if (xhr.status == 200) {
                    var ret = xhr.response;
                    if(typeof ret == 'string'){
                        ret = JSON.parse(ret);
                    }
                    if(ret.statusCode == '200'){
                        alert("操作成功");
                        that.dialog.close();
                        if($.isFunction(scallback)){
                            scallback(ret.data);
                        }
                    }else{
                        alert(ret.message || "操作失败");
                    }
                } else {
                    alert("异常错误");
                }
                $this.removeAttr("disabled");;
            };

            var formdata = new FormData($modalBody.find("form")[0]);
            if (type && type == 'layout') {
                var layoutAreaDatas = [];
                var $laycell = $(".laycell.active");
                if ($laycell[0]) {
                    $.each($laycell, function(i, item) {
                        var $item = $(item);
                        layoutAreaDatas.push({
                            areaCode: $item.attr("code"),
                            areaName: $item.text(),
                            // compType : "col",
                            elemCode: "DIV",
                            selectorExp: "DIV." + $item.attr("code")
                        })

                    })
                }
                var layoutCode = formdata.get("layoutCode");
                formdata.append("selectorExp", "." + layoutCode);
                formdata.append("layoutAreaDatas", JSON.stringify(layoutAreaDatas));
            }

            if (type && (type == 'control' || type == 'layoutareacontrol')) {
                //新增控件时设置筛选表达式
                if(!formdata.get("controlId")){
                    var compType = formdata.get("compType");
                    if(!compType){
                        compType = that.rightClickNode.compType;
                        formdata.append("compType",compType);
                    }
                    var elemCode = "";
                    var $compType = $($("#" + compType).html());
                    // if ($compType[0]) {
                    //     elemCode = $compType[0].tagName;
                    // }

                    var controlCode = formdata.get("controlCode");
                    var commonFlag = formdata.get("commonFlag");
                    var selectorExp;
                    var selectorExp2;
                    if (elemCode) {
                        selectorExp = elemCode + "." + compType;
                        selectorExp2 = elemCode + "[data-role='" + compType+"']";
                    } else {
                        selectorExp = "." + compType;
                        selectorExp2 = "[data-role='" + compType+"']";
                    }
                    if(commonFlag == null || commonFlag != 1){
                        selectorExp +=  "." + controlCode;
                        selectorExp2 += "." + controlCode;
                    }
                    selectorExp += "," + selectorExp2;
                    formdata.append("selectorExp", selectorExp);
                    formdata.append("elemCode", elemCode);
                }else{
                    //更新筛选表达式
                    var compType = that.rightClickNode.compType;
                    var controlCode = that.rightClickNode.controlCode;
                    var selectorExp;
                    var selectorExp2;
                    var elemCode = "";
                    var commonFlag = formdata.get("commonFlag");
                    if (elemCode) {
                        selectorExp = elemCode + "." + compType;
                        selectorExp2 = elemCode + "[data-role='" + compType+"']";
                    } else {
                        selectorExp = "." + compType;
                        selectorExp2 = "[data-role='" + compType+"']";
                    }
                    if(commonFlag == null || commonFlag != 1){
                        selectorExp +=  "." + controlCode;
                        selectorExp2 += "." + controlCode;
                    }
                    selectorExp += "," + selectorExp2;
                    formdata.append("selectorExp", selectorExp);
                }
            }
            $this.attr("disabled", true); //按钮禁用
            xhr.send(formdata); //发送请求
        })
    },
    
};

/**
 * 创建控件类型列表
 * @param  {[type]} datas   [description]
 * @param  {[type]} $parent [description]
 * @return {[type]}         [description]
 */
function createMenu(datas,$parent){
    var $parentUl = $('<ul class="cmpTypeParentUl">');
    var $controlUl = null;
    var list = [];
    $.each(datas,function(i,item){
        if(item.parentId){

        }else{
            list.push(item);
        }
    });
    $.each(list,function(i,item){
        $li = $('<li class="cmpTypeParent">');
        if(i ==0 ){
            $li.addClass("active");
        }
        var $span = $("<span class='cmpTypeTitle oneTitle'></span>");
        $span.text(item.name);
        $li.append($span);
        $parentUl.append($li);
        $controlUl = $('<ul class="">');
        $li.append($controlUl);

        $.each(datas,function(k,ktem){
            if(ktem.parentId == item.id){
                item.children = item.children || [];
                item.children.push(ktem);

                $li = $('<li class="controlItem">');
                var $div = $("<div class='cmpTypeTitle secondTitle'></div>");
                $div.append('<i class="fa fa-caret-right"></i>');
                $div.append(ktem.name);
                $li.append($div);
                $li.data("data",ktem);
                $controlUl.append($li);
            }
        });
    });
    $parent.append($parentUl);
}

//获取筛选表达式（拼接父节点）
function getSelectorExp(data) {
    var that = this;
    if(!data){
        return;
    }
    var themeType = data.treeType;
    if (themeType == 'control') {
        //为控件时，不需要拼接父节点的表达式
        return data.selectorExp;
    } else if (themeType == 'layoutarea') {
        //为布局区域时，拼接父节点表达式
        var parentNode = data.getParentNode();
        return getSelectorExp(parentNode) + " " + data.selectorExp;
    } else if (themeType == 'layoutareacontrol') {
        //为布局控件/模板时，拼接父节点表达式
        var parentNode = data.getParentNode();
        return getSelectorExp(parentNode) + " " + data.selectorExp;
    } else if (themeType == 'layout') {
        //为布局时，不需要拼接
        return data.selectorExp;
    } else {
        return "";
    }
}

var _settingPanelFunc = {
    attrToolboxJson :{},
    _init : function(){
        //初始化方法，只能执行一次
        var that = this;
        that._bindEvent();
    },
    /**
     * [_initAttr_toolbox 初始化属性面板]
     * @param  {[type]} $component   [控件的对象]
     * @param  {[type]} $attrContent [属性面板生成的内容存放位置]
     * @param  {[type]} data         [数据]
     * @return {[type]}              [description]
     */
    _initAttr_toolbox : function($component,$attrContent,data){
        var that = this;
        var attrToolbox = that.attrToolboxJson[data.ztreeId]; //属性工具栏
        if(!attrToolbox){
            attrToolbox = new $.Attr_toolbox(); //属性工具栏
            that.attrToolboxJson[data.ztreeId] = attrToolbox;
        }

        attrToolbox.controlId = data.id; //控件ID
        attrToolbox.compType = data.compType; //控件类型
        attrToolbox.themeType = data.treeType; //主题类型
        attrToolbox.styleData = data.styleData ? JSON.parse(data.styleData) : {}; //控件信息
        attrToolbox.CSS = data.CSS; //控件信息
        

        var selectorExp = getSelectorExp(data) || data.selectorExp;

        //设置筛选表达式
        if($component){
            $component.data("selectorExp", selectorExp);
            attrToolbox.$component = $component;
        }

        // attrToolbox.$style = $style;
        var $style = that._getJqueryStyle(data);

        attrToolbox.$style = $style;

        $style.data("nodeData",data);
        $style.data("selectorExp", selectorExp);


        if($attrContent && $attrContent[0]){
            that._createAttrProperties($attrContent,data);
        }
    },
    /**
     * 创建属性面板中的属性配置
     * @param  {[type]} $attrContent [description]
     * @param  {[type]} data         [description]
     * @return {[type]}              [description]
     */
    _createAttrProperties : function($attrContent,data){
        var that = this;
        var attrToolbox = that.attrToolboxJson[data.ztreeId]; //属性工具栏
        var compType_attr = base_attr;
        try{
            compType_attr = eval(data.compType + "_attr");
        }catch(e){

        }
        $attrContent.append(attrToolbox.createProperties(compType_attr));
        initTouchSpin();
        initColorPicker();
    },
    /**
     * 加载控件的样式
     * @param  {[type]} item [description]
     * @return {[type]}      [description]
     */
    _loadControlDisplay : function(item){
        var that = this;
        //加上全局控件的本身
        $(".controlAttrContent").empty();
        $(".controlDislayPanel").empty();
        $(".controlDislayPanel").addClass(that.theme.themeTmpCode);
        var $cmp = that._buildControl(item, $(".controlDislayPanel"));
            that._initAttr_toolbox($cmp,$(".controlAttrContent"),item);
        that._loadEnd();
    },
    /**
     * 创建“控件各种形态的选择面板”
     * @param  {[type]} datas [description]
     * @param  {[type]} data  [description]
     * @return {[type]}       [description]
     */
    _createControlThemes : function(datas,data){
        $(".controlThemePanel").empty();
        $.each(datas,function(i,item){
            item.nowdate = new Date().getTime();
            var $controlItem = $(ArtTemplateDataUtils.convertOne(item, $("#controlItem_template").html()));
            $(".controlThemePanel").append($controlItem);
            $controlItem.addClass("controlThemeItem");
            $controlItem.attr("controlId",item.id);
            item.cmpId = data.id;
            item.cmpName = data.name;
            $controlItem.data("data",item);
            $controlItem.data("cmpData",data);
            if(item.defaultFlag && item.defaultFlag == 1){
                $controlItem.addClass("default");
            }
        })
        var addBtn = $($("#controlThemeAdd_template").html());
        addBtn.data("cmpData",data);
        addBtn.addClass("controlThemeItem");
        $(".controlThemePanel").append(addBtn);

        $(".theme_item").hover(function(e){
            var $this = $(this);
            if($this.hasClass("default")){
                $this.find(".setDefaultBtn").text("取消默认主题");
            }else{
                $this.find(".setDefaultBtn").text("设置为默认主题");
            }
            $(this).find(".mask").show();
        },function(e){
            $(this).find(".mask").hide();
        })
    },
    /**
     * 加载“控件的形态数据”
     * @param  {[type]} data [description]
     * @return {[type]}      [description]
     */
    _loadControlThemes : function(data,scallback){
        var that = this;
        $.ajax({
            url: contextPath + "/mx/theme/sysThemeTmpControl/getControls",
            async: true,
            type: 'post',
            data : {'compType':data.dataRole,"themeTmpId":_themeId},
            // contentType : 'appliaction/json',
            // dataType: 'json',
            success: function(ret) {
                var datas = "";
                if(typeof ret == 'string'){
                    ret = JSON.parse(ret);
                }
                datas = ret.data;
                if(datas){
                    that._createControlThemes(datas,data);
                    that._loadEnd();
                    if($.isFunction(scallback)){
                        scallback();
                    }
                }
            }
        });
    },
    /**
     * 获取样式的JQUERY对象
     * @param  {[type]} data [description]
     * @return {[type]}      [description]
     */
    _getJqueryStyle : function(data){
        var $style = $("#style_" + data.treeType + (data.id || data.themeId));
        if(!$style[0]){
            $style = $("<style class='themeStyle' id='style_" + data.treeType + (data.id || data.themeId) + "'></style>");
            $style.data("styleData",data.styleData ? JSON.parse(data.styleData) : {}); //控件信息
            $style.html(data.CSS);
            $(".stylePanel").append($style);
        }
        return $style;
    },
    _loadGobalStyle : function(){
        //生成全局样式
        var that = this;
        var data = _leftPanelFunc.themeTreeObj.getNodes()[0];
        that.theme = data;
        // $("#gobalStyle").text(data.CSS);
    },
    //加载主题全局样式
    _loadMainStyle: function(data) {
        var that = this;

        //添加样式属性
        data.compType = "theme";
        data.selectorExp = "."+data.themeTmpCode;

        //清空右边面板内容
        var $mainStylePanel = $($("#mainStylePanel_template").html());
        $(".theme_right_panel").empty();
        $(".theme_right_panel").append($mainStylePanel);

        $mainStylePanel.find(".exampleDisplay").addClass(data.themeTmpCode);
        //设置常规样式
        // var $layoutSetting = that._buildLayoutSetting(data, $mainStylePanel);
        that._initAttr_toolbox(null,$mainStylePanel.find(".baseAttrContentContent"),data);
        that._loadGobalStyle();



        that._loadEnd();
    },
    //加载控件的所有种类列表
    _loadControlPanelStyle: function(data){
        var that = this;
        //清空右边面板内容
        var $controlPanel = $($("#controlPanel_template").html());
        $(".theme_right_panel").empty();
        $(".theme_right_panel").append($controlPanel);
        createMenu(_leftPanelFunc.componentTypeAllData,$controlPanel.find(".controlTypePanel"));
        that._loadEnd();
    },
    //加载结束，用于重计算面板宽高
    _loadEnd: function() {
        var that  = this;
        $(".display_panel").addClass(that.themeTmpCode);

        $("#compDsp .leftPanel").addClass(that.themeTmpCode);
        ResizePanel();
    },
    //创建控件真实的展现形态
    _createControl : function(item,$gobalControlType){
        var that = this;
        //加上全局控件的本身
        var $gobalControlItem = $($("#gobalControlItem_template").html());
            $gobalControlType.append($gobalControlItem);
            $gobalControlItem.attr("dataRole","true");
            $gobalControlItem.data("data",item);
        var $cmp = that._buildControl(item, $gobalControlItem.find(".content"));
            that._initAttr_toolbox($cmp,null,item);
    },
    //全部控件预览面板
    _loadControl3: function(datas, $display_panel) {
        var that = this;
        var $compDsp = that.$compDsp = $display_panel;

        $compDsp.empty();
        var $gobalControls = $($('#allGobalControls_template').html());
        $compDsp.append($gobalControls);

        $.each(datas, function(i, item) {
            var $gobalControlType = $($("#gobalControlType_template").html());
            $gobalControls.find(".gobalControls").append($gobalControlType);

            // that._createControl(item,$gobalControlType);
            var n = 0;
            $.each(item.children, function(k, ktem) {
                ktem.cmpId = item.cmpId;
                ktem.cmpName = item.name;

                that._createControl(ktem,$gobalControlType);

                n = k;
            })
            for( n=n+1; n < 5 ; n++){
                var $gobalControlItem = $($("#gobalControlItem_template").html());
                $gobalControlType.append($gobalControlItem);
            }
            $gobalControlType.find(".gobalControlItem").height($gobalControlType.innerHeight());
        })
        that._loadEnd();
    },
    //创建控件真实的展现形态
    _buildControl: function(data, $container) {
        var that = this;
        //控件外面套一层容器，用于作为选中框
        var $cmpbox = $("<div class='compbox'></div>");
        var $cmp;
        if(data.HTML){
            $cmp = $(data.HTML);
        }else{
            $cmp = $($("#" + data.compType).html());
        }
        //控件外面套一层容器，用于作为选中框
        // $cmpbox.append($cmp);
        //绑定id或cname
        $cmp.attr("cname", data.controlName);
        //设置ID
        $cmp.attr("dataid", data.id);
        $cmp.attr("data-role", data.compType);
        //设置主题类型
        var treeType = data.treeType;
        $cmp.attr("themeType", treeType);
        $cmp.attr("id", treeType + data.controlId);
        $cmp.addClass(data.compType);
        $cmp.addClass(data.controlCode);
        $cmp.addClass(treeType + data.controlId);
        $cmp.data("nodeData", data);

        //设置筛选表达式
        $cmp.data("selectorExp", getSelectorExp(data));
        
        $container.append($cmp);
        compRenderMethod.render(treeType + data.controlId,data.cmpId);

        return $cmp;
    },
    _bindEvent: function() {
        var that = this;
        //加载控件的样式种类
        $("body").on("click",".controlItem",function(event){
            $(".controlItem.seclected").removeClass("seclected");
            var $this = $(this);
            $this.addClass("seclected");
            var data = $this.data("data");
            that._loadControlThemes(data);
        })
        //展开开控件列表
        $("body").on("click",".oneTitle",function(event){
            $('.cmpTypeParent.active').removeClass("active");
            var $this = $(this);
            $this.parent().addClass("active");
        })
        //编辑控件样式
        $("body").on("click",".editControlItemBtn",function(event){
            var $this = $(this).closest(".controlThemeItem");
            $(".used").removeClass("used");
            $this.addClass("used");
            var data = $this.data("data");
            that._loadControlDisplay(data);
        })
        //修改控件样式基础信息
        $("body").on("click",".changeControlItemBtn",function(event){
            var $this = $(this).closest(".controlThemeItem");
            var data = $this.data("data");
            _leftPanelFunc.rightClickNode = data;
            data.themeTmpId = _themeId;
            var scallback = function(item){
                var item = item;
                item.dataRole = data.compType;
                that._loadControlThemes(item,function(){
                    $(".controlThemeItem[controlId='"+item.controlId+"']").find(".editControlItemBtn").trigger("click");
                });
            }
            _leftPanelFunc._openEditControlMenu("修改控件信息",data,scallback);
        })
        //增加控件样式
        $("body").on("click",".add_theme_btn",function(event){
            var $this = $(this).closest(".controlThemeItem");
            var data = $this.data("cmpData");
            _leftPanelFunc.rightClickNode = {"compType":data.dataRole,"themeTmpId" : _themeId};
            var scallback = function(item){
                var item = item;
                item.dataRole = data.dataRole;
                that._loadControlThemes(item,function(){
                    debugger;
                    $(".controlThemeItem[controlId='"+item.controlId+"']").find(".editControlItemBtn").trigger("click");
                });
            }
            _leftPanelFunc._openEditControlMenu("新增控件信息",{"compType":data.dataRole,"themeTmpId" : _themeId},scallback);
        })

        //设置默认控件
        $("body").on("click",".setDefaultBtn",function(event){
            var $this = $(this).closest(".controlThemeItem");
            var itemData = $this.data("data");
            itemData.dataRole = itemData.compType;
            $.ajax({
                url: contextPath + "/mx/theme/sysThemeTmpControl/setDefaultTheme",
                async: true,
                type: 'post',
                data : {'controlId':itemData.controlId,"defaultFlag":itemData.defaultFlag?0:1},
                // contentType : 'appliaction/json',
                // dataType: 'json',
                success: function(ret) {
                    var datas = "";
                    if(typeof ret == 'string'){
                        ret = JSON.parse(ret);
                    }
                    if(ret && ret.statusCode == '200'){
                        that._loadControlThemes(itemData)
                    }
                }
            });
        })

        //保存布局样式
        $('body').on("click","#saveControlStyleBtn,#saveLayoutStyleBtn",function(event) {
            var $layout = $("style[changedStyle]");
            var datas = [];
            $.each($layout, function(i, item) {
                var $item = $(item);
                var styleData = $item.data("styleData");
                var nodeData = $item.data("nodeData");
                var selectorExp = nodeData.selectorExp;
                var CSSID = nodeData.CSS_id;
                var styleDataId = nodeData.styleData_id;

                var $style = $item || $('#style_' + nodeData.treeType + nodeData.id);
                var styleCss = $style.html();

                datas.push({
                    bussKey: nodeData.id || $item.attr("dataid"), //业务关键字
                    bussType: nodeData.treeType.toLocaleUpperCase(), //业务分类(控件)
                    styleData: styleData,
                    styleDataId: styleDataId,
                    styleCss: styleCss,
                    styleCssId: CSSID,
                    // themeTmpId : themeId,
                })
            })
            var callback = function(retdata){
                var iddatas = retdata.data;
                $.each($layout,function(i,item){
                    var $item = $(item);
                    var nodeData = $item.data("nodeData");
                    if(!nodeData.CSS_id || !nodeData.styleData_id){
                        $.each(iddatas,function(k,ktem){
                            if(nodeData.treeType.toLocaleUpperCase() == ktem.bussType && nodeData.id == ktem.bussKey){
                                if(ktem.type == 'CSS'){
                                    nodeData.CSS_id = ktem.id;
                                }else{
                                    nodeData.styleData_id = ktem.id;
                                }
                            }
                        })
                    }
                })
                alert("操作成功");
            }
            that.saveTmpByte(datas,callback);
        })

    },
    saveTmpByte: function(params,callback) {
        var that = this;
        $.ajax({
            url: _page_param.blukSaveTmpByteArrayUrl,
            async: false,
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(params),
            dataType: 'json',
            success: function(data) {
                if($.isFunction(callback)){
                    callback(data);
                }else{
                    if(data && data.statusCode == '200'){
                        alert("操作成功");
                    }else{
                        alert("操作失败");
                    }
                }
            }
        });
    },
}

function getUiTypeData(){
    var that = this;
    var option = {
        url: contextPath + "/mx/form/defined/getDictByCode",
        async: false,
        data: {
            code: 'ui_type'
        },
        type: 'post',
        dataType: 'json',
        success : function(data){
            window._uiTypeData = data;
        }
    }
    $.ajax(option);
}  

function loadTheme(){
    $.ajax({
        url: contextPath + "/mx/theme/sysThemeTmp/getThemeById",
        async: true,
        type: 'post',
        data : {'themeId':_themeId},
        // contentType : 'appliaction/json',
        // dataType: 'json',
        success: function(ret) {
            var datas = "";
            if(typeof ret == 'string'){
                ret = JSON.parse(ret);
            }
            if(ret.statusCode == '200'){
                window._themeData = ret.data;
                _themeData.uiTypeData = window._uiTypeData;
                var templateHtml = ArtTemplateDataUtils.convertOne(ret.data, $("#themeEditTemplate").html());
                $("#themeInfoPanel").append(templateHtml);
                loadEvent();
            }else{
                alert(ret.message || '获取主题信息失败');
            }
        }
    });
}

function loadEvent(){
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
    var $form = $("#themeEditForm");
    var saveThemeValidator = $form.validate(validate_setting);
    $form.find(".theme_thumbnail_panel").hover(function(e){
        $(this).find("a").show();
    },function(e){
        $(this).find("a").hide();
    })

    //缩略图修改时，实现图片预览
    $form.find("#thumbnail_upload").change(function(e){
        var file = this.files[0];
        //实现文件预览功能
        var reader = new FileReader();
        var $theme_thumbnail_show = $form.find("#theme_thumbnail_show");
        reader.onloadend = function () {
            $theme_thumbnail_show.attr("src",reader.result).show();
        }
        if (file) {
            reader.readAsDataURL(file);
        }
    })

    $form.find("#saveThemeBtn").click(function(e){
        //按钮
        var $this = $(this);
        //保存主题信息
        if(!saveThemeValidator.form()){
            return;
        }

        if($form.find("#thumbnail_upload")[0].files[0] && $form.find("#thumbnail_upload")[0].files[0].size > 4194304){
            alert("缩略图过大，不能超过4M!");
            return;
        }

        var url = contextPath + "/mx/theme/sysThemeTmp/saveSysThemeTmp";                    
        // 接收上传文件的后台地址 
        var xhr = new XMLHttpRequest();
        xhr.open("post", url, true);
        xhr.onload = function (oEvent) {
            debugger;
            if (xhr.status == 200) {
                alert("修改主题成功");
            }else{
                alert("异常错误");
            }
            $this.removeAttr("disabled");;
        };
        
        var formdata = new FormData($form[0]);
        $this.attr("disabled",true);    //按钮禁用
        xhr.send(formdata); //发送请求
    })
}

$(function(){
    $('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
        ResizePanel();
    })

    if (_themeId) {
        getUiTypeData();
        loadTheme();

        _leftPanelFunc._init_(_themeId);
        _settingPanelFunc._init(_themeId);
        //生成css文件
        $("#buildThemeStyleBtn").click(function(event){
            $.ajax({
                url: contextPath + "/mx/theme/sysThemeTmp/buildThemeStyle",
                async: true,
                type: 'post',
                data : {'themeTmpId':_themeId},
                // contentType : 'appliaction/json',
                // dataType: 'json',
                success: function(ret) {
                    var datas = "";
                    if(typeof ret == 'string'){
                        ret = JSON.parse(ret);
                    }
                    if(ret.statusCode == '200'){
                        alert(ret.message || '操作成功');
                    }else{
                        alert(ret.message || '操作失败');
                    }
                }
            });
        });
        //全局控件预览
        $("#prevAllControlBtn").click(function(event){
            var height = $(window).height() * 0.8;
            BootstrapDialog.show({
                title : '主题预览',
                size : BootstrapDialog.SIZE_WIDE,
                css : {
                    width : $(window).width() * 0.8,
                },
                onshown:function(dialog){
                    var $modalBody = dialog.getModalBody();

                    var $div = $("<div></div>");
                    $div.height(height);
                    $div.addClass(_settingPanelFunc.theme.themeTmpCode);
                    $modalBody.append($div);

                    $.ajax({
                        url: _page_param.queryAllControlUrl,
                        async: true,
                        type: 'post',
                        data:{"themeId":_themeId},
                        dataType: 'json',
                        success: function(datas) {
                            if(datas && datas.rows){
                                var componentTypeData = _leftPanelFunc.componentTypeData;
                                $.each(datas.rows,function(i,item){
                                    $.each(componentTypeData, function(k, ktem) {
                                        if (item.compType == ktem.dataRole) {
                                            item.name += "(" +ktem.name +")";
                                            item.cmpId = ktem.id;
                                            item.cmpTypeParentId = ktem.parentId;
                                            return false;
                                        }
                                    })
                                })
                                _settingPanelFunc._loadControl3(datas.rows,$div);    
                            }
                        }
                    });
                },
                modal : true //遮蔽层
            });
        })
    }
    
})

