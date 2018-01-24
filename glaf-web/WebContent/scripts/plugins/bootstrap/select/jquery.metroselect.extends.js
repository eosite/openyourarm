/**
 * Metronic select2扩展
 */
(function($){
	var plugin = 'metroselect';
	var _rowIndex = 'row-index';

	function createSelect(target){
		var $target = $(target);
		initEvents($target);
		var opts = getOptions($target);
		if(opts.static){
			_loadStaticData($target);
		}else{
			_loadData($target);	
		}
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

	function _loadStaticData($target){
		var id = $target.attr('id');
		var opts = getOptions($target);
		var selectedIndex = $target.attr('selectedindex');
		var $select = $target.find('select') ;
		$target.find("span.select2-container").remove();
		$select.select2({'width':'auto'});
	}

	var _loadData = function($target){
		var id = $target.attr('id');
		var opts = getOptions($target);
		if(opts.asDemo){
			convertData($target, {width: "auto"});
			return;
		}
		//设置为正在加载状态
		opts.loading = true;

		var ar = opts.ajax.read;
		ar.success = function(ret){
			if(ret && ret instanceof Object){
				addOptions($target,ret);
			}
			//设置为加载完成状态
			opts.loading = false;
			if(opts.preValue){
				select($target[0],opts.preValue);
				opts.preValue = null;
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
			var selectedIndex = $target.attr('selectedindex');
			var _data = [];
			if($target.find('select').attr('data-placeholder') == null){
				_data.push({"id":"　", "text":"　", "element":HTMLOptionElement});
			}else{          
				_data.push({"id":"", "text":"", "element":HTMLOptionElement});
				 
			}
			$.each(datas, function(i, data){
				var item = {};
				//init row-index
				data[_rowIndex] = opts.__dataArray.length;
				opts.__dataArray.push(data);

				//data = $.extend(data, {"index":i});
				var value = data[$target.attr('data-value-field')] ||'';
				var text = data[$target.attr('data-text-field')] ||'';
				if(value==""){
					value=Math.random();  
				}
				item.id = value ;
				item.data = data;
				item.text = text;
				item.element = HTMLOptionElement;
				if((i+1) === eval(selectedIndex)){
					item.selected = true;
					opts.defaultSelection = [value];
				}
				_data.push(item);
			});
			opts._selectData = _data;

			convertData($target, {data: _data});

		}
	}

	function convertData($target, options){
//		$.ajax({
//			url: contextPath+'/scripts/plugins/bootstrap/select/resource/metroselect_demo.html',
//			type: 'POST',
//			dataType: 'html'
//		})
//		.done(function(result) {
//			var id = $target.attr('id');
//			var disabled = !eval($target.attr('enabled'));
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
//			$target.find('select').select2(options).prop("disabled", disabled);
//
//			initValidate($target);
//
//		})
//		.fail(function() {
//			//console.log("error");
//		})
//		.always(function() {
//			//console.log("complete");
//		});
		
		var id = $target.attr('id');
		var $select = $target.find('select') ;
		
		var disabled = !eval($target.attr('enabled')) || ($select.attr("disabled") !== undefined);
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

		
		if(!$target.hasClass("hasSearchBox")){
			options.minimumResultsForSearch = -1;	
		}

		options.width = "auto" ;
		
		$select.select2(options);
		if(opts.defaultSelection.length>0){
			select($target, opts.defaultSelection);
		}

		initValidate($target);
		
		$select.prop("disabled", disabled);
		//IEDisable_($select, disabled);
//		if($target.find('select').attr('data-placeholder') != null){
//			 if($target.find(".select2-selection__placeholder")[0] == undefined){
//                	$target.find(".select2-selection__rendered").append("<span class='select2-selection__placeholder'>"+$target.find('select').attr('data-placeholder')+"</span>")
//                }
//                else{
//                	 $target.find(".select2-selection__placeholder")[0].innerHTML = $target.find('select').attr('data-placeholder');
//                }
//		}
	}
	
	/**
	 * 解决IE下disabled 问题
	 * @param $target
	 * @param disabled
	 * @returns
	 */
	function IEDisable_($select, disabled){
		var $span = $select.next("span.select2-container");
		var disableCls = "select2-container--disabled";
		var id = $select.attr("id"), $results = //
			$("#select2-" + id + "-results").closest("span.select2-container");
		if(disabled){
			$span.addClass(disableCls);
			$results.css({
				display:'none'
			});
		} else {
			$span.removeClass(disableCls);
			$results.css({
				display:'block'
			});
		}
	}

	function getOptions(target){
		//var opts = $.data(target, plugin).options;
		return $(target).data(plugin).options;
	}

	function getTargetObj(target){
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

	function getSelectData(target){
		var data = getOptions($(target))._selectData;
		return data;
	}
	
	function getValue(target){
		var id = $(target).attr('id');
		var data = $('#'+id).find('select').select2('data');
		if(data.length>0){
			return data[0].id;
		}
		return null;
	}

	function getText(target){
		var id = $(target).attr('id');
		var data = $('#'+id).find('select').select2('data');
		if(data.length>0){
			return data[0].text;
		}
		return null;
	}

	function select(target, value){
		getTargetObj(target).val(value).trigger('change');
	}

	function prepareSelect(target, value){
		var opts = getOptions(target);
		opts.defaultSelection = [value];
	}

	function unselect(target, value){
	}

	function clear(target){
		getTargetObj(target).val(null).trigger('change');
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
		getSelectData : function(jq){
			return getSelectData(jq[0]);
		},
		setValue:function(jq, param){
			
		},
		getOptions:function(jq,param){
			return getOptions(jq[0]);
		},
		//是否启用    
		disabled:function(jq, bl){
			var $select = $(jq).find("select");
			if(!bl){
			//	$select.attr("disabled","disabled");		
			}else{
				//$select.removeAttr("disabled");
			}
			
			/**
			 * select还在加载的时候改变diasble属性会出问题，导致
			 * select加载完以后都不能再disable
			 */
			window.setTimeout(function(){
				$select.prop("disabled", !bl);
			}, 0);
			
			
			//IEDisable_($select, !bl);
		},
		getText:function(jq){
			return getText(jq[0]);
		},
		reload: function(jq, params){
			reload(jq[0], params);
		},
		select: function(jq, value){
			if(jq[0]){
				var options = getOptions(jq[0]);	
				if(options && options.loading){
					options.preValue = value;
				}else{
					select(jq[0], value);
				}
			}
		},
		prepareSelect: function(jq, value){
			prepareSelect(jq[0], value);
		},
		unselect: function(jq, value){
			unselect(jq[0], value);
		},
		clear: function(jq){
			clear(jq[0]);
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
			onLoadSucess: function(ret){

			},
			onSelect : function(index, value, text, row){

			},
			onChange: function(){

			}
        }
		
	};
})(jQuery);