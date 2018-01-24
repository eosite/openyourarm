package com.glaf.platforms.rule.model.bootstrap;

import com.glaf.platforms.rule.model.CommonModel;

public class BootstrapTabsModel extends CommonModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String templateScript = "";

	// 增加模板
	/*public String getTemplateScript() {
		
		String script = "<link rel='stylesheet/less' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/tabs/style/tabs.css' />"
		                + "<script type='text/javascript' src='${contextPath}/scripts/jqueryui/jquery-ui.min.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/tabs/js/jquery.bootstraptabs.extends.js'></script>";

		scriptMap.put("bootstraptabs", script);
		return templateScript;
	}*/
	public String getElementHtml() {
		return null;
	}

	public String getElementTagName() {
		return null;
	}

	public String getFoldAble() {
		return source.getOrDefault("foldAble", "false");
	}

}
