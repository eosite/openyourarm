<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/layer/skin/layer.ext.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/treegrid/css/jquery.treegrid.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/layer/extend/layer.ext.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/treegrid/js/jquery.treegrid.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/webfile/js/jquery.cookie.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/webfile/js/similarity.js"></script>
<style type="text/css">
html {
	height: 100%;
	width: 100%;
	margin: 0;
}

body {
	height: 100%;
	width: 100%;
	margin: 0;
	font: 14px/1 Tahoma, Helvetica, Arial, "宋体", sans-serif;
	overflow: hidden;
}

.expspan span:visited {
	line-height: 18px;
	color: #000066;
	letter-spacing: 0.1em;
	text-decoration: none;
}

.expspan  span:hover {
	line-height: 18px;
	color: #FF0000;
	letter-spacing: 0.1em;
	text-decoration: underline;
}

.expspan  span:active {
	font-size: 14px;
	line-height: 18px;
	color: #000066;
	letter-spacing: 0.1em;
	text-decoration: none;
}

.tree td {
	border-top: 1px solid #ddd;
	padding: 5px;
}

.groupConTree td {
	border-top: 1px solid #ddd;
	padding: 5px;
}

ul li {
	list-style-type: none;
}

.dom_line {
	margin: 2px;
	border-bottom: 1px gray dotted;
	height: 1px
}

.domBtnDiv {
	display: block;
	padding: 2px;
	border: 1px gray dotted;
	background-color: powderblue
}

.categoryDiv {
	display: inline-block;
	width: 335px
}

.domBtn {
	display: inline-block;
	cursor: pointer;
	padding: 2px;
	margin: 2px 10px;
	border: 1px gray solid;
	background-color: #FFE6B0
}

.domBtn_Disabled {
	display: inline-block;
	cursor: default;
	padding: 2px;
	margin: 2px 10px;
	border: 1px gray solid;
	background-color: #DFDFDF;
	color: #999999
}

.dom_tmp {
	position: absolute;
	font-size: 12px;
}

.active {
	background-color: #93C3CF
}

.td-cls {
	background-color: #FCFCFC;
	align: left;
	height: 16px;
}
.matchTitle{
	font-size: 16px;
	font-weight: 900;
	background-color: rgb(217, 236, 245);
}
ul.conmenu.k-widget,button{
	border-radius: 4px;
	font-size: 12px !important;
}
</style>
<script type="text/javascript">
	window.mt = {
		contextPath : '${pageContext.request.contextPath}',
		eleId : '${param.eleId}',
		eleType : '${param.eleType}',
		fn : '${param.fn}',
		retFn : '${param.retFn}',
		isQuick : '${param.isQuick}',
		$parent : window.opener || window.parent,
		datas: null
	},window.fzmt = {
		each : function(obj,fn,collection){		
			this._each = function(o,f,c){
				if(f){			
					for(var i in o){
						var r = f.call(c == undefined ? o[i] : c,i,o[i]);
						if(r === false){
							break;
						} else if(r === true){
							continue;
						}
					}
				}
				return c;
			};
			return this._each(obj,fn,collection);		
		}
	};
	var paramTable;
	//初始化参数
	function initExpressionFn() {
		var $eve = mt.eventObj ;
		var $this = $eve.parent("ul"),
		$td = $this.closest('td');
   	    var textbox = $td.find('.textbox-menu');
		return JSON.parse(textbox.data("item").data);
	}
	
	//返回表达式执行函数
	function retExpression(data) {
		var $eve = mt.eventObj ;
		var $this = $eve.parent("ul"),
		$td = $this.closest('td');
   	    var textbox = $td.find('.textbox-menu');
   	    var item = $eve.data("item");
   	    if(item){
   	    	//if(item.items){
   	    		$td.find('ul')/*.hide()*/;
   	    		var value = data.expVal ;
   	    		item.value = value;
   	 			item.data = JSON.stringify(data) ;
   	    		textbox.val(value).data("item",item).show(); 	
	    	//}
   	    }
	}
	
	//获取参数
	function getExpression() {
		var expressionData = [];
		var datas = mt.eventObj.data("item"), inparams = datas?datas.items:[], inparam,i;
		if(mt.datas && mt.datas["menus"] && mt.datas["menus"]["in"]){
			inparams = mt.datas["menus"]["in"];
		}
		if(inparams && inparams.length >0){
			for(i=0;i<inparams.length;i++){
				inparam = inparams[i];
				var express = {};
				express.id = inparam.items.pageId+inparam.id ;
				express.name = inparam.text;
				getExpParams(inparam,express.id,expressionData);
				expressionData.push(express); 
			}
		}
		return expressionData;
	}
	
	//获取表达式参数
	function getExpParams(inParam,pid,expressionData){
		var items = inParam.items;
		for(var j=0;j<items.length;j++){
			var param = items[j];
			var subexpress = {};
			subexpress.id = param.id ? (items.pageId+param.id):(pid+"-"+j);
			if(subexpress.id == pid){
				subexpress.id = subexpress.id + j ;
			}
			subexpress.name = param.text;
			subexpress.pId = pid;
			subexpress.t = "";
			if(param.items){
				getExpParams(param,subexpress.id,expressionData)
			}else{
				subexpress.pageId = items.pageId ;
				subexpress.eleId = param.id ;
				subexpress.fnType = param.type;
				subexpress.lev = items.lev;
				subexpress.otype = items.otype;
				subexpress.idataId = param.dataId;
				subexpress.idataType = param.dataType;
				subexpress.iEditor = param.oEditor;
				if(param.code){
					subexpress.datasetId = param.datasetId;
					subexpress.columnName = param.columnName ;
					subexpress.dType = param.FieldType;
					subexpress.code = param.code;
					subexpress.value = param.value;
					subexpress.isFn = false ;
				}else{
					subexpress.columnName = param.columnName ;
					subexpress.dType = "string";
					subexpress.code = "~F{"+items.pageId+"."+param.id+"."+param.type+"}";
					subexpress.value = "~F{"+inParam.text+"."+param.text+"}";
					subexpress.isFn = true;
				}
			}
			expressionData.push(subexpress); 
		}
	}
	function getRets(){
		var rets = new Object(),fieldInput,paramInput;
		paramTable.find('tr').each(function(i,v){
			fieldInput = $(this).find('input[name=field]');
			paramInput = $(this).find('input[name=param]');
			
			var paramItem = paramInput.data('item');
			var fieldItem = fieldInput.data('item');
			
			if(!paramItem || !fieldItem )
				return false;
			
			if(paramItem &&!rets[paramItem.id]){
				rets[paramItem.id] = new Array();
			}
			var o = {
					inparam : fieldItem.inparam,
					inid : fieldItem.id,
					inname : fieldItem.value,
					inlev : fieldItem.lev,
					inpage : fieldItem.pageId,
					itype : fieldItem.otype,
					icode : fieldItem.code,
					idatasetId : fieldItem.datasetId,
					columnName  : fieldItem.columnName || '',
					outname : paramItem.value,
					outid : paramItem.id,
					outlev : paramItem.lev ,
					outpage : paramItem.pageId,
					eventType : paramItem.eventType,
					type : fieldItem.type,
					param : paramItem.param,
					otype : paramItem.otype,
					srcUrl : paramItem.srcUrl,

					dataId : paramItem.dataId,
					dataType : paramItem.dataType,
					idataType : fieldItem.dataType,
					idataId : fieldItem.dataId,

					oEditor : paramItem.oEditor,
					iEditor : fieldItem.oEditor
				} ;
			if(fieldItem.cssClass){
				//以下暂时不知道用来做什么的 先注释
				//o.items = fieldItem.items ;
				o.data = fieldItem.data ;
				o.cssClass = fieldItem.cssClass;
			}
			rets[paramItem.id].push(o);
		});
		return rets;
	}
	$(function(){
		
		var fn = mt.isQuick?mt.$parent.parent.parent[mt.fn]:mt.$parent.parent[mt.fn];
			paramTable = $("#param-table");
		
		//确定
		$("#button2").kendoButton({
			imageUrl : mt.contextPath +  "/images/okay.png",
			click : function(e) {
				
				var rets = getRets();
				mt.$parent[mt.retFn]([{name : '输入输出关系',datas : rets}]);
				parent.layer && parent.layer.close(parent.layer.getFrameIndex());
				/* if(fn){
					fn.call(mt.$parent.$('#' + mt.eleId)[0],[{
						name : '输入输出关系',
						datas : rets
					}]);
				} */
			}
		});
		
		if(fn){
			var params = mt.$parent['getParamFn']() ;
			var data = mt.datas = fn.call(mt.$parent.$('#' + mt.eleId)[0],"",params.inWidget,params.outWidget,params.outExt);
			if(data){
				var tf = new tableFunc("param-table");
				tf.menus = data.menus;

				if(!($.cookie("matchDis")=="true") && $.isEmptyObject(data.datas)){
					try{
						data.datas = autoMatch(data.menus);
					}catch(e){
						console.log(e);
					}
				}
				
				tf.initadd(data.datas);

				
				$('#button1').kendoButton({
					click : function(){
						tf.add();
					}
				});

				$('#button23').kendoButton({
					click : function(){
						isDetect = false;
						try{
							tf.initadd(autoMatch(data.menus));
						}catch(e){
							console.log(e);
						}
						//dialog.open();
					}
				});
				$('#button22').kendoButton({
					click : function(){
						$("#param-table").empty();
					}
				});

				$('#button666').kendoButton({
					click : function(){
						detectSelectObj = {};
						detectSelectOutObj = {};
						//设置为已经定义
						var checkeds = $("#dictcc").find(".auto-in").find(":checked");
						if(checkeds.length){
							$.each(checkeds,function(i,checked){
								var that = $(checked);
								detectSelectObj[that.attr("name")] = that.val();
							})
							isDetect = true;
						}
						var outcheckeds = $("#dictcc").find(".auto-out").find(":checked");
						if(outcheckeds.length){
							$.each(outcheckeds,function(i,checked){
								var that = $(checked);
								detectSelectOutObj[that.attr("name")] = that.val();
							})
							isDetect = true;
						}
						try{
							tf.initadd(autoMatch(data.menus));
						}catch(e){
							console.log(e);
						}
						dialog.close();
					}
				});
				$('#button667').kendoButton({
					click : function(){
						dialog.close();
					}
				});
			}
		}


		$(".mt-cb").on("click",function(){
			var $this = $(this),
				keyStr = "matchAll",
				valStr = "false";
			if($this.hasClass("dis")){
				keyStr = "matchDis";
			}
			if($this.is(":checked")){
				valStr = "true";
			}
			if(keyStr=="matchAll"){
				matchAll = valStr == "true"?true:false;
			}
			$.cookie(keyStr,valStr);
		});
		
		$.cookie("matchDis")=="true" && $(".mt-cb.dis").attr("checked",true);
		$.cookie("matchAll")=="true" && $(".mt-cb.all").attr("checked",true);
	});
	
	function tableFunc(id){
		
		this.menus = null;
		
		var $this = $('#' + id),K = this,$tr = [
             "<tr>",
       	  		"<td class='td-cls' ><div style='display:flex;'>",
       				"<div><ul class='in-menu conmenu' style='width:79px' ></ul></div>",
       		  		"<input type='text' class='k-textbox textbox-menu' name='field' style='width:100%;'/>",
       	  		"</div></td>",
       	  		
       	  		"<td class='td-cls' style='width:12px;'>=></td>",
       	  		
       	  		"<td class='td-cls' ><div style='display:flex;'>",
       				" <div><ul class='out-menu conmenu' style='width:44px;' ></ul></div>",
       		  		" <input type='text' class='k-textbox textbox-menu' name='param' style='width:100%' />",
       	  		"</div></td>",
       	  		
       	  		"<td class='td-cls' style='width:40px;'><button class='k-button del-cls'>删除</button></td>",
       	  		
       	  	"</tr>"
       	],eve = {
			//点击输入输出参数 赋值到输入框
			select : function(e){  
				var $eve = $(e.item);
				mt.eventObj = $eve ;
				if($eve.hasClass('exp')){
					var _href = "${pageContext.request.contextPath}/mx/expression/defined/view?category=front&retFn=retExpression" 
						+ "&getFn=getExpression&initExpFn=initExpressionFn&notValidate=true";
					window.open(_href);
					return ;
				}
				var $this = $eve.parent("ul"),
				$td = $this.closest('td');
		   	    var textbox = $td.find('.textbox-menu');
		   	    var item = $eve.data("item");
		   	    if(item){
		   	    	if(!item.items){
		   	    		//$td.find('ul').hide();
		   	    		var t = [
							item.text
		   	    		],p = [],value;
		   	    		$(e.item).parents('li').each(function(){
		   	    			if(!$(this).parent('ul').hasClass("conmenu")){
		    	    			var text = $(this).find('span:first').text();
		    	    			t.push(text);
		   	    			}
		   	    		});
		   	    		for(var i = t.length; i --;){
		   	    			p.push(t[i]);
		   	    		}
		   	    		value = p.join('-->');
		   	    		item.value = value;
		   	    		textbox.val(value).data("item",item).show();
		   	    		
		   	    		if(item.type == "updateSource"){
		   	    			var $p = $td.closest("tr").find("input[name=param]");
		   	    			var obj = $.extend({},item);
		   	    			obj.value = "自动匹配";
		   	    			$p.val(obj.value).data("item",obj);
		   	    		}
		   	    		
			    	}
		   	    }
			},
			close : function(e){
				var $li = $(e.item);
				var item = $li.data("item");
				if(item.level == 0){
					//var $ul = $li.parent("ul").hide();
		    	   // $ul.closest('td').find('.textbox-menu').show();
				}
			},
			popupCollision: false
		};
		
		this.add = function(){
			var c = $($tr.join(''));
			$this.append(c);
			K.initEvent(c);
			return c;
		};
		
		this.initadd = function(datas){//还原
			if(datas){
				var $tr;
				$.each(datas,function(k,item){
					
					$.each(item,function(i,v){
						$tr = K.add();
						$tr.find('input[name=field]').val(v.inname).data("item",{
							id : v.inid,
							value : v.inname,
							type : v.type,
							inparam : v.inparam,
							columnName : v.columnName || '' ,
							pageId : v.inpage,
							lev : v.inlev ,
							cssClass : v.cssClass ,
							data : v.data ,
							items : v.items ,
							otype : v.itype ,
							code : v.icode ,
							datasetId : v.idatasetId,
							dataId : v.idataId,
							dataType : v.idataType,
							oEditor : v.iEditor
						});
						$tr.find('input[name=param]').val(v.outname).data("item",{
							param : v.param,
							id : v.outid,
							value : v.outname,
							lev : v.outlev ,
							pageId : v.outpage,
							eventType :  v.eventType,
							otype : v.otype,
							srcUrl : v.srcUrl,
							dataId : v.dataId,
							dataType : v.dataType,
							oEditor : v.oEditor
						});
					});
					
				});
			}
		};
		
		this.addbatch = function(datas){
			$.each(datas,function(i,item){
				K.add();
			});
		};
		
		this.remove = function(){
			
		};
		
		this.initEvent = function(jq){
			jq.find(".del-cls").on('click',function(){
				if(confirm('你确定删除吗?')){
					$(this).closest('tr').remove();
				}
			});
			
			jq.find(".in-menu").each(function(i,item){
				var $this = $(this).empty();
				createMenu([{
					text : 'Q',
					level : 0,
					items : K.menus['in']
				},{
					text : 'E',
					level : 0 ,
					'cssClass' : 'exp',
					//items : K.menus['in']
				}],$this);
				$this.kendoMenu(eve);
			});
			
			 jq.find(".out-menu").each(function(i,item){
				var $this = $(this).empty();
				var outMenus = K.menus['out'];
				outMenus[0] && (outMenus[0].text = "Q");
				createMenu(outMenus,$this);
				$this.kendoMenu(eve);
			});
			 
			 jq.find(".textbox-menu").each(function(){
				$(this).on('dblclick',function(){
					$(this)/*.hide()*/.next().show();
				}).attr({
					readonly : true
				});
			});
			
		};
		
		//根据数据生成 ul >> li
		function createMenu(items,$meun){
			var $ul,$li, ditems=items;
			$.each(items,function(i,item){
				$li = $("<li>").text(item.text);
				if(item.cssClass){
					$li.addClass(item.cssClass);
					item.items = item.items ;
				}else{
					if(item.items){
						$ul = $("<ul>");
						$li.append($ul);
						ditems.lev && (item.items.lev = ditems.lev);
						ditems.pageId && (item.items.pageId = ditems.pageId);
						ditems.eventType && (item.items.eventType = ditems.eventType);
						ditems.otype && (item.items.otype = ditems.otype);

						createMenu(item.items,$ul);
					}
				}
				item.lev = ditems.lev ;
				item.pageId = ditems.pageId ;
				item.eventType = ditems.eventType ;
				if(ditems.otype){
					item.otype = ditems.otype ;	
				}
				item.oEditor = ditems.oEditor;
				item.srcUrl = ditems.srcUrl;
				$li.data("item",item);
				$meun.append($li);
			});
		}
		
	}
	
	
	
</script>
</head>
<body>
	<div id="head" class="k-header k-footer footerCss">
		<table style="width: 100%;">
			<tr>
				<td style="text-align: left; vertical-align: middle;"><img
					src="${pageContext.request.contextPath}/images/flow.png" style="vertical-align: middle;" /> <span
					style="font-size: 16px; font-weight: bolder;">输入&输出关系定义</span>
					<button class='k-button' id='button1' >
						<span class="k-icon k-add"></span>
						添加
					</button>
				</td>
				<td style="text-align: right;">
				<label><input type="checkbox" class="mt-cb all">全匹配</label>
				<label title="禁止自动匹配"><input type="checkbox" class="mt-cb dis">禁用</label>
					<button id="button23" type="button" class='k-button' >自动生成</button>
					<button id="button22" type="button" class='k-button' >一键删除</button>
					<c:choose>
					   <c:when test="${empty param.isQuick}">  
					         <button id="button2" type="button" class='k-button' >确定</button>
					   </c:when>
					   <c:otherwise> 
					   		<span style="width: 20px;display: inline-block;">&nbsp;</span>
					   </c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
	</div>
	<div style="height: 85%; width: 100%; overflow: auto;">
		<table id="param-table" class=""  width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="BCD2EE" align="center"></table>
		<div style="height: 600px;">
			
		</div>
	</div>
	<div id="dialog" data-role="window" style="padding: 0px;display: none;">
		<div class="k-header k-footer footerCss">
			<table style="width: 100%;">
			<tr>
				<td style="text-align: left; vertical-align: middle;"><img
					src="${pageContext.request.contextPath}/images/flow.png" style="vertical-align: middle;" /> <span
					style="font-size: 16px; font-weight: bolder;">自动匹配选择</span>
				</td>
				<td style="text-align: right;">
					<button id="button666" type="button" class='k-button' >确定</button>
					<button id="button667" type="button" class='k-button' >关闭</button>
				</td>
			</tr>
		</table>
		</div>
		<div id="dictcc">
			
		</div>
	</div>
	<script type="text/javascript">
		var dialog = $("#dialog").kendoWindow({
			title: "自动匹配",
		  	actions: [ "Minimize", "Maximize" ,"Close"],
		  	modal: true,
		  	width: "80%",
		  	height: "80%",
		}).data("kendoWindow");
		dialog.center();
		//dialog.maximize();
		//dialog.open();

		
	</script>
</body>
</html>