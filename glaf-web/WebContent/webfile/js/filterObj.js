/**
 * 过滤数据对象
 */


function filterNode(node) {
	Object.defineProperty(node, "children", {
		enumerable: false
	});
	Object.defineProperty(node, "icon", {
		enumerable: false
	});
	Object.defineProperty(node, "isEle", {
		enumerable: false
	});
	Object.defineProperty(node, "tId", {
		enumerable: false
	});
	Object.defineProperty(node, "parentTId", {
		enumerable: false
	});
	Object.defineProperty(node, "open", {
		enumerable: false
	});
	Object.defineProperty(node, "isParent", {
		enumerable: false
	});
	Object.defineProperty(node, "zAsync", {
		enumerable: false
	});
	Object.defineProperty(node, "isFirstNode", {
		enumerable: false
	});
	Object.defineProperty(node, "isLastNode", {
		enumerable: false
	});
	Object.defineProperty(node, "isAjaxing", {
		enumerable: false
	});
	Object.defineProperty(node, "checked", {
		enumerable: false
	});
	Object.defineProperty(node, "checkedOld", {
		enumerable: false
	});
	Object.defineProperty(node, "nocheck", {
		enumerable: false
	});
	Object.defineProperty(node, "chkDisabled", {
		enumerable: false
	});
	Object.defineProperty(node, "halfCheck", {
		enumerable: false
	});
	Object.defineProperty(node, "check_Child_State", {
		enumerable: false
	});
	Object.defineProperty(node, "check_Focus", {
		enumerable: false
	});
	Object.defineProperty(node, "isHover", {
		enumerable: false
	});
	Object.defineProperty(node, "editNameFlag", {
		enumerable: false
	});
	Object.defineProperty(node, "t", {
		enumerable: false
	});
	return node;
}