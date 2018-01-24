/**
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
};