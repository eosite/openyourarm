<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    request.setAttribute("theme", "default");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_easyui.jsp"%>
<%@ include file="/WEB-INF/views/form/defined/table/select_js.jsp"%>
<style type="text/css">
	.preGrid td{
		border: 1px solid #B3DFEC;
	}
</style>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/layer/skin/layer.ext.css" >
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/layer/extend/layer.ext.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/easyui/treegrid-dnd.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jquery.mtResizable.js"></script>
<script type="text/javascript">
	var height=document.documentElement.clientHeight,width=document.documentElement.clientWidth;
	
	//数据列统计数据源
	//mx/form/defined/getDictByCode?code=?
	var dataColumnStat = [] ;
	$.ajax({
		url : '${pageContext.request.contextPath }/mx/form/defined/getDictByCode',
		data : {code : 'function'},
		type : 'post',
		dataType : 'json',
		async : false,
		success:function(data){ 
			$.each(data,function(i,d){
				var e = {} ;
				e.id = d.code ;
				e.text = d.name
				dataColumnStat.push(e);
			});
		}
	});
	
	var setting = {
		view: {
			showIcon:true,
			showLine:true,
			showTitle:false,
			selectedMulti: false
		},
		async:{
			enable:true,
			url:"${pageContext.request.contextPath}/mx/form/defined/getFormPageTree"
		},
		data:{
			simpleData:{
				enable:true,
				idKey:"id",
				pIdKey:"parentId"
			}
		}
	};

	var treeCount = 0,columnData = [],editingId;
	$(function(){
		$.fn.zTree.init($("#linkpageTree"), setting);  //链接树
		
		$('#win').window({   //关联字段弹窗
			title:"关联字段",
			collapsible:false,
			minimizable:false,
			maximizable:false,
			closed:true,
		    width:300,   
		    height:450,   
		    modal:true  
		}); 
		
		//恢复数据
		var datasId = '${param.datasId}';
		if(datasId != ''  && datasId != 'undefined'){
			var columnTempData = parent.document.getElementById(datasId).value;
			if(columnTempData!==undefined && columnTempData !=="" ){
				var t = JSON.parse(columnTempData);
				$.each(t,function(index,table){
					$.each(table.columns,function(index,field){
						var obj = {};
						obj.columnName=field.columnName;
						obj.columnNameCN=field.title;
						obj.columnId=field.id;
						obj.columnNameTitle=field.tableNameCn+"."+field.title;
						columnData.push(obj);
					});
				});
			}
		}
		
		//表头编辑grid
		$("#tg").treegrid({
			height:398,
			idField:'id',   
		    treeField:'name',
		    lines:true,
		    fitColumns: false,
		    singleSelect : false,
		    toolbar:[
		    	{
		    		text:"确定",
		    		handler:function(){
		    			if("${param.elementId}"!=""){
		    				var nodes = $("#tg").treegrid("getData");
		    				deleteEditorProperty(nodes);//删除editor属性
		    				
		    				if("${param.elementId}" !==""){
		    					var headers = getAllHeaderNames(nodes);
			    				parent.document.getElementById("${param.elementId}").value = headers;
		    				}
		    				if("${param.objElementId}" !==""){
			    				parent.document.getElementById("${param.objElementId}").value = JSON.stringify(nodes);
		    				}
		    				parent.layer.close(parent.layer.getFrameIndex());
		    			}
		    		}
		    	},"-",{
					text:"同级增加",
					handler:function(){
						var node = $("#tg").treegrid("getSelected");
						var parent;
						if(node){
							parent = $("#tg").treegrid("getParent",node.id);
						}
						$("#tg").treegrid("append",{
							parent: parent?parent.id:null,
							data:[{id:++treeCount,name:"新表头_"+treeCount,isHidden:""}]
						});
					}
				},
				{
					text:"下级增加",
					handler:function(){
						var node = $("#tg").treegrid("getSelected");
						if(!node){
							alert("请先选择一个表头后再增加");
							return;
						}
						
						$("#tg").treegrid("append",{
							parent: node.id,
							data:[{id:++treeCount,name:"新表头_"+treeCount,isHidden:""}]
						});
					}
				},{
					text:"删除",
					handler: function(){
						var nodes = $("#tg").treegrid("getSelections");
						if(!nodes.length){
							alert("请先选择一个表头后再删除");
							return;
						}
						for(var i=(nodes.length-1);i>=0;i--){
							var node = nodes[i];							
							if(node){
								$('#tg').treegrid('remove', node.id);
							}
						}
					}
				},{
					text:"自动生成",
					handler: function(){
						//data:[{id:++treeCount,name:"新表头_"+treeCount,isHidden:""}]
						if(!columnData || columnData.length===0){
							alert("请先定义字段后再通过此功能自动生成表头");
							return;
						}
						var data = [];
						for(var i=0,len=columnData.length;i<len;i++){
							var obj = {};
							obj.id = ++treeCount;
							obj.name = columnData[i].columnNameCN,
							obj.isHidden = "";
							obj.columnName = columnData[i].columnName;
							obj.columnNameCN = columnData[i].columnNameCN,
							obj.columnNameTitle = columnData[i].columnNameTitle,
							obj.columnId = columnData[i].columnId,//数据集列主键
							obj.value = columnData[i].columnName;
							data.push(obj);
						}
						$("#tg").treegrid("append",{
							parent: null,
							data:data
						});
					}
				},"-",{
					text:"编辑",
					handler: function(){
						editRow();
					}
				},{
					text:"保存",
					handler: function(){
						saveRow();
					}
				},{
					text:"取消编辑",
					handler: function(){
						$('#tg').treegrid('cancelEdit', editingId);
						editingId = undefined;
					}
				},"-",{
					text:"关联字段",
					handler:function(){
						var nodes = $("#tg").treegrid("getData");
						var allChildren = getAllChildren(nodes);
						$('#win').window('open');
						
						$("#pg").propertygrid({
							height:380,
							data:allChildren,
							columns:[[
					        	{
					        		field:"id",
					        		title:"id",
					        		hidden:true
					        	},
								{
									field:"name",
									title:"表头名称",
									width:150
								},{
									field:"value",
									title:"字段名称",
									width:250,
									formatter:function(value,rowData){
										for(var i=0,len=columnData.length;i<len;i++){
											if(value===columnData[i].columnName){
												rowData.columnNameCN = columnData[i].columnNameCN;
												rowData.columnId = columnData[i].columnId;
												return columnData[i].columnNameTitle;
											}
										}
									}
								}
							]],
							onUncheck: function(rowIndex,rowData){
								var editor = $("#pg").propertygrid("getEditor",{index:rowIndex,field:"value"});
								var text = $(editor.target).combobox("getText");
								alert(text);
							}
						});
					}
				},"-",{
					text:"调整列宽",
					handler:function(){
						var nodes = $("#tg").treegrid("getData");
						createTable(nodes);
					}
				}
			],
		    columns:[[
				{	
					checkbox : true,
					field:'id_'
				},
                {
                	field:'name',
                	title:'表头名称',
                	width:150,
                	editor:{
                		type:'text'
                	}
                },{
                	field:'link',
                	title:'表头样式',
                	width:150,
                	editor:{
                		type:'text'
                	}
                },{
                	field:'columnName',
                	title:'关联字段',
                	width:120,
                	formatter: function(value,rowData){
                		return rowData.columnNameTitle;
                	}
                },{
                	field:'dataColunmStat',
                	title:'数据列统计',
                	width:100,
                	align:'center',
                	editor:{
                		type:'combotree',
                		options:{
                			multiple:true,
                			data:dataColumnStat
                		}
                	},formatter:function(value,rowData){
                		var v = value ;
                		$.each(dataColumnStat,function(i,d){
                			if(v!=null && v!= ""){
	                			v = v.replace(d.id,d.text);
                			}
                		});
                		return v ;
					}
                },{
                	field:'isHidden',
                	title:'是否隐藏',
                	width:80,
                	align:'center',
                	editor:{
                		type:'checkbox',
                		options:{
							on:"√",
							off:""
                		}
                	}
                },{
                	field:'isLock',
                	title:'是否冻结',
                	width:80,
                	align:'center',
                	editor:{
                		type:'checkbox',
                		options:{
							on:"√",
							off:""
                		}
                	}
                },{
                	field:'width',
                	title:'宽度',
                	width:80,
                	align:'center',
                	editor:{
                		type:'text',
                	}
                }
            ]],
            onLoadSuccess: function(row){
            	$(this).treegrid('enableDnd',row?row.id:null);
            },
            onBeforeEdit: function(row){
            	$(this).treegrid('disableDnd');
            },
            onAfterEdit: function(row){
            	$(this).treegrid('enableDnd');
            },
            onCancelEdit: function(row){
            	$(this).treegrid('enableDnd');
            },
            onDblClickRow: function(row){
            	editRow(row.id);
            },
            onStopDrag : function(row){
            	$(this).treegrid('enableDnd',row?row.id:null);
            }
		});
		
		var data = parent.document.getElementById("${param.objElementId}").value;
		if(data!=""){
			var _data = JSON.parse(data);
			treeCount = getMaxTreeId(_data);
			$("#tg").treegrid("loadData",JSON.parse(data));
		}
	});
	
	function getAllHeaderNames(nodes){
		var names = [];
		for(var i=0,len=nodes.length;i<len;i++){
			if(nodes[i].children && nodes[i].children.length>0){
				var tnames = getAllHeaderNames(nodes[i].children);
				for(var j=0;j<tnames.length;j++){
					names.push(tnames[j]);
				}
			}else{
				names.push(nodes[i].name);
			}
		}
		return names;
	}
	
	//保存事件
	function saveRow(){
		if (editingId != undefined){
			var t = $('#tg');
			var node = t.treegrid("getSelected");
			t.treegrid('endEdit', editingId);
			editingId = undefined;
		}
	}
	
	//html表达式
	// html编辑器获取变量方法
	function getVarFn(){
		//获取字典类型FieldTypeControl
		//columnData
		/* var expressionData = [] ;
		for(var i=0;i<selectFieldRows.length;i++){
			var express = {} ;
			var selectFieldRow = selectFieldRows[i];
			express.t = selectFieldRow.title ;
			express.dType = getfieldTypeValue(selectFieldRow.FieldType) ;
			express.code = selectFieldRow.code!=null?selectFieldRow.code.replace("~F{","~V{"):"" ;
			express.value = selectFieldRow.value!=null?selectFieldRow.value.replace("~F{","~V{"):"" ;
			express.name = selectFieldRow.title ;
			expressionData.push(express);
		}
		return expressionData ; */
		return null;
	}
	// html编辑器获取参数方法
	function getParamFn(){
		//这里传递页面之间 或者联动的参数
	}
	// html编辑器回调函数
	function retHtmlFn(data){
		var row = $('#tg').treegrid('getSelected');
		
		row.link = data.htmlVal;
		row.htmldata = JSON.stringify(data),
		$('#tg').treegrid('refresh',editingId);
		$("#tg").treegrid('endEdit', editingId);
		/* if(data){
			$('#linkImgGrid').datagrid('endEdit',gindex);
			$('#linkImgGrid').datagrid('updateRow',{index:gindex,row:{htmldata:JSON.stringify(data),
				htmlExpression:data.htmlVal}});
		} */
	}
	// html编辑器回显内容 
	function initHTMLFn(){
		//alert(1);
		var rows = $('#tg').treegrid('getData');
		if(rows){
			var row = rows[editingId-1];
			if(row){
				//var obj = {};
				//obj.varVal = row.htmlMap;
				//obj.htmlVal = row.htmlExpression ;
				return JSON.parse(row.htmldata);
			}
		}
	}
	//html 编辑器需要的字段
	function getHtmlFn(){
		//return columnData ;
	}
	
	//编辑事件
	function editRow(rowId){
		if (editingId != undefined){
			saveRow();
		}
		var row = $('#tg').treegrid('getSelected');
		if (row){
			editingId = row.id
			$('#tg').treegrid('beginEdit', editingId);
			var editor = $("#tg").treegrid("getEditor",{index:row.id,field:"link"});
			//链接文本框事件
			editor.target.bind("dblclick",function(){
				var htmlUrl = "${pageContext.request.contextPath}/mx/html/editor/view?retFn=retHtmlFn&"+
					"getFieldFn=getHtmlFn&getVarFn=getVarFn&getParamFn=getParamFn&initHTMLFn=initHTMLFn";
				window.open(htmlUrl);
				/* $.layer({
					type: 1,
					maxmin: true,
					shadeClose: true,
					title: "请选择页面",
					closeBtn: [0, true],
					shade: [0, '#000'],
					border: [2, 0.3, '#000'],
					fadeIn: 100,
					area: ["300px", "450px"],
					btns: 2,
					btn: ['确定', '取消'],
					page:{
						dom:"#linkpage_select_content"
					},
					yes: function(index){
						var treeObj = $.fn.zTree.getZTreeObj("linkpageTree");
						var nodes = treeObj.getSelectedNodes();
						if(nodes.length==0){
							layer.alert("请先选择链接页面！",3);
							return;
						}else{
							var node = nodes[0];
							row.link = node.id;
							row.linkPageName=node.name;
							
							$('#tg').treegrid('refresh',row.id);
							$("#tg").treegrid('endEdit', editingId);
							editingId = undefined;
							layer.close(index);
						}
					}
				}); */
			});
		}
	}
	
	//删除editor属性,editor属性为配置时需要，在配置时自动添加，不需要返回结果
	function deleteEditorProperty(nodes){
		for(var i=0,len=nodes.length;i<len;i++){
			if(nodes[i].children && nodes[i].children.length>0){
				deleteEditorProperty(nodes[i].children);
			}else{
				delete nodes[i].editor;
			}
		}
	}
	
	//保存字段与表头的绑定
	function saveColumnBind(){
		var data = $("#pg").propertygrid("getData").rows;
		for(var i=0,len=data.length;i<len;i++){
			var row = $("#tg").treegrid("find",data[i].id);
			row.columnName = data[i].value;
			row.columnNameCN = data[i].columnNameCN;
			row.columnId = data[i].columnId;
			$("#tg").treegrid("refresh",row.id);
		}
		$("#win").window("close");
	}
	
	//获取叶子节点，返回JSON对象数组
	function getAllChildren(nodes){
		var allChildren = [];
		var editor = {
				type:"combobox",
				options:{
					valueField:"columnName",
					textField:"columnNameTitle",
					panelHeight:"auto",
					data:columnData
				}
		}
		
		for(var i=0,len=nodes.length;i<len;i++){
			if(nodes[i].children && nodes[i].children.length>0){
				var temp = getAllChildren(nodes[i].children);
				for(var j=0,len2=temp.length;j<len2;j++){
					//temp[j].value="";
					temp[j].editor=editor;
					allChildren.push(temp[j]);
				}
			}else{
				//nodes[i].value="";
				nodes[i].editor=editor;
				allChildren.push(nodes[i]);
			}
		}
		return allChildren;
	}
	
	//获取所有节点中最大的id值
	function getMaxTreeId(data){
		var maxid = 0;
		for(var i=0,len=data.length;i<len;i++){
			if(data[i].children && data[i].children.length>0){
				maxid = getMaxTreeId(data[i].children);
			}else{
				maxid = maxid>data[i].id?maxid:data[i].id;
			}
		}
		return maxid;
	}
	
	//预览创建表格
	var maxLevelNum = 0;
	function createTable(childrenNodes){
		var preview = document.getElementById("preview");
		preview.innerHTML="";
		$(preview).css("height","100%");
		
		var table = document.createElement("table");
		table.border="0";
		table.cellspacing="1";
		table.cellpadding="0";
		table.width="100%";
		// $(table).css("height","100%");
		table.style.backgroundColor="#ccc;";
		var row = document.createElement("tr");
		row.style.backgroundColor="#FFF;";
		table.appendChild(row);

		$(table).attr('cellspacing','0');
		$(table).attr('cellpadding','0');
		$(table).addClass("preGrid");
		
		maxLevelNum = getLastLevelNum(childrenNodes)+1;//获取最深一层的层级 
		
		createTD(table,row,childrenNodes);
		
		preview.appendChild(table);

		var $ele = $(table).find("td");

		// var data = $("#tg").treegrid("getData");

		var $col;
		var childrenNodes = childrenNodes;
		//增加拖拽效果
		var resizabled = new mtResizable($ele, {
			hint: function(handle) {
				return $('<div class="mt-resize-indicator" />').css({
					height: $(table).height() + 20,
					top : $(table).offset().top-10,
				});
			},
			start: function(e) {
				$(table).attr("onselectstart","return false;").css({
					"user-select": "none"
				});
				$col = $(this);
			},
			move: function(e) {

			},
			end: function(e) {
				var width = this.location.oldWidth + (e.pageX - this.location.oldX);
				if (width < 10) {
					$col.width(10);
				} else {
					$col.width(width);
				}

				var totalWidth = $(table).width() * 1.0;

				var $originalParent = getOriginalParent($col);
				var headerWidth = ($originalParent.width() / totalWidth) * 100;
				headerWidth = headerWidth.toFixed(2);
				var gridId = $originalParent.attr("gridId");
				$.each(childrenNodes,function(i,item){
					if(item.id == gridId){
						item.width = headerWidth + "%";
						return false;
					}
				})

				$("#tg").treegrid("refreshRow",gridId);

				$(table).removeAttr('onselectstart').css({
					"user-select": ""
				});
			}
		});
	}

	function getOriginalParent($td){
		var parentGridId = $td.attr("parentGridId");
		if(parentGridId){
			return getOriginalParent($("#preview").find("[gridId='"+parentGridId+"']"));
		}else{
			return $td;
		}
	}
	
	//预览创建单元格
	function createTD(table,row,childrenNodes,parentNode){
		if(childrenNodes && childrenNodes.length>0){
			for(var i=0;i<childrenNodes.length;i++){
				var children = childrenNodes[i].children;
				if(children && children.length>0){
					if(!Boolean(childrenNodes[i].isHidden)){
						var childrenNum = getLastChildrenNum(children);
						var col = document.createElement("td");
						col.align="center";
						col.colSpan = childrenNum;
						
						//如果有链接，添加链接
						if(childrenNodes[i].link && childrenNodes[i].link !==""){
							var a = document.createElement("a");
							a.href="#";
							a.innerText = childrenNodes[i].name;
							col.appendChild(a);
						}else{
							col.innerText = childrenNodes[i].name;
						}
						if(childrenNodes[i].width){
							$(col).css("width",childrenNodes[i].width);
						}

						if(parentNode){
							$(col).attr("parentGridId",childrenNodes[i].id);
						}
						$(col).attr("gridId",childrenNodes[i].id);
						row.appendChild(col);
						
						var trow = table.rows[$("#tg").treegrid("getLevel",children[0].id)-1];
						if(!trow){
							trow = document.createElement("tr");
							trow.style.backgroundColor="#FFF;";
							table.appendChild(trow);
						}
						createTD(table,trow,children,childrenNodes[i]);
					}
				}else{
					if(!Boolean(childrenNodes[i].isHidden)){
						var col = document.createElement("td");
						col.align="center";
						
						var level = $("#tg").treegrid("getLevel",childrenNodes[i].id);
						col.rowSpan = (maxLevelNum-level);
						
						//如果有链接，添加链接
						if(childrenNodes[i].link && childrenNodes[i].link !==""){
							var a = document.createElement("a");
							a.href="#";
							a.innerText = childrenNodes[i].name;
							col.appendChild(a);
						}else{
							col.innerText = childrenNodes[i].name;
						}

						if(childrenNodes[i].width){
							$(col).css("width",childrenNodes[i].width);
						}

						if(parentNode){
							$(col).attr("parentGridId",childrenNodes[i].id);
						}
						$(col).attr("gridId",childrenNodes[i].id);
						row.appendChild(col);
					}
				}
			}
		}
	}
	
	//获取最深一层叶子节点总数
	function getLastChildrenNum(childrenNodes){
		var childrenNum = 0;
		if(childrenNodes && childrenNodes.length>0){
			for(var i=0;i<childrenNodes.length;i++){
				var children = childrenNodes[i].children;
				if(children && children.length>0){
					childrenNum += getLastChildrenNum(childrenNodes[i].children);
				}else{
					if(!Boolean(childrenNodes[i].isHidden)){
						childrenNum++;
					}
				}
			}
		}
		return childrenNum;
	}
	
	//获取最深一层叶子节点层级
	function getLastLevelNum(childrenNodes){
		var maxLevel = 0;
		if(childrenNodes && childrenNodes.length>0){
			for(var i=0;i<childrenNodes.length;i++){
				var tlevel = 0;
				var children = childrenNodes[i].children;
				if(children && children.length>0){
					tlevel = getLastLevelNum(childrenNodes[i].children);
				}else{
					if(!Boolean(childrenNodes[i].isHidden)){
						tlevel = $("#tg").treegrid("getLevel",childrenNodes[i].id);
					}
				}
				
				if(tlevel>maxLevel) maxLevel = tlevel;
			}
		}
		return maxLevel;
	}

</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north'" style="height:400px">
		<table id="tg"></table>
	</div>
	<div data-options="region:'center'">
		<div id="preview"></div>
	</div>
	<div id="linkpage_select_content" style="overflow:auto;width:300px;height: 370px;display: none">
		<ul id="linkpageTree" class="ztree"></ul>
	</div>
	<div id="win">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false">
				<table id="pg"></table>
			</div>
			<div data-options="region:'south',border:false" style="height:30px" align="right">
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="javascript:saveColumnBind();">保存</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#win').window('close');">取消</a>
			</div>
		</div>
	</div>
</body>
</html>