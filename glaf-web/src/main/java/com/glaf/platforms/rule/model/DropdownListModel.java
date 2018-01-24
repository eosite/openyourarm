package com.glaf.platforms.rule.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class DropdownListModel extends ComboboxModel implements IRule, CssRule,
		AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getElementTagName() {
		return "input";
	}
	
	
	public String getRuleData() {
		String rules[] = { "paraType" };
		JSONObject json = new JSONObject();
		String str;
		for (String rule : rules) {
			str = super.getSource(rule, "");
			if(!isNullOrEmpty(str) && rule.equalsIgnoreCase("paratype")){
				json.put(rule, JSONArray.parseArray(str));
			}else{
				json.put(rule, str);
			}
		}
		return json.toJSONString();
	}
	
	public Integer getSelectedIndex(){
		if(source.get("selectedIndex")==null){
			return 0;
		}else{
			return Integer.parseInt(source.get("selectedIndex"));
		}
	}
}
