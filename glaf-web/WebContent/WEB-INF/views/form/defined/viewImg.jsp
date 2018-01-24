<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.core.config.SystemConfig"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看图片</title>
<style type="text/css">
	body{
		margin: auto;
		text-align: center;
	}
</style>
</head>
<body>
	<img id="img" src="">
</body>
<script> 
	var s = '${param.srcl}' ;
	s = s.replace(/\@/ig, "&"),
	id = '${param.id}',
	width = window.innerWidth,
	height = window.innerHeight;
	var img = document.getElementById('img');

	var contextPath = '<%=request.getContextPath()%>' , 
		model = '${param.model}', 
		rp = '${param.rp}' , 
		id = '${param.id}' , 
		key = '${param.key}' , 
		url = '${param.url}' ,
		dbid = '${param.databaseId}' ,
		fid = '${param.fid}' ;
	if(id){
		s = "<%=request.getContextPath()%>/mx/form/imageUpload?method=downloadById&id="+id ;
	}

	if(fid && key && url){
		s = contextPath+"/mx/form/imageUpload?method=fileView&mode="+model+"&rp="+rp+"&id="+id+"&key="+key+"&url="+url+"&fid="+fid+"&databaseId="+dbid ;
	}
	img.setAttribute('src', s);
	img.onload = function(){
		/*var x = this.width, y = this.height;
		var w = {
			x : 'width',
			y : 'height'
		}
		var arg = 800;
		var mar = true;
		if(this[w.x]<this[w.y] && this[w.y]>800){
			var temp = w.x;
			w.x = w.y;
			w.y = temp;
			mar = false;
		}
		var _scale = this[w.y]/this[w.x];

		if(this[w.x]>arg){
			x = arg;
			y = Math.floor(arg*_scale);
		}
		this.style[w.x] = x+'px';
		this.style[w.y] = y+'px';

		if(mar){
			this.style.margin = Math.floor((800-y)/2)-2+'px 0px';
		}else{
			this.style.margin = Math.floor((800-x)/2)-2+'px 0px';
		}*/

		function initImgSize(e) {
			width = e? window.innerWidth:width;
			height = e? window.innerHeight:height;
			var img = document.getElementById("img");
			if ((height / width) < (img.height / img.width)) {
				if (img.height >= height || e) {
					//img.width ='';
					img.removeAttribute('width');
					img.height = height - 5;
				}
				/*$wrap.css({
						display: ''
				});*/
			} else {
				if (img.width >= width || e) {
					/*$wrap.css({
						display: 'flex'
					});*/
					img.width= width - 5;
					img.removeAttribute('height');
					//img.height='';
				}
			}

		}
 		window.onresize = initImgSize ;
		//$(window).resize(initImgSize);
		initImgSize();

		window.resizeTo(this.width+35,this.height+100); 

		window.moveTo((screen.width - this.width-35)/2, (screen.height - this.height-100)/2);
	}
</script>
</html>