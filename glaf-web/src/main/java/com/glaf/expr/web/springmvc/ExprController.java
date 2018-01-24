package com.glaf.expr.web.springmvc;

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
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.metamodel.query.Query;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.WorkbookEvaluator;
import org.apache.poi.ss.formula.atp.DateParser;
import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.formula.eval.EvaluationException;
import org.apache.poi.ss.formula.eval.NumberEval;
import org.apache.poi.ss.formula.eval.OperandResolver;
import org.apache.poi.ss.formula.eval.ValueEval;
import org.apache.poi.ss.formula.functions.Fixed1ArgFunction;
import org.apache.poi.ss.formula.functions.Fixed2ArgFunction;
import org.apache.poi.ss.formula.functions.Function;
import org.apache.poi.ss.formula.functions.NumericFunction;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.POILogger;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.boris.expr.Expr;
import org.boris.expr.ExprException;
import org.boris.expr.engine.DependencyEngine;
import org.boris.expr.engine.GridReference;
import org.boris.expr.engine.Range;
import org.boris.expr.util.ExcelDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.dep.report.domain.DepReportTemplate;
import com.glaf.dep.report.service.DepReportTemplateService;
import com.glaf.expr.poi.POIFunctionsRegister;
import com.glaf.expr.utils.BaseEngineProvider;
import com.glaf.form.core.util.MutilDatabaseBean;

/**
 * GIS弹出窗
 *
 */
@Controller("/expr")
@RequestMapping("/expr")
public class ExprController {

	@Autowired
	protected DepReportTemplateService depReportTemplateService;

	@Autowired
	protected MutilDatabaseBean mutilDatabaseBean;

	/**
	 * 表达式计算
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/calculate")
	@ResponseBody
	public byte[] calculate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String params = RequestUtils.getString(request, "params", null);

		DependencyEngine engine = new DependencyEngine(new BaseEngineProvider());
		JSONObject outputObj = new JSONObject();
		List<String> outputList = new ArrayList<>();
		if (StringUtils.isNotEmpty(params)) {

			String useKey = "mtUserId";
			JSONObject paramsObj = JSON.parseObject(params);

			Long id = paramsObj.getLong(useKey);
			DepReportTemplate depReportTemplate = depReportTemplateService.getDepReportTemplate(id);

			/*
			 * 模板填充
			 */
			String tmpJson = depReportTemplate.getTmpJson();

			if (tmpJson != null && !tmpJson.isEmpty()) {

				String pattern = "/OADate\\((\\d*\\.\\d*)\\)/";

				JSONObject tmpJsonObj = JSON.parseObject(tmpJson);
				JSONObject sheets = tmpJsonObj.getJSONObject("sheets");
				Set<String> keys = sheets.keySet();
				for (String sheetName : keys) {
					JSONObject sheet = sheets.getJSONObject(sheetName);
					JSONObject data = sheet.getJSONObject("data");
					if (data != null) {
						JSONObject dataTable = data.getJSONObject("dataTable");
						if (dataTable != null) {
							Set<String> rowKeys = dataTable.keySet();
							for (String rowKey : rowKeys) {
								JSONObject columnsObj = dataTable.getJSONObject(rowKey);
								Set<String> colKeys = columnsObj.keySet();
								for (String colKey : colKeys) {
									Range range = new Range(sheetName, new GridReference(Integer.parseInt(colKey) + 1, Integer.parseInt(rowKey) + 1));
									JSONObject columnObj = columnsObj.getJSONObject(colKey);
									String expression = columnObj.getString("value");
									String formula = columnObj.getString("formula");
									if (StringUtils.isNotEmpty(formula)) {
										expression = "=" + formula;
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
											//expression = fromOaDate(Double.parseDouble(dateStrValue)) + "";
											expression = dateStrValue ;
										}
									}
									engine.set(range, expression);
								}
							}
						}
					}
				}

			}

			String ruleJson = depReportTemplate.getRuleJson();
			if (ruleJson != null && !ruleJson.isEmpty()) {
				JSONObject ruleJsonObj = JSON.parseObject(ruleJson);
				JSONObject pageObj = ruleJsonObj.getJSONObject("page");

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
							String tableNamePrefix = tableObj.getString("tableName") + "_0_";
							JSONArray columnsAry = tableMsgObj.getJSONArray("columns");

							// 构建sql start
							DataSetBuilder builder = new DataSetBuilder();
							Map<String, Object> parameter = new HashMap<String, Object>();
							parameter.put("HttpServletRequest", request);
							parameter.putAll(JSON.parseObject(params));

							Query query = builder.buildQuery(datasetId, parameter);

							String sql = query.toSql();

							List<Map<String, Object>> lists = mutilDatabaseBean.getDataListBySql(sql, tableObj.getLong("databaseId"));

							JSONObject columnObj;
							int count = 0;
							for (Map<String, Object> map : lists) {
								for (Object object3 : columnsAry) {
									columnObj = (JSONObject) object3;
									String columnId = columnObj.getString("id");
									String[] columnIds = columnId.split("-");
									Object field = map.get(tableNamePrefix + columnObj.getString("fieldName"));
									if (field != null) {
										engine.set(new Range(null, new GridReference(Integer.parseInt(columnIds[1]) + 1, Integer.parseInt(columnIds[0]) + 1 + count)),
												field.toString());
										count++;
									}
								}
							}
						}
					}
				}

				/*
				 * 获取返回值定义的参数
				 */
				String paramsDefined = "params-defined";
				String paramsDefinedStr = pageObj.getString(paramsDefined);
				if (StringUtils.isNotEmpty(paramsDefinedStr)) {
					JSONArray ary = JSON.parseArray(paramsDefinedStr);
					JSONObject col2Obj = null;
					JSONObject col3Obj = null;
					String linkageControlStr = null;
					JSONObject valObj;
					if (ary != null && !ary.isEmpty()) {
						for (Object object : ary) {
							valObj = (JSONObject) object;
							col2Obj = valObj.getJSONObject("col-2");
							col3Obj = valObj.getJSONObject("col-3");
							if ("output".equalsIgnoreCase(col3Obj.getString("name"))) {
								linkageControlStr = col2Obj.getJSONObject("data").getString("linkageControl");
								if (StringUtils.isNotEmpty(linkageControlStr)) {
									JSONArray lcAry = JSON.parseArray(linkageControlStr);
									for (Object object2 : lcAry) {
										JSONObject lcObj = (JSONObject) object2;
										outputList.add(lcObj.getString("id"));
									}
								}
							}
						}
					}
				}
			}

			/*
			 * 填充参数
			 */
			Set<String> keys = paramsObj.keySet();
			for (String key : keys) {
				if (key.indexOf("-") != -1) {
					String[] k = key.split("-");
					engine.set(new Range(null, new GridReference(Integer.parseInt(k[1]) + 1, Integer.parseInt(k[0]) + 1)), paramsObj.getString(key));
				}
			}

			for (String outputKey : outputList) {
				String[] k = outputKey.split("-");
				Expr value = engine.getValue(new Range(null, new GridReference(Integer.parseInt(k[1]) + 1, Integer.parseInt(k[0]) + 1)));
				if (value != null) {
					outputObj.put(outputKey, value.toString());
				}
			}
		}

		return outputObj.toJSONString().getBytes("UTF-8");
	}
	
	/**
	 * 表达式计算by poi
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/calculateByPoi")
	@ResponseBody
	public byte[] calculateByPoi(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String params = RequestUtils.getString(request, "params", null);

		System.setProperty("org.apache.poi.util.POILogger", "org.apache.poi.util.SystemOutLogger");
		System.setProperty("poi.log.level", POILogger.INFO + "");
		
		Workbook workbook = new SXSSFWorkbook(300);
		//注册方法
		POIFunctionsRegister.register(workbook);
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		Sheet excelSheet = null ;
		
		JSONObject outputObj = new JSONObject();
		List<String> outputList = new ArrayList<>();
		if (StringUtils.isNotEmpty(params)) {

			String useKey = "mtUserId";
			JSONObject paramsObj = JSON.parseObject(params);

			Long id = paramsObj.getLong(useKey);
			DepReportTemplate depReportTemplate = depReportTemplateService.getDepReportTemplate(id);

			/*
			 * 模板填充
			 */
			String tmpJson = depReportTemplate.getTmpJson();

			String numberPattern = "^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$" ;
			if (tmpJson != null && !tmpJson.isEmpty()) {

				String pattern = "/OADate\\((\\d*\\.?\\d*)\\)/";

				JSONObject tmpJsonObj = JSON.parseObject(tmpJson);
				JSONObject sheets = tmpJsonObj.getJSONObject("sheets");
				Set<String> keys = sheets.keySet();
				for (String sheetName : keys) {
					JSONObject sheet = sheets.getJSONObject(sheetName);
					JSONObject data = sheet.getJSONObject("data");
					if (data != null) {
						JSONObject dataTable = data.getJSONObject("dataTable");
						if (dataTable != null) {
							excelSheet = workbook.createSheet(sheetName);
							Set<String> rowKeys = dataTable.keySet();
							for (String rowKey : rowKeys) {
								JSONObject columnsObj = dataTable.getJSONObject(rowKey);
								Set<String> colKeys = columnsObj.keySet();
								Row row = excelSheet.createRow(Integer.parseInt(rowKey));
								
								for (String colKey : colKeys) {
									JSONObject columnObj = columnsObj.getJSONObject(colKey);
									String expression = columnObj.getString("value");
									String formula = columnObj.getString("formula");
									
									Cell cell =row.createCell(Integer.parseInt(colKey));
									
									if (StringUtils.isNotEmpty(formula)) {
										cell.setCellFormula(formula);
									}else if(StringUtils.isNotEmpty(expression)){
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
												//这里有一个坑  如果日期小于1 最后解析出的值会是-1，现强制使用数值类型来计算 
												cell.setCellValue(dateDouble);
												/*if(dateDouble<1){
												}else{
													cell.setCellValue(parseOaDate(dateDouble));
												}*/
											}
										}else{
											if(expression.matches(numberPattern)){
												cell.setCellValue(Double.parseDouble(expression));
											}else{
												cell.setCellValue(expression);
											}
										}
									}
								}
							}
						}
					}
				}

			}

			String ruleJson = depReportTemplate.getRuleJson();
			if (ruleJson != null && !ruleJson.isEmpty()) {
				JSONObject ruleJsonObj = JSON.parseObject(ruleJson);
				JSONObject pageObj = ruleJsonObj.getJSONObject("page");

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
							parameter.putAll(JSON.parseObject(params));

							Query query = builder.buildQuery(datasetId, parameter);

							String sql = query.toSql();

							List<Map<String, Object>> lists = mutilDatabaseBean.getDataListBySql(sql, tableObj.getLong("databaseId"));

							JSONObject columnObj;
							int count = 0;
							for (Map<String, Object> map : lists) {
								for (Object object3 : columnsAry) {
									columnObj = (JSONObject) object3;
									String columnId = columnObj.getString("id");
									String[] columnIds = columnId.split("-");
									Object field = tableName==null?map.get(columnObj.getString("fieldName")):map.get(tableNamePrefix + columnObj.getString("fieldName"));
									if (field != null) {
										int rownum = Integer.parseInt(columnIds[0]) +count ;
										int colnum = Integer.parseInt(columnIds[1]);
										Row row = excelSheet.getRow(rownum);
										if(row == null){
											row = excelSheet.createRow(rownum);
										}
										Cell cell = row.getCell(colnum);
										if(cell == null){
											cell = row.createCell(colnum);
										}
										if(field instanceof Date){
											cell.setCellValue((Date)field);
										}else if(field instanceof Number){
											cell.setCellValue(Double.parseDouble(field.toString()));
										}else{
											cell.setCellValue(field.toString());
										}
									}
								}
								count++;
							}
						}
					}
				}

				/*
				 * 获取返回值定义的参数
				 */
				String paramsDefined = "params-defined";
				String paramsDefinedStr = pageObj.getString(paramsDefined);
				if (StringUtils.isNotEmpty(paramsDefinedStr)) {
					JSONArray ary = JSON.parseArray(paramsDefinedStr);
					JSONObject col2Obj = null;
					JSONObject col3Obj = null;
					String linkageControlStr = null;
					JSONObject valObj;
					if (ary != null && !ary.isEmpty()) {
						for (Object object : ary) {
							valObj = (JSONObject) object;
							col2Obj = valObj.getJSONObject("col-2");
							col3Obj = valObj.getJSONObject("col-3");
							if ("output".equalsIgnoreCase(col3Obj.getString("name"))) {
								linkageControlStr = col2Obj.getJSONObject("data").getString("linkageControl");
								if (StringUtils.isNotEmpty(linkageControlStr)) {
									JSONArray lcAry = JSON.parseArray(linkageControlStr);
									for (Object object2 : lcAry) {
										JSONObject lcObj = (JSONObject) object2;
										outputList.add(lcObj.getString("id"));
									}
								}
							}
						}
					}
				}
			}

			/*
			 * 填充参数
			 */
			Set<String> keys = paramsObj.keySet();
			for (String key : keys) {
				if (key.indexOf("-") != -1) {
					String[] k = key.split("-");
					int rownum = Integer.parseInt(k[0]);
					int colnum = Integer.parseInt(k[1]);
					Row row = excelSheet.getRow(rownum);
					if(row == null){
						row = excelSheet.createRow(rownum);
					}
					Cell cell = row.getCell(colnum);
					if(cell == null){
						cell = row.createCell(colnum);
					}
					String value = paramsObj.getString(key);
					try {
						if(value.matches(numberPattern)){
							cell.setCellValue(Double.parseDouble(value));
						}else{
							Calendar date ;
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
			
			for (String outputKey : outputList) {
				String[] k = outputKey.split("-");
				int rownum = Integer.parseInt(k[0]);
				int colnum = Integer.parseInt(k[1]);
				Row row = excelSheet.getRow(rownum);
				Cell cell = row.getCell(colnum);
				if(cell != null){
					CellValue cellValue = evaluator.evaluate(cell);
					if (cellValue != null) {
						outputObj.put(outputKey, getCellValue(cellValue));
					}
				}
			}
		}

		return outputObj.toJSONString().getBytes("UTF-8");
	}
	
	private Object getCellValue(CellValue cellValue){
		Object retObj = null ;
		switch (cellValue.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			retObj = cellValue.getNumberValue();
			break;
		case Cell.CELL_TYPE_STRING:
			retObj = cellValue.getStringValue();
			break;
		default:
			break;
		}
		return retObj;
	}
	
	private Date parseOaDate(Double time){
		Double d = time - 25569;
		Date date = new Date((long) (d * 864e5));
		int r = d >= 0 ? 1 : -1;
		Long timeLong = (long) ((time * 124416e6 + r - 3181192704e6 + date.getTimezoneOffset() * 864e5) / 1440);
		return new Date(timeLong);
	}
	
	private double fromOaDate(Double time) {
		Double d = time - 25569;
		Date date = new Date((long) (d * 864e5));
		int r = d >= 0 ? 1 : -1;
		Long timeLong = (long) ((time * 124416e6 + r - 3181192704e6 + date.getTimezoneOffset() * 864e5) / 1440);
		// ExcelDate.toJavaDate(value)
		return /* ExcelDate.toExcelDate(timeLong) */timeLong;
	}

	public static void main(String[] args) {
		//DateUtils.toDate(dateString);
		/*try {
			Expr expr = ExprParser.parse("1+1,2");
			Exprs.toUpperCase(expr);
		} catch (IOException | ExprException e) {
			e.printStackTrace();
		}*/

		DependencyEngine e = new DependencyEngine(new BaseEngineProvider());
			//{Sheet1!F7==SUM(E11:E21), Sheet1!I8==I4-H4, Sheet1!H8=输出参数, Sheet1!E10=动态参数列, F8=2, 
			//Sheet1!I4==TEXT(I3,"hh:MM"), Sheet1!E8=输入参数, 
			//Sheet1!H4==TEXT(H3,"hh:MM"), Sheet1!H3=1.4762304E12, Sheet1!I3=1.476234E12}
		try {
			e.setNamespace("sheet1");
			Date d = new Date(ExcelDate.toJavaDate(42655.333333333336));
			e.set("H3", d.toLocaleString());
			e.set("I4", "=TEXT(H3,\"hh:MM\")");
			System.out.println(e.getValue(Range.valueOf("I4")));
			//e.set("B1", "=A1*2");
			//e.set("A1", "=12*2");
			//e.set("C1", "=B1*A1");
			//e.set("D1", "=SUM(A1:C1)");
			// Range r = new Range("sheet2", GridReference.valueOf("A1"));
			// e.set(r, "3"); e.set("E1", "=sheet2!A1+2"); e.set("sheet2!A1",
			// "12");

			//System.out.println(e.getValue(Range.valueOf("B1")));
			//System.out.println(e.getValue(new Range("", new GridReference(2, 1))));
			// System.out.println(e.getValue(Range.valueOf("C1")));
			// System.out.println("D1:"+e.getValue(Range.valueOf("D1")));
			// System.out.println("2222-->"+e.getValue(Range.valueOf("sheet2!A1")));
			// System.out.println("E1-->"+e.getValue(Range.valueOf("E1")));
			// e.set("A1", "2");
			// System.out.println(e.getValue(Range.valueOf("B1"))); //
			//System.out.println(e.getValue(Range.valueOf("C1")));

			// e.evaluateVariable(variable)
		} catch (ExprException e1) {
			e1.printStackTrace();
		}

		/*String str = "/OADate(11.11)/";
		String pattern = "/OADate\\((\\d*\\.\\d*)\\)/";
		Pattern r = Pattern.compile(pattern);

		Matcher m = r.matcher(str);
		if (m.find()) {
			System.out.println("Found value: " + m.group(0));
			System.out.println("Found value: " + m.group(1));
		} else {
			System.out.println("NO MATCH");
		}

		System.out.println(Pattern.matches(pattern, str));*/
		
		HSSFWorkbook wb = new HSSFWorkbook();  
		FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
		
		HSSFSheet sheet = wb.createSheet();
		
		HSSFRow row = sheet.createRow(0);
		
		//A1
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(new Date());
	    CellValue cellValue = evaluator.evaluate(cell);
		System.out.println("A1--->"+cellValue);
		
		//B1
		cell = row.createCell(1);
		cell.setCellFormula("TEXT(A1,\"HH:mm\")");
		cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
	    evaluator.evaluateFormulaCell(cell);
	    cellValue = evaluator.evaluate(cell);
		System.out.println("B1--->"+cellValue);
		
		//C1
		HSSFCell cell3 = row.createCell(2);
		cell3.setCellFormula("VALUE(B1)");
		cell3.setCellType(HSSFCell.CELL_TYPE_FORMULA);
	    evaluator.evaluateFormulaCell(cell);
	    cellValue = evaluator.evaluate(cell3);
		System.out.println("C1--->"+cellValue);
		
		//D1
		cell = row.createCell(3);
		cell.setCellFormula("TEXT(A1,\"HH:mm\")");
	    evaluator.evaluateFormulaCell(cell);
	    cellValue = evaluator.evaluate(cell);
		System.out.println("D1--->"+cellValue);
		
		//E1
		cell = row.createCell(4);
		cell.setCellFormula("E1-B1");
	    evaluator.evaluateFormulaCell(cell);
	    cellValue = evaluator.evaluate(cell);
		System.out.println("--->"+cellValue);
		
		

		org.apache.poi.ss.usermodel.Workbook workbook = new SXSSFWorkbook();
		POIFunctionsRegister.register(workbook);
		//Function4Arg
		//FunctionEval.registerFunction("STDEVA", new STDEVA());
		
		org.apache.poi.ss.usermodel.FormulaEvaluator f =workbook.getCreationHelper().createFormulaEvaluator();
		
		org.apache.poi.ss.usermodel.Sheet s = workbook.createSheet();
		org.apache.poi.ss.usermodel.Row r = s.createRow(0);
		org.apache.poi.ss.usermodel.Cell c = r.createCell(0);
		//STDEVA
		//c.setCellValue(new Date());
		
		c = r.createCell(1);
		c.setCellValue(1);

		
		c = r.createCell(2);
		c.setCellValue(2);
		
		
		c = r.createCell(3);
		c.setCellFormula("STDEVA(B1:C1,A1)");
		//c.setCellFormula("A1+B1");
		cellValue = f.evaluate(c);
		System.out.println("STDEVA-->"+cellValue);
		
		c = r.createCell(4);
		c.setCellFormula("ROUND(AVERAGE(B1:C1),2)");
		//c.setCellFormula("A1+B1");
		cellValue = f.evaluate(c);
		System.out.println("AVERAGE-->"+cellValue);
		
		//ROUND(AVERAGE(A2:A200),2)
		
		
		//System.out.println("AA--->"+StringUtils.isNumeric("12.3"));
		
		/*Collection<String> cs = FunctionEval.getSupportedFunctionNames();
		for (String string : cs) {
			System.out.println(string);
		}*/
		
	}

}
