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
        success: function (rdata) {
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
        error: function () {
            console.log("获取控件列表数据失败");
        }

    });
}
function createCommonMenu() {
    var lidom = $("<li class=\"nav-item start active open\"><a href=\"#\" class=\"nav-link nav-toggle\"><i class=\"icon-star\"></i><span class=\"title\">常用控件</span><span class=\"selected\"></span><span class=\"arrow open\"></span></a></li>");
    var uldom = $("<ul class=\"sub-menu favoritePanel\"></ul>");
    createMenu(commonComps, uldom, '', 1);
    lidom.append(uldom);
    $(".page-sidebar-menu .heading").before(lidom);
}
//创建控件类型下拉选择项
function createTypeSelect(types) {
    $("#type-select").empty();
    var option;
    $.each(types, function (i, type) {
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
    $("#type-select").bind("change", function () {
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
                function (i, node) {
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
                        if(!pnode.hasClass("favoritePanel")){
                            commonComps.push(node);
                        }
                    }

                    //常用控件加上五角星
                    if(!node.children || node.children.length == 0){
                        var $favoriteBtn = $("<span ></span>");
                        $favoriteBtn.addClass("favoriteBtn");
                        var $i = $("<i></i>");
                        $favoriteBtn.append($i);
                        //fa fa-star
                        if(node.baseComp && node.baseComp == 1){
                            $i.addClass("favorite");
                            $i.addClass("fa fa-star");    
                        }else{
                            // $i.addClass("nofavorite");
                            // $i.addClass("fa fa-star-o");    
                        }
                        lidom.data("data",node);
                        lidom.attr("componentId",node.id);
                        lidom.append($favoriteBtn);    
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
                    if (("" + node.dataRole).indexOf("col-md") > -1 || ("" + node.dataRole).indexOf("aspectlayout") > -1) {
                        adom.addClass("layout");
                    } else {
                        adom.addClass("elem");
                    }
                    adom.attr("href", "#");
                    if (node.url && node.url != "") {
                        adom.attr("onclick", "openUrl("
                            + node.id + ",\"" + node.name
                            + "\",\"" + node.url + "\",\""
                            + node.showmenu + "\")");
                    }
                    if (l == 0) {
                        if(node.smallImageFileName!=undefined){
							adom
                            .append("<i class=\""+node.smallImageFileName+"\"></i>");
							adom.append("<span  class=\"title\">" + node.name
							+ "</span>");
						}else{
							adom
								.append("<i class=\"icon-puzzle\"></i>");
							adom.append("<span  class=\"title\">" + node.name
							+ "</span>");
						}
                    } else {
                        //adom
                           // .append("<i class=\"icon-bulb\"></i>");
                        var $img = $("<img title=\""+ node.name+ "\" src=\""+contextPath+"/scripts/designer/images/component/"+node.dataRole+".png\"/>");
                        $img.attr("onerror","javascript:this.src='"+contextPath+"/scripts/designer/images/component/empty.png'");
						  adom.append($img);
						  adom.append("<span  class=\"title\" style=\"display:none;\">"+node.name+"</span>");
                          adom.append("<div  class='menulicontent' title=\""+ node.name+ "\" >"+node.name+"</span>");
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

						if(i==nodes.length-1&&(i+1)%3>0 && !(pnode.attr("id") && pnode.attr("id") == "sidebar-menu")){
						  //不够三个补齐
						  var addli,adda,addimg,addspan;
						  for(var j=0;j<3-(i+1)%3;j++){
							  addli=$("<li class=\"nav-item emptyItem\"></li>");
							  adda=$("<a></a>");
							  addimg=$("<img border=\"0\" src=\""+contextPath+"/scripts/designer/images/component/empty.png\" style=\"height:32px;\"/>");
							  addspan=$("<span></span>");
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

/**
 * 在常用控件栏中删除常用控件
 * @param  {[type]} node [description]
 * @return {[type]}      [description]
 */
function removeCommonComps(node){
    // var $parentDom = $($(".start .sub-menu")[0]);
    var $parentDom = $(".favoritePanel");
    var $lidom = $parentDom.find("[componentId='"+node.id+"']");
    //删除
    $lidom.remove();
    //遍历常用控件列表，删除对应的控件
    $.each(commonComps,function(i,item){
        if(item.id = node.id){
            commonComps.splice(i,1);
            return false;
        }
    })
    var $emptyItem = $parentDom.find(".emptyItem");
    if($emptyItem.length == 2){
        //刚好拥有3个时，直接去掉，即刚好一行为空
        $emptyItem.remove();
    }else{
        //最后加上一个空栏（用于占位置）
        var addli=$("<li class=\"nav-item emptyItem\"></li>");
        var adda=$("<a></a>");
        var addimg=$("<img border=\"0\" src=\""+contextPath+"/scripts/designer/images/component/empty.png\" style=\"height:32px;\"/>");
        var addspan=$("<span></span>");
        adda.append(addimg);
        adda.append(addspan);
        addli.append(adda);
        $parentDom.append(addli);
    }
}
/**
 * 在常用控件栏中添加常用控件
 * @param  {[type]} node [description]
 * @return {[type]}      [description]
 */
function addCommonComps(node){
    // var $parentDom = $($(".start .sub-menu")[0]);
    var $parentDom = $(".favoritePanel");
    var lidom = $parentDom.find(".emptyItem")[0];
    if(lidom){
        lidom = $(lidom);
        lidom.removeClass("emptyItem");
        lidom.empty();
    }else{
        lidom = $("<li></li>");
        $parentDom.append(lidom);
        //新的补上最后2个
        for(var j=0;j<2;j++){
            var addli=$("<li class=\"nav-item emptyItem\"></li>");
            var adda=$("<a></a>");
            var addimg=$("<img border=\"0\" src=\""+contextPath+"/scripts/designer/images/component/empty.png\" style=\"height:32px;\"/>");
            var addspan=$("<span></span>");
            adda.append(addimg);
            adda.append(addspan);
            addli.append(adda);
            $parentDom.append(addli);
        }
    }

    lidom.addClass("nav-item");
    adom = $("<a data_role='" + node.dataRole + "'  data_id='" + node.id + "'></a>");

    if (node.pDataRole != undefined && node.pDataRole != '') {
        adom.attr("pdata_role", pDataRole);
    }

    if (node.baseComp && node.baseComp == 1) {
        commonComps.push(node);
    }

    //常用控件加上五角星
    if(!node.children || node.children.length == 0){
        var $favoriteBtn = $("<span ></span>");
        $favoriteBtn.addClass("favoriteBtn");
        var $i = $("<i></i>");
        $favoriteBtn.append($i);
        //fa fa-star
        if(node.baseComp && node.baseComp == 1){
            $i.addClass("favorite");
            $i.addClass("fa fa-star");    
        }else{
            // $i.addClass("nofavorite");
            // $i.addClass("fa fa-star-o");    
        }
        lidom.data("data",node);
        lidom.attr("componentId",node.id);
        lidom.append($favoriteBtn);    
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
        adom.attr("onclick", "openUrl("
            + node.id + ",\"" + node.name
            + "\",\"" + node.url + "\",\""
            + node.showmenu + "\")");
    }

    var $img = $("<img title=\""+ node.name+ "\" src=\""+contextPath+"/scripts/designer/images/component/"+node.dataRole+".png\"/>");
    $img.attr("onerror","javascript:this.src='"+contextPath+"/scripts/designer/images/component/empty.png'");
    adom.append($img);
    adom.append("<span  class=\"title\" style=\"display:none;\">"+node.name+"</span>");
    adom.append("<div  class='menulicontent' title=\""+ node.name+ "\" >"+node.name+"</span>");
    lidom.append(adom);
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
    $.each(compTemplateCategoryJsonDataTree, function (i, compTemplateCategoryJsonData) {
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
        $.each(data.children, function (i, item) {
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
            success: function (rdata) {
                $(".templateli").empty();
                if (rdata != null && rdata.templates != null) {
                    $.each(rdata.templates, function (i, item) {
                        createTemplateItem($(".templateli"), item);
                    });
                }

            },
            error: function () {
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
$('#template-select').change(function () {
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
$(window).resize(function () {
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
    $(".designer").delegate(".remove", "click", function (e) {
        e.preventDefault();
        $(this).parent().remove();
        if (!$(".designer .lyrow").length > 0) {
            clearDesigner()
        }
        clashCompute();
    });
    $(".designer").delegate(".myremove", "click", function (e) {
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
    $(".designer").delegate(".removecol", "click", function (e) {
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
    $("#elem_toolbar").hide();
    //控件列表清空
    $(".componentlist").componentlist("empty_");
    layouthistory = null;
    if (supportstorage())
        localStorage.removeItem("layoutdata");
}
$(function () {
    //settingFrameHeight();
    $("body").css("min-height", $(window).height() - 50);
    $("#dsDiv").css("min-height", $(window).height() - 56);
    //$("#dsDiv").css("height", $(window).height() - 56);
    restoreData();
    initdraggable();
    initContainer(true);
    //预览按钮绑定事件
    $("#previewBt").click(function () {
        //公用样式面板隐藏
        $(".commonStylePanel").hide();
        $("#elem_toolbar").hide();
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
    $("#editBt").click(function () {
        //公用样式面板显示
        $(".commonStylePanel").show();
        $("#elem_toolbar").show();
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
        $("[contenteditable]").attr("contenteditable","true");
        $(this).addClass("active");
        $("#dsDiv").css("height", "");
        return false
    });
    $("#saveBt").click(function (e) {
        e.preventDefault();
        handleSaveLayout();
        if ($(".designer").html().trim() == '') {
            $.alert(1, "设计页面内容为空");
            return;
        }

        //寻找包含data-role的非栅格，非label，非form控件
        var $notNameObj = $('#dsDiv').find("[data-role]:not([data-role^=col]):not([data-role=label]):not([data-role=buttongroup]):not(form)");
        // var errorStr = '以下控件未设置控件名称，请设置后再保存!/n';
        var errorStr = '';
        $.each($notNameObj,function(i,item){
            var cname = item.getAttribute("cname");
            if(!cname || cname.indexOf(item.id) > -1){
                if(errorStr){
                    errorStr += ",";
                }
                errorStr += item.id;
                $(item).addClass("noSetName");
            }
        })
        if(errorStr){
            errorStr = '以下控件未设置控件名称，请设置后再保存!\n' + errorStr;
            alert(errorStr);
            // return;
        }

        //保存前调用需要Destroy的控件
        //metro_destroy.destroyAll();

        convertLayoutHtml();
        //获取全局样式
        var globalStyle = $(".designer").attr("data-rule");
        var params = {id: id, pId: pId, pageLayout: $("#pageLayout").text().trim(), pageDesigner: $("#pageDesigner").text().trim()};
        params.designerJson = globalStyle;
	    $(this).attr("disabled",true);
		var $this=$(this);
        $.ajax({
            url: contextPath + "/mx/page/designer/save",
            type: "post",
            async: true,
            data: params,
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            success: function (rdata) {
                if (rdata.result == 1) {
                    $.alert(0, "保存成功!");
                    //进行控件初始化
                    $.ajax({
                        url: contextPath + '/mx/form/defined/initPageComponent',
                        type: 'post',
                        data: {
                            id: id
                        },
                        dataType: 'json',
                        async: true,
                        success: function(msg) {
                            if (msg && msg.statusCode == '500') {
                                console.log(msg.message || "控件初始化失败!");
                                // $.alert(1, "控件初始化失败!" || msg.message);
                            } else {
                                console.log(msg.message || "控件初始化成功!");
                                // $.alert(0, "控件初始化成功!");
                            }
                        },
                        complete: function(){
                        },
                        error: function(e, errorText, errorThrown) {
                            console.log("控件初始化异常!");
                            // $.alert(1, "控件初始化异常!");
                        }
                    })
                }else if(rdata.result == -2){
                    $.alert(1, "保存失败，页面设计信息丢失! 请刷新页面重试。");
                } else if (rdata.result == -999) {
                    $.alert(1, "没有权限。");
                } else {
                    $.alert(1, "保存失败!");
                }
				$this.attr("disabled",false);
            },
            error: function () {
                $.alert(1, "保存出错!");
				$this.attr("disabled",false);
            },
            complete: function (XHR, TS) {
                //保存后调用需要Restore的控件
                //metro_restore.restoreAll();
				$this.attr("disabled",false);
            }
        });

    });
    //清空按钮绑定事件
    $("#emptyBt").click(function () {
        clearDesigner();
        // $(".designer").empty();
        // $(".ui-list").empty();
        // layouthistory = null;
        // if (supportstorage())
        //     localStorage.removeItem("layoutdata");
    });
    //导出按钮绑定事件
    $("#exportBt")
        .click(
            function () {
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
    $("#designerExportBt")
        .click(
            function () {
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
    $("#designerImportBt")
        .click(
            function () {
                $('#importDesignerModal').modal('show');
                $("#fileinput").fileinput('refresh');
     });
	 //页面模板选择按钮绑定事件
    $("#themeTemplate")
        .click(
            function () {
                $('#pageTemplateModal').modal('show');
     });
	 //自由布局按钮绑定事件
	 $("#layoutDefined")
        .click(
            function () {
                $('#freelayoutModal').modal('show');
				$("#freelayoutModal").layout("refresh_");
     });
    //初始化fileinput控件
    function initFileInput(ctrlName, uploadUrl) {
        var control = $('#' + ctrlName);

        control.fileinput({
            language: 'zh', //设置语言
            uploadUrl: uploadUrl, //上传的地址
            allowedFileExtensions: ['tmpl', 'html', 'htm'],//接收的文件后缀
            'showPreview': false,
            maxFileSize: 2000,
            multiple: false

        });
        //导入完成后回调方法
        control.on('fileuploaded', function (event, data, previewId, index) {
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
    setInterval(function () {
        handleSaveLayout()
    }, timerSave);
    //响应式模拟
    $(".reponsive-block li>a").click(function () {

        $(".reponsive-block li>a").removeClass("active");
        var designerWidth = $(this).attr("data-width");
        var currentWidth = $(".designer").width();
        //PC
        if (designerWidth == 'auto') {
            $(".designer").width("auto")
        }
        else if (($(window).width() - 230) >= designerWidth) {
            $(".designer").width(designerWidth)
        }
        $(this).addClass("active");

    });
    //设置页面为编辑模式
    //document.designMode = "on";
    $("#setStaticData").click(function(event){
        var $this = $(this);
        if($this.attr("disabled")){
            return;
        }
        var $component = window.currentComponent;
        if($component[0]){
            var dataRole = $component.attr("data-role");
            var ctrImpl = "showStaticDataPanle";
            try{
                ctrImpl = dataRole!=""?eval(dataRole+"_designer."+ctrImpl):eval(ctrImpl);
                ctrImpl();
            }catch(e){
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
        $.each(rows, function (i, row) {
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
    $.each(lyrows, function (i, lyrow) {
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
        //去掉不需要
        //currRow.attr("scope", scope);
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
    //去掉对齐线、工具栏、拖动点
    $layoutHtml.find("[class^=ui-resizable-],#elem_toolbar,.x-Snapline,.y-Snapline,.xyPositionBox").remove();
    $layoutHtml.find(".lyrow").addClass(
        "removeClean");
    $layoutHtml.find(".box-element").addClass(
        "removeClean");
    $layoutHtml
        .find(
            ".lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .removeClean")
        .each(function () {
            cleanHtml(this);
        });
    $layoutHtml
        .find(
            ".lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .removeClean")
        .each(function () {
            cleanHtml(this);
        });
    $layoutHtml
        .find(
            ".lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .removeClean")
        .each(function () {
            cleanHtml(this);
        });
    $layoutHtml
        .find(
            ".lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .removeClean")
        .each(function () {
            cleanHtml(this);
        });
    $layoutHtml
        .find(
            ".lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .removeClean")
        .each(function () {
            cleanHtml(this);
        });
    $layoutHtml
        .find(
            ".lyrow .lyrow .lyrow .lyrow .lyrow .removeClean")
        .each(function () {
            cleanHtml(this);
        });
    $layoutHtml
        .find(
            ".lyrow .lyrow .lyrow .lyrow .removeClean")
        .each(function () {
            cleanHtml(this);
        });
    $layoutHtml.find(
        ".lyrow .lyrow .lyrow .removeClean")
        .each(function () {
            cleanHtml(this);
        });
    $layoutHtml.find(".lyrow .lyrow .removeClean")
        .each(function () {
            cleanHtml(this);
        });
    $layoutHtml.find(".lyrow .removeClean").each(
        function () {
            cleanHtml(this);
        });
    $layoutHtml.find(".containerComp .containerComp .containerComp .containerComp .removeClean").each(
        function () {
            cleanHtml(this);
        });
    $layoutHtml.find(".containerComp .containerComp  .containerComp .removeClean").each(
        function () {
            cleanHtml(this);
        });
    $layoutHtml.find(".containerComp .containerComp .removeClean").each(
        function () {
            cleanHtml(this);
        });
    $layoutHtml.find(".containerComp .removeClean").each(
        function () {
            cleanHtml(this);
        });
    $layoutHtml.find(".removeClean").each(
        function () {
            cleanHtml(this);
        });
    $layoutHtml.find(".removeClean").each(function (index, el) {
        $(el).remove();
    });
    $layoutHtml.find('[contenteditable=true]').each(function (index, el) {
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
    $.each(fitsizeRows, function (i, fitsizeRow) {
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
    $.each(charts, function (i, chart) {
        $(chart).attr("chartType", $(chart).attr("data-role"));
        $(chart).attr("id", $(chart).attr("id").replace($(chart).attr("data-role"), "charts"));
        $(chart).attr("data-role", $(chart).attr("pdata-role"));
    });
    var charts = $(outPutHtml).find("[pdata-role='echarts']");
    $.each(charts, function (i, chart) {
        $(chart).attr("chartType", $(chart).attr("data-role"));
        $(chart).attr("id", $(chart).attr("id").replace($(chart).attr("data-role"), "echarts"));
        $(chart).attr("data-role", $(chart).attr("pdata-role"));
    });
    $("#pageLayout").text(outPutHtml.html());
    $layoutHtml = $(".designer").clone();
    outPutHtml.empty();
    outPutHtml.append($layoutHtml.html());
    outPutHtml.find('.shine_red').removeClass("shine_red");
    //删除工具栏、对齐线、拖动点
    outPutHtml.find("[class^=ui-resizable-],#elem_toolbar,.x-Snapline,.y-Snapline,.xyPositionBox").remove();
    //去掉未设置名称的class
    outPutHtml.find(".noSetName").removeClass("noSetName");

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
    $.each(colums, function (i, item) {
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
/**
 * 根据dataRole，获取对应主题默认控件的样式及HTML代码
 * @param  {[type]} elementType []
 * @param  {[type]} targetElem [目标HTML]
 * @return {[type]}             [description]
 */
function createCompByTheme(elementType,targetElem){
    window.compThemeObj = window.compThemeObj || {};
    if(!window.compThemeObj[elementType]){
        //如果没有的话重新加载
        var params = {
            compType : elementType,
            themeTmpId : _themeTmpId,
            defaultFlag : 1,
        }
        var option = {
            url: contextPath + "/mx/theme/sysThemeTmpControl/list",
            async: false,
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(params),
            dataType: 'json',
            success: function(ret){
                window.compThemeObj[elementType] = ret.rows[0];    
            }
        }
        $.ajax(option);
    }
    if(window.compThemeObj[elementType]){
        if(window.compThemeObj[elementType].HTML){
            //如果主题拥有自己的HTML代码,则重新生成
            targetElem = $(window.compThemeObj[elementType].HTML);
        }
        //加上对应的主题code
        targetElem.attr("controlCode",window.compThemeObj[elementType].controlCode);
        targetElem.addClass(window.compThemeObj[elementType].controlCode);
    }
    return targetElem;
}
//布局控件拖动对象
function initdraggable() {
    $(".layout").draggable(
        {
            connectToSortable: ".designer",
            cursor: "move",
            //snapTolerance:10,
            helper: function (event) {
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
            start: function (e, t) {
                if (!startdrag) stopsave++;
                startdrag = 1;
            },
            drag: function (e, t) {
                //t.helper.width(400);
                //t.helper.find(".view").css("display","none");
            },
            stop: function (e, t) {

                var moveObj = $(t.helper);
                if (moveObj.parent().hasClass("nav-item")) {
                    return;
                }
                var elementType = moveObj.attr("data-role");
                var pElementType = moveObj.attr("pdata-role") != undefined ? moveObj.attr("pdata-role") : "";
                var scope = moveObj.attr("scope");
                var integral = moveObj.attr("integral");
                var targetElem, gridlayout;

                if ($('#' + elementType)
                    && $('#' + elementType).html()) {
                    targetElem = $($('#' + elementType).html());
                    //获取工具按钮组（编辑、删除、拖动）
                    gridlayout = $($("#gridlayout").html());
                    gridlayout.find(".view").append(targetElem);
                    targetElem = gridlayout;
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
                        contextPath + "/images/component/"
                        + elementType + ".png");
                    targetElem.css("vertical-align", "middle");
                }
                //加入主题
                targetElem = createCompByTheme(elementType,targetElem);

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

                $(".componentlist").componentlist("addComponentTreeNode",targetElem);
            }
        });
    //非布局控件拖动对象
    var currentDragComponent;
    $(".elem:not([data_role=form_group])").draggable(
        {
            connectToSortable: ".column,.containerComp,.myformgroup",//启用此属性helper需设置为clone，否则droppable drop会执行两次；后面对droppable drop方法进行了处理
            cursor: "move",
            //snapTolerance:10,
            helper: function (event) {
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
            start: function (e, t) {
                var moveObj = $(t.item);
                beforeDropElemStyle(moveObj);
                if (!startdrag) stopsave++;
                startdrag = 1;
            },
            drag: function (e, t) {
                //t.helper.width(400);
                //t.helper.find(".view").css("display","none");
            },
            stop: function (e, t) {
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
                if ($('template#' + elementType)
                    && $('template#' + elementType).html()) {
                    targetElem = $($('template#' + elementType).html());
                }
                else if ($.template[elementType]
                    && $.template[elementType] != '') {
                    targetElem = $($.template[elementType]);
                } else {
                    targetElem = $("<img class='img-responsive'></img>");
                    targetElem.attr("src",
                        contextPath + "/images/component/"
                        + elementType + ".png");
                    targetElem.css("vertical-align", "middle");
                }

                //加入主题
                targetElem = createCompByTheme(elementType,targetElem);
                var dtStr = new Date().getTime();
                var id = elementType + "_" + dtStr;
                targetElem.attr("id", id);

                if(elementType && elementType == 'label'){
                    //如果控件为label则默认不生成名称
                }else{
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
                $.each(childComp, function (i, item) {
                    oldId = $(item).attr("id");
                    dtStr = new Date().getTime() + i;
                    nId = $(item).attr("data-role") + "_" + dtStr;
                    $(item).attr("id", nId);
                    //替换class中的id
                    var childRetain = $(item).find('.id');
                    if (childRetain.length > 0) {
                        $.each(childRetain, function (i, o) {
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
                        $.each(retain, function (i, o) {
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
                    elementTemplate.attr("id", id+ "_ct");
                    moveObj.replaceWith(elementTemplate);
                    changeElemStyle(elementTemplate);
                    $(".componentlist").componentlist("addComponent",elementTemplate);
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
				compRenderMethod.render(id,dataId);
            }
        });

    $(".elem[data_role=form_group]").draggable(
        {
            connectToSortable: ".myform",//启用此属性helper需设置为clone，否则droppable drop会执行两次；后面对droppable drop方法进行了处理
            cursor: "move",
            //snapTolerance:10,
            helper: function (event) {
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
            start: function (e, t) {
                var moveObj = $(t.item);
                beforeDropElemStyle(moveObj);
                if (!startdrag) stopsave++;
                startdrag = 1;
            },
            drag: function (e, t) {
                //t.helper.width(400);
                //t.helper.find(".view").css("display","none");
            },
            stop: function (e, t) {
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
                if ($('#' + elementType)
                    && $('#' + elementType).html()) {
                    targetElem = $($('#' + elementType).html());
                }
                else if ($.template[elementType]
                    && $.template[elementType] != '') {
                    targetElem = $($.template[elementType]);
                } else {
                    targetElem = $("<img class='img-responsive'></img>");
                    targetElem.attr("src",
                        contextPath + "/images/component/"
                        + elementType + ".png");
                    targetElem.css("vertical-align", "middle");
                }

                //加入主题
                targetElem = createCompByTheme(elementType,targetElem);
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
                $.each(childComp, function (i, item) {
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
				compRenderMethod.render(id,dataId);
            }
        });

    $(".template").draggable(
        {
            connectToSortable: ".column",//启用此属性helper需设置为clone，否则droppable drop会执行两次；后面对droppable drop方法进行了处理
            cursor: "move",
            //snapTolerance:10,
            helper: function (event) {
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
            start: function (e, t) {
                var moveObj = $(t.item);
                beforeDropElemStyle(moveObj);
                if (!startdrag) stopsave++;
                startdrag = 1;
            },
            drag: function (e, t) {
                //t.helper.width(400);
                //t.helper.find(".view").css("display","none");
            },
            stop: function (e, t) {
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
                $.each(childComp, function (i, item) {
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
                $.each(layoutComps, function (i, layoutComp) {
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
                $.each(elemComps, function (i, elemComp) {
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
                        elementTemplate.attr("id", elementType + "_"
                            + dtStr + "_ct");
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
                eleId = e.toElement.tagName == "LI" ? $toEle.attr("id") : $toEle.closest("li").attr("id"),
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
    $(".designer .mybox").draggable(
        {
            connectToSortable: ".column,.containerComp",
            cursor: "move",
            handle: ".drag",
            scroll: false,
            revert: "invalid",
            helper: function (event) {
                currentDragComponent = $(this);
                var elementName = $(this).find('.preview>input:first').val();
                var targetElem = $("<div class=\"elemtag\">" + elementName + "</div>");
                //设置元素位置为当前光标位置
                var position = getMousePos();
                targetElem.css("position", "absolute");
                targetElem.offset({left: position.x - (100 / 2), top: position.y - (60 / 2)});
                $(this).css("position", "absolute");
                $(this).offset({left: position.x - (100 / 2), top: position.y - (60 / 2)});
                return targetElem;
            },
            drag: function (e, t) {
                $(this).css("display", "none");
            },
            stop: function (e, t) {
                var moveObj = $(t.helper);
                moveObj.replaceWith(currentDragComponent);
                changeElemStyle(currentDragComponent);
            }
        });
    $(".designer .myformgroup").draggable(
        {
            connectToSortable: ".myform",
            cursor: "move",
            scroll: false,
            revert: "invalid",
            helper: function (event) {
                currentDragComponent = $(this);
                var targetElem = $("<div class=\"elemtag\">表单控件组</div>");
                //设置元素位置为当前光标位置
                var position = getMousePos();
                targetElem.css("position", "absolute");
                targetElem.offset({left: position.x - (100 / 2), top: position.y - (60 / 2)});
                $(this).css("position", "absolute");
                $(this).offset({left: position.x - (100 / 2), top: position.y - (60 / 2)});
                return targetElem;
            },
            drag: function (e, t) {
                $(this).css("display", "none");
            },
            stop: function (e, t) {
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
    $(".designer .myformgroup").children(":not(.formgrouptoolbar)").draggable(
        {
            connectToSortable: ".myformgroup",
            cursor: "move",
            scroll: false,
            revert: "invalid",
            helper: function (event) {
                currentDragComponent = $(this);
                var targetElem = $("<div class=\"elemtag\">表单控件</div>");
                //设置元素位置为当前光标位置
                var position = getMousePos();
                targetElem.css("position", "absolute");
                targetElem.offset({left: position.x - (100 / 2), top: position.y - (60 / 2)});
                $(this).css("position", "absolute");
                $(this).offset({left: position.x - (100 / 2), top: position.y - (60 / 2)});
                return targetElem;
            },
            drag: function (e, t) {
                $(this).css("display", "none");
            },
            stop: function (e, t) {
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
$('#undoBt').click(function () {
    stopsave++;
    if (undoLayout()) initContainer();
    draggEventBind();
    stopsave--;
    clashCompute();
});
//重做
$('#redoBt').click(function () {
    stopsave++;
    if (redoLayout()) initContainer();
    draggEventBind();
    stopsave--;
    clashCompute();
});
//ui查询绑定事件
$('#ui-select').change(function () {
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
    return {'x': x, 'y': y};
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
        cursorAt: {   //指定拖动条目时，光标出现的位置，取值是对象，可以包含top、left、right、Bottom属性中的一个或两个。 默认值 false
            top: 5,
            right: 125
        },
        start: function (e, t) {
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
        stop: function (e, t) {
            var moveObj = $(t.item);
            var contId = moveObj.attr("id");
            changeElemStyle(moveObj);
            if (stopsave > 0) stopsave--;
            startdrag = 0;
        },
        deactivate: function (e, t) {
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
        cursorAt: {   //指定拖动条目时，光标出现的位置，取值是对象，可以包含top、left、right、Bottom属性中的一个或两个。 默认值 false
            top: 5,
            right: 125
        },
        start: function (e, t) {
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
        stop: function (e, t) {
            var moveObj = $(t.item);
            var contId = moveObj.attr("id");
            if (stopsave > 0) stopsave--;
            startdrag = 0;
        },
        deactivate: function (e, t) {
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
        start: function (e, t) {
            if ($(".holderCss")) {
                $(".holderCss").text("可拖入元素");
            }
            var moveObj = $(t.item);
            //设置元素位置为当前光标位置
            var position = getMousePos();
            moveObj.css("position", "absolute");
            moveObj.offset({left: position.x, top: position.y});
            //t.helper.height(100);
            if (!moveObj.hasClass('elemtag')) {
                //moveObj.width(50);
                beforeDropElemStyle(moveObj);
            }
            if (!startdrag) stopsave++;
            startdrag = 1;
        },
        stop: function (e, t) {
            var moveObj = $(t.item);
            var contId = moveObj.attr("id");
            changeElemStyle(moveObj);
            if (stopsave > 0) stopsave--;
            startdrag = 0;
        },
        deactivate: function (e, t) {
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
    $(".designer").find("[scope]").each(function (i, item) {
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
        $(scopeArray).each(function (i, item) {
            $(".ui-list").append("<li class=\"glyphicon glyphicon-edit\">" + item.toLocaleUpperCase() + "</li>");
        });
    }
    //$('#warningModal').modal('show');
}
//警告框显示时调用事件
$('#warningModal').on('show.bs.modal', function () {
    $(this).find(".modal-body").html("<p>新增控件与页面控件存在冲突,是否继续？</p>");
});
//关闭警告框
$('.modalClose').click(function () {
    stopsave++;
    if (undoLayout()) initContainer();
    draggEventBind();
    stopsave--;
    $(".ui-list").html(hisUiList);
    $('.modalcontainer').removeClass("my-modal");
    $('.modalcontainer').addClass("hidemy-modal");
});
//关闭警告框
$('.modalContinue').click(function () {
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
Array.intersect = function (a, b) {
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
Array.prototype.uniquelize = function () { // 去重
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
$.alert = function (type, msg) {
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
    $(".myalert").fadeIn(500).delay(1000).fadeOut(500);
    ;
}

/**
 * 编辑按钮绑定点击事件
 *
 */
function   getAbsPoint(e)   
{   
    var   x   =   e.offsetLeft,   y   =   e.offsetTop;   
    while(e=e.offsetParent) 
    { 
        x   +=   e.offsetLeft;   
        y   +=   e.offsetTop;
    }
    return{left:x,top:y};
}
var currentComponent;

function loadCompTheme($component){
    var compType = $component.attr("data-role");
    var selectedCompThemeCode = $component.attr("controlCode");
    if(CompThemeManage){
        window.compThemeManage = window.compThemeManage || new CompThemeManage(_themeTmpId,chageCompThemeFunc);
        window.compThemeManage.loadCompTheme(compType,selectedCompThemeCode);    
    }
}

function chageCompThemeFunc(themeData){
    var $nowComponent = currentComponent;
    if(!currentComponent.attr("data-role")){
        $nowComponent = currentComponent.find("[data-role]");
    }

    var lastCompThemeCode = $nowComponent.attr("controlCode");
    if(lastCompThemeCode){
        $nowComponent.removeClass(lastCompThemeCode);
    }
    $nowComponent.attr("controlCode",themeData.controlCode);
    $nowComponent.addClass(themeData.controlCode);
}

$("body").delegate(".designer .mybox","click",function(e){
    if($(e.target).closest(".formgrouptoolbar")[0]){
        //点击表单中的控件信息，跳过。
        return;
    }

    $(".selected").removeClass("selected");

    var key = window.key;
    if(key && key != 0){
        return;
    }else{
        $(".selectedComp").removeClass("selectedComp");
    }
                

    //阻止冒泡
    e.stopPropagation();
    //显示工具栏
    var absPosition = getAbsPoint(this);
    $(".selected").removeClass("selected");
    $(this).addClass("selected");
    //控件列表中的控件设置为选中
    var $datarole = $(this).find(">.view>[data-role]");
    var complisttr_id = "";
    if($datarole[0]){
        complisttr_id = $datarole.attr("id");
        currentComponent = $datarole;
    }else{
        complisttr_id =$(this).attr("id");
        currentComponent = $("#"+complisttr_id);
    }
    $("#"+complisttr_id).addClass("selected");
   

    // currentComponent = $(this).parent().parent().parent().parent();
    
    $(".shine_red").removeClass("shine_red");
    currentComponent.addClass("shine_red");

    $("#elem_toolbar").elem_toolbar("reload_","mybox");
    if($(this).css("display")=="none"){
        $("#elem_toolbar").hide();
    }else{
        $("#elem_toolbar").show();
    }
    
    $(".componentlist").componentlist("selecteNode",complisttr_id.substring(0,complisttr_id.indexOf("_ct")));
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
    if (currentComponent.hasClass("layout_elem") && dataRole != 'aspectlayout') {
        jsId = "coljs";
        js = "<script id='" + jsId + "' type='text/javascript' src='" + contextPath + "/scripts/designer/components/col.js'></script>";
    }
    if ($("#" + jsId).length == 0) {
        $("body").append(js);
    }
    var pDataRole = currentComponent.attr("pdata-role");
    if(pDataRole){
        //若存在父控件，则获取父控件的js信息
        jsId = pDataRole + "js";
        if (pDataRole != undefined && pDataRole != '') {
            js = "<script id='" + jsId + "' type='text/javascript' src='" + contextPath + "/scripts/designer/components/" + pDataRole + ".js'></script>";
        }
        if ($("#" + jsId).length == 0) {
            try{
                $("body").append(js);    
            }catch(e){

            }
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
            $("#quick_sidebar_tab_1").find(".media-list"),
            {
                "contextPath": contextPath
            },
            {
                "type":"template"
            });
    $
        .Toolbox(
            $("#quick_sidebar_tab_2").find(".mt-list-container"),
            {
                "contextPath": contextPath
            },
            {
                "type":"setting"
            }, "initSideBar,openSideBar");

    $
        .Toolbox(
            $("#quick_sidebar_tab_3").find(".mt-list-container"),
            {
                "contextPath": contextPath
            },
            {"type":"static"}, "initSideBar,openSideBar");

    $("#elem_toolbar").elem_toolbar("resize_elem_toolbar");

    var $nowComponent = currentComponent;
    if(!currentComponent.attr("data-role")){
        $nowComponent = currentComponent.find("[data-role]");
    }
    loadCompTheme($nowComponent);
});


/**
 * 双击弹出编辑窗口
 *
 */
$(".designer").delegate("[data-role]", "dblclick", function (e) {
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
    if (currentComponent.hasClass("layout_elem")  && dataRole != 'aspectlayout') {
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
            $("#quick_sidebar_tab_1").find(".media-list"),
            {
                "contextPath": contextPath
            },
            {
                "type":"template"
            });
    $
        .Toolbox(
            $("#quick_sidebar_tab_2").find(".mt-list-container"),
            {
                "contextPath": contextPath
            },
            {"type":"setting"}, "initSideBar,openSideBar");

    $
        .Toolbox(
            $("#quick_sidebar_tab_3").find(".mt-list-container"),
            {
                "contextPath": contextPath
            },
            {"type":"static"}, "initSideBar,openSideBar");

    //找到实际的控件对象
    var $nowComponent = currentComponent;
    if(!currentComponent.attr("data-role")){
        $nowComponent = currentComponent.find("[data-role]");
    }

    //判断是否可以设置模拟数据，不可设置时禁用。
    if($nowComponent[0]){
        var dataRole = $nowComponent.attr("data-role");
        var ctrImpl = "showStaticDataPanle";
        ctrImpl = dataRole!=""?eval(dataRole+"_designer."+ctrImpl):eval(ctrImpl);
        if(!$.isFunction(ctrImpl)){
            $("#setStaticData").attr("disabled","disabled");
        }else{
            $("#setStaticData").removeAttr("disabled");
        }
    }
    
    //加载控件样式
    loadCompTheme($nowComponent);
});

$("body").delegate(".designer [data-role*=col],.designer [data-role=aspectlayout]","click",function(e){
    if($(e.target).closest(".formgrouptoolbar")[0]){
        //点击表单中的控件信息，跳过。
        return;
    }

    if(key && key != 0){
        return;
    }else{
        $(".selectedComp").removeClass(".selectedComp");
    }

    $(".selected").removeClass("selected");
    //阻止冒泡
    e.stopPropagation();
    //显示工具栏
    // var left = $(this).offset().top-document.body.scrollTop-40;
    // var top = $(this).offset().left-document.body.scrollLeft+5;
    // if(top < 50){
    //     top += $(this).height()+10;
    // }
    // $("#elem_toolbar").css("top",top+"px");
    // $("#elem_toolbar").css("left",left+"px");
    // $("#elem_toolbar").elem_toolbar("reload_","col");
    $(".selected").removeClass("selected");
    $(this).addClass("selected");
    //控件列表中的控件设置为选中
    var $datarole = $(e.target);
    var complisttr_id = $(this).attr("id");
    currentComponent = $("#"+complisttr_id);
    
    // var complisttr_id=$(this).attr("id");
    $("#"+complisttr_id).addClass("selected");

    var dataRole = currentComponent.attr("data-role");
    // currentComponent = $(this).parent().parent().parent().parent();
    // currentComponent = $("#"+complisttr_id);
    $(".shine_red").removeClass("shine_red");
    currentComponent.addClass("shine_red");
    $("#elem_toolbar").elem_toolbar("reload_",dataRole=='aspectlayout'?'layout':"col");
    if($(this).css("display")=="none"){
        $("#elem_toolbar").hide();
    }else{
        $("#elem_toolbar").show();
    }
    

    $(".componentlist").componentlist("selecteNode",complisttr_id);
    //获取控件属性
    
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
    if (currentComponent.hasClass("layout_elem") && dataRole != 'aspectlayout') {
        jsId = "coljs";
        js = "<script id='" + jsId + "' type='text/javascript' src='" + contextPath + "/scripts/designer/components/col.js'></script>";
    }
    if ($("#" + jsId).length == 0) {
        $("body").append(js);
    }
    var pDataRole = currentComponent.attr("pdata-role");
    if(pDataRole){
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
            $("#quick_sidebar_tab_1").find(".media-list"),
            {
                "contextPath": contextPath
            },
            {
                "type":"template"
            });
    $
        .Toolbox(
            $("#quick_sidebar_tab_2").find(".mt-list-container"),
            {
                "contextPath": contextPath
            },
            {
                "type":"setting"
            }, "initSideBar,openSideBar");

    $
        .Toolbox(
            $("#quick_sidebar_tab_3").find(".mt-list-container"),
            {
                "contextPath": contextPath
            },
            {"type":"static"}, "initSideBar,openSideBar");

    $("#elem_toolbar").elem_toolbar("resize_elem_toolbar");

    //找到实际的控件对象
    var $nowComponent = currentComponent;
    if(!currentComponent.attr("data-role")){
        $nowComponent = currentComponent.find("[data-role]");
    }

    loadCompTheme($nowComponent);
})

/**
 *栅格列双击事件
 */
$("body").delegate(".designer .column", "click", function (e) {
    if($(e.target).closest(".formgrouptoolbar")[0]){
        //点击表单中的控件信息，跳过。
        return;
    }

    if(key && key != 0){
        return;
    }else{
        $(".selectedComp").removeClass(".selectedComp");
    }

    //阻止冒泡
    e.stopPropagation();
    currentComponent = $(this);
    $(".shine_red").removeClass("shine_red");
    currentComponent.addClass("shine_red");
    //工具栏
    $("#elem_toolbar").elem_toolbar("reload_","col_column");
    if($(this).css("display")=="none"){
        $("#elem_toolbar").hide();
    }else{
        $("#elem_toolbar").show();
    }
    
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
            $("#quick_sidebar_tab_1").find(".media-list"),
            {
                "contextPath": contextPath
            },
            {
                "type":"template"
            });
    $
        .Toolbox(
            $("#quick_sidebar_tab_2").find(".mt-list-container"),
            {
                "contextPath": contextPath
            },
            {
                "type":"setting","dataRole":"div"}, "initSideBar,openSideBar");

    $("#elem_toolbar").elem_toolbar("resize_elem_toolbar");

    //找到实际的控件对象
    var $nowComponent = currentComponent;
    if(!currentComponent.attr("data-role")){
        $nowComponent = currentComponent.find("[data-role]");
    }
    loadCompTheme($nowComponent);
});

/**
 *设计器双击事件 弹出页面全局样式编辑
 */
$(".designer").on("dblclick", function (e) {
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
            $("#quick_sidebar_tab_1").find(".media-list"),
            {
                "contextPath": contextPath
            },
            {
                "type":"template"
            });
    $
        .Toolbox(
            $("#quick_sidebar_tab_2").find(".mt-list-container"),
            {
                "contextPath": contextPath
            },
            {"type":"setting"}, "initSideBar,openSideBar");

});

$(".designer").delegate(".integral", "click", function () {
    //调用编辑按钮的点击事件
    var mybox = $(this).closest(".mybox");
    var editBt = mybox.find(".configuration:first").find(".editBt");
    editBt.trigger("click");
});


/**
 *选择grid系统时改变允许最大分组数
 */
$("input[name='gridsys']").on('ifChecked', function () {
    $("#groupNumber").attr("max", $(this).val());
    $("#groupNumber").trigger("touchspin.updatesettings", {max: $(this).val()});
});

/**
 *栏目划分增加事件绑定
 */
$("#groupNumber").validate({
    onfocusout: true
});
jQuery.validator.addMethod("divideValid", function (value, element) {
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
            $.each(groupdivide.split(","), function (i, item) {
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
    submitHandler: function (form) {//表单提交句柄,为一回调函数，带一个参数：form

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
$(".okBt").click(function () {
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
    $.each(groupdivide.split(","), function (i, item) {
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

//剪贴板
var clipboard;
//复制、剪切操作标识
var copyOrCut="copy";

/**
 *粘贴
 */
$(".designer").delegate(".paste-row", "click", function () {
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
	if(copyOrCut=="copy"){
		var newRow = copyRow(clipboard);
		//子控件重新生成ID
		var nElem = generatorId(newRow);
		currentComponent.replaceWith(nElem);
    }
	else{
		currentComponent.replaceWith(clipboard.clone());
	}
    initContainer();
    initdraggable();
});

//元素对换
var exchange = function (a, b) {
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

function getHandleComponent(o) {
    var ele;
    if ($(o).hasClass("nlayout_elem")) { //判断data-role是否在第一层
        ele = $(o);
    } else {
        ele = $(o).closest(".nlayout_elem"); //data-role在子层就获取父层

    }
    return ele;
}
function formatComponent(o) {   //获取一个对象的 模板
    var ele = $(o), $realEle = getHandleComponent(o);
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
$(".saveEditBt").click(function () {
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
$(".saveEditBt").click(function () {
    //获取编辑器内容
    var content = $("#editModal").find("iframe")[0].contentWindow.getCode();
    var $content = $("<div>").append(content);
    $.each($content.find("[data-role]"), function (i, o) {

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



//获取当前栅格系统
function getGridSysByColumnClass(columnClass) {
    return columnClass.match((/col\-(md|mmd|24md)\-/))[0];
}

function generatorId(obj) {
    var oldFirstId = obj.find(".view:first").children("[data-role]").attr("id");
    var childComp = obj.find("[data-role]");
    var dtStr;
    var nId;
    var oldId;
    var newObj = obj.clone();
    $.each(childComp, function (i, item) {
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
 *粘贴
 */
$(".designer").delegate(".elempaste-row", "click", function () {
    //验证当前剪贴板内容是否为行对象
    if (clipboard == undefined || clipboard == null) {
        $.alert(1, "剪贴板为空");
        return;
    }
    //新建元素对象
    var newElem = clipboard.clone();
	currentComponent = $(this).closest(".mybox");
    currentComponent.empty();
    if(copyOrCut=="copy"){
		//重新生成ID
		var dtStr = new Date().getTime();
		var nId = newElem.attr("data-role") + "_" + dtStr;
		newElem.attr("id", nId);
		//子控件重新生成ID
		var nElem = generatorId(newElem);
		currentComponent.append(nElem.html());
	}else{
		currentComponent.append(newElem);
	}
    initContainer();
    initdraggable();
});
/**
 *自动分栏
 */
$(".autoBt").click(function () {
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
        .each(function () {
            cleanHtml(this)
        });
    $layoutHtml.find(".removeClean").each(function (index, el) {
        if (!$(el).parent().hasClass('containerComp')) {
            $(el).remove();
        }
    });
    $layoutHtml.find('[contenteditable=plaintext-only]').each(function (index, el) {
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
    $.each(layoutComps, function (i, layoutComp) {
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
    $.each(elemComps, function (i, elemComp) {
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
    var desObj = targetElem/*$(targetElem.html())*/;
    currentComponent.contents().each(function(){ 
        if(this.nodeType === 3){ 
            $(this).remove();
        } 
    }); 
    //验证套用对象与被套用对象是否属同一类型控件（行、普通元素、列）
    if (currentComponent.hasClass('column')) {
        currentComponent.children(":not(.removecol,.configuration)").remove();
        currentComponent.append(desObj.html());
    } else if (currentComponent.hasClass('lyrow')) {
        if (/*desObj.hasClass('lyrow')*/desObj.find(">.lyrow").length) {
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
    var $obj = currentComponent.find("[data-role]"), dataRoles = {};
    $.each($obj, function (i, o) {
        var ele = $(o);
        var dataRole = ele.attr("data-role") || "";
        if (!dataRoles[dataRole]) {
            dataRoles[dataRole] = [ele.attr("id") || ""];
        } else {
            dataRoles[dataRole].push(ele.attr("id") || "");
        }
    });
    $.each(dataRoles, function (k, v) {
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
			$.each(v, function (i, o) {
				var id = o;
				if ($("#" + id).attr("render") == "new") {
					compRenderMethod.render(id,componentId);
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
    $.each(childComp, function (i, item) {
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
    $.each(layoutComps, function (i, layoutComp) {
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
    $.each(elemComps, function (i, elemComp) {
        elemCompClone = $(elemComp).clone();
        elementType = elemCompClone.attr("data-role");
        dtStr = new Date().getTime();
        compId = elemCompClone.attr("id");
        elementTemplate = $($('#elementTemplate')
            .html());
        elementTemplate.find(".view").attr("id", compId + "_view");
        elementTemplate.find(".view").append(elemCompClone);
        elementTemplate.find(".preview").html($(this).find('span').text());
        elementTemplate.attr("id", elementType + "_"
            + dtStr + "_ct");
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
function ApplyPageTemplate(content,id) {
	if(id!=undefined){
		//获取当前页面样式，动态加载样式表
		var styleUrl=contextPath+"/rs/designer/"+id+"/getTemplateStyle";
		var styleDom="<link id=\"themeCss\" href=\""+styleUrl+"\" rel=\"stylesheet\" type=\"text/css\" />";
		if($("#themeCss").length>0){
			$("#themeCss").remove();
		}
			$("body").append(styleDom);
		
	}
    var targetElem = $("<div></div>");
	//验证首层元素是否都为栅格row
	//首层不全为栅格
	if(targetElem.children(":not(.lyrow)").length>0){
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

	}else{
		    targetElem.append(content);
	}
    //子控件重新生成ID
    var childComp = targetElem.find("[data-role]");
    var dtStr;
    var nId;
    var oldId;
    $.each(childComp, function (i, item) {
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
    $.each(layoutComps, function (i, layoutComp) {
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
    $.each(elemComps, function (i, elemComp) {
        elemCompClone = $(elemComp).clone();
        elementType = elemCompClone.attr("data-role");
        dtStr = new Date().getTime();
        compId = elemCompClone.attr("id");
        elementTemplate = $($('#elementTemplate')
            .html());
        elementTemplate.find(".view").attr("id", compId + "_view");
        elementTemplate.find(".view").append(elemCompClone);
        elementTemplate.find(".preview").html($(this).find('span').text());
        elementTemplate.attr("id", elementType + "_"
            + dtStr + "_ct");
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
function ApplyPageTemplateStyle (id){
	if(id!=undefined){
		//获取当前页面样式，动态加载样式表
		var styleUrl=contextPath+"/rs/designer/"+id+"/getTemplateStyle";
		var styleDom="<link id=\"themeCss\" href=\""+styleUrl+"\" rel=\"stylesheet\" type=\"text/css\" />";
		if($("#themeCss").length>0){
			$("#themeCss").remove();
		}
			$("body").append(styleDom);
		
	}
}
//表单分组控件插入按钮事件绑定
$(".designer").delegate(".insert", "click", function () {
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
    $.each(childComps, function (i, item) {
        oldId = $(item).attr("id");
        dtStr = new Date().getTime() + i;
        nId = $(item).attr("data-role") + "_" + dtStr;
        $(item).attr("id", nId);
        $(item).attr("scope", "bootstrap");
        $(item).attr("crtltype", "kendo");
        $(item).attr("cname",nId + "_控件");
    });
    //获取插入方向
    var insertType = $(this).attr("type");
    if (insertType == 'prev') {
        currentComponent.before(component);
    } else {
        currentComponent.after(component);
    }

    $.each(childComps, function (i, item) {
        //控件初始化缺省值
        var datarole = $(item).attr("data-role");
        if(datarole == 'icheckradio'){
            $(item).icheckradio({
                asDemo:true
            });    
        }else if(datarole == 'icheckbox'){
            $(item).icheckbox({
                asDemo:true
            });
        }else if(datarole == 'metroselect_m'){
            //控件初始化
            $(item).find('select.select2-multiple').select2({width:"auto"});
        }
    });
    initdraggable();
});
/**
 *表单分组控件 左移按钮事件绑定
 */
$(".designer").delegate(".move-left", "click", function () {
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
$(".designer").delegate(".move-right", "click", function () {
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
$(".designer").delegate(".delete", "click", function () {
    var currentComponent = $(this).closest(".myformgroup");
    currentComponent.remove();
});
//格式刷按钮
var copyFormat;
var brushComp = {};
$("#formatBrushBt").click(function () {
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
        $.each(brushComp, function (i, comp) {
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
                }
                else if (variableDom.length > 0) {
                    $.each(variableDom, function (index, val) {
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
                            var ctrImpl = eval(compType+"_designer."+ctrImpl);
                            ctrImpl.call(this,newcontent,$(comp));
                        } catch(e) {
                            console.log('控件"'+compType+'"无格式化方法，执行默认格式化方法！');
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
$(document).ready(function () {
    window.key = 0;  //记录ctrl/shift键
    $(window).keydown(function (e) {
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
    }).keyup(function () {
        key = 0;
    });
    //注册控件单击事件
    $(".designer").delegate("[data-role]", "click", function (event) {
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
        }else{
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
    $(".designer").delegate(".column", "click", function (event) {
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
    $("#autoHeightBt").click(function () {
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
function initCommonStyleSettingPanel(){
    //初始化共有样式配置面板
    // $(".commonStylePanel").find("caption").text("统一样式设置");
    var $body = $(".commonStylePanel").find(".portlet-body");
    // $body.commonStyleList({});
    
    //获取配置项
    var jsonPath=contextPath+"/scripts/designer/components/json/"+"commonStyle.json";
    var nodes={};
    $.ajax({
        url:jsonPath,
        dataType:"json",
        async: false,
        success:function(jsonRule){
            if(jsonRule)
            nodes=jsonRule;
        },
        error: function (xhr, ts) {
            console.log('加载控件配置项失败！');
        }
    });
    initCommonStyleList($body,nodes);
    // $(".commonStylePanel").draggable({
    //     width:"100%",
    //     height:"100%",
    // });
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
function setCommonStyleDataByComponent($component){
    if(!initCommonStyleList.toolbox){
        return;
    }
    initCommonStyleList.toolbox.dbclickComponent = $component;
    var $item = $component.find(".mainObj");
    var htmlItem = $component[0];
    if($item[0]){
        htmlItem = $item[0];
    }
    var componentStyles = htmlItem.style;
    $.each(componentStyles,function(key,value){
        var $commonStyle = $(".commonStylePanel").find("#"+value);
        if($commonStyle.attr("type") && $commonStyle.attr("type") == 'btn'){
            // var $targetStyle = $commonStyle.find(".btn[value='"+componentStyles[value]+"']");
            $commonStyle.find(".btn[value='"+componentStyles[value]+"']").addClass("active");
        }else{
            $(".commonStylePanel").find("#"+value).val(componentStyles[value]).change();    
        }
    })

};
function initCommonStyleList($body,nodes){
    var $form_inline = $("<div class='form-inline' style=''></div>");
    $body.append($form_inline);
    var toolbox = new $.commonStyleToolbox();
    $.each(nodes,function(i,node){
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
        $.each(customHeightRows, function (i, row) {
            $(row).removeClass("autoHt");
            $(row).children(".view").removeClass("autoHt");
        });
    } else {
        $(".designer").find(".autoHt").removeClass("autoHt");
    }
}
//排序禁用
function disableSortable() {
    setTimeout(function () {
        $(".designer:hidden,.designer .column:hidden,.designer .containerComp:hidden").sortable("disable");
    }, 152);
}
//排序启用
function enableSortable() {
    $(".designer,.designer .column.ui-sortable-disabled,.designer .containerComp.ui-sortable-disabled").sortable("enable");
}

//如果页面内容为空，则自动弹出模板选择页面
$(function(){
    changedTheme(null,_themeTmpId);

    var $rightTabPanel = $(".rightTabPanel");
    App.destroySlimScroll($rightTabPanel);
    App.initSlimScroll($rightTabPanel);

    $("#elem_toolbar").elem_toolbar("init");

    $(".componentlist").componentlist({
        elemContainer:".designer"
    });
    
    //控件列表
    $("#complistBt").click(function(){
          var  componentlist=$(".componentlist");
          var $this=$(this);
          if($this.hasClass("selectedBt")){
             $this.removeClass("selectedBt");
             componentlist.hide();
         }else{
             $this.addClass("selectedBt");
             componentlist.show();
         }
         resizeQuickPanel();
    });

	if($("#dsDiv").html().trim()==""){
		$("#themeTemplate").trigger("click")
	}

    //显示或隐藏全局样式框
    $("#showGobalStyleBt").click(function(){
        $(".commonStylePanel").toggle();
        $("body").toggleClass("openGobalStyle");
    })

    //主题套用和切换
    $("#showThemeStyleBt").click(function(){
        BootstrapDialog.show({
            title : '主题切换',
            size : BootstrapDialog.SIZE_WIDE,
            message : "<iframe id='font_icon'  width='100%' height='600' src='"+contextPath+"/mx/form/defined/ex/themeList?retFn=changedTheme&pageId="+id+"&ui="+_ui+"&themeId="+_themeTmpId+"' frameborder='no' scrolling='yes'></iframe>",
            css : {
                width : $(window).width() * 0.6
            },
            modal : false //遮蔽层
        });
    })

    //禁用页面右键菜单
    document.oncontextmenu=new Function("event.returnValue=false;");
    // document.onselectstart=new Function("event.returnValue=false;");

    $("#sidebar-menu").oncontextmenu = function(){
        return false;
    }

    //右键弹出菜单栏，用于设置常用控件
    $("#sidebar-menu").on("mouseup","[data_role]",function(e){
    // $("#sidebar-menu ").mouseup(function(e){
        if(3 == e.which){
            e.preventDefault();
            e.stopPropagation();
            var $leftMenu = $(".leftControlMenu");

            var $this = $(this);
            var favoriteItemData = $this.closest(".nav-item").data("data");
            //判断是否为常用控件
            var flag = favoriteItemData.baseComp && favoriteItemData.baseComp == 1;
            if(flag){
                //常用控件
                $(".setCommomComp").text("删除常用控件");
            }else{
                //非常用控件
                $(".setCommomComp").text("设置为常用控件");
            }

            $leftMenu.css("left", event.pageX);
            $leftMenu.css("top", event.pageY);
            $leftMenu.slideDown(100);
            $leftMenu.data("$compObj",$this);
            return false;
        }
    })
    $("body").click(function(event){
        var $leftMenu = $(".leftControlMenu");
        $leftMenu.hide();
    })

    //设置为常用控件
    $(".setCommomComp").click(function(event){
        var $this = $(".leftControlMenu").data("$compObj");
        setCompFavorite($this);
    })
    

    // $("#sidebar-menu").on("click",".favoriteBtn",function(event){
    // // $(".favoriteBtn").click(function(event){
    //     var $this = $(this);
    //     setCompFavorite($this);
    // })
    window.initOpenSideBar = false;
    openSideBar();
    resizeQuickPanel();
    // App.initSlimScroll($(".page-quick-sidebar-wrapper .rightTabPanel"));
    // //左边树查询
    // $("#searchInp").change(function(e){
        
    // })
    // $("#deleteBt").click(function(e){
    //     //阻止冒泡
    //     e.stopPropagation();
    //     currentComponent.closest(".mybox").remove();
    //     $("#elem_toolbar").hide();
    //     $(".componentlist").componentlist("reload_");
    // });
});

function setCompFavorite($itemObj){
    var $itemObj = $itemObj;
    var favoriteItemData = $itemObj.closest(".nav-item").data("data");
    //判断是否为常用控件
    var flag = favoriteItemData.baseComp && favoriteItemData.baseComp == 1;
    var url = contextPath + "/mx/form/component";
    if(flag){
        //原本为常用控件则取消常用控件
        url += "/removeFavoriteComp";
    }else{
        //添加为常用控件
        url += "/setFavoriteComp";
    }
    $.ajax({
        url: url,
        async: true,
        type: 'post',
        data:{id:favoriteItemData.id},
        dataType: 'json',
        success: function(ret) {
            if(typeof ret == "string"){
                ret = JSON.parse(ret);
            }
            if(ret.statusCode == '200'){
                // var $item = $("#sidebar-menu [data_role='"+favoriteItemData.dataRole+"']").parent();
                var $item = $("#sidebar-menu [componentId='"+favoriteItemData.id+"']");
                if(flag){
                    //原本为常用控件则取消常用控件
                    $item.find("i").removeClass().addClass("fa fa-star-o").addClass("nofavorite");
                    favoriteItemData.baseComp = 0;
                    removeCommonComps(favoriteItemData);
                }else{
                    //添加为常用控件
                    $item.find("i").removeClass().addClass("fa fa-star").addClass("favorite");
                    favoriteItemData.baseComp = 1;
                    addCommonComps(favoriteItemData);
                }    
            }
        }
    });
}

function changedTheme(themeData,themeId){
    if(themeData){
        _themeTmpId = themeData.sysThemeTmpId;
    }else if(themeId){
        _themeTmpId = themeId;     
    }else{
        return;
    }
    var $myThemeStyleCss = $("#themeStyleLink");
    if(!$myThemeStyleCss[0]){
        $myThemeStyleCss = $("<link id='themeStyleLink'>")
            .attr({ rel: "stylesheet",
                type: "text/css",
                href: contextPath+'/theme/'+_themeTmpId+'.css'
            })
            .appendTo($(".designer"));

    }else{
        $myThemeStyleCss.attr("href",contextPath+'/theme/'+_themeTmpId+'.css');    
    }

    if(!themeData){
        $.ajax({
            url: contextPath + '/mx/theme/sysThemeTmp/getThemeById',
            async: true,
            data : {themeId : _themeTmpId},
            type: 'post',
            dataType: 'json',
            success: function(ret) {
                if(typeof ret == 'string'){
                    ret = JSON.parse(ret);
                }
                if(ret.statusCode == '200'){
                    var data = ret.data;
                    $(".designer").addClass(data.themeTmpCode);
                    $(".designer").attr("themeTmpCode",data.themeTmpCode);
                }
            }
        });
    }else{
        var lastThemeTmpCode = $(".designer").attr("themeTmpCode");
        if(lastThemeTmpCode){
            $(".designer").removeClass(themeData.themeTmpCode);
        }
        $(".designer").addClass(themeData.themeTmpCode);
        $(".designer").attr("themeTmpCode",themeData.themeTmpCode);
    }
}

function openSideBar() {
    //宽度改变
    if (!$('body').hasClass('page-quick-sidebar-open')) {
        $('body').toggleClass('page-quick-sidebar-open');
        $('.designer').width($('.designer').width() - 263);
        $('.commonStylePanel').width($('.commonStylePanel').width() - 263);
        // $(".page-quick-sidebar-toggler").unbind("click");
        if(!window.initOpenSideBar){
            $('body').on("click",".page-quick-sidebar-toggler",function (e) {
                // $('body').toggleClass('page-quick-sidebar-open');
                $('.designer').css("width", "");
                $('.designer').trigger("designerWidthChange");
                $('.commonStylePanel').css("width","");
            })
            window.initOpenSideBar = true;
        }
        $('.designer').trigger("designerWidthChange");
    }
}
function resizeQuickPanel(){
    App.destroySlimScroll($(".page-quick-sidebar-wrapper .rightTabPanel"));
    var height = $(".page-quick-sidebar-wrapper").height() - $(".page-quick-sidebar-wrapper .nav-tabs").height();
    if(!$(".page-quick-sidebar-wrapper .componentlistPanel").is(":hidden")){
        height -= $(".page-quick-sidebar-wrapper .componentlistPanel").height();
    }
    $(".page-quick-sidebar-wrapper .rightTabPanel").height(height);
    initMySlimScroll($(".page-quick-sidebar-wrapper .rightTabPanel"));
}

function initMySlimScroll(J){
    $(J).each(function() {
        if ($(this).attr("data-initialized")) {
            return
        }
        var K;
        if ($(this).attr("data-height")) {
            K = $(this).attr("data-height")
        } else {
            K = $(this).css("height")
        }
        $(this).slimScroll({
            allowPageScroll: true,
            size: "7px",
            color: ($(this).attr("data-handle-color") ? $(this).attr("data-handle-color") : "#bbb"),
            wrapperClass: ($(this).attr("data-wrapper-class") ? $(this).attr("data-wrapper-class") : "slimScrollDiv"),
            railColor: ($(this).attr("data-rail-color") ? $(this).attr("data-rail-color") : "#eaeaea"),
            position: false ? "left" : "right",
            height: K,
            opacity: ($(this).attr("data-opacity") ? $(this).attr("data-opacity") : .7), //轨道透明度
            alwaysVisible: ($(this).attr("data-always-visible") == "1" ? true : false),
            railVisible: ($(this).attr("data-rail-visible") == "1" ? true : false),
            disableFadeOut: true
        });
        $(this).attr("data-initialized", "1")
    })
}

function searchControl(e){
    var value = $("#searchInp").val();
    $(".nav-item.open .sub-menu").hide();
    $(".nav-item.open").removeClass("open");
    $(".nav-item.searched").removeClass("searched");
    //找到包含value的所有菜单。
    var $searched_menu = $(".tabbable-designer .sub-menu .menulicontent[title*='" + value + "']");
    $searched_menu.closest(".nav-item").addClass("searched");

    var $sub_menu = $searched_menu.closest(".sub-menu").show();
    $sub_menu.closest(".nav-item").addClass("open");

    e.preventDefault();
    e.stopPropagation();
    return false;
}

//增加自适应高度样式
function addAutoHeightClass(obj) {
	if (obj.hasClass("layout_elem")) {
		obj.addClass("autoHt");
		obj.find(".view").addClass("autoHt");
		obj.find(".view>.row").addClass("autoHt");
		obj.find(".view>.row>.column").addClass("autoHt");
	}
}


	