var metro_destroy = {
	destroyAll: function(){
		//iCheck destroy
		$('[data-role=icheckbox] input').iCheck('destroy');
		$('[data-role=icheckradio] input').iCheck('destroy');

		//slimScroll destroy
		var $scroller = $('[data-role=portlet] .scroller');
		if($scroller.attr('data-initialized')=='1'){
			$scroller.removeAttr('data-initialized');
			$scroller.slimScroll({destroy:true});
		}

		//select2 destroy
		try{
			metro_destroy.destroySelect2();
		}catch(e){
			metro_restore.initSelect2();
			metro_destroy.destroySelect2();
		}
		
		//gantt destroy
		var gantts = $('[data-role=gantt]');
		$.each(gantts, function(i, o) {
			$(o).gantt('destroy');
		});
	},
	destroySelect2: function(){
		var select2s = $('select.select2, select.select2-multiple');
		$.each(select2s, function(i, o) {
			 $(o).select2('destroy');
		});
	}
};