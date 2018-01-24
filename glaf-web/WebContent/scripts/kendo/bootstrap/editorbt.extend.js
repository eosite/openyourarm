var editorbtFunc = {
	init : function(rule, args) {
		var options = args[0];
		$("#" + rule.inid).summernote(options);	
		var editor = $("#" + rule.inid).next();
		editor[0].style.display = args[0].display;
	},
	getValue : function(rule, args) { //获取值
		var $id = pubsub.getJQObj(rule, true);
		return $id.code();
	},
	destroy : function(rule, args) {//删除dom
		var $id = pubsub.getJQObj(rule);
		$id.destroy();
	},
	tclear: function(rule, args) { //清空
		var $id = pubsub.getJQObj(rule);
		var jq = $id.next().find("div.note-editable.panel-body");
		if(jq){
			jq.html("");
		}
	},
	setValue : function(rule, args) { //获取值
		var $id = pubsub.getJQObj(rule),r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			$id.parents().find(".note-editable p").append(v);
		} else {
			$id.parents().find(".note-editable p").append(v);
		}
	},
};
pubsub.sub("editorbt", editorbtFunc);