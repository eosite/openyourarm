/**
 * 
 */
$(function() {
	 initTouchSpin();
	 initColorPicker();
	 //initSlider();
	 initRangeSlider();
	 $('.bs-select').selectpicker({
            iconBase: 'fa',
            tickIcon: 'fa-check'
        });
});


function initSideBar(){
	 initTouchSpin();
	 initColorPicker();
	 //initSlider();
	 initRangeSlider();
}
//颜色拾取器
function  colorPickerEx(item){
	 $(item).minicolors({
                control: $(item).attr('data-control') || 'hue',
                defaultValue: $(item).attr('data-defaultValue') || '',
                inline: $(item).attr('data-inline') === 'true',
                letterCase: $(item).attr('data-letterCase') || 'lowercase',
                opacity: $(item).attr('data-opacity'),
                position: $(item).attr('data-position') || 'bottom left',
                change: function(hex, opacity) {
                    if (!hex) return;
                    if (opacity) hex += ', ' + opacity;
                    if (typeof console === 'object') {
                        console.log(hex);
                    }
                },
                theme: 'bootstrap'
            });
}
//初始化颜色拾取器
function initColorPicker(){
	 $(".colorpicker").each(
			function(i,item){
	        colorPickerEx(item)
	  });
}
//数值增量输入框
function touchSpinEx(item){
	 var prefix=$(item).attr("prefix");
	 var postfix=$(item).attr("postfix");
	 var min=$(item).attr("min");
	 var max=$(item).attr("max");
	 $(item).TouchSpin({
         verticalbuttons: true,
         postfix: postfix?postfix:'',
         boostat: 5,
         step:1,
         prefix:prefix?prefix:'',
         "min":min,
         "max":max
     });
	 //if(min)
	 //$(item).trigger("touchspin.updatesettings", {"min": min});
	 //if(max)
	// $(item).trigger("touchspin.updatesettings", {"max": max});
}
//初始化数值增量输入
function initTouchSpin(){
		 $(".spaceTouchSpin").each(
			function(i,item){
				 var min=$(item).attr("min");
				 min?min:$(item).attr("min",-100);
				 var max=$(item).attr("max");
				 max?max:$(item).attr("max",100);
				 touchSpinEx(item);
			 });
	
	 $(".spaceTouchSpin").parent().css("width","75px");
	 $(".spaceTouchSpin").parent().css("float","left");
	 $(".spaceTouchSpin").parent().css("margin-top","2px");
	 $(".sizeTouchSpin").each(
		function(i,item){
			 var min=$(item).attr("min");
			 min?min:$(item).attr("min",0);
			 var max=$(item).attr("max");
			 max?max:$(item).attr("max",1000);
			 touchSpinEx(item);
		 });
	 $(".sizeTouchSpin").parent().css("width","70px");
	 $(".sizeTouchSpin").parent().css("float","left");
	 $(".sizeTouchSpin").parent().css("margin-top","2px");
}
function  initSlider(){
	$(".noUi-success").each(
			function(i,item){
				createSlider(item);
			 });
}
    var createSlider= function(item) {
        noUiSlider.create(item, {
            start: [0],
            connect: false,
            range: {
                'min': 0,
                'max': 100
            },
            pips: {
                mode: 'values',
                values: [0, 100],
                density: 25
            }
        });
		var forinput=$(item).attr("forinput");
		 var inputNumber = document.getElementById(forinput);
		 item.noUiSlider.on('update', function( value) {
                inputNumber.value = Math.round(value);
        });
		 inputNumber.addEventListener('change', function(){
            item.noUiSlider.set([this.value]);
        });
    }
	function initRangeSlider(){
		$(".rangeSlider").each(
			function(i,item){
				createRangeSlider(item);
			 });
	}
	function  createRangeSlider(item){
	$(item).ionRangeSlider({
            grid: true,
            min: 0,
            max: 100,
            prefix: ""
        });
	} 
	
/**
* 图标库回调函数
*/
function iconCallBack(className){//this对象为隐藏域
	$(this).val(className);
	$(this).closest("div.input-group").find("#icon").attr("class","").addClass(className);
	$(this).closest("div.input-group").find("#iconName").html(className);
	
	$(this).click();
}
function selectIconPage(o){
	var $this = $(o);
	BootstrapDialog.show({
		title : '选择图标',
		size : BootstrapDialog.SIZE_WIDE,
		message : "<iframe id='font_icon'  width='100%' height='600' src='"+contextPath+"/scripts/plugins/bootstrap/textbox/font_icons.html?fn=iconCallBack&targetId="+$this.closest(".input-group").find("input[type='hidden']").attr("id")+"' frameborder='no' scrolling='yes'></iframe>",
		css : {
			width : $(window).width() * 0.6
		},
		modal : false //遮蔽层
	});
}
/**
 * 背景图回调函数
 */
function imageCallBack(url){//this为隐藏域对象
	$(this).val(url);
	$(this).closest("div.input-group").find("#backgroundImage1").val(url);
	$(this).click();
}
/**
 * 静态数据回调函数
 */
function staticDataCallBack(data){//this为隐藏域对象
	// $(this).val(data);
	// $(this).closest("div.input-group").find("#staticData1").val(data);
	// $(this).click();

	var $component = window.currentComponent;
    if($component[0]){
        var dataRole = $component.attr("data-role");
        var ctrImpl = "setStaticData";
        ctrImpl = dataRole!=""?eval(dataRole+"_designer."+ctrImpl):eval(ctrImpl);
        try{
            ctrImpl($component,data);
        }catch(e){
            console.log("无该模拟数据方法！");
        }
    }
}
/**
 * 静态数据
 */
function setStaticData(o,type){
	var $this = $(o);
	var componentId = currentComponent[0].id;
	var pageId = id;
	var dialog = BootstrapDialog.show({
		title : '选择图标',
		size : BootstrapDialog.SIZE_WIDE,
		message : "<iframe id='staticDataIframe'  width='100%' height='600' src='"+contextPath+"/mx/form/defined/ex/creatStaticData?fn=staticDataCallBack&targetId="+$this.closest(".input-group").find("input[type='hidden']").attr("id")+"&type="+type+"&componentId="+componentId+"&pageId="+pageId+"' frameborder='no' scrolling='yes'></iframe>",
		css : {
			width : $(window).width() * 0.6
		},
		draggable : true,
		modal : false //遮蔽层
	});
	
}
function imagePage(o){
	var $this = $(o);
	BootstrapDialog.show({
		title : '选择图片',
		size : BootstrapDialog.SIZE_WIDE,
		message : "<iframe id='imageIframe'  width='100%' height='600' src='"+contextPath+"/mx/form/defined/ex/selectImage?fn=imageCallBack&targetId="+$this.closest(".input-group").find("input[type='hidden']").attr("id")+"' frameborder='no' scrolling='yes'></iframe>",
		css : {
			width : $(window).width() * 0.6
		},
		modal : false //遮蔽层
	});
}
(function($) {
	//var prop={},parentProp={},parentDom,data={},bodyArea,level;
	$.Toolbox = function(target,options,params,callback) {
		if (params.type==undefined||params.type=="setting"||params.type=="animation") {
			var dataRole=currentComponent.attr("data-role");
			if(params.dataRole&&params.dataRole!=""){
				dataRole=params.dataRole;
			}
			var pdataRole=currentComponent.attr("pdata-role");
			if(dataRole.indexOf("col")>-1){
				dataRole="col";
			}
			//获取配置项
			var jsonPath=contextPath+"/scripts/designer/components/json/"+dataRole+".json";
			if(params.type&&params.type=="animation"){
				pdataRole="";
				jsonPath=contextPath+"/service/designer/animation/setting";
			}
			
			var nodes={};
			$.ajax({
						url:jsonPath,
						dataType:"json",
						async: false,
						success:function(jsonRule){
							if(jsonRule)
                            nodes=jsonRule;
						},
						error: function (xhr, ts) {
							console.log('加载控件配置项失败！');
						}
					});
				if(pdataRole!=undefined&&pdataRole!=""){
					//获取父配置项
					var pjsonPath=contextPath+"/scripts/designer/components/json/"+pdataRole+".json";
					$.ajax({
						url:pjsonPath,
						dataType:"json",
						async: false,
						success:function(data){
							if(data)
                            nodes= $.extend({}, nodes, data); 
						},
						error: function (xhr, ts) {
							console.log('加载控件配置项失败！');
						}
					});
					}
					$.Toolbox.prototype.data = nodes;
					if(nodes.propertyPackages&&nodes.propertyPackages.length>0){
					   $.Toolbox.prototype._init(target);
					}
					if(callback&&callback!=null&&callback!=""){
						var funcs=callback.split(",");
						var func;
						$.each(funcs,function(i,item){
							func = eval(item);
							func();
						});
					}
				
		}
		
		else if(params.type&&params.type=="template"){
			$.Toolbox.prototype._initTemplate(target);
		}
	};
	$.Toolbox.prototype = {
		prop : {},
		parentProp : {},
		parentDom : null,
		data : null,
		bodyArea : null,
		level : null,
		_init : function(target) {
			target.attr("id", target.attr("id"));
			target.css("border", "0px");
			var that=this;
			$.each(this.data.propertyPackages, function(i, obj) {
				this.prop = obj;
				var aHtml=$("<a></a>");
				//展开
				var expland=obj.expland;
				aHtml.addClass("list-toggle-container");
				aHtml.attr("data-toggle","collapse");
				aHtml.attr("href","#"+obj.id+"-prop");
				if(expland)
				{
					aHtml.attr("aria-expanded",expland);
					if(expland=="false"){
						aHtml.addClass("collapsed");
					}
				}
				var  divHtml=$("<div></div>");
				divHtml.addClass("list-toggle uppercase");
				divHtml.append(obj.title);
				var  spanHtml=$("<span></span>");
				spanHtml.addClass("pull-right arrow");
				divHtml.append(spanHtml);
				aHtml.append(divHtml);
				target.append(aHtml);
				//获取栏目属性
				var props = obj.properties;
				if (props) {
					that.parentProp = null;
					var propDivHtml=$("<div></div>");
					propDivHtml.attr("id",obj.id+"-prop");
					propDivHtml.addClass("panel-collapse");
					propDivHtml.addClass("collapse");
					if(expland=="true"){
						propDivHtml.addClass("in");
						propDivHtml.attr("aria-expanded","true");
					}
					var ulHtml=$("<ul></ul>");
					ulHtml.addClass("list-items");
					that.bodyArea = ulHtml;
					if(obj.inputMode&&obj.inputMode=="list"){
						that.createList(obj,props);
					}else{
					   that.createProperties(props);
					}
					propDivHtml.append(ulHtml);
					target.append(propDivHtml);
				}
			});
		},
		_initTemplate: function(target){
			target.attr("id", target.attr("id"));
			target.css("border", "0px");
			var that=this;

			/*var sb = new StringBuffer();
			 $.each(this.data.templates, function(i, obj) {
				ids.push({'id':obj.id,'id_':obj.id_});
				var imageUrl = contextPath+'/service/designer/'+obj.id_+'/getTemplateImage';
				imageUrl = obj.image_exists?imageUrl:contextPath+'/images/notfound.jpg';
				sb.append('<li id="'+obj.id+'" class="mymedia" style="float: left;cursor:pointer;">');
				sb.append('<img class="mymedia-object" style="width:100px;height:100px;" src="'+imageUrl+'" alt="..." title="'+obj.title+'"/>');
				sb.append('<div class="mymedia-body">');
				sb.append('<h4 class="mymedia-heading">'+obj.title+'</h4>');
				sb.append('</div>');
				sb.append('</li>');
			}); */
			var dataRole=currentComponent.attr("data-role");
			var liTemplatePath=contextPath+"/scripts/designer/components/templates/"+dataRole+"/"+dataRole+".html";
			try{
				target.load(liTemplatePath,function(){
					var lis=$(this).find("li");
					//target.html(sb.toString());
					$.each(lis, function(i, o) {
						$(o).click(function(event) {
							        var tmpid=$(this).attr("tmpid");
							        //获取模板HTML代码
									var templatePath=contextPath+"/scripts/designer/components/templates/"+dataRole+"/"+dataRole+"_"+tmpid+".html";
									$.get(templatePath, function(data){
									if(data!=null&&data!=""){
										var content,id,newcontent;
										newcontent=$(data);
		//								var fvs = newcontent.find(".frame-variable");
		//								if(!fvs.length){
		//									return;
		//								}
										//判断当前控件是布局控件还是表单控件
										if(currentComponent.hasClass("layout_elem"))
										{
											content=currentComponent.find(".view:first").children();
											id=currentComponent.attr("id");
										}else{
											content=currentComponent;
											id=content.attr("id");
											//模板补充扩展属性
											newcontent.attr("data-role",content.attr("data-role"));
											newcontent.attr("scope",content.attr("scope"));
											newcontent.attr("crtltype",content.attr("crtltype"));
			                                newcontent.addClass("nlayout_elem");
										}
										fvs = content.find("."+id+".frame-variable");
										if(fvs.length==0&&content.hasClass(id)&&content.hasClass("frame-variable")){
											fvs = content;
											var $tag=newcontent.find("[frame-variable="+fvs.attr('frame-variable')+"]");
											if($tag.length==0&&newcontent.hasClass("frame-variable")){
												if(newcontent.attr("frame-variable")==fvs.attr('frame-variable')){
													$tag = newcontent;
												}else{
													return;
												}
											}
											if($tag.attr('type') == "text"){
												$tag.val(fvs.val());
											}else{
												$tag.html(fvs.html());
											}
										}
										else if(fvs.length>0){
											$.each(fvs, function(index, val) {
												var $tag=newcontent.find("[frame-variable="+$(val).attr('frame-variable')+"]");
												if($tag.length==0&&newcontent.hasClass("frame-variable")){
													if(newcontent.attr("frame-variable")==$(val).attr('frame-variable')){
														$tag = newcontent;
													}else{
														return;
													}
												}
												if($tag.attr('type') == "text"){
													$tag.val($(val).val());
												}else{
													$tag.html($(val).html());
												}
											});
										}else{
											newcontent.find("[frame-variable=body]").append(content.prop("outerHTML"));
										}
										//补充ID CLASS
										if(newcontent.hasClass("id")){
											 newcontent.addClass(id);
											 newcontent.removeClass("id");
										}
										$.each(newcontent.find(".id"),function(i,item){
										     $(item).addClass(id);
											 $(item).removeClass("id");
										});
										content.replaceWith(newcontent);
										initContainer();
										if(currentComponent.hasClass("layout_elem")){
											currentComponent=newcontent.closest(".layout_elem");
										}else{
											currentComponent=newcontent;
											newcontent.attr("id",id);
										}
									   }   
		                             });
						});
					});
				});
			}catch(exception){
				console.log("找不到以下文件:" + liTemplatePath);
			}


		},
	    createList: function(obj,animations){
			var sb = new StringBuffer();
			$.each(animations,function(i,animation){
				sb.append('<li id="'+animation.id+'" class="animation">');
				sb.append('<img class="animation-object" src="'+contextPath+'/scripts/designer/images/star.png" alt="..." title="'+animation.title+'"/>');
				sb.append('<div class="animation-body">');
				sb.append('<span  class="animation-heading">'+animation.title+'</span >');
				sb.append('</div>');
				sb.append('</li>');
			});
			this.bodyArea.append(sb.toString());
			$(".animateBtn:first").click();
			//获取动画规则
			if(currentComponent.attr("animate-in")){
			    var animateRule=JSON.parse(currentComponent.attr("animate-in"));
				$("#generalsetting_looptimes").val(animateRule["looptimes"]);
				$("#generalsetting_continueTime").val(animateRule["continueTime"]);
				$("#generalsetting_delayTime").val(animateRule["delayTime"]);
				this.bodyArea.find("#"+animateRule["animationName"]).addClass("animation-selected");
			}
			//绑定点击事件
			var funcName=eval(obj.ctrImpl);
			
			var continueTime,delayTime,looptimes;
			this.bodyArea.find(".animation").click(function(){
				var animationType=$(".animateBtn.selectedType").attr("value");
				looptimes=$("#generalsetting_looptimes").val();
				if($("#generalsetting_continueTime").val()!=""){
				   continueTime=$("#generalsetting_continueTime").val();
				}
				if($("#generalsetting_delayTime").val()!="")
				{
					delayTime=$("#generalsetting_delayTime").val();
				}
				funcName.call(this,animationType,$(this).attr("id"),looptimes,currentComponent,continueTime,delayTime,true); 
			});
		},
		createTbHeadTh : function(styleName, styleVal, title) {
			var hd_tr_th = $("<th></th>");
			if (styleName && styleVal)
				hd_tr_th.css(styleName, styleVal);
			if (title)
				hd_tr_th.append(title);
			return hd_tr_th;
		},
		createTbBody : function() {
			var table_body = $("<tbody></tbody>");
			table_body.addClass("datagrid-tbody");
			return table_body;
		},
		createProperties : function(props, parentProp, parentDom) {
			this.level++;
			var that=this;
			$.each(props, function(i, prop) {
				if (parentProp) {
					if (parentProp.pid)
						prop.pid = parentProp.pid + " " + prop.id;
					else
						prop.pid = parentProp.id + " " + prop.id;
				} else {
					prop.pid = prop.id;
				}
				that.prop = prop;
				var propdom = that.createProperty(prop, parentProp, parentDom);
				//var d_prop = prop.properties;
				//if (d_prop) {
				//	that.createProperties(d_prop, prop, propdom);
				//}
			});
			this.level--;
		},
		createProperty : function createProperty(prop, parentProp, parentDom) {
			var propdom;
			this.parentProp = parentProp;
			this.parentDom = parentDom;
			this.prop=prop;
			var liDom=$("<li></li>");
			liDom.addClass("mt-list-item");
			var rowDom=$("<div class=\"row\"></div>");
			var titleDom=$("<div class=\"col-md-4 column\"></div>");
			titleDom.append("<i class=\"icon-check\"></i>");
			titleDom.append(this.prop.title);
			rowDom.append(titleDom);
			var controlDom=$("<div class=\"col-md-8 column hmtd-xs\"></div>");
		
			if (prop.inputMode) {
				if (prop.inputMode == 'input') {
					liDom.css("line-height","45px");
					liDom.css("padding-top","5px");
					liDom.css("padding-bottom","5px");
					propdom = this.createInput();
				} else if (prop.inputMode == 'radio') {
					propdom = this.createRadio();
				} else if (prop.inputMode == 'select') {
					liDom.css("line-height","45px");
					liDom.css("padding-top","5px");
					liDom.css("padding-bottom","5px");
					propdom = this.createSelect();
				} else if (prop.inputMode == 'touchspin') {
				    propdom = this.createTouchspin();
				}else if (prop.inputMode == 'buttongroup') {
					 propdom = this.createButtonGroup();
				}else if (prop.inputMode == 'mulitselbuttongroup') {
					 propdom = this.createMulitSelButtonGroup();
				}else if (prop.inputMode == 'colorpicker') {
					 propdom = this.createColorPicker();
				}else if (prop.inputMode == 'rangeSlider') {
					propdom = this.createRangeSlider();
				}
				else if (prop.inputMode == 'template') {
					propdom = this.createTemplate();
				}
			} 
			controlDom.append(propdom);
			rowDom.append(controlDom);
			liDom.append(rowDom);
			this.bodyArea.append(liDom);
			return propdom;
		},
		getDataRole : function(){
			var dataRole;
			if(currentComponent.attr("data-role")!=undefined&&currentComponent.attr("data-role")!=""&&currentComponent.attr("data-role").indexOf("col-md")==-1){
				dataRole=currentComponent.attr("data-role");
			}else if(currentComponent.hasClass("column")){
				dataRole="div";
			}else if(currentComponent.attr("data-role").indexOf("col-md")!=-1){
				dataRole="col";
			}
			else
			{
			    dataRole=currentComponent.find("[data-role]:first")?currentComponent.find("[data-role]:first").attr("data-role"):"";
			}
			return dataRole;
		},
		createInput : function() {
			var contentDom=$("<div class=\"form-group form-md-line-input\" style=\"padding:0px;margin:0px;\"></div>");
			var inputDom=$("<input type=\"text\" class=\"form-control\" \>");
			inputDom.attr("id",this.prop.id);
			var ctrImpl=this.prop.ctrImpl;
			var eventName=this.prop.event;
			if(this.prop.defaultVal!=null&&this.prop.defaultVal!=undefined){
				inputDom.val(this.prop.defaultVal);
			}
			contentDom.append(inputDom);
			var toolbox=this;
			
			var key =  this.prop.id.replace(/-[A-Za-z]/g, function(word) {   
                           return word.substring(1,2).toUpperCase();
                         });   
			var value ="";
			if(currentComponent.attr("data-rule")){
				value = JSON.parse(currentComponent.attr("data-rule"))[key];
			}
			inputDom.val(value);
			
			  //获取事件方法名
			  //绑定事件
			  if(eventName!=undefined&&eventName!=""){
				  inputDom.blur(function(){
				  var inputVal=$(this).val();
				  var dataRole;
				  try {
					dataRole=toolbox.getDataRole();
					ctrImpl = dataRole!=""?eval(dataRole+"_designer."+ctrImpl):eval(ctrImpl);
				} catch(e) {
					console.log(e);
					try{
					   if(dataRole!="")
					   ctrImpl=eval(ctrImpl)
					}catch(e){
					   console.log(e);
					   alert(ctrImpl+'方法不存在！');
					}
				}
				if (typeof ctrImpl === 'function'){
					
					ctrImpl.call(this,currentComponent,inputVal); 
				}   
			  });
			  }else{
			  inputDom.change(function(){
				  var inputVal=$(this).val();
				  var dataRole;
				  try {
					dataRole=toolbox.getDataRole();
					if(typeof ctrImpl == 'string'){
						ctrImpl = dataRole!=""?eval(dataRole+"_designer."+ctrImpl):eval(ctrImpl);	
					}
				} catch(e) {
					console.log(e);
					try{
					   if(dataRole!="")
					   ctrImpl=eval(ctrImpl)
					}catch(e){
					   console.log(e);
					   alert(ctrImpl+'方法不存在！');
					}
				}
				if (typeof ctrImpl === 'function'){
					
					ctrImpl.call(this,currentComponent,inputVal); 
				}   
			  });
			  }
			return contentDom;
		},
		createRadio : function() {
			var that=this.prop;
			var contentDom=$("<div class=\"form-group\" style=\"padding:0px;margin:0px;\"></div>");
			var inputGroupDom=$("<div class=\"input-group\"></div>");
			var radioGroupDom=$("<div class=\"icheck-inline\"></div>");
			var radioItems=this.prop.valrange;
			var ctrImpl=that.ctrImpl
			var toolbox=this;
			$.each(radioItems, function(i, item) {
                      var labelDom=$("<label></label>");
					  var radioDom=$("<input type=\"radio\"  name=\""+that.id+"\" class=\"icheck\" data-radio=\"iradio_flat-grey\">");
					    var key =  that.id.replace(/-[A-Za-z]/g, function(word) {   
                           return word.substring(1,2).toUpperCase();
                         });   
					 if(currentComponent.attr("data-rule")){
					    that.defaultVal = JSON.parse(currentComponent.attr("data-rule"))[key];
					 }
					   
					  if(that.defaultVal==item.value){
						  radioDom.attr("checked", "checked");
					  }
					  //获取事件方法名
					  //绑定事件
					  radioDom.click(function(){
						  var checkedVal=$('input[name="'+$(this).attr("name")+'"]:checked ').val();
						 var dataRole;
						  try {
							dataRole=toolbox.getDataRole();
							ctrImpl = dataRole!=""?eval(dataRole+"_designer."+ctrImpl):eval(ctrImpl);
						} catch(e) {
							console.log(e);
							try{
							   if(dataRole!="")
							   ctrImpl=eval(ctrImpl)
							}catch(e){
							   console.log(e);
							   alert(ctrImpl+'方法不存在！');
							}
						}
						if (typeof ctrImpl === 'function'){
							ctrImpl.call(this,currentComponent,checkedVal); 
							initContainer();
						}   
					  });
					  radioDom.attr("value",item.value);
					  labelDom.append(radioDom);
					  labelDom.append(item.title);
					  radioGroupDom.append(labelDom);
				});
			inputGroupDom.append(radioGroupDom);
			contentDom.append(inputGroupDom);
			return contentDom;
		},
		createSelect : function() {
			var select_dom = $("<select class=\"bs-select form-control\" data-width=\"125px\" data-style=\"btn-primary\"></select>");
			if (this.prop.valrange) {
				var that=this;
				select_dom.attr("name", that.prop.id);
				select_dom.attr("id", that.prop.id);
				$.each(this.prop.valrange, function(i, item) {
					var option_dom = $("<option></option>");
					option_dom.attr("value", item.value);
					option_dom.append(item.title);
					/*if(that.prop.value!=null){
						if (that.prop.value == item.value) {
							option_dom.attr("selected", "selected");
						}
					}else{*/
					   var key =  that.prop.id.replace(/-[A-Za-z]/g, function(word) {   
                           return word.substring(1,2).toUpperCase();
                         });   
					 if(currentComponent.attr("data-rule")){
					    that.prop.defaultVal = JSON.parse(currentComponent.attr("data-rule"))[key];
					 }
					  if (that.prop.defaultVal == item.value) {
						    option_dom.attr("selected", "selected");
					  }else{
						    //option_dom.attr("selected", "selected");
					  }
					//}
					select_dom.append(option_dom);
				});
				var ctrImpl=this.prop.ctrImpl;
				var toolbox=this;
				select_dom.change(function(){
							  var selectedVal=$(this).val();
							  var dataRole;
							  try {
								dataRole=toolbox.getDataRole();
								ctrImpl = dataRole!=""?eval(dataRole+"_designer."+ctrImpl):eval(ctrImpl);
							} catch(e) {
								console.log(e);
								try{
								   if(dataRole!="")
								   ctrImpl=eval(ctrImpl)
								}catch(e){
								   console.log(e);
								   alert(ctrImpl+'方法不存在！');
								}
							}
							if (typeof ctrImpl === 'function'){
								ctrImpl.call(this,currentComponent,selectedVal); 
							}   
				});
			}
			return select_dom;

		},
		createTouchspin : function() {
			if (this.prop.properties) {
				var that=this.prop;
				var contentDom=$("<div></div>");
				var toolbox=this;
				$.each(this.prop.properties, function(i, item) {
					var ctrImpl=item.ctrImpl
					var  inputDom=$("<input class=\"form-control spaceTouchSpin spininput\">");
					inputDom.attr("id",that.id+"_"+item.id);
					inputDom.attr("name",that.id+"_"+item.id);
					inputDom.attr("prefix",item.title);
					if(item.icon){
						inputDom.attr("prefix","<i class='fa fa-"+item.icon+"' style='color:#000;'></i>");
					}
					if(item.minval)
					inputDom.attr("min",item.minval);
				    if(item.maxval)
					inputDom.attr("max",item.maxval);
				      var key =  item.id.replace(/-[A-Za-z]/g, function(word) {   
                           return word.substring(1,2).toUpperCase();
                         });   
					 if(currentComponent.attr("data-rule")){
					    item.defaultVal = JSON.parse(currentComponent.attr("data-rule"))[key];
					 }
					if(item.defaultVal)
					inputDom.val(item.defaultVal);
				   
				    inputDom.change(function(){
						  var inputVal=$(this).val();
						  var unitSelectId=$(this).attr("id")+"_sel";
						  var unitVal;
						  if($("#"+unitSelectId).length==1){
							  unitVal=$("#"+unitSelectId).val();
						  }
						  var dataRole;
						  try {
							dataRole=toolbox.getDataRole();
							ctrImpl = dataRole!=""?(eval(dataRole+"_designer."+ctrImpl)!=undefined?eval(dataRole+"_designer."+ctrImpl):eval(ctrImpl)):eval(ctrImpl);
						} catch(e) {
							console.log(e);
							try{
							   if(dataRole!="")
							   ctrImpl=eval(ctrImpl)
							}catch(e){
							   console.log(e);
							   alert(ctrImpl+'方法不存在！');
							}
						}
						if (typeof ctrImpl === 'function'){
							
							if(unitVal!=undefined){
							     ctrImpl.call(this,currentComponent,inputVal,unitVal); 
							}else{
								 ctrImpl.call(this,currentComponent,inputVal); 
							}
						}   
					  });
				    contentDom.append(inputDom);
					if(item.unit!=undefined&&item.unit!=''){
					    
						var unitSelect=$("<select class=\"unitselect\"></select>");
						unitSelect.css("float","inherit");
						var opts=item.unit.split(",");
						unitSelect.attr("id",that.id+"_"+item.id+"_sel");
						$.each(opts,function(i,opt){
							unitSelect.append("<option value=\""+opt+"\">"+opt+"</option>");
						});
						contentDom.append(unitSelect);
						 if(currentComponent.attr("data-rule")){
							var selectedVal = JSON.parse(currentComponent.attr("data-rule"))[key+"_unit"];
							if(selectedVal!=undefined&&selectedVal!=''){
								unitSelect.val(selectedVal);
							}
						 }
						unitSelect.change(function(){
						  var inputVal=$(this).prev().find(".spaceTouchSpin").val();
						  var unitVal=$(this).val();
						  var dataRole;
						  try {
							dataRole=toolbox.getDataRole();
							ctrImpl = dataRole!=""?eval(dataRole+"_designer."+ctrImpl):eval(ctrImpl);
						} catch(e) {
							console.log(e);
							try{
							   if(dataRole!="")
							   ctrImpl=eval(ctrImpl)
							}catch(e){
							   console.log(e);
							   alert(ctrImpl+'方法不存在！');
							}
						}
						if (typeof ctrImpl === 'function'){
							if(unitVal!=undefined&&unitVal!=''){
							 ctrImpl.call(this,currentComponent,inputVal,unitVal); 
							}else{
								 ctrImpl.call(this,currentComponent,inputVal); 
							}
						}   
					  }); 
					}
				});
			}											   
			return contentDom.children();
		},
		createButtonGroup : function() {
			var contentDom=$("<div class=\"btn-group\"></div>");
			if (this.prop.valrange) {
				var that=this;
				var ctrImpl=this.prop.ctrImpl;
				var toolbox=this;
				//规则的ID信息
				var key =  this.prop.id.replace(/-[A-Za-z]/g, function(word) {   
                           return word.substring(1,2).toUpperCase();
                         });   
				var value ="";
				//当前控件的规则信息
				if(currentComponent.attr("data-rule")){
					value = JSON.parse(currentComponent.attr("data-rule"))[key];
				}
				$.each(this.prop.valrange, function(i, item) {	
                    var buttonDom= $("<button type=\"button\" class=\"btn btn-default  btn-sm\"></button>");
					if(item.icon){
						buttonDom.append("<i class='fa fa-"+item.icon+"'></i>");
					}
					buttonDom.append(item.name);
					buttonDom.attr("title",item.title);
					buttonDom.attr("value",item.value);
					if(value && item.value && value == item.value){
						buttonDom.addClass('active');
					}
					
					buttonDom.click(function(){
						
						// $(this).addClass('active');
						  if($(this).hasClass('active')){
							  $(this).removeClass('active');
						  }else{
						  	contentDom.find(".btn.active").removeClass('active');
							  $(this).addClass('active');
						  }
						  var inputVal = "";
							if ($(this).hasClass('active')) {
								inputVal = $(this).attr("value");
							}
							
						 var dataRole;
						 if(typeof ctrImpl == 'string'){
							  try {
								dataRole=toolbox.getDataRole();
								ctrImpl = dataRole!=""?eval(dataRole+"_designer."+ctrImpl):eval(ctrImpl);
							} catch(e) {
								console.log(e);
								try{
								   if(dataRole!="")
								   ctrImpl=eval(ctrImpl)
								}catch(e){
								   console.log(e);
								   alert(ctrImpl+'方法不存在！');
								}
							}
						 }
						if (typeof ctrImpl === 'function'){
							ctrImpl.call(this,currentComponent,inputVal); 
						}   
					  });
					contentDom.append(buttonDom);
				});
			}
			return contentDom;
		},
		createMulitSelButtonGroup: function() {
			var contentDom=$("<div class=\"btn-group\"></div>");
			if (this.prop.valrange) {
				var that=this;
				var ctrImpl=this.prop.ctrImpl;
				var toolbox=this;
				var key =  this.prop.id.replace(/-[A-Za-z]/g, function(word) {  
                   return word.substring(1,2).toUpperCase();
                });   
				var value ="";
				if(currentComponent.attr("data-rule")){
					value = JSON.parse(currentComponent.attr("data-rule"))[key];
				}
				$.each(this.prop.valrange, function(i, item) {
                    var buttonDom= $("<button type=\"button\" class=\"btn btn-default  btn-sm\"></button>");
					if(item.icon){
						buttonDom.append("<i class='fa fa-"+item.icon+"'></i>");
					}
					buttonDom.append(item.name);
					buttonDom.attr("title",item.title);
					buttonDom.attr("value",item.value);
					if(value!=undefined&&value!=''&&value.indexOf(item.value)>-1){
						      buttonDom.addClass('active');
							  buttonDom.removeClass('btn-default');
							  buttonDom.addClass('btn-success');
					}
					buttonDom.click(function(){
						  if($(this).hasClass('active')){
							  $(this).removeClass('active');
							  $(this).removeClass('btn-success');
							  $(this).addClass('btn-default');
						  }else{
							  $(this).addClass('active');
							  $(this).removeClass('btn-default');
							  $(this).addClass('btn-success');
						  }
						 
						 //获取选中的值
						 var btgroup=$(this).closest(".btn-group");
						 var selectedBts=btgroup.find(".active");
						 var inputVal=new Array();
						 $.each(selectedBts,function(i,selectedBt){
							   inputVal.push($(selectedBt).attr("value")); 
						 });
						 var dataRole;
						  try {
							dataRole=toolbox.getDataRole();
							ctrImpl = dataRole!=""?eval(dataRole+"_designer."+ctrImpl):eval(ctrImpl);
						} catch(e) {
							console.log(e);
							try{
							   if(dataRole!="")
							   ctrImpl=eval(ctrImpl)
							}catch(e){
							   console.log(e);
							   alert(ctrImpl+'方法不存在！');
							}
						}
						if (typeof ctrImpl === 'function'){
							ctrImpl.call(this,currentComponent,inputVal); 
						}   
					  });
					contentDom.append(buttonDom);
				});
			}
			return contentDom;
		},
		createColorPicker : function() {
			var inputDom=$(" <input type=\"text\" class=\"form-control colorpicker\">");
			inputDom.attr("id",this.prop.id);
			var ctrImpl=this.prop.ctrImpl;
			var toolbox=this;
            var key =  this.prop.id.replace(/-[A-Za-z]/g, function(word) {   
                           return word.substring(1,2).toUpperCase();
                         });   
			var value ="";
			if(currentComponent.attr("data-rule")){
				value = JSON.parse(currentComponent.attr("data-rule"))[key];
			}
			inputDom.attr("value",value);
			inputDom.change(function(){
						  var inputVal=$(this).val();
						 var dataRole;
						  try {
							dataRole=toolbox.getDataRole();
							if(typeof ctrImpl == 'string'){
								ctrImpl = dataRole!=""?eval(dataRole+"_designer."+ctrImpl):eval(ctrImpl);	
							}
						} catch(e) {
							console.log(e);
							try{
							   if(dataRole!="")
							   ctrImpl=eval(ctrImpl)
							}catch(e){
							   console.log(e);
							   alert(ctrImpl+'方法不存在！');
							}
						}
						if (typeof ctrImpl === 'function'){
							ctrImpl.call(this,currentComponent,inputVal); 
						}   
			});
			return inputDom;
		},
		createRangeSlider : function() {
			var inputDom=$(" <input type=\"text\" class=\"rangeSlider\"/>");
			inputDom.attr("id",this.prop.id);
			var ctrImpl=this.prop.ctrImpl;
			var toolbox=this;
			var key =  this.prop.id.replace(/-[A-Za-z]/g, function(word) {   
                           return word.substring(1,2).toUpperCase();
                         });   
			var value ="";
			if(currentComponent.attr("data-rule")){
				value = JSON.parse(currentComponent.attr("data-rule"))[key];
			}
			inputDom.val(value);
			inputDom.change(function(){
						  var inputVal=$(this).val();
						  var dataRole;
						  try {
							dataRole=toolbox.getDataRole();
							ctrImpl = dataRole!=""?eval(dataRole+"_designer."+ctrImpl):eval(ctrImpl);
						} catch(e) {
							console.log(e);
							try{
							   if(dataRole!="")
							   ctrImpl=eval(ctrImpl)
							}catch(e){
							   console.log(e);
							   alert(ctrImpl+'方法不存在！');
							}
						}
						if (typeof ctrImpl === 'function'){
							ctrImpl.call(this,currentComponent,inputVal); 
						}   
			});
			return inputDom;
		},
		createTemplate : function() {
			var template=this.prop.template;
			var toolbox=this;
			var ctrImpl=this.prop.ctrImpl;
			
			var key =  this.prop.id.replace(/-[A-Za-z]/g, function(word) {   
                           return word.substring(1,2).toUpperCase();
                         });
			var value ="";
			if(currentComponent.attr("data-rule")){
				value = JSON.parse(currentComponent.attr("data-rule"))[key];
			}
//			if(value!=""){
				var dataRole = toolbox.getDataRole();
				var initImpl = dataRole!=""?eval(dataRole+"_designer."+this.prop.initImpl):eval(initImpl);
				if (typeof initImpl === 'function'){
					template = initImpl(template,value); 
				}
//			}
			var hidden= $("<input type=\"hidden\">");
			hidden.attr("id",key);	
			hidden.click(function(){
						  var inputVal=$(this).val();
						  var dataRole;
						  try {
							dataRole=toolbox.getDataRole();
							ctrImpl = dataRole!=""?eval(dataRole+"_designer."+ctrImpl):eval(ctrImpl);
						} catch(e) {
							console.log(e);
							try{
							   if(dataRole!="")
							   ctrImpl=eval(ctrImpl)
							}catch(e){
							   console.log(e);
							   alert(ctrImpl+'方法不存在！');
							}
						}
						if (typeof ctrImpl === 'function'){
							ctrImpl.call(this,currentComponent,inputVal); 
						}   
			});
			hidden.val(value);
			$template = $(template).append(hidden);
			return $template;
		}
	};
})(jQuery);