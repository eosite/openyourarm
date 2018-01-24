
/**
 * 自定义表格
 * 
 * @param $
 */
(function($) {
	
	$.fn.outter = function(){
		return this[0] ? this[0].outerHTML : '';
	};

	var plugin = 'definedTable',
		optionKey = 'k-definedTable';

	function isMobile(){
		var userAgentInfo = navigator.userAgent;  
		var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod");  
		var flag = false;  
		for (var v = 0; v < Agents.length; v++) {  
			if (userAgentInfo.indexOf(Agents[v]) > 0) { flag = true; break; }  
		}  
		return flag;  
	};

	function downRefreshFun($target,options){
		var $this = $target;
		var target = $this[0];
		var mscroll_options = {};

		var that_options = options;
		if(options.scrollload ){
			mscroll_options.up = {  
				style:'circle',//必选，下拉刷新样式，目前支持原生5+ ‘circle’ 样式
				color:'#2BD009', //可选，默认“#2BD009” 下拉刷新控件颜色
				// height:'50px',//可选,默认50px.下拉刷新控件的高度,
				// range:'100px', //可选 默认100px,控件可下拉拖拽的范围
				// offset:'0px', //可选 默认0px,下拉刷新控件的起始位置
				contentrefresh : "正在加载...",//可选，正在加载状态时，上拉加载控件上显示的标题内容
				contentnomore:'没有更多了',//可选，请求完毕若没有更多数据时显示的提醒内容；
				callback: function(){
					var that = this;
					var builder = $this.data(plugin);

					var pageMax = Math.ceil(builder.total / that_options.pageSize);
					if(pageMax <= that_options.page){
						this.endPullupToRefresh(true);
						return;
					}
					if (builder) {
						that_options.page++;
						if(that_options.template.serverPaging == "true"){
		            		var orienParam;
							if(that_options.params && typeof that_options.params == 'string'){
								orienParam = JSON.parse(that_options.params);
							}
							orienParam = orienParam?orienParam:{};
							var params = {};
							return ajaxRequest($this, {
								rid: that_options.rid,
								page: that_options.page,
								pageSize: that_options.pageSize,
								params : JSON.stringify($.extend(true,orienParam,params)) || JSON.stringify(that_options.params||{})
							},function(){
								that.endPullupToRefresh();	
							});
						}else{
							load($this, builder.allRows, builder.total);
							that.endPullupToRefresh(); //refresh completed 
						}
					}
				}  
			}
		}
		if(options.downRefresh){
			mscroll_options.down = {  
			  	style:'circle',//必选，下拉刷新样式，目前支持原生5+ ‘circle’ 样式
				color:'#2BD009', //可选，默认“#2BD009” 下拉刷新控件颜色
				// height:'50px',//可选,默认50px.下拉刷新控件的高度,
				// range:'100px', //可选 默认100px,控件可下拉拖拽的范围
				// offset:'0px', //可选 默认0px,下拉刷新控件的起始位置
				contentdown : "<div>下拉可以刷新</div><div>上次刷新时间:" + mtDate.formatDatetime(Date(),"yyyy-MM-dd HH:mm:ss</div>"),//可选，在下拉可刷新状态时，下拉刷新控件上显示的标题内容
      			// contentover : "　",//可选，在释放可刷新状态时，下拉刷新控件上显示的标题内容
      			contentrefresh : "正在刷新...",//可选，正在刷新状态时，下拉刷新控件上显示的标题内容
				callback: function(){
					if(jq.data("isloadding")){
						return;
					}
					this.options.down.contentdown = "<div>下拉可以刷新</div><div>上次刷新时间:" + mtDate.formatDatetime(Date(),"yyyy-MM-dd HH:mm:ss</div>");
					var that = this;
					var options = jq[plugin]("options");
					options.page = 1;
					var orienParam = null;
					if(that_options.params){
						if(typeof that_options.params == 'string'){
							orienParam = JSON.parse(that_options.params);	
						}else{
							orienParam = that_options.params;
						}
					}
					orienParam = orienParam?orienParam:{};
					var params = {};
					ajaxRequest(jq, {
						rid: options.rid,
						page: options.page,
						pageSize: options.pageSize,
						params : JSON.stringify($.extend(true,orienParam,params)) || JSON.stringify(that_options.params||{})
					},function(){
						that.endPulldownToRefresh(); //refresh completed 	
						that.finished && (that.refresh(true),jq.find(".mui-pull-bottom-pocket.mui-visibility").removeClass("mui-visibility"));
					});
				}  
			}
		}

		if(!options.isAddedScroll){
			if(options.downRefresh || options.scrollload){ 
				var that_options = options;
				var jq = $this;

				mscroll_options.container = '#'+target.id +"_definedScrollContent";
				mui.init({  
					pullRefresh: mscroll_options
				}); 
				options.isAddedScroll = true;

				// mui.init({  
				// 	pullRefresh: {
				// 		repeat : true,
				// 	  container: '#'+target.id,  
				// 	  down: {  
				// 	  	style:'circle',//必选，下拉刷新样式，目前支持原生5+ ‘circle’ 样式
				// 		color:'#2BD009', //可选，默认“#2BD009” 下拉刷新控件颜色
				// 		// height:'50px',//可选,默认50px.下拉刷新控件的高度,
				// 		// range:'100px', //可选 默认100px,控件可下拉拖拽的范围
				// 		// offset:'0px', //可选 默认0px,下拉刷新控件的起始位置
				// 		contentdown : "　",//可选，在下拉可刷新状态时，下拉刷新控件上显示的标题内容
		  //     			// contentover : "　",//可选，在释放可刷新状态时，下拉刷新控件上显示的标题内容
		  //     			contentrefresh : "　",//可选，正在刷新状态时，下拉刷新控件上显示的标题内容
				// 		callback: function(){
				// 			var options = jq[plugin]("options");
				// 				options.params = JSON.stringify(params) || JSON.stringify(options.params||{});
				// 			options.page = 1;
				// 			var params = {};
				// 			ajaxRequest(jq, {
				// 				rid: options.rid,
				// 				page: options.page,
				// 				pageSize: options.pageSize,
				// 				params : JSON.stringify(params) || JSON.stringify(options.params||{})
				// 			});

				// 			mui('#'+target.id).pullRefresh().endPulldownToRefresh(); //refresh completed 
				// 		}  
				// 	  },  
				// 	}  
				// });
			}
		}
	}

	function addRefreshFun($target,options){
		var $target = $target;
		var options = options;
		setTimeout(function(){
			try{
				downRefreshFun($target,options);
			}catch(e){
				addRefreshFun($target,options);
			}
		},200);
	}

	function createtable(target) {
		var $this = $(target),
			state = $this.data(optionKey),
			options = state.options;
		if (options && options.template) {
			options.page = options.template.page ? options.template.page * 1 : 1;
			options.pageSize = options.template.pageSize ? options.template.pageSize * 1 : 10;
			var params = options.params || {};
			if(typeof params.page == 'string'){
				params.page = eval(params.page);
			}
			options.page = params.page || options.page;
			options.isloadding = true;
			ajaxRequest($this, {
				rid: options.rid,
				page: options.page,
				pageSize: options.pageSize,
				params : JSON.stringify(options.params||{}) 
			});
		}

		if($this.data("loadEndExecOneFn")){
			options.loadEndExecOneFn = $this.data("loadEndExecOneFn");
		}
		if($this.data("onClickTable")){
			options.onClickTable = $this.data("onClickTable");	
		}
		if($this.data("loadEndFn")){
			options.loadEndFn = $this.data("loadEndFn");
		}

		if(options.downRefresh || options.scrollload){ 
			// $this.addClass("mui-content mui-scroll-wrapper");
			// $this.css("position","absolute");
			$this.css({"widht":"100%","height":"100%"})
			var $realContent = $('<div class="definedScrollContent mui-content mui-scroll-wrapper" style="widht:100%;height:100%;"></div>');
			$realContent.attr("id",$this[0].id + "_definedScrollContent");
			$realContent.css("position","absolute");
			$realContent.css({"position":"absolute","left":"inherit","top":"inherit","bottom":"inherit"});
			$this.append($realContent);

			var $content = $('<div class="definedTableContent mui-scroll"><ul class="mui-table-view mui-table-view-chevron"></ul></div>');
			$realContent.prepend($content);
		}

		// $this.append($('<div class="definedTableShade"><div class="shadeImgDiv"><img class="shadeImg" src="/glaf/images/definedtable-loading.gif"></div></div>'));

		$this.resize(function() {
			if(!$this.is(":hidden")){ 
				addRefreshFun($this,options);
			}
		});

		setTimeout(function(){
			if(!$this.is(":hidden")){ 
				addRefreshFun($this,options); 
			}	
		},200)

		var eventStr = "click";
		if(isMobile() && window.mui){
			eventStr = "tap"; 
		}
		var that_options = $this[plugin]("options")
		$this.on(eventStr,"tr[role='k-row'],.list[role='k-row']",function(event){
			event.preventDefault();
			$this.find("tr[role='k-row'].selected").removeClass("selected");
			$this.find(".list[role='k-row'].selected").removeClass("selected");
			$(this).addClass("selected");
			if($.isFunction(that_options.onClickTable) && !$(event.target).attr("btn-id")){
				//动态按钮时不触发选中
				that_options.onClickTable.call($this); 
			}
		})
	}

	function ajaxRequest(jq, params,callback) {
		jq.data("isloadding",true);
		// var $definedTableShade = jq.find(".definedTableShade");
		// if(jq.find(".definedScrollContent")[0]){
		// 	$definedTableShade.find('.shadeImgDiv').css("margin-top",jq.find(".definedScrollContent").height()/2-25);	
		// }else{
		// 	$definedTableShade.find('.shadeImgDiv').css("margin-top",jq.height()/2-25);	
		// }
		// $definedTableShade.show();
		$.ajax({
			url: contextPath + '/mx/form/data/gridData',
			type: 'post',
			dataType: 'json',
			async: true,
			data: JSON.stringify(params),
			contentType: "application/json",
			success: function(data) {
				// $definedTableShade.hide();
				jq.data("isloadding",false);
				load(jq, data.data, data.total);
				if(callback){
					callback();
				}
			}
		});
	}

	function load(jq, data, total) {
		// if(data && data.length > 0){
		var options = jq[plugin]("options");
		// var t = new BuildTable(jq,options.template,data.length);
		// $.each(data,function(i,row){
		// t.append(row);
		// });
		// setTimeout(function(){
		new BuildTable(jq, options.template, data || [], total);
		// },50);
		// } else {
		// jq.html("");
		// }
		//添加选中行事件
		var onClickTable = options.onClickTable;

		

		//进行文件列表查询
		var getImageListUrl = contextPath + "/mx/form/attachment?method=getFilesByRandomParent&randomParent=";
		var getImageUrl = contextPath + '/mx/form/imageUpload?method=downloadById';
		$.each(jq.find(".formImagesDiv"),function(k,ktem){
			var $ktem = $(ktem);
			var parentId  = $ktem.attr("parentid");
			var imageHeight  = $ktem.attr("imageHeight");
			var ismin  = $ktem.attr("ismin");
			var min  = $ktem.attr("min");
			var colNum  = $ktem.attr("colNum") || 3;
			var maxNum  = $ktem.attr("maxNum");
			var comBase  = $ktem.attr("comBase");
			var imageWidth = (100/colNum) + "%"; 
			var marginLeft = $ktem.attr("marginLeft") || '0px';
			var marginTop = $ktem.attr("marginTop") || '0px';
			var nowgetImageUrl = getImageUrl+"&ismin="+ismin+"&min="+min+"&comBase="+comBase+"&comBase="+comBase;
			$.ajax({
				url: getImageListUrl+parentId + '&_'+(new Date().getTime()),
				context: ktem
			}).done(function(result) {
				$.each(JSON.parse(result), function(index, file) {
					if(maxNum && index >= maxNum){
						return false;
					}
            		var fileid = file.id;
            		var $div = $("<div style='width:100%;float:left;'>");

					var $img = $("<img style='width:100%'>");
					// $img.addClass("clickViewImg");
					var src = nowgetImageUrl + '&_'+(new Date().getTime())+'&id='+fileid;
	                $img.attr("src", src);

	                $div.append($img);
	                $div.css({"width":imageWidth,"height":imageHeight,"overflow":"hidden","padding-top":marginTop});
	                if(index % colNum != 0){
	                	$div.css("padding-left",marginLeft);
	                }
	                $ktem.append($div);
				});
			}).complete(function() {
			})
		})

		options.isloadding = false;
		
		// 执行加载完回调事件只执行一次
		var loadEndExecOne = options.loadEndExecOneFn;
		if (loadEndExecOne) {
			loadEndExecOne.call(jq);
			options.loadEndFn = null;
		}

		var loadEnd = options.loadEndFn;
		if (loadEnd) {
			loadEnd.call(jq);
		}
	}

	function BuildColumn(latitudes, dynamicColumn, valueKey) {
		this.single = {};
		this.latitudes = latitudes || [];
		this.dynamicColumn = dynamicColumn;
		this.valueKey = valueKey;
		this.rows = new Object();
		this.rowIndex = 0;
		this.collection = [];
		this.push = function(row) {
			var o = row[this.dynamicColumn];
			if (!this.single[o]) {
				this.single[o] = "_" + (this.rowIndex++);
				this.collection.push(o);
			}
			if (this.latitudes.length > 0) {
				var key = [];
				$.each(this.latitudes, function(i, v) {
					key.push(row[v]);
				});
				key = key.join('_0_');
				var r = this.rows[key];
				if (!r) {
					r = this.rows[key] = {};
					$.extend(r, row);
				}
				r[this.single[o]] = row[this.valueKey] || "";
			}
		};

		this.getRows = function() {
			var rows = new Array(),
				row, index = 1;
			for (var key in this.rows) {
				row = this.rows[key];
				row.startIndex = index;
				index++;
				rows.push(row);
			}
			this.flush();
			return rows;
		};

		this.build = function(tdTemplate, syn) {
			if (this.collection.length > 0) {
				// var tdTemplate = td.html("{0}").outter();
				var SB = new StringBuffer();
				$.each(this.collection,
					function(i, o) {
						SB.appendFormat(tdTemplate, syn ? "~F{row._" + i + "}" : o);
					});
				return SB.toString();
			}
		};

		this.flush = function() {
			this.single = null;
			this.collection = null;
		};
	}

	function BuildTable(target, template, rows, total) {

		this.size = rows.length;
		this.rows = rows;
		this.rowsSize = rows.length;
		this.allRows = rows;
		this.index = 1;
		var $style = target.find("style");
		
		this.options = target[plugin]("options");

		this.target = target;
		this.content = this.target.find(".definedTableContent");
		if(!this.options.scrollload || this.options.page == 1){
			this.target.find("table").remove();

			if(this.content[0]){
				// this.content.css("transform","translate3d(0px, 0px, 0px) translateZ(0px)");
				// this.target.find(".mui-scrollbar-indicator").css("transform","translate3d(0px, 0px, 0px) translateZ(0px)");
			}
			// this.target = target.empty();
			// this.target.append($style);
		}
		this.target = target;

		this.template = template;
		this.isList = false;
		this.listTemplate = template.htmldata || "PLEASE EDIT HTML CSS DEFINED TEMPLATE!"; // 循环体
		this.sb = new StringBuffer();
		this.dynamics = [];
		this.callbacks = null;
		this.total = total;

		this.customdefinedArray = this.options.customdefined;

		this.replaceTd = function(jq, tds) {
			jq.replaceWith(tds);
		};

		this.setRows = function(rows) {
			this.rows = rows;
			this.size = rows.length;
		};

		this.init = function() {

			var that = this,
				tdList = '.td-list';
			var list = $(that.template.listTemplate),
				header = that.template.header;

			if (that.template.dynamic) { // 动态列
				var tdT = list.find(tdList);
				var bc = new BuildColumn(that.template.latitudes,
					that.template.dynamicColumn, that.template.valueKey);
				$.each(that.rows, function(i, v) {
					bc.push(v);
				});
				var tds = bc.build(that.template.tdBodyTemplate, true);
				// var tds = bc.build(tdT, true);
				that.replaceTd(tdT, tds);
				if (header) {
					// var headerTd = $(header).find(tdList);
					tds = bc.build(that.template.tdHeaderTemplate);
					// header = header.replace(headerTd.outter(), tds);
					header = new StringBuffer().appendFormat(header, tds)
						.toString();
					// header = header.replace(headerTd.outter(),
					// bc.build(headerTd));
				}
				that.setRows(bc.getRows());
			}

			that.sb.append(header || "<table style='width:100%' class='k-definedtable'>");
			if (list && list[0]) {
				that.isList = true;
				that.listTemplate = list.attr({
					role: 'k-row',
					rowindex: '~F{row.index - 1}'
				}).outter();
			}

			function remove(html) {
				if (html[0])
					html.remove();
			}
			if(this.template.pageable == "true" && this.template.serverPaging != "true"){
				this.startPageCount = (this.options.page-1)*this.options.pageSize ;
				this.endPageCount = this.options.page*this.options.pageSize ;
				that.rows = that.rows.slice(this.startPageCount,this.endPageCount);
				this.rowsSize = this.rows.length ;
			}
			$.each(that.rows, function(i, row) {
				that.append(row);
			});
			if (that.size == 0) {
				this.build();
			}

			//修改图片信息，将没有表达式的图片修改为取rp
			$.each(that.target.find("img"),function(i,o){
				var $img = $(o);
				var $img_src = $img.attr("src");
				if($img_src.indexOf("/mx/form/imageUpload") > -1){
					var rp = $img_src.substring($img_src.indexOf("rp=")+3);
					var matchAry = rp.match(/.jpg|.png|.gif|.bmp/);
					if(matchAry && matchAry.length > 0){
						$img.attr("src",rp).show();
					}
				}
			})
		};

		this.getTemplate = function(row) {
			if (row != null) {
				this.rowIndex(row);
				var t = this.listTemplate;
				var customdefinedArray = this.customdefinedArray;
				var customdefined = null,templateStr = null;
				var that = this;
				for (var key in row) {
					//若有控件定义时，则取控件定义的数据进行渲染
					if(customdefinedArray && customdefinedArray.length > 0){
						customdefined = customdefinedArray[0];
						if(customdefined.value && customdefined.value.length > 0){
							$.each(customdefined.value,function(i,item){
								var expdata = JSON.parse(item.expdata);
								var expression = expdata.expActVal;
								// var reg1 = /~F{[\w|\.]+}/g;

								var reg1 = /#:[^#]+#/g;

								var varVal = expdata.varVal;
								$.each(varVal,function(k,column){
									expression = expression.replace(column.value.code,"#:"+column.value.columnName+"#");
									
								})
								//column.value.code

								var columns = expression.match(reg1);
								$.each(columns,function(k,column){
									var dataItemName = column.substring(2,column.length-1);
									// dataItemName = dataItemName.split("_0_")[1];

									if(!row[dataItemName]){
										dataItemName = dataItemName.toLowerCase();
									}
									expression = expression.replace(column,row[dataItemName] || '');
								})

								if(eval(expression)){
									templateStr = item.htmlExpression;
									t = that._renderContentbyTemplate(templateStr,row);
									return false;
								}
							})
							//跳过
							continue;
						}
					}
					t = this.filter(key, row, t);
				}
				var openTag = window.template.defaults.openTag;
				var closeTag = window.template.defaults.closeTag;
				window.template.defaults.openTag = "{{";
				window.template.defaults.closeTag = "}}";
				k = ArtTemplateDataUtils.convertOne({},t); 	
				window.template.defaults.openTag = openTag;
				window.template.defaults.closeTag = closeTag;
				return k;
				// var k = "";
				// try{
					
				// }catch(e){
				// 	k = t;
				// }
				// return k;
				// return t;
			}
		};
		this._renderContentbyTemplate = function(templateStr,data){
			var that = this;
			var reg1 = /#:[^#]+#/g;
			// var columns = reg1.exec(templateStr);
			var columns = templateStr.match(reg1);
			if(columns){
				$.each(columns,function(i,item){
					var dataItemName = item.substring(2,item.length-1);
					if(!data[dataItemName]){
						dataItemName = dataItemName.toLowerCase();
					}
					templateStr = templateStr.replace(item,data[dataItemName] || "");	
				})
			}
			else if(data['content'] != null && data['content'] != undefined){
				
				templateStr = templateStr.replace('Locations',data['content']);
				//templateStr = data['content'];
			}

			templateStr = pubsub.htmlUnescape4Html(templateStr);

			return templateStr;
		};

		this.rowIndex = function(row) {
			row.index = row.startIndex;
			var index = (this.options.page - 1) * (this.options.pageSize) + row.startIndex;
			row.rowIndex = index;
			row.row_0_index = row.index;
		};

		this.regex = new RegExp("~F\{(.*?)\}", "g");

		this.filter = function(key, row, t) {
			var result;
			while ((result = this.regex.exec(t)) != null) { // 表达式运算
				if (result.length > 0) {
					var find = result[0],
						express, val;
					if(find == "~F{databaseId}"){
						t = t.replace(find, row.databaseId || 0);
					}else{
						if (result.length > 1) {
							express = result[1];
							try {
								var col = ""
								if(express && express.indexOf(".") > -1){
									var express_ = express.split(".");
									col = express_[express_.length - 1];
									var tn = express_[express_.length - 2];
									express = "row." + tn + "_0_" + col;
								}
								val = eval(express);
								if(!val && val != 0){
									col = col.trim();
									val = row[col];
								}
								t = t.replace(find, val == undefined ? "" : val);
							} catch (e) {
								console.log(e); 
							}
						}
					}
				}
			}
			if (/_0_/.test(key)) {
				// 模版 table.column 数据 table_0_column 替换
				row[key.split("_0_")[1]] = row[key];
				t = t.replace(new RegExp(key.replace("_0_", "."), "g"),
					row[key]);
			} else if (/field_/i.test(key)) {
				t = t.replace("row." + key, row[key]);
			}
			return t;
		};

		this.append = function(row) {
			var t = this.getTemplate(row);
			if (this.size > 0) {
				if (!this.isList) {
					this.sb.append("<tr").append(" rowindex=");
					if(this.options.downRefresh || this.options.scrollload){
						this.sb.append((this.index+((this.options.page-1)*this.options.pageSize)) - 1).append(" role='k-row'") //
					}else{
						this.sb.append(this.index - 1).append(" role='k-row'") //
					}
					this.sb.append(" ><td>").append(t) //
						.append("</td></tr>");
				} else {
					this.sb.append(t || "");
				}
			}
			if (this.index == this.rowsSize) {
				this.build();
			}
			this.index++;
		};

		this.build = function() {
			var that = this;
			that.sb.append(that.template.footer || "</table>");
			if(that.content && that.content[0]){
				that.content.find(">ul").append(that.sb.toString());
				that.target.data(plugin, that);	
			}else{
				that.target.append(that.sb.toString()).data(plugin, that);	
			}

			if (that.template.pageable == 'true' && !that.options.scrollload) { // 分页
				that
					.addFn(function() {
						// var tfoot = that.target.find('tfoot');
						var $page = $("<div>", {
							class: 'row'
						}).pagination({
							pageSize: that.options.pageSize,
							page: that.options.page,
							pageNumber:that.options.page,
							total: that.total,
							events : {
					            onSelectPage : function(pageNumber, pageSize) {   
					            	that.options.page = pageNumber;   
					            	if(that.template.serverPaging == "true"){
										that.query({});
									}else{
										load(that.target, that.allRows, that.total);
									}
					            //  that._request(that.options.ajax.read.data, options);
					            },
					            onChangePageSize : function(pageNumber,pageSize) {
					            	that.options.page = pageNumber;
					            	that.options.pageSize = pageSize;
									if(that.template.serverPaging == "true"){
										that.query({});
									}else{
										load(that.target, that.allRows, that.total);
									}
								}
					        }
						});
						var $table = that.target.find(">table");
						if(that.content && that.content[0]){
							$table = that.content.find(">ul>table");
						}
						$table.find("tfoot") && $table
							.append("<tfoot><tr><td></td></tr></tfoot>");
						// that.target.find("table").after($page);
						$table.find("tfoot tr td:eq(0)")
							.attr({
								colspan: that.target.find(
									"table tr:eq(0)").find(
									"td").length,
								style: 'text-align:right'
							}).append($page);
					});
			}

			if (that.template.combine == 'true') { // 合并
				function combineTable_() {
					var combine = {
						id: '#' + that.target.attr('id'),
						spanLevel: that.template.spanLevel,
						colspan: that.template.colspan,
						rowspan: that.template.rowspan
					};
					combineTable(combine);
				}
				that.addFn(combineTable_);
			}

			that.fire();
		};

		this.fire = function() {
			if (this.callbacks) {
				this.callbacks.fire();
			}
		};

		this.addFn = function(fn) {
			if (!this.callbacks) {
				this.callbacks = $.Callbacks("definedTable callbacks");
			}
			this.callbacks.add(fn);
		};

		this.dataItem = function(o) {
			return this.rows[$(o).closest("[role=k-row]").attr("rowindex")];
		};

		this.query = function(params) {
			var options = this.options,orienParam;
			if(options.params && typeof options.params == 'string'){
				options.params = JSON.parse(options.params);
			}
			orienParam = options.params?options.params:{};
			return ajaxRequest(this.target, {
				rid: options.rid,
				page: options.page,
				pageSize: options.pageSize,
				params : JSON.stringify($.extend(true,orienParam,params)) || JSON.stringify(options.params||{})
			});
		};

		this.reload = function(params) {
			return this.query(params);
		};

		this.init();

	}

	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string') {
			return $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i, o) {
			var state = $.data(o, optionKey);
			if (state) {
				$.extend(true, state.options, options);
			} else {
				$.data(o, optionKey, {
					options: $
						.extend(true, {}, $.fn[plugin].defaults, options),
					target: null
				});
			}
			createtable(o);
		});

	};

	$.fn[plugin].methods = {
		options: function(jq) {
			if(jq.data(optionKey)){
				return jq.data(optionKey).options;	
			}else{
				return null;	
			}
		},
		query: function(jq, params) { 
			var options = jq[plugin]("options");
				options.params = JSON.stringify(params) || JSON.stringify(options.params||{});
			options.page = 1;//刷新到第一页中

			var $this = $(jq);
			if(options.downRefresh || options.scrollload){ 
				var $style = $this.find("style");
				$this.empty();
				var $realContent = $('<div class="definedScrollContent mui-content mui-scroll-wrapper" style="widht:100%;height:100%;"></div>');
				$realContent.attr("id",$this[0].id + "_definedScrollContent");
				$realContent.css("position","absolute");
				var $content = $('<div class="definedTableContent mui-scroll"><ul class="mui-table-view mui-table-view-chevron"></ul></div>');
				$realContent.prepend($content);
				$this.prepend($realContent);
				$this.append($style);

				options.isAddedScroll = false;
				setTimeout(function(){
					if(!$this.is(":hidden")){ 
						addRefreshFun($this,options); 
					}	
				},200)
			}

			ajaxRequest(jq, {
				rid: options.rid,
				page: options.page,
				pageSize: options.pageSize,
				params : JSON.stringify(params) || JSON.stringify(options.params||{})
			});
		},
		load: function(jq, datas) {
			load(jq, datas);
		},
		dataItem: function(jq, o) {
			var builder = jq.data(plugin);
			if (builder) {
				var $tr = $(o).closest("table[role=k-row]");
				return builder.allRows[$tr.attr("rowindex")];
			}
			return null;
		},
		loadEndExecOne: function(jq, fn) { // 加载完执行一次事件
			if (typeof fn == "function") {
				if(jq[plugin]("options")){
					$.extend(jq[plugin]("options"), {
						loadEndExecOneFn: fn
					});	
				}else{
					jq.data("loadEndExecOneFn",fn);
				}
			}
		},
		loadEnd: function(jq, fn) { // 加载完执行事件
			if (typeof fn == "function") {
				if(jq[plugin]("options")){
					$.extend(jq[plugin]("options"), {
						loadEndFn: fn
					});
				}else{
					jq.data("loadEndFn",fn);
				}
			}
		},
		onClickTable: function(jq,fn){	//选中表格事件
			if (typeof fn == "function") {
				if(jq[plugin]("options")){
					$.extend(jq[plugin]("options"), {
						onClickTable: fn
					});
				}else{
					jq.data("onClickTable",fn);
				}
			}
		},
		getSelectedRowsData : function(jq){
			var selectedDatas = [];
			var builder = jq.data(plugin);
			var datas = builder.allRows;
			$.each(jq.find("[role='k-row'].selected"),function(i,tr){
				var $tr = $(tr);
				if($tr.attr("rowindex") != null){
					selectedDatas.push(datas[$tr.attr("rowindex")]);	
				}else if($tr.closest(".list").attr("rowindex") != null){
					selectedDatas.push(datas[$tr.closest(".list").attr("rowindex")]);	
				}else{
					selectedDatas.push(datas[$tr.closest("table.list").attr("rowindex")]);	
				}
			})
			return selectedDatas;
		},
		getPage : function(jq){
			var selectedDatas = [];
			var builder = jq.data(plugin);
			return builder.options.page;
		}
	};

	$.fn[plugin].defaults = {

	};

})(jQuery);


/**
 * 合并单元格
 * 
 * @param combine
 */
function combineTable(combine) {

	function tdFilter(td0, td1) {
		return (txt0 = td0.text()) && (txt1 = td1.text()) && txt1 == txt0;
	}

	var spanLevel = {
		colspan: function() {
			if (combine.colspan)
				$.colspan(combine.id, combine.colspan || 'n', tdFilter);
			if (combine.rowspan)
				$.rowspan(combine.id, combine.rowspan || 'n', tdFilter);
		},
		rowspan: function() {
			if (combine.rowspan)
				$.rowspan(combine.id, combine.rowspan || 'n', tdFilter);
			if (combine.colspan)
				$.colspan(combine.id, combine.colspan || 'n', tdFilter);
		}
	};
	setTimeout(function() {
		spanLevel[combine.spanLevel || 'rowspan']();
	}, 0);
}