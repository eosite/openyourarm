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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css" >
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/layer/skin/layer.ext.css" >
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/layer/extend/layer.ext.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/easyui/treegrid-dnd.js"></script>
<script type="text/javascript">
	var paramElementId = '${param.paramElementId}';
	var params = window.parent.$('#'+paramElementId).val() ; //控件输入参数
	var height=document.documentElement.clientHeight,width=document.documentElement.clientWidth;
	
	// var setting = {
	// 	view: {
	// 		showIcon:true,
	// 		showLine:true,
	// 		showTitle:false,
	// 		selectedMulti: false
	// 	},
	// 	async:{
	// 		enable:true,
	// 		url:"${pageContext.request.contextPath}/mx/form/defined/getFormPageTree"
	// 	},
	// 	data:{
	// 		simpleData:{
	// 			enable:true,
	// 			idKey:"id",
	// 			pIdKey:"parentId"
	// 		}
	// 	}
	// };
	var treeCount = 0,columnData = [],editingId;
	$(function(){
		//debugger;
		// $.fn.zTree.init($("#linkpageTree"), setting);  //链接树
		
		// $('#win').window({   //关联字段弹窗
		// 	title:"关联字段",
		// 	collapsible:false,
		// 	minimizable:false,
		// 	maximizable:false,
		// 	closed:true,
		//     width:300,   
		//     height:450,   
		//     modal:true  
		// }); 
		
		//表头编辑grid
		$("#tg").treegrid({
			height:564,
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
		    				debugger;
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
						var val = ++treeCount;
						$("#tg").treegrid("append",{
							parent: parent?parent.id:null,
							data:[{id:val,index:val,isHidden:""}]
						});
					}
				},
				// {
				// 	text:"下级增加",
				// 	handler:function(){
				// 		var node = $("#tg").treegrid("getSelected");
				// 		if(!node){
				// 			alert("请先选择一个表头后再增加");
				// 			return;
				// 		}
						
				// 		$("#tg").treegrid("append",{
				// 			parent: node.id,
				// 			data:[{id:++treeCount,name:"新表头_"+treeCount,isHidden:""}]
				// 		});
				// 	}
				// },
				{
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
				},"-",{
					text:"编辑",
					handler: function(){
						editRow();
					}
				},{
					text:"取消编辑",
					handler: function(){
						$('#tg').treegrid('cancelEdit', editingId);
						editingId = undefined;
					}
				},{
					text:"保存",
					handler: function(){
						saveRow();
					}
				}
			],
		    columns:[[
				{	
					checkbox : true,
					field:'id_',
					width:100,
				},
				{	
					field:'index',
					title:'编号',
					width:100,
					editor:{
                		type:'text'
                	}
				},
                {
                	field:'number',
                	title:'序号区',
                	width:200,
                	editor:{
                		type:'text'
                	}
                },{
                	field:'content',
                	title:'内容区',
                	width:200,
                	editor:{
                		type:'text'
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
            }
		});
		
		var data = parent.document.getElementById("${param.objElementId}").value;
		if(data!=""){
			var _data = JSON.parse(data);
			treeCount = getMaxTreeId(_data);
			$("#tg").treegrid("loadData",JSON.parse(data));
		}
	});

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
	
	function getAllHeaderNames(nodes){
		var names = [];
		for(var i=0,len=nodes.length;i<len;i++){
			if(nodes[i].children && nodes[i].children.length>0){
				var tnames = getAllHeaderNames(nodes[i].children);
				for(var j=0;j<tnames.length;j++){
					names.push(tnames[j]);
				}
			}else{
				names.push(nodes[i].number);
			}
		}
		return names;
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
	
			var numberEditor = $("#tg").treegrid("getEditor",{index:row.id,field:"number"});
			
			var _href0 = "${pageContext.request.contextPath}/mx/html/editor/view?retFn=retHtmlFn0&"
				+ "getFieldFn=getExpression&getVarFn=getVarFn&getParamFn=getParamFn&initHTMLFn=initHTMLFn0";
			//链接文本框事件
			numberEditor.target.bind("dblclick",function(){
				//debugger;
				$this = $(this);
				window.open(_href0);
			});
			
			var _href = "${pageContext.request.contextPath}/mx/html/editor/view?retFn=retHtmlFn&"
				+ "getFieldFn=getExpression&getVarFn=getVarFn&getParamFn=getParamFn&initHTMLFn=initHTMLFn";
			var contentEditor = $("#tg").treegrid("getEditor",{index:row.id,field:"content"});
			contentEditor.target.bind("dblclick",function(){
				//debugger;
				$this = $(this);
				window.open(_href);
			});
		}
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
	//获取参数
	function getExpression() {
		var expressionData = [];
		if(params){
			var paramsJsons = JSON.parse(params);
			
			for (var i = 0; i < paramsJsons.length; i++) {
				var express = {};
				var param = paramsJsons[i];
				express.t = param.name;
				express.dType = "string";
				express.param = param.param;
				express.code = "~F{"+param.param+"."+param.param+"."+param.param+"}";
				express.value = "~F{"+param.name+"}";
				express.name = param.name;
				expressionData.push(express); 
			}
		}
		return expressionData;
	}
	// html编辑器回调函数
	function retHtmlFn0(data){
		var row = $('#tg').treegrid('getSelected');
		
		row.number = data.htmlVal;
		row.numberdata = JSON.stringify(data),
		$('#tg').treegrid('refresh',editingId);
		$("#tg").treegrid('endEdit', editingId);
		/* if(data){
			$('#linkImgGrid').datagrid('endEdit',gindex);
			$('#linkImgGrid').datagrid('updateRow',{index:gindex,row:{htmldata:JSON.stringify(data),
				htmlExpression:data.htmlVal}});
		} */
	}
	// html编辑器回显内容 
	function initHTMLFn0(){
		//alert(1);
		var rows = $('#tg').treegrid('getData');
		if(rows){
			var row = rows[editingId-1];
			if(row){
				//var obj = {};
				//obj.varVal = row.htmlMap;
				//obj.htmlVal = row.htmlExpression ;
				return JSON.parse(row.numberdata);
			}
		}
	}
	// html编辑器回调函数
	function retHtmlFn(data){
		var row = $('#tg').treegrid('getSelected');
		
		row.content = data.htmlVal;
		row.contentdata = JSON.stringify(data),
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
				return JSON.parse(row.contentdata);
			}
		}
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
	
	

</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north'" style="height:100%">
		<table id="tg"></table>
	</div>

	<!-- <div id="linkpage_select_content" style="overflow:auto;width:300px;height: 370px;display: none">
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
	</div> -->


</body>
</html>