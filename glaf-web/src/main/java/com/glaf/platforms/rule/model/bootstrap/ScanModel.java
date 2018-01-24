package com.glaf.platforms.rule.model.bootstrap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.context.ApplicationContext;
import com.glaf.form.core.util.FormDictoryFactory;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class ScanModel extends CommonModel implements IRule, CssRule,
		AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 增加模板
	public String getTemplateScript() {
		String script = "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/scan/ext/jquery.scan.ext.js' ></script>";
		scriptMap.put("scan", script);
		return "";
	}
	
	@Override
	public String getBind() {
		return null;
	}

	public String getElementTagName() {
		return null;
	}


	@Override
	public boolean isEnabled() {
		return Boolean.parseBoolean(this.source.get("enabled"));
	}
	
	public String getSaveUrl() {
		String contextpath = ApplicationContext.getContextPath();
		String saveUrl = FormDictoryFactory.getInstance().getFormDictoryByCode("imgUploadPath").getValue();
		return contextpath + saveUrl + "&to=" + source.getOrDefault("saveOperation", "to_db");
	}
	
	@Override
	public String getElementHtml() {
		
		/*"id":"{{id}}",
	     "downloadUrl":"{{downloadurl}}",
	    "saveUrl":"{{saveurl}}",
	    "removeUrl":"{{removeurl}}",
	     "attachmentId":"",
	     outputName:"{{outputname}}",*/
		String id = this.getId();
		Element ele = new org.jsoup.nodes.Element("div");
		ele.attr("id",id);
		ele.attr("data-role","scan");
		ele.attr("data-rid",this.getRuleId());
		//ele.attr("saveurl",this.getSaveUrl());
		ele.attr("outputName",this.getOutPutName());
		ele.attr("outputId",this.getOutPutId());
		ele.append("<object id=\""+id+"_obj\"  name=\""+id+"_obj\"  width=\"100%\" height=\"100%\" classid=\"CLSID:598237A0-1A96-442B-90EC-622B086E7BBC\"></object>");
		/*Element inputEle = new org.jsoup.nodes.Element("input") ;
		inputEle.attr("id",this.getId()+"_file");
		inputEle.attr("type","file");
		ele.append(inputEle.outerHtml());//style=\"display:none;\">
*/		ele.append("<script-tag language=\"javascript\">var "+id+"_obj = document.getElementById(\""+id+"_obj\");"
				+ "try{"+id+"_obj.SetSession(\"GLAF_COOKIE_glaf=\" + $.cookie(\"GLAF_COOKIE_glaf\"),"
				+ "window.location.origin + contextPath+\"/mx/upload/file/upload\");}catch(e){console.log(e)};"
				//+ "window.onbeforeunload = function(){"+id+"_obj.CloseForm();var parent_element = "+id+"_obj.parentElement;"
				//+ "while (parent_element.children.length){parent_element.removeChild(parent_element.children[0]);}};"
				+ "</script-tag>");
		ele.append("<script-tag language=\"javascript\"  for=\""+id+"_obj\"  event=\"OnSaveFile(nVar)\">$(\"#"+id+"\").scan('upload',"+id+"_obj.SaveFile(nVar));</script-tag>");
		return ele.outerHtml();
	}

	
	/**
	 *  输出控件
	 * @return
	 */
	public String getOutPutName() {
		// [{"id":"textbox","text":"名称","parent":null,"name":"textbox"}]
		String outputnames = source.getOrDefault("outName", null);
		String retAry = "";
		if (!isNullOrEmpty(outputnames)) {
			JSONArray outputAry = JSON.parseArray(outputnames);
			if (outputAry != null && !outputAry.isEmpty()) {
				JSONObject jo;
				retAry = "";
				for (Object object : outputAry) {
					jo = (JSONObject) object;
					retAry += jo.getString("id") + ",";
				}
				return retAry.substring(0, retAry.length() - 1);
			}
		}
		return retAry;
	}
	
	/**
	 * ID输出节点
	 * @return
	 */
	public String getOutPutId() {
		// [{"id":"textbox","text":"名称","parent":null,"name":"textbox"}]
		String outputIds = source.getOrDefault("outId", null);
		String retAry = "";
		if (!isNullOrEmpty(outputIds)) {
			JSONArray outputAry = JSON.parseArray(outputIds);
			if (outputAry != null && !outputAry.isEmpty()) {
				JSONObject jo;
				retAry = "";
				for (Object object : outputAry) {
					jo = (JSONObject) object;
					retAry += jo.getString("id") + ",";
				}
				return retAry.substring(0, retAry.length() - 1);
			}
		}
		return retAry;
	}

}
