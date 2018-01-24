package com.glaf.platforms.rule.model.bootstrap;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormPage;
import com.glaf.platforms.rule.model.ComboboxModel;

public class IcheckboxModel extends ComboboxModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Map<String, String> scriptMap;
	protected FormPage formPage;

	public String getBind() {
		return null;
	}
	
	public Element getElement() {
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml);
		Element ele = doc.getElementById(this.getId());
		if ("false".equals(this.isVisible())) {
			ele.attr("style", "display:none;"+ele.attr("style"));
		}
		return ele;
	}

	public String getElementTagName() {
		return null;
	}
	
	public String getElementHtml() {
		Element ele = getElement();
		return ele.toString();
	}
	
	public void setScriptMap(Map<String, String> scriptMap) {
		this.scriptMap = scriptMap;
		StringBuffer SB = new StringBuffer();

		//SB.append("<script src=\"${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js\"></script>");
		SB.append("<script src=\"${contextPath}/scripts/plugins/bootstrap/icheck/jquery.checkbox.extends.js\"></script>");

		this.scriptMap.put("icheckbox", SB.toString());
	}

	public void setFormPage(FormPage formPage) {
		this.formPage = formPage;
	}
	
	public String getStatic(){
		return source.get("static");
	}
	public String getColumnNumber(){
		return source.get("columnNumber");
	}
	public String getIcheckSet(){
		String zhou = source.get("icheckSet");
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
					
					setOBJ(zhouObj, retObj, "fontSize", "fontSize","string");
					setOBJ(zhouObj, retObj, "textColor", "textColor","string");
					setOBJ(zhouObj, retObj, "fontWeight", "fontWeight","string");
					setOBJ(zhouObj, retObj, "fontStyle", "fontStyle","string");
					retArray.add(retObj);
				}
				return retArray.toJSONString() ;
			}
		}
		
		return retArray.toJSONString() ;
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
	private boolean notEmpty(String str){
		if(str!=null && !"".equals(str.trim())){
			return true ;
		}
		return false ;
	}
	public String getCheckBoxWidth(){
		return source.get("checkboxwidth");
	}
	public String getCheckBoxHeight(){
		return source.get("checkboxheight");
	}
	//全选
    public String getCheckAll(){
    	return source.get("checkall");
    }
}
