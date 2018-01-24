! function($) {
	var plugin = 'muitab';

	var Build = function(target, state) {
		this.target = target;
		this.state = state;
		this.options = state.options;
		this._init();
	};
	$.parser.plugins.push(plugin);
	$.fnInit(plugin, function(target) {

		var state = $.data(target, plugin + '.options');
		var columns = $(target).data("columns");
		if (columns && columns.length > 0) {
			state.options.columns = eval('(' + columns + ')');
		}
		var p = new Build(target, state);

		$.data(target, plugin, p);
	});

	$.fn[plugin].defaults = {
		datas : null,
		url:"",
		visible:true,	//显示
		events: {
		}
	};

	$.fn[plugin].methods = {
		_init : function(){
			var that = this;
			var options = that.options;
			var $target = $(that.target);
			that.$target = $target;

			if(that.$target.attr("realWidth")){
				that.$target.css("width",that.$target.attr("realWidth"));
			}
			if(that.$target.attr("realHeight")){
				that.$target.css("height",that.$target.attr("realHeight"));
			}
			

			if(!that.$target.is(":hidden")){
				that.$target.css("height",that.$target.css("height"));
				that.heightEnd = true;
			}
			
			if(!that.endinit){
				that._buildTab();
				$target.find(".slider").removeAttr("data-slider");
			}
			
			mui("#"+that.target.id).slider({});
			that.endinit = true;
			that._buildEvent();
			that.resize();
		},
		resize : function(){
			var that = this;

			if(!that.$target.is(":hidden") ){
				if(!that.heightEnd){
					that.$target.css("height",that.$target.css("height"));
					that.heightEnd = true;
				}
				var flag = false;
				if(!that.lastHeight || that.lastHeight != that.$target.height()){
					that.lastHeight = that.$target.height();
					flag = true;
				}
				if(flag){
					that.$tabContent.height(that.$slider.height() - that.$tabContentHeader.outerHeight(true));
					mui("#"+that.target.id).slider({});	
				}
			}else{
				that.lastHeight = null;
			}
		},
		_buildEvent : function(){
			var that = this;
			that.count = that.count || 0;
			$(that.target).resize(function() {
				if(!that.count){
					that.count++;
					setTimeout(function(){
						that.resize.call(that);
						that.count = 0;
					},100)
				}
			});
		},
		getData : function(){
			var that = this;
			return that.tabdatas;
		},	
		_buildTab : function(){
			var that = this;
			var tabdatas = that.options.tabdatas;
			if(tabdatas){
				that.$target.empty();
				// that._buildHeader();
				that._buildContents(tabdatas);
			}else{
				that.$slider = that.$target.find(".slider");
				that.$tabContentHeader = that.$target.find(".sliderSegmentedTitle");
				that.$tabContent = that.$target.find(".sliderSegmentedContent");
				that.$progressbar = that.$target.find(".sliderProgressBar");
			}
		},
		_buildContents : function(datas){
			var that = this;
			var $content = $('<div class="mui-content"></div>');
			var $slider = $('<div class="mui-slider slider"></div>');
			that.$target.append($content);
			$slider.css("margin","0px 0px");
			that.$slider = $slider;
			$content.append($slider);

			var $tabContentHeader = $('<div class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted sliderSegmentedTitle"></div>');
			var $tabContent = $('<div class="mui-slider-group sliderSegmentedContent"></div>');
			$slider.append($tabContentHeader);
			var $progressbar = $('<div class="mui-slider-progress-bar sliderProgressBar"></div>');
			$slider.append($progressbar);
			$slider.append($tabContent);
			that.$tabContentHeader = $tabContentHeader;
			that.$tabContent = $tabContent;
			that.$progressbar = $progressbar;
			
			$.each(datas,function(i,item){
				that.addTabContent(item,true);
			})
			var widthpercent = (100 / datas.length) + '%';
			// $progressbar.hide();
			$progressbar.width(widthpercent);
			
		},
		//创建表头
		_buildHeader : function(){
			var that = this;
			var $header = $('<header class="mui-bar mui-bar-nav"></header>');
			var $back = $('<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>');
			var $title = $('<h1 class="mui-title">顶部选项卡-可左右拖动(div)</h1>');
			$header.append($back);
			$header.append($title);
			if(that.hideHeader || (that.$target.attr("tab_header") == 'hide')){
				$header.hide();
			}
			that.$target.append($header);
			that.$header = $header;
		},
		getMaxLength : function(){
			return this.$tabContent.find(".mui-control-content").length;
		},
		getMaxNumber : function(){
			var that = this;
			var $content =  that.$tabContent.find(".mui-control-content");
			var maxi = 0,rowIndex;
			$.each($content,function(i,item){
				rowIndex = $(item).attr("row-index");
				if(maxi < rowIndex){
					maxi = rowIndex;
				}
			})
			return maxi;
		},
		resetProgressbar : function(){
			var that = this;
			var widthpercent = (100 / that.getMaxLength()) + '%';
			that.$progressbar.width(widthpercent);
		},
		addTabContent : function(item,flag){
			var that = this;
			var i = that.getMaxNumber();
			i++;
			var id = that.target.id + "_tab_"+i;
			//标题
			var $title = $('<a class="mui-control-item" href="#'+id+'"></a>');
			$title.append(item.title);
			$title.attr("row-index",i);
			that.$tabContentHeader.append($title);
			//内容
			var $content = $('<div class="mui-slider-item mui-control-content"></div>');
			$content.attr('id',id);
			$content.append(item.content);
			$content.attr("row-index",i);
			$content.css("min-height","200px");
			$content.addClass("containerComp");
			that.$tabContent.append($content);
			if(item.active){
				$title.addClass("mui-active");
				$content.addClass("mui-active");
			}
			that.resetProgressbar();
			if(!flag){
				mui("#"+that.target.id).slider({});
			}
		},
		removeLast : function(i){
			var that = this;

			var $content = that.$tabContent.find(".mui-control-content");
			if($content){
				that.remove($($content[$content.length-1]).attr("row-index"));		
			}
			that.resetProgressbar();
			mui("#"+that.target.id).slider({});
		},
		remove : function(i){
			var that = this;
			that.$target.find("[row-index='"+i+"']").remove();
			that.resetProgressbar();
			mui("#"+that.target.id).slider({});
		}
	}

	Build.prototype = $.extend(true, {}, $.fn[plugin].methods, {
		constructor: Build
	});

}(jQuery);