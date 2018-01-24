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
<title>R+搜索</title>
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
		<!--??????-->
		<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/fancybox/source/jquery.fancybox.css?v=2.1.5" media="screen" />
		<style style="text/css">
             body{
			    margin:auto;
			    text-align:center;
			 } 
			 .row{
				 margin-left:0;
				 margin-right:0;
			 }
			 .searchItem{
				 margin-top:15px;
				 margin-bottom:15px;
			 }
			 .searchItem .title{
				 font-size:16px;
				 color:#0000cc;
				 text-decoration: underline;
			 }
			 .searchItem .main{
				 font-size:13px;
				 color:#666;
			 }
			 .searchItem .main .publishTime{
				 color:#666;
			 }
			 .searchItem .main .content{
				 color:#333;
			 }
			 .search-highlight-red{
				 font-style: normal;
				 color:#c00;
			 }
			 .address{
				 color: green;
                 font-size: 13px;
			 }
			 .snapshot{
				 font-size: 13px;
				 color:#666; 
				 text-decoration: underline;
			 }
			 .pageBt {
				 height:34px;
				 width:34px;
				 margin-right:9px;
			 }
			 .prevBt{
				 margin-right:9px;
			 }
			 .nextBt{
				 margin-right:9px;
			 }
			 .pageBt:hover,.prevBt:hover,.nextBt:hover {
					background: #f2f8ff;
					border: 1px solid #38f;
			}
			.currentPage{
				font-weight:bold;
				border:none;
			}
			.currentPage.pageBt:hover{
				background: #fff;
				border:none;
			}
		</style>
</head>
<body>
 <div>
  <div class="row">
	<div class="col-lg-12">
		<div class="input-group" style="width:800px;padding-top:20px;">
		    <img src="${contextPath}/images/chiss_logo_blue.png" style="height:30px;float:left;padding-top:5px;padding-right:10px;"/>
			<input id="searchTxt" type="text" value="${param.w}" class="form-control" style="width:536px;height:40px;float:left;" placeholder="查询...">
			<span class="input-group-btn" style="float:left;">
				<button id="searchBt" class="btn blue uppercase bold" style="height:40px;" type="button">搜索一下</button>
			</span>
		</div>
	</div>
  </div>
  <div class="row">
       <div class="col-lg-12 text-left" id="records" style="font-size:12px;color: #999;margin-top:30px;">
	       
	   </div>
  </div>
  <div class="row detail">
       <div id="content_area" class="col-lg-12 text-left" >
	       
	   </div>
  </div>
  <div class="row">
      <div id="pageArea" class="col-lg-12 text-left" >
	  
	  </div>
  </div>
 </div>
 <template id="searchItem_tmp"> 
   <div class="row searchItem">
	 <div class="col-lg-12 text-left">
		<div><a class="title" target="_blank"></a></div>
		<div class="main">
		  <span class="publishTime"></span>-<span class="content"></span>
		</div>
		<div class="btaddress">
		  <a class="address" target="_blank"></a><a class="snapshot" target="_blank">-R+快照</a>
		</div>
	 </div>
   </div>
 </template>
  <script type="text/javascript">
        var contextPath='${contextPath}';
        </script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
		<script type="text/javascript">
		   //页码
		   var pageNo=1;
		   //单页条数
		   var pagesize=10;
		   //总记录数
		   var records=0;
		   //获取总页数
		   var totalPage=0;
		   function search(searchTxt){
			   //清空内容区
			   $("#content_area").empty();
			   $.ajax({
					url : contextPath+"/mx/search/r",
					type : "post",
					data:{"w":searchTxt,"pageNo":pageNo,"pagesize":pagesize,"records":records},
					dataType : "json",
					success : function(rdata) {
						if (rdata) {
							records=rdata.records;
							var msg;
							if(records&&records>0){
								totalPage=Math.ceil(records/pagesize);
								msg="R+为您找到相关结果约"+records+"个"
							}else{
								msg="R+未找到符合条件的结果"
							}
							if(pageNo==1){
								$("#pageArea").empty();
								createPageArea();
							}
							setCurrentPage();
							$("#records").html(msg);
							var searchItem_tmpDom=$("#searchItem_tmp").html();
							var resultItems=rdata.hits&&rdata.hits.hits?rdata.hits.hits:undefined;
							if(resultItems){
								var searchItem,title,content,address,show_address,publishTime,snapshot,resultItemHighlight,resultItemSource,titleLink;
								$.each(resultItems,function(i,resultItem){
									searchItem=$(searchItem_tmpDom);
									resultItemHighlight=resultItem.highlight;
									resultItemSource=resultItem._source;
									title=resultItemHighlight["title.pinyin"];
									if(!title){
										title=resultItemSource.title;
									}
									if(title){
									  searchItem.find(".title").html(title);
									  
									}
									content=resultItemHighlight["content.pinyin"];
									if(!content){
									  content=resultItemSource.content;
									}
									if(content){
									  searchItem.find(".content").html(content);	
									}
									if(!title){
									  searchItem.find(".title").html(content);	
									}
									address=resultItemHighlight["address.pinyin"];
									if(!address){
									  address=resultItemSource.address;
									}
									if(address){
									   show_address=address;
									  if(address.length>25){
										  show_address=address.substring(0,25)+"...";
									  }
									  searchItem.find(".address").html(show_address);
									  searchItem.find(".title").attr("href",address);
									  searchItem.find(".address").attr("href",address);
									}else{
										searchItem.find(".address").remove();
									}
									publishTime=resultItemHighlight["publishTime.pinyin"];
									if(!publishTime){
									  publishTime=resultItemSource.publishTime;
									}
									if(publishTime){
									  searchItem.find(".publishTime").html(publishTime);	
									}else{
									  publishTime=getCurrentDate();
									  searchItem.find(".publishTime").html(publishTime);	
									}
									snapshot=resultItemHighlight["snapshot.pinyin"];
                                    if(!snapshot){
									  snapshot=resultItemSource.snapshot;
									}								
									if(snapshot){
									     searchItem.find(".snapshot").attr("href","www.baidu.com");
									}else{
										 searchItem.find(".snapshot").remove();
									}
									$("#content_area").append(searchItem);
								});
							}
						}
					},
					error : function() {
						
					}
				});
		   }
		   function createPageArea(){
			        var paginateBtToolBar=$("<div class=\"btn-toolbar margin-bottom-10\">");	
                    var paginateBtGroup=$(" <div class=\"btn-group\">");
					var prevBt=$("<button type=\"button\" class=\"btn btn-default prevBt\"><上一页</button>");
                    paginateBtGroup.append(prevBt);
					var pageBt;
					var totalPages=totalPage;
					for(var i=0;i<10;i++){
						if(i>=totalPages){
							break;
						}
						pageBt=$("<button id=\"page_"+(i+1)+"\" type=\"button\" class=\"btn pageBt btn-default\"></button>");
						pageBt.append(i+1);
						paginateBtGroup.append(pageBt);
					}
					var nextBt=$("<button type=\"button\" class=\"btn btn-default nextBt\">下一页></button>");
					paginateBtGroup.append(nextBt);
					paginateBtToolBar.append(paginateBtGroup);
					$("#pageArea").append(paginateBtToolBar);
					$(".prevBt").click(function(){
						pageNo=pageNo-1;
						_search();
					});
					$("#pageArea").on("click",".pageBt",function(){
						pageNo=parseInt($(this).text());
						_search();
					});
					$(".nextBt").click(function(){
						pageNo=pageNo+1;
						_search();
					});
		   }
		   function _search(){
			   var searchTxt=$("#searchTxt").val();
			   if(searchTxt!=''&&searchTxt.trim().length>0){
				  search(searchTxt);
			   }
		   }
		   function setCurrentPage(){
			   if(pageNo==1){
				  $(".prevBt").hide();
				  $(".nextBt").show();
			   }
			   else if(pageNo==totalPage){
				    $(".prevBt").show();
				    $(".nextBt").hide();
			   }else{
				   $(".prevBt").show();
				   $(".nextBt").show();
			   }
			      //增加页码选择项
			      var $current_Page=$("#page_"+pageNo);
				  var presize=$current_Page.prevAll(".pageBt").length;
				  var aftersize=$current_Page.nextAll(".pageBt").length;
				  var pageBt,prePageBt,nextPageBt,paginateBtGroup;
				  for(var i=5-presize;i>=0;i--){
					    if(pageNo-5+i<=1){
							break;
						}
						pageBt=$("<button id=\"page_"+(pageNo-5+i-1)+"\" type=\"button\" class=\"btn pageBt btn-default\"></button>");
						pageBt.append(pageNo-5+i-1);
						prePageBt=$("#page_"+(pageNo-5+i))
						pageBt.insertBefore(prePageBt);
				  }
				  for(var i=1;i<=4-aftersize;i++){
					    if(pageNo+aftersize+i>totalPage){
							break;
						}
						pageBt=$("<button id=\"page_"+(pageNo+aftersize+i)+"\" type=\"button\" class=\"btn pageBt btn-default\"></button>");
						pageBt.append(pageNo+aftersize+i);
						nextPageBt=$("#page_"+(pageNo+aftersize+i-1))
						pageBt.insertAfter(nextPageBt);
				  }
				   
				   //获取当前页码后四页的按钮
				   if(pageNo>=6&&totalPage>10&&pageNo+4<=totalPage){
				     $("#page_"+(pageNo+4)).nextAll(".pageBt").remove();
				   }
				   var aftersize=$current_Page.nextAll(".pageBt").length;
				   if(aftersize>=4){
					   var firstPageBt=pageNo+aftersize-10+1;
					   $("#page_"+firstPageBt).prevAll(".pageBt").remove();
				   }
			       $("#pageArea").find(".currentPage").removeClass("currentPage");
			       $("#page_"+pageNo).addClass("currentPage");
			   
		   }
		   $(function(){
			   var searchTxt='${param.w}';
			   if(searchTxt!='')
			   search(searchTxt);
		       $("#searchBt").click(function(){
			   var searchTxt=$("#searchTxt").val();
			   if(searchTxt!=''&&searchTxt.trim().length>0){
				  search(searchTxt);
			   }
		      });
			  $("#searchTxt").bind('keyup',function(event){
				event=document.all?window.event:event;
				if((event.keyCode || event.which)==13){
				  var searchTxt=$("#searchTxt").val();
			      if(searchTxt!=''&&searchTxt.trim().length>0){
				  search(searchTxt);
			     }
				}
			   }); 
		   });
		   function getCurrentDate(){
			   var date=new Date();
			   return (1900+date.getYear())+"年"+(date.getMonth()+1)+"月"+date.getDate()+"日";
		   }
		</script>
</body>
</html>