package com.glaf.platforms.rule.model.bootstrap;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.model.CommonModel;

public class MapExtModel extends CommonModel {

	@Override
	public String getElementTagName() {
		return "div";
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getTemplateScript() {
		String  script =  ""/*"<script type='text/javascript' src='${contextPath}/static/scripts/highcharts/highcharts.js'></script>"*/
//				"<script type='text/javascript' src='${contextPath}/static/scripts/highstock/highstock.js'></script>"
//			+"<script type='text/javascript' src='${contextPath}/static/scripts/highcharts/highcharts-3d.js'></script>"
//			+"<script type='text/javascript' src='${contextPath}/static/scripts/highcharts/highcharts-more.js'></script>"
//			+"<script type='text/javascript' src='${contextPath}/static/scripts/highcharts/modules/funnel.js'></script>"
//			+"<script type='text/javascript' src='${contextPath}/static/scripts/highcharts/modules/exporting.js'></script>"
//			+"<script type='text/javascript' src='${contextPath}/static/scripts/highcharts/themes/default.js'></script>" 
			+"<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/echartsExt/js/echarts.js'></script>"
			+"<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/echartsExt/ext/jquery.echartsmap.extends.js'></script>";
		
		scriptMap.put("mapext", script);
		return templateScript;
	}
	
	public String getText(){
		return this.source.getOrDefault("text", "");
	}
	/**
	 * 标题对齐方式
	 * @return
	 */
	public String getAlign(){
		return source.getOrDefault("align","center");
	}
	
	/**
	 * 标题对齐方式
	 * @return
	 */
	public String getTitley(){
		return source.getOrDefault("titley","top");
	}
	
	/**
	 * 副标题文本
	 * @return
	 */
	public String getSubText(){
		return this.source.getOrDefault("subText", "");
	}
	/**
	 * 标题字体大小
	 * @return
	 */
	public String getTitleSize(){
		return this.source.getOrDefault("titlesize", "18");
	}
	/**
	 * 标题颜色
	 * @return
	 */
	public String getTitleColor(){
		return this.source.getOrDefault("titlecolor", "#333");
	}
	/**
	 * 副标题字体大小
	 * @return
	 */
	public String getSubtextSize(){
		return this.source.getOrDefault("subtextsize", "12");
	}
	/**
	 * 标题颜色
	 * @return
	 */
	public String getSubtextColor(){
		return this.source.getOrDefault("subtextcolor", "#aaa");
	}
	/**
	 * 图例是否启用
	 * @return
	 */
	public String getLegendEnabled(){
		return this.source.getOrDefault("legendEnabled", "false");
	}
	
	
	/**
	 * 图例垂直对齐方式
	 * @return
	 */
	public String getLegendVerticalAlign(){
		return this.source.getOrDefault("legendVerticalAlign", "vertical");
	}
	/**
	 * 图例水平对齐方式
	 * @return
	 */
	public String getLegendAlign(){
		return this.source.getOrDefault("legendAlign", "left");
	}
	/**
	 * 图例文本显示方式
	 * @return
	 */
	public String getLegendLayout(){
		return this.source.getOrDefault("legendLayout", "top");
	}
	
	public String getLegendpadding(){
		return this.source.getOrDefault("legendpadding", "5");
	}
	public String getLegendItemStyleFontSize(){
		return this.source.getOrDefault("legendItemStyleFontSize", "18");
	}
	
	/**
	 * 提示是否启用
	 * @return
	 */
	public String getToolTipEnabled(){
		return this.source.getOrDefault("tooltipenabled", "false");
	}
	
	public String getMapid(){
		String mapRule = this.source.get("mapJson");
		if(mapRule != null && !mapRule.isEmpty()){
			JSONArray mapRuleArray = JSON.parseArray(mapRule);
			JSONObject mapRuleJson = mapRuleArray.getJSONObject(0);
			return mapRuleJson.getString("id");
		}else{
			return "";
		}
	}
	
	/**
	 * 标签字体大小
	 * @return
	 */
	public String getNormalLabelSize(){
		return this.source.getOrDefault("normallabelsize", "18");
	}
	/**
	 * 标签颜色
	 * @return
	 */
	public String getNormalLabelColor(){
		return this.source.getOrDefault("normallabelcolor", "#333");
	}
	/**
	 * 标签值是否启用
	 * @return
	 */
	public String getShowNormalLabel(){
		return this.source.getOrDefault("shownormallabel", "false");
	}
	
	/**
	 * 标签字体大小
	 * @return
	 */
	public String getEmphasisLabelSize(){
		return this.source.getOrDefault("emphasislabelsize", "18");
	}
	/**
	 * 标签颜色
	 * @return
	 */
	public String getEmphasisLabelColor(){
		return this.source.getOrDefault("emphasislabelcolor", "#333");
	}
	/**
	 * 标签值是否启用
	 * @return
	 */
	public String getEmphasisLabelShow(){
		return this.source.getOrDefault("emphasislabelshow", "true");
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
	private boolean notEmpty(String str){
		if(str!=null && !"".equals(str.trim())){
			return true ;
		}
		return false ;
	}
	/**
	 * 数据源
	 * @return 
	 */
	public String getChartsSource(){
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
			retJSONObject.put("id", key); //标记
			
			dataset = findSourceByKey(key, selectDatasourceJSONArray);
			retJSONObject.put("chartType", dataset.getString("chartType")); //图类型
			retJSONObject.put("markerEnable", dataset.getString("markerEnable"));	//是否显示曲线
			retJSONObject.put("dataLabelsEnable", dataset.getString("dataLabelsEnable"));	//是否显示值
			for (Object object : jjj) {
				JSONObject j = (JSONObject) object;
				String a = j.getString("ctype");
				if("axisName".equalsIgnoreCase(a)){
					o = new JSONObject();
					o.put("cn", j.getString("title"));
					o.put("en", j.getString("columnLabel"));
					o.put("type", j.getString("FieldType"));
					retJSONObject.put(a, o); //数值
				}else if("yAxisName".equalsIgnoreCase(a)){
					JSONArray ja = retJSONObject.getJSONArray(a);
					if(ja==null || ja.isEmpty()){
						ja = new JSONArray();
					}
					o = new JSONObject();
					o.put("cn", j.getString("title"));
					o.put("en", j.getString("columnLabel"));
					o.put("type", j.getString("FieldType"));
					ja.add(o);
					retJSONObject.put(a,ja);//名称 y 轴
				}else if("xAxisName".equalsIgnoreCase(a) || "zAxisName".equalsIgnoreCase(a)){
					o = new JSONObject();
					o.put("cn", j.getString("title"));
					o.put("en", j.getString("columnLabel"));
					o.put("type", j.getString("FieldType"));
					retJSONObject.put(a,o); //系列 x 轴 或 （气泡）z 轴
				}
				String cs = j.getString("chartStack");
				if(cs!=null && !"".equals(cs) && "true".equals(cs)){
					retJSONObject.put("stack",j.getString("columnLabel"));//堆叠字段功能
				}
			}
			//日期转换格式
			String dateFormat = source.get("dateFormat");
			if(notEmpty(dateFormat)){
				retJSONObject.put("tf", dateFormat);
			}
			retJSONArray.add(retJSONObject);
		}
		return retJSONArray.toString() ;
	}
	
	public String getColors(){
		String colorsStr = source.get("colors") ;
		String reAry = null ;
		JSONArray retAry = new JSONArray();
		if(notEmpty(colorsStr)){
			JSONArray colorsArray = JSON.parseArray(colorsStr) ;
			if(colorsArray!=null && !colorsArray.isEmpty()){
				JSONObject colorsObj = colorsArray.getJSONObject(0);
				JSONArray ca = colorsObj.getJSONArray("colors");
				if(ca!=null && !ca.isEmpty()){
					JSONObject jo = null ;
					String color = "" ;
					for (Object object : ca) {
						jo = (JSONObject) object ;
						color = jo.getString("color") ;
						retAry.add(color);
					}
					reAry = retAry.toJSONString();
				}
			}
		}
		return reAry ;
	}
}
