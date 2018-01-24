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
		}
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

function initPageConfigFunc(){};/**
 * 工具类
 */

/**
 * 以XX开始
 * @param s
 * @returns {Boolean}
 */
String.prototype.startsWith = function(s) {
    if (s == null || s == "" || this.length == 0 || s.length > this.length)
        return false;
    if (this.substr(0, s.length) == s)
        return true;
    else
        return false;
    return true;
};
/**
 * 以xx结束
 * @param s
 * @returns {Boolean}
 */
String.prototype.endsWith = function(s) {
    if (s == null || s == "" || this.length == 0 || s.length > this.length)
        return false;
    if (this.substring(this.length - s.length) == s)
        return true;
    else
        return false;
    return true;
};
/**
 * 数组删除
 * @param  from to 
 * @returns 
 */
Array.prototype.remove = function(from, to) {
    var rest = this.slice((to || from) + 1 || this.length);
    this.length = from < 0 ? this.length + from : from;
    return this.push.apply(this, rest);
};
Array.prototype.removeObj = function(obj) {
    return this.remove(this.indexOf(obj));
};
/**
 * 定时、延时工具类
 * @param 
 * @returns 工具类
 */
var mtutils = (function() {
    var utils = {};
    utils.sleep = function(numberMillis) {
        var now = new Date();
        var exitTime = now.getTime() + numberMillis;
        while (true) {
            now = new Date();
            if (now.getTime() > exitTime)
                return;
        }
    };
    utils.eachDelays = function(fn, numberMillis) {
        this.sleep(numberMillis);
        if (typeof fn == "function")
            return fn.call(this);
    };
    return utils;
})();

//获取当前地址栏的url的开头 http://ip:port/
function getUrlToSplitter() {
    var _href = window.location.href;
    var re = /(https?:\/\/)?(\w+\.?)+(\:\d+)?/; // 将匹配
    // 上面的分组都是捕获性分组
    var arr = re.exec(_href);
    if (arr) {
        return arr[0];
    } else {
        return '';
    }
};

function getUrlNoHttp() {
    var _href = window.location.href;
    var re = /(https?:\/\/)?((\w+\.?)+(\:\d+)?)/; // 将匹配
    // 上面的分组都是捕获性分组
    var arr = re.exec(_href);
    if (arr) {
        return arr[2];
    } else {
        return '';
    }
};
/**
 *获取系统时间
 */
var getSysDate = function() {
    return JSON.parse(pageParameters)['sys_date'];
};

//表达式函数集
var mtException = (function() {
    var mt = {};
    //数组包含
    mt.arrayContains = function(s, o) {
        if (o && s) {
            var ary = o.split(",");
            for (var i = 0; i < ary.length; i++) {
                if (s.indexOf(ary[i]) != -1)
                    return true;
            }
        }
        return false;
    };
    //序号生成器
    mt.seqGenerator = function(s, l , n) {
        var sl = (s+"").length,
            ary = [] ;
        !l && (l = sl) ;
        s = parseInt(s);
        s = (isNaN(s)?0:s) + 1 ;
        var difLen = l - (s+"").length ;
        ary.length = difLen<=0?0:(difLen+1) ;
        return ary.join(n?n:"0") + s ;
    };
    //文本连接（合并）
    mt.conc = function(s, l) {
        return s+""+l ;
    };
    //文本长度
    mt.len = function(s) {
        return s?s.length:0;
    };
    //位置查找
    mt.find = function(s,p) {
        return s&&p?s.indexOf(p):-1;
    };
    //位置查找忽略英文大小写
    mt.search = function(s,p) {
        s += "";
        p += "";
        return s&&p?s.toUpperCase().indexOf(p.toUpperCase()):-1;
    };
    //返回小写
    mt.lower = function(s) {
        s += "";
        return s?s.toLowerCase():s;
    };
    mt.upper = function(s) {
        s += "";
        return s?s.toUpperCase():s;
    };
    //截取字符串
    mt.subString = function(s,f,t) {
        s+="";
        return t?s.substring(f,t):s.substring(f);
    };
    //去除空格
    mt.trim = function(s) {
        return $.trim(s);
    };
    //去除空格
    mt.replace = function(s,o,n) {
        return s.replace(new RegExp(o),n);
    };
    mt.replaceAll = function(s,o,n) {
        return s.replace(new RegExp(o,"gm"),n);
    };
    //数字转大写
    mt.digitUppercase = function(n){
        var n = parseFloat(n);
        var fraction = ['角', '分'];  
        var digit = [  
            '零', '壹', '贰', '叁', '肆',  
            '伍', '陆', '柒', '捌', '玖'  
        ];  
        var unit = [  
            ['元', '万', '亿'],  
            ['', '拾', '佰', '仟']  
        ];  
        var head = n < 0 ? '欠' : '';  
        n = Math.abs(n);  
        var s = '';  
        for (var i = 0; i < fraction.length; i++) {  
            s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');  
        }  
        s = s || '整';  
        n = Math.floor(n);  
        for (var i = 0; i < unit[0].length && n > 0; i++) {  
            var p = '';  
            for (var j = 0; j < unit[1].length && n > 0; j++) {  
                p = digit[n % 10] + unit[1][j] + p;  
                n = Math.floor(n / 10);  
            }  
            s = p.replace(/(零.)*零$/, '').replace(/^$/, '零') + unit[0][i] + s;  
        }  
        return head + s.replace(/(零.)*零元/, '元')  
            .replace(/(零.)+/g, '零')  
            .replace(/^整$/, '零元整');
    }

    return mt;
})();

//数学公式函数集
var mtMath = (function() {
    var mt = {};
    //四舍五入
    mt.round = function(s) {
        if (s && $.isNumeric(s)) {
            return Math.round(s);
        }
        return 0;
    };
    //ceil 向上取整
    mt.ceil = function(s) {
        if (s && $.isNumeric(s)) {
            return Math.ceil(s);
        }
        return 0;
    };
    //floor 向下取整
    mt.floor = function(s) {
        if (s && $.isNumeric(s)) {
            return Math.floor(s);
        }
        return 0;
    };
    //formatNumber 格式化数字保留几位小数
    mt.formatNumber = function(s, n) {
        if (s && $.isNumeric(s)) {
            return parseFloat(s.toFixed(n || 2));
        }
        return 0;
    };
    //加 提供对大数值的相加
    mt.add = function(arg1, arg2) {
        var r1, r2, m;
        try {
            r1 = arg1.toString().split(".")[1].length;
        } catch (e) {
            r1 = 0;
        }
        try {
            r2 = arg2.toString().split(".")[1].length;
        } catch (e) {
            r2 = 0;
        }
        m = Math.pow(10, Math.max(r1, r2));
        return (arg1 * m + arg2 * m) / m;
    };
    //减 提供对大数值的相加
    mt.sub = function(arg1, arg2) {
        var r1, r2, m, n;
        try {
            r1 = arg1.toString().split(".")[1].length;
        } catch (e) {
            r1 = 0;
        }
        try {
            r2 = arg2.toString().split(".")[1].length;
        } catch (e) {
            r2 = 0;
        }
        m = Math.pow(10, Math.max(r1, r2));
        //last modify by deeka  
        //动态控制精度长度  
        n = (r1 >= r2) ? r1 : r2;
        return ((arg1 * m - arg2 * m) / m).toFixed(n);
    };
    //乘 提供对大数值的相加
    mt.mul = function(arg1, arg2) {
        var m = 0,
            s1 = arg1.toString(),
            s2 = arg2.toString();
        try {
            m += s1.split(".")[1].length;
        } catch (e) {}
        try {
            m += s2.split(".")[1].length;
        } catch (e) {}
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
    };
    //除 提供对大数值的相加
    mt.div = function(arg1, arg2) {
        var t1 = 0,
            t2 = 0,
            r1, r2;
        try {
            t1 = arg1.toString().split(".")[1].length;
        } catch (e) {}
        try {
            t2 = arg2.toString().split(".")[1].length;
        } catch (e) {}
        r1 = Number(arg1.toString().replace(".", ""));
        r2 = Number(arg2.toString().replace(".", ""));
        return (r1 / r2) * Math.pow(10, t2 - t1);
    };
    return mt;
})();

var mtIsMobile =(function(){
    var mt = {};
    var isMobile = {   
        Android: function() {   
            return navigator.userAgent.match(/Android/i) ? true : false;   
        },   
        BlackBerry: function() {   
            return navigator.userAgent.match(/BlackBerry/i) ? true : false;   
        },   
        iOS: function() {   
            return navigator.userAgent.match(/iPhone|iPad|iPod/i) ? true : false;   
        },   
        Windows: function() {   
            return navigator.userAgent.match(/IEMobile/i) ? true : false;   
        },   
        any: function() {   
            return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Windows());   
        }   
    };
    mt.isMobile = function(){
        return isMobile.any();
    }
    return mt;
})();
   
//日期函数集
var mtDate = (function() {
    var mt = {};
    // 获取月份
    mt.getMonthStr = function(s) {
        return new Date(s).getMonth() + 1;
    };
    //获取时间差
    mt.getDiffDatetime = function(d1, d2, type) {
        d1 = hmtdUtils.parseDate(d1);
        d2 = hmtdUtils.parseDate(d2);
        d3 = d2.getTime() - d1.getTime();
        var retVal = 0;
        switch (type) {
            case "Y": //计算年
                retVal = d2.getFullYear() - d1.getFullYear();
                break;
            case "M": //计算月
                retVal = ((d2.getFullYear() - d1.getFullYear()) * 12) + d2.getMonth() - d1.getMonth();
                break;
            case "D": //计算日
                retVal = Math.floor(d3 / (24 * 3600 * 1000))
                break;
            case "H": //计算时
                retVal = Math.floor((d3 % (24 * 3600 * 1000)) / (3600 * 1000));
                break;
            case "MI": //计算分
                retVal = Math.floor((d3 % (24 * 3600 * 1000) % (3600 * 1000)) / (60 * 1000));
                break;
            case "S": //计算秒
                etVal = Math.round((d3 % (24 * 3600 * 1000) % (3600 * 1000) % (60 * 1000)) / 1000);
                break;
            default:
        }
        return retVal;
    };
    //获取excel日期
    mt.cellToDate = function(d,format){
        return hmtdUtils.toString(hmtdUtils.parseDate((d -25569) * 864e5 ),format || "yyyy-MM-dd HH:mm:ss");
    };
    //格式化日期
    mt.formatDatetime = function(d,format){
        return hmtdUtils.toString(hmtdUtils.parseDate(d), format || "yyyy-MM-dd HH:mm:ss");
    };
    return mt;
})();


//文件查看
function viewFile(e) {
    var $that = $(e),
        ridObj = $that.parents("[rid]"),
        rid = ridObj.attr("rid") || "";
    var path = contextPath + "/mx/form/defined/viewFile?model=" + $that.attr("model") + "&rp=" + $that.attr("tval") + "&id=" + rid + "&key=" + $that.attr("key") + "&url=" + $that.attr("url") + "&fid=" + $that.attr("fid");
    window.open(path, '查看附件', "modal=true,status=no");
};

function viewFiles(e) {
    function fileTypes(html, type) {
        var doc = [".doc", ".docx", ".xls", ".xlsx", ".ppt"];
        if (type == "tif") {
            doc = [".tif", ".tiff"];
        } else if (type == "pdf") {
            doc = [".pdf"];
        } else if (type == 'txt'){
            doc = [".txt"];
        }
        for (var i = 0; i < doc.length; i++) {
            if (html.toLowerCase().indexOf(doc[i]) != -1) {
                return true;
            }
        }
        return false;
    }
    var $that = $(e),
        html = $that.html(),
        ridObj = $that.parents("[rid]"),
        rid = ridObj.attr("rid") || "",
        fid = $that.attr("fid"),
        model = $that.attr("model"),
        rp = $that.attr("kid"),
        key = $that.attr("key"),
        dbid = $that.attr("dbid"),
        url = $that.attr("url"),
        params = "?id=" + rp,
        //path = contextPath + "/mx/form/defined/viewImg?id=";
        path = contextPath + "/mx/form/defined/viewImg";
    if (fid && key && url) {
        params = "?model=FTP&rp=" + rp + "&id=" + rid + "&key=" + key + "&url=" + url + "&fid=" + fid+ "&databaseId=" + (dbid||"");
    }
    if (fileTypes(html, "doc")) {
        path = contextPath + "/mx/form/defined/viewFile";
        //path = contextPath + "/mx/form/defined/viewFile?id=";
    } else if (fileTypes(html, "tif")) {
        path = contextPath + "/mx/form/defined/viewTif";
    } else if (fileTypes(html, "pdf")) {
        path = contextPath + "/mx/form/defined/viewPdf";
    } else if (fileTypes(html,"txt")){
        path = contextPath + "/mx/form/defined/viewText"
    }
    window.open(path + params, '查看图片', "modal=true,status=no,scrollbars=yes,resizable=yes");
}


//日期和数值格式化
(function() {
    var mt = {
            cultures: {}
        },
        dateFormatRegExp = /dddd|ddd|dd|d|MMMM|MMM|MM|M|yyyy|yy|HH|H|hh|h|mm|m|fff|ff|f|tt|ss|s|zzz|zz|z|"[^"]*"|'[^']*'/g,
        formatRegExp = /\{(\d+)(:[^\}]+)?\}/g,
        standardFormatRegExp = /^(n|c|p|e)(\d*)$/i,
        literalRegExp = /(\\.)|(['][^']*[']?)|(["][^"]*["]?)/g,
        commaRegExp = /\,/g,
        EMPTY = "",
        POINT = ".",
        COMMA = ",",
        SHARP = "#",
        ZERO = "0",
        PLACEHOLDER = "??",
        EN = "en-US",
        CN = "zh-CN",
        objectToString = {}.toString,
        NUMBER = "number",
        STRING = "string",
        zeros = ["", "0", "00", "000", "0000"],
        globalize = window.MTGlobalize;

    //cultures
    mt.cultures[EN] = {
        name: EN,
        numberFormat: {
            pattern: ["-n"],
            decimals: 2,
            ",": ",",
            ".": ".",
            groupSize: [3],
            percent: {
                pattern: ["-n %", "n %"],
                decimals: 2,
                ",": ",",
                ".": ".",
                groupSize: [3],
                symbol: "%"
            },
            currency: {
                pattern: ["($n)", "$n"],
                decimals: 2,
                ",": ",",
                ".": ".",
                groupSize: [3],
                symbol: "$"
            }
        },
        calendars: {
            standard: {
                days: {
                    names: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
                    namesAbbr: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
                    namesShort: ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"]
                },
                months: {
                    names: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
                    namesAbbr: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
                },
                AM: ["AM", "am", "AM"],
                PM: ["PM", "pm", "PM"],
                patterns: {
                    d: "M/d/yyyy",
                    D: "dddd, MMMM dd, yyyy",
                    F: "dddd, MMMM dd, yyyy h:mm:ss tt",
                    g: "M/d/yyyy h:mm tt",
                    G: "M/d/yyyy h:mm:ss tt",
                    m: "MMMM dd",
                    M: "MMMM dd",
                    s: "yyyy'-'MM'-'ddTHH':'mm':'ss",
                    t: "h:mm tt",
                    T: "h:mm:ss tt",
                    u: "yyyy'-'MM'-'dd HH':'mm':'ss'Z'",
                    y: "MMMM, yyyy",
                    Y: "MMMM, yyyy"
                },
                "/": "/",
                ":": ":",
                firstDay: 0,
                twoDigitYearMax: 2029
            }
        }
    };
    mt.cultures[CN] = {
        name: CN,
        numberFormat: {
            pattern: ["-n"],
            decimals: 2,
            ",": ",",
            ".": ".",
            groupSize: [3],
            percent: {
                pattern: ["-n %", "n %"],
                decimals: 2,
                ",": ",",
                ".": ".",
                groupSize: [3],
                symbol: "%"
            },
            currency: {
                pattern: ["($n)", "$n"],
                decimals: 2,
                ",": ",",
                ".": ".",
                groupSize: [3],
                symbol: "￥"
            }
        },
        calendars: {
            standard: {
                days: {
                    names: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
                    namesAbbr: ["日", "一", "二", "三", "四", "五", "六"],
                    namesShort: ["日", "一", "二", "三", "四", "五", "六"]
                },
                months: {
                    names: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                    namesAbbr: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"]
                },
                AM: ["AM", "am", "AM"],
                PM: ["PM", "pm", "PM"],
                patterns: {
                    d: "M/d/yyyy",
                    D: "dddd, MMMM dd, yyyy",
                    F: "dddd, MMMM dd, yyyy h:mm:ss tt",
                    g: "M/d/yyyy h:mm tt",
                    G: "M/d/yyyy h:mm:ss tt",
                    m: "MMMM dd",
                    M: "MMMM dd",
                    s: "yyyy'-'MM'-'ddTHH':'mm':'ss",
                    t: "h:mm tt",
                    T: "h:mm:ss tt",
                    u: "yyyy'-'MM'-'dd HH':'mm':'ss'Z'",
                    y: "MMMM, yyyy",
                    Y: "MMMM, yyyy"
                },
                "/": "/",
                ":": ":",
                firstDay: 0,
                twoDigitYearMax: 2029
            }
        }
    };
    //补全0函数
    function pad(number, digits, end) {
        number = number + "";
        digits = digits || 2;
        end = digits - number.length;
        if (end) {
            return zeros[digits].substring(0, end) + number;
        }
        return number;
    }

    function findCulture(culture) {
        if (culture) {
            if (culture.numberFormat) {
                return culture;
            }

            if (typeof culture === STRING) {
                var cultures = mt.cultures;
                return cultures[culture] || cultures[culture.split("-")[0]] || null;
            }

            return null;
        }

        return null;
    }

    function getCulture(culture) {
        if (culture) {
            culture = findCulture(culture);
        }

        return culture || mt.cultures.current;
    }

    function expandNumberFormat(numberFormat) {
        numberFormat.groupSizes = numberFormat.groupSize;
        numberFormat.percent.groupSizes = numberFormat.percent.groupSize;
        numberFormat.currency.groupSizes = numberFormat.currency.groupSize;
    }

    mt.culture = function(cultureName) {
        var cultures = mt.cultures,
            culture;

        if (cultureName !== undefined) {
            culture = findCulture(cultureName) || cultures[EN];
            culture.calendar = culture.calendars.standard;
            cultures.current = culture;

            if (globalize && !globalize.load) {
                expandNumberFormat(culture.numberFormat);
            }

        } else {
            return cultures.current;
        }
    };

    mt.findCulture = findCulture;
    mt.getCulture = getCulture;

    //设置默认为中文显示
    mt.culture(CN);

    function formatDate(date, format, culture) {
        culture = getCulture(culture);

        var calendar = culture.calendars.standard,
            days = calendar.days,
            months = calendar.months;

        format = calendar.patterns[format] || format;

        return format.replace(dateFormatRegExp, function(match) {
            var minutes;
            var result;
            var sign;

            if (match === "d") {
                result = date.getDate();
            } else if (match === "dd") {
                result = pad(date.getDate());
            } else if (match === "ddd") {
                result = days.namesAbbr[date.getDay()];
            } else if (match === "dddd") {
                result = days.names[date.getDay()];
            } else if (match === "M") {
                result = date.getMonth() + 1;
            } else if (match === "MM") {
                result = pad(date.getMonth() + 1);
            } else if (match === "MMM") {
                result = months.namesAbbr[date.getMonth()];
            } else if (match === "MMMM") {
                result = months.names[date.getMonth()];
            } else if (match === "yy") {
                result = pad(date.getFullYear() % 100);
            } else if (match === "yyyy") {
                result = pad(date.getFullYear(), 4);
            } else if (match === "h") {
                result = date.getHours() % 12 || 12;
            } else if (match === "hh") {
                result = pad(date.getHours() % 12 || 12);
            } else if (match === "H") {
                result = date.getHours();
            } else if (match === "HH") {
                result = pad(date.getHours());
            } else if (match === "m") {
                result = date.getMinutes();
            } else if (match === "mm") {
                result = pad(date.getMinutes());
            } else if (match === "s") {
                result = date.getSeconds();
            } else if (match === "ss") {
                result = pad(date.getSeconds());
            } else if (match === "f") {
                result = math.floor(date.getMilliseconds() / 100);
            } else if (match === "ff") {
                result = date.getMilliseconds();
                if (result > 99) {
                    result = math.floor(result / 10);
                }
                result = pad(result);
            } else if (match === "fff") {
                result = pad(date.getMilliseconds(), 3);
            } else if (match === "tt") {
                result = date.getHours() < 12 ? calendar.AM[0] : calendar.PM[0];
            } else if (match === "zzz") {
                minutes = date.getTimezoneOffset();
                sign = minutes < 0;

                result = math.abs(minutes / 60).toString().split(".")[0];
                minutes = math.abs(minutes) - (result * 60);

                result = (sign ? "+" : "-") + pad(result);
                result += ":" + pad(minutes);
            } else if (match === "zz" || match === "z") {
                result = date.getTimezoneOffset() / 60;
                sign = result < 0;

                result = math.abs(result).toString().split(".")[0];
                result = (sign ? "+" : "-") + (match === "zz" ? pad(result) : result);
            }

            return result !== undefined ? result : match.slice(1, match.length - 1);
        });
    }

    //数值格式化
    function formatNumber(number, format, culture) {
        culture = getCulture(culture);

        var numberFormat = culture.numberFormat,
            groupSize = numberFormat.groupSize[0],
            groupSeparator = numberFormat[COMMA],
            decimal = numberFormat[POINT],
            precision = numberFormat.decimals,
            pattern = numberFormat.pattern[0],
            literals = [],
            symbol,
            isCurrency, isPercent,
            customPrecision,
            formatAndPrecision,
            negative = number < 0,
            integer,
            fraction,
            integerLength,
            fractionLength,
            replacement = EMPTY,
            value = EMPTY,
            idx,
            length,
            ch,
            hasGroup,
            hasNegativeFormat,
            decimalIndex,
            sharpIndex,
            zeroIndex,
            hasZero, hasSharp,
            percentIndex,
            currencyIndex,
            startZeroIndex,
            start = -1,
            end;

        //return empty string if no number
        if (number === undefined) {
            return EMPTY;
        }

        if (!isFinite(number)) {
            return number;
        }

        //if no format then return number.toString() or number.toLocaleString() if culture.name is not defined
        if (!format) {
            return culture.name.length ? number.toLocaleString() : number.toString();
        }

        formatAndPrecision = standardFormatRegExp.exec(format);

        // standard formatting
        if (formatAndPrecision) {
            format = formatAndPrecision[1].toLowerCase();

            isCurrency = format === "c";
            isPercent = format === "p";

            if (isCurrency || isPercent) {
                //get specific number format information if format is currency or percent
                numberFormat = isCurrency ? numberFormat.currency : numberFormat.percent;
                groupSize = numberFormat.groupSize[0];
                groupSeparator = numberFormat[COMMA];
                decimal = numberFormat[POINT];
                precision = numberFormat.decimals;
                symbol = numberFormat.symbol;
                pattern = numberFormat.pattern[negative ? 0 : 1];
            }

            customPrecision = formatAndPrecision[2];

            if (customPrecision) {
                precision = +customPrecision;
            }

            //return number in exponential format
            if (format === "e") {
                return customPrecision ? number.toExponential(precision) : number.toExponential(); // toExponential() and toExponential(undefined) differ in FF #653438.
            }

            // multiply if format is percent
            if (isPercent) {
                number *= 100;
            }

            number = round(number, precision);
            negative = number < 0;
            number = number.split(POINT);

            integer = number[0];
            fraction = number[1];

            //exclude "-" if number is negative.
            if (negative) {
                integer = integer.substring(1);
            }

            value = integer;
            integerLength = integer.length;

            //add group separator to the number if it is longer enough
            if (integerLength >= groupSize) {
                value = EMPTY;
                for (idx = 0; idx < integerLength; idx++) {
                    if (idx > 0 && (integerLength - idx) % groupSize === 0) {
                        value += groupSeparator;
                    }
                    value += integer.charAt(idx);
                }
            }

            if (fraction) {
                value += decimal + fraction;
            }

            if (format === "n" && !negative) {
                return value;
            }

            number = EMPTY;

            for (idx = 0, length = pattern.length; idx < length; idx++) {
                ch = pattern.charAt(idx);

                if (ch === "n") {
                    number += value;
                } else if (ch === "$" || ch === "%") {
                    number += symbol;
                } else {
                    number += ch;
                }
            }

            return number;
        }

        //custom formatting
        //
        //separate format by sections.

        //make number positive
        if (negative) {
            number = -number;
        }

        if (format.indexOf("'") > -1 || format.indexOf("\"") > -1 || format.indexOf("\\") > -1) {
            format = format.replace(literalRegExp, function(match) {
                var quoteChar = match.charAt(0).replace("\\", ""),
                    literal = match.slice(1).replace(quoteChar, "");

                literals.push(literal);

                return PLACEHOLDER;
            });
        }

        format = format.split(";");
        if (negative && format[1]) {
            //get negative format
            format = format[1];
            hasNegativeFormat = true;
        } else if (number === 0) {
            //format for zeros
            format = format[2] || format[0];
            if (format.indexOf(SHARP) == -1 && format.indexOf(ZERO) == -1) {
                //return format if it is string constant.
                return format;
            }
        } else {
            format = format[0];
        }

        percentIndex = format.indexOf("%");
        currencyIndex = format.indexOf("$");

        isPercent = percentIndex != -1;
        isCurrency = currencyIndex != -1;

        //multiply number if the format has percent
        if (isPercent) {
            number *= 100;
        }

        if (isCurrency && format[currencyIndex - 1] === "\\") {
            format = format.split("\\").join("");
            isCurrency = false;
        }

        if (isCurrency || isPercent) {
            //get specific number format information if format is currency or percent
            numberFormat = isCurrency ? numberFormat.currency : numberFormat.percent;
            groupSize = numberFormat.groupSize[0];
            groupSeparator = numberFormat[COMMA];
            decimal = numberFormat[POINT];
            precision = numberFormat.decimals;
            symbol = numberFormat.symbol;
        }

        hasGroup = format.indexOf(COMMA) > -1;
        if (hasGroup) {
            format = format.replace(commaRegExp, EMPTY);
        }

        decimalIndex = format.indexOf(POINT);
        length = format.length;

        if (decimalIndex != -1) {
            fraction = number.toString().split("e");
            if (fraction[1]) {
                fraction = round(number, Math.abs(fraction[1]));
            } else {
                fraction = fraction[0];
            }
            fraction = fraction.split(POINT)[1] || EMPTY;
            zeroIndex = format.lastIndexOf(ZERO) - decimalIndex;
            sharpIndex = format.lastIndexOf(SHARP) - decimalIndex;
            hasZero = zeroIndex > -1;
            hasSharp = sharpIndex > -1;
            idx = fraction.length;

            if (!hasZero && !hasSharp) {
                format = format.substring(0, decimalIndex) + format.substring(decimalIndex + 1);
                length = format.length;
                decimalIndex = -1;
                idx = 0;
            }
            if (hasZero && zeroIndex > sharpIndex) {
                idx = zeroIndex;
            } else if (sharpIndex > zeroIndex) {
                if (hasSharp && idx > sharpIndex) {
                    idx = sharpIndex;
                } else if (hasZero && idx < zeroIndex) {
                    idx = zeroIndex;
                }
            }

            if (idx > -1) {
                number = round(number, idx);
            }
        } else {
            number = round(number);
        }

        sharpIndex = format.indexOf(SHARP);
        startZeroIndex = zeroIndex = format.indexOf(ZERO);

        //define the index of the first digit placeholder
        if (sharpIndex == -1 && zeroIndex != -1) {
            start = zeroIndex;
        } else if (sharpIndex != -1 && zeroIndex == -1) {
            start = sharpIndex;
        } else {
            start = sharpIndex > zeroIndex ? zeroIndex : sharpIndex;
        }

        sharpIndex = format.lastIndexOf(SHARP);
        zeroIndex = format.lastIndexOf(ZERO);

        //define the index of the last digit placeholder
        if (sharpIndex == -1 && zeroIndex != -1) {
            end = zeroIndex;
        } else if (sharpIndex != -1 && zeroIndex == -1) {
            end = sharpIndex;
        } else {
            end = sharpIndex > zeroIndex ? sharpIndex : zeroIndex;
        }

        if (start == length) {
            end = start;
        }

        if (start != -1) {
            value = number.toString().split(POINT);
            integer = value[0];
            fraction = value[1] || EMPTY;

            integerLength = integer.length;
            fractionLength = fraction.length;

            if (negative && (number * -1) >= 0) {
                negative = false;
            }

            //add group separator to the number if it is longer enough
            if (hasGroup) {
                if (integerLength === groupSize && integerLength < decimalIndex - startZeroIndex) {
                    integer = groupSeparator + integer;
                } else if (integerLength > groupSize) {
                    value = EMPTY;
                    for (idx = 0; idx < integerLength; idx++) {
                        if (idx > 0 && (integerLength - idx) % groupSize === 0) {
                            value += groupSeparator;
                        }
                        value += integer.charAt(idx);
                    }

                    integer = value;
                }
            }

            number = format.substring(0, start);

            if (negative && !hasNegativeFormat) {
                number += "-";
            }

            for (idx = start; idx < length; idx++) {
                ch = format.charAt(idx);

                if (decimalIndex == -1) {
                    if (end - idx < integerLength) {
                        number += integer;
                        break;
                    }
                } else {
                    if (zeroIndex != -1 && zeroIndex < idx) {
                        replacement = EMPTY;
                    }

                    if ((decimalIndex - idx) <= integerLength && decimalIndex - idx > -1) {
                        number += integer;
                        idx = decimalIndex;
                    }

                    if (decimalIndex === idx) {
                        number += (fraction ? decimal : EMPTY) + fraction;
                        idx += end - decimalIndex + 1;
                        continue;
                    }
                }

                if (ch === ZERO) {
                    number += ch;
                    replacement = ch;
                } else if (ch === SHARP) {
                    number += replacement;
                }
            }

            if (end >= start) {
                number += format.substring(end + 1);
            }

            //replace symbol placeholders
            if (isCurrency || isPercent) {
                value = EMPTY;
                for (idx = 0, length = number.length; idx < length; idx++) {
                    ch = number.charAt(idx);
                    value += (ch === "$" || ch === "%") ? symbol : ch;
                }
                number = value;
            }

            length = literals.length;

            if (length) {
                for (idx = 0; idx < length; idx++) {
                    number = number.replace(PLACEHOLDER, literals[idx]);
                }
            }
        }

        return number;
    }

    var round = function(value, precision) {
        precision = precision || 0;

        value = value.toString().split('e');
        value = Math.round(+(value[0] + 'e' + (value[1] ? (+value[1] + precision) : precision)));

        value = value.toString().split('e');
        value = +(value[0] + 'e' + (value[1] ? (+value[1] - precision) : -precision));

        return value.toFixed(precision);
    };

    var toString = function(value, fmt, culture) {
        if (fmt) {
            if (objectToString.call(value) === "[object Date]") {
                return formatDate(value, fmt, culture);
            } else if (typeof value === NUMBER) {
                return formatNumber(value, fmt, culture);
            }
        }

        return value !== undefined ? value : "";
    };

    if (globalize && !globalize.load) {
        toString = function(value, format, culture) {
            if ($.isPlainObject(culture)) {
                culture = culture.name;
            }

            return globalize.format(value, format, culture);
        };
    }

    mt.format = function(fmt) {
        var values = arguments;

        return fmt.replace(formatRegExp, function(match, index, placeholderFormat) {
            var value = values[parseInt(index, 10) + 1];

            return toString(value, placeholderFormat ? placeholderFormat.substring(1) : "");
        });
    };

    mt._extractFormat = function(format) {
        if (format.slice(0, 3) === "{0:") {
            format = format.slice(3, format.length - 1);
        }

        return format;
    };

    mt._activeElement = function() {
        try {
            return document.activeElement;
        } catch (e) {
            return document.documentElement.activeElement;
        }
    };

    mt._round = round;
    mt.toString = toString;
    //})();

    //(function() {
    var nonBreakingSpaceRegExp = /\u00A0/g,
        exponentRegExp = /[eE][\-+]?[0-9]+/,
        shortTimeZoneRegExp = /[+|\-]\d{1,2}/,
        longTimeZoneRegExp = /[+|\-]\d{1,2}:?\d{2}/,
        dateRegExp = /^\/Date\((.*?)\)\/$/,
        offsetRegExp = /[+-]\d*/,
        formatsSequence = ["G", "g", "d", "F", "D", "y", "m", "T", "t"],
        numberRegExp = {
            2: /^\d{1,2}/,
            3: /^\d{1,3}/,
            4: /^\d{4}/
        },
        objectToString = {}.toString;

    function outOfRange(value, start, end) {
        return !(value >= start && value <= end);
    }

    function designatorPredicate(designator) {
        return designator.charAt(0);
    }

    function mapDesignators(designators) {
        return $.map(designators, designatorPredicate);
    }

    //if date's day is different than the typed one - adjust
    function adjustDST(date, hours) {
        if (!hours && date.getHours() === 23) {
            date.setHours(date.getHours() + 2);
        }
    }

    function lowerArray(data) {
        var idx = 0,
            length = data.length,
            array = [];

        for (; idx < length; idx++) {
            array[idx] = (data[idx] + "").toLowerCase();
        }

        return array;
    }

    function lowerLocalInfo(localInfo) {
        var newLocalInfo = {},
            property;

        for (property in localInfo) {
            newLocalInfo[property] = lowerArray(localInfo[property]);
        }

        return newLocalInfo;
    }

    function parseExact(value, format, culture) {
        if (!value) {
            return null;
        }

        var lookAhead = function(match) {
                var i = 0;
                while (format[idx] === match) {
                    i++;
                    idx++;
                }
                if (i > 0) {
                    idx -= 1;
                }
                return i;
            },
            getNumber = function(size) {
                var rg = numberRegExp[size] || new RegExp('^\\d{1,' + size + '}'),
                    match = value.substr(valueIdx, size).match(rg);

                if (match) {
                    match = match[0];
                    valueIdx += match.length;
                    return parseInt(match, 10);
                }
                return null;
            },
            getIndexByName = function(names, lower) {
                var i = 0,
                    length = names.length,
                    name, nameLength,
                    subValue;

                for (; i < length; i++) {
                    name = names[i];
                    nameLength = name.length;
                    subValue = value.substr(valueIdx, nameLength);

                    if (lower) {
                        subValue = subValue.toLowerCase();
                    }

                    if (subValue == name) {
                        valueIdx += nameLength;
                        return i + 1;
                    }
                }
                return null;
            },
            checkLiteral = function() {
                var result = false;
                if (value.charAt(valueIdx) === format[idx]) {
                    valueIdx++;
                    result = true;
                }
                return result;
            },
            calendar = culture.calendars.standard,
            year = null,
            month = null,
            day = null,
            hours = null,
            minutes = null,
            seconds = null,
            milliseconds = null,
            idx = 0,
            valueIdx = 0,
            literal = false,
            date = new Date(),
            twoDigitYearMax = calendar.twoDigitYearMax || 2029,
            defaultYear = date.getFullYear(),
            ch, count, length, pattern,
            pmHour, UTC, matches,
            amDesignators, pmDesignators,
            hoursOffset, minutesOffset,
            hasTime, match;

        if (!format) {
            format = "d"; //shord date format
        }

        //if format is part of the patterns get real format
        pattern = calendar.patterns[format];
        if (pattern) {
            format = pattern;
        }

        format = format.split("");
        length = format.length;

        for (; idx < length; idx++) {
            ch = format[idx];

            if (literal) {
                if (ch === "'") {
                    literal = false;
                } else {
                    checkLiteral();
                }
            } else {
                if (ch === "d") {
                    count = lookAhead("d");
                    if (!calendar._lowerDays) {
                        calendar._lowerDays = lowerLocalInfo(calendar.days);
                    }

                    if (day !== null && count > 2) {
                        continue;
                    }

                    day = count < 3 ? getNumber(2) : getIndexByName(calendar._lowerDays[count == 3 ? "namesAbbr" : "names"], true);

                    if (day === null || outOfRange(day, 1, 31)) {
                        return null;
                    }
                } else if (ch === "M") {
                    count = lookAhead("M");
                    if (!calendar._lowerMonths) {
                        calendar._lowerMonths = lowerLocalInfo(calendar.months);
                    }
                    month = count < 3 ? getNumber(2) : getIndexByName(calendar._lowerMonths[count == 3 ? 'namesAbbr' : 'names'], true);

                    if (month === null || outOfRange(month, 1, 12)) {
                        return null;
                    }
                    month -= 1; //because month is zero based
                } else if (ch === "y") {
                    count = lookAhead("y");
                    year = getNumber(count);

                    if (year === null) {
                        return null;
                    }

                    if (count == 2) {
                        if (typeof twoDigitYearMax === STRING) {
                            twoDigitYearMax = defaultYear + parseInt(twoDigitYearMax, 10);
                        }

                        year = (defaultYear - defaultYear % 100) + year;
                        if (year > twoDigitYearMax) {
                            year -= 100;
                        }
                    }
                } else if (ch === "h") {
                    lookAhead("h");
                    hours = getNumber(2);
                    if (hours == 12) {
                        hours = 0;
                    }
                    if (hours === null || outOfRange(hours, 0, 11)) {
                        return null;
                    }
                } else if (ch === "H") {
                    lookAhead("H");
                    hours = getNumber(2);
                    if (hours === null || outOfRange(hours, 0, 23)) {
                        return null;
                    }
                } else if (ch === "m") {
                    lookAhead("m");
                    minutes = getNumber(2);
                    if (minutes === null || outOfRange(minutes, 0, 59)) {
                        return null;
                    }
                } else if (ch === "s") {
                    lookAhead("s");
                    seconds = getNumber(2);
                    if (seconds === null || outOfRange(seconds, 0, 59)) {
                        return null;
                    }
                } else if (ch === "f") {
                    count = lookAhead("f");

                    match = value.substr(valueIdx, count).match(numberRegExp[3]);
                    milliseconds = getNumber(count);

                    if (milliseconds !== null) {
                        match = match[0].length;

                        if (match < 3) {
                            milliseconds *= Math.pow(10, (3 - match));
                        }

                        if (count > 3) {
                            milliseconds = parseInt(milliseconds.toString().substring(0, 3), 10);
                        }
                    }

                    if (milliseconds === null || outOfRange(milliseconds, 0, 999)) {
                        return null;
                    }

                } else if (ch === "t") {
                    count = lookAhead("t");
                    amDesignators = calendar.AM;
                    pmDesignators = calendar.PM;

                    if (count === 1) {
                        amDesignators = mapDesignators(amDesignators);
                        pmDesignators = mapDesignators(pmDesignators);
                    }

                    pmHour = getIndexByName(pmDesignators);
                    if (!pmHour && !getIndexByName(amDesignators)) {
                        return null;
                    }
                } else if (ch === "z") {
                    UTC = true;
                    count = lookAhead("z");

                    if (value.substr(valueIdx, 1) === "Z") {
                        checkLiteral();
                        continue;
                    }

                    matches = value.substr(valueIdx, 6)
                        .match(count > 2 ? longTimeZoneRegExp : shortTimeZoneRegExp);

                    if (!matches) {
                        return null;
                    }

                    matches = matches[0].split(":");

                    hoursOffset = matches[0];
                    minutesOffset = matches[1];

                    if (!minutesOffset && hoursOffset.length > 3) { //(+|-)[hh][mm] format is used
                        valueIdx = hoursOffset.length - 2;
                        minutesOffset = hoursOffset.substring(valueIdx);
                        hoursOffset = hoursOffset.substring(0, valueIdx);
                    }

                    hoursOffset = parseInt(hoursOffset, 10);
                    if (outOfRange(hoursOffset, -12, 13)) {
                        return null;
                    }

                    if (count > 2) {
                        minutesOffset = parseInt(minutesOffset, 10);
                        if (isNaN(minutesOffset) || outOfRange(minutesOffset, 0, 59)) {
                            return null;
                        }
                    }
                } else if (ch === "'") {
                    literal = true;
                    checkLiteral();
                } else if (!checkLiteral()) {
                    return null;
                }
            }
        }

        hasTime = hours !== null || minutes !== null || seconds || null;

        if (year === null && month === null && day === null && hasTime) {
            year = defaultYear;
            month = date.getMonth();
            day = date.getDate();
        } else {
            if (year === null) {
                year = defaultYear;
            }

            if (day === null) {
                day = 1;
            }
        }

        if (pmHour && hours < 12) {
            hours += 12;
        }

        if (UTC) {
            if (hoursOffset) {
                hours += -hoursOffset;
            }

            if (minutesOffset) {
                minutes += -minutesOffset;
            }

            value = new Date(Date.UTC(year, month, day, hours, minutes, seconds, milliseconds));
        } else {
            value = new Date(year, month, day, hours, minutes, seconds, milliseconds);
            adjustDST(value, hours);
        }

        if (year < 100) {
            value.setFullYear(year);
        }

        if (value.getDate() !== day && UTC === undefined) {
            return null;
        }

        return value;
    }

    function parseMicrosoftFormatOffset(offset) {
        var sign = offset.substr(0, 1) === "-" ? -1 : 1;

        offset = offset.substring(1);
        offset = (parseInt(offset.substr(0, 2), 10) * 60) + parseInt(offset.substring(2), 10);

        return sign * offset;
    }

    mt.parseDate = function(value, formats, culture) {
        if (objectToString.call(value) === "[object Date]") {
            return value;
        }

        if (new Date(value) != "Invalid Date" && !formats && !culture) {
            return new Date(value);
        }

        var idx = 0;
        var date = null;
        var length, patterns;
        var tzoffset;
        var sign;

        if (value && value.indexOf("/D") === 0) {
            date = dateRegExp.exec(value);
            if (date) {
                date = date[1];
                tzoffset = offsetRegExp.exec(date.substring(1));

                date = new Date(parseInt(date, 10));

                if (tzoffset) {
                    tzoffset = parseMicrosoftFormatOffset(tzoffset[0]);
                    date = mt.timezone.apply(date, 0);
                    date = mt.timezone.convert(date, 0, -1 * tzoffset);
                }

                return date;
            }
        }

        culture = mt.getCulture(culture);

        if (!formats) {
            formats = [];
            patterns = culture.calendar.patterns;
            length = formatsSequence.length;

            for (; idx < length; idx++) {
                formats[idx] = patterns[formatsSequence[idx]];
            }

            idx = 0;

            formats = [
                "yyyy/MM/dd HH:mm:ss",
                "yyyy/MM/dd HH:mm",
                "yyyy/MM/dd",
                "ddd MMM dd yyyy HH:mm:ss",
                "yyyy-MM-ddTHH:mm:ss.fffffffzzz",
                "yyyy-MM-ddTHH:mm:ss.fffzzz",
                "yyyy-MM-ddTHH:mm:sszzz",
                "yyyy-MM-ddTHH:mm:ss.fffffff",
                "yyyy-MM-ddTHH:mm:ss.fff",
                "yyyy-MM-ddTHH:mmzzz",
                "yyyy-MM-ddTHH:mmzz",
                "yyyy-MM-ddTHH:mm:ss",
                "yyyy-MM-ddTHH:mm",
                "yyyy-MM-dd HH:mm:ss",
                "yyyy-MM-dd HH:mm",
                "yyyy-MM-dd",
                "HH:mm:ss",
                "HH:mm"
            ].concat(formats);
        }

        formats = $.isArray(formats) ? formats : [formats];
        length = formats.length;

        for (; idx < length; idx++) {
            date = parseExact(value, formats[idx], culture);
            if (date) {
                return date;
            }
        }

        return date;
    };

    mt.parseInt = function(value, culture) {
        var result = mt.parseFloat(value, culture);
        if (result) {
            result = result | 0;
        }
        return result;
    };

    mt.parseFloat = function(value, culture, format) {
        if (!value && value !== 0) {
            return null;
        }

        if (typeof value === NUMBER) {
            return value;
        }

        value = value.toString();
        culture = mt.getCulture(culture);

        var number = culture.numberFormat,
            percent = number.percent,
            currency = number.currency,
            symbol = currency.symbol,
            percentSymbol = percent.symbol,
            negative = value.indexOf("-"),
            parts, isPercent;

        //handle exponential number
        if (exponentRegExp.test(value)) {
            value = parseFloat(value.replace(number["."], "."));
            if (isNaN(value)) {
                value = null;
            }
            return value;
        }

        if (negative > 0) {
            return null;
        } else {
            negative = negative > -1;
        }

        if (value.indexOf(symbol) > -1 || (format && format.toLowerCase().indexOf("c") > -1)) {
            number = currency;
            parts = number.pattern[0].replace("$", symbol).split("n");
            if (value.indexOf(parts[0]) > -1 && value.indexOf(parts[1]) > -1) {
                value = value.replace(parts[0], "").replace(parts[1], "");
                negative = true;
            }
        } else if (value.indexOf(percentSymbol) > -1) {
            isPercent = true;
            number = percent;
            symbol = percentSymbol;
        }

        value = value.replace("-", "")
            .replace(symbol, "")
            .replace(nonBreakingSpaceRegExp, " ")
            .split(number[","].replace(nonBreakingSpaceRegExp, " ")).join("")
            .replace(number["."], ".");

        value = parseFloat(value);

        if (isNaN(value)) {
            value = null;
        } else if (negative) {
            value *= -1;
        }

        if (value && isPercent) {
            value /= 100;
        }

        return value;
    };

    if (globalize && !globalize.load) {
        mt.parseDate = function(value, format, culture) {
            if (objectToString.call(value) === "[object Date]") {
                return value;
            }

            return globalize.parseDate(value, format, culture);
        };

        mt.parseFloat = function(value, culture) {
            if (typeof value === NUMBER) {
                return value;
            }

            if (value === undefined || value === null) {
                return null;
            }

            if ($.isPlainObject(culture)) {
                culture = culture.name;
            }

            value = globalize.parseFloat(value, culture);

            return isNaN(value) ? null : value;
        };
    }
    window.hmtdUtils = mt;
    return mt;
})();/**
 *  kendo 扩展方法
 */
(function($) {
	//选卡隐藏事件
	$.fn.kendoTabstripHidden = function() {
		if (this.is(":hidden")) {
			this.closest(".k-tabstrip-wrapper").hide();
		}
	};
	//grid 单击事件
	$.fn.kendoGridClick = function(id, linkType, rules, params) {
		$("#" + id).data("isdblclick", false);
		setTimeout(function() {
			if ($("#" + id).data("isdblclick")) {
				return;
			}
			pubsub.pub(linkType, rules, params);
		}, 500)
	};
	//grid 双击事件
	$.fn.kendoGridDblClickByType = function(id, linkType, rules, params) {
		$("#" + id + " tbody").delegate("tr", "dblclick", function(e) {
			$("#" + id).data("isdblclick", true);
			pubsub.pub(linkType, rules, params);
		});
	}
	//双击事件 by 事件定义器
	$.fn.kendoGridDblClick = function(id, ex) {
		$("#" + id + " tbody").delegate("tr", "dblclick", function(e) {
			$("#" + id).data("isdblclick", true);
			pubsub.pub(ex, "", this);
		});
	}
})(jQuery);var MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver;
var jqCb = $.Callbacks(),
	mapCb = $.Callbacks();
//eg: pubsub.pub("linkageControl",datas);
/**
 *从页面级参数中获取
 */
function getParam(id, param) {
	if (pageParameters) {
		var paramObj = eval('(' + pageParameters + ')'),
			detail = paramObj['detail'],
			data, obj, v;
		if (detail && (data = detail['data'])) {
			obj = data[id];
			if (obj && obj.length) {
				for (var i = 0, length = obj.length; i < length; i++) {
					v = obj[i];
					if (v.param == param) {
						//return detail[v.inparam];
						return v.inparam?detail[v.inparam]:v.value ;
					}
				}
			}
		}
	}
	return null;
}

function getParams(cid) {
	if (pageParameters) {
		var paramObj = eval('(' + pageParameters + ')'),
			detail = paramObj['detail'],
			data, obj, v, retVal = {};
		if (detail && (data = detail['data'])) {
			if (cid) {
				obj = data[cid];
				if (obj && obj.length) {
					for (var i = 0, length = obj.length; i < length; i++) {
						v = obj[i];
						//retVal[v.param] = detail[v.inparam];
						retVal[v.param] = v.inparam?detail[v.inparam]:v.value;
					}
				}
			} else {
				for (var id in data) {
					obj = data[id];
					if (obj && obj.length) {
						for (var i = 0, length = obj.length; i < length; i++) {
							v = obj[i];
							// 增加 如果是数据集中转发的参数 则从数据集转发中获取
							if(v.type == "__defaultValue__"){
								retVal[v.param] = v.columnName;
							}else{
								retVal[v.param] = v.inparam?detail[v.inparam]:v.value;
							}
							
						}
					}
				}
			}
			return retVal;
		}
	}
	return null;
}
var pubsub = (function() {
	var q = {
			elongateStockIndex: null
		},
		roles = {},
		subUid = -1;
	q.execChilds = function(rule) {
		var childs = rule.childs;
		childs && pubsub._execOps(childs, rule.trigger, null, null);
	};
	q.getThat = function(rs, isIn) {
		var that = window,
			tlevel;
		if (typeof rs == "object") {
			var rules = [];
			if ($.isArray(rs)) {
				rules = rs;
				tlevel = rs.tlevel;
			} else {
				rules.push(rs);
			}
			var rule = rules[0],
				lev = isIn ? rule.inlev : rule.outlev,
				pageId = isIn ? rule.inpage : rule.outpage;
			if (lev) {
				// 输入or输出控件层数 - 触发事件控件层数
				var t = lev - (tlevel || rule.tlevel || pageLevel);
				if (t > 0) { // 往下找
					that = q.getNextLevel(that, t, pageId, isIn);
					if (that)
						return that;
					else
						return null;
				} else if (t < 0) { // 往上找
					that = q.getPrevLevel(that, -t);
					return that;
				} else { // 处在同级
					var pageParams = JSON.parse(pageParameters),
						thisPageId = pageParams.id;
					if (pageId == thisPageId) { // 如果在同一页面内
						return that;
					} else { // 处在同级 但是在不同页面

					}
				}
			} else {
				return that;
			}
		} else {
			return that;
		}
	};
	q.getPrevLevel = function(that, t) {
		for (var i = 0; i < t; i++) {
			that = that.parent;
		}
		return that;
	};
	q.getNextLevel = function(that, t, pageId, container) {
		var iframes, iframe, ct = (typeof container === "boolean") ? "body" : container;
		if (t == 1) {
			iframe = that.$('iframe[src*="' + pageId + '"]', ct);
		} else {
			iframes = that.$('iframe', ct);
			var iframeContents;
			iframeContents = iframes.contents();
			while (t > 2) {
				t--;
				iframeContents = iframeContents.contents();
			}
			iframe = iframeContents.find('iframe[src*="' + pageId + '"]');
		}
		if (iframe && iframe[0]) {
			that = iframe[0].contentWindow;
			return that;
		}
		// return null ;
	};
	q.getJQObj = function(rs, isIn) {
		var $ret = null;
		if (typeof rs == "object") {
			var rules = [];
			if ($.isArray(rs)) {
				rules = rs;
			} else {
				rules.push(rs);
			}
			var rule = rules[0],
				id = isIn ? (rule.iid || rule.inid) : (rule.oid || rule.outid);
			var that = q.getThat(rs, isIn);
			if (that) {
				$ret = that.$("#" + id);
			}
		} else {
			$ret = $("#" + rs);
		}
		//计算变长区偏移量
		if ($ret && $ret.attr("columnname") && q.elongateStockIndex) {
			$ret = $("[columnname=" + $ret.attr("columnname") + "]").eq(q.elongateStockIndex);
		}
		return $ret;
	};
	q.getRole = function(id) {
		// 去除数字
		return id.replace(/\d|\_|\-/g, "");
	};
	q.paramsToStr = function(params, exclude) {
		if (exclude) {
			var excludeParams = {};
			for (var p in params) {
				if ($.inArray(p, exclude) == -1) {
					excludeParams[p] = params[p];
				}
			}
			params = excludeParams;
		}
		return "&" + $.param(params);
	};
	q.expConvert = function(exp, params, toEscaping) {
			if (params) {
				var escapingStr = "\"",
					reger, val, str;
				for (var p in params) {
					reger = new RegExp(p, "gm");
					val = params[p];
					str = toEscaping && $.isNumeric(val) ? "" : escapingStr;
					exp = exp.replace(reger, str + params[p] + str);
				}
			}
			return exp.replace(/\\"/gm, "'");
		},
		q.variableLength = function(out, cellExp, outFuncs, cindex, args, tflag) {
			var exp = cellExp.exp.replace(/\{area\}/ig, cindex);
			var obj = eval(exp);
			if (obj == "NaN")
				obj = "";
			var outissplits = out.outid.split("_");
			tflag ? outissplits[2] = cindex : outissplits[1] = cindex;
			out.outid = outissplits.join("_");
			outFuncs[out.eventType](out, obj, args);
		},
		/**
		 * 根据规则获取参数
		 */
		q.getParameters = function(rules) {
			// TODO
			var args = Array.prototype.slice.call(arguments, 1); // 这个东东获取不明确...
			if (rules) {
				var id, ruleArray, params = new Object(),
					that = this;
				for (id in rules) {
					ruleArray = rules[id];
					$.each(ruleArray, function(i, r) {
						// 根据输入控件id获取其所属角色role
						var inRole = (r.inid.length < 31 && r.inid.indexOf("/") == -1) ? that.getRole(r.inid) : 'page';
						if (!roles[inRole]) {
							return false;
						}
						// 根据控件角色获取控件 事件方法
						var inFuncs = roles[inRole][0]['funcs'];
						if (typeof inFuncs[r.type] == 'function') {
							var p = inFuncs[r.type](r, args);
							if (p) {
								params[r.param] = p;
							}
						}
					});
				}
				return params;
			}
			return null;
		};
	q._getInParams = function(expds, tlevel) {
		var eobj = {};
		if (expds && expds.length) {
			for (var kk = 0; kk < expds.length; kk++) {
				var ed = expds[kk];
				ed.tlevel = tlevel;
				// 根据输入控件id获取其所属角色role
				var inRole = (ed.inid.length < 31 && ed.inid.indexOf("/") == -1) ? this.getRole(ed.iid || ed.inid) : 'page';
				if (!roles[inRole]) { // 找不到方法跳过
					continue;
				}
				// 根据控件角色获取控件 事件方法
				var inFuncs = roles[inRole][0]['funcs'];
				if (typeof inFuncs[ed.type] == 'function') {
					try {
						var p = inFuncs[ed.type](ed, []);
						//if (p) {
						eobj[ed.param] = p;
						//}
					} catch (e) {
						console.log(e);
					}
				}
			}
		}
		return eobj;
	}
	q._execOps = function(ops, trigger, rules, args) {
		var tlevel = trigger.level,
			outp, pars, par, expdata, exp, expds, expd, cellExp, childs;
		for (var ii = 0, ol = ops.length, op; ii < ol; ii++) {
			op = ops[ii];
			outp = op['out']; //输出控件
			pars = op['param']; //输入输出关系
			// 增加了表达式验证执行
			expdata = op['expdata']; //表达式
			exp = expdata.exp; // 验证表达式
			expds = expdata.expdata; // 数据属性
			cellExp = op['cellExp']; //cell表达式
			if (expds) {
				var eobj = {};
				for (var kk = 0; kk < expds.length; kk++) {
					var ed = expds[kk];
					ed.tlevel = tlevel;
					// 根据输入控件id获取其所属角色role
					var inRole = (ed.inid.length < 31 && ed.inid.indexOf("/") == -1) ? this.getRole(ed.iid || ed.inid) : 'page';
					if (!roles[inRole]) { // 找不到方法跳过
						continue;
					}
					// 根据控件角色获取控件 事件方法
					var inFuncs = roles[inRole][0]['funcs'];
					if (typeof inFuncs[ed.type] == 'function') {
						try {
							var p = inFuncs[ed.type](ed, args);
							//if (p) {
							eobj[ed.param] = p;
							//}
						} catch (e) {
							console.log(e);
						}
					}
				}
				// 替换表达式 数据
				exp = q.expConvert(exp, eobj);
			}
			// 执行表达式
			if (exp != "") {
				var exps = eval(exp);
				if (!exps) {
					continue;
				}
			}

			// 处理没有输入输出参数的其他事件方法
			for (var jj = 0; jj < outp.length; jj++) {
				var out = outp[jj];
				out.tlevel = tlevel;
				if (!pars || !pars[out.outid]) {
					var outRole = (out.outid.length < 31 && out.outid.indexOf("/") == -1) ? this.getRole(out.oid || out.outid) : 'page';
					var outFuncs = roles[outRole][0]['funcs'];
					if (typeof outFuncs[out.eventType] == 'function') {
						var obj;
						if (cellExp) { // 执行  cell 表达式 
							var cell = op['cell'];
							if (cell) { //变长区
								var tflag = (cell.d == "h");
								var target = mtxx.e.target,
									tid = target.id,
									idIndexs = tid.split("_"); //点击事件
								var index = tflag ? idIndexs[2] : idIndexs[1];
								var cindex = tflag ? cell.c : cell.r;
								if (cindex <= index) { //当前点击是属于变长区内变量
									this.variableLength(out, cellExp, outFuncs, index, args, tflag);
								} else { //当前点击控件不是变长区控件
									//获取out同变长区数量
									var areas = $("[name=" + $('#' + out.outid).attr('name') + "]"),
										areaLength = areas.length,
										area, areaId, areasplits;
									for (var m = 0; m < areaLength; m++) {
										area = areas[m];
										areaId = area.id, areasplits = areaId.split("_");
										cindex = tflag ? areasplits[2] : areasplits[1];
										this.variableLength(out, cellExp, outFuncs, cindex, args, tflag);
										cindex++;
									}
								}
							} else { //非变长cell公式计算
								obj = eval(cellExp.exp);
								outFuncs[out.eventType](out, obj, args);
							}
						} else {
							obj = args;
							try {
								out.childs = op['childs'];
								out.tlevel = tlevel;
								out.trigger = trigger;
								outFuncs[out.eventType](out, obj, args);
							} catch (e) {
								console.log(e);
							}
						}
					}
				}
			}
			var isCallback = false;
			// 执行参数传递工作
			for (var id in pars) {
				par = pars[id];
				var obj = {},
					aryFlag = [],
					expObj = {},
					insExpEval;
				//获取输入参数对象
				for (var i = 0; i < par.length; i++) {
					var r = par[i],
						isExpFunc = false;
					r.tlevel = tlevel;
					if (r.insExp) { //表达式计算参数传递
						var ins = r.ins,
							isExpFunc = true;
						if (ins) {
							var insLength = ins.length,
								rc;
							for (var m = 0; m < insLength; m++) {
								rc = ins[m];
								rc.tlevel = tlevel;
								// 根据输入控件id获取其所属角色role
								var inRole = (rc.inid.length < 31 && rc.inid.indexOf("/") == -1) ? this.getRole(rc.iid || rc.inid) : 'page';
								if (!roles[inRole]) {
									continue;
								}
								// 根据控件角色获取控件 事件方法
								var inFuncs = roles[inRole][0]['funcs'];
								if (typeof inFuncs[rc.type] == 'function') {
									try {
										var p = inFuncs[rc.type](rc, args);
										//if (p) {
										expObj[rc.param] = p;
										//}
									} catch (e) {
										console.log(e);
									}
								}
							}
						}
					} else {
						// 根据输入控件id获取其所属角色role					
						var inRole = (r.inid.length < 31 && r.inid.indexOf("/") == -1) ? this.getRole(r.iid || r.inid) : 'page';

						if (!roles[inRole]) {
							continue;
						}
						// 根据控件角色获取控件 事件方法
						var inFuncs = roles[inRole][0]['funcs'];
						if (typeof inFuncs[r.type] == 'function') {
							try {
								var p = inFuncs[r.type](r, args);
								r.dataId && (obj[r.dataId] || (obj[r.dataId] = {})) && (obj[r.dataId][r.param] = p );
								r.dataId && (r.dataId.indexOf("ary") != -1) && ($.inArray(r.dataId,aryFlag) == -1) && aryFlag.push(r.dataId); 
								if (p) {
									obj[r.param] = p;
								}
							} catch (e) {
								console.log(e);
							}
						}
					}
					if (isExpFunc) { //表达式执行运算 
						insExpEval = q.expConvert(r.insExp, expObj, true);
						if (insExpEval) {
							obj[r.param] = eval(insExpEval);
						}
					}

				}
				//参数传递的数组封装
				if(aryFlag.length){
					var cObj = {};
					$.each(aryFlag,function(i,flag){
						var flagAry = [];
						var flagObj = obj[flag];
						for(var p in flagObj){
							var pary = (flagObj[p]+"").split(",");
							$.each(pary,function(i,v){
								flagAry[i] || (flagAry[i] = {});
								flagAry[i][p] = v ;
							});
						}
						cObj[flag] = flagAry;
					});
					obj = cObj;
				}

				// 输出控件方法
				var outd = par[0],
					outType = outd.eventType,
					outRole = (id.length < 31 && id.indexOf("/") == -1) ? this.getRole(outd.oid || id) : 'page'
				isCallback = outd.callback;
				if (!roles[outRole]) {
					continue;
				}
				par.childs = op['childs'];
				par.trigger = trigger ;
				var outFuncs = roles[outRole][0]['funcs'];
				if (typeof outFuncs[outType] == 'function') {
					try {
						par.tlevel = tlevel;
						outFuncs[outType](par, obj, args);
					} catch (e) {
						console.log(e);
					}
				}
			}
			/*if(!isCallback){
				childs = op['childs'];
				if (childs && childs.length) {
					q._execOps(childs, tlevel, rules, args);
				}
			}*/
		}
	};
	// 执行事件 type 方法类型，rule 规则
	// {"grid":[{"inid":"textbox","outid":"grid","type":"getValue","param":"col1438410245523"},
	//          {"inid":"grid","outid":"grid","type":"getRow","param":"col1438410245523"}]
	//}
	q.pub = function(type, rules) {
		var args = Array.prototype.slice.call(arguments, 2);
		if (typeof type == 'string') { // 事件类型为字符串
			// 根据规则分组输入 输出控件
			for (var id in rules) {
				// 获取规则
				var ruleArray = rules[id];
				// 封装参数
				var obj = {};
				for (var i = 0; i < ruleArray.length; i++) {
					var r = ruleArray[i];
					// 根据输入控件id获取其所属角色role
					var inRole = (r.inid.length < 31 && r.inid.indexOf("/") == -1) ? this.getRole(r.inid) : 'page';
					if (!roles[inRole]) {
						continue;
					}
					// 根据控件角色获取控件 事件方法
					var inFuncs = roles[inRole][0]['funcs'];
					if (typeof inFuncs[r.type] == 'function') {
						try {
							var p = inFuncs[r.type](r, args);
							if (p) {
								obj[r.param] = p;
							}
						} catch (e) {
							console.log(e);
						}
					}
				}
				// 输出控件方法
				var outRole = (id.length < 31 && id.indexOf("/") == -1) ? this.getRole(id) : 'page';
				if (!roles[outRole]) {
					continue;
				}
				var outFuncs = roles[outRole][0]['funcs'];

				if (typeof outFuncs[type] == 'function') {
					// args.rules = ruleArray ;
					try {
						outFuncs[type](id, obj, args);
					} catch (e) {
						console.log(e);
					}
				}
			}
		} else if (typeof type == 'object') { // 事件传递的是对象
			// type.out.   inps = type['in'], //输入控件
			var ops = type['ops'], //输出控件
				trigger = type['trigger'][0], //触发控件
				tlevel = trigger.level, //触发控件层级
				childs = type["childs"]; //子事件
			q._execOps(ops, trigger, rules, args);
			//循环子节点事件
			if (childs && childs.length) {
				$.each(childs, function(i, child) {
					q.pub(child, rules);
				})
			}
		}
		return this;
	};
	// 注册事件 role 角色类型 funcs 角色相关事件
	q.sub = function(role, funcs) {
		var fn = function() {
			for (var p in funcs) {
				this[p] = funcs[p];
			}
		};
		fn.prototype = new baseFunc();
		roles[role] = roles[role] ? roles[role] : [];
		var token = (++subUid).toString();
		roles[role].push({
			token: token,
			funcs: new fn()
		});
		return token;
	};
	return q;
})();

var bindSpreadEvents = function(spread) {
	var sheet = spread.getActiveSheet();
	sheet.bind(GcSpread.Sheets.Events.ValueChanged, function(e, info) {
		var cell = info.sheet.getCell(info.row, info.col),
			cellType = cell.cellType();
		if (cellType instanceof GcSpread.Sheets.ComboBoxCellType || cellType instanceof GcSpread.Sheets.RadioButtonCellType || cellType instanceof GcSpread.Sheets.CheckboxButtonCellType) {
			var $target = $(info.sheet.parent._host),
				eventsRule = $target.data("eventsRule");
			if (eventsRule) {
				//var event = eventsRule[info.row + "-" + info.col];
				var event = eventsRule[dynamicAreaFunc.calculate(info.sheet, info.row + "-" + info.col, true)];
				if (event) {
					event.call();
				}
			}
		}
	});
	spread.bind(GcSpread.Sheets.Events.ButtonClicked, function(e, info) {
		var cell = info.sheet.getCell(info.row, info.col),
			cellType = cell.cellType(),
			watermark = cell.watermark();
		if (watermark) {
			var obj = JSON.parse(watermark);
			if (obj) {
				if (obj.type == "del") { //删除按钮
					dynamicAreaFunc.delFunc.call(cell, obj.area);
				} else if (obj.type == "add") { //新增按钮
					dynamicAreaFunc.addFunc.call(cell, obj.area);
				}
			}
		}
		if (cellType instanceof GcSpread.Sheets.ButtonCellType) {
			var $target = $(info.sheet.parent._host),
				eventsRule = $target.data("eventsRule");
			if (eventsRule) {
				//var event = eventsRule[info.row + "-" + info.col];
				var event = eventsRule[dynamicAreaFunc.calculate(info.sheet, info.row + "-" + info.col, true)];
				if (event) {
					event.call();
				}
			}
		}
	});
}

/**
 *	spread js 绑定验证器
 */
var bindSpreadValidate = function(eleId) {
	if (!CustomerCondition) {
		function CustomerCondition(compareType, expected, formula, trigger) {
			var self = this;
			self.ignoreBlank = false;
			self.conditionType = "CustomerCondition";
			self.compareType = compareType;
			self.expected = expected;
			self.formula = formula;
			self.trigger = trigger;
		};
		CustomerCondition.prototype = new GcSpread.Sheets.CellValueCondition();
		CustomerCondition.prototype.evaluate = function(evaluator, baseRow, baseColumn, actualValue) {
			//debugger;
			var trigger = this.trigger,
				mtValidate = trigger.vali,
				iid = trigger.iid,
				k = $('#' + iid);
			// 执行表达式
			var execObj = pubsub._getInParams(
					mtValidate.execExpData,
					trigger.inlev),
				exec = pubsub.expConvert(mtValidate.execExp || "", execObj);
			if (exec != "") {
				try {
					var execBol = eval(exec);
					if (!execBol)
						return true;
				} catch (e) {
					return false;
				}
			}
			// 验证表达式
			var eObj = pubsub._getInParams(mtValidate.expData, trigger.inlev),
				exp = pubsub.expConvert(mtValidate.exp || "", eObj);
			if (exp != "") {
				k.data("currentCell", trigger);
				try {
					var expBol = eval(exp);
					if (expBol)
						return true;
					else
						return false;
				} catch (e) {
					return false;
				}
			}
			return true;
		};
	}
	var $this = $("#" + eleId),
		spread = $this.data("spread"),
		sheet = spread.getActiveSheet(),
		spreadValidate = $this.data("spreadValidate"),
		inids, trigger, vali, cCondition, validator1;
	spread.highlightInvalidData(true);
	if (spreadValidate) {
		for (var pro in spreadValidate) {
			trigger = spreadValidate[pro];
			vali = trigger.vali;
			inids = pro.split("-");
			cCondition = new CustomerCondition(1, 4, null, trigger);
			validator1 = new GcSpread.Sheets.DefaultDataValidator(cCondition);
			//validator1.inputTitle = vali.tip;
			validator1.inputMessage = vali.tip;
			validator1.ignoreBlank = false;
			validator1.type = GcSpread.Sheets.CriteriaType.Custom;
			sheet.setDataValidator(inids[0], inids[1], validator1);
		}
	}
};

/**
 * 日期格式化
 * @param  
 * @returns 
 */
Date.prototype.format = function(format) {
	var args = {
		"M+": this.getMonth() + 1,
		"d+": this.getDate(),
		"H+": this.getHours(),
		"m+": this.getMinutes(),
		"s+": this.getSeconds(),
		"q+": Math.floor((this.getMonth() + 3) / 3), //quarter 
		"S": this.getMilliseconds()
	};
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
			.substr(4 - RegExp.$1.length));
	for (var i in args) {
		var n = args[i];
		if (new RegExp("(" + i + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));
	}
	return format;
};



function imageUploadInitFunc() {
	// 添加事件
	$('body')
		.delegate(
			'.image_upload_div',
			'change',
			function() {
				var $this = $(this),
					input = $this
					.find(".image_upload_input_file")[0],
					maxfilesize = $this
					.attr("maxfilesize");
				var mypic = input.files[0];
				/* 判断文件大小 */
				if(!mypic)return;
				if (mypic.size > maxfilesize) {
					alert("上传的文件不能超过" + (maxfilesize / (1024 * 1024)) + "M");
					return;
				}
				$.ajaxFileUpload({
					url: $this.attr("saveurl"),
					secureuri: false,
					fileElementId: input.id,
					data: {
						randomparent: $this.attr("randomparent"),
						attachmentId: $this.attr("attachmentId")
					},
					dataType: 'json',
					success: function(data, status) {
						if (data && data.statusCode == 200) {
							alert("上传成功！");
							$this.attr("attachmentId",
								data.attachmentId);
							var $image = $this.find(".imageupload_class");
							// $image[0].onload = function() {
						 //        EXIF.getData($image[0], function() {
						 //            $(this).data("tags",EXIF.getAllTags(this));
						 //        });
						 //    };
							$image.attr(
									"src",
									window.URL.createObjectURL(mypic))
								.show();

							var urlTest = '/glaf/mx/form/imageUpload?method=downloadById&_'+(new Date().getTime())+'&id='+data.attachmentId;

                            var imageTest = new Image();
							imageTest.onload = function() {
								EXIF.getData(this, function(){
									$image.data("tags",EXIF.getAllTags(this));
								});
							};
							imageTest.src = urlTest;

							var outputName = $this.attr("outputName");
							if (outputName) {
								imageuploadFunc.setOutPutName(
									outputName, data.names);
							}
						} else {
							alert('上传失败！');
						}
					},
					error: function(data, status, e) {
						alert('上传失败！Error=' + e);
						console.log('出错 ' + status + '; data=' + JSON.stringify(data) + '; e=' + e);
					}
				});
			});
}/**
 * 按钮
 */
var cellFunc = {
	setValue: function(rule, params, args) {
		var outid = rule[0]["outid"];
		if(outid == rule['oid']){
			var sheet = cellFunc._getActiveSheet(rule),
			doutid = dynamicAreaFunc.calculate(sheet, outid),
			outids = doutid.split("-");
			if (typeof args == "object") {
				for (var i = 0; i < rule.length; i++) {
					r = rule[i];
					v = params[r.param];
				}
				sheet.setValue(outids[0], outids[1], v);
			} else {
				sheet.setValue(outids[0], outids[1], params);
			}
		}else{
			var spread = cellFunc._getSpread(rule);
			for(var key in params){
				spread.fromJSON(JSON.parse(params[key]));	
			}
		}
			
	},
	getValue: function(rule, params, args) {
		var outid = rule["inid"];
		if(outid != rule['iid']){
			var sheet = cellFunc._getActiveSheet(rule, true),
			doutid = dynamicAreaFunc.calculate(sheet, outid),
			outids = doutid.split("-");
			return sheet.getValue(outids[0], outids[1]);	
		}else{
			var spread = cellFunc._getSpread(rule, true);
			return JSON.stringify(spread.toJSON());
		}
		
	},
	prevPage: function(rule, params, args){
		var $jq = pubsub.getJQObj(rule),
			__page__ = $jq.data("__page__") || 1;
		$jq.data("__page__",__page__>1?__page__-1:1);
		pageCell.call($jq.data("spread"));
	},
	nextPage: function(rule, params, args){
		var $jq = pubsub.getJQObj(rule),
			__page__ = $jq.data("__page__") || 1;
		$jq.data("__page__",__page__+1);
		pageCell.call($jq.data("spread"));
	},
	linkAge: function(rule, params, args){
		var $jq = pubsub.getJQObj(rule);
		$jq.data("__params__",params);
		pageCell.call($jq.data("spread"));
	},
	_getSpread : function(rule, isIn){
		var $jq = pubsub.getJQObj(rule, isIn);
		return $jq.data("spread")
	},
	_getActiveSheet: function(rule, isIn) {
		var spread = cellFunc._getSpread(rule,isIn),
			sheet = spread.getActiveSheet();
		return sheet;
	}
};
pubsub.sub("cell", cellFunc);



/**
 * cell 表达式引擎  函数
 */

/*
 * 获取value值
 */
function c_gv(id) {
	return $('#' + id).val();
}
/*
 * 获取name相同的元素
 */
function c_gn(name) {
	var eles = $('[name=' + name + ']');
	var ret;
	for (var i = 0; i < eles.length; i++) {

	}
}
/*
 * if判断函数
 */
function jsif(condition, trueVal, falseVal) {
	return condition ? trueVal : falseVal;
}

/*
 * 
 *  规范表 cell_criterion
 *    -chkformual 检测公式字段
 *    -alltext 偏差范围
 *    -ichecknum_less 最少检测点数
 *    
 * 1、引用规范：SETVARIABLE('textbox_11_15','302')   表示当前单元格组  是使用302规范的中的某个规范 (保存在全局map中标记)  --->   在汇总检测时使用（在保存前检测规范）
 *    //criterion  -- > select * from cell_criterion  where id='20130530/jm-0000087' and index_id='302'
 * 	  'textbox_11_15' 检测规范变长区
 *    '302' 规范表中的index_id
 * 2、引用规范参数：SETVARIABLE('criterion_302','强度底基层设计值')  设置当前单元格的值为  302规范中的 参数字段【强度底基层设计值】（保存到全局map中）---->在汇总检测时使用(保存前执行)
 * 	  //criterionParam  -- > select * from dbo.interface where frmtype='sys_orderfield' and fname='宽度基层设计值' and index_id='302'
 *    'criterion_302' (规范参数标识)_(规范表的index_id)  
 *    '强度底基层设计值' 规范的 参数名称   dataPoint 
 * 3、引用其它数据表数据：排除CVT_ELEM_TMPL_REF表中REF_TYPE_字段类型为criterion 和 criterionParam
 * 					            在页面初始化的时候执行，赋值给单元格内   (如果当前空格为空 则赋值)
 *    @domainId --> X业务表中的 domain_index
 *    @wbsInstId --> WBS实例id在业务表中的index_id
 */

/*
 * 设置变量
 */
function SETVARIABLE(id, index_id) {
	var obj = {
		"id": id,
		"index_id": index_id
	}; //当前单元格参数
	if (id.startsWith('criterion_')) { //引用规范参数  当前单元格的值放置到全局中
		obj.type = "cri_params";
		//console.log("cri_params-"+id+"-"+index_id);
	} else { //引用规范  在保存前执行检查动作
		obj.type = "cri";
		//console.log("cri-"+id+"-"+index_id);
	}
	return obj;
}

/*
 * id 汇总的范围格
 * 汇总 type : 0合格 , 1不合格,2检测点数
 */
function CRITERIONSTAT(id, type) {
	var obj = {
		"id": id,
		"ctype": type,
		"type": "stat"
	};
	//console.log("stat-"+id+"-"+type);
	return obj;
};/**
 * 链接
 */
var aFunc = {
	getValue : function(rule, args) {
		return pubsub.getJQObj(rule, true).html();
	},
	getActVal : function(rule, args) {
		return pubsub.getJQObj(rule, true).attr("actVal");
	},
	setValue:function(rule, args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			$id.find("p").html(v);
		} else {
			$id.find("p").html(args);
		}
	}
};
pubsub.sub("a", aFunc);/**
 * 按钮
 */
var buttonFunc = {

};
pubsub.sub("button", buttonFunc);var chartsFunc = {
	init: function(rule, args) {
		var options = args[0];
		$.ajax({
			type: "POST",
			url: contextPath + "/mx/form/charts/datas",
			contentType: "application/json",
			dataType: "json",
			data: JSON.stringify({
				'rid': options.rid,
				'params':getParams(rule.inid)?JSON.stringify(getParams(rule.inid)):null
			}),
			success: function(msg) {
				var series = chartsFunc.transform(msg, options),
					$this = $('#' + rule.inid),
					sersLens = [];
				options.series = series;
				$.each(series,function(i,v){
					v.data.length >1000 && sersLens.push(v.data.length);
				})
				if(sersLens.length){
					options.plotOptions.series.turboThreshold = Math.max.apply(Math,sersLens);
				}
				if ($this.data("clickEvent")) {
					options.plotOptions.series.point.events.click = $this.data("clickEvent");
				}
				$this.highcharts(options);
				if (options.rotate3D) {
					chartsFunc.rotate3D(rule.inid);
				}
				if($this.resize){
					$this.resize(function(){
		  				$this.highcharts().setSize($this.width(), $this.height(), true);
					});
				}
			}
		});
	},
	tshow: function(rule, args) { // 显示
		var mtcharts = pubsub.getJQObj(rule)
		mtcharts.highcharts().setSize(100, 100, true);
		mtcharts.show();
		mtcharts.highcharts().setSize(mtcharts.width(), mtcharts.height(), true);
	},
	getRow: function(rule, args) {
		// $("#" + rule.inid).highcharts();
		var data = args[1];
		if (rule.columnName == "categories") { //系列名称
			return (data.series && data.series.name) || "";
		}
		return data[rule.columnName];
	},
	refresh: function(id, params, args) {
		chartsFunc.linkageControl(id, params, args);
	},
	linkageControl: function(id, params, args) {
		var $id = pubsub.getJQObj(id);
		var $highcharts = $id.highcharts();
		if ($highcharts) {
			var options = $id.highcharts().options;
			var data = {
				rid: options.rid
			};
			data.params = JSON.stringify(params);
			$.ajax({
				type: "POST",
				url: contextPath + "/mx/form/charts/datas",
				contentType: "application/json",
				dataType: "json",
				data: JSON.stringify(data),
				success: function(msg) {
					var series = chartsFunc.transform(msg, options);
					options.series = series;
					$id.highcharts(options);
				}
			});
		} else {
			// 循环监测
			var chartsfn = setInterval(function() {
				if ($id.highcharts()) {
					var options = $id.highcharts().options;
					var data = {
						rid: options.rid
					};
					data.params = JSON.stringify(params);
					$.ajax({
						type: "POST",
						url: contextPath + "/mx/form/charts/datas",
						contentType: "application/json",
						dataType: "json",
						data: JSON.stringify(data),
						success: function(msg) {
							var series = chartsFunc.transform(msg, options);
							options.series = series;
							$id.highcharts(options);
						}
					});
					clearInterval(chartsfn);
				}
			}, 200);
		}
	},
	_getRule: function(rules, id) { // 根据id找到对应的规则
		var rulesSize = rules.length;
		if (rulesSize) {
			var rule;
			for (var i = 0; i < rulesSize; i++) {
				rule = rules[i];
				if (rule.id == id) {
					return rule;
				}
			}
		}
		return null;
	},
	_hasYAxisName: function(series, yAxisName) {
		var seriesSize = series.length,
			serie;
		if (seriesSize) {
			for (var i = 0; i < seriesSize; i++) {
				serie = series[i];
				if (serie.name == yAxisName) {
					return serie;
				}
			}
		}
		return null;
	},
	_staticBuildSubserie: function(data, rule) { // 静态列
		var subserie = {};
		for (var p in data) {
			if (rule.xAxisName && p == rule.xAxisName.en) {
				subserie.name = data[p];
				if (rule.xAxisName.type == "datetime") {
					subserie.name = chartsFunc._formatTime(data[p], rule.tf);
				}
			} else if (p == rule.axisName.en) {
				/*var y = parseFloat(data[p]);
				subserie.y = parseFloat(y.toFixed(2));*/
				if (data[p]) {
					var y = parseFloat(data[p]);
					subserie.y = parseFloat(y.toFixed(2));
				} else {
					subserie.y = null;
				}
			}
			subserie[p] = data[p];
		}
		return subserie;
	},
	_buildSubserie: function(data, rule, yan) { // 动态列
		var subserie = {};
		for (var p in data) {
			if (rule.xAxisName && p == rule.xAxisName.en) {
				subserie.name = data[p];
				if (rule.xAxisName.type == "datetime") {
					subserie.name = chartsFunc._formatTime(data[p], rule.tf);
				}
			} else if (p == yan.en) {
				if (data[p]) {
					var y = parseFloat(data[p]);
					subserie.y = parseFloat(y.toFixed(2));
				} else {
					subserie.y = null;
				}
			}
			subserie[p] = data[p];
		}
		if (!subserie.name) { // 如果系列名x轴未配置 则默认使用y轴的列名
			subserie.name = yan.cn;
		}
		return subserie;
	},
	_formatTime: function(str, format) { // 时间转换
		//return kendo.format('{0:' + (format || 'yyyy-MM-dd') + '}', kendo.parseDate(str)) || str;
		return hmtdUtils.parseDate(str).format(format || 'yyyy-MM-dd') || str;
		//return new Date(str).format(format || 'yyyy-MM-dd') || str;
	},
	_setShowValueAndPoint : function(serie,rule){
		if (rule.markerEnable){
			serie.marker = serie.marker || {};
			serie.marker.enabled = rule.markerEnable && rule.markerEnable == 'true'? true : false;
		}
		if (rule.dataLabelsEnable){
			serie.dataLabels = serie.dataLabels || {};
			serie.dataLabels.enabled = rule.dataLabelsEnable && rule.dataLabelsEnable == 'true'? true : false;
		}
	},
	_groupBySeries: function(datas, rule, ind, opts) { // 根据系列分组
		var series = [],
			serie, subseries, subserie, data, datasSize = datas.length,
			yans = rule.yAxisName,
			yansSize = yans.length,
			an = rule.axisName,
			yan, xan = rule.xAxisName,
			yAxisLength = opts.yAxis.length > 1;
		// 静态系列
		if (yans.length == 1 && an != undefined && an.en != "") {
			for (var i = 0; i < datasSize; i++) {
				data = datas[i];
				var yAxisName = data[yans[0].en];
				if (yans[0].type == "datetime") {
					yAxisName = chartsFunc._formatTime(yAxisName, rule.tf);
				}
				// var xAxisName = data[xan.en]; 系列名
				var hasSerie = chartsFunc._hasYAxisName(series, yAxisName);
				if (hasSerie) { // 如果包含
					hasSerie.data.push(chartsFunc._staticBuildSubserie(data, rule));
				} else {
					serie = {};
					serie.name = yAxisName;
					if (yAxisLength) {
						serie.yAxis = ind;
					}
					// serie.xAxis = ind ;
					serie.type = rule.chartType;
					if (rule.stack) {
						serie.stack = data[rule.stack];
					}
					chartsFunc._setShowValueAndPoint(serie,rule);
					
					/*
					 * if(serie.name == "0"){ serie.stack =ind+ "我的" ; }else{
					 * serie.stack =ind+ "你的" ; }
					 */
					subseries = [];
					subserie = chartsFunc._staticBuildSubserie(data, rule);
					subseries.push(subserie);
					serie.data = subseries;
					if (opts.colorByPoint) {
						serie.colorByPoint = true; //根据点自动分配颜色
					}
					series.push(serie);
				}
			}
		} else { // 动态系列
			for (var i = 0; i < datasSize; i++) {
				data = datas[i];
				for (var j = 0; j < yansSize; j++) {
					yan = yans[j];
					var hasSerie = chartsFunc._hasYAxisName(series, yan.cn);
					if (hasSerie) {
						hasSerie.data.push(chartsFunc._buildSubserie(data, rule, yan));
					} else {
						serie = {};
						if (yAxisLength) {
							serie.yAxis = ind;
						}
						serie.name = yan.cn;
						serie.type = rule.chartType;
						chartsFunc._setShowValueAndPoint(serie,rule);
						if (rule.stack) {
							serie.stack = data[rule.stack];
						}
						subseries = [];
						subserie = chartsFunc._buildSubserie(data, rule, yan);
						subseries.push(subserie);
						serie.data = subseries;
						if (opts.colorByPoint) {
							serie.colorByPoint = true; //根据点自动分配颜色
						}
						series.push(serie);
					}
				}
			}
		}
		return series;
	},
	_buildPie: function(serie) { // 饼图多系列Y轴合并数据
		var ss = [],
			s = {
				data: []
			},
			ser, sdatas;
		for (var j = 0, l = serie.length; j < l; j++) {
			ser = serie[j];
			sdatas = ser.data;
			s.name = sdatas[0].name;
			for (var k = 0, ll = sdatas.length; k < ll; k++) {
				sdatas[k].name = ser.name;
				s.data.push(sdatas[k]);
			}
		}
		ss.push(s);
		return ss;
	},
	transform: function(datas, opts) {
		var retobj = {},
			rules = opts.chartsSource,
			datasSize = datas.length,
			data, rule, series = [];
		if (datasSize) {
			for (var i = 0; i < datasSize; i++) {
				data = datas[i];
				rule = chartsFunc._getRule(rules, data.id);
				if (rule) {
					var serie = chartsFunc._groupBySeries(data.data, rule, i, opts);
					if (serie && serie.length > 1 && opts.chart.type == "pie") { // 多饼图数据合并处理
						serie = chartsFunc._buildPie(serie);
					}
					$.merge(series, serie);
				}
			}
			if (opts.sortLegend) {
				var temp = $.extend([], series);
				var sortLegend = opts.sortLegend;
				/*for (var j = 0; j < sortLegend.length; j++) {
					series[j] = temp[(sortLegend[j] - 1)];
				}*/
				var diff = 0,
					tempSerie;
				for (var j = 0; j < sortLegend.length; j++) {
					tempSerie = temp[(sortLegend[j] - 1)];
					if (tempSerie)
						series[j - diff] = tempSerie;
					else
						diff++;
				}
			}
			return series;
		}
		return [];
	},
	rotate3D: function(id) {
		var chart = $('#' + id).highcharts();
		$(chart.container).bind('mousedown.hc touchstart.hc', function(e) {
			e = chart.pointer.normalize(e);
			var posX = e.pageX,
				posY = e.pageY,
				alpha = chart.options.chart.options3d.alpha,
				beta = chart.options.chart.options3d.beta,
				newAlpha, newBeta, sensitivity = 5;
			$(document).bind({
				'mousemove.hc touchdrag.hc': function(e) {
					newBeta = beta + (posX - e.pageX) / sensitivity;
					newBeta = Math.min(100, Math.max(-100, newBeta));
					chart.options.chart.options3d.beta = newBeta;
					newAlpha = alpha + (e.pageY - posY) / sensitivity;
					newAlpha = Math.min(100, Math.max(-100, newAlpha));
					chart.options.chart.options3d.alpha = newAlpha;
					chart.redraw(false);
				},
				'mouseup touchend': function() {
					$(document).unbind('.hc');
				}
			});
		});
	},
	changeSeriesValue : function(rule,args){
		var mtcharts = pubsub.getJQObj(rule);
		var mtcharts_hightchart = mtcharts.highcharts();
		var series = mtcharts_hightchart.series;
		// var name = 
		$.each(args,function(key,value){

			$.each(series,function(i,item){
				if(item.name == key){
					var valArray = [];
					$.each(item.data,function(k,kitem){
						valArray.push(parseInt(value));
					})
					item.setData(valArray);
				}
			})
		})
		mtcharts_hightchart.redraw();
	}
};
pubsub.sub("charts", chartsFunc);

/*$(window).resize(function() {
	setTimeout(function() {
		var mtcharts = $("[data-role=charts]");
		if (mtcharts && mtcharts.length) {
			$.each(mtcharts, function(index, val) {
				var $this = $(this);
				$this.highcharts().setSize($this.width(), $this.height(), true);
			});
		}
	}, 100);
});*/

// highcharts 全局设置
try {
	Highcharts.setOptions({
		lang: {
			drillUpText: '<< 返回 {series.name}',
			noData: '暂无数据'
		}
	});
} catch (ex) {}/**
 * 下拉框
 */
var comboboxFunc = $.extend({}, {}, {
	getText : function(rule, args) {
		var d = pubsub.getJQObj(rule, true);
		var k = kendo.widgetInstance(d)
		return k.text();
	},
	getValue : function(rule, args) {
		var d = pubsub.getJQObj(rule, true);
		var k = kendo.widgetInstance(d)
		return k.value();
	},
	setValue : function(rule, args) {
		var $id = pubsub.getJQObj(rule), r, v = "";
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param];
		}
		kendo.widgetInstance($id).value(v);
	},
	thidden : function(rule, args) {// 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.closest('.k-widget').hide();
	},
	tshow : function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.closest('.k-widget').show();
	},
	tdisabled : function(rule, args) {// 禁用
		kendo.widgetInstance(pubsub.getJQObj(rule)).enable(false);
	},
	tenabled : function(rule, args) {// 启用
		kendo.widgetInstance(pubsub.getJQObj(rule)).enable(true);
	},
	linkage : function(id, params) {
		var targer = pubsub.getJQObj(id);
		var widget = kendo.widgetInstance(targer);
		var data = widget.dataSource.transport.options.read.data;
		data.params = JSON.stringify(params);
		widget.dataSource.read();
	},
	linkageControl : function(id, params) {
		comboboxFunc.linkage(id,params);
	}

});
pubsub.sub("combobox", comboboxFunc);

var dropdownlistFunc = $.extend({}, comboboxFunc, {});
pubsub.sub("dropdownlist", dropdownlistFunc);


pubsub.sub("autocomplete", comboboxFunc);var customFunc = {
	init: function(rule, args) {
		$("#" + rule.inid).data("retSource", args[0]);
		customFunc.setValue($("#" + rule.inid), "");
	},
	setValue: function(jq, args) {
		var rets = jq.data("retSource"),
			length = rets.length,
			ret, key, val;
		if (length) {
			for (var i = 0; i < length; i++) {
				ret = rets[i];
				key = customFunc._convert(ret.key, args, 'key');
				if (eval(key) || key == "true") {
					jq.html(customFunc._convert(ret.val, args, 'val'));
					return;
				}
			}
		}
	},
	_convert: function(k, v, type) {
		var length = v.length,
			o, par, val, reger, tv = (type == 'key' ? "'" : "");
		if (length) {
			for (var i = 0; i < length; i++) {
				o = v[i];
				par = o.param;
				val = o.value;
				reger = new RegExp("##" + par + "##", "gm");
				k = k.replace(reger, tv + val + tv);
			}
		}
		return k.replace(/\\"/gm, "'").replace(/##col\w*##/gm, type == 'key' ? "''" : "");
	},
	_paramsToArgs: function(params) {
		var args = [],
			arg;
		for (var p in params) {
			arg = {};
			arg.param = p;
			arg.value = params[p];
			args.push(arg);
		}
		return args;
	},
	linkage: function(rule, params) {
		customFunc.linkageControl(rule, params);
	},
	linkageControl: function(id, params) { // 联动控件
		var jq = pubsub.getJQObj(id),
			rets = jq.data("retSource"),
			length = rets.length,
			ret, key, val, args = customFunc._paramsToArgs(params);
		if (length) {
			for (var i = 0; i < length; i++) {
				ret = rets[i];
				key = customFunc._convert(ret.key, args, 'key');
				if (eval(key) || key == "true") {
					if(params.esc){
						jq.html($(customFunc._convert(ret.val, args, 'val')).text());	
					}else{
						jq.html(customFunc._convert(ret.val, args, 'val'));
					}
					return;
				}
			}
		}
	}
};
pubsub.sub("custom", customFunc);var datetimepickerFunc = {
	getValue : function(rule, args) {
		var $id = pubsub.getJQObj(rule, true);
		return kendo.widgetInstance($id).value();
	},
	setValue : function(rule, args) {
		var $id = pubsub.getJQObj(rule), r, v = "";
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param];
		}
		kendo.widgetInstance($id).value(v);
	},
	thidden : function(rule, args) {// 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.closest('.k-widget').hide();
	},
	tshow : function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.closest('.k-widget').show();
	},
	tdisabled : function(rule, args) {// 禁用
		kendo.widgetInstance(pubsub.getJQObj(rule)).enable(false);
	},
	tenabled : function(rule, args) {// 启用
		kendo.widgetInstance(pubsub.getJQObj(rule)).enable(true);
	},
};
pubsub.sub("datetimepicker", datetimepickerFunc);

var datepickerFunc = $.extend({}, datetimepickerFunc, {

});
pubsub.sub("datepicker", datepickerFunc);/**
 * 自定义表格
 */
var definedTableFunc = {
	getRow : function(rule, args) {
		var target = mtxx.e.target;
		if (target) {
			var dataItem = pubsub.getJQObj(rule,true).definedTable("dataItem", target)
			if (dataItem) {
				return dataItem[rule.columnName];
			}
		}
		/*
		 * var widget = kendo.widgetInstance(targer); var rows =
		 * widget.select(); var value = []; for (var i = 0; i < rows.length;
		 * i++) { var data = widget.dataItem(rows[i]);
		 * value.push(data[rule.columnName]); } return value.join(",");
		 */
		return null;
	},
	getValue : function(rule, args) {
		return pubsub.getJQObj(rule,true).closest("a").text();
	},
	linkage : function(rule,args){
		definedTableFunc.linkageControl(rule,args);
	},
	linkageControl : function(id, params) {
		pubsub.getJQObj(id).definedTable("query", $.isArray(params)?{}:params);
	},
	getAll : function(rule, args) {
		var $in = pubsub.getJQObj(rule, args[0] || true);
		if ($in) {
			var retAry = [], dataItems = $in.data("definedTable").rows, dataItem;
			if (dataItems) {
				for (var i = 0; i < dataItems.length; i++) {
					dataItem = dataItems[i];
					retAry.push(dataItem[rule.columnName]);
				}
				return retAry.join(",");
			}
		}
		return null;
	},
	getAllRows : function(rule,args){
		var $in = pubsub.getJQObj(rule, args[0] || true);
		if ($in) {
			var retAry = [], dataItems = $in.data("definedTable").allRows, dataItem;
			if (dataItems) {
				for (var i = 0; i < dataItems.length; i++) {
					dataItem = dataItems[i];
					retAry.push(dataItem[rule.columnName]);
				}
				return retAry.join(",");
			}
		}
		return null;
	},
	getFirstRow : function(rule, args) {
		var $in = pubsub.getJQObj(rule, args[0] || true);
		if ($in) {
			var retAry = [], dataItems = $in.data("definedTable").rows, dataItem;
			if (dataItems && dataItems[0]) {
				return dataItems[0][rule.columnName];
			}
		}
		return null;
	}
};
pubsub.sub("definedTable", definedTableFunc);/**
 * 树型网格
 */
var treelistbtFunc = {
	getRow : function(rule, args) {
		var dataItems = pubsub.getJQObj(rule,true).treelist("getSelectedRows"),val=[];
		if(dataItems && dataItems.length){
			$.each(dataItems,function(i,o){
				if(o[rule.columnName]){
					val.push(o[rule.columnName]||"");
				}
			});
			return val.join(",");
		}
		return "";
	},
	getValue : function(rule, args) {
		return pubsub.getJQObj(rule,true).closest("a").text();
	},
	linkage: function(rule, params) {
		treelistbtFunc.linkageControl(rule, params);
	},
	linkageControl : function(id, params) {
		pubsub.getJQObj(id).data("treelist").options.expandChilds = params.expandLevel||true;
		pubsub.getJQObj(id).treelist("query", params);
	},
	selectRow : function(rule, args){
		var $id = pubsub.getJQObj(rule);
		var rows = $id.treelist("getData");
		if (rows && rows.length) {
			$.each(rows,function(i,row){
				var idVal = args[rule[0].param];
				if(idVal){
					var  pas = idVal.split(",");
					for(var j=0;j<pas.length;j++){
						if(row.id == pas[j]){
							$id.treelist("select",row.id);
						}
					}
				}
			});
		}
	},
	cancelSelect:function(rule, args){
		var $id = pubsub.getJQObj(rule).treelist("cancelSelect");
	},
	getAll : function(rule, args) {
		var $in = pubsub.getJQObj(rule, args[0] || true);
		if ($in) {
			var retAry = [], dataItems = $in.data("treelist").getData(), dataItem;
			if (dataItems) {
				for (var i = 0; i < dataItems.length; i++) {
					dataItem = dataItems[i];
					retAry.push(dataItem[rule.columnName]||"");
				}
				return retAry.join(",");
			}
		}
		return "";
	},
	setRowBgColor: function(rule, args){
		pubsub.getJQObj(rule).treelist("setRowBgColor", args.rowIndex, args.color);
	},
	tSelectFirst: function(rule, args) { //默认选中第一行
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			setTimeout(function() {				
				$id.treelist("select","","tr:eq(0)");
				var ink = new MutationObserver(function(record) {
					$id.treelist("select","","tr:eq(0)");
					ink.disconnect();
				});
				ink.observe($id[0], {
					'childList': true,
					'arrtibutes': true,
					'subtree': true,
					'characterData': true
				});
			}, 100);
		}
	},
	grefresh: function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		$id.treelist("query", $.isArray(args)?{}:args);
	},
	gsaveChange:function(rule, args){
		var $id = pubsub.getJQObj(rule);
		$id.treelist("saveHandle");
	}
};
pubsub.sub("treelistbt", treelistbtFunc);


/**
 * 组织机构图表
 */
var diagramFunc = {
	init : function(rule, args) {
		var options = args[0];
		$("#" + rule.inid).kendoDiagram(options);
	},
	getValue : function(rule, args) {
		return args[1].indexId;
	},
	getNode : function(rule, args) {
		return args[1][rule.columnName];
	}
};
pubsub.sub("diagram", diagramFunc);var gisFunc = {
	getValue : function(rule, args) {
		return args[1] || '0';
	},
	linkageControl : function(id, params) { // 联动控件
		for ( var p in params) {
			slCtl.Content.mainForm.SelectUnit(params[p]/* "28738" */, '#003333CC');
		}
	}
};
pubsub.sub("gis", gisFunc);var gridFunc = {
	getText: function(rule) { // 获取单元格的值
		// var targer = jQuery('#' + rule.inid);
		var targer = pubsub.getJQObj(rule, true);
		var widget = kendo.widgetInstance(targer);
		var value = [];
		if (widget) {
			var rows = widget.select();
			for (var i = 0; i < rows.length; i++) {
				if(rows[i].innerText){
					value.push(rows[i].innerText);
				}
			}
		}
		return value.join(",");
	},
	getRow: function(rule) { // 获取选中行的字段
		// var targer = jQuery('#' + rule.inid);
		var targer = pubsub.getJQObj(rule, true);
		var widget = kendo.widgetInstance(targer);
		var value = [];
		if (widget) {
			var rows = widget.select();
			for (var i = 0; i < rows.length; i++) {
				var data = widget.dataItem(rows[i]);
				if(data[rule.columnName]){
					value.push(data[rule.columnName]);
				}
			}
		}
		return value.join(",");
	},
	getAll: function(rule, args) {
		var targer = pubsub.getJQObj(rule, true);
		var widget = kendo.widgetInstance(targer);
		var value = [];
		if (widget) {
			var rows = widget.dataItems();
			for (var i = 0; i < rows.length; i++) {
				var data = rows[i];
				value.push(data[rule.columnName]);
			}
		}
		return value.join(",");
	},
	linkage: function(rule, params) {
		gridFunc.linkageControl(rule, params);
	},
	linkageControl: function(id, params) {
		// var targer = jQuery('#' + id);
		var targer = pubsub.getJQObj(id);
		params["databaseId"] && targer.attr("dbid",params["databaseId"]);
		var widget = kendo.widgetInstance(targer);
		if (widget) {
			var data = widget.dataSource.transport.options.read.data;
			data.params = JSON.stringify(params);
			// 传入创建父ID
			var create = widget.dataSource.transport.options.create;
			if (create) {
				var createData = create.data;
				createData.params = JSON.stringify(params);
			}
			var update = widget.dataSource.transport.options.update;
			if (update) {
				var updateData = update.data;
				updateData.params = JSON.stringify(params);
			}
			widget.dataSource.read();
		}
	},
	gsaveChange: function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			// var kg = $id.data('kendoGrid') || $id.data('kendoTreeList');
			var kg = kendo.widgetInstance($id);
			kg.saveChanges();
		}
	},
	grefresh: function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			// var kg = $id.data('kendoGrid') || $id.data('kendoTreeList');
			var kg = kendo.widgetInstance($id);
			kg.dataSource.read();
		}
	},
	tSelectFirst: function(rule, args) { //选中第一行
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			setTimeout(function() {
				var kg = kendo.widgetInstance($id);
				var isGrid = $id.data("role") == "grid";
				kg.select(isGrid ? ( /*"#" + $id.attr("id") + */ " tbody tr:eq(0)") : $id.find("tbody tr:eq(0)"));
				var ink = new MutationObserver(function(record) {
					kg.select(isGrid ? ( /*"#"+$id.attr("id")+*/ " tbody tr:eq(0)") : $id.find("tbody tr:eq(0)"));
					ink.disconnect();
				});
				ink.observe($id[0], {
					'childList': true,
					'arrtibutes': true,
					'subtree': true,
					'characterData': true
				});
			}, 100);
		}
	},
	cancelSelect: function(rule, args) { //取消选中
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			// var kg = $id.data('kendoGrid') || $id.data('kendoTreeList');
			var kg = kendo.widgetInstance($id);
			var isGrid = $id.data("role") == "grid";
			if (isGrid) {
				kg.cancelChanges();
			} else {
				kg.clearSelection();
			}
		}
	},
	selectRow: function(rule, params,args) { //恢复选中事件
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			var widget = kendo.widgetInstance($id);
			if (widget) {
				var isGrid = $id.data("role") == "grid";
				var rows = widget.dataItems();
				if (rows && rows.length) {
					$.each(rows,function(i,row){
						for(var param in params){
							var ps = params[param], pas = ps.split(",");
							for(var j=0;j<pas.length;j++){
								if(row.id == pas[j]){
									widget.select(isGrid ? "tbody tr:eq("+i+")" : $id.find("tbody tr:eq"+i+")"));
								}
							}
						}
					});
				}
			}
		}
	},
	addNode: function(rule, args){
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			var kg = kendo.widgetInstance($id);
			var isGrid = $id.data("role") == "grid";
			if (isGrid) {
				//kg.cancelChanges();
			} else {
				var selectRow = kg.select();
				if(selectRow.length==1){
					kg.addRow(selectRow);
				}else if(selectRow.length==0){
					kg.addRow();
				}
			}
		}
	}
};
//注册 grid
pubsub.sub("grid", gridFunc);
pubsub.sub("treelist", gridFunc);
// ********************************** kendo grid 相关
// ***************************************************************
var glafGrid = {
	// 打开链接
	openLink: function(t, path) {
		var ele = $(t);
		window.open(path + '/mx/form/page/viewPage?id=' + ele.attr('link'));
	},
	treelistExpand: function(e) {
		if (e.data.isExpand) {
			var params = e.sender.dataSource.transport.options.read.data.params;
			if (params && !$.isEmptyObject(JSON.parse(params)) && $(e.sender).data('expandTime')) {
				var rows = e.sender.tbody.find("tr");
				if (rows && rows.length < 10) {
					for (var j = 0; j < rows.length; j++) {
						e.sender.expand(rows[j]);
					}
				}
				$(e.sender).data('expandTime',($(e.sender).data('expandTime')||0)+1);
			}else{
				$(e.sender).data('expandTime',1);
			}
		}
	},
	// k:kendo的Grid对象 linkType:联动类型 ids:联动的对象ID集合
	clickFunction: function(k, linkType, ids) {
		// 获取选中行
		var selectedRows = k.select();
		var dataItem = k.dataItem(selectedRows[0]);
		// console.log(k);
		var prid = k.dataSource.transport.options.read.data.rid;
		// console.log(dataItem);
		for (var i = 0; i < ids.length; i++) {
			// 获取目标组件
			var targer = jQuery('#' + ids[i].name);
			if (linkType == 'linkageControl') {
				var widget = kendo.widgetInstance(targer);
				var woname = widget.options.name;
				if (woname == "Grid" || woname == "TreeList") {
					var data = widget.dataSource.transport.options.read.data;
					data.parentId = (k.options.name == "Grid" ? dataItem.id : dataItem.startId);
					data.prid = prid;
					widget.dataSource.read();
					// 传入创建父ID
					var createData = widget.dataSource.transport.options.create.data;
					createData.parentId = (k.options.name == "Grid" ? dataItem.id : dataItem.startId);
					createData.prid = prid;
				}
			}
		}
	},
	// Grid checkbox选择行
	selectRow: function(that, id, ids) {
		var checked = that.checked,
			row = $(that).closest("tr"),
			grid = $("#" + id).data("kendoGrid"),
			dataItem = grid.dataItem(row);
		ids[dataItem.id] = checked;
		if (checked) {
			row.addClass("k-state-selected");
		} else {
			row.removeClass("k-state-selected");
		}
	},
	// Grid checkbox全选框
	selectAllRow: function(that, id, ids) {
		var dataItem, row, checked = that.checked,
			grid = $("#" + id).data("kendoGrid"),
			dataItems = grid.dataSource.data();
		var checkboxs = $("." + id + "_checkbox");
		$.each(checkboxs, function(i, n) {
			n.checked = checked;
			row = $(n).closest("tr");
			dataItem = grid.dataItem(row)
			ids[dataItem.id] = checked;
			if (checked) {
				row.addClass("k-state-selected");
			} else {
				row.removeClass("k-state-selected");
			}
		});
	},
	// 翻页单选
	onDataBound: function(e, id, ids) {
		var checkbox = $("." + id + "_checkbox_All");
		checkbox[0].checked = false;
		var view = e.sender.dataSource.view();
		var count = 0;
		for (var i = 0; i < view.length; i++) {
			if (ids[view[i].id]) {
				e.sender.tbody.find("tr[data-uid='" + view[i].uid + "']").addClass("k-state-selected").find("." + id + "_checkbox").attr("checked", "checked");
				count++;
			}
		}
		if (view.length == count) {
			checkbox[0].checked = true;
		}
	},
	// 启用1、禁用0 validityFlag
	toolbar_enable: function(type, e) {
		if (!confirm("确定要" + (type == 1 ? "启用" : "禁用") + "吗？")) {
			return;
		}
		var grid = $("#" + e.getAttribute("gid")).data("kendoGrid");
		var rows = grid.select();
		for (var i = 0; i < rows.length; i++) {
			var data = grid.dataItem(rows[i]);
			if (data.hasOwnProperty('validityFlag')) {
				data.set("validityFlag", type);
			} else {
				alert('当前无法使用启用、禁用功能!');
				return;
			}
		}
		grid.dataSource.sync();
	},
	// 全局提示
	showTooltip: function(id) {
		$("#" + id).kendoTooltip({
			width: 200,
			filter: "td:not([data-container-for]):not(:contains('编辑删除')):not(:contains('更新取消'))",
			position: "right",
			content: function(e) {
				var td = e.target.closest("td");
				var content = td[0].innerText;
				return content;
			}
		}).data("kendoTooltip");
	},
	// 显示数据源的值
	getGridDataSourceValue: function(array, value, t, v) {
		var length = arguments.length;
		for (var i = 0; i < array.length; i++) {
			var obj = array[i];
			if (length < 4) {
				if (obj.value == value) {
					return obj.text;
				}
			} else {
				if (obj[v] == value) {
					return obj[t];
				}
			}
		}
		return "";
	},
	searchGrid: function() {
		var array = this.wrapper.find("input[cn]");
		// grid
		var targer = jQuery('#' + this.wrapper.attr('grid'));
		var widget = kendo.widgetInstance(targer);
		var data = widget.dataSource.transport.options.read.data;
		// "filter":{"logic":"and","filters":[{"field":"cell_useradd7028_user3","operator":"eq","value":22}]
		var filter = {};
		var fiobj = {
			"logic": "and"
		};
		var farray = [];
		for (var i = 0; i < array.length; i++) {
			var ele = jQuery(array[i]);
			var obj = new Object();
			obj['field'] = ele.attr('cn');
			if (ele.attr('ft') == 'checkbox') {
				obj['value'] = ele.val() == 'true' ? 1 : 0;
			} else {
				obj['value'] = ele.val();
			}
			obj['operator'] = "eq";
			if (ele.val() != '') {
				farray.push(obj);
			}
		}
		fiobj.filters = farray;
		data.filter = fiobj;
		widget.dataSource.read();

	},
	searchClear: function() {
		var array = this.wrapper.find("input[cn]");
		for (var i = 0; i < array.length; i++) {
			kendo.widgetInstance(jQuery(array[i])).value('');
		}
	},
	hiddenSearchToolbar: function(e) {
		if ($(e).html() == '隐藏') {
			$(e).html('显示');
			$('#' + e.getAttribute("gid") + '_toolbar').hide('fast', 'swing');
		} else {
			$(e).html('隐藏');
			$('#' + e.getAttribute("gid") + '_toolbar').show('fast');
		}
	}
};/**
 * 图片上传控件
 */
var imageuploadFunc = {
	tdisabled: function(rule, args) { // 禁用
		var $id = pubsub.getJQObj(rule);
		$id.find('.imageupload_tools').hide();
	},
	tenabled: function(rule, args) { // 启用
		var $id = pubsub.getJQObj(rule);
		$id.find('.imageupload_tools').show();
	},
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule).hide();
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule).show();
	},
	delImg: function(e) {
		// 
		var $this = $(e).closest('.image_upload_div');
		var randomparent = $this.attr("randomparent");
		if (randomparent) {
			if (confirm("是否删除图片")) {
				var removeurl = $this.attr("removeurl");
				$.ajax({
					type: "post",
					url: removeurl,
					data: {
						id: randomparent
					},
					success: function(data) {
						alert("删除成功");
						$this.children('img').attr("src", "").hide();
						$this.attr("attachmentid", "");
						var outputName = $this.attr("outputName");
						if (outputName) {
							imageuploadFunc.setOutPutName(outputName, "");
						}
					},
					error: function(err) {
						alert("删除失败");
					}
				});
			}
		} else {
			alert("请先上传图片");
		}
	},
	setOutPutName: function(outputNames, names) {
		var opns = outputNames.split(",");
		for (var k = 0; k < opns.length; k++) {
			outputName = opns[k];
			if (outputName) {
				$("#" + outputName).val(names);
			}
		}
	},
	getValue: function(rule){
		var $id = pubsub.getJQObj(rule, true);
		return $id.attr("attachmentId");
	},
	setImgPic:function(rule,args){
		
		var $id = pubsub.getJQObj(rule),r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
		} else {
			v = args;
		}
		$id[0].onload = function() {
	        EXIF.getData($id[0], function() {
	            $(this).data("tags",EXIF.getAllTags(this));
	        });
	    };
		//8243d27f19e041df8d8225261a788c0b
		var url = '/glaf/mx/form/imageUpload?method=downloadById&_'+(new Date().getTime())+'&id='+v;
		$id.attr("attachmentid",v).find(".imageupload_class").attr(
			"src",
			url)
		.show();
	},
	getBasePicData: function(rule,args){
		var $id = pubsub.getJQObj(rule, true);
		var tags = $id.find(".imageupload_class").data("tags");
		return tags[rule.columnName];
	}
};
pubsub.sub("imageupload", imageuploadFunc);


//图片上传
function initImageUploadFunc() {

	$("[data-role=imageupload]").each(function() {
		initImageUpload(this, this.id, $(this).attr("randomparent"));
	});
	$('body').delegate('.clickViewImg', 'click', zoomViewImg);
	$('body').delegate('.dblclickViewImg', 'dblclick',
		zoomViewImg);
	imageUploadInitFunc();
}
function initImageUpload(elementObj, elementId, randomUUID) {
	if (elementObj) {
		var flag = true;
		if (!randomUUID) { //
			randomUUID = new UUID().createUUID();
			flag = false;
		}
		elementObj.setAttribute("randomparent", randomUUID);

		var downloadUrl = $(elementObj).attr("downloadUrl"),
			$img = $(
				"#" + elementId).find(".imageupload_class");
		$img.attr("src", downloadUrl + "&_"+(new Date().getTime())+"&randomparent=" + randomUUID);
		$img[0].onload = function() {
	        EXIF.getData($img[0], function() {
	            $(this).data("tags",EXIF.getAllTags(this));
	        });
	    };
	    
		if (flag) {
			$img.show();
		}
	}
}var dojoConfig = {
	parseOnLoad: true,
	cacheBust: true ,
	packages: [{
		"name": "myModules",
		"location": contextPath + "/scripts/arcgis"
	}]
};
var jsGisFunc = {
	getNode: function(rule, args) { //获取单击图形的某参数
		if (rule.param == "point") {
			return "";
		}
		return args[0].graphic.symbol.data[rule.columnName] || "";
	},
	_gallery: function(BasemapGallery, BasemapLayer, Basemap, dojo, jsgisMap, datas, position) { //多底图功能
		if (datas) {
			var content = '<div style="position:absolute; right:80px; top:10px; z-Index:99;"><div data-dojo-type="dijit.TitlePane" id="dijit_TitlePane_0" data-dojo-props="title:\'切换地图\',open:false"><div data-dojo-type="dijit.layout.ContentPane" style="overflow:auto;"><div id="basemapGallery"></div></div></div></div>',
				$connect = $(content);
			if (position) {
				$connect.css(JSON.parse(position));
			}
			$("#" + jsgisMap.id).append($connect);
			var basemapGallery = new BasemapGallery({
				showArcGISBasemaps: false,
				map: jsgisMap,
			}, "basemapGallery");
			var dataJsons = JSON.parse(datas);
			$.each(dataJsons, function(i, v) {
				var layer = new BasemapLayer({
					url: v.mapUrl
				});
				var basemap = new Basemap({
					layers: [layer],
					title: v.name,
					thumbnailUrl: contextPath + v.thumbnailUrl
				});
				basemapGallery.add(basemap);
			});
			$("body").on("mouseover", "#dijit_TitlePane_0", function bbc(e) {
				$(this).find(".dijitTitlePaneContentOuter").show();
				$(this).find(".dijitReset").show();
			}).on("mouseleave", "#dijit_TitlePane_0", function bbc(e) {
				$(this).find(".dijitTitlePaneContentOuter").hide();
				$(this).find(".dijitReset").hide();
			});
			basemapGallery.startup(); // 开启  
			dojo.connect(basemapGallery, "onError", function(msg) {
				console.log(msg);
			});
		}
	},
	_convert: function(k, v, type) { //转换函数
		var length = v.length,
			o, par, val, reger, tv = (type == 'key' ? "'" : "");
		if (length) {
			for (var i = 0; i < length; i++) {
				o = v[i];
				for (var p in o) {
					var kt = p.split("_0_")[1];
					reger = new RegExp("##" + kt + "##", "gm");
					k = k.replace(reger, tv + o[p] + tv);
				}
			}
		}
		return k.replace(/\\"/gm, "'").replace(/##col\w*##/gm, type == 'key' ? "''" : "").replace(/##\w*##/gm, type == 'key' ? "''" : "");
	},
	_getParamName: function(rule, param, type) { //获取传递的参数名称
		if (type) {
			if (rule.rules) {
				var r = JSON.parse(rule.rules),
					rulek, ruleObj, par, retVal = "";
				for (var p in r) {
					rulek = r[p];
					if (rulek) {
						for (var i = 0; i < rulek.length; i++) {
							ruleObj = rulek[i];
							par = param[ruleObj['columnName']];
							if (par) {
								retVal += ruleObj['param'] + "=" + par + "&";
							}
						}
					}
				}
				return retVal;
			}
			return "";
		} else {
			for (var i = 0; i < rule.length; i++) {
				var r = rule[i];
				if (param.urlId == r.outid) {
					return r.param;
				}
			}
			return "";
		}
	},
	_jsGisDraw: function(map, params, rules) { //手动画图
		var trul = getUrlToSplitter() + contextPath + "/mx/form/page/viewPage?id=";
		jsgistb = new esri.toolbars.Draw(map);
		dojo.connect(jsgistb, "onDrawEnd", function(geometry) {
			var symbol = "",
				point = geometry,
				type = geometry.type;
			if (type === "point" || type === "multipoint") {
				symbol = jsgistb.markerSymbol;
			} else if (type === "line" || type === "polyline") {
				point.x = geometry.paths[0][0][0];
				point.y = geometry.paths[0][0][1];
				symbol = jsgistb.lineSymbol;
			} else {
				symbol = jsgistb.fillSymbol;
			}
			map.graphics.add(new esri.Graphic(geometry, symbol));
			var gsonStr = JSON.stringify(geometry.toJson()),
				gson = {
					type: type,
					source: gsonStr
				};
			var allParams = [];
			for (var i = 0; i < params.length; i++) {
				var param = params[i];
				if (param.type == type && param.type != "all") {
					var p = "&" + jsGisFunc._getParamName(rules, param) + "=" + JSON.stringify(gson).replace(/\\"/gm, "\\\\\"").replace(/"/gm, "\\\"");
					if (param.urlId) {
						jsGisFunc.jsGisShowInfoWindow(map, point, param.title, trul + param.urlId + p, param.width, param.height);
					}
					return;
				} else if (param.type == "all") {
					allParams.push(param);
				}
			}
			if (allParams) {
				for (var i = 0; i < allParams.length; i++) {
					var allParam = allParams[i];
					var p = "&" + jsGisFunc._getParamName(rules, allParam) + "=" + JSON.stringify(gson).replace(/\\"/gm, "\\\\\"").replace(/"/gm, "\\\"");
					if (allParam.urlId) {
						jsGisFunc.jsGisShowInfoWindow(map, point, allParam.title, trul + allParam.urlId + p, allParam.width, allParam.height);
					}
				}
			}
		});
	},
	_initType: function(data, rules) { //初始化规则（解析某条数据中有多少点）
		var ees = [];
		for (var k = 0; k < rules.length; k++) {
			var ee = rules[k];
			var key = jsGisFunc._convert(ee.exp, [data], 'key');
			if (eval(key)) {
				ees.push(ee);
			}
		}
		return ees;
	},
	_init: function(map, data, rules, coordInfo, url) { //初始化图层
		map.rules = rules;
		var hids = {},
			hidIds = {},
			pointGraphics = [];
		map.strokeGraphics || (map.strokeGraphics = []); //存储所有图形
		map.defaultGraphics || (map.defaultGraphics = []); //存储默认显示图形
		map.prevStorage || (map.prevStorage = []); //上次操作存储区
		for (var i = 0; i < data.length; i++) {
			var dat = data[i].data; //单个数据集数据
			for (var j = 0; j < dat.length; j++) {
				var da = dat[j],
					p = da[coordInfo.x]; //p
				var ees = jsGisFunc._initType(da, rules); //获取适用于当前数据的规则
				for (var k = 0; k < ees.length; k++) {
					var ee = ees[k];
					if (ee.column) {
						p = da[ee.column];
					}
					if (p) {
						try {
							var points = JSON.parse(p.replace(/\\&quot;/ig,"\"").replace(/\\\\/ig,"\\")),
								source,
								flag = points.flag ? true : false;
							source = JSON.parse(points.source);
							var symbol = null,
								geometry = null,
								isPoint = false;
							if (points.type == "point") {
								isPoint = true;
								symbol = new esri.symbol.PictureMarkerSymbol(contextPath + ee.icon, ee.iconWidth || 20, ee.iconHeight || 20);
								geometry = new esri.geometry.Point(source);

							} else if (points.type == "polyline") {
								symbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new esri.Color(ee.lineColor || ([255, 0, 0])), ee.lineWidth || 2);
								geometry = new esri.geometry.Polyline(source);
							} else if (points.type == "polygon") {
								var linesymbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new esri.Color(ee.lineColor || ([255, 0, 0])), ee.lineWidth || 2);
								symbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID, linesymbol, new esri.Color(ee.lineColor || ([255, 0, 0])));
								geometry = new esri.geometry.Polygon(source);
							}
							if (flag) {
								geometry = esri.geometry.geographicToWebMercator(geometry);
							}
							symbol.data = da;
							symbol.rule = ee;
							symbol.rules = rules;
							symbol.column = ee.column;
							var dd = new esri.Graphic(geometry, symbol);
							/*if (ee.isShow == "true") {} else {
								dd.visible = false;
							}*/
							if (ee.isShow == "true") {
								if (isPoint) {
									pointGraphics.push(dd);
								} else {
									map.defaultGraphics.push(dd);
								}
							} else {
								dd.visible = false;
							}
							//map.graphics.add(dd);
							map.strokeGraphics.push(dd);
						} catch (ex) {
							if (p) { // 根据规则 解析点数据对象
								var pAry = p.split(",");
								$.each(pAry, function(index, pVal) {
									var oo = {};
									var psplit = pVal.split("-");
									oo.id = psplit[1];
									hidIds[psplit[0]] || (hidIds[psplit[0]] = []);
									if ($.inArray(oo.id, hidIds[psplit[0]]) == -1) {
										hidIds[psplit[0]].push(oo.id);
									}
									oo.layerId = psplit[0];
									oo.data = da;
									oo.rule = ee;
									//oo.rules = ees;
									oo.column = ee.column;
									(hids[psplit[0]] || (hids[psplit[0]] = {})) && ((hids[psplit[0]][oo.id]) || (hids[psplit[0]][oo.id] = []));
									hids[psplit[0]][oo.id].push(oo);
								});
							}
						}
					}
				}
			}
		}
		if (!$.isEmptyObject(hids)) {
			var queryTask, query = new esri.tasks.Query();
			query.outFields = ["*"];
			query.returnGeometry = true;
			//query.multipatchOption = "xyFootprint";
			var layerIdLength = Object.getOwnPropertyNames(hidIds).length,
				j = 0;
			for (var layerId in hidIds) {
				queryTask = new esri.tasks.QueryTask(url + "/" + layerId);
				queryTask.layerId = layerId;
				//query.objectIds = hidIds[layerId];
				query.where = ' fid in ('+hidIds[layerId].join(",")+') ';
				queryTask.on("complete", function(r) {
					j++;
					var datas = hids[r.target.layerId];
					$.each(r.featureSet.features, function(index, grap) {
						$.each(datas[grap.attributes.FID], function(i, data) {
							var graphic = $.extend({}, grap);
							if (data.id == graphic.attributes.FID) {
								var ptype = graphic.geometry.type,
									ee = data.rule;
								isPoint = false, isPolygon = false;
								if (ptype == "point") {
									isPoint = true;
									symbol = new esri.symbol.PictureMarkerSymbol(contextPath + ee.icon, ee.iconWidth || 20, ee.iconHeight || 20);
								} else if (ptype == "polyline") {
									//fill-opacity
									symbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new esri.Color(ee.lineColor || ([255, 0, 0])), ee.lineWidth || 2);
									//symbol.setStyle({});
								} else if (ptype == "polygon") {
									isPolygon = true;
									var linesymbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new esri.Color(ee.lineColor || ([255, 0, 0])), ee.lineWidth || 2);
									symbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID, linesymbol, new esri.Color(ee.lineColor || ([255, 0, 0])));
								}
								var toffset = ee.toffset;
								if (toffset && isPoint) {
									try {
										var toffset = toffset.split(",");
										symbol.setOffset(parseFloat(toffset[0]), parseFloat(toffset[1]));
									} catch (e) {
										console.log(e);
									}
								}
								symbol.id = data.id;
								symbol.data = data.data;
								symbol.rule = ee;
								symbol.column = data.column;
								symbol.layerId = data.layerId;
								graphic.setSymbol(symbol);
								if (ee.isShow == "true") {
									//graphic.visible = true;
									if (isPoint) {
										pointGraphics.push(graphic);
									} else if (isPolygon) {
										map.graphics.add(graphic);
									} else {
										map.defaultGraphics.push(graphic);
									}
								} else {
									graphic.visible = false;
								}
								/*if (isPoint) {
									graphicsPoints.push(graphic);
								} else {*/
								map.strokeGraphics.push(graphic);
								map.prevStorage.push(graphic);
								//map.graphics.add(graphic);
								/*}*/
							}
						});
					});
					if (j == layerIdLength) {
						$.merge(map.defaultGraphics, pointGraphics);
						$.each(map.defaultGraphics, function(index, grap) {
							map.graphics.add(grap);
							map.workingStorage.push(grap);
							var graphic = $.extend({}, grap);
							var symbol = graphic.symbol,
								ee = symbol.rule;
							if (ee && ee.isShow == "true" && (ee.isSort == "true" || ee.showColumn) && grap.geometry.type == "point") {
								var text = new jsgisMap.esri.symbol.TextSymbol(ee.isSort == "true" ? count++ : (symbol.data[ee.showColumn] || ""));
								text.rule = symbol.rule;
								text.data = symbol.data;
								text.column = symbol.column;
								text.url = text.url;
								if (ee.sortSize) {
									var font = new map.esri.symbol.Font();
									font.setSize(ee.sortSize);
									text.setFont(font);
								}
								if (ee.sortColor) {
									var color = new map.esri.Color(ee.sortColor);
									text.setColor(color);
								}
								var sortOffset = ee.sortOffset;
								if (sortOffset) {
									try {
										var offsets = sortOffset.split(",");
										text.setOffset(parseFloat(offsets[0]), parseFloat(offsets[1]));
									} catch (e) {
										console.log(e);
									}
								}
								var graphic = new map.esri.Graphic(graphic.geometry, text);
								graphic.visible = true;
								graphic.isText = true;
								map.graphics.add(graphic);
								map.workingStorage.push(graphic);
								map.prevStorage.push(graphic);
							}
						});
						//加载完毕执行事件
						if (mapCb) {
							mapCb.fire();
						}
					}
				});
				queryTask.execute(query);
			}
		} else {
			$.merge(map.defaultGraphics, pointGraphics);
			$.each(map.defaultGraphics, function(index, grap) {
				map.graphics.add(grap);
				map.workingStorage.push(grap);
				var graphic = $.extend({}, grap);
				var symbol = graphic.symbol,
					ee = symbol.rule;
				if (ee && ee.isShow == "true" && (ee.isSort == "true" || ee.showColumn) && grap.geometry.type == "point") {
					var text = new jsgisMap.esri.symbol.TextSymbol(ee.isSort == "true" ? count++ : (symbol.data[ee.showColumn] || ""));
					text.rule = symbol.rule;
					text.data = symbol.data;
					text.column = symbol.column;
					text.url = text.url;
					if (ee.sortSize) {
						var font = new map.esri.symbol.Font();
						font.setSize(ee.sortSize);
						text.setFont(font);
					}
					if (ee.sortColor) {
						var color = new map.esri.Color(ee.sortColor);
						text.setColor(color);
					}
					var sortOffset = ee.sortOffset;
					if (sortOffset) {
						try {
							var offsets = sortOffset.split(",");
							text.setOffset(parseFloat(offsets[0]), parseFloat(offsets[1]));
						} catch (e) {
							console.log(e);
						}
					}
					var graphic = new map.esri.Graphic(graphic.geometry, text);
					graphic.visible = true;
					graphic.isText = true;
					map.graphics.add(graphic);
					map.workingStorage.push(graphic);
					map.prevStorage.push(graphic);
				}
			});
			if (mapCb) {
				mapCb.fire();
			}
		}
	},
	jsGisShowInfoWindow: function(map, geometry, title, url, width, height) {
		map.infoWindow.resize(width.replace(/px||%/gm, ""), parseInt(height.replace(/px||%/gm, "")) + 10);
		map.infoWindow.setTitle(title);
		map.infoWindow.setContent("<iframe frameborder='no' border='0' marginwidth='0' marginheight='0' src='" + url + "' style='width:100%;height:" + height + ";'></iframe>");
		map.infoWindow.show(geometry, map.getInfoWindowAnchor(geometry));
		var obj = {};
		obj.width = width.replace(/px||%/gm, "");
		obj.height = height.replace(/px||%/gm, "");
		return obj;
	},
	getCoord: function(rule, args) { //获取当前坐标信息
		jsGisFunc._jsGisDraw(args[0], args[1], args[2]);
	},
	clickEvent: function(map, e, clickEvent) { //注册点击事件
		var symbol = e.symbol || e.graphic.symbol,
			symdata = symbol.data,
			objs = [],
			ee = symbol.rule,
			key = jsGisFunc._convert(ee.exp, [symdata], 'key');
		if (eval(key) && symbol.column == ee.column) {
			if (ee.urlId) {
				var trul = getUrlToSplitter() + contextPath + "/mx/form/page/viewPage?id=" + ee.urlId + "&" + jsGisFunc._getParamName(ee, symdata, 'c');
				var point = clickEvent;
				if (!clickEvent) {
					point = e.screenPoint || e.geometry;
					if (point.type === "line" || point.type === "polyline") {
						point.x = point.paths[0][0][0];
						point.y = point.paths[0][0][1];
					}
				}
				var obj = jsGisFunc.jsGisShowInfoWindow(map, point, ee.windowName, trul, ee.windowWidth, ee.windowHeight);
				objs.push(obj);
			}
		}
		return objs;
	},
	_bindEventType: function($id, fnType, e) {
		var ary = $id.data(fnType);
		if (ary && ary.length) {
			$.each(ary, function(i, v) {
				v(e);
			})
		}
	},
	_bindEvent: function(map) {
		map.graphics.on("click", function(e) {
			jsGisFunc._bindEventType($("#" + map.id), "clickEventList", e);

			/*var win = jsGisFunc.clickEvent(map, e, eee),
				width = win[0] ? win[0].width : 0,
				height = win[0] ? win[0].height : 0,
				screen = map.toScreen(e.mapPoint); //当前点的坐标位置
			var offset = e.graphic.symbol.rule.offset || 0;
			screen.x = screen.x - (map.width / 2 - width - 80) + offset;
			screen.y = screen.y + (map.height / 2 - height - 80);
			map.centerAt(map.toMap(screen));*/

		});
		map.graphics.on("dbl-click", function(e) {
			jsGisFunc._bindEventType($("#" + map.id), "dbclickList", e);
		});
		map.graphics.on("mouse-over", function(e) {
			jsGisFunc._bindEventType($("#" + map.id), "mouseoverList", e);
		});
		map.graphics.on("mouse-up", function(e) {
			jsGisFunc._bindEventType($("#" + map.id), "mouseupList", e);
		});



	},
	initDataAndClick: function(map, eee, rid, coordInfo, url) {
		$.ajax({
			type: "POST",
			url: contextPath + "/mx/form/charts/datas",
			contentType: "application/json",
			dataType: "json",
			data: JSON.stringify({
				rid: rid
			}),
			success: function(data) {
				/*map.graphics.on("click", function(e) {
					var win = jsGisFunc.clickEvent(map, e, eee),
						width = win[0] ? win[0].width : 0,
						height = win[0] ? win[0].height : 0,
						screen = map.toScreen(e.mapPoint); //当前点的坐标位置
					var offset = e.graphic.symbol.rule.offset || 0;
					screen.x = screen.x - (map.width / 2 - width - 80) + offset;
					screen.y = screen.y + (map.height / 2 - height - 80);
					map.centerAt(map.toMap(screen));
				});*/
				jsGisFunc._bindEvent(map);
				jsGisFunc._init(map, data, eee, coordInfo, url);
				/*setTimeout(function() {
					if (mapCb) { //初始化 地图完毕后 执行事件
						mapCb.fire();
					}
				}, 500);*/
			},
			error: function(msg) {
				alert("数据获取错误");
			}
		});
	},
	_clearGis: function(workingStorage, jsgisMap, remit) { //清除上一次存储
		if (remit) {
			return;
		}
		//隐藏弹出窗口
		if (jsgisMap.infoWindow && jsgisMap.infoWindow.isShowing) {
			jsgisMap.infoWindow.hide();
		}
		if (workingStorage.length > 0) {
			clearInterval(jsgisMap.timer); //清楚闪烁定时任务
			for (var k = 0; k < workingStorage.length; k++) {
				var storageGraphics = workingStorage[k];
				var symbol = storageGraphics.symbol,
					rule = symbol.rule;
				if (rule && "true" != rule.isShow) { //初始化隐藏的 则移除
					//storageGraphics.hide();
					//jsgisMap.graphics.remove(storageGraphics);	
				}
				jsgisMap.graphics.remove(storageGraphics);
				var ptype = storageGraphics.geometry.type;

				if ("textsymbol" != symbol.type && (ptype == "line" || ptype == "polyline")) { // 如果是线条  还原回原来样式
					storageGraphics.symbol.color = new jsgisMap.BColor(rule.lineColor || ([255, 0, 0]));
					storageGraphics.symbol.width = rule.lineWidth;
					storageGraphics.draw();

					if (storageGraphics.isFlicker) {
						storageGraphics.isFlicker = false;
					}
				} else if ("textsymbol" == symbol.type) {
					//jsgisMap.graphics.remove(storageGraphics);
				}
			}
			workingStorage.length = 0;
		}
	},
	isExtis: function(symbol, r, pms, pmsAry) {
		return (symbol) && r.columnName && (symbol.column == r.columnName) && (symbol.data[r.columnName].split(",").length > 1 ? pms.indexOf(symbol.data[r.columnName]) != -1 : $.inArray(symbol.data[r.columnName], pmsAry) != -1);
	},
	_eventFunc: function(rule, params, args, callback, isClear) {
		var jsgisMap = jsGisFunc.getMap(rule),
			$id = pubsub.getJQObj(rule),
			workingStorage = jsgisMap.workingStorage,
			prevStorage = jsgisMap.prevStorage = [];
		if (!isClear) {
			jsGisFunc._clearGis(workingStorage, jsgisMap, params.remit);
		}
		var syrule, graphics = jsgisMap.strokeGraphics,
			pointGraphics = [] /*jsgisMap.graphics.graphics*/ ,
			_col = params["_col"],
			_col_p = params["_col_p"];
		for (var i = 0, il = graphics.length; i < il; i++) { // 获取定位的Graphic
			var currentGraphic = graphics[i],
				symbol = currentGraphic.symbol;
			for (var p in params) {
				if (p != "zoom" && p != "point" && p != "remit" && p != "_col" && p != "_col_p") {
					var r = jsGisFunc.getRule(rule, p),
						pms = params[p],
						pmsAry = (pms + "").split(",");
					if (jsGisFunc.isExtis(symbol, r, pms, pmsAry) && (!(_col && _col_p) || (_col && _col_p && (symbol["data"][_col] == _col_p)))) {
						//console.log(currentGraphic);
						workingStorage.push(currentGraphic);
						prevStorage.push(currentGraphic);
						if (currentGraphic.geometry.type == "point") {
							pointGraphics.push(currentGraphic);
						} else {
							jsgisMap.graphics.add(currentGraphic);
						}
						callback.call(this, currentGraphic, jsgisMap, symbol, args, pointGraphics);
					}
				}
			}
		}
		if (pointGraphics && pointGraphics.length) {
			$.each(pointGraphics, function(idex, currGrap) {
				jsgisMap.graphics.add(currGrap);
			})
		}
	},
	_flicker: function(jsgisMap) { //闪烁方法
		var workingStorage = jsgisMap.workingStorage;
		if (workingStorage.length > 0) {
			jsgisMap.timer = setInterval(function() {
				for (var k = 0; k < workingStorage.length; k++) {
					if (!workingStorage[k].isText && workingStorage[k].isFlicker) {
						if (workingStorage[k].visible) {
							workingStorage[k].hide();
						} else {
							workingStorage[k].show();
						}
					}
				}
			}, jsgisMap.flicker || 500);
		}
	},
	highlightingGis: function(rule, params, agrs) { //加粗 高亮显示 只适用于线
		jsGisFunc._eventFunc(rule, params, agrs, function(currentGraphic, jsgisMap, symbol) {
			var ptype = currentGraphic.geometry.type;
			if (ptype == "line" || ptype == "polyline") {
				syrule = currentGraphic.symbol.rule;
				currentGraphic.symbol.color = new jsgisMap.BColor(syrule.highlightColor || ([255, 0, 0]));
				currentGraphic.symbol.width = syrule.highlightWidth;
				currentGraphic.draw();
			}
			currentGraphic.show();
			//jsgisMap.workingStorage.push(currentGraphic);
		});
	},
	flickerGis: function(rule, params, agrs) { //闪烁
		var jsgisMap = jsGisFunc.getMap(rule);
		jsGisFunc._eventFunc(rule, params, agrs, function(currentGraphic, jsgisMap, symbol) {
			currentGraphic.show();
			currentGraphic.isFlicker = true;
			//jsgisMap.workingStorage.push(currentGraphic);
		});
		jsGisFunc._flicker(jsgisMap);
	},
	flickerAndHighlightingGis: function(rule, params, agrs) { //闪烁加粗
		var jsgisMap = jsGisFunc.getMap(rule);
		jsGisFunc._eventFunc(rule, params, agrs, function(currentGraphic, jsgisMap, symbol) {
			var ptype = currentGraphic.geometry.type;
			if (ptype == "line" || ptype == "polyline") {
				syrule = currentGraphic.symbol.rule;
				currentGraphic.symbol.color = new jsgisMap.BColor(syrule.highlightColor || ([255, 0, 0]));
				currentGraphic.symbol.width = syrule.highlightWidth;
				currentGraphic.draw();
			}
			currentGraphic.isFlicker = true;
			currentGraphic.show();
			//jsgisMap.workingStorage.push(currentGraphic);
		})
		jsGisFunc._flicker(jsgisMap);
	},
	showAndHideLastGis: function(rule, params, agrs) { //显示隐藏上一个图标
		$.each(jsgisMap.prevStorage, function(i, storageGraphics) {
			var symbol = storageGraphics.symbol,
				rule = symbol.rule;
			jsgisMap.graphics.remove(storageGraphics);
			jsgisMap.strokeGraphics.removeObj(storageGraphics);
			var ptype = storageGraphics.geometry.type;
			if ("textsymbol" != symbol.type && (ptype == "line" || ptype == "polyline")) { // 如果是线条  还原回原来样式
				storageGraphics.symbol.color = new jsgisMap.BColor(rule.lineColor || ([255, 0, 0]));
				storageGraphics.symbol.width = rule.lineWidth;
				storageGraphics.draw();
				if (storageGraphics.isFlicker) {
					storageGraphics.isFlicker = false;
				}
			}
		});
		jsgisMap.prevStorage.length = 0;
		jsGisFunc._eventFunc(rule, params, agrs, function(currentGraphic, jsgisMap, symbol) {
			if (currentGraphic.visible) {
				currentGraphic.hide();
			} else {
				currentGraphic.show();
				//jsgisMap.workingStorage.push(currentGraphic);
			}
		}, true);
	},
	showAndHideGis: function(rule, params, agrs) { //显示隐藏图标
		jsGisFunc._eventFunc(rule, params, agrs, function(currentGraphic, jsgisMap, symbol) {
			if (currentGraphic.visible) {
				currentGraphic.isFlicker = false;
				currentGraphic.hide();
			} else {
				currentGraphic.show();
				//jsgisMap.workingStorage.push(currentGraphic);
				//	var taxLotExtent = selectedTaxLot.geometry.getExtent();
				//	jsgisMap.setExtent(taxLotExtent);// 定位到选定Graphic
			}
		}, true);
	},
	hideGis: function(rule, params, agrs) { //隐藏图标
		jsGisFunc._eventFunc(rule, params, agrs, function(currentGraphic, jsgisMap, symbol) {
			if (currentGraphic.visible) {
				currentGraphic.isFlicker = false;
				currentGraphic.hide();
			}
		}, true);
	},
	locateAndShowWindow: function(rule, params, agrs) { //弹出窗口
		var $id = pubsub.getJQObj(rule),
			time = $id.data("isZoom") ? 1000 : 0;
		setTimeout(function() {
			jsGisFunc._eventFunc(rule, params, agrs, function(currentGraphic, jsgisMap, symbol, args) {
				var clickPoint = agrs && agrs.length > 1 && agrs[1] ? agrs[0].mapPoint : "";
				if (currentGraphic.visible == false) {
					currentGraphic.show();
				}
				//TODO 如果使用了放大则默认使用原来的点信息
				var objs = jsGisFunc.clickEvent(jsgisMap, currentGraphic, $id.data("isZoom") ? currentGraphic.geometry : clickPoint);
				var point = clickPoint;
				if (!clickPoint) {
					point = currentGraphic.geometry;
					if (point.type === "line" || point.type === "polyline") {
						point.x = point.paths[0][0][0];
						point.y = point.paths[0][0][1];
					}
				}
				//jsgisMap.centerAt(point);
				var screen = jsgisMap.toScreen(point),
					width = objs[0] ? objs[0].width : 0,
					height = objs[0] ? objs[0].height : 0,
					offset = symbol.rule.offset || 0;
				screen.x = screen.x - ($id.width() / 2 - width - 80) + offset;
				screen.y = screen.y + ($id.height() / 2 - height - 80);
				jsgisMap.centerAt(jsgisMap.toMap(screen));
			}, true);
			$id.data("isZoom", false);
		}, time);

	},
	showAndHideOtherGis: function(rule, params, agrs) { //显示自己并隐藏其他
		$id = pubsub.getJQObj(rule);
		var jsgisMap = jsGisFunc.getMap(rule),
			graphics = jsgisMap.graphics.graphics;
		var workingStorage = jsgisMap.workingStorage;
		jsGisFunc._clearGis(workingStorage, jsgisMap, params.remit);
		for (var i = 0, il = graphics.length; i < il; i++) { // 获取定位的Graphic
			var currentGraphic = graphics[i];
			currentGraphic.hide();
		}
		jsgisMap.count = 1;
		jsGisFunc._eventFunc(rule, params, agrs, function(currentGraphic, jsgisMap, symbol, args, pointGraphics) {
			var krule = symbol.rule;
			// 显示图标序号
			var ptype = currentGraphic.geometry.type;
			currentGraphic.show();
			if (krule && (krule.isSort == "true" || krule.showColumn) && ptype != "line" && ptype != "polyline") {
				var text = new jsgisMap.esri.symbol.TextSymbol(krule.isSort == "true" ? jsgisMap.count++ : (symbol.data[krule.showColumn] || ""));
				text.rule = symbol.rule;
				text.rules = symbol.rules;
				text.data = symbol.data;
				text.column = symbol.column;
				text.url = text.url;
				if (krule.sortSize) {
					var font = new jsgisMap.esri.symbol.Font();
					font.setSize(krule.sortSize);
					text.setFont(font);
				}
				if (krule.sortColor) {
					var color = new jsgisMap.esri.Color(krule.sortColor);
					text.setColor(color);
				}
				var sortOffset = krule.sortOffset;
				if (sortOffset) {
					try {
						var offsets = sortOffset.split(",");
						text.setOffset(parseFloat(offsets[0]), parseFloat(offsets[1]));
					} catch (e) {
						console.log(e);
					}
				}
				var graphic = new jsgisMap.esri.Graphic(currentGraphic.geometry, text);
				graphic.visible = true;
				graphic.isText = true;
				//jsgisMap.graphics.add(graphic);
				pointGraphics.push(graphic);
				jsgisMap.workingStorage.push(graphic);
				jsgisMap.prevStorage.push(graphic);
			}
		});
	},
	zoomGis: function(rule, params, agrs) { //放大地图
		$id = pubsub.getJQObj(rule);
		$id.data("isZoom", true);
		var jsgisMap = jsGisFunc.getMap(rule),
			zoom = params["zoom"] ? params["zoom"] : 8;
		for (var p in params) {
			if (p != "zoom" && p != "point") {
				var r = jsGisFunc.getRule(rule, p),
					pms = params[p],
					pmsAry = (pms + "").split(",");
				for (var i = 0, il = jsgisMap.graphics.graphics.length; i < il; i++) { // 获取定位的Graphic
					var currentGraphic = jsgisMap.graphics.graphics[i],
						symbol = currentGraphic.symbol;
					if ((symbol) && (symbol.column == r.columnName) && (symbol.data[r.columnName].split(",").length > 1 ? pms.indexOf(symbol.data[r.columnName]) != -1 : $.inArray(symbol.data[r.columnName], pmsAry) != -1)) {
						var point = currentGraphic.geometry;
						if (point.type === "line" || point.type === "polyline") {
							var extentCenter = point.getExtent().getCenter();
							point.x = extentCenter.x;
							point.y = extentCenter.y;
							//point.x = point.paths[0][0][0];
							//point.y = point.paths[0][0][1];
						}
						jsgisMap.centerAt(point);
						setTimeout(function() {
							jsgisMap.setZoom(zoom);
						}, 300);
					}
				}
			}
		}
	},
	previewGis: function(rule, params, agrs) { //预览
		var map = jsGisFunc.getMap(rule);
		var findTask = new esri.tasks.FindTask(map.jsgisUrl);
		var findParams = new esri.tasks.FindParameters();
		findParams.contains = false;
		findParams.returnGeometry = true;
		map.graphics.clear()
		for (var p in params) {
			var layerSerch = params[p].split("-");
			if (layerSerch.length == 2) {
				findParams.layerIds = [parseInt(layerSerch[0])];
				findParams.searchFields = ["FID"];
				findParams.searchText = parseInt(layerSerch[1]); // 获取查询条件
				findTask.execute(findParams, function(results) {
					var symbol, ptype, res, graphic;
					for (var i = 0, il = results.length; i < il; i++) {
						res = results[i];
						graphic = res.feature; // 以下为高亮显示查询结果
						ptype = graphic.geometry.type;
						if (ptype == "point") {
							symbol = new esri.symbol.SimpleMarkerSymbol();
							symbol.setColor(new esri.Color([255, 0, 0, 0.5]));
						} else if (ptype == "polyline") {
							symbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new esri.Color([255, 0, 0]), 2);
						}
						graphic.setSymbol(symbol);
						graphic.visible = true;
						map.graphics.add(graphic);
					}
				});
			}
		}
	},
	initExtent: function(rule, params) { //还原初始化位置
		var jsgisMap = jsGisFunc.getMap(rule);
		jsgisMap.setExtent(jsgisMap.Extent(jsgisMap.initExtent));
	},
	hideWindow: function(rule, params) { //隐藏弹出窗口
		var jsgisMap = jsGisFunc.getMap(rule);
		jsgisMap.infoWindow.hide();
	},
	getRule: function(rule, param) {
		for (var i = 0; i < rule.length; i++) {
			var r = rule[i];
			if (r.param == param) {
				return r;
			}
		}
	},
	getMap: function(rs, isIn) {
		if (typeof rs == "object") {
			var rules = [];
			if ($.isArray(rs)) {
				rules = rs;
			} else {
				rules.push(rs);
			}
			var that = pubsub.getThat(rules, isIn);
			var rule = rules[0],
				id = isIn ? rule.inid : rule.outid;
			if (that) {
				return that.eval(id + "Map");
			}
		}
		return eval(rs + "Map");
	}
};
pubsub.sub("jsgis", jsGisFunc);/**
 * 列表视图
 */
var listviewFunc = {
	getRow: function(rule) { // 获取选中行的字段
		// var targer = jQuery('#' + rule.inid);
		var targer = pubsub.getJQObj(rule, true);
		var widget = kendo.widgetInstance(targer);
		var value = [];
		if (widget) {
			var rows = widget.select();
			for (var i = 0; i < rows.length; i++) {
				var data = widget.dataItem(rows[i]);
				value.push(data[rule.columnName]);
			}
		}
		if (targer.attr("localfile")=="true") {
			var rdata = widget.dataSource.transport.options.read.data,params,databaseId = 0;
			if(rdata.params){
				params = JSON.parse(rdata.params);
				databaseId = params.params || 0 ;
			}
			var ids = "" ;
			$.ajax({
				url: contextPath + "/mx/form/imageUpload?method=localFile&databaseId=" + databaseId + "&id=" + rdata.rid + "&rp=" + value.join(","),
				async : false ,
				dataType : 'json' ,
				success: function(ret) {
					console.log(ret);
					ids = ret.ids;
				}
			});
			return ids ;
		}
		return value.join(",");
	},
	linkage: function(rule, params) {
		gridFunc.linkageControl(rule, params);
	},
	linkageControl: function(id, params) {
		var targer = pubsub.getJQObj(id);
		var widget = kendo.widgetInstance(targer);
		var data = widget.dataSource.transport.options.read.data;
		data.params = JSON.stringify(params);
		widget.dataSource.read();
		// 传入创建父ID
		/*
		 * var createData = widget.dataSource.transport.options.create.data;
		 * createData.params = JSON.stringify(params);
		 */
	},
	grefresh: function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			var widget = kendo.widgetInstance($id);
			widget.dataSource.read();
		}
	},
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.closest('.listview_div').hide();
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.closest('.listview_div').show();
	},
};
pubsub.sub("listview", listviewFunc);/**
 * 多选下拉框
 */
var multiselectFunc = {
	getValue : function(rule, args) {
		var v = pubsub.getJQObj(rule, true).val();
		if (v) {
			return v.join(",");
		}
		return null;
	},
	setValue : function(rule, args) {
		var $id = pubsub.getJQObj(rule), r, v = "";
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param];
		}
		kendo.widgetInstance($id).value(v.split(","));
	},
	thidden : function(rule, args) {// 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.closest('.k-widget').hide();
	},
	tshow : function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.closest('.k-widget').show();
	},
	tdisabled : function(rule, args) {// 禁用
		kendo.widgetInstance(pubsub.getJQObj(rule)).enable(false);
	},
	tenabled : function(rule, args) {// 启用
		kendo.widgetInstance(pubsub.getJQObj(rule)).enable(true);
	},
};
pubsub.sub("multiselect", multiselectFunc);var pageFunc = {
	getValue : function(rule, args) {
		if (mtxx.params) {
			return mtxx.params[rule.inparam];
		}
		return null;
	},
	getRow : function(rule, args) {
		var targer = pubsub.getJQObj(rule);
		var params = targer.data("params");
		if (params) {
			return params.value;
		}
		//获取不到则从页面数据中获取
		var params = eval("(" + pageParameters + ")"),idatasetId = rule.idatasetId || rule.indatasetId;
		if (idatasetId) {
			var dataSources = params["detail"]["dataSources"];
			if (!$.isEmptyObject(dataSources) && dataSources[idatasetId]) {
				return dataSources[idatasetId][rule.columnName] || '';
			}
		} else {
			return params[rule.columnName] || (window.callbackParam && window.callbackParam[rule.columnName]) || '';
		}
		return "";
	},
	linkageControl : function(rule, params) {
		pageFunc.linkagePage(rule, params);
	},
	linkagePage : function(rule, params) {
		params.newWin = '1';
		var p = pubsub.paramsToStr(params), id = rule;
		if (typeof rule == 'object') {
			id = rule[0].outid;
		}
		$("iframe[src*='" + id + "'],iframe[prosrc*='" + id + "']").each(
				function(i) {
					$this = $(this);
					if (!$this.attr('prosrc')) {
						$this.attr('prosrc', $this.attr('src'));
					}
					$this.attr('src', $this.attr('prosrc') + p);
				});
	},
	newWindow : function(id, params, args) { // 弹出窗口
		// args[0]
		// 格式{link:{url:'xxxx',name:'图片链接',id:'xx'},model:true,title:'标题',jumpType:'singlePage',width:'200px',height:'100px'}
		var obj = args[0];
		if (obj && obj.jumpType) {
			var p = pubsub.paramsToStr(params); // 参数
			var path;
			var f = "";
			if (obj.slink) { // charts 查看原始数据专用
				if (obj.fillform) {
					f = "&fillform=" + obj.fillform;
				}
				/*
				 * if(obj.slink.startsWith("/mx")){ obj.slink = contextPath +
				 * obj.slink ; } path = obj.slink + "?" + p + f
				 */;
			}
			var link = JSON.parse(obj.link), url = link.url;
			path = contextPath + url + ((url.indexOf("?") > -1) ? "" : "?") + p
					+ f;
			if (obj.jumpType == 'singlePage') {
				window.open(path);
			} else {
				var iTop = (window.screen.availHeight - 30 - eval(obj.height
						.replace('px', ''))) / 2; // 获得窗口的垂直位置;
				var iLeft = (window.screen.availWidth - 10 - eval(obj.width
						.replace('px', ''))) / 2; // 获得窗口的水平位置;
				if (obj.model == "true") {
					window.open(path, obj.title, "width=" + obj.width
							+ ",height=" + obj.height + ",top=" + iTop
							+ ",left=" + iLeft + ", modal=true,status=no");
				} else {
					var kendoWindow = $('#mt-popwindow').data("kendoWindow")
							|| $("<div id='mt-popwindow'></div>").kendoWindow({
								modal : obj.model,
								title : obj.title,
								width : obj.width,
								height : obj.height,
								/*
								 * position : { top : iTop, left : iLeft },
								 */
								iframe : true
							}).data("kendoWindow");
					kendoWindow.refresh({
						url : path,
					});
					kendoWindow.open();
					kendoWindow.center();
					if (obj.maximize == "true") {
						kendoWindow.maximize();
					}
				}
			}
		}else{ //事件定义器中打开页面
			var p = pubsub.paramsToStr(params,['isMax','width','height','singlePage','model','title']), // 参数
				url = id[0].srcUrl ,
				path = contextPath + url + ((url.indexOf("?") > -1) ? "" : "?") + p;
			if(!params.singlePage){
				window.open(path);
			} else {
				var iTop = (window.screen.availHeight - 30 - eval((params.height+"").replace('px', ''))) / 2; // 获得窗口的垂直位置;
				var iLeft = (window.screen.availWidth - 10 - eval((params.width+"").replace('px', ''))) / 2; // 获得窗口的水平位置;
				if (params.model) {
					window.open(path, params.title, "width=" + params.width
							+ ",height=" + params.height + ",top=" + iTop
							+ ",left=" + iLeft + ", modal=true,status=no");
				} else {
					var kendoWindow = $('#mt-popwindow').data("kendoWindow")
							|| $("<div id='mt-popwindow'></div>").kendoWindow({
								modal : params.model,
								title : params.title,
								width : params.width,
								height : params.height,
								iframe : true
							}).data("kendoWindow");
					kendoWindow.refresh({
						url : path,
					});
					kendoWindow.open();
					kendoWindow.center();
					if (params.isMax) {
						kendoWindow.maximize();
					}
				}
			}
		}
	},
	pageClose : function(rule, args) {
		var that = pubsub.getThat(rule);
		if (that.top.location.href == that.location.href) {
			that.close();
		} else {
			var kendoWindows = that.parent.$('[data-role=window]');
			kendoWindows.each(function(i, k) {
				var tw = that.parent.$(k).data('kendoWindow');
				if (tw.content().indexOf(rule.outid) != -1)
					tw.close();
			});
		}
	},
	grefresh : function(rule, args) {
		var that = pubsub.getThat(rule);
		// that.location.href = that.location.href ;
		that.location.reload();
	},
	/**
	 * 保存操作
	 * 
	 * @param rule
	 * @param args
	 */
	mtSave : function(rule, args) {
		var msg = $(document.body).data("saveMsg");
		if(msg){
			//btn.save();
			$.each(msg, function(i, btn){
				if(btn && btn.save){
					btn.save();
				}
			});
		}
	},
	/**
	 * 登录操作
	 * 
	 * @param rule
	 * @param args
	 */
	mtLogin : function(rule,params,args) {
		handleLogin(rule,params,args);
	},
	/**
	 * 流程提交
	 * 
	 * @param rule
	 * @param args
	 */
	mtSubmit : function(rule, args) {
		if(window.mtxx && window.mtxx.params){
			if(confirm("你确定提交吗?")){
				var pageId = mtxx.params.id, p = $.extend(true, {}, args, {
					approve : true
				}), id = $("." + pageId).attr("idValue");
				var data = {
						pageId : pageId,
				};
				data.flowParams = JSON.stringify(p);
				data[pageId] = id;
				$.ajax({
					url : contextPath + "/mx/form/workflow/defined/submit",
					type : "POST",
					dataType : "JSON",
					data : data,
					success : function(ret){
						alert("操作成功!");
					}
				});
			}
		}
	},
	/**
	 * 流程撤回
	 */
	mtReject : function(rule, args){
		FlowProcess.reject(args);
	},
	/**
	 * 流程终止
	 */
	mtCancel : function(rule, args){
		FlowProcess.cancel(args);
	},
	/**
	 * 挂起
	 */
	mtStop : function(rule, args){
		FlowProcess.stop(args);
	},
	/**
	 * 激活
	 */
	mtActive : function(rule, args){
		FlowProcess.active(args);
	},
	/**
	 * 下发
	 */
	sendDown : function(rule,args){
		
	}
};
pubsub.sub("page", pageFunc);/**
 * 进度条
 */
var progressbarFunc = {
	setValue : function(rule, args) {
		var $id = pubsub.getJQObj(rule), r, v;
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param];
		}
		if (v) {
			kendo.widgetInstance($id).value(v);
		}
	},
};
pubsub.sub("progressbar", progressbarFunc);/**
 * 单选按钮
 */
var radioFunc = {
	getValue: function(rule, args) {
		return pubsub.getJQObj(rule, true).val();
	},
	isChecked: function(rule, args) {
		return pubsub.getJQObj(rule, true).is(":checked");
	},
	getSelect: function(rule, args) {
		var t = pubsub.getJQObj(rule, true);
		var s = t.parents("body").find("input[name=" + t.attr("name") + "]:checked");
		return s.val();
	},
	selectNode: function(rule, params, args) {
		var t = pubsub.getJQObj(rule);
		var s = t.parents("body").find("input[name=" + t.attr("name") + "]");
		s.prop("checked", false);
		$.each(s, function(i, v) {
			var $ele = $(v);
			for (var p in params) {
				if ($ele.val() == params[p]) {
					$ele.prop("checked", true);
				}
			}
		});
	}
};
pubsub.sub("radio", radioFunc);

var checkboxFunc = $.extend(true, {}, radioFunc, {
	selectNode: function(rule, params, args) {
		var t = pubsub.getJQObj(rule);
		var s = t.parents("body").find("input[name=" + t.attr("name") + "]");
		s.prop("checked", false);
		$.each(s, function(i, v) {
			var $ele = $(v);
			for (var p in params) {
				var param = params[p];
				if (param) {
					var ps = param.toString().split(",");
					$.each(ps, function(j, k) {
						if ($ele.val() == k) {
							$ele.prop("checked", true);
						}
					})
				}
			}
		});
	}
});

pubsub.sub("checkbox", checkboxFunc);/**
 * 选卡
 */
var tabstripFunc = {
	thidden: function(rule, args) { // 隐藏
		pubsub.getJQObj(rule).hide().closest(".k-tabstrip-wrapper").hide();
	},
	tshow: function(rule, args) { // 显示
		pubsub.getJQObj(rule).show().closest(".k-tabstrip-wrapper").show();
	},
	activateTab:function(rule,params,args){
		for(var key in params){
			pubsub.getJQObj(rule).data("kendoTabStrip").select(params[key]);	
		}
	}
};
pubsub.sub("tabstrip", tabstripFunc);
var glafTabstrip = {
	initHeight: function(e) {
		var dd = $(this.wrapper),
			ts = dd.data("kendoTabStrip"),ismodel = dd.attr("model");

		options = this.options, tabPosition = options.tabPosition, flag = (tabPosition == 'left' || tabPosition == 'right') ? true : false;
		if (options.innerWidth) {
			$(e.contentElement).width(options.innerWidth);
		}
		if (options.innerHeight) {
			$(e.contentElement).height(options.innerHeight);
		} else {
			$(e.contentElement).height(dd.innerHeight() - (flag ? 0 : dd.children(".k-tabstrip-items").outerHeight()) - 16);
		}
		if (kendo.support.browser.msie && flag /*&& !ismodel*/) { // 用于适配IE浏览器问题
			//if ($("html").hasClass("dj_contentbox")) {
			//	dd.children(".k-tabstrip-items").css("font-size", "74%");
			//}
			var ow = $(e.contentElement).hasClass('k-state-active') ? 0 : $(e.contentElement).outerWidth();
			var iow = dd.children(".k-tabstrip-items").outerWidth(); // 适配隐藏tab导致的
			if (dd.is(':hidden')) { // 如果是隐藏
				iow = 70;
			}
			dd.width(ow + iow + 3);
		}
		var iframe = $(e.contentElement).find('iframe');
		setTimeout(function() { // 延迟嵌套问题
			if (!iframe.attr('src')) {
				iframe.attr('src', iframe.attr('prosrc'));
				iframe.load(function() {
					$(this).data("onReadyState", true);
					if (ts._events.activate) {
						var acts = ts._events.activate,
							actsLength = acts.length;
						for (var i = 0; i < actsLength; i++) {
							acts[i](e);
						}
					}
				});
			}
		}, 350);
	}
};var textboxFunc = {
	getValue: function(rule, args) {
		var $dom = pubsub.getJQObj(rule, true);
		return $dom.val() || $dom.attr("defaultval");
	},
	setValue: function(rule, args) {
		var $id = pubsub.getJQObj(rule),
			r, v = "";
		$id.attr("setValue", "setValue");
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param];
			}
			$id.val(v);
		} else {
			$id.val(args);
		}
	},
	memorySetValue: function(rule, args) {
		var $id = ( pubsub.getThat(rule) && pubsub.getThat(rule).memoryObj ) || pubsub.getJQObj(rule),
			r, v = "";
		$id.attr("memorySetValue", "memorySetValue");
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param];
			}
			$id.val(v);
		} else {
			$id.val(args);
		}
	},
	setValueByName: function(rule, args) {
		var $id = pubsub.getJQObj(rule),
			r, v = "",
			columnname = $id.attr("columnname"),
			name = $id.attr("name"),
			$target = $id.parents("body").find("[" + (columnname ? "columnname" : "name") + "=" + (columnname ? columnname : name) + "]");
		$target.attr("setValueByName", "setValueByName");
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param];
			}
			$target.val(v);
		} else {
			$target.val(args);
		}
	},
	appendSetValue: function(rule, args) {
		var $id = pubsub.getJQObj(rule),
			r, v = "",
			bval = $id.val() ? $id.val() + "," : "";
		$id.attr("setValue", "setValue");
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param];
			}
			$id.val(bval + v);
		} else {
			$id.val(bval + args);
		}
	},
	setValueByDynamic: function(rule, args) { //变长区动态公式
		var $id = pubsub.getJQObj(rule),
			r, v = "";
		$id.attr("setValueByDynamic", "setValueByDynamic");
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param];
			}
			$id.val(v);
		} else {
			$id.val(args);
		}
	},
	tclear: function(rule, args) {
		pubsub.getJQObj(rule).attr("tclear", "tclear").val("");
	},
	cellCriterion: function(rule, args) {
		var $this = pubsub.getJQObj(rule);
		console.log(rule);
		if (args) {
			switch (args.type) {
				case "cri":

					break;
				case "cri_params":

					break;
				case "stat":

					break;
				default:
					break;
			}
		}
	}
};
pubsub.sub("textbox", textboxFunc);/**
 * 上传
 */
var uploadFunc = {
	init : function(rule, args) {
		var options = args[0];
		$("#" + rule.inid).kendoUpload(options);
	}
}
pubsub.sub("upload", uploadFunc);/**
 * 
 */
var videoFunc = {
	getRow : function(rule, args) {
		return null;
	},
	linkageControl : function(id, params) {
		var $this = $("#" + id) ;
		var drole = $this.attr('drole');
		if(drole && "ys"==drole){ //萤石云模式
			$this.ysVideo("link",{params:params});
		}else{
			params = params || {};
			params.rid = $this.attr("rid");
			$.ajax({
				url : contextPath + '/mx/form/data/getVideoSet',
				data : params,
				dataType : "JSON",
				async : false,
				success : function(data) {
					if (data && data[0]) {
						loginNetVideo(data[0]);
					}
				}
			});
		}
	}
};
pubsub.sub("video", videoFunc);/**
 * 图片轮播
 */
var yscrollFunc = {
	linkage : function(rule, params) {
		yscrollFunc.linkageControl(rule, params);
	},
	linkageControl : function(id, params) {
		var target = pubsub.getJQObj(id);
		var opts = target.data("opts");
		if(opts && opts.params){
			if(params.databaseId){
				target.data("databaseId",params.databaseId);
			}
			opts.params.params = JSON.stringify(params) ;
			target.yScroll("init",opts);
		}else{
			target.data("params",params);
		}
	},
}
pubsub.sub("yscroll", yscrollFunc);var ztreeFunc = {
	getRow: function(rule, args) {
		//if (args && args.length > 1) {
		//	return args[1][rule.columnName];
		//} else {
			var $id = pubsub.getJQObj(rule, true);
			if (!$id) {
				$id = pubsub.getJQObj(rule.inid, true);
			}
			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
			var nodes = ztreeObj.getSelectedNodes();
			nodes.length || (nodes = ztreeObj.getCheckedNodes());
			var retVal = "" ;
			if (nodes.length) {
				for(var i=0,len = nodes.length;i<len;i++){
					retVal += nodes[i][rule.columnName]||"";
					retVal += (i==len-1)?"":",";
				}
				//return nodes[0][rule.columnName];
			}
			return retVal ;
		//}
	},
	linkage:function(rule,args){
		ztreeFunc.linkageControl(rule, args);
	},
	linkageControl: function(id, params) {
		var $id = pubsub.getJQObj(id);
		if ($id) {
			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
			var otherParam = ztreeObj.setting.async.otherParam;
			otherParam.params = JSON.stringify(params);
			/*
			 * for(var p in params){ otherParam[p] = params[p]; }
			 */
			ztreeObj.setting.async.otherParam = otherParam;
			setTimeout(function(){
				if($id.data("isloaded")){
						ztreeObj.reAsyncChildNodes(null, "refresh");
				}else{
						$id.data("params",params);
				}	
			}, 100);
		}
		/*
		 * view: { fontCss: getFontCss } function getFontCss(treeId, treeNode) {
		 * return (!!treeNode.highlight) ? {color:"#A60000",
		 * "font-weight":"bold"} : {color:"#333", "font-weight":"normal"}; }
		 */
	},
	_buildRetAry: function(nodes, retAry, rule) {
		var node, nodesLength = nodes.length;
		for (var i = 0; i < nodesLength; i++) {
			node = nodes[i];
			if (node[rule.columnName]) {
				retAry.push(node[rule.columnName]);
			}
			if (node.children) {
				ztreeFunc._buildRetAry(node.children, retAry, rule);
			}
		}
	},
	getAll: function(rule, args) {
		var $id = pubsub.getJQObj(rule, true);
		if ($id) {
			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
			var nodes = ztreeObj.getNodes();
			var retAry = [];
			if (nodes) {
				ztreeFunc._buildRetAry(nodes, retAry, rule);
			}
			return retAry.join(",");
		}
		// rule.columnName
	},
	thidden: function(rule, args) { // 隐藏
		pubsub.getJQObj(rule).closest("div").hide();
	},
	tshow: function(rule, args) { // 显示
		pubsub.getJQObj(rule).closest("div").show();
	},
	tSelectFirst: function(rule, args) { //选中第一个节点
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id')),
				nodes = ztreeObj.getNodes();
			if (nodes && nodes.length > 0) {
				var o = nodes[0];
				ztreeObj.selectNode(o);
				ztreeObj.setting.callback.onClick(null, $id.attr('id'), o);
			}
			var mo = new MutationObserver(function(record) {
				nodes = ztreeObj.getNodes();
				if (nodes && nodes.length > 0) {
					var o = nodes[0];
					ztreeObj.selectNode(o);
					ztreeObj.setting.callback.onClick(null, $id.attr('id'), o);
				}
				mo.disconnect();
			});
			mo.observe($id[0], {
				'childList': true,
				'arrtibutes': true,
				'subtree': true,
				'characterData': true
			});
		}
	},
	cancelSelectNodes: function(rule, args){//取消选中节点
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
			ztreeObj.cancelSelectedNode();
			ztreeObj.checkAllNodes(false);
		}
	}
};
pubsub.sub("ztree", ztreeFunc);

//根据参数获取父节点
function getParentByParam(node, field, exp, outFiled, isDeep) {
	var pNode = node.getParentNode(),
		depVal, retVal = "";
	if (pNode) {
		if (eval("'" + pNode[field] + "'" + exp)) {
			retVal = pNode[outFiled];
			if (isDeep) {
				depVal = getParentByParam(pNode, field, exp, outFiled, isDeep);
				if (depVal)
					retVal = depVal;
			}
		} else {
			retVal = getParentByParam(pNode, field, exp, outFiled, isDeep);
		}
	}
	return retVal;
};
//根据参数获取子节点
function getChildByParam(node, field, exp, outFiled, isDeep) {
	var childrens = node.children;
	if (childrens && childrens.length) {
		var children, retVal = "",
			childVal;
		for (var i = 0; i < childrens.length; i++) {
			children = childrens[i];
			if (eval("'" + children[field] + "'" + exp)) {
				retVal = children[outFiled];
				if(!isDeep){
					return retVal ;
				}
			} else {
				childVal = getChildByParam(children, field, exp, outFiled,isDeep);
				if (childVal) {
					retVal = childVal;
				}
			}
		}
		return retVal;
	}
	return "";
};
//往上查找节点
function getTreeUpNode(args, field, exp, outFiled, isDeep) {
	try {
		return getParentByParam(args[1], field, exp, outFiled, isDeep);
	} catch (e) {
		console.log(e);
	}
	return "";
};
//往下查找节点
function getTreeDownNode(args, field, exp, outFiled, isDeep) {
	try {
		return getChildByParam(args[1], field, exp, outFiled, isDeep);
	} catch (e) {
		console.log(e);
	}
	return "";
};
//获取树节点
function getTreeNode(trigger, args, level, outFiled) {
	var node = args[1];
	if (level == 0) {
		//k.getPreNode() k.getNextNode()
		var node = node.getParentNode();
	}
	/*var rule = {
		inid: trigger.eleId,
		inpage: trigger.pageId,
		inlev: trigger.level
	};
	var $id = pubsub.getJQObj(rule, true);
	try {
		if ($id) {
			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
			//ztreeObj.
			var node = ztreeObj.getNodesByFilter(function(node) {
				return eval("'" + node[field] + "'" + exp);
			}, true, args[1]);
			if (node) {
				return node[outFiled];
			}
		}
	} catch (e) {
		console.log(e);
	}
	return "";*/
};
// ********************************** ztree相关
// *****************************************************************
// event:标准事件;treeId：树ID;treeNode：点击的节点;
// clickFlag：点击模式;linkType:单击类型;ids:联动的对象ID集合
var glafZtree = {
	// treeId：树ID;treeNode：拖拽的节点
	// ;targetNode：拖拽的目标节点;moveType："inner"：成为子节点，"prev"：成为同级前一个节点，"next"：成为同级后一个节点
	// isCopy:移动类型（复制/移动）;ruleId:规则id;
	beforeDrop: function(treeId, treeNodes, targetNode, moveType, isCopy, ruleId) {
		if (moveType == "null") {
			return false;
		} else {
			var treeNodeIds = [];
			for (var i = 0; i < treeNodes.length; i++) {
				var treeNode = treeNodes[i];
				treeNodeIds.push(treeNode.id);
			}
			var data = "treeNodeIds=" + treeNodeIds.join(",") + "&targetNodeId=" + (targetNode ? targetNode.id : "") + "&moveType=" + moveType + "&rid=" + ruleId + "&isCopy=" + isCopy;
			var flag = true;
			$.ajax({
				type: "POST",
				url: contextPath + '/mx/form/treeData/drop',
				data: data,
				async: false,
				success: function(msg) {},
				error: function() {
					alert("操作失败");
					flag = false
				}
			});
			return flag;
		}
	},
	// treeId：树ID;treeNode：节点对象 ;ruleId:规则id;
	beforeRemove: function(treeId, treeNode, ruleId) {
		if (treeNode.children && treeNode.children.length > 0) {
			alert('有子节点不能删除');
			return false;
		}
		if (confirm("确认删除 节点<" + treeNode.name + ">吗？")) {
			var data = "treeNodeIds=" + treeNode.id + "&rid=" + ruleId;
			var flag = true;
			$.ajax({
				type: "POST",
				url: contextPath + '/mx/form/treeData/remove',
				data: data,
				async: false,
				success: function(msg) {},
				error: function() {
					alert("操作失败");
					flag = false;
				}
			});
			return flag;
		} else {
			return false;
		}
	},
	// treeId：树ID;treeNode：节点对象 ;newName：新名称：isCancel:是否取消编辑;ruleId:规则id;
	beforeRename: function(treeId, treeNode, newName, isCancel, ruleId) {
		if (isCancel) {
			return true;
		}
		var data = "treeNodeId=" + treeNode.id + "&newName=" + newName + "&rid=" + ruleId;
		var flag = true;
		$.ajax({
			type: "POST",
			url: contextPath + '/mx/form/treeData/rename',
			data: data,
			async: false,
			success: function(msg) {},
			error: function() {
				alert("操作失败");
				flag = false;
			}
		});
		return flag;
	},
	// type：工具条类型;ztreeId：树ID;ruleId:规则id;
	ztreeToolbar: function(type, ztreeId, ruleId, editPanleAutoClose) {
		var treeObj = $.fn.zTree.getZTreeObj(ztreeId);
		var nodes;
		if (treeObj.setting.check.enable) {
			nodes = treeObj.getCheckedNodes(true);
		} else {
			nodes = treeObj.getSelectedNodes();
		}
		var link;
		switch (type) {
			case "add": // 增加同级
				if (nodes.length > 1) {
					alert('请选择一个节点');
					return;
				}
				var pidkey = "";
				if (nodes.length == 1) {
					pidkey = nodes[0].pidkey;
				}
				link = contextPath + '/mx/form/treeData/ztreeEdit?rid=' + ruleId + '&pid=' + pidkey + '&treeId=' + ztreeId + '&epac=' + editPanleAutoClose;
				break;
			case "next": // 增加子级
				if (!nodes || nodes.length <= 0) {
					alert('请选择节点');
					return;
				}
				if (nodes.length != 1) {
					alert('请选择一个节点');
					return;
				}
				link = contextPath + '/mx/form/treeData/ztreeEdit?rid=' + ruleId + '&pid=' + nodes[0].indexkey + '&treeId=' + ztreeId + '&epac=' + editPanleAutoClose;
				break;
			case "edit": // 编辑
				if (!nodes || nodes.length <= 0) {
					alert('请选择节点');
					return;
				}
				if (nodes.length != 1) {
					alert('请选择一个节点');
					return;
				}
				link = contextPath + '/mx/form/treeData/ztreeEdit?rid=' + ruleId + '&id=' + nodes[0].id + '&treeId=' + ztreeId + '&epac=' + editPanleAutoClose;
				break;
			case "del": // 删除
				if (!nodes || nodes.length <= 0) {
					alert('请选择节点');
					return;
				}
				if (!confirm("确认删除 节点 吗？")) {
					return;
				}
				var treeNodeIds = [];
				for (var i = 0; i < nodes.length; i++) {
					var treeNode = nodes[i];
					treeNodeIds.push(treeNode.id);
				}
				var data = "treeNodeIds=" + treeNodeIds.join(",") + "&rid=" + ruleId;
				$.ajax({
					type: "POST",
					url: contextPath + '/mx/form/treeData/remove',
					data: data,
					async: false,
					success: function(msg) {
						treeObj.reAsyncChildNodes(null, "refresh");
					},
					error: function() {
						alert("操作失败");
					}
				});
				return;
				break;
			default:
				alert('操作失败');
				break;
		}
		if (!link) {
			return;
		}
		$.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "增加/编辑节点",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['', ''],
			fadeIn: 100,
			area: ['400px', '300px'],
			iframe: {
				src: link
			}
		});
	},
	init:function(id,options){
		$.fn.zTree.init($('#'+id),options);
		if(options){
			$("#ztree_"+id+"_add").kendoButton({click:function(){glafZtree.ztreeToolbar('add',id,options.opts.ruleId,options.opts.editPanleAutoClose);}});
			$("#ztree_"+id+"_add_next").kendoButton({click:function(){glafZtree.ztreeToolbar('next',id,options.opts.ruleId,options.opts.editPanleAutoClose);}});
			$("#ztree_"+id+"_edit").kendoButton({click:function(){glafZtree.ztreeToolbar('edit',id,options.opts.ruleId,options.opts.editPanleAutoClose);}});
			$("#ztree_"+id+"_del").kendoButton({click:function(){glafZtree.ztreeToolbar('del',id,options.opts.ruleId,options.opts.editPanleAutoClose);}});
		}
	},
	convert: function(dataItem,rule){
		if(rule.icon && rule.icon.length){
			var icons = rule.icon ;
			for(var i=0;i<icons.length;i++){
				var icon = icons[i];
				if(icon.expression && eval("("+icon.expression.replace(/\\\"/g,"\"")+")")){
					dataItem[icon.type] = contextPath + icon.icon;
				}
			}
		}
		if(rule.check && rule.check.length){
			var checks = rule.check ;
			for(var i=0;i<checks.length;i++){
				var check = checks[i];
				if(check.expression && eval("("+check.expression.replace(/\\\"/g,"\"")+")")){
					if(check.type && check.type == "disabled"){
						dataItem["chkDisabled"] = true;	
					}else{
						dataItem["checked"] = true;
					}
				}
			}
		}
		if(rule.text && rule.text.length){
			var texts = rule.text ;
			for(var i=0;i<texts.length;i++){
				var text = texts[i];
				if(text.expression && eval("("+text.expression.replace(/\\\"/g,"\"")+")")){
					dataItem["name"] = new Function( "var dataItem = arguments[0];return \""+text.htmlVal.replace(/##/g,"\"")+"\";")(dataItem);	
				}
			}
		}
	}
};var roleList = {
	grid: 'kendoGrid',
	combobox: 'kendoComboBox'
};

function getKendoData(jq) {
	if (!jq || !jq[0]) {
		return null;
	}
	var dataRole = jq.attr('data-role');
	return {
		dataRole: dataRole || 'text',
		kendo: jq.data(roleList[dataRole])
	};
}

function kendoReload(jq, params) {
	if (!jq || !jq[0]) {
		return false;
	} else {
		jq.mtkendo('reload', params);
	}
}

function getKendoValue(jq, key) {
	if (!jq || !jq[0]) {
		return null;
	} else {
		return jq.mtkendo('getValue', key);
	}
}

function setKendoValue(jq, val) {
	if (!jq || !jq[0]) {
		return false;
	} else {
		jq.mtkendo('setValue', val);
	}
}

// 按钮事件 klaus.wang 2015-05-14
function kendoButtonFunc(viewModel) {
	if (!viewModel.buttonType) {
		return false;
	}
	var functions = viewModel.functions;
	if (!functions) {
		functions = new KendoButton(viewModel);
	}

	if ((viewModel.buttonType in functions)) {
		functions[viewModel.buttonType]();
	} else {
		alert(viewModel.buttonType + ' 方法未找到');
	}
}

function KendoButton(vm) {

	this.viewModel = vm;

	this.init();
}

KendoButton.prototype.genReport = function() {
	var rule = this.viewModel.data.rule;
	var selectReportTemplate = rule.selectReportTemplate;
	var opts = selectReportTemplate[0];
	var paraType = rule.paraType;
	var $id = this.viewModel.id;

	var reportParamsArr = [];
	if (paraType && paraType.length > 0) {
		reportParamsArr = paraType[0].datas[$id];
	}

	var reportParams = {};
	$.each(reportParamsArr, function(index, val) {
		reportParams[val.param] = $("#" + val.inid).val();
	});
	$.ajax({
		url: contextPath + '/mx/form/defined/getDatabaseInfo?databaseCode=' + opts.dbCode,
		type: 'post',
		dataType: 'JSON',
		success: function(datakk) {
			$.ajax({
				url: opts.generateReportUrl,
				type: 'post',
				dataType: 'jsonp',
				data: {
					data: JSON.stringify({
						ReportTemplateId: opts.id,
						ReportParameters: reportParams,
						key: datakk.key,
						content: datakk.content
					})
				},
				// data: '{"ReportTemplateId":"' + opts.id +
				// '","ReportParameters":' + JSON.stringify(reportParams) +
				// ',"key":"'+datakk.key+'",content":"'+datakk.content+'"}',
				success: function(data) {
					if (data.ResultCode == 1) {
						if (data.ResultCode) {
							var resultData = data.ResultData;

							var p = {};
							p.templateId = opts.id;
							p.reportId = resultData.ReportId;
							p.pageCount = resultData.PageCount;
							p.content = datakk.content;
							p.key = datakk.key;
							$.each(paraType[0].datas, function(index, val) {
								if (val[0].type == 'setValue') {
									$("#" + val[0].outid).val(p[val[0].inid]);
								}
							});
						}
					}
					alert(data.ResultMessage);
				}
			});
		}
	});
};

KendoButton.prototype.init = function() {
	this.viewModel.data = new Object();
	if (this.viewModel.handlecolumns)
		this.viewModel.data.tableMsg = eval('(' + this.viewModel.handlecolumns + ')');
	if (this.viewModel.ruleData) {
		this.viewModel.data.rule = eval('(' + this.viewModel.ruleData + ')');
	}
	this.viewModel.data.pageParameters = eval('(' + pageParameters + ')');
	this.viewModel.functions = this;
};

/**
 * 修改cell表
 */
KendoButton.prototype.edit_cell = function() {
	var K = this,
		editFunctions = K.editFuncs;
	jQuery.each(K.viewModel.data, function(id, v) {
		var $this = jQuery('#' + id),
			dataRole = K.kendoData($this).dataRole;
		if (editFunctions[dataRole]) {
			editFunctions[dataRole].call(K, $this);
		}
	});
};
KendoButton.prototype.editFuncs = {
	grid: function(jq) {
		var K = this,
			kendoData = K.kendoData(jq),
			grid = kendoData.kendo,
			rows = grid
			.select();
		if (rows && rows.length == 1) {
			var edit = $(rows[0]);
			if (edit && edit[0]) {
				grid.editRow(edit);
			}
		} else {
			alert('请选择一条记录!');
		}
	}
};

/**
 * 查询操作
 */
KendoButton.prototype.search = function() {
	var jq = jQuery(this.viewModel.change);

	var paraType = this.viewModel.data.rule.paraType,
		idRelation = {};
	if (paraType && paraType[0]) {
		var datas = paraType[0].datas;
		if (datas) {
			pubsub.pub("linkageControl", datas);
		}
	}

	/*
	 * if (jq[0]) jq.mtkendo('reload',{ paramType : 'query-button', prid :
	 * this.viewModel.prid, params : kendo.stringify(this.getSearchData()) });
	 */
};
// 查询方法
KendoButton.prototype.getSearchData = function() {
	if (this.viewModel.data) {
		jQuery.each(this.viewModel.data, function(id, json) {
			json.value = jQuery('#' + id).mtkendo('getValue', json.ColumnName);
		});
	}
	return this.viewModel.data;
};

/**
 * 删除操作
 */
KendoButton.prototype['delete'] = function() {
	var K = this,
		deleteFunctions = K.deleteFuncs;
	jQuery.each(K.viewModel.data.tableMsg, function(i, v) {
		var $this = jQuery('#' + v.id),
			dataRole = K.kendoData($this).dataRole;
		if (deleteFunctions[dataRole]) {
			deleteFunctions[dataRole].call(K, $this);
		}
	});
};
// 删除方法
KendoButton.prototype.deleteFuncs = {
	grid: function(jq) {
		var K = this,
			kendoData = K.kendoData(jq);
		var grid = kendoData.kendo;
		var rows = grid.select();
		if (rows && rows.length > 0) {
			if (K.viewModel.multiPlay == 'false') {
				if (rows.length > 1) {
					alert('请选择一条记录!');
					return false;
				}
			}
			if (confirm('你确定删除吗?')) {
				var models = new Array();
				jQuery.each(rows, function(i, item) {
					models.push(grid.dataItem(item));
				});

				var dataSource = grid.dataSource;
				$.each(models, function(i, dataItem) {
					dataSource.remove(dataItem);
					dataSource.sync();
				});
			}
		} else if (rows.length == 0) {
			alert('请选择!');
		}
	}
};

/**
 * 流程提交
 */
KendoButton.prototype.submit = function() {
	var that = this,
		data = that.viewModel.data;

	var process = {
		isDefined: function() {
			return true;
		},
		isUndefined: function() {
			alert('流程未定义!');
		}
	};
	if (data && data.rule) {
		var id = $("[fieldname=id]:hidden").val(),
			flow = mtxx.params.flow;
		var pageRuleId = $(".pageruleid").val(),
			params = {
				multiple: data.rule["multiple-flow"],
				rid: that.viewModel.prid,
				id: id,
				pageRuleId: pageRuleId,
				pageId: data.rule.pageId
			};
		if (objectISNotEmpty(flow)) {
			if (id) {
				var url = contextPath + "/mx/form/defined/ex/flowFeedback?" + $.param({
					fn: 'processGetData',
					pageId: data.rule.pageId
				});
				var win = jQuery.mt.window({
					id: pageRuleId,
					title: '流程审批',
					width: '750px',
					height: '350px',
					refresh: true,
					modal: true,
					content: {
						url: url,
						iframe: true
					},
					appendTo: 'body'
				});
				win.kendo.center();
			} else {
				alert("该流程未启动");
			}
		} else if (objectISNotEmpty(flow) || params.multiple) {
			var paraType = data.rule.paraType;
			if (paraType) {
				var flowParams = getParaType(paraType);
				if (flowParams) {
					if (!flowParams.isReady) {
						alert('流程参数不能为空!');
						return false;
					}
					params.flowParams = JSON.stringify(flowParams);
				}
			}

			var processData = null;
			if (!flow) {
				$.ajax({
					url: contextPath + "/mx/form/workflow/defined/getMultipleVariables",
					async: false,
					data: params,
					type: "POST",
					dataType: "JSON",
					success: function(bytes) {
						if (bytes && bytes[0]) {
							$.each(bytes, function(i, b) {
								var by = JSON.parse(b);
								if ((by = by[0]) && by.targets) {
									$.each(by.targets, function(i, v) {
										console.log(v);
										addVariables(v.eleId, v.taskId);
									});
								}
							});
							processData = processGetData() || {};
							$.extend(params, processData);
						}
						mtxx.params.flow = null;
					}
				});
			}

			if (confirm("你确定提交流程吗?")) {
				if (processData && !processData.id) {
					alert("请先保存!");
					return false;
				} else {
					$.post(contextPath + "/mx/form/workflow/defined/processSubmit",
						params,
						function(ret) {
							alert("流程提交成功! 流程实例ID为：" + ret.processInstanceId);
						}, "JSON");
				}
			}
		} else {
			process.isUndefined();
		}
	} else {
		process.isUndefined();
	}
};

function getParaType(paraType) {
	if (paraType && paraType[0]) {
		var datas = paraType[0].datas,
			params = {
				isReady: true
			},
			data, val;
		for (var key in datas) {
			if ((data = datas[key]) && data[0]) {
				$.each(data, function(i, v) {
					val = $("#" + v.inid).mtkendo('getValue', v.columnName);
					if (val) {
						params[v.param] = val;
					} else {
						params.isReady = false;
					}
				});
			}
		}
		return params;
	}
	return null;
}

/**
 * 判断对象是否为空
 */
function objectISNotEmpty(o) {
	if (o) {
		for (var key in o) {
			return true;
		}
	}
	return false;
}

function processCloseWin(id) {
	var win = $("#" + id);
	if (win[0]) {
		win.data("kendoWindow") ? win.data("kendoWindow").close() : null;
	}
}

/**
 * 获取流程定义参数
 */
function processGetData() {
	var id = $("[fieldname=id]:hidden").val(),
		flow = mtxx.params.flow;
	var pageRuleId = $(".pageruleid").val();
	var params = {
		id: id,
		pageRuleId: pageRuleId
	};
	if (flow.variables) {
		$.each(flow.variables, function(i, v) {
			if (v.datas) {
				var expActVal = v.datas.expActVal;
				var o = new RegExp("~M\{([^\\}]*)\}", "g").exec(expActVal);
				if (o && o[1]) {
					params[v.varname] = $("#" + o[1]).val();
					params[v.varcode] = $("#" + o[1]).val();
				}
			}
		});
	}
	return params;
}

/**
 * 保存操作
 */
KendoButton.prototype.save = function($alert) {
	var K = this,
		data = new Array(),
		tables = new Object();
	// 验证
	if (K.viewModel.saveValidate) {
		var valis = $("[valisave=true]"),
			vali, $vali, length = valis.length,
			i = 0,
			bol, rebol = true;
		if (valis && valis.length) {
			for (; i < length; i++) {
				vali = valis[i];
				$vali = $(vali);
				if ($vali.data("role") === "cell") {
					var spreadValidate = $vali.data("spreadValidate"),
						spread = $vali
						.data("spread"),
						sheet = spread.getActiveSheet(),
						inids;
					for (var pro in spreadValidate) {
						inids = pro.split("-");
						bol = sheet.getDataValidator(inids[0], inids[1])
							.isValid();
						if (!bol)
							rebol = false;
					}
				} else {
					bol = $(vali).data("kendoValidator").validate();
					if (!bol)
						rebol = false;
				}
			}
			if (!rebol)
				return false;
		}
		// var bol = $("[valisave=true]").data("kendoValidator").validate();
		// if (!bol)
		// return false;
	}
	/**
	 * jQuery.each(K.viewModel.data, function(id, v) { var $this = jQuery('#' +
	 * id), params = $this.data('params') || v;
	 * 
	 * params.value = $this.mtkendo('getValue', params.fieldName);
	 * 
	 * if(params.value){ data.push(params); }
	 * 
	 * 
	 * });
	 */
	var vmd = K.viewModel.data;
	if (!vmd.tableMsg) {
		alert('未保存设置!');
		return false;
	}
	jQuery.each(vmd.tableMsg, function(i, tableMsg) {
		var container = {
			dataSetId: tableMsg.dataSetId,
			idValue: tableMsg.idValue,
			table: tableMsg.table,
			columns: new Array(),
			batch: tableMsg.batch,
			wdataSet: tableMsg.wdataSet
		};
		if (tableMsg.columns) {
			$.each(tableMsg.columns, function(index, v) {

				var $this = jQuery('#' + v.id),
					params = $this.data('params') || {};

				if (params.idValue && !container.idValue) {
					container.idValue = params.idValue;
				}

				v.value = $this.mtkendo('getValue', v.fieldName);
				// console.log(v.value);

				// if(v.value){
				container.columns.push(v);
				// }
			});
		}

		var tables = $("." + container.table.tableName);
		if (tables.length) {
			tables.each(function(i, v) {
				var $this = $(v);
				container.columns.push({
					fieldName: $this.attr('fieldName'),
					value: $this.val()
				});
			});
		}

		if (container.columns.length > 0) {
			if (tableMsg.batch || tableMsg.cell) {
				if (window.batch) {
					if (container.wdataSet && container.wdataSet.id) {
						window.batch.getValue(container.columns = []);
						data.push(container);
					} else {
						window.batch.getValue(data);
					}
				}
			} else {
				data.push(container);
			}
		}
	});

	if (data.length > 0) {

		var s = true;

		if (window.saveFileToUrl) {
			s = saveFileToUrl(true);
		}

		if (s) {

			var msg = data[0];
			if (msg && msg.wdataSet && msg.wdataSet.id) { // 更新集保存方式
				var saveMsg = [];

				function to(m, collection) {
					var obj = {
						id: m.idValue
					};
					$.each(m.columns, function(i, v) {
						if (m.batch) {
							to(v, collection);
						} else {
							obj[v.fieldName] = v.value;
						}
					});
					if (!m.batch)
						collection.push(obj);
				}

				$.each(data, function(i, m) {
					var t = {
						id: m.wdataSet.id,
						table: m.table,
						datas: []
					};

					to(m, t.datas);

					saveMsg.push(t);
				});

				$.ajax({
					url: contextPath + '/mx/form/data/saveFormData',
					type: 'POST',
					data: {
						// relation: relation ? JSON.stringify(relation) :
						// relation,
						tableMsg: JSON.stringify(saveMsg)
					},
					dataType: 'JSON',
					success: successFunc
						/*
						 * function(ret) { if (ret && ret.message) { alert(ret.message);
						 * $.each(saves, function(i, msg) { if (wdataSetId && msg.id !==
						 * wdataSetId) { return false; } window.dynamicCellDataSet(msg);
						 * }); } }
						 */
				});

				return false;
			}

			/**
			 * 返回主键保存
			 */
			function eachTableMsg(i, v, data) {
				var tmsg = data[v.table.tableName];
				if (tmsg) {
					v.idValue = tmsg.idValue || tmsg;
					!$("input." + v.table.tableName)[0] && ($("<input>", {
						'class': v.table.tableName,
						type: 'hidden',
						fieldname: 'id',
						value: v.idValue
					}).appendTo("body"));

					var $idVal = $("." + mtxx.params.id);
					if (!$idVal.get(0)) {
						$idVal = $("<input>", {
							'class': mtxx.params.id,
							type: 'hidden',
						}).appendTo("body");
					}

					$idVal.attr({
						idValue: v.idValue
					});

				}
				if (v.cell && window.batch) {
					window.batch.refresh();
				}
			}

			/**
			 * 保存成功回调
			 */
			function successFunc(data) {
				if (data && data.message && !data.rst) {
					alert(data.message);
				} else {
					if (!$alert)
						alert('操作成功!');
					$.each(vmd.tableMsg, function(i, v) {
						eachTableMsg(i, v, data);
					});
					if ($alert && $.isFunction($alert)) {
						$alert.call(window, data);
					}
				}
			}

			var saveUrl = contextPath + '/mx/form/data/saveOrUpdateDetail';
			$.ajax({
				url: saveUrl,
				type: 'post',
				data: {
					tableMsg: JSON.stringify(data)
				},
				async: true,
				dataType: 'json',
				success: function(data) {
					if (data && data.message) {
						alert(data.message);
					} else {
						if (data) {
							successFunc(data);
						}
						$(K.viewModel.change).mtkendo('reload', {});

						if (vmd.rule && vmd.rule.closeWindow == 'true') {
							K.closeWindow();
						}
					}
				}
			});
		}
	} else {
		// alert('请输入!');
	}
};

/**
 * 新建窗口
 */
KendoButton.prototype.newWindow = function(type) {
	var K = this,
		id = 'mtWindow_' + K.viewModel.id,
		rule = K.viewModel.data.rule,
		maximize = K.viewModel.maximize;
	var jumpType = rule.jumpType; // 页面跳转类型
	var getUrl = function(url, data) {
		var queryString = jQuery.param(data);
		return (url.indexOf("?") > -1) ? (url + "&" + queryString) : (url + "?" + queryString)
	};
	var jumpFuncs = {
		childPage: { // 子窗口(弹出)
			open: function() {
				K.newWinFuncs.init(K, function(url, data) {

					var win = jQuery.mt.window({
						id: id,
						title: rule.title || '查看',
						width: rule.width || '650px',
						height: rule.height || '450px',
						refresh: true,
						modal: rule.modal,
						content: {
							// url: url + '?' + jQuery.param(data),
							url: getUrl(url, data),
							iframe: true
						},
						appendTo: 'body'
					});
					win.kendo.center();
					if (maximize)
						win.kendo.maximize();
				});
			},
			close: function(win) {
				jQuery('#' + id).data('kendoWindow').close();
			}
		},
		singlePage: { // 跳转页面
			open: function() {
				K.newWinFuncs.init(K, function(url, data) {
					// window.open(url + '?' + jQuery.param(data));
					window.open(getUrl(url, data));
				});
			},
			close: function(win) {
				win.close();
			}
		}
	};
	if (!type) {
		if (jumpFuncs[jumpType]) {
			jumpFuncs[jumpType].open();
		}
	}
	return jumpFuncs[jumpType];
};

/**
 * 获取分组参数 (如果有相同的参数名，应该放到分组里面传JSON过去)
 * 
 * @param datas
 * @returns
 */
function getInOutParameters(datas) {
	if (!datas) {
		return {};
	}
	var sortType = {},
		sort = false, //
		param = pubsub.getParameters(datas);
	for (var k in datas) {
		var params = datas[k];
		if (params && params.length) {
			$.each(params, function(i, v) {
				if (v.sortType) {
					!sortType[v.sortType] && (sortType[v.sortType] = {});
					!sortType[v.sortType][k] && (sortType[v.sortType][k] = []);
					sortType[v.sortType][k].push(v);
					sort = true;
				}
			});
		}
	}
	if (sort) {
		var sortParams = {};
		$.each(sortType, function(k, v) {
			sortParams[k] = pubsub.getParameters(v);
		});
		param = $.extend((param || {}), {
			params: JSON.stringify(sortParams)
		});
	}
	return param;
}

// 窗口方法
KendoButton.prototype.newWinFuncs = {
	init: function(K, fn) {
		var paraType = K.viewModel.data.rule.paraType,
			idRelation = {};
		var url = contextPath + '/mx/form/page/viewPage';
		/*
		 * if (paraType && paraType[0]) { var datas = paraType[0].datas; if
		 * (datas) { $.each(datas, function(k, v) { if (!idRelation[v[0].inid]) {
		 * idRelation[v[0].inid] = {}; } v[0].items = [];
		 * idRelation[v[0].inid][v[0].param] = v[0]; }); } } var id,v,has =
		 * false; var commonParam = { newWin : K.viewModel.buttonType, id :
		 * K.viewModel.pageId, prid : K.viewModel.prid, btnId : K.viewModel.id };
		 * for(id in idRelation){ has = true; var $this = jQuery('#' + id),
		 * dataRole = K.kendoData($this).dataRole; if ($this[0]) {
		 * $this.data("idRelation", idRelation[id]); } if
		 * (K.newWinFuncs[dataRole]) { var data = K.newWinFuncs[dataRole](K,
		 * $this); if (data && fn) { fn.call($this, url,
		 * $.extend(data,commonParam)); } } else if(fn){ fn.call($this, url,
		 * commonParam); break; } }
		 * 
		 * if(!has){ if(fn){ fn.call({}, url,commonParam); } }
		 */
		var has = false,
			params = null,
			commonParam = {
				newWin: K.viewModel.buttonType,
				id: K.viewModel.pageId,
				prid: K.viewModel.prid,
				btnId: K.viewModel.id
			};
		if (paraType && paraType[0]) {
			var datas = paraType[0].datas;
			if (datas) {
				for (var k in datas) {
					if (k.indexOf("/mx/") > -1) {
						url = contextPath + k;
						break;
					}
				}
				// params = pubsub.getParameters(datas);
				params = window.getInOutParameters(datas);
			}
		}

		var linkPageJson = K.viewModel.data.rule.linkPageJson;
		if (linkPageJson) {
			var pageType = linkPageJson.pageType;
			var emu = {
				'report-page': '',
				'relate-page': ''
			};
			if (pageType && (pageType in emu)) {
				if (linkPageJson.url) {
					url = contextPath + linkPageJson.url;
				}
				if (pageType == 'relate-page' && commonParam) {
					$.extend(commonParam, {
						fn: 'odblClickCallFunc_',
						targetId: K.viewModel.data.rule.id
					});
				}
			}
		}

		/**
		 * 有参数时执行
		 */
		if (params != null) {
			has = true;
			if (fn) {
				fn(url, $.extend(params, commonParam));
			}
		}

		/**
		 * 无参数时...
		 */
		if (!has) {
			if (fn) {
				fn.call({}, url, commonParam);
			}
		}

		/*
		 * jQuery.each(K.viewModel.data, function(id, v) { var $this =
		 * jQuery('#' + id), dataRole = K.kendoData($this).dataRole; if
		 * ($this[0]) { $this.data("idRelation", idRelation[id]); } if
		 * (K.newWinFuncs[dataRole]) { var data = K.newWinFuncs[dataRole](K,
		 * $this); if (data && fn) { fn.call($this, url, data); } } else if(fn){
		 * fn.call($this, url, { id : K.viewModel.pageId }); return; } });
		 */
	},
	grid: function(K, jq) {
		var kendoData = K.kendoData(jq),
			grid = kendoData.kendo,
			rows = grid
			.select();
		if (rows && rows.length > 0) {
			if (K.viewModel.multiPlay == 'false') {
				if (rows.length > 1) {
					alert('请选择一条记录!');
					return false;
				}
			}
			var pageId = K.viewModel.pageId;
			if (pageId) {
				var ids = new Array();

				var relation = jq.data("idRelation"),
					ret = {};

				jQuery.each(rows, function(i, v) {
					if (relation) {
						$.each(relation, function(col, item) {
							item.items.push(grid.dataItem(v)[item.columnName]);
						});
					} else
						ids.push(grid.dataItem(v).id);
				});

				if (relation) {
					$.each(relation, function(k, item) {
						ret[k] = item.items.join('_0_');
					});
				}
				ret.newWin = K.viewModel.buttonType;
				ret.id = K.viewModel.pageId;
				ret.prid = K.viewModel.prid;
				ret.btnId = K.viewModel.id;

				return ret;
				/*
				 * return { newWin : K.viewModel.buttonType, id : pageId,
				 * parentId : '[' + ids.join(',') + ']' , prid :
				 * K.viewModel.prid, btnId : K.viewModel.id };
				 */
			}
		} else {
			alert('请选择!');
			return false;
		}
	}
};

/**
 * 关闭操作
 */
KendoButton.prototype.closeWindow = function() {
	if (confirm('你确定关闭窗口吗?')) {
		var pageParameters = this.viewModel.data.pageParameters;
		if (pageParameters) {
			var rst = KendoButtonClose(window);
			if (!rst) {
				window.close();
			}
			/*
			 * var btnId = pageParameters.btnId, vm; if (btnId) { var parent =
			 * opener || window.parent; vm = parent.$('#' +
			 * btnId).mtkendo('getViewModel');
			 * parent.$(vm.change).mtkendo('reload', {}); if
			 * (pageParameters.newWin) {
			 * vm.functions[pageParameters.newWin](true).close(window); } } else {
			 * window.close(); }
			 */
		} else {
			window.close();
		}
	}
};

var findWin = 0;

function KendoButtonClose(win) {
	var pageParametersStr = win.pageParameters;
	if (pageParametersStr) {
		var params = JSON.parse(pageParametersStr),
			vm;
		var parent = win.opener || win.parent;
		if (params && params.btnId) {
			var btn = parent.$('#' + params.btnId);
			vm = btn.mtkendo('getViewModel');
			parent.$(vm.change).mtkendo('reload', {});
			if (params.newWin) {
				vm.functions[params.newWin](true).close(win);
				return true;
			}
		} else {
			if (findWin++ == 3) {
				return false;
			}
			return KendoButtonClose(parent);
		}
	}
	return false;
}

KendoButton.prototype.kendoData = function(jq) {
	return jq.mtkendo('getKendoData');
};

var mtxx = {

};

function getCompensate() {

}
// 初始化页面层次
function initPageLevel() {
	var parent = window.parent,
		parentPageLevel = parent.pageLevel;
	/*
	 * if (parentPageLevel && !isWeighting) { pageLevel = parentPageLevel + 1; }
	 * else { pageLevel = 1; }
	 */
	if (parentPageLevel) {
		pageLevel = parentPageLevel + 1;
	} else {
		pageLevel = 1;
	}
	if (pageLevel != 1) {
		if (isWeighting) { // 合并事件
			// pageed =
			// JSON.stringify($.merge(JSON.parse(pageed),JSON.parse(parent.pageed)))
			var pageedJson = JSON.parse(pageed);
			pageed = parent.pageed || {};
			pageed[pageLevel] = pageedJson;
		} else {
			pageed = parent.pageed;
		}
	} else {
		if (pageed) {
			var pageedJson = JSON.parse(pageed);
			pageed = {};
			pageed[pageLevel] = pageedJson;
		}
	}
	if (pageed) {
		for (var plevel in pageed) {
			var pageEvents = pageed[plevel],
				len = pageEvents.length,
				pageEvent, triggers, trigger, thref = window.location.href,
				stockMoObj = {};
			for (var i = 0; i < len; i++) {
				pageEvent = pageEvents[i];
				triggers = pageEvent.trigger;
				for (var j = 0; j < triggers.length; j++) {
					trigger = triggers[j];
					if ((trigger.level == pageLevel || (trigger.level == pageLevel - plevel + 1)) && thref.indexOf(trigger.pageId) != -1) { // 控件事件的层级
						// TODO 注册事件
						var $this = $('#' + trigger.eleId),
							r = trigger.eleId.length > 25 ? "page" : pubsub.getRole(trigger.eleId),
							nb = (r == "button" ? "default" : r),
							t = $this.attr('data-role') || nb;

						if (trigger.otype) {
							$this = $('#' + trigger.oid);
							t = trigger.otype;
						}
						//变长区事件特殊注册
						var columnnameKey = "columnname",
							columnname = $this.attr(columnnameKey);
						if (columnname) {
							var $table = $this.closest('table'),
								eventType = trigger.eventType,
								index = $this.closest("tr").index();
							if ("listenVal" == eventType) {
								var moObj = stockMoObj[$table.attr("tbid")] || (stockMoObj[$table.attr("tbid")] = {
										target: $table,
										stock: []
									}),
									stock = moObj.stock;
								stock.push({
									columnname: columnname,
									pageEvent: pageEvent,
									index: index
								});
								eventType = "change";
							}
							$table.on(eventType + "." + columnname, "[" + columnnameKey + "=" + columnname + "]", {
								index: index,
								pageEvent: pageEvent
							}, function(e) {
								pubsub.elongateStockIndex = $(this).closest("tr").index() - e.data.index;
								pubsub.pub(e.data.pageEvent, "");
								pubsub.elongateStockIndex = null;
							});
						} else {
							var fn = pubsubobjects[t] || pubsubobjects["default"];
							fn.call($this, {
								pageEvent: pageEvent,
								trigger: trigger,
								eventType: trigger.eventType
							});
						}
					}
				}
			}
			for (var tbid in stockMoObj) {
				var stock = stockMoObj[tbid];
				stock.target.data("moStock", stock);
				var ink = new MutationObserver(function(record) {
					$.each(record, function(index, el) {
						var columnname = el.target.getAttribute ? (el.target.getAttribute("columnname") || $(el.target).closest('[data-role]').attr("columnname")) : null;
						var tstock = $(el.target).closest('table').data("moStock")["stock"];
						if ((columnname && el.attributeName.indexOf("setvalue") != -1)) {
							$.each(tstock, function(i, v) {
								if (v.columnname == columnname) {
									pubsub.elongateStockIndex = $(el.target).closest("tr").index() - v.index;
									pubsub.pub(v.pageEvent, "");
									pubsub.elongateStockIndex = null;
								}
							});
						} else if (el.removedNodes.length) {
							$.each(tstock, function(i, v) {
								pubsub.elongateStockIndex = $(el.previousSibling).closest("tr").index() - v.index;
								pubsub.pub(v.pageEvent, "");
								pubsub.elongateStockIndex = null;
							});
						}
					});
				});
				ink.observe(stock.target[0], {
					'attributes': true,
					'subtree': true,
					'childList': true,
					'attributesFilter': ['setvalue']
				});
			}
		}
	}
	initPageValidate(validateObj);
}
initPageValidate.prototype.customRule = function(k) {
	var mtValidate = k.data("mtValidate"),
		trigger = k.data("mtValiObj");
	// 执行表达式
	var execObj = pubsub._getInParams(mtValidate.execExpData, trigger.inlev),
		exec = pubsub
		.expConvert(mtValidate.execExp || "", execObj);
	if (exec != "") {
		var execBol = eval(exec);
		if (!execBol)
			return true;
	}
	// 验证表达式
	var eObj = pubsub._getInParams(mtValidate.expData, trigger.inlev),
		exp = pubsub
		.expConvert(mtValidate.exp || "", eObj);
	if (exp != "") {
		var expBol = eval(exp);
		if (expBol)
			return true;
		else
			return false;
	}
	return true;
};
// 初始化页面验证方法
function initPageValidate(ruleObj) {
	function bindValidateFunc($this, vali) {
		var types = vali.type.split(","),
			type;
		$.each(types, function(i, v) {
			switch (v) {
				case "validate_type_foucs": // 聚焦
					/*
					 * $this.on('blur', function(event) {
					 * $(this).data("kendoValidator").validate(); });
					 */
					ruleObj.validate_type_foucs($this);
					break;
				case "validate_type_save":
					/* $this.attr("valiSave", "true"); */
					ruleObj.validate_type_save($this);
					break;
				default:
					break;
			}
		})
	}
	var parent = window.parent;
	if (pageLevel != 1)
		mtValidate = parent.mtValidate;
	if (mtValidate) {
		var pageValidate = JSON.parse(mtValidate),
			len = pageValidate.length,
			vali, triggers, trigger, tlen, $target;
		for (var i = 0; i < len; i++) {
			vali = pageValidate[i];
			triggers = vali.trigger;
			tlen = triggers.length;
			for (var j = 0; j < tlen; j++) {
				trigger = triggers[j];
				$target = pubsub.getJQObj(trigger, true);
				if (trigger.itype == "cell") { // cell表达式验证
					var $jq = pubsub.getJQObj(trigger, true),
						spread = $jq
						.data("spread"),
						sheet = spread.getActiveSheet(),
						inid = trigger["inid"],
						inids = inid
						.split("-"),
						spreadValidate = $jq
						.data("spreadValidate") || {};
					trigger.vali = vali;
					spreadValidate[inid] = trigger;
					$jq.data("spreadValidate", spreadValidate);
					if (vali.type && vali.type.indexOf("validate_type_save") > 0) {
						$jq.attr("valiSave", "true");
					}
					// 绑定
					/*
					 * if(j==0){
					 * spread.bind(GcSpread.Sheets.Events.EditChange,function(sender,
					 * args){ console.log(sender); console.log(args); var $host =
					 * $(args.sheet.parent.getHost()); }); }
					 */
				} else if ($target) {
					$target.data("mtValidate", vali).data("mtValiObj", trigger)
						.attr("mtValidate", "true").attr("mtTitle",
							$target.attr("title"));
					bindValidateFunc($target, vali);
				}
			}
		}
		ruleObj.bindEvent();
	}
};
var pubsubobjects = {
	'default': function(e) {
		if ("changeByName" == e.eventType) {
			$('input[name=' + $(this).attr('name') + ']').on('change',
				function(event) {
					// 事件方法
					mtxx.e = event;
					pubsub.pub(e.pageEvent, "");
				});
		} else if ("listenVal" == e.eventType) {
			var ink = new MutationObserver(function(record) {
				pubsub.pub(e.pageEvent, "");
			});
			ink.observe($(this)[0], {
				'attributes': true,
				'characterData': true
			});
			$(this).on("change", function(event) {
				// 事件方法
				mtxx.e = event;
				pubsub.pub(e.pageEvent, "");
			});
		} else {
			$(this).on(e.eventType, function(event) {
				// 事件方法
				mtxx.e = event;
				pubsub.pub(e.pageEvent, "");
			});
		}
	},
	ztree: function(e) { // ztree 事件
		var ztreeObj = $.fn.zTree.getZTreeObj(this.attr('id'));
		if (e.eventType == "clickEvent") {
			ztreeObj.setting.callback.onClick = function(event, treeId,
				treeNode) {
				pubsub.pub(e.pageEvent, "", treeId, treeNode);
			}
		}
	},
	grid: function(e) {
		var id = this.attr("id"),
			$this = $("#" + id),
			g = $this
			.data('kendoGrid');
		if (e.eventType == "gchange") {
			g.bind('change', function(ex) {
				$this.kendoGridClick(id, e.pageEvent, "", ex);
				// pubsub.pub(e.pageEvent, "", ex);
			})
		} else if (e.eventType == "dbclick") {
			$this.kendoGridDblClick(id, e.pageEvent);
		} else if (e.eventType == "loadEnd") {
			g.bind("dataBound", function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			});
		}
	},
	treelist: function(e) {
		var id = this.attr("id"),
			$this = $("#" + id),
			g = $this
			.data('kendoTreeList');
		if (e.eventType == "gchange") {
			g.bind('change', function(ex) {
				$this.kendoGridClick(id, e.pageEvent, "", ex);
				// pubsub.pub(e.pageEvent, "", ex);
			})
		} else if (e.eventType == "dbclick") {
			$this.kendoGridDblClick(id, e.pageEvent);
		}
	},
	dropdownlist: function(e) {
		var g = $("#" + this.attr("id")).data('kendoDropDownList');
		if (e.eventType == "change") {
			g.bind('change', function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			})
		}else if (e.eventType == "loadEnd") {
			g.bind('dataBound', function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			})
		}
	},
	tabstrip: function(e) {
		var tabStrip = this.data("kendoTabStrip");
		if (e.eventType == "activated") { // 激活事件
			tabStrip
				.bind(
					"activate",
					function(ex) {
						var $tabStrip = $(ex.sender.element),
							$content = $(ex.contentElement);
						if ($tabStrip.attr("model") == "noframe") {
							var eles = $content.find("#" + e.pageEvent['in'][0].eleId);
							if (!eles.length) {
								return;
							}
							pubsub.pub(e.pageEvent, "", "#" + ex.contentElement.id);
						} else {
							var iframe = $content.find('iframe');
							if (!iframe.attr('src') || iframe
								.attr('src')
								.indexOf(
									e.pageEvent['in'][0].pageId) == -1) {
								return;
							}
							var win = iframe[0].contentWindow;
							// 初次执行
							var options = {
								'childList': true,
								'arrtibutes': true,
								'subtree': true,
								'characterData': true
							};
							var k = new MutationObserver(function() {
								pubsub.pub(e.pageEvent, "", "#" + ex.contentElement.id);
								k.disconnect();
								win.t = true;
							});
							var body = win.document.body;
							if (body) {
								k.observe(body, options);
							}
							if (win.t) {
								pubsub.pub(e.pageEvent, "", "#" + ex.contentElement.id);
							} else {
								if (body && iframe.data("onReadyState")) {
									// setTimeout(function(){
									body.appendChild(document
										.createElement("div"));
									// },350);
								}
							}
							/*
							 * if(iframe.data("onReadyState")){
							 * setTimeout(function(){ //延时执行
							 * pubsub.pub(e.pageEvent,"","#"+ex.contentElement.id);
							 * },450); }
							 */
						}
					});
		}
	},
	button: function(e) {
		var events = $(this).attr("t-events");
		if (events == 'true') { // 动态事件

			/*
			 * var id = $(this).attr("id"); var $a = $("a[btn-id=" + id + "]");
			 * var definedTable = $a.closest("[data-role=definedTable]");
			 * 
			 * if (definedTable[0]) {
			 * 
			 * var $event = definedTable.data("event") || {};
			 * 
			 * definedTable.data("event", $event);
			 * 
			 * $event[id] = { id : id, e : e };
			 * 
			 * definedTable.off(e.eventType, "a").on(e.eventType, "a",
			 * function(event) { var $this = $(this), btnId =
			 * $this.attr("btn-id"), eve = $event[btnId]; if (eve && eve.id) {
			 * mtxx.e = event; pubsub.pub(eve.e.pageEvent, ""); } }); } else {
			 * $a.each(function(i, a) { pubsubobjects["default"].call($(a), e);
			 * }); }
			 */
			var targetEle = /*"[data-role=definedTable] [btn-id]"*/ "[data-role] [btn-id][t-events=true]",
				key = "event";
			$(this).data(key, e);
			$(document.body).off(e.eventType, targetEle).on(
				e.eventType,
				targetEle,
				function(event) {
					event.preventDefault();
					var btnId = $(this).attr("btn-id"),
						$btn = $("#" + btnId);
					if ($btn[0]) {
						var eve = $btn.data(key);
						if (eve) {
							mtxx.e = event;
							pubsub.pub(eve.pageEvent, "");
						}
					}
				});

		} else {
			pubsubobjects["default"].call(this, e);
		}
	},
	page: function(e) {
		if (e.eventType == "pageInit") {
			jqCb.add(function() {
				pubsub.pub(e.pageEvent);
			});
		}
	},
	jsgis: function(e) {
		var jsgisEvent = function(e, eventName) {
			var $this = $("#" + e.trigger.eleId),
				ary = $this.data(eventName) || [];
			ary
				.push(function(kk) {
					var bol = false,
						isCurrentPoint = false; // 当前点是否是当前弹窗位置的点
					$
						.each(
							e.pageEvent.ops,
							function(i, v) {
								for (var p in v.param) {
									var dps = v.param[p];
									$
										.each(
											dps,
											function(i, v) {
												if (v.param == "point" && v.columnName == kk.graphic.symbol.column)
													bol = true;
												if (v.param != "point" && v.columnName == kk.graphic.symbol.column)
													isCurrentPoint = true;
											});
								}
							})
					if (bol)
						pubsub.pub(e.pageEvent, "", kk, isCurrentPoint);
				}) && $this.data(eventName, ary);
		}
		if (e.eventType == "jsgisInit") {
			mapCb.add(function() {
				pubsub.pub(e.pageEvent);
			});
		} else if (e.eventType == "clickEvent") { // 单击事件
			jsgisEvent(e, "clickEventList");
		} else if (e.eventType == "dbclick") { // 双击事件
			jsgisEvent(e, "dbclickList");
		} else if (e.eventType == "mouseover") { // 鼠标移入
			jsgisEvent(e, "mouseoverList");
		} else if (e.eventType == "mouseup") { // 鼠标移出
			jsgisEvent(e, "mouseupList");
		}
	},
	definedTable: function(e) {
		var jq = $("#" + this.attr("id"));
		if (e.eventType == "loadEndExecOne") {
			jq.definedTable('loadEndExecOne', function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			})
		} else if (e.eventType == "loadEnd") {
			jq.definedTable('loadEnd', function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			})
		}
	},
	treelistbt: function(e) {
		var id = this.attr("id"),
			$this = $("#" + id);
		if (e.eventType == "gchange") {
			$this.treelist({
				events: {
					onClickRow: function(index, row) {
						pubsub.pub(e.pageEvent, "", index, row);
					}
				}
			});
			// pubsub.pub(e.pageEvent, "", ex);

		}
	},
	gridbt: function(e) {
		var id = this.attr("id"),
			$this = $("#" + id);
		if (e.eventType == "gchange") {
			$this.grid({
				events: {
					onClickRow: function(index, row) {
						pubsub.pub(e.pageEvent, "", index, row);
					}
				}
			});
			// pubsub.pub(e.pageEvent, "", ex);

		}
	},
	cell: function(e) {
		$this = this;
		var eventsRule = $this.data("eventsRule") || {};
		eventsRule[e.trigger.eleId] = function(ex) {
			// console.log(ex);
			pubsub.pub(e.pageEvent, "", ex);
		}
		$this.data("eventsRule", eventsRule);
	},
	charts: function(e) {
		var $this = this;
		if (e.eventType == "clickEvent") {
			$this.data("clickEvent", function(ex) {
				pubsub.pub(e.pageEvent, "", ex, this);
			});
		}
	},
};

function initProgressBarFunc() {
	var options = {
		'childList': true,
		'arrtibutes': true,
		'subtree': true,
		'characterData': true
	};
	var observer = new MutationObserver(
		function() {
			$(".mt-progressbar").each(
				function() {
					var $this = $(this);
					var data = JSON.parse($this.attr("data").replace(
						/\'/gi, '\"'));
					var val = $this.attr("tval");
					var $div = $("<div></div>");
					$this.after($div[0]);
					try {
						var opts = {
							type: data.model,
							showStatus: data.showState,
							value: parseFloat(val)
						};
						if (data.min) {
							opts.min = parseFloat(data.min);
						}
						if (data.max) {
							opts.max = parseFloat(data.max);
						}
						$div.attr("style", $this.attr("style"));
						$div.kendoProgressBar(opts);
						$div.find(".k-state-selected").css({
							"background-color": "#ef1f0c",
							"border-color": "#ef1f0c"
						});
					} catch (e) {
						console.log(e);
					}
					$this.remove();
				});
			// 多文件查看功能
			$(".mt-fileView")
				.each(
					function() {
						var $this = $(this);
						if ($this.attr("model") == "multi") {
							var htmlStr = $this.html(),
								tvalStr = $this.attr("tval"),
								splitStr = $this.attr("split"),
								html, tval, i = 0,
								key = $this.attr("key"),
								url = $this.attr("url"),
								model = $this.attr("model"),
								fid = $this.attr("fid"),
								dbid = $this.attr("dbid"),
								htmls = htmlStr.split(","),
								tvals = tvalStr.split(","),
								length = htmls.length,
								targetStr = "";
							for (; i < length; i++) {
								if (htmls[i]) {
									targetStr += "<a href='#' fid='" + fid + "' kid='" + tvals[i] + "' dbid='" + dbid + "' model='" + model + "' key='" + key + "' url='" + url + "' onclick='viewFiles(this)'>" + htmls[i] + "</a>" + splitStr;
								}
							}
							if (targetStr) {
								targetStr = targetStr.substr(0,
									targetStr.length - 1);
								$this.after(targetStr);
								$this.remove();
							}
						}
					});
			// observer.disconnect();
		});
	observer.observe(document.body, options);
	document.body.appendChild(document.createElement("div"));
}

function initKendoUploadFunc() {
	$("[data-role=upload]").each(function() {
		initKendoUpload(this, this.id, $(this).attr("randomparent"));
	});
}
//

//

// 页面初始化执行方法
function pageInit() {
	var p = pageParameters || undefined;
	if (p) {
		var params = eval('(' + p + ')');
		if (params) {
			mtxx.params = params;
			var ret = params['detail'];
			initPageDetailSuccess(ret);
		}
		// initPageDetail(p);
	} else {
		initPageDetailSuccess();
	}

	// 给记忆控件注册点击事件
	$('body').delegate('input[memory=true]', 'click', function() {
		window.memoryObj = $(this);
	});
}

function initPageDetail(p) {
	var params = eval('(' + p + ')');
	if (params) {
		mtxx.params = params;
		var $body = $(document.body);
		var url = contextPath + '/mx/form/data/initPageDetail',
			jdom;
		$.ajax({
			url: url,
			data: params,
			dataType: 'json',
			type: 'post',
			async: false,
			beforeSend: function() {
				// $body.mask();
			},
			success: initPageDetailSuccess
		}).always(function() {
			// $body.mask("hide");
		});
	}
}

/**
 * 页面初始化赋值
 * 
 * @param ret
 * @returns
 */
function initPageDetailSuccess(ret) {
	if ((window.$ret = ret)) {
		if (ret.data) {
			ret.jdoms = [];
			$.each(ret.data, function(id, v) {
				jdom = $("#" + id);
				if (jdom[0]) {
					jdom.mtkendo('setValue', v[0].value || jdom.val(), v);
					if (!id.startsWith("ztree")) {
						jdom.data("params", v[0]);
					}
					ret.jdoms.push({
						jdom: jdom,
						id: id
					});
				}
			});
			linkAge(ret); // 联动页面控件、
			initPageFlow(ret); // 页面工作流
		}
		if (ret.handleColumns) {
			var $body = document.body,
				saveKey = "saveMsg";
			!$.data($body, saveKey) && ($.data($body, saveKey, []));
			var btn = new KendoButton({
				handlecolumns: JSON.stringify(ret.handleColumns)
			});
			$.data(document.body, saveKey).push(btn);
		}
	}
	initProgressBarFunc();
	initKendoUploadFunc();
	initImageUploadFunc();
	//

	initPageLevel();
	jqCb.fire();
}

/**
 * 页面工作流
 * 
 * @param o
 */
function initPageFlow(o) {
	if (o.jdoms.length) {
		$.ajax({
			url: contextPath + '/mx/form/data/initPageFlow',
			type: 'post',
			dataType: 'JSON',
			data: {
				idValue: o.__idValue,
				pageId: mtxx.params.id
			},
			success: function(ret) {
				if (ret.flowDefined) {
					$.data(document.body, "flowFilter", new FlowFilter(ret.flowDefined).exec(o.jdoms));
				}
			}
		});
	}
}

/**
 * 工作流权限过滤
 * 
 * @param flow
 * @returns
 */
function FlowFilter(flow) {

	this.flow = flow;

	this.elements = flow.elements;

	this.exec = function(doms) {
		var that = this;
		$.each(doms, function(i, v) {
			that.filter(v.jdom, v.id);
		});
		$.each(that.elements, function(id, element) {
			if (!element.ready) {
				var jq = $("#" + id);
				jq[0] && (that.filter(jq, id));
				element.$jq = jq;
			}
		});
	};

	this.target = function(id) {
		addVariables(id, this.flow._taskId);
	};

	this.filter = function(jq, id) {
		if (!this.elements)
			return true;
		var f = this.elements[id];
		if (f) {
			f.ready = true;

			var value = jq.mtkendo("getValue");

			jq.mtkendo("setValue", value || f.defaultVal || '');
			// if (f.target) { // 接收人 (已经放到全局参数里面)
			// that.target(id);
			// }
			if (!f.show) { // 不显示
				jq.toggle();
				return false;
			}
			if (!f.write) {
				jq.attr({
					readonly: true
				});
			}
			if (!f.read) {}
		}
		return true;
	};

	(function(that) {
		var f = that.flow;
		mtxx.params.flow = f;
		if (f.targets && f.targets.length) { // 接收人放入工作流全局变量
			$.each(f.targets, function(i, v) {
				addVariables(v.eleId, v.taskId);
			});
		}
	})(this);
}

/**
 * 获取选择用户类型[角色，部门，人]
 * 
 * @param jq
 * @returns
 */
function getSelectType(jq) {
	var surl = jq.attr("surl");
	if (surl) {
		var pars = surl.split("=");
		return pars[pars.length - 1];
	}
	return jq.attr("select-type") || "user";
}

/**
 * 工作流参数
 * 
 * @param id
 * @param taskId
 * @returns
 */
function addVariables(id, taskId) {
	var jq = $("#" + id);
	var selectType = getSelectType(jq);
	!mtxx.params.flow && (mtxx.params.flow = {});
	!mtxx.params.flow.variables && (mtxx.params.flow.variables = []);
	mtxx.params.flow.variables.push({
		datas: {
			expActVal: "~M{" + id + "}"
		},
		varcode: taskId,
		varname: taskId + "_" + selectType
	});
};

/**
 * 流程实例函数 撤销、终止...
 * @author klaus.wang
 */
var FlowProcess = {
	STOP: 1,
	CANCEL: 2,
	ACTIVE: 3,
	REJECT: 4
};


FlowProcess.callWindow = function(args, sfn, efn) {
	$.extend(FlowProcess, {
		args: args,
		sfn: sfn,
		efn: efn
	});
	var url = contextPath + //
		"/mx/form/defined/ex/flowProcessFeedback?fn=FlowProcessFunc&&taskId=" + (args.taskId || '') + "&" + $.param({
			processId: args.processId,
			type: args.type
		});

	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: false,
		title: "流程操作",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		//	offset : [ '', '' ],
		fadeIn: 100,
		area: [650, 400],
		iframe: {
			src: url
		}
	});
};

function FlowProcessFunc(args) {
	args = $.extend(true, (FlowProcess.args || {}), args || {});
	var url = contextPath + //
		"/mx/form/workflow/defined/flowProcess";
	$.ajax({
		url: url,
		data: args,
		type: 'post',
		dataType: 'json',
		success: FlowProcess.sfn || function(ret) {
			if (!ret)
				return;
			if (ret.errMsg) {
				alert(ret.errMsg);
			} else {
				alert("操作成功!");
				FlowProcessFunc.close();
			}
		},
		error: FlowProcess.efn || function(ret) {
			alert("服务器处理出错!");
		}
	});
}

FlowProcessFunc.close = function() {
	closeLayer();
}

/**
 * 流程终止
 */
FlowProcess.stop = function(args) {
	if (!confirm("你确定终止流程吗?")) {
		return false;
	}
	FlowProcess.callWindow({
		type: FlowProcess.STOP,
		processId: args.processId
	});
}

/**
 * 流程挂起
 */
FlowProcess.cancel = function(args) {
	if (!confirm("你确定挂起流程吗?")) {
		return false;
	}
	FlowProcessFunc({
		type: FlowProcess.CANCEL,
		processId: args.processId
	});
}

/**
 * 流程激活
 */
FlowProcess.active = function(args) {
	if (!confirm("你确定激活流程吗?")) {
		return false;
	}
	FlowProcessFunc({
		type: FlowProcess.ACTIVE,
		processId: args.processId
	});
}

/**
 * 流程撤回
 */
FlowProcess.reject = function(args) {
	if (!confirm("你确定撤回流程吗?")) {
		return false;
	}
	FlowProcess.callWindow({
		type: FlowProcess.REJECT,
		processId: args.processId,
		taskId: args.taskId
	});
}

///////流程实例函数 end/////////////////

/**
 * 页面初始化 联动
 * 
 * @param ret
 */
function linkAge(ret) {
	if (ret) {
		if (ret.controlRule) { // 页面联动
			pubsub.pub("linkageControl", ret.controlRule);
			pubsub.pub("linkagePage", ret.controlRule);
		}
		if (ret.tables) { // 页面参数隐藏域
			$.each(ret.tables, function(key, list) {
				if (list.length) {
					for (var i = 0; i < list.length; i++) {
						var v = list[i];
						$("<input>", {
							'class': key,
							type: 'hidden',
							fieldName: v.fieldName,
							value: v.value
						}).appendTo("body");
					}
				}
			});
		}

		if (ret.pageRuleId) {
			$("<input>", {
				'class': 'pageruleid ' + mtxx.params.id,
				type: 'hidden',
				value: ret.pageRuleId,
				idValue: ret.__idValue
			}).appendTo("body");
		}
		if (ret.batchRules && ret.batchRules[0]) { // 变长区数据获取
			if (ret.batchRules[0].cell) {
				new CellBatchFunc(ret.batchRules[0]);
			} else {
				new BatchFunc(ret.batchRules);
			}
		}
	}
}

function CellBatchFunc(rule) {
	this.rule = rule;
	this.hashField = {};
	this.topId = null;
	this.index_id = null;
	this.init();
}

CellBatchFunc.prototype = {
	constructor: CellBatchFunc,
	refresh: function() {
		this.init();
	},
	init: function() {
		var that = this;
		var rule = this.rule,
			dataSetId = rule.dataSetId;
		var topTableName = rule.topTableName,
			tableName = rule.table.tableName;
		var topIdField = $("." + topTableName + "[fieldName=id]");
		if (!topIdField[0]) {
			topIdField = $("." + topTableName + "[fieldName=topid]");
		}
		window.batch = that;
		if (dataSetId && topIdField.val() && rule.columns.length) {
			that.topId = rule.topId = topIdField.val();

			$.each(rule.columns, function(i, v) {
				that.hashField[v.fieldName] = v;
			});

			initPageBatch({
					cell: true,
					dataSetId: dataSetId,
					topId: topIdField.val(),
					topTableName: topTableName
				},
				function(data) {
					var rows = data.rows;
					if (rows && rows.length > 0) {
						var tmp = that.hashField,
							key, field, id, input, fieldInput;
						$.each(rows,
							function(i, v) {
								if (!that.index_id) {
									that.index_id = v[tableName + "_0_index_id"];
								}
								if (!v[tableName + "_0_id"]) {
									return false;
								}
								for (key in tmp) {
									field = tableName + "_0_" + key;
									fieldInput = $("[name=" + tmp[key].id + "]");
									if (fieldInput && fieldInput[0]) {
										fieldInput.attr({
											t: key
										});
										input = fieldInput.eq(i);
										if (input && input[0]) {
											input.val(v[field]).data(
												"row", v);
										}
									}
								}
							});
					}
				});
		}
	},
	getValue: function(tables) {

		var that = this,
			rules = that.rule,
			fieldName, key, tmp = that.hashField,
			length, input;

		if (tmp) {
			for (key in tmp) {
				length = $("[t=" + key + "]").length;
				if (length)
					break;
			}
			var i, fieldInput, input, obj, collection, table, isok, index_id = that.index_id;

			if (!index_id) {
				initPageBatch({
					dataSetId: dataSetId,
					topId: topIdField.val()
				}, function(data) {

				});
			}

			for (i = 0; i < length; i++) {
				collection = new Array();
				isok = false;
				table = {
					idValue: null,
					table: {
						tableName: rules.table.tableName
					},
					columns: collection
				};

				for (key in tmp) {
					obj = new Object();
					fieldInput = $("[t=" + key + "]");
					if (fieldInput && fieldInput[0]) {
						input = fieldInput.eq(i);
						if (input && input[0]) {
							var row = input.data("row");
							if (row && !table.idValue) {
								table.idValue = row[rules.table.tableName + "_0_id"];
							}
							obj.fieldName = key;
							obj.value = input.val();
							if (obj.value) {
								isok = true;
							}
						}
						collection.push(obj);
					}
				}
				collection.push({ // listno
					fieldName: 'listno',
					value: i
				});
				collection.push({ // topId
					fieldName: 'topId',
					value: rules.topId
				});
				collection.push({ // topId
					fieldName: 'index_id',
					value: index_id
				});
				if (isok)
					tables.push(table);
			}
		}
	}
};

function initPageBatch(data, fn) {
	var url = contextPath + '/mx/form/data/initPageBatch';
	$.ajax({
		url: url,
		type: 'post',
		dataType: 'json',
		data: data,
		async: true,
		success: fn
	});
}

function BatchFunc(rules) {
	this.rules = rules;
	console.log(rules);
	this.deleteCollection = [];
	this.init();
}
BatchFunc.prototype = {
	constructor: BatchFunc,
	init: function() {
		var that = this,
			rules = that.rules;
		that.initTools();
		for (var i = 0; i < rules.length; i++) {
			var rule = rules[i],
				tableMsg = rule.tableMsg,
				tableName = rule.tableName,
				dataSetId = rule.dataSetId,
				typeField = rule.typeField,
				topTableName = rule.topTableName;
			var topIdField = $("." + topTableName + "[fieldName=id]");
			if (!topIdField[0]) {
				topIdField = $("." + topTableName + "[fieldName=topid]");
			}
			if (tableMsg) {
				var typeSort = {
						stuff: new Object()
					},
					typeCode;

				/**
				 * 根据datasetId 从数据集获取
				 */
				var dataSource = [];
				window.batch = that;
				if (dataSetId && topIdField.val()) {
					rule.topId = topIdField.val();
					var url = contextPath + '/mx/form/data/initPageBatch';
					$
						.ajax({
							url: url,
							type: 'post',
							dataType: 'json',
							data: {
								dataSetId: dataSetId,
								topId: topIdField.val()
							},
							async: false,
							success: function(data) {
								var rows = data.rows;
								if (rows && rows.length) {
									$
										.each(
											rows,
											function(i, v) {
												var obj = new Object();
												for (var key in v) {
													obj[key
														.replace(
															tableName + '_0_',
															'')] = v[key];
												}
												typeCode = obj[typeField] || "sys-0";
												if (!typeSort[typeCode]) {
													typeSort[typeCode] = new Array();
												}
												typeSort[typeCode]
													.push(obj);
											});
								}
							}
						});
				}
				var clickType = "click.elements",
					randomId = new Date()
					.getTime(),
					idKey = "id";
				for (var k = 0; k < tableMsg.length; k++) {
					var msg = tableMsg[k];
					var table = $("#" + msg.cid).closest("table"),
						trIndex = msg.trIndex,
						tdIndex = msg.tdIndex;
					table
						.unbind(clickType)
						.on(
							clickType,
							'td [columnname]',
							function(e) {
								var $target = $(e.target);
								var $tr = $target.closest("tr");
								var $templateTr = table.find("tr[tid='" + $tr.attr("class") + "']"); // 当前选择行的母板
								$tr
									.find("[columnname]")
									.each(
										function(i, v) {
											var $v = $(v);
											!$v.attr(idKey) && ($v
												.attr(
													idKey,
													randomId++));
											$templateTr
												.find(
													"[columnname='" + $v
													.attr("columnname") + "']")
												.attr({
													toId: $v
														.attr(idKey)
												});
										});
							});
					var initData = typeSort[msg.xy];
					if (initData) {
						initData.sort(function(a, b) {
							return (a.listno - b.listno);
						});
					}
					if (msg.items) {
						var trs = table.find("tr"),
							$this = trs.eq(trIndex)
							.data("fieldIds", msg.fieldIds).data('type', {
								typeField: typeField,
								typeCode: msg.xy,
								typeName: msg.name
							}).data("rule", rule),
							fn = new Batch($this,
								tdIndex),
							tId = $this.attr("tId");
						typeSort.stuff[msg.xy] = {
							fn: fn,
							length: msg.items.length
						};
						$.each(msg.items, function(i, v) {
							var tr = trs.eq(v.trIndex).addClass(tId);
							if (tr && tr[0]) {
								fn.initTrEvents(tr);
								if (!($this[0] === tr[0])) {
									tr.find("td").each(function(index, td) {
										var last = $(this).children().last();
										if (last && last[0]) {
											last.attr({
												columnName: fn.fields[index]
											});
										}
									});
								}
								fn.rowspan(+1);
								if (initData && initData.length >= i) {
									fn.set(initData[i], tr);
								}
							}
						});
					}
				}
				if (typeSort.stuff) {
					var obj;
					for (var key in typeSort.stuff) {
						obj = typeSort.stuff[key];
						if (obj) {
							var kds = typeSort[key];
							if (kds && (kds.length > obj.length)) {
								var datas = kds.slice(obj.length, kds.length);
								if (datas) {
									obj.fn.add(datas);
								}
							}
						}
					}
				}
			}
		}
	},
	getValue: function(tables) {
		var that = this,
			rules = that.rules;
		// tables = new Array();
		for (var i = 0; i < rules.length; i++) {
			var rule = rules[i],
				cId = rule.cId;
			var table = $("#" + cId).closest("table"),
				tbId = table
				.attr("tbId");
			table
				.find("tr[tbid='" + tbId + "']")
				.each(
					function() {
						var batchRoot = $(this),
							typeObj = batchRoot
							.data("type") || {};
						var tid = batchRoot.attr("tId"),
							o;
						table
							.find('.' + tid)
							.each(
								function(i, v) {
									var typeField = typeObj.typeField,
										collection = new Array();

									var row = $(this).data(
										"row");
									tables
										.push({
											idValue: row ? row['id'] : '',
											table: {
												tableName: rule.tableName
											},
											columns: collection
										});
									if (typeObj.typeField) { // type
										collection
											.push({
												fieldName: typeObj.typeField,
												value: typeObj.typeCode
											});
									}
									collection.push({ // listno
										fieldName: 'listno',
										value: i
									});
									collection.push({ // topId
										fieldName: 'topId',
										value: rule.topId
									});
									$(this)
										.find(
											"[columnName]")
										.each(
											function() {
												var cn = $(this),
													fieldName = cn
													.attr('columnName');
												if (fieldName) {
													// fieldName
													// =
													// fieldName.replace(rule.tableName
													// +
													// '_0_','');
													collection
														.push({
															fieldName: fieldName,
															// value:
															// cn
															// .val()
															value: cn
																.mtkendo("getValue")
														});
												}
											});
								});
					});
		}

		var dels = that.deleteCollection;
		if (dels.length > 0) {
			for (var i = 0; i < dels.length; i++) {
				tables.push(dels[i]);
			}
		}

	},
	initTools: function() {
		var $tools = $(".k-batch-tools");
		if (!$tools[0]) {
			$tools = $("<div>", {
				'class': 'k-batch-tools'
			}).css({
				position: 'absolute',
				'z-index': '99'
			}).appendTo(document.body).hide();

			$("<button>", {
				'class': 'k-button k-batch-tools-add'
			}).text("增加").on('click', function(e) {
				var o = $tools.data("o");
				if (o && o.fn) {
					o.fn.add(null, o.target);
				}
			}).appendTo($tools);
			$("<button>", {
				'class': 'k-button k-batch-tools-remove'
			}).text("删除").on('click', function(e) {
				var o = $tools.data("o");
				if (o && o.fn) {
					o.fn.remove(o.target);
				}
			}).appendTo($tools);
		}

		return $tools;
	}
};

function Batch(tr, tdIndex) {
	this.tr = tr;
	this.rows = 0;
	this.template = null;
	this.tdIndex = tdIndex;
	this.fieldIds = tr.data("fieldIds");
	this.rule = tr.data("rule");
	this.fields = new Array();
	this.init();
}
Batch.prototype = {
	constructor: Batch,
	init: function() {
		var that = this;
		if (that.fieldIds) {
			$.each(that.fieldIds, function(index, v) {
				var o = that.tr.find("#" + v.id);
				if (o && o[0]) {
					o.attr({
						columnName: v.columnName
					});
				}
			});
			that.tr.find("[columnName]").each(function() {
				that.fields.push($(this).attr("columnName"));
			});
		}
		var tmpTr = that.tr.clone(true);
		for (var i = 0; i <= that.tdIndex; i++) {
			tmpTr.find("td").eq(i).remove();
		}

		that.template = tmpTr.html();
		var table = that.tr.closest("table");
		var tbId = table.attr("tbId") || (new Date().getTime());
		table.attr("tbId", tbId);
		that.tr.attr({
			'data-role': 'k-batch',
			tId: "tr" + (new Date().getTime() + that.tr.index()),
			tbId: tbId
		});
		return that;
	},
	set: function(data, target) {
		if (data) {
			for (var key in data) {
				var dom = target.data("table", {
					tableName: this.rule.tableName
				}).data("row", data).find("[columnName='" + key + "']");
				if (dom && dom[0]) {
					dom.mtkendo("setValue", data[key]);
				}
			}
		}
		return this;
	},
	add: function(datas, target) {
		var that = this;
		target = target || $("." + that.tr.attr("tid") + ":last");
		if (datas) {
			for (var i = 0; i < datas.length; i++) {
				target = appendTr(datas[i]);
			}
		} else {
			appendTr();
		}

		function appendTr(data) {
			var $tr = target,
				$current = target;
			if (that.rows == 0) {

			} else {
				$tr = $("<tr>", {
					'class': that.tr.attr('tId')
				});
				target.after($tr);
			}
			$tr.append(that.template);
			var tmpId = new Date().getTime(),
				optionsKey = "optionsKey";
			data = data || {};
			$tr
				.find("[id]")
				.each(
					function() {
						var $tmp = $("#" + this.id),
							columnname = $tmp
							.attr("columnname");
						var opts = $tmp.data(optionsKey),
							datas, tmpValue = $current ? $current
							.find(
								"[columnname='" + columnname + "']").mtkendo(
								"getValue") : null;

						$(this).show().attr({
							tempId: this.id,
							id: this.id + (tmpId++)
						});

						if (!!($tmp.attr("data-role") == "imageupload")) {
							// $(this).attr("randomparent", new
							// UUID().createUUID());
							// initImageUpload(this, this.id,
							// $(this).attr("randomparent"));

							initImageUpload(this, this.id, $(this)
								.attr("randomparent"));
						}

						if ($tmp.attr("tmpval") == "true") {
							data[columnname] = tmpValue || ($tmp).mtkendo("getValue");
						}
						if (!opts) {
							if (datas = $tmp.data()) {
								for (var k in datas) {
									if (k.toLowerCase() == ("kendo" + $(
											this).attr('data-role'))) {
										opts = {
											key: k,
											options: $.extend(true, {},
												datas[k].options)
										};
										$tmp.data(optionsKey, opts);
										break;
									}
								}
							}
						}
						if (opts) {
							$(this)[opts.key](opts.options);
						}
					});
			that.initTrEvents($tr).rowspan(1).set(data, $tr);
			return $tr;
		}
		return that;
	},
	remove: function(target) {
		if (confirm("确定删除?")) {
			if (this.tr[0] === target[0]) {
				alert('首行不能删除!');
			} else {
				var row = target.data("row"),
					table = target.data("table");
				if (row && table && window.batch) {
					var tableName = table.tableName;
					var del = {
						'delete': true,
						tableName: tableName,
						id: row["id"]
					};
					window.batch.deleteCollection.push(del);
				}
				target.remove();
				this.rowspan(-1);
				$(".k-batch-tools").hide();
			}
		}
		return this;
	},
	rowspan: function(n) {
		this.rows = this.rows + n;
		if (this.tdIndex > -1) {
			this.tr.find("td").eq(this.tdIndex).attr("rowspan", this.rows);
		}
		return this;
	},
	initTrEvents: function($this) {
		var that = this;
		var lastTd = $this.find('td:last'),
			$tools = $(".k-batch-tools");
		$this.on(
			'mouseover',
			function(e) {
				var p = lastTd.offset();
				$tools.css({
					left: p.left + lastTd.css('width').replace(/px/gi,
						'') * 1 - 50,
					top: p.top
				}).show();
			}).on('mouseout', function(e) {
			if (e.target == lastTd[0]) {
				$tools.data("o", {
					fn: that,
					target: $this
				});
			} else {
				$tools.hide();
			}
		});
		return that;
	}
};


function openDialog(e) {
	e.preventDefault();
	var $target = $(e.target).closest("a");
	var dialogHeight = $target.attr("dialogHeight");
	var dialogWidth = $target.attr("dialogWidth");
	var isModel = $target.attr("isModel");
	var url = $target.attr("ahref");
	var title = $target.attr("title") || $target.text();
	var iTop = (window.screen.availHeight - 30 - dialogHeight)
		// 获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth - 10 - dialogWidth)
		// 获得窗口的水平位置;
		// var iTop = e.screenY; // 获得窗口的垂直位置;
		// var iLeft = e.screenX; // 获得窗口的水平位置;
	if (isModel == "true") {
		if (url.startsWith(contextPath)) {
			url = url.replace(contextPath, "");
		}
		var link = {
			url: url,
			name: title,
			id: 'xx'
		};
		var rules = {
			link: JSON.stringify(link),
			model: true,
			title: title,
			jumpType: 'childPage',
			width: dialogWidth,
			height: dialogHeight
		};
		pageFunc.newWindow("", "", [rules]);
	} else {
		var option = "height=" + dialogHeight + ",width=" + dialogWidth + ",top=" + iTop + ",left=" + iLeft + ",toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no";
		window.open(url, title, option);
	}
}

function layerOpen($target) {
	closeLayer();
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: false,
		title: title,
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['', ''],
		fadeIn: 100,
		area: [dialogWidth, dialogHeight],
		iframe: {
			src: url
		}
	});
}

function closeLayer() {
	try {
		layer.close(layer.getFrameIndex());
	} catch (e) {

	}
}

function onResize(o, persent) {

	persent = persent || 1;

	o.height = document.body.scrollHeight * persent;

}

/**
 * 文件上传控件初始化前查询文件列表
 * 
 * @param elementObj
 * @param elementId
 * @param randomUUID
 */
function initKendoUpload(elementObj, elementId, randomUUID) {
	var id = elementObj.id;
	if (!randomUUID) { //
		randomUUID = new UUID().createUUID();
		$(elementObj).attr("randomparent", randomUUID);
		loadKendoUpload(elementObj.id, randomUUID);
	} else {
		if (elementObj) {
			$(elementObj).attr("randomparent", randomUUID);
			var files = [];
			$.post(contextPath + '/mx/form/attachment?method=getFilesByRandomParent', {
				randomParent: randomUUID
			}, function(data) {
				for (var i = 0; i < data.length; i++) {
					var obj = {};
					var fileName = data[i].fileName;
					obj.name = fileName;
					obj.size = data[i].fileSize;
					obj.id = data[i].id;
					obj.extension = fileName.substring(fileName
						.lastIndexOf("."), fileName.length);
					files.push(obj);
				}
				loadKendoUpload(elementObj.id, randomUUID, files);
			}, 'json');
		} else {
			id = elementId
			document.getElementById(elementId).setAttribute("randomparent",
				randomUUID);
			loadKendoUpload(elementId, randomUUID);
		}
	}

	$("body").on(
		"dblclick",
		".k-upload .k-dropzone",
		function() {
			$(this).parents("div .k-upload").find('ul.k-upload-files')
				.toggle('slow');
		});
}

/**
 * 加载文件上传控件
 * 
 * @param id
 * @param randomparent
 * @param files
 */
function loadKendoUpload(id, randomparent, files) {
	if (!files) {
		files = [];
	}

	var options = $("#" + id).data("options");
	var localization = $("#" + id).data("localization");

	var saveUrl = options.saveUrl + "&randomParent=" + randomparent + "&rid=" + options.rid,
		removeUrl = options.removeUrl + "&randomParent=" + randomparent,
		downloadUrl = options.downloadUrl,
		outputNames = options.outputName,
		outputIds = options.outputId;
	$("#" + id)
		.kendoUpload({
			multiple: options.multiple,
			showFileList: options.showFileList,
			files: files,
			async: {
				autoUpload: options.autoUpload,
				saveUrl: saveUrl,
				removeUrl: removeUrl,
				batch: options.batch
			},
			localization: localization,
			template: function(e) {
				var files = e.files;
				var html = "<span class='k-progress' style='width: 100%;'></span>";
				$.each(files, function(i, v) {
					html += "<span class='k-filename' title='" + this.name + "'>" + "<a href='" + downloadUrl + "&id=" + this.id + "' auid='" + this.uid + i + "'>" + this.name + "</a>" + "</span>";
				});
				html += "<strong class='k-upload-status'>" + "<button type='button' class='k-button k-button-bare k-upload-action'>" + "<span class='k-icon k-i-close k-delete' title='移除'></span>" + "</button>" + "</strong>";
				return html;
			},
			upload: function(e) {
				var files = e.files;
				if (options.fileextensionvalue) {
					var stuffixs = options.fileextensionvalue
						.trim().split(';');

					var flag = false;
					$
						.each(
							files,
							function() {
								var fileextension = this.extension
									.toLowerCase();
								$
									.each(
										stuffixs,
										function() {
											if (this
												.toLowerCase() == '' || this
												.toLowerCase() == fileextension) {
												flag = true;
												return;
											}
										});

								if (!flag) {
									alert("请上传后缀名为（" + options.fileextensionvalue + "）的文件");
									e.preventDefault();
									return;
								}

								if (this.size > options.maxfilesize) {
									alert("上传的文件不能超过" + (options.maxfilesize / (1024 * 1024)) + "M");
									e.preventDefault();
									return;
								}
							});
				}
			},
			remove: function(e) {
				var files = e.files;
				var filename = [];
				var idparam = "";
				$.each(files, function() {
					filename.push(this.name);
					idparam = idparam + "&id=" + this.id;
				});
				e.sender.options.async.removeUrl = removeUrl + idparam;

				if (!confirm("确定要删除文件[" + filename + "]吗？")) {
					e.preventDefault();
				}
			},
			success: function(e) {
				var files = e.files,
					names = "",
					ids = "",
					datas = e.response;
				if (e.operation == "upload") {
					for (var i = 0; i < files.length; i++) {
						names += files[i].name + ",";
						ids += datas[i].id + ",";
						files[i].id = datas[i].id;
						$("a[auid=" + files[i].uid + i + "]").attr(
							'href',
							downloadUrl + '&id=' + files[i].id);
					}
					if (outputNames && names) {
						var opns = outputNames.split(",");
						for (var k = 0; k < opns.length; k++) {
							outputName = opns[k];
							var $op = $("#" + outputName);
							var opval = $op.val();
							$op.val((opval ? (opval + ",") : "") + names.substr(0,
								names.length - 1));
						}
					}
					if (outputIds && ids) {
						var opids = outputIds.split(",");
						for (var k = 0; k < opids.length; k++) {
							var outputId = opids[k],
								$op = $("#" + outputId),
								opval = $op.val();
							$op
								.val((opval ? (opval + ",") : "") + ids.substr(0,
									ids.length - 1));
						}
					}
				} else if (e.operation == "remove") {
					for (var i = 0; i < files.length; i++) {
						names += files[i].name + ",";
						ids += files[i].id + ",";
					}
					if (outputNames && names) {
						var opns = outputNames.split(",");
						for (var k = 0; k < opns.length; k++) {
							outputName = opns[k];
							var $op = $("#" + outputName);
							var str = ($op.val() + ",").replace(
								names, "");
							$op.val(str.substr(0, str.length - 1));
						}
					}
					if (outputIds && ids) {
						var opns = outputIds.split(",");
						for (var k = 0; k < opns.length; k++) {
							var outputId = opns[k],
								$op = $("#" + outputId),
								str = ($op.val() + ",")
								.replace(ids, "");
							$op.val(str.substr(0, str.length - 1));
						}
					}
				}
			}
		});
}

function odblClick_(o) {
	var surl, url = contextPath + (surl = ($(o).attr("surl") || "/mx/form/defined/jsgis")) + ((surl.indexOf("?") > -1) ? "&" : "?") + $.param({
		targetId: o.id,
		fn: "odblClickCallFunc_"
	});

	window.open(url);
}

function odblClickCallFunc_(datas) {
	var $this = $(this);
	if (datas) {
		var data = datas.data;
		var rules = datas.rule || $this.attr("dbrule");
		var rs = eval(rules);
		for (var i = 0; i < rs.length; i++) {
			var r = rs[i];
			if (r.name == "FID") {
				if ($.isArray(data)) {
					var outAry = [],
						da;
					for (var j = 0; j < data.length; j++) {
						da = data[j];
						outAry.push(da.layerId + "-" + da.FID);
					}
					$('#' + r.output).val(outAry.join(","));
				} else {
					$('#' + r.output).val(data.layerId + "-" + data.FID);
				}
			} else {
				if ($.isArray(data)) {
					var outAry = [],
						da;
					for (var j = 0; j < data.length; j++) {
						da = data[j];
						outAry.push(da[r.name]);
					}
					$('#' + r.output).val(outAry.join(","));
				} else {
					$('#' + r.output).val(data[r.name]);
				}
			}
		}
	} else {
		if ($this[0]) {
			var rules = $this.attr("dbrule");
			if (rules) {
				var rs = eval(rules),
					data = {};
				for (var i = 0; i < rs.length; i++) {
					var r = rs[i];
					data[r.name] = $("#" + r.output).mtkendo("getValue");
				}
				return data;
			}
		}
	}
}

// --end--