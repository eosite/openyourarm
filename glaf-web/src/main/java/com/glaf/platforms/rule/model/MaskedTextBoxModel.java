package com.glaf.platforms.rule.model;

import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class MaskedTextBoxModel extends CommonModel implements IRule, CssRule,
		AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getBind() {
		return null;
	}

	public String getElementTagName() {
		return "input";
	}

	public String getCls() {
		return null;
	}

}
