package com.glaf.platforms.rule.model.bootstrap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;


public class StepModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getBind() {
		return null;
	}

	
	public void setScriptMap(Map<String, String> scriptMap) {
		this.scriptMap = scriptMap;
		StringBuffer SB = new StringBuffer();
		SB.append("<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/step/css/step.css'>");
		scriptMap.put("step", SB.toString());
	}
	
	public String getElementTagName() {
		return null;
	}

	/**
	 * 返回初始化方法
	 */
	public String getDefined(){
		JSONArray retAry = new JSONArray();
		JSONObject retObj = null ;
		String imageDefined = source.get("stepDefined");
		JSONArray idArray = JSON.parseArray(imageDefined);
		if(idArray!=null && !idArray.isEmpty()){
			JSONObject idObject = idArray.getJSONObject(0);
			JSONArray value = idObject.getJSONArray("value");
			if(value != null && !value.isEmpty()){
				JSONObject valueObj = null;
				for (Object object : value) {
					valueObj = (JSONObject) object ;
					JSONObject expdata = JSON.parseObject(valueObj.getString("expdata"));
					JSONObject htmlstyledata = JSON.parseObject(valueObj.getString("htmlstyledata"));
					JSONObject htmldata = JSON.parseObject(valueObj.getString("htmldata"));
					retObj = new JSONObject();
					String expVal = expdata.getString("expActVal");
					retObj.put("key",HTMLExpressionConvertUtil.imageConvert(expVal));
					if(htmlstyledata!=null){						
						String htmlStyleVal = htmlstyledata.getString("htmlVal");
						retObj.put("numberVal",HTMLExpressionConvertUtil.imageConvert(htmlStyleVal));
					}
					String htmlVal = htmldata.getString("htmlVal");
					retObj.put("contentVal", HTMLExpressionConvertUtil.imageConvert(htmlVal));
					retAry.add(retObj);
				}
			}	
		}
		return retAry.toJSONString() ;
	}

	
	//获取第一层
	@Override
	public String getElementHtml() {
		String rid = getRuleId();
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml);
		Element ele = doc.getElementById(this.getId());
//		ele.removeAttr("contenteditable");
		if ("false".equals(this.isVisible())) {
			ele.attr("style", "display:none;"+ele.attr("style"));
		}
		String html = ele.outerHtml();

		return html;
	}
	
	
	
//	/**
//	 * 分组数据源
//	 * @param datasourceColumnsJSONArray
//	 * @return
//	 */
//	private Map<String, JSONArray> sortSource(JSONArray datasourceColumnsJSONArray){
//		Map<String, JSONArray> hamap = new HashMap();
//		if(datasourceColumnsJSONArray!=null && !datasourceColumnsJSONArray.isEmpty()){
//			for (Object object : datasourceColumnsJSONArray) {
//				JSONObject columns = (JSONObject) object;
//				String ctype = columns.getString("ctype");
////			String cstack = columns.getString("chartStack");
//				String seq = columns.getString("seq"); 
//				if(ctype!=null && !"".equals(ctype)){
//					if(hamap.containsKey(seq)){
//						JSONArray array = hamap.get(seq);
//						array.add(columns);
//					}else{
//						JSONArray array = new JSONArray();
//						array.add(columns);
//						hamap.put(columns.getString("seq"),array);
//					}
//				}
//			}
//		}
//		return hamap ;
//	}
//	private JSONObject  findSourceByKey(String key,JSONArray selectDatasourceJSONArray){
//		JSONObject selectDatasourceJSONObject = null ;
//		for (Object object : selectDatasourceJSONArray) {
//			selectDatasourceJSONObject = (JSONObject) object ;
//			if(key.equalsIgnoreCase(selectDatasourceJSONObject.getString("id"))){
//				return selectDatasourceJSONObject ;
//			}
//		}
//		return null ;
//	}
//	/**
//	 * 数据源
//	 * @return 
//	 */
//	public String getColumns(){
//		String dataSourceSet = source.get("dataSourceSet");
//		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
//		JSONArray selectDatasourceJSONArray = null;
//		JSONArray datasourceColumnsJSONArray = null;
//		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
//			JSONObject datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
//			selectDatasourceJSONArray = datasourceSetJSONObject.getJSONArray("selectDatasource") ;
//			datasourceColumnsJSONArray = datasourceSetJSONObject.getJSONArray("selectColumns");
//		}
//		JSONArray retJSONArray = new JSONArray();
//		//根据seq分组
//		Map<String, JSONArray> hamap = this.sortSource(datasourceColumnsJSONArray);
//		
//		Set<String> keys = hamap.keySet();
//		JSONObject o = null ;
//		JSONObject dataset = null ;
//		for (String key : keys) {
//			JSONArray jjj = hamap.get(key);
//			JSONObject retJSONObject = new JSONObject();
//			retJSONObject.put("id", key); //标记
//			
//			dataset = findSourceByKey(key, selectDatasourceJSONArray);
//			for (Object object : jjj) {
//				JSONObject j = (JSONObject) object;
//				String a = j.getString("ctype");
//				if("number".equalsIgnoreCase(a)){
//					o = new JSONObject();
//					o.put("cn", j.getString("title"));
//					o.put("en", j.getString("columnLabel"));
//					o.put("type", j.getString("FieldType"));
//					retJSONObject.put(a, o); //
//				}else if("title".equalsIgnoreCase(a)){
//					o = new JSONObject();
//					o.put("cn", j.getString("title"));
//					o.put("en", j.getString("columnLabel"));
//					o.put("type", j.getString("FieldType"));
//					retJSONObject.put(a,o);//
//				}else if("content".equalsIgnoreCase(a)){
//					o = new JSONObject();
//					o.put("cn", j.getString("title"));
//					o.put("en", j.getString("columnLabel"));
//					o.put("type", j.getString("FieldType"));
//					retJSONObject.put(a,o); //
//				}else if("status".equalsIgnoreCase(a)){
//					o = new JSONObject();
//					o.put("cn", j.getString("title"));
//					o.put("en", j.getString("columnLabel"));
//					o.put("type", j.getString("FieldType"));
//					retJSONObject.put(a,o); //
//				}
//			}
//			retJSONArray.add(retJSONObject);
//		}
//		return retJSONArray.toString() ;
//	}
	
	//获取固定数据集
	public String getFixDataSource(){
		JSONArray retAry = new JSONArray();
		String dataset = source.get("fixDataSource");
		if(StringUtils.isNotEmpty(dataset)){
			retAry = JSONArray.parseArray(dataset);
		}
		return retAry.toJSONString();
	}


}
