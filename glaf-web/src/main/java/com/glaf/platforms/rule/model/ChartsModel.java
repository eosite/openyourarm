package com.glaf.platforms.rule.model;

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
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class ChartsModel implements IRule, CssRule, AttrRule {
	
	private static final long serialVersionUID = 1L;
	protected Map<String, String> source;
	protected Map<String, String> scriptMap;
	
	protected String templateScript = "" ;
	protected FormPage formPage;

	public void setFormPage(FormPage formPage) {
		this.formPage = formPage;
	}
	
	

	//增加模板
	public String getTemplateScript(){
		String  script =  /*"<script type='text/javascript' src='${contextPath}/static/scripts/highcharts/highcharts.js'></script>"*/
				"<script type='text/javascript' src='${contextPath}/scripts/highstock/highstock.js'></script>"
			+"<script type='text/javascript' src='${contextPath}/scripts/highcharts/highcharts-3d.js'></script>"
			+"<script type='text/javascript' src='${contextPath}/scripts/highcharts/highcharts-more.js'></script>"
			+"<script type='text/javascript' src='${contextPath}/scripts/highcharts/modules/funnel.js'></script>"
			+"<script type='text/javascript' src='${contextPath}/scripts/highcharts/modules/heatmap.js'></script>"
			+"<script type='text/javascript' src='${contextPath}/scripts/highcharts/modules/treemap.js'></script>"
			+"<script type='text/javascript' src='${contextPath}/scripts/highcharts/modules/exporting.js'></script>"
			+"<script type='text/javascript' src='${contextPath}/scripts/highcharts/themes/default.js'></script>" ;
		
		scriptMap.put("charts", script);
		return templateScript;
	}
	
	
	public void addTemplate(String subTemplate){
		this.templateScript += subTemplate ;
	}
	public String getColors(){
		String colorsStr = source.get("colors") ;
		String reAry = null ;
		JSONArray retAry = new JSONArray();
		if(notEmpty(colorsStr)){
			JSONArray colorsArray = JSON.parseArray(colorsStr) ;
			if(colorsArray!=null && !colorsArray.isEmpty()){
				JSONObject colorsObj = colorsArray.getJSONObject(0);
				String gradientAbleStr = colorsObj.getString("gradientAble");
				boolean flag = false;
				if(gradientAbleStr != null && gradientAbleStr.equals("true")){
					flag = true;
				}
				JSONArray ca = colorsObj.getJSONArray("colors");
				if(ca!=null && !ca.isEmpty()){
					JSONObject jo = null ;
					String color = "" ;
					for (Object object : ca) {
						jo = (JSONObject) object ;
						color = jo.getString("color") ;
						if(flag){
							String endcolor = jo.getString("endcolor");
							JSONObject gradientColorObj = new JSONObject();
							JSONObject gradientColor = new JSONObject();
							gradientColor.put("cx", 0.5);
							gradientColor.put("cy", 0.3);
							gradientColor.put("r", 0.7);
							gradientColorObj.put("radialGradient", gradientColor);
							JSONArray stops = new JSONArray();
							JSONArray stop = new JSONArray();
							stop.add("0");
							stop.add(color);
							stops.add(stop);
							stop = new JSONArray();
							stop.add("1");
							stop.add(endcolor);
							stops.add(stop);
							gradientColorObj.put("stops", stops);
							retAry.add(gradientColorObj);
						}else{
							retAry.add(color);
						}
					}
					reAry = retAry.toJSONString();
				}
			}
		}
		return reAry ;
	}
	/**
	 * 是否显示值
	 * @return
	 */
	public String getSeriesDataLabelsEnabled(){
		return source.get("seriesDataLabelsEnabled");
	}
	/**
	 * 堆叠模式
	 * @return
	 */
	public String getStacking(){
		
		String charType = getCharType();
		if(StringUtils.isNotEmpty(charType)){
			if("charts_pile".equals(charType)&&StringUtils.isEmpty(source.get("stacking"))){
				return "normal";
			}
		}
		return source.get("stacking");
	}
	
	/**
	 * 图表类型
	 * @return
	 */
	public String getCharType(){
		String rid = getRuleId();
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml);
		Element ele = doc.getElementById(this.getId());
		String charType = ele.attr("charttype");
		return charType;
	}
	/**
	 * charts type
	 * @return
	 */
	public String getChartsType(){
		String retVal = null ;
		String charType = getCharType();
		retVal = source.get("chartsType") ;
		if(StringUtils.isNotEmpty(charType)){
			if("charts_line".equals(charType)){
				charType = "line";
			}else if("charts_histo".equals(charType)){
				charType = "column";
			}else if("charts_pie".equals(charType)){
				charType = "pie";
			}else if("charts_dial".equals(charType)){
				charType = "gauge";
			}else if("charts_bar".equals(charType)){
				charType = "bar";
			}else if("charts_scatter".equals(charType)){
				charType = "scatter";
			}else if("charts_area".equals(charType)){
				charType = "area";
			}else if("charts_pile".equals(charType)){
				charType = "column";
			}
			if(StringUtils.isEmpty(retVal)){
				retVal =  charType;
			}
		}
		if("line".equals(retVal)){
			if("true".equals(source.getOrDefault("splineAble", "false"))){
				retVal = "spline";
			}
		}
		return retVal;
	}
	/**
	 * 雷达显示
	 * @return
	 */
	public String getPolar(){
		return source.get("polar");
	}
	/**
	 * 滚动条
	 * @return
	 */
	public String getScrollbar(){
		return source.get("scrollbar");
	}
	/**
	 * zoom type
	 * @return
	 */
	public String getZoomType(){
		return source.get("zoomType");
	}
	/**
	 * 单击行事件所执行的方法
	 */
	public String getClickFunction() {
		if (source.get("click") != null && !"".equals(source.get("click"))) {
			//方法类型
			String type = StringUtils.isNotEmpty(source.get("click").toString()) ? source.get("click") : "''" ;
			//获取规则 -->输入表达式定义
			String paraType = source.get("paraType");
			if(StringUtils.isNotEmpty(paraType)){
				JSONArray paraTypeArray = JSON.parseArray(paraType);
				JSONObject paraTypeObject = paraTypeArray.getJSONObject(0);
				String datas = paraTypeObject.getString("datas") ;
				
				JSONObject params = new JSONObject() ;
				JSONObject linkstrObj = null ;
				String linkPage = source.get("linkPage"); //联动页面
				if(StringUtils.isNotEmpty(linkPage)){
					JSONArray linkPages = JSON.parseArray(linkPage);
					if(linkPages!=null && !linkPages.isEmpty()){
						linkstrObj = linkPages.getJSONObject(0) ;
						String linkstr = linkstrObj.toJSONString();
						params.put("link", linkstr);
					}
					String jumpType = source.get("jumpType"); //跳转类型
					params.put("jumpType", jumpType);
					String title = source.get("windowTitle"); //窗口名称
					params.put("title", title);
					String model = source.get("windowModal"); //是否为模态窗口
					params.put("model", model);
					String width = source.get("windowWidth"); //窗口宽度
					params.put("width", width);
					String height = source.get("windowHeight");//窗口高度
					params.put("height", height);
					String maximize = source.get("maximize");
					params.put("maximize", maximize);
					
					//源数据查看
					String sourceUrl = source.get("isSourceUrl");
					if(notEmpty(sourceUrl)){
						params.put("slink",true);
						params.put("fillform", source.get("fillform"));
						JSONObject rj = new JSONObject() ;
						JSONArray ra = new JSONArray();
						JSONObject raObj1 = new JSONObject();
						raObj1.put("param", "category");
						raObj1.put("inid", this.getId());
						raObj1.put("type", "getRow");
						raObj1.put("columnName", "category");
						
						ra.add(raObj1);
						JSONObject raObj2 = new JSONObject();
						raObj2.put("param", "databaseId");
						raObj2.put("inid", this.getId());
						raObj2.put("type", "getRow");
						raObj2.put("columnName", "databaseId");
						ra.add(raObj2);
						rj.put("page", ra);
						
						if(datas!=null){
							JSONObject ja = JSON.parseObject(datas) ;
							if(!ja.isEmpty() ){
								JSONArray paAry = ja.getJSONArray(linkstrObj.getString("id"));
								paAry.addAll(ra);
								datas = ja.toJSONString();
							}else{
								datas = rj.toJSONString();
							}
						}else{
							datas = rj.toJSONString();
						}
					}
				}
				
				//源数据查看
				/*String sourceUrl = source.get("sourceUrl");
				if(notEmpty(sourceUrl)){
					params.put("fillform", source.get("fillform"));
					
					params.put("slink", sourceUrl);
					String jumpType = source.get("jumpType"); //跳转类型
					params.put("jumpType", jumpType);
					String title = source.get("windowTitle"); //窗口名称
					params.put("title", title);
					String model = source.get("windowModal"); //是否为模态窗口
					params.put("model", model);
					String width = source.get("windowWidth"); //窗口宽度
					params.put("width", width);
					String height = source.get("windowHeight");//窗口高度
					params.put("height", height);
					
					JSONObject rj = new JSONObject() ;
					JSONArray ra = new JSONArray();
					JSONObject raObj1 = new JSONObject();
					raObj1.put("param", "category");
					raObj1.put("inid", this.getId());
					raObj1.put("type", "getRow");
					raObj1.put("columnName", "category");
					
					ra.add(raObj1);
					JSONObject raObj2 = new JSONObject();
					raObj2.put("param", "databaseId");
					raObj2.put("inid", this.getId());
					raObj2.put("type", "getRow");
					raObj2.put("columnName", "databaseId");
					ra.add(raObj2);
					rj.put("page", ra);
					datas = rj.toJSONString();
				}*/
				
				return "pubsub.pub('"+type+"',"+datas+","+params.toJSONString()+",this);" ;
			}else{
				return null ;
			}
		} else {
			return null;
		}

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
		
		//曲线类型
		String curveTypeStr = source.get("curveType");
		JSONArray curveTypeAry = JSON.parseArray(curveTypeStr);
		JSONArray curveAry = null;
		if(curveTypeAry!=null && !curveTypeAry.isEmpty()){
			JSONArray retJSONArray = new JSONArray();
			JSONObject curveTypeObj = curveTypeAry.getJSONObject(0);
			//数据集的seq列表
			JSONArray seqList = curveTypeObj.getJSONArray("seqList");
			
			curveAry = curveTypeObj.getJSONArray("rightColumns");
			
			JSONArray leftColumns = curveTypeObj.getJSONArray("leftColumns");
			
			for(int seqNum = 0 ; seqNum < seqList.size() ; seqNum++){
				//chartsSource根据seq区分
				JSONObject retJSONObject = new JSONObject();
				String key = seqList.getString(seqNum);
				retJSONObject.put("id", key); //标记
				JSONObject dataset = findSourceByKey(key, selectDatasourceJSONArray);
				retJSONObject.put("chartType", dataset.getString("chartType")); //图类型
				retJSONObject.put("markerEnable", dataset.getString("markerEnable"));	//是否显示曲线
				retJSONObject.put("dataLabelsEnable", dataset.getString("dataLabelsEnable"));	//是否显示值
				
				JSONObject o = null ;
				int typeNum = curveTypeObj.getIntValue("type");
				if(typeNum == 4){
					//加上基准线
					//属性值
					JSONObject prosJSON =  curveTypeObj.getJSONObject("pros");
					double datum1 = prosJSON.getJSONObject(key).getDoubleValue("datum1");	//datum1
					double datum2 = prosJSON.getJSONObject(key).getDoubleValue("datum2");	//datum2
					double datum3 = prosJSON.getJSONObject(key).getDoubleValue("datum3");	//datum3
					double datum4 = prosJSON.getJSONObject(key).getDoubleValue("datum4");	//datum4
					double datum5 = prosJSON.getJSONObject(key).getDoubleValue("datum5");	//datum5
					o = new JSONObject();
					o.put("cn", "基准线（"+datum1 +"）");
					o.put("en", "datum("+datum1 +")");
					o.put("type", "string");
					JSONArray ja = retJSONObject.getJSONArray("yAxisName");
					if(ja==null || ja.isEmpty()){
						ja = new JSONArray();
					}
					ja.add(o);
					retJSONObject.put("yAxisName",ja);//名称 y 轴
					o = new JSONObject();
					o.put("cn", "基准线（"+datum2 +"）");
					o.put("en", "datum("+datum2 +")");
					o.put("type", "string");
					ja = retJSONObject.getJSONArray("yAxisName");
					ja.add(o);
					retJSONObject.put("yAxisName",ja);//名称 y 轴
					o = new JSONObject();
					o.put("cn", "基准线（"+datum3 +"）");
					o.put("en", "datum("+datum3 +")");
					o.put("type", "string");
					ja = retJSONObject.getJSONArray("yAxisName");
					ja.add(o);
					retJSONObject.put("yAxisName",ja);//名称 y 轴
					o = new JSONObject();
					o.put("cn", "基准线（"+datum4 +"）");
					o.put("en", "datum("+datum4 +")");
					o.put("type", "string");
					ja = retJSONObject.getJSONArray("yAxisName");
					ja.add(o);
					retJSONObject.put("yAxisName",ja);//名称 y 轴
					o = new JSONObject();
					o.put("cn", "基准线（"+datum5 +"）");
					o.put("en", "datum("+datum5 +")");
					o.put("type", "string");
					ja = retJSONObject.getJSONArray("yAxisName");
					ja.add(o);
					retJSONObject.put("yAxisName",ja);//名称 y 轴
				}
				for(int i = 0 ; i < curveAry.size() ; i++){
					JSONObject curveObj = curveAry.getJSONObject(i);
					
					
					
					if(!key.equals(curveObj.getString("seq"))){
						//不是同一个seq，跳过
						continue;
					}
					
					for(Object leftColumnsObj : leftColumns){
						JSONObject leftCol = (JSONObject)leftColumnsObj;
						String calculeType = leftCol.getString("calculeType");
						if(!calculeType.isEmpty()){
							if(leftCol.getString("seq").equals(key) && calculeType.equals(curveObj.getString("columnName"))){
								curveObj.put("FieldType", leftCol.getString("FieldType"));
								break;
							}
						}
					}
					
					String a = curveObj.getString("ctype");
					String fieldType = ParamUtils.getString(curveObj,"FieldType","string");
					if("axisName".equalsIgnoreCase(a)){
						o = new JSONObject();
						o.put("cn", curveObj.getString("cn"));
						o.put("en", curveObj.getString("en"));
						o.put("type", fieldType);
						retJSONObject.put(a, o); //数值
					}else if("yAxisName".equalsIgnoreCase(a)){
						JSONArray ja = retJSONObject.getJSONArray(a);
						if(ja==null || ja.isEmpty()){
							ja = new JSONArray();
						}
						o = new JSONObject();
						o.put("cn", curveObj.getString("cn"));
						o.put("en", curveObj.getString("en"));
						o.put("type", fieldType);
						ja.add(o);
						retJSONObject.put(a,ja);//名称 y 轴
					}else if("xAxisName".equalsIgnoreCase(a)){
						o = new JSONObject();
						o.put("cn", curveObj.getString("cn"));
						o.put("en", curveObj.getString("en"));
						o.put("type", fieldType);
						retJSONObject.put(a,o); //系列 x 轴
					}
					String cs = curveObj.getString("chartStack");
					if(cs!=null && !"".equals(cs) && "true".equals(cs)){
						retJSONObject.put("stack",curveObj.getString("columnName"));//堆叠字段功能
					}
				}
				retJSONArray.add(retJSONObject);
			}
			
			
			return retJSONArray.toString() ;
			
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
				else if("tSize".equalsIgnoreCase(a) || "tSize".equalsIgnoreCase(a)){
					o = new JSONObject();
					o.put("cn", j.getString("title"));
					o.put("en", j.getString("columnLabel"));
					o.put("type", j.getString("FieldType"));
					retJSONObject.put(a,o); //系列 x 轴 或 （气泡）z 轴
				}
				else if("tColor".equalsIgnoreCase(a) || "tColor".equalsIgnoreCase(a)){
					o = new JSONObject();
					o.put("cn", j.getString("title"));
					o.put("en", j.getString("columnLabel"));
					o.put("type", j.getString("FieldType"));
					retJSONObject.put(a,o); //系列 x 轴 或 （气泡）z 轴
				}
				else if("tName".equalsIgnoreCase(a) || "tName".equalsIgnoreCase(a)){
					o = new JSONObject();
					o.put("cn", j.getString("title"));
					o.put("en", j.getString("columnLabel"));
					o.put("type", j.getString("FieldType"));
					retJSONObject.put(a,o); //系列 x 轴 或 （气泡）z 轴
				}
				else if("tid".equalsIgnoreCase(a) || "tid".equalsIgnoreCase(a)){
					o = new JSONObject();
					o.put("cn", j.getString("title"));
					o.put("en", j.getString("columnLabel"));
					o.put("type", j.getString("FieldType"));
					retJSONObject.put(a,o); //系列 x 轴 或 （气泡）z 轴
				}
				else if("tParent".equalsIgnoreCase(a) || "tParent".equalsIgnoreCase(a)){
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
	// ***************  标题    ***************
	/**
	 * 标题文本
	 * @return
	 */
	public String getText(){
		//[{"htmldata":{"htmlVal":"<p>我的</p>","varVal":[]},"name":"html 样式定义"}]
		String text =  source.get("text") ;
		String retAry = "null" ;
		if(StringUtils.isNotEmpty(text) && !"".equals(text.trim())){
			try {
				JSONArray array = JSON.parseArray(text);
				if(array!=null && !array.isEmpty()){
					JSONObject obj = array.getJSONObject(0);
					JSONObject subObj = obj.getJSONObject("htmldata") ;
					retAry = subObj.getString("htmlVal").replace("\"", "'");
				}
			} catch (Exception e) {
				retAry = text ; 
			}
		}
		if(!notEmpty(retAry)){
			retAry = " " ;
		}
		return retAry;
	}
	/**
	 * 标题对齐方式
	 * @return
	 */
	public String getAlign(){
		return source.get("align");
	}
	// ***************  副标题    ***************
	/**
	 * 副标题文本
	 * @return
	 */
	public String getSubText(){
		String text =  source.get("subText") ;
		String retAry = null ;
		if(StringUtils.isNotEmpty(text)){
			try {
				JSONArray array = JSON.parseArray(text);
				if(array!=null && !array.isEmpty()){
					JSONObject obj = array.getJSONObject(0);
					JSONObject subObj = obj.getJSONObject("htmldata") ;
					retAry = subObj.getString("htmlVal").replace("\"", "'");
				}
			} catch (Exception e) {
				retAry = text ; 
			}
		}
		if(!notEmpty(retAry)){
			retAry = " " ;
		}
		return retAry;
	}
	/**
	 * 副标题对齐方式
	 * @return
	 */
	public String getsSubAlign(){
		return source.get("subAlign");
	}
	// ***************  图例    ***************
	public String getLegendItemStyleColor(){
		return source.getOrDefault("legendItemStyleColor", "#000");
	}
	public String getLegendItemStyleColorHidden(){
		return source.getOrDefault("legendItemStyleColorHidden", "#cccccc");
	}
	/**
	 * 图例是否启用
	 * @return
	 */
	public String getLegendEnabled(){
		return source.get("legendEnabled");
	}
	/**
	 * 图例文本显示方式
	 * @return
	 */
	public String getLegendLayout(){
		return source.get("legendLayout");
	}
	/**
	 * 图例水平对齐方式
	 * @return
	 */
	public String getLegendAlign(){
		return source.get("legendAlign");
	}
	/**
	 * 图例漂浮效果
	 * @return
	 */
	public String getLegendFloating(){
		return source.get("legendFloating");
	}
	/**
	 * 图例 x偏移量
	 * @return
	 */
	public String getLegendX(){
		return source.get("legendX");
	}
	/**
	 * 图例 y偏移量
	 * @return
	 */
	public String getLegendY(){
		return source.get("legendY");
	}
	/**
	 * 图例垂直对齐方式
	 * @return
	 */
	public String getLegendVerticalAlign(){
		return source.get("legendVerticalAlign");
	}
	/**
	 * 图例字体大小
	 * @return
	 */
	public String getLegendItemStyleFontSize(){
		return source.get("legendItemStyleFontSize");
	}
	/**
	 * 图例外边距
	 * @return
	 */
	public String getLegendMargin(){
		return source.get("legendMargin");
	}
	/**
	 * 图例内边距
	 * @return
	 */
	public String getLegendPadding(){
		return source.get("legendPadding");
	}
	
	
	// ***************  提示  ***************
	/**
	 * 提示信息启用
	 * @return
	 */
	public String getTooltipEnabled(){
		return source.get("tooltipEnabled");
	}
	/**
	 * 提示信息 值后缀
	 * @return
	 */
	public String getTooltipValueSuffix(){
		return source.get("tooltipValueSuffix");
	}
	/**
	 * 提示信息  准线
	 * @return
	 */
	public String getTooltipCrosshairs(){
		return source.get("tooltipCrosshairs");
	}
	/**
	 * 提示信息  共享提示框
	 * @return
	 */
	public String getTooltipShared(){
		return source.get("tooltipShared");
	}
	// ***************  3d 设置  ***************
	/**
	 * 是否启用3D效果
	 * @return
	 */
	public String getEnabled3D(){
		return source.get("enabled3D");
	}
	/**
	 * alpha 角度
	 * @return
	 */
	public String getAlpha(){
		return source.get("alpha");
	}
	/**
	 * beta 角度
	 * @return
	 */
	public String getBeta(){
		return source.get("beta");
	}
	/**
	 * depth 深度
	 * @return
	 */
	public String getDepth(){
		return source.get("depth");
	}
	/**
	 * 旋转3D图
	 * @return
	 */
	public String getRotate3D(){
		return source.get("rotate3D");
	}
	/**
	 * viewDistance 观察距离
	 * @return
	 */
	public String getViewDistance(){
		return source.get("viewDistance");
	}
	
	// ***************  x 轴  ***************
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
	/**
	 * 雷达
	 * @return
	 */
	private boolean isPolarPolygon(){
		//雷达
		String polar = source.get("polar");
		//雷达多边形
		String polarPolygon = source.get("polar");
		if("true".equalsIgnoreCase(polar) && "true".equalsIgnoreCase(polarPolygon)){
			return true;
		}
		return false;
	}
	
	public String getXzhou(){
		String zhou = source.get("xzhou");
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
					JSONObject titleObj = new JSONObject();
					if(notEmpty(title)){
						titleObj.put("text", title);
					}else{
						titleObj.put("text", " ");
					}
					retObj.put("title" ,titleObj);
					
					JSONObject labelsObj =  new JSONObject();
					retObj.put("labels" ,labelsObj);
					String labelsRotation = zhouObj.getString("labelsRotation") ;
					if(notEmpty(labelsRotation)){
						labelsObj.put("rotation", labelsRotation);
//						JSONObject rotationObj = new JSONObject();
//						rotationObj.put("rotation", labelsRotation);
//						retObj.put("labels" ,rotationObj);
					}
					String textColor = zhouObj.getString("textColor");
					if(notEmpty(textColor)){
						JSONObject styleObj = new JSONObject();
						styleObj.put("color", textColor);
						labelsObj.put("style", styleObj);
					}
					
					retObj.put("type" ,"category");
					if(zhouObj.getBoolean("allowDecimals")){
						retObj.put("allowDecimals" , true);
					}else{
						retObj.put("allowDecimals" , false);
					}
					if(zhouObj.getBoolean("opposite")){
						retObj.put("opposite" , true);
					}
					if(zhouObj.getBoolean("axisVisible")!=null && !zhouObj.getBoolean("axisVisible")){
						retObj.put("visible" , false);
					}
					setOBJ(zhouObj, retObj, "alternateGridColor", "alternateGridColor","string");
					setOBJ(zhouObj, retObj, "ceiling","ceiling","number");
					setOBJ(zhouObj, retObj, "floor" ,"floor","number");
					setOBJ(zhouObj, retObj, "max" ,"yMax","number");
					setOBJ(zhouObj, retObj, "min" ,"yMin","number");
					String axisPlotBands = zhouObj.getString("axisPlotBands");
					if(notEmpty(axisPlotBands)){
						retObj.put("plotBands" ,JSON.parseArray(axisPlotBands));
					}
					//x轴 雷达图 多边形 设置
					if(isPolarPolygon()){
						retObj.put("tickmarkPlacement", "on");
						retObj.put("lineWidth", "0");
					}
					retArray.add(retObj);
				}
				return retArray.toJSONString() ;
			}
		}
		JSONObject tObj = new JSONObject();
		tObj.put("text"," ");
		JSONObject emptyObj = new JSONObject();
		emptyObj.put("title", tObj);
		//x轴 雷达图 多边形 设置
		if(isPolarPolygon()){
			emptyObj.put("tickmarkPlacement", "on");
			emptyObj.put("lineWidth", "0");
		}
		retArray.add(emptyObj);
		return retArray.toJSONString() ;
	}
	public String getYzhou(){
		String zhou = source.get("yzhou");
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
					JSONObject titleObj = new JSONObject();
					if(notEmpty(title)){
						titleObj.put("text", title);
					}else{
						titleObj.put("text", " ");
					}
					retObj.put("title" ,titleObj);
					JSONObject labelsObj =  new JSONObject();
					retObj.put("labels" ,labelsObj);
					
					String labelsRotation = zhouObj.getString("labelsRotation") ;
					if(notEmpty(labelsRotation)){
						labelsObj.put("rotation", labelsRotation);
//						JSONObject rotationObj = new JSONObject();
//						rotationObj.put("rotation", labelsRotation);
//						retObj.put("labels" ,rotationObj);
					}
					String textColor = zhouObj.getString("textColor");
					if(notEmpty(textColor)){
						JSONObject styleObj = new JSONObject();
						styleObj.put("color", textColor);
						labelsObj.put("style", styleObj);
					}
					if(zhouObj.getBoolean("allowDecimals")){
						retObj.put("allowDecimals" , true);
					}else{
						retObj.put("allowDecimals" , false);
					}
					if(zhouObj.getBoolean("opposite")){
						retObj.put("opposite" , true);
					}
					if(zhouObj.getBoolean("axisVisible")!=null && !zhouObj.getBoolean("axisVisible")){
						retObj.put("visible" , false);
					}
					setOBJ(zhouObj, retObj, "alternateGridColor", "alternateGridColor","string");
					setOBJ(zhouObj, retObj, "ceiling","ceiling","number");
					setOBJ(zhouObj, retObj, "floor" ,"floor","number");
					setOBJ(zhouObj, retObj, "max" ,"yMax","number");
					setOBJ(zhouObj, retObj, "min" ,"yMin","number");
					//setOBJ(zhouObj, retObj, "plotBands" , "axisPlotBands");
					String axisPlotBands = zhouObj.getString("axisPlotBands");
					if(notEmpty(axisPlotBands)){
						retObj.put("plotBands" ,JSON.parseArray(axisPlotBands));
					}
					
					//x轴 雷达图 多边形 设置
					if(isPolarPolygon()){
						retObj.put("gridLineInterpolation", "polygon");
					}
					
					retArray.add(retObj);
				}
				return retArray.toJSONString() ;
			}
		}
		JSONObject tObj = new JSONObject();
		tObj.put("text"," ");
		JSONObject emptyObj = new JSONObject();
		emptyObj.put("title", tObj);
		//x轴 雷达图 多边形 设置
		if(isPolarPolygon()){
			emptyObj.put("gridLineInterpolation", "polygon");
		}
		retArray.add(emptyObj);
		return retArray.toJSONString() ;
	}
	/**
	 * x 轴标题
	 * @return
	 */
//	public String getXTitle(){
//		return source.get("xTitle");
//	}
	/**
	 * x 轴隔行颜色 
	 * @return
	 */
//	public String getXAlternateGridColor(){
//		return source.get("xAlternateGridColor");
//	}
	/**
	 * x 轴标签旋转角度
	 * @return
	 */
//	public String getXLabelsRotation(){
//		return source.get("xLabelsRotation");
//	}
	// ***************  y 轴  ***************
	/**
	 * y 轴标题
	 * @return
	 */
//	public String getYTitle(){
//		return source.get("yTitle");
//	}
	/**
	 * y 轴隔行颜色 
	 * @return
	 */
//	public String getYAlternateGridColor(){
//		return source.get("yAlternateGridColor");
//	}
	/**
	 * y 轴刻度允许小数
	 * @return
	 */
//	public String getYAllowDecimals(){
//		return source.get("yAllowDecimals");
//	}
	/**
	 * y 轴自动计算坐标刻度最大值
	 * @return
	 */
//	public String getYCeiling(){
//		return source.get("yCeiling");
//	}
	/**
	 * y 轴自动计算坐标刻度最小值
	 * @return
	 */
//	public String getYFloor(){
//		return source.get("yFloor");
//	}
	/**
	 * y 轴 最大值
	 * @return
	 */
//	public String getYMax(){
//		return source.get("yMax");
//	}
	/**
	 * y 轴 最小值
	 * @return
	 */
//	public String getYMin(){
//		return source.get("yMin");
//	}
	/**
	 * y 轴 坐标颜色
	 * @return
	 */
//	public String getYAxisPlotBands(){
//		String yAxisPlotBands = source.get("yAxisPlotBands");
//		if(yAxisPlotBands!=null && yAxisPlotBands!=""){
//			JSONArray plotArray = JSON.parseArray(yAxisPlotBands);
//			if(!plotArray.isEmpty()){
//				return plotArray.getJSONObject(0).getString("values");
//			}
//		}
//		return null ;
//	}
	// ***************  pane 适用于仪表盘  ***************
	public String getGaugeDialBcolor(){
		return source.getOrDefault("gaugeDialBcolor", "#000");
	}
	/**
	 *  仪表盘设置
	 * @return
	 */
	public String getPaneSet(){
		if(!"gauge".equalsIgnoreCase(source.get("chartsType"))){
			return null ;
		}
		String paneset = source.get("paneSet") ;
		JSONArray retAry = null ;
		if(paneset!=null && !"".equals(paneset)){
			JSONArray paneArray = JSON.parseArray(paneset);
			if(paneArray!=null &&!paneArray.isEmpty()){
				JSONObject paneObject = paneArray.getJSONObject(0);
				JSONArray valArray = paneObject.getJSONArray("values");
				if(valArray!=null &&!valArray.isEmpty()){
					retAry = new JSONArray();
					JSONObject peneObj ;
					JSONObject retObj ;
					for (Object object : valArray) {
						peneObj = (JSONObject) object ;
						retObj = new JSONObject();
						setOBJ(peneObj, retObj, "startAngle" ,"startAngle","string");
						setOBJ(peneObj, retObj, "endAngle" ,"endAngle","string");
						boolean flag = peneObj.getBoolean("backOpacity")==null?false:peneObj.getBoolean("backOpacity") ;
						if(flag){
							retObj.put("background", "");
						}else{
							setOBJ(peneObj, retObj, "background" ,"background","string");
						}
						setOBJ(peneObj, retObj, "size" ,"size","string");
						String centerX = peneObj.getString("centerX");
						String centerY = peneObj.getString("centerY");
						if(notEmpty(centerY) && notEmpty(centerX)){
							JSONArray centerArray = new JSONArray();
							centerArray.add(centerX);
							centerArray.add(centerY);
							retObj.put("center", centerArray);
						}
						retAry.add(retObj);
					}
				}
			}
		}
		return retAry==null?null:retAry.toJSONString();
	}
	
//	/**
//	 *  仪表盘刻度开始角度
//	 * @return
//	 */
//	public String getPaneStartAngle(){
//		return source.get("paneStartAngle");
//	}
//	/**
//	 *  仪表盘刻度结束角度
//	 * @return
//	 */
//	public String getPaneEndAngle(){
//		return source.get("paneEndAngle");
//	}
	
	/**
	 * 饼图字体和饼图的距离
	 */
	public String getPieDataLabelsDistance(){
		return source.get("pieDataLabelsDistance");
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
		this.source = source ;
	}

	@Override
	public void setFormRules(List<FormRule> formRules) {

	}

	@Override
	public void setFormComponents(List<FormComponent> formComponents) {

	}

	@Override
	public String getElementTagName() {
		return "div";
	}

	@Override
	public String getElementHtml() {
		// TODO Auto-generated method stub
		return null;
	}
	//获取第一层
//	@Override
//	public String getElementHtml() {
//		String rid = getRuleId();
//		String formHtml = formPage.getFormHtml();
//		Document doc = Jsoup.parse(formHtml);
//		Element ele = doc.getElementById(this.getId());
//		ele.removeAttr("contenteditable");
//		ele.html("");
//		Element div = new Element(Tag.valueOf("div"), "");
//		if("img".equals(ele.tagName())){
//			Attributes attrs= ele.attributes();
//			Iterator<Attribute> it = attrs.iterator();
//			while(it.hasNext()){
//				Attribute attr= it.next();
//				if(!"src".equals(attr.getKey())){
//					div.attr(attr.getKey(),attr.getValue());
//				}			
//			}
//		}else{
//			div=ele;
//		}
//		return div.toString();
//	}

	@Override
	public String getRuleId() {
		return source.get("ruleId");
	}

	@Override
	public String getId() {
		return source.get("id");
	}

	@Override
	public String getName() {
		return source.get("name");
	}

	@Override
	public String getValue() {
		return source.get("value");
	}

	@Override
	public boolean isReadable() {
		return false;
	}

	@Override
	public boolean isRequired() {
		return false;
	}

	@Override
	public boolean isWritable() {
		return false;
	}
	
	private boolean notEmpty(String str){
		if(str!=null && !"".equals(str.trim())){
			return true ;
		}
		return false ;
	}
	/**  柱、列 边距   **/
	/**
	 * 柱宽
	 * @return
	 */
	public String getColumnPointWidth(){
		return source.get("columnPointWidth"); 
	}
	/**
	 * 柱边距
	 * @return
	 */
	public String getColumnPointPadding(){
		return source.get("columnPointPadding"); 
	}
	/**
	 * 组柱边距
	 * @return
	 */
	public String getColumnGroupPadding(){
		return source.get("columnGroupPadding"); 
	}
	
	public String isVisible(){
		String visible = source.get("visible");
		return "true".equals(visible)?null:"none" ;
	}

	public void setScriptMap(Map<String, String> scriptMap) {
		this.scriptMap = scriptMap;
	}
	/**
	 * 是否树形
	 * @return
	 */
	public String getTree(){
		if ("true".equalsIgnoreCase(source.get("tree"))) {
			return "true";
		}
		return "false";
		
	}
	
	/**
	 * 图例顺序
	 */
	public String getSortLegend(){
		String sortLegend = source.get("sortLegend") ;
		JSONArray retAry = new JSONArray() ;
		if(notEmpty(sortLegend)){
			JSONArray sortLegendAry = JSON.parseArray(sortLegend);
			if(sortLegendAry!=null && !sortLegendAry.isEmpty()){
				JSONObject sortObj = null ;
				for (Object object : sortLegendAry) {
					sortObj = (JSONObject) object ;
					retAry.add(sortObj.getInteger("name")) ;
				}
				return retAry.toJSONString() ;
			}
		}
		return null ;
	}
	
	/**
	 * 颜色根据柱自动分派
	 * @return
	 */
	public String getColorByPoint(){
		return source.get("colorByPoint");
	}
	
	/**
	 * 图例显示值模板
	 * @return
	 */
	public String getSeriesDataLabelsFormat(){
		String text =  source.get("seriesDataLabelsFormat") ;
		String retAry = null ;
		if(StringUtils.isNotEmpty(text)){
			try {
				JSONArray array = JSON.parseArray(text);
				if(array!=null && !array.isEmpty()){
					JSONObject obj = array.getJSONObject(0);
					JSONObject subObj = obj.getJSONObject("htmldata") ;
					retAry = subObj.getString("htmlVal").replace("\"", "'");
				}
			} catch (Exception e) {
				retAry = text ; 
			}
		}
		return retAry;
	}
	
	/**
	 * 背景色
	 * @return
	 */
	public String getBackgroundColor(){
		 String bt = source.get("background_transparent") ;
		 if(StringUtils.isNotEmpty(bt) && Boolean.parseBoolean(bt)){
			 return "transparent";
		 }
		 return null;
	}
	
	public String getPieInnerSize(){
		return source.get("innerSize");
	}

	protected static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}
	/**
	 * 图例显示值模板
	 * @return
	 */
	public String getSeriesDataLabelsFormatExp(){
		String customDefined = source.get("seriesDataLabelsFormatExp");
		JSONArray result = new JSONArray();
		JSONArray htmlAry = null;
		if(!isNullOrEmpty(customDefined)){
			htmlAry = JSON.parseArray(customDefined);
			if(htmlAry!=null && !htmlAry.isEmpty()){
				JSONObject htmlObj = htmlAry.getJSONObject(0);
				JSONArray htmldataObjArray = htmlObj.getJSONArray("value");
				
				for(Object htmldataObj : htmldataObjArray){
					JSONObject htmldataJsonObj = (JSONObject)htmldataObj;
					String htmlExpression = htmldataJsonObj.getString("htmlExpression");
					String expression = htmldataJsonObj.getString("expression");
					
					JSONObject formatExpObj = new JSONObject();
					formatExpObj.put("expression", expression);
					
					String retAry = null ;
					String text = htmldataJsonObj.getString("htmldata");
					if(StringUtils.isNotEmpty(text)){
						try {
							JSONObject obj = JSONObject.parseObject(text);
							retAry = obj.getString("htmlVal").replace("\"", "'");
						} catch (Exception e) {
							retAry = text ; 
						}
					}
					
					formatExpObj.put("htmlExpression", retAry);
					result.add(formatExpObj);
				}
			}
		}
		return result.toJSONString();
	}
	
	/**
	 * 图例颜色显示值模板
	 * @return
	 */
	public String getSeriesDataLabelsColors(){
	
		String customDefined = source.get("seriesDataLabelsColors");
		JSONArray result = new JSONArray();
		JSONArray htmlAry = null;
		String reAry = null;
		if(!isNullOrEmpty(customDefined)){
			htmlAry = JSON.parseArray(customDefined);
			if(htmlAry!=null && !htmlAry.isEmpty()){
				JSONObject htmlObj = htmlAry.getJSONObject(0);
				JSONArray htmldataObjArray = htmlObj.getJSONArray("value");
				
				for(Object htmldataObj : htmldataObjArray){
					JSONObject htmldataJsonObj = (JSONObject)htmldataObj;
					String htmlExpression = htmldataJsonObj.getString("htmlExpression");
					String expression = htmldataJsonObj.getString("expression");
					
					JSONObject formatExpObj = new JSONObject();
					formatExpObj.put("expression", expression);
					
					String text = htmldataJsonObj.getString("color");
					formatExpObj.put("color", text);
					
					formatExpObj.put("htmlExpression", reAry);
					result.add(formatExpObj);
				}
			}
		}
		return result.toJSONString();
	}
	
}
