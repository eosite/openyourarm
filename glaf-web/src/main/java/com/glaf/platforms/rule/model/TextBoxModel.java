package com.glaf.platforms.rule.model;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class TextBoxModel extends CommonModel implements IRule, CssRule, AttrRule {

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

	public boolean getOndblClick() {
		return Boolean.parseBoolean(super.getSource("dblclick-gis", "false"));
	}

	@Override
	public boolean isEnabled() {
		return Boolean.parseBoolean(this.source.get("enabled"));
	}

	@Override
	public String getElementHtml() {

		String formHtml = formPage.getFormHtml();

		Document doc = Jsoup.parse(formHtml);

		Element ele = doc.getElementById(super.getId());

		this.attrDblclick(ele);
		this.attrEnable(ele);
		this.attrVisible(ele);

		String html = ele.outerHtml();

		return html;
	}

	public String getDefaultVal() {
		return super.getSource("defaultVal", "");
	}

	private void attrDblclick(Element ele) {
		if (this.getOndblClick()) {
			ele.attr("ondblclick", "odblClick_(this)");
			ele.attr("dbrule", this.dbRules());
		} else if (!isNullOrEmpty(this.source.get("linkPage"))) {
			ele.attr("ondblclick", "odblClick_(this)");
			ele.attr("dbrule", this.dbRules());
			String selectPageStr = this.source.get("linkPage");
			if (!isNullOrEmpty(selectPageStr)) {
				JSONArray selectPageAry = JSONArray.parseArray(selectPageStr);
				if (selectPageAry != null && !selectPageAry.isEmpty()) {
					JSONObject obj = selectPageAry.getJSONObject(0);

					String url = obj.getString("url"), code;
					ele.attr("surl", url);
					if ((obj = obj.getJSONObject("node")) != null) {
						if (StringUtils.isNotEmpty(code = obj.getString("code"))) {
							ele.attr("select-type", code.split("_")[0]);
						}
					}
				}
			}
		}
	}

	private void attrEnable(Element ele) {
		if (!this.isEnabled()) {
			ele.attr("disabled", "disabled");
		}
	}

	private void attrVisible(Element ele) {
		if (!this.isVisible().equals("true")) {
			ele.attr("style", "display: none;" + ele.attr("style"));
		}
	}

	/**
	 * memory 记忆
	 * 
	 * @return
	 */
	public String getMemory() {
		return super.getSource("memory", "false");
	}

}
