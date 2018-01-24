/**
 * 检查参数关系
 * @param  {[object]}   param    [输入参数] 格式
 *                             {
 *                             		saveId : "", // 这个是控件保存的规则ID form_rule 表的id_  可选
 *                             		param: JSON.stringify({
 *                             			param:"",	// 参数名称 例如 col12312 必选
 *                             			databaseId:"",// 数据集id  可选
 *                             		})
 *                             }
 * @param  {Function} callback [回调函数 用来执行回调]
 * @return {[void]}            []
 */
function checkParamRelation(param, callback) {

	var width = $(window).width(),
		height = $(window).height(),
		moduleName = "mt_loadding_div",
		$module = $("<div id=\"" + moduleName + "\" style=\"position: absolute;top: 0;left: 0;background-color: RGBA(0,0,0,0.3);z-index: 99999;\"></div>").width(width + "px").height(height + "px"),
		$lodding = $("<div style=\"position: absolute;top: 50%;right: 50%;color:white;font-size:30px;\">正在检查...</div>"),
		$showContent = $("<div style=\"position: absolute;top: 20%;left: 30%;color:white;font-size:20px;\"><div>该参数被以下页面引用，确定要删除吗？(试用)<div></div>"),
		$buttons = $("<div style=\"text-align:center;padding-top: 10px;\"><input class='mt_sure' type=\"button\" value=\"确定\"> <input  class='mt_cancel' type=\"button\" value=\"取消\"></div>");
	$module.clone().append($lodding).appendTo("body");

	function removeLoadding() {
		$("#" + moduleName).remove();
	};

	function buildTable(datas, callback) {
		var tablehtml = ["<table  border=\"1\" cellspacing=\"0\" >"];
		tablehtml.push("<tr style=\"background-color:#84d06a;\"><td>页面名称</td><td>控件/页面名称</td><td>来自数据集</td></tr>");
		$.each(datas, function(i, data) {
			tablehtml.push("<tr>");
			tablehtml.push("<td>" + "<a href=\"#\" onclick=\"window.open('" + (window.contextPath || "/glaf") + "/mx/form/defined/ex/list?selectPageId=" + data.pageId + "&selectPageName=" + encodeURI(data.pageName) + "')\">" + data.pageName + "</a></td>");
			tablehtml.push("<td>" + data.widgetName + "</td>");
			tablehtml.push("<td>" + (data.databaseId ? "<a href=\"#\" onclick=\"window.open('" + (window.contextPath || "/glaf") + "/mx/dataset/sqlbuilder?id=" + data.databaseId + "')\">" + data.databaseName + "<a>" : "") + "</td>");
			tablehtml.push("</tr>");
		})
		tablehtml.push("</table>");
		var $tablehtml = $(tablehtml.join(""));
		$tablehtml.find("td").css({
			padding: "5px"
		});
		$showContent.append($tablehtml).append($buttons);
		$buttons.find(".mt_sure").on("click", function() {
			callback();
		});
		$buttons.find(".mt_cancel").on("click", function() {
			removeLoadding();
		});
		$module.clone().append($showContent).appendTo("body");
	};
	$.ajax({
		url: contextPath + "/mx/form/relationCheck/param",
		data: param,
		success: function(data) {
			removeLoadding();
			console.log(data);
			if (data && (data = JSON.parse(data)) && data.length) {
				buildTable(data, callback);
			} else {
				callback();
			}
		},
		error: function() {
			removeLoadding();
			callback();
		}
	});
}