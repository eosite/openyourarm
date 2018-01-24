//闭包限定命名空间
(function ($) {
    //默认参数
    var defaluts = {
        scroll:true,
		width:"250px",
		height:"300px",
		name:"控件列表",
		elemContainer:"",
		listelem:".mybox"
    };
    var methods = {
        init : function(options) {
			//支持多选择器多对象
			return this.each(function () {
               		
			    //支持链式调用
                var $this = $(this);
				if($this.data("componentIds")!=undefined&&$this.data("componentIds").length>0){
				  	 return $this;
				}
				var opts = $.extend({}, defaluts, options); //覆盖插件默认参数
				if(!isValid(opts)){
					return;
				}
				$this.css("width",opts.width);
				$this.find(".scroller").css("height",opts.height);
				var tb=$this.find(".scroller>table");
				tb.css("width",opts.width);
				$this.data("opts",opts);
				$this.find(".caption").html("<i class=\"fa fa-arrow-circle-right\"></i>"+opts.name);
				//获取所有控件列表
                $this.componentlist("load");
				$this.componentlist("bindToolbarEvent_");
				var l=$(window).width()-600;
				$this.css("left",l+"px");
				$this.show();
            });
        },
		collapse_:function(){
			
		},
		load:function (){
			    //获取所有控件列表
				var tb=this.find(".scroller>table");
				var scroller=this.find(".scroller");
				var components=$(this.data("opts").elemContainer).find(this.data("opts").listelem);
				var trdom,icontd,iconbt,nametd;
				var componentIds=new Array();
				var that=this;
				var componentId,pcomponent,pcomponentId,lchildcomps,plevel,level,ppaddingleft;
				$.each(components,function(i,component){
						$component=$(component);
						trdom=$("<tr></tr>");
						trdom.attr("id",$component.attr("id")+"_tr");
						trdom.attr("c_id",$component.attr("id"));
						icontd=$("<td class=\"eye\"></td>");
						iconbt=$("<button type=\"button\" class=\"btn btn-icon-only c-btn-border-1x c-btn-white\"></button>");
						if($component.css("display")=="none"){
							iconbt.title="隐藏";
							iconbt.append("<i class=\"fa fa-eye-slash font-white\"></i>");
						}else{
							iconbt.title="显示";
						    iconbt.append("<i class=\"fa fa-eye font-white\"></i>");
						}
						iconbt.unbind("click");
						componentId=$component.attr("id");
						icontd.append(iconbt);
						trdom.append(icontd);
						nametd=$("<td class=\"cname\"></td>");
						nametd.append($component.find(".preview>input").val());
						componentIds.push($component.find(".preview>input").val());
						trdom.append(nametd);
						//获取控件父控件
						pcomponent=$component.parents(".mybox");
						if(pcomponent.length>0)
						{
							pcomponentId=pcomponent.attr("id");
							trdom.attr("p_id",pcomponentId);
							level=1;
							//获取父控件所处层级
							pcomponent=$("#"+pcomponentId+"_tr");
							ppaddingleft=13+parseInt(pcomponent.find(".cname").css("padding-left").replace("px"));
							plevel=pcomponent.attr("level");
							if(plevel!=undefined){
							   level=parseInt(plevel)+1;
							}
							trdom.attr("level",level+"");
							trdom.find(".cname").css("padding-left",ppaddingleft+"px");
							//获取最后一个叶子节点
							lchildcomps=$("[p_id="+pcomponentId+"]:last");
							if(lchildcomps.length>0){
							   if($("[p_id="+lchildcomps.attr("c_id")+"]:last").length>0){
								   $("[p_id="+lchildcomps.attr("c_id")+"]:last").after(trdom);
							   }else{
								   lchildcomps.after(trdom);
							   }
							}else{
							   pcomponent.after(trdom);
							   if(pcomponent.find(".cname").find("i").length==0){
							   pcomponent.find(".cname").prepend("<i class=\"fa fa-caret-down font-white\"></i>");
						       that.componentlist("collapse_",pcomponent);
							   }
							}
						}else{
							trdom.attr("level","1");
							tb.append(trdom);
						}
						iconbt.bind("click",function(){
							    var componentId=$(this).closest("tr").attr("c_id");
								that.componentlist("showOrHide_",$(this),$("#"+componentId));
						});
						trdom.bind("click",function(){
								//控件选中
								$("#"+$(this).attr("c_id")).click();
								tb.find(".selected").removeClass("selected");
								$(this).addClass("selected");
						});
				});
			    App.destroySlimScroll(scroller);
				scroller.outerHeight(300);
				App.initSlimScroll(scroller);
				this.data("componentIds",componentIds);
		},
		reload_:function(){
			this.find("table").empty();
			this.componentlist("load");
		},
		addComponent:function($component){
			var that=this;
			var tb=this.find(".scroller>table");
			var trdom=$("<tr style='cursor:pointer;'></tr>");
			trdom.attr("id",$component.attr("id")+"_tr");
			trdom.attr("c_id",$component.attr("id"));
			var icontd=$("<td class=\"eye\"></td>");
			var iconbt=$("<button type=\"button\" class=\"btn btn-icon-only c-btn-border-1x c-btn-white\"></button>");
			if($component.css("display")=="none"){
				iconbt.title="隐藏";
				iconbt.append("<i class=\"fa fa-eye-slash font-white\"></i>");
			}else{
				iconbt.title="显示";
				iconbt.append("<i class=\"fa fa-eye font-white\"></i>");
			}
			var pcomponent,pcomponentId,lchildcomps,plevel,level,ppaddingleft;
			var componentId=$component.attr("id");
			icontd.append(iconbt);
			trdom.append(icontd);
			var nametd=$("<td class=\"cname\"></td>");
			nametd.append($component.find(".preview>input").val());
			var componentIds=this.data("componentIds");
			componentIds.push($component.find(".preview>input").val());
			this.data("componentIds",componentIds);
			trdom.append(nametd);
			//获取控件父控件
			pcomponent=$component.parents(".mybox");
			if(pcomponent.length>0)
			{
				pcomponentId=pcomponent.attr("id");
				trdom.attr("p_id",pcomponentId);
				//获取父控件所处层级
				pcomponent=$("#"+pcomponentId+"_tr");
				ppaddingleft=13+parseInt(pcomponent.find(".cname").css("padding-left").replace("px"));
			    plevel=pcomponent.attr("level");
				level=1;
				if(plevel!=undefined){
				   level=parseInt(plevel)+1;
				}
				trdom.attr("level",level+"");
				trdom.find(".cname").css("padding-left",ppaddingleft+"px");
				//获取最后一个叶子节点
				lchildcomps=$("[p_id="+pcomponentId+"]:last");
				if(lchildcomps.length>0){
				   if($("[p_id="+lchildcomps.attr("c_id")+"]:last").length>0){
					   $("[p_id="+lchildcomps.attr("c_id")+"]:last").after(trdom);
				    }else{
					   lchildcomps.after(trdom);
					}
				}else{
				   pcomponent.after(trdom);
				   if(pcomponent.find(".cname").find("i").length==0){
				   pcomponent.find(".cname").prepend("<i class=\"fa fa-caret-down font-white\"></i>");
			       this.componentlist("collapse_",pcomponent);
				   }
				}
			}else{
				trdom.attr("level","1");
				tb.append(trdom);
			}
			iconbt.unbind("click");
			iconbt.bind("click",function(){
					 var componentId=$(this).closest("tr").attr("c_id");
					 that.componentlist("showOrHide_",$(this),$("#"+componentId));
			});
			trdom.unbind("click");
			trdom.bind("click",function(){
					//控件选中
					$("#"+$(this).attr("c_id")).click();
					tb.find(".selected").removeClass("selected");
					$(this).addClass("selected");
			});
		},
		empty_:function(){
			this.find(".scroller>table").empty();
		},
		close_ : function(){
			this.hide();
		},
		open_:function(){
			this.show();
		},
		bindToolbarEvent_:function(){
			var that=this;
			this.find(".remove").click(function(){
				that.componentlist("close_");
			});
			this.find(".reload").click(function(){
				that.componentlist("reload_");
			});
		},
		showOrHide_ : function(compRow,component){
			var tb=compRow.closest("table");
			if(component.css("display")=="none"){
				component.css("display","block");
				compRow.find("i").removeClass("fa-eye-slash").addClass("fa-eye");
				component.click();
				tb.find(".selected").removeClass("selected");
				compRow.addClass("selected");
			}else{
				component.css("display","none");
				compRow.find("i").removeClass("fa-eye").addClass("fa-eye-slash");
				tb.find(".selected").removeClass("selected");
				compRow.addClass("selected");
			}
		},
		collapse_:function(component){
			 var cname_icon= component.find(".cname").find("i");
			 var that=this;
			 cname_icon.unbind();
			 var childrenComp;
			 cname_icon.bind("click",
			   function(){
				    childrenComp=that.find("[p_id="+component.attr("c_id")+"]");
				    if($(this).hasClass("fa-caret-down")){
						$(this).removeClass("fa-caret-down");
					    $(this).addClass("fa-caret-right");
					    childrenComp.hide();
					}else{
						$(this).removeClass("fa-caret-right");
						$(this).addClass("fa-caret-down");
					    childrenComp.show();
					}
			   });
		}
    };
	$.fn.componentlist = function(method) {
        if ( methods[method] ) {
            return methods[method].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof method === 'object' || ! method ) {
            return methods.init.apply( this, arguments );
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.componentlist' );
			return this;
        }    
    };
	//私有方法，检测参数是否合法
    function isValid(options) {
        return !options || (options && typeof options === "object") ? true : false;
    }
})(window.jQuery);