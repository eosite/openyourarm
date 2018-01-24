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
</style>
<script type="text/javascript">
	

	var contextPath = '${pageContext.request.contextPath}',mt = {
		each : function(obj,fn,collection){		
			this._each = function(o,f,c){
				if(f){			
					var i,r;
					for(i in o){
						r = f.call(c == undefined ? o[i] : c,i,o[i]);
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
		
	},eleId = '${param.eleId}',eleType = '${param.eleType}',paraTypeFunc = '${param.fn}',$parent = window.opener || window.parent;
	
	$.fn.extend({
		addNodes : function(nodes){
			var $this = this;
			if(nodes && nodes.length > 0){
				return $this.each(function(){
					var $t = $(this);
					$.each(nodes,function(i,node){
						return $t.addNode(node);
					});
				});
			}
		},
		addNode : function(node){
			var $this = this;
			if(!node.pId){//根节点
				return $this.find('tr:first');
			} else {
				var pnode = $this.find('treegrid-' + node.pId);
				if(pnode[0]){
					return createnode($this.find('treegrid-parent-' + node.id + ':last'),node);
				} else {
					return createnode($this.find('tr:last'),node);
				}
			}
			
			function createnode($tr,n){
				var $node;
				if(n.isParent){
					$node = $(kendo.format(templateComposit.join(''),n.id + " treegrid-parent-" + n.pId,n.name));
				} else {
					$node = $(kendo.format(templateChildren.join(''),n.id,n.pId,n.name));
				}
				$tr.after($node);
				
				initEvent($node);
				
				$this.treegrid();
				
				return $node;
			}
		}
	});

	var templateComposit = [ 
	   		"<tr class='treegrid-{0}'>",
	   			"<td>{1}</td>",
	   			"<td colspan='2'>",
	   				"<button class='addCon addChildCon k-button' name='addChildCon' type='button'>新增</button>", " <select class='linkCon'> </select>",
	   			"</td>", 
	   			"<td>{2}</td>", 
	   			"<td></td>", 
	   		"</tr>" ];

	var templateChildren = [ 
	     "<tr class='treegrid-{0} treegrid-parent-{1} mytree' >", 
	     		"<td>{2}</td>", 
	     		"<td>",
	     			"<button class='addCon addSiblingCon k-button' name='addSiblingCon' type='button'>新增</button>", 
	     		"</td>",
	     		
	     		"<td>",
						" <input type='text' class='k-textbox textbox-menu' name='field' />",
						" <ul class='conMenu' style='display:none;' ></ul>",
				"</td>", 
	     		
	     		"<td>",
					" <select class='jCon' style='width: 80px;'> </select> ", "</td>", "<td>", " <input  class='fieldComB' name='param' ></input> ", "<input type='checkbox'  value='1'/> 绑定 ", " <button  class='delCon k-button' name='delCon' type='button'>删除</button> ",
					" <button  class='upCon k-button' name='upCon' type='button'>转换为复合条件</button> ",
				"</td>",
	"</tr>" ];

	var templateCompositStr = kendo.format(templateComposit.join(''), 1, 1, "");

	var outParamDataSource = [ {
		title : '参数一',
		columnName : 'col1'
	}, {
		title : '参数二',
		columnName : 'col2'
	} ];
	var inParamDataSource = new Array();
	
	function increateInParam(){
		var len = inParamDataSource.length;
		inParamDataSource.push({
			name : '参数' + (len + 1),
			param : 'col' + (new Date().getTime())
		});
	}
	
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
</script>
</head>
<body>
	<div id="head" class="k-header k-footer footerCss">
		<table style="width: 100%;">
			<tr>
				<td style="width: 500px; text-align: left; vertical-align: middle;"><img
					src="${pageContext.request.contextPath}/images/flow.png" style="vertical-align: middle;" /> <span
					style="font-size: 16px; font-weight: bolder;">输入&输出关系定义</span></td>
				<td style="text-align: right;">
					<button id="button2" type="button">确定</button>
				</td>
			</tr>
		</table>
	</div>
	<div style="height: 555px; width: 96%; overflow-y: auto; overflow-x: hidden;">
		<table id="groupConTree" class="tableCon groupConTree" style="border-spacing: 0px;"></table>
	</div>
	<script type="text/javascript">
		//初始化确认按钮
		$("#button2").kendoButton({
			imageUrl : "${pageContext.request.contextPath}/images/okay.png",
			click : function(e) {
				
				$("#groupConTree").each(function(){
					var nodes = $(this).treegrid("getAllNodes"),$this,collection = [],collectionTree = [],isOk = true;
					
					$(this).find("tr input").css({
						"background-color" : ''
					});
					 if(nodes && nodes.length > 1){
						 
						function hasChildren($tr){
							var children = $tr.treegrid('getChildNodes');
							if(children.length > 0){
								for(var i = 0; i < children.length; i ++){
									if(!$(children[i]).hasClass("parent-cls")){
										return true;
									}
								}
							}
							return false;
						}
						
						for(var i = 0; i < nodes.length; i ++){
							
							$this = $(nodes[i]), id = $this.treegrid("getNodeId"),pId = $this.treegrid("getParentNodeId");
							
							var treeObject = {
								name : $this.find("td:first").text(),
								id : id,
								pId : pId,
								isParent : $this.hasClass("parent-cls")
							};
							
							
							if(!$this.treegrid("isLeaf") || $this.hasClass("parent-cls")){
								
								var dataItem = $this.find("select").data("kendoDropDownList").dataItem();
								if(dataItem){
									treeObject.connector = dataItem.value;
								}
								
								if(hasChildren($this)){
									collectionTree.push(treeObject);
								}
								continue;
							}
							
							collectionTree.push(treeObject);
							var job = {},$connector = $this.treegrid("getParentNode");
							
							var fieldInput = $this.find("input[name=field]"),
							paramInput = $this.find("input[name=param]").data("kendoDropDownList");
							
							if(!fieldInput.val()){
								fieldInput.css({
									"background-color" : 'red'
								});
								isOk = false;
							}
							
							if(!isOk){
								continue;
							}
							
							var item = paramInput.dataItem();
							
							if(item){
								$.extend(job,item);
							}
							job.parentId = pId;
							job.ordinal = id;
							
							collection.push(job);
						}
					}
					 
					 var retArray = {
							 datas : collection,
							 tree : collectionTree
					 };
					 
					 if(eleId && paraTypeFunc){
						
						$parent[paraTypeFunc].call($parent.document.getElementById(eleId),retArray);
						
					 }
				});
			}
		});

		$("#groupConTree").append(templateCompositStr).treegrid();

		var treeFuncs = {
			addChildCon : function($tr) {
				treeFuncs.addSiblingCon.call(this, $tr, true);
			},
			addSiblingCon : function($tr, isChild) {

				var tree = $tr.parent();

				var parentNode = $tr.treegrid("getParentNode");
				if (isChild)
					parentNode = $tr;

				if (!tree.data("childId")) {
					tree.data("childId", 1);
				}

				var id = tree.data("childId") * 1 + 1;

				tree.data("childId", id);

				var pid = parentNode.treegrid("getNodeId");

				var name = parentNode.find(">td:first").text() + "." + (parentNode.treegrid("getChildNodes").length + 1);

				var last = tree.find(".treegrid-parent-" + pid + ":last");

				var node = $(kendo.format(templateChildren.join(''), id, pid, name)).attr({
					level : name
				});

				last = last[0] ? last : $tr;

				last.after(node);

				initEvent(node);

				tree.treegrid();

			},
			delCon : function($tr) {

				var tree = $tr.parent();

				var parentNode = $tr.treegrid("getParentNode");

				var pid = parentNode.treegrid("getNodeId");
				
				var parentName = parentNode.find(">td:first").text();

				tree.find("tr[level^='"+ $tr.find(">td:first").text() +"']").each(function(){
					$(this).remove();
				});
				
				if($tr[0])
					$tr.remove();
				
				tree.find(".treegrid-parent-" + pid).each(function(i, t) {
					$(this).find(">td:first").text(parentName + "." + (i + 1));
				});
				tree.treegrid();

			},
			upCon : function($tr) {

				var tree = $tr.parent();

				var id = $tr.treegrid("getNodeId");

				var name = $tr.find(">td:first").text();

				$tr.find(">td:first").text(name + ".1");

				var parentNode = $tr.treegrid("getParentNode");

				var pid = parentNode.treegrid("getNodeId");

				var node = $(kendo.format(templateComposit.join(''), id, name, "<button  class='delCon k-button' name='delCon2' type='button'>删除</button>"));

				node.removeAttr("class").attr({
					'class' : $tr.attr('class'),
					level : name
				});
				
				var tId = tree.data("childId") * 1 + 1;

				tree.data("childId", tId);

				$tr.removeClass("treegrid-parent-" + pid).addClass("treegrid-parent-" + id);

				$tr.removeClass("treegrid-" + id).addClass("treegrid-" + tId);
				
				$tr.attr({
					level : $tr.find(">td:first").text()
				}).before(node);

				initEvent(node);

				tree.treegrid();
			}
		};
		
		var datas = new Array(),menu = {
			text : "选择参数",
			level : 0,
			leaf : false,
			items : []
		};
		
		$.ajax({
			url : contextPath + '/mx/form/defined/getParametersByPageId',
			data : {pageId : '${param.pageId}'},
			type : 'post',
			dataType : 'json',
			async : false,
			success:function(data){
				if(data){
					mt.each(data, function(i,v){
						
						if(v.NAME_ != eleType && v.PARAMTYPE_ == 'outParameters' && v.VALUE_){
							if(v.VALUE_){
								var items = mt.each(JSON.parse(v.VALUE_),function(i,p){
									p.text = p.name;
									this.push(p);
								},new Array());
								
								menu.items.push({
									text : v.TITLE_ || v.NAME_,
									items : items
								});
							}
							
						}else if(v.NAME_ == eleType && v.PARAMTYPE_ == 'inParameters'){
							//当前 控件 的输入参数
						}
						
					});
				}
			}
		});
		
		var temp = {};
		if($parent[paraTypeFunc]){//输入参数(已经配置)
			var str = $parent[paraTypeFunc]();
			if(str){
				try{
					var arr = JSON.parse(str);
					if(arr){
						mt.each(arr, function(i,v){
							temp[v.param] = v;
							this.push(v);
						}, inParamDataSource);
					}
				}catch(e){
					
				}
			}
		}
		
		var ele = $parent.document.getElementById(eleId);
		if(ele){
			var str = $(ele).val();
			if(str){
				try{
					var json = JSON.parse(str);
					if(json){
						var tree = json.tree;
						$("#groupConTree").addNodes(tree);
					}
				}catch(e){
					
				}
			}
			
		} 
		
		
		function initEvent(jq) {
			jq.find(".addCon").kendoButton({
				imageUrl : "${pageContext.request.contextPath}/images/bullet_add.png",
				click : function(e) {
					var target = $(e.event.target), name = target.attr('name');
					if (name && (name in treeFuncs)) {
						treeFuncs[name].call(target, target.parents("tr"));
					}
				}
			});

			jq.find(".delCon").kendoButton({
				imageUrl : "${pageContext.request.contextPath}/images/bullet_delete.png",
				click : function(e) {
					var target = $(e.event.target);
					treeFuncs['delCon'].call(target, target.closest("tr"));
				}
			});

			jq.find(".upCon").kendoButton({
				imageUrl : "${pageContext.request.contextPath}/images/arrow_small_up.png",
				click : function(e) {
					var target = $(e.event.target);
					console.log(target.parents("tr"));
					treeFuncs['upCon'].call(target, target.parents("tr"));
				}
			});

			jq.find(".fieldComB").kendoDropDownList({
				dataTextField : "name",
				dataValueField : "param",
				dataSource : inParamDataSource,
				animation : false
			});

			jq.find(".linkCon").kendoDropDownList({
				dataTextField : "text",
				dataValueField : "value",
				dataSource : [ {
					text : '全部符合',
					value : 'All'
				}, {
					text : '任意符合',
					value : 'Any'
				}, {
					text : '无',
					value : 'None'
				}, {
					text : '排除全部',
					value : 'Not All'
				}, ]
			});

			jq.find(".jCon").kendoDropDownList({
				dataTextField : "text",
				dataValueField : "value",
				dataSource : [ {
					text : '等于',
					value : '='
				}, {
					text : '不等于',
					value : '<>'
				}, {
					text : '大于',
					value : '>'
				}, {
					text : '小于',
					value : '<'
				}, { 
					text : '大于等于',
					value : '>='
				}, {
					text : '小于等于',
					value : '<='
				}, {
					text : '包含',
					value : 'like'
				}, ]
			});
			
			initMenuEvent(jq);
		}
		
		
		function initMenuEvent(jq){//下拉菜单
			
			jq.find(".textbox-menu").on('dblclick',function(){
				var $this = $(this);
				$this.next('.conMenu').show();
				$this.hide();
			}).each(function(){
				$(this).attr({
					readonly : true
				});
			});
			
			var menus = [menu];
			
			jq.find(".conMenu").each(function(){
				var $this = $(this).empty();
				
				createMenu(menus,$this);
				
				$this.kendoMenu({
					select : function(e){
						var $this = $(e.item).parents("ul");
			    	    var textbox = $this.prev('.textbox-menu');
			    	    var item = $(e.item).data("item");
			    	    if(item){
			    	    	if(item.level == 1){//表達式
			    	    		
			    	    	}else if(!item.items){//列
			    	    		$this.hide();
			    	    		textbox.val(item.text).data("item",item);
					    	    textbox.show();
					    	    
					    	    if($(".textbox-menu").length > inParamDataSource.length){
					    	    	increateInParam();
					    	    	var dataSource =  new kendo.data.DataSource({
										  data:inParamDataSource
									});
					    	    	$("#groupConTree").find(".fieldComB").each(function(){
					    	    		var k = $(this).data("kendoDropDownList");
					    	    		if(k)
					    	    			k.setDataSource(dataSource);
					    	    	});
					    	    }
					    	    
			    	    	}
			    	    }
					},
					close : function(e){
						var $li = $(e.item);
						var item = $li.data("item");
						if(item.level == 0){
							var $ul = $li.parents("ul").hide();
				    	    $ul.prev('.textbox-menu').show();
						}
					}
				});
			});
		}

		initEvent($(".tableCon"));
	</script>
</body>
</html>