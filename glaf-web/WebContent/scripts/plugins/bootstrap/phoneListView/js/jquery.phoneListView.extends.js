(function($, window, document, undefined) {
	var plugin = "phoneListView", optionKey = plugin + ".options",
	
    Build = function(o) {
		this.target = $(o);
		this.w = $(document);
		this.option = this.target.data(optionKey).options;
		this._init($(this));
		
	};
	
	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string' && (options in $.fn[plugin].methods)) {
			var p = this.data(plugin);
			if (!plugin) {
				this.data("phoneListView", new Plugin(this, params));
            } 
			return p ? p[options].apply(p, Array.prototype.slice.call(
					arguments, 1)) : $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i, o) {
			var state = $.data(o, optionKey);
			if (state) {
				$.extend(true, state.options, options);
			} else {
				$.data(o, optionKey, {
					options : $
							.extend(true, {}, $.fn[plugin].defaults, options),
					datas : param
				});
			}
			$.data(o, plugin, new Build(o));
		});
	};

	$.fn[plugin].defaults = {
			
	};

	$.fn[plugin].methods = {
		_init : function(e){
			var that = this,
			    $opt = that.option,
			    $target = that.target;
			that.__resultSet($target,$opt);
			that._loadData(that);
			//that._updateComponet($target,$opt);
			
		},
		_loadData : function($obj){
			$.ajax($obj.option.read);
		},
		__resultSet : function($target,$opt){
			var that = this;
			$opt.read.success = function(ret){
				$opt.phonelistDataSource = eval("("+$opt.phoneListDataSource+")");
				that.__renderTemplate($target,$opt,ret);
				$opt.getData = ret[0].data;
				that.__linkageControl($target,$opt);
			}
		},
		/*_updateComponet : function($target,$opt){
			if($opt.paraType != undefined && $opt.paraType != ""){
				$opt.paraType = eval("("+$opt.paraType+")");
				$.each($opt.paraType,function(i,para){
					$.each(para.datas,function(j,v){
						$.each($opt.getData,function(i,data){
							$.each($($($target[0]).find(".thumbnails [data-role]")[i]),function(j,el){
								data[v[0].columnName];	
							});
						});
						
					})
				});
				$($target[0]).find(".thumbnails [data-role]");
			}
			
		},*/
		getData : function(){
			return $opt.getData;
		},
		__linkageControl : function($target,$opt){
			var $paraType = $opt.paraType;
			var columns = [];
			$.each($paraType,function(i,$t){
				$.each($t.datas,function(j,$para){
					var column = {
					    columnName : $para[0].columnName,
						outid : $para[0].outid,	
						param : $para[0].param
					}
					columns.push(column);
				})
			});
			
			if(columns.length != 0 ){
				$.each(columns,function(i,cols){
					var nName = cols.outid.split("_")[0],
					    nNumber = cols.outid.split("_")[1],
					    option = $target.find(".thumbnails #"+cols.outid+"").data(nName).option;
					for(var count = 0;count < $target.find(".thumbnails").length ; count++){
						
						var newEl = nName + "_" + (parseInt(nNumber) + count),
						    paramter = {},
						    fn = "$(\"#"+newEl+"\")."+nName+"("+JSON.stringify(option)+")",
						    that = $target.find(".thumbnails " + " #" + newEl).data(nName);
						paramter[cols.param] = $opt.getData[count][cols.columnName];
						that.__linkageControl(that,$target.find(".thumbnails " + " #" + newEl),paramter);
					}
					
				});
			}
		},
		__renderTemplate : function($target,$opt,ret){
			var pldataSource = $opt.phonelistDataSource;
			$.each(ret[0].data,function(i,data){
				if(i == 0){
					$target.find(".weui_cell .title span.titleMessage")[0].innerHTML = data[pldataSource[0].title.en];
					/*if(pldataSource[0].datetime != undefined){
						$($target[0]).find(".weui_cell .title span.weui_datetime")[0].innerHTML = data[pldataSource[0].datetime.en];
						
					}*/
					if(pldataSource[0].imgUrl != undefined){
						$target.find(".weui_cell_hd img")[0].src = contextPath + pldataSource[0].imgUrl[0].en;
					} 
					if(pldataSource[0].databaseId != undefined){
						$target.find(".weui_cell_hd img")[0].src = contextPath +"/mx/form/imageUpload?method=downloadById&id="+pldataSource[0].databaseId[0].en;
					}
					$target.find(".weui_cell .paragraph")[0].innerHTML = data[pldataSource[0].value[0].en];
				}
				else{
					$target.append($($target[0]).find(".weui_cell")[0].outerHTML);
					$target.find(".weui_cell .title span.titleMessage")[i].innerHTML = data[pldataSource[0].title.en];
					$target.find(".weui_cell .paragraph")[i].innerHTML = data[pldataSource[0].value[0].en];
					/*if(pldataSource[0].datetime != undefined){
						$($target[0]).find(".weui_cell .title span.weui_datetime")[0].innerHTML = data[pldataSource[0].datetime.en];
					}*/
					
					$.each($($target.find(".thumbnails [data-role]")[i]),function(j,el){
						var id = $(el).attr("id"),
						    nName = id.split("_")[0],
						    nNumber = parseInt(id.split("_")[1]);
						    option = $target.find(".thumbnails #"+nName + "_" + nNumber+"").data(nName).option;
						nNumber = nNumber + i;
						$(el).attr("id",nName + "_" + nNumber);		
						var fn = "$(\"#"+nName + "_" + nNumber+"\")."+nName+"("+JSON.stringify(option)+")";
					    eval(fn);
					});
				}
				
			});
		}
	};
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery || window.Zepto, window, document);

