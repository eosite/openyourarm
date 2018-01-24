/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.matrix.data.sync.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.core.util.Tools;
import com.glaf.matrix.data.sync.model.ColumnMapping;
import com.glaf.matrix.data.sync.model.TableSyncDefinition;
import com.glaf.matrix.data.sync.model.TargetTable;

public class TableSyncXmlReader {

	protected void read(Element elem, ColumnMapping model) {
		List<?> attrs = elem.attributes();
		if (attrs != null && !attrs.isEmpty()) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Iterator<?> iter = attrs.iterator();
			while (iter.hasNext()) {
				Attribute attr = (Attribute) iter.next();
				dataMap.put(attr.getName(), attr.getStringValue());
			}
			Tools.populate(model, dataMap);
			model.setAttributeMap(dataMap);
		}

		model.setTitle(elem.attributeValue("title"));
		model.setSrcTableColumn(elem.attributeValue("srcTableColumn"));
		model.setTargetTableColumn(elem.attributeValue("targetTableColumn"));
		model.setValueExpression(elem.attributeValue("valueExpression"));
		model.setInitValue(elem.attributeValue("initValue"));
	}

	protected void read(Element elem, TableSyncDefinition model) {
		List<?> attrs = elem.attributes();
		if (attrs != null && !attrs.isEmpty()) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Iterator<?> iter = attrs.iterator();
			while (iter.hasNext()) {
				Attribute attr = (Attribute) iter.next();
				dataMap.put(attr.getName(), attr.getStringValue());
			}
			Tools.populate(model, dataMap);
			model.setAttributeMap(dataMap);
		}

		model.setTitle(elem.attributeValue("title"));
		model.setTable(elem.attributeValue("table"));
		model.setPrimaryKey(elem.attributeValue("primaryKey"));

	}

	protected void read(Element elem, TargetTable model) {
		List<?> attrs = elem.attributes();
		if (attrs != null && !attrs.isEmpty()) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Iterator<?> iter = attrs.iterator();
			while (iter.hasNext()) {
				Attribute attr = (Attribute) iter.next();
				dataMap.put(attr.getName(), attr.getStringValue());
			}
			Tools.populate(model, dataMap);
			model.setAttributeMap(dataMap);
		}

		model.setTitle(elem.attributeValue("title"));
		model.setTable(elem.attributeValue("table"));
		model.setPrimaryKey(elem.attributeValue("primaryKey"));
		model.setReprocessor(elem.attributeValue("reprocessor"));
		model.setAfterSql(elem.attributeValue("afterSql"));
		model.setBeforeSql(elem.attributeValue("beforeSql"));

		if ("true".equals(elem.attributeValue("multi"))) {
			model.setMulti(true);
		} else {
			model.setMulti(false);
		}

		Element elem2 = elem.element("afterDynamicSqls");
		if (elem2 != null) {
			List<?> list5 = elem.elements("afterDynamicSql");
			if (list5 != null && list5.size() > 0) {
				Iterator<?> iterator5 = list5.iterator();
				while (iterator5.hasNext()) {
					Element elem5 = (Element) iterator5.next();
					model.addAfterDynamicSql(elem5.getStringValue());
				}
			}
		}

	}

	public List<TableSyncDefinition> read(java.io.InputStream inputStream) {
		List<TableSyncDefinition> rows = new ArrayList<TableSyncDefinition>();
		SAXReader xmlReader = new SAXReader();
		try {
			Document doc = xmlReader.read(inputStream);
			Element root = doc.getRootElement();
			List<?> list = root.elements("tableSync");
			if (list != null && list.size() > 0) {
				Iterator<?> iterator = list.iterator();
				while (iterator.hasNext()) {
					Element elem = (Element) iterator.next();
					TableSyncDefinition tableSync = new TableSyncDefinition();
					this.read(elem, tableSync);
					rows.add(tableSync);
					List<?> list2 = elem.elements("target");
					if (list2 != null && list2.size() > 0) {
						Iterator<?> iterator2 = list2.iterator();
						while (iterator2.hasNext()) {
							Element elem2 = (Element) iterator2.next();
							TargetTable table = new TargetTable();
							this.read(elem2, table);
							tableSync.addTargetTable(table);

							List<?> list3 = elem2.elements("mapping");
							if (list3 != null && list3.size() > 0) {
								Iterator<?> iterator3 = list3.iterator();
								while (iterator3.hasNext()) {
									Element elem3 = (Element) iterator3.next();
									ColumnMapping col = new ColumnMapping();
									this.read(elem3, col);
									table.addColumn(col);
								}
							}

							List<?> list4 = elem2.elements("child");
							if (list4 != null && list4.size() > 0) {
								Iterator<?> iterator4 = list4.iterator();
								while (iterator4.hasNext()) {
									Element elem4 = (Element) iterator4.next();
									TargetTable child = new TargetTable();
									this.read(elem4, child);
									table.addChild(child);

									List<?> list5 = elem4.elements("mapping");
									if (list5 != null && list5.size() > 0) {
										Iterator<?> iterator5 = list5.iterator();
										while (iterator5.hasNext()) {
											Element elem5 = (Element) iterator5.next();
											ColumnMapping col = new ColumnMapping();
											this.read(elem5, col);
											child.addColumn(col);
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return rows;
	}

}
