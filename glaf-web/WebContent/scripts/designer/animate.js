function _setAnimation(animationType,animateName,looptimes,target,starttime,delaytime,setValFlag){
      return function()  
        { 
            setAnimation(animationType,animateName,looptimes,target,starttime,delaytime,setValFlag);  
        } 
}
function runAnimation(animationType,animateName,looptimes,target,starttime,delaytime,setValFlag){
	$(target).removeClass("animated "+$(target).attr("animate-name"));
	if(animateName!=""){
		$(target).css("animation-iteration-count",looptimes);
		$(target).css("animation-duration","0");
		$(target).css("animation-delay","0");
	}
	if(starttime!=undefined&&animateName!="")
	{
		$(target).css("animation-duration",starttime+"s");
	}else{
		starttime=0;
	}
	if(delaytime!=undefined&&animateName!="")
    {
		$(target).css("animation-delay",delaytime+"s");
	}else{
		delaytime=0;
	}
	$(target).attr("animate-name",animateName);
	if(animateName!=""){
	   $(target).addClass("animated "+animateName);
	}
	//动画结束后执行
    $(target).one("webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend",function(){
		$(this).removeClass(animateName);
		$(this).removeClass("animated");
		if(animationType=='in'){
			animationType="continue";
		}else if(animationType=='continue'){
			animationType="out";
		}else if(animationType=='out'){
			//$(target).css("animation-fill-mode","none");
			return;
		}
		    var animateRule=JSON.parse($(this).attr("animate-"+animationType));
			var nlooptimes=animateRule["looptimes"];
			var nstarttime=animateRule["continueTime"];
			var ndelaytime=animateRule["delayTime"];
			var nanimateName=animateRule["animationName"];
			runAnimation(animationType,nanimateName,nlooptimes,this,nstarttime,ndelaytime);
        });
}
function setAnimation(animationType,animateName,looptimes,target,starttime,delaytime,setValFlag){
	$(target).removeClass("animated "+$(target).attr("animate-name"));
	if(animateName!=""){
		$(target).css("animation-iteration-count",looptimes);
		$(target).css("animation-duration","0");
		$(target).css("animation-delay","0");
	}
	if(setValFlag&&setValFlag==true){
		if($(this).hasClass("animation-selected")){
		$(this).removeClass("animation-selected");
		animateName="";
	}else{
		$(".animation-selected").removeClass("animation-selected");
	    $(this).addClass("animation-selected");
	}
	    setAnimationRule(animationType,"animationName",animateName,target);
		setAnimationRule(animationType,"continueTime",starttime,target);
		setAnimationRule(animationType,"delayTime",delaytime,target);
		setAnimationRule(animationType,"looptimes",looptimes,target);
	}
	if(starttime!=undefined&&animateName!="")
	{
		$(target).css("animation-duration",starttime+"s");
	}else{
		starttime=0;
	}
	if(delaytime!=undefined&&animateName!="")
    {
		$(target).css("animation-delay",delaytime+"s");
	}else{
		delaytime=0;
	}
	$(target).attr("animate-name",animateName);
	if(animateName!=""){
	   $(target).addClass("animated "+animateName);
	}
	//动画结束后执行
    $(target).one("webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend",function(){
			$(this).removeClass(animateName);
			$(this).removeClass("animated");
			if(animationType=='in'){
			animationType="continue";
		}else if(animationType=='continue'){
			animationType="out";
		}else if(animationType=='out'){
			//$(target).css("animation-fill-mode","none");
			return;
		}
		    var animateRule=JSON.parse($(this).attr("animate-"+animationType));
			var nlooptimes=animateRule["looptimes"];
			var nstarttime=animateRule["continueTime"];
			var ndelaytime=animateRule["delayTime"];
			var nanimateName=animateRule["animationName"];
			setAnimation(animationType,nanimateName,nlooptimes,target,nstarttime,ndelaytime);
    });
		//通过setTimeout实现多动画连播
		/*if(animationType=='in'){
			animationType="continue";
		}else if(animationType=='continue'){
			animationType="out";
		}else if(animationType=='out'){
			$(target).css("animation-fill-mode","none");
			return;
		}
		    var animateRule=JSON.parse(currentComponent.attr("animate-"+animationType));
			var nlooptimes=animateRule["looptimes"];
			var nstarttime=animateRule["continueTime"];
			var ndelaytime=animateRule["delayTime"];
			var nanimateName=animateRule["animationName"];
			setTimeout(_setAnimation(animationType,nanimateName,nlooptimes,target,nstarttime,ndelaytime),(0.2+parseInt(starttime)*parseInt(looptimes)+parseInt(delaytime))*1000);
			*/
}

function setAnimationRule(animationType,ruleType,val,target){
	var animateJSON={};
	if(target.attr("animate-"+animationType)){
	   animateJSON = JSON.parse(target.attr("animate-"+animationType));
	}
	animateJSON[ruleType]=val;
	target.attr("animate-"+animationType,JSON.stringify(animateJSON));
}
function getAnimationRule(animationType,ruleType,target){
	var value ="";
	if(target.attr("animate-"+animationType)){
		value = JSON.parse(target.attr("animate-"+animationType))[ruleType];
	}	
	return value;
}

function setContinueTime(target,val){
	//获取动画编排分类（入场、持续、退场）
	var animationType=getAnimationType();
	setAnimationRule(animationType,"continueTime",val,target);
}

function getAnimationType(){
	var animationType=$(".animateBtn.selectedType").attr("value");
	return animationType;
}
function setDelayTime(target,val){
	//获取动画编排分类（入场、持续、退场）
	var animationType=getAnimationType();
	setAnimationRule(animationType,"delayTime",val,target);
}

function setLooptimes(target,val){
	//获取动画编排分类（入场、持续、退场）
	var animationType=getAnimationType();
	setAnimationRule(animationType,"looptimes",val,target);
}