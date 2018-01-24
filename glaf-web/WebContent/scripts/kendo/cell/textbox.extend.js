function textboxCell(rule) {
	if (rule) {
		var sc = $SC(rule, this);
		if (rule.quote) {//获取引用数据
			rule.quote.id = sc.spread.getHost().getAttribute("id");
			sc.getQuoteDatas(rule.quote, function(data) {
				sc.cell.value(data);
				initCellProperties(sc);
			});
		} 
	}
};