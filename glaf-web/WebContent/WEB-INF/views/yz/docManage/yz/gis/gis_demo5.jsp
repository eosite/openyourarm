<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.glaf.core.util.RequestUtils,com.glaf.core.identity.User"%>
<% 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>GISSystem</title>
    <style type="text/css">
    html, body {
	    
    }
    #mT img{
    	width: 60px;height: 60px;cursor: pointer;background-image: url('<%=request.getContextPath() %>/ClientBin/images/back.png');
    }
   .ms_right1{position:absolute; top:10;right:10; width:25px; font-size:20px;font-weight: bold;}
   .ms_right{position:absolute; top:0;right:0; width:25px; font-size:20px;font-weight: bold;}
   .ms_left{position:absolute; top:10;left:10; width:91px; font-size:20px;font-weight: bold;}
   .listTable{
		border:1px solid #000000;
		cellspacing:0; 
		cellpadding:1; 
		width:100%;
		border-collapse:collapse;
		border-color:#111111;
		text-align: center;
	}
	.listTable td{
		border:1px solid #000000;
		vertical-align: middle;
	}
	.listTable tr{
		height: 30px;
	}
	.icon-video{
		background:url('<%=request.getContextPath() %>/ClientBin/images/u111.png') no-repeat center center;
	}
	.icon-up{
		background:url('<%=request.getContextPath() %>/ClientBin/images/up.png') no-repeat center center;
	}
	.icon-down{
		background:url('<%=request.getContextPath() %>/ClientBin/images/down.png') no-repeat center center;
	}
	.icon-left{
		background:url('<%=request.getContextPath() %>/ClientBin/images/left.png') no-repeat center center;
	}
	.icon-right{
		background:url('<%=request.getContextPath() %>/ClientBin/images/right.png') no-repeat center center;
	}
	.ms{position:absolute; bottom:0;left:0; width:100%; height:25px;  background:#000; filter:progid:DXImageTransform.Microsoft.Alpha(opacity=30);}
	.span1{
		font-size:13px;color:#186BAD;cursor: pointer 
	}
	.text1{
		border:0px;width: 35px;color: #0000FF;text-decoration: underline;
	}
	.font1{
		color: red;font-size:16px;text-decoration: underline;font-weight: bold;
	}
	.font2{
		background-color: #0099CC;color: white;
	}
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
	<!-- Layer -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/layer/layer.min.js"></script>
	<link href="<%=request.getContextPath()%>/scripts/layer/skin/layer.css" rel="stylesheet" />
    <script type="text/javascript">
    	var Netocx;
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
		
		var setting1 = {
			data:{
				simpleData:{
					enable:true
				}
			},
			callback:{
				onClick:zTreeOnClick1
			}
		};
		
		var setting2 = {
			data:{
				simpleData:{
					enable:true
				}
			},
			callback:{
				onClick:zTreeOnClick2
			}
		};
		
		var setting3 = {
			data:{
				simpleData:{
					enable:true
				}
			},
			callback:{
				onClick: zTreeOnClick3
			}
		};
		
		function zTreeOnClick(event, treeId, treeNode){
		   
		};
		
		function selArea(name){
			$("#bName").val(name);
		    $("#cName").val(name);
		    if(name == '全省')
		    	slCtl.Content.mainForm.ChangeCity("114.5|28.5|121.5|23.5");
		    else if	(name == '福州'){
		    	slCtl.Content.mainForm.ChangeCity("119|26.3|119.75|25.8");
		    }
		    clearFeature();	
		    $('#area_win').window('close');
		}
		
		function zTreeOnClick1(event, treeId, treeNode) {
		    alert(treeNode.name);
		};
		
		function zTreeOnClick2(event, treeId, treeNode) {
		    alert(treeNode.name);
		};
		
		function zTreeOnClick3(event, treeId, treeNode) {
		    slCtl.Content.mainForm.SelectUnit(treeNode.id,'#003333CC');
		};
		
		var zNodes =[
			{id:1, pId:0, name:"全省", open:true}
			,{id:11, pId:1, name:"福州市"}
			,{id:12, pId:1, name:"厦门市"}
			,{id:13, pId:1, name:"龙岩市"}
			,{id:14, pId:1, name:"南平市"}
			,{id:15, pId:1, name:"宁德市"}
			,{id:16, pId:1, name:"莆田市"}
			,{id:17, pId:1, name:"泉州市"}
			,{id:18, pId:1, name:"漳州市"}
		];
		
		var zNodes1 =[
			{id:1, pId:0, name:"全省在建高速公路", open:true}
			,{id:11, pId:1, name:"福州绕城高速公路东南段"}
			,{id:12, pId:1, name:"福州长平高速公路"}
			,{id:13, pId:1, name:"京台建瓯至闽侯高速公路福州段"}
		];
		
		var zNodes2 =[
			{id:1, pId:0, name:"全省在建高速公路", open:true}
			,{id:11, pId:1, name:"福州绕城高速公路东南段"}
			,{id:12, pId:1, name:"福州长平高速公路"}
			,{id:13, pId:1, name:"京台建瓯至闽侯高速公路福州段"}
		];
		
		var zNodes3 =[
			{id:1, pId:0, name:"A1合同段", open:true}
			,{id:28737, pId:1, name:"K4+872.5幕浦1#桥"}
			,{id:28738, pId:1, name:"K5+158幕浦2#桥"}
			,{id:28736, pId:1, name:"ZK1+157～ZK3+741曹阳山隧道"}
		];
		
		var zNodes4 =[
			{id:1, pId:0, name:"A2合同段", open:true}
			,{id:27998, pId:1, name:"K8+030  锦溪分离式大桥"}
			,{id:28000, pId:1, name:"浦口隧道"}
			,{id:61560, pId:1, name:"T2改路天桥"}
		];
		
		var zNodes5 =[
			{id:1, pId:0, name:"A3合同段", open:true}
			,{id:9, pId:1, name:"K4+100~K5+500路基工程"}
			,{id:78, pId:1, name:"K4+629.325龙泉寺大桥"}
			,{id:79, pId:1, name:"ZK5+491/ YK5+512~ZK6+900/YK6+900  浦口互通"}
			,{id:80, pId:1, name:"ZK5+491/YK5+512~ZK6+900/YK6+900  南峰隧道"}
		];
    
    	var url;
    	var slCtl = null;
    	var tasksDataSource;
    	$(document).ready(function(){
    		//alert($(window).height());
    		var t = $("#tree");
			t = $.fn.zTree.init(t, setting, zNodes);
			
			var t1 = $("#tree1");
			t = $.fn.zTree.init(t1, setting1, zNodes1);
			
			var t2 = $("#tree2");
			t = $.fn.zTree.init(t2, setting2, zNodes2);
			
			var a1Tree= $("#A1Tree");
			t = $.fn.zTree.init(a1Tree, setting3, zNodes3);
    	
			$('#tabDiv').tabs({    
			    border:false,    
			    onSelect:function(title,index){
			    	
			    }    
			}); 
			$('#point_win').window({   
				    width:700    
				    ,height:500  
				    ,modal: false 
				    ,border: false
				    ,collapsible: false
				    ,minimizable: false
				    ,maximizable: false
				    ,closable: true  
				    ,closed: true
				    ,title: '起点'
				});
			$('#problem_win').window({   
				    width:800    
				    ,height:600  
				    ,modal: false 
				    ,border: false
				    ,collapsible: false
				    ,minimizable: false
				    ,maximizable: false
				    ,closable: true  
				    ,closed: true
				    ,title: '问题详细信息'
				});
			$('#good_win').window({   
				    width:800    
				    ,height:600  
				    ,modal: false 
				    ,border: false
				    ,collapsible: false
				    ,minimizable: false
				    ,maximizable: false
				    ,closable: true  
				    ,closed: true
				    ,title: '工程亮点描述'
				});
			$('#vip_win').window({   
				    width:800    
				    ,height:600  
				    ,modal: false 
				    ,border: false
				    ,collapsible: false
				    ,minimizable: false
				    ,maximizable: false
				    ,closable: true  
				    ,closed: true
				    ,title: '重点关注工程描述'
				});	
			$('#imgDiv').window({    
			    width:800,    
			    height:$(window).height()<700?$(window).height()-100:700,
			    padding:5,
			    collapsible:false,
			    minimizable:false,
			    maximizable: true,    
			    modal: true
			    ,closed: true
			    ,title: '详细照片'
			});
			$('#imgDiv1').window({    
			    width:800,    
			    height:$(window).height()<700?$(window).height()-100:700,
			    padding:5,
			    collapsible:false,
			    minimizable:false,
			    maximizable: true,    
			    modal: true
			    ,closed: true
			    ,title: '详细照片'
			});
			$('#imgWin').window({   
				    width:800    
				    ,height:600  
				    ,modal: false 
				    ,border: false
				    ,collapsible: false
				    ,minimizable: false
				    ,maximizable: false
				    ,closable: true  
				    ,closed: true
				    ,title: '标准化建设情况'
				});
    		$('#win').window({   
				    width:800    
				    ,height:$(window).height()<700?$(window).height()-100:700  
				    ,modal: false 
				    ,border: false
				    ,collapsible: false
				    ,minimizable: false
				    ,maximizable: false
				    ,closable: true  
				    ,closed: true
				    ,title: ' '
				});
			$('#more_win').window({   
				    width: $(window).width()    
				    ,height: $(window).height()-20  
				    ,modal: false 
				    ,border: false
				    ,collapsible: false
				    ,minimizable: false
				    ,maximizable: false
				    ,closable: true  
				    ,closed: true
				    ,title: ' '
				});	
			$('#detail_win').window({    
					title: ' '
				    ,width:770    
				    ,height:590  
				    ,modal: false 
				    ,border: false
				    ,collapsible: false
				    ,minimizable: false
				    ,maximizable: false
				    ,closable: true  
				    ,closed: true
				    ,title: '详细信息'
				});				
			$('#vWin').window({    
			     width:500    
			    ,height:$(window).height()-30    
			    ,modal: false 
			    ,border: false
			    ,collapsible: false
			    ,minimizable: false
			    ,maximizable: false
			    ,closable: true  
			    ,closed: true
			    ,left:$(window).width()-500
				,top: 22
				,title: '工程建设概况'
			});
			$('#projProWin').window({    
			     width:500    
			    ,height:$(window).height()-30    
			    ,modal: false 
			    ,border: false
			    ,collapsible: false
			    ,minimizable: false
			    ,maximizable: false
			    ,closable: true  
			    ,closed: true
			    ,left:$(window).width()-500
				,top: 22
				,title: '工程建设问题'
			});
			$('#goodWin').window({    
			     width:500    
			    ,height:$(window).height()-30    
			    ,modal: false 
			    ,border: false
			    ,collapsible: false
			    ,minimizable: false
			    ,maximizable: false
			    ,closable: true  
			    ,closed: true
			    ,left:$(window).width()-500
				,top: 22
				,title: '工程建设亮点'
			});
			$('#vipProjWin').window({    
			     width:500    
			    ,height:$(window).height()-30    
			    ,modal: false 
			    ,border: false
			    ,collapsible: false
			    ,minimizable: false
			    ,maximizable: false
			    ,closable: true  
			    ,closed: true
			    ,left:$(window).width()-500
				,top: 22
				,title: '重点关注工程'
			});
			$('#video_win').window({   
			    width:825 
			    ,height:650  
			    ,modal: false 
			    ,border: false
			    ,collapsible: false
			    ,minimizable: false
			    ,maximizable: false
			    ,closable: true  
			    ,closed: true
			    ,title: '监控视频'
			    ,tools:'#video_toolBar'
			});
			$('#area_win').window({    
			     width:300  
			    ,height:70    
			    ,modal: false 
			    ,border: false
			    ,collapsible: false
			    ,minimizable: false
			    ,maximizable: false
			    ,closable: false  
			    ,closed: true
			    ,left:$(window).width()-1680
				,top: 55
				,title: ''
			});
			/**
			$('#tabDiv').tabs({    
			    border:false,    
			    onSelect:function(title,index){ 
			      
			    }    
			});
			*/ 	
			
			$('#win_tabDiv').tabs({    
			    border:false,    
			    onSelect:function(title,index){
			       
			    }    
			}); 
			$('#moreWin_tabDiv').tabs({    
			    border:false,    
			    onSelect:function(title,index){
			       
			    }    
			}); 
			$('#p').panel({    
			  width:480,    
			  height:150,    
			  title: '项目列表'
			});
			$('#p1').panel({    
			  width:480,    
			  height:450,    
			  title: '标段信息'
			});
			$('#p2').panel({    
			  width:480,    
			  height:150,    
			  title: '项目列表'
			});
			$('#p3').panel({    
			  width:480,    
			  height:450,    
			  title: '标段信息'
			}); 
			$('#p4').panel({    
			  width:480,    
			  height:450,    
			  title: '问题列表'
			});  
			$('#p5').panel({    
			  width:480,    
			  height:450,    
			  title: '工程建设亮点'
			}); 
			$('#p6').panel({    
			  width:245    
			  ,height:200 
			  ,title: '选择地区'
			  ,tools:'#t'
			});
			$('#p7').panel({    
			  width:245    
			  ,height:200    
			  ,title: '选择监控类型'
			});
			$('#p8').panel({    
			  width:245    
			  ,height:200    
			  ,title: '监控列表'
			  ,collapsible: true
			  ,collapsed: true
			});       
    	})
    	
    	function aa(){
    		clearFeature();
    		slCtl.Content.mainForm.ShowAddress("117", "http://192.168.10.203:8082/yz/ClientBin/images/u1172.png");
    	}
    	function bb(){
    		clearFeature();
    		slCtl.Content.mainForm.ShowAddress("112,113", "http://192.168.10.203:8082/yz/ClientBin/images/u1172.png");		
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
        
        function getType(tag) {  
              		
		}
		
		var x,y;
        function show_coords(event){
        	x=event.clientX;
			y=event.clientY;
        }
       
		var tag = 0;
		function showV(){
			if(tag == 0){
				clearFeature();
				$('#vWin').window('open');
				tag = 1;
				slCtl.Content.mainForm.ShowAddress("117", "http://192.168.10.203:8082/yz/ClientBin/images/u1172.png");
			}else if(tag == 1){
				$('#vWin').window('close');
				tag = 0;
			}
		}
		var tag1 = 0;
		function showV1(){
			if(tag1 == 0){
				clearFeature();
				$('#projProWin').window('open');
				slCtl.Content.mainForm.ShowAddress("54,42", "http://192.168.10.203:8082/yz/ClientBin/images/u1172.png");
				tag1 = 1;
			}else if(tag1 == 1){
				$('#projProWin').window('close');
				tag1 = 0;
			}
		}
		var tag2 = 0;
		function showV2(){
			if(tag2 == 0){
				clearFeature();
				$('#goodWin').window('open');
				slCtl.Content.mainForm.ShowAddress("97,113", "http://192.168.10.203:8082/yz/ClientBin/images/u1172.png");
				tag2 = 1;
			}else if(tag2 == 1){
				$('#goodWin').window('close');
				tag2 = 0;
			}
		}
		var tag3 = 0;
		function showV3(){
			if(tag3 == 0){
				clearFeature();
				$('#vipProjWin').window('open');
				slCtl.Content.mainForm.ShowAddress("55", "http://192.168.10.203:8082/yz/ClientBin/images/u1172.png");
				tag3 = 1;
			}else if(tag3 == 1){
				$('#vipProjWin').window('close');
				tag3 = 0;
			}
		}
		function showWin(id){
			//alert(id);
			if(id == 113){
				$.fn.zTree.init($("#A1Tree"), setting3, zNodes3);
				var treeObj = $.fn.zTree.getZTreeObj("A1Tree"); 
				treeObj.expandAll(true);
			}else if(id == 112){
				$.fn.zTree.init($("#A1Tree"), setting3, zNodes4);
				var treeObj = $.fn.zTree.getZTreeObj("A1Tree"); 
				treeObj.expandAll(true);
			}else{
				$.fn.zTree.init($("#A1Tree"), setting3, zNodes5);
				var treeObj = $.fn.zTree.getZTreeObj("A1Tree"); 
				treeObj.expandAll(true);
			}
			clearFeature();
			slCtl.Content.mainForm.ShowRoad(id);
			$('#win').window('open');
		}
		function showDetail(){
			$('#detail_win').window('open');
		}
		function showImg(){
			$('#imgWin').window('open');
		}
		function showBigImg(src) { 
	    	$("#realImg").attr("src",src);
	    	$("#imgDiv").window('open');
		}
		function showBigImg1(src) { 
	    	$("#realImg1").attr("src",src);
	    	$("#imgDiv1").window('open');
		}  
		function showProDetail(id){
			slCtl.Content.mainForm.ShowAddress(id, "http://192.168.10.203:8082/yz/ClientBin/images/u1172.png");
		}
		function showGoodWin(id){
			slCtl.Content.mainForm.ShowAddress(id, "http://192.168.10.203:8082/yz/ClientBin/images/u1172.png");
		}
		function showVipWin(){
			$('#vip_win').window('open');
		}
		function showBigDiv(tag){
			selArea('全省');
			if(tag == 1){
				//$("#plan").css("display","");
				//$("#video").css("display","none");
				//$("#tree").show();
				//$("#vWest").hide();
				$("#rBtn").show();
				$('#cc').layout('collapse','east');  
			}else if(tag == 2){
			
			}else if(tag == 3){
				//$("#plan").css("display","none");
				//$("#video").css("display","block");
				//$("#tree").hide();
				//$("#vWest").show();
				$("#rBtn").hide();
				$('#cc').layout('expand','east');  
			}else if(tag == 4){
			
			}
			clearFeature();
		}
		function showVideoWin(){
			$('#video_win').window('open');
			Play();
		}
		/**视频监控 start */
		function Play(){
			var szURL = "rtsp://192.168.10.17:554/PSIA/streaming/channels/101";
			Netocx.HWP_Play(szURL, "OmFkbWluOnFhendzeDEyMzQ1", 0, "", "");
		}
		function Stop() {
			var Netocx = document.getElementById("PreviewActiveX");
			Netocx.HWP_Stop(0);//0是通道号
		}
		function init() {
			Netocx = document.getElementById("PreviewActiveX");
			setTimeout(Play, 1000);
			//Play();
		}
		/**视频监控 end */
		function turn(tag){
			if(tag == 1){
				$("#uFr").attr("src","http://192.168.10.203:8686/services?direction=left&stop=0");
			}else if(tag == 2){
				$("#uFr").attr("src","http://192.168.10.203:8686/services?direction=right&stop=0");
			}else if(tag == 3){
				$("#uFr").attr("src","http://192.168.10.203:8686/services?direction=up&stop=0");
			}else if(tag == 4){
				$("#uFr").attr("src","http://192.168.10.203:8686/services?direction=down&stop=0");
			}
		}
		function stop(tag){
			if(tag == 1){
				$("#uFr").attr("src","http://192.168.10.203:8686/services?direction=left&stop=1");
			}else if(tag == 2){
				$("#uFr").attr("src","http://192.168.10.203:8686/services?direction=right&stop=1");
			}else if(tag == 3){
				$("#uFr").attr("src","http://192.168.10.203:8686/services?direction=up&stop=1");
			}else if(tag == 4){
				$("#uFr").attr("src","http://192.168.10.203:8686/services?direction=down&stop=1");
			}
		}
		function showVideo(tag){
			//slCtl.Content.mainForm.ShowVedio("福州", "桥梁", "工地试验室","", "http://localhost:34708/ClientBin/images/1.png");
			if(tag == 0){
				slCtl.Content.mainForm.ShowVedio("","", "","", "http://192.168.10.203:8082/yz/ClientBin/images/u111.png");
			}else if(tag == 1){
				$("#area").val("福州");
				slCtl.Content.mainForm.ShowVedio("福州", "", "","", "http://192.168.10.203:8082/yz/ClientBin/images/u111.png");
			}
		}
		function showVideo1(tag){
			var area = $("#area").val();
			if(tag == 0){
				slCtl.Content.mainForm.ShowVedio(area,"", "","", "http://192.168.10.203:8082/yz/ClientBin/images/u111.png");
			}else if(tag == 1){
				$("#type1").val("路基");
				slCtl.Content.mainForm.ShowVedio(area, "路基", "","", "http://192.168.10.203:8082/yz/ClientBin/images/u111.png");
			}else if(tag == 2){
				$("#type1").val("桥梁");
				slCtl.Content.mainForm.ShowVedio(area, "桥梁", "","", "http://192.168.10.203:8082/yz/ClientBin/images/u111.png");
			}
		}
		function showVideo2(tag){
			var area = $("#area").val();
			var type1 = $("#type1").val();
			if(tag == 0){
				slCtl.Content.mainForm.ShowVedio(area,type1, "","", "http://192.168.10.203:8082/yz/ClientBin/images/u111.png");
			}else if(tag == 1){
				$("#type2").val("业主中心试验室");
				slCtl.Content.mainForm.ShowVedio(area, type1, "业主中心试验室","", "http://192.168.10.203:8082/yz/ClientBin/images/u111.png");
			}else if(tag == 2){
				$("#type2").val("监理试验室");
				slCtl.Content.mainForm.ShowVedio(area, type1, "监理试验室","", "http://192.168.10.203:8082/yz/ClientBin/images/u111.png");
			}else if(tag == 3){
				$("#type2").val("工地试验室");
				slCtl.Content.mainForm.ShowVedio(area, type1, "工地试验室","", "http://192.168.10.203:8082/yz/ClientBin/images/u111.png");
			}
		}
		function markRoad(){
			slCtl.Content.mainForm.MarkRoad("http://192.168.10.203:8082/yz/ClientBin/images/q.png","http://192.168.10.203:8082/yz/ClientBin/images/z.png");
			slCtl.Content.mainForm.ShowRoad("117");
		}
		var point_tag = 0;
		function PointClick(id){
			if(id == undefined){
			
			}else if(("54,42").indexOf(id) != -1){
				$('#problem_win').window('open');
			}else if(("97,113").indexOf(id) != -1){
				$('#good_win').window('open');
			}else if(("55").indexOf(id) != -1){
				$('#vip_win').window('open');
			}else if(("起点").indexOf(id) != -1){
				$('#point_win').window('open');
				$('#point_win').panel('setTitle','起点');
				$("#point_start").show();
				$("#point_end").hide();
			}else if(("终点").indexOf(id) != -1){
				$('#point_win').window('open');
				$('#point_win').panel('setTitle','终点');
				$("#point_start").hide();
				$("#point_end").show();
			}else{
				slCtl.Content.mainForm.ShowRoad(id);
				$('#win').window('open');
			}
		} 
		function bak(){
			$('#point_win').window('open');
			if(point_tag == 0){
				$('#point_win').panel('setTitle','起点');
				$("#point_start").show();
				$("#point_end").hide();
				point_tag = 1;
			}else if(point_tag == 1){
				$('#point_win').panel('setTitle','终点');
				$("#point_start").hide();
				$("#point_end").show();
				point_tag = 0;
			}
		}
		function clearFeature(){
			slCtl.Content.mainForm.ClearFeature();
		}
		var a = 0;
		function showArea(){
			if(a == 0){
				$('#area_win').window('open');
				a = 1;
			}else if(a == 1){
				$('#area_win').window('close');
				a = 0;
			}
		}
		function hideDiv(){
			$('#cc').layout('collapse','east');  
		}
	 	function VedioClick(id) {            
	 		alert(id);        
	 	}
	 	function moreWin(){
	 		$('#more_win').window('open');
	 	}
    </script>
</head>
<body style="margin:0;padding:0;overflow: hidden" onmousedown="show_coords(event)" onload="init()">
	<input type="hidden" id="dName" value="">
	<input type="hidden" id="area" value="">
	<input type="hidden" id="type1" value="">
	<input type="hidden" id="type2" value="">
	<div id="tt" align="right" style="background-color: #E7F0FF;">
		<a href="#"  onclick="javascript: showBigDiv(1)" style="width: 80px">平面图</a>&nbsp;|
		<a href="#"  onclick="javascript: showBigDiv(2)" style="width: 80px">数据统计</a>&nbsp;|
		<a href="#"  onclick="javascript: showBigDiv(3)" style="width: 80px">视频监控</a>&nbsp;|
		<a href="#"  onclick="javascript: showBigDiv(4)" style="width: 140px;height: 20px">GIS信息设置</a>
	</div>
	<div id="video_toolBar" style="background-color: #E7F0FF;">
		<a href="#" style="width: 80px;">方向控制：</a>
		<a href="#"  onmousedown="javascript: turn(1)" onmouseup="stop(1)" class="icon-left"></a>
		<a href="#"  onmousedown="javascript: turn(2)" onmouseup="stop(2)" class="icon-right"></a>
		<a href="#"  onmousedown="javascript: turn(3)" onmouseup="stop(3)" class="icon-up"></a>
		<a href="#"  onmousedown="javascript: turn(4)" onmouseup="stop(4)" class="icon-down"></a>
	</div>
	<div id="t" style="background-color: #E7F0FF;">
		<a href="#"  onclick="javascript: hideDiv()" style="">&gt;&gt;</a>
	</div>
	<div id="cc" class="easyui-layout"  style="width:$(window).width();height:910px;display:  ">
		
		<div data-options="region:'center',split:true,collapsible:true,border:false,title:''"  style="overflow:hidden">
			<div id="sdf" class="easyui-layout" style="height:910px">   
			    <div data-options="region:'center'" style="padding:5px;">
			    	<span class="ms_left">
				   		<div align="left">
				   			<div style="width: 90px;height: 25px;border:1px solid #1357A2;background: #E8F1FF;vertical-align: middle;cursor: pointer" align="center" onclick="showArea()"><span style="font-size:13px">&nbsp;地区选择</span></div>
				   		</div>
		    		</span>
			    	<span class="ms_right" id="rBtn">
				   		<div align="right" style="">
				   			<div style="width: 25px;height: 140px;border:1px solid #1357A2;background: #E8F1FF;vertical-align: middle;" align="center" onclick="showV()"><span class="span1" >工程建设概况<p>&lt;</span></div>
				   			<div style="width: 25px;height: 140px;border:1px solid #1357A2;background: #E8F1FF;vertical-align: middle;" align="center" onclick="showV1()"><span class="span1">工程建设问题<p>&lt;</span></div>
				   			<div style="width: 25px;height: 140px;border:1px solid #1357A2;background: #E8F1FF;vertical-align: middle;" align="center" onclick="showV2()"><span class="span1">工程建设亮点<p>&lt;</span></div>
				   			<div style="width: 25px;height: 140px;border:1px solid #1357A2;background: #E8F1FF;vertical-align: middle;" align="center" onclick="showV3()"><span class="span1">重点关注工程<p>&lt;</span></div>
				   		</div>
			    	</span>
			    	
			    	<object data="data:application/x-silverlight-2," type="application/x-silverlight-2" width="100%" height="100%">
					  <param name="source" value="<%=request.getContextPath() %>/ClientBin/QCGIS.xap"/>
					  <param name="onError" value="onSilverlightError" />
					  <param name="onLoad" value="SilverlightLoaded" />
					  <param name="background" value="white" />
			          <param name="windowless" value="true" /> 
					  <param name="minRuntimeVersion" value="5.0.61118.0" />
					  <param name="autoUpgrade" value="true" />
			          <param name="initParams" value="MapType = 7, MapUrl=http://192.168.10.204:6080/arcgis/rest/services/fjs/jtrc8/MapServer, FeatureLayer=3|2|48, area=118.2|26|120|27" />
					  <a href="http://go.microsoft.com/fwlink/?LinkID=149156&v=5.0.61118.0" style="text-decoration:none">
			 			  <img src="http://go.microsoft.com/fwlink/?LinkId=161376" alt="获取 Microsoft Silverlight" style="border-style:none"/>
					  </a>
					</object>
			    </div>   
			</div>  
		</div>
		<div data-options="region:'east',split:true,border:false,title:'',collapsed: true" style="position:relative;width:250px;overflow:auto;">
			<div id="p6" style="padding:10px;">    
				<a href="" onclick="javascript: showVideo(0);return false;">全省</a>&nbsp;&nbsp;&nbsp;
				<a href="" onclick="javascript: showVideo(1);return false;">福州</a>&nbsp;&nbsp;&nbsp;
				<a href="" onclick="javascript: showVideo(2);return false;">龙岩</a>&nbsp;&nbsp;&nbsp;
				<a href="" onclick="javascript: showVideo(3);return false;">南平</a>&nbsp;&nbsp;&nbsp;
				<a href="" onclick="javascript: showVideo(4);return false;">宁德</a>&nbsp;&nbsp;&nbsp;
				<a href="" onclick="javascript: showVideo(5);return false;">莆田</a>&nbsp;&nbsp;&nbsp;
				<a href="" onclick="javascript: showVideo(6);return false;">泉州</a>&nbsp;&nbsp;&nbsp;
				<a href="" onclick="javascript: showVideo(7);return false;">厦门</a>&nbsp;&nbsp;&nbsp;
				<a href="" onclick="javascript: showVideo(8);return false;">漳州</a>		
			</div>
			<div id="p7" style="padding:10px;">    
				<div>
					现场监控：
					<a href="" onclick="javascript: showVideo1(0);return false;">全部</a>&nbsp;&nbsp;&nbsp;
					<a href="" onclick="javascript: showVideo1(1);return false;">路基</a>&nbsp;&nbsp;&nbsp;
					<a href="" onclick="javascript: showVideo1(2);return false;">桥梁</a>&nbsp;&nbsp;&nbsp;
					<a href="" onclick="javascript: showVideo1(3);return false;">隧道</a>&nbsp;&nbsp;&nbsp;
				</div>
				<div>
					试验监控：
					<a href="" onclick="javascript: showVideo2(0);return false;">全部</a>&nbsp;&nbsp;&nbsp;
					<a href="" onclick="javascript: showVideo2(1);return false;">业主中心试验室</a>&nbsp;&nbsp;&nbsp;
					<a href="" onclick="javascript: showVideo2(2);return false;">监理试验室</a>&nbsp;&nbsp;&nbsp;
					<a href="" onclick="javascript: showVideo2(3);return false;">工地试验室</a>&nbsp;&nbsp;&nbsp;
				</div>	
			</div>
			<div>
			<div id="p8" style="position:absolute;padding:10px;z-index: 2;">    
				<div><input type="checkbox" id="c1" name="c1" value=""/>福州绕城高速公路东南段</div>
				<div><input type="checkbox" id="c1" name="c1" value=""/>福州长平高速公路</div>
				<div><input type="checkbox" id="c1" name="c1" value=""/>京台线建瓯至闽侯高速公路福州段</div>
				<div><input type="checkbox" id="c1" name="c1" value=""/>夏成高速公路厦门段</div>
				<div><input type="checkbox" id="c1" name="c1" value=""/>南安至厦门高速公路厦门段</div>
			</div>
			<div style="position:absolute;z-index: 1;">	
				<table style="width: 100%;height: 100%;padding: 10px">
					<tr>
						<td>
							<img src="<%=request.getContextPath() %>/ClientBin/images/u111.png"></img>
						</td>
						<td>
							&nbsp;&nbsp;<a href="#" onclick="showVideoWin();return false;">曹阳山隧道</a>
						</td>
					</tr>
					<tr>
						<td>
							
						</td>
						<td>
							&nbsp;&nbsp;东南绕城高速A1合同段
						</td>
					</tr>
					<tr>
						<td>
							<img src="<%=request.getContextPath() %>/ClientBin/images/u111.png"></img>
						</td>
						<td>
							&nbsp;&nbsp;<a href="#" onclick="showVideoWin();return false;">幕浦1#桥</a>
						</td>
					</tr>
					<tr>
						<td>
							
						</td>
						<td>
							&nbsp;&nbsp;东南绕城高速A1合同段
						</td>
					</tr>
					<tr>
						<td>
							<img src="<%=request.getContextPath() %>/ClientBin/images/u111.png"></img>
						</td>
						<td>
							&nbsp;&nbsp;<a href="#" onclick="showVideoWin();return false;">浦口隧道</a>
						</td>
					</tr>
					<tr>
						<td>
							
						</td>
						<td>
							&nbsp;&nbsp;东南绕城高速A2合同段
						</td>
					</tr>
				</table>
			</div>
			</div>
		</div>
	</div>
	<!-- 
	<div id="video" class="easyui-layout"  style="width:1900px;height:910px;display:  " >
		
		
		 
		
	</div>
	 -->
	<div id="win" >
		<div id="win_tabDiv" >
			<div title="项目概况" style="padding:5px;" id="div1">
				<iframe src="http://192.168.10.121:8080/glaf//mx/form/page/viewPage?isPublish=true&id=e711d8639c574277ba4500cc927c4e14" width="100%" height="525px"></iframe>
			</div>   
			<div title="项目总体进度" style="padding:5px;" id="div2">
				<iframe src="http://192.168.10.121:8080/glaf//mx/form/page/viewPage?isPublish=true&id=4fc0c34b525f465ca7a211f95cea9f22" width="100%" height="525px"></iframe>
			</div>   
			<div title="项目形象进度" style="padding:5px;" id="div3">
				<table class="listTable">
				<tr class="font2">
					<td style="width: 20%">项目名称</td><td style="width: 15%">单位</td><td style="width: 15%">工程数量</td><td style="width: 15%">完成数量</td><td>完成百分比</td>
				</tr>
				<tr>
					<td>路基土石方</td><td>万方</td><td>4235</td><td>2753</td><td><div  class="easyui-progressbar" data-options="value: 65" style="width:300px;float: left"></div></td>
				</tr>
				<tr>
					<td>桥梁桩基</td><td>根</td><td>3250</td><td>2437</td><td><div  class="easyui-progressbar" data-options="value: 75" style="width:300px;float: left"></div></td>
				</tr>
				<tr>
					<td>墩柱</td><td>㎡</td><td>12356</td><td>4325</td><td><div  class="easyui-progressbar" data-options="value: 35" style="width:300px;float: left"></div></td>
				</tr>
				<tr>
					<td>T梁预制</td><td>片</td><td>7325</td><td>1538</td><td><div  class="easyui-progressbar" data-options="value: 21" style="width:300px;float: left"></div></td>
				</tr>
			</table>
			</div> 
			<div title="质量情况" style="padding:5px;" id="div4">
				<iframe src="http://192.168.10.121:8080/glaf//mx/form/page/viewPage?isPublish=true&id=6713c828693e4b1ba061fdbba7c9dbaa" width="100%" height="525px"></iframe>
			</div> 
			<div title="安全情况" style="padding:5px;" id="div5">
				<iframe src="http://192.168.10.121:8080/glaf//mx/form/page/viewPage?isPublish=true&id=4cf276dbc57b4f1cb20bf01775153a1d" width="100%" height="525px"></iframe>
			</div> 
			<!-- 
			<div title="费用情况" style="padding:5px;" id="div6">
				费用情况
			</div>
			 --> 
		</div>
	</div>
	<div id="more_win" >
		<div id="moreWin_tabDiv" >
			<div title="总体情况" style="padding:5px;" id="div4">
				<iframe src="http://192.168.10.121:8080/glaf//mx/form/page/viewPage?isPublish=true&id=60f2f5aa44f44bbc979307e208b6f7bd" width="100%" height="930px"></iframe>
			</div>   
			<div title="质量情况" style="padding:5px;" id="div5">
				<iframe src="http://192.168.10.121:8080/glaf//mx/form/page/viewPage?isPublish=true&id=538c4686dff848fcaeb4fa2f57fc5151" width="100%" height="930px"></iframe>
			</div>   
			<div title="安全情况" style="padding:5px;" id="div6">
				<iframe src="http://192.168.10.121:8080/glaf//mx/form/page/viewPage?isPublish=true&id=1ef480e0306a458495e846ee0afa24c2" width="100%" height="930px"></iframe>
			</div> 
		</div>
	</div>
	<div id="detail_win" >
		<div style="float: right"><a href="" onclick="showImg();return false;"><span style="font-weight: bold;">标准化建设情况>>></span></a></div>
		<iframe src="http://192.168.10.121:8080/glaf//mx/form/page/viewPage?isPublish=true&id=dabd88781a0443ae9a35347b31790ed8" width="100%" height="525px"></iframe>
	</div>
	<div id="vWin" >
		<div id="tabDiv">
			<div title="工程概况" style="padding:5px;" id="div1">
				<div>建设概况<hr></div>
				<div>
					<input type="text" id="bName" class="text1" value="">在建高速公路<span class="font1">20</span>个，其中本年度新开工<span class="font1">3</span>个，<input type="text" id="cName" class="text1" value="">高速公路累计建设里程<span class="font1">4300</span>公里，项目累计完成投资<span class="font1">199.4</span>亿元。
				</div>
				<div align="right"><a href="#" onclick="moreWin();return false;">详细信息&gt;&gt;</a></div>
				<br>
				<div>项目列表<hr></div>
				<div>
					 <table style="width: 100%;height: 100%;border: 1">
						<tr>
							<td><a href="#" onclick="showWin(117)">福州绕城高速公路东南段</a></td>
							<td align="right"><a href="#" onclick="markRoad()">起终点信息</a></td>
						</tr>
						<tr>
							<td colspan="2">
								<div style="float: left">进度信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><div  class="easyui-progressbar" data-options="value: 17.4" style="width:100px;float: left"></div>
							</td>
						</tr>
						<tr>
							<td><a href="#">福州长平高速公路</a></td>
							<td align="right"><a href="#">起终点信息</a></td>
						</tr>
						<tr>
							<td colspan="2">
								<div style="float: left">进度信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><div class="easyui-progressbar" data-options="value: 18.9" style="width:100px;float: left"></div>
							</td>
						</tr>
						<tr>
							<td><a href="#">京台线建瓯至闽侯高速公路福州段</a></td>
							<td align="right"><a href="#">起终点信息</a></td>
						</tr>
						<tr>
							<td colspan="2">
								<div style="float: left">进度信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><div class="easyui-progressbar" data-options="value: 78.6" style="width:100px;float: left"></div>
							</td>
						</tr>
					</table>
				</div>
			</div>   
			<div title="施工标段信息" style="padding:0px;" id="div2">
				<div id="p" style="padding:10px;">    
					<div id="tree1" class="ztree">
		
					</div>
				</div>
				<div id="p1" style="padding:10px;">    
					<table class="listTable">
						<tr>
							<td>标段号</td><td>施工单位全称</td><td>查看详细信息</td>
						</tr>
						<tr>
							<td><a href="#" onclick="showWin(113)">A1</a></td><td>中铁十七局集团第六工程有限公司</td><td><a href="#" onclick="showDetail();return false;">详细信息</a></td>
						</tr>
						<tr>
							<td><a href="#" onclick="showWin(112)">A2</a></td><td>中交一公局公路工程局有限公司</td><td><a href="#" onclick="showDetail();return false;">详细信息</a></td>
						</tr>
						<tr>
							<td><a href="#" onclick="showWin(117)">A3</a></td><td>中交一公局第六工程有限公司</td><td><a href="#" onclick="showDetail();return false;">详细信息</a></td>
						</tr>
					</table>
					<hr>
					<div id="A1Tree" class="ztree">
						
					</div>
				</div>
			</div>   
			<div title="监理标段信息" style="padding:0px;" id="div3">
				<div id="p2" style="padding:10px;">    
					<div id="tree2" class="ztree">
		
					</div>
				</div>
				<div id="p3" style="padding:10px;">    
					<table class="listTable">
						<tr>
							<td>标段号</td><td>监理单位全称</td><td>查看详细信息</td>
						</tr>
						<tr>
							<td>J1</td><td></td><td><a href="#" onclick="showDetail();return false;">详细信息</a></td>
						</tr>
						<tr>
							<td>J2</td><td></td><td><a href="#" onclick="showDetail();return false;">详细信息</a></td>
						</tr>
						<tr>
							<td>J3</td><td></td><td><a href="#" onclick="showDetail();return false;">详细信息</a></td>
						</tr>
					</table>
				</div>
			</div>   
		</div>
	</div>
	<div id="imgWin">
		<div>场地建设标准化</div>
		<div>
			<img src='<%=request.getContextPath() %>/ClientBin/images/u723.png' style='width: 100px;height: 100px;cursor: pointer ' onclick='showBigImg(this.src)' ></img>&nbsp;
			<img src='<%=request.getContextPath() %>/ClientBin/images/u725.png' style='width: 100px;height: 100px;cursor: pointer ' onclick='showBigImg(this.src)' ></img>&nbsp;
			<img src='<%=request.getContextPath() %>/ClientBin/images/u727.png' style='width: 100px;height: 100px;cursor: pointer ' onclick='showBigImg(this.src)' ></img>&nbsp;
		</div>
		<div>施工工艺标准化</div>
		<div>
			<img src='<%=request.getContextPath() %>/ClientBin/images/u729.png' style='width: 100px;height: 100px;cursor: pointer ' onclick='showBigImg(this.src)' ></img>&nbsp;
		</div>
	</div>
	<!-- 图片弹出窗 -->
	<div id="imgDiv" style="padding:5px">
		<img id="realImg" src="" alt="" style="width:100%;height:100%"/>   
	</div>
	<div id="imgDiv1" style="padding:0px">
		<img id="realImg1" src="" alt="" style="width:100%;height:100%"/>   
		<span class="ms">
		   <a style="position:absolute;" id="" href="http://map.qq.com/#pano=20011002141221101520800&heading=346&pitch=-5&zoom=1" target="_blank">&nbsp;&nbsp;进入全景>></a>
		 </span>
	</div>
	<!-- 视频监控窗 -->
	<div id="video_win" style="padding:5px">
		<embed type='application/hwp-webvideo-plugin' id='PreviewActiveX' width='800px' height='600px' name='PreviewActiveX' align='center' wndtype='1' playmode='0'>
		</embed>
	</div>
	<div id="projProWin" >
		<div id="p4" style="padding:10px;">    
			<table class="listTable">
				<tr>
					<td>序号</td><td>问题类型</td><td>问题描述</td>
				</tr>
				<tr>
					<td>1</td><td>征地拆迁</td><td><a href="#" onclick="showProDetail(54);return false;">曹阳山隧道进口路基征地问题</a></td>
				</tr>
				<tr>
					<td>2</td><td>征地拆迁</td><td><a href="#" onclick="showProDetail(42);return false;">幕浦1#桥-2#桥区间路基征地问题</a></td>
				</tr>
				<tr>
					<td>3</td><td>突发事件</td><td><a href="#" onclick="showProDetail();return false;">XXX隧道洞口坍塌</a></td>
				</tr>
			</table>
		</div>	
	</div>
	<div id="goodWin" style="padding: 10px">
		<div>
			<img style="position:absolute; z-index:1;" width="30" height="40" src="<%=request.getContextPath() %>/ClientBin/images/u1171.png" />
			<img style="position:absolute; z-index:2;top:45px; left:25px;" width="10" height="10"   src="<%=request.getContextPath() %>/ClientBin/images/u1173.png" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="showGoodWin(97);return false;">曹阳山隧道实现了隧道施工信息化，现代化，智能化</a>
		</div>
		<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;东南绕城高速 A1合同段 2014年12月10日</div>
		<div>
			<img style="position:absolute; z-index:1;" width="30" height="40" src="<%=request.getContextPath() %>/ClientBin/images/u1171.png" />
			<img style="position:absolute; z-index:2;top:90px; left:25px;" width="10" height="10"   src="<%=request.getContextPath() %>/ClientBin/images/u1173.png" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="showGoodWin(113);return false;">东南绕城实现了拌合站管规范化管理</a>
		</div>
		<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;东南绕城高速 A1合同段 2014年12月23日</div>
	</div>
	<div id="problem_win" >
		<div>
			<table class="listTable">
				<tr>
					<td class="font2" width="20%">问题名称</td><td> 曹阳山隧道进口路基征地问题</td>
				</tr>
				<tr>
					<td class="font2">所在项目</td><td> 东南绕城高速</td>
				</tr>
				<tr>
					<td class="font2">所在标段</td><td> A1</td>
				</tr>
				<tr>
					<td class="font2">所在乡镇</td><td> 连江县东湖镇</td>
				</tr>
				<tr>
					<td class="font2">问题描述</td><td> 曹阳山隧道进口路基段范围内还有一座水池尚未解决，无法提交用地，已严重影响该段路基施工进度；</td>
				</tr>
				<tr>
					<td class="font2">影响范围</td><td> 曹阳山隧道进口路基施工</td>
				</tr>
			</table>
		</div>
		<div>&nbsp;</div>
		<div>相关照片<hr></div>
		<div style="padding: 5px">
			<img src='<%=request.getContextPath() %>/ClientBin/images/u1071.png' style='width: 100px;height: 100px;cursor: pointer ' onclick='showBigImg(this.src)' ></img>&nbsp;
			<img src='<%=request.getContextPath() %>/ClientBin/images/u1073.png' style='width: 100px;height: 100px;cursor: pointer ' onclick='showBigImg(this.src)' ></img>&nbsp;
		</div>
	</div>
	<div id="good_win" >
		<div>
			<table class="listTable">
				<tr>
					<td class="font2" width="20%">工程亮点名称</td><td> 曹阳山隧道实现了隧道施工信息化、现代化、智能化</td>
				</tr>
				<tr>
					<td class="font2">所在项目</td><td> 东南绕城高速</td>
				</tr>
				<tr>
					<td class="font2">所在标段</td><td> A1</td>
				</tr>
				<tr>
					<td class="font2">亮点描述</td><td> 针对目前隧道施工安全管理现状，为了实现隧道的信息化、现代化、智能化管理，便于对进出隧道的人员和当前所处的位置做到实时定位掌控，使管理人员能够随时掌握施工现场人员的分布状况，进行更加合理的调度管理</td>
				</tr>
			</table>
		</div>
		<div>&nbsp;</div>
		<div>相关照片<hr></div>
		<div style="padding: 5px">
			<img src='<%=request.getContextPath() %>/ClientBin/images/u1134.png' style='width: 100px;height: 100px;cursor: pointer ' onclick='showBigImg(this.src)' ></img>&nbsp;
			<img src='<%=request.getContextPath() %>/ClientBin/images/u1136.png' style='width: 100px;height: 100px;cursor: pointer ' onclick='showBigImg(this.src)' ></img>&nbsp;
		</div>
	</div>
	<div id="vipProjWin" style="padding: 10px">
		<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;大于30m高边坡</div>
		<div>
			<img style="position:absolute; z-index:1;" width="30" height="40" src="<%=request.getContextPath() %>/ClientBin/images/u1171.png" />
			<img style="position:absolute; z-index:2;top:75px; left:25px;" width="10" height="10"   src="<%=request.getContextPath() %>/ClientBin/images/u1173.png" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="showVipWin();return false;">东南绕城高速公路A1合同段K1+366 右侧高边坡</a>
		</div>
		<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;特大桥</div>
		<div>
			<img style="position:absolute; z-index:1;" width="30" height="40" src="<%=request.getContextPath() %>/ClientBin/images/u1171.png" />
			<img style="position:absolute; z-index:2;top:110px; left:25px;" width="10" height="10"   src="<%=request.getContextPath() %>/ClientBin/images/u1173.png" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="showVipWin();return false;">东南绕城高速公路A1合同段 XXX特大桥</a>
		</div>
	</div>
	<div id="vip_win" >
		<div>
			<table class="listTable">
				<tr>
					<td class="font2" width="20%">工程类型</td><td>大于30m高边坡</td>
				</tr>
				<tr>
					<td class="font2">所在项目</td><td>东南绕城高速</td>
				</tr>
				<tr>
					<td class="font2">所在标段</td><td>A1</td>
				</tr>
				<tr>
					<td class="font2">工程描述</td><td></td>
				</tr>
			</table>
		</div>
		<div>&nbsp;</div>
		<div>相关照片<hr></div>
		<div style="padding: 5px">
			<img src='<%=request.getContextPath() %>/ClientBin/images/u1245.png' style='width: 200px;height: 200px;cursor: pointer ' onclick='showBigImg1(this.src)' ></img>&nbsp;
		</div>
	</div>
	<div id="area_win" style="padding:10px;overflow: hidden">
			<a href="#" onclick="javascript: selArea('全省');return false;">全省</a>&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="javascript: selArea('福州');return false;">福州</a>&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="javascript: selArea('龙岩');return false;">龙岩</a>&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="javascript: selArea('南平');return false;">南平</a>&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="javascript: selArea('宁德');return false;">宁德</a>&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="javascript: selArea('莆田');return false;">莆田</a>&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="javascript: selArea('泉州');return false;">泉州</a>&nbsp;&nbsp;&nbsp;<br>
			<a href="#" onclick="javascript: selArea('厦门');return false;">厦门</a>&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="javascript: selArea('漳州');return false;">漳州</a>		
	</div>
	<iframe id="uFr" src="" style="display: none"></iframe>
	<div id="point_win">
		<div id="point_start" class="easyui-layout" style="width:100%;height:100%">   
		    <div data-options="region:'north',title:'',split:true" style="height:100px;">
		    	起点信息
		    	<hr>
		    	<table class="listTable" style="height: 50px"><tr><td style="background-color: #E9F0F9">起点桩号</td><td>K0+000</td><td style="background-color: #E9F0F9">起点位置</td><td>连江洋门</td></tr></table>
		    </div>   
		    <div data-options="region:'center',title:''" style="padding:0px;background:#eee;overflow: hidden">
		    	<div id="point_tabs" class="easyui-tabs" style="width:683px;height:358px;">   
				    <div title="起点实景" style="padding:0px;width: 100%">   
				       <img src="<%=request.getContextPath() %>/ClientBin/images/u283.png" alt="" width="100%" height="100%"/>   
				       <span class="ms">
					   	<a style="position:absolute;" id="" href="http://map.baidu.com/#panoid=09002100001407121401349766X&panotype=street&heading=180.71&pitch=-6.72&l=13&tn=B_NORMAL_MAP&sc=0&newmap=1&shareurl=1&pid=09002100001407121401349766X" target="_blank">&nbsp;&nbsp;进入全景>></a>
					   </span>
				    </div>   
				    <div title="相连线路实景" data-options="" style="overflow:auto;padding:0px;width: 100%">   
				       <img src="<%=request.getContextPath() %>/ClientBin/images/u287.png" alt="" width="100%" height="100%"/>   
				       <span class="ms">
					   	<a style="position:absolute;" id="" href="" target="_blank">&nbsp;&nbsp;进入全景>></a>
					   </span> 
				    </div>   
				</div> 
		    </div>   
		</div>
		<div id="point_end" class="easyui-layout" style="width:100%;height:100%;display: none">   
		    <div data-options="region:'north',title:'',split:true" style="height:100px;">
		    	终点信息
		    	<hr>
		    	<table class="listTable" style="height: 50px"><tr><td style="background-color: #E9F0F9">起点桩号</td><td>K74+280</td><td style="background-color: #E9F0F9">起点位置</td><td>连江颜岐</td></tr></table>
		    </div>   
		    <div data-options="region:'center',title:''" style="padding:0px;background:#eee;overflow: hidden">
		    	<div id="point_tabs" class="easyui-tabs" style="width:683px;height:358px;">   
				    <div title="起点实景" style="padding:0px;width: 100%">   
				       <img src="<%=request.getContextPath() %>/ClientBin/images/u341.png" alt="" width="100%" height="100%"/>   
				       <span class="ms">
					   	<a style="position:absolute;" id="" href="" target="_blank">&nbsp;&nbsp;进入全景>></a>
					   </span>
				    </div>   
				    <div title="相连线路实景" data-options="" style="overflow:auto;padding:0px;width: 100%">   
				       <img src="<%=request.getContextPath() %>/ClientBin/images/u345.png" alt="" width="100%" height="100%"/>   
				       <span class="ms">
					   	<a style="position:absolute;" id="" href="" target="_blank">&nbsp;&nbsp;进入全景>></a>
					   </span> 
				    </div>   
				</div> 
		    </div>   
		</div>    
	</div>
</body>
</html>
