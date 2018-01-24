/**
 * 参数转换成对象
 */


/**
 * 合并旧参数和对象参数定义的数据
 * @param  {[type]} objParamAry [可能是转换后的数组对象或者是原来的数组]
 * @param  {[type]} paramAry    [原来的数组添加]
 * @return {[type]}             [description]
 */
function mergeObjParams(objParamAry, paramAry) {
	if (objParamAry && paramAry && paramAry.length) {
		if (objParamAry.length && objParamAry[0]["type"]) {
			var source = objParamAry[0]["source"],
				paramKeys = [];
			$.each(source, function(j, sourceObj) {
				paramKeys.push(sourceObj["param"]);
			})
			$.each(paramAry, function(i, paramObj) {
				var param = paramObj["param"];
				if ($.inArray(param, paramKeys) == -1) {
					source.push(paramObj);
				}
			})
		} else {
			var tmp = {};
			$.each(objParamAry,function(i,v){
				tmp[v.param] = v;
			})
			$.each(paramAry, function(i, v) {
				if (!tmp[v.param]) {
					objParamAry.push(v);
				}
			});
			// $.merge(objParamAry, paramAry);
		}
	}
	return objParamAry;
}

/**
 * 输入输出参数初始化
 * @param  {[type]} v [description]
 * @return {[type]}   [description]
 */
function initObjParams(v,callback) {
	var value_ = JSON.parse(v.VALUE_);
	if (value_ && value_[0] && value_[0]["type"]) {
		$.each(value_[0]["source"], function(i, p) {
			p.text = p.name;
			p.id = v.NAME_;
			p.title = v.NAME_ + "-选中行-" + p.name;
			v.items.push(p);
		});

		if (value_[0]["objSource"]) {
			$.each(value_[0]["objSource"], function(i, p) {
				var objv = {
					text: p.name,
					items: []
				};
				$.each(JSON.parse(p.child), function(ii, pp) {
					pp.text = pp.name;
					pp.id = v.NAME_;
					pp.dataType = "obj";
					pp.dataId = p.param;
					objv.items.push(pp);
				});
				v.items.push(objv);
			});
		}

		if (value_[0]["arySource"]) {
			$.each(value_[0]["arySource"], function(i, p) {
				var objv = {
					text: p.name,
					items: []
				};
				//判断下集合中是否有对象。
				var p_child = JSON.parse(p.child)[0];
				if(p_child){
					p_child = p_child["child"];
				}else{
					p_child = "[]";
				}
				$.each(JSON.parse(p_child), function(ii, pp) {
					pp.text = pp.name;
					pp.id = v.NAME_;
					pp.dataType = "ary";
					pp.dataId = p.param;
					objv.items.push(pp);
				});
				v.items.push(objv);
			});
		}
	} else {
		$.each(value_, function(i, p) {
			p.text = p.name;
			p.id = v.NAME_;
			p.title = v.NAME_ + "-选中行-" + p.name;
			v.items.push(p);
		});
	}
}