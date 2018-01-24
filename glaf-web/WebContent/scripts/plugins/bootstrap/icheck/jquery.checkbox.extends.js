/**

 * Metronic icheckbox��չ

 */

(function($){

	var plugin = 'icheckbox';

	var _rowIndex = 'row-index';

	var _dataId = 'dataId';

	var data_style = 'icheckbox_minimal-grey';



	function createChecklist(target){
		var $target = $(target);

		$target.focusout(function(event) {
			if($target.closest('.ui-draggable')[0]){
				var items = $target.find('.icheck-list').children();
				var datas = [];
				$.each(items, function(i, o) {
					datas.push({text: $(o).text()});
				});
				$target.attr('static-datas', JSON.stringify(datas));
			}
		});

		_loadData($target);
		var opts = getOptions($target);
		if(opts.checkall){
	        checkAll($target);
		}

		$target.on('ifClicked', "input", function(event) {
			$(this).closest("label").toggleClass("selected");
		});
	}

	function fetchValidate($target){
		var opts = getOptions($target);
		var valiSave = $target.attr('valiSave') || opts.valiSave;
		opts.valiSave = valiSave;
		if(!valiSave){
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
	function initValidate($target){
		var opts = getOptions($target);
		if(!opts.valiSave){
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

		$('input[name='+$select.attr('name')+']').on('ifChanged', function(event){
			$select.valid();
		});
	}
	//复选框从数据集查询出结果方法
	function _loadData($target){

		var opts = getOptions($target);

		data_style = $target.attr('data-style') || data_style;
		if(opts.asDemo){
			convertData($target);
			return;
		}
		if(eval($target.attr('static'))){
			opts.static = true;
			return;
		}

		var ar = opts.ajax.read;

		ar.success = function(ret){

			if(ret && ret instanceof Object){

				addItems($target,ret);
				opts.loadding = false;
			}

		}

		ar.beforeSend = function(){
			opts.loadding = true;
			clear($target);

		}

		ar.data = opts.ajax.parameterMap(ar.data);

		$.ajax(ar);

	}

	//复选框联动事件调用的方法
	function _loadooData($target,args){

		var opts = getOptions($target);

		data_style = $target.attr('data-style') || data_style;
		if(opts.asDemo){
			convertData($target);
			return;
		}
		if(eval($target.attr('static'))){
			opts.static = true;
			return;
		}
		
		var ar = opts.ajax.read;
		ar.success = function(ret){

			if(ret && ret instanceof Object){

				addItems($target,ret);

			}
			opts.loadding = false;	//加载中

		}

		ar.beforeSend = function(){
			opts.loadding = true;	//加载中
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

	


	function init($target, datas){

		var sb = new StringBuffer();

		$.each(datas, function(index, val) {

			sb.append('<label><input id="'+this.id+'" '+_rowIndex+ '="'+this[_rowIndex]+'" text="'+this.text+'" name="'+this.name+'" type="checkbox" class="icheck" value="'+this.value+'" data-checkbox="'+this.style+'"> '+'<span class="contentSpan">'+this.text+'</span> </label>');

		});
		var opts = getOptions($target);
		
        
		var id = $target.attr('id');

		var $ig = $('#'+id+' .input-group');

		var $list = $($ig.html());

		$ig.empty();

		$list.empty();

		$list.append(sb.toString())

		$ig.append($list.prop('outerHTML'));
		if(opts.columnNumber != undefined && opts.columnNumber != 0){
			$.each($target.find(".icheck-inline label"),function(i,el){			
					if((i + 1) % opts.columnNumber == 0){
						el.outerHTML = el.outerHTML + "<br / >";
					}	
			});
		}
		opts.checkboxwidth != undefined && opts.checkboxwidth != "" ? $target.find(".icheck-inline label").css("width",opts.checkboxwidth) : null;
		opts.checkboxheight != undefined && opts.checkboxheight != "" ? $target.find(".icheck-inline label").css("height",opts.checkboxheight) : null;
        if(opts.icheckSet != undefined){
        	$.each(eval("("+opts.icheckSet+")"),function(i,$obj){
        		$target.find(".icheck-inline label").css("fontSize",$obj.fontSize + "px");
        		$target.find(".icheck-inline label").css("color",$obj.textColor);
        		
        		$obj.fontWeight != undefined && $obj.fontWeight == "true" ? $target.find(".icheck-inline label").css("fontWeight","bold") : null;
        		$obj.fontStyle != undefined && $obj.fontStyle == "true" ? $target.find(".icheck-inline label").css("fontStyle","italic") : null;
        	})
        } 
	}


	function clear($target){

		var id = $target.attr('id');

		var $ig = $('#'+id+' .input-group');

		var $list = $($ig.html());

		$ig.empty();

		$list.empty();

		$ig.append($list.prop('outerHTML'));

	}



	function getDataStyle($target){

		var id = $target.attr('id');

		return $('#'+id+' [data-checkbox^=icheckbox_]:first').attr('data-checkbox');

	}



	function addItems($target,datas){

		var id = $target.attr('id');

		if(datas){

			var opts = getOptions($target);

			var list = [];

			$.each(datas, function(i, data){

				var item = {};

				//init row-index

				data[_rowIndex] = opts.__dataArray.length;

				data[_dataId] = id+'_'+data[_rowIndex];

				opts.__dataArray.push(data);



				//data = $.extend(data, {"index":i});

				var value = data[$target.attr('data-value-field')] ||'';

				var text = data[$target.attr('data-text-field')] ||'';

				

				item.id = data[_dataId];
				item[_rowIndex] = data[_rowIndex];
				item.name = id+'_in';

				item.value = value;

				item.text = text;

				item.style = data_style;

				list.push(item);

			});

		}

		fetchValidate($target);

		init($target, list);

		$('#'+id+' input').iCheck({checkboxClass: data_style});
	
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

		if(opts.checkAll){
			$("#"+id+" input").iCheck("check");
		}

		if(opts.defaultCompare){
			var key = opts.defaultCompare.key;
			var values = opts.defaultCompare.values;	//为数组，即[]
			var index = opts.defaultCompare.index;
			var type = opts.defaultCompare.type;

			if(key){
				$.each(opts.__dataArray,function(i,row){
					var pas = values;
					for (var j = 0; j < pas.length; j++) {
						if (row[key] == pas[j]) {
							$target.find('#'+row[_dataId]).iCheck(type);	
						}
					}
				})
			}else{
				$.each($("#"+id+" input"),function(i,o){
					var $input = $(o);
					var arr = values;
					for(var i=0;i<arr.length;i++){	
						if(arr[i]==$input.val()){
							$input.iCheck(type);
						}
					}
				});
			}
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

	function convertData($target, options){
		
		var id = $target.attr('id');

		var datas = JSON.parse($target.attr('static-datas'));

		var list = [];

		$.each(datas, function(i, o) {

			$.extend(true, o, {

				id : id+'_'+i,

				name : id+'_in',

				value : i,

				text : o.text,

				style: data_style

			});

			list.push(o);

		});

		init($target, list);
		
		$('#'+id+' input').iCheck({checkboxClass: data_style});

	}



	function getOptions(target){

		//var opts = $.data(target, plugin).options;

		return $(target).data(plugin).options;

	}



	function _attr(name, val){

		return name+'="'+val+'"';

	}



	function getSelection(target){
		
		var data = [];
		var opts = getOptions(target);
		var st = $(target).find('.input-group input');

		$.each(st, function(i, e) {

			var $input = $(e);

			if($input.prop('checked')){
				data.push({'data':opts.__dataArray[$input.attr(_rowIndex)],'name':$input.attr('name'), 'value':$input.val(), 'text':$input.attr("text")/*$input.parent().parent().text()*/});
			}


		});

		return data;

	}



	function disable(target, index){

		var opts = getOptions(target);

		if(opts.static){
			$("#"+$(target).attr("id") + "_" + index).iCheck('disable');
		}else{
			$(target).find('#'+opts.__dataArray[index][_dataId]).iCheck('disable');
		}

	}



	function enable(target, index){

		var opts = getOptions(target);

		if(opts.static){
			$("#"+$(target).attr("id") + "_" + index).iCheck('enable');
		}else{
			$(target).find('#'+opts.__dataArray[index][_dataId]).iCheck('enable');
		}

	}



	function check(target, index){

		var opts = getOptions(target);
		if(opts.static){
			$("#"+$(target).attr("id") + "_" + index).iCheck('check');
		}else{
			opts.defaultSelection = [];
			if(opts.loadding){
				//加载中，放入预留位置
				//加载中，
				opts.defaultCompare = {
					key : key,
					index : index,
					type : 'check',
				};
			}else{
				$(target).find('#'+opts.__dataArray[index][_dataId]).iCheck('check');	
			}
		}
	}

	function checkByCompare(target, key, value, type){
		var pas = value;
		var opts = getOptions(target);
		if(opts.static){
			return;
		}
		if(typeof pas == 'string'){
			pas = pas.split(",");
		}
		if($.isArray(pas)){
			
			if(opts.loadding){
				//加载中，
				opts.defaultCompare = {
					key : key,
					values : pas,
					type : type
				};
				// opts.defaultCompare[type] = true;
			}else{
				$.each(opts.__dataArray,function(i,row){
					for (var j = 0; j < pas.length; j++) {
						if (row[key] == pas[j]) {
							$(target).find('#'+row[_dataId]).iCheck(type);	
						}
					}
				})
			}
		}
	}

	function uncheck(target, index){

		var opts = getOptions(target);

		if(opts.static){
			$("#"+$(target).attr("id") + "_" + index).iCheck('uncheck');
		}else{
			$(target).find('#'+opts.__dataArray[index][_dataId]).iCheck('uncheck');
		}
	}



	function checkAll(target){
		var opts = getOptions(target);

		var inputs = $(target).find('.input-group input');

		if(opts.loadding){
			opts.checkAll = true;
		}else{
			inputs.attr("isCheckAll","isCheckAll");
			for(var i = 0 ; i < inputs.length ; i++){
				var e = inputs[i];
				if(i == inputs.length-1){
					inputs.removeAttr("isCheckAll");
				}
				$(e).iCheck('check');
			}
		}
	}



	function uncheckAll(target){

		var inputs = $(target).find('.input-group input');

		$.each(inputs, function(i, e) {

			$(e).iCheck('uncheck');

		});
		$(target).find(".selected").removeClass("selected");
	}



	function invert(target, index){

		var objs = $(target).find('.input-group .'+data_style);

		$.each(objs, function(i, e) {

			var $input = $(e).find('input:first');

			if($(e).hasClass('checked')){

				$input.iCheck('uncheck');

			}else{

				$input.iCheck('check');

			}

		});

	}

	function getValue(target){
		var val = "";
		var datas = getSelection(target);
		if(datas.length){
			var vals = [];
			$.each(datas, function(index, data) {
				vals.push(data.value);
			});
			val = vals.join(',');
		}
		return val;
	}



	//��ʼ��

	$.fn[plugin] = function(options, param){

	    if (typeof options == 'string'){

			return $.fn[plugin].methods[options](this, param);

		}

		options = options || {};

		return this.each(function(i,o){

			var state = $.data(o, plugin);

			if (state) {

				state.tabs = [];

				$.extend(true,state.options,options);

			} else {

				$.data(o, plugin, {

					options : $.extend(true,{},$.fn[plugin].defaults, options),

					target : o,

					columns : [],

					datas : param,

				});

			}

			createChecklist(o);

		});

	};

	

	//�ⲿ���÷���

	var methods = $.fn[plugin].methods = {

		loadData: function(jq, $target){

			$target = $target || $jq[0];

		},

		getValue: function(jq){
			return getValue(jq[0]);
		},
		
		setValue:function(jq, param){
			var opts = getOptions(jq);
			if(param){
				opts.defaultSelection = param.split(",");
			}
			doWhileCheck($(jq),opts.defaultSelection);
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
		
		getSelection:function(jq){

			return getSelection(jq[0]);

		},

		disable:function(jq, index){

			disable(jq[0], index);

		},

		enable:function(jq, index){

			enable(jq[0], index);

		},

		check:function(jq, index){

			check(jq[0], index);

		},

		checkByCompare : function(jq, param){

			checkByCompare(jq[0], param.key, param.value, param.type);

		},

		uncheck:function(jq, index){

			uncheck(jq[0], index);

		},

		checkAll:function(jq){

			checkAll(jq[0]);

		},

		uncheckAll:function(jq){

			uncheckAll(jq[0]);

		},

		invert:function(jq){

			invert(jq[0]);

		}

	};

	

	$.fn[plugin].defaults = {
		
		defaultSelection:[],

		asDemo:false,

		valiSave:false,

		__dataArray : [],

		width: 'auto',

		height: 'auto',

		datas : null,

		total:0,

        ajax : {

            read : {

                url : '',

                type : 'POST',

                dataType : 'JSON',

                data : {},

                contentType : "application/json",

                async : true

            },

            save : {

                url : '',

                type : 'POST',

                data : {},

                success : function(e){}

            },

            destroy : {

                url : '',

                type : 'POST',

                data : {},

                success : function(e){}

            },

            parameterMap: function(params){

            	return params;

            },

            schema : {

            	data : 'data',

            	total : 'total'

            }

        },

		onLoad: function(target){

			//loadList(target);

		}

	};

})(jQuery);