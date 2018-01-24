package com.glaf.cell;

import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public class InputBuilder {
	public static Element createInputText(String baseUri,Map<String,String> attributes) {
		 Element element=new Element(Tag.valueOf("input"),baseUri);
		 element.attr("type","text");
		 attributes.put("background", "transparent");
		 CssBuilder.builderCssByCellAttr(element,attributes);
		 CssBuilder.css(element,"border","none");
		 
		 return element;
	}
	public static Element createTextArea(String baseUri,Map<String,String> attributes) {
		 Element element=new Element(Tag.valueOf("textarea"),baseUri);
		 attributes.put("background", "transparent");
		 CssBuilder.builderCssByCellAttr(element,attributes);	
		 CssBuilder.css(element,"border","none");
		 return element;
	}
}
