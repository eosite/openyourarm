/**
 * Metronic select2扩展
 */
(function($){
	var plugin = 'metroselect_m';
	var _rowIndex = 'row-index';

	function createSelect(target){
		var $target = $(target);
		initEvents($target);
		_loadData($target);
	}

	function fetchValidate($target){
		var opts = getOptions($target);
		var $select = $target.find('select');
		var valiSave = $select.attr('valiSave') || false;
		opts.valiSave = valiSave;
		if(!valiSave){
			return;
		}
		/**
		 * 表单验证 fetch
		 */
		var mtTitle = $select.attr("mtTitle");
		var ruleId = $select.data("ruleId");
		var mtValidate = $select.data("mtValidate");
		var mtValiObj = $select.data("mtValiObj");

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
		var id = $target.attr('id');
		/**
		 * 表单验证 restore
		 */
		var $select = $target.find('select');
		$select.attr({
			name: id,
			mtTitle: opts.mtTitle,
			valiSave: opts.valiSave
		});
		$select.data('ruleId', opts.ruleId);
		$select.data('isSelect2', opts.valiSave);
		$select.data("mtValidate", opts.mtValidate);
		$select.data("mtValiObj", opts.mtValiObj);

		// $select.change(function(event) {
		// 	$select.valid();
		// });

	}

	function initEvents($target){
		var opts = getOptions($target);
		if(opts.events){
			$.each(opts.events, function(k, fn){
				$target.bind(k, "select", function(e){
					var params = Array.prototype.slice.call(arguments, 1);
					fn.apply(e, params);
				});
			});
		}

		$target.on("change.select", 'select', function(e){
		});

	}

	var _loadData = function($target){
		var id = $target.attr('id');
		var opts = getOptions($target);
		if(opts.asDemo){
			convertData($target, {width: null});
			return;
		}

		var ar = opts.ajax.read;
		ar.success = function(ret){
			if(ret && ret instanceof Object){
				addOptions($target,ret);
			}
			if($.isFunction(opts.events.onLoadSucess)){
				opts.events.onLoadSucess(ret);
			}
			//$target.trigger("onLoad", [ret,9]);
		}
		ar.beforeSend = function(){
			$target.find("select").empty();
		}
		ar.complete = function(XHR, TS){

		}
		ar.data = opts.ajax.parameterMap(ar.data);
		$.ajax(ar);
	}

	function addOptions($target,datas){
		if(datas){
			var id = $target.attr('id');
			var opts = getOptions($target);
			var _data = [];
			_data.push({"id":"", "text":"", "element":HTMLOptionElement});
			$.each(datas, function(i, data){
				//init row-index
				data[_rowIndex] = opts.__dataArray.length;
				opts.__dataArray.push(data);

				//data = $.extend(data, {"index":i});
				var value = data[$target.attr('data-value-field')] ||'';
				var text = data[$target.attr('data-text-field')] ||'';
				_data.push({"id":value,"data":data, "text":text, "element":HTMLOptionElement});
			});

			convertData($target, {data: _data});

		}
	}

	function convertData($target, options){		
//		$.ajax({
//			url: contextPath+'/scripts/plugins/bootstrap/select/resource/metroselect_m_demo.html',
//			type: 'POST',
//			dataType: 'html'
//		})
//		.done(function(result) {			
//			var conf = {};
//			var designConfig = $target.attr('designConfig');
//			if(designConfig){
//				conf = $.parseJSON(designConfig);
//			}
//			var opts = getOptions($target);
//			$.extend(true, conf, {'asDemo':opts.asDemo,id:$target.attr("id")});
//			
//			var ret = ArtTemplateDataUtils.convertOne(conf, result);
//			
//			fetchValidate($target);
//
//			$target.empty();
//			$target.append($(ret).outer());
//			
//			options.width = "auto" ;
//			$target.find('select').select2(options);
//
//			initValidate($target);
//
//		})
//		.fail(function() {
//			//console.log("error");
//		})
//		.always(function() {
//			
//		});
		var conf = {};
		var designConfig = $target.attr('designConfig');
		if(designConfig){
			conf = $.parseJSON(designConfig);
		}
		var opts = getOptions($target);
		$.extend(true, conf, {'asDemo':opts.asDemo,id:$target.attr("id")});
	
		if(!conf.asDemo){
			$target.find("select.select2").html("");	
		}
		$target.find("span.select2-container").remove();
		
		fetchValidate($target);
		
		options.width = "auto" ;
		var $select = $target.find('select');
		$select.select2(options);
		if(opts.defaultSelection.length>0){
			select($target, opts.defaultSelection);
		}

		initValidate($target);
		//var disabled = !eval($target.attr('enabled')) || ($select.attr("disabled") !== undefined);
		var disabled = ($select.attr("disabled") !== undefined);
		
		$select.prop("disabled", disabled);
		
	}

	function getOptions(target){
		//var opts = $.data(target, plugin).options;
		return $(target).data(plugin).options;
	}

	function getTargetSelect2(target){
		var id = $(target).attr('id');
		return $('#'+id).find('select');
	}

	function reload(target, params){
		if(params){
			var opts = getOptions($(target));
			var ar = opts.ajax.read;
			$.extend(true, ar.data, {"params" : JSON.stringify(params)});
		}
//		var k,v="";
//		if (!isEmptyObject(params)) {
//			for(var k in params){
//				v = params[k];
//			}
//			prepareSelect(target, v);
//		}
		_loadData($(target));
	}

	function isEmptyObject(obj) { //空对象判断工具
		for ( var name in obj ) { 
			return false; 
		} 
		return true; 
	}

	function getValue(target){
		var id = $(target).attr('id');
		var data = $('#'+id).find('select').select2('data');
		var v="";
		if(data){
			$.each(data,function(i,o){
				v+=o.id+",";
			});
			v=v.substr(0,v.length-1);
		}
		if(v.length>0){
			return v;
		}
		return null;
	}

	function getText(target){
		var id = $(target).attr('id');
		var data = $('#'+id).find('select').select2('data');
		var v = "";
		if(data.length>0){
			$.each(data,function(i,o){
				v+=o.text+",";
			});
			v=v.substr(0,v.length-1);
			// return data[0].text;
		}
		if(v.length>0){
			return v;
		}
		return null;
	}

	function select(target, value){
		getTargetSelect2(target).val(value).trigger('change');
	}

	function prepareSelect(target, value){
		var opts = getOptions(target);
		var list = [];
		var strAry = "";
		if(value.indexOf(",") != -1){
			strAry = value.split(',');
		}else if(value.indexOf("，") != -1){
			strAry = value.split('，');
		}else{
			strAry = value.split(',');
		}
		$.each(strAry, function(index, val) {
			list.push(val);
		});
		opts.defaultSelection = list;
	}

	function unselect(target, value){
	}

	function clear(target){
		getTargetSelect2(target).val("　").trigger('change');
	}
	//value为index
	function selectIndex(target,val){
		var list = [];
		var opts = getOptions(target);
		var $target = $(target);
		var datas = opts.__dataArray;
		var valueText = $target.attr('data-value-field');
		if(typeof val === 'string'){
			var strAry = "";
			if(value.indexOf(",") != -1){
				strAry = value.split(',');
			}else if(value.indexOf("，") != -1){
				strAry = value.split('，');
			}else{
				strAry = value.split(',');
			}
			$.each(strAry, function(index, val) {
				var value = datas[val][valueText] || '';
				list.push(value);
			});
			select(target,list);
		}else if(typeof val === 'number'){
			var value = datas[val][valueText] || '';
			select(target,value);
		}
	}

	//初始化
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
			createSelect(o);
		});
	};
	
	//外部调用方法
	$.fn[plugin].methods = {
		loadData: function(jq, $target){
			$target = $target || $jq[0];
		},
		getValue:function(jq){
			return getValue(jq[0]);
		},
		//是否启用    
		disabled:function(jq, bl){
			var $select = $(jq).find("select");
			if(!bl){
				$select.attr("disabled","disabled");		
			}else{
				$select.removeAttr("disabled");
			}
		},
		getText:function(jq){
			return getText(jq[0]);
		},
		reload: function(jq, params){
			reload(jq[0], params);
		},
		select: function(jq, value){
			select(jq[0], value);
		},
		prepareSelect: function(jq, value){
			prepareSelect(jq[0], value);
		},
		unselect: function(jq, value){
			unselect(jq[0], value);
		},
		clear: function(jq){
			clear(jq[0]);
		},
		selectIndex: function(jq,value){
			selectIndex(jq[0],value);
		},
		getOptions:function(jq,param){
			return getOptions(jq[0]);
		}
	};
	
	$.fn[plugin].defaults = {
		defaultSelection:[],
		__dataArray : [],
		width: 'auto',
		height: 'auto',
		datas : null,
		total:0,
		asDemo:false,
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
        events : {
        	onLoad: function(ret){

			},
			onSelect : function(index, value, text, row){

			},
			onChange: function(){

			}
        }
		
	};
})(jQuery);
$.fn.select2.defaults.defaults.language.noResults = function(){return "无记录";}