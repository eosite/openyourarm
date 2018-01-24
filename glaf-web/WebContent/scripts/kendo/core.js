//图片放大显示
function zoomViewImg(e) {
	var $that = $(this),
		width = $(window).width(),
		height = $(window).height(),
		ileft = (width - 800) / 2,
		itop = (height - 800) / 2;
	if ($that.hasClass("showWin")) {
		var path = contextPath + "/mx/form/defined/viewImg?srcl=";
		window.open(path + $that.attr('src').replace(/&/ig, "@"), '查看图片', "width=" + screen.width + ",height=" + screen.height + ",modal=true,status=no,scrollbars=no,resizable=no");
	} else {
		var $body = $('body');
		$body.append('<div class="hock_wrap" style="display: block; width: 100%;height: 100%; opacity: 0.5;position: absolute;background:#000;top:0;left:0;z-index:9999"></div>');
		$body.append('<div class="img_view_wrap hock_wrap" style="width: 100%;height:100%;vertical-align:middle;align-items: center;line-height:100%;text-align:center;position: absolute;top:0;left:0;z-index:99999"><img src="' + $that.attr('src') + '" style="vertical-align:middle"></div>');
		var $wrap = $('.img_view_wrap');
		/*$wrap.css({
			'margin-left': -$wrap.width() / 2,
			'margin-top': -$wrap.height() / 2
		});*/
		function initImgSize(e) {
			width = e ? $(window).width() : width;
			height = e ? $(window).height() : height;
			var $img = $(".img_view_wrap > img");
			$wrap.css({
				'line-height': height + 'px'
			});
			if ((height / width) < ($img.height() / $img.width())) {
				if ($img.height() >= height || e) {
					$img.width('').height(height - 5);
				}

			} else {
				if ($img.width() >= width || e) {
					/*$wrap.css({
						display: 'flex'
					});*/
					$img.width(width - 5).height('');
				}
			}
		}

		$(window).resize(initImgSize);
		initImgSize();
		/*$(".img_view_wrap > img").on("mousewheel",function(e){
			var $t = $(this);
			if(e.originalEvent.wheelDelta>0){//up
				$t.css({
					transform: "scale(0.8, 0.8)"
				});
			}else if(e.originalEvent.wheelDelta<0){//down
				$t.css({
					transform: "scale(1.2, 1.2)"
				});
			}
		})*/
		$(".hock_wrap").on("click", function(e) {
			$(".hock_wrap").remove();
		});
	}
};


var baseFunc = function() {
	return {
		thidden: function(rule, args) { // 隐藏
			pubsub.getJQObj(rule).hide();
		},
		tshow: function(rule, args) { // 显示
			pubsub.getJQObj(rule).show();
		},
		tdisabled: function(rule, args) { // 禁用
			pubsub.getJQObj(rule).attr("disabled", "disabled");
		},
		tenabled: function(rule, args) { // 启用
			pubsub.getJQObj(rule).removeAttr("disabled");
		},
		tclear: function(rule, args) {
			kendo.widgetInstance(pubsub.getJQObj(rule, true)).value('');
		},
		_init_: function(rule, param, args) { //控件初始化事件
			var $id = pubsub.getJQObj(rule);
			eval("(" + __gobalInit__[$id.attr("id")] + ")")($.isArray(param) ? {} : param);
			pubsub.execChilds(rule);
		},
	}
};
var validateMethods = {
	// 邮件
	email: function(value, element) {
		return /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/.test(value);
	},

	// URL
	url: function(value, element) {
		return /^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})).?)(?::\d{2,5})?(?:[/?#]\S*)?$/i.test(value);
	},

	// 日期
	date: function(value, element) {
		return !/Invalid|NaN/.test(new Date(value).toString());
	},

	// ISO日期
	dateISO: function(value, element) {
		return /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$/.test(value);
	},

	// 数值
	number: function(value, element) {
		return /^(?:-?\d+|-?\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/.test(value);
	},
	// 数字
	digits: function(value, element) {
		return /^\d+$/.test(value);
	},
	// 信用卡号
	creditcard: function(value, element) {
		if (/[^0-9 \-]+/.test(value)) {
			return false;
		}
		var nCheck = 0,
			nDigit = 0,
			bEven = false,
			n, cDigit;

		value = value.replace(/\D/g, "");
		if (value.length < 13 || value.length > 19) {
			return false;
		}

		for (n = value.length - 1; n >= 0; n--) {
			cDigit = value.charAt(n);
			nDigit = parseInt(cDigit, 10);
			if (bEven) {
				if ((nDigit *= 2) > 9) {
					nDigit -= 9;
				}
			}
			nCheck += nDigit;
			bEven = !bEven;
		}

		return (nCheck % 10) === 0;
	}
};
var validateMessage = {
	required: "{0} 必须填写",
	email: "{0} 格式错误,请输入正确邮件",
	dateEqual: "{0} 大于\"{1}\"小于\"{2}\"",
	checkNum: "{0} 至少选中 {1} 项"
};
var validateObj = {
	validate_type_foucs: function($this) {
		$this.on('blur', function(event) {
			$(this).data("kendoValidator").validate();
		});
	},
	validate_type_save: function($this) {
		$this.attr("valiSave", "true");
	},
	bindEvent: function() {
		$("[mtValidate=true]")
			.kendoValidator({
				validateOnBlur: false,
				rules: {
					customRule: function(k) {
						return initPageValidate.prototype.customRule(k);
					}
				},
				messages: {
					customRule: function(k) {
						var errorMsg = k.data("mt-vail-er-msg");
						if (errorMsg) {
							k.removeData('mt-vail-er-msg');
							return errorMsg;
						}
						return k.data("mtValidate").tip;
					}
				}
			}).removeAttr('title');
	}
};
/**
 *验证必填
 */
var getSpreadValue = function($this, trigger) {
	var spread = $this.data("spread"),
		sheet = spread.getActiveSheet(),
		inid = trigger["inid"],
		inids = inid.split("-"),
		val = sheet.getValue(inids[0], inids[1]);
	return val;
}
var validate = function($this) {
	var trigger = $this.data("currentCell"),
		val;
	if (trigger) {
		/*var mtVailErMsg = $this.data("mt-vail-er-msg") || {} ;
		mtVailErMsg[]
		$this.data("mtVailErMsg",mtVailErMsg);*/
		val = getSpreadValue($this, trigger);
	} else {
		$this.data("mt-vail-er-msg", kendo.format(validateMessage['required'], $this.attr("mtTitle")));
		val = $this.val();
	}
	return $.trim(val) !== "";
};
var validateType = function($this, type) {
	var trigger = $this.data("currentCell"),
		val;
	if (trigger) {
		val = getSpreadValue($this, trigger);
	} else {
		$this.data("mt-vail-er-msg", kendo.format(validateMessage[type], $this.attr("mtTitle")));
		val = $this.val()
	}
	return validateMethods[type](val);
};
var validateDate = function($this, minDate, maxDate) {
	var trigger = $this.data("currentCell"),
		val;
	if (trigger) {
		val = getSpreadValue($this, trigger);
	} else {
		$this.data("mt-vail-er-msg", kendo.format(validateMessage["dateEqual"], $this.attr("mtTitle"), minDate, maxDate));
		val = $this.val();
	}
	var curTime = kendo.parseDate($.trim(val), ""),
		bol = false;
	if (minDate && maxDate)
		bol = (curTime - minDate > 0) && (maxDate - curTime > 0);
	return bol;
};
var validateLength = function($this, min, max) {
	var trigger = $this.data("currentCell"),
		val;
	if (trigger) {
		val = getSpreadValue($this, trigger);
	} else {
		$this.data("mt-vail-er-msg", kendo.format(validateMessage["dateEqual"], $this.attr("mtTitle"), min, max));
		val = $this.val();
	}
	var curVal = $.trim(val),
		length = curVal.length;
	try {
		if ($.isNumeric(curVal)) {
			return (curVal - min > 0) && (max - curVal > 0);
		} else {
			return (length - min > 0) && (max - length > 0)
		}
	} catch (e) {
		return false;
	}
};
var validateCheckNum = function($this, num) {
	var trigger = $this.data("currentCell"),
		val;
	if (trigger) {
		return false;
	} else {
		$this.data("mt-vail-er-msg", kendo.format(validateMessage["checkNum"], $this.attr("mtTitle"), num));
		var checkNum = $("input[name=" + $this.attr("name") + "]").prop("checked");
		if (checkNum.length - num >= 0) {
			return true;
		}
		return false;
	}
};

function initPageConfigFunc() {};