package com.glaf.excel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.factory.TableFactory;
import com.glaf.core.identity.User;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.form.core.util.MutilDatabaseBean;

public class ExcelUtils {
	MutilDatabaseBean mutilDatabaseBean = null;
	
	private String tableName;	//表名
	private String actorId;	//用户
	private Long databaseId;	//标段
	private ColumnDefinition idColumn;	//主键列
	private JSONArray columnAry;	//数据集信息
	private Map<String, ColumnDefinition> columnMap;	//数据集字段信息
	private JSONArray addExcelModelJson;	//表头规则，即数据集的表头与excel的表头对应关系
	private JSONObject param;	//输入形参
	private String parentIndexId;	//传值parentId时，的父节点信息
	private String parentTreeId;	//传值parentId时，的父节点信息
	private int[] idruleDivideArray;	// EXCEL编码规则ID数组，即[2,2,3,4]
	private String isTolerantError;		// 是否允许容错
	private String isAllowRepeat;	// 是否允许重复插入
	private String paramValueStr;	//所有参数值
	private int idruleGroup;	//最大层数
	
	public void saveSource(HttpServletRequest request,Map<String, String> ruleMap, Map map,MutilDatabaseBean mutilDatabaseBean) throws Exception {
		this.mutilDatabaseBean = mutilDatabaseBean;	//全局变量
		
		User user = RequestUtils.getUser(request);
		actorId = user.getActorId();	//用户ID

		//错误信息
		List errorData =  (List) map.get("errorData");
		
		// EXCEL上传，编码规则信息
		JSONObject ruleData = JSON.parseObject(ruleMap.get("excelIdRule"));
		/**
		 * 得到规则信息
		 */
		// String showErrorMessage = ruleMap.get("showErrorMessage");
		// 是否允许容错
		isTolerantError = ruleData.getString("isCanFault");
		// 是否允许重复插入
		isAllowRepeat = ruleData.getString("isCanRepeat");
		//最大层数
		idruleGroup = ruleData.getIntValue("idruleGroup");
		
		// EXCEL编码规则
		String[] nodeSizeAry = ruleData.getString("idruleDivide").split(",");	
		/**
         * 将字符串数组转化成int数组
         */
		idruleDivideArray = new int[nodeSizeAry.length];	// EXCEL编码规则ID数组，即[2,2,3,4]
		for(int i=0;i<nodeSizeAry.length;i++){
			idruleDivideArray[i] = Integer.parseInt(nodeSizeAry[i]);
		}
		
		//输入形参
		String paramStr = ruleMap.get("param");
		param = null;
		if(paramStr != null && !paramStr.isEmpty()){
			param = JSON.parseObject(paramStr);
		}
		paramValueStr = "";	//所有参数值
		if(param != null && param.size() > 0){
			Iterator iterator= param.values().iterator();
			while (iterator.hasNext()) {
				paramValueStr = iterator.next().toString();
			}
		}
		
		String parentWBSID = null;//传值parentId时，的父节点信息
		parentIndexId = param != null ? param.getString("parentId") : null;//传值parentId时，的父节点信息
		String cellIdHeadName = map.get("cellIdHeadName").toString();	//编码key值，即编码的表头信息
		
		//Excel规则信息
		/*得到excel表头的规则*/
		addExcelModelJson = JSON.parseArray(ruleMap.get("addExcelModel"));	//表头规则
		
		
		String dataSourceSet = ruleMap.get("dataSourceSet");	//数据集
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONObject datasourceSetJSONObject = null;
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
		}
		if (datasourceSetJSONObject == null) {
			return;
		}

		JSONArray tablesAry = datasourceSetJSONObject.getJSONArray("selectDatasource");
		if (tablesAry == null || tablesAry.size() != 1) {
			return;
		}
		databaseId = tablesAry.getJSONObject(0).getLong("databaseId");	//数据集标段
		if(databaseId == null){
			databaseId = 0l;
		}
		columnAry = datasourceSetJSONObject.getJSONArray("selectColumns");	//表信息

		JSONObject firstObj = columnAry.getJSONObject(0);
		/*获取表名*/
		tableName = firstObj.getString("tableName");
		
		if (!DBUtils.isAllowedTable(tableName)) {
			throw new RuntimeException("不允许访问系统表。");
		}
		
		/*获取列名*/
		List<ColumnDefinition> columns = TableFactory.getColumnDefinitions(databaseId, tableName);
		columnMap = new HashMap<String, ColumnDefinition>();	//所有列
		idColumn = null;	//主键列
		for (ColumnDefinition column : columns) {
			if (column.isPrimaryKey()) {
				idColumn = column;
			}
			columnMap.put(column.getColumnName().toLowerCase(), column);
		}
		
		//若存在parentId参数是，查询出指定的数据信息
		//遍历数据集字段信息,找到cellIdHeadName对应的tableName;
		String cellIdTableName = null;
		for (Object object : columnAry) {
		    //赋值excel表内容
			JSONObject columnObj = (JSONObject) object;
			String ctype = columnObj.getString("ctype");
			if(ctype.equals(cellIdHeadName)){
				String columnName = columnObj.getString("columnName");
				String columnNameSimple = columnName.replace(tableName+"_0_", "");
				//获取数据集对应的表的信息
				ColumnDefinition column = columnMap.get(columnNameSimple.toLowerCase());
				cellIdTableName = column.getColumnName();
				break;
			}
		}
		//获取表中的数据信息
		List<Map> dataRows = (List)map.get("dataRows");
		if(parentIndexId != null && !parentIndexId.isEmpty()){
			String sql = "select * from " + tableName + " where INDEX_ID = '" +parentIndexId + "'";
			List<Map<String,Object>> datalist = mutilDatabaseBean.getDataListBySql(sql, databaseId);
			if(datalist != null && !datalist.isEmpty()){
				Map data = datalist.get(0);
				parentWBSID = data.get(cellIdTableName.toLowerCase()).toString();
				parentTreeId = data.get("TREEID").toString();
			}
			if(parentWBSID == null || parentWBSID.isEmpty()){
				throw new Exception("父节点无WBS编码！");
			}
			if(parentTreeId == null || parentTreeId.isEmpty()){
				throw new Exception("父节点无TREEID！");
			}
			//有parentIdexId时校验，若非其子节点报错
			for(Map dataRow : dataRows){
				Map content = (Map) dataRow.get("content");
				String cellId = content.get(cellIdHeadName).toString();
				Pattern pattern = Pattern.compile("^" + parentWBSID + "..*");
				Matcher matcher = pattern.matcher(cellId);
				if(!matcher.matches()){
					throw new Exception("WBS 编码与父节点不相同！");
				}
			}
		};
		
		/*得到表中的数据*/
		JSONArray bodyJSONArray = new JSONArray();
		if(map != null && map.size()>0){
			bodyJSONArray = (JSONArray) map.get("bodyJsonAry");
		}
		//插入数据
		try{
			insertTable(dataRows,null,errorData);
		}catch(Exception e){
			e.printStackTrace();
			map.put("status","40000");
			map.put("message", "出现异常，请查看后台代码");
		}
		
		Map<String,String> nameMap = new HashMap<String,String>();	
	}
	
	/**
	 * 
	 * @param dataRows
	 * @param parentRow
	 * @param tableName
	 * @param actorId
	 * @param databaseId
	 * @param idColumn
	 * @param columnAry
	 * @param columnMap
	 * @param addExcelModelJson
	 * @param param
	 * @param errorData	//错误信息
	 * @throws Exception
	 */
	public void insertTable(List<Map> dataRows,Map parentRow,List errorData) throws Exception{
		
		if(dataRows == null || dataRows.size() == 0){
			return;
		}
		//遍历数据
		for(Map dataRow:dataRows){
			//插入数据
			TableModel tableModel = new TableModel();
			ColumnDefinition column = null;
			ColumnModel colModel = null ;
			
			tableModel.setTableName(tableName);
			String nextId ="";
			String cellId = dataRow.get("cellId").toString();	//WBS编码号
			String cellIdHeadName = dataRow.get("cellIdHeadName").toString();	//WBS编码号对应的Excel的表头名
			String parentWhere = "";	//查询父节点的条件
			String cellIdtableNum = "";	//WBS编码号对应的表的列名
			
			if(isAllowRepeat == null || (!isAllowRepeat.equals("on") && !isAllowRepeat.equals("true"))){
				nextId = cellId + paramValueStr;	//若不允许重复，则id为编码值 + 参数值 
			}else{
				nextId = mutilDatabaseBean.getNextId(tableName, "id", actorId, databaseId);
			}
			
			ColumnModel idCol = new ColumnModel();
			idCol.setColumnName(idColumn.getColumnName());
			idCol.setJavaType(idColumn.getJavaType());
			idCol.setValue(nextId);
			tableModel.addColumn(idCol);
			
			/*添加parent_id*/
			ColumnModel parentIdColModel = new ColumnModel();
			parentIdColModel.setColumnName("parent_id");
			parentIdColModel.setJavaType("varchar");
			parentIdColModel.setValue("");
			tableModel.addColumn(parentIdColModel);
			
			/*添加treeid*/
			ColumnModel treeIdColModel = new ColumnModel();
			treeIdColModel.setColumnName("treeid");
			treeIdColModel.setJavaType("varchar");
			treeIdColModel.setValue("");
			tableModel.addColumn(treeIdColModel);
			
			/*添加index_id*/
			String indexId = dataRow.get("indexId").toString();
			colModel = new ColumnModel();
			colModel.setColumnName("index_id");
			colModel.setJavaType("varchar");
			colModel.setValue(indexId);
			tableModel.addColumn(colModel);

			/*EXCEL中的内容*/
			Map content = (Map) dataRow.get("content");
		    JSONObject columnObj  = null ;
		    //遍历数据集字段信息
			for (Object object : columnAry) {
			    //赋值excel表内容
				columnObj = (JSONObject) object;
				String ctype = columnObj.getString("ctype");
				String columnName = columnObj.getString("columnName");
				String columnNameSimple = columnName.replace(tableName+"_0_", "");
				//获取数据集对应的表的信息
				column = columnMap.get(columnNameSimple.toLowerCase());
				String javaType = column.getJavaType();
				colModel = new ColumnModel();
				colModel.setColumnName(column.getColumnName());
				colModel.setJavaType(javaType);
				String value = "";
				//赋值数据集信息，从excel中获取或输入参数
				for(Object excelName :addExcelModelJson){
					JSONObject excelItem = (JSONObject)excelName;
					if(excelItem.getString("name").equals(ctype)){
						String linkName = excelItem.getString("columnName");
						if(linkName == null || excelItem.getString("columnName").isEmpty()){
							//若不是关联，即不是参数获取的
							if(content.get(ctype) == null){
								value = "";
							}else{
								value =  content.get(ctype).toString();
								if(cellIdHeadName.equals(ctype)){
									cellIdtableNum = columnName.split("_0_")[1];
								}
							}
							
						}else{
							if(param != null && param.size() > 0){
								value = param.getString(linkName);
							}
							if(value == null){
								value = "";
							}
							parentWhere += " and " + columnName.split("_0_")[1] + " = '" + value + "' ";
						}
						break;
					}
				}
				if(javaType.equals("Integer")){
					if(!value.isEmpty()){
						colModel.setValue((int)(Double.parseDouble(value)));
					}else{
						colModel.setValue(0);
					}
				}else if(javaType.equals("Double")){
					if(!value.isEmpty()){
						colModel.setValue(Double.parseDouble(value.replaceAll(",", "")));
					}else{
						colModel.setValue(0.0);
					}
				}else if(javaType.equals("Date")){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");  
				    Date date = sdf.parse(value);  
					colModel.setValue(date);
				}else if(javaType.equals("String")){
					colModel.setValue(value);
				}else{
					throw new Exception("未知类型");
				}
				tableModel.addColumn(colModel);
			} 
			int level = idRule(cellId, idruleDivideArray, isTolerantError);
			if((parentRow == null || parentRow.isEmpty()) && level > 1 && level <= idruleGroup){
				//若是没有父节点（就是第一层），但是id规则的层集不是第一，寻找父节点
				//根据id规则，可获取其父节点的编码id
				String cellParentId = getParentId(cellId,idruleDivideArray);
					/**
					 * 数据库查询语句
					 */
					String sql = "select * from " + tableName + " where "+cellIdtableNum+" = '" +cellParentId + "' " + parentWhere;
					List<Map<String,Object>> datalist = mutilDatabaseBean.getDataListBySql(sql, databaseId);
					if(datalist != null && datalist.size() > 0){
						Map dataMap = datalist.get(0);
						/*修改parent_id*/
						parentIdColModel.setValue(dataMap.get("INDEX_ID"));
						
						/*修改treeid*/
						String treeid = (String)dataMap.get("TREEID")+indexId+"|";
						treeIdColModel.setValue(treeid);
						dataRow.put("treeId",treeid);
					}else{
						//错误数据
						content.put("errorMessage", "无法查询父节点信息");
						errorData.add(content);
						addErrorRows(errorData,(List)dataRow.get("children"),"父节点信息错误");
						return;
					}
			}else if(level == 0 || level > idruleGroup){
				//错误数据
				content.put("errorMessage", "第0层数据错误");
				errorData.add(content);
//				addErrorRows(errorData,(List)dataRow.get("children"));
//				return;
			}else{
				
				/*修改parent_id*/
				if(parentRow == null || parentRow.isEmpty()){
					//若有父节点ID时，用父节点ID
					if(parentIndexId != null && !parentIndexId.isEmpty()){
						dataRow.put("treeId",parentTreeId + "|" + indexId + "|");
						parentIdColModel.setValue(parentIndexId);
					}else{
						dataRow.put("treeId", indexId + "|");
						parentIdColModel.setValue("-1");
					}
				}else{
					String parentcellId = parentRow.get("cellId").toString();
					int parentlevel = idRule(parentcellId, idruleDivideArray, isTolerantError);
					if(level != parentlevel + 1){
						content.put("errorMessage", "层级不是父节点的下一层");
						errorData.add(content);
						addErrorRows(errorData,(List)dataRow.get("children"),"父节点信息错误");
						return;
					}
					dataRow.put("treeId", parentRow.get("treeId") + indexId +"|");
					parentIdColModel.setValue(parentRow.get("indexId").toString());
				}
				/*修改treeid*/
				treeIdColModel.setValue(dataRow.get("treeId").toString());
			}
			
			//插入语句
			try {
				//重复插入时，查询数据库，判断是否有该信息
				if(isAllowRepeat == null || (!isAllowRepeat.equals("on") && !isAllowRepeat.equals("true"))){
					String sql = "select * from " +tableName+ " where "+idColumn.getColumnName()+ "='"+nextId+"'";
					List<Map<String, Object>> countList = mutilDatabaseBean.getDataListBySql(sql, databaseId);
	  				if(countList != null && countList.size() >= 1 ){
	  						content.put("errorMessage", "该节点已存在");
							errorData.add(content);
	  				}else{
  						mutilDatabaseBean.insertTableData(tableModel, databaseId);
  					}
				}else{
					mutilDatabaseBean.insertTableData(tableModel, databaseId);
				}
			} catch (Exception e) {
				throw new Exception("save error" + e.getMessage());
			}
			
			//插入孩子节点信息
			insertTable((List)dataRow.get("children"),dataRow,errorData);
		}
	}
	
	//添加错误信息
	public void addErrorRows(List errorRows,List rowsData,String errorMessage){
		if(rowsData == null || rowsData.size() == 0){
			return;
		}
		for(int i = 0 ; i < rowsData.size(); i ++){
			/*EXCEL中的内容*/
			Map rowData = (Map) rowsData.get(i);
			Map content = (Map) rowData.get("content");
			content.put("errorMessage", errorMessage);
			errorRows.add(content);
			addErrorRows(errorRows,(List)rowData.get("children"),errorMessage);
		}
		
		
		
	}
	
	/**
	 * 获取id有多少层
	 * 
	 * @param id
	 * @param length
	 * @param number
	 * @return
	 */
	public int idRule(String id, int[] number, String isTolerantError) {
		int ceng = 0;
		int idLength = id.length();
		int count = 0;
		count = 0;
		if (isTolerantError != null && (isTolerantError.equals("on") || isTolerantError.equals("true"))) {
			for (int i = 0; i < number.length; i++) {
				if (idLength <= count) {
					// ceng++;
					return ceng;
				}
				ceng++;
				count += number[i];
			}
		} else {
			for (int i = 0; i < number.length; i++) {
				if (idLength <= count) {
					if (idLength == count) {
						// ceng++;
						return ceng;
					} else {
						return 0;
					}

				}
				ceng++;
				count += number[i];
			}
		}
		if(idLength > count){
			return (ceng+1);
		}
		
		return ceng;
	}
	
	/**
	 * 根据cellId，去除最后一个节点的id，生成新的cellParentId
	 */
	public String getParentId(String cellId, int[] number) {
		String cellParentId = null;
		int cellIdLength = cellId.length();
		int count = number[0];
		for (int i = 1; i < number.length; i++) {
			if (cellIdLength <= count + number[i]) {
				cellParentId = cellId.substring(0, count);
				return cellParentId;
			}
			count += number[i];
		}
		return cellParentId;
	}
}
