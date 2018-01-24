/**
 * 定义平台js   defined.kendo.js 
 * klaus.wang 
 */

/**
 * 布局 start
 */

var $tabstrip = $(".tabstrip"),$vertical = $("#vertical"), contextPath = mtxx.contextPath;
var updateComponents= new Array();
updateComponents.push('dropdownlist');
updateComponents.push('grid');
Array.prototype.contains = function(item){
	return RegExp(item).test(this);
};
$vertical.css({
	height : $(document).height()*0.97
}).kendoSplitter({
	orientation: "vertical",
	panes: [
		{ collapsible: false , resizable : false, scrollable: false ,size : '32px'},
		{ collapsible: false, resizable : true, scrollable: false}
	],
	layoutChange : onResize,
});

$("#center-pane").kendoSplitter({
	orientation: "horizontal",
	panes: [
		{ collapsible: true, resizable : true, scrollable: true},
		{ collapsible: true, resizable : true, scrollable: false,size : '340px'}
	]
});

function onResize(e){
	
	$vertical.css({
		height : $(window).height()*0.97
	});
	
	$tabstrip.css({
		height : $tabstrip.closest('div[role=group]').height()
	});
	
}

$tabstrip.each(function(i,item){
	var $this = $(this);
	mtxx[$this.attr('id')] = $this.kendoTabStrip({
		animation:  {
			open: {
				effects: "fadeIn"
			}
		},
		select : function(e){
			var dd = $(this.wrapper);
			$('#' + e.contentElement.id).height(dd.innerHeight() - dd.children(".k-tabstrip-items").outerHeight() - 16);
		}
	});
});

/**
 * 保存设置
 */
$('#button-1').kendoButton({
	imageUrl : mtxx.contextPath + "/images/database_save.png",
	click : function(){
		if(mtxx.saveData){
			var field = null,val,collection = new Object();
			var json = mtxx.resultData.data.value || new Object();
			$(".prop-table").each(function(i,v){
				
				var id = $(this).attr('id');
				
				if(!(id in collection)){//按数据类型分类
					collection[id] = new Object();
				}
				
				$(this).find("input[idField]").each(function(index,item){
					
					field = jQuery(this).attr('idfield');
					if(field){
						val = jQuery(this).val(); //|| json[field];
						json[field] = val;
						collection[id][field] = val;
					}
				});
			});
			
			if(field){
				var saveData = mtxx.saveData;
				saveData.id = saveData.formRuleId;
				saveData.value = JSON.stringify(json);
				saveData.collection = JSON.stringify(collection);
				
				mtxx.jQueryFrame.data('save',{id:saveData.name,isSave:true});
				
				jQuery.post(mtxx.url + '/saveOrUpdateFormRule',saveData,function(data){
					if(data){
						saveData.formRuleId = data.id;
						alert('操作成功!');
					}
				},'json');
				
			}
		} else {
			alert('请配置属性!');
		}
		
	}
});

/**
 * 预览
 */
$('#button-2').kendoButton({
	imageUrl : mtxx.contextPath + "/images/eye.png",
	click : function(){
		if(mtxx.jQueryFrame){
			var id = mtxx.jQueryFrame.attr('id');
			if(id){
				var url = mtxx.contextPath + "/mx/form/page/viewPage?" + $.param({
					isPublish : true,
					id : id
				});
				window.open(url);
			}
		} else {
			alert('请选择!');
		}
		
	}
});

/**
 * 增加分类
 */
$('#button-3').kendoButton({
	imageUrl : mtxx.contextPath + "/images/documents_add.png",
	click : function(){
		createSort();
	}
});

/**
 * 搜索
 */
$('#button-4').kendoButton({
	imageUrl : mtxx.contextPath + "/images/search.png",
	click : function(){
		var searchTextBox = $("#searchTextBox"),val = searchTextBox.val();
		var f = mtxx.zTree.expandAll(false);
		function updateHighlight(nodes,flag){
			$.each(nodes,function(i,node){
				node.highlight = flag || false;
				mtxx.zTree.updateNode(node);
			});
		}
		if(!f){
			setTimeout(function(){
				var ns = searchTextBox.data('nodes');
				if(ns && ns[0]){
					updateHighlight(ns,false);
				}
				if(val){
					var nodes = mtxx.zTree.getNodesByParamFuzzy("name", val, null);
					if(nodes && nodes[0]){
						$.each(nodes,function(i,node){
							if(!node.isParent){
								node = node.getParentNode();
							}
							mtxx.zTree.expandNode(node, true, true, false);
						});
						updateHighlight(nodes,true);
						searchTextBox.data('nodes',nodes);
					}
				}
			}, 500);
		}
	}
});
/** * 布局 end */


/**
 * 页面初始方法
 */
$(function(){
	tabstripAppend("页面布局",pageId);	
	var dictSort = mtxx.dictSort = {};
	mtxx.getDictByCode('property_sort',function(data){
		if(!dictSort.data){
			dictSort.data = new Object();
		}		
		$.each(data,function(i,v){
			dictSort.data[v.code] = v;
		});
	});
});

/**
 * 定义页面
 * 
 * @param text
 * @param id
 */
function tabstripAppend(text,id){
	
	var $tabstrip1 = mtxx["tabstrip-1"].data('kendoTabStrip'),selector = "li:last";
	
	$tabstrip1.remove(selector);
	
	mtxx.jQueryFrame = jQuery('<iframe>', {
		frameborder : 0,
		scrolling : 'auto',
		id : id,
		style : 'width:100%;height:99%'
	}).attr({
		src: mtxx.url + '/getFormPageHtmlById?id=' + id,
		onload:function(){
			setTimeout(function(){
				
				populateJs(mtxx.jQueryFrame = $('#' + id));				
				$tabstrip1.wrapper.find(selector).bind('click',function(){
					mtxx.jQueryFrame.contents().find("body").click();
				}).attr({
					title : '点击配置页面属性'
				});
				
				mtxx["tabstrip-2"].data('kendoTabStrip').remove("li");
				initSelectedCrtltype.click();
			},800);  // 延迟加载，保证iframe 完全加载后注入js
		}
	});
	
	$tabstrip1.append({
		text : text,
		content : mtxx.jQueryFrame.outter()
	}).select(selector);
	
	
}

/**
 * 注册定义页面事件
 * 
 * @param jQueryFrame
 */
//初始选中的控件
var initSelectedCrtltype;
function populateJs(jQueryFrame){
	var doc = jQueryFrame.contents(),divs_ = new Array();
	var crtltypes = doc.find('[crtltype]'),pageId = jQueryFrame.attr("id");
	if(crtltypes && crtltypes.length){
		var $this,$body = doc.find("body");
		$body.bind('click',function(index,item){
			mtxx.saveData = {
					name : pageId,
					pageId : pageId,
					dataRole : 'page'
			};
			showComponentProperties({
				id : pageId,
				pageId : pageId,
				dataRole : 'page'
			},$(this));
			return false;
		});
		 $.each(crtltypes,function(index,item){
			
			 $this = $(item).unbind('click');
		
			 $this.bind('click',function(e){
				 
				$this = jQuery(this);
				var id = $this.attr('id');
				var dataRole = $this.attr('data-role');
				
				if(dataRole){ 
					dataRole = dataRole.toLowerCase();
				}
				mtxx.saveData = {
					name : id,
					pageId : pageId,
					dataRole : dataRole
				};
				showComponentProperties({
					id : id,
					pageId : pageId,
					dataRole : dataRole
				},$this);
				// 清除其他div_颜色
				jQuery.each(divs_,function(){
					$(this).css({
						'border-color' : ''
					});
				});
				
				jQuery.each(crtltypes,function(){
					
					if($(this).attr('id') == id){
						$this.css({
							border:'5px #FFB90F solid'
						});
					}else if(updateComponents.contains($(this).attr('id')))
					{
						$(this).css({
							border:'5px red solid'
						});
					}else{
						$(this).css({
							border:''
						});
					}
				});
				return false;
			});
			if(updateComponents.contains($(item).attr('id')))
			{
			    if(!initSelectedCrtltype)
		    	{
		    	 initSelectedCrtltype=$(item);
		    	}
				$(item).css({
								border:'5px red solid'
							});
			}
			 
		 });
		
	}
	doc.find("[data-hover='hover']").each(function(i,v){
		 $this = $(this);
		 var id = $this.attr('id');
		 var offset = $this.offset();
		 var left = offset.left+$this.width()-30;
		 var div_ = $('<div>',{
			   id : id + '_div',
			  'data-role':'splitter',
			  'crtltype':'splitter'
			});
			 div_.css({
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
					mtxx.saveData = {
							name : dd,
							pageId : pageId,
							dataRole : dataRole
					};
					showComponentProperties({
						id : dd,
						pageId : pageId,
						dataRole : dataRole
					},that);
					div_.css({
						border:'2px #FFB90F solid'
					});
					
					// 清除其他div_颜色
					jQuery.each(divs_,function(){
						if($(this).attr('id') != div_.attr('id')){
							$(this).css({
								'border-color' : ''
							});
						}
					});
					// 清除其他元素的颜色
					jQuery.each(crtltypes,function(){
						$(this).css({
							border : ''
						});
					});
					return false;
			})
			 .appendTo(doc.find('body'));
		
		divs_.push(div_);
		
	});
}
var dictDatas = new Object();
/**
 * 获取、显示配置属性
 * 规则说明: 每个属性配置都有一个显示域与隐藏域。
 * 1、显示域 id 为 (id_ + form属性id); eg : var id = 'id_' + itemid;
 * 2、隐藏域 id 为 显示域 id + '_hidden'; eg : var hiddenId = 'id_' + itemid + '_hidden'
 * 注: 每个dom 都有一个属性(attr) 'itemid'
 * @param options
 * @param jq
 * @returns {showComponentProperties}
 */
function showComponentProperties(options,jq){
	this.options = options;
	this.jq = jq;
	window.K = this;
	this.init = function(){
		mtxx.tfMsg = {};
		mtxx.resultData = {};
		jQuery.ajax({
			url : mtxx.url +  '/getFormCompoentProperties',
			type : 'post',
			data : options,
			async : false,
			dataType : 'json',
			success : function(data){
				if(data){
					mtxx.resultData.data = data;
					mtxx.saveData.formRuleId = data.formRuleId;
					var obj = new Object();
					mtxx.rows = new Object();
					if(data.rows.length > 0){
						data.rows.sort(function(a,b){
							return b.listNo - a.listNo;//根据容器属性排序(倒序)
						});
					}
					mtxx.hideRows = new Object();
					function populateObj(item,collection){//按类型分组
						if(!(item.type in collection)){
							collection[item.type] = new Array();
						}
						collection[item.type].push(item);
					}
					
					jQuery.each(data.rows,function(index,item){
						
						var pValue = item.pValue,parentId = item.parentId;
						if(pValue){
							jQuery.each(pValue.split(','),function(i,v){
								if(!(v in mtxx.hideRows)){
									mtxx.hideRows[v] = new Object();
								}
								populateObj(item,mtxx.hideRows[v]);
							});
						}
						if(!parentId){
							populateObj(item,obj);
						} else {
							if(!mtxx.resultData[parentId]){
								mtxx.resultData[parentId] = new Array();								
							}
							mtxx.resultData[parentId].push(item);
						}
					});
					var tabs = new Array(),name,table;
					for(var i in obj){						
						if(i in mtxx.dictSort.data){
							name = mtxx.dictSort.data[i].name;							
							table = new StringBuffer();
							table.appendFormat("<table class='prop-table' id={0} width='100%' border=0 cellpadding=5 cellspacing=0 align='left'>",i);
							K.createPropertyDocument(obj[i],function(tr){
								table.append(tr.join(''));
							});
							table.append("</table>");
							tabs.push({
								text : name,
								content : table.toString()
							});
						}																							
					}
					$tabstrip2Append(tabs);
					K.initEvent($('.prop-table'));
				}
			}
		});
	};
	
	this.hideRows = {};
	
	this.showHideRows = function(value_){
		if(value_){
			var obj = {};
			for(var i in value_){
				obj[i] = value_[i];
				var hide = value_[i],roleindex = hide.id,value = hide.value;
				var $tr = $("table tr[roleindex=" + roleindex + "]");
				if($tr[0]){
					var hideRow = mtxx.hideRows[value],pad = $tr.attr('pad');
					if(hideRow){
						$.each(hideRow,function(key,val){
							K.createPropertyDocument(val,function(tr){
								$tr.after(tr.join(''));
							},pad,$tr.attr('class'));
						});
					}
					K.initEvent($('.cls_' + roleindex));
				}
			}
			var t = null;
			value_ = mtxx.tfMsg.value_;
			if(value_){
				for(var key in value_){
					if(!(key in obj)){
						if(t == null)
							t = new Object();
						t[key] = value_[key];
					}
				}
				mtxx.tfMsg.value_ = null;
				K.showHideRows(t);
			}
		}
	};
	
	this.createPropertyDocument = function(data,fn,pad,cls){
		var tr = new Array(), df = new K.domFn({
			tr : tr,
			span : "<span style='color:#fff;'  >·</span>",
			pad : pad || 1,
			cls : cls || ''
		});
		$.each(data,function(index,item){
			df.dTypeFn.init(item);
		});
		if(fn){
			fn(tr);
		}
		
	};
	
	this.domFn = function(options){
		this.options = options;
		var Fn = this;
		this.dTypeFn = {
			init : function(item){
				item.value ? item.value = item.value : item.value = '';
				item.parentId ? item.parentId = item.parentId : item.parentId = -1;
				mtxx.saveData.componentId = item.componentId;
				var id = 'id_' + item.id,isSort = item.isSort == '0',editor = item.editor,pad = Fn.options.pad,desc = item.desc || '';
				Fn.options.tr.push("<tr title='" + desc + "' roleIndex='"+ item.id +"' class='"+ Fn.options.cls +" cls_"+ item.parentId +"' isSort='" + isSort + "' pad=" + pad + " ><td class='tdCls' >");
				function padding(n,collection){
					for(var i = 0; i < n ; i ++){
						collection.push(Fn.options.span);
					}
				}
				if(isSort){//分类
					var $img = $("<img>",{
						id : 'img_' + item.id,
						src : mtxx.contextPath + '/webfile/icons/open.png',
						onclick : 'openChildren(this)',
						name : item.id,
						itemid : item.id
					});
					padding(pad - 1,Fn.options.tr);
					Fn.options.tr.push($img.outter());
				}else{
					padding(pad,Fn.options.tr);
				}
				Fn.options.tr.push(item.title);
				Fn.options.tr.push('</td><td class="tdCls" style="width:60%;">');
				if(editor){
					if(!isSort){
						var fn = editor.type,rst;
						if(Fn.dTypeFn[fn]){
							rst = Fn.dTypeFn[fn](item,id,Fn.options.tr);
						}else{
							rst = Fn.dTypeFn["def"](item,id,Fn.options.tr);
						}
						
						if(rst){
							Fn.options.tr.push($('<input>',{
								id:id + '_hidden',
								idField:item.id,
								name : item.id,
								type:'hidden',
								itemid : item.id,
								value:item.hiddenValue || item.value
							}).outter());
						}
					}
				}else {
					Fn.options.tr.push(item.value);
				}
				Fn.options.tr.push('</td></tr>');
			},
			def : function(item,id,tr){
				var value_ = item.value_,idField = '',json = new Object(),jdom,cls = '';
				var selectLength = 'selectLength';
				if(!value_){
					idField = item.id;
					json.value = item.value;
				}else{
					//id = 'name_' + item.id;
					function endswidth(str,syn){
						if(!str || !syn){
							return false;
						}
						if(str.substring(str.length - syn.length) == syn)
						  return true;
						else
						  return false;
					}
					if(item.value){
						if(!dictDatas[selectLength]){
							mtxx.getDictByCode(selectLength,function(data){
								dictDatas[selectLength] = data;
							});
						}
						$.each(dictDatas[selectLength],function(i,v){
							var syn = v.code;
							if(endswidth(item.value,syn)){
								json.value = item.value.substring(0,item.value.length - syn.length);
								json.code = syn;
							}
						});
					}
					cls = 'changeCls';
				}
				var jdomOption = {id:id,itemid:item.id,idField:idField,name:item.id,type:'text','class':'textCls k-textbox ' + cls,value:json.value};
				if(item.dataType == 'int' || item.dataType == 'double'){
					jdomOption['class'] = 'easyui-numberbox ' + cls ;
					jdomOption.dataType = item.dataType;
				}
				if(item.name == 'id'){
					jdomOption.readonly = 'readonly=true';
					jdomOption.value = item.value ? item.value : K.options.id;
				}else if(item.name == 'html'){
					jdomOption.value = item.value || K.jq.attr('title') || K.jq.attr('name') || K.jq.text();
				}
				jdom = jQuery('<input>', jdomOption).outter();
				tr.push(jdom);
				if(value_ && value_ == selectLength){
					/*
					tr.push($("<input>",{
						idField : item.id,
						id : 'hidden_' + item.id,
						type : 'hidden',
						name : item.id,
						value : item.value
					}).outter());
					*/
					tr.push("<select itemid='"+item.id+"' name='" + item.id + "'  class='definedcombo' style='width:70px;margin-left:5px;'  value_='"+ item.value_ +"' >");
					if(dictDatas[selectLength]){
						jQuery.each(dictDatas[selectLength],function(i,o){
							if(json.code == o.code){
								selected = 'selected';
							}else
								selected = '';
							tr.push("<option value='"+o.code+"' "+ selected +"   >");
							tr.push(o.name);
							tr.push("</option>");
						});
					}
					tr.push("</select>");
					
					return true;
				}
			},
			text : function(item,id,tr){
				tr.push(jQuery('<input>',{id:id,itemid:item.id,idField:item.id,name:item.id,class:'textCls',value:item.value}).outter());
			},
			dialog : function(item,id,tr){
				var value = item.value,hiddenId = id + '_hidden',hiddenValue = '',json = value,editor = item.editor;
				try{
					if(!(value instanceof Array) || !(value instanceof Object))
						json = JSON.parse(value);
				}catch(e){}
				if(json instanceof Array){
					hiddenValue = JSON.stringify(json);
					value = [];
					jQuery.each(json,function(i,v){
						value.push(v.name);
					});
					value = value.join(',');
				}else if(json instanceof Object){
					hiddenValue = JSON.stringify(json);
					value = [];
					jQuery.each(json.datas,function(i,v){
						value.push(v.name);
					});
					value = value.join(',');
				}
				if(editor.data){
					if(editor.data == 'selectTable'){
						mtxx.tfMsg.tableObjElementId == null ? mtxx.tfMsg.tableObjElementId = hiddenId : "";
					} else if(editor.data == 'selectField'){
						mtxx.tfMsg.fieldObjElementId == null ?  mtxx.tfMsg.fieldObjElementId = hiddenId : '';
						mtxx.tfMsg.filedNameElementId == null ? mtxx.tfMsg.filedNameElementId = id : '';
					}else if(editor.data == 'selectTreeField'){
						mtxx.tfMsg.selectTreeFieldId == null ? mtxx.tfMsg.selectTreeFieldId = hiddenId : '';
					}else if(editor.data == 'selectPage'){
						mtxx.tfMsg.pageElementId == null ? mtxx.tfMsg.pageElementId = hiddenId : '';
					}else if(editor.data == 'dataSourceSet'){
						mtxx.tfMsg.dataSourceSetId == null ? mtxx.tfMsg.dataSourceSetId = hiddenId : '';
					} else {
						var objId = editor.data + "Id";//
						mtxx.tfMsg[objId] = mtxx.tfMsg[objId] || hiddenId;
					}
				}
				var jdom;
				if(editor.data && editor.data == 'selectIcon'){//图标
					var src = value ? value : '/images/FIX_edit.png';
					hiddenValue = src;
					jdom = jQuery('<img>',{
						id:id,
						readonly:true,
						name:item.id,
						itemid:item.id,
						src:mtxx.contextPath + src,
						alt:'图片',
						style : 'margin-right:40px;margin-top:5px;margin-left:45px;'
				    });
					tr.push(jdom.outter());//显示值
				}else{
					jdom = jQuery('<input>',{
						id:id,
						class:'easyui-validatebox k-textbox textCls',
						readonly:true,
						name:item.id,
						itemid:item.id,
						type:'text',
						value:value,
						ondblclick:'showDetailDialog(this)'
					});
					tr.push(jdom.outter());//显示值
				}
				
				item.hiddenValue = hiddenValue;
				//jdom = jQuery('<input>',{id:hiddenId,idField:item.id,name:item.id,type:'hidden',value:hiddenValue});
				//tr.push(jdom.outter());//隐藏值(保存)
				jdom = jQuery('<a>',{
					'class' : 'linkbutton',
					value : '..',
					dialogType : editor.data,
					nameElementId : id,
					objElementId : hiddenId,
					name : item.id,
					itemid:item.id,
					onclick : 'selectObj(this)'
				}).html('.');
				tr.push(jdom.outter());
				
				return true;
			},
			checkbox : function(item,id,tr){
				item.value == 'true' ? item.value = true : item.value = false;								
				tr.push($('<input>',{
					id:id,
					name:item.id,
					itemid:item.id,
					type:'checkbox',
					style:'margin-left: 40%;'
				}).outter());
				
				//tr.push(jQuery('<input>',{id:id + '_hidden',idField:item.id,name:item.id,value:item.value,type:'hidden'}).outter());
				return true;
			},
			select : function(item,id,tr){
				
				
				//tr.push(jQuery('<input>',{id:id,idField:item.id,value:item.value,type:'hidden'}).outter());
				if(!mtxx.tfMsg.value_){
					mtxx.tfMsg.value_ = new Object();
				}
				mtxx.tfMsg.value_[item.id] = {
						id : item.id,
						value : item.value
				};
				/*mtxx.tfMsg.value_.push({
					id : item.id,
					value : item.value
				});
				*/
				tr.push("<select itemid='" + item.id + "' hiddenId='"+ id +"' class='combobox selectCls'  name='" + item.id + "' value_='"+ item.value_ +"' ><option value='' >---请选择---</option>");
				jQuery.each(item.editor.options.data,function(i,o){
					if(item.value == o.value){
						selected = 'selected';
					}else
						selected = '';
					tr.push("<option value='"+o.value+"' "+ selected +"   >");
					tr.push(o.text);
					tr.push("</option>");
				});
				tr.push("</select>"); 
				return true;
			},
			multiselect : function(item,id,tr){
				var editor = item.editor,opt = {
						id : id,
						hiddenId:id,
						name : item.id,
						itemid:item.id,
						class:'combotree selectCls',
						'data-options':'width:120,multiple:true,data:[]'	
				};
				if(editor.options.data){
					if(item.name == 'linkageControlIn'){
						mtxx.tfMsg.linkageControlInId = id;
					} else {
						mtxx.tfMsg[item.name + "Id"] = id;
					}
					//tr.push(jQuery('<input>',{id:id,idField:item.id,type:'hidden',value:item.value}).outter());
					if(editor.options.data instanceof Array){
						var data = JSON.stringify(editor.options.data);
						opt["data-options"] = "multiple:true,data:" + data;
					} else {
						opt.multiChange = editor.data;
					}
					tr.push($("<select>",opt).outter());
					return true;
				}
			}
		};
	};
	
	this.initEvent = function(jDom){
		//数字文本框
		jDom.find('.easyui-numberbox').each(function(){
			var $this = $(this);
			var dataType = $this.attr('dataType'),precision = 0,$width;
			if(dataType == 'double')
				precision = 2;
			if($this.nextAll('select').length>0){
				$width = '45%';
			} else{
				$width = '90%';
			}
			$this.css({
				width : $width
			}).kendoNumericTextBox({
				decimals : 	precision
			});
		});
		
		//文本框
		jDom.find('.k-textbox').each(function(){
			var $this = $(this);
			var $width;
			if($this.nextAll('.linkbutton').length>0){
				$width = "78%";
			} else{
				$width = "90%";
			}
			$this.css({
				width : $width
			});
		});
		
		//checkbox
		jDom.find('input[type=checkbox]').each(function(){
			var $this = $(this),hidden = $('#' + $this.attr('id') + '_hidden');
			$this.attr('checked',hidden.val() == 'true').bind('click',function(){
				var checked = $(this).is(':checked');
				hidden.val(checked);
			});
		});
		
		//弹出框 小按钮
		jDom.find('.linkbutton').kendoButton({
			
		});
		
		//combo   自定义下拉
		jDom.find('.definedcombo').each(function(i,v){
			var $this = $(this);		
			$this.kendoDropDownList({
				change : function(e){
					var data = e.sender.dataItem();
					var itemid= "id_" + $this.attr('itemid');
					$('#' + itemid + '_hidden').val($('#' + itemid).val() + data.value);
				}
			});
		});
		
		//combobox
		try{
			jDom.find('.combobox').each(function(){
				var $this = $(this);
				$this.kendoDropDownList({
					change : function(e){
						appendChildren.call($this,e.sender.dataItem().value);
					}
				});
			});
		}catch(e){
			$.log(e);
		}
		
		//changeCls
		jDom.find('.changeCls').each(function(){
			var $this = $(this),$select = $this.closest('td').find("select.definedcombo"),
			itemid = $this.attr('itemid'),syn;
			$this.on('change',function(){
				if($select[0]){
					syn = $select.data('kendoDropDownList').value();
					$('#id_' + itemid + "_hidden").val(this.value + syn);
				}
			});
			/*
			$this.next('span').find('input:visible').on('change',function(){
				alert('changeCls');
				var combo = $this.parent().find('.definedcombo');
				var syn = combo.data("kendoDropDownList").value();
				var itemid = $this.attr('itemid');
				$('#id_' + itemid + "_hidden").val(this.value + syn);
			});
			*/
		});
		
		//combotree
		jDom.find('.combotree').each(function(index,item){
			var $this = $(this).css({
				width : '155'
			}),hiddenId = "id_"+$this.attr('name') + "_hidden";
			var hiddenObj = jQuery('#' + hiddenId);
			var multiChange = $this.attr('multiChange');
			var datas = new Array(),values = new Array();;
			if(multiChange == 'domId'){//联动控件(找到dom 里面控件)
				var doc = mtxx.jQueryFrame.contents();
				var crtltypes = doc.find('[crtltype]');
				if(crtltypes && crtltypes.length){
					jQuery.each(crtltypes,function(index,item){
						var id = jQuery(this).attr('id');
						var text = jQuery(this).attr('title') || jQuery(this).attr('name') || id;
						datas.push({id : id,text : text});
					});
				}
			}else if(multiChange == 'fields'){
			/**	
				var fieldsData = getFieldsData();
				if(fieldsData){
					$this.combotree({
						data : fieldsData,
						onShowPanel : function(){  //每次点击下拉时，都要重新获取数据字段信息
							if($this.combotree())
							var jsons = getHiddenJson(hiddenId);
							var values = new Array();
							fieldsData = getFieldsData();
							if(fieldsData && jsons){
								if(jQuery.isArray(fieldsData)){
									jQuery.each(fieldsData,function(i,v){
										if(jQuery.inArray(v.id,jsons) > -1){
											values.push(v.id);
										}
									});
								}
							}
							var tree = $this.combotree('tree'); // 得到树对象  
							var nodes = tree.tree('getChecked'); // 得到选择的节点
							if(nodes.length == 0){
								jQuery('#' + hiddenId).val('');
							}
							$this.combotree({
								data:fieldsData
							});
							
							if(jsons){
								$this.combotree('setValues',values);
							}
						}
					});
				}
				*/
			}else if('parameterIn' == multiChange){
				var pageId = mtxx.jQueryFrame.attr('id');//当前页面
				$.getJSON(mtxx.url + '/getFormRule',{name:pageId,pageId:pageId},function(data){
					if(data && data[0]){
						
						datas = $.parseJSON(data[0].pageParameters);
						
						var val = hiddenObj.val();
						if(val){
							$.each($.parseJSON(val),function(i,v){
								values.push(v.id);
							});
						}
					}
				});
			} else if(!multiChange){
				var str = $this.attr('data-options');
				if(str){
					try{
						datas = eval("({" + str + "})").data;
					}catch(e){
						
					}
				}
			}
			function getHiddenJson(id){
				var val = jQuery('#' + id).val();
				if(val){
					try{
						var jsons = eval(val),values = new Array();
						jQuery.each(jsons,function(i,n){
							values.push(n.name);
						});
						return values;
					}catch(ex){
						return val;
					}
				}
				return null;
			}
			if(!values.length){
				values = getHiddenJson(hiddenId) || [];
			}
			$this.kendoMultiSelect({
				dataTextField: "text",
				dataValueField: "id",
				dataSource: datas,
				change : function(e){
					var $kendo = e.sender,dataItems = $kendo.dataItems();
					if(dataItems && dataItems.length > 0){
						jQuery.each(dataItems,function(i,n){
							n.name = n.id;
							n.parent = null;
							n._events = null;
						});
					}
					hiddenObj.val(JSON.stringify(dataItems));
				},
				autoClose: false,
                tagMode: "single",
                value : values
			});
		});
		
		/**
		 * tr tooltip
		 */
		function initTr(jq){
			jq.each(function(i,tr){
				var $this = $(this);
				if($this.attr('isSort') == 'true'){
					$this.css({
						'font-size' : 13,
						'font-family' : 'Microsoft YaHei',
						'font-weight' : 'bold'
					});
				}
				var title = $this.attr('title');
				if(title){
					$this.kendoTooltip({
		                autoHide: true,
		                position: "left",
		                content: title
		            });
				}
			});
		}
		if(jDom.is('tr')){
			initTr(jDom);
		} else {
			initTr(jDom.find('tr'));
		}
		
	};
	this.init();
}

function getChildren(parentId){
	return mtxx.resultData[parentId];
}

function appendChildren(type){
	
	var $this = $(this);
	
	var hiddenId = "id_" + $this.attr('itemid') + "_hidden",hiddenObj = jQuery('#' + hiddenId),$tr = hiddenObj.closest('tr');
	var children = '.cls_' + $tr.attr('roleIndex'),pad = $tr.attr('pad'),$children = $(children);
	if($children && $children[0]){
		$children.remove();
	}
	hiddenObj.val(type);
	var value_ = $this.attr('value_'),cre = function(){
		if(type){
			var obj = mtxx.hideRows[type];
			if(obj){
				$.each(obj,function(key,data){
					K.createPropertyDocument(data,function(tr){
						$tr.after(tr.join(''));
					},pad,$tr.attr('class'));
				});
				K.initEvent($(children));
				//隐藏的内容
				K.showHideRows(mtxx.tfMsg.value_);
			}
		}
	};
	switch(value_){
		case 'buttonType':
		case 'jumpType':
			cre();
			break;
		default:
			break;
	}

}

/**
 * 点击显示分类
 * @param img
 */
function openChildren(img){
	if(img){
		var $img = $(img),$tr = $img.parent().parent();
		var parentId = $tr.attr('roleIndex'),data = getChildren(parentId);
		if(data){							
			var children = '.cls_' + parentId;
			var opertator = new operateChildren($(children),parentId);
			if(!$tr.data('open')){
				$img.attr({
					src : mtxx.contextPath + '/webfile/icons/close.png'
				});
				if(!$tr.data('isShow')){
					var pad = $tr.attr('pad') * 1;
					K.createPropertyDocument(data,function(tr){
						$tr.after(tr.join(''));
					},(pad  + 1),$tr.attr('class'));
					K.initEvent($(children));
					//隐藏的内容
					K.showHideRows(mtxx.tfMsg.value_);
					$tr.data('isShow',true);
				}else{
					opertator.show();
				}
			}else{
				$img.attr({
					src : mtxx.contextPath + '/webfile/icons/open.png'
				});
				opertator.hide();
			}
			$tr.data('open',!$tr.data('open'));
		}
	}
}

/**
 * 显示隐藏分类
 */
function operateChildren(jq,parentId){
	this.jq = jq,this.parentId = parentId;
	this.hide = function(){
		$('.cls_' + parentId).each(function(){
			$(this).hide();
		});
	};
	
	this.show = function(){
		var cls = null;
		$('.cls_' + parentId).each(function(){
			if($(this).attr('isSort') == 'true' && !$(this).data('open')){
				cls = 'cls_'+$(this).attr('roleIndex');
			}
			if(!$(this).hasClass(cls)){
				$(this).show();
			}
		});
	};
}




function $tabstrip2Append(tabs){
	var $tabstrip2 = mtxx["tabstrip-2"].data('kendoTabStrip');
	$tabstrip2.remove("li");
	$tabstrip2.append(tabs).select(0);
}

/**
* 获取字典信息
*/
mtxx.getDictByCode = function(code,fn){
	$.ajax({
		url : mtxx.url + '/getDictByCode',
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

function selectObj(obj){
	
	var $this = jQuery(obj),dialogType = $this.attr('dialogType');
	
	if(!dialogType)
		return false;
	if(!(dialogType in selectObjFuncs)){
		dialogType = 'default';
	} 

	selectObjFuncs[dialogType].call($this,{
		nameElementId : $this.attr('nameElementId'),
		objElementId : $this.attr('objElementId'),
		objJson : jQuery('#' + $this.attr('objElementId')).val()
	});
	
}

function saveSourceSetFunc(data){
	if(data){
		
	} else {
		var $linkageControlIn = $("#" + mtxx.tfMsg.linkageControlInId + "_hidden");
		if($linkageControlIn[0]){
			return $linkageControlIn.val();
		}
	}
}

var selectObjFuncs = {
		
		/**
		 * 保存设置
		 * @param e
		 */
		'saveSourceSet' : function(e){
			var url = contextPath+"/mx/form/defined/ex/createTable?"+$.param({
				fn : 'saveSourceSetFunc' , //回调函数
				targetId : e.objElementId,//隐藏域的ID
			}) ;
			$.layer({
				type: 2,
				maxmin: true,
				shadeClose: true,
				title: "保存设置",
				closeBtn: [0, true],
				shade: [0, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['',''],
				fadeIn: 100,
				area: ['980px', '500px'],
				iframe: {src: url}
			});
		},
		
		'selectField': function(e){
			var tableObjElementId = mtxx.tfMsg.tableObjElementId;
			var tableJson = '';
			if(tableObjElementId){
				tableJson = jQuery('#' + tableObjElementId).val();
			}
			selectField('','',e.nameElementId,e.objElementId,tableJson,e.objJson);
		},
			
		'selectTreeField': function(e){//树数据字段 弹出窗口
			var tableObjElementId = mtxx.tfMsg.dataSourceSetId;
			var tableJson = '';
			if(tableObjElementId){
				tableJson = jQuery('#' + tableObjElementId).val();
			}
			selectTreeField('','',e.nameElementId,e.objElementId,tableJson,e.objJson,'',mtxx.saveData.name);
		}, 
			
		'selectTable': function(e){
			selectTable('','',e.nameElementId,e.objElementId,e.objJson);
		},
			
		'dataSourceSet' : function(e){
			selectTableAndFields(e.objElementId,e.nameElementId,mtxx.saveData.name);
//				selectTableAndFields(objElementId,nameElementId,"");
			//selectTableAndFields(objElementId,"tableName","fieldName");
		},
			
			
			
		'columnTemplate': function(e){
			//alert(tfMsg.fieldObjElementId);
			modifyTableHander(e.nameElementId,e.objElementId,mtxx.tfMsg.dataSourceSetId);
			//modifyTableHander("tableHander","headerObjElementId",null,"fieldObjElementId");
		},
			
		'updateField' : function(e){
			var fieldJson = '';
			if(mtxx.tfMsg.fieldObjElementId){
				fieldJson = jQuery('#' + mtxx.tfMsg.fieldObjElementId).val();
				var nameVal = jQuery('#' + mtxx.tfMsg.filedNameElementId).val();
				jQuery('#' + e.nameElementId).val(nameVal);
			}
			modifyField('',mtxx.tfMsg.fieldObjElementId,fieldJson);
		},
				
		
		'combineFields': function(e){
			selectObjFuncs.defaultOrderParameters.call(this,e);
		},
		'countFields':function(e){
			selectObjFuncs.defaultOrderParameters.call(this,e);
		},
		'pageParameters':function(e){
			selectObjFuncs.defaultOrderParameters.call(this,e);
		},
		'defaultOrderParameters': function(e){
			alert('defaultOrderParameters 未完成');
			//operateFields(obj,dialogType);
		},
		
		inParamDefined : function(e){
			var url = contextPath+"/mx/form/defined/param/paramDefined?"+$.param({
				nameElementId : e.nameElementId , //展示的ID
				objelementId : e.objElementId,//隐藏域的ID
			}) ;
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
		},
			
		inParameters : function(e){
			var url = contextPath+"/mx/form/defined/param/inparam?"+$.param({
				nameElementId : e.nameElementId , //展示的ID
				objelementId : e.objElementId,//隐藏域的ID
				dataSourceSetId :mtxx.tfMsg.dataSourceSetId ,
				inParamDefinedId : mtxx.tfMsg.inParamDefinedId
			}) ;
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
		},
			
	      selectPage : function(e){ // 选择关联页面
	    	    //return obj {name : '',id : '',url : '',node : obj}
	    		var url = contextPath+"/mx/form/defined/ex/selectPage?"+$.param({
	    			fn : 'selectPageBack' ,
	    			targetId : e.objElementId,
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
	    			area: ['980px', '500px'],
	    	        iframe: {src: url}
	    		});
	      },
		
	      tableRelationDefined :  function(e){
	    	  var url = contextPath + '/mx/form/defined/ex/tableRelationDefined';
	    	  window.open(url);
	      },
			
		  selectTabstrip : function(e){
			   var link = contextPath + '/mx/form/defined/ex/tabstrip_datasource?' + $.param({
				   id : e.objElementId,
				   name : e.nameElementId
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
		 },
		 
	     paraType : function(e){
	    	var link = contextPath + '/mx/form/defined/param/outInParam?' + $.param({
	    		pageId : mtxx.jQueryFrame.attr('id'),
	    		eleId : e.objElementId,
	    		eleType : mtxx.saveData.name,
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
	     },
	     
		 datatextfield: function(e){
			 selectObjFuncs.datavaluefield.call(this,e);
		 },
		 datavaluefield: function(e){
			 var $dataSourceSet = $("#" + mtxx.tfMsg.dataSourceSetId),val = $dataSourceSet.val();
			 if(!val){
				 alert('请先配置数据源!');
				 return false;
			 } else {
				 var data = JSON.parse(val);
				 if(data[0]){	
					 var columns = data[0].columns;
					 
					 var obj = $("#" + e.objElementId),json = [{}];
					 
					 if(obj.val()){
						 json = JSON.parse(obj.val());
					 }
					 
					 $("#selectFieldComb").kendoDropDownList({
						 dataSource : columns,
						 dataTextField: "title",
						 dataValueField: "columnName",
						 change : function(s){
							 var data = s.sender.dataItem();
							 data.name = data.title;
							 $("#" + e.nameElementId).val(data.name);
							 obj.val(JSON.stringify([data]));
						 },
						 value : json[0].columnName
					 });
					 
					 $("#selectField").show().dialog({
						 title : '请选择字段',
						 buttons : [
				            {
				            	text : '确定',
				            	click : function(e){
				            		$(e).dialog('close');
				            	}
				            },
				            {
				            	text : '取消',
				            	click : function(e){
				            		$(e).dialog('close');
				            	}
				            }
						]
					 });
					 
				 }
			 }
		 },
		 
		selectIcon: function(e){
			var link = contextPath + "/mx/image/choose?" + $.param({
				fn : 'selectIconFunc',
				elementId : e.objElementId
			});
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
		},
		
		'default' : function(e){
			var link = mtxx.contextPath + '/mx/form/defined/ex/' + e.dialogType;
			$.layer({
				type: 2,
				maxmin: true,
				shadeClose: false,
				title: e.dialogType,
				closeBtn: [0, true],
				shade: [0, '#000'],
				border: [10, 0.3, '#000'],
				offset: ['',''],
				fadeIn: 100,
				area: ['600px', '500px'],
		        iframe: {src: link}
			});
		}
};

//选择图片 (回调函数)
function selectIconFunc(src){
	$('#id_' + $(this).attr('itemid')).attr({
		src : contextPath + src
	});
}

//选择关联页面(回调函数)
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

/**
 * 查看详细信息
 * @param o
 */
function showDetailDialog(o){
	if(!mtxx.showDetailDialog){
		mtxx.showDetailDialog = $("<div>").dialog({
			title : '查看详细信息',
			width : 400,
			height : 150,
			buttons : [
			     {
			    	 text : '确定',
			    	 click : function(e){
			    		 $(e).dialog('close');
			    	 }
			     }
			]
		});
	} else {
		mtxx.showDetailDialog.dialog('open');
	}
	mtxx.showDetailDialog.text(o.value);
}

/**
 *  type[{
 *  	value : 文本框值
 *  	text : 单元格文本
 *  	row : 一行
 *  }]
 *  输入输出关系配置 start  (以下代码，有待完善...)
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
		url : mtxx.url + '/getDataSourceSet',
		data : {pageId : mtxx.saveData.pageId, name : id},
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
		var saveData = mtxx.saveData,tfMsg = mtxx.tfMsg;
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
		get = function(drop,isIn){
			var jq = $("#id_" + drop.attr('itemid') + "_hidden");
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
/**
 * 输入输出关系配置 end
 */
