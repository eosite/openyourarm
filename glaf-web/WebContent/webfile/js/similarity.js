/**
 * 计算2个字符串的相似度
 * @param  {[type]} str1 [description]
 * @param  {[type]} str2 [description]
 * @return {[type]}      [description]
 */
function similarity(str1, str2) {
    //计算两个字符串的长度。
    var len1 = str1.length,
        len2 = str2.length;
    //建立数组，比字符长度大一个空间
    var dif = new Array(len1 + 1);
    for (var i = 0; i <= len1; i++) {
        !dif[i] && (dif[i] = new Array(len2 + 1));
        dif[i][0] = i;
    }
    for (var i = 0; i <= len1; i++) {
        dif[0][i] = i;
    }
    //计算两个字符是否一样，计算左上的值
    var temp;
    for (var i = 1; i <= len1; i++) {
        for (var j = 1; j <= len2; j++) {
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                temp = 0;
            } else {
                temp = 1;
            }
            //取三个值中最小的
            dif[i][j] = Math.min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1, dif[i - 1][j] + 1);
        }
    }
    return 1 - dif[len1][len2] / Math.max(str1.length, str2.length);
}

/**
 * 匹配相似度
 */
function MatchSimilarity() {

}
/**
 * 匹配精度
 * @type {Number}
 */
var matchAll = $.cookie("matchAll") == "true",
    matchPrecision = 0.1,
    /**
     * 存储系统资源
     * @type {Object}
     */
    storeSystemResource = {},
    /**
     * 系统参数名称
     * @type {Array}
     */
    systemParam = [];

function addResource(key, obj) {
    storeSystemResource[key] = obj;
}

/**
 * newWindow
 */
addResource("newWindow", {
    /**
     * 不需要匹配的属性
     * @type {Array}
     */
    param: ["isMax", "width", "height", "singlePage", "model", "render", "title", "draggable", "mtOpenTab", "TABNAME", "headerPadding", "backdrop", "isParent", "closeByBackdrop", "ignoreWindow"],
    /**
     * 系统内置属性
     * @type {Object}
     */
    prop: {
        "title": {
            param: "\"窗口\"",
            cssClass: "exp",
            data: "{\"expVal\":\"\\\"窗口\\\"\",\"expActVal\":\"\\\"窗口\\\"\",\"varVal\":[]}",
        },
        "width": {
            param: "800",
            cssClass: "exp",
            data: "{\"expVal\":\"800\",\"expActVal\":\"800\",\"varVal\":[]}",
        },
        "height": {
            param: "400",
            cssClass: "exp",
            data: "{\"expVal\":\"400\",\"expActVal\":\"400\",\"varVal\":[]}",
        },
        "singlePage": {
            param: "true",
            cssClass: "exp",
            data: "{\"expVal\":\"true\",\"expActVal\":\"true\",\"varVal\":[]}",
        },
        "headerPadding": {
            param: "5",
            cssClass: "exp",
            data: "{\"expVal\":\"5\",\"expActVal\":\"5\",\"varVal\":[]}",
        },
    }
});
/**
 * 自动装载系统参数配置
 * @return {[type]} [description]
 */
function autoMunt() {
    for (var p in storeSystemResource) {
        $.merge(systemParam, storeSystemResource[p]["param"] || []);
    }
}

/**
 * 探测多个输入参数时选择什么类型
 * @return {[type]} [description]
 */
var detectObj = {},
    detectOutObj = {};
/**
 * 检测 参数的类型 (输入)
 * @param  {[type]} sin  [description]
 * @param  {[type]} pObj [description]
 * @return {[type]}      [description]
 */
function detectParamType(sin, pObj) {
    for (var i = 0; i < sin.length; i++) {
        var inObj = sin[i];
        pObj && (inObj.pObj = pObj);
        if (inObj.items) {
            inObj.items.length && detectParamType(inObj.items, inObj);
        } else {
            if (inObj && inObj.type && inObj.datasetId) {
                detectObj[inObj.id] || (detectObj[inObj.id] = {
                    name: (pObj && pObj.pObj ? pObj.pObj.text : inObj.id) || inObj.id,
                    ary: []
                });
                detectObj[inObj.id]["ary"].indexOf(pObj.text) == -1 && detectObj[inObj.id]["ary"].push(pObj.text);
            }
        }
    }
}
/**
 * 检测 参数的类型 （输出参数）
 * @param  {[type]} sout [description]
 * @param  {[type]} pObj [description]
 * @return {[type]}      [description]
 */
function detectParamOutType(sout, pObj) {
    for (var i = 0; i < sout.length; i++) {
        var outObj = sout[i];
        pObj && (outObj.pObj = pObj);
        if (outObj.items) {
            outObj.items.length && detectParamOutType(outObj.items, outObj);
        } else {
            if (outObj && outObj.otype) {
                detectOutObj[outObj.id] || (detectOutObj[outObj.id] = {
                    name: (pObj && pObj.pObj ? pObj.pObj.text : outObj.id) || outObj.id,
                    ary: []
                });
                detectOutObj[outObj.id]["ary"].indexOf(pObj.text) == -1 && detectOutObj[outObj.id]["ary"].push(pObj.text);
            }
        }
    }
}
/**
 * 是否为空对象
 * @param  {[type]}  simObj [description]
 * @return {Boolean}        [description]
 */
function isEmptyObj(simObj) {
    var isEmpty = true;
    if (simObj) {
        for (var p in simObj) {
            return false;
        }
    }
    return isEmpty;
}
/**
 * 是否探测匹配
 * @type {Boolean}
 */
var isDetect = false,
    detectSelectObj = {},
    detectSelectOutObj = {},
    detectSelectTemp = {
        "title": "<span>名称：{0}<span>",
        "select": "<label><input type='radio' name='{1}' value='{0}'>{0}</label>",
    };
/**
 * 自动匹配
 * @param  {[type]} menus [description]
 * @return {[type]}       [description]
 */
function autoMatch(menus) {

    autoMunt();

    var sin = menus['in'],
        sout = menus['out'],
        ioAry = [];

    !isDetect && detectParamType(sin);
    !isDetect && detectParamOutType(sout);

    if (!isDetect) {
        var showContent = [],
            isShow = false;
        if (!isEmptyObj(detectObj)) {
            isShow = true;
            showContent.push("<div class='auto-in'><div class='matchTitle'>&gt;&gt;&gt;输入选择</div>");
            for (var p in detectObj) {
                var detObj = detectObj[p];
                showContent.push("<div>");
                showContent.push(detectSelectTemp.title.replace('{0}', detObj["name"]));
                showContent.push("-->");
                var detAry = detObj["ary"];
                for (var i = 0; i < detAry.length; i++) {
                    showContent.push(detectSelectTemp.select.replace(/\{0\}/g, detAry[i]).replace('{1}', p));
                }
                showContent.push("</div>");
            }
            showContent.push("</div>");
        }
        if (!isEmptyObj(detectOutObj)) {
            isShow = true;
            showContent.push("<div class='auto-out'><div class='matchTitle'>&gt;&gt;&gt;输出选择</div>");
            for (var p in detectOutObj) {
                var detObj = detectOutObj[p];
                showContent.push("<div>");
                showContent.push(detectSelectTemp.title.replace('{0}', detObj["name"]));
                showContent.push("-->");
                var detAry = detObj["ary"];
                for (var i = 0; i < detAry.length; i++) {
                    showContent.push(detectSelectTemp.select.replace(/\{0\}/g, detAry[i]).replace('{1}', p));
                }
                showContent.push("</div>");
            }
            showContent.push("</div>");
        }
        if (isShow) {
            $("#dictcc").empty().append(showContent.join(""));
            dialog.open();
            return;
        }
    }

    /**
     * 获取显示的名称
     * @param  {[type]} obj [description]
     * @return {[type]}     [description]
     */
    function getText(obj) {
        var retText = "";
        if (obj.text != "输入参数" && obj.text != "Q")
            retText = obj.text + (retText ? "-->" + retText : "");
        if (obj.pObj) {
            var ptext = getText(obj.pObj);
            ptext && (retText = ptext + (retText ? "-->" + retText : ""));
        }
        return retText;
    }
    /**
     * 创建输入输出参数对象
     * @param  {[type]} inObj  [description]
     * @param  {[type]} pObj   [description]
     * @param  {[type]} outObj [description]
     * @return {[type]}        [description]
     */
    function buildObj(inObj, pObj, outObj) {
        return {
            inparam: inObj.param,
            inid: inObj.id,
            inname: inObj.cssClass ? inObj.param : getText(pObj) ? getText(pObj) + "-->" + inObj.text : inObj.text,
            inlev: inObj.lev,
            inpage: inObj.pageId,
            itype: inObj.otype,
            icode: inObj.code,
            idatasetId: inObj.datasetId,
            columnName: inObj.columnName || '',
            outname: getText(outObj),
            outid: outObj.id,
            outlev: outObj.lev,
            outpage: outObj.pageId,
            eventType: outObj.eventType,
            type: inObj.type,
            param: outObj.param,
            otype: outObj.otype,
            srcUrl: outObj.srcUrl,

            dataId: outObj.dataId,
            dataType: outObj.dataType,
            idataType: inObj.dataType,
            idataId: inObj.dataId,

            oEditor: outObj.oEditor,
            iEditor: inObj.oEditor,

            data: inObj.data,
            cssClass: inObj.cssClass
        }
    }
    /**
     * 优先级数组 参数
     * @type {Array}
     */
    var prioritys = ["getValue","getText","getSumValue"];

    function getPriorityObj(simObj, buildObj) {
        if (simObj) {
            function checkPriority(str, str2) {
                var p1 = prioritys.indexOf(str);
                var p2 = prioritys.indexOf(str2);
                p1 == -1 && (p1 = 100);
                p2 == -1 && (p2 = 100);
                return p2 - p1 > 0;
            }
            if (checkPriority(simObj.type, buildObj.type)) {
                return simObj;
            }
        }
        return buildObj;
    }

    /**
     * 迭代遍历输入参数 （左边）
     * @param  {[type]} sin  [description]
     * @param  {[type]} pObj [description]
     * @return {[type]}      [description]
     */
    function iterIn(sin, pObj) {
        var that = this,
            outObj = that.out;
        for (var i = 0; i < sin.length; i++) {
            var inObj = sin[i];
            pObj && (inObj.pObj = pObj);
            if (inObj.items) {
                sin.lev && (inObj.items.lev = sin.lev);
                sin.pageId && (inObj.items.pageId = sin.pageId);
                sin.eventType && (inObj.items.eventType = sin.eventType);
                sin.otype && (inObj.items.otype = sin.otype);

                inObj.items.length && iterIn.call(that, inObj.items, inObj);
            } else {
                inObj.lev = sin.lev;
                inObj.pageId = sin.pageId;
                inObj.eventType = sin.eventType;
                if (sin.otype) {
                    inObj.otype = sin.otype;
                }
                inObj.oEditor = sin.oEditor;
                inObj.srcUrl = sin.srcUrl;

                if (inObj && inObj.type) {
                    var outTexts = outObj.text.split("-->"),
                        outText = outObj.text;
                    if (matchAll) {
                        outTexts.length > 1 && (outText = outTexts[outTexts.length - 1]);
                        outText = outText.replace("输入形参", "");
                    }
                    if (!inObj.datasetId) {
                        var sim = similarity(outText, pObj.text);
                        if (sim) {
                            if (matchAll && sim != 1) {
                                continue;
                            }
                            //修改为采用优先级
                            that.sim[sim] = getPriorityObj(that.sim[sim],buildObj(inObj, pObj, outObj));
                            //that.sim[sim] = buildObj(inObj, pObj, outObj);
                        }
                    }
                    if (inObj.datasetId) {
                        var selText = detectSelectObj[inObj.id];
                        if (selText && selText == pObj.text) {
                            var sim = similarity(outText, inObj.text);
                            if (sim) {
                                if (matchAll && sim != 1) {
                                    continue;
                                }
                                //修改为采用优先级
                                that.sim[sim] = getPriorityObj(that.sim[sim],buildObj(inObj, pObj, outObj));
                                //that.sim[sim] = buildObj(inObj, pObj, outObj);
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     *  迭代遍历输出参数  右边
     * @param  {[type]} sout [description]
     * @param  {[type]} pObj [description]
     * @return {[type]}      [description]
     */
    function iterOut(sout, pObj) {
        for (var i = 0; i < sout.length; i++) {
            var outObj = sout[i];
            pObj && (outObj.pObj = pObj);
            if (outObj.items) {
                sout.lev && (outObj.items.lev = sout.lev);
                sout.pageId && (outObj.items.pageId = sout.pageId);
                sout.eventType && (outObj.items.eventType = sout.eventType);
                sout.otype && (outObj.items.otype = sout.otype);

                outObj.items.length && iterOut(outObj.items, outObj);
            } else {
                outObj.lev = sout.lev;
                outObj.pageId = sout.pageId;
                outObj.eventType = sout.eventType;
                if (sout.otype) {
                    outObj.otype = sout.otype;
                }
                outObj.oEditor = sout.oEditor;
                outObj.srcUrl = sout.srcUrl;

                var ioObj = {
                    "out": outObj,
                    "sim": {}
                };

                if (pObj && allowMatch(pObj, outObj)) {
                    iterIn.call(ioObj, sin);
                    ioAry.push(ioObj);
                } else if (systemMatch(outObj)) {
                    var sysObj = storeSystemResource[outObj.eventType]["prop"][outObj.param];
                    if (sysObj) {
                        ioObj.sim[1] = buildObj(sysObj, {}, outObj);
                        ioAry.push(ioObj);
                    }
                }
            }
        }
    }

    /**
     * 匹配系统参数名称
     * @param  {[type]} outObj [description]
     * @return {[type]}        [description]
     */
    function systemMatch(outObj) {
        if (outObj.param && systemParam.indexOf(outObj.param) != -1) {
            return true;
        }
        return false;
    }
    /**
     * 允许匹配（不包含系统参数）
     * @param  {[type]} pObj   [description]
     * @param  {[type]} outObj [description]
     * @return {[type]}        [description]
     */
    function allowMatch(pObj, outObj) {
        if (outObj.param && systemParam.indexOf(outObj.param) != -1) {
            return false;
        }
        if (pObj.PARAMTYPE_ == "inParamDefined") {
            return true;
        }
        var selText = detectSelectOutObj[outObj.id];
        if (selText && pObj.text == selText) {
            return true;
        }
        /* if (outObj.eventType == "mtcrud" && pObj.text == "新增更新") {
             return true;
         }*/
        /*if (outObj.eventType) {
            return true;
        }*/
        return false;
    }

    iterOut(sout);
    var ioObj = null,
        simObj;
    var reObj = {};

    /**
     * 获取最大的相似度
     * @param  {[type]} simObj [description]
     * @return {[type]}        [description]
     */
    function getMaxSim(simObj) {
        var ret = 0;
        for (var p in simObj) {
            if (p - ret > 0) {
                ret = p;
            }
        }
        return ret;
    }

    for (var i = 0; i < ioAry.length; i++) {
        ioObj = ioAry[i];
        simObj = ioObj["sim"];
        if (!isEmptyObj(simObj)) {
            reObj[ioObj["out"]["id"]] || (reObj[ioObj["out"]["id"]] = []);
            reObj[ioObj["out"]["id"]].push(simObj[getMaxSim(simObj)]);
        }
    }

    return reObj;
};