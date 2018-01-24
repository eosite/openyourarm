package com.glaf.platforms.rule.model;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class ComboboxModel extends CommonModel implements IRule, CssRule,
		AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getBind() {
		return "value: selectedProduct,source: products,visible: isVisible, enabled: isEnabled,events: {change: onChange,open: onOpen,close: onClose}";
	}

	public String getPlaceholder() {
		return super.getSource("placeholder", "---请选择---");
	}

	public String getDataTextField() {
		//return super.getSource("datatextfield", this.getColumnName("name"));
		return this.getDataField("datatextfield", "name");
	}

	public String getDataValueField() {
		//return super.getSource("datavaluefield", this.getColumnName("code"));
		return this.getDataField("datavaluefield", "code");
	}
	
	private String getDataField(String type,String defVal){
		String enumValue = getEnumValue();
		if (StringUtils.isNotEmpty(enumValue)) {
			return defVal;
		} else {
			String str = super.getSource(type, "");
			if(StringUtils.isNotEmpty(str)){
				List<JSONObject> jsonArray = JSON.parseArray(str,
						JSONObject.class);
				if (jsonArray.size() > 0) {
					return jsonArray.get(0).getString("columnName");
				}
			}
		}
		return defVal;
	}

	public String getValuePrimitive() {
		return super.getSource("valueprimitive", "true");
	}

	public String getEnumValue() {
		return super.getSource("enumValue", "");
	}

	private String getColumnName(String defVal) {
		String enumValue = getEnumValue();
		if (StringUtils.isNotEmpty(enumValue)) {
			return defVal;
		} else {
			String columns = super.getSource("columns", "[]");
			List<JSONObject> jsonArray = JSON.parseArray(columns,
					JSONObject.class);
			if (jsonArray.size() > 0) {
				return jsonArray.get(0).getString("ColumnName");
			}
			return defVal;
		}
	}

	public String getChange() {
		String linkageControl = super.getSource("linkageControl", "[]");
		if (StringUtils.isNotEmpty(linkageControl)) {
			List<JSONObject> jsonArray = JSON.parseArray(linkageControl,
					JSONObject.class);
			if (jsonArray.size() > 0) {
				StringBuffer ids = new StringBuffer();
				for (int i = 0; i < jsonArray.size(); i++) {
					ids.append(",#").append(jsonArray.get(i).get("id"));
				}
				return ids.substring(1).toString();
			}
		}
		return "";
	}

	public String getParam() {

		return "code";
	}

}
