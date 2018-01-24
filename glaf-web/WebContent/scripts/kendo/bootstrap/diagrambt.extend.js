var diagrambtFunc = {
	init: function(rule, args) {
		var options = args[0];
		diagrambtFunc.initOptions = $.extend(true, {}, options);

		$.ajax({
			type: "POST",
			url: contextPath + "/mx/form/diagrambt/data",
			contentType: "application/x-www-form-urlencoded",
			dataType: "json",
			data: {
				'rid': options.rid,
				params: options.parameters
			},
			success: function(ret) {
				if (options.DDepth) {
					options.depth = options.DDepth;
				}
				if (options.CContent) {
					options.nodeContent = "title";
				}
				options.data = ret;
				var dataarray = [];
				dataarray.push(options.data);
				diagrambtFunc.buildTitle(dataarray);

				var $ruleinid = $("#" + rule.inid);
				$ruleinid.empty();
				$ruleinid.removeData();
				$ruleinid.orgchart(options);
				if(options.viewState){
					$ruleinid.find(".orgchart").addClass('view-state');
				}
			}
		});

	},
	buildTitle : function(dataarray){
		$.each(dataarray,function(i,item){
			var str = diagrambtFunc._buildTemplateContent(item);
			if(str){
				item.title = str;
			}
			diagrambtFunc.buildTitle(item.children);
		})
		

	},
	_buildTemplateContent : function(data){
		var options = diagrambtFunc.initOptions;
		var that = diagrambtFunc;
		that.options = options;
		var templateStr = that.options.template;

		var customdefinedArray = that.options.customdefined;
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
	getValue: function(rule, args) {
		return args[1].id;
	},
	setValue: function(rule, args) {

	},
	getFocusNodeData: function(rule, args) {
		var $id = pubsub.getJQObj(rule, true);
		var $node = $id.find("div.node.focused");
		var focusedId = $node.attr("id");
		var data = [];
		data.push($id.find('.orgchart').data('options').data);
		var b = diagrambtFunc.getNodeDataById(data, focusedId);
		return b[rule.columnName];
	},
	getNodeDataById: function(data, id) {
		var ret = null;
		$.each(data, function(i, item) {
			if (item.id == id) {
				ret = item;
				return false;
			} else {
				ret = diagrambtFunc.getNodeDataById(item.children, id);
				if (ret != null) {
					return false;
				}
			}
		})
		return ret;
	},
	getNode: function(rule, args) { //获取图中选中节点
		var $id = pubsub.getJQObj(rule);
		if ($id.find("div.node.focused").length > 0) {
			return $id.find("div.node.focused");
		} else {
			return $("");
		}
	},
	removeNodes: function(rule, args) {
		var $id = pubsub.getJQObj(rule);
		var $node = diagrambtFunc.getNode(rule, args);
		$id.orgchart('removeNodes', $node);
	},

	addParent: function(rule, args) {
		var $id = pubsub.getJQObj(rule),
			r, v = "";
		var $node = diagrambtFunc.getNode(rule, args);
		if ($node.length > 0) {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param];
				$id.orgchart('addParent', $id.find('.node:first'), {
					'name': v
				});
			}
		}
	},
	addSiblings: function(rule, args) {
		var $id = pubsub.getJQObj(rule),
			r, v = "";
		var $node = diagrambtFunc.getNode(rule, args);
		if ($node.length > 0) {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param];
				$id.orgchart('addSiblings', $node, {
					'siblings': [{
						'name': v,
						'relationship': '110'
					}]
				});
			}
		}
	},
	addChildren: function(rule, args) {
		var $id = pubsub.getJQObj(rule),
			r, v = "";
		var $node = diagrambtFunc.getNode(rule, args);
		var options = $.extend({}, $id.find('.orgchart').data('options'), {
			depth: 0
		});
		if ($node.length > 0) {
			var hasChild = $node.parent().attr('colspan') > 0 ? true : false;
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param];
				if (!hasChild) {
					$id.orgchart('addChildren', $node, {
						'children': [{
							'name': v,
							'relationship': '100'
						}]
					}, options);
				} else {
					$id.orgchart('addSiblings', $node.closest('tr').siblings('.nodes').find('.node:first'), {
						'siblings': [{
							'name': v,
							'relationship': '110'
						}]
					});
				}
			}
		}
	},
	grefresh: function(rule, args) {
		var params = [];
		params.push(diagrambtFunc.initOptions)
		diagrambtFunc.init({
			'inid': rule.outid
		}, params);
	},
	/**
	 * 改变格子标题颜色
	 */
	changeTitleColor: function(rule,args){
		var $id = pubsub.getJQObj(rule);

		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			if(r && r.param != 'key' && r.param != 'color')
				v = args[r.param];
		}

		var key = args.key;
		key = key || 'id';
		var $id = pubsub.getJQObj(rule);
		var options = $id.find('.orgchart').data('options');
		var dataItems = $id.find('.orgchart').data('options').data;	//数据

		var callback = function(item){
			$id.find("div.node[id='"+item.id+"']").find('div.title').css("background-color",args.color);
		}
		var dataArray = [];
		dataArray.push(dataItems)
		diagrambtFunc.getNodes(dataArray,key,v,callback);

	},
	/**
	 * [根据key与value查询出对应的节点信息]
	 * @param  {[type]}   treeDatas {....children:[{}]}
	 * @param  {[type]}   key       [description]
	 * @param  {[type]}   value     [description]
	 * @param  {Function} callback  [description]
	 * @return {[type]}             [description]
	 */
	getNodes : function(treeDatas,key,value,callback){
		$.each(treeDatas,function(i,item){
			if(item[key] == v){
				if(callback && $.isFunction(callback)){
					callback(item);
				}
			}
			diagrambtFunc.getNodes(item.children,key,value,callback);
		})
		
	},
	/**
	 * 获取数据集字段信息
	 */
	getKeyName : function(rule,args){
		return rule.columnName;
	},

};
pubsub.sub("diagrambt", diagrambtFunc);