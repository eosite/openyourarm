       var setting = {
            view: {
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom,
                selectedMulti: false
            },
            check: {
                enable: true,
				chkStyle: "radio",
				radioType:"all"
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
                enable: true,
				removeTitle:"删除模板分类",
				renameTitle:"修改分类名称"
            },
			callback:{
				onClick: zTreeOnClick,
				onRename:zTreeOnRename,
				beforeRemove:zTreeBeforeRemove
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
		var url=contextPath+"/rs/designer/categorys";
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
					rdata.push({categoryId:"0",parentId:"0",name:"模板类型列表",open:true,chkDisabled:true});
					$.fn.zTree.init(container,setting, rdata);
				},
				error : function() {
					console.log("获取分类数据失败");
				}

		   }
		  );
		}});
        var newCount = 1;
		//新增节点事件
        function addHoverDom(treeId, treeNode) {
            var sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
            var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                + "' title='新增模板分类' onfocus='this.blur();'></span>";
            sObj.after(addStr);
            var btn = $("#addBtn_"+treeNode.tId);
            if (btn) btn.bind("click", function(){
                
                //zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"模板分类" + (newCount++)});
				return zTreeOnAdd(treeId,treeNode);
            });
        };
		//新增节点后台处理
		function zTreeOnAdd(treeId,treeNode){
			 var pId=treeNode.categoryId;
			 var pTreeId=treeNode.treeID;
			 var level=treeNode.level+1;
             var url=contextPath+"/rs/designer/category/add";
			 var name="新建分类" + (newCount++);
			 var params={"pId":pId,"pTreeId":pTreeId,"level":level,"name":name};
			 var result=false;
			 $.ajax({
				url : url,
				type : "post",
				async : false,
				data:params,
				contentType : "application/x-www-form-urlencoded",
				dataType : "json",
				success : function(rdata) {
					if(rdata!=null&&rdata.result==1){
						var zTree = $.fn.zTree.getZTreeObj(treeId);
						zTree.addNodes(treeNode, {id:rdata.id,"categoryId":rdata.categoryId,pId:pId,name:name});
						result= true;
					}else{
						result= false;
					}
					
				},
				error : function() {
					console.log("新增模板分类失败");
					result= false;
				}
		   });
		   return result;
		}
        function removeHoverDom(treeId, treeNode) {
            $("#addBtn_"+treeNode.tId).unbind().remove();
        };
		//ztree树节点重命名事件
		function zTreeOnRename(event, treeId, treeNode, isCancel){
			 var categoryId=treeNode.categoryId;
			 var name=treeNode.name;
			 var url=contextPath+"/rs/designer/category/rename";
			 var params={"categoryId":categoryId,"name":name};
			 var result=false;
			 $.ajax({
				url : url,
				type : "post",
				async : false,
				data:params,
				contentType : "application/x-www-form-urlencoded",
				dataType : "json",
				success : function(rdata) {
					result= true;
				},
				error : function() {
					console.log("重命名失败");
					result= false;
				}

		   }
		  );
		  return result;
		}
		//ztree树节点移除事件
		function zTreeBeforeRemove(treeId, treeNode){
			 var result=false;
			 if(confirm("确认删除分类"+treeNode.name+"吗?")){
			 var categoryId=treeNode.categoryId;
			 var url=contextPath+"/rs/designer/category/delete";
			 var params={"categoryId":categoryId};
			 $.ajax({
				url : url,
				type : "post",
				async : false,
				data:params,
				contentType : "application/x-www-form-urlencoded",
				dataType : "json",
				success : function(rdata) {
					result= true;
				},
				error : function() {
					console.log("删除分类失败");
					result= false;
				}
		   }
		  );
			 }
		  return result;
		}
		//ztree树节点点击事件
		function zTreeOnClick(event, treeId, treeNode){
			var options={
			   url:contextPath+"/rs/designer/listCategoryTemplates",
			   "category":treeNode.categoryId,
			   callback:{
				   OnChooseClick:chooseOnClick,
				   OnApplyClick:applyClick,
				   OnOpen:openOn,
				   OnClose:closeOn,
				   OnMouseover:showMask
			   }
			};
			var templateGrid=$(".gridcontent").templateGrid(options);
			$('.nav-tabs a[href="#portlet_tab2"]').tab('show');
		}
		//编辑模板按钮事件绑定
		var currentEditItem;
		function chooseOnClick(item){
			currentEditItem=item;
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
						 $('.nav-tabs a[href="#portlet_tab1"]').tab('show');
						   var  div=$("<div class='contentArea'></div>");
						   div.append(rdata.template);
						   $("#summernote").summernote('code',div);
						   /**
						   ue.ready(function () {
								// editor准备好之后才可以使用
								ue.setContent(div.prop("outerHTML"));
							 });
						   */
						   $("#tmpName").val(item.title);
						   $(".fileinput-exists").trigger("click");
					}
				},
				error : function() {
					console.log("套用模板失败");
				}

			   }
			  );
			  
			}
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
						parent.ApplyTemplate(rdata.template);
					}
				},
				error : function() {
					console.log("套用模板失败");
				}

			   }
			  );
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
						console.log("获取控件列表数 据失败");
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
				pagesize:8,
				pageNo:1,
				count:0,
				rowColumns:4,
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
		function changeTab(tabId){
			$('.nav-tabs a[href="#'+tabId+'"]').tab('show');
		}
		$(function() {
			$(".tab-pane").css("min-height","540px");
			$(".tab-pane").height($(window).height()-80);
			$('.joinBtn').click(function(){
					//获取当前选中的分类
					var zTree = $.fn.zTree.getZTreeObj("templateCategory");
					//获取选中节点
					var nodes=zTree.getCheckedNodes(true);
					//获取编辑器内容
					var code=$("#summernote").summernote('code');
					//var code=ue.getContent();
					var tmpName=$("#tmpName").val();
					if(nodes==null||nodes.length==0){
						$.alert(1,"请选择分类");
						return false;
					}
					if($.trim(code)==''){
						$.alert(1,"模板内容为空");
						return false;
					}
					code=$(code).html();
					//保存操作
					var url=contextPath+"/rs/designer/"+nodes[0].categoryId+"/saveTemplate";
					var params={"code":code,"tmpName":tmpName};
					if(nodes[0].categoryId==1||nodes[0].parentId==1){
						//获取控件模板规则
						var comps=$(code).find("[data-role]");
						var compsDataRule={},dataRule,dataRole;
						//获取页面全局定义
						if($(parent.document)&&$(parent.document).find(".designer").length>0){
							var pageDataRule=$(parent.document).find(".designer").attr("data-rule");
							compsDataRule["page"]=pageDataRule;
						}
						$.each(comps,function(i,comp){
							dataRule=$(comp).attr("data-rule");
							dataRole=$(comp).attr("data-role");
							if(dataRule!=null)
							{
								compsDataRule[dataRole]=dataRule;
							}
						});
						params.compDataRule=JSON.stringify(compsDataRule);
					}
					//检测用户是否有上传缩率图
					var imgPath=$("#thumbnail").val();
					if(imgPath!=null&&imgPath!=""){
						params.imgdata=$(".fileinput-preview.thumbnail").children("img").attr("src");
						saveTmp(url,params);
					}else{
                    html2canvas($('.contentArea'), {
					//html2canvas($($("iframe")[1].contentWindow.document.body).find(".contentArea"), {	
                    onrendered : function(canvas) {  
						var myImage = canvas.toDataURL("image/jpeg");
						params.imgdata=myImage;
						saveTmp(url,params);
						}  
					  });  
					}

				});
				function saveTmp(url,params){
					//并将图片上传到服务器用作图片分享  
						$.ajax({
						url : url,
						type : "post",
						async : false,
						data:params,
						contentType : "application/x-www-form-urlencoded",
						dataType : "json",
						success : function(rdata) {
							if(rdata!=null){
								var id=rdata.id;
								$.alert(0,"保存成功！");
							}
						},
						error : function() {
							console.log("保存到分类成功！");
						}
						 });
				}
				function exitFile(url,params){
					
					var content = '<form action="'+url+'?ids='+params+'" method="post" id="uploadForm"></form>'
					$("body").append(content);
					$("#uploadForm").submit();
					$("#uploadForm").remove();
				}
				$('.editBtn').click(function(){
					//获取当前编辑的模板ID
                    var templateId= currentEditItem && currentEditItem.id;
                    if(!templateId){
                    		$.alert(1,"请先保存模版");
                    		return;
                    }
					//获取编辑器内容
					var code=$("#summernote").summernote('code');
					if($.trim(code)==''){
						$.alert(1,"模板内容为空");
						return false;
					}
					code=$(code).html();
					var tmpName=$("#tmpName").val();
					//保存操作
					var url=contextPath+"/rs/designer/"+templateId+"/editTemplate";
					var params={"code":code,"tmpName":tmpName};
                    //检测用户是否有上传缩率图
					var imgPath=$("#thumbnail").val();
					if(imgPath!=null&&imgPath!=""){
						params.imgdata=$(".fileinput-preview.thumbnail").children("img").attr("src");
						saveTmp(url,params);
					}else{
					html2canvas($('.contentArea'), {
						onrendered : function(canvas) {  
						var myImage = canvas.toDataURL("image/jpeg");
						params.imgdata=myImage;
						saveTmp(url,params);
						}  
					   });  
					}
				});
				$('.exitBtn').click(function(){
					var content = $(".tab-content").find(".templatecolumn .thumbnail");
					var array = [];
					$.each(content,function(i,v){
						
						array.push(v.getAttribute("id"));
					});
					var url = contextPath + "/rs/designer/exitTemplate";	
					var str = array.join(',');
									
					exitFile(url,str);
				});
			if(action=='join'){
				$('.nav-tabs a[href="#portlet_tab1"]').tab('show');
			}else{
				$('.nav-tabs a[href="#portlet_tab2"]').tab('show');
				$('.joinBtn').remove();
			}
			$("#summernote").summernote({
			  lang: 'zh-CN',
			  minHeight: 450,// set minimum height of editor
			  focus: true}
			  );
			
		});
		function setSummernoteCode(content){
			var  div=$("<div class='contentArea'></div>");
			div.append(content);
			$("#summernote").summernote('code',div);
			/**百度编辑器集成
			ue.ready(function () {
					// editor准备好之后才可以使用
					ue.setContent(div.prop("outerHTML"));
				 });
		   */
		}
		$(window).resize(function() {
			$(".tab-pane").height($(window).height()-80);
		});
