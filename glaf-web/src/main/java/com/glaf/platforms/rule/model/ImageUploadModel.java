package com.glaf.platforms.rule.model;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.context.ApplicationContext;
import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.util.FormDictoryFactory;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class ImageUploadModel extends CommonModel implements IRule, CssRule, AttrRule {

	private static final long serialVersionUID = 1L;
	private String randomUUID;

	public String getRandomParent() {
		//"randomParent":"{{randomparent}}",
		if (randomUUID == null || StringUtils.isEmpty(randomUUID)) {
			randomUUID = UUID.randomUUID().toString();
		}
		return randomUUID;
	}

	@Override
	public String getBind() {
		return null;
	}

	protected String templateScript = "";

	// 增加模板
	public String getTemplateScript() {
		String script = "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/imageupload.css' ></link>"
				/*+ "<script type='text/javascript' src='${contextPath}/scripts/uuid.js'></script>"*/;
		// 修改备注 限制页面只加载一次js
		scriptMap.put("imageUpload", script);
		scriptMap.put("ajaxFileUpload", "<script type='text/javascript' src='${contextPath}/webfile/js/ajaxfileupload.js' ></script>");
		scriptMap.put("exif","<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/exif/js/exif.js'></script>");
		return "";
	}

	public void addTemplate(String subTemplate) {
		this.templateScript += subTemplate;
	}

	@Override
	public String getElementHtml() {
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml); 
		Element ele = doc.getElementById(this.getId());
		
		String preImage = ele.attr("preImage");
		String borderWidth = ele.attr("borderWidth");
		String radiu=ele.attr("border-radius:");
		
		String style = "width:"+ getWidth() + ";overflow:hidden;height:" + getHeight() + ";border-radius:"+radiu+";";
		String visible = "";
		if("false".equalsIgnoreCase(this.isVisible())){
			visible = "display:none;" ;
		}
		String enable = "";
		if(!this.isEnabled()){
			enable = "display:none;" ;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<div id='" + getId() + "' data-role='imageupload' class='image_upload_div' style='"+style+ "background-image:"+ preImage +";border-width:"+borderWidth+";"+visible+"'>");
//		sb.append("<div id='progress_bar_" + getId() + "' class='img_upload_progress_bar'></div>");
		sb.append("<div class=\"imageupload_tools\" style=\""+style+enable+"\">");
		sb.append("<a href='#' class='image_upload_btn_select' style=\"line-height: "+getHeight()+";left:0px;\">");
		sb.append("<img class=\"imageupload_del\" title=\"删除图片\" alt=\"删除\" src=\"${contextPath}/images/del.png\"  onclick=\"return imageuploadFunc.delImg(this)\">");
		sb.append("<input class='image_upload_input_file' type='file' name='file' id='img_upload_input_" + getId()
				+ "'/>选择图片</a>");
		sb.append("</div>");
		
		String isWin = source.get("isWin");
		String showWin = "" ; 
		if(isWin!=null && "true".equals(isWin)){
			showWin = "showWin" ;
		}
		String ableresize = this.source.get("ableresize");
//		String ableresize = this.source.getOrDefault("ableresize", "false");
		String imgStyle = "";
		if(ableresize!= null && ableresize.equals("true")){
			imgStyle += "width:100%;height:100%;";
		}
		sb.append("<img class='imageupload_class "+this.zoomView()+"Img "+showWin+"' id='img_" + getId() + "' width='100%' height='100%' style='"+imgStyle+"display:none;'/>");
		sb.append("</div>");

		return sb.toString();
	}

	@Override
	public String getElementTagName() {

		return null;
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

	private String getStringValue(String key, String defaultValue) {
		return source.get(key) == null ? defaultValue : source.get(key);
	}

	private int getIntValue(String key, Integer defaultValue) {
		if (source.get(key) == null) {
			return defaultValue;
		} else {
			return Integer.parseInt(source.get(key));
		}
	}

	public String getSaveUrl() {
		String contextpath = ApplicationContext.getContextPath();
		String saveUrl = FormDictoryFactory.getInstance().getFormDictoryByCode("imgUploadPath").getValue();
		return contextpath + saveUrl + "&to=" + this.getStringValue("saveOperation", "to_db");
	}

	public String getDownloadUrl() {
		String contextpath = ApplicationContext.getContextPath();
		String downloadUrl = FormDictoryFactory.getInstance().getFormDictoryByCode("imgDownloadPath").getValue();
		return contextpath + downloadUrl + "&from=" + this.getStringValue("saveOperation", "to_db");
	}

	public String getRemoveUrl() {
		String contextpath = ApplicationContext.getContextPath();
		String removeUrl = FormDictoryFactory.getInstance().getFormDictoryByCode("imgRemovePath").getValue();
		return contextpath + removeUrl + "&from=" + this.getStringValue("saveOperation", "to_db");
	}

	public String getMaxFileSize() {
		return (this.getIntValue("maxFileSize", 5) * 1024 * 1024)+"";
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
	private String zoomView(){
		return source.get("zoomView"); 
	}
}
