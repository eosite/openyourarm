//常用控件
var commonComps = [];
//控件类型
var types;
initMenu(false, selectedUiType);
//初始化控件列表
function initMenu(aysnc, type) {
    $.ajax({
        url: contextPath + "/rs/form/component/read?categoryLike=" + type,
        type: "post",
        async: aysnc,
        contentType: "application/json",
        dataType: "json",
        success: function(rdata) {
            commonComps = [];
            if (rdata) {
                var jsonData = eval(rdata);
                var jsonDataTree = transData(jsonData, 'id',
                    'parentId', 'children');
                jsonDataTree = {
                    "children": jsonDataTree
                };
                if ("children" in jsonDataTree) {
                    $("#sidebar-menu .sidebar-search-wrapper").nextAll().remove();
                    $("#sidebar-menu").append("<li class=\"heading\"><h3 class=\"uppercase\">控件列表</h3></li>");
                    types = [];
                    createMenu(jsonDataTree.children,
                        $("#sidebar-menu"), '', 0);
                    createTypeSelect(types);
                    createCommonMenu();
                }
            }

        },
        error: function() {
            console.log("获取控件列表数据失败");
        }

    });
}

function createCommonMenu() {
    var lidom = $("<li class=\"nav-item start active open\"><a href=\"#\" class=\"nav-link nav-toggle\"><i class=\"icon-star\"></i><span class=\"title\">常用控件</span><span class=\"selected\"></span><span class=\"arrow open\"></span></a></li>");
    var uldom = $("<ul class=\"sub-menu\"></ul>");
    createMenu(commonComps, uldom, '', 1);
    lidom.append(uldom);
    $(".page-sidebar-menu .heading").before(lidom);
}
//创建控件类型下拉选择项
function createTypeSelect(types) {
    $("#type-select").empty();
    var option;
    $.each(types, function(i, type) {
        option = $("<option></option>");
        option.attr("value", type.id);
        option.text(type.name);
        $("#type-select").append(option);
    });
    //默认选中第一个分类
    // $("#sidebar-menu").children("li[class*=nav-item]:not(.start)").css("display","none");
    $("#sidebar-menu").children("li[class*=nav-item]:not(.start):first").css("display", "block");
    //绑定change事件
    $("#type-select").unbind("change");
    $("#type-select").bind("change", function() {
        //获取选中的类型
        var id = $(this).val();
        //设置控件菜单为隐藏
        // $("#sidebar-menu").children("li[class*=nav-item]:not(.start)").css("display","none");
        //当前选中控件类型设置为显示
        // var selectedNavItem=$("#sidebar-menu").find("a[data_id="+id+"]").closest("li");
        // selectedNavItem.css("display","block");
        //调用click事件
        $("#sidebar-menu").find("a[data_id=" + id + "]").click();
    });
}

//创建控件菜单项
function createMenu(nodes, pnode, pDataRole, level) {
    var l = level;
    if (!pnode.length)
        return;
    if (pnode[0].localName && pnode[0].localName == "ul") {
        var lidom, adom, spandom;
        $
            .each(
                nodes,
                function(i, node) {
                    l = level;
                    lidom = $("<li></li>");
                    //if (l == 0 && i == 0) {
                    //lidom.addClass("active");
                    //lidom.addClass("open");
                    //lidom.addClass("start");
                    //}
                    //如果是第一层则添加到控件类型下拉选择项
                    if (l == 0) {
                        var type = new Object();
                        type.id = node.id;
                        type.name = node.name;
                        types.push(type);
                    }
                    lidom.addClass("nav-item");
                    adom = $("<a data_role='" + node.dataRole + "'  data_id='" + node.id + "'></a>");
                    if (node.pDataRole != undefined && node.pDataRole != '') {
                        adom.attr("pdata_role", pDataRole);
                    } else if (pDataRole != '') {
                        adom.attr("pdata_role", pDataRole);
                    }
                    if (node.baseComp && node.baseComp == 1) {
                        node.pDataRole = pDataRole;
                        commonComps.push(node);
                    }
                    if (node.integral != undefined) {
                        adom.attr("integral", node.integral);
                    } else {
                        adom.attr("integral", 0);
                    }
                    adom.addClass("nav-link");

                    //获取适用范围（哪些UI包含此组件）
                    var scope = node.category;
                    if (scope) {
                        adom.attr("scope", scope);
                    }
                    //判断页面元素是否属于布局元素
                    if (("" + node.dataRole).indexOf("col-md") > -1) {
                        adom.addClass("layout");
                    } else {
                        adom.addClass("elem");
                    }
                    adom.attr("href", "#");
                    if (node.url && node.url != "") {
                        adom.attr("onclick", "openUrl(" + node.id + ",\"" + node.name + "\",\"" + node.url + "\",\"" + node.showmenu + "\")");
                    }
                    if (l == 0) {
                        if (node.smallImageFileName != undefined) {
                            adom
                                .append("<i class=\"" + node.smallImageFileName + "\"></i>");
                            adom.append("<span  class=\"title\">" + node.name + "</span>");
                        } else {
                            adom
                                .append("<i class=\"icon-puzzle\"></i>");
                            adom.append("<span  class=\"title\">" + node.name + "</span>");
                        }
                    } else {
                        //adom
                        // .append("<i class=\"icon-bulb\"></i>");
                        adom.append("<img title=\"" + node.name + "\" src=\"" + contextPath + "/scripts/designer/images/component/" + node.dataRole + ".png\"/>");
                        adom.append("<span  class=\"title\" style=\"display:none;\">" + node.name + "</span>");
                    }

                    spandom = $(" <span class=\"arrow\"></span>");

                    if ("children" in node) {
                        adom.addClass("nav-toggle");
                        if (lidom.hasClass("open")) {
                            adom.append("<span class=\"selected\"></span>");
                            spandom.addClass("open");
                        }
                        adom.append(spandom);
                    }

                    lidom.append(adom);
                    if ("children" in node) {
                        var uldom = $("<ul class=\"sub-menu\"></ul>");
                        createMenu(node.children, uldom, node.dataRole, ++l);
                    }
                    lidom.append(uldom);
                    pnode.append(lidom);
                    if (l > 0) {
                        if (i == nodes.length - 1 && (i + 1) % 3 > 0) {
                            //不够三个补齐
                            var addli, adda, addimg, addspan;
                            for (var j = 0; j < 3 - (i + 1) % 3; j++) {
                                addli = $("<li class=\"nav-item\"></li>");
                                adda = $("<a></a>");
                                addimg = $("<img border=\"0\" src=\"" + contextPath + "/scripts/designer/images/component/empty.png\" style=\"height:32px;\"/>");
                                addspan = $("<span></span>");
                                adda.append(addimg);
                                adda.append(addspan);
                                addli.append(adda);
                                pnode.append(addli);

                            }
                        }
                    }
                });
    }
}
//创建控件模板分类树
if (objectCategoryJson != '') {
    objectCategoryJson = eval(objectCategoryJson);
}

var compTemplateCategoryJsonDataTree = transData(objectCategoryJson, 'categoryId',
    'parentId', 'children');
createCompTemplateCategoryTree();

function createCompTemplateCategoryTree() {
    $("#template-select").append("<option value=\"\">---请选择模板类型---</option>");
    $.each(compTemplateCategoryJsonDataTree, function(i, compTemplateCategoryJsonData) {
        createCompTemplateCategory(compTemplateCategoryJsonData);
    });
}

function createCompTemplateCategory(data) {
    var name = "&nbsp;&nbsp;&nbsp;&nbsp;" + data.name;
    for (var i = 1; i < data.level; i++) {
        name = "&nbsp;&nbsp;&nbsp;&nbsp;" + name;
    }
    $("#template-select").append("<option value=\"" + data.categoryId + "\">" + name + "</option>");
    if (data.children != null) {
        $.each(data.children, function(i, item) {
            createCompTemplateCategory(item);
        });
    }
}
//创建模板控件列表
function initTemplate(aysnc, type) {
    if ($.trim(type) != '') {
        $.ajax({
            url: contextPath + "/rs/designer/" + type + "/categoryTemplates",
            type: "post",
            async: aysnc,
            contentType: "application/json",
            dataType: "json",
            success: function(rdata) {
                $(".templateli").empty();
                if (rdata != null && rdata.templates != null) {
                    $.each(rdata.templates, function(i, item) {
                        createTemplateItem($(".templateli"), item);
                    });
                }

            },
            error: function() {
                console.log("获取控件列表数据失败");
            }

        });
    }
}
//创建模板控件菜单项
function createTemplateItem(ul, item) {
    var lidom = $("<li class=\"nav-item start\"></li>");
    var temlpateContent = "<template id=\"template_" + item.id + "\">" + item.template + "</template>";
    //temlpateContent.append(item.template);
    lidom.append(temlpateContent);
    var adom = $("<a href=\"#\" id=\"" + item.id + "\" class=\"nav-link nav-toggle template\"></a>");
    var thumbnaildom = $("<i></i>");
    if (item.image_exists == 1) {
        var imgdom = $("<img></img>");
        imgdom.css("width", "150px");
        imgdom.attr("src", contextPath + "/rs/designer/" + item.id + "/getCategoryTemplateImage");
        thumbnaildom.append(imgdom);
    }
    adom.append(thumbnaildom);
    var titledom = $("<span class=\"templatetitle\"></span>");
    titledom.append(item.title);
    adom.append(titledom);
    var arrowdom = $("<span class=\"templatearrow\"></span>");
    adom.append(arrowdom);
    lidom.append(adom);
    ul.append(lidom);
}
$('#template-select').change(function() {
    //异步获取模板信息
    initTemplate(false, $("#template-select").val());
    initdraggable();
});
//重新计算iframe高度
function settingFrameHeight() {
    var contentTh = $(".content-wrapper").height();
    var contentHh = $(".content-header").height();
    //var conentFh=$(".main-footer").height();
    $("#dsDiv").height(contentTh - contentHh - 100);
}
$(window).resize(function() {
    //settingFrameHeight();
    $("body").css("min-height", $(window).height() - 50);
    $("#dsDiv").css("min-height", $(window).height() - 56);
    //$("#dsDiv").css("height", $(window).height() - 56);
});
//从缓存加载最近一次定义
function restoreData() {
    if (supportstorage() && $(".designer").html().trim() == "") {
        layouthistory = JSON.parse(localStorage.getItem("layoutdata"));
        if (!layouthistory) return false;
        window.designerHtml = layouthistory.list[layouthistory.count - 1];
        if (window.designerHtml) $(".designer").html(window.designerHtml);
    }
}
//为元素删除按钮绑定事件
function removeElement() {
    $(".designer").delegate(".remove", "click", function(e) {
        e.preventDefault();
        $(this).parent().remove();
        if (!$(".designer .lyrow").length > 0) {
            clearDesigner()
        }
        clashCompute();
    });
    $(".designer").delegate(".myremove", "click", function(e) {
        e.preventDefault();
        $(this).closest(".toolbar").parent().remove();
        if (!$(".designer .lyrow").length > 0) {
            clearDesigner()
        }
        clashCompute();
    });
}
//为删除列按钮绑定事件
function removeCol() {
    $(".designer").delegate(".removecol", "click", function(e) {
        e.preventDefault();
        //获取行
        var row = $(this).closest(".row");
        //获取当前列
        var currentCol = $(this).closest(".column");
        //获取列栅格样式
        var currClass = currentCol.attr("class").match((/col\-(md|mmd|24md)\-\d+/))[0];
        //获取列栅格数
        var currCells = currClass.match((/\d+$/))[0];
        if (row.children().length == 1) {
            alert("容器内容为空，禁止删除！");
            return;
        }
        var preColumnClass = getGridSysByColumnClass(currClass);
        //如果不是最后一列
        if (currentCol.index() < row.children().length - 1) {
            //调整第二列样式
            var changeColClass = $(row.children()[currentCol.index() + 1]).attr("class").match((/col\-(md|mmd|24md)\-\d+/))[0];
            var changeColCells = changeColClass.match((/\d+$/))[0];
            var newColCells = parseInt(changeColCells) + parseInt(currCells);
            var newClass = preColumnClass + newColCells;
            $(row.children()[currentCol.index() + 1]).removeClass(changeColClass);
            $(row.children()[currentCol.index() + 1]).addClass(newClass);
            if (newColCells > 1) {
                $(row.children()[currentCol.index() + 1]).removeClass("sigcol");
            }
        } else {
            var changeColClass = $(row.children()[currentCol.index() - 1]).attr("class").match((/col\-(md|mmd|24md)\-\d+/))[0];
            var changeColCells = changeColClass.match((/\d+$/))[0];
            var newColCells = parseInt(changeColCells) + parseInt(currCells);
            var newClass = preColumnClass + newColCells;
            if (newColCells > 1) {
                $(row.children()[currentCol.index() - 1]).removeClass("sigcol");
            }
            $(row.children()[currentCol.index() - 1]).removeClass(changeColClass);
            $(row.children()[currentCol.index() - 1]).addClass(newClass);
        }
        currentCol.remove();


    })
}
//清空设计区
function clearDesigner() {
    $(".designer").empty();
    $(".ui-list").empty();
    layouthistory = null;
    if (supportstorage())
        localStorage.removeItem("layoutdata");
}
$(function() {
    //settingFrameHeight();
    $("body").css("min-height", $(window).height() - 50);
    $("#dsDiv").css("min-height", $(window).height() - 56);
    //$("#dsDiv").css("height", $(window).height() - 56);
    restoreData();
    initdraggable();
    initContainer(true);
    //预览按钮绑定事件
    $("#previewBt").click(function() {
        //公用样式面板隐藏
        $(".commonStylePanel").hide();
        $("#editBt").removeClass("active");
        $(this).addClass("active");

        $("body").removeClass("edit");
        $("body").addClass("devpreview preview");
        //修改左侧背景色
        //$(".page-content-wrapper").css("background-color", "#fff");
        //$(".page-sidebar-wrapper").css("background-color", "#fff");
        //修改左侧隐藏
        if (!$("body").hasClass("page-sidebar-closed")) {
            $("body").addClass("page-sidebar-closed");
        }
        if (!$("#sidebar-menu").hasClass("page-sidebar-menu-closed")) {
            $("#sidebar-menu").addClass("page-sidebar-menu-closed");
        }
        if (!$("#template-sidebar-menu").hasClass("page-sidebar-menu-closed")) {
            $("#template-sidebar-menu").addClass("page-sidebar-menu-closed");
        }
        $(".mymenu button").removeClass("active");

        $(".mybox.box-element[id^='mtbutton_'").addClass('inline_block');

        //取消编辑属性
        $("[contenteditable]").attr("contenteditable", "false");
        $(this).addClass("active");
        //检测当前页面高度模式
        if ($("#autoHeightBt").hasClass("green-jungle")) {
            $("#dsDiv").css("height", $("#dsDiv").css("min-height"));
        }
        return false
    });
    //编辑按钮绑定事件
    $("#editBt").click(function() {
        //公用样式面板显示
        $(".commonStylePanel").show();
        $("#previewBt").removeClass("active");
        $(this).addClass("active");

        $("body").removeClass("devpreview preview");
        $("body").addClass("edit");

        $(".mybox.box-element[id^='mtbutton_'").removeClass('inline_block');
        //$(".page-sidebar-wrapper").attr("style", "");
        //$(".page-content-wrapper").attr("style", "");
        $(".mymenu button").removeClass("active");
        //$(".content-wrapper").css("margin-left", "auto");
        //开放编辑属性
        $("[contenteditable]").attr("contenteditable", "true");
        $(this).addClass("active");
        $("#dsDiv").css("height", "");
        return false
    });
    $("#saveBt").click(function(e) {
        e.preventDefault();
        handleSaveLayout();
        if ($(".designer").html().trim() == '') {
            $.alert(1, "设计页面内容为空");
            return;
        }

        //寻找包含data-role的非栅格，非label，非form控件
        // var $notNameObj = $('#dsDiv').find("[data-role]:not([data-role^=col]):not([data-role=label]):not([data-role=buttongroup]):not(form)");
        // // var errorStr = '以下控件未设置控件名称，请设置后再保存!/n';
        // var errorStr = '';
        // $.each($notNameObj, function(i, item) {
        //     var cname = item.getAttribute("cname");
        //     if (!cname || cname.indexOf(item.id) > -1) {
        //         if (errorStr) {
        //             errorStr += ",";
        //         }
        //         errorStr += item.id;
        //     }
        // })
        // if (errorStr) {
        //     errorStr = '以下控件未设置控件名称，请设置后再保存!\n' + errorStr;
        //     alert(errorStr);
        // }

        //保存前调用需要Destroy的控件
        //metro_destroy.destroyAll();

        convertLayoutHtml();
        //获取全局样式
        var globalStyle = $(".designer").attr("data-rule");
        var params = {
            id: id,
            pId: pId,
            pageLayout: $("#pageLayout").text(),
            pageDesigner: $("#pageDesigner").text()
        };
        params.designerJson = globalStyle;
        $(this).attr("disabled", true);
        var $this = $(this);
        $.ajax({
            url: contextPath + "/mx/page/designer/save",
            type: "post",
            async: true,
            data: params,
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            success: function(rdata) {
                if (rdata.result == 1) {
                    $.alert(0, "保存成功!");
                } else if (rdata.result == -2) {
                    $.alert(1, "保存失败，页面设计信息丢失! 请刷新页面重试。");
                } else if (rdata.result == -999) {
                    $.alert(1, "没有权限。");
                } else {
                    $.alert(1, "保存失败!");
                }
                $this.attr("disabled", false);
            },
            error: function() {
                $.alert(1, "保存出错!");
                $this.attr("disabled", false);
            },
            complete: function(XHR, TS) {
                //保存后调用需要Restore的控件
                //metro_restore.restoreAll();
                $this.attr("disabled", false);
            }
        });

    });
    //清空按钮绑定事件
    $("#emptyBt").click(function() {
        $(".designer").empty();
        $(".ui-list").empty();
        layouthistory = null;
        if (supportstorage())
            localStorage.removeItem("layoutdata");
    });
    //导出按钮绑定事件
    $("#exportBt").click(function() {
        if ($(".designer").html().trim() == '') {
            $.alert(1, "设计页面内容为空");
            return;
        }
        convertLayoutHtml();
        dForm.action = "download";
        dForm.target = "operFrame";
        dForm.submit();
    });
    //导出布局按钮绑定事件
    $("#designerExportBt").click(function() {
        if ($(".designer").html().trim() == '') {
            $.alert(1, "设计页面内容为空");
            return;
        }
        convertLayoutHtml();
        dForm.action = "downloadDesigner";
        dForm.target = "operFrame";
        dForm.submit();
    });
    //布局导入按钮绑定事件
    $("#designerImportBt").click(function() {
        $('#importDesignerModal').modal('show');
        $("#fileinput").fileinput('refresh');
    });
    //页面模板选择按钮绑定事件
    $("#themeTemplate").click(function() {
        $('#pageTemplateModal').modal('show');
    });
    //页面模板选择按钮绑定事件
    $("#pageRuleTemplate").click(function() {
        var dialog = BootstrapDialog.show({
            title: "页面规则模板",
            draggable: true,
            message: function(dialog) {
                var pageToLoad = dialog.getData('pageToLoad');
                var frame = "<iframe onload=\"$(this).contents().find('body').css('overflow-y', 'auto');\" width='100%' height='800px' src='" + pageToLoad + "' frameborder='no' scrolling='yes'></iframe>";
                return $(frame);
            },
            data: {
                'pageToLoad': contextPath + "/mx/form/defined/ex/pageRuleTemplate"
            },
            css: {
                width: '1200px',
                height: '800px'
            }
        });
    });
    //自由布局按钮绑定事件
    $("#layoutDefined").click(function() {
        $('#freelayoutModal').modal('show');
        $("#freelayoutModal").layout("refresh_");
    });
    //初始化fileinput控件
    function initFileInput(ctrlName, uploadUrl) {
        var control = $('#' + ctrlName);

        control.fileinput({
            language: 'zh', //设置语言
            uploadUrl: uploadUrl, //上传的地址
            allowedFileExtensions: ['tmpl', 'html', 'htm'], //接收的文件后缀
            'showPreview': false,
            maxFileSize: 2000,
            multiple: false

        });
        //导入完成后回调方法
        control.on('fileuploaded', function(event, data, previewId, index) {
            var response = data.response;
            if (response.result) {
                var tmplContent = $(response.result);
                var designerContent = tmplContent.find("body").length > 0 ? tmplContent.find("body").html() : tmplContent;
                //验证模板内容是否符合要求
                var tempContent = $("<div></div>");
                tempContent.append(designerContent);
                //获取有效区间内容
                if (tempContent.children(".lyrow.layout_elem:first").length == 0) {
                    $.alert(1, "导入模板内容不符合要求!");
                } else {
                    var actContent = tempContent.children(".lyrow.layout_elem:first").parent().html();
                    $(".designer").html(actContent);
                    draggEventBind();
                    initContainer();
                    $('#importDesignerModal').modal('hide');
                }

            }
        });
    }

    initFileInput("fileinput", contextPath + '/rs/designer/importDesigner');
    removeElement();
    removeCol();
    //初始化适用UI范围
    clashCompute();
    //定时自动保存
    setInterval(function() {
        handleSaveLayout()
    }, timerSave);
    //响应式模拟
    $(".reponsive-block li>a").click(function() {

        $(".reponsive-block li>a").removeClass("active");
        var designerWidth = $(this).attr("data-width");
        var currentWidth = $(".designer").width();
        //PC
        if (designerWidth == 'auto') {
            $(".designer").width("auto")
        } else if (($(window).width() - 230) >= designerWidth) {
            $(".designer").width(designerWidth)
        }
        $(this).addClass("active");

    });
    //设置页面为编辑模式
    //document.designMode = "on";
    $("#setStaticData").click(function(event) {
        var $this = $(this);
        if ($this.attr("disabled")) {
            return;
        }
        var $component = window.currentComponent;
        if ($component[0]) {
            var dataRole = $component.attr("data-role");
            var ctrImpl = "showStaticDataPanle";
            ctrImpl = dataRole != "" ? eval(dataRole + "_designer." + ctrImpl) : eval(ctrImpl);
            try {
                ctrImpl();
            } catch (e) {
                console.log("无该模拟数据方法！");
            }
        }
    })
});
//获取页面设计高度模式 1、自适应高度 默认0
function getPageHeightDesingerMode() {
    if ($("#autoHeightBt").hasClass('green-jungle')) {
        return 1;
    }
    return 0;
}
//转换页面布局HTML
function convertLayoutHtml() {
    var $layoutHtml = $(".designer").clone();
    $layoutHtml.find("[data-rule]").removeAttr('data-rule');
    $layoutHtml.find("[scope]").removeAttr('scope');
    //高度还原
    if (getPageHeightDesingerMode() == 1) {
        var rows = $layoutHtml.find(".row.autoHt");
        var lyrow, actHeight;
        $.each(rows, function(i, row) {
            //防止行嵌套替换异常
            lyrow = $(row).parent().parent(".lyrow");
            if (lyrow.length == 1) {
                actHeight = lyrow.css("height");
                if (actHeight.indexOf("%") > -1)
                    $(row).css("height", actHeight);
            }
        });
    }
    //补充 栅格行 ID、data-role、scope、crtltype 属性值
    var lyrows = $layoutHtml.find(".lyrow.layout_elem[data-role]");
    var rowId, dataRole, cname, scope, crtltype, currRow, $lyrow;
    $.each(lyrows, function(i, lyrow) {
        $lyrow = $(lyrow);
        rowId = $lyrow.attr("id");
        dataRole = $lyrow.attr("data-role");
        scope = $lyrow.attr("scope");
        crtltype = $lyrow.attr("crtltype");
        cname = $lyrow.attr("cname");
        $lyrow.removeAttr("id");
        $lyrow.removeAttr("data-role");
        $lyrow.removeAttr("scope");
        $lyrow.removeAttr("ctrltype");
        currRow = $lyrow.children(".view").children();
        currRow.attr("id", rowId);
        currRow.attr("data-role", dataRole);
        currRow.attr("scope", scope);
        currRow.attr("crtltype", crtltype);
        if (cname && cname != undefined) {
            currRow.attr("cname", cname);
        }
    });
    //去掉设计元素
    $layoutHtml
        .find(
            ".preview, .configuration,.formgrouptoolbar,.toolbar, .drag, .remove,.removecol")
        .remove();
    $layoutHtml.find(".lyrow").addClass(
        "removeClean");
    $layoutHtml.find(".box-element").addClass(
        "removeClean");
    $layoutHtml
        .find(
            ".lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .removeClean")
        .each(function() {
            cleanHtml(this);
        });
    $layoutHtml
        .find(
            ".lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .removeClean")
        .each(function() {
            cleanHtml(this);
        });
    $layoutHtml
        .find(
            ".lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .removeClean")
        .each(function() {
            cleanHtml(this);
        });
    $layoutHtml
        .find(
            ".lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .removeClean")
        .each(function() {
            cleanHtml(this);
        });
    $layoutHtml
        .find(
            ".lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .removeClean")
        .each(function() {
            cleanHtml(this);
        });
    $layoutHtml
        .find(
            ".lyrow .lyrow .lyrow .lyrow .lyrow .removeClean")
        .each(function() {
            cleanHtml(this);
        });
    $layoutHtml
        .find(
            ".lyrow .lyrow .lyrow .lyrow .removeClean")
        .each(function() {
            cleanHtml(this);
        });
    $layoutHtml.find(
            ".lyrow .lyrow .lyrow .removeClean")
        .each(function() {
            cleanHtml(this);
        });
    $layoutHtml.find(".lyrow .lyrow .removeClean")
        .each(function() {
            cleanHtml(this);
        });
    $layoutHtml.find(".lyrow .removeClean").each(
        function() {
            cleanHtml(this);
        });
    $layoutHtml.find(".containerComp .containerComp .containerComp .containerComp .removeClean").each(
        function() {
            cleanHtml(this);
        });
    $layoutHtml.find(".containerComp .containerComp  .containerComp .removeClean").each(
        function() {
            cleanHtml(this);
        });
    $layoutHtml.find(".containerComp .containerComp .removeClean").each(
        function() {
            cleanHtml(this);
        });
    $layoutHtml.find(".containerComp .removeClean").each(
        function() {
            cleanHtml(this);
        });
    $layoutHtml.find(".removeClean").each(
        function() {
            cleanHtml(this);
        });
    $layoutHtml.find(".removeClean").each(function(index, el) {
        $(el).remove();
    });
    $layoutHtml.find('[contenteditable=true]').each(function(index, el) {
        $(el).removeAttr('contenteditable');
    });
    //移除控件编辑状态样式
    $layoutHtml.find('.shine_red').removeClass("shine_red");
    $layoutHtml.find(".column").removeClass(
        "ui-sortable");
    //移除formgroup样式
    $layoutHtml.find('.myform').removeClass("myform");
    $layoutHtml.find('.myformgroup').removeClass("myformgroup");
    //获取设置了适应小尺寸规则的栅格
    var fitsizeRows = $layoutHtml.find(".row_sm,.row_xs");
    $.each(fitsizeRows, function(i, fitsizeRow) {
        addFitSizeColumn($(fitsizeRow));
    });
    $layoutHtml.find(".row")
        .removeClass("clearfix").children()
        .removeClass("column ui-droppable");

    var outPutHtml = $("<div class=\"container\"></div>");
    outPutHtml.append($layoutHtml.html());
    outPutHtml.find('.shine_red').removeClass("shine_red");
    //$layoutHtml.find(".row").each(
    //function(i, item) {
    //	outPutHtml.append(item);
    //});
    //debugger;
    //如果是图标控件 增加图标类型设置chartType=datarole，更改datarole为父控件datarole
    var charts = $(outPutHtml).find("[pdata-role='charts']");
    $.each(charts, function(i, chart) {
        $(chart).attr("chartType", $(chart).attr("data-role"));
        $(chart).attr("id", $(chart).attr("id").replace($(chart).attr("data-role"), "charts"));
        $(chart).attr("data-role", $(chart).attr("pdata-role"));
    });
    var charts = $(outPutHtml).find("[pdata-role='echarts']");
    $.each(charts, function(i, chart) {
        $(chart).attr("chartType", $(chart).attr("data-role"));
        $(chart).attr("id", $(chart).attr("id").replace($(chart).attr("data-role"), "echarts"));
        $(chart).attr("data-role", $(chart).attr("pdata-role"));
    });
    $("#pageLayout").text(outPutHtml.html());
    $layoutHtml = $(".designer").clone();
    outPutHtml.empty();
    outPutHtml.append($layoutHtml.html());
    outPutHtml.find('.shine_red').removeClass("shine_red");
    $("#pageDesigner").text(outPutHtml.html());
}
/**
 *处理适应小尺寸栅格分栏样式
 */
function addFitSizeColumn(obj) {
    //获取所有列
    var colums = obj.find(".column:not(form)");
    var preColumnClass, currClass, currCells, fitClass, gridsys;
    //枚举提高执行效率
    var fitsm = obj.hasClass("row_sm");
    var fitxs = obj.hasClass("row_xs");
    $.each(colums, function(i, item) {
        //获取列栅格样式
        currClass = $(item).attr("class").match((/col\-(md|mmd|24md)\-\d+/))[0];
        //获取列栅格数
        currCells = currClass.match((/\d+$/))[0];
        //获取列栅格样式前缀
        preColumnClass = getGridSysByColumnClass(currClass);
        //获取分栏方式（12、24、48）
        if (currClass.indexOf("col-mmd-") > -1) {
            gridsys = "m";
        } else if (currClass.indexOf("col-24md-") > -1) {
            gridsys = "24";
        } else {
            gridsys = "";
        }
        if (fitsm) {
            fitClass = preColumnClass.substring(0, preColumnClass.indexOf("-") + 1) + gridsys + "sm-" + currCells;
            $(item).addClass(fitClass);
        }
        if (fitxs) {
            fitClass = preColumnClass.substring(0, preColumnClass.indexOf("-") + 1) + gridsys + "xs-" + currCells;
            $(item).addClass(fitClass);
        }
    });
}
//布局控件拖动对象
function initdraggable() {
    $(".layout").draggable({
        connectToSortable: ".designer",
        cursor: "move",
        //snapTolerance:10,
        helper: function(event) {
            //获取当前元素类型
            var elementType = $(this).attr("data_role");
            var pElementType = $(this).attr("pdata_role");
            var scope = $(this).attr("scope");
            var integral = $(this).attr("integral");
            var targetElem;
            /*if ($('#' + elementType)
             && $('#' + elementType).html()) {
             targetElem = $($('#' + elementType).html());
             //获取portlet模板
             //var portletElem=$($('#portlet-template').html());
             //targetElem.find('.column').append(portletElem);
             }
             else if ($.template[elementType]
             && $.template[elementType] != '') {
             targetElem = $($.template[elementType]);

             } else {
             targetElem = $("<img></img>");
             targetElem.attr("src",
             contextPath+"/images/component/"
             + elementType + ".png");
             targetElem.css("vertical-align", "middle");
             }*/
            var elementName = $(this).find('span').text();
            targetElem = $("<div class=\"elemtag\">" + elementName + "</div>");
            //var dtStr = new Date().getTime();
            //targetElem.attr("id", elementType + "_" + dtStr);
            if (pElementType != undefined && pElementType != '') {
                targetElem.attr("pdata-role", pElementType);
            }
            targetElem.attr("data-role", elementType);
            targetElem.attr("integral", integral);
            targetElem.attr("scope", scope);
            //targetElem.attr("crtltype", "kendo");
            return targetElem;
        },
        start: function(e, t) {
            if (!startdrag) stopsave++;
            startdrag = 1;
        },
        drag: function(e, t) {
            //t.helper.width(400);
            //t.helper.find(".view").css("display","none");
        },
        stop: function(e, t) {

            var moveObj = $(t.helper);
            if (moveObj.parent().hasClass("nav-item")) {
                return;
            }
            var elementType = moveObj.attr("data-role");
            var pElementType = moveObj.attr("pdata-role") != undefined ? moveObj.attr("pdata-role") : "";
            var scope = moveObj.attr("scope");
            var integral = moveObj.attr("integral");
            var targetElem, gridlayout;
            if ($('#' + elementType) && $('#' + elementType).html()) {
                targetElem = $($('#' + elementType).html());
                //获取工具按钮组（编辑、删除、拖动）
                gridlayout = $($("#gridlayout").html());
                gridlayout.find(".view").append(targetElem);
                targetElem = gridlayout;
                //获取portlet模板
                //var portletElem=$($('#portlet-template').html());
                //targetElem.find('.column').append(portletElem);
            } else if ($.template[elementType] && $.template[elementType] != '') {
                targetElem = $($.template[elementType]);

            } else {
                targetElem = $("<img></img>");
                targetElem.attr("src",
                    contextPath + "/images/component/" + elementType + ".png");
                targetElem.css("vertical-align", "middle");
            }
            var dtStr = new Date().getTime();
            targetElem.addClass("layout_elem");
            targetElem.attr("id", elementType + "_" + dtStr);
            targetElem.find(".view>.row:first").addClass(elementType + "_" + dtStr);
            targetElem.find(".preview>input").attr("value",
                $(this).find('span').text());
            targetElem.attr("data-role", elementType);
            if (pElementType != '') {
                targetElem.attr("pdata-role", pElementType);
            }
            targetElem.attr("scope", scope);
            targetElem.attr("crtltype", "kendo");
            //替换id样式表
            targetElem.find('.id').addClass(targetElem.attr("id"));
            targetElem.find('.id').removeClass("id");
            //列添加工具栏
            targetElem.find(".column").prepend($("#colconfigTemplate").html());

            //增加自适用高度样式
            if ($("#autoHeightBt").hasClass("green-jungle")) {
                addAutoHeightClass(targetElem);
            }
            moveObj.replaceWith(targetElem);
            initContainer();
            /*$(".designer .lyrow").draggable(
             {
             connectToSortable : ".designer,.column,.containerComp",
             cursor : "move",
             handle: ".drag",
             scroll: false,
             revert:"invalid",
             helper : function(event) {
             currentDragComponent=$(this);
             var elementName=$(this).find('.preview>input:first').val();
             var targetElem=$("<div class=\"elemtag\">"+elementName+"</div>");
             //设置元素位置为当前光标位置
             var position=getMousePos();
             targetElem.css("position","absolute");
             targetElem.offset({left:position.x-(100/2),top:position.y-(60/2)});
             $(this).css("position","absolute");
             $(this).offset({left:position.x-(100/2),top:position.y-(60/2)});
             return targetElem;
             },
             drag : function(e, t) {
             $(this).css("display","none");
             },
             stop: function(e,t) {
             var moveObj=$(t.helper);
             moveObj.replaceWith(currentDragComponent);
             }
             });*/
            changeElemStyle(targetElem);
            if (stopsave > 0) stopsave--;
            startdrag = 0;
            clashCompute();
        }
    });
    //非布局控件拖动对象
    var currentDragComponent;
    $(".elem:not([data_role=form_group])").draggable({
        connectToSortable: ".column,.containerComp,.myformgroup", //启用此属性helper需设置为clone，否则droppable drop会执行两次；后面对droppable drop方法进行了处理
        cursor: "move",
        //snapTolerance:10,
        helper: function(event) {
            //获取当前元素类型
            var elementType = $(this).attr("data_role");
            var pElementType = $(this).attr("pdata_role");
            var scope = $(this).attr("scope");
            var integral = $(this).attr("integral");
            var targetElem;
            /*if ($('#' + elementType)
             && $('#' + elementType).html()) {
             targetElem = $($('#' + elementType).html());
             //获取portlet模板
             //var portletElem=$($('#portlet-template').html());
             //targetElem.find('.column').append(portletElem);
             }
             else if ($.template[elementType]
             && $.template[elementType] != '') {
             targetElem = $($.template[elementType]);

             } else {
             targetElem = $("<img></img>");
             targetElem.attr("src",
             contextPath+"/images/component/"
             + elementType + ".png");
             targetElem.css("vertical-align", "middle");
             }*/
            var elementName = $(this).find('span').text();
            targetElem = $("<div class=\"elemtag\">" + elementName + "</div>");
            //var dtStr = new Date().getTime();
            //targetElem.attr("id", elementType + "_" + dtStr);
            targetElem.attr("data-role", elementType);
            if (pElementType != undefined && pElementType != '') {
                targetElem.attr("pdata-role", pElementType);
            }
            targetElem.attr("scope", scope);
            targetElem.attr("integral", integral);
            //targetElem.attr("crtltype", "kendo");
            return targetElem;
        },
        start: function(e, t) {
            var moveObj = $(t.item);
            beforeDropElemStyle(moveObj);
            if (!startdrag) stopsave++;
            startdrag = 1;
        },
        drag: function(e, t) {
            //t.helper.width(400);
            //t.helper.find(".view").css("display","none");
        },
        stop: function(e, t) {
            var dataId = $(e.target).attr("data_id");
            var moveObj = $(t.helper);
            if (moveObj.parent().hasClass("nav-item")) {
                return;
            }
            //获取当前元素类型
            var elementType = moveObj.attr("data-role");
            var pElementType = moveObj.attr("pdata-role") != undefined ? moveObj.attr("pdata-role") : "";
            var scope = moveObj.attr("scope");
            var integral = moveObj.attr("integral");

            var targetElem;
            if ($('#' + elementType) && $('#' + elementType).html()) {
                targetElem = $($('#' + elementType).html());
            } else if ($.template[elementType] && $.template[elementType] != '') {
                targetElem = $($.template[elementType]);
            } else {
                targetElem = $("<img class='img-responsive'></img>");
                targetElem.attr("src",
                    contextPath + "/images/component/" + elementType + ".png");
                targetElem.css("vertical-align", "middle");
            }
            var dtStr = new Date().getTime();
            var id = elementType + "_" + dtStr;
            targetElem.attr("id", id);

            if (elementType && elementType == 'label') {
                //如果控件为label则默认不生成名称
            } else {
                targetElem.attr("cname", id + '_控件');
            }

            targetElem.addClass(id);
            targetElem.addClass("nlayout_elem");
            if (integral == 1) {
                targetElem.addClass("integral");
            }
            //子控件重新生成ID
            var childComp = targetElem.find("[data-role]");
            var dtStr;
            var nId;
            var oldId;
            $.each(childComp, function(i, item) {
                oldId = $(item).attr("id");
                dtStr = new Date().getTime() + i;
                nId = $(item).attr("data-role") + "_" + dtStr;
                $(item).attr("id", nId);
                //替换class中的id
                var childRetain = $(item).find('.id');
                if (childRetain.length > 0) {
                    $.each(childRetain, function(i, o) {
                        var $id = $(o).closest('[data-role]');
                        if ($id.length > 0 && $id.attr("data-role") == $(item).attr("data-role")) {
                            $(o).addClass($(item).attr("id"));
                            $(o).removeClass("id");
                        }
                    });
                } else {
                    return;
                }
            });
            targetElem.attr("data-role", elementType);
            if (pElementType != '') {
                targetElem.attr("pdata-role", pElementType);
            }
            targetElem.attr("scope", scope);
            targetElem.attr("crtltype", "kendo");
            //元素内容可编辑
            //targetElem.attr("contenteditable","plaintext-only");
            //表单控件组增加工具栏
            //获取工具栏模板
            var toolbar = $($("#formgrouptoolbarTemplate").html());
            targetElem.find(".myformgroup").prepend(toolbar);
            if (t.helper.parent().hasClass('myform') || t.helper.parent().hasClass('myformgroup')) {
                if (t.helper.parent().hasClass('myform') && !targetElem.hasClass("myformgroup")) {
                    moveObj.remove();
                } else {
                    moveObj.replaceWith(targetElem);
                }
            } else {
                if (targetElem.hasClass("myformgroup")) {
                    moveObj.remove();
                }
                //获取元素模板对象
                var elementTemplate = $($('#elementTemplate')
                    .html());
                //替换id样式表
                var retain = targetElem.find('.id');
                if (retain.length > 0) {
                    $.each(retain, function(i, o) {
                        var $id = $(o).closest('[data-role]');
                        if ($id.length > 0 && $id.attr("data-role") == elementType) {
                            $(o).addClass(targetElem.attr("id"));
                            $(o).removeClass("id");
                        }
                    });
                }

                elementTemplate.find(".view").attr("id", id + "_view");
                elementTemplate.find(".view").append(targetElem);
                elementTemplate.find(".preview>input").attr("value",
                    $(this).find('span').text());
                elementTemplate.attr("id", elementType + "_" + dtStr + "_ct");
                moveObj.replaceWith(elementTemplate);
                changeElemStyle(elementTemplate);
            }

            draggEventBind();
            initContainer();
            if (stopsave > 0) stopsave--;
            startdrag = 0;
            clashCompute();

            //控件初始渲染
            /* $.ajax({
                url: contextPath + '/service/designer/' + dataId + '/getTemplateRender',
                type: "post",
                dataType: 'json',
                success: function (data) {
                    if (data.template) {
                        eval(data.template);
                    }
                },
                error: function (xhr, ts) {
                    console.log('控件渲染失败！');
                }
            }); */
            compRenderMethod.render(id, dataId);
        }
    });

    $(".elem[data_role=form_group]").draggable({
        connectToSortable: ".myform", //启用此属性helper需设置为clone，否则droppable drop会执行两次；后面对droppable drop方法进行了处理
        cursor: "move",
        //snapTolerance:10,
        helper: function(event) {
            //获取当前元素类型
            var elementType = $(this).attr("data_role");
            var pElementType = $(this).attr("pdata_role");
            var scope = $(this).attr("scope");
            var integral = $(this).attr("integral");
            var targetElem;
            /*if ($('#' + elementType)
             && $('#' + elementType).html()) {
             targetElem = $($('#' + elementType).html());
             //获取portlet模板
             //var portletElem=$($('#portlet-template').html());
             //targetElem.find('.column').append(portletElem);
             }
             else if ($.template[elementType]
             && $.template[elementType] != '') {
             targetElem = $($.template[elementType]);

             } else {
             targetElem = $("<img></img>");
             targetElem.attr("src",
             contextPath+"/images/component/"
             + elementType + ".png");
             targetElem.css("vertical-align", "middle");
             }*/
            var elementName = $(this).find('span').text();
            targetElem = $("<div class=\"elemtag\">" + elementName + "</div>");
            //var dtStr = new Date().getTime();
            //targetElem.attr("id", elementType + "_" + dtStr);
            targetElem.attr("data-role", elementType);
            if (pElementType != undefined && pElementType != '') {
                targetElem.attr("pdata-role", pElementType);
            }
            targetElem.attr("scope", scope);
            targetElem.attr("integral", integral);
            //targetElem.attr("crtltype", "kendo");
            return targetElem;
        },
        start: function(e, t) {
            var moveObj = $(t.item);
            beforeDropElemStyle(moveObj);
            if (!startdrag) stopsave++;
            startdrag = 1;
        },
        drag: function(e, t) {
            //t.helper.width(400);
            //t.helper.find(".view").css("display","none");
        },
        stop: function(e, t) {
            var dataId = $(e.target).attr("data_id");
            var moveObj = $(t.helper);
            if (moveObj.parent().hasClass("nav-item")) {
                return;
            }
            //获取当前元素类型
            var elementType = moveObj.attr("data-role");
            var pElementType = moveObj.attr("pdata-role") != undefined ? moveObj.attr("pdata-role") : "";
            var scope = moveObj.attr("scope");
            var integral = moveObj.attr("integral");

            var targetElem;
            if ($('#' + elementType) && $('#' + elementType).html()) {
                targetElem = $($('#' + elementType).html());
            } else if ($.template[elementType] && $.template[elementType] != '') {
                targetElem = $($.template[elementType]);
            } else {
                targetElem = $("<img class='img-responsive'></img>");
                targetElem.attr("src",
                    contextPath + "/images/component/" + elementType + ".png");
                targetElem.css("vertical-align", "middle");
            }
            //获取工具栏模板
            var toolbar = $($("#formgrouptoolbarTemplate").html());
            targetElem.prepend(toolbar);
            var dtStr = new Date().getTime();
            var id = elementType + "_" + dtStr;
            targetElem.attr("id", id);
            targetElem.attr("cname", id + '_控件');
            targetElem.addClass(id);
            targetElem.addClass("nlayout_elem");
            if (integral == 1) {
                targetElem.addClass("integral");
            }
            //子控件重新生成ID
            var childComp = targetElem.find("[data-role]");
            var dtStr;
            var nId;
            var oldId;
            $.each(childComp, function(i, item) {
                oldId = $(item).attr("id");
                dtStr = new Date().getTime() + i;
                nId = $(item).attr("data-role") + "_" + dtStr;
                $(item).attr("id", nId);
            });
            targetElem.attr("data-role", elementType);
            if (pElementType != '') {
                targetElem.attr("pdata-role", pElementType);
            }
            targetElem.attr("scope", scope);
            targetElem.attr("crtltype", "kendo");
            //元素内容可编辑
            //targetElem.attr("contenteditable","plaintext-only");
            moveObj.replaceWith(targetElem);
            changeElemStyle(targetElem);
            initContainer();
            draggEventBind();
            if (stopsave > 0) stopsave--;
            startdrag = 0;
            clashCompute();

            /* //控件初始渲染
            $.ajax({
                url: contextPath + '/service/designer/' + dataId + '/getTemplateRender',
                type: "post",
                dataType: 'json',
                success: function (data) {
                    if (data.template) {
                        eval(data.template);
                    }
                },
                error: function (xhr, ts) {
                    console.log('控件渲染失败！');
                }
            }); */
            compRenderMethod.render(id, dataId);
        }
    });

    $(".template").draggable({
        connectToSortable: ".column", //启用此属性helper需设置为clone，否则droppable drop会执行两次；后面对droppable drop方法进行了处理
        cursor: "move",
        //snapTolerance:10,
        helper: function(event) {
            //获取当前元素类型
            var elementType = "template";
            var targetElem;
            /*if ($('#' + elementType)
             && $('#' + elementType).html()) {
             targetElem = $($('#' + elementType).html());
             //获取portlet模板
             //var portletElem=$($('#portlet-template').html());
             //targetElem.find('.column').append(portletElem);
             }
             else if ($.template[elementType]
             && $.template[elementType] != '') {
             targetElem = $($.template[elementType]);

             } else {
             targetElem = $("<img></img>");
             targetElem.attr("src",
             contextPath+"/images/component/"
             + elementType + ".png");
             targetElem.css("vertical-align", "middle");
             }*/
            var elementName = $(this).find('.templatetitle').text();
            targetElem = $("<div class=\"elemtag\">" + elementName + "</div>");
            //var dtStr = new Date().getTime();
            //targetElem.attr("id", elementType + "_" + dtStr);
            targetElem.attr("data-role", elementType);
            targetElem.attr("id", $(this).attr("id"));
            //targetElem.attr("crtltype", "kendo");
            return targetElem;
        },
        start: function(e, t) {
            var moveObj = $(t.item);
            beforeDropElemStyle(moveObj);
            if (!startdrag) stopsave++;
            startdrag = 1;
        },
        drag: function(e, t) {
            //t.helper.width(400);
            //t.helper.find(".view").css("display","none");
        },
        stop: function(e, t) {
            var moveObj = $(t.helper);
            if (moveObj.parent().hasClass("nav-item")) {
                return;
            }

            var targetElem = $("<div></div>");
            targetElem.append($("#template_" + moveObj.attr("id")).html());
            //子控件重新生成ID
            var childComp = targetElem.find("[data-role]");
            var dtStr;
            var nId;
            var oldId;
            $.each(childComp, function(i, item) {
                oldId = $(item).attr("id");
                dtStr = new Date().getTime() + i;
                nId = $(item).attr("data-role") + "_" + dtStr;
                $(item).attr("id", nId);
                targetElem = updateIdCss(targetElem, oldId, nId);
            });

            //布局控件补充工具栏
            var layoutComps = targetElem.find(".layout_elem");
            //获取布局控件工具栏模板（编辑、删除、拖动）
            var gridlayout;

            var preview;
            $.each(layoutComps, function(i, layoutComp) {
                gridlayout = $($("#gridlayout_toolbar").html());
                preview = $(layoutComp).find(".preview:first");
                //行添加工具栏
                preview.before(gridlayout);
            });
            //获取列工具栏模板（编辑、删除、拖动）
            var colconfigTemplate = $("#colconfigTemplate").html()
                //列添加工具栏
            $(targetElem).find(".column").prepend(colconfigTemplate);
            //非布局控件补充工具栏
            var elemComps = targetElem.find(".nlayout_elem");
            //获取元素模板对象
            var elementTemplate, compId, elemCompClone;
            $.each(elemComps, function(i, elemComp) {
                elemCompClone = $(elemComp).clone();
                elementType = elemCompClone.attr("data-role");
                dtStr = new Date().getTime() + i;
                compId = elemCompClone.attr("id");
                //元素内容可编辑
                //$(elemComp).attr("contenteditable","plaintext-only");
                if (!t.helper.parent().hasClass('myform') && !t.helper.parent().hasClass('myformgroup')) {
                    elementTemplate = $($('#elementTemplate')
                        .html());
                    elementTemplate.find(".view").attr("id", compId + "_view");
                    elementTemplate.find(".view").append(elemCompClone);
                    elementTemplate.find(".preview>input").attr("value", $(this).find('span').text());
                    elementTemplate.attr("id", elementType + "_" + dtStr + "_ct");
                    //元素替换
                    $(elemComp).replaceWith(elementTemplate);
                }
            });
            //表单控件组增加工具栏
            //获取工具栏模板
            var toolbar = $($("#formgrouptoolbarTemplate").html());
            targetElem.find(".myformgroup").prepend(toolbar);
            var desObj = $(targetElem.html());
            moveObj.replaceWith(desObj);
            initContainer();
            changeElemStyle(desObj);


            /**
                 //获取当前元素类型
                 var elementType = moveObj.attr("data-role");
                 //获取元素模板对象
                 var elementTemplate = $($('#elementTemplate')
                 .html());
                 var targetElem=$($("#template_"+moveObj.attr("id")).html());
                 var dtStr = new Date().getTime();
                 var id = elementType + "_" + dtStr;
                 targetElem.attr("id", id);
                 targetElem.attr("cname", id+'_控件');
                 targetElem.addClass(id);
                 targetElem.addClass("nlayout_elem");
                 //子控件重新生成ID
                 var childComp=targetElem.find("[data-role]");
                 var dtStr;
                 var nId;
                 var oldId;
                 $.each(childComp,function(i,item){
							oldId=$(item).attr("id");
							dtStr = new Date().getTime();
						    nId=$(item).attr("data-role")+"_"+dtStr;
						    $(item).attr("id",nId);
						});
                 targetElem.attr("data-role", elementType);
                 //替换id样式表
                 targetElem.find('.id').addClass(targetElem.attr("id"));
                 targetElem.find('.id').removeClass("id");
                 elementTemplate.find(".view").attr("id",id+"_view");
                 elementTemplate.find(".view").append(targetElem);
                 elementTemplate.find(".preview").html(
                 $(this).find('span').text());
                 elementTemplate.attr("id", elementType + "_"
                 + dtStr + "_ct");
                 elementTemplate.find(".view").append(targetElem);
                 elementTemplate.find(".preview").html(
                 $(this).find('span').text());
                 //元素内容可编辑
                 targetElem.attr("contenteditable","true");
                 moveObj.replaceWith(elementTemplate);
                 changeElemStyle(elementTemplate);
                 initContainer();*/
            draggEventBind();
            if (stopsave > 0) stopsave--;
            startdrag = 0;
        }
    });

    $("#ztree li a :first-child:not([style*=folder_page_blue])").closest("a").draggable({
        connectToSortable: ".column,.containerComp,.myformgroup,.designer",
        cursor: "move",
        //snapTolerance:10,
        helper: function(event) {
            var targetElem = $(this).closest("li").clone();
            return targetElem;
        },
        start: function(e, t) {
            var moveObj = $(t.item);
            beforeDropElemStyle(moveObj);
            if (!startdrag) stopsave++;
            startdrag = 1;
        },
        drag: function(e, t) {

        },
        stop: function(e, t) {
            var moveObj = $(t.helper);
            var $toEle = $(e.toElement),
                eleId = $toEle.attr("id"),
                ztreeObj = $.fn.zTree.getZTreeObj("ztree"),
                node = ztreeObj.getNodeByTId(eleId),
                nodeId = node.id;
            $.ajax({
                url: contextPath + '/mx/form/defined/getFormDesignHtmlById?id=' + nodeId,
                type: "post",
                async: false,
                contentType: "application/json",
                success: function(rdata) {
                    if (rdata) {
                        var $rdata = $(rdata);
                        $rdata.find(".view:first>div:first-child").attr("data-templateId", nodeId);
                        $toEle.after($rdata);
                        $toEle.remove();
                    }
                },
                error: function(e) {
                    console.log(e);
                }
            });

            draggEventBind();
            initContainer();
            if (stopsave > 0) stopsave--;
            startdrag = 0;
            clashCompute();

        }
    });

    draggEventBind();
}

function draggEventBind() {
    $(".designer .mybox").draggable({
        connectToSortable: ".column,.containerComp",
        cursor: "move",
        handle: ".drag",
        scroll: false,
        revert: "invalid",
        helper: function(event) {
            currentDragComponent = $(this);
            var elementName = $(this).find('.preview>input:first').val();
            var targetElem = $("<div class=\"elemtag\">" + elementName + "</div>");
            //设置元素位置为当前光标位置
            var position = getMousePos();
            targetElem.css("position", "absolute");
            targetElem.offset({
                left: position.x - (100 / 2),
                top: position.y - (60 / 2)
            });
            $(this).css("position", "absolute");
            $(this).offset({
                left: position.x - (100 / 2),
                top: position.y - (60 / 2)
            });
            return targetElem;
        },
        drag: function(e, t) {
            $(this).css("display", "none");
        },
        stop: function(e, t) {
            var moveObj = $(t.helper);
            moveObj.replaceWith(currentDragComponent);
            changeElemStyle(currentDragComponent);
        }
    });
    $(".designer .myformgroup").draggable({
        connectToSortable: ".myform",
        cursor: "move",
        scroll: false,
        revert: "invalid",
        helper: function(event) {
            currentDragComponent = $(this);
            var targetElem = $("<div class=\"elemtag\">表单控件组</div>");
            //设置元素位置为当前光标位置
            var position = getMousePos();
            targetElem.css("position", "absolute");
            targetElem.offset({
                left: position.x - (100 / 2),
                top: position.y - (60 / 2)
            });
            $(this).css("position", "absolute");
            $(this).offset({
                left: position.x - (100 / 2),
                top: position.y - (60 / 2)
            });
            return targetElem;
        },
        drag: function(e, t) {
            $(this).css("display", "none");
        },
        stop: function(e, t) {
            var moveObj = $(t.helper);
            moveObj.replaceWith(currentDragComponent);
            currentDragComponent.attr("style", "");
        }
    });
    /*$(".designer .lyrow").draggable(
     {
     connectToSortable : ".designer,.column,.containerComp",
     cursor : "move",
     handle: ".drag",
     scroll: false,
     revert:"invalid",
     helper : function(event) {
     currentDragComponent=$(this);
     var elementName=$(this).find('.preview>input:first').val();
     var targetElem=$("<div class=\"elemtag\">"+elementName+"</div>");
     //设置元素位置为当前光标位置
     var position=getMousePos();
     targetElem.css("position","absolute");
     targetElem.offset({left:position.x-(100/2),top:position.y-(60/2)});
     $(this).css("position","absolute");
     $(this).offset({left:position.x-(100/2),top:position.y-(60/2)});
     return targetElem;
     },
     drag : function(e, t) {
     $(this).css("display","none");
     },
     stop: function(e,t) {
     var moveObj=$(t.helper);
     moveObj.replaceWith(currentDragComponent);
     }
     });*/
    $(".designer .myformgroup").children(":not(.formgrouptoolbar)").draggable({
        connectToSortable: ".myformgroup",
        cursor: "move",
        scroll: false,
        revert: "invalid",
        helper: function(event) {
            currentDragComponent = $(this);
            var targetElem = $("<div class=\"elemtag\">表单控件</div>");
            //设置元素位置为当前光标位置
            var position = getMousePos();
            targetElem.css("position", "absolute");
            targetElem.offset({
                left: position.x - (100 / 2),
                top: position.y - (60 / 2)
            });
            $(this).css("position", "absolute");
            $(this).offset({
                left: position.x - (100 / 2),
                top: position.y - (60 / 2)
            });
            return targetElem;
        },
        drag: function(e, t) {
            $(this).css("display", "none");
        },
        stop: function(e, t) {
            var moveObj = $(t.helper);
            moveObj.replaceWith(currentDragComponent);
            currentDragComponent.attr("style", "");
        }
    });
}

function cleanHtml(e) {
    $(e).after($(e).children().html());
    $(e).children().remove();
}
//撤销
$('#undoBt').click(function() {
    stopsave++;
    if (undoLayout()) initContainer();
    draggEventBind();
    stopsave--;
    clashCompute();
});
//重做
$('#redoBt').click(function() {
    stopsave++;
    if (redoLayout()) initContainer();
    draggEventBind();
    stopsave--;
    clashCompute();
});
//ui查询绑定事件
$('#ui-select').change(function() {
    //异步获取元素列表
    initMenu(false, $("#ui-select").val());
    initdraggable();
});
//记录操作轨迹
var timerSave = 1000;
var stopsave = 0;
var startdrag = 0;
var designerHtml = $(".designer").html();
//判断当前环境是否支持客户端缓存
function supportstorage() {
    if (typeof window.localStorage == 'object')
        return true;
    else
        return false;
}
//保存页面布局入口方法
function handleSaveLayout() {
    var e = $(".designer").html();
    if (!stopsave && e != window.designerHtml) {
        stopsave++;
        window.designerHtml = e;
        saveLayout();
        stopsave--;
    }
}

var layouthistory;
//保存页面布局 方法
function saveLayout() {
    var data = layouthistory;
    if (!data) {
        data = {};
        data.count = 0;
        data.list = [];
    }
    if (data.list.length > data.count) {
        for (i = data.count; i < data.list.length; i++)
            data.list[i] = null;
    }
    data.list[data.count] = window.designerHtml;
    data.count++;
    if (supportstorage()) {
        try {
            localStorage
                .setItem("layoutdata", JSON.stringify(data));
        } catch (e) {

        }
    }
    layouthistory = data;
}
//撤销方法实现
function undoLayout() {
    var data = layouthistory;
    if (data) {
        if (data.count < 2)
            return false;
        window.designerHtml = data.list[data.count - 2];
        data.count--;
        $('.designer').html(window.designerHtml);
        if (supportstorage()) {
            try {
                localStorage
                    .setItem("layoutdata", JSON.stringify(data));
            } catch (e) {

            }
        }
        return true;
    }
    return false;
}
//重做方法实现
function redoLayout() {
    var data = layouthistory;
    if (data) {
        if (data.list[data.count]) {
            window.designerHtml = data.list[data.count];
            data.count++;
            $('.designer').html(window.designerHtml);
            if (supportstorage()) {
                try {
                    localStorage
                        .setItem("layoutdata", JSON.stringify(data));
                } catch (e) {

                }
            }
            return true;
        }
    }
    return false;
}
//方法1
function mousePos(e) {
    var x, y;
    var e = e || window.event;
    return {
        x: e.clientX + document.body.scrollLeft + document.documentElement.scrollLeft,
        y: e.clientY + document.body.scrollTop + document.documentElement.scrollTop
    };
};

//方法2
//Firefox支持属性pageX,与pageY属性，这两个属性已经把页面滚动计算在内了,
//在Chrome可以通过document.body.scrollLeft，document.body.scrollTop计算出页面滚动位移，
//而在IE下可以通过document.documentElement.scrollLeft ，document.documentElement.scrollTop
function getMousePos(event) {
    var e = event || window.event;
    var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
    var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
    var x = e.pageX || e.clientX + scrollX;
    var y = e.pageY || e.clientY + scrollY;
    //alert('x: ' + x + '\ny: ' + y);
    return {
        'x': x,
        'y': y
    };
}
//初始化容器
function initContainer(pageload) {
    $(".designer,.designer .column,.designer .containerComp").sortable({
        connectWith: ".designer,.column,.containerComp",
        opacity: .35,
        handle: ".drag",
        placeholder: "holderCss",
        cursor: "move",
        //distance:20,
        //tolerance:"pointer",
        cursorAt: { //指定拖动条目时，光标出现的位置，取值是对象，可以包含top、left、right、Bottom属性中的一个或两个。 默认值 false
            top: 5,
            right: 125
        },
        start: function(e, t) {
            if ($(".holderCss")) {
                $(".holderCss").text("可拖入元素");
            }
            var moveObj = $(t.item);
            //t.helper.height(100);
            if (!moveObj.hasClass('elemtag')) {
                t.helper.width(400);
                beforeDropElemStyle(moveObj);
            }
            if (!startdrag) stopsave++;
            startdrag = 1;
        },
        stop: function(e, t) {
            var moveObj = $(t.item);
            var contId = moveObj.attr("id");
            changeElemStyle(moveObj);
            if (stopsave > 0) stopsave--;
            startdrag = 0;
        },
        deactivate: function(e, t) {
            var moveObj = $(t.item);
            changeElemStyle(moveObj);
        }
    });
    $(".designer .myform").sortable({
        connectWith: ".myform",
        opacity: .35,
        handle: ".formgrouptoolbar",
        placeholder: "holderCss",
        cursor: "move",
        //distance:20,
        //tolerance:"pointer",
        cursorAt: { //指定拖动条目时，光标出现的位置，取值是对象，可以包含top、left、right、Bottom属性中的一个或两个。 默认值 false
            top: 5,
            right: 125
        },
        start: function(e, t) {
            if ($(".holderCss")) {
                $(".holderCss").text("可拖入元素");
            }
            var moveObj = $(t.item);
            //t.helper.height(100);
            if (!moveObj.hasClass('elemtag')) {
                t.helper.width(400);
                beforeDropElemStyle(moveObj);
            }
            if (!startdrag) stopsave++;
            startdrag = 1;
        },
        stop: function(e, t) {
            var moveObj = $(t.item);
            var contId = moveObj.attr("id");
            if (stopsave > 0) stopsave--;
            startdrag = 0;
        },
        deactivate: function(e, t) {
            var moveObj = $(t.item);
            changeElemStyle(moveObj);
        }
    });
    $(".designer .myformgroup").sortable({
        connectWith: ".myformgroup",
        opacity: .35,
        placeholder: "holderCss",
        cursor: "move",
        //distance:2,
        cancel: ".formgrouptoolbar",
        //tolerance:"intersect",
        start: function(e, t) {
            if ($(".holderCss")) {
                $(".holderCss").text("可拖入元素");
            }
            var moveObj = $(t.item);
            //设置元素位置为当前光标位置
            var position = getMousePos();
            moveObj.css("position", "absolute");
            moveObj.offset({
                left: position.x,
                top: position.y
            });
            //t.helper.height(100);
            if (!moveObj.hasClass('elemtag')) {
                //moveObj.width(50);
                beforeDropElemStyle(moveObj);
            }
            if (!startdrag) stopsave++;
            startdrag = 1;
        },
        stop: function(e, t) {
            var moveObj = $(t.item);
            var contId = moveObj.attr("id");
            changeElemStyle(moveObj);
            if (stopsave > 0) stopsave--;
            startdrag = 0;
        },
        deactivate: function(e, t) {
            var moveObj = $(t.item);
            changeElemStyle(moveObj);
        }
    });
    //检测当前页面是否为高度自适应模式
    if (pageload == undefined && $("#autoHeightBt").hasClass("green-jungle")) {
        changeRowAutoHeightCss(true);
    }
}
//修改元素拖入前的样式
function beforeDropElemStyle(moveObj) {
    moveObj.find(".preview").css('display', 'block');
    moveObj.find(".view").css('display', 'none');
}
//修改元素拖入后的样式
function changeElemStyle(moveObj) {
    //记录历史的display样式
    var oldDisplay = moveObj.css("display");
    moveObj.attr('style', '');
    if (oldDisplay == 'none' || oldDisplay == '') {
        moveObj.css('display', 'block');
    }
    moveObj.find(".preview").css('display', 'none');
    moveObj.find('.view').attr('style', '');
}
//历史适用UI
var hisUiList;
//计算当前设计中是否存在UI冲突
function clashCompute() {

    hisUiList = $(".ui-list").html();
    var scopeArray = [];
    var scope;
    var array;
    $(".designer").find("[scope]").each(function(i, item) {
        scope = $(item).attr("scope");
        array = scope.split(',');
        if (scope && scope != "") {
            if (i == 0)
                scopeArray = array;
            else
                scopeArray = Array.intersect(scopeArray, array);
        }
    });
    $(".ui-list").empty();
    if (array && array.length > 0 && (scopeArray == null || scopeArray.length == 0) && hisUiList.trim().length > 0) {
        //显示内容
        $('.modalcontainer').removeClass("hidemy-modal");
        $('.modalcontainer').addClass("my-modal");
        $('#warningModal').modal('show');
    } else {
        $(scopeArray).each(function(i, item) {
            $(".ui-list").append("<li class=\"glyphicon glyphicon-edit\">" + item.toLocaleUpperCase() + "</li>");
        });
    }
    //$('#warningModal').modal('show');
}
//警告框显示时调用事件
$('#warningModal').on('show.bs.modal', function() {
    $(this).find(".modal-body").html("<p>新增控件与页面控件存在冲突,是否继续？</p>");
});
//关闭警告框
$('.modalClose').click(function() {
    stopsave++;
    if (undoLayout()) initContainer();
    draggEventBind();
    stopsave--;
    $(".ui-list").html(hisUiList);
    $('.modalcontainer').removeClass("my-modal");
    $('.modalcontainer').addClass("hidemy-modal");
});
//关闭警告框
$('.modalContinue').click(function() {
    $('#warningModal').modal('hide');
    $('.modalcontainer').removeClass("my-modal");
    $('.modalcontainer').addClass("hidemy-modal");
});
/**
 * 求两个集合的交集
 * @param {Array} a 集合A
 * @param {Array} b 集合B
 * @returns {Array} 两个集合的交集
 */
Array.intersect = function(a, b) {
    var result = [];
    for (var i = 0; i < b.length; i++) {
        var temp = b[i];
        for (var j = 0; j < a.length; j++) {
            if (temp === a[j]) {
                result.push(temp);
                break;
            }
        }
    }
    return result.uniquelize();
};
/*
 *数组去重复
 */
Array.prototype.uniquelize = function() { // 去重
        var r = [];
        for (var i = 0; i < this.length; i++) {
            var flag = true;
            var temp = this[i];
            for (var j = 0; j < r.length; j++) {
                if (temp === r[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                r.push(temp);
            }
        }
        return r;
    }
    /*
     *提示框
     */
$.alert = function(type, msg) {
    var alertDom = $("<div style=\"display:none;\"  class=\"alert alert-dismissable myalert\"></div>");
    alertDom.append("<button class=\"close\" aria-hidden=\"true\" type=\"button\" data-dismiss=\"alert\">×</button>");
    if (type == 1) {
        alertDom.addClass("alert-danger");

    } else {
        alertDom.addClass("alert-success");
    }
    alertDom.append("<h4>	<i class=\"icon fa fa-check\"></i> 操作提示!</h4>");
    alertDom.append(msg);
    $("body").append(alertDom);
    $(".myalert").fadeIn(500).delay(1000).fadeOut(500);;
}

/**
 * 编辑按钮绑定点击事件
 *
 */
var currentComponent;
$(".designer").delegate(".editBt", "click", function() {
    currentComponent = $(this).parent().parent().parent().parent();
    $(".shine_red").removeClass("shine_red");
    currentComponent.addClass("shine_red");
    //获取控件属性
    var dataRole = currentComponent.attr("data-role");
    if (dataRole == undefined || dataRole == null) {
        var children = currentComponent.children('.view').children();
        if (children.length > 0) {
            if ($(children).attr("data-role") && $.trim($(children).attr("data-role")) != '') {
                currentComponent = children;
            } else {
                currentComponent = $(children).find("[data-role]:first");
            }
            dataRole = currentComponent.attr("data-role");
        } else {
            dataRole = "__unknown__";
        }
    }
    //动态加载js
    var jsId = dataRole + "js";
    var js = "<script id='" + jsId + "' type='text/javascript' src='" + contextPath + "/scripts/designer/components/" + dataRole + ".js'></script>";
    if (currentComponent.hasClass("layout_elem")) {
        jsId = "coljs";
        js = "<script id='" + jsId + "' type='text/javascript' src='" + contextPath + "/scripts/designer/components/col.js'></script>";
    }
    if ($("#" + jsId).length == 0) {
        $("body").append(js);
    }
    var pDataRole = currentComponent.attr("pdata-role");
    if (pDataRole) {
        //若存在父控件，则获取父控件的js信息
        jsId = pDataRole + "js";
        if (pDataRole != undefined && pDataRole != '') {
            js = "<script id='" + jsId + "' type='text/javascript' src='" + contextPath + "/scripts/designer/components/" + pDataRole + ".js'></script>";
        }
        if ($("#" + jsId).length == 0) {
            $("body").append(js);
        }
    }

    //$.getScript(contextPath+"/scripts/designer/components/col.js").done(function() {
    //$.cookie("cookie_name", "value", { expires: 7 });
    //});
    //清空工具栏
    $("#quick_sidebar_tab_1").find(".media-list").empty();
    $("#quick_sidebar_tab_2").find(".mt-list-container").empty();
    $("#quick_sidebar_tab_3").find(".mt-list-container").empty();
    //加载工具栏
    $
        .Toolbox(
            $("#quick_sidebar_tab_1").find(".media-list"), {
                "contextPath": contextPath
            }, {
                "type": "template"
            });
    $
        .Toolbox(
            $("#quick_sidebar_tab_2").find(".mt-list-container"), {
                "contextPath": contextPath
            }, {
                "type": "setting"
            }, "initSideBar,openSideBar");

    $
        .Toolbox(
            $("#quick_sidebar_tab_3").find(".mt-list-container"), {
                "contextPath": contextPath
            }, {
                "type": "static"
            }, "initSideBar,openSideBar");

});

/**
 * 双击弹出编辑窗口
 *
 */
$(".designer").delegate("[data-role]", "dblclick", function(e) {
    //阻止冒泡
    setCommonStyleDataByComponent($(this));
    //去掉选中的控件
    $(".selectedComp").removeClass("selectedComp");
    e.stopPropagation();
    currentComponent = $(this);
    $(".shine_red").removeClass("shine_red");
    currentComponent.addClass("shine_red");
    //获取控件属性
    var dataRole = currentComponent.attr("data-role");
    //动态加载js
    var jsId = dataRole + "js";
    var js = "<script id='" + jsId + "' type='text/javascript' src='" + contextPath + "/scripts/designer/components/" + dataRole + ".js'></script>";
    if (currentComponent.hasClass("layout_elem")) {
        jsId = "coljs";
        js = "<script id='" + jsId + "' type='text/javascript' src='" + contextPath + "/scripts/designer/components/col.js'></script>";
    }
    if ($("#" + jsId).length == 0) {
        $("body").append(js);
    }
    var pDataRole = currentComponent.attr("pdata-role") != undefined ? currentComponent.attr("pdata-role") : '';
    if (pDataRole != '') {
        jsId = pDataRole + "js";
        if (pDataRole != undefined && pDataRole != '') {
            js = "<script id='" + jsId + "' type='text/javascript' src='" + contextPath + "/scripts/designer/components/" + pDataRole + ".js'></script>";
        }
        if ($("#" + jsId).length == 0) {
            $("body").append(js);
        }
    }
    //$.getScript(contextPath+"/scripts/designer/components/col.js").done(function() {
    //$.cookie("cookie_name", "value", { expires: 7 });
    //});
    //清空工具栏
    $("#quick_sidebar_tab_1").find(".media-list").empty();
    $("#quick_sidebar_tab_2").find(".mt-list-container").empty();
    $("#quick_sidebar_tab_3").find(".mt-list-container").empty();
    //加载工具栏
    $
        .Toolbox(
            $("#quick_sidebar_tab_1").find(".media-list"), {
                "contextPath": contextPath
            }, {
                "type": "template"
            });
    $
        .Toolbox(
            $("#quick_sidebar_tab_2").find(".mt-list-container"), {
                "contextPath": contextPath
            }, {
                "type": "setting"
            }, "initSideBar,openSideBar");

    $
        .Toolbox(
            $("#quick_sidebar_tab_3").find(".mt-list-container"), {
                "contextPath": contextPath
            }, {
                "type": "static"
            }, "initSideBar,openSideBar");


    //判断是否可以设置模拟数据，不可设置时禁用。
    var $component = $(this);
    if ($component[0]) {
        var dataRole = $component.attr("data-role");
        var ctrImpl = "showStaticDataPanle";
        ctrImpl = dataRole != "" ? eval(dataRole + "_designer." + ctrImpl) : eval(ctrImpl);
        if (!$.isFunction(ctrImpl)) {
            $("#setStaticData").attr("disabled", "disabled");
        } else {
            $("#setStaticData").removeAttr("disabled");
        }
    }
});
/**
 *栅格列双击事件
 */
$(".designer").delegate(".column", "dblclick", function(e) {
    //阻止冒泡
    e.stopPropagation();
    currentComponent = $(this);
    $(".shine_red").removeClass("shine_red");
    currentComponent.addClass("shine_red");
    //获取控件属性
    var dataRole = "div";
    //动态加载js
    var jsId = "divjs";
    var js = "<script id='" + jsId + "' type='text/javascript' src='" + contextPath + "/scripts/designer/components/" + dataRole + ".js'></script>";
    if ($("#" + jsId).length == 0) {
        $("body").append(js);
    }
    //$.getScript(contextPath+"/scripts/designer/components/col.js").done(function() {
    //$.cookie("cookie_name", "value", { expires: 7 });
    //});
    //清空工具栏
    $("#quick_sidebar_tab_1").find(".media-list").empty();
    $("#quick_sidebar_tab_2").find(".mt-list-container").empty();
    //加载工具栏
    $
        .Toolbox(
            $("#quick_sidebar_tab_1").find(".media-list"), {
                "contextPath": contextPath
            }, {
                "type": "template"
            });
    $
        .Toolbox(
            $("#quick_sidebar_tab_2").find(".mt-list-container"), {
                "contextPath": contextPath
            }, {
                "type": "setting",
                "dataRole": "div"
            }, "initSideBar,openSideBar");

});

/**
 *设计器双击事件 弹出页面全局样式编辑
 */
$(".designer").on("dblclick", function(e) {
    //阻止冒泡
    e.stopPropagation();
    currentComponent = $(this);
    $(".shine_red").removeClass("shine_red");
    currentComponent.addClass("shine_red");
    //获取控件属性
    var dataRole = "global";
    //动态加载js
    var jsId = "global";
    var js = "<script id='" + jsId + "' type='text/javascript' src='" + contextPath + "/scripts/designer/components/" + dataRole + ".js'></script>";
    if ($("#" + jsId).length == 0) {
        $("body").append(js);
    }
    //$.getScript(contextPath+"/scripts/designer/components/col.js").done(function() {
    //$.cookie("cookie_name", "value", { expires: 7 });
    //});
    //清空工具栏
    $("#quick_sidebar_tab_1").find(".media-list").empty();
    $("#quick_sidebar_tab_2").find(".mt-list-container").empty();
    //加载工具栏
    $
        .Toolbox(
            $("#quick_sidebar_tab_1").find(".media-list"), {
                "contextPath": contextPath
            }, {
                "type": "template"
            });
    $
        .Toolbox(
            $("#quick_sidebar_tab_2").find(".mt-list-container"), {
                "contextPath": contextPath
            }, {
                "type": "setting"
            }, "initSideBar,openSideBar");

});

$(".designer").delegate(".integral", "click", function() {
    //调用编辑按钮的点击事件
    var mybox = $(this).closest(".mybox");
    var editBt = mybox.find(".configuration:first").find(".editBt");
    editBt.trigger("click");
});
/**
 *划分栏目按钮绑定点击事件
 *
 */
$(".designer").delegate(".divideBt", "click", function() {
    currentComponent = $(this).parent().parent().parent().parent();

    //获取行
    var row = currentComponent.find(".view:first").find(".row:first");
    //获取当前栅格系统
    var gridsys = $(row.children()[0]).attr("class").match((/col\-(md|mmd|24md)\-\d+/))[0];
    if (gridsys.indexOf("col-mmd-") > -1) {
        gridsys = "48";
    } else if (gridsys.indexOf("col-24md-") > -1) {
        gridsys = "24";
    } else {
        gridsys = "12";
    }
    //设置选中栅格系统
    $('#divideForm input[name="gridsys"][value="' + gridsys + '"]').attr("checked", true);
    $("#groupNumber").attr("max", gridsys);
    $("#groupNumber").trigger("touchspin.updatesettings", {
        max: parseInt(gridsys)
    });
    //获取当前列数
    var currGroups = row.children().length;
    //栅格样式
    var currClass;
    //组栅格数方案
    var groupdivide;
    //初始化分组数
    $("#groupNumber").val(currGroups);
    $.each(row.children(), function(i, item) {
        currClass = $(item).attr("class").match((/col\-(md|mmd|24md)\-\d+/))[0];
        if (i == 0)
            groupdivide = currClass.match(/\d+$/)[0];
        else
            groupdivide += "," + currClass.match(/\d+$/)[0];
    });
    //初始化组栅格数
    $("#divideNumber").val(groupdivide);
    $('#divideModal').modal('show');
});

function openSideBar() {
    //宽度改变
    if (!$('body').hasClass('page-quick-sidebar-open')) {
        $('body').toggleClass('page-quick-sidebar-open');
        $('.designer').width($('.designer').width() - 320);
        $(".page-quick-sidebar-toggler").unbind("click");
        $(".page-quick-sidebar-toggler").bind("click", function(e) {
            $('body').toggleClass('page-quick-sidebar-open');
            $('.designer').css("width", "");
        });
    }
}
/**
 *选择grid系统时改变允许最大分组数
 */
$("input[name='gridsys']").on('ifChecked', function() {
    $("#groupNumber").attr("max", $(this).val());
    $("#groupNumber").trigger("touchspin.updatesettings", {
        max: $(this).val()
    });
});

/**
 *栏目划分增加事件绑定
 */
$("#groupNumber").validate({
    onfocusout: true
});
jQuery.validator.addMethod("divideValid", function(value, element) {
    //获取栅格系统
    var gridsys = $('#divideForm input[name="gridsys"]:checked').val();
    //获取分组数
    var groups = $("#groupNumber").val();
    //获取组栅格数
    var groupdivide = $("#divideNumber").val();
    if (groupdivide && groupdivide != '') {
        if (groups != groupdivide.split(",").length) {
            return false;
        } else {
            var totals = 0;
            $.each(groupdivide.split(","), function(i, item) {
                totals += parseInt(item);
            });
            if (totals - parseInt(gridsys) != 0) {
                return false;
            }
        }
    }
    return true;
}, "<span class=\"help-block\"> 栅格数与分组数不正确 </span>");
var validate = $("#divideForm").validate({
    debug: true,
    submitHandler: function(form) { //表单提交句柄,为一回调函数，带一个参数：form

    },
    rules: {
        divideNumber: {
            required: true,
            divideValid: true
        }
    },
    //如果验证控件没有message，将调用默认的信息
    messages: {
        divideNumber: {
            required: "请输入组栅格数",
            divideValid: "<span class=\"help-block\"> 栅格数与分组数不正确 </span>"
        }
    }

});
$(".okBt").click(function() {
    if (!$("#divideForm").valid()) {
        return false;
    }
    //重新计算分栏
    var row = currentComponent.find(".view:first").find(".row:first");
    //获取当前列数
    var currGroups = row.children().length;
    //重新设置列大小
    var groupdivide = $("#divideNumber").val();
    var newGroups = groupdivide.split(",").length;
    var currClass;
    var newClass;
    //获取当前选择使用的栅格系统
    var gridsys = $('#divideForm input[name="gridsys"]:checked').val();
    var columnClass = getGridColumnCss(gridsys);
    $.each(groupdivide.split(","), function(i, item) {
        newClass = columnClass + item;

        if (i < currGroups) {
            currClass = $(row.children()[i]).attr("class").match((/col\-(md|mmd|24md)\-\d+/))[0];
            $(row.children()[i]).removeClass(currClass);
            $(row.children()[i]).addClass(newClass);
            if (item > 1) {
                $(row.children()[i]).removeClass("sigcol");
            } else {
                $(row.children()[i]).addClass("sigcol");
            }
        } else {
            var newColumn = $("<div class=\"" + newClass + " column ui-sortable\"></div>");
            newColumn.append($("#colconfigTemplate").html());
            if (item > 1) {
                newColumn.removeClass("sigcol");
            } else {
                newColumn.addClass("sigcol");
            }
            row.append(newColumn);
        }
    });
    if (newGroups < currGroups) {
        for (var i = newGroups; i < currGroups; i++) {
            $(row.children()[newGroups - 1]).append($(row.children()[i]).html());
        }

        $(row.children()[newGroups - 1]).nextAll().remove();
    }
    initContainer();
    draggEventBind();
    $('#divideModal').modal('hide');
});
//获取栅格系统列样式前缀
function getGridColumnCss(gridsys) {
    if (gridsys == "48") {
        return "col-mmd-";
    } else if (gridsys == "24") {
        return "col-24md-";
    } else {
        return "col-md-";
    }
}
/**
 *上移按钮点击事件绑定
 */
$(".designer").delegate(".moveUpBt", "click", function() {
    currentComponent = $(this).parent().parent().parent().parent();
    //行位置对换
    //获取前面的行
    var preRow = currentComponent.prev();
    if (preRow && preRow.length > 0)
        exchange(preRow, currentComponent);
});
/**
 *下移按钮点击事件绑定
 */
$(".designer").delegate(".moveDownBt", "click", function() {
    currentComponent = $(this).parent().parent().parent().parent();
    //行位置对换
    //获取后面的行
    var nextRow = currentComponent.next();
    if (nextRow && nextRow.length > 0)
        exchange(nextRow, currentComponent);
});
/**
 *向上插入空行按钮点击事件绑定
 */
$(".designer").delegate(".addup-row", "click", function() {
    currentComponent = $(this).closest(".lyrow");
    //新建行
    var newRow = currentComponent.clone();
    //重新生成ID
    var dtStr = new Date().getTime();
    var nId = newRow.attr("data-role") + "_" + dtStr;
    newRow.attr("id", nId);
    //清空列内容
    newRow.find(".column").find(".configuration").nextAll().remove();
    newRow.find(".column").find(".mybox").remove();
    currentComponent.before(newRow);
    initContainer();
    draggEventBind();
});
/**
 *向下插入空行按钮点击事件绑定
 */
$(".designer").delegate(".adddown-row", "click", function() {
    currentComponent = $(this).closest(".lyrow");
    //新建行
    var newRow = currentComponent.clone();
    //重新生成ID
    var dtStr = new Date().getTime();
    var nId = newRow.attr("data-role") + "_" + dtStr;
    newRow.attr("id", nId);
    //清空列内容
    newRow.find(".column").find(".configuration").nextAll().remove();
    newRow.find(".column").find(".mybox").remove();
    currentComponent.after(newRow);
    initContainer();
    draggEventBind();
});
/**
 *向上复制插入行按钮点击事件绑定
 */
$(".designer").delegate(".copyaddup-row", "click", function() {
    currentComponent = $(this).closest(".lyrow");
    var newRow = copyRow(currentComponent);
    //子控件重新生成ID
    var nElem = generatorId(newRow);
    currentComponent.before(nElem);
    initContainer();
    draggEventBind();
});

function copyRow(row) {
    //新建行
    var newRow = currentComponent.clone();
    newRow.removeClass("shine_red");
    //获取历史ID
    var oldId = newRow.attr("id");
    //重新生成ID
    var dtStr = new Date().getTime();
    var nId = newRow.attr("data-role") + "_" + dtStr;
    newRow.attr("id", nId);
    //替换ID样式表
    newRow.find("." + oldId).addClass(nId).removeClass(oldId);
    return newRow;
}
/**
 *向下复制插入行按钮点击事件绑定
 */
$(".designer").delegate(".copyadddown-row", "click", function() {
    currentComponent = $(this).closest(".lyrow");
    var newRow = copyRow(currentComponent);
    //子控件重新生成ID
    var nElem = generatorId(newRow);
    currentComponent.after(nElem);
    initContainer();
    draggEventBind();
});
//剪贴板
var clipboard;
//复制、剪切操作标识
var copyOrCut = "copy";
/**
 *剪切
 */
$(".designer").delegate(".cut-row", "click", function() {
    currentComponent = $(this).closest(".lyrow");
    //新建行
    clipboard = currentComponent.clone();
    currentComponent.remove();
    copyOrCut = "cut";
    //$.alert(0,"已加入到剪贴板!");
});
/**
 *复制
 */
$(".designer").delegate(".copy-row", "click", function() {
    currentComponent = $(this).closest(".lyrow");
    //新建行
    clipboard = currentComponent.clone();
    copyOrCut = "copy";
    //$.alert(0,"已加入到剪贴板!");
});
/**
 *粘贴
 */
$(".designer").delegate(".paste-row", "click", function() {
    //验证当前剪贴板内容是否为行对象
    if (clipboard == undefined || clipboard == null) {
        $.alert(1, "剪贴板为空");
        return;
    }
    if (!clipboard.hasClass("lyrow")) {
        $.alert(1, "剪贴板中对象为非行对象!");
        return;
    }
    currentComponent = $(this).closest(".lyrow");
    if (copyOrCut == "copy") {
        var newRow = copyRow(clipboard);
        //子控件重新生成ID
        var nElem = generatorId(newRow);
        currentComponent.replaceWith(nElem);
    } else {
        currentComponent.replaceWith(clipboard.clone());
    }
    initContainer();
    initdraggable();
});

//元素对换
var exchange = function(a, b) {
    //var aIndex=a.index();
    var tempObj1 = a[0].outerHTML;
    var tempObj2 = b[0].outerHTML;
    //var bIndex=$(b).index();
    //var parentCont=$(a).parent();
    b.replaceWith(tempObj1);
    a.replaceWith(tempObj2);
    initContainer();
    draggEventBind();
    //parentCont.children()[aIndex]=$(b);
    //parentCont.children()[bIndex]=$(a);
};
/**
 *左移按钮点击事件绑定
 */
$(".designer").delegate(".moveLeftBt", "click", function() {
    var currentColumn = $(this).closest(".column");
    //行位置对换
    //获取前面的行
    var preColumn = currentColumn.prev();
    if (preColumn && preColumn.length > 0)
        exchange(preColumn, currentColumn);
});
/**
 *右移按钮点击事件绑定
 */
$(".designer").delegate(".moveRightBt", "click", function() {
    var currentColumn = $(this).closest(".column");
    //行位置对换
    //获取后面的行
    var nextColumn = currentColumn.next();
    if (nextColumn && nextColumn.length > 0)
        exchange(nextColumn, currentColumn);
});
/**
 *列元素剪切
 */
$(".designer").delegate(".column-cut-row", "click", function() {
    currentComponent = $(this).closest(".column");
    clipboard = currentComponent.clone();
    currentComponent.find(".mybox:first").remove();
    //$.alert(0,"已加入到剪贴板!");
});
/**
 *清空列
 */
$(".designer").delegate(".column-empty-row", "click", function() {
    currentComponent = $(this).closest(".column");
    currentComponent.find(".configuration").nextAll().remove();
});
/**
 *清空列
 */
$(".designer").delegate(".col-align", "click", function() {
    currentComponent = $(this).closest(".column");
    var type = $(this).attr("align-type");
    if (type == 'left') {
        currentComponent.addClass("text-left");
        currentComponent.removeClass("text-right");
        currentComponent.removeClass("text-center");
    } else if (type == 'right') {
        currentComponent.addClass("text-right");
        currentComponent.removeClass("text-left");
        currentComponent.removeClass("text-center");
    } else {
        currentComponent.addClass("text-center");
        currentComponent.removeClass("text-left");
        currentComponent.removeClass("text-right");
    }

});
/**
 *自定义
 */
$(".designer").delegate(".column-custom-row", "click", function() {
    currentComponent = $(this).closest(".column");
    var htmlContent = convertComponentHtml(currentComponent);

    var $htmlContent = $("<div>").append(htmlContent);
    $.each($htmlContent.find("[data-role]"), function(i, o) { //默认有很多个data-role  做循环
        //格式化内容
        var tmp = formatComponent(o);
        var ele = getHandleComponent(o);
        if (ele.attr("id") == $(tmp).attr("id") || !ele.attr("id")) {
            $(ele).after(tmp).remove();
        }
    });

    openCustomEditor($htmlContent.html());
});

function getHandleComponent(o) {
    var ele;
    if ($(o).hasClass("nlayout_elem")) { //判断data-role是否在第一层
        ele = $(o);
    } else {
        ele = $(o).closest(".nlayout_elem"); //data-role在子层就获取父层

    }
    return ele;
}

function formatComponent(o) { //获取一个对象的 模板
    var ele = $(o),
        $realEle = getHandleComponent(o);
    var dataRole = ele.attr("data-role");
    var tmp = "";
    if (o.outerHTML) {
        var template = $.base64.encode($realEle.outer());
        if (dataRole == "textboxbt") {
            tmp = '<input type="text" id="' + ele.attr("id") + '" data-role="' + ele.attr("data-role") + '" template="' + template + '"/>';
        } else if (dataRole == "label") {
            tmp = ' <span id="' + ele.attr("id") + '" data-role="' + ele.attr("data-role") + '" template="' + template + '" contenteditable="false">CHINAISS </span>';
            //tmp = '<img class="'+ele.attr("data-role")+'" src="'+obj.imageUrl+'" id="'+ele.attr("id")+'" data-role="'+ele.attr("data-role")+'" template="'+template+'"/>';
        } else if (dataRole == "datepickerbt") {
            tmp = '<span contenteditable="false" id="' + ele.attr("id") + '" data-role="' + ele.attr("data-role") + '" template="' + template + '"><input type="text"/><img src="' + contextPath +
                '/scripts/designer/editor/htmlEditorExtend/datepickerbt/1.png" style="position:relative;top:4px;margin-left:-24px;"/></span>';
        } else if (dataRole == "datetimepickerbt") {
            tmp = '<span contenteditable="false" id="' + ele.attr("id") + '" data-role="' + ele.attr("data-role") + '" template="' + template + '"><input type="text"/><img src="' + contextPath +
                '/scripts/designer/editor/htmlEditorExtend/datetimepickerbt/2.png" style="position:relative;top:4px;margin-left:-24px;"/></span>';
        } else if (dataRole == "touchspin") {
            tmp = '<input type="text" id="' + ele.attr("id") + '" data-role="' + ele.attr("data-role") + '" template="' + template + '" placeholder="数值框"/>';
        } else if (dataRole == "textareabt") {
            tmp = '<input id="' + ele.attr("id") + '" data-role="' + ele.attr("data-role") + '" template="' + template + '" placeholder="文本域"/>';
        } else if (dataRole == "metroselect") {
            tmp = '<select id="' + ele.attr("id") + '" data-role="' + ele.attr("data-role") + '" template="' + template + '"><option>单选下拉框</option></select>';
        } else if (dataRole == "metroselect_m") {
            tmp = '<select id="' + ele.attr("id") + '" data-role="' + ele.attr("data-role") + '" template="' + template + '"><option>多选下拉框</option></select>';
        } else if (dataRole == "icheckradio") {
            tmp = '<input type="radio" id="' + ele.attr("id") + '" data-role="' + ele.attr("data-role") + '" template="' + template + '" />';
        } else if (dataRole == "icheckbox") {
            tmp = '<input type="checkbox" id="' + ele.attr("id") + '" data-role="' + ele.attr("data-role") + '" template="' + template + '" />';
        } else if (dataRole == "mtbutton") {
            tmp = '<button id="' + ele.attr("id") + '" data-role="' + ele.attr("data-role") + '" template="' + template + '" contenteditable="false">按钮</button>';
        } else if (dataRole == "jqfileupload2") {
            tmp = '<input type="file" id="' + ele.attr("id") + '" data-role="' + ele.attr("data-role") + '" template="' + template + '" placeholder="文件上传"/>';
        } else if (dataRole == "col-md-12") {
            tmp = '<input type="text" id="' + ele.attr("id") + '" data-role="' + ele.attr("data-role") + '" template="' + template + '" placeholder="栅格"/>';
        } else {
            tmp = $realEle.outer();
        }
    }
    return tmp;
}

function openCustomEditor(content) {
    //弹出编辑窗口
    $('#editModal').modal('show');
    setEditorContent(content);
}
/**
 *编辑保存
 */
$(".saveEditBt").click(function() {
    $('#editModal').modal('hide');
});
/**
 *设置编辑器内容
 */
function setEditorContent(content) {

    $("#editModal").find("iframe")[0].contentWindow.setCode(content);
}
/**
 *编辑器保存按钮点击事件
 */
$(".saveEditBt").click(function() {
    //获取编辑器内容
    var content = $("#editModal").find("iframe")[0].contentWindow.getCode();
    var $content = $("<div>").append(content);
    $.each($content.find("[data-role]"), function(i, o) {

        var template = $(o).attr("template");
        var tmp = "";
        if (template) {
            tmp = $.base64.decode(template);
            $(o).after(tmp).remove();
        }
    });
    //还原内容
    formatContent($content.html());
});


/**
 *列元素复制
 */
$(".designer").delegate(".column-copy-row", "click", function() {
    currentComponent = $(this).closest(".column");
    clipboard = currentComponent.clone();
    copyOrCut = "copy";
});
/**
 *列元素粘贴
 */
$(".designer").delegate(".column-paste-row", "click", function() {
    if (clipboard == undefined || clipboard == null) {
        $.alert(1, "剪贴板为空");
        return;
    }
    //新建列元素
    var newCol = clipboard.clone();
    var nElem;
    if (copyOrCut == "copy") {
        //重新生成ID
        var dtStr = new Date().getTime();
        if (newCol.attr("data-role") != undefined) {
            var nId = newCol.attr("data-role") + "_" + dtStr;
            newCol.attr("id", nId);
        }
        //子控件重新生成ID
        nElem = generatorId(newCol);
    } else {
        nElem = clipboard.clone();
    }
    currentComponent = $(this).closest(".column");
    //检测是否是复制的列对象
    if (clipboard.hasClass("column")) {
        currentComponent.empty();
        currentComponent.append(nElem.html());
    } else {
        currentComponent.find(".configuration:first").nextAll().remove();
        currentComponent.find(".configuration:first").after(nElem);
    }
    initContainer();
    initdraggable();
});
//获取当前栅格系统
function getGridSysByColumnClass(columnClass) {
    return columnClass.match((/col\-(md|mmd|24md)\-/))[0];
}
/**
 *左侧插入列按钮点击事件绑定
 */
$(".designer").delegate(".leftAddBt", "click", function() {
    var currentColumn = $(this).closest(".column");
    //获取列栅格样式
    var currClass = currentColumn.attr("class").match((/col\-(md|mmd|24md)\-\d+/))[0];
    //获取列栅格数
    var currCells = currClass.match((/\d+$/))[0];
    if (currCells == 1) {
        return;
    }
    //重新计算列栅格数
    var newCells = parseInt(currCells / 2);
    if (newCells == 1) {
        currentColumn.addClass("sigcol");
    }
    var preColumnClass = getGridSysByColumnClass(currClass);
    var newClass = preColumnClass + newCells;
    currentColumn.removeClass(currClass);
    currentColumn.addClass(newClass);
    newClass = preColumnClass + (currCells - newCells);
    var newColumn = $("<div class=\"" + newClass + " column ui-sortable\"></div>");
    newColumn.append($("#colconfigTemplate").html());
    //如果列栅格数为1则隐藏左右插入列操作按钮
    if (currCells - newCells == 1) {
        newColumn.addClass("sigcol");
    }
    currentColumn.before(newColumn);
    initContainer();
    draggEventBind();
});
/**
 *左侧插入列按钮点击事件绑定
 */
$(".designer").delegate(".rightAddBt", "click", function() {
    var currentColumn = $(this).closest(".column");
    //获取列栅格样式
    var currClass = currentColumn.attr("class").match((/col\-(md|mmd|24md)\-\d+/))[0];
    //获取列栅格数
    var currCells = currClass.match((/\d+$/))[0];
    if (currCells == 1) {
        return;
    }
    //重新计算列栅格数
    var newCells = parseInt(currCells / 2);
    if (newCells == 1) {
        currentColumn.addClass("sigcol");
    }
    var preColumnClass = getGridSysByColumnClass(currClass);
    var newClass = preColumnClass + newCells;
    currentColumn.removeClass(currClass);
    currentColumn.addClass(newClass);
    newClass = preColumnClass + (currCells - newCells);
    var newColumn = $("<div class=\"" + newClass + " column ui-sortable\"></div>");
    newColumn.append($("#colconfigTemplate").html());
    //如果列栅格数为1则隐藏左右插入列操作按钮
    if (currCells - newCells == 1) {
        newColumn.addClass("sigcol");
    }
    currentColumn.after(newColumn);
    initContainer();
    draggEventBind();

});


/**
 *向上复制插入元素按钮点击事件绑定
 */
$(".designer").delegate(".elemcopyaddup-row", "click", function() {
    currentComponent = $(this).closest(".mybox");
    //新建行
    var newElem = currentComponent.clone();
    newElem.removeClass("shine_red");
    newElem.find(".shine_red").removeClass("shine_red");
    if (newElem.attr("data-role") != undefined) {
        //重新生成ID
        var dtStr = new Date().getTime();
        var nId = newElem.attr("data-role") + "_" + dtStr;
        newElem.attr("id", nId);
    }
    //子控件重新生成ID
    var nElem = generatorId(newElem);
    currentComponent.before(nElem);
    initContainer();
    initdraggable();
});
/**
 *向下复制插入元素按钮点击事件绑定
 */
$(".designer").delegate(".elemcopyadddown-row", "click", function() {
    currentComponent = $(this).closest(".mybox");
    //新建元素
    var newElem = currentComponent.clone();
    newElem.removeClass("shine_red");
    newElem.find(".shine_red").removeClass("shine_red");
    if (newElem.attr("data-role") != undefined) {
        //重新生成ID
        var dtStr = new Date().getTime();
        var nId = newElem.attr("data-role") + "_" + dtStr;
        newElem.attr("id", nId);
    }
    //子控件重新生成ID
    var nElem = generatorId(newElem);
    currentComponent.after(nElem);
    initContainer();
    initdraggable();
});

function generatorId(obj) {
    var oldFirstId = obj.find(".view:first").children("[data-role]").attr("id");
    var childComp = obj.find("[data-role]");
    var dtStr;
    var nId;
    var oldId;
    var newObj = obj.clone();
    $.each(childComp, function(i, item) {
        oldId = $(item).attr("id");
        dtStr = new Date().getTime() + i;
        nId = $(item).attr("data-role") + "_" + dtStr;
        $(item).attr("id", nId);
        newObj = updateIdCss(newObj, oldId, nId);
    });
    //获取当前元素ID
    var newFirstid = obj.find(".view:first").children("[data-role]").attr("id");
    newObj = updateIdCss(newObj, oldFirstId, newFirstid);
    return newObj;
}
/**
 *更新ID相关的CSS
 */
function updateIdCss(obj, oldIdCss, newIdCss) {
    var reg = new RegExp("(" + oldIdCss + ")", "g");
    var newObj = $(obj.prop("outerHTML").replace(reg, newIdCss));
    return newObj;
}
/**
 *剪切
 */
$(".designer").delegate(".elemcut-row", "click", function() {
    currentComponent = $(this).closest(".mybox");
    //新建行
    clipboard = currentComponent.clone();
    currentComponent.remove();
    copyOrCut = "cut";
    //$.alert(0,"已加入到剪贴板!");
});
/**
 *复制
 */
$(".designer").delegate(".elemcopy-row", "click", function() {
    currentComponent = $(this).closest(".mybox");
    //新建行
    clipboard = currentComponent.clone();
    copyOrCut = "copy";
    //$.alert(0,"已加入到剪贴板!");
});
/**
 *粘贴
 */
$(".designer").delegate(".elempaste-row", "click", function() {
    //验证当前剪贴板内容是否为行对象
    if (clipboard == undefined || clipboard == null) {
        $.alert(1, "剪贴板为空");
        return;
    }
    //新建元素对象
    var newElem = clipboard.clone();
    currentComponent = $(this).closest(".mybox");
    currentComponent.empty();
    if (copyOrCut == "copy") {
        //重新生成ID
        var dtStr = new Date().getTime();
        var nId = newElem.attr("data-role") + "_" + dtStr;
        newElem.attr("id", nId);
        //子控件重新生成ID
        var nElem = generatorId(newElem);
        currentComponent.append(nElem.html());
    } else {
        currentComponent.append(newElem);
    }
    initContainer();
    initdraggable();
});
/**
 *自动分栏
 */
$(".autoBt").click(function() {
    //获取栅格系统
    var gridsys = $('#divideForm input[name="gridsys"]:checked').val();
    //获取分组数
    var groups = $("#groupNumber").val();
    var groupdivide = new Array();
    var groupdivide;
    if (gridsys != undefined && groups > 0) {
        var vgaCells = parseInt(gridsys / groups);
    }
    for (var i = 0; i < groups; i++) {
        if (i < groups - 1)
            groupdivide[i] = vgaCells;
        else
            groupdivide[i] = gridsys - vgaCells * (groups - 1)
    }
    groupdivide = groupdivide.join(",");
    $("#divideNumber").val(groupdivide);
});
/**
 *另存为模板
 *
 */
$(".designer").delegate(".save-as-row,.elem-save-as,.column-save-as", "click", function() {
    //行另存为
    if ($(this).hasClass('save-as-row')) {
        currentComponent = $(this).closest(".lyrow");
    }
    //元素另存为
    else if ($(this).hasClass('elem-save-as')) {
        currentComponent = $(this).closest(".mybox");
    }
    //列另存为
    else if ($(this).hasClass('column-save-as')) {
        currentComponent = $(this).closest(".column");
    }
    //设置iframe src
    //var url=contextPath+"/mx/page/designer/metro/template?action=join&fn=getCurrentComponent";
    var code = convertComponentHtml(currentComponent);
    $("#codeEditModal").find("iframe")[0].contentWindow.changeTab("portlet_tab1");
    $("#codeEditModal").find("iframe")[0].contentWindow.setSummernoteCode(code);
    $('#codeEditModal').modal('show');
});
//获取当前控件
function getCurrentComponent() {
    return currentComponent;
}
//转换页面布局HTML
function convertComponentHtml(obj) {
    var $layoutHtml = $("<div class=\"container_\"></div>");
    $layoutHtml.append(obj.prop("outerHTML"));
    //去掉设计元素
    $layoutHtml.find(".mybox>.preview, .configuration,.formgrouptoolbar,.toolbar,.drag, .remove,.removecol").remove();
    $layoutHtml.find(".box-element").addClass(
        "removeClean");
    $layoutHtml
        .find(".removeClean")
        .each(function() {
            cleanHtml(this)
        });
    $layoutHtml.find(".removeClean").each(function(index, el) {
        if (!$(el).parent().hasClass('containerComp')) {
            $(el).remove();
        }
    });
    $layoutHtml.find('[contenteditable=plaintext-only]').each(function(index, el) {
        $(el).removeAttr('contenteditable');
    });
    $layoutHtml.find('.shine_red').removeClass("shine_red");
    //列内容转换
    if (obj.hasClass("column")) {
        return $layoutHtml.children().html();
    }
    return $layoutHtml.html();
}
//还原内容
function formatContent(content) {
    var targetElem = $("<div></div>");
    targetElem.append(content);
    /*
     //子控件重新生成ID
     var childComp=targetElem.find("[data-role]");
     var dtStr;
     var nId;
     var oldId;
     $.each(childComp,function(i,item){
     oldId=$(item).attr("id");
     dtStr = new Date().getTime()+i;
     nId=$(item).attr("data-role")+"_"+dtStr;
     $(item).attr("id",nId);
     targetElem=updateIdCss(targetElem,oldId,nId);
     });
     */
    //布局控件补充工具栏
    var layoutComps = targetElem.find(".layout_elem");
    //获取布局控件工具栏模板（编辑、删除、拖动）
    var gridlayout;

    var preview;
    $.each(layoutComps, function(i, layoutComp) {
        preview = $(layoutComp).find(".preview:first");
        //行添加工具栏
        gridlayout = $($("#gridlayout_toolbar").html());
        preview.before(gridlayout);
    });
    //获取列工具栏模板（编辑、删除、拖动）
    var colconfigTemplate = $("#colconfigTemplate").html()
        //列添加工具栏
    $(targetElem).find(".column").prepend(colconfigTemplate);
    //非布局控件补充工具栏
    var elemComps = targetElem.find(".nlayout_elem");
    //获取元素模板对象
    var elementTemplate, compId, elemCompClone;
    $.each(elemComps, function(i, elemComp) {
        elemCompClone = $(elemComp).clone();

        if (elemCompClone.attr("data-role")) {
            elementType = elemCompClone.attr("data-role");
            compId = elemCompClone.attr("id");
        } else {
            var ele = elemCompClone.find("[data-role]");
            if (ele.length == 1) {
                elementType = ele.attr("data-role");
                compId = ele.attr("id");
            }
        }

        dtStr = new Date().getTime() + i;
        elementTemplate = $($('#elementTemplate').html());
        elementTemplate.find(".view").attr("id", compId + "_view");
        elementTemplate.find(".view").append(elemCompClone);
        elementTemplate.find(".preview").html($(this).find('span').text());
        elementTemplate.attr("id", elementType + "_" + dtStr + "_ct");
        //元素内容可编辑
        //$(elemComp).attr("contenteditable","plaintext-only");
        //元素替换
        $(elemComp).replaceWith(elementTemplate);
    });
    var desObj = targetElem /*$(targetElem.html())*/ ;
    currentComponent.contents().each(function() {
        if (this.nodeType === 3) {
            $(this).remove();
        }
    });
    //验证套用对象与被套用对象是否属同一类型控件（行、普通元素、列）
    if (currentComponent.hasClass('column')) {
        currentComponent.children(":not(.removecol,.configuration)").remove();
        currentComponent.append(desObj.html());
    } else if (currentComponent.hasClass('lyrow')) {
        if ( /*desObj.hasClass('lyrow')*/ desObj.find(">.lyrow").length) {
            currentComponent.replaceWith(desObj.html());
        } else {
            $("#editModal").find("iframe")[0].contentWindow.$.alert(1, "非布局控件模板不能套用");
            return;
        }
    } else {
        currentComponent.replaceWith(desObj.html());
    }
    //表单控件组增加工具栏
    //获取工具栏模板
    var toolbar = $($("#formgrouptoolbarTemplate").html());
    currentComponent.find(".myformgroup").prepend(toolbar);
    draggEventBind();
    initContainer();
    if (stopsave > 0) stopsave--;
    startdrag = 0;

    //控件初始渲染
    var $obj = currentComponent.find("[data-role]"),
        dataRoles = {};
    $.each($obj, function(i, o) {
        var ele = $(o);
        var dataRole = ele.attr("data-role") || "";
        if (!dataRoles[dataRole]) {
            dataRoles[dataRole] = [ele.attr("id") || ""];
        } else {
            dataRoles[dataRole].push(ele.attr("id") || "");
        }
    });
    $.each(dataRoles, function(k, v) {
        var componentId, dataRole = k;
        var $a = $("#sidebar-menu", window.parent.document).find('a[data_role="' + dataRole + '"]');
        if ($a.length > 0) {
            componentId = $a.attr("data_id");
        }
        if (componentId) {
            /* $.ajax({
                url: contextPath + '/service/designer/' + componentId + '/getTemplateRender',
                type: "post",
                dataType: 'json',
                success: function (data) {
                    if (data.template) {
                        $.each(v, function (i, o) {
                            var id = o;
                            if ($("#" + id).attr("render") == "new") {
                                eval(data.template);
                                $("#" + id).removeAttr('render');
                            }
                        });
                    }
                },
                error: function (xhr, ts) {
                    console.log('控件渲染失败！');
                }
            }); */
            $.each(v, function(i, o) {
                var id = o;
                if ($("#" + id).attr("render") == "new") {
                    compRenderMethod.render(id, componentId);
                    $("#" + id).removeAttr('render');
                }
            });
        }
    });
}

//套用模板
function ApplyTemplate(content) {
    var targetElem = $("<div></div>");
    targetElem.append(content);
    //子控件重新生成ID
    var childComp = targetElem.find("[data-role]");
    var dtStr;
    var nId;
    var oldId;
    $.each(childComp, function(i, item) {
        oldId = $(item).attr("id");
        dtStr = new Date().getTime() + i;
        nId = $(item).attr("data-role") + "_" + dtStr;
        $(item).attr("id", nId);
        targetElem = updateIdCss(targetElem, oldId, nId);
    });

    //布局控件补充工具栏
    var layoutComps = targetElem.find(".layout_elem");
    //获取布局控件工具栏模板（编辑、删除、拖动）
    var gridlayout;

    var preview;
    $.each(layoutComps, function(i, layoutComp) {
        preview = $(layoutComp).find(".preview:first");
        //行添加工具栏
        gridlayout = $($("#gridlayout_toolbar").html());
        preview.before(gridlayout);
    });
    //获取列工具栏模板（编辑、删除、拖动）
    var colconfigTemplate = $("#colconfigTemplate").html()
        //列添加工具栏
    $(targetElem).find(".column").prepend(colconfigTemplate);
    //非布局控件补充工具栏
    var elemComps = targetElem.find(".nlayout_elem");
    //获取元素模板对象
    var elementTemplate, compId, elemCompClone;
    $.each(elemComps, function(i, elemComp) {
        elemCompClone = $(elemComp).clone();
        elementType = elemCompClone.attr("data-role");
        dtStr = new Date().getTime();
        compId = elemCompClone.attr("id");
        elementTemplate = $($('#elementTemplate')
            .html());
        elementTemplate.find(".view").attr("id", compId + "_view");
        elementTemplate.find(".view").append(elemCompClone);
        elementTemplate.find(".preview").html($(this).find('span').text());
        elementTemplate.attr("id", elementType + "_" + dtStr + "_ct");
        //元素内容可编辑
        //$(elemComp).attr("contenteditable","plaintext-only");
        //元素替换
        $(elemComp).replaceWith(elementTemplate);
    });
    var desObj = $(targetElem.html());
    //验证套用对象与被套用对象是否属同一类型控件（行、普通元素、列）
    if (currentComponent.hasClass('column')) {
        currentComponent.children(":not(.removecol,.configuration)").remove();
        currentComponent.append(desObj);
    } else if (currentComponent.hasClass('lyrow')) {
        if (desObj.hasClass('lyrow')) {
            currentComponent.replaceWith(desObj);
        } else {
            $("#codeEditModal").find("iframe")[0].contentWindow.$.alert(1, "非布局控件模板不能套用");
            return;
        }
    } else {
        currentComponent.replaceWith(desObj);
    }
    draggEventBind();
    initContainer();
    changeElemStyle(desObj);


    /**
     //获取当前元素类型
     var elementType = moveObj.attr("data-role");
     //获取元素模板对象
     var elementTemplate = $($('#elementTemplate')
     .html());
     var targetElem=$($("#template_"+moveObj.attr("id")).html());
     var dtStr = new Date().getTime();
     var id = elementType + "_" + dtStr;
     targetElem.attr("id", id);
     targetElem.attr("cname", id+'_控件');
     targetElem.addClass(id);
     targetElem.addClass("nlayout_elem");
     //子控件重新生成ID
     var childComp=targetElem.find("[data-role]");
     var dtStr;
     var nId;
     var oldId;
     $.each(childComp,function(i,item){
							oldId=$(item).attr("id");
							dtStr = new Date().getTime();
						    nId=$(item).attr("data-role")+"_"+dtStr;
						    $(item).attr("id",nId);
						});
     targetElem.attr("data-role", elementType);
     //替换id样式表
     targetElem.find('.id').addClass(targetElem.attr("id"));
     targetElem.find('.id').removeClass("id");
     elementTemplate.find(".view").attr("id",id+"_view");
     elementTemplate.find(".view").append(targetElem);
     elementTemplate.find(".preview").html(
     $(this).find('span').text());
     elementTemplate.attr("id", elementType + "_"
     + dtStr + "_ct");
     elementTemplate.find(".view").append(targetElem);
     elementTemplate.find(".preview").html(
     $(this).find('span').text());
     //元素内容可编辑
     targetElem.attr("contenteditable","true");
     moveObj.replaceWith(elementTemplate);
     changeElemStyle(elementTemplate);
     initContainer();*/
    if (stopsave > 0) stopsave--;
    startdrag = 0;
    $("#codeEditModal").find("iframe")[0].contentWindow.$.alert(0, "套用成功！");
}

//套用页面模板
function ApplyPageTemplate(content, id) {
    if (id != undefined) {
        //获取当前页面样式，动态加载样式表
        var styleUrl = contextPath + "/rs/designer/" + id + "/getTemplateStyle";
        var styleDom = "<link id=\"themeCss\" href=\"" + styleUrl + "\" rel=\"stylesheet\" type=\"text/css\" />";
        if ($("#themeCss").length > 0) {
            $("#themeCss").remove();
        }
        $("body").append(styleDom);

    }
    var targetElem = $("<div></div>");
    //验证首层元素是否都为栅格row
    //首层不全为栅格
    if (targetElem.children(":not(.lyrow)").length > 0) {
        //增加行
        var rowDom = $($.template[elementType]);
        var dtStr = new Date().getTime();
        rowDom.addClass("layout_elem");
        rowDom.attr("id", elementType + "_" + dtStr);
        rowDom.find(".view>.row:first").addClass(elementType + "_" + dtStr);
        rowDom.find(".preview>input").attr("value",
            $(this).find('span').text());
        rowDom.attr("data-role", elementType);
        if (pElementType != '') {
            rowDom.attr("pdata-role", pElementType);
        }
        rowDom.attr("scope", scope);
        rowDom.attr("crtltype", "kendo");
        //替换id样式表
        rowDom.find('.id').addClass(rowDom.attr("id"));
        rowDom.find('.id').removeClass("id");
        //列添加工具栏
        rowDom.find(".column").prepend($("#colconfigTemplate").html());
        rowDom.append(content);
        targetElem.append(rowDom);

    } else {
        targetElem.append(content);
    }
    //子控件重新生成ID
    var childComp = targetElem.find("[data-role]");
    var dtStr;
    var nId;
    var oldId;
    $.each(childComp, function(i, item) {
        oldId = $(item).attr("id");
        dtStr = new Date().getTime() + i;
        nId = $(item).attr("data-role") + "_" + dtStr;
        $(item).attr("id", nId);
        targetElem = updateIdCss(targetElem, oldId, nId);
    });

    //布局控件补充工具栏
    var layoutComps = targetElem.find(".layout_elem");
    //获取布局控件工具栏模板（编辑、删除、拖动）
    var gridlayout;

    var preview;
    $.each(layoutComps, function(i, layoutComp) {
        preview = $(layoutComp).find(".preview:first");
        //行添加工具栏
        gridlayout = $($("#gridlayout_toolbar").html());
        preview.before(gridlayout);
    });
    //获取列工具栏模板（编辑、删除、拖动）
    var colconfigTemplate = $("#colconfigTemplate").html()
        //列添加工具栏
    $(targetElem).find(".column").prepend(colconfigTemplate);
    //非布局控件补充工具栏
    var elemComps = targetElem.find(".nlayout_elem");
    //获取元素模板对象
    var elementTemplate, compId, elemCompClone;
    $.each(elemComps, function(i, elemComp) {
        elemCompClone = $(elemComp).clone();
        elementType = elemCompClone.attr("data-role");
        dtStr = new Date().getTime();
        compId = elemCompClone.attr("id");
        elementTemplate = $($('#elementTemplate')
            .html());
        elementTemplate.find(".view").attr("id", compId + "_view");
        elementTemplate.find(".view").append(elemCompClone);
        elementTemplate.find(".preview").html($(this).find('span').text());
        elementTemplate.attr("id", elementType + "_" + dtStr + "_ct");
        //元素内容可编辑
        //$(elemComp).attr("contenteditable","plaintext-only");
        //元素替换
        $(elemComp).replaceWith(elementTemplate);
    });
    var desObj = $(targetElem.html());
    //设计区更换内容
    $("#dsDiv").html(desObj);
    draggEventBind();
    initContainer();
    changeElemStyle(desObj);


    /**
     //获取当前元素类型
     var elementType = moveObj.attr("data-role");
     //获取元素模板对象
     var elementTemplate = $($('#elementTemplate')
     .html());
     var targetElem=$($("#template_"+moveObj.attr("id")).html());
     var dtStr = new Date().getTime();
     var id = elementType + "_" + dtStr;
     targetElem.attr("id", id);
     targetElem.attr("cname", id+'_控件');
     targetElem.addClass(id);
     targetElem.addClass("nlayout_elem");
     //子控件重新生成ID
     var childComp=targetElem.find("[data-role]");
     var dtStr;
     var nId;
     var oldId;
     $.each(childComp,function(i,item){
							oldId=$(item).attr("id");
							dtStr = new Date().getTime();
						    nId=$(item).attr("data-role")+"_"+dtStr;
						    $(item).attr("id",nId);
						});
     targetElem.attr("data-role", elementType);
     //替换id样式表
     targetElem.find('.id').addClass(targetElem.attr("id"));
     targetElem.find('.id').removeClass("id");
     elementTemplate.find(".view").attr("id",id+"_view");
     elementTemplate.find(".view").append(targetElem);
     elementTemplate.find(".preview").html(
     $(this).find('span').text());
     elementTemplate.attr("id", elementType + "_"
     + dtStr + "_ct");
     elementTemplate.find(".view").append(targetElem);
     elementTemplate.find(".preview").html(
     $(this).find('span').text());
     //元素内容可编辑
     targetElem.attr("contenteditable","true");
     moveObj.replaceWith(elementTemplate);
     changeElemStyle(elementTemplate);
     initContainer();*/
    if (stopsave > 0) stopsave--;
    startdrag = 0;
    $("#codeEditModal").find("iframe")[0].contentWindow.$.alert(0, "套用成功！");
}
//套用模板样式
function ApplyPageTemplateStyle(id) {
    if (id != undefined) {
        //获取当前页面样式，动态加载样式表
        var styleUrl = contextPath + "/rs/designer/" + id + "/getTemplateStyle";
        var styleDom = "<link id=\"themeCss\" href=\"" + styleUrl + "\" rel=\"stylesheet\" type=\"text/css\" />";
        if ($("#themeCss").length > 0) {
            $("#themeCss").remove();
        }
        $("body").append(styleDom);

    }
}
//表单分组控件插入按钮事件绑定
$(".designer").delegate(".insert", "click", function() {
    var currentComponent = $(this).closest(".myformgroup");
    //获取模板名称
    var formgroupName = $(this).attr("template");
    //从模板新建表单分组控件
    var component = $($("#" + formgroupName).html());
    //获取工具栏模板
    var toolbar = $($("#formgrouptoolbarTemplate").html());
    component.prepend(toolbar);
    //子控件重新生成ID
    var childComps = component.find("[data-role]");
    var dtStr;
    var nId;
    var oldId;
    $.each(childComps, function(i, item) {
        oldId = $(item).attr("id");
        dtStr = new Date().getTime() + i;
        nId = $(item).attr("data-role") + "_" + dtStr;
        $(item).attr("id", nId);
        $(item).attr("scope", "bootstrap");
        $(item).attr("crtltype", "kendo");
        $(item).attr("cname", nId + "_控件");
    });
    //获取插入方向
    var insertType = $(this).attr("type");
    if (insertType == 'prev') {
        currentComponent.before(component);
    } else {
        currentComponent.after(component);
    }

    $.each(childComps, function(i, item) {
        //控件初始化缺省值
        var datarole = $(item).attr("data-role");
        if (datarole == 'icheckradio') {
            $(item).icheckradio({
                asDemo: true
            });
        } else if (datarole == 'icheckbox') {
            $(item).icheckbox({
                asDemo: true
            });
        }
    });
    initdraggable();
});
/**
 *表单分组控件 左移按钮事件绑定
 */
$(".designer").delegate(".move-left", "click", function() {
    var currentComponent = $(this).closest(".myformgroup");
    //分组控件位置对换
    //获取前面的分组控件
    var prevComp = currentComponent.prev();
    if (prevComp && prevComp.length > 0)
        exchange(prevComp, currentComponent);
});
/**
 *表单分组控件 右移按钮事件绑定
 */
$(".designer").delegate(".move-right", "click", function() {
    var currentComponent = $(this).closest(".myformgroup");
    //分组控件位置对换
    //获取后面的分组控件
    var nextComp = currentComponent.next();
    if (nextComp && nextComp.length > 0)
        exchange(nextComp, currentComponent);
});
/**
 *表单分组控件 右移按钮事件绑定
 */
$(".designer").delegate(".delete", "click", function() {
    var currentComponent = $(this).closest(".myformgroup");
    currentComponent.remove();
});
//格式刷按钮
var copyFormat;
var brushComp = {};
$("#formatBrushBt").click(function() {
    if (currentComponent == null) {
        return;
    }
    //格式刷复制格式
    if ($(this).hasClass("btn-default")) {
        copyFormat = currentComponent;
        $(this).removeClass("btn-default");
        $(this).addClass("red-flamingo");
    } else {
        //获取套用格式控件
        //定义控件类型
        var compType, variableDom, compId, compName, cpCompId;
        $.each(brushComp, function(i, comp) {
            compType = $(comp).attr("data-role");
            compId = $(comp).attr("id");
            compName = $(comp).attr("name");
            cpCompId = copyFormat.attr("id");
            if (compType == copyFormat.attr("data-role")) {
                //获取样式表
                if (copyFormat.attr("data-rule") != '') {
                    $(comp).attr("data-rule", copyFormat.attr("data-rule"));
                }
                if (copyFormat.attr("style") != '') {
                    $(comp).attr("style", copyFormat.attr("style"));
                }
                //获取模板内容区
                variableDom = $(comp).find(".frame-variable." + compId + "");
                var newcontent = $(copyFormat).clone();
                if (newcontent.hasClass("column")) {
                    //获取复制栅格列数样式
                    var srcClass = newcontent.attr("class").match((/col\-(md|mmd|24md)\-\d+/))[0];
                    //获取目标栅格列数样式
                    var targetClass = $(comp).attr("class").match((/col\-(md|mmd|24md)\-\d+/))[0];
                    newcontent.removeClass(srcClass);
                    newcontent.addClass(targetClass);
                }
                newcontent.removeClass("shine_red");
                newcontent.find('.shine_red').removeClass("shine_red");
                if (variableDom.length == 0 && $(comp).hasClass(id) && $(comp).hasClass("frame-variable")) {
                    variableDom = $(comp);
                    var $tag = newcontent.find("[frame-variable=" + fvs.attr('frame-variable') + "]");
                    if ($tag.length == 0 && newcontent.hasClass("frame-variable")) {
                        if (newcontent.attr("frame-variable") == fvs.attr('frame-variable')) {
                            $tag = newcontent;
                        } else {
                            return;
                        }
                    }
                    if ($tag.attr('type') == "text") {
                        $tag.val(fvs.val());
                    } else {
                        $tag.html(fvs.html());
                    }
                } else if (variableDom.length > 0) {
                    $.each(variableDom, function(index, val) {
                        var $tag = newcontent.find("[frame-variable=" + $(val).attr('frame-variable') + "]");
                        if ($tag.length == 0 && newcontent.hasClass("frame-variable")) {
                            if (newcontent.attr("frame-variable") == $(val).attr('frame-variable')) {
                                $tag = newcontent;
                            } else {
                                return;
                            }
                        }
                        if ($tag.attr('type') == "text") {
                            $tag.val($(val).val());
                        } else {
                            $tag.html($(val).html());
                        }
                    });
                } else {
                    if (newcontent.find("[frame-variable=body]") != undefined && newcontent.find("[frame-variable=body]").length > 0) {
                        newcontent.find("[frame-variable=body]:first").append($(comp).prop("outerHTML"));
                    } else {
                        try {
                            var ctrImpl = "copyFormat";
                            var ctrImpl = eval(compType + "_designer." + ctrImpl);
                            ctrImpl.call(this, newcontent, $(comp));
                        } catch (e) {
                            console.log('控件"' + compType + '"无格式化方法，执行默认格式化方法！');
                            console.log(e);
                            newcontent.html($(comp).html());
                        }
                    }
                }
                if (compId != undefined && compId != '') {
                    //补充ID CLASS
                    if (newcontent.hasClass(cpCompId)) {
                        newcontent.addClass(compId);
                        newcontent.removeClass(cpCompId);
                    }
                    newcontent.find("." + cpCompId).addClass(compId);
                    newcontent.find("." + cpCompId).removeClass(cpCompId);
                    newcontent.addClass(compId);
                    newcontent.removeClass(cpCompId);
                    //修复ID
                    newcontent.attr("id", compId);
                    //修复cname
                    newcontent.attr("cname", compName);
                }
                $(comp).replaceWith(newcontent);
                initContainer();
            }
        });
        //套用格式
        $(this).removeClass("red-flamingo");
        $(this).addClass("btn-default");
        $(".designer").find(".formatComp").removeClass("formatComp");
        copyFormat = null;
        brushComp = {};
    }
});
$(document).ready(function() {
    var key = 0; //记录ctrl/shift键
    $(window).keydown(function(e) {
        if (e.ctrlKey) {
            key = 1;
        } else if (e.shiftKey) {
            key = 2;
        }
        //上方复制插入键盘快捷键（ctrl+alt+u）
        if (e.ctrlKey && e.altKey && e.keyCode == 85) {
            key = 3;
            if (currentComponent && currentComponent.hasClass("layout_elem") && currentComponent.children(".toolbar").find(".copyaddup-row")) {
                currentComponent.children(".toolbar").find(".copyaddup-row").trigger("click");
            } else if (currentComponent && currentComponent.hasClass("nlayout_elem") && currentComponent.closest(".mybox").find(".elemcopyaddup-row:first")) {
                currentComponent.closest(".mybox").find(".elemcopyaddup-row:first").trigger("click");
            }
        }
        //下方复制插入键盘快捷键（ctrl+alt+k）
        else if (e.ctrlKey && e.altKey && e.keyCode == 75) {
            key = 4;
            if (currentComponent && currentComponent.hasClass("layout_elem") && currentComponent.children(".toolbar").find(".copyadddown-row")) {
                currentComponent.children(".toolbar").find(".copyadddown-row:first").trigger("click");
            } else if (currentComponent && currentComponent.hasClass("nlayout_elem") && currentComponent.closest(".mybox").find(".elemcopyadddown-row:first")) {
                currentComponent.closest(".mybox").find(".elemcopyadddown-row:first").trigger("click");
            }
        }
        //栅格列左移动（ctrl+←）
        if (e.ctrlKey && e.keyCode == 37) {
            key = 5;
            if (currentComponent && currentComponent.hasClass("column") && currentComponent.children(".configuration").find(".moveLeftBt")) {
                currentComponent.children(".configuration").find(".moveLeftBt").trigger("click");
                currentComponent = $(".designer").find(".column.shine_red");
            }
        }
        //栅格列右移动（ctrl+→）
        else if (e.ctrlKey && e.keyCode == 39) {
            key = 6;
            if (currentComponent && currentComponent.hasClass("column") && currentComponent.children(".configuration").find(".moveRightBt")) {
                currentComponent.children(".configuration").find(".moveRightBt").trigger("click");
                currentComponent = $(".designer").find(".column.shine_red");
            }
        }
        //栅格行上移动（ctrl+↑）
        else if (e.ctrlKey && e.keyCode == 38) {
            key = 7;
            if (currentComponent && currentComponent.hasClass("layout_elem") && currentComponent.children(".toolbar").find(".moveUpBt")) {
                currentComponent.children(".toolbar").find(".moveUpBt").trigger("click");
                currentComponent = $(".designer").find(".layout_elem.shine_red");
            } else if (currentComponent && currentComponent.hasClass("nlayout_elem") && currentComponent.closest(".mybox").find(".moveUpBt:first")) {
                currentComponent.closest(".mybox").find(".moveUpBt:first").trigger("click");
                currentComponent = $(".designer").find(".nlayout_elem.shine_red");
            }

        }
        //栅格行下移动（ctrl+↓）
        else if (e.ctrlKey && e.keyCode == 40) {
            key = 8;
            if (currentComponent && currentComponent.hasClass("layout_elem") && currentComponent.children(".toolbar").find(".moveDownBt")) {
                currentComponent.children(".toolbar").find(".moveDownBt").trigger("click");
                currentComponent = $(".designer").find(".layout_elem.shine_red");
            } else if (currentComponent && currentComponent.hasClass("nlayout_elem") && currentComponent.closest(".mybox").find(".moveDownBt:first")) {
                currentComponent.closest(".mybox").find(".moveDownBt:first").trigger("click");
                currentComponent = $(".designer").find(".nlayout_elem.shine_red");
            }
        }
    }).keyup(function() {
        key = 0;
    });
    //注册控件单击事件
    $(".designer").delegate("[data-role]", "click", function(event) {
        if ($("#formatBrushBt").hasClass("red-flamingo")) {
            if (key == 2) {
                $(this).siblings().addClass("formatComp");
                event.stopPropagation();
            } else if (key == 1) {
                if (!$(this).hasClass("formatComp")) {
                    $(this).addClass("formatComp");
                    brushComp[$(this).attr("id")] = $(this);
                } else {
                    $(this).removeClass("formatComp");
                    delete brushComp[$(this).attr("id")];
                }
                event.stopPropagation();
            } else {
                $(this).removeClass("formatComp");
            }
        } else {
            if (key == 1) {
                if (!$(this).hasClass("selectedComp")) {
                    $(this).addClass("selectedComp");
                    brushComp[$(this).attr("id")] = $(this);
                } else {
                    $(this).removeClass("selectedComp");
                    delete brushComp[$(this).attr("id")];
                }
                event.stopPropagation();
            }
        }
    });
    //注册栅格列单击事件
    $(".designer").delegate(".column", "click", function(event) {
        if ($("#formatBrushBt").hasClass("red-flamingo")) {
            if (key == 2) {
                $(this).siblings().addClass("formatComp");
                event.stopPropagation();
            } else if (key == 1) {
                if (!$(this).hasClass("formatComp")) {
                    $(this).addClass("formatComp");
                    if ($(this).attr("id") == undefined || $(this).attr("id") == '') {
                        var id = new Date().getTime();
                        $(this).attr("id", id);
                    }
                    brushComp[$(this).attr("id")] = $(this);
                } else {
                    $(this).removeClass("formatComp");
                    delete brushComp[$(this).attr("id")];
                }
                event.stopPropagation();
            } else {
                $(this).removeClass("formatComp");
            }
        }
    });
    //自适用高度按钮单击事件
    $("#autoHeightBt").click(function() {
        if ($(this).hasClass("red-mint")) {
            $(this).removeClass("red-mint");
            $(this).addClass("green-jungle");
            changeRowAutoHeightCss(true);
            $.alert(0, "切换为高度自适应模式");

        } else {
            $(this).removeClass("green-jungle");
            $(this).addClass("red-mint");
            //删除自适用样式表
            changeRowAutoHeightCss(false);
            $.alert(0, "取消高度自适应模式");
        }
    });
    //初始化自适应高度模式
    function initAutoHeightMode() {
        if ($("#dsDiv").children(".lyrow:first") && $("#dsDiv").children(".lyrow:first").hasClass("autoHt")) {
            $("#autoHeightBt").removeClass("red-mint");
            $("#autoHeightBt").addClass("green-jungle");
        }
    }

    initCommonStyleSettingPanel();
    initAutoHeightMode();
});

function initCommonStyleSettingPanel() {
    //初始化共有样式配置面板
    // $(".commonStylePanel").find("caption").text("统一样式设置");
    var $body = $(".commonStylePanel").find(".portlet-body");
    // $body.commonStyleList({});

    //获取配置项
    var jsonPath = contextPath + "/scripts/designer/components/json/" + "commonStyle.json";
    var nodes = {};
    $.ajax({
        url: jsonPath,
        dataType: "json",
        async: false,
        success: function(jsonRule) {
            if (jsonRule)
                nodes = jsonRule;
        },
        error: function(xhr, ts) {
            console.log('加载控件配置项失败！');
        }
    });
    initCommonStyleList($body, nodes);
    $(".commonStylePanel").draggable({
        width: "100%",
        height: "100%",
    });
    // $.Toolbox.prototype.data = nodes;
    // if(nodes.propertyPackages&&nodes.propertyPackages.length>0){
    //    $.Toolbox.prototype._init(target);
    // }
    // if(callback&&callback!=null&&callback!=""){
    //     var funcs=callback.split(",");
    //     var func;
    //     $.each(funcs,function(i,item){
    //         func = eval(item);
    //         func();
    //     });
    // }
};
/**
 * 根据已选中的控件(jquery对象)设置公共样式的数据
 * @param  {[type]} $component [已选中的控件(jquery对象)]
 * @return {[type]}            [description]
 */
function setCommonStyleDataByComponent($component) {
    initCommonStyleList.toolbox.dbclickComponent = $component;
    var $item = $component.find(".mainObj");
    var htmlItem = $component[0];
    if ($item[0]) {
        htmlItem = $item[0];
    }
    var componentStyles = htmlItem.style;
    $.each(componentStyles, function(key, value) {
        var $commonStyle = $(".commonStylePanel").find("#" + value);
        if ($commonStyle.attr("type") && $commonStyle.attr("type") == 'btn') {
            // var $targetStyle = $commonStyle.find(".btn[value='"+componentStyles[value]+"']");
            $commonStyle.find(".btn[value='" + componentStyles[value] + "']").addClass("active");
        } else {
            $(".commonStylePanel").find("#" + value).val(componentStyles[value]).change();
        }
    })

};

function initCommonStyleList($body, nodes) {
    var $form_inline = $("<div class='form-inline' style=''></div>");
    $body.append($form_inline);
    var toolbox = new $.commonStyleToolbox();
    $.each(nodes, function(i, node) {
        $form_inline.append(toolbox.createProperty(node));
    })
    initTouchSpin();
    initColorPicker();
    //initSlider();
    initRangeSlider();
    initCommonStyleList.toolbox = toolbox;
}

function changeRowAutoHeightCss(autoFlag) {
    if (autoFlag) {
        $(".designer").find(".lyrow.layout_elem").addClass("autoHt");
        $(".designer").find(".lyrow.layout_elem").children(".view").addClass("autoHt");
        $(".designer").find(".lyrow.layout_elem").children(".view").children(".row").addClass("autoHt");
        $(".designer").find(".lyrow.layout_elem").children(".view").children(".row").children(".column").addClass("autoHt");
        //获取自定义高度的行
        //获取所有行
        var customHeightRows = $(".designer").find(".lyrow.layout_elem[data-rule*=\\\"height_unit\\\"\\:\\\"px\\\"]");
        $.each(customHeightRows, function(i, row) {
            $(row).removeClass("autoHt");
            $(row).children(".view").removeClass("autoHt");
        });
    } else {
        $(".designer").find(".autoHt").removeClass("autoHt");
    }
}
//排序禁用
function disableSortable() {
    setTimeout(function() {
        $(".designer:hidden,.designer .column:hidden,.designer .containerComp:hidden").sortable("disable");
    }, 152);
}
//排序启用
function enableSortable() {
    $(".designer,.designer .column.ui-sortable-disabled,.designer .containerComp.ui-sortable-disabled").sortable("enable");
}

//如果页面内容为空，则自动弹出模板选择页面
$(function() {
    if ($("#dsDiv").html().trim() == "") {
        $("#themeTemplate").trigger("click")
    }
});

//增加自适应高度样式
function addAutoHeightClass(obj) {
    if (obj.hasClass("layout_elem")) {
        obj.addClass("autoHt");
        obj.find(".view").addClass("autoHt");
        obj.find(".view>.row").addClass("autoHt");
        obj.find(".view>.row>.column").addClass("autoHt");
    }
}