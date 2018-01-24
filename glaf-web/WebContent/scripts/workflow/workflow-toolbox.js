/**
 * 
 */
(function($) {
	//var prop={},parentProp={},parentDom,data={},bodyArea,level;
	$.Toolbox = function(target,options,params,callback) {
		if (options.url) {
			$.ajax({
				url : options.url,
				type :"post",
				data :params,
				dataType : "json",
				success : function(rdata) {
					if (rdata) {
						var nodes = eval(rdata);
						$.Toolbox.prototype.data = nodes;
						$.Toolbox.prototype._init(target);
						if(callback&&callback!=null&&callback!=""){
					    var funcs=callback.split(",");
					    var func;
					    $.each(funcs,function(i,item){
					    	func = eval(item);
					    	func();
					    });
						}
					}
				},
				error : function() {
					console.log("初始化工具箱失败");
				}

			});
		}
		
	};
	$.Toolbox.prototype = {
		prop : {},
		parentProp : {},
		parentDom : null,
		data : null,
		bodyArea : null,
		level : null,
		_init : function(target) {
			target.attr("id", target.attr("id"));
			target.css("border", "0px");
			var that=this;
			$.each(this.data.propertyPackages, function(i, obj) {
				this.prop = obj;
				li_dom = $("<li></li>");
				li_dom.attr("id", obj.id);
				var li_dom_span = $("<span></span>");
				li_dom_span.addClass("k-link");
				if (obj.expland && obj.expland == 'true') {
					li_dom.addClass("k-state-active");
					li_dom_span.addClass("k-state-selected");
				}
				var levelCss = obj.id;
				li_dom_span.addClass(levelCss);
				var li_dom_span_img = $("<img></img>");
				li_dom_span_img.attr("id", obj.id + "_img");
				if (obj.expland && obj.expland == "true") {
					li_dom_span_img.attr("src", contextPath
							+ "/images/bullet_toggle_minus.png");
				} else {
					li_dom_span_img.attr("src", contextPath
							+ "/images/bullet_toggle_plus.png");
				}
				li_dom_span_img.css("vertical-align", "middle");
				var li_dom_span_text = "&nbsp;&nbsp;" + obj.title;
				li_dom_span.append(li_dom_span_img);
				li_dom_span_img.after(li_dom_span_text);
				li_dom.append(li_dom_span);
				var li_dom_div = $("<div></div>");
				var headName=obj.head;
				var li_dom_div_tb = that.createTable(headName);
				var li_dom_div_tb_tbody = that.createTbBody();
				that.bodyArea = li_dom_div_tb_tbody;
				li_dom_div_tb.append(li_dom_div_tb_tbody);
				li_dom_div.append(li_dom_div_tb);
				li_dom.append(li_dom_div);
				target.append(li_dom);
				//获取栏目属性
				var props = obj.properties;
				if (props) {
					that.parentProp = null;
					that.createProperties(props);
				}
			});
			target.parent().append(target);
			target
			.kendoPanelBar(
					{
						expandMode : "single",
						collapse : onCollapse,
						expand : onExpand
					});
		},
		createTable : function(headName) {
			var table_dom = $("<table></table>");
			table_dom.css("width", "100%");
			table_dom.css("margin", "0px");
			table_dom.css("padding", "0px");
			table_dom.css("border-spacing", "0px");
			var table_hd_dom = this.createTbHead(headName);
			if (table_hd_dom)
				table_dom.append(table_hd_dom);
			return table_dom;
		},
		createTbHead : function(headName) {
			var table_hd = $("<thead></thead>");
			table_hd.addClass("datagrid-header");
			var hd_tr = $("<tr></tr>");
			var col1Name="名称";
			var col2Name="属性值";
			if(headName&&headName!=""){
				col1Name=headName.split(",")[0];
				col2Name=headName.split(",")[1];
			}
			hd_tr.append(this.createTbHeadTh("width", "40px", null));
			hd_tr.append(this.createTbHeadTh("width", "180px", col1Name));
			hd_tr.append(this.createTbHeadTh(null, null, col2Name));
			table_hd.append(hd_tr);
			return table_hd;
		},
		createTbHeadTh : function(styleName, styleVal, title) {
			var hd_tr_th = $("<th></th>");
			if (styleName && styleVal)
				hd_tr_th.css(styleName, styleVal);
			if (title)
				hd_tr_th.append(title);
			return hd_tr_th;
		},
		createTbBody : function() {
			var table_body = $("<tbody></tbody>");
			table_body.addClass("datagrid-tbody");
			return table_body;
		},
		createProperties : function(props, parentProp, parentDom) {
			this.level++;
			var that=this;
			$.each(props, function(i, prop) {
				if (parentProp) {
					if (parentProp.pid)
						prop.pid = parentProp.pid + " " + prop.id;
					else
						prop.pid = parentProp.id + " " + prop.id;
				} else {
					prop.pid = prop.id;
				}
				that.prop = prop;
				var propdom = that.createProperty(prop, parentProp, parentDom);
				var d_prop = prop.properties;
				if (d_prop) {
					that.createProperties(d_prop, prop, propdom);
				}
			});
			this.level--;
		},
		createProperty : function createProperty(prop, parentProp, parentDom) {
			var propdom;
			this.parentProp = parentProp;
			this.parentDom = parentDom;
			this.prop=prop;
			if (prop.inputMode) {
				if (prop.inputMode == 'input') {
					propdom = this.createInput();
				} else if (prop.inputMode == 'radio') {
					propdom = this.createRadio();
				} else if (prop.inputMode == 'select') {
					propdom = this.createSelect();
				} else if (prop.inputMode == 'checkbox') {
					propdom = this.createCheckbox();
				}else if (prop.inputMode == 'template') {
					propdom = this.createTemplate();
				}
			} else {
				propdom = this.createTitle();
			}
			this.bodyArea.append(propdom);
			return propdom;
		},
		createInput : function() {
			var prop_tr = $("<tr></tr>");
			if(this.prop.hide!=undefined&&this.prop.hide=='true'){
				prop_tr.css("display","none");
			}
			if (this.parentProp) {
				prop_tr.addClass(this.parentProp.pid);
			}
			var prop_td = $("<td></td>");
			prop_td.addClass("titleTd");
			prop_tr.append(prop_td);
			prop_td = $("<td></td>");
			prop_td.append(this.prop.title);
			prop_tr.append(prop_td);
			prop_td = $("<td></td>");
			var input_dom = $("<input></input>");
			if(this.prop.value.indexOf("#")!=0)
		  {
				input_dom.attr("value", this.prop.value);	
		  }
			if((input_dom.attr("value")==null||input_dom.attr("value")=="")&&this.prop.defaultVal){
				input_dom.attr("value", this.prop.defaultVal);	
			}
			if(this.prop.readonly&&this.prop.readonly=="true"){
				input_dom.attr("readonly", "readonly");
			}
			input_dom.attr("type", "text");
			input_dom.addClass("textCls");
			input_dom.addClass("k-textbox");
			input_dom.attr("name", this.prop.id);
			input_dom.css("width", "260px");
			//增加失焦事件
			if(this.prop.onblur){
				input_dom.attr("onblur", this.prop.onblur);
			}
			//增加双击事件
			if(this.prop.ondblclick){
				input_dom.attr("ondblclick", this.prop.ondblclick);
			}
			prop_td.append(input_dom);
			prop_tr.append(prop_td);
			return prop_tr;
		},
		createTitle : function() {
			var prop_tr = $("<tr></tr>");
			if(this.prop.hide!=undefined&&this.prop.hide=='true'){
				prop_tr.css("display","none");
			}
			prop_tr.addClass("datagrid-title");
			if (this.parentProp) {
				prop_tr.addClass(this.parentProp.pid);
			}
			var prop_td = $("<td></td>");
			prop_td.attr("colspan", "3");
			for (var i = 0; i < this.level; i++) {
				prop_td.append("&nbsp;&nbsp;");
			}
			var img_dom = $("<img></img>");
			var img_id=this.prop.pid.replace(/ /g,"_");
			img_dom.attr("id", img_id + "_img");
			img_dom.attr("src", contextPath + "/images/toggle-small.png");
			img_dom.css("vertical-align", "middle");
			img_dom.attr("onclick", "showHiddenFunc(this,'" + this.prop.pid
					+ "')");
			prop_td.append(img_dom);
			prop_td.append(this.prop.title);
			prop_tr.append(prop_td);
			return prop_tr;
		},
		createRadio : function() {
			var prop_tr = $("<tr></tr>");
			if(this.prop.hide!=undefined&&this.prop.hide=='true'){
				prop_tr.css("display","none");
			}
			if (this.parentProp) {
				prop_tr.addClass(this.parentProp.pid);
			}
			var prop_td = $("<td></td>");
			prop_td.addClass("titleTd");
			prop_tr.append(prop_td);
			prop_td = $("<td></td>");
			prop_td.append(this.prop.title);
			prop_tr.append(prop_td);
			prop_td = $("<td></td>");
			if (this.prop.valrange) {
				var that=this;
				$.each(this.prop.valrange, function(i, item) {
					var input_dom = $("<input></input>");
					input_dom.attr("type", "radio");
					input_dom.attr("name", that.prop.id);
					input_dom.attr("value", item.value);
					if(that.prop.value!=null&&that.prop.value!=""){
						if (that.prop.value == item.value) {
							input_dom.attr("checked", "checked");
						 }
					}
					else if(that.prop.defaultVal!=null&&that.prop.defaultVal == item.value) {
						input_dom.attr("checked", "checked");
					}
					prop_td.append(input_dom);
					prop_td.append(item.title);
				});
			}
			prop_tr.append(prop_td);
			return prop_tr;
		},
		createCheckbox : function() {
			var prop_tr = $("<tr></tr>");
			if(this.prop.hide!=undefined&&this.prop.hide=='true'){
				prop_tr.css("display","none");
			}
			if (this.parentProp) {
				prop_tr.addClass(this.parentProp.pid);
			}
			var prop_td = $("<td></td>");
			prop_td.addClass("titleTd");
			prop_tr.append(prop_td);
			prop_td = $("<td></td>");
			prop_td.append(this.prop.title);
			prop_tr.append(prop_td);
			prop_td = $("<td></td>");
			var that=this;
			var input_dom = $("<input></input>");
			input_dom.attr("type", "checkbox");
			input_dom.attr("name", that.prop.id);
			input_dom.attr("value", that.prop.value);
			if(that.prop.defaultVal!=''&&that.prop.value==''){
				input_dom.attr("value", that.prop.defaultVal);
			}
			if (that.prop.checkedval == that.prop.value) {
				input_dom.attr("checked", "checked");
			}
			prop_td.append(input_dom);
			prop_tr.append(prop_td);
			return prop_tr;
		},
		createSelect : function() {
			var prop_tr = $("<tr></tr>");
			if(this.prop.hide!=undefined&&this.prop.hide=='true'){
				prop_tr.css("display","none");
			}
			if (this.parentProp) {
				prop_tr.addClass(this.parentProp.pid);
			}
			var prop_td = $("<td></td>");
			prop_td.addClass("titleTd");
			prop_tr.append(prop_td);
			prop_td = $("<td></td>");
			prop_td.append(this.prop.title);
			prop_tr.append(prop_td);
			prop_td = $("<td></td>");
			if (this.prop.valrange) {
				var select_dom = $("<select></select>");
				select_dom.addClass("k-input");
				select_dom.addClass("k-state-default");
				var that=this;
				select_dom.attr("name", that.prop.id);
				$.each(this.prop.valrange, function(i, item) {
					var option_dom = $("<option></option>");
					option_dom.attr("value", item.value);
					option_dom.append(item.title);
					if(that.prop.value!=null){
						if (that.prop.value == item.value) {
							option_dom.attr("selected", "selected");
						}
					}else{
					  if (that.prop.defaultVal == item.value) {
						    option_dom.attr("selected", "selected");
					  }else{
						    option_dom.attr("selected", "selected");
					  }
					}
					select_dom.append(option_dom);
				});
				prop_td.append(select_dom);
			}
			prop_tr.append(prop_td);
			if (this.prop.usecondition) {
				var elemdom = this.parentDom.find("td:last input");
				var userConval=this.prop.usecondition;
				//获取checked val
				var currChecked=this.parentDom.find("td:last input[checked='checked']").val();
				if(currChecked== userConval){
					prop_tr.css("display", "");
				}
				else{
					prop_tr.css("display", "none");
				}
				elemdom.bind("click", function(e) {
					if (e.target.value == userConval) {
						prop_tr.css("display", "");
					} else {
						prop_tr.css("display", "none");
					}
				});
			}
			return prop_tr;

		},
		createTemplate : function() {
			var prop_tr = $("<tr></tr>");
			if(this.prop.hide!=undefined&&this.prop.hide=='true'){
				prop_tr.css("display","none");
			}
			if (this.parentProp) {
				prop_tr.addClass(this.parentProp.pid);
			}
			var prop_td = $("<td></td>");
			prop_td.addClass("titleTd");
			prop_tr.append(prop_td);
			prop_td = $("<td></td>");
			prop_td.append(this.prop.title);
			prop_tr.append(prop_td);
			prop_td = $("<td></td>");
			var template_dom = this.prop.template;
			template_dom = template_dom.replace(/contextPath/g, contextPath);
			prop_td.append(template_dom);
			prop_tr.append(prop_td);
			return prop_tr;
		}
	};
})(jQuery);
//折叠属性栏
function onCollapse(e) {
	var colimg = contextPath+'/images/bullet_toggle_plus.png';
	$('#' + e.item.id + '_img').attr('src', colimg);
}
//展开属性栏
function onExpand(e) {
	var expimg = contextPath+'/images/bullet_toggle_minus.png';
	$('#' + e.item.id + '_img').attr('src', expimg);
}
//展开、隐藏子栏目
function showHiddenFunc(obj, collCssName) {
	if ($('#' + obj.id).attr('hidd')
			&& $('#' + obj.id).attr('hidd') == 'true') {
		onItemExpand(obj, collCssName);
	} else {
		onItemCollapse(obj, collCssName);
	}
}
//隐藏子栏目
function onItemCollapse(obj, collCssName) {
	var colimg = contextPath+'/images/toggle-small-expand.png';
	collCssName=collCssName.replace(/ /g,".");
	//获取所有包含id的图片
	$("." + collCssName+" img").attr('src', colimg);
	$('#' + obj.id).attr('src', colimg);
	$("." + collCssName).attr('hidden', true);
	$('#' + obj.id).attr('hidd', true);
}
//展开子栏目
function onItemExpand(obj, collCssName) {
	var expimg = contextPath+'/images/toggle-small.png';
	collCssName=collCssName.replace(/ /g,".");
	$("." + collCssName+" img").attr('src', expimg);
	$('#' + obj.id).attr('src', expimg);
	$("." + collCssName).attr('hidden', false);
	$('#' + obj.id).attr('hidd', false);
}
//name改变绑定事件
