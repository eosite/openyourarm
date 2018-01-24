       var setting = {
            view: {
                selectedMulti: false
            },
            check: {
                enable: false
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
                enable: false
            },
			callback:{
				onClick: zTreeOnClick
			}
        };

        $(document).ready(function(){		
           $.fn.zTree.initZtree($("#templateCategory"),url,setting);
        });
	    /**
		var ue = UE.getEditor('container', {
						initialFrameWidth : '99%',
						initialFrameHeight : 500
		});*/
		var url=contextPath+"/rs/designer/categorys?treeId=1|";
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
					$.fn.zTree.init(container,setting, rdata);
				},
				error : function() {
					console.log("获取分类数据失败");
				}

		   }
		  );
		}});
		//ztree树节点点击事件
		function zTreeOnClick(event, treeId, treeNode){
			var options={
			   url:contextPath+"/rs/designer/listCategoryTemplates",
			   "treeId":treeNode.treeID,
			   callback:{
				   OnChooseClick:chooseOnClick,
				   OnApplyClick:applyClick,
				   OnApplyStyleClick:applyStyleClick,
				   OnOpen:openOn,
				   OnClose:closeOn,
				   OnMouseover:showMask
			   }
			};
			var templateGrid=$(".gridcontent").templateGrid(options);
		}
		//浏览按钮绑定事件
		function chooseOnClick(item){
			if(this&&this!=window){
			  var url=contextPath+"/rs/designer/"+item.id+"/categoryTemplate";
			  $.ajax({
				url : url,
				type : "post",
				async : false,
				contentType : "application/json",
				dataType : "json",
				success : function(rdata) {
					if(rdata.template!=null){
						//弹出浏览页面
						var url=contextPath+"/mx/form/page/preViewPage?uiType=bootstrap";
						openPostWindow(url,convertTemplate(rdata.template));
					}
				},
				error : function() {
					console.log("获取分类数据失败");
				}

			   }
			  );
			}
        }
		//格式化模板内容 去掉工具栏
		function convertTemplate(template) {
			var $layoutHtml =$("<div></div>").append($(template));
			//补充 栅格行 ID、data-role、scope、crtltype 属性值
			var lyrows = $layoutHtml.find(".lyrow.layout_elem[data-role]");
			var rowId, dataRole, cname, scope, crtltype, currRow, $lyrow;
			$.each(lyrows, function (i, lyrow) {
				$lyrow = $(lyrow);
				rowId = $lyrow.attr("id");
				dataRole = $lyrow.attr("data-role");
				scope = $lyrow.attr("scope");
				crtltype = $lyrow.attr("crtltype");
				cname = $lyrow.attr("cname");
				$lyrow.removeAttr("id");
				$lyrow.removeAttr("data-role");
				$lyrow.removeAttr("scope");
				$lyrow.removeAttr("ctrltype");
				currRow = $lyrow.children(".view").children();
				currRow.attr("id", rowId);
				currRow.attr("data-role", dataRole);
				currRow.attr("scope", scope);
				currRow.attr("crtltype", crtltype);
				if (cname && cname != undefined) {
					currRow.attr("cname", cname);
				}
			});
			//去掉设计元素
			$layoutHtml
				.find(
					".preview, .configuration,.formgrouptoolbar,.toolbar, .drag, .remove,.removecol")
				.remove();
			$layoutHtml.find(".lyrow").addClass(
				"removeClean");
			$layoutHtml.find(".box-element").addClass(
				"removeClean");
			$layoutHtml
				.find(
					".lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .removeClean")
				.each(function () {
					cleanHtml(this);
				});
			$layoutHtml
				.find(
					".lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .removeClean")
				.each(function () {
					cleanHtml(this);
				});
			$layoutHtml
				.find(
					".lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .removeClean")
				.each(function () {
					cleanHtml(this);
				});
			$layoutHtml
				.find(
					".lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .removeClean")
				.each(function () {
					cleanHtml(this);
				});
			$layoutHtml
				.find(
					".lyrow .lyrow .lyrow .lyrow .lyrow .lyrow .removeClean")
				.each(function () {
					cleanHtml(this);
				});
			$layoutHtml
				.find(
					".lyrow .lyrow .lyrow .lyrow .lyrow .removeClean")
				.each(function () {
					cleanHtml(this);
				});
			$layoutHtml
				.find(
					".lyrow .lyrow .lyrow .lyrow .removeClean")
				.each(function () {
					cleanHtml(this);
				});
			$layoutHtml.find(
				".lyrow .lyrow .lyrow .removeClean")
				.each(function () {
					cleanHtml(this);
				});
			$layoutHtml.find(".lyrow .lyrow .removeClean")
				.each(function () {
					cleanHtml(this);
				});
			$layoutHtml.find(".lyrow .removeClean").each(
				function () {
					cleanHtml(this);
				});
			$layoutHtml.find(".containerComp .containerComp .containerComp .containerComp .removeClean").each(
				function () {
					cleanHtml(this);
				});
			$layoutHtml.find(".containerComp .containerComp  .containerComp .removeClean").each(
				function () {
					cleanHtml(this);
				});
			$layoutHtml.find(".containerComp .containerComp .removeClean").each(
				function () {
					cleanHtml(this);
				});
			$layoutHtml.find(".containerComp .removeClean").each(
				function () {
					cleanHtml(this);
				});
			$layoutHtml.find(".removeClean").each(
				function () {
					cleanHtml(this);
				});
			$layoutHtml.find(".removeClean").each(function (index, el) {
				$(el).remove();
			});
			$layoutHtml.find('[contenteditable=plaintext-only]').each(function (index, el) {
				$(el).removeAttr('contenteditable');
			});
			//移除控件编辑状态样式
			$layoutHtml.find('.shine_red').removeClass("shine_red");
			$layoutHtml.find(".column").removeClass(
				"ui-sortable");
			//移除formgroup样式
			$layoutHtml.find('.myform').removeClass("myform");
			$layoutHtml.find('.myformgroup').removeClass("myformgroup");
			//获取设置了适应小尺寸规则的栅格
			var fitsizeRows = $layoutHtml.find(".row_sm,.row_xs");
			$.each(fitsizeRows, function (i, fitsizeRow) {
				addFitSizeColumn($(fitsizeRow));
			});
			$layoutHtml.find(".row")
				.removeClass("clearfix").children()
				.removeClass("column ui-droppable");

			var outPutHtml = $("<div class=\"container\"></div>");
			outPutHtml.append($layoutHtml.html());
			outPutHtml.find('.shine_red').removeClass("shine_red");
			//如果是图标控件 增加图标类型设置chartType=datarole，更改datarole为父控件datarole
			var charts = $(outPutHtml).find("[pdata-role='charts']");
			$.each(charts, function (i, chart) {
				$(chart).attr("chartType", $(chart).attr("data-role"));
				$(chart).attr("id", $(chart).attr("id").replace($(chart).attr("data-role"), "charts"));
				$(chart).attr("data-role", $(chart).attr("pdata-role"));
			});
			var charts = $(outPutHtml).find("[pdata-role='echarts']");
			$.each(charts, function (i, chart) {
				$(chart).attr("chartType", $(chart).attr("data-role"));
				$(chart).attr("id", $(chart).attr("id").replace($(chart).attr("data-role"), "echarts"));
				$(chart).attr("data-role", $(chart).attr("pdata-role"));
			});
			return outPutHtml.html();
		}
		function cleanHtml(e) {
			$(e).after($(e).children().html());
			$(e).children().remove();
		}
		function openPostWindow(url,data){

			var tempForm = document.createElement("form");
			tempForm.id = "tempForm1";
			tempForm.method = "post";
			tempForm.action = url;
			tempForm.target="_blank"; //打开新页面
			var hideInput = document.createElement("input");
			hideInput.type = "hidden";
			hideInput.name="pageContent"; //后台要接受这个参数来取值
			hideInput.value = data; //后台实际取到的值
			tempForm.appendChild(hideInput);
			if(document.all){
				tempForm.attachEvent("onsubmit",function(){}); //IE
			}else{
				var subObj = tempForm.addEventListener("submit",function(){},false);//firefox
			}
			document.body.appendChild(tempForm);
			if(document.all){
				tempForm.fireEvent("onsubmit");
			}else{
				tempForm.dispatchEvent(new Event("submit"));
			}
			tempForm.submit();
			document.body.removeChild(tempForm);
		}

		//套用模板按钮事件绑定
		function applyClick(item){
			if(this&&this!=window){
			    var url=contextPath+"/rs/designer/"+item.id+"/categoryTemplate";
			  $.ajax({
				url : url,
				type : "post",
				async : false,
				contentType : "application/json",
				dataType : "json",
				success : function(rdata) {
					if(rdata.template!=null){
						parent.ApplyPageTemplate(rdata.template,rdata.id);
					}
				},
				error : function() {
					console.log("套用模板失败");
				}

			   }
			  );
			   
			}
		}
		function applyStyleClick(item){
			if(this&&this!=window){
			parent.ApplyPageTemplateStyle(item.id);
			}
		}
		//打开模板大图弹窗
		function openOn(item){
			if(this&&this!=window){
			$.fancybox.open({
						href:contextPath+"/rs/designer/"+item.id+"/getCategoryTemplateImage",
						type: 'image',
						title:item.title,
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
			});
			}
		}
		//关闭模板大图弹窗
		function closeOn(item){
			if(this&&this!=window){
			$.fancybox.close();
			}
		}
		function showMask(){
			//获取当前图片的位置和高宽度信息
			if(this&&this!=window){
			var imgObj=$(this).find(".img-responsive");
			//设置遮罩层大小位置
			var mask=$(this).find(".mask");
			mask.height(imgObj.height());
			mask.width(imgObj.width());
			mask.css("position","absolute");
			mask.css("line-height",imgObj.height()+"px");
			mask.css("top",imgObj.position().top);
			mask.css("left",imgObj.position().left);
			}
		}
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
							$this.options.count=rdata.count&&rdata.count!=undefined?rdata.count:0;
							$this.options.dataset=rdata.data&&rdata.data!=undefined?rdata.data:{};
						}
					},
					error : function() {
						console.log("获取控件列表数据失败");
					}
				});
			},
			refresh:function(){
				this.setDataset();
				this.render();
				this.initEvent();
			},
			renderContent:function(){
				var $this=this;
				$(this.element).empty();
				var rowdom,rowNo,coldom,thumbnaildom;
				var pageNo=this.options.pageNo;
				var pagesize=this.options.pagesize;
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
					thumbnaildom.find("img").attr("src",contextPath+"/rs/designer/"+item.id+"/getCategoryTemplateImage");
					 
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
					thumbnaildom.find("h3").text(item.title);
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
					if($callback["OnApplyStyleClick"]!=null&&typeof $callback["OnApplyStyleClick"] =="function"){
					   //绑定事件
					   thumbnaildom.find(".applyStyleTemplateBt").click(function(e){
					      $callback["OnApplyStyleClick"].call(this,item);
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
							that.options.pageNo = parseInt($(this).html());
							that.setDataset();
							that.render();
						});
						$(this.element).on("click",".prevBt",function(e){
							if(that.options.pageNo-1>0){
								that.options.pageNo= that.options.pageNo-1;
								that.setDataset();
								that.render();
							}
						});
						$(this.element).on("click",".nextBt",function(e){
							if(that.options.pageNo+1<=that.options.totalPage){
								that.options.pageNo = that.options.pageNo+1;
								that.setDataset();
								that.render();
							}	
						});
					},
			renderPagination:function(){
				    var $this=this;
				    if(this.options.count>0){
                    var rowdom=$("<div class=\"row\"></div>");
                    var coldom=	$("<div class=\"col-sm-12 col-md-12\"></div>");			
					var paginateBtToolBar=$("<div class=\"btn-toolbar margin-bottom-10\">");	
                    var paginateBtGroup=$(" <div class=\"btn-group\">");
					var prevBt=$("<button type=\"button\" class=\"btn btn-default prevBt\"><i class=\"fa fa-angle-left\"></i></button>");
					if(this.options.pageNo==1){
						prevBt.addClass("disabled");
					}
                    paginateBtGroup.append(prevBt);
					var pageBt;
					var totalPages=Math.ceil(this.options.count/this.options.pagesize);
					for(var i=0;i<totalPages;i++){
						pageBt=$("<button type=\"button\" class=\"btn pageBt btn-default\"></button>");
						pageBt.append(i+1);
						paginateBtGroup.append(pageBt);
					}
					var nextBt=$("<button type=\"button\" class=\"btn btn-default nextBt\"><i class=\"fa fa fa-angle-right\"></i></button>");
					if(this.options.pageNo==totalPages){
						nextBt.addClass("disabled");
					}
					paginateBtGroup.append(nextBt);
					paginateBtToolBar.append(paginateBtGroup);
					coldom.append(paginateBtToolBar);
					rowdom.append(coldom);
					$($this.element).append(rowdom);
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
				pagesize:9,
				pageNo:1,
				count:0,
				rowColumns:3,
				dataset:{},
				callback:{
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
             $(".myalert").fadeIn(500).delay(2000).fadeOut(1000);;
		}
		$(function() {
			$(".content").css("min-height","540px");
			$(".content").height($(window).height()-80);
			//默认展开树第一层
			var treeObj = $.fn.zTree.getZTreeObj("templateCategory");
			if(treeObj){
				var nodes = treeObj.getNodes();
				if (nodes.length>0) {
					treeObj.expandNode(nodes[0], true, false, true);
					$("#"+nodes[0].tId+"_a").click();//默认选中第一个节点
				}
			}
			
		});
		$(window).resize(function() {
			$(".content").height($(window).height()-80);
		});
