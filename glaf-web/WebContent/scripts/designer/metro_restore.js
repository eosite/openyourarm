var metro_restore = {
	restoreAll: function(){
		metro_restore.initICheck();
		metro_restore.initSelect2();
//		metro_restore.initGantt();

		metro_restore.initTabs();
		metro_restore.initMuiTabs();
		// metro_restore.initPortlet();
		metro_restore.initTouchSpin();
		metro_restore.initMenu();
		metro_restore.initSwitch();
		metro_restore.initFullpage();

		metro_restore.initAspectLayout();
	},
	initAspectLayout: function(){
		$('.aspectlayout').each(function(index,el){
			var $self = $(this);
			if($self.aspectlayout){
				$self.aspectlayout();
			}
		})
	},
	initSwitch: function(){
		$('[data-role=switch]').each(function(index,el){
			var $self = $(this);
			$self.find(".switch_content").html($self.find("input.switch_input"));
			$self.find("input.switch_input").bootstrapSwitch();
		})
	},
	initFullpage: function(){
		var tabs = $('[data-role=fullpage]');
			$.each(tabs, function(i, o) {
				$(o).Pagefullpage({
					navigation: false,
					navigationPosition: '',
					slidesNavigation: false,
					slidesNavPosition: '',
					verticalCentered:false,
					slidesColor:'#323423',
					controlArrowColor:'#adf',
					scrollingSpeed: 1000,
					loopHorizontal: false,
					continuousVertical: true,
					continuousHorizontal: true,
					controlArrows: true,
					keyboardScrolling:true,
					sectionsColor:['#f2f2f2', '#4BBFC3', '#7BAABE', 'whitesmoke', '#000'],
                    scrollOverflow:true,
                    autoScrolling:false
				});
		});
		$('.section').on('click',function(){
			$('.section').removeClass('besed');
			$('.slide').removeClass('besed');
			$(this).addClass('besed');
			$('.section,.slide').removeAttr("bc");
			$(this).attr("bc","true");
		});
		$('.slide ').on('click',function(){
			$('.section').removeClass('besed');
			$('.slide').removeClass('besed');
			$(this).addClass('besed');
			$('.section,.slide').removeAttr("bc");
			$(this).attr("bc","true");
		});
		$('.fp-slide').addClass('containerComp ui-sortable');
		var section=$('.fp-section');
		$.each(section,function(i,o){
			var childs=$(o).find('.fp-slide');
			if(childs.length==0){
				$(o).addClass('containerComp ui-sortable');
			}else{
				$(o).removeClass('containerComp ui-sortable');
			}
		})
		
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
			self.on('ifClicked', "input", function(event) {
				$(this).closest("label").toggleClass("selected");
				// pubsub.pub(e.pageEvent, "", event);
			});
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
			self.on('ifClicked', "input", function(event) {
				self.find("label.selected").removeClass("selected");
				$(this).closest("label").addClass("selected");
				// pubsub.pub(e.pageEvent, "", event);
			});
		});
		
	},
	initSelect2: function(){
		//select2 init
		var select2s = $('[data-role=metroselect]');
		$.each(select2s, function(i, o) {
			// $(o).find('select.select2').empty();
			$(o).metroselect({asDemo:true,static:true});
		});
		select2s = $('[data-role=metroselect_m]');
		$.each(select2s, function(i, o) {
			$(o).metroselect_m({asDemo:true});
		});
	},
	initMuiTabs: function(){
		var tabs = $('[data-role=muitab]');
		$.each(tabs, function(i, o) {
			$(o).muitab({});
		});
	},
	initTabs: function(){
		//tabs init
		var tabs = $('[data-role=bootstrap_tabs]');
		$.each(tabs, function(i, o) {
			/*$(o).find('a[data-toggle="tab"]').click(function(event){
				$(this).tab('show');
			});*/
			/*
			* ²»ÖªµÀÊ²Ã´Ô­Òò$(o)µ¼ÖÂÑ¡¿¨ÀïÃæÌ×Ñ¡¿¨ÔÚ¶¨Òå½çÃæµ¼ÖÂÀïÃæµÄÑ¡¿¨³õÊ¼»¯²»ÁË
			*/
			$("#"+$(o).attr("id")).tabs({
				asDemo: true,
				minHeight: 200,
				onLoad: function(){initContainer();disableSortable();},
				onSelect:function(){
					enableSortable();
					disableSortable();
				}
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
	initTouchSpin:function(){
		$("div[data-role=touchspin]").each(function(i,o){
	 		var $this = $(this);
	 		$this.find("input").TouchSpin({
	            initval: 0
	        });
		});
	},
	initMenu:function(){
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
	}
};
$(function(){
	metro_restore.restoreAll();
});