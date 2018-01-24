var btalert_designer = {
	/**
	 * 设置控件名称
	 */
	setName: function (component, val){
		if(val!==''){
			component.attr("cname", val);
		}
	},
	setAriaHidden: function (component, val){
		component.find('[data-dismiss="alert"]').attr('aria-hidden', val);
	}
};