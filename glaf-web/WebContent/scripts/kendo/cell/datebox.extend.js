function dateboxCell(rule) {
	if (rule) {
		var $target = $(this._containerDiv).parents("[data-role=cell]"),
			eventsRule = $target.data("eventsRule");
		if (eventsRule) {
			var event = eventsRule[rule.id];
			if (event) {
				var spread = $target.data("spread"),
					sheet = spread.getActiveSheet(),
					ids = rule.id.split("-"),
					cell = sheet.getCell(ids[0],ids[1]);
				//$SC(rule, this).bind('click', event);
			}
		}
	}
};