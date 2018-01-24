package com.glaf.platforms.rule.model.bootstrap;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.domain.FormPage;
import com.glaf.form.core.util.FormDictoryFactory;
import com.glaf.platforms.rule.model.UploadModel;

public class Jqfileupload2Model extends UploadModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getSaveUrl() {
		return super.getSaveUrl()+"&randomParent="/*+super.getRandomParent()*/;
	}
	
	public String getFileExtensionValue() {
		String code = source.getOrDefault("fileExtension", "");
		if(code == null || code.isEmpty()){
			JSONArray dict = FormDictoryFactory.getInstance().getFormDictoryJSONArrayByTreeCode("fileExtension");
			return dict.toJSONString();
		}else{
			FormDictory dict = FormDictoryFactory.getInstance().getFormDictoryByCode(code);
			JSONObject rs = dict.toJsonObject();
			JSONArray dictarray = new JSONArray();
			dictarray.add(rs);
			return dictarray.toJSONString();
		}
		
	}
	
	public String getCutquality(){
		return source.get("cutquality");
	}
	
	public String getCutwidth(){
		return source.get("cutwidth");
	}
	
	public String getFileNumber(){
		return source.getOrDefault("fileNumber","10");
	}
	
	private int getIntValue(String key, Integer defaultValue) {
		if (source.get(key) == null) {
			return defaultValue;
		} else {
			return Integer.parseInt(source.get(key));
		}
	}
	
	public int getMaxtotalsize(){
		return this.getIntValue("maxtotalsize",50) * 1024 * 1024;
	}
	
	public String getCompressable(){
		return source.getOrDefault("compressable","false");
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
	
	public boolean isReadOnly() {
		return Boolean.parseBoolean(this.getSource("readOnly", "false"));
	}
	
	//是否覆盖上传，即保存时会将之前的randomParent删除再新增。
	public String getCoverable(){
		return this.getSource("coverable", "false");
	}
	
	// 增加模板
	public String getTemplateScript() {
		String script = "";
		scriptMap.put("jqfileupload2", script);
		return templateScript;
	}
	@Override
	public String getWidth() {
		return source.get("width");
	}

	@Override
	public String getHeight() {

		return source.get("height");
	}

	public String getPreviewable(){
		return source.get("previewable");
	}
}
