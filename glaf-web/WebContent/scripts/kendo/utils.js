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
var getProjectPath = function() {
    return contextPath;
}
//获取顶层的窗口
function getTopParentPage(p,c){
    while(p!=c){
        c = p       
        p = p.parent
    }
    return c
}
var getPageUrl = function(flag){
    var that_window = window;
    if(flag){
        var that_window = getTopParentPage(window,window.parent);
    }
    
    return that_window.location.href;
}

var getCookie = function(str){
    return $.cookie(str);
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
    //数组或者字符串去重
    mt.duplicate = function(s) {
    	var arr = s.split(",");
    	var arrTable = {},arrData = [];
        for (var i = 0; i < arr.length; i++) {
            if( !arrTable[ arr[i] ]){
                arrTable[ arr[i] ] = true;
                arrData.push(arr[i]);
            }
        }
        return arrData.toString();
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
    mt.sum = function(s){
        if(s){
            if(typeof s == 'string'){
                var k = s.split(",");
                var sum = 0;
                $.each(k,function(i,item){
                    if(item){
                        sum += parseFloat(item);
                    }
                })
                return sum;
            }else if($.isNumeric(s)){
                return s;
            }
        }
        return 0;
    }
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
}