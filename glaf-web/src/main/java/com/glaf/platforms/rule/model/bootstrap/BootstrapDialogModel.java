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

public class BootstrapDialogModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String templateScript = "";

	// 增加模板
	public String getTemplateScript() {
		
		String script = "<link rel='stylesheet' type='text/css' href='${contextPath}/static/scripts/layer/skin/layer.css' />"
				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/bootstrapdialog/css/bootstrapDialog.css' />"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/bootstrapdialog/js/jquery.bootstrapDialog.min.js'></script>";
        scriptMap.put("bootstrapdialog", script);
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
	 * 显示或隐藏
	 * 
	 * @return
	 */
	public String getVisible() {
		if ("true".equalsIgnoreCase(source.get("visible"))) {
			return "true";
		}
		return "false";
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
	/**
	 * 编辑窗口自动关闭
	 * 
	 * @return
	 */
	public String getEditPanleAutoClose() {
		if (StringUtils.isNotEmpty(source.get("editPanleAutoClose")) && "true".equalsIgnoreCase(source.get("editPanleAutoClose"))) {
			return "true";
		}
		return "false";
	}

	@Override
	public void setFormRules(List<FormRule> formRules) {
	}

	@Override
	public void setFormComponents(List<FormComponent> formComponents) {

	}
	
	/**
	 * 是否异步
	 * 
	 * @return
	 */
	public String getIsSync() {
		String isSync = source.get("isSync");
		return isSync == null ? "false" : isSync;
	}
	
	/**
	 * 拷贝属性
	 * @param fromObj
	 * @param toObj
	 * @param arys
	 */
	private void convertObj(JSONObject fromObj,JSONObject toObj,String... arys){
		if(arys.length>0){
			for (String key : arys) {
				toObj.put(key, fromObj.get(key));
			}
		}
	}

	public String getStyleRule() {
		String dataLinkPage = source.get("dataLinkPage");
		StringBuffer styleSb = new StringBuffer();
		JSONObject retObj = new JSONObject();
		if (!isNullOrEmpty(dataLinkPage)) {
			JSONArray dataLinkPageAry = JSONArray.parseArray(dataLinkPage);
			if (!dataLinkPageAry.isEmpty()) {
				JSONObject dataLinkPageObj = dataLinkPageAry.getJSONObject(0);
				JSONObject valuesObj = dataLinkPageObj.getJSONObject("values");
				// 图标数组
				JSONArray iconAry = valuesObj.getJSONArray("icon");
				if (!iconAry.isEmpty()) {
					JSONObject iconObj;
					JSONObject icon ;
					List<JSONObject> list = new ArrayList<>();
					String model,iconstr;
					for (Object object : iconAry) {
						iconObj = (JSONObject) object;
						icon = new JSONObject();
						model = iconObj.getString("model");
						if("level".equalsIgnoreCase(model)){
							iconstr = iconObj.getString("type");
							// ico_docu  ico_open ico_close
							switch (iconstr) {
							case "iconOpen":
								iconstr = "button.ico_open" ;
								break;
							case "iconClose":
								iconstr = "button.ico_close" ;
								break;
							case "icon":
								iconstr = "button" ;
								break;
							default:
								iconstr = "button.ico_docu" ;
								break;
							}
							styleSb.append("#"+this.getId()+" a.level"+(iconObj.getIntValue("expression")-1)+" span."+iconstr);
							styleSb.append("{");
							styleSb.append("margin-right:2px; background: url(${contextPath}"+iconObj.getString("icon")+") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle");
							styleSb.append("}");
						}else if("custom".equalsIgnoreCase(model)){
							this.convertObj(iconObj, icon,"type","icon");
							String expdata = iconObj.getString("expdata");
							if(!isNullOrEmpty(expdata)){
								JSONObject expdataObj = JSON.parseObject(expdata);
								String es = HTMLExpressionConvertUtil.htmlConvertForZtree(expdataObj.getString("expActVal"));
								icon.put("expression", es);
							}
							list.add(icon);
						}
					}
					JSONArray jo = (JSONArray) JSON.toJSON(list);
					retObj.put("icon", jo);
				}
				JSONArray checkAry = valuesObj.getJSONArray("check");
				if (!checkAry.isEmpty()) {
					JSONObject checkObj;
					JSONObject check ;
					List<JSONObject> list = new ArrayList<>();
					String type;
					for (Object object : checkAry) {
						checkObj = (JSONObject) object;
						check = new JSONObject();
						this.convertObj(checkObj, check,"type");
						String expdata = checkObj.getString("expdata");
						if(!isNullOrEmpty(expdata)){
							JSONObject expdataObj = JSON.parseObject(expdata);
							String es = HTMLExpressionConvertUtil.htmlConvertForZtree(expdataObj.getString("expActVal"));
							check.put("expression", es);
						}
						list.add(check);
					}
					JSONArray jo = (JSONArray) JSON.toJSON(list);
					retObj.put("check", jo);
				}
				
				JSONArray textAry = valuesObj.getJSONArray("text");
				if (!textAry.isEmpty()) {
					JSONObject textObj;
					JSONObject text ;
					List<JSONObject> list = new ArrayList<>();
					String model,expdata,htmldata;
					for (Object object : textAry) {
						textObj = (JSONObject) object;
						text = new JSONObject();
						model = textObj.getString("model");
						if("custom".equalsIgnoreCase(model)){
							expdata = textObj.getString("expdata");
							if(!isNullOrEmpty(expdata)){
								JSONObject expdataObj = JSON.parseObject(expdata);
								String es = HTMLExpressionConvertUtil.htmlConvertForZtree(expdataObj.getString("expActVal"));
								text.put("expression", es);
							}
							htmldata = textObj.getString("htmldata");
							if(!isNullOrEmpty(htmldata)){
								JSONObject htmldataObj = JSON.parseObject(htmldata);
								String es = HTMLExpressionConvertUtil.htmlConvertForZtree(htmldataObj.getString("htmlVal"),"##+","+##");
								text.put("htmlVal", es.replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("</br>", ""));
							}
							list.add(text);
						}
					}
					JSONArray jo = (JSONArray) JSON.toJSON(list);
					retObj.put("text", jo);
				}
			}
		}
		if(styleSb.length()>0){
			this.addTemplate("<style>"+styleSb.toString()+"</style>");
		}
		return retObj.toJSONString();
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

}
