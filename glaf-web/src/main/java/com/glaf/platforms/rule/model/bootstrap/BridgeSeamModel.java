package com.glaf.platforms.rule.model.bootstrap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import com.alibaba.druid.sql.ast.statement.SQLIfStatement.Else;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.ParamUtils;
import com.glaf.form.core.domain.FormComponent;
import com.glaf.form.core.domain.FormPage;
import com.glaf.form.core.domain.FormRule;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class BridgeSeamModel extends CommonModel {

	private static final long serialVersionUID = 1L;

	// 增加模板
	public String getTemplateScript() {
		scriptMap.put("d3", "<script type=\"text/javascript\" src=\"${contextPath}/scripts/D3/d3.min.js\"></script>");

		String script = "<script type='text/javascript' src='${contextPath}/scripts/jquery.bridgeSeam.js'></script>";
		scriptMap.put("bridgeSeam", script);
		return templateScript;
	}

	public void addTemplate(String subTemplate) {
		this.templateScript += subTemplate;
	}

	/**
	 * 分组数据源
	 * 
	 * @param datasourceColumnsJSONArray
	 * @return
	 */
	private Map<String, JSONArray> sortSource(JSONArray datasourceColumnsJSONArray) {
		Map<String, JSONArray> hamap = new HashMap();
		for (Object object : datasourceColumnsJSONArray) {
			JSONObject columns = (JSONObject) object;
			String ctype = columns.getString("ctype");
			String cstack = columns.getString("chartStack");
			String seq = columns.getString("seq");
			if ((ctype != null && !"".equals(ctype)) || (cstack != null && !"".equals(cstack) && "true".equals(cstack))) {
				if (hamap.containsKey(seq)) {
					JSONArray array = hamap.get(seq);
					array.add(columns);
				} else {
					JSONArray array = new JSONArray();
					array.add(columns);
					hamap.put(columns.getString("seq"), array);
				}
			}
		}
		return hamap;
	}

	/**
	 * 数据源
	 * 
	 * @return
	 */
	public String getChartsSource() {
		String dataSourceSet = source.get("dataSourceSet");
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONArray datasourceColumnsJSONArray = null;
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			JSONObject datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
			datasourceColumnsJSONArray = datasourceSetJSONObject.getJSONArray("selectColumns");
		}

		JSONArray retJSONArray = new JSONArray();

		// 根据seq分组
		Map<String, JSONArray> hamap = this.sortSource(datasourceColumnsJSONArray);

		Set<String> keys = hamap.keySet();
		JSONObject o = null;
		for (String key : keys) {
			JSONArray jjj = hamap.get(key);
			JSONObject retJSONObject = new JSONObject();
			retJSONObject.put("id", key); // 标记
			for (Object object : jjj) {
				JSONObject j = (JSONObject) object;
				String a = j.getString("ctype");
				o = new JSONObject();
				o.put("cn", j.getString("title"));
				o.put("en", j.getString("columnLabel"));
				o.put("type", j.getString("FieldType"));
				retJSONObject.put(a, o); // 系列 x 轴 或 （气泡）z 轴
			}
			retJSONArray.add(retJSONObject);
		}
		return retJSONArray.toString();
	}

	@Override
	public String getElementTagName() {
		return "div";
	}

	@Override
	public String getElementHtml() {
		return null;
	}

	private boolean notEmpty(String str) {
		if (str != null && !"".equals(str.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 横梁数量
	 * 
	 * @return
	 */
	public String getHSize() {
		return source.get("hSize");
	}

	/**
	 * 支撑梁宽度
	 * 
	 * @return
	 */
	public String getVWidth() {
		return source.get("vWidth");
	}

	public String getAngles() {
		String anglesStr = source.get("angles");
		String retStr = null;
		if (notEmpty(anglesStr)) {
			JSONArray ary = JSON.parseArray(anglesStr);
			JSONObject obj;
			JSONArray retAry = new JSONArray();
			for (Object object : ary) {
				obj = (JSONObject) object;
				retAry.add(obj.getIntValue("name"));
			}
			retStr = retAry.toJSONString();
		}
		return retStr;
	}

}
