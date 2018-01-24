/**
 * 图片上传控件
 */
var imageuploadFunc = {
	tdisabled: function(rule, args) { // 禁用
		var $id = pubsub.getJQObj(rule);
		$id.find('.imageupload_tools').hide();
	},
	tenabled: function(rule, args) { // 启用
		var $id = pubsub.getJQObj(rule);
		$id.find('.imageupload_tools').show();
	},
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule).hide();
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule).show();
	},
	delImg: function(e) {
		// 
		var $this = $(e).closest('.image_upload_div');
		var randomparent = $this.attr("randomparent");
		if (randomparent) {
			if (confirm("是否删除图片")) {
				var removeurl = $this.attr("removeurl");
				$.ajax({
					type: "post",
					url: removeurl,
					data: {
						id: randomparent
					},
					success: function(data) {
						alert("删除成功");
						$this.children('img').attr("src", "").hide();
						$this.attr("attachmentid", "");
						var outputName = $this.attr("outputName");
						if (outputName) {
							imageuploadFunc.setOutPutName(outputName, "");
						}
					},
					error: function(err) {
						alert("删除失败");
					}
				});
			}
		} else {
			alert("请先上传图片");
		}
	},
	setOutPutName: function(outputNames, names) {
		var opns = outputNames.split(",");
		for (var k = 0; k < opns.length; k++) {
			outputName = opns[k];
			if (outputName) {
				$("#" + outputName).val(names);
			}
		}
	},
	getRandomparent: function(rule){
		var $id = pubsub.getJQObj(rule, true);
		return $id.attr("randomparent");
	},
	getValue: function(rule){
		var $id = pubsub.getJQObj(rule, true);
		return $id.attr("attachmentId");
	},
	setImgPic:function(rule,args){
		
		var $elementObj = pubsub.getJQObj(rule),r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
		} else {
			v = args;
		}
		var $img = $elementObj.find(".imageupload_class");
		$img[0].onload = function() {
	        EXIF.getData($img[0], function() {
	            $(this).data("tags",EXIF.getAllTags(this));
	        });
	    };
		var url = '/glaf/mx/form/imageUpload?method=downloadById&_'+(new Date().getTime())+'&id='+v;
		$elementObj.attr("attachmentid",v);
		$img.attr(
			"src",
			url)
		.show();
	},
	setRandomParent:function(rule,args){
		
		var $elementObj = pubsub.getJQObj(rule),r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
		} else {
			v = args;
		}
		var $img = $elementObj.find(".imageupload_class");
		if(v == null || v == ""){
			$img.hide();
			var randomUUID = new UUID().createUUID();
			$elementObj.attr("randomparent",randomUUID);
			$elementObj.removeAttr("attachmentId");
			return ;
		}
		$elementObj.attr("randomparent",v);
		// $img[0].onload = function() {
	 //        EXIF.getData($img[0], function() {
	 //            $(this).data("tags",EXIF.getAllTags(this));
	 //        });
	 //    };
		//8243d27f19e041df8d8225261a788c0b
		var downloadUrl = $elementObj.attr("downloadUrl");
		$img[0].onload = function() {
	        EXIF.getData($img[0], function() {
	            $(this).data("tags",EXIF.getAllTags(this));
	        });
	    };
		$img.attr("src",  downloadUrl + "&_"+(new Date().getTime())+"&randomparent=" + v).show();
	},
	getBasePicData: function(rule,args){
		var $id = pubsub.getJQObj(rule, true);
		var tags = $id.find(".imageupload_class").data("tags");
		return tags[rule.columnName];
	},
	clear : function(rule,args){
		var $elementObj = pubsub.getJQObj(rule),r, v = "";
		var $img = $elementObj.find(".imageupload_class");
		$img.hide();
		var randomUUID = new UUID().createUUID();
		$elementObj.attr("randomparent",randomUUID);
		$elementObj.attr("attachmentId","");
		// $elementObj.removeAttr("attachmentId");
	}
};
pubsub.sub("imageupload", imageuploadFunc);


//图片上传
function initImageUploadFunc() {

	$("[data-role=imageupload]").each(function() {
		initImageUpload(this, this.id, $(this).attr("randomparent"));
	});
	$('body').delegate('.clickViewImg', 'click', zoomViewImg);
	$('body').delegate('.dblclickViewImg', 'dblclick',
		zoomViewImg);
	// imageUploadInitFunc();
}
function initImageUpload(elementObj, elementId, randomUUID) {
	if (elementObj) {
		var flag = true;
		if (!randomUUID) { //
			randomUUID = new UUID().createUUID();
			flag = false;
		}
		elementObj.setAttribute("randomparent", randomUUID);

		var downloadUrl = $(elementObj).attr("downloadUrl"),
			$img = $(
				"#" + elementId).find(".imageupload_class");
		$img.attr("src", downloadUrl + "&_"+(new Date().getTime())+"&randomparent=" + randomUUID);
		if($img[0]){
			$img[0].onload = function() {
		        EXIF.getData($img[0], function() {
		            $(this).data("tags",EXIF.getAllTags(this));
		        });
		    };
		}
	    
		if (flag) {
			$img.show();
		}
	}
}