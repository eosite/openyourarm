function comboboxCell(rule) {
	if (rule) {
		var sc = $SC(rule, this);
		if (rule.code) { // 获取字典数据
			sc.getDictoryByCode(rule.code, function(data) {
				var items = [];
				if (data && data.length) {
					$.each(data, function(i, v) {
						items.push(v.name);
					});
				}
				sc.cellType.items(items);
				initCellProperties(sc);
			});
		} if (rule.customDatas) { // 自定义数据源
			sc.cellType.items(rule.customDatas);
			//initCellProperties(sc);
		}else if (rule.quote) {//获取引用数据
			/*sc.getQuoteDatas(rule.quote, function(data) {
				var items = [];
				if (data && data.length) {
					$.each(data, function(i, v) {
						items.push(v.name);
					});
				}
				sc.cellType.items(items);
				initCellProperties(sc);
			});*/
		} else {
			initCellProperties(sc);
		}
	}
};