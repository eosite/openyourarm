var ratylimasterFunc = {
	getValue : function(rule, args){
	   var $id = pubsub.getJQObj(rule, true),
	       build = $id.data("ratylimaster"),
	       el = build.el;  
	   return $(el).find('.ratymode').attr('data-rate');
	   
	},
	
	setValue : function(id, args){
	   var $id = pubsub.getJQObj(id[0].outid, true),
	       build = $id.data("ratylimaster"),
	       emp = '☆',
	       full = '★',
	       el = build.el;  
	  switch (build.option.state) {
		case 'xingxing':
			emp = '☆';
			full = '★';
			break;
		case 'radius':
			emp = 'O';
			full = 'X';
			break;	
		case 'agree':
			emp = '<i class="fa fa-thumbs-o-up"></i>';
			full = '<i class="fa fa-thumbs-up"></i>';
			break;
		case 'people':
			full = "<i class='fa fa-male' aria-hidden='true'></i>";
			emp = "<i class='fa fa-male' aria-hidden='true'></i>";
			break;
		default:
			emp = '☆';
		    full = '★';
			break;
		}
	  var rule = el.find(".ratymode").attr("rule");
	  if(rule != undefined){
			rule = eval("(" + rule + ")");
			if(rule.icon != undefined){
				full = "<i class='"+ rule.icon+"'></i>";
				emp = "<i class='"+ rule.icon+"'></i>";
			} 
			if(rule.iconsize != undefined){
				el.find(".ratymode").css("font-size",rule.iconsize);
			}
	   }
	   $.each(args,function(i,arg){
		   arg = arg != undefined && arg != null ? arg : 0;
		   $(el).find('.ratymode').attr('data-rate',arg);
		   $(el).find('.ratymode').empty();
		   for(var raty = 0;raty<$(el).find('.ratymode').attr('data-ratemax');raty++){
			   var content = '<span class="rate rate-full" style="cursor:default;" style="color:#fe5845">'+full+'</span>';
			   if(raty >  Math.round(parseFloat(arg))-1){
				   content = '<span class="rate rate-empty" style="cursor:default;" >'+emp+'</span>';
			   }
			   $(el).find('.ratymode').append(content);
			   var emptyColor = $(el).find('.ratymode').attr("empty-color");
				var fullColor = $(el).find('.ratymode').attr("full-color");
				emptyColor != undefined ? ($(el).find('span.rate-empty').css("color",emptyColor)) : undefined;
				fullColor != undefined ? ($(el).find('span.rate-full').css("color",fullColor)) : undefined;
		   }
	   });
	   
	   
	},
	stopClick : function(rule,args){
		var $id = pubsub.getJQObj(rule.outid,true),
		    build = $id.data("ratylimaster"),
		    el = build.el;  
		$(el).find(".ratymode").unbind();
		
	}
};
pubsub.sub("ratylimaster", ratylimasterFunc);
