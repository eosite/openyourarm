var rangeSliderFunc={
		setValue:function(rule, args){
			var t = pubsub.getJQObj(rule, false);
			var keys=new Array()
			for(key in args){
				keys.push(args[key]);
			}
			if(keys.length==1){
				var slider = $("#range").data("ionRangeSlider");
				var form=Number(keys[0]);
				
				slider.reset();
				slider.update({
				    from: form
				});
			}else{
				var slider = $("#range").data("ionRangeSlider");	
				var form=Number(keys[0]);
				var to=Number(keys[1]);
				slider.reset();
				slider.update({
				    from: form,
				    to:to
				});
			}
		},
		getRightValue:function(rule, args){
			//获取进度条的值
			var t = pubsub.getJQObj(rule, true);
			var value=t.find('.irs-to').text();
			return value; 
		},
		getLeftValue:function(rule, args){
			//获取进度条的值
			var t = pubsub.getJQObj(rule, true);
			//var value=t.find('.irs-from').text();
			var value=t.find('.irs-single').text();
			return value; 
		}
}

//把data-role与事件进行绑定
pubsub.sub("rangeSlider",rangeSliderFunc);