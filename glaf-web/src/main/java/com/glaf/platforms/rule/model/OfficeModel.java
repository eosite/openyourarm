package com.glaf.platforms.rule.model;

import java.io.File;
import java.io.IOException;

import com.glaf.core.config.SystemProperties;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.mchange.io.FileUtils;

public class OfficeModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String templateScript = "";

	public String getElementTagName() {
		return null;
	}

	@Override
	public String getBind() {

		return null;
	}

	public String getIp() {
		return super.getSource("ip", "");
	}

	public String getTemplateScript() {
		StringBuffer style = new StringBuffer(
				"<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/officecontrol/StyleSheet.css'>");
		style.append(
				"<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/bootstrap/css/bootstrap.min.css'>");
		//style.append("<script src='${contextPath}/scripts/uuid.js' type='text/javascript'></script>");
		style.append(
				"<script src='${contextPath}/scripts/officecontrol/OfficeContorlFunctions.js' type='text/javascript'></script>");
		scriptMap.put("office", style.toString());
		return this.templateScript;
	}

	@Override
	public String getElementHtml() {
		String str = "";
		String path = SystemProperties.getConfigRootPath() + "/conf/templates/form/office.tmp";
		File file = new File(path);
		try {
			str = this.getTmp(filter(FileUtils.getContentsAsString(file, "UTF-8")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	protected String getTmp(String str) {
		return "<div class='container'><br>" + str + "</div>";
	}

	public String filter(String str) {

		Boolean visible = Boolean.parseBoolean(super.getSource("visible", "true"));

		return str.replace("officeRid", super.getRuleId()).replace("officeId", super.getId())
				.replace("OFFICE-OBJECT-NUM", (visible ? 10 : 12) + "").replace("OFFICE-OBJECT-KEY", visible + "")
				.replaceAll("OFFICE-HEIGHT", super.getHeight()).replaceAll("[\\t\\n\\r]", "");
	}

	public String getPageId() {
		return source.get("pageId");
	}

}
