//获取当前地址栏的url的开头 http://ip:port/
function getContextPath() {
	var _href = window.parent.location.href;
	var re = /(https?:\/\/)?(\w+\.?)+(\:\d+)?(\/\w+)?/; // 将匹配
	// 上面的分组都是捕获性分组
	var arr = re.exec(_href);
	if (arr) {
		return arr[4];
	} else {
		return '';
	}
};

function getParameter(name){
    var params = {};
    var search = window.location.search;
    if(search && search.length > 1){
      search = search.substring(1);
      search = search.split("&");
      var s;
      $.each(search, function(i, v){
        s = v.split("=");
        params[s[0]] = s[1];
      });
    }
    return params[name];
}


function callBack2(val){
	var fn = window.getParameter("fn"), tragetId = window.getParameter("tragetId");
	var $parent = window.opener || window.parent;
	if(fn && $parent[fn]){
		$parent[fn].call($parent.document.getElementById("tragetId"), val);
	}
}



/**
 * alert("我叫{name},今年{age}岁,现在读{0}".format("幼儿园", {name : '小新', age : 5}));
 * 
 * @returns {String}
 */
String.prototype.format = function() {
	var str = this, arg;
	if (str) {
		for (var i = 0; i < arguments.length; i++) {
			arg = arguments[i];
			if (typeof arg === 'object') {
				str = mt.format(str, arg);
			} else {
				str = str.replace(new RegExp("\\{" + (i) + "\\}", "g"), arg);
			}
		}
	}
	return str;
};

function StringBuffer(str) {
	this.collection = new Array();
	this.appendFormat.apply(this, arguments);
}

StringBuffer.prototype = {
	constructor : StringBuffer,
	size : function() {
		return this.collection.length;
	},
	append : function(str) {
		this.collection.push(str);
		return this;
	},
	appendFormat : function() {
		if (arguments.length == 0)
			return this;
		var str = arguments[0];
		if (arguments.length > 1)
			str = str.format.apply(str, Array.prototype.slice
					.call(arguments, 1));
		return this.append(str);
	},
	toString : function(split) {
		return this.collection.join(split ? split : '');
	}
};