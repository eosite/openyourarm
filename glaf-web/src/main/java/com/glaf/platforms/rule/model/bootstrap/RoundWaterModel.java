package com.glaf.platforms.rule.model.bootstrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.expression.core.util.ExpressionConvertUtil;
import com.glaf.form.core.domain.FormComponent;
import com.glaf.form.core.domain.FormRule;
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class RoundWaterModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String templateScript = "";

	// 增加模板
	public String getTemplateScript() {
		
		String script = "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/roundwater/js/jquery.roundwater.extends.js'></script>";

		scriptMap.put("roundwater", script);
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
		Element ele = getElement();
		return ele.toString();
	}
	
	@Override
	public String getId() {
		return source.get("id");
	}

	@Override
	public String getName() {
		return source.get("name");
	}
	public String getRoundColor() {
		return source.get("roundcolor");
	}
	public String getRoundBorderColor() {
		return source.get("roundBorderColor");
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
	public String getRoundSource(){
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
				if("title".equalsIgnoreCase(a)){
					o = new JSONObject();
					o.put("cn", j.getString("title"));
					o.put("en", j.getString("columnLabel"));
					
					retJSONObject.put(a, o); //数值
				}else if("value".equalsIgnoreCase(a)){
					JSONArray ja = retJSONObject.getJSONArray(a);
					if(ja==null || ja.isEmpty()){
						ja = new JSONArray();
					}
					o = new JSONObject();
					o.put("cn", j.getString("title"));
					o.put("en", j.getString("columnLabel"));
					
					ja.add(o);
					retJSONObject.put(a,ja);
				}
				
			}
			
			retJSONArray.add(retJSONObject);
		}
		return retJSONArray.toString() ;
	}
	public String getRoundSet(){
		String zhou = source.get("roundSet");
		JSONArray zhouA = JSON.parseArray(zhou);
		JSONArray zhouArray  = null ;
		JSONObject zhouObj  = null ;
		JSONArray retArray = new JSONArray();
		JSONObject retObj  = null ;
		if(zhouA!=null && !zhouA.isEmpty()){
			zhouArray = zhouA.getJSONObject(0).getJSONArray("values");
			if(zhouArray!=null && !zhouArray.isEmpty()){
				for (Object object : zhouArray) {
					zhouObj =  (JSONObject) object ;
					retObj = new JSONObject();
					String title = zhouObj.getString("title");
					if(zhouObj.get("tWeight")!=null){
						if(zhouObj.getBoolean("tWeight")){
							retObj.put("tWeight" , true);
						}
					}
					if(zhouObj.get("tStyle")!=null){
						if(zhouObj.getBoolean("tStyle")){
							retObj.put("tStyle" , true);
						}
					}
					if(zhouObj.get("vWeight")!=null){
						if(zhouObj.getBoolean("vWeight")){
							retObj.put("vWeight" , true);
						}
					}
					if(zhouObj.get("vStyle")!=null){
						if(zhouObj.getBoolean("vStyle")){
							retObj.put("vStyle" , true);
						}
					}
					setOBJ(zhouObj, retObj, "tfontSize","tfontSize","number");
					setOBJ(zhouObj, retObj, "ttextColor","ttextColor","string");
					setOBJ(zhouObj, retObj, "vfontSize","vfontSize","number");
					setOBJ(zhouObj, retObj, "vtextColor","vtextColor","string");
					
					retArray.add(retObj);
				}
				return retArray.toJSONString() ;
			}
		}
		JSONObject tObj = new JSONObject();
		JSONObject emptyObj = new JSONObject();
		
		retArray.add(emptyObj);
		return retArray.toJSONString() ;
	}
	private boolean notEmpty(String str){
		if(str!=null && !"".equals(str.trim())){
			return true ;
		}
		return false ;
	}
	private void setOBJ(JSONObject zhouObj,JSONObject retObj,String key,String getVlaue,String type){
		if("string".equalsIgnoreCase(type)){
			String str = zhouObj.getString(getVlaue) ;
			if(notEmpty(str)){
				retObj.put(key ,str);
			}
		}else if("number".equalsIgnoreCase(type)){
			Double str = zhouObj.getDouble(getVlaue) ;
			if(str!=null){
				retObj.put(key ,str);
			}
		}
	}
}
