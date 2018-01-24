var opendoctype, TANGER_OCX_OBJ, docname, ntkodoc, TANGER_OCX_str, TANGER_OCX_obj, docdata, mparent, newWin, TANGER_OCX_bDocOpen = !1, printcount = 0, cutomtoolbar = !1, boolvalue = !1, val = 1;
function objinit() {
    demoinit(),
    TANGER_OCX_OBJ = document.getElementById("TANGER_OCX"),
    TANGER_OCX_OBJ.AddDomainToTrustSite(document.domain),
    TANGER_OCX_OBJ.SetAutoCheckSignKey("ntko"),
    OpenTestDoc("templatefile/default.doc"),
    TANGER_OCX_OBJ.IsNTKOSecSignInstalled() || ($("#signbuttons").hide(),
    $("#sign").html("没有安装NTKO 电子印章系统此功能无法使用！"))
}
function ObjectDisplay(a) {
    var b = document.getElementById("ocxobject");
    a ? (b.style.display = "block",
    b.style.height = "800px",
    a = !1,
    Sys.ie || Sys.ie9 || (TANGER_OCX_OBJ.PutBase64Value(docdata),
    TANGER_OCX_OBJ.toolbars = !1,
    showcutomtoolbar(!0))) : (Sys.ie || Sys.ie9 || (docdata = TANGER_OCX_OBJ.GetBase64Value()),
    b.style.display = "none",
    a = !0)
}
function CreatNewoffice(a) {
    showprogress(!0),
    ObjectDisplay(!0),
    TANGER_OCX_OBJ.CreateNew(a)
}
function OpenTestDoc(a) {
    return null  == TANGER_OCX_OBJ.Caption ? (setvisibleinfo("控件没有正常加载!"),
    !1) : (showprogress(!0),
    TANGER_OCX_OBJ.toolbars = !1,
    TANGER_OCX_OBJ.AddDocTypePlugin(".pdf", "PDF.NtkoDocument", "4.0.0.5", "http://www.ntko.com/control/officecontrol/ntkooledocall.cab", 51, !0),
    TANGER_OCX_OBJ.AddDocTypePlugin(".tif", "TIF.NtkoDocument", "4.0.0.5", "http://www.ntko.com/control/officecontrol/ntkooledocall.cab", 52, !0),
    TANGER_OCX_OBJ.AddDocTypePlugin(".tiff", "TIF.NtkoDocument", "4.0.0.5", "http://www.ntko.com/control/officecontrol/ntkooledocall.cab", 52, !0),
    TANGER_OCX_OBJ.BeginOpenFromURL(a),
    ObjectDisplay(!0),
    void 0)
}
function showcutomtoolbar(a) {
    a ? cutomtoolbar || (TANGER_OCX_OBJ.CustomToolBar = !0,
    TANGER_OCX_OBJ.AddCustomToolButton("新建", 3),
    TANGER_OCX_OBJ.AddCustomToolButton("打开", 4),
    TANGER_OCX_OBJ.AddCustomToolButton("保存", 5),
    TANGER_OCX_OBJ.AddCustomToolButton("打印", 9),
    TANGER_OCX_OBJ.AddCustomToolButton("打印预览", 10),
    TANGER_OCX_OBJ.AddCustomToolButton("关闭文档", -1),
    TANGER_OCX_OBJ.AddCustomToolButton("功能区", -1),
    cutomtoolbar = !0) : TANGER_OCX_OBJ.CustomToolBar = !1
}
function objStatusbar() {
    TANGER_OCX_OBJ.statusbar = !TANGER_OCX_OBJ.statusbar
}
function objTitleBar() {
    TANGER_OCX_OBJ.Titlebar = !TANGER_OCX_OBJ.Titlebar
}
function objMenubar() {
    TANGER_OCX_OBJ.Menubar = !TANGER_OCX_OBJ.Menubar
}
function objCustomToolBar() {
    TANGER_OCX_OBJ.CustomToolBar = !TANGER_OCX_OBJ.CustomToolBar
}
function officeToolBar() {
    TANGER_OCX_OBJ.toolbars = !TANGER_OCX_OBJ.toolbars
}
function objprint(a) {
    TANGER_OCX_bDocOpen ? TANGER_OCX_OBJ.PrintOut(a) : setvisibleinfo("无文档打开不能执行打印！")
}
function objprintpreview() {
    TANGER_OCX_bDocOpen ? TANGER_OCX_OBJ.PrintPreview() : setvisibleinfo("无文档打开不能执行打印预览！")
}
function objexitprintpreview() {
    TANGER_OCX_OBJ.ExitPrintPreview()
}
function showbookmark() {
    TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.ActivePane.View.ShowBookmarks = !TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.ActivePane.View.ShowBookmarks
}
function ShowWindow(a) {
    var d, b = 80, c = 62;
    docdata = TANGER_OCX_OBJ.GetBase64Value(),
    d = window.showModalDialog(a, window, "edge:raised;scroll:1;status:0;help:0;resizable:1;dialogWidth:1000px;dialogHeight:800px;dialogTop:" + c + "px;dialogLeft:" + b + "px"),
    d.open()
}
function modadialoginit() {
    mparent = window.dialogArguments;
    var a = mparent.docdata;
    document.getElementById("TANGER_OCX").PutBase64Value(a)
}
function mpageclose() {
    mparent.docdata = document.getElementById("TANGER_OCX").GetBase64Value(),
    window.opener.document.getElementById("TANGER_OCX").PutBase64Value(mparent.docdata)
}
function DocumentOpened(a) {
    TANGER_OCX_bDocOpen = !0,
    opendoctype = TANGER_OCX_OBJ.DocType,
    initCustomMenus1(),
    showcutomtoolbar(!0),
    showprogress(!1),
    setvisibleinfo("打开文档" + a),
    15 == TANGER_OCX_OBJ.getOfficeVer() && (TANGER_OCX_OBJ.ActiveDocument.Application.Options.UseLocalUserInfo = !0)
}
function filecommand(a) {
    (4 == a || 3 == a) && (ShowModal("这是文件菜单操作接管事件，你可以更改菜单本身的默认操作。</br>如先进行权限判断，确定用户是取消操作还是将文件保存到本地或保存到服务器！"),
    TANGER_OCX_OBJ.CancelLastCommand = !0)
}
function bookmarkpater() {
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Copy(),
    TANGER_OCX_OBJ.ActiveDocument.Bookmarks.item(1).Range.Paste()
}
function TANGER_OCX_OnDocumentClosed() {
    TANGER_OCX_bDocOpen = !1;
    try {
        TANGER_OCX_OBJ.ActiveDocument.AttachedTemplate.Saved = !0
    } catch (a) {} finally {}
}
function Highlight() {
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Range.HighlightColorIndex = 4
}
function DocumentBackGround() {
    TANGER_OCX_OBJ.ActiveDocument.Background.Fill.ForeColor.ObjectThemeColor = 4,
    TANGER_OCX_OBJ.ActiveDocument.Background.Fill.ForeColor.TintAndShade = .8,
    TANGER_OCX_OBJ.ActiveDocument.Background.Fill.Visible = -1,
    TANGER_OCX_OBJ.ActiveDocument.Background.Fill.Solid()
}
function displyaHead() {
    TANGER_OCX_OBJ.Activate(!0),
    TANGER_OCX_OBJ.ActiveDocument.Application.ActiveWindow.DisplayHeadings = !TANGER_OCX_OBJ.ActiveDocument.Application.ActiveWindow.DisplayHeadings
}
function OpenTestDoc1(a) {
    TANGER_OCX_OBJ = document.all.item("TANGER_OCX"),
    TANGER_OCX_OBJ.BeginOpenFromURL(a),
    TANGER_OCX_OBJ.AddCustomToolButton("新建", 3),
    TANGER_OCX_OBJ.AddCustomToolButton("打开", 4),
    TANGER_OCX_OBJ.AddCustomToolButton("保存", 5),
    TANGER_OCX_OBJ.AddCustomToolButton("关闭", -1),
    TANGER_OCX_OBJ.AddCustomToolButton("工具栏", -1)
}
function getDocSize() {
    ShowModal("当前文档有：" + TANGER_OCX_OBJ.DocSize / 1024 + "k")
}
function insertfile() {
    try {
        TANGER_OCX_OBJ.ActiveDocument.Application.Selection.GoTo(-1, 0, 0, "file1"),
        TANGER_OCX_OBJ.ActiveDocument.BookMarks("file2").Select(),
        TANGER_OCX_OBJ.ActiveDocument.BookMarks("file3").Select(),
        TANGER_OCX_OBJ.ActiveDocument.Application.Selection.HomeKey(6, 0)
    } catch (a) {} finally {
        ObjectDisplay(!0)
    }
}
function objSaveAsPDFFile(a) {
    if (TANGER_OCX_bDocOpen)
        try {
            1 == opendoctype ? TANGER_OCX_OBJ.ActiveDocument.ExportAsFixedFormat(a, 17, !1, 0, 0, 1, 1, 0, !0, !0, 0, !0, !0, !1) : 2 == opendoctype && TANGER_OCX_OBJ.ActiveDocument.ExportAsFixedFormat(0, a, 0, !0, !1, 1, 1, !1),
            setvisibleinfo("PDF保存成功" + a)
        } catch (b) {
            TANGER_OCX_OBJ.IsPDFCreatorInstalled() ? (TANGER_OCX_OBJ.SaveAsPDFFile(a, !1),
            setvisibleinfo(TANGER_OCX_OBJ.StatusMessage)) : (setvisibleinfo("请安装PDFCreator"),
            ShowModal("转换为PDF文件需要客户端安装PDFCreator虚拟打印机，请下载安装：<a href='http://www.ntko.com/download/down_s/id/80'>PDFCreator</a>"))
        }
    else
        setvisibleinfo("没有文档处于编辑状态")
}
function aftersavepdffile(a) {
    0 == a ? setvisibleinfo("PDF文件保存成功，请到相关目录下查看") : setvisibleinfo("PDF文件保存失败！")
}
function objSaveAsHtmlFile(a) {
    1 == TANGER_OCX_bDocOpen ? (TANGER_OCX_OBJ.SaveAsHTMLFile(a, !1),
    0 == TANGER_OCX_OBJ.statuscode && setvisibleinfo("保存成功" + a)) : setvisibleinfo("没有文档处于编辑状态!")
}
function objSaveAsOtherFormatFile(a) {
    1 == TANGER_OCX_bDocOpen ? (TANGER_OCX_OBJ.SaveAsOtherFormatFile("2", a, !1),
    0 == TANGER_OCX_OBJ.statuscode ? setvisibleinfo("保存成功" + a) : setvisibleinfo("保存出错了:" + TANGER_OCX_OBJ.StatusMessage)) : setvisibleinfo("没有文档处于编辑状态!")
}
function DocRead() {
    TANGER_OCX_OBJ.ActiveDocument.Protect(3, !0, "123456")
}
function setReadKey() {
    TANGER_OCX_OBJ.SetReadOnly(!0, "123456")
}
function unSetReadKey() {
    TANGER_OCX_OBJ.SetReadOnly(!1, "123456"),
    TANGER_OCX_OBJ.IsShowToolMenu = !0
}
function protectrevision() {
    TANGER_OCX_OBJ.ActiveDocument.Protect(0, !0, "123456")
}
function isprotect() {
    switch (TANGER_OCX_OBJ.ActiveDocument.ProtectionType) {
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
}
function addlocalsign() {
    ntkodoc.Bookmarks.exists("esp") ? (ntkodoc.Bookmarks("esp").Select(),
    TANGER_OCX_OBJ.AddSecSignFromurl("jdy", "hetong.esp", 0, 0, 1, 0, !1, !1, !0, !1, "11111111", !1)) : (TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Range,
    ntkodoc.Bookmarks.Add("esp"),
    TANGER_OCX_OBJ.AddSecSignFromurl("jdy", "hetong.esp", 0, 0, 1, 0, !1, !1, !0, !0, "11111111", !0))
}
function sheetvalue() {
    TANGER_OCX_OBJ.Activate(!1),
    TANGER_OCX_OBJ.SetRangeValue(TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.name, "D20", "当前插入表格名称：【" + TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.name + "】")
}
function TextAlginMent() {
    with (TANGER_OCX_OBJ.Activate(!0),
    TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.Range("D6:D12").Select(),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection)
        HorizontalAlignment = -4108,
        VerticalAlignment = -4108
}
function rowAutoFit() {
    TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.rows("7:7").AutoFit(),
    TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.Columns("A:I").AutoFit()
}
function WorkbookAddName() {
    TANGER_OCX_OBJ.Activate(!0),
    TANGER_OCX_OBJ.ActiveDocument.Application.ActiveWorkbook.Names.Add("test", "=Sheet1!$D$1")
}
function GotoName() {
    TANGER_OCX_OBJ.Activate(!0),
    TANGER_OCX_OBJ.ActiveDocument.Application.goto("test"),
    TANGER_OCX_OBJ.ActiveDocument.Application.ActiveCell.FormulaR1C1 = "你找到我test了!"
}
function bkleftinvalue(value) {
    var bookcount = TANGER_OCX_OBJ.activedocument.Bookmarks.count
      , bname = "";
    for (i = 1; bookcount >= i; i++)
        with (bname = TANGER_OCX_OBJ.activedocument.Bookmarks.item(i).Name,
        TANGER_OCX_OBJ.ActiveDocument.Application.Selection)
            goto(-1, 0, 0, bname),
            MoveLeft(1, 1),
            TypeText(value)
}
function gridlines() {
    TANGER_OCX_OBJ.ActiveDocument.Application.Options.DisplayGridLines = !TANGER_OCX_OBJ.ActiveDocument.Application.Options.DisplayGridLines
}
function addbookmark() {
    var a = TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Range
      , b = "书签名称：<input type='text' class='form-control' placeholder='请输入书签名称' id='bookname' name='bookname'/>书签内容：<input type='text' class='form-control' placeholder='请输入书签内容' id='bookvalue' name='bookvalue'/> ";
    ShowModal(b),
    $("#savebutton").show(),
    "" != a.text && $("#bookvalue").attr("value", a.text),
    $("#savebutton").unbind("click"),
    $("#savebutton").click(function() {
        var e, c = document.getElementById("bookname").value, d = document.getElementById("bookvalue").value;
        return "" == c ? (e = "<span class='label label-danger'>内容不能为空！</span>",
        ShowModal(b + e),
        void 0) : TANGER_OCX_OBJ.activedocument.Bookmarks.exists(c) ? (e = "<span class='label label-danger'>书签已经存在不能重名！</span>",
        ShowModal(b + e),
        void 0) : (TANGER_OCX_OBJ.ActiveDocument.Bookmarks.Add(c, a),
        "" != d && TANGER_OCX_OBJ.SetBookMarkValue(c, d),
        HideModal(),
        void 0)
    }
    )
}
function delbookmark() {
    var a = "选择文档中已有书签名称删除：</br>"
      , b = TANGER_OCX_OBJ.activedocument.Bookmarks.count
      , c = "";
    for (i = 1; b >= i; i++)
        c = TANGER_OCX_OBJ.activedocument.Bookmarks.item(i).Name,
        a += "<label class='checkbox'><input type='checkbox' name='bookbox'  value='" + c + "' />" + c + "</label>";
    ShowModal(a),
    $("#savebutton").show(),
    $("#savebutton").unbind("click"),
    $("#savebutton").click(function() {
        try {
            $('input[name="bookbox"]').each(function() {
                1 == $(this).prop("checked") && TANGER_OCX_OBJ.ActiveDocument.BookMarks.item($(this).attr("value")).Delete()
            }
            ),
            setvisibleinfo("删除书签成功！")
        } catch (a) {
            setvisibleinfo("删除书签出错！")
        }
        HideModal()
    }
    )
}
function insertmark() {
    var a = [["gs", "公司名称"], ["wh", "文号"], ["bt", "标题"], ["zw", "正文"], ["bm", "部门"], ["rq", "日期"], ["ztc", "主题词"], ["cb", "抄报"]];
    try {
        for (i = 0; i < a.length; i++)
            TANGER_OCX_OBJ.activedocument.Bookmarks.exists(a[i][1]) && TANGER_OCX_OBJ.SetBookMarkValue(a[i][1], document.getElementById(a[i][0]).value);
        setvisibleinfo("设置书签成功！")
    } catch (b) {
        setvisibleinfo("设置书签出错！")
    }
}
function getbookmarkvalue() {
    var a = TANGER_OCX_OBJ.activedocument.Bookmarks.count
      , b = ""
      , c = ""
      , d = "";
    for (i = 1; a >= i; i++)
        d = TANGER_OCX_OBJ.activedocument.Bookmarks.item(i).name,
        b = TANGER_OCX_OBJ.getbookmarkvalue(d),
        c += "<label>" + d + ":&nbsp;&nbsp;&nbsp;" + b + "</label></br>";
    ShowModal(c),
    $("#savebutton").hide()
}
function insertbookmark() {
    var a = TANGER_OCX_OBJ.activedocument.Bookmarks.count
      , b = ""
      , c = "<table width='100%'>"
      , d = "";
    for (i = 1; a >= i; i++)
        d = TANGER_OCX_OBJ.activedocument.Bookmarks.item(i).name,
        b = TANGER_OCX_OBJ.getbookmarkvalue(d),
        c += "<tr><td width='15%'>" + d + "：</td><td><input type='text' class='form-control' placeholder='请输入书签内容' id='" + d + "' value='" + b + "'/></td></tr>";
    ShowModal(c + "</table>"),
    $("#savebutton").show(),
    $("#savebutton").unbind("click"),
    $("#savebutton").click(function() {
        try {
            for (i = 1; a >= i; i++)
                d = TANGER_OCX_OBJ.activedocument.Bookmarks.item(i).name,
                TANGER_OCX_OBJ.SetBookmarkValue(d, document.getElementById(d).value);
            HideModal(),
            setvisibleinfo("书签编辑成功！")
        } catch (b) {
            setvisibleinfo("书签编辑出错！")
        }
    }
    )
}
function relmark() {
    var a, b;
    try {
        if (TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Find.ClearFormatting(),
        TANGER_OCX_OBJ.ActiveDocument.Application.Selection.find.text = "【发文日期】",
        a = TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Find.Execute(),
        !a)
            return alert("没找到书签文字");
        TANGER_OCX_OBJ.ActiveDocument.BookMarks("发文日期").Delete(),
        b = TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Range,
        TANGER_OCX_OBJ.ActiveDocument.Bookmarks.Add("发文日期", b),
        TANGER_OCX_OBJ.SetBookMarkValue("发文日期", "2008年8月8日")
    } catch (c) {
        alert(c.description)
    }
}
function bookmarkedit() {
    try {
        var a = TANGER_OCX_OBJ.activedocument.Bookmarks.count;
        for (i = 1; a >= i; i++)
            TANGER_OCX_OBJ.ActiveDocument.Bookmarks.item(i).Range.Editors.Add(-1);
        TANGER_OCX_OBJ.ActiveDocument.Protect(3, !1, "123456", !1, !0),
        setvisibleinfo("书签可编辑区域成功，可对黄色标记内容进行编辑。")
    } catch (b) {
        setvisibleinfo("书签编辑区域设置错误！")
    }
}
function notbookmarkedit() {
    var j, a = TANGER_OCX_OBJ.ActiveDocument, b = a.Application.Selection, c = a.Bookmarks, d = c.Count, e = 0, f = 0, g = 0, h = new Array, i = new Array;
    for (b.Endkey(6),
    g = b.Start,
    j = 1; d >= j; j++)
        h[j] = c.item(j).Range.Start + "." + c.item(j).Range.End;
    for (h.sort(function(a, b) {
        var c = a.split(".")
          , d = b.split(".");
        return parseInt(c[j]) < parseInt(d[j]) ? b - a : a - b
    }
    ),
    j = 0; j < h.length - 1; j++)
        i = h[j].split("."),
        f = i[0],
        b.SetRange(e, f),
        b.Editors.Add(-1),
        e = i[1];
    b.SetRange(e, g),
    b.Editors.Add(-1),
    a.Protect(3, 0, "123456")
}
function findtext() {
    var a = "<input type='text' class='form-control' placeholder='请输入查找内容' id='findtext' name='findtext'/> ";
    ShowModal(a),
    $("#savebutton").show(),
    $("#savebutton").unbind("click"),
    $("#savebutton").click(function() {
        var c, b = document.getElementById("findtext").value;
        return "" == b ? (c = "<span class='label label-danger'>内容不能为空！</span>",
        ShowModal(a + c),
        void 0) : (TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Find.Execute(b, !1, !1, !1, !1, !1, !0, 1, !1),
        HideModal(),
        void 0)
    }
    )
}
function findrelpacetext() {
    var a = "查找:<input type='text' class='form-control' placeholder='请输入查找内容' id='findtext' name='findtext'/>替换：<input type='text' class='form-control' placeholder='请输入替换内容' id='relpacetext' name='relpacetext'/>";
    ShowModal(a),
    $("#savebutton").show(),
    $("#savebutton").unbind("click"),
    $("#savebutton").click(function() {
        var d, b = document.getElementById("findtext").value, c = document.getElementById("relpacetext").value;
        return "" == b || "" == c ? (d = "<span class='label label-danger'>内容不能为空！</span>",
        ShowModal(a + d),
        void 0) : (TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Find.Execute(b, !1, !1, !1, !1, !1, !0, 1, !1, c, 2),
        HideModal(),
        void 0)
    }
    )
}
function findtextnext() {
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Find.Execute()
}
function findtextadddoc() {
    var a, b = "替换的内容";
    alert(111),
    a = prompt("要查找的内容:", ""),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Find.Execute(a, !1, !1, !1, !1, !1, !0, 1, !1, b, 2)
}
function insertwh() {
    TANGER_OCX_OBJ.ActiveDocument.BookMarks("文号").Select(),
    TANGER_OCX_OBJ.SetBookMarkValue("文号", "2008年8月8日第11111111111111号"),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.ParagraphFormat.Alignment = 2
}
function CopyText(a) {
    var b = document.forms[0].elements(a);
    b && (alert(b.value),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.TypeText(b.value))
}
function sectiondelete() {
    var b, c, a = TANGER_OCX_OBJ.ActiveDocument;
    for (alert(a.Sections.Count),
    i = 1; i <= a.Sections.Count; i++)
        b = a.Sections.item(i).Range,
        c = b.End - 1,
        b.SetRange(c, b.End),
        b.Delete()
}
function addWaterMark(a) {
    var b, c;
    try {
        b = TANGER_OCX_OBJ.ActiveDocument,
        b.ActiveWindow.ActivePane.View.SeekView = 9,
        c = b.Application.Selection,
        c.PageSetup.DifferentFirstpageHeaderFooter = c.PageSetup.DifferentFirstpageHeaderFooter = !1,
        -1 == c.PageSetup.OddAndEvenPagesHeaderFooter ? c.PageSetup.OddAndEvenPagesHeaderFooter = !1 : "",
        c.HeaderFooter.Shapes.AddTextEffect(0, a, "宋体", 1, !1, !1, 0, 0).select(),
        c.ShapeRange.Line.Visible = !1,
        c.ShapeRange.Fill.Visible = !0,
        c.ShapeRange.Fill.Solid(),
        c.ShapeRange.Fill.ForeColor.RGB = 12345,
        c.ShapeRange.Fill.Transparency = .5,
        c.ShapeRange.Rotation = 315,
        c.ShapeRange.LockAspectRatio = !0,
        c.ShapeRange.Height = b.Application.CentimetersToPoints(4.13),
        c.ShapeRange.Width = b.Application.CentimetersToPoints(16.52),
        c.ShapeRange.WrapFormat.AllowOverlap = !0,
        c.ShapeRange.WrapFormat.Side = 3,
        c.ShapeRange.WrapFormat.Type = 3,
        c.ShapeRange.RelativeHorizontalPosition = 0,
        c.ShapeRange.RelativeVerticalPosition = 0,
        c.ShapeRange.Left = -999995,
        c.ShapeRange.Top = -999995,
        b.ActiveWindow.ActivePane.View.SeekView = 0
    } catch (d) {
        addWaterMark(a)
    }
}
function addWaterMarkPic(a) {
    var b, c;
    try {
        b = TANGER_OCX_OBJ.ActiveDocument,
        b.ActiveWindow.ActivePane.View.SeekView = 9,
        c = b.Application.Selection,
        c.HeaderFooter.Shapes.AddPicture(a, !1, !0).Select(),
        c.ShapeRange.Name = "WordPictureWatermark1",
        c.ShapeRange.PictureFormat.Brightness = .8,
        c.ShapeRange.PictureFormat.Contrast = .5,
        c.ShapeRange.LockAspectRatio = !0,
        c.ShapeRange.Height = b.Application.CentimetersToPoints(4.42),
        c.ShapeRange.Width = b.Application.CentimetersToPoints(4.92),
        c.ShapeRange.WrapFormat.AllowOverlap = !0,
        c.ShapeRange.WrapFormat.Side = 3,
        c.ShapeRange.WrapFormat.Type = 3,
        c.ShapeRange.RelativeHorizontalPosition = 0,
        c.ShapeRange.RelativeVerticalPosition = 0,
        c.ShapeRange.Left = -999995,
        c.ShapeRange.Top = -999995,
        b.ActiveWindow.ActivePane.View.SeekView = 0,
        setvisibleinfo("插入水印成功")
    } catch (d) {
        setvisibleinfo("addWaterMark errir:" + d.number + ":" + d.description)
    }
}
function insertpicforlocal() {
    TANGER_OCX_bDocOpen ? (TANGER_OCX_OBJ.AddMultiPicFromLocal(),
    0 == TANGER_OCX_OBJ.statuscode && setvisibleinfo("插入图片成功！")) : setvisibleinfo("没有文档处于编辑状态！")
}
function insertpicforurl() {
    TANGER_OCX_bDocOpen ? (TANGER_OCX_OBJ.AddPicFromURL("templatefile/officebanner.jpg", !1, 0, 0, 1, 30, 1),
    0 == TANGER_OCX_OBJ.statuscode && setvisibleinfo("插入图片成功！")) : setvisibleinfo("没有文档处于编辑状态！")
}
function FindHeaderFooter(a, b) {
    TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.ActivePane.View.SeekView = 9,
    TANGER_OCX_OBJ.ActiveDocument.select(),
    TANGER_OCX_OBJ.ActiveDocument.Application.selection.Find.Execute(a, !1, !1, !1, !1, !1, !0, 1, !1, b, 2),
    TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.ActivePane.View.SeekView = 0
}
function HeaderFooter() {
    var a = TANGER_OCX_OBJ.ActiveDocument
      , b = TANGER_OCX_OBJ.ActiveDocument.Application;
    TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.ActivePane.View.SeekView = 9,
    b.Selection.TypeText("重庆软航科技有限公司"),
    b.Selection.TypeParagraph(),
    b.Selection.Font.Bold = 9999998,
    b.Selection.Font.Size = 22,
    b.Selection.TypeText("页眉插入内容演示"),
    a.ActiveWindow.ActivePane.View.SeekView = 0
}
function CellsReplace() {
    var a = prompt("查找要替换的内容:", "")
      , b = prompt("要替换成的内容:", "");
    TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.Cells.Replace(a, b)
}
function chang(size) {
    var height = $("#ocxobject").height()
      , widt = $("#ocxobject").width();
    $("#ocxobject").height(eval(height + size)),
    $("#ocxobject").width(eval(widt + size))
}
function CopyDiv(a) {
    var b, c;
    if (Sys.ie || Sys.ie9)
        try {
            TANGER_OCX_OBJ.IsStrictNoCopy = !1,
            TANGER_OCX_OBJ.NoCopy = !1,
            b = document.body.createControlRange(),
            c = document.getElementById(a),
            c.contentEditable = !0,
            b.addElement(c),
            b.execCommand("Copy", !1) ? (TANGER_OCX_OBJ.ActiveDocument.application.Selection.TypeParagraph(),
            TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Paste(),
            c.contentEditable = !1,
            setvisibleinfo("拷贝网页内容成功！")) : ShowModal("当前浏览器设定不允许拷贝功能，请在浏览器选项中允许此功能。")
        } catch (d) {
            setvisibleinfo("拷贝网页内容出错！")
        }
    else
        ShowModal("此功能只支持IE浏览器")
}
function AddTemplate() {
    TANGER_OCX_OBJ.AddTemplateFromURL("templatefile/ntko.xml", !1),
    setReadKey()
}
function DisplayRulers() {
    TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.ActivePane.DisplayRulers = !TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.ActivePane.DisplayRulers,
    TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.View.ShowParagraphs = !0
}
function ActiveCellAddress() {
    TANGER_OCX_OBJ.Activate(!0);
    var a, b;
    a = TANGER_OCX_OBJ.ActiveDocument.Application.ActiveCell.Row,
    b = TANGER_OCX_OBJ.ActiveDocument.Application.ActiveCell.Column,
    ShowModal("第" + a + "行," + "第" + b + "列")
}
function EndParagraph() {
    if (TANGER_OCX_bDocOpen)
        with (TANGER_OCX_OBJ.ActiveDocument.Application.Selection)
            EndKey(6, 0),
            TypeParagraph(),
            TypeText("领导签字:"),
            TypeParagraph(),
            typetext(nowdate());
    else
        setvisibleinfo("没有文档打开")
}
function tableCenter() {
    TANGER_OCX_OBJ.ActiveDocument.Tables(1).Rows.Alignment = 1
}
function TANGER_OCX_PrintDoc(a) {
    var b, c;
    TANGER_OCX_OBJ.Activate(!0);
    try {
        c = TANGER_OCX_OBJ.ActiveDocument.Application.Options,
        b = c.PrintBackground,
        c.PrintBackground = a
    } catch (d) {}
    TANGER_OCX_OBJ.printout(!0);
    try {
        c = TANGER_OCX_OBJ.ActiveDocument.Application.Options,
        c.PrintBackground = b
    } catch (d) {}
}
function printout() {
    TANGER_OCX_OBJ.ActiveDocument.PrintOut(!0, !0, 0, "", "", "", 0, 1, "", 0, !1, !0, "", !0, 0, 0, 0)
}
function CopyTextToField(a, b) {
    var c, d, e, f, g, h, i;
    try {
        if (c = "",
        e = document.forms[0].elements(a),
        !e)
            return alert("HTML的FORM中没有此输入域：" + a),
            void 0;
        switch (e.type) {
        case "select-one":
            c = e.options[e.selectedIndex].text;
            break;
        case "select-multiple":
            for (g = !0,
            d = 0; d < e.options.length; d++)
                f = e.options[d],
                f.selected && (g ? (c = f.text,
                g = !1) : c += "  " + f.text);
            break;
        default:
            c = e.value
        }
        for (h = TANGER_OCX_OBJ.ActiveDocument.Fields,
        i = 1; i <= h.Count; i++)
            alert(h(i).Code.Text),
            h(i).Code.Text.indexOf(b) >= 0 && (h(i).Select(),
            TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Text = c)
    } catch (j) {
        alert(j.number + ":" + j.description)
    } finally {}
}
function insetOleObject() {
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.InlineShapes.AddOLEObject("Excel.Sheet.8", "", !1, !1)
}
function signselect(a, b) {
    setvisibleinfo(b)
}
function CustomMenuCmd(a, b, c, d) {
    setvisibleinfo("选择了[" + d + "]菜单项。在此你可以调用自己的JS函数")
}
function doHandSign() {
    TANGER_OCX_OBJ.DoHandSign(TANGER_OCX_OBJ.WebUserName, 0, 255, 2, 100, 50, !1, "ntko")
}
function doHandSign2() {
    TANGER_OCX_OBJ.IsFastSaveHandSign2 = !0,
    TANGER_OCX_OBJ.DoHandSign2(TANGER_OCX_OBJ.WebUserName, "ntko", 0, 0, 0, 100)
}
function ScaleWidth() {
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.ShapeRange.ScaleWidth(.79, !1, 0),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.ShapeRange.ScaleHeight(.79, !1, 0)
}
function AddSecSignFromLocal() {
    TANGER_OCX_OBJ.AddSecSignFromLocal("NTKO", "", !0)
}
function addFile() {
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.TypeParagraph(),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.TypeText("插入文件内容开始:"),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.TypeParagraph(),
    TANGER_OCX_OBJ.AddTemplateFromURL("专报信息.doc"),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.TypeText("插入文件内容结束.")
}
function addtemplatefile(a) {
    TANGER_OCX_bDocOpen && 1 == opendoctype ? (TANGER_OCX_OBJ.ActiveDocument.Application.Selection.TypeParagraph(),
    TANGER_OCX_OBJ.AddTemplateFromURL(a)) : setvisibleinfo("没有文档处于编辑状态或者不是WORD文档")
}
function addtemplatelocalfile() {
    TANGER_OCX_bDocOpen && 1 == opendoctype ? (TANGER_OCX_OBJ.ActiveDocument.Application.Selection.TypeParagraph(),
    TANGER_OCX_OBJ.AddTemplateFromLocal("", !0, !1)) : setvisibleinfo("没有文档处于编辑状态或者不是WORD文档")
}
function insertBreak() {
    TANGER_OCX_bDocOpen ? (1 == opendoctype && insertWordBreak(),
    2 == opendoctype && insertExccelBreak(),
    0 == TANGER_OCX_OBJ.statuscode && setvisibleinfo("插入分页符成功")) : setvisibleinfo("没有文档处于编辑状态")
}
function insertWordBreak() {
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.InsertBreak(0)
}
function insertExccelBreak() {
    var a = "e25"
      , b = TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.Range(a);
    TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.HPageBreaks.Add(b),
    TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.VPageBreaks.Add(b)
}
function Merge() {
    TANGER_OCX_OBJ.Activate(!0),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Merge()
}
function Superscript() {
    TANGER_OCX_OBJ.Activate(!0);
    try {
        var a = TANGER_OCX_OBJ.ActiveDocument.Application.ActiveCell.Characters.Count;
        TANGER_OCX_OBJ.ActiveDocument.Application.ActiveCell.Characters(a, 1).Font.Superscript = !0
    } catch (b) {
        alert(b.description)
    }
}
function superscript() {
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Font.Subscript = 9999998
}
function QuickAccessToolbar() {
    TANGER_OCX_OBJ.ShowCommandBar("Quick Access Toolbar", !1)
}
function ToggleRibbon() {
    var a = TANGER_OCX_OBJ.getOfficeVer();
    "12" == a ? TANGER_OCX_OBJ.ActiveDocument.Application.ExecuteExcel4Macro('SHOW.TOOLBAR("Ribbon",False)') : "14" == a ? TANGER_OCX_OBJ.ActiveDocument.CommandBars.ExecuteMso("MinimizeRibbon") : alert("此功能只限office2007以上使用")
}
function Ribbon(a) {
    var c, d, b = TANGER_OCX_OBJ.getOfficeVer();
    alert(b),
    b >= 12 && (c = TANGER_OCX_OBJ.ActiveDocument.CommandBars("Ribbon"),
    d = 0,
    c && (d = c.Height),
    alert(d),
    d > 120 && !a ? alert(2) : 120 > d && a && (alert(3),
    TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.ToggleRibbon()))
}
function getSecSignInfo() {
    alert(TANGER_OCX_OBJ.ActiveDocument.Shapes(1).OLEFormat.Object.SignSN)
}
function signcount() {
    var a, b, c, d, e, f, g;
    if (!TANGER_OCX_bDocOpen || 1 != opendoctype && 2 != opendoctype)
        ShowModal("没有文档处于编辑状态或不是支持的签章文档类型！");
    else
        for (a = TANGER_OCX_OBJ,
        b = a.ActiveDocument,
        c = b.Shapes,
        d = 0,
        e = 1; e <= c.Count; e++)
            f = c.item(e),
            12 == f.TYPE && "NTKO.SecSignControl".toUpperCase() == f.OLEFormat.ClassType.toUpperCase() && (g = f.OLEFormat.Object,
            g.SetForceShowGray(!0),
            g.RefreshCheckStatus(),
            d = ++d)
}
function signcount1() {
    var e, f, g, a = TANGER_OCX_OBJ, b = a.ActiveDocument, c = b.Shapes, d = 0;
    for (e = 1; e <= c.Count; e++)
        f = c.item(e),
        12 == f.TYPE && "NTKO.SecSignControl".toUpperCase() == f.OLEFormat.ClassType.toUpperCase() && (alert("start"),
        g = f.OLEFormat.Object,
        g.SetForceShowGray(!1),
        g.RefreshCheckStatus(),
        alert(g.Caption),
        d = ++d);
    alert(d)
}
function SetSignControlViewMode(a, b) {
    var d, e;
    if (TANGER_OCX_OBJ.GetOfficeVer() <= 11) {
        TANGER_OCX_OBJ.ShowCommandBar("iSignature", a);
        try {
            TANGER_OCX_OBJ.ShowCommandBar("NTKO签章", b)
        } catch (c) {
            alert("请安装签章客户端！")
        }
    } else
        try {
            for (d = TANGER_OCX_OBJ.ActiveDocument.Application.COMAddIns,
            e = d.Count; e > 0; e--)
                "Office Add-In" == d.Item(e).Description ? d.Item(e).Connect = a : "NTKO安全电子印章插件OFFICE服务器版" == d.Item(e).Description && (d.Item(e).Connect = b)
        } catch (c) {
            alert(c.description)
        }
}
function openlocal() {
    TANGER_OCX_OBJ.OpenLocalFile("E:\\test\\bookmark\\default.dot")
}
function objsavelocalfile(a) {
    TANGER_OCX_bDocOpen ? (TANGER_OCX_OBJ.SaveTolocal(a),
    0 == TANGER_OCX_OBJ.status,
    setvisibleinfo("文档保存成功" + a)) : setvisibleinfo("没有文档处理编辑")
}
function addpostil() {
    TANGER_OCX_OBJ.Activate(!0),
    TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.View.ShowHiddenText = !0,
    TANGER_OCX_OBJ.ActiveDocument.Selection.Comments.Add(TANGER_OCX_OBJ.ActiveDocument.Selection.Rang, "IS ok")
}
function GetRangTxet() {
    var a = TANGER_OCX_OBJ.ActiveDocument.Tables(1).Cell(1, 1).range.Text;
    alert(a.substring(0, a.length - 1))
}
function SetReviewMode() {
    (1 == opendoctype || 6 == opendoctype) && (TANGER_OCX_OBJ.ActiveDocument.TrackRevisions = !TANGER_OCX_OBJ.ActiveDocument.TrackRevisions,
    TANGER_OCX_OBJ.ActiveDocument.TrackRevisions ? setvisibleinfo("进入痕迹模式") : setvisibleinfo("退出痕迹模式"))
}
function TANGER_OCX_ShowRevisions() {
    TANGER_OCX_OBJ.ActiveDocument.ShowRevisions = !TANGER_OCX_OBJ.ActiveDocument.ShowRevisions,
    TANGER_OCX_OBJ.ActiveDocument.ShowRevisions ? setvisibleinfo("显示修订文字") : setvisibleinfo("不显示修订文字")
}
function RejectAllChanges() {
    TANGER_OCX_OBJ.ActiveDocument.Application.WordBasic.RejectAllChangesInDoc()
}
function ScrollBar() {
    TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.DisplayHorizontalScrollBar = !TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.DisplayHorizontalScrollBar,
    TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.DisplayVerticalScrollBar = !TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.DisplayVerticalScrollBar
}
function DelShapes() {
    var c, a = TANGER_OCX_OBJ.ActiveDocument, b = a.Shapes;
    for (alert(b.count),
    c = 1; c <= b.Count; c++)
        try {
            b.item(c).Delete()
        } catch (d) {}
    TANGER_OCX_ShowRevisions(!1)
}
function SetOnlyRead() {
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Homekey(6),
    TANGER_OCX_OBJ.SetReadOnly(!0, "")
}
function ReadingLayout() {
    TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.View.ReadingLayout = !TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.View.ReadingLayout,
    TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.View.ReadingLayoutActualView = !TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.View.ReadingLayoutActualView,
    TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.View.ReadingLayout = !1,
    TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.View.Type = 3
}
function ExcelLink() {
    TANGER_OCX_OBJ.Activate(!0),
    TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.Hyperlinks.Add(TANGER_OCX_OBJ.ActiveDocument.Application.Selection, "http://www.ntko.com", "", "屏幕提示的文字", "超链接内容")
}
function getpagecount() {
    TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.PageSetup.Orientation = 2,
    TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.PageSetup.Zoom = 300,
    TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.PageSetup.FirstPageNumber = 2;
    var a = TANGER_OCX_OBJ.ActiveDocument.Application.ExecuteExcel4Macro("GET.DOCUMENT(50)");
    alert(a)
}
function wordlink(a) {
    TANGER_OCX_bDocOpen ? TANGER_OCX_OBJ.ActiveDocument.Hyperlinks.Add(TANGER_OCX_OBJ.ActiveDocument.Application.Selection.range, a, "", "", "") : setvisibleinfo("没有文档打开")
}
function FreezePanes() {
    TANGER_OCX_OBJ.Activate(!0),
    TANGER_OCX_OBJ.ActiveDocument.application.ActiveWindow.FreezePanes = !TANGER_OCX_OBJ.ActiveDocument.application.ActiveWindow.FreezePanes
}
function InsertPage() {
    TANGER_OCX_bDocOpen ? (TANGER_OCX_OBJ.ActiveDocument.Application.Selection.goto(1, 2, 1, 1),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Find.ClearFormatting(),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.InsertBreak(7),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.goto(1, 2, 1, 1),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Font.color = 255) : setvisibleinfo("没有文档打开")
}
function insertendpage() {
    TANGER_OCX_bDocOpen ? (TANGER_OCX_OBJ.ActiveDocument.Application.Selection.EndKey(6, 0),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.InsertBreak(7)) : setvisibleinfo("没有文档打开")
}
function SetRowHead() {
    TANGER_OCX_OBJ.ActiveDocument.tables(1).cell(1, 1).Select(),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Rows.HeadingFormat = !0
}
function WordTextAlignMent() {
    TANGER_OCX_OBJ.ActiveDocument.tables(1).Select(),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.ParagraphFormat.Alignment = 1
}
function InsetTable() {
    var a = TANGER_OCX_OBJ.ActiveDocument.Application.Selection
      , b = a.tables.add(a.range, 1, 5);
    for (b.style = "网格型",
    j = 0; 5 >= j; j++)
        TANGER_OCX_OBJ.ActiveDocument.tables.item(1).cell(1, j).range.Text = "标题" + j
}
function insertIntoTableData() {
    var a, b, c;
    if (TANGER_OCX_bDocOpen) {
        for (a = new Array,
        b = 10,
        0 == TANGER_OCX_OBJ.ActiveDocument.tables.Count && InsetTable(),
        i = 0; b > i; i++)
            for (c = 5,
            a[i] = new Array(c),
            j = 0; c > j; j++)
                a[i][j] = i + 1 + "." + (j + 1);
        for (TANGER_OCX_OBJ.ActiveDocument.tables.item(1).cell(1, 1).select(),
        TANGER_OCX_OBJ.ActiveDocument.Application.Selection.InsertRowsBelow(a.length),
        i = 0; i < a.length; i++)
            for (j = 0; 5 > j; j++)
                TANGER_OCX_OBJ.ActiveDocument.tables.item(1).cell(2 + i, j + 1).range.Text = a[i][j]
    } else
        setvisibleinfo("没有文档打开")
}
function insertTableData() {
    var a = 11;
    for (0 == TANGER_OCX_OBJ.ActiveDocument.tables.Count && InsetTable(),
    i = 1; a > i; i++)
        TANGER_OCX_OBJ.ActiveDocument.tables.item(i).cell(1, 1).select(),
        TANGER_OCX_OBJ.ActiveDocument.tables.item(i).cell(1, 1).range.Text = "表格" + i + "第一个单元格值",
        TANGER_OCX_OBJ.ActiveDocument.tables.item(i).select(),
        TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Copy(),
        TANGER_OCX_OBJ.ActiveDocument.Application.Selection.MoveDown(5, 1),
        TANGER_OCX_OBJ.ActiveDocument.Application.Selection.TypeParagraph(),
        TANGER_OCX_OBJ.ActiveDocument.Application.Selection.PasteAndFormat(0)
}
function TANGER_OCX_PrintDoc(a) {
    try {
        var b = TANGER_OCX_OBJ.ActiveDocument.Application.ActivePrinter;
        confirm("打印机名称:" + b) ? (2 == TANGER_OCX_OBJ.DocType ? TANGER_OCX_OBJ.ActiveDocument.Application.ActiveSheet.PrintOut(null , null , null , !1, b, !0, !0, !0) : 1 == TANGER_OCX_OBJ.DocType && (TANGER_OCX_OBJ.ActiveDocument.Application.ActivePrinter = b),
        TANGER_OCX_OBJ.printout(!a)) : TANGER_OCX_OBJ.printout(a)
    } catch (c) {
        alert("错误：" + c.number + ":" + c.description)
    } finally {}
}
function forprint() {
    if (6 > printcount)
        try {
            TANGER_OCX_PrintDoc(!0)
        } catch (a) {
            alert("文档" + printcount + "打印出错")
        } finally {
            printcount++,
            OpenTestDoc1(printcount + ".doc")
        }
}
function setPagesetup() {
    with (TANGER_OCX_OBJ.ActiveDocument.PageSetup)
        FirstPageTray = 0,
        TopMargin = TANGER_OCX_OBJ.ActiveDocument.Application.CentimetersToPoints(3),
        BottomMargin = TANGER_OCX_OBJ.ActiveDocument.Application.CentimetersToPoints(3),
        LeftMargin = TANGER_OCX_OBJ.ActiveDocument.Application.CentimetersToPoints(3),
        RightMargin = TANGER_OCX_OBJ.ActiveDocument.Application.CentimetersToPoints(3);
    TANGER_OCX_OBJ.ActiveDocument.Tables(1).Rows.LeftIndent = TANGER_OCX_OBJ.ActiveDocument.Application.CentimetersToPoints(0)
}
function getdocname() {
    ShowModal("当前文件名称：" + TANGER_OCX_OBJ.docfilename)
}
function bo() {
    TANGER_OCX_OBJ.ActiveDocument.CommandBars(1).Visible = !TANGER_OCX_OBJ.ActiveDocument.CommandBars(1).visible
}
function delfiled() {
    TANGER_OCX_OBJ.ActiveDocument.Fields("1").Delete()
}
function characterCount() {
    ShowModal("文档中一共有：" + TANGER_OCX_OBJ.activedocument.Characters.Count + "字数。")
}
function backgroundfill() {
    var a = TANGER_OCX_OBJ;
    a.ActiveDocument.Background.Fill.Visible = -1,
    a.ActiveDocument.Background.Fill.ForeColor.RGB = 255e4,
    a.ActiveDocument.Background.Fill.Solid(),
    alert(a.ActiveDocument.Background.Fill.ForeColor.RGB)
}
function ColumnHidden() {
    TANGER_OCX_OBJ.Activate(!0),
    TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.Columns("A:A").Select(),
    TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.Selection.EntireColumn.Hidden = !0
}
function getvalue() {
    TANGER_OCX_OBJ.ActiveDocument.Application.ActiveSheet.Range("A1:B1").Select(),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Copy()
}
function setsheetvalue() {
    for (TANGER_OCX_OBJ.Activate(!0),
    i = 1; 4 >= i; i++)
        TANGER_OCX_OBJ.ActiveDocument.Application.ActiveSheet.Rows(21).Insert;
    TANGER_OCX_OBJ.ActiveDocument.Application.ActiveSheet.Range("B21").value = "处理意见：处理完成",
    TANGER_OCX_OBJ.ActiveDocument.Application.ActiveSheet.Range("B21:E24").MergeCells = !0,
    TANGER_OCX_OBJ.ActiveDocument.Application.ActiveSheet.Range("B21:E24").VerticalAlignment = 1,
    TANGER_OCX_OBJ.ActiveDocument.Application.ActiveSheet.Range("B21:E24").WrapText = !0
}
function setvalue() {
    TANGER_OCX_OBJ.Activate(!0),
    TANGER_OCX_OBJ.SetRangeValue(TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.name, "D6", "重庆软航科技有限公司"),
    TANGER_OCX_OBJ.SetRangeValue(TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.name, "D7", "重庆市"),
    TANGER_OCX_OBJ.SetRangeValue(TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.name, "D8", "重庆市"),
    TANGER_OCX_OBJ.SetRangeValue(TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.name, "D9", "重庆市南岸区"),
    TANGER_OCX_OBJ.SetRangeValue(TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.name, "D10", "400060"),
    TANGER_OCX_OBJ.SetRangeValue(TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.name, "D11", "023-62943208"),
    TANGER_OCX_OBJ.SetRangeValue(TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.name, "D12", "1000000")
}
function InteriorColor() {
    alert(TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.Range("A1").Interior.Color),
    TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.Range("D1").select(),
    TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.Range("D1").Borders(7).LineStyle = "-4142"
}
function copyRamgeFormat() {
    var RangeString, PhasteRangeString, sel, sphaste;
    with (TANGER_OCX_OBJ.Activate(!0),
    RangeString = "A1:B2",
    PhasteRangeString = "A2:B2",
    TANGER_OCX_OBJ.ActiveDocument.Application)
        sel = Range(RangeString),
        Range(RangeString).Select(),
        sel.Copy(),
        sphaste = Range(PhasteRangeString),
        Range(PhasteRangeString).Select(),
        sphaste.PasteSpecial(null , null , !1, !1)
}
function insertForBookMarkValue() {
    TANGER_OCX_OBJ.SetBookmarkValue("xmmc", document.fmtest.h_xmmc.value),
    TANGER_OCX_OBJ.SetBookmarkValue("xmmc2", document.fmtest.h_xmmc.value),
    TANGER_OCX_OBJ.SetBookmarkValue("nd", document.fmtest.h_nd.value),
    TANGER_OCX_OBJ.SetBookmarkValue("zxdm", document.fmtest.h_zxdm.value),
    TANGER_OCX_OBJ.SetBookmarkValue("qydm", document.fmtest.h_qydm.value),
    TANGER_OCX_OBJ.SetBookmarkValue("qydm2", document.fmtest.h_qydm.value)
}
function noopen() {
    TANGER_OCX_OBJ.filenew = !1,
    TANGER_OCX_OBJ.fileopen = !1,
    TANGER_OCX_OBJ.SetCustomToolButtonStatus(0, !1, !0),
    TANGER_OCX_OBJ.SetCustomToolButtonStatus(1, !1, !0),
    $("#creatnewbtn,#openlocalbtn").hide()
}
function nosave() {
    TANGER_OCX_OBJ.FileSaveAs = !1,
    TANGER_OCX_OBJ.filesave = !1,
    TANGER_OCX_OBJ.SetCustomToolButtonStatus(2, !1, !0),
    $("#savelocalbtn,#savelocalbtnm,#savelocalpdfbtn,#savelocalhtmlbtn,#savelocalxmlbtn").hide()
}
function noprint() {
    TANGER_OCX_OBJ.FilePrint = !1,
    TANGER_OCX_OBJ.FilePrintPreview = !1,
    TANGER_OCX_OBJ.SetCustomToolButtonStatus(3, !1, !0),
    TANGER_OCX_OBJ.SetCustomToolButtonStatus(4, !1, !0),
    $("#printbtn,#printpreviewbtn").hide()
}
function dd() {
    try {
        TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.PageSetup.Orientation = 2
    } catch (a) {
        alert(a.description)
    }
}
function replacstr(a, b, c, d) {
    var e, f, g, h, i, j;
    a = a,
    e = b,
    f = c,
    g = a.split(e),
    h = g[d],
    i = h.indexOf(f),
    j = h.substr(i + 1, h.length),
    alert(j)
}
function getfirstlinetext() {
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.GoTo(3, 1, 1, ""),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.EndKey(5, 1),
    ShowModal(TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Range.Text),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.MoveLeft(1, 1)
}
function xlsprotect() {
    TANGER_OCX_OBJ.Activate(!0),
    TANGER_OCX_OBJ.SetRangeLocked(1, "A1", !1),
    TANGER_OCX_OBJ.ActiveDocument.Worksheets(1).Protect("123456", !0, !0, !1, !1, !1, !1, !1, !1, !1, !1, !1, !1, !1, !0, !0),
    TANGER_OCX_OBJ.ActiveDocument.application.ActiveWorkbook.Protect("123456", !0, !1)
}
function sheetvisible(a) {
    TANGER_OCX_OBJ.Activate(!0);
    var b = new Array(2,3);
    for (i in b)
        TANGER_OCX_OBJ.ActiveDocument.sheets(b[i]).Visible = a
}
function xlsunprotect() {
    TANGER_OCX_OBJ.ActiveDocument.Worksheets(1).Unprotect("123456"),
    TANGER_OCX_OBJ.ActiveDocument.application.ActiveWorkbook.Unprotect("123456")
}
function getpagenumber() {
    var a = TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Information(3)
      , b = TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Information(4);
    ShowModal("当前是第:" + a + "页,一共有" + b + "页。</br>光标所在行：" + TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Information(10))
}
function TANGER_OCX_Revisions() {
    var c, d, e, a = "", b = TANGER_OCX_OBJ.ActiveDocument.Revisions.Count;
    if (0 != b)
        for (c = 1; b >= c; c++)
            d = "",
            e = TANGER_OCX_OBJ.ActiveDocument.Revisions.item(c).Range.text,
            d = 1 == TANGER_OCX_OBJ.ActiveDocument.Revisions.item(c).TYPE ? "插入" : "删除",
            a += "【" + TANGER_OCX_OBJ.ActiveDocument.Revisions.item(c).Author + "】" + d + "内容： " + e + "  【时间】：" + TANGER_OCX_OBJ.ActiveDocument.Revisions.item(c).date + "</br>";
    else
        a = "无修订内容";
    ShowModal(a),
    $("#savebutton").hide()
}
function AddRevisionCustom() {
    var c, d, e, f, g, h;
    for (TANGER_OCX_OBJ.ActiveDocument.Application.UserName,
    TANGER_OCX_OBJ.ActiveDocument.ShowRevisions = !0,
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.HomeKey(6, 0),
    c = TANGER_OCX_OBJ.ActiveDocument.Revisions.Count,
    d = 1; c >= d; d++)
        e = TANGER_OCX_OBJ.ActiveDocument.Application.Selection.NextRevision(),
        f = e.Author,
        g = "",
        h = e.TYPE,
        g = 1 == h ? "插入" : "删除",
        TANGER_OCX_OBJ.ActiveDocument.Application.Selection.MoveRight(),
        TANGER_OCX_OBJ.ActiveDocument.Application.Selection.TypeText("[" + f + g + "]")
}
function TANGER_OCX_ShowRev() {
    var e, a = "", b = "", c = new Array, d = TANGER_OCX_OBJ.ActiveDocument.Revisions.Count;
    if (0 != d) {
        for (e = 1; d >= e; e++)
            a += TANGER_OCX_OBJ.ActiveDocument.Revisions.item(e).Author + ",";
        for (c = a.split(","),
        c = unique(c),
        e = 0; e < c.length; e++)
            bname = c[e],
            "" != bname && (b += "<label class='checkbox'><input type='checkbox' name='bookbox'  value='" + bname + "' />" + bname + "</label>");
        ShowModal(b),
        $("#savebutton").show(),
        $("#savebutton").unbind("click"),
        $("#savebutton").click(function() {
            TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.ToggleShowAllReviewers(),
            $('input[name="bookbox"]').each(function() {
                1 == $(this).prop("checked") && (TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.View.Reviewers($(this).attr("value")).Visible = !0)
            }
            ),
            setvisibleinfo("用户痕迹显示成功！"),
            HideModal()
        }
        )
    } else
        b = "当前文档无痕迹内容！需要启用痕迹模式吗？<button type='button' class='btn btn-sm btn-primary' data-toggle='button' onClick='SetReviewMode();'>启用痕迹</button>",
        ShowModal(b)
}
function inserrow() {
    TANGER_OCX_OBJ.Activate(!0),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.GoTo(-1, 0, 0, "cpszjybz"),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.InsertRowsBelow(1),
    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Cells.Merge()
}
function closewindow() {
    if (0 == TANGER_OCX_OBJ.ActiveDocument.Saved) {
        var a = confirm("提示：\n文件未存盘,是否需要保存?");
        a || TANGER_OCX_OBJ.Close()
    } else
        TANGER_OCX_OBJ.Close()
}
function AddControl() {
    var a = TANGER_OCX_OBJ.ActiveDocument.Application.Selection.InlineShapes.AddOLEControl("NTKO.SecSignControl")
      , b = a.ConvertToShape();
    b.width = 200,
    b.height = 100
}
function EndRevisionsView() {
    TANGER_OCX_OBJ.ActiveDocument.Revisions.AcceptAll()
}
function TANGER_OCX_AllRevisions(a) {
    if (isprotect())
        return setvisibleinfo("文档保护模式下禁用！"),
        void 0;
    if (1 == opendoctype || 6 == opendoctype)
        if (a)
            TANGER_OCX_OBJ.ActiveDocument.AcceptAllRevisions();
        else if (1 == opendoctype)
            TANGER_OCX_OBJ.ActiveDocument.Application.WordBasic.RejectAllChangesInDoc();
        else {
            if (6 != officetype)
                return;
            TANGER_OCX_OBJ.ActiveDocument.Revisions.RejectAll()
        }
}
function addButton() {
    TANGER_OCX_OBJ.ActiveDocument.CommandBars("计算编辑器").Controls.Add(1, 26, 11)
}
function mergeTemplete() {
    insertmark();
    var a = TANGER_OCX_OBJ.ActiveDocument.BookMarks("正文");
    a.Select(),
    TANGER_OCX_OBJ.AddTemplateFromURL("templatefile/test.docx", 0)
}
function TANGER_OCX_AddDocHeader(strHeader) {
    var i, cNum, lineStr;
    if (TANGER_OCX_bDocOpen)
        if (cNum = 15,
        lineStr = "",
        1 == opendoctype || 6 == opendoctype)
            try {
                for (i = 0; cNum > i; i++)
                    lineStr += "=";
                with (TANGER_OCX_OBJ.ActiveDocument.Application) {
                    with (Selection.HomeKey(6, 0),
                    Selection.TypeText(strHeader),
                    Selection.TypeParagraph(),
                    Selection.TypeText(lineStr),
                    Selection.TypeText("★"),
                    Selection.TypeText(lineStr),
                    Selection.TypeParagraph(),
                    Selection.HomeKey(6, 1),
                    Selection.ParagraphFormat.Alignment = 1,
                    Selection.Font)
                        NameFarEast = "黑体",
                        Name = "黑体",
                        Size = 24,
                        Bold = !1,
                        Italic = !1,
                        Underline = 0,
                        UnderlineColor = 0,
                        StrikeThrough = !1,
                        DoubleStrikeThrough = !1,
                        Outline = !1,
                        Emboss = !1,
                        Shadow = !1,
                        Hidden = !1,
                        SmallCaps = !1,
                        AllCaps = !1,
                        Color = 255,
                        Engrave = !1,
                        Superscript = !1,
                        Subscript = !1,
                        Spacing = 0,
                        Scaling = 100,
                        Position = 0,
                        Kerning = 0,
                        Animation = 0,
                        DisableCharacterSpaceGrid = !1,
                        EmphasisMark = 0;
                    Selection.MoveDown(5, 3, 0)
                }
                with (TANGER_OCX_OBJ.ActiveDocument.Application) {
                    with (Selection.EndKey(6),
                    Selection.TypeParagraph(),
                    Selection.ParagraphFormat.Alignment = 1,
                    Selection.Font)
                        NameFarEast = "宋体",
                        Name = "宋体",
                        Size = 24,
                        Bold = !1,
                        Italic = !1,
                        Underline = 0,
                        UnderlineColor = 0,
                        StrikeThrough = !1,
                        DoubleStrikeThrough = !1,
                        Outline = !1,
                        Emboss = !1,
                        Shadow = !1,
                        Hidden = !1,
                        SmallCaps = !1,
                        AllCaps = !1,
                        Color = 255,
                        Engrave = !1,
                        Superscript = !1,
                        Subscript = !1,
                        Spacing = 0,
                        Scaling = 100,
                        Position = 0,
                        Kerning = 0,
                        Animation = 0,
                        DisableCharacterSpaceGrid = !1,
                        EmphasisMark = 0;
                    Selection.TypeText(lineStr),
                    Selection.TypeText(lineStr),
                    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.HomeKey(6, 0)
                }
            } catch (err) {
                setvisibleinfo("错误：" + err.number + ":" + err.description)
            } finally {}
        else
            setvisibleinfo("不支持的文档内型!")
}
function insertRedHeadFromUrl(headFileURL) {
    var curSel, BookMarkName;
    if (1 == opendoctype || 6 == opendoctype) {
        if (curSel = TANGER_OCX_OBJ.ActiveDocument.Application.Selection,
        curSel.WholeStory(),
        curSel.Copy(),
        TANGER_OCX_OBJ.openfromurl("templatefile/hongtou.doc"),
        BookMarkName = "正文",
        !TANGER_OCX_OBJ.ActiveDocument.BookMarks.Exists(BookMarkName))
            return setvisibleinfo('Word 模板中不存在名称为："' + BookMarkName + '"的书签！'),
            void 0;
        with (TANGER_OCX_OBJ.ActiveDocument.Application.Selection.GoTo(-1, 0, 0, BookMarkName),
        TANGER_OCX_OBJ.ActiveDocument.Application.Selection.PasteAndFormat("16"),
        TANGER_OCX_OBJ.ActiveDocument.Application.Selection)
            EndKey(6, 0),
            TypeParagraph();
        TANGER_OCX_OBJ.AddTemplateFromURL("templatefile/hongwei.doc"),
        TANGER_OCX_OBJ.ActiveDocument.BookMarks.Exists("正文日期") && TANGER_OCX_OBJ.SetBookmarkValue("正文日期", nowdate()),
        TANGER_OCX_OBJ.ActiveDocument.BookMarks.Exists("印发日期") && TANGER_OCX_OBJ.SetBookmarkValue("印发日期", nowdate())
    } else
        ShowModal("不支持的文档内型")
}
function opemTemplete() {
    var a = document.all.item("TANGER_OCX");
    a.close(),
    a.OpenLocalFile("c:\\2.doc", !1, "Word.Document")
}
function openDoc() {
    var a = document.all.item("TANGER_OCX");
    a.close(),
    a.OpenLocalFile("c:\\1.doc", !1, "Word.Document")
}
function sheetsprint() {
    TANGER_OCX_OBJ.Activate(!0),
    TANGER_OCX_OBJ.ActiveDocument.Sheets.PrintPreview()
}
function addsheettest() {
    TANGER_OCX_OBJ.AddSheet(1, null , 2, "E:\\test\\bookmark\\default.xls")
}
function sheetselect() {
    TANGER_OCX_OBJ.Activate(!0),
    TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.range("d6").select()
}
function getzyinfo() {
    ShowModal(TANGER_OCX_OBJ.ActiveDocument.BuiltInDocumentProperties(1))
}
function addsignfromkey() {
    TANGER_OCX_OBJ.AddSecSignFromEkey("${LoginUser.employeeName}", 0, 0, 1, 2, !1, !1, !0, !1, null , -1, !1)
}
function pptSlideShowSettings() {
    with (TANGER_OCX_OBJ.ActiveDocument.Application.ActivePresentation.SlideShowSettings)
        ShowType = 2,
        LoopUntilStopped = -1,
        ShowWithNarration = -1,
        ShowWithAnimation = -1,
        RangeType = 1,
        AdvanceMode = 2,
        Run()
}
function exitSlideShow() {
    TANGER_OCX_OBJ.ActiveDocument.Application.SlideShowWindows("1").View.Exit(),
    alert("执行结束")
}
function setPagesetupleft() {
    TANGER_OCX_OBJ.Activate(!0),
    TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.pagesetup.LeftFooter = "注：可\n另附\n本副页",
    TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.pagesetup.BottomMargin = TANGER_OCX_OBJ.ActiveDocument.Application.InchesToPoints(1.75)
}
function addsecsignurl() {
    !TANGER_OCX_bDocOpen || 1 != opendoctype && 2 != opendoctype ? ShowModal("没有文档处于编辑状态或不是支持的签章文档类型！") : (0 == isprotect() && unSetReadKey(),
    TANGER_OCX_OBJ.AddSecSignFromURL("NTKO", "templatefile/test.esp", 0, 0, 1, 2, !1, !1, !0, !1, "11111111", !1, !0))
}
function addSecHandSign() {
    !TANGER_OCX_bDocOpen || 1 != opendoctype && 2 != opendoctype ? ShowModal("没有文档处于编辑状态或不是支持的签章文档类型！") : TANGER_OCX_OBJ.AddSecHandSign(TANGER_OCX_OBJ.WebUserName)
}
function AddSignFromEkey() {
    !TANGER_OCX_bDocOpen || 1 != opendoctype && 2 != opendoctype ? ShowModal("没有文档处于编辑状态或不是支持的签章文档类型！") : (TANGER_OCX_OBJ.Activate(!0),
    TANGER_OCX_OBJ.AddSecSignFromEkey("NTKO", 0, 0, 1, 2, !0, !0, !0, !1, "11111111", 1, !1))
}
function SecKeyBoardComment() {
    !TANGER_OCX_bDocOpen || 1 != opendoctype && 2 != opendoctype ? ShowModal("没有文档处于编辑状态或不是支持的签章文档类型！") : TANGER_OCX_OBJ.AddSecKeyBoardComment("NTKO", 100, 40, 1, 2, !1, !0, !1, !1, "11111111", !0)
}
function checksecsign() {
    var a, b, c, d, e, f, g;
    if (!TANGER_OCX_bDocOpen || 1 != opendoctype && 2 != opendoctype)
        ShowModal("没有文档处于编辑状态或不是支持的签章文档类型！");
    else if (a = 0,
    b = 0,
    1 == opendoctype) {
        for (c = TANGER_OCX_OBJ.ActiveDocument,
        d = c.Shapes,
        e = 1; e <= d.Count; e++)
            f = d.item(e),
            12 == f.TYPE && "NTKO.SecSignControl".toUpperCase() == f.OLEFormat.ClassType.toUpperCase() && (b = ++b,
            g = f.OLEFormat.Object,
            g.RefreshCheckStatus(),
            g.IsSignChecked && (a = ++a));
        ShowModal("文档中有：" + b + "个印章," + a + "个有效印章！")
    } else if (2 == opendoctype) {
        for (d = TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.OLEObjects,
        e = 1; e <= d.Count; e++)
            f = d.item(e),
            "NTKO.SecSignControl".toUpperCase() == f.ProgId.toUpperCase() && (b = ++b,
            g = f.Object,
            g.RefreshCheckStatus(),
            g.IsSignChecked && (a = ++a));
        ShowModal("文档中有：" + b + "个印章," + a + "个有效印章！")
    }
}
function addLocalSign() {
    if (!TANGER_OCX_bDocOpen || 1 != opendoctype && 2 != opendoctype)
        ShowModal("没有文档处于编辑状态或不是支持的签章文档类型！");
    else
        try {
            TANGER_OCX_OBJ.AddSignFromLocal("ntko", "", !0, 100, 100, "ntko", 3, 100, 1)
        } catch (a) {
            setvisibleinfo("添加本地图片印章功能出错！")
        }
}
function addServerSign(a) {
    if (!TANGER_OCX_bDocOpen || 1 != opendoctype && 2 != opendoctype)
        ShowModal("没有文档处于编辑状态或不是支持的签章文档类型！");
    else
        try {
            TANGER_OCX_OBJ.AddSignFromURL("ntko", a, 0, 0, "ntko", 1, 100, 1)
        } catch (b) {
            setvisibleinfo("添加服务器图片印章功能出错！")
        }
}
function DoCheckSign() {
    if (TANGER_OCX_bDocOpen) {
        var a = TANGER_OCX_OBJ.DoCheckSign(!1, "ntko");
        setvisibleinfo(a)
    }
}
function picsignvisble(a) {
    TANGER_OCX_OBJ.SetSignsVisible("*", a, "ntko", 0)
}
function officeclose() {
    0 == TANGER_OCX_OBJ.ActiveDocument.Saved ? (ShowModal("你正在编辑的文档还没有保存，你可以先保存后再关闭。"),
    $("#savebutton").show(),
    $("#savebutton").unbind("click"),
    $("#savebutton").click(function() {
        TANGER_OCX_OBJ.ShowDialog(3),
        TANGER_OCX_OBJ.Close(),
        TANGER_OCX_bDocOpen = !1,
        setvisibleinfo("文档已经关闭"),
        HideModal()
    }
    )) : (TANGER_OCX_OBJ.Close(),
    TANGER_OCX_bDocOpen = !1,
    setvisibleinfo("文档已经关闭"))
}
function setusername() {
    var a = "当前用户：" + TANGER_OCX_OBJ.WebUserName + "<input type='text' class='form-control' placeholder='请输入用户名' id='username' name='username'/> ";
    ShowModal(a),
    $("#savebutton").show(),
    $("#savebutton").unbind("click"),
    $("#savebutton").click(function() {
        var c, b = document.getElementById("username").value;
        return "" == b ? (c = "<span class='label label-danger'>内容不能为空！</span>",
        ShowModal(a + c),
        void 0) : (TANGER_OCX_OBJ.WebUserName = b,
        setvisibleinfo("用户名设置成功！"),
        HideModal(),
        void 0)
    }
    )
}
function nocopy() {
    TANGER_OCX_OBJ.IsNoCopy ? (setvisibleinfo("启用复制粘贴功能！"),
    TANGER_OCX_OBJ.IsNoCopy = !1,
    $("#cut,#copy,#paste").show()) : (setvisibleinfo("禁用复制粘贴功能！"),
    TANGER_OCX_OBJ.IsNoCopy = !0,
    $("#cut,#copy,#paste").hide())
}
function cut() {
    TANGER_OCX_OBJ.ActiveDocument.application.Selection.Cut()
}
function copy() {
    TANGER_OCX_OBJ.ActiveDocument.application.Selection.Copy()
}
function paste() {
    TANGER_OCX_OBJ.ActiveDocument.application.Selection.PasteAndFormat(16)
}
function trycatchtest() {
    try {
        12 == val ? alert("ok") : wi.print()
    } catch (a) {
        val++,
        alert(val),
        trycatchtest()
    }
}
function copydata() {
    var a = TANGER_OCX_OBJ.data;
    TANGER_OCX_OBJ.close(),
    TANGER_OCX_OBJ.CreateNew("word.document"),
    TANGER_OCX_OBJ.data = a
}
function initCustomMenus1() {
    var b, c, d, a = TANGER_OCX_OBJ;
    for (b = 0; 1 > b; b++)
        for (a.AddCustomMenu2(b, "自定义菜单(&" + b + ")"),
        c = 0; 1 > c; c++)
            for (a.AddCustomMenuItem2(b, c, -1, !0, "自定义菜单组", !1),
            d = 0; 3 > d; d++)
                0 == d && a.AddCustomMenuItem2(b, c, d, !1, "自定义菜单1", !1, 100 * b + 20 * c + d),
                1 == d && a.AddCustomMenuItem2(b, c, d, !1, "自定义菜单2", !1, 100 * b + 20 * c + d),
                2 == d && a.AddCustomMenuItem2(b, c, d, !1, "自定义菜单3", !1, 100 * b + 20 * c + d)
}
function CustomToolBarCom(a) {
    0 == a && TANGER_OCX_OBJ.CreateNew("Word.Document"),
    1 == a && TANGER_OCX_OBJ.ShowDialog("1"),
    2 == a && (TANGER_OCX_OBJ.AutoSaveHandSign2Pic(),
    TANGER_OCX_OBJ.ShowDialog("3")),
    3 == a && objprint(!0),
    4 == a && objprintpreview(),
    5 == a && officeclose(),
    6 == a && (TANGER_OCX.toolbars = !TANGER_OCX.toolbars)
}
function add2DPDF417code() {
    var a = "GB0626-2005^00000000^文档内容^|";
    TANGER_OCX_OBJ.Add2DCodePic(1, a, !0, 20, 20, 1, 100, 1, !0)
}
function add2DQRcode() {
    var a = "蒋道勇^NTKO^技术^13333333333^133@133.com^重庆南岸区^www.ntko.com^^786914423^|";
    TANGER_OCX_OBJ.Add2DCodePic(2, a, !0, 20, 20, 1, 100, 1, !0)
}
function add2DGWcode() {
    var a = "";
    TANGER_OCX_OBJ.Add2DCodePic(1, a, !0, 20, 20, 1, 100, 1, !0)
}
function add2DGWcode1() {
    var a = "";
    TANGER_OCX_OBJ.Add2DCodePic(1, a, !0, 20, 20, 1, 100, 1, !0, "barCodeVer=19900101&barCodeUrgenType=111&barCodeUrgenLevel=abc")
}
function Add128() {
    var a = "ABCDEFG123456789";
    TANGER_OCX_OBJ.Add2DCodePic(3, a, !0, 20, 20, 1, 100, 1, !0)
}
function Add39() {
    var a = "1234321";
    TANGER_OCX_OBJ.Add2DCodePic(4, a, !0, 20, 20, 1, 100, 1, !0)
}
function EAN13() {
    var a = "7501031311309";
    TANGER_OCX_OBJ.Add2DCodePic(5, a, !0, 20, 20, 1, 100, 1, !0)
}
function winreload() {
    document.location.href = "http://www.ntko.com"
}
function time() {
    t_div = document.getElementById("showtime");
    var a = new Date;
    t_div.innerHTML = "现在是" + a.getFullYear() + "年" + (a.getMonth() + 1) + "月" + a.getDate() + "日" + a.getHours() + "时" + a.getMinutes() + "分" + a.getSeconds() + "秒",
    setTimeout(time, 1e3)
}
function nowdate() {
    var a = new Date;
    return datestr = a.getFullYear() + "年" + (a.getMonth() + 1) + "月" + a.getDate() + "日"
}
function demoinit() {
    $(".dropdown-menu,#myModal").append('<iframe frameborder=0 scrolling=no style="background-color:transparent; position: absolute; z-index: -1; width: 100%; height: 100%; top: 0;left:0;"></iframe>'),
    $("#ocxobject").resizable(),
    "win64" == window.navigator.platform.toLowerCase() && ShowModal("当前使用的是64位IE浏览器，请使用32位浏览器或者安装IE10以上版本(微软默认IE10以上版本运行32位模式)"),
    tabchange()
}
function tabchange() {
    $('a[data-toggle="tab"]').on("shown.bs.tab", function(a) {
        var b = ""
          , c = "";
        b = $(a.target)[0].innerHTML,
        c = b.substring(b.indexOf("#") + 1, b.length),
        "word" == c && 1 != opendoctype && OpenTestDoc("templatefile/default.doc"),
        "excel" == c && 2 != opendoctype && OpenTestDoc("templatefile/default.xls"),
        "ppt" == c && 3 != opendoctype && OpenTestDoc("templatefile/default.ppt")
    }
    )
}
function AutoScroll(a) {
    $(a).find("ul:first").animate({
        marginTop: "-25px"
    }, 500, function() {
        $(this).css({
            marginTop: "0px"
        }).find("li:first").appendTo(this)
    }
    )
}
function showprogress(a) {
    a ? $("#progress").show() : $("#progress").hide()
}
function ShowModal(a) {
    $("#modelbody").html(a),
    $("#myModal").modal("show")
}
function HideModal() {
    $("#savebutton").hide(),
    $("#myModal").modal("hide")
}
function setvisibleinfo(a) {
    document.getElementById("infovalue").innerHTML = a
}
function unique(a) {
    var b, c, d;
    for (a = a || [],
    b = {},
    len = a.length,
    c = 0; len > c; c++)
        d = a[c],
        "undefined" == typeof b[d] && (b[d] = 1);
    a.length = 0;
    for (c in b)
        a[a.length] = c;
    return a
}
function getURL() {
    var a = window.document.location.href
      , b = window.document.location.pathname
      , c = a.indexOf(b)
      , d = a.substring(0, c)
      , e = b.substring(0, b.substr(1).indexOf("/") + 1)
      , f = d + e;
    return f
}
function Todate(a) {
    var b, c, d;
    return a += "",
    b = "",
    c = new Array,
    c.Jan = 1,
    c.Feb = 2,
    c.Mar = 3,
    c.Apr = 4,
    c.May = 5,
    c.Jan = 6,
    c.Jul = 7,
    c.Aug = 8,
    c.Sep = 9,
    c.Oct = 10,
    c.Nov = 11,
    c.Dec = 12,
    d = new Array,
    d.Mon = "一",
    d.Tue = "二",
    d.Wed = "三",
    d.Thu = "四",
    d.Fri = "五",
    d.Sat = "六",
    d.Sun = "日",
    str = a.split(" "),
    b = str[3] + "-" + c[str[1]] + "-" + str[2] + "  " + str[4]
}
function goTop() {
    $(window).scroll(function() {
        $(window).scrollTop() > 200 ? ($("#gotop").fadeIn(1e3),
        $("#ocxobject").animate({
            height: $("#ocxobject")[0].scrollHeight + 100
        }, "fast")) : $("#gotop").fadeOut(1e3)
    }
    )
}
function goTopinit() {
    setvisibleinfo("此模式下你可以向下滚动浏览器垂直滚动条，文档自动向下延伸阅读，点击向上按钮快速返回(BETA)"),
    TANGER_OCX_OBJ.statusbar = !1,
    $("#gotop").click(function() {
        $("body,html").animate({
            scrollTop: 0
        }, 500),
        $("#ocxobject").height(880)
    }
    ),
    $("#gotop").mouseover(function() {
        $(this).css("background", "url(css/images/backtop.png) no-repeat 0px 0px")
    }
    ),
    $("#gotop").mouseout(function() {
        $(this).css("background", "url(css/images/backtop.png) no-repeat -36px 0px")
    }
    ),
    goTop()
}