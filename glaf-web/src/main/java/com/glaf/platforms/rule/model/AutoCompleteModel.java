package com.glaf.platforms.rule.model;

import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class AutoCompleteModel extends ComboboxModel implements IRule, CssRule,
		AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getElementTagName() {
		return "input";
	}
}
