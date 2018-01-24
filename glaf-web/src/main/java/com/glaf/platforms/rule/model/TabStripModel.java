package com.glaf.platforms.rule.model;

import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class TabStripModel extends CommonModel implements IRule, CssRule, AttrRule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		if(noFrame()){
			return null ;
		}
		return "div";
	}
	
	@Override
	public String getElementHtml() {
		return null;
	}
	
	private boolean noFrame(){
		Element ele = super.getElement();
		String model = ele.attr("model");
		if(model!=null && !"".equals(model)){
			return true ;
		}
		return false;
	}
	/**
	 * 图片
	 */
	public String getImageUrl() {
		return "imageUrl";
	}
	/**
	 * 滚动条
	 */
	public String getScrollable() {
		return source.get("scrollable");
	}
	/**
	 * 内联宽度
	 */
	public String getInnerWidth() {
		return source.get("innerWidth");
	}
	/**
	 * 内联高度
	 */
	public String getInnerHeight() {
		return source.get("innerHeight");
	}
	/**
	 * 数据源
	 */
	public String getDataSource() {
		if(noFrame()){
			return null;
		}
		String linkPage = source.get("linkPage") ;
		if(linkPage!=null&&!"".equals(linkPage)){
			JSONArray linkArray = JSON.parseArray(linkPage);
			if(linkArray!=null && !linkArray.isEmpty()){
				JSONObject linkObject = null ;
				for (Object object : linkArray) {
					linkObject = (JSONObject) object ;
					linkObject.remove("htmldata");
				}
				return ".append("+linkArray.toJSONString().replace("\"", "'")+")";
			}
		}
		return null ;
	}
	
	/**
	 * 选卡方位
	 */
	public String getTabPosition(){
		return source.get("tabPosition") ;
	}
	/**
	 * 默认展开
	 * @return
	 */
	public String getIsSelect() {
		String isSelect = source.get("isSelect");
		if(isSelect!=null && "true".equals(isSelect)){
			return ".select(0)";
		}
		return null ;
	}
	/**
	 * 折叠
	 * @return
	 */
	public String getCollapsible() {
		return source.get("collapsible");
	}
	
	public String isVisible(){
		if ("false".equals(source.get("visible"))) {
			return "display: none;";
		}
		return "" ;
	}
	
	public String getVisibleExtent(){
		return "$(\"#"+this.getId()+"\").kendoTabstripHidden();";
	}

	@Override
	public String getBind() {
		return null;
	}
}
