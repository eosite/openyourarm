package com.glaf.platforms.rule;

public enum ControlsEnum {
	// role全部使用小写 className需要和类名一致
	ASPECTLAYOUT("aspectlayout","AspectLayoutModel"),LOGINMSGVALID("loginmsgvalid","LoginMessageValidModel"),WSLIDER("wslider","WSliderModel"),ISLIDER("islider","IsliderModel"),BMAPMARKER("bmapmarker","BmapMarkerModel"),VECTORDRAW("vectorDraw","VectorDrawModel"),RANGECALENDAR("rangecalendar","RangeCalendarModel"),MDATEPICKER("mdatepicker","MdatePickerModel"),MUITAB("muitab","MuitabModel"),MSWITCH("mswitch","MswitchModel"),BMAPDDEXT("bmapddext","BmapddextModel"),SELECTPICKER("selectpicker","SelectPickerModel"),FOLDLIST("foldlist","FoldListModel"),LEVELLIST("levellist","LeveLlistModel"),DHXGANTT("dhxgantt","DhxGanttModel"),BMAPHEAT("bmapheat","BmapHeatModel"),IMAGESEXT("imagesext","ImagesExtModel"),BMAPEXT("bmapext","BmapExtModel"),DEFINEDPANEL("definedpanel","DefinedPanelModel"),FULLPAGE("fullpage","FullPageModel"),POPOVER("popover","PopoverModel"),PROMPT("prompt","PromptModel"),FIELDSET("fieldset","FieldsetModel"),RANGESLIDER("rangeSlider","RangeSliderModel"),MAPEXT("mapext","MapExtModel"),GRIDLIST("gridlist", "GridListModel"),SIONALCODE("sionalcode", "SionalCodeModel"),SIDEBAR("sidebar","SidebarModel"), GRID("grid", "GridModel"), COMBOBOX("combobox", "ComboboxModel"), ZTREEMODEL(
			"ztree", "ZTreeModel"), BUTTONMODEL("button", "ButtonModel"), NESTABLEMODEL("nestable","NesTableModel"),TEXTBOXMODEL(
			"textbox", "TextBoxModel"),MASKEDTEXTBOXMODEL("maskedtextbox",
			"MaskedTextBoxModel"), BOOTSTRAPDIALOG("bootstrapdialog","BootstrapDialogModel"),AUTOCOMPLETE("autocomplete",
			"AutoCompleteModel"), DATETIMEPICKER("datetimepicker",
			"DateTimePickerModel"), DATEPICKER("datepicker", "DatePickerModel"),
			DATERANGEPICKER("daterangepicker","DateRangePickerModel"),
			DROPDOWNLIST("dropdownlist", "DropdownListModel"), MULTISELECT("multiselect",
			"MultiSelectModel"), NUMERICTEXTBOX("numerictextbox",
			"NumericTextBoxModel"), TREELIST("treelist", "TreeListModel"), TABSTRIP(
			"tabstrip", "TabStripModel"), CHARTS("charts", "ChartsModel"), DIAGRAM(
			"diagram", "DiagramModel"), GIS("gis", "GisModel"), EDITOR(
			"editor", "EditorModel"), IMAGE("custom", "CustomModel"), UPLOAD(
			"upload", "UploadModel"), VIDEO("video", "VideoModel"), JSGIS(
			"jsgis", "JSGisModel"), DEFINEDTABLE("definedtable",
			"DefinedTableModel"), RADIO("radio", "RadioModel"), ImageUpload(
			"imageupload", "ImageUploadModel"), YSCROLL("yscroll",
			"YScrollModel"), OFFICE("office", "OfficeModel"), A("a", "AModel"), LISTVIEW(
			"listview", "ListViewModel"), PROGRESSBAR("progressbar",
			"ProgressBarModel"), TABS("tabs", "TabsModel"), ACCORDION(
			"accordion", "AccordionModel"), TREELISTBT("treelistbt",
			"TreeListBtModel"), METROLIST("metrolist", "MetrolistModel"), DATEPICKERBT(
			"datepickerbt", "DatePickerBtModel"), TEXTBOXBT("textboxbt",
			"TextBoxBtModel"), METROSELECT("metroselect", "MetroselectModel"),METROSELECTM("metroselect_m", "MetroselectmModel"), PROGRESSBARBT(
			"progressbarbt", "ProgressBarBtModel"), ICHECKBOX("icheckbox",
			"IcheckboxModel"), ICHECKRADIO("icheckradio", "IcheckradioModel"), JQFILEUPLOAD(
			"jqfileupload", "JqfileuploadModel"), JQFILEUPLOAD2(
			"jqfileupload2", "Jqfileupload2Model"), CALENDARBT("calendarbt",
			"CalendarBtModel"), PORTLET("portlet", "PortletModel"), DATETIMEPICKERBTMODEL(
			"datetimepickerbt", "DateTimePickerBtModel"), MEGAMENUMODEL("megamenu", "MegaMenuModel"),
			BTALERT("btalert", "BtalertModel"), BTMESSENGER("btmessenger", "BtmessengerModel"),
			MTBUTTON("mtbutton", "MtbuttonModel"),LABELMODEL("label","LabelModel"),
			CELL("cell", "CellModel"),TEXTAREABTMODEL("textareabt", "TextAreaBtModel"),TOUCHSPIN("touchspin","TouchSpinModel"),
			GISUPLOAD("gisupload","GisUploadModel"),LOGINPAGE("login_page","LoginPageModel"),LOGINUSERNAME("login_username","LoginUserNameModel"),
			LOGINPASSWORD("login_password","LoginPasswordModel"),LOGINACTIONS("login_actions","LoginActionsModel"),LOGINVERIFY("login_verify","LoginVerifyModel"),
			LOGINCOPYRIGHT("login_copyright","LoginCopyrightModel"),LOGINLOGO("login_logo","LoginLogoModel"),LOGINALERT("login_alert","LoginAlertModel"),
			GRIDBT("gridbt","GridBtModel"),GANTT("gantt","GanttModel"),CHECKBOX("checkbox","CheckBoxModel"), OFFICEBT("officebt", "OfficeBtModel"),
			CAROUSEL("carousel","CarouselModel"),DIAGRAMBT("diagrambt", "DiagramBtModel"), EDITORBT(
					"editorbt", "EditorBtModel"),STEP("step", "StepModel"),PAGE("page", "PageModel"),TAGSINPUT("tagsinput", "TagsInputModel"),CUSTOMLIST("customList", "CustomListModel"),
			COlMD12("col-md-12","ColModel"),DEFINEDBUTTON("definedButton","DefinedButtonModel"),
			BIMUPLOAD("bimupload","BIMUploadModel"),
			EXCELUPLOAD("excelupload","EXCELUploadModel"),
			BIM("bim","BIMModel"),SVGEDITOR("svgeditor","SvgEditorModel"),COMMONMODEL("bootstrap_tabs","BootstrapTabsModel"),
			BRIDGESEAM("bridgeseam","BridgeSeamModel"),VIDEOPLAY("videoplay","VideoPlayModel"),DEFINEDCARD("definedcard","DefinedCardModel"),
			SWTICH("switch","SwitchModel"),RATYLIMASTER("ratylimaster","RatyLiMasterModel"),RADIALINDICATOR("radialindicator","RadialIndicatorModel"),
			ECHARTS("echarts","EchartsModel"),JPLAYER("jplayer","JPlayerModel"),ROUNDWATER("roundwater","RoundWaterModel"),
			WYVIDEO("wyvideo","WYVideoModel"),WEBCHAT("webchat","WebChatModel"),PHONELISTVIEW("phoneListView","PhoneListViewModel"),
			PHOTOSWIPE("photoswipe","PhotoSwipeModel"),TABBAR("tabbar","TabbarModel"),SEARCHBAR("searchbar","SearchBarModel"),
			PHONETIMEPICKER("phoneTimePicker","PhoneTimePickerModel"),SLIDEOUTS("slideouts","SlideOutsModel"),MMENU("mmenucom","MmenuComModel"),
			ALIVIDEO("alivideo","ALiVideoModel"),PHONEPANEL("phonePanel","PhonePanelModel"),CUSTOMSELECT("customSelect","CustomSelectModel"),
			LOADMORE("loadMore","LoadMoreModel"),CELLWEBCAB("cellWebCab","CellWebCabModel"),IMAGEVIEW("imageview","ImageViewModel"),
			MEDIALIST("medialist","MediaListModel"),REVIEWAREA("reviewArea","ReviewAreaModel"),SCAN("scan","ScanModel"),
			INDEXEDLIST("indexedlist","IndexedListModel"),TABLEVIEWLIST("tableViewList","TableViewListModel"),MODELBIM("modelbim","ModelBimModel");
	// 成员变量
	private String roleName;
	private String className;

	// 构造方法
	private ControlsEnum(String roleName, String className) {
		this.roleName = roleName;
		this.className = className;
	}

	// 获取类名
	public static String getClassName(String roleName) {
		for (ControlsEnum c : ControlsEnum.values()) {
			if (c.roleName.equalsIgnoreCase(roleName)) {
				return c.className;
			}
		}
		return null;
	}
}
