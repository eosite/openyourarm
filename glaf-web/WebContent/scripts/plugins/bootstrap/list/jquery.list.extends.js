/**
 * Metronic List扩展
 */
(function($){
	var plugin = 'metrolist';
	//list风格
	var listStyle = {
		'default':'list-default',
		'simple':'list-simple',
		'todo':'list-todo',
		'news':'list-news'
	};
	var _groupIndex = 'group-index';
	var _itemIndex = 'item-index';
	function createList(target){

		loadList(target);
	}

	var _loadData = function($target){
		var opts = getOptions($target);
		var ar = opts.ajax.read;

		ar.url = opts.url;
		ar.data.rid = opts.ruleId;
		var parameterdata = {};
		if(opts.parameterdata){
			parameterdata = {params : opts.parameterdata};
		}
		ar.data = $.extend(true,parameterdata,ar.data);
		ar.success = function(ret){
			$target.find("ul li:last").unblock({     //解除加载中效果
	            onUnblock: function() {
	                $target.find("ul li:last").css('position', '');
	                $target.find("ul li:last").css('zoom', '');
	            }
	        });
			if(ret.data && ret.data instanceof Object){
//				ret = ret[opts.ajax.schema.data] || [];
				var data = handleTreeData($target,ret.data);
				var $body = $target.find("div.mt-list-container");
				if($body.hasClass(listStyle['simple']) || $body.hasClass(listStyle['default'])){
					if($body.hasClass('group')){
						addGroups($target, ret.data);
					}else{
						addItems($target, ret);
					}
				}else if($body.hasClass(listStyle['todo'])){
					addGroups($target, ret.data);
				}else if($body.hasClass(listStyle['news'])){
					addItems($target, ret);
				}
			}

			if(opts.draggable){
				$target.find(".mt-list-item:not(.more)").draggable(
			        {
			            connectToSortable: ".designer",
			            cursor: "move",
			            helper: function (event) {
			                var targetElem;
			                var elementName = $(this).text();
			                targetElem = $("<div class=\"elemtag\">" + elementName + "</div>");
			                targetElem.width($(this).width());
			                return targetElem;
			            },
						start: function (e, t) {
			               var $dragItem = $(e.target);
			               opts.dragItemNum = $dragItem.attr(_itemIndex);
			               
			            },
			        }
		        );
			}

			if(opts.droppable){
				var container = $target.find(".mt-list-container");
				if(container.height() < 400){
					container.height(400);
				}
			}
		}
		// ar.beforeSend = function(){
		// 	$target.find("div.mt-list-container").html($target.find(".scroll-top"));
		// }
//		ar.complete = function(XHR,TS){
//			ar.data = $.parseJSON(ar.data);
//		}
		firstHandle($target);
		loadParams(opts);
		if(opts.datas != null && opts.datas != undefined){
			var content = "<div class='mt-element-list'>"
					+ "<div class='mt-list-head list-simple bg-red font-white'>"
					+ "<div class='list-head-title-container'>"
					+ "<div class='list-date frame-variable' frame-variable='fv2'></div>"
					+ "<h3 class='list-title frame-variable' frame-variable='fv1'></h3>"
					+ "</div></div><div class='mt-list-container list-simple'>"
					+ "<ul></ul></div></div>",
				elementHtml = "";
			$($target).append(content);
            $.each(opts.datas,function(index,data){
            	elementHtml = elementHtml + "<li class='mt-list-item'>"
								+ "<div class='list-icon-container done'><i class='icon-check'></i></div>"
								+ "<div class='list-item-content'><h3 class='uppercase'><a href='javascript:;'>"+data.content+"</a>"
								+ "</h3></div></li>" 
            })
			$($target).find(".mt-list-container ul").append(elementHtml);
			$target.find(".mt-list-item:not(.more)").draggable(
			        {
			            connectToSortable: ".designer",
			            cursor: "move",
			            helper: function (event) {
			                var targetElem;
			                var elementName = $(this).text();
			                targetElem = $("<div class=\"elemtag\">" + elementName + "</div>");
			                targetElem.width($(this).width());
			                return targetElem;
			            },
						start: function (e, t) {
			               var $dragItem = $(e.target);
			               opts.dragItemNum = $dragItem.attr(_itemIndex);
			               
			            },
			        }
		        );
				var container = $target.find(".mt-list-container");
				if(container.height() < 400){
					container.height(400);
				}
		}
		else{
			$.ajax(ar);
		}
		
	}
	
	function firstHandle($target){
		var opts = getOptions($target),ar = opts.ajax.read;
		if (!ar.__success) { //第一次ajax 调用
			ar.__success = true;
			ar.__data = $.extend(ar.__data || {}, ar.data);// 原始参数保存
			$target.find("div.mt-list-container").html($target.find(".scroll-top"));//第一次清空模板
		}
	}
	function loadParams(opts){
		var ar = opts.ajax.read;
		var data = $.extend(true,{},ar.__data);
		if(opts.pageable){
			$.extend(data, {
				page : opts.page,
				pageSize : opts.pageSize
			});
		}
		ar.data = opts.ajax.parameterMap(data);
	}

	function handleTreeData($target,ret){
		var opts = getOptions($target);
		var r = [],tmpMap = [];
		var sNodes = ret,key = opts.columnsMap.indexKey,parentKey = opts.columnsMap.pIdKey,childKey = "children" ;
		
		for (i=0, l=sNodes.length; i<l; i++) {
			tmpMap[sNodes[i][key]] = sNodes[i];
		}
		for (i=0, l=sNodes.length; i<l; i++) {
			if (tmpMap[sNodes[i][parentKey]] && sNodes[i][key] != sNodes[i][parentKey]) {
				if (!tmpMap[sNodes[i][parentKey]][childKey])
					tmpMap[sNodes[i][parentKey]][childKey] = [];
				tmpMap[sNodes[i][parentKey]][childKey].push(sNodes[i]);
			} else {
				r.push(sNodes[i]);
			}
		}
		return r;
	}

	function _attr(name, val){
		return name+'="'+val+'"';
	}
	
	function reloadList(target, params){
		if(params){
			var opts = getOptions($(target));
			var ar = opts.ajax.read;
			$.extend(true, ar.data, {"params" : JSON.stringify(params)});
		}
		loadList(target);
	}
	function loadList(target){
		//list container
		var $target = $(target);
		var opts = getOptions($target);


		if($target.find(".mt-list-head .list-head-title-container").length){
			$target.find(".mt-list-head .list-head-title-container").empty().append(opts.mainTitle);
		}else{
			$target.find(".mt-list-container").css("border-top","1px solid #e5e5e5");
		}

		if(opts.titleAlign=="center"){   //对齐
			$target.find(".list-head-title-container").css("display","flex").css("justify-content","center");
		}else if(opts.titleAlign=="left"){
			$target.find(".list-head-title-container").css("display","flex").css("justify-content","flex-start");
		}else if(opts.titleAlign=="right"){
			$target.find(".list-head-title-container").css("display","flex").css("justify-content","flex-end");
		}
		$target.find(".mt-list-container").append('<div class="scroll-top"><i class="icon-arrow-up"></i></div>');
		//加载更多置顶按钮
		_loadData($target);
	}

	function getColumnData(opts, data){       //不解释
		var sb = new StringBuffer();
		var nameKey = opts.columnsMap.nameKey;
		var template;
		$.each(opts.columns, function(i, column){
			if(column.field==nameKey&&column.template){//如果要显示的字段定义了模板
				template = column.template;
				if($.isFunction(template)){
					template = template(data);
				}
				sb.append(template);
				return ;
			}
		});
		if(!template){
			sb.append(data[nameKey]);
		}
		return sb.toString();
	}

	function addGroups($target,datas){
		var $body = $target.find("div.mt-list-container");
		var sb = new StringBuffer();
		if(datas){
			var opts = getOptions($target);
			$.each(datas, function(i, data){
				//init group-index
				data[_groupIndex] = opts.__groupArray.length;
				opts.__groupArray.push(data);

				data = $.extend(data, {"index":i});
				var group = '';
				if($body.hasClass(listStyle['todo'])){
					group = getTodoGroupHtml($target,data);
				}else{
					group = getGroupHtml($target, data);
				}
				sb.append(group);
			});
		}
		$body.html(sb.toString());
	}

	function getTodoGroupHtml($target,data){
		if(!data){
			return '';
		}
		var opts = getOptions($target);
		var id = $target.attr("id");
		var collapseId = id+"_collapseId"+data.index;
		var state = "";
		var expanded = false;
		if(data.index==0){
			//state = "in";
			//expanded = true;
		}
		var sb = new StringBuffer();
		sb.append('<div class="list-todo-line"></div>');
			sb.append('<ul>');
				sb.append('<li class="mt-list-item">');
					sb.append('<div class="list-todo-icon bg-white">');
						sb.append('<i class="fa fa-database"></i>');
					sb.append('</div>');
					sb.append('<div class="list-todo-item">');
						sb.append('<a '+_attr(_groupIndex, data[_groupIndex])+' class="list-toggle-container font-white collapsed" data-toggle="collapse" href="#'+collapseId+'" aria-expanded="false">');
						sb.append('<div class="list-toggle done bg-dark">');
							sb.append('<div class="list-toggle-title bold"></div>');
							//sb.append('<div class="badge badge-default pull-right bg-white font-dark bold">3</div>');
							var cd = getColumnData(opts, data);
							sb.append(cd);
							
						sb.append('</div>');
						sb.append('</a>');
						sb.append('<div class="task-list panel-collapse collapse" id="'+collapseId+'" aria-expanded="false" style="height: 2px;">');
							sb.append('<ul>');

							sb.append('</ul>');
							/**
							sb.append('<div class="task-footer bg-grey">');
								sb.append('<div class="row">');
									sb.append('<div class="col-xs-6">');
										sb.append('<a class="task-trash" href="javascript:;">');
										sb.append('<i class="fa fa-trash"></i>');
										sb.append('</a>');
									sb.append('</div>');
									sb.append('<div class="col-xs-6">');
										sb.append('<a class="task-add" href="javascript:;">');
											sb.append('<i class="fa fa-plus"></i>');
										sb.append('</a>');
									sb.append('</div');
								sb.append('</div>');
							sb.append('</div');
							*/
						sb.append('</div>');
					sb.append('</div>');
				sb.append('</li>');
			sb.append('</ul>');
		return sb.toString();
	}

	function getGroupHtml($target,data){
		if(!data){
			return '';
		}
		var opts = getOptions($target);
		var id = $target.attr("id");
		var collapseId = id+"_collapseId"+data.index;
		var state = "";
		var expanded = false;
		if(data.index==0){
			//state = "in";
			//expanded = true;
		}
		var sb = new StringBuffer();
		sb.append('<a '+_attr(_groupIndex, data[_groupIndex])+' class="list-toggle-container" data-toggle="collapse" href="#'+collapseId+'" aria-expanded="'+expanded+'">');
		sb.append('<div class="list-toggle done">');
		var cd = getColumnData(opts, data);
		sb.append(cd);
		//sb.append('<span class="badge badge-default pull-right bg-white font-green bold">2</span>')
		sb.append('</div>');
		sb.append('</a>');
		sb.append('<div class="panel-collapse collapse '+state+'" id="'+collapseId+'">');
		sb.append('<ul>');
		sb.append('</ul>');
		sb.append('</div>');
		return sb.toString();
	}

	function addItems($target, ret){     //不解释
		var datas = ret.data,$ul=$('<ul>');
		var opts = getOptions($target);
		if(datas){
			var $body = $target.find("div.mt-list-container");
			var sb = new StringBuffer();
			$.each(datas, function(i, data){
				data[_itemIndex] = opts.__itemArray.length;
				opts.__itemArray.push(data);
				var item = getItemHtml($target, data);
				sb.append(item);
			});
			if($body.find("ul").length){   //加载第二页以后
				$ul = $body.find("ul");
				if($ul.find("li.more")){
					$ul.find("li.more").before(sb.toString());
				}else{
					$ul.append(sb.toString());
				}
			}else{             //第一ye加载
				$ul.append(sb.toString());
				if(opts.pageable){
					var $li = $('<li class="text-center more"><a href="javascript:;">加载更多</a></li>');
					$li.addClass($ul.find("li:last").attr("class"));
					$li.find("a").attr("total",ret.total);
					$ul.append($li);
				}
			}
		}
		$body.append($ul);

		
		$target.find(".scroller").scroll(function(e) {
			if ($(this).scrollTop() > $body.innerHeight()) {
				$target.find(".mt-list-container .scroll-top").fadeIn(500);
			} else {
				$target.find(".mt-list-container .scroll-top").fadeOut(500);
			}
			// if($(this).scrollTop() == $(this).innerHeight()){   //加载中效果
			// 	var $li = $('<li></li>');
			// 	$li.addClass($body.find("ul li:last").attr("class"));
			// 	$body.find("ul").append($li);
			// 	App.blockUI({
   //                  target: $li,
   //                  animate: true,
   //                  overlayColor: 'none'
   //              });
          		//window.setTimeout(function() {
		        //     $("#div").unblock({
		        //         onUnblock: function() {
		        //             $("#div").css('position', '');
		        //             $("#div").css('zoom', '');
		        //         }
		        //     });
		        // }, 2000);
			// }
		});

	}
	
	function getItemHtml(target,data){
		if(!data){
			return '';
		}
		var opts = getOptions($(target));
		var sb = new StringBuffer();
		var itemstyle = opts.listStyle;
		if(opts.listStyle=='todo'){
			itemstyle = 'task-list-item';
		}else{
			itemstyle = 'mt-list-item';
		}
		sb.append('<li class="'+itemstyle+'" '+_attr(_itemIndex, data[_itemIndex])+'>');
		var cd = getColumnData(opts, data);
		sb.append(cd);
		sb.append('</li>');
		return sb.toString();
	}
	
	function addItem(target,data){
		var item = getItemHtml(target,data);
		$(item).appendTo($(target).find('ul'));
	}

	function removeItem(target,params){
		$(target).find('.mt-list-container>ul>li ['+_itemIndex+'='+params.itemIndex+']').remove();
	}

	function getOptions(target){
		//var opts = $.data(target, plugin).options;
		return $(target).data(plugin).options;
	}

	function initEvents(target){
		var $target = $(target),opts = getOptions($target);
		$(target).on("click.a.list-toggle-container", 'a.list-toggle-container', function(e){
			_loadItem(target,this)
			return true;
		});

		// $(target).on("scroll.scroller",".scroller",function(e) {
		// 	if ($(this).scrollTop() > 50) {
		// 		$(target).find(".mt-list-container .scroll-top").fadeIn(500);
		// 	} else {
		// 		$(target).find(".mt-list-container .scroll-top").fadeOut(500);
		// 	}
		// });

		if(opts.droppable){
			$(target).find(".mt-list-container").droppable({
				drop: function( event, ui ) {
					// alert("drap");
					if($.isFunction(opts.onDroppEnd)){
						opts.onDroppEnd();
					}
				}
			});
		}

		

		$(target).on("click.a.list-toggle-container", 'a.list-toggle-container', function(e){
			_loadItem(target,this)
			return true;
		});

		$(target).on("click.scroll-top",".mt-list-container .scroll-top",function(e) {
			e.preventDefault();
			$(target).find(".mt-list-container.scroller").animate({scrollTop: 0}, 500);
			$(target).find(".mt-list-container.scroller").slimScroll({ scrollTo: '0px' });
			return false;
		});

		$(target).on("click.more",".mt-list-container li.more a",function(e) {  //加载更多
			e.preventDefault();
			
			var total = parseInt($(this).attr("total"));
			if(opts.pageSize*opts.page<total){
				opts.page = parseInt(opts.page) + 1 ;
			}else{
				alert("友情提示：没有更多了。");
				return false;
			}
			var $li = $target.find(".mt-list-container ul li:last"); //加载中效果
			App.blockUI({
                target: $li,
                animate: true,
                overlayColor: 'none'
            });
            _loadData($target);
			return false;
		});
	}

	function _loadItem(target,trigger){
		var $trigger = $(trigger);
		if($trigger.data('requested')){
			return;
		}
		var id = $trigger.attr("href");
		var opts = getOptions(target);
		var rowIndex = $trigger.attr(_groupIndex);
		var data = opts.__groupArray[rowIndex];

		var ar = opts.ajax.read;
		ar.success = function(ret){
			if(ret && ret instanceof Object){
				ret = ret[opts.ajax.schema.data];
				var sb = new StringBuffer();
				$.each(ret, function(){
					var item = getItemHtml($(target), this);
					sb.append(item);
				});
				$(id+' ul').append(sb.toString());
				$trigger.data('requested', true);
			}
		}
		
		ar.data = $.parseJSON(ar.data);
		$.extend(ar.data, {'parentId':data[opts.columnsMap.indexKey]});
		ar.data = opts.ajax.parameterMap(ar.data)
		$.ajax(ar);
	}
	
	//初始化
	$.fn[plugin] = function(options, param){
		
		
		
	    if (typeof options == 'string'){
			return $.fn[plugin].methods[options](this, param);
		}
		
		var columns = $(this).data("columns");
		if(columns){
			columns = eval('('+columns+')');
			options.columns = columns;
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
					datas : param
				});
			}
			createList(o);
			initEvents(o);
		});
	};
	
	//外部调用方法
	$.fn[plugin].methods = {
		reload: function(jq, params){
			reloadList(jq[0],params);
		},
		getData:function(jq, $target){
			return $.data(jq[0], plugin).list;
		},
		resize:function(jq){
			
		},
		addItem:function(jq, data){
			addItem(jq[0], data)
		},
		removeItem:function(jq, params){
			removeItem(jq[0], params);
		},
		getDrapRow:function(jq){
			var opts = getOptions(jq[0]);
			var dragItemNum = opts.dragItemNum;
			var __groupArray = opts.__groupArray;
			var __itemArray = opts.__itemArray;
			var $body = jq.find("div.mt-list-container");
			if(dragItemNum){
				if($body.hasClass(listStyle['simple']) || $body.hasClass(listStyle['default'])){
					if($body.hasClass('group')){
						return __groupArray[dragItemNum];
					}else{
						return __itemArray[dragItemNum];
					}
				}else if($body.hasClass(listStyle['todo'])){
					return __groupArray[dragItemNum];
				}else if($body.hasClass(listStyle['news'])){
					return __itemArray[dragItemNum];
				}
			}
		}
	};
	
	$.fn[plugin].defaults = {
		page:1,
		pageSize:10,
		pageable:false,
		__groupArray : [],
		__itemArray : [],
		collapsed : true,
		mainTitle : '',
		titleAlign: "left",
		otherTitle : '2016/03/02',
		width: 'auto',
		height: 'auto',
		listStyle:'simple',//list风格:[simple|default|todo|news]
		head_color:'bg-red',
		head_font:'font-white',
		icon:'check',//icon样式:[icon-check|icon-close]
		datas : null,
		total:0,
        ajax : {
            read : {
                url : '',
                type : 'POST',
                dataType : 'json',
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
            	return JSON.stringify(params);
            },
            schema : {
            	data : 'data',
            	total : 'total'
            }
        },
		onLoad: function(target){
			//loadList(target);
		},
		onSelect: function(title,index){}
	};
})(jQuery);