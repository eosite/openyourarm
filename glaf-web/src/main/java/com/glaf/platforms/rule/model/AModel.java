package com.glaf.platforms.rule.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class AModel extends CommonModel implements IRule, CssRule,
		AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getClick(){
		//方法类型
		String clickLink = source.get("clickLink") ;
		if ( !isNullOrEmpty(clickLink)) {
			//获取规则 -->输入表达式定义
			String paraType = source.get("paraType");
			JSONObject paraTypeObject = null ;
			if(!isNullOrEmpty(paraType)){
				JSONArray paraTypeArray = JSON.parseArray(paraType);
				if(paraTypeArray!=null && !paraTypeArray.isEmpty()){
					paraTypeObject = paraTypeArray.getJSONObject(0);
				}
			}
			//联动页面需要的参数
			String linkPage = source.get("linkPage"); //联动页面
			JSONObject params = new JSONObject() ;
			if(!isNullOrEmpty(linkPage)){
				JSONArray linkPages = JSON.parseArray(linkPage);
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
			}
			return "pubsub.pub('"+clickLink+"',"+(paraTypeObject==null?"''":paraTypeObject.getString("datas"))+","+params.toJSONString()+");" ;
		} else {
			return null;
		}

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
	
	@Override
	public String getElementHtml() {
		
		String formHtml = formPage.getFormHtml();
		
		Document doc = Jsoup.parse(formHtml);
		
		Element ele = doc.getElementById(super.getId());

		this.attrVisible(ele);
		this.attrActVal(ele);
		
		String html = ele.outerHtml();
		
		return html;
	}
	/**
	 * 添加真实值
	 * @param ele
	 */
	private void attrActVal(Element ele){
		ele.attr("actVal", source.get("actVal"));
	}
	/**
	 * 显示隐藏
	 * @param ele
	 */
	private void attrVisible(Element ele) {
		if (!this.isVisible().equals("true")) {
			ele.attr("style", ele.attr("style") + "display: none;");
		}
	}

}
