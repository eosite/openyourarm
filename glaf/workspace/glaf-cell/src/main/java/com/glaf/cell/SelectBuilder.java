package com.glaf.cell;

import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public class SelectBuilder {
	public static Element createSelect(String baseUri,Map<String,String> attributes) {
		 Element element=new Element(Tag.valueOf("select"),baseUri);
		 CssBuilder.builderCssByCellAttr(element,attributes);	
		 return element;
	}
}
