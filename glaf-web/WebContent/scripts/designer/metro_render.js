var metro_restore = {
	restoreAll: function(){
		metro_restore.initICheck();
//		metro_restore.initSelect2();
//		metro_restore.initGantt();
		metro_restore.initTabs();
		metro_restore.initPortlet();
		metro_restore.initSlimScroll();
		metro_restore.initAnimation();
	},
	initICheck: function(){
		//iCheck init
		$('[data-role=icheckbox]').each(function(index, el) {
			var self = $(this);
			self.find("label").each(function(i,o){
				var $input = $(o).find("input");
				var icheckboxClass = $input.attr('data-checkbox') || "icheckbox_minimal-grey";
				$(o).prepend($input);
				$(o).find("div").remove();
				$input.iCheck({checkboxClass: icheckboxClass});
			})
		});
		$('[data-role=icheckradio]').each(function(index, el) {
			var self = $(this);
			self.find("label").each(function(i,o){
				var $input = $(o).find("input");
				var iradioClass = $input.attr('data-radio') || "iradio_minimal-grey";
				$(o).prepend($input);
				$(o).find("div").remove();
				$input.iCheck({radioClass: iradioClass});
			})
		});
	},
	initSelect2: function(){
		//select2 init
		var select2s = $('[data-role=metroselect]');
		$.each(select2s, function(i, o) {
			$(o).metroselect({asDemo:true});
		});
		select2s = $('[data-role=metroselect_m]');
		$.each(select2s, function(i, o) {
			$(o).metroselect_m({asDemo:true});
		});
	},
	initTabs: function(){
		//tabs init
		var tabs = $('[data-role=bootstrap_tabs]');
		$.each(tabs, function(i, o) {
			$(o).tabs({
				minHeight: 80
			});
		});
	},
	initPortlet: function(){
		//portlets init
		var portlets = $('[data-role=portlet]');
		$.each(portlets, function(i, el) {
			$(el).portlet();
		});
	},
	initSlimScroll:function(){
		var scroller = $("body").find(".scroller");
		$.each(scroller,function(i,o){
			var height = $(o).outerHeight();
			App.destroySlimScroll($(o));
			$(o).outerHeight(height);
			App.initSlimScroll($(o));
		});
	},
	initAnimation:function(){
			/**
			*加载动画效果
			*/
		   var animateComps=$("[data-role][animate-name]");
		   animateComps.each(function(i,animateComp){
					var animationType="in"; 
					var animateRuleJSON=$(this).attr("animate-"+animationType);
					if(animateRuleJSON==undefined){
						animationType="continue";
						animateRuleJSON=$(this).attr("animate-"+animationType);
						if(animateRuleJSON==undefined){
							animationType="out";
							animateRuleJSON=$(this).attr("animate-"+animationType);
						}
					}
					if(animateRuleJSON==undefined){
						return;
					}
					var animateRule=JSON.parse(animateRuleJSON);
					var nlooptimes=animateRule["looptimes"];
					var nstarttime=animateRule["continueTime"];
					var ndelaytime=animateRule["delayTime"];
					var nanimateName=animateRule["animationName"];
					runAnimation(animationType,nanimateName,nlooptimes,$(this),nstarttime,ndelaytime);
		   });
	}
};
//自动计算高度
function autoComputeHeight(){
	//获取需自适应高度的栅格
	var containers=$("body").find("div[style*=height].autoHt.row,.autocomp,.portlet-title").parent();
	var contHt,sibDivs,autoHtDiv,autoHt;
	$.each(containers,function(i,container){
		contHt=$(container).height();
		autoHtDiv=$(container).children("div:not([style*=height]).autoHt.row,div.autocomp,.autoHt.portlet,.portlet-body");
		if(autoHtDiv&&autoHtDiv.length==1){
			sibDivs=autoHtDiv.siblings();
			autoHt=contHt;
			$.each(sibDivs,function(j,sibDiv){
				autoHt=autoHt-$(sibDiv).outerHeight(true);
			});
			autoHtDiv.addClass("autocomp");
			autoHtDiv.outerHeight(autoHt);
		}else if(autoHtDiv.length>0){
			sibDivs=$(autoHtDiv[autoHtDiv.length-1]).siblings();
			autoHt=contHt;
			$.each(sibDivs,function(j,sibDiv){
				autoHt=autoHt-$(sibDiv).outerHeight(true);
			});
			$(autoHtDiv[autoHtDiv.length-1]).outerHeight(autoHt);
			$(autoHtDiv[autoHtDiv.length-1]).addClass("autocomp");
		}
	});

	initColSlimScroll();
}
function initColSlimScroll(){
	$.each($("[needslimscroll]"),function(i,container){
		var $container = $(container);
		if ($container.attr("data-initialized")) {
            //如果存在，则先销毁，后创建
			var lastHeight = $container.attr("lastHeight");
			//高度变化时才进行销毁并重新生成滚动条
			$container.removeAttr("data-initialized");
            $container.removeAttr("style");
            var L = {};
            if($container.attr("data-handle-width")){
            	L["data-handle-width"] = $container.attr("data-handle-width");
            }
            if ($container.attr("data-handle-color")) {
                L["data-handle-color"] = $container.attr("data-handle-color");
            }
            if ($container.attr("data-wrapper-class")) {
                L["data-wrapper-class"] = $container.attr("data-wrapper-class");
            }
            if ($container.attr("data-rail-color")) {
                L["data-rail-color"] = $container.attr("data-rail-color");
            }
            if ($container.attr("data-always-visible")) {
                L["data-always-visible"] = $container.attr("data-always-visible");
            }
            if ($container.attr("data-rail-visible")) {
                L["data-rail-visible"] = $container.attr("data-rail-visible");
            }
            $container.slimScroll({
                wrapperClass: ($container.attr("data-wrapper-class") ? $container.attr("data-wrapper-class") : "slimScrollDiv"),
                destroy: true
            });
            $.each(L, function(M, N) {
                $container.attr(M, N)
            })
        }
        var originStyle = $container.data("originStyle");
        if(!originStyle){
        	$container.data("originStyle",$container.attr("style"));
        }else{
        	$container.attr("style",originStyle);	
        }
        var height = $container.height();
        $container.attr("lastHeight",height);
        $container.slimScroll({
            allowPageScroll: true,
            size: ($container.attr("data-handle-width") ? $container.attr("data-handle-width") : "7px"),
            color: ($container.attr("data-handle-color") ? $container.attr("data-handle-color") : "#bbb"),
            wrapperClass: "slimScrollDiv",
            railColor: ($container.attr("data-rail-color") ? $container.attr("data-rail-color") : "#eaeaea"),
            position: "right",
            width: $container.width(),
            height: height,
            opacity: ($container.attr("data-opacity") ? $container.attr("data-opacity") : .7), //轨道透明度
            alwaysVisible: ($container.attr("data-always-visible") == "1" ? true : false),
            railVisible: ($container.attr("data-rail-visible") == "1" ? true : false),
            disableFadeOut: true
        });
        $container.attr("data-initialized", "1")
	})
}
/**
*获取浏览器缩放比例
*/
function detectZoom (){ 
  var ratio = 0,
    screen = window.screen,
    ua = navigator.userAgent.toLowerCase();
 
   if (window.devicePixelRatio !== undefined) {
      ratio = window.devicePixelRatio;
  }
  else if (~ua.indexOf('msie')) {  
    if (screen.deviceXDPI && screen.logicalXDPI) {
      ratio = screen.deviceXDPI / screen.logicalXDPI;
    }
  }
  else if (window.outerWidth !== undefined && window.innerWidth !== undefined) {
    ratio = window.outerWidth / window.innerWidth;
  }
   
   if (ratio){
    ratio = Math.round(ratio * 100);
  }
   
   return ratio;
};
var ratio=100;
$(function(){
	metro_restore.restoreAll();
	if($(".autoHt").length>0){
		setTimeout(autoComputeHeight,1000);
		$(window).resize(autoComputeHeight);
	}
	setTimeout(initColSlimScroll,1000);
	var scrollFunc = function (e) {
        var direct = 0;
        e = e || window.event;
		if(e.ctrlKey&&(e.wheelDelta||e.detail)){
			if (e.wheelDelta) {  //判断浏览器IE，谷歌滑轮事件             
				if (e.wheelDelta > 0) { //当滑轮向上滚动时
					direct=1;
				}
				if (e.wheelDelta < 0) { //当滑轮向下滚动时
					direct=0;
				}
			} else if (e.detail) {  //Firefox滑轮事件
				if (e.detail> 0) { //当滑轮向上滚动时
					 direct=1;
				}
				if (e.detail< 0) { //当滑轮向下滚动时
				   direct=0;
				}
			}
			zoom(direct);
			e.returnValue=false;  
		}
    }
	var zoom=function(direct){
		if(direct==1){
			if(ratio<=120)
			ratio+=10;
		}else{
			if(ratio>=80)
			ratio-=10;
		}
		$("body").css("zoom",ratio+"%");
	}
    //给页面绑定滑轮滚动事件
    if (document.addEventListener) {
        document.addEventListener('DOMMouseScroll', scrollFunc, false);
    }
    //滚动滑轮触发scrollFunc方法
    window.onmousewheel = document.onmousewheel = scrollFunc;  
});
/**
*设置等比缩放（非浏览器比例功能）
*/
function setZoom(ratio){
   $("body").css("transform","scale("+ratio+","+ratio+")");
   $("body").css("-ms-transform","scale("+ratio+","+ratio+")");
   $("body").css("-webkit-transform","scale("+ratio+","+ratio+")");
   setTimeout(setPos,500,ratio);
}
/**
*重新定位
*/
function setPos(ratio){
   $("body").css("position","absolute");
   $("body").css("top",(ratio-1)*$(window).height()/2);
   $("body").css("left",(ratio-1)*$(window).width()/2);
}