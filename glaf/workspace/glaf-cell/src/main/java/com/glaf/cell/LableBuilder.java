package com.glaf.cell;

import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public class LableBuilder {
	
	public static Element createLable(String baseUri,Map<String,String> attributes) {
		 Element element=new Element(Tag.valueOf("lable"),baseUri);
		 CssBuilder.builderCssByCellAttr(element,attributes);	
		 String title=attributes.get("title");
		 if(title!=null&&title.trim().length()>0)
		 {
			 title=title.replaceAll(" ", "&nbsp;");
			 title=title.replaceAll("	", "&nbsp;&nbsp;&nbsp;");
			 element.append(title);
		 }
		 return element;
	}	
}
