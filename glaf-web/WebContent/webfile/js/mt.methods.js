var textboxFunc = {
		getValue : function(rule,args){
			return  $("#" + rule.inid).val();
		}
};

var pageFunc = {
	getValue : function(rule,args){
		if(mtxx.params){
			return "'" + mtxx.params[rule.inparam] + "'";
		}
		return  null;
	},
	linkagePage : function(id,params){ 
		var p = pubsub.paramsToStr(params); 
		$("iframe[src*='"+id+"']").each(function(i){ 
		if(!this.prosrc){ 
		this.prosrc = this.src ; 
		}  
		this.src = this.prosrc + p; 
		}); 
	}
};

pubsub.sub("textbox",textboxFunc);

pubsub.sub("page",pageFunc);

var roleList = {
	grid : 'kendoGrid',
	combobox : 'kendoComboBox'
};

function getKendoData(jq){
	if(!jq || !jq[0]){
		return null;
	}
	var dataRole = jq.attr('data-role');
	return {
		dataRole : dataRole || 'text',
		kendo : jq.data(roleList[dataRole])
	};
}

function kendoReload(jq, params) {
	if (!jq || !jq[0]) {
		return false;
	} else {
		jq.mtkendo('reload',params);
	}
}

function getKendoValue(jq, key) {
	if (!jq || !jq[0]) {
		return null;
	} else {
		return jq.mtkendo('getValue',key);
	}
}

function setKendoValue(jq,val){
	if (!jq || !jq[0]) {
		return false;
	}else{
		jq.mtkendo('setValue',val);
	}
}


// 按钮事件 klaus.wang 2015-05-14
function kendoButtonFunc(viewModel) {
	if(!viewModel.buttonType){
		return false;
	}
	var functions = viewModel.functions;
	if (!functions) {
		functions = new KendoButton(viewModel);
	}
	
	if((viewModel.buttonType in functions)){
		functions[viewModel.buttonType]();
	}else{
		alert(viewModel.buttonType + ' 方法未找到');
	}
}

function KendoButton(vm) {

	this.viewModel = vm;
	
	this.init();
}

KendoButton.prototype.init = function(){
	this.viewModel.data = new Object();
	if (this.viewModel.handlecolumns)
		this.viewModel.data = eval('(' + this.viewModel.handlecolumns + ')');
	if(this.viewModel.ruleData){
		this.viewModel.data.rule = eval('(' + this.viewModel.ruleData + ')');
	}
	this.viewModel.data.pageParameters = eval('(' + pageParameters + ')');
	this.viewModel.functions = this;
};

/**
 * 修改cell表
 */
KendoButton.prototype.edit_cell = function(){
	var K = this,editFunctions = K.editFuncs;
	jQuery.each(K.viewModel.data, function(id, v) {
		var $this = jQuery('#' + id), dataRole = K.kendoData($this).dataRole;
		if (editFunctions[dataRole]) {
			editFunctions[dataRole].call(K, $this);
		}
	});
};
KendoButton.prototype.editFuncs = {
	grid : function(jq){					
		var K = this,kendoData = K.kendoData(jq),grid = kendoData.kendo,rows = grid.select();
		if (rows && rows.length == 1) {
			var edit = $(rows[0]);
			if(edit && edit[0]){
				grid.editRow(edit);
			}
		}else{
			alert('请选择一条记录!');
		}
	}
};

/**
 * 查询操作
 */
KendoButton.prototype.search = function() {
	var jq = jQuery(this.viewModel.change);
	
	var paraType = this.viewModel.data.rule.paraType,idRelation = {};
	if(paraType && paraType[0]){
		var datas = paraType[0].datas;
		if(datas){
			pubsub.pub("linkageControl",datas);
		}
	}
	
	/*if (jq[0])
		jq.mtkendo('reload',{
			paramType : 'query-button',
			prid : this.viewModel.prid,
			params : kendo.stringify(this.getSearchData())
		});*/
};
// 查询方法
KendoButton.prototype.getSearchData = function() {
	if (this.viewModel.data) {
		jQuery.each(this.viewModel.data, function(id, json) {
			json.value = jQuery('#' + id).mtkendo('getValue',json.ColumnName);
		});
	}
	return this.viewModel.data;
};

/**
 * 删除操作
 */
KendoButton.prototype['delete'] = function() {
	var K = this,deleteFunctions = K.deleteFuncs;
	jQuery.each(K.viewModel.data, function(id, v) {
		var $this = jQuery('#' + id), dataRole = K.kendoData($this).dataRole;
		if (deleteFunctions[dataRole]) {
			deleteFunctions[dataRole].call(K, $this);
		}
	});
};			
//删除方法
KendoButton.prototype.deleteFuncs = {
	grid : function(jq) {
		var K = this,kendoData = K.kendoData(jq);
		var grid = kendoData.kendo;
		var rows = grid.select();
		if (rows && rows.length > 0) {
			
			if(K.viewModel.multiPlay == 'false'){
				if(rows.length > 1){
					alert('请选择一条记录!');
					return false;
				}
			}
			if(confirm('你确定删除吗?')){
				var models = new Array();
				jQuery.each(rows, function(i, item) {
					models.push(grid.dataItem(item));
				});
				jQuery.ajax({
					url : grid.dataSource.transport.options.destroy.url
					|| contextPath + "/mx/form/data/deleteData",
					contentType : "application/json",
					type : "POST",
					async : false,
					data : kendo.stringify({
						models : models,
						rid : grid.dataSource.transport.options.read.data.rid
					}),
					success : function(data) {
						K.search();
					}
				});
			}
		} else if (rows.length == 0) {
			alert('请选择!');
		}
	}
};

/**
 * 保存操作
 */
KendoButton.prototype.save = function(){
	var K = this,data = new Array();
	jQuery.each(K.viewModel.data, function(id, v) {
		var $this = jQuery('#' + id),params = $this.data('params') || v.column;					
		if(params){
			params.value = $this.mtkendo('getValue',params.ColumnName);
			data.push(params);
		}
	});
	if(data.length > 0){
		console.log(data);
		jQuery.ajax({
			url : contextPath + '/mx/form/data/saveOrUpdateDetail',
			type : 'post',
			data : {columns : JSON.stringify(data)},
			async : false,
			dataType : 'json',
			success : function(rst){
				if(rst){
					alert('操作成功!');
					$(K.viewModel.change).mtkendo('reload',{});
					if(K.viewModel.data.rule.closeWindow == 'true'){
						K.closeWindow();
					}
				}
			}
		});
	}
};

/**
 * 新建窗口
 */
KendoButton.prototype.newWindow = function(type){
	var K = this,id = 'mtWindow_' + K.viewModel.id,rule = K.viewModel.data.rule;
	var jumpType = rule.jumpType;//页面跳转类型
	var jumpFuncs = {
		childPage : {//子窗口(弹出)
			open : function(){
				K.newWinFuncs.init(K,function(url,data){
					var win = jQuery.mt.window({
						id : id,
						title : rule.title || '查看',
						width: rule.width || '650px',
						height : rule.height || '450px',
						refresh :true,
						modal : rule.modal,
						content : {
							url : url + '?' + jQuery.param(data),
							iframe : true
						},
						appendTo: 'body'
					});
					win.kendo.center();
				});
			},
			close : function(win){
				jQuery('#' + id).data('kendoWindow').close();
			}
		},
		singlePage : {//跳转页面
			open : function(){
				K.newWinFuncs.init(K,function(url,data){
					window.open(url + '?' + jQuery.param(data));
				});
			},
			close : function(win){
				win.close();
			}
		}
	};
	if(!type){
		if(jumpFuncs[jumpType]){
			jumpFuncs[jumpType].open();
		}
	}
	return jumpFuncs[jumpType];
};
//窗口方法
KendoButton.prototype.newWinFuncs = {
	init : function(K,fn){
		var paraType = K.viewModel.data.rule.paraType,idRelation = {};
		if(paraType && paraType[0]){
			var datas = paraType[0].datas;
			if(datas){
				$.each(datas,function(k,v){
					if(!idRelation[v[0].inid]){
						idRelation[v[0].inid] = {};
					}
					v[0].items = [];
					idRelation[v[0].inid][v[0].param] = v[0];
				});
			}
		}
		
		var url = contextPath + '/mx/form/page/viewPage';
		jQuery.each(K.viewModel.data, function(id, v) {
			var $this = jQuery('#' + id), dataRole = K.kendoData($this).dataRole;
			if($this[0]){
				$this.data("idRelation",idRelation[id]);
			}
			if (K.newWinFuncs[dataRole]) {
				var data =  K.newWinFuncs[dataRole](K, $this);
				if(data && fn){
					fn.call($this,url,data);
				}
			}
		});
	},
	grid : function(K,jq){
		var kendoData = K.kendoData(jq),grid = kendoData.kendo,rows = grid.select();
		if(rows && rows.length > 0){
			if(K.viewModel.multiPlay == 'false'){
				if(rows.length > 1){
					alert('请选择一条记录!');
					return false;
				}
			}
			var pageId = K.viewModel.pageId;						
			if(pageId){
				var ids = new Array();
				
				var relation = jq.data("idRelation"),ret = {};
				
				jQuery.each(rows,function(i,v){
					if(relation){
						$.each(relation,function(col,item){
							item.items.push(
									grid.dataItem(v)[item.columnName]
							);
						});
					}
					else
						ids.push(grid.dataItem(v).id);
				});
				
				if(relation){
					$.each(relation,function(k,item){
						ret[k] = item.items.join('_0_');
					});
				}
				ret.newWin = K.viewModel.buttonType;
				ret.id = pageId;
				ret.prid = K.viewModel.prid;
				ret.btnId = K.viewModel.id;
				
				return ret;
				/*return  {
					newWin : K.viewModel.buttonType,
					id : pageId,
					parentId : '[' +  ids.join(',') + ']' ,
					prid : K.viewModel.prid,
					btnId : K.viewModel.id
				};*/
			}
		}else{
			alert('请选择!');
			return false;
		}
	}
};

/**
 * 关闭操作
 */
KendoButton.prototype.closeWindow = function(){
	if(confirm('你确定关闭窗口吗?')){
		var pageParameters = this.viewModel.data.pageParameters;
		if(pageParameters){
			var btnId = pageParameters.btnId,vm;
			if(btnId){
				var parent = opener || window.parent;
				vm = parent.$('#' + btnId).mtkendo('getViewModel');
				parent.$(vm.change).mtkendo('reload',{});
				if(pageParameters.newWin){
					vm.functions[pageParameters.newWin](true).close(window);
				}
			}else {
				window.close();
			}
		}else{
			window.close();
		}
	}
};

KendoButton.prototype.kendoData = function(jq){
	return jq.mtkendo('getKendoData');
};

//页面初始化赋值
jQuery(function() {
	var p = pageParameters || undefined;
	if (p) {
		//initdetail(p);
		initPageDetail(p);
	}
});

var mtxx = {
		
};

function initPageDetail(p){
	var params = eval('(' + p + ')');
	if(params && params.newWin){
		var url = contextPath + '/mx/form/data/initPageDetail',jdom;
		$.post(url,params,function(ret){
			if(ret && ret.data){
				$.each(ret.data,function(id,val){
					jdom = $("#" + id);
					if(jdom[0]){
						jdom.mtkendo('setValue', val);
					}
				});
			} 
			mtxx.params = params;
			if(ret && ret.controlRule){
				pubsub.pub("linkageControl",ret.controlRule);
			}
		},'json');
	}
}



function initdetail(p){
	var params = eval('(' + p + ')'), id = params.parentId;
	if (params && params.newWin) {
		jQuery.ajax({
			type : "POST",
			dataType : 'json',
			data : params,
			url : contextPath + '/mx/form/data/gridDataDetail',
			success : function(data) {
				if (data && data.dataMap) {
					var dataList, jDom;
					for ( var tableName in data.dataMap) {
						dataList = data.dataMap[tableName];
						jQuery.each(dataList, function(i, v) {
							v.id = id;
							jDom = jQuery('#' + v.domId);
							if (jDom && jDom[0]) {
								jDom.mtkendo('setValue', v.value);
								jDom.data('params', v);
							}
						});
					}
				}
			}
		});
	}
}

