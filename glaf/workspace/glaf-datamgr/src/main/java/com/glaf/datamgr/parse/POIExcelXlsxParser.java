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

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.StringTools;

public class POIExcelXlsxParser implements Parser {

	protected final static Log logger = LogFactory.getLog(POIExcelXlsxParser.class);

	public static void main(String[] args) throws Exception {
		String mappingFile = "./report/mapping/BaseData.mapping.xml";
		String dataFile = "./report/data/BaseData.xlsx";
		ContextFactory.hasBean("dataSource");
		ParserFacede parser = new ParserFacede();
		List<TableModel> rows = parser.parse(Environment.DEFAULT_SYSTEM_NAME, mappingFile, dataFile, null, true);
		if (rows != null && !rows.isEmpty()) {
			JSONArray array = new JSONArray();
			for (TableModel model : rows) {
				JSONObject jsonObject = new JSONObject();
				List<ColumnModel> columns = model.getColumns();
				if (columns != null && !columns.isEmpty()) {
					for (ColumnModel col : columns) {
						if (col.getName() != null) {
							jsonObject.put(col.getName(), col.getValue());
						}
						if (col.getColumnName() != null) {
							jsonObject.put(col.getColumnName().toLowerCase(), col.getValue());
						}
					}
				}
				if (model.getIdColumn() != null) {
					ColumnModel col = model.getIdColumn();
					if (col.getName() != null) {
						jsonObject.put(col.getName(), col.getValue());
					}
					if (col.getColumnName() != null) {
						jsonObject.put(col.getColumnName().toLowerCase(), col.getValue());
					}
				}
				array.put(jsonObject);
			}
			System.out.println(array.toString('\n'));
		}
	}

	public List<TableModel> parse(TableModel metadata, java.io.InputStream data) {
		List<TableModel> rows = new java.util.ArrayList<TableModel>();
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(data);

			int sheetCount = wb.getNumberOfSheets();
			SimpleDateFormat formatter = null;
			for (int r = 0; r < sheetCount; r++) {
				XSSFSheet sheet = wb.getSheetAt(r);
				int rowCount = sheet.getPhysicalNumberOfRows();
				if (metadata.getStopSkipRow() > 0) {
					rowCount = rowCount - metadata.getStopSkipRow();// 除去末尾行
				}
				
				int startRow = metadata.getStartRow();// 从1开始
				for (int i = 0; i < rowCount; i++) {
					
					if (startRow > 0 && i < startRow - 1) {
						continue;// 跳过开始行
					}

					XSSFRow row = sheet.getRow(i);
					TableModel model = new TableModel();

					model.setIdColumn(metadata.getIdColumn());
					model.setTableName(metadata.getTableName());
					model.setAggregationKeys(metadata.getAggregationKeys());
					logger.debug("aggregationKeys:" + metadata.getAggregationKey());
					int colCount = row.getPhysicalNumberOfCells();
					// logger.info("column count="+colCount);
					int colIndex = 0;
					for (ColumnModel cell : metadata.getColumns()) {
						if (cell.getPosition() >= 0 && cell.getPosition() < colCount) {
							colIndex++;
							XSSFCell hssfCell = row.getCell(cell.getPosition());
							if (hssfCell == null) {
								continue;
							}
							ColumnModel col = new ColumnModel();
							col.setName(cell.getName());
							col.setColumnName(cell.getColumnName());
							col.setValueExpression(cell.getValueExpression());
							String javaType = cell.getType();

							String value = null;

							switch (hssfCell.getCellType()) {
							case XSSFCell.CELL_TYPE_FORMULA:
								break;
							case XSSFCell.CELL_TYPE_BOOLEAN:
								value = String.valueOf(hssfCell.getBooleanCellValue());
								break;
							case XSSFCell.CELL_TYPE_NUMERIC:
								if ("Date".equals(javaType)) {
									Date date = hssfCell.getDateCellValue();
									if (date != null) {
										col.setDateValue(date);
										col.setValue(date);
									}
								} else {
									value = String.valueOf(hssfCell.getNumericCellValue());
								}
								break;
							case XSSFCell.CELL_TYPE_STRING:
								if (hssfCell.getRichStringCellValue() != null) {
									value = hssfCell.getRichStringCellValue().getString();
								} else {
									value = hssfCell.getStringCellValue();
								}
								break;
							default:
								if (StringUtils.isNotEmpty(hssfCell.getStringCellValue())) {
									value = hssfCell.getStringCellValue();
								}
								break;
							}

							if (StringUtils.isNotEmpty(value)) {
								value = value.trim();
								if (value.endsWith(".0")) {
									value = value.substring(0, value.length() - 2);
								}
								col.setStringValue(value);
								col.setValue(value);
								logger.debug(col.getName() + "->" + col.getValue());
								if ("Boolean".equals(javaType)) {
									col.setBooleanValue(Boolean.valueOf(value));
									col.setValue(Boolean.valueOf(value));
								} else if ("Integer".equals(javaType)) {
									if (value.indexOf(".") != -1) {
										value = value.substring(0, value.indexOf("."));
									}
									col.setIntValue(Integer.parseInt(value));
									col.setValue(Integer.parseInt(value));
								} else if ("Long".equals(javaType)) {
									if (value.indexOf(".") != -1) {
										value = value.substring(0, value.indexOf("."));
									}
									col.setLongValue(Long.parseLong(value));
									col.setValue(Long.parseLong(value));
								} else if ("Double".equals(javaType)) {
									if (StringUtils.isNumeric(value)) {
										col.setDoubleValue(Double.parseDouble(value));
										col.setValue(Double.parseDouble(value));
									}
								} else if ("Date".equals(javaType)) {
									if (cell.getFormat() != null) {
										try {
											formatter = new SimpleDateFormat(cell.getFormat());
											Date date = formatter.parse(value);
											col.setDateValue(date);
											col.setValue(date);
										} catch (ParseException ex) {
											logger.debug(
													"error date format: row(" + startRow + ") col(" + colIndex + ")");
										}
									} else {
										try {
											value = StringTools.replace(value, "/", "-");
											Date date = DateUtils.toDate(value);
											col.setDateValue(date);
											col.setValue(date);
										} catch (Exception ex) {
											logger.debug(
													"error date format: row(" + startRow + ") col(" + colIndex + ")");
										}
									}
								}
							}
							if (metadata.getIdColumn() != null) {
								if (StringUtils.equals(col.getColumnName(), metadata.getIdColumn().getColumnName())) {
									ColumnModel idColumn = new ColumnModel();
									idColumn.setColumnName(metadata.getIdColumn().getColumnName());
									idColumn.setName(metadata.getIdColumn().getName());
									idColumn.setType(metadata.getIdColumn().getType());
									idColumn.setValue(col.getValue());
									model.setIdColumn(idColumn);
								}
							}
							model.addColumn(col);
						}
					}
					if (metadata.getIdColumn() != null && metadata.getIdColumn().isRequired()) {
						if (model.getIdColumn().getValue() != null) {
							rows.add(model);
						}
					} else {
						/**
						 * 如果定义了聚合列，需要判断聚合列的值都必须存在才认为是合法数据
						 */
						if (metadata.getAggregationKeys() != null && !metadata.getAggregationKeys().isEmpty()) {
							boolean skip = false;
							for (ColumnModel col : model.getColumns()) {
								if (metadata.getAggregationKeys().contains(col.getName())
										|| metadata.getAggregationKeys().contains(col.getName().toLowerCase())) {
									if (col.getValue() == null && col.getValueExpression() == null) {
										skip = true;
									}
								}
							}
							if (!skip) {
								rows.add(model);
							}
						} else {
							rows.add(model);
						}
					}
				}
			}
			logger.debug("total rows size:" + rows.size());
			return rows;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (wb != null) {
				try {
					wb.close();
					wb = null;
				} catch (IOException e) {
				}
			}
		}
	}
}
