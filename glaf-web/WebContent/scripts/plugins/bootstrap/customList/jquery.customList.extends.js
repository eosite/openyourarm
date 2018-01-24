//模板列表插件
!(function ($) {

	$.fn.customList.defaults = {
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
		refresh:function(){
			this.options.data == null || this.options.data == undefined ? this.setStaticDataSet(this.option.data) : this.setDataset();
			this.setDataset();
			this.render();
			this.initEvent();
		},
		setStaticDataSet : function(data){
			var that = this;
			that.options.count = data.length();
			that.option.dataset = data;
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
					}
				},
				error : function() {
					console.log("获取控件列表数 据失败");
				}
			});
		},
		//getPageData:function(pageNo){  //客户端分页
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
		render:function(){
			this.renderContent();
			this.renderPagination();
		},
		renderContent:function(){
			var $this=this;
			$(this.element).empty();
			var rowdom,rowNo,coldom,thumbnaildom;
			var pageNo=this.options.page;
			var pagesize=this.options.rows;
			var rowColumns=this.options.rowColumns;
			rowNo=(pageNo-1)*(pagesize/rowColumns);
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
				//thumbnaildom.find(".viewTemplateBt").attr("id","img_"+item.id);
				//thumbnaildom.find(".viewTemplateBt").attr("title",item.title);
				//thumbnaildom.find(".viewTemplateBt").attr("href",contextPath+"/rs/designer/"+item.id+"/getCategoryTemplateImage");
				thumbnaildom.find("img").attr("src",contextPath+"/website/lob/lob2/download?fileId="+item.id);
				 
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
		},
		initEvent:function(){
			var that = this;
			$(this.element).on("click",".pageBt",function(e){
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
		}
	};
	var list = $.fn.customList=function(options){
		   // 合并参数
		return this.each(function () {
			//在这里编写相应的代码进行处理
			var ui = $.data(this, "customList");
			 //如果该元素没有初始化过(可能是新添加的元素), 就初始化它.
			if (!ui) {
				var opts = $.extend(true, {}, $.fn.customList.defaults, typeof options === "object" ? options : {});
				var ui = new Plugin(this, opts);
				// 缓存插件
		        $.data(this, "customList", ui);
			}
			// 调用方法
			if (typeof options === "string" && typeof ui[options] == "function") {
				// 执行插件的方法
				ui[options].apply(ui, args);
			}
		});
	};
	
	list.template = '<div class="thumbnail">'+
//		    			'<span class="mask"></span>'+
						'<img class="img-responsive" src="${contextPath}/images/a.jpg" >'+
//			 			'<div class="caption">'+
//							'<p class="btarea" style="text-align:center;">'+
//				  				 '<a href="#" class="btn thumbnailbt viewTemplateBt" role="button">查看</a>'+
//				   				 '<a href="#" class="btn thumbnailbt editTemplateBt" role="button">编辑</a>'+
//				  				 '<a href="#" class="btn thumbnailbt applyTemplateBt" role="button">选用</a>'+
//							'</p>'+
//			 			'</div>'+
			 			'<p>模板1</p>'+
		  			'</div>';

})(jQuery);