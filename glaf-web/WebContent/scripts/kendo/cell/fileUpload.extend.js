function fileUploadCell(rule) {
	if (rule) {
		var sc = $SC(rule, this);
		if (rule.maxFileSize) { // 自定义数据源
			sc.cellType.setMaxFileSize(rule.maxFileSize);
			initCellProperties(sc);
		} else {
			initCellProperties(sc);
		}
	}
};