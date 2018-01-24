package com.glaf.platforms.rule.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class ButtonModel extends CommonModel implements IRule, CssRule, AttrRule {

	public String getDbRules() {
		return super.dbRules();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getElementTagName() {
		return "button";
	}

	@Override
	public String getBind() {
		//// System.out.println(source.get("dataRole"));
		return null;
	}

	public String getButtonType() {
		return super.getSource("buttonType", "search");
	}

	public String getHandleColumns() {
		String linkageControlIn = super.getSource("linkageControlIn", "[]");

		String saveSourceSet = super.getSource("saveSourceSet", linkageControlIn);

		//// System.out.println(saveSourceSet);

		return saveSourceSet;
	}

	public String getChange() {
		String linkageControl = super.getSource("linkageControl", "[]");
		if (StringUtils.isNotEmpty(linkageControl)) {
			List<JSONObject> jsonArray = JSON.parseArray(linkageControl, JSONObject.class);
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

	public String getImageUrl() {

		String selectIcon = super.getSource("selectIcon", "");

		if (!isNullOrEmpty(selectIcon)) {
			selectIcon = "${contextPath}" + selectIcon;
		}

		return selectIcon;

	}

	public String getPageId() {
		// String linkPageStr = super.getSource("linkPage", "");
		// if (!isNullOrEmpty(linkPageStr)) {
		// List<JSONObject> list = JSON.parseArray(linkPageStr,
		// JSONObject.class);
		// if (!list.isEmpty()) {
		// String pageId = list.get(0).getString("id");
		//
		// return pageId;
		// }
		// }
		//
		JSONObject json = this.getLinkPageJson();
		if (json != null) {
			return json.getString("id");
		}

		return null;
	}

	private JSONObject getLinkPageJson() {
		String linkPageStr = super.getSource("linkPage", "");
		if (!isNullOrEmpty(linkPageStr)) {
			List<JSONObject> list = JSON.parseArray(linkPageStr, JSONObject.class);
			if (!list.isEmpty()) {
				return list.get(0);
			}
		}
		return null;
	}

	/**
	 * 是否批量操作
	 * 
	 * @return
	 */
	public String getMultiPlay() {
		return super.getSource("multiPlay", "false");
	}

	public String getRuleData() {
		// String rules[] = { "multiPlay", "id", "closeWindow", "height",
		// "width", "jumpType", "title", "paraType",
		// "pageId", "selectReportTemplate" };

		Set<String> rules = new HashSet<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				add("multiPlay");
				add("id");
				add("closeWindow");
				add("height");
				add("width");
				add("jumpType");
				add("title");
				add("pageId");
			}
		};

		JSONObject json = new JSONObject();
		String str;
		Set<String> array = new HashSet<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				add("paraType");
				add("selectReportTemplate");
				add("linkageControl");
				add("linkageControlIn");
			}
		};

		rules.addAll(array);

		for (String rule : rules) {
			str = super.getSource(rule, "");
			if (!isNullOrEmpty(str) && array.contains(rule)) {
				json.put(rule, JSONArray.parseArray(str));
			} else {
				json.put(rule, str);
			}
		}
		json.put("modal", getModal());
		json.put("multiple-flow", StringUtils.isNotBlank(super.getSource("dataSourceSetByFlow", "")));

		json.put("linkPageJson", this.getLinkPageJson());

		return json.toJSONString();
	}

	private Boolean getModal() {
		return Boolean.parseBoolean(super.getSource("modal", "false"));
	}

	public String getSaveValidate() {
		return super.getSource("saveValidate", "false");
	}

	public String getMaximize() {
		return super.getSource("maximize", "false");
	}

}
