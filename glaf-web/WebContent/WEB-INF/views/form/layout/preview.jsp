<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.form.core.domain.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
	FormLayout formLayout = (FormLayout) request.getAttribute("formLayout");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>表单设计元素实体列表</title>
<style type="text/css">
        html
        {
			 width:100%;
			 height:100%;
			 margin:0;
        }
        body
        {	
			width:100%;
            height:99%;
            margin:0; 
        }
		
</style>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript">

Array.prototype.getJsonObjByRow = function (minJson){
	var jsonArray = new Array();
	for(var i=0;i<this.length;i++){
		var json = this[i] ;
		if(json.rowIndex == minJson.rowIndex && json.rowSpan == minJson.rowSpan && (json.colIndex+json.colSpan-minJson.colIndex ==0 || minJson.colIndex+minJson.colSpan-json.colIndex ==0  || (json.colIndex == minJson.colIndex && json.colSpan == minJson.colSpan) ) ){
			jsonArray.push(json) ;
		}
	}
	return jsonArray ;
}
Array.prototype.getJsonObjByCol = function (minJson){
	var jsonArray = new Array();
	for(var i=0;i<this.length;i++){
		var json = this[i] ;
		if(json.colIndex == minJson.colIndex && json.colSpan == minJson.colSpan &&  (json.rowIndex+json.rowSpan-minJson.rowIndex ==0 || minJson.rowIndex+minJson.rowSpan-json.rowIndex ==0  || (json.rowIndex == minJson.rowIndex && json.rowSpan == minJson.rowSpan) ) ){
			jsonArray.push(json) ;
		}
	}
	return jsonArray ;
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


$(document).ready(function() {
		var jsonArray =  JSON.parse("<%=formLayout.getJson()%>");
		var divs = new Array(); //存放div集合
		var jsArrays = new Array() ;//存放需要渲染的各个div的javascript集合
		var count = jsonArray.length*2 ;//循环次数
		var breakJsons = new Array();//存放最小单位无同级元素的集合
		for(var h = 0 ; h<count ; h++){
			if(jsonArray.length == 1) break;
			//获取除breakJsons外，其包含的最小单位
			minJson = jsonArray.getMinJsonObj(breakJsons);
			//判断行坐标个数量 并且 colSpan 或 rowSpan 相同的
			var rows = jsonArray.getJsonObjByRow(minJson);
			var cols = jsonArray.getJsonObjByCol(minJson);
			//如果该最小单位无同级排列的其它元素，则排除，再次寻找
			if(rows.length == cols.length == 1){
					breakJsons.push(minJson);
					continue ;
			}
			breakJsons = new Array();//清空

			//var id = Math.random();
			var id = "fzmt1411" + h ;
			var jsArray = new Array() ;//用于渲染div的javascript
			jsArray.id = id ; //
			var panes = new Array() ;//kendoSplitter 的panes
			
			var newJson = Array();//把此div 作为新的json放入 jsonArray里面
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
				var pane = new Array();
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
				jsonArray.remove(row);//删掉已经创建的元素
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
				jsonArray.push(newJson);
				jsonArray.sort(sortByColIndex);//排序
				divs.push(divObj);
		
	}
	//写入div
	for (var i = 0;i<divs.length ; i++){
		document.body.appendChild(divs[i]);
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
 
});		
</script>
</head>
<body >
</body>
</html>