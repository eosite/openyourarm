var createComponent = function (dataRole){
	    if ($('#' + dataRole,window.parent.document)&& $('#' + dataRole,window.parent.document).html()) {
            targetElem = $($('#' + dataRole,window.parent.document).html());
        }else {
            targetElem = $("<img class='img-responsive'></img>");
            targetElem.attr("src",contextPath+"/images/component/"+ dataRole + ".png");
            targetElem.css("vertical-align", "middle");
        }
        var dtStr = new Date().getTime();
        var id = dataRole + "_" + dtStr;
        targetElem.attr("id", id);
        targetElem.attr("data-role", dataRole);
        targetElem.attr("cname", id+'_控件');
        targetElem.attr("scope","bootstrap");
        targetElem.attr("crtltype", "kendo");
        targetElem.attr("render","new");
        targetElem.addClass(id);
        targetElem.addClass("nlayout_elem");
        //元素内容可编辑
        targetElem.attr("contenteditable","true");
        //替换class中的id
        var retain = targetElem.find('.id');
        if(retain.length>0){
        	$.each(retain,function(i,o){
            	var $id = $(o).closest('[data-role]');
            	if($id.length>0&&$id.attr("data-role")==dataRole){
            		$(o).addClass(targetElem.attr("id"));
            		$(o).removeClass("id");
            	}
            });
        }
        //子控件重新生成ID
		var childComp=targetElem.find("[data-role]");
		var dtStr,nId;
		$.each(childComp,function(i,item){
			dtStr = new Date().getTime()+i;
		    nId=$(item).attr("data-role")+"_"+dtStr;
		    $(item).attr("id",nId);
		    //替换class中的id
		    var childRetain = $(item).find('.id');
		    if(childRetain.length>0){
		    	$.each(childRetain,function(i,o){
	            	var $id = $(o).closest('[data-role]');
	            	if($id.length>0&&$id.attr("data-role")==$(item).attr("data-role")){
	            		$(o).addClass($(item).attr("id"));
	            		$(o).removeClass("id");
	            	}
	            });
		    }else{
		    	return;
		    }
		});
		var template = targetElem[0].outerHTML;
        template = $.base64.encode(template);
        return {'id':id,'template':template};
}