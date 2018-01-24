package com.glaf.platforms.rule.model;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.context.ApplicationContext;
import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.util.FormDictoryFactory;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class UploadModel extends CommonModel implements IRule, CssRule, AttrRule {

	private static final long serialVersionUID = 1L;

	private String randomUUID;

	protected String templateScript = "";

	// 增加模板
	public String getTemplateScript() {
		//String script = "<script type='text/javascript' src='${contextPath}/scripts/uuid.js'></script>";
		//scriptMap.put("upload", script);

		return templateScript;
	}

	public void addTemplate(String subTemplate) {
		this.templateScript += subTemplate;
	}

	@Override
	public String getBind() {
		StringBuffer sb = new StringBuffer();
		sb.append("events:{");
		sb.append("upload: onUpload,");
		sb.append("remove: onRemove");
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String getElementTagName() {
		return null;
	}

	@Override
	public String getElementHtml() {
		return "<div style='width:" + this.getWidth() + ";height:" + this.getHeight()
				+ ";min-width:300px;'><input id='" + this.getId() + "' data-role='upload' /></div>";
	}

	public String getRandomParent() {
		// "randomParent":"{{randomparent}}"
		if (randomUUID == null || StringUtils.isEmpty(randomUUID)) {
			randomUUID = UUID.randomUUID().toString();
		}
		return randomUUID;
	}

	public boolean getAutoUpload() {
		return this.getBooleanValue("autoUpload", true);
	}

	public boolean getBatch() {
		return this.getBooleanValue("batch", false);
	}

	public String getRemoveField() {
		return this.getStringValue("removeField", null);
	}

	public String getRemoveUrl() {
		String contextpath = ApplicationContext.getContextPath();
		String removeUrl = FormDictoryFactory.getInstance().getFormDictoryByCode("removePath").getValue();
		return contextpath + removeUrl + "&from=" + this.getStringValue("saveOperation", "to_db");
	}

	public String getRemoveVerb() {
		return this.getStringValue("removeVerb", "POST");
	}

	public String getSaveField() {
		return this.getStringValue("saveField", null);
	}

	public String getSaveUrl() {
		String contextpath = ApplicationContext.getContextPath();
		String saveUrl = FormDictoryFactory.getInstance().getFormDictoryByCode("uploadPath").getValue();
		return contextpath + saveUrl + "&to=" + this.getStringValue("saveOperation", "to_db");
	}

	public String getDownloadUrl() {
		String contextpath = ApplicationContext.getContextPath();
		String downloadUrl = FormDictoryFactory.getInstance().getFormDictoryByCode("downloadPath").getValue();
		return contextpath + downloadUrl + "&from=" + this.getStringValue("saveOperation", "to_db");
	}

	public boolean getWithCredentials() {
		return this.getBooleanValue("withCredentials", true);
	}

	public String getFileName() {
		return this.getStringValue("fileName", "");
	}

	public int getFileSize() {
		return this.getIntValue("fileSize", 0);
	}

	public String getFileExtension() {
		return this.getStringValue("fileExtension", "");
	}

	public String getFileExtensionValue() {
		String code = this.getStringValue("fileExtension", "");
		if (StringUtils.isNotEmpty(code)) {
			FormDictory dict = FormDictoryFactory.getInstance().getFormDictoryByCode(code);
			return dict.getValue();
		}
		return "";
	}

	public int getMaxFileSize() {
		return this.getIntValue("maxFileSize", 5) * 1024 * 1024;
	}

	public boolean getMultiple() {
		return this.getBooleanValue("multiple", true);
	}

	public boolean getShowFileList() {
		return this.getBooleanValue("showFileList", true);
	}

	public String getLocalizationDropFilesHere() {
		return this.getStringValue("localizationDropFilesHere", null);
	}

	public String getLocalizationCancel() {
		return this.getStringValue("localizationCancel", null);
	}

	public String getLocalizationHeaderStatusUploading() {
		return this.getStringValue("localizationHeaderStatusUploading", null);
	}

	public String getLocalizationHeaderStatusUploaded() {
		return this.getStringValue("localizationHeaderStatusUploaded", null);
	}

	public String getLocalizationRemove() {
		return this.getStringValue("localizationRemove", null);
	}

	public String getLocalizationRetry() {
		return this.getStringValue("localizationRetry", null);
	}

	public String getLocalizationStatusFailed() {
		return this.getStringValue("localizationStatusFailed", null);
	}

	public String getLocalizationStatusUploaded() {
		return this.getStringValue("localizationStatusUploaded", null);
	}

	public String getLocalizationStatusUploading() {
		return this.getStringValue("localizationStatusUploading", null);
	}

	public String getLocalizationUploadSelectedFiles() {
		return this.getStringValue("localizationUploadSelectedFiles", null);
	}

	public String getLocalizationSelect() {
		return this.getStringValue("localizationSelect", null);
	}

	private String getStringValue(String key, String defaultValue) {
		return source.get(key) == null ? defaultValue : source.get(key);
	}

	private boolean getBooleanValue(String key, boolean defaultValue) {
		if (source.get(key) == null) {
			return defaultValue;
		} else {
			return Boolean.parseBoolean(source.get(key));
		}
	}

	private int getIntValue(String key, Integer defaultValue) {
		if (source.get(key) == null) {
			return defaultValue;
		} else {
			return Integer.parseInt(source.get(key));
		}
	}

	public String getOutPutName() {
		// [{"id":"textbox","text":"名称","parent":null,"name":"textbox"}]
		String outputnames = getStringValue("outName", null);
		String retAry = null;
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
	 * ID输入节点
	 * @return
	 */
	public String getOutPutId() {
		// [{"id":"textbox","text":"名称","parent":null,"name":"textbox"}]
		String outputIds = getStringValue("outId", null);
		String retAry = null;
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
