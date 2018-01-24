package com.glaf.cell;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.nodes.Element;

public class CssBuilder {

	/**
	 * 设置元素样式
	 * 
	 * @param element
	 * @param styleName
	 * @param styleValue
	 * @return
	 */
	public static Element css(Element element, String styleName, String styleValue) {
		// 获取当前元素样式集合
		Map<String, String> styleMap = getElementCss(element);
		styleMap.put(styleName, styleValue);
		String style=builderElementCss(styleMap);
		element.attr("style",style);
		return element;
	}
	/**
	 * 根据cell表模板属性设置样式
	 * @param element
	 * @param attributes
	 * @return
	 */
    public static Element builderCssByCellAttr(Element element,Map<String, String> attributes){
    	if(attributes!=null){
			String attrName=null;
			String attrValue=null;
			for(Entry<String,String> attribute:attributes.entrySet()){
				attrName=attribute.getKey().toLowerCase();
				attrValue=attribute.getValue();
				switch (attrName){
				case "width":
					element=css(element, attrName, attrValue+"px");
					if(attrValue.equals("0"))
					element=css(element, "display", "none");
					break;
				case "height":
					element=css(element, attrName, attrValue+"px");
					if(attrValue.equals("0"))
				    element=css(element, "display", "none");
					break;
				case "background":
					element=css(element, attrName, attrValue);
					break;
				case "foreground":
					element=css(element, "font-color", attrValue);
					break;
				case "displaytype":
					if(element.tagName().equals("td")){
						break;
					}
					if(attrValue.equals("1"))
					element=css(element, "display", "none");
					break;
				case "ishide":
					if(element.tagName().equals("td")){
						break;
					}
					if(attrValue.equals("True"))
					element=css(element, "display", "none");
					break;
				case "textalignment":
					element=css(element, "text-align", attrValue);
					break;	
					//title
				case "verticalalignment":
					element=css(element, " vertical-align", attrValue);
					break;		
				case "fontname":
					element=css(element, "font-family", attrValue);
					break;			
				case "isbold":
					if(attrValue.equals("true"))
					element=css(element, "font-weight", "bolder");
					break;			
				case "isitalic":
					if(attrValue.equals("true"))
					element=css(element, "font-style", "italic");
					break;		
				case "fontsize":
					element=css(element, "font-size",attrValue+"pt");
					break;	
				case "border":
					if(!element.tagName().equals("td")){
						break;
					}
					String cellBorderStyles[]=attrValue.split(",");
					String borderStyle=null;
					int i=0;
					for(String cellBorderStyle:cellBorderStyles){
						borderStyle=BorderStyle.getBorderTyle(Integer.parseInt(cellBorderStyle));
						if(i==0)
						element=css(element, "border-left",borderStyle);
						else if(i==1)
						element=css(element, "border-top",borderStyle);
						else if(i==2)
						element=css(element, "border-right",borderStyle);
						else if(i==3)
						element=css(element, "border-bottom",borderStyle);
						i++;
					}
					break;
				case "borderColor":
					if(!element.tagName().equals("td")){
						break;
					}
					String cellBorderColors[]=attrValue.split(",");
					int j=0;
					for(String cellBorderColor:cellBorderColors){
						if(j==0)
						element=css(element, "border-left-color",cellBorderColor);
						else if(j==1)
						element=css(element, "border-top-color",cellBorderColor);
						else if(j==2)
						element=css(element, "border-right-color",cellBorderColor);
						else if(j==3)
						element=css(element, "border-bottom-color",cellBorderColor);
						j++;
					}
					break;
				default:
					element=element.attr(attrName, attrValue);
					break;
				}
			}
    	}
    	return element;
    }
	/**
	 * 设置元素样式
	 * 
	 * @param element
	 * @param styleName
	 * @param styleValue
	 * @return
	 */
	public static Element removeCss(Element element, String styleName) {
		// 获取当前元素样式集合
		Map<String, String> styleMap = getElementCss(element);
		styleMap.remove(styleName);
		String style=builderElementCss(styleMap);
		element.attr("style",style);
		return element;
	}

	/**
	 * 获取元素样式值
	 * 
	 * @return
	 */
	public static String getCss(Element element, String styleName) {
		// 获取当前元素样式集合
		Map<String, String> styleMap = getElementCss(element);
		return styleMap.get(styleName);
	}

	/**
	 * 获取元素样式集合
	 * 
	 * @param element
	 * @return
	 */
	public static Map<String, String> getElementCss(Element element) {
		// 获取当前style属性值
		String style = element.attr("style");
		Map<String, String> styleMap = new HashMap<String, String>();
		String[] styleItems = null;
		if (style != null && style.trim().length() > 0) {
			// 获取样式表
			styleItems = style.split(";");
			String[] styleItemArr = null;
			for (String styleItem : styleItems) {
				styleItemArr = styleItem.split(":");
				if(styleItemArr.length==2)
				styleMap.put(styleItemArr[0], styleItemArr[1]);
			}
		}
		return styleMap;
	}

	/**
	 * 生成元素样式
	 * 
	 * @param styleMap
	 * @return
	 */
	public static String builderElementCss(Map<String, String> styleMap) {
		StringBuilder stylebd = new StringBuilder();
		if (styleMap != null) {
			for (Entry<String, String> styleItem : styleMap.entrySet()) {
				stylebd.append(styleItem.getKey()+":"+styleItem.getValue()+";");
			}
		}
		return stylebd.toString();
	}
}
