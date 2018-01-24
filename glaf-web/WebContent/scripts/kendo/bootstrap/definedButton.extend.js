
var definedButtonFunc = {
		binding: function(rule, args,options){
			debugger;
			var $id = pubsub.getJQObj(rule),r, v = "";
			var ids = rule.outid;
			if(!ids){
				ids = rule[0].outid;
			}
			var idd = rule.trigger.eleId
			var htmled = $('#'+idd).clone();
			var place =options[0].promptmessage;
			var tile =options[0].titile;
			if(!tile){
				tile = args.title;
			}
			htmled.attr("id","");
			if(tile){
				$('#'+ids).tooltip({title:tile,placement:place,html:true,template:htmled});
			}else {
				htmled.find('.tooltip-inner').remove();
				$('#'+ids).tooltip({title:"z",placement:place,html:true,template:htmled});
			}
			
		},
		setValue : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			$.each(args,function(i,arg){
				$id.find("span.frame-variable")[0].innerHTML = arg;
			});
			
		},
		//添加按钮
		copyMtButon : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			var content = $id.parent().parent();
			var div = $id.parent().parent().parent();
			
			/*
			$id.closest("div").append($id[0].outerHTML);*/
			$.each(args,function(i,arg){
				if(arg.toString().indexOf(",") != -1){
					var arr = arg.toString().split(",");
					for(var z=0;z<arr.length;z++){
						content.before(content[0].outerHTML);
						$(div.find("div.row")[z]).css("display","block");
						$(div.find("div.row")[z]).find("span")[0].innerHTML = arr[z];
						var $closeSpan = $('<span>').addClass('ui-icon ui-icon-close');
						$closeSpan[0].innerHTML = 'x';
						var $closeDIV = $(div.find("div.row")[z]);
						$closeSpan.attr({
							'style': 'cursor:pointer;float:right;position:absolute;top:2px;right:8px;color:#66C8F2;font-weight: bolder;font-size: 12px;display:none'
						}).bind('click', function(event) {
							$closeDIV.remove();
						});
						$closeSpan.appendTo($closeDIV);
						$closeDIV.mouseover(function(){
							$closeDIV.on('mouseover', function(e){
								$closeDIV.find("span.ui-icon-close").css("display","block");
		                	});
			            });
						$closeDIV.mouseout(function(){
							$closeDIV.on('mouseout', function(e){
								$closeDIV.find("span.ui-icon-close").css("display","none");
			                	});     
			                    // OnMouseUp Code in here
			            });  
					}
				}
				else{
					content.before(content[0].outerHTML);
					$(div.find("div")[0]).css("display","block");
					div.find("div").find("span")[0].innerHTML = arg;
					var $closeDIV = $(div.find("div")[0]);
					var $closeSpan = $('<span>').addClass('ui-icon ui-icon-close');
					$closeSpan[0].innerHTML = 'x';
					$closeSpan.attr({
						'style': 'cursor:pointer;float:right;position:absolute;top:2px;right:10px;color:#66C8F2;font-weight: bolder;font-size: 12px;display:none'
					}).bind('click', function(event) {
						$closeDIV.remove();
					});
					$closeSpan.appendTo($closeDIV.find("div"));
					$closeDIV.find("div").mouseover(function(){
						$closeDIV.find("div").on('mouseover', function(e){
							$closeDIV.find("div").find("span.ui-icon-close").css("display","block");
	                	});
	                    
		            });
					$closeDIV.find("div").mouseout(function(){
						$closeDIV.find("div").on('mouseout', function(e){
							$closeDIV.find("div").find("span.ui-icon-close").css("display","none");
		                	});     
		                    // OnMouseUp Code in here
		                
		            });  
				}
				
			})
		},
		//添加按钮
		copyMtButon : function(rule,args){
			var $id = pubsub.getJQObj(rule);
			var content = $id.parent().parent();
			var div = $id.parent().parent().parent();
			
			/*
			$id.closest("div").append($id[0].outerHTML);*/
			$.each(args,function(i,arg){
				if(arg.toString().indexOf(",") != -1){
					var arr = arg.toString().split(",");
					for(var z=0;z<arr.length;z++){
						content.before(content[0].outerHTML);
						$(div.find("div.row")[z]).css("display","block");
						$(div.find("div.row")[z]).find("span")[0].innerHTML = arr[z];
						var $closeSpan = $('<span>').addClass('ui-icon ui-icon-close');
						$closeSpan[0].innerHTML = 'x';
						var $closeDIV = $(div.find("div.row")[z]);
						$closeSpan.attr({
							'style': 'cursor:pointer;float:right;position:absolute;top:2px;right:8px;color:#66C8F2;font-weight: bolder;font-size: 12px;display:none'
						}).bind('click', function(event) {
							$closeDIV.remove();
						});
						$closeSpan.appendTo($closeDIV);
						$closeDIV.mouseover(function(){
							$closeDIV.on('mouseover', function(e){
								$closeDIV.find("span.ui-icon-close").css("display","block");
		                	});
			            });
						$closeDIV.mouseout(function(){
							$closeDIV.on('mouseout', function(e){
								$closeDIV.find("span.ui-icon-close").css("display","none");
			                	});     
			                    // OnMouseUp Code in here
			            });  
					}
				}
				else{
					content.before(content[0].outerHTML);
					$(div.find("div")[0]).css("display","block");
					div.find("div").find("span")[0].innerHTML = arg;
					var $closeDIV = $(div.find("div")[0]);
					var $closeSpan = $('<span>').addClass('ui-icon ui-icon-close');
					$closeSpan[0].innerHTML = 'x';
					$closeSpan.attr({
						'style': 'cursor:pointer;float:right;position:absolute;top:2px;right:10px;color:#66C8F2;font-weight: bolder;font-size: 12px;display:none'
					}).bind('click', function(event) {
						$closeDIV.remove();
					});
					$closeSpan.appendTo($closeDIV.find("div"));
					$closeDIV.find("div").mouseover(function(){
						$closeDIV.find("div").on('mouseover', function(e){
							$closeDIV.find("div").find("span.ui-icon-close").css("display","block");
	                	});
	                    
		            });
					$closeDIV.find("div").mouseout(function(){
						$closeDIV.find("div").on('mouseout', function(e){
							$closeDIV.find("div").find("span.ui-icon-close").css("display","none");
		                	});     
		                    // OnMouseUp Code in here
		                
		            });  
				}
				
			})
		},
		bindpopover: function(rule, args,options){
			var $id = pubsub.getJQObj(rule),r, v = "";
			var ids = rule.outid;
			if(!ids){
				ids = rule[0].outid;
			}
			var idd = rule.trigger.eleId
			var htmled = $('#'+idd);
			var place =options[0].promptmessage;
			var tile =options[0].titile;
			if(!tile){
				tile = args.title;
			}
			var cont =options[0].content;
			if(!cont){
				content = args.content;
			}
			htmled.attr("id","");
			!tile && htmled.find(".popover-title").remove();
			!cont && htmled.find(".popover-content").remove();
			$('#'+ids).popover({title:tile?tile:"x",content:cont?cont:"x",placement:place,html:true,template:htmled});
			
		},
	
};
pubsub.sub("definedButton", definedButtonFunc);