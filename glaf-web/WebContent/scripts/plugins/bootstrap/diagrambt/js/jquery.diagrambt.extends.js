(function($, window, document, undefined) {
	var plugin = "diagrambt", optionKey = plugin + ".options",
	
    Build = function(o) {
		this.target = $(o);
		this.w = $(document);
		this.option = this.target.data(optionKey).options;
		this.option.data != null && this.option.data != undefined ? this._staticinit($(this)) : this._init($(this));
		
	};
	
	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string' && (options in $.fn[plugin].methods)) {
			var p = this.data(plugin);
			if (!plugin) {
				this.data("bootstrapdialog", new Plugin(this, params));
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
			
		},
		_staticinit : function(e){
			var that = this; 
			    target = that.target,
			    options = that.option,
			    dataarray = [],
			    temp = [];
			options.data = that.staticData(options);
			options.data = that.formatDiagramData(options,temp);
			
			dataarray.push(options.data);
			that.buildTitle(dataarray);
            
			target.empty();
			target.removeData();
			options = that.staticParam(options);
			target.orgchart(options);
			if(options.viewState){
				target.find(".orgchart").addClass('view-state');
			}
		},
		staticData : function(options){
			var temp = [];
			$.each(options.data,function(i,data){
				if(data.parentId != null && data.parentId != undefined){
					data.parentId = data.parentId;
				}
				else{
					data.parentId = -1;
				}
				temp.push(data);
			});
			return temp;
		},
		formatDiagramData : function(options,config){
			var that = this;
			$.each(options.data,function(i,data){
				if(data.parentId == -1){
					data.children = [];
					data = that.diagramData(options,data.id,data);
					config.push(data);
				}
			});
			return config[0];
		},
		diagramData : function(options,id,data){
			var that = this;
			$.each(options.data,function(i,param){
				if(param.parentId == id){	
					    param.children = [];
						data.children.push(that.diagramData(options,param.id,param));				
				}
			});
			return data;
		},
		staticParam : function(options){
			options.customdefined = [];
			options.nodeID = "index_id";
			options.parameters = "{}";
			options.CContent = true;
			options.pan = false;
			options.zoom = false;
			options.toggleSiblingsResp = true;
			options.direction = "t2b";
			options.DDepth = "";
			options.draggable = false;
			options.viewState = true;
			options.parentNodeSymbol = "";
			options.nodeContent = "title";
			return options;
		},
		buildTitle : function(dataarray){
			var that = this;
			$.each(dataarray,function(i,item){
				var str = that._buildTemplateContent(item);
				if(str){
					item.title = str;
				}
				//that.buildTitle(item.children);
			})
			

		},
		_buildTemplateContent : function(data){
			var that = this;
			var templateStr = that.option.template;

			var customdefinedArray = that.option.customdefined;
			if(customdefinedArray && customdefinedArray.length > 0){
				customdefined = customdefinedArray[0];
				if(customdefined.value){
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

							if(!data[dataItemName]){
								dataItemName = dataItemName.toLowerCase();
							}
							expression = expression.replace(column,data[dataItemName] || '');
						})

						if(eval(expression)){
							templateStr = item.htmlExpression;
							templateStr = that._renderContentbyTemplate(templateStr,data);
							return false;
						}
					})
				}else{
					if(templateStr != null)
						templateStr = that._renderContentbyTemplate(templateStr,data);	
				}
			}else{
				if(templateStr != null)
					templateStr = that._renderContentbyTemplate(templateStr,data);
			}

			return templateStr;
		},
		_renderContentbyTemplate : function(templateStr,data){
			var reg1 = /#:[^#]+#/g;
			// var columns = reg1.exec(templateStr);
			var columns = templateStr.match(reg1);
			if(columns){
				$.each(columns,function(i,item){
					var dataItemName = item.substring(2,item.length-1);
					if(!data[dataItemName]){
						dataItemName = dataItemName.toLowerCase();
					}
					templateStr = templateStr.replace(item,data[dataItemName]);
				})
			}
			return templateStr;
		},
	};
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery || window.Zepto, window, document);




















