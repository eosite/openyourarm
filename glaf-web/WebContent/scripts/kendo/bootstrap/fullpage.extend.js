var fullpageFunc={
	movesectionUp:function(){
		$.fn.fullpage.moveSectionUp();
	},
	movesectionDown:function(){
		$.fn.fullpage.moveSectionDown();
	},
	moveSlideLeft:function(){
		$.fn.fullpage.moveSlideLeft();
	},
	moveSlideRight:function(){
		$.fn.fullpage.moveSlideRight();
	}
};
pubsub.sub("fullpage", fullpageFunc);