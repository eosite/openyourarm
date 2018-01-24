package com.glaf.conver.spread2excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.expr.poi.POIFunctionsRegister;

public class Spread2Excel {
	JSONObject spreadJson;
	OutputStream os;
	Workbook workbook;
	private List<Integer> existRows = new ArrayList<>();

	public Spread2Excel(JSONObject spreadJson, OutputStream os) {
		this.spreadJson = spreadJson;
		this.os = os;
	}

	public void convert() throws IOException {

		if (spreadJson != null && !spreadJson.isEmpty()) {
			String pattern = "/OADate\\((\\d*\\.?\\d*)\\)/";
			String numberPattern = "^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$";

			workbook = new SXSSFWorkbook(300);

			// 注册方法
			POIFunctionsRegister.register(workbook);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

			Sheet excelSheet;
			JSONObject sheets = spreadJson.getJSONObject("sheets");
			Set<String> keys = sheets.keySet();
			for (String sheetName : keys) {
				JSONObject sheet = sheets.getJSONObject(sheetName);
				JSONObject data = sheet.getJSONObject("data");
				if (data != null) {
					JSONObject dataTable = data.getJSONObject("dataTable");
					if (dataTable != null) {
						excelSheet = workbook.createSheet(sheetName);
						JSONArray rowsAry = sheet.getJSONArray("rows");
						// 填充宽度/高度
						fixColumnsWidth(excelSheet, sheet);
						// 合并单元格
						JSONArray spans = sheet.getJSONArray("spans");
						mergingCells(excelSheet, spans);

						Set<String> rowKeys = dataTable.keySet();
						for (String rowKey : rowKeys) {
							JSONObject columnsObj = dataTable.getJSONObject(rowKey);
							Set<String> colKeys = columnsObj.keySet();
							Row row = excelSheet.createRow(Integer.parseInt(rowKey));

							fixRowsHeight(row, rowsAry);

							for (String colKey : colKeys) {
								JSONObject columnObj = columnsObj.getJSONObject(colKey);
								String expression = columnObj.getString("value");
								String formula = columnObj.getString("formula");
								JSONObject cellType = null ;
								if(columnObj.containsKey("style")){
									JSONObject styleObj = columnObj.getJSONObject("style");
									if(styleObj.containsKey("cellType")){
										cellType = styleObj.getJSONObject("cellType");
									}
								}

								Cell cell = row.createCell(Integer.parseInt(colKey));

								fixCell(cell, columnObj);

								// 公式 和 值
								if (StringUtils.isNotEmpty(formula)) {
									cell.setCellFormula(formula);
								} else if (StringUtils.isNotEmpty(expression)) {
									if(cellType!=null){ //自定义样式单元格
										try {
											switch (cellType.getString("typeName")) {
											case "104":
												Date d = new Date();
												d.setTime(Long.parseLong(expression));
												cell.setCellValue(d);
												continue;
											default:
											}
										} catch (Exception e) {
										}
									}
									// expression 值为
									// /OADate(42655.333333333336)/ 表示日期 需要算法转换
									if (!StringUtils.isEmpty(expression) && Pattern.matches(pattern, expression)) {
										// expression.
										Pattern r = Pattern.compile(pattern);
										// 现在创建 matcher 对象
										Matcher m = r.matcher(expression);
										if (m.find()) {
											String dateStrValue = m.group(1);
											double dateDouble = Double.parseDouble(dateStrValue);
											// 这里有一个坑 如果日期小于1
											// 最后解析出的值会是-1，现强制使用数值类型来计算
											cell.setCellValue(dateDouble);
										}
									} else {
										if (expression.matches(numberPattern)) {
											cell.setCellValue(Double.parseDouble(expression));
										} else {
											cell.setCellValue(expression);
										}
									}
								}
							}
						}

						// 对没有数据的row 但设置了行隐藏或者行高的调整
						fixOtherRowsHeight(excelSheet, rowsAry);
					}
				}
			}
			evaluator.evaluateAll();
			workbook.write(this.os);
		}
	}

	/**
	 * 填充行高和列宽
	 * 
	 * @param sheet
	 * @param sheetObj
	 */
	protected void fixColumnsWidth(Sheet sheet, JSONObject sheetObj) {
		if (sheetObj.containsKey("columns")) {
			JSONArray columsAry = sheetObj.getJSONArray("columns");
			JSONObject columsObj = null;
			for (int i = 0; i < columsAry.size(); i++) {
				columsObj = columsAry.getJSONObject(i);
				if (columsObj != null) {
					if (columsObj.containsKey("size")) {
						sheet.setColumnWidth(i, (int) (37.5 * columsObj.getIntValue("size")));
					}
					if (columsObj.containsKey("visible")) {
						boolean visible = columsObj.getBooleanValue("visible");
						sheet.setColumnHidden(i, !visible);
					}
				}
			}
			/*for (Object object : columsAry) {
				if (object != null) {
					columsObj = (JSONObject) object;
					if (columsObj.containsKey("size")) {
						sheet.setColumnWidth(columsAry.indexOf(object), (int) (37.5 * columsObj.getIntValue("size")));
					}
					if (columsObj.containsKey("visible")) {
						boolean visible = columsObj.getBooleanValue("visible");
						sheet.setColumnHidden(columsAry.indexOf(object), !visible);
					}
				}
			}*/
		}

	}

	protected void _fixRowHeight(Row row, JSONObject rowObj) {
		if (rowObj.containsKey("size")) {
			row.setHeight((short) (13.5 * rowObj.getIntValue("size")));
		}
		if (rowObj.containsKey("visible")) {
			boolean visible = rowObj.getBooleanValue("visible");
			row.setZeroHeight(!visible);
		}
	}

	protected void fixRowsHeight(Row row, JSONArray rowsAry) {
		if (rowsAry != null) {
			int rowNum = row.getRowNum(), rowsLen = rowsAry.size();
			if (rowNum < rowsLen) {
				JSONObject rowObj = rowsAry.getJSONObject(rowNum);
				existRows.add(rowNum);
				if (rowObj != null) {
					_fixRowHeight(row, rowObj);
				}
			}
		}
	}

	protected void fixOtherRowsHeight(Sheet sheet, JSONArray rowsAry) {
		if (rowsAry != null) {
			JSONObject rowObj = null;
			int index = 0;
			for (Object object : rowsAry) {
				if (object != null && !existRows.contains(index)) {
					rowObj = (JSONObject) object;
					Row row = sheet.createRow(index);
					_fixRowHeight(row, rowObj);
				}
				index++;
			}
		}
	}

	/**
	 * 填充cell样式
	 * 
	 * @param cell
	 * @param cellJson
	 */
	protected void fixCell(Cell cell, JSONObject cellJson) {
		CellStyle cellStyle = workbook.createCellStyle();
		JSONObject styleObj = cellJson.getJSONObject("style");
		// 添加边框
		addBorder(cellStyle, styleObj);

		// 添加数据格式
		addDataFormat(cellStyle, styleObj);

		// 对齐样式
		hAlignAndvAlign(cellStyle, styleObj);

		// 填充字体
		addFont(cellStyle, styleObj);
		
		//背景色
		addBackGround(cellStyle, styleObj);

		//自动换行
		if(styleObj.containsKey("wordWrap")){
			cellStyle.setWrapText(styleObj.getBooleanValue("wordWrap"));
		}
		
		cell.setCellStyle(cellStyle);
		
		
	}
	
	/**
	 * 背景色
	 * @param cellStyle
	 * @param styleObj
	 */
	protected void addBackGround(CellStyle cellStyle, JSONObject styleObj){
		try {
			if (styleObj.containsKey("backColor")) {
				String[] colors = styleObj.getString("backColor").replace("rgb(", "").replace(")", "").replace(" ", "").split(",");
				XSSFCellStyle xs = (XSSFCellStyle) cellStyle;
				XSSFColor color = new XSSFColor(new java.awt.Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2])));
				xs.setFillPattern(CellStyle.SOLID_FOREGROUND);
				xs.setFillForegroundColor(color);
				xs.setFillBackgroundColor(color);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 填充字体
	 * @param cellStyle
	 * @param styleObj
	 */
	protected void addFont(CellStyle cellStyle, JSONObject styleObj) {
		Font font = workbook.createFont();
		try {
			if (styleObj.containsKey("font")) {
				String fontStr = styleObj.getString("font");
				String[] fontStyles = fontStr.split(" ");
				if (fontStr.contains("bold")) {
					font.setBold(true);
				}
				if (fontStr.contains("italic")) {
					font.setItalic(true);
				}
				
				if(fontStyles.length==2){
					font.setFontName(fontStyles[fontStyles.length-1]);
					
					Double d = Double.parseDouble(fontStyles[fontStyles.length-2].replace("px", ""));
					font.setFontHeightInPoints((short) (d.shortValue()-6.0));
				}
				
				if(fontStyles.length>2){
					font.setFontName(fontStyles[fontStyles.length-1]);
					
					Double d = Double.parseDouble(fontStyles[fontStyles.length-2].replace("px", ""));
					font.setFontHeightInPoints((short) (d.shortValue()-6.0));
				}
			}
		
			if (styleObj.containsKey("foreColor")) {
				String[] colors = styleObj.getString("foreColor").replace("rgb(", "").replace(")", "").replace(" ", "").split(",");
				XSSFFont hf = (XSSFFont) font;
				hf.setColor(new XSSFColor(new java.awt.Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2]))));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		// 下划线
		if (styleObj.containsKey("textDecoration")) {
			font.setUnderline(Font.U_SINGLE);
		}
		// font.setStrikeout(true);
		cellStyle.setFont(font);
	}

	/**
	 * 添加数据格式
	 * 
	 * @param cellStyle
	 * @param styleObj
	 */
	protected void addDataFormat(CellStyle cellStyle, JSONObject styleObj) {
		if (styleObj != null && styleObj.containsKey("formatter")) {
			DataFormat df = workbook.createDataFormat();
			cellStyle.setDataFormat(df.getFormat(styleObj.getString("formatter")));
		}
	}

	/**
	 * 添加边框
	 * 
	 * @param cellStyle
	 * @param styleObj
	 */
	protected void addBorder(CellStyle cellStyle, JSONObject styleObj) {
		if (styleObj != null) {
			if (styleObj.containsKey("borderLeft")) {
				cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
				// cellStyle.setLeftBorderColor(CellStyle.);
			}
			if (styleObj.containsKey("borderRight")) {
				cellStyle.setBorderRight(CellStyle.BORDER_THIN);
			}
			if (styleObj.containsKey("borderTop")) {
				cellStyle.setBorderTop(CellStyle.BORDER_THIN);
			}
			if (styleObj.containsKey("borderBottom")) {
				cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			}

		}
	}

	/**
	 * 合并单元格
	 * 
	 * @param sheet
	 */
	protected void mergingCells(Sheet sheet, JSONArray spans) {
		if (spans != null) {
			JSONObject span = null;
			for (Object object : spans) {
				span = (JSONObject) object;
				int row = span.getIntValue("row");
				int rowCount = span.getIntValue("rowCount");
				int col = span.getIntValue("col");
				int colCount = span.getIntValue("colCount");
				sheet.addMergedRegion(new CellRangeAddress(row, row + rowCount - 1, col, col + colCount - 1));
			}
		}
	}

	/**
	 * 对齐样式
	 */
	protected void hAlignAndvAlign(CellStyle cellStyle, JSONObject styleObj) {
		if (styleObj.containsKey("hAlign")) {
			// 0 左 1右 2中
			int hAlign = styleObj.getIntValue("hAlign");
			cellStyle.setAlignment(hAlign == 0 ? CellStyle.ALIGN_LEFT : (hAlign == 2 ? CellStyle.ALIGN_RIGHT : CellStyle.ALIGN_CENTER));
		}/* else {
			cellStyle.setAlignment(CellStyle.ALIGN_GENERAL);
		}*/
		
		if (styleObj.containsKey("vAlign")) {
			// 0 上 1下 2中
			int vAlign = styleObj.getIntValue("vAlign");
			cellStyle.setVerticalAlignment(vAlign == 0 ? CellStyle.VERTICAL_TOP : (vAlign == 2 ? CellStyle.VERTICAL_BOTTOM : CellStyle.VERTICAL_CENTER));
		} /*else {
			cellStyle.setVerticalAlignment(CellStyle.VERTICAL_JUSTIFY);
		}*/
	}

	public static void main(String[] args) {
		//int s = 177;
		//System.out.println(((s + 5) / 7 * 256) / 256);
		Date d = new Date();
		d.setTime(1469980800000l);
		System.out.println(d);
	}
}
