package com.glaf.platforms.rule.model.bootstrap;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.expression.core.util.ExpressionConvertUtil;
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class CalendarBtModel extends CommonModel implements IRule, CssRule, AttrRule {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String getElementTagName() {
		return null;
	}

	@Override
	public String getElementHtml() {
		String rid = super.getRuleId();
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml);
		Element ele = doc.getElementById(this.getId());
		ele.attr("rid",rid);
//		ele.removeAttr("contenteditable");
		if ("false".equals(this.isVisible())) {
			ele.attr("style", "display:none;"+ele.attr("style"));
		}
		if("true".equals(source.getOrDefault("disabledTime", "false"))){
			ele.addClass("calendarbt_disabledtime");
		}
		return ele.toString();
	}
	@Override
	public String getBind() {
		return null;
	}
	
	/**
	 * 标题名称
	 * @return
	 */
	public String getTitle(){
		return super.getSource("title", "");
	}
	
	
	/**
	 * 工具条样式
	 * @return
	 */
	public String getToolbar() {
		return super.getSource("toolbar", "four");
	}
	
	public String getDefaultView(){
		return super.getSource("defaultView", "month");
	}
	
	//获取数据集字段名称
	public String getDataSourceSet(){
		JSONObject coordInfo = new JSONObject() ;
		String dataset = source.get("dataSourceSet");
		if(notEmpty(dataset)){
			JSONArray dataSetAry = JSON.parseArray(dataset);
			if(dataSetAry!=null && !dataSetAry.isEmpty()){
				JSONObject dataSetObj = dataSetAry.getJSONObject(0);
				JSONArray columns = dataSetObj.getJSONArray("selectColumns");
				if(columns!=null && !columns.isEmpty()){
					JSONObject colunm = null ;
					String ctype = null ;
					for (Object object : columns) {
						colunm = (JSONObject) object ;
						ctype = colunm.getString("ctype") ;
						if(notEmpty(ctype)){
							coordInfo.put(ctype, colunm.getString("columnName"));
						}
					}
				}
			}
		}
		return coordInfo.toJSONString();
	}
	
	
	//判断条件
	public String getDataLinkPage(){
		
		String dataStyle = source.get("dataLinkPage"); //样式定义
		JSONArray retAry = null ;
		if(notEmpty(dataStyle)){
			JSONArray dataStyleAry = JSON.parseArray(dataStyle);
			if(dataStyleAry!=null && !dataStyleAry.isEmpty()){
				JSONObject valueObj = null ;
				retAry = new JSONArray();
				JSONObject retObj = null ;
				JSONArray paramAry = null ;
			
				for (Object object : dataStyleAry) {
					valueObj = (JSONObject) object ;
					retObj = new JSONObject();
					
					retObj.put("name", valueObj.getString("name"));
					retObj.put("bgcColor", valueObj.getString("bgcColor"));
					retObj.put("urlId", valueObj.getString("urlId"));
					retObj.put("windowName", valueObj.getString("windowName"));
					retObj.put("windowWidth", valueObj.getString("windowWidth"));
					retObj.put("windowHeight", valueObj.getString("windowHeight"));
					
					//输入输出参数
					String paramStr = valueObj.getString("param") ;
					if(notEmpty(paramStr)){
						paramAry = JSON.parseArray(paramStr);
						if(paramAry!=null && !paramAry.isEmpty()){
							retObj.put("rules", paramAry.getJSONObject(0).getJSONObject("datas").toJSONString());
						}
					}
					
					JSONObject expdata = JSON.parseObject(valueObj.getString("expdata"));
					String expVal = expdata.getString("expActVal");
					retObj.put("exp",ExpressionConvertUtil.expressionConvert(HTMLExpressionConvertUtil.imageConvert(expVal), ExpressionConvertUtil.JAVASCRIPT_TYPE));
					
					retAry.add(retObj);
				}
				return retAry.toJSONString();
			}
		}
		return null;
	}
	
	@Override
    public void setScriptMap(Map<String, String> scriptMap) {
    	this.scriptMap = scriptMap;
		StringBuffer SB = new StringBuffer();

		SB.append("<link href=\"${contextPath}/scripts/plugins/bootstrap/calendar/css/fullcalendar.min.css\" rel=\"stylesheet\" type=\"text/css\" />");
		SB.append("<link href=\"${contextPath}/scripts/plugins/bootstrap/calendar/ext/calendar.extends.css\" rel=\"stylesheet\" type=\"text/css\" />");
		SB.append("<script type=\"text/javascript\" src=\"${contextPath}/scripts/plugins/bootstrap/calendar/js/moment.min.js\"></script>");
		SB.append("<script type=\"text/javascript\" src=\"${contextPath}/scripts/plugins/bootstrap/calendar/js/fullcalendar.min.js\"></script>");
		SB.append("<script type=\"text/javascript\" src=\"${contextPath}/scripts/plugins/bootstrap/calendar/ext/lang-zh-cn.js\"></script>");
		SB.append("<script type=\"text/javascript\" src=\"${contextPath}/scripts/plugins/bootstrap/calendar/ext/jquery.calendar.extends.js\"></script>");
	
		this.scriptMap.put("calendarbt", SB.toString());
    }


	
	private boolean notEmpty(String str){
		if(str!=null && !"".equals(str.trim())){
			return true ;
		}
		return false ;
	}
	
	public String getBasicModel(){
		return source.getOrDefault("basicModel", "false");
	}
	
}
