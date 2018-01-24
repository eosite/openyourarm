/**

 * Bootstrap Carousel

 */

(function($){

	var plugin = 'carouselbt';

	var _rowIndex = 'row-index';

	var _dataId = 'dataId';

	var data_style = 'iradio_minimal-grey';



	function createCarousel(target){

		var $target = $(target),
		    datas = $(target).data(plugin).options.datas;
		if(datas && datas != undefined){
			var ol = "<div id='myCarousel' class='carousel slide'>"
					+ "<ol class='carousel-indicators'></ol><div class='carousel-inner'></div>"
					+ "<a class='carousel-control left' href='#myCarouse' data-slide='prev'><i class='glyphicon glyphicon-chevron-left'></i></a>"
					+ "<a class='carousel-control right' href='#myCarouse' data-slide='next'><i class='glyphicon glyphicon-chevron-right'></i></a></div>";
			$target.append(ol);
			_loadStaticData($target, datas);
		}
		   
		else 
		  _loadData($target);

	}
    function _loadStaticData($target,datas){
    	addItems($target, datas);
    }


	function _loadData($target){

		var opts = getOptions($target);

		data_style = $target.attr('data-style') || data_style;

		if(opts.asDemo || eval($target.attr('static'))){

			convertData($target);

			return;

		}



		var ar = opts.ajax.read;

		ar.success = function(ret){
			ret = ret || {};
			var total = eval(ret.total)*1;
			if(!isNaN(total) && total>0){

				addItems($target, ret.data);

			}

		}

		ar.beforeSend = function(){

			clear($target);

		}

		ar.complete = function(){

			ar.data = JSON.parse(ar.data);

		}

		ar.data = JSON.stringify(ar.data);

		$.ajax(ar);

	}



	function init($target, datas){
		var opts = getOptions($target);

		var sb1 = new StringBuffer();
		var sb2 = new StringBuffer();
		var isTemplateModel = opts.adaptive && ( opts.adaptive.indexOf("template") != -1 ) ,
			render ;
		if(isTemplateModel){
			var currentTemplate = $.extend(true, {}, template);
			currentTemplate.config ("openTag", "#:");
			currentTemplate.config ("closeTag", "#");
			render = currentTemplate.compile(opts.template);
		}
		var vis = opts.vis,
		    j = 0;
		var spanWidth = parseInt(100 / (vis + 1)) + "%";
		$.each(datas, function(index, data) {
			if(vis != 0 && vis != undefined){
				if(index == (vis * j)){
					j++;
					
					var act = "";
					
					if(index == 0){
						 
						act = " active";
					}
					sb1.append('<li data-target="#myCarousel" data-slide-to="'+index+'" class="'+act+'"></li>');
					sb2.append('<div class="item'+act+'" style="text-align:center;">');
					//模板模式
					sb2.append("<ul class=\"thumbnails\">");
					
					sb2.append("<li class=\"span3\" style=\"width:"+spanWidth+";margin-left:10%\">");
		            sb2.append("<div class=\"thumbnail\">");  
	              
					if(isTemplateModel && render){
						sb2.append(render(data.data));
					}else{
						//高度自适应或者拉伸
						 sb2.append('<img class="'+opts.zoomView+' showWin '+(opts.adaptive?"carousel-inner":"")+'" src="'+data.url+'" onerror="this.onerror=null;this.src=\''+contextPath+'/scripts/designer/images/404.png\'" alt="404" style="display:inline-block;">');	
					}
					
					sb2.append("</div>");
					sb2.append("</li>");
					
					
				}
				else if(index < (vis * j)){
					
					if(index == (vis * j) - 1){
						
						sb2.append("<li class=\"span3\" style=\"width:"+spanWidth+"\">");
			            sb2.append("<div class=\"thumbnail\">");  
		              
						if(isTemplateModel && render){
							sb2.append(render(data.data));
						}else{
							//高度自适应或者拉伸
							 sb2.append('<img class="'+opts.zoomView+' showWin '+(opts.adaptive?"carousel-inner":"")+'" src="'+data.url+'" onerror="this.onerror=null;this.src=\''+contextPath+'/scripts/designer/images/404.png\'" alt="404" style="display:inline-block;">');	
						}
						sb2.append("</ul>");
				      	sb2.append('</div>');
					}
					else{
						sb2.append("<li class=\"span3\" style=\"width:"+spanWidth+"\">");
			            sb2.append("<div class=\"thumbnail\">");  
		              
						if(isTemplateModel && render){
							sb2.append(render(data.data));
						}else{
							//高度自适应或者拉伸
							 sb2.append('<img class="'+opts.zoomView+' showWin '+(opts.adaptive?"carousel-inner":"")+'" src="'+data.url+'" onerror="this.onerror=null;this.src=\''+contextPath+'/scripts/designer/images/404.png\'" alt="404" style="display:inline-block;">');	
						}
						
						sb2.append("</div>");
						sb2.append("</li>");
					}
				}
				
				

			}
			else{
				var act = "";
				
				if(index == 0){
					 
					act = " active";
				}
				sb1.append('<li data-target="#myCarousel" data-slide-to="'+index+'" class="'+act+'"></li>');
				sb2.append('<div class="item'+act+'" style="text-align:center;float:left;width:25%;margin-left:25px;">');
				//模板模式
          
	              
				if(isTemplateModel && render){
					sb2.append(render(data.data));
				}else{
					//高度自适应或者拉伸
					 sb2.append('<img class="'+opts.zoomView+' showWin '+(opts.adaptive?"carousel-inner":"")+'" src="'+data.url+'" onerror="this.onerror=null;this.src=\''+contextPath+'/scripts/designer/images/404.png\'" alt="404" style="display:inline-block;">');	
				}

		      	sb2.append('</div>');

			}
		});
		
		$target.find('.carousel-indicators').append(sb1.toString());
		$target.find('.carousel-inner').append(sb2.toString());

		var id = $target.attr('id');
		$target.append($target.find('#myCarousel').children()).addClass('carousel slide');
		$target.find('#myCarousel').remove();
		$target.find('.carousel-indicators').children().attr('data-target','#'+id);
		$target.find('.carousel-control').attr('href','#'+id);
		
		$target.find('div.carousel-inner').attr('style', $target.attr('style'));

		//自适应高度
		if(opts.adaptive === "autoheight" || opts.adaptive === "templateAutoHeight" ){
			$target.find('img').addClass("carousel-inner").css({
				"display":"initial",
				"width" : "auto",
				"max-width" : "100%",
				"height" : $target.height(),
				"max-height" : "100%",
			});
		}else if(opts.adaptive === "templateAdaptive"){
			//模板自适应
			$target.find('img').addClass("carousel-inner");
		}

		//自动轮播
		if(opts.autoPlay){
			$("#"+id).carousel('cycle');
		}
	}



	function clear($target){

		$target.find('.carousel-indicators').empty();
		$target.find('.carousel-inner').empty();

	}



	function getDataStyle($target){

		var id = $target.attr('id');

		return $('#'+id+' [data-radio^=iradio_]:first').attr('data-radio');

	}



	function addItems($target,datas){

		var id = $target.attr('id');

		var ruleId = $target.attr('ruleid');
		var mode = $target.attr('mode');
		var idField = $target.attr('idfield');
        if($target.data(plugin).options.datas && $target.data(plugin).options.datas != undefined){

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

				item.caption = "";

				item.url = contextPath + data.image;
                
				item.data = data ;
				
				list.push(item);

			});

		}
		else if(datas){
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
				item.databaseId = data.databaseId || "";

				item.caption = "";

				item.url = contextPath+'/mx/form/imageUpload?method=download2&databaseId='+item.databaseId+'&mode='+mode+'&id='+ruleId+'&rp='+data[idField];

				item.data = data ;
				
				list.push(item);

			});

		}
		init($target, list);

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

	}



	function getOptions(target){

		return $(target).data(plugin).options;

	}



	function _attr(name, val){

		return name+'="'+val+'"';

	}



	function getSelection(target){

		var data = [];

		var st = $(target).find('.input-group .checked');

		$.each(st, function(i, e) {

			var $input = $(e).find('input:first');

			data.push({'name':$input.attr('name'), 'value':$input.val(), 'text':$input.text()});

		});

		return data;

	}



	function disable(target, index){

		var opts = getOptions(target);

		$(target).find('#'+opts.__dataArray[index][_dataId]).iCheck('disable');

	}



	function enable(target, index){

		var opts = getOptions(target);

		$(target).find('#'+opts.__dataArray[index][_dataId]).iCheck('enable');

	}



	function check(target, index){

		var opts = getOptions(target);

		$(target).find('#'+opts.__dataArray[index][_dataId]).iCheck('check');

	}



	function uncheck(target, index){

		var opts = getOptions(target);

		$(target).find('#'+opts.__dataArray[index][_dataId]).iCheck('uncheck');

	}



	//³õÊ¼»¯

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

			createCarousel(o);

		});

	};

	

	//Íâ²¿µ÷ÓÃ·½·¨

	$.fn[plugin].methods = {

		loadData: function(jq, params){
			var opts = getOptions(jq),
				ar = opts.ajax.read;
			ar.data.params = JSON.stringify(params);
			_loadData(jq);
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

		uncheck:function(jq, index){

			uncheck(jq[0], index);

		},

		refresh:function(jq, index){
			clear(jq);
			_loadData(jq);
		}

	};

	

	$.fn[plugin].defaults = {

		
			
		autoPlay: true,

		zoomView: '',

		asDemo: false,

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