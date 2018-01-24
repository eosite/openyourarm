
(function($){
	$.fn.extend({
		outter : function(){
			return $('<div>').append(jQuery(this).clone()).html();
		}
	});
	
	$.extend({
		log : function(msg){
			console.log(msg);
		}
	});
	
	
	
})(jQuery);
function StringBuffer(str){
	  
	  this.collection = new Array();
	  
	  this.append = function(str){
		  this.collection[this.collection.length] = str;
		  return this;
	  };
	  
	  this.toString = function(split){
		  return this.collection.join(split ? split : '');
	  };
	  
	  this.appendFormat = function(){
		  if(arguments.length == 0) 
			  return this;
		  var str = arguments[0];
		  for(var i = 1; i < arguments.length; i++)  
			  str = str.replace(new RegExp("\\{" + (i - 1) + "\\}","g"), arguments[i]);  
		  return this.append(str);
	  };
	  
	  if(str){
		  this.append(str);
	  }
	  
}

/**
 * 
 */
var setting = {
	async : {
			enable : true,
			url : url + '/getFormPageTree',
			autoParam : [ "id" ]
	},
	view: {
		dblClickExpand: true,
		showLine: true,
		selectedMulti: false,
		expandSpeed : 'fast',    //'slow'
		fontCss: function(treeId, treeNode) {
			return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
		}
	},
	data: {
		simpleData: {
			enable:true,
			idKey: "id",
			pIdKey: "parentId",
			rootPId: '-1'
		}
	},
	check:{
		chkStle:'checkbox',
		enable:false
	},
	callback: {
		beforeClick: ztreeBeforeClick,
		onRightClick: zTreeOnRightClick,
		onRename: zTreeOnRename,
		onRemove: zTreeOnRemove,
		onCreate : zTreeOnCreate,
		onUpdate : zTreeOnUpdate,
		onEexport : zTreeOnExport,
		onAsyncSuccess : function(){
			
			if(selectedNode){
				
				var nodes = zTree.getNodesByParamFuzzy("id", selectedNode.id, null);
				
				zTree.expandNode(nodes[0], true, true, false);
				
			}
		},
		onCollapse : function(event, treeId, treeNode){
			
		}
	}
};



var dictSort = {
	Configuration : '属性',
	Fields : '字段',
	Methods : '方法',
	Events : '事件',
	buttonConf : '按钮属性'
};

$.getDictByCode = function(code,fn){
	$.ajax({
		url : url + '/getDictByCode',
		data : {code : code},
		type : 'post',
		dataType : 'json',
		async : false,
		success:function(data){ 
			if(fn)
				fn(data);
		}
	});
};

jQuery(function() {

	tabs = jQuery('#tabs').tabs({
		onSelect : function(title,index){
			
		}
	});//页面tab
	
	zTree = jQuery.fn.zTree.init(jQuery("#tree"), setting);				
				
	jQueryMenu = jQuery('#mm');
	
	expandAll();
	
	$.getDictByCode('property_sort',function(data){
		if(!dictSort.data){
			dictSort.data = new Object();
		}
		
		$.each(data,function(i,v){
			dictSort.data[v.code] = v;
		});
	});
	
	prop_tabs = $('#prop-tabs').tabs();//属性tab
	
	$("#searchTextBox").on('change',function(){
		searchProject();
	});
});

function expandAll(){
	//setTimeout(function(){zTree.expandAll(true);},500);
}

//左键事件
function ztreeBeforeClick(treeId, treeNode, clickFlag) {
	if (!treeNode.isParent){
		openTab("<a style='display:none' onclick='alert(123)' >" + treeNode.parentId + "</a>" + treeNode.name, contextPath + '',treeNode.id);
	}
}

//右键事件
function zTreeOnRightClick(e, treeId, treeNode){
	selectedNode = treeNode;
	zTree.selectNode(treeNode);
	jQueryMenu.menu('show', {
		left: e.pageX,
		top: e.pageY
	});
}

//名称编辑完以后
function zTreeOnRename(event, treeId, treeNode, isCancel) {
	if(!treeNode.name){
		alert('请输入节点名称!');
		zTree.reAsyncChildNodes(null, "refresh");
		return;
	}
	else{
		jQuery.post(contextPath + '/mx/form/page/saveFormPage',{id:treeNode.id,name:treeNode.name},function(data){});
	}
}

//删除节点
function zTreeOnRemove() {
	
	var msg = "你确定删除该节点吗?";
	if(selectedNode.isParent){
		msg = '你确定删除该节点以及级联节点吗?';
	}
	//alert('暂不提供删除');return false;
	if(!confirm(msg)){
		return false;
	}else{
		var ids = new Array();
		getNodeIds(selectedNode,ids);
		jQuery.post(contextPath + '/mx/form/page/delete',{ids : ids.join(',')},function(){
			zTree.removeNode(selectedNode);
		});
	}

}

function zTreeOnExport(){
	if(selectedNode.isParent){
		
	}else{
		window.location.href = contextPath + "/mx/form/defined/download?pageId=" +  selectedNode.id;
	}
}

function zTreeOnUpdate(){
	openUploadPage({
		id : selectedNode ? selectedNode.id : '',
		type : 'update'
	});
}

function getNodeIds(node,collection){
	collection.push(node.id);
	if(node.isParent){			
		jQuery.each(node.children,function(index,item){				
			getNodeIds(item,collection);
		});
	}
}

//增加节点
function zTreeOnCreate(){
	
	openUploadPage({
		id : selectedNode ? selectedNode.id : ''
	});
}

function searchProject(){
	
	var searchTextBox = $("#searchTextBox"),val = searchTextBox.val();
	
	var f = zTree.expandAll(false);
	
	function updateHighlight(nodes,flag){
		$.each(nodes,function(i,node){
			node.highlight = flag || false;
			zTree.updateNode(node);
		});
	}
	
	if(!f){
		setTimeout(function(){
			var ns = searchTextBox.data('nodes');
			if(ns && ns[0]){
				updateHighlight(ns,false);
			}
			if(val){
				var nodes = zTree.getNodesByParamFuzzy("name", val, null);
				if(nodes && nodes[0]){
					$.each(nodes,function(i,node){
						if(!node.isParent){
							node = node.getParentNode();
						}
						zTree.expandNode(node, true, true, false);
					});
					updateHighlight(nodes,true);
					searchTextBox.data('nodes',nodes);
				}
			}
		}, 500);
	}
}

//新建分类
function createSort(selectedNode){
	jQuery('#sortDialog').dialog({
		closed:false,
		modal : true,
		buttons:[
			{
				text : '确 定',
				handler:function(){
					var sortName = jQuery('#sortId').val();
					if(!sortName){
						alert('请输入分类');return;
					}else{
						jQuery.post(contextPath + '/mx/form/page/saveFormPage',{
							name:sortName,
							formHtml:sortName,
							parentId : selectedNode ? selectedNode.id : ''
						},function(rst){
							if(rst){
								
								alert('操作成功!');
								
								jQuery('#sortDialog').dialog('close');
								
								zTree.reAsyncChildNodes(null, "refresh");
								
								//expandAll();
							}
						});
					}
				}
			}
		]
	});
}


var tmpLayer;	
function openUploadPage(data){
	var link = url + "/uploadFiles?" + jQuery.param(data);
	tmpLayer = jQuery.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "增加节点",
		closeBtn: [0, true],
		shade: [0.8, '#000'],
		border: [10, 0.3, '#000'],
		//offset: ['20px',''],
		fadeIn: 100,
		close : function(){
			zTree.reAsyncChildNodes(null, "refresh");expandAll();
		},
		area: ['420px', (jQuery(window).height() - 350) +'px'],
               iframe: {src: link}
	});
}

//打开页面
function openTab(text, link, id) {
	
	//jQuery('#button_tool').empty();
	
	closeAllTabs(prop_tabs);//关闭所有属性tab
	
	//rows = null;				
	
	jQueryFrame = jQuery('<iframe>', {
		frameborder : 0,
		scrolling : 'auto',
		id : id,
		style : 'width:100%;height:99%'
	}).attr({
		src:url + '/getFormPageHtmlById?id=' + id,
		onload:function(){
			setTimeout(function(){
				populateJs(jQueryFrame);
			},800);  //延迟加载，保证iframe 完全加载后注入js
		}
	});
	
	tabs.tabs('close',0);//先关闭(防止js 无法注入)
	
	tabs.tabs('add',{
		title: text,
		content: jQueryFrame
	});
	
	var a = $('#tabs .tabs-inner').on('click',function(){
		saveData = {};
		saveData.name = id;
		saveData.pageId = id;
		saveData.dataRole = 'page';
		showComponentProperties({
			id : id,
			pageId : id,
			dataRole : 'page'
		},$(this));
	}).attr({
		title : '点击配置页面属性'
	});
	
}

//关闭所有tab
function closeAllTabs(jq){
	
	if(jq && jq[0]){
		var tabs = jq.tabs('tabs'),titles = [];
		$.each(tabs,function(i,tab){
			titles[i] = tab.panel('options').title;
		});
		if(titles.length > 0){
			for(var i in titles){
				jq.tabs('close',titles[i]);
			}
		}
	}
}

//给每个控件[crtltype] 注册点击事件
function populateJs(jQueryFrame){
	divs_ = new Array();
	var doc = jQueryFrame.contents();
	
	var crtltypes = doc.find('[crtltype]');

	if(crtltypes && crtltypes.length){
		var $this;
		var pageId = jQueryFrame.attr("id");
		
		doc.find("body").bind('click',function(index,item){
			saveData = {};
			saveData.name = pageId;
			saveData.pageId = pageId;
			saveData.dataRole = 'page';
			showComponentProperties({
				id : pageId,
				pageId : pageId,
				dataRole : 'page'
			},$(this));
			return false;
		});
		
		 jQuery.each(crtltypes,function(index,item){
		 	jQuery(item).unbind('click');
			jQuery(item).bind('click',function(e){
				
				$this = jQuery(this);
				var id = $this.attr('id');
				var dataRole = $this.attr('data-role');
				
				if(dataRole){ 
					dataRole = dataRole.toLowerCase();
				}
				saveData = {};
				saveData.name = id;
				saveData.pageId = pageId;
				saveData.dataRole = dataRole;
				showComponentProperties({
					id : id,
					pageId : pageId,
					dataRole : dataRole
				},$this);
				
				//清楚其他div_颜色
				jQuery.each(divs_,function(){
					$(this).css({
						'border-color' : ''
					});
				});
				
				jQuery.each(crtltypes,function(){
					if($(this).attr('id') === id){
						$this.css({
							border:'5px #FFB90F solid'
						});
					}else{
						$(this).css({
							border:''
						});
					}
				});
				return false;
			});
		});
		 
	}
	doc.find("[data-hover='hover']").each(function(i,v){
		 $this = $(this);
		 var id = $this.attr('id');
		 var offset = $this.offset();
		 var left = offset.left+$this.width()-30 ;
//		 var left = offset.left+3 ;
		var div_ = 
		$('<div>',{
			   id : id + '_div',
			  'data-role':'splitter',
			  'crtltype':'splitter'
			})
			 .css({
				 'font-size': '12px',
				 color: 'green',
				 'border':'2px', 
				 'border-style': 'solid',
				 'border-color':'green', 
				 position:'absolute',
				 float: 'left',
				 left: left,
				 top : offset.top+3,
				 background:'#fff',
				 filter: 'alpha(opacity=70)',
				 opacity: '0.7'
			 })
			 .text('设置').bind('click',function(e){
				 	var that = $(this);
					var dd = that.attr('id');
					var dataRole = that.attr('data-role');
					if(dataRole){ 
						dataRole = dataRole.toLowerCase();
					}
					saveData = {};
					saveData.name = dd;
					saveData.pageId = pageId;
					saveData.dataRole = dataRole;
					showComponentProperties({
						id : dd,
						pageId : pageId,
						dataRole : dataRole
					},that);
					div_.css({
						border:'2px #FFB90F solid'
					});
					
					//清楚其他div_颜色
					jQuery.each(divs_,function(){
						if($(this).attr('id') != div_.attr('id')){
							$(this).css({
								'border-color' : ''
							});
						}
					});
					//清楚其他元素的颜色
					jQuery.each(crtltypes,function(){
						$(this).css({
							border : ''
						});
					});
					return false;
			})
			 .appendTo(doc.find('body'));
		
		divs_.push(div_);
		//注册鼠标移入事件
	//	$(this).bind({
	//		'mouseover': function(){
//				div_.show();
//			},
//			'mouseout':function(){
//				div_.hide();
//			}
//		});
		
	});
}

//预览
function showFunc(){
	if(jQueryFrame.attr('id')){
		var url = contextPath + "/mx/form/page/viewPage?" + jQuery.param({
			isPublish : true,
			id : jQueryFrame.attr('id')
		});
		window.open(url);
	}
}

//文本框双击查看
function showDetailDialog(obj){
	jQuery('#detailDialog').dialog({
		modal : true,
		closed : false,
		content : obj.value
	});
}

function selectPageBack(data){
	
	var obj = {
		name : '',
		value : ''
	};
	
	if(data){
		obj = {
			name : data.name,
			value : JSON.stringify([{id:data.id,name:data.name,url:data.url}])
		};
	}
	var $this = $(this);
	if($this[0]){
		var idfield = $this.attr('idfield');
		$('#id_' + idfield).val(obj.name);
		$this.val(obj.value);
	}
	
	layer.close(layer.getFrameIndex());
	
}
//选择关联页面
var selectPageDiv;
function selectPage(objElementId,nameElementId){
	
	//return obj {name : '',id : '',url : '',node : obj}
	var url = contextPath+"/mx/form/defined/ex/selectPage?"+$.param({
		fn : 'selectPageBack' ,
		targetId : objElementId,
	}) ;
	
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "页面选择",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['',''],
		fadeIn: 100,
		area: ['300px', '500px'],
        iframe: {src: url}
	});
	/*
	 var selectPageTree = $.fn.zTree.init($("#selectPageTree"), {
		view: {
			selectedMulti: false,
			dblClickExpand: true
		}
	}, zTree.getNodes());
	
	selectPageTree.expandAll(false);
	
	selectPageDiv = $('#selectPage').show().dialog({
		closed : false,
		title : '选择关联页面',
		buttons : [
			{
				text : '确定',
				iconCls : 'icon-ok',
				handler : function(){
					var nodes = selectPageTree.getSelectedNodes();
					if(nodes){
						var selectedNode = nodes[0];
						jQuery('#' + nameElementId).val(selectedNode.name);
						
						jQuery('#' + objElementId).val(JSON.stringify([{id:selectedNode.id,name:selectedNode.name}]));
						
						selectPageDiv.dialog('close');
					}
					return false;
				}
			},{
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function(){
					selectPageDiv.dialog('close');
				}
			}
		]
	});
	*/
}
var fieldTypeControl = [] ;
$.ajax({
	url : contextPath+'/mx/form/defined/getDictByCode',
	data : {code : 'FieldTypeControl'},
	type : 'post',
	dataType : 'json',
	async : false,
	success:function(data){ 
		$.each(data,function(i,d){
			var e = {} ;
			e.code = d.code ;
			e.value = d.value
			fieldTypeControl.push(e);
		});
	}
});
//转换字段类型
function getfieldTypeValue(fieldType){
	var v = "" ;
	$.each(fieldTypeControl,function(i,d){
		if(d.code == fieldType){
			v = d.value ;
		}
	})
	return v ;
}
//输入参数  表达式回调函数
function retInParametersFn(data){
	
}
//输入参数  表达式字段属性
function getInParametersFn(){
	//获取字典类型FieldTypeControl
	var expressionData = [] ;
	var datas = $("#"+tfMsg.dataSourceSetId).val() ;
	if(datas){
		var datasets = JSON.parse(datas);
		var rows = datasets[0].columns ;
		if(rows){
			for(var i=0;i<rows.length;i++){
				var express = {} ;
				var selectFieldRow = rows[i];
				express.t = selectFieldRow.title ;
				express.dType = getfieldTypeValue(selectFieldRow.FieldType) ;
				express.code = selectFieldRow.code ;
				express.value = selectFieldRow.value ;
				express.name = selectFieldRow.title ;
				expressionData.push(express);
			}
		}
	}
	return expressionData ;
}
//输入参数  表达式回显
function initInParametersFn(){
	
}

//弹出窗口选择表信息
var tfMsg = {};
function selectObj(obj){
	
	var $this = jQuery(obj);
	
	var nameElementId = $this.attr('nameElementId'),
		objElementId = $this.attr('objElementId'),
		dialogType = $this.attr('dialogType'),
		objJson = jQuery('#' + objElementId).val();
	
	if(!dialogType)
		return false;
	
	
	if(dialogType){
		switch(dialogType){
			case 'selectField':
				var tableObjElementId = tfMsg.tableObjElementId;
				var tableJson = '';
				if(tableObjElementId){
					tableJson = jQuery('#' + tableObjElementId).val();
				}
				selectField('','',nameElementId,objElementId,tableJson,objJson);
				break;
			case 'selectTreeField':  //树数据字段 弹出窗口
				var tableObjElementId = tfMsg.dataSourceSetId;
				var tableJson = '';
				if(tableObjElementId){
					tableJson = jQuery('#' + tableObjElementId).val();
				}
				selectTreeField('','',nameElementId,objElementId,tableJson,objJson,'',saveData.name);
				break;
			case  'selectTable':
				selectTable('','',nameElementId,objElementId,objJson);
				break;
			case 'dataSourceSet' :
				
				selectTableAndFields(objElementId,nameElementId,saveData.name);
//				selectTableAndFields(objElementId,nameElementId,"");
				//selectTableAndFields(objElementId,"tableName","fieldName");
				
				break;
			case  'columnTemplate':
				//alert(tfMsg.fieldObjElementId);
				modifyTableHander(nameElementId,objElementId,tfMsg.dataSourceSetId);
				//modifyTableHander("tableHander","headerObjElementId",null,"fieldObjElementId");
				break;
			case 'updateField':
				//modifyField('',fieldObjElementId,fieldJson,endFunction);
				var fieldJson = '';
				if(tfMsg.fieldObjElementId){
					fieldJson = jQuery('#' + tfMsg.fieldObjElementId).val();
					var nameVal = jQuery('#' + tfMsg.filedNameElementId).val();
					jQuery('#' + nameElementId).val(nameVal);
				}
				modifyField('',tfMsg.fieldObjElementId,fieldJson);
				break;
			case 'combineFields':
			case 'countFields':
			case 'pageParameters':
			case 'defaultOrderParameters':
				operateFields(obj,dialogType);
				break;
			case 'inParamDefined':
				var url = contextPath+"/mx/form/defined/param/paramDefined?"+$.param({
					nameElementId : nameElementId , //展示的ID
					objelementId : objElementId,//隐藏域的ID
				}) ;
//				window.open(url);
				$.layer({
					type: 2,
					maxmin: true,
					shadeClose: true,
					title: "输入形参定义",
					closeBtn: [0, true],
					shade: [0, '#000'],
					border: [10, 0.3, '#000'],
					offset: ['',''],
					fadeIn: 100,
					area: ['680px', '500px'],
			        iframe: {src: url}
				});
				break;
			case 'inParameters':
				var url = contextPath+"/mx/form/defined/param/inparam?"+$.param({
					nameElementId : nameElementId , //展示的ID
					objelementId : objElementId,//隐藏域的ID
					dataSourceSetId :tfMsg.dataSourceSetId ,
					inParamDefinedId : tfMsg.inParamDefinedId
				}) ;
//				window.open(url);
				$.layer({
					type: 2,
					maxmin: true,
					shadeClose: true,
					title: "输入表达式定义",
					closeBtn: [0, true],
					shade: [0, '#000'],
					border: [10, 0.3, '#000'],
					offset: ['',''],
					fadeIn: 100,
					area: ['980px', '500px'],
			        iframe: {src: url}
				});
				break;
			case 'selectIcon':
				
				var link = contextPath + "/mx/image/choose?" + $.param({
					fn : 'selectIconFunc',
					elementId : objElementId
				});
				//openWindow(link, self, 50, 50, 1098, 520);
				
				$.layer({
					type: 2,
					maxmin: true,
					shadeClose: true,
					title: "选择图标",
					closeBtn: [0, true],
					shade: [0, '#000'],
					border: [10, 0.3, '#000'],
					offset: ['',''],
					fadeIn: 100,
					area: ['1100px', '550px'],
			        iframe: {src: link}
				});
				
				break;
			case 'selectPage' : 
				selectPage(objElementId,nameElementId);
				break;
			case 'tableRelationDefined' : 
				var url = contextPath + '/mx/form/defined/ex/tableRelationDefined';
				window.open(url);
				break;
			case 'selectTabstrip' :
				
				var link = contextPath + '/mx/form/defined/ex/tabstrip_datasource?' + $.param({
					id : objElementId,
					name : nameElementId
				});
				
				$.layer({
					type: 2,
					maxmin: true,
					shadeClose: true,
					title: "选项卡配置",
					closeBtn: [0, true],
					shade: [0, '#000'],
					border: [10, 0.3, '#000'],
					offset: ['',''],
					fadeIn: 100,
					area: ['600px', '500px'],
			        iframe: {src: link}
				});
				break;
				
			case 'paraType':
				var link = contextPath + '/mx/form/defined/param/outInParam?' + $.param({
					pageId : jQueryFrame.attr('id'),
					eleId : objElementId,
					eleType : saveData.name,
					fn : "initInOutParameter"
				});
				
				$.layer({
					type: 2,
					maxmin: true,
					shadeClose: true,
					title: "输入&输出关系定义",
					closeBtn: [0, true],
					shade: [0, '#000'],
					border: [10, 0.3, '#000'],
					offset: ['',''],
					fadeIn: 100,
					area: ['980px', '500px'],
			        iframe: {src: link}
				});
				
				//window.open(link);
				break;
				
			case 'datatextfield':
			case 'datavaluefield':
				var $dataSourceSet = $("#" + tfMsg.dataSourceSetId),val = $dataSourceSet.val();
				if(!val){
					alert('请先配置数据源!');
					return false;
				} else {
					var data = JSON.parse(val);
					if(data[0]){	
						var columns = data[0].columns;
						
						var obj = $("#" + objElementId),json = [{}];
						
						if(obj.val()){
							json = JSON.parse(obj.val());
						}
						
						$("#selectFieldComb").combobox({
							data : columns,
							valueField: 'columnName',   
					        textField: 'title',
					        onSelect : function(data){
					        	data.name = data.title;
					        	$("#" + nameElementId).val(data.name);
					        	obj.val(JSON.stringify([data]));
					        }
						}).combobox("setValue",json[0].columnName);
						
						$("#selectField").show().dialog({
							closed : false,
							title : '请选择字段',
							buttons : [
							     {
							    	 text : '确定',
							    	 handler : function(){
							    		 $("#selectField").dialog('close');
							    	 }
							     },
							     {
							    	 text : '取消',
							    	 handler : function(){
							    		 $("#selectField").dialog('close');
							    	 }
							     }
							]
						});
						
					}
				}
				break;
			default :
				var link = contextPath + '/mx/form/defined/ex/' + dialogType;
				$.layer({
					type: 2,
					maxmin: true,
					shadeClose: false,
					title: dialogType,
					closeBtn: [0, true],
					shade: [0, '#000'],
					border: [10, 0.3, '#000'],
					offset: ['',''],
					fadeIn: 100,
					area: ['600px', '500px'],
			        iframe: {src: link}
				});
				break;
		}
	}
}

/**
 *  type[{
 *  	value : 文本框值
 *  	text : 单元格文本
 *  	row : 一行
 *  }]
 */

window.mt = {//全局参数
	control : {
		'default' : function(id){
		 return [
		     {
			 	id : id,
				text : 'value',
				type : 'getValue'
			}];
		},
		combobox : function(id){
			return [{
				id : id,
				text : 'text',
				type : 'getText'
			},{
				id : id,
				text : 'value',
				type : 'getValue'
			}];
		} ,
		dropdownlist : function(id){
			return mt.control.combobox(id);
		} ,
		grid : function(id){
			var datas = [{
				text : '选中行',
				items : []
			},{
				text : '选中单元格',
				items : [
			         {
			        	 text : '单元格值',
			        	 type : 'getText',
			        	 id : id
			         }
				]
			}];
			
			//获取列
			var ds = getDataSourceSet(id);
			if(ds && ds[0]){
				var columns = ds[0].columns;
				if(columns){
					$.each(columns,function(i,c){
						c.text = c.title;
						c.type = 'getRow';
						c.id = id;
						datas[0].items.push(c);
					});
				}
			}
			return datas;
		},treelist : function(id){
			var datas = [{
				text : '选中行',
				items : []
			},{
				text : '选中单元格',
				items : [
			         {
			        	 text : '单元格值',
			        	 type : 'getText',
			        	 id : id
			         }
				]
			}];
			
			//获取列
			var ds = getDataSourceSet(id);
			if(ds && ds[0]){
				var columns = ds[0].columns;
				if(columns){
					$.each(columns,function(i,c){
						c.text = c.title;
						c.type = 'getRow';
						c.id = id;
						datas[0].items.push(c);
					});
				}
			}
			return datas;
		},
		ztree : function(id){
			var datas = [{
				text : '选中节点',
				items : []
			}];
			//获取列
			var ds = getDataSourceSet(id);
			if(ds && ds[0]){
				var columns = ds[0].columns;
				if(columns){
					$.each(columns,function(i,c){
						c.text = c.title;
						c.type = 'getRow';
						c.id = id;
						datas[0].items.push(c);
					});
				}
			}
			return datas;
		},
		page : function(id){
			
		}
	}
},window.fzmt = {
	getControlParams : function(type,id){
		if(!(type in mt.control))
			type = 'default';
		var f = mt.control[type];
		if($.isFunction(f)){
			return f(id);
		} else {
			return f;
		}
	}
};

function getDataSourceSet(id){
	var datas = null;
	$.ajax({
		url : url + '/getDataSourceSet',
		data : {pageId : saveData.pageId, name : id},
		dataType : 'JSON',
		type : 'post',
		async : false,
		success:function(data){
			datas = data;
		}
	});
	return datas;
}

function getParametersByPageId(pageId){
	var datas = null;
	//获取输入参数
	$.ajax({
		url : contextPath + '/mx/form/defined/getParametersByPageId',
		data : {pageId : pageId},
		type : 'post',
		dataType : 'json',
		async : false,
		success:function(data){
			datas = data;
		}
	});
	return datas;
}

function getStuffByPageId(pageId){
	var datas = null;
	//获取输入参数
	$.ajax({
		url : contextPath + '/mx/form/defined/getStuffByPageId',
		data : {pageId : pageId},
		type : 'post',
		dataType : 'json',
		async : false,
		success:function(data){
			datas = data;
		}
	});
	return datas;
}

//输入输出参数
function initInOutParameter(data){
	if(data){
		$(this).val(JSON.stringify(data));
		$('#id_' + $(this).attr('idfield')).val(data[0].name);
		layer.close(layer.getFrameIndex());
	} else {
		var params = getParametersByPageId(saveData.pageId),inParams = {};//输出控件的输入参数
		if(params){
			if(saveData.pageId == saveData.name){
				$.each(params,function(i,v){
					if(v.PARAMTYPE_ == 'inParamDefined'){
						if(v.NAME_ != v.PAGEID_){
							v.text = v.TITLE_ || v.NAME_;
							v.items = [];
							if(v.VALUE_){
								$.each(JSON.parse(v.VALUE_),function(i,p){
									p.text = p.name;
									p.id = v.NAME_;
									p.title = v.NAME_ + "-选中行-" + p.name;
									v.items.push(p);
								});
							}
							inParams[v.NAME_] = v;
						}
					}
				});
			}else{
				$.each(params,function(i,v){
					if(v.PARAMTYPE_ == 'inParamDefined'){
						v.text = v.TITLE_ || v.NAME_;
						v.items = [];
						if(v.VALUE_){
							$.each(JSON.parse(v.VALUE_),function(i,p){
								p.text = p.name;
								p.id = v.NAME_;
								p.title = v.NAME_ + "-选中行-" + p.name;
								v.items.push(p);
							});
						}
						inParams[v.NAME_] = v;
					}
				});
			}

			
			
		}
		var $in = $("#" + tfMsg.linkageControlInId),
		$out = $("#" + tfMsg.linkageControlId),
		get = function(jq,isIn){
			var str = jq.val();
			if(str){
				try{
					var arr = JSON.parse(str);
					if(arr){
						var retArr = new Array();
						var obj0 = {
							text : '输入参数',
							items : [],
							level : 0
						},obj;
						$.each(arr,function(i,item){
							var role = item.id.replace(/[^a-z]*/g,'');//只保留字母
							var items = [];
							if(isIn){
								items = fzmt.getControlParams(role,item.id);
								obj = {
									text : item.text,
									id : item.id,
									items : items
								};
								retArr.push(obj);
							} else {
								var p = inParams[item.id];
								if(p)
									obj0.items.push(p);
							}
						});
						
						if(!isIn){
							retArr.push(obj0);
						}
						
						return retArr;
					}
				}catch(e){
					$.log(e);
				}
			}
		};
		
		var menus;
		
		if(saveData.pageId == saveData.name){//本页面页面参数
			var obj = {
					text : '输入参数',
					items : [],
					level : 0
				};
			$.each(inParams,function(i,item){
				var items = [];
				var p = inParams[i];
				if(p)
					items.push(p);
				
				obj.items.push(p);
			});
			
			
			
			var datas = [{
				text : '数据源',
				items : []
			},{
				text : '页面参数',
				items : []
			}];
			
			//获取数据源列
			var str = $('#' + tfMsg.dataSourceSetId).val();
			if(str){
				str = JSON.parse(str);
			}
			var ds = str;
			if(ds && ds[0]){
				var columns = ds[0].columns;
				if(columns){
					$.each(columns,function(i,c){
						c.text = c.title;
						c.type = 'getRow';
						c.id = saveData.pageId;
						datas[0].items.push(c);
					});
				}
			}
			
			//获取页面参数
			$.each(params,function(i,v){
				if(v.PARAMTYPE_ == 'inParamDefined' && v.NAME_ == saveData.pageId){
					v.text = '页面参数';
					v.items = [];
					if(v.VALUE_){
						$.each(JSON.parse(v.VALUE_),function(i,p){
							p.text = p.name;
							p.id = v.PAGEID_;
							p.inparam = p.param;
							p.type = 'getValue';
							v.items.push(p);
						});
					}
					datas[1] = v;
				}
			});
			
			menus = {
				'in' : datas,
				out : [obj]
			};
			
			
		} else {
			
			menus = {
				'in' : get($in,true),
				out : get($out,false) || [{
					text : '输入参数',
					items : [],
					level : 0
				} ]
			};
			
			var isSavePageLink = false;
			var id = '';
			if(tfMsg.pageElementId){
				if($("#" + tfMsg.pageElementId).val()){
					id = JSON.parse($("#" + tfMsg.pageElementId).val())[0].id;
				}
			}
			
			function getLinkPageParams(pageId){//获取当前页面所关联的所有页面参数
				var data = getStuffByPageId(pageId);
				if(data){
					$.each(data,function(i,v){
						if(id == v.PAGEID_)
							isSavePageLink = true;
						if(v.VALUE_){
							getInParamDefined(v.VALUE_);
						}
					});
				}
			}
			
			function getInParamDefined(str){
				if(str){
					var d = JSON.parse(str);
					if(d && d[0]){
						var r = d[0];
						params = getParametersByPageId(r.id),inParams = {};
						$.each(params,function(i,v){
							if(v.PARAMTYPE_ == 'inParamDefined' && v.NAME_ == r.id){
								if(id == v.PAGEID_){
									isSavePageLink = true;
								}
								v.text = v.TITLE_ || v.NAME_;
								v.items = [];
								if(v.VALUE_){
									$.each(JSON.parse(v.VALUE_),function(i,p){
										p.text = p.name;
										p.id = v.NAME_;
										v.items.push(p);
									});
								}
								menus['out'][0].items.push(v);
							}
						});
					}
				}
			}
			getLinkPageParams(saveData.pageId);//
			
			if(tfMsg.pageElementId && !isSavePageLink){//获取关联页面的接收参数
				var p = $("#" + tfMsg.pageElementId);
				getInParamDefined(p.val());
			}
			
			
		}
		
		var data = $(this).val();
		if(data){
			var v = JSON.parse(data)[0].datas;
			data = v;
		}
		
		if(!menus){
			alert("参数不够!");
			layer.close(layer.getFrameIndex());
			return false;
		}
		
		return {
			datas : data || {},
			menus : menus
		};
	}
}


function paraTypeFunc(data){
	var $inId = $("#" + tfMsg.inParametersId);//输入参数赋值
	if(data){
		if($inId[0]){
			$inId.val(JSON.stringify(data.datas));
		}
		
		//当前赋值
		var $thisName = $("#id_" + $(this).attr('idfield')),name = [];//输入框显示
		$.each(data.datas,function(i,v){
			name.push(v.name);
		});
		$thisName.val(name.join(','));
		$(this).val(JSON.stringify(data));//隐藏域
	} else {
		return $inId.val();
	}
}

function selectIconFunc(src){
	$('#id_' + $(this).attr('idfield')).attr({
		src : contextPath + src
	});
}

/**
 * 和并列,
 * 数据列统计
 */
var operateFieldsDialog;
var operates = {
	combineFields : {title:'合并列',datas:['合并字段名称 ',"<input class='easyui-validatebox textbox' id='combineField'/>",'所选字段名称',"<div id='combineFields'></div>"]},
	countFields : {title:'数据列统计',datas:['字段名',"<div id='countField' ></div>",'统计函数',"<div id='countFields'></div>"]},
	outParameters : {title:'输出参数对应',datas:['参数名',"<div id='outParametersName' ></div>",'对应字段',"<div id='outParametersField'></div>",'查询参数',"<div id='outParametersSyn'></div>"]},
	pageParameters : {title:'输入参数',datas:['参数名',"<input class='textbox' id='pageParameters' ></input>",'','']},
	defaultOrderParameters : {title:'默认排序',datas:['字段名',"<div id='defaultOrderField'></div>",'排序类型',"<div id='defaultOrderType'></div>"]},
};
function operateFields(obj,dialogType){
	
	var $this = jQuery(obj),fieldObjElementId = tfMsg.fieldObjElementId,fieldsName,
	fieldJson = '',fieldsData = new Array(),nameElementId = $this.attr('nameElementId'),
	objElementId = $this.attr('objElementId');
	
	var operator = operates[dialogType];
	if(operator){
		createDom(operator,dialogType);
		if(fieldObjElementId){
			fieldJson = jQuery('#' + fieldObjElementId).val();
			if(fieldJson){
				fieldsData = eval(fieldJson);
				if(jQuery.isArray(fieldsData)){
					jQuery.each(fieldsData,function(index,item){
						item.id = item.ColumnName;
						item.text = item.name;
					});
				}
			}
			if(dialogType == 'combineFields'){
				jQuery('#combineFields').combotree({
					multiple:true,
					data : fieldsData
				});
			}else{
				jQuery('#countField').combobox({
					editable:false,
					textField : 'name',
					valueField : 'ColumnName',
					onSelect: function(selected){   
			            jQuery(this).data('selected',selected); 
			        },
					data : fieldsData
				});
				jQuery('#countFields').combobox({
					editable:false,
					textField : 'name',
					valueField : 'code',
					onSelect: function(selected){   
			            jQuery(this).data('selected',selected); 
			        },
					url : contextPath + '/rs/dictory/jsonArray/function'
				});
			}
		}
	}
	
	if(dialogType == 'defaultOrderParameters'){
				
		var linkageControlInData = $('#' + tfMsg.linkageControlInId).val() || JSON.stringify([{id:saveData.name}]);
		if(linkageControlInData){
			getTableFields(linkageControlInData,function(fields){
				var item = jQuery('#defaultOrderField');
				item.combotree({
					editable:false,
					multiple:true,
					onCheck : function(node){
						var tree = item.combotree('tree'); // 得到树对象  
						var selected = tree.tree('getChecked') || ''; // 得到选择的节点
						jQuery(this).data('selected',selected); 
					},
					data : fields
				});
			});
		}
		
		jQuery('#defaultOrderType').combobox({
			editable:false,
			textField : 'text',
			valueField : 'value',
			onSelect: function(selected){   
	            jQuery(this).data('selected',selected); 
	        },
			data : [{value:'desc',text:'反序'},{value:'asc',text:'正序'}]
		});
	}
	
	if(dialogType == 'pageParameters'){
		//createDom(operator);
	}else if(dialogType == 'outParameters'){
		if(tfMsg.linkageControlInId || saveData.name){
		//	tfMsg.pageElementId = tfMsg.pageElementId || 
			//createDom(operator);
			var pageData = $('#' + tfMsg.pageElementId).val() || JSON.stringify([{id:jQueryFrame.attr('id')}]),linkageControlInData = $('#' + tfMsg.linkageControlInId).val() || JSON.stringify([{id:saveData.name}]);
			if(pageData){
				var pageJSON = jQuery.parseJSON(pageData);
				var pageId = pageJSON[0].id;
				if(pageId){
					//createDom(operator);
					$.getJSON(url + '/getFormRule',{name:pageId,pageId:pageId},function(data){
						if(data && data[0].pageParameters){
							jQuery('#outParametersName').combobox({
								editable:false,
								textField : 'name',
								valueField : 'value',
								onSelect: function(selected){   
						            jQuery(this).data('selected',selected); 
						        },
								data : jQuery.parseJSON(data[0].pageParameters)
							});
						}
					});
				}
				if(linkageControlInData){
					getTableFields(linkageControlInData,function(fields){
						jQuery('#outParametersField').combobox({
							editable:false,
							textField : 'name',
							valueField : 'ColumnName',
							onSelect: function(selected){
								//$.log(selected);
								jQuery(this).data('selected',selected); 
							},
							data : fields
						});
					});
				}
			}
		}
	}
	
	
	function createDom(operator,dialogType){
		
		jQuery.each(operator.datas,function(index,item){
			jQuery('#operator' + index ).html(item);
		});
		
		/*if(dialogType == 'outParameters'){
			$('#outParametersSyn').combobox({
				editable:false,
				textField : 'name',
				valueField : 'code',
				onSelect: function(selected){
		            jQuery(this).data('selected',selected); 
		        },
				url : contextPath + '/rs/dictory/jsonArray/parameter'
			});
			$('.syn').show();
		}else{
			$('.syn').hide();
		}*/
		
		jQuery('#add-operator').attr('onclick',dialogType + 'AddFunc()');
		operateDialog(operator);
	}
	
	function operateDialog(operator){
		var conbineJson = jQuery('#' + objElementId).val();
		if(conbineJson)
			conbineJson = eval(conbineJson);
		showOperateList(conbineJson);
		
		operateFieldsDialog = jQuery('#operateFieldsDialog').dialog({
			title : operator.title,
			closed : false,
			modal : true,
			buttons : [
				{
					text : '确定',
					iconCls : 'icon-ok',
					handler:function(){
						var operateList = jQuery('#operateList').data('operateList');
						if(operateList){
							fieldsName = '';
							jQuery.each(operateList,function(index,v){
								fieldsName += "," + v.name;
							});
							fieldsName = fieldsName.substring(1);
							jQuery('#' + nameElementId).val(fieldsName);
							conbineJson = JSON.stringify(operateList);
							jQuery('#' + objElementId).val(conbineJson);
							operateFieldsDialog.dialog('close');
						}
					}
				},
				{
					text : '关闭',
					iconCls : 'icon-cancel',
					handler : function(){
						operateFieldsDialog.dialog('close');
					}
				}
			]
		});	
	}
}

/**
 * 获取当前控件配置的表字段
 * @param linkageControlInData
 * @param fn
 */
function getTableFields(linkageControlInData,fn){
	if(linkageControlInData){
		var pageId = jQueryFrame.attr('id');//当前页面
		var linkageControlInJson = jQuery.parseJSON(linkageControlInData);
		var names = new Array();
		jQuery.each(linkageControlInJson,function(i,v){
			names.push(v.id);
		});
		$.getJSON(url + '/getFormRule',{name:names.join(','),pageId:pageId},function(data){
			if(data){
				var fields = new Array();
				var tableTmp = new Object();
				jQuery.each(data,function(i,v){
					if(v.dataSourceSet){
						var dataSourceSet = jQuery.parseJSON(v.dataSourceSet);
						var datasetId = dataSourceSet[0].datasetId ;
						$.ajax({
	                        url: contextPath + "/rs/dataset/getLastestJson",
	                        type: "POST",
	                        dataType:"JSON",
	                        data:{datasetId : datasetId},
	                        async : false,
	                        success : function(data){
	                        	if(data){
	                        		$.each(data.selectSegments,function(i,item){
	                        			if(!tableTmp[item.dname]){
	                        				var v = new Object();
	                        				v.name = item.title;
	                        				v.ColumnName = item.columnName;
	                        				v.id = v.ColumnName;
	                        				v.text = v.name;
                        					fields.push(v);
                        					tableTmp[item.title] = item;
                        				}
	                        		});
	                        	}
	                        }
						});
//						jQuery.each(tables,function(i,o){
//							$.ajax({
//								contentType: "application/json",
//		                        url: contextPath + "/rs/isdp/cellDataField/selectFieldByTableId",
//		                        type: "POST",
//		                        dataType:"JSON",
//		                        data:JSON.stringify({
//		                        	tableId:o.TableId,
//		                			type:99,
//		                			tableName:o.TableName
//		                        }),
//		                        async : false,
//		                        success : function(data){
//		                        	if(data){
//		                        		$.each(data.rows,function(i,item){
//		                        			if(!tableTmp[item.dname]){
//		                        				var v = new Object();
//		                        				v.name = item.fname;
//		                        				v.ColumnName = item.dname;
//		                        				v.id = v.ColumnName;
//		                        				v.text = v.name;
//	                        					fields.push(v);
//	                        					tableTmp[item.dname] = item;
//	                        				}
//		                        		});
//		                        	}
//		                        }
//							});
//						});
					}
				});
				if(fn){
					fn(fields);
				}
			}
		});
	
	}
}


function defaultOrderParametersAddFunc(){
	var defaultOrderField = jQuery('#defaultOrderField'),
	defaultOrderType = jQuery('#defaultOrderType'),fieldsName,fieldsValue;
	var tree = defaultOrderField.combotree('tree'); // 得到树对象  
	var defaultOrderFieldData = tree.tree('getChecked') || ''; // 得到选择的节点
	
	if(defaultOrderFieldData && defaultOrderFieldData.length > 0){
		if(defaultOrderType.combobox('getValue')){
			var defaultOrderTypeData = defaultOrderType.data('selected');
			var operateList = jQuery('#operateList').data('operateList');
			if(!operateList)
				operateList = new Array();
			var SB = new StringBuffer("[");
			var fields = new Array();
			$.each(defaultOrderFieldData,function(i,v){
				SB.append(v.text);
				fields.push({
					ColumnName : v.ColumnName,
					text : v.text
				});
			});
			SB.append("]");
			
			fieldsName =  SB.toString(" ") + " " + defaultOrderTypeData.text;
			
			operateList.push({
				name : fieldsName,
				fieldsName : fieldsName,
				defaultOrderField : fields,
				defaultOrderType : defaultOrderTypeData
			});
			
			defaultOrderField.combotree('setValue',[]);
			
			defaultOrderType.combobox('setValue','');
			
			showOperateList(operateList);
		}else{
			alert('请选择排序类型!');
		}
	}else{
		alert('请选择字段名!');
	}
}

function outParametersAddFunc(){
	var outParametersField = jQuery('#outParametersField'),
		outParametersName = jQuery('#outParametersName'),
		outParametersSyn = jQuery('#outParametersSyn'),
		fieldsName,fieldsValue;
	if(outParametersName.combobox('getValue')){
		var outParametersFieldData = outParametersField.data('selected');
		if(outParametersField.combobox('getValue')){
			var outParametersNameData = outParametersName.data('selected');
			var operateList = jQuery('#operateList').data('operateList');
			if(!operateList)
				operateList = new Array();
			fieldsName = outParametersNameData.name + " = " + outParametersFieldData.name;
			operateList.push({
				name : outParametersFieldData.name,
				fieldsName : fieldsName,
				column : outParametersFieldData,
				value : outParametersNameData
				/*syn : {
					name : outParametersSynData.name,
					value : outParametersSynData.value,
					code : outParametersSynData.code
				}*/
			});
			outParametersField.combobox('setValue','');
			outParametersName.combobox('setValue','');
			//outParametersSyn.combobox('setValue','');
			showOperateList(operateList);
		}else{
			alert('请选择对应列名!');
		}
	}else{
		alert('请选择参数名!');
	}
}

function pageParametersAddFunc(){
	var pageParameters = jQuery('#pageParameters'),value = pageParameters.val();
	if(!value){
		alert('参数名不能为空!');
		return false;
	}
	var operateList = jQuery('#operateList').data('operateList');
	if(!operateList)
		operateList = new Array();
	var index = 0;
	
	function getMax(a,b){
		return (a > b) ? a : b;
	}
	
	for(var i in operateList){
		var o = operateList[i];
		if(o.name === value){
			alert('该参数名已经存在!');
			return false;
		}
		var v = o.value.substring(1) * 1;
		index = getMax(v,index);
	}
	var v = encodeURI(encodeURI(value));
	operateList.push({
		name : value,
		text : value,
		fieldsName : value,
		value : v,
		id : v
	});
	
	showOperateList(operateList);
	
	pageParameters.val('');
}

function countFieldsAddFunc(){
	var countField = jQuery('#countField'),
		countFields = jQuery('#countFields'),
		fieldsName,fieldsValue;
	if(countField.combobox('getValue')){
		var countFieldData = countField.data('selected');
		if(countFields.combobox('getValue')){
			var countFieldsData = countFields.data('selected');
			var operateList = jQuery('#operateList').data('operateList');
			if(!operateList)
				operateList = new Array();
			fieldsName = countFieldData.name + "=" + countFieldsData.name;
			operateList.push({
				name : countFieldData.name,
				fieldsName : fieldsName,
				fieldsValue : countFieldData,
				func : countFields.combobox('getValue')
			});
			countField.combobox('setValue','');
			countFields.combobox('setValue','');
			showOperateList(operateList);
		}else{
			alert('请选择统计函数!');
		}
	}else{
		alert('请选择统计列!');
	}
}

function combineFieldsAddFunc(){
	var combineField = jQuery('#combineField'),
		combineFields = jQuery('#combineFields'),
		fieldsName,fieldsValue;
	if(!combineField.val()){
		alert('合并列名称不能为空!');return false;
	}
	var tree = combineFields.combotree('tree'); // 得到树对象  
	var nodes = tree.tree('getChecked'); // 得到选择的节点
	if(nodes.length < 2){
		alert('请选择两列或更多!');return false;
	}
	fieldsName = combineField.val() + "=";
	fieldsValue = new Array();
	var operateList = jQuery('#operateList').data('operateList');
	if(!operateList)
		operateList = new Array();
		
	for(var i in operateList){
		var o = operateList[i];
		if(o.name === combineField.val()){
			alert('该合并列名称已经存在!');return false;
		}
	}
	jQuery.each(nodes,function(index,node){
		fieldsName += node.text + "+";
	});
	fieldsName = fieldsName.substring(0,fieldsName.length -1);
	operateList.push({
		name : combineField.val(),
		fieldsName : fieldsName,
		fieldsValue : nodes
	});
	combineField.val('');
	combineFields.combotree('setValue',[]);
	showOperateList(operateList);
}

function deleteCombineList(){
	var row = jQuery('#operateList').data('selectedRow');
	if(row){
		var rowIndex = jQuery(row).attr('row-index');
		var operateList = jQuery('#operateList').data('operateList');
		operateList.splice(rowIndex, 1);
		showOperateList(operateList);
	}
}

function showOperateList(arr){
	var datas = new Array();
	if(arr){
		jQuery.each(arr,function(index,item){
			datas.push('<tr row-index=');
			datas.push(index);
			datas.push('><td><span>');
			datas.push(item.fieldsName);
			datas.push('</span></td></tr>');
		});
	}
	jQuery('#operateList').empty().append(datas.join('')).data('operateList',arr).data('selectedRow',null);
	jQuery('#operateList tr').bind('click',function(){
		jQuery(this).css({
			background : '#FFF68F'
		}).siblings().css({
			background : ''
		});
		jQuery('#operateList').data('selectedRow',this);
	});
}