package com.glaf.platforms.rule.model.bootstrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class CellWebCabModel extends CommonModel implements IRule, CssRule, AttrRule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String templateScript = "";

	// 增加模板
	public String getTemplateScript() {

		String script ="<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/cellWebCab/js/jquery.cellWebCab.extends.js'></script>";
			
		scriptMap.put("cellWebCab", script);
		return templateScript;
	}

	public void addTemplate(String subTemplate) {
		this.templateScript += subTemplate;
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
	public String getElementTagName() {
		// return "ul";
		return null;
	}

	public String getElementHtml() {
//		Element ele = getElement();
//		return ele.toString();
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
/*	public String getEnumValue() {
		return super.getSource("enumValue", "");
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
	}*/
	public String getVisible(){
		return source.get("visible");
	}
	public String getEnabled(){
		return source.get("enabled");
	}
	private JSONObject  findSourceByKey(String key,JSONArray selectDatasourceJSONArray){
		JSONObject selectDatasourceJSONObject = null ;
		for (Object object : selectDatasourceJSONArray) {
			selectDatasourceJSONObject = (JSONObject) object ;
			if(key.equalsIgnoreCase(selectDatasourceJSONObject.getString("id"))){
				return selectDatasourceJSONObject ;
			}
		}
		return null ;
	}
	/**
	 * 分组数据源
	 * @param datasourceColumnsJSONArray
	 * @return
	 */
	private Map<String, JSONArray> sortSource(JSONArray datasourceColumnsJSONArray){
		Map<String, JSONArray> hamap = new HashMap();
		for (Object object : datasourceColumnsJSONArray) {
			JSONObject columns = (JSONObject) object;
			String ctype = columns.getString("ctype");
			String cstack = columns.getString("chartStack");
			String seq = columns.getString("seq"); 
			if((ctype!=null && !"".equals(ctype)) || (cstack!=null && !"".equals(cstack) && "true".equals(cstack)) ){
				if(hamap.containsKey(seq)){
					JSONArray array = hamap.get(seq);
					array.add(columns);
				}else{
					JSONArray array = new JSONArray();
					array.add(columns);
					hamap.put(columns.getString("seq"),array);
				}
			}
		}
		return hamap ;
	}
	/**
	 * 数据源
	 * @return 
	 */
	
	public String getCellDataSource(){
		String dataSourceSet = source.get("dataSourceSet");
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONArray selectDatasourceJSONArray = null ;
		JSONArray datasourceColumnsJSONArray = null;
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			JSONObject datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
			selectDatasourceJSONArray = datasourceSetJSONObject.getJSONArray("selectDatasource") ;
			datasourceColumnsJSONArray = datasourceSetJSONObject.getJSONArray("selectColumns");
		}
		JSONArray retJSONArray = new JSONArray();
		//根据seq分组
		Map<String, JSONArray> hamap = this.sortSource(datasourceColumnsJSONArray);	
		Set<String> keys = hamap.keySet();
		JSONObject o = null ;
		JSONObject dataset = null ;
		for (String key : keys) {
			JSONArray jjj = hamap.get(key);
			JSONObject retJSONObject = new JSONObject();
			dataset = findSourceByKey(key, selectDatasourceJSONArray);		
			for (Object object : jjj) {
				JSONObject j = (JSONObject) object;
				String a = j.getString("ctype");
				if("fileContent".equalsIgnoreCase(a)){
					o = new JSONObject();
					o.put("cn", j.getString("title"));
					o.put("en", j.getString("columnLabel"));
					
					retJSONObject.put(a, o); //数值
				}
				
			}
			
			retJSONArray.add(retJSONObject);
		}
		return retJSONArray.toString() ;
	}
}
