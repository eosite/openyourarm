/**
 
 * Metronic icheckradio��չ
 
 */

(function($) {
	var plugin = 'icheckradio';
	var _rowIndex = 'row-index';
	var _dataId = 'dataId';
	var data_style = 'iradio_minimal-grey';

	function createRadiolist(target) {
		var $target = $(target);
		$target.focusout(function(event) {
			if ($target.closest('.ui-draggable')[0]) {
				var items = $target.find('.icheck-list').children();
				var datas = [];
				$.each(items, function(i, o) {
					datas.push({
						text: $(o).text()
					});
				});
				$target.attr('static-datas', JSON.stringify(datas));
			}
		});
		_loadData($target);

		$target.on('ifClicked', "input", function(event) {
			$target.find("label.selected").removeClass("selected");
			$(this).closest("label").addClass("selected");
			// pubsub.pub(e.pageEvent, "", event);
		});
	}

	function fetchValidate($target) {
		var opts = getOptions($target);
		var valiSave = $target.attr('valiSave') || opts.valiSave;
		opts.valiSave = valiSave;
		if (!valiSave) {
			return;
		}
		$target.removeAttr('valiSave');
		/**
		 * ����֤ fetch
		 */
		var mtTitle = $target.attr("mtTitle");
		var ruleId = $target.data("ruleId");
		var mtValidate = $target.data("mtValidate");
		var mtValiObj = $target.data("mtValiObj");

		$.extend(true, opts, {
			'mtTitle': mtTitle,
			'ruleId': ruleId,
			'mtValidate': mtValidate,
			'mtValiObj': mtValiObj
		});
	}

	function initValidate($target) {
		var opts = getOptions($target);
		if (!opts.valiSave) {
			return;
		}
		/**
		 * ����֤ restore
		 */
		var $select = $($target.find('input')[0]);
		$select.attr({
			mtTitle: opts.mtTitle,
			valiSave: opts.valiSave
		});
		$select.data('ruleId', opts.ruleId);
		$select.data('isICheck', opts.valiSave);
		$select.data("mtValidate", opts.mtValidate);
		$select.data("mtValiObj", opts.mtValiObj);

		setCustomRule($select, '');

		$('input[name=' + $select.attr('name') + ']').on('ifChanged', function(event) {
			$select.valid();
		});
	}
	function _loadData($target) {
		var opts = getOptions($target);
		data_style = $target.attr('data-style') || data_style;
		if(opts.asDemo){
			convertData($target);
			return;
		}
		if(eval($target.attr('static'))){
			opts.static = true;
			var defaultIndex = $target.attr("defaultIndex");
			if(defaultIndex != null){
				$target.icheckradio("check",defaultIndex-1)
			}
			return;
		}
		var ar = opts.ajax.read;
		ar.success = function(ret) {
			if (ret && ret instanceof Object) {
				addItems($target, ret);
			}
			var defaultIndex = $target.attr("defaultIndex");
			if(defaultIndex != null){
				$target.icheckradio("check",defaultIndex-1)
			}
		}
		ar.beforeSend = function() {
			clear($target);
		}
		ar.data = opts.ajax.parameterMap(ar.data);
		$.ajax(ar);
	}
	
	//联动
	function _loadooData($target,args) {
		var opts = getOptions($target);
		data_style = $target.attr('data-style') || data_style;
		//先取消选中
		uncheckAll($target[0]);
		if(opts.asDemo){
			convertData($target);
			return;
		}
		if(eval($target.attr('static'))){
			opts.static = true;
			var defaultIndex = $target.attr("defaultIndex");
			if(defaultIndex != null){
				$target.icheckradio("check",defaultIndex-1)
			}
			return;
		}
		var ar = opts.ajax.read;
		ar.success = function(ret) {
			if (ret && ret instanceof Object) {
				addItems($target, ret);
			}
			var defaultIndex = $target.attr("defaultIndex");
			if(defaultIndex != null){
				$target.icheckradio("check",defaultIndex-1)
			}
		}
		ar.beforeSend = function() {
			clear($target);
		}
		if(ar.data.params != null){
			if(typeof ar.data.params == 'string'){
				ar.data.params = JSON.parse(ar.data.params);
			}
		}else{
			ar.data.params = {};
		}
		ar.data.params = JSON.stringify($.extend(true,ar.data.params,args));
		$.ajax(ar);
	}
	
	
	
	function init($target, datas) {
		var sb = new StringBuffer();
		$.each(datas, function(index, val) {
			sb.append('<label><input id="' + this.id + '" '+_rowIndex+ '="'+this[_rowIndex]+'" text="'+this.text+'" name="' + this.name + '" type="radio" class="icheck" value="' + this.value + '" data-radio="' + this.style + '"> ' +'<span class="radioSpan">'+ this.text +'</span>'+ '</label>');
		});
		var id = $target.attr('id');
		var $ig = $('#' + id + ' .input-group');
		var $list = $($ig.html());
		$ig.empty();
		$list.empty();
		$list.append(sb.toString());
		$ig.append($list.prop('outerHTML'));
	}
	function clear($target) {
		var id = $target.attr('id');
		var $ig = $('#' + id + ' .input-group');
		var $list = $($ig.html());
		$ig.empty();
		$list.empty();
		$ig.append($list.prop('outerHTML'));
	}
	function getDataStyle($target) {
		var id = $target.attr('id');
		return $('#' + id + ' [data-radio^=iradio_]:first').attr('data-radio');
	}
	function addItems($target, datas) {
		var id = $target.attr('id');
		if (datas) {
			var opts = getOptions($target);
			var list = [];
			$.each(datas, function(i, data) {
				var item = {};
				//init row-index
				data[_rowIndex] = opts.__dataArray.length;
				data[_dataId] = id + '_' + data[_rowIndex];
				opts.__dataArray.push(data);
				//data = $.extend(data, {"index":i});
				var value = data[$target.attr('data-value-field')] || '';
				var text = data[$target.attr('data-text-field')] || '';
				item.id = data[_dataId];
				item.name = id + '_in';
				item[_rowIndex] = data[_rowIndex];
				item.value = value;
				item.text = text;
				item.style = data_style;
				list.push(item);
			});
		}
		fetchValidate($target);
		init($target, list);
		$('#' + id + ' input').iCheck({
			radioClass: data_style
		});
		
		var opts = getOptions($target);
		if(opts.defaultSelection.length>0){
			$.each($("#"+id+" input"),function(i,o){
				var $input = $(o);
				var arr = opts.defaultSelection;
				for(var i=0;i<arr.length;i++){
					if(arr[i]==$input.val()){
						$input.iCheck('check');
					}
				}
			});
		}
		
		if($target.data("disabled") && $target.data("disabled")=="disabled"){
			methods.disabled($target,false);
		}
	
		initValidate($target);
	}
	
	function doWhileCheck(jq,arr){
		var $inputs = jq.find("input");
		$inputs.iCheck('uncheck');
		$.each($inputs,function(i,o){
			var $input = $(o);
			for(var i=0;i<arr.length;i++){	
				if(arr[i]==$input.val()){
					$input.iCheck('check');
				}
			}
		});
	}
	
	function convertData($target, options) {
		var id = $target.attr('id');
		var datas = JSON.parse($target.attr('static-datas'));
		var list = [];
		$.each(datas, function(i, o) {
			$.extend(true, o, {
				id: id + '_' + i,
				name: id + '_in',
				value: i,
				text: o.text,
				style: data_style
			});
			list.push(o);
		});
		init($target, list);
		$('#' + id + ' input').iCheck({
			radioClass: data_style
		});
	}

	function getOptions(target) {
		//var opts = $.data(target, plugin).options;
		return $(target).data(plugin) != undefined ? ($(target).data(plugin).options) : undefined;
	}

	function _attr(name, val) {
		return name + '="' + val + '"';
	}

	function getSelection(target) {
		var data = [];
		var opts = getOptions(target);
		var st = $(target).find('.input-group input');
		$.each(st, function(i, e) {
			var $input = $(e);
			
			if ($input.prop('checked')) {
				data.push({
					'data':opts.__dataArray[$input.attr(_rowIndex)],
					'name': $input.attr('name'),
					'value': $input.val(),
					'text': $input.attr("text")/*$input.parent().parent().text()*/
				});
			}
		});
		return data;
	}

	function disable(target, index) {
		var opts = getOptions(target);
		if(opts.static){
			$("#"+$(target).attr("id") + "_" + index).iCheck('disable');
		}else{
			$(target).find('#' + opts.__dataArray[index][_dataId]).iCheck('disable');
		}
	}

	function enable(target, index) {
		var opts = getOptions(target);
		if(opts.static){
			$("#"+$(target).attr("id") + "_" + index).iCheck('enable');
		}else{
			$(target).find('#' + opts.__dataArray[index][_dataId]).iCheck('enable');
		}
	}

	function check(target, index) {
		var opts = getOptions(target);
		if(opts.static){
			$("#"+$(target).attr("id") + "_" + index).iCheck('check');
		}else{
			$(target).find('#' + opts.__dataArray[index][_dataId]).iCheck('check');	
		}
	}

	function uncheck(target, index) {
		var opts = getOptions(target);

		var $check = null;
		if(opts.static){
			$check = $("#"+$(target).attr("id") + "_" + index);
		}else{
			$check = $(target).find('#' + opts.__dataArray[index][_dataId]);
			
		}
		$check.iCheck('uncheck');
		$check.closest("label.selected").removeClass("selected");
	}

	function uncheckAll(target){
		var $target = $(target);
		$target.find("div.checked input.icheck").iCheck('uncheck');
		$(target).find(".selected").removeClass("selected");
	}

	function getValue(target) {
		var val = "";
		var datas = getSelection(target);
		if (datas.length) {
			var vals = [];
			$.each(datas, function(index, data) {
				vals.push(data.value);
			});
			val = vals.join(',');
		}
		return val;
	}

	//��ʼ��
	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string') {
			return $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i, o) {
			var state = $.data(o, plugin);
			if (state) {
				state.tabs = [];
				$.extend(true, state.options, options);
			} else {
				$.data(o, plugin, {
					options: $.extend(true, {}, $.fn[plugin].defaults, options),
					target: o,
					columns: [],
					datas: param,
				});

			}
			createRadiolist(o);
		});
	};

	//�ⲿ���÷���
	var methods = $.fn[plugin].methods =  {
		loadData: function(jq, $target) {
			$target = $target || $jq[0];
		},
		getValue: function(jq) {
			return getValue(jq[0]);
		},
		setValue:function(jq, param){
			var opts = getOptions(jq);
			if(opts != undefined){
				if(typeof param == "number"){
					param = ""+param;
				}
				if(param){
					opts.defaultSelection = param.split(",");
				}
				doWhileCheck($(jq),opts.defaultSelection);
			}
			
		},
		//是否启用    
		disabled:function(jq,bl){
			$(jq).attr("data-disabled","disabled");
			var $inputs = $(jq).find("input");
			if(!bl){
				$.each($inputs,function(i,o){
					var $input = $(o);
					$input.attr("disabled","disabled");	
				});
			}else{
				$.each($inputs,function(i,o){
					var $input = $(o);
					$input.removeAttr("disabled");
				});
			}
		},
		
		//联动事件
		linkage: function($id,args){
			_loadooData($id,args);
		},
		
		getSelection: function(jq) {
			return getSelection(jq[0]);
		},
		disable: function(jq, index) {
			disable(jq[0], index);
		},
		enable: function(jq, index) {
			enable(jq[0], index);
		},
		check: function(jq, index) {
			check(jq[0], index);
		},
		uncheck: function(jq, index) {
			uncheck(jq[0], index);
		}
	};
	$.fn[plugin].defaults = {
		defaultSelection:[],
		asDemo: false,
		__dataArray: [],
		width: 'auto',
		height: 'auto',
		datas: null,
		total: 0,
		ajax: {
			read: {
				url: '',
				type: 'POST',
				dataType: 'JSON',
				data: {},
				contentType: "application/json",
				async: true
			},
			save: {
				url: '',
				type: 'POST',
				data: {},
				success: function(e) {}
			},
			destroy: {
				url: '',
				type: 'POST',
				data: {},
				success: function(e) {}
			},
			parameterMap: function(params) {
				return params;
			},
			schema: {
				data: 'data',
				total: 'total'
			}
		},
		onLoad: function(target) {
			//loadList(target);
		}
	};
})(jQuery);