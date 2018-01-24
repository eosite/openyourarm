//初始化数值增量输入
function initTouchSpin() {
	$(".spaceTouchSpin").each(
		function(i, item) {
			var min = $(item).attr("min");
			min ? min : $(item).attr("min", -100);
			var max = $(item).attr("max");
			max ? max : $(item).attr("max", 100);
			touchSpinEx(item);

			if ($(item).attr("widthSize")) {
				$(item).parent().css("width", $(item).attr("widthSize"));
			} else {
				$(item).parent().css("width", "75px");
			}
		});

	// $(".spaceTouchSpin").parent().css("width","75px");
	$(".spaceTouchSpin").parent().css("float", "left");
	$(".spaceTouchSpin").parent().css("margin-top", "2px");
	$(".sizeTouchSpin").each(
		function(i, item) {
			var min = $(item).attr("min");
			min ? min : $(item).attr("min", 0);
			var max = $(item).attr("max");
			max ? max : $(item).attr("max", 1000);
			touchSpinEx(item);
		});
	$(".sizeTouchSpin").parent().css("width", "70px");
	$(".sizeTouchSpin").parent().css("float", "left");
	$(".sizeTouchSpin").parent().css("margin-top", "2px");
}
//数值增量输入框
function touchSpinEx(item) {
	var prefix = $(item).attr("prefix");
	var postfix = $(item).attr("postfix");
	var min = $(item).attr("min");
	var max = $(item).attr("max");
	$(item).TouchSpin({
		verticalbuttons: true,
		postfix: postfix ? postfix : '',
		boostat: 5,
		step: 1,
		prefix: prefix ? prefix : '',
		"min": min,
		"max": max
	});
}

//初始化颜色拾取器
function initColorPicker() {
	$(".colorpicker").each(
		function(i, item) {
			colorPickerEx(item)
		});
}
//颜色拾取器
function colorPickerEx(item) {
	$(item).minicolors({
		control: $(item).attr('data-control') || 'hue',
		defaultValue: $(item).attr('data-defaultValue') || '',
		inline: $(item).attr('data-inline') === 'true',
		letterCase: $(item).attr('data-letterCase') || 'lowercase',
		opacity: $(item).attr('data-opacity'),
		position: $(item).attr('data-position') || 'bottom left',
		change: function(hex, opacity) {
			if (!hex) return;
			if (opacity) hex += ', ' + opacity;
			if (typeof console === 'object') {
				console.log(hex);
			}
		},
		theme: 'bootstrap'
	});
}

(function($) {
	$.Attr_toolbox = function() {

	};
	$.Attr_toolbox.prototype = {
		$component: null,	//控件实际对象
		$style: null,	//控件的自定义样式对象
		findSelectedComponent: function() {
			return this.$component;
		},
		findNowStyle: function() {
			return this.$style;
		},
		getCtrImpl: function(compType,ctrImpl) {
			try {
				if (typeof ctrImpl == "string") {
					try {
						ctrImpl = eval(compType + "_attr_func." + ctrImpl);
					} catch (e) {
						ctrImpl = eval("base_attr_func." + ctrImpl);
					}
				}
			} catch (e) {
				alert(ctrImpl + '方法不存在！');
			}

			return ctrImpl;

		},
		getValue : function(item){
			var that = this;
			var value = "";
			if (item.defaultVal)
				value = item.defaultVal;
			if (that.styleData[item.id]) {
				value = that.styleData[item.id];
			}
			var $style = that.findNowStyle();
			if($style[0]){
				var styleData = $style.data("styleData") || {};
				value = styleData[item.id];
			}
			return value;
		},
		getRealValue : function(item){
			var that = this;
			var value = "";
			if (that.styleData[item.id]) {
				value = that.styleData[item.id];
			}
			var $style = that.findNowStyle();
			if($style[0]){
				var styleData = $style.data("styleData") || {};
				value = styleData[item.id];
			}
			return value;
		},
		/**
		 * 执行实例方法
		 * @param  {[type]} object     [方法调用的对象]
		 * @param  {[type]} ctrImpl     [方法实例]
		 * @param  {[type]} value     [值]
		 * @param  {[type]} util       [单位值]
		 * @param  {[type]} otherParam [其他值]
		 * @return {[type]}            [description]
		 */
		callCtrlImpl:function(object,ctrImpl,value,util,otherParam){
			var that = this;
			// ctrImpl.call(this, that.findSelectedComponent,that.findNowStyle(), inputVal, unitVal);
			if(ctrImpl && $.isFunction(ctrImpl)){
				ctrImpl.call(object,that.findSelectedComponent(),that.findNowStyle(),value,util,otherParam);	
			}
		},
		createProperties: function(props) {
			var that = this;
			that.$component = that.$component || $(".display_panel [dataid='" + that.controlId + "'][themeType='" + that.themeType + "']");
			var $div = $('<div></div>');
			$.each(props, function(i, item) {
				if(item.isParent){
					var $row = $("<div class='row' style='margin:0px 0px; border-bottom: 1px solid #dbdbdb;padding: 6px 0px;'></div>");
					$div.append($row);
					var $label = $("<label class='col-xs-3' style='text-align:right;margin-top: 8px;'></label>");
					$label.text(item.value);
					$row.append($label);
					var $content = $("<div class='col-xs-9'></div>");
					$row.append($content);
					$.each(item.children,function(k,ktem){
						$content.append(that.createProperty(ktem));
					})
				}else{
					$div.append(that.createProperty(item));	
				}
			})
			return $div;
		},
		createProperty: function(prop, parentProp, parentDom) {
			if(prop.inputMode == 'br'){
				return $("<br>");
			}
			var $package = $('<div class="form-group hmtd-xs"></div>');
			if (prop.img) {
				$package.append($("<img src='" + contextPath + prop.img + "' style='padding-right: 3px;'>:"));
			} else if (prop.label) {
				$package.append($("<label>").text(prop.label));
			}

			var $input_group = $('<div class="input-group" title="' + prop.title + '"></div>');
			var propdom;
			if(prop.addonText){
				$input_group.append('<span class="input-group-addon bootstrap-touchspin-prefix">'+prop.addonText+'</span>');
			}
			if (prop.inputMode) {
				this.prop = prop;
				if (prop.inputMode == 'input') {
					$input_group.css("line-height", "45px");
					$input_group.css("padding-top", "5px");
					$input_group.css("padding-bottom", "5px");
					propdom = this.createInput();
				} else if (prop.inputMode == 'radio') {
					propdom = this.createRadio();
				} else if (prop.inputMode == 'select') {
					$input_group.css("line-height", "45px");
					// $input_group.css("padding-top", "5px");
					// $input_group.css("padding-bottom", "5px");
					$input_group.addClass("hmtd-xs");
					propdom = this.createSelect();
				} else if (prop.inputMode == 'touchspin') {
					propdom = this.createTouchspin();
				} else if (prop.inputMode == 'buttongroup') {
					propdom = this.createButtonGroup();
				} else if (prop.inputMode == 'mulitselbuttongroup') {
					propdom = this.createMulitSelButtonGroup();
				} else if (prop.inputMode == 'colorpicker') {
					propdom = this.createColorPicker();
				} else if (prop.inputMode == 'rangeSlider') {
					propdom = this.createRangeSlider();
				} else if (prop.inputMode == 'template') {
					propdom = this.createTemplate();
				} else if (prop.inputMode == 'split') {
					propdom = $('<img class="split" src="/glaf/scripts/designer/images/toolbar/line.png" style="float: left;height: 38px;width:2px;">');
				} else if (prop.inputMode == 'numberInput') {
					propdom = this.createNumberInput();
				}
			}

			$input_group.append(propdom);
			$package.append($input_group);
			return $package;
		},
		getDataRole: function() {
			return "common";
		},
		createInput: function() {
			var that = this;

			var contentDom = $("<div class=\"form-group\" style=\"padding:0px;margin:0px;\"></div>");
			var inputDom = $("<input type=\"text\" class=\"form-control input-sm\" \>");
			inputDom.attr("id", this.prop.id);
			var ctrImpl = this.prop.ctrImpl;
			var eventName = this.prop.event;
			if (this.prop.defaultVal != null && this.prop.defaultVal != undefined) {
				inputDom.val(this.prop.defaultVal);
			}
			if (this.prop.width) {
				inputDom.css("width", this.prop.width);
			}
			if (this.prop.type) {
				inputDom.attr("type", this.prop.type);
				inputDom.attr("max", this.prop.max);
				inputDom.attr("min", this.prop.min);
			}


			contentDom.append(inputDom);
			var toolbox = this;

			var key = this.prop.id.replace(/-[A-Za-z]/g, function(word) {
				return word.substring(1, 2).toUpperCase();
			});
			var value = "";

			inputDom.val(value);
			//获取事件方法名
			//绑定事件
			if (eventName != undefined && eventName != "") {
				inputDom.blur(function() {
					var inputVal = $(this).val();
					var dataRole;
					try {
						dataRole = toolbox.getDataRole();
						if (typeof ctrImpl == "string") {
							ctrImpl = dataRole != "" ? eval(dataRole + "_designer." + ctrImpl) : eval(ctrImpl);
						}
					} catch (e) {
						console.log(e);
						try {
							if (dataRole != "")
								ctrImpl = eval(ctrImpl)
						} catch (e) {
							console.log(e);
							alert(ctrImpl + '方法不存在！');
						}
					}
					if (typeof ctrImpl === 'function') {
						ctrImpl.call(this, currentComponent, inputVal);
					}
				});
			} else {
				inputDom.change(function() {
					var compType = that.compType; //控件类型
					var inputVal = $(this).val();
					try {
						if (typeof ctrImpl == "string") {
							ctrImpl = eval(compType + "_designer." + ctrImpl) || eval("base_attr_func." + ctrImpl);
						}
					} catch (e) {
						alert(ctrImpl + '方法不存在！');
					}
					if (typeof ctrImpl === 'function') {
						ctrImpl.call(this, that.$component, inputVal);
					}
				});
			}
			return contentDom;
		},
		createRadio: function() {
			var that = this.prop;
			var contentDom = $("<div class=\"form-group\" style=\"padding:0px;margin:0px;\"></div>");
			var inputGroupDom = $("<div class=\"input-group\"></div>");
			var radioGroupDom = $("<div class=\"icheck-inline\"></div>");
			var radioItems = this.prop.valrange;
			var ctrImpl = that.ctrImpl
			var toolbox = this;
			$.each(radioItems, function(i, item) {
				var labelDom = $("<label></label>");
				var radioDom = $("<input type=\"radio\"  name=\"" + that.id + "\" class=\"icheck\" data-radio=\"iradio_flat-grey\">");
				var key = that.id.replace(/-[A-Za-z]/g, function(word) {
					return word.substring(1, 2).toUpperCase();
				});
				// if(currentComponent.attr("data-rule")){
				//    that.defaultVal = JSON.parse(currentComponent.attr("data-rule"))[key];
				// }

				if (that.defaultVal == item.value) {
					radioDom.attr("checked", "checked");
				}
				//获取事件方法名
				//绑定事件
				radioDom.click(function() {
					var compType = that.compType; //控件类型
					var inputVal = $(this).val();
					try {
						if (typeof ctrImpl == "string") {
							ctrImpl = eval(compType + "_designer." + ctrImpl) || eval("base_attr_func." + ctrImpl);
						}
					} catch (e) {
						alert(ctrImpl + '方法不存在！');
					}
					if (typeof ctrImpl === 'function') {
						ctrImpl.call(this, that.$component, checkedVal);
					}
				});
				radioDom.attr("value", item.value);
				labelDom.append(radioDom);
				labelDom.append(item.title);
				radioGroupDom.append(labelDom);
			});
			inputGroupDom.append(radioGroupDom);
			contentDom.append(inputGroupDom);
			return contentDom;
		},
		createSelect: function() {
			var select_dom = $("<select class=\"bs-select form-control\" data-width=\"125px\" data-style=\"btn-primary\"></select>");
			if(this.prop.width){
				select_dom.width(this.prop.width);
			}
			if (this.prop.valrange) {
				var that = this;
				select_dom.attr("name", that.prop.id);
				select_dom.attr("id", that.prop.id);

				//获取已设置的值，或默认值
				var defaultVal = that.getValue(that.prop);
				$.each(this.prop.valrange, function(i, item) {
					var option_dom = $("<option></option>");
					option_dom.attr("value", item.value);
					option_dom.append(item.title);
					/*if(that.prop.value!=null){
						if (that.prop.value == item.value) {
							option_dom.attr("selected", "selected");
						}
					}else{*/
					var key = that.prop.id.replace(/-[A-Za-z]/g, function(word) {
						return word.substring(1, 2).toUpperCase();
					});
					// if(currentComponent.attr("data-rule")){
					//    that.prop.defaultVal = JSON.parse(currentComponent.attr("data-rule"))[key];
					// }
					if (defaultVal == item.value) {
						option_dom.attr("selected", "selected");
					} else {
						//option_dom.attr("selected", "selected");
					}
					//}
					select_dom.append(option_dom);
				});
				var ctrImpl = this.prop.ctrImpl;
				var toolbox = this;
				select_dom.change(function() {

					var selectedVal = $(this).val();
					var compType = that.compType; //控件类型
					ctrImpl = that.getCtrImpl(compType,ctrImpl);

					that.callCtrlImpl(this, ctrImpl, selectedVal);
				});
			}
			return select_dom;
		},
		createNumberInput: function() {
			var contentDom = $("<div class='hmtd-xs' style='margin-top:-3px;'></div>");



			var ctrImpl = this.prop.ctrImpl
			var inputDom = $("<input class=\"form-control spaceTouchSpin spininput\">");



			inputDom.attr("id", this.prop.id + "_" + this.prop.id);
			inputDom.attr("name", this.prop.id + "_" + this.prop.id);
			inputDom.attr("prefix", this.prop.title);
			if (this.prop.icon) {
				inputDom.attr("prefix", "<i class='fa fa-" + this.prop.icon + "' style='color:#000;'></i>");
			}
			if (this.prop.minval)
				inputDom.attr("min", this.prop.minval);
			if (this.prop.maxval)
				inputDom.attr("max", this.prop.maxval);
			var key = this.prop.id.replace(/-[A-Za-z]/g, function(word) {
				return word.substring(1, 2).toUpperCase();
			});
			// if(currentComponent.attr("data-rule")){
			//    this.prop.defaultVal = JSON.parse(currentComponent.attr("data-rule"))[key];
			// }
			if (this.prop.defaultVal)
				inputDom.val(this.prop.defaultVal);
			var toolbox = this;
			inputDom.change(function() {
				var $component = toolbox.findSelectedComponent();
				if (!$component[0]) {
					return;
				}
				var inputVal = $(this).val();
				var unitSelectId = $(this).attr("id") + "_sel";
				var unitVal;
				if ($("#" + unitSelectId).length == 1) {
					unitVal = $("#" + unitSelectId).val();
				}
				var dataRole;
				try {
					dataRole = toolbox.getDataRole();
					if (typeof ctrImpl == 'string') {
						ctrImpl = dataRole != "" ? (eval(dataRole + "_designer." + ctrImpl) != undefined ? eval(dataRole + "_designer." + ctrImpl) : eval(ctrImpl)) : eval(ctrImpl);
					}
				} catch (e) {
					console.log(e);
					try {
						if (dataRole != "")
							ctrImpl = eval(ctrImpl)
					} catch (e) {
						console.log(e);
						alert(ctrImpl + '方法不存在！');
					}
				}
				if (typeof ctrImpl === 'function') {

					if (unitVal != undefined) {
						ctrImpl.call(this, $component, inputVal, unitVal);
					} else {
						ctrImpl.call(this, $component, inputVal);
					}
				}
			});
			contentDom.append(inputDom);
			if (this.prop.unit != undefined && this.prop.unit != '') {

				var unitSelect = $("<select class=\"unitselect\"></select>");
				unitSelect.css("float", "inherit");
				var opts = this.prop.unit.split(",");
				unitSelect.attr("id", this.prop.id + "_" + this.prop.id + "_sel");
				$.each(opts, function(i, opt) {
					unitSelect.append("<option value=\"" + opt + "\">" + opt + "</option>");
				});
				contentDom.append(unitSelect);
				// if(currentComponent.attr("data-rule")){
				// var selectedVal = JSON.parse(currentComponent.attr("data-rule"))[key+"_unit"];
				// if(selectedVal!=undefined&&selectedVal!=''){
				// 	unitSelect.val(selectedVal);
				// }
				// }
				var toolbox = this;
				unitSelect.change(function() {
					var $component = toolbox.findSelectedComponent();
					if (!$component[0]) {
						return;
					}
					var inputVal = $(this).prev().find(".spaceTouchSpin").val();
					var unitVal = $(this).val();
					var dataRole;
					try {
						dataRole = toolbox.getDataRole();
						if (typeof ctrImpl == "string") {
							ctrImpl = dataRole != "" ? eval(dataRole + "_designer." + ctrImpl) : eval(ctrImpl);
						}
					} catch (e) {
						console.log(e);
						try {
							if (dataRole != "")
								ctrImpl = eval(ctrImpl)
						} catch (e) {
							console.log(e);
							alert(ctrImpl + '方法不存在！');
						}
					}
					if (typeof ctrImpl === 'function') {
						if (unitVal != undefined && unitVal != '') {
							ctrImpl.call(this, $component, inputVal, unitVal);
						} else {
							ctrImpl.call(this, $component, inputVal);
						}
					}
				});
			}
			return contentDom;
		},
		createTouchspin: function() {
			if (this.prop.properties) {
				var that = this;
				var contentDom = $("<div class='hmtd-xs' style='margin-top:-3px;'></div>");
				var toolbox = this;
				$.each(this.prop.properties, function(i, item) {
					var ctrImpl = item.ctrImpl;
					var inputDom = $("<input class=\"form-control spaceTouchSpin spininput\">");
					if (item.width) {
						inputDom.attr("widthSize", item.width);
					} else {
						inputDom.attr("widthSize", "75px");
					}
					// inputDom.attr("id",that.id+"_"+item.id);
					inputDom.attr("id", item.id);
					inputDom.attr("name", item.id);
					// inputDom.attr("name",that.id+"_"+item.id);
					inputDom.attr("prefix", item.title);
					if (item.icon) {
						inputDom.attr("prefix", "<i class='fa fa-" + item.icon + "' style='color:#000;'></i>");
					}
					if (item.minval)
						inputDom.attr("min", item.minval);
					if (item.maxval)
						inputDom.attr("max", item.maxval);
					var key = item.id.replace(/-[A-Za-z]/g, function(word) {
						return word.substring(1, 2).toUpperCase();
					});
					// if(currentComponent.attr("data-rule")){
					//    item.defaultVal = JSON.parse(currentComponent.attr("data-rule"))[key];
					// }
					inputDom.val(that.getValue(item));

					inputDom.change(function() {

						var inputVal = $(this).val();
						var unitSelectId = $(this).attr("id") + "_sel";
						var unitVal;
						if ($("#" + unitSelectId).length == 1) {
							unitVal = $("#" + unitSelectId).val();
						}

						var compType = that.compType; //控件类型
						ctrImpl = that.getCtrImpl(compType,ctrImpl);

						if (typeof ctrImpl === 'function') {

							if (unitVal != undefined) {
								that.callCtrlImpl(this, ctrImpl, inputVal, unitVal);
								// ctrImpl.call(this, that.findSelectedComponent,that.findNowStyle(), inputVal, unitVal);
							} else {
								that.callCtrlImpl(this, ctrImpl, inputVal);
								// ctrImpl.call(this, that.findSelectedComponent,that.findNowStyle(), inputVal);
							}
						}
					});
					contentDom.append(inputDom);
					if (item.unit != undefined && item.unit != '') {

						var unitSelect = $("<select class=\"unitselect\"></select>");
						unitSelect.css("float", "inherit");
						var opts = item.unit.split(",");
						unitSelect.attr("id", that.id + "_" + item.id + "_sel");
						$.each(opts, function(i, opt) {
							unitSelect.append("<option value=\"" + opt + "\">" + opt + "</option>");
						});
						contentDom.append(unitSelect);
						// if(currentComponent.attr("data-rule")){
						// var selectedVal = JSON.parse(currentComponent.attr("data-rule"))[key+"_unit"];
						// if(selectedVal!=undefined&&selectedVal!=''){
						// 	unitSelect.val(selectedVal);
						// }
						// }
						unitSelect.change(function() {
							var $component = toolbox.findSelectedComponent();
							if (!$component[0]) {
								return;
							}
							var inputVal = $(this).prev().find(".spaceTouchSpin").val();
							var unitVal = $(this).val();

							//实例方法调用
							var compType = that.compType;
							ctrImpl = that.getCtrImpl(compType,ctrImpl)
							if (typeof ctrImpl === 'function') {

								if (unitVal != undefined) {
									that.callCtrlImpl(this, ctrImpl, inputVal, unitVal);
								} else {
									that.callCtrlImpl(this, ctrImpl, inputVal);
								}
							}
						});
					}
				});
			}
			return contentDom;
		},
		createButtonGroup: function() {
			var contentDom = $("<div class=\"btn-group\" id='" + this.prop.id + "' type='btn'></div>");
			if (this.prop.valrange) {
				var that = this;
				var ctrImpl = this.prop.ctrImpl;
				var toolbox = this;
				$.each(this.prop.valrange, function(i, item) {
					var buttonDom = $("<button type=\"button \" class=\"btn btn-default  btn-sm attrBtn\"></button>");
					if (item.icon) {
						buttonDom.append("<i class='fa fa-" + item.icon + "'></i>");
					}
					buttonDom.append(item.name);
					buttonDom.attr("title", item.title);
					buttonDom.attr("value", item.value);

					if(item.value == that.getRealValue(that.prop)){
						buttonDom.addClass("active");
					}

					buttonDom.click(function() {
						if ($(this).hasClass('active')) {
							$(this).removeClass('active');
						} else {
							contentDom.find(".btn").removeClass('active');
							$(this).addClass('active');
						}
						var inputVal = "";
						if ($(this).hasClass('active')) {
							inputVal = $(this).attr("value");
						}

						//实例方法调用
						var compType = that.compType;
						ctrImpl = that.getCtrImpl(compType,ctrImpl);
						if (typeof ctrImpl === 'function') {
							that.callCtrlImpl(this, ctrImpl, inputVal);
						}
					});
					contentDom.append(buttonDom);
				});
			}
			return contentDom;
		},
		createMulitSelButtonGroup: function() {
			var contentDom = $("<div class=\"btn-group\"></div>");
			if (this.prop.valrange) {
				var that = this;
				var ctrImpl = this.prop.ctrImpl;
				var toolbox = this;
				var key = this.prop.id.replace(/-[A-Za-z]/g, function(word) {
					return word.substring(1, 2).toUpperCase();
				});
				var value = "";
				// if(currentComponent.attr("data-rule")){
				// 	value = JSON.parse(currentComponent.attr("data-rule"))[key];
				// }
				$.each(this.prop.valrange, function(i, item) {
					var buttonDom = $("<button type=\"button\" class=\"btn btn-default  btn-sm\"></button>");
					if (item.icon) {
						buttonDom.append("<i class='fa fa-" + item.icon + "'></i>");
					}
					buttonDom.append(item.name);
					buttonDom.attr("title", item.title);
					buttonDom.attr("value", item.value);
					if (value != undefined && value != '' && value.indexOf(item.value) > -1) {
						buttonDom.addClass('active');
						buttonDom.removeClass('btn-default');
						buttonDom.addClass('btn-success');
					}
					buttonDom.click(function() {
						var $component = toolbox.findSelectedComponent();
						if (!$component[0]) {
							return;
						}
						if ($(this).hasClass('active')) {
							$(this).removeClass('active');
							$(this).removeClass('btn-success');
							$(this).addClass('btn-default');
						} else {
							$(this).addClass('active');
							$(this).removeClass('btn-default');
							$(this).addClass('btn-success');
						}

						//获取选中的值
						var btgroup = $(this).closest(".btn-group");
						var selectedBts = btgroup.find(".active");
						var inputVal = new Array();
						$.each(selectedBts, function(i, selectedBt) {
							inputVal.push($(selectedBt).attr("value"));
						});
						var dataRole;
						try {
							dataRole = toolbox.getDataRole();
							if (typeof ctrImpl == "string") {
								ctrImpl = dataRole != "" ? eval(dataRole + "_designer." + ctrImpl) : eval(ctrImpl);
							}
						} catch (e) {
							console.log(e);
							try {
								if (dataRole != "")
									ctrImpl = eval(ctrImpl)
							} catch (e) {
								console.log(e);
								alert(ctrImpl + '方法不存在！');
							}
						}
						if (typeof ctrImpl === 'function') {
							ctrImpl.call(this, $component, inputVal);
						}
					});
					contentDom.append(buttonDom);
				});
			}
			return contentDom;
		},
		createColorPicker: function() {
			var that = this;
			var inputDom = $(" <input type=\"text\" class=\"form-control colorpicker\">");
			inputDom.attr("id", this.prop.id);
			var ctrImpl = this.prop.ctrImpl;
			var toolbox = this;
			var key = this.prop.id.replace(/-[A-Za-z]/g, function(word) {
				return word.substring(1, 2).toUpperCase();
			});
			var value = "";
			// if(currentComponent.attr("data-rule")){
			// 	value = JSON.parse(currentComponent.attr("data-rule"))[key];
			// }
			// if (that.styleData[this.prop.id]) {
			// 	value = that.styleData[this.prop.id];
			// }
			inputDom.attr("value", that.getValue(this.prop));
			inputDom.change(function() {
				var inputVal = $(this).val();
				var dataRole;
				//实例方法调用
				var compType = that.compType;
				ctrImpl = that.getCtrImpl(compType,ctrImpl);
				if (typeof ctrImpl === 'function') {
					that.callCtrlImpl(this, ctrImpl, inputVal);
				}
			});
			return inputDom;
		},
		createRangeSlider: function() {
			var inputDom = $(" <input type=\"text\" class=\"rangeSlider\"/>");
			inputDom.attr("id", this.prop.id);
			var ctrImpl = this.prop.ctrImpl;
			var toolbox = this;
			var key = this.prop.id.replace(/-[A-Za-z]/g, function(word) {
				return word.substring(1, 2).toUpperCase();
			});
			var value = "";
			// if(currentComponent.attr("data-rule")){
			// 	value = JSON.parse(currentComponent.attr("data-rule"))[key];
			// }
			inputDom.val(value);
			inputDom.change(function() {
				var $component = toolbox.findSelectedComponent();
				if (!$component[0]) {
					return;
				}
				var inputVal = $(this).val();
				var dataRole;
				try {
					dataRole = toolbox.getDataRole();
					if (typeof ctrImpl == "string") {
						ctrImpl = dataRole != "" ? eval(dataRole + "_designer." + ctrImpl) : eval(ctrImpl);
					}
				} catch (e) {
					console.log(e);
					try {
						if (dataRole != "")
							ctrImpl = eval(ctrImpl)
					} catch (e) {
						console.log(e);
						alert(ctrImpl + '方法不存在！');
					}
				}
				if (typeof ctrImpl === 'function') {
					ctrImpl.call(this, $component, inputVal);
				}
			});
			return inputDom;
		},
		createTemplate: function() {
			var template = this.prop.template;
			var toolbox = this;
			var ctrImpl = this.prop.ctrImpl;

			var key = this.prop.id.replace(/-[A-Za-z]/g, function(word) {
				return word.substring(1, 2).toUpperCase();
			});
			var value = "";
			// if(currentComponent.attr("data-rule")){
			// 	value = JSON.parse(currentComponent.attr("data-rule"))[key];
			// }
			var dataRole = toolbox.getDataRole();
			var initImpl = dataRole != "" ? eval(dataRole + "_designer." + this.prop.initImpl) : eval(initImpl);
			if (typeof initImpl === 'function') {
				template = initImpl(template, value);
			}
			var hidden = $("<input type=\"hidden\">");
			hidden.attr("id", key);
			hidden.click(function() {
				var inputVal = $(this).val();
				var dataRole;
				try {
					dataRole = toolbox.getDataRole();
					if (typeof ctrImpl == "string") {
						ctrImpl = dataRole != "" ? eval(dataRole + "_designer." + ctrImpl) : eval(ctrImpl);
					}
				} catch (e) {
					console.log(e);
					try {
						if (dataRole != "")
							ctrImpl = eval(ctrImpl)
					} catch (e) {
						console.log(e);
						alert(ctrImpl + '方法不存在！');
					}
				}
				if (typeof ctrImpl === 'function') {
					ctrImpl.call(this, currentComponent, inputVal);
				}
			});
			hidden.val(value);
			$template = $(template).append(hidden);
			return $template;
		}
	};
})(jQuery);