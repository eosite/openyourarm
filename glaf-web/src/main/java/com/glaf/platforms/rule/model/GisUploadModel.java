package com.glaf.platforms.rule.model;

import com.glaf.core.context.ApplicationContext;
import com.glaf.form.core.util.FormDictoryFactory;

public class GisUploadModel extends UploadModel {

	private static final long serialVersionUID = 1L;

	public String getSaveUrl() {
		String contextpath = ApplicationContext.getContextPath();
		String saveUrl = FormDictoryFactory.getInstance().getFormDictoryByCode("uploadGis").getValue();
		return contextpath + saveUrl + "&to=" + stringValue("saveOperation", "to_db");
	}
	private String stringValue(String key, String defaultValue) {
		return source.get(key) == null ? defaultValue : source.get(key);
	}
}
