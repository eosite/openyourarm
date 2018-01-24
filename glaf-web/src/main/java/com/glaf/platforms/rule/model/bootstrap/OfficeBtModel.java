package com.glaf.platforms.rule.model.bootstrap;

import com.glaf.platforms.rule.model.OfficeModel;

public class OfficeBtModel extends OfficeModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getTemplateScript() {
		StringBuffer style = new StringBuffer(
				"<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/officecontrol/StyleSheet.css'>");
		//style.append("<script src='${contextPath}/scripts/uuid.js' type='text/javascript'></script>");
		//style.append(
		//		"<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js'></script>");
		style.append(
				"<script src='${contextPath}/scripts/officecontrol/OfficeContorlFunctions.js' type='text/javascript'></script>");
		scriptMap.put("office", style.toString());

		return this.templateScript;
	}

	@Override
	protected String getTmp(String str) {
		return str;
	}
}
