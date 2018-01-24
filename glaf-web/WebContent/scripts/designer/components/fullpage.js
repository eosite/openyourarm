var fullpage_designer = {
		
		/**
		 * 设置控件名称
		 */
		setName : function(component, val) {
			var $component = $(component);
			var prop = "name";
			fullpage_designer.setDataRule(component,prop,val);
			if (val == '') {
				$component.attr("cname", "");
			} else {
				$component.attr("cname", val);
			}
		},
		/**
		 * 设置外间距
		 */
		setMarginTop : function(component, val) {
			var prop = "marginTop";
			fullpage_designer.setDataRule(component,prop,val);
			fullpage_designer.setMargin(component, "top", val);
		},
		setMarginBottom : function(component, val) {
			var prop = "marginBottom";
			fullpage_designer.setDataRule(component,prop,val);
			fullpage_designer.setMargin(component, "bottom", val);
		},
		setMarginLeft : function(component, val) {
			var prop = "marginLeft";
			fullpage_designer.setDataRule(component,prop,val);
			fullpage_designer.setMargin(component, "left", val);
		},
		setMarginRight : function(component, val) {
			var prop = "marginRight";
			fullpage_designer.setDataRule(component,prop,val);
			fullpage_designer.setMargin(component, "right", val);
		},
		setMargin : function(component, direct, val) {
			if (val == '') {
				component.css("margin-" + direct, "");
			} else {
				component.css("margin-" + direct, val + "px");
			}
		},

		/**
		 * 设置位置
		 */
		setPositionTop : function(component, val) {
			var prop = "top";
			fullpage_designer.setDataRule(component,prop,val);
			fullpage_designer.setPositon(component, 'top', val);
		},
		setPositionBottom : function(component, val) {
			var prop = "bottom";
			fullpage_designer.setDataRule(component,prop,val);
			fullpage_designer.setPositon(component, 'bottom', val);
		},
		setPositionLeft : function(component, val) {
			var prop = "left";
			fullpage_designer.setDataRule(component,prop,val);
			fullpage_designer.setPositon(component, 'left', val);
		},
		setPositionRight : function(component, val) {
			var prop = "right";
			fullpage_designer.setDataRule(component,prop,val);
			fullpage_designer.setPositon(component, 'right', val);
		},
		setPositon : function(component, direct, val) {
			component.css("position", "relative");
			if (val == '') {
				component.css(direct, '');
			} else {
				component.css(direct, val + "px");
			}
		},
		/**
		 * 设置定位方式
		 */
		setPositonStyle : function(component, val) {
			var prop = "position";
			fullpage_designer.setDataRule(component,prop,val);
			component.css("position", val);
		},

		/**
		 * 设置宽度
		 */
		setWidth : function(component, val) {
			var prop = "width";
			fullpage_designer.setDataRule(component,prop,val);
			if (val == '') {
				component.css("width", '');
			} else {
				component.css("width", val + "px");
			}

		},

		/**
		 * 设置高度
		 */
		setHeight : function(component, val) {
			var prop = "height";
			fullpage_designer.setDataRule(component,prop,val);
			if (val == '') {
				component.css("height", '');
				//设置line-height
				component.find('.progress-bar').css("line-height",'');
				component.find('.progress_remain').css("line-height",'20px');
			} else {
				component.css("height", val + "px");
				component.find('.progress-bar').css("line-height",val + "px");
				component.find('.progress_remain').css("line-height",val + "px");
			}
		},

		/**
		 * 设置主题
		 * 
		 * @param {}
		 *            component
		 * @param {}
		 *            val
		 */
		setTheme : function(component, val) {
			var prop = "theme";
			fullpage_designer.setDataRule(component,prop,val);
			var obj = component.find(".progress-bar");
			if (val !== "") {
				obj.removeClass("progress-bar-success progress-bar-info progress-bar-warning progress-bar-danger");
				obj.addClass(val);
			}
		},
		setDataRule : function(component,prop,val){
			
			var lastVal = fullpage_designer.getDataRule(component,prop)
			
			if(component.attr("data-rule")){
				var data_rule = JSON.parse(component.attr("data-rule"));
				data_rule[prop]=val;
			}else{
				var data_rule={};
				data_rule[prop]=val;
			}
			component.attr("data-rule",JSON.stringify(data_rule));
			
			return lastVal;
			
		},
		getDataRule : function(component,prop){
			//debugger;
			var value ="";
			if(component.attr("data-rule")){
				value = JSON.parse(component.attr("data-rule"))[prop];
			}
			return value;
		},
		
	
		//左加一屏
		//右加一屏
		addPage :function(component,prop){
			
			
			
			 var currentElem=$('div[bc]')
			 
			 
			 
			 var child=$(currentElem[0]).find('.slide').length;
			 var choseEle=$(currentElem[0]);
			 var slide=currentElem.find('.fp-slide.active')
			 
			 $.fn.fullpage.destroy('all');
			 currentElem.addClass("active");
			//当前屏的上一屏添加
			if(prop=="top"){
						$(choseEle).before($("<div class='section' data-centered='true'></div>"));
			}else if(prop=="bottom"){
						$(choseEle).after($("<div class='section' data-centered='true'></div>")); 
			}else if(prop=="left"){
					if(child==0){
						//把当前section中的所有子元素放到slide1中
						var cl=$(currentElem[0]).children();
						var slide1=$("<div class='slide' data-centered='true'></div>")
						slide1.append(cl);
						choseEle.prepend(slide1);
						choseEle.prepend($("<div class='slide' data-centered='true'></div>"));
					}else{
						slide.before($("<div class='slide' data-centered='true'></div>"));
						if(slide.length==0){
							choseEle.prepend($("<div class='slide' data-centered='true'></div>"));
						}
					}	
			}else{
					if(child==0){
						var cl=$(currentElem[0]).children();
						var slide1=$("<div class='slide' data-centered='true'></div>")
						slide1.append(cl);
						choseEle.append(slide1);
						choseEle.append($("<div class='slide' data-centered='true'></div>"));
					}else{
						slide.after($("<div class='slide' data-centered='true'></div>"));
						if(slide.length==0){
							choseEle.append($("<div class='slide' data-centered='true'></div>"));
						}
					}	
			}
			$('.fp-slide').css('width','100%');
			fullpage_designer.setStyle();
		},
		//删除当前屏
		deletePage :function(component,prop){
			var divSection=$('.section.active')
			var divSlide=$('.slide.active')
			var parents=$(divSlide[0]).parents('.section');
			var parent=$(parents[0]);
			var div=$(divSection[0]);
			//两个
			if(parent[0]==div[0]){
				var currentslide=$(divSlide[0]);
				var children=div.find('.fp-slide');
				if(children.length==1){
					div.remove();
				}
				$(currentslide).remove();
				$.fn.fullpage.destroy('all');
				div.addClass("active");
				var children=div.find('.fp-slide');
				if(children.length==1){
					$(children[0]).addClass('active');
					$(children[0]).css('width','100%');
				}
				fullpage_designer.setStyle();
			}else if(divSlide.length!=1){
				var xv=div.find('.fp-slide');
				if(xv.length==1||xv.length==0){
					div.remove();
				}else{
					div.find('.fp-slide.active').remove();
					$.fn.fullpage.destroy('all');
					div.addClass("active");
					var children=div.find('.fp-slide');
					if(children.length==1){
						$(children[0]).addClass('active');
						$(children[0]).css('width','100%');
					}
					fullpage_designer.setStyle();
				}
			}else{
				div.remove();
			}
			var children=div.find('.fp-slide');
			$.each(children,function(o){
				$(o).css('width','100%');
			});
		},
		setStyle:function(){
			$('#fullpage').Pagefullpage({
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
                scrollOverflow:true,
                autoScrolling:false
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
		}
}