<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>表单定义</title>
<%@ include file="/WEB-INF/views/inc/init_easyui.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<%@ include file="/WEB-INF/views/isdp/table/select_js.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/glaf-base.js"></script>
<style type="text/css"> 
	.tdCls{
		background-color:#FCFCFC;
		align:left;
		height:16px;
	}
	.selectCls{
		width : 47%;
	}
	.textCls{
		width : 80%;
	}
</style>
<script type="text/javascript">
	var zTree,tabs,jQueryMenu,jQueryFrame,selectedNode,url = "${contextPath}/mx/form/defined";	
	var setting = {
		async : {
				enable : true,
				url : url + '/getFormPageTree',
				autoParam : [ "id" ]
		},
		view: {
			dblClickExpand: false,
			showLine: true,
			selectedMulti: false,
			expandSpeed : 'fast'    //'slow'
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
			onCreate : zTreeOnCreate
		}
	};

	var dictSort = {
		Configuration : '属性',
		Fields : '字段',
		Methods : '方法',
		Events : '事件',
		buttonConf : '按钮属性'
	};
	
	jQuery(function() {

		tabs = jQuery('#tabs');	
		
		zTree = jQuery.fn.zTree.init(jQuery("#tree"), setting);				
					
		jQueryMenu = jQuery('#mm');
		
		expandAll();
		
	});
	
	function expandAll(){
		setTimeout(function(){zTree.expandAll(true);},500);
	}
	
	//左键事件
	function ztreeBeforeClick(treeId, treeNode, clickFlag) {
		if (!treeNode.isParent){
			openTab("<a style='display:none' >" + treeNode.parentId + "</a>" + treeNode.name, '${contextPath}',treeNode.id);
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
			jQuery.post('${contextPath}/mx/form/page/saveFormPage',{id:treeNode.id,name:treeNode.name},function(data){});
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
			jQuery.post('${contextPath}/mx/form/page/delete',{ids : ids.join(',')},function(){
				zTree.reAsyncChildNodes(null, "refresh");expandAll();
			});
		}
	
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
		
		jQuery('#button_tool').empty();
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
				
	}
	
	//给每个控件[crtltype] 注册点击事件
	function populateJs(jQueryFrame){
		
		var doc = jQueryFrame.contents();
		
		var crtltypes = doc.find('[crtltype]');
	
		if(crtltypes && crtltypes.length){
			var $this;
			var pageId = jQueryFrame.attr("id");
			
			doc.find("body").bind('click',function(index,item){
				jQuery('#button_tool').html('');
				saveData = {};
				saveData.name = pageId;
				saveData.pageId = pageId;
				saveData.dataRole = 'page';
				showFormCompoentProperties({
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
					jQuery('#button_tool').html('');
					saveData = {};
					saveData.name = id;
					saveData.pageId = pageId;
					saveData.dataRole = dataRole;
					showFormCompoentProperties({
						id : id,
						pageId : pageId,
						dataRole : dataRole
					},$this);
					
					jQuery.each(crtltypes,function(){
						if($(this).attr('id') === id){
							$this.css({
								background : '#FFB90F'
							});
						}else{
							$(this).css({
								background : ''
							});
						}
					});
					return false;
				});
			});
		}
	}
	
	var rows;//数据库保存数据
	var hideRows;
	//获取、显示配置属性
	function showFormCompoentProperties(options,jq){
		tfMsg = {};
		jQuery.ajax({
			url : url +  '/getFormCompoentProperties',
			type : 'post',
			data : options,
			async : false,
			dataType : 'json',
			success : function(data){
				if(data){
					saveData.formRuleId = data.formRuleId;
					var obj = new Object();
					rows = new Object();
					if(data.rows.length > 0){
						data.rows.sort(function(a,b){
							return b.listNo - a.listNo;//根据容器属性排序(倒序)
						});
					}
					hideRows = new Object();
					
					function populateObj(item,collection){//按类型分组
						if(!(item.type in collection)){
							collection[item.type] = new Array();
						}
						collection[item.type].push(item);
					}
					
					jQuery.each(data.rows,function(index,item){
						var pValue = item.pValue;
						if(pValue){
							jQuery.each(pValue.split(','),function(i,v){
								if(!(v in hideRows)){
									hideRows[v] = new Object();
								}
								populateObj(item,hideRows[v]);
							});
						}else{
							populateObj(item,obj);
							rows[item.id] = item.value;
						}
					});
					
					createPropertyDom(obj);
				}
				
				function createPropertyDom(obj){
					var tr,id,editor;
					for(var key in obj){
						tr = ['<tr class="trCls" ><td style="background-color:#FFEC8B;cursor:hand;" colspan=2 >',dictSort[key],'</td></tr>'];
						jQuery.each(obj[key],function(index,item){
							item.value ? item.value = item.value : item.value = '';
							
							saveData.componentId = item.componentId;
							id = 'id_' + item.id;
							tr.push("<tr roleIndex='"+ item.id +"' ><td class='tdCls' >");
							tr.push(item.title);
							tr.push('</td><td class="tdCls" style="width:60%;">');
							editor = item.editor;
							var jdom;
							var jdomOption;
							console.log(item.dataType);
							if(editor){
								switch(editor.type){
									case 'text':
										tr.push(jQuery('<input>',{id:id,idField:item.id,name:item.id,class:'textCls',value:item.value}).outter());
										break;
									case 'select':
										tr.push(jQuery('<input>',{id:id,idField:item.id,value:item.value,type:'hidden'}).outter());
										
										if(!tfMsg.value_){
											tfMsg.value_ = new Object();
										}
										
										tfMsg.value_[item.value] = item.value;
										
										tr.push("<select hiddenId='"+ id +"' class='combobox selectCls'  name='" + item.id + "' value_='"+ item.value_ +"' ><option value='' >---请选择---</option>");
										var selected;
										jQuery.each(editor.options.data,function(i,o){											
											if(item.value == o.value){
												selected = 'selected';
											}else
												selected = '';
											tr.push("<option value='"+o.value+"' "+ selected +"   >");
											tr.push(o.text);
											tr.push("</option>");
										});
										tr.push("</select>"); 
										break;
									case 'multiselect':
										if(editor.options.data){
											if(item.name == 'linkageControlIn'){
												tfMsg.linkageControlInId = id;
											}
											tr.push(jQuery('<input>',{id:id,idField:item.id,type:'hidden',value:item.value}).outter());
											if(editor.options.data instanceof Array){
												var data = JSON.stringify(editor.options.data);
												jdom = jQuery('<div>',{hiddenId:id,class:'combotree selectCls','data-options':"multiple:true,data:" + data + ""});
											}else{
												jdom = jQuery('<div>',{hiddenId:id,class:'combotree selectCls',multiChange:editor.data,'data-options':'width:120,multiple:true,data:[]'});
											}
											tr.push(jdom.outter());
										}
										break;	
									case 'checkbox':
										item.value ? item.value = item.value : item.value = false;
										tr.push(jQuery('<input>',{id:id,idField:item.id,name:item.id,value:item.value,type:'checkbox',style:'margin-left: 40%;'}).outter());
										break;
									case 'dialog':
										var value = item.value;
										var hiddenId = id + '_hidden';
										var hiddenValue = '';
										var json = value;
										try{
											if(!(value instanceof Array))
												json = eval(value);
										}catch(e){
											
										}
										if(json instanceof Array){
											hiddenValue = JSON.stringify(json);
											value = '';
											jQuery.each(json,function(i,v){
												value += ',' + v.name;
											});
											value = value.substring(1);
										}
										if(editor.data){
											if(editor.data == 'selectTable'){
												tfMsg.tableObjElementId == null ? tfMsg.tableObjElementId = hiddenId : "";
											} else if(editor.data == 'selectField'){
												tfMsg.fieldObjElementId == null ?  tfMsg.fieldObjElementId = hiddenId : '';
												tfMsg.filedNameElementId == null ? tfMsg.filedNameElementId = id : '';
											}else if(editor.data == 'selectTreeField'){
												tfMsg.fieldObjElementId == null ? tfMsg.fieldObjElementId = hiddenId : '';
											}else if(editor.data == 'selectPage'){
												tfMsg.pageElementId == null ? tfMsg.pageElementId = hiddenId : '';
											}
										}
										
										if(editor.data && editor.data == 'selectIcon'){//图标
											var src = value ? value : '/images/FIX_edit.png';
											hiddenValue = src;
											jdom = jQuery('<img>',{id:id,readonly:true,name:item.id,src:'${contextPath}' + src,alt:'图片',style : 'margin-right:40px;margin-top:5px;margin-left:45px;'});
											tr.push(jdom.outter());//显示值
										}else{
											jdom = jQuery('<input>',{id:id,class:'easyui-validatebox textbox textCls',readonly:true,name:item.id,type:'text',value:value,ondblclick:'showDetailDialog(this)'});
											tr.push(jdom.outter());//显示值
										}
										
										jdom = jQuery('<input>',{id:hiddenId,idField:item.id,name:item.id,type:'hidden',value:hiddenValue});
										tr.push(jdom.outter());//隐藏值(保存)
										
										jdom = jQuery('<a>',{'class' : 'linkbutton',value : '..',dialogType : editor.data,nameElementId : id,objElementId : hiddenId,onclick : 'selectObj(this)'}).html('..');
										tr.push(jdom.outter());
										break;
									default :
										jdomOption = {id:id,idField:item.id,name:item.id,type:'text','class':'textCls easyui-validatebox textbox',value:item.value};
										if(item.dataType == 'int' || item.dataType == 'double'){
											jdomOption['class'] = 'easyui-numberbox';
											jdomOption.dataType = item.dataType;
										}
										if(item.name == 'id'){
											jdomOption.readonly = 'readonly=true';
											jdomOption.value = item.value ? item.value : options.id;
										}else if(item.name == 'html'){
											jdomOption.value = item.value ? item.value : jq.text();
										}
										jdom = jQuery('<input>', jdomOption).outter();
										tr.push(jdom);
										break;
								}
							}else{
								tr.push(item.value);
							}
							tr.push('</td></tr>');
						});
						jQuery('#button_tool').append(tr.join(''));
					}
				}
				
				if(tfMsg.value_){
					showHide(tfMsg.value_);
				}
				
				//隐藏的内容
				function showHide(value_){
					for(var key in value_){
						var hide = hideRows[key];
						createPropertyDom(hide);
						for(var k in tfMsg.value_){//隐藏内部的内容
							if(k == key){
								delete tfMsg.value_[k];
							}else{
								showHide(tfMsg.value_);
							}
						}
					}
				}
				
				
				initEvent(jQuery('#button_tool'));
				
				function initEvent(jDom){
				
					jDom.find('.easyui-numberbox').each(function(){
						var dataType = $(this).attr('dataType'),precision = 0;
						if(dataType == 'double')
							precision = 2;
						$(this).numberbox({
							width : '80%',
							precision : precision
						});
					});
				
					//checkbox 
					jDom.find('input[type=checkbox]').each(function(){
						jQuery(this).attr('checked',(jQuery(this).val() == 'true')).bind('click',function(){
							jQuery(this).val(jQuery(this).is(':checked'));
						});
					});
					
					//combotree
					jDom.find('.combotree').each(function(index,item){
						var hiddenId = jQuery(item).attr('hiddenId');
						var hiddenObj = jQuery('#' + hiddenId);
						
						var multiChange = jQuery(item).attr('multiChange');
						if(multiChange == 'domId'){//联动控件(找到dom 里面控件)
							var doc = jQueryFrame.contents();
							var crtltypes = doc.find('[crtltype]');
							if(crtltypes && crtltypes.length){
								var jsons = new Array();
								jQuery.each(crtltypes,function(index,item){
									var id = jQuery(this).attr('id');
									var text = jQuery(this).attr('title') || id;
									if(id != options.id)
										jsons.push({id : id,text : text});
								});
								jQuery(item).combotree({data : jsons});
							}
						}else if(multiChange == 'fields'){
							var fieldsData = getFieldsData();
							if(fieldsData){
								jQuery(item).combotree({
									data : fieldsData,
									onShowPanel : function(){  //每次点击下拉时，都要重新获取数据字段信息
										if(jQuery(item).combotree())
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
										var tree = jQuery(item).combotree('tree'); // 得到树对象  
										var nodes = tree.tree('getChecked'); // 得到选择的节点
										if(nodes.length == 0){
											jQuery('#' + hiddenId).val('');
										}
										jQuery(item).combotree({
											data:fieldsData
										});
										
										if(jsons){
											jQuery(item).combotree('setValues',values);
										}
									}
								});
							}
						}else if('parameterIn' == multiChange){
							var pageId = jQueryFrame.attr('id');//当前页面
							$.getJSON(url + '/getFormRule',{name:pageId,pageId:pageId},function(data){
								if(data && data[0].pageParameters){
									jQuery(item).combotree({
										data : jQuery.parseJSON(data[0].pageParameters)
									});
									
									var val = hiddenObj.val();
									if(val){
										var values = new Array();
										$.each($.parseJSON(val),function(i,v){
											values.push(v.id);
										});
										jQuery(item).combotree('setValues',values);
									}
								}
							});
						}
						
						jQuery(item).combotree({
							onCheck : function(node){
								var tree = jQuery(item).combotree('tree'); // 得到树对象  
								var nodes = tree.tree('getChecked') || ''; // 得到选择的节点
								if(nodes && nodes.length > 0){
									jQuery.each(nodes,function(i,n){
										n.name = n.id;
										n.target = null;
									});
								}
								hiddenObj.val(JSON.stringify(nodes));
							}
						});
						
						var jsons = getHiddenJson(hiddenId);
						if(jsons){
							jQuery(item).combotree('setValues',jsons);
						}
						
						function getHiddenJson(id){
							var val = jQuery('#' + id).val();
							if(val){
								var jsons;
								try{
									jsons = eval(val);
								}catch(ex){
									
								}
								if(jsons){
									var datas = new Array();
									jQuery.each(jsons,function(i,n){
										datas.push(n.name);
									});
									return datas;
								}
							}
							return null;
						}
						
					});
					
					//combobox
					try{
						jDom.find('.combobox').combobox({
							onSelect : function(data){
								var hiddenId = jQuery(this).attr('hiddenId');
								var hiddenObj = jQuery('#' + hiddenId);
								hiddenObj.val(data.value);
								
								var value_ = jQuery(this).attr('value_'),$tr,$nextAll,cre = function($tr){
									$nextAll = $tr.nextAll();
									if($nextAll && $nextAll[0]){
										$nextAll.remove();
									}
									if(data.value){
										var obj = hideRows[data.value];
										createPropertyDom(obj);
										initEvent($tr.nextAll());
									}
								};
								
								switch(value_){
									case 'buttonType':
										$tr = jQuery('#button_tool [roleIndex=123]');
										cre($tr);
									break;
									
									case 'jumpType':
										var $tr = hiddenObj.parent().parent();
										cre($tr);
									break;
									default:break;
								}
								
								
								
							},
							editable:false
						});
					}catch(e){
						console.log(e);
					}
					
					jDom.find('.linkbutton').linkbutton();
					
					//收缩配置栏
					jDom.find('.trCls').each(function(index,item){
						jQuery(this).bind('click',function(){
							jQuery(this).data('isClose',!jQuery(this).data('isClose'));
							var isClose = !jQuery(this).data('isClose');
							jQuery(this).nextAll('tr').each(function(){
								if(jQuery(this).hasClass('trCls')){
									return false;
								}else{
									if(isClose){
										jQuery(this).show();
									}else{
										jQuery(this).hide();
									}
								}
							});
						});
					});
				}
			}
		});
	}
	
	jQuery.fn.outter = function(){
		return jQuery('<div>').append(jQuery(this)).html();
	};
	
	function getFieldsData(){
		var fieldObjElementId = tfMsg.fieldObjElementId,fieldJson;
		if(fieldObjElementId){
			fieldJson = jQuery('#' + fieldObjElementId).val();
			if(fieldJson){
				var fieldsData = eval(fieldJson);
				if(jQuery.isArray(fieldsData)){
					jQuery.each(fieldsData,function(index,item){
						item.id = item.ColumnName;
						item.text = item.name;
					});
				}
				return fieldsData;
			}
		}
		return null;
	}
	
	//弹出窗口选择表信息
	var tfMsg = {};
	function selectObj(obj){
		
		var $this = jQuery(obj);
		
		var nameElementId = $this.attr('nameElementId'),
			objElementId = $this.attr('objElementId'),
			dialogType = $this.attr('dialogType'),
			objJson = jQuery('#' + objElementId).val();
		
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
				case 'selectTreeField':
					var tableObjElementId = tfMsg.tableObjElementId;
					var tableJson = '';
					if(tableObjElementId){
						tableJson = jQuery('#' + tableObjElementId).val();
					}
					selectTreeField('','',nameElementId,objElementId,tableJson,objJson);
					break;
				case  'selectTable':
					selectTable('','',nameElementId,objElementId,objJson);
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
				case 'outParameters':
					operateFields(obj,dialogType);
					break;
				case 'selectIcon':
					openWindow("/glaf/mx/image/choose?elementId="+ objElementId, self, 50, 50, 1098, 520);
					break;
				case 'selectPage' : 
					selectPage(objElementId,nameElementId);
					break;
				default :
					break;
			}
		}
	}
	
	//和并列 & 数据列统计
	var operateFieldsDialog;
	var operates = {
		combineFields : {title:'合并列',datas:['合并字段名称 ',"<input class='easyui-validatebox textbox' id='combineField'/>",'所选字段名称',"<div id='combineFields'></div>"]},
		countFields : {title:'数据列统计',datas:['字段名',"<div id='countField' ></div>",'统计函数',"<div id='countFields'></div>"]},
		outParameters : {title:'输出参数对应',datas:['参数名',"<div id='outParametersName' ></div>",'对应字段',"<div id='outParametersField'></div>"]},
		pageParameters : {title:'输入参数',datas:['参数名',"<input class='textbox' id='pageParameters' ></input>",'','']}
	};
	function operateFields(obj,dialogType){
		
		var $this = jQuery(obj),fieldObjElementId = tfMsg.fieldObjElementId,fieldsName,
		fieldJson = '',fieldsData = new Array(),nameElementId = $this.attr('nameElementId'),
		objElementId = $this.attr('objElementId');
		
		var operator = operates[dialogType];
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
			if(operator){
				createDom(operator);
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
						url : '${contextPath}/rs/dictory/jsonArray/function'
					});
				}
			}
		}
		
		if(dialogType == 'pageParameters'){
			createDom(operator);
		}else if(dialogType == 'outParameters'){
			if(tfMsg.linkageControlInId || saveData.name){
			//	tfMsg.pageElementId = tfMsg.pageElementId || 
				createDom(operator);
				var pageData = $('#' + tfMsg.pageElementId).val() || JSON.stringify([{id:jQueryFrame.attr('id')}]),linkageControlInData = $('#' + tfMsg.linkageControlInId).val() || JSON.stringify([{id:saveData.name}]);
				if(pageData){
					var pageJSON = jQuery.parseJSON(pageData);
					var pageId = pageJSON[0].id;
					if(pageId){
						createDom(operator);
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
									if(v.table){
										var tables = jQuery.parseJSON(v.table);
										jQuery.each(tables,function(i,o){
											$.ajax({
												contentType: "application/json",
						                        url: "${pageContext.request.contextPath}/rs/isdp/cellDataField/selectFieldByTableId",
						                        type: "POST",
						                        dataType:"JSON",
						                        data:JSON.stringify({
						                        	tableId:o.TableId,
						                			type:99,
						                			tableName:o.TableName
						                        }),
						                        async : false,
						                        success : function(data){
						                        	if(data){
						                        		$.each(data.rows,function(i,item){
						                        			if(!tableTmp[item.dname]){
						                        				var v = new Object();
						                        				v.name = item.fname;
						                        				v.ColumnName = item.dname;
					                        					fields.push(v);
					                        					tableTmp[item.dname] = item;
					                        				}
						                        		});
						                        	}
						                        }
											});
										});
									}
								});
								
								jQuery('#outParametersField').combobox({
									editable:false,
									textField : 'name',
									valueField : 'ColumnName',
									onSelect: function(selected){   
							            jQuery(this).data('selected',selected); 
							        },
									data : fields
								});
							}
						});
					}
				}
			}
		}
		
		
		function createDom(operator){
			jQuery.each(operator.datas,function(index,item){
				jQuery('#operator' + index ).html(item);
			});
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
	
	//选择关联页面
	var selectPageDiv;
	function selectPage(objElementId,nameElementId){
		
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
	}
	
	function showDetailDialog(obj){
		
		jQuery('#detailDialog').dialog({
			modal : true,
			closed : false,
			content : obj.value
		});
	}
	
	//判断数据有没有修改
	function isModified(){
		for(var key in rows){
			var idField = jQuery("#button_tool [idField="+ key +"]");
			if(rows[key] != idField.val())
				return true;
		}
		return false;
	}
	
	//保存配置
	var saveData = new Object();
	function saveFunc(){
		
		var idField = jQuery('#button_tool [idField]');
		saveData.id = saveData.formRuleId;
		if(idField.length){
			var json = new Object();
			jQuery.each(idField,function(index,item){
				json[jQuery(this).attr('idField')] = jQuery(this).val();
			});
			saveData.value = JSON.stringify(json);
			jQueryFrame.data('save',{id:saveData.name,isSave:true});
			console.log(saveData);
		 	jQuery.post(url + '/saveOrUpdateFormRule',saveData,function(data){
				if(data){
					saveData.formRuleId = data.id;
					alert('操作成功!');
				}
			},'json');
		}
	}
	
	function showFunc(){
		if(jQueryFrame.attr('id')){
			var url = "${contextPath}/mx/form/page/viewPage?" + jQuery.param({
				isPublish : true,
				id : jQueryFrame.attr('id')
			});
			window.open(url);
		}
	}
	
	function publishFunc(){
	}
	
	//新建分类
	function createSort(){
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
							jQuery.post('${contextPath}/mx/form/page/saveFormPage',{name:sortName,formHtml:sortName},function(rst){
								if(rst){
									alert('操作成功!');
									jQuery('#sortDialog').dialog('close');
									zTree.reAsyncChildNodes(null, "refresh");expandAll();
								}
							});
						}
					}
				}
			]
		});
	}
	
	
</script>
</head>
<body class="easyui-layout">
	
	<div id='north' data-options="region:'north',title:'',split:true"
		style="height:60px;">
		<div style='background: url("${contextPath}/themes/default/images/top_bar_bg.jpg") repeat-x center 50% / 83px 100% rgb(127, 153, 190); height: 53px; color: rgb(255, 255, 255); line-height: 52px; overflow: hidden; font-family: Verdana, 微软雅黑,黑体;'>
			<span class="" style="padding-left: 10px; font-size: 24px;">
				华闽通达自定义平台(JAVA)
			</span>
			<div style="float:right;">
				<a class='easyui-linkbutton' onclick="javascript:saveFunc();" >保  存  配  置</a>
				<a style="display:none;" class='easyui-linkbutton' onclick="publishFunc()" >发  布</a>
				<a class='easyui-linkbutton' onclick="showFunc()" >预  览</a>
			</div>
		</div>		
	</div>

	<div id='west' style="width:230px;" data-options="region:'west',split:true,title:''" >
		<div>
			<div style='margin: 5px;' >
				<a class='easyui-linkbutton' onclick="javascript:createSort()" >增加分类</a>
			</div>
		</div>
		<div class="ztree" id="tree"></div>
	</div>
	<div id='east' data-options="region:'east',split:true,title:''" style="width:260px;">
		<form id="dataForm">
			<table id="button_tool" width="98%" border="0" cellpadding="5" cellspacing="1" bgcolor="BCD2EE" align="center"></table>
		</form>
	</div>
	<div id='center' data-options="region:'center',split:true">
		<div class="easyui-tabs" data-options="fit:true,border:false" id="tabs">
			<div title="首页">
				<div align="center" style="padding-top: 100px;">
				</div>
			</div>
		</div>
	</div>
	<div id="mm" class="easyui-menu" style="width:120px;">  		
	    <div onclick="zTree.setting.callback.onCreate.call();" >增加</div>
		<div onclick="zTree.editName(selectedNode);" >修改名称</div>
	    <div onclick="zTree.setting.callback.onRemove.call();" >删除</div>
	</div> 
	
	<div id='detailDialog' class="easyui-dialog" style="width:400px;height:200px;" data-options="closed:true,title:'详细信息'" >
		
	</div>
	<div id='sortDialog' class="easyui-dialog" style="width:300px;height:120px;" data-options="closed:true,title:'增加分类'" >
		<div style="padding:10px;">
			增加分类   : <input id="sortId" type='text' >
		</div>
	</div>
	
	<div id='operateFieldsDialog' class="easyui-dialog" style="width:300px;height:400px;" data-options="closed:true,title:''" >
		<div style="padding:10px;">
			<table width="98%" border="0" cellpadding="5" cellspacing="1" bgcolor="BCD2EE" align="center">
				<tr>
					<td class="tdCls" id='operator0'>
					</td>
					<td class="tdCls" id='operator1'>
					</td>
				</tr>
				<tr>
					<td class="tdCls" id='operator2'>
					</td>
					<td class="tdCls" id='operator3'>
					</td>
				</tr>
				<tr>
					<td colspan="2" class='tdCls' >
						<div style="float:right;">
							<a class='easyui-linkbutton' data-options="iconCls:'icon-add'" id="add-operator" >添 加</a>
							<a class='easyui-linkbutton' data-options="iconCls:'icon-remove'" onclick='deleteCombineList()' >删 除</a>
						</div>
						<script type="text/javascript">
						
							function outParametersAddFunc(){
								var outParametersField = jQuery('#outParametersField'),
									outParametersName = jQuery('#outParametersName'),
									fieldsName,fieldsValue;
								if(outParametersName.combobox('getValue')){
									var outParametersFieldData = outParametersField.data('selected');
									if(outParametersField.combobox('getValue')){
										var outParametersNameData = outParametersName.data('selected');
										var operateList = jQuery('#operateList').data('operateList');
										if(!operateList)
											operateList = new Array();
										fieldsName = outParametersNameData.name + "=" + outParametersFieldData.name;
										operateList.push({
											name : outParametersFieldData.name,
											fieldsName : fieldsName,
											column : outParametersFieldData,
											value : outParametersNameData
										});
										outParametersField.combobox('setValue','');
										outParametersName.combobox('setValue','');
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
						</script>
					</td>
				</tr>			
				<tr>
					<td class="tdCls">
						结果
					</td>
					<td class="tdCls">
						<div style="height:190px;overflow: auto;" >
							<table id="operateList" ></table>
						</div>
					</td>
				</tr>				
			</table>
		</div>
	</div>
	<div id="selectPage" style="display:none;width:300px;height:400px;" >
		<div class="ztree" id="selectPageTree"></div>
	</div>
</body>
</html>