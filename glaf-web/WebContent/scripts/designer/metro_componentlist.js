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
				var $caption = $this.find(".caption");
				$caption.append($("<i class=\"fa fa-arrow-circle-right\"></i>"));
				$caption.append($("<span></span>").text(opts.name));
				$caption.append($('<a href="javascript:;" id="changeList" title="DOM树切换" class="btn active"><i class="fa fa-list"></i></a>'));
				// $this.find(".caption").html();
				//获取所有控件列表
                $this.componentlist("load");
                $this.componentlist("initDomTree");
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
						//名称
						var component_name = $component.find("[data-role]").attr("cname") || $component.find("[data-role]").attr("id");
						component_name = $component.find(".preview>input").val() + "(" + component_name + ")";
						nametd.append(component_name);
						nametd.attr("title",component_name);
						
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
								// $("#elem_toolbar").hide();
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
			var zTreeObj = this.data("componentlist_ztree_obj");
			zTreeObj.destroy();
			this.componentlist("loadDomTree_");
		},
		addComponentTreeNode : function($component){
			var that = this;
			var zTreeObj = that.data("componentlist_ztree_obj");
			if(zTreeObj){
				var componentId = $component.attr("id");
				if(componentId.indexOf("_ct") > 0){
					componentId = componentId.substring(0,componentId.indexOf("_ct"));
				}
				var parentNode = null;
				var pcomponentId = $component.closest("[data-role]:not([id='"+$component.attr("id")+"'])").attr("id");
				if(pcomponentId){
					var pcomponent_real_id = pcomponentId;
					if(pcomponent_real_id.indexOf("_ct") > 0){
						pcomponent_real_id = pcomponent_real_id.substring(0,pcomponent_real_id.indexOf("_ct"));
					}
					
					if(pcomponent_real_id.indexOf("col") == 0){
						//父节点为栅格时
						pcomponent_real_id  = pcomponent_real_id +"_column_"+($component.closest(".column").index()+1);
					}
					parentNode = zTreeObj.getNodeByParam("id",pcomponent_real_id,null);	
				}
				var nodes = zTreeObj.addNodes(parentNode, {
					name:$component.attr("cname") || componentId,
					id:componentId,
					title : componentId,
					dataRole : $component.attr("data-role"),
				});
				if(componentId.indexOf("col") == 0){
					//为栅格是，加上下面的column
					var $columns = $component.find(">.view>.row>.column");
					if($columns.length == 0){
						$columns = $component.find(">.view>.portlet>.portlet-body>.row>.column");
					}
					$.each($columns,function(k,column){
						zTreeObj.addNodes(nodes, {
							name:"栅格第"+(k+1)+"列",
							id:componentId+"_column_"+(k+1),
							title : componentId+"_column_"+(k+1),
							columnIndex : k,
							pId : componentId,
						});
					})
				}
			}
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
			
			that.componentlist("addComponentTreeNode",$component);
			
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
			var zTreeObj = this.data("componentlist_ztree_obj");
			zTreeObj.destroy();
			// debugger;
			// that.find("#componentlist_ztree").empty();
		},
		close_ : function(){
			var that = this;
			this.toggleClass("componentlistfloat");
			if(this.hasClass("componentlistfloat")){
				$("body").append(this);
				//控件列表绑定拖动
			    this.draggable(
			    {
			        handle:"div.portlet-title",
			      cursor : "move"
			    });
			}else{
				this.draggable( "destroy" );
				$(".componentlistPanel").append(this);	
			}
			if($.isFunction(resizeQuickPanel)){
				resizeQuickPanel();
			}

			// this.hide();
		},
		open_:function(){
			this.toggleClass("componentlistfloat");
			//控件列表绑定拖动
		    this.draggable(
		    {
		        handle:"div.portlet-title",
		      cursor : "move"
		    });
		    $("body").append(this);
		},
		bindToolbarEvent_:function(){
			var that=this;
			//控件列表绑定拖动
		    this.draggable(
		    {
		        handle:"div.portlet-title",
		      cursor : "move"
		    });
			this.find(".remove").click(function(e){
				 e.stopPropagation();
				that.componentlist("close_");
			});
			this.find(".reload").click(function(){
				that.componentlist("reload_");
			});
			this.find("#changeList").click(function(){
				$(this).toggleClass("active");
				if($(this).hasClass("active")){
					that.find("#componentlist_list_panel").show();
					that.find("#componentlist_ztree_panel").hide();
				}else{
					that.find("#componentlist_ztree_panel").show();
					that.find("#componentlist_list_panel").hide();
				}
			})
		},
		domTreeSetting : {
			data:{
				key:{title:"title"},
				simpleData:{enable:true}
			},
			callback: {
				onClick: function(event, treeId, treeNode){
					$("#"+treeNode.id).click();
				},
				// beforeDrop: function(treeId, treeNodes, targetNode, moveType) {
				// 	//拖拽结束校验，只有栅格列可以拖入，栅格列不可拖拽，控件只能在控件上下移动。
				// 	var treeNode = treeNodes[0];
				// 	var dataRole = treeNode.dataRole;
				// 	if(dataRole){
				// 		//为控件或栅格时
				// 		if(moveType == "inner"){
				// 			//插入操作，只能插入到栅格列中
				// 			return targetNode.dataRole == null ;
				// 		}else{
				// 			//前后插入，只能是同类型的才能前后插入
				// 			return dataRole == targetNode.dataRole;
				// 		}
				// 	}else{
				// 		return false;
				// 	}
				// },
				// onDrop: (event, treeId, treeNodes, targetNode, moveType) {
				//   //拖拽结束事件
				//   exchange(preRow, $component);
				// },
			},
			edit: {
				enable: false,
				showRemoveBtn: false,
				showRenameBtn: false
			},
			view:{
				//自定义按钮
				addDiyDom:function(treeId, treeNode) {
					var aObj = $("#" + treeNode.tId);
					if ($("#eyeBtn_"+treeNode.id).length>0) return;

					var $btn = $('<a href="javascript:;"></a>');
					$btn.attr("id","eyeBtn_"+treeNode.id);
					aObj.append($btn);
					var $component = $("#"+treeNode.id);
					if($component.attr("data-role") && $component.attr("data-role").indexOf("col") == -1){
						$component = $component.closest(".mybox");
					}
					var $compRow = $("#"+treeNode.id + "_ct_tr");
					if($component.css("display")=="none"){
						$btn.append($('<i class="fa fa-eye-slash"></i>'));
					}else{
						$btn.append($('<i class="fa fa-eye"></i>'));
					}
					$btn.click(function(e){
						$(".componentlist").componentlist("showOrHide_",$compRow,$component);
					})
					// var btn = $("#diyBtn_"+treeNode.id);
					// if (btn) btn.bind("click", function(){alert("diy Button for " + treeNode.name);});
				}
			}
		},
		initDomTree : function(){
			var that = this;
			that.componentlist("loadDomTree_");
		},
		loadDomTree_ : function(){
			var that = this;
			var datas = [];
			that.componentlist("createDomTreeData_",datas);
			var zTreeObj = $.fn.zTree.init(that.find("#componentlist_ztree"), methods.domTreeSetting,datas);
			this.data("componentlist_ztree_obj",zTreeObj);
		},
		selecteNode : function(id){
			var that = this;
			var zTreeObj = this.data("componentlist_ztree_obj");
			if(!zTreeObj){
				return;
			}
			var node = zTreeObj.getNodeByParam("id",id,null);
			zTreeObj.selectNode(node);

			var tb = that.find(".scroller>table");
			var trdom=tb.find("[c_id="+id+"_ct]");
			tb.find(".selected").removeClass("selected");
			trdom.addClass("selected");
			// if(node){
			// 	that.find(node.tId).trigger("click");
			// }
		},
		createDomTreeData_ : function(datas){
			var that = this;
			var $doms = $(".designer [data-role]");
			$.each($doms,function(i,item){
				var $pId = $(item).closest("[data-role]:not([id="+$(item).attr("id")+"])");
				var pId = $pId?$pId.attr("id"):"0";
				var itemId = $(item).attr("id");
				var data = {
					name:$(item).attr("cname") || itemId,
					id : itemId,
					title : itemId,
					dataRole : $(item).attr("data-role"),
					// icon : contextPath + "/scripts/designer/images/component/"+ $(item).attr("data-role")+ ".png",
					pId : pId,
					// $component : $(item),
				}
				if($pId.attr("data-role").indexOf("col") > -1){
					data.pId = pId + "_column_"+($(item).closest(".column").index() + 1);
				}
				if($(item).attr("data-role").indexOf("col") > -1){
					//为栅格是，加上下面的column
					var $columns = $(item).find(">.view>.row>.column");
					if($columns.length == 0){
						$columns = $(item).find(">.view>.portlet>.portlet-body>.row>.column");
					}
					$.each($columns,function(k,column){
						var $column = $(column);
						var id = itemId+"_column_"+(k+1);
						$column.attr("id",id);
						var data2 = {
							name:"栅格第"+(k+1)+"列",
							id : id,
							title : id,
							columnIndex : k,
							pId : itemId,
							// $component : $(column),
						}
						datas.push(data2);
					})
					// index() + 1 
				}
				datas.push(data);
			});

			
			// var that = this;
			// $.each($doms,function(i,item){
			// 	var children = [];
			// 	var data = {
			// 		name:$(item).attr("cname") || $(item).attr("id"),
			// 		id : $(item).attr("id"),
			// 		children : children
			// 	}
			// 	datas.push(data);
			// 	//$()
			// 	var $childrenDoms = $(item).find('[data-role]:not([pdata-role="container"]):not([data-role*=col])');
			// 	$.merge($childrenDoms,$(item).find('[pdata-role="container"],[data-role*=col]'));
			// 	that.componentlist("createDomTreeData_",$childrenDoms,children);
			// })
		},
		showOrHide_ : function(compRow,component){
			var tb=compRow.closest("table");
			var $eyebtn = $(this).find("#eyeBtn_"+component.attr("id").replace("_ct",""));
			$eyebtn.empty();

			if(component.css("display")=="none"){
				component.css("display","block");
				compRow.find("i").removeClass("fa-eye-slash").addClass("fa-eye");
				tb.find(".selected").removeClass("selected");
				compRow.addClass("selected");
				$eyebtn.append($('<i class="fa fa-eye"></i>'));
				component.click();
			}else{
				component.css("display","none");
				compRow.find("i").removeClass("fa-eye").addClass("fa-eye-slash");
				tb.find(".selected").removeClass("selected");
				compRow.addClass("selected");
				$eyebtn.append($('<i class="fa fa-eye-slash"></i>'));
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

function _getPageScrollTop() {
	var xScroll, yScroll;
	if (self.pageYOffset) {
		yScroll = self.pageYOffset;
		xScroll = self.pageXOffset;
	} else if (document.documentElement && document.documentElement.scrollTop) { // Explorer 6 Strict
		yScroll = document.documentElement.scrollTop;
		xScroll = document.documentElement.scrollLeft;
	} else if (document.body) {// all other Explorers
		yScroll = document.body.scrollTop;
		xScroll = document.body.scrollLeft;  
	}
	return {top:yScroll,left:xScroll};
}

//闭包限定命名空间
(function ($) {
    //默认参数
    var elem_toolbar_data_ = {
    	"col":{
    		'left':[{
	    		"id":"divideBt",
	    		"class":"divideBt",
	    		"title":"分栏",
	    		"icon":"glyphicon glyphicon-th"
	    	},{
	    		"id":"moveUpBt",
	    		"class":"moveUpBt",
	    		"title":"上移",
	    		"icon":"fa fa-arrow-up font-white"
	    	},{
	    		"id":"moveDownBt",
	    		"class":"moveDownBt",
	    		"title":"下移",
	    		"icon":"fa fa-arrow-down font-white"
	    	},{
	    		"id":"save-as-row",
	    		"class":"save-as-row",
	    		"title":"另存为模板",
	    		"icon":"fa fa-save",
	    	}],
	    	'right':[{
	    		"id":"addup-row",
	    		"class":"addup-row",
	    		"title":"上方插入空行",
	    		"icon":"fa fa-chevron-up"
	    	},{
	    		"id":"adddown-row",
	    		"class":"adddown-row",
	    		"title":"下方插入空行",
	    		"icon":"fa fa-chevron-down"
	    	},{
	    		"id":"copyaddup-row",
	    		"class":"copyaddup-row",
	    		"title":"上方复制插入",
	    		"icon":"glyphicon glyphicon-export"
	    	},{
	    		"id":"copyadddown-row",
	    		"class":"copyadddown-row",
	    		"title":"下方复制插入",
	    		"icon":"glyphicon glyphicon-import"
	    	},{
	    		"id":"cut-row",
	    		"class":"cut-row",
	    		"title":"剪切",
	    		"icon":"fa fa-cut"
	    	},{
	    		"id":"copy-row",
	    		"class":"copy-row",
	    		"title":"复制",
	    		"icon":"fa fa-copy"
	    	},
	    	// {
	    	// 	"id":"paste-row",
	    	// 	"class":"paste-row",
	    	// 	"title":"粘贴",
	    	// 	"icon":"fa fa-paste",
	    	// },
	    	{
	    		"id":"myremove",
	    		"class":"myremove",
	    		"title":"删除",
	    		"icon":"fa fa-trash font-white"
	    	}]
	    },
    	"col_column":{
    		'left':[{
	    		"id":"leftAddBt",
	    		"class":"leftAddBt",
	    		"title":"左侧插入列",
	    		"icon":"fa fa-hand-o-left"
	    	},{
	    		"id":"rightAddBt",
	    		"class":"rightAddBt",
	    		"title":"右侧插入列",
	    		"icon":"fa fa-hand-o-right"
	    	},{
	    		"id":"moveLeftBt",
	    		"class":"moveLeftBt",
	    		"title":"向左移",
	    		"icon":"glyphicon glyphicon-chevron-left"
	    	},{
	    		"id":"moveRightBt",
	    		"class":"moveRightBt",
	    		"title":"向右移",
	    		"icon":"glyphicon glyphicon-chevron-right"
	    	},{
	    		"id":"column-save-as",
	    		"class":"column-save-as",
	    		"title":"另存为模板",
	    		"icon":"fa fa-save",
	    	},{
	    		"id":"col-align-left",
	    		"class":"col-align-left",
	    		"title":"居左",
	    		"icon":"fa fa-align-left"
	    	},{
	    		"id":"col-align-center",
	    		"class":"col-align-center",
	    		"title":"居中",
	    		"icon":"fa fa-align-center"
	    	},{
	    		"id":"col-align-right",
	    		"class":"col-align-right",
	    		"title":"居右",
	    		"icon":"fa fa-align-right"
	    	}],
	    	'right':[{
	    		"id":"column-cut-row",
	    		"class":"column-cut-row",
	    		"title":"剪切",
	    		"icon":"fa fa-cut"
	    	},{
	    		"id":"column-copy-row",
	    		"class":"column-copy-row",
	    		"title":"复制",
	    		"icon":"fa fa-copy"
	    	},{
	    		"id":"column-paste-row",
	    		"class":"column-paste-row",
	    		"title":"粘贴",
	    		"icon":"fa fa-paste",
	    	},{
	    		"id":"column-empty-row",
	    		"class":"column-empty-row",
	    		"title":"清空",
	    		"icon":"fa fa-eraser",
	    	},{
	    		"id":"column-custom-row",
	    		"class":"column-custom-row",
	    		"title":"自定义",
	    		"icon":"fa fa-cogs",
	    	}]
    	},
    	"mybox":{
    		'left':[{
	    		"id":"moveUpBt_mybox",
	    		"class":"moveUpBt_mybox",
	    		"title":"上移",
	    		"icon":"fa fa-arrow-up font-white"
	    	},{
	    		"id":"moveDownBt_mybox",
	    		"class":"moveDownBt_mybox",
	    		"title":"下移",
	    		"icon":"fa fa-arrow-down font-white"
	    	},{
	    		"id":"elem-save-as",
	    		"class":"elem-save-as",
	    		"title":"另存为模板",
	    		"icon":"fa fa-save",
	    	}],
	    	'right':[{
	    		"id":"elemcopyaddup-row",
	    		"class":"elemcopyaddup-row",
	    		"title":"上方复制插入",
	    		"icon":"glyphicon glyphicon-export"
	    	},{
	    		"id":"elemcopyadddown-row",
	    		"class":"elemcopyadddown-row",
	    		"title":"下方复制插入",
	    		"icon":"glyphicon glyphicon-import"
	    	},{
	    		"id":"elemcut-row",
	    		"class":"elemcut-row",
	    		"title":"剪切",
	    		"icon":"fa fa-cut"
	    	},{
	    		"id":"elemcopy-row",
	    		"class":"elemcopy-row",
	    		"title":"复制",
	    		"icon":"fa fa-copy"
	    	},
	    	// {
	    	// 	"id":"paste-row",
	    	// 	"class":"paste-row",
	    	// 	"title":"粘贴",
	    	// 	"icon":"fa fa-paste",
	    	// },
	    	{
	    		"id":"myremove",
	    		"class":"myremove",
	    		"title":"删除",
	    		"icon":"fa fa-trash font-white"
	    	}]
    	},
    	"layout":{
    		'left':[{
	    		"id":"moveUpBt_mybox",
	    		"class":"moveUpBt_mybox",
	    		"title":"上移",
	    		"icon":"fa fa-arrow-up font-white"
	    	},{
	    		"id":"moveDownBt_mybox",
	    		"class":"moveDownBt_mybox",
	    		"title":"下移",
	    		"icon":"fa fa-arrow-down font-white"
	    	}],
	    	'right':[{
	    		"id":"copyadd-row",
	    		"class":"copyadd-row",
	    		"title":"上方复制插入",
	    		"icon":"glyphicon glyphicon-export"
	    	},{
	    		"id":"copyadddown-row",
	    		"class":"copyadddown-row",
	    		"title":"下方复制插入",
	    		"icon":"glyphicon glyphicon-import"
	    	},{
	    		"id":"elemcut-row",
	    		"class":"elemcut-row",
	    		"title":"剪切",
	    		"icon":"fa fa-cut"
	    	},{
	    		"id":"elemcopy-row",
	    		"class":"elemcopy-row",
	    		"title":"复制",
	    		"icon":"fa fa-copy"
	    	},
	    	{
	    		"id":"layoutremove",
	    		"class":"myremove",
	    		"title":"删除",
	    		"icon":"fa fa-trash font-white"
	    	}]
    	}
    }
    var methods = {
		init:function (){
			var $elem_toolbar = $(this);
	  		$elem_toolbar.elem_toolbar("bind_event");
	  		//监听滚动事件
	  		window.onscroll= function(){
	  			$elem_toolbar.elem_toolbar("resize_elem_toolbar");
	  		}
		},
		reload_:function(type){
			var $elem_toolbar = $(this);


			var $currentComponent = getCurrentComponent();
			if(type == 'mybox'){
				$currentComponent = $currentComponent.closest(".mybox");
			}
			$elem_toolbar.empty();
			var datas = elem_toolbar_data_[type];
			var $left_btn_group = $('<div class="btn-group" style="border-right:1px #fff;"></div>');
			$.each(datas.left,function(i,item){
				var $btn = $('<button type="button" class="btn btn-icon-only c-btn-border-1x c-btn-white"></button>');
				$btn.attr({
					id:item.id,
					title:item.title
				});
				$btn.addClass(item.class);
				$btn.append('<i class="'+item.icon+'"></i>');

				if(item.id == 'moveUpBt' || item.id == 'moveUpBt_mybox'){
					var preRow = $elem_toolbar.elem_toolbar("getPrevDom",$currentComponent);
					if(!preRow || !preRow[0]){
						$btn.attr("disabled","disabled");
					}
				}
				if(item.id == 'moveDownBt' || item.id == 'moveDownBt_mybox'){
					var nextRow = $elem_toolbar.elem_toolbar("getNextDom",$currentComponent);
					if(!nextRow || !nextRow[0]){
						$btn.attr("disabled","disabled");
					}
				}
				$left_btn_group.append($btn);
			})
			var $right_btn_group = $('<div class="btn-group" style="border-right:1px #fff;float:right;"></div>');
			$.each(datas.right,function(i,item){
				var $btn = $('<button type="button" class="btn btn-icon-only  c-btn-border-1x c-btn-white"></button>');
				$btn.attr({
					id:item.id,
					title:item.title
				});
				$btn.addClass(item.class);
				$btn.append('<i class="'+item.icon+'"></i>');
				$right_btn_group.append($btn);
			})


			$right_btn_group.append('<button type="button" class="removeBtn btn btn-icon-only c-btn-border-1x c-btn-white"><i class="fa fa-remove"></i></button>')
			

			$elem_toolbar.width((datas.left.length + datas.right.length + 1)*34);

			$elem_toolbar.append($right_btn_group);
			$elem_toolbar.append($left_btn_group);
			if(!clipboard){
				//没有任何剪切信息，修改粘贴按钮为不可用
				$elem_toolbar.find(".column-paste-row").attr("disabled",true);
			}
		},
		
		//重定位工具栏
		resize_elem_toolbar: function(){
			var that = this;
			var $component;
			if(!currentComponent){
				//无选中控件时
				return;
			}
			if(currentComponent.hasClass("column")){
	        	//栅格的列，直接放在上面
	        	$component = currentComponent;
	        }else if(currentComponent.attr("data-role") && (currentComponent.attr("data-role").indexOf("col")>-1 || currentComponent.attr("data-role").indexOf("aspectlayout")>-1)){
	        	//栅格直接在栅格上面
	        	$component = currentComponent;
	        }else{
	        	//非栅格，放在mybox的上面
	        	$component = currentComponent.closest(".mybox");
	        }
	        var offset = _getPageScrollTop();
	        var top = $component.offset().top - offset.top - 40;
	        var left = $component.offset().left + $component.width() - $("#elem_toolbar").width() - offset.left+5;

	        // var top = $component.position().top-document.body.scrollTop-40;
		    // var left = $component.position().left + $component.width() - $("#elem_toolbar").width() -document.body.scrollLeft+5;
		    if(top < 40){
		        top += $component.height()+40;
		    }
		    var min_left = $(".designer").offset().left;
		    var max_left = $(".designer").width()+ $(".designer").offset().left - $("#elem_toolbar").width()
		    if(left > max_left){
		    	left = max_left;
		    }
		    if(left < min_left){
		    	left = min_left;
		    }
		    $("#elem_toolbar").css("top",top+"px");
		    $("#elem_toolbar").css("left",left+"px");

	        // $("#elem_toolbar").css("top",$component.offset().top-40+"px");
    		// $("#elem_toolbar").css("left",$component.offset().left+5+"px");
		},
		getPrevDom: function($component){
			return $component.prev("[data-role],.mybox");
		},
		getNextDom: function($component){
			return $component.next("[data-role],.mybox");
		},
		bind_event: function(){
			var $elem_toolbar = $(this);

			$elem_toolbar.on("click",".removeBtn",function(e){
			 	//阻止冒泡
    			e.stopPropagation();
    			$elem_toolbar.hide();
			})

			//栅格分栏操作
			$elem_toolbar.on("click",".divideBt",function(e){
				//获取行
			    var row = currentComponent.find(".view:first").find(".row:first");
			    //获取当前栅格系统
			    var gridsys = $(row.children()[0]).attr("class").match((/col\-(md|mmd|24md)\-\d+/))[0];
			    if (gridsys.indexOf("col-mmd-") > -1) {
			        gridsys = "48";
			    } else if (gridsys.indexOf("col-24md-") > -1) {
			        gridsys = "24";
			    } else {
			        gridsys = "12";
			    }
			    //设置选中栅格系统
			    $('#divideForm input[name="gridsys"][value="' + gridsys + '"]').attr("checked", true);
			    $("#groupNumber").attr("max", gridsys);
			    $("#groupNumber").trigger("touchspin.updatesettings", {max: parseInt(gridsys)});
			    //获取当前列数
			    var currGroups = row.children().length;
			    //栅格样式
			    var currClass;
			    //组栅格数方案
			    var groupdivide;
			    //初始化分组数
			    $("#groupNumber").val(currGroups);
			    $.each(row.children(), function (i, item) {
			        currClass = $(item).attr("class").match((/col\-(md|mmd|24md)\-\d+/))[0];
			        if (i == 0)
			            groupdivide = currClass.match(/\d+$/)[0];
			        else
			            groupdivide += "," + currClass.match(/\d+$/)[0];
			    });
			    //初始化组栅格数
			    $("#divideNumber").val(groupdivide);
			    $('#divideModal').modal('show');
			})
			//上移
			$elem_toolbar.on("click",".moveUpBt,.moveUpBt_mybox",function(e){
				//行位置对换
			    //获取前面的行
			    var $component = currentComponent;
			    if ($(this).hasClass('moveUpBt_mybox')) {
			    	$component = currentComponent.closest(".mybox");
			    }
			    var preRow = $elem_toolbar.elem_toolbar("getPrevDom",$component);
			    if (preRow && preRow.length > 0)
			        exchange(preRow, $component);
			   	
			   	var currentComponentId = currentComponent.attr("id")
			   	if(currentComponentId){
			   		currentComponent = $("#"+currentComponentId);
			   	};

			    $elem_toolbar.elem_toolbar("resize_elem_toolbar");
			});
			//下移
			$elem_toolbar.on("click",".moveDownBt,.moveDownBt_mybox",function(e){
			    //行位置对换
			    //获取后面的行
			    var $component = currentComponent;
			    if ($(this).hasClass('moveDownBt_mybox')) {
			    	$component = currentComponent.closest(".mybox");
			    }
			    var nextRow = $elem_toolbar.elem_toolbar("getNextDom",$component);
			    if (nextRow && nextRow.length > 0)
			        exchange(nextRow, $component);
			    
			    var currentComponentId = currentComponent.attr("id")
			   	if(currentComponentId){
			   		currentComponent = $("#"+currentComponentId);
			   	};

			    $elem_toolbar.elem_toolbar("resize_elem_toolbar");
			});
			//另存为模板
			$elem_toolbar.on("click",".save-as-row,.elem-save-as,.column-save-as",function(e){
			    //行另存为
			    var $component = currentComponent;
			    if ($(this).hasClass('save-as-row')) {
			        // currentComponent = currentComponent.closest(".lyrow");
			        $component = currentComponent.closest(".lyrow");
			    }
			    //元素另存为
			    else if ($(this).hasClass('elem-save-as')) {
			        $component = currentComponent;
			    }
			    //列另存为
			    else if ($(this).hasClass('column-save-as')) {
			    	$component = currentComponent;
			        // currentComponent = $(this).closest(".column");
			    }
			    //设置iframe src
			    //var url=contextPath+"/mx/page/designer/metro/template?action=join&fn=getCurrentComponent";
			    var code = convertComponentHtml($component);
			    $("#codeEditModal").find("iframe")[0].contentWindow.changeTab("portlet_tab1");
			    $("#codeEditModal").find("iframe")[0].contentWindow.setSummernoteCode(code);
			    $('#codeEditModal').modal('show');
			});
			//上方插入空行
			$elem_toolbar.on("click",".addup-row",function(e){
			    //新建行
			    var newRow = currentComponent.clone();
			    //重新生成ID
			    var dtStr = new Date().getTime();
			    var nId = newRow.attr("data-role") + "_" + dtStr;
			    newRow.attr("id", nId);
			    //清空列内容
			    newRow.find(".column").find(".configuration").nextAll().remove();
			    newRow.find(".column").find(".mybox").remove();
			    currentComponent.before(newRow);
			    initContainer();
			    draggEventBind();
			    $elem_toolbar.elem_toolbar("resize_elem_toolbar");
			});
			//下方插入空行
			$elem_toolbar.on("click",".adddown-row",function(e){
			    //新建行
			    var newRow = currentComponent.clone();
			    //重新生成ID
			    var dtStr = new Date().getTime();
			    var nId = newRow.attr("data-role") + "_" + dtStr;
			    newRow.attr("id", nId);
			    //清空列内容
			    newRow.find(".column").find(".configuration").nextAll().remove();
			    newRow.find(".column").find(".mybox").remove();
			    currentComponent.after(newRow);
			    initContainer();
			    draggEventBind();
			    $elem_toolbar.elem_toolbar("resize_elem_toolbar");
			});
			//上方复制插入
			$elem_toolbar.on("click",".copyaddup-row",function(e){
			    var newRow = copyRow(currentComponent);
			    //子控件重新生成ID
			    var nElem = generatorId(newRow);
			    currentComponent.before(nElem);
			    initContainer();
			    draggEventBind();
			    $elem_toolbar.elem_toolbar("resize_elem_toolbar");
			});
			//上方复制插入
			$elem_toolbar.on("click",".copyadddown-row",function(e){
			    var newRow = copyRow(currentComponent);
			    //子控件重新生成ID
			    var nElem = generatorId(newRow);
			    currentComponent.after(nElem);
			    initContainer();
			    draggEventBind();
			    $elem_toolbar.elem_toolbar("resize_elem_toolbar");
			});

			//剪切
			$elem_toolbar.on("click",".cut-row,.elemcut-row",function(e){
			    var $component = currentComponent;
			    if ($(this).hasClass('cut-row')) {
			        // currentComponent = currentComponent.closest(".lyrow");
			        // currentComponent = currentComponent.closest(".lyrow");
			    }else if($(this).hasClass('elemcut-row')){
			    	$component = currentComponent.closest(".mybox");
			    }
			    //新建行
			    clipboard = $component.clone();
			    $component.remove();
				copyOrCut="cut";
				$elem_toolbar.hide();
			});

			//复制
			$elem_toolbar.on("click",".copy-row,.elemcopy-row,.column-copy-row",function(e){
			    var $component = currentComponent;
			    if ($(this).hasClass('copy-row')) {
			        // currentComponent = currentComponent.closest(".lyrow");
			        // currentComponent = currentComponent.closest(".lyrow");
			    }else if($(this).hasClass('elemcopy-row')){
			    	$component = currentComponent.closest(".mybox");
			    }else if($(this).hasClass('column-copy-row')){
			    	$component = currentComponent;
			    }
			    //新建行
			    clipboard = $component.clone();
				copyOrCut="copy";
				$elem_toolbar.find(".paste-row").attr("disabled",true);
			});

			//粘贴
			// $elem_toolbar.on("click",".paste-row",function(e){
			//     //验证当前剪贴板内容是否为行对象
			//     if (clipboard == undefined || clipboard == null) {
			//         $.alert(1, "剪贴板为空");
			//         return;
			//     }
			//     if (!clipboard.hasClass("lyrow")) {
			//         $.alert(1, "剪贴板中对象为非行对象!");
			//         return;
			//     }
			// 	currentComponent = $(this).closest(".lyrow");
			// 	if(copyOrCut=="copy"){
			// 		var newRow = copyRow(clipboard);
			// 		//子控件重新生成ID
			// 		var nElem = generatorId(newRow);
			// 		currentComponent.replaceWith(nElem);
			//     }
			// 	else{
			// 		currentComponent.replaceWith(clipboard.clone());
			// 	}
			//     initContainer();
			//     initdraggable();
			// });
			//删除
			$elem_toolbar.on("click",".myremove",function(e){
		        //阻止冒泡
		        e.stopPropagation();
		        if(currentComponent.attr("data-role").indexOf("col")>-1 || currentComponent.attr("data-role").indexOf("aspectlayout")>-1){
		        	currentComponent.remove();
		        }else{
		        	currentComponent.closest(".mybox").remove();
		        }
		        $("#elem_toolbar").hide();
		        $(".componentlist").componentlist("reload_");
		    });

			//------------------------控件的----------------
			//上方复制插入
			$elem_toolbar.on("click",".elemcopyaddup-row",function(e){
		        var $component = currentComponent.closest(".mybox");
			    //新建行
			    var newElem = $component.clone();
			    newElem.removeClass("shine_red");
			    newElem.find(".shine_red").removeClass("shine_red");
			    if (newElem.attr("data-role") != undefined) {
			        //重新生成ID
			        var dtStr = new Date().getTime();
			        var nId = newElem.attr("data-role") + "_" + dtStr;
			        newElem.attr("id", nId);
			    }
			    //子控件重新生成ID
			    var nElem = generatorId(newElem);
			    $component.before(nElem);
			    initContainer();
			    initdraggable();
			    $elem_toolbar.elem_toolbar("resize_elem_toolbar");
		    });
			//下方复制插入
		    $elem_toolbar.on("click",".elemcopyadddown-row",function(e){
		        var $component = currentComponent.closest(".mybox");
			    //新建元素
			    var newElem = $component.clone();
			    newElem.removeClass("shine_red");
			    newElem.find(".shine_red").removeClass("shine_red");
			    if (newElem.attr("data-role") != undefined) {
			        //重新生成ID
			        var dtStr = new Date().getTime();
			        var nId = newElem.attr("data-role") + "_" + dtStr;
			        newElem.attr("id", nId);
			    }
			    //子控件重新生成ID
			    var nElem = generatorId(newElem);
			    $component.after(nElem);
			    initContainer();
			    initdraggable();
			    $elem_toolbar.elem_toolbar("resize_elem_toolbar");
		    });

		    //-----------------------栅格列
		    //左侧插入列
		    $elem_toolbar.on("click",".leftAddBt",function(e){
		        // var currentColumn = $(this).closest(".column");
		        var currentColumn = currentComponent;
			    //获取列栅格样式
			    var currClass = currentColumn.attr("class").match((/col\-(md|mmd|24md)\-\d+/))[0];
			    //获取列栅格数
			    var currCells = currClass.match((/\d+$/))[0];
			    if (currCells == 1) {
			        return;
			    }
			    //重新计算列栅格数
			    var newCells = parseInt(currCells / 2);
			    if (newCells == 1) {
			        currentColumn.addClass("sigcol");
			    }
			    var preColumnClass = getGridSysByColumnClass(currClass);
			    var newClass = preColumnClass + newCells;
			    currentColumn.removeClass(currClass);
			    currentColumn.addClass(newClass);
			    newClass = preColumnClass + (currCells - newCells);
			    var newColumn = $("<div class=\"" + newClass + " column ui-sortable\"></div>");
			    newColumn.append($("#colconfigTemplate").html());
			    //如果列栅格数为1则隐藏左右插入列操作按钮
			    if (currCells - newCells == 1) {
			        newColumn.addClass("sigcol");
			    }
			    currentColumn.before(newColumn);
			    initContainer();
			    draggEventBind();
		    });
		    //右侧插入列
		    $elem_toolbar.on("click",".rightAddBt",function(e){
		        // var currentColumn = $(this).closest(".column");
			    var currentColumn = currentComponent;
			    //获取列栅格样式
			    var currClass = currentColumn.attr("class").match((/col\-(md|mmd|24md)\-\d+/))[0];
			    //获取列栅格数
			    var currCells = currClass.match((/\d+$/))[0];
			    if (currCells == 1) {
			        return;
			    }
			    //重新计算列栅格数
			    var newCells = parseInt(currCells / 2);
			    if (newCells == 1) {
			        currentColumn.addClass("sigcol");
			    }
			    var preColumnClass = getGridSysByColumnClass(currClass);
			    var newClass = preColumnClass + newCells;
			    currentColumn.removeClass(currClass);
			    currentColumn.addClass(newClass);
			    newClass = preColumnClass + (currCells - newCells);
			    var newColumn = $("<div class=\"" + newClass + " column ui-sortable\"></div>");
			    newColumn.append($("#colconfigTemplate").html());
			    //如果列栅格数为1则隐藏左右插入列操作按钮
			    if (currCells - newCells == 1) {
			        newColumn.addClass("sigcol");
			    }
			    currentColumn.after(newColumn);
			    initContainer();
			    draggEventBind();
		    });
		    //向左移
		    $elem_toolbar.on("click",".moveLeftBt",function(e){
		        // var currentColumn = $(this).closest(".column");
			    //行位置对换
			    //获取前面的行
			    var preColumn = currentColumn.prev();
			    if (preColumn && preColumn.length > 0)
			        exchange(preColumn, currentColumn);
		    });
		    //向右移
		    $elem_toolbar.on("click",".moveRightBt",function(e){
		        // var currentColumn = $(this).closest(".column");
			    //行位置对换
			    //获取后面的行
			    var nextColumn = currentColumn.next();
			    if (nextColumn && nextColumn.length > 0)
			        exchange(nextColumn, currentColumn);
		    });
		    //居左
		    $elem_toolbar.on("click",".col-align-left",function(e){
			    // currentComponent = $(this).closest(".column");
			    currentComponent.addClass("text-left");
		        currentComponent.removeClass("text-right");
		        currentComponent.removeClass("text-center");
		    });
		    //居中
		    $elem_toolbar.on("click",".col-align-center",function(e){
			    // currentComponent = $(this).closest(".column");
			    currentComponent.addClass("text-center");
		        currentComponent.removeClass("text-left");
		        currentComponent.removeClass("text-right");
		    });
		    //居右
		    $elem_toolbar.on("click",".col-align-right",function(e){
			    // currentComponent = $(this).closest(".column");
			    currentComponent.addClass("text-right");
		        currentComponent.removeClass("text-left");
		        currentComponent.removeClass("text-center");
		    });
		    //剪切
			$elem_toolbar.on("click",".column-cut-row",function(e){
			    // currentComponent = $(this).closest(".column");
			    clipboard = currentComponent.clone();
			    currentComponent.find(".mybox:first").remove();
			});
			//粘贴
			$elem_toolbar.on("click",".column-paste-row",function(e){
			    if (clipboard == undefined || clipboard == null) {
			        $.alert(1, "剪贴板为空");
			        return;
			    }
					//新建列元素
				var newCol = clipboard.clone();
				var nElem;
				if(copyOrCut=="copy"){
					//重新生成ID
					var dtStr = new Date().getTime();
					if (newCol.attr("data-role") != undefined) {
						var nId = newCol.attr("data-role") + "_" + dtStr;
						newCol.attr("id", nId);
					}
					//子控件重新生成ID
					nElem = generatorId(newCol);
				}else{
					nElem=clipboard.clone();
				}
			    // currentComponent = $(this).closest(".column");
			    //检测是否是复制的列对象
			    if (clipboard.hasClass("column")) {
			        currentComponent.empty();
			        currentComponent.append(nElem.html());
			    } else {
			    	if(currentComponent.find(".configuration:first")[0]){
			    		currentComponent.find(".configuration:first").nextAll().remove();
			        	currentComponent.find(".configuration:first").after(nElem);	
			    	}else{
			    		currentComponent.children(":not(button)");
			        	currentComponent.append(nElem);	
			    	}
			    }
			    initContainer();
			    initdraggable();
			});
			//清空
			$elem_toolbar.on("click",".column-empty-row",function(e){
			    // currentComponent = $(this).closest(".column");
    			// currentComponent.find(".configuration").nextAll().remove();
    			currentComponent.empty();
			});
			//自定义
			$elem_toolbar.on("click",".column-custom-row",function(e){
			    // currentComponent = $(this).closest(".column");
			    var htmlContent = convertComponentHtml(currentComponent);

			    var $htmlContent = $("<div>").append(htmlContent);
			    $.each($htmlContent.find("[data-role]"), function (i, o) {  //默认有很多个data-role  做循环
			        //格式化内容
			        var tmp = formatComponent(o);
			        var ele = getHandleComponent(o);
			        if (ele.attr("id") == $(tmp).attr("id") || !ele.attr("id")) {
			            $(ele).after(tmp).remove();
			        }
			    });

			    openCustomEditor($htmlContent.html());
			});
		},
    };
	$.fn.elem_toolbar = function(method) {
        if ( methods[method] ) {
            return methods[method].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof method === 'object' || ! method ) {
            return methods.init.apply( this, arguments );
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.elem_toolbar' );
			return this;
        }    
    };
})(window.jQuery);