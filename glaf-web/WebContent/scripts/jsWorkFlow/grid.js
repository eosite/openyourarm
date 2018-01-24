// register the grid component
Vue.component('demo-grid', {
    template: '#grid-template',
    replace: true,
    props: {
        data: Array,
        columns: Array,
        filterKey: String,
        groupField: String,
        showGroup: Boolean,
        obj: Object,
        scroll: Boolean
    },
    data: function() {
        var sortOrders = {}
        this.columns.forEach(function(key) {
            sortOrders[key['field']] = 1
        })
        return {
            sortKey: '',
            sortOrders: sortOrders
        }
    },
    computed: {
        filteredData: function() {
            var sortKey = this.sortKey
            var filterKey = this.filterKey && this.filterKey.toLowerCase()
            var order = this.sortOrders[sortKey] || 1
            var data = this.data
            if (filterKey) {
                data = data.filter(function(row) {
                    return Object.keys(row).some(function(key) {
                        return String(row[key]).toLowerCase().indexOf(filterKey) > -1
                    })
                })
            }
            if (sortKey) {
                data = data.slice().sort(function(a, b) {
                    a = a[sortKey]
                    b = b[sortKey]
                    return (a === b ? 0 : a > b ? 1 : -1) * order
                })
            }
            if (!this.showGroup) {
                return data;
            }
            //进行分组
            var groups = [];

            function getGroup(groupName) {
                for (var i = 0; i < groups.length; i++) {
                    var group = groups[i];
                    if (group.groupName == groupName) {
                        return group;
                    }
                }
                return null;
            }
            $.each(data, function(i, row) {
                var group = getGroup(row['groupName']);
                if (!group) {
                    group = {
                        groupName: row['groupName'],
                        rows: [row],
                        startRow: i
                    };
                    groups.push(group);
                } else {
                    group.rows.push(row);
                }
            });

            return groups;
        },
        width: function() {
            var $that = $(this.$parent.$el);
            return $that.width() - 18;
        },
        conClass: function() {
            return null;
        }
    },
    filters: {
        capitalize: function(str) {
            return str.charAt(0).toUpperCase() + str.slice(1)
        }
    },
    methods: {
        updateValue: function(e) {
            var $t = $(e.target),
                key = $t.attr("tname"),
                val = $t.val();
            this.$set(this.obj, key, val);
            this.$emit('updatevalue');
        },
        dialogClick: function(e) {
            var $t = $(e.target),
                code = $t.attr("tname"),
                dep = $t.attr("dep");
            if (dep) {
                var data = this.obj[dep + "_data"];
                if (data && data.length == 1) {
                    dep = data[0]["eName"];
                }
            }
            openDialog(code, dep);
            this.selectProp = code;
            $('body').toggleClass('page-quick-sidebar-open');
        },
        sortBy: function(key) {
            this.sortKey = key['field'];
            this.sortOrders[key['field']] = this.sortOrders[key['field']] * -1
        },
        expander: function(e) {
            var $this = $(e.target),
                $tdiv = $this.closest('div'),
                index = $tdiv.attr("group-index"),
                $that = $(this.$parent.$el);
            if ($this.hasClass('mtbox_collapse')) {
                $this.removeClass('mtbox_collapse').addClass('mtbox_expand');
                $that.find("table[group-index=" + index + "]").hide();
            } else {
                $this.removeClass('mtbox_expand').addClass('mtbox_collapse');
                $that.find("table[group-index=" + index + "]").show();
            }
        },
        dependMethod: function(dep, depVal, e) {
            if (dep && depVal) {
                var data = this.obj[dep + "_data"];
                if (data && data.length == 1) {
                    var ename = data[0]["eName"];
                    if (depVal.indexOf("~") == 0 && outExpDefined[ename]) {
                        var outExp = outExpDefined[ename]();
                        return true;
                    } else {
                        return depVal.split(",").indexOf(ename) != -1;
                    }
                }
                return false;
            }
            return true;
        }
    }
});

//v-on:input="updateValue($event.target.value)"\
/*Vue.component('demo-editor', {
    replace: true,
    render: function(createElement) {
        if (this.column.edit) {
            debugger;
            var options = {};
            var props = {};
            props[this.row['code']] = this.obj[this.row['code']];
            options['props'] = props;
            options['model'] = "obj."+this.row['code'];
            return createElement("input", options);
        } else {
            return createElement("div", {
                domProps: {
                    innerHTML: this.row['name']
                }
            });
        }
    },
    props: {
        column: Object,
        obj: Object,
        row: Object
    },
})*/
var pageRule = {};
var gridColumns = [{
        field: 'name',
        title: '名称',
        width: "100px"
    }, {
        field: 'value',
        title: '属性值',
        edit: true
    }],
    gridData = [];
// bootstrap the demo
function initVue(options) {
    options || (options = {});

    return new Vue({
        el: '#demo',
        data: {
            obj: options.obj || {},
            options: options.options || {},
            defaultOptions: {
                scroll: true,
                width: "320px",
                height: "auto",
            },
            searchQuery: '',
            showGroup: true,
            groupField: options.groupField || "groupName",
            gridColumns: options.gridColumns || [],
            gridData: options.gridData || [],
        },
        computed: {
            width: function() {
                return this.options.width || this.defaultOptions.width;
            },
            height: function() {
                return this.options.height || this.defaultOptions.height;
            },
            scroll: function() {
                return this.options.scroll || this.defaultOptions.scroll;
            }
        },
        methods: {
            updateObj: function() {
                console.log(2);
            }
        },
        mounted: function() {
            var $that = $(this.$el);
            $that.css({
                width: this.width,
                height: this.scroll ? this.height : this.defaultOptions.height
            });
            $that.find('.mtbox_view').width($that.width() - 18);
            if (this.scroll) {
                var $container = $that.find(".mtbox_container");
                $container.height($that.height() - 23);
                $that.find(".mtbox_scroll").height($container.height() - 26);
            }

            $that.find(".mtbox_view .mtbox_content.mtbox_scroll").bind("scroll.mtbox", function(e) {
                $that.find(".mtbox_inner .mtbox_content.mtbox_scroll").scrollTop(this.scrollTop);
            });
        }
    })
}
var demo = initVue({
    options: {
        height: "100%"
    },
    gridColumns: gridColumns,
    gridData: gridData
});

var baseGridData = [{
        name: "ID",
        code: "id",
        groupName: "基础",
        listno: 2,
        editor: "textbox"
    }, {
        name: "名称",
        code: "name",
        groupName: "基础",
        listno: 2,
        editor: "textbox"
    }],
    otherGridData = {
        "bpmn:SendTask": [{
            name: "输入控件",
            code: "input",
            groupName: "属性",
            listno: 2,
            editor: "dialog"
        }],
        "bpmn:ReceiveTask": [{
            name: "输出控件",
            code: "output",
            groupName: "属性",
            listno: 2,
            editor: "dialog"
        }, {
            name: "输出控件扩展",
            code: "out_ext",
            groupName: "属性",
            listno: 2,
            editor: "dialog",
            depend: "output",
            depVal: "~outExpDefined",
        }, {
            name: "输入输出参数",
            code: "inoutparams",
            groupName: "属性",
            listno: 3,
            editor: "dialog"
        }, {
            name: "回调定义",
            code: "callback",
            groupName: "属性",
            listno: 2,
            editor: "dialog",
            depend: "output",
            depVal: "~callbackDefined",
        }],
        "bpmn:ServiceTask": [{
            name: "触发控件",
            code: "event",
            groupName: "属性",
            listno: 2,
            editor: "dialog"
        }],
        "bpmn:ScriptTask": [{
            name: "弹出页面",
            code: "output_page",
            groupName: "属性",
            listno: 2,
            editor: "dialog"
        }, {
            name: "输入输出参数",
            code: "inoutparams",
            groupName: "属性",
            listno: 3,
            editor: "dialog"
        }],
        "bpmn:SequenceFlow": [{
            name: "表达式定义",
            code: "expression",
            groupName: "属性",
            listno: 2,
            editor: "dialog"
        }],
        "bpmn:DataStoreReference": [{
            name: "输出控件扩展",
            code: "out_ext",
            groupName: "属性",
            listno: 2,
            editor: "dialog"
        }]
    };

function getInputData() {
    var $child = demo.$children[0],
        propName = $child.selectProp;
    dd = {
        nodes: $child.obj[propName + "_data"] || []
    };
    return dd;
}
//选择页面格式转换为选择控件格式
function pageObj2NodeObj(data) {
    var node = data.node;
    node.eName = "newWindow";
    node.isPage = true;
    node.eleId = node.id;
    node.pageId = node.id;
    node.srcUrl = data.url;
    node.pObj = JSON.stringify(node);
    return {
        names: data.name,
        nodes: [node]
    };
}

function retInputData(data) {
    if (data.name) {
        data = pageObj2NodeObj(data);
    }
    var $child = demo.$children[0],
        propName = $child.selectProp;
    $child.$set($child.obj, propName, data["names"] || data["name"]);
    $child.$set($child.obj, propName + "_data", data["nodes"] || data["node"] || data["value"]);
    mt_dialog.close();
    $('body').toggleClass('page-quick-sidebar-open');
}

function retOutExtData(data) {
    var $child = demo.$children[0],
        propName = $child.selectProp;
    data.type = $child.obj["output_data"][0]["eName"];
    $child.$set($child.obj, propName, data.name);
    $child.$set($child.obj, propName + "_data", data);
    mt_dialog.close();
    $('body').toggleClass('page-quick-sidebar-open');
}

function retCallbackFn(data) {
    var $child = demo.$children[0],
        propName = $child.selectProp;
    $child.$set($child.obj, propName, data[0].name);
    $child.$set($child.obj, propName + "_data", data);
    mt_dialog.close();
    $('body').toggleClass('page-quick-sidebar-open');
}

var urlObj = {
    "input": {
        url: contextPath + "/mx/form/defined/ex/verificationSetting?getFn=getInputData&retFn=retInputData&pageId=",
        title: "选择输入控件"
    },
    "event": {
        url: contextPath + "/mx/form/defined/ex/eventDefinedSelect?getFn=getInputData&retFn=retInputData&isTrigger=true&pageId=",
        title: "选择触发控件"
    },
    "output": {
        url: contextPath + "/mx/form/defined/ex/eventDefinedSelect?getFn=getInputData&retFn=retInputData&pageId=",
        title: "选择输出控件"
    },
    "inoutparams": {
        url: contextPath + "/mx/form/defined/param/events_outInParam?&eleId=hidParam&fn=initInOutParameterByEvents&retFn=retParamFn&pageId=",
        title: "选择输入输出参数"
    },
    "callback": {
        url: contextPath + "/mx/form/defined/param/events_outInParam?&eleId=hidParam&fn=initCallbackByEvents&retFn=retCallbackFn&pageId=",
        title: "回调输入输出参数"
    },
    "output_page": {
        url: contextPath + "/mx/form/defined/ex/selectPage?retFn=retInputData&_=",
        title: "选择页面"
    },
    "expression": {
        url: contextPath + "/mx/expression/defined/view?category=front&retFn=retExpression&getFn=getExpression&initExpFn=initExpressionFn&notValidate=true&_=",
        title: "表达式定义"
    }
};

function openDialog(code, depend) {
    var uobj = depend && code != "callback" ? outExpDefined[depend]() : urlObj[code],
        title = uobj.title,
        url = uobj.url + pageId;
    if (depend && code != "callback") {
        var params = uobj.params ? ("&" + $.param(uobj.params)) : "";
        url = contextPath + "/mx/form/defined/outExp/" + uobj.urlStr + "?retFn=retOutExtData&pageId=" + pageId + params;
        if (uobj.url) {
            url = contextPath + uobj.url + "?retFn=retOutExtData&pageId=" + pageId + params;
        }
    }

    if (["inoutparams", "callback"].indexOf(code) != -1) {
        url += "&eleType=" + pageId;

        var $child = demo.$children[0],
            propName = $child.selectProp;
        propName = code;
        $("#hidParam").val(JSON.stringify($child.obj[propName + "_data"]) || "");
    }
    var width = $("body").width() - 24;
    var height = $("body").height() - 200;
    window.mt_dialog = BootstrapDialog.show({
        title: title || "窗口",
        draggable: true,
        message: function(dialog) {
            var pageToLoad = dialog.getData('pageToLoad');
            var frame = "<iframe onload=\"$(this).contents().find('body').css('overflow-y', 'auto');\" width='100%' height='" + height + "' src='" + pageToLoad + "' frameborder='no' scrolling='yes'></iframe>";
            return $(frame);
        },
        data: {
            'pageToLoad': url
        },
        css: {
            width: '1920px',
            height: (height + 6) + 'px'
        }
    });
    mt_dialog.$modalDialog.css({
        "margin-top": '50px',
        "margin-left": '10px',
        "margin-right": '10px',
        "width": width
    });
}

function isUse(element) {
    return ["bpmn:Task", "bpmn:EndEvent", "bpmn:StartEvent", "bpmn:SequenceFlow", "bpmn:ServiceTask", "bpmn:ScriptTask", "bpmn:ReceiveTask", "bpmn:SendTask"].indexOf(element.type) != -1;
}

function save($this) {
    viewer.saveXML({
        format: true
    }, function(err, xml) {
        console.log(xml);
        $.ajax({
            url: contextPath + "/mx/form/formEvent/saveFormEvent",
            type: "POST",
            data: {
                id: _diagramId,
                diagram: xml,
                rule: JSON.stringify(pageRule),
                pageId: pageId
            },
            success: function(data) {
                if(data && (data = JSON.parse(data)) && !data.statusCode ){
                    _diagramId = data.id ;
                    BootstrapDialog.alert("保存成功");
                }else{
                    BootstrapDialog.alert("保存失败");
                }
                $this && $this.removeAttr('disabled');
            },
            error: function() {
                BootstrapDialog.alert("保存失败");
                $this && $this.removeAttr('disabled');
            }
        })
    })
}
jQuery(document).ready(function() {
    $("#saveBt").on("click",function(){
        $(this).attr("disabled","disabled")
        save($(this));
    });
    var contentHeight = $('.page-content').height();
    window.viewer = new BpmnJS({
        container: '#canvas',
        height: contentHeight,
        metadata: {
            "first": "value1"
        },
        /*additionalModules: [
            customTranslate
        ],*/
        //资源文件
        translations: translations,
        //自定义模块
        moddles: moddles,
        //替换操作选项
        replaceOptions: replaceOptions
    });
    $.ajax({
        url: contextPath + "/mx/form/formEvent/getEventByPageId",
        type: "POST",
        data: {
            pageId: pageId
        },
        success: function(data) {
            var dataObj = JSON.parse(data);
            dataObj.rule && (pageRule = JSON.parse(dataObj.rule));
            window._diagramId = dataObj.id;
            renderXml(dataObj.diagram);
        },
        error: function() {
            renderXml();
        }
    });

    function renderXml(xml) {
        xml = xml || '<?xml version="1.0" encoding="UTF-8"?><bpmn2:definitions xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:bpmn2="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" id="sample-diagram" targetNamespace="http://bpmn.io/schema/bpmn" xsi:schemaLocation="http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd"><bpmn2:process id="Process_1" isExecutable="false"><bpmn2:startEvent id="StartEvent_0hznix9" name="开始"><bpmn2:outgoing>SequenceFlow_0o6x7u5</bpmn2:outgoing></bpmn2:startEvent><bpmn2:endEvent id="EndEvent_0ipvcot" name="结束"><bpmn2:incoming>SequenceFlow_1j4xei8</bpmn2:incoming></bpmn2:endEvent><bpmn2:task id="Task_0109xjy" name="初始化"><bpmn2:incoming>SequenceFlow_0o6x7u5</bpmn2:incoming><bpmn2:outgoing>SequenceFlow_1j4xei8</bpmn2:outgoing></bpmn2:task><bpmn2:sequenceFlow id="SequenceFlow_0o6x7u5" sourceRef="StartEvent_0hznix9" targetRef="Task_0109xjy" /><bpmn2:sequenceFlow id="SequenceFlow_1j4xei8" sourceRef="Task_0109xjy" targetRef="EndEvent_0ipvcot" /></bpmn2:process><bpmndi:BPMNDiagram id="BPMNDiagram_1"><bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_1"><bpmndi:BPMNShape id="StartEvent_0hznix9_di" bpmnElement="StartEvent_0hznix9"><dc:Bounds x="225" y="205" width="36" height="36" /><bpmndi:BPMNLabel><dc:Bounds x="231" y="241" width="24" height="14" /></bpmndi:BPMNLabel></bpmndi:BPMNShape><bpmndi:BPMNShape id="EndEvent_0ipvcot_di" bpmnElement="EndEvent_0ipvcot"><dc:Bounds x="749" y="205" width="36" height="36" /><bpmndi:BPMNLabel><dc:Bounds x="755" y="241" width="24" height="14" /></bpmndi:BPMNLabel></bpmndi:BPMNShape><bpmndi:BPMNShape id="Task_0109xjy_di" bpmnElement="Task_0109xjy"><dc:Bounds x="460" y="183" width="100" height="80" /></bpmndi:BPMNShape><bpmndi:BPMNEdge id="SequenceFlow_0o6x7u5_di" bpmnElement="SequenceFlow_0o6x7u5"><di:waypoint xsi:type="dc:Point" x="261" y="223" /><di:waypoint xsi:type="dc:Point" x="460" y="223" /><bpmndi:BPMNLabel><dc:Bounds x="361" y="208" width="0" height="0" /></bpmndi:BPMNLabel></bpmndi:BPMNEdge><bpmndi:BPMNEdge id="SequenceFlow_1j4xei8_di" bpmnElement="SequenceFlow_1j4xei8"><di:waypoint xsi:type="dc:Point" x="560" y="223" /><di:waypoint xsi:type="dc:Point" x="749" y="223" /><bpmndi:BPMNLabel><dc:Bounds x="655" y="198" width="0" height="0" /></bpmndi:BPMNLabel></bpmndi:BPMNEdge></bpmndi:BPMNPlane></bpmndi:BPMNDiagram></bpmn2:definitions>';
        viewer.importXML(xml, function(err) {

            if (err) {
                console.log('error rendering', err);
            } else {
                var eventBus = viewer.get('eventBus');

                var events = [
                    'element.click'
                ];
                events.forEach(function(event) {
                    eventBus.on(event, function(e) {
                        if (isUse(e.element)) {
                            if (e.element.type == "bpmn:SequenceFlow") {
                                if (!(e.element.businessObject.sourceRef && e.element.businessObject.sourceRef.$type == "bpmn:ExclusiveGateway")) {
                                    demo.$data.obj = {};
                                    demo.$data.gridData = [];
                                    return;
                                }
                            }
                            mtxx.selectElement = e.element;
                            $('body').addClass('page-quick-sidebar-open');
                            var obj = pageRule[e.element.id] || (pageRule[e.element.id] = {});
                            obj.id = e.element.id;
                            obj.name = e.element.businessObject.name;
                            demo.$data.obj = obj;
                            demo.$data.gridData = baseGridData.concat(otherGridData[e.element.type] || []);
                            /*demo.$nextTick(function() {

                            });*/
                        } else {
                            demo.$data.obj = {};
                            demo.$data.gridData = [];
                            $('body').removeClass('page-quick-sidebar-open');
                        }
                    });
                });
            }
        });
    }
});

//输入输出参数定义中 获取输入输出控件
function getParamFn() {
    //
    var selectNode = mtxx.selectElement,
        eventNode,
        inputNode;
    /*//获取输入线
    if (selectNode.businessObject.incoming) {
        var incoming = selectNode.businessObject.incoming[0];
        //获取触发控件
        if (incoming && incoming.sourceRef && incoming.sourceRef.$type == "bpmn:ExclusiveGateway") {
            //如果有路由网关
            var gatewayNode = incoming.sourceRef;
            if (gatewayNode.incoming[0]) {
                incoming = gatewayNode.incoming[0];
            }
        }
        incoming && (eventNode = incoming.sourceRef);


        //获取输入控件
        eventNode && eventNode.incoming[0] && (inputNode = eventNode.incoming[0].sourceRef);
    }*/
    eventNode = getBusinessObject(selectNode.businessObject, "bpmn:ServiceTask");
    inputNode = getBusinessObject(selectNode.businessObject, "bpmn:SendTask");

    var data = {};
    if (inputNode && pageRule[inputNode.id]) {
        data.inWidget = JSON.stringify(pageRule[inputNode.id]["input_data"] || []);
    }
    if (selectNode && pageRule[selectNode.id]) {
        data.outWidget = JSON.stringify(pageRule[selectNode.id]["output_data"] || pageRule[selectNode.id]["output_page_data"] || []);
    }
    if (pageRule[selectNode.id]["out_ext_data"]) {
        data.outExt = pageRule[selectNode.id]["out_ext_data"] || [];
    }
    return data;
}


//选择树返回参数
function retParamFn(data) {
    if (data) {
        debugger;
        var $child = demo.$children[0];
        $child.$set($child.obj, "inoutparams", data[0].name);
        $child.$set($child.obj, "inoutparams_data", data);
    }
    mt_dialog.close();
    $('body').toggleClass('page-quick-sidebar-open');
}

//初始化参数
function initExpressionFn() {
    var selectNode = mtxx.selectElement,
        data = pageRule[selectNode.id]["expression_data"];
    return data ? (typeof data == "string" ? JSON.parse(data) : data) : [];
}
//返回表达式执行函数
function retExpression(data) {
    if (data) {
        var $child = demo.$children[0];
        $child.$set($child.obj, "expression", data.expVal);
        $child.$set($child.obj, "expression_data", data);
    }
    mt_dialog.close();
    $('body').toggleClass('page-quick-sidebar-open');
}

//获取左边的图形
function getBusinessObject(businessObject, type) {
    if (businessObject) {
        if (businessObject.$type == type /*"bpmn:ServiceTask"*/ ) {
            return businessObject;
        } else {
            return getBusinessObject(businessObject.sourceRef || (businessObject.incoming && businessObject.incoming[0]), type);
        }
    }
    return null;
}

//获取参数
function getExpression() {
    var selectNode = mtxx.selectElement,
        eventNode,
        inputNode;
    //获取输入线
    eventNode = getBusinessObject(selectNode.businessObject, "bpmn:ServiceTask");
    inputNode = getBusinessObject(selectNode.businessObject, "bpmn:SendTask");
    /* if (selectNode.businessObject.incoming) {
         var incoming = selectNode.businessObject.incoming[0];
         //获取触发控件
         incoming && (eventNode = incoming.sourceRef);

         //获取输入控件
         eventNode && eventNode.incoming[0] && (inputNode = eventNode.incoming[0].sourceRef);
     }*/

    var
    /*$tr = $("#treegrid").find(".mt-select"),
           $input = $tr.find("input.tg-input"),*/
        idata = /*$input.data("idata")*/ (inputNode && pageRule[inputNode.id] && pageRule[inputNode.id]["input_data"]) || [],
        inDatas = idata ? (typeof idata == "string" ? JSON.parse(idata) : idata) : [],
        expressionData = [],
        inOutParams = parent.initInOutParameterByEvents(null, JSON.stringify(inDatas)),
        inparams = inOutParams.menus['in'],
        inparam, i;
    if (inparams && inparams.length > 0) {
        for (i = 0; i < inparams.length; i++) {
            inparam = inparams[i];
            var express = {};
            express.id = inparam.items.pageId + inparam.id;
            express.name = inparam.text;
            getExpParams(inparam, express.id, expressionData);
            expressionData.push(express);
        }
    }
    return expressionData;
}
//获取表达式参数
function getExpParams(inParam, pid, expressionData) {
    var items = inParam.items;
    for (var j = 0; j < items.length; j++) {
        var param = items[j];
        var subexpress = {};
        subexpress.id = param.id ? (items.pageId + param.id) : (pid + "-" + j);
        if (subexpress.id == pid) {
            subexpress.id = subexpress.id + j;
        }
        subexpress.name = param.text;
        subexpress.pId = pid;
        subexpress.t = "";
        if (param.items) {
            getExpParams(param, subexpress.id, expressionData)
        } else {
            subexpress.pageId = items.pageId;
            subexpress.eleId = param.id;
            subexpress.fnType = param.type;
            subexpress.lev = items.lev;
            subexpress.otype = items.otype;
            if (param.code) {
                subexpress.datasetId = param.datasetId;
                subexpress.columnName = param.columnName;
                subexpress.dType = param.FieldType;
                subexpress.code = param.code;
                subexpress.value = param.value;
                subexpress.isFn = false;
            } else {
                subexpress.dType = "string";
                subexpress.code = "~F{" + items.pageId + "." + param.id + "." + param.type + "}";
                subexpress.value = "~F{" + inParam.text + "." + param.text + "}";
                subexpress.isFn = true;

                param.columnName && (subexpress.columnName = param.columnName);
            }
        }
        expressionData.push(subexpress);
    }
}