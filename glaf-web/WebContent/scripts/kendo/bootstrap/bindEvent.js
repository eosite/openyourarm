$.validator.methods.customRule = function(value, element, param) {
	return initPageValidate.prototype.customRule($(element));
};
var setCustomRule = function($element, message, param) {
	$element.rules('add', {
		customRule: param || '',
		messages: {
			customRule: message
		}
	});
};
// 初始化页面层次
function initPageLevel() {
	var parent,
		parentPageLevel;
	try {
		parent = window.opener || window.parent;
		parentPageLevel = parent.pageLevel;
	} catch (e) {

	}
	if (parentPageLevel) {
		pageLevel = parentPageLevel + 1;
	} else {
		pageLevel = 1;
	}
	if (pageLevel != 1) {
		if (isWeighting) { // 合并事件
			var pageedJson = null
			try{
				pageedJson = JSON.parse(pageed);
			}catch(e){
				pageedJson = eval(pageed);
			}
			
			pageed = $.extend({}, parent.pageed || {});
			pageed[pageLevel] = pageedJson;
		} else {
			pageed = $.extend({}, parent.pageed || {})/* parent.pageed */;
		}
	} else {
		if (pageed) {
			var pageedJson = null
			try{
				pageedJson = JSON.parse(pageed);
			}catch(e){
				pageedJson = eval(pageed);
			}
			pageed = {};
			pageed[pageLevel] = pageedJson;
		}
	}
	if (pageed) {
		for (var plevel in pageed) {
			var pageEvents = pageed[plevel],
				len = pageEvents.length,
				pageEvent, triggers, trigger, thref = window.location.href,
				pageParamsObj = pageParameters || {},
				pageId = pageParamsObj['id'],
				stockMoObj = {};
			for (var i = 0; i < len; i++) {
				pageEvent = pageEvents[i];
				triggers = pageEvent.trigger;
				for (var j = 0; j < triggers.length; j++) {
					trigger = triggers[j];
					if ((trigger.level == pageLevel || (trigger.level == pageLevel - plevel + 1)) && (thref.indexOf(trigger.pageId) != -1 || pageId == trigger.pageId)) { // 控件事件的层级
						// TODO 注册事件
						var $this = $('#' + trigger.eleId),
							r = trigger.eleId.length > 31 ? "page" : pubsub.getRole(trigger.eleId),
							nb = (r == "button" ? "default" : r),
							t = $this
							.attr('data-role') || nb;

						if (trigger.otype && trigger.oid) {
							$this = $('#' + trigger.oid);
							t = trigger.otype;
						}
						var columnnameKey = "columnname",
							columnname = $this.attr(columnnameKey),
							ety = null;
						if (columnname) {
							var $table = $this.closest('table'),
								eventType = trigger.eventType,
								index = $this.closest("tr").index();
							if ("listenVal" == eventType) {
								var moObj = stockMoObj[$table.attr("tbid")] || (stockMoObj[$table.attr("tbid")] = {
										target: $table,
										stock: []
									}),
									stock = moObj.stock;
								stock.push({
									columnname: columnname,
									pageEvent: pageEvent,
									index: index
								});
								eventType = "change";
							}
							else if ("listenKey" == eventType) {
								var moObj = stockMoObj[$table.attr("tbid")] || (stockMoObj[$table.attr("tbid")] = {
										target: $table,
										stock: []
									}),
									stock = moObj.stock;
								stock.push({
									columnname: columnname,
									pageEvent: pageEvent,
									index: index
								});
								eventType = "keyup";
							}
							else if ("listenEnter" == eventType) {
								var moObj = stockMoObj[$table.attr("tbid")] || (stockMoObj[$table.attr("tbid")] = {
										target: $table,
										stock: []
									}),
									stock = moObj.stock;
								stock.push({
									columnname: columnname,
									pageEvent: pageEvent,
									index: index
								});
								eventType = "keyup";
								ety = "listenEnter";
							}
							if(ety != null){
								$table.on(eventType + "." + columnname, "[" + columnnameKey + "=" + columnname + "]", {
									index: index,
									pageEvent: pageEvent
								}, function(e) {
									if(e.keyCode == "13") {
										pubsub.elongateStockIndex = $(this).closest("tr").index() - e.data.index;
										pubsub.pub(e.data.pageEvent, "");
										pubsub.elongateStockIndex = null;
								    }								
								});
							}
							else{
							$table.on(eventType + "." + columnname, "[" + columnnameKey + "=" + columnname + "]", {
								index: index,
								pageEvent: pageEvent
							}, function(e) {
								pubsub.elongateStockIndex = $(this).closest("tr").index() - e.data.index;
								pubsub.pub(e.data.pageEvent, "");
								pubsub.elongateStockIndex = null;
							});
							}
						} else {
							var fn = pubsubobjects[t] || pubsubobjects["default"];
							fn.call($this, {
								pageEvent: pageEvent,
								trigger: trigger,
								eventType: trigger.eventType
							});
						}
					}
				}
			}
			for (var tbid in stockMoObj) {
				var stock = stockMoObj[tbid];
				stock.target.data("moStock", stock);
				var ink = new MutationObserver(function(record) {
					$.each(record, function(index, el) {
						var columnname = el.target.getAttribute ? (el.target.getAttribute("columnname") || $(el.target).closest('[data-role]').attr("columnname")) : null;
						var tstock = $(el.target).closest('table').data("moStock")["stock"];
						if ((columnname && el.attributeName.indexOf("setvalue") != -1)) {
							$.each(tstock, function(i, v) {
								if (v.columnname == columnname) {
									pubsub.elongateStockIndex = $(el.target).closest("tr").index() - v.index;
									pubsub.pub(v.pageEvent, "");
									pubsub.elongateStockIndex = null;
								}
							});
						} else if (el.removedNodes.length) {
							$.each(tstock, function(i, v) {
								pubsub.elongateStockIndex = $(el.previousSibling).closest("tr").index() - v.index;
								pubsub.pub(v.pageEvent, "");
								pubsub.elongateStockIndex = null;
							});
						}
					});
				});
				ink.observe(stock.target[0], {
					'attributes': true,
					'subtree': true,
					'childList': true,
					'attributesFilter': ['setvalue']
				});
			}
		}
	}
	initPageValidate({
		validate_type_foucs: function($this) {
			$this.attr("valiBlur", "true");
			$this.on('blur', function(event) {
				$(this).valid();
			});
		},
		validate_type_save: function($this) {
			$this.attr("valiSave", "true");
			/* $(vali).mtbootstrap('validate'); */
		},
		bindEvent: function() {

			/**
			 * 外套一层form 以供表单验证
			 */
			var $bodyContent = $('body').children();
			$('body').append('<form id="validateform" >');
			$('body').find('#validateform').append($bodyContent);
			$("#validateform").validate({
				// debug: true,
				onfocusout: false,
				errorElement: "em",
				errorPlacement: function(error, element) {
					// Add the `help-block` class to the error element
					
					if($(element).data("isJqfileupload2")){
						element = $(element).parent();
					}

					error.addClass("help-block");
					error.insertAfter(element);

					var tipColor = error.css('color');

					if ($(element).data('isICheck')) {
						$(element).closest('label').parent().addClass('has-error');
						$(element).closest('label').parent().append(error);
					} else if ($(element).data('isSelect2')) {
						error.insertAfter($(element).next().next());
					} else if ($(element).data('isDatepicker')) {
						error.insertAfter($(element).parent());
					}

					// Add `has-feedback` class to the parent div.form-group
					// in order to add icons to inputs
					if ($(element).data('isDatepicker')) {
						element.parent("div").parent("div").addClass("has-feedback");
					} else {
						element.parent("div").addClass("has-feedback");
					}

					// Add the span element, if doesn't exists, and apply the
					// icon classes to it.
					var feedback = $(element).next("span.glyphicon")[0];
					if ($(element).data('isDatepicker')) {
						feedback = $(element).parent('div').next("span.glyphicon")[0];
					}
					if (!feedback && !$(element).data('isICheck')) {
						var nlayout_elem = '';
						if ($(element).hasClass('nlayout_elem') || $(element).data('isDatepicker')) {
							nlayout_elem = ' nlayout_elem';
						}
						$("<span class='glyphicon glyphicon-remove form-control-feedback" + nlayout_elem + "'></span>").insertAfter(element);

						feedback = $(element).next("span.glyphicon")[0];
						if ($(element).data('isDatepicker')) {
							feedback = $(feedback).insertAfter(element.parent('div'));
						}

						var $feedback = $(feedback);
						$feedback.css('line-height', $feedback.height() + 'px');
						if ($(element).data('isSelect2')) {
							$feedback.css({
								'z-index': 100,
								'right': 0
							});
						}
						if ($(element).data('isDatepicker')) {
							$feedback.css({
								'background-color': 'white',
								'border-left': '1px solid ' + tipColor
							});
							var dateToggle = $(element).next();
							dateToggle.find('button').css({
								'color': tipColor,
								'border': '1px solid ' + tipColor
							});
						}
					}
				},
				success: function(label, element) {
					// Add the span element, if doesn't exists, and apply the
					// icon classes to it.
					if($(element).data("isJqfileupload2")){
						element = $(element).parent();
					}
					var feedback = $(element).next("span.glyphicon")[0];
					if ($(element).data('isDatepicker')) {
						feedback = $(element).parent('div').next("span.glyphicon")[0];
					}
					var tipColor = $(feedback).css('color');
					if (!feedback && !$(element).data('isICheck')) {
						var nlayout_elem = '';
						if ($(element).hasClass('nlayout_elem') || $(element).data('isDatepicker')) {
							nlayout_elem = ' nlayout_elem';
						}
						$("<span class='glyphicon glyphicon-ok form-control-feedback" + nlayout_elem + "'></span>").insertAfter(element);

						feedback = $(element).next("span.glyphicon")[0];
						if ($(element).data('isDatepicker')) {
							feedback = $(feedback).insertAfter(element.parent('div'));
						}

						var $feedback = $(feedback);
						$feedback.css('line-height', $feedback.height() + 'px');
						if ($(element).data('isSelect2')) {
							$feedback.css({
								'z-index': 100,
								'right': 0
							});
						}
						if ($(element).data('isDatepicker')) {
							$feedback.css({
								'background-color': 'white',
								'border-left': '1px solid ' + tipColor
							});
							var dateToggle = $(element).next();
							dateToggle.find('button').css({
								'color': tipColor,
								'border': '1px solid ' + tipColor
							});
						}
						// if($(element).data("isJqfileupload2")){
						// 	//文件上传，修改对应的样式
						// 	$feedback.css({
						// 		'background-color': 'white',
						// 		'border-left': '1px solid ' + tipColor
						// 	});
						// 	var dateToggle = $(element).next();
						// 	dateToggle.find('button').css({
						// 		'color': tipColor,
						// 		'border': '1px solid ' + tipColor
						// 	});
						// }
					}
				},
				highlight: function(element, errorClass, validClass) {
					if ($(element).data('isDatepicker')) {
						$(element).parent("div").parent("div").addClass("has-error").removeClass("has-success");
						$(element).parent("div").next("span.glyphicon").addClass("glyphicon-remove").removeClass("glyphicon-ok");
					} else if ($(element).data('isICheck')) {

					} else if($(element).data("isJqfileupload2")){
						var $element = $(element).parent();
						$element.parent("div").addClass("has-error").removeClass("has-success");
						$element.next("span.glyphicon").addClass("glyphicon-remove").removeClass("glyphicon-ok");
					}else {
						$(element).parent("div").addClass("has-error").removeClass("has-success");
						$(element).next("span.glyphicon").addClass("glyphicon-remove").removeClass("glyphicon-ok");
					}
				},
				unhighlight: function(element, errorClass, validClass) {
					if ($(element).data("mtValidate")) {
						if ($(element).data('isDatepicker')) {
							$(element).parent("div").parent("div").addClass("has-success").removeClass("has-error");
							$(element).parent("div").next("span.glyphicon").addClass("glyphicon-ok").removeClass("glyphicon-remove");
							var feedback = $(element).parent("div").next("span.glyphicon");
							if (feedback) {
								var tipColor = $(feedback).css('color');
								$(feedback).css({
									'background-color': 'white',
									'border-left': '1px solid ' + tipColor
								});
								var dateToggle = $(element).next();
								dateToggle.find('button').css({
									'color': tipColor,
									'border': '1px solid ' + tipColor
								});
							}
						} else if ($(element).data('isICheck')) {

						}  else if($(element).data("isJqfileupload2")){
							var $element = $(element).parent();
							$element.parent("div").addClass("has-success").removeClass("has-error");
							$element.next("span.glyphicon").addClass("glyphicon-ok").removeClass("glyphicon-remove");
						}else {
							$(element).parent("div").addClass("has-success").removeClass("has-error");
							$(element).next("span.glyphicon").addClass("glyphicon-ok").removeClass("glyphicon-remove");
						}
					}
				}
			});

			var $this = $("[mtValidate=true]");
			var rules = {};
			$.each($this, function(i, v) {
				var $t = $(v),
					isBlur = $t.attr("valiBlur") == "true",
					methodName = $t.data('rule-method');
				if ($t.data('isICheck') && isBlur) {
					methodName = $t.attr('name');
					$('input[name=' + $t.attr('name') + ']').on('ifChanged', function(event) {
						$t.valid();
					});
				} else if($t.data('isJqfileupload2')){
					//文件上传时
					if(isBlur){
						$t.change(function(event) {
							$t.valid();
						});
					}
				}else {
					$t.attr('name', methodName);
				}
				if ($t.data('isDatepicker') && isBlur) {
					$t.change(function(event) {
						$t.valid();
					});
				}

				if ($t.data('isSelect2') && isBlur) {
					$t.change(function(event) {
						$t.valid();
					});
				}
				var message = $t.data("mt-vail-er-msg") || $t.data("mtValidate").tip;

				// if(!$t.data('isICheck')){
				setCustomRule($t, message);
				// }

			});
		}
	});
};
initPageValidate.prototype.customRule = function(k) {
	var mtValidate = k.data("mtValidate"),
		trigger = k.data("mtValiObj");
	// 执行表达式
	var execObj = pubsub._getInParams(
			mtValidate.execExpData,
			/* trigger.inlev */
			1),
		exec = pubsub
		.expConvert(mtValidate.execExp || "", execObj);
	if (exec != "") {
		var execBol = eval(exec);
		if (!execBol)
			return true;
	}
	// 验证表达式
	var eObj = pubsub._getInParams(
			mtValidate.expData, /* trigger.inlev */ 1),
		exp = pubsub
		.expConvert(mtValidate.exp || "",
			eObj);
	if (exp != "") {
		var expBol = eval(exp);
		if (expBol)
			return true;
		else
			return false;
	}
	return true;
};
// 初始化页面验证方法
function initPageValidate(ruleObj) {
	function bindValidateFunc($this, vali) {
		var types = vali.type.split(","),
			type;
		$.each(types, function(i, v) {
			switch (v) {
				case "validate_type_foucs": // 聚焦
					ruleObj.validate_type_foucs($this);
					break;
				case "validate_type_save":
					ruleObj.validate_type_save($this);
					break;
				default:
					break;
			}
		})
	}
	var parent = window.parent;
	// 如果不是 则默认获取父
	/*
	 * if (pageLevel != 1) mtValidate = parent.mtValidate;
	 */
	if (mtValidate && mtValidate !== "[]") {
		var pageValidate = JSON.parse(mtValidate),
			len = pageValidate.length,
			vali, triggers, trigger, tlen, $target,
			specialRule = {};
		for (var i = 0; i < len; i++) {
			vali = pageValidate[i];
			triggers = vali.trigger;
			tlen = triggers.length;
			for (var j = 0; j < tlen; j++) {
				trigger = triggers[j];
				// 暂时
				trigger.level = trigger.inlev = pageLevel;
				$target = pubsub.getJQObj(trigger, true);
				if (!$target)
					continue;
				var dataRole = $target.attr('data-role') || "";
				var ruleId = $target.attr('id');
				var cname = $target.attr("cname");
				if ($target) {
					if(dataRole == "gridbt"){
						specialRule[ruleId] || (specialRule[ruleId] = []);
						specialRule[ruleId].push({
							mtValidate : vali,
							dataRole : dataRole,
							ruleId : ruleId,
							"rule-method" : ruleId,
							mtValiObj : trigger,
							"attr-mtValidate" : "true",
							"attr-mtTitle" : cname
						});
						continue;
					}
					var ipts = $target.find('input');
					if (dataRole.indexOf('metroselect') != -1) {
						var select0 = $target.find('select')[0];
						var tempId = $target.attr('id');
						$target = $(select0);
						$target.attr('name', tempId);
						$target.data('isSelect2', true);
					} else if (dataRole.indexOf('icheck') != -1) {
						$target = $(ipts[0]);
						$target.data('isICheck', true);
					} else if (dataRole.indexOf('datepicker') != -1) {
						$target = $(ipts[0]);
						$target.data('isDatepicker', true);
					} else if (dataRole.indexOf("textareabt") != -1) {
						$target = $target.find("textarea");
						$target.data('isTextareabt', true);
					} else if (dataRole.indexOf("touchspin") != -1) {
						$target = $(ipts[0]);
						$target.data('isTouchspin', true);
					} else if (dataRole.indexOf('datetimepickerbt') != -1) {
						$target = $(ipts[0]);
						$target.data('isDatepicker', true);
					} else if (dataRole.indexOf('jqfileupload2') != -1){
						$target = $(ipts[0]);
						$target.data('isJqfileupload2', true);
					}

					$target.data("dataRole", dataRole);
					$target.data("ruleId", ruleId);
					$target.data("rule-method", ruleId);
					$target.data("mtValidate", vali).data("mtValiObj", trigger)
						.attr("mtValidate", "true").attr("mtTitle",
							cname);
					bindValidateFunc($target, vali);
				}
			}
		}
		for(var key in specialRule){
			var $trigger = $("#"+key),
				cRules = specialRule[key];
			$trigger.on("click",function(e){
				console.log(e);
				var $innerTarget = $(e.target),name = $innerTarget.attr("name");
				if(name){
					$.each(cRules,function(i,cRule){
						if(name == cRule.mtValiObj.inid && !$innerTarget.attr("mtValidate")){
							var trSpecial = $innerTarget.closest("td"),specalName;
							var elementSpecial = $("#"+cRule.ruleId).find("thead tr th");
							$.each(elementSpecial,function(i,v){
								if(v.getAttribute("tid") == trSpecial.attr("hid")){
									specalName = v.innerText;
								}
							});
							$innerTarget.data("dataRole", cRule.dataRole);
							$innerTarget.data("ruleId", cRule.ruleId);
							$innerTarget.data("rule-method", cRule.ruleId);
							$innerTarget.data("mtValidate", cRule.mtValidate).data("mtValiObj", cRule.mtValiObj)
								.attr("mtValidate", "true").attr("mtTitle",
										specalName);
							// TODO mtTitle 找到当前列的标题
							bindValidateFunc($innerTarget, cRule.mtValidate);
							setCustomRule($innerTarget, $innerTarget.data("mt-vail-er-msg") || $innerTarget.data("mtValidate").tip);
						}	
					})
				}
			})
			
		}
		ruleObj.bindEvent();
	}
}

// 注册事件
var pubsubobjects = {
	'default': function(e) {
		if ("changeByName" == e.eventType) {
			$('input[name=' + $(this).attr('name') + ']').on('change', function(event) {
				// 事件方法
				mtxx.e = event;
				pubsub.pub(e.pageEvent, "");
			});
		} else if ("listenVal" == e.eventType) {
			var ink = new MutationObserver(function(record) {
				pubsub.pub(e.pageEvent, "");
			});
			ink.observe($(this)[0], {
				'attributes': true,
				'characterData': true,
				'subtree': true,
				'attributeFilter': ['setvalue']
			});
			$(this).on("change", function(event) {
				// 事件方法
				mtxx.e = event;
				pubsub.pub(e.pageEvent, "");
			});
		}
		else if ("listenKey" == e.eventType) {
			var $this = this;
			$this.on("keyup", function(event) {
				// 事件方法
				mtxx.e = event;
		        pubsub.pub(e.pageEvent, "");
		        
			});
		}
		else if ("listenEnter" == e.eventType) {
			var $this = this;
			$this.on("keyup", function(event) {
				// 事件方法
		    mtxx.e = event;
		    var key = e.which;
		    if(event.keyCode == "13") {
			    pubsub.pub(e.pageEvent, "");
		    }
			
			});
		}
		else {
			if (e.eventType.indexOf('By') != -1) {
				var element = e.eventType.substr(e.eventType.indexOf('By') + 2);
				if ($(this).find(element).length > 0) {
					$(this).on(e.eventType.substr(0, e.eventType.indexOf('By')), function(event) {
						// 事件方法
						mtxx.e = event;
						pubsub.pub(e.pageEvent, "");
					});
				} else {
					$(this).on(e.eventType.substr(0, e.eventType.indexOf('By')), function(event) {
						// 事件方法
						mtxx.e = event;
						pubsub.pub(e.pageEvent, "");
					});
				}
			} else {
				$(this).on(e.eventType, function(event) {
					// 事件方法
					mtxx.e = event;
					pubsub.pub(e.pageEvent, "");
				});
			}
		}
	},
	ztree: function(e) { // ztree 事件
		var ztreeObj = $.fn.zTree.getZTreeObj(this.attr('id'));
		if (ztreeObj) {
			if (e.eventType == "clickEvent") {
				ztreeObj.setting.callback.onClick = function(event, treeId, treeNode) {
					pubsub.pub(e.pageEvent, "", treeId, treeNode);
				}
			} else if (e.eventType == "checkEvent") {
				ztreeObj.setting.callback.onCheck = function(event, treeId, treeNode) {
					pubsub.pub(e.pageEvent, "", treeId, treeNode);
				}
			} else if (e.eventType == "onLoadSuccess") {
				ztreeObj.setting.callback.onLoadSuccess = function(event, treeId, treeNode) {
					// 加载完事件
					pubsub.pub(e.pageEvent, "", treeId, treeNode);
				}
			}
		} else {
			var state = this.data("_bindEvent_");
			if (state) {
				state.push(e);
			} else {
				this.data("_bindEvent_", [e]);
			}
		}

	},
	/*
	 * 提示框初始化事件
	 */
	prompt: function(e){
		var jq = $("#" + this.attr("id"));
		var ct = jq.data("promptExt");
		if (e.eventType == "inithind") {
		if(ct && ct.options){
			jqCb.add(function() {
				pubsub.pub(e.pageEvent, "", ct.options);
			});
		 }			
		}
		
	},
	/*
	 * 弹出框初始化事件
	 */
	popover: function(e){
		var jq = $("#" + this.attr("id"));
		var ct = jq.data("popoverExt");
		if (e.eventType == "initpopover") {
			if(ct && ct.options){
				// 队列
				jqCb.add(function() {
					pubsub.pub(e.pageEvent, "", ct.options);
				});
			}			
		}
		
	},
	button: function(e) {
		var events = $(this).attr("t-events");
		if (events == 'true') { // 动态事件 =definedTable
			var targetEle = "[data-role] a[btn-id][t-events=true]",
				key = "event";
			var selector = "[data-role] a[btn-id="+$(this).attr("id")+"][t-events=true]";
			$(this).data(key, e);
			var eventType = e.eventType+"."+$(this).attr("id");

			var userAgentInfo = navigator.userAgent;  
			var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod");  
			var flag = false;  
			for (var v = 0; v < Agents.length; v++) {  
				if (userAgentInfo.indexOf(Agents[v]) > 0) { flag = true; break; }  
			}
			eventType = e.eventType;
			if(flag){
				//手机端时，使用手机端事件
				if(e.eventType == 'click' && window.mui){
					eventType = "tap"+"."+$(this).attr("id");
				}
			}
			
			$(document)./*
						 * off(e.eventType + "." + $(this).attr("id"),
						 * selectortargetEle).
						 */on(eventType, selector, function(event) {
				event.preventDefault();
				var btnId = $(this).attr("btn-id"),
					$btn = $("#" + btnId);
				if ($btn[0]) {
					var $tr = $(this).closest("tr"),
						rowIndex = $tr.attr("row-index"),
						$Ele = $(this).closest("[data-role]"),
						dataRole = $Ele.attr("data-role"),
						curRow;
					var eve = $btn.data(key);
					if (dataRole == "gridbt") {
						curRow = $Ele.data("grid").getData()[rowIndex];
					}
					if (eve) {
						mtxx.e = event;
						pubsub.pub(eve.pageEvent, "", undefined, curRow);
					}
				}
			});

		} else {
			pubsubobjects["default"].call(this, e);
		}
	},
	page: function(e) {
		if (e.eventType == "pageInit") {
			jqCb.add(function() {
				pubsub.pub(e.pageEvent);
			});
		} else if (e.eventType == "mtSubmit") {
			$(document.body).data("mtSubmit", function() {
				pubsub.pub(e.pageEvent);
			});
		} else if (e.eventType == 'oncloseEvent') {
			var onbeforeunloadFunc = function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			}
			$('body').data("onbeforeunload", onbeforeunloadFunc);
			window.onbeforeunload=onbeforeunloadFunc;
			// window.onbeforeunload=;
		}else if(e.eventType == 'singleClick'){
			$('body').on('click',function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			});
		}else if(e.eventType == 'scanCallback'){
			$('body').data("scanCallback", function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			});
		}else if(e.eventType == 'wpfscanSavedEvents'){
			$('body').data("wpfscanSavedEvents", function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			});
		}
	},
	jsgis: function(e) {
		var jsgisEvent = function(e, eventName) {
			var $this = $("#" + e.trigger.eleId),
				ary = $this.data(eventName) || [];
			ary.push(function(kk) {
				var bol = false,
					isCurrentPoint = false; // 当前点是否是当前弹窗位置的点
				$.each(e.pageEvent.ops, function(i, v) {
					for (var p in v.param) {
						var dps = v.param[p];
						$.each(dps, function(i, v) {
							if ((v.param == "point" && v.columnName == kk.graphic.symbol.column) || v.eventType == 'newWindow')
								bol = true;
							if (v.param != "point" && v.columnName == kk.graphic.symbol.column)
								isCurrentPoint = true;
						});
					}
				})
				if (bol)
					pubsub.pub(e.pageEvent, "", kk, isCurrentPoint);
			}) && $this.data(eventName, ary);
		}
		if (e.eventType == "jsgisInit") {
			mapCb.add(function() {
				pubsub.pub(e.pageEvent);
			});
		} else if (e.eventType == "clickEvent") { // 单击事件
			jsgisEvent(e, "clickEventList");
		} else if (e.eventType == "dbclick") { // 双击事件
			jsgisEvent(e, "dbclickList");
		} else if (e.eventType == "mouseover") { // 鼠标移入
			jsgisEvent(e, "mouseoverList");
		} else if (e.eventType == "mouseup") { // 鼠标移出
			jsgisEvent(e, "mouseupList");
		} else if(e.eventType == "drawEnd"){ // 画图结束触发
			var events = this.data(e.eventType) || []; 
			events.push(function(parmas){
				pubsub.pub(e.pageEvent, "", parmas);
			}) && this.data(e.eventType, events);
		}
	},
	definedTable: function(e) {
		var jq = $("#" + this.attr("id"));
		if (e.eventType == "loadEndExecOne") {
			jq.definedTable('loadEndExecOne', function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			})
		}
		if (e.eventType == "loadEnd") {
			jq.definedTable('loadEnd', function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			})
		}
		if(e.eventType == "onClickTable"){
			jq.definedTable('onClickTable', function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			})	
		}
	},
	calendarbt: function(e) {
		var jq = $("#" + this.attr("id"));
		if (e.eventType == "unselect") {
			jq.find("div.has-toolbar").data("fullCalendar").options.unselect = function(event, view, jsEvent) {
				pubsub.pub(e.pageEvent, "", view, jsEvent);
			};
		} else if (e.eventType == "eventClick") {
			jq.find("div.has-toolbar").data("fullCalendar").options.eventClick = function(calEvent, jsEvent, view) {
				pubsub.pub(e.pageEvent, "", calEvent, jsEvent, view);
			};
		} else if (e.eventType == "dayClick") {
			jq.find("div.has-toolbar").data("fullCalendar").options.dayClick = function(date, allDay, jsEvent) {
				pubsub.pub(e.pageEvent, "", date, allDay, jsEvent);
			};
		}
	},
	megamenu: function(e) {
		var $this = $("#" + this.attr("id"));
		var selectcolor = $this.attr("selectcolor");
		var selectColor = $this.megaMenuExt("getSelectColor", selectcolor);
		$this.on('click', 'ul.page-sidebar-menu-mt a:not(".nav-toggle"),ul.nav.navbar-nav a:not(".nav-toggle")', function() {
			if ($this.data("select")) {
				$this.data("select").removeClass(selectColor);
				$this.data("select").parents("li").find(">a").removeClass(selectColor);
			}
			$(this).addClass(selectColor);
			$(this).parents("li").find(">a").addClass(selectColor);
			$this.data("select", $(this));
			pubsub.pub(e.pageEvent, "", $(this).closest("li").data("mObj"));
		});
	},
	login_actions: function(e) {
		var jq = $("#" + this.attr("id"));
		if (e.eventType == "click") {
			jq.on("click", 'button[frame-variable="loginBtn"]', function(event) {
				pubsub.pub(e.pageEvent, "", jq);
			});
		}
	},
	login_username: function(e) {
		var jq = $("#" + this.attr("id"));
		if (e.eventType == "keypress") {
			jq.on("keypress", 'input[frame-variable="username"]', function(event) {
				if (event.which == 13) {
					pubsub.pub(e.pageEvent, "");
				}
			});
		}
		if (e.eventType == "listenVal") {
			jq.on("change", 'input[frame-variable="username"]', function(event) {
					pubsub.pub(e.pageEvent, "");
			});
			
		}
		if (e.eventType == "listenEnter") {
			jq.on("keyup", 'input[frame-variable="username"]', function(event) {
				if(event.keyCode == "13") {
				    pubsub.pub(e.pageEvent, "");
			    }
			});
		
		}
	},
	login_password: function(e) {
		var jq = $("#" + this.attr("id"));
		if (e.eventType == "keypress") {
			jq.on("keypress", 'input[frame-variable="password"]', function(event) {
				if (event.which == 13) {
					pubsub.pub(e.pageEvent, "");
				}
			});
			
		}
		if (e.eventType == "listenVal") {
			jq.on("change", 'input[frame-variable="password"]', function(event) {
					pubsub.pub(e.pageEvent, "");
			});
			
		}
		if (e.eventType == "listenEnter") {
			jq.on("keyup", 'input[frame-variable="password"]', function(event) {
				if(event.keyCode == "13") {
				    pubsub.pub(e.pageEvent, "");
			    }
			});
		
		}
	},
	login_verify: function(e) {
		var jq = $("#" + this.attr("id"));
		if (e.eventType == "keypress") {
			jq.on("keypress", 'input[frame-variable="verification"]', function(event) {
				if (event.which == 13) {
					pubsub.pub(e.pageEvent, "");
				}
			});
		}
		if (e.eventType == "listenKey") {
			jq.on("keyup", 'input[frame-variable="verification"]',function(event) {
				// 事件方法
				mtxx.e = event;
		        pubsub.pub(e.pageEvent, "");
		        
			});
		}
		if (e.eventType == "listenEnter") {
			jq.on("keyup", 'input[frame-variable="verification"]', function(event) {
				if(event.keyCode == "13") {
				    pubsub.pub(e.pageEvent, "");
			    }
			});
		
		}
	},
	__gridbt__: function(e) {
		var id = this.attr("id"),
			$this = $("#" + id);

		if (e.eventType == "listenVal" || e.eventType == "change") {
			$this.on("change", "[name=" + e.trigger.eleId + "]", function(event) {
				// 事件方法
				mtxx.e = event;
				pubsub.pub(e.pageEvent, "");
			});
		} else if (e.eventType == "click") {
			$this.on("click", "[name=" + e.trigger.eleId + "]", function(event) {
				// 事件方法
				mtxx.e = event;
				pubsub.pub(e.pageEvent, "");
			});
		} else if (e.eventType == "loadSucess") {
			var opts = $this.metroselect("getOptions");
			opts.events.onLoadSucess = function(data) {
				pubsub.pub(e.pageEvent, "", data);
			}
		} else if (e.eventType == "clickNode") {
			$this.on('ifClicked', "[name=" + e.trigger.eleId + "]", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		} else if (e.eventType == "nodeChange") {
			$this.on('ifChanged', "[name=" + e.trigger.eleId + "]", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		} else if (e.eventType == "nodeChecked") {
			$this.on('ifChecked', "[name=" + e.trigger.eleId + "]", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		} else if (e.eventType == "nodeUnchecked") {
			$this.on('ifUnchecked', "[name=" + e.trigger.eleId + "]", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		} else if (e.eventType == "dblclick") {
			$this.on("dblclick", "[name=" + e.trigger.eleId + "]", function(event) {
				// 事件方法
				mtxx.e = event;
				pubsub.pub(e.pageEvent, "");
			});
		}
	},
	gridbt: function(e) {
		var id = this.attr("id"),
			$this = $("#" + id);
		if (e.trigger.otype) {
			pubsubobjects.__gridbt__.call(this, e);
			// if (e.eventType == "listenVal" || e.eventType == "change") {
			// $(this).on("change", "[name=" + e.trigger.eleId + "]",
			// function(event) {
			// // 事件方法
			// mtxx.e = event;
			// pubsub.pub(e.pageEvent, "");
			// });
			// }
			return;
		}
		if (e.eventType == "gchange") {

			var ct = $this.data("grid");
			if (ct && ct.options) {
				ct.options.events.onClickRow = function(index, row) {
					pubsub.pub(e.pageEvent, "", index, row);
				}
			}
		} else if (e.eventType == "loadEnd") {
			var ct = $this.data("grid");
			if (ct && ct.options) {
				ct.options.events.onLoadSuccess = function(data) {
					pubsub.pub(e.pageEvent, "", data);
				}
			}
		} else if (e.eventType == "dbclick") {
			var ct = $this.data("grid");
			if (ct && ct.options) {
				ct.options.events.onDblClickRow = function(index, row) {
					pubsub.pub(e.pageEvent, "", index, row);
				}
			}
		} else if (e.eventType == 'beginEditEvent') {
			var ct = $this.data("grid");
			if (ct && ct.options) {
				ct.options.events.beginEditEvent = function(o) {
					pubsub.pub(e.pageEvent, "", o);
				}
			}
		} else if (e.eventType == 'onCheckRow') {
			var ct = $this.data("grid");
			if (ct && ct.options) {
				ct.options.events.onCheckRow = function(index, row) {
					pubsub.pub(e.pageEvent, "", index, row);
				}
			}
		}
		else if (e.eventType == 'onclick') {
			var ct = $this.data("grid");
			if (ct && ct.options) {
				ct.options.events.onClickCell = function(index, field, value) {
					pubsub.pub(e.pageEvent, "",index,field,value,this);
				}
			}
		}else if(e.eventType == "justClick"){
			$this.on('click',function(event){
				if($(event.target).is('div')){
					pubsub.pub(e.pageEvent, "", event);
				}
			});
		}else if(e.eventType == 'beforeEndEdit'){
			var ct = $this.data("grid");
			if (ct && ct.options) {
				ct.options.events.onBeforeEndEdit = function() {
					pubsub.pub(e.pageEvent, "");
				}
			}
		}
		else if(e.eventType == 'updateButtonClick'){
			var ct = $this.data("grid");
			if (ct && ct.options) {
				ct.options.events.onUpdateButtonClick = function() {
						pubsub.pub(e.pageEvent, "", event);
				
				}
			}		
		}
	},
	gridlist: function(e) {
		var id = this.attr("id"),
			$this = $("#" + id);
		if (e.eventType == "clickEvent") {
			var ct = $this.data("gridList");
			if (ct && ct.options) {
				ct.options.callback.OnContentClick = function(data) {
					pubsub.pub(e.pageEvent, "", data);
				}
			}
		}
		if (e.eventType == "dblClickEvent") {
			var ct = $this.data("gridList");
			if (ct && ct.options) {
				ct.options.callback.OnContentDblClick = function(data) {
					pubsub.pub(e.pageEvent, "", data);
				}
			}
		}
		if (e.eventType == "droppend") {
			var ct = $this.data("gridList");
			if (ct && ct.options) {
				ct.options.callback.onDroppEnd = function(data) {
					pubsub.pub(e.pageEvent, "", data);
				}
			}
		}
		if (e.eventType == "drapStart") {
			var ct = $this.data("gridList");
			if (ct && ct.options) {
				ct.options.callback.onDrapStart = function(data) {
					pubsub.pub(e.pageEvent, "", data);
				}
			}
		}
		if (e.eventType == "draopOver") {
			var ct = $this.data("gridList");
			if (ct && ct.options) {
				ct.options.callback.onDroppOver = function(data) {
					pubsub.pub(e.pageEvent, "", data);
				}
			}
		}
		if (e.eventType == "onloadSuccess") {
			var ct = $this.data("gridList");
			if (ct && ct.options) {
				ct.options.callback.OnLoadSuccess = function(data) {
					pubsub.pub(e.pageEvent, "", data);
				}
			}
		}
	},
	gridList: function(e) {
		pubsubobjects.gridlist.call(this, e);
		// var id = this.attr("id"),
		// $this = $("#" + id);
		// if (e.eventType == "clickEvent") {
		// var ct = $this.data("gridList");
		// if (ct && ct.options) {
		// ct.options.callback.OnContentClick = function(data) {
		// pubsub.pub(e.pageEvent, "", data);
		// }
		// }
		// }
		// if (e.eventType == "droppend") {
		// var ct = $this.data("gridList");
		// if (ct && ct.options) {
		// ct.options.callback.onDroppEnd = function(data) {
		// pubsub.pub(e.pageEvent, "", data);
		// }
		// }
		// }
		// if (e.eventType == "drapStart") {
		// var ct = $this.data("gridList");
		// if (ct && ct.options) {
		// ct.options.callback.onDrapStart = function(data) {
		// pubsub.pub(e.pageEvent, "", data);
		// }
		// }
		// }
		// if (e.eventType == "draopOver") {
		// var ct = $this.data("gridList");
		// if (ct && ct.options) {
		// ct.options.callback.onDroppOver = function(data) {
		// pubsub.pub(e.pageEvent, "", data);
		// }
		// }
		// }
	},
	treelistbt: function(e) {
		var id = this.attr("id"),
			$this = $("#" + id);
		if (e.trigger.otype) {
			pubsubobjects.__gridbt__(e);
			// if (e.eventType == "listenVal" || e.eventType == "change") {
			// $(this).on("change", "[name=" + e.trigger.eleId + "]",
			// function(event) {
			// // 事件方法
			// mtxx.e = event;
			// pubsub.pub(e.pageEvent, "");
			// });
			// }
			return;
		}
		if (e.eventType == "gchange") {
			var ct = $this.data("treelist");
			if (ct && ct.options) {
				ct.options.events.onClickRow = function(index, row) {
					pubsub.pub(e.pageEvent, "", index, row);
				}
			}else if($this[0]){
				$this.data("__onClickRow__",function(index, row) {
					pubsub.pub(e.pageEvent, "", index, row);
				});
			}
		} else if (e.eventType == "loadEnd") {
			var ct = $this.data("treelist");
			if (ct && ct.options) {
				ct.options.events.onLoadSuccess = function(data) {
					pubsub.pub(e.pageEvent, "", data);
				}
			}else if($this[0]){
				$this.data("__onLoadSuccess__",function(data) {
					pubsub.pub(e.pageEvent, "", data);
				});
			}
		} else if (e.eventType == "dbclick") {
			var ct = $this.data("treelist");
			if (ct && ct.options) {
				ct.options.events.onDblClickRow = function(index, row) {
					pubsub.pub(e.pageEvent, "", index, row);
				}
			}else if($this[0]){
				$this.data("__onDblClickRow__",function(index, row) {
					pubsub.pub(e.pageEvent, "", index, row);
				});
			}
		} else if (e.eventType == 'onCheckRow') {
			var ct = $this.data("treelist");
			if (ct && ct.options) {
				ct.options.events.onCheckRow = function(index, row) {
					pubsub.pub(e.pageEvent, "", index, row);
				}
			}else if($this[0]){
				$this.data("__onCheckRow__",function(index, row) {
					pubsub.pub(e.pageEvent, "", index, row);
				});
			}
		}
	},
	cell: function(e) {
		$this = this;
		var eventsRule = $this.data("eventsRule") || {};
		eventsRule[e.trigger.eleId] = function(ex) {
			// console.log(ex);
			pubsub.pub(e.pageEvent, "", ex);
		}
		$this.data("eventsRule", eventsRule);
	},
	charts: function(e) {
		var $this = this;
		if (e.eventType == "clickEvent") {
			$this.data("clickEvent", function(ex) {
				pubsub.pub(e.pageEvent, "", ex, this);
			});
		}
	},
	mapext: function(e){
		var id = this.attr("id"),
			$this = $("#" + id);
		if (e.eventType == "clickNode") {
			var ct = $this.data("echartsmapExt");
			if (ct && ct.options) {
				ct.options.events.clickNode = function(data) {
					pubsub.pub(e.pageEvent, "", data);
				}
			}
		}
	},
	diagrambt: function(e) {
		var $this = this;
		var isdblclick = false;
		$this.isdblclick = false;
		if (e.eventType == "clickNode") {
			$this.on('click', '.orgchart .node', function(event) {
				$this.data("isdblclick",false);
				setTimeout(function() {
					if(!$this.data("isdblclick")){
						pubsub.pub(e.pageEvent, "", event, this);
					}
				},250);
			});
		}
		if (e.eventType == "dblclickNode") {
			$this.on('dblclick', '.orgchart .node', function(event) {
				$this.data("isdblclick",true);
				pubsub.pub(e.pageEvent, "", event, this);
			});
		}
	},
	gantt: function(e) {
		var id = this.attr("id"),
			$this = $("#" + id);
		if (e.eventType == "gchange") {
			$this.bind("gantt-click", function(ex, index, row) {
				pubsub.pub(e.pageEvent, "", index, row);
			});
			$this.iframegantt('initEvents', {
				events: {
					onClickRow: function(index, row) {
						$this.trigger('gantt-click', [index, row]);
					},
					onDbClickRow: function(index, row) {
						$this.trigger('gantt-click', [index, row]);
					}
				}
			});

		}
	},
	step: function(e) {
		var $this = this;
		if (e.eventType == "clickStep") {
			$this.on('click', 'div.mt-step-col', function(event) {
				var data = $(this).data("step");
				pubsub.pub(e.pageEvent, "", data);
			});
		}
	},
	icheckradio: function(e) {
		var $this = this;
		if (e.eventType == "clickNode") {
			$this.on('ifClicked', "input", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		} else if (e.eventType == "nodeChange") {
			$this.on('ifChanged', "input", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		} else if (e.eventType == "nodeChecked") {
			$this.on('ifChecked', "input", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		} else if (e.eventType == "nodeUnchecked") {
			$this.on('ifUnchecked', "input", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		}
	},
	icheckbox: function(e) {
		var $this = this;
		var count = 0;
		if (e.eventType == "clickNode") {
			$this.on('ifClicked', "input", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		} else if (e.eventType == "nodeChange") {
			$this.on('ifChanged', "input", function(event) {
				if(!$(this).attr("isCheckAll")){
					pubsub.pub(e.pageEvent, "", event);
				}
			});
		} else if (e.eventType == "nodeChecked") {
			$this.on('ifChecked', "input", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		} else if (e.eventType == "nodeUnchecked") {
			$this.on('ifUnchecked', "input", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		}
	},
	excelupload: function(e) {
		var $this = this;
		if (e.eventType == "onUploadSucess") {
			var opts = $this.data("excelFileUpload").opts;
			opts.onUploadSucess = function(data) {
				pubsub.pub(e.pageEvent, "", data);
			}
		}

	},
	metroselect: function(e) {
		var $this = this;
		if (e.eventType == "loadSucess") {
			var opts = $this.metroselect("getOptions");
			opts.events.onLoadSucess = function(data) {
				pubsub.pub(e.pageEvent, "", data);
			}
		} else {
			$(this).on(e.eventType, function(event) {
				// 事件方法
				mtxx.e = event;
				pubsub.pub(e.pageEvent, "");
			});
		}
	},
	metroselect_m: function(e) {
		var $this = this;
		if (e.eventType == "loadSucess") {
			var opts = $this.metroselect_m("getOptions");
			opts.events.onLoadSucess = function(data) {
				pubsub.pub(e.pageEvent, "", data);
			}
		} else {
			$(this).on(e.eventType, function(event) {
				// 事件方法
				mtxx.e = event;
				pubsub.pub(e.pageEvent, "");
			});
		}
	},
	customSelect: function(e) {
		var $this = this;
		if (e.eventType == "loadSucess") {
			var that = $this.data("customSelect");
			that.option.events.onLoadSuccess = function(data) {
				pubsub.pub(e.pageEvent, "", data);
			}
		} else {
			$(this).on(e.eventType, function(event) {
				// 事件方法
				mtxx.e = event;
				pubsub.pub(e.pageEvent, "");
			});
		}
	},
	searchbar : function(e){
		var $this = this;
		if ("listenVal" == e.eventType) {
			var ink = new MutationObserver(function(record) {
				pubsub.pub(e.pageEvent, "");
			});
			ink.observe($(this)[0], {
				'attributes': true,
				'characterData': true,
				'subtree': true,
				'attributeFilter': ['setvalue']
			});
			$(this).on("change", function(event) {
				// 事件方法
				mtxx.e = event;
				pubsub.pub(e.pageEvent, "");
			});
		}
		else if ("listenKey" == e.eventType) {
			$this.on("keyup", function(event) {
				// 事件方法
				mtxx.e = event;
		        pubsub.pub(e.pageEvent, "");
		        
			});
		}
		else if ("listenEnter" == e.eventType) {
			$this.on("keyup", function(event) {
				// 事件方法
		    mtxx.e = event;
		    var key = e.which;
		    if(event.keyCode == "13") {
			    pubsub.pub(e.pageEvent, "");
		    }
			
			});
		} else if ("dblclick" == e.eventType) {
			$this.on("dbclick", function(event) {
				mtxx.e = event;
				pubsub.pub(e.pageEvent, "");
			});
		} else if ("click" == e.eventType) {
			$this.on("click", function(event) {
				mtxx.e = event;
				pubsub.pub(e.pageEvent, "");
			});
		}
	},
	jqfileupload2: function(e) {
		var $this = this;
		if (e.eventType == "uploadSucess") {
			var pluginData = $this.data("spFileUpload");
			if (pluginData && pluginData.opts) {
				var opts = pluginData.opts;
				opts.events = opts.events || {};
				opts.events.onUploadSuccess = function(data) {
					pubsub.pub(e.pageEvent, "", data);
				}
			}
		}
	},

	bootstrap_tabs: function(e) {
		var $this = this;
		if (e.eventType == "activeEvent") {
			$this.find('>.nav-tabs a[data-toggle="tab"],>.row>.tabnav>.nav-tabs a[data-toggle="tab"]').on('shown.bs.tab', function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			});
		}
	}

	,
	bim: function(e) {
		var $this = this,
			events = {
				BimClick: function(event) { // 节点单击事件
					var opts = $this.data("bim");
					opts.onSelect = function(msg) {
						try {
							var client_method = $this.data("client_method");
							if (!client_method) {
								var id = msg.dbIdArray[0];
								if (id != null) {
									var node = $this.bim("GetNodeById", id);
									pubsub.pub(e.pageEvent, "", $.extend(true, node, {
										_documentId: opts._documentId
									}));
								} else {

								}
							}
						} catch (ex) {
							throw ex;
						} finally {
							$this.data("client_method", false);
						}
					};
				}
			};
		events[e.eventType] && (events[e.eventType]());
	},
	reviewArea : function(e){
		var $this = this;
		if (e.eventType == "reply") {
			var ct = $this.data("reviewArea");
			if (ct && ct.option) {
				ct.option.event.reply = function(event) {
					pubsub.pub(e.pageEvent, "");
				}
			}
		}
	},
	modelbim : function(e){
		var $this = this;
		if(e.eventType == "BimClick"){
			var ct = $this.data("modelbim");
			if (ct && ct.option) {
				ct.option.event.BimClick = function(event) {
					pubsub.pub(e.pageEvent, "",event);
				}
			}
		}
		
	},
	echarts : function(e){
		var $this = this;
		if(e.eventType == "click"){
			var ct = $this.data("echarts");
			if (ct && ct.options) {
				ct.options.events.EchartClick = function(item) {
					pubsub.pub(e.pageEvent, "",item);
				}
			}
		}
		
	},
	selectpicker : function(e){
		var $this = this;
		if(e.eventType == "change"){
			var ct = $this.data("selectpicker");
			if (ct && ct.option) {
				ct.option.events.change = function(item) {
					pubsub.pub(e.pageEvent, "",item);
				}
			}
		}
		else if(e.eventType == "click"){
			var ct = $this.data("selectpicker");
			if (ct && ct.option) {
				ct.option.events.click = function(item) {
					pubsub.pub(e.pageEvent, "",item);
				}
			}
		}
	},
	definedcard: function(e) {
		var $this = this;
		if (e.eventType == "gchange") {
			var ct = $this.data("definedCardExt");
			if (ct && ct.options) {
				ct.options.events.onClickRow = function(index, row) {
					pubsub.pub(e.pageEvent, "", index);
				}
			}
		}
	},
	switch: function(e) {
		var $this = this;
		if (e.eventType == "gchange") {
			$this.find("input[type='checkbox']").bootstrapSwitch("onSwitchChange", function(event, state) {
				pubsub.pub(e.pageEvent, "", state);
			})
		}
	},
	metrolist: function(e) {
		var $this = this;
		if (e.eventType == 'droppend') {
			var ct = $this.data("metrolist");
			if (ct && ct.options) {
				ct.options.onDroppEnd = function() {
					pubsub.pub(e.pageEvent, "", null);
				}
			}
		}
	},
	rangeSlider:function(e){
		var id = this.attr("id"),
		$this = $("#" + id);
		if(e.eventType == 'dragBar'){
			var rs = $this.data("rangeSlider");
			if(rs){
				rs.options.onChan = function(){
					pubsub.pub(e.pageEvent, "", null);
				}
			}
		}
	},
	
	fullpage:function(e){
		var id = this.attr("id"),
		$this = $("#" + id);
		if(e.eventType == 'afterLoad'){
			var rs = $this.data("fullpage");
			if(rs){
				rs.options.afl = function(){
					pubsub.pub(e.pageEvent, "", null);
				}
			}
		}else if(e.eventType=='afterSlideload'){
			var rs = $this.data("fullpage");
			if(rs){
				rs.options.asl = function(){
					pubsub.pub(e.pageEvent, "", null);
				}
			}
		}
	},
	nestable : function(e){
		var $this = this;
		// 拖入事件
		if (e.eventType == "dropNestEvent") {
			var build = $this.data("nestable");
			if(build){
				build.dragStopEvent = function(param){
					pubsub.pub(e.pageEvent,"");
				};
			}
			
		}	
		if (e.eventType == "dropStartEvent") {
			var build = $this.data("nestable");
			if(build){
				build.dragStartEvent = function(param){
					pubsub.pub(e.pageEvent,"");
				};
			}
			
		}		
	},
	definedpanel : function(e){
		var $this = this;
		if(e.eventType == 'onClickEvent'){
			var ct = $this.data("definedPanelExt");
			if (ct && ct.options) {
				ct.options.events.onClickEvent = function(index, row) {
					pubsub.pub(e.pageEvent, "", index);
				}
			}
		}
	},
	custom : function(e){
		var $this = this;
		if(e.eventType == 'onLoadSucess'){
			$this.data("onLoadSucess",function(event){
				pubsub.pub(e.pageEvent, "", event);
			})
		}
	},
	officebt : function(e){
		
		/**
		 * 数据装载完事件
		 */
		if(e.eventType == 'onAppendRowsByBookMarks'){
			var $target = $(this);
			if (!$target.get(0)) {
				return;
			}
			var state = $target.data("office.options");
			state.options.events.onAppendRowsByBookMarks = function(events) {
				pubsub.pub(e.pageEvent, "", events);
			};
		}
	},
	
	office : function(e){
		return pubsubobjects.officebt.apply(this,//
				Array.prototype.slice.call(arguments, 0))
	},

	bmapext : function(e){
		var $this = this;
		if(e.eventType == 'initEnd'){
			var ct = $this.data("bmapext");
			if (ct && ct.options) {
				ct.options.events.initEnd = function(index, row) {
					pubsub.pub(e.pageEvent, "", index);
				}
			}
		}else if(e.eventType == 'onSelectSearch'){
			var ct = $this.data("bmapext");
			if (ct && ct.options) {
				ct.options.events.onSelectSearch = function(index, row) {
					pubsub.pub(e.pageEvent, "", index);
				}
			}
		}
	},

	bmapddext : function(e){
		var $this = this;
		if(e.eventType == 'initEnd'){
			var ct = $this.data("bmapddext");
			if (ct && ct.options) {
				ct.options.events.initEnd = function(index, row) {
					pubsub.pub(e.pageEvent, "", index);
				}
			}
		}else if(e.eventType == 'onLocationChange'){
			var ct = $this.data("bmapddext");
			if (ct && ct.options) {
				ct.options.events.onLocationChange = function(index, row) {
					pubsub.pub(e.pageEvent, "", index);
				}
			}
		}
	},

	bmapmarker : function(e){
		var $this = this;
		if(e.eventType == 'onClickMarker'){
			var ct = $this.data("bmapmarker");
			if (ct && ct.options) {
				ct.options.events.onClickMarker = function(index, row) {
					pubsub.pub(e.pageEvent, "", index);
				}
			}
		}
	},

	dhxgantt : function(e){
		var $this = this;
		if(e.eventType == 'gchange'){
			var ct = $this.data("dhxgantt");
			if (ct && ct.options) {
				ct.options.events.onTaskSelected = function(index, row) {
					pubsub.pub(e.pageEvent, "", index);
				}
			}
		}
		if(e.eventType == 'onDblClickRow'){
			var ct = $this.data("dhxgantt");
			if (ct && ct.options) {
				ct.options.events.onDblClickRow = function(index, row) {
					pubsub.pub(e.pageEvent, "", index);
				}
			}
		}
		
	},
	levellist : function(e){
		var $this = this;
		if(e.eventType == 'switchChange'){
			var ct = $this.data("levellistext");
			if (ct && ct.options) {
				ct.options.events.switchChange = function(index, row) {
					pubsub.pub(e.pageEvent, "", index);
				}
			}
		}
		if(e.eventType == 'onSelectedEndLevel'){
			var ct = $this.data("levellistext");
			if (ct && ct.options) {
				ct.options.events.onSelectedEndLevel = function(index, row) {
					pubsub.pub(e.pageEvent, "", index);
				}
			}
		}
	},
	foldlist : function(e){
		var $this = this;
		if(e.eventType == 'switchChange'){
			var ct = $this.data("foldlistext");
			if (ct && ct.options) {
				ct.options.events.switchChange = function(index, row) {
					pubsub.pub(e.pageEvent, "", index);
				}
			}
		}
		if(e.eventType == 'onSelected'){
			var ct = $this.data("foldlistext");
			if (ct && ct.options) {
				ct.options.events.onSelected = function(index, row) {
					pubsub.pub(e.pageEvent, "", index);
				}
			}
		}
	},
	mswitch : function(e){
		var $this = this;
		if(e.eventType == 'switchChange'){
			var ct = $this.data("mswitchext");
			if (ct && ct.options) {
				ct.options.events.switchChange = function(index, row) {
					pubsub.pub(e.pageEvent, "", index);
				}
			}
		}
	},
	tabbar : function(e){
		var $this = this;
		if(e.eventType == "tabClick"){
			var ct = $this.data("tabbar");
			if (ct && ct.option) {
				ct.option.events.tabClick = function() {
					pubsub.pub(e.pageEvent, "");
				}
			}
		}
	},
	mdatepicker : function(e){
		var $this = this;
		if(e.eventType == "changeByinput"){
			var ct = $this.data("mdatepicker");
			if (ct && ct.option) {
				ct.option.events.changeByinput = function() {
					pubsub.pub(e.pageEvent, "");
				}
			}
		}
	},
	vectorDraw : function(e){
		var $this = this;
		if(e.eventType == "click"){
			var ct = $this.data("vectorDrawOpt");
			if (ct && ct.option) {
				ct.option.events.click = function(event) {
					pubsub.pub(e.pageEvent,"", event);
				}
			}
		}
		else if(e.eventType == "mouseOverSpot"){
			var ct = $this.data("vectorDrawOpt");
			if (ct && ct.option) {
				ct.option.events.mouseOverSpot = function(event) {
					pubsub.pub(e.pageEvent, "",event);
				}
			}
		}
	},
	medialist : function(e){
		var $this = this;
		if(e.eventType == "tap"){
			var ct = $this.data("medialist");
			if (ct && ct.option) {
				ct.option.events.tap = function(item) {
					pubsub.pub(e.pageEvent,"", item);
				}
			}
		}
		
	},
	rangecalendar : function(e){
		var $this = this;
		if(e.eventType == "onClickCell"){
			var ct = $this.data("rangecalendarext");
			if (ct && ct.options) {
				ct.options.events.onClickCell = function() {
					pubsub.pub(e.pageEvent, "");
				}
			}
		}
	},
	webchat : function(e){
		var $this = this;
		if(e.eventType == "connecteOnline"){
			var ct = $this.data("webchat");
			if (ct) {
				ct.events.connecteOnline = function() {
					pubsub.pub(e.pageEvent, "");
				}
			}
		}
	},
	imageupload : function(e){
		var $this = this;
		if(e.eventType == 'onUploadSuccess'){
			$this.data("onUploadSuccess",function(index, row) {
				pubsub.pub(e.pageEvent, "", index);
			})
		}
	}
};