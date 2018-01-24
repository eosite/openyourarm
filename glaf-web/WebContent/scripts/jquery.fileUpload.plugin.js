(function($) {
	function fileUpload_fileTypes(html, type) {
        var doc = [".doc", ".docx", ".xls", ".xlsx", ".ppt"];
        if (type == "tif") {
            doc = [".tif", ".tiff"];
        } else if (type == "pdf") {
            doc = [".pdf"];
        } else if (type == 'txt') {
            doc = [".txt"];
        }
        for (var i = 0; i < doc.length; i++) {
            if (html.toLowerCase().indexOf(doc[i]) != -1) {
                return true;
            }
        }
        return false;
    }
	/**
	 * spreadjs 文件上传  扩展自 jquery fileupload
	 * 		  
	 */
	//上传文件按钮
	var uploadButton = $('<button type="button"/>').addClass('btn btn-primary upload').prop('disabled', true).text('正在上传...').on(
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
		cancelButton = $('<button type="button"/>').addClass('btn btn-danger cancel').text('取消').on('click', function() {
			var $this = $(this),
				data = $this.data();
			$this.closest('div').remove();
			data.abort();
		}),
		removeButton1 = $('<button type="button"/>').addClass('btn btn-danger delete').text('删除').on('click', function() {
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

						//删除上传的文件信息
						delete opts.filesList[$this.attr("fileid")];
					},
					error: function(d) {
						alert("删除失败");
					}
				});
			}
		}),
		removeButton2 = $('<button type="button"/>').addClass('btn delete')
			.css({"background-color":"white","color":"red","border-color":"white","margin-top": "-18px"})
			.append('<i class="fa fa-remove"></i>').on('click', function() {
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

						//删除上传的文件信息
						delete opts.filesList[$this.attr("fileid")];
					},
					error: function(d) {
						alert("删除失败");
					}
				});
			}
		}),
		//文件上传
		fileuploadadd = function(e, data) {
			var opts = $(e.target).parents(".mt-sp-jqfileupload").data(plugin).opts;
			
			//var $mtspFiles = $(o).find('.mt-sp-files');
			var $mtspFiles = $(e.target).parents(".mt-sp-jqfileupload").find(".mt-sp-files");
			if(opts && opts.coverable && $mtspFiles.children()[0]){
				//若是覆盖上传，且已经上传过，只是覆盖。
				$mtspFiles.empty();
			}

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
			var $spfileupload = $(this).parent().parent(),
				pluginData = $spfileupload.data(plugin),
				option = pluginData.opts;
				pluginData.opts && pluginData.opts.uploadSuccessCallback.call($spfileupload,e,data);
			$.each(data.result, function(index, file) {
				if (file.error) {
					var error = $('<span class="text-danger"/>').text(file.error);
					$(data.context.children()[index]).append('<br>').append(error);
				} else {
					//记录上传的文件信息
					option.filesList[file.id] = file;

					if(option.events && $.isFunction(option.events.onUploadSuccess)){
						option.events.onUploadSuccess(data);
					}
					var $tg = $(data.context.children()[index]);
					$tg.find('button.cancel').remove();
					$tg.find('button.upload').remove();
					
					//保存当前文件的ID
					option.uploadFieldId = file.id;
					//预览模式
					if(option.previewable){
						var path = "";
						var lastfileName = file.fileName.substring(file.fileName.lastIndexOf("."));
						if (fileUpload_fileTypes(lastfileName, "doc")) {
					        path = contextPath + "/mx/form/defined/viewFile";
					        //path = contextPath + "/mx/form/defined/viewFile?id=";
					    } else if (fileUpload_fileTypes(lastfileName, "tif")) {
					        path = contextPath + "/mx/form/defined/viewTif";
					    } else if (fileUpload_fileTypes(lastfileName, "pdf")) {
					        path = contextPath + "/mx/form/defined/viewPdf";
					    } else if (fileUpload_fileTypes(lastfileName, "txt")) {
					        path = contextPath + "/mx/form/defined/viewText"
					    }else{
					    	path = contextPath + "/mx/form/defined/viewImg";
					    }
						$tg.wrap($('<a>').attr('target', '_blank').prop('href', path +"?id="+ file.id));	
					}else{
						//普通模式
						$tg.wrap($('<a>').attr('target', '_blank').prop('href', data.dowmloadUrl +"&id="+ file.id));	
					}
					
					var removeButton = $spfileupload.hasClass("horizontal")?removeButton2 : removeButton1;
					var $file = $tg.parent().parent();
					$file.append(removeButton.clone(true).data(data).attr("removeUrl", option.removeUrl + "&id=" + file.id + "&fileNames=&randomParent=" + file.parent).attr("fileId",file.id));
					if(!$spfileupload.hasClass("horizontal")){
						var str = "";
						if(file.fileSize < 1024){
							str = file.fileSize+"字节"
						}else if(file.fileSize < 1024*1024){
							str = (file.fileSize/1024).toFixed(2)+"KB";
						}else{
							str = (file.fileSize/1024/1024).toFixed(2)+"MB";
						}
						$file.append("<span style='color:#e73d4a'>"+str+"</span>");
					}
					//$(o).data('saveId', file.parent);
				}

			});
			
		},
		cutimagesFunc = function(files, cutWidth, cutQuality,callback){
			var k = 0;
			var URL = window.URL || window.webkitURL;
			var files = files;
			var newFiles = [];
			$.each(files,function(i,item){
				if(item.type.indexOf("image") == 0){
					//为图片时，进行压缩
					k++;
					//获取二进制数据
            		var blob = URL.createObjectURL(item);
					cutimagefunc(blob,cutWidth,cutQuality,function(data){
						k--;
						newFiles.push(new File([data.clearBase64], item.name+"?base64"))
						// newFiles.push(new File(["data:image/jpeg;base64," + data.clearBase64], item.name+"?base64"))
						if(k == 0){
							callback(newFiles);
						}
					})
				}else{
					newFiles.push(item);
				}
				
			})
		},
		/**
		 * 对图片进行压缩跳转
		 * @param  {[type]} blob       [图片二进制数据]
		 * @param  {[type]} cutWidth   [压缩宽度]
		 * @param  {[type]} cutQuality [压缩质量（0-1）]
		 * @return {[type]}            [description]
		 */
		cutimagefunc = function(blob, cutWidth, cutQuality,callback){
			var img = new Image();
            img.src = blob;
            img.onload = function() {
            	var that = this;
                //生成比例
                var w = that.width, h = that.height, scale = w / h;
                w = cutWidth || w;
                h = w / scale;
                //生成canvas
                var canvas = document.createElement('canvas');
                var ctx = canvas.getContext('2d');
                $(canvas).attr({
                    width : w,
                    height : h
                });
                ctx.drawImage(that, 0, 0, w, h);
                /**
                 * 生成base64
                 * 兼容修复移动设备需要引入mobileBUGFix.js
                 */
                var base64 = canvas.toDataURL('image/jpeg', cutQuality || 0.8);
                // 修复IOS
                if (navigator.userAgent.match(/iphone/i)) {
                    var mpImg = new MegaPixImage(img);
                    mpImg.render(canvas, {
                        maxWidth : w,
                        maxHeight : h,
                        quality : cutQuality || 0.8
                    });
                    base64 = canvas.toDataURL('image/jpeg', cutQuality || 0.8);
                }
                // 修复android
                if (navigator.userAgent.match(/Android/i)) {
                    var encoder = new JPEGEncoder();
                    base64 = encoder.encode(ctx.getImageData(0, 0, w, h),
                            cutQuality * 100 || 80);
                }
                // 生成结果
                var result = {
                    base64 : base64,
                    clearBase64 : base64.substr(base64.indexOf(',') + 1)
                };

                // 执行后函数
                callback(result);
            }
		},
		//方法集合
		methods = {
			init: function(options) {
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
					if(!opts.showFileList){   //是否显示文件列表
						$(o).find("div.mt-sp-files").hide();
					}

					opts.fileNumber = opts.fileNumber || 10;	//限制文件上传数量

					var that = this;
					opts.filesList = {};	//用于记录已上传的文件信息
					//覆盖保存时，将ID上传。
					var coverUpload = function(Event){
						var lastFieldId = opts.uploadFieldId;	//获取上次上传的文件ID
						if(opts.coverable && lastFieldId){
							//若是覆盖上传，且已经上传过文件（拥有fieldId）
							var option = $(that).find(".mt-sp-fileupload").fileupload("option");	
							var url = option.url;	//获取上传的路径
							url = opts.url + "&fieldId="+lastFieldId;	//组合成新的上传路径,带上fieldId
							$(that).find(".mt-sp-fileupload").fileupload("option","url",url);	//赋值真正路径
						}
					}
					$(that).find(".mt-sp-files").css("width",opts.width);
					$(that).find(".mt-sp-files").css("overflow-y","auto");
					$(that).find(".mt-sp-files").css("overflow-x","auto");
					$(that).find(".mt-sp-files").css("height",opts.height);

					var maxSizeMB = opts.maxFileSize || 209715200;
					maxSizeMB = (maxSizeMB/1024/1024).toFixed(2);
					//增加最大数量限制提示
					if($(that).hasClass("horizontal")){
						$(that).append("<div class='maxSizeSpan'>大小限制:"+maxSizeMB+" M</div>");
					}else{
						$(that).find(".progress").before("<div class='maxSizeSpan'>大小限制:"+maxSizeMB+" M</div>");
					}
					opts.add = function (e, data) {
			            if (e.isDefaultPrevented()) {
			                return false;
			            }
			            if (data.autoUpload || (data.autoUpload !== false &&
			                    $(this).fileupload('option', 'autoUpload'))) {
			            	var fileextensionvalue = opts.fileextensionvalue;

			            	var pattern = "";
			            	//获取文件最大大小
			            	var maxSize = opts.maxFileSize || 209715200;	//200MB
			            	if(maxSize > 209715200)
			            		maxSize = 209715200;

			            	if(fileextensionvalue && fileextensionvalue != "''"){
			            		$.each(fileextensionvalue,function(i,item){
			            			pattern += item.value.replace(/;/g,"|");
			            			pattern += "|";
			            		})
			            		pattern.substring(0,pattern.length-1);
				            	
				            	regx = new RegExp(pattern);

				            	var istrueformat = true;
				            	var istruesize = true;
				            	var totalsize = 0;
				            	var fileNumber = 0;
				            	
				            	$.each(opts.filesList,function(k,ktem){
				            		fileNumber++;
				            		totalsize += ktem.fileSize;	//计算已上传的文件总大小
				            	})
				            	//遍历文件判断是否符合文件格式
				            	$.each(data.originalFiles,function(i,item){
				            		var fileObj = item;
					            	var type = fileObj.type;
					            	var filename = fileObj.name;
					            	var fileSize = fileObj.size;
					            	var lastType = filename.substring(filename.lastIndexOf("."),filename.length);

					            	totalsize += fileSize;	//计算总上传大小;
					            	var rs = regx.exec(lastType);
					            	if(!rs || !rs[0]){
					            		var error = $('<span class="text-danger"/>').text("文件的格式不允许上传!");
										$(data.context.children()[0]).append('<br>').append(error);
					            		istrueformat = false;
					            		return false;
					            	}

					            	if(fileSize >= maxSize - 512){
					        			var error = $('<span class="text-danger"/>').text("上传的文件大小超过限制，禁止上传!");
										$(data.context.children()[0]).append('<br>').append(error);
					        			istruesize = false;
					        			return false;
					        		}
					        		fileNumber++;
				            	})

				            	if(!istrueformat){
				            		alert("文件的格式不允许上传!");
				            		return false;
				            	}
				            	if(!istruesize){
				            		alert("上传的文件大小超过限制，禁止上传!");
				            		return false;
				            	}
				            	if((opts.maxtotalsize-512) < totalsize){
				            		//上传总量大于限制，不允许上传
				            		var error = $('<span class="text-danger"/>').text("上传的文件总大小（总量）超过限制，禁止上传!");
									$(data.context.children()[0]).append('<br>').append(error);
				        			return false;
				            	}
				            	if(opts.fileNumber < fileNumber){
				            		//上传总数量大于限制，不允许上传
				            		var error = $('<span class="text-danger"/>').text("上传的文件总数量（总数量）超过限制，禁止上传!");
									$(data.context.children()[0]).append('<br>').append(error);
				        			return false;
				            	}
			            	}
			            	if(opts.compressable && opts.cutwidth && opts.cutquality){
			            		//对图片进行压缩
				            	cutimagesFunc(data.originalFiles,opts.cutwidth,opts.cutquality,function(files){
				            		data.files = files;

					                data.process().done(function () {
					                    data.submit();
					                });
				            	});
			            	}else{
			            		data.process().done(function () { 
				                    data.submit();
				                });
			            	}
			            }
			        }
					
					$(o).data("saveId", opts.randomParent).find(".mt-sp-fileupload").on("click",coverUpload)
						.fileupload(opts)
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
						// $(o).find("div.mt-sp-files").toggle();
					});
				});
			},
			hasFiles : function(){
				var $this = $(this);
				var option = $(this).data(plugin).opts;
				return option && option.filesList && !$.isEmptyObject(option.filesList);
			},
			setValue: function(value) {
				//还原 数据
				var $this = $(this),
					pluginData = $(this).data(plugin),
					opts = pluginData.opts,
					$filesWrap = $(this).find(".mt-sp-files"),
					$fileupload = $(this).find(".mt-sp-fileupload");
					
				if(value == null || value == ""){
					opts.randomParent = new UUID().createUUID();
					value = opts.randomParent;
				}

				$this.data('saveId', value);
				opts.randomParent=value;
				opts.url= opts.baseUrl+value;
				//$fileupload.fileupload("destroy");
				//methods.init.apply(this, opts);
					
				var option = $fileupload.fileupload('option');
				$fileupload.fileupload(opts);
				$.ajax({
					url: opts.getUrl+value + '&_'+(new Date().getTime()),
					context: $this[0]
				}).always(function() {
					$this.removeClass('fileupload-processing');
				}).done(function(result) {
					//清空以前的文件列表
					$this.find(".mt-sp-files").empty();

					option.context = $('<div/>').appendTo($filesWrap);
					//记录已上传的文件列表信息
					opts.filesList = {};
					$.each(JSON.parse(result), function(index, file) {
						var removeButton = $this.hasClass("horizontal")?removeButton2 : removeButton1;
						var $tg = $('<span/>').text(file.fileName),$a;
							
						
						if(option.previewable){
							var path = "";
							var lastfileName = file.fileName.substring(file.fileName.lastIndexOf("."));
							if (fileUpload_fileTypes(lastfileName, "doc")) {
						        path = contextPath + "/mx/form/defined/viewFile";
						        //path = contextPath + "/mx/form/defined/viewFile?id=";
						    } else if (fileUpload_fileTypes(lastfileName, "tif")) {
						        path = contextPath + "/mx/form/defined/viewTif";
						    } else if (fileUpload_fileTypes(lastfileName, "pdf")) {
						        path = contextPath + "/mx/form/defined/viewPdf";
						    } else if (fileUpload_fileTypes(lastfileName, "txt")) {
						        path = contextPath + "/mx/form/defined/viewText"
						    }else{
						    	path = contextPath + "/mx/form/defined/viewImg";
						    }
						    $a = $('<a>').attr('target', '_blank').prop('href',  path +"?id="+ file.id).append($tg);
						}else{
							$a = $('<a>').attr('target', '_blank').prop('href', option.dowmloadUrl + "&id=" + file.id).append($tg);
						}
						var node = $('<div>').append($a),
							$rBtn = removeButton.clone(true).data(option).attr("removeUrl", option.removeUrl + "&id=" + file.id + "&fileNames=&randomParent=" + file.parent).attr("fileId",file.id);
						node.append($rBtn);
						if($this.attr("disabled")){
							$rBtn.attr("disabled","disabled");
						}
						node.appendTo(option.context);

						opts.filesList[file.id] = file;	//记录已上传的文件列表信息
						opts.uploadFieldId = file.id; //纪录上一次的ID信息
					});

					var readOnlyFlag = $this.data("readOnly");
					if(readOnlyFlag == null){
						readOnlyFlag = opts.readOnly;
					}
					// var $fileLists = $this.find("div.mt-sp-files");
					if(readOnlyFlag){  //是否只读
						$this.spFileUpload("setReadOnly");
						// $fileLists.find("button").remove();
						// $this.html($fileLists);
					}
					//$fileupload.fileupload(option);
					
					// $(this).find(".mt-sp-fileupload").fileupload('option', 'done').call($(this).find(".mt-sp-fileupload"), $.Event('done'), {result: JSON.parse(result)});
				});
			},
			//设置只读状态，不能上传
			setReadOnly:function(){
				var $this = $(this);
				$this.find(".fileinput-button,.progress").hide();
				var $fileLists = $this.find("div.mt-sp-files");
				$fileLists.find("button").hide();
				$this.data("readOnly",true);
			},
			//取消只读状态。
			removeReadOnly:function(){
				var $this = $(this);
				$this.find(".fileinput-button,.progress").show();
				var $fileLists = $this.find("div.mt-sp-files");
				$fileLists.find("button").show();
				$this.data("readOnly",false);
			},
			disabledDelete : function(){
				var $this = $(this);
				var $fileLists = $this.find("div.mt-sp-files");
				$fileLists.find("button").hide();
			},
			removeDisabledDelete : function(){
				var $this = $(this);
				var $fileLists = $this.find("div.mt-sp-files");
				$fileLists.find("button").show();
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
			}
		};
	var plugin = "spFileUpload";
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
		baseUrl: '/glaf/mx/form/attachment?method=upload&to=to_db&randomParent=',
		url: '/glaf/mx/form/attachment?method=upload&to=to_db&randomParent=',
		dowmloadUrl: '/glaf/mx/form/attachment?method=download&from=to_db',
		removeUrl: '/glaf/mx/form/attachment?method=remove&from=to_db',
		getUrl: "/glaf/mx/form/attachment?method=getFilesByRandomParent&randomParent=",
		randomParent: '',
		dataType: 'json',
		autoUpload: false,
		acceptFileTypes: '',
		maxFileSize: 5242880,
		disableImageResize: /Android(?!.*Chrome)|Opera/.test(window.navigator.userAgent),
		previewMaxWidth: 100,
		previewMaxHeight: 100,
		previewCrop: true,
		limitMultiFileUploadSize: 512,	//文件大小限制,200MB = 209715200bytes
		uploadSuccessCallback:function(e,data){},
		deleteCallback:function(){},
		messages: {
            uploadedBytes: '上传的文件大小超过200MB，禁止上传!',
        },
        
	};

})(jQuery);