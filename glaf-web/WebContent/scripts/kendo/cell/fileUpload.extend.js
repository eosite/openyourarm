function fileUploadCell(rule) {
	if (rule) {
		var sc = $SC(rule, this);
		if (rule.maxFileSize) { // �Զ�������Դ
			sc.cellType.setMaxFileSize(rule.maxFileSize);
			initCellProperties(sc);
		} else {
			initCellProperties(sc);
		}
	}
};