package com.glaf.cell;

import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

public class TableBuilder {
	/**
	 * 创建Table
	 * @return
	 */
	public static Element createTable(String baseUri,Map<String,String> attributes) {
		 Element element=new Element(Tag.valueOf("table"),baseUri);
		 element.attr("cellpadding","0");
		 element.attr("cellspacing","0");
		 CssBuilder.builderCssByCellAttr(element,attributes);	
		 return element;
	}
	/**
	 * 创建表头
	 * @param baseUri
	 * @param attributes
	 * @param title
	 * @return
	 */
	public static Element createThead(String baseUri,Map<String,String> attributes,String title){
		Element thead=new Element(Tag.valueOf("thead"),baseUri);
		Element tr=new Element(Tag.valueOf("tr"),"");
		Element th=new Element(Tag.valueOf("th"),"");
		th.appendText(title);
		if(attributes.containsKey("columns")){
			th.attr("colspan", attributes.get("columns"));
		}
		tr.appendChild(th);
		thead.appendChild(tr);
		return thead;
	}
	/**
	 * 创建Table Body
	 * @return
	 */
	public static Element createTbody(String baseUri,Map<String,String> attributes) {
		 Element element=new Element(Tag.valueOf("tbody"),baseUri);
		 //CssBuilder.builderCssByCellAttr(element,attributes);	
		 return element;
	}
	/**
	 * 创建行
	 * @param html
	 * @param attributes
	 * @return
	 */
	public static Element createTr(String baseUri,Map<String,String> attributes,int rowNum){
		Element element=new Element(Tag.valueOf("tr"),baseUri);
		//CssBuilder.builderCssByCellAttr(element,attributes);	
		element.addClass("row-"+rowNum);
		return element;
	}
	/**
	 * 创建列
	 * @param attributes
	 * @return
	 */
	public static Element createTd(String baseUri,Map<String,String> attributes,int rowNum,int colNum){
		Element element=new Element(Tag.valueOf("td"),baseUri);
		CssBuilder.builderCssByCellAttr(element,attributes);
		element.addClass("row-"+rowNum);
		element.addClass("col-"+colNum);
		if(attributes.containsKey("colSpan")){
			element.attr("colspan", attributes.get("colSpan"));
		}
        if(attributes.containsKey("rowSpan")){
        	element.attr("rowspan", attributes.get("rowSpan"));
		}
		return element;
	}
	/**
	 * 获取行
	 * @param table
	 * @param rowNum
	 * @return
	 */
	public static Element getTrByRowNum(Element table,int rowNum){
		Elements elements=table.select("tr.row-"+rowNum);
		return elements!=null&&elements.size()>0?elements.get(0):null;
	}
	/**
	 * 获取某行某列
	 * @param table
	 * @param rowNum
	 * @return
	 */
	public static Element getTdByRowNum(Element table,int rowNum,int colNum){
		Elements elements=table.select("td.row-"+rowNum).select("td.col-"+colNum);
		return elements!=null&&elements.size()>0?elements.get(0):null;
	}
}
