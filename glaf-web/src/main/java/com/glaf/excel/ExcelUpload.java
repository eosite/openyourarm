package com.glaf.excel;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.factory.TableFactory;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.identity.User;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.website.springmvc.DataSetExportController;
import com.glaf.form.core.util.MutilDatabaseBean;
import com.glaf.form.rule.Global;

@Component("excelUpload")
public class ExcelUpload {
	protected static final Log logger = LogFactory.getLog(ExcelUpload.class);
	
	@Autowired
	protected DataSetService dataSetService;
	protected IdGenerator idGenerator;
	int idruleGroup = 0;
	private String actorId; // 用户
	private JSONArray readRule; // 解析规则，第一层，第二层等解析规则
	private int excelHeaderRowCount = 1; 	//表头位置
	// 是否保存树表格式
	private String isTree;
	// 是否返回错误信息
	private String isBackError;
	// 是否允许容错,对于树表格式
	private String isCanFault;
	// EXCEL的规则字段信息
	private String ruleIdHeadName = ""; // 规则字段信息
	// 是否允许重复导入
	private String isCanRepeat;
	private JSONArray addExcelModelJson; // 表头规则，即数据集的表头与excel的表头对应关系
	private String ruleType; // 规则类型

	private JSONArray columnAry; // 数据集信息
	private ColumnDefinition idColumn; // 主键列
	private String idColumnCellName; // 主键列对应的cell表名称
	private Long databaseId; // 标段
	private String tableName; // 表名
	private Map<String, ColumnDefinition> columnMap; // 数据集字段信息
	private JSONObject cellTableColumns; // 数据集与Excel映射完的信息

	/* 创建一个数组，用来保存头部信息 */
	List<String> headList;
	/* errorData:用来存放错误的Excell行信息 */
	List errorData;
    Map<String,Object> countMap;

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	// 创建字段信息
	public TableModel createTableModel(String id, JSONArray dataArray) {
		TableModel tableModel = new TableModel();
		tableModel.setTableName(tableName);
		// id列
		ColumnModel idCol = new ColumnModel();
		idCol.setColumnName(idColumn.getColumnName());
		idCol.setJavaType(idColumn.getJavaType());
		idCol.setValue(id);
		tableModel.addColumn(idCol);

		// 遍历每个字段
		for (Object dataObj : dataArray) {
			JSONObject dataJson = (JSONObject) dataObj;
			ColumnModel colModel = new ColumnModel();
			colModel.setColumnName(dataJson.getString("columnName"));
			colModel.setJavaType(dataJson.getString("javaType"));
			colModel.setValue(dataJson.get("value"));
			tableModel.addColumn(colModel);
		}
		return tableModel;
	}

	/**
	 * 获取cell格对应的值（字符串形式）
	 * 
	 * @param xssfXell
	 * @return
	 */
	private String getStrValueByCell2(Cell xssfXell,int cellType) {
		String str = "";
		if (cellType == xssfXell.CELL_TYPE_FORMULA) {
			// 表达式
			BigDecimal bigDecimal = new BigDecimal(xssfXell.getNumericCellValue());
			str = bigDecimal.toString();
		} else if (cellType == xssfXell.CELL_TYPE_STRING) {
			// 字符串
			str = xssfXell.getStringCellValue();
		} else if (cellType == xssfXell.CELL_TYPE_BLANK) {
			str = "";
		} else if (cellType == xssfXell.CELL_TYPE_BOOLEAN) {
			str = String.valueOf(xssfXell.getBooleanCellValue());
		} else if (cellType == xssfXell.CELL_TYPE_ERROR) {
			str = "";
		} else if (cellType == xssfXell.CELL_TYPE_NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(xssfXell)) {
				double d = xssfXell.getNumericCellValue();
				short format = xssfXell.getCellStyle().getDataFormat();
				SimpleDateFormat sdf = null;
				if (format == 14 || format == 31 || format == 57 || format == 58) {
					// 日期
					sdf = new SimpleDateFormat("yyyy-MM-dd");
				} else if (format == 20 || format == 32) {
					// 时间
					sdf = new SimpleDateFormat("HH:mm");
				}else{
					sdf = new SimpleDateFormat("yyyy-MM-dd");
				}
				Date date = HSSFDateUtil.getJavaDate(d);
				str = sdf.format(date);
			} else {
				// double value = xssfXell.getNumericCellValue();
				BigDecimal bigDecimal = new BigDecimal(xssfXell.getNumericCellValue());
				
				str = bigDecimal.setScale(4,   BigDecimal.ROUND_HALF_UP).toString(); 
//				str = bigDecimal.toString();
			}
		}
		return str;
	}
//	private String getStrValueByCell(XSSFCell xssfXell) {
	private String getStrValueByCell(Cell xssfXell,Integer type) {
		String str = "";
		int cellType = xssfXell.getCellType(); // excel中格子的类型
		
		try{
			if(type != null){
				xssfXell.setCellType(type);
				str = getStrValueByCell2(xssfXell,type);
			}else{
				xssfXell.setCellType(cellType);
				str = getStrValueByCell2(xssfXell,cellType);
			}
		}catch(Exception e){
			xssfXell.setCellType(cellType);
			str = getStrValueByCell2(xssfXell,cellType);
		}
		return str;
	}

	public void saveGridSource(InputStream is, MutilDatabaseBean mutilDatabaseBean, String fileName,int count) throws Exception {
		boolean isExcel2003 = true;
        // 对文件的合法性进行验
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
//		Sheet  Workbook
//		try (XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is)) {
//        new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream)
        
		try (Workbook xssfWorkbook = isExcel2003 ?new HSSFWorkbook(is) : new XSSFWorkbook(is)) {
			// 获取第一个工作薄
//			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
			Sheet xssfSheet = xssfWorkbook.getSheetAt(0);
			int length = 0;

			List<Map> datalist = new ArrayList<>();
			Map<String, Object> rowData;
			// 遍历excel表格，保存进去
			for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				if (rowNum < excelHeaderRowCount) {
					continue;
				} else if (rowNum == excelHeaderRowCount) {
					// 保存表头数据
					if (xssfSheet.getLastRowNum() > excelHeaderRowCount) {
						/* 得到表头这一行 */
//						XSSFRow xssfRow = xssfSheet.getRow(excelHeaderRowCount);
						Row xssfRow = xssfSheet.getRow(excelHeaderRowCount);
						length = xssfRow.getLastCellNum();
						for (int i = 0; i < length; i++) {
							try{
								Cell xssfXell = xssfRow.getCell(i, Row.CREATE_NULL_AS_BLANK); // 读取excel中的格子数据
								headList.add(getStrValueByCell(xssfXell,xssfXell.CELL_TYPE_STRING).trim());
							}catch(Exception e){
								length = i+1;
							}
						}
					}
				} else {
					// 读取excel中的数据放入List中
//					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					Row xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow != null) {
						JSONObject obj = new JSONObject();
						JSONArray data = new JSONArray();
						String id = "";
						Map cellRow = new HashMap();
						
						rowData = new HashMap<String,Object>();
						
						SimpleDateFormat sdf = null;
						sdf = new SimpleDateFormat("yyyy-MM-dd");
						
						SimpleDateFormat sdf2 = null;
						sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						
						boolean isBlankRow = true;	//空行标识 
						boolean iserror = false;
						for (int cell = 0; cell < length; cell++) {
							String headName = headList.get(cell);
							// 获取对应的字段信息
							JSONObject cellTableColumn = cellTableColumns.getJSONObject(headName);
							if(cellTableColumn == null){
								//未能获取到对应的表信息,跳过
								continue;
							}
							String str = "";
							Cell xssfXell = xssfRow.getCell(cell, Row.CREATE_NULL_AS_BLANK); // 读取excel中的格子数据
							
							String cellStr = "";
							if("Date".equals(cellTableColumn.getString("javaType"))){
								cellStr = getStrValueByCell(xssfXell,null);
							}else{
								cellStr = getStrValueByCell(xssfXell,xssfXell.CELL_TYPE_STRING);
							}
							cellStr = cellStr.trim();
							
							if(!cellStr.isEmpty()){
								isBlankRow = false;	//不为空行
							}
							if (!cellTableColumn.getBoolean("byInParam")) {
								// 从excel中获取
								//XSSFCell xssfXell = xssfRow.getCell(cell, Row.CREATE_NULL_AS_BLANK); // 读取excel中的格子数据
								str = cellStr;
								if(StringUtils.isNotEmpty(str) && "Date".equals(cellTableColumn.getString("javaType"))){
									try{
										cellTableColumn.put("value", sdf.parse(str));
									}catch(Exception e){
										try{
											cellTableColumn.put("value", sdf2.parse(str));
										}catch(Exception e1){
											iserror = true;
										}
									}
									
								}else{
									cellTableColumn.put("value", str);
								}
							}
							data.add(cellTableColumn);
							if ((!ruleIdHeadName.isEmpty() && ruleIdHeadName.equals(headName)) || (!idColumnCellName.isEmpty() && idColumnCellName.equals(headName))) {
								id = str;
							}
							// 将excel表的信息放入cellRow中
							cellRow.put(headList.get(cell), str);
						}
						if(isBlankRow){
							//该行为空行，跳过
							continue;
						}
						JSONObject idData = new JSONObject();
						logger.error("id:"+id);
						logger.error(id == null || id.isEmpty());
						
						if(iserror){
							//错误数据
							cellRow.put("errorMessage", "日期类型数据转换错误，请使用YYYY-MM-DD格式");
							errorData.add(cellRow);
							continue;
						}
						if(id == null || id.isEmpty()){
							id = mutilDatabaseBean.getNextId(tableName, idColumn.getColumnName(), actorId, databaseId);
						}
						TableModel tableModel = createTableModel(id, data);
						// 重复插入时，查询数据库，判断是否有该信息
						if (isCanRepeat == null || (!isCanRepeat.equals("on") && !isCanRepeat.equals("true"))) {
							String sql = "select * from " + tableName + " where " + idColumn.getColumnName() + "='" + id
									+ "'";
							List<Map<String, Object>> countList = mutilDatabaseBean.getDataListBySql(sql, databaseId);
							if (countList != null && countList.size() >= 1) {
								cellRow.put("errorMessage", "该节点已存在");
								errorData.add(cellRow);
							} else {
								try{
									mutilDatabaseBean.insertTableData2(tableModel, databaseId);
								}catch(Exception e){
									e.printStackTrace();
									logger.error(e.toString());
									cellRow.put("errorMessage", e.toString());
									errorData.add(cellRow);
								}
							}
						} else {
							try{
								mutilDatabaseBean.insertTableData2(tableModel, databaseId);
								count++;
								countMap.put("count", count);
							}catch(Exception e){
								e.printStackTrace();
								logger.error(e.toString());
								cellRow.put("errorMessage", e.toString());
								errorData.add(cellRow);
							}
						}
					}
				}
			}
		}catch(Exception e){
			logger.error(e.toString());
			e.printStackTrace();
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String,Object> readExcel(InputStream is, Map<String, String> ruleMap, MutilDatabaseBean mutilDatabaseBean,
			User user,String fileName) throws Exception {
		String[] nodeSizeAry = null; // EXCEL编码规则ID数组，即[2,2,3,4]
        Map<String,Object> map = new HashMap<String,Object>();
        int count = 0;
		actorId = user.getActorId(); // 用户ID
		headList = new ArrayList<String>();
		errorData = new ArrayList();
		countMap = new HashMap<String,Object>();
		JSONObject excelRuleData = JSON.parseObject(ruleMap.get("excelRuleData"));
		// 获取基础规则信息，容错，树表，重复数据等
		JSONObject baseRule = excelRuleData.getJSONObject("baseRule");
		excelHeaderRowCount = 1; // 正式数据的行数(包括表头一行)
		// 是否保存树表格式
		isTree = baseRule.getString("isTree");
		// 是否返回错误信息
		isBackError = baseRule.getString("isBackError");
		// 是否允许容错,对于树表格式
		isCanFault = baseRule.getString("isCanFault");
		// 是否允许重复导入
		isCanRepeat = baseRule.getString("isCanRepeat");

		String dataSourceSet = ruleMap.get("dataSourceSet"); // 数据集
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONObject datasourceSetJSONObject = null;
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
		}
		if (datasourceSetJSONObject == null) {
			return null;
		}

		JSONArray tablesAry = datasourceSetJSONObject.getJSONArray("selectDatasource");
		if (tablesAry == null || tablesAry.size() != 1) {
			return null;
		}
		databaseId = tablesAry.getJSONObject(0).getLong("databaseId"); // 数据集标段
		if (databaseId == null) {
			databaseId = 0l;
		}
		columnAry = datasourceSetJSONObject.getJSONArray("selectColumns"); // 表信息

		JSONObject firstObj = columnAry.getJSONObject(0);
		/* 获取表名 */
		tableName = firstObj.getString("tableName");

		if (!DBUtils.isAllowedTable(tableName)) {
			throw new RuntimeException("不允许访问系统表。");
		}

		/* 获取列名 */
		List<ColumnDefinition> columns = TableFactory.getColumnDefinitions(databaseId, tableName);
		columnMap = new HashMap<String, ColumnDefinition>(); // 所有列
		idColumn = null; // 主键列
		for (ColumnDefinition column : columns) {
			if (column.isPrimaryKey()) {
				idColumn = column;
			}
			columnMap.put(column.getColumnName().toLowerCase(), column);
		}

		addExcelModelJson = JSON.parseArray(ruleMap.get("addExcelModel"));

		// 遍历表头规则找到规则字段
		logger.error("addExcelModelJson:"+addExcelModelJson.toJSONString());
		for (Object addExcelModelObj : addExcelModelJson) {
			JSONObject addExcelModel = (JSONObject) addExcelModelObj;
			if (!addExcelModel.getString("isRuleColumn").isEmpty()) {
				ruleIdHeadName = addExcelModel.getString("name");
			}
		}

		JSONObject param; // 输入形参
		// 输入形参
		String paramStr = ruleMap.get("param");
		param = null;
		if (paramStr != null && !paramStr.isEmpty()) {
			param = JSON.parseObject(paramStr);
		}

		cellTableColumns = new JSONObject(); // cell与数据集映射后的字段信息
		JSONObject columnObj = null;
		ColumnDefinition column = null;
		ColumnModel colModel = null;
		// 遍历数据集字段信息
		JSONObject columnsInformation = new JSONObject();
		
		//获取表字段的映射信息
		JSONObject metadata = dataSetService.getDataSetMetadata(tablesAry.getJSONObject(0).getString("datasetId")),
				mapping = null;
		if (metadata != null) {
			mapping = metadata.getJSONObject("mapping");
		}
		
		boolean flag = false;
		idColumnCellName = "";
		for (Object object : columnAry) {
			// 赋值excel表内容
			columnObj = (JSONObject) object;
			String ctype = columnObj.getString("ctype"); // 与excel表的映射情况（对应的CELL表表头名称）

			if (ctype == null || ctype.isEmpty()) {
				continue;
			}
			;
					
			String columnName = columnObj.getString("columnName");
			String columnNameSimple = Global.getOriginalColumnName(mapping, columnName).split("_0_")[1];
			logger.error(columnMap.keySet().size());
			logger.error(columnMap.keySet().toString());
			logger.error(columnNameSimple);
			
//			String columnNameSimple = columnName.replace(tableName + "_0_", "");
			// 获取数据集对应的表的信息
			column = columnMap.get(columnNameSimple.toLowerCase());
			String javaType = column.getJavaType();
			// 映射字段信息
			JSONObject cellTableColumn = new JSONObject();
			cellTableColumn.put("javaType", javaType);
			cellTableColumn.put("columnName", column.getColumnName());
			
			
			// 保存映射信息
			flag = false;	//判断是否存在映射信息
			for (Object excelName : addExcelModelJson) {
				JSONObject excelItem = (JSONObject) excelName;
				if (excelItem.getString("name").equals(ctype)) {
					String linkName = excelItem.getString("columnName");
					if (linkName != null && !linkName.isEmpty()) {
						cellTableColumn.put("byInParam", true);
						cellTableColumn.put("value", param.getString(linkName));
					}
					cellTableColumn.put("byInParam", false);
					flag = true;	//找到映射信息
					break;
				}
			}
			if(flag){
				if(column.getColumnName().equals(idColumn.getColumnName())){
					idColumnCellName = ctype.trim();
				}
				
				cellTableColumns.put(ctype.trim(), cellTableColumn);
			}
		}

		if (isTree == null || isTree.isEmpty()) {
			// 直接保存，不需要转换为树格式
			saveGridSource(is, mutilDatabaseBean, fileName,count);
		} else {
			// 树型结构
			ruleType = excelRuleData.getString("ruleType");
			if (ruleType != null) {
				if (ruleType.equals("1")) {
					// 字符串解析规则
					JSONObject strRule = excelRuleData.getJSONObject("strRule");
					String idruleGroup = strRule.getString("idruleGroup");
					String idruleDivide = strRule.getString("idruleDivide");
					nodeSizeAry = idruleDivide.split(",");
					readRule = new JSONArray();
					for (String item : nodeSizeAry) {
						readRule.add(item);
					}
				} else if (ruleType.equals("2")) {
					// 表达式解析规则
					readRule = new JSONArray();
					JSONArray expRuleArry = excelRuleData.getJSONArray("expRule");
					for (Object expRule : expRuleArry) {
						JSONObject expRuleObj = (JSONObject) expRule;
						JSONObject levelExp = expRuleObj.getJSONObject("levelExp");
						JSONObject parentLevelExp = expRuleObj.getJSONObject("parentLevelExp");
						JSONObject expActRule = new JSONObject();
						expActRule.put("level", expRuleObj.get("level"));
						expActRule.put("levelActExp", levelExp.getString("expActVal"));
						if(parentLevelExp!=null){
							expActRule.put("parentActExp", parentLevelExp.getString("expActVal"));
						}
						readRule.add(expActRule);
					}
					// readRule =excelRuleData.getJSONArray("expRule");
				}
			}
			saveTreeSource(is, mutilDatabaseBean, fileName);
		}

		if (isBackError == null || (!isBackError.equals("on") && !isBackError.equals("true"))) {
			
			return null;
		}else{
			map.put("count", countMap);
			map.put("errorData", errorData);
			return map;
		}
	}

	/**
	 * 解析树型结构规则
	 * 
	 * @param is
	 * @param mutilDatabaseBean
	 * @throws Exception
	 */
	public void saveTreeSource(InputStream is, MutilDatabaseBean mutilDatabaseBean,String fileName) throws Exception {
		List<Map> cellRows = new ArrayList<>();
		int count = 0;
		boolean isExcel2003 = true;
        // 对文件的合法性进行验
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
		try (Workbook xssfWorkbook = isExcel2003 ?new HSSFWorkbook(is) : new XSSFWorkbook(is)) {
			// 获取第一个工作薄
			Sheet xssfSheet = xssfWorkbook.getSheetAt(0);
			int length = 0;
			
			// 遍历excel表格，保存进去
			for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				if (rowNum < excelHeaderRowCount) {
					continue;
				} else if (rowNum == excelHeaderRowCount) {
					// 保存表头数据
					if (xssfSheet.getLastRowNum() > excelHeaderRowCount) {
						/* 得到表头这一行 */
						Row xssfRow = xssfSheet.getRow(excelHeaderRowCount);
						length = xssfRow.getLastCellNum();
						for (int i = 0; i < length; i++) {
							headList.add(xssfRow.getCell(i).toString());
						}
					}
				} else {
					// 读取excel中的数据放入List中
					Row xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow != null) {
						JSONObject obj = new JSONObject();
						JSONArray data = new JSONArray();
						String id = "";
						Map cellRow = new HashMap();
						boolean flag = true;
						for (int cell = 0; cell < length; cell++) {
							String headName = headList.get(cell);
							// 获取对应的字段信息
							JSONObject cellTableColumn = cellTableColumns.getJSONObject(headName);
							String str = "";
							if(cellTableColumn == null){
								//未能获取到对应的表信息,跳过
								continue;
							}
							if (!cellTableColumn.getBoolean("byInParam")) {
								// 从excel中获取
								Cell xssfXell = xssfRow.getCell(cell, Row.CREATE_NULL_AS_BLANK); // 读取excel中的格子数据
								
								if(cellTableColumn.getString("javaType") == "String"){
									str = getStrValueByCell(xssfXell,xssfXell.CELL_TYPE_STRING);
								}else{
									str = getStrValueByCell(xssfXell,null);
								}
								cellTableColumn.put("value", str);
							}
							data.add(cellTableColumn);
							if (ruleIdHeadName.equals(headName)) {
								id = str;
								if (str.isEmpty()) {
									flag = false;
								}
							}
							// 将excel表的信息放入cellRow中
							cellRow.put(headList.get(cell), str);
						}
						if (flag) {
							cellRows.add(cellRow);
						} else {
							cellRow.put("errorMessage", "该节点没有规则id");
							errorData.add(cellRow);
						}
					}
				}
			}
		}
		
		// 没有任何数据，不导入数据
		if (cellRows == null || cellRows.size() == 0) {
			return;
		}

		/**
		 * dataRows：保存Excel处理后的值（也就是处理的最终结果），注意这里面放的都是正确的结果
		 */
		List<Map> dataRows = new ArrayList<>();
		// 遍历cellRows（即Excel表里面的数据），给他建立父子关系，生成固定格式
		for (Map cellRow : cellRows) {

			// parentMap:用来存放父节点的信息
			Map parentMap = new HashMap<>();
			// 获取cellId
			if(cellRow.get(ruleIdHeadName) == null){
				cellRow.put("errorMessage", "excel中无法获取规则ID信息(规则ID名为:"+ruleIdHeadName+")");
				errorData.add(cellRow);
				continue;
			}
			String str = cellRow.get(ruleIdHeadName).toString();

			Map data = new HashMap();
			Long ids;
			ids = idGenerator.nextId(tableName);
			data.put("cellId", str);
			data.put("children", new ArrayList<Map>());
			data.put("content", cellRow);
			data.put("indexId", ids);
			
			// 判断是否存在父节点
			addRow(0,dataRows,data);
		}
		//遍历dataRows，并插入数据
		insertRow(0,dataRows,mutilDatabaseBean,"-1","",count);
	}

	/**
	 * 遍历dataRows并插入数据
	 * @param i
	 * @param dataRows
	 * @param data
	 */
	private void insertRow(int i, List<Map> dataRows,MutilDatabaseBean mutilDatabaseBean,String parentId,String parentTreeId,int count) {
		// 无下一个时，推出循环
		if (dataRows.size() <= i) {
			return;
		}
		Map<String,Object> data = dataRows.get(i);
		
		JSONArray columns = new JSONArray();
		/*添加parent_id*/
		JSONObject column = new JSONObject();
		column.put("columnName", "parent_id");
		column.put("javaType", "varchar");
		column.put("value", parentId);
		columns.add(column);
		
		/*添加treeid*/
		column = new JSONObject();
		String treeid = parentTreeId + data.get("indexId") +"|";
		column.put("columnName", "treeid");
		column.put("javaType", "varchar");
		column.put("value", treeid);
		columns.add(column);
		
		/*添加index_id*/
		column = new JSONObject();
		column.put("columnName", "index_id");
		column.put("javaType", "varchar");
		column.put("value", data.get("indexId"));
		columns.add(column);
		
		String id = "";
		Map<String,Object> content = (Map<String, Object>) data.get("content");
		for (String key : content.keySet()) {  
			// 获取对应的字段信息
			JSONObject cellTableColumn = cellTableColumns.getJSONObject(key);
			if(cellTableColumn != null){
				if (!cellTableColumn.getBoolean("byInParam")) {
					cellTableColumn.put("value", content.get(key));
				}
				columns.add(cellTableColumn);
				if (ruleIdHeadName.equals(key)) {
					id = content.get(key).toString();
				}
			}
		} 
		
		if (isCanRepeat == null || (!isCanRepeat.equals("on") && !isCanRepeat.equals("true"))) {
		} else {
			id = mutilDatabaseBean.getNextId(tableName, "id", actorId, databaseId);
		}
		TableModel tableModel = createTableModel(id, columns);
		// 重复插入时，查询数据库，判断是否有该信息
		if (isCanRepeat == null || (!isCanRepeat.equals("on") && !isCanRepeat.equals("true"))) {
			String sql = "select * from " + tableName + " where " + idColumn.getColumnName() + "='" + id
					+ "'";
			List<Map<String, Object>> countList = mutilDatabaseBean.getDataListBySql(sql, databaseId);
			if (countList != null && countList.size() >= 1) {
				content.put("errorMessage", "该节点已存在");
				errorData.add(content);
			} else {
				try{
					mutilDatabaseBean.insertTableData2(tableModel, databaseId);
					count++;
					countMap.put("count", count);
				}catch(Exception e){
					e.printStackTrace();
					logger.error(e.toString());
					content.put("errorMessage", e.toString());
					errorData.add(content);
				}
			}
		} else {
			try{
				mutilDatabaseBean.insertTableData2(tableModel, databaseId);
				count++;
				countMap.put("count", count);
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.toString());
				content.put("errorMessage", e.toString());
				errorData.add(content);
			}
		}
		
		insertRow(0,(List)data.get("children"),mutilDatabaseBean,data.get("indexId").toString(),treeid,count);
		
		insertRow(++i,dataRows,mutilDatabaseBean,parentId,parentTreeId,count);
	}
		
	/**
	 * 获取id有多少层,从1开始
	 * readRule :[{'level':'2','levelActExp':'$LEN(~F{cellIdValue})==3','parentActExp':'$LEN(~F{cellIdValue})==3'}]
	 * @param cellid 
	 * @return -1没有层级，从1开始
	 */
	public int getLevel(String cellid) {
		// 解析规则，获取层级数
		if (ruleType.equals("1")) {
			int idLength = cellid.length();
			int nowLen = 0;
			for (int i = 0; i < readRule.size(); i++) {
				if (idLength <= nowLen) {
					return i;
				}
				int k = (int) readRule.get(i);
				nowLen += k;
			}

		} else if (ruleType.equals("2")) {
			// 表达式解析规则
			for (int i = 0; i < readRule.size(); i++) {
				JSONObject obj = readRule.getJSONObject(i);
				String levelActExp = obj.getString("levelActExp");
				if (checkExpress(levelActExp, cellid, null)) {
					return obj.getIntValue("level");
				}
			}
		}
		return -1;
	}
	
	/**
	 * 根据EXCEL解析规则判断parentId是否是cellId父节点
	 * @param cellId
	 * @param parentId
	 * @return
	 */
	public boolean isParent(String cellId,int level,String parentId){
		// 解析规则，获取层级数
		if (ruleType.equals("1")) {
			return cellId.matches("^" + parentId + ".*");
		} else if (ruleType.equals("2")) {
			// 表达式解析规则
			for (int i = 0; i < readRule.size(); i++) {
				JSONObject obj = readRule.getJSONObject(i);
				if(obj.getInteger("level")== level){
					String parentActExp = obj.getString("parentActExp");
					return checkExpress(parentActExp, cellId, parentId);
				}
			}
		}
		return false;
	}
	/**
	 * 表达式校验
	 * @param expression
	 * @param cellid	单元格值
	 * @param parentid	父节点值
	 * @return
	 */
	public boolean checkExpress(String expression, String cellid, String parentid) {
		List<Variable> variables = new ArrayList<Variable>();
		if(cellid != null && !cellid.isEmpty()){
			variables.add(Variable.createVariable("~F{cellIdValue}",cellid));
		}
		if(parentid != null && !parentid.isEmpty()){
			variables.add(Variable.createVariable("~F{parentCellIdValue}",parentid));
		}
		boolean flag = (boolean)ExpressionEvaluator.evaluate(expression, variables);
		
		return flag;
	}

	/**
	 * 插入行，在dataRows中根据excel解析规则插入数据
	 * @param i	//循环标识，从0开始
	 * @param dataRows	//已放入的数据
	 * @param cellId	//单元格的编码值，用于查询父节点
	 * @param parentMap	//父节点信息
	 * @param level	//当前级别
	 */
	public void addRow(int i, List<Map> dataRows, Map data) {
		int dataRowsSize = dataRows.size();
		String cellId = (String)data.get("cellId");
		int level = getLevel(cellId);
		if (level == 1) {
			dataRows.add(data);
			return;
		}
		if(level < 1){
			Map<String,Object> content = (Map<String, Object>) data.get("content");
			content.put("errorMessage", "该节点不符合任何一层");
			errorData.add(content);
			return;
		}
		
		// 无下一个时，推出循环
		if (dataRowsSize <= i) {
			return;
		}
		
		//获取比较节点信息
		Map dataRow = dataRows.get(i);
		//获取节点cellId
		String parentId = dataRow.get("cellId").toString();
		
		int parentidLevel = getLevel(parentId);
		if(level == parentidLevel + 1 && isParent(cellId,level,parentId)){
			//为子节点
//			data.put("parent_id", dataRow.get("indexId"));
			ArrayList children = (ArrayList) dataRow.get("children");
			children.add(data);
		}else{
			if(parentidLevel == level + 1 && isParent(parentId,parentidLevel,cellId)){
				//为父节点
				dataRows.remove(i);
				ArrayList children = (ArrayList) data.get("children");
				children.add(dataRow);
				dataRows.add(data);
			}else{
				//都不是
				if(parentidLevel >= level){
					addRow(++i, dataRows, data);
				}else{
					addRow(0,(List) dataRow.get("children"), data);
					addRow(++i, dataRows, data);
				}
			}
		}
	}
	
	/**
	 * 寻找父节点
	 * @param i	//循环标识，从0开始
	 * @param dataRows	//已放入的数据
	 * @param cellId	//单元格的编码值，用于查询父节点
	 * @param parentMap	//父节点信息
	 * @param level	//当前级别
	 */
	public void getParentMap(int i, List<Map> dataRows, String cellId, Map parentMap, int level) {
		// 无下一个时，推出循环
		if (dataRows.size() <= i) {
			return;
		}
		//获取比较节点信息
		Map data = dataRows.get(i);
		//获取节点cellId
		String parentId = data.get("cellId").toString();

		boolean flag = isParent(cellId,0,parentId);
		if(flag){
			//为子节点
			parentMap.clear();
			parentMap.putAll(data);
		}else{
			if(isParent(parentId,0,cellId)){
				
			}
		}
		
		// 判断节点cellId是否包含parentId
		Pattern pattern = Pattern.compile("^" + parentId + ".*");
		Matcher matcher = pattern.matcher(cellId);
		boolean b = matcher.matches();
		// 若包含，即正确
		if (b) {
			// 继续判断子节点
			parentMap.clear();
			data.put("level", level);
			parentMap.putAll(data);
			getParentMap(0, (List) parentMap.get("children"), cellId, parentMap, ++level);
		} else {
			// 若不包含，跳过
			getParentMap(++i, dataRows, cellId, parentMap, level);
		}
	}
}
