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
<title>Java定义平台</title>
<%@ include file="/WEB-INF/views/inc/init_easyui.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<%@ include file="/WEB-INF/views/isdp/table/select_js.jsp"%>
<%@ include file="/WEB-INF/views/form/defined/table/select_js.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/glaf-base.js"></script>

<style type="text/css"> 
	.tdCls{
		background-color:#FCFCFC;
		align:left;
		height:16px;
	}
	.selectCls{
		width : 36%;
	}
	.textCls{
		width : 65%;
	}
</style>
<script type="text/javascript">
	var zTree,tabs,prop_tabs,jQueryMenu,jQueryFrame,selectedNode,contextPath = '${contextPath}',url = contextPath + "/mx/form/defined";
</script>
<script type="text/javascript" src="${contextPath }/webfile/js/defined.js" ></script>
<script type="text/javascript">

	var rows;//数据库保存数据
	var hideRows;
	//获取、显示配置属性
	function showComponentProperties(options,jq){
		this.options = options;
		this.jq = jq;
		window.K = this;
		this.init = function(){
			tfMsg = {};
			resultData = {};
			jQuery.ajax({
				url : url +  '/getFormCompoentProperties',
				type : 'post',
				data : options,
				async : false,
				dataType : 'json',
				success : function(data){
					if(data){
						resultData.data = data;
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
							}
							if(!item.parentId){
								populateObj(item,obj);
							}
						});
						
						closeAllTabs(prop_tabs);//关闭所有属性tab
						
						$.each(obj,function(k,v){
							if(k != 'undefined'){
								var table = $("<table>",{
									id : k,
									width : '98%',
									border : 0,
									cellpadding : 5,
									cellspacing : 1,
									bgcolor : 'BCD2EE',
									align : 'left'
								});
								prop_tabs.tabs('add',{
									title: dictSort.data[k] ? dictSort.data[k].name : k,
									content:$("<div class='tab_div' >").append(table)
								});
								K.createPropertyDocument(v,function(tr){
									table.append(tr.join(''));
								});
								K.initEvent(table);
							}
						});
						prop_tabs.tabs('select',0);
						
						//隐藏的内容
						if(tfMsg.value_){
							K.showHideRows(tfMsg.value_);
							tfMsg.value_ = null;
						}
					}
				}
			});
		};
		
		this.showHideRows = function(value_){
			if(value_){
				for(var i in value_){
					var hide = value_[i],roleindex = hide.id,value = hide.value;
					var $tr = $("table tr[roleindex=" + roleindex + "]");
					if($tr[0]){
						var hideRow = hideRows[value],pad = $tr.attr('pad');
						if(hideRow){
							$.each(hideRow,function(key,val){
								$.log(val);
								K.createPropertyDocument(val,function(tr){
									$tr.after(tr.join(''));
								},pad,$tr.attr('class'));
							});
						}
						K.initEvent($('.cls_' + roleindex));
					}
				}
			}
		};
		
		this.createPropertyDocument = function(data,fn,pad,cls){
			
			var tr = new Array();
			
			  var domFn = new K.domFn({
				tr : tr,
				span : "<span style='color:#fff;'  >·</span>",
				pad : pad || 1,
				cls : cls || ''
			});
			
			$.each(data,function(index,item){
				domFn.dTypeFn.init(item);
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
					
					saveData.componentId = item.componentId;
					
					var id = 'id_' + item.id,isSort = item.isSort == '0',editor = item.editor,pad = Fn.options.pad;
					
					Fn.options.tr.push("<tr roleIndex='"+ item.id +"' class='"+ Fn.options.cls +" cls_"+ item.parentId +"' isSort='" + isSort + "' pad=" + pad + " ><td class='tdCls' >");
					
					function padding(n,collection){
						for(var i = 0; i < n ; i ++){
							collection.push(Fn.options.span);
						}
					}
					
					if(isSort){//分类
						var $img = $("<img>",{
							id : 'img_' + item.id,
							src : contextPath + '/webfile/icons/open.png',
							onclick : 'openChildren(this)'
						});
						padding(pad - 1,Fn.options.tr);
						Fn.options.tr.push($img.outter());
					}else{
						padding(pad,Fn.options.tr);
					}
					
					Fn.options.tr.push(item.title);
					Fn.options.tr.push('</td><td class="tdCls" style="width:45%;">');
					
					if(editor){
						if(!isSort){
						
							var fn = editor.type;
							
							if(Fn.dTypeFn[fn]){
								Fn.dTypeFn[fn](item,id,Fn.options.tr);
							}else{
								Fn.dTypeFn["def"](item,id,Fn.options.tr);
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
					
						id = 'name_' + item.id;
						
						function endswidth(str,syn){
							if(!str || !syn){
								return false;
							}
							if(str.substring(str.length - syn.length) == syn)
							  return true;
							else
							  return false;
							return true;
						}
						
						if(item.value){
							if(!dictDatas[selectLength]){
								$.getDictByCode(selectLength,function(data){
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
					
					var jdomOption = {id:id,idField:idField,name:item.id,type:'text','class':'textCls textbox ' + cls,value:json.value};
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
					
						tr.push($("<input>",{
							idField : item.id,
							id : 'hidden_' + item.id,
							type : 'hidden',
							value : item.value
						}).outter());
						tr.push("<select itemid='"+ item.id +"'  name='" + item.id + "'  class='definedcombo' style='width:60px;'  value_='"+ item.value_ +"' >");
						if(dictDatas[selectLength]){
							jQuery.each(dictDatas[selectLength],function(i,o){
								//$.log(o);
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
					}
					
				},
				text : function(item,id,tr){
					tr.push(jQuery('<input>',{id:id,idField:item.id,name:item.id,class:'textCls',value:item.value}).outter());
				},
				dialog : function(item,id,tr){
					
					var value = item.value,hiddenId = id + '_hidden',hiddenValue = '',json = value,editor = item.editor;
					
					try{
						if(!(value instanceof Array) || !(value instanceof Object))
							json = JSON.parse(value);
					}catch(e){
						
					}
					if(json instanceof Array){
						hiddenValue = JSON.stringify(json);
						value = [];
						jQuery.each(json,function(i,v){
							//value += ',' + v.name;
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
							tfMsg.tableObjElementId == null ? tfMsg.tableObjElementId = hiddenId : "";
						} else if(editor.data == 'selectField'){
							tfMsg.fieldObjElementId == null ?  tfMsg.fieldObjElementId = hiddenId : '';
							tfMsg.filedNameElementId == null ? tfMsg.filedNameElementId = id : '';
						}else if(editor.data == 'selectTreeField'){
							tfMsg.selectTreeFieldId == null ? tfMsg.selectTreeFieldId = hiddenId : '';
						}else if(editor.data == 'selectPage'){
							tfMsg.pageElementId == null ? tfMsg.pageElementId = hiddenId : '';
						}else if(editor.data == 'dataSourceSet'){
							tfMsg.dataSourceSetId == null ? tfMsg.dataSourceSetId = hiddenId : '';
						} else {
							var objId = editor.data + "Id";//
							tfMsg[objId] = tfMsg[objId] || hiddenId;
						}
						
					}
					var jdom;
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
				},
				checkbox : function(item,id,tr){
					
					item.value == 'true' ? item.value = true : item.value = false;								
								
					tr.push(jQuery('<input>',{id:id,name:item.id,type:'checkbox',style:'margin-left: 40%;'}).outter());
					
					tr.push(jQuery('<input>',{id:id + '_hidden',idField:item.id,name:item.id,value:item.value,type:'hidden'}).outter());
				},
				select : function(item,id,tr){
					tr.push(jQuery('<input>',{id:id,idField:item.id,value:item.value,type:'hidden'}).outter());
					if(!tfMsg.value_){
						tfMsg.value_ = new Array();
					}
					tfMsg.value_[item.value] = item.value;
					tfMsg.value_.push({
						id : item.id,
						value : item.value
					});
					
					tr.push("<select hiddenId='"+ id +"' class='combobox selectCls'  name='" + item.id + "' value_='"+ item.value_ +"' ><option value='' >---请选择---</option>");
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
				},
				multiselect : function(item,id,tr){
					var editor = item.editor;
					if(editor.options.data){
						if(item.name == 'linkageControlIn'){
							tfMsg.linkageControlInId = id;
						} else {
							tfMsg[item.name + "Id"] = id;
						}
						tr.push(jQuery('<input>',{id:id,idField:item.id,type:'hidden',value:item.value}).outter());
						if(editor.options.data instanceof Array){
							var data = JSON.stringify(editor.options.data);
							Fn.options.jdom = jQuery('<div>',{hiddenId:id,class:'combotree selectCls','data-options':"multiple:true,data:" + data + ""});
						}else{
							Fn.options.jdom = jQuery('<div>',{hiddenId:id,class:'combotree selectCls',multiChange:editor.data,'data-options':'width:120,multiple:true,data:[]'});
						}
						tr.push(Fn.options.jdom.outter());
					}
				}
			};
		};
		
		this.initEvent = function(jDom){

			jDom.find('.easyui-numberbox').each(function(){
				var $this = $(this),dataType = $this.attr('dataType'),precision = 0;
				if(dataType == 'double')
					precision = 2;
				if($(this).nextAll('select').length>0){
					$(this).numberbox({
						width : '45%',
						precision : precision
					});
				} else{
					$(this).numberbox({
						width : '90%',
						precision : precision
					});
				}
			});
			
			jDom.find('.textbox').each(function(){
				if($(this).nextAll('.linkbutton').length>0){
					$(this).css({
						width : "78%",
						height : 20
					});
				} else{
					$(this).css({
						width : "90%",
						height : 20
					});
				}
			});
		
			//checkbox
			jDom.find('input[type=checkbox]').each(function(){
				var hidden = $('#' + $(this).attr('id') + '_hidden');
				jQuery(this).attr('checked',hidden.val() == 'true').bind('click',function(){
					hidden.val(jQuery(this).is(':checked'));
				});
			});
			
			//combotree
			jDom.find('.combotree').each(function(index,item){
				
				jQuery(item).css({
					width : '90%'
				});
				
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
							var text = jQuery(this).attr('title') || jQuery(this).attr('name') || id;
							//if(id != options.id)
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
						if(data && data[0]){
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
			
			//combo   自定义下拉
			jDom.find('.definedcombo').combobox({
				onSelect : function(data){
				
					var itemid = jQuery(this).attr('itemid'),nameObj = jQuery('#name_' + itemid),hiddenObj = jQuery('#hidden_' + itemid);
					
					hiddenObj.val(nameObj.val() + data.value);
					
				}
			});
			
			//changeCls
			jDom.find('.changeCls').each(function(){
				var $this = $(this);
				$this.next('span').find('input:visible').on('change',function(){
					var combo = $this.parent().find('.definedcombo');
					var val = this.value,syn = combo.combobox('getValue');
					var idfield = $this.attr('textboxname');
					$('#hidden_' + idfield).val(val + syn);
				});
			});
			
			//combobox
			try{
				jDom.find('.combobox').combobox({
					onSelect : function(data){
						var hiddenId = jQuery(this).attr('hiddenId'),hiddenObj = jQuery('#' + hiddenId),$tr = hiddenObj.parent().parent();
						var children = '.cls_' + $tr.attr('roleIndex'),pad = $tr.attr('pad'),$children = $(children);
						if($children && $children[0]){
							$children.remove();
						}
						hiddenObj.val(data.value);
						var value_ = jQuery(this).attr('value_'),$tr,$nextAll,cre = function(){
							if(data.value){
								var obj = hideRows[data.value];
								if(obj){
									$.each(obj,function(key,data){
										K.createPropertyDocument(data,function(tr){
											$tr.after(tr.join(''));
										},pad,$tr.attr('class'));
									});
									K.initEvent($(children));
									//隐藏的内容
									if(tfMsg.value_){
										K.showHideRows(tfMsg.value_);
										tfMsg.value_ = null;
									}
								}
							}
						};
						
						switch(value_){
							case 'buttonType':
								cre();
								break;
							case 'jumpType':
								cre();
								break;
							default:
								break;
						}
					},
					editable:false
				});
			}catch(e){
				$.log(e);
			}
			
			jDom.find('.linkbutton').linkbutton();
			
		};
		
		this.init();
		
	}
	
	var dictDatas = new Object();
	
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
	
	//保存配置
	var saveData = new Object();
	function saveFunc(){
		//var idField = jQuery('#button_tool [idField]');
		//var idField = $('.tab_div table [idField]');
		
		var field = null,val,collection = new Object();
		var json = resultData.data.value || new Object();
		$(".tab_div table").each(function(i,v){
			
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
			saveData.id = saveData.formRuleId;
			saveData.value = JSON.stringify(json);
			saveData.collection = JSON.stringify(collection);
			
			jQueryFrame.data('save',{id:saveData.name,isSave:true});
			
		   jQuery.post(url + '/saveOrUpdateFormRule',saveData,function(data){
				if(data){
					saveData.formRuleId = data.id;
					alert('操作成功!');
				}
			},'json');
			
		}
		
	}
	
	var resultData;
	function getChildren(parentId){
		if(!resultData[parentId]){
			resultData[parentId] = new Array();
			$.each(resultData.data.rows,function(i,item){
				if(item.parentId == parentId)
					resultData[parentId].push(item);
			});
		}
		return resultData[parentId];
	}
	
	function openChildren(img){
		if(img){
			var $img = $(img),$tr = $img.parent().parent();
			var parentId = $tr.attr('roleIndex'),data = getChildren(parentId);
			if(data){							
				var children = '.cls_' + parentId;
				var opertator = new operateChildren($(children),parentId);
				if(!$tr.data('open')){
					$img.attr({
						src : contextPath + '/webfile/icons/close.png'
					});
					if(!$tr.data('isShow')){
						var pad = $tr.attr('pad') * 1;
						K.createPropertyDocument(data,function(tr){
							$tr.after(tr.join(''));
						},(pad  + 1),$tr.attr('class'));
						K.initEvent($(children));
						//隐藏的内容
						if(tfMsg.value_){
							K.showHideRows(tfMsg.value_);
							tfMsg.value_ = null;
						}
						$tr.data('isShow',true);
					}else{
						opertator.show();
					}
				}else{
					$img.attr({
						src : contextPath + '/webfile/icons/open.png'
					});
					opertator.hide();
				}
				$tr.data('open',!$tr.data('open'));
			}
		}
	}
	
	function operateChildren(jq,parentId){
		this.jq = jq,this.parentId = parentId;
		this.hide = function(){
			$('.cls_' + parentId).each(function(){
				$(this).hide();
			});
		};
		
		this.show = function(){
			var cls;
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

	function showSort(node) {
        var link="${contextPath}/mx/form/page/showSort?parentId="+node.id;
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "分类排序",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['620px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
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
				<a class='easyui-linkbutton' onclick="showFunc()" >预  览</a>
			</div>
		</div>		
	</div>

	<div id='west' style="width:230px;" data-options="region:'west',split:true,title:'项目',collapsible:true"  >
		<div>
			<table>
				<tr>
					<td>
						<a class='easyui-linkbutton' onclick="javascript:createSort()" >增加分类</a>
					</td>
					<td style="padding-left: 25px;" >
						<input style="height: 20px; width:60px;" class="textbox" id="searchTextBox"  >
					</td>
					<td>
						<a class='easyui-linkbutton' onclick="javascript:searchProject()" >搜索</a>
					</td>
				</tr>
			</table>
		</div>
		<div class="ztree" id="tree"></div>
	</div>
	<div id='east' data-options="region:'east',split:true,title:''" style="width:360px;">
		<div class="easyui-tabs" data-options="fit:true,border:false" id="prop-tabs"></div>
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
	    <div onclick="createSort(selectedNode);" >增加分类</div>
		<div onclick="showSort(selectedNode);" >分类排序</div>
	    <div onclick="zTree.setting.callback.onCreate.call();" >增加模板</div>
		<div onclick="zTree.editName(selectedNode);" >修改模板名称</div>
		<div onclick="zTree.setting.callback.onUpdate.call();" >更新模板</div>
		<div onclick="zTree.setting.callback.onEexport.call();" >导出模板</div>
	    <div onclick="zTree.setting.callback.onRemove.call();" >删除</div>
	</div> 
	
	<div id='detailDialog' class="easyui-dialog" style="width:400px;height:200px;" data-options="closed:true,title:'详细信息'" >
		
	</div>
	<div id='sortDialog' class="easyui-dialog" style="width:300px;height:120px;" data-options="closed:true,title:'增加分类'" >
		<div style="padding:10px;">
			增加分类   : <input id="sortId" type='text' >
		</div>
	</div>
	
	<div id='operateFieldsDialog' class="easyui-dialog" style="width:300px;height:440px;" data-options="closed:true,title:''" >
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
				<tr class="syn" style="display:none;" >
					<td class="tdCls" id='operator4'>
					</td>
					<td class="tdCls" id='operator5'>
					</td>
				</tr>
				<tr>
					<td colspan="2" class='tdCls' >
						<div style="float:right;">
							<a class='easyui-linkbutton' data-options="iconCls:'icon-add'" id="add-operator" >添 加</a>
							<a class='easyui-linkbutton' data-options="iconCls:'icon-remove'" onclick='deleteCombineList()' >删 除</a>
						</div>
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
	
	<div id="selectField" style="display:none;width:300px;height:200px;" >
		<div id='selectFieldComb' style='margin : 10px;' ></div>
	</div>
</body>
</html>