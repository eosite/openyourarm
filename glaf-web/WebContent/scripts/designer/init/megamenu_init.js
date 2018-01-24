
//$(function(){
	
 	$("div[data-role=megamenu]").each(function(){
 		
		var fHover,sHover;
		if($(this).hasClass("page-header-mt")){
			fHover = "div.hor-menu ul.navbar-nav>li";
			sHover = "div.hor-menu ul.navbar-nav>li ul.dropdown-menu li";
		}else if($(this).hasClass("page-sidebar-mt")){
			fHover = "ul.page-sidebar-menu-mt>li";
			sHover = "ul.page-sidebar-menu-mt ul.sub-menu > li";
		}	
/**
 * 设置主菜单悬浮背景色
 */		
		$(".designer,.lyrow").delegate(fHover,"mouseover",function () { 
			var el = $(this).find(">a");
			var fhovercolor = $(this).closest("div[data-role=megamenu]").attr("fhovercolor");
			if(!fhovercolor){
			    el.css("background-color","");
				$(this).find("ul").css("background-color","");
			}else{
			    el.css("background-color",fhovercolor);
			    $(this).find("ul").css("background-color",fhovercolor);
			}
		
		});
		$(".designer,.lyrow").delegate(fHover,"mouseleave", function () { 
			var el = $(this).find(">a");
			var fhovercolor = $(this).closest("div[data-role=megamenu]").attr("fhovercolor");
			el.css("background-color","");
			$(this).find("ul").css("background-color","");
		});
		
	/**
	 * 设置子菜单悬浮背景色
	 */
		$(".designer,.lyrow").delegate(sHover,"mouseover",function () { 
			var el = $(this).find(">a");
			var shovercolor = $(this).closest("div[data-role=megamenu]").attr("shovercolor");
			if(!shovercolor){
			   el.css("background-color","");		  
			}else{
			   el.css("background-color",shovercolor);	
			}
		});
		$(".designer,.lyrow").delegate(sHover,"mouseleave", function () { 
			var shovercolor = $(this).closest("div[data-role=megamenu]").attr("shovercolor");
			var el = $(this).find(">a");
			el.css("background-color","");	
		});
	});

//});