<%@ page pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jquery.base64.js"></script>
<script type="text/javascript">

var fieldJsonArray = [],tableJsonArray = [];
function selectTable(databaseCode,tableIdElementId,tableNameElementId,tableObjElementId,tableJson,endFunction){
	var link = "${pageContext.request.contextPath}/mx/isdp/table?method=selectTable";
	/* 
	if(databaseCode == ""){
		layer.alert("请先选择数据源",3);
		return;
	}
	 */
	link += "&databaseCode="+databaseCode;
	link += "&tableIdElementId="+tableIdElementId+"&tableNameElementId="+tableNameElementId+"&tableObjElementId="+tableObjElementId;
	
	if(tableJson != ""){
		//link += "&tableJson="+encodeURIComponent($.base64.encode(tableJson));
		tableJsonArray = tableJson;
	}
	
	//layer彻底关闭后的回调函数
	 if(endFunction==undefined){
		 endFunction = function(index){
       	tableJsonArray = [];
       }
	 }
	
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "选择表",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['',''],
		fadeIn: 100,
		area: ['800px', '550px'],
        iframe: {src: link},
        end:endFunction
	});
}

function selectField(databaseCode,fieldIdElementId,fieldNameElementId,fieldObjElementId,tableJson,fieldJson,endFunction){
	var link = "${pageContext.request.contextPath}/mx/isdp/table?method=selectField";
	/* 
	if(databaseCode == ""){
		layer.alert("请先选择数据源",3);
		return;
	}
	 */
	link += "&databaseCode="+databaseCode;
	if(tableJson == ""){
		layer.alert("请选择数据表",3);
		return;
	}
	//link += "&tableJson="+encodeURIComponent($.base64.encode(tableJson));
	link += "&fieldIdElementId="+fieldIdElementId+"&fieldNameElementId="+fieldNameElementId+"&fieldObjElementId="+fieldObjElementId;
	
	tableJsonArray = tableJson;
	if(fieldJson != ""){
		fieldJsonArray = fieldJson;
		//link += "&fieldJson="+encodeURIComponent($.base64.encode(fieldJson));
	}
	
	//layer彻底关闭后的回调函数
	 if(endFunction==undefined){
		 endFunction = function(index){
			 fieldJsonArray = [];
       }
	 }
	
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "选择字段",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['',''],
		fadeIn: 100,
		area: ['550px', '500px'],
        iframe: {src: link},
        end: endFunction
	});
}

function selectTreeField(databaseCode,fieldIdElementId,fieldNameElementId,fieldObjElementId,tableJson,fieldJson,endFunction){
	var link = "${pageContext.request.contextPath}/mx/isdp/table?method=selectTreeField";
	/* 
	if(databaseCode == ""){
		layer.alert("请先选择数据源",3);
		return;
	}
	 */
	link += "&databaseCode="+databaseCode;
	if(tableJson == ""){
		layer.alert("请选择数据表",3);
		return;
	}
	//link += "&tableJson="+encodeURIComponent($.base64.encode(tableJson));
	link += "&fieldIdElementId="+fieldIdElementId+"&fieldNameElementId="+fieldNameElementId+"&fieldObjElementId="+fieldObjElementId;
	
	tableJsonArray = tableJson;
	if(fieldJson != ""){
		//link += "&fieldJson="+encodeURIComponent($.base64.encode(fieldJson));
		fieldJsonArray = fieldJson;
	}
	
	//layer彻底关闭后的回调函数
	 if(endFunction==undefined){
		 endFunction = function(index){
			 fieldJsonArray = [];
       }
	 }
	
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "选择字段",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['',''],
		fadeIn: 100,
		area: ['900px', '600px'],
        iframe: {src: link},
        end:endFunction
	});
}

function modifyField(databaseCode,fieldObjElementId,fieldJson,endFunction){
	var link = "${pageContext.request.contextPath}/mx/isdp/table?method=modifyField";
	
	link += "&databaseCode="+databaseCode;
	link += "&fieldObjElementId="+fieldObjElementId;
	if(fieldJson != ""){
		//link += "&fieldJson="+encodeURIComponent($.base64.encode(fieldJson));
		fieldJsonArray = fieldJson;
	}
	
	//layer彻底关闭后的回调函数
	 if(endFunction==undefined){
		 endFunction = function(index){
			 fieldJsonArray = [];
       }
	 }
	
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "编辑字段",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['',''],
		fadeIn: 100,
		area: ['800px', '600px'],
        iframe: {src: link},
        end: endFunction
	});
}

function modifyTableHander(elementId,objElementId,tableHanderJsonArray,fieldObjElementId,endFunction){
	var link = "${pageContext.request.contextPath}/mx/isdp/table?method=tableHander";
	link += "&elementId="+elementId+"&objElementId="+objElementId+"&fieldObjElementId="+fieldObjElementId;
	//link += "&tableHanderJsonArray="+tableHanderJsonArray;
	
	//layer彻底关闭后的回调函数
	 if(endFunction==undefined){
		 endFunction = function(index){
			 
      }
	 }
	
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "编辑表头",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['',''],
		fadeIn: 100,
		area: ['800px', '600px'],
        iframe: {src: link},
        end: endFunction
	});
}
</script>