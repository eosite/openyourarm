
$(function(){
	
 	$("div[data-role=touchspin]").each(function(i,o){
 		var $this = $(this);
 		$this.find("input").TouchSpin({
            initval: 0
        });
	});

});