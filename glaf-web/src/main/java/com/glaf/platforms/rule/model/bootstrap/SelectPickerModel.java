package com.glaf.platforms.rule.model.bootstrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormPage;
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.ButtonModel;
import com.glaf.platforms.rule.model.CommonModel;

public class SelectPickerModel extends CommonModel implements IRule, CssRule, AttrRule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String templateScript = "";
	

	// 增加模板
	public String getTemplateScript() {
		
		String script = "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/mui/css/mui.min.css' />"
				+ "<script type='text/javascript' src='${contextPath}/scripts/mui/js/mui.min.js'></script>";
		scriptMap.put("mui", script);
		
		script =  "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/mui/plugin/picker/css/mui.picker.css' />"
					+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/mui/plugin/picker/css/mui.poppicker.css' />";
		scriptMap.put("mui-slect", script);
		String kk =  "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/mui/plugin/picker/dist/css/mui.picker.min.css' />"
				+ "<script type='text/javascript' src='${contextPath}/scripts/mui/plugin/picker/dist/js/mui.picker.all.js'></script>";
	   scriptMap.put("mui-picker", kk);
		script =  "<script type='text/javascript' src='${contextPath}/scripts/mui/plugin/picker/js/mui.picker.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/mui/plugin/picker/js/mui.poppicker.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/selectpicker/js/jquery.selectpicker.extends.js'></script>";

		scriptMap.put("selectpicker", script);
		return templateScript;
	}

	public void addTemplate(String subTemplate) {
		this.templateScript += subTemplate;
	}
	public String getRuleId() {
		return source.get("ruleId");
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
	public String getElementTagName() {
		// return "ul";
		return null;
	}
	private Elements as = new Elements();
	public String getElementHtml() {
//		Element ele = getElement();
//		return ele.toString();
		return null;
	}
	@Override
	public String getId() {
		return source.get("id");
	}
	public String getSelectFirst(){
		return source.getOrDefault("selectFirst", "false");
	}


	public String getBind() {
		return "value: selectedProduct,source: products,visible: isVisible, enabled: isEnabled,events: {change: onChange,open: onOpen,close: onClose}";
	}

	public String getPlaceholder() {
		return super.getSource("placeholder", "---请选择---");
	}
    public String getVisible(){
    	return source.get("visible");
    }
    public String getEnabled(){
    	return source.get("enabled");
    }
	public String getDataTextField() {
		//return super.getSource("datatextfield", this.getColumnName("name"));
		return this.getDataField("datatextfield", "name");
	}

	public String getDataValueField() {
		//return super.getSource("datavaluefield", this.getColumnName("code"));
		return this.getDataField("datavaluefield", "code");
	}
	public String getGrade(){
		return source.get("grade");
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
	/**
	 * 获取值
	 * 
	 * @param columnType
	 * @return
	 */
	private String columnsValue(String columnType) {
		String columns = source.get("columns");
		JSONArray columnsArray = JSON.parseArray(columns);
		JSONObject jo = null;
		String str = null;
		if(null!=columnsArray){
			for (Object object : columnsArray) {
				jo = (JSONObject) object;
				if (columnType.equalsIgnoreCase(jo.getString("columnType"))) {
					str = jo.getString("ColumnName");
				}
		  }
		}
		return str;
	}
	/**
	 * 简单模式 显示名称
	 * 
	 * @return
	 */
	public String getNameKey() {
		return columnsValue("nameKey");
	}

	/**
	 * 简单模式 idkey (treeid)
	 * 
	 * @return
	 */
	public String getIdKey() {
		// return "treeid";
		return columnsValue("idKey");
	}

	/**
	 * 简单模式 indexKey
	 * 
	 * @return
	 */
	public String getIndexKey() {
		// return "index_id";
		return columnsValue("indexKey");
	}

	/**
	 * 简单模式 父id
	 * 
	 * @return
	 */
	public String getPIdKey() {
		// return "parent_id";
		return columnsValue("pIdKey");
	}
	public String getExpandChilds() {
		return source.getOrDefault("expandChilds", "0");
	}
	public String getDefaultParentId(){
		
		return source.get("defaultParentId");
		
	}
	
}
