/**
 * 报表使用
 */
GcSpreadStyle.add("report", '报表设计器', function() {
	var offset = 220;
	
	var $fillSpreadContent = $(".fill-spread-content").css({
		left : offset,
		right : offset + 20
	}).before("<div id='ztree-01' ></div>");

	var dataFilter = function(treeId, parentNode, ret) {
		var rows = [], tmpId = 0;
		if (ret.parentRows) {
			$.each(ret.parentRows, function(i, v) {
				v.icon = CONFIG.contextPath + '/images/home.png';
				rows.push(v);
			});
		}
		$.each(ret.rows, function(i, v) {
			if (v.pId == 0) {
				v.pId = tmpId;
			}
			rows.push(v);
		});
		return rows;
	};

	var setting = {
		async : {
			url : CONFIG.contextPath
					+ "/mx/dep/report/depReportCategory/json?rows=999999",
			dataFilter : dataFilter
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
			}
		}
	};

	$("#ztree-01").attr({
		'class' : 'ztree'
	}).css({
		width : offset - 10,
		'overflow-y' : 'scroll'
	}).ztree(setting);

}, {
	resize : function() {
		var st, resize = function() {
			$("#ztree-01").css({
				height : ContentHeight() - 10
			});
			st = null;
		};
		$(window).resize(function() {
			st && window.clearTimeout(st);
			st = setTimeout(resize, 200);
		});
	}
});