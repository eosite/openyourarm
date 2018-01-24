var hasDataset = ["bimupload","bootstrapdialog","calendarbt","carousel","charts","combobox","customList","definedcard","definedTable","diagrambt","dropdownlist","echarts","excelupload","gantt","gisupload","grid","gridbt","gridlist","icheckbox","icheckradio","jsgis","listview","mapext","megamenu","metrolist","metroselect","metroselect_m","mylist","nestable","page","step","treelist","treelistbt","yscroll","ztree"];
var setting = {
	view : {
		showIcon : true,
		showLine : true,
		showTitle : false,
		selectedMulti : false
	},
	check : {
		enable : false,
		chkStyle : "checkbox",
		chkboxType : {
			"Y" : "",
			"N" : ""
		}
	},
	async : {
		enable : true,
		url : contextPath+"/mx/form/defined/getPageHierarchicalAssembly?pageId="+pageId+"&showEvent=false&isTrigger=false&isGroup=true",
		dataFilter : function(treeId, parentNode, childNodes) {
			if (!childNodes)
				return null;
			for (var i = 0, l = childNodes.length, childNode; i < l; i++) {
				childNode = childNodes[i];
				if (childNode.icon) {
					childNode.icon = contextPath + childNode.icon;
				}
				if (!childNode.isEvn) {
					//childNode.chkDisabled = true;
				}
				if (i == 0) {
					childNode.open = true;
				}
			}
			return childNodes;
		}
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "pId"
		}
	},
	edit :{
		drag:{
			isCopy:true
		},
		enable: true,
		showRemoveBtn: false,
		showRenameBtn: false
	},
	callback: {
		beforeDrag:function(treeId, treeNodes){
			return !!treeNodes[0].isEle || !!treeNodes[0].isPage;
		},
		beforeDrop:function(treeId, treeNodes, targetNode, moveType){
			return false;
		},
		onDrop:function(event, treeId, treeNodes, targetNode, moveType){
			var treeNode = treeNodes[0],
				drole = treeNode.drole,
				$target = $(event.target),
				$viewport = $target.find(">.viewport");
			if($target.attr("data-element-id") == "Process_1"){
				var transform = $viewport.css('transform').match(/matrix\((-?\d+, -?\d+, -?\d+, -?\d+, -?\d+, -?\d+)\)/),
					matrixs = transform && transform[1]?transform[1].split(","):null,
					offsetX = matrixs?parseFloat(matrixs[4]):0,
					offsetY = matrixs?parseFloat(matrixs[5]):0,
					x = event.clientX-offsetX,
					y = event.clientY-offsetY;
				/**
				 * x,y [0,0]
				 * [a,b,c,d,e,f]
				 * x = ax+cy+e  
				 * y = bx+dy+f
				 */
				var eleId = cli.create('bpmn:ChinaissContainers', {
					  x: x -30 , y: y - 50
				},cli.element("Process_1"));
				var element = cli.element(eleId),
					nodeName = treeNode.name+"->"+treeNode.eleId;
				/*var textAnnotation = cli.create('bpmn:TextAnnotation', {
				  x: element.x - 50, y: element.y + 150
				}, element.parent);*/
				cli.setLabel(element, nodeName);
				modeling.updateProperties(element,{chinaissDataRole:drole});
				
				//更改databox属性
				var obj = pageRule[element.id] || (pageRule[element.id] = {});
    			obj.id = element.id;
    			obj.name = nodeName;
    			obj.input = treeNode.name;
    			filterNode(treeNode);
    			obj.input_data = treeNodes;
    			updateDirty();
    			
    			buildDataset(drole,element,treeNode.pageId,treeNode.eleId);
			}
		}
	}
};
/**
 * 构件数据集
 * @param drole
 * @param element
 * @param pageId
 * @param eleId
 * @returns
 */
function buildDataset(drole,element,pageId,eleId){
	if(drole && $.inArray(drole,hasDataset)>=0){
		$.ajax({
    		url: contextPath + "/mx/form/defined/getDatasetTables",
    		type: "POST",
    		data: {
    			pageId : pageId,
    			widgetId : eleId
    		},
    		success: function(data) {
    			if(data && (data = JSON.parse(data))){
    				$.each(data,function(i,d){
    					var eleId = cli.create('bpmn:ChinaissDataNode', {
	    					  x: element.x -80 , y: element.y - 100
	    				},cli.element("Process_1"));
	    				var dataNode = cli.element(eleId);
	    				cli.setLabel(dataNode, d.name || d.title);
	    				modeling.updateProperties(dataNode,{datasetId:d.datasetId || d.dataSetId});
	    				cli.connect(dataNode, element, 'bpmn:ChinaissDataFlow');
    				})
    			}
    		},
    		error: function() {
    			BootstrapDialog.warning("发生了未知错误,请联系管理员！");
    		}
    	});
	}
}

var setting2 = {
	async: {
		enable : true,
		url:contextPath+'/mx/form/defined/getFormPageTree',
		autoParam: ["parentId=pId", "id"],
		otherParam: {},
		dataFilter: function (treeId, parentNode, responseData) {
			if (responseData) {
				for (var i = 0; i < responseData.length; i++) {
					var data = responseData[i];
					if (data.locked == 1) {
						data.icon = contextPath + "/images/lock.png";
					}
				}
			}
			return responseData;
		}
	},
	callback: {
		beforeDrag:function(treeId, treeNodes){
			return treeNodes[0].formType=="0";
		},
		beforeDrop:function(treeId, treeNodes, targetNode, moveType){
			return false;
		},
		onDrop:function(event, treeId, treeNodes, targetNode, moveType){
			var treeNode = treeNodes[0],
				drole = treeNode.drole,
				$target = $(event.target),
				$viewport = $target.find(">.viewport");
			if($target.attr("data-element-id") == "Process_1"){
				var transform = $viewport.css('transform').match(/matrix\((-?\d+, -?\d+, -?\d+, -?\d+, -?\d+, -?\d+)\)/),
					matrixs = transform && transform[1]?transform[1].split(","):null,
					offsetX = matrixs?parseFloat(matrixs[4]):0,
					offsetY = matrixs?parseFloat(matrixs[5]):0,
					x = event.clientX-offsetX,
					y = event.clientY-offsetY;
				/**
				 * x,y [0,0]
				 * [a,b,c,d,e,f]
				 * x = ax+cy+e  
				 * y = bx+dy+f
				 */
				var eleId = cli.create('bpmn:ChinaissPageNode', {
					  x: x -30 , y: y - 50
				},cli.element("Process_1"));
				var element = cli.element(eleId),
					nodeName = treeNode.name+"->"+treeNode.id;
				cli.setLabel(element, nodeName);
				//更改databox属性
				var obj = pageRule[element.id] || (pageRule[element.id] = {});
    			obj.id = element.id;
    			obj.name = nodeName;
    			obj.output_page = treeNode.name;
    			filterNode(treeNode);
    			var data = {
    				node:treeNode,
    				name:treeNode.name,
    				url: "/mx/form/page/viewPage?id="+treeNode.id
    			}
    			obj.output_page_data = pageObj2NodeObj(data).nodes;
    			updateDirty();
			}
		}
	},
	data: {
		simpleData: {
			enable: true,
			idKey: "id",
			pIdKey: "parentId",
		}
	},
	edit: {
		enable: true,
		showRenameBtn: false,
		showRemoveBtn: false,
		editNameSelectAll: true,
		drag: {
			isCopy: true,
			isMove: false
		}
	}
};

var setting3 = {
		async: {
			enable : true,
			url:contextPath+'/mx/form/formEventComplex/getComplexByPageId',
			otherParam: {
				pageId : pageId,
				complexId : complexId
			},
			dataFilter: function (treeId, parentNode, responseData) {
				if (responseData) {
					for (var i = 0; i < responseData.length; i++) {
						var data = responseData[i];
						data.icon = contextPath + "/images/briefcase.png";
					}
				}
				return responseData;
			}
		},
		callback: {
			beforeDrop:function(treeId, treeNodes, targetNode, moveType){
				return false;
			},
			onDrop:function(event, treeId, treeNodes, targetNode, moveType){
				var treeNode = treeNodes[0],
					$target = $(event.target),
					$viewport = $target.find(">.viewport");
				if($target.attr("data-element-id") == "Process_1"){
					var transform = $viewport.css('transform').match(/matrix\((-?\d+, -?\d+, -?\d+, -?\d+, -?\d+, -?\d+)\)/),
						matrixs = transform && transform[1]?transform[1].split(","):null,
						offsetX = matrixs?parseFloat(matrixs[4]):0,
						offsetY = matrixs?parseFloat(matrixs[5]):0,
						x = event.clientX-offsetX,
						y = event.clientY-offsetY;
					/**
					 * x,y [0,0]
					 * [a,b,c,d,e,f]
					 * x = ax+cy+e  
					 * y = bx+dy+f
					 */
					var eleId = cli.create('bpmn:ChinaissChunk', {
						  x: x -30 , y: y - 50
					},cli.element("Process_1"));
					var element = cli.element(eleId),
						nodeName = treeNode.name;
					cli.setLabel(element, nodeName);
					modeling.updateProperties(element,{viewId:treeNode.id,remark:treeNode.remark});
					
					var obj = pageRule[element.id] || (pageRule[element.id] = {});
	    			obj.id = element.id;
	    			obj.name = nodeName;
	    			obj.viewId = treeNode.id;
	    			obj.complexRule = JSON.parse(treeNode.complexRule);
	    			updateDirty();
				}
			},
			beforeRemove:function(treeId, treeNode){
				BootstrapDialog.confirm('您知道在做什么吗，请确认没有引用再删除！', function(result){
		            if(result) {
		            	$.ajax({
		            		url: contextPath + "/mx/form/formEventComplex/delete",
		            		type: "POST",
		            		data: {
		            			id : treeNode.id
		            		},
		            		success: function(data) {
		            			mtxx.zTree3Obj.reAsyncChildNodes(null, "refresh");
		            		},
		            		error: function() {
		            			BootstrapDialog.warning();
		            		}
		            	});
		            }
		        });
				return false;
			}
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parentId",
			}
		},
		edit: {
			enable: true,
			removeTitle: "删除复合构件",
			showRenameBtn: false,
			showRemoveBtn: true,
			editNameSelectAll: true,
			drag: {
				isCopy: true,
				isMove: false
			}
		}
	};

function updateDirty(){
	window.xdirty = true;
}

var pageRule = {},
	restoreRule = {},
	gridColumns = [{
        field: 'name',
        title: '名称',
        width: "100px"
    }, {
        field: 'value',
        title: '属性值',
        edit: true
    }],
    gridData = [],
    demo = initVue({
	    options: {
	        height: "100%"
	    },
	    gridColumns: gridColumns,
	    gridData: gridData
	}),
	baseGridData = [{
        name: "ID",
        code: "id",
        groupName: "基础",
        listno: 1,
        editor: "textbox"
    }, {
        name: "名称",
        code: "name",
        groupName: "基础",
        listno: 2,
        editor: "textbox"
    }],
    otherGridData = {
        "bpmn:ChinaissContainers": [{
            name: "选择控件",
            code: "input",
            groupName: "属性",
            listno: 3,
            editor: "dialog"
        }],
        "bpmn:ChinaissPageNode": [{
            name: "选择页面",
            code: "output_page",
            groupName: "属性",
            listno: 3,
            editor: "dialog"
        }],
        "bpmn:ChinaissSeqFlow": [],
        "bpmn:ChinaissParamFlow": [],
        "bpmn:ChinaissActionFlow": [],
        "bpmn:ChinaissAssistNode": [{
            name: "扩展构件",
            code: "out_ext",
            groupName: "属性",
            listno: 3,
            editor: "dialog",
            depend: "output",
        }],
        "bpmn:ChinaissGetNode": [{
        	name: "多构件选择",
            code: "minput",
            groupName: "属性",
            listno: 3,
            editor: "dialog"
        }],
        "bpmn:ChinaissDataOperate":[{
			name: "操作类型",
			code: "operate",
			groupName: "属性",
			listno: 1,
			data: [{NAME_:"",TITLE_:""},{NAME_:"mtcrud",TITLE_:"CUD事件"},{NAME_:"mtsearch",TITLE_:"查询服务"}],
			editor: "dropdown"
		},{
            name: "扩展构件",
            code: "out_ext",
            groupName: "属性",
            listno: 3,
            editor: "dialog",
            depend: "operate",
            depVal: "~out_ext"
        }]
    };
/**
 * 打开选择控件获取选中的输入控件
 * @returns
 */
function getInputData() {
    var $child = demo.$children[0],
        propName = $child.selectProp;
    var dd = {
        nodes: $child.obj[propName + "_data"] || [],
        isGroup: true
    };
    return dd;
}
//选择页面格式转换为选择控件格式
function pageObj2NodeObj(data) {
    var node = data.node;
    filterNode(node);
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
/**
 * 输入，输出，触发控件定义
 * @param data
 * @returns
 */
function retInputData(data) {
    if (data.name) {
        data = pageObj2NodeObj(data);
    }
    var $child = demo.$children[0],
        propName = $child.selectProp;
    $child.$set($child.obj, propName, data["names"] || data["name"]);
    var _data = data["nodes"] || data["node"] || data["value"] ;
    $child.$set($child.obj, propName + "_data", _data);
    mt_dialog.close();
    $('body').toggleClass('page-quick-sidebar-open');
    
    if(_data && _data.length == 1 && _data[0]){    	
    	var element = mtxx.selectElement,
    		treeNode = _data[0],
    		nodeName = treeNode.name+"->"+treeNode.eleId;
    	modeling.updateProperties(element,{chinaissDataRole:treeNode.drole});
    	$child.$set($child.obj, "name", nodeName);
    	cli.setLabel(element, nodeName);
    }else if(_data && _data.length > 1 ){
    	var element = mtxx.selectElement,
    		nodeName = "";
    	$.each(_data,function(i,_d){
    		nodeName += _d.name +",";
    	});
    	$child.$set($child.obj, "name", nodeName);
    	cli.setLabel(element, nodeName);
    }
}

/**
 * 输出扩展构建
 * @param data
 * @returns
 */
function retOutExtData(data) {
    var $child = demo.$children[0],
        propName = $child.selectProp;
    var outExtObj = findOutExtObj();
    //data.type = $child.obj["output_data"][0]["eName"];
    data.type = outExtObj.value;
    $child.$set($child.obj, propName, data.name);
    $child.$set($child.obj, propName + "_data", data);
    mt_dialog.close();
    $('body').toggleClass('page-quick-sidebar-open');
    var labelText = outExtObj["name"]+"-"+data.name;
    cli.setLabel(mtxx.selectElement,labelText);
}
/**
 * 回调定义返回
 * @param data
 * @returns
 */
function retCallbackFn(data) {
    var $child = demo.$children[0],
        propName = $child.selectProp;
    $child.$set($child.obj, propName, data[0].name);
    $child.$set($child.obj, propName + "_data", data);
    modeling.updateProperties(mtxx.selectElement,{"hasCallback":"true"});
    updateDirty();
    mt_dialog.close();
    $('body').toggleClass('page-quick-sidebar-open');
}

var urlObj = {
    "input": {
    	url: contextPath + "/mx/form/defined/ex/quickSelect?getFn=getInputData&retFn=retInputData&isTrigger=false&isRadio=true&showEvent=false&pageId=",
        title: "选择控件",
        width: "500px"
    },
    "minput": {
    	url: contextPath + "/mx/form/defined/ex/quickSelect?getFn=getInputData&retFn=retInputData&isTrigger=false&showEvent=false&pageId=",
    	title: "多选择控件",
    	width: "500px"
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
/**
 * 找到输出扩展对应的事件对象
 * @returns
 */
function findOutExtObj(){
	var element = mtxx.selectElement;
	if(element.type == "bpmn:ChinaissDataOperate"){
		var rule = pageRule[element.id];
		return {
			name:rule["operateName"],
			value:rule["operate"]
		};
	}
	var	outgoing = element.outgoing[0],
		tarElement = outgoing.target,
		tarIncoming = tarElement.incoming,
		incomings = [];
	$.each(tarIncoming,function(i,incoming){
		if(incoming.type == "bpmn:ChinaissSeqFlow"){
			incomings.push(incoming);
		}
	});
	if(incomings && incomings.length == 1){		
		return {
			value:pageRule[incomings[0].id]["execEvent"],
			name:pageRule[incomings[0].id]["execEventName"]
		};
	}
	return {};
}
/**
 * 弹出窗口设置
 * @param code
 * @param depend
 * @returns
 */
function openDialog(code, depend) {
    var uobj = depend && code != "callback" ? outExpDefined[depend]() : urlObj[code],
        url = uobj.url + pageId;
    mtxx.currentDialog = code;
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
    dialogShow(url,uobj);
}
/**
 * 打开窗口
 * @param url
 * @param uobj
 * @returns
 */
function dialogShow(url,uobj,callback){
	$('body').removeClass('page-quick-sidebar-open');
	uobj || (uobj={});
	var bodyWidth = $("body").width(),
		width = uobj.width || (bodyWidth - 24),
		height = uobj.height || ($("body").height() - 80);
	window.mt_dialog = BootstrapDialog.show($.extend({
		title: uobj.title || "窗口",
		draggable: true,
		message: function(dialog) {
			var pageToLoad = dialog.getData('pageToLoad');
			var frame = "<iframe onload=\"$(this).contents().find('body').css('overflow-y', 'auto');\" width='100%' height='" + height + "' src='" + pageToLoad + "' frameborder='no' scrolling='yes'></iframe>";
			return $(frame);
		},
		data: {
			'pageToLoad': url
		}
	},callback||{}));
	mt_dialog.$modalDialog.css({
		"top":"10px",
		"left":((bodyWidth - (width+"").replace("px",""))/2+"px") || "10px",
		"margin": '0px',
		"width": width
	});
	mt_dialog.$modalBody.css(uobj.bodyCss || {padding:0})
}

/**
 * 是否为有效元素
 * @param element
 * @returns
 */
function isUse(element) {
    return ["bpmn:ChinaissContainers","bpmn:ChinaissSeqFlow","bpmn:ChinaissPageNode","bpmn:ChinaissAssistNode","bpmn:ChinaissChunk","bpmn:ChinaissGetNode","bpmn:ChinaissDataOperate"].indexOf(element.type) != -1;
}
/**
 * 全部事件 
 */
var gobalEvents = {trigger:{},exec:{}};
$.ajax({
    url: contextPath + "/mx/form/defined/getEvent",
    type: "POST",
    data: {
    },
    success: function(data) {
        if(data && (data = JSON.parse(data)) && !data.statusCode ){
        	gobalEvents = data;
        }
    },
    error: function() {
    }
})
/**
 * 保存规则
 * @param $this
 * @returns
 */
function save($this) {
    viewer.saveXML({
        format: true
    }, function(err, xml) {
        console.log(xml);
        $.ajax({
            url: contextPath + "/mx/form/formEvent/saveFormEvent",
            type: "POST",
            data: {
                id: window._diagramId,
                diagram: xml,
                rule: JSON.stringify(pageRule),
                pageId: pageId,
                version:"2.0",
                eleId:null
            },
            success: function(data) {
                if(data && (data = JSON.parse(data)) && !data.statusCode ){
                    window._diagramId = data.id ;
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
/**
 * 保存复合构件
 * @param $this
 * @returns
 */
function save2($this) {
	var fname = $("#fname").val();
	if(!fname){
		BootstrapDialog.alert("请填写复合构件名称!");
	}else{
		var exposeId = $("[name=exposeName]:checked").val();
		if(exposeId){
			var coreRule = [pageRule[exposeId]];
			viewer.saveXML({
				format: true
			}, function(err, xml) {
				$.ajax({
					url: contextPath + "/mx/form/formEventComplex/saveFormEventComplex",
					type: "POST",
					data: {
						id: window._diagramId,
						diagram: xml,
						rule: JSON.stringify(pageRule),
						name: $("#fname").val(),
						remark: $("#fremark").val(),
						complexRule: JSON.stringify(coreRule),
						pageId: pageId
					},
					success: function(data) {
						if(data && (data = JSON.parse(data)) && !data.statusCode ){
							window.xdirty = false;
							window._diagramId = data.id ;
							$("#myModal2").modal("hide");
							BootstrapDialog.alert("保存成功");
							if(parent.location.href != window.location.href){
								parent.modeling.updateProperties(parent.mtxx.selectElement,{viewId:data.id,remark:data.remark});
								parent.cli.setLabel(parent.mtxx.selectElement,data.name);
								parent.mtxx.zTree3Obj.reAsyncChildNodes(null, "refresh");
							}
							if(!complexId){
								var newUrl = contextPath+"/mx/form/defined/workflow/workflow2?pageId="+pageId+"&isComplex=true&complexId="+data.id;
								window.location.href = newUrl;
							}
						}else{
							BootstrapDialog.alert("保存失败");
						}
						$this && $this.removeAttr('disabled');
					},
					error: function() {
						BootstrapDialog.alert("保存失败");
						$this && $this.removeAttr('disabled');
					}
				});
			});
		}else{
			BootstrapDialog.warning("请选择暴露构件.");
		}
	}
	
}
/**
 * 更新模型状态
 * @returns
 */
function updateModdleStatue(key,val){
	updateDirty();
	/**
	 * 更新模型属性
	 */
	function __update__(propObj){
		modeling.updateProperties(mtxx.selectElement,propObj);
	}
	/**
	 * 更新输入控件的连接线
	 */
	function __updateInWidget__(){
		var source = mtxx.selectElement.source;
		$.each(source.incoming,function(i,im){
			if(!im.hasEvent && !im.hasParam && !im.isInParam){
				modeling.updateProperties(im,{isInParam:true});
			}
		});
	}
	/**
	 * 增加辅助构件
	 */
	function __addAssistWidget__(){
		var target = mtxx.selectElement.target;
		var eleId = cli.create('bpmn:ChinaissAssistNode', {
			  x: target.x -30 , y: target.y - 50
		},cli.element("Process_1"));
		var assist = cli.element(eleId);
		cli.connect(assist, target, 'bpmn:ChinaissAssistFlow');
	}
	/**
	 * 清除辅助构件
	 */
	function __delAssistWidget__(){
		var target = mtxx.selectElement.target,
			removeList = [];
		$.each(target.incoming,function(i,im){
			if(im.type == 'bpmn:ChinaissAssistFlow'){
				removeList.push(im.source);
			}
		});
		$.each(removeList,function(i,shape){
			cli.removeShape(shape);
		})
	}
	/**
	 * 更新 连接线文本
	 */
	function __updateFlowLabel__(){
		var ele = mtxx.selectElement,
			rule = pageRule[ele.id],
			flowName = "",
			startName = "",
			endName = "";
		if(ele.target.type == "bpmn:ChinaissPageNode"){
			endName = "打开窗口";
		}
		if(ele.source.type != "bpmn:ChinaissExclusiveGateway"){
			startName = rule.triggrEventName;
		}
		if(ele.target.type != "bpmn:ChinaissExclusiveGateway" && ele.target.type != "bpmn:ChinaissPageNode"){
			endName = rule.execEventName;
		}
		flowName = startName + "-" + endName ;
		if(!startName){
			flowName = endName;
		}
		if(!endName){
			flowName = startName;
		}
		cli.setLabel(ele, flowName);
	}
	switch (key) {
	case "triggrEvent": //触发事件
		__update__({"hasEvent":val?true:false});
		__updateInWidget__();
		__updateFlowLabel__();
		break;
	case "execEvent": //执行方法
		if(val in outExpDefined){
			__addAssistWidget__();
		}else{
			__delAssistWidget__();
		}
		__updateFlowLabel__();
		break;
	case "inoutparams": //输入输出参数
		__update__({"hasParam":val?true:false});
		break;
	case "operate":
		var ele = mtxx.selectElement,
		rule = pageRule[ele.id]
		rule.outWidget =  [{
			eName : rule["operate"],
			isEvn : true,
			klevel : 1,
			level : 1,
			name : rule["operateName"],
			pObj : JSON.stringify({
				klevel:1,
				level : 1,
				eleId : pageId,
				pageId : pageId,
				drole: "page",
				isPage:true
			})
		}];
		delete rule.out_ext;
		delete rule.out_ext_data;
		break;
	default:
		break;
	}
}
/**
 * 复合构件编辑
 * @param viewId
 * @param willChage 是否提示变更
 * @returns
 */
function complexEdit(viewId,willChage){
	var url = contextPath+"/mx/form/defined/workflow/workflow2?pageId="+pageId+"&isComplex=true&complexId="+(viewId || "");
	willChage && (url += "&willChange=true");
	dialogShow(url,{
		title:"复合构件编辑",
		bodyCss:{
			"padding":"0px"
		},
	},{
		onhide: function(dialogRef){
		   var $body = dialogRef.getModalBody(),
		   	   iframe = $body.find("iframe")[0],
		   	   thatWin = iframe.contentWindow,
		   	   bool = true;
		   if(thatWin.xdirty && !thatWin.oneTime){
			   bool = false;
			   BootstrapDialog.confirm('未保存，是否继续关闭?', function(result){
		            if(result) {
		            	thatWin.oneTime = true;
		            	dialogRef.close();
		            }
		        });
		   }else{
			   var eles = thatWin.cli.elements(),
			   	   element;
			   $.each(eles,function(i,ele){
				   element = thatWin.cli.element(ele);
				   if(element.businessObject.willChange){
					   bool = false;
				   }
			   });
			   !bool && BootstrapDialog.warning("请先完成输入输出参数和回调定义的映射！");
		   }
           return bool;
        },
	});
}
var modeling;
jQuery(document).ready(function() {
	if(isComplex){
		$("#saveBt").hide();
		$("#saveBt2").show();
	}
	
	mtxx.zTreeObj = $.fn.zTree.init($("#ztree"), setting);
	mtxx.zTree2Obj = $.fn.zTree.init($("#ztree2"), setting2);
	mtxx.zTree3Obj = $.fn.zTree.init($("#ztree3"), setting3);
	
	$("#complexSave").on("click",function(){
        $(this).attr("disabled","disabled");
        save2($(this));
    });
	

    $("#saveBt").on("click",function(){
        $(this).attr("disabled","disabled")
        save($(this));
    });
    
    /**
     * 是否允许暴露结构
     */
    function canExpose(elementId){
    	var exposeTypes = ["ChinaissContainers","ChinaissPageNode"],
    		canExp = false;
    	if(elementId){
    		$.each(exposeTypes,function(i,exposeType){
    			if(elementId.indexOf(exposeType)>=0 && elementId.indexOf("label")<0){
    				canExp = true;
    				return false;
    			}
    		});
    	}
    	return canExp;
    }
    $("#saveBt2").on("click",function(){
    	var exposeIds = [];
		$.each(cli.elements(),function(i,elementId){
			if(canExpose(elementId)){
				exposeIds.push(elementId);
			}
		});
		if(exposeIds.length){
			var radioHtmls = [],
				expRule ,
				selectId = exposeIds[0];
			window._complexRule_ && (selectId = window._complexRule_[0]["id"]);
			$.each(exposeIds,function(i,exposeId){
				expRule = pageRule[exposeId];
				radioHtmls.push("<label><input type='radio' name='exposeName'"+(selectId==exposeId?"checked":"")+" value='"+exposeId+"'>"+expRule.name+"</label></br>");
			})
			$("#expose_col").html(radioHtmls.join(""));
			$("#myModal2").modal();
		}else{
			BootstrapDialog.warning("请添加 \"构件\"或者\"页面构件\"!");
		}
    });
    
    function openSaveTempPage(){
    	 var url = contextPath+"/mx/form/defined/workflow/event_template?isSave=true&pageId="+pageId+"&complexId="+window._diagramId;
         dialogShow(url,{
         	title:"保存模板",
         	width:"1200",
         	height:"600",
         	bodyCss:{
         		"padding":"0px"
         	},
         });
    }
    
    $("#saveBt3").on("click",function(){
    	if(window.xdirty){
    		BootstrapDialog.confirm('复合构件未保存将使用原来的复合构件规则来构件模板，是否继续?', function(result){
    			if(result) {
    				openSaveTempPage();
    			}else {
    			}
    		});
    	}else{
    		openSaveTempPage();
    	}
    });
    
    $("#keyboardBt").on("click",function(){
    	 $("#myModal").modal();
    });
    
    var contentHeight = $('.page-content').height();
    
    $("#myTabContent").height($('body').height()-$("#myTab").height());
    
    window.viewer = new BpmnJS({
        container: '#canvas',
        height: contentHeight,
        keyboard: { bindTo: document },
        additionalModules: [ bpmnJsCli, bpmnI18N, diagramMiniMap],
        cli: { bindTo: 'cli' },
        viewEye:function(event, element, autoActivate){
        	mtxx.selectElement = element;
        	complexEdit(element.businessObject.viewId);
        },
        viewTmp:function(event, element, autoActivate){
        	mtxx.selectElement = element;
        	var url = contextPath+"/mx/form/defined/workflow/event_template?isSelect=true&pageId="+pageId+"&complexId="+(element.businessObject.viewId || "");
        	dialogShow(url,{
        		title:"选择模板",
        		width:"1200",
        		height:"600",
        		bodyCss:{
        			"padding":"0px"
        		},
        	});
        },
        viewDataset:function(event, element, autoActivate){
        	mtxx.selectElement = element;
        	if(element.businessObject.datasetId){
        		window.open(contextPath+"/mx/dataset/sqlbuilder?id="+element.businessObject.datasetId);
        	}else{
        		$.alert(0,"未选择数据集!");
        	}
        }
    });
  
    var url = contextPath + "/mx/form/formEvent/getEventByPageId";
    if(isComplex){
    	url = contextPath + "/mx/form/formEventComplex/getEventByPageId";
    }else if(isTemplate){
    	url = contextPath + "/mx/form/formEventTemplate/getEventByPageId";
    }
    
    $.ajax({
        url: url,
        type: "POST",
        data: {
        	complexId: complexId,
        	templateId: templateId,
            pageId: pageId
        },
        success: function(data) {
            var dataObj = JSON.parse(data);
            //只有在未定义的情况下才能进行转换
            if(toDiagram && $.isEmptyObject(dataObj)){
            	renderXml();
            	toDiagramFun(pageId);
            	$("#saveBt").removeAttr("disabled");
            	return;
            }else if(toDiagram && !$.isEmptyObject(dataObj)){
            	$.alert(1,"已经定义或转换后，不能再次转换！！");
            }
            dataObj.rule && (pageRule = JSON.parse(dataObj.rule));
            window._diagramId = dataObj.id;
            if(window._diagramId){
            	//$("#fname_row").hide();
            	if(isComplex=="true"){
            		$("#saveBt3").show();
            		window._complexRule_ = dataObj.complexRule && JSON.parse(dataObj.complexRule);
            		$("#fname").val(dataObj.name);
            		$("#fremark").val(dataObj.remark);
            	}else if(isTemplate){
            		$("#saveBt").hide();
            	}else{
            		$("#saveBt").removeAttr("disabled");
            	}
            }else{
            	$("#saveBt").removeAttr("disabled");
            }
            renderXml(dataObj.diagram);
        },
        error: function() {
            renderXml();
        }
    });
    var emptyXml = '<?xml version="1.0" encoding="UTF-8"?><bpmn2:definitions xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:bpmn2="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" id="sample-diagram" targetNamespace="http://bpmn.io/schema/bpmn" xsi:schemaLocation="http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd"><bpmn2:process id="Process_1" isExecutable="false" /><bpmndi:BPMNDiagram id="BPMNDiagram_1"><bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_1" /></bpmndi:BPMNDiagram></bpmn2:definitions>';
    function renderXml(xml) {
        xml = xml || emptyXml;
        viewer.importXML(xml, function(err) {
        	modeling = cli._injector.get("modeling");
            if (err) {
                console.log('error rendering', err);
            } else {
            	if(window.willChange){
            		window.paramdirty = true;
            		$.alert(1, "请修改粗体连接线中的输入输出参数或回调参数。");
            	}
            	
                var eventBus = viewer.get('eventBus');

                /**
                 * can connect
                 */
                eventBus.on('connect.end',2000, function(e) {
                	var context = e.context,
                		source = context.source,
                		target = context.target,
                		errorMsg = [];
                	if(target == null || $.inArray(target.type,["bpmn:Process","bpmn:ChinaissAssistNode","bpmn:ChinaissDataNode","bpmn:ChinaissGetNode"])>=0){
                		return false;
                	}
                	/*
                	 * 此类型节点必须为输入参数
                	 */
                	if(source.type == "bpmn:ChinaissGetNode"){
                		var bool = false;
                		$.each(target.outgoing,function(i,og){
            				if(og.type == "bpmn:ChinaissSeqFlow" && og.businessObject.hasEvent){
            					bool = true;
            					return false;
                			}
            			});
                		if(!bool){
                			$.alert(1,"多参数构件必须连接触发事件");
                			return false;
                		}
                	}
                	//如果是下级事件可连接线
                	var hasNextEvent = isOutHasNext(source);
                	if(hasNextEvent){
                		return;
                	}
                	
                	//判断输出线
            		$.each(source.outgoing,function(i,og){
            			if(og.target.id == target.id){
        					errorMsg.push("目标和源节点无须重复连接");
        					return false;
        				}
            			/*//判断当前节点为触发控件
            			if(og.type == "bpmn:ChinaissSeqFlow" && og.businessObject.hasEvent){
            				return false;
            			//判断当前节点为输入参数
            			}else if(og.type == "bpmn:ChinaissSeqFlow" && og.businessObject.isInParam){
            				return false;
            			}*/
            		});
            		
        			$.each(source.incoming,function(i,im){
        				if(im.source.id == target.id){
        					errorMsg.push("目标和源节点不能相互引用");
        					return false;
        				}
        				//判断当前节点为输出控件
        				if(im.type == "bpmn:ChinaissSeqFlow" && (im.businessObject.hasParam || im.businessObject.hasCallback)){
        					errorMsg.push("源节点不能构造下级事件");
        					return false;
        				}
        			});
        			
        			$.each(target.outgoing,function(i,og){
        				if(og.type == "bpmn:ChinaissSeqFlow" && og.businessObject.isInParam){
            				errorMsg.push("参数控件不能再被操作");
            				return false;
            			}
        			});
        			
                	if(errorMsg.length){
                		$.alert(1,errorMsg.join("<br/>") || "未知情况暂不能配置,请联系管理员!");
                		return false;
                	}
                });
                
                var events = [
                    'element.click',
                    'shape.added',
                    'shape.remove',
                    'connect.end',
                    'element.paste'
                ];
                events.forEach(function(event) {
                    eventBus.on(event, function(e) {
                    	/*
                    	 * 粘贴
                    	 */
                    	if(e.type == 'element.paste'){
                    		if((e.descriptor.type != "label")){
                    			var sRule = pageRule[e.descriptor.id];
                    			sRule.id = e.descriptor.businessObject.id;
                    			pageRule[e.descriptor.businessObject.id] = sRule;
                    		}
                    		return;
                    	}
                    	/*
                    	 * 连线结束后
                    	 */
                    	if(e.type == 'connect.end'){
                    		var context = e.context,
                    			source = context.source,
                    			target = context.target;
                    		//更新如果是输入参数的话更新线状态
                    		if(!source.incoming.length){
                    			$.each(target.outgoing,function(i,outgo){
                    				if(outgo.id in pageRule){
                    					var rule =  pageRule[outgo.id];
                    					if(rule["triggrEvent"]){
                    						$.each(source.outgoing,function(i,og){
                    							if(og.source.id == source.id && og.target.id == target.id){
                    								modeling.updateProperties(og,{isInParam:true});
                    							}
                    						});
                    						return false;
                    					}
                    				}
                    			});
                    		}
                    		return;
                    	}
                    	/*
                    	 * 元素添加后，主要用于还原规则
                    	 */
                    	if(e.type == "shape.added"){
                    		if(e.element.id in restoreRule){
                    			pageRule[e.element.id] = restoreRule[e.element.id];
                        		delete restoreRule[e.element.id];
                    		}
                    		return ;
                    	}
                    	//输出扩展  && outExpDefined[rule["execEvent"]]
                    	var element = e.element,
                    		businessObject = element.businessObject,
                    		eleType = element.type;
                    	/*
                    	 * 元素删除后，记录规则，以便用于还原
                    	 */
                    	if(e.type == "shape.remove"){
                    		restoreRule[element.id] = pageRule[element.id];
                    		delete pageRule[element.id];
                    	}else{
                    		if (isUse(element)) {
                    			var isSourceGateway = (businessObject.sourceRef && businessObject.sourceRef.$type == "bpmn:ChinaissExclusiveGateway");
                    			var isTargetGateway = (businessObject.targetRef && businessObject.targetRef.$type == "bpmn:ChinaissExclusiveGateway");
                    			mtxx.selectElement = element;
                    			$('body').addClass('page-quick-sidebar-open');
                    			var obj = pageRule[element.id] || (pageRule[element.id] = {});
                    			obj.id = element.id;
                    			obj.name = businessObject.name;
                    			demo.$data.obj = obj;
                    			var gridData = $.extend([],otherGridData[eleType]) || [];
                    			var eleSource = element.source,
	                    			eleTarget = element.target,
	                    			tRule = {},
	                    			srole="",
	                    			trole="";
                    			if(eleType=="bpmn:ChinaissSeqFlow" && !element.businessObject.isInParam){
                    				if(eleSource && eleSource.id && (eleSource.type == "bpmn:ChinaissContainers" || eleSource.type == "bpmn:ChinaissPageNode") && (tRule = pageRule[eleSource.id])){
                    					var inputRule = tRule.input_data,
                    						srole = inputRule[0]["drole"];
                    				}
                    				if(eleSource.type == "bpmn:ChinaissChunk" && (tRule = pageRule[eleSource.id])){
            							var complexRule = tRule.complexRule,
                						srole = complexRule[0]["input_data"][0]["drole"];
            						}
                    				var isoutput = isOutHasNext(eleSource);
                    				if((isTargetGateway || (!isSourceGateway && !isTargetGateway))){
                    					if(isoutput){
                    						gridData.push({
                    							name: "回调定义",
                    							code: "callback",
                    							groupName: "属性",
                    							listno: 3,
                    							editor: "dialog"
                    						});
                    					}else{
                							gridData.push({
                								name: "触发事件",
                								code: "triggrEvent",
                								groupName: "属性",
                								listno: 1,
                								data: [{NAME_:"",TITLE_:""}].concat(gobalEvents["trigger"][srole]||{}),
                								editor: "dropdown"
                							});
                    					}
                    				}
                    				if(isSourceGateway){
                    					gridData.push({
                    						name: "表达式定义",
                    						code: "expression",
                    						groupName: "属性",
                    						listno: 2,
                    						editor: "dialog"
                    					});
                    				}
                    				if(!isTargetGateway){
                    					/**
                    					 * 普通构件
                    					 */
                    					var hasTarget = false;
                    					if(eleTarget && eleTarget.id && (eleTarget.type == "bpmn:ChinaissContainers") && (tRule = pageRule[eleTarget.id])){
                    						var inputRule = tRule.input_data,
                    						trole = inputRule?inputRule[0]["drole"]:"";
                    						hasTarget = true;
                    					}
                    					/**
                    					 * 复合构件
                    					 */
                    					if(eleTarget && eleTarget.id && eleTarget.type == "bpmn:ChinaissChunk"  && (tRule = pageRule[eleTarget.id])){
                							var complexRule = tRule.complexRule,
                							trole = complexRule[0]["input_data"][0]["drole"];
                							hasTarget = true;
                						}
                    					if(hasTarget){
                    						gridData.push({
                    							name: "执行方法",
                    							code: "execEvent",
                    							groupName: "属性",
                    							listno: 2,
                    							data: [{NAME_:"",TITLE_:""}].concat(gobalEvents["exec"][trole]||{}),
                    							editor: "dropdown"
                    						});
                    					}
                    					gridData.push({
                    						name: "输入输出参数",
                    						code: "inoutparams",
                    						groupName: "属性",
                    						listno: 3,
                    						editor: "dialog"
                    					});
                    				}
                    			}
                    			demo.$data.gridData = baseGridData.concat(gridData);
                    		} else {
                    			demo.$data.obj = {};
                    			demo.$data.gridData = [];
                    			$('body').removeClass('page-quick-sidebar-open');
                    		}
                    	}
                    });
                });
            }
        });
    }
});

/**
 * 获取输入参数
 * @param actionFlow
 * @returns
 */
function getPrepElements(actionFlow){
	var source = actionFlow.source,
		incomings = source.incoming,
		inrules = [],
		isource;
	function iter(incomings){
		$.each(incomings,function(i,incoming){
			isource = incoming.source;
			if(incoming.type == "bpmn:ChinaissSeqFlow"){
				if(isource.type == "bpmn:ChinaissExclusiveGateway"){
					var incomings = isource.incoming; 
					iter(incomings);
				}else if(isource.type == "bpmn:ChinaissChunk"){	
					var input_data = pageRule[isource.id]['complexRule'][0]["input_data"] || [];				
					inrules.push(input_data[0]);
				}else{				
					if(isource.id in pageRule && !incoming.businessObject.hasEvent && !isource.incoming.length){		
						var input_data = pageRule[isource.id]["input_data"] || pageRule[isource.id]["minput_data"] || [];				
						//inrules.push(input_data[0]);
						input_data.length && $.merge(inrules,input_data);
					}else{
						var incomings = isource.incoming; 
						iter(incomings);
					}
				}			
			}else{
				var incomings = isource.incoming; 
				iter(incomings);
			}
		});
	}
	iter(incomings);
	return inrules;
}
/**
 * 获取输出参数
 * @param actionFlow
 * @returns
 */
function getNextElements(actionFlow){
	var actionRule = pageRule[actionFlow.id],
		isGetSource = mtxx.currentDialog == "callback" /*isOutHasNext(actionFlow.source)*/,
		target = isGetSource?actionFlow.source:actionFlow.target,
		rule = /*isGetSource?null:*/pageRule[target.id],
		isPage = target.type == "bpmn:ChinaissPageNode",
		incomings = target.incoming,
		retObj = {
			outWidget :null
		},
		widgetRule;
	if((actionRule["execEvent"] || isPage) && !isGetSource){	
		if(rule && rule["input_data"] && rule["input_data"].length){
			widgetRule = rule["input_data"][0];
			widgetRule["level"] = widgetRule["klevel"];
		}
		retObj.outWidget = isPage ? JSON.stringify(rule["output_page_data"]) : JSON.stringify([{
			isEvn:true,
			eName:actionRule["execEvent"],
			name :actionRule["execEventName"],
			level:widgetRule["klevel"],
			klevel:widgetRule["klevel"],
			pObj:JSON.stringify(widgetRule)
		}]);
		$.each(incomings,function(i,incoming){
			if(incoming.type == "bpmn:ChinaissAssistFlow"){
				var extWidget = incoming.source;
				if(extWidget.id in pageRule){
					retObj.outExt = pageRule[extWidget.id]["out_ext_data"];
				}
				return false;
			}
		});
	}else if(target.type == "bpmn:ChinaissDataOperate"){
		/*
		 * 快捷 cud事件 和 查询服务 
		 */
		retObj.outWidget = JSON.stringify(rule.outWidget);
		retObj.outExt = rule["out_ext_data"];
	}
	return retObj;
}
/**
 * 判断是否有回调回调
 * @param eleSource
 * @returns
 */
function isOutHasNext(eleSource){
	var incomings = eleSource.incoming,
		isout = false;
	if(eleSource.type == "bpmn:ChinaissDataOperate"){
		return true;
	}
	$.each(incomings,function(i,incom){
		if(incom.type == "bpmn:ChinaissAssistFlow"){
			isout = true;
			return false;
		}
	});
	return isout;
}
//输入输出参数定义中 获取输入输出控件
function getParamFn() {
    var actionFlow = mtxx.selectElement,
        eventNode,
        inputNode;
    
    var inWidget = actionFlow?getPrepElements(actionFlow):[];	
    var outAndExtWidget = getNextElements(actionFlow);	

    var data = {};
    if(inWidget){
    	data.inWidget = JSON.stringify(inWidget);
    }
    if(outAndExtWidget){
    	$.extend(data,outAndExtWidget);
    }
    return data;
}


//选择树返回参数
function retParamFn(data) {
    if (data) {
        var $child = demo.$children[0];
        $child.$set($child.obj, "inoutparams", data[0].name);
        $child.$set($child.obj, "inoutparams_data", data);
    }
    updateModdleStatue("inoutparams",data?true:false);
    modeling.updateProperties(mtxx.selectElement,{"willChange":""});
    updateDirty();
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
    cli.setLabel(mtxx.selectElement,data.expVal);
}

//获取参数
function getExpression() {
    var actionFlow = mtxx.selectElement,
    	idata =  getPrepElements(actionFlow),
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
	$(".myalert").remove();
	$("body").append(alertDom);
	$(".myalert").fadeIn(500).delay(3000).fadeOut(500);
}