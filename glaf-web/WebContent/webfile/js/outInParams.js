//初始化参数
function initExpressionFnByOutIn() {
	var $eve = mtxx.eventObj;
	var $this = $eve.parent("ul"),
		$td = $this.closest('td');
	var textbox = $td.find('.textbox-menu');
	return JSON.parse(textbox.data("item").data);
}

//返回表达式执行函数
function retExpressionByOutIn(data) {
	var $eve = mtxx.eventObj;
	var $this = $eve.parent("ul"),
		$td = $this.closest('td');
	var textbox = $td.find('.textbox-menu');
	var item = $eve.data("item");
	if (item) {
		//if(item.items){
		$td.find('ul') /*.hide()*/ ;
		var value = data.expVal;
		item.value = value;
		item.data = JSON.stringify(data);
		textbox.val(value).data("item", item).show();
		//}
	}
}

//获取参数
function getExpressionByOutIn() {
	var expressionData = [];
	var datas = mtxx.eventObj.data("item"),
		inparams = datas ? datas.items : [],
		inparam, i;
	if (mtxx.data && mtxx.data["menus"] && mtxx.data["menus"]["in"]) {
		inparams = mtxx.data["menus"]["in"];
	}
	if (inparams && inparams.length > 0) {
		for (i = 0; i < inparams.length; i++) {
			inparam = inparams[i];
			var express = {};
			express.id = inparam.items.pageId + inparam.id;
			express.name = inparam.text;
			getExpParamsByOutIn(inparam, express.id, expressionData);
			expressionData.push(express);
		}
	}
	return expressionData;
}

//获取表达式参数
function getExpParamsByOutIn(inParam, pid, expressionData) {
	var items = inParam.items;
	for (var j = 0; j < items.length; j++) {
		var param = items[j];
		var subexpress = {};
		subexpress.id = param.id ? (items.pageId + param.id) : (pid + "-" + j);
		if (subexpress.id == pid) {
			subexpress.id = subexpress.id + j;
		}
		subexpress.name = param.text;
		subexpress.pId = pid;
		subexpress.t = "";
		if (param.items) {
			getExpParamsByOutIn(param, subexpress.id, expressionData)
		} else {
			subexpress.pageId = items.pageId;
			subexpress.eleId = param.id;
			subexpress.fnType = param.type;
			subexpress.lev = items.lev;
			subexpress.otype = items.otype;
			subexpress.idataId = param.dataId;
			subexpress.idataType = param.dataType;
			subexpress.iEditor = param.oEditor;
			if (param.code) {
				subexpress.datasetId = param.datasetId;
				subexpress.columnName = param.columnName;
				subexpress.dType = param.FieldType;
				subexpress.code = param.code;
				subexpress.value = param.value;
				subexpress.isFn = false;
			} else {
				subexpress.columnName = param.columnName;
				subexpress.dType = "string";
				subexpress.code = "~F{" + items.pageId + "." + param.id + "." + param.type + "}";
				subexpress.value = "~F{" + inParam.text + "." + param.text + "}";
				subexpress.isFn = true;
			}
		}
		expressionData.push(subexpress);
	}
}

function getRets() {
	var rets = new Object(),
		fieldInput, paramInput;
	paramTable.find('tr').each(function(i, v) {
		fieldInput = $(this).find('input[name=field]');
		paramInput = $(this).find('input[name=param]');

		var paramItem = paramInput.data('item');
		var fieldItem = fieldInput.data('item');

		if (!paramItem || !fieldItem)
			return false;

		if (paramItem && !rets[paramItem.id]) {
			rets[paramItem.id] = new Array();
		}
		var o = {
			inparam: fieldItem.inparam,
			inid: fieldItem.id,
			inname: fieldItem.value,
			inlev: fieldItem.lev,
			inpage: fieldItem.pageId,
			itype: fieldItem.otype,
			icode: fieldItem.code,
			idatasetId: fieldItem.datasetId,
			columnName: fieldItem.columnName || '',
			outname: paramItem.value,
			outid: paramItem.id,
			outlev: paramItem.lev,
			outpage: paramItem.pageId,
			eventType: paramItem.eventType,
			type: fieldItem.type,
			param: paramItem.param,
			otype: paramItem.otype,
			srcUrl: paramItem.srcUrl,

			dataId: paramItem.dataId,
			dataType: paramItem.dataType,
			idataType: fieldItem.dataType,
			idataId: fieldItem.dataId,

			oEditor: paramItem.oEditor,
			iEditor: fieldItem.oEditor
		};
		if (fieldItem.cssClass) {
			//以下暂时不知道用来做什么的 先注释
			//o.items = fieldItem.items ;
			o.data = fieldItem.data;
			o.cssClass = fieldItem.cssClass;
		}
		rets[paramItem.id].push(o);
	});
	return rets;
}

function tableFunc(id) {

	this.menus = null;

	var $this = $('#' + id),
		K = this,
		$tr = [
			"<tr>",
			"<td class='td-cls' ><div style='display:flex;'>",
			"<div><ul class='in-menu conmenu' style='width:72px' ></ul></div>",
			"<input type='text' class='k-textbox textbox-menu' name='field' style='width:100%;'/>",
			"</div></td>",

			"<td class='td-cls' style='width:12px;'>=></td>",

			"<td class='td-cls' ><div style='display:flex;'>",
			" <div><ul class='out-menu conmenu' style='width:41px;' ></ul></div>",
			" <input type='text' class='k-textbox textbox-menu' name='param' style='width:100%' />",
			"</div></td>",

			"<td class='td-cls' style='width:40px;'><button class='k-button del-cls'>删除</button></td>",

			"</tr>"
		],
		eve = {
			//点击输入输出参数 赋值到输入框
			select: function(e) {
				var $eve = $(e.item);
				mtxx.eventObj = $eve;
				if ($eve.hasClass('exp')) {
					var _href = mtxx.contextPath + "/mx/expression/defined/view?category=front&retFn=retExpressionByOutIn" + "&getFn=getExpressionByOutIn&initExpFn=initExpressionFnByOutIn&notValidate=true";
					window.open(_href);
					return;
				}
				var $this = $eve.parent("ul"),
					$td = $this.closest('td');
				var textbox = $td.find('.textbox-menu');
				var item = $eve.data("item");
				if (item) {
					if (!item.items) {
						//$td.find('ul').hide();
						var t = [
								item.text
							],
							p = [],
							value;
						$(e.item).parents('li').each(function() {
							if (!$(this).parent('ul').hasClass("conmenu")) {
								var text = $(this).find('span:first').text();
								t.push(text);
							}
						});
						for (var i = t.length; i--;) {
							p.push(t[i]);
						}
						value = p.join('-->');
						item.value = value;
						textbox.val(value).data("item", item).show();

						if (item.type == "updateSource") {
							var $p = $td.closest("tr").find("input[name=param]");
							var obj = $.extend({}, item);
							obj.value = "自动匹配";
							$p.val(obj.value).data("item", obj);
						}

					}
				}
			},
			close: function(e) {
				var $li = $(e.item);
				var item = $li.data("item");
				if (item.level == 0) {
					//var $ul = $li.parent("ul").hide();
					// $ul.closest('td').find('.textbox-menu').show();
				}
			},
			popupCollision: false
		};

	this.add = function() {
		var c = $($tr.join(''));
		$this.append(c);
		K.initEvent(c);
		return c;
	};

	this.initadd = function(datas) { //还原
		if (datas) {
			var $tr;
			$.each(datas, function(k, item) {

				$.each(item, function(i, v) {
					$tr = K.add();
					$tr.find('input[name=field]').val(v.inname).data("item", {
						id: v.inid,
						value: v.inname,
						type: v.type,
						inparam: v.inparam,
						columnName: v.columnName || '',
						pageId: v.inpage,
						lev: v.inlev,
						cssClass: v.cssClass,
						data: v.data,
						items: v.items,
						otype: v.itype,
						code: v.icode,
						datasetId: v.idatasetId,
						dataId: v.idataId,
						dataType: v.idataType,
						oEditor: v.iEditor
					});
					$tr.find('input[name=param]').val(v.outname).data("item", {
						param: v.param,
						id: v.outid,
						value: v.outname,
						lev: v.outlev,
						pageId: v.outpage,
						eventType: v.eventType,
						otype: v.otype,
						srcUrl: v.srcUrl,
						dataId: v.dataId,
						dataType: v.dataType,
						oEditor: v.oEditor
					});
				});

			});
		}
	};

	this.addbatch = function(datas) {
		$.each(datas, function(i, item) {
			K.add();
		});
	};

	this.remove = function() {

	};

	this.initEvent = function(jq) {
		jq.find(".del-cls").on('click', function() {
			if (confirm('你确定删除吗?')) {
				$(this).closest('tr').remove();
			}
		});

		jq.find(".in-menu").each(function(i, item) {
			var $this = $(this).empty();
			createMenu([{
				text: 'Q',
				level: 0,
				items: K.menus['in']
			}, {
				text: 'E',
				level: 0,
				'cssClass': 'exp',
				//items : K.menus['in']
			}], $this);
			$this.kendoMenu(eve);
		});

		jq.find(".out-menu").each(function(i, item) {
			var $this = $(this).empty();
			var outMenus = K.menus['out'];
			outMenus[0] && (outMenus[0].text = "Q");
			createMenu(outMenus, $this);
			$this.kendoMenu(eve);
		});

		jq.find(".textbox-menu").each(function() {
			$(this).on('dblclick', function() {
				$(this) /*.hide()*/ .next().show();
			}).attr({
				readonly: true
			});
		});

	};

	//根据数据生成 ul >> li
	function createMenu(items, $meun) {
		var $ul, $li, ditems = items;
		$.each(items, function(i, item) {
			$li = $("<li>").text(item.text);
			if (item.cssClass) {
				$li.addClass(item.cssClass);
				item.items = item.items;
			} else {
				if (item.items) {
					$ul = $("<ul>");
					$li.append($ul);
					ditems.lev && (item.items.lev = ditems.lev);
					ditems.pageId && (item.items.pageId = ditems.pageId);
					ditems.eventType && (item.items.eventType = ditems.eventType);
					ditems.otype && (item.items.otype = ditems.otype);

					createMenu(item.items, $ul);
				}
			}
			item.lev = ditems.lev;
			item.pageId = ditems.pageId;
			item.eventType = ditems.eventType;
			if (ditems.otype) {
				item.otype = ditems.otype;
			}
			item.oEditor = ditems.oEditor;
			item.srcUrl = ditems.srcUrl;
			$li.data("item", item);
			$meun.append($li);
		});
	}

}

/**
 * 初始化输入输出参数
 * @return {[type]} [description]
 */
function initOutInParams() {
	mtxxModule.lodding("正在加载中....");
	setTimeout(initOutInParamsLate, 0);
}
$(function() {
	window.paramTable = $("#param-table");
	mtxx.fn = parent.initInOutParameterByEvents;
	paramTable.empty();


	$('#button1').kendoButton({
		click: function() {
			mtxx.tf.add();
		}
	});

	$('#button23').kendoButton({
		click: function() {
			isDetect = false;
			try {
				mtxx.tf.initadd(autoMatch(mtxx.data.menus));
			} catch (e) {
				console.log(e);
			}
			//dialog.open();
		}
	});
	$('#button22').kendoButton({
		click: function() {
			$("#param-table").empty();
		}
	});

	$('#button666').kendoButton({
		click: function() {
			detectSelectObj = {};
			detectSelectOutObj = {};
			//设置为已经定义
			var checkeds = $("#dictcc").find(".auto-in").find(":checked");
			if (checkeds.length) {
				$.each(checkeds, function(i, checked) {
					var that = $(checked);
					detectSelectObj[that.attr("name")] = that.val();
				})
				isDetect = true;
			}
			var outcheckeds = $("#dictcc").find(".auto-out").find(":checked");
			if (outcheckeds.length) {
				$.each(outcheckeds, function(i, checked) {
					var that = $(checked);
					detectSelectOutObj[that.attr("name")] = that.val();
				})
				isDetect = true;
			}
			try {
				mtxx.tf.initadd(autoMatch(mtxx.data.menus));
			} catch (e) {
				console.log(e);
			}
			dialog.close();
		}
	});
	$('#button667').kendoButton({
		click: function() {
			dialog.close();
		}
	});

	$(".mt-cb").on("click", function() {
		var $this = $(this),
			keyStr = "matchAll",
			valStr = "false";
		if ($this.hasClass("dis")) {
			keyStr = "matchDis";
		}
		if ($this.is(":checked")) {
			valStr = "true";
		}
		if (keyStr == "matchAll") {
			matchAll = valStr == "true" ? true : false;
		}
		$.cookie(keyStr, valStr);
	});

	$.cookie("matchDis") == "true" && $(".mt-cb.dis").attr("checked", true);
	$.cookie("matchAll") == "true" && $(".mt-cb.all").attr("checked", true);

	window.dialog = $("#dialog").kendoWindow({
		title: "自动匹配",
		actions: ["Minimize", "Maximize", "Close"],
		modal: true,
		width: "80%",
		height: "80%",
	}).data("kendoWindow");
	dialog.center();
});

(function(){
	var width = $(window).width(),
		height = $(window).height(),
		moduleName = "mt_loadding_div";

	function loadModule(showMsg) {
		var $module = $("<div id=\"" + moduleName + "\" style=\"position: absolute;top: 0;left: 0;background-color: RGBA(0,0,0,0.3);z-index: 99999;\"></div>").width(width + "px").height(height + "px"),
			$lodding = $("<div style=\"position: absolute;top: 50%;right: 50%;color:white;font-size:30px;\">" + showMsg + "</div>");
		$module.clone().append($lodding).appendTo("body");
	}

	function removeModel() {
		$("#"+moduleName).remove();
	}

	window.mtxxModule = {
		lodding : loadModule,
		unLodding : removeModel
	}
})();

function initOutInParamsLate() {
	$("#param-table").empty();
	var fn = mtxx.fn;
	if (mtxx.fn) {
		var params = getParamFn();
		var data = mtxx.data = fn.call($('#hidParam')[0], "", params.inWidget, params.outWidget, params.outExt);
		if (data) {
			var tf = mtxx.tf = new tableFunc("param-table");
			tf.menus = data.menus;

			if (!($.cookie("matchDis") == "true") && $.isEmptyObject(data.datas)) {
				try {
					data.datas = autoMatch(data.menus);
				} catch (e) {
					console.log(e);
				}
			}

			tf.initadd(data.datas);
		}
	}
	setTimeout(function(){
		mtxxModule.unLodding();
	},0);
	
};