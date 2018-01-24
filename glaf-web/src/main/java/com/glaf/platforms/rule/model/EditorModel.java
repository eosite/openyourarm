package com.glaf.platforms.rule.model;

import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class EditorModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String templateScript = "";

	// 增加模板
	public String getTemplateScript() {
		String script = "<script type='text/javascript' src='${contextPath}/scripts/ueditor/ueditor.config.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/ueditor/ueditor.all.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/webfile/js/jquery.editor.extends.js' ></script>";

		scriptMap.put("editor", script);
		return templateScript;
	}

	public void addTemplate(String subTemplate) {
		this.templateScript += subTemplate;
	}

	@Override
	public String getBind() {
		return null;
	}

	public String getElementTagName() {
		return "div";
	}

	@Override
	public String getWidth() {
		return super.getWidth().replace("px", "");
	}

	@Override
	public String getHeight() {
		return super.getHeight().replace("px", "");
	}
	
	public String getOWidth() {
		return super.getWidth();
	}

	public String getOHeight() {
		return super.getHeight();
	}

}
