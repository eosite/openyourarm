
(function($) {
	var common_designer = {
		// getCommonStyleData : function($component,prop){
		// 	var value = "";
		// 	if (component.attr("common-data-rule")) {
		// 		value = JSON.parse(component.attr("common-data-rule"))[prop];
		// 	}
		// 	return value;
		// },
		// setCommonStyleData : function($component,prop,val){
		// 	if (component.attr("common-data-rule")) {
		// 		var data_rule = JSON.parse(component.attr("common-data-rule"));
		// 		data_rule[prop] = val;
		// 	} else {
		// 		var data_rule = {};
		// 		data_rule[prop] = val;
		// 	}
		// 	component.attr("common-data-rule", JSON.stringify(data_rule));
		// },
		dbclickComponent : null,
		overComponents : function($components,callback){
			$.each($components,function(i,item){
				var $item = $(item);
				var $target = $item.find(".mainObj");
				if($target[0]){
					$item = $target;
				}
				if($.isFunction(callback)){
					callback($item);
				}
			});
		},
		setFontFamily : function($components,val){
			var $component = $(".selectedComp");
			common_designer.overComponents($components,function($item){
				$item.css("font-family",val);
			})
		},
		setFontSize : function($components,val){
			
			common_designer.overComponents($components,function($item){
				if(val==''){
				   $item.css("font-size",val);
				}else{
					$item.css("font-size",val+"px");
				}
				
			})
		},
		setFontBold : function($components,val){
			var $this = $(this);
			common_designer.overComponents($components,function($item){
				if($this.hasClass('active')){
			      $item.css("font-weight",'bold');
				 }else{
				   $item.css("font-weight",'');   
			    }
			})
		},
		setTextItalic : function($components,val){
			var $this = $(this);
			common_designer.overComponents($components,function($item){
				if($this.hasClass('active')){
			      $item.css("font-style",'italic');
				 }else{
				   $item.css("font-style",'');
			    }
			})
		},
		setTextUnderline : function($components,val){
			var $this = $(this);
			common_designer.overComponents($components,function($item){
				if($this.hasClass('active')){
			      $item.css("text-decoration",'underline');
				 }else{
				   $item.css("text-decoration",'');
			    }
			})
		},
		setTextAlign : function($components,val){
			var $this = $(this);
			common_designer.overComponents($components,function($item){
				$item.css("text-align",val);
			})
		},
		setPaddingTop : function($components,val){
			var $this = $(this);
			common_designer.overComponents($components,function($item){
				if(val){
					$item.css("padding-top",val+'px');
				}else{
					$item.css("padding-top",val);
				}
			})
		},
		setPaddingBottom : function($components,val){
			var $this = $(this);
			common_designer.overComponents($components,function($item){
				if(val){
					$item.css("padding-bottom",val+'px');
				}else{
					$item.css("padding-bottom",val);
				}
			})
		},
		setPaddingLeft : function($components,val){
			var $this = $(this);
			common_designer.overComponents($components,function($item){
				if(val){
					$item.css("padding-left",val+'px');
				}else{
					$item.css("padding-left",val);
				}
			})
		},
		setPaddingRight : function($components,val){
			var $this = $(this);
			common_designer.overComponents($components,function($item){
				if(val){
					$item.css("padding-right",val+'px');
				}else{
					$item.css("padding-right",val);
				}
			})
		},
		setMarginTop : function($components,val){
			var $this = $(this);
			common_designer.overComponents($components,function($item){
				if(val){
					$item.css("margin-top",val+'px');
				}else{
					$item.css("margin-top",val);
				}
			})
		},
		setMarginBottom : function($components,val){
			var $this = $(this);
			common_designer.overComponents($components,function($item){
				if(val){
					$item.css("margin-bottom",val+'px');
				}else{
					$item.css("margin-bottom",val);
				}
			})
		},
		setMarginLeft : function($components,val){
			var $this = $(this);
			common_designer.overComponents($components,function($item){
				if(val){
					$item.css("margin-left",val+'px');
				}else{
					$item.css("margin-left",val);
				}
			})
		},
		setMarginRight : function($components,val){
			var $this = $(this);
			common_designer.overComponents($components,function($item){
				if(val){
					$item.css("margin-right",val+'px');
				}else{
					$item.css("margin-right",val);
				}
			})
		},
		setBorder : function($components,val){
			common_designer.border = val;
		},
		setBorderSize : function($components,val){
			var key = "border-width";
			if(common_designer.border){
				key = "border-"+common_designer.border+"-width";
			}
			common_designer.overComponents($components,function($component){
				if(val==''){
				   $component.css(key,val);
				}else{
					$component.css(key,val+"px");
				}

			})
		}, 
		setBorderStyle : function($components,val){
			var key = "border-style";
			if(common_designer.border){
				key = "border-"+common_designer.border+"-style";
			}
			common_designer.overComponents($components,function($component){
				$component.css(key,val);
			})
		},
		setBorderColor : function($components,val){
			var key = "border-color";
			if(common_designer.border){
				key = "border-"+common_designer.border+"-color";
			}
			common_designer.overComponents($components,function($component){
				$component.css(key,val);
			})
		},
	};
	//var prop={},parentProp={},parentDom,data={},bodyArea,level;
	$.commonStyleToolbox = function() {
	
	};
	$.commonStyleToolbox.prototype = {
		prop : {},
		parentProp : {},
		parentDom : null,
		data : null,
		bodyArea : null,
		level : null,
		dbclickComponent : null,
		findSelectedComponent : function(){
			var $components = $(".selectedComp");
			if($components[0]){
				return $components;
			}else{
				return this.dbclickComponent;
			}
		},
		createProperty : function createProperty(prop, parentProp, parentDom) {
			var $package = $('<div class="form-group hmtd-xs"></div>');
			if(prop.img){
				$package.append($("<img src='"+contextPath+prop.img+"' style='padding-right: 3px;'>:"));
			}else if(prop.label){
				$package.append($("<label>").text(prop.label));	
			}

			var $input_group = $('<div class="input-group" title="'+prop.title+'"></div>');
			var propdom;
			if(prop.inputMode){
				this.prop = prop;
				if (prop.inputMode == 'input') {
					$input_group.css("line-height","45px");
					$input_group.css("padding-top","5px");
					$input_group.css("padding-bottom","5px");
					propdom = this.createInput();
				} else if (prop.inputMode == 'radio') {
					propdom = this.createRadio();
				} else if (prop.inputMode == 'select') {
					$input_group.css("line-height","45px");
					$input_group.css("padding-top","0px");
					$input_group.css("padding-bottom","0px");
					$input_group.addClass("hmtd-xs");
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
				}else if(prop.inputMode == 'split'){
					propdom = $('<img class="split" src="/glaf/scripts/designer/images/toolbar/line.png">');
				}else if(prop.inputMode == 'numberInput'){
					propdom = this.createNumberInput();
				}
			}


			// controlDom.append(propdom);
			// rowDom.append(propdom);
			
			$input_group.append(propdom);
			$package.append($input_group);
			return $package;
		},
		getDataRole : function(){
			return "common";
		},
		createInput : function() {
			var contentDom=$("<div class=\"form-group\" style=\"padding:0px;margin:0px;\"></div>");
			var inputDom=$("<input type=\"text\" class=\"form-control input-sm\" \>");
			inputDom.attr("id",this.prop.id);
			var ctrImpl=this.prop.ctrImpl;
			var eventName=this.prop.event;
			if(this.prop.defaultVal!=null&&this.prop.defaultVal!=undefined){
				inputDom.val(this.prop.defaultVal);
			}
			if(this.prop.width){
				inputDom.css("width",this.prop.width);
			}
			if(this.prop.type){
				inputDom.attr("type",this.prop.type);
				inputDom.attr("max",this.prop.max);
				inputDom.attr("min",this.prop.min);
			}


			contentDom.append(inputDom);
			var toolbox=this;
			
			var key =  this.prop.id.replace(/-[A-Za-z]/g, function(word) {   
                           return word.substring(1,2).toUpperCase();
                         });   
			var value ="";
			// if(currentComponent.attr("data-rule")){
			// 	value = JSON.parse(currentComponent.attr("data-rule"))[key];
			// }
			inputDom.val(value);
			
			  //获取事件方法名
			  //绑定事件
			  if(eventName!=undefined&&eventName!=""){
				  inputDom.blur(function(){
				  var inputVal=$(this).val();
				  var dataRole;
				  try {
					dataRole=toolbox.getDataRole();
					if(typeof ctrImpl == "string"){
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
			  }else{
			  inputDom.change(function(){
			  		var $component = toolbox.findSelectedComponent();
			  		if(!$component[0]){
			  			return;
			  		}
				  	var inputVal=$(this).val();
				  	var dataRole;
				  try {
					dataRole=toolbox.getDataRole();
					if(typeof ctrImpl == "string"){
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
					
					ctrImpl.call(this,$component,inputVal); 
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
					 // if(currentComponent.attr("data-rule")){
					 //    that.defaultVal = JSON.parse(currentComponent.attr("data-rule"))[key];
					 // }
					   
					  if(that.defaultVal==item.value){
						  radioDom.attr("checked", "checked");
					  }
					  //获取事件方法名
					  //绑定事件
					  radioDom.click(function(){
					  	var $component = toolbox.findSelectedComponent();
				  		if(!$component[0]){
				  			return;
				  		}
						  var checkedVal=$('input[name="'+$(this).attr("name")+'"]:checked ').val();
						 var dataRole;
						  try {
							dataRole=toolbox.getDataRole();
							if(typeof ctrImpl == "string"){
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
							ctrImpl.call(this,$component,checkedVal); 
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
					 // if(currentComponent.attr("data-rule")){
					 //    that.prop.defaultVal = JSON.parse(currentComponent.attr("data-rule"))[key];
					 // }
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
					var $component = toolbox.findSelectedComponent();
				  		if(!$component[0]){
				  			return;
				  		}
							  var selectedVal=$(this).val();
							  var dataRole;
							  try {
								dataRole=toolbox.getDataRole();
								if(typeof ctrImpl == "string"){
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
								ctrImpl.call(this,$component,selectedVal); 
							}   
				});
			}
			return select_dom;
		},
		createNumberInput : function(){
			var contentDom=$("<div class='hmtd-xs' style='margin-top:-3px;'></div>");

			var ctrImpl=this.prop.ctrImpl
			var  inputDom=$("<input class=\"form-control spaceTouchSpin spininput\">");
			inputDom.attr("id",this.prop.id+"_"+this.prop.id);
			inputDom.attr("name",this.prop.id+"_"+this.prop.id);
			inputDom.attr("prefix",this.prop.title);
			if(this.prop.icon){
				inputDom.attr("prefix","<i class='fa fa-"+this.prop.icon+"' style='color:#000;'></i>");
			}
			if(this.prop.minval)
			inputDom.attr("min",this.prop.minval);
		    if(this.prop.maxval)
			inputDom.attr("max",this.prop.maxval);
		      var key =  this.prop.id.replace(/-[A-Za-z]/g, function(word) {   
                   return word.substring(1,2).toUpperCase();
                 });   
			 // if(currentComponent.attr("data-rule")){
			 //    this.prop.defaultVal = JSON.parse(currentComponent.attr("data-rule"))[key];
			 // }
			if(this.prop.defaultVal)
			inputDom.val(this.prop.defaultVal);
		   var toolbox=this;
		    inputDom.change(function(){
		    	var $component = toolbox.findSelectedComponent();
				  		if(!$component[0]){
				  			return;
				  		}
				  var inputVal=$(this).val();
				  var unitSelectId=$(this).attr("id")+"_sel";
				  var unitVal;
				  if($("#"+unitSelectId).length==1){
					  unitVal=$("#"+unitSelectId).val();
				  }
				  var dataRole;
				  try {
					dataRole=toolbox.getDataRole();
					if(typeof ctrImpl == 'string'){
						ctrImpl = dataRole!=""?(eval(dataRole+"_designer."+ctrImpl)!=undefined?eval(dataRole+"_designer."+ctrImpl):eval(ctrImpl)):eval(ctrImpl);	
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
					
					if(unitVal!=undefined){
					     ctrImpl.call(this,$component,inputVal,unitVal); 
					}else{
						 ctrImpl.call(this,$component,inputVal); 
					}
				}   
			  });
		    contentDom.append(inputDom);
		    if(this.prop.unit!=undefined&&this.prop.unit!=''){
					    
				var unitSelect=$("<select class=\"unitselect\"></select>");
				unitSelect.css("float","inherit");
				var opts=this.prop.unit.split(",");
				unitSelect.attr("id",this.prop.id+"_"+this.prop.id+"_sel");
				$.each(opts,function(i,opt){
					unitSelect.append("<option value=\""+opt+"\">"+opt+"</option>");
				});
				contentDom.append(unitSelect);
				 // if(currentComponent.attr("data-rule")){
					// var selectedVal = JSON.parse(currentComponent.attr("data-rule"))[key+"_unit"];
					// if(selectedVal!=undefined&&selectedVal!=''){
					// 	unitSelect.val(selectedVal);
					// }
				 // }
				 var toolbox=this;
				unitSelect.change(function(){
					var $component = toolbox.findSelectedComponent();
				  		if(!$component[0]){
				  			return;
				  		}
				  var inputVal=$(this).prev().find(".spaceTouchSpin").val();
				  var unitVal=$(this).val();
				  var dataRole;
				  try {
					dataRole=toolbox.getDataRole();
					if(typeof ctrImpl == "string"){
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
					if(unitVal!=undefined&&unitVal!=''){
					 ctrImpl.call(this,$component,inputVal,unitVal); 
					}else{
						 ctrImpl.call(this,$component,inputVal); 
					}
				}   
			  }); 
			}
			return contentDom;
		},
		createTouchspin : function() {
			if (this.prop.properties) {
				var that=this.prop;
				var contentDom=$("<div class='hmtd-xs' style='margin-top:-3px;'></div>");
				var toolbox=this;
				$.each(this.prop.properties, function(i, item) {
					var ctrImpl=item.ctrImpl
					var  inputDom=$("<input class=\"form-control spaceTouchSpin spininput\">");
					// inputDom.attr("id",that.id+"_"+item.id);
					inputDom.attr("id",item.id);
					inputDom.attr("name",item.id);
					// inputDom.attr("name",that.id+"_"+item.id);
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
					 // if(currentComponent.attr("data-rule")){
					 //    item.defaultVal = JSON.parse(currentComponent.attr("data-rule"))[key];
					 // }
					if(item.defaultVal)
					inputDom.val(item.defaultVal);
				   
				    inputDom.change(function(){
				    	var $component = toolbox.findSelectedComponent();
				  		if(!$component[0]){
				  			return;
				  		}
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
							     ctrImpl.call(this,$component,inputVal,unitVal); 
							}else{
								 ctrImpl.call(this,$component,inputVal); 
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
						 // if(currentComponent.attr("data-rule")){
							// var selectedVal = JSON.parse(currentComponent.attr("data-rule"))[key+"_unit"];
							// if(selectedVal!=undefined&&selectedVal!=''){
							// 	unitSelect.val(selectedVal);
							// }
						 // }
						unitSelect.change(function(){
							var $component = toolbox.findSelectedComponent();
				  		if(!$component[0]){
				  			return;
				  		}
						  var inputVal=$(this).prev().find(".spaceTouchSpin").val();
						  var unitVal=$(this).val();
						  var dataRole;
						  try {
							dataRole=toolbox.getDataRole();
							if(typeof ctrImpl == "string"){
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
							if(unitVal!=undefined&&unitVal!=''){
							 ctrImpl.call(this,$component,inputVal,unitVal); 
							}else{
								 ctrImpl.call(this,$component,inputVal); 
							}
						}   
					  }); 
					}
				});
			}											   
			return contentDom;
		},
		createButtonGroup : function() {
			var contentDom=$("<div class=\"btn-group\" id='"+this.prop.id+"' type='btn'></div>");
			if (this.prop.valrange) {
				var that=this;
				var ctrImpl=this.prop.ctrImpl;
				var toolbox=this;
				$.each(this.prop.valrange, function(i, item) {	
                    var buttonDom= $("<button type=\"button\" class=\"btn btn-default  btn-sm\"></button>");
					if(item.icon){
						buttonDom.append("<i class='fa fa-"+item.icon+"'></i>");
					}
					buttonDom.append(item.name);
					buttonDom.attr("title",item.title);
					buttonDom.attr("value",item.value);
					
					buttonDom.click(function(){
						var $component = toolbox.findSelectedComponent();
				  		if(!$component[0]){
				  			return;
				  		}
						  if($(this).hasClass('active')){
							  $(this).removeClass('active');
						  }else{
						  	contentDom.find(".btn").removeClass('active');
							  $(this).addClass('active');
						  }
						  var inputVal=$(this).attr("value");
						 var dataRole;
						  try {
							dataRole=toolbox.getDataRole();
							if(typeof ctrImpl == "string"){
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
							ctrImpl.call(this,$component,inputVal); 
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
				// if(currentComponent.attr("data-rule")){
				// 	value = JSON.parse(currentComponent.attr("data-rule"))[key];
				// }
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
						var $component = toolbox.findSelectedComponent();
				  		if(!$component[0]){
				  			return;
				  		}
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
							if(typeof ctrImpl == "string"){
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
							ctrImpl.call(this,$component,inputVal); 
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
			// if(currentComponent.attr("data-rule")){
			// 	value = JSON.parse(currentComponent.attr("data-rule"))[key];
			// }
			inputDom.attr("value",value);
			inputDom.change(function(){
				var $component = toolbox.findSelectedComponent();
				  		if(!$component[0]){
				  			return;
				  		}
						  var inputVal=$(this).val();
						 var dataRole;
						  try {
							dataRole=toolbox.getDataRole();
							if(typeof ctrImpl == "string"){
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
							ctrImpl.call(this,$component,inputVal); 
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
			// if(currentComponent.attr("data-rule")){
			// 	value = JSON.parse(currentComponent.attr("data-rule"))[key];
			// }
			inputDom.val(value);
			inputDom.change(function(){
				var $component = toolbox.findSelectedComponent();
				  		if(!$component[0]){
				  			return;
				  		}
						  var inputVal=$(this).val();
						  var dataRole;
						  try {
							dataRole=toolbox.getDataRole();
							if(typeof ctrImpl == "string"){
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
							ctrImpl.call(this,$component,inputVal); 
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
			// if(currentComponent.attr("data-rule")){
			// 	value = JSON.parse(currentComponent.attr("data-rule"))[key];
			// }
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
							if(typeof ctrImpl == "string"){
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
			hidden.val(value);
			$template = $(template).append(hidden);
			return $template;
		}
	};
})(jQuery);