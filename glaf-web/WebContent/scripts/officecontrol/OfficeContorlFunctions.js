var OFFICE_CONTROL_OBJ = document.all("TANGER_OCX");// 控件对象
var IsFileOpened; // 控件是否打开文档
var fileType;
var fileTypeSimple;
var TANGER_OCX_str = '';
var TANGER_OCX_obj = null;
var result, code, menuPos, submenuPos, subsubmenuPos, menuCaption, menuID;

function getOFFICE_CONTROL_OBJ() {
	if (OFFICE_CONTROL_OBJ == null) {
		OFFICE_CONTROL_OBJ = document.all("TANGER_OCX");
	}
	return OFFICE_CONTROL_OBJ || {};
}

$(function() {
	TANGER_OCX_obj = OFFICE_CONTROL_OBJ = document.all("TANGER_OCX");
	$(document.body).attr({
		onbeforeunload : "onPageClose()"
	});

	// intializePage(contextPath + "/webfile/templates/ntko/blank.docx");
});

function intializePage(fileUrl) {
	// OFFICE_CONTROL_OBJ = document.all("TANGER_OCX");
	// initCustomMenus();
	NTKO_OCX_OpenDoc(fileUrl);
}

function onPageClose() {
	/*
	 * if (getOFFICE_CONTROL_OBJ().activeDocument) { if
	 * (!getOFFICE_CONTROL_OBJ().activeDocument.saved) { if
	 * (confirm("文档修改过,还没有保存,是否需要保存?")) { saveFileToUrl(); } } }
	 */
}

function NTKO_OCX_OpenDoc(fileUrl) {
	getOFFICE_CONTROL_OBJ().BeginOpenFromURL(fileUrl, false);
}
function setFileOpenedOrClosed(bool) {

	// OFFICE_CONTROL_OBJ = OFFICE_CONTROL_OBJ || document.all("TANGER_OCX");

	IsFileOpened = bool;

	fileType = getOFFICE_CONTROL_OBJ().DocType;
}
// 删除左右两端的空格
function trim(str) {
	if (str)
		return str.replace(/(^\s*)|(\s*$)/g, "");
	return str;
}

function getFile(doctype) {
	return (fileTypes[doctype] || fileTypes[8]);
}

function saveForDownLoad(){
	var myUrl = /* document.forms[0] */officeForm.action;

	var fileName = officeForm.filename.value;
	
	var f = getFile(getOFFICE_CONTROL_OBJ().doctype);
	
	f && (f.dl = 1);

	fileType = f.fileType;

	var obj = {
		url : myUrl,// 提交到的url地址
		id : 'upLoadFile',// 文件域的id，类似<input type=file id=upLoadFile 中的id
		fileType : $.param(f),// 与控件一起提交的参数如："p1=a&p2=b&p3=c"
		fileName : fileName,// 上传文件的名称，类似<input type=file 的value,
		form : 'officeForm' // 与控件一起提交的表单id，也可以是form的序列号，这里应该是0.
	};
	
	SetReviewMode(false) ;
	

	setShowRevisions(false);

	getOFFICE_CONTROL_OBJ().SaveToURL(obj.url, obj.id,
			obj.fileType, obj.fileName, obj.form);
//	
	f.dl = "";
}

function saveFileToUrl(save) {
	var myUrl = /* document.forms[0] */officeForm.action;

	var fileName = officeForm.filename.value;

	if (!fileName) {
		alert('请输入文件名称!');
		return false;
	}

	if (IsFileOpened) {
		var f = getFile(getOFFICE_CONTROL_OBJ().doctype);

		fileType = f.fileType;

		var obj = {
			url : myUrl,// 提交到的url地址
			id : 'upLoadFile',// 文件域的id，类似<input type=file id=upLoadFile 中的id
			fileType : $.param(f),// 与控件一起提交的参数如："p1=a&p2=b&p3=c"
			fileName : fileName,// 上传文件的名称，类似<input type=file 的value,
			form : 'officeForm' // 与控件一起提交的表单id，也可以是form的序列号，这里应该是0.
		};

		var result = getOFFICE_CONTROL_OBJ().SaveToURL(obj.url, obj.id,
				obj.fileType, obj.fileName, obj.form);

		var json = JSON.parse(result);

		document.all("statusBar").innerHTML = "服务器返回信息:" + json.rst;

		officeForm.attachmentId.value = json.id;

		if (!save) {
			alert("操作成功!");
		}

		getOFFICE_CONTROL_OBJ().activeDocument.saved = true;
		// window.close();
	} else {

	}
	return true;
}
function saveFileAsHtmlToUrl() {
	var myUrl = "upLoadHtmlFile.jsp";
	var htmlFileName = document.all("fileName").value + ".html";
	var result;
	if (IsFileOpened) {
		result = getOFFICE_CONTROL_OBJ().PublishAsHTMLToURL(
				"upLoadHtmlFile.jsp", "uploadHtml",
				"htmlFileName=" + htmlFileName, htmlFileName);
		result = trim(result);
		document.all("statusBar").innerHTML = "服务器返回信息:" + result;
		alert(result);
		window.close();
	}
}
function saveFileAsPdfToUrl() {
	var myUrl = "upLoadPdfFile.jsp";
	var pdfFileName = document.all("fileName").value + ".pdf";
	if (IsFileOpened) {
		getOFFICE_CONTROL_OBJ().PublishAsPdfToURL(myUrl, "uploadPdf",
				"PdfFileName=" + pdfFileName, pdfFileName, "", "", true, false);
	}
}
function testFunction() {
	alert(IsFileOpened);
}
function addServerSecSign() {
	var signUrl = document.all("secSignFileUrl").options[document
			.all("secSignFileUrl").selectedIndex].value;
	if (IsFileOpened) {
		if (getOFFICE_CONTROL_OBJ().doctype == 1
				|| getOFFICE_CONTROL_OBJ().doctype == 2) {
			try {
				alert("正式版本用户请插入EKEY！\r\n\r\n此为电子印章系统演示功能，请购买正式版本！");
				getOFFICE_CONTROL_OBJ().AddSecSignFromURL("ntko", signUrl);
			} catch (error) {
			}
		} else {
			alert("不能在该类型文档中使用安全签名印章.");
		}
	}
}
function addLocalSecSign() {
	if (IsFileOpened) {
		if (getOFFICE_CONTROL_OBJ().doctype == 1
				|| getOFFICE_CONTROL_OBJ().doctype == 2) {
			try {
				getOFFICE_CONTROL_OBJ().AddSecSignFromLocal("ntko", "");
			} catch (error) {
			}
		} else {
			alert("不能在该类型文档中使用安全签名印章.");
		}
	}
}
function addEkeySecSign() {
	if (IsFileOpened) {
		if (getOFFICE_CONTROL_OBJ().doctype == 1
				|| getOFFICE_CONTROL_OBJ().doctype == 2) {
			try {
				getOFFICE_CONTROL_OBJ().AddSecSignFromEkey("ntko");
			} catch (error) {
			}
		} else {
			alert("不能在该类型文档中使用安全签名印章.");
		}
	}
}
function addHandSecSign() {
	if (IsFileOpened) {
		if (getOFFICE_CONTROL_OBJ().doctype == 1
				|| getOFFICE_CONTROL_OBJ().doctype == 2) {
			try {
				getOFFICE_CONTROL_OBJ().AddSecHandSign("ntko");
			} catch (error) {
			}
		} else {
			alert("不能在该类型文档中使用安全签名印章.");
		}
	}
}

function addServerSign(signUrl) {
	if (IsFileOpened) {
		try {
			getOFFICE_CONTROL_OBJ().AddSignFromURL("ntko",// 印章的用户名
			signUrl,// 印章所在服务器相对url
			100,// 左边距
			100,// 上边距 根据Relative的设定选择不同参照对象
			"ntko",// 调用DoCheckSign函数签名印章信息,用来验证印章的字符串
			3, // Relative,取值1-4。设置左边距和上边距相对以下对象所在的位置 1：光标位置；2：页边距；3：页面距离
			// 4：默认设置栏，段落
			100,// 缩放印章,默认100%
			1); // 0印章位于文字下方,1位于上方

		} catch (error) {
		}
	}
}

function addLocalSign() {
	if (IsFileOpened) {
		try {
			getOFFICE_CONTROL_OBJ().AddSignFromLocal("ntko",// 印章的用户名
			"",// 缺省文件名
			true,// 是否提示选择
			100,// 左边距
			100,// 上边距 根据Relative的设定选择不同参照对象
			"ntko",// 调用DoCheckSign函数签名印章信息,用来验证印章的字符串
			3, // Relative,取值1-4。设置左边距和上边距相对以下对象所在的位置
			// 1：光标位置；2：页边距；3：页面距离 4：默认设置栏，段落
			100,// 缩放印章,默认100%
			1); // 0印章位于文字下方,1位于上方
		} catch (error) {
		}
	}
}
function addPicFromUrl(picURL) {
	if (IsFileOpened) {
		if (getOFFICE_CONTROL_OBJ().doctype == 1
				|| getOFFICE_CONTROL_OBJ().doctype == 2) {
			try {
				getOFFICE_CONTROL_OBJ().AddPicFromURL(picURL,// 图片的url地址可以时相对或者绝对地址
				false,// 是否浮动,此参数设置为false时,top和left无效
				100,// left 左边距
				100,// top 上边距 根据Relative的设定选择不同参照对象
				1, // Relative,取值1-4。设置左边距和上边距相对以下对象所在的位置 1：光标位置；2：页边距；3：页面距离
				// 4：默认设置栏，段落
				100,// 缩放印章,默认100%
				1); // 0印章位于文字下方,1位于上方

			} catch (error) {
			}
		} else {
			alert("不能在该类型文档中使用安全签名印章.");
		}
	}
}
function addPicFromLocal() {
	if (IsFileOpened) {
		try {
			getOFFICE_CONTROL_OBJ().AddPicFromLocal("",// 印章的用户名
			true,// 缺省文件名
			false,// 是否提示选择
			100,// 左边距
			100,// 上边距 根据Relative的设定选择不同参照对象
			1, // Relative,取值1-4。设置左边距和上边距相对以下对象所在的位置
			// 1：光标位置；2：页边距；3：页面距离 4：默认设置栏，段落
			100,// 缩放印章,默认100%
			1); // 0印章位于文字下方,1位于上方
		} catch (error) {
		}
	}
}

function TANGER_OCX_AddDocHeader(strHeader) {
	if (!IsFileOpened) {
		return;
	}
	var i, cNum = 30;
	var lineStr = "";
	try {
		for (i = 0; i < cNum; i++)
			lineStr += "_"; // 生成下划线
		with (getOFFICE_CONTROL_OBJ().ActiveDocument.Application) {
			Selection.HomeKey(6, 0); // go home
			Selection.TypeText(strHeader);
			Selection.TypeParagraph(); // 换行
			Selection.TypeText(lineStr); // 插入下划线
			// Selection.InsertSymbol(95,"",true); //插入下划线
			Selection.TypeText("★");
			Selection.TypeText(lineStr); // 插入下划线
			Selection.TypeParagraph();
			// Selection.MoveUp(5, 2, 1); //上移两行，且按住Shift键，相当于选择两行
			Selection.HomeKey(6, 1); // 选择到文件头部所有文本
			Selection.ParagraphFormat.Alignment = 1; // 居中对齐
			with (Selection.Font) {
				NameFarEast = "宋体";
				Name = "宋体";
				Size = 12;
				Bold = false;
				Italic = false;
				Underline = 0;
				UnderlineColor = 0;
				StrikeThrough = false;
				DoubleStrikeThrough = false;
				Outline = false;
				Emboss = false;
				Shadow = false;
				Hidden = false;
				SmallCaps = false;
				AllCaps = false;
				Color = 255;
				Engrave = false;
				Superscript = false;
				Subscript = false;
				Spacing = 0;
				Scaling = 100;
				Position = 0;
				Kerning = 0;
				Animation = 0;
				DisableCharacterSpaceGrid = false;
				EmphasisMark = 0;
			}
			Selection.MoveDown(5, 3, 0); // 下移3行
		}
	} catch (err) {
		alert("错误：" + err.number + ":" + err.description);
	} finally {
	}
}

function insertRedHeadFromUrl(headFileURL) {
	if (getOFFICE_CONTROL_OBJ().doctype != 1)// getOFFICE_CONTROL_OBJ().doctype=1为word文档
	{
		return;
	}
	getOFFICE_CONTROL_OBJ().ActiveDocument.Application.Selection.HomeKey(6, 0);// 光标移动到文档开头
	getOFFICE_CONTROL_OBJ().addtemplatefromurl(headFileURL);// 在光标位置插入红头文档
}
function openTemplateFileFromUrl(templateUrl) {
	getOFFICE_CONTROL_OBJ().openFromUrl(templateUrl);
}
function doHandSign() {
	/*
	 * if(getOFFICE_CONTROL_OBJ().doctype==1||getOFFICE_CONTROL_OBJ().doctype==2)//此处设置只允许在word和excel中盖章.doctype=1是"word"文档,doctype=2是"excel"文档 {
	 */
	getOFFICE_CONTROL_OBJ().DoHandSign2("ntko",// 手写签名用户名称
	"ntko",// signkey,DoCheckSign(检查印章函数)需要的验证密钥。
	0,// left
	0,// top
	1,// relative,设定签名位置的参照对象.0：表示按照屏幕位置插入，此时，Left,Top属性不起作用。1：光标位置；2：页边距；3：页面距离
	// 4：默认设置栏，段落（为兼容以前版本默认方式）
	100);
	// }
}
function SetReviewMode(boolvalue) {
	if (getOFFICE_CONTROL_OBJ().doctype == 1) {
		getOFFICE_CONTROL_OBJ().ActiveDocument.TrackRevisions = boolvalue;// 设置是否保留痕迹
	}
}

function setShowRevisions(boolevalue) {
	if (getOFFICE_CONTROL_OBJ().doctype == 1) {
		getOFFICE_CONTROL_OBJ().ActiveDocument.ShowRevisions = boolevalue;// 设置是否显示痕迹
	}
}
function setFilePrint(boolvalue) {
	getOFFICE_CONTROL_OBJ().fileprint = boolvalue;// 是否允许打印
}
function setFileNew(boolvalue) {
	getOFFICE_CONTROL_OBJ().FileNew = boolvalue;// 是否允许新建
}
function setFileSaveAs(boolvalue) {
	getOFFICE_CONTROL_OBJ().FileSaveAs = boolvalue;// 是否允许另存为
}

function setIsNoCopy(boolvalue) {
	getOFFICE_CONTROL_OBJ().IsNoCopy = boolvalue;// 是否禁止粘贴
}
// 验证文档控件自带印章功能盖的章
function DoCheckSign() {
	if (IsFileOpened) {
		var ret = getOFFICE_CONTROL_OBJ().DoCheckSign(false,/*
															 * 可选参数 IsSilent
															 * 缺省为FAlSE，表示弹出验证对话框,否则，只是返回验证结果到返回值
															 */
		"ntko"// 使用盖章时的signkey,这里为"ntko"
		);// 返回值，验证结果字符串
		// alert(ret);
	}
}
// 设置工具栏
function setToolBar() {
	getOFFICE_CONTROL_OBJ().ToolBars = !getOFFICE_CONTROL_OBJ().ToolBars;
}
// 控制是否显示所有菜单
function setMenubar() {
	getOFFICE_CONTROL_OBJ().Menubar = !getOFFICE_CONTROL_OBJ().Menubar;
}
// 控制”插入“菜单栏
function setInsertMemu() {
	getOFFICE_CONTROL_OBJ().IsShowInsertMenu = !getOFFICE_CONTROL_OBJ().IsShowInsertMenu;
}
// 控制”编辑“菜单栏
function setEditMenu() {
	getOFFICE_CONTROL_OBJ().IsShowEditMenu = !getOFFICE_CONTROL_OBJ().IsShowEditMenu;
}
// 控制”工具“菜单栏
function setToolMenu() {
	getOFFICE_CONTROL_OBJ().IsShowToolMenu = !getOFFICE_CONTROL_OBJ().IsShowToolMenu;
}

// 自定义菜单功能函数
function initCustomMenus() {
	var myobj = getOFFICE_CONTROL_OBJ();

	for (var menuPos = 0; menuPos < 3; menuPos++) {
		myobj.AddCustomMenu2(menuPos, "菜单" + menuPos + "(&" + menuPos + ")");
		for (var submenuPos = 0; submenuPos < 10; submenuPos++) {
			if (1 == (submenuPos % 3)) // 主菜单增加分隔符。第3个参数是-1是在主菜单增加
			{
				myobj.AddCustomMenuItem2(menuPos, submenuPos, -1, false, "-",
						true);
			} else if (0 == (submenuPos % 2)) // 主菜单增加子菜单，第3个参数是-1是在主菜单增加
			{
				myobj.AddCustomMenuItem2(menuPos, submenuPos, -1, true, "子菜单"
						+ menuPos + "-" + submenuPos, false);
				// 增加子菜单项目
				for (var subsubmenuPos = 0; subsubmenuPos < 9; subsubmenuPos++) {
					if (0 == (subsubmenuPos % 2))// 增加子菜单项目
					{
						myobj.AddCustomMenuItem2(menuPos, submenuPos,
								subsubmenuPos, false, "子菜单项目" + menuPos + "-"
										+ submenuPos + "-" + subsubmenuPos,
								false, menuPos * 100 + submenuPos * 20
										+ subsubmenuPos);
					} else // 增加子菜单分隔
					{
						myobj
								.AddCustomMenuItem2(menuPos, submenuPos,
										subsubmenuPos, false, "-"
												+ subsubmenuPos, true);
					}
					// 测试禁用和启用
					if (2 == (subsubmenuPos % 4)) {
						myobj.EnableCustomMenuItem2(menuPos, submenuPos,
								subsubmenuPos, false);
					}
				}
			} else // 主菜单增加项目，第3个参数是-1是在主菜单增加
			{
				myobj.AddCustomMenuItem2(menuPos, submenuPos, -1, false, "菜单项目"
						+ menuPos + "-" + submenuPos, false, menuPos * 100
						+ submenuPos);
			}

			// 测试禁用和启用
			if (1 == (submenuPos % 4)) {
				myobj.EnableCustomMenuItem2(menuPos, submenuPos, -1, false);
			}
		}
	}
}

/**
 * 文档类型、扩展名 基础数据
 */
var fileTypes = {
	1 : {
		fileType : 'Word.Document',
		ext : 'doc',
		fileTypeSimple : "wrod"
	},
	2 : {
		fileType : 'Excel.Sheet',
		ext : 'xls',
		fileTypeSimple : "excel"
	},
	3 : {
		fileType : 'PowerPoint.Show',
		ext : 'ppt',
		fileTypeSimple : "ppt"
	},
	4 : {
		fileType : 'Visio.Drawing',
		ext : '',
		fileTypeSimple : "excel"
	},
	5 : {
		fileType : 'MSProject.Project',
		ext : '',
		fileTypeSimple : "excel"
	},
	6 : {
		fileType : 'WPS Doc',
		ext : '',
		fileTypeSimple : "wps"
	},
	7 : {
		fileType : 'Kingsoft Sheet',
		ext : '',
		fileTypeSimple : "et"
	},
	8 : {
		fileType : 'unkownfiletype',
		ext : '',
		fileTypeSimple : "unkownfiletype"
	}
};

var officeController = {

	OnDocumentClosed : function() {
		setFileOpenedOrClosed(false);
	},
	OnDocumentOpened : function(TANGER_OCX_str, TANGER_OCX_obj) {
		if (getOFFICE_CONTROL_OBJ().activeDocument) {
			getOFFICE_CONTROL_OBJ().activeDocument.saved = true;// saved属性用来判断文档是否被修改过,文档打开的时候设置成ture,当文档被修改,自动被设置为false,该属性由office提供.
			// 获取文档控件中打开的文档的文档类型
			var f = getFile(getOFFICE_CONTROL_OBJ().doctype);
			fileType = f.fileType;
			fileTypeSimple = f.fileTypeSimple;
			setFileOpenedOrClosed(true);
		}

		// var $office = $(getOFFICE_CONTROL_OBJ())//
		// .closest("[data-role=office]");
		var $office = $("div[data-role=office]");
		if ($office.get(0)) {
			// $office.office("setProperties");
			// $office.office("EnableTrackRevisions", true);

			// $("div[data-role=office]").office("ShowRevisions", false);

			window.setTimeout(function() {
				$office.office("onDocOpen");
			}, 0);

		}

	},
	BeforeOriginalMenuCommand : function(TANGER_OCX_str, TANGER_OCX_obj) {
		// alert("BeforeOriginalMenuCommand事件被触发");
	},
	OnFileCommand : function(TANGER_OCX_str, TANGER_OCX_obj) {
		if (TANGER_OCX_str == 3) {
			console.log(TANGER_OCX_obj);
			window.saveFileToUrl();
			CancelLastCommand = true;
			return true;
		}
	},
	AfterPublishAsPDFToURL : function(result, code) {
		result = trim(result);
		// alert(result);
		document.all("statusBar").innerHTML = "服务器返回信息:" + result;
		if (result == "文档保存成功。") {
			window.close();
		}
	},
	OnCustomMenuCmd2 : function(menuPos, submenuPos, subsubmenuPos,
			menuCaption, menuID) {
		// alert("第" + menuPos + "," + submenuPos + "," + subsubmenuPos
		// + "个菜单项,menuID=" + menuID + ",菜单标题为\"" + menuCaption
		// + "\"的命令被执行.");
	}
};

/**
 * ntko office
 */
(function($) {

	var plugin = "office", optionKey = plugin + ".options";

	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string' && (options in $.fn[plugin].methods)) {
			var p = this.data(plugin);
			return p ? p[options].apply(p, Array.prototype.slice.call(
					arguments, 1)) : $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i, o) {
			var state = $.data(o, optionKey);
			if (state) {
				$.extend(true, state.options, options);
			} else {
				$.data(o, optionKey, {
					options : $
							.extend(true, {}, $.fn[plugin].defaults, options),
					datas : param
				});
			}
			$.data(o, plugin, new Build(o));
		});
	};

	$.fn[plugin].defaults = {
		events : {
			onAppendRowsByBookMarks : function() {
				console.log(arguments);
			}
		}
	};

	function Build(target) {

		this.$target = $(target);

		this.officeObj = null;
		
		this.pics = 0;

		this.randomUUID = new UUID().createUUID();

		this.options = this.$target.data(optionKey).options;

		this.docUrl = contextPath + "/mx/form/attachment";

		this.timeout = null;

		this.defaultUrl = contextPath + "/webfile/templates/ntko/blank.doc";

		this.mode = {
			Menubar : false
		// 工具栏
		};// 此属性配置需要从后台属性获取[配置的方式]

		this.ActiveDocument = {
			TrackRevisions : true
		// 留痕
		};

		this.properties = {
			modeSetAccept : null,// 接受修订
			modeSetNo : null,// 取消留痕
			modeSetOk : null, // 保留痕迹
			modeSetView : null,// 显示痕迹，
			mainSetMenu : null, // 菜单栏切换
		};

		this.pwd = "ao123";

		(function(that) {

			var $this = that.$target;
			
			
			var isChrome = navigator.userAgent.toLowerCase().match(/chrome/) != null;
			if (isChrome) {

				$this.find("object").attr("type", "application/ntko-plug")//
					.find("param").each(function(i, v){
					var $this = $(this);
						$(this).attr({
						name : "_" + $this.attr("name")
					});
				});
			}

			$this.attr({
				randomparent : that.randomUUID
			});

			var key = $this.attr("key");
			if (key == 'true') {
				that.buildKey();
			}

			that.officeObj = $this.find("object").get(0);

			// that.officeObj.Activate && that.officeObj.Activate(true);

			// that.officeObj.CreateNew("Word.Document");
			/**
			 * 初始赋值
			 */
			officeForm.randomparent.value = that.randomUUID;

			window.setTimeout(function() {
				/**
				 * 隐藏菜单
				 */
				// that.officeObj.Menubar = false;
				$.each([ /* "FilePrint" */, "FileSave", "FileOpen" ],
						function(i, v) {
							that.officeObj[v] = false;
						});

				var webUserName = window.GetParameters().username //
						|| window.GetParameters().actorId;

				that.officeObj.WebUserName = webUserName;// TODO
				
				that.officeObj.Caption = " ";

			}, 1000);

			/**
			 * 事件注册
			 */
			var options = $.data($this.get(0), optionKey).options;
			$.each(options.events, function(et, fn) {
				$this.bind(et, function() {
					return options.events[et].apply(that, //
							Array.prototype.slice.call(arguments, 1));
				});
			});

		})(this);

	}

	$.fn[plugin].methods = {
		/**
		 * 获取uuid
		 */
			
		_trigger: function(et, params) {
			return this.$target.trigger(et, params);
		},
			
		GetUUID : function() {
			return new UUID().createUUID();
		},
		buildKey : function() {// 工具栏
			var that = this;
			var keyBoard = [ "<div class='col-xs-2'>",
					"<br><br><ul style='list-style-type:none'></ul>", "</div>" ];
			var liStr = "<li><span style='font-size: 16px; font-weight: bolder;'>{0}</span></li>";
			var that = this;
			url = contextPath + '/mx/form/defined/getFormCompoentProperties';

			function each_(ret) {
				if (!ret || !ret.rows) {
					return false;
				}
				var collection = new Object(), p = {};
				ret.rows.sort(function(a, b) {
					return b.listNo - a.listNo; // 根据容器属性排序(倒序)
				});
				$.each(ret.rows, function(i, v) {
					if (v.parentId) {
						if (!collection[v.parentId]) {
							collection[v.parentId] = new Array();
						}
						collection[v.parentId].push(v);
					} else {
						p[v.name] = v;
					}
					if (v.id in collection) {
						v.children = collection[v.id];
					} else {
						v.children = collection[v.id] = new Array();
					}
				});
				if (p.officeKey) {
					var iterator = function(items) {
						$.each(items, function(i, v) {
							if (v.name in that.properties) {
								if (v.value == "true") {
									that.properties[v.name] = {
										fn : v.value_
									};
								}
							}
							if (v.children) {
								iterator(v.children);
							}
						});
					};
					iterator(p.officeKey.children);
				}
			}

			jQuery.ajax({
				url : url,
				data : {
					id : that.$target.attr("id"),
					pageId : that.options.pageId,
					dataRole : "office"
				},
				dataType : 'json',
				success : function(ret) {
					each_(ret);
					that.setProperties();
				}
			});
		},
		openDocs : function() {
			var that = this, jq = that.$target;
			var isSet = jq.attr("isSet");
			var randomparent = jq.attr("randomparent");
			var form = jq.find("form").get(0);
			if (!that.officeObj) {
				console.log("office plugin is not init!");
				return false;
			}
			form.randomparent.value = randomparent;
			form.filename.value = form.filename.value || randomparent;
			if (isSet == 'true') {
				that.openByDocId(randomparent);
			} else {
				this.openByUrl(this.defaultUrl);
			}
		},
		/**
		 * 获取文件信息
		 */
		getFileMsg : function(randomparent, fn) {
			var that = this, params = {
				method : 'getOfficeFormAttachment',
				randomparent : randomparent
			};
			$.ajax({
				url : that.docUrl,
				data : params,
				type : 'post',
				dataType : 'JSON',
				success : function(ret) {
					if (ret) {
						var ext = /\.[^\.]+/.exec(ret.fileName);
						officeForm.filename.value = ret.fileName.replace(ext,
								'');
						officeForm.attachmentId.value = ret.id;
						officeForm.randomparent.value = ret.parent;
						fn && fn(ret);
					} else {
						alert("未找到文档!");
					}
				},
				error : function() {
					alert("未找到文档!");
				}
			});

		},

		/**
		 * 留痕等等属性开启关闭
		 */
		setProperties : function() {

			var tc = function(v) {
				try {
					console.log(v.fn);
				//	eval(v.fn);
				} catch (e) {
					console.log(e);
				}
			};

			$.each(this.properties, function(k, v) {
				if (v && v.fn) {
					tc(v);
				}
			});
		},
		/**
		 * 打开模板
		 */
		openByDocId : function(docId) {
			var that = this;
			that.getFileMsg(docId, function() {
				var docUrl = that.docUrl + "?" + $.param({
					method : 'getOfficeByRandomParent',
					randomparent : docId
				});
				that.openByUrl(docUrl);
			});
		},

		ReSet : function() {
			officeForm.attachmentId.value = null;
			officeForm.randomparent.value = that.GetUUID();
		},

		/**
		 * 打开模板，但保存的时候生成新的文件
		 */
		openModelByDocId : function(docId) {
			var that = this;
			that.getFileMsg(docId, function(ret) {
				var docUrl = that.docUrl + "?" + $.param({
					method : 'getOfficeByRandomParent',
					randomparent : docId
				});
				that.openByUrl(docUrl);
				that.ReSet();
			});
		},
		openByUrl : function(docUrl) {
			var that = this;
			try {
				var exec = function(msg) {
					window.intializePage && window.intializePage(docUrl);
					setFileOpenedOrClosed(true);
					console.log(msg || "timeout");
				};
				this.timeout && window.clearTimeout(this.timeout);
				if (!this.timeout) {
					this.timeout = window.setTimeout(exec, 800);
				} else {
					exec("!timeout");
				}
			} catch (e) {
				console.log(e);
			}
		},
		getOffice : function() {
			return this.$target.find("object").get(0);
			// return this.officeObj;
		},
		doc : function() {
			return this.getOffice().ActiveDocument;
		},
		getBookMark : function(bookMark) {
			var doc = this.doc(), bmObj;
			if (!doc.BookMarks.Exists(bookMark)) {
				bmObj = null;
			} else {
				bmObj = doc.BookMarks(bookMark);
			}
			if (!bmObj) {
				console.log("模板中不存在名称为[ " + bookMark + " ]的书签！");
			}
			return bmObj;
		},

		__getContent : function(content, pic) {
			if (content) {
				if (content.length <= (32 + contextPath.length) || pic) {
					content = this.docUrl + "?" + $.param({
						method : 'getOfficeByRandomParent',
						randomparent : content
					});
				}
			}
			return content;
		},
		
		AsyncCounter : function(n){
			if(n) that.pics = that.pics + n
			return (that.pics);
		},
		setValue : function(bookMark, content) {
			var that = this, doc = this.doc();
			if (bookMark == "DocTitle") {//
				officeForm.filename.value = content;
			} else if (bookMark == "InsertMsg") {// 插入签章。。。
				// content 可能是地址，也可能只是附件parent
				// content = this.__getContent(content);
				// if (content) {
				// if (content.length <= (32 + contextPath.length)) {
				// content = that.docUrl + "?" + $.param({
				// method : 'getOfficeByRandomParent',
				// randomparent : content
				// });
				// }
				// this.InsertMsg(this.__getContent(content));
				// }
				return this.InsertMsg(this.__getContent(content));
			} else if (bookMark.indexOf("PIC") > -1) { // 往书签里查图片等等...
				function setPic(bm, c) {
					// if(doc.Bookmarks.exists(bm)){
					// doc.Bookmarks(bm).Select();
					// return that.InsertMsg(that.__getContent(c, true));
					// }
					
					that.AsyncCounter(1);
					
					/**
					 * 异步回调callback
					 */
					if(that.officeObj._callback_)
						that.officeObj._callback_.async = true;
					/**
					 * 加异步处理请求图片
					 */
					window.setTimeout(function() {
						try {
							if (doc.Bookmarks.exists(bm)) {
								doc.Bookmarks(bm).Select();
								that.InsertMsg(that.__getContent(c, true));
							}
						} catch(e){
							console.log(e);
						} finally {
							that.AsyncCounter(-1);
						}

						if(that.AsyncCounter() == 0){
							that.AppendRowsByBookMarksCallback(true);
						}
						
					}, 0);
				}
				if (content) {
					var cts;
					if ((cts = content.split(",")).length > 1) {// 多个
						var bms = bookMark.split("_"), bm;
						if (bms.length != 3) {
							alert("书签格式不对!");
						} else {
							var n = Math.min(bms[1], cts.length), i = 0;
							for (; i < n; i++) {
								bm = bms[0] + "_" + bms[1] + "_" + (i + 1);
								setPic(bm, cts[i]);
							}
						}
					} else {
						setPic(bookMark, content);
					}
				}

				/*
				 * if(doc.Bookmarks.exists(bookMark)){
				 * doc.Bookmarks(bookMark).Select(); return
				 * that.InsertMsg(that.__getContent(content, true)); }
				 */

				return false;
			}
			try { // 文档中的标签赋值
				var bmObj = this.getBookMark(bookMark);
				if (bmObj) {
					this.getOffice().SetBookmarkValue(bookMark, content);
				}
			} catch (e) {
				console.log(e);
			}
		},
		getValue : function(bookMark) {
			var bmObj = this.getBookMark(bookMark);
			if (bmObj) {
				return bmObj.Range.Text;
			}
			return null;
		},
		InsertMsg : function(URL) {
			if (!URL) {
				alert("路径不能为空!");
				return false;
			}
			try {
				this.getOffice().AddPicFromURL(URL,// URL
				// 注意；URL必须返回Word支持的图片类型。
				true,// 是否浮动图片
				0, //
				0, //
				1, // 当前段落处
				100,// 无缩放
				1 // 文字上方
				);
			} catch (e) {
				console.log(e);
			}
		},

		/**
		 * 服务端保存
		 */
		save : function() {
			window.saveFileToUrl();
		},

		/**
		 * 客户端另存为(下载)
		 */
		saveAs : function(title) {
			var officeObj = this.getOffice();
			var doctype = officeObj.doctype;
			var type = fileTypes[doctype];
			if (!type) {
				alert("未知的文件格式!");
				return;
			}

			if (!title) {
				var d = new Date();
				title = d.getFullYear() + "-" + (//
				d.getMonth() + 1) + "-" + d.getDate();
			}

			return (officeObj.webfilename = title + "." + type.ext),//
			officeObj.showdialog(2);
		},

		print : function() {
			var officeObj = this.getOffice();
			officeObj.FilePrint = true;
			officeObj.printout(true);
		},
		/**
		 * 显示，隐藏修订
		 */
		ShowRevisions : function(bl) {
			this.doc().ShowRevisions = bl;
		},
		/**
		 * 修订功能开启
		 */
		EnableTrackRevisions : function(bl) {
			this.doc().TrackRevisions = bl;
		},
		EnableReviewBar : function(boolvalue) {
			var TANGER_OCX_OBJ = this.getOffice();
			TANGER_OCX_OBJ.ActiveDocument.CommandBars("Reviewing").Enabled = boolvalue;
			TANGER_OCX_OBJ.ActiveDocument.CommandBars("Track Changes").Enabled = boolvalue;
			TANGER_OCX_OBJ.IsShowToolMenu = boolvalue; // 关闭或打开工具菜单
		},
		/**
		 * 文档打开时触发事件
		 */
		onDocOpen : function() {

			// 默认开启修订功能
			this.EnableTrackRevisions(true);

			/**
			 * 默认保护修订状态
			 */
			this.Protect(this.pwd);

			// 默认不显示痕迹
			this.ShowRevisions(false);

		},
		/**
		 * 保护修订
		 */
		Protect : function(pwd) {
			if (this.IsProtect()) {
				// alert("当前修订已加密!");
			} else {
				this.doc().Protect(0, !0, pwd);
			}
			// !(this.IsProtect()) && this.doc().Protect(0, !0, pwd);
		},
		IsProtect : function() {
			switch (this.doc().ProtectionType) {
			case -1:
				return !1;
			case 0:
				return !0;
			case 1:
				return !0;
			case 2:
				return !0;
			default:
				return !0
			}
		},
		/**
		 * 取消保护修订
		 */
		Unprotect : function(pwd) {
			if (this.IsProtect()) {
				try {
					this.doc().Unprotect(pwd);
				} catch (e) {
					alert(e.message);
					console.log(e);
				}
			} else {
				// alert("");
			}
			// (this.IsProtect()) && this.doc().Unprotect(pwd);
		},
		/**
		 * 打开另存为窗口
		 */
		ShowDialog : function(type) {
			this.getOffice().ShowDialog(type);
		},

		/**
		 * 插入行
		 */
		InsertRowsBelow : function(n) {
			this.doc().Application.Selection.InsertRowsBelow(n || 1);
		},
		/**
		 * 增加一行
		 */
		AppendRow : function(data) {

		},

		/**
		 * 根据数据对象key(标签)赋值
		 */
		PopulateRow : function(data) {
			if (!data)
				return false;
			var that = this, doc = this.doc();
			$.each(data, function(k, v) {
				if (doc.Bookmarks.exists(k)) {
					that.setValue(k, v);
				}
			});
		},

		AppendRowsByBookMarks : function(bookMarkMapping, data, append, callback) {
			var that = this, doc = this.doc(), bookMark;
			for ( var bm in bookMarkMapping) {
				!bookMark && (bookMark = bm);
			}
			that.officeObj._callback_ = callback;
			if (doc.Bookmarks.exists(bookMark)) {
				if (typeof data == "string") {
					try {
						data = JSON.parse(data);
					} catch (e) {
						console.log(e);
						data = null;
					}
				}

				if (data == null) {
					alert("输入数据格式不对!");
					return;
				} else if (data.length == 0) {
					alert("输入数据格为空!");
					return;
				}

				var row;
				function RowSet(m, d) {
					row = {};
					$.each(m, function(k, v) {
						row[k] = d[v];
					});
					that.PopulateRow(row);
				}

				// tb1_1_1_2_2
				// tb1_1_2_2_2

				var bms = bookMark.split("_"), keys = [];

				var rowCount = bms[3] * 1, colCount = bms[4] * 1;

				var column_ = bms[2], columnType = "";
				if (column_) { // 获取列显示类型(比如PIC)
					try {
						columnType = (column_.substring(column_//
						.match(/\d+/)[0].length)) || "";
					} catch (e) {
						columnType = "";
					}

				}

				bookMark = bms[0] + "_1_1" + columnType + "_" + rowCount//
						+ "_" + colCount;

				bms = bookMark.split("_");

				if (!append) {
					/**
					 * 不追加，判断之前有没有插入数据.先清空
					 */
					for (var i = 1; i < rowCount; i++) {
						for (var k = 0; k < colCount; k++) {
							var bm_ = bms[0] + "_" + (bms[1] * 1 + i) + "_"
									+ (bms[2] * 1 + k) + columnType //
									+ "_" + rowCount + "_" + colCount;
							that.setValue(bm_, "");
						}
					}
				}
				if (data.length > 1) {

					doc.Bookmarks(bookMark).Select();
					/**
					 * 动态插入行
					 */

					var bookMarkMapping_;

					for (var i = 1; i < data.length; i++) {

						/**
						 * 行数限制
						 */
						if (i >= rowCount) {
							break;
						}

						bookMarkMapping_ = {};
						var bm_ = bms[0] + "_" + (bms[1] * 1 + i) + "_"
								+ (bms[2] * 1) + columnType //
								+ "_" + rowCount + "_" + colCount;// 原始第一行标签

						function SetBookMark(k) {
							var bm_ = bms[0] + "_" + (bms[1] * 1) + "_"
									+ (bms[2] * 1 + k) + columnType + //
									"_" + rowCount + "_" + colCount;// 原始第一行标签
							var bm = bms[0] + "_" + (bms[1] * 1 + i) + "_"
									+ (bms[2] * 1 + k) + columnType + //
									"_" + rowCount + "_" + colCount;// 创建新标签
							bookMarkMapping_[bm] = bookMarkMapping[bm_];
							return bm;
						}

						if (doc.Bookmarks.exists(bm_)) {
							for (var k = 0; k < colCount; k++) {
								SetBookMark(k);
							}
						} else {
							that.InsertRowsBelow();
							var Cells = doc.Application.Selection.Cells;
							var n = Cells.Count, ranges = new Array(n);
							for (var $i = 0; $i < n; $i++) {
								ranges[$i] = Cells.Item($i + 1).Range;
							}
							var tmpBm;
							$.each(ranges, function(k, v) {
								v.Select();
								var bm = SetBookMark(k);
								doc.Bookmarks.Add(bm);
								if (k == 0) {
									tmpBm = bm;
								}
							});
							doc.Bookmarks(tmpBm).Select();

						}

						RowSet(bookMarkMapping_, data[i]);
					}
				}
				RowSet(bookMarkMapping, data[0]);
				
				//onAppendRowsByBookMarks
				window.setTimeout(function() {
					that._trigger("onAppendRowsByBookMarks", [data]);
				}, 10);
			} else {
				alert("标签不存在，无法操作[AppendRowsByBookMarks]!");
			}

			that.AppendRowsByBookMarksCallback(false);
		},
		
		AppendRowsByBookMarksCallback: function(async){
			
			if(!that.officeObj._callback_){
				return;
			}
			
			if(async != !!that.officeObj._callback_.async){
				return;
			}
			
			that.officeObj._callback_();
			
			that.officeObj._callback_ = null;
		},

		/**
		 * 根据书签动态添加行
		 */
		AppendRowsByBookMark : function(bookMark, data) {
			var that = this, doc = this.doc();
			if (doc.Bookmarks.exists(bookMark)) {
				if (typeof data == "string") {
					try {
						data = JSON.parse(data);
					} catch (e) {
						console.log(e);
						data = null;
					}
				}

				if (data == null) {
					alert("输入数据格式不对!");
					return;
				}

				!(data instanceof Array) && (data = [ data ]);
				if (data.length > 1) {
					// tb1_tr_td1_2 tb1_tr_td2_2
					var bms = bookMark.split("_"), keys = [];
					var colN = bms[3] * 1;// 列数
					for (var i = 0; i < colN; i++) {
						keys[i] = bms[0] + "_tr_td" + (i + 1) + "_" + colN;
					}

					doc.Bookmarks(bookMark).Select();

					/**
					 * 先执行删除 TODO
					 */
					for (var i = 1; i < data.length; i++) {
						that.InsertRowsBelow();
						var Cells = doc.Application.Selection.Cells;
						var n = Cells.Count, ranges = new Array(n);
						for (var $i = 0; $i < n; $i++) {
							ranges[$i] = Cells.Item($i + 1).Range;
						}
						var row = {};
						// 新增的列增加书签
						$.each(ranges, function(k, v) {
							v.Select();
							var bm = bms[0] + "_tr" + i + "_td" + (k + 1) + "_"
									+ colN;
							row[bm] = data[i][bms[0] + "_tr" + "_td" + (k + 1)
									+ "_" + colN]
									|| "";
							doc.Bookmarks.Add(bm);
						});
						that.PopulateRow(row);
					}
				}
				that.PopulateRow(data[0]);
			}
		}
	};

	Build.prototype = $.extend(true, $.fn[plugin].methods, {
		constructor : Build
	});

	/**
	 * bootstrap alert 与office控件样式问题
	 */
	window.$alert && (window.alert = window.$alert);

})(jQuery);

function GetParameters() {
	return pageParameters;
}
