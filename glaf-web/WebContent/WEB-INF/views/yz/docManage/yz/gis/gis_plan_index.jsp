<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.glaf.core.util.RequestUtils,com.glaf.core.identity.User"%>
<% 
	User user = RequestUtils.getUser(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>GISSystem</title>
    <style type="text/css">
    html, body {
	    
    }
    .td_textColor{
    	color:red;
    }
	    .listTable{
		border:1px solid #000000;
		cellspacing:0; 
		cellpadding:1; 
		width:100%;
		border-collapse:collapse;
		border-color:#111111;
	}
		.listTable td{
		border:1px solid #000000;
	}
	.middle{
		vertical-align:middle;
	}
	.ms{position:absolute; bottom:0;left:0; width:100%; height:25px;  background:#000; filter:progid:DXImageTransform.Microsoft.Alpha(opacity=30);}
	.ms_right{position:absolute; top:0;right:0; width:100%; font-size:20px;font-weight: bold;}
	
	#panorama {width:100%; height: 500px;}
	
	#result {width:100%;font-size:12px;}
    </style>
    <script type="text/javascript" src="<%=request.getContextPath() %>/scripts/jquery.min.js"></script>
	<!-- easyui -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/scripts/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/scripts/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/scripts/easyui/themes/demo.css">
	<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/easyui/jquery.easyui.min.js"></script>
	<!-- gis插件 -->
	<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/Silverlight.js"></script>
    <link href="<%=request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
    <!-- ztree -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/scripts/ztree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="<%=request.getContextPath() %>/scripts/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/ztree/js/jquery.ztree.core.min.js"></script>
	
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=HNQgLseKGp9mlIWDWnjOLwy8"></script>
	
    <script type="text/javascript">
    	var setting = {
			data:{
				simpleData:{
					enable:true
				}
			},
			callback:{
				onClick: zTreeOnClick
			}
		};
		function zTreeOnClick(event, treeId, treeNode) {
		    //alert(treeNode.type);
		    slCtl.Content.mainForm.SelectUnit(treeNode.id,'#003333CC');
		    $("#index_id").val(treeNode.id);
		    
		    if(treeNode.name != null){
            	if(treeNode.name.indexOf("桥")>0 || treeNode.name.indexOf("立交")>0){
	            		$('#qdiv').css("display","");
	            		$('#sdiv').css("display","none");
	            		$('#otherDiv').css("display","none");
	            	}else if(treeNode.name.indexOf("隧道")>0){
	            		$('#sdiv').css("display","");
	            		$('#qdiv').css("display","none");
	            		$('#otherDiv').css("display","none");
	            	}else{
	            		$('#otherDiv').css("display","");
	            		$('#qdiv').css("display","none");
	            		$('#sdiv').css("display","none");
	            	}
            }
		    
		     //获取该节点下的图片信息
        	getImg();
		};
    
    	var url;
    	var slCtl = null;
    	var tasksDataSource;
    	$(document).ready(function(){
    		$('#b').window({    
			    width:615,    
			    height:440,
			    padding:5,
			    collapsible:false,
			    minimizable:false,
			    maximizable: true,    
			    modal:true,
			    title:'基本信息'
			    ,closed: true
			});
			
			$('#imgDiv').window({    
			    width:625,    
			    height:445,
			    padding:5,
			    collapsible:false,
			    minimizable: false,
			    maximizable: false,    
			    modal:true,
			    title:'实景图'
			    ,closed:true
			    ,fit: true
			});
    	
			$('#tabDiv').tabs({    
			    border:false,    
			    onSelect:function(title,index){
			    	if(title == '质量'){
			    		$.post('<%=request.getContextPath()%>/rs/gisInfoRest/getGisConf',{key:'PMT',code:'quatity'},function(result){
					 		if(result != null && result.length>0){
					 			 alert(result[0].url);
					 		}
						});
			    	}else if(title == '安全'){
			    		
			    	}
			        if(index == 5){
			        
			        }
			    }    
			}); 
    	
			 $('#a').click(function(){
			 	alert("跳转到不合格的分项列表页面");
			 });
			 
			 $('#imgUpload').click(function(){
			 	$("#userForm2").submit();
			 	$("#ImgUploadDiv").empty();
			 	/**清空文件域内容*/ 
			 	var file = $("#file") 
				file.after(file.clone().val("")); 
				file.remove();
			 	setTimeout(getImg,1000);
			 });
			 
			  $('#imgClear').click(function(){
				if(window.confirm("确认要清空吗？")){
				    $("#imgDiv1").empty();  
				    $.post('<%=request.getContextPath()%>/rs/gisInfoRest/deleteAll',{index_id:$("#index_id").val()},function(result){
					   	//alert(result);
					});
				}  
			 });
			 
			 $('#btn').bind('click', function(){    
		        aa();
		    });  
		    
		    $(document).keydown(function(event) {
			    if (event.keyCode == 13) {
			    	aa();  
			    }
			});
		        
		    $('#bb').combobox({    
			    url:'<%=request.getContextPath()%>/rs/gisInfoRest/showLineList',    
			    valueField:'lineName',    
			    textField:'lineName'   
			    ,height:26
			    ,editable:false 
			    ,onSelect:function(rec){
			    	var url = '<%=request.getContextPath()%>/rs/gisInfoRest/showList?userid=<%=user.getActorId()%>&lineName='+encodeURI(rec.lineName,"UTF-8");    
		            $('#cc').combobox('reload', url);  
			    }
			    ,onLoadSuccess:function(){
			    	//alert("bb onLoadSuccess......");
			    	var val = $('#bb').combobox('getValue');
					var url = '<%=request.getContextPath()%>/rs/gisInfoRest/showList?userid=<%=user.getActorId()%>&lineName='+encodeURI(val,"UTF-8");    
		            $('#cc').combobox('reload', url); 
				}
			});    
			
			$('#cc').combobox({    
			    valueField:'indexId',    
			    textField:'projName'   
			    ,height:26
			    ,editable:false 
			    ,onSelect:function(record){
					 var val = $('#cc').combobox('getValue');
					 $('#systemName').val(val);
		    		$.post('<%=request.getContextPath()%>/rs/gisInfoRest/getGisInfoForPlanIndex',{systemName:val,tag:1},function(result){
					    $.fn.zTree.init($("#treeDemo"), setting, result);
						var treeObj = $.fn.zTree.getZTreeObj("treeDemo"); 
						treeObj.expandAll(true);
					});
					if(val != ''){
					 	slCtl.Content.mainForm.SelectPOI(val,'#ff3333CC');
					 	setTimeout("slCtl.Content.mainForm.SelectPOI("+val+",'#003333CC')",1000);
					 }
				}
				,onLoadSuccess:function(){
					var val = $('#cc').combobox('getValue');
					$('#systemName').val(val);
		            $.post('<%=request.getContextPath()%>/rs/gisInfoRest/getGisInfoForPlanIndex',{systemName:val,tag:1},function(result){
					    $.fn.zTree.init($("#treeDemo"), setting, result);
						var treeObj = $.fn.zTree.getZTreeObj("treeDemo"); 
						treeObj.expandAll(true);
					});
					if(slCtl != undefined){
						slCtl.Content.mainForm.SelectPOI(val,'#ff3333CC');
						setTimeout("slCtl.Content.mainForm.SelectPOI("+val+",'#003333CC')",1000);
					}
				}
			});  
			
			
			//图片上传
			j = 1;  
	        $("#btn_add2").click(function(){  
	            $("#ImgUploadDiv").append('<div id="div_'+j+'"><input  name="file_'+j+'" type="file"  /><input type="button" value="删除"  onclick="del_2('+j+')"/></div>');  
	              j = j + 1;  
	        });  
	        
	        $("#BYCinerama").click(function(){
	        	var index_id = $("#index_id").val();
	        	$('#BYCinerama').attr('href','<%=request.getContextPath()%>/mx/docManage/yz/gisInfo?method=goToBYCinerama&type=swf&index_id='+index_id+''); 
	        	
	        });
    	})
    	
    	function changeMap(mapVal){
            if(mapVal == 0){
            	alert("显示卫星");
            	//slCtl.Content.mainForm.ChangeMap("http://zygs.fzmt.com.cn:6084/arcgis/rest/services/fjs/jtrc/MapServer");
            }else if(mapVal == 1){
            	alert("显示普通");
            	//slCtl.Content.mainForm.ChangeMap("http://192.168.10.202:6080/arcgis/rest/services/JT_NANPING/jt_nanping/MapServer");
            }
    	}
    	
    	function del_2(o){  
		         document.getElementById("ImgUploadDiv").removeChild(document.getElementById("div_"+o));  
		    }
    	
    	function showBigImg(fileId) { 
		    	var src = "<%=request.getContextPath()%>/mx/lob/lob/download?fileId="+fileId;
		    	//alert(src);
		    	$("#realImg").attr("src",src);
		    	$("#imgDiv").window('open');
			} 
    	
    	function getImg_bak(){
    		var imgUrlStr = "";
	        var id = $("#index_id").val();
	        $.post('<%=request.getContextPath()%>/rs/gisInfoRest/getGisImgInfo',{id:id},function(data){
        		$("#imgDiv1").html("");
        		 for(var i=0,l=data.length;i<l;i++){  
				     var url=data[i].imgUrl;
				     imgUrlStr += "<img src='"+url+"' style='width:100px;height:100px;cursor:hand' onclick='showBigImg(this.src)' ></img>&nbsp;";
				}  
				$('#imgDiv1').html(imgUrlStr);
			});
    	}
    	
    	function getImg(){
    		var imgUrlStr = "";
	        var id = $("#index_id").val();
	        $.post('<%=request.getContextPath()%>/rs/gisInfoRest/getGisImgInfo1',{id:id,type:'img'},function(data){
        		$("#imgDiv1").html("");
        		 for(var i=0,l=data.length;i<l;i++){  
				     var name=data[i].imgName;
				     var fileId=data[i].file_id;
				     var url = "<%=request.getContextPath()%>/mx/lob/lob/download?fileId="+fileId;
				     imgUrlStr += "<img src='"+url+"' style='width:100px;height:100px;cursor:hand' onclick=showBigImg('"+fileId+"') ></img>&nbsp;";
				}  
				$('#imgDiv1').html(imgUrlStr);
			});
    	}
    	
    	function aa(){
    		var a = $("#searchWord").val();
    		var systemName = $("#systemName").val();
    		if(systemName == ''){
    			alert("请先选择标段");return;
    		}
    		$.post("<%=request.getContextPath()%>/rs/gisInfoRest/getGisInfoForPlanIndex",{systemName:systemName,name:a,tag:1},function(result){
			    $.fn.zTree.init($("#treeDemo"), setting, result);
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo"); 
				treeObj.expandAll(true);
			});
    }
    	
        function SilverlightLoaded(sender) {
        	slCtl = sender.getHost();
        }

        function onSilverlightError(sender, args) {
            var appSource = "";
            if (sender != null && sender != 0) {
              appSource = sender.getHost().Source;
            }
            
            var errorType = args.ErrorType;
            var iErrorCode = args.ErrorCode;

            if (errorType == "ImageError" || errorType == "MediaError") {
              return;
            }

            var errMsg = "Silverlight 应用程序中未处理的错误 " +  appSource + "\n" ;

            errMsg += "代码:"+ iErrorCode + "    \n";
            errMsg += "类别:" + errorType + "       \n";
            errMsg += "消息:" + args.ErrorMessage + "     \n";

            if (errorType == "ParserError") {
                errMsg += "文件:" + args.xamlFile + "     \n";
                errMsg += "行:" + args.lineNumber + "     \n";
                errMsg += "位置:" + args.charPosition + "     \n";
            }
            else if (errorType == "RuntimeError") {           
                if (args.lineNumber != 0) {
                    errMsg += "行:" + args.lineNumber + "     \n";
                    errMsg += "位置:" +  args.charPosition + "     \n";
                }
                errMsg += "方法名称:" + args.methodName + "     \n";
            }

            throw new Error(errMsg);
        }
        var baseText = null; 
											
        function ShowPopup(index_id){ 
           $('#b').window('open');
        }

        function queryclick() {
            var a = document.getElementById("searchWord").value;
            slCtl.Content.mainForm.QueryPOI(a,'');
        }
        
        var a = 1;
        var t;
        function CompleteMap() {   
        	if(a == 1){
        		selBD();
        		a = 0;
        	}  
		}
		
		function selBD(){
				//alert("selBD");
				if(slCtl != undefined){
					var val = $('#cc').combobox('getValue');
					if(val != ''){
						try{ 
							setTimeout("slCtl.Content.mainForm.SelectPOI("+val+",'#003333CC')",1000);
						} 
						catch (e){ 
							alert(e.message); 
						} 
					 }
				}
			}
    </script>
</head>
<body style="margin:0;padding:0" class="easyui-layout">
	<div data-options="region:'north',split:true,border:false" style="position:relative;width:100%;height:32px;overflow:hidden;text-align:center;" id="nDiv">
		<p>
		 <input id="bb" name="bb">
		 <input id="cc" name="cc">  
		<input type="text" value="" id="searchWord" style="width:300px;height:20px"/> <a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	</div>
	<div data-options="region:'west',split:true,border:false" style="position:relative;width:260px;overflow:auto;" id="treeDemo" class="ztree">
		<!-- <table id="dg" width="100%" border="0"></table> -->  
	</div>
	<div data-options="region:'center',split:true,maximizable:true,collapsible:true,border:false"  style="overflow:hidden">
			<span class="ms_right">
		   		<div align="right" style="margin: 5px">
		   			<img id="nImg" src="<%=request.getContextPath() %>/ClientBin/images/nMap.jpg" title="显示普通地图" style="cursor: hand" onclick="changeMap(1)"/> <br>
		   			<img id="wImg" src="<%=request.getContextPath() %>/ClientBin/images/wMap.jpg" title="显示卫星图" style="cursor: hand" onclick="changeMap(0)"/> 
		   		</div>
		    </span>
	    <object data="data:application/x-silverlight-2," type="application/x-silverlight-2" width="100%" height="100%">
			  <param name="source" value="<%=request.getContextPath() %>/ClientBin/GISSystem.xap"/>
			  <param name="onError" value="onSilverlightError" />
			  <param name="onLoad" value="SilverlightLoaded" / >
			  <param name="background" value="white" />
	          <param name="windowless" value="true" /> 
			  <param name="minRuntimeVersion" value="5.0.61118.0" />
			  <param name="autoUpgrade" value="true" />
	          <param name="initParams" value="MapType = 1, MapUrl=http://192.168.10.202:6080/arcgis/rest/services/JT_NANPING/jt_nanping/MapServer, FeatureLayer=48, area=117.85|26.53|119.11|27.16" />
			  <a href="http://go.microsoft.com/fwlink/?LinkID=149156&v=5.0.61118.0" style="text-decoration:none">
	 			  <img src="http://go.microsoft.com/fwlink/?LinkId=161376" alt="获取 Microsoft Silverlight" style="border-style:none"/>
			  </a>
		    </object>
	</div>
	<input type="hidden" id="object_id" value=""/>
	<input type="hidden" id="object_name" value=""/>
	<input type="hidden" id="systemName" value=""/>
	<div id="b"><!-- 基本信息弹出窗 -->
		<div id="tabDiv" style=""> 
		    <div title="实景图片" style="padding:5px;" id="rImgDiv">   
		      <div name="上传按钮div" style="display:">
		     		<a id="btn_add2" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">增加一行</a>
		     		<a id="imgUpload" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">上传</a>  
		     		<a id="imgClear" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">清空</a>  
		      		<form id="userForm2" action="<%=request.getContextPath()%>/mx/docManage/yz/gisInfo/upload" enctype="multipart/form-data" method="post" target="imgPost">  
				   	 <input type="hidden" name="index_id" id="index_id" value="">
				   	 <input type="hidden" name="type" id="type" value="img">   
				        <div id="newUpload2">  
				            <input type="file" name="file" id="file">  
				        </div>  
				          <div id="ImgUploadDiv">  
				        </div>  
				    </form>   
				    </div>
				   <div id="imgDiv1"></div>
				   <iframe name="imgPost" style="display:none"></iframe>
				   <span class="ms">
				   <!-- <a style="position:absolute;" id="cinerama" href="<%=request.getContextPath()%>/mx/docManage/yz/gisInfo?method=goToBDCinerama&id=09002100001407121112005016X" target="_blank">&nbsp;&nbsp;进入全景>></a> -->
				   <a style="position:absolute;" id="BYCinerama" href="" target="_blank">&nbsp;&nbsp;进入全景>></a>
				   </span>
		    </div>   
		    <div title="质量" id=""  style="padding:5px;overflow:hidden">   
		    	<table class="listTable">
					<tr  align="center" style="height:30px;">
						<td width="25px" rowspan="5" class="middle">质量前期保证文件</td>
						<td width="25px" >内容</td>
						<td width="25px" >应报验数量</td>
						<td width="25px" >已报验数量</td>
						<td width="25px" >应审批数量</td>
						<td width="25px" >备注</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >施工方案</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >质量计划</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >质量目标</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >技术交底</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" rowspan="6"  class="middle">质量检验情况</td>
						<td width="25px" >内容</td>
						<td width="25px" >应检数量</td>
						<td width="25px" >已检数量</td>
						<td width="25px" >合格数量</td>
						<td width="25px" >合格率</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >检验项目</td>
						<td width="25px" >10</td>
						<td width="25px" >10</td>
						<td width="25px" >10</td>
						<td width="25px" >100%</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >检验点数</td>
						<td width="25px" >50</td>
						<td width="25px" >40</td>
						<td width="25px" >30</td>
						<td width="25px" >75%</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >内容</td>
						<td width="25px" >存在数量</td>
						<td width="25px" >处理情况</td>
						<td width="25px" >验收情况</td>
						<td width="25px" >备注</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >缺陷</td>
						<td width="25px" >2</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >质量事故</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" rowspan="5"  class="middle">评定情况</td>
						<td width="25px" ></td>
						<td width="25px" >≥95</td>
						<td width="25px" >85-95</td>
						<td width="25px" >≤85</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >分项评定得分比例</td>
						<td width="25px" >50%</td>
						<td width="25px" >25%</td>
						<td width="25px" >25%</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >分项评定得分数量</td>
						<td width="25px" >20</td>
						<td width="25px" >10</td>
						<td width="25px" >10</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" ></td>
						<td width="25px" >分项个数</td>
						<td width="25px" >一次合格数</td>
						<td width="25px" >一次合格率</td>
						<td width="25px" >返工率</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >评定</td>
						<td width="25px" >20</td>
						<td width="25px" >15</td>
						<td width="25px" >75%</td>
						<td width="25px" >25%</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" rowspan="4"  class="middle">材料情况</td>
						<td width="25px" ></td>
						<td width="25px" >应检次数</td>
						<td width="25px" >已检次数</td>
						<td width="25px" >检验频率</td>
						<td width="25px" >备注</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >材料频率</td>
						<td width="25px" >5</td>
						<td width="25px" class="td_textColor">4</td>
						<td width="25px" class="td_textColor">90%</td>
						<td width="25px" class="td_textColor">检测次数不够</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" ></td>
						<td width="25px" >应检数量</td>
						<td width="25px" >合格数量</td>
						<td width="25px" >合格率</td>
						<td width="25px" >备注</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >材料质量</td>
						<td width="25px" >20</td>
						<td width="25px" class="td_textColor">18</td>
						<td width="25px" class="td_textColor">90%</td>
						<td width="25px" class="td_textColor">材料有不合格</td>
					</tr>
				</table>	
	    	</div>
	    	<div title="费用" id=""  style="padding:5px;overflow:hidden">   
	    		<table  class="listTable">
					<tr  align="center" style="height:30px">
						<td width="25px" ></td>
						<td width="25px" >总量</td>
						<td width="25px" >已完成量</td>
						<td width="25px" >已完成百分比</td>
						<td width="25px" >已计量数量</td>
						<td width="25px" >已计量百分比</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >工程数量</td>
						<td width="25px" >1000万</td>
						<td width="25px" >600万</td>
						<td width="25px" >60%</td>
						<td width="25px" >400万</td>
						<td width="25px" >80%</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >计量支付</td>
						<td width="25px" >1000万</td>
						<td width="25px" >600万</td>
						<td width="25px" >60%</td>
						<td width="25px" >400万</td>
						<td width="25px" >80%</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >内业文件</td>
						<td width="25px" >500份</td>
						<td width="25px" >200份</td>
						<td width="25px" >40%</td>
						<td width="25px" >100份</td>
						<td width="25px" >50%</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" rowspan="3"  class="middle">工程计量情况</td>
						<td width="25px"  colspan="2">超计量</td>
						<td width="25px"  colspan="2">未计量</td>
						<td width="25px" >备注</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >数量</td>
						<td width="25px" >原因</td>
						<td width="25px" >数量</td>
						<td width="25px" >原因</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >20万</td>
						<td width="25px" ></td>
						<td width="25px" >200万</td>
						<td width="25px" ></td>
						<td width="25px" ></td>
					</tr>
				</table>
	    	</div>
	    	<div title="进度" id="qProgress"  style="padding:5px;overflow:hidden">   
	    	<div id="qdiv">
	    		<table class="listTable">
	    			<tr  align="center" style="height:30px">
						<td width="25px" rowspan="4"  class="middle">资源编制情况</td>
						<td width="25px" >内容</td>
						<td width="25px" >计划</td>
						<td width="25px" >实际</td>
						<td width="25px" >投入百分比</td>
						<td width="25px" >备注</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >人员</td>
						<td width="25px" >8</td>
						<td width="25px" >6</td>
						<td width="25px" >75%</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >设备</td>
						<td width="25px" >8</td>
						<td width="25px" >8</td>
						<td width="25px" >100%</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >计划文件</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" >100%</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" rowspan="8"  class="middle">进度情况</td>
						<td width="25px" >内容</td>
						<td width="25px" >计划完成量</td>
						<td width="25px" >累计完成量</td>
						<td width="25px" >完成百分比</td>
						<td width="25px" >进度情况</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >关键节点</td>
						<td width="25px" >10</td>
						<td width="25px" >6</td>
						<td width="25px" >60%</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" rowspan="2">实体进度</td>
						<td width="25px" >基础及下部构造完成</td>
						<td width="25px" >基础及下部构造完成</td>
						<td width="25px" >100%</td>
						<td width="25px" >完成</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >T梁安装10片</td>
						<td width="25px" class='td_textColor'>T梁安装8片</td>
						<td width="25px" class='td_textColor'>80%</td>
						<td width="25px" class='td_textColor'>滞后</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >工程数量</td>
						<td width="25px" >1000万</td>
						<td width="25px" >600万</td>
						<td width="25px" >60%</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >费用计量</td>
						<td width="25px" >1000万</td>
						<td width="25px" >600万</td>
						<td width="25px" >60%</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >内业文件</td>
						<td width="25px" >2000份</td>
						<td width="25px" >1000份</td>
						<td width="25px" >50%</td>
						<td width="25px" ></td>
					</tr>
				</table>
					</div>
					<div id="otherDiv">
		    			<table class="listTable">
			    			<tr  align="center" style="height:30px">
								<td width="25px" rowspan="4"  class="middle">资源编制情况</td>
								<td width="25px" >内容</td>
								<td width="25px" >计划</td>
								<td width="25px" >实际</td>
								<td width="25px" >投入百分比</td>
								<td width="25px" >备注</td>
							</tr>
							<tr  align="center" style="height:30px">
								<td width="25px" >人员</td>
								<td width="25px" >8</td>
								<td width="25px" >6</td>
								<td width="25px" >75%</td>
								<td width="25px" ></td>
							</tr>
							<tr  align="center" style="height:30px">
								<td width="25px" >设备</td>
								<td width="25px" >8</td>
								<td width="25px" >8</td>
								<td width="25px" >100%</td>
								<td width="25px" ></td>
							</tr>
							<tr  align="center" style="height:30px">
								<td width="25px" >计划文件</td>
								<td width="25px" >1</td>
								<td width="25px" >1</td>
								<td width="25px" >100%</td>
								<td width="25px" ></td>
							</tr>
							<tr  align="center" style="height:30px">
								<td width="25px" rowspan="8"  class="middle">进度情况</td>
								<td width="25px" >内容</td>
								<td width="25px" >计划完成量</td>
								<td width="25px" >累计完成量</td>
								<td width="25px" >完成百分比</td>
								<td width="25px" >进度情况</td>
							</tr>
							<tr  align="center" style="height:30px">
								<td width="25px" >关键节点</td>
								<td width="25px" >10</td>
								<td width="25px" >6</td>
								<td width="25px" >60%</td>
								<td width="25px" ></td>
							</tr>
							<tr  align="center" style="height:30px">
								<td width="25px" >工程数量</td>
								<td width="25px" >1000万</td>
								<td width="25px" >600万</td>
								<td width="25px" >60%</td>
								<td width="25px" ></td>
							</tr>
							<tr  align="center" style="height:30px">
								<td width="25px" >费用计量</td>
								<td width="25px" >1000万</td>
								<td width="25px" >600万</td>
								<td width="25px" >60%</td>
								<td width="25px" ></td>
							</tr>
							<tr  align="center" style="height:30px">
								<td width="25px" >内业文件</td>
								<td width="25px" >2000份</td>
								<td width="25px" >1000份</td>
								<td width="25px" >50%</td>
								<td width="25px" ></td>
							</tr>
						</table>
					</div>
					<div id="sdiv">
					<table class="listTable">
	    			<tr  align="center" style="height:30px">
						<td width="25px" rowspan="4"  class="middle">资源编制情况</td>
						<td width="25px" >内容</td>
						<td width="25px" >计划</td>
						<td width="25px" >实际</td>
						<td width="25px" >投入百分比</td>
						<td width="25px" >备注</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >人员</td>
						<td width="25px" >8</td>
						<td width="25px" >6</td>
						<td width="25px" >75%</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >设备</td>
						<td width="25px" >8</td>
						<td width="25px" >8</td>
						<td width="25px" >100%</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >计划文件</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" >100%</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" rowspan="8"  class="middle">进度情况</td>
						<td width="25px" >内容</td>
						<td width="25px" >计划完成量</td>
						<td width="25px" >累计完成量</td>
						<td width="25px" >完成百分比</td>
						<td width="25px" >进度情况</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >关键节点</td>
						<td width="25px" >10</td>
						<td width="25px" >6</td>
						<td width="25px" >60%</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" rowspan="3">实体进度</td>
						<td width="25px" >掘进550m</td>
						<td width="25px" >掘进560m</td>
						<td width="25px" >101.8%</td>
						<td width="25px" >超前</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >初支550m</td>
						<td width="25px" >初支560m</td>
						<td width="25px" >101.8%</td>
						<td width="25px" >超前</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >二衬400m</td>
						<td width="25px" >二衬380m</td>
						<td width="25px" >95%</td>
						<td width="25px"  class='td_textColor'>滞后</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >工程数量</td>
						<td width="25px" >1000万</td>
						<td width="25px" >600万</td>
						<td width="25px" >60%</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >费用计量</td>
						<td width="25px" >1000万</td>
						<td width="25px" >600万</td>
						<td width="25px" >60%</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >内业文件</td>
						<td width="25px" >1800份</td>
						<td width="25px" >1500份</td>
						<td width="25px" >83.3%</td>
						<td width="25px" ></td>
					</tr>
				</table>
					</div>
	    	</div>
	    	<div title="安全" id=""  style="padding:5px;overflow:hidden">   
	    		<table class="listTable">
					<tr  align="center" style="height:30px">
						<td width="25px" ></td>
						<td width="25px" ></td>
						<td width="25px" ></td>
						<td width="25px" ></td>
						<td width="25px" ></td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" rowspan="2"  class="middle">安全准备</td>
						<td width="25px" >文件</td>
						<td width="25px" >培训</td>
						<td width="25px" >设备检验</td>
						<td width="25px" >人员资质</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >5个</td>
						<td width="25px" >2次</td>
						<td width="25px" >2次</td>
						<td width="25px" >100%达标</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" rowspan="4"  class="middle">安全检查</td>
						<td width="25px" ></td>
						<td width="25px" >存在问题个数</td>
						<td width="25px" >处理情况</td>
						<td width="25px" >复查情况</td>
						<td width="25px" >备注</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >危险源</td>
						<td width="25px" >21</td>
						<td width="25px" >12</td>
						<td width="25px" >3</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >设备</td>
						<td width="25px" >20</td>
						<td width="25px" >3</td>
						<td width="25px" >3</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px" >人员</td>
						<td width="25px" >30</td>
						<td width="25px" >2</td>
						<td width="25px" >2</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px">安全资金</td>
						<td width="25px" >应使用金额</td>
						<td width="25px" >已使用金额</td>
						<td width="25px" >使用百分率</td>
						<td width="25px" ></td>
						<td width="25px" >备注</td>
					</tr>
					<tr  align="center" style="height:30px">
						<td width="25px"></td>
						<td width="25px" >80万</td>
						<td width="25px" >60万</td>
						<td width="25px" >75%</td>
						<td width="25px" ></td>
						<td width="25px" ></td>
					</tr>
				</table>
	    	</div>
	    	<div title="视频监控" id=""  style="padding:5px;overflow:hidden">
	    		 
	    	</div>
	    	<div title="监控量测" id=""  style="padding:5px;overflow:hidden"></div>   
		</div>
		<!-- 图片弹出窗 -->
		<div id="imgDiv" style="padding:5px">
			<img id="realImg" src="" alt="" style="width:100%;height:100%"/> 
		</div>
</body>
</html>
