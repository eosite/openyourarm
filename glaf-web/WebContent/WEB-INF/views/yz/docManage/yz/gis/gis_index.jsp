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
			    title:'基本信息'
			    ,closed: true
			});
			
			$('#imgDiv').window({    
			    width:625,    
			    height:645,
			    padding:5,
			    collapsible:false,
			    minimizable:false,
			    maximizable:false,    
			    modal:true,
			    title:'实景图'
			    ,closed: true
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
            
            //图片上传
            $('#imgUpload').click(function(){
			 	$("#userForm2").submit();
			 	$("#ImgUploadDiv").empty();
			 	/**清空文件域内容*/ 
			 	var file = $("#file"); 
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
            
			j = 1;  
	        $("#btn_add2").click(function(){  
	            //document.getElementById("ImgUploadDiv").innerHTML+='<div id="div_'+j+'"><input  name="file_'+j+'" type="file"  /><input type="button" value="删除"  onclick="del_2('+j+')"/></div>';  
	            $("#ImgUploadDiv").append('<div id="div_'+j+'"><input  name="file_'+j+'" type="file"  /><input type="button" value="删除"  onclick="del_2('+j+')"/></div>');  
	              j = j + 1;  
	        });  
	        
	        //初始化下拉框控件
	        /***/
	        $('#cc').combobox({    
			    url:'<%=request.getContextPath()%>/rs/gisInfoRest/getGisConf?key=gis_url_conf&actorId=<%=user.getActorId()%>',    
			    valueField:'code',    
			    textField:'name'   
			    ,height: 26
			    ,editable: false 
			    ,onSelect:function(record){
					 var val = $('#cc').combobox('getValue');
					 $.post('<%=request.getContextPath()%>/rs/gisInfoRest/getGisConf',{key:'gis_url_conf',code:val},function(result){
				 		if(result != null && result.length>0){
				 			 slCtl.Content.mainForm.ChangeMap(result[0].url);
				 		}
					});
					gannt(val);
				}
				,onLoadSuccess :function(){
					//var val = $('#cc').combobox('getValue');
		        	//gannt(val); 
					
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
				var val = $('#cc').combobox('getValue');         
				$.post('<%=request.getContextPath()%>/rs/gisInfoRest/getGisConf',{key:'gis_url_conf',code:val},function(result){
			 		if(result != null && result.length>0){
			 			try{ 
			 			 	slCtl.Content.mainForm.ChangeMap(result[0].url);
			 			 	a = 0;
			 			 	gannt(val);
			 			 	//setTimeout('slCtl.Content.mainForm.ChangeMap('+result[0].url+')',1000);
			 			}catch(e){
			 				alert(e.message); 	
			 			}
			 		}
				});
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
    	
    	function aa(){
    		$("#gantt").empty();
    		var val = $('#cc').combobox('getValue');
    		var a = $("#searchWord").val();
    		$.get("<%=request.getContextPath()%>/rs/gisInfoRest/getGisGinttTree?systemName="+val,{name:a},function(result){
			    var gantt = $("#gantt").kendoGantt({
                   dataSource: result,
                    views: [
                       // "day",
                       // "week",
                        { type: "month", selected: true }
                    ],
                    columns: [
                        { field: "id", title: "序号", width: 50 },
                        { field: "title", title: "单位，分部或分项", editable: true, sortable: false },
                        { field: "start", title: "开工时间", format: "{0:MM/dd/yyyy}", width: 80, editable: true, sortable: false },
                        { field: "end", title: "完工时间", format: "{0:MM/dd/yyyy}", width: 80, editable: true, sortable: false }
                    ],
                    height: 500,
                    showWorkHours: false,
                    showWorkDays: false,
                    snap: false
                    ,editable: false
                    ,change: onChange
                }).data("kendoGantt");

                $(document).bind("kendo:skinChange", function() {
                    gantt.refresh();
                });
			  });
    	}
    	/***/	
    	function gannt(val){
    		 $("#gantt").empty();
    		var serviceRoot;
    		/**
    		if(keyWord != undefined)
    			serviceRoot = "<%=request.getContextPath()%>/rs/gisInfoRest/getGisGinttTree?systemName=6&name="+keyWord;
    		else
    			serviceRoot = "<%=request.getContextPath()%>/rs/gisInfoRest/getGisGinttTree?systemName=6";
    		*/
    		//alert(val)
    		serviceRoot = "<%=request.getContextPath()%>/rs/gisInfoRest/getGisGinttTree?systemName="+val;	
    		tasksDataSource = new kendo.data.GanttDataSource({
                    batch: false,
                    transport: {
                        read: {
                            url: serviceRoot,
                            contentType: "application/json",
                            dataType: "json"
                        },
                        parameterMap: function(options, operation) {
                            if (operation !== "read") {
                                return { models: kendo.stringify(options.models || [options]) };
                            }
                        }
                    },
                    schema: {
                        model: {
                            id: "id",
                            fields: {
                                id: { from: "id", type: "number" },
						        parentId: { from: "parentId", type: "number",defaultValue: null},
						        start: { from: "start", type: "date" },
						        end: { from: "end", type: "date" },
						        title: { from: "title", defaultValue: "", type: "string" },
						        percentComplete: { from: "percentComplete", type: "number" },
						        expanded: { from: "expanded", type: "boolean", defaultValue: true }
                            }
                        }
                    }
                });

                var gantt = $("#gantt").kendoGantt({
                   dataSource: tasksDataSource,
                    views: [
                        //"day",
                       // "week",
                        { type: "month", selected: true ,slotSize: 200,monthHeaderTemplate: "#=kendo.toString(start, 'yyyy年MM月')#" }
                        //,{ type: "year",slotSize: 2000,monthHeaderTemplate: "#=kendo.toString(start, 'yyyy年')#" }
                    ],
                    columns: [
                        { field: "id", title: "序号", width: 50 },
                        { field: "title", title: "单位，分部或分项", editable: true, sortable: false },
                        { field: "start", title: "开工时间", format: "{0:yyyy-MM-dd}", width: 80, editable: true, sortable: false },
                        { field: "end", title: "完工时间", format: "{0:yyyy-MM-dd}", width: 80, editable: true, sortable: false }
                    ],
                    height: 500,
                    showWorkHours: false,
                    showWorkDays: false,//workWeekStart:1,
                    snap: false
                    ,editable: false
                    ,change: onChange
                }).data("kendoGantt");

                $(document).bind("kendo:skinChange", function() {
                    gantt.refresh();
                });
    	}
    		
    	function onChange(e) {
            var gantt = e.sender;
            var selection = gantt.select();
			var dataItem;
            if (selection.length) {
                dataItem = gantt.dataItem(selection);
                //alert(dataItem.title);
            	slCtl.Content.mainForm.QueryPOI(dataItem.title,'');
            	//alert(dataItem.id);
            	$("#object_id").val(dataItem.id);
            	$("#object_name").val(dataItem.title);
            	$("#index_id").val(dataItem.id);
            	if(dataItem.title != null){
	            	if(dataItem.title.indexOf("桥")>0 || dataItem.title.indexOf("立交")>0){
	            		$('#qdiv').css("display","");
	            		$('#sdiv').css("display","none");
	            		$('#otherDiv').css("display","none");
	            	}else if(dataItem.title.indexOf("隧道")>0){
	            		$('#sdiv').css("display","");
	            		$('#qdiv').css("display","none");
	            		$('#otherDiv').css("display","none");
	            	}else{
	            		$('#otherDiv').css("display","");
	            		$('#qdiv').css("display","none");
	            		$('#sdiv').css("display","none");
	            	}
            	}
            }
            getImg();
        }
        
        function getImg(){
    		var imgUrlStr = "";
	        var id = $("#index_id").val();
	        //alert(id);
	        $.post('<%=request.getContextPath()%>/rs/gisInfoRest/getGisImgInfo',{id:id},function(data){
	            //alert("图片个数："+data.length);
        		$("#imgDiv1").html("");
        		 for(var i=0,l=data.length;i<l;i++){  
				     //alert(data[i].imgUrl);  
				     var url=data[i].imgUrl;
				     imgUrlStr += "<img src='"+url+"' style='width: 100px;height: 100px;cursor: hand' onclick='showBigImg(this.src)' ></img>&nbsp;";
				}  
				$('#imgDiv1').html(imgUrlStr);
			});
    	}
    	
        var slCtl = null;
        function SilverlightLoaded(sender) {
        	var jsonData;
        	$.get("<%=request.getContextPath()%>/rs/gisInfoRest/getGisGinttTree?systemName=6",{},function(data){
        		 //eval("(" + data + ")");
        		 for(var i=0,l=data.length;i<l;i++){  
				   for(var key in data[i]){  
				       //alert(key+":"+data[i][key]);  
				   }  
				}  
			});
        	jsonData = 
        	"[{\"QSZHString\":\"K10+890\",\"JSZHString\":\"K11+238\",\"QSZHValue\":0,\"JSZHValue\":0,\"Type\":\"1\",\"StateColor\":\"#3300FF00\"},"+
        	"{\"QSZHString\":\"K11+238\",\"JSZHString\":\"K11+424\",\"QSZHValue\":0,\"JSZHValue\":0,\"Type\":\"1\",\"StateColor\":\"#33808080\"},"+
        	"{\"QSZHString\":\"K11+424\",\"JSZHString\":\"K11+870\",\"QSZHValue\":0,\"JSZHValue\":0,\"Type\":\"1\",\"StateColor\":\"#339e855d\"}]";
            slCtl = sender.getHost();
            //slCtl.Content.mainForm.ShowProjectColor("[{\"QSZHString\":\"K10+890\",\"JSZHString\":\"K11+238\",\"QSZHValue\":0,\"JSZHValue\":0,\"Type\":\"1\",\"StateColor\":\"#3300FF00\"}]");
            //slCtl.Content.mainForm.ShowProjectColor("[{\"QSZHString\":\"K11+238\",\"JSZHString\":\"K11+624\",\"QSZHValue\":0,\"JSZHValue\":0,\"Type\":\"1\",\"StateColor\":\"#33808080\"}]");
            //slCtl.Content.mainForm.ShowProjectColor("[{\"QSZHString\":\"K11+624\",\"JSZHString\":\"K11+870\",\"QSZHValue\":0,\"JSZHValue\":0,\"Type\":\"1\",\"StateColor\":\"#339e855d\"}]");
            slCtl.Content.mainForm.ShowProjectColor(jsonData);
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
    </script>
</head>
<body style="margin:0;padding:0;">
<input type="hidden" id="object_id" value="<%=user.getActorId()%>"/>
<input type="hidden" id="systemName" value=""/>
<div id="aa" style="" align="center"><!---->GIS地图选择：<input id="cc" name="dept">   &nbsp;&nbsp; 查询内容：<input type="text" value="" id="searchWord" style="height: 18px"/> <a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> </div>

<div class="easyui-layout" style="width:100%;height: 1000px">
	<div data-options="region:'north',split:true,maximizable:true,collapsible:true,border:true,title:'地图展示'"  style="height: 500px;overflow: hidden" id="gisDiv">
	<!--  -->
	    <object data='data:application/x-silverlight-2' type='application/x-silverlight-2' width='100%' height='100%'>
			  <param name='source' value='<%=request.getContextPath() %>/ClientBin/GISSystem.xap'/>
			  <param name='onError' value='onSilverlightError' />
			  <param name='onLoad' value='SilverlightLoaded' / >
			  <param name='background' value='white' />
	          <param name='windowless' value='true' />  
			  <param name='minRuntimeVersion' value='5.0.61118.0' />
			  <param name='autoUpgrade' value='true' />
	        <%--  <param name='initParams' value='MapType = 1, MapUrl=http://192.168.10.202:6080/arcgis/rest/services/JT_NANPING/jt_nanping_dt/MapServer, FeatureLayer=4' />
	        http://zygs.fzmt.com.cn:6080/arcgis/rest/services/fzrcgs/rcgs_A1/MapServer
	        http://zygs.fzmt.com.cn:6080/arcgis/rest/services/JT_NANPING/jt_nanping_A2zdm/MapServer
	        --%>
	          <param name='initParams' id='initParams' value='MapType = 2, MapUrl=http://zygs.fzmt.com.cn:6080/arcgis/rest/services/JT_NANPING/jt_nanping_A2zdm/MapServer, FeatureLayer=0,MinimumResolution=1.000285, MaximumResolution=5.4221319' />
			  <a href='http://go.microsoft.com/fwlink/?LinkID=149156&v=5.0.61118.0' style='text-decoration:none'>
	 			  <img src='http://go.microsoft.com/fwlink/?LinkId=161376' alt='获取 Microsoft Silverlight' style='border-style:none'/>
			  </a>
		    </object>
	</div>	
	<div data-options="region:'center',split:true,border: false" style="position: relative;width: 100%;height: 100%;overflow: hidden" id="gantt"></div>

	<input type="hidden" id="object_id" value=""/>
	<input type="hidden" id="object_name" value=""/>
	
	<div id="w"><!-- 基本信息弹出窗 -->
		<div id="tabDiv" style="">   <!-- TAB页 -->
		    <div title="实景图片" style="padding:5px;" id="rImgDiv">   
		    <div name="上传按钮div" style="display: ">
		        <a id="btn_add2" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">增加一行</a>
		     		<a id="imgUpload" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">上传</a>  
		     		<a id="imgClear" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">清空</a>  
		      		<form id="userForm2" action="<%=request.getContextPath()%>/mx/docManage/yz/gisInfo/upload" enctype="multipart/form-data" method="post" target="imgPost">  
				   	 <input type="hidden" name="index_id" id="index_id" value="">  
				        <div id="newUpload2">  
				            <input type="file" name="file" id="file">  
				        </div>  
				          <div id="ImgUploadDiv">  
				        </div>  
				    </form>   
			</div>
				   <div id="imgDiv1"></div>
				   <iframe name="imgPost" style="display: none"></iframe>
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
					<div id="otherDiv">
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
					<div id="sdiv">
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
						<td width="25px" rowspan="3">实体进度</td>
						<td width="25px" >掘进550m</td>
						<td width="25px" >掘进560m</td>
						<td width="25px" >101.8%</td>
						<td width="25px" >超前</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >初支550m</td>
						<td width="25px" >初支560m</td>
						<td width="25px" >101.8%</td>
						<td width="25px" >超前</td>
					</tr>
					<tr  align="center" style="height: 30px">
						<td width="25px" >二衬400m</td>
						<td width="25px" >二衬380m</td>
						<td width="25px" >95%</td>
						<td width="25px"  class='td_textColor'>滞后</td>
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
						<td width="25px" >1800份</td>
						<td width="25px" >1500份</td>
						<td width="25px" >83.3%</td>
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
		    <div title="文档信息" id=""  style="padding:5px;">   
		    	
	    	</div>   
	    	<div title="示意图" style="padding:5px;" id="smDiv">   
		        <img id="smImg" src="" alt="" align="middle"  style="width: 100%;height: 100%" />
		    </div>
		</div>
	</div>
	<!-- 图片弹出窗 -->
	<div id="imgDiv" style="padding:5px">
		<img id="realImg" src="" alt="" style="width:100%;height:100%"/>   
	</div>
</div>

</body>
</html>
