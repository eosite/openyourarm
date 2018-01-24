package com.glaf.platforms.rule.model.bootstrap;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glaf.form.core.domain.FormComponent;
import com.glaf.form.core.domain.FormPage;
import com.glaf.form.core.domain.FormRule;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class TabsModel extends SourceModel implements IRule, CssRule, AttrRule {
	private static final long serialVersionUID = 1L;

	protected FormPage formPage;

	public void setFormPage(FormPage formPage) {
		this.formPage = formPage;
	}

	@Override
	public String getBind() {
		return null;
	}

	@Override
	public String getWidth() {
		return source.get("width");
	}

	@Override
	public String getHeight() {
		return source.get("height");
	}

	@Override
	public void setSource(Map<String, String> source) {
		this.source = source;
	}

	@Override
	public void setFormRules(List<FormRule> formRules) {

	}

	@Override
	public void setFormComponents(List<FormComponent> formComponents) {

	}

	@Override
	public String getElementTagName() {
		return "div";
	}

	/**
	 * 导航位置
	 * <p>
	 * 上方tabsTop；下方tabsBelow；左边tabsLeft；右边tabsRight
	 * 
	 * @return
	 */
	public String getNavPosition() {
		return getStringValue("navPosition", "tabsTop");
	}

	/**
	 * 样式。
	 * <p>
	 * 默认nav-tabs；紧凑nav-pills；
	 * 
	 * @return
	 */
	public String getNavStyle() {
		return getStringValue("navStyle", "nav-tabs");
	}

	/**
	 * 是否反向
	 * 
	 * @return
	 */
	public boolean isDirection() {
		return getBooleanValue("isDirection", false);
	}

	/**
	 * 导航占用列，默认为3列
	 * 
	 * @return
	 */
	public int getNavColumn() {
		return getIntValue("navColumn", 3);
	}

	@Override
	public String getElementHtml() {
		String formHtml = formPage.getFormHtml();

		Document doc = Jsoup.parse(formHtml);

		// 最外层div,配置了data-role="tabs"
		Element ele = doc.getElementById(this.getId());
		
		if (StringUtils.equals(this.getNavPosition(), "tabsBelow")) {
			ele.addClass(this.getNavPosition());// 设置位置
		} else if (StringUtils.equals(this.getNavPosition(), "tabsTop")) {
			ele.addClass("");// div不设置值时，为上方
		}
		
		// 获取<ul>
		Elements ul = ele.getElementsByTag("ul");
		ul.addClass("nav");
		ul.addClass(this.getNavStyle());
		if (this.isDirection()) {
			ul.addClass("tabs-reversed");// 反向
		}

		if (StringUtils.equals(this.getNavPosition(), "tabsLeft")
				|| StringUtils.equals(this.getNavPosition(), "tabsRight")) {
			//在左边或右边时
			ul.addClass(this.getNavPosition());
			
			int navColumn = getNavColumn();
			int contentColumn = 12-getNavColumn();
			Element rowEle = ele.appendElement("div").addClass("row");
			Element navColDivEle = rowEle.appendElement("div");
			navColDivEle.addClass("col-md-" + navColumn);
			navColDivEle.addClass("col-sm-" + navColumn);
			navColDivEle.addClass("col-xs-" + navColumn);
			
			Element contentColDivEle = rowEle.appendElement("div");
			contentColDivEle.addClass("col-md-" + contentColumn);
			contentColDivEle.addClass("col-sm-" + contentColumn);
			contentColDivEle.addClass("col-xs-" + contentColumn);
			
			
		}

		

		String html = ele.outerHtml();

		return html;
	}

	@Override
	public String getRuleId() {
		return null;
	}

	@Override
	public String getId() {
		return source.get("id");
	}

	@Override
	public String getName() {
		return source.get("name");
	}

	@Override
	public String getValue() {
		return null;
	}

	@Override
	public boolean isReadable() {
		return false;
	}

	@Override
	public boolean isRequired() {
		return false;
	}

	@Override
	public boolean isWritable() {
		return false;
	}

}
