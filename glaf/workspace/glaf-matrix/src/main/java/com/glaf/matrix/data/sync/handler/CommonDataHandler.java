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

package com.glaf.matrix.data.sync.handler;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.security.Authentication;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.UUID32;
import com.glaf.matrix.data.sync.TableSyncContext;
import com.glaf.matrix.data.sync.handler.DataHandler;
import com.glaf.matrix.data.sync.model.ColumnMapping;
import com.glaf.matrix.data.sync.model.TargetTable;

public class CommonDataHandler implements DataHandler {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	public void process(Connection conn, TableSyncContext context) {
		TargetTable targetTable = context.getTargetTable();
		String split = targetTable.getSplit();
		String splitSrcColumn = targetTable.getSplitSrcColumn();
		String splitToColumns = targetTable.getSplitToColumns();
		ColumnDefinition idColumn = context.getIdColumn();
		List<ColumnDefinition> columns = context.getColumns();
		String idPrefix = DateUtils.getNowYearMonthDay() + "/system";
		if (Authentication.getAuthenticatedActorId() != null) {
			idPrefix = DateUtils.getNowYearMonthDay() + "/" + Authentication.getAuthenticatedActorId();
		}
		List<String> names = new ArrayList<String>();
		for (ColumnDefinition col : columns) {
			names.add(col.getColumnName());
			names.add(col.getColumnName().toLowerCase());
			names.add(col.getColumnName().toUpperCase());
		}

		if (splitSrcColumn.indexOf(".") != -1) {
			splitSrcColumn = splitSrcColumn.substring(splitSrcColumn.lastIndexOf(".") + 1, splitSrcColumn.length());
		}

		if (StringUtils.isEmpty(split)) {
			split = ",";
		}
		long ts = System.currentTimeMillis();
		String srcTableColumn = null;
		String targetTableColumn = null;
		Map<String, String> initDataMap = (Map<String, String>) context.getInitData();
		Map<String, Object> masterData = context.getMasterData();
		List<Map<String, Object>> processedDataList = new ArrayList<Map<String, Object>>();
		String value = ParamUtils.getString(masterData, targetTable.getSplitSrcColumn());

		if (value == null) {
			value = ParamUtils.getString(masterData, splitSrcColumn);
		}
		// logger.debug(splitSrcColumn + "->" + value);
		// logger.debug("masterData:" + masterData);
		if (StringUtils.isNotEmpty(value)) {
			if (value.startsWith(split)) {
				value = value.substring(value.indexOf(split) + split.length(), value.length());
			}
			List<String> ls = split(value, split);
			List<ColumnMapping> cols = targetTable.getColumns();
			// logger.debug("col mappings:" + cols);
			for (String str : ls) {
				if (StringUtils.isEmpty(str)) {
					continue;
				}

				Map<String, Object> rowMap = new HashMap<String, Object>();

				Set<Entry<String, Object>> entrySet = masterData.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					String key = entry.getKey();
					Object val = entry.getValue();
					if (names.contains(key.toLowerCase())) {
						rowMap.put(key, val);
					}
				}

				for (ColumnMapping col : cols) {
					srcTableColumn = col.getSrcTableColumn();
					targetTableColumn = col.getTargetTableColumn();
					// logger.debug(srcTableColumn + ":" + targetTableColumn);

					if (srcTableColumn != null && srcTableColumn.indexOf(".") != -1) {
						srcTableColumn = srcTableColumn.substring(srcTableColumn.lastIndexOf(".") + 1,
								srcTableColumn.length());
					}
					if (targetTableColumn.indexOf(".") != -1) {
						targetTableColumn = targetTableColumn.substring(targetTableColumn.lastIndexOf(".") + 1,
								targetTableColumn.length());
					}
					if (names.contains(targetTableColumn.toLowerCase())) {
						if (ParamUtils.getObject(masterData, col.getSrcTableColumn()) != null) {
							rowMap.put(targetTableColumn, ParamUtils.getObject(masterData, col.getSrcTableColumn()));
							rowMap.put(targetTableColumn.toLowerCase(),
									ParamUtils.getObject(masterData, col.getSrcTableColumn()));
						} else {
							rowMap.put(targetTableColumn, ParamUtils.getObject(masterData, srcTableColumn));
							rowMap.put(targetTableColumn.toLowerCase(),
									ParamUtils.getObject(masterData, srcTableColumn));
						}
					}
				}

				if (initDataMap != null && !initDataMap.isEmpty()) {
					String name = initDataMap.get(str);
					List<String> splitColumns = split(splitToColumns, ",");
					for (String splitToColumn : splitColumns) {
						rowMap.put(splitToColumn, name);
					}
				} else {
					// rowMap.put(splitToColumn, str);
					List<String> splitColumns = split(splitToColumns, ",");
					for (String splitToColumn : splitColumns) {
						rowMap.put(splitToColumn, str);
					}
				}

				// if (ParamUtils.getObject(rowMap, idColumn.getColumnName()) == null) {
				switch (idColumn.getJavaType()) {
				case "Long":
					rowMap.put(idColumn.getColumnName(), ++ts);
					rowMap.put(idColumn.getColumnName().toLowerCase(), rowMap.get(idColumn.getColumnName()));
					break;
				default:
					if (StringUtils.equals(targetTable.getPrimaryKeyExpression(), "#{uuid}")) {
						rowMap.put(idColumn.getColumnName(), UUID32.getUUID());
						rowMap.put(idColumn.getColumnName().toLowerCase(), rowMap.get(idColumn.getColumnName()));
					} else {
						rowMap.put(idColumn.getColumnName(),
								idPrefix + "-" + StringTools.getDigit8Id(context.getCurrentId()));
						rowMap.put(idColumn.getColumnName().toLowerCase(), rowMap.get(idColumn.getColumnName()));
					}
					break;
				}
				// }
				logger.debug("->id=" + rowMap.get(idColumn.getColumnName()));
				logger.debug("child data:" + rowMap);
				processedDataList.add(rowMap);
			}
		}
		context.setProcessedDataList(processedDataList);
	}

	@SuppressWarnings("unchecked")
	public static List<String> split(String text, String delimiter) {
		if (delimiter == null) {
			throw new RuntimeException("delimiter is null");
		}
		if (text == null) {
			return Collections.EMPTY_LIST;
		}
		List<String> pieces = new java.util.ArrayList<String>();
		int start = 0;
		int end = text.indexOf(delimiter);
		while (end != -1) {
			pieces.add((text.substring(start, end)).trim());
			start = end + delimiter.length();
			end = text.indexOf(delimiter, start);
		}
		if (start < text.length()) {
			String temp = text.substring(start);
			if (temp != null && temp.trim().length() > 0) {
				pieces.add(temp.trim());
			}
		}
		return pieces;
	}

	public static void main(String[] args) {
		String value = ", B582 , D495 , ";
		String split = ",";
		System.out.println(split(value, split));
		List<String> ls = split(value, split);
		System.out.println("ls->" + ls);
		for (String str : ls) {
			if (StringUtils.isEmpty(str)) {
				continue;
			}
			System.out.println(str);
		}

		if (value.startsWith(split)) {
			value = value.substring(value.indexOf(split) + split.length(), value.length());
		}
		System.out.println(value);
		System.out.println(split(value, split));

		ls = split(value, split);
		System.out.println("ls->" + ls);
		for (String str : ls) {
			if (StringUtils.isEmpty(str)) {
				continue;
			}
			System.out.println(str);
		}
	}

}
