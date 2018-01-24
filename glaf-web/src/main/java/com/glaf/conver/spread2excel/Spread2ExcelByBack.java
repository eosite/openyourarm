package com.glaf.conver.spread2excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.metamodel.query.Query;
import org.apache.poi.hssf.record.cf.CellRangeUtil;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.ss.formula.Formula;
import org.apache.poi.ss.formula.atp.DateParser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressBase;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.DateUtils;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.dep.report.domain.DepReportTemplate;
import com.glaf.expr.poi.POIFunctionsRegister;
import com.glaf.form.core.domain.FormAttachment;
import com.glaf.form.core.query.FormAttachmentQuery;
import com.glaf.form.core.service.IFormAttachmentService;
import com.glaf.form.core.service.impl.FormAttachmentServiceImpl;
import com.glaf.form.core.util.MutilDatabaseBean;
import com.itextpdf.text.log.SysoCounter;

public class Spread2ExcelByBack {
	JSONObject spreadJson;
	JSONObject spreadRule;
	OutputStream os;
	XSSFWorkbook workbook;
	JSONObject params;
	MutilDatabaseBean mutilDatabaseBean;
	HttpServletRequest request;
	IFormAttachmentService formAttachmentService;
	JSONObject rowOffset = new JSONObject();
	
	String pattern = "/OADate\\((\\d*\\.?\\d*)\\)/";
	//"^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$";
	String numberPattern = "^[-+]?(([0-9]+)([.]([0-9]+))?)$";

	private List<Integer> existRows = new ArrayList<>();

	public Spread2ExcelByBack(DepReportTemplate depReportTemplate, OutputStream os, JSONObject params, MutilDatabaseBean mutilDatabaseBean, HttpServletRequest request) {
		this.spreadJson = JSONObject.parseObject(depReportTemplate.getTmpJson());
		this.spreadRule = JSONObject.parseObject(depReportTemplate.getRuleJson());
		this.os = os;
		this.params = params;
		this.mutilDatabaseBean = mutilDatabaseBean;
		this.request = request;
	}
	public Spread2ExcelByBack(DepReportTemplate depReportTemplate, OutputStream os, JSONObject params, MutilDatabaseBean mutilDatabaseBean, HttpServletRequest request,IFormAttachmentService formAttachmentService) {
		this.spreadJson = JSONObject.parseObject(depReportTemplate.getTmpJson());
		this.spreadRule = JSONObject.parseObject(depReportTemplate.getRuleJson());
		this.os = os;
		this.params = params;
		this.mutilDatabaseBean = mutilDatabaseBean;
		this.formAttachmentService = formAttachmentService;
		this.request = request;
	}

	public void convert() throws IOException {

		if (spreadJson != null && !spreadJson.isEmpty()) {
			workbook = new XSSFWorkbook();

			// 注册方法
			POIFunctionsRegister.register(workbook);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

			XSSFSheet excelSheet = null;
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

								Cell cell = row.createCell(Integer.parseInt(colKey));

								fixCell(cell, columnObj);

								// 公式 和 值
								if (StringUtils.isNotEmpty(formula)) {
									cell.setCellFormula(formula);
								} else if (StringUtils.isNotEmpty(expression)) {
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

			// List<CellRangeAddress> cellRangeAddresss =
			// excelSheet.getMergedRegions();

			/*
			 * 填充参数
			 */
			Set<String> pkeys = params.keySet();
			for (String key : pkeys) {
				if (key.indexOf("-") != -1) {
					String[] k = key.split("-");
					int rownum = Integer.parseInt(k[0]);
					int colnum = Integer.parseInt(k[1]);
					Row row = excelSheet.getRow(rownum);
					if (row == null) {
						row = excelSheet.createRow(rownum);
					}
					Cell cell = row.getCell(colnum);
					if (cell == null) {
						cell = row.createCell(colnum);
					}
					String value = params.getString(key);
					try {
						if (value.matches(numberPattern)) {
							cell.setCellValue(Double.parseDouble(value));
						} else {
							Calendar date;
							try {
								date = DateParser.parseDate(value);
							} catch (Exception e) {
								date = Calendar.getInstance();
								date.setTime(DateUtils.toDate(value));
							}
							cell.setCellValue(date);
						}
					} catch (Exception e) {
						cell.setCellValue(value);
					}

				}
			}

			if (spreadRule != null && !spreadRule.isEmpty() && excelSheet != null) {
				//存放图片位置
				JSONArray imagesAry = new JSONArray();
				List<String> fileIds = new ArrayList<>();
				
				JSONObject pageObj = spreadRule.getJSONObject("page");
				/*
				 * 数据集填充
				 */
				String dataset = "data_save_area";
				String datasetStr = pageObj.getString(dataset);
				if (StringUtils.isNotEmpty(datasetStr)) {
					JSONArray datasetAry = JSON.parseArray(datasetStr);
					JSONObject datasetObj;
					for (Object object : datasetAry) {
						datasetObj = (JSONObject) object;
						JSONObject col2Obj = datasetObj.getJSONObject("col-2");
						String tableMsg = col2Obj.getJSONObject("data").getString("tableMsg");

						JSONArray tableMsgAry = JSON.parseArray(tableMsg);

						JSONObject tableMsgObj;
						for (Object object2 : tableMsgAry) {
							tableMsgObj = (JSONObject) object2;
							String datasetId = tableMsgObj.getString("dataSetId");
							JSONObject tableObj = tableMsgObj.getJSONObject("table");
							String tableName = tableObj.getString("tableName");
							String tableNamePrefix = tableObj.getString("tableName") + "_0_";
							JSONArray columnsAry = tableMsgObj.getJSONArray("columns");

							// 构建sql start
							DataSetBuilder builder = new DataSetBuilder();
							Map<String, Object> parameter = new HashMap<String, Object>();
							parameter.put("HttpServletRequest", request);
							parameter.putAll(params);

							Long databaseId = tableObj.getLong("databaseId");
							if (params.getLong("databaseId") != null && !params.getLong("databaseId").equals(0))
								databaseId = params.getLong("databaseId");

							Query query = builder.buildQuery(datasetId, parameter);

							String sql = query.toSql();

							List<Map<String, Object>> lists = mutilDatabaseBean.getDataListBySql(sql, databaseId);

							JSONObject columnObj;
							int count = 0;
							int rowNum = Integer.parseInt(columnsAry.getJSONObject(0).getString("id").split("-")[0]);
							// 插入空行
							Row sourceRow = excelSheet.getRow(rowNum);
							if (sourceRow == null) {
								sourceRow = excelSheet.createRow(rowNum);
							}
							Row offsetRow = getOffsetRow(excelSheet, sourceRow);
							insertRows(excelSheet, sourceRow, offsetRow, lists.size(), excelSheet.getMergedRegions());
							int offsetCount = offsetRow.getRowNum() - sourceRow.getRowNum();
							String dType = null;
							for (Map<String, Object> map : lists) {
								for (Object object3 : columnsAry) {
									columnObj = (JSONObject) object3;
									String columnId = columnObj.getString("id");
									String[] columnIds = columnId.split("-");
									dType = spreadRule.getJSONObject(columnId).getString("A001-1-001");
									Object field = tableName == null ? map.get(columnObj.getString("fieldName")) : map.get(tableNamePrefix + columnObj.getString("fieldName"));
									if (field != null) {
										int rownum = Integer.parseInt(columnIds[0]) + count + offsetCount;
										int colnum = Integer.parseInt(columnIds[1]);

										Row row = excelSheet.getRow(rownum);
										if (row == null) {
											row = excelSheet.createRow(rownum);
										}

										Cell cell = row.getCell(colnum);
										if (cell == null) {
											cell = row.createCell(colnum);
										}
										//图片类型
										if("imageUpload".equalsIgnoreCase(dType)){
											fileIds.add(field.toString());
											JSONObject imageObj = new JSONObject();
											imageObj.put("cell", cell);
											imageObj.put("value", field);
											imagesAry.add(imageObj);
										}else{											
											if (field instanceof Date) {
												cell.setCellValue((Date) field);
											} else if (field instanceof Number) {
												cell.setCellValue(Double.parseDouble(field.toString()));
											} else {
												cell.setCellValue(field.toString());
											}
										}

									}
								}
								count++;
							}
						}
					}
					
					// 填充图片
					if(!imagesAry.isEmpty()){
						JSONObject imageObj = null;
						XSSFCell cell = null;
						String value = null;
						byte[] bytes= null;
						FormAttachment formAttachment = null;
						CreationHelper helper = workbook.getCreationHelper();
						// 创建绘图工具 
						Drawing drawing = excelSheet.createDrawingPatriarch();
						
						FormAttachmentQuery query = new FormAttachmentQuery();
						query.setParents(fileIds);
						List<FormAttachment> attas = formAttachmentService.list(query);
						Map<String,FormAttachment> fileMap = new HashMap<>();
						if(attas!=null && !attas.isEmpty()){
							for (FormAttachment atta : attas) {
								fileMap.put(atta.getParent(), atta);
							}
						}
						List<CellRangeAddress> mergedRegions = excelSheet.getMergedRegions();
						CellRangeAddress currentCellRangeAddress = null;
						if(!fileMap.isEmpty()){
							for (Object object : imagesAry) {
								imageObj = (JSONObject) object;
								cell = imageObj.getObject("cell", XSSFCell.class);
								value = imageObj.getString("value");
								formAttachment = fileMap.get(value);
								if(formAttachment == null){
									continue;
								}
								int picType = 0;
								if(StringUtils.endsWithIgnoreCase(formAttachment.getFileName(), "jpeg") 
										|| StringUtils.endsWithIgnoreCase(formAttachment.getFileName(), "jpg")){
									picType = Workbook.PICTURE_TYPE_JPEG;
								}else if(StringUtils.endsWithIgnoreCase(formAttachment.getFileName(), "png")){
									picType = Workbook.PICTURE_TYPE_PNG;
								}else{
									//其它格式不处理
									continue;
								}
								if("0".equalsIgnoreCase(formAttachment.getType())){
									String projectpath = request.getSession().getServletContext().getRealPath("/");
									File file = new File(projectpath + formAttachment.getSaveServicePath());
									if(file.exists()){
										bytes = FileUtils.readFileToByteArray(file);
									}
								}else{
									bytes = formAttachment.getFileContent();
								}
								if(bytes==null){
									continue;
								}
								int pictureIdx = workbook.addPicture(bytes, picType);
								//add a picture shape 添加图形
								ClientAnchor anchor = helper.createClientAnchor();
								currentCellRangeAddress = null;
								for (CellRangeAddress cellRangeAddress : mergedRegions) {
									if(cellRangeAddress.isInRange(cell.getRowIndex(), cell.getColumnIndex())){
										currentCellRangeAddress = cellRangeAddress;
										break;
									}
								}
								//添加图片位置
								anchor.setCol1(cell.getColumnIndex());
								anchor.setCol2((currentCellRangeAddress==null?cell.getColumnIndex():currentCellRangeAddress.getLastColumn())+1);
								anchor.setRow1(cell.getRowIndex());
								anchor.setRow2((currentCellRangeAddress==null?cell.getRowIndex():currentCellRangeAddress.getLastRow())+1);
								Picture pict = drawing.createPicture(anchor, pictureIdx);
								//自动调整图片
								//pict.resize();
								
							}
						}
					}
				}

				/*
				 * 自动合并
				 */
				parseRule("data_merge", excelSheet, new ParseRule() {
					@Override
					public void exec(Sheet sheet, JSONObject obj) {
						mergeCell(sheet, obj);
					}
				});
				//自动行高和列宽
				parseRule("data_autosize", excelSheet, new ParseRule() {
					@Override
					public void exec(Sheet sheet, JSONObject obj) {
						setAutoSize(sheet, obj);
					}
				});
			}

			evaluator.evaluateAll();
			workbook.write(this.os);
		}
	}
	
	/**
	 * 模板类
	 *
	 */
	interface ParseRule{
		void exec(Sheet sheet,JSONObject obj);
	}
	/**
	 * 解析 合并等类似格式规则
	 * @param ruleStrName
	 * @param sheet
	 * @param pr
	 */
	void parseRule(String ruleStrName,Sheet sheet,ParseRule pr){
		JSONObject pageObj = spreadRule.getJSONObject("page");
		String mergeStr = pageObj.getString(ruleStrName);
		if (StringUtils.isNotEmpty(mergeStr)) {
 			JSONArray ary = JSON.parseArray(mergeStr);
			for (Object object : ary) {
				pr.exec(sheet,(JSONObject) object);
			}
		}
	}
	

	/**
	 * 自动行高和列宽
	 * @param sheet
	 * @param obj
	 */
	void setAutoSize(Sheet sheet, JSONObject obj) {
		String type = obj.getJSONObject("col-1").getString("name");
		if("autoRow".equals(type)){
			setAutoRowHeight(sheet, obj);
		}else{
			setAutoColumnWidth(sheet, obj);
		}
	}
	void setAutoColumnWidth(Sheet sheet, JSONObject obj) {
		JSONObject rule = obj.getJSONObject("col-2").getJSONArray("datas").getJSONObject(0);
		int startCol = rule.getIntValue("col");
		int lastCol = startCol + rule.getIntValue("colCount");
		for (int i = startCol; i <= lastCol; i++) {
			sheet.autoSizeColumn(i,true);
		}
	}
	void setAutoRowHeight(Sheet sheet, JSONObject obj) {
		JSONObject mergeRule = obj.getJSONObject("col-2").getJSONArray("datas").getJSONObject(0);
		int startRow = getNewOffsetRowNum(mergeRule.getIntValue("row"));
		int lastRow = getMergeLastRowNum(mergeRule, startRow);
		CellStyle style = workbook.createCellStyle();
		style.setWrapText(true);
		CellStyle rowStyle = null;
		for (int i = startRow; i <= lastRow; i++) {
			Row row = sheet.getRow(i);
			rowStyle = row.getRowStyle();
			row.setHeight((short) -1);
			if (rowStyle != null) {
				rowStyle.setWrapText(true);
			} else {
				row.setRowStyle(style);
			}
		}
	}

	/**
	 * 合并单元格
	 * 
	 * @param sheet
	 * @param mergeObj
	 */
	void mergeCell(Sheet sheet, JSONObject mergeObj) {
		String type = mergeObj.getJSONObject("col-1").getString("name");
		JSONObject mergeRule = mergeObj.getJSONObject("col-2").getJSONArray("datas").getJSONObject(0);
		int startRow = getNewOffsetRowNum(mergeRule.getIntValue("row"));
		int lastRow = getMergeLastRowNum(mergeRule, startRow);
		int startCol = mergeRule.getIntValue("col");
		int lastCol = startCol + mergeRule.getIntValue("colCount");

		List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
		List<Integer> removeIndexs = new ArrayList<>();
		List<CellRangeAddress> waitMergedRegions = new ArrayList<>();
		if (type.equals("top_buttom")) {
			/*
			 * 上下合并
			 */
			for (int j = startCol; j < lastCol; j++) {
				Integer firstNum = null;
				Integer lastNum = null;
				boolean isMerge = false;
				for (int i = startRow; i <= lastRow; i++) {
					Cell fcell = sheet.getRow(i).getCell(j);
					Cell scell = null;
					if(i != lastRow){
						scell = sheet.getRow(i + 1).getCell(j);
					}
					if (canMerge(fcell, scell)) {
						if (firstNum == null) {
							firstNum = i;
							lastNum = i + 1;
							isMerge = true;
						} else {
							lastNum += 1;
						}
					} else {
						if (firstNum != null && isMerge) {
							waitMergedRegions.add(new CellRangeAddress(firstNum, lastNum, j, j));
						}
						firstNum = null;
						lastNum = null;
						isMerge = false;
					}
				}
			}
			/**
			 * 匹配
			 */
			for (CellRangeAddress cellRangeAddress : mergedRegions) {
				int cfr = cellRangeAddress.getFirstRow(), clr = cellRangeAddress.getLastRow(), cfc = cellRangeAddress.getFirstColumn(), clc = cellRangeAddress.getLastColumn();
				if (cfr != clr) {
					continue;
				}
				for (CellRangeAddress waitMergeCell : waitMergedRegions) {
					int wfr = waitMergeCell.getFirstRow(), wlr = waitMergeCell.getLastRow(), wfc = waitMergeCell.getFirstColumn(), wlc = waitMergeCell.getLastColumn();
					if (cfr >= wfr && cfr <= wlr && cfc == wfc && clc >= wlc) {
						int index = mergedRegions.indexOf(cellRangeAddress);
						if (!removeIndexs.contains(index)) {
							removeIndexs.add(index);
						}
						if (clc > wlc) {
							waitMergeCell.setLastColumn(clc);
						}
					}
				}
			}

		} else if (type.equals("left_right")) {
			/*
			 * 左右合并
			 */

			for (int j = startRow; j <= lastRow; j++) {
				Integer firstNu = null;
				Integer lastNu = null;
				boolean isMerg = false;
				Cell fcel = null;
				Cell scel = null;
				for (int i = startCol; i < lastCol; i++) {
					 fcel = sheet.getRow(j).getCell(i);
					 scel = sheet.getRow(j).getCell(i + 1);
					for (CellRangeAddress cellRangeAddress : mergedRegions) {
						int cfr = cellRangeAddress.getFirstRow() ;//首行，从0开始
						int clr = cellRangeAddress.getLastRow();//末行,从0开始 
						int cfc = cellRangeAddress.getFirstColumn();	//首列从0开始
						int clc = cellRangeAddress.getLastColumn();	//末列
						//j开始行，从0开始,i开始列，从0开始
						if(cfr<=j && j<=clr && cfc<=i && i+1<=clc){
							scel.setCellValue(fcel.getStringCellValue());
						}
					}
					if (canMerge(fcel, scel)) {
						if (firstNu == null) {
							firstNu = i;
							lastNu = i + 1;
							isMerg = true;
						} else {
							lastNu += 1;
						}
					} else {
						if (firstNu != null && isMerg) {
							waitMergedRegions.add(new CellRangeAddress(j, j, firstNu, lastNu));
						}
						firstNu = null;
						lastNu = null;
						isMerg = false;
					}
				}
				
			}
			
			/**
			 * 匹配
			 */
			for (CellRangeAddress cellRangeAddress : mergedRegions) {
				int cfr = cellRangeAddress.getFirstRow(), clr = cellRangeAddress.getLastRow(), cfc = cellRangeAddress.getFirstColumn(), clc = cellRangeAddress.getLastColumn();
				for (CellRangeAddress waitMergeCell : waitMergedRegions) {
					int wfr = waitMergeCell.getFirstRow(), wlr = waitMergeCell.getLastRow(), wfc = waitMergeCell.getFirstColumn(), wlc = waitMergeCell.getLastColumn();
					if (cfc >= wfc && cfc <= clc && cfr == wfr && clr >= wlr) {
						int index = mergedRegions.indexOf(cellRangeAddress);
						if (!removeIndexs.contains(index)) {
							removeIndexs.add(index);
						}
						if (clr > wlr) {
							waitMergeCell.setLastRow(clr);
						}
					}
				}
			}
		} else {

			int arr[][] = new int[lastRow + 1][lastCol + 1];
			for (int i = 0; i <= lastRow; i++) {
				for (int j = 0; j <= lastCol; j++) {

					arr[i][j] = 1;
				}

			}
			int arr1[][] = new int[lastRow + 1][lastCol + 1];
			for (int i = 0; i <= lastRow; i++) {
				for (int j = 0; j <= lastCol; j++) {

					arr1[i][j] = 1;
				}

			}
			/*
			 * 左右上下都合并 搞个多维数组
			 */
			for (int j = startCol; j <= lastCol; j++) {
				Integer firstNum = null;
				Integer lastNum = null;
				boolean isMerge = false;
				for (int i = startRow; i <= lastRow; i++) {
					Cell fcell = sheet.getRow(i).getCell(j);
					Cell scell = null;
					Cell scedd = null;
					if(i != lastRow){
						 scell = sheet.getRow(i + 1).getCell(j);
						 scedd = sheet.getRow(i).getCell(j+1);
					}
					for (CellRangeAddress cellRangeAddress : mergedRegions) {
						int cfr = cellRangeAddress.getFirstRow() ;//首行，从0开始
						int clr = cellRangeAddress.getLastRow();//末行,从0开始 
						int cfc = cellRangeAddress.getFirstColumn();	//首列从0开始
						int clc = cellRangeAddress.getLastColumn();	//末列
						//j开始行，从0开始,i开始列，从0开始
						if(cfr<=i && i<=clr && cfc<=j && j+1<=clc ){
							scedd.setCellValue(fcell.getStringCellValue());
						}
					}
					
					if (canMerge(fcell, scell)) {
						arr[i + 1][j] = 0;
						if (firstNum == null) {
							firstNum = i;
							lastNum = i + 1;
							isMerge = true;

						} else {

							lastNum += 1;
						}

					} else {
						if (firstNum != null && isMerge) {
							arr[firstNum][j] = lastNum - firstNum + 1;
						}
						firstNum = null;
						lastNum = null;
						isMerge = false;
					}
				}
				if (firstNum != null && isMerge) {
					arr[firstNum][j] = lastNum - firstNum + 1;
				}
			}

			for (int j = startRow; j <= lastRow; j++) {
				Integer firstNu = null;
				Integer lastNu = null;
				boolean isMerg = false;
				for (int i = startCol; i < lastCol; i++) {
					Cell fcel = sheet.getRow(j).getCell(i);
					Cell scel = sheet.getRow(j).getCell(i + 1);
					for (CellRangeAddress cellRangeAddress : mergedRegions) {
						int cfr = cellRangeAddress.getFirstRow() ;//首行，从0开始
						int clr = cellRangeAddress.getLastRow();//末行,从0开始 
						int cfc = cellRangeAddress.getFirstColumn();	//首列从0开始
						int clc = cellRangeAddress.getLastColumn();	//末列
						//j开始行，从0开始,i开始列，从0开始
						if(cfr<=j && j<=clr && cfc<=i && i+1<=clc){
							scel.setCellValue(fcel.getStringCellValue());
						}
					}
					if (canMerge(fcel, scel) && arr[j][i] != 0 && arr[j][i] == arr[j][i + 1]) {
						if (firstNu == null) {
							firstNu = i;
							lastNu = i + 1;
							isMerg = true;

						} else {
							lastNu += 1;
						}

					} else {
						if (firstNu != null && isMerg) {
							waitMergedRegions.add(new CellRangeAddress(j, j + arr[j][firstNu] - 1, firstNu, lastNu));
						} else {
							if (arr[j][i] > 1) {
								waitMergedRegions.add(new CellRangeAddress(j, j + arr[j][i] - 1, i, i));
							}
						}
						firstNu = null;
						lastNu = null;
						isMerg = false;
					}
				}
			}
			
			/**
			 * 匹配
			 */
			for (CellRangeAddress cellRangeAddress : mergedRegions) {
				/*int index = mergedRegions.indexOf(cellRangeAddress);
				if (!removeIndexs.contains(index)) {
					removeIndexs.add(index);
				}*/
				
				int cfr = cellRangeAddress.getFirstRow(), clr = cellRangeAddress.getLastRow(), cfc = cellRangeAddress.getFirstColumn(), clc = cellRangeAddress.getLastColumn();
				/*if (cfr != clr) {
					continue;
				}*/
				for (CellRangeAddress waitMergeCell : waitMergedRegions) {
					int wfr = waitMergeCell.getFirstRow(), wlr = waitMergeCell.getLastRow(), wfc = waitMergeCell.getFirstColumn(), wlc = waitMergeCell.getLastColumn();
					if (cfc >= wfc && cfc <= wlc && cfr >= wfr && clr<= wlr) {
						int index = mergedRegions.indexOf(cellRangeAddress);
						if (!removeIndexs.contains(index)) {
							removeIndexs.add(index);
						}
						/*if (clr > wlr) {
							waitMergeCell.setLastRow(clr);
						}*/
						
					}
				}
			}
		}
		// 删除原来已合并的单元格
		for (int i = removeIndexs.size() - 1; i >= 0; i--) {
			sheet.removeMergedRegion(removeIndexs.get(i));
		}
		// 重新添加合并后的单元格
		for (CellRangeAddress waitMergeCell : waitMergedRegions) {
			sheet.addMergedRegion(waitMergeCell);
		}
	}

	/**
	 * 判断2个单元格是否能合并
	 * 
	 * @param fcell
	 * @param scell
	 * @return
	 */
	boolean canMerge(Cell fcell, Cell scell) {
		if (fcell != null && scell != null && fcell.getCellType() == scell.getCellType()) {
			switch (fcell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				return fcell.getStringCellValue().equals(scell.getStringCellValue());
			case Cell.CELL_TYPE_NUMERIC:
				return fcell.getNumericCellValue() == scell.getNumericCellValue();
			case Cell.CELL_TYPE_BOOLEAN:
				return fcell.getBooleanCellValue() == scell.getBooleanCellValue();
			default:
				break;
			}
		}
		return false;
	}

	/**
	 * 计算变长后的合并单元格的范围行
	 * 
	 * @param mergeRule
	 * @param startRow
	 * @return
	 */
	int getMergeLastRowNum(JSONObject mergeRule, int startRow) {
		int rowCount = mergeRule.getIntValue("rowCount");
		int endRow = startRow + rowCount - 1;
		int lastRow = endRow;
		int sourceStartRow = mergeRule.getIntValue("row");
		Set<String> keys = rowOffset.keySet();
		for (String key : keys) {
			int keyNum = Integer.parseInt(key);
			if (sourceStartRow <= keyNum && (endRow) >= keyNum) {
				int offset = rowOffset.getIntValue(key);
				lastRow += offset;
			}
		}
		return lastRow;
	}

	/***
	 * 返回偏移的行数
	 * 
	 * @return
	 */
	int getNewOffsetRowNum(int sourceRowNum) {
		int newRowNum = sourceRowNum;
		Set<String> keys = rowOffset.keySet();
		for (String key : keys) {
			if (sourceRowNum > Integer.parseInt(key)) {
				newRowNum += rowOffset.getIntValue(key);
			}
		}
		return newRowNum;
	}

	/**
	 * 返回偏移后的行位置
	 * 
	 * @param oldRow
	 *            原始位置
	 * @return
	 */
	Row getOffsetRow(Sheet sheet, Row sourceRow) {
		Row offsetRow = sourceRow;
		int sourceRowNum = sourceRow.getRowNum();
		int newRowNum = getNewOffsetRowNum(sourceRowNum);
		if (newRowNum > sourceRowNum) {
			offsetRow = sheet.getRow(newRowNum);
		}
		return offsetRow;
	}

	/**
	 * 插入行
	 * 
	 * @param sheet
	 * @param sourceRow
	 * @param size
	 * @param cellRangeAddresss
	 */
	protected void insertRows(Sheet sheet, Row sourceRow, Row offsetRow, int size, List<CellRangeAddress> cellRangeAddresss) {
		if (size <= 1)
			return;
		// 移动要插入位置的行 如果最后一行等于自己 在后面创建一行
		if ((sourceRow.getRowNum() + 1) >= sheet.getLastRowNum()) {
			sheet.createRow(sheet.getLastRowNum() + size + 5);
		}
		// 保存偏移量信息
		rowOffset.put(sourceRow.getRowNum() + "", size - 1);

		sheet.shiftRows(offsetRow.getRowNum() + 1, sheet.getLastRowNum(), size - 1, true, false);

		copyRowsStyle(sheet, offsetRow, size);
		coverRange(sheet, offsetRow, size, cellRangeAddresss, !(sourceRow.getRowNum() == offsetRow.getRowNum()));
	}

	/**
	 * 重新计算合并单元格范围
	 * 
	 * @param sheet
	 * @param sourceRow
	 * @param size
	 * @param cellRangeAddresss
	 */
	protected void coverRange(Sheet sheet, Row offsetRow, int size, List<CellRangeAddress> cellRangeAddresss, boolean isOffset) {
		int offsetNum = 0;
		if (isOffset) {
			cellRangeAddresss = sheet.getMergedRegions();
			offsetNum = size - 1;
		}
		if (cellRangeAddresss != null && !cellRangeAddresss.isEmpty()) {
			List<Integer> removeList = new ArrayList<>();
			List<CellRangeAddress> addList = new ArrayList<>();
			for (CellRangeAddress cellRangeAddress : cellRangeAddresss) {
				// 目标行 在单元格合并范围之内
				if ((offsetRow.getRowNum() + offsetNum) > cellRangeAddress.getFirstRow() && (offsetRow.getRowNum() + offsetNum) <= cellRangeAddress.getLastRow()) {
					// 处理不跨行的合并单元格（列合并）
					if (cellRangeAddress.getFirstRow() == cellRangeAddress.getLastRow()) {
						for (int i = 1; i < size; i++) {
							sheet.addMergedRegion(new CellRangeAddress(cellRangeAddress.getFirstRow() - offsetNum + i, cellRangeAddress.getLastRow() - offsetNum + i,
									cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn()));
						}
					} else {
						// sheet.removeMergedRegion(cellRangeAddresss.indexOf(cellRangeAddress));
						removeList.add(cellRangeAddresss.indexOf(cellRangeAddress));
						// sheet.addMergedRegion(new
						// CellRangeAddress(cellRangeAddress.getFirstRow(),
						// cellRangeAddress.getLastRow() + size - 1,
						// cellRangeAddress.getFirstColumn(),
						// cellRangeAddress.getLastColumn()));
						addList.add(new CellRangeAddress(cellRangeAddress.getFirstRow() - offsetNum, cellRangeAddress.getLastRow() + (isOffset ? 0 : (size - 1)),
								cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn()));
					}
				}
			}
			for (int i = removeList.size() - 1; i >= 0; i--) {
				sheet.removeMergedRegion(removeList.get(i));
			}
			for (CellRangeAddress cellRangeAddress : addList) {
				sheet.addMergedRegion(cellRangeAddress);
			}
		}
	}

	/**
	 * 拷贝行样式
	 * 
	 * @param sheet
	 * @param sourceRow
	 * @param size
	 */
	protected void copyRowsStyle(Sheet sheet, Row sourceRow, int size) {
		XSSFRow row = null;
		for (int i = 1; i < size; i++) {
			row = (XSSFRow) sheet.createRow(sourceRow.getRowNum() + i);
			//使用自带的copy 就好 不用自己写
			row.copyRowFrom(sourceRow, new CellCopyPolicy());
			//copyRowStyle(sheet,sourceRow, row);
		}
	}

	/**
	 * 拷贝样式
	 * 
	 * @param sourceRow
	 * @param targetRow
	 */
	protected void copyRowStyle(Sheet sheet,Row sourceRow, Row targetRow) {
		targetRow.setHeight(sourceRow.getHeight());
		targetRow.setRowStyle(sourceRow.getRowStyle());

		
		int lastCellNum = sourceRow.getLastCellNum();
		XSSFCell sourceCell = null;
		XSSFCell targetCell = null;
		CellCopyPolicy policy = new CellCopyPolicy();
		policy.setCopyCellFormula(true);
		for (int i = 0; i < lastCellNum; i++) {
			sourceCell = (XSSFCell) sourceRow.getCell(i);
			if (sourceCell != null) {
				targetCell = (XSSFCell) targetRow.createCell(i);
				targetCell.setCellStyle(sourceCell.getCellStyle());
				if (sourceCell.getCellType() == Cell.CELL_TYPE_FORMULA && sourceCell.getCellFormula() != null) {
					//targetCell.copyCellFrom(sourceCell, policy);
					targetCell.setCellFormula(sourceCell.getCellFormula());
				}
			}
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
		}

	}

	/**
	 * 填充行高
	 * 
	 * @param row
	 * @param rowObj
	 */
	protected void _fixRowHeight(Row row, JSONObject rowObj) {
		if (rowObj.containsKey("size")) {
			row.setHeight((short) (13.5 * rowObj.getIntValue("size")));
		}
		if (rowObj.containsKey("visible")) {
			boolean visible = rowObj.getBooleanValue("visible");
			row.setZeroHeight(!visible);
		}
	}

	/**
	 * 填充行高
	 * 
	 * @param row
	 * @param rowObj
	 */
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

	/**
	 * 填充没有数据的行高
	 * 
	 * @param row
	 * @param rowObj
	 */
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
		if (styleObj == null || styleObj.isEmpty()) {
			return;
		}
		// 添加边框
		addBorder(cellStyle, styleObj);

		// 添加数据格式
		addDataFormat(cellStyle, styleObj);

		// 对齐样式
		hAlignAndvAlign(cellStyle, styleObj);

		// 填充字体
		addFont(cellStyle, styleObj);

		// 背景色
		addBackGround(cellStyle, styleObj);

		// 自动换行
		if (styleObj.containsKey("wordWrap")) {
			cellStyle.setWrapText(styleObj.getBooleanValue("wordWrap"));
		}

		cell.setCellStyle(cellStyle);

	}

	/**
	 * 背景色
	 * 
	 * @param cellStyle
	 * @param styleObj
	 */
	protected void addBackGround(CellStyle cellStyle, JSONObject styleObj) {
		if (styleObj.containsKey("backColor")) {
			try {
				String[] colors = styleObj.getString("backColor").replace("rgb(", "").replace(")", "").replace(" ", "").split(",");
				XSSFCellStyle xs = (XSSFCellStyle) cellStyle;
				XSSFColor color = new XSSFColor(new java.awt.Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2])));
				xs.setFillPattern(CellStyle.SOLID_FOREGROUND);
				xs.setFillForegroundColor(color);
				xs.setFillBackgroundColor(color);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	/**
	 * 填充字体
	 * 
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

				if (fontStyles.length == 2) {
					font.setFontName(fontStyles[fontStyles.length - 1]);

					Double d = Double.parseDouble(fontStyles[fontStyles.length - 2].replace("px", ""));
					font.setFontHeightInPoints((short) (d.shortValue() - 6.0));
				}

				if (fontStyles.length >= 2) {
					font.setFontName(fontStyles[fontStyles.length - 1]);

					Double d = Double.parseDouble(fontStyles[fontStyles.length - 2].replace("px", ""));
					font.setFontHeightInPoints((short) (d.shortValue() - 6.0));
				}
			}
			if (styleObj.containsKey("foreColor")) {
				String[] colors = styleObj.getString("foreColor").replace("rgb(", "").replace(")", "").replace(" ", "").split(",");
				XSSFFont hf = (XSSFFont) font;
				hf.setColor(new XSSFColor(new java.awt.Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2]))));
			}
		} catch (Exception e) {
		}
		// 下划线
		if (styleObj.containsKey("textDecoration") && styleObj.getIntValue("textDecoration")==1) {
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
		if (styleObj != null && styleObj.containsKey("formatter") && styleObj.getString("formatter") != null) {
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
			// 0 左 1中 2右
			int hAlign = styleObj.getIntValue("hAlign");
			cellStyle.setAlignment(hAlign == 0 ? CellStyle.ALIGN_LEFT : (hAlign == 2 ? CellStyle.ALIGN_RIGHT : CellStyle.ALIGN_CENTER));
		} /*
			 * else { cellStyle.setAlignment(CellStyle.ALIGN_GENERAL); }
			 */

		if (styleObj.containsKey("vAlign")) {
			// 0 上 1中 2下
			int vAlign = styleObj.getIntValue("vAlign");
			cellStyle.setVerticalAlignment(vAlign == 0 ? CellStyle.VERTICAL_TOP : (vAlign == 2 ? CellStyle.VERTICAL_BOTTOM : CellStyle.VERTICAL_CENTER));
		} /*
			 * else {
			 * cellStyle.setVerticalAlignment(CellStyle.VERTICAL_JUSTIFY); }
			 */
	}

	public static void main(String[] args) throws Exception {
		/*
		 * int s = 177; System.out.println(((s + 5) / 7 * 256) / 256);
		 */

		/*File file = new File("D://a.xls");
		OutputStream os = new FileOutputStream(file);

		Workbook xw = new SXSSFWorkbook();
		// XSSFWorkbook xw = (XSSFWorkbook) workbook;
		XSSFSheet sheet = (XSSFSheet) xw.createSheet("啊");
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("我的");
		cell = row.createCell(2);
		cell.setCellValue("你的");

		// 往下移动
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue("我的222");
		cell = row.createCell(2);
		cell.setCellValue("你的333");
		sheet.shiftRows(1, 100, 1, true, false);

		// 在原来位置插入信息
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue("我的1");
		cell = row.createCell(2);
		cell.setCellValue("你的2");
		xw.write(os);*/
		/*
		String numberPattern = "^[-+]?(([0-9]+)([.]([0-9]+))?)$";
		String str = "-" ;
		//str = "1.1" ;
		System.out.println(str.matches(numberPattern));*/

	}
}
