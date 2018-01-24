/**
 * textboxbt
 */
var form_designer = {
	setNotWrap:function(component,val){
		if(val && val == 'yes'){
			component.css("display","inline-flex");
			component.addClass("mustInlineForm");
		}else{
			component.css("display","");
		}
	}
	
}
