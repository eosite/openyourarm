(function($){
	$.extend({
		log : function(msg){
			console.log(msg);
		}
	});
	
})(jQuery);




var MoveTable= {
	errorMsg: "放错了...请选择正确的类别！",
	curTarget: null,
	curTmpTarget: null,
	noSel: function() {
		try {
			window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
		} catch(e){}
	},
	dragTree2Dom: function(treeId, treeNodes) {
		return !treeNodes[0].isParent;
	},
	prevTree: function(treeId, treeNodes, targetNode) {
		return !targetNode.isParent && targetNode.parentTId == treeNodes[0].parentTId;
	},
	nextTree: function(treeId, treeNodes, targetNode) {
		return !targetNode.isParent && targetNode.parentTId == treeNodes[0].parentTId;
	},
	innerTree: function(treeId, treeNodes, targetNode) {
		return targetNode!=null && targetNode.isParent && targetNode.tId == treeNodes[0].parentTId;
	},
	//移动到目标DOM样式变更
	dragMove: function(e, treeId, treeNodes) {
		var p = null, pId = 'targetDom';
		if (e.target.id == pId) {
			p = $(e.target);
		} else {
			p = $(e.target).parent('#' + pId);
			if (!p.get(0)) {
				p = null;
			}
		}
		$('.domBtnDiv.active').removeClass('active');
		if (p) {
			p.addClass('active');
		}
	},
	//移动到目标处理
	dropTree2Dom: function(e, treeId, treeNodes, targetNode, moveType) {
		var domId = "targetDom";
		if (moveType == null && (domId== e.target.id || $(e.target).parents("#" + domId).length > 0)) {
			var newDom = $('#tbl_'+treeNodes[0].tableName);
			if (newDom.length > 0) {
				alert("已选择数据表："+treeNodes[0].tableNameCN);
			} else {
				var targetObj = $("#" + domId);
				var node = treeNodes[0];
				createDataTable({
					tableId : node.tableId || node.id,
					tableName : treeNodes[0].tableName,
					tableNameCN : treeNodes[0].tableNameCN,
					tableType : $("#select-type").data('kendoDropDownList').dataItem().code,
					R : node.R
				},$select_biaoduan.dataItem(),targetObj,e.clientX,e.clientY, node);
			}
		} else if ( $(e.target).parents(".domBtnDiv").length > 0) {
			alert(MoveTable.errorMsg);
		}
		$("#"+domId).removeClass('active');
	},
	dom2Tree: function(e, treeId, treeNode) {
		var target = MoveTable.curTarget, tmpTarget = MoveTable.curTmpTarget;
		if (!target) return;
		var zTree = $.fn.zTree.getZTreeObj("fieldTree"), parentNode;
		if (treeNode != null && treeNode.isParent && "dom_" + treeNode.id == target.parent().attr("id")) {
			parentNode = treeNode;
		} else if (treeNode != null && !treeNode.isParent && "dom_" + treeNode.getParentNode().id == target.parent().attr("id")) {
			parentNode = treeNode.getParentNode();
		}

		if (tmpTarget) tmpTarget.remove();
		if (!!parentNode) {
			var nodes = zTree.addNodes(parentNode, {id:target.attr("domId"), name: target.text()});
			zTree.selectNode(nodes[0]);
		} else {
			target.removeClass("domBtn_Disabled");
			target.addClass("domBtn");
			alert(MoveTable.errorMsg);
		}
		MoveTable.curTarget = null;
		MoveTable.curTmpTarget = null;
	},
	bindDom: function() {
		$(".domBtnDiv").bind("mousedown", MoveTable.bindMouseDown);
	},
	bindMouseDown: function(e) {
		var target = e.target;
		if (target!=null && target.className=="domBtn") {
			var doc = $(document), target = $(target),
			docScrollTop = doc.scrollTop(),
			docScrollLeft = doc.scrollLeft();
			target.addClass("domBtn_Disabled");
			target.removeClass("domBtn");
			curDom = $("<span class='dom_tmp domBtn'>" + target.text() + "</span>");
			curDom.appendTo("body");

			curDom.css({
				"top": (e.clientY + docScrollTop + 3) + "px",
				"left": (e.clientX + docScrollLeft + 3) + "px"
			});
			MoveTable.curTarget = target;
			MoveTable.curTmpTarget = curDom;

			doc.bind("mousemove", MoveTable.bindMouseMove);
			doc.bind("mouseup", MoveTable.bindMouseUp);
			doc.bind("selectstart", MoveTable.docSelect);
		}
		if(e.preventDefault) {
			e.preventDefault();
		}
	},
	bindMouseMove: function(e) {
		MoveTable.noSel();
		var doc = $(document), 
		docScrollTop = doc.scrollTop(),
		docScrollLeft = doc.scrollLeft(),
		tmpTarget = MoveTable.curTmpTarget;
		if (tmpTarget) {
			tmpTarget.css({
				"top": (e.clientY + docScrollTop + 3) + "px",
				"left": (e.clientX + docScrollLeft + 3) + "px"
			});
		}
		return false;
	},
	bindMouseUp: function(e) {
		var doc = $(document);
		doc.unbind("mousemove", MoveTable.bindMouseMove);
		doc.unbind("mouseup", MoveTable.bindMouseUp);
		doc.unbind("selectstart", MoveTable.docSelect);
		var target = MoveTable.curTarget, tmpTarget = MoveTable.curTmpTarget;
		if (tmpTarget) tmpTarget.remove();

		if ($(e.target).parents("#fieldTree").length == 0) {
			if (target) {
				target.removeClass("domBtn_Disabled");
				target.addClass("domBtn");
			}
			MoveTable.curTarget = null;
			MoveTable.curTmpTarget = null;
		}
	},
	bindSelect: function() {
		return false;
	}
};

var dTypeGlobal = {
		varchar : "string",
		i4 : 'int',
		r8 : 'int'
};

function getFieldByTableId(tableObj){
	tableObj.result = null;
	$.ajax({
        url: contextPath + "/rs/isdp/cellDataField/selectFieldByTableId",
        type: "POST",
        dataType:"JSON",
        async : false,
        data:{
        	tableId:tableObj.tableId,
    		type:tableObj.tableType || 99,
    		tableName:tableObj.tableName,
    		systemName : tableObj.systemName,
    		R : tableObj.R
        },
        success : function(data){
        	tableObj.result = data;
        }
	});
	return tableObj;
}

function getDataSetFields(dataSetObj){
	dataSetObj.result = null;
	$.ajax({
        url: contextPath + "/mx/dataset/getDataSetFields",
        type: "POST",
        dataType:"JSON",
        async : false,
        data:{
        	id : dataSetObj.indexId
        },
        success : function(data){
        	dataSetObj.result = data;
        }
	});
	return dataSetObj;
}

/**
 * 只获取指定的列
 */
var GetFieldsRow = (function($){
	var fields = ["tableName","tableNameCN",//
	              "dname", "fname", "dtype", "strlen", "lenstr"];
	return function(row, n){
		if(n){
			var ret = {};
			$.each(fields, function(i, v){
				ret[v] = row[v];
			});
			return ret;
		} else {
			return row;
		}
	};
})(jQuery);

function mtToLowerCase(object, key){
	if(!object || !key){
		return null;
	}
	object[key] = object[key] ? object[key].toLowerCase() : object[key];
}


$(function(){
	$(document).on("click.table.a-view", "table.bordered-ds a.a-view"//
			, function(e){
		e.preventDefault();
		var datasetid = $(this).closest("table.bordered-ds").attr("datasetid");
		window.open(contextPath + "/mx/dataset/sqlbuilder?id=" + datasetid);
	});
});

function createDataTable(tableObj,dataSource,targetObj,x,y, node){
	
	var data, data99;
	var newVer = window.DataSetMetadata && DataSetMetadata.newVer && DataSetMetadata.newVer();
	if(dataSource){
		tableObj.systemName = dataSource.code;
	}
	
	if(newVer){
		mtToLowerCase(tableObj, "tableName");
	}
	
	if(node && node.ds){ //数据集
		if(node.ds == 'T'){
			
			if(dataSet && dataSet.id == node.indexId){
				alert("当前数据集不能递归使用!");
				return false;
			}
			
			$.extend(tableObj, {
				tableNameCN : node.indexName,
				tableName : "ds_" + node.indexId,
				indexId : node.indexId
			});
			
			if(window.DataSetMetadata && DataSetMetadata.isNew()){
				tableObj.tableName = DataSetMetadata.getTableAlias(tableObj);
			}
			
			data = getDataSetFields(node).result;
		} else {
			alert("请选择数据集!");
			return false;
		}
	} else {
		data = getFieldByTableId(tableObj).result,params = $.extend({},tableObj);
		params.tableType = 99;
		data99 = getFieldByTableId(params).result;
	}
	if((data && data.rows &&  data.rows.length > 0) || //
			(data99 && data99.rows && data99.rows.length > 0)){
		var toolbarspan = {
				Maximize : {
					html : "<span role='presentation' class='k-icon k-i-maximize'>Maximize</span>",
					tdpadding : '3px',
					tablewidth : 200,
					trheight : "30px",
					opo : 'Restore'
				} ,
				Restore : {
					html : "<span role='presentation' class='k-icon k-i-restore'>Restore</span>",
					tdpadding : 0,
					tablewidth : 150,
					trheight : "8px",
					opo : 'Maximize'
				},
				Close : {
					html : "<span role='presentation' class='k-icon k-i-close'>Close</span>"
				}
		},css = toolbarspan.Restore;
		
		
		var tr = ["<thead><tr class='tr-cls'><th colspan=2 class='td-cls' >",
		    "<input type='checkbox' class='head-chk' ><a class='a-view' href='javascript:void(0)'>",
          	tableObj.tableNameCN,
          	"</a>"];
		if(tableObj.indexId != undefined){
			tr.push("<button onclick=\"Glayer('"+tableObj.indexId+"')\" style='background-color:#DCE999;border:0px'>信息</button>");
		} 
		tr.push("<div class='k-window-actions' style='float:right;'>",
    	"<a role='button' href='#' class='k-window-action k-link'>",
		toolbarspan.Close.html,
	    "</a>",
       " </div>",
	"</th></tr><tr><th colspan=2 class='td-cls' ><input title='动态表名参数' type='text' data-model='tb_params' class='tb_params' readonly=true ></th></tr></thead><tbody>"
       );
		var trId,dnames = {};
		
		function initRows(rows){
			$.each(rows,function(i,row){
				var __dname = row.dname.toLowerCase();
				if(newVer){
					mtToLowerCase(row, "dname");
					//mtToLowerCase(row, "fname");
				}
				if(!dnames[__dname]){
					trId = tableObj.tableName +  "_tr_" + row.dname;
					chkId = tableObj.tableName +  "_chk_" + row.dname;
					if(row.dtype && (row.dtype in dTypeGlobal))
						row.dtype = dTypeGlobal[row.dtype];
					
					tr.push("<tr class='field' style='height:" + css.trheight + ";padding:'"+css.tdpadding+"' position:relative;' id='" + trId + "' fieldjson='" + JSON.stringify(GetFieldsRow(row, true)) +"' >");
					
					tr.push("<td class='td-cls start' style='width:20px;' ><input class='field-chk' type='checkbox' id='" + chkId + "' value='"+ row.dname +"' /></td>>");
					
					tr.push("<td class='td-cls fname end'>"+ row.fname +"</td>");
					
					tr.push("</tr>");
					dnames[__dname] = row;
				}
			});
		}
		
		if(data && data.rows && data.rows.length > 0){
			initRows(data.rows);
		}
		
		if(data99 && data99.rows && data99.rows.length > 0){
			initRows(data99.rows);
		}
		
		dnames = null;
		tr.push("</tbody><tfoot><tr class='tr-cls'><th colspan='2' >" + tableObj.tableName + "</th></tr></tfoot>");
		
		
		var $div = $('<div>',{
			class : 'div-cls',
			style : "width:auto;height:auto;position:absolute;overflow:auto; ",
		}).css({
			top: y-100,
		    left:x-350
		});
		
		var cls = "tbl-cls bordered";
		
		if(node.ds){
			cls += " bordered-ds"
		}
		
		var $table = $("<table>",{
			id : "tbl_" + tableObj.tableName,
			dataSetId : tableObj.indexId,
			tableId : tableObj.tableId,
			tableName : tableObj.tableName,
			nameCN : tableObj.tableNameCN,
			tableType : tableObj.tableType || 99,
			class : cls,
			R : tableObj.R
		}).css({
			width :css.tablewidth
		}).append(tr.join('')).appendTo($div.resizable());
		
		/**
		 * 数据集参数同步
		 */
		if(data.params && data.params.length){
			var __params = [], keys = ["name", "param", //
			     "prepared"];
			$.each(data.params, function(i, v){
				var p = {};
				$.each(keys, function(){
					p[this] = v[this];
				});
				__params.push(p);
			});
			window.addParams(__params);
		}
		
		$table.data("dataSource",dataSource);//标段信息
		
		$div.on('dblclick.div.jsplumb',function(e){
			var tn = $(e.target).closest("table").attr("tableName");
			openOperatorWin(function(t){
				var $this = t.wrapper.find("[data-role=dropdownlist]"),
        		id = $this.attr("id"), index = id.replace("drop-table-",""),tableName;
        		if(t.dataItem())
        			tableName = t.dataItem().code;
				if(index == 0){
					tableName = tn;
					t.value(tn);
				}
				setColumns(index, tableName);
			}).data("defined",true);
		}).appendTo($('#targetDom'));
		
		$div.on('dblclick.div.jsplumb', "table thead input.tb_params", function(e){
			return !!openExpress(this);
		});
		
		//JP.batch($table.find('.field'));
		
		var st = $table.find('.field');
		
		JP.initBatch(st,st);
		
		function toolbarFuncs(){
			var K = this;
			
			this.Close = function(){
				$(this).on('click',function(){
					if(window.confirm("确定删除吗?")){
    					JP.deleteEndpointByContainer($div);
    					$div.remove();
					}
					return false;		
				});
			};
			
			this.RestoreOrMaximize = function(type){
				
				var obj = toolbarspan[type];
				$(this).on('click',function(){
					$table.find("th,td").css({
						padding : obj.tdpadding
					});
					$table.css({
						width : obj.tablewidth
					}).find("tbody tr").css({
						height : obj.trheight
					});
					$(this).unbind().html(toolbarspan[obj.opo].html);
					//K.rebuild();
					K[type].call(this);
					return false;
				});
			};
			
			this.Restore = function(){
				K.RestoreOrMaximize.call(this,"Maximize");
			};
			
			this.Maximize = function(){
				K.RestoreOrMaximize.call(this,"Restore");
			};
			
			this.rebuild = function(){
				JP.batch($table.find('.field'),true);
			};
			
		};
		
		$table.find("thead tr").each(function(){
			var text,$this = $(this);
			$this.find(".head-chk").on('click',function(){
    			var chk = this;
				$table.find(".field-chk").each(function(){
					this.checked = chk.checked; 
				});
    		});
			
			$this.find('div a').each(function(){
				text = $(this).text();
				var tf = new toolbarFuncs();
				tf[text].call(this);
			});
		});
		
	} else {
		alert('未找到表结构信息!');
	}
	
	
	/*$.ajax({
        url: contextPath + "/rs/isdp/cellDataField/selectFieldByTableId",
        type: "POST",
        dataType:"JSON",
        async : false,
        data:{
        	tableId:tableObj.tableId,
    		type:tableObj.tableType || 99,
    		tableName:tableObj.tableName
        },
        success : function(data){
        	
        }
	});*/
}

function createDataTableNEW(tableId,tableName,targetObj,x,y){
     $.ajax({
		url : contextPath + "/mx/dataset/bulider/getTableColumn",
		type : "post",
		async : false,
		dataType : "json",
		data : {
			tableId : tableId,
		},
		success : function(data) {
			if (data) {
				if(data.columnJson!=null)
				{
				     //生成表格控件
				     //targetObj.append("<table id='"+tableId+"'><tr><td><input type='checkbox' id='"+tableId+"' value='数据列1'></td><td>数据列1</td><td>数值型</td></tr></table>");
				     var tableContainer=$("<div></div>");
				     tableContainer.attr('id','container_'+tableId);
				     tableContainer.appendTo(targetObj);
				     $("#container_"+tableId).kendoWindow({
				           position: {
							    top: y-100,
							    left:x-125
							  },
						  actions: ["Refresh","Minimize", "Maximize", "Close"],
						  animation: {
						    open: true
						  },
                          close: function() {
                                $('#container_'+tableId).remove();
                           },		  
						  title:"<div style='width:100%;font-family:微软雅黑;font-size:13px;text-align:center;'><h3>"+tableName+"</h3></div>",
						  visible: true,
						  resizable:true,
						  width: 250,
						  height:200
						});
						$("#container_"+tableId).data("kendoWindow").open();
						
						var tablecontainer = ["<table id='"+tableId+"'>"];
						var trId = tableId + "_tr";
						tablecontainer.push("<tr id='"+ trId +"' class='field' ><td class='start-cls' >o</td><td><input type='checkbox' id='"+tableId+"_chk' value='"+tableName+".数据列1'></td>");
						tablecontainer.push("<td>"+tableName+".数据列2</td><td>数值型</td>");
						tablecontainer.push("<td class='end-cls' >o</td></tr>");
						
						tablecontainer.push("</table>");
						
						var dialog = $("#container_"+tableId).data("kendoWindow");
						
                        dialog.content(tablecontainer.join(''));
					}
			}
		},
		error : function() {
		
		}

	});
}


var getDataSets = (function(){
	return function(parentNode){
		if(!parentNode){
			return window.dataSetTrees;
		} else {
			return [];
		}
	};
})();

var fieldTree;
var utableTreeUrl = contextPath + "/rs/isdp/cellUTableTree/getUtableTreeByTableTypeInit?1=1";
var cellUrl = contextPath + "/rs/isdp/treeDot/getTreeDotInit?1=1";// getTreeDotInit
var setting = {
	edit: {
		enable: true,
		showRemoveBtn: false,
		showRenameBtn: false,
		drag: {
			prev: MoveTable.prevTree,
			next: MoveTable.nextTree,
			inner: MoveTable.innerTree
		}
	},
	async:{
		enable:true,
		url:utableTreeUrl,
		autoParam:["indexId","nlevel=level"],
		dataFilter : function(treeId, parentNode, responseData){
			
			var datas = $.extend(true, [], window.getDataSets(parentNode));
			
			if(datas && datas.length){
				$.each(datas, function(i, node){
					if(node.ds == "F"){ //数据集目录
						node.icon = contextPath + '/images/folder-horizontal.png'; 
						node.iconOpen = contextPath + '/images/folder-horizontal-open.png'; 
						node.iconClose = contextPath + '/images/folder-horizontal.png';
					} else  if(node.ds == 'T'){//数据集
						node.icon = contextPath + '/images/blue-documents-stack.png'; 
						node.tableName = node.indexId;
					}
					node.name = node.indexName;
				});
			}
			
			if(responseData && responseData.length ){
				var tableName;
				$.each(responseData, function(i, v){
					if((tableName = v.tableName )&& tableName.toLowerCase().indexOf("log_") == 0){
						return false;
					}
					if(!tableName){
						v.icon = contextPath + '/images/blue-folder-horizontal.png'; 
						v.iconOpen = contextPath + '/images/blue-folder-horizontal-open.png'; 
						v.iconClose = contextPath + '/images/blue-folder-horizontal.png';
					}
					datas.push(v);
				});
			}
			return datas;
		}
	},
	data: {
		simpleData:{
			enable:true,
			idKey:"indexId",
			pIdKey:"parentId"
		},
		key:{
			name:"indexName",
			title : 'tableName'
		}
	},
	callback: {
		beforeAsync: zTreeBeforeAsync,
	    beforeClick : beforeClick,
		beforeDrag: MoveTable.dragTree2Dom,
		onDrop: MoveTable.dropTree2Dom,
		onDragMove: MoveTable.dragMove,
		onMouseUp: MoveTable.dom2Tree
	},
	view: {
		selectedMulti: false,
		fontCss: function(treeId, treeNode) {
			return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
		}
	}
};

var tmp = new Object();
//单击回调事件
function beforeClick(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	//如果是父节点单击则展开，如果是叶子节点单击则在输入框中插入相应的表达式
	if (treeNode.isParent) {
		zTree.expandNode(treeNode);
		return false;
	}
	$("#tb-span").text(treeNode.tableName);
}

function zTreeBeforeAsync(treeId, treeNode){
	//console.log(treeNode);
}

//重新加载zTree事件
function initZTree(tableType,ds){
	var zTreeObj = $("#fieldTree");
	if(tableType==1){
		setting.async.url = utableTreeUrl+"&type=1&tableType=2&systemName="+ds;
    	$.fn.zTree.init(zTreeObj, setting); 
	}else if(tableType==2)	{
		setting.async.url = cellUrl+'&systemName='+ds;
    	$.fn.zTree.init(zTreeObj, setting); 
	}else if(tableType==3){
		setting.async.url = utableTreeUrl+"&type=1&tableType=12&systemName="+ds;
    	$.fn.zTree.init(zTreeObj, setting); 
	}else if(tableType==4){
		setting.async.url = utableTreeUrl+"&type=4&tableType=12&systemName="+ds;
		$.fn.zTree.init(zTreeObj, setting,[{'indexName':'系统内置表','parentId':-1,'indexId':1,id:'1|',isParent:true}]); 
	}else if(tableType==5){
		$.fn.zTree.init(zTreeObj, setting,[{'indexName':'自动生成表','parentId':-1,'indexId':-1,id:'1|'}]); 
	}
	$("#searchText").data("nodes", null);
}

function searchProject(){
	
	var searchTextBox = $("#searchText"),val = searchTextBox.val();
	
	var f = fieldTree.expandAll(false), tmp = {};
	
	function updateHighlight(ns,flag){
		$.each(ns, function(i,node){
			node.highlight = flag || false;
			fieldTree.updateNode(node);
		});
	}
	
	function getNodesLike(value, collection){
		if(value && (value = $.trim(value))){
			$.each(["name", "tableName"], function(){
				var ns = fieldTree.getNodesByParamFuzzy(this, value, null);
				if(ns && ns.length){
					$.each(ns, function(){
						if(!this.isParent && !tmp[this.id]){							
							if(this.indexName && this.indexName.indexOf(this.tableName) < 0){
								this.indexName += " [" + this.tableName + "]";
							}
							collection.push(this);
							tmp[this.id] = this;
						}
					});
				}
			});
		}
	}
	
	if(!f){
		setTimeout(function(){
			var ns = searchTextBox.data('nodes');
			if(ns && ns[0]){
				updateHighlight(ns, false);
			}
			if(val){
				nodes = [];
				if(val.indexOf(",") > 0){
					$.each(val.split(","), function(){
						getNodesLike(this, nodes);
					});
				} else {
					getNodesLike(val, nodes);
				}
				if(nodes && nodes[0]){
					$.each(nodes,function(i,node){
						if(!node.isParent){
							node = node.getParentNode();
						}
						fieldTree.expandNode(node, true, true, false);
					});
					updateHighlight(nodes, true);
					searchTextBox.data('nodes', nodes);
					searchTextBox.data("n-index", 0);
					window.setTimeout(function(){
						window.selectNode();
					}, 500);					
				}
			}
			
		}, 500);
	}
}
function Glayer($id){
	$.layer({
		  type: 2,
		  title: '数据集',
		  shadeClose: true,
		  shade: 0.8,
		  area: ['70%', '90%'],
		  iframe: {  
		        src: contextPath + '/mx/dataset/sqlbuilder?id='+$id  
		   }
	}); 
}
function selectNode(){
	var $o = $("#searchText");
	var nodes = $o.data("nodes"), index = ($o.data("n-index") || 0)*1;
	if(nodes && nodes.length){
		if(index >= nodes.length){
			index = nodes.length - 1;
			$o.data("n-index", -1);
		}
		var node = nodes[index*1];
		fieldTree.selectNode(node);
		
		$("#tb-span").text(node.tableName);
	}
}

var data;
var fieldzNodes;
