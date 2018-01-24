
$(function(){	
	
	$(".designer").delegate('div[data-role="login_logo"]',"dblclick",function(e){
		$("body .shine_red").removeClass("shine_red");
		currentComponent = $(this);
		currentComponent.addClass("shine_red");
		getMenu(currentComponent);
	});
	
	$(".designer").delegate('div[data-role="login_title"]',"dblclick",function(e){
		$("body .shine_red").removeClass("shine_red");
		currentComponent = $(this);
		currentComponent.addClass("shine_red");
		getMenu(currentComponent);
	});
	
	$(".designer").delegate('div[data-role="login_username"]',"dblclick",function(e){
		$("body .shine_red").removeClass("shine_red");
		currentComponent = $(this);
		currentComponent.addClass("shine_red");
		getMenu(currentComponent);
	});
	
	$(".designer").delegate('div[data-role="login_password"]',"dblclick",function(e){
		$("body .shine_red").removeClass("shine_red");
		currentComponent = $(this);
		currentComponent.addClass("shine_red");
		getMenu(currentComponent);
	});
	
	$(".designer").delegate('div[data-role="login_actions"]',"dblclick",function(e){
		$("body .shine_red").removeClass("shine_red");
		currentComponent = $(this);
		currentComponent.addClass("shine_red");
		getMenu(currentComponent);
	});
	
	$(".designer").delegate('div[data-role="login_register"]',"dblclick",function(e){
		$("body .shine_red").removeClass("shine_red");
		currentComponent = $(this);
		currentComponent.addClass("shine_red");
		getMenu(currentComponent);
	});
	
	$(".designer").delegate('div[data-role="login_verification"]',"dblclick",function(e){
		$("body .shine_red").removeClass("shine_red");
		currentComponent = $(this);
		currentComponent.addClass("shine_red");
		getMenu(currentComponent);
	});
	
	$(".designer").delegate('div[data-role="login_copyright"]',"dblclick",function(e){
		$("body .shine_red").removeClass("shine_red");
		currentComponent = $(this);
		currentComponent.addClass("shine_red");
		getMenu(currentComponent);
	});
	
	
	
//	
//	$(".designer").delegate("div.designer","dblclick",function(e){
//		debugger;
//		//console.log(e.target);
//		var $target = $(e.target);
//		$("body .shine_red").removeClass("shine_red");
//		
//		if($target.closest('div[data-role="login_logo"]').length>0){
//			currentComponent=$target.closest("div[data-role='login_logo']");
//			currentComponent.addClass("shine_red");
//		}else if($target.hasClass("form-title")||$target.hasClass("form-subtitle")||$target.closest('div[data-role="login_title"]').length>0){
//			currentComponent=$target.closest("div[data-role='login_title']");
//			currentComponent.addClass("shine_red");
//		}else if($target.attr("name")=="username"){
//			currentComponent=$target.closest("div[data-role='login_username']");
//			currentComponent.addClass("shine_red");
//		}else if($target.attr("name")=="password"){
//			currentComponent=$target.closest("div[data-role='login_password']");
//			currentComponent.addClass("shine_red");
//		}else if($target.hasClass("btn")||$target.attr("id")=="forget-password"||$target.closest('div[data-role="login_actions"]').length>0){
//			currentComponent=$target.closest("div[data-role='login_actions']");
//			currentComponent.addClass("shine_red");
//		}else if($target.attr("id")=="register-btn"){
//			currentComponent=$target.closest("div[data-role='login_register']");
//			currentComponent.addClass("shine_red");
//		}else if($target.hasClass("copyright")||$target.closest('div[data-role="login_copyright"]').length>0){
//			currentComponent=$target.closest("div[data-role='login_copyright']");
//			currentComponent.addClass("shine_red");
//		}else if($target.closest('div[data-role="login_verification"]').length>0){
//			currentComponent=$target.closest("div[data-role='login_verification']");
//			currentComponent.addClass("shine_red");
//		}else if($target.closest('div[data-role="login_copyright"]').length>0){
//			currentComponent=$target.closest("div[data-role='login_copyright']");
//			currentComponent.addClass("shine_red");
//		}else{
//			$("body .shine_red").removeClass("shine_red");
//			return;
//		}
//		
//		
//	});
	
	
	function getMenu(currentComponent){
		//获取控件属性
		var dataRole;
		if(currentComponent){
			dataRole=currentComponent.attr("data-role");		
		}
		if(dataRole==undefined || dataRole==null){	
			dataRole = "__unknown__";
			return;
		}
		//动态加载js
		var jsId=dataRole+"js";
		var js="<script id='"+jsId+"' type='text/javascript' src='"+contextPath+"/scripts/designer/components/"+dataRole+".js'></script>";
		if($("#"+jsId).length==0){
		  $("body").append(js);
		}
		//清空工具栏
		$("#quick_sidebar_tab_1").find(".media-list").empty();
		$("#quick_sidebar_tab_2").find(".mt-list-container").empty();
		//加载工具栏
		$.Toolbox(
						$("#quick_sidebar_tab_1").find(".media-list"),
						{
							"url" : contextPath+"/service/designer/"+dataRole+"/templates",
							"contextPath" : contextPath
						},
						{
							"getTemplates":"true"
						});
		$.Toolbox(
						$("#quick_sidebar_tab_2").find(".mt-list-container"),
						{
							"url" : contextPath+"/service/designer/"+dataRole+"/setting",
							"contextPath" : contextPath
						},
						{
						}, "initSideBar,openSideBar");
	}

});