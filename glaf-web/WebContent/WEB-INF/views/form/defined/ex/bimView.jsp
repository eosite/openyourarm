<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<title></title>

<!-- <link
	href="https://developer.api.autodesk.com/viewingservice/v1/viewers/style.min.css"
	rel="stylesheet" /> -->
	
	<link
	href="https://developer.api.autodesk.com/derivativeservice/v2/viewers/style.css?v=3.2.1"
	rel="stylesheet" />
	
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/plugins/bootstrap/jquery.core.extends.js"></script>
	
<!-- <script type="text/javascript"
	src="https://developer.api.autodesk.com/viewingservice/v1/viewers/three.min.js"></script> -->
	
<script type="text/javascript"
	src="https://developer.api.autodesk.com/derivativeservice/v2/viewers/three.min.js?v=3.2.1"></script>
	
<script type="text/javascript"
	src="https://developer.api.autodesk.com/derivativeservice/v2/viewers/viewer3D.min.js?v=3.2.1"></script>
	
<!-- <script type="text/javascript"
	src="https://developer.api.autodesk.com/viewingservice/v1/viewers/viewer3D.min.js"></script> -->
<script type="text/javascript"
	src="${contextPath}/scripts/jquery.bim.js"></script>
</head>
<body>
	<div class="mt-bim" id="mt-bim" style="width: 100%; height: 100%"></div>
	<c:if test="${param.id != null}">
		<div class="mt-bim-properties" style="display: none;"></div>
		<style type="text/css">
		.fixed {
			z-index : 9999;
			position: absolute; +
			top: expression(eval(document.body.scrollTop) +100);
		}
		</style>
		<script type="text/javascript">
		var $bim = $(".mt-bim"), $props = $(".mt-bim-properties");
			function GetTree() {
				return $bim.bim("getObjectTree");
			}

			var filter = {
				children : '',
				root : ''
			}, copy = function(f, t) {
				if (f) {
					for ( var k in f) {
						if (!(k in filter)) {
							t[k] = f[k];
						}
					}
				}
				return t;
			};

			function each(node, collection) {
				var o = copy(node, {});
				o.nodeId = o.id;
				delete o.id;
				(o.parentId == 0) && (o.parentId = -1);
				collection.push(o);
				if (node.children) {
					$.each(node.children, function(i, v) {
						each(v, collection);
					});
				}
				return collection;
			}
			
			function GetData(){
				var data = $props.data("props") || [],treeData;
				if (!data.length && (treeData = GetTree())//
						&& treeData.length) {
					return each(treeData[0], data);;
				}
				return data;
			}

			function save() {
				var data = GetData();
				if (!data || !data.length) {
					alert("还未加载完!");
					return false;
				}
				$.ajax({
					type : 'POST',
					url : '${contextPath}/mx/bim/saveMsg',
					data : {
						bimId : "${param.id}",
						tree : JSON.stringify(data)
					},
					success : function(ret) {
						alert("节点同步完毕!");
					}
				})
			}

			$(document).ready(function(){
				$bim.bim({
					tokenUrl : "${contextPath}/mx/bim/getToken",
					documentId : "urn:${param.id}",
					onLoadModelSuccess : function(model) {
						var data =[];
						IntervalProcess.wait(function(){
							data = GetData();
							return data && data.length;
						}, function(){
							if(data && data.length){
								$props.empty();
								$props.text(JSON.stringify(data))//
									.data("props", data);
								save();
							}
						}, 1000, 60);
					}
				});
				
				$(".save").on("click", save);
			});
		</script>
	</c:if>
</body>
</html>