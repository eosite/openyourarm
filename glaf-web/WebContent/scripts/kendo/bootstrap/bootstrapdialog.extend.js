var bootstrapdialogFunc = {
	curwindow : function(id,args){
		var $id = pubsub.getJQObj(id);
		var param = {
			title : null,
			width : null,
			height : null
		};
		$.each(args,function(i,arg){
			switch (i) {
			case '标题':
				param.title = arg;
				break;
			case '宽度':
				param.width = arg;
				break;
			case '高度':
				param.height = arg;
				break;
			}
		});
		$id.bootstrapdialog("_open",param);
	},
	closeCurwindow : function(id,args){
		var $id = pubsub.getJQObj(id);
		$id.bootstrapdialog("_close");
	},
	hidden : function(id,args){
		var $id = pubsub.getJQObj(id);
		var build = $id.data("bootstrapdialog");
		build._hidden();
	},
	visible : function(id,args){
		var $id = pubsub.getJQObj(id);
		var build = $id.data("bootstrapdialog");
		build._visible();
	}
};
pubsub.sub("bootstrapdialog", bootstrapdialogFunc);
