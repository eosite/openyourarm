package com.glaf.cell;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.glaf.cell.domain.CellObject;

public class HtmlConverter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 创建HTML文档
		Document doc = Jsoup
				.parse("<html xmlns=\"http://www.w3.org/1999/xhtml\"></html>");
		// 设置文档编码
		doc.charset(Charset.forName("utf-8"));
		// 创建HTML HEAD
		//Element head = doc.append("<head></head>");
		doc.append("<head></head>");
		// 创建HTML BODY
		Element body = doc.body();
		// 创建Table
		// Element table = body.append("<table></table>");
		HtmlConverter htmlConverter = new HtmlConverter();
		List<Map<String, String>> tableNodes = htmlConverter
				.getNodesByXmlFile("d:/a.xml");
		Map<String, String> attributeMap = null;
		Element table = null;
		Element tbody = null;
		Element tr = null;
		Element td = null;
		//Element component = null;
		for (int i = 0; i < tableNodes.size(); i++) {
			attributeMap = tableNodes.get(i);
			if (i == 0) {
				// 创建Table
				table = TableBuilder.createTable("", attributeMap);
				// Element thead = TableBuilder.createThead("", attributeMap,
				// attributeMap.get("title"));
				// table.appendChild(thead);
				tbody = TableBuilder.createTbody("", attributeMap);
				table.appendChild(tbody);
			} else {
				// 获取行列
				String rowNum = attributeMap.get("rowIndex");
				String colNum = attributeMap.get("colIndex");
				// 有效标识
				String vaildFlag = "true";
				if (attributeMap.containsKey("valid")) {
					vaildFlag = attributeMap.get("valid");
				}
				if (rowNum != null && rowNum.trim().length() > 0
						&& colNum != null && colNum.trim().length() > 0) {
					// 获取当前行
					tr = TableBuilder.getTrByRowNum(table,
							Integer.parseInt(rowNum));
					// 获取当前列
					td = TableBuilder.getTdByRowNum(table,
							Integer.parseInt(rowNum), Integer.parseInt(colNum));
					if (tr == null) {
						tr = TableBuilder.createTr("", attributeMap,
								Integer.parseInt(rowNum));
					}
					if (td == null && vaildFlag.equals("true")) {
						td = TableBuilder.createTd("", attributeMap,
								Integer.parseInt(rowNum),
								Integer.parseInt(colNum));
						// 获取包含控件类型
						String compType = attributeMap.get("nodeType");
						Element componentEle = HtmlBuilder.createComponent("",
								attributeMap, compType, "kendo");
						if (componentEle != null) {
							td.appendChild(componentEle);
						}
						tr.appendChild(td);
					}
					tbody.select("tr.row-" + rowNum).remove();
					tbody.appendChild(tr);
				}

			}
		}
		table.appendChild(tbody);
		body.appendChild(table);
		System.out.println(doc.html());
	}
    /**
     * 转换CELL表模板视图为HTML页面视图
     * @param xmlContent
     * @return
     * @throws UnsupportedEncodingException
     */
	public static CellObject ConvertCellViewToPageView(String xmlContent,String desPageType)
			throws UnsupportedEncodingException {
		CellObject cellObject=new CellObject();
		byte[] desContent = null;
		// 创建HTML文档
		Document doc = Jsoup
				.parse("<html xmlns=\"http://www.w3.org/1999/xhtml\"></html>");
		// 设置文档编码
		doc.charset(Charset.forName("utf-8"));
		// 创建HTML HEAD
		//Element head = doc.append("<head></head>");
		doc.append("<head></head>");
		// 创建HTML BODY
		Element body = doc.body();
		HtmlConverter htmlConverter = new HtmlConverter();
		List<Map<String, String>> tableNodes = htmlConverter
				.getNodesByXmlContent(xmlContent);
		cellObject.setPropList(tableNodes);
		Map<String, String> attributeMap = null;
		Element table = null;
		Element tbody = null;
		Element tr = null;
		Element td = null;
		Element componentEle = null;
		String compType = null;
		for (int i = 0; i < tableNodes.size(); i++) {
			attributeMap = tableNodes.get(i);
			if (i == 0) {
				// 创建Table
				table = TableBuilder.createTable("", attributeMap);
				tbody = TableBuilder.createTbody("", attributeMap);
				table.appendChild(tbody);
			} else {
				// 获取行列
				String rowNum = attributeMap.get("rowIndex");
				String colNum = attributeMap.get("colIndex");
				// 有效标识
				String vaildFlag = "true";
				if (attributeMap.containsKey("valid")) {
					vaildFlag = attributeMap.get("valid");
				}
				if (rowNum != null && rowNum.trim().length() > 0
						&& colNum != null && colNum.trim().length() > 0) {
					// 获取当前行
					tr = TableBuilder.getTrByRowNum(table,
							Integer.parseInt(rowNum));
					// 获取当前列
					td = TableBuilder.getTdByRowNum(table,
							Integer.parseInt(rowNum), Integer.parseInt(colNum));
					if (tr == null) {
						tr = TableBuilder.createTr("", attributeMap,
								Integer.parseInt(rowNum));
					}
					if (td == null && vaildFlag.equals("true")) {
						td = TableBuilder.createTd("", attributeMap,
								Integer.parseInt(rowNum),
								Integer.parseInt(colNum));
						// 获取包含控件类型
						compType = attributeMap.get("nodeType");
						componentEle = HtmlBuilder.createComponent("",
								attributeMap, compType, desPageType);
						if (componentEle != null) {
							td.appendChild(componentEle);
						}
						tr.appendChild(td);
					}
					tbody.select("tr.row-" + rowNum).remove();
					tbody.appendChild(tr);
				}

			}
		}
		if(table!=null)
		table.appendChild(tbody);
		if(body!=null)
		body.appendChild(table);
		desContent = doc.html().getBytes("UTF-8");
		cellObject.setDesContent(desContent);
		return cellObject;
	}

	/**
	 * 解析XML文件
	 * 
	 * @param filePath
	 * @return
	 */
	public List<Map<String, String>> getNodesByXmlFile(String filePath) {
		List<Map<String, String>> tableNodes = new ArrayList<Map<String, String>>();
		// 创建saxReader对象
		SAXReader reader = new SAXReader();
		// 通过read方法读取一个文件 转换成Document对象
		org.dom4j.Document document = null;
		try {
			document = reader.read(new File(filePath));
			// 获取根节点元素对象
			org.dom4j.Element node = document.getRootElement();

			if (node != null) {
				Map<String, String> attrMap = getNodeAttributes(node);
				tableNodes.add(attrMap);
				if (node.elements() != null && node.elements().size() > 0) {
					org.dom4j.Element element = null;
					for (int j = 0; j < node.elements().size(); j++) {
						element = (org.dom4j.Element) node.elements().get(j);
						attrMap = getNodeAttributes(element);
						tableNodes.add(attrMap);
					}
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return tableNodes;
	}

	/**
	 * 解析XML文件
	 * 
	 * @param content
	 * @return
	 */
	public List<Map<String, String>> getNodesByXmlContent(String content) {
		List<Map<String, String>> tableNodes = new ArrayList<Map<String, String>>();
		org.dom4j.Document document = null;
		try {
			document = DocumentHelper.parseText(content);
			// 获取根节点元素对象
			org.dom4j.Element node = document.getRootElement();

			if (node != null) {
				Map<String, String> attrMap = getNodeAttributes(node);
				tableNodes.add(attrMap);
				if (node.elements() != null && node.elements().size() > 0) {
					org.dom4j.Element element = null;
					for (int j = 0; j < node.elements().size(); j++) {
						element = (org.dom4j.Element) node.elements().get(j);
						attrMap = getNodeAttributes(element);
						tableNodes.add(attrMap);
					}
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return tableNodes;
	}

	/**
	 * 获取节点属性
	 * 
	 * @param node
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, String> getNodeAttributes(org.dom4j.Element node) {
		Map<String, String> attrMap = new HashMap<String, String>();
		if (node != null) {
			List attrs = node.attributes();
			Attribute attribute = null;
			for (int i = 0; i < attrs.size(); i++) {
				attribute = (Attribute) attrs.get(i);
				attrMap.put(attribute.getName(), attribute.getValue());
			}
		}
		return attrMap;
	}
}