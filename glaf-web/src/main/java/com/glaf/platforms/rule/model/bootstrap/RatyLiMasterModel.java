package com.glaf.platforms.rule.model.bootstrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class RatyLiMasterModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String templateScript = "";

	// 增加模板
	public String getTemplateScript() {
		
		String script = "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/bootstrap/css/font-awesome.min.css'>"
				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/Ratyli-master/demo/css/ratyli.css' />"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/Ratyli-master/jquery.ratyli.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/Ratyli-master/demo/js/jquery.ratylimaster.extends.js'></script>";

		scriptMap.put("ratylimaster", script);
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
	/**
	 * 普通表
	 * 
	 * @return
	 */
	public String getNormal() {
		if ("true".equalsIgnoreCase(source.get("normal"))) {
			return "true";
		}
		return "false";
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

		return null;
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

	@Override
	public String getRuleId() {
		return source.get("ruleId");
	}

	
	/**
	 * 层级数量
	 * 
	 * @return
	 */
	
	public String getCount() {

		return source.get("count");
	}
	
	/**
	 * 形状
	 * 
	 * @return
	 */
	public String getState() {

		return source.get("state");
	}
	
	
	/**
	 * 联动控件（输出控件）
	 * 
	 * @return
	 */
	public String getLinkageControl() {
		// return (source.get("linkageControl")!=null &&
		// StringUtils.isNotEmpty(source.get("linkageControl").toString())) ?
		// source.get("linkageControl") :"''";
		return null;
	}
	/**
	 * 输入形参
	 * 
	 * @return
	 */
	public String getInParamDefined() {
		// return (source.get("linkageControl")!=null &&
		// StringUtils.isNotEmpty(source.get("linkageControl").toString())) ?
		// source.get("linkageControl") :"''";
		String s = source.get("inParamDefined");
		return source.get("inParamDefined");
	}
	

	@Override
	public void setFormRules(List<FormRule> formRules) {
	}

	@Override
	public void setFormComponents(List<FormComponent> formComponents) {

	}
	

	
	public String getFontCss() {
		String dataLinkPage = source.get("dataLinkPage");
		StringBuffer sb = new StringBuffer();
		if (!isNullOrEmpty(dataLinkPage)) {
			JSONArray dataLinkPageAry = JSONArray.parseArray(dataLinkPage);
			if (!dataLinkPageAry.isEmpty()) {
				JSONObject dataLinkPageObj = dataLinkPageAry.getJSONObject(0);
				JSONObject valuesObj = dataLinkPageObj.getJSONObject("values");
				
				JSONArray textAry = valuesObj.getJSONArray("text");
				if (!textAry.isEmpty()) {
					sb.append("function(treeId, treeNode){");
					JSONObject textObj;
					String model;
					boolean isLevel = false;
					for (Object object : textAry) {
						textObj = (JSONObject) object;
						model = textObj.getString("model");
						if("level".equalsIgnoreCase(model)){
							isLevel = true;
							sb.append("if(treeNode.level=="+(textObj.getIntValue("expression")-1)+"){return {color:\""+textObj.getString("color")+"\"};};");
						}
					}
					sb.append("return {};}");
					if(isLevel){
						return sb.toString();
					}
				}
			}
		}
		return null;
	}
	/**
	 * 获取输入形参规则中的对象规则
	 * @return
	 */
	public String getInParamObjRule(){
		String inPamranDefinedStr = this.source.get("inParamDefined");
		JSONObject ret = new JSONObject();
		if(StringUtils.isNotEmpty(inPamranDefinedStr)){
			JSONArray inPamranDefinedArray = JSON.parseArray(inPamranDefinedStr);
			if(inPamranDefinedArray!= null && !inPamranDefinedArray.isEmpty()){
				JSONObject obj = inPamranDefinedArray.getJSONObject(0);
				ret.put("objSource", obj.getJSONArray("objSource"));
				JSONArray arySource = obj.getJSONArray("arySource");
				if(arySource!= null && !arySource.isEmpty()){
					for(Object aryObj : arySource){
						JSONObject aryJson = (JSONObject)aryObj;
						JSONArray aryChildrenArray = aryJson.getJSONArray("child");
						for(Object aryChildObj : aryChildrenArray){
							JSONObject aryChildJson = (JSONObject)aryChildObj;
							aryChildJson.remove("child");
						}
						aryJson.put("child", aryChildrenArray);
					}
					ret.put("arySource", arySource);
				}
				return ret.toJSONString();
			}
		}
		return "[]";
	}
}
