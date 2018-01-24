<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.business.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.ui.model.*"%>
<%@ page import="com.glaf.core.security.LoginContext"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%
	String context = request.getContextPath();
	com.glaf.base.utils.ContextUtil.getInstance().setContextPath(context);
	pageContext.setAttribute("contextPath", context);
	if (request.getAttribute("userTheme") != null) {
		UserTheme userTheme = (UserTheme) request.getAttribute("userTheme");
		request.setAttribute("theme", userTheme.getThemeStyle());
		request.setAttribute("homeTheme", userTheme.getHomeThemeStyle());
	} else {
		String theme = com.glaf.core.util.RequestUtils.getTheme(request);
		request.setAttribute("theme", theme);
		String homeTheme = com.glaf.core.util.RequestUtils.getHomeTheme(request);
		request.setAttribute("homeTheme", homeTheme);
	}
	LoginContext loginContext = RequestUtils.getLoginContext(request);
	request.setAttribute("loginContext", loginContext);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><%=SystemConfig.getString("res_system_name")%></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
		<link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-summernote/summernote.css" rel="stylesheet" type="text/css" />
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/css/components.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/apps/css/inbox.min.css" rel="stylesheet" type="text/css"/>
		<!--ztree-->
		<link rel="stylesheet" href="${contextPath}/scripts/ztree/css/metroStyle/metroStyle.css" type="text/css"/>
		<!--图片弹窗-->
		<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/fancybox/source/jquery.fancybox.css?v=2.1.5" media="screen" />
		<style>
		  .myalert{
				 width:500px;
				 position: absolute;
				 top: 40px;
				 bottom: auto;
				 right: 5px;
				 left: auto;
				 display: block;
				 z-index:9999;
			}    
		    .thumbnail{
				background-color: #eee;
			}
		   .thumbnailbt {
			      padding: 3px 10px;
				  color: #fff;
				  border: 1px #fff solid;
				  margin: -13px 5px 0;
				  background: transparent;
				  position: relative;
				  top: 50%;
				  font-size: 12px;
		   }
		   .thumbnail div{
			    position:relative;
				margin-top:-80px;
				height:80px !important;
		   }
		   .thumbnail h3{
			   text-align:center;font-size:14px;font-weight:400;color:#3e4d5c;
			   margin:3px;
		   }
		   .thumbnail:hover p{
			   display:block;
		   }
		   .thumbnail p{
			   display:none;
		   }
		   .thumbnail img{
			   min-width:150px;
			   min-height:200px;
		   }
		   .thumbnail p>a{
			  color:#fff;
		   }
		   .thumbnail p>a:hover{
			  background:#e84d1c;
			  color:#fff;
		   }
		   .templatecolumn{
			   padding-left:3px;
			   padding-right:3px;
		   }
		   .btn-toolbar button i {
			   line-height:20px !important;
		   }
		   .btn-toolbar .btn-group{
			   float:right;
		   }
		   .myFancyboxImage{
			   max-width:800px;
			   max-height:600px;
		   }
		   .mask{
			   display:none;
		   }
		   /**遮罩层样式*/
		   .thumbnail:hover .mask{
			  display:block;
			  background-color:rgba(0,0,0,0.8);
			  filter:alpha(opacity=40);
			  -moz-opacity:0.4;
			  opacity:0.4;
			  color:yellow;
			  font-weight:bold;
			  text-align:center;
			}
			.btn-toolbar .active {
			    z-index: 2;
			    color: #fff;
			    background-color: #337ab7;
			    border-color: #337ab7;
			    cursor: default;
			}
		
		.file {
		    position: relative;
		    display: inline-block;
		    background: #D0EEFF;
		    border: 1px solid #99D3F5;
		    border-radius: 4px;
		    padding: 4px 12px;
		    overflow: hidden;
		    color: #1E88C7;
		    text-decoration: none;
		    text-indent: 0;
		    line-height: 20px;
		}
		.file input {
		    position: absolute;
		    font-size: 100px;
		    right: 0;
		    top: 0;
		    opacity: 0;
		}
		.file:hover {
		    background: #AADFFD;
		    border-color: #78C3F3;
		    color: #004974;
		    text-decoration: none;
		}
		
		</style>
</head>
<body>
 	<div class="portlet light" style="margin:3px;3px;">
             <div class="portlet-title tabbable-line">
                 <div class="caption">
                     <i class="fa fa-gift"></i>图片列表
				 </div>
				 <div class="caption" style='margin-left:30px'>
                     
                     
				 </div>
				 <ul class="nav nav-tabs">
                    <li class="active">
                        <a href="#tab_0" data-toggle="tab" aria-expanded="false"> 附件图片列表</a>
                    </li>
                    <li class="">
                        <a href="#tab_1" data-toggle="tab" aria-expanded="false"> 编辑器图片列表</a>
                    </li>
                    <li class="">
                        <a href="#tab_2" data-toggle="tab" aria-expanded="false"> 图片列表</a>
                    </li>
                </ul>
             </div>
             <div class="portlet-body" style="background:#FAFAFA;">
                <div class="tab-content">
                	<div class="tab-pane active" id="tab_0">
                		<div class="col-md-12" id="content0"></div>
                	</div>                	
					<div class="tab-pane" id="tab_1">
						<div class="col-md-12" id="content1"></div>
					</div>
					<div class="tab-pane" id="tab_2">
					 <select name="uploadSel" class="uploadSel">  
					  <option value ="server" selected="selected">服务器目录</option>
					  <option value ="database">数据库</option>	  
					</select>
					<button id="uploadBtn" class="btn btn-sm blue file"><i class="fa fa-upload"></i> 选择文件
					<form id='uploadForm' nctype ="multipart/form-data">
					
                     <input  type="text" id='randomparent' name="randomparent" style='display:none' value='7ATTACH-UPLOAD-IMAGE' />
                     <input id='loadtx' type="file"  name="file" />
                    <input class='uploadType' type="text"  name="uploadType" style="display : none"/>
                     </form>
                     
                      <button id="uploadBtn" class="btn btn-sm blue file" style="margin-left:10px;" onclick="uploadFile()"><i class="fa fa-upload"  ></i> 上传
                    
                     </button> 
						<div class="col-md-12" id="content2"></div>
					</div>
                </div>
  			</div>
  </div>


  <template id="thumbnail_template">
     <div class="thumbnail">
	     <span class="mask"></span>
		 <img class="img-responsive" src="${contextPath}/images/a.jpg" >
		 <div class="caption">
			<p class="btarea" style="text-align:center;">
			   <!--<a href="#" class="btn thumbnailbt viewTemplateBt" role="button">
				  查看
			   </a>
			   <a href="#" class="btn thumbnailbt editTemplateBt" role="button">
				  编辑
			   </a>
			   -->
			   <a href="#" class="btn thumbnailbt applyTemplateBt" role="button">
				  选用
			   </a>
			</p>
		 </div>
		 <h3>模板1</h3>
	  </div>
  </template>


  <script type="text/javascript">
        var contextPath='${contextPath}';
        var id='${param.nameElementId}';
        var hiddenId='${param.objelementId}';
        var designerJson='${desingerJson}';
		var action='${param.action}'==''?'join':'${param.action}';
        </script>
        <!-- END FOOTER -->
        <!--[if lt IE 9]>
		<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/respond.min.js"></script>
		<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/excanvas.min.js"></script> 
		<![endif]-->
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
		<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-summernote/summernote.min.js" type="text/javascript"></script>
		<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-summernote/lang/summernote-zh-CN.js" type="text/javascript"></script>
        <script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.core.min.js"></script>
        <script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.excheck.min.js"></script>
        <script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.exedit.min.js"></script>
		<script type="text/javascript" src="${contextPath}/scripts/html2canvas/html2canvas.js"></script>
		<script type="text/javascript" src="${contextPath}/scripts/html2canvas/html2canvas.svg.js"></script>
		<script type="text/javascript" src="${contextPath}/scripts/fancybox/source/jquery.fancybox.pack.js?v=2.1.5"></script>
		
		<script type ="text/javascript">
			//模板列表插件
			(function ($) {
			
			    var Plugin = function (element, options) {
					this.element = element;
					this.options = options;
					this.init();
				};
		        Plugin.prototype={
					
					init:function(){
						$(this.element).empty();
						this.refresh();
					},
					setDataset:function(){
						var $this=this;
							$.ajax({
							url : this.options.url,
							type : "post",
							async : false,
							data:this.options,
							contentType : "application/x-www-form-urlencoded",
							dataType : "json",
							success : function(rdata) {
								if(rdata!=null){
									$this.options.count=rdata.total&&rdata.total!=undefined?rdata.total:0;
									$this.options.dataset=rdata.rows&&rdata.rows!=undefined?rdata.rows:{};
									if(rdata.rows&&rdata.rows!=undefined){
										$this.options.dataset = rdata.rows
										$.each($this.options.dataset,function(i,data){
											
											data.filename = data.fileName != null && data.fileName != undefined ? data.fileName : data.filename;
											data.path = data.saveServicePath != null && data.saveServicePath != undefined ? data.saveServicePath : data.path;
											
										});
									}
									else{
										$this.options.dataset = {};
									}
								}
							},
							error : function() {
								console.log("获取控件列表数 据失败");
							}
						});
					},
					refresh:function(){
						this.setDataset();
						this.render();
						this.initEvent();
					},
					// getPageData:function(pageNo){
					// 	var data = this.options.dataset;
					// 	var pageSize = this.options.rows;
					// 	var pageData = [];
					// 	for(var i=0;i<data.length;i++){
					// 		if((pageNo-1)*pageSize<=i&&i<pageNo*pageSize){
					// 			pageData.push(data[i]);
					// 		}	
					// 	}
					// 	return pageData;
					// },
					renderContent:function(){
						var $this=this;
						$(this.element).empty();
						var rowdom,rowNo,coldom,thumbnaildom;
						var pageNo=this.options.page;
						var pagesize=this.options.rows;
						var rowColumns=this.options.rowColumns,
						rowNo=(pageNo-1)*(pagesize/rowColumns),
						$callback=this.options.callback;
						$.each(this.options.dataset,function(i,item){
							if(i%rowColumns==0){
								rowNo++;
								rowdom=$("<div id=\"row_"+rowNo+"\" class=\"row\"></div>");
								$($this.element).append(rowdom);
							}
							coldom=$("<div class=\"col-sm-6 templatecolumn\">");
							var coldeliv=Math.ceil(12/rowColumns);
							if((i+1)%rowColumns==0){
								coldeliv=12-(rowColumns-1)*coldeliv;
							}
							coldom.addClass("col-md-"+coldeliv);
							thumbnaildom=$($("#thumbnail_template").html());
							if($this.options.previewUrl){
								thumbnaildom.find("img").attr("src",$this.options.previewUrl+item.id);
							}else{
								if(item.saveServicePath != null && item.saveServicePath != undefined){
									thumbnaildom.find("img").attr("src",contextPath+item.saveServicePath);
								}
								else {
									 var thumbPath = "/mx/form/imageUpload?method=downloadById&id="+item.id
									   thumbnaildom.find("img").attr("src",contextPath + thumbPath);
								}
								  		
							}
							 
							/*$("#img_"+item.id).fancybox({
								type: 'image',
								tpl:{
			                         image    : '<img class="fancybox-image" src="{href}" alt="" /></div>',
								},
								closeClick : true,
								openEffect : 'none',
								helpers : {
									title : {
										type : 'inside'
									}
								}
							});*/
							thumbnaildom.find("h3").text(item.filename);
							thumbnaildom.attr("id",item.id);
							coldom.append(thumbnaildom);
							//鼠标移入绑定事件
							if($callback["OnMouseover"]!=null&&typeof $callback["OnMouseover"] =="function"){
							   //绑定事件
							   thumbnaildom.mouseover(function(e){
							      $callback["OnMouseover"].call(this);
								   e.stopPropagation();
							   });
						    }
							//点击绑定事件
							if($callback["OnOpen"]!=null&&typeof $callback["OnOpen"] =="function"){
							   //绑定事件
							   thumbnaildom.click(function(e){
							      $callback["OnOpen"].call(this,item);
								   e.stopPropagation();
							   });
						    }
							if($callback["OnClose"]!=null&&typeof $callback["OnClose"] =="function"){
							   //绑定事件
							  //thumbnaildom.mouseleave(function(){
							     // $callback["OnClose"].call(this,item);
							  // });
						    }
							if($callback["OnChooseClick"]!=null&&typeof $callback["OnChooseClick"] =="function"){
							   //绑定事件
							   thumbnaildom.find(".editTemplateBt").click(function(e){
							      $callback["OnChooseClick"].call(this,item);
								   e.stopPropagation();
							   });
						    }
							if($callback["OnApplyClick"]!=null&&typeof $callback["OnApplyClick"] =="function"){
							   //绑定事件
							   thumbnaildom.find(".applyTemplateBt").click(function(e){
								  if(item.path == undefined){
									  item.path = "/mx/form/imageUpload?method=downloadById&id="+item.id
								  }
							      $callback["OnApplyClick"].call(this,item);
								   e.stopPropagation();
							   });
						    }
							rowdom.append(coldom);
						});
						//记录不足补空行
						var recordsize=(this.options.dataset&&this.options.dataset.length!=undefined)?this.options.dataset.length:0;
						for(var j=recordsize;j<pagesize;j++){
							if(j%rowColumns==0){
								rowNo++;
								rowdom=$("<div id=\"row_+"+rowNo+"\" class=\"row\"></div>");
								$($this.element).append(rowdom);
							}
							coldom=$("<div class=\"col-sm-6 templatecolumn\">");
							var coldeliv=Math.ceil(12/rowColumns);
							if((j+1)%rowColumns==0){
								coldeliv=12-(rowColumns-1)*coldeliv;
							}
							coldom.addClass("col-md-"+coldeliv);
							thumbnaildom=$($("#thumbnail_template").html());
							thumbnaildom.find("img").attr("src",contextPath+"/scripts/designer/images/none.jpg");
							thumbnaildom.find("h3").text("");
							thumbnaildom.find(".caption").empty();
							coldom.append(thumbnaildom);
							rowdom.append(coldom);
						}
					},
					render:function(){
						this.renderContent();
						this.renderPagination();
					},
					initEvent:function(){
						var that = this;
						$(this.element).on("click",".pageBt",function(e){
							// $(that.element).find(".btn-toolbar .btn-group .pageBt").removeClass("active");
							// $(this).addClass("active");
							that.options.page = parseInt($(this).html());
							that.setDataset();
							that.render();
						});
						$(this.element).on("click",".prevBt",function(e){
							if(that.options.page-1>0){
								that.options.page= that.options.page-1;
								that.setDataset();
								that.render();
							}
						});
						$(this.element).on("click",".nextBt",function(e){
							if(that.options.page+1<=that.options.totalPage){
								that.options.page = that.options.page+1;
								that.setDataset();
								that.render();
							}	
						});
					},
					renderPagination:function(){
						if(this.options.count>0){
		                    var rowdom=$("<div class=\"row\"></div>");
		                    var coldom=	$("<div class=\"col-sm-12 col-md-12\"></div>");			
							var paginateBtToolBar=$("<div class=\"btn-toolbar margin-bottom-10\">");	
		                    var paginateBtGroup=$(" <div class=\"btn-group\">");
							var prevBt=$("<button type=\"button\" class=\"btn btn-default prevBt\"><i class=\"fa fa-angle-left\"></i></button>");
							// if(this.options.pageNo==1){
							// 	prevBt.addClass("disabled");
							// }
		                    paginateBtGroup.append(prevBt);
							
							var totalPages = this.options.totalPage=Math.ceil(this.options.count/this.options.rows);
							for(var i=0;i<totalPages;i++){
								var pageBt = $("<button type=\"button\" class=\"btn btn-default pageBt\"></button>");
								if(this.options.page==(i+1)){
									pageBt.addClass("active");
								}
								pageBt.html(i+1);
								paginateBtGroup.append(pageBt);
							}
							var nextBt=$("<button type=\"button\" class=\"btn btn-default nextBt\"><i class=\"fa fa fa-angle-right\"></i></button>");
							// if(this.options.pageNo==totalPages){
							// 	nextBt.addClass("disabled");
							// }
							paginateBtGroup.append(nextBt);
							paginateBtToolBar.append(paginateBtGroup);
							coldom.append(paginateBtToolBar);
							rowdom.append(coldom);
							$(this.element).append(rowdom);
						}
					}
				};
				$.fn.templateGrid=function(options){
						   // 合并参数
						    return this.each(function () {
							//在这里编写相应的代码进行处理
							//var ui = $.data(this, "templateGrid");
							// 如果该元素没有初始化过(可能是新添加的元素), 就初始化它.
							//if (!ui) {
								var opts = $.extend(true, {}, $.fn.templateGrid.defaults, typeof options === "object" ? options : {});
								var ui = new Plugin(this, opts);
								// 缓存插件
						        $.data(this, "templateGrid", ui);
							//}
							// 调用方法
							if (typeof options === "string" && typeof ui[options] == "function") {
								// 执行插件的方法
								ui[options].apply(ui, args);
							}
						});
				};
				$.fn.templateGrid.defaults={
						url:"",
						rows:8,
						page:1,
						count:0,
						rowColumns:4,
						dataset:{},
						callback:{
						   OnChooseClick:function(){},
						   OnApplyClick:function(){},
						   OnOpen:function(){},
						   OnClose:function(){},
						   OnMouseover:function(){}
						}
				};	
			})(jQuery);
			/*
			*提示框
			*/
			$.alert=function(type,msg){
				 var alertDom=$("<div style=\"display:none\" class=\"alert alert-dismissable myalert\"></div>");
				 alertDom.append("<button class=\"close\" aria-hidden=\"true\" type=\"button\" data-dismiss=\"alert\">×</button>");
				 if(type==1){
					 alertDom.addClass("alert-danger");
				 }else{
					 alertDom.addClass("alert-success");
				 }
	             alertDom.append("<h4>	<i class=\"icon fa fa-check\"></i> 操作提示!</h4>");
	             alertDom.append(msg);
	             $("body").append(alertDom);
	             $(".myalert").fadeIn(500).delay(2000).fadeOut(1000);
			}
		</script>
		<script type="text/javascript">

			function initTab(){
				if (location.hash) {
		            var tabid = encodeURI(location.hash.substr(1));
		            $('a[href="#' + tabid + '"]').parents('.tab-pane:hidden').each(function() {
		                var tabid = $(this).attr("id");
		                $('a[href="#' + tabid + '"]').click();
		            });
		            $('a[href="#' + tabid + '"]').click();
		        }
			}

			$(document).ready(function(){	

				// initTab();
				var options={
			   		url:contextPath+"/mx/file/attachment/json?type=files",
			   		previewUrl:contextPath+"/website/lob/lob2/download?fileId=",
				   	callback:{
					   OnApplyClick:applyClick,
					   OnOpen:openOn,
					   OnClose:closeOn,
					   OnMouseover:showMask
				  	}
				};
				var templateGrid0=$("#content0").templateGrid(options);
				var templateGrid2=$("#content2").templateGrid({
			   		url:contextPath+"/mx/form/attachment?method=getFilesByRandomParentOTemplate&randomParent=7ATTACH-UPLOAD-IMAGE",
			        number : 12,
					page:1,
					count:0,
					rowColumns:6,
				   	callback:{
					   OnApplyClick:applyClick1,
					   OnOpen:openOn1,
					   OnClose:closeOn,
					   OnMouseover:showMask
				  	}
				});
				var templateGrid1=$("#content1").templateGrid({
			   		url:contextPath+"/mx/ueditor/image/data",
			   		rows:12,
					page:1,
					count:0,
					rowColumns:6,
				   	callback:{
					   OnApplyClick:applyClick1,
					   OnOpen:openOn1,
					   OnClose:closeOn,
					   OnMouseover:showMask
				  	}
				});
			});
			
			
			function uploadFile() {
				//var uuid = gluuid();
				//$($("#uploadForm")[0]).find("#attachmentId").val(uuid);
				$(".uploadType").val($(".uploadSel").val());
				var formData = new FormData($("#uploadForm")[0]);
				$.ajax({
					url : contextPath
							+ "/mx/form/attachment?method=ImageUpload",
					type : 'POST',
					data : formData,
					async : false,
					cache : false,
					contentType : false,
					processData : false,
					success : function(data) {
						
						$("#content2").templateGrid({
							url:contextPath+"/mx/form/attachment?method=getFilesByRandomParentOTemplate&randomParent=7ATTACH-UPLOAD-IMAGE",
					        number : 12,
							page:1,
							count:0,
							rowColumns:6,
						   	callback:{
							   OnApplyClick:applyClick1,
							   OnOpen:openOn1,
							   OnClose:closeOn,
							   OnMouseover:showMask
						  	}
						});
						alert("上传成功");
						console.log(data);
					},
					error : function(data) {
						alert("上传失败");
						
						console.log(data)
					}
				});
			}
			//选用模板按钮事件绑定
			function applyClick(item) {
				if (this && this != window) {
					if (hiddenId) {
						$("#" + hiddenId, window.parent.document).val(
								"/website/lob/lob2/download?fileId=" + item.id);
						$("#" + id, window.parent.document).val(
								"/website/lob/lob2/download?fileId=" + item.id);
					}
					callBack2("/website/lob/lob2/download?fileId=" + item.id);
				}
			}
			//打开模板大图弹窗
			function openOn(item) {
				if (this && this != window) {
					$.fancybox
							.open({
								href : contextPath
										+ "/website/lob/lob2/download?fileId="
										+ item.id,
								type : 'image',
								title : item.filename,
								tpl : {
									image : '<img class="fancybox-image" src="{href}" alt="" /></div>',
								},
								closeClick : true,
								openEffect : 'none',
								helpers : {
									title : {
										type : 'inside'
									}
								}
							});
				}
			}
			//选用模板按钮事件绑定
			function applyClick1(item) {
				if (this && this != window) {
					if (hiddenId) {
						$("#" + hiddenId, window.parent.document)
								.val(item.path);
						$("#" + id, window.parent.document).val(item.path);
					}
					callBack2(item.path);
				}
			}
			//打开模板大图弹窗
			function openOn1(item) {
				if (this && this != window) {
					$.fancybox
							.open({
								href : contextPath + item.path,
								type : 'image',
								title : item.filename,
								tpl : {
									image : '<img class="fancybox-image" src="{href}" alt="" /></div>',
								},
								closeClick : true,
								openEffect : 'none',
								helpers : {
									title : {
										type : 'inside'
									}
								}
							});
				}
			}
			//关闭模板大图弹窗
			function closeOn(item) {
				if (this && this != window) {
					$.fancybox.close();
				}
			}
			function showMask() {
				//获取当前图片的位置和高宽度信息
				if (this && this != window) {
					var imgObj = $(this);
					//设置遮罩层大小位置
					var mask = $(this).find(".mask");
					mask.height(imgObj.height());
					mask.width(imgObj.width());
					mask.css("position", "absolute");
					mask.css("line-height", imgObj.height() + "px");
					mask.css("top", imgObj.position().top);
					mask.css("left", imgObj.position().left);
				}
			}

			function callBack2(url) {
				var fn = window.getParameter("fn"), targetId = window
						.getParameter("targetId");
				var $parent = window.opener || window.parent;
				if (fn && $parent[fn]) {
					$parent[fn].call($parent.document.getElementById(targetId),
							url);
				}
			}
			function getParameter(name) {
				var params = {};
				var search = window.location.search;
				if (search && search.length > 1) {
					search = search.substring(1);
					search = search.split("&");
					var s;
					$.each(search, function(i, v) {
						s = v.split("=");
						params[s[0]] = s[1];
					});
				}
				return params[name];
			}
		</script>





















		<script type="text/javascript">

        var setting = {
            view: {
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom,
                selectedMulti: false
            },
            check: {
                enable: true,
				chkStyle: "radio",
				radioType:"all"
            },
            data: {
                simpleData: {
                    enable: true,
					idKey: "categoryId",
					pIdKey: "parentId",
					rootPId: 0
                }
            },
            edit: {
                enable: true,
				removeTitle:"删除模板分类",
				renameTitle:"修改分类名称"
            },
			callback:{
				onClick: zTreeOnClick,
				onRename:zTreeOnRename,
				beforeRemove:zTreeBeforeRemove
			}
        };

        $(document).ready(function(){		
           $.fn.zTree.initZtree($("#templateCategory"),url,setting);
        });
		var url=contextPath+"/rs/designer/categorys";
        $.fn.extend($.fn.zTree,{
		  initZtree:function (container,url,setting){
			  $.ajax({
				url : url,
				type : "post",
				async : false,
				contentType : "application/json",
				dataType : "json",
				success : function(rdata) {
					if($.isEmptyObject(rdata)){
						rdata=new Array();
					}
					rdata.push({categoryId:"0",parentId:"0",name:"模板类型列表",open:true,chkDisabled:true});
					$.fn.zTree.init(container,setting, rdata);
				},
				error : function() {
					console.log("获取分类数据失败");
				}

		   }
		  );
		}});
        var newCount = 1;
		//新增节点事件
        function addHoverDom(treeId, treeNode) {
            var sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
            var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                + "' title='新增模板分类' onfocus='this.blur();'></span>";
            sObj.after(addStr);
            var btn = $("#addBtn_"+treeNode.tId);
            if (btn) btn.bind("click", function(){
                
                //zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"模板分类" + (newCount++)});
				return zTreeOnAdd(treeId,treeNode);
            });
        };
		//新增节点后台处理
		function zTreeOnAdd(treeId,treeNode){
			 var pId=treeNode.categoryId;
			 var pTreeId=treeNode.treeID;
			 var level=treeNode.level+1;
             var url=contextPath+"/rs/designer/category/add";
			 var name="新建分类" + (newCount++);
			 var params={"pId":pId,"pTreeId":pTreeId,"level":level,"name":name};
			 var result=false;
			 $.ajax({
				url : url,
				type : "post",
				async : false,
				data:params,
				contentType : "application/x-www-form-urlencoded",
				dataType : "json",
				success : function(rdata) {
					if(rdata!=null&&rdata.result==1){
						var zTree = $.fn.zTree.getZTreeObj(treeId);
						zTree.addNodes(treeNode, {id:rdata.id,"categoryId":rdata.categoryId,pId:pId,name:name});
						result= true;
					}else{
						result= false;
					}
					
				},
				error : function() {
					console.log("新增模板分类失败");
					result= false;
				}
		   });
		   return result;
		}
        function removeHoverDom(treeId, treeNode) {
            $("#addBtn_"+treeNode.tId).unbind().remove();
        };
		//ztree树节点重命名事件
		function zTreeOnRename(event, treeId, treeNode, isCancel){
			 var categoryId=treeNode.categoryId;
			 var name=treeNode.name;
			 var url=contextPath+"/rs/designer/category/rename";
			 var params={"categoryId":categoryId,"name":name};
			 var result=false;
			 $.ajax({
				url : url,
				type : "post",
				async : false,
				data:params,
				contentType : "application/x-www-form-urlencoded",
				dataType : "json",
				success : function(rdata) {
					result= true;
				},
				error : function() {
					console.log("重命名失败");
					result= false;
				}

		   }
		  );
		  return result;
		}
		//ztree树节点移除事件
		function zTreeBeforeRemove(treeId, treeNode){
			 var result=false;
			 if(confirm("确认删除分类"+treeNode.name+"吗?")){
			 var categoryId=treeNode.categoryId;
			 var url=contextPath+"/rs/designer/category/delete";
			 var params={"categoryId":categoryId};
			 $.ajax({
				url : url,
				type : "post",
				async : false,
				data:params,
				contentType : "application/x-www-form-urlencoded",
				dataType : "json",
				success : function(rdata) {
					result= true;
				},
				error : function() {
					console.log("删除分类失败");
					result= false;
				}
		   }
		  );
			 }
		  return result;
		}
		//ztree树节点点击事件
		function zTreeOnClick(event, treeId, treeNode){
			var options={
			   url:contextPath+"/rs/designer/"+treeNode.categoryId+"/listCategoryTemplates",
			   "category":treeNode.categoryId,
			   callback:{
				   OnChooseClick:chooseOnClick,
				   OnApplyClick:applyClick,
				   OnOpen:openOn,
				   OnClose:closeOn,
				   OnMouseover:showMask
			   }
			};
			var templateGrid=$(".gridcontent").templateGrid(options);
			$('.nav-tabs a[href="#portlet_tab2"]').tab('show');
		}
		
	
		function changeTab(tabId){
			$('.nav-tabs a[href="#'+tabId+'"]').tab('show');
		}
		$(function() {
			$(".tab-pane").css("min-height","540px");
			$(".tab-pane").height($(window).height()-80);
			$('.joinBtn').click(function(){
					//获取当前选中的分类
					var zTree = $.fn.zTree.getZTreeObj("templateCategory");
					//获取选中节点
					var nodes=zTree.getCheckedNodes(true);
					//获取编辑器内容
					var code=$("#summernote").summernote('code');
					//var code=ue.getContent();
					var tmpName=$("#tmpName").val();
					if(nodes==null||nodes.length==0){
						$.alert(1,"请选择分类");
						return false;
					}
					if($.trim(code)==''){
						$.alert(1,"模板内容为空");
						return false;
					}
					code=$(code).html();
					//保存操作
					var url=contextPath+"/rs/designer/"+nodes[0].categoryId+"/saveTemplate";
					var params={"code":code,"tmpName":tmpName};
                    html2canvas($('.contentArea'), {
					//html2canvas($($("iframe")[1].contentWindow.document.body).find(".contentArea"), {	
                    onrendered : function(canvas) {  
                    var myImage = canvas.toDataURL("image/jpeg");
					params.imgdata=myImage;
                    //并将图片上传到服务器用作图片分享  
					$.ajax({
					url : url,
					type : "post",
					async : false,
					data:params,
					contentType : "application/x-www-form-urlencoded",
					dataType : "json",
					success : function(rdata) {
						if(rdata!=null){
							var id=rdata.id;
							$.alert(0,"保存成功！");
						}
					},
					error : function() {
						console.log("保存到分类成功！");
					}
				     });
                    }  
                  });  

				});
				$('.editBtn').click(function(){
					//获取当前编辑的模板ID
                    var templateId=currentEditItem.id;
					//获取编辑器内容
					var code=$("#summernote").summernote('code');
					if($.trim(code)==''){
						$.alert(1,"模板内容为空");
						return false;
					}
					code=$(code).html();
					var tmpName=$("#tmpName").val();
					//保存操作
					var url=contextPath+"/rs/designer/"+templateId+"/editTemplate";
					var params={"code":code,"tmpName":tmpName};
                    html2canvas($('.contentArea'), {
                    onrendered : function(canvas) {  
                    var myImage = canvas.toDataURL("image/jpeg");
					params.imgdata=myImage;
                    //并将图片上传到服务器用作图片分享  
					$.ajax({
					url : url,
					type : "post",
					async : false,
					data:params,
					contentType : "application/x-www-form-urlencoded",
					dataType : "json",
					success : function(rdata) {
						if(rdata!=null){
							var id=rdata.id;
							$.alert(0,"保存成功！");
						}
					},
					error : function() {
						console.log("保存到分类成功！");
					}
				     });
                    }  
                  });  

				});
			if(action=='join'){
				$('.nav-tabs a[href="#portlet_tab1"]').tab('show');
			}else{
				$('.nav-tabs a[href="#portlet_tab2"]').tab('show');
				$('.joinBtn').remove();
			}
			$("#summernote").summernote({
			  lang: 'zh-CN',
			  minHeight: 450,// set minimum height of editor
			  focus: true}
			  );
			
		});
		function setSummernoteCode(content){
			var  div=$("<div class='contentArea'></div>");
			div.append(content);
			$("#summernote").summernote('code',div);
			/**百度编辑器集成
			ue.ready(function () {
					// editor准备好之后才可以使用
					ue.setContent(div.prop("outerHTML"));
				 });
		   */
		}
		$(window).resize(function() {
			$(".tab-pane").height($(window).height()-80);
		});

        //-->
      </script>
</body>
</html>