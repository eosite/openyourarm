//**********************全局元素******************************************
	var map = new Map();
	var cnMap = new Map();
	var list = new ArrayList();
	var divsJson ;
	var jsArraysJson ;
	var enabled = false ;	
	//元素动态属性
	var attributeInit = new Array();
	//公共属性
	var publicAttrInit = [{}];
//**********************自定义帮助解析excel布局******************************************
Array.prototype.getJsonObjByRow = function (minJson){
	var jsons = new Array();
	for(var i=0;i<this.length;i++){
		var json = this[i] ;
		if(json.rowIndex == minJson.rowIndex && json.rowSpan == minJson.rowSpan && (json.colIndex+json.colSpan-minJson.colIndex ==0 || 
		minJson.colIndex+minJson.colSpan-json.colIndex ==0  || (json.colIndex == minJson.colIndex && json.colSpan == minJson.colSpan) ) ){
			jsons.push(json) ;
		}
	}
	return jsons ;
}
Array.prototype.getJsonObjByCol = function (minJson){
	var jsons = new Array();
	for(var i=0;i<this.length;i++){
		var json = this[i] ;
		if(json.colIndex == minJson.colIndex && json.colSpan == minJson.colSpan &&  (json.rowIndex+json.rowSpan-minJson.rowIndex ==0 || 
		minJson.rowIndex+minJson.rowSpan-json.rowIndex ==0  || (json.rowIndex == minJson.rowIndex && json.rowSpan == minJson.rowSpan) ) ){
			jsons.push(json) ;
		}
	}
	return jsons ;
}
Array.prototype.getMinJsonObj = function (breakJsons){
	var minJson = null ;
	for(var i=0;i<this.length;i++){
		var json = this[i] ;
		var flag = false ;
		for (var j=0 ; j < breakJsons.length ; j++ ){
			var breakJson = breakJsons[j];
			if(json.id == breakJson.id ){
				flag = true ;
				break;
			}
		}
		if(flag)continue;
		if(minJson!=null){
			if((json.colSpan + json.rowSpan) < (minJson.colSpan + minJson.rowSpan)){
				minJson = json ;	
			}
		}else{
			minJson = json ;	
		}
	}
	return minJson ;
}
Array.prototype.remove = function ( json ){
	var a = this.indexOf(json); 
	if (a >= 0) { 
		this.splice(a, 1); 
		return true; 
	} 
	return false; 
}
Array.prototype.getDivById = function (id){
	for(var i=0;i<this.length;i++){
		var div = this[i] ;
		if(div.id == id || (div.id == id+"-child")){
			return div ;
		}
	}
	return null ;
}
function sortByColIndex(x,y){
	return x.colIndex - y.colIndex ;
}
Array.prototype.getAttrByType = function (type){
	for(var i=0;i<this.length;i++){
		var attr = this[i] ;
		if(attr.type+"" == type+"" ){
			return $.extend(true, {}, attr) ;
		}
	}
	return null ;
}
Array.prototype.indexOf = function(value){
	var array = this ;
	for (var i = 0; i < array.length; i++) {
		if(array[i]==value){
			return i;
		}
	}
	return -1 ;
}

//**********************解析excel布局区******************************************
function parseExcelJson(jsons){
		var divs = new Array(); //存放div集合
		var jsArrays = new Array() ;//存放需要渲染的各个div的javascript集合
		var count = jsons.length*2 ;//循环次数
		var breakJsons = new Array();//存放最小单位无同级元素的集合
		for(var h = 0 ; h<count ; h++){
			if(jsons.length == 1) break;
			//获取除breakJsons外，其包含的最小单位
			minJson = jsons.getMinJsonObj(breakJsons);
			//判断行坐标个数量 并且 colSpan 或 rowSpan 相同的
			var rows = jsons.getJsonObjByRow(minJson);
			var cols = jsons.getJsonObjByCol(minJson);
			//如果该最小单位无同级排列的其它元素，则排除，再次寻找
			if(rows.length == cols.length == 1){
					breakJsons.push(minJson);
					continue ;
			}
			breakJsons = new Array();//清空

			//var id = Math.random();
			var id = "fzmt_design_"+Math.floor((Math.random()*(999999999-900000000)+900000000))+ h ;
			var jsArray = new Object() ;//用于渲染div的javascript
			jsArray.id = id ; //
			var panes = new Array() ;//kendoSplitter 的panes
			
			var newJson = Array();//把此div 作为新的json放入 jsons里面
			newJson.id = id ;	
			newJson.colIndex = null  ;
			newJson.rowIndex =null ;
			newJson.colSpan =0 ;
			newJson.rowSpan =0;
			
			var divObj = document.createElement("div");
			divObj.setAttribute("id", id);
			//divObj.setAttribute("style", "width:100%;height:100%");
			var rowsAndcols ;
			var orientationFlag = false ;//默认为垂直方向
			if(rows.length >= cols.length){
				orientationFlag = true ;
				jsArray.orientation = "horizontal"; //水平方向
				rowsAndcols = rows ;
			}else{
				jsArray.orientation = "vertical" ; //垂直方向
				rowsAndcols = cols ;
			}
			for (var i = 0 ;i<rowsAndcols.length ; i++ ){
				var row = rowsAndcols[i];
				var pane = new Object();
				pane.collapsible = true;
				if(orientationFlag){
					pane.size = row.width+"%" ;
				}else{
					pane.size = row.height+"%" ;
				}
				panes.push(pane);
				var flag = false ;
				var divChildObj ;
				if(divs.getDivById(row.id)!=null){ 
					flag = true ;
					divChildObj = divs.getDivById(row.id);
					divs.remove(divs.getDivById(row.id));
				}else{
					divChildObj = document.createElement("div");
					divChildObj.setAttribute("id", row.id);
					//divChildObj.setAttribute("contenteditable", true);
					//divChildObj.setAttribute("style", "width:100%;height:100%"); 
					//divChildObj.innerHTML = row.id;
					//divObj.appendChild(divChildObj);
				}
				if(flag){
					var flagDiv = document.createElement("div");
					flagDiv.appendChild(divChildObj);
					flagDiv.setAttribute("id", id+"-child");
					divObj.appendChild(flagDiv);
				}else{
					divObj.appendChild(divChildObj);
				}
				jsons.remove(row);//删掉已经创建的元素
				//计算合并后的元素所占的col/row
				if(orientationFlag){
					if(newJson.colIndex == null ){
						newJson.colIndex = row.colIndex ;
					}else{
						if(newJson.colIndex > row.colIndex){
							newJson.colIndex = row.colIndex ;
						}
					}
					newJson.colSpan = newJson.colSpan + row.colSpan ;
					newJson.rowIndex = minJson.rowIndex;
					newJson.rowSpan = minJson.rowSpan ;
				}else{
					if(newJson.rowIndex == null ){
						newJson.rowIndex = row.rowIndex ;
					}else{
						if(newJson.rowIndex > row.rowIndex){
							newJson.rowIndex = row.rowIndex ;
						}
					}
					newJson.rowSpan = newJson.rowSpan + row.rowSpan ;
					newJson.colIndex = minJson.colIndex;
					newJson.colSpan = minJson.colSpan ;
				}
			}		
				jsArray.panes =  panes;
				jsArrays.push(jsArray);
				jsons.push(newJson);
				jsons.sort(sortByColIndex);//排序
				divs.push(divObj);
		}
		jsArraysJson =  JSON.stringify(jsArrays) ;
		divsJson =  divs[0].outerHTML ;
		//写入div
		for (var i = 0;i<divs.length ; i++){
			document.getElementById("htmlDiv").appendChild(divs[i]);
		}
		//渲染 kendo-ui splitter
		for (var i = 0;i < jsArrays.length ;i++ ){
			var jsArray = jsArrays[i] ;
			var id = "#"+jsArray.id ;
			//$(id)[0].style.height="100%";
			$(id).height("100%");
			$(id).kendoSplitter({
					orientation: jsArray.orientation,
					panes: jsArray.panes
			});
		}
		
		//var returnList = new ArrayList();
		//returnList.add(divs);
		//returnList.add(jsArrays);
		//return returnList ;
}

//**********************预览******************************************
function preview(){
	var id = document.getElementById("id").value
	if(id==""){
		document.getElementById('xx').innerHTML = "请先保存后再预览" ;
		return ;
	}
	var iframe = document.createElement("iframe");
	var time =  new Date().toLocaleTimeString();
	var url = contextPath + "/mx/form/design/preview?id="+id+"&time="+time ;
	iframe.setAttribute("src",url);
	iframe.setAttribute("width","100%");
	iframe.setAttribute("height","550px");
	document.getElementById('xx').innerHTML = "" ;
	document.getElementById('xx').appendChild(iframe);
}
//**********************操作功能区******************************************
//保存
function saveDesign (){

	//元素html
	var docArrays = new Array();
	for (var i = 0; i < list.size(); i++) {
		var id = list.get(i);
		var innerHtml = document.getElementById(id).innerHTML ;
		var docObj = new Object();
		docObj.id = id ;
		docObj.html = innerHtml ;
		docArrays.push(docObj);
	}
	var docJson = JSON.stringify(docArrays) ;
	//console.log(docJson);
	
	//元素javascript
	var attrArrays = new Array();
	var keys = map.keys() ;
	for (var i = 0 ;i < keys.length ; i++){
		var attrObj = new Object();
		var key = keys[i] ;
		var value =  map.get(key);
		attrObj.id = key ;
		attrObj.attr = value ;
		attrArrays.push(attrObj);
	}
	var attrJson = JSON.stringify(attrArrays);
	//console.log(attrJsons);
	
	//布局html
	//console.log(divsJson);
	
	//布局javascript
	//console.log(jsArraysJson);
	
	var id = document.getElementById("id").value  ;
	var url = contextPath+"/mx/form/design/saveFormDesign" ;
	jQuery.ajax({ 
		   type: "POST", 
		   url: url, 
		   dataType:  'json', 
		   data: {
			   	id : id ,
				deploymentId : id ,
				layoutHtml : divsJson ,
				layoutJS : jsArraysJson ,
				addElementHtml : docJson ,
				addElementJS : attrJson	,
				addElementDS : "" 
			}, 
		   error: function(XMLHttpRequest, textStatus, errorThrown){ 
		   		alert('服务器处理错误！'); 
		   }, 
		   success: function(data){ 
			   if(data != null && data.message != null){ 
			  		 alert(data.message); 
			   } else { 
				   document.getElementById("id").value = data.id ;
			  	   alert('操作成功完成！'); 
			   } 
		   } 
	});
	
}

//**********************拖拽功能区******************************************
//规定拖动放置元素
var diffX ;
var diffY ;
function allowDrop(ev){	
	//console.log("移动"+event.clientX+"/"+event.clientY);
	var dropDiv = document.getElementById("dropDiv");
	if(dropDiv){
		alert(ev.target.id);
		var obj = document.getElementById(ev.target.id);
		var orect = obj.getBoundingClientRect();
		var style = "width:5px;height:5px;cursor: nw-resize;position: absolute;float:left;left:"+(orect.left-5)+"px;top:"+(orect.top-5)+"px;background-color: red;";
		att(dropDiv,{"style":style});
	}
	ev.preventDefault();//阻隔默认方法，实现可拖动
}
//拖拽开始时触发
function drag(ev){//规定了拖动的数据 text 为id
	diffX = event.clientX - ev.target.offsetLeft; 
	diffY = event.clientY - ev.target.offsetTop;  
	ev.dataTransfer.setData("Text",ev.target.id);
	if(resize)
		resize.destroy();
}
//拖拽过程中触发
function drop(ev){	
	ev.preventDefault(); //阻隔默认方法
	var data=ev.dataTransfer.getData("Text");//获取拖动的数据id
	var element = document.getElementById(data);
	//存放布局模块存放了的id
	if(!list.contains(ev.target.id)){
		list.add(ev.target.id);
	}
	var template = element.getAttribute("template") ;
//	console.log("后"+event.clientX+"/"+event.clientY);
	if(template){ //创建元素
		//动态调用方法
		eval(template+"(ev)");
	}else{//元素拖拽
		//console.log();
		if((event.clientX-diffX)<0 || (event.clientY-diffY)<0 || (event.clientX > ev.target.offsetWidth) || (event.clientY > ev.target.offsetHeight) )
			Css(element,{left:ev.offsetX+'px',top:ev.offsetY+'px'});
			//element.setAttribute("style","left: "+ev.offsetX+"px;top:"+ev.offsetY+"px;position:absolute;" );
		else
			Css(element,{left:(event.clientX-diffX)+'px',top:(event.clientY-diffY)+'px'});
			//element.setAttribute("style","left: "+(event.clientX-diffX)+"px;top:"+(event.clientY-diffY)+"px;position:absolute;" );
		ev.target.appendChild(element);
		
		resize=new Resize(element); 
		resize.create();
	}
}
//**********************属性功能******************************************
function setAttr(ele){
	var obj = map.get(ele.getAttribute("bind_id"));
	if(obj!=null){
		var attrObj = obj.attr ;
		for (var p in attrObj ) {
			if(p == ele.getAttribute("bind_name")){
				attrObj[p] = ele.value ;
			}
		}
	}	
}
//**********************初始化页面******************************************
function initLayout(){
	//页面布局
	$("#mainDiv").kendoSplitter({
		orientation: "horizontal",
		panes: [
			{ collapsible: true, resizable:false,size: "150px" },
			{ collapsible: false, resizable:false }
		]
	 });
	 $("#tabstrip").kendoTabStrip({
			animation:  {
				open: {
					effects: "fadeIn"
				}
			}
      });
  	 //左边panel
  	  var viewModel = kendo.observable({
          isVisible: true
      });
      kendo.bind($("#panel"), viewModel);
      //顶端menu
      $("#menu").kendoMenu();
      //window 
      var attrWindow = $("#window");
	  if (!attrWindow.data("kendoWindow")) {
		  attrWindow.kendoWindow({
	          width: "300px",
	          position: { top:0,left:document.body.clientWidth-310 },
	          visible : false ,
	          title: "属性",
	          actions: [
	             // "Minimize",
	              "close"
	          ]
	      });
	  }
	  //
	  $("#tabstrip2").kendoTabStrip({
			animation:  {
				open: {
					effects: "fadeIn"
				}
			}
	  });
}

function openWindow(e){
	var openAttr = "打开属性";
	var closeAttr = "关闭属性";
	if(e.innerHTML == openAttr){
		enabled = true ;
		$("#window").data("kendoWindow").open();
		e.innerHTML = closeAttr ;
	}else if(e.innerHTML == closeAttr){
		enabled = false ;
		$("#window").data("kendoWindow").close();
		e.innerHTML = openAttr ;
	}
}

function showWindow(e){
	if(enabled){
		var attrWindow = $("#window") ;
		var width = e.clientX+10;
		var height = e.clientY-10;
		var eleWidth = attrWindow[0].clientWidth == 0 ? 300 : attrWindow[0].clientWidth ;
		var eleHeight = attrWindow[0].clientHeight == 0 ? 120 : attrWindow[0].clientHeight ;
	//	alert(eleWidth+"/"+eleHeight);
		if(eleWidth > (document.body.clientWidth - e.clientX) )
			width =  e.clientX - eleWidth - 10;
		if(eleHeight > (document.body.clientHeight - e.clientY-50))
			height = document.body.clientHeight - eleHeight -100 ;
		//console.log(height);
		attrWindow.data("kendoWindow").open();
		attrWindow.kendoWindow({
			  position: {
			    top: height,
			    left: width
			  }
		});
	}
}

//**********************属性加载******************************************
var resize  ;
function show_element(e){  
	showWindow(e);
	//console.log(e.clientX+"/"+e.offsetX+"/"+e.x);
	var hiddenId = document.getElementById("hiddenID");
	if(hiddenId.value != ""){
		var old = document.getElementById(hiddenId.value);
		if(old)
			old.style.backgroundColor="" ;
	}
	//获取当前选中的元素
	var obj=document.elementFromPoint(e.clientX,e.clientY);
	if(obj.ondragstart){
		resize = new Resize(obj);
		resize.create();
	}else{
		if(resize)
			resize.destroy();
	}
	
	hiddenId.value= obj.id ;
	obj.style.backgroundColor="rgba(60, 70, 80, 0.10)" ;
	
	var tableObj = document.createElement("table");
	tableObj.setAttribute("border", "0");
	tableObj.setAttribute("cellspacing", "0");
	tableObj.setAttribute("cellpadding", "1");
	tableObj.setAttribute("bgColor", "#eaf4f9");
	tableObj.setAttribute("width", "100%");
	tableObj.setAttribute("style", "BORDER-COLLAPSE: collapse");
	var tbodyObj = document.createElement("tbody");
	tableObj.appendChild(tbodyObj);

	var trObj = document.createElement("tr");
	var tdObj = document.createElement("td");
	tdObj.setAttribute("style", "background-color:#aOc6e5");
	tdObj.setAttribute("width", "40%");
	tdObj.innerHTML =  "属性" ;
	var tdObj2 = document.createElement("td");
	tdObj2.innerHTML =  "值" ;
	trObj.appendChild(tdObj);
	trObj.appendChild(tdObj2);
	tbodyObj.appendChild(trObj);
	if(!obj.id)return;
	
	createAttribute(obj,tbodyObj);
		
	var docDiv = document.getElementById("tabstrip2-1");
	docDiv.innerHTML = "" ;
	docDiv.appendChild(tableObj);
      
} 


function createAttribute(obj,tbodyObj){
	//通用属性
	trObj = document.createElement("tr");
	tdObj = document.createElement("td");
	tdObj.innerHTML =  "ID" ;
		//+obj.style.width ;
	tdObj2 = document.createElement("td");
	tdObj2.innerHTML = obj.id  ;
	trObj.appendChild(tdObj);
	trObj.appendChild(tdObj2);
	tbodyObj.appendChild(trObj);
	trObj = document.createElement("tr");
	tdObj = document.createElement("td");
	tdObj.innerHTML =  "组件" ;
	tdObj2 = document.createElement("td");
	tdObj2.innerHTML =  obj.getAttribute("data-role") || obj.getAttribute("role") ;
	trObj.appendChild(tdObj);
	trObj.appendChild(tdObj2);
	tbodyObj.appendChild(trObj);
	//alert(obj.id);
	var attrObj = map.get(obj.id);
	
	var dataRole = obj.getAttribute("data-role") ;
	if(attrObj == null ||  attrObj == undefined){
			if(attributeInit.getAttrByType(dataRole) != null)
				attrObj = attributeInit.getAttrByType(dataRole) ;
			else 
				return ;
	}
	var attr = attrObj.attr ;
	var acCNName = cnMap.get(dataRole);
	for (var p in attr){
		if(acCNName[p] == undefined)continue;
		trObj = document.createElement("tr");
		tdObj = document.createElement("td");
		tdObj.innerHTML =  acCNName[p] ;
		tdObj2 = document.createElement("td");
		var input = document.createElement("input");
		input.value = attr[p] ;
		input.setAttribute("size","10");
		input.setAttribute("bind_id",obj.id);
		input.setAttribute("bind_name", p+"");
		input.setAttribute("onchange","setAttr(this)");
		tdObj2.appendChild(input)   ;
		trObj.appendChild(tdObj);
		trObj.appendChild(tdObj2);
		tbodyObj.appendChild(trObj);
	}
}
//**********************绑定固定数据******************************************
function fixEleAttr(obj,ev,attr){
	var id = "fzmt_ele_"+Math.floor((Math.random()*(999999999-900000000)+900000000))  ;
	//obj.setAttribute("id", id );
	//obj.setAttribute("data-role",attr.type);
	//obj.setAttribute("draggable","true");
	//obj.setAttribute("contenteditable","true");
	//obj.setAttribute("ondragstart","drag(event);");
	//obj.setAttribute("style","left: "+ev.offsetX+"px;top:"+ev.offsetY+"px;position:absolute;" );
	var atts= {"id":id,"data-role":attr.type,"draggable":"true","ondragstart":"drag(event);","style":"left: "+ev.offsetX+"px;top:"+ev.offsetY+"px;position:absolute;"} ;
	att(obj,atts);
	ev.target.appendChild(obj);
	//为创建元素绑定添加属性
	//var value = attr ;
		//attributeInit.getAttrByType(attr.type) ;
	map.put(id,$.extend(true, {}, attr));
}
//
var att = function(e,o){
	for(var p in o){
		e.setAttribute(p,o[p]);
	}
}

//****************************************调整大小************************************************************
var bind = function(object, fun) { 
	//slice截取字符串，call 将(arguments)代替array。
    var args = Array.prototype.slice.call(arguments).slice(2); //第2个参数开始截取arguments
    return function() { 
        return fun.apply(object, args); //object可以使用fun的方法，并传入agrs数组参数
    } 
}; 
var bindAsEventListener = function (obj,fun){
	var args = Array.prototype.slice.call(arguments).slice(2); 
    return function(event) { 
        return fun.apply(obj, [event || window.event].concat(args)); //合并数组
    } 
};
//绑定事件
function addListener(element,e,fn){ 
	//target.addEventListener(type, listener, useCapture); type：事件名称比如click（此函数不包含on） listenner：监听函数，
	//useCapture触发顺序\捕获阶段（根节点到子节点检查是否调用了监听函数）→目标阶段（目标本身）→冒泡阶段（目标本身到根节点)\true只在捕获阶段处理，false为其它2个阶段（默认）
    element.addEventListener?element.addEventListener(e,fn,false):element.attachEvent("on" + e,fn); 
}; 
//删除事件
function removeListener(element,e,fn){ 
    element.removeEventListener?element.removeEventListener(e,fn,false):element.detachEvent("on" + e,fn) 
}; 

var Class = function(properties){
	var class_ = function(){
		return (arguments[0] !== null && this.init && typeof(this.init) == 'function') ? this.init.apply(this, arguments) : this;
	}; 
	class_.prototype = properties; 
    return class_; 
}
var Resize =new Class({ 
    init: function(obj){ 
    	this.selectObj = null ;//被选中的
    	this.initLeft = null ;//
    	this.initTop = null ; //
    	this.startLeft = obj.offsetLeft ;
    	this.startTop =  obj.offsetTop ;
    	this.startClientX = null ;
    	this.startClientY = null ;
    	this.obj = obj ;
    	// e-resize东，ne-resize 东北，nw-resize西北，n-resize北，se-resize东南，sw-resize西南，s-resize南，w-resize西
    	this.original = ['e-resize','ne-resize','nw-resize','n-resize','se-resize','sw-resize','s-resize','w-resize'] ;
    	pxsize = 6 ;//像素宽度
    	var orect = obj.getBoundingClientRect();//获取当前元素的坐标
    	rleft = orect.left ; //当前元素的left
    	rtop = orect.top ;
    	rwidth = obj.offsetWidth ;  //当前元素的宽度
    	rheight = obj.offsetHeight ;//当前元素的高度
    	this.param = [{left:rleft+rwidth,top:rtop+(rheight-pxsize)/2},	//东
    	              {left:rleft+rwidth,top:rtop-pxsize},				//东北
    	              {left:rleft-pxsize,top:rtop-pxsize},						//西北
    	              {left:rleft+(rwidth-pxsize)/2,top:rtop-pxsize},			//北
    	              {left:rleft+rwidth,top:rtop+rheight},		//东南
    	              {left:rleft-pxsize,top:rtop+rheight},				//西南
    	              {left:rleft+(rwidth-pxsize)/2,top:rtop+rheight},   //南
    	              {left:rleft-pxsize,top:rtop+(rheight-pxsize)/2}];  		//西
    	this.fR = bindAsEventListener(this,this.resize);  //绑定
        this.fS = bindAsEventListener(this,this.stop);  //删除绑定事件   
    },
    create:function(){//创建拖动模块
    	//初始化参数方法
    	for (var i = 0; i < this.original.length; i++) {
    		var dropDiv = document.getElementById(this.original[i]);
        	if(dropDiv){
        		this.destroy(dropDiv);
        		//att(dropDiv,{"style":style});
        	}
        	var params  = this.param[i];
        	var backimg = "background-image:url("+contextPath+"/scripts/54.jpg); " ;
        				//"background-color: red;";
        	var style = "width:"+pxsize+"px;height:"+pxsize+"px;cursor: "+this.original[i]+";position: absolute;float:left;left:"+params.left+"px;top:"+params.top+"px;"+backimg;
    		dropDiv = document.createElement("div");
    		att(dropDiv,{"style":style,"id":this.original[i],"z-index":"100"});
    		document.body.appendChild(dropDiv);
        	addListener(dropDiv,"mousedown",bindAsEventListener(this,this.set));
		}
    },
    set:function(e){//按下鼠标后绑定事件
    	var doc = document.getElementById(e.target.id);
    	this.initLeft = event.clientX - doc.offsetLeft ;//
    	this.initTop = event.clientY - doc.offsetTop ;
    	selectObj = doc ;
    	this.startClientX = event.clientX ;
    	this.startClientY = event.clientY ;
    	//绑定事件
    	addListener(document,'mousemove',this.fR); //绑定鼠标按下事件
    	addListener(document,'mouseup',this.fS); //删除事件
    },
    stop:function(e){
    	//删除事件 松开鼠标后
    	var doc = document.getElementById(e.target.id);
    	removeListener(document, "mousemove", this.fR); 
        removeListener(document, "mouseup", this.fS); 
		//取消所有选中
       // window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();    
    },
    destroy:function(ele){//销毁
    	if(ele){
    		document.body.removeChild(ele);
    	}else{
    		for (var i = 0; i < this.original.length; i++) {
        		var dropDiv = document.getElementById(this.original[i]);
        		if(dropDiv)
        			document.body.removeChild(dropDiv);
    		}
    	}
    },
    resize:function(e){//移动过程中执行的事件
		if(selectObj.id=='e-resize'){//东
			var left_ = e.clientX - this.initLeft ;
			var offset_ = fzmt('w-resize').offsetLeft ;
			var wsize = left_ - offset_ - pxsize ;
			Css(selectObj,{left:left_+'px'});
			Css(fzmt('ne-resize'),{left:left_+'px'});//东北
			Css(fzmt('se-resize'),{left:left_+'px'});//东南
			Css(this.obj,{width:wsize+'px'});//选中对象
			Css(fzmt('n-resize'),{left:(offset_ + (wsize+pxsize)/2)+'px'});//北
			Css(fzmt('s-resize'),{left:(offset_ + (wsize+pxsize)/2)+'px'});//南
		}else if(selectObj.id=='w-resize'){//西
			var left_ = e.clientX - this.initLeft ;
			var offset_ = fzmt('e-resize').offsetLeft ;
			var wsize = offset_ - left_ - pxsize ;
			Css(selectObj,{left:left_+'px'});
			Css(fzmt('nw-resize'),{left:left_+'px'});//西北
			Css(fzmt('sw-resize'),{left:left_+'px'});//西南
			//console.log(this.obj.offsetLeft);
			Css(this.obj,{width:wsize+'px',left:(this.startLeft+e.clientX-this.startClientX)+'px'});
			Css(fzmt('n-resize'),{left:(left_ + (wsize+pxsize)/2)+'px'});//北
			Css(fzmt('s-resize'),{left:(left_ + (wsize+pxsize)/2)+'px'});//南
		}else if(selectObj.id=='s-resize'){//南
			var top_ = e.clientY - this.initTop ;
			var offset_ = fzmt('n-resize').offsetTop ;
			var wsize = top_ - offset_ - pxsize ;
			Css(selectObj,{top:top_+'px'});
			Css(fzmt('sw-resize'),{top:top_+'px'});//西南
			Css(fzmt('se-resize'),{top:top_+'px'});//东南
			Css(this.obj,{height:wsize+'px'});
			Css(fzmt('e-resize'),{top:(offset_ + (wsize+pxsize)/2)+'px'});//东
			Css(fzmt('w-resize'),{top:(offset_ + (wsize+pxsize)/2)+'px'});//西
		}else if(selectObj.id=='n-resize'){//北
			var top_ = e.clientY - this.initTop ;
			var offset_ = fzmt('s-resize').offsetTop ;
			var wsize = offset_ - top_ - pxsize ;
			Css(selectObj,{top:top_+'px'});
			Css(fzmt('ne-resize'),{top:top_+'px'});//东北
			Css(fzmt('nw-resize'),{top:top_+'px'});//西北
			Css(this.obj,{height:wsize+'px',top:(this.startTop+e.clientY-this.startClientY)+'px'});
			Css(fzmt('e-resize'),{top:(top_ + (wsize+pxsize)/2)+'px'});//东
			Css(fzmt('w-resize'),{top:(top_ + (wsize+pxsize)/2)+'px'});//西
		}else if(selectObj.id=='se-resize'){//东南
			var left_ = e.clientX - this.initLeft ;
			var top_ = e.clientY - this.initTop ;
			var offsetLeft_ = fzmt('nw-resize').offsetLeft ;
			var offsetTop_ = fzmt('nw-resize').offsetTop ;
			var wsizeLeft = left_ - offsetLeft_ - pxsize ;
			var wsizeTop = top_ - offsetTop_ - pxsize ;
			Css(selectObj,{left:left_+'px',top:top_+'px'});
			Css(fzmt('e-resize'),{left:left_+'px',top: (offsetTop_ + (wsizeTop+pxsize)/2)+'px'});//东
			Css(fzmt('w-resize'),{top:(offsetTop_ + (wsizeTop+pxsize)/2)+'px'});//西
			Css(fzmt('s-resize'),{left:(offsetLeft_ + (wsizeLeft+pxsize)/2)+'px',top:top_+'px'});//南
			Css(fzmt('n-resize'),{left:(offsetLeft_ + (wsizeLeft+pxsize)/2)+'px'});//北
			Css(this.obj,{width:wsizeLeft+'px',height:wsizeTop+'px'});
			Css(fzmt('ne-resize'),{left:left_+'px'});//东北
			Css(fzmt('sw-resize'),{top:top_+'px'});//西南
		}else if(selectObj.id=='sw-resize'){//西南
			var left_ = e.clientX - this.initLeft ;
			var top_ = e.clientY - this.initTop ;
			var offsetLeft_ = fzmt('ne-resize').offsetLeft ;
			var offsetTop_ = fzmt('ne-resize').offsetTop ;
			var wsizeLeft = offsetLeft_ - left_ - pxsize ;
			var wsizeTop = top_ - offsetTop_ - pxsize ;
			Css(selectObj,{left:left_+'px',top:top_+'px'});
			Css(fzmt('e-resize'),{top: (offsetTop_ + (wsizeTop+pxsize)/2)+'px'});//东
			Css(fzmt('w-resize'),{left:left_+'px',top:(offsetTop_ + (wsizeTop+pxsize)/2)+'px'});//西
			Css(fzmt('s-resize'),{left:(offsetLeft_ - (wsizeLeft+pxsize)/2)+'px',top:top_+'px'});//南
			Css(fzmt('n-resize'),{left:(offsetLeft_ - (wsizeLeft+pxsize)/2)+'px'});//北
			Css(this.obj,{width:wsizeLeft+'px',height:wsizeTop+'px',left:(this.startLeft+e.clientX-this.startClientX)+'px'});
			Css(fzmt('se-resize'),{top:top_+'px'});//东南
			Css(fzmt('nw-resize'),{left:left_+'px'});//西北
		}else if(selectObj.id=='nw-resize'){//西北
			var left_ = e.clientX - this.initLeft ;
			var top_ = e.clientY - this.initTop ;
			var offsetLeft_ = fzmt('se-resize').offsetLeft ;
			var offsetTop_ = fzmt('se-resize').offsetTop ;
			var wsizeLeft = offsetLeft_ - left_ - pxsize ;
			var wsizeTop = offsetTop_ - top_ - pxsize ;
			Css(selectObj,{left:left_+'px',top:top_+'px'});
			Css(fzmt('e-resize'),{top: (offsetTop_ - (wsizeTop+pxsize)/2)+'px'});//东
			Css(fzmt('w-resize'),{left:left_+'px',top:(offsetTop_ - (wsizeTop+pxsize)/2)+'px'});//西
			Css(fzmt('s-resize'),{left:(offsetLeft_ - (wsizeLeft+pxsize)/2)+'px'});//南
			Css(fzmt('n-resize'),{left:(offsetLeft_ - (wsizeLeft+pxsize)/2)+'px',top:top_+'px'});//北
			Css(this.obj,{width:wsizeLeft+'px',height:wsizeTop+'px',left:(this.startLeft+e.clientX-this.startClientX)+'px',top:(this.startTop+e.clientY-this.startClientY)+'px'});
			Css(fzmt('ne-resize'),{top:top_+'px'});//东北
			Css(fzmt('sw-resize'),{left:left_+'px'});//西南
		}else if(selectObj.id=='ne-resize'){//东北
			var left_ = e.clientX - this.initLeft ;
			var top_ = e.clientY - this.initTop ;
			var offsetLeft_ = fzmt('sw-resize').offsetLeft ;
			var offsetTop_ = fzmt('sw-resize').offsetTop ;
			var wsizeLeft = left_ - offsetLeft_ - pxsize ;
			var wsizeTop = offsetTop_ - top_ - pxsize ;
			Css(selectObj,{left:left_+'px',top:top_+'px'});
			Css(fzmt('e-resize'),{left:left_+'px',top: (offsetTop_ - (wsizeTop+pxsize)/2)+'px'});//东
			Css(fzmt('w-resize'),{top:(offsetTop_ - (wsizeTop+pxsize)/2)+'px'});//西
			Css(fzmt('s-resize'),{left:(offsetLeft_ + (wsizeLeft+pxsize)/2)+'px'});//南
			Css(fzmt('n-resize'),{left:(offsetLeft_ + (wsizeLeft+pxsize)/2)+'px',top:top_+'px'});//北
			Css(this.obj,{width:wsizeLeft+'px',height:wsizeTop+'px',top:(this.startTop+e.clientY-this.startClientY)+'px'});
			Css(fzmt('nw-resize'),{top:top_+'px'});//西北
			Css(fzmt('se-resize'),{left:left_+'px'});//东南
		}
    }
});
var Css = function(e,o){ 
    for(var i in o) 
    e.style[i] = o[i]; 
}; 
var fzmt = function (id){
	if(id == null || id == undefined){return null } ;
	return document.getElementById(id);
}