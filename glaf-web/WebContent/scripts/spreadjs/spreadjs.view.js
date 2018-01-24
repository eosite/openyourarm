

//Export File
function exportFile(serverUrl, content) {
	var formInnerHtml = '<input type="hidden" name="type" value="application/json" />';
	formInnerHtml += '<input type="hidden" name="data" value="' + htmlSpecialCharsEntityEncode(content) + '" />';
	content = null;
	var $iframe = $("<iframe style='display: none' src='about:blank'></iframe>").appendTo("body");
	$iframe.ready(function () {
		var formDoc = getiframeDocument($iframe);
		formDoc.write("<html><head></head><body><form method='Post' action='" + serverUrl + "'>" + formInnerHtml + "</form>dummy windows for postback</body></html>");
		formInnerHtml = null;
		var $form = $(formDoc).find('form');
		$form.submit();
		$form[0].reset();
	});
}

//Help Method
function getiframeDocument($iframe) {
	var iframeDoc = $iframe[0].contentWindow || $iframe[0].contentDocument;
	if (iframeDoc.document) {
		iframeDoc = iframeDoc.document;
	}
	return iframeDoc;
}

function htmlSpecialCharsEntityEncode(str) {
	var htmlSpecialCharsRegEx = /[<>&\r\n"']/gm;
	var htmlSpecialCharsPlaceHolders = {
		'<' : 'lt;',
		'>' : 'gt;',
		'&' : 'amp;',
		'\r' : "#13;",
		'\n' : "#10;",
		'"' : 'quot;',
		"'" : 'apos;' /*single quotes just to be safe*/
	};
	return str.replace(htmlSpecialCharsRegEx, function (match) {
		return '&' + htmlSpecialCharsPlaceHolders[match];
	});
}

function showLoading() {
	$("#loading").css("position", "relative");
	var width = $("#loading").width() + 2,
	height = $("#loading").height();
	$("<span id='delaySpan'><span id='icon' style='display:inline-block'></span>Importing...</span>")
	.css("left", width / 2 - 70)
	.css("top", height / 2 - 30)
	.css("position", "absolute")
	.css("color", "#4f4f4f")
	.css("background", "#ffffff")
	.css("border", "1px solid #a8a8a8")
	.css("border-radius", "3px")
	.css("-webkit-border-radius", "3px")
	.css("box-shadow", "0 0 10px rgba(0, 0, 0, 0.25")
	.css("font-family", "Arial, sans-serif")
	.css("font-size", "20px")
	.css("padding", "0.4em")
	.insertAfter("#ss");
	$("<div id='delayDiv'></div>")
	.css("background", "#2D5972")
	.css("opacity", 0.3)
	.css("position", "absolute")
	.css("top", 0)
	.css("left", 0)
	.css("width", width)
	.css("height", height)
	.insertAfter("#ss");
}

function hideLoading() {
	$("#delayDiv").remove();
	$("#delaySpan").remove();
}

function updateSheetList() {
	$("#sheetList").empty();
	$("#sheetList").append("<option value='All'>All</option>");
	var spread = $("#ss").data('spread');
	if (spread && spread.sheets && spread.sheets.length >= 0) {
		for (var index = 0; index < spread.sheets.length; index++) {
			var sheetName = spread.sheets[index].getName();
			$("#sheetList").append("<option value='" + sheetName + "'>" + sheetName + "</option>");
		}
	}
}

function getSaveFlags() {
	var ExcelSaveFlags = {
		NoFlagsSet : 0,
		NoFormulas : 1,
		SaveCustomRowHeaders : 2,
		SaveCustomColumnHeaders : 4,
		SaveAsFiltered : 8,
		SaveBothCustomRowAndColumnHeaders : 6,
		SaveAsViewed : 136,
		DataOnly : 32,
		AutoRowHeight : 4096
	};

	var flags = ExcelSaveFlags.NoFlagsSet;
	var collection = $("#saveFlags input");
	$.each(collection, function (i, v) {
		if (collection[i].checked) {
			flags |= ExcelSaveFlags[collection[i].id];
		}
	});
	return flags;
}

function getSavePDFSettings() {
	var author_prop = $("#author").val(),
	title_prop = $("#title").val(),
	subject_prop = $("#subject").val(),
	creator_prop = $("#application").val(),
	keywords_prop = $("#keyWords").val(),
	centerWindow_prop = $("#centerWindow").prop("checked"),
	displayDocTitle_prop = $("#showTitle").prop("checked"),
	hideMenubar_prop = !($("#showMenuBar").prop("checked")),
	fitWindow_prop = $("#fitWindow").prop("checked"),
	hideToolbar_prop = !($("#showToolbar").prop("checked")),
	hideWindowUI_prop = $("#showWindowUI").prop("checked");
	var settings = {
		author : author_prop,
		title : title_prop,
		subject : subject_prop,
		creator : creator_prop,
		keywords : keywords_prop,
		centerWindow : centerWindow_prop,
		displayDocTitle : displayDocTitle_prop,
		hideMenubar : hideMenubar_prop,
		fitWindow : fitWindow_prop,
		hideToolbar : hideToolbar_prop,
		hideWindowUI : hideWindowUI_prop
	};
	return settings;
}

function getSheetList(spread) {
	var sheetCount = spread.getSheetCount();
	var sheetIndexes = [];
	var list = $("#sheetList").val();
	if (list === "All") {
		for (var index = 0; index < sheetCount; index++) {
			sheetIndexes.push(index);
		}
	} else if (list !== "") {
		if (spread && spread.sheets && spread.sheets.length >= 0) {
			for (var index = 0; index < spread.sheets.length; index++) {
				var sheetName = spread.sheets[index].getName();
				if (list === sheetName) {
					sheetIndexes.push(index);
					break;
				}
			}
		}
	}
	return sheetIndexes;
}

function setExportFileName(data) {
	var name = $("#exportFileName").val();

	if (data && name) {
		data["exportFileName"] = name;
	}
}

function getExportServerUrl() {
	//return $("#exportUrl").val();
	return "http://localhost:10787/xsapi/Export";
}

function getImportServerUrl() {
	//return $("#importUrl").val();
	return "http://localhost:10787/xsapi/Import";
}
$(function () {
	//settingFrameHeight();
	$(".spreadDiv").css("height", $(window).height() - 100);
});
//Import and export
$(function () {
	// show and hide toolbar content   and  refresh the spread 
	$(".titleControl").on("dblclick",function(){
		$(".tab-content").toggleClass("hideContent");
		var $content = $(".tab-content"),
			hasClass = $content.hasClass('hideContent'),
			$spreadDiv = $(".spreadDiv");
		$(this).text(hasClass?"显示":"隐藏");
		$spreadDiv.css("height", $(window).height() - (hasClass?100:170));
		$spreadDiv.data("spread").refresh();
	});
	new GcSpread.Sheets.Spread($("#ss")[0], {
		sheetCount : 1
	});
	updateSheetList();

	$(".rangeSlider").ionRangeSlider({
		grid : false,
		min : 0,
		max : 400,
		postfix : "%",
		hide_min_max : true
	});
	$(".rangeSlider").on("change", function () {
		var $this = $(this),
		value = $this.prop("value");
		$(".zoomval").text(value + "%");
		setSheetZoom(value / 100);
	});
	$(".zoomlink").click(function () {
		var zoomVal = $(this).attr("val");
		$(".zoomval").text(zoomVal + "%");
		$(".rangeSlider").val(zoomVal);
		var slider = $(".rangeSlider").data("ionRangeSlider");
		slider.update({
			from : zoomVal
		});
		setSheetZoom(zoomVal / 100);
	});
	initSpread();
	getReportData();
	//导入按钮事件
	$("#import_excel").click(function () {
		var files = $('#import_excel_file').prop('files');
		var fd = new FormData();
		fd.append('files[]', files[0]);
		fd.append('ExcelOpenFlags', 0);
		fd.append('Password', '');

		$.ajax({
			url : getImportServerUrl(),
			type : "POST",
			data : fd,
			processData : false,
			contentType : false,
			success : function (response) {
				var spreadObj = response.spread;
				if (spreadObj) {
					var spread = $("#ss").data("spread");
					spread.fromJSON(spreadObj);
					updateSheetList();
					hideLoading();
					spreadObj = null;
					//设置缩放比例 setSheetZoom(1);
				} else if (spreadJson.error) {
					hideLoading();
					alert(spreadJson.error);
				}
			},
			error : function (jqXHR, textStatus, errorMessage) {
				hideLoading();
				console.log(errorMessage);
			}
		});
	});
    //导出Excel/保存按钮事件
	$("#export_excel").click(function () {
		var spread = $("#ss").data("spread");
		var saveFlags = getSaveFlags();
		var password = $("#exportPassword").val();
		var dataObj = {
			"spread" : spread.toJSON(),
			"exportFileType" : "xlsx",
			"excel" : {
				"saveFlags" : saveFlags,
				"password" : password
			}
		};
		setExportFileName(dataObj);
		var content = JSON.stringify(dataObj);
		dataObj = null;
		var serverUrl = getExportServerUrl();
		exportFile(serverUrl, content);
	});
    //导出PDF按钮事件
	$("#export_pdf").click(function () {
		var spread = $("#ss").data("spread");
		var settings = getSavePDFSettings();
		var sheetIndexes = getSheetList(spread);
		var dataObj = {
			"spread" : spread.toJSON(),
			"exportFileType" : "pdf",
			"pdf" : {
				"sheetIndexes" : sheetIndexes,
				"setting" : settings
			}
		};
		setExportFileName(dataObj);
		var content = JSON.stringify(dataObj);
		dataObj = null;
		var serverUrl = getExportServerUrl();
		exportFile(serverUrl, content);
	});
	//高级打印窗口打印按钮事件
	$("#print").click(function () {
		// used to adjust print range, should set with printInfo (refer custom print for detail)
		//spread.sheets[0].setText(31, 11, " ");
		//var spread = $("#ss").data("spread");
		//spread.print();
		$('#printModal').modal('show');
	});
	//高级打印按钮事件
	$("#btnSetPrintInfo").click(function () {
		var spread = $("#ss").data("spread");
		function setPrintInfoNumberValueItem(printInfo, key, method) {
			var value = parseInt($("#" + key).val()),
			method = method || key;

			if (!isNaN(value)) {
				printInfo[method](value);
			}
		}

		var sheet = spread.getActiveSheet(),
		printInfo = sheet.printInfo();

		["rowStart", "rowEnd", "columnStart", "columnEnd",
			"repeatRowStart", "repeatRowEnd", "repeatColumnStart", "repeatColumnEnd"
		].forEach(function (name) {
			setPrintInfoNumberValueItem(printInfo, name);
		});

		printInfo.showBorder($("#showBorder").prop("checked"));
		printInfo.showGridLine($("#showGridLine").prop("checked"));
		printInfo.showRowHeader(+$("#showRowHeader").val());
		printInfo.showColumnHeader(+$("#showColumnHeader").val());
		printInfo.headerLeft($("#headerLeft").val());
		printInfo.headerLeftImage($("#headerLeftImage").val());
		printInfo.headerCenter($("#headerCenter").val());
		printInfo.headerCenterImage($("#headerCenterImage").val());
		printInfo.headerRight($("#headerRight").val());
		printInfo.headerRightImage($("#headerRightImage").val());
		printInfo.footerLeft($("#footerLeft").val());
		printInfo.footerLeftImage($("#footerLeftImage").val());
		printInfo.footerCenter($("#footerCenter").val());
		printInfo.footerCenterImage($("#footerCenterImage").val());
		printInfo.footerRight($("#footerRight").val());
		printInfo.footerRightImage($("#footerRightImage").val());
		//设置打印缩放比例
		printInfo.zoomFactor(1);
	});
	//快速打印按钮事件
	$(".modalPrint").click(function () {
		var spread = $("#ss").data("spread");
		spread.print();
	});
	//第一页按钮事件
	$("#firstPage").click(function () {
		currentPage = 1;
		gotoPage(currentPage);
	});
	//最后一页按钮事件
	$("#lastPage").click(function () {
		currentPage = totalPage;
		gotoPage(totalPage);
	});
	//前一页按钮事件
	$("#prevPage").click(function () {
		currentPage = currentPage - 1;
		gotoPage(currentPage);
	});
	//后一页按钮事件
	$("#nextPage").click(function () {
		currentPage = currentPage + 1;
		gotoPage(currentPage);
	});
});
//设置报表显示比例
function setSheetZoom(val) {
	var spread = $("#ss").data("spread");
	spread.allowUserZoom = false;
	var activeSheet = spread.getActiveSheet();
	activeSheet.zoom(val);
	activeSheet.isPaintSuspended(false);
	activeSheet.repaint();
}
function getReportData() {
	gotoPage(1);
}
var defaultZoom;
//初始化报表外观和比例
function initSpread() {
	var spread = $("#ss").data("spread");
	spread.tabStripVisible(false);
	var sheet = spread.getActiveSheet();
	//Hide column headers.
	sheet.setColumnHeaderVisible(false);
	//Hide row headers.
	sheet.setRowHeaderVisible(false);
	//Hide horizontal grid lines.
	sheet.setGridlineOptions({
		showHorizontalGridline : false
	});
	//Hide vertical grid lines.
	sheet.setGridlineOptions({
		showVerticalGridline : false
	});
	//获取当前sheet默认zoom
	defaultZoom = sheet.zoom();
	//设置实际大小值
	$(".actzoom>.zoomlink").attr("val",defaultZoom * 100);
	$(".zoomval").text(defaultZoom * 100 + "%");
	var slider = $(".rangeSlider").data("ionRangeSlider");
	slider.update({
		from : defaultZoom * 100
	});
}
//报表渲染
function initReportData(data) {
	if (data) {
		if (data.ResultCode == 1) {
			var spread = $("#ss").data("spread");
			var ruleJson = JSON.parse(data.ResultData.PageFile);
			function sppp(ruleJson){
				for(var sheetKey in ruleJson.sheets){
					var dataTable = ruleJson.sheets[sheetKey].data.dataTable;
					for(var rowKey in dataTable){
						var row = dataTable[rowKey];
						for(var cellKey in row){
							var cell = row[cellKey];
							if(cell.formula == "CurrentPage()"){
								//cell.formula = "CURRENTPAGE()";
								delete cell.formula;
								cell.value = currentPage;
							}else if(cell.formula == "TotalPage()"){
								//cell.formula = "TOTALPAGE()";
								delete cell.formula;
								cell.value = data.ResultData.PageCount;
							}
						}
					}
				}
			}
			sppp(ruleJson);
			spread.fromJSON(ruleJson);
			totalPage = data.ResultData.PageCount;
			var sheet = spread.getActiveSheet();
			//加自定义函数
			//总页面
			function TotalPageFunction() {
				this.name = "TOTALPAGE";
				this.maxArgs = 0;
		    	this.minArgs = 0;
			}
			TotalPageFunction.prototype = new GcSpread.Sheets.Calc.Functions.Function();
			TotalPageFunction.prototype.evaluate = function(args) {
				return totalPage;
			}
			var totalPageFunc = new TotalPageFunction();
			sheet.addCustomFunction(totalPageFunc);
			//当前页面
			function CurrentPageFunction() {
				this.name = "CURRENTPAGE";
				this.maxArgs = 0;
		    	this.minArgs = 0;
			}
			CurrentPageFunction.prototype = new GcSpread.Sheets.Calc.Functions.Function();
			CurrentPageFunction.prototype.evaluate = function(args) {
				return currentPage;
			}
			var currentPageFunc = new CurrentPageFunction();
			sheet.addCustomFunction(currentPageFunc);


			updateSheetList();
			hideLoading();
			spread.tabStripVisible(false);
			
			//Hide column headers.
			sheet.setColumnHeaderVisible(false);
			//Hide row headers.
			sheet.setRowHeaderVisible(false);

			//Hide horizontal grid lines.
			sheet.setGridlineOptions({
				showHorizontalGridline : false
			});
			//Hide vertical grid lines.
			sheet.setGridlineOptions({
				showVerticalGridline : false
			});


			

			//更新页面信息
			$(".pagemsg").html(currentPage + "/" + totalPage);
		} else {
			console.log("获取到返回数据：" + data.ResultMessage);
		}
	} else {
		console.log("未获取到返回数据");
	}
}

var totalPage = 0;
var currentPage = 1;
//更新页面跳转按钮状态
function updatePageBtStatus() {
	if (totalPage > 0) {
		$(".myicon").attr("disabled",false);
		$(".myicon").removeClass("disabled");
		$("#prevPage").attr("disabled", false);
		$("#nextPage").attr("disabled", false);
		if (totalPage == 1) {
			//$("#firstPage").attr("disabled",true);
			//$("#lastPage").attr("disabled",true);
			$("#prevPage").attr("disabled", true);
			$("#nextPage").attr("disabled", true);
			//$("#firstPage").addClass("disabled");
			//$("#lastPage").addClass("disabled");
			$("#prevPage").addClass("disabled");
			$("#nextPage").addClass("disabled");
		} else if (totalPage > 1) {
			if (currentPage == 1) {
				$("#prevPage").attr("disabled", true);
				$("#prevPage").addClass("disabled");
			} else if (currentPage == totalPage) {
				$("#nextPage").attr("disabled", true);
				$("#nextPage").addClass("disabled");
			} else {
				$("#prevPage").attr("disabled", false);
				$("#nextPage").attr("disabled", false);
			}
		}
	}
}
//翻页
function gotoPage(pageNo) {
	if(!key && !content){
		$.ajax({
			url: contextPath + '/mx/form/defined/getDatabaseInfo?databaseCode='+database,
			type: 'post',
			async : false ,
			dataType : 'JSON' ,
			success:function(datakk){
				key = datakk.key ;
				content = datakk.content;
			}
		});
	}
	$.ajax({
		url : serverUrl,
		type : "post",
		async : false,
		dataType : "JSONP",
		jsonpCallback : "jsonpCallback",
		data : {
			templateId : templateId==''?"0891521A-BD52-47D5-9D3E-85BD768C7907":templateId,
			reportId : reportId==''?"f918397d-6b81-4ec4-8b1b-85fe3ac40b9e":reportId,
			pageIndex : pageNo,
			key : key,
			content : content
		},
		success : function (data) {
			initReportData(data);
			initSpread();
			updatePageBtStatus();
		},
		error : function () {
			console.log("调用接口出错");
		}
	});
	
}; 