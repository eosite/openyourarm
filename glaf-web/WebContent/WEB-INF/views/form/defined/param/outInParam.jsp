<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.glaf.core.config.*"%>
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
div.k-animation-container ul{
	max-height : 400px !important;	
}
</style>
<script type="text/javascript">
	window.mt = {
		contextPath : '${pageContext.request.contextPath}',
		eleId : '${param.eleId}',
		eleType : '${param.eleType}',
		dynamicDataBase : '${param.dynamicDataBase}',
		fn : '${param.fn}',
		$parent : window.opener || window.parent,
		getDataSetParams : function(datasetIds, fn,isAsync) {
			$.ajax({
				url: mt.contextPath + "/rs/dataset/getDataSetParams",
				type: 'POST',
				dataType: 'JSON',
				async: isAsync?false:true,
				data: {
					datasetIds: datasetIds
				},
				success: function(ret) {
					if (fn) {
						fn(ret);
					}
				}
			});
		},
		dynamicId:'<%=SystemConfig.getString("dynamicDatabase")%>',
		buildDynamicParams:function(data){
			var items = []; //{"text":"字段名","id":"fieldName"}
			$.each(data,function(i,v){
				items.push({"text":v.name,"id":v.param});
			})
			return [{"text":"输出属性","items":[{"text":"属性","items":items}],"level":0}];
		}
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
	window.rushDyn = function(){
		var dynamicId = $("#dynaDatabase").data("dynamicId");
		$("body").data("dynamicId",dynamicId);
		window.menusout = mt.$parent.menus.out;
		//TODO 要改为动态从数据库获取
		mt.getDataSetParams(dynamicId,function(data){
			mt.$parent.menus.out = mt.buildDynamicParams(data);
		})
		$("#button4")[0].checked = false ;
	};

	$(function(){
		//动态数据集 start
		if(mt.dynamicDataBase=="true"){
			$("#button3").show();
			$("#tongyong").show();
			$("#dynaDatabase").show();
			$("#selectDatabaseDrop").show().kendoDropDownList({
				  dataSource: {
				    data: [{value:'databaseid',text:'下拉'},{value:'new',text:'新增动态数据集'}]
				  },
				  dataTextField:'text',
				  dataValueField:'value',
				  animation: false,
				  select:function(e){
				  		console.log(this.value());
				  		console.log(this.text());
				  		console.log(e);
				  }
			});
		}
		$("#button3").kendoButton({
			imageUrl : mt.contextPath +  "/images/database.png",
			click : function(e) {
				// /glaf/mx/form/defined/table/main2?resultsElementId=id_274_hidden&tablenameElementId=id_274&fieldnameElementId=&flag=0
				mt.$parent.$ioWindow = window ;
				mt.$parent.$("body").data("dynaDatabase",$("#dynaDatabase"));
				var SB = new mt.$parent.StringBuffer(),
					//url = "/glaf/mx/form/dataset/dynamicDatabase?id=744576e9c20e4ad0825b24b87caa3eac" ;
					url = mt.contextPath+"/mx/form/defined/table/main2?results=dynaDatabase&flag=1" ;
				SB.appendFormat("<iframe id='{id}' width='100%' height='660' src='{url}' frameborder='no' scrolling='no'></iframe>", {
					id: mt.$parent.getSingleId(),
					url: url
				});
				mt.$parent.dynaDatabase =  mt.$parent.show({
					title: '动态数据集',
					size: mt.$parent.BootstrapDialog.SIZE_WIDE,
					message: SB.toString(),
					modal: true,
					draggable: true,
					buttons : [  
					{   label : 'Close!', 
					    cssClass : 'btn-warning',
					    action : function(d) { 
					    	d.close(); 
					    } 
					} ],
					css: {
						width: $(mt.$parent).width() * 0.5
					}
				});
			}
		});
		$("#button4").on("click",function(){
			if(this.checked){
				$("body").data("dynamicId",mt.dynamicId);
				window.menusout = mt.$parent.menus.out;
				//TODO 要改为动态从数据库获取
				mt.getDataSetParams(mt.dynamicId,function(data){
					mt.$parent.menus.out = mt.buildDynamicParams(data);
				})
			}else{
				$("body").removeData("dynamicId");
				if(window.menusout){
					mt.$parent.menus.out = window.menusout;
				}
			}
		});
		//动态数据集 end

		var fn = mt.$parent[mt.fn],paramTable = $("#param-table");
		
		//确定
		$("#button2").kendoButton({
			imageUrl : mt.contextPath +  "/images/okay.png",
			click : function(e) {
				
				var rets = new Object(),fieldInput,paramInput;
				var sort = false;
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
					
					!sort && (sort = !!paramItem.sortType);
					var time = new Date().getTime();
					rets[paramItem.id].push({
						inparam : fieldItem.inparam,
						inid : fieldItem.id || (time ++),
						inname : fieldItem.value,
						incode : fieldItem.code,
						indatasetId : fieldItem.datasetId,
						columnName  : fieldItem.columnName || '',
						outname : paramItem.value,
						outid : paramItem.id,
						type : fieldItem.type,
						param : paramItem.param,
						sortType : paramItem.sortType || fieldItem.sortType,
						params : fieldItem.params
					});
					
				});
				if(fn){
					var $target =  mt.$parent.$("body").data("target"),
						eleId = $target?$target.attr("data-field"):"" ,
						dynamicId = $("body").data("dynamicId"),
						dynamicName = $("#dynaDatabase").val();
					fn.call(mt.$parent.$('#' + mt.eleId)[0],[{
						name : '输入输出关系',
						isDynamic: mt.dynamicDataBase,
						dynamicId: dynamicId,
						dynamicName : dynamicName,
						eleId : eleId , 
						datas : rets,
						sort : sort
					}]);
				}
			}
		});
		
		if(fn){
			var data = fn.call(mt.$parent.$('#' + mt.eleId)[0]);
			if(data){
				
				window.tf = new tableFunc("param-table");
				tf.menus = data.menus;
				if(data.isDynamic == "true"){
					$("body").data("dynamicId",data.dynamicId);
					if(data.dynamicId == mt.dynamicId){
						$("#button4")[0].checked = true ;
					}else{
						$("#dynaDatabase").data("dynamicId",data.dynamicId).val(data.dynamicName);
					}
					window.menusout = mt.$parent.menus.out;
					mt.getDataSetParams(data.dynamicId,function(d){
						mt.$parent.menus.out = mt.buildDynamicParams(d);
					},true);
				}

				tf.initadd(data.datas);
				
				$('#button1').kendoButton({
					click : function(){
						tf.add();
					}
				});
			}
			
			
		}
		
	});
	
	/**
	 *表达式func start
	 */
	function getSelectedItem(){
		var e = $(document).data("menu");
		var $this = $(e.item).parent("ul"),
		$td = $this.closest('td');
		var $textbox = $td.find('.textbox-menu');
   	    var  item = $textbox.data("item");
   	 	!item && ($textbox.data("item", itme = {}));
		item.$target = e.item;
		item.$td = $td;
		item.$ul = $this;
		item.$textbox = $textbox;
		return item;
	}
	
	function setRowField(data){
		var item = window.getSelectedItem();
		if(data && item){
			item.value = data.expVal;
			!item.params && (item.params = {});
			item.params.expression = data;
			
			item.$ul.hide();
	   	 	item.$textbox.val(item.value).data("item", item).show();
		}
	}
	
	function getinitExp(){
		var item = window.getSelectedItem();
		if(item){
			return item.params ? item.params.expression : null;
		}
		return  null;
	}
	
	function getRowTree(){
		
	}
	
	/**
	 *表达式func end
	 */
	
	function tableFunc(id){
		
		this.menus = null;
		
		var $this = $('#' + id),K = this,$tr = [
             "<tr>",
       	  		"<td class='td-cls' style='width:42%'>",
       		  		" <input type='text' class='k-textbox textbox-menu' name='field' style='width:100%;' />",
       				" <ul class='in-menu conmenu' style='display:none;width:100%;' ></ul>",
       	  		"</td>",
       	  		
       	  		"<td class='td-cls'> ==> </td>",
       	  		
       	  		"<td class='td-cls' style='width:42%'>",
       		  		" <input type='text' class='k-textbox textbox-menu' name='param' style='width:100%;' />",
       				" <ul class='out-menu conmenu' style='display:none;width:100%;' ></ul>",
       	  		"</td>",
       	  		
       	  		"<td class='td-cls'><button class='k-button del-cls'>删除</button></td>",
       	  		
       	  	"</tr>"
       	],eve = {
			select : function(e){
				var $this = $(e.item).parent("ul"),
				$td = $this.closest('td');
		   	    var textbox = $td.find('.textbox-menu');
		   	    var item = $(e.item).data("item");
		   	    if(item){
		   	    	
		   	    	if(item.level == 1 && "${param.flow}"){
		   	    		$(document).data("menu", e);
	    	    		var expURL = "${contextPath}/mx/expression/defined/view?" + $.param({
							retFn : "setRowField",
							getFn : "getRowTree",
							initExpFn : "getinitExp",
							category : 'db'
						});
						window.open(expURL);
						
						return false;
		   	    	}
		   	    	
		   	    	if(!item.items){
		   	    		$td.find('ul').hide();
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
					var $ul = $li.parent("ul").hide();
		    	    $ul.closest('td').find('.textbox-menu').show();
				}
			}
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
							columnName : v.columnName || '',
							sortType : v.sortType,
							params : v.params,
							code : v.incode,
							datasetId : v.indatasetId
						});
						$tr.find('input[name=param]').val(v.outname).data("item",{
							param : v.param,
							id : v.outid,
							value : v.outname
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
				
				var menus = [];
				
				if("${param.flow}"){
					menus.push({
						text : "表达式",
						level : 1,
						leaf : false
					});
				}
				
				menus.push({
					text : '输入控件',
					level : 0,
					items : K.menus['in']
				});
				
				createMenu(menus, $this);
				$this.kendoMenu(eve);
			});
			
			 jq.find(".out-menu").each(function(i,item){
				var $this = $(this).empty();
				createMenu(K.menus['out'],$this);
				$this.kendoMenu(eve);
			});
			 
			 jq.find(".textbox-menu").each(function(){
				$(this).on('dblclick',function(){
					$(this).hide().next().show();
				}).attr({
					readonly : true
				});
			});
			
		};
		
		//根据数据生成 ul >> li
		function createMenu(items,$meun){
			var $ul,$li;
			$.each(items,function(i,item){
				$li = $("<li>").text(item.text);
				if(item.items){
					$ul = $("<ul>");
					$li.append($ul);
					createMenu(item.items,$ul);
				}
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
				<td style="width: 500px; text-align: left; vertical-align: middle;"><img
					src="${pageContext.request.contextPath}/images/flow.png" style="vertical-align: middle;" /> <span
					style="font-size: 16px; font-weight: bolder;">输入&输出关系定义</span>
					<button class='k-button' id='button1' >新增</button>
				</td>
				<td style="text-align: right;">
					<span style="display: none;" id="tongyong">
						<input type="checkbox" id="button4" name="button4" ><label for="button4">通用</label>
					</span>
					<!--
					<input type="hidden" class="k-textbox" id="dynaName_hid">
					<div id="selectDatabaseDrop" style="display: none;"></div>
					-->
					<input type="text" class="k-textbox" id="dynaDatabase" style="display: none;">
					<button id="button3" type="button" class='k-button' style="display: none;" >动态数据集</button>
					<button id="button2" type="button" class='k-button' >确定</button>
				</td>
			</tr>
		</table>
	</div>
	<div style="height: 85%; width: 98%; overflow: auto;">
		<table id="param-table" class=""  width="98%" border="0" cellpadding="5" cellspacing="1" bgcolor="BCD2EE" align="center"></table>
	</div>
</body>
</html>