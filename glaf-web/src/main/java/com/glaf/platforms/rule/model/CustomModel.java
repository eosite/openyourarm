package com.glaf.platforms.rule.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

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

public class CustomModel extends CommonModel implements IRule, CssRule, AttrRule {
	
	private static final long serialVersionUID = 1L;
	
	private String prefixStr = "custom";
	private Elements as = new Elements();
	
	/**
	 * 返回初始化方法
	 */
	public String getInit(){
		JSONArray retAry = new JSONArray();
		JSONObject retObj = null ;
		String imageDefined = source.get("customDefined");
		JSONArray idArray = JSON.parseArray(imageDefined);
		if(idArray!=null && !idArray.isEmpty()){
			JSONObject idObject = idArray.getJSONObject(0);
			JSONArray value = idObject.getJSONArray("value");
			if(value != null && !value.isEmpty()){
				JSONObject valueObj = null;
				for (Object object : value) {
					valueObj = (JSONObject) object ;
					JSONObject expdata = JSON.parseObject(valueObj.getString("expdata"));
					JSONObject htmldata = JSON.parseObject(valueObj.getString("htmldata"));
					String expVal = expdata.getString("expActVal");
					String htmlVal = htmldata.getString("htmlVal");
					retObj = new JSONObject();
					retObj.put("key",ExpressionConvertUtil.expressionConvert(HTMLExpressionConvertUtil.imageConvert(expVal), ExpressionConvertUtil.JAVASCRIPT_TYPE));
					retObj.put("val", ExpressionConvertUtil.expressionConvert(HTMLExpressionConvertUtil.imageConvert(htmlVal), ExpressionConvertUtil.JAVASCRIPT_TYPE));
					retAry.add(retObj);
					pushDomElement(htmlVal,as);
					addHrefRole(as);
				}
			}	
		}
		return retAry.toJSONString() ;
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
	public String getElementTagName() {
		return null;
	}

	@Override
	public String getElementHtml() {
		//imageDefined
		return "<p id='"+this.getId()+"' data-role='custom'></p>";
	}
	
	public String getRstyle(){
		Element ele = getElement();
		return ele.attr("style");
	}
	/**
	 * 显示控件
	 * 
	 * @return
	 */
	public String isVisible() {
		if (!Boolean.valueOf(source.get("visible"))) {
			return "none";
		}else{
			return "";
		}
	}
	
	/**
	 * 放大显示
	 * @return
	 */
	public String getZoomView(){
		return source.get("zoomView");
	}
	
	public Map<String,String> getDataSource(){
		Map<String,String> map = new HashMap<>();
		String dataSourceSet = source.get("dataSourceSet");
		if(!isNullOrEmpty(dataSourceSet)){
			JSONArray dsAry = JSON.parseArray(dataSourceSet);
			if(dsAry!=null && !dsAry.isEmpty()){
				JSONObject jo = dsAry.getJSONObject(0);
				JSONArray columns = jo.getJSONArray("columns");
				if(columns!=null && !columns.isEmpty()){
					JSONObject col = null ;
					for (Object object : columns) {
						col = (JSONObject) object ;
						map.put(col.getString("code"), col.getString("columnName"));
					}
				}
			}
		}
		return  map ;
	}
	
	
	/**
	 * 解析html定义获取所有的动态事件控件，push 所有到集合中
	 */
	public void pushDomElement(String hidLinkImg,Elements as){
		if(StringUtils.isNotEmpty(hidLinkImg)){
			Document doc = Jsoup.parse(hidLinkImg);
			as.addAll(doc.select("a[t-events=true]"));
		}
	}
	
	/**
	 * 删除自动生成的按钮
	 */
	public void deleteHrefRole(Document doc) {
		Elements eles = doc.select("#" + prefixStr+this.getId()+"-" + this.getRuleId());
		if (eles != null) {
			eles.remove();
		}
	}
	/**
	 * 动态生成按钮
	 */
	public void addHrefRole(Elements as) {
		String html = formPage.getFormHtml();
		if (html != null) {
			Document fromHtml = Jsoup.parse(html);
			this.deleteHrefRole(fromHtml);
			/**
			 * 
			 */
			if (as != null && as.size() > 0) {
				Element div = new Element(Tag.valueOf("div"), ""), button;
				div.attr("id", prefixStr +this.getId()+"-"+ this.getRuleId());
				div.attr("style", "display:none;");
				for (Element a : as) {
					a.attr("href", "javascript:void(0);");
					button = new Element(Tag.valueOf("button"), "");
					// button.attr("class", "k-button");
					button.text(a.attr("title"));
					button.attr("id", a.attr("btn-id"));
					button.attr("t-events", "true");
					button.attr("data-role", "button");
					button.attr("crtltype", "kendo");
					button.attr("title", a.attr("title"));
					button.attr("cname", a.text());
					div.appendChild(button);
				}
				fromHtml.body().appendChild(div);
				formPage.setFormHtml(fromHtml.html());
			}
		}
	}
}
