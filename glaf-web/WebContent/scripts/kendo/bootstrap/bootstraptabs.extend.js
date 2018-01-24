/**
 * 
 */
var bootstraptabsFunc = {
	activeTab:function(rule, args){
		var $id = pubsub.getJQObj(rule),r, v = "";
		var $a = $id.find('a[data-toggle="tab"]');
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
				$.each($a,function(j,o){
					if($(o).find("span").html()==v||j==v){
						setTimeout(function(){
							$(o).tab('show');
						},100);
						// $(o.hash).show();
					}
				});
			}
		} 
	},
	showTab : function(rule, args){
		var $tabs = pubsub.getJQObj(rule),
			param = "";
		$.each(rule,function(i,r){
			param = args[r.param]+"";
		});
		var params = param.split(",");
		$.each(params,function(i,p){
			$tabs.tabs("show",$.isNumeric(p)?parseInt(p):p);
		});
	},
	hideTab : function(rule, args){
		var $tabs = pubsub.getJQObj(rule),
			param = "";
		$.each(rule,function(i,r){
			param = args[r.param]+"";
		});
		var params = param.split(",");
		$.each(params,function(i,p){
			$tabs.tabs("hide",$.isNumeric(p)?parseInt(p):p);
		});
	},
	setTabName : function(rule,args){
		var $tabs = pubsub.getJQObj(rule);
		var $li = $tabs.find(".nav.nav-tabs li");
		var $span;
		$.each($li,function(i,item){
			if(args && args[i] != null){
				$span = $(item).find("span");
				$.each($span,function(y,span){
					$(span).attr("class") != undefined ? null : $(span).text(args[i]);
				});
				if(!$span[0]){
					$(item).find(">a").text(args[i]);
				}
			}
		});
	},
	setTabStatus : function(rule,args){
		var $tabs = pubsub.getJQObj(rule);
		var $li = $tabs.find(".nav.nav-tabs li");
		var status = null;
		$.each(args,function(k,v){
			status = v;
		})
		if(status != null){
			status = parseInt(status);
			$($li[status]).find("a:first-child").trigger("click");
		}
	},
	getActivedValue : function(rule,args){
		var $tabs = pubsub.getJQObj(rule,true);
		return $tabs.find("li.active:first>a").text();
	},
	getActivedIndex : function(rule,args){
		var $tabs = pubsub.getJQObj(rule,true);
		var $li = $tabs.find(".nav.nav-tabs li");
		var index = -1;
		$.each($li,function(i,item){
			if($(item).hasClass('active')){
				index = i+1;
				return false;
			}
		})
		return index;
	},
	addUrlTab : function(rule,args){
		var value = "";
		$.each(rule,function(i,item){
			if(rule[i].param == 'title'){
				return true;
			}
			value = args[rule[i].param] || "";
		})
		var title = args.title;
		var $tabs = pubsub.getJQObj(rule);
		if(value){
			$tabs.tabs("add", {
	            title: title,
	            content: "",
	            minHeight: 200,
	            href:value
	        });	
	        // var $iframe = $('<iframe style="width: 100%; height: 100%;border-style:none;"></iframe>');
	        // $iframe.attr("src",value);
	        // $tabs.find(".tab-pane.active").empty().append($iframe);
		}
	}
};
pubsub.sub("bootstraptabs", bootstraptabsFunc);