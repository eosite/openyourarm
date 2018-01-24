function MyAjax(options) {
	var oldSuccess = options.success;
	options.success = function(msg) {
		if (oldSuccess && $.isFunction(oldSuccess)) {
			oldSuccess(msg);
		}
		var data = options.data;

		if (data) {
			if (typeof data === "string") {
				data = JSON.parse(data);
			}
			if (data.params && typeof data.params === 'string') {
				data.params = JSON.parse(data.params);
			}
		}

		if (typeof msg === "string") {
			msg = JSON.parse(msg);
		}

		var debuggerObj = [{
			tmplId: "operatorTmpl",
			name: '操作名称',
			value: options.operation
		}, {
			tmplId: "requestParamsTmpl",
			name: '请求参数',
			value: data.params || data
		}, {
			tmplId: "responseResultTmpl",
			name: '返回结果',
			value: msg
		}]

		if (msg && msg.statusCode == '500') {
			debuggerObj.push({
				tmplId: "responseStatusTmpl",
				name: '结果状态',
				value: false
			})
		} else {
			debuggerObj.push({
				tmplId: "responseStatusTmpl",
				name: '结果状态',
				value: true
			})
		}

		mtDebugger.pushQueue("info", 'operatorTmpl', debuggerObj);
	}
	$.ajax(options);
};

var pageBtFunc = {
	getValue: function(rule, args) {
		if (mtxx.params) {
			return mtxx.params[rule.inparam];
		}
		return null;
	},
	getRow: function(rule, args, obj) {
		//优先从缓存中获取 即配置了页面输入输出关系的
		// var targer = pubsub.getJQObj(rule);
		// var params = targer.data("params");
		// if (params && params.value) {
		// 	return params.value;
		// }
		//获取不到则从页面数据中获取
		var params = pageParameters,
			idatasetId = rule.idatasetId || rule.indatasetId,
			thatWin = /*pubsub.getThat(rule, true)*/ window;
		if (idatasetId) {
			var dataSources = params["detail"]["dataSources"];
			if (!$.isEmptyObject(dataSources) && dataSources[idatasetId]) {
				return dataSources[idatasetId][rule.columnName] || '';
			}
		} else {
			//输出参数为obj时候
			pubsub.outParamsObj(rule, obj, params, thatWin.callbackParam);
			var value =pubsub.inParamsObj(rule, thatWin.callbackParam) || params[rule.columnName] || (thatWin.callbackParam && thatWin.callbackParam[rule.columnName]);
			if(!value && value != 0){
				value = "";
			}

			return value;
		}
		return "";

	},
	/**
	 * [设置页面缓存参数]
	 */
	setValue: function(rule, params, args) {
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		thatWin.callbackParam || (thatWin.callbackParam = {});
		for (var p in params) {
			thatWin.callbackParam[p] = params[p];
		}
	},
	linkageControl: function(rule, params) {
		pageBtFunc.linkagePage(rule, params);
	},
	linkagePage: function(rule, params) {
		params.newWin = '1';
		var p = pubsub.paramsToStr(params),
			id = rule;
		if (typeof rule == 'object') {
			id = rule[0].outid;
		}
		$("iframe[src*='" + id + "'],iframe[prosrc*='" + id + "']").each(
			function(i) {
				$this = $(this);
				if (!$this.attr('prosrc')) {
					$this.attr('prosrc', $this.attr('src'));
				}
				$this.attr('src', $this.attr('prosrc') + p);
			});
	},
	getClientIp: function(rule, params) {
		var ruleOne;
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}

		$.ajax({
			url: contextPath + "/mx/form/page/getClientIp",
			type: "post",
			dataType: "json",
			async: true,
			data: params,
			success: function(ret) {
				if (ret) {
					//回调参数信息
					thatWin.callbackParam = thatWin.callbackParam || {};
					$.each(ruleOne.callback, function(i, k) {
						thatWin.callbackParam[k.param] = ret[k.columnName];
					})
				} else {
					$.each(ruleOne.callback, function(i, k) {
						thatWin.callbackParam[k.param] = "";
					})
				}
				if (ret) {
					pubsub.execChilds(rule);
				}
			},
			error: function(e) {
				console.log("服务器处理错误！,请修改后再试。");
			}
		})
	},
	newWindow: function(id, params, args, windowUrl) { // 弹出窗口
		// args[0]
		// 格式{link:{url:'xxxx',name:'图片链接',id:'xx'},model:true,title:'标题',jumpType:'singlePage',width:'200px',height:'100px'}
		var obj = args ? args[0] : null;
		if (obj && obj.jumpType) {} else {
			var parameters_ = params.parameters || "";
			if (parameters_) {
				delete params.parameters;
			}

			var p = pubsub.paramsToStr(params, ['headerFontColor','closeBtnSize','closeBtnColor','isMax', 'width', 'height', 'singlePage', //
				'model', 'title', 'mtOpenTab', 'TABNAME','contentPadding', 'headerPadding', 'backdrop', 'isParent', 'closeByBackdrop', 'render', 'opacity','hideHeader','borderRadius'
			]); // 参数
			if (windowUrl) {
				url = windowUrl;
				path = url;
			} else {
				url = id[0] ? id[0].srcUrl : id.srcUrl;
				path = contextPath + url + ((url.indexOf("?") > -1) ? "" : "?") + (p + (parameters_ ? ("&" + parameters_) : ""));
				if (window.location.href.indexOf("isPublish=true") != -1) {
					path += "&isPublish=true";
				}
			}

			var mtOpenTab = params.mtOpenTab === true ? true : false;
			if (mtOpenTab) { //选项卡打开
				params.path = path;
				pageBtFunc.mtOpenTab(id, params, args);
			} else if (params.render) {
				location.href = path;
			} else if (!params.singlePage) {
				window.open(path);
			} else {
				var iTop = (window.screen.availHeight - 30 - eval((params.height + "").replace('px', ''))) / 2; // 获得窗口的垂直位置;
				var iLeft = (window.screen.availWidth - 10 - eval((params.width + "").replace('px', ''))) / 2; // 获得窗口的水平位置;
				var width = eval(params.width) || 650;
				var height = eval(params.height) || 450;
				if (params.isMax) {
					width = $("body").width() - 24;
					height = $("body").height() - 93;
				}
				if (params.model) {
					window.open(path, params.title, "width=" + params.width + ",height=" + params.height + ",top=" + iTop + ",left=" + iLeft + ", modal=true,status=no,scrollbars=yes");
				} else if (params.isParent && top && top.$.fancybox) {
					top.$.fancybox.open({
						href: path,
						width: width,
						height: height,
						type: 'iframe',
						modal: false,
						closeClick: true,
						openEffect: 'none',
						padding: 5,
						autoSize: false,
						autoHeight: false,
						autoWidth: false,
						helpers: {
							title: (params.title == null ? "窗口" : params.title)
						}
					});
					//parent.$.fancybox.open("<iframe onload=\"$(this).contents().find('body').css('overflow-y', 'auto');\" width='100%' height='" + height + "' src='" + path + "' frameborder='no' scrolling='yes'></iframe>");
				} else {
					var dialog = BootstrapDialog.show({
						title: (params.title == null ? "窗口" : params.title),
						draggable: params.draggable === false ? false : true,
						message: function(dialog) {
							//dialog.$modal.removeAttr('tabindex');
							var pageToLoad = dialog.getData('pageToLoad');
							var frame
							if (windowUrl) {
								frame = "<iframe width='100%' border='0' marginwidth='0' marginheight='0' height='" + height + "' src='" + pageToLoad + "' frameborder='no' scrolling='yes'></iframe>";
							} else {
								frame = "<iframe border='0' marginwidth='0' marginheight='0' onload=\"$(this).contents().find('body').css('overflow-y', 'auto');\" width='100%' height='" + height + "' src='" + pageToLoad + "' frameborder='no' scrolling='yes'></iframe>";
							}
							return $(frame);
						},
						closeByBackdrop: params.closeByBackdrop === false ? false : true,
						data: {
							'pageToLoad': path
						},
						css: {
							width: width + 'px',
							height: height + 'px'
						},
						onhide: function(dialog) {
							var iframe = dialog.$modal.find("iframe:first")[0];
							if (iframe && !windowUrl) {
								var iframe_window = iframe.contentWindow;
								if (iframe_window && $(iframe_window.document.body)[0]) {
									var onbeforeunloadFunc = $(iframe_window.document.body).data("onbeforeunload");
									if ($.isFunction(onbeforeunloadFunc)) {
										onbeforeunloadFunc();
										return;
									}
								}
								/*var onbeforeunloadFunc = $(document.body).data("onbeforeunload");
								if ($.isFunction(onbeforeunloadFunc)) {
									onbeforeunloadFunc();
									return;
								}*/
							    if(iframe_window.onbeforeunload != undefined && iframe_window.onbeforeunload != null){
							    	iframe_window.onbeforeunload();
									return;
							    }
							}
						}
					});

					if (params.backdrop != null && !params.backdrop) {
						dialog.$modal.data('bs.modal').$backdrop.hide();
					}

					if (params.opacity != null) {
						dialog.getModalContent().css("opacity", params.opacity)
					}
					
					dialog.getModalBody().find(".bootstrap-dialog-message").css("line-height",0);
					dialog.getModalHeader().find(".bootstrap-dialog-header .bootstrap-dialog-close-button").remove();
					// dialog.getModalHeader().find(".bootstrap-dialog-header").append('<button type="button" class="btn closeBtn" style=""><i class="fa fa-remove"></i></button>');
					dialog.getModalContent().prepend('<button type="button" class="btn closeBtn" style=""><i class="fa fa-remove"></i></button>');

					// dialog.getModalDialog().css({"margin":"auto","top":"50%","margin-top":"-"+(height+34)/2+"px"});
					//去掉居中
					dialog.getModalDialog().css({"margin":"auto","margin-top":"180px"});
					if(params.hideHeader){
						dialog.getModalHeader().hide();
						dialog.getModalContent().find(".closeBtn").hide();
					}
					if(params.borderRadius){
						dialog.getModalContent().attr("style","border-radius:"+params.borderRadius+" !important;")
						dialog.getModalContent().css("border-style","none");
					}
					if(params.closeBtnColor){
						dialog.getModalContent().find(".closeBtn").css("color",params.closeBtnColor);
					}
					if(params.closeBtnSize){
						dialog.getModalContent().find(".closeBtn").css("font-size",params.closeBtnSize);	
					}

					dialog.getModalContent().find(".closeBtn").click(function(event){
						dialog.close();
					})

					dialog.getModalHeader().css("border-style","none");
					if (params.headerPadding != null) {
						var headerPaddingStr = params.headerPadding + 'px ' + params.headerPadding + 'px ' + params.headerPadding + 'px ' + params.headerPadding + 'px';
						var headerBackgroundColor = params.headerBackgroundColor || "";
						var headerFontSize = params.headerFontSize || "";
						dialog.getModalHeader().css({
							padding: headerPaddingStr,
							"background-color": headerBackgroundColor,
							// "font-size":headerFontSize
						});
						var headerFontColor = params.headerFontColor || "";
						dialog.getModalHeader().find(".bootstrap-dialog-title").css("color", headerFontColor);
						dialog.getModalHeader().find(".bootstrap-dialog-title").css("font-size", headerFontSize);

					}
					if(params.contentPadding != null){
						var headerPaddingStr = params.contentPadding + 'px ' + params.contentPadding + 'px ' + params.contentPadding + 'px ' + params.contentPadding + 'px';
						dialog.getModalBody().css("padding",headerPaddingStr);
					}

					//dialog.$modal.removeAttr('tabindex');
					if (params.isMax) {
						dialog.$modal.css({
							//display: 'table',
							padding: '0px 10px'
						});
						dialog.$modalDialog.css({
							margin: '0px 10px'
						});
					}
					/* 
					var kendoWindow = $('#mt-popwindow').data("kendoWindow")
							|| $("<div id='mt-popwindow'></div>").kendoWindow({
								modal : params.model,
								title : params.title,
								width : params.width,
								height : params.height,
								iframe : true
							}).data("kendoWindow");
					kendoWindow.refresh({
						url : path,
					});
					kendoWindow.open();
					kendoWindow.center();
					if (params.isMax) {
						kendoWindow.maximize();
					}*/
				}
			}
		}
	},
	getActoidMessage: function(rule, args) {
		$.each(args, function(i, arg) {
			window.actoid = arg;
		});
	},
	openWindowFunc: function(id, params, args) {
		if (params && params.url) {
			params.render = params.isturn;
			pageBtFunc.newWindow(null, params, null, params.url);
			// if(params.isturn){
			// 	location.href = params.url;
			// }else{
			// 	window.open(params.url);	
			// }
		}
	},
	pageClose: function(rule, args) {
		var that = pubsub.getThat(rule);
		if (that.top.location.href == that.location.href) {
			// var onbeforeunloadFunc = $('body').data("onbeforeunload");
			// if ($.isFunction(onbeforeunloadFunc)) {
			// 	onbeforeunloadFunc();
			// }

			try {
				that.close();
			} catch (e) {

			}

		} else {
			var openWindows = that.parent.$("[role=dialog]");
			var onbeforeunloadFunc = $('body').data("onbeforeunload");
			if ($.isFunction(onbeforeunloadFunc)) {
				onbeforeunloadFunc();
			}
			openWindows.each(function(i, k) {
				var id = $(this).attr("id");
				if(id){
					that.parent.BootstrapDialog.getDialog($(this).attr("id")).close();	
				}
			});
			that.parent.$("input[type=text]:visible:enabled:not([readonly]):not([class*=date]):first").focus();
		}

		/**
		 * 关闭流程审批界面
		 */
		try {
			var this_ = that.opener || that.parent;
			this_.closeWindow && (this_.closeWindow());
		} catch (e) {

		}
	},
	grefresh: function(rule, args) {
		var that = pubsub.getThat(rule);
		that.location.reload();
	},

	/**
	 * 微服务
	 */
	mt_service: function(rule, params, args) {
		var ruleOne, thatWin = window;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		} else if ($.isArray(rule)) {
			ruleOne = rule[0];
		}

		if (!ruleOne.useId) {
			alert("服务地址不存在!");
			pubsub.execChilds(rule);
			return false;
		}

		/**
		 * 参数组合
		 */
		var inParams = {
				page: '',
				pageSize: ''
			},
			data = {},
			paramsEmpty = true;
		$.each(params, function(k, v) {
			!(k in inParams) && (data[k] = v) && (paramsEmpty = false);
		});
		data = {
			params: !paramsEmpty ? JSON.stringify(data) : null
		}
		$.each(inParams, function(k, v) {
			params[k] && (data[k] = params[k]);
		});

		MyAjax({
			operation: '执行微服务',
			url: ruleOne.useId,
			type: "post",
			data: data,
			dataType: 'jsonp',
			success: function(msg) {
				SearchCallback(thatWin, params, msg, rule, ruleOne);
			}
		});
	},

	/**
	 * 保存操作
	 * 
	 * @param rule
	 * @param args
	 */
	mtSave: function(rule, args) {
		//保存验证
		/*var $valiSave = $("[valiSave=true]");
		if(!$valiSave.length && !$valiSave.valid()){
			return;
		}*/
		var that = pubsub.getThat(rule);
		if (!that.getFormValiate()) {
			return false;
		}
		var fn = null;
		var msg = $(that.document.body).data("saveMsg");
		if (msg) {


			$.each(msg, function(i, btn) {
				if (btn && btn.save) {
					btn.save(fn, args);
				}
			});

			var childs = rule.childs;
			if (childs) {
				that.savecount = 0;
				var interval = setInterval(function() {
					if (that.savecount == msg.length) {
						that.clearInterval(interval);
						pubsub.execChilds(rule);
					}
				}, 500);
			}

		}
	},
	/**
	 * sqlLite文件下载
	 * @param  {[type]} rule [description]
	 * @param  {[type]} args [description]
	 * @return {[type]}      [description]
	 */
	downSqlLite: function(rule, params, args) {
		var ruleOne;
		var thatWin = pubsub.getThat(rule);
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		// var p = {
		// 	useId: ruleOne['useId'],
		// 	params: JSON.stringify(params),
		// 	type: ruleOne['oid']
		// }

		var url = contextPath + '/mx/form/wdatasetSqllite/getById';
		MyAjax({
			operation: 'sqlLite文件下载',
			url: url,
			type: "post",
			data: {
				id: ruleOne['useId']
			},
			async: false,
			success: function(msg) {
				if (msg) {
					var sqlRule = JSON.parse(msg);
					var dataSetIds = "";
					if (sqlRule.ruleJson) {
						var ruleJson = JSON.parse(sqlRule.ruleJson);
						$.each(ruleJson, function(i, item) {
							if (dataSetIds) {
								dataSetIds += ',';
							}
							dataSetIds += item.dataSetId;
						});
					}


					var downloadUrl = contextPath + '/website/public/dataset/export/sqlite?1=1';


					// var downloadUrl = '/glaf/website/public/dataset/export/sqlite';
					if (dataSetIds) {
						downloadUrl = downloadUrl + "&dataSetIds=" + dataSetIds;
					}
					if (params) {
						downloadUrl = downloadUrl + "&params=" + JSON.stringify(params);
					}

					window.open(downloadUrl);
					// location.href = downloadUrl;
					return;

					// FormData 对象
					// 	var form = new FormData();
					// 	form.append("dataSetIds", dataSetIds);
					// 	form.append("params",params);

					// var xhr = new XMLHttpRequest();
					// xhr.open("post", downloadUrl, true);		//写入请求内容

					// xhr.send(form);
					// console.log(dataSetIds);
				}
			},
			error: function() {
				alert("异常错误,请稍后再试.");
			}
		})

	},
	/**
	 * 警示操作
	 * 
	 * @param rule
	 * @param args
	 */
	mtAlert: function(rule, args) {
		var r, v;
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param];
			alert(v);
		}
	},
	/**
	 * 加载效果
	 * 
	 * @param rule
	 * @param args
	 */
	mtLoading: function(rule, args) {
		var html = '<div class="loading-message "><div class="block-spinner-bar"><div class="bounce1"></div><div class="bounce2"></div><div class="bounce3"></div></div></div>';
		$.blockUI({
			message: html,
			baseZ: 1000,
			css: {
				border: '0',
				padding: '0',
				backgroundColor: 'none'
			},
			overlayCSS: {
				backgroundColor: '#555',
				opacity: 0.1,
				cursor: 'wait'
			}
		});
	},
	/**
	 * 取消加载效果
	 * 
	 * @param rule
	 * @param args
	 */
	cancelLoading: function(rule, args) {
		$.unblockUI();
	},
	/**
	 * 登录操作
	 * 
	 * @param rule
	 * @param args
	 */
	mtLogin: function(rule, params, args) {
		handleLogin(rule, params, args);
	},
	/**
	 * 流程提交
	 * 
	 * @param rule
	 * @param args
	 */
	mtSubmit: function(rule, args) {
		if (window.mtxx && window.mtxx.params) {
			if (confirm("你确定提交吗?")) {
				var pageId = mtxx.params.id,
					p = $.extend(true, {}, args, {
						approve: true
					}),
					id = $("." + pageId).attr("idValue");
				var data = {
					pageId: pageId,
				};
				data.flowParams = JSON.stringify(p);
				data[pageId] = id;
				MyAjax({
					operation: '流程提交',
					url: contextPath + "/mx/form/workflow/defined/submit",
					type: "POST",
					dataType: "JSON",
					data: data,
					success: function(ret) {
						alert("操作成功!");
					}
				});
			}
		}
	},

	/**
	 * 流程签收
	 * 
	 * @param rule
	 * @param args
	 */
	mtAssign0: function(rule, args) {
		FlowProcess.mtAssign0(rule, args);
	},
	/**
	 * 流程提交(流程已经启动的才可以)
	 * 
	 * @param rule
	 * @param args
	 */
	mtSubmit0: function(rule, args) {
		FlowProcess.mtSubmit0(rule, args);
	},
	/**
	 * 流程退回(流程已经启动的才可以)
	 * 
	 * @param rule
	 * @param args
	 */
	mtBack0: function(rule, args) {
		args.approve = false;
		FlowProcess.mtSubmit0(rule, args);
	},
	/**
	 * 流程撤回(作废)
	 */
	mtReject: function(rule, args) {
		FlowProcess.reject(args);
	},
	/**
	 * 流程撤回
	 */
	mtReject0: function(rule, args) {
		FlowProcess.reject0(rule, args);
	},
	/**
	 * 流程终止(作废)
	 */
	mtCancel: function(rule, args) {
		FlowProcess.cancel(args);
	},
	/**
	 * 流程终止
	 */
	mtCancel0: function(rule, args) {
		FlowProcess.cancel0(rule, args);
	},
	/**
	 * 挂起(作废)
	 */
	mtStop: function(rule, args) {
		FlowProcess.stop(args);
	},
	/**
	 * 挂起
	 */
	mtStop0: function(rule, args) {
		FlowProcess.stop0(rule, args);
	},
	/**
	 * 激活(作废)
	 */
	mtActive: function(rule, args) {
		FlowProcess.active(args);
	},
	/**
	 * 激活
	 */
	mtActive0: function(rule, args) {
		FlowProcess.active0(rule, args);
	},
	/**
	 * 下发
	 */
	sendDown: function(rule, params, args) {
		var p = {},
			sendData = {};
		for (var key in params) {
			if (key.indexOf('hmtdusd-') != -1) {
				sendData[key.replace('hmtdusd-', '')] = params[key];
			} else {
				p[key] = params[key];
			}
		}
		p["sendData"] = JSON.stringify(sendData);
		MyAjax({
			operation: '下发',
			url: contextPath + "/mx/form/data/sendDown2",
			type: "post",
			data: p,
			async: false,
			success: function(msg) {
				if (msg) {
					msg = JSON.parse(msg);
					alert(msg.message);
					pubsub.execChilds(rule);
				}
			},
			error: function() {
				alert("异常错误,请稍后再试.");
			}
		})
	},
	/**
	 * 整表下发
	 */
	sendAllDown: function(rule, params, args) {
		MyAjax({
			operation: '整表下发',
			url: contextPath + "/mx/form/data/sendDown",
			type: "post",
			data: params,
			async: false,
			success: function(msg) {
				if (msg) {
					msg = JSON.parse(msg);
					alert(msg.message);
					pubsub.execChilds(rule);
				}
			},
			error: function() {
				alert("异常错误,请稍后再试.");
			}
		})
	},
	/**
	 * 表达式运算
	 */
	exprCalc: function(rule, params, args) {
		if ($.isArray(rule)) {
			var thatWin = /*pubsub.getThat(rule, true)*/ window;
			var p = params || {},
				ruleOne = rule[0],
				callback = ruleOne.callback,
				elongateStockIndex = pubsub.elongateStockIndex;
			p.mtUserId = ruleOne['useId'];
			MyAjax({
				operation: '表达式运算',
				url: contextPath + "/mx/expr/calculateByPoi",
				type: "post",
				data: {
					params: JSON.stringify(p)
				},
				async: true,
				success: function(msg) {
					if (msg) {
						pubsub.elongateStockIndex = elongateStockIndex;
						msg = JSON.parse(msg);
						thatWin.callbackParam = thatWin.callbackParam || {};
						$.each(callback, function(i, k) {
							thatWin.callbackParam[k.param] = msg[k.columnName];
						})
						pubsub.execChilds(rule);
						pubsub.elongateStockIndex = null;
					}
					console.log(msg);
				},
				error: function() {
					alert("异常错误,请稍后再试.");
				}
			})
		}
		//console.log(rule);
		//console.log(params);
	},
	/*
	 * 逐级汇总事件
	 */
	collect: function(rule, params, args) {
		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}

		function collectInnerFunc() {
			//window.open("");
			var databaseId = params["databaseId"] || '';
			var link = contextPath + "/mx/form/treeTableAggregate/executeSpec?treeTableAggregateId=" + ruleOne['useId'] + "&databaseId=" + databaseId;
			jQuery.ajax({
				type: "POST",
				url: link,
				data: params,
				dataType: 'json',
				error: function(data) {
					alert('服务器处理错误！');
				},
				success: function(data) {
					if (data != null && data.message != null) {
						//alert(data.message);
						if (!params["isAlt"]) {
							alert('操作成功完成！');
						}
					} else {
						if (!params["isAlt"]) {
							alert('操作成功完成！');
						}
					}
					pubsub.execChilds(rule);
				}
			});
		}
		if (!params["isAlt"]) {
			if (confirm("确定执行数据汇总吗？")) {
				collectInnerFunc();
			}
		} else {
			collectInnerFunc();
		}
	},
	/**
	 * 将数组对象转换为普通的字符串信息
	 * @param  {[type]} datas [如{1:[{1:2},{1:3}],2:[{1:5}]}]
	 * @return {[type]}      [转换为{1:'2,3',2:'5'}]
	 */
	parseAryToObj : function(datas){
		debugger
		var newdatas = {};
		$.each(datas,function(k,ktem){
			var value = "";
			if($.isArray(ktem)){
				value = [];
				$.each(ktem,function(i,item){
					if(typeof item == 'object'){
						var kvalue = "";
						$.each(item,function(j,jtem){
							kvalue  = jtem;
						})
						value.push(pubsub.htmlUnescape(kvalue));
					}else{
						value.push(pubsub.htmlUnescape(kvalue));
					}
				})
				newdatas[k] = JSON.stringify(value);
			}else{
				newdatas[k] = pubsub.htmlUnescape(ktem);
			}
		})
		return newdatas;
	},
	/*
	 * CRUD事件
	 */
	mtcrud: function(rule, params, args) {
		var params = pageBtFunc.parseAryToObj(params);
		//console.log(rule);
		var ruleOne;
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		if(ruleOne.columnName){

		}
		var p = {
			useId: ruleOne['useId'],
			params: JSON.stringify(params),
			type: ruleOne['oid']
		}
		if (p.type == 'del' && !params["isDelAlt"]) {
			confirm2("是否确认删除", function(ok) {
				if (ok) {
					MyAjax({
						operation: 'CRUD事件',
						url: contextPath + "/mx/form/data/mtcrud",
						type: "post",
						data: p,
						async: false,
						success: function(msg) {
							if (msg && (msg = JSON.parse(msg)) && (!msg.statusCode || msg.statusCode != '500')) {
								//window.callbackParam = window.callbackParam || {};
								thatWin.callbackParam = thatWin.callbackParam || {};
								$.each(ruleOne.callback, function(i, k) {
									thatWin.callbackParam[k.param] = msg[k.columnName];
									//window.callbackParam[k.param] = msg[k.columnName];
								})
								if (!params["isAlt"]) {
									alert('操作成功完成！');
								}
							} else {
								alert('异常错误,请稍后再试.');
							}
							pubsub.execChilds(rule);
						},
						error: function() {
							alert("异常错误,请稍后再试.");
						}
					})
				}
			});
		} else {
			MyAjax({
				operation: 'CRUD事件',
				url: contextPath + "/mx/form/data/mtcrud",
				type: "post",
				data: p,
				async: false,
				success: function(msg) {
					if (msg && (msg = JSON.parse(msg)) && !msg.statusCode) {
						//window.callbackParam = window.callbackParam || {};
						thatWin.callbackParam = thatWin.callbackParam || {};
						$.each(ruleOne.callback, function(i, k) {
							thatWin.callbackParam[k.param] = msg[k.columnName];
							//window.callbackParam[k.param] = msg[k.columnName];
						})
						if (!params["isAlt"]) {
							alert('操作成功完成！');
						}
					} else {
						alert('异常错误,请稍后再试.');
					}
					pubsub.execChilds(rule);
				},
				error: function() {
					alert("异常错误,请稍后再试.");
				}
			})
		}
	},

	/*
	 * 查询服务
	 */
	mtsearch: function(rule, params, args) {
		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		} else if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		MyAjax({
			operation: '查询服务',
			url: contextPath + "/mx/form/data/mtsearch",
			type: "post",
			data: {
				did: ruleOne['useId'],
				params: JSON.stringify(params)
			},
			dataType: "json",
			async: false,
			success: function(msg) {
				var initParam = {};
				thatWin.callbackParam = thatWin.callbackParam || {};
				if (msg.total) {
					var retData = msg.data,
						retDataOne = retData[0];
					$.each(ruleOne.callback, function(i, k) {
						thatWin.callbackParam[k.param] = retDataOne[k.columnName];
						if (k.dataId && k.dataId.indexOf("ary") == 0) {
							!initParam[k.dataId] && (thatWin.callbackParam[k.dataId] = []) && (initParam[k.dataId] = true);
							//callbackParam[k.dataId] || (callbackParam[k.dataId] = []);
							$.each(retData, function(index, val) {
								thatWin.callbackParam[k.dataId][index] || (thatWin.callbackParam[k.dataId][index] = {});
								thatWin.callbackParam[k.dataId][index][k.param] = val[k.columnName];
							})
						} else if (k.dataId && k.dataId.indexOf("obj") == 0) {
							!initParam[k.dataId] && (thatWin.callbackParam[k.dataId] = {}) && (initParam[k.dataId] = true);
							//callbackParam[k.dataId] || (callbackParam[k.dataId] = {});
							thatWin.callbackParam[k.dataId][k.param] = retDataOne[k.columnName];
						}
					})
					if (!params["isAlt"]) {
						alert('操作成功完成！');
					}
				} else {
					$.each(ruleOne.callback, function(i, k) {
						thatWin.callbackParam[k.param] = "";
						if (k.dataId && k.dataId.indexOf("ary") == 0) {
							!initParam[k.dataId] && (thatWin.callbackParam[k.dataId] = []) && (initParam[k.dataId] = true);
							$.each(retData, function(index, val) {
								thatWin.callbackParam[k.dataId][index] || (thatWin.callbackParam[k.dataId][index] = {});
								thatWin.callbackParam[k.dataId][index][k.param] = "";
							})
						} else if (k.dataId && k.dataId.indexOf("obj") == 0) {
							!initParam[k.dataId] && (thatWin.callbackParam[k.dataId] = {}) && (initParam[k.dataId] = true);
							thatWin.callbackParam[k.dataId][k.param] = "";
						}
					})
					if (!params["isAlt"]) {
						alert('没有查询结果.');
					}
				}
				pubsub.execChilds(rule);
			},
			error: function() {
				alert("异常错误,请稍后再试.");
			}
		})
	},

	/*
	 * 转换服务事件
	 */
	convertService: function(rule, params, args) {
		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		var p = {
			useId: ruleOne['useId'],
			params: JSON.stringify(params),
			type: ruleOne['oid']
		};
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		MyAjax({
			operation: '转换服务事件',
			url: contextPath + "/mx/form/data/convertService",
			type: "post",
			data: p,
			async: false,
			success: function(msg) {
				if (msg && (msg = JSON.parse(msg)) && !msg.statusCode) {
					thatWin.callbackParam = thatWin.callbackParam || {};
					$.each(ruleOne.callback, function(i, k) {
						thatWin.callbackParam[k.param] = msg[k.columnName];
					})
					if (!params["isAlt"]) {
						alert('操作成功完成！');
					}
				} else {
					alert('异常错误,请稍后再试.');
				}
				pubsub.execChilds(rule);
			},
			error: function() {
				alert("异常错误,请稍后再试.");
			}
		})
	},
	/**
	 * cell 转 pdf 
	 * @param  {[type]} rule   [description]
	 * @param  {[type]} params [description]
	 * @param  {[type]} args   [description]
	 * @return {[type]}        [description]
	 */
	cell2pdf: function(rule, params, args) {
		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		var p = {
			useId: ruleOne['useId'],
			params: JSON.stringify(params),
			type: ruleOne['oid']
		};
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		MyAjax({
			operation: 'CELL表转PDF操作',
			url: contextPath + "/mx/form/data/cell2pdf",
			type: "post",
			data: p,
			async: false,
			success: function(msg) {
				if (msg && (msg = JSON.parse(msg)) && !msg.statusCode) {
					thatWin.callbackParam = thatWin.callbackParam || {};
					$.each(ruleOne.callback, function(i, k) {
						thatWin.callbackParam[k.param] = msg[k.columnName];
					})
					if (!params["isAlt"]) {
						alert('操作成功完成！');
					}
				} else {
					alert('异常错误,请稍后再试.');
				}
				pubsub.execChilds(rule);
			},
			error: function() {
				alert("异常错误,请稍后再试.");
			}
		})
	},
	/**
	 * cell 转 pdf 
	 * @param  {[type]} rule   [description]
	 * @param  {[type]} params [description]
	 * @param  {[type]} args   [description]
	 * @return {[type]}        [description]
	 */
	pdfmerge: function(rule, params, args) {
		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		var p = {
			useId: ruleOne['useId'],
			params: JSON.stringify(params),
			type: ruleOne['oid']
		};
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		MyAjax({
			operation: 'PDF合成服务',
			url: contextPath + "/mx/form/data/pdfmerge",
			type: "post",
			data: p,
			async: false,
			success: function(msg) {
				if (msg && (msg = JSON.parse(msg)) && !msg.statusCode) {
					thatWin.callbackParam = thatWin.callbackParam || {};
					$.each(ruleOne.callback, function(i, k) {
						thatWin.callbackParam[k.param] = msg[k.columnName];
					})
					if (!params["isAlt"]) {
						alert('操作成功完成！');
					}
				} else {
					alert('异常错误,请稍后再试.');
				}
				pubsub.execChilds(rule);
			},
			error: function() {
				alert("异常错误,请稍后再试.");
			}
		})
	},
	/**
	 * 页面打印服务
	 * @return {[type]} [description]
	 */
	mtPagePrint: function(rule, params, args) {
		var $inDom = pubsub.getJQObj(rule, true); //输入控件，获取输入控件

		if ($inDom && $inDom[0]) {
			if (!$inDom.printArea) {
				var $style = $('<script type="text/javascript" src="' + contextPath + '/scripts/jquery.PrintArea.js"></script>');
				$("body").append($style);
			}
			$inDom.printArea();
		} else {
			window.print();
		}

	},
	/**
	 * cell 打印服务
	 * @return {[type]} [description]
	 */
	mtCellPrint: function(rule, params, args) {
		var $in = pubsub.getJQObj(rule, true),
			spread = $in.data("spread");
		spread.print();
	},
	/**
	 * cell 导出excel
	 */
	mtCellExport: function(rule, params, args) {
		var that = pubsub.getThat(rule) || window;
		params = ($.isArray(params) ? params[0] : params) || {};
		var p = params["exprotValue"],
			exportName = params["exprotName"] || "",
			urlParams = "",
			ruleOne = rule;
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		var callback = ruleOne.callback,
			mtUserId = ruleOne['useId'];
		if (mtUserId) {
			urlParams = "?mtUserId=" + mtUserId;
			p = JSON.stringify(params);
		}
		var actionUrl = contextPath + "/mx/form/data/spread2xls" + urlParams;
		var iframe = that.$("#__cell_export_iframe__").get(0) ? that.$("#__cell_export_iframe__") : that.$("<iframe>", {
				id: "__cell_export_iframe__",
				name: "__cell_export_iframe__",
				style: "display:none"
			}).appendTo('body'),
			form = that.$("#__cell_export_form__").get(0) ? that.$("#__cell_export_form__").attr("action", actionUrl) : that.$("<form>", {
				id: "__cell_export_form__",
				action: actionUrl,
				method: "post",
				target: "__cell_export_iframe__",
				style: "display:none"
			}).appendTo('body'),
			exprotValue = that.$("#__cell_export_value__").get(0) ? that.$("#__cell_export_value__").val(p) : that.$("<input>", {
				id: "__cell_export_value__",
				name: "exprotValue",
				type: "text",
				value: p
			}).appendTo(form),
			exprotName = that.$("#__cell_export_name__").get(0) ? that.$("#__cell_export_name__").val(exportName) : that.$("<input>", {
				id: "__cell_export_name__",
				name: "exprotName",
				type: "text",
				value: exportName
			}).appendTo(form);
		form.submit();
	},

	/*
	 * 短信服务
	 */
	nodeMessage: function(rule, params, args) {
		MyAjax({
			operation: '短信服务',
			url: contextPath + "/mx/form/sendMessage",
			type: "post",
			data: {
				params: JSON.stringify(params)
			},
			dataType: "json",
			async: false,
			success: function(ret) {
				if (ret && ret.message) {
					alert(ret.message);
				}
				pubsub.execChilds(rule);
			},
			error: function() {
				alert("异常错误,请稍后再试.");
			}
		})
	},
	/**
	 * 打开选项卡(主页)
	 */
	mtOpenTab: function(rule, params, args) {
		var TABURL = params.TABURL,
			search = "" //
			,
			path = params.path;
		if (!path) {
			if (!TABURL) {
				alert("地址不能为空!");
				return false;
			}
			var use = {
					TABURL: '',
					TABID: '',
					TABNAME: ''
				},
				others = [];
			$.each(params, function(k, v) {
				!(k in use) && (others.push(k + "=" + v));
			});
			search = others.join("&");
			TABURL = TABURL.indexOf("?") >= 0 ? //
				(TABURL + "&" + search) : TABURL + "?" + search;
		} else {
			TABURL = path;
		}
		var func = parent.addTabEx || parent.parent.addTabEx;
		if (func) {
			func(params.TABID || new Date().getTime(), params.TABNAME, TABURL);
		} else {
			window.open(TABURL);
		}
		if(window.pageParameters && window.pageParameters.id){
			var __windows = "__windows";
			!window.top[__windows] && (window.top[__windows] = {});
			window.top[__windows][window.pageParameters.id] = window;
		}
	},
	/**
	 * 关闭选项卡(主页)
	 */
	mtCloseTab: function(rule, params, args) {
		window.ClosePageTab(params.TABID);
	},
	/**
	 * 获取手机号码
	 * @return {[type]} [description]
	 */
	callTelNo: function(rule, params, args) {
		if (window.WebViewJavascriptBridge) {
			var thatWin = /*pubsub.getThat(rule, true)*/ window;
			var ruleOne;
			if ($.isPlainObject(rule)) {
				ruleOne = rule;
			}
			if ($.isArray(rule)) {
				ruleOne = rule[0];
			}
			window.WebViewJavascriptBridge.callHandler('callTelNo', {}, function(responseData) {
				thatWin.callbackParam || (thatWin.callbackParam = {});
				window.callbackParam || (window.callbackParam = {});
				$.each(ruleOne.callback, function(i, k) {
					window.callbackParam[k.param] = thatWin.callbackParam[k.param] = responseData;
				})
				pubsub.execChilds(rule);
			});
		} else {
			console.error("请在手机端使用获取手机号码功能");
		}
	},
	/**
	 * 获取gps坐标位置
	 * @param  {[type]} rule   [description]
	 * @param  {[type]} params [description]
	 * @param  {[type]} args   [description]
	 * @return {[type]}        [description]
	 */
	callGps: function(rule, params, args) {
		if (window.WebViewJavascriptBridge) {
			var thatWin = /*pubsub.getThat(rule, true)*/ window;
			var ruleOne;
			if ($.isPlainObject(rule)) {
				ruleOne = rule;
			}
			if ($.isArray(rule)) {
				ruleOne = rule[0];
			}
			window.WebViewJavascriptBridge.callHandler('callGps', {}, function(responseData) {
				thatWin.callbackParam || (thatWin.callbackParam = {});
				window.callbackParam || (window.callbackParam = {});
				$.each(ruleOne.callback, function(i, k) {
					window.callbackParam[k.param] = thatWin.callbackParam[k.param] = responseData;
				})
				pubsub.execChilds(rule);
			});
		} else {
			console.error("请在手机端使用获取GPS坐标功能");
		}
	},
	/**
	 * 扫描二维码
	 * @return {[type]} [description]
	 */
	callScan: function(rule, params, args) {
		if (window.WebViewJavascriptBridge) {
			window.WebViewJavascriptBridge.callHandler('callScan', !!(params && params.hasCallback), function(responseData) {});
		} else {
			console.error("请在手机端使用扫描二维码功能");
		}
	},
	/**
	 * 手机注销
	 * @return {[type]} [description]
	 */
	callLogout: function(rule, params, args) {
		if (window.WebViewJavascriptBridge) {
			window.WebViewJavascriptBridge.callHandler('logout', {}, function(responseData) {});
		} else {
			console.error("请在手机端使用注销退出功能");
		}
	},
	/**
	 * 手机查看tif
	 * @return {[type]} [description]
	 */
	callTif: function(rule, params, args) {
		if (window.WebViewJavascriptBridge) {
			var tid = params["tifId"],
				tn = params["tnfield"],
				ct = params["ctfield"],
				id = params["idfield"] || "id",
				databaseId = params["databaseId"],
				url = "/mx/form/imageUpload?method=downloadById&id=" + tid;
			tn && ct && (url = "/mx/form/imageUpload?method=downloadByTab&tid=" + tid + "&tn=" + tn + "&ct=" + ct + "&id=" + id + "&databaseId=" + databaseId);
			window.WebViewJavascriptBridge.callHandler('callTif', {
				url: url
			}, function(responseData) {});
		} else {
			console.error("请在手机端使用查看TIF功能");
		}
	},
	/**
	 * [callWpfSoaTif 手机查看wpfsoatif文件]
	 * @param  {[type]} rule   [description]
	 * @param  {[type]} params [description]
	 * @param  {[type]} args   [description]
	 * @return {[type]}        [description]
	 */
	callWpfSoaTif: function(rule, params, args) {
		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		} else if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		var thatWin = window;

		pageBtFunc.mtLoading();
		MyAjax({
			operation: '手机查看wpfsoatif文件',
			url: contextPath + "/mx/wpf/getFiledotByIdForSoa",
			type: "post",
			data: params,
			dataType: "json",
			success: function(ret) {
				pageBtFunc.cancelLoading();
				if (ret && ret.code == 200) {
					if (window.WebViewJavascriptBridge) {
						window.WebViewJavascriptBridge.callHandler('callWpfSoaTif', {
							url: ret.data
						}, function(responseData) {});
					} else {
						//非手机端，进行回调
						thatWin.callbackParam = thatWin.callbackParam || {};
						$.each(ruleOne.callback, function(i, k) {
							thatWin.callbackParam[k.param] = ret.data;
						})
						pubsub.execChilds(rule);
					}
				} else {
					alert(ret.message);
				}
			},
			error: function() {
				pageBtFunc.cancelLoading();
				alert("异常错误,请稍后再试.");
			}
		});

		
	},
	/**
	 * 拨打电话
	 * @param  {[type]} rule   [description]
	 * @param  {[type]} params [description]
	 * @param  {[type]} args   [description]
	 * @return {[type]}        [description]
	 */
	callTelPhone: function(rule, params, args) {
		if (window.WebViewJavascriptBridge) {
			var tid = params["tifId"];
			window.WebViewJavascriptBridge.callHandler('callTelPhone', params["telPhone"], function(responseData) {});
		} else {
			console.error("请在手机端使用拨打电话功能");
		}
	},
	/**
	 * 手机登录
	 * @return {[type]} [description]
	 */
	callLogin: function(rule, params, args) {
		if (window.WebViewJavascriptBridge) {
			if (params.rember && params.rember == 1) {
				//保存用户cookie
				if (!$.cookie) {
					var $style = $('<script type="text/javascript" src="' + contextPath + '/webfile/js/jquery.cookie.js"></script>');
					$("body").append($style);
				}
				//保存单个用户信息
				var cookieopts = {};
				if (params.expires) {
					cookieopts.expires = params.expires;
				}
				$.cookie("userInfo", params['x'], cookieopts);
			}

			window.WebViewJavascriptBridge.callHandler('goHome', {
				"x": params['x'],
				"y": params['y'],
				"z": params['z'] || "",
				"k": params['k'] || contextPath,
				"a": params['a'],
				"r": params['r']
			}, function(responseData) {});
		} else {
			console.error("请在手机端使用手机登录功能");
		}
	},
	/**
	 * 手机查看萤石云视频
	 * @return {[type]} [description]
	 */
	callYsVideo: function(rule, params, args) {
		if (window.WebViewJavascriptBridge) {
			MyAjax({
				operation: '手机查看萤石云视频',
				url: contextPath + "/mx/form/videoData/videoMobileData",
				type: "post",
				data: params,
				async: false,
				success: function(ret) {
					if (ret) {
						window.WebViewJavascriptBridge.callHandler('callPlayVideo', {
							"param": ret
						}, function(responseData) {});
					} else {
						alert("参数传递错误");
					}
				},
				error: function() {
					alert("异常错误,请稍后再试.");
				}
			})
		} else {
			console.error("请在手机端使用此功能");
		}
	},
	/**
	 * 批量任务调度
	 */
	taskTable: function(rule, params, args) {
		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		var p = {
			useId: ruleOne['useId'],
			params: JSON.stringify(params)
		}
		MyAjax({
			operation: '组合任务表调度',
			url: contextPath + "/mx/form/data/taskTable",
			type: "post",
			data: p,
			dataType: "json",
			async: false,
			success: function(ret) {
				if (ret && ret.message) {
					if (!params["isAlt"]) {
						alert(ret.message);
					}
				}
				pubsub.execChilds(rule);
			},
			error: function() {
				alert("异常错误,请稍后再试.");
			}
		});
	},
	/**
	 * 组合表调度
	 */
	tableCombination: function(rule, params, args) {
		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		var p = {
			useId: ruleOne['useId'],
			params: JSON.stringify(params)
		}
		MyAjax({
			operation: '组合任务表调度',
			url: contextPath + "/mx/form/data/tableCombination",
			type: "post",
			data: p,
			dataType: "json",
			async: false,
			success: function(ret) {
				if (ret && ret.message) {
					if (!params["isAlt"]) {
						alert(ret.message);
					}
				}
				pubsub.execChilds(rule);
			},
			error: function() {
				alert("异常错误,请稍后再试.");
			}
		});
	},
	/**
	 * wpf推送通知
	 * @param  {[type]} rule   [description]
	 * @param  {[type]} params [description]
	 * @param  {[type]} args   [description]
	 * @return {[type]}        [description]
	 */
	wpfMobileNotification: function(rule, params, args) {
		MyAjax({
			operation: 'wpf推送通知',
			url: contextPath + "/mx/jiguang/push",
			type: "post",
			data: params,
			dataType: "json",
			async: false,
			success: function(ret) {
				if (ret && ret.message) {}
			},
			error: function() {
				alert("推送失败.");
			}
		});
	},
	/**
	 * 设置定时循环任务
	 * @param {[type]} rule   [description]
	 * @param {[type]} params [传经来的参数]
	 * @param {[type]} args   [description]
	 */
	setCyclicTask: function(rule, params, args) {
		var thatWin = /*pubsub.getThat(rule, true)*/ window;

		thatWin.createCyclicTask = function() {
			pubsub.execChilds(rule);
		}

		var cyclicTask = thatWin.setInterval("createCyclicTask()", params.delayTime || 300);

		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		} else if ($.isArray(rule)) {
			ruleOne = rule[0];
		}

		thatWin.callbackParam = thatWin.callbackParam || {};
		$.each(ruleOne.callback, function(i, k) {
			thatWin.callbackParam[k.param] = cyclicTask;
		})
	},

	/**
	 * 清除定时循环任务
	 * @param  {[type]} rule   [description]
	 * @param  {[type]} params [description]
	 * @param  {[type]} args   [description]
	 * @return {[type]}        [description]
	 */
	clearCyclicTask: function(rule, params, args) {
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		var intervalId = params.cyclicTask;
		if (intervalId)
			thatWin.clearInterval(intervalId);
	},
	/**
	 * 验证事件
	 * @param  {[type]} rule   [description]
	 * @param  {[type]} params [description]
	 * @param  {[type]} args   [description]
	 * @return {[type]}        [description]
	 */
	mtVali: function(rule, params, args) {
		var thatWin = pubsub.getThat(rule),
			ruleOne,
			$valiSave = thatWin.$("[valiSave=true]"),
			bool = true;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		if ($valiSave.length && !$valiSave.valid()) {
			bool = false;
		}

		var pl = (ruleOne["insExp"] - 0);

		for (; pl > 0; pl--) {
			thatWin = thatWin.opener || thatWin.parent;
		}

		thatWin.callbackParam || (thatWin.callbackParam = {});
		window.callbackParam || (window.callbackParam = {});
		$.each(ruleOne.callback, function(i, k) {
			window.callbackParam[k.param] = thatWin.callbackParam[k.param] = bool;
		})
		pubsub.execChilds(rule);
	},
	/*
	 * 表列转换
	 */
	tableTransform: function(rule, params, args) {
		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		var databaseId = params["databaseId"] || '';
		var link = contextPath + "/mx/form/tableTransform/execute?tableName=" + ruleOne['useId'] + "&databaseId=" + databaseId;
		MyAjax({
			type: "POST",
			url: link,
			data: params,
			dataType: 'json',
			error: function(data) {
				alert('服务器处理错误！');
			},
			success: function(data) {
				if (data != null && data.message != null) {
					if (!params["isAlt"]) {
						alert('操作成功完成！');
					}
				} else {
					if (!params["isAlt"]) {
						alert('操作成功完成！');
					}
				}
				pubsub.execChilds(rule);
			}
		});
	},
	mtConfirm: function(rule, params, args) {
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		var types = [BootstrapDialog.TYPE_DEFAULT,
				BootstrapDialog.TYPE_INFO,
				BootstrapDialog.TYPE_PRIMARY,
				BootstrapDialog.TYPE_SUCCESS,
				BootstrapDialog.TYPE_WARNING,
				BootstrapDialog.TYPE_DANGER
			],
			btnTypes = [
				"btn-default",
				"btn-info",
				"btn-primary",
				"btn-success",
				"btn-warning",
				"btn-danger",
			],
			titleType,
			btnOKClass,
			btnCancelClass;
		if (params["titleType"] && types.length - (params["titleType"] - 1) > 1) {
			titleType = types[params["titleType"] - 1];
		}
		if (params["btnOKClass"] && btnTypes.length - (params["btnOKClass"] - 1) > 1) {
			btnOKClass = btnTypes[params["btnOKClass"] - 1];
		}
		if (params["btnCancelClass"] && btnTypes.length - (params["btnCancelClass"] - 1) > 1) {
			btnCancelClass = btnTypes[params["btnCancelClass"] - 1];
		}
		window.confirm2 && window.confirm2({
			type: titleType,
			title: params["title"],
			message: params["message"],
			btnOKClass: btnOKClass,
			btnOKLabel: params["btnOKLabel"],
			btnCancelClass: btnCancelClass,
			btnCancelLabel: params["btnCancelLabel"],
			callback: function(state) {
				thatWin.callbackParam = thatWin.callbackParam || {};
				$.each(ruleOne.callback, function(i, k) {
					thatWin.callbackParam[k.param] = state;
				})
				if (!ruleOne.callback) {
					state && pubsub.execChilds(rule);
				} else {
					pubsub.execChilds(rule);
				}
			}
		})
	},
	//禁用后退按钮
	banrebackbtn: function(rule, params, args){
		if (window.history && window.history.pushState) { 
		　　$(window).on('popstate', function () { 
			　　window.history.pushState('forward', null, '#'); 
			　　window.history.forward(1); 
		　　}); 
		} 
	　　window.history.pushState('forward', null, '#'); //在IE中必须得有这两行 
	　　window.history.forward(1); 
	},
	//退出系统
	logout: function(rule, params, args) {
		// window.location.replace("http://"+window.location.host + contextPath + "/mx/login/logout")
		location.href = contextPath + "/mx/login/logout";
	},
	newSystemServer : function(rule,params,args){
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}

		var operation = rule[0] != undefined ? rule[0].useId : rule.useId;
		if (!params) {
			return;
		}
		var isAlt = false;
		if (params.isAlt) {
			isAlt = true;
		}
		var callbackFunct = function() {
			pubsub.execChilds(rule);
		}
		$.ajax({
            url: contextPath + "/mx/form/formSystemServer/getDataById",
            type: 'POST',
            dataType: 'json',
            data : {
            	id : operation
            }
        }).done(function(result) {
            if(typeof result == 'string'){
            	result = JSON.parse(result);
            }
            if(result){
            	var url = contextPath + result.url;
            	var defaultParamJSON = result.defaultParam;
            	var defaultParam = {};
            	if(defaultParamJSON){
            		$.each(defaultParamJSON,function(i,item){
            			defaultParam[item.key] = item.value;
            		})
            	}
            	if(defaultParam){
            		params = $.extend(true,params,defaultParam);
            	}
            	MyAjax({
					operation: '执行新版系统内置服务',
					url: url,
					type: "post",
					data: params,
					dataType: 'jsonp',
					success: function(msg) {
						//回调参数信息
						thatWin.callbackParam = thatWin.callbackParam || {};
						$.each(ruleOne.callback, function(i, k) {
							thatWin.callbackParam[k.param] = ret.data[k.columnName];
							//window.callbackParam[k.param] = msg[k.columnName];
						})
						callbackFunct();
					}
				});
            }
        }).fail(function(e) {
            console.log(e);
        })

	},
	systemServer: function(rule, params, args) {
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		var operation = rule[0] != undefined ? rule[0].useId : rule.useId;
		if (!params) {
			return;
		}
		var isAlt = false;
		if (params.isAlt) {
			isAlt = true;
		}
		var ruleOne;
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}


		var callbackFunct = function() {
			pubsub.execChilds(rule);
		}
		if (operation == 'addRole') {
			//新增部门
			$.ajax({
				url: contextPath + '/mx/custom/role/saveAdd',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("添加角色成功");
								}
							}
							//回调参数信息
							thatWin.callbackParam = thatWin.callbackParam || {};
							$.each(ruleOne.callback, function(i, k) {
									thatWin.callbackParam[k.param] = ret.data[k.columnName];
									//window.callbackParam[k.param] = msg[k.columnName];
								})
								// thatWin.callbackParam.id = ret.data.id;
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("添加角色失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("服务器处理错误！,请修改后再试。");
				}
			})
		} else if (operation == 'modifyRole') {
			//新增部门
			$.ajax({
				url: contextPath + '/mx/custom/role/saveModify',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("修改角色成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("修改角色失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("服务器处理错误！,请修改后再试。");
				}
			})
		} else if (operation == 'deleteRole') {
			//删除角色
			confirm2("是否确认删除", function(ok) {
				if (!ok) {
					return;
				}
				$.ajax({
					url: contextPath + '/mx/custom/role/batchDelete',
					type: "post",
					dataType: "json",
					async: true,
					data: params,
					success: function(ret) {
						if (ret) {
							if (ret.statusCode == '200') {
								if (!isAlt) {
									if (ret.message) {
										alert(ret.message);
									} else {
										alert("删除角色成功");
									}
								}
							} else {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("删除角色失败");
								}
							}
						}
						if (ret && ret.statusCode == '200') {
							callbackFunct();
						}
					},
					error: function(e) {
						alert("服务器处理错误！,请修改后再试。");
					}
				})
			});
		} else if (operation == 'addDepartment') {
			//新增部门
			$.ajax({
				url: contextPath + '/mx/custom/department/saveAdd',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("新增部门成功");
								}
							}
							//回调参数信息
							thatWin.callbackParam = thatWin.callbackParam || {};
							$.each(ruleOne.callback, function(i, k) {
									thatWin.callbackParam[k.param] = ret.data[k.columnName];
									//window.callbackParam[k.param] = msg[k.columnName];
								})
								// thatWin.callbackParam.id = ret.data.id;
								// thatWin.callbackParam.nodeId = ret.data.nodeId;
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("新增部门失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("服务器处理错误！,请修改后再试。");
				}
			})
		} else if (operation.split("-", 1) == 'copyServer') {
			var arr = operation.split("-");
			$.ajax({
				url: contextPath + '/mx/form/data/saveDataServer',
				async: true,
				data: {
					"databaseId": arr[2],
					"tableName": arr[1],
					"indexId": params.indexId,
					"tindexId": params.t_indexId,
					"indexKey": arr[3],
					"parentKey": arr[4],
					"treeKey": arr[5]
				},
				type: 'post',
				success: function(ret) {
					ret = eval("(" + ret + ")");
					if (ret.msg) {
						if (!isAlt) {
							alert(ret.msg);
						}
						callbackFunct();
					}
				}
			});

		} else if (operation == 'modifyDepartment') {
			var link = contextPath + "/mx/custom/department/saveModify";
			if (!params.id) {
				alert("修改时需要部门id");
				return;
			}
			$.ajax({
				url: link,
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("修改部门成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("修改部门失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("服务器处理错误！,请修改后再试。");
				}
			})
		} else if (operation == 'delDepartment') {
			var link = contextPath + "/mx/custom/department/batchDelete";
			confirm2("是否确认删除", function(ok) {
				if (params.id && !params.ids) {
					//拥有ID时
					params.ids = params.id;
				}
				if (ok) {
					jQuery.ajax({
						type: "POST",
						url: link,
						dataType: 'json',
						data: params,
						error: function(data) {
							alert('服务器处理错误！');
						},
						success: function(ret) {
							if (ret) {
								if (ret.statusCode == '200') {
									if (!isAlt) {
										if (ret.message) {
											alert(ret.message);
										} else {
											alert("删除部门成功");
										}
									}
								} else {
									if (ret.message) {
										alert(ret.message);
									} else {
										alert("删除部门失败");
									}
								}
							}
							if (ret && ret.statusCode == '200') {
								callbackFunct();
							}

						}
					});
				}
			});

		} else if (operation == 'blukAddUser') {
			//新增用户
			$.ajax({
				url: contextPath + '/mx/custom/user/blukSaveAdd',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("新增用户成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("新增用户失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'addUser') {
			//新增用户
			$.ajax({
				url: contextPath + '/mx/custom/user/saveAdd',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("新增用户成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("新增用户失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'registerUser') {
			//新增用户
			$.ajax({
				url: contextPath + '/mx/custom/user/register',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("新增用户成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("新增用户失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'modifyUser') {
			//修改用户
			if (!params.id) {
				alert("修改用户需要用户账号信息!");
			}
			$.ajax({
				url: contextPath + '/mx/custom/user/saveModify',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("修改用户成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("修改用户失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'resetUserPwd') {
			if (!params.id) {
				alert("修改用户密码需要用户账号信息!");
			}
			//修改用户
			$.ajax({
				url: contextPath + '/mx/custom/user/resetPwd',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("修改用户成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("修改用户失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'resetPwdByMySelf') {
			if (!params.id) {
				alert("修改用户密码需要用户账号信息!");
			}
			//修改用户
			$.ajax({
				url: contextPath + '/mx/custom/user/resetPwdByMySelf',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("修改密码成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("修改密码失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'deleteUser') {
			//修改用户
			confirm2("是否确认删除", function(ok) {
				if (ok) {
					$.ajax({
						url: contextPath + '/mx/custom/user/batchDelete',
						type: "post",
						dataType: "json",
						async: true,
						data: params,
						success: function(ret) {
							if (ret) {
								if (ret.statusCode == '200') {
									if (!isAlt) {
										if (ret.message) {
											alert(ret.message);
										} else {
											alert("删除成功");
										}
									}
								} else {
									if (ret.message) {
										alert(ret.message);
									} else {
										alert("删除失败");
									}
								}
							}
							if (ret && ret.statusCode == '200') {
								callbackFunct();
							}
						},
						error: function(e) {
							alert("异常错误");
						}
					})
				}
			});
		} else if (operation == 'saveUserRoles') {
			if (!params.actorId) {
				alert("分配用户角色需要用户信息!");
			}
			//修改用户
			$.ajax({
				url: contextPath + '/mx/custom/user/saveUserRoles',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("分配成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("分配失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误");
				}
			})
		} else if (operation == 'blukSaveUserRoles') {
			//修改用户
			$.ajax({
				url: contextPath + '/mx/custom/user/blukSaveUserRoles',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("分配成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("分配失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误");
				}
			})
		}else if (operation == 'blukInsertUserRoles') {
			//修改用户
			$.ajax({
				url: contextPath + '/mx/custom/user/blukInsertUserRoles',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("分配成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("分配失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误");
				}
			})
		}else if (operation == 'blukRemoveUserRoles') {
			//删除用户角色
			confirm2("是否确认删除", function(ok) {
				if (!ok) {
					return;
				}
				$.ajax({
					url: contextPath + '/mx/custom/user/blukRemoveUserRoles',
					type: "post",
					dataType: "json",
					async: true,
					data: params,
					success: function(ret) {
						if (ret) {
							if (ret.statusCode == '200') {
								if (!isAlt) {
									if (ret.message) {
										alert(ret.message);
									} else {
										alert("分配成功");
									}
								}
							} else {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("分配失败");
								}
							}
						}
						if (ret && ret.statusCode == '200') {
							callbackFunct();
						}
					},
					error: function(e) {
						alert("异常错误");
					}
				})
			});
		} else if (operation == 'addRole') {
			//新增角色
			$.ajax({
				url: contextPath + '/mx/custom/role/saveAdd',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("新增角色成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("新增角色失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'branchDepartmentSaveRole') {
			//新增角色
			$.ajax({
				url: contextPath + '/mx/custom/branch/department/saveRoles',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("新增成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("新增失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'addDatabaseAccessor') {
			//新增角色
			$.ajax({
				url: contextPath + '/mx/custom/database/saveAccessor',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("权限添加成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("权限添加失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'deleteDatabaseAccessor') {
			//删除数据库权限
			confirm2("是否确认删除", function(ok) {
				if (!ok) {
					return;
				}
				params.operation = 'revoke';
				$.ajax({
					url: contextPath + '/mx/custom/database/saveAccessor',
					type: "post",
					dataType: "json",
					async: true,
					data: params,
					success: function(ret) {
						if (ret) {
							if (ret.statusCode == '200') {
								if (!isAlt) {
									if (ret.message) {
										alert(ret.message);
									} else {
										alert("权限添加成功");
									}
								}
							} else {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("权限添加失败");
								}
							}
						}
						if (ret && ret.statusCode == '200') {
							callbackFunct();
						}
					},
					error: function(e) {
						alert("异常错误，请联系管理员！");
					}
				})
			});
		} else if (operation == 'setSmsPersion') {
			//新增角色
			$.ajax({
				url: contextPath + '/mx/custom/user/setSmsPersion',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("操作成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("操作失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'saveRoleMenus') {
			//新增角色
			$.ajax({
				url: contextPath + '/mx/custom/role/saveRoleMenus',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("操作成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("操作失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		}  else if (operation == 'saveRoleMenusNoDelete') {
			//新增角色
			$.ajax({
				url: contextPath + '/mx/custom/role/saveRoleMenusNoDelete',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("操作成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("操作失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'deleteRoleMenu') {
			//删除角色菜单
			confirm2("是否确认删除", function(ok) {
				if (!ok) {
					return;
				}
				$.ajax({
					url: contextPath + '/mx/custom/role/deleteRoleMenu',
					type: "post",
					dataType: "json",
					async: true,
					data: params,
					success: function(ret) {
						if (ret) {
							if (ret.statusCode == '200') {
								if (!isAlt) {
									if (ret.message) {
										alert(ret.message);
									} else {
										alert("操作成功");
									}
								}
							} else {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("操作失败");
								}
							}
						}
						if (ret && ret.statusCode == '200') {
							callbackFunct();
						}
					},
					error: function(e) {
						alert("异常错误，请联系管理员！");
					}
				})
			});
		}
	},
	downloadFile : function(rule, params, args) {
		var attachmentId = params.attachmentId;
		if (attachmentId){
			var url = contextPath + '/mx/form/attachment?method=download&id='+attachmentId;
			window.open(url);
		}
	},
	tableConvert : function(rule, params, args) {
		
	     var link = contextPath + "/mx/sys/matrixTableTransform/execute?transformId="+rule.useId;
		 jQuery.ajax({
				   type: "POST",
				   url: link,
				   dataType: 'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
					  
				   }
		});
	},
	confirmMessage : function(rule,params,args){
		var thatWin = pubsub.getThat(rule) || window;
	    var ar = {};
		$.each(rule,function(i,le){
			if(le['param'] == "isConfirmTitle"){
				ar['isConfirmTitle'] = params['isConfirmTitle'];
			}
			else if(le['param'] == "isConfirmContent"){
				ar['isConfirmContent'] = params['isConfirmContent'];
			}
			
			else if(le['param'] == "isAlertTitle"){
				ar['isAlertTitle'] = params['isAlertTitle'];
			}
			else if(le['param'] == "isAlertContent"){
				ar['isAlertContent'] = params['isAlertContent'];
			}
			else if(le['param'] == "isToast"){
				ar['isToast'] = params['isToast'];
			}
			else if(le['param'] == "toast"){
				ar['name'] = 'toast';
			}
			else if(le['param'] == "confirm"){
				ar['name'] = 'confirm';
			}
			else if(le['param'] == "alert"){
				ar['name'] = 'alert';
			}
		});
		
		
		if(document.head.innerHTML.indexOf("mui.min.css") == -1){
			document.head.innerHTML = document.head.innerHTML + "<link rel='stylesheet' type='text/css' href='"+ contextPath +"/scripts/mui/css/mui.min.css' />";
			
		}
		

		if(ar['name'] == 'alert'){
			thatWin.mui.alert(ar['isAlertContent'], ar['isAlertTitle'], function() {
//					info.innerText = '你刚关闭了警告框';
				pubsub.execChilds(rule);
			});
		}
		else if(ar['name'] == 'confirm'){
			var btnArray = ['取消', '确定'];
			thatWin.mui.confirm(ar['isConfirmContent'], ar['isConfirmTitle'], btnArray, function(e) {
				if (e.index == 1) {
//					info.innerText = '你刚确认MUI是个好框架';
					pubsub.execChilds(rule);
				} 
			})
		}
		else if(ar['name'] == 'toast'){
			thatWin.mui.toast(ar['isToast'],params);
			pubsub.execChilds(rule);
		}
		
	},
	callteim : function(rule,params,args){
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		var value = rule[0] != undefined ? rule[0].useId : rule.useId;
		if (!params) {
			return;
		}
		var isAlt = false;
		if (params.isAlt) {
			isAlt = true;
		}
		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		var callbackFunct = function() {
			pubsub.execChilds(rule);
		}
		var data = value.split("_0_");

		var tmpId = data[1];
		var baseId = data[0];
		if(!tmpId || !baseId){
			if(!isAlt){
				alert("未配置外部服务");
			}
			return;
		}
		var datapos;
		$.ajax({
			url :contextPath + "/rs/teim/tmp/resbody",
			type : 'post',
			dataType : 'json',
			data : {
				tmpId:tmpId
			},
			async : false,
			success : function(data){
				if(typeof data == 'string'){
					data = JSON.parse(data);
				}
				var responseBody=data;
				if(responseBody && responseBody.data && responseBody.data.datapos){
					datapos = responseBody.data.datapos;
				}
			},
			error : function(e){
				console.log(e);
			},
			complete : function(e){
				console.log(e);
			}
		});

		var url = contextPath + "/rs/teim/api/"+data[1]+"/"+data[0];
		var realParam = {
			params : JSON.stringify(params)
		};

		// url += "?params="+JSON.stringify(params);
		$.ajax({
			url: url,
			type: "post",
			dataType: "json",
			async: true,
			data: realParam,
			success: function(ret) {
				if(ret.msg && !isAlt){
					alert(ret.msg);
				}
				if(ret.return){
					var retData = ret.return;
					if(typeof ret.return == 'string'){
						try{
							retData = JSON.parse(ret.return);	
						}catch(e){
							retData = ret.return;						
						}
					}
					//回调参数信息
					thatWin.callbackParam = thatWin.callbackParam || {};
					$.each(ruleOne.callback, function(i, k) {
						thatWin.callbackParam[k.param] = "";
						if(datapos){
							var dataposAry = datapos.split(",");
							$.each(dataposAry,function(i,item){
								thatWin.callbackParam[k.param] += retData[item][k.columnName];
							})
						}else{
							if(typeof retData == 'object'){
								thatWin.callbackParam[k.param] = retData[k.columnName];
							}else{
								thatWin.callbackParam[k.param] = retData;
							}
						}
					})	
					callbackFunct();
				}
			},
			error: function(e) {
				alert("服务器处理错误！,请修改后再试。");
			}
		})
	},
	getJSONArrayByPath : function(dataAry,json,path){
		if(path == 'root'){

		}else{
			var pathAry = path.split(".");
			var doc = null;
			var nextPath = path.substring(path.indexOf(".")+1,path.length);
			var obj = null;
			var nextJson = null;
			if(pathAry.length > 0){
				doc = pathAry[0];
				obj = json[doc];
				if($.isArray(obj)){
					if(pathAry.length == 1){
						$.extend(true,dataAry,obj);
					}else{
						$.each(obj,function(k,ktem){
							nextJson = ktem;
							pageBtFunc.getJSONArrayByPath(dataAry,nextJson,nextPath);		
						})	
					}
				}else{
					if(typeof obj == 'string'){
						obj = JSON.parse(obj);
					}
					if(pathAry.length == 1){
						dataAry.push(obj);
					}else{
						pageBtFunc.getJSONArrayByPath(dataAry,obj,nextPath);
					}
				}
			}

		}
	},
	parseIdentity : function(rule,params,args){
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		if (!params) {
			return;
		}
		var isAlt = false;
		if (params.isAlt) {
			isAlt = true;
		}
		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		var callbackFunct = function() {
			pubsub.execChilds(rule);
		}

		var value = rule[0] != undefined ? rule[0].useId : rule.useId;
		var data = value.split("_0_");

		var tmpId = data[1];
		var baseId = data[0];
		if(!tmpId || !baseId){
			if(!isAlt){
				alert("未配置外部服务");
			}
			return;
		}

		var datapos;
		$.ajax({
			url :contextPath + "/rs/teim/tmp/resbody",
			type : 'post',
			dataType : 'json',
			data : {
				tmpId:tmpId
			},
			async : false,
			success : function(data){
				if(typeof data == 'string'){
					data = JSON.parse(data);
				}
				var responseBody=data;
				if(responseBody && responseBody.data && responseBody.data.datapos){
					datapos = responseBody.data.datapos;
				}
			},
			error : function(e){
				console.log(e);
			},
			complete : function(e){
				console.log(e);
			}
		});

		params.tmpId = tmpId;
		params.baseId = baseId;
		
		var url = contextPath + "/mx/form/attachment/OrcData";
		$.ajax({
			url: url,
			type: "post",
			dataType: "json",
			async: true,
			data: params,
			success: function(ret) {
				if(ret.msg && !isAlt){
					alert(ret.msg);
				}
				if(ret.return){
					var retData = ret.return;
					if(typeof ret.return == 'string'){
						try{
							retData = JSON.parse(ret.return);	
						}catch(e){
							retData = ret.return;						
						}
					}
					var dataAry = [];
					if(datapos){
						var dataposAry = datapos.split(",");
						$.each(dataposAry,function(i,item){
							pageBtFunc.getJSONArrayByPath(dataAry,retData,item);
						});
					}
					//回调参数信息
					thatWin.callbackParam = thatWin.callbackParam || {};
					var variable = retData;
					$.each(ruleOne.callback, function(i, k) {
						thatWin.callbackParam[k.param] = "";
						if(datapos){
							$.each(dataAry,function(j,jtem){
								thatWin.callbackParam[k.param] += jtem[k.columnName];
							})
						}else{
							if(typeof retData == 'object'){
								thatWin.callbackParam[k.param] = retData[k.columnName];
							}else{
								thatWin.callbackParam[k.param] = retData;
							}
						}
					})	
					callbackFunct();
				}
				// if(ret.msg && !isAlt){
				// 	alert(ret.msg);
				// }
				// if(ret.status == '200'){
				// 	var retData = ret.data;
				// 	console.log(retData);
					
				// 	//回调参数信息
				// 	// thatWin.callbackParam = thatWin.callbackParam || {};
				// 	// $.each(ruleOne.callback, function(i, k) {
				// 	// 	thatWin.callbackParam[k.param] = "";
						
				// 	// 	if(typeof retData == 'object'){
				// 	// 		thatWin.callbackParam[k.param] = retData[k.columnName];
				// 	// 	}else{
				// 	// 		thatWin.callbackParam[k.param] = retData;
				// 	// 	}
				// 	// })	
				// 	callbackFunct();
				// }
			},
			error: function(e) {
				alert("服务器处理错误！,请修改后再试。");
			}
		})
	},
};
pubsub.sub("page", pageBtFunc);

/**
 * 查询服务、其他服务等等回调函数
 * @param thatWin
 * @param params
 * @param msg
 * @param rule
 * @param ruleOne
 * @returns
 */
function SearchCallback(thatWin, params, msg, rule, ruleOne) {
	thatWin.callbackParam = thatWin.callbackParam || {};
	if (msg.total) {
		var retData = msg.data,
			retDataOne = retData[0],
			initParam = {};
		$.each(ruleOne.callback, function(i, k) {
			thatWin.callbackParam[k.param] = retDataOne[k.columnName];
			if (k.dataId && k.dataId.indexOf("ary") == 0) {
				!initParam[k.dataId] && (thatWin.callbackParam[k.dataId] = []) && (initParam[k.dataId] = true);
				//callbackParam[k.dataId] || (callbackParam[k.dataId] = []);
				$.each(retData, function(index, val) {
					thatWin.callbackParam[k.dataId][index] || (thatWin.callbackParam[k.dataId][index] = {});
					thatWin.callbackParam[k.dataId][index][k.param] = val[k.columnName];
				})
			} else if (k.dataId && k.dataId.indexOf("obj") == 0) {
				!initParam[k.dataId] && (thatWin.callbackParam[k.dataId] = {}) && (initParam[k.dataId] = true);
				//callbackParam[k.dataId] || (callbackParam[k.dataId] = {});
				thatWin.callbackParam[k.dataId][k.param] = retDataOne[k.columnName];
			}
		});
		if (!params["isAlt"]) {
			alert('操作成功完成！');
		}
	} else {
		$.each(ruleOne.callback, function(i, k) {
			thatWin.callbackParam[k.param] = "";
		});

		if (msg.message) {
			if(!params["isAlt"]){
				alert(msg.message);
			}
		} else if (!params["isAlt"]) {
			alert('没有查询结果.');
		}
	}
	pubsub.execChilds(rule);
}

function ClosePageTab(TABID) {
	var tabWin = GetTabWin.find(),
		cWin;
	if (tabWin != null) {
		var close_ = function(id) {
			var a = tabWin.$("#link_" + id) //
				.closest("li").find("div a");
			return !!!tabWin.closePage(a.get(0));
		};
		if (TABID) {
			close_(TABID);
		} else if (cWin = tabWin.__child) {
			tabWin.$("iframe").each(function() {
				if (this.contentWindow === cWin) {
					var id = tabWin.$(this) //
						.closest("div").attr("id");
					return !close_(id);
				}
			});
		}
	}
}


/**
 * 获取选项卡所在的window
 */
var GetTabWin = (function(win) {
	var func = new Function(),
		index = 0, //
		level = 5;
	func.find = function(win) {
		win = win || window;
		if (win.document.getElementById("usual1") && //
			win.closePage) {
			index = 0;
			return win;
		} else {
			win.parent && (win.parent.__child = win);
			if (!win.parent || index > level) {
				index = 0;
				return null;
			}
			++index;
			return func.find(win.parent);
		}
	};
	return func;
})();

/**
 * 页面参数取值
 * @param key
 * @returns
 */
function GetPageParameter(key) {
	var params = pageParameters;
	return (params || {})[key];
}
/**
 * 页面参数赋值
 * @param key
 * @param value
 * @returns
 */
function SetPageParameter(key, value) {
	var params = pageParameters;
	params && (params[key] = value);
}

function initPageConfigFunc(opts) {
	//背景图切换
	var images = [];
	if (opts.bgImage && opts.bgImage.length == 0) {} else if (opts.bgImage && opts.bgImage.length == 1) {
		$.backstretch([
			contextPath + opts.bgImage[0].name
		], {
			fade: opts.fade * 1000,
			duration: opts.duration * 1000
		});
	} else if (opts.bgImage) {
		$.each(opts.bgImage, function(i, o) {
			images[i] = contextPath + o.name;
		});
		$.backstretch(images, {
			fade: opts.fade * 1000,
			duration: opts.duration * 1000
		});
	}
}