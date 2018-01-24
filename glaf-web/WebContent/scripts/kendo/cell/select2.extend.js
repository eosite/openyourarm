function select2Cell(rule) {
	if (rule) {
		var sc = $SC(rule, this);
		if (rule.customDatas) { // 自定义数据源
			sc.cellType.items(rule.customDatas);
			//initCellProperties(sc);
		} else {
			//initCellProperties(sc);
		}
	}
};