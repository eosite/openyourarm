/**
 * jquery baidu editor plugin
 * 
 */
(function($,ue) {
	var plugin = 'editor';
	function create(target,param) {
		var state = $.data(target, plugin), visible = $(target).data(plugin).options.visible;
		// state.target = $.fn.zTree.init($(target), state.options);
		state.target = ue.getEditor(target.id, state.options);
		if (visible != undefined && !visible) {
			$(target).css("display", "none");
		}
		if (typeof param == 'function') {
			state.target.ready(function() {
				param.call(this, target);
			});
		}
	}
	$.fn[plugin] = function(options, param) {

		if (typeof options == 'string') {
			return $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i, o) {
			if(!o.id)
				return;
			var state = $.data(o, plugin);
			if (state) {
				$.extend(true, state.options, options);
			} else {
				$.data(o, plugin, {
					options : $.extend(true, {}, $.fn[plugin].defaults, options),
					target : null
				});
			}
			create(o,param);
		});

	};

	$.fn[plugin].methods = {
		getEditor : function(jq) {
			return $.data(jq[0], plugin).target;
		},
		setContent : function(jq,content){
			this.getEditor(jq).setContent(content);
		},
		getContent : function(jq){
			return this.getEditor(jq).getContent();
		},
		getContentTxt : function(jq){
			return this.getEditor(jq).getContentTxt();
		},
		options : function(jq) {
			return $.data(jq[0], plugin).options;
		}
	};

	$.fn[plugin].defaults = $.extend(window.UEDITOR_CONFIG, {
		toolbars : [
		[ 'fullscreen', 'emotion','simpleupload', 'source', 'undo', 'redo', 'bold', 'italic',
				'underline', 'fontborder', 'backcolor', '|',
	            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|','fontsize',
				'fontfamily', 'justifyleft', 'justifyright', 'justifycenter',
				'justifyjustify', 'strikethrough', 'superscript', 'subscript',
				'removeformat', 'formatmatch', 'autotypeset', 'blockquote',
				'pasteplain', '|', 'forecolor', 'backcolor',
				'insertorderedlist', 'insertunorderedlist', 'selectall',
				'cleardoc', /*'link', 'unlink',*/  'help' ]
		], 
		serverUrl: (window.PROJECT_CONTEXT || (contextPath + "/mx/")) + "ueditor/dispatch"
	});
	
})(jQuery,UE);
