package com.glaf.platforms.rule.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class DatePickerModel extends CommonModel implements IRule, CssRule,
		AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getElementTagName() {
		return "input";
	}

	@Override
	public String getBind() {
		return null;
	}

	public String getFormat() {
		return super.getSource("format", "yyyy-MM-dd");
	}
	
	public String getDefaultSystemDate(){
		if (source.get("defaultSystemDate") == null || !Boolean.parseBoolean(source.get("defaultSystemDate"))) {
			return "null";
		} else {
			return new SimpleDateFormat(getFormat()).format(new Date());
		}
	}
}
