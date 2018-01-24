/**
 * 
 */

function dataRoleFilter(dataRole) {
	if (dataRole && (dataRole in {
		ztree : '',
		grid : '',
		button : '',
		splitter : '',
		label : '',
		mtbutton : '',
		form : '',
		div : '',
		tabstrap : '',
		contextmenu : '',
		tabstrip : '',
		selectable : '',
		popup : '',
		staticlist : '',
		bootstrap_tabs : ''
	}) || dataRole.indexOf("col-") > -1) {
		return true;
	}
	return false;
}