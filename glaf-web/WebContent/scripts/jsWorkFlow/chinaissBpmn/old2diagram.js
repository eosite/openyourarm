/**
 * 主要用于旧的事件定义器转化为图形定义器
 */

$(function() {
	// 原始偏移量
	var originalX = 240, originalY = 160,
	// x偏移量
	offsetX = originalX,
	// 行数
	rowCount = 1,
	// 列数
	colCount = 0;
	var offsetOperate = {
		addX : function() {
			offsetX += originalY;
		},
		resetX : function() {
			offsetX = originalX;
		},
		getX : function() {
			return offsetX;
		},
		setX : function(x) {
			offsetX = x;
		}
	};
	/**
	 * 创建元素
	 * 
	 * @param role
	 * @returns
	 */
	function createEle(role, rule) {
		var eleId = cli.create(role || 'bpmn:ChinaissContainers', $.extend({
			x : offsetX,
			y : (rowCount * 140)
		}, rule || {}), cli.element("Process_1"));
		return cli.element(eleId);
	}
	/**
	 * 输入控件
	 */
	function convertInput(inWidgets) {
		if (inWidgets && inWidgets.length) {
			var isMutil = inWidgets.length > 1, element = createEle(isMutil ? 'bpmn:ChinaissGetNode' : 'bpmn:ChinaissContainers');
			// 偏移
			offsetOperate.addX();
			var inputName = "", name = "", drole = "";
			$.each(inWidgets, function(i, inWidget) {
				drole = inWidget.drole;
				name = inWidget.name + "->" + inWidget.eleId;
				inputName += inWidget.name + ",";
			})
			var rule = {
				id : element.id,
				name : isMutil ? inputName : name
			};
			if (isMutil) {
				rule["minput_data"] = inWidgets;
				rule["minput"] = inputName;
			} else {
				rule["input_data"] = inWidgets;
				rule["input"] = inputName;
			}
			pageRule[element.id] = rule;

			cli.setLabel(element, rule.name);
			!isMutil && modeling.updateProperties(element, {
				chinaissDataRole : drole
			});

			return element;
		}
	}
	/**
	 * 更新元素标签文本和规则
	 * 
	 * @param widget
	 * @param element
	 * @returns
	 */
	function updateElementAndRule(widget, element) {
		var pObj = JSON.parse(widget.pObj);
		pageRule[element.id] = {
			id : element.id,
			name : pObj.name + "->" + (pObj.eleId || widget.eleId),
			input : pObj.name,
			input_data : [ pObj ]
		};
		buildDataset(pObj.drole,element,pageId,pObj.eleId);
		if (element.type == "bpmn:ChinaissContainers" && pObj.drole) {
			modeling.updateProperties(element, {
				chinaissDataRole : pObj.drole
			})
		}
		cli.setLabel(element, pageRule[element.id].name);
	}
	/**
	 * 创建分之路由
	 */
	function createRouteByTrigger(ele, widget) {
		var element = createEle('bpmn:ChinaissExclusiveGateway');
		if (ele) {
			var seqFlowId = cli.connect(ele, element, 'bpmn:ChinaissSeqFlow'), seqFolw = cli.element(seqFlowId);
			pageRule[seqFlowId] = {
				id : seqFlowId,
				name : widget.name,
				triggrEvent : widget.eName,
				triggrEventName : widget.name
			}
			// 更新触发事件
			modeling.updateProperties(seqFolw, {
				hasEvent : true
			});
			cli.setLabel(seqFolw, pageRule[seqFlowId].name);
		}
		return element;
	}
	/**
	 * 转换触发控件
	 * 
	 * @returns
	 */
	function convertTrigger(widget, val, inEle) {
		var element = createEle();
		// 连接输入和触发(如果有输入)
		if (inEle) {
			var seqFlowId = cli.connect(inEle, element, 'bpmn:ChinaissSeqFlow');
			var seqFlow = cli.element(seqFlowId);
			modeling.updateProperties(seqFlow, {
				isInParam : true
			});
		}
		updateElementAndRule(widget, element);
		offsetOperate.addX();
		// 创建分之路由
		var routeEle = createRouteByTrigger(element, widget);
		$.each(val["widgetEvent"], function(i, wEvent) {
			if (i == 0) {
				offsetOperate.addX();
			}
			$.each(wEvent["outWidget"], function(j, oWidget) {
				convertOutput(oWidget, wEvent, routeEle);
				if (j != wEvent["outWidget"].length - 1)
					rowCount++;
			});
			rowCount++;
		});
	}
	/**
	 * 创建触发到输出控制线
	 * 
	 * @param oWidget
	 * @param element
	 * @param routeEle
	 * @returns
	 */
	function createTrigger2OutputFlow(oWidget, wEvent, element, routeEle) {
		var seqFlowId = cli.connect(routeEle, element, 'bpmn:ChinaissSeqFlow'), seqFlow = cli.element(seqFlowId);
		pageRule[seqFlowId] = {
			id : seqFlowId,
			name : oWidget.name,
			execEvent : oWidget.eName,
			execEventName : oWidget.name,
			expression : wEvent.exp,
			expression_data : wEvent.expdata,
			inoutparams : wEvent.paramName,
			inoutparams_data : filterParam(wEvent.param,oWidget)
		};
		cli.setLabel(seqFlow, pageRule[seqFlowId].execEventName);
		// 更新为参数状态线
		if (pageRule[seqFlowId].inoutparams_data && pageRule[seqFlowId].inoutparams_data.length) {
			modeling.updateProperties(seqFlow, {
				hasParam : true
			});
		}
	}

	/**
	 * 创建分之路由 下级事件
	 */
	function createRouteByChild(outEle, wEvent) {
		var element = createEle('bpmn:ChinaissExclusiveGateway');
		var seqFlowId = cli.connect(outEle, element, 'bpmn:ChinaissSeqFlow');
		pageRule[seqFlowId] = {
			id : seqFlowId,
			name : "",
			callback : wEvent.callbackName,
			callback_data : wEvent.callback,
		}
		return element;
	}

	/**
	 * 输出控件
	 * 
	 * @returns
	 */
	function convertOutput(oWidget, wEvent, routeEle) {
		var element = createEle();
		// 如果拥有输出扩展事件
		if (wEvent.outExt) {
			createAssist(oWidget, wEvent, element);
		}
		updateElementAndRule(oWidget, element);
		// offsetOperate.addX();
		// 创建事件连接线
		createTrigger2OutputFlow(oWidget, wEvent, element, routeEle);
		/**
		 * 下级事件
		 */
		if ((wEvent.childs && wEvent.childs.length) || (wEvent.callback && wEvent.callback.length)) {
			offsetOperate.addX();
			var cRouteEle = createRouteByChild(element, wEvent);
			$.each(wEvent.childs, function(i, child) {
				if (i == 0) {
					offsetOperate.addX();
				}
				$.each(child.outWidget, function(j, coWidget) {
					convertOutput(coWidget, child, cRouteEle);
					if (j != child.outWidget.length - 1)
						rowCount++;
				});
				if (i != 0) {
					rowCount++;
				}
			});
		}

	}
	/**
	 * 过滤输入输出参数,只包含当前输出控件的参数
	 * 
	 * @returns
	 */
	function filterParam(param,oWidget) {
		if(param && param.length){
			var pObj = JSON.parse(oWidget.pObj);
			$.each(param,function(i,p){
				for(var key in p.datas){
					if(key != pObj.eleId){
						delete p.datas[key];
					}
				}
			})
		}
		return param;
	}

	/**
	 * 创建辅助构件
	 * 
	 * @returns
	 */
	function createAssist(oWidget, wEvent, outEle) {
		var assistNode = createEle("bpmn:ChinaissAssistNode", {
			x : outEle.x - 60,
			y : outEle.y - 30
		});
		pageRule[assistNode.id] = {
			id : assistNode.id,
			name : oWidget.name + "-" + wEvent.outExtName,
			out_ext : wEvent.outExtName,
			out_ext_data : wEvent.outExt
		};
		cli.setLabel(assistNode, pageRule[assistNode.id].name);
		cli.connect(assistNode, outEle, 'bpmn:ChinaissAssistFlow');
	}

	/**
	 * 主函数方法
	 * 
	 * @param pageId
	 * @returns
	 */
	function toDiagramFun(pageId) {
		App.blockUI({
			target : "#body",
			message : "正在转换中...",
		});
		$.ajax({
			url : contextPath + "/mx/form/defined/getEventDefinedStr",
			type : "POST",
			data : {
				pageId : pageId
			},
			success : function(data) {
				if (data && (data = JSON.parse(data))) {
					$.each(data, function(i, d) {
						$.each(d.values, function(j, val) {
							// 输入控件处理
							var inElement = convertInput(val["inWidget"]);
							// 存储当前偏移位置
							var storeOffsetX = offsetX;
							// 触发控件拆分
							$.each(val["widget"], function(k, widget) {
								// 转换第一个触发控件
								convertTrigger(widget, val, inElement);
								if (k != val["widget"].length - 1)
									rowCount++;
							});
							offsetOperate.resetX();
						})
					});
				}
				App.unblockUI("#body");
			},
			error : function() {
				$.alert(1, "未知错误，请联系管理员");
				App.unblockUI("#body");
			}
		});
	}

	window.toDiagramFun = toDiagramFun;
});