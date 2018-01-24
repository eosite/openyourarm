var pageFunc = {
	getValue : function(rule, args) {
		if (mtxx.params) {
			return mtxx.params[rule.inparam];
		}
		return null;
	},
	getRow : function(rule, args) {
		var targer = pubsub.getJQObj(rule);
		var params = targer.data("params");
		if (params) {
			return params.value;
		}
		//获取不到则从页面数据中获取
		var params = pageParameters,idatasetId = rule.idatasetId || rule.indatasetId;
		if (idatasetId) {
			var dataSources = params["detail"]["dataSources"];
			if (!$.isEmptyObject(dataSources) && dataSources[idatasetId]) {
				return dataSources[idatasetId][rule.columnName] || '';
			}
		} else {
			return params[rule.columnName] || (window.callbackParam && window.callbackParam[rule.columnName]) || '';
		}
		return "";
	},
	linkageControl : function(rule, params) {
		pageFunc.linkagePage(rule, params);
	},
	linkagePage : function(rule, params) {
		params.newWin = '1';
		var p = pubsub.paramsToStr(params), id = rule;
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
	newWindow : function(id, params, args) { // 弹出窗口
		// args[0]
		// 格式{link:{url:'xxxx',name:'图片链接',id:'xx'},model:true,title:'标题',jumpType:'singlePage',width:'200px',height:'100px'}
		var obj = args[0];
		if (obj && obj.jumpType) {
			var p = pubsub.paramsToStr(params); // 参数
			var path;
			var f = "";
			if (obj.slink) { // charts 查看原始数据专用
				if (obj.fillform) {
					f = "&fillform=" + obj.fillform;
				}
				/*
				 * if(obj.slink.startsWith("/mx")){ obj.slink = contextPath +
				 * obj.slink ; } path = obj.slink + "?" + p + f
				 */;
			}
			var link = JSON.parse(obj.link), url = link.url;
			path = contextPath + url + ((url.indexOf("?") > -1) ? "" : "?") + p
					+ f;
			if (obj.jumpType == 'singlePage') {
				window.open(path);
			} else {
				var iTop = (window.screen.availHeight - 30 - eval(obj.height
						.replace('px', ''))) / 2; // 获得窗口的垂直位置;
				var iLeft = (window.screen.availWidth - 10 - eval(obj.width
						.replace('px', ''))) / 2; // 获得窗口的水平位置;
				if (obj.model == "true") {
					window.open(path, obj.title, "width=" + obj.width
							+ ",height=" + obj.height + ",top=" + iTop
							+ ",left=" + iLeft + ", modal=true,status=no");
				} else {
					var kendoWindow = $('#mt-popwindow').data("kendoWindow")
							|| $("<div id='mt-popwindow'></div>").kendoWindow({
								modal : obj.model,
								title : obj.title,
								width : obj.width,
								height : obj.height,
								/*
								 * position : { top : iTop, left : iLeft },
								 */
								iframe : true
							}).data("kendoWindow");
					kendoWindow.refresh({
						url : path,
					});
					kendoWindow.open();
					kendoWindow.center();
					if (obj.maximize == "true") {
						kendoWindow.maximize();
					}
				}
			}
		}else{ //事件定义器中打开页面
			var p = pubsub.paramsToStr(params,['isMax','width','height','singlePage','model','title']), // 参数
				url = id[0].srcUrl ,
				path = contextPath + url + ((url.indexOf("?") > -1) ? "" : "?") + p;
			if(!params.singlePage){
				window.open(path);
			} else {
				var iTop = (window.screen.availHeight - 30 - eval((params.height+"").replace('px', ''))) / 2; // 获得窗口的垂直位置;
				var iLeft = (window.screen.availWidth - 10 - eval((params.width+"").replace('px', ''))) / 2; // 获得窗口的水平位置;
				if (params.model) {
					window.open(path, params.title, "width=" + params.width
							+ ",height=" + params.height + ",top=" + iTop
							+ ",left=" + iLeft + ", modal=true,status=no");
				} else {
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
					}
				}
			}
		}
	},
	pageClose : function(rule, args) {
		var that = pubsub.getThat(rule);
		if (that.top.location.href == that.location.href) {
			that.close();
		} else {
			var kendoWindows = that.parent.$('[data-role=window]');
			kendoWindows.each(function(i, k) {
				var tw = that.parent.$(k).data('kendoWindow');
				if (tw.content().indexOf(rule.outid) != -1)
					tw.close();
			});
		}
	},
	grefresh : function(rule, args) {
		var that = pubsub.getThat(rule);
		// that.location.href = that.location.href ;
		that.location.reload();
	},
	/**
	 * 保存操作
	 * 
	 * @param rule
	 * @param args
	 */
	mtSave : function(rule, args) {
		var msg = $(document.body).data("saveMsg");
		if(msg){
			//btn.save();
			$.each(msg, function(i, btn){
				if(btn && btn.save){
					btn.save();
				}
			});
		}
	},
	/**
	 * 登录操作
	 * 
	 * @param rule
	 * @param args
	 */
	mtLogin : function(rule,params,args) {
		handleLogin(rule,params,args);
	},
	/**
	 * 流程提交
	 * 
	 * @param rule
	 * @param args
	 */
	mtSubmit : function(rule, args) {
		if(window.mtxx && window.mtxx.params){
			if(confirm("你确定提交吗?")){
				var pageId = mtxx.params.id, p = $.extend(true, {}, args, {
					approve : true
				}), id = $("." + pageId).attr("idValue");
				var data = {
						pageId : pageId,
				};
				data.flowParams = JSON.stringify(p);
				data[pageId] = id;
				$.ajax({
					url : contextPath + "/mx/form/workflow/defined/submit",
					type : "POST",
					dataType : "JSON",
					data : data,
					success : function(ret){
						alert("操作成功!");
					}
				});
			}
		}
	},
	/**
	 * 流程撤回
	 */
	mtReject : function(rule, args){
		FlowProcess.reject(args);
	},
	/**
	 * 流程终止
	 */
	mtCancel : function(rule, args){
		FlowProcess.cancel(args);
	},
	/**
	 * 挂起
	 */
	mtStop : function(rule, args){
		FlowProcess.stop(args);
	},
	/**
	 * 激活
	 */
	mtActive : function(rule, args){
		FlowProcess.active(args);
	},
	/**
	 * 下发
	 */
	sendDown : function(rule,args){
		
	}
};
pubsub.sub("page", pageFunc);