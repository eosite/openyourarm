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
package com.glaf.form.core.dataimport;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormLayout;

public class ExcelLayoutImport {
	protected final static Log logger = LogFactory
			.getLog(ExcelLayoutImport.class);

	public Integer getHeightPix(int x) {
		return (int) Math.round(x / 14.125);
	}

	public Integer getWidthPix(int x) {
		return (int) Math.round(x / 28.44);
	}

	public List<FormLayout> read(InputStream inputStream) {
		List<FormLayout> layouts = new ArrayList<FormLayout>();
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(inputStream);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e) {
				}
			}
		}
		int sheetCount = wb.getNumberOfSheets();
		for (int k = 0; k < sheetCount; k++) {
			HSSFSheet sheet = wb.getSheetAt(k);
			String sheetName = wb.getSheetName(k);
			if (StringUtils.isEmpty(sheetName)) {
				break;
			}
			FormLayout layout = new FormLayout();
			layout.setName(sheetName);
			JSONArray array = new JSONArray();

			Integer y = 0;
			int rows = sheet.getPhysicalNumberOfRows();
			// layout.setRows(rows);

			for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
				HSSFRow row = sheet.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				Integer x = 0;

				int cells = row.getLastCellNum();

				// layout.setColumns(cells);

				for (int colIndex = 0; colIndex < cells; colIndex++) {
					HSSFCell cell = row.getCell(colIndex);
					if (cell != null
							&& StringUtils
									.isNotEmpty(cell.getStringCellValue())) {
						JSONObject json = new JSONObject();
						json.put("id", cell.getStringCellValue());
						json.put("height",
								Integer.valueOf(getHeightPix(row.getHeight())));
						json.put("width", Integer.valueOf(getWidthPix(sheet
								.getColumnWidth((colIndex)))));
						json.put("rowIndex", row.getRowNum());
						json.put("colIndex", cell.getColumnIndex());
						json.put("rowSpan", 1);
						json.put("colSpan", 1);

						for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
							CellRangeAddress range = sheet.getMergedRegion(i);

							if (range.getFirstRow() <= row.getRowNum()
									&& row.getRowNum() <= range.getLastRow()
									&& range.getFirstColumn() <= cell
											.getColumnIndex()
									&& cell.getColumnIndex() <= range
											.getLastColumn()) {

								Integer height = 0;
								Integer width = 0;

								for (int kk = range.getFirstColumn(); kk <= range
										.getLastColumn(); kk++) {
									width = width
											+ getWidthPix(sheet
													.getColumnWidth(kk));
								}

								for (int kk = range.getFirstRow(); kk <= range
										.getLastRow(); kk++) {
									HSSFRow rowx = sheet.getRow(kk);
									height = height
											+ getHeightPix(rowx.getHeight());
								}

								json.put("height", height);
								json.put("width", width);
								json.put(
										"rowSpan",
										range.getLastRow()
												- range.getFirstRow() + 1);
								json.put("colSpan", range.getLastColumn()
										- range.getFirstColumn() + 1);
							}
						}
						array.add(json);
					}
					x = x + getWidthPix(sheet.getColumnWidth(colIndex));
				}

				y = y + getHeightPix(row.getHeight());
				layout.setWidth(Integer.valueOf(x));
				layout.setHeight(Integer.valueOf(y));
			}

			for (int j = 0; j < array.size(); j++) {
				JSONObject json = array.getJSONObject(j);
				int width = json.getIntValue("width");
				json.put("_width_", width);
				json.put(
						"width",
						Math.round(((width * 1.0D / layout.getWidth() * 1.0D) * 100.0D)));
				int height = json.getIntValue("height");
				json.put("_height_", height);
				json.put("height", Math.round(((height * 1.0D
						/ layout.getHeight() * 1.0D) * 100.0D)));
			}
			layout.setJson(array.toJSONString());
			layouts.add(layout);
		}
		return layouts;
	}

	public static void main(String[] args) throws Exception {
		ExcelLayoutImport imp = new ExcelLayoutImport();
		List<FormLayout> layouts = imp.read(new java.io.FileInputStream(
				"./data/Layout.xls"));
		for (int i = 0; i < layouts.size(); i++) {
			//System.out.println(layouts.get(i).getJson());
		}
	}

}