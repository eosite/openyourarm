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

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.dom4j.*;

import com.glaf.core.util.Dom4jUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.StringTools;

public class POIExcelDataImport {
	protected final static Log logger = LogFactory
			.getLog(POIExcelDataImport.class);

	public static final int COLOR_MASK = Integer.parseInt("FFFFFF", 16);

	public Color getBackgroundColor(HSSFPalette palette, HSSFCell cell) {
		short index = cell.getCellStyle().getFillForegroundColor();
		short[] rgb = palette.getColor(index).getTriplet();
		return new Color(rgb[0], rgb[1], rgb[2]);
	}

	public String getColor(HSSFPalette palette, short index) {
		if (palette.getColor(index) == null) {
			return null;
		}
		short[] rgb = palette.getColor(index).getTriplet();
		Color color = new Color(rgb[0], rgb[1], rgb[2]);
		return this.getColorHexa(color);
	}

	public String getColorHexa(Color color) {
		String hexa = Integer.toHexString(color.getRGB() & COLOR_MASK)
				.toUpperCase();
		return "#" + ("000000" + hexa).substring(hexa.length());
	}

	public Color getFrontColor(HSSFWorkbook wb, HSSFCell cell) {
		HSSFPalette palette = wb.getCustomPalette();
		HSSFFont font = wb.getFontAt(cell.getCellStyle().getFontIndex());
		short index = font.getColor();
		if (palette.getColor(index) == null) {
			return Color.BLACK;
		}
		short[] rgb = palette.getColor(index).getTriplet();
		return new Color(rgb[0], rgb[1], rgb[2]);
	}

	public Integer getHeightPix(int x) {
		return (int) Math.round(x / 14.125);
	}

	public Integer getWidthPix(int x) {
		return (int) Math.round(x / 28.44);
	}

	public Document read(InputStream inputStream) {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("form-definition");
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
		HSSFSheet sheet = wb.getSheetAt(0);
		String sheetName = wb.getSheetName(0);
		String formName = sheetName;
		if (sheetName.indexOf("{") != -1 && sheetName.indexOf("}") != -1
				&& sheetName.indexOf("{") < sheetName.indexOf("}")) {
			String json = sheetName.substring(sheetName.lastIndexOf("{"),
					sheetName.lastIndexOf("}") + 1);
			json = StringTools.replaceIgnoreCase(json, "=", ":");
			formName = sheetName.substring(0, sheetName.lastIndexOf("{"));
			Map<String, Object> dataMap = JsonUtils.decode(json);
			String title = (String) dataMap.get("T");
			if (StringUtils.isNotEmpty(title)) {
				if (dataMap.get("title") == null) {
					dataMap.put("title", title);
				}
			}
			root.addAttribute("name", formName);
			root.addAttribute("title", (String) dataMap.get("title"));
		} else {
			root.addAttribute("name", sheetName);
			root.addAttribute("title", sheetName);
		}

		Integer y = 0;
		int rows = sheet.getPhysicalNumberOfRows();

		root.addAttribute("rows", String.valueOf(rows));

		for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
			HSSFRow row = sheet.getRow(rowIndex);
			if (row == null) {
				continue;
			}
			Integer x = 0;

			int cells = row.getLastCellNum();

			root.addAttribute("columns", String.valueOf(cells));

			for (int colIndex = 0; colIndex < cells; colIndex++) {

				HSSFCell cell = row.getCell(colIndex);

				if (cell != null) {

					String cellValue = null;

					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_FORMULA:
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						break;
					case HSSFCell.CELL_TYPE_STRING:
						cellValue = cell.getRichStringCellValue().getString();
						break;
					default:
						break;
					}

					if (cellValue == null) {
						cellValue = "";
					}

					cellValue = cellValue.trim();

					Element node = root.addElement("cell");
					if (cell.getCellComment() != null) {
						node.addAttribute("comment", cell.getCellComment()
								.getString().getString());
					}

					if (cellValue.startsWith("##按钮:")
							&& cellValue.endsWith("##")) {
						node.addAttribute("nodeType", "button");
					} else if (cellValue.startsWith("##复选框:")
							&& cellValue.endsWith("##")) {
						node.addAttribute("nodeType", "checkbox");
					} else if (cellValue.startsWith("##单选框:")
							&& cellValue.endsWith("##")) {
						node.addAttribute("nodeType", "radio");
					} else if (cellValue.startsWith("##下拉列表:")
							&& cellValue.endsWith("##")) {
						node.addAttribute("nodeType", "select");
					} else if (cellValue.startsWith("##日期:")
							&& cellValue.endsWith("##")) {
						node.addAttribute("nodeType", "datefield");
					} else if (cellValue.startsWith("##数值框:")
							&& cellValue.endsWith("##")) {
						node.addAttribute("nodeType", "numberfield");
					} else if (cellValue.startsWith("##文本域:")
							&& cellValue.endsWith("##")) {
						node.addAttribute("nodeType", "textarea");
					} else if (cellValue.startsWith("##文本框:")
							&& cellValue.endsWith("##")) {
						node.addAttribute("nodeType", "textfield");
					} else if (cellValue.startsWith("##密码输入框:")
							&& cellValue.endsWith("##")) {
						node.addAttribute("nodeType", "password");
					} else if (cellValue.startsWith("##文本编辑器:")
							&& cellValue.endsWith("##")) {
						node.addAttribute("nodeType", "editor");
					} else if (cellValue.startsWith("##数据网格:")
							&& cellValue.endsWith("##")) {
						node.addAttribute("nodeType", "grid");
					} else if (cellValue.startsWith("##多维数据网格:")
							&& cellValue.endsWith("##")) {
						node.addAttribute("nodeType", "pivotgrid");
					} else if (cellValue.startsWith("##列表视图:")
							&& cellValue.endsWith("##")) {
						node.addAttribute("nodeType", "listview");
					} else if (cellValue.startsWith("##树型控件:")
							&& cellValue.endsWith("##")) {
						node.addAttribute("nodeType", "treeview");
					} else if (cellValue.startsWith("##日程控件:")
							&& cellValue.endsWith("##")) {
						node.addAttribute("nodeType", "scheduler");
					} else if (cellValue.startsWith("##隐藏域:")
							&& cellValue.endsWith("##")) {
						node.addAttribute("nodeType", "hidden");
					} else if (cellValue.startsWith("##标签:")
							&& cellValue.endsWith("##")) {
						node.addAttribute("nodeType", "label");
					} else {
						node.addAttribute("nodeType", "label");
						node.addAttribute("title", cellValue);
					}

					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_FORMULA:
						node.addAttribute("formula", cell.getCellFormula());
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
						} else {
						}
						break;
					case HSSFCell.CELL_TYPE_STRING:
						break;
					default:
						break;
					}

					this.setStyle(wb, cell, node);

					if (cellValue.startsWith("##") && cellValue.endsWith("##")
							&& cellValue.indexOf(":") != -1) {
						String name = cellValue.substring(
								cellValue.indexOf(":") + 1,
								cellValue.length() - 2);
						node.addAttribute("name", name);
						logger.debug("name:" + name);
					}

					int height = getHeightPix(row.getHeight());
					int width = getWidthPix(sheet.getColumnWidth((colIndex)));

					node.addAttribute("x", String.valueOf(x));
					node.addAttribute("y", String.valueOf(y));

					node.addAttribute("rowIndex",
							String.valueOf(row.getRowNum() + 1));
					node.addAttribute("colIndex", String.valueOf(colIndex + 1));

					for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
						CellRangeAddress range = sheet.getMergedRegion(i);
						if (range.getFirstRow() <= row.getRowNum()
								&& row.getRowNum() <= range.getLastRow()
								&& range.getFirstColumn() <= cell
										.getColumnIndex()
								&& cell.getColumnIndex() <= range
										.getLastColumn()) {
							if (StringUtils.isEmpty(node
									.attributeValue("title"))
									&& StringUtils.isEmpty(node
											.attributeValue("name"))) {
								node.addAttribute("rendered",
										String.valueOf(false));
							} else {
								height = 0;
								width = 0;
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

								node.addAttribute("height",
										String.valueOf(height));
								node.addAttribute("width",
										String.valueOf(width));
								node.addAttribute(
										"rowSpan",
										String.valueOf(range.getLastRow()
												- range.getFirstRow() + 1));
								node.addAttribute(
										"colSpan",
										String.valueOf(range.getLastColumn()
												- range.getFirstColumn() + 1));
							}
						}
					}
					node.addAttribute("height", String.valueOf(height));
					node.addAttribute("width", String.valueOf(width));
				}
				x = x + getWidthPix(sheet.getColumnWidth(colIndex));
			}
			y = y + getHeightPix(row.getHeight());
			root.addAttribute("width", String.valueOf(x));
			root.addAttribute("height", String.valueOf(y));
			List<?> elements = root.elements();
			Iterator<?> iterator = elements.iterator();
			while (iterator.hasNext()) {
				Element elem = (Element) iterator.next();
				if ("false".equals(elem.attributeValue("rendered"))) {
					root.remove(elem);
				}
			}
		}

		return doc;
	}

	protected void setStyle(HSSFWorkbook wb, HSSFCell cell, Element node) {
		HSSFCellStyle style = cell.getCellStyle();
		if (style != null) {
			short alignment = style.getAlignment();
			switch (alignment) {
			case HSSFCellStyle.ALIGN_CENTER:

				break;
			case HSSFCellStyle.ALIGN_JUSTIFY:

				break;
			case HSSFCellStyle.ALIGN_LEFT:

				break;
			case HSSFCellStyle.ALIGN_RIGHT:

				break;
			default:
				break;
			}

			short verticalAlignment = style.getVerticalAlignment();
			switch (verticalAlignment) {
			case HSSFCellStyle.VERTICAL_BOTTOM:

				break;
			case HSSFCellStyle.VERTICAL_CENTER:

				break;
			case HSSFCellStyle.VERTICAL_TOP:

				break;
			default:
				break;
			}

			Color x2 = this.getFrontColor(wb, cell);
			node.addAttribute("foreground", this.getColorHexa(x2));

			HSSFPalette palette = wb.getCustomPalette();
			Color x4 = this.getBackgroundColor(palette, cell);
			node.addAttribute("background", this.getColorHexa(x4));
			HSSFFont font = wb.getFontAt(style.getFontIndex());
			if (font != null) {

			}
		}
	}

	public static void main(String[] args) throws Exception {
		POIExcelDataImport imp = new POIExcelDataImport();
		Document doc = imp.read(new java.io.FileInputStream(
				"./data/InvoiceApply.xls"));
		FileUtils.save("./test.fdl.xml",
				Dom4jUtils.getBytesFromPrettyDocument(doc, "UTF-8"));
	}

}