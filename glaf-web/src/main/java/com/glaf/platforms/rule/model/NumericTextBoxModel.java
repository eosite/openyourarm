package com.glaf.platforms.rule.model;

import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class NumericTextBoxModel extends MaskedTextBoxModel implements IRule,
		CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getMax() {
		return this.getDoubleVal("max", "99999999");
	}

	public String getMin() {
		return this.getDoubleVal("min", "-99999999");
	}

	public String getFormat() {
		return super.getSource("format", "c") + this.getDecimals();
	}

	public String getStep() {
		return this.getDoubleVal("step", "");
	}

	private String getDecimals() {
		return this.getDoubleVal("decimals", "");
	}

	private String getDoubleVal(String key, String defVal) {
		key = super.getSource(key, defVal);
		try {
			Double.parseDouble(key);
		} catch (RuntimeException ex) {
			return defVal;
		}
		return key;
	}

}
