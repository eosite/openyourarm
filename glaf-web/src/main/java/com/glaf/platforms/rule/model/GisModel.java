package com.glaf.platforms.rule.model;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemConfig;
import com.glaf.form.core.domain.FormComponent;
import com.glaf.form.core.domain.FormRule;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class GisModel implements IRule, CssRule, AttrRule {
	
	private static final long serialVersionUID = 1L;
	protected Map<String, String> source;
	protected String templateScript = "" ;
	//增加模板
	public String getTemplateScript(){
		String linkPage = source.get("linkPage"); //联动页面
		JSONArray linkPages = JSON.parseArray(linkPage);
		JSONObject params = new JSONObject() ;
		if(linkPages!=null && !linkPages.isEmpty()){
			String linkstr = linkPages.getJSONObject(0).toJSONString();
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
		
				
		String paraType = source.get("paraType"); //规则
		JSONArray paraTypes = JSON.parseArray(paraType);
		String rules = null ;
		if(paraTypes!=null && !paraTypes.isEmpty()){
			rules = paraTypes.getJSONObject(0).getJSONObject("datas").toJSONString();
		}
		
		String clickType = source.get("click");// 事件类型
		
		StringBuffer sb = new StringBuffer("<script >") ;
		sb.append("function ShowPopup(index_id){");
		sb.append("pubsub.pub('"+clickType+"',"+rules+","+params.toJSONString()+",index_id)");
		sb.append("}");
		sb.append("</script>");
		this.addTemplate(sb.toString());
		return this.templateScript;
	}
	public void addTemplate(String subTemplate){
		this.templateScript += subTemplate ;
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
		return null;
	}

	@Override
	public String getElementHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<object id='"+this.getId()+"' data='data:application/x-silverlight-2,' type='application/x-silverlight-2' >");
		sb.append("<param name='source' value='${contextPath}/ClientBin/GISSystem.xap'/>")	;
		sb.append("<param name='onError' value='onSilverlightError' />");
		sb.append("<param name='onLoad' value='SilverlightLoaded'/ >");
		sb.append("<param name='background' value='white' />");
		sb.append("<param name='windowless' value='true' />");
		sb.append("<param name='minRuntimeVersion' value='5.0.61118.0' />");
		sb.append("<param name='autoUpgrade' value='true' />");
		sb.append("<param name='initParams'");
		sb.append("value='MapType = 1, MapUrl="+SystemConfig.getString("gisServiceUrl")+", FeatureLayer=48, area=117.85|26.53|119.11|27.16' />");
		sb.append("<a href='http://go.microsoft.com/fwlink/?LinkID=149156&v=5.0.61118.0' style='text-decoration: none'>");
		sb.append("<img src='http://go.microsoft.com/fwlink/?LinkId=161376' alt='获取 Microsoft Silverlight' style='border-style: none' />");
		sb.append("</a>");
		sb.append("</object> ;");
		return sb.toString();
	}

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
}
