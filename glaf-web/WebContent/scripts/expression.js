var start = 0;
var end = 0;
var cursorObj;
/**
 * 判断当前浏览器是否是IE
 * @returns {String}
 */
function isIE() { 
    if (!!window.ActiveXObject || "ActiveXObject" in window)
        return true;
    else
        return false;
}
/**
 * 移动光标到指定位置
 * @param obj
 * @param pos
 */
function MoveCursortoPos(obj, pos) {//定位光标到某个位置   
	pos = pos ? pos : obj.value.length;
	if (obj.createTextRange) {//IE浏览器
		var range = obj.createTextRange();
		range.moveStart("character", pos);
		range.collapse(true);
		range.select();
	} else {//非IE浏览器
		obj.setSelectionRange(obj.value.length, pos);
	}
	obj.focus();
}
/**
 * 在当前光标位置插入指定字符
 * @param textBox
 * @param val
 */
function insertAtCursor(textBox, val) {
	var pre = textBox.value.substr(0, start);
	var post = textBox.value.substr(end);
	textBox.value = pre + val + post;
	MoveCursortoPos(textBox, start + val.length);
}
function savePos(textBox) {
	cursorObj=textBox;
	//如果是Firefox(1.5)的话，方法很简单
	if (typeof (textBox.selectionStart) == "number") {
		start = textBox.selectionStart;
		end = textBox.selectionEnd;
	}
	//下面是IE(6.0)的方法，麻烦得很，还要计算上'\n'
	else if (document.selection) {
		var range = document.selection.createRange();
		if (range.parentElement().id == textBox.id) {
			// create a selection of the whole textarea
			var range_all = document.body.createTextRange();
			range_all.moveToElementText(textBox);
			//两个range，一个是已经选择的text(range)，一个是整个textarea(range_all)
			//range_all.compareEndPoints()比较两个端点，如果range_all比range更往左(further to the left)，则                //返回小于0的值，则range_all往右移一点，直到两个range的start相同。
			// calculate selection start point by moving beginning of range_all to beginning of range
			for (start = 0; range_all.compareEndPoints("StartToStart", range) < 0; start++)
				range_all.moveStart('character', 1);
			// get number of line breaks from textarea start to selection start and add them to start
			// 计算一下\n
			for (var i = 0; i <= start; i++) {
				if (textBox.value.charAt(i) == '\n')
					start++;
			}
			// create a selection of the whole textarea
			var range_all = document.body.createTextRange();
			range_all.moveToElementText(textBox);
			// calculate selection end point by moving beginning of range_all to end of range
			for (end = 0; range_all.compareEndPoints('StartToEnd', range) < 0; end++)
				range_all.moveStart('character', 1);
			// get number of line breaks from textarea start to selection end and add them to end
			for (var i = 0; i <= end; i++) {
				if (textBox.value.charAt(i) == '\n')
					end++;
			}
		}
	}
}
//验证消息输出
function outMessage(contextPath, execFlag, msg, errorToken, errorPosition,
		showEditor, showMsgObj) {
	var titleTemplate = "";
	if (execFlag == 1) {
		titleTemplate = '<img src="'
				+ contextPath
				+ '/images/okay.png"/>'
				+ '<font style="font-size: 15px;font-weight: bolder;margin-left: 5px;">验证成功</font>'
	} else if (execFlag == 0) {
		titleTemplate = '<img src="'
				+ contextPath
				+ '/images/error.png"/>'
				+ '<font style="font-size: 15px;font-weight: bolder;margin-left: 5px;">验证失败</font>'
	} else {
		titleTemplate = '<img src="'
				+ contextPath
				+ '/images/warning_triangle.png"/>'
				+ '<font style="font-size: 15px;font-weight: bolder;margin-left: 5px;">验证异常</font>'
	}
	if (errorToken != "" && errorToken != undefined) {
		msg = "错误操作符：" + errorToken + "<br/>" + msg;
	}
	/* 	kendo.message(msg, {
				title : titleTemplate,
				width : '350px',
				height : '200px'
			});  */
	showMsgObj.html(titleTemplate + "</font><br><br>" + msg);
	//IE浏览器支持
    if( isIE())
    {
	    settingTokenColor(showEditor, errorToken, errorPosition || -1);
    }
	MoveCursortoPos(showEditor[0], errorPosition + 1);//暂时注释掉
}
//设置问题操作符的字体颜色
function settingTokenColor(editor, errorToken, errorPosition) {
	if (errorPosition >= 0) {
		//光标定位到错误位置
		var editorVal = editor.val();
		//值清空
		editor.val("");
		//设置字体为红色
		var pre = editorVal.substr(0, errorPosition);
		var post = editorVal.substr(errorPosition + 1, editorVal.length);
		var prefont = document.createElement("font");
		prefont.color = "black";
		prefont.innerText = pre;
		editor.append(prefont);
		var red = document.createElement("font");
		red.color = "red";
		red.innerText = errorToken;
		editor.append(red);
		var postfont = document.createElement("font");
		postfont.color = "black";
		postfont.innerText = post;
		editor.append(postfont);
	}
}
//将显示文本框中的表达式转换为隐藏域中的表达式
function changeExpre(showEditor, hiddenEditor) {
	var actExpre = showEditor.val();
	var reg;
	//变量map替换
	$.each(map.elements, function(i, obj) {
		if (obj.value.value != obj.value.code) {
			//reg=new RegExp("\$F\{db1.cell_useradd12.column1\}","g"); 
			actExpre = replaceAll(actExpre, obj.value.value, obj.value.code);
		}
	});
	hiddenEditor.val(actExpre);
}
/**
 *替换所有字符
 *
 */
function replaceAll(srcStr, repStr, nStr) {
	while (srcStr.indexOf(repStr) > -1) {
		srcStr = srcStr.replace(repStr, nStr);
	}
	return srcStr;
}
function cleanCursor()
{
	cursorObj=null;
	start = 0;
	end = 0;
}