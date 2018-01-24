/**
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
    return pageParameters['sys_date'];
};

/**
 *获取当前系统域名
 */
var getContextPath = function() {
    return window.location.host + contextPath;
}

//表达式函数集
var mtExpressions = mtException = (function() {
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
    mt.seqGenerator = function(s, l, n) {
        var sl = (s + "").length,
            ary = [];
        !l && (l = sl);
        s = parseInt(s);
        s = (isNaN(s) ? 0 : s) + 1;
        var difLen = l - (s + "").length;
        ary.length = difLen <= 0 ? 0 : (difLen + 1);
        return ary.join(n ? n : "0") + s;
    };
    //文本连接（合并）
    mt.conc = function(s, l) {
        return s + "" + l;
    };
    //文本长度
    mt.len = function(s) {
        s += "";
        return s ? s.length : 0;
    };
    //位置查找
    mt.find = function(s, p) {
        return s && p ? s.indexOf(p) : -1;
    };
    //位置查找忽略英文大小写
    mt.search = function(s, p) {
        s += "";
        p += "";
        return s && p ? s.toUpperCase().indexOf(p.toUpperCase()) : -1;
    };
    //返回小写
    mt.lower = function(s) {
        s += "";
        return s ? s.toLowerCase() : s;
    };
    mt.upper = function(s) {
        s += "";
        return s ? s.toUpperCase() : s;
    };
    //截取字符串
    mt.subString = function(s, f, t) {
        s = (s || "") + "";
        return t != undefined ? s.substring(f, f + t) : s.substring(f);
    };
    //去除空格
    mt.trim = function(s) {
        return $.trim(s);
    };
    //去除空格
    mt.replace = function(s, o, n) {
        return s.replace(new RegExp(o), n);
    };
    mt.replaceAll = function(s, o, n) {
        return s.replace(new RegExp(o, "gm"), n);
    };
    mt.getStrByIndex = function(s, i, n) {
        s = (s || "") + "";
        var ss = s.split(n || ","),
            ssLen = ss.length;
        return (i <= ssLen && i >= 1) ? ss[i - 1] : "";
    };
    //获取子字符串的个数
    mt.getOccurSize = function(s1,s2){
        var o = 0;
        var index=-1;
        while((index=s1.indexOf(s2,index))>-1){
            ++index;
            ++o;
        }
        return o;
    };
    //数字转大写
    mt.digitUppercase = function(n) {
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
    var funcs = {
        //GIS地图转换为轨迹图
        convertGis: function(arys) {
            var retAry = [],
                str;
            $.each(arys, function(i, v) {
                for (var p in v) {
                    str = v[p];
                    if (str) {
                        str = eval("(" + str + ")");
                        retAry.push([str[0]["Longitude"], str[0]["Latitude"]]);
                    }
                }
            })
            return JSON.stringify(retAry);
        }
    }
    mt.func = function() {
        var params = Array.prototype.slice.call(arguments, 1),
            methodName = arguments[0];
        return funcs[methodName].apply(this, params);
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
    mt.formatNumber = function(s, n) {
        if (s && $.isNumeric(s)) {
            return s.toFixed(n || 2);
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

var mtIsMobile = (function() {
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
    mt.isMobile = function() {
        return isMobile.any();
    }
    return mt;
})();

var mtBrowser = (function() {
    //取得浏览器的userAgent字符串  
    var userAgent = navigator.userAgent,
        //判断是否Opera浏览器  
        isOpera = userAgent.indexOf("Opera") > -1,
        browser = "Opera";
    //判断是否Firefox浏览器  
    if (userAgent.indexOf("Firefox") > -1) {
        browser = "FF";
    }
    if (userAgent.indexOf("Chrome") > -1) {
        browser = "Chrome";
    }
    //判断是否Safari浏览器  
    if (userAgent.indexOf("Safari") > -1) {
        browser = "Safari";
    }
    //判断是否IE浏览器  
    if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
        browser = "IE";
    };
    var mt = {};
    mt.isIE = function() {
        return browser == "IE";
    };
    mt.isChrome = function() {
        return browser == "Chrome";
    };
    mt.browser = function() {
        return browser;
    };
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
    mt.cellToDate = function(d, format) {
        return hmtdUtils.toString(hmtdUtils.parseDate((d - 25569) * 864e5), format || "yyyy-MM-dd HH:mm:ss");
    };
    //格式化日期
    mt.formatDatetime = function(d, format) {
        return hmtdUtils.toString(hmtdUtils.parseDate(d), format || "yyyy-MM-dd HH:mm:ss");
    };

    mt.addDays = function(d1, days, format) {
        d1 = hmtdUtils.parseDate(d1);
        try{
            days = parseInt(days);
            var newDate = new Date(d1.getTime() + days * 24 * 60 * 60 * 1000);
            return hmtdUtils.toString(newDate, format || "yyyy-MM-dd HH:mm:ss");
        }catch(e){

        }
        return "";
    };
    mt.addYears = function(d1,years,format){
        d1 = hmtdUtils.parseDate(d1);
        try{
            years = parseInt(years);
            d1.setFullYear(d1.getFullYear() + years);
            return hmtdUtils.toString(d1, format || "yyyy-MM-dd HH:mm:ss");
        }catch(e){

        }
        return "";
    };
    mt.addMonths = function(d1,months,format){
        d1 = hmtdUtils.parseDate(d1);
        try{
            months = parseInt(months);
            d1.setMonth(d1.getMonth() + months);
            return hmtdUtils.toString(d1, format || "yyyy-MM-dd HH:mm:ss");
        }catch(e){

        }
        return "";
    };
    mt.addSeconds = function(d1,seconds,format){
        d1 = hmtdUtils.parseDate(d1);
        try{
            seconds = parseInt(seconds);
            var newDate = new Date(d1.getTime() + seconds * 1000);
            return hmtdUtils.toString(newDate, format || "yyyy-MM-dd HH:mm:ss");
        }catch(e){

        }
        return "";
    };
     mt.addValueByType = function(d1,type,value,format){
        if(type == 'day'){
            return mt.addDays(d1,value,format);
        }
        if(type == 'month'){
            return mt.addMonths(d1,value,format);   
        }
        if(type == 'year'){
            return mt.addYears(d1,value,format);   
        }
        if(type == 'hours'){
            var hours = parseInt(value);
            value = hours * 60 * 60;//换算为秒
            return mt.addSeconds(d1,value,format);   
        }
        if(type == 'minutes'){
            var minutes = parseInt(value);
            value = minutes * 60;//换算为秒
            return mt.addSeconds(d1,value,format);   
        }
        if(type == 'second'){
            return mt.addSeconds(d1,value,format);   
        }
        return "";
    };
    return mt;
})();


//文件查看
function viewFile(e) {
    var $that = $(e),
        ridObj = $that.parents("[rid]"),
        rid = ridObj.attr("rid") || "";
    var model = $that.attr("model");
    var path = "";
    if(model == 'SOA'){
        path = 'http://' + $that.attr("soahost") + ':' + $that.attr("soaport") + '/' + $that.attr("soapath");
    }else{
        path = contextPath + "/mx/form/defined/viewFile?model=" + model + "&rp=" + $that.attr("tval") + "&id=" + rid + "&key=" + $that.attr("key") + "&url=" + $that.attr("url") + "&fid=" + $that.attr("fid");    
    }
    window.open(path, '查看附件', "modal=true,status=no");
};

function viewFiles(e) {
    function fileTypes(html, type) {
        var doc = [".doc", ".docx", ".xls", ".xlsx", ".ppt"];
        if (type == "tif") {
            doc = [".tif", ".tiff"];
        } else if (type == "pdf") {
            doc = [".pdf"];
        } else if (type == 'txt') {
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
        params = "?model=FTP&rp=" + rp + "&id=" + rid + "&key=" + key + "&url=" + url + "&fid=" + fid + "&databaseId=" + (dbid || "");
    }
    if (fileTypes(html, "doc")) {
        path = contextPath + "/mx/form/defined/viewFile";
        //path = contextPath + "/mx/form/defined/viewFile?id=";
    } else if (fileTypes(html, "tif")) {
        path = contextPath + "/mx/form/defined/viewTif";
    } else if (fileTypes(html, "pdf")) {
        path = contextPath + "/mx/form/defined/viewPdf";
    } else if (fileTypes(html, "txt")) {
        path = contextPath + "/mx/form/defined/viewText"
    }
    if(window.WebViewJavascriptBridge){
    	window.open(path + params);
    }else{    	
    	window.open(path + params, '查看图片', "modal=true,status=no,scrollbars=yes,resizable=yes");
    }
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
        
        var shap=format.indexOf(SHARP);
        var zero=format.indexOf("0");
        
        //数字串
        var numberStr=number.toString();
        var index  =numberStr.indexOf(POINT);
        //格式串替换成0串
       
      
        //#.##
       if(shap!=-1&&zero==-1){
    	    format=format.replace(/#/ig,'0');
    	    var findex =format.indexOf(POINT);
    	   
	        //格式串有小数位
	        if(findex!=-1){
	        	//求出格式串小数位数长度
	        	    var formatelength=format.substring((findex+1)).length;
	        	    //原数据有数位
			        if(index!=-1){
			        	var numlength=numberStr.substring((index+1)).length;
			        		if(formatelength>numlength){
			        			for(var i=0;i<(formatelength-numlength);i++){
			        				 numberStr=numberStr+"0";
			        			}
			        		}else{
			        			var len=numlength-formatelength;
			        			numberStr=numberStr.substring(0,(numberStr.length-len));
			        		}
			        }else{
			        	numberStr=numberStr+".";
			        	 for(var i=0;i<formatelength;i++){
			        		 numberStr=numberStr+"0";
			        	 }
			        }
	        }
	   //#.00
       }else{
    	   var num=numberStr.substring(0,index);
    	   numberStr=format.replace("#",num);
       }
        return numberStr;
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
})();


function getUserInfoArray(){
    var b = $.cookie("userInfo");
    if(b){
        return b.split(",");
    }
    return [];
}

function getLastUserInfo(){
    var b = $.cookie("userInfo");

    return b || "";
}var mtxx = {},
	baseFunc = function() {
		return {
			thidden: function(rule, args) { // 隐藏
				pubsub.getJQObj(rule).hide();
			},
			tshow: function(rule, args) { // 显示
				var $id = pubsub.getJQObj(rule); 
				if($id.attr("data-role") && $id.attr("data-role") == "a"){
					$id.css("display","block");
				}else{
					$id.show();
				}
				
			},
			tdisabled: function(rule, args) { // 禁用
				pubsub.getJQObj(rule).attr("disabled", "disabled");
			},
			tenabled: function(rule, args) { // 启用
				pubsub.getJQObj(rule).removeAttr("disabled");
			},
			_init_: function(rule, param, args) { //控件初始化事件
				var $id = pubsub.getJQObj(rule);
				eval("(" + __gobalInit__[$id.attr("id")] + ")")($.isArray(param) ? {} : param);
				pubsub.execChilds(rule);
			},
			setIndex : function(rule,args){
				var $id = pubsub.getJQObj(rule);
				var v = "";
				for (var i = 0; i < rule.length; i++) {
					r = rule[i];
					v = args[r.param]||"";
				}
				$id.css("z-Index",v);
			},
		}
	};

// 页面初始化赋值
function pageInit() {
	// 控件缺省初始化
	$("[data-role]").each(function(i, o) {
		var dataRole = $(o).attr("data-role");
		if (!$(o).data("textboxbt") && dataRole == "textboxbt") {
			$(o).textboxBtExt();
		} else if (!$(o).data("touchspin") && dataRole == "touchspin") {
			$(o).touchSpinExt();
		} else if (!$(o).data("textareabt") && dataRole == "textareabt") {
			$(o).textAreaBtExt();
		} else if (!$(o).data("progressbarbt") && dataRole == "progressbarbt") {
			$(o).progressBarExt();
		} else if (!$(o).data("datetimepickerbt") && dataRole == "datetimepickerbt") {
			$(o).datetimepickerExt();
		} else if (!$(o).data("datepickerbt") && $(o).closest("div.input-daterange").length == 0 && dataRole == "datepickerbt") {
			$(o).datepickerExt();
		} else if (!$(o).data("daterangepicker") && dataRole == "daterangepicker") {
			$(o).daterangepickerExt();
		} else if (!$(o).data("calendarbt") && dataRole == "calendarbt") {
			$(o).calendarExt();
		}
	});

	if (pageParameters) {
		// initPageDetail(p);
		var params = pageParameters;
		if (params) {
			mtxx.params = params;
			var ret = params['detail'];
			initPageDetailSuccess(ret);
		}
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
			success: initPageDetailSuccess
		});
	}
}

function initPageDetailSuccess(ret) {
	if ((window.$ret = ret) && ret.data) {
		// var ff = new FlowFilter(ret.flowDefined || {});
		ret.jdoms = [];
		$.each(ret.data, function(id, v) {
			jdom = $("#" + id);
			if (jdom[0]) {
				// if (ff.filter(jdom, id)) {
				var val;
				if (v[0].value || v[0].value == 0) {
					val = v[0].value;
				} else {
					val = jdom.val();
				}
				jdom.mtbootstrap('setValue', val, v);
				if (!id.startsWith("ztree")) {
					jdom.data("params", v[0]);
				}
				// }
				ret.jdoms.push({
					jdom: jdom,
					id: id
				});
			}
		});

		if (ret.handleColumns) {
			var $body = document.body,
				saveKey = "saveMsg";
			!$.data($body, saveKey) && ($.data($body, saveKey, []));
			var btn = new MtButton({
				handlecolumns: JSON.stringify(ret.handleColumns)
			});
			$.data(document.body, saveKey).push(btn);
		}

		linkAge(ret); // 联动页面控件、
		initPageFlow(ret); // 页面工作流
	}

	initProgressBarFunc();
	// initKendoUploadFunc();
	initImageUploadFunc();
	initImageUploadFunc2();
	initMutilFileViewsFunc();
	initPageLevel();
	jqCb.fire();
}

/**
 * 页面工作流
 * 
 * @param o
 */
function initPageFlow(o) {
	o.__idValue = o.__idValue || o.idValue;
	if (o.jdoms.length) {
		$.ajax({
			url: contextPath + '/mx/form/data/initPageFlow',
			type: 'post',
			dataType: 'JSON',
			data: {
				idValue: o.__idValue,
				pageId: mtxx.params.id,
				processId : mtxx.params.processId
			},
			success: function(ret) {
				if (ret.flowDefined) {
					$.data(document.body, "flowFilter", new FlowFilter(ret.flowDefined).exec(o.jdoms));
				}
			}
		});
	}
}

function FlowFilter(flow) {

	this.flow = flow;

	this.elements = flow.elements;

	this.unValisaves = [];

	this.exec = function(doms) {
		var that = this;
		$.each(doms, function(i, v) {
			that.filter(v.jdom, v.id);
		});
		if (that.elements) {
			$.each(that.elements, function(id, element) {
				if (!element.ready) {
					var jq = $("#" + id);
					jq[0] && (that.filter(jq, id));
				}
			});

			if (that.unValisaves.length) { // 去掉当前节点不可写的验证
				var valisaveKey = "valisave";
				$.each(that.unValisaves, function(i, $jq) {
					$jq.removeAttr(valisaveKey);
					$("[" + valisaveKey + "=true]", $jq) //
						.removeAttr(valisaveKey);
				});
			}
		}
		return that;
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
			try {
				var value = jq.mtbootstrap("getValue");
				if(f.defaultVal && !value){
					jq.mtbootstrap("setValue", f.defaultVal);
				}
				// jq.mtbootstrap("setValue", value || f.defaultVal || '');
			} catch (e) {
				console.log(e);
			}

			// if (f.target) { // 接收人 (已经放到全局参数里面)
			// that.target(id);
			// }
			if (!f.show) { // 不显示
				jq.hide();
				return false;
			} else {
				// jq.show();
			}
			if (!f.write) {
				try {
					if(jq.attr("id") && jq.closest("tr").attr("tid")){
						jq.closest("tbody").find("[tempid='"+jq.attr("id")+"']").mtbootstrap("disable", true);	
					}
					jq.mtbootstrap("disable", true);	
				} catch (e) {
					if (jq.is("div")) {
						jq = jq.find("." + id);
					}
					jq.attr({
						readonly: true
					});
				}
				this.unValisaves.push(jq);
			} else { // 流程里面可写的保存才做验证

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
				if(f._taskId && f._taskId != v.taskId)//当前节点的  taskId_user  参数不传
					addVariables(v.eleId, v.taskId);
			});
		}

		/**
		 * 指定节点
		 */
		if (f.targetTasks && f.targetTasks.length) {
			mtxx.params.flow.targetTasksVars = [];
			$.each(f.targetTasks, function(i, v) {//必须是当前节点下
				if(f._taskId && f._taskId == v.taskId)
					addVar(v.eleId, v.taskId, mtxx.params.flow.targetTasksVars);
			});
		}

	})(this);
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
		pageRuleId: pageRuleId,
		targetTask : null
	};
	
	var populate = function(collection, params){
		if(collection && collection.length){
			function _each(i, v){
				if (v.datas) {
					var expActVal = v.datas.expActVal;
					var o = new RegExp("~M\{([^\\}]*)\}", "g").exec(expActVal);
					if (o && o[1]) {
						try {
							var value_ = $("#" + o[1]).mtbootstrap("getValue");
							params[v.varname] = value_;
							params[v.varcode] = value_;
						} catch (e) {
							console.log(e);
						}
					}
				}
			}
			$.each(collection, _each);
		}
	};
	
	// targetTasks
	
//	if (flow.variables) {
//		$.each(flow.variables, function(i, v) {
//			if (v.datas) {
//				var expActVal = v.datas.expActVal;
//				var o = new RegExp("~M\{([^\\}]*)\}", "g").exec(expActVal);
//				if (o && o[1]) {
//					try {
//						params[v.varname] = $("#" + o[1]).mtbootstrap("getValue");
//						params[v.varcode] = $("#" + o[1]).mtbootstrap("getValue");
//					} catch (e) {
//						console.log(e);
//					}
//				}
//			}
//		});
//	}
	
	populate(flow.variables, params);
	
	var tt = {};
	
	populate(flow.targetTasksVars, tt);

	function GetTargetTask(t){
		var targetTask = null;
		for(var k in t){
			if(targetTask = t[k]){
				break;
			}
		}
		return targetTask;
	}
	params.targetTask = GetTargetTask(tt);
	
	/*if(params.targetTask){//指定节点的指派人还得加进去
		$.each(["user", "role"], function(i, v){
			if(params[flow._taskId + "_" + v]){
				params[params.targetTask + "_" + v] //
				= params[flow._taskId + "_" + v];
				delete params[flow._taskId + "_" + v];
			}
		});
		if(params[flow._taskId]){
			delete params[flow._taskId];
		}
	}*/
	
	return params;
}

/**
 * 获取流程动态更新节点的参数
 * 
 * @returns
 */
function getFlowDataSetParams() {
	var ff = $.data(document.body, "flowFilter"),
		flow;
	var params = window.getIdValues();
	if (ff && (flow = ff.flow)) {
		if (flow.taskWdataSet) {
			return $.extend(params, //
				pubsub.getParameters(flow.taskWdataSet[0].datas));
		}
	}
	return null;
}

/**
 * 获取页面隐藏主键值
 * 
 * @returns
 */
function getIdValues() {
	var params = {};

	function values(i, v) {
		params[$(v).attr("fieldname")] = $(v).val();
	}
	$("input.id-field:hidden[fieldname]").each(values);
	return params;
}

/**
 * 角色类型 部门、角色、用户
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

function addVariables(id, taskId) {
	// var jq = $("#" + id);
	// var selectType = getSelectType(jq);
	!mtxx.params.flow && (mtxx.params.flow = {});
	!mtxx.params.flow.variables && (mtxx.params.flow.variables = []);
// mtxx.params.flow.variables.push({
// datas: {
// expActVal: "~M{" + id + "}"
// },
// varcode: taskId,
// varname: taskId + "_" + selectType
// });
	addVar(id, taskId, mtxx.params.flow.variables);
};

function addVar(id, taskId, collection){
	var jq = $("#" + id);
	var selectType = getSelectType(jq);
// !mtxx.params.flow && (mtxx.params.flow = {});
// !mtxx.params.flow.variables && (mtxx.params.flow.variables = []);
	collection.push({
		datas: {
			expActVal: "~M{" + id + "}"
		},
		varcode: taskId,
		varname: taskId + "_" + selectType
	});
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
							class: (key || "") + " id-field", // 主键
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
				class: 'pageruleid',
				type: 'hidden',
				value: ret.pageRuleId
			}).appendTo("body");
		}
		if (ret.batchRules && ret.batchRules[0]) { // 变长区数据获取
			if (ret.batchRules[0].cell) {
				// new CellBatchFunc(ret.batchRules[0]);
			} else {
				new BatchFunc(ret.batchRules);
			}
		}
	}
}

function odblClick_(o) {
	var $this = $(o),
		$t = $this.closest(".nlayout_elem");
	var surl, url = contextPath + (surl = ($(o).attr("surl") || "/mx/form/defined/jsgis")) + ((surl.indexOf("?") > -1) ? "&" : "?") + $.param({
		targetId: $t.attr("data-role") == "form" ? $this.attr("id") : $t.attr("id"),
		fn: "odblClickCallFunc_"
	});

	window.open(url);
}

function getControlInput($this) {
	return $($this.find("input." + $this.attr("id")).get(0) || $this);
}

function odblClickCallFunc_(datas) {
	// var $that = $(this),$this =
	// $($that.find("input."+$that.attr("id")).get(0) || $that);
	var $that = $(this),
		$this = window.getControlInput($that);
	if (datas) {
		var data = datas.data;
		var rules = datas.rule || $this.attr("dbrule");
		var rs = eval(rules);
		for (var i = 0; i < rs.length; i++) {
			var r = rs[i];
			if (r.name == "FID") {
				$('#' + r.output).val(data.layerId + "-" + data.FID);
			} else {
				var $output = $('#' + r.output);

				// $output.find("input."+$output.attr("id")).val(data[r.name]);
				window.getControlInput($output).val(data[r.name]);
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
					data[r.name] = $("#" + r.output).val();
				}
				return data;
			}
		}
	}
}

function initImageUploadFunc2() {
	$('body').delegate('.clickView', 'click', zoomViewImg);
	$('body').delegate('.dblclickView', 'dblclick', zoomViewImg);
	imageUploadInitFunc();
}
// 图片放大显示
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
					$img.width(width - 5).height('');
				}
			}
		}

		$(window).resize(initImgSize);
		initImgSize();

		$(".hock_wrap").on("click", function(e) {
			$(".hock_wrap").remove();
		});
	}
};


function initMutilFileViewsFunc() {
	var options = {
		'childList': true,
		'arrtibutes': true,
		'subtree': true,
		'characterData': true
	};
	var observer = new MutationObserver(
		function() {
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

// //////变长区

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
					// if (initData) {
					// 	initData.sort(function(a, b) {
					// 		return (a.listno - b.listno);
					// 	});
					// }
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

		var tmps, index; // 先删除，在push
		function remove() {
			tables.splice(index, tmps.length);
		}

		function iterator(key) {
			tmps = [], index = -1;
			$.each(tables, function(i, v) {
				if (v.table && v.table.tableName == key) {
					tmps.push(v);
					if (index == -1) {
						index = i;
					}
				}
			});
			if (tmps.length) {
				remove();
			}
		}
		var batchId = new Date().getTime();
		for (var i = 0; i < rules.length; i++) {
			var rule = rules[i],
				cId = rule.cId;
			if (tables.length > 0) {
				iterator(rule.tableName);
			}
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
									
									batchId ++;
									
									collection.push({ // topId
										fieldName: 'batchId',
										value: batchId
									});
									
									$(this).addClass("_" + batchId, batchId);
									
									$(this)
										.find(
											"[columnName]")
										.each(
											function() {
												var cn = $(this),
													fieldName = cn
													.attr('columnName');
												if (fieldName) {
													collection
														.push({
															fieldName: fieldName,
															value: cn.mtbootstrap("getValue")
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
			}).text("↑").on('click', function(e) {
				e.preventDefault();
				var o = $tools.data("o");
				if (o && o.fn) {
					o.fn.insert(null, o.target,"top");
				}
			}).appendTo($tools);
			$("<button>", {
				'class': 'k-button k-batch-tools-add'
			}).text("↓").on('click', function(e) {
				e.preventDefault();
				var o = $tools.data("o");
				if (o && o.fn) {
					o.fn.insert(null, o.target,"bot");
				}
			}).appendTo($tools);
			$("<button>", {
				'class': 'k-button k-batch-tools-remove'
			}).text("-").on('click', function(e) {
				e.preventDefault();
				var o = $tools.data("o");
				if (o && o.fn) {
					o.fn.remove(o.target);
				}
			}).appendTo($tools);
			$("<button>", {
				'class': ''
			}).text("X").on('click', function(e) {
				e.preventDefault();
				var o = $tools.hide();
			}).appendTo($tools);
		}

		return $tools;
	}
};

/**
 * 控件角色（data-role）与初始化jq 插件初始化方法映射
 */
var $copyRole = (function() {
	var mapping = {
		touchspin: 'touchSpinExt',
		"datepickerbt" : "datepickerExt"
	}, enableds = {
		touchspin : function(){
			return !!!$(this).find("input").attr("disabled");
		},
		textboxbt : function(){
			return !!!$(this).attr("disabled");
		}
	};
	var func = function() {};
	func.add = function(role, initMethod) {
		mapping[role] = initMethod;
	};
	func.get = function(role) {
		return mapping[role];
	};
	
	func.getOptions = function(opts){
		
		var options = $.extend(true, {}, opts.options, {
			id : this.id
		});
		
		var enabled = func.enabled($("#" + opts.options.id));
		
		if(enabled !== undefined){
			
			options.enabled = enabled;
		}
		
		return options;
	};
	
	func.enabled = function($jq){
		var r = $jq.data("role"), fn = enableds[r];
		if(fn){
			return fn.call($jq.get(0));
		}
	};
	
	return func;
})();

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
					dom.mtbootstrap("setValue", data[key]);
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
							datas;

						/*
						 * $(this).show().attr({ tempId : this.id, id : this.id +
						 * (tmpId++) });
						 */
						var id = this.id + (tmpId++);
						$(this).show().attr({
								tempId: this.id,
								id: id
							}).removeClass(this.id).find("." + this.id) //
							.removeClass(this.id).addClass(id);

						if ($tmp.attr("tmpval") == "true") {
							var tmpValue = $current ? $current.find("[columnname='" + columnname + "']").mtbootstrap("getValue") : null ;
							if(!data[columnname]){
								//若值不存在是，才取模板值。
								data[columnname] = tmpValue || ($tmp).mtbootstrap("getValue");	
							}
						}
						/*
						 * if (!opts) { if (datas = $tmp.data()) { for ( var k
						 * in datas) { if (k.toLowerCase() == ("kendo" + $(
						 * this).attr('data-role'))) { opts = { key : k, options :
						 * $.extend(true, {}, datas[k].options) };
						 * $tmp.data(optionsKey, opts); break; } } } } if (opts) {
						 * $(this)[opts.key](opts.options); }
						 */
						if (!opts) {
							if (datas = $tmp.data()) {
								if (datas.role && $tmp.data(datas.role)) {
									opts = {
										key: datas.role,
										options: $.extend(true, {},
											$tmp.data(datas.role).options)
									};
									$tmp.data(optionsKey, opts);
								}
							}
						}
						if (opts) {

							var key = $copyRole.get(opts.key) || opts.key;
							if($.fn[key]){
								var options = $copyRole.getOptions//
									.call(this, opts);
								$(this)[key](options);
							} else {
								var enabled = $copyRole.enabled($tmp);
								if(!enabled){
									$(this).attr("disabled", "disabled");
								}
							}
						}
					});
			that.initTrEvents($tr).rowspan(1).set(data, $tr);
			return $tr;
		}
		return that;
	},
	insert: function(datas,target,state) {
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
				if(state == "top"){
					if(target[0].previousElementSibling.getAttribute("class")!=null){
						target.before($tr);						
					}
				}
				else if(state == "bot"){
					target.after($tr);
				}			
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
							datas;

						/*
						 * $(this).show().attr({ tempId : this.id, id : this.id +
						 * (tmpId++) });
						 */
						var id = this.id + (tmpId++);
						$(this).show().attr({
								tempId: this.id,
								id: id
							}).removeClass(this.id).find("." + this.id) //
							.removeClass(this.id).addClass(id);

						if ($tmp.attr("tmpval") == "true") {
							var tmpValue = $current ? $current.find("[columnname='" + columnname + "']").mtbootstrap("getValue") : null ;
							data[columnname] = tmpValue || ($tmp).mtbootstrap("getValue");
						}
						/*
						 * if (!opts) { if (datas = $tmp.data()) { for ( var k
						 * in datas) { if (k.toLowerCase() == ("kendo" + $(
						 * this).attr('data-role'))) { opts = { key : k, options :
						 * $.extend(true, {}, datas[k].options) };
						 * $tmp.data(optionsKey, opts); break; } } } } if (opts) {
						 * $(this)[opts.key](opts.options); }
						 */
						if (!opts) {
							if (datas = $tmp.data()) {
								if (datas.role && $tmp.data(datas.role)) {
									opts = {
										key: datas.role,
										options: $.extend(true, {},
											$tmp.data(datas.role).options)
									};
									$tmp.data(optionsKey, opts);
								}
							}
						}
						if (opts) {

							var key = $copyRole.get(opts.key) || opts.key;
							if($.fn[key]){
								var options = $copyRole.getOptions//
									.call(this, opts);
								$(this)[key](options);
							} else {
								var enabled = $copyRole.enabled($tmp);
								if(!enabled){
									$(this).attr("disabled", "disabled");
								}
							}
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
		var $table = $this.closest("table");
		$this.off("mouseover").on(
			'mouseover',
			function(e) {
				var p = lastTd.offset();
				$tools.css({
					left: p.left + lastTd.css('width').replace(/px/gi,
						'') * 1 - 50,
					top: p.top
				}).show();
				return true;
			}).off("mouseout").on('mouseout', function(e) {
			var $target = $(e.target);
			if (!$target.is("td")) {
				$target = $target.closest("td");
			}
			if ($target.get(0) == lastTd[0]) {
				$tools.data("o", {
					fn: that,
					target: $this
				});
			} else {
				// if($table.get(0) != $target.closest("table").get(0))
				$tools.hide();
			}
			return true;
		});

		return that;
	}
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
					var $div = $('<div class="progress"><div class="progress-bar progress-bar-primary progress-bar-striped"><span class="sr-only">40%</span></div></div>');
					
					if($this.closest(".grid")[0]){
						//如果是grid和treelist内部，则去掉外间距
						$div.css("margin-bottom","0px");
						$div.find(".progress-bar").css("color","black");
					}
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
						$div.progressBarExt(opts)
							/*
							 * $div.kendoProgressBar(opts);
							 * $div.find(".k-state-selected").css({
							 * "background-color" : "#ef1f0c", "border-color" :
							 * "#ef1f0c" });
							 */
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
								splitStr = $this.attr("split") || " ",
								html, tval, i = 0,
								key = $this.attr("key"),
								url = $this.attr("url"),
								model = $this.attr("model"),
								dbid = $this.attr("dbid"),
								fid = $this.attr("fid"),
								htmls = htmlStr.split(","),
								tvals = tvalStr.split(","),
								length = htmls.length,
								targetStr = "";
							for (; i < length; i++) {
								if (htmls[i]) {
									targetStr += "<a href='#' fid='" + fid + "' kid='" + tvals[i] + "' dbid='"+dbid+"' model='" + model + "' key='" + key + "' url='" + url + "' onclick='viewFiles(this)'>" + htmls[i] + "</a>" + splitStr;
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
}var MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver;
var jqCb = $.Callbacks(),
	mapCb = $.Callbacks();
//eg: pubsub.pub("linkageControl",datas);
/**
 *从页面级参数中获取
 */
function getParam(id, param) {
	if (pageParameters) {
		var paramObj = pageParameters,
			detail = paramObj['detail'],
			data, obj, v;
		if (detail && (data = detail['data'])) {
			obj = data[id];
			if (obj && obj.length) {
				for (var i = 0, length = obj.length; i < length; i++) {
					v = obj[i];
					if (v.param == param) {
						//return detail[v.inparam];
						return v.inparam ? detail[v.inparam] : v.value;
					}
				}
			}
		}
	}
	return null;
}

function getParams(cid) {
	if (pageParameters) {
		var paramObj = pageParameters,
			detail = paramObj['detail'],
			data, obj, v, retVal = {};
		if (detail && (data = detail['data'])) {
			if (cid) {
				obj = data[cid];
				if (obj && obj.length) {
					for (var i = 0, length = obj.length; i < length; i++) {
						v = obj[i];
						//retVal[v.param] = detail[v.inparam];
						retVal[v.param] = v.inparam ? detail[v.inparam] : v.value;
					}
				}
			} else {
				for (var id in data) {
					obj = data[id];
					if (obj && obj.length) {
						for (var i = 0, length = obj.length; i < length; i++) {
							v = obj[i];
							// 增加 如果是数据集中转发的参数 则从数据集转发中获取
							if (v.type == "__defaultValue__") {
								retVal[v.param] = v.columnName;
							} else {
								retVal[v.param] = v.inparam ? detail[v.inparam] : v.value;
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
	/**
	 *  输出参数为对象或者数组时转换
	 * @param  {[object]} rule [规则]
	 * @param  {[object]} obj  [输出参数的对象]
	 * @param  {[object]} o    [数据对象]
	 * @param  {[object]} o2    [数据对象2]
	 * @return {[void]}      [无返回值]
	 */
	q.outParamsObj = function(rule, obj, o, o2) {
		var isObj = obj && $.isPlainObject(obj),
			isAry = obj && $.isArray(obj);
		o || (o = {});
		o2 || (o2 = {});
		isObj && (obj[rule.param] = o[rule.columnName] || o2[rule.columnName] || "");
		if (isAry) {
			var i = this;
			obj[i] || (obj[i] = {});
			obj[i][rule.param] = o[rule.columnName] || o2[rule.columnName] || "";
		}
	};
	q.inParamsObj = function(rule, o, o2) {
		var obj;
		o || (o = {});
		o2 || (o2 = {});
		if (rule.idataId) {
			obj = o[rule.idataId] || o2[rule.idataId];
		}
		return obj;
	};
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
					var pageParams = pageParameters,
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
			that = that.opener || that.parent;
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
			//如果是grid内联事件
			if ((isIn && rule.ie) || (!isIn && rule.oe)) {
				$ret = $ret.find("[name=" + rule[isIn ? "inid" : "outid"] + "]");
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
		return "&" + (window.BASE64?"__mt__="+BASE64.encoder(JSON.stringify(params)) : $.param(params));
	};
	q.expConvert = function(exp, params, toEscaping) {
			if (params) {
				var escapingStr = "\"",
					reger, val, str;
				for (var p in params) {
					reger = new RegExp(p, "gm");
					val = params[p];
					str = toEscaping && ($.isNumeric(val) || $.isArray(val) || $.isPlainObject(val)) ? "" : escapingStr;
					if ($.isArray(val) || $.isPlainObject(val)) {
						val = JSON.stringify(val);
					}
					exp = exp.replace(reger, str + val + str);
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
				//var inRole = (ed.inid.length < 31 && ed.inid.indexOf("/") == -1) ? this.getRole(ed.iid || ed.inid) : 'page';
				var inRole = ed.ie ? this.getRole(ed.ie) : (ed.inid.length < 31 && ed.inid.indexOf("/") == -1) ? this.getRole(ed.iid || ed.inid) : 'page';
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
			outp, pars, par, expdata, exp, expds, expd, cellExp, childs,
			dbObj;
		for (var ii = 0, ol = ops.length, op; ii < ol; ii++) {
			dbObj = {
				"trigger": trigger
			};
			pubsub.dbAry.push(dbObj);
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
					var inRole = ed.ie ? this.getRole(ed.ie) : (ed.inid.length < 31 && ed.inid.indexOf("/") == -1) ? this.getRole(ed.iid || ed.inid) : 'page';
					if (!roles[inRole]) { // 找不到方法跳过
						continue;
					}
					// 根据控件角色获取控件 事件方法
					var inFuncs = roles[inRole][0]['funcs'],
						edType = ed.type;
					if (inRole == "page") {
						edType.indexOf("getRow") == 0 && (edType = "getRow");
						edType.indexOf("getValue") == 0 && (edType = "getValue");
					}
					if (typeof inFuncs[edType] == 'function') {
						try {
							ed.trigger = trigger;
							var p = inFuncs[edType](ed, args);
							//if (p) {
							eobj[ed.param] = p;
							//}
						} catch (e) {
							console.log(e);
						}
					} else {

					}
				}
				// 替换表达式 数据
				exp = q.expConvert(exp, eobj);
			}
			// 执行表达式
			dbObj["exp"] = exp;
			if (exp != "") {
				var exps = eval(exp.replace(/\n/g,"\\n"));
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
						var obj = {};
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
							//args && (obj = args);
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
			//获取输入参数对象
			dbObj["in"] = [];
			dbObj["param"] = [];
			dbObj["out"] = [];
			// 执行参数传递工作
			for (var id in pars) {
				par = pars[id];
				var obj = {},
					aryFlag = [],
					expObj = {},
					insExpEval;
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
								var inRole = rc.ie ? this.getRole(rc.ie) : (rc.inid.length < 31 && rc.inid.indexOf("/") == -1) ? this.getRole(rc.iid || rc.inid) : 'page';
								if (!roles[inRole]) {
									continue;
								}
								// 根据控件角色获取控件 事件方法
								var inFuncs = roles[inRole][0]['funcs'],
									rcType = rc.type;
								if (inRole == "page") {
									rcType.indexOf("getRow") == 0 && (rcType = "getRow");
									rcType.indexOf("getValue") == 0 && (rcType = "getValue");
								}
								if (typeof inFuncs[rcType] == 'function') {
									try {
										rc.trigger = trigger;
										var p = inFuncs[rcType](rc, args, obj[rc.dataId]);
										//if (p) {
										expObj[rc.param] = p;
										//}
										dbObj["in"].push({
											id: rc.iid || rc.inid,
											method: rc.type,
											param: p,
											columnName: rc.columnName
										});
									} catch (e) {
										console.error(e);
									}
								} else {
									dbObj["in"].push({
										id: rc.iid || rc.inid,
										method: rc.type,
										type: "none",
										param: p,
										columnName: rc.columnName
									});
								}
							}
						}
					} else {
						// 根据输入控件id获取其所属角色role					
						var inRole = r.ie ? this.getRole(r.ie) : (r.inid.length < 31 && r.inid.indexOf("/") == -1) ? this.getRole(r.iid || r.inid) : 'page';

						if (!roles[inRole]) {
							continue;
						}
						// 根据控件角色获取控件 事件方法
						var inFuncs = roles[inRole][0]['funcs'],
							rType = r.type;
						if (inRole == "page") {
							rType.indexOf("getRow") == 0 && (rType = "getRow");
							rType.indexOf("getValue") == 0 && (rType = "getValue");
						}
						if (typeof inFuncs[rType] == 'function') {
							try {
								if (r.dataId) {
									(r.dataId.indexOf("obj") != -1) && (obj[r.dataId] || (obj[r.dataId] = {}));
									(r.dataId.indexOf("ary") != -1) && (obj[r.dataId] || (obj[r.dataId] = []));
								}
								r.trigger = trigger;
								var p = inFuncs[rType](r, args, obj[r.dataId]);
								//r.dataId && (obj[r.dataId] || (obj[r.dataId] = {})) && (obj[r.dataId][r.param] = p);
								//r.dataId && (r.dataId.indexOf("ary") != -1) && ($.inArray(r.dataId, aryFlag) == -1) && aryFlag.push(r.dataId);

								dbObj["in"].push({
									id: r.iid || r.inid,
									method: r.type,
									param: p,
									columnName: r.columnName
								});
								if (p || p == 0) {
									obj[r.param] = p;
								}
							} catch (e) {
								console.error(e);
							}
						} else {
							dbObj["in"].push({
								id: r.iid || r.inid,
								method: r.type,
								param: p,
								type: "none",
								columnName: r.columnName
							});
						}
					}
					if (isExpFunc) { //表达式执行运算 
						dbObj["in"].push({
							exp: r.insExp,
							type: "exp",
							param: "",
							columnName: r.insExp
						});
						try {
							insExpEval = q.expConvert(r.insExp, expObj, true);
							if (insExpEval) {
								obj[r.param] = eval(insExpEval);
							}
						} catch (e) {
							mtDebugger.pushQueue("error", "输入表达式：" + r.insExp + ",执行异常");
						}
					}

				}

				dbObj["param"].push(obj);

				// 输出控件方法
				var outd = par[0],
					outType = outd.eventType,
					outRole = outd.oe ? this.getRole(outd.oe) : (id.length < 31 && id.indexOf("/") == -1) ? this.getRole(outd.oid || id) : 'page'
				isCallback = outd.callback;

				if (!roles[outRole]) {
					continue;
				}

				dbObj["out"].push({
					id: id || outd.oid,
					method: outType
				});

				par.childs = op['childs'];
				par.trigger = trigger;

				var outFuncs = roles[outRole][0]['funcs'];
				if (typeof outFuncs[outType] == 'function') {
					try {
						par.tlevel = tlevel;
						outFuncs[outType](par, obj, args);
					} catch (e) {
						console.log(e);
						mtDebugger.pushQueue("error", "outLinkTmp", {
							id: id,
							outType: outType,
							message: e.message
						});
					}
				} else {
					mtDebugger.pushQueue("error", "outLinkTmp", {
						id: id,
						outType: outType,
						message: "没有执行方法"
					});
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
			pubsub.dbAry = [];
			q._execOps(ops, trigger, rules, args);
			//循环子节点事件
			if (childs && childs.length) {
				$.each(childs, function(i, child) {
					q.pub(child, rules);
				})
			}
			mtDebugger.__etable(pubsub.dbAry);
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
	var $t = $(spread._host),
		er = $t.data("eventsRule"),
		ev;
	er && (ev = er[$t.attr("id")]);
	ev && ev.call();
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
				if (!mypic) return;
				if (mypic.size > maxfilesize) {
					alert("上传的文件不能超过" + (maxfilesize / (1024 * 1024)) + "M");
					return;
				}
				var params = {
					randomparent: $this.attr("randomparent"),
					type: "image"
				}
				if($this.attr("attachmentId")){
					params.attachmentId = $this.attr("attachmentId");
				}
				$.ajaxFileUpload({
					url: $this.attr("saveurl"),
					secureuri: false,
					fileElementId: input.id,
					data: params,
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

							var urlTest = '/glaf/mx/form/imageUpload?method=downloadById&_' + (new Date().getTime()) + '&id=' + data.attachmentId;

							var imageTest = new Image();
							imageTest.onload = function() {
								EXIF.getData(this, function() {
									$image.data("tags", EXIF.getAllTags(this));
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
		if (outid != rule[0]['oid']) {
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
		} else {
			var spread = cellFunc._getSpread(rule);
			for (var key in params) {
				spread.fromJSON(JSON.parse(params[key]));
			}
		}

	},
	insertImg: function(rule, params, args) {
		var outid = rule[0]["outid"];
		var sheet = cellFunc._getActiveSheet(rule),
			doutid = dynamicAreaFunc.calculate(sheet, outid),
			outids = doutid.split("-"),
			baseUrl = contextPath + "/mx/form/imageUpload?method=download&from=to_db&randomparent=",
			//cellRect = sheet.getCellRect(outids[0], outids[1]);
			span = sheet.getSpan(outids[0], outids[1]);
		if (span) {
			if (typeof args == "object") {
				for (var i = 0; i < rule.length; i++) {
					r = rule[i];
					v = params[r.param];
				}
				//name ,src, x,y ,width,height
				//var pic = sheet.pictures.add(v, baseUrl + v, cellRect[x], cellRect[y], cellRect[width], cellRect[height]);
				sheet.addPicture(v + (new Date().getTime()), baseUrl + v, span['row'], span['col'], span['row'] + span['rowCount'], span['col'] + span['colCount']);
			}
			//name,src,startRow,startColumn,endRow,endColumn
			//sheet.addPicture(v,"tsoutline.png",2,2,6,6);
			//picture2.backColor("black");
			//sheet.setValue(outids[0], outids[1], v);
		} else {
			//sheet.setValue(outids[0], outids[1], params);
			//var pic = sheet.pictures.add(params, baseUrl + params, cellRect[x], cellRect[y], cellRect[width], cellRect[height]);
			sheet.addPicture(v + (new Date().getTime()), baseUrl + v, span['row'], span['col'], span['row'] + span['rowCount'], span['col'] + span['colCount']);
		}
	},
	getValue: function(rule, params, args) {
		var outid = rule["inid"];
		if (outid != rule['iid']) {
			var sheet = cellFunc._getActiveSheet(rule, true),
				doutid = dynamicAreaFunc.calculate(sheet, outid),
				outids = doutid.split("-");
			return sheet.getValue(outids[0], outids[1]);
		} else {
			var spread = cellFunc._getSpread(rule, true);
			return JSON.stringify(spread.toJSON());
		}

	},
	prevPage: function(rule, params, args) {
		var $jq = pubsub.getJQObj(rule),
			__page__ = $jq.data("__page__") || 1;
		$jq.data("__page__", __page__ > 1 ? __page__ - 1 : 1);
		pageCell.call($jq.data("spread"));
	},
	nextPage: function(rule, params, args) {
		var $jq = pubsub.getJQObj(rule),
			__page__ = $jq.data("__page__") || 1;
		$jq.data("__page__", __page__ + 1);
		pageCell.call($jq.data("spread"));
	},
	linkAge: function(rule, params, args) {
		var $jq = pubsub.getJQObj(rule);
		$jq.data("__params__", params);
		pageCell.call($jq.data("spread"));
	},
	_getSpread: function(rule, isIn) {
		var $jq = pubsub.getJQObj(rule, isIn);
		return $jq.data("spread")
	},
	_getActiveSheet: function(rule, isIn) {
		var spread = cellFunc._getSpread(rule, isIn),
			sheet = spread.getActiveSheet();
		return sheet;
	},
	tshow: function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		$id.show();
	},
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.hide();
	},
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
};$.validator.methods.customRule = function(value, element, param) {
	return initPageValidate.prototype.customRule($(element));
};
var setCustomRule = function($element, message, param) {
	$element.rules('add', {
		customRule: param || '',
		messages: {
			customRule: message
		}
	});
};
// 初始化页面层次
function initPageLevel() {
	var parent,
		parentPageLevel;
	try {
		parent = window.opener || window.parent;
		parentPageLevel = parent.pageLevel;
	} catch (e) {

	}
	if (parentPageLevel) {
		pageLevel = parentPageLevel + 1;
	} else {
		pageLevel = 1;
	}
	if (pageLevel != 1) {
		if (isWeighting) { // 合并事件
			var pageedJson = null
			try{
				pageedJson = JSON.parse(pageed);
			}catch(e){
				pageedJson = eval(pageed);
			}
			
			pageed = $.extend({}, parent.pageed || {});
			pageed[pageLevel] = pageedJson;
		} else {
			pageed = $.extend({}, parent.pageed || {})/* parent.pageed */;
		}
	} else {
		if (pageed) {
			var pageedJson = null
			try{
				pageedJson = JSON.parse(pageed);
			}catch(e){
				pageedJson = eval(pageed);
			}
			pageed = {};
			pageed[pageLevel] = pageedJson;
		}
	}
	if (pageed) {
		for (var plevel in pageed) {
			var pageEvents = pageed[plevel],
				len = pageEvents.length,
				pageEvent, triggers, trigger, thref = window.location.href,
				pageParamsObj = pageParameters || {},
				pageId = pageParamsObj['id'],
				stockMoObj = {};
			for (var i = 0; i < len; i++) {
				pageEvent = pageEvents[i];
				triggers = pageEvent.trigger;
				for (var j = 0; j < triggers.length; j++) {
					trigger = triggers[j];
					if ((trigger.level == pageLevel || (trigger.level == pageLevel - plevel + 1)) && (thref.indexOf(trigger.pageId) != -1 || pageId == trigger.pageId)) { // 控件事件的层级
						// TODO 注册事件
						var $this = $('#' + trigger.eleId),
							r = trigger.eleId.length > 31 ? "page" : pubsub.getRole(trigger.eleId),
							nb = (r == "button" ? "default" : r),
							t = $this
							.attr('data-role') || nb;

						if (trigger.otype && trigger.oid) {
							$this = $('#' + trigger.oid);
							t = trigger.otype;
						}
						var columnnameKey = "columnname",
							columnname = $this.attr(columnnameKey),
							ety = null;
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
							else if ("listenKey" == eventType) {
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
								eventType = "keyup";
							}
							else if ("listenEnter" == eventType) {
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
								eventType = "keyup";
								ety = "listenEnter";
							}
							if(ety != null){
								$table.on(eventType + "." + columnname, "[" + columnnameKey + "=" + columnname + "]", {
									index: index,
									pageEvent: pageEvent
								}, function(e) {
									if(e.keyCode == "13") {
										pubsub.elongateStockIndex = $(this).closest("tr").index() - e.data.index;
										pubsub.pub(e.data.pageEvent, "");
										pubsub.elongateStockIndex = null;
								    }								
								});
							}
							else{
							$table.on(eventType + "." + columnname, "[" + columnnameKey + "=" + columnname + "]", {
								index: index,
								pageEvent: pageEvent
							}, function(e) {
								pubsub.elongateStockIndex = $(this).closest("tr").index() - e.data.index;
								pubsub.pub(e.data.pageEvent, "");
								pubsub.elongateStockIndex = null;
							});
							}
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
	initPageValidate({
		validate_type_foucs: function($this) {
			$this.attr("valiBlur", "true");
			$this.on('blur', function(event) {
				$(this).valid();
			});
		},
		validate_type_save: function($this) {
			$this.attr("valiSave", "true");
			/* $(vali).mtbootstrap('validate'); */
		},
		bindEvent: function() {

			/**
			 * 外套一层form 以供表单验证
			 */
			var $bodyContent = $('body').children();
			$('body').append('<form id="validateform" >');
			$('body').find('#validateform').append($bodyContent);
			$("#validateform").validate({
				// debug: true,
				onfocusout: false,
				errorElement: "em",
				errorPlacement: function(error, element) {
					// Add the `help-block` class to the error element
					error.addClass("help-block");
					error.insertAfter(element);

					var tipColor = error.css('color');

					if ($(element).data('isICheck')) {
						$(element).closest('label').parent().addClass('has-error');
						$(element).closest('label').parent().append(error);
					} else if ($(element).data('isSelect2')) {
						error.insertAfter($(element).next().next());
					} else if ($(element).data('isDatepicker')) {
						error.insertAfter($(element).parent());
					}

					// Add `has-feedback` class to the parent div.form-group
					// in order to add icons to inputs
					if ($(element).data('isDatepicker')) {
						element.parent("div").parent("div").addClass("has-feedback");
					} else {
						element.parent("div").addClass("has-feedback");
					}

					// Add the span element, if doesn't exists, and apply the
					// icon classes to it.
					var feedback = $(element).next("span.glyphicon")[0];
					if ($(element).data('isDatepicker')) {
						feedback = $(element).parent('div').next("span.glyphicon")[0];
					}
					if (!feedback && !$(element).data('isICheck')) {
						var nlayout_elem = '';
						if ($(element).hasClass('nlayout_elem') || $(element).data('isDatepicker')) {
							nlayout_elem = ' nlayout_elem';
						}
						$("<span class='glyphicon glyphicon-remove form-control-feedback" + nlayout_elem + "'></span>").insertAfter(element);

						feedback = $(element).next("span.glyphicon")[0];
						if ($(element).data('isDatepicker')) {
							feedback = $(feedback).insertAfter(element.parent('div'));
						}

						var $feedback = $(feedback);
						$feedback.css('line-height', $feedback.height() + 'px');
						if ($(element).data('isSelect2')) {
							$feedback.css({
								'z-index': 100,
								'right': 0
							});
						}
						if ($(element).data('isDatepicker')) {
							$feedback.css({
								'background-color': 'white',
								'border-left': '1px solid ' + tipColor
							});
							var dateToggle = $(element).next();
							dateToggle.find('button').css({
								'color': tipColor,
								'border': '1px solid ' + tipColor
							});
						}
					}
				},
				success: function(label, element) {
					// Add the span element, if doesn't exists, and apply the
					// icon classes to it.
					var feedback = $(element).next("span.glyphicon")[0];
					if ($(element).data('isDatepicker')) {
						feedback = $(element).parent('div').next("span.glyphicon")[0];
					}
					var tipColor = $(feedback).css('color');
					if (!feedback && !$(element).data('isICheck')) {
						var nlayout_elem = '';
						if ($(element).hasClass('nlayout_elem') || $(element).data('isDatepicker')) {
							nlayout_elem = ' nlayout_elem';
						}
						$("<span class='glyphicon glyphicon-ok form-control-feedback" + nlayout_elem + "'></span>").insertAfter(element);

						feedback = $(element).next("span.glyphicon")[0];
						if ($(element).data('isDatepicker')) {
							feedback = $(feedback).insertAfter(element.parent('div'));
						}

						var $feedback = $(feedback);
						$feedback.css('line-height', $feedback.height() + 'px');
						if ($(element).data('isSelect2')) {
							$feedback.css({
								'z-index': 100,
								'right': 0
							});
						}
						if ($(element).data('isDatepicker')) {
							$feedback.css({
								'background-color': 'white',
								'border-left': '1px solid ' + tipColor
							});
							var dateToggle = $(element).next();
							dateToggle.find('button').css({
								'color': tipColor,
								'border': '1px solid ' + tipColor
							});
						}
					}
				},
				highlight: function(element, errorClass, validClass) {
					if ($(element).data('isDatepicker')) {
						$(element).parent("div").parent("div").addClass("has-error").removeClass("has-success");
						$(element).parent("div").next("span.glyphicon").addClass("glyphicon-remove").removeClass("glyphicon-ok");
					} else if ($(element).data('isICheck')) {

					} else {
						$(element).parent("div").addClass("has-error").removeClass("has-success");
						$(element).next("span.glyphicon").addClass("glyphicon-remove").removeClass("glyphicon-ok");
					}
				},
				unhighlight: function(element, errorClass, validClass) {
					if ($(element).data("mtValidate")) {
						if ($(element).data('isDatepicker')) {
							$(element).parent("div").parent("div").addClass("has-success").removeClass("has-error");
							$(element).parent("div").next("span.glyphicon").addClass("glyphicon-ok").removeClass("glyphicon-remove");
							var feedback = $(element).parent("div").next("span.glyphicon");
							if (feedback) {
								var tipColor = $(feedback).css('color');
								$(feedback).css({
									'background-color': 'white',
									'border-left': '1px solid ' + tipColor
								});
								var dateToggle = $(element).next();
								dateToggle.find('button').css({
									'color': tipColor,
									'border': '1px solid ' + tipColor
								});
							}
						} else if ($(element).data('isICheck')) {

						} else {
							$(element).parent("div").addClass("has-success").removeClass("has-error");
							$(element).next("span.glyphicon").addClass("glyphicon-ok").removeClass("glyphicon-remove");
						}
					}
				}
			});

			var $this = $("[mtValidate=true]");
			var rules = {};
			$.each($this, function(i, v) {
				var $t = $(v),
					isBlur = $t.attr("valiBlur") == "true",
					methodName = $t.data('rule-method');
				if ($t.data('isICheck') && isBlur) {
					methodName = $t.attr('name');
					$('input[name=' + $t.attr('name') + ']').on('ifChanged', function(event) {
						$t.valid();
					});
				} else {
					$t.attr('name', methodName);
				}
				if ($t.data('isDatepicker') && isBlur) {
					$t.change(function(event) {
						$t.valid();
					});
				}

				if ($t.data('isSelect2') && isBlur) {
					$t.change(function(event) {
						$t.valid();
					});
				}
				var message = $t.data("mt-vail-er-msg") || $t.data("mtValidate").tip;

				// if(!$t.data('isICheck')){
				setCustomRule($t, message);
				// }

			});
		}
	});
};
initPageValidate.prototype.customRule = function(k) {
	var mtValidate = k.data("mtValidate"),
		trigger = k.data("mtValiObj");
	// 执行表达式
	var execObj = pubsub._getInParams(
			mtValidate.execExpData,
			/* trigger.inlev */
			1),
		exec = pubsub
		.expConvert(mtValidate.execExp || "", execObj);
	if (exec != "") {
		var execBol = eval(exec);
		if (!execBol)
			return true;
	}
	// 验证表达式
	var eObj = pubsub._getInParams(
			mtValidate.expData, /* trigger.inlev */ 1),
		exp = pubsub
		.expConvert(mtValidate.exp || "",
			eObj);
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
					ruleObj.validate_type_foucs($this);
					break;
				case "validate_type_save":
					ruleObj.validate_type_save($this);
					break;
				default:
					break;
			}
		})
	}
	var parent = window.parent;
	// 如果不是 则默认获取父
	/*
	 * if (pageLevel != 1) mtValidate = parent.mtValidate;
	 */
	if (mtValidate && mtValidate !== "[]") {
		var pageValidate = JSON.parse(mtValidate),
			len = pageValidate.length,
			vali, triggers, trigger, tlen, $target,
			specialRule = {};
		for (var i = 0; i < len; i++) {
			vali = pageValidate[i];
			triggers = vali.trigger;
			tlen = triggers.length;
			for (var j = 0; j < tlen; j++) {
				trigger = triggers[j];
				// 暂时
				trigger.level = trigger.inlev = pageLevel;
				$target = pubsub.getJQObj(trigger, true);
				if (!$target)
					continue;
				var dataRole = $target.attr('data-role') || "";
				var ruleId = $target.attr('id');
				var cname = $target.attr("cname");
				if ($target) {
					if(dataRole == "gridbt"){
						specialRule[ruleId] || (specialRule[ruleId] = []);
						specialRule[ruleId].push({
							mtValidate : vali,
							dataRole : dataRole,
							ruleId : ruleId,
							"rule-method" : ruleId,
							mtValiObj : trigger,
							"attr-mtValidate" : "true",
							"attr-mtTitle" : cname
						});
						continue;
					}
					var ipts = $target.find('input');
					if (dataRole.indexOf('metroselect') != -1) {
						var select0 = $target.find('select')[0];
						var tempId = $target.attr('id');
						$target = $(select0);
						$target.attr('name', tempId);
						$target.data('isSelect2', true);
					} else if (dataRole.indexOf('icheck') != -1) {
						$target = $(ipts[0]);
						$target.data('isICheck', true);
					} else if (dataRole.indexOf('datepicker') != -1) {
						$target = $(ipts[0]);
						$target.data('isDatepicker', true);
					} else if (dataRole.indexOf("textareabt") != -1) {
						$target = $target.find("textarea");
						$target.data('isTextareabt', true);
					} else if (dataRole.indexOf("touchspin") != -1) {
						$target = $(ipts[0]);
						$target.data('isTouchspin', true);
					} else if (dataRole.indexOf('datetimepickerbt') != -1) {
						$target = $(ipts[0]);
						$target.data('isDatepicker', true);
					}

					$target.data("dataRole", dataRole);
					$target.data("ruleId", ruleId);
					$target.data("rule-method", ruleId);
					$target.data("mtValidate", vali).data("mtValiObj", trigger)
						.attr("mtValidate", "true").attr("mtTitle",
							cname);
					bindValidateFunc($target, vali);
				}
			}
		}
		for(var key in specialRule){
			var $trigger = $("#"+key),
				cRules = specialRule[key];
			$trigger.on("click",function(e){
				console.log(e);
				var $innerTarget = $(e.target),name = $innerTarget.attr("name");
				if(name){
					$.each(cRules,function(i,cRule){
						if(name == cRule.mtValiObj.inid && !$innerTarget.attr("mtValidate")){
							var trSpecial = $innerTarget.closest("td"),specalName;
							var elementSpecial = $("#"+cRule.ruleId).find("thead tr th");
							$.each(elementSpecial,function(i,v){
								if(v.getAttribute("tid") == trSpecial.attr("hid")){
									specalName = v.innerText;
								}
							});
							$innerTarget.data("dataRole", cRule.dataRole);
							$innerTarget.data("ruleId", cRule.ruleId);
							$innerTarget.data("rule-method", cRule.ruleId);
							$innerTarget.data("mtValidate", cRule.mtValidate).data("mtValiObj", cRule.mtValiObj)
								.attr("mtValidate", "true").attr("mtTitle",
										specalName);
							// TODO mtTitle 找到当前列的标题
							bindValidateFunc($innerTarget, cRule.mtValidate);
							setCustomRule($innerTarget, $innerTarget.data("mt-vail-er-msg") || $innerTarget.data("mtValidate").tip);
						}	
					})
				}
			})
			
		}
		ruleObj.bindEvent();
	}
}

// 注册事件
var pubsubobjects = {
	'default': function(e) {
		if ("changeByName" == e.eventType) {
			$('input[name=' + $(this).attr('name') + ']').on('change', function(event) {
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
				'characterData': true,
				'subtree': true,
				'attributeFilter': ['setvalue']
			});
			$(this).on("change", function(event) {
				// 事件方法
				mtxx.e = event;
				pubsub.pub(e.pageEvent, "");
			});
		}
		else if ("listenKey" == e.eventType) {
			var $this = this;
			$this.on("keyup", function(event) {
				// 事件方法
				mtxx.e = event;
		        pubsub.pub(e.pageEvent, "");
		        
			});
		}
		else if ("listenEnter" == e.eventType) {
			var $this = this;
			$this.on("keyup", function(event) {
				// 事件方法
		    mtxx.e = event;
		    var key = e.which;
		    if(event.keyCode == "13") {
			    pubsub.pub(e.pageEvent, "");
		    }
			
			});
		}
		else {
			if (e.eventType.indexOf('By') != -1) {
				var element = e.eventType.substr(e.eventType.indexOf('By') + 2);
				if ($(this).find(element).length > 0) {
					$(this).on(e.eventType.substr(0, e.eventType.indexOf('By')), function(event) {
						// 事件方法
						mtxx.e = event;
						pubsub.pub(e.pageEvent, "");
					});
				} else {
					$(this).on(e.eventType.substr(0, e.eventType.indexOf('By')), function(event) {
						// 事件方法
						mtxx.e = event;
						pubsub.pub(e.pageEvent, "");
					});
				}
			} else {
				$(this).on(e.eventType, function(event) {
					// 事件方法
					mtxx.e = event;
					pubsub.pub(e.pageEvent, "");
				});
			}
		}
	},
	ztree: function(e) { // ztree 事件
		var ztreeObj = $.fn.zTree.getZTreeObj(this.attr('id'));
		if (ztreeObj) {
			if (e.eventType == "clickEvent") {
				ztreeObj.setting.callback.onClick = function(event, treeId, treeNode) {
					pubsub.pub(e.pageEvent, "", treeId, treeNode);
				}
			} else if (e.eventType == "checkEvent") {
				ztreeObj.setting.callback.onCheck = function(event, treeId, treeNode) {
					pubsub.pub(e.pageEvent, "", treeId, treeNode);
				}
			} else if (e.eventType == "onLoadSuccess") {
				ztreeObj.setting.callback.onLoadSuccess = function(event, treeId, treeNode) {
					// 加载完事件
					pubsub.pub(e.pageEvent, "", treeId, treeNode);
				}
			}
		} else {
			var state = this.data("_bindEvent_");
			if (state) {
				state.push(e);
			} else {
				this.data("_bindEvent_", [e]);
			}
		}

	},
	/*
	 * 提示框初始化事件
	 */
	prompt: function(e){
		var jq = $("#" + this.attr("id"));
		var ct = jq.data("promptExt");
		if (e.eventType == "inithind") {
		if(ct && ct.options){
			jqCb.add(function() {
				pubsub.pub(e.pageEvent, "", ct.options);
			});
		 }			
		}
		
	},
	/*
	 * 弹出框初始化事件
	 */
	popover: function(e){
		var jq = $("#" + this.attr("id"));
		var ct = jq.data("popoverExt");
		if (e.eventType == "initpopover") {
			if(ct && ct.options){
				// 队列
				jqCb.add(function() {
					pubsub.pub(e.pageEvent, "", ct.options);
				});
			}			
		}
		
	},
	button: function(e) {
		var events = $(this).attr("t-events");
		if (events == 'true') { // 动态事件 =definedTable
			var targetEle = "[data-role] a[btn-id][t-events=true]",
				key = "event";
			var selector = "[data-role] a[btn-id="+$(this).attr("id")+"][t-events=true]";
			$(this).data(key, e);
			$(document)./*
						 * off(e.eventType + "." + $(this).attr("id"),
						 * selectortargetEle).
						 */on(e.eventType+"."+$(this).attr("id"), selector, function(event) {
				event.preventDefault();
				var btnId = $(this).attr("btn-id"),
					$btn = $("#" + btnId);
				if ($btn[0]) {
					var $tr = $(this).closest("tr"),
						rowIndex = $tr.attr("row-index"),
						$Ele = $(this).closest("[data-role]"),
						dataRole = $Ele.attr("data-role"),
						curRow;
					var eve = $btn.data(key);
					if (dataRole == "gridbt") {
						curRow = $Ele.data("grid").getData()[rowIndex];
					}
					if (eve) {
						mtxx.e = event;
						pubsub.pub(eve.pageEvent, "", undefined, curRow);
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
		} else if (e.eventType == "mtSubmit") {
			$(document.body).data("mtSubmit", function() {
				pubsub.pub(e.pageEvent);
			});
		} else if (e.eventType == 'oncloseEvent') {
			var onbeforeunloadFunc = function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			}
			$('body').data("onbeforeunload", onbeforeunloadFunc);
			window.onbeforeunload=onbeforeunloadFunc;
			// window.onbeforeunload=;
		}else if(e.eventType == 'singleClick'){
			$('body').on('click',function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			});
		}else if(e.eventType == 'scanCallback'){
			$('body').data("scanCallback", function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			});
		}
	},
	jsgis: function(e) {
		var jsgisEvent = function(e, eventName) {
			var $this = $("#" + e.trigger.eleId),
				ary = $this.data(eventName) || [];
			ary.push(function(kk) {
				var bol = false,
					isCurrentPoint = false; // 当前点是否是当前弹窗位置的点
				$.each(e.pageEvent.ops, function(i, v) {
					for (var p in v.param) {
						var dps = v.param[p];
						$.each(dps, function(i, v) {
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
		}
		if (e.eventType == "loadEnd") {
			jq.definedTable('loadEnd', function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			})
		}
		if(e.eventType == "onClickTable"){
			jq.definedTable('onClickTable', function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			})	
		}
	},
	calendarbt: function(e) {
		var jq = $("#" + this.attr("id"));
		if (e.eventType == "unselect") {
			jq.find("div.has-toolbar").data("fullCalendar").options.unselect = function(event, view, jsEvent) {
				pubsub.pub(e.pageEvent, "", view, jsEvent);
			};
		} else if (e.eventType == "eventClick") {
			jq.find("div.has-toolbar").data("fullCalendar").options.eventClick = function(calEvent, jsEvent, view) {
				pubsub.pub(e.pageEvent, "", calEvent, jsEvent, view);
			};
		} else if (e.eventType == "dayClick") {
			jq.find("div.has-toolbar").data("fullCalendar").options.dayClick = function(date, allDay, jsEvent) {
				pubsub.pub(e.pageEvent, "", date, allDay, jsEvent);
			};
		}
	},
	megamenu: function(e) {
		var $this = $("#" + this.attr("id"));
		var selectcolor = $this.attr("selectcolor");
		var selectColor = $this.megaMenuExt("getSelectColor", selectcolor);
		$this.on('click', 'ul.page-sidebar-menu-mt a:not(".nav-toggle"),ul.nav.navbar-nav a:not(".nav-toggle")', function() {
			if ($this.data("select")) {
				$this.data("select").removeClass(selectColor);
				$this.data("select").parents("li").find(">a").removeClass(selectColor);
			}
			$(this).addClass(selectColor);
			$(this).parents("li").find(">a").addClass(selectColor);
			$this.data("select", $(this));
			pubsub.pub(e.pageEvent, "", $(this).closest("li").data("mObj"));
		});
	},
	login_actions: function(e) {
		var jq = $("#" + this.attr("id"));
		if (e.eventType == "click") {
			jq.on("click", 'button[frame-variable="loginBtn"]', function(event) {
				pubsub.pub(e.pageEvent, "", jq);
			});
		}
	},
	login_username: function(e) {
		var jq = $("#" + this.attr("id"));
		if (e.eventType == "keypress") {
			jq.on("keypress", 'input[frame-variable="username"]', function(event) {
				if (event.which == 13) {
					pubsub.pub(e.pageEvent, "");
				}
			});
		}
		if (e.eventType == "listenVal") {
			jq.on("change", 'input[frame-variable="username"]', function(event) {
					pubsub.pub(e.pageEvent, "");
			});
			
		}
		if (e.eventType == "listenEnter") {
			jq.on("keyup", 'input[frame-variable="username"]', function(event) {
				if(event.keyCode == "13") {
				    pubsub.pub(e.pageEvent, "");
			    }
			});
		
		}
	},
	login_password: function(e) {
		var jq = $("#" + this.attr("id"));
		if (e.eventType == "keypress") {
			jq.on("keypress", 'input[frame-variable="password"]', function(event) {
				if (event.which == 13) {
					pubsub.pub(e.pageEvent, "");
				}
			});
			
		}
		if (e.eventType == "listenVal") {
			jq.on("change", 'input[frame-variable="password"]', function(event) {
					pubsub.pub(e.pageEvent, "");
			});
			
		}
		if (e.eventType == "listenEnter") {
			jq.on("keyup", 'input[frame-variable="password"]', function(event) {
				if(event.keyCode == "13") {
				    pubsub.pub(e.pageEvent, "");
			    }
			});
		
		}
	},
	login_verify: function(e) {
		var jq = $("#" + this.attr("id"));
		if (e.eventType == "keypress") {
			jq.on("keypress", 'input[frame-variable="verification"]', function(event) {
				if (event.which == 13) {
					pubsub.pub(e.pageEvent, "");
				}
			});
		}
		if (e.eventType == "listenKey") {
			jq.on("keyup", 'input[frame-variable="verification"]',function(event) {
				// 事件方法
				mtxx.e = event;
		        pubsub.pub(e.pageEvent, "");
		        
			});
		}
		if (e.eventType == "listenEnter") {
			jq.on("keyup", 'input[frame-variable="verification"]', function(event) {
				if(event.keyCode == "13") {
				    pubsub.pub(e.pageEvent, "");
			    }
			});
		
		}
	},
	__gridbt__: function(e) {
		var id = this.attr("id"),
			$this = $("#" + id);

		if (e.eventType == "listenVal" || e.eventType == "change") {
			$this.on("change", "[name=" + e.trigger.eleId + "]", function(event) {
				// 事件方法
				mtxx.e = event;
				pubsub.pub(e.pageEvent, "");
			});
		} else if (e.eventType == "click") {
			$this.on("click", "[name=" + e.trigger.eleId + "]", function(event) {
				// 事件方法
				mtxx.e = event;
				pubsub.pub(e.pageEvent, "");
			});
		} else if (e.eventType == "loadSucess") {
			var opts = $this.metroselect("getOptions");
			opts.events.onLoadSucess = function(data) {
				pubsub.pub(e.pageEvent, "", data);
			}
		} else if (e.eventType == "clickNode") {
			$this.on('ifClicked', "[name=" + e.trigger.eleId + "]", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		} else if (e.eventType == "nodeChange") {
			$this.on('ifChanged', "[name=" + e.trigger.eleId + "]", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		} else if (e.eventType == "nodeChecked") {
			$this.on('ifChecked', "[name=" + e.trigger.eleId + "]", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		} else if (e.eventType == "nodeUnchecked") {
			$this.on('ifUnchecked', "[name=" + e.trigger.eleId + "]", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		} else if (e.eventType == "dblclick") {
			$this.on("dblclick", "[name=" + e.trigger.eleId + "]", function(event) {
				// 事件方法
				mtxx.e = event;
				pubsub.pub(e.pageEvent, "");
			});
		}
	},
	gridbt: function(e) {
		var id = this.attr("id"),
			$this = $("#" + id);
		if (e.trigger.otype) {
			pubsubobjects.__gridbt__.call(this, e);
			// if (e.eventType == "listenVal" || e.eventType == "change") {
			// $(this).on("change", "[name=" + e.trigger.eleId + "]",
			// function(event) {
			// // 事件方法
			// mtxx.e = event;
			// pubsub.pub(e.pageEvent, "");
			// });
			// }
			return;
		}
		if (e.eventType == "gchange") {

			var ct = $this.data("grid");
			if (ct && ct.options) {
				ct.options.events.onClickRow = function(index, row) {
					pubsub.pub(e.pageEvent, "", index, row);
				}
			}
		} else if (e.eventType == "loadEnd") {
			var ct = $this.data("grid");
			if (ct && ct.options) {
				ct.options.events.onLoadSuccess = function(data) {
					pubsub.pub(e.pageEvent, "", data);
				}
			}
		} else if (e.eventType == "dbclick") {
			var ct = $this.data("grid");
			if (ct && ct.options) {
				ct.options.events.onDblClickRow = function(index, row) {
					pubsub.pub(e.pageEvent, "", index, row);
				}
			}
		} else if (e.eventType == 'beginEditEvent') {
			var ct = $this.data("grid");
			if (ct && ct.options) {
				ct.options.events.beginEditEvent = function(o) {
					pubsub.pub(e.pageEvent, "", o);
				}
			}
		} else if (e.eventType == 'onCheckRow') {
			var ct = $this.data("grid");
			if (ct && ct.options) {
				ct.options.events.onCheckRow = function(index, row) {
					pubsub.pub(e.pageEvent, "", index, row);
				}
			}
		}
		else if (e.eventType == 'onclick') {
			var ct = $this.data("grid");
			if (ct && ct.options) {
				ct.options.events.onClickCell = function(index, field, value) {
					pubsub.pub(e.pageEvent, "",index,field,value,this);
				}
			}
		}else if(e.eventType == "justClick"){
			$this.on('click',function(event){
				if($(event.target).is('div')){
					pubsub.pub(e.pageEvent, "", event);
				}
			});
		}else if(e.eventType == 'beforeEndEdit'){
			var ct = $this.data("grid");
			if (ct && ct.options) {
				ct.options.events.onBeforeEndEdit = function() {
					pubsub.pub(e.pageEvent, "");
				}
			}
		}
		else if(e.eventType == 'updateButtonClick'){
			var ct = $this.data("grid");
			if (ct && ct.options) {
				ct.options.events.onUpdateButtonClick = function() {
					$this.find("a[atype='update']").on('click',function(event){
						pubsub.pub(e.pageEvent, "", event);
					});
				}
			}		
		}
	},
	gridlist: function(e) {
		var id = this.attr("id"),
			$this = $("#" + id);
		if (e.eventType == "clickEvent") {
			var ct = $this.data("gridList");
			if (ct && ct.options) {
				ct.options.callback.OnContentClick = function(data) {
					pubsub.pub(e.pageEvent, "", data);
				}
			}
		}
		if (e.eventType == "dblClickEvent") {
			var ct = $this.data("gridList");
			if (ct && ct.options) {
				ct.options.callback.OnContentDblClick = function(data) {
					pubsub.pub(e.pageEvent, "", data);
				}
			}
		}
		if (e.eventType == "droppend") {
			var ct = $this.data("gridList");
			if (ct && ct.options) {
				ct.options.callback.onDroppEnd = function(data) {
					pubsub.pub(e.pageEvent, "", data);
				}
			}
		}
		if (e.eventType == "drapStart") {
			var ct = $this.data("gridList");
			if (ct && ct.options) {
				ct.options.callback.onDrapStart = function(data) {
					pubsub.pub(e.pageEvent, "", data);
				}
			}
		}
		if (e.eventType == "draopOver") {
			var ct = $this.data("gridList");
			if (ct && ct.options) {
				ct.options.callback.onDroppOver = function(data) {
					pubsub.pub(e.pageEvent, "", data);
				}
			}
		}
		if (e.eventType == "onloadSuccess") {
			var ct = $this.data("gridList");
			if (ct && ct.options) {
				ct.options.callback.OnLoadSuccess = function(data) {
					pubsub.pub(e.pageEvent, "", data);
				}
			}
		}
	},
	gridList: function(e) {
		pubsubobjects.gridlist.call(this, e);
		// var id = this.attr("id"),
		// $this = $("#" + id);
		// if (e.eventType == "clickEvent") {
		// var ct = $this.data("gridList");
		// if (ct && ct.options) {
		// ct.options.callback.OnContentClick = function(data) {
		// pubsub.pub(e.pageEvent, "", data);
		// }
		// }
		// }
		// if (e.eventType == "droppend") {
		// var ct = $this.data("gridList");
		// if (ct && ct.options) {
		// ct.options.callback.onDroppEnd = function(data) {
		// pubsub.pub(e.pageEvent, "", data);
		// }
		// }
		// }
		// if (e.eventType == "drapStart") {
		// var ct = $this.data("gridList");
		// if (ct && ct.options) {
		// ct.options.callback.onDrapStart = function(data) {
		// pubsub.pub(e.pageEvent, "", data);
		// }
		// }
		// }
		// if (e.eventType == "draopOver") {
		// var ct = $this.data("gridList");
		// if (ct && ct.options) {
		// ct.options.callback.onDroppOver = function(data) {
		// pubsub.pub(e.pageEvent, "", data);
		// }
		// }
		// }
	},
	treelistbt: function(e) {
		var id = this.attr("id"),
			$this = $("#" + id);
		if (e.trigger.otype) {
			pubsubobjects.__gridbt__(e);
			// if (e.eventType == "listenVal" || e.eventType == "change") {
			// $(this).on("change", "[name=" + e.trigger.eleId + "]",
			// function(event) {
			// // 事件方法
			// mtxx.e = event;
			// pubsub.pub(e.pageEvent, "");
			// });
			// }
			return;
		}
		if (e.eventType == "gchange") {
			var ct = $this.data("treelist");
			if (ct && ct.options) {
				ct.options.events.onClickRow = function(index, row) {
					pubsub.pub(e.pageEvent, "", index, row);
				}
			}else if($this[0]){
				$this.data("__onClickRow__",function(index, row) {
					pubsub.pub(e.pageEvent, "", index, row);
				});
			}
		} else if (e.eventType == "loadEnd") {
			var ct = $this.data("treelist");
			if (ct && ct.options) {
				ct.options.events.onLoadSuccess = function(data) {
					pubsub.pub(e.pageEvent, "", data);
				}
			}else if($this[0]){
				$this.data("__onLoadSuccess__",function(data) {
					pubsub.pub(e.pageEvent, "", data);
				});
			}
		} else if (e.eventType == "dbclick") {
			var ct = $this.data("treelist");
			if (ct && ct.options) {
				ct.options.events.onDblClickRow = function(index, row) {
					pubsub.pub(e.pageEvent, "", index, row);
				}
			}else if($this[0]){
				$this.data("__onDblClickRow__",function(index, row) {
					pubsub.pub(e.pageEvent, "", index, row);
				});
			}
		} else if (e.eventType == 'onCheckRow') {
			var ct = $this.data("treelist");
			if (ct && ct.options) {
				ct.options.events.onCheckRow = function(index, row) {
					pubsub.pub(e.pageEvent, "", index, row);
				}
			}else if($this[0]){
				$this.data("__onCheckRow__",function(index, row) {
					pubsub.pub(e.pageEvent, "", index, row);
				});
			}
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
	mapext: function(e){
		var id = this.attr("id"),
			$this = $("#" + id);
		if (e.eventType == "clickNode") {
			var ct = $this.data("echartsmapExt");
			if (ct && ct.options) {
				ct.options.events.clickNode = function(data) {
					pubsub.pub(e.pageEvent, "", data);
				}
			}
		}
	},
	diagrambt: function(e) {
		var $this = this;
		var isdblclick = false;
		$this.isdblclick = false;
		if (e.eventType == "clickNode") {
			$this.on('click', '.orgchart .node', function(event) {
				$this.data("isdblclick",false);
				setTimeout(function() {
					if(!$this.data("isdblclick")){
						pubsub.pub(e.pageEvent, "", event, this);
					}
				},250);
			});
		}
		if (e.eventType == "dblclickNode") {
			$this.on('dblclick', '.orgchart .node', function(event) {
				$this.data("isdblclick",true);
				pubsub.pub(e.pageEvent, "", event, this);
			});
		}
	},
	gantt: function(e) {
		var id = this.attr("id"),
			$this = $("#" + id);
		if (e.eventType == "gchange") {
			$this.bind("gantt-click", function(ex, index, row) {
				pubsub.pub(e.pageEvent, "", index, row);
			});
			$this.iframegantt('initEvents', {
				events: {
					onClickRow: function(index, row) {
						$this.trigger('gantt-click', [index, row]);
					},
					onDbClickRow: function(index, row) {
						$this.trigger('gantt-click', [index, row]);
					}
				}
			});

		}
	},
	step: function(e) {
		var $this = this;
		if (e.eventType == "clickStep") {
			$this.on('click', 'div.mt-step-col', function(event) {
				var data = $(this).data("step");
				pubsub.pub(e.pageEvent, "", data);
			});
		}
	},
	icheckradio: function(e) {
		var $this = this;
		if (e.eventType == "clickNode") {
			$this.on('ifClicked', "input", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		} else if (e.eventType == "nodeChange") {
			$this.on('ifChanged', "input", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		} else if (e.eventType == "nodeChecked") {
			$this.on('ifChecked', "input", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		} else if (e.eventType == "nodeUnchecked") {
			$this.on('ifUnchecked', "input", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		}
	},
	icheckbox: function(e) {
		var $this = this;
		var count = 0;
		if (e.eventType == "clickNode") {
			$this.on('ifClicked', "input", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		} else if (e.eventType == "nodeChange") {
			$this.on('ifChanged', "input", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		} else if (e.eventType == "nodeChecked") {
			$this.on('ifChecked', "input", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		} else if (e.eventType == "nodeUnchecked") {
			$this.on('ifUnchecked', "input", function(event) {
				pubsub.pub(e.pageEvent, "", event);
			});
		}
	},
	excelupload: function(e) {
		var $this = this;
		if (e.eventType == "onUploadSucess") {
			var opts = $this.data("excelFileUpload").opts;
			opts.onUploadSucess = function(data) {
				pubsub.pub(e.pageEvent, "", data);
			}
		}

	},
	metroselect: function(e) {
		var $this = this;
		if (e.eventType == "loadSucess") {
			var opts = $this.metroselect("getOptions");
			opts.events.onLoadSucess = function(data) {
				pubsub.pub(e.pageEvent, "", data);
			}
		} else {
			$(this).on(e.eventType, function(event) {
				// 事件方法
				mtxx.e = event;
				pubsub.pub(e.pageEvent, "");
			});
		}
	},
	customSelect: function(e) {
		var $this = this;
		if (e.eventType == "loadSucess") {
			var that = $this.data("customSelect");
			that.option.events.onLoadSuccess = function(data) {
				pubsub.pub(e.pageEvent, "", data);
			}
		} else {
			$(this).on(e.eventType, function(event) {
				// 事件方法
				mtxx.e = event;
				pubsub.pub(e.pageEvent, "");
			});
		}
	},
	jqfileupload2: function(e) {
		var $this = this;
		if (e.eventType == "uploadSucess") {
			var pluginData = $this.data("spFileUpload");
			if (pluginData && pluginData.opts) {
				var opts = pluginData.opts;
				opts.events = opts.events || {};
				opts.events.onUploadSuccess = function(data) {
					pubsub.pub(e.pageEvent, "", data);
				}
			}
		}
	},

	bootstrap_tabs: function(e) {
		var $this = this;
		if (e.eventType == "activeEvent") {
			$this.find('>.nav-tabs a[data-toggle="tab"],>.row>.tabnav>.nav-tabs a[data-toggle="tab"]').on('shown.bs.tab', function(ex) {
				pubsub.pub(e.pageEvent, "", ex);
			});
		}
	}

	,
	bim: function(e) {
		var $this = this,
			events = {
				BimClick: function(event) { // 节点单击事件
					var opts = $this.data("bim");
					opts.onSelect = function(msg) {
						try {
							var client_method = $this.data("client_method");
							if (!client_method) {
								var id = msg.dbIdArray[0];
								if (id != null) {
									var node = $this.bim("GetNodeById", id);
									pubsub.pub(e.pageEvent, "", $.extend(true, node, {
										_documentId: opts._documentId
									}));
								} else {

								}
							}
						} catch (ex) {
							throw ex;
						} finally {
							$this.data("client_method", false);
						}
					};
				}
			};
		events[e.eventType] && (events[e.eventType]());
	},
	definedcard: function(e) {
		var $this = this;
		if (e.eventType == "gchange") {
			var ct = $this.data("definedCardExt");
			if (ct && ct.options) {
				ct.options.events.onClickRow = function(index, row) {
					pubsub.pub(e.pageEvent, "", index);
				}
			}
		}
	},
	switch: function(e) {
		var $this = this;
		if (e.eventType == "gchange") {
			$this.find("input[type='checkbox']").bootstrapSwitch("onSwitchChange", function(event, state) {
				pubsub.pub(e.pageEvent, "", state);
			})
		}
	},
	metrolist: function(e) {
		var $this = this;
		if (e.eventType == 'droppend') {
			var ct = $this.data("metrolist");
			if (ct && ct.options) {
				ct.options.onDroppEnd = function() {
					pubsub.pub(e.pageEvent, "", null);
				}
			}
		}
	},
	rangeSlider:function(e){
		var id = this.attr("id"),
		$this = $("#" + id);
		if(e.eventType == 'dragBar'){
			var rs = $this.data("rangeSlider");
			if(rs){
				rs.options.onChan = function(){
					pubsub.pub(e.pageEvent, "", null);
				}
			}
		}
	},
	
	fullpage:function(e){
		var id = this.attr("id"),
		$this = $("#" + id);
		if(e.eventType == 'afterLoad'){
			var rs = $this.data("fullpage");
			if(rs){
				rs.options.afl = function(){
					pubsub.pub(e.pageEvent, "", null);
				}
			}
		}else if(e.eventType=='afterSlideload'){
			var rs = $this.data("fullpage");
			if(rs){
				rs.options.asl = function(){
					pubsub.pub(e.pageEvent, "", null);
				}
			}
		}
	},
	nestable : function(e){
		var $this = this;
		// 拖入事件
		if (e.eventType == "dropNestEvent") {
			var build = $this.data("nestable");
			if(build){
				build.dragStopEvent = function(param){
					pubsub.pub(e.pageEvent,"");
				};
			}
			
		}	
		if (e.eventType == "dropStartEvent") {
			var build = $this.data("nestable");
			if(build){
				build.dragStartEvent = function(param){
					pubsub.pub(e.pageEvent,"");
				};
			}
			
		}		
	},
	definedpanel : function(e){
		var $this = this;
		if(e.eventType == 'onClickEvent'){
			var ct = $this.data("definedPanelExt");
			if (ct && ct.options) {
				ct.options.events.onClickEvent = function(index, row) {
					pubsub.pub(e.pageEvent, "", index);
				}
			}
		}
	},
	custom : function(e){
		var $this = this;
		if(e.eventType == 'onLoadSucess'){
			$this.data("onLoadSucess",function(event){
				pubsub.pub(e.pageEvent, "", event);
			})
		}
	},
	officebt : function(e){
		
		/**
		 * 数据装载完事件
		 */
		if(e.eventType == 'onAppendRowsByBookMarks'){
			var $target = $(this);
			if (!$target.get(0)) {
				return;
			}
			var state = $target.data("office.options");
			state.options.events.onAppendRowsByBookMarks = function(events) {
				pubsub.pub(e.pageEvent, "", events);
			};
		}
	},
	
	office : function(e){
		return pubsubobjects.officebt.apply(this,//
				Array.prototype.slice.call(arguments, 0))
	}
};/**
 * Bootstrap Alert
 */
var btalertFunc = {
	open : function(rule, args) {
		pubsub.getJQObj(rule).btalert("open", args);
	},
	close : function(rule, args){
		pubsub.getJQObj(rule).btalert("close", args);
	}
};
pubsub.sub("btalert", btalertFunc);/**
 * Bootstrap Messenger
 */
var btmessengerFunc = {
	open : function(rule, args) {
		pubsub.getJQObj(rule).btmessenger("open", args.content);
	},
	close : function(rule, args){
		pubsub.getJQObj(rule).btmessenger("close");
	}
};
pubsub.sub("btmessenger", btmessengerFunc);/**
 * calendar事件
 */
var calendarbtFunc = {

	grefresh: function(rule, args) {//刷新操作
		var $id = pubsub.getJQObj(rule);
		$id.calendarExt("refresh");
	},
	getSource:function(rule, args){//选中日程
		return args[0][rule.columnName];
	},
	getDate:function(rule, args){//选中日历
		var $id = pubsub.getJQObj(rule);
		return $id.calendarExt("dateFormat",args[0],"yyyy-MM-dd HH:mm:dd");
	},
	linkage:function(rule, args){//选中日历
		var $id = pubsub.getJQObj(rule);

		return $id.calendarExt("query",args);
	},
	
};
pubsub.sub("calendarbt", calendarbtFunc);
var definedButtonFunc = {
		binding: function(rule, args,options){
			debugger;
			var $id = pubsub.getJQObj(rule),r, v = "";
			var ids = rule.outid;
			if(!ids){
				ids = rule[0].outid;
			}
			var idd = rule.trigger.eleId
			var htmled = $('#'+idd).clone();
			var place =options[0].promptmessage;
			var tile =options[0].titile;
			if(!tile){
				tile = args.title;
			}
			htmled.attr("id","");
			if(tile){
				$('#'+ids).tooltip({title:tile,placement:place,html:true,template:htmled});
			}else {
				htmled.find('.tooltip-inner').remove();
				$('#'+ids).tooltip({title:"z",placement:place,html:true,template:htmled});
			}
			
		},
		setValue : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			$.each(args,function(i,arg){
				$id.find("span.frame-variable")[0].innerHTML = arg;
			});
			
		},
		//添加按钮
		copyMtButon : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			var content = $id.parent().parent();
			var div = $id.parent().parent().parent();
			
			/*
			$id.closest("div").append($id[0].outerHTML);*/
			$.each(args,function(i,arg){
				if(arg.toString().indexOf(",") != -1){
					var arr = arg.toString().split(",");
					for(var z=0;z<arr.length;z++){
						content.before(content[0].outerHTML);
						$(div.find("div.row")[z]).css("display","block");
						$(div.find("div.row")[z]).find("span")[0].innerHTML = arr[z];
						var $closeSpan = $('<span>').addClass('ui-icon ui-icon-close');
						$closeSpan[0].innerHTML = 'x';
						var $closeDIV = $(div.find("div.row")[z]);
						$closeSpan.attr({
							'style': 'cursor:pointer;float:right;position:absolute;top:2px;right:8px;color:#66C8F2;font-weight: bolder;font-size: 12px;display:none'
						}).bind('click', function(event) {
							$closeDIV.remove();
						});
						$closeSpan.appendTo($closeDIV);
						$closeDIV.mouseover(function(){
							$closeDIV.on('mouseover', function(e){
								$closeDIV.find("span.ui-icon-close").css("display","block");
		                	});
			            });
						$closeDIV.mouseout(function(){
							$closeDIV.on('mouseout', function(e){
								$closeDIV.find("span.ui-icon-close").css("display","none");
			                	});     
			                    // OnMouseUp Code in here
			            });  
					}
				}
				else{
					content.before(content[0].outerHTML);
					$(div.find("div")[0]).css("display","block");
					div.find("div").find("span")[0].innerHTML = arg;
					var $closeDIV = $(div.find("div")[0]);
					var $closeSpan = $('<span>').addClass('ui-icon ui-icon-close');
					$closeSpan[0].innerHTML = 'x';
					$closeSpan.attr({
						'style': 'cursor:pointer;float:right;position:absolute;top:2px;right:10px;color:#66C8F2;font-weight: bolder;font-size: 12px;display:none'
					}).bind('click', function(event) {
						$closeDIV.remove();
					});
					$closeSpan.appendTo($closeDIV.find("div"));
					$closeDIV.find("div").mouseover(function(){
						$closeDIV.find("div").on('mouseover', function(e){
							$closeDIV.find("div").find("span.ui-icon-close").css("display","block");
	                	});
	                    
		            });
					$closeDIV.find("div").mouseout(function(){
						$closeDIV.find("div").on('mouseout', function(e){
							$closeDIV.find("div").find("span.ui-icon-close").css("display","none");
		                	});     
		                    // OnMouseUp Code in here
		                
		            });  
				}
				
			})
		},
		//添加按钮
		copyMtButon : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			var content = $id.parent().parent();
			var div = $id.parent().parent().parent();
			
			/*
			$id.closest("div").append($id[0].outerHTML);*/
			$.each(args,function(i,arg){
				if(arg.toString().indexOf(",") != -1){
					var arr = arg.toString().split(",");
					for(var z=0;z<arr.length;z++){
						content.before(content[0].outerHTML);
						$(div.find("div.row")[z]).css("display","block");
						$(div.find("div.row")[z]).find("span")[0].innerHTML = arr[z];
						var $closeSpan = $('<span>').addClass('ui-icon ui-icon-close');
						$closeSpan[0].innerHTML = 'x';
						var $closeDIV = $(div.find("div.row")[z]);
						$closeSpan.attr({
							'style': 'cursor:pointer;float:right;position:absolute;top:2px;right:8px;color:#66C8F2;font-weight: bolder;font-size: 12px;display:none'
						}).bind('click', function(event) {
							$closeDIV.remove();
						});
						$closeSpan.appendTo($closeDIV);
						$closeDIV.mouseover(function(){
							$closeDIV.on('mouseover', function(e){
								$closeDIV.find("span.ui-icon-close").css("display","block");
		                	});
			            });
						$closeDIV.mouseout(function(){
							$closeDIV.on('mouseout', function(e){
								$closeDIV.find("span.ui-icon-close").css("display","none");
			                	});     
			                    // OnMouseUp Code in here
			            });  
					}
				}
				else{
					content.before(content[0].outerHTML);
					$(div.find("div")[0]).css("display","block");
					div.find("div").find("span")[0].innerHTML = arg;
					var $closeDIV = $(div.find("div")[0]);
					var $closeSpan = $('<span>').addClass('ui-icon ui-icon-close');
					$closeSpan[0].innerHTML = 'x';
					$closeSpan.attr({
						'style': 'cursor:pointer;float:right;position:absolute;top:2px;right:10px;color:#66C8F2;font-weight: bolder;font-size: 12px;display:none'
					}).bind('click', function(event) {
						$closeDIV.remove();
					});
					$closeSpan.appendTo($closeDIV.find("div"));
					$closeDIV.find("div").mouseover(function(){
						$closeDIV.find("div").on('mouseover', function(e){
							$closeDIV.find("div").find("span.ui-icon-close").css("display","block");
	                	});
	                    
		            });
					$closeDIV.find("div").mouseout(function(){
						$closeDIV.find("div").on('mouseout', function(e){
							$closeDIV.find("div").find("span.ui-icon-close").css("display","none");
		                	});     
		                    // OnMouseUp Code in here
		                
		            });  
				}
				
			})
		},
		bindpopover: function(rule, args,options){
			var $id = pubsub.getJQObj(rule),r, v = "";
			var ids = rule.outid;
			if(!ids){
				ids = rule[0].outid;
			}
			var idd = rule.trigger.eleId
			var htmled = $('#'+idd);
			var place =options[0].promptmessage;
			var tile =options[0].titile;
			if(!tile){
				tile = args.title;
			}
			var cont =options[0].content;
			if(!cont){
				content = args.content;
			}
			htmled.attr("id","");
			!tile && htmled.find(".popover-title").remove();
			!cont && htmled.find(".popover-content").remove();
			$('#'+ids).popover({title:tile?tile:"x",content:cont?cont:"x",placement:place,html:true,template:htmled});
			
		},
	
};
pubsub.sub("definedButton", definedButtonFunc);var datepickerFunc = {
	getValue : function(rule, args) {
		var $id = pubsub.getJQObj(rule, true);
		return $id.datepickerExt("getValue");
	},
	setValue : function(rule, args) {
		var $id = pubsub.getJQObj(rule), r, v = "";
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param]||"";
		}
		$id.datepickerExt("setValue",v);
	},
	tclear: function(rule, args) { //清空
		var $id = pubsub.getJQObj(rule);
		var jq = $id.datepickerExt("getOject");
		jq.attr("tclear", "tclear").val("");
	},
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.datepickerExt("display",false);
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.datepickerExt("display",true);
	},
	tdisabled : function(rule, args) {// 禁用
		var $id = pubsub.getJQObj(rule);
		$id.datepickerExt("disabled",false);
	},
	tenabled : function(rule, args) {// 启用
		var $id = pubsub.getJQObj(rule);
		$id.datepickerExt("disabled",true);
	}
};
pubsub.sub("datepickerbt", datepickerFunc);



var datetimepickerFunc = {
	getValue : function(rule, args) {
		var $id = pubsub.getJQObj(rule, true);
		return $id.datetimepickerExt("getValue");
	},
	setValue : function(rule, args) {
		var $id = pubsub.getJQObj(rule), r, v = "";
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param]||"";
		}
		$id.datetimepickerExt("setValue",v);
	},
	tclear: function(rule, args) { //清空
		var $id = pubsub.getJQObj(rule);
		var jq = $id.datetimepickerExt("getOject");
		jq.attr("tclear", "tclear").val("");
	},
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.datetimepickerExt("display",false);
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.datetimepickerExt("display",true);
	},
	tdisabled : function(rule, args) {// 禁用
		var $id = pubsub.getJQObj(rule);
		$id.datetimepickerExt("disabled",false);
	},
	tenabled : function(rule, args) {// 启用
		var $id = pubsub.getJQObj(rule);
		$id.datetimepickerExt("disabled",true);
	}
};
pubsub.sub("datetimepickerbt", datetimepickerFunc);  


var daterangepickerFunc = {
		
    //开始时间赋值
	startSetValue : function(rule,args){
		var $id = pubsub.getJQObj(rule),
		    element = $id.find(".form-control"),
	        obj = $id.data("daterangepicker"),
	        date = obj.methods.formatDate(obj,args),
	        element = $id.find(".form-control");
		$(element[0]).datepicker('setDate',date);
	},
	dateFormat :function(time, format){   //格式化日期工具
	    var t = new Date(time); 
	    var tf = function(i){return (i < 10 ? '0' : '') + i};
	    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
	        switch(a){
	            case 'yyyy':
	                return tf(t.getFullYear());
	                break;
	            case 'MM':
	                return tf(t.getMonth() + 1);
	                break;
	            case 'mm':
	                return tf(t.getMinutes());
	                break;
	            case 'dd':
	                return tf(t.getDate());
	                break;
	            case 'HH':
	                return tf(t.getHours());
	                break;
	            case 'ss':
	                return tf(t.getSeconds());
	                break;
	        }
	    })   
	},
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.daterangepickerExt("display",false);
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.daterangepickerExt("display",true);
	},
	tdisabled : function(rule, args) {// 禁用
		var $id = pubsub.getJQObj(rule);
		$id.daterangepickerExt("disabled",false);
	},
	tenabled : function(rule, args) {// 启用
		var $id = pubsub.getJQObj(rule);
		$id.daterangepickerExt("disabled",true);
	}
};
pubsub.sub("daterangepicker",daterangepickerFunc); 

var ratylimasterFunc = {
	getValue : function(rule, args){
	   var $id = pubsub.getJQObj(rule, true),
	       build = $id.data("ratylimaster"),
	       el = build.el;  
	   return $(el).find('.ratymode').attr('data-rate');
	   
	},
	
	setValue : function(id, args){
	   var $id = pubsub.getJQObj(id[0].outid, true),
	       build = $id.data("ratylimaster"),
	       emp = '☆',
	       full = '★',
	       el = build.el;  
	  switch (build.option.state) {
		case 'xingxing':
			emp = '☆';
			full = '★';
			break;
		case 'radius':
			emp = 'O';
			full = 'X';
			break;	
		case 'agree':
			emp = '<i class="fa fa-thumbs-o-up"></i>';
			full = '<i class="fa fa-thumbs-up"></i>';
			break;
		default:
			emp = '☆';
		    full = '★';
			break;
		}
	   $.each(args,function(i,arg){
		   arg = arg != undefined && arg != null ? arg : 0;
		   $(el).find('.ratymode').attr('data-rate',arg);
		   $(el).find('.ratymode').empty();
		   for(var raty = 0;raty<$(el).find('.ratymode').attr('data-ratemax');raty++){
			   var content = '<span class="rate rate-full" style="cursor:default;" style="color:#fe5845">'+full+'</span>';
			   if(raty > parseInt(arg)-1){
				   content = '<span class="rate rate-empty" style="cursor:default;" >'+emp+'</span>';
			   }
			   $(el).find('.ratymode').append(content);
		   }
	   })
	   
	   
	},
};
pubsub.sub("ratylimaster", ratylimasterFunc);
var bootstrapdialogFunc = {
	curwindow : function(id,args){
		var $id = pubsub.getJQObj(id),
		build = $id.data("bootstrapdialog");
		var param = {
			title : null,
			width : null,
			height : null
		};
		$.each(args,function(i,arg){
			switch (i) {
			case '标题':
				param.title = arg;
				break;
			case '宽度':
				param.width = arg;
				break;
			case '高度':
				param.height = arg;
				break;
			}
		});
		build._open(param);
	},
	closeCurwindow : function(id,args){
		var $id = pubsub.getJQObj(id);
		var build = $id.data("bootstrapdialog");
		build._close();
	},
	hidden : function(id,args){
		var $id = pubsub.getJQObj(id);
		var build = $id.data("bootstrapdialog");
		build._hidden();
	},
	visible : function(id,args){
		var $id = pubsub.getJQObj(id);
		var build = $id.data("bootstrapdialog");
		build._visible();
	}
};
pubsub.sub("bootstrapdialog", bootstrapdialogFunc);
var bootstrapdialogFunc = {
	curwindow : function(id,args){
		var $id = pubsub.getJQObj(id),
		build = $id.data("bootstrapdialog");
		var param = {
			title : null,
			width : null,
			height : null
		};
		$.each(args,function(i,arg){
			switch (i) {
			case '标题':
				param.title = arg;
				break;
			case '宽度':
				param.width = arg;
				break;
			case '高度':
				param.height = arg;
				break;
			}
		});
		build._open(param);
	},
	closeCurwindow : function(id,args){
		var $id = pubsub.getJQObj(id);
		var build = $id.data("bootstrapdialog");
		build._close();
	},
	hidden : function(id,args){
		var $id = pubsub.getJQObj(id);
		var build = $id.data("bootstrapdialog");
		build._hidden();
	},
	visible : function(id,args){
		var $id = pubsub.getJQObj(id);
		var build = $id.data("bootstrapdialog");
		build._visible();
	}
};
pubsub.sub("bootstrapdialog", bootstrapdialogFunc);
/**
 * Metronic select2
 */
var customSelectFunc = {
	reload: function(rule, args) {
		return pubsub.getJQObj(rule).metroselect("reload", args);
	},
	getValue: function(rule, args) {
		return pubsub.getJQObj(rule, true).metroselect("getValue");
	},
	getData: function(rule, args) {
		var data = pubsub.getJQObj(rule, true).find('select').select2('data')[0].data;
		if (data) {
			return data[rule.columnName] || "";
		} else {
			return "";
		}
	},
	setValue: function(rule, args) {
		var $id = pubsub.getJQObj(rule),
			r, v = "";
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param] || "";
		}
		$id.metroselect('select', v);
	},
	getText: function(rule, args) {
		// var msg = pubsub.getJQObj(rule, true).select2('data')[0].text != '' ? pubsub.getJQObj(rule, true).select2('data')[0].text : null;
		var msg = pubsub.getJQObj(rule, true).metroselect("getText");
		return msg;
	},
	thidden: function(rule, args) { // 隐藏
		pubsub.getJQObj(rule).hide();
	},
	tshow: function(rule, args) { // 显示
		pubsub.getJQObj(rule).show();
	},
	tdisabled: function(rule, args) { // 禁用
		pubsub.getJQObj(rule).find("select").attr("disabled", "disabled");
	},
	tenabled: function(rule, args) { // 启用
		pubsub.getJQObj(rule).find("select").removeAttr("disabled");
	},
	clear: function(rule, args) {
		pubsub.getJQObj(rule).metroselect("clear");
	},


	disable: function(rule, args) {
		if (args.index) {
			pubsub.getJQObj(rule).metroselect("disable", args.index);
		}
	},
	enable: function(rule, args) {
		if (args.index) {
			pubsub.getJQObj(rule).metroselect("enable", args.index);
		}
	},
	change: function(rule, args) {
		return pubsub.getJQObj(rule).metroselect("change", args);
	},
	select: function(rule, args) {
		if (args.value) {
			pubsub.getJQObj(rule).metroselect("select", args.value);
		}
	},
	unselect: function(rule, args) {
		if (args.index) {
			pubsub.getJQObj(rule).metroselect("uncheck", args.index);
		}
	}

};
pubsub.sub("metroselect", customSelectFunc);



var jplayerFunc = {		
    //接收文件信息
	acceptMessage : function(id, args){
	   var $id = pubsub.getJQObj(id[0].outid, true),
	       build = $id.data("jp"),
	       content = $id.data("jplayer").target,
	       config = {},
	       para = []; 
	   /*
	    config = {	   
		        title : arg['title'],
				mp3 : arg['value'],
				ogg : arg['value'],
				wav : arg['value']
		 }
		
	   build.add(config);*/
	   var config = new jPlayerPlaylist({
			jPlayer: "#jquery_jplayer_2",
			cssSelectorAncestor: "#jp_container_2"
		}, [], {
			swfPath: "js/jplayer",
			supplied: "oga, mp3,wav",
			wmode: "window",
			useStateClassSkin: true,
			autoBlur: false,
			smoothPlayBar: true,
			keyEnabled: true
		});
	   var data = {	   
		        title : args['title'],
				mp3 : contextPath + args['value'],
				ogg : contextPath + args['value'],
				wav : contextPath + args['value']
		 }
	   config.add(data);
	},
	//接收文件信息
	linkageControl : function(id, args){
		var $id = pubsub.getJQObj(id.outid, true),
	       build = $id.data("jplayer");
	    build.target.find("#jquery_jplayer_2").jPlayer("clearMedia");
	    build.target.find("ul").empty();
		$.ajax({
			url : build.option.read.url,
			async : build.option.read.async,
			data : JSON.stringify(build.option.read.data),
			type : 'post',
			dataType : 'JSON',
			contentType : "application/json",
			success : function(ret) {
				
			    var data = build._convertData(ret[0].data,build.option)
				$.each(data,function(i,v){
					build.option.config.add(v);
				});  
			}
	    });
	},
	refresh : function(id, args){
		   var $id = pubsub.getJQObj(id.outid, true),
		       build = $id.data("jplayer");
		    build.target.find("#jquery_jplayer_2").jPlayer("clearMedia");
		    build.target.find("ul").empty();
			$.ajax({
				url : build.option.read.url,
				async : build.option.read.async,
				data : JSON.stringify(build.option.read.data),
				type : 'post',
				dataType : 'JSON',
				contentType : "application/json",
				success : function(ret) {
					
				    var data = build._convertData(ret[0].data,build.option)
					$.each(data,function(i,v){
						build.option.config.add(v);
					});  
				}
		    });
    
		},
	
};
pubsub.sub("jplayer", jplayerFunc);
var editorFunc = {
	init : function(rule, args) {
		var options = args[0];
		$("#" + rule.inid).summernote(options);	
		var editor = $("#" + rule.inid).next();
		editor[0].style.display = args[0].display;
	},
	getValue : function(rule, args) { //获取值
		var $id = pubsub.getJQObj(rule, true),
		    ret = UE.getEditor($id[0].id).getContent();
		ret = ret != undefined && ret != "" ? ret.replace(/"/g,"'") : ret;
		return ret;
	},
	destroy : function(rule, args) {//删除dom
		var $id = pubsub.getJQObj(rule);
		$id.destroy();
	},
	tclear: function(rule, args) { //清空
		var $id = pubsub.getJQObj(rule);
		UE.getEditor($id[0].id).setContent("")
		/*var jq = $id.next().find("div.note-editable.panel-body");
		if(jq){
			jq.html("");
		}*/
	},
	setValue : function(rule, args) { //获取值
		var $id = pubsub.getJQObj(rule),r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			$id.parents().find(".note-editable p").append(v);
		} else {
			$id.parents().find(".note-editable p").append(v);
		}
	},
};
pubsub.sub("editor", editorFunc);var diagrambtFunc = {
	init: function(rule, args) {
		var options = args[0];
		diagrambtFunc.initOptions = $.extend(true, {}, options);

		$.ajax({
			type: "POST",
			url: contextPath + "/mx/form/diagrambt/data",
			contentType: "application/x-www-form-urlencoded",
			dataType: "json",
			data: {
				'rid': options.rid,
				params: options.parameters
			},
			success: function(ret) {
				if (options.DDepth) {
					options.depth = options.DDepth;
				}
				if (options.CContent) {
					options.nodeContent = "title";
				}
				options.data = ret;
				var dataarray = [];
				dataarray.push(options.data);
				diagrambtFunc.buildTitle(dataarray);

				var $ruleinid = $("#" + rule.inid);
				$ruleinid.empty();
				$ruleinid.removeData();
				$ruleinid.orgchart(options);
				if(options.viewState){
					$ruleinid.find(".orgchart").addClass('view-state');
				}
			}
		});

	},
	buildTitle : function(dataarray){
		$.each(dataarray,function(i,item){
			var str = diagrambtFunc._buildTemplateContent(item);
			if(str){
				item.title = str;
			}
			diagrambtFunc.buildTitle(item.children);
		})
		

	},
	_buildTemplateContent : function(data){
		var options = diagrambtFunc.initOptions;
		var that = diagrambtFunc;
		that.options = options;
		var templateStr = that.options.template;

		var customdefinedArray = that.options.customdefined;
		if(customdefinedArray && customdefinedArray.length > 0){
			customdefined = customdefinedArray[0];
			if(customdefined.value){
				$.each(customdefined.value,function(i,item){
					var expdata = JSON.parse(item.expdata);
					var expression = expdata.expActVal;
					// var reg1 = /~F{[\w|\.]+}/g;

					var reg1 = /#:[^#]+#/g;

					var varVal = expdata.varVal;
					$.each(varVal,function(k,column){
						expression = expression.replace(column.value.code,"#:"+column.value.columnName+"#");
						
					})
					//column.value.code

					var columns = expression.match(reg1);
					$.each(columns,function(k,column){
						var dataItemName = column.substring(2,column.length-1);
						// dataItemName = dataItemName.split("_0_")[1];

						if(!data[dataItemName]){
							dataItemName = dataItemName.toLowerCase();
						}
						expression = expression.replace(column,data[dataItemName] || '');
					})

					if(eval(expression)){
						templateStr = item.htmlExpression;
						templateStr = that._renderContentbyTemplate(templateStr,data);
						return false;
					}
				})
			}else{
				if(templateStr != null)
					templateStr = that._renderContentbyTemplate(templateStr,data);	
			}
		}else{
			if(templateStr != null)
				templateStr = that._renderContentbyTemplate(templateStr,data);
		}

		return templateStr;
	},
	_renderContentbyTemplate : function(templateStr,data){
		var reg1 = /#:[^#]+#/g;
		// var columns = reg1.exec(templateStr);
		var columns = templateStr.match(reg1);
		if(columns){
			$.each(columns,function(i,item){
				var dataItemName = item.substring(2,item.length-1);
				if(!data[dataItemName]){
					dataItemName = dataItemName.toLowerCase();
				}
				templateStr = templateStr.replace(item,data[dataItemName]);
			})
		}
		return templateStr;
	},
	getValue: function(rule, args) {
		return args[1].id;
	},
	setValue: function(rule, args) {

	},
	getFocusNodeData: function(rule, args) {
		var $id = pubsub.getJQObj(rule, true);
		var $node = $id.find("div.node.focused");
		var focusedId = $node.attr("id");
		var data = [];
		data.push($id.find('.orgchart').data('options').data);
		var b = diagrambtFunc.getNodeDataById(data, focusedId);
		return b[rule.columnName];
	},
	getNodeDataById: function(data, id) {
		var ret = null;
		$.each(data, function(i, item) {
			if (item.id == id) {
				ret = item;
				return false;
			} else {
				ret = diagrambtFunc.getNodeDataById(item.children, id);
				if (ret != null) {
					return false;
				}
			}
		})
		return ret;
	},
	getNode: function(rule, args) { //获取图中选中节点
		var $id = pubsub.getJQObj(rule);
		if ($id.find("div.node.focused").length > 0) {
			return $id.find("div.node.focused");
		} else {
			return $("");
		}
	},
	removeNodes: function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		var $node = diagrambtFunc.getNode(rule, args);
		$id.orgchart('removeNodes', $node);
	},

	addParent: function(rule, args) {
		var $id = pubsub.getJQObj(rule),
			r, v = "";
		var $node = diagrambtFunc.getNode(rule, args);
		if ($node.length > 0) {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param];
				$id.orgchart('addParent', $id.find('.node:first'), {
					'name': v
				});
			}
		}
	},
	addSiblings: function(rule, args) {
		var $id = pubsub.getJQObj(rule),
			r, v = "";
		var $node = diagrambtFunc.getNode(rule, args);
		if ($node.length > 0) {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param];
				$id.orgchart('addSiblings', $node, {
					'siblings': [{
						'name': v,
						'relationship': '110'
					}]
				});
			}
		}
	},
	addChildren: function(rule, args) {
		var $id = pubsub.getJQObj(rule),
			r, v = "";
		var $node = diagrambtFunc.getNode(rule, args);
		var options = $.extend({}, $id.find('.orgchart').data('options'), {
			depth: 0
		});
		if ($node.length > 0) {
			var hasChild = $node.parent().attr('colspan') > 0 ? true : false;
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param];
				if (!hasChild) {
					$id.orgchart('addChildren', $node, {
						'children': [{
							'name': v,
							'relationship': '100'
						}]
					}, options);
				} else {
					$id.orgchart('addSiblings', $node.closest('tr').siblings('.nodes').find('.node:first'), {
						'siblings': [{
							'name': v,
							'relationship': '110'
						}]
					});
				}
			}
		}
	},
	grefresh: function(rule, args) {
		var params = [];
		params.push(diagrambtFunc.initOptions)
		diagrambtFunc.init({
			'inid': rule.outid
		}, params);
	},
	/**
	 * 改变格子标题颜色
	 */
	changeTitleColor: function(rule,args){
		var $id = pubsub.getJQObj(rule);

		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			if(r && r.param != 'key' && r.param != 'color')
				v = args[r.param];
		}

		var key = args.key;
		key = key || 'id';
		var $id = pubsub.getJQObj(rule);
		var options = $id.find('.orgchart').data('options');
		var dataItems = $id.find('.orgchart').data('options').data;	//数据

		var callback = function(item){
			$id.find("div.node[id='"+item.id+"']").find('div.title').css("background-color",args.color);
		}
		var dataArray = [];
		dataArray.push(dataItems)
		diagrambtFunc.getNodes(dataArray,key,v,callback);

	},
	/**
	 * [根据key与value查询出对应的节点信息]
	 * @param  {[type]}   treeDatas {....children:[{}]}
	 * @param  {[type]}   key       [description]
	 * @param  {[type]}   value     [description]
	 * @param  {Function} callback  [description]
	 * @return {[type]}             [description]
	 */
	getNodes : function(treeDatas,key,value,callback){
		$.each(treeDatas,function(i,item){
			if(item[key] == v){
				if(callback && $.isFunction(callback)){
					callback(item);
				}
			}
			diagrambtFunc.getNodes(item.children,key,value,callback);
		})
		
	},
	/**
	 * 获取数据集字段信息
	 */
	getKeyName : function(rule,args){
		return rule.columnName;
	},

};
pubsub.sub("diagrambt", diagrambtFunc);var editorbtFunc = {
	init : function(rule, args) {
		var options = args[0];
		$("#" + rule.inid).summernote(options);	
		var editor = $("#" + rule.inid).next();
		editor[0].style.display = args[0].display;
	},
	getValue : function(rule, args) { //获取值
		var $id = pubsub.getJQObj(rule, true);
		return $id.code();
	},
	destroy : function(rule, args) {//删除dom
		var $id = pubsub.getJQObj(rule);
		$id.destroy();
	},
	tclear: function(rule, args) { //清空
		var $id = pubsub.getJQObj(rule);
		var jq = $id.next().find("div.note-editable.panel-body");
		if(jq){
			jq.html("");
		}
	},
	setValue : function(rule, args) { //获取值
		var $id = pubsub.getJQObj(rule),r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			$id.parents().find(".note-editable p").append(v);
		} else {
			$id.parents().find(".note-editable p").append(v);
		}
	},
};
pubsub.sub("editorbt", editorbtFunc);/**
 * Metronic icheckbox
 */
var icheckboxFunc = {
	getSelection : function(rule, args) {
		return pubsub.getJQObj(rule).icheckbox("getSelection");
	},
	getText:function(rule, args){
		var $id =pubsub.getJQObj(rule, true), retVal = [];
		// if ($.isArray(args)&&args.length>0) { //触发事件传过来的值
		// 	retVal.push($(args[0].currentTarget).attr("text")||"");
		// }else{         //控件获取的值
			var selections = $id.icheckbox("getSelection");
			$.each(selections,function(i,v){
				retVal.push(v.text);
			});
		// }
		return retVal.join(",");
	},
	getValue:function(rule,args){
		var $id =pubsub.getJQObj(rule, true), retVal = [];
		// if ($.isArray(args)&&args.length>0) { //触发事件传过来的值
		// 	retVal.push($(args[0].currentTarget).attr("value")||"");
		// }else{         //控件获取的值
			var selections = $id.icheckbox("getSelection");
			$.each(selections,function(i,v){
				retVal.push(v.value);
			});
		// }
		return retVal.join(",");
	},
	getData:function (rule,args){
		var $id =pubsub.getJQObj(rule, true), retVal = [];
		if ($.isArray(args)&&args.length>0) { //触发事件传过来的值
//			var datas = $(args[0].delegateTarget).data("icheckbox").options.__dataArray;
			var datas =  $id.data("icheckbox").options.__dataArray;
			var index = $(args[0].currentTarget).attr("row-index")||"";
			if(datas.length>0){
				retVal.push(datas[index][rule.columnName]);
			}
		}else{         //控件获取的值
			var selections = $id.icheckbox("getSelection");
			$.each(selections,function(i,v){
				retVal.push(v["data"][rule.columnName]);
			});
		}
		return retVal.join(",");
	},
	setValue:function(rule,args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			$id.icheckbox("setValue",v);
		} else {
			$id.icheckbox("setValue",args);
		}
	},
	
	//联动事件
	linkage: function(rule, args) {
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.icheckbox("linkage",args);
		
	},
	
	getKeyName : function(rule,args){
		return rule.columnName;
	},
	
	
	tdisabled:function(rule, args){
		var $id = pubsub.getJQObj(rule);
		$id.icheckbox("disabled",false);
	},
	tenabled:function(rule, args){
		var $id = pubsub.getJQObj(rule);
		$id.icheckbox("disabled",true);
	},
	disable: function(rule, args){
		if(args.index){
			pubsub.getJQObj(rule).icheckbox("disable", args.index);
		}else{
			var key = args.key;
			// key = key || 'id';
			var value = "";
			
			$.each(rule,function(i,item){
				if(rule[i].param == 'key'){
					return true;
				}
				value = args[rule[i].param] || "";
			})

			var param = {
				key : key,
				value : value,
				type:'disable'
			}

			pubsub.getJQObj(rule).icheckbox("checkByCompare", param);
		}
	},
	enable: function(rule, args){
		if(args.index){
			pubsub.getJQObj(rule).icheckbox("enable", args.index);
		}else{
			var key = args.key;
			// key = key || 'id';
			var value = "";
			
			$.each(rule,function(i,item){
				if(rule[i].param == 'key'){
					return true;
				}
				value = args[rule[i].param] || "";
			})

			var param = {
				key : key,
				value : value,
				type:'enable'
			}

			pubsub.getJQObj(rule).icheckbox("checkByCompare", param);
		}
	},
	check: function(rule, args){
		if(args.index){
			pubsub.getJQObj(rule).icheckbox("check", args.index);
		}else{
			var key = args.key;
			// key = key || 'id';
			var value = "";
			
			$.each(rule,function(i,item){
				if(rule[i].param == 'key'){
					return true;
				}
				value = args[rule[i].param] || "";
			})

			var param = {
				key : key,
				value : value,
				type : 'check'
			}

			pubsub.getJQObj(rule).icheckbox("checkByCompare", param);
		}
	},
	uncheck: function(rule, args){
		if(args.index){
			pubsub.getJQObj(rule).icheckbox("uncheck", args.index);
		}else{
			var key = args.key;
			// key = key || 'id';
			var value = "";
			
			$.each(rule,function(i,item){
				if(rule[i].param == 'key'){
					return true;
				}
				value = args[rule[i].param] || "";
			})

			var param = {
				key : key,
				value : value,
				type : 'uncheck'
			}

			pubsub.getJQObj(rule).icheckbox("checkByCompare", param);
		}
	},
	checkAll: function(rule, args){
		pubsub.getJQObj(rule).icheckbox("checkAll");
	},
	invert: function(rule, args){
		pubsub.getJQObj(rule).icheckbox("invert");
	}
};
pubsub.sub("icheckbox", icheckboxFunc);/**
 * Metronic icheckradio
 */
var icheckradioFunc = {
	getSelection : function(rule, args) {
		return pubsub.getJQObj(rule).icheckradio("getSelection");
	},
	getText:function(rule, args){
		var $id =pubsub.getJQObj(rule, true), retVal = [];
		if ($.isArray(args)&&args.length>0 && args[0] && args[0].currentTarget) { //触发事件传过来的值
			retVal.push($(args[0].currentTarget).attr("text")||"");
		}else{         //控件获取的值
			var selections = $id.icheckradio("getSelection");
			$.each(selections,function(i,v){
				retVal.push(v.text);
			});
		}
		return retVal.join(",");
	},
	getValue:function(rule, args){
		var $id =pubsub.getJQObj(rule, true), retVal = [];
		if ($.isArray(args)&&args.length>0 && args[0] && args[0].currentTarget) { //触发事件传过来的值
			retVal.push($(args[0].currentTarget).attr("value")||"");
		}else{         //控件获取的值
			var selections = $id.icheckradio("getSelection");
			$.each(selections,function(i,v){
				retVal.push(v.value);
			});
		}
		return retVal.join(",");
	},
	getData:function (rule,args){
		var $id =pubsub.getJQObj(rule, true), retVal = [];
		if ($.isArray(args)&&args.length>0) { //触发事件传过来的值
//			var datas = $(args[0].delegateTarget).data("icheckradio").options.__dataArray;
			var datas =  $id.data("icheckradio").options.__dataArray;
			var index = $(args[0].currentTarget).attr("row-index")||"";
			if(datas.length>0){
				retVal.push(datas[index][rule.columnName]);
			}
		}else{         //控件获取的值
			var selections = $id.icheckradio("getSelection");
			$.each(selections,function(i,v){
				retVal.push(v["data"][rule.columnName]);
			});
		}
		return retVal.join(",");
	},
	setValue:function(rule,args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			$id.icheckradio("setValue",v);
		} else {
			$id.icheckradio("setValue",args);
		}
	},
	//联动事件
	linkage: function(rule, args) {
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.icheckradio("linkage",args);
		
	},
	
	tdisabled:function(rule, args){
		var $id = pubsub.getJQObj(rule);
		$id.icheckradio("disabled",false);
	},
	tenabled:function(rule, args){
		var $id = pubsub.getJQObj(rule);
		$id.icheckradio("disabled",true);
	},
	disable: function(rule, args){
		if(args.index != null){
			pubsub.getJQObj(rule).icheckradio("disable", args.index);
		}
	},
	enable: function(rule, args){
		if(args.index != null){
			pubsub.getJQObj(rule).icheckradio("enable", args.index);
		}
	},
	check: function(rule, args){
		if(args.index != null){
			pubsub.getJQObj(rule).icheckradio("check", args.index);
		}
	},
	uncheck: function(rule, args){
		if(args.index != null){
			pubsub.getJQObj(rule).icheckradio("uncheck", args.index);
		}
	}
};
pubsub.sub("icheckradio", icheckradioFunc);/**
 * 文件上传事件定义器
 */
var fileUploadFunc = {
	getValue: function(rule, args) { //取值
		var $id = pubsub.getJQObj(rule, true);
		return $id.data("saveId");
	},
	setValue: function(rule, args) { //赋值
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.attr("setValue", "setValue");
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			$id.spFileUpload("setValue",v);
		} else {
			$id.spFileUpload("setValue",args);
		}
	},
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.hide();
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.show();
	},
	tdisabled: function(rule, args) { // 禁用
		var $id = pubsub.getJQObj(rule);
		$id.spFileUpload("disabled", false);
	},
	tenabled: function(rule, args) { // 启用
		var $id = pubsub.getJQObj(rule);
		$id.spFileUpload("disabled", true);
	},
	setRamdomparent : function(rule, args){
		var $id = pubsub.getJQObj(rule);

		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
		} else {
			v = args;
		}

		$id.spFileUpload("setValue", v);
	},
	clear : function(rule,args){
		var $id = pubsub.getJQObj(rule);

		$id.spFileUpload("setValue", "");
	},
	//设置只读状态，不能上传
	setReadOnly:function(rule){
		var $id = pubsub.getJQObj(rule);
		$id.spFileUpload("setReadOnly");
	},
	//取消只读状态。
	removeReadOnly:function(rule){
		var $id = pubsub.getJQObj(rule);
		$id.spFileUpload("removeReadOnly");
	},

	disabledDelete:function(rule){
		var $id = pubsub.getJQObj(rule);
		$id.spFileUpload("disabledDelete");
	},
	removeDisabledDelete:function(rule){
		var $id = pubsub.getJQObj(rule);
		$id.spFileUpload("removeDisabledDelete");
	}
};
pubsub.sub("jqfileupload", fileUploadFunc);




function uploadSuccessDo(e,data){
	
	var files = data.result, names = "", ids = "", 
	  	outputNames = $(this).data("spFileUpload").opts.outputNames,
		outputIds = $(this).data("spFileUpload").opts.outputIds;
		for (var i = 0; i < files.length; i++) {
			names += files[i].fileName + ",";
			ids += files[i].id + ",";
		}
		if (outputNames && names) {
			var opns = outputNames.split(",");
			for (var k = 0; k < opns.length; k++) {
				outputName = opns[k];
				var $op = $("#" + outputName);
				var opval = $op.val();
				$op.val((opval ? (opval + ",") : "")+ names.substr(0,names.length - 1));
			}
		}
		if (outputIds && ids) {
			var opids = outputIds.split(",");
			for (var k = 0; k < opids.length; k++) {
				var outputId = opids[k], $op = $("#"
						+ outputId), opval = $op.val();
				$op.val((opval ? (opval + ","): "")+ ids.substr(0,ids.length - 1));
			}
		}
}

function deleteDo(data){
	
	var files = data, names = "", ids = "", 
	  	outputNames = $(this).data("spFileUpload").opts.outputNames,
		outputIds = $(this).data("spFileUpload").opts.outputIds;
	 	for (var i = 0; i < files.length; i++) {
			names += files[i].fileName + ",";
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
				var outputId = opns[k], $op = $("#"
						+ outputId), str = ($op.val() + ",")
						.replace(ids, "");
				$op.val(str.substr(0, str.length - 1));
			}
		}
}/**
 * label事件定义器
 */
var labelFunc = {
	getValue: function(rule, args) { //取值
		var $id = pubsub.getJQObj(rule, true);
		var $span = $id.find("span").length>0?$id.find("span"):$id;
		return $span.html();
	},
	setValue: function(rule, args) { //赋值
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.attr("setValue", "setValue");
		var $span = $id.find("span").length>0?$id.find("span"):$id;
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			$span.html(v);
		} else {
			$span.html(args);
		}
	},
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.hide();
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.show();
	}
};
pubsub.sub("label", labelFunc);

/**
 * Metronic Button
 */
var metrolistFunc = {
	reload : function(rule, args) {
		return pubsub.getJQObj(rule).metrolist("reload", args);
	},
	removeItem : function(rule, args){
		return pubsub.getJQObj(rule).metrolist("removeItem", args);
	},
	getDrapRow : function(rule,args){
		var $inid = pubsub.getJQObj(rule,true);
		var column = $inid.metrolist("getDrapRow");
		return column[rule.columnName];
	}
};
pubsub.sub("metrolist", metrolistFunc);/**
 * 登录事件
 */
var loginUserNameFunc = {

	getValue: function(rule, args) { //取值
		var $id = pubsub.getJQObj(rule, true);
		return $id.find("input").val();
	}
	
};
pubsub.sub("loginusername", loginUserNameFunc);


var loginPasswordFunc = {

	getValue: function(rule, args) { //取值
		var $id = pubsub.getJQObj(rule, true);
		return $id.find("input").val();
	}
	
};
pubsub.sub("loginpassword", loginPasswordFunc);



var loginVerificationFunc = {

	getValue: function(rule, args) { //取值
		var $id = pubsub.getJQObj(rule, true);
		return $id.find("input").val();
	}
};
pubsub.sub("login_verify", loginVerificationFunc);


var loginAlertFunc = {

	setValue: function(rule, args) { //取值
		var $span = pubsub.getJQObj(rule).find("span"), r, v = "";
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param]||"";
		}
		$span.html(v);
	}
};
pubsub.sub("loginalert", loginAlertFunc);


var loginFormFunc = {
	getValue: function(rule, args) { //取值
		var $id = pubsub.getJQObj(rule, true);
		return $id.attr("alert");
	}
};
pubsub.sub("loginform", loginFormFunc);/**
 * menu事件
 */
var megaMenuFunc = {

	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.megaMenuExt("display",false);
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.megaMenuExt("display",true);
	},
	getMenu:function(rule,args){	//获取数据集
		return args[0][rule.columnName];
	},
	removeAllSelected : function(rule,args){
		//取消所有选中
		var $id = pubsub.getJQObj(rule);
		var selectcolor = $id.attr("selectcolor");
		var selectColor = $id.megaMenuExt("getSelectColor", selectcolor);
		$id.data("select").removeClass(selectColor);
		$id.removeData("select");
	}
	
};
pubsub.sub("megamenu", megaMenuFunc);var ratylimasterFunc = {
	getValue : function(rule, args){
	   var $id = pubsub.getJQObj(rule, true),
	       build = $id.data("ratylimaster"),
	       el = build.el;  
	   return $(el).find('.ratymode').attr('data-rate');
	   
	},
	
	setValue : function(id, args){
	   var $id = pubsub.getJQObj(id[0].outid, true),
	       build = $id.data("ratylimaster"),
	       emp = '☆',
	       full = '★',
	       el = build.el;  
	  switch (build.option.state) {
		case 'xingxing':
			emp = '☆';
			full = '★';
			break;
		case 'radius':
			emp = 'O';
			full = 'X';
			break;	
		case 'agree':
			emp = '<i class="fa fa-thumbs-o-up"></i>';
			full = '<i class="fa fa-thumbs-up"></i>';
			break;
		default:
			emp = '☆';
		    full = '★';
			break;
		}
	   $.each(args,function(i,arg){
		   arg = arg != undefined && arg != null ? arg : 0;
		   $(el).find('.ratymode').attr('data-rate',arg);
		   $(el).find('.ratymode').empty();
		   for(var raty = 0;raty<$(el).find('.ratymode').attr('data-ratemax');raty++){
			   var content = '<span class="rate rate-full" style="cursor:default;" style="color:#fe5845">'+full+'</span>';
			   if(raty > parseInt(arg)-1){
				   content = '<span class="rate rate-empty" style="cursor:default;" >'+emp+'</span>';
			   }
			   $(el).find('.ratymode').append(content);
		   }
	   })
	   
	   
	},
};
pubsub.sub("ratylimaster", ratylimasterFunc);
/**
 * Metronic select2
 */
var metroselectFunc = {
	reload: function(rule, args) {
		return pubsub.getJQObj(rule).metroselect("reload", args);
	},
	getValue: function(rule, args) {
		return pubsub.getJQObj(rule, true).metroselect("getValue");
	},
	getData: function(rule, args) {
		var data = pubsub.getJQObj(rule, true).find('select').select2('data')[0].data;
		if (data) {
			return data[rule.columnName] || "";
		} else {
			return "";
		}
	},
	setValue: function(rule, args) {
		var $id = pubsub.getJQObj(rule),
			r, v = "";
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param] || "";
		}
		$id.metroselect('select', v);
	},
	getText: function(rule, args) {
		// var msg = pubsub.getJQObj(rule, true).select2('data')[0].text != '' ? pubsub.getJQObj(rule, true).select2('data')[0].text : null;
		var msg = pubsub.getJQObj(rule, true).metroselect("getText");
		return msg;
	},
	thidden: function(rule, args) { // 隐藏
		pubsub.getJQObj(rule).hide();
	},
	tshow: function(rule, args) { // 显示
		pubsub.getJQObj(rule).show();
	},
	tdisabled: function(rule, args) { // 禁用
		pubsub.getJQObj(rule).find("select").attr("disabled", "disabled");
	},
	tenabled: function(rule, args) { // 启用
		pubsub.getJQObj(rule).find("select").removeAttr("disabled");
	},
	clear: function(rule, args) {
		pubsub.getJQObj(rule).metroselect("clear");
	},


	disable: function(rule, args) {
		if (args.index) {
			pubsub.getJQObj(rule).metroselect("disable", args.index);
		}
	},
	enable: function(rule, args) {
		if (args.index) {
			pubsub.getJQObj(rule).metroselect("enable", args.index);
		}
	},
	change: function(rule, args) {
		return pubsub.getJQObj(rule).metroselect("change", args);
	},
	select: function(rule, args) {
		if (args.value) {
			pubsub.getJQObj(rule).metroselect("select", args.value);
		}
	},
	unselect: function(rule, args) {
		if (args.index) {
			pubsub.getJQObj(rule).metroselect("uncheck", args.index);
		}
	}

};
pubsub.sub("metroselect", metroselectFunc);



/**
 * metroselectMFunc
 */
var metroselectMFunc = {
	reload: function(rule, args) {
		return pubsub.getJQObj(rule).metroselect_m("reload", args);
	},
	getValue: function(rule, args) {
		return pubsub.getJQObj(rule, true).metroselect_m("getValue");
	},
	getData: function(rule, args) {
		var data = pubsub.getJQObj(rule, true).find('select').select2('data');
		var _rowIndex = 'row-index',
			val = [],
			item;
		for (var j = 0; j < data.length; j++) {
			item = data[j]["data"][rule.columnName] || "";
			val.push(item);
		}
		return val.join(",");
	},
	setValue: function(rule, args) {
		var $id = pubsub.getJQObj(rule),
			r, v = "";
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param] || "";
		}
		if(v){
			v = v.split(",");	
		}
		$id.metroselect_m('select', v);
	},
	getText: function(rule, args) {
		return pubsub.getJQObj(rule, true).metroselect_m("getText");
	},
	thidden: function(rule, args) { // 隐藏
		pubsub.getJQObj(rule).hide();
	},
	tshow: function(rule, args) { // 显示
		pubsub.getJQObj(rule).show();
	},
	tdisabled: function(rule, args) { // 禁用
		pubsub.getJQObj(rule).find("select").attr("disabled", "disabled");
	},
	tenabled: function(rule, args) { // 启用
		pubsub.getJQObj(rule).find("select").removeAttr("disabled");
	},
	clear: function(rule, args) {
		pubsub.getJQObj(rule).metroselect_m("clear");
	},



	disable: function(rule, args) {
		if (args.index) {
			pubsub.getJQObj(rule).metroselect_m("disable", args.index);
		}
	},
	enable: function(rule, args) {
		if (args.index) {
			pubsub.getJQObj(rule).metroselect_m("enable", args.index);
		}
	},
	change: function(rule, args) {
		return pubsub.getJQObj(rule).metroselect_m("change", args);
	},
	select: function(rule, args) {
		if (args.value) {
			pubsub.getJQObj(rule).metroselect_m("select", args.value);
		}
	},
	unselect: function(rule, args) {
		if (args.index) {
			pubsub.getJQObj(rule).metroselect_m("uncheck", args.index);
		}
	},
	selectIndex: function(rule, args) {
		if (args.index != null) {
			pubsub.getJQObj(rule).metroselect_m("selectIndex", args.index);
		}
	}

};
pubsub.sub("metroselectm", metroselectMFunc);


/**
 * Metronic select2
 */
var selectFunc = {
	selectIndex: function(rule,args){
		var $that = pubsub.getJQObj(rule);

	},
	reload: function(rule, args) {
		var $that = pubsub.getJQObj(rule),
			options = $that.data("select2").options.options,
			params = $.extend({}, options.editor.params, {
				params: JSON.stringify(args)
			});
		$.ajax({
			url: contextPath + options.editor.url.replace("contextPath", ""),
			type: 'POST',
			dataType: 'JSON',
			contentType: "application/x-www-form-urlencoded",
			data: params,
			success: function(data) {
				var cons = [];
				if (data && data.length) {
					$.each(data, function(i, v) {
						v["id"] = v[options.editor.params.value];
						v["text"] = v[options.editor.params.text];
						cons.push(v);
					});
				}
				var oldData = $that.select2("data") || [];
				$that.empty();
				$that.select2($.extend({},options,{
					data: cons
				}))
				if(oldData[0] || !options.editor.defaultIndex){
					$that.val(oldData[0]?oldData[0].value:"").trigger("change");
				}else if(cons[options.editor.defaultIndex-1]){
					$that.val(cons[options.editor.defaultIndex-1].id).trigger("change");
				}
			},
			async: true
		});
		return $that;
	},
	getValue: function(rule, args) {
		return pubsub.getJQObj(rule, true).val();
	},
	getData: function(rule, args) {
		var data = pubsub.getJQObj(rule, true).find('select').select2('data')[0].data;
		if (data) {
			return data[rule.columnName] || "";
		} else {
			return "";
		}
	},
	setValue: function(rule, args) {
		var $id = pubsub.getJQObj(rule),
			r, v = "";
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param] || "";
		}
		$id.val(v).trigger("change");
	},
	getText: function(rule, args) {
		return pubsub.getJQObj(rule, true).metroselect("getText");
	},
	thidden: function(rule, args) { // 隐藏
		pubsub.getJQObj(rule).hide();
	},
	tshow: function(rule, args) { // 显示
		pubsub.getJQObj(rule).show();
	},
	tdisabled: function(rule, args) { // 禁用
		pubsub.getJQObj(rule).find("select").attr("disabled", "disabled");
	},
	tenabled: function(rule, args) { // 启用
		pubsub.getJQObj(rule).find("select").removeAttr("disabled");
	},
	clear: function(rule, args) {
		pubsub.getJQObj(rule).val("").trigger("change");
	},
	disable: function(rule, args) {
		if (args.index) {
			pubsub.getJQObj(rule).metroselect("disable", args.index);
		}
	},
	enable: function(rule, args) {
		if (args.index) {
			pubsub.getJQObj(rule).metroselect("enable", args.index);
		}
	},
	change: function(rule, args) {
		return pubsub.getJQObj(rule).metroselect("change", args);
	},
	select: function(rule, args) {
		if (args.value) {
			pubsub.getJQObj(rule).metroselect("select", args.value);
		}
	},
	unselect: function(rule, args) {
		if (args.index) {
			pubsub.getJQObj(rule).metroselect("uncheck", args.index);
		}
	}
};
pubsub.sub("select", selectFunc);
pubsub.sub("select2", selectFunc);// 按钮事件 klaus.wang 2015-05-14
function configMtbutton(options){
	if(options&&!options.isVisible){
		$("#"+options.id).css("display", "none");
	}
	if(options&&!options.isEnabled){
		$("#"+options.id).attr("disabled","disabled");	
	}
	if(options&&options.sureDialog){
		if (!$().confirmation) {
            return;
        }
        $("#"+options.id).off(".mtbutton");
        $("#"+options.id).confirmation({ 
        	container: 'body', 
        	placement: options.dialogPosition,
			title : '是否确定?',
        	btnOkClass: 'btn btn-sm btn-success', 
        	btnOkLabel: '确定',
        	btnCancelClass: 'btn btn-sm btn-danger',
        	btnCancelLabel : '取消',
        	onConfirm : function(event, element){
        		kendoButtonFunc(options, 'mtbutton');
        	}
        });	
	}
	
}

function kendoButtonFunc(viewModel) {
	if (!viewModel.buttonType) {
		return false;
	}
	var functions = viewModel.functions;
	if (!functions) {
		functions = new MtButton(viewModel);
	}

	if ((viewModel.buttonType in functions)) {
		functions[viewModel.buttonType]();
	} else {
		alert(viewModel.buttonType + ' 方法未找到');
	}
}
function MtButton(vm) {

	this.buttonKey = "MtButton";

	this.$target = $("#" + vm.id);

	this.viewModel = vm;

	this.init();

	(function(that){
		if(that.$target.get(0))
			$.data(that.$target.get(0), that.buttonKey, that);
	})(this);

}
MtButton.prototype.dialogInst = function(dialogRel) {
	this.dialogRel = dialogRel;
};
MtButton.prototype.init = function() {
	this.viewModel.data = new Object();
	if (this.viewModel.handlecolumns)
		this.viewModel.data.tableMsg = eval('(' + this.viewModel.handlecolumns + ')');
	if (this.viewModel.ruleData) {
		this.viewModel.data.rule = eval('(' + this.viewModel.ruleData + ')');
	}
	this.viewModel.data.pageParameters = pageParameters;
	this.viewModel.functions = this;
};

/**
 * 表单验证
 * @returns
 */
function getFormValiate(){
	var $valiSave = $("[valiSave=true]");
	if(!$valiSave.get(0)){
		return true; //不验证
	} else { 
		return $valiSave.valid(); 
	}
}

/**
 * 保存操作
 */
MtButton.prototype.save = function($alert, args) {
	args = args || {};
	var K = this,
		data = new Array(),
		tables = new Object();
	//验证
	/*if (K.viewModel.saveValidate) {
		var valis = $("[valisave=true]"),
			vali, length = valis.length,
			i = 0,
			bol, rebol = true;
		if (valis && valis.length) {
			var first = null;
			for (; i < length; i++) {
				vali = valis[i];

				bol = $(vali).valid();
				if (!bol){
					rebol = false;
				}
				if(!first && !rebol){
					first = $(vali);
				}
			}
			if(first){
				$(first).focus();
			}
			if (!rebol)
				return false;//unsubmit
		}
	}*/
	
	if(!window.getFormValiate()){
		return false;
	}
	
	var vmd = K.viewModel.data;
	if (!vmd.tableMsg) {
		alert('未保存设置!');
		return false;
	}
	
	var tableMap = null;
	jQuery.each(K.viewModel.data.tableMsg, function(i, tableMsg) {
		var container = {dataSetId : tableMsg.dataSetId,
				idValue : tableMsg.idValue,
				table : tableMsg.table,
				columns : new Array(),
				batch : tableMsg.batch,
				wdataSet : tableMsg.wdataSet};
		
		var tmpMap = {};
		if (tableMsg.columns) {
			$.each(tableMsg.columns, function(index, v) {

				var $this = jQuery('#' + v.id),
					params = $this.data('params') || {};

				if (params.idValue && !container.idValue) {
					container.idValue = params.idValue;
				}

				try {
					v.value = $this.mtbootstrap('getValue', v.fieldName);
				} catch(e){
					v.value = $this.val() || "";
				}
				container.columns.push(v);
				
				tmpMap[v.fieldName] = v;
			});
		}

		
		/**
		 *  获取页面获取主键
		 */
		var tables = $("." + container.table.tableName);
		if (tables.length) {
			tables.each(function(i, v) {
				var $this = $(v), fieldName = $this.attr('fieldName');
				
				/**
				 * 判断该主键字段 值有没有
				 */
				if(fieldName && (fieldName in tmpMap) //
						){
					tmpMap[fieldName].value = tmpMap[fieldName].value;
				} else if(fieldName){
					container.columns.push({
						fieldName: fieldName,
						value: $this.val()
					});
				}
				
			});
		}

		if (container.columns.length > 0) {
			if (tableMsg.batch || tableMsg.cell) {
				if (window.batch) {
					if (container.wdataSet && container.wdataSet.id) {
						//window.batch.getValue(container.columns = []);
						//data.push(container);
						if(!tableMap){
							var cols = [];
							tableMap = {};
							window.batch.getValue(cols);
							if(cols.length > 0){
								$.each(cols, function(i, v){
									var t = v.table;
									if(t){
										!tableMap[t.tableName] && (tableMap[t.tableName] = []);
										tableMap[t.tableName].push(v);
									}
								});
							}
						}
						container.columns = tableMap[container.table.tableName] || [];
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
			
			var msg = data[0], relation = [];
			if (msg && msg.wdataSet  && msg.wdataSet.id) {// 更新集保存方式
				var saveMsg = [];

				function to(m, collection, dels) {
					var obj = {
						id : m.idValue
					};
					$.each(m.columns, function(i, v) {
						if(v.delete && v.id){
							dels.push(v);
						} else if (m.batch) {
							to(v, collection);
						} else {
							obj[v.fieldName] = v.value;
						}
					});
					$.extend(obj, args); //外部传入的参数优先级高，覆盖表单数据
					if (!m.batch)
						collection.push(obj);
				}
				var map = {}, ch, chMap = {};
				
				$.each(data, function(i, m){
					if((ch = m.table.children) && ch.length){
						$.each(ch, function(ii, mm){
							chMap[mm] = m.table.id;//将子节点的父节点id存放起来
						});
					}
				});
				
				$.each(data, function(i, m) {
					var t = {
						id : m.wdataSet.id,
						table : m.table,
						datas : [],
						dels : []
					};
					m.table.children = [];
					
					if(chMap[m.table.tableName] && !m.table.topId){
						m.table.topId = chMap[m.table.tableName];
					}
					
					!map[m.table.id] && (map[m.table.id] = m.table) &&//
					(relation.push($.extend(true, {}, m.table)));
					//relation.push(m.table);
					to(m, t.datas, t.dels);

					saveMsg.push(t);
				});
				
				if(relation.length > 1){
					//relation = $.transformToTreeFormat(relation, "id", "topId", "children");
					relation = $.transformToTreeFormat(relation, "tableName", "topTableName", "children");
				} else {
					relation = null;
				}

				$.ajax({
					url : contextPath + '/mx/form/data/saveFormData',
					type : 'POST',
					data : {
						relation : relation ? JSON.stringify(relation) : relation,
						tableMsg : JSON.stringify(saveMsg)
					},
					error:function(){
						window.savecount++;
					},
					dataType : 'JSON',
					success : successFunc
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
				if(!tmsg && relation && relation.length){
					var table = relation[0];
					if(v.table.id == table.id && data[table.id]){
						tmsg = {
							idValue : data[table.id]
						};
					}
				}
				if (tmsg) {
					v.idValue = tmsg.idValue || tmsg;
					!$("input." + v.table.tableName)[0] && ($("<input>", {
						'class' : v.table.tableName,
						type : 'hidden',
						fieldname : 'id',
						value : v.idValue
					}).appendTo("body"));

					var $idVal = $("." + mtxx.params.id);
					if (!$idVal.get(0)) {
						$idVal = $("<input>", {
							'class' : mtxx.params.id,
							type : 'hidden',
						}).appendTo("body");
					}

					$idVal.attr({
						idValue : v.idValue
					});

				}
				if (v.cell && window.batch) {
					window.batch.refresh();
				}
				
				/**
				 * 变长区id赋值
				 */
				if(v.batch && data.batchIds){
					var $tr, row;
					$.each(data.batchIds, function(cls, id){
						$tr = $("._" + cls);
						if($tr.get(0) && !(row = $tr.data("row"))){
							 $tr.data("row", {id : id});
						}
					});
				}
			}

			/**
			 * 保存成功回调
			 */
			function successFunc(data) {
				window.savecount++;
				if (data && data.message && !data.rst) {
					alert(data.message);
					mtDebugger.pushQueue("error", "页面保存失败！错误信息为:");
					mtDebugger.pushQueue("error", data.message);
				} else {
					mtDebugger.pushQueue("info", "页面保存成功！其保存结果为:");
					mtDebugger.pushQueue("info", data);
					if(!$alert && !args.isAlt)
						alert('操作成功!');
					$.each(vmd.tableMsg, function(i, v) {
						eachTableMsg(i, v, data);
					});
					if($alert && $.isFunction($alert)){
						$alert.call(window, data);
					}
				}
			}
			
			jQuery.ajax({
				url: contextPath + '/mx/form/data/saveOrUpdateDetail',
				type: 'post',
				data: {
					tableMsg: JSON.stringify(data)
				},
				async: true,
				dataType: 'json',
				error:function(){
					window.savecount++;
				},
				success: function(data) {
					window.savecount++;
					if (data && data.message) {
						alert(data.message);
					} else {
//						alert('操作成功!');
						
						successFunc(data);
						
						var odt = getOpenDialogToggle(window);
						if(odt){
							var bootstrapData = K.bootstrapData(parent.$(odt.viewModel.change));
							if(bootstrapData && bootstrapData.bootstrap){
								bootstrapData.bootstrap.load();
							}
						}
						if (K.viewModel.data.rule.closeWindow == 'true') {
							K.closeWindow();
						}
					}
				}
			});
		}
	} else {
		//alert('请输入!');
	}
};
/**
 * 关闭操作
 */
MtButton.prototype.closeWindow = function() {
	var that = this;
	var pageParameters = that.viewModel.data.pageParameters;
	if (pageParameters) {
		var rst = MtButtonClose(window, that);
		if (!rst) {
			window.close();
		}
	} else {
		window.close();
	}
};
/**
 * 查询操作
 */
MtButton.prototype.search = function() {
	var jq = jQuery(this.viewModel.change);

	var paraType = this.viewModel.data.rule.paraType,
		idRelation = {};
	if (paraType && paraType[0]) {
		var datas = paraType[0].datas;
		if (datas) {
			pubsub.pub("linkageControl", datas);
		}
	}

};
/**
 * 删除操作
 */
MtButton.prototype['delete'] = function() {
	var K = this,
		deleteFunctions = K.deleteFuncs;
	jQuery.each(K.viewModel.data.tableMsg, function(i, v) {
		var $this = jQuery('#' + v.id),
			dataRole = K.bootstrapData($this).dataRole;
		if (deleteFunctions[dataRole]) {
			deleteFunctions[dataRole].call(K, $this);
		}
	});
};
// 删除方法
MtButton.prototype.deleteFuncs = {
	gridbt: function(jq) {
		var K = this,
			bootstrapData = K.bootstrapData(jq);
		var grid = bootstrapData.bootstrap;
		var rows = grid.getSelectedRows();
		if (rows && rows.length > 0) {
			if (K.viewModel.multiPlay == 'false') {
				if (rows.length > 1) {
					alert('请选择一条记录!');
					return false;
				}
			}
			var models = rows;
			grid.ajaxDestroy(models, function(){
				grid.load();
			});
//			confirm2('你确定删除吗?', function(result) {
//				if(result){
//				}
//			});
		} else if (rows.length == 0) {
			alert('请选择!');
		}
	}
};
/**
 * 流程提交
 */
MtButton.prototype.submit = function() {
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
										addVariables(v.eleId, v.taskId);
									});
								}
							});
							processData = processGetData() || {};
							$.extend(params, processData)
						}
						mtxx.params.flow = null;
					}
				})
			}

			if (confirm("你确定提交流程吗?")) {
				if (processData && !processData.id) {
					alert("请先保存!");
					return false;
				} else {
					$.post(contextPath + "/mx/form/workflow/defined/processSubmit", params, function(ret) {
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
/**
 * 新建窗口
 */
MtButton.prototype.newWindow = function(type) {
	var K = this,
		id = 'mtWindow_' + K.viewModel.id,
		rule = K.viewModel.data.rule,
		maximize = K.viewModel.maximize;
	var jumpType = rule.jumpType; // 页面跳转类型
	var jumpFuncs = {
		childPage: { // 子窗口(弹出)
			open: function() {
				K.newWinFuncs.init(K, function(url, data) {
					var width = rule.width || 650;
					var height = rule.height || 450;
					if(maximize){
						width = $(document).width()-24;
						height = $(document).height()-81;
					}
					var dialog = BootstrapDialog.show({
						draggable:true,
				        message: function(dialog) {
				            var pageToLoad = dialog.getData('pageToLoad');
				            var frame = "<iframe onload=\"$(this).contents().find('body').css('overflow-y', 'auto');\" width='100%' height='"+height+"' src='"+pageToLoad+"' frameborder='no' scrolling='yes'></iframe>";
				            return frame;
				        },
				        data: {
				            'pageToLoad': url + '?' + jQuery.param(data)
				        },
				        css : {
							width : width+'px',
							height: height+'px'
						}
				    });
				    if(maximize){
					    dialog.$modal.css({
					    	display: 'table',
					    	padding: '0px 10px'
					    });
					    dialog.$modalDialog.css({
					    	margin: '0px 10px'
					    });
				    }
				    K.dialogInst(dialog);
				});
			},
			close: function(win) {
				//jQuery('#' + id).data('kendoWindow').close();
			}
		},
		singlePage: { // 跳转页面
			open: function() {
				K.newWinFuncs.init(K, function(url, data) {
					window.open(url + '?' + jQuery.param(data));
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
// 窗口方法
MtButton.prototype.newWinFuncs = {
	init: function(K, fn) {
		var paraType = K.viewModel.data.rule.paraType,
			idRelation = {};
		var url = contextPath + '/mx/form/page/viewPage';
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
				params = pubsub.getParameters(datas);
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
	},
	grid: function(K, jq) {
		var bootstrapData = K.bootstrapData(jq),
			grid = bootstrapData.bootstrap,
			rows = grid.select();
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
			}
		} else {
			alert('请选择!');
			return false;
		}
	}
};
var roleList = {
	gridbt: 'grid'
};
function getBootstrapData(jq) {
	if (!jq || !jq[0]) {
		return null;
	}
	var dataRole = jq.attr('data-role');
	return {
		dataRole: dataRole || 'text',
		bootstrap: jq.data(roleList[dataRole])
	};
}
MtButton.prototype.bootstrapData = function(jq) {
	return getBootstrapData(jq);
};
function getOpenDialogToggle(win) {
	if (pageParameters) {
		var params = pageParameters;
		var parent = win.opener || win.parent;
		if (params && params.btnId) {
			var $btn = parent.$('#' + params.btnId);
			var btn = $btn.data('MtButton');
			return btn;
		}
	}
	return null;
}
function MtButtonClose(win, btnTarget) {
	if (pageParameters) {
		var params = pageParameters;
		var parent = win.opener || win.parent;
		if (params && params.btnId) {
			var $btn = parent.$('#' + params.btnId);
			var btn = $btn.data(btnTarget.buttonKey);
			if(params.newWin && btn){
				btn.dialogRel.close();
			}
		} else {
			return MtButtonClose(parent);
		}
	}
	return false;
}
/**
 * Metronic Button
 */
var mtbuttonFunc = {
		binding: function(rule, args,options){
			debugger;
			var $id = pubsub.getJQObj(rule),r, v = "";
			var ids = rule.outid;
			if(!ids){
				ids = rule[0].outid;
			}
			var idd = rule.trigger.eleId
			var htmled = $('#'+idd).clone();
			var place =options[0].promptmessage;
			var tile =options[0].titile;
			if(!tile){
				tile = args.title;
			}
			htmled.attr("id","");
			if(tile){
				$('#'+ids).tooltip({title:tile,placement:place,html:true,template:htmled});
			}else {
				htmled.find('.tooltip-inner').remove();
				$('#'+ids).tooltip({title:"z",placement:place,html:true,template:htmled});
			}
			
		},
		setValue : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			$.each(args,function(i,arg){
				$id.find("span.frame-variable")[0].innerHTML = arg;
			});
			
		},
		//添加按钮
		copyMtButon : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			var content = $id.parent().parent();
			var div = $id.parent().parent().parent();
			
			/*
			$id.closest("div").append($id[0].outerHTML);*/
			$.each(args,function(i,arg){
				if(arg.toString().indexOf(",") != -1){
					var arr = arg.toString().split(",");
					for(var z=0;z<arr.length;z++){
						content.before(content[0].outerHTML);
						$(div.find("div.row")[z]).find("button").css("display","block");
						$(div.find("div.row")[z]).find("span")[0].innerHTML = arr[z];
					}
				}
				else{
					content.before(content[0].outerHTML);
					$(div.find("div")[0]).find("button").css("display","block");
					div.find("div").find("span")[0].innerHTML = arg;
				}
				
			})
		},
		bindpopover: function(rule, args,options){
			var $id = pubsub.getJQObj(rule),r, v = "";
			var ids = rule.outid;
			if(!ids){
				ids = rule[0].outid;
			}
			var idd = rule.trigger.eleId
			var htmled = $('#'+idd);
			var place =options[0].promptmessage;
			var tile =options[0].titile;
			if(!tile){
				tile = args.title;
			}
			var cont =options[0].content;
			if(!cont){
				content = args.content;
			}
			htmled.attr("id","");
			!tile && htmled.find(".popover-title").remove();
			!cont && htmled.find(".popover-content").remove();
			$('#'+ids).popover({title:tile?tile:"x",content:cont?cont:"x",placement:place,html:true,template:htmled});
			
		},
	
};
pubsub.sub("mtbutton", mtbuttonFunc);function MyAjax(options) {
	var oldSuccess = options.success;
	options.success = function(msg) {
		if (oldSuccess && $.isFunction(oldSuccess)) {
			oldSuccess(msg);
		}
		var data = options.data;

		if (data) {
			if (typeof data === "string") {
				data = JSON.parse(data);
			}
			if (data.params && typeof data.params === 'string') {
				data.params = JSON.parse(data.params);
			}
		}

		if (typeof msg === "string") {
			msg = JSON.parse(msg);
		}

		var debuggerObj = [{
			tmplId: "operatorTmpl",
			name: '操作名称',
			value: options.operation
		}, {
			tmplId: "requestParamsTmpl",
			name: '请求参数',
			value: data.params || data
		}, {
			tmplId: "responseResultTmpl",
			name: '返回结果',
			value: msg
		}]

		if (msg && msg.statusCode == '500') {
			debuggerObj.push({
				tmplId: "responseStatusTmpl",
				name: '结果状态',
				value: false
			})
		} else {
			debuggerObj.push({
				tmplId: "responseStatusTmpl",
				name: '结果状态',
				value: true
			})
		}

		mtDebugger.pushQueue("info", 'operatorTmpl', debuggerObj);
	}
	$.ajax(options);
};

var pageBtFunc = {
	getValue: function(rule, args) {
		if (mtxx.params) {
			return mtxx.params[rule.inparam];
		}
		return null;
	},
	getRow: function(rule, args, obj) {
		//优先从缓存中获取 即配置了页面输入输出关系的
		// var targer = pubsub.getJQObj(rule);
		// var params = targer.data("params");
		// if (params && params.value) {
		// 	return params.value;
		// }
		//获取不到则从页面数据中获取
		var params = pageParameters,
			idatasetId = rule.idatasetId || rule.indatasetId,
			thatWin = /*pubsub.getThat(rule, true)*/ window;
		if (idatasetId) {
			var dataSources = params["detail"]["dataSources"];
			if (!$.isEmptyObject(dataSources) && dataSources[idatasetId]) {
				return dataSources[idatasetId][rule.columnName] || '';
			}
		} else {
			//输出参数为obj时候
			pubsub.outParamsObj(rule, obj, params, thatWin.callbackParam);

			return pubsub.inParamsObj(rule, thatWin.callbackParam) || params[rule.columnName] || (thatWin.callbackParam && thatWin.callbackParam[rule.columnName]) || '';
		}
		return "";

	},
	/**
	 * [设置页面缓存参数]
	 */
	setValue: function(rule, params, args) {
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		thatWin.callbackParam || (thatWin.callbackParam = {});
		for (var p in params) {
			thatWin.callbackParam[p] = params[p];
		}
	},
	linkageControl: function(rule, params) {
		pageBtFunc.linkagePage(rule, params);
	},
	linkagePage: function(rule, params) {
		params.newWin = '1';
		var p = pubsub.paramsToStr(params),
			id = rule;
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
	getClientIp: function(rule, params) {
		var ruleOne;
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}

		$.ajax({
			url: contextPath + "/mx/form/page/getClientIp",
			type: "post",
			dataType: "json",
			async: true,
			data: params,
			success: function(ret) {
				if (ret) {
					//回调参数信息
					thatWin.callbackParam = thatWin.callbackParam || {};
					$.each(ruleOne.callback, function(i, k) {
						thatWin.callbackParam[k.param] = ret[k.columnName];
					})
				} else {
					$.each(ruleOne.callback, function(i, k) {
						thatWin.callbackParam[k.param] = "";
					})
				}
				if (ret) {
					pubsub.execChilds(rule);
				}
			},
			error: function(e) {
				console.log("服务器处理错误！,请修改后再试。");
			}
		})
	},
	newWindow: function(id, params, args, windowUrl) { // 弹出窗口
		// args[0]
		// 格式{link:{url:'xxxx',name:'图片链接',id:'xx'},model:true,title:'标题',jumpType:'singlePage',width:'200px',height:'100px'}
		var obj = args ? args[0] : null;
		if (obj && obj.jumpType) {} else {
			var parameters_ = params.parameters || "";
			if (parameters_) {
				delete params.parameters;
			}

			var p = pubsub.paramsToStr(params, ['headerFontColor','closeBtnSize','closeBtnColor','isMax', 'width', 'height', 'singlePage', //
				'model', 'title', 'mtOpenTab', 'TABNAME', 'headerPadding', 'backdrop', 'isParent', 'closeByBackdrop', 'render', 'opacity'
			]); // 参数
			if (windowUrl) {
				url = windowUrl;
				path = url;
			} else {
				url = id[0] ? id[0].srcUrl : id.srcUrl;
				path = contextPath + url + ((url.indexOf("?") > -1) ? "" : "?") + (p + (parameters_ ? ("&" + parameters_) : ""));
				if (window.location.href.indexOf("isPublish=true") != -1) {
					path += "&isPublish=true";
				}
			}

			var mtOpenTab = params.mtOpenTab === true ? true : false;
			if (mtOpenTab) { //选项卡打开
				params.path = path;
				pageBtFunc.mtOpenTab(id, params, args);
			} else if (params.render) {
				location.href = path;
			} else if (!params.singlePage) {
				window.open(path);
			} else {
				var iTop = (window.screen.availHeight - 30 - eval((params.height + "").replace('px', ''))) / 2; // 获得窗口的垂直位置;
				var iLeft = (window.screen.availWidth - 10 - eval((params.width + "").replace('px', ''))) / 2; // 获得窗口的水平位置;
				var width = eval(params.width) || 650;
				var height = eval(params.height) || 450;
				if (params.isMax) {
					width = $("body").width() - 24;
					height = $("body").height() - 93;
				}
				if (params.model) {
					window.open(path, params.title, "width=" + params.width + ",height=" + params.height + ",top=" + iTop + ",left=" + iLeft + ", modal=true,status=no,scrollbars=yes");
				} else if (params.isParent && top && top.$.fancybox) {
					top.$.fancybox.open({
						href: path,
						width: width,
						height: height,
						type: 'iframe',
						modal: false,
						closeClick: true,
						openEffect: 'none',
						padding: 5,
						autoSize: false,
						autoHeight: false,
						autoWidth: false,
						helpers: {
							title: (params.title == null ? "窗口" : params.title)
						}
					});
					//parent.$.fancybox.open("<iframe onload=\"$(this).contents().find('body').css('overflow-y', 'auto');\" width='100%' height='" + height + "' src='" + path + "' frameborder='no' scrolling='yes'></iframe>");
				} else {
					var dialog = BootstrapDialog.show({
						title: (params.title == null ? "窗口" : params.title),
						draggable: params.draggable === false ? false : true,
						message: function(dialog) {
							//dialog.$modal.removeAttr('tabindex');
							var pageToLoad = dialog.getData('pageToLoad');
							var frame
							if (windowUrl) {
								frame = "<iframe width='100%' height='" + height + "' src='" + pageToLoad + "' frameborder='no' scrolling='yes'></iframe>";
							} else {
								frame = "<iframe onload=\"$(this).contents().find('body').css('overflow-y', 'auto');\" width='100%' height='" + height + "' src='" + pageToLoad + "' frameborder='no' scrolling='yes'></iframe>";
							}
							return $(frame);
						},
						closeByBackdrop: params.closeByBackdrop === false ? false : true,
						data: {
							'pageToLoad': path
						},
						css: {
							width: width + 'px',
							height: (height + 6) + 'px'
						},
						onhide: function(dialog) {
							var iframe = dialog.$modal.find("iframe:first")[0];
							if (iframe && !windowUrl) {
								var iframe_window = iframe.contentWindow;
								if (iframe_window && $(iframe_window.document.body)[0]) {
									var onbeforeunloadFunc = $(iframe_window.document.body).data("onbeforeunload");
									if ($.isFunction(onbeforeunloadFunc)) {
										onbeforeunloadFunc();
										return;
									}
								}
								var onbeforeunloadFunc = $(document.body).data("onbeforeunload");
								if ($.isFunction(onbeforeunloadFunc)) {
									onbeforeunloadFunc();
									return;
								}
							}
						}
					});

					if (params.backdrop != null && !params.backdrop) {
						dialog.$modal.data('bs.modal').$backdrop.hide();
					}

					if (params.opacity != null) {
						dialog.getModalContent().css("opacity", params.opacity)
					}

					
					dialog.getModalHeader().find(".bootstrap-dialog-header .bootstrap-dialog-close-button").remove();
					// dialog.getModalHeader().find(".bootstrap-dialog-header").append('<button type="button" class="btn closeBtn" style=""><i class="fa fa-remove"></i></button>');
					dialog.getModalContent().prepend('<button type="button" class="btn closeBtn" style=""><i class="fa fa-remove"></i></button>');

					if(params.closeBtnColor){
						dialog.getModalContent().find(".closeBtn").css("color",params.closeBtnColor);
					}
					if(params.closeBtnSize){
						dialog.getModalContent().find(".closeBtn").css("font-size",params.closeBtnSize);	
					}

					dialog.getModalContent().find(".closeBtn").click(function(event){
						dialog.close();
					})

					if (params.headerPadding != null) {
						var headerPaddingStr = params.headerPadding + 'px ' + params.headerPadding + 'px ' + params.headerPadding + 'px ' + params.headerPadding + 'px';
						var headerBackgroundColor = params.headerBackgroundColor || "";
						var headerFontSize = params.headerFontSize || "";
						dialog.getModalHeader().css({
							padding: headerPaddingStr,
							"background-color": headerBackgroundColor,
							// "font-size":headerFontSize
						});
						var headerFontColor = params.headerFontColor || "";
						dialog.getModalHeader().find(".bootstrap-dialog-title").css("color", headerFontColor);
						dialog.getModalHeader().find(".bootstrap-dialog-title").css("font-size", headerFontSize);

					}

					//dialog.$modal.removeAttr('tabindex');
					if (params.isMax) {
						dialog.$modal.css({
							//display: 'table',
							padding: '0px 10px'
						});
						dialog.$modalDialog.css({
							margin: '0px 10px'
						});
					}
					/* 
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
					}*/
				}
			}
		}
	},
	getActoidMessage: function(rule, args) {
		$.each(args, function(i, arg) {
			window.actoid = arg;
		});
	},
	openWindowFunc: function(id, params, args) {
		if (params && params.url) {
			params.render = params.isturn;
			pageBtFunc.newWindow(null, params, null, params.url);
			// if(params.isturn){
			// 	location.href = params.url;
			// }else{
			// 	window.open(params.url);	
			// }
		}
	},
	pageClose: function(rule, args) {
		var that = pubsub.getThat(rule);
		if (that.top.location.href == that.location.href) {
			// var onbeforeunloadFunc = $('body').data("onbeforeunload");
			// if ($.isFunction(onbeforeunloadFunc)) {
			// 	onbeforeunloadFunc();
			// }

			try {
				that.close();
			} catch (e) {

			}

		} else {
			var openWindows = that.parent.$("[role=dialog]");
			var onbeforeunloadFunc = $('body').data("onbeforeunload");
			if ($.isFunction(onbeforeunloadFunc)) {
				onbeforeunloadFunc();
			}
			openWindows.each(function(i, k) {
				var id = $(this).attr("id");
				if(id){
					that.parent.BootstrapDialog.getDialog($(this).attr("id")).close();	
				}
			});
			that.parent.$("input[type=text]:visible:enabled:not([readonly]):not([class*=date]):first").focus();
		}

		/**
		 * 关闭流程审批界面
		 */
		try {
			var this_ = that.opener || that.parent;
			this_.closeWindow && (this_.closeWindow());
		} catch (e) {

		}
	},
	grefresh: function(rule, args) {
		var that = pubsub.getThat(rule);
		that.location.reload();
	},

	/**
	 * 微服务
	 */
	mt_service: function(rule, params, args) {
		var ruleOne, thatWin = window;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		} else if ($.isArray(rule)) {
			ruleOne = rule[0];
		}

		if (!ruleOne.useId) {
			alert("服务地址不存在!");
			pubsub.execChilds(rule);
			return false;
		}

		/**
		 * 参数组合
		 */
		var inParams = {
				page: '',
				pageSize: ''
			},
			data = {},
			paramsEmpty = true;
		$.each(params, function(k, v) {
			!(k in inParams) && (data[k] = v) && (paramsEmpty = false);
		});
		data = {
			params: !paramsEmpty ? JSON.stringify(data) : null
		}
		$.each(inParams, function(k, v) {
			params[k] && (data[k] = params[k]);
		});

		MyAjax({
			operation: '执行微服务',
			url: ruleOne.useId,
			type: "post",
			data: data,
			dataType: 'jsonp',
			success: function(msg) {
				SearchCallback(thatWin, params, msg, rule, ruleOne);
			}
		});
	},

	/**
	 * 保存操作
	 * 
	 * @param rule
	 * @param args
	 */
	mtSave: function(rule, args) {
		//保存验证
		/*var $valiSave = $("[valiSave=true]");
		if(!$valiSave.length && !$valiSave.valid()){
			return;
		}*/
		var that = pubsub.getThat(rule);
		if (!that.getFormValiate()) {
			return false;
		}
		var fn = null;
		var msg = $(that.document.body).data("saveMsg");
		if (msg) {


			$.each(msg, function(i, btn) {
				if (btn && btn.save) {
					btn.save(fn, args);
				}
			});

			var childs = rule.childs;
			if (childs) {
				that.savecount = 0;
				var interval = setInterval(function() {
					if (that.savecount == msg.length) {
						that.clearInterval(interval);
						pubsub.execChilds(rule);
					}
				}, 500);
			}

		}
	},
	/**
	 * sqlLite文件下载
	 * @param  {[type]} rule [description]
	 * @param  {[type]} args [description]
	 * @return {[type]}      [description]
	 */
	downSqlLite: function(rule, params, args) {
		var ruleOne;
		var thatWin = pubsub.getThat(rule);
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		// var p = {
		// 	useId: ruleOne['useId'],
		// 	params: JSON.stringify(params),
		// 	type: ruleOne['oid']
		// }

		var url = contextPath + '/mx/form/wdatasetSqllite/getById';
		MyAjax({
			operation: 'sqlLite文件下载',
			url: url,
			type: "post",
			data: {
				id: ruleOne['useId']
			},
			async: false,
			success: function(msg) {
				if (msg) {
					var sqlRule = JSON.parse(msg);
					var dataSetIds = "";
					if (sqlRule.ruleJson) {
						var ruleJson = JSON.parse(sqlRule.ruleJson);
						$.each(ruleJson, function(i, item) {
							if (dataSetIds) {
								dataSetIds += ',';
							}
							dataSetIds += item.dataSetId;
						});
					}


					var downloadUrl = contextPath + '/website/public/dataset/export/sqlite?1=1';


					// var downloadUrl = '/glaf/website/public/dataset/export/sqlite';
					if (dataSetIds) {
						downloadUrl = downloadUrl + "&dataSetIds=" + dataSetIds;
					}
					if (params) {
						downloadUrl = downloadUrl + "&params=" + JSON.stringify(params);
					}

					window.open(downloadUrl);
					// location.href = downloadUrl;
					return;

					// FormData 对象
					// 	var form = new FormData();
					// 	form.append("dataSetIds", dataSetIds);
					// 	form.append("params",params);

					// var xhr = new XMLHttpRequest();
					// xhr.open("post", downloadUrl, true);		//写入请求内容

					// xhr.send(form);
					// console.log(dataSetIds);
				}
			},
			error: function() {
				alert("异常错误,请稍后再试.");
			}
		})

	},
	/**
	 * 警示操作
	 * 
	 * @param rule
	 * @param args
	 */
	mtAlert: function(rule, args) {
		var r, v;
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param];
			alert(v);
		}
	},
	/**
	 * 加载效果
	 * 
	 * @param rule
	 * @param args
	 */
	mtLoading: function(rule, args) {
		var html = '<div class="loading-message "><div class="block-spinner-bar"><div class="bounce1"></div><div class="bounce2"></div><div class="bounce3"></div></div></div>';
		$.blockUI({
			message: html,
			baseZ: 1000,
			css: {
				border: '0',
				padding: '0',
				backgroundColor: 'none'
			},
			overlayCSS: {
				backgroundColor: '#555',
				opacity: 0.1,
				cursor: 'wait'
			}
		});
	},
	/**
	 * 取消加载效果
	 * 
	 * @param rule
	 * @param args
	 */
	cancelLoading: function(rule, args) {
		$.unblockUI();
	},
	/**
	 * 登录操作
	 * 
	 * @param rule
	 * @param args
	 */
	mtLogin: function(rule, params, args) {
		handleLogin(rule, params, args);
	},
	/**
	 * 流程提交
	 * 
	 * @param rule
	 * @param args
	 */
	mtSubmit: function(rule, args) {
		if (window.mtxx && window.mtxx.params) {
			if (confirm("你确定提交吗?")) {
				var pageId = mtxx.params.id,
					p = $.extend(true, {}, args, {
						approve: true
					}),
					id = $("." + pageId).attr("idValue");
				var data = {
					pageId: pageId,
				};
				data.flowParams = JSON.stringify(p);
				data[pageId] = id;
				MyAjax({
					operation: '流程提交',
					url: contextPath + "/mx/form/workflow/defined/submit",
					type: "POST",
					dataType: "JSON",
					data: data,
					success: function(ret) {
						alert("操作成功!");
					}
				});
			}
		}
	},

	/**
	 * 流程签收
	 * 
	 * @param rule
	 * @param args
	 */
	mtAssign0: function(rule, args) {
		FlowProcess.mtAssign0(rule, args);
	},
	/**
	 * 流程提交(流程已经启动的才可以)
	 * 
	 * @param rule
	 * @param args
	 */
	mtSubmit0: function(rule, args) {
		FlowProcess.mtSubmit0(rule, args);
	},
	/**
	 * 流程退回(流程已经启动的才可以)
	 * 
	 * @param rule
	 * @param args
	 */
	mtBack0: function(rule, args) {
		args.approve = false;
		FlowProcess.mtSubmit0(rule, args);
	},
	/**
	 * 流程撤回(作废)
	 */
	mtReject: function(rule, args) {
		FlowProcess.reject(args);
	},
	/**
	 * 流程撤回
	 */
	mtReject0: function(rule, args) {
		FlowProcess.reject0(rule, args);
	},
	/**
	 * 流程终止(作废)
	 */
	mtCancel: function(rule, args) {
		FlowProcess.cancel(args);
	},
	/**
	 * 流程终止
	 */
	mtCancel0: function(rule, args) {
		FlowProcess.cancel0(rule, args);
	},
	/**
	 * 挂起(作废)
	 */
	mtStop: function(rule, args) {
		FlowProcess.stop(args);
	},
	/**
	 * 挂起
	 */
	mtStop0: function(rule, args) {
		FlowProcess.stop0(rule, args);
	},
	/**
	 * 激活(作废)
	 */
	mtActive: function(rule, args) {
		FlowProcess.active(args);
	},
	/**
	 * 激活
	 */
	mtActive0: function(rule, args) {
		FlowProcess.active0(rule, args);
	},
	/**
	 * 下发
	 */
	sendDown: function(rule, params, args) {
		var p = {},
			sendData = {};
		for (var key in params) {
			if (key.indexOf('hmtdusd-') != -1) {
				sendData[key.replace('hmtdusd-', '')] = params[key];
			} else {
				p[key] = params[key];
			}
		}
		p["sendData"] = JSON.stringify(sendData);
		MyAjax({
			operation: '下发',
			url: contextPath + "/mx/form/data/sendDown2",
			type: "post",
			data: p,
			async: false,
			success: function(msg) {
				if (msg) {
					msg = JSON.parse(msg);
					alert(msg.message);
					pubsub.execChilds(rule);
				}
			},
			error: function() {
				alert("异常错误,请稍后再试.");
			}
		})
	},
	/**
	 * 整表下发
	 */
	sendAllDown: function(rule, params, args) {
		MyAjax({
			operation: '整表下发',
			url: contextPath + "/mx/form/data/sendDown",
			type: "post",
			data: params,
			async: false,
			success: function(msg) {
				if (msg) {
					msg = JSON.parse(msg);
					alert(msg.message);
					pubsub.execChilds(rule);
				}
			},
			error: function() {
				alert("异常错误,请稍后再试.");
			}
		})
	},
	/**
	 * 表达式运算
	 */
	exprCalc: function(rule, params, args) {
		if ($.isArray(rule)) {
			var thatWin = /*pubsub.getThat(rule, true)*/ window;
			var p = params || {},
				ruleOne = rule[0],
				callback = ruleOne.callback,
				elongateStockIndex = pubsub.elongateStockIndex;
			p.mtUserId = ruleOne['useId'];
			MyAjax({
				operation: '表达式运算',
				url: contextPath + "/mx/expr/calculateByPoi",
				type: "post",
				data: {
					params: JSON.stringify(p)
				},
				async: true,
				success: function(msg) {
					if (msg) {
						pubsub.elongateStockIndex = elongateStockIndex;
						msg = JSON.parse(msg);
						thatWin.callbackParam = thatWin.callbackParam || {};
						$.each(callback, function(i, k) {
							thatWin.callbackParam[k.param] = msg[k.columnName];
						})
						pubsub.execChilds(rule);
						pubsub.elongateStockIndex = null;
					}
					console.log(msg);
				},
				error: function() {
					alert("异常错误,请稍后再试.");
				}
			})
		}
		//console.log(rule);
		//console.log(params);
	},
	/*
	 * 逐级汇总事件
	 */
	collect: function(rule, params, args) {
		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}

		function collectInnerFunc() {
			//window.open("");
			var databaseId = params["databaseId"] || '';
			var link = contextPath + "/mx/form/treeTableAggregate/executeSpec?treeTableAggregateId=" + ruleOne['useId'] + "&databaseId=" + databaseId;
			jQuery.ajax({
				type: "POST",
				url: link,
				data: params,
				dataType: 'json',
				error: function(data) {
					alert('服务器处理错误！');
				},
				success: function(data) {
					if (data != null && data.message != null) {
						//alert(data.message);
						if (!params["isAlt"]) {
							alert('操作成功完成！');
						}
					} else {
						if (!params["isAlt"]) {
							alert('操作成功完成！');
						}
					}
					pubsub.execChilds(rule);
				}
			});
		}
		if (!params["isAlt"]) {
			if (confirm("确定执行数据汇总吗？")) {
				collectInnerFunc();
			}
		} else {
			collectInnerFunc();
		}
	},
	/*
	 * CRUD事件
	 */
	mtcrud: function(rule, params, args) {
		//console.log(rule);
		var ruleOne;
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		var p = {
			useId: ruleOne['useId'],
			params: JSON.stringify(params),
			type: ruleOne['oid']
		}
		if (p.type == 'del' && !params["isDelAlt"]) {
			confirm2("是否确认删除", function(ok) {
				if (ok) {
					MyAjax({
						operation: 'CRUD事件',
						url: contextPath + "/mx/form/data/mtcrud",
						type: "post",
						data: p,
						async: false,
						success: function(msg) {
							if (msg && (msg = JSON.parse(msg)) && (!msg.statusCode || msg.statusCode != '500')) {
								//window.callbackParam = window.callbackParam || {};
								thatWin.callbackParam = thatWin.callbackParam || {};
								$.each(ruleOne.callback, function(i, k) {
									thatWin.callbackParam[k.param] = msg[k.columnName];
									//window.callbackParam[k.param] = msg[k.columnName];
								})
								if (!params["isAlt"]) {
									alert('操作成功完成！');
								}
							} else {
								alert('异常错误,请稍后再试.');
							}
							pubsub.execChilds(rule);
						},
						error: function() {
							alert("异常错误,请稍后再试.");
						}
					})
				}
			});
		} else {
			MyAjax({
				operation: 'CRUD事件',
				url: contextPath + "/mx/form/data/mtcrud",
				type: "post",
				data: p,
				async: false,
				success: function(msg) {
					if (msg && (msg = JSON.parse(msg)) && !msg.statusCode) {
						//window.callbackParam = window.callbackParam || {};
						thatWin.callbackParam = thatWin.callbackParam || {};
						$.each(ruleOne.callback, function(i, k) {
							thatWin.callbackParam[k.param] = msg[k.columnName];
							//window.callbackParam[k.param] = msg[k.columnName];
						})
						if (!params["isAlt"]) {
							alert('操作成功完成！');
						}
					} else {
						alert('异常错误,请稍后再试.');
					}
					pubsub.execChilds(rule);
				},
				error: function() {
					alert("异常错误,请稍后再试.");
				}
			})
		}
	},

	/*
	 * 查询服务
	 */
	mtsearch: function(rule, params, args) {
		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		} else if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		MyAjax({
			operation: '查询服务',
			url: contextPath + "/mx/form/data/mtsearch",
			type: "post",
			data: {
				did: ruleOne['useId'],
				params: JSON.stringify(params)
			},
			dataType: "json",
			async: false,
			success: function(msg) {
				thatWin.callbackParam = thatWin.callbackParam || {};
				if (msg.total) {
					var retData = msg.data,
						retDataOne = retData[0],
						initParam = {};
					$.each(ruleOne.callback, function(i, k) {
						thatWin.callbackParam[k.param] = retDataOne[k.columnName];
						if (k.dataId && k.dataId.indexOf("ary") == 0) {
							!initParam[k.dataId] && (thatWin.callbackParam[k.dataId] = []) && (initParam[k.dataId] = true);
							//callbackParam[k.dataId] || (callbackParam[k.dataId] = []);
							$.each(retData, function(index, val) {
								thatWin.callbackParam[k.dataId][index] || (thatWin.callbackParam[k.dataId][index] = {});
								thatWin.callbackParam[k.dataId][index][k.param] = val[k.columnName];
							})
						} else if (k.dataId && k.dataId.indexOf("obj") == 0) {
							!initParam[k.dataId] && (thatWin.callbackParam[k.dataId] = {}) && (initParam[k.dataId] = true);
							//callbackParam[k.dataId] || (callbackParam[k.dataId] = {});
							thatWin.callbackParam[k.dataId][k.param] = retDataOne[k.columnName];
						}
					})
					if (!params["isAlt"]) {
						alert('操作成功完成！');
					}
				} else {
					$.each(ruleOne.callback, function(i, k) {
						thatWin.callbackParam[k.param] = "";
						if (k.dataId && k.dataId.indexOf("ary") == 0) {
							!initParam[k.dataId] && (thatWin.callbackParam[k.dataId] = []) && (initParam[k.dataId] = true);
							$.each(retData, function(index, val) {
								thatWin.callbackParam[k.dataId][index] || (thatWin.callbackParam[k.dataId][index] = {});
								thatWin.callbackParam[k.dataId][index][k.param] = "";
							})
						} else if (k.dataId && k.dataId.indexOf("obj") == 0) {
							!initParam[k.dataId] && (thatWin.callbackParam[k.dataId] = {}) && (initParam[k.dataId] = true);
							thatWin.callbackParam[k.dataId][k.param] = "";
						}
					})
					if (!params["isAlt"]) {
						alert('没有查询结果.');
					}
				}
				pubsub.execChilds(rule);
			},
			error: function() {
				alert("异常错误,请稍后再试.");
			}
		})
	},

	/*
	 * 转换服务事件
	 */
	convertService: function(rule, params, args) {
		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		var p = {
			useId: ruleOne['useId'],
			params: JSON.stringify(params),
			type: ruleOne['oid']
		};
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		MyAjax({
			operation: '转换服务事件',
			url: contextPath + "/mx/form/data/convertService",
			type: "post",
			data: p,
			async: false,
			success: function(msg) {
				if (msg && (msg = JSON.parse(msg)) && !msg.statusCode) {
					thatWin.callbackParam = thatWin.callbackParam || {};
					$.each(ruleOne.callback, function(i, k) {
						thatWin.callbackParam[k.param] = msg[k.columnName];
					})
					if (!params["isAlt"]) {
						alert('操作成功完成！');
					}
				} else {
					alert('异常错误,请稍后再试.');
				}
				pubsub.execChilds(rule);
			},
			error: function() {
				alert("异常错误,请稍后再试.");
			}
		})
	},
	/**
	 * 页面打印服务
	 * @return {[type]} [description]
	 */
	mtPagePrint: function(rule, params, args) {
		var $inDom = pubsub.getJQObj(rule, true); //输入控件，获取输入控件

		if ($inDom && $inDom[0]) {
			if (!$inDom.printArea) {
				var $style = $('<script type="text/javascript" src="' + contextPath + '/scripts/jquery.PrintArea.js"></script>');
				$("body").append($style);
			}
			$inDom.printArea();
		} else {
			window.print();
		}

	},
	/**
	 * cell 打印服务
	 * @return {[type]} [description]
	 */
	mtCellPrint: function(rule, params, args) {
		var $in = pubsub.getJQObj(rule, true),
			spread = $in.data("spread");
		spread.print();
	},
	/**
	 * cell 导出excel
	 */
	mtCellExport: function(rule, params, args) {
		var that = pubsub.getThat(rule) || window;
		params = ($.isArray(params) ? params[0] : params) || {};
		var p = params["exprotValue"],
			exportName = params["exprotName"] || "",
			urlParams = "",
			ruleOne = rule;
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		var callback = ruleOne.callback,
			mtUserId = ruleOne['useId'];
		if (mtUserId) {
			urlParams = "?mtUserId=" + mtUserId;
			p = JSON.stringify(params);
		}
		var actionUrl = contextPath + "/mx/form/data/spread2xls" + urlParams;
		var iframe = that.$("#__cell_export_iframe__").get(0) ? that.$("#__cell_export_iframe__") : that.$("<iframe>", {
				id: "__cell_export_iframe__",
				name: "__cell_export_iframe__",
				style: "display:none"
			}).appendTo('body'),
			form = that.$("#__cell_export_form__").get(0) ? that.$("#__cell_export_form__").attr("action", actionUrl) : that.$("<form>", {
				id: "__cell_export_form__",
				action: actionUrl,
				method: "post",
				target: "__cell_export_iframe__",
				style: "display:none"
			}).appendTo('body'),
			exprotValue = that.$("#__cell_export_value__").get(0) ? that.$("#__cell_export_value__").val(p) : that.$("<input>", {
				id: "__cell_export_value__",
				name: "exprotValue",
				type: "text",
				value: p
			}).appendTo(form),
			exprotName = that.$("#__cell_export_name__").get(0) ? that.$("#__cell_export_name__").val(exportName) : that.$("<input>", {
				id: "__cell_export_name__",
				name: "exprotName",
				type: "text",
				value: exportName
			}).appendTo(form);
		form.submit();
	},

	/*
	 * 短信服务
	 */
	nodeMessage: function(rule, params, args) {
		MyAjax({
			operation: '短信服务',
			url: contextPath + "/mx/form/sendMessage",
			type: "post",
			data: {
				params: JSON.stringify(params)
			},
			dataType: "json",
			async: false,
			success: function(ret) {
				if (ret && ret.message) {
					alert(ret.message);
				}
				pubsub.execChilds(rule);
			},
			error: function() {
				alert("异常错误,请稍后再试.");
			}
		})
	},
	/**
	 * 打开选项卡(主页)
	 */
	mtOpenTab: function(rule, params, args) {
		var TABURL = params.TABURL,
			search = "" //
			,
			path = params.path;
		if (!path) {
			if (!TABURL) {
				alert("地址不能为空!");
				return false;
			}
			var use = {
					TABURL: '',
					TABID: '',
					TABNAME: ''
				},
				others = [];
			$.each(params, function(k, v) {
				!(k in use) && (others.push(k + "=" + v));
			});
			search = others.join("&");
			TABURL = TABURL.indexOf("?") >= 0 ? //
				(TABURL + "&" + search) : TABURL + "?" + search;
		} else {
			TABURL = path;
		}
		var func = parent.addTabEx || parent.parent.addTabEx;
		if (func) {
			func(params.TABID || new Date().getTime(), params.TABNAME, TABURL);
		} else {
			window.open(TABURL);
		}
		if(window.pageParameters && window.pageParameters.id){
			var __windows = "__windows";
			!window.top[__windows] && (window.top[__windows] = {});
			window.top[__windows][window.pageParameters.id] = window;
		}
	},
	/**
	 * 关闭选项卡(主页)
	 */
	mtCloseTab: function(rule, params, args) {
		window.ClosePageTab(params.TABID);
	},
	/**
	 * 获取手机号码
	 * @return {[type]} [description]
	 */
	callTelNo: function(rule, params, args) {
		if (window.WebViewJavascriptBridge) {
			var thatWin = /*pubsub.getThat(rule, true)*/ window;
			var ruleOne;
			if ($.isPlainObject(rule)) {
				ruleOne = rule;
			}
			if ($.isArray(rule)) {
				ruleOne = rule[0];
			}
			window.WebViewJavascriptBridge.callHandler('callTelNo', {}, function(responseData) {
				thatWin.callbackParam || (thatWin.callbackParam = {});
				window.callbackParam || (window.callbackParam = {});
				$.each(ruleOne.callback, function(i, k) {
					window.callbackParam[k.param] = thatWin.callbackParam[k.param] = responseData;
				})
				pubsub.execChilds(rule);
			});
		} else {
			console.error("请在手机端使用获取手机号码功能");
		}
	},
	/**
	 * 获取gps坐标位置
	 * @param  {[type]} rule   [description]
	 * @param  {[type]} params [description]
	 * @param  {[type]} args   [description]
	 * @return {[type]}        [description]
	 */
	callGps: function(rule, params, args) {
		if (window.WebViewJavascriptBridge) {
			var thatWin = /*pubsub.getThat(rule, true)*/ window;
			var ruleOne;
			if ($.isPlainObject(rule)) {
				ruleOne = rule;
			}
			if ($.isArray(rule)) {
				ruleOne = rule[0];
			}
			window.WebViewJavascriptBridge.callHandler('callGps', {}, function(responseData) {
				thatWin.callbackParam || (thatWin.callbackParam = {});
				window.callbackParam || (window.callbackParam = {});
				$.each(ruleOne.callback, function(i, k) {
					window.callbackParam[k.param] = thatWin.callbackParam[k.param] = responseData;
				})
				pubsub.execChilds(rule);
			});
		} else {
			console.error("请在手机端使用获取GPS坐标功能");
		}
	},
	/**
	 * 扫描二维码
	 * @return {[type]} [description]
	 */
	callScan: function(rule, params, args) {
		if (window.WebViewJavascriptBridge) {
			window.WebViewJavascriptBridge.callHandler('callScan', !!(params && params.hasCallback), function(responseData) {});
		} else {
			console.error("请在手机端使用扫描二维码功能");
		}
	},
	/**
	 * 手机注销
	 * @return {[type]} [description]
	 */
	callLogout: function(rule, params, args) {
		if (window.WebViewJavascriptBridge) {
			window.WebViewJavascriptBridge.callHandler('logout', {}, function(responseData) {});
		} else {
			console.error("请在手机端使用注销退出功能");
		}
	},
	/**
	 * 手机查看tif
	 * @return {[type]} [description]
	 */
	callTif: function(rule, params, args) {
		if (window.WebViewJavascriptBridge) {
			var tid = params["tifId"],
				tn = params["tnfield"],
				ct = params["ctfield"],
				id = params["idfield"] || "id",
				databaseId = params["databaseId"],
				url = "/mx/form/imageUpload?method=downloadById&id=" + tid;
			tn && ct && (url = "/mx/form/imageUpload?method=downloadByTab&tid=" + tid + "&tn=" + tn + "&ct=" + ct + "&id=" + id + "&databaseId=" + databaseId);
			window.WebViewJavascriptBridge.callHandler('callTif', {
				url: url
			}, function(responseData) {});
		} else {
			console.error("请在手机端使用查看TIF功能");
		}
	},
	/**
	 * [callWpfSoaTif 手机查看wpfsoatif文件]
	 * @param  {[type]} rule   [description]
	 * @param  {[type]} params [description]
	 * @param  {[type]} args   [description]
	 * @return {[type]}        [description]
	 */
	callWpfSoaTif: function(rule, params, args) {
		if (window.WebViewJavascriptBridge) {
			pageBtFunc.mtLoading();
			MyAjax({
				operation: '手机查看wpfsoatif文件',
				url: contextPath + "/mx/wpf/getFiledotByIdForSoa",
				type: "post",
				data: params,
				dataType: "json",
				success: function(ret) {
					pageBtFunc.cancelLoading();
					if (ret && ret.code == 200) {
						window.WebViewJavascriptBridge.callHandler('callWpfSoaTif', {
							url: ret.data
						}, function(responseData) {});
					} else {
						alert(ret.message);
					}
				},
				error: function() {
					pageBtFunc.cancelLoading();
					alert("异常错误,请稍后再试.");
				}
			});
		} else {
			console.error("请在手机端使用查看TIF功能");
		}
	},
	/**
	 * 拨打电话
	 * @param  {[type]} rule   [description]
	 * @param  {[type]} params [description]
	 * @param  {[type]} args   [description]
	 * @return {[type]}        [description]
	 */
	callTelPhone: function(rule, params, args) {
		if (window.WebViewJavascriptBridge) {
			var tid = params["tifId"];
			window.WebViewJavascriptBridge.callHandler('callTelPhone', params["telPhone"], function(responseData) {});
		} else {
			console.error("请在手机端使用拨打电话功能");
		}
	},
	/**
	 * 手机登录
	 * @return {[type]} [description]
	 */
	callLogin: function(rule, params, args) {
		if (window.WebViewJavascriptBridge) {
			if (params.rember && params.rember == 1) {
				//保存用户cookie
				if (!$.cookie) {
					var $style = $('<script type="text/javascript" src="' + contextPath + '/webfile/js/jquery.cookie.js"></script>');
					$("body").append($style);
				}
				//保存单个用户信息
				var cookieopts = {};
				if (params.expires) {
					cookieopts.expires = params.expires;
				}
				$.cookie("userInfo", params['x'], cookieopts);
			}

			window.WebViewJavascriptBridge.callHandler('goHome', {
				"x": params['x'],
				"y": params['y'],
				"z": params['z'] || "",
				"k": params['k'] || contextPath,
				"a": params['a'],
				"r": params['r']
			}, function(responseData) {});
		} else {
			console.error("请在手机端使用手机登录功能");
		}
	},
	/**
	 * 手机查看萤石云视频
	 * @return {[type]} [description]
	 */
	callYsVideo: function(rule, params, args) {
		if (window.WebViewJavascriptBridge) {
			MyAjax({
				operation: '手机查看萤石云视频',
				url: contextPath + "/mx/form/videoData/videoMobileData",
				type: "post",
				data: params,
				async: false,
				success: function(ret) {
					if (ret) {
						window.WebViewJavascriptBridge.callHandler('callPlayVideo', {
							"param": ret
						}, function(responseData) {});
					} else {
						alert("参数传递错误");
					}
				},
				error: function() {
					alert("异常错误,请稍后再试.");
				}
			})
		} else {
			console.error("请在手机端使用此功能");
		}
	},
	/**
	 * 批量任务调度
	 */
	taskTable: function(rule, params, args) {
		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		var p = {
			useId: ruleOne['useId'],
			params: JSON.stringify(params)
		}
		MyAjax({
			operation: '组合任务表调度',
			url: contextPath + "/mx/form/data/taskTable",
			type: "post",
			data: p,
			dataType: "json",
			async: false,
			success: function(ret) {
				if (ret && ret.message) {
					if (!params["isAlt"]) {
						alert(ret.message);
					}
				}
				pubsub.execChilds(rule);
			},
			error: function() {
				alert("异常错误,请稍后再试.");
			}
		});
	},
	/**
	 * 组合表调度
	 */
	tableCombination: function(rule, params, args) {
		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		var p = {
			useId: ruleOne['useId'],
			params: JSON.stringify(params)
		}
		MyAjax({
			operation: '组合任务表调度',
			url: contextPath + "/mx/form/data/tableCombination",
			type: "post",
			data: p,
			dataType: "json",
			async: false,
			success: function(ret) {
				if (ret && ret.message) {
					if (!params["isAlt"]) {
						alert(ret.message);
					}
				}
				pubsub.execChilds(rule);
			},
			error: function() {
				alert("异常错误,请稍后再试.");
			}
		});
	},
	/**
	 * wpf推送通知
	 * @param  {[type]} rule   [description]
	 * @param  {[type]} params [description]
	 * @param  {[type]} args   [description]
	 * @return {[type]}        [description]
	 */
	wpfMobileNotification: function(rule, params, args) {
		MyAjax({
			operation: 'wpf推送通知',
			url: contextPath + "/mx/jiguang/push",
			type: "post",
			data: params,
			dataType: "json",
			async: false,
			success: function(ret) {
				if (ret && ret.message) {}
			},
			error: function() {
				alert("推送失败.");
			}
		});
	},
	/**
	 * 设置定时循环任务
	 * @param {[type]} rule   [description]
	 * @param {[type]} params [传经来的参数]
	 * @param {[type]} args   [description]
	 */
	setCyclicTask: function(rule, params, args) {
		var thatWin = /*pubsub.getThat(rule, true)*/ window;

		thatWin.createCyclicTask = function() {
			pubsub.execChilds(rule);
		}

		var cyclicTask = thatWin.setInterval("createCyclicTask()", params.delayTime || 300);

		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		} else if ($.isArray(rule)) {
			ruleOne = rule[0];
		}

		thatWin.callbackParam = thatWin.callbackParam || {};
		$.each(ruleOne.callback, function(i, k) {
			thatWin.callbackParam[k.param] = cyclicTask;
		})
	},

	/**
	 * 清除定时循环任务
	 * @param  {[type]} rule   [description]
	 * @param  {[type]} params [description]
	 * @param  {[type]} args   [description]
	 * @return {[type]}        [description]
	 */
	clearCyclicTask: function(rule, params, args) {
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		var intervalId = params.cyclicTask;
		if (intervalId)
			thatWin.clearInterval(intervalId);
	},
	/**
	 * 验证事件
	 * @param  {[type]} rule   [description]
	 * @param  {[type]} params [description]
	 * @param  {[type]} args   [description]
	 * @return {[type]}        [description]
	 */
	mtVali: function(rule, params, args) {
		var thatWin = pubsub.getThat(rule),
			ruleOne,
			$valiSave = thatWin.$("[valiSave=true]"),
			bool = true;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		if ($valiSave.length && !$valiSave.valid()) {
			bool = false;
		}

		var pl = (ruleOne["insExp"] - 0);

		for (; pl > 0; pl--) {
			thatWin = thatWin.opener || thatWin.parent;
		}

		thatWin.callbackParam || (thatWin.callbackParam = {});
		window.callbackParam || (window.callbackParam = {});
		$.each(ruleOne.callback, function(i, k) {
			window.callbackParam[k.param] = thatWin.callbackParam[k.param] = bool;
		})
		pubsub.execChilds(rule);
	},
	/*
	 * 表列转换
	 */
	tableTransform: function(rule, params, args) {
		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		var databaseId = params["databaseId"] || '';
		var link = contextPath + "/mx/form/tableTransform/execute?tableName=" + ruleOne['useId'] + "&databaseId=" + databaseId;
		MyAjax({
			type: "POST",
			url: link,
			data: params,
			dataType: 'json',
			error: function(data) {
				alert('服务器处理错误！');
			},
			success: function(data) {
				if (data != null && data.message != null) {
					if (!params["isAlt"]) {
						alert('操作成功完成！');
					}
				} else {
					if (!params["isAlt"]) {
						alert('操作成功完成！');
					}
				}
				pubsub.execChilds(rule);
			}
		});
	},
	mtConfirm: function(rule, params, args) {
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		var ruleOne;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}
		var types = [BootstrapDialog.TYPE_DEFAULT,
				BootstrapDialog.TYPE_INFO,
				BootstrapDialog.TYPE_PRIMARY,
				BootstrapDialog.TYPE_SUCCESS,
				BootstrapDialog.TYPE_WARNING,
				BootstrapDialog.TYPE_DANGER
			],
			btnTypes = [
				"btn-default",
				"btn-info",
				"btn-primary",
				"btn-success",
				"btn-warning",
				"btn-danger",
			],
			titleType,
			btnOKClass,
			btnCancelClass;
		if (params["titleType"] && types.length - (params["titleType"] - 1) > 1) {
			titleType = types[params["titleType"] - 1];
		}
		if (params["btnOKClass"] && btnTypes.length - (params["btnOKClass"] - 1) > 1) {
			btnOKClass = btnTypes[params["btnOKClass"] - 1];
		}
		if (params["btnCancelClass"] && btnTypes.length - (params["btnCancelClass"] - 1) > 1) {
			btnCancelClass = btnTypes[params["btnCancelClass"] - 1];
		}
		window.confirm2 && window.confirm2({
			type: titleType,
			title: params["title"],
			message: params["message"],
			btnOKClass: btnOKClass,
			btnOKLabel: params["btnOKLabel"],
			btnCancelClass: btnCancelClass,
			btnCancelLabel: params["btnCancelLabel"],
			callback: function(state) {
				thatWin.callbackParam = thatWin.callbackParam || {};
				$.each(ruleOne.callback, function(i, k) {
					thatWin.callbackParam[k.param] = state;
				})
				if (!ruleOne.callback) {
					state && pubsub.execChilds(rule);
				} else {
					pubsub.execChilds(rule);
				}
			}
		})
	},
	//退出系统
	logout: function(rule, params, args) {
		location.href = contextPath + "/mx/login/logout";
	},

	systemServer: function(rule, params, args) {
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		var operation = rule[0] != undefined ? rule[0].useId : rule.useId;
		if (!params) {
			return;
		}
		var isAlt = false;
		if (params.isAlt) {
			isAlt = true;
		}
		var ruleOne;
		var thatWin = /*pubsub.getThat(rule, true)*/ window;
		if ($.isPlainObject(rule)) {
			ruleOne = rule;
		}
		if ($.isArray(rule)) {
			ruleOne = rule[0];
		}


		var callbackFunct = function() {
			pubsub.execChilds(rule);
		}
		if (operation == 'addRole') {
			//新增部门
			$.ajax({
				url: contextPath + '/mx/custom/role/saveAdd',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("添加角色成功");
								}
							}
							//回调参数信息
							thatWin.callbackParam = thatWin.callbackParam || {};
							$.each(ruleOne.callback, function(i, k) {
									thatWin.callbackParam[k.param] = ret.data[k.columnName];
									//window.callbackParam[k.param] = msg[k.columnName];
								})
								// thatWin.callbackParam.id = ret.data.id;
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("添加角色失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("服务器处理错误！,请修改后再试。");
				}
			})
		} else if (operation == 'modifyRole') {
			//新增部门
			$.ajax({
				url: contextPath + '/mx/custom/role/saveModify',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("修改角色成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("修改角色失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("服务器处理错误！,请修改后再试。");
				}
			})
		} else if (operation == 'deleteRole') {
			//删除角色
			confirm2("是否确认删除", function(ok) {
				if (!ok) {
					return;
				}
				$.ajax({
					url: contextPath + '/mx/custom/role/batchDelete',
					type: "post",
					dataType: "json",
					async: true,
					data: params,
					success: function(ret) {
						if (ret) {
							if (ret.statusCode == '200') {
								if (!isAlt) {
									if (ret.message) {
										alert(ret.message);
									} else {
										alert("删除角色成功");
									}
								}
							} else {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("删除角色失败");
								}
							}
						}
						if (ret && ret.statusCode == '200') {
							callbackFunct();
						}
					},
					error: function(e) {
						alert("服务器处理错误！,请修改后再试。");
					}
				})
			});
		} else if (operation == 'addDepartment') {
			//新增部门
			$.ajax({
				url: contextPath + '/mx/custom/department/saveAdd',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("新增部门成功");
								}
							}
							//回调参数信息
							thatWin.callbackParam = thatWin.callbackParam || {};
							$.each(ruleOne.callback, function(i, k) {
									thatWin.callbackParam[k.param] = ret.data[k.columnName];
									//window.callbackParam[k.param] = msg[k.columnName];
								})
								// thatWin.callbackParam.id = ret.data.id;
								// thatWin.callbackParam.nodeId = ret.data.nodeId;
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("新增部门失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("服务器处理错误！,请修改后再试。");
				}
			})
		} else if (operation.split("-", 1) == 'copyServer') {
			var arr = operation.split("-");
			$.ajax({
				url: contextPath + '/mx/form/data/saveDataServer',
				async: true,
				data: {
					"databaseId": arr[2],
					"tableName": arr[1],
					"indexId": params.indexId,
					"tindexId": params.t_indexId,
					"indexKey": arr[3],
					"parentKey": arr[4],
					"treeKey": arr[5]
				},
				type: 'post',
				success: function(ret) {
					ret = eval("(" + ret + ")");
					if (ret.msg) {
						if (!isAlt) {
							alert(ret.msg);
						}
						callbackFunct();
					}
				}
			});

		} else if (operation == 'modifyDepartment') {
			var link = contextPath + "/mx/custom/department/saveModify";
			if (!params.id) {
				alert("修改时需要部门id");
				return;
			}
			$.ajax({
				url: link,
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("修改部门成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("修改部门失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("服务器处理错误！,请修改后再试。");
				}
			})
		} else if (operation == 'delDepartment') {
			var link = contextPath + "/mx/custom/department/batchDelete";
			confirm2("是否确认删除", function(ok) {
				if (params.id && !params.ids) {
					//拥有ID时
					params.ids = params.id;
				}
				if (ok) {
					jQuery.ajax({
						type: "POST",
						url: link,
						dataType: 'json',
						data: params,
						error: function(data) {
							alert('服务器处理错误！');
						},
						success: function(ret) {
							if (ret) {
								if (ret.statusCode == '200') {
									if (!isAlt) {
										if (ret.message) {
											alert(ret.message);
										} else {
											alert("删除部门成功");
										}
									}
								} else {
									if (ret.message) {
										alert(ret.message);
									} else {
										alert("删除部门失败");
									}
								}
							}
							if (ret && ret.statusCode == '200') {
								callbackFunct();
							}

						}
					});
				}
			});

		} else if (operation == 'blukAddUser') {
			//新增用户
			$.ajax({
				url: contextPath + '/mx/custom/user/blukSaveAdd',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("新增用户成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("新增用户失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'addUser') {
			//新增用户
			$.ajax({
				url: contextPath + '/mx/custom/user/saveAdd',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("新增用户成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("新增用户失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'registerUser') {
			//新增用户
			$.ajax({
				url: contextPath + '/mx/custom/user/register',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("新增用户成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("新增用户失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'modifyUser') {
			//修改用户
			if (!params.id) {
				alert("修改用户需要用户账号信息!");
			}
			$.ajax({
				url: contextPath + '/mx/custom/user/saveModify',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("修改用户成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("修改用户失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'resetUserPwd') {
			if (!params.id) {
				alert("修改用户密码需要用户账号信息!");
			}
			//修改用户
			$.ajax({
				url: contextPath + '/mx/custom/user/resetPwd',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("修改用户成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("修改用户失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'resetPwdByMySelf') {
			if (!params.id) {
				alert("修改用户密码需要用户账号信息!");
			}
			//修改用户
			$.ajax({
				url: contextPath + '/mx/custom/user/resetPwdByMySelf',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("修改密码成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("修改密码失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'deleteUser') {
			//修改用户
			confirm2("是否确认删除", function(ok) {
				if (ok) {
					$.ajax({
						url: contextPath + '/mx/custom/user/batchDelete',
						type: "post",
						dataType: "json",
						async: true,
						data: params,
						success: function(ret) {
							if (ret) {
								if (ret.statusCode == '200') {
									if (!isAlt) {
										if (ret.message) {
											alert(ret.message);
										} else {
											alert("删除成功");
										}
									}
								} else {
									if (ret.message) {
										alert(ret.message);
									} else {
										alert("删除失败");
									}
								}
							}
							if (ret && ret.statusCode == '200') {
								callbackFunct();
							}
						},
						error: function(e) {
							alert("异常错误");
						}
					})
				}
			});
		} else if (operation == 'saveUserRoles') {
			if (!params.actorId) {
				alert("分配用户角色需要用户信息!");
			}
			//修改用户
			$.ajax({
				url: contextPath + '/mx/custom/user/saveUserRoles',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("分配成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("分配失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误");
				}
			})
		} else if (operation == 'blukSaveUserRoles') {
			//修改用户
			$.ajax({
				url: contextPath + '/mx/custom/user/blukSaveUserRoles',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("分配成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("分配失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误");
				}
			})
		}else if (operation == 'blukInsertUserRoles') {
			//修改用户
			$.ajax({
				url: contextPath + '/mx/custom/user/blukInsertUserRoles',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("分配成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("分配失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误");
				}
			})
		}else if (operation == 'blukRemoveUserRoles') {
			//删除用户角色
			confirm2("是否确认删除", function(ok) {
				if (!ok) {
					return;
				}
				$.ajax({
					url: contextPath + '/mx/custom/user/blukRemoveUserRoles',
					type: "post",
					dataType: "json",
					async: true,
					data: params,
					success: function(ret) {
						if (ret) {
							if (ret.statusCode == '200') {
								if (!isAlt) {
									if (ret.message) {
										alert(ret.message);
									} else {
										alert("分配成功");
									}
								}
							} else {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("分配失败");
								}
							}
						}
						if (ret && ret.statusCode == '200') {
							callbackFunct();
						}
					},
					error: function(e) {
						alert("异常错误");
					}
				})
			});
		} else if (operation == 'addRole') {
			//新增角色
			$.ajax({
				url: contextPath + '/mx/custom/role/saveAdd',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("新增角色成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("新增角色失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'branchDepartmentSaveRole') {
			//新增角色
			$.ajax({
				url: contextPath + '/mx/custom/branch/department/saveRoles',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("新增成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("新增失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'addDatabaseAccessor') {
			//新增角色
			$.ajax({
				url: contextPath + '/mx/custom/database/saveAccessor',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("权限添加成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("权限添加失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'deleteDatabaseAccessor') {
			//删除数据库权限
			confirm2("是否确认删除", function(ok) {
				if (!ok) {
					return;
				}
				params.operation = 'revoke';
				$.ajax({
					url: contextPath + '/mx/custom/database/saveAccessor',
					type: "post",
					dataType: "json",
					async: true,
					data: params,
					success: function(ret) {
						if (ret) {
							if (ret.statusCode == '200') {
								if (!isAlt) {
									if (ret.message) {
										alert(ret.message);
									} else {
										alert("权限添加成功");
									}
								}
							} else {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("权限添加失败");
								}
							}
						}
						if (ret && ret.statusCode == '200') {
							callbackFunct();
						}
					},
					error: function(e) {
						alert("异常错误，请联系管理员！");
					}
				})
			});
		} else if (operation == 'setSmsPersion') {
			//新增角色
			$.ajax({
				url: contextPath + '/mx/custom/user/setSmsPersion',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("操作成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("操作失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'saveRoleMenus') {
			//新增角色
			$.ajax({
				url: contextPath + '/mx/custom/role/saveRoleMenus',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("操作成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("操作失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		}  else if (operation == 'saveRoleMenusNoDelete') {
			//新增角色
			$.ajax({
				url: contextPath + '/mx/custom/role/saveRoleMenusNoDelete',
				type: "post",
				dataType: "json",
				async: true,
				data: params,
				success: function(ret) {
					if (ret) {
						if (ret.statusCode == '200') {
							if (!isAlt) {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("操作成功");
								}
							}
						} else {
							if (ret.message) {
								alert(ret.message);
							} else {
								alert("操作失败");
							}
						}
					}
					if (ret && ret.statusCode == '200') {
						callbackFunct();
					}
				},
				error: function(e) {
					alert("异常错误，请联系管理员！");
				}
			})
		} else if (operation == 'deleteRoleMenu') {
			//删除角色菜单
			confirm2("是否确认删除", function(ok) {
				if (!ok) {
					return;
				}
				$.ajax({
					url: contextPath + '/mx/custom/role/deleteRoleMenu',
					type: "post",
					dataType: "json",
					async: true,
					data: params,
					success: function(ret) {
						if (ret) {
							if (ret.statusCode == '200') {
								if (!isAlt) {
									if (ret.message) {
										alert(ret.message);
									} else {
										alert("操作成功");
									}
								}
							} else {
								if (ret.message) {
									alert(ret.message);
								} else {
									alert("操作失败");
								}
							}
						}
						if (ret && ret.statusCode == '200') {
							callbackFunct();
						}
					},
					error: function(e) {
						alert("异常错误，请联系管理员！");
					}
				})
			});
		}
	}
};
pubsub.sub("page", pageBtFunc);

/**
 * 查询服务、其他服务等等回调函数
 * @param thatWin
 * @param params
 * @param msg
 * @param rule
 * @param ruleOne
 * @returns
 */
function SearchCallback(thatWin, params, msg, rule, ruleOne) {
	thatWin.callbackParam = thatWin.callbackParam || {};
	if (msg.total) {
		var retData = msg.data,
			retDataOne = retData[0],
			initParam = {};
		$.each(ruleOne.callback, function(i, k) {
			thatWin.callbackParam[k.param] = retDataOne[k.columnName];
			if (k.dataId && k.dataId.indexOf("ary") == 0) {
				!initParam[k.dataId] && (thatWin.callbackParam[k.dataId] = []) && (initParam[k.dataId] = true);
				//callbackParam[k.dataId] || (callbackParam[k.dataId] = []);
				$.each(retData, function(index, val) {
					thatWin.callbackParam[k.dataId][index] || (thatWin.callbackParam[k.dataId][index] = {});
					thatWin.callbackParam[k.dataId][index][k.param] = val[k.columnName];
				})
			} else if (k.dataId && k.dataId.indexOf("obj") == 0) {
				!initParam[k.dataId] && (thatWin.callbackParam[k.dataId] = {}) && (initParam[k.dataId] = true);
				//callbackParam[k.dataId] || (callbackParam[k.dataId] = {});
				thatWin.callbackParam[k.dataId][k.param] = retDataOne[k.columnName];
			}
		});
		if (!params["isAlt"]) {
			alert('操作成功完成！');
		}
	} else {
		$.each(ruleOne.callback, function(i, k) {
			thatWin.callbackParam[k.param] = "";
		});

		if (msg.message) {
			alert(msg.message);
		} else if (!params["isAlt"]) {
			alert('没有查询结果.');
		}
	}
	pubsub.execChilds(rule);
}

function ClosePageTab(TABID) {
	var tabWin = GetTabWin.find(),
		cWin;
	if (tabWin != null) {
		var close_ = function(id) {
			var a = tabWin.$("#link_" + id) //
				.closest("li").find("div a");
			return !!!tabWin.closePage(a.get(0));
		};
		if (TABID) {
			close_(TABID);
		} else if (cWin = tabWin.__child) {
			tabWin.$("iframe").each(function() {
				if (this.contentWindow === cWin) {
					var id = tabWin.$(this) //
						.closest("div").attr("id");
					return !close_(id);
				}
			});
		}
	}
}


/**
 * 获取选项卡所在的window
 */
var GetTabWin = (function(win) {
	var func = new Function(),
		index = 0, //
		level = 5;
	func.find = function(win) {
		win = win || window;
		if (win.document.getElementById("usual1") && //
			win.closePage) {
			index = 0;
			return win;
		} else {
			win.parent && (win.parent.__child = win);
			if (!win.parent || index > level) {
				index = 0;
				return null;
			}
			++index;
			return func.find(win.parent);
		}
	};
	return func;
})();

/**
 * 页面参数取值
 * @param key
 * @returns
 */
function GetPageParameter(key) {
	var params = pageParameters;
	return (params || {})[key];
}
/**
 * 页面参数赋值
 * @param key
 * @param value
 * @returns
 */
function SetPageParameter(key, value) {
	var params = pageParameters;
	params && (params[key] = value);
}

function initPageConfigFunc(opts) {
	//背景图切换
	var images = [];
	if (opts.bgImage && opts.bgImage.length == 0) {} else if (opts.bgImage && opts.bgImage.length == 1) {
		$.backstretch([
			contextPath + opts.bgImage[0].name
		], {
			fade: opts.fade * 1000,
			duration: opts.duration * 1000
		});
	} else if (opts.bgImage) {
		$.each(opts.bgImage, function(i, o) {
			images[i] = contextPath + o.name;
		});
		$.backstretch(images, {
			fade: opts.fade * 1000,
			duration: opts.duration * 1000
		});
	}
}/**
 * 进度条
 */
var progressbarFunc = {
	getValue : function(rule, args) {
		var $id = pubsub.getJQObj(rule,true);
		return $id.progressBarExt("getValue");
	},
	setValue : function(rule, args) {
		var $id = pubsub.getJQObj(rule), r, v;
		var params = {};
		if(args.value){
			//设置固定值
			params.value = args.value;
			params.length = 1;
		}
		if(args.value_uint){
			//设置固定值后缀
			params.value_uint = args.value_uint;
			params.length = 1;
		}
		if(args.remain){
			//设置剩余值
			params.remain = args.remain;
			params.length = 1;
		}
		if(args.remain_uint){
			//设置剩余值后缀
			params.remain_uint = args.remain_uint;
			params.length = 1;
		}
		if(params.length > 0){
			$id.progressBarExt("setValue",params);
		}else{
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			if (v) {
				$id.progressBarExt("setValue",v);
			}
		}
	},
	tdisabled: function(rule, args) { // 禁用
		var $id = pubsub.getJQObj(rule);
		$id.progressBarExt("disabled",false);
	},
	tenabled: function(rule, args) { // 启用
		var $id = pubsub.getJQObj(rule);
		$id.progressBarExt("disabled",true);
	}
};
pubsub.sub("progressbarbt", progressbarFunc);var tagsinputObj = {
	
	defaults:{
		visible:true,
		enabled:true,
		defaultVal:''
	},
	init : function(rule, args) {
		var options = args[0],$id = $("#" + rule.inid);
		var opts = $.extend(true,{},tagsinputObj.defaults, options);
		
		tagsinputObj.setValue($id,opts.defaultVal);
		tagsinputObj.display($id,opts.visible);
		$id.data('tagsinput',{"element":$id[0],"defaults":tagsinputObj.defaults,"options":opts});
	},
	getValue : function(rule, args) {
		var $id = (rule instanceof $)?rule:pubsub.getJQObj(rule, true);
		var jq = tagsinputObj.getOject.call($id);
		return jq.val();
	},
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		tagsinputObj.display(rule,false);
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		tagsinputObj.display(rule,true);
	},
	tclear: function(rule, args) { //清空
		var $id = (rule instanceof $)?rule:pubsub.getJQObj(rule);
		var jq =  tagsinputObj.getOject.call($id);
		jq.attr("tclear", "tclear");
		jq.tagsinput('removeAll');
	},
	setValue : function(rule,args){
		var jq,r,v="";
		jq = tagsinputObj.getOject.call((rule instanceof $)?rule:pubsub.getJQObj(rule));
		jq.attr("setValue", "setValue");
		jq.tagsinput('removeAll');
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			jq.tagsinput('add', v);
		} else if(rule instanceof $){
			jq.tagsinput('add', args);
		}
	},
	appendValue : function(rule,args){
		var $id = (rule instanceof $)?rule:pubsub.getJQObj(rule),jq,r,v;
		jq = tagsinputObj.getOject.call($id);
		jq.attr("setValue", "setValue");
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			jq.tagsinput('add', v);
		} else if(rule instanceof $){
			jq.tagsinput('add', args);
		}
	},
	display : function(rule,args){    //是否显示
		var $id = (rule instanceof $)?rule:pubsub.getJQObj(rule);
		if (!args){    
			$id.css("display", "none");
		}else{
			$id.css("display", "");
		}
	},
//	disabled : function(bl){    //是否启用   
//		if(!bl){
////			jq.attr("disabled","disabled");		
//			this.css("cursor","not-allowed");
//		}else{
////			jq.removeAttr("disabled");
//			this.css("cursor","");
//		}
//	},
	getOject : function(){
		$this = $(this);
		var obj;
		if ($this.find('input[role="tagsinput"]').length>0) {
			obj = $this.find('input[role="tagsinput"]');
		} else {
			obj = $this;
		}
		return obj;
	}
};
pubsub.sub("tagsinput", tagsinputObj);/**
 * textareabt事件定义器
 */
var textAreaBtFunc = {
	getValue: function(rule, args) { //取值
		var $id = pubsub.getJQObj(rule, true);
		return $id.textAreaBtExt("getValue");
	},
	setValue: function(rule, args) { //赋值
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.attr("setValue", "setValue");
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			$id.textAreaBtExt("setValue",v);
		} else {
			$id.textAreaBtExt("setValue",args);
		}
	},
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.textAreaBtExt("display",false);
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.textAreaBtExt("display",true);
	},
	tclear: function(rule, args) { //清空
		var $id = pubsub.getJQObj(rule);
		var jq = $id.textAreaBtExt("getOject");
		jq.attr("tclear", "tclear").val("");
	},
	tdisabled: function(rule, args) { // 禁用
		var $id = pubsub.getJQObj(rule);
		$id.textAreaBtExt("disabled",false);
	},
	tenabled: function(rule, args) { // 启用
		var $id = pubsub.getJQObj(rule);
		$id.textAreaBtExt("disabled",true);
	},
	appendSetValue: function(rule, args) {//追加赋值
		var $id = pubsub.getJQObj(rule),r, v = "";
		var jq = $id.textAreaBtExt("getOject"),
			bval = jq.val() ? jq.val() + "," : "";
		$id.attr("setValue", "setValue");
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			$id.textAreaBtExt("setValue",bval + v);
		} else {
			$id.textAreaBtExt("setValue",bval + args);
		}
	}
	
};
pubsub.sub("textareabt", textAreaBtFunc);/**
 * textboxbt事件定义器
 */
var textboxbtFunc = {
	getValue: function(rule, args) { //取值
		var $id = pubsub.getJQObj(rule, true);
		return $id.textboxBtExt("getValue");
	},
	setValue: function(rule, args) { //赋值
		var $id = pubsub.getJQObj(rule),r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			$id.textboxBtExt("setValue",v);
		} else {
			$id.textboxBtExt("setValue",args);
		}
	},
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		$id.textboxBtExt("display",false);
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		$id.textboxBtExt("display",true);
	},
	tclear: function(rule, args) { //清空
		var $id = pubsub.getJQObj(rule);
		var jq = $id.textboxBtExt("getOject");
		jq.attr("tclear", "tclear").val("");
	},
	tdisabled: function(rule, args) { // 禁用
		var $id = pubsub.getJQObj(rule);
		$id.textboxBtExt("disabled",false);
	},
	tenabled: function(rule, args) { // 启用
		var $id = pubsub.getJQObj(rule);
		$id.textboxBtExt("disabled",true);
	},
	appendSetValue: function(rule, args) {//追加赋值
		var $id = pubsub.getJQObj(rule),r, v = "";
		var jq = $id.textboxBtExt("getOject"),
			bval = jq.val() ? jq.val() : "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
				if(v){
					if(bval)
						bval += ",";
					bval = bval + v;
				}
			}
			$id.textboxBtExt("setValue",bval);
		} else {
			$id.textboxBtExt("setValue",bval + args);
		}
	}
};
pubsub.sub("textboxbt", textboxbtFunc);/**
 * touchspin事件定义器
 */
var touchSpinFunc = {
	getValue: function(rule, args) { //取值
		var $id = pubsub.getJQObj(rule, true);
		return $id.touchSpinExt("getValue");
	},
	getSumValue: function(rule, args) {
		var $id = pubsub.getJQObj(rule, true);
		var $thats = $id.closest("table").find("[columnname=" + $id.attr("columnname") + "]");
		var retVal = 0;
		$.each($thats, function(index, el) {
			retVal = mtMath.add(retVal, parseFloat($(this).touchSpinExt("getValue") || 0));
		});
		return retVal;
	},
	setValue: function(rule, args) { //赋值
		var $id = pubsub.getJQObj(rule),
			r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param] != null ? args[r.param] : "";
			}
			$id.touchSpinExt("setValue", v);
		} else {
			$id.touchSpinExt("setValue", args);
		}
	},
	tclear: function(rule, args) { //清空
		var $id = pubsub.getJQObj(rule);
		var jq = $id.textboxBtExt("getOject");
		jq.attr("tclear", "tclear").val("");
	},
	tdisabled: function(rule, args) { // 禁用
		var $id = pubsub.getJQObj(rule);
		$id.touchSpinExt("disabled", false);
	},
	tenabled: function(rule, args) { // 启用
		var $id = pubsub.getJQObj(rule);
		$id.touchSpinExt("disabled", true);
	}

};
pubsub.sub("touchspin", touchSpinFunc);var validateMethods = {
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
var validateLength = function($this, min, max) {
	$this.data("mt-vail-er-msg", validateMessage["dateEqual"].format($this.attr("mtTitle"), min, max));
	setCustomRule($this, $this.data("mt-vail-er-msg"));
	var curVal = $.trim(/*$('#' + $this.data('ruleId'))*/$this.mtbootstrap('getValue')),
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
	$this.data("mt-vail-er-msg", validateMessage["checkNum"].format($this.attr("mtTitle"), num));
	setCustomRule($this, $this.data("mt-vail-er-msg"));
	var checkNum = $("input[name=" + $this.attr("name") + "]").prop("checked");
	if (checkNum.length - num >= 0) {
		return true;
	}
	return false;
};var ztreeFunc = {
	//获取选中的节点
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
		//nodes.length || (nodes = ztreeObj.getCheckedNodes());
		var retVal = "";
		if (nodes.length) {
			for (var i = 0, len = nodes.length; i < len; i++) {
				retVal += nodes[i][rule.columnName] || "";
				retVal += (i == len - 1) ? "" : ",";
			}
			//return nodes[0][rule.columnName];
		}
		return retVal;
		//}
	},
	//设置展开层级数
	setExpandLevel:function(rule,args){
		var $id = pubsub.getJQObj(rule);
		var value = "";
		$.each(rule,function(i,item){
			value = args[rule[i].param] || "";
		})
		$id.data("expandChilds",value||0);
	},
	//获取勾选的节点
	getChkRow: function(rule, args) {
		var $id = pubsub.getJQObj(rule, true);
		if (!$id) {
			$id = pubsub.getJQObj(rule.inid, true);
		}
		var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
		var nodes = ztreeObj.getCheckedNodes();
		var retVal = "";
		if (nodes.length) {
			for (var i = 0, len = nodes.length; i < len; i++) {
				retVal += nodes[i][rule.columnName] || "";
				retVal += (i == len - 1) ? "" : ",";
			}
		}
		return retVal;
	},
	//获取当前点击的节点
	getCurRow: function(rule, args) {
		if (args && args.length > 1) {
			return args[1][rule.columnName];
		}
		var $id = pubsub.getJQObj(rule, true);
		if (!$id) {
			$id = pubsub.getJQObj(rule.inid, true);
		}
		var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
		var nodes = ztreeObj.getCheckedNodes();
		var retVal = "";
		if (nodes.length) {
			for (var i = 0, len = nodes.length; i < len; i++) {
				retVal += nodes[i][rule.columnName] || "";
				retVal += (i == len - 1) ? "" : ",";
			}
			return retVal;
		}
		return "";
	},
	linkage: function(rule, args) {
		ztreeFunc.linkageControl(rule, args);
	},
	linkageControl: function(id, params) {
		var $id = pubsub.getJQObj(id);
		var requireNum = $id.data("requireNum") || 0;	//请求数量
		if ($id && (!requireNum)) {
			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
			var otherParam = ztreeObj.setting.async.otherParam;
			otherParam.params = JSON.stringify(params);
			ztreeObj.setting.async.otherParam = otherParam;

			if(params.expandLevel != null){
				$id.data("expandChilds",params.expandLevel||0);
			}else{
				var expandChilds = $id.data("expandChilds");
				$id.data("expandChilds",expandChilds||ztreeObj.setting.expandChilds||0);
			}
			ztreeObj.reAsyncChildNodes(null, "refresh");
		}
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
	showChilds : function(rule, args){
		var $id = pubsub.getJQObj(rule, true);
		if ($id) {
			var ztreeObj = $.fn.zTree.getZTreeObj($id.attr('id'));
			var nodes = ztreeObj.getCheckedNodes(true);
			$.each(nodes,function(i,o){
				ztreeObj.expandNode(o, true, true, true);
			})
		/*	if (nodes.length>0) {
				ztreeObj.expandNode(nodes[0], true, true, true);
			}*/
		}
	},
	//当前节点和子节点刷新
	nodeRefresh:function(rule, args){
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			var ztreeObj = $.fn.zTree.getZTreeObj($id.attr('id'));
			var nodes = ztreeObj.getSelectedNodes();
			if (nodes.length>0) {
				ztreeObj.reAsyncChildNodes(nodes[0], "refresh");
			}
		}
		
	},
	//当前节点和父节点刷新
	parentRefresh:function(rule, args){
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			var ztreeObj = $.fn.zTree.getZTreeObj($id.attr('id'));
			var nodes = ztreeObj.getSelectedNodes();
			if (nodes.length>0) {
				var node = nodes[0].getParentNode();
				ztreeObj.reAsyncChildNodes(node, "refresh");
			}
		}
		
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
	cancelSelectNodes: function(rule, args) { //取消选中节点
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
			ztreeObj.cancelSelectedNode();
			ztreeObj.checkAllNodes(false);
		}
	},
	_init_: function(rule, param, args) {
		var $id = pubsub.getJQObj(rule);
		eval("(" + __gobalInit__[$id.attr("id")] + ")")($.isArray(param) ? {} : param);
		pubsub.execChilds(rule);
	},
	isChecked : function(rule, args){
		if(args[1]){
			return args[1].checked;
		}
		var $id = pubsub.getJQObj(rule,true),
			ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
			selectNodes = ztreeObj.getSelectedNodes();
		if(selectNodes.length){
			return selectNodes[0].checked ;
		}
		return false;
	},
	
	
	checkedNode : function(rule,args){
		// var $id = pubsub.getJQObj(rule);
		// if ($id) {
		// 	var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
		// 	//遍历args
		// 	$.each(args,function(key,value){
		// 		//根据,分割字符串
		// 		var strs = value.split(",");
		// 		$.each(strs,function(i,str){
		// 			var ztreeNodes = ztreeObj.getNodesByParam(key, str, null);
		// 			$.each(ztreeNodes,function(i,item){
		// 				ztreeObj.checkNode(item, true, false);	
		// 			})
		// 		})
		// 	})
		// }

		var $id = pubsub.getJQObj(rule);
		if ($id) {
			var compare_key = args.key;

			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
			//遍历args
			$.each(args,function(key,value){
				//根据,分割字符串
				if(key == 'key'){
					return true;
				}
				var strs = value.split(",");
				$.each(strs,function(i,str){
					var ztreeNodes = ztreeObj.getNodesByParam(compare_key || key, str, null);
					$.each(ztreeNodes,function(i,item){
						ztreeObj.checkNode(item, true, false);	
					})
				})
			})
		}
	},
	selectNode: function(rule, args) { //取消选中节点
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));

			var ztreeKey = args.key;	//获取key值
			//key = key || 'id';

			//遍历args
			$.each(args,function(key,value){
				//如果输入参数是用于设置key值时，跳过
				if(key == 'key'){
					return true;
				}
				//根据,分割字符串
				var strs = value.split(",");
				key = ztreeKey || key;
				$.each(strs,function(i,str){
					var ztreeNodes = ztreeObj.getNodesByParam(key, str, null);
					$.each(ztreeNodes,function(i,item){
						ztreeObj.selectNode(item);
						ztreeObj.setting.callback.onClick(null, $id.attr('id'), item);
					})
				})
			})
		}
	},
	getKeyName : function(rule,args){
		return rule.columnName;
	},
	insertStaticRow : function(rule,args){
		var $id = pubsub.getJQObj(rule);
		if(!$id && !$id[0]){
			//无对象时跳过
			return;
		}
		var inparamobjrule = $id.data("inparamobjrule");
		
		//集合信息
		var arySource = inparamobjrule["arySource"];
		if($.isArray(arySource)){
			//数据转换为如下：集合：对象
			//{ary1495094362175: "obj1495077696961"}
			//若是数组，转换为json数据
			var arySourceArray = arySource;
			arySource = {};
			$.each(arySourceArray,function(i,item){
				var child = item.child;
				var objName = "";	//对象名称
				if(child && child[0]){
					objName = child[0].param;
				}
				arySource[item.param] = objName;
			})
			inparamobjrule["arySource"] = arySource;
		}
		// console.log(arySource);
		//对象信息
		var objSource = inparamobjrule["objSource"];
		if($.isArray(objSource)){
			//转换为如下数据，参数：映射字段
			// {
			// 	"obj1495077696961": {
			// 		"col1495077685661": "r_glafdb_tb3_0_r_glafdb_tb3_col9",
			// 		"col1495077685657": "r_glafdb_tb3_0_r_glafdb_tb3_col8",
			// 		"col1495077685651": "r_glafdb_tb3_0_r_glafdb_tb3_col7",
			// 		"col1495077685646": "r_glafdb_tb3_0_r_glafdb_tb3_col6",
			// 		"col1495077685642": "r_glafdb_tb3_0_r_glafdb_tb3_col5"
			// 	},
			// }
			
			//若是数组，则将数组转换为json数据
			var objSourceArray = objSource;
			objSource = {};
			$.each(objSourceArray,function(i,item){
				var child = JSON.parse(item.child);
				var param = {};
				$.each(child,function(i,item){
					param[item.param] = item.columnFiled;
				})
				objSource[item.param] = param;
			})
			inparamobjrule["objSource"] = objSource;
		}
		if(!objSource){
			//无对象信息时，无法插入
			return;
		}

		//寻找对象赋值
		var datas = [];
		var data = null;
		$.each(args,function(key,value){
			if($.isPlainObject(value) && !$.isEmptyObject(value)){
				//为对象时
				data = {};
				var nowInparamObj = objSource[key];
				$.each(value,function(key2,value2){
					if(nowInparamObj[key2]){
						//注释掉为"sss,ssss"的格式.
						// var value2Array = value2.split(",");
						// $.each(value2Array,function(i,item){
						// 	if(datas.length <= i){
						// 		datas.push({});
						// 	}
						// 	datas[i][nowInparamObj[key2]] = item;
						// })
						data[nowInparamObj[key2]] = value2;
					}
				})
				datas.push(data);
			}else if($.isArray(value)){
				//为集合时
				if(!arySource[key]){
					//集合中无对象信息
					return true;
				}

				var nowInparamObj = objSource[arySource[key]];
				$.each(value,function(i,item){
					data = {};
					$.each(item,function(key2,value2){
						if(nowInparamObj[key2]){
							data[nowInparamObj[key2]] = value2;
						}
					})
					datas.push(data);
				})
			}

		})

		if(datas && datas.length > 0){
			//grid插入数据
			
			var ztreeObj = $id.zTree.getZTreeObj($id.attr('id'));
		}
	}
};
if(typeof pubsub != 'undefined'){
	pubsub.sub("ztree", ztreeFunc);
}

function expandNodesFunc(ztreeObj,expandNodes,expandChilds){
	var ztreeObj = ztreeObj;
	var expandChilds = expandChilds;
	if(expandChilds && expandChilds >= 2 && expandNodes && expandNodes.length>0){
		$.each(expandNodes,function(i,item){
			ztreeObj.expandNode(item, true, false, true);
			expandNodesFunc(ztreeObj,item.children,expandChilds - 1);
		})
	}
}

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
				if (!isDeep) {
					return retVal;
				}
			} else {
				childVal = getChildByParam(children, field, exp, outFiled, isDeep);
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
function isMobile(){
	var userAgentInfo = navigator.userAgent;  
	var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod");  
	var flag = false;  
	for (var v = 0; v < Agents.length; v++) {  
		if (userAgentInfo.indexOf(Agents[v]) > 0) { flag = true; break; }  
	}  
	return flag;  
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
				var treeIdValue = "";
				if (nodes.length == 1) {
					pidkey = nodes[0].pidkey;
					treeIdValue = nodes[0].idkey;
				}
				link = contextPath + '/mx/form/treeData/ztreeEdit?rid=' + ruleId + '&pid=' + pidkey + '&treeId=' + ztreeId + '&epac=' + editPanleAutoClose + '&treeIdValue=' + treeIdValue;
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
		var SB = "<iframe width='100%' height='280' src='" + link + "' frameborder='no' scrolling='no'></iframe>";
		window.ztreeOpenShow = window.show({
			title: '增加/编辑节点',
			size: BootstrapDialog.SIZE_WIDE,
			message: SB,
			modal: true,
			draggable: true,
			css: {
				width: 400,
				height: 300
			}
		});
	},
	init: function(id, options,ztreeNodesData) {
		var ztreeObj = null;
		
		// $('body').append('<link rel="stylesheet" href="'+contextPath+'/scripts/ztree/css/demo.css">');
		
		
		
		

		var $this = $('#' + id),
			_bindEvent_ = $this.data("_bindEvent_");
		
		$this.data("expandChilds",options.expandChilds || 1);
		//选中该节点和子节点
		var chkboxType = options.check.chkboxType;
		if(options.check.chkboxType == '2'){
			chkboxType = { "Y" : "s", "N" : "s" };
		}
		
		//选中该节点的父节点
		if(options.check.chkboxType == '1'){
		    chkboxType = { "Y" : "p", "N" : "p" };
		}
		//之选中该节点
		if(options.check.chkboxType == '0'){
		    chkboxType = { "Y" : "", "N" : "" };
		}
		//之选中该节点
		if(options.check.chkboxType == '3'){
		    chkboxType = { "Y" : "ps", "N" : "ps" };
		}
	    options.check.chkboxType = chkboxType;
		/*var check = {
				enable: true,
        	checkboxType : chkboxType
        }
		options.check = check;*/
		
		if(isMobile() || $this.attr("zTreeStyle") == 'awesome'){
			options.callback.beforeClick = function (treeId, treeNode, clickFlag) {
			    //treeNode.checked = treeNode.checked?false:true;
			    //ztreeObj.refresh();
			    if(!treeNode.chkDisabled){
			    	ztreeObj.checkNode(treeNode, !treeNode.checked, true);	
			    }
			    return true;
			};

			glafZtree.trunToMobileStyle(options);
		}

		if(ztreeNodesData){
			
			ztreeObj = $.fn.zTree.init($this, options, ztreeNodesData);
		}else{
			ztreeObj = $.fn.zTree.init($this, options);
		}

		

		$this.data("kkk",ztreeObj);		
		if (_bindEvent_) {
			$.each(_bindEvent_, function(i, bindEvent) {
				pubsubobjects["ztree"].call($this, bindEvent);
			})
		}
		if (options) {
			$("#ztree_" + id + "_add").addClass('btn btn-primary hmtd-xs').on('click', function() {
				if(ztreeNodesData){
					ztreeObj.addNodes(null,[{name:"newNode1"}] );
				}else{
					glafZtree.ztreeToolbar('add', id, options.opts.ruleId, options.opts.editPanleAutoClose);	
				}
				
			});
			$("#ztree_" + id + "_add_next").addClass('btn btn-primary hmtd-xs').on('click', function() {
				if(ztreeNodesData){
					var nodes = ztreeObj.getSelectedNodes();
					if(nodes){
						ztreeObj.addNodes(nodes[0],[{name:"newNode1"}] );
					}

				}else{
					glafZtree.ztreeToolbar('next', id, options.opts.ruleId, options.opts.editPanleAutoClose);
				}
			});
			$("#ztree_" + id + "_edit").addClass('btn btn-primary hmtd-xs').on('click', function() {
				if(ztreeNodesData){

				}else{
					glafZtree.ztreeToolbar('edit', id, options.opts.ruleId, options.opts.editPanleAutoClose);
				}
			});
			$("#ztree_" + id + "_del").addClass('btn btn-primary hmtd-xs').on('click', function() {
				if(ztreeNodesData){

				}else{
					glafZtree.ztreeToolbar('del', id, options.opts.ruleId, options.opts.editPanleAutoClose);
				}
			});
		}
	},
	trunToMobileStyle : function(options){
		if(!$("[href='"+contextPath+"/scripts/ztree/css/awesomeStyle/awesome.css']")[0]){
			$('body').append('<link rel="stylesheet" href="'+contextPath+'/scripts/ztree/css/awesomeStyle/awesome.css">');
			$('body').append('<link rel="stylesheet" href="'+contextPath+'/scripts/ztree/css/zTreeStyle/font-awesome.min.css">');
			$('body').append('<link rel="stylesheet" href="'+contextPath+'/scripts/ztree/css/awesomeStyle/ztree.extend.css">');
			$("[href='"+contextPath+"/scripts/ztree/css/zTreeStyle/zTreeStyle.css']").remove();
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
};/**
 * 侧边栏事件定义器接口
 */
var sidebarFunc = {
	display:function(rule, args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.sidebarExt("display");
	},
	show:function(rule, args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.sidebarExt("showSidebar");
	},
	hide:function(rule, args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.sidebarExt("hideSidebar");
	},
};
pubsub.sub("sidebar", sidebarFunc);var stepFunc = {
	init : function(rule, args) {
		var options = args[0];
		stepFunc.display.call($("#"+rule.inid),options.visible);
		if(options.fixDataSource.length!=0){
			stepFunc.createEle.call($("#"+rule.inid),options,options.fixDataSource);
		}else{
			$.ajax({
				type : "POST",
				url : contextPath + "/mx/form/data/gridData",
				contentType : "application/json",
				dataType : "json",
				data : JSON.stringify({'rid' : options.rid}),
				success : function(ret) {
					stepFunc.createEle.call($("#"+rule.inid),options,ret.data);
				}
			});
		}
		
	},
	createEle:function(opts,data){
		if(data.length!=0){
			var that = this;
			var container = that.find("div.step-line").html("");
			$.each(data,function(i,o){
				
				var $col= $('<div class="step mt-step-col"></div>');
				if(i==0){
					$col.addClass("first");
				}else if(i==data.length-1){
					$col.addClass("last");
				}
				$col.css("width",100/data.length+"%");
				$col.data("step",data[i]);
				container.append($col);	
//				
//				if(opts.fixDataSource.length!=0){//如果使用固定数据集则不应用自定义规则
//					if(data[i].number){
//						$col.append('<div class="mt-step-number bg-white">'+data[i].number+'</div>');
//					}else{
//						$col.append('<div class="mt-step-number bg-white">'+(i+1)+'</div>');				
//					}
//					if(data[i].content){
//						$col.append('<div class="mt-step-title uppercase font-grey-cascade">'+data[i].content+'</div>');
//					}else{
//						$col.append('<div class="mt-step-title uppercase font-grey-cascade"></div>');				
//					}
//				}else{
					if (opts.defined&&opts.defined.length) {//应用自定义规则，假如成立就添加元素
						var defineds = opts.defined;
						for (var j = 0; j < defineds.length; j++) {
							var defined = defineds[j];
							key = stepFunc._convert(defined.key, data[i], 'key');
							if (eval(key) || key == "true") {
								if(defined.numberVal){
									$col.append('<div class="mt-step-number bg-white">'+stepFunc._convert(defined.numberVal,data[i])+'</div>');
								}else{
									$col.append('<div class="mt-step-number bg-white">'+(i+1)+'</div>');
								}
								$col.append('<div class="mt-step-title uppercase font-grey-cascade">'+stepFunc._convert(defined.contentVal,data[i])+'</div>');
								return;
							}
						}
					}
					if($col.find("div.mt-step-number").length==0&&data[i].number){//如果自定义不成立，添加默认
						$col.append('<div class="mt-step-number bg-white">'+data[i].number+'</div>');
					}else{						
						$col.append('<div class="mt-step-number bg-white">'+(i+1)+'</div>');
					}
					if($col.find("div.mt-step-title").length==0&&data[i].content){
						$col.append('<div class="mt-step-title uppercase font-grey-cascade">'+data[i].content+'</div>');
					}else{
						$col.append('<div class="mt-step-title uppercase font-grey-cascade"></div>');
					}
//				}

			});
		}
		
	},
	_convert: function(k, v, type) {
		var par, val, reger, tv = (type == 'key' ? "'" : "");
		if (v) {
			for (var p in v) {
				if(v[p] ){
					var kt = p.split("_0_")[1]|| p;
					reger = new RegExp("##" + kt + "##", "gm");
					k = k.replace(reger, tv + v[p] + tv);
				}
			}
		}
		return k.replace(/##/gm, "'").replace(/\\"/gm, "'").replace(/##col\w*##/gm, type == 'key' ? "''" : "");
	},
	display : function(bl){
		var that = this;
		if (!bl){    
			that.css("display", "none");
		}else{
			that.css("display", "");
		}
	},
	
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		stepFunc.display.call($id,false);
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		stepFunc.display.call($id,true);
	},
	getValue : function(rule, args) {
		
	},
	setValue : function(rule, args){
		
	},
	getData:function(rule, args){
		return args[0][rule.columnName];
	},
	active:function(rule, args){
		var $id = pubsub.getJQObj(rule);
		var v = stepFunc.getParam(rule, args);
		var steps = $id.find("div.mt-step-col");
		steps.removeClass("done active error");
		for (var j = 0; j < steps.length; j++) {
			var data = $(steps[j]).data("step");
			$(steps[j]).addClass("done");
			if(data[rule[0].columnName]==v||data.index==v){
				$(steps[j]).removeClass("done");
				$(steps[j]).addClass("active");
				return;
			}
		}
	},
	error:function(rule, args){
		var $id = pubsub.getJQObj(rule);
		var v = stepFunc.getParam(rule, args);
		var steps = $id.find("div.mt-step-col");
		steps.removeClass("done active error");
		for (var j = 0; j < steps.length; j++) {
			var data = $(steps[j]).data("step");
			$(steps[j]).addClass("done");
			if(data[rule[0].columnName]==v||data.index==v){
				$(steps[j]).removeClass("done");
				$(steps[j]).addClass("error");
				return;
			}
		}
	},
	getParam:function(rule, args){
		var r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
				if(v){
					break;
				}
			}
		}
		return v;
	}
};
pubsub.sub("step", stepFunc);/**
 * 
 */
var bootstraptabsFunc = {
	activeTab:function(rule, args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		var $a = $id.find('a[data-toggle="tab"]');
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
				$.each($a,function(j,o){
					if($(o).find("span").html()==v||j==v){
						setTimeout(function(){
							$(o).tab('show');
						},100);
						// $(o.hash).show();
					}
				});
			}
		} 
	},
	showTab : function(rule, args){
		var $tabs = pubsub.getJQObj(rule),
			param = "";
		$.each(rule,function(i,r){
			param = args[r.param]+"";
		});
		var params = param.split(",");
		$.each(params,function(i,p){
			$tabs.tabs("show",$.isNumeric(p)?parseInt(p):p);
		});
	},
	hideTab : function(rule, args){
		var $tabs = pubsub.getJQObj(rule),
			param = "";
		$.each(rule,function(i,r){
			param = args[r.param]+"";
		});
		var params = param.split(",");
		$.each(params,function(i,p){
			$tabs.tabs("hide",$.isNumeric(p)?parseInt(p):p);
		});
	},
	setTabName : function(rule,args){
		var $tabs = pubsub.getJQObj(rule);
		var $li = $tabs.find(".nav.nav-tabs li");
		$.each($li,function(i,item){
			if(args && args[i] != null){
				$.each($(item).find("span"),function(y,span){
					$(span).attr("class") != undefined ? null : $(span).text(args[i]);
				});
				;
			}
		});
	},
	setTabStatus : function(rule,args){
		var $tabs = pubsub.getJQObj(rule);
		var $li = $tabs.find(".nav.nav-tabs li");
		var status = null;
		$.each(args,function(k,v){
			status = v;
		})
		if(status != null){
			status = parseInt(status);
			$($li[status]).find("a:first-child").trigger("click");
		}
	},
	getActivedValue : function(rule,args){
		var $tabs = pubsub.getJQObj(rule,true);
		return $tabs.find("li.active:first>a").text();
	},
	getActivedIndex : function(rule,args){
		var $tabs = pubsub.getJQObj(rule,true);
		var $li = $tabs.find(".nav.nav-tabs li");
		var index = -1;
		$.each($li,function(i,item){
			if($(item).hasClass('active')){
				index = i+1;
				return false;
			}
		})
		return index;
	}
};
pubsub.sub("bootstraptabs", bootstraptabsFunc);/**
 * դ???ͳ
 */
var colmdFunc = {
	
};
pubsub.sub("colmd", colmdFunc);/**
 * դ???ͳ
 */
var carouselFunc = {
	linkage: function(rule,params,args){
		var $this = pubsub.getJQObj(rule);
		$this.carouselbt("loadData",$.isArray(params)?{}:params) ;
	},
	refresh: function(rule,params,args){
		var $this = pubsub.getJQObj(rule);
		$this.carouselbt("refresh") ;
	}
};
pubsub.sub("carousel", carouselFunc);/**
 * 树型网格
 */
var treelistbtFunc = {
	/**
	 * 插入静态数据
	 * @param {[type]} rule [规则]
	 * @param {[type]} args [输入形参]
	 * @param {[type]} obj  [description]
	 */
	insertStaticRow: function(rule,args,obj){
		var $id = pubsub.getJQObj(rule);
		var inparamobjrule = $id.data("inparamobjrule");
		if(!$id && !$id[0]){
			//无对象时跳过
			return;
		}
		//集合信息
		var arySource = inparamobjrule["arySource"];
		if($.isArray(arySource)){
			//数据转换为如下：集合：对象
			//{ary1495094362175: "obj1495077696961"}
			//若是数组，转换为json数据
			var arySourceArray = arySource;
			arySource = {};
			$.each(arySourceArray,function(i,item){
				var child = item.child;
				var objName = "";	//对象名称
				if(child && child[0]){
					objName = child[0].param;
				}
				arySource[item.param] = objName;
			})
			inparamobjrule["arySource"] = arySource;
		}
		// console.log(arySource);
		//对象信息
		var objSource = inparamobjrule["objSource"];
		if($.isArray(objSource)){
			//转换为如下数据，参数：映射字段
			// {
			// 	"obj1495077696961": {
			// 		"col1495077685661": "r_glafdb_tb3_0_r_glafdb_tb3_col9",
			// 		"col1495077685657": "r_glafdb_tb3_0_r_glafdb_tb3_col8",
			// 		"col1495077685651": "r_glafdb_tb3_0_r_glafdb_tb3_col7",
			// 		"col1495077685646": "r_glafdb_tb3_0_r_glafdb_tb3_col6",
			// 		"col1495077685642": "r_glafdb_tb3_0_r_glafdb_tb3_col5"
			// 	},
			// 	"obj1495077686857": {
			// 		"col1495077685625": "r_glafdb_tb3_0_treeid",
			// 		"col1495077685623": "r_glafdb_tb3_0_parent_id",
			// 		"col1495077685622": "r_glafdb_tb3_0_index_id",
			// 		"col1495077685620": "r_glafdb_tb3_0_topid",
			// 		"col1495077685617": "r_glafdb_tb3_0_id"
			// 	}
			// }
			
			//若是数组，则将数组转换为json数据
			var objSourceArray = objSource;
			objSource = {};
			$.each(objSourceArray,function(i,item){
				var child = JSON.parse(item.child);
				var param = {};
				$.each(child,function(i,item){
					param[item.param] = item.columnFiled;
				})
				objSource[item.param] = param;
			})
			inparamobjrule["objSource"] = objSource;
		}
		if(!objSource){
			//无对象信息时，无法插入
			return;
		}

		//寻找对象赋值
		var datas = [];
		var data = null;
		$.each(args,function(key,value){
			if($.isPlainObject(value) && !$.isEmptyObject(value)){
				//为对象时
				data = {};
				var nowInparamObj = objSource[key];
				$.each(value,function(key2,value2){
					if(nowInparamObj[key2]){
						//注释掉为"sss,ssss"的格式.
						// var value2Array = value2.split(",");
						// $.each(value2Array,function(i,item){
						// 	if(datas.length <= i){
						// 		datas.push({});
						// 	}
						// 	datas[i][nowInparamObj[key2]] = item;
						// })
						data[nowInparamObj[key2]] = value2;
					}
				})
				datas.push(data);
			}else if($.isArray(value)){
				//为集合时
				if(!arySource[key]){
					//集合中无对象信息
					return true;
				}

				var nowInparamObj = objSource[arySource[key]];
				$.each(value,function(i,item){
					data = {};
					$.each(item,function(key2,value2){
						if(nowInparamObj[key2]){
							data[nowInparamObj[key2]] = value2;
						}
					})
					datas.push(data);
				})
			}

		})

		if(datas && datas.length > 0){
			//grid插入数据
			$id.treelist("insertStaticRow",datas);
		}
		
	},
	getRow : function(rule, args, obj) {
		var dataItems = pubsub.getJQObj(rule,true).treelist("getSelectedRows"),val=[];
		if(dataItems && dataItems.length){
			$.each(dataItems,function(i,o){
				if(o[rule.columnName]){
					val.push(o[rule.columnName]||"");
					pubsub.outParamsObj.call(i,rule, obj, o);
				}
			});
			return val.join(",");
		}
		return "";
	},
	//获取当前点击行节点
	getCurRow: function(rule, args) {
		if (args && args[1]) {
			return args[1][rule.columnName] || "";
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
	getGrade : function(rule,args){
		var dataItems = pubsub.getJQObj(rule,true).treelist("getSelectedRows"),val=[];
		return ""+(dataItems[0]._level_+1);
	},
	selectRow : function(rule, args){
		var $id = pubsub.getJQObj(rule);
		var rows = $id.treelist("getData");

		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			if(r && r.param != 'key')
				v = args[r.param];
		}

		var key = args.key;
		key = key || 'id';
		var $id = pubsub.getJQObj(rule);
		var dataItems = $id.treelist("getData");
		var rowIndex = null;
		$.each(dataItems,function(i,item){
			if(item[key].indexOf(v) > -1){
				$id.treelist("select",item.id);
			}
		})

		// if (rows && rows.length) {
		// 	$.each(rows,function(i,row){
		// 		var idVal = args[rule[0].param];
		// 		if(idVal){
		// 			var  pas = idVal.split(",");
		// 			for(var j=0;j<pas.length;j++){
		// 				if(row.id == pas[j]){
		// 					$id.treelist("select",row.id);
		// 				}
		// 			}
		// 		}
		// 	});
		// }
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
	},
	//展开并刷新子节点
	refreshAndExpandNode : function(rule,args){
		var expandNodeId = args.expandNodeId;
		var key = args.key;
		var v = null;

		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			if(r && r.param != 'key')
				v = args[r.param];
		}

		key = key || 'id';
		var $id = pubsub.getJQObj(rule);
		var dataItems = $id.treelist("getData");
		var rowIndex = null;
		$.each(dataItems,function(i,item){
			if(item[key] == v){
				$id.treelist("refreshAndExpandNode",item);
			}
		})
	},
	
	//展开并刷新子节点的父节点
	parentRefresh : function(rule,args){
		var expandNodeId = args.expandNodeId;
		var key = args.key;
		var v = null;

		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			if(r && r.param != 'key')
				v = args[r.param];
		}

		key = key || 'id';
		var $id = pubsub.getJQObj(rule);
		var pItemNode = $id.treelist("getRow",$id.treelist("getNodeById",$id.treelist("getParentNodeId",$id.treelist("getSelections")),$id));
		pItemNode && $id.treelist("refreshAndExpandNode",pItemNode);
	},
	
	
	//修改当前节点内容
	nodeRefresh:function(rule,args){
		var expandNodeId = args.expandNodeId;
		var rename = args.rename;
		var text = args.text;
		var key = args.key;
		var v = null;

		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			if(r && r.param == 'expandNodeId')
				v = args[r.param];
		}

		key = key || 'id';
		var $id = pubsub.getJQObj(rule);
		var dataItems = $id.treelist("getData");
		var rowIndex = null;
		$.each(dataItems,function(i,item){
			if(item[key] == v){
				$id.treelist("nodeRefresh",item,rename,text);
			}
		})
		
	},
	
	
	expandNode:function(rule,args){
		var expandNodeId = args.expandNodeId;
		var key = args.key;
		var v = null;

		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			if(r && r.param != 'key')
				v = args[r.param];
		}

		key = key || 'id';
		var $id = pubsub.getJQObj(rule);
		var dataItems = $id.treelist("getData");
		var rowIndex = null;
		$.each(dataItems,function(i,item){
			if(item[key] == v){
				rowIndex = item["row-index"];
				$id.find("tr[row-index='"+rowIndex+"'] .treelist-expander").click();
			}
		})
	},
	//取消编辑事件
	endEdit: function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		$id.treelist("endEdit");
	},

	getKeyName : function(rule,args){
		return rule.columnName;
	},

	getCheckedRow: function(rule, args, obj) {
		var dataItems = pubsub.getJQObj(rule, true).treelist("getCheckedRowsData"),
			val = [];
		if (dataItems && dataItems.length) {
			$.each(dataItems, function(i, o) {
				if (o[rule.columnName]) {
					val.push(o[rule.columnName] || "");
					pubsub.outParamsObj(rule, obj, o);
				}
			});
			return val.join(",");
		}
		return "";
	}, 

	checkRow : function(rule, args){
		var $id = pubsub.getJQObj(rule);
		var rows = $id.treelist("getData");

		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			if(r && r.param != 'key')
				v = args[r.param];
		}

		var key = args.key;
		key = key || 'id';
		var $id = pubsub.getJQObj(rule);
		var dataItems = $id.treelist("getData");
		var rowIndex = null;
		$.each(dataItems,function(i,item){
			if(item[key] == v){
				$id.treelist("checkRow",null,null,item);
			}
		})
	}, 

	isCheckedInput: function(rule, args) {
		if (args && args[1]) {
			var $jq = pubsub.getJQObj(rule, true),
				g = $jq.data("grid");
			return g.tbody().find("tr:eq(" + args[1]["row-index"] + ") .inner_checkbox").prop("checked");
		}
		return false;
	},
	editRow: function(rule,args){
		var $id = pubsub.getJQObj(rule);
		var rows = $id.treelist("getData");

		var key = args.key;
		key = key || 'id';

		var value = "";
		$.each(rule,function(i,item){
			if(rule[i].param == 'key'){
				return true;
			}
			value = args[rule[i].param] || "";
		})

		if (rows && rows.length) {
			$.each(rows, function(i, row) {
				var idVal = value;
				if (idVal) {
					var pas = idVal.split(",");
					for (var j = 0; j < pas.length; j++) {
						if (row[key] == pas[j]) {
							if(row.id){
								$id.treelist("editRow", row.id);
							}else{
								$id.treelist("editRow", null,null,row);
							}
						}
					}
				}
			});
		}
		
		// var $id = pubsub.getJQObj(rule);
		// var rows = $id.treelist("getData");
		// if (rows && rows.length) {
		// 	$.each(rows, function(i, row) {
		// 		var idVal = args[rule[0].param];
		// 		if (idVal) {
		// 			var pas = idVal.split(",");
		// 			for (var j = 0; j < pas.length; j++) {
		// 				if (row.id == pas[j]) {
		// 					$id.treelist("editRow", row.id);
		// 				}
		// 			}
		// 		}
		// 	});
		// }
	},
};
pubsub.sub("treelistbt", treelistbtFunc);


/**
 * 网格
 */
var gridbtFunc = {
	/**
	 * 插入静态数据
	 * @param {[type]} rule [规则]
	 * @param {[type]} args [输入形参]
	 * @param {[type]} obj  [description]
	 */
	insertStaticRow: function(rule,args,obj){
		var $id = pubsub.getJQObj(rule);
		var inparamobjrule = $id.data("inparamobjrule");
		if(!$id && !$id[0]){
			//无对象时跳过
			return;
		}
		//集合信息
		var arySource = inparamobjrule["arySource"];
		if($.isArray(arySource)){
			//数据转换为如下：集合：对象
			//{ary1495094362175: "obj1495077696961"}
			//若是数组，转换为json数据
			var arySourceArray = arySource;
			arySource = {};
			$.each(arySourceArray,function(i,item){
				var child = item.child;
				var objName = "";	//对象名称
				if(child && child[0]){
					objName = child[0].param;
				}
				arySource[item.param] = objName;
			})
			inparamobjrule["arySource"] = arySource;
		}
		// console.log(arySource);
		//对象信息
		var objSource = inparamobjrule["objSource"];
		if($.isArray(objSource)){
			//转换为如下数据，参数：映射字段
			// {
			// 	"obj1495077696961": {
			// 		"col1495077685661": "r_glafdb_tb3_0_r_glafdb_tb3_col9",
			// 		"col1495077685657": "r_glafdb_tb3_0_r_glafdb_tb3_col8",
			// 		"col1495077685651": "r_glafdb_tb3_0_r_glafdb_tb3_col7",
			// 		"col1495077685646": "r_glafdb_tb3_0_r_glafdb_tb3_col6",
			// 		"col1495077685642": "r_glafdb_tb3_0_r_glafdb_tb3_col5"
			// 	},
			// 	"obj1495077686857": {
			// 		"col1495077685625": "r_glafdb_tb3_0_treeid",
			// 		"col1495077685623": "r_glafdb_tb3_0_parent_id",
			// 		"col1495077685622": "r_glafdb_tb3_0_index_id",
			// 		"col1495077685620": "r_glafdb_tb3_0_topid",
			// 		"col1495077685617": "r_glafdb_tb3_0_id"
			// 	}
			// }
			
			//若是数组，则将数组转换为json数据
			var objSourceArray = objSource;
			objSource = {};
			$.each(objSourceArray,function(i,item){
				var child = JSON.parse(item.child);
				var param = {};
				$.each(child,function(i,item){
					param[item.param] = item.columnFiled;
				})
				objSource[item.param] = param;
			})
			inparamobjrule["objSource"] = objSource;
		}
		if(!objSource){
			//无对象信息时，无法插入
			return;
		}
		//寻找对象赋值
		var datas = [];
		var data = null;
		$.each(args,function(key,value){
			if($.isPlainObject(value) && !$.isEmptyObject(value)){
				//为对象时
				data = {};
				var nowInparamObj = objSource[key];
				$.each(value,function(key2,value2){
					if(nowInparamObj[key2]){
						//注释掉为"sss,ssss"的格式.
						// var value2Array = value2.split(",");
						// $.each(value2Array,function(i,item){
						// 	if(datas.length <= i){
						// 		datas.push({});
						// 	}
						// 	datas[i][nowInparamObj[key2]] = item;
						// })
						data[nowInparamObj[key2]] = value2;
					}
				})
				datas.push(data);
			}else if($.isArray(value)){
				//为集合时
				if(!arySource[key]){
					//集合中无对象信息
					return true;
				}

				var nowInparamObj = objSource[arySource[key]];
				$.each(value,function(i,item){
					data = {};
					$.each(item,function(key2,value2){
						if(nowInparamObj[key2]){
							data[nowInparamObj[key2]] = value2;
						}
					})
					datas.push(data);
				})
			}

		})

		if(datas && datas.length > 0){
			//grid插入数据
			$id.grid("insertStaticRow",datas);
		}
		
	},
	getRow: function(rule, args, obj) {
		var dataItems = pubsub.getJQObj(rule, true).grid("getSelectedRows"),
		    item = pubsub.getJQObj(rule, true).data("grid"),
			val = [];
		if (dataItems && dataItems.length) {
			$.each(dataItems, function(i, o) {
				if (o[rule.columnName]) {
					var td = $(item.target).find("td[field="+rule.columnName+"]")[(o.row_number)-1];
					if($(td).find("input[type=radio]")[0] != undefined){
						$.each($(td).find("input[type=radio]"),function(i,j){
							 if(j.checked){
								 val.push(j.value || "");
							 }
						});
					}
					else{
						val.push(o[rule.columnName] || "");
						pubsub.outParamsObj.call(i,rule, obj, o);
					}
					
				}
			});
			return val.join(",");
		}
		return "";
	},
	getUnselectRow: function(rule, args) {
		var dataItems = pubsub.getJQObj(rule, true).grid("getUnselectedRows"),
			val = [];
		if (dataItems && dataItems.length) {
			$.each(dataItems, function(i, o) {
				if (o[rule.columnName]) {
					val.push(o[rule.columnName] || "");
				}
			});
			return val.join(",");
		}
		return "";
	},
	//获取当前点击行节点
	getCurRow: function(rule, args) {
		if (args && args[1]) {
			return args[1][rule.columnName] || "";
		}
		return "";
	},
	
	getText : function(rule,args){
		//当点击的单元格获取的非文本时
		var ret = '';
		var dataItems = pubsub.getJQObj(rule, true).data("grid");
		var td = $(dataItems.target).find("td[field="+args[1]+"]")[args[0]];
		if($(td).find("input[type=radio]")[0] != undefined){
			$.each($(td).find("input[type=radio]"),function(i,j){
				 if(j.checked){
					 ret = j.value
				 }
			});
		}
		else{
			ret = args[2]
		}
		return ret;
	},
	getCurColumn : function(rule, args){	
		var dataItems = pubsub.getJQObj(rule, true).data("grid");
		var obj = args[3];
		
		var item = dataItems.options.columns;
		var value = ""
		$.each(item,function(i,v){
			if(v.field == args[1]){
				value = v.title;
			}
		})
		return value;
	},
	isChecked: function(rule, args) {
		if (args && args[1]) {
			var $jq = pubsub.getJQObj(rule, true),
				g = $jq.data("grid");
			return g.tbody().find("tr:eq(" + args[1]["row-index"] + ")").hasClass(g.options.selectedCls);
		}
		return false;
	},
	getValue: function(rule, args) {
		return pubsub.getJQObj(rule, true).closest("a").text();
	},
	linkage: function(rule, params) {
		gridbtFunc.linkageControl(rule, params);
	},
	linkageControl: function(id, params) {
		params["databaseId"] && pubsub.getJQObj(id).attr("dbid", params["databaseId"]);
		var $id = pubsub.getJQObj(id);
		var options = $id.data("grid.options").options;
		if (options && options.pagination.page) {
			options.pagination.page = 1;
		}
		pubsub.getJQObj(id).grid("query", params);
	},
	selectRow: function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		var rows = $id.grid("getData");

		var key = args.key;
		key = key || 'id';

		var value = "";
		$.each(rule,function(i,item){
			if(rule[i].param == 'key'){
				return true;
			}
			value = args[rule[i].param] || "";
		})

		if (rows && rows.length) {
			$.each(rows, function(i, row) {
				
				var idVal = value;
				if (idVal) {
					var pas = idVal.split(",");
					for (var j = 0; j < pas.length; j++) {
						if (row[key] == pas[j]) {
							if(row.id){
								$id.grid("select", row.id);
							}else{
								$id.grid("select", null,null,row);
							}
						}
					}
				}
			});
		}
	},
	//根据传入参数勾选，grid中的对应的行的复选框的事件
	checkRecord:function(rule, args){
		var $id = pubsub.getJQObj(rule);
		var values=args[rule[0].param];
		
		var key = args.key;
		key = key || 'id';

		var rows = $id.grid("getData");
		if (rows && rows.length) {
			$.each(rows, function(i, row) {
				var idVal = args[rule[0].param];
				if (idVal) {
					var pas = idVal.split(",");
					for (var j = 0; j < pas.length; j++) {
						if (row[key] == pas[j]) {
							if(row.id){
								$id.grid("checkRow", row.id);
							}else{
								$id.grid("checkRow", null,null,row);
							}
						}
					}
				}
			});
		}
	},
	cancelSelect: function(rule, args) {
		var $id = pubsub.getJQObj(rule).grid("cancelSelect");
	},
	getAll: function(rule, args) {
		var $in = pubsub.getJQObj(rule, /*args[0] ||*/ true);
		if ($in) {
			var retAry = [],
				dataItems = $in.data("grid").getData(),
				dataItem;
			if (dataItems) {
				for (var i = 0; i < dataItems.length; i++) {
					dataItem = dataItems[i];
					retAry.push(dataItem[rule.columnName] || "");
				}
				return retAry.join(",");
			}
		}
		return "";
	},
	setRowBgColor: function(rule, args) {
		pubsub.getJQObj(rule).grid("setRowBgColor", args.rowIndex, args.color);
	},
	tSelectFirst: function(rule, args) { //默认选中第一行
		var $id = pubsub.getJQObj(rule);
		if ($id) {
			setTimeout(function() {
				$id.grid("select", "", "tr:eq(0)");
				var ink = new MutationObserver(function(record) {
					$id.grid("select", "", "tr:eq(0)");
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
		$id.grid("query", $.isArray(args) ? {} : args);
	},
	gsaveChange: function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		$id.grid("saveHandle");
	},
	getTotal: function(rule, args) {
		return pubsub.getJQObj(rule, true).grid("getTotal") || 0;
	},
	//取消编辑事件
	endEdit: function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		$id.grid("endEdit");
	},
	//隐藏工具栏
	hideToolbar: function(rule,args){
		var $id = pubsub.getJQObj(rule);
		$id.find(".grid-toolbar").hide();
	},
	//显示工具栏
	showToolbar: function(rule,args){
		var $id = pubsub.getJQObj(rule);
		$id.find(".grid-toolbar").show();
	},
	editRow: function(rule,args){
		var $id = pubsub.getJQObj(rule);
		var rows = $id.grid("getData");

		var key = args.key;
		key = key || 'id';

		var value = "";
		$.each(rule,function(i,item){
			if(rule[i].param == 'key'){
				return true;
			}
			value = args[rule[i].param] || "";
		})

		if (rows && rows.length) {
			$.each(rows, function(i, row) {
				var idVal = value;
				if (idVal) {
					var pas = idVal.split(",");
					for (var j = 0; j < pas.length; j++) {
						if (row[key] == pas[j]) {
							if(row.id){
								$id.grid("editRow", row.id);
							}else{
								$id.grid("editRow", null,null,row);
							}
							// $id.grid("editRow", row.id);
						}
					}
				}
			});
		}
	},
	addRow: function(rule,args){
		var $id = pubsub.getJQObj(rule);
		$id.grid("_newRow");
		
	},
	getCheckedRow: function(rule, args, obj) {
		var dataItems = pubsub.getJQObj(rule, true).grid("getCheckedRowsData"),
			val = [];
		if (dataItems && dataItems.length) {
			$.each(dataItems, function(i, o) {
				if (o[rule.columnName]) {
					val.push(o[rule.columnName] || "");
					pubsub.outParamsObj.call(i,rule, obj, o);
				}
			});
			return val.join(",");
		}
		return "";
	}, 

	isCheckedInput: function(rule, args) {
		if (args && args[1]) {
			var $jq = pubsub.getJQObj(rule, true),
				g = $jq.data("grid");
			return g.tbody().find("tr:eq(" + args[1]["row-index"] + ") .inner_checkbox").prop("checked");
		}
		return false;
	},
	getKeyName : function(rule,args){
		return rule.columnName;
	},

	//设置行不可编辑状态
	setEditDisabled : function(rule,args){
		var $id = pubsub.getJQObj(rule);
		var rows = $id.grid("getData");

		var key = args.key;
		key = key || 'id';

		var value = "";
		$.each(rule,function(i,item){
			if(rule[i].param == 'key'){
				return true;
			}
			value = args[rule[i].param] || "";
		})

		if (rows && rows.length) {
			$.each(rows, function(i, row) {
				
				var idVal = value;
				if (idVal) {
					var pas = idVal.split(",");
					for (var j = 0; j < pas.length; j++) {
						if (row[key] == pas[j]) {
							if(row.id){
								$id.grid("setEditDisabled", row[key]);
							}else{
								$id.grid("setEditDisabled", null,null,row);
							}
						}
					}
				}
			});
		}
	},

	//设置行可编辑状态
	setEditEnabled : function(rule,args){
		var $id = pubsub.getJQObj(rule);
		var rows = $id.grid("getData");

		var key = args.key;
		key = key || 'id';

		var value = "";
		$.each(rule,function(i,item){
			if(rule[i].param == 'key'){
				return true;
			}
			value = args[rule[i].param] || "";
		})

		if (rows && rows.length) {
			$.each(rows, function(i, row) {
				
				var idVal = value;
				if (idVal) {
					var pas = idVal.split(",");
					for (var j = 0; j < pas.length; j++) {
						if (row[key] == pas[j]) {
							if(row.id){
								$id.grid("setEditEnabled", row[key]);
							}else{
								$id.grid("setEditEnabled", null,null,row);
							}
						}
					}
				}
			});
		}
	}
};

pubsub.sub("gridbt", gridbtFunc);var chartsFunc = {
	init: function(rule, args) {
		var options = args[0];
		options._querydata = getParams(rule.inid) ? JSON.stringify(getParams(rule.inid)) : null;
		$.ajax({
			type: "POST",
			url: contextPath + "/mx/form/charts/datas",
			contentType: "application/json",
			dataType: "json",
			data: JSON.stringify({
				'rid': options.rid,
				'params': options._querydata
			}),
			success: function(msg) {
				var series = chartsFunc.transform(msg, options),
					$this = $('#' + rule.inid),
					
				sersLens = [];
				options.series = series;
				if(options.pieInnerSize != undefined && options.pieInnerSize != ""){
					options.series[0].innerSize = options.pieInnerSize;
				} 
				if(options.chart.type == 'pie'){
					options.tooltip.pointFormat = '{series.name}: <b>{point.y}个({point.percentage:.1f}%)</b>';
				} 
				$.each(series, function(i, v) {
					v.data.length > 1000 && sersLens.push(v.data.length);
					
				});
				
					
				if (sersLens.length) {
					options.plotOptions.series.turboThreshold = Math.max.apply(Math, sersLens);
				}
				if ($this.data("clickEvent")) {
					options.plotOptions.series.point.events.click = $this.data("clickEvent");
				}
				$this.highcharts(options);
				if (options.rotate3D) {
					chartsFunc.rotate3D(rule.inid);
				}
				if ($this.resize) {
					$this.resize(function(width,height,flag) {
						$this.highcharts().setSize($this.width(), $this.height(), true);
						// if(!width){
						// 	width = $this.width();
						// }
						// if(!height){
						// 	height = $this.height();
						// }
						// if(flag == null){
						// 	flag = true;
						// }
						// if(typeof width == 'object'){
						// 	$this.highcharts().setSize($this.width(), $this.height(), true);	
						// }else{
						// 	$this.highcharts().setSize(width, height, flag);	
						// }
					});
				}
			}
		});
	},
	tshow: function(rule, args) { // 显示
		var mtcharts = pubsub.getJQObj(rule)
		// mtcharts.highcharts().setSize(100, 100, true);
		mtcharts.show();
		// mtcharts.highcharts().setSize(width, height, true);
	},
	getRow: function(rule, args) {
		// $("#" + rule.inid).highcharts();
		var data = args[1];
		if (rule.columnName == "categories") { //系列名称
			var $id = pubsub.getJQObj(rule,true);
			var type = $id.highcharts().options.chart.type;
			if(type == 'pie'){
				return data.name;
			}

			return (data.series && data.series.name) || "";
		}
		return data[rule.columnName];
	},
	fresh: function(id,params,args){
		var $id = pubsub.getJQObj(id);
		var $highcharts = $id.highcharts();
		if ($highcharts) {
			var options = $id.highcharts().options;
			$.ajax({
				type: "POST",
				url: contextPath + "/mx/form/charts/datas",
				contentType: "application/json",
				dataType: "json",
				data:  JSON.stringify({
					'rid': options.rid,
					'params': options._querydata
				}),
				success: function(msg) {
					var series = chartsFunc.transform(msg, options);
					options.series = series;
					$id.highcharts(options);
				}
			});
		}
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
					if(options.pieInnerSize != undefined && options.pieInnerSize != ""){
						options.series[0].innerSize = options.pieInnerSize;
					} 
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
		var subserie = {},
			chartType = rule.chartType || rule._chartType,
			isScatter = chartType == "scatter",
			isBubble = chartType == "bubble";
		for (var p in data) {
			if (rule.xAxisName && p == rule.xAxisName.en) {
				subserie.name = data[p];
				if (rule.xAxisName.type == "datetime") {
					subserie.name = chartsFunc._formatTime(data[p], rule.tf);
				}
				if (isScatter || isBubble) {
					var x = parseFloat(+data[p]);
					subserie.x = parseFloat(x.toFixed(2));
				}
			} else if (p == rule.axisName.en) {
				/*var y = parseFloat(data[p]);
				subserie.y = parseFloat(y.toFixed(2));*/
				if (data[p]) {
					var y = parseFloat(+data[p]);
					subserie.y = parseFloat(y.toFixed(2));
				} else {
					subserie.y = null;
				}
			} else if (isBubble && rule.zAxisName && p == rule.zAxisName.en) {
				subserie.zoneAxis = "z";
				if (data[p]) {
					var z = parseFloat(+data[p]);
					subserie.z = parseFloat(z.toFixed(2));
				} else {
					subserie.z = null;
				}
			}
			subserie[p] = data[p];
		}
		return subserie;
	},
	_buildSubserie: function(data, rule, yan) { // 动态列
		var subserie = {},
			chartType = rule.chartType || rule._chartType,
			isScatter = chartType == "scatter",
			isBubble = chartType == "bubble";
		for (var p in data) {
			if (rule.xAxisName && p == rule.xAxisName.en) {
				subserie.name = data[p];
				if (rule.xAxisName.type == "datetime") {
					subserie.name = chartsFunc._formatTime(data[p], rule.tf);
				}
				if (isScatter || isBubble) {
					var x = parseFloat(+data[p]);
					subserie.x = parseFloat(x.toFixed(2));
				}
			} else if (p == yan.en) {
				if (data[p]) {
					var y = parseFloat(+data[p]);
					subserie.y = parseFloat(y.toFixed(2));
				} else {
					subserie.y = null;
				}
			} else if (isBubble && rule.zAxisName && p == rule.zAxisName.en) {
				subserie.zoneAxis = "z";
				if (data[p]) {
					var z = parseFloat(+data[p]);
					subserie.z = parseFloat(z.toFixed(2));
				} else {
					subserie.z = null;
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
	_setShowValueAndPoint: function(serie, rule) {
		if (rule.markerEnable) {
			serie.marker = serie.marker || {};
			serie.marker.enabled = rule.markerEnable && rule.markerEnable == 'true' ? true : false;
		}
		if (rule.dataLabelsEnable) {
			serie.dataLabels = serie.dataLabels || {};
			serie.dataLabels.enabled = rule.dataLabelsEnable && rule.dataLabelsEnable == 'true' ? true : false;
		}
	},
	/**
	 * 计算表达式
	 * @param  {[type]} data      [数据来源]
	 * @param  {[type]} format    [表达式]
	 * @param  {[type]} yan 	  [y轴的信息]
	 * @param  {[type]} datasSize [总数]
	 * @return {[type]}           [返回Str]
	 */
	_convertExpToStr : function(exp,data,yan,datasSize){
		var reg = /{[\w|\.]+}/g;
		var columns = exp.match(reg);
		$.each(columns,function(k,column){
			if(column == '{point.name}'){
				//获取系列名称
				exp = exp.replace(column,yan.cn);
			}else if(column == "{y}"){
				//获取值名称
				exp = exp.replace(column,data[yan.en]);
			}else if(column == "{total}"){
				//总数
				exp = exp.replace(column,datasSize);
			}
		});
		return exp;
	},
	_groupBySeries: function(datas, rule, ind, opts) { // 根据系列分组
		var series = [],
			serie, subseries, subserie, data, datasSize = datas.length,
			yans = rule.yAxisName,
			yansSize = yans != undefined ? yans.length : 0,
			an = rule.axisName,
			tsize = rule.tSize,
			tname = rule.tName,
			tid = rule.tid,
			tparent = rule.tParent,
			tcolor = rule.tColor,
			yan, xan = rule.xAxisName,
			yAxisLength = opts.yAxis.length > 1;
		rule._chartType = opts.chart.type;
		rule.pieInnerSize = opts.pieInnerSize;
		// 静态系列
		if(yans != undefined){
		if (yans.length == 1 && an != undefined && an.en != "") {
			for (var i = 0; i < datasSize; i++) {
				data = datas[i];
				var yAxisName = data[yans[0].en];
				if (yans[0].type == "datetime") {
					yAxisName = chartsFunc._formatTime(yAxisName, rule.tf);
				}
				/*if(tsize != undefined)
					data['value'] = parseInt(data[tsize.en]);					
				if(tcolor != undefined)
					data['color'] = data[tcolor.en];
				if(tname != undefined)
					data['name'] = data[tname.en];
				if(tid != undefined)
					data['id'] = data[tid.en];
				if(tparent != undefined){
					if(opts.istree)
						data['parent'] = data[tparent.en];					
				}*/
					// var xAxisName = data[xan.en]; 系列名
				var hasSerie = chartsFunc._hasYAxisName(series, yAxisName);
				if (hasSerie) { // 如果包含
					var subserie = chartsFunc._staticBuildSubserie(data, rule);
					//subserie.color = '#000';
					var formatColor =serie.dataLabels.formatColor;
					if(formatColor){
						var boo = "";
						try{
							boo = JSON.parse(formatColor)
						}catch(e){
							
						}
						if(boo){
							var strExp = "";
							$.each(boo,function(i,item){
								var expression = item.expression;
								// chartsFunc._evalExp(expression,data,serie.name);
								var color = item.color;
								var exp = chartsFunc._convertExpToStr(expression,data,yan,datasSize);
								if(eval(exp)){
									//计算表达式并返回字符串
									// serie.dataLabels.format = chartsFunc._convertExpToStr(htmlExpression,data,yan,datasSize);
									subserie.color = color;
								}
							})
						}
					}
					
					hasSerie.data.push(subserie);
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

					serie.dataLabels = serie.dataLabels || $.extend(true,{},opts.plotOptions.series.dataLabels) || {};
					//获取表达式模板中的信息
					var formatExp = serie.dataLabels.formatExp;
					if(formatExp && formatExp.length > 0){
						var strExp = "";
						$.each(formatExp,function(i,item){
							var expression = item.expression;
							// chartsFunc._evalExp(expression,data,serie.name);
							var htmlExpression = item.htmlExpression;
							var exp = chartsFunc._convertExpToStr(expression,data,yan,datasSize);
							if(eval(exp)){
								//计算表达式并返回字符串
								// serie.dataLabels.format = chartsFunc._convertExpToStr(htmlExpression,data,yan,datasSize);
								serie.dataLabels.format = htmlExpression;
							}
						})
					}
					
					var formatColor =serie.dataLabels.formatColor;
					if(formatColor){
						var boo = "";
						try{
							boo = JSON.parse(formatColor)
						}catch(e){
							
						}
						if(boo){
							var strExp = "";
							$.each(boo,function(i,item){
								var expression = item.expression;
								// chartsFunc._evalExp(expression,data,serie.name);
								var color = item.color;
								var exp = chartsFunc._convertExpToStr(expression,data,yan,datasSize);
								if(eval(exp)){
									//计算表达式并返回字符串
									// serie.dataLabels.format = chartsFunc._convertExpToStr(htmlExpression,data,yan,datasSize);
									subserie.color = color;
								}
							})
						}
					}
					
					chartsFunc._setShowValueAndPoint(serie, rule);

					/*
					 * if(serie.name == "0"){ serie.stack =ind+ "我的" ; }else{
					 * serie.stack =ind+ "你的" ; }
					 */
					subseries = [];
					subserie = chartsFunc._staticBuildSubserie(data, rule);
					var formatColor =serie.dataLabels.formatColor;
					if(formatColor){
						var formatc ="";
						try{
							formatc = JSON.parse(formatColor)
						}catch(e){
						}
						if(formatc){
							var strExp = "";
							$.each(formatc,function(i,item){
								var expression = item.expression;
								var color = item.color;
								var exp = chartsFunc._convertExpToStr(expression,data,yan,datasSize);
								if(eval(exp)){
									//计算表达式并返回字符串
									// serie.dataLabels.format = chartsFunc._convertExpToStr(htmlExpression,data,yan,datasSize);
									subserie.color = color;
								}
							})
						}
					}
				
					subseries.push(subserie);
					serie.data = subseries;
                    
					if (opts.pieInnerSize) {
						serie.innerSize = opts.pieInnerSize;
					}
					if ((serie.type == "pie" || (!serie.type && opts.chart.type == "pie")) && opts.pieInnerSize) {
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
					/*if(tsize != undefined)
						data['value'] = parseInt(data[tsize.en]);					
					if(tcolor != undefined)
						data['color'] = data[tcolor.en];
					if(tname != undefined)
						data['name'] = data[tname.en];
					if(tid != undefined)
						data['id'] = data[tid.en];
					if(tparent != undefined){
						if(opts.istree)
							data['parent'] = data[tparent.en];					
					}*/
					if (hasSerie) {
//						hasSerie.data.push(chartsFunc._buildSubserie(data, rule, yan));
						var subserie = chartsFunc._buildSubserie(data, rule, yan);
						//subserie.color = '#000';
						var formatColor =serie.dataLabels.formatColor;
						if(formatColor){
							var boo = "";
							try{
								boo = JSON.parse(formatColor)
							}catch(e){
								
							}
							if(boo){
								var strExp = "";
								$.each(boo,function(i,item){
									var expression = item.expression;
									// chartsFunc._evalExp(expression,data,serie.name);
									var color = item.color;
									var exp = chartsFunc._convertExpToStr(expression,data,yan,datasSize);
									if(eval(exp)){
										//计算表达式并返回字符串
										// serie.dataLabels.format = chartsFunc._convertExpToStr(htmlExpression,data,yan,datasSize);
										subserie.color = color;
									}
								})
							}
						}
						hasSerie.data.push(subserie);
					} else {
						serie = {};
						if (yAxisLength) {
							serie.yAxis = ind;
						}
						serie.name = yan.cn;
						serie.type = rule.chartType;

						serie.dataLabels = serie.dataLabels || $.extend(true,{},opts.plotOptions.series.dataLabels) || {};
						//获取表达式模板中的信息
						var formatExp = serie.dataLabels.formatExp;
						if(formatExp && formatExp.length > 0){
							var strExp = "";
							$.each(formatExp,function(i,item){
								var expression = item.expression;
								// chartsFunc._evalExp(expression,data,serie.name);
								var htmlExpression = item.htmlExpression;
								var exp = chartsFunc._convertExpToStr(expression,data,yan,datasSize);
								if(eval(exp)){
									//计算表达式并返回字符串
									// serie.dataLabels.format = chartsFunc._convertExpToStr(htmlExpression,data,yan,datasSize);
									serie.dataLabels.format = htmlExpression;
								}
							})
						}
						

						chartsFunc._setShowValueAndPoint(serie, rule);
						if (rule.stack) {
							serie.stack = data[rule.stack];
						}
						subseries = [];
						subserie = chartsFunc._buildSubserie(data, rule, yan);
						
						var formatColor =serie.dataLabels.formatColor;
						if(formatColor){
							var boo = "";
							try{
								boo = JSON.parse(formatColor)
							}catch(e){
								
							}
							if(boo){
								var strExp = "";
								$.each(boo,function(i,item){
									var expression = item.expression;
									// chartsFunc._evalExp(expression,data,serie.name);
									var color = item.color;
									var exp = chartsFunc._convertExpToStr(expression,data,yan,datasSize);
									if(eval(exp)){
										//计算表达式并返回字符串
										// serie.dataLabels.format = chartsFunc._convertExpToStr(htmlExpression,data,yan,datasSize);
										subserie.color = color;
									}
								})
							}
						}
						
						//subserie.color = "#000";
						subseries.push(subserie);
						serie.data = subseries; //颜色存放地点
						if (opts.colorByPoint) {
							serie.colorByPoint = true; //根据点自动分配颜色
						}
						if ((serie.type == "pie" || (!serie.type && opts.chart.type == "pie")) && opts.pieInnerSize) {
							serie.innerSize = opts.pieInnerSize;
						}
						series.push(serie);
					}
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
	_hastreeName: function(series, name) {
		var seriesSize = series.length,
			serie;
		if (seriesSize) {
			for (var i = 0; i < seriesSize; i++) {
				serie = series[i];
				if (serie.name == name) {
					return serie;
				}
			}
		}
		return null;
	},
	_buildtreemap : function(rule,datas,opts){
		var tsize = rule.tSize,
			tname = rule.tName,
			tid = rule.tid,
			tparent = rule.tParent,
			tcolor = rule.tColor,
			datasSize = datas.length,
			subserie,series = [],colorCount = 0,
			color = ['#d94e5d','#eac736','#50a3ba','#404a59','#323c48','#a6c84c', '#ffa022', '#46bee9'];
		for (var i = 0; i < datasSize; i++) {
			    data = datas[i];	
			    var hasSerie = chartsFunc._hastreeName(series,"treemap")
				if(rule.yAxisName != undefined)
					data['value'] = parseInt(data[rule.yAxisName[0].en]);
					
				if(rule.xAxisName != undefined)
					data['name'] = data[rule.xAxisName.en];
				if(tid != undefined)
					data['id'] = data[tid.en];
				if(tparent != undefined){
					if(opts.istree)
						data['parent'] = data[tparent.en];					
				}
				colorCount = colorCount < color.length-1 ? colorCount+1 : 0; 
				data['color'] = color[colorCount];
				if (hasSerie) {
					hasSerie.data.push(data);
				}
				else{
				serie = {};
				serie.type = rule.chartType;
				serie.name = "treemap";
				serie.layoutAlgorithm = 'stripes';
				serie.alternateStartingDirection = true;
				var levels = [{
					level: 1,
	                layoutAlgorithm: 'sliceAndDice',
	                dataLabels: {
	                    enabled: true,
	                    align: 'left',
	                    verticalAlign: 'top',
	                    style: {
	                        fontSize: '15px',
	                        fontWeight: 'bold'
	                    }
	                }
				}]
				if(!opts.istree){
					levels[0].dataLabels.verticalAlign = "middle";
					levels[0].dataLabels.align = "center";
				}
				serie.levels = levels;	
				chartsFunc._setShowValueAndPoint(serie, rule);
				if (rule.stack) {
					serie.stack = data[rule.stack];
				}
				subseries = [];
				subseries.push(data);
				serie.data = subseries;
		    	if (opts.pieInnerSize) {
					serie.innerSize = opts.pieInnerSize;
				}				
				   series.push(serie);
				}
		}
		return series;
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
					if (opts.chart.type == "treemap") { // 多饼图数据合并处理
						serie = chartsFunc._buildtreemap(rule,data.data,opts);
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
	changeSeriesValue: function(rule, args) {
		var mtcharts = pubsub.getJQObj(rule);
		var mtcharts_hightchart = mtcharts.highcharts();
		var series = mtcharts_hightchart.series;
		// var name = 
		$.each(args, function(key, value) {

			$.each(series, function(i, item) {
				if (item.name == key) {
					var valArray = [];
					$.each(item.data, function(k, kitem) {
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
} catch (ex) {}var customFunc = {
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
					var onLoadSucessFunc =jq.data("onLoadSucess");
					if($.isFunction(onLoadSucessFunc)){
						onLoadSucessFunc();
					}
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
					var onLoadSucessFunc = jq.data("onLoadSucess");
					if($.isFunction(onLoadSucessFunc)){
						onLoadSucessFunc();
					}
					return;
				}
			}
		}
	}
};
pubsub.sub("custom", customFunc);/**
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
	getRandomparent: function(rule){
		var $id = pubsub.getJQObj(rule, true);
		return $id.attr("randomparent");
	},
	getValue: function(rule){
		var $id = pubsub.getJQObj(rule, true);
		return $id.attr("attachmentId");
	},
	setImgPic:function(rule,args){
		
		var $elementObj = pubsub.getJQObj(rule),r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
		} else {
			v = args;
		}
		var $img = $elementObj.find(".imageupload_class");
		$img[0].onload = function() {
	        EXIF.getData($img[0], function() {
	            $(this).data("tags",EXIF.getAllTags(this));
	        });
	    };
		var url = '/glaf/mx/form/imageUpload?method=downloadById&_'+(new Date().getTime())+'&id='+v;
		$elementObj.attr("attachmentid",v);
		$img.attr(
			"src",
			url)
		.show();
	},
	setRandomParent:function(rule,args){
		
		var $elementObj = pubsub.getJQObj(rule),r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
		} else {
			v = args;
		}
		var $img = $elementObj.find(".imageupload_class");
		if(v == null || v == ""){
			$img.hide();
			var randomUUID = new UUID().createUUID();
			$elementObj.attr("randomparent",randomUUID);
			$elementObj.removeAttr("attachmentId");
			return ;
		}
		$elementObj.attr("randomparent",v);
		// $img[0].onload = function() {
	 //        EXIF.getData($img[0], function() {
	 //            $(this).data("tags",EXIF.getAllTags(this));
	 //        });
	 //    };
		//8243d27f19e041df8d8225261a788c0b
		var downloadUrl = $elementObj.attr("downloadUrl");
		$img[0].onload = function() {
	        EXIF.getData($img[0], function() {
	            $(this).data("tags",EXIF.getAllTags(this));
	        });
	    };
		$img.attr("src",  downloadUrl + "&_"+(new Date().getTime())+"&randomparent=" + v).show();
	},
	getBasePicData: function(rule,args){
		var $id = pubsub.getJQObj(rule, true);
		var tags = $id.find(".imageupload_class").data("tags");
		return tags[rule.columnName];
	},
	clear : function(rule,args){
		var $elementObj = pubsub.getJQObj(rule),r, v = "";
		var $img = $elementObj.find(".imageupload_class");
		$img.hide();
		var randomUUID = new UUID().createUUID();
		$elementObj.attr("randomparent",randomUUID);
		$elementObj.attr("attachmentId","");
		// $elementObj.removeAttr("attachmentId");
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
	// imageUploadInitFunc();
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
		if($img[0]){
			$img[0].onload = function() {
		        EXIF.getData($img[0], function() {
		            $(this).data("tags",EXIF.getAllTags(this));
		        });
		    };
		}
	    
		if (flag) {
			$img.show();
		}
	}
}/**
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
pubsub.sub("a", aFunc);var chartsFunc = {
	init: function(rule, args) {
		var options = args[0];
		options._querydata = getParams(rule.inid) ? JSON.stringify(getParams(rule.inid)) : null;
		$.ajax({
			type: "POST",
			url: contextPath + "/mx/form/charts/datas",
			contentType: "application/json",
			dataType: "json",
			data: JSON.stringify({
				'rid': options.rid,
				'params': options._querydata
			}),
			success: function(msg) {
				var series = chartsFunc.transform(msg, options),
					$this = $('#' + rule.inid),
					
				sersLens = [];
				options.series = series;
				if(options.pieInnerSize != undefined && options.pieInnerSize != ""){
					options.series[0].innerSize = options.pieInnerSize;
				} 
				if(options.chart.type == 'pie'){
					options.tooltip.pointFormat = '{series.name}: <b>{point.y}个({point.percentage:.1f}%)</b>';
				} 
				$.each(series, function(i, v) {
					v.data.length > 1000 && sersLens.push(v.data.length);
					
				});
				
					
				if (sersLens.length) {
					options.plotOptions.series.turboThreshold = Math.max.apply(Math, sersLens);
				}
				if ($this.data("clickEvent")) {
					options.plotOptions.series.point.events.click = $this.data("clickEvent");
				}
				$this.highcharts(options);
				if (options.rotate3D) {
					chartsFunc.rotate3D(rule.inid);
				}
				if ($this.resize) {
					$this.resize(function(width,height,flag) {
						$this.highcharts().setSize($this.width(), $this.height(), true);
						// if(!width){
						// 	width = $this.width();
						// }
						// if(!height){
						// 	height = $this.height();
						// }
						// if(flag == null){
						// 	flag = true;
						// }
						// if(typeof width == 'object'){
						// 	$this.highcharts().setSize($this.width(), $this.height(), true);	
						// }else{
						// 	$this.highcharts().setSize(width, height, flag);	
						// }
					});
				}
			}
		});
	},
	tshow: function(rule, args) { // 显示
		var mtcharts = pubsub.getJQObj(rule)
		// mtcharts.highcharts().setSize(100, 100, true);
		mtcharts.show();
		// mtcharts.highcharts().setSize(width, height, true);
	},
	getRow: function(rule, args) {
		// $("#" + rule.inid).highcharts();
		var data = args[1];
		if (rule.columnName == "categories") { //系列名称
			var $id = pubsub.getJQObj(rule,true);
			var type = $id.highcharts().options.chart.type;
			if(type == 'pie'){
				return data.name;
			}

			return (data.series && data.series.name) || "";
		}
		return data[rule.columnName];
	},
	fresh: function(id,params,args){
		var $id = pubsub.getJQObj(id);
		var $highcharts = $id.highcharts();
		if ($highcharts) {
			var options = $id.highcharts().options;
			$.ajax({
				type: "POST",
				url: contextPath + "/mx/form/charts/datas",
				contentType: "application/json",
				dataType: "json",
				data:  JSON.stringify({
					'rid': options.rid,
					'params': options._querydata
				}),
				success: function(msg) {
					var series = chartsFunc.transform(msg, options);
					options.series = series;
					$id.highcharts(options);
				}
			});
		}
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
					if(options.pieInnerSize != undefined && options.pieInnerSize != ""){
						options.series[0].innerSize = options.pieInnerSize;
					} 
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
		var subserie = {},
			chartType = rule.chartType || rule._chartType,
			isScatter = chartType == "scatter",
			isBubble = chartType == "bubble";
		for (var p in data) {
			if (rule.xAxisName && p == rule.xAxisName.en) {
				subserie.name = data[p];
				if (rule.xAxisName.type == "datetime") {
					subserie.name = chartsFunc._formatTime(data[p], rule.tf);
				}
				if (isScatter || isBubble) {
					var x = parseFloat(+data[p]);
					subserie.x = parseFloat(x.toFixed(2));
				}
			} else if (p == rule.axisName.en) {
				/*var y = parseFloat(data[p]);
				subserie.y = parseFloat(y.toFixed(2));*/
				if (data[p]) {
					var y = parseFloat(+data[p]);
					subserie.y = parseFloat(y.toFixed(2));
				} else {
					subserie.y = null;
				}
			} else if (isBubble && rule.zAxisName && p == rule.zAxisName.en) {
				subserie.zoneAxis = "z";
				if (data[p]) {
					var z = parseFloat(+data[p]);
					subserie.z = parseFloat(z.toFixed(2));
				} else {
					subserie.z = null;
				}
			}
			subserie[p] = data[p];
		}
		return subserie;
	},
	_buildSubserie: function(data, rule, yan) { // 动态列
		var subserie = {},
			chartType = rule.chartType || rule._chartType,
			isScatter = chartType == "scatter",
			isBubble = chartType == "bubble";
		for (var p in data) {
			if (rule.xAxisName && p == rule.xAxisName.en) {
				subserie.name = data[p];
				if (rule.xAxisName.type == "datetime") {
					subserie.name = chartsFunc._formatTime(data[p], rule.tf);
				}
				if (isScatter || isBubble) {
					var x = parseFloat(+data[p]);
					subserie.x = parseFloat(x.toFixed(2));
				}
			} else if (p == yan.en) {
				if (data[p]) {
					var y = parseFloat(+data[p]);
					subserie.y = parseFloat(y.toFixed(2));
				} else {
					subserie.y = null;
				}
			} else if (isBubble && rule.zAxisName && p == rule.zAxisName.en) {
				subserie.zoneAxis = "z";
				if (data[p]) {
					var z = parseFloat(+data[p]);
					subserie.z = parseFloat(z.toFixed(2));
				} else {
					subserie.z = null;
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
	_setShowValueAndPoint: function(serie, rule) {
		if (rule.markerEnable) {
			serie.marker = serie.marker || {};
			serie.marker.enabled = rule.markerEnable && rule.markerEnable == 'true' ? true : false;
		}
		if (rule.dataLabelsEnable) {
			serie.dataLabels = serie.dataLabels || {};
			serie.dataLabels.enabled = rule.dataLabelsEnable && rule.dataLabelsEnable == 'true' ? true : false;
		}
	},
	/**
	 * 计算表达式
	 * @param  {[type]} data      [数据来源]
	 * @param  {[type]} format    [表达式]
	 * @param  {[type]} yan 	  [y轴的信息]
	 * @param  {[type]} datasSize [总数]
	 * @return {[type]}           [返回Str]
	 */
	_convertExpToStr : function(exp,data,yan,datasSize){
		var reg = /{[\w|\.]+}/g;
		var columns = exp.match(reg);
		$.each(columns,function(k,column){
			if(column == '{point.name}'){
				//获取系列名称
				exp = exp.replace(column,yan.cn);
			}else if(column == "{y}"){
				//获取值名称
				exp = exp.replace(column,data[yan.en]);
			}else if(column == "{total}"){
				//总数
				exp = exp.replace(column,datasSize);
			}
		});
		return exp;
	},
	_groupBySeries: function(datas, rule, ind, opts) { // 根据系列分组
		var series = [],
			serie, subseries, subserie, data, datasSize = datas.length,
			yans = rule.yAxisName,
			yansSize = yans != undefined ? yans.length : 0,
			an = rule.axisName,
			tsize = rule.tSize,
			tname = rule.tName,
			tid = rule.tid,
			tparent = rule.tParent,
			tcolor = rule.tColor,
			yan, xan = rule.xAxisName,
			yAxisLength = opts.yAxis.length > 1;
		rule._chartType = opts.chart.type;
		rule.pieInnerSize = opts.pieInnerSize;
		// 静态系列
		if(yans != undefined){
		if (yans.length == 1 && an != undefined && an.en != "") {
			for (var i = 0; i < datasSize; i++) {
				data = datas[i];
				var yAxisName = data[yans[0].en];
				if (yans[0].type == "datetime") {
					yAxisName = chartsFunc._formatTime(yAxisName, rule.tf);
				}
				/*if(tsize != undefined)
					data['value'] = parseInt(data[tsize.en]);					
				if(tcolor != undefined)
					data['color'] = data[tcolor.en];
				if(tname != undefined)
					data['name'] = data[tname.en];
				if(tid != undefined)
					data['id'] = data[tid.en];
				if(tparent != undefined){
					if(opts.istree)
						data['parent'] = data[tparent.en];					
				}*/
					// var xAxisName = data[xan.en]; 系列名
				var hasSerie = chartsFunc._hasYAxisName(series, yAxisName);
				if (hasSerie) { // 如果包含
					var subserie = chartsFunc._staticBuildSubserie(data, rule);
					//subserie.color = '#000';
					var formatColor =serie.dataLabels.formatColor;
					if(formatColor){
						var boo = "";
						try{
							boo = JSON.parse(formatColor)
						}catch(e){
							
						}
						if(boo){
							var strExp = "";
							$.each(boo,function(i,item){
								var expression = item.expression;
								// chartsFunc._evalExp(expression,data,serie.name);
								var color = item.color;
								var exp = chartsFunc._convertExpToStr(expression,data,yan,datasSize);
								if(eval(exp)){
									//计算表达式并返回字符串
									// serie.dataLabels.format = chartsFunc._convertExpToStr(htmlExpression,data,yan,datasSize);
									subserie.color = color;
								}
							})
						}
					}
					
					hasSerie.data.push(subserie);
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

					serie.dataLabels = serie.dataLabels || $.extend(true,{},opts.plotOptions.series.dataLabels) || {};
					//获取表达式模板中的信息
					var formatExp = serie.dataLabels.formatExp;
					if(formatExp && formatExp.length > 0){
						var strExp = "";
						$.each(formatExp,function(i,item){
							var expression = item.expression;
							// chartsFunc._evalExp(expression,data,serie.name);
							var htmlExpression = item.htmlExpression;
							var exp = chartsFunc._convertExpToStr(expression,data,yan,datasSize);
							if(eval(exp)){
								//计算表达式并返回字符串
								// serie.dataLabels.format = chartsFunc._convertExpToStr(htmlExpression,data,yan,datasSize);
								serie.dataLabels.format = htmlExpression;
							}
						})
					}
					
					var formatColor =serie.dataLabels.formatColor;
					if(formatColor){
						var boo = "";
						try{
							boo = JSON.parse(formatColor)
						}catch(e){
							
						}
						if(boo){
							var strExp = "";
							$.each(boo,function(i,item){
								var expression = item.expression;
								// chartsFunc._evalExp(expression,data,serie.name);
								var color = item.color;
								var exp = chartsFunc._convertExpToStr(expression,data,yan,datasSize);
								if(eval(exp)){
									//计算表达式并返回字符串
									// serie.dataLabels.format = chartsFunc._convertExpToStr(htmlExpression,data,yan,datasSize);
									subserie.color = color;
								}
							})
						}
					}
					
					chartsFunc._setShowValueAndPoint(serie, rule);

					/*
					 * if(serie.name == "0"){ serie.stack =ind+ "我的" ; }else{
					 * serie.stack =ind+ "你的" ; }
					 */
					subseries = [];
					subserie = chartsFunc._staticBuildSubserie(data, rule);
					var formatColor =serie.dataLabels.formatColor;
					if(formatColor){
						var formatc ="";
						try{
							formatc = JSON.parse(formatColor)
						}catch(e){
						}
						if(formatc){
							var strExp = "";
							$.each(formatc,function(i,item){
								var expression = item.expression;
								var color = item.color;
								var exp = chartsFunc._convertExpToStr(expression,data,yan,datasSize);
								if(eval(exp)){
									//计算表达式并返回字符串
									// serie.dataLabels.format = chartsFunc._convertExpToStr(htmlExpression,data,yan,datasSize);
									subserie.color = color;
								}
							})
						}
					}
				
					subseries.push(subserie);
					serie.data = subseries;
                    
					if (opts.pieInnerSize) {
						serie.innerSize = opts.pieInnerSize;
					}
					if ((serie.type == "pie" || (!serie.type && opts.chart.type == "pie")) && opts.pieInnerSize) {
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
					/*if(tsize != undefined)
						data['value'] = parseInt(data[tsize.en]);					
					if(tcolor != undefined)
						data['color'] = data[tcolor.en];
					if(tname != undefined)
						data['name'] = data[tname.en];
					if(tid != undefined)
						data['id'] = data[tid.en];
					if(tparent != undefined){
						if(opts.istree)
							data['parent'] = data[tparent.en];					
					}*/
					if (hasSerie) {
//						hasSerie.data.push(chartsFunc._buildSubserie(data, rule, yan));
						var subserie = chartsFunc._buildSubserie(data, rule, yan);
						//subserie.color = '#000';
						var formatColor =serie.dataLabels.formatColor;
						if(formatColor){
							var boo = "";
							try{
								boo = JSON.parse(formatColor)
							}catch(e){
								
							}
							if(boo){
								var strExp = "";
								$.each(boo,function(i,item){
									var expression = item.expression;
									// chartsFunc._evalExp(expression,data,serie.name);
									var color = item.color;
									var exp = chartsFunc._convertExpToStr(expression,data,yan,datasSize);
									if(eval(exp)){
										//计算表达式并返回字符串
										// serie.dataLabels.format = chartsFunc._convertExpToStr(htmlExpression,data,yan,datasSize);
										subserie.color = color;
									}
								})
							}
						}
						hasSerie.data.push(subserie);
					} else {
						serie = {};
						if (yAxisLength) {
							serie.yAxis = ind;
						}
						serie.name = yan.cn;
						serie.type = rule.chartType;

						serie.dataLabels = serie.dataLabels || $.extend(true,{},opts.plotOptions.series.dataLabels) || {};
						//获取表达式模板中的信息
						var formatExp = serie.dataLabels.formatExp;
						if(formatExp && formatExp.length > 0){
							var strExp = "";
							$.each(formatExp,function(i,item){
								var expression = item.expression;
								// chartsFunc._evalExp(expression,data,serie.name);
								var htmlExpression = item.htmlExpression;
								var exp = chartsFunc._convertExpToStr(expression,data,yan,datasSize);
								if(eval(exp)){
									//计算表达式并返回字符串
									// serie.dataLabels.format = chartsFunc._convertExpToStr(htmlExpression,data,yan,datasSize);
									serie.dataLabels.format = htmlExpression;
								}
							})
						}
						

						chartsFunc._setShowValueAndPoint(serie, rule);
						if (rule.stack) {
							serie.stack = data[rule.stack];
						}
						subseries = [];
						subserie = chartsFunc._buildSubserie(data, rule, yan);
						
						var formatColor =serie.dataLabels.formatColor;
						if(formatColor){
							var boo = "";
							try{
								boo = JSON.parse(formatColor)
							}catch(e){
								
							}
							if(boo){
								var strExp = "";
								$.each(boo,function(i,item){
									var expression = item.expression;
									// chartsFunc._evalExp(expression,data,serie.name);
									var color = item.color;
									var exp = chartsFunc._convertExpToStr(expression,data,yan,datasSize);
									if(eval(exp)){
										//计算表达式并返回字符串
										// serie.dataLabels.format = chartsFunc._convertExpToStr(htmlExpression,data,yan,datasSize);
										subserie.color = color;
									}
								})
							}
						}
						
						//subserie.color = "#000";
						subseries.push(subserie);
						serie.data = subseries; //颜色存放地点
						if (opts.colorByPoint) {
							serie.colorByPoint = true; //根据点自动分配颜色
						}
						if ((serie.type == "pie" || (!serie.type && opts.chart.type == "pie")) && opts.pieInnerSize) {
							serie.innerSize = opts.pieInnerSize;
						}
						series.push(serie);
					}
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
	_hastreeName: function(series, name) {
		var seriesSize = series.length,
			serie;
		if (seriesSize) {
			for (var i = 0; i < seriesSize; i++) {
				serie = series[i];
				if (serie.name == name) {
					return serie;
				}
			}
		}
		return null;
	},
	_buildtreemap : function(rule,datas,opts){
		var tsize = rule.tSize,
			tname = rule.tName,
			tid = rule.tid,
			tparent = rule.tParent,
			tcolor = rule.tColor,
			datasSize = datas.length,
			subserie,series = [],colorCount = 0,
			color = ['#d94e5d','#eac736','#50a3ba','#404a59','#323c48','#a6c84c', '#ffa022', '#46bee9'];
		for (var i = 0; i < datasSize; i++) {
			    data = datas[i];	
			    var hasSerie = chartsFunc._hastreeName(series,"treemap")
				if(rule.yAxisName != undefined)
					data['value'] = parseInt(data[rule.yAxisName[0].en]);
					
				if(rule.xAxisName != undefined)
					data['name'] = data[rule.xAxisName.en];
				if(tid != undefined)
					data['id'] = data[tid.en];
				if(tparent != undefined){
					if(opts.istree)
						data['parent'] = data[tparent.en];					
				}
				colorCount = colorCount < color.length-1 ? colorCount+1 : 0; 
				data['color'] = color[colorCount];
				if (hasSerie) {
					hasSerie.data.push(data);
				}
				else{
				serie = {};
				serie.type = rule.chartType;
				serie.name = "treemap";
				serie.layoutAlgorithm = 'stripes';
				serie.alternateStartingDirection = true;
				var levels = [{
					level: 1,
	                layoutAlgorithm: 'sliceAndDice',
	                dataLabels: {
	                    enabled: true,
	                    align: 'left',
	                    verticalAlign: 'top',
	                    style: {
	                        fontSize: '15px',
	                        fontWeight: 'bold'
	                    }
	                }
				}]
				if(!opts.istree){
					levels[0].dataLabels.verticalAlign = "middle";
					levels[0].dataLabels.align = "center";
				}
				serie.levels = levels;	
				chartsFunc._setShowValueAndPoint(serie, rule);
				if (rule.stack) {
					serie.stack = data[rule.stack];
				}
				subseries = [];
				subseries.push(data);
				serie.data = subseries;
		    	if (opts.pieInnerSize) {
					serie.innerSize = opts.pieInnerSize;
				}				
				   series.push(serie);
				}
		}
		return series;
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
					if (opts.chart.type == "treemap") { // 多饼图数据合并处理
						serie = chartsFunc._buildtreemap(rule,data.data,opts);
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
	changeSeriesValue: function(rule, args) {
		var mtcharts = pubsub.getJQObj(rule);
		var mtcharts_hightchart = mtcharts.highcharts();
		var series = mtcharts_hightchart.series;
		// var name = 
		$.each(args, function(key, value) {

			$.each(series, function(i, item) {
				if (item.name == key) {
					var valArray = [];
					$.each(item.data, function(k, kitem) {
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
} catch (ex) {}var dojoConfig = {
	parseOnLoad: true,
	cacheBust: true,
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
		jsgistb = new esri.toolbars.Draw(jsgisMap);
		if (datas) {
			//right:80px; top:10px;
			var content = '<div style="position:absolute; z-Index:99;"><div data-dojo-type="dijit.TitlePane" id="dijit_TitlePane_0" data-dojo-props="title:\'切换地图\',open:false"><div data-dojo-type="dijit.layout.ContentPane" style="overflow:auto;"><div id="basemapGallery"></div></div></div></div>',
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
		//jsgistb = new esri.toolbars.Draw(map);
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
				rule || (rule = {});
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
	},
	//显示轨迹
	showTrack: function(rule, params) {
		var jsgisMap = jsGisFunc.getMap(rule),
			workingStorage = jsgisMap.workingStorage,
			symbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new esri.Color([255, 0, 0]), 4),
			path = params[rule[0].param];
		jsGisFunc._clearGis(workingStorage, jsgisMap, params.remit);
		if (path && (path = JSON.parse(path))) {
			var geometry = new esri.geometry.Polyline(path),
				graphic = new esri.Graphic(geometry, symbol),
				extentCenter = graphic.geometry.getExtent().getCenter();
			jsgisMap.graphics.add(graphic);
			graphic.isFlicker = true;
			workingStorage.push(graphic);
			jsgisMap.centerAt(new esri.geometry.Point([extentCenter.x,extentCenter.y]));
		}
		jsGisFunc._flicker(jsgisMap);
	}
};
pubsub.sub("jsgis", jsGisFunc);/**
 * 自定义表格
 */
var definedTableFunc = {
	getRow : function(rule, args) {
		// var target = mtxx.e.target;
		// if (target) {
		// 	var dataItem = pubsub.getJQObj(rule,true).definedTable("dataItem", target)
		// 	if (dataItem) {
		// 		return dataItem[rule.columnName];
		// 	}
		// }
		
		var dataItem = pubsub.getJQObj(rule,true).definedTable("getSelectedRowsData");
		if(dataItem && dataItem[0]){
			return dataItem[0][rule.columnName];
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
 * Gantt
 */
var ganttFunc = {
	getRow : function(rule, args) {
		var index = args[0];
		var task = args[1] || {};
		return task["row"][rule.columnName] || '';
		//return pubsub.getJQObj(rule, true).iframegantt("getRow", args);
	},
	save : function(rule, args){
		return pubsub.getJQObj(rule).iframegantt("save", args);
	},
	load : function(rule, args){
		return pubsub.getJQObj(rule).iframegantt("load", $.isArray(args)?{}:args);
	}
};
pubsub.sub("gantt", ganttFunc);var officebtFunc = {
	/**
	 * 联动
	 */
	linkage : function(rule, args) {
		var $target = pubsub.getJQObj(rule);
		if (args && args.DocId && $target.get(0)) {
			$target.office('openByDocId', args.DocId);
		}
	},
	/**
	 * 赋值
	 */
	setValue : function(rule, args) {
		var $target = pubsub.getJQObj(rule);
		if (args && $target.get(0)) {
			$.each(args, function(k, v) {
				$target.office("setValue", k, v);
			});
		}
	},
	/**
	 * 控件自身保存
	 */
	OfficeSave : function(rule, args) {
		var $target = pubsub.getJQObj(rule);
		if ($target.get(0)) {
			$target.office("save");
		}
	},
	/**
	 * 打印
	 */
	print : function(rule, args) {
		var $target = pubsub.getJQObj(rule);
		if ($target.get(0)) {
			$target.office("print");
		}
	},
	/**
	 * 联动模板（保存会生成新的文件）
	 */
	linkModel : function(rule, args) {
		var $target = pubsub.getJQObj(rule);
		if (args && args.DocId && $target.get(0)) {
			$target.office('openModelByDocId', args.DocId);
		}
	},
	/**
	 * 修订
	 */
	ShowRevisions : function(rule, args){
		var $target = pubsub.getJQObj(rule);
		if (args && $target.get(0)) {
			$target.office('ShowRevisions', !!args.ShowRevisions);
		}
	},
	/**
	 * 修订加密
	 */
	MtProtect : function(rule, args){
		var $target = pubsub.getJQObj(rule);
		if (args && $target.get(0)) {
			if(args.Protect === undefined){
				alert("加密参数不能为空!");
				return false;
			}
			if(args.PWD === undefined){
				alert("密码不能为空!");
				return false;
			}
			if(args.Protect == true || args.Protect == 'true'){
				$target.office('Protect', args.PWD);
			} else {
				$target.office('Unprotect', args.PWD);
			}
		}
	},
	/**
	 * 另存为
	 */
	MtShowDialog : function(rule, args){
		var $target = pubsub.getJQObj(rule);
		if (args && $target.get(0)) {
			$target.office('ShowDialog', args.TYPE || 3);
		}
	},
	
	saveAs : function(rule, args){
		var $target = pubsub.getJQObj(rule);
		if (args && $target.get(0)) {
			$target.office('saveAs', args.fileName);
		}
	},
	
	AppendRowsByBookMarks : function(rule, args){
		var $target = pubsub.getJQObj(rule);
		if (args && $target.get(0)) {
			!(rule instanceof Array) && (rule = [rule]);
			var bookMarkMapping = {};
			$.each(rule, function(i, v){
				bookMarkMapping[v.param] = v.columnName;
			});
			var data = null;
			for(var k in args){
				!data && (data = args[k]);
			}
			$target.office('AppendRowsByBookMarks', bookMarkMapping, data);
		}
	}
};
pubsub.sub("officebt", officebtFunc);/**
 * 流程实例函数 撤销、终止...
 * 
 * @author klaus.wang
 */
var FlowProcess = {
	SUBMIT : 0,
	STOP : 1,
	CANCEL : 2,
	ACTIVE : 3,
	REJECT : 4
};

FlowProcess.callWindow = function(args, sfn, efn, fn) {
	$.extend(FlowProcess, {
		args : args,
		sfn : sfn,
		efn : efn
	});
	var url = contextPath + //
	"/mx/form/defined/ex/flowProcessFeedback?taskId=" + (args.taskId || '')
			+ "&" + $.param({
				processId : args.processId,
				type : args.type,
				fn : fn || "FlowProcessFunc"
			});

	var $frame = $("<iframe>", {
		frameborder : 'no',
		width : '100%',
		height : 400
	});

	FlowProcess.dialog = window.show({
		css : {
			width : 650,
			height : 450
		},
		message : $frame,
		onshown : function(dialog) {
			$frame.attr("src", url);
		}
	});

};

function FlowProcessFunc(args) {
	args = $.extend(true, (FlowProcess.args || {}), args || {});
	var url = contextPath + //
	"/mx/form/workflow/defined/flowProcess";
	$.ajax({
		url : url,
		data : args,
		type : 'post',
		dataType : 'json',
		success : FlowProcess.sfn || function(ret) {
			if (!ret)
				return;
			if (ret.errMsg) {
				alert(ret.errMsg);
			} else {
				alert("操作成功!");
				FlowProcessFunc.close();
				FlowProcess.callback && FlowProcess.callback(ret);
				FlowProcess.callback = null;
			}
		},
		error : FlowProcess.efn || function(ret) {
			alert("服务器处理出错!");
		}
	});
}

FlowProcessFunc.close = function() {
	FlowProcess.dialog && (FlowProcess.dialog.close());
};

/**
 * 流程终止(作废)
 */
FlowProcess.stop = function(args) {
	if (!confirm("你确定终止流程吗?")) {
		return false;
	}
	FlowProcess.callWindow({
		type : FlowProcess.STOP,
		processId : args.processId
	});
};

/**
 * 流程终止
 */
FlowProcess.stop0 = function(rule, args) {
	return FlowProcessFunc0(rule, $.extend(true, {}, args, {
		type : FlowProcess.STOP
	}), "终止");
};

/**
 * 流程挂起(作废)
 */
FlowProcess.cancel = function(args) {
	if (!confirm("你确定挂起流程吗?")) {
		return false;
	}
	FlowProcessFunc({
		type : FlowProcess.CANCEL,
		processId : args.processId
	});
};

/**
 * 流程挂起
 */
FlowProcess.cancel0 = function(rule, args) {
	return FlowProcessFunc0(rule, $.extend(true, {}, args, {
		type : FlowProcess.CANCEL
	}), "挂起");
};

/**
 * 流程激活(作废)
 */
FlowProcess.active = function(args) {
	if (!confirm("你确定激活流程吗?")) {
		return false;
	}
	FlowProcessFunc({
		type : FlowProcess.ACTIVE,
		processId : args.processId
	});
};
/**
 * 流程激活
 */
FlowProcess.active0 = function(rule, args) {
	return FlowProcessFunc0(rule, $.extend(true, {}, args, {
		type : FlowProcess.ACTIVE
	}), "激活");
};

/**
 * 流程撤回(作废)
 */
FlowProcess.reject = function(args) {
	// if (!confirm("你确定撤回/退回流程吗?")) {
	// return false;
	// }
	FlowProcess.callWindow({
		type : FlowProcess.REJECT,
		processId : args.processId,
		taskId : args.taskId
	});
}
/**
 * 流程撤回
 */
FlowProcess.reject0 = function(rule, args) {

	// if(window.$ParentFlow){
	// return window.$ParentFlow.back();
	// }

	if (!args.taskId) {
		alert("流程任务id不能为空");
		return false;
	}
	return FlowProcessFunc0(rule, $.extend(true, {}, args, {
		type : FlowProcess.REJECT
	}), "撤回/退回");
}

/**
 * 流程提交(作废)
 */
FlowProcess.submit = function(args) {
	FlowProcess.callWindow({
		type : FlowProcess.SUBMIT,
		processId : args.processId,
		taskId : args.taskId
	});
};

/**
 * 签收
 */
FlowProcess.mtAssign0 = function(rule, args) {
	if (!args.processId) {
		alert("流程实例id不能为空");
		return false;
	}

	var url = contextPath + "/mx/form/workflow/defined/assign";
	$.ajax({
		url : url,
		data : args,
		type : 'post',
		dataType : 'json',
		success : function(ret) {
			if (!ret)
				return;
			if (ret.errMsg) {
				alert(ret.errMsg);
			} else {
				alert("操作成功!");
			}
			pubsub.execChilds(rule);
		},
		error : function(ret) {
			alert("服务器处理出错!");
		}
	});

}

/**
 * 流程提交
 */
FlowProcess.mtSubmit0 = function(rule, args) {
	if (window.$ParentFlow) {
		function callback(msg) {
			var ruleOne;
			var thatWin = /* pubsub.getThat(rule, true) */window;
			if ($.isPlainObject(rule)) {
				ruleOne = rule;
			}
			if ($.isArray(rule)) {
				ruleOne = rule[0];
			}
			thatWin.callbackParam = thatWin.callbackParam || {};
			$.each(ruleOne.callback, function(i, k) {
				thatWin.callbackParam[k.param] = msg[k.param];
			});
			pubsub.execChilds(rule);
		}
		if (args.approve == false) {
			if (args.notOpen != true)
				return window.$ParentFlow.back(FlowProcess.callback = callback);
		} else
			return window.$ParentFlow.submit(callback);
	}

	if (!args.processId) {
		alert("流程实例id不能为空");
		return false;
	}

	/**
	 * 退回直接走撤回道路
	 */
	if (args.approve == false && args.notOpen == true) {
		args.approve = null;
		args.notOpen = null;
		return FlowProcessFunc0(rule, $.extend(true, {}, args, {
			type : FlowProcess.REJECT
		}), "撤回/退回");
	}

	if (!confirm((args.approve == false ? "退回" : "提交") + "流程?")) {
		return false;
	}

	var url = contextPath + //
	"/mx/form/workflow/defined/submit";
	$.ajax({
		url : url,
		data : args,
		type : 'post',
		dataType : 'json',
		success : function(ret) {
			if (!ret)
				return;
			if (ret.errMsg) {
				alert(ret.errMsg);
			} else {
				alert("操作成功!");
			}
			pubsub.execChilds(rule);
		},
		error : function(ret) {
			alert("服务器处理出错!");
		}
	});

};

function FlowProcessFunc0(rule, args, msg) {

	if (!args.processId) {
		alert("流程实例id不能为空");
		return false;
	}

	if (!confirm(msg + "流程?")) {
		return false;
	}

	var url = contextPath + //
	"/mx/form/workflow/defined/flowProcess";
	$.ajax({
		url : url,
		data : args,
		type : 'post',
		dataType : 'json',
		success : function(ret) {
			if (!ret)
				return;
			if (ret.errMsg) {
				alert(ret.errMsg);
			} else {
				alert("操作成功!");
			}
			pubsub.execChilds(rule);
		},
		error : function(ret) {
			alert("服务器处理出错!");
		}
	});
}/**
 * 链接
 */
var bimFunc = {
	setValue:function(rule, params){
		var $jqObj = pubsub.getJQObj(rule);
		for(var key in params){
			$jqObj.bim("setValue",params[key]);
		}
	},
	select2View : function(rule, params){
		var $jqObj = pubsub.getJQObj(rule);
		for(var key in params){
			$jqObj.bim("select2View", params[key]);
		}
	},
	getData : function(rule, params){
		var $jqObj = pubsub.getJQObj(rule, true);
		if($jqObj && $jqObj.get(0)){
			params = $jqObj.bim("GetSelection");
		}
		if(params[0]){
			return params[0][rule.columnName];
		}
		return null;
	}
};
pubsub.sub("bim", bimFunc);/**
 * 九宫格事件定义器接口
 */
var gridlistFunc = {
	setIndex : function(rule,args){
		var $id = pubsub.getJQObj(rule);
		var v = "";
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param]||"";
		}
		$id.css("z-Index",v);
	},
	getNowClickCell : function(rule, args) {
		var $id = pubsub.getJQObj(rule,true),r, v = "";
		var data = $id.gridList("getClickValue");
		return data[rule.columnName];
	},
	getNowDblClickCell : function(rule, args) {
		var $id = pubsub.getJQObj(rule,true),r, v = "";
		var data = $id.gridList("getDblClickValue");
		return data[rule.columnName];
	},
	isChecked: function(rule, args) {
		if (args && args[0]) {
			var $jq = pubsub.getJQObj(rule, true);
			var gridlistBox = $jq.gridList("getGridlistBox");
			
			return gridlistBox.find('>.gridList_ul>li>.demo_content[' + "cell-index" + "=" + args[0]["cell-index"] + ']').hasClass("selected");
			// return g.tbody().find("tr:eq(" + args[1]["row-index"] + ")").hasClass(g.options.selectedCls);
		}
		return false;
	},
	getClickCell : function(rule, args){
		//pubsub.getJQObj(rule,true)，若是true则获取输入控件，否则获取输出控件。
		// var $id = pubsub.getJQObj(rule,true),r, v = "";
		// var data = $id.gridList("getClickValue");
		// return data[rule.columnName];


		var dataItems = pubsub.getJQObj(rule, true).gridList("getSelectedDatas"),
			val = [];
		if (dataItems && dataItems.length) {
			$.each(dataItems, function(i, o) {
				if (o[rule.columnName]) {
					val.push(o[rule.columnName] || "");
					// pubsub.outParamsObj(rule, obj, o);
				}
			});
			return val.join(",");
		}

	},
	getAll : function(rule, args) {
		var $in = pubsub.getJQObj(rule, /*args[0] ||*/ true);
		if ($in) {
			var retAry = [], dataItems = $in.data("gridList").getData(), dataItem;
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
	linkage: function(rule, params) {
		gridlistFunc.linkageControl(rule, params);
	},
	linkageControl : function(id, params) {
		pubsub.getJQObj(id).gridList("query", params);
	},
	thidden: function(rule, args) { // 隐藏
		pubsub.getJQObj(rule).hide();
	},
	tshow: function(rule, args) { // 显示
		pubsub.getJQObj(rule).show();
	},

	getDrapRow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule,true),r, v = "";
		var data = $id.gridList("getDrapRow");
		return data[rule.columnName];
	},

	getDropRow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule,true),r, v = "";
		var data = $id.gridList("getDropRow");
		return data[rule.columnName];
	},

	getOverRow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule,true),r, v = "";
		var data = $id.gridList("getOverRow");
		return data[rule.columnName];
	},

	refresh: function(rule,args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		var data = $id.gridList("refresh");
	},
	//禁用单元格
	disableCell: function(rule,args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param];
				if(args[r.param] == null){
					v = "";
				}
			}
		} else {
			v = args;
		}
		$id.gridList("disableCell",v);
	},
	//禁用单元格
	blukCancelDisableCell: function(rule,args){
		var $id = pubsub.getJQObj(rule),r, v = "";

		var key = args.key;
		var v = args.value;
		$id.gridList("blukCancelDisableCell",key,v);
	},
	//全部选择
	selectCheckCell: function(rule,args){
		var $id = pubsub.getJQObj(rule.outid,true),
		    build = $id.data('gridList'),
		    dataRule = $(build.target).attr('data-rule'),
		    ele = $(build.target).find("li");
		$.each(ele,function(i,e){
			if($(e).find(".disabledCell")[0] != undefined && $(e).find(".disabledCell")[0] != null){
				$(e).find("input[type=checkbox]").iCheck("uncheck");
			}
			else{
				$(e).find(".demo_content").addClass("selected");
				$(e).find("input[type=checkbox]").iCheck("check");
			}
			
			
		});
		
	},
	//全部取消
	cancelCheckCell: function(rule,args){
		var $id = pubsub.getJQObj(rule.outid,true),
		    build = $id.data('gridList'),
		    dataRule = $(build.target).attr('data-rule'),
		    ele = $(build.target).find("li");
		
		$.each(ele,function(i,e){
			$(e).find(".demo_content").removeClass("selected");
			$(e).find("input[type=checkbox]").iCheck("uncheck");
			
		});
	},
	
	
	//取消禁用格
	cancelDisableCell: function(rule,args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
			}
			$id.gridList("cancelDisableCell",v);
		} else {
			$id.gridList("cancelDisableCell",args);
		}
	},
	//取消全部禁用色
	cancelAllDisableCell: function(rule,args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.gridList("cancelAllDisableCell");
	},

	//禁用单元格
	blukDisableCell: function(rule,args){
		var $id = pubsub.getJQObj(rule),r, v = "";

		var key = args.key;
		var v = args.value;

		// if (typeof args == "object") {
		// 	for (var i = 0; i < rule.length; i++) {
		// 		r = rule[i];
		// 		v = args[r.param];
		// 		if(args[r.param] == null){
		// 			v = "";
		// 		}
		// 	}
		// } else {
		// 	v = args;
		// }
		
		$id.gridList("blukDisableCell",key,v);
	},

	getKeyName : function(rule,args){
		return rule.columnName;
	}
	
};
pubsub.sub("gridlist", gridlistFunc);
pubsub.sub("gridList", gridlistFunc);var exceluploadFunc = {
	//设置参数
	setValue : function(rule, args) { //赋值
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.excelFileUpload("setParam",args);
	},
	getExcelVal : function(rule,args){
		var count = 0;
		$.each(args[0].result,function(i,arg){
			if(arg.count != undefined && arg.count != null){
				count = arg.count;
			}
		})
		return count;
	}
};
pubsub.sub("excelupload", exceluploadFunc);/**
 * 链接
 */
var svgEditorFunc = {
	setValue:function(rule, params){
		var $jqObj = pubsub.getJQObj(rule);
		for(var key in params){
			$jqObj.svgEditor("setValue",params[key]);
		}
	},
	getValue:function(rule, args){
		var $jqObj = pubsub.getJQObj(rule,true);
		return $jqObj.svgEditor("getValue");
	}
};
pubsub.sub("svgeditor", svgEditorFunc);var bridgeseamFunc = {
	init: function(rule, args) {
		var options = args[0];
		options.url = contextPath + "/mx/form/charts/datas";
		options.data = JSON.stringify({
			'rid': options.rid,
			'params': getParams(rule.inid) ? JSON.stringify(getParams(rule.inid)) : null
		});
		var $this = $('#' + rule.inid);
		$this.bridgeSeam(options);
	},
	linkage :function(id, params, args){
		bridgeseamFunc.linkageControl(id, params, args);
	},
	linkageControl: function(id, params, args) {
		var $this = pubsub.getJQObj(id);
		$this.bridgeSeam("query",params);
	}
};
pubsub.sub("bridgeseam", bridgeseamFunc);/**
 * mobile 手机注册事件
 */

(function() {

	function connectWebViewJavascriptBridge(callback) {
		if (window.WebViewJavascriptBridge) {
			callback(WebViewJavascriptBridge)
		} else {
			document.addEventListener('WebViewJavascriptBridgeReady', function() {
				callback(WebViewJavascriptBridge)
			}, false);
		}
	}

	//注册回调函数，第一次连接时调用 初始化函数
	connectWebViewJavascriptBridge(function(bridge) {
		if(!bridge.init){
			return;
		}
		//初始化
		bridge.init(function(message, responseCallback) {
			var data = {
				'Javascript Responds': 'Wee!'
			};
			responseCallback(data);
		});

		//接管window.open
		var oldOpen = window.open,
			oldClose = window.close;

		window.open = function() {
			var args = [].slice.call(arguments);
			window.WebViewJavascriptBridge.callHandler('openWindow', {
				"url": args[0]
			}, function(responseData) {});
			//oldOpen.apply(this, args);
		}

		window.close = function() {
			var args = [].slice.call(arguments);
			window.WebViewJavascriptBridge.callHandler('closeWindow', {}, function(responseData) {});
			//oldClose.apply(this, args);
		}

		//接收安卓发来的消息   并返回给安卓通知
		
		//扫码回调
		bridge.registerHandler("scanCallback", function(data, responseCallback) {
		    var callback = $("body").data("scanCallback");
		    callback && callback.call(null,data);
		});
	})
})();/**
 * 网格
 */
var videoplayFunc = {
	setVideoSource: function(rule, args) {
		var $id = pubsub.getJQObj(rule), r, v;
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param];
		}
		var sourceObj = {
			src: v, 
			type: "video/" + v.substring(v.lastIndexOf(".")+1)
		}
		$id.videoExt("setVideoSource",sourceObj);
	},
	setVideoPreImg: function(rule, args) {
		var $id = pubsub.getJQObj(rule), r, v;
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			v = args[r.param];
		}
		var posterObj = {
			src: v, 
			type: "video/" + v.substring(v.lastIndexOf(".")+1)
		}
		// var posterSrc = v;

		$id.videoExt("setVideoPreImg",posterObj);
	}
};

pubsub.sub("videoplay", videoplayFunc);/**
 *二维码事件定义器接口
 */
var sionalcodeFunc = {
		
		copyContent:function(rule, args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		args.id=$id.selector;
		$(args.id).empty();
		$id.sionalcodeExt("copyContent",args);
	},
	downloadSional:function(rule, args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		args.id=$id.selector;
		$id.sionalcodeExt("downloadSional",args);
	},
};
pubsub.sub("sionalcode", sionalcodeFunc);var rangeSliderFunc={
		setValue:function(rule, args){
			var t = pubsub.getJQObj(rule, false);
			var keys=new Array()
			for(key in args){
				keys.push(args[key]);
			}
			if(keys.length==1){
				var slider = $("#range").data("ionRangeSlider");
				var form=Number(keys[0]);
				
				slider.reset();
				slider.update({
				    from: form
				});
			}else{
				var slider = $("#range").data("ionRangeSlider");	
				var form=Number(keys[0]);
				var to=Number(keys[1]);
				slider.reset();
				slider.update({
				    from: form,
				    to:to
				});
			}
		},
		getRightValue:function(rule, args){
			//获取进度条的值
			var t = pubsub.getJQObj(rule, true);
			var value=t.find('.irs-to').text();
			return value; 
		},
		getLeftValue:function(rule, args){
			//获取进度条的值
			var t = pubsub.getJQObj(rule, true);
			//var value=t.find('.irs-from').text();
			var value=t.find('.irs-single').text();
			return value; 
		}
}

//把data-role与事件进行绑定
pubsub.sub("rangeSlider",rangeSliderFunc);/**
 * 
 */
var videoFunc = {
	getRow : function(rule, args) {
		return null;
	},
	linkage : function(rule, params) {
		videoFunc.linkageControl(rule, params);
	},
	linkageControl : function(rule, params) {
		var $this = pubsub.getJQObj(rule)  ;
		var drole = $this.attr('drole');
		var model = $this.attr('model');
		if(drole && "ys"==drole){ //萤石云模式
			$this.ysVideo("link",{params:params});
		} else if(model && "traffic"==model){
			var sparams = {
					params : JSON.stringify(params) || null
				};
				sparams.rid = $this.attr("rid");
				$.ajax({
					url : contextPath + '/mx/form/data/getVideoSetByTraffic',
					data : sparams,
					type : "POST" ,
					dataType : "JSON",
					async : false,
					success : function(data) {
						if (data && data[0]) {
							loginNetVideo(data[0]);
						}
					}
				});
		}else{
			var sparams = {
				params : JSON.stringify(params) || null
			};
			sparams.rid = $this.attr("rid");
			$.ajax({
				url : contextPath + '/mx/form/data/getVideoSet',
				data : sparams,
				type : "POST" ,
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
 * 自定义卡片事件定义器接口
 */
var definedcardFunc = {
	getSelectRow : function(rule, args){
		//pubsub.getJQObj(rule,true)，若是true则获取输入控件，否则获取输出控件。
		var $id = pubsub.getJQObj(rule,true),r, v = "";
		var data = $id.definedCardExt("getSelectRow");
		return data[rule.columnName];
	},
	getAll : function(rule, args) {
		var $in = pubsub.getJQObj(rule, /*args[0] ||*/ true);
		if ($in) {
			var retAry = [], dataItems = $in.data("gridList").getData(), dataItem;
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
	refresh : function(rule,args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.definedCardExt("refresh");
	},
	linkage : function(rule,params){
		params["databaseId"] && pubsub.getJQObj(id).attr("dbid", params["databaseId"]);
		var $id = pubsub.getJQObj(rule),r, v = "";
		$id.definedCardExt("linkage",params);
	},
};
pubsub.sub("definedcard", definedcardFunc);/**
 * 网格
 */
var definedPanelFunc = {
	getSelectedData: function(rule, args, obj) {
		var dataItems = pubsub.getJQObj(rule, true).definedPanelExt("getSelectRowData"),
			val = [];
		if (dataItems) {
			return dataItems[rule.columnName];
		}
		return "";
	},
};

pubsub.sub("definedpanel", definedPanelFunc);/**
 * 链接
 */
var wyvideoFunc = {
	linkage:function(rule, params,args){
		var $this = pubsub.getJQObj(rule);
		for(var key in params){
			var param =  params[key];
			if(param){
				$this.wyvideo("play",param);
			}
		}
	}
};
pubsub.sub("wyvideo", wyvideoFunc);/**
 * 链接
 */
var formFunc = {
	
};
pubsub.sub("form", formFunc);/**
 * Metronic icheckradio
 */
var imageFunc = {
	setAttachmentId : function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		var attachmentId = "";
		$.each(args,function(key,value){
			attachmentId = value;
		})
		if(attachmentId){
			var url = contextPath + '/mx/form/imageUpload?method=downloadById&_' + (new Date().getTime()) + '&id=' + attachmentId;
			$id.attr("src",url).show();
		}
		
	},
	setAttachmentParent : function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		var attachmentParent = "";
		$.each(args,function(key,value){
			attachmentParent = value;
		})
		if(attachmentParent){
			var url = contextPath + '/mx/form/imageUpload?method=download&from=to_db&' + (new Date().getTime()) + '&randomparent=' + attachmentParent;
			$id.attr("src",url).show();
		}
		
	},
};
pubsub.sub("image", imageFunc);/**
 * 基于websocket的聊天室
 */
var webchatFunc = {
	linkage:function(rule, params,args){
		var $this = pubsub.getJQObj(rule),
			key = "roomId";
		if(key in params){
			$this.webchat("reload",params[key]);
		}
	},
	sendMsg:function(rule, params,args){
		var $this = pubsub.getJQObj(rule);
		for(var key in params){
			params[key] && $this.webchat("sendMsg",params[key]);
		}
	}
};
pubsub.sub("webchat", webchatFunc);/**
 * 阿里直播播放器
 */
var alivideoFunc = {
	linkage: function(rule, params, args) {
		var $this = pubsub.getJQObj(rule);
		for (var key in params) {
			var param = params[key];
			if (param) {
				$this.alivideo(key == "roomId" ? "playByRoomId" : "play", param);
			}
		}
	}
};
pubsub.sub("alivideo", alivideoFunc);/**
 * 
 */
var bmapextFunc = {
	setLocationMarker : function(rule, params) {
		var $this = pubsub.getJQObj(rule)  ;
		$this.bmapext("tolocationPosition");
	}
};
pubsub.sub("bmapext", bmapextFunc);