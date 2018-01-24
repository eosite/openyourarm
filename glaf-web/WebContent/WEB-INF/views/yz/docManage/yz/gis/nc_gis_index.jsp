<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.glaf.core.util.RequestUtils,com.glaf.core.identity.User"%>
<%@ page import="com.glaf.base.modules.BaseDataManager,com.glaf.base.modules.sys.model.BaseDataInfo"%>
<% 
	User user = RequestUtils.getUser(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
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
		border-collapse: collapse;
		border-color: #111111;
	}
		.listTable td{
		border:1px solid #000000;
	}
	.middle{
		vertical-align: middle;
	}
    </style>
	<!-- kendo 甘特图 -->
	<link href="<%=request.getContextPath()%>/scripts/kendoui/styles/kendo.common.min.css" rel="stylesheet" />
	<link href="<%=request.getContextPath()%>/scripts/kendoui/styles/kendo.default.min.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/scripts/kendoui/styles/kendo.dataviz.min.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/scripts/kendoui/styles/kendo.dataviz.default.min.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/scripts/kendoui/styles/kendo.rtl.min.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/scripts/kendoui/styles/kendo.mobile.all.min.css" rel="stylesheet" />
    
    <script type="text/javascript" src="<%=request.getContextPath() %>/scripts/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/kendoui/js/kendo.all.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/scripts/kendoui_zh_CN.js"></script>  
	
	<!-- easyui -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/scripts/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/scripts/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/scripts/easyui/themes/demo.css">
	
	<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/easyui/jquery.easyui.min.js"></script>
	
	<!-- 纵断面图插件 -->
	<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/Silverlight.js"></script>
	
    <link href="<%=request.getContextPath() %>/css/site.css" type="text/css" rel="stylesheet">
    <script type="text/javascript">
    
    	var url;
    	var tasksDataSource;
    	$(document).ready(function(){
    	
    		$('#w').window({    
			    width:715,    
			    height:540,
			    padding:5,
			    collapsible: false,
			    minimizable: false,
			    maximizable: false,    
			    modal:true,
			    title: '1'
			    ,closed: true
			});
			
			$('#imgDiv').window({    
			    //width: $(window).width(),    
			    //height: $(window).height(),
			    width:925,    
			    height:645,
			    padding:5,
			    collapsible: false,
			    minimizable: false,
			    maximizable: true,    
			    modal:true,
			    title:'实景图'
			    ,closed: true
			    ,onMaximize :function(){
			    	$('#imgDiv').panel('resize',{
						width: $(window).width()   
			    		, height: $(window).height()
					});
			    }
			});
    	
			$('#tabDiv').tabs({    
			    border:false,    
			    onSelect:function(title,index){
			        if(index == 5){
			        	var id = $("#object_id").val();
			        	var url = "<%=request.getContextPath()%>/mx/docManage/yz/gisInfo?method=getProjDocNum&systemName=6&id=" + id;
			        	var tab = $('#tabDiv').tabs('getSelected');
			        	//tab.panel('refresh', url);
			        }
			    }    
			}); 
    	
			 $('#a').click(function(){
			 	alert("跳转到不合格的分项列表页面");
			 });
			 
			 $('#btn').bind('click', function(){    
		        aa();
		    });  
		    
		    $(document).keydown(function(event){
			    if (event.keyCode == 13) {
			    	aa();  
			    }
			});
	        
    	});
    	
    	var a = 1;
    	function CompleteMap(){   
    		if(a == 1){
        		selBD();
        	}  
		}
		
		function selBD(){
			if(slCtl != undefined){
				
			}
		}
		
		function cm(){
			
		}	
    	
    	function del_2(o){  
		         document.getElementById("ImgUploadDiv").removeChild(document.getElementById("div_"+o));  
		    }
    	
    	function showBigImg(src) { 
		    	$("#realImg").attr("src",src);
		    	$("#imgDiv").window('open');
			} 
    	
        var slCtl = null;
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

            errMsg += "代码: "+ iErrorCode + "    \n";
            errMsg += "类别: " + errorType + "       \n";
            errMsg += "消息: " + args.ErrorMessage + "     \n";

            if (errorType == "ParserError") {
                errMsg += "文件: " + args.xamlFile + "     \n";
                errMsg += "行: " + args.lineNumber + "     \n";
                errMsg += "位置: " + args.charPosition + "     \n";
            }
            else if (errorType == "RuntimeError") {           
                if (args.lineNumber != 0) {
                    errMsg += "行: " + args.lineNumber + "     \n";
                    errMsg += "位置: " +  args.charPosition + "     \n";
                }
                errMsg += "方法名称: " + args.methodName + "     \n";
            }

            throw new Error(errMsg);
        }
        var baseText = null; 

        function ShowDetailForm(paramerObj) {
        	//$("#smDiv").css("display","none");
        	$('#tabDiv').tabs('disableTab', '示意图');
        	var pObj = eval('(' + paramerObj + ')');
        	//alert(pObj);
        	var type = pObj.Type;//1 点, 2 涵洞, 3 填方, 4 挖方
        	var name = $("#object_name").val();
        	//alert(type);
			$("#tabDiv").tabs('select', 0);//每次打开默认选中第一个标签页
			$('#w').window('open');
        }
        
        function queryclick() {
            var a = document.getElementById("searchWord").value;
            slCtl.Content.mainForm.QueryPOI(a,'');
        }
        
        
        function PointClick(name){
        	var imgUrlStr = "";
        	if(name == '1号风亭'){
        		imgUrlStr = "<img src='<%=request.getContextPath() %>/ClientBin/images/1.jpg' style='width:100px;height:100px;cursor:hand' onclick=showBigImg(this.src) ></img>&nbsp;";
        	}else if(name == '2号风亭'){
        		imgUrlStr = "<img src='<%=request.getContextPath() %>/ClientBin/images/2.jpg' style='width:100px;height:100px;cursor:hand' onclick=showBigImg(this.src) ></img>&nbsp;";
        	}else if(name == '2号风亭'){
        		imgUrlStr = "<img src='<%=request.getContextPath() %>/ClientBin/images/2.png' style='width:100px;height:100px;cursor:hand' onclick=showBigImg(this.src) ></img>&nbsp;";
        	}else if(name == '1号有盖出入口'){
        		imgUrlStr = "<img src='<%=request.getContextPath() %>/ClientBin/images/11.jpg' style='width:100px;height:100px;cursor:hand' onclick=showBigImg(this.src) ></img>&nbsp;";
        	}else if(name == '2号有盖出入口'){
        		imgUrlStr = "<img src='<%=request.getContextPath() %>/ClientBin/images/22.jpg' style='width:100px;height:100px;cursor:hand' onclick=showBigImg(this.src) ></img>&nbsp;";
        	}else if(name == '3号有盖出入口'){
        		imgUrlStr = "<img src='<%=request.getContextPath() %>/ClientBin/images/33.jpg' style='width:100px;height:100px;cursor:hand' onclick=showBigImg(this.src) ></img>&nbsp;";
        	}else if(name == '4号有盖出入口'){
        		imgUrlStr = "<img src='<%=request.getContextPath() %>/ClientBin/images/44.jpg' style='width:100px;height:100px;cursor:hand' onclick=showBigImg(this.src) ></img>&nbsp;";
        	}else if(name == '维护工程'){
        		imgUrlStr = "<img src='<%=request.getContextPath() %>/ClientBin/images/4.jpg' style='width:100px;height:100px;cursor:hand' onclick=showBigImg(this.src) ></img>&nbsp;";
        	}else if(name == '主体结构'){
        		imgUrlStr = "<img src='<%=request.getContextPath() %>/ClientBin/images/zt1.jpg' style='width:100px;height:100px;cursor:hand' onclick=showBigImg(this.src) ></img>&nbsp;";
        		imgUrlStr += "<img src='<%=request.getContextPath() %>/ClientBin/images/zt2.jpg' style='width:100px;height:100px;cursor:hand' onclick=showBigImg(this.src) ></img>&nbsp;";
        		imgUrlStr += "<img src='<%=request.getContextPath() %>/ClientBin/images/zt3.jpg' style='width:100px;height:100px;cursor:hand' onclick=showBigImg(this.src) ></img>&nbsp;";
        		imgUrlStr += "<img src='<%=request.getContextPath() %>/ClientBin/images/zt4.jpg' style='width:100px;height:100px;cursor:hand' onclick=showBigImg(this.src) ></img>&nbsp;";
        	}       
        	$('#smDiv').html(imgUrlStr);
        	$('#w').panel('setTitle',name);
        	$('#w').window('open'); 
        }
        
        function showBigImg(src) { 
	    	$("#realImg").attr("src",src);
	    	$("#imgDiv").window('open');
		} 
    </script>
</head>
<body style="margin:0;padding:0;">
<input type="hidden" id="object_id" value="<%=user.getActorId()%>"/>
<input type="hidden" id="systemName" value=""/>

<div class="easyui-layout" style="width:100%;height: 900px">
	<div data-options="region:'north',split:true,maximizable:true,collapsible:true,border:true,title:'地图展示'"  style="height: 900px;overflow: hidden" id="gisDiv">
	<!--  -->
	    <object data='data:application/x-silverlight-2' type='application/x-silverlight-2' width='100%' height='100%'>
			  <param name='source' value='<%=request.getContextPath() %>/ClientBin/QCGIS.xap'/>
			  <param name='onError' value='onSilverlightError' />
			  <param name='onLoad' value='SilverlightLoaded' / >
			  <param name='background' value='white' />
	          <param name='windowless' value='true' />  
			  <param name='minRuntimeVersion' value='5.0.61118.0' />
			  <param name='autoUpgrade' value='true' />
	          <param name='initParams' id='initParams' value='MapType = 6, MapUrl=http://zygs.fzmt.com.cn:6084/arcgis/rest/services/jxs/nanchang_dt2/MapServer, FeatureLayer=1,area=112.744|0.408|112.985|0.571' />
			  <a href='http://go.microsoft.com/fwlink/?LinkID=149156&v=5.0.61118.0' style='text-decoration:none'>
	 			  <img src='http://go.microsoft.com/fwlink/?LinkId=161376' alt='获取 Microsoft Silverlight' style='border-style:none'/>
			  </a>
		 </object>
	</div>	

	<input type="hidden" id="object_id" value=""/>
	<input type="hidden" id="object_name" value=""/>
	
	<div id="w"><!-- 基本信息弹出窗 -->
		<div id="tabDiv" style="">   <!-- TAB页 -->
		     <div title="示意图" style="padding:5px;" id="smDiv">   
		    </div>
		    <div title="质量" id=""  style="padding:5px;overflow: hidden">   
		    	<table class="listTable">
					<tr  align="center" style="height: 30px;">
						<td width="25px" rowspan="5" class="middle">质量前期保证文件</td>
						<td width="25px" >内容</td>
						<td width="25px" >应报验数量</td>
						<td width="25px" >已报验数量</td>
						<td width="25px" >应审批数量</td>
						<td width="25px" >备注</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >施工方案</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >质量计划</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >质量目标</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >技术交底</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" rowspan="6"  class="middle">质量检验情况</td>
						<td width="25px" >内容</td>
						<td width="25px" >应检数量</td>
						<td width="25px" >已检数量</td>
						<td width="25px" >合格数量</td>
						<td width="25px" >合格率</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >检验项目</td>
						<td width="25px" >10</td>
						<td width="25px" >10</td>
						<td width="25px" >10</td>
						<td width="25px" >100%</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >检验点数</td>
						<td width="25px" >50</td>
						<td width="25px" >40</td>
						<td width="25px" >30</td>
						<td width="25px" >75%</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >内容</td>
						<td width="25px" >存在数量</td>
						<td width="25px" >处理情况</td>
						<td width="25px" >验收情况</td>
						<td width="25px" >备注</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >缺陷</td>
						<td width="25px" >2</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >质量事故</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" rowspan="5"  class="middle">评定情况</td>
						<td width="25px" ></td>
						<td width="25px" >≥95</td>
						<td width="25px" >85-95</td>
						<td width="25px" >≤85</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >分项评定得分比例</td>
						<td width="25px" >50%</td>
						<td width="25px" >25%</td>
						<td width="25px" >25%</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >分项评定得分数量</td>
						<td width="25px" >6</td>
						<td width="25px" >10</td>
						<td width="25px" >10</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" ></td>
						<td width="25px" >分项个数</td>
						<td width="25px" >一次合格数</td>
						<td width="25px" >一次合格率</td>
						<td width="25px" >返工率</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >评定</td>
						<td width="25px" >6</td>
						<td width="25px" >15</td>
						<td width="25px" >75%</td>
						<td width="25px" >25%</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" rowspan="4"  class="middle">材料情况</td>
						<td width="25px" ></td>
						<td width="25px" >应检次数</td>
						<td width="25px" >已检次数</td>
						<td width="25px" >检验频率</td>
						<td width="25px" >备注</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >材料频率</td>
						<td width="25px" >5</td>
						<td width="25px" class="td_textColor">4</td>
						<td width="25px" class="td_textColor">90%</td>
						<td width="25px" class="td_textColor">检测次数不够</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" ></td>
						<td width="25px" >应检数量</td>
						<td width="25px" >合格数量</td>
						<td width="25px" >合格率</td>
						<td width="25px" >备注</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >材料质量</td>
						<td width="25px" >6</td>
						<td width="25px" class="td_textColor">18</td>
						<td width="25px" class="td_textColor">90%</td>
						<td width="25px" class="td_textColor">材料有不合格</td>
					</tr>
				</table>	
	    	</div>
	    	<div title="费用" id=""  style="padding:5px;overflow: hidden">   
	    		<table  class="listTable">
					<tr  align="center" style="height: 30px">
						<td width="25px" ></td>
						<td width="25px" >总量</td>
						<td width="25px" >已完成量</td>
						<td width="25px" >已完成百分比</td>
						<td width="25px" >已计量数量</td>
						<td width="25px" >已计量百分比</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >工程数量</td>
						<td width="25px" >1000万</td>
						<td width="25px" >600万</td>
						<td width="25px" >6%</td>
						<td width="25px" >400万</td>
						<td width="25px" >80%</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >计量支付</td>
						<td width="25px" >1000万</td>
						<td width="25px" >600万</td>
						<td width="25px" >6%</td>
						<td width="25px" >400万</td>
						<td width="25px" >80%</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >内业文件</td>
						<td width="25px" >500份</td>
						<td width="25px" >200份</td>
						<td width="25px" >40%</td>
						<td width="25px" >100份</td>
						<td width="25px" >50%</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" rowspan="3"  class="middle">工程计量情况</td>
						<td width="25px"  colspan="2">超计量</td>
						<td width="25px"  colspan="2">未计量</td>
						<td width="25px" >备注</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >数量</td>
						<td width="25px" >原因</td>
						<td width="25px" >数量</td>
						<td width="25px" >原因</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >20万</td>
						<td width="25px" ></td>
						<td width="25px" >200万</td>
						<td width="25px" ></td>
						<td width="25px" ></td>
					</tr>
				</table>
	    	</div>
	    	<div title="进度" id="qProgress"  style="padding:5px;overflow: hidden">   
	    	<div id="qdiv">
	    		<table class="listTable">
	    			<tr  align="center" style="height: 30px">
						<td width="25px" rowspan="4"  class="middle">资源编制情况</td>
						<td width="25px" >内容</td>
						<td width="25px" >计划</td>
						<td width="25px" >实际</td>
						<td width="25px" >投入百分比</td>
						<td width="25px" >备注</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >人员</td>
						<td width="25px" >8</td>
						<td width="25px" >6</td>
						<td width="25px" >75%</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >设备</td>
						<td width="25px" >8</td>
						<td width="25px" >8</td>
						<td width="25px" >100%</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >计划文件</td>
						<td width="25px" >1</td>
						<td width="25px" >1</td>
						<td width="25px" >100%</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" rowspan="8"  class="middle">进度情况</td>
						<td width="25px" >内容</td>
						<td width="25px" >计划完成量</td>
						<td width="25px" >累计完成量</td>
						<td width="25px" >完成百分比</td>
						<td width="25px" >进度情况</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >关键节点</td>
						<td width="25px" >10</td>
						<td width="25px" >6</td>
						<td width="25px" >6%</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" rowspan="2">实体进度</td>
						<td width="25px" >基础及下部构造完成</td>
						<td width="25px" >基础及下部构造完成</td>
						<td width="25px" >100%</td>
						<td width="25px" >完成</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >T梁安装10片</td>
						<td width="25px" class='td_textColor'>T梁安装8片</td>
						<td width="25px" class='td_textColor'>80%</td>
						<td width="25px" class='td_textColor'>滞后</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >工程数量</td>
						<td width="25px" >1000万</td>
						<td width="25px" >600万</td>
						<td width="25px" >6%</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >费用计量</td>
						<td width="25px" >1000万</td>
						<td width="25px" >600万</td>
						<td width="25px" >6%</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >内业文件</td>
						<td width="25px" >2000份</td>
						<td width="25px" >1000份</td>
						<td width="25px" >50%</td>
						<td width="25px" ></td>
					</tr>
				</table>
					</div>
	    	</div>
	    	<div title="安全" id=""  style="padding:5px;overflow: hidden">   
	    		<table class="listTable">
					<tr  align="center" style="height: 30px">
						<td width="25px" ></td>
						<td width="25px" ></td>
						<td width="25px" ></td>
						<td width="25px" ></td>
						<td width="25px" ></td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" rowspan="2"  class="middle">安全准备</td>
						<td width="25px" >文件</td>
						<td width="25px" >培训</td>
						<td width="25px" >设备检验</td>
						<td width="25px" >人员资质</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >5个</td>
						<td width="25px" >2次</td>
						<td width="25px" >2次</td>
						<td width="25px" >100%达标</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" rowspan="4"  class="middle">安全检查</td>
						<td width="25px" ></td>
						<td width="25px" >存在问题个数</td>
						<td width="25px" >处理情况</td>
						<td width="25px" >复查情况</td>
						<td width="25px" >备注</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >危险源</td>
						<td width="25px" >21</td>
						<td width="25px" >12</td>
						<td width="25px" >3</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >设备</td>
						<td width="25px" >6</td>
						<td width="25px" >3</td>
						<td width="25px" >3</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >人员</td>
						<td width="25px" >30</td>
						<td width="25px" >2</td>
						<td width="25px" >2</td>
						<td width="25px" ></td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px">安全资金</td>
						<td width="25px" >应使用金额</td>
						<td width="25px" >已使用金额</td>
						<td width="25px" >使用百分率</td>
						<td width="25px" ></td>
						<td width="25px" >备注</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px"></td>
						<td width="25px" >80万</td>
						<td width="25px" >60万</td>
						<td width="25px" >75%</td>
						<td width="25px" ></td>
						<td width="25px" ></td>
					</tr>
				</table>
	    	</div>
		</div>
	</div>
</div>
<!-- 图片弹出窗 -->
		<div id="imgDiv" style="padding:5px">
			<img id="realImg" src="" alt="" style="width:100%;height:100%"/> 
		</div>
</body>
</html>
