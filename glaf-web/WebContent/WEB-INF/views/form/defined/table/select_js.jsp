<%@ page pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jquery.base64.js"></script>
<script type="text/javascript">
//参数1：返回的结果文本ID
//参数2：中文表名文本框ID
//参数3：中文字段名文本框ID
function selectTableAndFields(resultElementId,tablenameElementId,elementId,pageId){
	var flag = 0 ;
	if(elementId.indexOf("ztree")!= (-1)){
		flag = 1;  //ztree
	}else if(elementId.length > 30){
		flag = 2; //页面
	}
	var link = '${pageContext.request.contextPath}/mx/form/defined/table/main2?resultsElementId='+resultElementId+'&tablenameElementId='+tablenameElementId+'&fieldnameElementId=&flag='+flag+"&pageId="+pageId;
	//&fieldnameElementId=
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "选择表/字段",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['',''],
		fadeIn: 100,
		area: ['980px', '700px'],
        iframe: {src: link}
	});
}
function selectDatasetByRoundWater(resultElementId,tablenameElementId,elementId,pageId){
	var flag = false ;
	if(elementId.indexOf("ztree")!= (-1)){
		flag = true;
	}
	var link = '/glaf/mx/form/defined/table/round_datasource?resultsElementId='+resultElementId+'&tablenameElementId='+tablenameElementId+'&fieldnameElementId=&flag='+flag+"&pageId="+pageId;
	//&fieldnameElementId=
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "选择数据集",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['',''],
		fadeIn: 100,
		area: ['980px', '700px'],
        iframe: {src: link}
	});
}
function selectDatasetByPhone(resultElementId,tablenameElementId,elementId,pageId){
	var flag = false ;
	if(elementId.indexOf("ztree")!= (-1)){
		flag = true;
	}
	var link = '/glaf/mx/form/defined/table/phone_datasource?resultsElementId='+resultElementId+'&tablenameElementId='+tablenameElementId+'&fieldnameElementId=&flag='+flag+"&pageId="+pageId;
	//&fieldnameElementId=
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "选择数据集",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['',''],
		fadeIn: 100,
		area: ['980px', '700px'],
        iframe: {src: link}
	});
}
function selectDatasetByIndexedList(resultElementId,tablenameElementId,elementId,pageId){
	var flag = false ;
	if(elementId.indexOf("ztree")!= (-1)){
		flag = true;
	}
	var link = '/glaf/mx/form/defined/table/indexedlist_datasource?resultsElementId='+resultElementId+'&tablenameElementId='+tablenameElementId+'&fieldnameElementId=&flag='+flag+"&pageId="+pageId;
	//&fieldnameElementId=
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "选择数据集",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['',''],
		fadeIn: 100,
		area: ['980px', '700px'],
        iframe: {src: link}
	});
}
function selectDatasetByCellSet(resultElementId,tablenameElementId,elementId,pageId){
	var flag = false ;
	if(elementId.indexOf("ztree")!= (-1)){
		flag = true;
	}
	var link = '/glaf/mx/form/defined/table/cellset_datasource?resultsElementId='+resultElementId+'&tablenameElementId='+tablenameElementId+'&fieldnameElementId=&flag='+flag+"&pageId="+pageId;
	//&fieldnameElementId=
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "选择数据集",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['',''],
		fadeIn: 100,
		area: ['980px', '700px'],
        iframe: {src: link}
	});
}
function selectDatasetByCharts(resultElementId,tablenameElementId,elementId,pageId){
	var flag = false ;
	if(elementId.indexOf("ztree")!= (-1)){
		flag = true;
	}
	var link = '${pageContext.request.contextPath}/mx/form/defined/table/charts_datasource?resultsElementId='+resultElementId+'&tablenameElementId='+tablenameElementId+'&fieldnameElementId=&flag='+flag+"&pageId="+pageId;
	//&fieldnameElementId=
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "选择数据集",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['',''],
		fadeIn: 100,
		area: ['980px', '700px'],
        iframe: {src: link}
	});
}
function selectDatasetByJPlayer(resultElementId,tablenameElementId,elementId,pageId){
	var flag = false ;
	if(elementId.indexOf("ztree")!= (-1)){
		flag = true;
	}
	var link = '${pageContext.request.contextPath}/mx/form/defined/table/jplayer_datasource?resultsElementId='+resultElementId+'&tablenameElementId='+tablenameElementId+'&fieldnameElementId=&flag='+flag+"&pageId="+pageId;
	//&fieldnameElementId=
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "选择数据集",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['',''],
		fadeIn: 100,
		area: ['980px', '700px'],
        iframe: {src: link}
	});
}

function selectDatasetByMap(resultElementId,tablenameElementId,elementId,pageId){
	var flag = false ;
	if(elementId.indexOf("ztree")!= (-1)){
		flag = true;
	}
	var link = '${pageContext.request.contextPath}/mx/form/defined/table/map_datasource?resultsElementId='+resultElementId+'&tablenameElementId='+tablenameElementId+'&fieldnameElementId=&flag='+flag+"&pageId="+pageId;
	//&fieldnameElementId=
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "选择数据集",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['',''],
		fadeIn: 100,
		area: ['980px', '700px'],
        iframe: {src: link}
	});
}

function selectDatasetByJSGIS(resultElementId,tablenameElementId,elementId,pageId){
	var flag = false ;
	if(elementId.indexOf("ztree")!= (-1)){
		flag = true;
	}
	var link = '${pageContext.request.contextPath}/mx/form/defined/table/jsgis_datasource?resultsElementId='+resultElementId+'&tablenameElementId='+tablenameElementId+'&fieldnameElementId=&flag='+flag+"&pageId="+pageId;
	//&fieldnameElementId=
	$.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		title: "选择数据集",
		closeBtn: [0, true],
		shade: [0, '#000'],
		border: [10, 0.3, '#000'],
		offset: ['',''],
		fadeIn: 100,
		area: ['980px', '700px'],
        iframe: {src: link}
	});
}


//参数1：返回的表头中文文本框ID
//参数2：返回的表头JSON字符串对象文本框ID
//参数3：选表后返回的结果文本框ID
function modifyTableHander(elementId,objElementId,datasId){
	var link = "${pageContext.request.contextPath}/mx/form/defined/table/table_hander?1=1";
	link += "&elementId="+elementId+"&objElementId="+objElementId+"&datasId="+datasId;
	//link += "&tableHanderJsonArray="+tableHanderJsonArray;
	
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
        iframe: {src: link}
	});
}

//选择树
function selectTreeField(databaseCode,fieldIdElementId,fieldNameElementId,fieldObjElementId,tableJson,fieldJson,endFunction,elementId,view){
	var link = "${pageContext.request.contextPath}/mx/isdp/table?method=selectTreeField";
	if(view){
		link += '&view='+view;
	}
	/* 
	if(databaseCode == ""){
		layer.alert("请先选择数据源",3);
		return;
	}
	 */
	var flag = false ;
	if(elementId.indexOf("treelist")!= (-1)){
		flag = true;
		link += "&flag="+flag;
	}
	
	
	link += "&databaseCode="+databaseCode;
	if(tableJson == ""){
		layer.alert("请选择数据集",3);
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
		area: ['600px', '600px'],
        iframe: {src: link},
        end:endFunction
	});
}
function selectMediaListField(databaseCode,fieldIdElementId,fieldNameElementId,fieldObjElementId,tableJson,fieldJson,endFunction,elementId,view){
	var link = "${pageContext.request.contextPath}/mx/form/defined?method=selectMediaListSource";

	if(view){
		link += '&view='+view;
	}
	/* 
	if(databaseCode == ""){
		layer.alert("请先选择数据源",3);
		return;
	}
	 */
	var flag = false ;
	if(elementId.indexOf("treelist")!= (-1)){
		flag = true;
		link += "&flag="+flag;
	}
	
	
	link += "&databaseCode="+databaseCode;
	if(tableJson == ""){
		layer.alert("请选择数据集",3);
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
		area: ['600px', '600px'],
        iframe: {src: link},
        end:endFunction
	});
}
</script>