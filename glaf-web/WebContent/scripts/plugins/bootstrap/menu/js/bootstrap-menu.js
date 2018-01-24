var Menu = function () { 
 // Handle sidebar menu
	
    var handleSidebarMenu = function () {
        // handle sidebar link click
        $('.page-sidebar-menu-mt').on('click', 'li > a.nav-toggle, li > a > span.nav-toggle', function (e) {
            var that = $(this).closest('.nav-item').children('.nav-link');

            if (!$('.page-sidebar-menu-mt').attr("data-initialized") && $('body').hasClass('page-sidebar-closed') &&  that.parent('li').parent('.page-sidebar-menu-mt').size() === 1) {
                return;
            }

            var hasSubMenu = that.next().hasClass('sub-menu');

            if (that.parents('.page-sidebar-menu-hover-submenu').size() === 1) { // exit of hover sidebar menu
                return;
            }

            if (hasSubMenu === false) {
                if ($('.page-sidebar-mt').hasClass("in")) { // close the menu on mobile view while laoding a page 
                    $('.page-header .responsive-toggler').click();
                }
                return;
            }

            if (that.next().hasClass('sub-menu always-open')) {
                return;
            }

            var parent =that.parent().parent();
            var the = that;
            var menu = $('.page-sidebar-menu-mt');
            var sub = that.next();

            var autoScroll = menu.data("auto-scroll");
            var slideSpeed = parseInt(menu.data("slide-speed"));
            var keepExpand = menu.data("keep-expanded");
            
            if (!keepExpand) {
                parent.children('li.open').children('a').children('.arrow').removeClass('open');
                parent.children('li.open').children('.sub-menu:not(.always-open)').slideUp(slideSpeed);
                parent.children('li.open').removeClass('open');
            }

            var slideOffeset = -200;

            if (sub.is(":visible")) {
                $('.arrow', the).removeClass("open");
                the.parent().removeClass("open");
                sub.slideUp(slideSpeed, function () {
                    if (autoScroll === true && $('body').hasClass('page-sidebar-closed') === false) {
                        if ($('body').hasClass('page-sidebar-fixed')) {
                            menu.slimScroll({
                                'scrollTo': (the.position()).top
                            });
                        } else {
//                            App.scrollTo(the, slideOffeset);
                        }
                    }
//                    handleSidebarAndContentHeight();
                });
            } else if (hasSubMenu) {
                $('.arrow', the).addClass("open");
                the.parent().addClass("open");
                sub.slideDown(slideSpeed, function () {
                    if (autoScroll === true && $('body').hasClass('page-sidebar-closed') === false) {
                        if ($('body').hasClass('page-sidebar-fixed')) {
                            menu.slimScroll({
                                'scrollTo': (the.position()).top
                            });
                        } else {
//                            App.scrollTo(the, slideOffeset);
                        }
                    }
//                    handleSidebarAndContentHeight();
                });
            }
			
            
			var fHover,sHover;
            fHover = $(this).find("ul.page-sidebar-menu-mt>li");
            sHover = $(this).find("ul.page-sidebar-menu-mt ul.sub-menu > li");	
			var fhovercolor = $(this).closest("div[data-role=megamenu]").attr("fhovercolor");
			var shovercolor = $(this).closest("div[data-role=megamenu]").attr("shovercolor");
			fHover.find(">a").css("background-color",fhovercolor);
			fHover.find("ul").css("background-color",fhovercolor);
			sHover.find(">a").css("background-color",shovercolor);	
			
            e.preventDefault();
        });
    }   
     return {
  
        init: function () {            
            handleSidebarMenu(); // handles main menu
//           handleSidebarToggler(); // handles sidebar hide/show
        }
    };   
}();


jQuery(document).ready(function() {    
   Menu.init(); // init metronic core componets
})