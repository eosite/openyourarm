/*
 * 表达式复制
 */
$(document).ready(function($) {
	var $editor = $("#expressionEditor"),
		isExpr = window.ue ? false : true,
		copyFlagText = isExpr?"mt_expression_editor":"mt_html_editor"; //是否为表达式页面
	function _queryObj_($ztreeObj, key, value) {
		return $ztreeObj.getNodeByParam(key, value);
	}

	function queryObjByCode($ztreeObj, value) {
		return !!_queryObj_($ztreeObj, "code", value)
	};
	/**
	 * 检查是否存在
	 * @param  {[type]} value [description]
	 * @return {[type]}       [description]
	 */
	function queryExits(value) {
		var flag = false;
		var $fieldTree = $.fn.zTree.getZTreeObj('fieldTree'); // expr and html
		if (isExpr) {
			var $expTree = $.fn.zTree.getZTreeObj('expTree'); // expr 
			var $pageAttrTree = $.fn.zTree.getZTreeObj('pageAttrTree'); // expr
			flag = queryObjByCode($fieldTree, value) || queryObjByCode($expTree, value) || queryObjByCode($pageAttrTree, value);
		} else {
			var $varTree = $.fn.zTree.getZTreeObj('varTree'); // html 
			var $paramTree = $.fn.zTree.getZTreeObj('paramTree'); // html
			flag = queryObjByCode($fieldTree, value) || queryObjByCode($paramTree, value) || queryObjByCode($varTree, value)
		}
		return flag;
	}
	setTimeout(function() {
		$(isExpr ? document.body : $("#ueditor_0")[0].contentDocument.body).bind({
			copy: function(e) { //copy事件  
				var win = isExpr ? window : $("#ueditor_0")[0].contentWindow;
				var clipboardData = window.clipboardData; //for IE  
				if (!clipboardData) { // for chrome  
					clipboardData = e.originalEvent.clipboardData;
				}
				//选择的区域
				var text,
					editorStr = isExpr ? $editor.val() : window.ue.getContent(); //所有的内容
				if (win.getSelection) {
					text = win.getSelection().toString();
				} else if (win.document.selection && win.document.selection.createRange) {
					text = win.document.selection.createRange().text;
				}
				var bool = text == editorStr;
				if (!isExpr) {
					bool = text.replace(/\n/ig, "") == window.ue.getContentTxt();
				}
				if (bool) {
					clipboardData.setData(copyFlagText, JSON.stringify({
						editorStr: editorStr,
						map: map
					}));
					alert("复制成功");
					return false; //否则设不生效  
				}
				clipboardData.clearData();
				return true;
			},
			paste: function(e) { //paste事件 


				var clipboardData = window.clipboardData; // IE  
				if (!clipboardData) { //chrome  
					clipboardData = e.originalEvent.clipboardData
				}
				var data = clipboardData.getData(copyFlagText);
				try {
					var dataObj = JSON.parse(data),
						dataMap = dataObj.map,
						failureStore = [];
					if (dataMap && dataMap.elements) {
						$.each(dataMap.elements, function(index, ele) {
							if (!map.containsKey(ele.key)) {
								if (queryExits(ele.value.code)) {
									map.elements.push(ele);
								} else {
									failureStore.push(ele);
								}
							}
						});
					}
					var editorStr = dataObj.editorStr || "";
					if (editorStr && failureStore.length) {
						$.each(failureStore, function(index, val) {
							editorStr = editorStr.replace(new RegExp(val.key, "ig"), "  ~%" + val.key + "%~  ");
						});
					}
					if (isExpr) {
						$editor.val($editor.val() + editorStr);
					} else {
						window.ue.setContent(window.ue.getContent() + editorStr);
					}
					return false;
				} catch (e) {
					// $editor.val($editor.val() + data);
					// alert("文本粘贴成功");
					console.error(e);
					//$editor.val(data);
				}
				//alert("粘贴成功");
			}
		});
	}, 1000);
});