package com.glaf.platforms.rule.model.bootstrap;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class TouchSpinModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getBind() {
		return null;
	}

	public String getElementTagName() {
		return null;
	}

	// 默认值参数
	public String getDefaultVal() {
		return super.getSource("defaultVal", "");
	}

	// 最大值参数
	public String getMaxValue() {
		return super.getSource("maxValue", "");
	}

	// 最小值参数
	public String getMinValue() {
		return super.getSource("minValue", "");
	}

	// 保留小数位参数
	public String getDecimals() {
		return super.getSource("decimals", "");
	}

	// 步长参数
	public String getStep() {
		return super.getSource("step", "");
	}

	// 前缀
	public String getPrefix() {
		return super.getSource("prefix", "");
	}

	// 后缀
	public String getPostfix() {
		return super.getSource("postfix", "");
	}

	@Override
	public String getElementHtml() {

		String formHtml = formPage.getFormHtml();

		Document doc = Jsoup.parse(formHtml);

		Element ele = doc.getElementById(this.getId());

//		try {
//			ele.removeAttr("contenteditable"); // 不try 会出现乱七八糟的问题
//		} catch (Exception ex) {
//
//		}
		if ("false".equals(this.isVisible())) {
			ele.attr("style", "display:none;" + ele.attr("style"));
		}
		String html = ele.outerHtml();

		return html;
	}

	// @Override
	// public void setScriptMap(Map<String, String> scriptMap) {
	// this.scriptMap = scriptMap;
	// StringBuffer SB = new StringBuffer();
	// SB.append("<link
	// href=\"${contextPath}/scripts/plugins/bootstrap/touchspin/css/bootstrap.touchspin.min.css\"
	// rel=\"stylesheet\" type=\"text/css\" />");
	// SB.append("<script type=\"text/javascript\"
	// src=\"${contextPath}/scripts/plugins/bootstrap/touchspin/ext/jquery.touchspin.js\"></script>");
	// SB.append("<script type=\"text/javascript\"
	// src=\"${contextPath}/scripts/plugins/bootstrap/touchspin/ext/jquery.touchspin.extends.js\"></script>");
	// this.scriptMap.put("touchspin", SB.toString());
	// }

}
