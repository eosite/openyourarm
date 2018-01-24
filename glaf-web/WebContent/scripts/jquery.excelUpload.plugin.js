/**
 * 初始化
 * @return {[type]} [description]
 */
function initExpressionFn(){
	var select = mtxx.select,
		data = select.data("expdata");
	return data ? (typeof data == "string" ? JSON.parse(data) : data) : [];
}

(function($) {
	
	
	/**
	 * spreadjs 文件上传  扩展自 jquery fileupload
	 * 		  
	 */
	//上传文件按钮
	var uploadButton = $('<button/>').addClass('btn btn-primary upload').prop('disabled', true).text('正在上传...').on(
			'click',
			function() {
				setTimeout(function() {
					//$('#progress .progress-bar').css('width', '0');
				}, 3000);
				var $this = $(this),
					data = $this.data();
				$this.off('click').text('中止').on('click', function() {
					$this.remove();
					data.abort();
				});
				data.submit().always(function() {
					$this.remove();
				});
			}),
		//取消按钮
		cancelButton = $('<button/>').addClass('btn btn-danger cancel').text('取消').on('click', function() {
			var $this = $(this),
				data = $this.data();
			$this.closest('div').remove();
			data.abort();
		}),
		removeButton = $('<button/>').addClass('btn btn-danger delete').text('删除').on('click', function() {
			if (confirm("确认删除吗？")) {
				var $this = $(this),
					$parent = $this.parents(".mt-sp-jqfileupload"),
					opts = $parent.data(plugin).opts;
				$.ajax({
					url: $this.attr("removeUrl"),
					dataType:"json",
					success: function(data) {
						alert("删除成功");
						$this.closest('div').remove();
						opts.deleteCallback.call($parent,data);
					},
					error: function(d) {
						alert("删除失败");
					}
				});
			}
		}),
		//文件上传
		fileuploadadd = function(e, data) {
			//var $mtspFiles = $(o).find('.mt-sp-files');
			var $mtspFiles = $(e.target).parents(".mt-sp-jqfileupload").find(".mt-sp-files");
			data.context = $('<div/>').appendTo($mtspFiles);
			$.each(data.files, function(index, file) {
				var node = $('<span/>').text(file.name);
				if (!index) {
					node.append(uploadButton.clone(true).data(data)).append(cancelButton.clone(true).data(
						data));
				}
				node.appendTo(data.context);
			});
		},
		fileuploadprocessalways = function(e, data) {
			var index = data.index,
				file = data.files[index],
				node = $(data.context.children()[index]);
			if (file.preview) {
				//node.prepend('<br>').prepend(file.preview);
			}

			if (file.error) {
				node.append('<br>').append($('<span class="text-danger"/>').text(file.error));
			}
			if (index + 1 === data.files.length) {
				data.context.find('button.upload').text('上传').prop('disabled', !!data.files.error);
			}
		},
		fileuploaddone = function(e, data) {
			$(this).parent().removeAttr("disabled");
			var $spfileupload = $(this).parent().parent(),
				pluginData = $spfileupload.data(plugin),
				option = pluginData.opts;
				$spfileupload.data("spFileUpload",pluginData);
				pluginData.opts && pluginData.opts.uploadSuccessCallback.call($spfileupload,e,data);
			$.each(data.result, function(index, file) {
				if (file.error) {
					var error = $('<span class="text-danger"/>').text(file.error);
					$(data.context.children()[index]).append('<br>').append(error);
				} else {
					var $tg = $(data.context.children()[index]);
					$tg.find('button.cancel').remove();
					$tg.find('button.upload').remove();
					$tg.wrap($('<a>').attr('target', '_blank').prop('href', data.dowmloadUrl +"&id="+ file.id));
					$tg.parent().parent().append(removeButton.clone(true).data(data).attr("removeUrl", option.removeUrl + "&id=" + file.id + "&fileNames=&randomParent=" + file.parent));
					//$(o).data('saveId', file.parent);
				}

			});
			var tableData = null;
			if(data.result[0] != "false"){
				tableData = data.result[0];
				
				creatGrid(tableData);
			}
			
			if($.isFunction(option.onUploadSucess)){
				option.onUploadSucess(data);
			}
			
			
		},
		creatGrid = function(tableData){
			var maxLength = 0;
			if(tableData && tableData.length != 0){
				maxLength = tableData.length;
				var columns = [];
				
				columns.push({
				    'filterable': false,
				    'field': 'errorMessage',
				    'width': 200,
				    'attributes': {
				        'style': 'text-align:left;'
				    },
				    'id': 'c4',
				    'sortable': false,
				    'title': 'errorMessage',
				    'menu': false
				});
				var i = 0;
				$.each(tableData[0],function(key,value){
					if(key == 'errorMessage'){
						return true;
					}
					if(i > 5){
						return false;
					}
					i++;
					var column = {
					    'filterable': false,
					    'field': key,
					    'width': 200,
					    'attributes': {
					        'style': 'text-align:left;'
					    },
					    'id': 'c4',
					    'sortable': false,
					    'title': key,
					    'menu': false
					};
					columns.push(column);
				});
				if(maxLength > 500){
					tableData.length = 500;
				}

				var $div = $('<div id="grid" style="height:600px;width:788px">');
				$div.grid({
					datas: tableData,
					columns: columns,
				    resizable: "false",
				    scrollable: false,
				    clickUpdate: false,
				    occupy: false,
				    sortable: {},
				    selectable: '',
				    showInLine: true,
				    pagination: {
				        paging: false,
				        page: 1,
				        pageable: false,
				    }
				});
			}else{
				alert("异常错误！");
				return;
				var $div = "没有错误信息";
			}

			var dialog = new BootstrapDialog({
				title: "错误数据共"+maxLength+"条，无法导入",
	        	message: $div,
	            buttons: [{
	                label: '确定',
	                action: function(dialogRef){
	                    dialogRef.close();
	                }
	            }],
	            closable: true
	        });
			dialog.realize();

		    dialog.getModalHeader().css({
				'background-color': '#337ab7'
			});

		    dialog.getModalContent().css({
				'width': '800px'
			});
			dialog.getModalBody().css({
				'width': '800px'
			});

			dialog.getModalBody().css({
				'height': '700px'
			});

			dialog.getModalBody().css({
				'padding': '5px'
			})

			dialog.open();

		},

		//选择规则弹窗
		openChooseExcelRule = function(title,data){
			var that = this;

	        //添加事件
	        var pluginData = $(this).data(plugin),opts = pluginData.opts;
	        var openWinUrl = contextPath + '/mx/form/defined/ex/excelUploadRule';
	        var dialog = null;
	        
	        dialog = new BootstrapDialog({
				title: title,
				message: function(){
					var $message = $('<iframe style="width:650px;height:500px;border-width:0px;"></iframe>');
					$message.attr("src", openWinUrl);
					return $message;
				},
				buttons: [{
					label: '关闭',
					cssClass: 'tn btn-outline blue pull-left cancelBt',
					action: function(dialogRef) {
						dialogRef.close();
					}
				},{
					label: '确定',
					cssClass: 'btn btn-outline red okBt',
					action: function(dialogRef) {
						var param = dialog.getModalBody().find("iframe")[0].contentWindow.getExcelUploadRule();
						if(param){
							that.ruledata = param;	//保存规则
							dialogRef.close();
						}
					}
				}],
				closable: false,
				onshown : function(){
					if(that.ruledata)
						dialog.getModalBody().find("iframe")[0].contentWindow.initData(that.ruledata[0]);
				}
			});
	        
			dialog.realize();
			dialog.getModalHeader().css({
				'background-color': '#337ab7'
			});

			dialog.getModalContent().css({
				'width': '660px'
			});

			dialog.getModalBody().css({
				'padding': '5px'
			})
			dialog.open();
			// dialog.options.onshown = 
		},
		//方法集合
		methods = {
			init: function(options) {
				var that = this;
				return this.each(function(i, o) {
					var pluginData = $.data(o, plugin) ,
						opts ;
					if (pluginData) {
						opts = $.extend(true, pluginData.opts, options);
					} else {
						opts = $.extend(true, {}, $.fn[plugin].defaults, options);
						opts.baseUrl = opts.url; 
						opts.url = opts.baseUrl + opts.randomParent;
						$.data(o, plugin, {
							opts: opts
						});
					}
					if(!opts.ableChangeRule){	//开放规则
						$(o).find("span.btn.dltemp").hide();
					}

					if(!opts.showFileList){   //是否显示文件列表
						$(o).find("div.mt-sp-files").hide();
					}

					if(opts.fileListMaxH){//设置文件列表最大高度
						$(o).find("div.mt-sp-files").css("max-height",opts.fileListMaxH);
						$(o).find("div.mt-sp-files").css("overflow-y",'scroll');
					}
					that.ruledata = that.ruledata || opts.excelRule;
					var uploadClick = function(events){
						if(that.find(".fileinput-button").attr("disabled")){
							event.stopImmediatePropagation();
							event.preventDefault();
							return;
						}
						var ruledata = that.ruledata;
						if(ruledata && ruledata[0]){
						}else{
							alert("你还未选择任何规则信息");
							event.stopImmediatePropagation();
							event.preventDefault();
						}
					}
					
					$(o).data("saveId", opts.randomParent).find(".mt-sp-fileupload").on("click",uploadClick)
						.fileupload(opts)
						.bind('fileuploadsubmit',function(e,data){
							var ruledata = that.ruledata;
							if(ruledata && ruledata[0]){
								data.formData = {excelRuleData : JSON.stringify(ruledata[0])};
							}
							var parameters = opts.excel_parameters;
							if(parameters){
								data.formData.param = JSON.stringify(parameters);
							}
							//禁用上传
							that.find(".fileinput-button").attr("disabled",true);
						})
						.bind('fileuploadadd', fileuploadadd)
						.bind('fileuploadprocessalways', fileuploadprocessalways)
						.bind('fileuploadprogressall', function(e, data) {
							var progress = parseInt(data.loaded / data.total * 100, 10);
							$(o).find('.progress-bar').css('width', progress + '%');
						})
						.bind('fileuploaddone', fileuploaddone)
						.on('fileuploadfail', function(e, data) {
							$.each(data.files, function(index) {
								var error = $('<span class="text-danger"/>').text('文件上传失败！');
								$(data.context.children()[index]).append('<br>').append(error);
							});
							//$(o).data('saveId', 0);
						})
						.prop('disabled', !$.support.fileInput).parent().addClass($.support.fileInput ? undefined : 'disabled');
					$(o).dblclick(function(e){
						$(o).find("div.mt-sp-files").toggle();
					});
					$(o).find(".dltemp").on('click',function(){
						openChooseExcelRule.call(that,"规则设置",options.excelRule);
					})
				});
			},
			setValue: function(value) {
				//还原 数据
				var $this = $(this),
					pluginData = $(this).data(plugin),
					opts = pluginData.opts,
					$filesWrap = $(this).find(".mt-sp-files"),
					$fileupload = $(this).find(".mt-sp-fileupload");
				$this.data('saveId', value);
				opts.randomParent=value;
				opts.url= opts.baseUrl+value;
				//$fileupload.fileupload("destroy");
				//methods.init.apply(this, opts);
					
				var option = $fileupload.fileupload('option');
				$fileupload.fileupload(opts);
				$.ajax({
					url: opts.getUrl+value,
					context: $this[0]
				}).always(function() {
					$this.removeClass('fileupload-processing');
				}).done(function(result) {
					option.context = $('<div/>').appendTo($filesWrap);
					$.each(JSON.parse(result), function(index, file) {
						var $tg = $('<span/>').text(file.fileName),
							$a = $('<a>').attr('target', '_blank').prop('href', option.dowmloadUrl + "&id=" + file.id).append($tg),
							node = $('<div>').append($a),
							$rBtn = removeButton.clone(true).data(option).attr("removeUrl", option.removeUrl + "&id=" + file.id + "&fileNames=&randomParent=" + file.parent);
						node.append($rBtn);
						if($this.attr("disabled")){
							$rBtn.attr("disabled","disabled");
						}
						node.appendTo(option.context);
					});

					var $fileLists = $this.find("div.mt-sp-files");
					if(opts.readOnly&&$fileLists.html()!=""){  //是否只读
						$fileLists.find("button").remove();
						$this.html($fileLists);
					}
					//$fileupload.fileupload(option);
					
					// $(this).find(".mt-sp-fileupload").fileupload('option', 'done').call($(this).find(".mt-sp-fileupload"), $.Event('done'), {result: JSON.parse(result)});
				});
			},
			disabled:function(bl){
				$(this).attr("disabled","disabled");
				var jq = $(this).find("input");
				var $btn = $(this).find(".delete");
				if(!bl){
					jq.attr("disabled","disabled");
					if($btn.length>0){
						$btn.attr("disabled","disabled");
					}
				}else{
					jq.removeAttr("disabled");
					if($btn.length>0){
						$btn.removeAttr("disabled");	
					}
				}
			},
			setParam: function(args){
				var that = this;
				var pluginData = $(this).data(plugin),opts = pluginData.opts;
				var parameters = opts.excel_parameters || {};
				parameters = $.extend(false,parameters,args);
				opts.excel_parameters = parameters;	//保存参数
			},
			setIniParam: function(args){
				var that = this;
				var argsJson = {};
				$.each(args,function(i,item){
					argsJson[item.param] = item.value;
				})
				var pluginData = $(this).data(plugin),opts = pluginData.opts;
				var parameters = argsJson;
				opts.excel_parameters = parameters;	//保存参数
			}
		};
	var plugin = "excelFileUpload";
	$.fn[plugin] = function(opts) {
		if (methods[opts]) {
			return methods[opts].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if (typeof opts === 'object' || !opts) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + opts + ' does not exist on jQuery.' + plugin);
		}
	}


	//默认参数
	$.fn[plugin].defaults = {
		baseUrl: '/glaf/mx/form/attachment?method=upload&to=to_dir&randomParent=',
		url: '/glaf/mx/form/attachment?method=upload&to=to_dir&randomParent=',
		dowmloadUrl: '/glaf/mx/form/attachment?method=download&from=to_dir',
		removeUrl: '/glaf/mx/form/attachment?method=remove&from=to_dir',
		getUrl: "/glaf/mx/form/attachment?method=getFilesByRandomParent&randomParent=",
		randomParent: '',
		dataType: 'json',
		autoUpload: false,
		acceptFileTypes: '',
		maxFileSize: 52428800,
		disableImageResize: /Android(?!.*Chrome)|Opera/.test(window.navigator.userAgent),
		previewMaxWidth: 100,
		previewMaxHeight: 100,
		previewCrop: true,
		excelRule:[],
		uploadSuccessCallback:function(e,data){},
		deleteCallback:function(){}
	};
})(jQuery);