var stepFunc = {
	init : function(rule, args) {
		var options = args[0];
		stepFunc.display.call($("#"+rule.inid),options.visible);
		if(options.fixDataSource.length!=0){
			stepFunc.createEle.call($("#"+rule.inid),options,options.fixDataSource);
		}else{
			$.ajax({
				type : "POST",
				url : contextPath + "/mx/form/data/gridData",
				contentType : "application/json",
				dataType : "json",
				data : JSON.stringify({'rid' : options.rid}),
				success : function(ret) {
					stepFunc.createEle.call($("#"+rule.inid),options,ret.data);
				}
			});
		}
		
	},
	createEle:function(opts,data){
		if(data.length!=0){
			var that = this;
			var container = that.find("div.step-line").html("");
			$.each(data,function(i,o){
				
				var $col= $('<div class="step mt-step-col"></div>');
				if(i==0){
					$col.addClass("first");
				}else if(i==data.length-1){
					$col.addClass("last");
				}
				$col.css("width",100/data.length+"%");
				$col.data("step",data[i]);
				container.append($col);	
//				
//				if(opts.fixDataSource.length!=0){//如果使用固定数据集则不应用自定义规则
//					if(data[i].number){
//						$col.append('<div class="mt-step-number bg-white">'+data[i].number+'</div>');
//					}else{
//						$col.append('<div class="mt-step-number bg-white">'+(i+1)+'</div>');				
//					}
//					if(data[i].content){
//						$col.append('<div class="mt-step-title uppercase font-grey-cascade">'+data[i].content+'</div>');
//					}else{
//						$col.append('<div class="mt-step-title uppercase font-grey-cascade"></div>');				
//					}
//				}else{
					if (opts.defined&&opts.defined.length) {//应用自定义规则，假如成立就添加元素
						var defineds = opts.defined;
						for (var j = 0; j < defineds.length; j++) {
							var defined = defineds[j];
							key = stepFunc._convert(defined.key, data[i], 'key');
							if (eval(key) || key == "true") {
								if(defined.numberVal){
									$col.append('<div class="mt-step-number bg-white">'+stepFunc._convert(defined.numberVal,data[i])+'</div>');
								}else{
									$col.append('<div class="mt-step-number bg-white">'+(i+1)+'</div>');
								}
								$col.append('<div class="mt-step-title uppercase font-grey-cascade">'+stepFunc._convert(defined.contentVal,data[i])+'</div>');
								return;
							}
						}
					}
					if($col.find("div.mt-step-number").length==0&&data[i].number){//如果自定义不成立，添加默认
						$col.append('<div class="mt-step-number bg-white">'+data[i].number+'</div>');
					}else{						
						$col.append('<div class="mt-step-number bg-white">'+(i+1)+'</div>');
					}
					if($col.find("div.mt-step-title").length==0&&data[i].content){
						$col.append('<div class="mt-step-title uppercase font-grey-cascade">'+data[i].content+'</div>');
					}else{
						$col.append('<div class="mt-step-title uppercase font-grey-cascade"></div>');
					}
//				}

			});
		}
		
	},
	_convert: function(k, v, type) {
		var par, val, reger, tv = (type == 'key' ? "'" : "");
		if (v) {
			for (var p in v) {
				if(v[p] ){
					var kt = p.split("_0_")[1]|| p;
					reger = new RegExp("##" + kt + "##", "gm");
					k = k.replace(reger, tv + v[p] + tv);
				}
			}
		}
		return k.replace(/##/gm, "'").replace(/\\"/gm, "'").replace(/##col\w*##/gm, type == 'key' ? "''" : "");
	},
	display : function(bl){
		var that = this;
		if (!bl){    
			that.css("display", "none");
		}else{
			that.css("display", "");
		}
	},
	
	thidden: function(rule, args) { // 隐藏
		var $id = pubsub.getJQObj(rule);
		stepFunc.display.call($id,false);
	},
	tshow: function(rule, args) { // 显示
		var $id = pubsub.getJQObj(rule);
		stepFunc.display.call($id,true);
	},
	getValue : function(rule, args) {
		
	},
	setValue : function(rule, args){
		
	},
	getData:function(rule, args){
		return args[0][rule.columnName];
	},
	active:function(rule, args){
		var $id = pubsub.getJQObj(rule);
		var v = stepFunc.getParam(rule, args);
		var steps = $id.find("div.mt-step-col");
		steps.removeClass("done active error");
		for (var j = 0; j < steps.length; j++) {
			var data = $(steps[j]).data("step");
			$(steps[j]).addClass("done");
			if(data[rule[0].columnName]==v||data.index==v){
				$(steps[j]).removeClass("done");
				$(steps[j]).addClass("active");
				return;
			}
		}
	},
	error:function(rule, args){
		var $id = pubsub.getJQObj(rule);
		var v = stepFunc.getParam(rule, args);
		var steps = $id.find("div.mt-step-col");
		steps.removeClass("done active error");
		for (var j = 0; j < steps.length; j++) {
			var data = $(steps[j]).data("step");
			$(steps[j]).addClass("done");
			if(data[rule[0].columnName]==v||data.index==v){
				$(steps[j]).removeClass("done");
				$(steps[j]).addClass("error");
				return;
			}
		}
	},
	getParam:function(rule, args){
		var r, v = "";
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param]||"";
				if(v){
					break;
				}
			}
		}
		return v;
	}
};
pubsub.sub("step", stepFunc);