/**
 * 控件界面控制
 */
// 显示标题栏
function showTitleBar() {
	TANGER_OCX_OBJ.Titlebar = true;
}
// 隐藏标题栏
function hideTitleBar() {
	TANGER_OCX_OBJ.Titlebar = false;
}
// 显示菜单栏
function showMenubar() {
	TANGER_OCX_OBJ.Menubar = true;
}
// 隐藏菜单栏
function hideMenubar() {
	TANGER_OCX_OBJ.Menubar = false;
}
// 显示状态栏
function showStatusbar() {
	TANGER_OCX_OBJ.Statusbar = true;
}
// 隐藏状态栏
function hideStatusbar() {
	TANGER_OCX_OBJ.Statusbar = false;
}
// 显示工具栏
function showToolbars() {
	TANGER_OCX_OBJ.Toolbars = true;
}
// 隐藏工具栏
function hideToolbars() {
	TANGER_OCX_OBJ.Toolbars = false;
}

/**
 * 控件功能控制
 */
// 允许用户从控件拷贝数据
function setCopy() {
	TANGER_OCX_OBJ.IsNoCopy = true;
}
// 禁止用户从控件拷贝数据
function setNoCopy() {
	TANGER_OCX_OBJ.IsNoCopy = false;
}
// 显示工具菜单项和审阅工具栏及右键菜单
function showToolMenu() {
	TANGER_OCX_OBJ.IsShowToolMenu = true;
}
// 隐藏工具菜单项和审阅工具栏及右键菜单
function hideToolMenu() {
	TANGER_OCX_OBJ.IsShowToolMenu = false;
}
/**
 * 控件文件菜单的控制
 */
// 允许文件－>新建菜单
function enableFileNewMenu() {
	TANGER_OCX_OBJ.FileNew = true;
}
// 禁止文件－>新建菜单
function disableFileNewMenu() {
	TANGER_OCX_OBJ.FileNew = false;
}
// 允许文件－>打开菜单
function enableFileOpenMenu() {
	TANGER_OCX_OBJ.FileOpen = true;
}
// 禁止文件－>打开菜单
function disableFileOpenMenu() {
	TANGER_OCX_OBJ.FileOpen = false;
}
// 允许文件－>关闭菜单
function enableFileCloseMenu() {
	TANGER_OCX_OBJ.FileClose = true;
}
// 禁止文件－>关闭菜单
function disableFileCloseMenu() {
	TANGER_OCX_OBJ.FileClose = false;
}
// 允许文件－>保存菜单
function enableFileSaveMenu() {
	TANGER_OCX_OBJ.FileSave = true;
}
// 禁止文件－>保存菜单
function disableFileSaveMenu() {
	TANGER_OCX_OBJ.FileSave = false;
}
// 允许文件－>另存为菜单
function enableFileSaveAsMenu() {
	TANGER_OCX_OBJ.FileSaveAs = true;
}
// 禁止文件－>另存为菜单
function disableFileSaveAsMenu() {
	TANGER_OCX_OBJ.FileSaveAs = false;
}

// 允许文件－>打印菜单
function enableFilePrintMenu() {
	TANGER_OCX_OBJ.FilePrint = true;
}

// 禁止文件－>打印菜单
function disableFilePrintMenu() {
	TANGER_OCX_OBJ.FilePrint = false;
}

// 允许文件－>打印预览菜单
function enableFilePrintPreviewMenu() {
	TANGER_OCX_OBJ.FilePrintPreview = true;
}

// 禁止文件－>打印预览菜单
function disableFilePrintPreviewMenu() {
	TANGER_OCX_OBJ.FilePrintPreview = false;
}

/**
 * 创建，从本地打开和保存文档
 */
// 创建新Word文档
function createWord() {
	TANGER_OCX_OBJ.CreateNew("Word.Document");
}
// 创建新Excel电子表格
function createExcel() {
	TANGER_OCX_OBJ.CreateNew("Excel.Sheet");
}
// 提示用户选择本地文件打开
function createFromLocal() {
	TANGER_OCX_OBJ.ShowDialog(1);
}
// 提示用户选择本地文件保存
function saveFromLocal() {
	TANGER_OCX_OBJ.ShowDialog(3);
}
// 不提示用户直接打开指定的本地文件
function openLocalFile(url) {
	// TANGER_OCX_OBJ.OpenLocalFile ("c:\\test.doc");
	TANGER_OCX_OBJ.OpenLocalFile(url);
}
// 不提示用户直接保存为指定的本地文件
function saveToLocal(url) {
	// TANGER_OCX_OBJ.SaveToLocal ("c:\\test.doc",false);//第二个参数为true将覆盖已存在的文件
	TANGER_OCX_OBJ.SaveToLocal(url, false);
}

// 从URL打开文档
function openFromURL(url) {
	TANGER_OCX_OBJ.OpenFromURL(url);// 可以是相对或者绝对URL
}

// 关闭文档
function ntkoClose() {
	TANGER_OCX_OBJ.Close();
}

/**
 * 痕迹保留控制相关
 */
// 进入强制痕迹保留状态
function setMarkModifyIn() {
	TANGER_OCX_SetReviewMode(true);
	TANGER_OCX_EnableReviewBar(!true);
}
// 退出强制痕迹保留状态
function setMarkModifyOut() {
	TANGER_OCX_SetReviewMode(false);
	TANGER_OCX_EnableReviewBar(!false);
}

// 允许显示修订工具栏和工具菜单（保护修订,用户不能更改当前修订状态）
function enableReviewBar() {
	TANGER_OCX_OBJ.ActiveDocument.CommandBars("Reviewing").Enabled = true;
	TANGER_OCX_OBJ.ActiveDocument.CommandBars("Track Changes").Enabled = true;
	TANGER_OCX_OBJ.IsShowToolMenu = true; // 打开工具菜单
}
// 禁止显示修订工具栏和工具菜单（保护修订,用户不能更改当前修订状态）
function disableReviewBar() {
	TANGER_OCX_OBJ.ActiveDocument.CommandBars("Reviewing").Enabled = false;
	TANGER_OCX_OBJ.ActiveDocument.CommandBars("Track Changes").Enabled = false;
	TANGER_OCX_OBJ.IsShowToolMenu = false; // 关闭工具菜单
}

// 打开修订模式
function openReviewMode() {
	TANGER_OCX_OBJ.ActiveDocument.TrackRevisions = true;
}
// 关闭修订模式
function closeReviewMode() {
	TANGER_OCX_OBJ.ActiveDocument.TrackRevisions = false;
}

// 显示修订文字
function showRevisions() {
	TANGER_OCX_OBJ.ActiveDocument.ShowRevisions = true;
}
// 隐藏修订文字
function hideRevisions() {
	TANGER_OCX_OBJ.ActiveDocument.ShowRevisions = false;
}

// 打印修订文字
function printRevisions() {
	TANGER_OCX_OBJ.ActiveDocument.PrintRevisions = true;
}
// 不打印修订文字
function notPrintRevisions() {
	TANGER_OCX_OBJ.ActiveDocument.PrintRevisions = false;
}

// 接受所有修订
function acceptAllRevisions() {
	TANGER_OCX_OBJ.ActiveDocument.AcceptAllRevisions();
}
// 拒绝所有修订
function rejectAllRevisions() {
	TANGER_OCX_OBJ.ActiveDocument.RejectAllRevisions();
}

/**
 * 文档控制
 */
// 只读
function setReadOnly() {
	var i;
	try {
		if (true)
			TANGER_OCX_OBJ.IsShowToolMenu = false;
		with (TANGER_OCX_OBJ.ActiveDocument) {
			if (TANGER_OCX_OBJ.DocType == 1) // word
			{
				if ((ProtectionType != -1) && !true) {
					Unprotect();
				}
				if ((ProtectionType == -1) && true) {
					Protect(2, true, "");
				}
			} else if (TANGER_OCX_OBJ.DocType == 2)// excel
			{
				for (i = 1; i <= Application.Sheets.Count; i++) {
					if (true) {
						Application.Sheets(i).Protect("", true, true, true);
					} else {
						Application.Sheets(i).Unprotect("");
					}
				}
				if (true) {
					Application.ActiveWorkbook.Protect("", true);
				} else {
					Application.ActiveWorkbook.Unprotect("");
				}
			}
		}
	} catch (err) {
		// alert("错误：" + err.number + ":" + err.description);
	} finally {
	}
}

// 可写
function setWrite() {
	var i;
	try {
		if (false)
			TANGER_OCX_OBJ.IsShowToolMenu = false;
		with (TANGER_OCX_OBJ.ActiveDocument) {
			if (TANGER_OCX_OBJ.DocType == 1) // word
			{
				if ((ProtectionType != -1) && !false) {
					Unprotect();
				}
				if ((ProtectionType == -1) && false) {
					Protect(2, true, "");
				}
			} else if (TANGER_OCX_OBJ.DocType == 2)// excel
			{
				for (i = 1; i <= Application.Sheets.Count; i++) {
					if (false) {
						Application.Sheets(i).Protect("", true, true, true);
					} else {
						Application.Sheets(i).Unprotect("");
					}
				}
				if (false) {
					Application.ActiveWorkbook.Protect("", true);
				} else {
					Application.ActiveWorkbook.Unprotect("");
				}
			}
		}
	} catch (err) {
		// alert("错误：" + err.number + ":" + err.description);
	} finally {
	}
}

/**
 * 手写签名和电子印章
 */
// 从默认路径增加印章到当前光标所在的段落的指定位置
function addSignFromLocal() {
	if (TANGER_OCX_bDocOpen) {
		TANGER_OCX_OBJ.AddSignFromLocal(TANGER_OCX_Username,// 当前登陆用户
		"",// 缺省文件
		true,// 提示选择
		0,// left
		0, TANGER_OCX_key, 1, 100, 0)
	}
}

// 从url增加印章到当前光标所在的段落的指定位置
function addSignFromURL(URL) {
	// alert(TANGER_OCX_key);
	if (TANGER_OCX_bDocOpen) {
		TANGER_OCX_OBJ.AddSignFromURL(TANGER_OCX_Username,// 当前登陆用户
		URL,// URL
		50,// left
		50, TANGER_OCX_key, 1, 100, 0)
	}
}

// 开始全屏手写签名
function doFullScreenHandSign(left, top, relative, scaling) {
	if (TANGER_OCX_bDocOpen) {
		TANGER_OCX_OBJ.DoHandSign2(TANGER_OCX_Username,// 当前登陆用户 必须
		TANGER_OCX_key, left,// 可选参数,ex:0
		top,// 可选参数,ex:0
		relative,// relative=0，表示按照屏幕位置批注
		scaling // 缩放100%，表示原大小
		);
	}
}

// 开始手写签名
function doHandSign(penType, rgb, penWidth, left, top) {
	if (TANGER_OCX_bDocOpen) {
		TANGER_OCX_OBJ.DoHandSign(TANGER_OCX_Username,// 当前登陆用户 必须
		penType,// 笔型0－实线 0－4,可选参数
		rgb, // 颜色 0x00RRGGBB,可选参数
		penWidth,// 笔宽,可选参数,ex:2
		left,// 可选参数,ex:100
		top,// 可选参数,ex:50
		false, TANGER_OCX_key);
	}
}

// 检查签名结果(弹出验证对话框)
function doCheckSignByWin() {
	if (TANGER_OCX_bDocOpen) {
		var ret = TANGER_OCX_OBJ.DoCheckSign(false,/* 如果为FAlSE,表示弹出验证对话框,否则,只是返回验证结果到返回值,boolean */
		TANGER_OCX_key);// 返回值，验证结果字符串
	}
}

// 检查签名结果(无弹出验证对话框)
function doCheckSign() {
	if (TANGER_OCX_bDocOpen) {
		var ret = TANGER_OCX_OBJ.DoCheckSign(true,/* 如果为FAlSE,表示弹出验证对话框,否则,只是返回验证结果到返回值,boolean */
		TANGER_OCX_key);// 返回值，验证结果字符串
	}
}

/**
 * 插入图片和手工批注
 */
// 增加本地图片到文档指定位置
function addPictureFromLocal(selTips, isFloating, leftMar, topMar) {
	if (TANGER_OCX_bDocOpen) {
		TANGER_OCX_OBJ.AddPicFromLocal("", // 默认本地路径
		selTips,// 是否提示选择文件(boolean)
		isFloating,// 是否浮动图片(boolean)
		leftMar,// 如果是浮动图片，相对于左边的Left(单位磅),ex:100
		topMar); // 如果是浮动图片，相对于当前段落Top,ex:100
	}
	;
}

// 从URL增加图片到文档指定位置
function addPictureFromURL(URL, isFloating, scaling) {
	if (TANGER_OCX_bDocOpen) {
		TANGER_OCX_OBJ.AddPicFromURL(URL,// URL 注意；URL必须返回Word支持的图片类型。
		isFloating,// 是否浮动图片(boolean)
		0, 0, 1, // 当前光标处
		scaling,// 无缩放,ex:100
		1 // 文字上方
		)
	}
	;
}

// 开始手工绘图
function doHandDraw(penType, rgb, penWidth, left, top) {
	if (TANGER_OCX_bDocOpen) {
		TANGER_OCX_OBJ.DoHandDraw(penType,// 笔型:0,可选参数,ex:实线 0－4
		rgb,// 颜色,格式为：0x00RRGGBB,可选参数
		penWidth,// 笔宽,可选参数,ex:3
		left,// 可选参数,ex:200
		top);// 可选参数,ex:50
	}
}

/**
 * 模板套红
 */
// 简单的套红:将URL指定的模板文件插入到当前文档的头部
function doTaoHong(URL) {
	try {
		TANGER_OCX_OBJ.ActiveDocument.Application.Selection.HomeKey(6);
		TANGER_OCX_OBJ.AddTemplateFromURL(URL);
	} catch (err) {
	}
	;
};
// 稍微复杂的套红:将URL指定的模板文件插入到当前编辑文档的”ZhengWen”书签位置。
function doPaiBan(URL) {
	try {
		// 选择对象当前文档的所有内容
		var curSel = TANGER_OCX_OBJ.ActiveDocument.Application.Selection;
		TANGER_OCX_SetMarkModify(false);
		curSel.WholeStory();
		curSel.Cut();
		// 插入模板
		TANGER_OCX_OBJ.AddTemplateFromURL(URL);
		var BookMarkName = "zhengwen";
		if (!TANGER_OCX_OBJ.ActiveDocument.BookMarks.Exists(BookMarkName)) {
			alert("Word 模板中不存在名称为：\"" + BookMarkName + "\"的书签！");
			return;
		}
		var bkmkObj = TANGER_OCX_OBJ.ActiveDocument.BookMarks(BookMarkName);
		var saverange = bkmkObj.Range
		saverange.Paste();
		TANGER_OCX_OBJ.ActiveDocument.Bookmarks.Add(BookMarkName, saverange);
		TANGER_OCX_SetMarkModify(true);
	} catch (err) {
		// alert("错误：" + err.number + ":" + err.description);
	}
	;
}
