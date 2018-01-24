package com.glaf.datamgr.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import com.glaf.core.base.TableModel;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.IOUtils;
import com.glaf.core.xml.XmlMappingReader;

public class TableModelReader {

	public static void main(String[] args) {
		// System.setProperty("config.path",
		// "E:/iss_develop/Java/glaf-web/WebContent/WEB-INF");
		// TableModelReader reader = new TableModelReader();
		// Document doc = reader.mergeExportMapping();
		// System.out.println(doc.getRootElement().asXML());
	}

	public Document mergeExportMapping(List<String> includes) {
		List<String> tables = new ArrayList<String>();
		if (includes != null && !includes.isEmpty()) {
			for (String table : includes) {
				tables.add(table.toLowerCase());
			}
		}
		Document document = DocumentHelper.createDocument();
		Element n_root = document.addElement("表结构");
		String path = System.getProperty("config.path") + "/conf/export/mapping";
		File dir = new File(path);
		File[] files = dir.listFiles();
		if (files != null && files.length > 0) {
			SAXReader xmlReader = new SAXReader();
			InputStream inputStream = null;
			for (File file : files) {
				if (file.getName().endsWith(".mapping.xml")) {
					try {
						inputStream = new FileInputStream(file);
						Document doc = xmlReader.read(inputStream);
						Element root = doc.getRootElement();
						Element element = root.element("entity");
						Element n_element = n_root.addElement("表内容");
						String table = element.attributeValue("table");
						if (tables.size() > 0) {
							if (!tables.contains(table.toLowerCase())) {
								continue;
							}
						}
						n_element.addAttribute("name", table);

						if (element.element("id") != null) {
							Element elem = element.element("id");
							Element em = n_element.addElement("id");
							em.addAttribute("name", elem.attributeValue("name"));
							em.addAttribute("column", elem.attributeValue("column"));
							em.addAttribute("type", elem.attributeValue("type"));
							if (elem.attributeValue("title") != null) {
								em.addAttribute("title", elem.attributeValue("title"));
							}
							if (elem.attributeValue("length") != null) {
								em.addAttribute("length", elem.attributeValue("length"));
							}
						}

						List<?> elems = element.elements("property");
						Iterator<?> iterator = elems.iterator();
						while (iterator.hasNext()) {
							Element elem = (Element) iterator.next();
							Element em = n_element.addElement("property");
							em.addAttribute("name", elem.attributeValue("name"));
							em.addAttribute("column", elem.attributeValue("column"));
							em.addAttribute("type", elem.attributeValue("type"));
							if (elem.attributeValue("title") != null) {
								em.addAttribute("title", elem.attributeValue("title"));
							}
							if (elem.attributeValue("length") != null) {
								em.addAttribute("length", elem.attributeValue("length"));
							}
						}

					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						IOUtils.closeStream(inputStream);
					}
				}

			}
		}
		return document;
	}

	public List<TableModel> readModels() {
		List<TableModel> rows = new ArrayList<TableModel>();
		XmlMappingReader reader = new XmlMappingReader();
		String path = SystemProperties.getConfigRootPath() + "/conf/dts/mapping";
		File dir = new File(path);
		File[] files = dir.listFiles();
		if (files != null && files.length > 0) {
			InputStream inputStream = null;
			for (File file : files) {
				if (file.getName().endsWith(".mapping.xml")) {
					try {
						inputStream = new FileInputStream(file);
						TableModel tableModel = reader.read(inputStream);
						rows.add(tableModel);
					} catch (IOException e) {
					} finally {
						IOUtils.closeStream(inputStream);
					}
				}
			}
		}
		return rows;
	}

}
