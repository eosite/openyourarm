package com.glaf.platforms.rule.model.bootstrap;

import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.glaf.form.core.domain.FormComponent;
import com.glaf.form.core.domain.FormPage;
import com.glaf.form.core.domain.FormRule;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class AccordionModel extends SourceModel implements IRule, CssRule, AttrRule {
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
		return null;
	}

	@Override
	public String getElementHtml() {
		String formHtml = formPage.getFormHtml();

		Document doc = Jsoup.parse(formHtml);

		Element ele = doc.getElementById(this.getId());

		

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
