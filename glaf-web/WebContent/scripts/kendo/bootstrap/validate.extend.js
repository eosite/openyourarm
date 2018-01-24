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
/**
 *验证必填
 */
var validateMessage = {
	required: "{0} 必须填写",
	email: "{0} 格式错误,请输入正确邮件",
	dateEqualgt: "{0} 必须大于\"{1}",
	dateEqual: "{0} 必须大于\"{1}\"小于\"{2}\"",
	dateAllEqual: "{0} 必须等于\"{1}\"",
	checkNum: "{0} 至少选中 {1} 项",
	number: "{0} 必须输入数字",
};
var validate = function($this) {
	$this.data("mt-vail-er-msg", validateMessage['required'].format($this.attr("mtTitle")));
	setCustomRule($this, $this.data("mt-vail-er-msg"));
	return $.trim(/*$('#' + $this.data('ruleId'))*/$this.mtbootstrap('getValue')) !== "";
};
var validateType = function($this, type) {
	$this.data("mt-vail-er-msg", validateMessage[type].format($this.attr("mtTitle")));
	setCustomRule($this, $this.data("mt-vail-er-msg"));
	return validateMethods[type](/*$('#' + $this.data('ruleId'))*/$this.mtbootstrap('getValue'));
};

var validateFilesUpload = function($this){
	var $target = $this.closest("[data-role='jqfileupload2']");
	$this.data("mt-vail-er-msg", "必须上传文件！");
	setCustomRule($this, $this.data("mt-vail-er-msg"));
	return $target.spFileUpload("hasFiles");
};
var validateDate = function($this, minDate, maxDate) {
	var formatStr = "yyyy-MM-dd";
	var role = $this.closest("div").attr("data-role");
	if (role == "datetimepickerbt") {
		formatStr = "yyyy-MM-dd HH:mm:ss";
	}
	if (!maxDate) {
		$this.data("mt-vail-er-msg", validateMessage["dateEqualgt"].format($this.attr("mtTitle"), hmtdUtils.format("{0:" + formatStr + "}", hmtdUtils.parseDate(minDate))));
	} else {
		$this.data("mt-vail-er-msg", validateMessage["dateEqual"].format($this.attr("mtTitle"), hmtdUtils.format("{0:" + formatStr + "}", hmtdUtils.parseDate(minDate)), hmtdUtils.format("{0:" + formatStr + "}", hmtdUtils.parseDate(maxDate))));
	}
	setCustomRule($this, $this.data("mt-vail-er-msg"));
	var curTime = hmtdUtils.parseDate($.trim(/*$('#' + $this.data('ruleId'))*/$this.mtbootstrap('getValue')), ""),
		bol = false,
		bol1 = true,
		bol2 = true;
	if (minDate)
		bol1 = (curTime - hmtdUtils.parseDate(minDate) > 0);
	if (maxDate)
		bol2 = (hmtdUtils.parseDate(maxDate) - curTime > 0);
	return bol1 && bol2;
};
var validateLength = function($this, min, max, equal) {
	if(equal != null){
		$this.data("mt-vail-er-msg", validateMessage["dateAllEqual"].format($this.attr("mtTitle"), equal));
	}else{
		$this.data("mt-vail-er-msg", validateMessage["dateEqual"].format($this.attr("mtTitle"), min, max));
	}
	setCustomRule($this, $this.data("mt-vail-er-msg"));
	var curVal = $.trim(/*$('#' + $this.data('ruleId'))*/$this.mtbootstrap('getValue')),
		length = curVal.length;
	try {
		if (typeof curVal == 'number' && $.isNumeric(curVal)) {
			if(equal != null){
				return (curVal == equal);
			}else{
				return (curVal - min > 0) && (max - curVal > 0);	
			}
		} else {
			if(equal != null){
				return (length == equal);
			}else{
				return (length - min > 0) && (max - length > 0)
			}
		}
	} catch (e) {
		return false;
	}
};
var validateCheckNum = function($this, num) {
	$this.data("mt-vail-er-msg", validateMessage["checkNum"].format($this.attr("mtTitle"), num));
	setCustomRule($this, $this.data("mt-vail-er-msg"));
	var checkNum = $("input[name=" + $this.attr("name") + "]").prop("checked");
	if (checkNum.length - num >= 0) {
		return true;
	}
	return false;
};