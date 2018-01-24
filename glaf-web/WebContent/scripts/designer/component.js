/**
 * 创建控件模板
 */
$.template = {
	office : "",
	imageupload : "",
	video : "<video src=\"video.mp4\"  width=\"320\"  height=\"200\"  controls  preload></video>",
	upload : "<input id=\"exampleInputFile\" type=\"file\"></input>",
	textbox : "<input class=\"form-control\" type=\"text\" placeholder=\".col-xs-3\">",
	autocomplete : "",
	button : "<button class=\"btn\" type=\"button\" contenteditable=\"true\" >Default</button>",
	combobox : "",
	checkbox : "<label><input type=\"checkbox\"> Check me out</label>",
	radio : "<input type=\"radio\" checked=\"\" value=\"option1\">",
	multiselect : "<select class=\"form-control\" multiple=\"\"><option>option 1</option><option>option 2</option><option>option 3</option><option>option 4</option><option>option 5</option></select>",
	dropdownlist : "<select tabindex=\"-1\" class=\"form-control select2 select2-hidden-accessible\" aria-hidden=\"true\" style=\"width: 100%;\">"
			+ "<option selected=\"selected\">Alabama</option>"
			+ "<option>Alaska</option>"
			+ "<option disabled=\"disabled\">California (disabled)</option>"
			+ "<option>Delaware</option>"
			+ "<option>Tennessee</option>"
			+ "<option>Texas</option>"
			+ "<option>Washington</option>"
			+ "</select><span class=\"select2 select2-container select2-container--default select2-container--below select2-container--open\" style=\"width: 100%;\" dir=\"ltr\">"
			+ "<span class=\"selection\">"
			+ "<span tabindex=\"0\" class=\"select2-selection select2-selection--single\" role=\"combobox\" aria-expanded=\"true\" aria-haspopup=\"true\" aria-labelledby=\"select2-3key-container\" aria-activedescendant=\"select2-3key-result-mna0-Delaware\" aria-owns=\"select2-3key-results\" aria-autocomplete=\"list\">"
			+ "<span title=\"Delaware\" class=\"select2-selection__rendered\" id=\"select2-3key-container\">Delaware</span><span class=\"select2-selection__arrow\" role=\"presentation\"><b role=\"presentation\"></b>"
			+ "</span>"
			+ "</span>"
			+ "</span>"
			+ "<span class=\"dropdown-wrapper\" aria-hidden=\"true\"></span>"
			+ "</span>",
	timepicker : "<input class=\"form-control timepicker\" type=\"text\"></input>",
	datetimepicker : "<input class=\"form-control datetimepicker\" type=\"text\"></input>",
	datepicker : "<input class=\"form-control datepicker\" type=\"text\"></input>",
	calendar : "<input type=\"text\" class=\"form-control\" data-inputmask=\"'alias': 'dd/mm/yyyy'\" data-mask>",
	maskedtextbox : "<input type=\"password\" class=\"form-control\" id=\"exampleInputPassword1\" placeholder=\"Password\">",
	numerictextbox : "",
	textfield : "<input class=\"form-control\" type=\"text\" placeholder=\".col-xs-3\">",
	textarea : "<textarea class=\"form-control\" placeholder=\"Enter ...\" rows=\"3\"></textarea>",
	//editor : "",
	//tabstrip : "",
	splitter : "",
	listview : "",
	//grid : "",
	//progressbar : "",
	a : "",
	yScroll : "",
	//definedTable : "",
	jsgis : "",
	custom : "",
	//gis : "",
	//charts : "",
	//diagram : "",
	ztree : "",
	treelist : "",
	treeview : ""
}