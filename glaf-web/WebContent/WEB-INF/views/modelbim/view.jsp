<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="chrome=1;IE=edge">
    <meta name="viewport" content="user-scalable=no">

	<title>modelbim</title>
	<link rel="stylesheet" href="http://61.237.239.100:28181/CarsEngine/webgl/api?tk=34b1d50a52cc1a94cf0e785e08eaa8661b68a2044dfcb1ba&fn=CarsView.min.css" type="text/css" />
	<script type="text/javascript" src="http://61.237.239.100:28181/CarsEngine/webgl/api?tk=34b1d50a52cc1a94cf0e785e08eaa8661b68a2044dfcb1ba&fn=CarsView.min.js" charset="utf-8"></script>


<script >
        var tk;
        var toolbarStr="snapshot,view,render,point,measure,select,walkAndTurntable,cuttingplane,note";
        window.onload = function () {
        	var parent = window.parent;
    		var opt = parent[parent.iframeId];
    		
        //    var pro=new CarsView("temp","csr","FaceStandard_0315_1511577467847",toolbarStr,callback);
    		var pro=new CarsView("temp",opt.renderType,opt.model,toolbarStr,callback);
            pro.then(function(value){
            	
                tk=value;
                opt.token = tk;
            });

        }

        
        function callback(arr){
            //console.log(arr);
        	if(arr.length != 0){
        		var parent = window.parent;
        		var opt = parent[parent.iframeId];
        		opt.event.BimClick(arr);
        	}
        }

 </script>
</head>
<body>
<div id="temp" style="width:100%;height:800px;position:relative;">

</div>
</body>
</html>