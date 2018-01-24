! function($) {
	var plugin = 'imagesext';

	var Build = function(target, state) {
		this.target = target;
		this.state = state;
		this.options = state.options;
		this._init();
	};
	$.parser.plugins.push(plugin);
	$.fnInit(plugin, function(target) {

		var state = $.data(target, plugin + '.options');
		var columns = $(target).data("columns");
		if (columns && columns.length > 0) {
			state.options.columns = eval('(' + columns + ')');
		}
		var p = new Build(target, state);

		$.data(target, plugin, p);
	});

	//method=remove&from=to_dir&id=0adfe7bf1c904223b9e32986547d0ccb&fileNames=&randomParent=C7B7FC3F-7BC0-0001-403B-E2261AA0AA20
	$.fn[plugin].defaults = {
		getUrl: contextPath + "/mx/form/attachment?method=getFilesByRandomParent&randomParent=",
		uploadUrl : contextPath + '/mx/form/attachment?method=upload',
		getImageUrl : contextPath + '/mx/form/imageUpload?method=downloadById',
		deleteUrl : contextPath + '/mx/form/attachment?method=remove',
		events :{
			initEnd : function(){

			}
		}
	};

	$.fn[plugin].methods = {
		_init : function(){
			var that = this;
			var options = that.options;
			var $target = $(this.target);

			//是否显示
			that.visible = options.visible;
			that.visible || $target.hide();

			//保存图片方式（数据库或文件）;
			that.saveoperation = that.options.saveoperation || 'to_db';
			if(that.saveoperation){
				that.options.uploadUrl += '&to='+that.saveoperation;
				that.options.deleteUrl += '&from='+that.saveoperation;
			}
			that.options.uploadUrl += '&randomParent=';
			that.options.deleteUrl += '&fileNames=';

			//获取附件信息
			var params = options.params;
			var randomParent;
			$.each(params,function(k,value){
				randomParent = value;
			})
			//附件信息
			that.randomParent = randomParent || that.options.randomParent;

			//最大上传数量
			that.maxnum = that.options.maxnum;
			that.nownum = 0;//当前上传数量

			//是否高度自动
			that.heightauto = options.heightauto;
			//获取每行个数
			that.colnum = options.colnum;
			//获取每行高度
			if(options.rowheight){
				that.rowheight = options.rowheight + "px";	
			}
			if(options.colnum){
				that.rowWidth = (100/options.colnum) + "%";	
			}
			if(options.rowwidth){
				that.rowWidth = options.rowwidth + "px";		
			}
			if(options.paddingvalue){
				that.paddingvalue = options.paddingvalue + "px";	
			}
			
		
			//图片上传按钮
			var $z_photo = $('<div class="z_photo"></div>');
			var $imgCell = that.createImgCell(); 
			var $z_file = $('<div class="z_file"></div>');
			if(that.heightauto){
				$target.css("height","auto");
				$z_photo.css("height","auto");
			}
			
			var $form = $('<form method="post" class="imagesext-form" enctype="multipart/form-data"></form>');
			$imgCell.append($z_file);
			$imgCell.append('<input type="file" name="file" class="pfile" accept="image/*" multiple=""  />');
			$form.append($imgCell);
			$z_photo.append($form);
			$target.append($z_photo);

			//加遮罩层
			var markstr = '<div class="z_mask">'
							+ '<div class="z_alert">'
							+ '<p>确定要删除这张图片吗？</p>'
							+ '<p>'
							+ '<span class="z_cancel">取消</span>'
							+ '<span class="z_sure">确定</span>'
							+ '</p></div></div></div>';
			var $mark = $(markstr);
			$target.append($mark);

			that._bindEvent();

			if(that.randomParent){
				that.setParentId(that.randomParent);
			}

			that.setAble(that.options.enabled);
		},
		createImgCell : function(){
			var that = this;
			var $imgCell = $('<div class="imgCell"></div>'); 
			if(that.rowWidth){
				$imgCell.width(that.rowWidth);
			}
			if(that.rowheight){
				$imgCell.height(that.rowheight);
			}
			if(that.paddingvalue){
				$imgCell.css({
					"padding-right":that.paddingvalue,
					"padding-bottom":that.paddingvalue,
				});
			}
			return $imgCell;
		},
		setAble : function(enabled){
			var that = this;
			var $target = $(that.target);
			that.able = enabled;
			
			if(enabled){
				$target.find(".z_file").show();
				$target.find(".clickViewImg").remove("clickViewImg");
			}else{
				$target.find(".z_file").hide();
				$target.find(".z_addImg img").addClass("clickViewImg");
			}
		},
		_bindEvent: function() {
			var that = this;
			var $target = $(that.target);

			$target.on("change",".pfile",function(event){
				that.imgChange($(this).closest('.z_photo'),$(this).closest('.z_file'));
			})

			var $mask = $target.find(".z_mask");
			$target.on("click",".z_addImg",function(event){
				if(that.able){
					if(that.maxnum && that.nownum > that.maxnum){
						//超过最大上传数据量，不允许上传
						alert("超过最大上传数据量，不允许上传!");
						return;
					}
					that.$nowAddImg = $(this);
					$mask.show();
				}else{
					//放大预览
				}
			})
			
			$target.on("click",".z_cancel",function(event){
				$mask.hide();
			})

			$target.on("click",".z_sure",function(event){
				if(that.able){
					$mask.hide();
					that.imgRemove(that.$nowAddImg);
				}
			})
		},
		getRandomparent:function(){
			var that = this;
			return that.randomParent;
		},
		setParentId : function(value){
			var that= this;
			that.randomParent = value;
			var $target = $(that.target);
			var options = that.options;
			var $imgContainer = $target.find(".z_photo");
			$imgContainer.find(".z_addImg").remove();
			$.ajax({
				url: options.getUrl+value + '&_'+(new Date().getTime()),
				context: $target[0]
			}).done(function(result) {
				that.nownum =0;
				$.each(JSON.parse(result), function(index, file) {
					that.nownum++;
					var $imgAdd = that.createImgCell();
            		$imgAdd.addClass("z_addImg");
            		//存放图片的父级元素
            		$imgContainer.append($imgAdd);

            		var fileid = file.id;
					$imgAdd.data("fileid",fileid);
					var $img = $("<img>");
					that.able || $img.addClass("clickViewImg");
					var src = that.options.getImageUrl + '&_'+(new Date().getTime())+'&id='+fileid;
	                $img.attr("src", src);
	                $imgAdd.append($img);
				});
			}).complete(function() {
			})

		},
		imgChange : function($imgContainer, $input) {
			var that = this;
			var $target = $(that.target);
			var $file = $target.find(".pfile");
            //获取点击的文本框
            var file =  $file[0];
            //获取的图片文件
            var fileList = file.files;

            var $imgAdd = that.createImgCell();
            var $orgimgAdd = $imgAdd;
            $imgAdd.addClass("z_addImg");
            var $loaddingDiv = that.createImgCell();

            var loaddingsrc = contextPath + '/images/ajax-loading.gif';
            var $loaddingImg = $("<img src= '"+loaddingsrc+"'>");
            $loaddingDiv.append($loaddingImg);
            // $loaddingDiv.append("上传中...");

            $imgContainer.append($loaddingDiv);
            //存放图片的父级元素
            $imgContainer.append($imgAdd);


            var url = that.options.uploadUrl + that.randomParent;
            var $form = $target.find("form");
            var formData = new FormData($form[0]);

            $.ajax({
			    url: url,
			    type: 'POST',
			    cache: false,
			    data: formData,
			    processData: false,
			    contentType: false
			}).done(function(res) {
				if(typeof res == 'string'){
					res = JSON.parse(res);
				}

				$.each(res,function(k,ktem){
					that.nownum++;
					if(k>0){
						$imgAdd = that.createImgCell();
			            $imgAdd.addClass("z_addImg");
			            $imgContainer.append($imgAdd);
					}

					var fileid = ktem.id;
					$imgAdd.data("fileid",fileid);
					var $img = $("<img>");
					that.able || $img.addClass("clickViewImg");
					var src = that.options.getImageUrl + '&_'+(new Date().getTime())+'&id='+fileid;
	                $img.attr("src", src);
	                $imgAdd.append($img);
				});
			}).fail(function(res) {
				alert("上传失败!");
				$orgimgAdd.remove();
			}).complete(function(){
				$loaddingDiv.remove();
			});
            // imgRemove();
        },
        imgRemove : function($nowAddImg) {
        	var that = this;
        	var $target = $(that.target);


        	var nowFileId = $nowAddImg.data("fileid");
        	var url = that.options.deleteUrl +"&id="+ nowFileId;

        	var $img = $nowAddImg.find("img");
        	$img.hide();

        	var loaddingsrc = contextPath + '/images/ajax-loading.gif';
            var $loaddingImg = $("<img src= '"+loaddingsrc+"'>");
            $nowAddImg.append($loaddingImg);

        	$.ajax({
			    url: url,
			    dataType:"json",
			}).done(function(res) {
				$nowAddImg.remove();
			}).fail(function(res) {
				alert("删除失败!");
				$img.show();
			}).complete(function(){
				$loaddingImg.remove();
			});
        }
	}

	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor: Build
	});

}(jQuery);

(function(doc, win) {
    var docEl = doc.documentElement,
        resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
        recalc = function() {
            var clientWidth = docEl.clientWidth;
            if (!clientWidth) return;
            if (clientWidth >= 640) {
                docEl.style.fontSize = '100px';
            } else {
                docEl.style.fontSize = 100 * (clientWidth / 640) + 'px';
            }
        };

    if (!doc.addEventListener) return;
    win.addEventListener(resizeEvt, recalc, false);
    doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);
