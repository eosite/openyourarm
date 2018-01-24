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

package com.glaf.datamgr.parse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.db.TableDataManager;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.xml.XmlMappingReader;
import com.glaf.core.xml.XmlReader;

public class JsonParser implements Parser {

	public static void main(String[] args) throws Exception {
		String mappingFile = "./report/mapping/SKU.mapping.xml";
		String dataFile = "./report/data/SKU.json";
		XmlReader reader = new XmlReader();
		TableDefinition tableDefinition = reader.read(new java.io.FileInputStream(mappingFile));
		if (tableDefinition != null) {
			System.out.println("TableName:" + tableDefinition.getTableName());
			if (DBUtils.tableExists(tableDefinition.getTableName())) {
				com.glaf.core.util.DBUtils.alterTable(tableDefinition);
			} else {
				com.glaf.core.util.DBUtils.createTable(tableDefinition);
			}
		}
		XmlMappingReader xmlReader = new XmlMappingReader();
		TableModel tableModel = xmlReader.read(new java.io.FileInputStream(mappingFile));
		JsonParser parser = new JsonParser();
		List<TableModel> rows = parser.parse(tableModel, new java.io.FileInputStream(dataFile));
		for (TableModel row : rows) {
			System.out.println(row.toString());
		}
		ITableDefinitionService tableDefinitionService = ContextFactory.getBean("tableDefinitionService");
		tableDefinitionService.save(tableDefinition);

		// ITableDataService tableDataService =
		// ContextFactory.getBean("tableDataService");
		// tableDataService.saveAll(tableModel.getTableName(), null, rows);

		TableDataManager manager = new TableDataManager();
		manager.saveAll("default", tableDefinition, null, rows);
	}

	public List<TableModel> parse(TableModel tableModel, java.io.InputStream data) {
		List<TableModel> rows = new java.util.ArrayList<TableModel>();
		try {
			byte[] bytes = FileUtils.getBytes(data);
			String text = new String(bytes);
			JSONArray jsonArray = JSON.parseArray(text);
			int len = jsonArray.size();
			for (int i = 0; i < len; i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				Map<String, Object> dataMap = new HashMap<String, Object>();
				Set<Entry<String, Object>> entrySet = json.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					String key = entry.getKey();
					Object value = entry.getValue();
					dataMap.put(key, value);
					dataMap.put(key.toLowerCase(), value);
				}

				TableModel row = new TableModel();
				row.setTableName(tableModel.getTableName());
				if (tableModel.getIdColumn() != null) {
					ColumnModel idColumn = tableModel.getIdColumn();
					if (idColumn.getName() != null && dataMap.get(idColumn.getName().toLowerCase()) != null) {
						ColumnModel col = new ColumnModel();
						col.setColumnName(idColumn.getColumnName());
						col.setName(idColumn.getName());
						col.setJavaType(idColumn.getJavaType());
						col.setValue(dataMap.get(idColumn.getName().toLowerCase()));
						switch (idColumn.getJavaType()) {
						case "Integer":
							col.setValue(ParamUtils.getInt(dataMap, idColumn.getColumnName().toLowerCase()));
							break;
						case "Long":
							col.setValue(ParamUtils.getLong(dataMap, idColumn.getColumnName().toLowerCase()));
							break;
						default:
							col.setValue(ParamUtils.getString(dataMap, idColumn.getColumnName().toLowerCase()));
							break;
						}
						row.setIdColumn(col);
					} else if (idColumn.getColumnName() != null
							&& dataMap.get(idColumn.getColumnName().toLowerCase()) != null) {
						ColumnModel col = new ColumnModel();
						col.setColumnName(idColumn.getColumnName());
						col.setName(idColumn.getName());
						col.setJavaType(idColumn.getJavaType());
						col.setValue(dataMap.get(idColumn.getColumnName().toLowerCase()));
						switch (idColumn.getJavaType()) {
						case "Integer":
							col.setValue(ParamUtils.getInt(dataMap, idColumn.getColumnName().toLowerCase()));
							break;
						case "Long":
							col.setValue(ParamUtils.getLong(dataMap, idColumn.getColumnName().toLowerCase()));
							break;
						default:
							col.setValue(ParamUtils.getString(dataMap, idColumn.getColumnName().toLowerCase()));
							break;
						}
						row.setIdColumn(col);
					}
				}

				List<ColumnModel> columns = tableModel.getColumns();
				if (columns != null && !columns.isEmpty()) {
					for (ColumnModel column : columns) {
						if (column.getName() != null && dataMap.get(column.getName().toLowerCase()) != null) {
							ColumnModel col = new ColumnModel();
							col.setColumnName(column.getColumnName());
							col.setName(column.getName());
							col.setJavaType(column.getJavaType());
							col.setValue(dataMap.get(column.getName()));
							switch (column.getJavaType()) {
							case "Integer":
								col.setValue(ParamUtils.getInt(dataMap, column.getName().toLowerCase()));
								break;
							case "Long":
								col.setValue(ParamUtils.getLong(dataMap, column.getName().toLowerCase()));
								break;
							case "Double":
								col.setValue(ParamUtils.getDouble(dataMap, column.getName().toLowerCase()));
								break;
							case "Date":
								col.setValue(ParamUtils.getDate(dataMap, column.getName().toLowerCase()));
								break;
							case "String":
								col.setValue(ParamUtils.getString(dataMap, column.getName().toLowerCase()));
							default:
								break;
							}
							row.addColumn(col);
						} else if (column.getColumnName() != null
								&& dataMap.get(column.getColumnName().toLowerCase()) != null) {
							ColumnModel col = new ColumnModel();
							col.setColumnName(column.getColumnName());
							col.setName(column.getName());
							col.setJavaType(column.getJavaType());
							col.setValue(dataMap.get(column.getColumnName().toLowerCase()));
							switch (column.getJavaType()) {
							case "Integer":
								col.setValue(ParamUtils.getInt(dataMap, column.getColumnName().toLowerCase()));
								break;
							case "Long":
								col.setValue(ParamUtils.getLong(dataMap, column.getColumnName().toLowerCase()));
								break;
							case "Double":
								col.setValue(ParamUtils.getDouble(dataMap, column.getColumnName().toLowerCase()));
								break;
							case "Date":
								col.setValue(ParamUtils.getDate(dataMap, column.getColumnName().toLowerCase()));
								break;
							case "String":
								col.setValue(ParamUtils.getString(dataMap, column.getColumnName().toLowerCase()));
							default:
								break;
							}
							row.addColumn(col);
						}
					}
				}
				rows.add(row);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return rows;
	}

}