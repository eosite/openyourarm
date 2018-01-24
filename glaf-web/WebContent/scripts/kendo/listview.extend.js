/**
 * 列表视图
 */
var listviewFunc = {
	getRow: function(rule) { // 获取选中行的字段
		// var targer = jQuery('#' + rule.inid);
		var targer = pubsub.getJQObj(rule, true);
		var widget = kendo.widgetInstance(targer);
		var value = [];
		if (widget) {
			var rows = widget.select();
			for (var i = 0; i < rows.length; i++) {
				var data = widget.dataItem(rows[i]);
				value.push(data[rule.columnName]);
			}
		}
		if (targer.attr("localfile")=="true") {
			var rdata = widget.dataSource.transport.options.read.data,params,databaseId = 0;
			if(rdata.params){
				params = JSON.parse(rdata.params);
				databaseId = params.params || 0 ;
			}
			var ids = "" ;
			$.ajax({
				url: contextPath + "/mx/form/imageUpload?method=localFile&databaseId=" + databaseId + "&id=" + rdata.rid + "&rp=" + value.join(","),
				async : false ,
				dataType : 'json' ,
				success: function(ret) {
					console.log(ret);
					ids = ret.ids;
				}
			});
			return ids ;
		}
		return value.join(",");
	},
	linkage: function(rule, params) {
		gridFunc.linkageControl(rule, params);
	},
	linkageControl: function(id, params) {
		var targer = pubsub.getJQObj(id);
		var widget = kendo.widgetInstance(targer);
		var data = widget.dataSource.transport.options.read.data;
		data.params = JSON.stringify(params);
		widget.dataSource.read();
		// 传入创建父ID
		/*
		 * var createData = widget.dataSource.transport.options.create.data;
		 * createData.params = JSON.stringify(params);
		 */
	},
	grefresh: function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			var widget = kendo.widgetInstance($id);
			widget.dataSource.read();
		}
	},
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.closest('.listview_div').hide();
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.closest('.listview_div').show();
	},
};
pubsub.sub("listview", listviewFunc);